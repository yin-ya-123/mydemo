package com.yin.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 控制台打印工具类
 * 使用 System.out.println 打印，支持 {} 占位符参数替换
 * 支持将日志写入文件
 */
public class ConsoleLog {

    /** 日志缓冲区，存储带日期的日志 */
    private static final List<String> logBuffer = new ArrayList<>();

    /** 是否启用文件记录 */
    private static boolean fileLoggingEnabled = true;

    /** 日期格式 */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat FILE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    /**
     * 打印信息
     * @param message 消息模板，使用 {} 作为占位符
     * @param args 参数列表
     */
    public static void info(String message, Object... args) {
        String formatted = format(message, args);
        System.out.println(formatted);
        if (fileLoggingEnabled) {
            logBuffer.add("[INFO] " + getCurrentDateTime() + " " + formatted);
        }
    }

    /**
     * 打印错误信息
     * @param message 消息模板，使用 {} 作为占位符
     * @param args 参数列表
     */
    public static void error(String message, Object... args) {
        String formatted = format(message, args);
        System.err.println(formatted);
        if (fileLoggingEnabled) {
            logBuffer.add("[ERROR] " + getCurrentDateTime() + " " + formatted);
        }
    }

    /**
     * 打印警告信息
     * @param message 消息模板，使用 {} 作为占位符
     * @param args 参数列表
     */
    public static void warn(String message, Object... args) {
        String formatted = format(message, args);
        System.out.println("[WARN] " + formatted);
        if (fileLoggingEnabled) {
            logBuffer.add("[WARN] " + getCurrentDateTime() + " " + formatted);
        }
    }

    /**
     * 打印调试信息
     * @param message 消息模板，使用 {} 作为占位符
     * @param args 参数列表
     */
    public static void debug(String message, Object... args) {
        String formatted = format(message, args);
        System.out.println("[DEBUG] " + formatted);
        if (fileLoggingEnabled) {
            logBuffer.add("[DEBUG] " + getCurrentDateTime() + " " + formatted);
        }
    }

    /**
     * 格式化消息，替换 {} 占位符
     * @param message 消息模板
     * @param args 参数列表
     * @return 格式化后的消息
     */
    private static String format(String message, Object... args) {
        if (message == null) {
            return "";
        }
        if (args == null || args.length == 0) {
            return message;
        }

        StringBuilder sb = new StringBuilder();
        int argIndex = 0;
        int messageLength = message.length();

        for (int i = 0; i < messageLength; i++) {
            char currentChar = message.charAt(i);
            
            if (currentChar == '{' && i + 1 < messageLength && message.charAt(i + 1) == '}') {
                if (argIndex < args.length) {
                    sb.append(args[argIndex] != null ? args[argIndex].toString() : "null");
                    argIndex++;
                } else {
                    sb.append("{}");
                }
                i++;
            } else {
                sb.append(currentChar);
            }
        }
        return sb.toString();
    }

    /**
     * 获取当前日期时间字符串
     * @return 格式化的日期时间
     */
    private static String getCurrentDateTime() {
        return DATE_FORMAT.format(new Date());
    }

    /**
     * 获取当前日期字符串（用于文件名）
     * @return 格式化的日期
     */
    private static String getCurrentDate() {
        return FILE_DATE_FORMAT.format(new Date());
    }

    /**
     * 设置是否启用文件记录
     * @param enabled 是否启用
     */
    public static void setFileLoggingEnabled(boolean enabled) {
        fileLoggingEnabled = enabled;
    }

    /**
     * 清空日志缓冲区
     */
    public static void clearBuffer() {
        logBuffer.clear();
    }

    /**
     * 将日志写入文件
     * @param directoryName 上级目录名称（相对于 resources 目录）
     * @param fileNamePrefix 文件名前缀（会自动加上日期）
     * @return 是否写入成功
     */
    public static boolean writeToFile(String directoryName, String fileNamePrefix) {
        return writeToFile(directoryName, fileNamePrefix, false);
    }

    /**
     * 将日志写入文件
     * @param directoryName 上级目录名称（相对于 resources 目录）
     * @param fileNamePrefix 文件名前缀（会自动加上日期）
     * @param clearBufferAfterWrite 写入后是否清空缓冲区
     * @return 是否写入成功
     */
    public static boolean writeToFile(String directoryName, String fileNamePrefix, boolean clearBufferAfterWrite) {
        if (logBuffer.isEmpty()) {
            System.out.println("日志缓冲区为空，无需写入文件");
            return true;
        }

        // 获取 resources 目录路径
        String resourcesPath = getResourcesPath();
        if (resourcesPath == null) {
            System.err.println("无法获取 resources 目录路径");
            return false;
        }

        // 构建完整目录路径
        File directory = new File(resourcesPath, directoryName);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                System.err.println("创建目录失败: " + directory.getAbsolutePath());
                return false;
            }
        }

        // 构建文件名：日期 + 前缀 + .log，如果重名则添加数字后缀
        String baseFileName = getCurrentDate() + "_" + fileNamePrefix;
        String fileName = baseFileName + ".log";
        File logFile = new File(directory, fileName);
        int counter = 1;
        
        // 如果文件已存在，添加数字后缀
        while (logFile.exists()) {
            fileName = baseFileName + counter + ".log";
            logFile = new File(directory, fileName);
            counter++;
        }

        // 追加写入文件
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(logFile, true), StandardCharsets.UTF_8))) {
            for (String log : logBuffer) {
                writer.write(log);
                writer.newLine();
            }
            writer.flush();
            System.out.println("日志写入成功: " + logFile.getAbsolutePath());
            
            if (clearBufferAfterWrite) {
                clearBuffer();
            }
            return true;
        } catch (IOException e) {
            System.err.println("写入日志文件失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 resources 目录路径
     * @return resources 目录路径，如果获取失败返回 null
     */
    private static String getResourcesPath() {
        // 尝试从 classpath 获取
        String classPath = ConsoleLog.class.getClassLoader().getResource("").getPath();
        if (classPath != null) {
            // 处理路径中的特殊字符
            classPath = classPath.replace("\\", "/");
            if (classPath.startsWith("/")) {
                classPath = classPath.substring(1);
            }
            
            // 如果在 target/classes 中，返回 src/main/resources
            if (classPath.contains("/target/classes/")) {
                return classPath.replace("/target/classes/", "/src/main/resources/");
            }
            
            // 如果在 classes 中，返回 resources
            if (classPath.endsWith("/classes/")) {
                return classPath.substring(0, classPath.length() - 9) + "resources/";
            }
            
            // 其他情况，尝试返回当前目录下的 resources
            File currentDir = new File(System.getProperty("user.dir"));
            File resourcesDir = new File(currentDir, "src/main/resources");
            if (resourcesDir.exists()) {
                return resourcesDir.getAbsolutePath();
            }
            
            return classPath;
        }
        
        // 如果 classpath 获取失败，尝试使用 user.dir
        File currentDir = new File(System.getProperty("user.dir"));
        File resourcesDir = new File(currentDir, "src/main/resources");
        if (resourcesDir.exists()) {
            return resourcesDir.getAbsolutePath();
        }
        
        return null;
    }
}
