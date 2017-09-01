package com.jcci.service.part;

import com.jcci.pojo.Administrator;



/**
 * 管理员组件类.用于实现和管理员相关且偏向数据库端的业务逻辑。
 * 
 * @author 周峰
 * @time 2016年7月22日 上午11:02:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 *
 */

public interface AdministratorService {
  /**
   * 使用管理员登录账号读取管理员的信息.
   * 
   * @param adminCode 管理员的登录账号
   * @return 管理员的信息
   */
  public Administrator readAdministratorByCode(String adminCode);


}
