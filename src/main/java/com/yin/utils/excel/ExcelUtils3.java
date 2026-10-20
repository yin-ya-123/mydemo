package com.yin.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.yin.utils.excel.ExcelListener.MonthSheetWriteHandler;
import com.yin.utils.excel.params.ExcelParams;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExcelUtils3<T> {

    private Integer PheadRowNumber=2;
    private String Ppath; //读取的文件路径
    private String Wpath; //写入的文件路径
    private String Psheet;
    private String PexcelTiele;
    private Integer PlastCol;
    private List<T> PexcelList;
    private T data;

    public ExcelUtils3<T> setHeadRowNumber(Integer headRowNumber1){
        PheadRowNumber=headRowNumber1;
        return this;
    }

    public ExcelUtils3<T> setPPath(String path){
        Ppath=path;
        return this;
    }
    public ExcelUtils3<T> setWPath(String path){
        Wpath=path;
        return this;
    }
    public ExcelUtils3<T> setSheet(String sheet){
        Psheet=sheet;
        return this;
    }
    public ExcelUtils3<T> setExelTitle(String exelTitle){
        PexcelTiele=exelTitle;
        return this;
    }
    public ExcelUtils3<T> setLastCol(){
        List<String> strings = readExcelGetTitle();
        PlastCol=strings.size();
        return this;
    }
    public ExcelUtils3<T> setExcelList(List<T> excelList){
        PexcelList=excelList;
        return this;
    }
    public ExcelUtils3(T data1){
        this.data = data1;
    }
    public ExcelUtils3<T> Builder(){
        return this;
    }

    /**
     读取excel文件内容，默认读取是从excel的第2行开始读取
     * @return 返回读取了全部数据的List集合
     */
    public List<T> readExcel(){
        List<T> excelList= ListUtils.newArrayList();
        EasyExcel.read(Ppath,data.getClass(),new PageReadListener<T>(excelList::addAll)).sheet().headRowNumber(PheadRowNumber).doRead();
        PexcelList=excelList;
        return excelList;
    }
    public ExcelUtils3<T>readExcel(boolean flag){
        List<T> excelList= ListUtils.newArrayList();
        EasyExcel.read(Ppath,data.getClass(),new PageReadListener<T>(excelList::addAll)).sheet().headRowNumber(PheadRowNumber).doRead();
        PexcelList=excelList;
        return this;
    }
    /**
     *写入excel数据，使用了readExcel方法后，可以直接使用该方法直接写入
     * @param path 写入的excel地址，可以直接写入，会自动创建
     * @param data 对应的实体类
     * @param excelTiele excel的文件大标题
     * @param lastCol excel大标题合并的列数，从readExcelGetTitle获取
     */
    public boolean writeExcel(){
        try {
            if(!StringUtils.hasText(Psheet)){
                Psheet="sheet";
            }
            if(StringUtils.hasText(PexcelTiele)&&PlastCol!=0){
                ExcelParams.excelTiele=PexcelTiele;
                ExcelParams.lastCol=PlastCol;
                EasyExcel.write(Wpath,data.getClass())
                        .useDefaultStyle(false) //取消写入的时候标题的样式
                        .sheet(Psheet)
                        .registerWriteHandler(new MonthSheetWriteHandler())
                        .relativeHeadRowIndex(1) //如果设置大标题必须设置为1,设置第几行设置字段标题
                        .doWrite(() -> {
                            // 分页查询数据
                            return PexcelList;
                        });
            }else {
                EasyExcel.write(Ppath,data.getClass())
                        .useDefaultStyle(false) //取消写入的时候标题的样式
                        .sheet("sheet")
                        .doWrite(() -> {
                            // 分页查询数据
                            return PexcelList;
                        });
            }
            System.out.println("写入完毕");
            return true;
        }catch (Exception e){
            return false;
        }

    }

    /**
     读取excel文件内容，然后获取文件的每列的标题信息
     * @param path excel文件地址
     * @param data 实体类，需要new一下
     * @return 返回读取了全部数据的List集合
     */
    public List<String> readExcelGetTitle(){
        LinkedList<String> titleList = new LinkedList<>();
        EasyExcel.read(Ppath, data.getClass(),new AnalysisEventListener() {
            @Override
            public void invoke (Object o, AnalysisContext analysisContext) {
//                log.info("解析到一个数据：{}",JSON.toJSONString(o));
            }
            @Override
            public void doAfterAllAnalysed (AnalysisContext analysisContext) {
//                log.info("解析的所有数据");
            }
            @Override
            public void invokeHeadMap (Map headMap, AnalysisContext context) {
                for (Object o : headMap.entrySet()) {
                    titleList.add(JSONObject.toJSONString(o));
                }
            }
        }).sheet().headRowNumber(PheadRowNumber).doRead();
        //headRowNumber控制从第几行开始读取,然后invokeHeadMap获取标题信息
        return titleList;
    }

}
