package com.jcci.service.kit;

import com.jcci.pojo.Administrator;

import unicorn.project.courier.StrBool;

/**
 * 令牌套件类.用于实现以令牌为主的复杂业务。
 * 
 * @author 周峰
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 *
 */
public interface TokenKitService {
  /**
   * 创建令牌.
   * 
   * @param administrator 管理员的对象
   * @return 新创建的令牌
   */
  public StrBool createToken(Administrator administrator);
  

  /**
   * 删除已有令牌.
   * 
   * @param token 要删除的令牌的对象
   * @return 删除令牌的结果
   */
  public boolean deleteToken(String token);
}
