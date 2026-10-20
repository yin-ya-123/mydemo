package com.yin.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.yin.MyDemoApplication;
import com.yin.mapper.OrderDao;
import com.yin.pojo.qt.yb.DemoData;
import com.yin.pojo.qt.yb.Zymxb;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author yin
 * @date 2024-11-12
 * @Description
 */
@Slf4j
@SpringBootTest(classes = MyDemoApplication.class)
@RunWith(SpringRunner.class)
public class ExcelUtilsNew {

    @Resource
    private OrderDao orderDao;

    //写入的数据数量。1000000，每读取一条数据加一
    public AtomicInteger writeCount = new AtomicInteger(0);
    //分割的数量1000000
    public final AtomicInteger readCount = new AtomicInteger(1000000);
    //多少数据写入一次
    public final AtomicInteger dataCount = new AtomicInteger(10000);

    private final String excelPath = "D:\\公司\\Oracle存放sql\\医保\\医保数据提取\\上报数据\\数据导出\\住院明细2019-2023(发生时间)";
    private String excelSheet = "住院明细";


    @Test
    public void write() {
        String fileName = excelPath + ".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, Zymxb.class).useDefaultStyle(false).build()) {
            AtomicInteger index = new AtomicInteger(0);
            List<Zymxb> batchData = new ArrayList<>();
            AtomicReference<WriteSheet> build = new AtomicReference<>();
            build.set(EasyExcel.writerSheet(index.get(), excelSheet + index.get()).build());
            orderDao.streamDoHandler(resultContext -> {
                Zymxb resultObject = resultContext.getResultObject();
                writeCount.incrementAndGet();
//                System.out.println(resultContext);
//                log.info("数据获取，当前{}",writeCount.get());
                batchData.add(resultObject);
                //判断数据是否到达指定写入数量，进行写入
                if (batchData.size() >= dataCount.get()) {
                    excelWriter.write(batchData, build.get());
                    log.info("数据到达{}，进行写入....集合大小{}，sheet名称{}",writeCount.get(),batchData.size(), excelSheet);
                    batchData.clear();
                    log.info("写入完毕，集合清空");
                }
                //判断是否到达分割点，到达后进行创建新的sheet
                if (writeCount.get() == readCount.get()) {
                    //初始化sheet名称
                    excelSheet = "住院明细";
                    index.incrementAndGet();
                    String sheet = excelSheet + index.get();
                    excelSheet = sheet;
                    build.set(EasyExcel.writerSheet(index.get(), sheet).build());
                    log.info("到达分割点，进行sheet分割{}", sheet);
                    //分割完毕后,初始化
                    writeCount.set(0);
                    log.info("分割完毕初始化参数");
                }
            });
            if (!batchData.isEmpty()) {
                excelWriter.write(batchData, build.get());
                log.info("最后数据写入{}", batchData.size());
            }
        }
    }

    @Test
    public void write2() {
        String fileName = excelPath + ".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, Zymxb.class).useDefaultStyle(false).build()) {
            AtomicInteger index = new AtomicInteger(0);
            List<Zymxb> batchData = new ArrayList<>();
            AtomicReference<WriteSheet> build = new AtomicReference<>();
            build.set(EasyExcel.writerSheet(index.get(), excelSheet + index.get()).build());
            orderDao.streamDoHandler2(resultContext -> {
                Zymxb resultObject = resultContext.getResultObject();
                writeCount.incrementAndGet();
//                System.out.println(resultContext);
//                log.info("数据获取，当前{}",writeCount.get());
                batchData.add(resultObject);
                //判断数据是否到达指定写入数量，进行写入
                if (batchData.size() >= dataCount.get()) {
                    excelWriter.write(batchData, build.get());
                    log.info("数据到达{}，进行写入....集合大小{}，sheet名称{}",writeCount.get(),batchData.size(), excelSheet);
                    batchData.clear();
                    log.info("写入完毕，集合清空");
                }
                //判断是否到达分割点，到达后进行创建新的sheet
                if (writeCount.get() == readCount.get()) {
                    //初始化sheet名称
                    excelSheet = "住院明细";
                    index.incrementAndGet();
                    String sheet = excelSheet + index.get();
                    excelSheet = sheet;
                    build.set(EasyExcel.writerSheet(index.get(), sheet).build());
                    log.info("到达分割点，进行sheet分割{}", sheet);
                    //分割完毕后,初始化
                    writeCount.set(0);
                    log.info("分割完毕初始化参数");
                }
            });
            if (!batchData.isEmpty()) {
                excelWriter.write(batchData, build.get());
                log.info("最后数据写入{}", batchData.size());
            }
        }
    }

    /**
     * 最简单的写
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 直接写即可
     */
    @Test
    public void simpleWrite() {
        String fileName = excelPath + ".xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, Zymxb.class).build()) {
//            List<Zymxb> zymxbs = orderDao.selectList();
//            System.out.println(zymxbs);
        }
    }

}
