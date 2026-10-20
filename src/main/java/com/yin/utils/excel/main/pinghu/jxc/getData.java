package com.yin.utils.excel.main.pinghu.jxc;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.yin.MyDemoApplication;
import com.yin.service.impl.PhOracleServiceImpl;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.main.pinghu.jxc.converter.WeiChaConverter;
import com.yin.utils.excel.main.pinghu.jxc.pojo.Depot;
import com.yin.utils.excel.main.pinghu.jxc.pojo.Kcxx;
import com.yin.utils.excel.main.pinghu.jxc.pojo.WeiChaDate;
import com.yin.utils.excel.main.pinghu.jxc.pojo.Param;
import com.yin.utils.excel.main.pinghu.jxc.pojo.WeiChaDrug;
import com.yin.utils.excel.main.pinghu.jxc.pojo.WeiChaTotal;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class getData {
    @Resource
    private PhOracleServiceImpl phOracleServiceImpl;

    String 总尾差 = FileUtils.readFile("D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\utils\\excel\\main\\pinghu\\jxc\\file\\尾差查询-总.txt", "utf-8");
    String 药品 = FileUtils.readFile("D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\utils\\excel\\main\\pinghu\\jxc\\file\\尾差查询-药品.txt", "utf-8");
    String 日期 = FileUtils.readFile("D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\utils\\excel\\main\\pinghu\\jxc\\file\\尾差查询-日期.txt", "utf-8");
    String 库房 = FileUtils.readFile("D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\utils\\excel\\main\\pinghu\\jxc\\file\\库房.txt", "utf-8");
    String 库存查询 = FileUtils.readFile("D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\utils\\excel\\main\\pinghu\\jxc\\file\\库存查询.txt", "utf-8");

    public JSONArray getDrygInfo(Param param) {
        String replace = 总尾差.replace(":KSSJ", param.getStartTime()).replace(":JSSJ", param.getEndTime()).replace(":KF", param.getDepotCode());
        List list = phOracleServiceImpl.selectAll(replace);
        JSONArray objects = JSONUtil.parseArray(JSONUtil.toJsonStr(list));
        return objects;
    }

    public List<WeiChaTotal> getDrygInfoEntity(Param param) {
        String replace = 总尾差.replace(":KSSJ", param.getStartTime()).replace(":JSSJ", param.getEndTime()).replace(":KF", param.getDepotCode());
        List<Map<String, Object>> list = phOracleServiceImpl.selectAll(replace);
        return WeiChaConverter.convertToWeiChaTotal(list);
    }

    public JSONArray getDrugInfoDetails(Param param) {
        String replace = 药品.replace(":KSSJ", param.getStartTime()).replace(":JSSJ", param.getEndTime()).replace(":KF", param.getDepotCode());
        List list = phOracleServiceImpl.selectAll(replace);
        return JSONUtil.parseArray(JSONUtil.toJsonStr(list));
    }

    public List<WeiChaDrug> getDrugInfoDetailsEntity(Param param) {
        String replace = 药品.replace(":KSSJ", param.getStartTime()).replace(":JSSJ", param.getEndTime()).replace(":KF", param.getDepotCode());
        List<Map<String, Object>> list = phOracleServiceImpl.selectAll(replace);
        return WeiChaConverter.convertToWeiChaDrug(list);
    }

    public JSONArray getDrugInfoDetailsRq(Param param) {
        String replace = 日期.replace(":KSSJ", param.getStartTime()).replace(":JSSJ", param.getEndTime()).replace(":KF", param.getDepotCode()).replace(":DRUG", param.getDrugCode());
        List list = phOracleServiceImpl.selectAll(replace);
        return JSONUtil.parseArray(JSONUtil.toJsonStr(list));
    }

    public List<WeiChaDate> getDrugInfoDetailsRqEntity(Param param) {
        String replace = 日期.replace(":KSSJ", param.getStartTime()).replace(":JSSJ", param.getEndTime()).replace(":KF", param.getDepotCode()).replace(":DRUG", param.getDrugCode());
        List<Map<String, Object>> list = phOracleServiceImpl.selectAll(replace);
        return WeiChaConverter.convertToWeiChaDate(list);
    }

    public JSONArray getDepotS() {
        List list = phOracleServiceImpl.selectAll(库房);
        return JSONUtil.parseArray(JSONUtil.toJsonStr(list));
    }

    public List<Depot> getDepotSEntity() {
        List<Map<String, Object>> list = phOracleServiceImpl.selectAll(库房);
        return WeiChaConverter.convertToDepot(list);
    }

    public List getKCxx(Param param) {
        String sql = 库存查询.replace(":KF", param.getDepotCode()).replace(":DRUG", param.getDrugCode()).replace(":KSSJ", param.getStartTime());
        return phOracleServiceImpl.selectAll(sql);
    }

    public List<Kcxx> getKCxxEntity(Param param) {
        String startDate = param.getStartTime().substring(0, 10);
        String sql = 库存查询.replace(":KF", param.getDepotCode()).replace(":DRUG", param.getDrugCode()).replace(":KSSJ", startDate);
        List<Map<String, Object>> list = phOracleServiceImpl.selectAll(sql);
        return WeiChaConverter.convertToKcxx(list);
    }
}
