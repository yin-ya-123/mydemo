package com.yin.utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件操作工具类，封装常用的文件读取、写入、路径处理及 zip 压缩操作。
 *
 * @author YIN
 * @since 2024-01-17
 */
@Slf4j
public class FileUtils {

    /**
     * 递归压缩 {@link #toZip(File)} / {@link #toZip(File, String)} 时复用的 zip 输出流，由 {@link #compress(File, String, String)} 初始化。
     */
    private static ZipOutputStream zipOutputStream = null;

    /**
     * 使用 commons-io 按 GB2312 编码逐行读取文件内容
     *
     * @param path 文件路径
     * @return 文件内容字符串；读取失败时返回包含异常信息的提示
     */
    public static String readFile(String path) {
//        使用commons-io的工具类读取
        try {
            //        文件路径
//            String fileName = MyFileUtils.class.getResource("新建文本文档.txt").getFile();
//            解决文件名字为中文的导致不能读的问题
            String fileName = path;
            LineIterator lineIterator = org.apache.commons.io.FileUtils.lineIterator(new File(fileName), "GB2312");
            StringBuffer str = new StringBuffer();
            while (lineIterator.hasNext()) {
                str.append(lineIterator.next());
            }
            return str.toString();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return "地址错误，读取失败" + new RuntimeException(e);
        }
    }

    /**
     * 使用 commons-io 按指定编码逐行读取文件内容，行之间补回换行符
     *
     * @param path     文件路径
     * @param encoding 文件编码（如 UTF-8、GBK 等）
     * @return 文件内容字符串；读取失败时返回包含异常信息的提示
     */
    @NotNull
    public static String readFile(String path, String encoding) {
//        使用commons-io的工具类读取
        try {
            //        文件路径
//            String fileName = MyFileUtils.class.getResource("新建文本文档.txt").getFile();
//            解决文件名字为中文的导致不能读的问题
            String fileName = path;
            LineIterator lineIterator = org.apache.commons.io.FileUtils.lineIterator(new File(fileName), encoding);
            StringBuffer str = new StringBuffer();
            while (lineIterator.hasNext()) {
                str.append(lineIterator.next());
                str.append("\n");
            }
            lineIterator.close();
            return str.toString();
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return "地址错误，读取失败" + new RuntimeException(e);
        }
    }

    /**
     * 创建文件，必要时会先创建父目录
     *
     * @param path 文件或目录路径
     * @return 是否创建成功
     */
    public static boolean createFile(String path) {
        return createFile(new File(path));
    }

    /**
     * 批量创建文件，必要时会先创建父目录
     *
     * @param paths 多个文件或目录路径
     * @return 是否全部创建成功（任一失败返回 false）
     */
    public static boolean createFile(String... paths) {
        boolean flag = true;
        for (String path : paths) {
            flag = createFile(new File(path));
        }
        return flag;

    }

