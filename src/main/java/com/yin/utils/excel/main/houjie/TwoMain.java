package com.yin.utils.excel.main.houjie;

import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.service.impl.DgHisOracleServiceImpl;
import com.yin.utils.ConvertAllUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class TwoMain {
    @Resource
    private DgHisOracleServiceImpl dgHisOracleServiceImpl;
    @Test
    public void t90211(){
        List list = dgHisOracleServiceImpl.selectAll(TwoSql.get90211());
//        log.info("90211:{}",list);
        for (Object o : list) {
            JSONObject jsonObject = ConvertAllUtils.convertJson(o);
            List list1 = dgHisOracleServiceImpl.selectAll(TwoSql.MI_FIXMEDINS_INFO(jsonObject.get("人员编号").toString()));
            if (!list1.isEmpty()){
                log.info("不为空:{}",jsonObject.get("人员编号"));
                log.info("MI_FIXMEDINS_INFO:{}",JSONObject.toJSONString(list1));
            }else {
                log.info("为空:{}",jsonObject.get("人员编号"));
            }
        }
    }
}
