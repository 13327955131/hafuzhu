package com.hoostec.hfz.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期 工具
 *
 * @Date: 20180619
 * @author: loo
 * @version: 1.00
 */
public class DateUtils {

    /**
     * 获取当前日期 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        return df.format(new Date());
    }

    /**
     * 获取当前日期yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentTim() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
        return df.format(new Date());
    }

    /**
     * 获取当前日期 yyyyMMdd
     *
     * @return
     */
    public static String getCurrentTimess() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
        return df.format(new Date());
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     *
     * @return
     */
    public static String getCurrentTimes() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
        return df.format(new Date());
    }

    /**
     * 获取从某天开始以后的若干天
     *
     * @param startTime
     * @param difDays
     * @return
     */
    public static String getLastDays(String startTime, int difDays) {
        // 获取当前日期
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        // 通过日历获取日期
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sf.parse(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DAY_OF_YEAR, difDays);
        String getDays = sf.format(cal.getTime());
        return getDays;
    }

    /**
     * 根据当前日期获得上周一
     *
     * @return
     */
    public static String getLastMonday() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 1 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 7);
        String lastMonday = sf.format(calendar.getTime());
        return lastMonday;
    }

    /**
     * 根据当前日期获得上周日
     *
     * @return
     */
    public static String getLastSunday() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int offset = 7 - dayOfWeek;
        calendar.add(Calendar.DATE, offset - 7);
        String lastSunday = sf.format(calendar.getTime());
        return lastSunday;
    }

    /**
     * 根据当前日期获得上月第一天
     *
     * @return
     */
    public static String getLastMonthFirstDay() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        // 获取上月的第一天
        Calendar cal_1 = Calendar.getInstance();// 获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        String firstDay = sf.format(cal_1.getTime());
        return firstDay;
    }

    /**
     * 根据当前日期获得上月最后一天
     *
     * @return
     */
    public static String getLastMonthLastDay() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        // 获取上月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
        String lastDay = sf.format(cale.getTime());
        return lastDay;
    }

    /**
     * 计算两时间相差小时数
     *
     * @param StringTime1
     * @param StringTime2
     * @return
     * @throws ParseException
     */
    public static long getTimeDiffer(String StringTime1, String StringTime2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
        Date dateTime1 = sdf.parse(StringTime1);
        Date dateTime2 = sdf.parse(StringTime2);
        long difference = dateTime1.getTime() - dateTime2.getTime();
        return difference / (1000 * 60 * 60);
    }

    /**
     * 计算两时间相差小时数
     *
     * @param StringTime1
     * @param StringTime2
     * @return
     * @throws ParseException
     */
    public static long getTimeDifferMinute(String StringTime1, String StringTime2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
        Date dateTime1 = sdf.parse(StringTime1);
        Date dateTime2 = sdf.parse(StringTime2);
        long difference = dateTime1.getTime() - dateTime2.getTime();
        return difference / (1000 * 60);
    }

}
