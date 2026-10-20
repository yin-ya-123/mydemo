package com.yin.utils.excel.main.pinghueryuan.jimu.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.yin.MyDemoApplication;
import com.yin.service.impl.PhOracleServiceImpl;
import com.yin.utils.excel.main.pinghueryuan.jimu.entity.JimuReport;
import com.yin.utils.excel.main.pinghueryuan.jimu.entity.JimuReportDb;
import com.yin.utils.excel.main.pinghueryuan.jimu.entity.JimuReportDbField;
import com.yin.utils.excel.main.pinghueryuan.jimu.entity.JimuReportDbParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class JiMuReportExport {

    @Resource
    private PhOracleServiceImpl phOracleServiceImpl;

    @Resource
    private JimuReportSqlService jimuReportSqlService;

    @Test
    public void main() {
        String jimuReportId = "1258284423297847296";
        // 设置转换规则：下划线转驼峰 + 忽略大小写
        CopyOptions options = CopyOptions.create()
                .setIgnoreCase(true)
                .setFieldNameEditor(StrUtil::toCamelCase);   // 下划线 → 驼峰
        List<JimuReport> jimuReports = BeanUtil.copyToList(phOracleServiceImpl.selectAll(jimuReportSqlService.getJimuReportSql(jimuReportId)), JimuReport.class, options);
        for (JimuReport jimuReport : jimuReports) {
            String jimuReportInsertSql = jimuReportSqlService.getJimuReportInsertSql(jimuReport);
//            System.out.println(jimuReport.getJsonStr());
            System.out.println("--============" + jimuReport.getId() + "============");
            System.out.println(jimuReportInsertSql);
            //获取数据源
            List<JimuReportDb> jimuReportDbs = BeanUtil.copyToList(phOracleServiceImpl.selectAll(jimuReportSqlService.getJimuReportDbSql(jimuReport.getId())), JimuReportDb.class, options);
            for (JimuReportDb jimuReportDb : jimuReportDbs) {
                //生产数据源的插入
                String jimuReportDbInsertSql = jimuReportSqlService.getJimuReportDbInsertSql(jimuReportDb);
                System.out.println(jimuReportDbInsertSql);
                //获取数据源对于的字段
                List<JimuReportDbField> jimuReportDbFields = BeanUtil.copyToList(phOracleServiceImpl.selectAll(jimuReportSqlService.getJimuReportDbFieldSql(jimuReportDb.getId())), JimuReportDbField.class, options);
                //生成数据源对于字段的插入
                for (JimuReportDbField jimuReportDbField : jimuReportDbFields) {
                    String jimuReportDbFieldInsertSql = jimuReportSqlService.getJimuReportDbFieldInsertSql(jimuReportDbField);
                    System.out.println(jimuReportDbFieldInsertSql);
                }
                //获取数据源对应的参数条件
                List<JimuReportDbParam> jimuReportDbParams = BeanUtil.copyToList(phOracleServiceImpl.selectAll(jimuReportSqlService.getJimuReportDbParamSql(jimuReportDb.getId())), JimuReportDbParam.class, options);
                //生成数据源对应的参数条件插入
                for (JimuReportDbParam jimuReportDbParam : jimuReportDbParams) {
                    String jimuReportDbParamInsertSql = jimuReportSqlService.getJimuReportDbParamInsertSql(jimuReportDbParam);
                    System.out.println(jimuReportDbParamInsertSql);
                }
            }
        }
    }
}
