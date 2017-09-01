package unicorn.common.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DataBindingException;

public class DateUtils {

  public static Date dateAddMinite(Date date,int value){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    
    int day = calendar.get(Calendar.MINUTE);
    calendar.set(Calendar.MINUTE, day + value);
    return calendar.getTime();
  }
  /**
   * 向前计算日期.
   * 
   * @param date 日期
   * @param interval 向前的天数
   * @return 计算后的日期
   */
  public static Date befor(Date date, int interval) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int day = calendar.get(Calendar.DATE);
    calendar.set(Calendar.DATE, day - interval);
    return calendar.getTime();
  }
  
  /**
   * 向后计算日期.
   * 
   * @param date 日期
   * @param interval 向后的天数
   * @return 计算后的日期
   */
  public static Date after(Date date, int interval) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int day = calendar.get(Calendar.DATE);
    calendar.set(Calendar.DATE, day + interval);
    return calendar.getTime();
  }

  /**
   * 向前计算日期.
   * 
   * @param date 日期
   * @param interval 向前的天数
   * @return 计算后的日期
   */
  public static String befor(String date, int interval) {
    try {
      Date dateResult = befor(new SimpleDateFormat(DATE_FORMAT).parse(date), interval);
      String dayBefore = new SimpleDateFormat(DATE_FORMAT).format(dateResult);
      return dayBefore;
    } catch (ParseException ex) {
      throw new DataBindingException(ex);
    }
  }

  /**
   * 计算两个日期相差的天数.
   * 
   * @param preDate 更早的日期
   * @param nextDate 更晚的日期
   * @return 相差的天数
   */
  public static int interval(Date preDate, Date nextDate) {
    int day = (int) ((nextDate.getTime() - preDate.getTime()) / (24 * 60 * 60 * 1000));
    return day;
  }

  /**
   * 计算两个日期相差的天数.在算日期的差时，需要对传入的日期格式化。
   * 
   * @param preDate 更早的日期
   * @param nextDate 更晚的日期
   * @param forMat 格式化方式
   * @return 相差的天数
   */
  public static int interval(String preDate, String nextDate, String forMat) {
    SimpleDateFormat formatter = new SimpleDateFormat(forMat);
    try {
      return interval(formatter.parse(preDate), formatter.parse(nextDate));
    } catch (ParseException ex) {
      throw new DataBindingException(ex);
    }
  }

  /**
   * 计算两个日期相差的天数.在算日期的差时，使用默认“yyyy-MM-dd”的形式格式化日期。
   * 
   * @param preDate 更早的日期
   * @param nextDate 更晚的日期
   * @return 相差的天数
   */
  public static int interval(String preDate, String nextDate) {
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
    try {
      return interval(formatter.parse(preDate), formatter.parse(nextDate));
    } catch (ParseException ex) {
      throw new DataBindingException(ex);
    }
  }

  /**
   * 计算两个日期相差的天数.在计算日期的差时，两个日期分别使用不同的格式化方法。
   * 
   * @param preDate 更早的日期
   * @param preForMat 更早的日期的格式化方式
   * @param nextDate 更晚的日期
   * @param nextForMat 更晚日期的格式化方式
   * @return 相差天数
   */
  public static int interval(String preDate, String preForMat, String nextDate, String nextForMat) {
    SimpleDateFormat previousFormatter = new SimpleDateFormat(preForMat);
    SimpleDateFormat nextFormatter = new SimpleDateFormat(nextForMat);
    try {
      return interval(previousFormatter.parse(preDate), nextFormatter.parse(nextDate));
    } catch (ParseException ex) {
      throw new DataBindingException(ex);
    }
  }

  /**
   * 计算两个日期相差的月数.可以跨年使用，如2016-02-15和2015-01-15相差为13个月。
   * 
   * @param preDate 更早的日期
   * @param nextDate 更晚的日期
   * @return 相差的月数
   */
  public static int intervalMonth(Date preDate, Date nextDate) {
    Calendar pre = Calendar.getInstance();
    Calendar next = Calendar.getInstance();
    pre.setTime(preDate);
    next.setTime(nextDate);
    int preYear = pre.get(Calendar.YEAR);
    int preMonth = pre.get(Calendar.MONTH);
    int nextYear = next.get(Calendar.YEAR);
    int nextMonth = next.get(Calendar.MONTH);
    int intervalYear = nextYear - preYear;
    int intervalMonth = nextMonth - preMonth;
    int result = intervalYear * 12 + intervalMonth;
    return result;
  }

  /**
   * 传入String获取Date对象的方法.使用默认的“yyyy-mm-dd”的形式格式化字符串。
   * 
   * @param date 日期字符串
   * @return 生成的Date对象
   */
  public static Date getDate(String date) {
    return getDate(date, DATE_FORMAT);
  }

  /**
   * 传入String获取Date对象的方法.
   * 
   * @param date 日期字符串
   * @param format 格式化字符串
   * @return 生成的Date对象
   */
  public static Date getDate(String date, String format) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    try {
      Date result = dateFormat.parse(date);
      return result;
    } catch (ParseException ex) {
      throw new DataBindingException(ex);
    }
  }

  /**
   * 传入Date获取String的方法.
   * 
   * @param date 日期
   * @return 生成的日期字符串
   */
  public static String getDate(Date date) {
    String result = getDate(date, "yyyy-MM-dd HH:mm:ss");
    return result;
  }

  /**
   * 传入Date和格式化字符串获取String的方法.
   * 
   * @param date 日期
   * @param format 格式化字符串
   * @return 生成的日期字符串
   */
  public static String getDate(Date date, String format) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    String result = dateFormat.format(date);
    return result;
  }

  /**
   * 日期时间转字符串.
   * 
   * @param date 日期时间
   * @param format 格式化字符串 yyyy-MM-dd
   * @return 字符串
   */
  public static String dateToString(Date date, String format) {
    SimpleDateFormat formater = new SimpleDateFormat();
    formater.applyPattern(format);
    return formater.format(date);
  }

  /**
   * 获取本地时间.
   * 
   * @param formart 格式化时间
   * @return 程序服务器的当前时间
   */
  public static String getTime(String formart) {
    Date date = new Date();
    SimpleDateFormat formater = new SimpleDateFormat();
    formater.applyPattern(formart);
    return formater.format(date);
  }

  private static String DATE_FORMAT = "yyyy-MM-dd";
}
