package com.yin.utils.excel.main.yb;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.yin.MyDemoApplication;
import com.yin.common.ApplicationUtils;
import com.yin.common.err.YException;
import com.yin.pojo.qt.jxc.JxcPojo;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * @author yin
 * @date 2024-11-11
 * @Description 查询进销存那一天有差 进销存处理
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
@Component
public class PurchaseSaleStock {
    public static void main(String[] args) throws YException {
        List<String> datesBetween = ApplicationUtils.getDatesBetween("2025-02-01", "2025-02-28", "day");
        log.info("datesBetween: {}", datesBetween);
    }
    @Autowired
    private OracleService hisOracleServiceImpl;

    @Test
    public void show() {
        String kssj = "2025-02-01";
        String jssj = "2025-02-28";
        LocalDate startDate = LocalDate.of(2025, 2, 1);
        LocalDate endDate = LocalDate.of(2025, 2, 28);
        List<LocalDate> datesInRange = getDatesBetween(startDate, endDate);
        String s = getjxc2(kssj, jssj, "");
        List list = hisOracleServiceImpl.selectAll(s);
        log.info("sql:{}",s);
        JSONArray objects1 = ConvertAllUtils.convertJsonArray(list);
        for (Object o : objects1) {
            JSONObject jsonObject1 = ConvertAllUtils.convertJson(o);
            String ypId = jsonObject1.get("YP_ID").toString();
            String cd_id = jsonObject1.get("CD_ID").toString();
            for (LocalDate date : datesInRange) {
//            System.out.println(date.toString());
                String sql = getjxc(kssj, date.toString(), ypId, cd_id);
                List list2 = hisOracleServiceImpl.selectAll(sql);
                JSONArray objects = ConvertAllUtils.convertJsonArray(list2);
                for (Object object : objects) {
                    JSONObject jsonObject = ConvertAllUtils.convertJson(object);
//                System.out.println(jsonObject.toString());
                    String string = jsonObject.get("差").toString();
                    String string2 = jsonObject.get("药品名称").toString();
                    String string3 = jsonObject.get("YP_ID").toString();
                    String string4 = jsonObject.get("CD_ID").toString();
                    if (!string.equals("0")) {
                        log.error("{}-{}-{}{}有差", string3, string4, string2, date);
                    }
                }
//                log.error("无数据{}",date);
            }
            log.info("分割线-------------------------------------------------");
        }
    }
    /**
     * 年度进销存，平账导出，把差数据挪到损益中
     * @throws YException
     */
    @Test
    public void exportYear() throws YException {
        //导出进销存年度，月度的数据，把差额平账
        String kssj = "2023-01-01";
        String jssj = "2023-01-31";
        String path = "D:\\公司\\项目文件\\康复医院\\药房数据导出\\2024-12-04";
        LocalDate startDate = LocalDate.of(2022, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
//        LocalDate startDate = LocalDate.of(2024, 1, 1);
//        LocalDate endDate = LocalDate.of(2024, 12, 31);
        List<LocalDate> datesBetween = ApplicationUtils.getDatesBetween(startDate, endDate, "year");
        ExcelUtils<JxcPojo> jxcPojoExcelUtils = new ExcelUtils<>();
        String year;
        String month;
        for (LocalDate localDate : datesBetween) {
            year = localDate.getYear() + "";
            month = localDate.getMonthValue() + "";
            LocalDate localDate1 = localDate.with(TemporalAdjusters.lastDayOfYear());
            if(localDate.toString().equals("2024-01-01")){
                localDate1 = LocalDate.parse("2024-11-30");
            }
            String jxc3 = getJxc3("3", localDate.toString(), localDate1.toString());
            List list = hisOracleServiceImpl.selectAll(jxc3);
            ArrayList<JxcPojo> jxcPojos = ConvertAllUtils.convertListAll(list, JxcPojo.class);
            if (!jxcPojos.isEmpty()) {
                for (JxcPojo jxcPojo : jxcPojos) {
                    BigDecimal sy = BigDecimal.valueOf(jxcPojo.getSy());
                    BigDecimal c = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getC()));
                    if (c.doubleValue() != 0) {
                        double v = Double.parseDouble(jxcPojo.getC());
                        if (v >= 10 || v <= -10) {
                            log.info("药品{}-{}-{},月份:{},差值为:{},数值大于10，较大", jxcPojo.getYpmc(), jxcPojo.getYP_ID(), jxcPojo.getCD_ID(), month, jxcPojo.getC());
                        }
                        if (v >= 5 || v <= -5) {
                            log.info("药品{}-{}-{},月份:{},差值为:{},数值大于5，较大", jxcPojo.getYpmc(), jxcPojo.getYP_ID(), jxcPojo.getCD_ID(), month, jxcPojo.getC());
                        }
//                        if(jxcPojo.getYP_ID().equals("35661") && jxcPojo.getCD_ID().equals("28907")){
//                            log.info("损益：{}，差：{}",sy,c);
//                        }
//                        log.info("损益：{}，差：{}", sy, c);
                        BigDecimal num = BigDecimal.valueOf(0.0);
                        if (sy.doubleValue() < 0 && c.doubleValue() > 0) {
                            num = sy.subtract(c);
//                            log.info("计算后的值：{}",num);
                        } else if (c.doubleValue() < 0) {
                            num = sy.add(c.abs());
                        } else if (c.doubleValue() > 0) {
                            num = sy.subtract(c);
                        }
                        jxcPojo.setSy(num.doubleValue());
                        jxcPojo.setC("0");
                    }
                    BigDecimal jsqm = BigDecimal.valueOf(0.0);
                    BigDecimal qc = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getQckc()));
                    BigDecimal j = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getJ()));
                    BigDecimal x = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getX()));
                    BigDecimal newsy = BigDecimal.valueOf(jxcPojo.getSy());
                    BigDecimal ly = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getLy()));
                    BigDecimal qm = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getQmkc()));
                    jsqm = jsqm.add(qc).add(j).add(x).add(newsy).add(ly).subtract(qm);
                    jxcPojo.setC(String.valueOf(jsqm));
                    if (jsqm.doubleValue() != 0) {
                        log.info("药品{}-{}-{},月份:{},差值为:{},还存在差额", jxcPojo.getYpmc(), jxcPojo.getYP_ID(),
                                jxcPojo.getCD_ID(), year + "-" + month, jsqm);

                    }
                }
                String newPath = path + "\\" + year + "year\\" + year + "年.xlsx";
                FileUtils.createFile(newPath);
                jxcPojoExcelUtils.writeExcel(newPath, new JxcPojo(), jxcPojos);
                log.info("{}-{} 进销存已导出", year, month);
            } else {
                log.info("{}-{} 进销存为空", year, month);
            }

        }

    }
    /**
     * 月度进销存，平账导出，把差数据挪到损益中
     * @throws YException
     */
    @Test
    public void exportMonth() throws YException {
        //导出进销存年度，月度的数据，把差额平账
        String kssj = "2023-01-01";
        String jssj = "2023-01-31";
        String path = "D:\\公司\\项目文件\\康复医院\\药房数据导出\\2024-12-04";
        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 11, 30);
