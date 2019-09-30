/**
 * FileName: DateUtils
 * Author: xivin
 * Date: 2019-09-19 18:02
 * Description:
 */
package cn.duoduo.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private final static String FORMAT="yyyy-MM-dd HH:mm:ss";

    /**
     * string 日期 转成 Date
     * @param dataStr 日期
     * @param format  日期格式
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String dataStr, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return simpleDateFormat.parse(dataStr);
    }

    /**
     * date 转string
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date,String format) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String dateToString(long date,String format) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static Date stringToDate(String dataStr) throws ParseException {
        return stringToDate(dataStr,FORMAT);
    }

    public static String dateToString(Date date) {
        return dateToString(date,FORMAT);
    }

    /**
     *  获取明天这个时候的系统时间
     * @return
     * @throws ParseException
     */
    public static Date getTomorrow() {

        return getAfterHoursDate(24);
    }

    /**
     * 获取当前时间加上 n小时的时间
     * @param hours
     * @return
     * @throws ParseException
     */
    public static Date getAfterHoursDate(int hours) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, hours);
            String date = dateToString(calendar.getTimeInMillis(), FORMAT);
            return stringToDate(date);
        }catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
