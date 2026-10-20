
import com.yin.utils.FileUtils;
import org.apache.poi.ss.formula.functions.T;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author yin
 * @date 2025-02-07
 * @Description
 */
public class Text5 {

    public static void main(String[] args) throws IOException {
        String path = "E:\\文件信息保存\\2025-02-08";
        String filePath = "E:\\文件信息保存\\2025-02-08\\医嘱-匹配-分割8.csv";
        String saveZip = "E:\\文件信息保存\\2025-02-08\\测试.zip";
//        FileUtils.compress(new File(path), saveZip);
        File file = new File(filePath);
        File sourcePath = new File(path);
//        FileUtils.createFileZip(FileUtils.getFileName(filePath),file,saveZip);
        FileUtils.toZip(sourcePath,saveZip);
//        FileInputStream fileInputStream = new FileInputStream(path);
//        FileInputStream fileInputStream2 = new FileInputStream(path2);
//        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(saveZip));
//        zipOutputStream.putNextEntry(new ZipEntry("医嘱-匹配-分割0.csv"));
//        byte[] buffer = new byte[1024];
//        int bytesRead;
//        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
//            zipOutputStream.write(buffer, 0, bytesRead);
//        }
//        zipOutputStream.putNextEntry(new ZipEntry("医嘱-匹配-分割2.csv"));
//        while ((bytesRead = fileInputStream2.read(buffer)) != -1) {
//            zipOutputStream.write(buffer, 0, bytesRead);
//        }
//        zipOutputStream.close();
//        fileInputStream.close();
//        fileInputStream2.close();


    }
    /**
     80      * 递归压缩方法
     81      * @param sourceFile 源文件
     82      * @param zos        zip输出流
     83      * @param name       压缩后的名称
     84      * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
     85      *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     86      * @throws Exception
     87      */
    /**
     88     private static void compress(File sourceFile, ZipOutputStream zos, String name,
     89boolean KeepDirStructure) throws Exception{
     90         byte[] buf = new byte[BUFFER_SIZE];
     91         if(sourceFile.isFile()){
     92             // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
     93             zos.putNextEntry(new ZipEntry(name));
     94             // copy文件到zip输出流中
     95             int len;
     96             FileInputStream in = new FileInputStream(sourceFile);
     97             while ((len = in.read(buf)) != -1){
     98                 zos.write(buf, 0, len);
     99             }
     100             // Complete the entry
     101             zos.closeEntry();
     102             in.close();
     103         } else {
     104             File[] listFiles = sourceFile.listFiles();
     105             if(listFiles == null || listFiles.length == 0){
     106                 // 需要保留原来的文件结构时,需要对空文件夹进行处理
     107                 if(KeepDirStructure){
     108                     // 空文件夹的处理
     109                     zos.putNextEntry(new ZipEntry(name + "/"));
     110                     // 没有文件，不需要文件的copy
     111                     zos.closeEntry();
     112                 }
     113             }else {
     114                 for (File file : listFiles) {
     115                     // 判断是否需要保留原来的文件结构
     116                     if (KeepDirStructure) {
     117                         // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
     118                         // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
     119                         compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
     120                     } else {
     121                         compress(file, zos, file.getName(),KeepDirStructure);
     122                     }
     123                 }
     124             }
     125         }
     126     }*/
}
