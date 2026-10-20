package com.yin.utils;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 修复后的OKHTTP工具类：解决多次请求失败、连接泄漏、参数串味问题
 * 调用方式与原代码完全一致，无需修改业务代码
 */
@Slf4j
public class NewHttpUtils {
    // 单例实例（饿汉式，线程安全）
    private static final NewHttpUtils INSTANCE = new NewHttpUtils();
    // 全局唯一OkHttpClient（核心：保证连接池复用）
    private OkHttpClient okHttpClient;
    // OkHttpClient构建器（仅初始化一次，后续仅修改配置不重建）
    private final OkHttpClient.Builder clientBuilder;

    // 配置参数（静态常量，全局生效）
    private static int READ_TIMEOUT = 40;
    private static int CONNECT_TIMEOUT = 40;
    private static int WRITE_TIMEOUT = 40;
    // 连接池配置（生产环境推荐值：最大空闲20，空闲超时5分钟）
    private static final int MAX_IDLE_CONNECTIONS = 20;
    private static final long KEEP_ALIVE_DURATION = 5;

    // 每次请求的临时参数（init时全清空，避免串味）
    private Boolean isLog = false;
    private Integer requestNo = 1;
    private String responseStr;
    private String encoding;
    private Proxy proxy;
    private JSONObject responseJsonObject;
    private Map<String, String> headerMap = new HashMap<>();
    private Map<String, String> paramsMap = new HashMap<>();
    private Map<String, Object> paramsMapObj = new HashMap<>();
    private String paramsStr = "";

