package com.yin.utils.xml;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Iterator;
import java.util.Stack;

/**
 *
 *@author yin
 *@date 2025-03-27
 *@Description
 */
@Slf4j
public class StAXParserExample {
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        long startTime = Instant.now().toEpochMilli();
        XmlPojo xmlPojo = readXml("D:\\ld_client_simple\\fastreport\\reports\\40801.frx", "GBK");
        System.out.println(JSONUtil.toJsonStr(xmlPojo));
//        assert xmlPojo != null;
//        String struct = xmlPojo.getElAttributeValue("struct","value",0);
//        System.out.println(struct);
//        System.out.println("用时:" + (Instant.now().toEpochMilli() - startTime) + "毫秒" + (Instant.now().toEpochMilli() - startTime) / 1000 + "秒");
    }

    public static XmlPojo readXml(String path, String encoding) {
        try {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encoding);
            XMLEventReader eventReader = xmlInputFactory.createXMLEventReader(inputStreamReader);
            XmlPojo root = new XmlPojo();
            Stack<XmlPojo> stack = new Stack<>();
            stack.push(root);
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    log.info("开始节点名称: {}", event.asStartElement().getName());
                    String startName = event.asStartElement().getName().toString();
                    XmlPojo xmlPojo = new XmlPojo();
                    xmlPojo.setStartName(startName);
                    //获取标签属性信息
                    Iterator<Attribute> attributes = event.asStartElement().getAttributes();
                    while (attributes.hasNext()) {
                        Attribute next = attributes.next();
                        if (next != null) {
                            xmlPojo.getTagAttribute().put(next.getName().toString(), next.getValue());
                        }
                    }
                    stack.peek().getXmlPojoListChildren().add(xmlPojo);
                    stack.push(xmlPojo);
                } else if (event.isCharacters()) {
                    log.info("节点内容: {}", event.asCharacters().getData());
                    String data = event.asCharacters().getData();
                    if (data != null) {
                        XmlPojo peek = stack.peek();
                        peek.setStartValue(data);
                    }
                } else if (event.isEndElement()) {
                    log.info("结束节点名称: {}", event.asEndElement().getName());
                    String endName = event.asEndElement().getName().toString();
                    XmlPojo currentElement = stack.pop();
                    currentElement.setEndName(endName);
//                    log.info("当前节点: {}",JSONUtil.toJsonStr(stack.peek()));
                }
            }
            return root;
//            log.info("XML解析结果: {}", JSONUtil.toJsonStr(root.getXmlPojoListChildren()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Test
    public void test() throws FileNotFoundException, UnsupportedEncodingException, XMLStreamException {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        FileInputStream fileInputStream = new FileInputStream("E:\\文件信息保存\\2025-03-24\\plstemp.xml");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK");
        XMLEventReader eventReader = xmlInputFactory.createXMLEventReader(inputStreamReader);
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();
            if (event.isStartElement()) {
                System.out.println("开始节点名称:" + event.asStartElement().getName());
                Iterator attributes = event.asStartElement().getAttributes();
                while (attributes.hasNext()) {
                    Object next = attributes.next();
                    if (next instanceof Attribute) {
                        Attribute attribute = (Attribute) next;
                        System.out.println("属性名称:" + attribute.getName() + " 属性值:" + attribute.getValue());
                    }
                }
            }
        }
    }
}
