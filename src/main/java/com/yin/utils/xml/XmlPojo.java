package com.yin.utils.xml;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.collections4.map.LinkedMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *@author yin
 *@date 2025-03-27
 *@Description
 */
@Data
public class XmlPojo {
    //获取节点名称
    private String startName;
    //获取节点结束名称
    private String endName;
    //获取节点内容
    private String startValue;
    //获取节点属性
    private LinkedMap<String, String> tagAttribute = new LinkedMap<>();
    //获取子节点数据
    private List<XmlPojo> xmlPojoListChildren = new ArrayList<>();
    //获取指定节点数据
    @Getter
    private String elValue;
    //获取指定节点的属性数据
    private String elAttributeValue;
    private int childrenSize;
    // 索引映射，存储节点名称和对应的节点对象列表
    private Map<String, List<XmlPojo>> nameIndex = new HashMap<>();
    // 缓存机制，存储已经查找过的属性值
    private Map<String, Map<String, String>> attributeCache = new HashMap<>();

    //指定节点下的所有子节点
    public int getChildrenSize(String elName) {
        getChildrenSize(this.xmlPojoListChildren, elName);
        return childrenSize;
    }

    private void getChildrenSize(List<XmlPojo> xml, String elName) {
        for (XmlPojo xmlPojo : xml) {
            if (xmlPojo.startName.equals(elName)) {
                childrenSize = xmlPojo.xmlPojoListChildren.size();
                return;
            } else {
                getChildrenSize(xmlPojo.getXmlPojoListChildren(), elName);
            }
        }
    }

    ;


    /**
     *
     * @param elName 获取指定节点的值
     * @return 节点值
     */
    public String getElValue(String elName) {
        if (!this.xmlPojoListChildren.isEmpty()) {
            setElValue(this.xmlPojoListChildren, elName);
        }
        return elValue;
    }

    private void setElValue(List<XmlPojo> xml, String elName) {
        for (XmlPojo xmlPojo : xml) {
            if (xmlPojo.startName.equals(elName)) {
                this.elValue = xmlPojo.startValue;
                return;
            }
            if (!xmlPojo.getXmlPojoListChildren().isEmpty()) {
                setElValue(xmlPojo.getXmlPojoListChildren(), elName);
            }
        }
    }

    /**
     *
     * @param elName 获取指定节点的属性值
     * @param attributeName 属性名称
     * @param index 节点索引
     * @return 属性值
     */
    public String getElAttributeValue(String elName, String attributeName, int index) {
        if (!this.xmlPojoListChildren.isEmpty()) {
            setElAttributeValue(this.xmlPojoListChildren, elName, attributeName, index, 1);
        }
        return elAttributeValue;
    }

    /**
     *
     * @param elName 获取指定节点的属性值
     * @param attributeName 属性名称
     * @return 属性值 根据节点名称、固定格式属性="属性名称=属性值=需要获取属性的名称"
     */
    public String getElAttributeValueForType2(String elName, String attributeName) {
        if (!this.xmlPojoListChildren.isEmpty()) {
            setElAttributeValue(this.xmlPojoListChildren, elName, attributeName, 0, 2);
        }
        return elAttributeValue;
    }

    /**
     * 根据属性值获取指定节点的属性值
     * @param xml xml
     * @param elName 节点名称
     * @param attributeName 属性名称
     * @param index 节点索引
     * @param type
     * 1:根据节点名称、属性名称、节点索引获取属性值
     * 2:根据节点名称、固定格式属性="属性名称=属性值=需要获取属性的名称"
     */
    public void setElAttributeValue(List<XmlPojo> xml, String elName, String attributeName, int index, int type) {
        for (int i = 0; i < xml.size(); i++) {
            XmlPojo xmlPojo = xml.get(i);
            this.elValue = xmlPojo.startValue;
            LinkedMap<String, String> tagAttribute1 = xmlPojo.getTagAttribute();
            if (type == 1 && i == index && xmlPojo.startName.equals(elName)) {
                if (xmlPojo.getTagAttribute() != null) {
                    elAttributeValue = isAttributeIsEmpty(tagAttribute1, attributeName);
                }
                return;
            }
            if (type == 2 && xmlPojo.startName.equals(elName)) {
                String[] split = attributeName.split("=");
                elAttributeValue = isAttributeIsEmpty(tagAttribute1, split[0]);
                if (elAttributeValue.equals(split[1])) {
                    elAttributeValue = isAttributeIsEmpty(tagAttribute1, split[2]);
                    return;
                }
            }
            if (!xmlPojo.getXmlPojoListChildren().isEmpty()) {
                setElAttributeValue(xmlPojo.getXmlPojoListChildren(), elName, attributeName, index, type);
            }
        }
    }

    public String isAttributeIsEmpty(LinkedMap<String, String> tagAttribute, String attributeName) {
        if (tagAttribute.get(attributeName) == null) {
            throw new NullPointerException("属性不存在!");
        }
        return tagAttribute.get(attributeName);
    }
}
