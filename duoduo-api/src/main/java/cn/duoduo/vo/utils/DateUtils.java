package cn.duoduo.vo.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    private static final int ZERO=0;
    private static final int HOUR_END=23;
    private static final int MINUTE_END=59;
    private static final int SECOND_END=59;

    private static final int ONE_DAY_HAS_MILLIS=86400000;
    private static final int ONE=1;


    public static long getCurrentDayBegin() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY,ZERO);
        calendar.set(Calendar.MINUTE,ZERO);
        calendar.set(Calendar.SECOND,ZERO);
        calendar.set(Calendar.MILLISECOND,ZERO);
        return calendar.getTimeInMillis();
    }

    public static long getCurrentDayEnd() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY,HOUR_END);
        calendar.set(Calendar.MINUTE,MINUTE_END);
        calendar.set(Calendar.SECOND,SECOND_END);
        calendar.set(Calendar.MILLISECOND,999);//毫秒
        return calendar.getTimeInMillis();
    }

    public static long getYesterdayBegin() {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(getCurrentDayBegin());
        calendar.add(Calendar.HOUR_OF_DAY,-1);
        return calendar.getTimeInMillis();
    }

    public static long getYesterdayEnd() {
        return getYesterdayBegin()+ONE_DAY_HAS_MILLIS-ONE;
    }

    public static Date getBeginDayOfWeek() {
        Date date = new Date();
        if (date == null)
            return null;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            dayOfWeek += 7;
        }

        calendar.add(Calendar.DATE,2-dayOfWeek);

        return getDayStartTime(calendar.getTime());
    }

    /**
     * 传入一个 date 返回 时间戳
     * @param date
     * @return
     */
    public static Timestamp getDayStartTime(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        if (null != date) calendar.setTime(date);

        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
        calendar.set(Calendar.MILLISECOND,ZERO);
        return new Timestamp(calendar.getTimeInMillis());
    }



    public static void main(String[] args) {

        System.out.println("=================获取今天时间戳======================");
        long currentDayBegin = getCurrentDayBegin();
        long end= currentDayBegin+86400000-1;
        System.out.println("currentDayBegin = " + currentDayBegin);
        System.out.println("end = " +end);
        long currentDayEnd = getCurrentDayEnd();
        System.out.println("currentDayEnd = " + currentDayEnd);

        System.out.println("=================获取昨天时间戳======================");
        long yesterdayBegin = getYesterdayBegin();
        System.out.println("yesterdayBegin = " + yesterdayBegin);
        long yesterdayEnd = getYesterdayEnd();
        System.out.println("yesterdayEnd = " + yesterdayEnd);

        System.out.println("=================获取本周时间戳======================");
        Date beginDayOfWeek = getBeginDayOfWeek();
        System.out.println("beginDayOfWeek = " + beginDayOfWeek);


    }


}
