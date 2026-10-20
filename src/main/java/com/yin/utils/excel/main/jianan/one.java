package com.yin.utils.excel.main.jianan;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.MyDemoApplication;
import com.yin.service.impl.JnOracleServiceImpl;
import com.yin.service.impl.OracleServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class one {
    @Resource
    private JnOracleServiceImpl jnOracleServiceImpl;
    @Resource
    private OracleServiceImpl2 oracleServiceImpl2;
    @Test
    public void one() {
        String str ="[\n" +
                "  {\n" +
                "    value: \"1\",\n" +
                "    label: \"正常存储\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"2\",\n" +
                "    label: \"常温存储\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"3\",\n" +
                "    label: \"低温存储\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"4\",\n" +
                "    label: \"低温冷藏\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"5\",\n" +
                "    label: \"防冻\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"6\",\n" +
                "    label: \"低温冷藏防冻\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"7\",\n" +
                "    label: \"阴凉干燥处\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"8\",\n" +
                "    label: \"密封\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"9\",\n" +
                "    label: \"密闭\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"10\",\n" +
                "    label: \"遮光\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"11\",\n" +
                "    label: \"干燥遮光\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"12\",\n" +
                "    label: \"遮光密闭\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"13\",\n" +
                "    label: \"遮光凉暗\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"14\",\n" +
                "    label: \"遮光密闭,2-8度\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"15\",\n" +
                "    label: \"冷冻\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"16\",\n" +
                "    label: \"遮光密闭，在凉处保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"17\",\n" +
                "    label: \"密闭，25℃以下保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"18\",\n" +
                "    label: \"遮光，在阴凉处保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"19\",\n" +
                "    label: \"遮光，密闭，25℃以下保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"20\",\n" +
                "    label: \"避光，2-8℃保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"21\",\n" +
                "    label: \"避光，20-30℃保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"22\",\n" +
                "    label: \"避光，2-25℃保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"23\",\n" +
                "    label: \"30℃以下，避光防潮保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"24\",\n" +
                "    label: \"15-25℃干燥处保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"25\",\n" +
                "    label: \"15-30℃保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"26\",\n" +
                "    label: \"密封，凉暗处保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"27\",\n" +
                "    label: \"25℃以下避光保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"28\",\n" +
                "    label: \"密闭，阴凉干燥处保存\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"29\",\n" +
                "    label: \"避光，密闭\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"30\",\n" +
                "    label: \"10-25℃密封保存\"\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"31\",\n" +
                "    label: \"密封、避光室温（10-30℃）保存\"\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"32\",\n" +
                "    label: \"避光，密闭\",\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"33\",\n" +
                "    label: \"遮光，密闭，防冻保存\"\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"34\",\n" +
                "    label: \"密封，在干燥处保存\"\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"35\",\n" +
                "    label: \"避光，密封，在干燥处保存\"\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"36\",\n" +
                "    label: \"密封，遮光\"\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"37\",\n" +
                "    label: \"密封,2-8°C保存\"\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"38\",\n" +
                "    label: \"密闭，防潮\"\n" +
                "  },\n" +
                "  {\n" +
                "    value: \"39\",\n" +
                "    label: \"避光，阴凉干燥处保存\"\n" +
                "  }\n" +
                "]";
        JSONArray objects = JSONUtil.parseArray(str);
        for (Object object : objects) {
            JSONObject jsonObject = JSONUtil.parseObj(object);
            String sql ="INSERT INTO DICTMANAGE.Dict_Public_Code(CCTJ_CODE, CCTJ_NAME) VALUES('%s','%s')";
            String format = String.format(sql, jsonObject.get("value"), jsonObject.get("label"));
            jnOracleServiceImpl.insertAll(format);
        }
    }


    @Test
    public void two() {

    }
}