//        LocalDate startDate = LocalDate.of(2024, 1, 1);
//        LocalDate endDate = LocalDate.of(2024, 12, 31);
        List<LocalDate> datesBetween = ApplicationUtils.getDatesBetween(startDate, endDate, "month");
        ExcelUtils<JxcPojo> jxcPojoExcelUtils = new ExcelUtils<>();
        String year;
        String month;
        for (LocalDate localDate : datesBetween) {
            year = localDate.getYear() + "";
            month = localDate.getMonthValue() + "";
            LocalDate localDate1 = localDate.withDayOfMonth(localDate.lengthOfMonth());
            String jxc3 = getJxc3("3", localDate.toString(), localDate1.toString());
            List list = hisOracleServiceImpl.selectAll(jxc3);
            ArrayList<JxcPojo> jxcPojos = ConvertAllUtils.convertListAll(list, JxcPojo.class);
            if (!jxcPojos.isEmpty()) {
                for (JxcPojo jxcPojo : jxcPojos) {
                    BigDecimal sy = BigDecimal.valueOf(jxcPojo.getSy());
                    BigDecimal c = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getC()));
                    if (c.doubleValue() != 0) {
                        if (c.doubleValue() >= 10 || c.doubleValue() <= -10) {
                            log.info("药品{}-{}-{},月份:{},差值为:{},数值大于10，较大", jxcPojo.getYpmc(), jxcPojo.getYP_ID(), jxcPojo.getCD_ID(), month, jxcPojo.getC());
                        }
                        if (c.doubleValue() >= 5 || c.doubleValue() <= -5) {
                            log.info("药品{}-{}-{},月份:{},差值为:{},数值大于5，较大", jxcPojo.getYpmc(), jxcPojo.getYP_ID(), jxcPojo.getCD_ID(), month, jxcPojo.getC());
                        }
//                        if(jxcPojo.getYP_ID().equals("37476")){
//                            log.info("1{},损益：{}，差：{}",localDate1,sy,c.doubleValue());
//                        }
                        BigDecimal num = BigDecimal.valueOf(0.0);
                        if (sy.doubleValue() < 0 && c.doubleValue() > 0) {
                            num = sy.subtract(c);
//                            log.info("计算后的值：{}",num);
                        } else if (c.doubleValue() < 0) {
                            num = sy.add(c.abs());
                        } else if (c.doubleValue() > 0) {
                            num = sy.subtract(c);
                        }
                        jxcPojo.setSy(num.doubleValue());
                        jxcPojo.setC("0");
                    }
                    BigDecimal jsqm = BigDecimal.valueOf(0.0);
                    BigDecimal qc = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getQckc()));
                    BigDecimal j = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getJ()));
                    BigDecimal x = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getX()));
                    BigDecimal newsy = BigDecimal.valueOf(jxcPojo.getSy());
                    BigDecimal ly = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getLy()));
                    BigDecimal qm = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getQmkc()));
                    jsqm = jsqm.add(qc).add(j).add(x).add(newsy).add(ly).subtract(qm);
                    jxcPojo.setC(String.valueOf(jsqm));
                    if (jsqm.doubleValue() != 0) {
                        log.info("药品{}-{}-{},月份:{},差值为:{},还存在差额", jxcPojo.getYpmc(), jxcPojo.getYP_ID(),
                                jxcPojo.getCD_ID(), year + "-" + month, jsqm);
                    }
                }
                String newPath = path + "\\" + year + "\\" + month + "月.xlsx";
                FileUtils.createFile(newPath);
                jxcPojoExcelUtils.writeExcel(newPath, new JxcPojo(), jxcPojos);
                log.info("{}-{} 进销存已导出", year, month);
            } else {
                log.info("{}-{} 进销存为空", year, month);
            }

        }

    }
    /**
     * 天度进销存，平账导出，把差数据挪到损益中
     * @throws YException
     */
    @Test
    public void exportDay() throws YException {
        //导出进销存年度，月度的数据，把差额平账
        String kssj = "2023-01-01";
        String jssj = "2023-01-31";
        String path = "D:\\公司\\项目文件\\康复医院\\药房数据导出\\2024-12-04";
        LocalDate startDate = LocalDate.of(2022, 5, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
//        LocalDate startDate = LocalDate.of(2024, 1, 1);
//        LocalDate endDate = LocalDate.of(2024, 12, 31);
        List<LocalDate> datesBetween = ApplicationUtils.getDatesBetween(startDate, endDate, "day");
        ExcelUtils<JxcPojo> jxcPojoExcelUtils = new ExcelUtils<>();
        String year;
        String month;
        String day;
        for (LocalDate localDate : datesBetween) {
            year = localDate.getYear() + "";
            month = localDate.getMonthValue() + "";
            day = localDate.getDayOfMonth() + "";
//            LocalDate localDate1 = localDate.withDayOfMonth(localDate.lengthOfMonth());
            String jxc3 = getJxc3("3", localDate.toString(), localDate.toString());
            List list = hisOracleServiceImpl.selectAll(jxc3);
            ArrayList<JxcPojo> jxcPojos = ConvertAllUtils.convertListAll(list, JxcPojo.class);
            if (!jxcPojos.isEmpty()) {
                String newPath = path + "\\" + year + "day" + "\\" + month + "-" + day + ".xlsx";
                FileUtils.createFile(newPath);
                jxcPojoExcelUtils.writeExcel(newPath, new JxcPojo(), jxcPojos);
                log.info("{}-{}-{} 进销存已导出", year, month, day);
            } else {
                log.info("{}-{}-{} 进销存为空", year, month, day);
            }

        }

    }
    /**
     * 每日记录增加
     *
     * @throws YException
     */
    @Test
    public void exportDayMrjlAdd() throws YException {
        //导出进销存年度，月度的数据，把差额平账
        String path = "D:\\公司\\项目文件\\康复医院\\药房数据导出\\2024-12-04";
        LocalDate startDate = LocalDate.of(2022, 11, 1);
        LocalDate endDate = LocalDate.of(2022, 11, 20);
//        LocalDate startDate = LocalDate.of(2024, 1, 1);
//        LocalDate endDate = LocalDate.of(2024, 12, 31);
        List<LocalDate> datesBetween = ApplicationUtils.getDatesBetween(startDate, endDate, "day");
        ExcelUtils<JxcPojo> jxcPojoExcelUtils = new ExcelUtils<>();
        String year;
        String month;
        String day;
        int index = 1;
        String startDay = "2022-06-06";
        for (LocalDate localDate : datesBetween) {
            year = localDate.getYear() + "";
            month = localDate.getMonthValue() + "";
            day = localDate.getDayOfMonth() + "";
//            LocalDate localDate1 = localDate.withDayOfMonth(localDate.lengthOfMonth());
            String jxc3 = getJxc3("3", startDay, localDate.toString());
            List list = hisOracleServiceImpl.selectAll(jxc3);
//            System.out.println(list);
            ArrayList<JxcPojo> jxcPojos = ConvertAllUtils.convertListAll(list, JxcPojo.class);
            log.info("{}日期", localDate.toString());
//            log.info("{}", jxc3);
            for (JxcPojo jxcPojo : jxcPojos) {
//                if (jxcPojo.getYP_ID().equals("35498") && jxcPojo.getCD_ID().equals("26301")) {
                System.out.println(jxcPojo);
                log.info("查询进销存数据为:{}", jxcPojo);
                //判断每日记录表是否存在数据
                String drugStoMrjl = getDrugStoMrjl("3", localDate.toString(), jxcPojo.getYP_ID(), jxcPojo.getCD_ID());
                List list1 = hisOracleServiceImpl.selectAll(drugStoMrjl);
                if (list1.size() <= 0) {
                    log.info("{}-{}-{}:每日库存记录表无对应数据保存", localDate.toString(), jxcPojo.getYP_ID(), jxcPojo.getCD_ID());
                    //计算当日结存
//                    int qc = Double.valueOf(jxcPojo.getQckc()).intValue();
//                    int j = Double.valueOf(jxcPojo.getJ()).intValue();
//                    int x = Double.valueOf(jxcPojo.getX()).intValue();
//                    int sy = Double.valueOf(jxcPojo.getSy()).intValue();
//                    int ly = Double.valueOf(jxcPojo.getLy()).intValue();
//                    int qm = Double.valueOf(jxcPojo.getQmkc()).intValue();
                    BigDecimal jsqm = BigDecimal.valueOf(0.0);
                    BigDecimal qc = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getQckc()));
                    BigDecimal j = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getJ()));
                    BigDecimal x = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getX()));
                    BigDecimal sy = BigDecimal.valueOf(jxcPojo.getSy());
                    BigDecimal ly = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getLy()));
                    BigDecimal qm = BigDecimal.valueOf(Double.parseDouble(jxcPojo.getQmkc()));
                    log.info("结算期末数据:期初{},进{},销{},损益{},领用{}", qc, j, x, sy, ly);
                    jsqm = jsqm.add(qc).add(j).add(x).add(sy).add(ly);
                    log.info("计算期末库存为{}，实际查询库存为:{}", jsqm, qm);
                    //设置插入数据
                    //查询药品库存表，获取系数等数据
                    String drugKcInfo = getDrugKcInfo(jxcPojo.getYP_ID(), jxcPojo.getCD_ID(), "3");
                    //获取日期之前的最新一次入库，价格当作使用
                    String drugIn = getDrugIn(localDate.toString(), jxcPojo.getYP_ID(), jxcPojo.getCD_ID(), "3");
                    List selected = hisOracleServiceImpl.selectAll(drugKcInfo);
                    List selectedIn = hisOracleServiceImpl.selectAll(drugIn);
                    if (selected.isEmpty() || selectedIn.isEmpty()) {
                        log.info("库存表数据为空，查询系数数据失败");
                    } else {
                        cn.hutool.json.JSONObject jsonObject = ConvertAllUtils.convertJson2(selected.get(0));
                        cn.hutool.json.JSONObject jsonObjectIn = ConvertAllUtils.convertJson2(selectedIn.get(0));
                        log.info(jsonObject.toString());
                        log.info(jsonObjectIn.toString());
                        String plxs = jsonObject.get("PL_XS").toString();
                        String zxbzsl = null == jsonObject.get("ZXBZL") ? "" : jsonObject.get("ZXBZL").toString();
                        String zxbzdw = null == jsonObject.get("ZXBZDW") ? "" : jsonObject.get("ZXBZDW").toString();
                        String jl = null == jsonObject.get("JL") ? "" : jsonObject.get("JL").toString();
                        String jldw = null == jsonObject.get("JLDW") ? "" : jsonObject.get("JLDW").toString();
                        String xsdw = jsonObject.get("XSDW_ID").toString();
                        String lsdj = jsonObjectIn.get("LS_DJ").toString();
                        String pfdj = jsonObjectIn.get("PF_DJ").toString();
                        String jhdj = jsonObjectIn.get("JH_DJ").toString();
                        lsdj = String.format("%.4f", Double.parseDouble(lsdj) / Double.parseDouble(plxs));
                        pfdj = String.format("%.4f", Double.parseDouble(pfdj) / Double.parseDouble(plxs));
                        jhdj = String.format("%.4f", Double.parseDouble(jhdj) / Double.parseDouble(plxs));
                        String sj = localDate.plusDays(1).toString() + " 00:01:04";
                        HashMap<String, String> drugMrMap = new HashMap<>();
                        drugMrMap.put("ypid", jxcPojo.getYP_ID());
                        drugMrMap.put("kfid", "3");
                        drugMrMap.put("cdid", jxcPojo.getCD_ID());
                        drugMrMap.put("plxs", plxs);
                        drugMrMap.put("lsdj", lsdj);
                        drugMrMap.put("sjkc", String.valueOf(jsqm));
                        drugMrMap.put("xnkc", String.valueOf(jsqm));
                        drugMrMap.put("zxbzl", zxbzsl);
                        drugMrMap.put("zxbzdw", zxbzdw);
                        drugMrMap.put("jl", jl);
                        drugMrMap.put("jldw", jldw);
                        drugMrMap.put("xsdwid", xsdw);
                        drugMrMap.put("cjsj", sj);
                        drugMrMap.put("zhgxsj", sj);
                        drugMrMap.put("pfdj", pfdj);
                        drugMrMap.put("jhdj", jhdj);
                        log.info("进行数据插入:{}", JSONObject.toJSONString(drugMrMap));
                        String addDrugStoMrjl = addDrugStoMrjl(drugMrMap);
//                                log.info(addDrugStoMrjl);
                        hisOracleServiceImpl.insertAll(addDrugStoMrjl);
                        log.info("数据插入成功");
                    }
                } else {
                    log.info("每日库存记录表中已存在数据");
                }
