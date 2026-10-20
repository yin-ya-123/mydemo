package com.yin.utils.excel.main.kangfu;

import com.yin.common.ApplicationUtils;
import com.yin.pojo.excel.HzYb;
import com.yin.pojo.qt.kangfu.HzYbHz;
import com.yin.pojo.qt.kangfu.HzYbHzBg;
import com.yin.utils.FileUtils;
import com.yin.utils.excel.ExcelUtils;
import com.yin.utils.excel.params.ExcelParams;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * 康复医保目录转换新的导入目录
 */
@Slf4j
public class OneExport {
   static String zfPath="E:\\文件信息保存\\自费药品\\自费.xlsx";
   static String hzPath="E:\\文件信息保存\\自费药品\\目录汇总.xlsx";
    //获取对象
   static ExcelUtils excelUtils = ExcelUtils.getExcelUtils();
    public static void main (String[] args) throws IOException {
        String oldExcelPath="D:\\公司\\项目文件\\平湖\\文档信息\\医保目录库更新\\2026-06-01\\浙江省医保药品编码库变更一览表（仅供参考）20260525-发地市6.1启用 - 副本.xlsx";
        export(oldExcelPath,"2026-06-01");
//       export2();
    }
    //
    /**
     * 处理医保更新目录
     * @param oldExcelPath 文件地址
     * @param sj 开始时间， 停止时间
     * @throws IOException
     */
    public static void export(String oldExcelPath,String sj) throws IOException {
        //获取实体对象
        HzYb hzYb = new HzYb();
        //读取excel数据,并且转换为list实体对象
        List<HzYb> hzYbList = excelUtils.readExcel(oldExcelPath, hzYb);
        //处理数据
        for (HzYb yb : hzYbList) {
            if("启用".equals(yb.getBg())){
//                yb.setQyrq(ApplicationUtils.getMonthFirst());
                yb.setQyrq(sj);
                yb.setTyrq("2099-1-1");
            }else if ("停用".equals(yb.getBg())){
//                yb.setTyrq(ApplicationUtils.getMonthLast());
//                系统存储有个+1，需要启用日期减2才是最终结束时间
                String dataStrReduction = ApplicationUtils.getDataStrReduction(sj, -2);
                yb.setTyrq(dataStrReduction);
            }
        }
//        System.out.println(hzYbList);
        //获取标题
        ExcelParams.headRowNumber=1;
        LinkedList excelTitle = excelUtils.getExcelTitle(oldExcelPath);
//        List<String> hzYbListTitle = excelUtils.readExcelGetTitle(oldExcelPath, hzYb);
//        System.out.println(JSONObject.toJSONString(excelTitle));
        //导出
        String exportPath= FileUtils.getFilePath(oldExcelPath)+"\\新\\";
        //如果路径不存在自动创建路径
        FileUtils.createFile(exportPath);
        String newExcelPath1=String.format(exportPath+"浙江省医保药品编码库变更%s.xlsx","-带标题");
        excelUtils.writeExcel(newExcelPath1,hzYb,"康复医保更新","康复医保更新",excelTitle.size());
        //无标题导出
        String newExcelPath2=String.format(exportPath+"浙江省医保药品编码库变更%s.xlsx","-不带标题");
        excelUtils.writeExcel(newExcelPath2,hzYb,hzYbList);
    }
    public static void exportZf(String oldExcelPath){
        //获取实体对象
        HzYb hzYb = new HzYb();
        //读取excel数据,并且转换为list实体对象
        List<HzYb> hzYbList = excelUtils.readExcel(oldExcelPath, hzYb,1);
        //处理数据
        for (HzYb yb : hzYbList) {
            if("启用".equals(yb.getBg().trim())){
                yb.setQyrq(ApplicationUtils.getNowDate());
                yb.setTyrq("2099-1-1");
            }else if ("停用".equals(yb.getBg().trim())){
                yb.setTyrq(ApplicationUtils.getMonthLast());
            }
        }
//        System.out.println(hzYbList);
        //获取标题
        List<String> hzYbListTitle = excelUtils.readExcelGetTitle(oldExcelPath, hzYb,1);
        //导出
        String exportPath= FileUtils.getFilePath(oldExcelPath)+"\\自费\\";
        //如果路径不存在自动创建路径
        FileUtils.createFile(exportPath);
        String newExcelPath1=String.format(exportPath+"浙江省医保药品编码库变更%s.xlsx","-带标题");
        excelUtils.writeExcel(newExcelPath1,hzYb,"康复医保更新","康复医保更新",hzYbListTitle.size(),hzYbList);
        //无标题导出
        String newExcelPath2=String.format(exportPath+"浙江省医保药品编码库变更%s.xlsx","-不带标题");
        excelUtils.writeExcel(newExcelPath2,hzYb,hzYbList);
    }
    /**
     * 自费药品数据格式导出
     */
    public static void export2(){
        ArrayList<HzYb> hzYbList = new ArrayList<>();
        hzYbList.add(setHzYb("1","XA06AGK111S004020300443","(G)开塞露(含甘油)","外用溶液剂","20ml*25支","支"));
//        hzYbList.add(setHzYb("2","XB02AAA171B002010204917","氨甲苯酸注射液","针剂","010ml：0.1g*5支盒","盒"));
        excelUtils.writeExcel(zfPath,new HzYb(),hzYbList);
        exportZf(zfPath);
    }

