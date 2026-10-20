package com.yin.utils;

import com.yin.MyDemoApplication;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 存放一些公共的变量/常量
 */
public class ApplicationCommon {
    public static void main(String[] args) {
        String str = "/D:/cs/爬虫项目/java_crawler/target/java_crawler-1.0-SNAPSHOT.jar!/BOOT-INF/classes!";
        String substring = str.substring(0, str.lastIndexOf(".jar"));
        str = substring.substring(0, substring.lastIndexOf("/"));
        System.out.println(str);
    }

    //浏览器标识
    public final static String UserAgentKey = "User-Agent";
    public final static String UserAgentValue = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36";
    public final static String QQUserAgentValue = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1";
    //Cookie 标识
    public final static String Cookie = "Cookie";
    public final static String DouYinCookie = "douyin.com; xg_device_score=7.763208477783497; device_web_cpu_core=28; device_web_memory_size=8; architecture=amd64; UIFID_TEMP=3c3e9d4a635845249e00419877a3730e2149197a63ddb1d8525033ea2b3354c24984e63696b2c33e36e331d0c6a2da35fb30202bd700fc92c623494a4b96c091f4ef9d0d0cd0bcb1926caf4bd473ff77; fpk1=U2FsdGVkX1/CrjYvWprdTQohgWoAgD6K5k7AP7XhdRXBZ+9PRCXuA65M9Lq3xHluOXwQjQcC9e3KoAoHL032/w==; fpk2=f1f6b29a6cc1f79a0fea05b885aa33d0; bd_ticket_guard_client_web_domain=2; SEARCH_RESULT_LIST_TYPE=%22single%22; UIFID=3c3e9d4a635845249e00419877a3730e2149197a63ddb1d8525033ea2b3354c23f2e0bdf03b54fa6b70c9015492cc8212f732d6bcdcf1df35684166c826d9402b6d0de589591beb9d14c0b01d2dc7422219b73abcdc0bf6de9fb47e8e9525f83dc8b59adce163a81636bc40358e44c27ab2c8c8d46b77d6e88b62b33332d7b65365c553e639f63bd950cf59fa24e147e81ce02abfdf6bbed9c12898b6d6078ee; d_ticket=aab4e1ee07a22d8bdd288bb60e399a36e598f; store-region-src=uid; hevc_supported=true; passport_assist_user=CkFspNtbfVJnmHhPNhw-WUUalMczMeBoutwO8OtPa6ce5BTbtSut06wXh1ySjm-q2ivcwVrjaTGEQKKNT__nunj8cBpKCjxvmRnU0DbTdglQ7ElGWIsD-F-fWqsrQOt9vGyNBOp-iA0yX82EJH6KmofNpGEvZ-k4vNY0cc3Zza5CfkEQtJPkDRiJr9ZUIAEiAQMp5YBR; sso_uid_tt=817324b4dbf9e80236f1ab5c906f1042; sso_uid_tt_ss=817324b4dbf9e80236f1ab5c906f1042; toutiao_sso_user=78dbffa83bbddeaf1b1ea1160037bc68; toutiao_sso_user_ss=78dbffa83bbddeaf1b1ea1160037bc68; uid_tt=062d6e5e632079e65efbe79b5e00b590; uid_tt_ss=062d6e5e632079e65efbe79b5e00b590; sid_tt=e0d60aeede2090074beab75c1669f891; sessionid=e0d60aeede2090074beab75c1669f891; sessionid_ss=e0d60aeede2090074beab75c1669f891; is_staff_user=false; _bd_ticket_crypt_cookie=2e1f7dec578010e2b42604997760d4d7; SelfTabRedDotControl=%5B%5D; tt_webid=7466403678086350362; store-region=cn-zj; passport_csrf_token=a5a26cc559884b986ecff53d047797b5; passport_csrf_token_default=a5a26cc559884b986ecff53d047797b5; sid_ucp_sso_v1=1.0.0-KDQ5ZGViZDM2MDcyNTg0YTY4N2I5MzJlYTg0YTVjMGY5MDBmYTRiNTMKIQjdzfCilI2-BxDu4_i-BhjaFiAMMLuN0YwGOAZA9AdIBhoCbGYiIDc4ZGJmZmE4M2JiZGRlYWYxYjFlYTExNjAwMzdiYzY4; ssid_ucp_sso_v1=1.0.0-KDQ5ZGViZDM2MDcyNTg0YTY4N2I5MzJlYTg0YTVjMGY5MDBmYTRiNTMKIQjdzfCilI2-BxDu4_i-BhjaFiAMMLuN0YwGOAZA9AdIBhoCbGYiIDc4ZGJmZmE4M2JiZGRlYWYxYjFlYTExNjAwMzdiYzY4; s_v_web_id=verify_m99dx9p7_zmgLRcCi_2A90_4YpJ_8tAR_mzxsmHUCj29c; ttwid=1%7C9cqVbfFrgJALGkfnzzfC8luTdgi2PrJxDuwngqJPve0%7C1744438415%7Cb76231471a0ccda8c3c7de7301d2e65ec96055ff016c6db0025890b36a258f8e; dy_swidth=1707; dy_sheight=1067; stream_recommend_feed_params=%22%7B%5C%22cookie_enabled%5C%22%3Atrue%2C%5C%22screen_width%5C%22%3A1707%2C%5C%22screen_height%5C%22%3A1067%2C%5C%22browser_online%5C%22%3Atrue%2C%5C%22cpu_core_num%5C%22%3A28%2C%5C%22device_memory%5C%22%3A8%2C%5C%22downlink%5C%22%3A10%2C%5C%22effective_type%5C%22%3A%5C%224g%5C%22%2C%5C%22round_trip_time%5C%22%3A50%7D%22; __security_mc_1_s_sdk_crypt_sdk=7280e971-4a30-bde0; __security_mc_1_s_sdk_cert_key=613146a8-4c45-85a9; __security_mc_1_s_sdk_sign_data_key_web_protect=fbd9c5da-46e5-b596; publish_badge_show_info=%220%2C0%2C0%2C1746069278552%22; sid_guard=e0d60aeede2090074beab75c1669f891%7C1746069286%7C5184000%7CMon%2C+30-Jun-2025+03%3A14%3A46+GMT; sid_ucp_v1=1.0.0-KDc4MGUxODIyZWJhNTZjOTU1NGNkYzI0ZDE1OGVhYmNlZjRiZTg3ZDQKGwjdzfCilI2-BxCmzsvABhjaFiAMOAZA9AdIBBoCaGwiIGUwZDYwYWVlZGUyMDkwMDc0YmVhYjc1YzE2NjlmODkx; ssid_ucp_v1=1.0.0-KDc4MGUxODIyZWJhNTZjOTU1NGNkYzI0ZDE1OGVhYmNlZjRiZTg3ZDQKGwjdzfCilI2-BxCmzsvABhjaFiAMOAZA9AdIBBoCaGwiIGUwZDYwYWVlZGUyMDkwMDc0YmVhYjc1YzE2NjlmODkx; WallpaperGuide=%7B%22showTime%22%3A1746069296731%2C%22closeTime%22%3A0%2C%22showCount%22%3A1%2C%22cursor1%22%3A10%2C%22cursor2%22%3A2%7D; __ac_nonce=0681561b100c4af52c422; __ac_signature=_02B4Z6wo00f016JIWLgAAIDDSWAScpTsFWeiaFwAAICPa7; strategyABtestKey=%221746231730.698%22; biz_trace_id=b3b68980; FOLLOW_NUMBER_YELLOW_POINT_INFO=%22MS4wLjABAAAAgklT3wn0vFphTrtdzREEC3X6tpFlvL7yFTEAXgrXo3Il0DiAGuM6DeSAD39C_sm_%2F1746288000000%2F0%2F1746231730880%2F0%22; bd_ticket_guard_client_data=eyJiZC10aWNrZXQtZ3VhcmQtdmVyc2lvbiI6MiwiYmQtdGlja2V0LWd1YXJkLWl0ZXJhdGlvbi12ZXJzaW9uIjoxLCJiZC10aWNrZXQtZ3VhcmQtcmVlLXB1YmxpYy1rZXkiOiJCQjRXOWV2b2VWc21aVnJPLytyd25hWWtFMEtSSXp5bDFRRmN4UEtMeWRWTGlXVjIrZENmVDBPdHFMUFhmRUVUNnZ3d3gzNFI0MHpqcnlOdEtjQ2x2VU09IiwiYmQtdGlja2V0LWd1YXJkLXdlYi12ZXJzaW9uIjoyfQ%3D%3D; volume_info=%7B%22isUserMute%22%3Afalse%2C%22isMute%22%3Atrue%2C%22volume%22%3A0.5%7D; home_can_add_dy_2_desktop=%221%22; odin_tt=8b858a62bf3290ad6097ebcfa9d59bfb951f3448c39080ab34e8eff51f748448bda637ba29bd32f7b9c16824f7778e22; download_guide=%222%2F20250503%2F0%22; IsDouyinActive=false; passport_fe_beating_status=false";
    public final static String QQCookie = "qlogid=lA9pZJ; _qimei_uuid42=186120930201005bc8bb604603715dea7eb6814435; pac_uid=0_jY6tYRyfQ3eTx; _qimei_q36=; _qimei_h38=8f953589c8bb604603715dea0200000f818612; RK=M0fdxiCWx8; ptcz=3a4f51742a5e53101573bef9a2c9e04af689bc98a70283d29e94092b7c0e525b; eas_sid=a107k243Z5e3u5I5h817P7f0B6; pgv_pvid=5977036340; _clck=4qqwgv|1|foh|0; suid=user_0_jY6tYRyfQ3eTx; xsj-device-id=itt5TR6Xs3ah6T8msHyrrEWZpaJzEmXx; _qimei_fingerprint=f78f00d05dda99fc0f9e4e76336bb1b2; _qpsvr_localtk=0.09095740478766556; uin=o3353832004; skey=@hHuJcpQY6; p_uin=o3353832004; pt4_token=D9Fc-WKtDHHdI9TNyLISVFuhpyHXjXnT5LNv6DJCw80_; p_skey=TJVwL7YiGG8kDNQlOcyaOyXHUsABmtA3De7Z9DB5Ku0_; login_state=1";

