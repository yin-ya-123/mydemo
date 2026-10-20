package com.yin.utils.excel.main.pinghu;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.yin.MyDemoApplication;
import com.yin.service.impl.PhOracleServiceImpl;
import com.yin.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class One {

    @Resource
    private PhOracleServiceImpl phOracleServiceImpl;

    String 药品明细 = FileUtils.readFile("D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\utils\\excel\\main\\pinghu\\药品明细.txt", "utf-8");
    String 批次 = FileUtils.readFile("D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\utils\\excel\\main\\pinghu\\批次.txt", "utf-8");
    String 日期 = FileUtils.readFile("D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\utils\\excel\\main\\pinghu\\日期.txt", "utf-8");

    ExcelWriter writer = ExcelUtil.getWriter("D:csssxjgmyDemo批次数据.xlsx");

    @Test
    public void test() {
        String[] aa = new String[]{"110775", "120303", "110210", "119855", "130122", "500029", "139885", "110155", "119930", "110661", "120093"};
        for (String s : aa) {
            JSONArray 日期1 = get日期("01015", s);
            System.out.println(日期1.toString());
        }
    }


    public JSONArray get药品明细(String kfid) {
        String replace = 药品明细.replace("${KFID}", kfid);
        List list = phOracleServiceImpl.selectAll(replace);
        JSONArray objects = JSONUtil.parseArray(JSONUtil.toJsonStr(list));
        return objects;
    }

    public JSONArray get批次(String kfid, String drugid) {
        String replace = 批次.replace("${KFID}", kfid).replace("${DRUG_ID}", drugid);
        List list = phOracleServiceImpl.selectAll(replace);
        return JSONUtil.parseArray(JSONUtil.toJsonStr(list));
    }

    public JSONArray get日期(String kfid, String drugid) {
        String replace = 日期.replace("${KFID}", kfid).replace("${DRUG_ID}", drugid);
        List list = phOracleServiceImpl.selectAll(replace);
        return JSONUtil.parseArray(JSONUtil.toJsonStr(list));
    }

    public List getKCxx(String kfid, String drugid, String kssj) {
        String sql = "SELECT  to_char(m.record_date,'yyyy-mm-dd') record_date,m.record_id,'-' INI_ID, m.batch_no, m.depot_id, m.drug_id,m.real_storage\n" +
                "\tFROM his.drug_storage_detail_mrjl m\n" +
                " where m.depot_id = '" + kfid + "'\n" +
                "\t and m.drug_id = '" + drugid + "'\n" +
                "\t and m.record_date >= date '" + kssj + "'\n" +
                "union all\n" +
                "SELECT  to_char(trunc(sysdate),'yyyy-mm-dd'),'-',INI_ID, m.batch_no, m.depot_id, m.drug_id,m.real_storage\n" +
                "\tFROM his.drug_storage_detail m\n" +
                " where m.depot_id = '" + kfid + "'\n" +
                "\t and m.drug_id = '" + drugid + "'\n" +
                "\t and m.real_storage <> 0\n";
        return phOracleServiceImpl.selectAll(sql);
    }

    public List getKfInfo(String depotId) {
        String sql = "SELECT * FROM \n" +
                "DICTMANAGE.Dict_Depot d \n" +
                "join DICTMANAGE.Dict_Hospinfo yy on d.hospital_code = yy.hosp_sn\n" +
                "where d.depot_code = '" + depotId + "'";
        return phOracleServiceImpl.selectAll(sql);
    }

    public List getYpInfo(String drug_id) {
        String sql = "SELECT y.drug_name FROM DICTMANAGE.Drug_Info y where y.drug_id = '" + drug_id + "'";
        return phOracleServiceImpl.selectAll(sql);
    }

    /*
    库存扣减生成
     */
    @Test
    public void main1() {
        String kfid = "080011";
        Map<String, BigDecimal> drugInfoMap = new LinkedHashMap<>();
        drugInfoMap.put("117219", new BigDecimal("1"));
        for (Map.Entry<String, BigDecimal> stringBigDecimalEntry : drugInfoMap.entrySet()) {
            String drugid = stringBigDecimalEntry.getKey();
            BigDecimal num = stringBigDecimalEntry.getValue();
            JSONArray 日期1 = get日期(kfid, drugid);
            String targetDateStr = "";
            for (Object o : 日期1) {
                JSONObject entries = JSONUtil.parseObj(o);
                String slwc = entries.get("数量尾差").toString();
                if (!slwc.equals("0")) {
                    targetDateStr = entries.get("RQ").toString();
                }
            }
            // 和字符串格式保持一致
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 解析字符串为日期
            LocalDate targetDate = LocalDate.parse(targetDateStr, formatter);
            // 当前本地日期
            LocalDate nowDate = LocalDate.now();
            // 相差天数：正数=目标日期在过去；负数=目标日期在未来
            long diffDays = ChronoUnit.DAYS.between(targetDate, nowDate);
            List<Object> kCxx = getKCxx(kfid, drugid, targetDateStr);
            List<Object> kfInfo = getKfInfo(kfid);
            List ypInfo = getYpInfo(drugid);
            Map<String, List<Object>> grouped = kCxx.stream()
                    .collect(Collectors.groupingBy(obj -> JSONUtil.parseObj(obj).get("BATCH_NO").toString()));
            for (Map.Entry<String, List<Object>> entry : grouped.entrySet()) {
                String batchNo = entry.getKey();
                List<Object> list = entry.getValue();
                if (list.size() < diffDays) continue;
                boolean flag = true;
                for (Object o : list) {
                    String realStorage = JSONUtil.parseObj(o).get("REAL_STORAGE").toString();
                    BigDecimal realStorage1 = new BigDecimal(realStorage);
                    BigDecimal subtract = realStorage1.subtract(num);
                    if (subtract.compareTo(BigDecimal.ZERO) < 0) {
                        flag = false;
                        System.out.println("--" + batchNo + "-批次不够扣:切换下一个");
                        break;
                    }
                }
                if (flag) {
                    System.out.println("--" + batchNo + "-批次够扣:" + JSONUtil.toJsonStr(list));
                    JSONObject entries1 = JSONUtil.parseObj(kfInfo.get(0));
                    JSONObject entries2 = JSONUtil.parseObj(ypInfo.get(0));
                    System.out.println(" ");
                    System.out.println("--" + entries2.get("DRUG_NAME").toString());
                    System.out.println("--" + entries1.get("YYMC").toString() + "-" + entries1.get("DEPOT_NAME").toString());
                    for (Object o : list) {
                        JSONObject entries = JSONUtil.parseObj(o);
                        String recordId = entries.get("RECORD_ID").toString();
                        String iniId = entries.get("INI_ID").toString();
                        String recordDate = entries.get("RECORD_DATE").toString();
                        String realStorage = entries.get("REAL_STORAGE").toString();
                        BigDecimal realStorage1 = new BigDecimal(realStorage);
                        BigDecimal subtract = realStorage1.subtract(num);
                        if (!recordId.equals("-")) {
                            String sql1 = "update his.drug_storage_detail_mrjl m set m.real_storage =" + subtract.toString() +
                                    " where m.record_id ='" + recordId + "' and m.depot_id ='" + kfid + "' and m.drug_id = '" + drugid + "' and m.record_date =date'" + recordDate +
                                    "' AND M.BATCH_NO = '" + batchNo + "';";
                            System.out.println("--每日记录:" + recordDate + "-药品ID:" + drugid + "-批次:" + batchNo + "-原始库存:" + realStorage + "-扣减库存:" + num.toString() + ",修改后库存:" + subtract.toString());
                            System.out.println(sql1);
                        } else if (!iniId.equals("-")) {
                            String slq1 = "update his.drug_storage_detail m set m.real_storage =" + subtract.toString() +
                                    " where m.ini_id ='" + iniId + "' and m.depot_id ='" + kfid + "' and m.drug_id = '" + drugid + "' AND M.BATCH_NO = '" + batchNo + "';";
                            System.out.println("--库存记录:" + recordDate + "-药品ID:" + drugid + "-批次:" + batchNo + "-原始库存:" + realStorage + "-扣减库存:" + num.toString() + ",修改后库存:" + subtract.toString());
                            System.out.println(slq1);
                        }
                    }
                    break;
                }
            }
        }
    }

    /**
     * 库存加生成
     */
    @Test
    public void main2() {
        String kfid = "010026";
        Map<String, BigDecimal> drugInfoMap = new LinkedHashMap<>();
        drugInfoMap.put("505424", new BigDecimal("2"));
        drugInfoMap.put("377298", new BigDecimal("2"));
        drugInfoMap.put("383933", new BigDecimal("2"));
        for (Map.Entry<String, BigDecimal> stringBigDecimalEntry : drugInfoMap.entrySet()) {
            String drugid = stringBigDecimalEntry.getKey();
            BigDecimal num = stringBigDecimalEntry.getValue();
            JSONArray 日期1 = get日期(kfid, drugid);
            String targetDateStr = "";
            for (Object o : 日期1) {
                JSONObject entries = JSONUtil.parseObj(o);
                String slwc = entries.get("数量尾差").toString();
                if (!slwc.equals("0")) {
                    targetDateStr = entries.get("RQ").toString();
                }
            }
            // 和字符串格式保持一致
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 解析字符串为日期
            LocalDate targetDate = LocalDate.parse(targetDateStr, formatter);
            // 当前本地日期
            LocalDate nowDate = LocalDate.now();
            // 相差天数：正数=目标日期在过去；负数=目标日期在未来
            long diffDays = ChronoUnit.DAYS.between(targetDate, nowDate);
            List<Object> kCxx = getKCxx(kfid, drugid, targetDateStr);
            List<Object> kfInfo = getKfInfo(kfid);
            List ypInfo = getYpInfo(drugid);
            Map<String, List<Object>> grouped = kCxx.stream()
                    .collect(Collectors.groupingBy(obj -> JSONUtil.parseObj(obj).get("BATCH_NO").toString()));
            for (Map.Entry<String, List<Object>> entry : grouped.entrySet()) {
                String batchNo = entry.getKey();
                List<Object> list = entry.getValue();

                if (list.size() >= diffDays) {
                    System.out.println("--" + batchNo + "-批次都存在:" + JSONUtil.toJsonStr(list));
                    JSONObject entries1 = JSONUtil.parseObj(kfInfo.get(0));
                    JSONObject entries2 = JSONUtil.parseObj(ypInfo.get(0));
                    System.out.println(" ");
                    System.out.println("--" + entries2.get("DRUG_NAME").toString());
                    System.out.println("--" + entries1.get("YYMC").toString() + "-" + entries1.get("DEPOT_NAME").toString());
                    for (Object o : list) {
                        JSONObject entries = JSONUtil.parseObj(o);
                        String recordId = entries.get("RECORD_ID").toString();
                        String iniId = entries.get("INI_ID").toString();
                        String recordDate = entries.get("RECORD_DATE").toString();
                        String realStorage = entries.get("REAL_STORAGE").toString();
                        BigDecimal realStorage1 = new BigDecimal(realStorage);
                        BigDecimal subtract = realStorage1.add(num);
                        if (!recordId.equals("-")) {
                            String sql1 = "update his.drug_storage_detail_mrjl m set m.real_storage =" + subtract.toString() +
                                    " where m.record_id ='" + recordId + "' and m.depot_id ='" + kfid + "' and m.drug_id = '" + drugid + "' and m.record_date =date'" + recordDate +
                                    "' AND M.BATCH_NO = '" + batchNo + "';";
                            System.out.println("--每日记录:" + recordDate + "-药品ID:" + drugid + "-批次:" + batchNo + "-原始库存:" + realStorage + "-增加库存:" + num.toString() + ",修改后库存:" + subtract.toString());
                            System.out.println(sql1);
                        } else if (!iniId.equals("-")) {
                            String slq1 = "update his.drug_storage_detail m set m.real_storage =" + subtract.toString() +
                                    " where m.ini_id ='" + iniId + "' and m.depot_id ='" + kfid + "' and m.drug_id = '" + drugid + "' AND M.BATCH_NO = '" + batchNo + "';";
                            System.out.println("--库存记录:" + recordDate + "-药品ID:" + drugid + "-批次:" + batchNo + "-原始库存:" + realStorage + "-增加库存:" + num.toString() + ",修改后库存:" + subtract.toString());
                            System.out.println(slq1);
                        }
                    }
                    break;
                }
            }
        }
    }
}
