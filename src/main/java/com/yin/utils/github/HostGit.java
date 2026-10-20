package com.yin.utils.github;

import com.yin.utils.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 写入host文件git地址，从而让gut可以访问
 */
public class HostGit {
    public static void main(String[] args) throws IOException {
//        String baseUrl = "https://gitee.com/xiaoyutou_647/hosts/raw/master/next-hosts";
//        Response response = HttpUtils.getHttpUtils().httpGet(baseUrl);
//        String string = response.body().string();
        String path = "D:\\cs\\ssxjg\\myDemo\\src\\main\\resources\\host.txt";
        String path1 = "D:\\cs\\ssxjg\\myDemo\\src\\main\\resources\\hosts.txt";
//        FileUtils.writeFile(path,string,false);
//        String info = FileUtils.readFile(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line = bufferedReader.readLine();
        StringBuilder stringBuilder = new StringBuilder();
        while (line != null) {
            if (!line.contains("#")) {
                if (!line.trim().isEmpty()) {
                    stringBuilder.append(line).append("\n");
                }
            }
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
//        FileUtils.writeFile(path1,stringBuilder.toString(),false);
    }
}
