package com.yin.utils.excel.main.kangfu.jxc;

import cn.hutool.json.JSONUtil;
import com.yin.MyDemoApplication;
import com.yin.common.ApplicationUtils;
import com.yin.common.err.YException;
import com.yin.service.OracleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

/**
 *
 *@author yin
 *@date 2025-04-08
 *@Description
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class Main {
    @Autowired
    private OracleService hisOracleServiceImpl;

    @Test
    public void test() throws YException {
        String kssj = "2025-05-07";
        String jssj = "2025-05-12";
        long startTime = Instant.now().toEpochMilli();
        List<String> datesBetween = ApplicationUtils.getDatesBetween(kssj, jssj, "day");
        for (String date : datesBetween) {
            String jxcCSql = JxcSql.getJxcCSql(date);
            List list = hisOracleServiceImpl.selectAll(jxcCSql);
//            log.info("{}",jxcCSql);
            log.info("{}:差:{}", date, JSONUtil.toJsonStr(list));
        }
        System.out.println("用时:" + (Instant.now().toEpochMilli() - startTime) + "毫秒" + (Instant.now().toEpochMilli() - startTime) / 1000 + "秒");
    }
    @Test
    public void test2() throws YException {
        String sj ="2025-03-15";
        String jxcCSql = JxcSql.getJxcCSql(sj);
        List list = hisOracleServiceImpl.selectAll(jxcCSql);
        System.out.println(JSONUtil.toJsonStr(list));
    }
}