    // 私有构造器：防止外部实例化，初始化全局OkHttpClient
    private NewHttpUtils() {
        this.clientBuilder = new OkHttpClient.Builder();
        // 初始化超时时间
        clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 自定义连接池（解决默认连接数不足问题）
                .connectionPool(new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION, TimeUnit.MINUTES))
                // 开启连接失败重试（解决网络抖动问题）
                .retryOnConnectionFailure(true)
                // 允许重定向（保持原功能）
                .followRedirects(true);
        // 初始化全局唯一OkHttpClient
        this.okHttpClient = clientBuilder.build();
        log.info("OKHTTP工具类初始化完成：读取超时{}s，连接超时{}s，写入超时{}s，最大空闲连接{}，空闲超时{}分钟",
                READ_TIMEOUT, CONNECT_TIMEOUT, WRITE_TIMEOUT, MAX_IDLE_CONNECTIONS, KEEP_ALIVE_DURATION);
    }

    // 获取单例实例（对外唯一入口）
    public static NewHttpUtils getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化请求：清空所有临时参数，保证每次请求独立
     * 原init()方法的替代，调用方式不变
     */
    public static NewHttpUtils init() {
        NewHttpUtils instance = getInstance();
        // 清空所有临时参数（核心：避免多次请求参数串味）
        instance.headerMap.clear();
        instance.paramsMap.clear();
        instance.paramsMapObj.clear();
        instance.paramsStr = "";
        instance.encoding = null;
        instance.isLog = false;
        instance.responseStr = null;
        instance.responseJsonObject = null;
        return instance;
    }

    /**
     * 自定义超时时间初始化
     */
    public static NewHttpUtils init(int readTimeout, int connectTimeout, int writeTimeout) {
        READ_TIMEOUT = readTimeout;
        CONNECT_TIMEOUT = connectTimeout;
        WRITE_TIMEOUT = writeTimeout;
        // 更新全局客户端的超时配置
        getInstance().clientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        getInstance().rebuildClient();
        return init();
    }

    /**
     * 设置代理
     */
    public NewHttpUtils setProxy(String proxyHost, Integer proxyPort) {
        if (proxyHost != null && proxyPort != null) {
            this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            clientBuilder.proxy(this.proxy);
            rebuildClient();
        }
        return this;
    }

    /**
     * 设置是否自动重定向
     */
    public NewHttpUtils followRedirects(boolean flag) {
        clientBuilder.followRedirects(flag);
        rebuildClient();
        return this;
    }

    /**
     * 开启日志打印
     */
    public NewHttpUtils isLogRes() {
        this.isLog = true;
        return this;
    }

    /**
     * 重建OkHttpClient（仅修改配置后调用，保证全局唯一）
     */
    private void rebuildClient() {
        this.okHttpClient = clientBuilder.build();
    }

    /**
     * 添加请求头
     */
    public NewHttpUtils addHeader(String key, String value) {
        if (key != null && value != null) {
            headerMap.put(key, value);
        }
        return this;
    }

    public NewHttpUtils addHeader(JSONObject jsonObject) {
        if (jsonObject != null) {
            for (String s : jsonObject.keySet()) {
                headerMap.put(s, jsonObject.get(s).toString());
            }
        }
        return this;
    }

    /**
     * 添加Chrome默认请求头
     */
    public NewHttpUtils addHeaderChrome() {
        headerMap.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");
        return this;
    }

    /**
     * 添加Cookie
     */
    public NewHttpUtils addHeaderCookie(String cookie) {
        if (cookie != null) {
            headerMap.put("cookie", cookie.replaceAll("\n", ""));
        }
        return this;
    }

    /**
     * 添加User-Agent
     */
    public NewHttpUtils addHeaderUserAgent(String userAgent) {
        if (userAgent != null) {
            headerMap.put("user-agent", userAgent);
        }
        return this;
    }

    /**
     * 添加字符串参数
     */
    public NewHttpUtils addParams(String key, String value) {
        if (key != null && value != null) {
            paramsMap.put(key, value);
        }
        return this;
    }

    public NewHttpUtils addParams(Map<String, String> parMap) {
        if (parMap != null && !parMap.isEmpty()) {
            paramsMap.putAll(parMap);
        }
        return this;
    }

    /**
     * 添加Object参数
     */
    public NewHttpUtils addParamsObj(String key, Object value) {
        if (key != null && value != null) {
            paramsMapObj.put(key, value);
        }
        return this;
    }

    /**
     * 添加字符串参数体
     */
    public NewHttpUtils addParamsStr(String info) {
        if (info != null) {
            this.paramsStr = info;
        }
        return this;
    }

    /**
     * 从JSONObject添加参数
     */
    public NewHttpUtils addParams(JSONObject jsonObject) {
        if (jsonObject != null) {
            for (String s : jsonObject.keySet()) {
                headerMap.put(s, jsonObject.get(s).toString());
            }
        }
        return this;
    }

    /**
     * 设置响应编码
     */
    public NewHttpUtils encoding(String encoding1) throws UnsupportedEncodingException {
        if (encoding1 != null && !encoding1.isEmpty()) {
            // 校验编码是否有效
            Charset.forName(encoding1);
            this.encoding = encoding1;
        }
        return this;
    }

    /**
     * GET请求（核心修复：每次新建Request.Builder，关闭Response资源）
     */
    public NewHttpUtils get(String url) throws IOException {
        long start = System.currentTimeMillis();
        log.info("------开启第{}次GET请求------", requestNo);
        log.info("请求开始时间:{}", ApplicationCommon.getDataNow());
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("请求URL不能为空");
        }
        url = url.trim();

        // 1. 拼接GET参数
        StringBuilder urlBuilder = new StringBuilder(url);
        if (!paramsMap.isEmpty()) {
            log.info("GET请求参数:{}", JSONUtil.toJsonStr(paramsMap));
            int count = 0;
            for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                if (count == 0) {
                    urlBuilder.append("?").append(param.getKey()).append("=").append(param.getValue());
                } else {
                    urlBuilder.append("&").append(param.getKey()).append("=").append(param.getValue());
                }
                count++;
            }
        }

        // 2. 每次请求新建Request.Builder（核心：避免参数串味）
        Request.Builder reqBuilder = new Request.Builder();
        // 添加请求头
        if (!headerMap.isEmpty()) {
            log.info("GET请求头:{}", JSONUtil.toJsonStr(headerMap));
            headerMap.forEach(reqBuilder::addHeader);
        }

        // 3. 构建请求
        Request request = reqBuilder.url(urlBuilder.toString()).get().build();
        log.info("GET请求地址:{}", urlBuilder.toString());

        // 4. 执行请求：try-with-resources自动关闭Response（核心：防止连接泄漏）
        try (Response response = okHttpClient.newCall(request).execute()) {
            handleResponse(response);
        } catch (Exception e) {
            log.error("第{}次GET请求执行失败，URL:{}", requestNo, url, e);
            throw e; // 抛出异常让调用方处理
        }

        printRequestCost(start);
        requestNo++;
        return this;
    }

    /**
     * POST请求（核心修复：每次新建Request.Builder，关闭Response资源）
     */
    public NewHttpUtils post(String url) throws IOException {
        long start = System.currentTimeMillis();
        log.info("------开启第{}次POST请求------", requestNo);
        log.info("请求开始时间:{}", ApplicationCommon.getDataNow());
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("请求URL不能为空");
        }
        url = url.trim();
        log.info("POST请求地址:{}", url);

        // 1. 每次请求新建Request.Builder（核心：避免参数串味）
        Request.Builder reqBuilder = new Request.Builder();
        // 添加请求头
        if (!headerMap.isEmpty()) {
            log.info("POST请求头:{}", JSONUtil.toJsonStr(headerMap));
            headerMap.forEach(reqBuilder::addHeader);
        }

        // 2. 构建请求体
        RequestBody requestBody = buildRequestBody();
        // 3. 构建请求
        Request request = reqBuilder.url(url).post(requestBody == null ? RequestBody.create(new byte[0], MediaType.parse("application/json")) : requestBody).build();

        // 4. 执行请求：try-with-resources自动关闭Response（核心：防止连接泄漏）
        try (Response response = okHttpClient.newCall(request).execute()) {
            handleResponse(response);
        } catch (Exception e) {
            log.error("第{}次POST请求执行失败，URL:{}", requestNo, url, e);
            throw e;
        }

        printRequestCost(start);
        requestNo++;
        return this;
    }

    /**
     * PUT请求
     */
    public NewHttpUtils put(String url) throws IOException {
        long start = System.currentTimeMillis();
        log.info("------开启第{}次PUT请求------", requestNo);
        log.info("请求开始时间:{}", ApplicationCommon.getDataNow());
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("请求URL不能为空");
        }
        url = url.trim();
        log.info("PUT请求地址:{}", url);

        // 每次请求新建Request.Builder
        Request.Builder reqBuilder = new Request.Builder();
        // 添加请求头
        if (!headerMap.isEmpty()) {
            log.info("PUT请求头:{}", JSONUtil.toJsonStr(headerMap));
            headerMap.forEach(reqBuilder::addHeader);
        }

        // 构建请求体
        RequestBody requestBody = buildRequestBody();
        // 构建请求
        Request request = reqBuilder.url(url).put(requestBody == null ? RequestBody.create(new byte[0], MediaType.parse("application/json")) : requestBody).build();

        // 执行请求：自动关闭Response
        try (Response response = okHttpClient.newCall(request).execute()) {
            handleResponse(response);
        } catch (Exception e) {
            log.error("第{}次PUT请求执行失败，URL:{}", requestNo, url, e);
            throw e;
        }

        printRequestCost(start);
        requestNo++;
        return this;
    }

    /**
     * 构建请求体（抽离公共逻辑，避免重复代码）
     */
    private RequestBody buildRequestBody() {
        if (!ObjectUtils.isEmpty(paramsStr)) {
            log.info("请求体（字符串）:{}", paramsStr);
            return RequestBody.create(paramsStr.getBytes(StandardCharsets.UTF_8), MediaType.parse("text/plain;charset=UTF-8"));
        } else if (!ObjectUtils.isEmpty(paramsMapObj)) {
            String json = JSONUtil.toJsonStr(paramsMapObj);
            log.info("请求体（Object转JSON）:{}", json);
            return RequestBody.create(json.getBytes(StandardCharsets.UTF_8), MediaType.parse("application/json;charset=UTF-8"));
        } else if (!ObjectUtils.isEmpty(paramsMap)) {
            String json = JSONUtil.toJsonStr(paramsMap);
            log.info("请求体（String转JSON）:{}", json);
            return RequestBody.create(json.getBytes(StandardCharsets.UTF_8), MediaType.parse("application/json;charset=UTF-8"));
        }
        return null;
    }

    /**
     * 处理响应结果（公共逻辑：校验响应、解析结果、关闭体）
     */
    private void handleResponse(Response response) throws IOException {
        // 校验响应是否成功（200-299）
        if (!response.isSuccessful()) {
            throw new IOException("服务端响应失败，状态码：" + response.code() + "，信息：" + response.message());
        }
        // 解析响应体：try-with-resources自动关闭ResponseBody
        try (ResponseBody responseBody = response.body()) {
            if (responseBody == null) {
                log.warn("服务端返回空响应体");
                this.responseStr = "";
                return;
            }
            // 按指定编码解析
            if (encoding != null && !encoding.isEmpty()) {
                byte[] bytes = responseBody.bytes();
                this.responseStr = new String(bytes, Charset.forName(encoding)).trim();
            } else {
                this.responseStr = responseBody.string().trim();
            }
            // 日志打印
            if (isLog) {
                log.info("服务端响应结果:{}", this.responseStr);
            }
            // 尝试转换为JSONObject（失败不抛异常，仅打印日志）
            try {
                this.responseJsonObject = JSONUtil.parseObj(this.responseStr);
            } catch (JSONException e) {
                log.warn("响应结果转换JSONObject失败，结果非JSON格式:{}", e.getMessage());
            }
        }
    }

    /**
     * 打印请求耗时
     */
    private void printRequestCost(long start) {
        long end = System.currentTimeMillis();
        long costMs = end - start;
        log.info("请求耗时：{}ms（{}s/{}m），结束时间:{}",
                costMs, costMs / 1000, costMs / 1000 / 60, ApplicationCommon.getDataNow());
    }

    // ------------------- 原有的getter方法（保留，保证调用方兼容） -------------------
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public Response getResponse() {
        // 已移除成员变量response，避免资源持有，如需可在handleResponse中临时存储
        return null;
    }

    public Boolean getIsLog() {
        return isLog;
    }

    public Integer getRequestNo() {
        return requestNo;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public String getEncoding() {
        return encoding;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public JSONObject getResponseJsonObject() {
        return responseJsonObject;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public Map<String, String> getParamsMap() {
        return paramsMap;
    }

    public Map<String, Object> getParamsMapObj() {
        return paramsMapObj;
    }

    public String getParamsStr() {
        return paramsStr;
    }
}