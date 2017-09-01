package com.jcci.repository;

import java.util.Date;

/**
 * 系统基本操作的数据库访问接口.
 * 
 * @author 周峰
 * @time 2016年7月22日 上午11:02:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
public interface SystemMapper {
  /**
   * 读取数据库当前时间.
   * 
   * @return 数据库当前时间
   */
  public Date readNow();
}
