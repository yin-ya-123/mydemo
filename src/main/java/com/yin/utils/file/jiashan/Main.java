package com.yin.utils.file.jiashan;

import cn.hutool.json.JSONUtil;
import com.yin.utils.FileUtils;
import com.yin.utils.xml.XmlPojo;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.junit.Test;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static com.yin.utils.xml.StAXParserExample.readXml;

/**
 *
 *@author yin
 *@date 2025-04-16
 *@Description
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String strDy = "0852696368456469740A260100007B5C727466315C616E73695C616E73696370673933365C64656666305C6E6F7569636F6D7061745C6465666C616E67313033335C6465666C616E676665323035327B5C666F6E7474626C7B5C66305C666E696C5C6663686172736574313334205C2763625C2763655C2763635C2765353B7D7B5C66315C666E696C5C6663686172736574313334205461686F6D613B7D7D0D0A7B5C2A5C67656E657261746F722052696368656432302031302E302E32323632317D5C766965776B696E64345C756331200D0A5C706172645C71635C625C66305C667333325C6C616E67323035325C2762365C2761625C2764315C2766345C2762645C2761645C2762315C2766355C2764325C2762645C2764345C2762615C62305C66315C667331365C7061720D0A7D0D0A00";
        String strJs = "0852696368456469740A260100007B5C727466315C616E73695C616E73696370673933365C64656666305C6E6F7569636F6D7061745C6465666C616E67313033335C6465666C616E676665323035327B5C666F6E7474626C7B5C66305C666E696C5C6663686172736574313334205C2763625C2763655C2763635C2765353B7D7B5C66315C666E696C5C6663686172736574313334205461686F6D613B7D7D0D0A7B5C2A5C67656E657261746F722052696368656432302031302E302E32323632317D5C766965776B696E64345C756331200D0A5C706172645C71635C625C66305C667333325C6C616E67323035325C2762635C2763655C2763395C2763365C2762635C2763655C2762365C2761625C2764325C2762645C2764345C2762615C62305C66315C667331365C7061720D0A7D0D0A00";
        String path = "D:\\Newhis\\report";
        String path1 = "E:\\文件信息保存\\2025-04-16\\new\\";
        File file = new File(path);
        for (File listFile : file.listFiles()) {
            String absolutePath = listFile.getAbsolutePath();
//            System.out.println(absolutePath);
            if (absolutePath.contains(".fr3")) {
                String s = FileUtils.readFile(absolutePath, "utf-8");
                String fileName = FileUtils.getFileName(absolutePath);
                String path2 = path1 + fileName + ".fr3";
                if (s.contains("东阳江滨医院") || s.contains(strDy)) {
                    String s1 = s.replaceAll("东阳江滨医院", "嘉善嘉东医院");
                    s1 = s1.replaceAll(strDy, strJs);
                    FileUtils.writeFile(path2, s1, false);
                    System.out.println(path2);
                }
            }
        }
    }
    @Test
    public void test() {
        String path ="D:\\Newhis\\report\\mzcf_new.fr3";
        XmlPojo xmlPojo = readXml(path, "GBK");
        String tfrxRichView = xmlPojo.getElAttributeValue("TfrxRichView","PropData",0);
//        System.out.println(tfrxRichView);
        // 步骤1：十六进制转字节数组
        byte[] bytes = hexStringToByteArray(tfrxRichView);

        // 步骤2：解码为字符串（检查是否为RTF）
        String decodedString = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("Decoded String:\n" + decodedString);
        // 步骤3：尝试解析RTF内容（需要Apache POI依赖）
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            HWPFDocument document = new HWPFDocument(bais);
            WordExtractor extractor = new WordExtractor(document);
            System.out.println("Parsed RTF Content:\n" + extractor.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
