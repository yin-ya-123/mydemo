package com.yin.utils.excel.main.kangfu;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.analysis.ExcelReadExecutor;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.yin.MyDemoApplication;
import com.yin.common.ApplicationUtils;
import com.yin.common.err.YException;
import com.yin.pojo.qt.kangfu.JnkfPojo;
import com.yin.pojo.qt.kangfu.ZcZj2025Pojo;
import com.yin.pojo.qt.kangfu.ZcZj2025YBSCPojo;
import com.yin.pojo.qt.kangfu.ZcZj2025YBSCPojoTwo;
import com.yin.service.OracleService;
import com.yin.utils.ConvertAllUtils;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelListener.DataListener;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.main.kangfu.sql.ZcZj2025Sql;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yin
 * @date 2025-02-19
 * @Description 自查自纠2025
 */
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ZcZj2025 {
    static ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
    private final int MAX = 700;
    @Autowired
    private OracleService hisOracleServiceImpl;
    @Autowired
    private OracleService oracleServiceImpl2;



    @Test
    public void show() {
        patXX();
    }

    @Test
    public void excelToaLL() throws YException {
        String savePath = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-18医保规则数据导出\\excelCl";
        File file = new File(savePath);
//        excelUtils.excelToAll(savePath,savePath+"\\all.xlsx",new ZcZj2025Pojo());
    }

    public void patXX() {
        String savePath = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-18医保规则数据导出";
        String path = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-18医保规则数据导出\\excel";
        String pathTest = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-18医保规则数据导出\\测试";
        String pathTestSave = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-02-18医保规则数据导出\\测试";
        ExcelUtils<ZcZj2025Pojo> zcZj2025PojoExcelUtils = ExcelUtils.of(ZcZj2025Pojo.class);
        File file = new File(pathTest);
        ArrayList<ZcZj2025Pojo> zcZj2025Pojos1 = new ArrayList<>();
        //先获取所有信息缓存到map中
        HashMap<String, Object> patZdxxYbxxAll = getPatZdxxYbxxAll();
        for (File listFile : file.listFiles()) {
            long start = System.currentTimeMillis();
            if(!FileUtils.getFileSuffix(listFile.getAbsolutePath()).equals("xlsx")){
                continue;
            }
            log.info("开始处理:{}", listFile.getAbsolutePath());
            List<ZcZj2025Pojo> zcZj2025Pojos = zcZj2025PojoExcelUtils.readExcel(listFile.getAbsolutePath(), 1);
            patXXxg(zcZj2025Pojos, patZdxxYbxxAll);
            String newSaveFile = pathTestSave + "\\excelCl\\" + FileUtils.getFileName(listFile.getAbsolutePath()) + ".xlsx";
//            System.out.println(newSaveFile);
            FileUtils.createFile(newSaveFile);
            zcZj2025PojoExcelUtils.writeExcel(newSaveFile, zcZj2025Pojos);
            long end = System.currentTimeMillis();
            log.info("数据处理成功:写入路径:{},耗时:{}/s", newSaveFile, (end - start) / 1000);
            zcZj2025Pojos1.addAll(zcZj2025Pojos);
            log.info("------------------------------------");
        }
    }


    public void patXXxg(List<ZcZj2025Pojo> zcZj2025Pojos, HashMap<String, Object> stringObjectHashMap) {
        long start = System.currentTimeMillis();
//        log.info("数据查询完毕，已缓存成功:{},耗时:{}/s", stringObjectHashMap.size(), (end - start) / 1000);
//        处理数据，进行数据赋值
        log.info("进行数据赋值处理:{}条数据", zcZj2025Pojos.size());
        for (ZcZj2025Pojo zcZj2025Pojo : zcZj2025Pojos) {
            patXxXg2(stringObjectHashMap, zcZj2025Pojo);
        }
        long end = System.currentTimeMillis();
        log.info("数据赋值处理完毕，耗时:{}/s", (end - start) / 1000);
    }

    public void patXxMap(List zdxxs, List ybxxs, HashMap<String, Object> stringObjectHashMap) {
        for (Object zdxx : zdxxs) {
            JSONObject zdxx1 = JSONUtil.parseObj(zdxx);
            String zylsh = zdxx1.get("ZYLSH").toString();
            stringObjectHashMap.put(zylsh + "zd", zdxx);
        }
        for (Object ybxx : ybxxs) {
            JSONObject ybxx1 = JSONUtil.parseObj(ybxx);
            String zylsh = ybxx1.get("ZYLSH").toString();
            stringObjectHashMap.put(zylsh + "yb", ybxx);
        }
    }

    public void patXxXg2(HashMap<String, Object> dataMap, ZcZj2025Pojo zcZj2025Pojo) {
        JSONObject zdxx = JSONUtil.parseObj(dataMap.get(zcZj2025Pojo.getZYLSH() + "zd"));
        JSONObject ybxx = JSONUtil.parseObj(dataMap.get(zcZj2025Pojo.getZYLSH() + "yb"));
        if (zdxx != null && ybxx != null) {
            String zd = zdxx.get("ZD").toString();
            String zje = ybxx.get("总金额").toString();
            String ybtcje = ybxx.get("医保统筹金额").toString();
            String zfje = ybxx.get("自付金额").toString();
            String jsid = ybxx.get("结算ID").toString();
            String jzid = ybxx.get("就诊ID").toString();
            String rybh = ybxx.get("人员编号").toString();
            zcZj2025Pojo.setZd2(zd);
            zcZj2025Pojo.setZd(zd.length() > 500 ? zd.substring(0, 500) : zd);
            zcZj2025Pojo.setZje(zje);
            zcZj2025Pojo.setYbtcje(ybtcje);
            zcZj2025Pojo.setZfje(zfje);
            zcZj2025Pojo.setJsID(jsid);
            zcZj2025Pojo.setJzID(jzid);
            zcZj2025Pojo.setRybh(rybh);
        }
    }

    public HashMap<String, Object> getPatZdxxYbxxAll() {
        long start = System.currentTimeMillis();
        log.info("开始缓存患者信息");
        String patZdxx = ZcZj2025Sql.getPatZdxxAll();
        String patYbxx = ZcZj2025Sql.getPatYbxxAll();
        List zdxxs = hisOracleServiceImpl.selectAll(patZdxx);
        List ybxxs = hisOracleServiceImpl.selectAll(patYbxx);
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        patXxMap(zdxxs, ybxxs, stringObjectHashMap);
        long end = System.currentTimeMillis();
        log.info("缓存全部患者信息成功:{},{},{}/s", zdxxs.size(), ybxxs.size(), (end - start) / 1000);
        return stringObjectHashMap;
    }
    @Test
    public void generate() throws IOException {
        String path = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-12\\自查自纠\\上传数据导出\\将含湿化液的鼻导管或面罩用于氧气吸入并纳入医保基金支付.xlsx";
        excelUtils.generateClassEnhance("ZcZj2025YBSCPojoTwo", ApplicationUtils.pathPojoKangFu, path);
    }

    @Test
    public void exportZyfymx(){
        String zcZjPat = ZcZj2025Sql.getZcZjPat("5");
        List selectAll = hisOracleServiceImpl.selectAll(zcZjPat);
        ArrayList<ZcZj2025Pojo> zcZj2025Pojos = new ArrayList<>();
        ExcelUtils<ZcZj2025Pojo> zcZj2025PojoExcelUtils = ExcelUtils.of(ZcZj2025Pojo.class);
        String saveFile = "D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-09\\老年康复中心\\zyfymx\\表2.xlsx";
        long startTime = Instant.now().toEpochMilli();
        for (Object o : selectAll) {
            JSONObject jsonObject = JSONUtil.parseObj(o);
            String zylsh = jsonObject.get("ZYLSH").toString();
            System.out.println(zylsh);
            String zcZjPatZyfymx = ZcZj2025Sql.getZcZjPatZyfymx(zylsh);
            List selectAll1 = hisOracleServiceImpl.selectAll(zcZjPatZyfymx);
            ArrayList<ZcZj2025Pojo> zcZj2025Pojos1 = ConvertAllUtils.convertListAll(selectAll1, ZcZj2025Pojo.class);
            zcZj2025Pojos.addAll(zcZj2025Pojos1);
        }
//        System.out.println(zcZj2025Pojos);
        zcZj2025PojoExcelUtils.writeExcel(saveFile,zcZj2025Pojos);
        System.out.println("用时:"+(Instant.now().toEpochMilli()-startTime)+"毫秒");
    }


    @Test
    public void test() throws YException {
//        String file="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-09\\老年康复中心\\zyfymx";
//        String saveFile =file+"\\汇总.xlsx";
//        ExcelUtils<ZcZj2025Pojo> zcZj2025PojoExcelUtils = ExcelUtils.of(ZcZj2025Pojo.class);
//        zcZj2025PojoExcelUtils.excelToAll(file, saveFile,new ZcZj2025Pojo());
        String path ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-09\\最终数据汇总";
        String saveFile =path+"\\汇总.xlsx";
        ExcelUtils<ZcZj2025YBSCPojo> zcZj2025YBSCPojoExcelUtils = ExcelUtils.of(ZcZj2025YBSCPojo.class);
        zcZj2025YBSCPojoExcelUtils.excelToAll(path, saveFile,new ZcZj2025YBSCPojo());
    }

    @Test
    public void test2() {
        String zDxx = ZcZj2025Sql.getZDxx();
        String gwyc = ZcZj2025Sql.getGwyc();
        List list = hisOracleServiceImpl.selectAll(gwyc);
    }

    @Test
    public void test3() {
        String savePath ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-14\\江南康复\\new";
        String wtlx = ZcZj2025Sql.getWtlx2();
        List list = oracleServiceImpl2.selectAll(wtlx);
        ExcelUtils<ZcZj2025YBSCPojoTwo> zcZj2025YBSCPojoTwoExcelUtils = ExcelUtils.of(ZcZj2025YBSCPojoTwo.class);
        for (Object o : list) {
            double num = 0;
            JSONObject jsonObject = JSONUtil.parseObj(o);
            String string = jsonObject.get("问题序号").toString();
//            String rs = jsonObject.get("人数").toString();
            String wtlxAll = ZcZj2025Sql.getWtlxAll2(string);
            List list1 = oracleServiceImpl2.selectAll(wtlxAll);
            ArrayList<ZcZj2025YBSCPojoTwo> zcZj2025YBSCPojoTwos = ConvertAllUtils.convertListAll(list1, ZcZj2025YBSCPojoTwo.class);
            for (int i = 0; i < zcZj2025YBSCPojoTwos.size(); i++) {
                num += Double.parseDouble(zcZj2025YBSCPojoTwos.get(i).get金额());
            }
            BigDecimal bd = new BigDecimal(num);
            BigDecimal bigDecimal = bd.setScale(2, RoundingMode.HALF_DOWN);
            String format = String.format("%s-金额(%s)", string, bigDecimal);
            String save = savePath + "\\"+format+".xlsx";
            zcZj2025YBSCPojoTwoExcelUtils.writeExcel(save,zcZj2025YBSCPojoTwos);
        }
    }
    @Test
    public void test4() {
        String path ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-13\\附件1：定点医疗机构违法违规使用医疗保障基金典型问题清单（2025浙江版）20250312-1.xlsx";
        String saveFile ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-13\\附件1：定点医疗机构违法违规使用医疗保障基金典型问题清单（2025浙江版）-汇总.xlsx";
        ExcelUtils<ZcZj2025YBSCPojo> zcZj2025YBSCPojoTwoExcelUtils = ExcelUtils.of(ZcZj2025YBSCPojo.class);
        HashMap<String, List<ZcZj2025YBSCPojo>> stringListHashMap = zcZj2025YBSCPojoTwoExcelUtils.readeExcelSheetAll(path, 1, "汇总", "Sheet1");
        List<ZcZj2025YBSCPojo> zcZj2025YBSCPojoTwos = stringListHashMap.get("临床检验 序号19");
        zcZj2025YBSCPojoTwoExcelUtils.writeExcel(saveFile,zcZj2025YBSCPojoTwos);
    }

    @Test
    public void test5() throws IOException, InterruptedException {
        String path ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-14\\江南康复\\江南康复自查自纠汇总版.xlsx";
        String pat2 ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-14\\江南康复\\工作簿1.xlsx";
        String savePath ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-14\\江南康复\\江南康复自查自纠汇总版-汇总.xlsx";
        ExcelReader build = EasyExcel.read(path).build();
        ExcelReadExecutor excelReadExecutor = build.excelExecutor();
        List<ReadSheet> readSheets = excelReadExecutor.sheetList();
        ExcelUtils<JnkfPojo> jnkfPojoExcelUtils = ExcelUtils.of(JnkfPojo.class);
        ArrayList<JnkfPojo> jnkfPojos1 = new ArrayList<>();
        for (ReadSheet readSheet : readSheets) {
            List<JnkfPojo> excelList1 = ListUtils.newArrayList();
            ExcelReaderSheetBuilder sheet = EasyExcel.read(path, JnkfPojo.class, new DataListener()).sheet(readSheet.getSheetNo(), readSheet.getSheetName());
            List<Object> objects = sheet.doReadSync();
            ArrayList<JnkfPojo> jnkfPojos = ConvertAllUtils.convertListAll(objects, JnkfPojo.class);
            for (JnkfPojo jnkfPojo : jnkfPojos) {
                jnkfPojo.setBm(readSheet.getSheetName());
            }
//            jnkfPojos1.addAll(jnkfPojos);
        }
//        jnkfPojoExcelUtils.writeExcel(savePath, jnkfPojos1);

    }
}