    /**
     * 创建文件或目录，必要时会先创建父目录
     *
     * @param file 待创建的文件或目录
     * @return 是否创建成功
     * @throws RuntimeException 当创建过程中发生 IO 异常时抛出
     */
    public static boolean createFile(File file) {
        boolean flag = true;
        String absolutePath = file.getAbsolutePath();
        int start = absolutePath.lastIndexOf("\\");
        String substring = absolutePath.substring(start + 1);
        //        获取文件路径
        String localPath = "";
        if (substring.contains(".")) {
            localPath = absolutePath.substring(0, start + 1);
        } else {
            localPath = absolutePath;
        }
        File file1 = new File(localPath);
        try {
            //判断路径是否存在
            if (!file1.exists()) {
                flag = file1.mkdirs();
            }
            //判断文件是否存在
            if (substring.contains(".")) {
                if (!file.isFile()) {
                    flag = file.createNewFile();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("当前路径不存在已创建:{}", absolutePath);
        return flag;
    }

    /**
     * 获取路径中不包含文件名的目录部分
     *
     * @param path 原始路径
     * @return 目录路径（不含文件名）
     */
    public static String getFilePath(String path) {
        int i = path.lastIndexOf("\\");
        return path.substring(0, i);
    }

    /**
     * 获取文件名（不含后缀）
     *
     * @param path 文件路径
     * @return 不含后缀的文件名
     */
    public static String getFileName(String path) {
        int start = path.lastIndexOf("\\");
        int end = path.lastIndexOf(".");
        return path.substring(start + 1, end);
    }

    /**
     * 获取文件后缀（不含点号）
     *
     * @param path 文件路径
     * @return 文件后缀
     */
    public static String getFileSuffix(String path) {
        int i = path.lastIndexOf(".");
        return path.substring(i + 1);
    }

    /**
     * 获取不含后缀的文件路径
     *
     * @param path 文件路径
     * @return 去除后缀后的路径
     */
    public static String getFileNotSuffix(String path) {
        int i = path.lastIndexOf(".");
        return path.substring(0, i);
    }

    /**
     * 通过是否包含后缀判断是否为文件路径
     *
     * @param path 路径
     * @return true 表示是文件路径，false 表示不是
     */
    public static boolean isFile(String path) {
        int i = path.lastIndexOf(".");
        return i != -1;
    }

    /**
     * 覆盖写入文件（默认不追加）
     *
     * @param path    文件地址
     * @param content 文件内容
     * @throws IOException 当写入过程中发生 IO 异常时抛出
     */
    public static void writeFile(String path, String content) throws IOException {
        writeFile(path, content, false);
    }

    /**
     * 写入文件
     *
     * @param path    文件地址
     * @param content 文件内容
     * @param flag    是否追加（true 追加，false 覆盖）
     * @throws IOException 当写入过程中发生 IO 异常时抛出
     */
    public static void writeFile(String path, String content, boolean flag) throws IOException {
        createFile(path);
        FileWriter fileWriter = new FileWriter(path, flag);
        fileWriter.write(content);
        fileWriter.close();
        if (flag) {
            System.out.println("内容已成功追加到文件中。");
        } else {
            System.out.println("内容覆盖写入成功。");
        }
    }

    /**
     * 将单个文件压缩到指定 zip 路径
     *
     * @param filePath 待压缩的源文件
     * @param zipPath  生成的压缩包路径
     * @throws IOException 当压缩过程中发生 IO 异常时抛出
     */
    public static void createFileZip(File filePath, String zipPath) throws IOException {
        //进行路径创建
        if (!filePath.isFile()) {
            log.error("源文件路径不存在,压缩失败:{}", filePath.getAbsolutePath());
            return;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(zipPath);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        // 创建ZipEntry对象并设置文件名
        ZipEntry zipEntry = new ZipEntry(filePath.getName());
        zipOutputStream.putNextEntry(zipEntry);
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            zipOutputStream.write(buffer, 0, bytesRead);
        }
        zipOutputStream.closeEntry();
        fileOutputStream.close();
        fileInputStream.close();
        log.info("{},当前文件已写入到压缩包中:{}", filePath.getAbsolutePath(), zipPath);
    }

    /**
     * 将单个文件压缩为同名 zip，压缩包与源文件同目录、后缀改为 .zip
     *
     * @param filePath 待压缩的源文件
     * @throws IOException 当压缩过程中发生 IO 异常时抛出
     */
    public static void createFileZip(File filePath) throws IOException {
        //进行路径创建
        if (!filePath.isFile()) {
            log.error("源文件路径不存在,压缩失败:{}", filePath.getAbsolutePath());
            return;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(getFileNotSuffix(filePath.getAbsolutePath()) + ".zip");
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        // 创建ZipEntry对象并设置文件名
        ZipEntry zipEntry = new ZipEntry(filePath.getName());
        zipOutputStream.putNextEntry(zipEntry);
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            zipOutputStream.write(buffer, 0, bytesRead);
        }
        zipOutputStream.closeEntry();
        fileOutputStream.close();
        fileInputStream.close();
        log.info("{},当前文件已写入到压缩包中:{}", filePath.getAbsolutePath(), getFileNotSuffix(filePath.getAbsolutePath()) + ".zip");
    }

    /**
     * 将单个文件压缩写入已打开的 zip 输出流（递归压缩场景使用）
     *
     * @param name     压缩包内的文件名称
     * @param filePath 待压缩的源文件
     * @throws IOException 当压缩过程中发生 IO 异常时抛出
     */
    private static void createFileZip(String name, File filePath) throws IOException {
        //进行路径创建
        if (!filePath.isFile()) {
            log.error("源文件路径不存在,压缩失败:{}", filePath.getAbsolutePath());
            return;
        }
        // 创建ZipEntry对象并设置文件名
        ZipEntry zipEntry = new ZipEntry(name.trim());
        zipOutputStream.putNextEntry(zipEntry);
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            zipOutputStream.write(buffer, 0, bytesRead);
        }
        fileInputStream.close();
        zipOutputStream.closeEntry();
        log.info("filePath:{},zipFileName:{}，当前文件已写入到压缩包中", filePath.getAbsolutePath(), name);

    }

    /**
     * 将目录递归压缩到指定 zip 路径
     *
     * @param sourcePath 源目录
     * @param zipPath    压缩包保存路径
     */
    public static void toZip(File sourcePath, String zipPath) {
        try {
            compress(sourcePath, zipPath, "");
            log.info("当前目录写入完毕:{}", zipPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将目录递归压缩，压缩包保存在源目录下，文件名以当前时间戳开头
     *
     * @param sourcePath 源目录
     */
    public static void toZip(File sourcePath) {
        try {
            String zipPath = System.currentTimeMillis() + "临时压缩包.zip";
            compress(sourcePath, sourcePath.getAbsolutePath() + File.separator + zipPath, "");
            log.info("当前目录写入完毕:{}", zipPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourcePath     文件夹路径
     * @param zipPath        zip 保存路径
     * @param fileFatherName 递归时的父目录名称（用于保留压缩包内的目录结构）
     * @throws IOException 当压缩过程中发生 IO 异常时抛出
     */
    private static void compress(File sourcePath, String zipPath, String fileFatherName) throws IOException {
        if (!sourcePath.exists()) {
            log.error("路径不存在，操作失败:{}", sourcePath);
            return;
        }
        File[] files = sourcePath.listFiles();
        assert files != null;
        if (files.length == 0) {
            log.error("路径不存在文件，操作失败:{}", sourcePath);
            return;
        }
//        第一次为空的时候，赋值写入zip流
        if (zipOutputStream == null) {
            //创建流
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipPath));
        }
        boolean flag = createFile(zipPath);
        if (flag) {
            for (File file : files) {
                //判断是否是目录还是文件
                if (file.isFile()) {
                    //剔除那些后缀
                    String fileSuffix = getFileSuffix(file.getAbsolutePath());
                    if (fileSuffix.equals("zip") || fileSuffix.equals("ZIP")) {
                        continue;
                    }
                    createFileZip(fileFatherName + file.getName(), file);
                } else {
                    compress(file, zipPath, file.getName() + "/");
                }
            }
            zipOutputStream.closeEntry();
        } else {
            throw new IOException("文件创建失败");
        }
    }
}
