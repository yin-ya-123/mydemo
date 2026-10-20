package com.yin.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 网络请求工具类
 */
public class HttpUtils {
    /**
     * volatile是一个特征修饰符(type specifier)。它的作用是确保本条指令不会因编译器的优化而省略，且要求每次直接读值。具体来说，volatile修饰的变量是指这变量可能会被意想不到地改变，这样，编译器就不会去假设这个变量的值了。
     */
    private volatile static HttpUtils httpUtils;
    private OkHttpClient okHttpClient;
    private static final int READ_TIMEOUT = 40;
    private static final int CONNECT_TIMEOUT = 40;
    private static final int WRITE_TIMEOUT = 40;
    private HttpUtils() {
        /**
         * TimeUnit.SECONDS是Java中的一个枚举类型，用于表示秒
         * READ_TIMEOUT 读取超时时间
         * CONNECT_TIMEOUT 连接超时时间
         * WRITE_TIMEOUT 写入超时时间
         * 最后创建一个 okHttpClient=builder.build();对象来进行请求操作
         */
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient=builder.build();
    }

    /**
     * 获取工具类对象
     * 这样写的话，那么私有的构造函数也会进行执行
     * @return
     */
    public static HttpUtils getHttpUtils(){
        if(httpUtils==null){
            /**
             * 通过将该对象作为锁，可以确保同一时刻只有一个线程可以进入同步块，从而避免多个线程同时访问共享资源而导致的竞态条件和数据不一致等问题。
             */
            synchronized(HttpUtils.class){
                if(httpUtils==null){
                    httpUtils=new HttpUtils();
                }
            }
        }
        return httpUtils;
    }
    public Response httpGet(String url) throws IOException {
        /**
         * 封装构建一个okhttp3的Request
         */
        Request request=new Request.Builder().url(url).get().build();
        //获取响应值
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }
    public Response httpGet(String url,Map<String,String> headerMap) throws IOException {
        /**
         * 封装构建一个okhttp3的Request
         */
        Request.Builder url1 = new Request.Builder().url(url);
        /**
         * 添加头文件
         */
        if(!ObjectUtils.isEmpty(headerMap)){
            for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
                url1.header(stringStringEntry.getKey(),stringStringEntry.getValue());
            }
        }
        Request request = url1.get().build();
        //获取响应值
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }
    public Response httpPost(String url, Map<String,String> headerMap,Map<String,String> paramsMap) throws IOException {
        Request.Builder url1 = new Request.Builder().url(url);
        //请求头
        if(!ObjectUtils.isEmpty(headerMap)){
            for (Map.Entry<String, String> stringStringEntry : headerMap.entrySet()) {
                url1.header(stringStringEntry.getKey(),stringStringEntry.getValue());
//                System.out.println(stringStringEntry.getKey());
//                System.out.println(stringStringEntry.getValue());
            }
//            System.out.println("请求头:"+ JSON.toJSONString(headerMap));
        }
        //请求体
        RequestBody requestBody =null;
        if(!ObjectUtils.isEmpty(paramsMap)){
            requestBody=RequestBody.create(MediaType.parse("application/json"), JSONObject.toJSONString(paramsMap));
//            System.out.println(JSONObject.toJSONString(paramsMap));
//            System.out.println("参数:"+ JSON.toJSONString(paramsMap));
        }
        Request request = url1.post(requestBody).build();
        Response execute = okHttpClient.newCall(request).execute();
        return execute;
    }
}
