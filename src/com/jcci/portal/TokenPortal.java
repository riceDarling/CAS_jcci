package com.jcci.portal;

import com.jcci.pojo.Administrator;
import com.jcci.service.kit.TokenKitService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import unicorn.project.answer.Answer;
import unicorn.project.answer.Status;
import unicorn.project.courier.StrBool;
import unicorn.project.security.Exposed;

/**
 * 安全令牌相关操作的访问入口.为调用者提供安全令牌的发放，查询和删除等等功能。
 * 
 * @author 周峰
 * @time 2016年7月22日 上午11:02:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
@RestController
@RequestMapping("token")
public class TokenPortal {
  /**
   * 使用登录账户和密码申请访问令牌.系统用户在使用系统功能前需要先申请访问令牌，访问令牌由本程序统一发放和管理。在大部分访问入口被调用时，首先要验证令牌的有效性，
   * 令牌的有效性往往代表了本次请求的有效性。<br>
   * 
   * @param sysUser系统用户的对象，用来接收调用者传来的参数。
   * @return 生成令牌的结果。
   * @Uri POST token/create.do
   * @RequestParams adminCode:管理员登录账号。字符串、必填、最大长度64。<br>
   *                adminPwd:管理员登录密码。字符串、必填、必须为32位。
   * @Answer 详见 {@linkplain unicorn.project.answer.Answer Answer}。<br>
   *         新增成功：
   * 
   *         <pre>
   *         {
   *           "status":"SUCCESS",
   *           "content":{
   *             "token":"令牌的字符串"
   *           }
   *         }
   *         </pre>
   * 
   *         新增失败：
   * 
   *         <pre>
   *         {
   *           "status":"ERROR",
   *           "message"{
   *             "cause":"原因",
   *             "notice":"提示"
   *           }
   *         }
   *         </pre>
   */
  @Exposed
  @RequestMapping(value = "create.do", method = RequestMethod.POST)
  public Answer createToken(Administrator administrator) {
    logger.debug("createToken(SystemUser)方法被执行。");
    StrBool result = tokenS.createToken(administrator);
    if (result.isSuccess()) {
      Answer answer = Answer.builder().setStatus(Status.SUCCESS)
          .putContent("token", result.getContent()).build();
      logger.debug("返回成功结果。");
      return answer;
    } else {
      Answer answer = Answer.builder().setStatus(Status.ERROR).putCause(result.getCause())
          .putNotice(result.getNotice()).build();
      logger.debug("返回失败结果。");
      return answer;
    }
  }
  

  /**
   * 删除令牌，让令牌提前终止.<br>
   * 
   * @return 删除令牌的结果。
   * @Uri POST token/delete.do
   * @Answer 详见 {@linkplain unicorn.project.answer.Answer Answer}。<br>
   *         新增成功：
   * 
   *         <pre>
   *         {
   *           "status":"SUCCESS"
   *         }
   *         </pre>
   * 
   *         新增失败：
   * 
   *         <pre>
   *         {
   *           "status":"ERROR"
   *         }
   *         </pre>
   */
  @RequestMapping(value = "delete.do", method = RequestMethod.POST)
  public Answer deleteToken() {
    boolean result = tokenS.deleteToken(null);
    if (result) {
      Answer answer = Answer.builder().setStatus(Status.SUCCESS).build();
      logger.debug("返回成功结果。");
      return answer;
    } else {
      Answer answer = Answer.builder().setStatus(Status.ERROR).build();
      logger.debug("返回成功结果。");
      return answer;
    }
  }

  @Autowired
  private TokenKitService tokenS;
  
  private static final Logger logger = LoggerFactory.getLogger(TokenPortal.class);
}
