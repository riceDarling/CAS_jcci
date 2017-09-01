package com.jcci.repository;

import com.jcci.pojo.Administrator;

import org.apache.ibatis.annotations.Param;

/**
 * 管理员相关的数据库操作类.
 * 
 * @author 周峰
 * @time 2016年7月22日 上午11:02:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
public interface AdministratorMapper {


  /**
   * 使用管理员的登录账号读取数据库中对应的内容.
   * 
   * @param adminCode 管理员的登录账号
   * @return 满足条件的管理员
   */
  public Administrator readAdministratorByCode(@Param("admin_code") String adminCode);

  
}