    /**
     * 自费药品对象信息组成
     * @param xh 序号
     * @param ybbh 医保编码
     * @param mc 药品名称
     * @param jx 剂型
     * @param gg 规格
     * @param dw 单位
     * @return 返回对象
     */
    public static HzYb setHzYb(String xh,String ybbh,String mc,String jx,String gg,String dw){
        HzYb hzYb = new HzYb();
        //序号
        hzYb.setXh(xh);
        //自费编码
        hzYb.setYbbh(ybbh);
        //药品1
        hzYb.setFl("1");
        hzYb.setYbzwmc(mc);
        hzYb.setYbwjjx(jx);
        hzYb.setBz("");
        hzYb.setJx(jx);
        hzYb.setGg(gg);
        hzYb.setDw(dw);
        hzYb.setBg("启用");
        hzYb.setBgyy("");
        return hzYb;
    }

    @Test
    public void generate() throws IOException {
        String path ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-06\\药品目录20250123.xlsx";
        String path2 ="D:\\公司\\项目文件\\康复医院\\其他数据导出\\医保\\2025-03-06\\浙江省医保药品编码库变更20250224（仅供参考）(2).xlsx";
        ExcelParams.headRowNumber=1;
        excelUtils.generateClassEnhanceZ("HzYbHzBg", ApplicationUtils.pathPojoKangFu, path2);
    }

