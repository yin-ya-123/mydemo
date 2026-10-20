package com.yin.utils.excel.poi;

import lombok.Getter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 *@author yin
 *@date 2025-04-08
 *@Description
 */
public class ExcelRead {
    @Getter
    private final List<List<String>> data = new ArrayList<>();
    public ExcelRead read(String path) {
        try (OPCPackage open = OPCPackage.open(path)) {
            XSSFReader reader = new XSSFReader(open);
            SharedStringsTable sharedStringsTable = (SharedStringsTable) reader.getSharedStringsTable();
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
//            saxParserFactory.setNamespaceAware(true);
            XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
            Iterator<InputStream> sheetsData = reader.getSheetsData();
            while (sheetsData.hasNext()) {
                try(InputStream next = sheetsData.next();){
                    MillionRowHandler millionRowHandler = new MillionRowHandler(sharedStringsTable);
                    xmlReader.setContentHandler(millionRowHandler);
                    xmlReader.parse(new InputSource(next));
                    data.addAll(millionRowHandler.getData());
                    millionRowHandler.close();
                }
            }
            sharedStringsTable.close();
        } catch (SAXException | ParserConfigurationException | OpenXML4JException | IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