//                }
            }
            log.info("{} 分割线", localDate.toString());
        }

    }
    /**
     * 排查那一天有差
     *
     * @throws YException
     */
    @Test
    public void jcxCchuli() throws YException {
        //导出进销存年度，月度的数据，把差额平账
        LocalDate startDate = LocalDate.of(2025, 2, 1);
        LocalDate endDate = LocalDate.of(2025, 2, 28);
//        LocalDate startDate = LocalDate.of(2024, 1, 1);
//        LocalDate endDate = LocalDate.of(2024, 12, 31);
        List<LocalDate> datesBetween = ApplicationUtils.getDatesBetween(startDate, endDate, "day");
        ExcelUtils<JxcPojo> jxcPojoExcelUtils = new ExcelUtils<>();
        String year;
        String month;
        String day;
        String startDay = "2025-02-01";
        for (LocalDate localDate : datesBetween) {
            year = localDate.getYear() + "";
            month = localDate.getMonthValue() + "";
            day = localDate.getDayOfMonth() + "";
//            LocalDate localDate1 = localDate.withDayOfMonth(localDate.lengthOfMonth());
            String jxc3 = getJxc3("3", startDay, localDate.toString());
            List list = hisOracleServiceImpl.selectAll(jxc3);
            ArrayList<JxcPojo> jxcPojos = ConvertAllUtils.convertListAll(list, JxcPojo.class);
            log.info("{}日期", localDate.toString());
            for (JxcPojo jxcPojo : jxcPojos) {
                if (!jxcPojo.getC().equals("0")) {
//                    System.out.println(jxcPojo);
                    log.info(jxcPojo.toString());
                    log.info("{} 进销存有差 药品id:{},产地id:{},药品名称:{},差:{}", localDate.toString(),
                            jxcPojo.getYP_ID(), jxcPojo.getCD_ID(), jxcPojo.getYpmc(), jxcPojo.getC());
                }
//                }
            }
            log.info("{} 分割线", localDate.toString());
        }

    }
    /**
     * 获取时间区间的每一天日期
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 时间集合
     */
    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }
    public static String getjxc(String kssj, String jssj, String ypid, String cd_id) {
        String s = "with ypxx as (\n" +
                "\n" +
                "    select '1' as lx,yp.yp_mc as \"药品名称\",cd.sccj_mc as \"生产厂家\",yp.gg as \"规格\",dw.dw_mc as \"单位\",n.yp_spmc as \"商品名\",\n" +
                "       ROUND(sum(nvl((a.SJ_KC)/A.PL_XS*KC.PL_XS,0)),2) as \"期初库存\",\n" +
                " sum(-1*b.fysl*kc.pl_xs) as \"销\",sum(rksl*kc.pl_xs) as \"进\",sum(sy*kc.pl_xs) as \"损益\",sum(rkks*kc.pl_xs) as \"领用\",\n" +
                "ROUND(sum(nvl(nvl(z.SJ_KC/Z.PL_XS*KC.PL_XS,kc.sj_kc),0)),2) as \"期末库存\",\n" +
                "ROUND( ROUND(sum(nvl((a.SJ_KC)/A.PL_XS*KC.PL_XS,0)),2) -sum(b.fysl*kc.pl_xs) +sum(rksl*kc.pl_xs) +sum(sy*kc.pl_xs) -ROUND(sum(nvl(nvl(z.SJ_KC/Z.PL_XS*KC.PL_XS,kc.sj_kc),0)),2)+sum(rkks*kc.pl_xs),2) as sl,b.yp_id,b.cd_id,kc.ls_dj \"零售价\"\n" +
                ",cw.cwh    \n" +
                "from his.drug_storage kc \n" +
                "     left join drug_storage_mrjl a \n" +
                "     on kc.yp_id=a.yp_id and kc.cd_id=a.cd_id and kc.kf_id=a.kf_id and   kc.kf_id='2'  and to_char(a.cjsj,'yyyy-mm-dd')=:in_ksrq\n" +
                "     left join drug_storage_mrjl z \n" +
                "     on kc.yp_id=z.yp_id and kc.cd_id=z.cd_id and  kc.kf_id=z.kf_id and  z.kf_id='2' and to_char(z.cjsj-1,'yyyy-mm-dd')=:in_jsrq\n" +
                "     left join DICTMANAGE.drug_area_list n on a.yp_id=n.yp_id and a.cd_id=n.yp_cd_id\n" +
                "     left join DICTMANAGE.dict_drug_area cd on   kc.CD_ID=cd.sccj_id\n" +
                "     left join DICTMANAGE.dict_pack_unit dw on kc.xsdw_id=dw.dw_id\n" +
                "     left join his.drug_cw cw on cw.kf_id=kc.kf_id and cw.yp_id=kc.yp_id and cw.cd_id=kc.cd_id\n" +
                "     left join DICTMANAGE.drug_info yp on kc.yp_id =yp.yp_id and yp_lx in('1','2'),     \n" +
                "    (\n" +
                "     --发药\n" +
                "     select yp_id ,cd_id,sum(sl) as fysl,sum(rksl) as rksl,sum(rkks) as rkks  ,sum(sy) as  sy from(\n" +
                "     select SFXM_ID as yp_id,a.YPCD_ID as cd_id,SUM(A.SFXM_SL/a.yp_plxs) as sl,0 as rksl,0 as rkks,0 sy from outpat_dispense_detail a,drug_storage b\n" +
                "     where a.FY_STATUS in('2','7') and a.sfxm_id=b.yp_id and a.fyyf_id=b.kf_id and -- a.sfxm_id='7039' and \n" +
                "     a.fy_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.fy_rq>=to_date(:in_ksrq,'yyyy-mm-dd')\n" +
                "     and a.fyyf_id= '2'  AND A.YPCD_ID=B.CD_ID\n" +
                "     group by sfxm_id,a.YPCD_ID--,--A.YP_PLXS--,B.pl_xs\n" +
                "     UNION ALL     \n" +
                "     select SFXM_ID as yp_id,a.CD_ID as cd_id,SUM(A.SFXM_SL/a.yp_plxs) as sl,0 as rksl,0 as rkks,0 sy from Inpat_Dispens_Detail a,drug_storage b\n" +
                "     where a.FY_STATUS in('1','2','7') and a.sfxm_id=b.yp_id and a.fy_yfid=b.kf_id and -- a.sfxm_id='7039' and \n" +
                "     a.cjsj<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.cjsj>=to_date(:in_ksrq,'yyyy-mm-dd')\n" +
                "     and a.fy_yfid= '2'  AND A.CD_ID=B.CD_ID\n" +
                "     group by sfxm_id,a.CD_ID--,--A.YP_PLXS--,B.pl_xs\n" +
                "     UNION ALL\n" +
                "      --结束时间点的未退药 ，放在销里边正数 ，为了把当天已经写入的发药表数据抵消\n" +
                "     select a.yp_id,a.cd_id,SUM(a.kc_sl) as sl,0 as rksl,0 as rkks,0 sy from bq_tykf_mrjl a,drug_storage b\n" +
                "     where a.yp_id=b.yp_id and a.cd_id=b.cd_id and a.kc_sl<>0 and b.kf_id='2' and '3'='2' and trunc(a.cjsj-1)=to_date(:in_jsrq,'yyyy-mm-dd')\n" +
                "     group by a.yp_id,a.CD_ID \n" +
                "     union ALL\n" +
                "     --开始时间点的未退药 , 放在销里边负数，为了之前的未退药导致期初数不对，减去销\n" +
                "     select a.yp_id,a.cd_id,-SUM(a.kc_sl) as sl,0 as rksl,0 as rkks,0 sy from bq_tykf_mrjl a,drug_storage b\n" +
                "     where a.yp_id=b.yp_id and a.cd_id=b.cd_id and a.kc_sl<>0 and b.kf_id='3' and '3'='2' and trunc(a.cjsj)=to_date(:in_ksrq,'yyyy-mm-dd')\n" +
                "     group by a.yp_id,a.CD_ID \n" +
                "     UNION ALL\n" +
                "     select a.yp_id,a.cd_id,SUM(a.kc_sl) as sl,0 as rksl,0 as rkks,0 sy from bq_tykf a,drug_storage b\n" +
                "     where a.yp_id=b.yp_id and a.cd_id=b.cd_id and a.kc_sl<>0 and b.kf_id='2' and '3'='2' and trunc(sysdate)=to_date(:in_jsrq,'yyyy-mm-dd')\n" +
                "     group by a.yp_id,a.CD_ID  \n" +
                "     union ALL\n" +
                "    --入库\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,sum(sl/a.pl_xs) as rksl,0 as rkks,0 sy from drug_inoutdepot a,drug_storage b,DICTMANAGE.dict_depot kf\n" +
                "       where a.yp_id=b.yp_id and a.ed_kf=b.kf_id and   ((ED_kf ='2' AND A.LX NOT IN('1','2')) OR (ED_kf='6' AND '6'='2' ))  and a.lx<>'8'and a.cd_id=b.cd_id and st_kf=kf.kf_id \n" +
                "      and a.js_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.js_rq>=to_date(:in_ksrq,'yyyy-mm-dd') and a.del_flag='0'and a.lx not in('6','7')\n" +
                "      group by a.yp_id,a.cd_id \n" +
                "      union ALL\n" +
                "      --出库\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,sum(-1*sl/a.pl_xs) as rksl,0 as rkks,0 sy from drug_inoutdepot a,drug_storage b,DICTMANAGE.dict_depot kf\n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and   ((st_kf ='2' AND A.LX NOT IN('1','2')) OR (ST_KF='6' AND '6'='2' )) and a.lx<>'8'and a.cd_id=b.cd_id and ed_kf=kf.kf_id \n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and a.lx not in('6','7')\n" +
                "      group by a.yp_id,a.cd_id\n" +
                "     --入库科室\n" +
                "      union ALL\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(sl/a.pl_xs)  as rkks,0 sy from drug_inoutdepot a,drug_storage b ,DICTMANAGE.dict_depart bm\n" +
                "      where a.yp_id=b.yp_id and a.ed_kf=b.kf_id and  ed_kf='2'  and lx<>'8'and a.cd_id=b.cd_id and st_kf=bm.bm_id\n" +
                "      and a.js_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.js_rq>=to_date(:in_ksrq,'yyyy-mm-dd') and a.del_flag='0' and lx in('4','3') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      union all\n" +
                "      --出库科室\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(-1*sl/a.pl_xs) as rkks, 0 sy from drug_inoutdepot a,drug_storage b ,DICTMANAGE.dict_depart bm\n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and  st_kF='2' and lx<>'8'and a.cd_id=b.cd_id and ed_kf=bm.bm_id \n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and lx in('4','3') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      --采购入库\n" +
                "      union ALL\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(sl/a.pl_xs)  as rkks,0 sy from drug_inoutdepot a,drug_storage b ,dictmanage.dict_drug_supplier bm\n" +
                "      where a.yp_id=b.yp_id and a.ed_kf=b.kf_id and  ed_kf='2'  and lx<>'8'and a.cd_id=b.cd_id and st_kf=bm.ghs_id\n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd') and a.del_flag='0' and lx in('1') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      union all\n" +
                "      --采购退库\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(-1*sl/a.pl_xs) as rkks, 0 sy from drug_inoutdepot a,drug_storage b ,dictmanage.dict_drug_supplier bm\n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and  st_kF='2' and lx<>'8'and a.cd_id=b.cd_id and ed_kf=bm.ghs_id \n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and lx in('2') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      union ALL\n" +
                "      --损益\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,0 as rkks, sum(1*sl/a.pl_xs) sy from drug_inoutdepot a,drug_storage b \n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and  st_kf='2' and lx<>'8'and a.cd_id=b.cd_id\n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and lx in ('6','7','9')  \n" +
                "      group by a.cd_id,a.yp_id\n" +
                "      \n" +
                "      ) group by yp_id,cd_id\n" +
                "      ) b where  b.CD_ID=kc.cd_id and  b.yp_id=kc.yp_id \n" +
                "      and  kc.kf_id ='2' \n" +
                "      group by yp.yp_mc,cd.sccj_mc,dw.dw_mc,z.PL_XS,yp.gg,yp.bz_bm,b.yp_id,b.cd_id,kc.ls_dj,YP_SPMC,cw.cwh\n" +
                "        )\n" +
                "      select yp_id as \"标准编码\",cwh 仓位号, 药品名称,商品名,生产厂家,规格,单位,\n" +
                "      sum(期初库存) as 期初库存 ,sum(进) as 进,ROUND(sum(销),2) as \"销\",sum(损益) as \"损溢\",sum(领用) as \"领用\",\n" +
                "      sum(期末库存) as \"期末库存\", sum(sl) as\"差\",零售价,yp_id,cd_id\n" +
                "      from ypxx where lx='1' and yp_id ='" + ypid + "' and cd_id='" + cd_id + "'\n" +
                "      group by lx, 药品名称,生产厂家,单位,规格,yp_id,cd_id,零售价,商品名,cwh\n" +
                "having sum(sl)<>'0'" +
                "      order by cwh\n" +
                "\n";
        String s1 = s.replaceAll(":in_jsrq", "'" + jssj + "'");
        s1 = s1.replaceAll(":in_ksrq", "'" + kssj + "'");
        return s1;
    }
    public static String getjxc2(String kssj, String jssj, String pym) {
        String s = "with ypxx as (\n" +
                "\n" +
                "    select '1' as lx,yp.yp_mc as \"药品名称\",cd.sccj_mc as \"生产厂家\",yp.gg as \"规格\",dw.dw_mc as \"单位\",n.yp_spmc as \"商品名\",\n" +
                "       ROUND(sum(nvl((a.SJ_KC)/A.PL_XS*KC.PL_XS,0)),2) as \"期初库存\",\n" +
                " sum(-1*b.fysl*kc.pl_xs) as \"销\",sum(rksl*kc.pl_xs) as \"进\",sum(sy*kc.pl_xs) as \"损益\",sum(rkks*kc.pl_xs) as \"领用\",\n" +
                "ROUND(sum(nvl(nvl(z.SJ_KC/Z.PL_XS*KC.PL_XS,kc.sj_kc),0)),2) as \"期末库存\",\n" +
                "ROUND( ROUND(sum(nvl((a.SJ_KC)/A.PL_XS*KC.PL_XS,0)),2) -sum(b.fysl*kc.pl_xs) +sum(rksl*kc.pl_xs) +sum(sy*kc.pl_xs) -ROUND(sum(nvl(nvl(z.SJ_KC/Z.PL_XS*KC.PL_XS,kc.sj_kc),0)),2)+sum(rkks*kc.pl_xs),2) as sl,b.yp_id,b.cd_id,kc.ls_dj \"零售价\"\n" +
                ",cw.cwh    \n" +
                "from his.drug_storage kc \n" +
                "     left join drug_storage_mrjl a \n" +
                "     on kc.yp_id=a.yp_id and kc.cd_id=a.cd_id and kc.kf_id=a.kf_id and   kc.kf_id='2'  and to_char(a.cjsj,'yyyy-mm-dd')=:in_ksrq\n" +
                "     left join drug_storage_mrjl z \n" +
                "     on kc.yp_id=z.yp_id and kc.cd_id=z.cd_id and  kc.kf_id=z.kf_id and  z.kf_id='2' and to_char(z.cjsj-1,'yyyy-mm-dd')=:in_jsrq\n" +
                "     left join DICTMANAGE.drug_area_list n on a.yp_id=n.yp_id and a.cd_id=n.yp_cd_id\n" +
                "     left join DICTMANAGE.dict_drug_area cd on   kc.CD_ID=cd.sccj_id\n" +
                "     left join DICTMANAGE.dict_pack_unit dw on kc.xsdw_id=dw.dw_id\n" +
                "     left join his.drug_cw cw on cw.kf_id=kc.kf_id and cw.yp_id=kc.yp_id and cw.cd_id=kc.cd_id\n" +
                "     left join DICTMANAGE.drug_info yp on kc.yp_id =yp.yp_id and yp_lx in('1','2'),     \n" +
                "    (\n" +
                "     --发药\n" +
                "     select yp_id ,cd_id,sum(sl) as fysl,sum(rksl) as rksl,sum(rkks) as rkks  ,sum(sy) as  sy from(\n" +
                "     select SFXM_ID as yp_id,a.YPCD_ID as cd_id,SUM(A.SFXM_SL/a.yp_plxs) as sl,0 as rksl,0 as rkks,0 sy from outpat_dispense_detail a,drug_storage b\n" +
                "     where a.FY_STATUS in('2','7') and a.sfxm_id=b.yp_id and a.fyyf_id=b.kf_id and -- a.sfxm_id='7039' and \n" +
                "     a.fy_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.fy_rq>=to_date(:in_ksrq,'yyyy-mm-dd')\n" +
                "     and a.fyyf_id= '2'  AND A.YPCD_ID=B.CD_ID\n" +
                "     group by sfxm_id,a.YPCD_ID--,--A.YP_PLXS--,B.pl_xs\n" +
                "     UNION ALL     \n" +
                "     select SFXM_ID as yp_id,a.CD_ID as cd_id,SUM(A.SFXM_SL/a.yp_plxs) as sl,0 as rksl,0 as rkks,0 sy from Inpat_Dispens_Detail a,drug_storage b\n" +
                "     where a.FY_STATUS in('1','2','7') and a.sfxm_id=b.yp_id and a.fy_yfid=b.kf_id and -- a.sfxm_id='7039' and \n" +
                "     a.cjsj<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.cjsj>=to_date(:in_ksrq,'yyyy-mm-dd')\n" +
                "     and a.fy_yfid= '2'  AND A.CD_ID=B.CD_ID\n" +
                "     group by sfxm_id,a.CD_ID--,--A.YP_PLXS--,B.pl_xs\n" +
                "     UNION ALL\n" +
                "      --结束时间点的未退药 ，放在销里边正数 ，为了把当天已经写入的发药表数据抵消\n" +
                "     select a.yp_id,a.cd_id,SUM(a.kc_sl) as sl,0 as rksl,0 as rkks,0 sy from bq_tykf_mrjl a,drug_storage b\n" +
                "     where a.yp_id=b.yp_id and a.cd_id=b.cd_id and a.kc_sl<>0 and b.kf_id='2' and '3'='2' and trunc(a.cjsj-1)=to_date(:in_jsrq,'yyyy-mm-dd')\n" +
                "     group by a.yp_id,a.CD_ID \n" +
                "     union ALL\n" +
                "     --开始时间点的未退药 , 放在销里边负数，为了之前的未退药导致期初数不对，减去销\n" +
                "     select a.yp_id,a.cd_id,-SUM(a.kc_sl) as sl,0 as rksl,0 as rkks,0 sy from bq_tykf_mrjl a,drug_storage b\n" +
                "     where a.yp_id=b.yp_id and a.cd_id=b.cd_id and a.kc_sl<>0 and b.kf_id='3' and '3'='2' and trunc(a.cjsj)=to_date(:in_ksrq,'yyyy-mm-dd')\n" +
                "     group by a.yp_id,a.CD_ID \n" +
                "     UNION ALL\n" +
                "     select a.yp_id,a.cd_id,SUM(a.kc_sl) as sl,0 as rksl,0 as rkks,0 sy from bq_tykf a,drug_storage b\n" +
                "     where a.yp_id=b.yp_id and a.cd_id=b.cd_id and a.kc_sl<>0 and b.kf_id='2' and '3'='2' and trunc(sysdate)=to_date(:in_jsrq,'yyyy-mm-dd')\n" +
                "     group by a.yp_id,a.CD_ID  \n" +
                "     union ALL\n" +
                "    --入库\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,sum(sl/a.pl_xs) as rksl,0 as rkks,0 sy from drug_inoutdepot a,drug_storage b,DICTMANAGE.dict_depot kf\n" +
                "       where a.yp_id=b.yp_id and a.ed_kf=b.kf_id and   ((ED_kf ='2' AND A.LX NOT IN('1','2')) OR (ED_kf='6' AND '6'='2' ))  and a.lx<>'8'and a.cd_id=b.cd_id and st_kf=kf.kf_id \n" +
                "      and a.js_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.js_rq>=to_date(:in_ksrq,'yyyy-mm-dd') and a.del_flag='0'and a.lx not in('6','7')\n" +
                "      group by a.yp_id,a.cd_id \n" +
                "      union ALL\n" +
                "      --出库\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,sum(-1*sl/a.pl_xs) as rksl,0 as rkks,0 sy from drug_inoutdepot a,drug_storage b,DICTMANAGE.dict_depot kf\n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and   ((st_kf ='2' AND A.LX NOT IN('1','2')) OR (ST_KF='6' AND '6'='2' )) and a.lx<>'8'and a.cd_id=b.cd_id and ed_kf=kf.kf_id \n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and a.lx not in('6','7')\n" +
                "      group by a.yp_id,a.cd_id\n" +
                "     --入库科室\n" +
                "      union ALL\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(sl/a.pl_xs)  as rkks,0 sy from drug_inoutdepot a,drug_storage b ,DICTMANAGE.dict_depart bm\n" +
                "      where a.yp_id=b.yp_id and a.ed_kf=b.kf_id and  ed_kf='2'  and lx<>'8'and a.cd_id=b.cd_id and st_kf=bm.bm_id\n" +
                "      and a.js_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.js_rq>=to_date(:in_ksrq,'yyyy-mm-dd') and a.del_flag='0' and lx in('4','3') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      union all\n" +
                "      --出库科室\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(-1*sl/a.pl_xs) as rkks, 0 sy from drug_inoutdepot a,drug_storage b ,DICTMANAGE.dict_depart bm\n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and  st_kF='2' and lx<>'8'and a.cd_id=b.cd_id and ed_kf=bm.bm_id \n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and lx in('4','3') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      --采购入库\n" +
                "      union ALL\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(sl/a.pl_xs)  as rkks,0 sy from drug_inoutdepot a,drug_storage b ,dictmanage.dict_drug_supplier bm\n" +
                "      where a.yp_id=b.yp_id and a.ed_kf=b.kf_id and  ed_kf='2'  and lx<>'8'and a.cd_id=b.cd_id and st_kf=bm.ghs_id\n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd') and a.del_flag='0' and lx in('1') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      union all\n" +
                "      --采购退库\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(-1*sl/a.pl_xs) as rkks, 0 sy from drug_inoutdepot a,drug_storage b ,dictmanage.dict_drug_supplier bm\n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and  st_kF='2' and lx<>'8'and a.cd_id=b.cd_id and ed_kf=bm.ghs_id \n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and lx in('2') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      union ALL\n" +
                "      --损益\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,0 as rkks, sum(1*sl/a.pl_xs) sy from drug_inoutdepot a,drug_storage b \n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and  st_kf='2' and lx<>'8'and a.cd_id=b.cd_id\n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and lx in ('6','7','9')  \n" +
                "      group by a.cd_id,a.yp_id\n" +
                "      \n" +
                "      ) group by yp_id,cd_id\n" +
                "      ) b where  b.CD_ID=kc.cd_id and  b.yp_id=kc.yp_id \n" +
                "      and  kc.kf_id ='2'\n" +
                "      group by yp.yp_mc,cd.sccj_mc,dw.dw_mc,z.PL_XS,yp.gg,yp.bz_bm,b.yp_id,b.cd_id,kc.ls_dj,YP_SPMC,cw.cwh\n" +
                "        )\n" +
                "      select yp_id as \"标准编码\",cwh 仓位号, 药品名称,商品名,生产厂家,规格,单位,\n" +
                "      sum(期初库存) as 期初库存 ,sum(进) as 进,ROUND(sum(销),2) as \"销\",sum(损益) as \"损溢\",sum(领用) as \"领用\",\n" +
                "      sum(期末库存) as \"期末库存\", sum(sl) as\"差\",零售价,yp_id,cd_id\n" +
                "      from ypxx where lx='1'\n" +
                "      group by lx, 药品名称,生产厂家,单位,规格,yp_id,cd_id,零售价,商品名,cwh\n" +
                "having sum(sl)<>'0'" +
                "      order by cwh\n" +
                "\n";
        String s1 = s.replaceAll(":in_jsrq", "'" + jssj + "'");
        s1 = s1.replaceAll(":in_ksrq", "'" + kssj + "'");
        return s1;
    }
    public static String getJxc3(String kfid, String kssj, String jssj) {
        String sql = "\n" +
                "with ypxx as (\n" +
                "\n" +
                "    select '1' as lx,yp.yp_mc as \"药品名称\",cd.sccj_mc as \"生产厂家\",yp.gg as \"规格\",dw.dw_mc as \"单位\",n.yp_spmc as \"商品名\",\n" +
                "       ROUND(sum(nvl((a.SJ_KC)/A.PL_XS*KC.PL_XS,0)),2) as \"期初库存\",\n" +
                " sum(-1*b.fysl*kc.pl_xs) as \"销\",sum(rksl*kc.pl_xs) as \"进\",sum(sy*kc.pl_xs) as \"损益\",sum(rkks*kc.pl_xs) as \"领用\",\n" +
                "ROUND(sum(nvl(nvl(z.SJ_KC/Z.PL_XS*KC.PL_XS,kc.sj_kc),0)),2) as \"期末库存\",\n" +
                "ROUND( ROUND(sum(nvl((a.SJ_KC)/A.PL_XS*KC.PL_XS,0)),2) -sum(b.fysl*kc.pl_xs) +sum(rksl*kc.pl_xs) +sum(sy*kc.pl_xs) -ROUND(sum(nvl(nvl(z.SJ_KC/Z.PL_XS*KC.PL_XS,kc.sj_kc),0)),2)+sum(rkks*kc.pl_xs),2) as sl,b.yp_id,b.cd_id,kc.ls_dj \"零售价\"\n" +
                ",cw.cwh    \n" +
                "from his.drug_storage kc \n" +
                "     left join drug_storage_mrjl a \n" +
                "     on kc.yp_id=a.yp_id and kc.cd_id=a.cd_id and kc.kf_id=a.kf_id and   kc.kf_id=:in_kf  and to_char(a.cjsj,'yyyy-mm-dd')=:in_ksrq\n" +
                "     left join drug_storage_mrjl z \n" +
                "     on kc.yp_id=z.yp_id and kc.cd_id=z.cd_id and  kc.kf_id=z.kf_id and  z.kf_id=:in_kf and to_char(z.cjsj-1,'yyyy-mm-dd')=:in_jsrq\n" +
                "     left join DICTMANAGE.drug_area_list n on a.yp_id=n.yp_id and a.cd_id=n.yp_cd_id\n" +
                "     left join DICTMANAGE.dict_drug_area cd on   kc.CD_ID=cd.sccj_id\n" +
                "     left join DICTMANAGE.dict_pack_unit dw on kc.xsdw_id=dw.dw_id\n" +
                "     left join his.drug_cw cw on cw.kf_id=kc.kf_id and cw.yp_id=kc.yp_id and cw.cd_id=kc.cd_id\n" +
                "     left join DICTMANAGE.drug_info yp on kc.yp_id =yp.yp_id and yp_lx in('1','2'),     \n" +
                "    (\n" +
                "     --发药\n" +
                "     select yp_id ,cd_id,sum(sl) as fysl,sum(rksl) as rksl,sum(rkks) as rkks  ,sum(sy) as  sy from(\n" +
                "     select SFXM_ID as yp_id,a.YPCD_ID as cd_id,SUM(A.SFXM_SL/a.yp_plxs) as sl,0 as rksl,0 as rkks,0 sy from outpat_dispense_detail a,drug_storage b\n" +
                "     where a.FY_STATUS in('2','7') and a.sfxm_id=b.yp_id and a.fyyf_id=b.kf_id and -- a.sfxm_id='7039' and \n" +
                "     a.fy_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.fy_rq>=to_date(:in_ksrq,'yyyy-mm-dd')\n" +
                "     and a.fyyf_id= :in_kf  AND A.YPCD_ID=B.CD_ID\n" +
                "     group by sfxm_id,a.YPCD_ID--,--A.YP_PLXS--,B.pl_xs\n" +
                "     UNION ALL     \n" +
                "     select SFXM_ID as yp_id,a.CD_ID as cd_id,SUM(A.SFXM_SL/a.yp_plxs) as sl,0 as rksl,0 as rkks,0 sy from Inpat_Dispens_Detail a,drug_storage b\n" +
                "     where a.FY_STATUS in('1','2','7') and a.sfxm_id=b.yp_id and a.fy_yfid=b.kf_id and -- a.sfxm_id='7039' and \n" +
                "     a.cjsj<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.cjsj>=to_date(:in_ksrq,'yyyy-mm-dd')\n" +
                "     and a.fy_yfid= :in_kf  AND A.CD_ID=B.CD_ID\n" +
                "     group by sfxm_id,a.CD_ID--,--A.YP_PLXS--,B.pl_xs\n" +
                "     UNION ALL\n" +
                "      --结束时间点的未退药 ，放在销里边正数 ，为了把当天已经写入的发药表数据抵消\n" +
                "     select a.yp_id,a.cd_id,SUM(a.kc_sl) as sl,0 as rksl,0 as rkks,0 sy from bq_tykf_mrjl a,drug_storage b\n" +
                "     where a.yp_id=b.yp_id and a.cd_id=b.cd_id and a.kc_sl<>0 and b.kf_id=:in_kf and '3'=:in_kf and trunc(a.cjsj-1)=to_date(:in_jsrq,'yyyy-mm-dd')\n" +
                "     group by a.yp_id,a.CD_ID \n" +
                "     union ALL\n" +
                "     --开始时间点的未退药 , 放在销里边负数，为了之前的未退药导致期初数不对，减去销\n" +
                "     select a.yp_id,a.cd_id,-SUM(a.kc_sl) as sl,0 as rksl,0 as rkks,0 sy from bq_tykf_mrjl a,drug_storage b\n" +
                "     where a.yp_id=b.yp_id and a.cd_id=b.cd_id and a.kc_sl<>0 and b.kf_id='3' and '3'=:in_kf and trunc(a.cjsj)=to_date(:in_ksrq,'yyyy-mm-dd')\n" +
                "     group by a.yp_id,a.CD_ID \n" +
                "     UNION ALL\n" +
                "     select a.yp_id,a.cd_id,SUM(a.kc_sl) as sl,0 as rksl,0 as rkks,0 sy from bq_tykf a,drug_storage b\n" +
                "     where a.yp_id=b.yp_id and a.cd_id=b.cd_id and a.kc_sl<>0 and b.kf_id=:in_kf and '3'=:in_kf and trunc(sysdate)=to_date(:in_jsrq,'yyyy-mm-dd')\n" +
                "     group by a.yp_id,a.CD_ID  \n" +
                "     union ALL\n" +
                "    --入库\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,sum(sl/a.pl_xs) as rksl,0 as rkks,0 sy from drug_inoutdepot a,drug_storage b,DICTMANAGE.dict_depot kf\n" +
                "       where a.yp_id=b.yp_id and a.ed_kf=b.kf_id and   ((ED_kf =:in_kf AND A.LX NOT IN('1','2')) OR (ED_kf='6' AND '6'=:in_kf ))  and a.lx<>'8'and a.cd_id=b.cd_id and st_kf=kf.kf_id \n" +
                "      and a.js_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.js_rq>=to_date(:in_ksrq,'yyyy-mm-dd') and a.del_flag='0'and a.lx not in('6','7')\n" +
                "      group by a.yp_id,a.cd_id \n" +
                "      union ALL\n" +
                "      --出库\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,sum(-1*sl/a.pl_xs) as rksl,0 as rkks,0 sy from drug_inoutdepot a,drug_storage b,DICTMANAGE.dict_depot kf\n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and   ((st_kf =:in_kf AND A.LX NOT IN('1','2')) OR (ST_KF='6' AND '6'=:in_kf )) and a.lx<>'8'and a.cd_id=b.cd_id and ed_kf=kf.kf_id \n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and a.lx not in('6','7')\n" +
                "      group by a.yp_id,a.cd_id\n" +
                "     --入库科室\n" +
                "      union ALL\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(sl/a.pl_xs)  as rkks,0 sy from drug_inoutdepot a,drug_storage b ,DICTMANAGE.dict_depart bm\n" +
                "      where a.yp_id=b.yp_id and a.ed_kf=b.kf_id and  ed_kf=:in_kf  and lx<>'8'and a.cd_id=b.cd_id and st_kf=bm.bm_id\n" +
                "      and a.js_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.js_rq>=to_date(:in_ksrq,'yyyy-mm-dd') and a.del_flag='0' and lx in('4','3') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      union all\n" +
                "      --出库科室\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(-1*sl/a.pl_xs) as rkks, 0 sy from drug_inoutdepot a,drug_storage b ,DICTMANAGE.dict_depart bm\n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and  st_kF=:in_kf and lx<>'8'and a.cd_id=b.cd_id and ed_kf=bm.bm_id \n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and lx in('4','3') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      --采购入库\n" +
                "      union ALL\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(sl/a.pl_xs)  as rkks,0 sy from drug_inoutdepot a,drug_storage b ,dictmanage.dict_drug_supplier bm\n" +
                "      where a.yp_id=b.yp_id and a.ed_kf=b.kf_id and  ed_kf=:in_kf  and lx<>'8'and a.cd_id=b.cd_id and st_kf=bm.ghs_id\n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd') and a.del_flag='0' and lx in('1') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      union all\n" +
                "      --采购退库\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,sum(-1*sl/a.pl_xs) as rkks, 0 sy from drug_inoutdepot a,drug_storage b ,dictmanage.dict_drug_supplier bm\n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and  st_kF=:in_kf and lx<>'8'and a.cd_id=b.cd_id and ed_kf=bm.ghs_id \n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and lx in('2') \n" +
                "      group by a.yp_id,a.cd_id\n" +
                "      union ALL\n" +
                "      --损益\n" +
                "      select a.yp_id,a.cd_id,0 as fysl,0 as rksl,0 as rkks, sum(1*sl/a.pl_xs) sy from drug_inoutdepot a,drug_storage b \n" +
                "      where a.yp_id=b.yp_id and a.st_kf=b.kf_id and  st_kf=:in_kf and lx<>'8'and a.cd_id=b.cd_id\n" +
                "      and a.sh_rq<to_date(:in_jsrq,'yyyy-mm-dd')+1 and a.sh_rq>=to_date(:in_ksrq,'yyyy-mm-dd')  and a.del_flag='0' and lx in ('6','7','9')  \n" +
                "      group by a.cd_id,a.yp_id\n" +
                "      \n" +
                "      ) group by yp_id,cd_id\n" +
                "      ) b where  b.CD_ID=kc.cd_id and  b.yp_id=kc.yp_id \n" +
                "      and  kc.kf_id =:in_kf\n" +
                "      group by yp.yp_mc,cd.sccj_mc,dw.dw_mc,z.PL_XS,yp.gg,yp.bz_bm,b.yp_id,b.cd_id,kc.ls_dj,YP_SPMC,cw.cwh\n" +
                "        )\n" +
                "      select yp_id as \"bzbm\",cwh \"cwh\", 药品名称 \"ypmc\",商品名 \"spm\",生产厂家 \"sccj\",规格 \"gg\",单位 \"dw\",\n" +
                "      sum(期初库存) as \"qckc\" ,round(sum(进),2) as \"j\",round(sum(销),2) as \"x\",round(sum(损益),2) as \"sy\",round(sum(领用),2) as \"ly\",\n" +
                "      sum(期末库存) as \"qmkc\", sum(sl) as \"c\",零售价 \"lsj\",yp_id,cd_id\n" +
                "      from ypxx where lx='1'\n" +
                "      group by lx, 药品名称,生产厂家,单位,规格,yp_id,cd_id,零售价,商品名,cwh\n" +
                "      order by cwh\n";
        sql = sql.replaceAll(":in_kf", "'" + kfid + "'");
        sql = sql.replaceAll(":in_jsrq", "'" + jssj + "'");
        sql = sql.replaceAll(":in_ksrq", "'" + kssj + "'");
        return sql;
    }
    public static String getDrugKcInfo(String ypid, String cdid, String kfid) {
        String sql = "SELECT kc.pl_xs,kc.xsdw_id,kc.jl,kc.jldw,kc.pl_xs,KC.ZXBZL,KC.ZXBZDW FROM drug_storage kc " +
                "where kc.yp_id='" + ypid + "' and kc.cd_id ='" + cdid + "' and kc.kf_id='" + kfid + "'";
        return sql;
    }
    public static String getDrugIn(String rq, String ypid, String cdid, String kfid) {
        String sql = "select max(a.js_rq),a.jh_dj,a.pf_dj,a.ls_dj\n" +
                " from his.drug_inoutdepot a\n" +
                "WHERE \n" +
                "a.js_rq<to_date('" + rq + "','yyyy-mm-dd')+1 \n" +
                "and a.yp_id='" + ypid + "' and a.cd_id='" + cdid + "'\n" +
                "AND DEL_FLAG ='0' and a.ed_kf ='" + kfid + "' \n" +
                "group by a.jh_dj,a.pf_dj,a.ls_dj";
        return sql;
    }
    public static String getDrugStoMrjl(String kfid, String rq, String ypid, String cdid) {
        String sql = "SELECT * FROM  his.DRUG_STORAGE_MRJL kc" +
                " where kc.yp_id='" + ypid + "' " +
                "and kc.cd_id='" + cdid + "' " +
                "and kc.kf_id='" + kfid + "' \n" +
                "and to_char(kc.cjsj-1,'yyyy-mm-dd')='" + rq + "'";
        return sql;
    }
    public static String drugStoMrjlUpdate(String kfid, String ypid, String cdid, String jlid, String sjkc, String xnkc) {
        String sql = "UPDATE \n" +
                "his.DRUG_STORAGE_MRJL kc \n" +
                "SET KC.SJ_KC = '" + sjkc + "',KC.XN_KC = '" + xnkc + "'\n" +
                "WHERE KC.CJR ='YYP' AND  KC.JL_ID='" + jlid + "' AND KC.YP_ID ='" + ypid + "' AND KC.CD_ID ='" + cdid + "' AND KC.KF_ID='" + kfid + "'";
        return sql;
    }
    public static String addDrugStoMrjl(Map<String, String> map) {
        String sql = "insert into his.DRUG_STORAGE_MRJL\n" +
                "  (JL_ID,\n" +
                "   YP_ID,\n" +
                "   KF_ID,\n" +
                "   CD_ID,\n" +
                "   PL_XS,\n" +
                "   LS_DJ,\n" +
                "   SJ_KC,\n" +
                "   XN_KC,\n" +
                "   CWH,\n" +
                "   KF_BZ,\n" +
                "   SP_BZ,\n" +
                "   BY_BZ,\n" +
                "   DC_SL,\n" +
                "   GC_SL,\n" +
                "   YXBZ,\n" +
                "   ZXBZL,\n" +
                "   ZXBZDW,\n" +
                "   JL,\n" +
                "   JLDW,\n" +
                "   GYTJ,\n" +
                "   GYPL,\n" +
                "   DJ_SL,\n" +
                "   DZ_YP,\n" +
                "   XSDW_ID,\n" +
                "   JL_RQ,\n" +
                "   CJSJ,\n" +
                "   CJR,\n" +
                "   ZHGXSJ,\n" +
                "   ZHGXR,\n" +
                "   pf_dj,\n" +
                "   jh_dj)\n" +
                "  select ypkc_SEQ.nextval,\n" +
                "         '" + map.get("ypid") + "' ypid,\n" +
                "         '" + map.get("kfid") + "' kfid,\n" +
                "         '" + map.get("cdid") + "' cdid,\n" +
                "         '" + map.get("plxs") + "' plxs,\n" +
                "          " + map.get("lsdj") + " lsdj,\n" +
                "         '" + map.get("sjkc") + "' sjkc,\n" +
                "         '" + map.get("xnkc") + "' xnkc,\n" +
                "         '' cwh,\n" +
                "         0 KF_BZ,\n" +
                "         0 SP_BZ,\n" +
                "         0 BY_BZ,\n" +
                "         0 DC_SL,\n" +
                "         0 GC_SL,\n" +
                "         0 YXBZ,\n" +
                "         '" + map.get("zxbzl") + "' ZXBZL,\n" +
                "         '" + map.get("zxbzdw") + "' ZXBZDW,\n" +
                "         '" + map.get("jl") + "' JL,\n" +
                "         '" + map.get("jldw") + "' JLDW,\n" +
                "         '' GYTJ,\n" +
                "         '' GYPL,\n" +
                "         '0' DJ_SL,\n" +
                "         '0' DZ_YP,\n" +
                "         '" + map.get("xsdwid") + "' XSDW_ID,\n" +
                "         sysdate jl_rq,\n" +
                "         to_date('" + map.get("cjsj") + "','yyyy-mm-dd hh24:mi:ss') cjsj,\n" +
                "         'YYP' CJR,\n" +
                "         to_date('" + map.get("zhgxsj") + "','yyyy-mm-dd hh24:mi:ss')   zhgxsj,\n" +
                "         'YYP' ZHGXR,\n" +
                "         " + map.get("pfdj") + " pf_dj,\n" +
                "         " + map.get("jhdj") + " jh_dj\n" +
                "    from dual";
        return sql;
    }
    @Test
    public void generateClass() throws IOException {
        ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
        LinkedList<String> excelTitle = excelUtils.getExcelTitle("D:\\公司\\项目文件\\康复医院\\药房数据导出\\2024-12-04\\进销存.xlsx");
        excelUtils.generateClassZ("JxcPojo", excelTitle, ApplicationUtils.pathPojoP + "\\jxc");
    }
}
