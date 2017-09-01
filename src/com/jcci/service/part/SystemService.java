package com.jcci.service.part;

import java.util.Date;

/**
 * 和本软件相关的组件类.
 * 
 * @author 周峰
 * @time 2016年7月22日 上午11:07:19
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 *
 */
public interface SystemService {
  /**
   * 读取数据库当前时间.
   * 
   * @return 数据库当前时间
   */
  public Date readNowDate();

  /**
   * 根据指定格式读取数据库当前时间.
   * 
   * @param format 格式化字符串
   * @return 读取结果
   */
  public String readNow(String format);

  /**
   * 使用“yyyy-MM-dd hh:mm:ss”格式化数据库当前时间.
   * 
   * @return 读取结果
   */
  public String readNow();
}