    /**
     * 根据基表药品总目录，把变更信息更新进去
     */
    @Test
    public void clYbMl(){
        String path ="D:\\公司\\项目文件\\康复医院\\医保导入\\药品目录20250403\\药品目录20250403（仅供参考）.xlsx";
        String savePath ="D:\\公司\\项目文件\\康复医院\\医保导入\\药品目录20250425\\药品目录20250403(增加变更记录20250425)-20250428.xlsx";
        String path2 ="D:\\公司\\项目文件\\康复医院\\医保导入\\药品目录20250425\\浙江省医保药品编码库变更20250425（仅供参考）(1).xlsx";
        ExcelUtils<HzYbHz> hzYbHzExcelUtils = ExcelUtils.of(HzYbHz.class);
        ExcelUtils<HzYbHzBg> hzYbHzBgExcelUtils = ExcelUtils.of(HzYbHzBg.class);
        List<HzYbHz> hzYbHzs = hzYbHzExcelUtils.readExcel(path,1);
        List<HzYbHzBg> hzYbHzBgs = hzYbHzBgExcelUtils.readExcel(path2,2);
        HashMap<String, HzYbHzBg> hzYbHzBgHashMap = new HashMap<>();
        for (HzYbHzBg hzYbHzBg : hzYbHzBgs) {
            hzYbHzBgHashMap.put(hzYbHzBg.getYbbm(),hzYbHzBg);
        }
        System.out.println(hzYbHzs.size());
        System.out.println(hzYbHzBgs.size());
        for (int i = 0; i < hzYbHzs.size(); i++) {
            HzYbHz hzYbHz = hzYbHzs.get(i);
            HzYbHzBg hzYbHzBg = hzYbHzBgHashMap.get(hzYbHz.getYbbm());
            if (hzYbHzBg != null){
                if(hzYbHzBg.getBg().contains("修改")){
                    hzYbHz.setYbzwmc(hzYbHzBg.getYbzwmc());
                    hzYbHz.setYbwjjx(hzYbHzBg.getYbwjjx());
                    hzYbHz.setBz(hzYbHzBg.getBz());
                    hzYbHz.setZbypID(hzYbHzBg.getZbypID());
                    hzYbHz.setZbyptym(hzYbHzBg.getZbyptym());
                    hzYbHz.setSpm(hzYbHzBg.getSpm());
                    hzYbHz.setJx(hzYbHzBg.getJx());
                    hzYbHz.setGg(hzYbHzBg.getGg());
                    hzYbHz.setDw(hzYbHzBg.getDw());
                    hzYbHz.setZhb(hzYbHzBg.getZhb());
                    hzYbHz.setBzcz(hzYbHzBg.getBzcz());
                    hzYbHz.setYpqy(hzYbHzBg.getYpqy());
                    hzYbHz.setZfbz(hzYbHzBg.getZfbz());
//                    System.out.println("修改目录:"+hzYbHz);
                }else if (hzYbHzBg.getBg().contains("停用")){
//                    System.out.println("停用目录:"+hzYbHz);
                    hzYbHzs.remove(hzYbHz);
                }
                hzYbHzBgHashMap.remove(hzYbHz.getYbbm());
            }
        }
        for (Map.Entry<String, HzYbHzBg> stringHzYbHzBgEntry : hzYbHzBgHashMap.entrySet()) {
            HzYbHzBg hzYbHzBg = stringHzYbHzBgEntry.getValue();
            HzYbHz hzYbHz = new HzYbHz();
            hzYbHz.setXh((hzYbHzs.size()+1)+"");
            hzYbHz.setYbbm(hzYbHzBg.getYbbm());
            hzYbHz.setFl(hzYbHzBg.getFl());
            hzYbHz.setYbzwmc(hzYbHzBg.getYbzwmc());
            hzYbHz.setBz(hzYbHzBg.getBz());
            hzYbHz.setYbwjjx(hzYbHzBg.getYbwjjx());
            hzYbHz.setBz(hzYbHzBg.getBz());
            hzYbHz.setZbypID(hzYbHzBg.getZbypID());
            hzYbHz.setZbyptym(hzYbHzBg.getZbyptym());
            hzYbHz.setSpm(hzYbHzBg.getSpm());
            hzYbHz.setJx(hzYbHzBg.getJx());
            hzYbHz.setGg(hzYbHzBg.getGg());
            hzYbHz.setDw(hzYbHzBg.getDw());
            hzYbHz.setZhb(hzYbHzBg.getZhb());
            hzYbHz.setBzcz(hzYbHzBg.getBzcz());
            hzYbHz.setYpqy(hzYbHzBg.getYpqy());
            hzYbHz.setZfbz(hzYbHzBg.getZfbz());
            hzYbHz.setYplx(hzYbHzBg.getYplx());
//            System.out.println("新增目录:"+hzYbHz);
            hzYbHzs.add(hzYbHz);
        }
        hzYbHzExcelUtils.writeExcel(savePath, hzYbHzs);
    }
}