    public final static String LocalPath = "D:\\cs\\文本文件信息";

    public static String getDataNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getDataNowSFM() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getDataNowTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String getDataFormat(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
    }

    public static String getDataFormat(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(time);
            return simpleDateFormat.format(parse);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static Date getDataFormat(Date time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(time));
            return parse;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getXmLocalPath() {
        URL resource = ApplicationCommon.class.getResource("");
        String XmLocalPath = null;
        try {
            if (resource != null) {
                XmLocalPath = isIniPath(resource);
            }
        } catch (UnsupportedEncodingException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return XmLocalPath;
    }

    /**
     * 判断是idea中运行还是jar中运行
     *
     * @return
     */
    public static String isIniPath(URL resource) throws UnsupportedEncodingException, URISyntaxException {
        String path = "";
        if (resource != null && resource.toString().startsWith("file:")) {
            System.out.println("在类文件中运行");
            path = resource.getPath();
            String decode = URLDecoder.decode(path, "UTF-8");
            path = decode.substring(0, decode.indexOf("classes") + 8);
        } else {
            System.out.println("在jar包中运行");
            String jarPath = new File(MyDemoApplication.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
            String substring = jarPath.substring(0, jarPath.lastIndexOf(".jar"));
            String substring1 = substring.substring(0, substring.lastIndexOf("\\")).replace("file:\\", "");
            path = substring1;
        }
        return path;
    }

    // 辅助方法：解析查询字符串并返回参数映射
    public static Map<String, String> parseQueryString(String queryString) {
        Map<String, String> map = new HashMap<>();
        if (queryString != null) {
            queryString = queryString.substring(queryString.indexOf("?") + 1);
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                if (idx > -1) {
                    String key = pair.substring(0, idx);
                    String value = pair.substring(idx + 1);
                    map.put(key, value);
                }
            }
        }
        return map;
    }

    /**
     * 过滤链接，获取http连接地址
     *
     * @param url
     * @return
     */
    public static String decodeHttpUrl(String url) {
        // 正则表达式匹配URL
//        String urlRegex = "https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=]+";
        String urlRegex = "https?://[\\w\\-._~:/?#\\[\\]@!$&'()*+,;=]+";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(url);
        // 查找并输出URL
        // 查找并输出URL
        if (matcher.find()) {
            url = matcher.group();
            return url;
        } else {
            System.out.println("未找到URL");
            return null;
        }
    }
}
