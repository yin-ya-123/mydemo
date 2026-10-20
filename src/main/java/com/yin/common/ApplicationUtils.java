package com.yin.common;
import com.yin.common.err.YException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 工具类
 */
public class ApplicationUtils {
    public static void main(String[] args) {
        System.out.println(getNowDate());
    }
   public static final String pathPojo = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\yb";
   public static final String pathPojoLS = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\liangshan";
   public static final String pathPojoKangFu = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\kangfu";
   public static final String pathPojoHouJie = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt\\houjie";
   public static final String pathPojoP = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt";
   public static final String pathPojoQT = "D:\\cs\\ssxjg\\myDemo\\src\\main\\java\\com\\yin\\pojo\\qt";


    //获取当天
    public static String getNowDate(){
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        return new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime());
    }

    /**
     * 天数加减
     * @param data
     * @param reduction
     * @return
     */
    public static String getDataStrReduction(String data,int reduction){
        try {
            Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(data);
            // 创建一个Calendar对象，并使用当前日期初始化
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            calendar.add(Calendar.DATE,reduction);
            Date time = calendar.getTime();
            return new SimpleDateFormat("yyyy-MM-dd").format(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getNowDateTime(){
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        return new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(instance.getTime());
    }


    //获取本月的最后一天
    public static String getMonthLast () {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        //设置Calendar为当月的第一天，一号
        instance.set(Calendar.DAY_OF_MONTH, 1);
        //计算当月的最后一天
        instance.add(Calendar.MONTH, 1);//当前月加一个月
        //减一天获取本月最后一天
        instance.add(Calendar.DAY_OF_MONTH, -1);
        String format = new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime());
        return format;
    }
    //获取下个月的第一天
    public static String getMonthFirst () {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        //设置Calendar为当月的第一天，一号
        instance.set(Calendar.DAY_OF_MONTH, 1);
        //计算当月的最后一天 //当前月加一个月
        instance.add(Calendar.MONTH, 1);
        String format = new SimpleDateFormat("yyyy-MM-dd").format(instance.getTime());
        return format;
    }
    //日期差处理
    public static String dateDifference(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate startDateLocal = LocalDate.parse(startDate, formatter);
        LocalDate endDateLocal = LocalDate.parse(endDate, formatter);
        // 计算两个日期之间的天数差
        long daysBetween = ChronoUnit.DAYS.between(startDateLocal, endDateLocal);

        // 输出结果
//        System.out.println("两个日期之间的天数差为: " + daysBetween);
        return String.valueOf(daysBetween);
    }
    /**
     * 获取时间区间的每一天日期
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 时间集合
     */
    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate, String type) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            if (type.equals("day")) {
                currentDate = currentDate.plusDays(1);
            }
            if (type.equals("month")) {
                currentDate = currentDate.plusMonths(1);
            }
            if (type.equals("year")) {
                currentDate = currentDate.plusYears(1);
            }
        }
        return dates;
    }

    /**
     * 获取时间区间的每一天日期
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<String> getDatesBetween(String startDate, String endDate,String type) throws YException {
        String[] startSplit = startDate.split("-");
        String[] endSplit = endDate.split("-");
        if((startSplit.length != endSplit.length ) || (startSplit.length >3 && endSplit.length >3) || (startDate.isEmpty() && endDate.isEmpty())){
            throw new YException("传递参数不正常");
        }
        LocalDate startDateL = LocalDate.of(Integer.parseInt(startSplit[0]), Integer.parseInt(startSplit[1]), Integer.parseInt(startSplit[2]));
        LocalDate endDateL = LocalDate.of(Integer.parseInt(endSplit[0]), Integer.parseInt(endSplit[1]), Integer.parseInt(endSplit[2]));
        List<LocalDate> datesBetween = getDatesBetween(startDateL, endDateL,type);
        ArrayList<String> strings = new ArrayList<>();
        for (LocalDate date : datesBetween) {
            strings.add(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        return strings;
    }
}
