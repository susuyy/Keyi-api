package com.ht.note.utils;



import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author: zheng weiguang
 * @Date: 2021/11/25 18:03
 */
public class DateStrUtil {

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String nowDateStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String nowDateStrYearMoonDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String nowDateStrYearMoon() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String nowDateStrYear() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 格式时间
     *
     * @return
     */
    public static String strYearMoonDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String nowDateStrToyyyyMMddHHmmss() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }


    /**
     * 转化Date
     *
     * @return
     */
    public static String dateToStr(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    /**
     * 转化Date
     *
     * @return
     */
    public static String dateToStrSs(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间HHmmSS
     *
     * @return
     */
    public static String nowDateStrHHmmSS() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前时间MMdd
     *
     * @return
     */
    public static String nowDateStrMMdd() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取时间MMdd
     *
     * @return
     */
    public static String dateStrMMdd(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMdd");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取时间MMdd
     *
     * @return
     */
    public static String dateStrHHmmss(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date StrToDateyyyyMMddHHmmss(String dateStr) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String jiaLianRetOrderDateChange(String dateStr) throws Exception {
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormatDate.parse(dateStr);
        SimpleDateFormat simpleDateFormatStr = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormatStr.format(date);
    }

    /**
     * 获取某年12个月份的列表
     *
     * @param yearStr
     * @return
     */
    public static List<MonthStatisticsData> getAllMonthShow(String yearStr) {
        // 获取当前年份
        int year = Integer.parseInt(yearStr);

        // 生成格式化日期字符串的模板
        Locale locale = new Locale("zh", "CN");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", locale);

        // 获取月份字符串数组
        String[] months = new DateFormatSymbols(locale).getMonths();

        List<MonthStatisticsData> retList = new ArrayList<>();

        // 输出中文月份和对应的日期字符串
        for (int month = 1; month <= 12; month++) {
            String monthName = months[month - 1];
            String dateString = dateFormat.format(getDate(year, month).getTime());

            MonthStatisticsData monthStatisticsData = new MonthStatisticsData();
            monthStatisticsData.setYearMonthKey(dateString);
            monthStatisticsData.setMonthKey(monthName);
            retList.add(monthStatisticsData);
        }

        return retList;
    }

    private static Calendar getDate(int year, int month) {
        // 创建一个指定年月的Calendar对象
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar;
    }

    /**
     * 获取yyyy-mm 指定月份的最后一天是几号
     *
     * @return
     */
    public static String getMonthLastDayNo(String yearMonth) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Date date = sdf.parse(yearMonth);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        int lastDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return lastDayOfMonth+"";
    }

    /**
     * 获取某年某月总共有几号
     * @param year
     * @param month
     * @return
     */
    public static List<String> getDatesInMonth(int year, int month) {
        List<String> dates = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1); // 设置年份和月份，月份从 0 开始计数，因此需要减去 1

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取该月份的最大天数

        for (int i = 1; i <= maxDay; i++) {
            String useDays;
            if (i<10){
                useDays = "0"+i;
            }else {
                useDays = i+"";
            }

            String useMonth;
            if (month<10){
                useMonth = "0"+month;
            }else {
                useMonth = month+"";
            }

            String date = year + "-" + useMonth + "-" + useDays;
            dates.add(date);
        }

        return dates;
    }

    /**
     * 当前日期加上制定天数
     * @param number
     * @return
     */
    public static String dateAddNumberDays(int number,Date oriDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oriDate);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        Date after = calendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(after);

    }

    /**
     * 当前日期加上制定天数
     * @param number
     * @return
     */
    public static Date dateAddNumberDaysRetDate(int number,Date oriDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oriDate);
        calendar.add(Calendar.DAY_OF_MONTH, number);
        return calendar.getTime();
    }

    public static void main(String[] args) {
//        int year = 2023;
//        int month = 6;
//
//        List<String> dates = getDatesInMonth(year, month);
//
//        System.out.println("Dates in " + year + "-" + month);
//        for (String date : dates) {
//            System.out.println(date);
//        }

        String limitDay = DateStrUtil.dateAddNumberDays(-15,new Date());
        System.out.println(limitDay);
    }
}
