package com.yin.utils.excel.main.pinghu.jxc;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yin.MyDemoApplication;
import com.yin.utils.excel.main.pinghu.jxc.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class Main {

    @Resource
    private getData getData;

    @Test
    public void test() {
        List<Depot> depotSEntity = getData.getDepotSEntity();
        log.info("查询到仓库信息列表，数量: {}, 详情: {}", depotSEntity.size(), JSONUtil.toJsonStr(depotSEntity));
        String startTime = "2026-10-01 00:00:00";
        String endTime = "2026-10-19 23:59:59";
        Param param = new Param();
        param.setStartTime(startTime);
        param.setEndTime(endTime);
        for (Depot depot : depotSEntity) {
            param.setDepotCode(depot.getDepotCode());
            log.info("参数: {}", param.toString());
            List<WeiChaTotal> drygInfoEntity = getData.getDrygInfoEntity(param);
            log.info("总尾差查询成功，仓库编码: {}, 仓库名称: {}, 查询结果数量: {}, 详情: {}", depot.getDepotCode(), depot.getDepotName(), drygInfoEntity.size(), JSONUtil.toJsonStr(drygInfoEntity));
            for (WeiChaTotal weiChaTotal : drygInfoEntity) {
                List<WeiChaDrug> drugInfoDetailsEntity = getData.getDrugInfoDetailsEntity(param);
                log.info("药品查询成功，药品数量: {}, 详情: {}", drugInfoDetailsEntity.size(), JSONUtil.toJsonStr(drugInfoDetailsEntity));
                for (WeiChaDrug weiChaDrug : drugInfoDetailsEntity) {
                    param.setDrugCode(weiChaDrug.getDrugId());
                    List<WeiChaDate> drugInfoDetailsRqEntity = getData.getDrugInfoDetailsRqEntity(param);
                    log.info("日期查询成功，药品ID: {}, 药品名称: {}, 日期记录数量: {}, 详情: {}", weiChaDrug.getDrugId(), weiChaDrug.getDrugName(), drugInfoDetailsRqEntity.size(), JSONUtil.toJsonStr(drugInfoDetailsRqEntity));
                    // 次数：负数的个数
                    long count = drugInfoDetailsRqEntity.stream()
                            .filter(e -> e.getWeiChaShouJia() != null
                                    && e.getWeiChaShouJia().compareTo(BigDecimal.ZERO) != 0)
                            .count();
                    if (count == 1) {
                        log.info("药品ID: {}, 药品名称: {}, 存在尾差不等于0的次数: {}", weiChaDrug.getDrugId(), weiChaDrug.getDrugName(), count);
                        for (WeiChaDate weiChaDate : drugInfoDetailsRqEntity) {
                            if (weiChaDate.getWeiChaShouJia().compareTo(BigDecimal.ZERO) != 0) {
                                log.info("医院名称: {}, 存在尾差药品: {}, 药品ID: {}, 日期: {}, 尾差售价: {}, 尾差数量: {}", weiChaTotal.getYymc(), weiChaDrug.getDrugName(), weiChaDrug.getDrugId(), weiChaDate.getRq(), weiChaDate.getWeiChaShouJia(), weiChaDate.getShuLiangWeiCha());
                                if (weiChaDate.getWeiChaShouJia().compareTo(BigDecimal.ZERO) < 0) {
                                    //生成扣减语句，查询库存信息，新增对象
                                    Param kcPar = new Param();
                                    kcPar.setStartTime(weiChaDate.getRq().toString());
                                    kcPar.setDepotCode(depot.getDepotCode());
                                    kcPar.setDrugCode(weiChaDate.getDrugId());
                                    List<Kcxx> kCxxEntity = getData.getKCxxEntity(kcPar);
                                    //计算有尾插的那天距离今天相差多少
                                    ZoneId zone = ZoneId.of("Asia/Shanghai");
                                    LocalDate target = weiChaDate.getRq().toInstant()
                                            .atZone(zone)
                                            .toLocalDate();
                                    long days = ChronoUnit.DAYS.between(target, LocalDate.now(zone));
                                    log.info("库存信息查询成功，药品ID: {}, 查询日期: {}, 库存记录数量: {}, 详情: {}", weiChaDate.getDrugId(), weiChaDate.getRq(), kCxxEntity.size(), JSONUtil.toJsonStr(kCxxEntity));
                                    // 按日期分组
                                    Map<String, List<Kcxx>> dateGroupMap = kCxxEntity.stream().collect(
                                            Collectors.groupingBy(item -> item.getRecordDate())
                                    );
                                    LocalDate today1 = LocalDate.now();
                                    String dateStr1 = today1.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    System.out.println("\n--药品名称: " + weiChaDrug.getDrugName() + ",药品ID:" + weiChaDrug.getDrugId());
                                    System.out.println("--医院名称-库房名称: " + weiChaTotal.getYymc() + "-" + depot.getDepotName());
                                    System.out.println("--"+dateStr1+"-"+ weiChaTotal.getYymc()+"-"+depot.getDepotName()+"-"+weiChaDrug.getDrugName());
                                    System.out.println("--需要扣减数量: " + weiChaDate.getShuLiangWeiCha().abs());

                                    BigDecimal needDeduct = weiChaDate.getShuLiangWeiCha().abs();
                                    BigDecimal remainingDeduct = needDeduct;

                                    // 从尾差日期开始遍历每一天
                                    LocalDate currentDate = target;
                                    LocalDate today = LocalDate.now(zone);

                                    while (!currentDate.isAfter(today) && remainingDeduct.compareTo(BigDecimal.ZERO) > 0) {
                                        String dateStr = currentDate.toString();
                                        List<Kcxx> dateKcxxList = dateGroupMap.get(dateStr);

                                        if (dateKcxxList != null && !dateKcxxList.isEmpty()) {
                                            // 按批次分组并自动排序（使用TreeMap）
                                            Map<String, List<Kcxx>> batchGroupMap = dateKcxxList.stream().collect(
                                                    Collectors.groupingBy(
                                                            item -> item.getBatchNo(), // 1. 分组键：批号
                                                            java.util.TreeMap::new,  // 2. 容器：TreeMap（键有序）
                                                            Collectors.toList() // 3. 每组的值：List<Kcxx>
                                                    )
                                            );

                                            // 在该日期内按批次查找够扣的批次
                                            boolean foundBatch = false;
                                            for (Map.Entry<String, List<Kcxx>> batchEntry : batchGroupMap.entrySet()) {
                                                String batchNo = batchEntry.getKey();
                                                List<Kcxx> kcxxList = batchEntry.getValue();

                                                // 计算该批次在该日期的总库存
                                                BigDecimal batchStorage = kcxxList.stream()
                                                        .map(Kcxx::getRealStorage)
                                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                                //判断这个批次的库存是否够扣减
                                                if (batchStorage.compareTo(remainingDeduct) >= 0) {
                                                    // 该批次够扣，执行扣减
                                                    foundBatch = true;
                                                    System.out.println("\n--日期: " + dateStr + ", 批次号: " + batchNo + ", 批次库存: " + batchStorage + ", 剩余需扣减: " + remainingDeduct);
                                                    for (Kcxx kcxx : kcxxList) {
                                                        String recordId = kcxx.getRecordId();
                                                        String iniId = kcxx.getIniId();
                                                        String recordDate = kcxx.getRecordDate();
                                                        BigDecimal realStorage = kcxx.getRealStorage();
                                                        String depotId = kcxx.getDepotId();
                                                        String drugId = kcxx.getDrugId();
                                                        // 够扣减的批次库存进行扣减
                                                        BigDecimal subtract = realStorage.subtract(remainingDeduct);
                                                        if (!recordId.equals("-")) {
                                                            String sql1 = "update his.drug_storage_detail_mrjl m set m.real_storage =" + subtract.toString() +
                                                                    " where m.record_id ='" + recordId + "' and m.depot_id ='" + depotId + "' and m.drug_id = '" + drugId + "' and m.record_date =date'" + recordDate +
                                                                    "' AND M.BATCH_NO = '" + batchNo + "';";
                                                            String rollbackSql1 = "update his.drug_storage_detail_mrjl m set m.real_storage =" + realStorage.toString() +
                                                                    " where m.record_id ='" + recordId + "' and m.depot_id ='" + depotId + "' and m.drug_id = '" + drugId + "' and m.record_date =date'" + recordDate +
                                                                    "' AND M.BATCH_NO = '" + batchNo + "';";
                                                            System.out.println("\t\n--每日记录: 日期: " + recordDate + ", 药品ID: " + drugId + ", 批次: " + batchNo + ", 原始库存: " + realStorage + ", 本次扣减: " + remainingDeduct + ", 修改后库存: " + subtract);
                                                            System.out.println(sql1);
                                                            System.out.println("--回滚SQL: " + rollbackSql1);
                                                        } else if (!iniId.equals("-")) {
                                                            String slq1 = "update his.drug_storage_detail m set m.real_storage =" + subtract.toString() +
                                                                    " where m.ini_id ='" + iniId + "' and m.depot_id ='" + depotId + "' and m.drug_id = '" + drugId + "' AND M.BATCH_NO = '" + batchNo + "';";
                                                            String rollbackSql2 = "update his.drug_storage_detail m set m.real_storage =" + realStorage.toString() +
                                                                    " where m.ini_id ='" + iniId + "' and m.depot_id ='" + depotId + "' and m.drug_id = '" + drugId + "' AND M.BATCH_NO = '" + batchNo + "';";
                                                            System.out.println("\t\n--库存记录: 日期: " + recordDate + ", 药品ID: " + drugId + ", 批次: " + batchNo + ", 原始库存: " + realStorage + ", 本次扣减: " + remainingDeduct + ", 修改后库存: " + subtract);
                                                            System.out.println(slq1);
//                                                            System.out.println("--回滚SQL: " + rollbackSql2);
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                            if (!foundBatch) {
                                                log.error("--日期: {}, 该日期所有批次都不够扣，继续下一天", dateStr);
                                            }
                                        } else {
                                            log.error("--日期: {}, 该日期无库存记录，继续下一天", dateStr);
                                        }
                                        //把 currentDate 往后推 1 天，并把结果重新赋给 currentDate。
                                        currentDate = currentDate.plusDays(1);
                                    }
                                } else {
                                    log.error("尾差数量不为负数");
                                    log.info("药品ID: {}, 药品名称: {}, 尾差日期: {}, 尾差数量: {}", weiChaDate.getDrugId(), weiChaDrug.getDrugName(), weiChaDate.getRq(), weiChaDate.getShuLiangWeiCha());
                                    // 数量不为负数 先查询库存，从最新的库存中的批次，找到是否一直存在的，一直存在的则使用这个批次，如果没有，则按照默认排序批次进行处理
                                    //询库存信息，新增对象
                                    Param kcPar = new Param();
                                    kcPar.setStartTime(weiChaDate.getRq().toString());
                                    kcPar.setDepotCode(depot.getDepotCode());
                                    kcPar.setDrugCode(weiChaDate.getDrugId());
                                    List<Kcxx> kCxxEntity = getData.getKCxxEntity(kcPar);
                                    Map<String, List<Kcxx>> collect = kCxxEntity.stream().collect(
                                            Collectors.groupingBy(
                                                    item -> item.getRecordDate(),
                                                    TreeMap::new,
                                                    Collectors.toList()
                                            )
                                    );
//                                    System.out.println(kCxxEntity);
                                    LocalDate today = LocalDate.now();
                                    String dateStr = today.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    String batchNo = "";
                                    //先拿出今天的库存信息
                                    List<Kcxx> dayKcxx = collect.get(dateStr);
                                    //在拿出尾差那天的库存信息
                                    List<Kcxx> dayOldKcxx = collect.get(DateUtil.formatDate(weiChaDate.getRq()));
                                    if(dayKcxx.isEmpty() || dayOldKcxx.isEmpty()){
                                        log.error("库存记录为空！");
                                        return;
                                    }
                                    for (Kcxx kcxx : dayKcxx) {
                                        if (!batchNo.isEmpty()) {
                                            break;
                                        }
                                        for (Kcxx oldKcxx : dayOldKcxx) {
                                            if (kcxx.getBatchNo().equals(oldKcxx.getBatchNo())) {
                                                batchNo = kcxx.getBatchNo();
                                                break;
                                            }
                                        }
                                    }
                                    if (!batchNo.isEmpty()) {
                                        log.info("批次获取成功,以下生成使用该批次:{}", batchNo);
                                        System.out.println("\n--药品名称: " + weiChaDrug.getDrugName() + ",药品ID:" + weiChaDrug.getDrugId());
                                        System.out.println("--医院名称-库房名称: " + weiChaTotal.getYymc() + "-" + depot.getDepotName());
                                        System.out.println("--"+dateStr+"-"+ weiChaTotal.getYymc()+"-"+depot.getDepotName()+"-"+weiChaDrug.getDrugName());
                                        System.out.println("--需要增加数量: " + weiChaDate.getShuLiangWeiCha().abs());
                                        for (Map.Entry<String, List<Kcxx>> stringListEntry : collect.entrySet()) {
                                            List<Kcxx> value = stringListEntry.getValue();
                                            for (Kcxx kcxx : value) {
                                                if (kcxx.getBatchNo().equals(batchNo)) {
                                                    // 使用之前筛选出来的批次进行数量的增加
                                                    BigDecimal realStorage = kcxx.getRealStorage();
                                                    BigDecimal diff = weiChaDate.getShuLiangWeiCha();
                                                    if (realStorage.compareTo(BigDecimal.ZERO) > 0) {
                                                        if (realStorage == null || diff == null) {
                                                            // 按业务决定：抛异常 / 跳过 / 取 0
                                                            throw new IllegalStateException("库存或差异为空");
                                                        }
                                                        BigDecimal result = realStorage.add(diff.abs());
                                                        if (!kcxx.getRecordId().equals("-")) {
                                                            System.out.println("\t\n--每日记录: 日期: " + kcxx.getRecordDate() + ", 药品ID: " + kcxx.getDrugId() + ", 批次: " + kcxx.getBatchNo() + ", 原始库存: " + realStorage + ", 本次增加: " + diff.abs() + ", 修改后库存: " + result);
                                                            String sql1 = "update his.drug_storage_detail_mrjl m set m.real_storage =" + result.toString() +
                                                                    " where m.record_id ='" + kcxx.getRecordId() + "' and m.depot_id ='" + kcxx.getDepotId() + "' and m.drug_id = '" + kcxx.getDrugId() + "' and m.record_date =date'" + kcxx.getRecordDate() +
                                                                    "' AND M.BATCH_NO = '" + kcxx.getBatchNo() + "';";
                                                            String rollbackSql1 = "update his.drug_storage_detail_mrjl m set m.real_storage =" + realStorage.toString() +
                                                                    " where m.record_id ='" + kcxx.getRecordId() + "' and m.depot_id ='" + kcxx.getDepotId() + "' and m.drug_id = '" + kcxx.getDrugId() + "' and m.record_date =date'" + kcxx.getRecordDate() +
                                                                    "' AND M.BATCH_NO = '" + kcxx.getBatchNo() + "';";
                                                            System.out.println(sql1);
                                                            System.out.println("--回滚SQL: " + rollbackSql1);
                                                        } else if (!kcxx.getIniId().equals("-")) {
                                                            System.out.println("\t\n--库存记录: 日期: " + kcxx.getRecordDate() + ", 药品ID: " + kcxx.getDrugId() + ", 批次: " + kcxx.getBatchNo() + ", 原始库存: " + realStorage + ", 本次增加: " + diff.abs() + ", 修改后库存: " + result);
                                                            String sql1 = "update his.drug_storage_detail m set m.real_storage =" + result.toString() +
                                                                    " where m.ini_id ='" + kcxx.getIniId() + "' and m.depot_id ='" + kcxx.getDepotId() + "' and m.drug_id = '" + kcxx.getDrugId() + "' AND M.BATCH_NO = '" + kcxx.getBatchNo() + "';";
                                                            String rollbackSql2 = "update his.drug_storage_detail m set m.real_storage =" + realStorage.toString() +
                                                                    " where m.ini_id ='" + kcxx.getIniId() + "' and m.depot_id ='" + kcxx.getDepotId() + "' and m.drug_id = '" + kcxx.getDrugId() + "' AND M.BATCH_NO = '" + kcxx.getBatchNo() + "';";
                                                            System.out.println(sql1);
                                                        }
                                                        break;
                                                    } else {
                                                        log.error("每日记录，库存为负数跳过");
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        log.error("批次获取失败");
                                    }
                                }
                            }
                        }
                    } else {
                        log.error("药品ID: {}, 药品名称: {}, 不等于0的次数大于1，次数: {}, 跳过处理", weiChaDrug.getDrugId(), weiChaDrug.getDrugName(), count);
                    }
                }
            }
        }
    }

    @Test
    public void show2() {
        Param kcPar = new Param();
        kcPar.setStartTime("2026-10-01");
        kcPar.setDepotCode("06015");
        kcPar.setDrugCode("119408");
        List<Kcxx> kCxxEntity = getData.getKCxxEntity(kcPar);
    }
}
