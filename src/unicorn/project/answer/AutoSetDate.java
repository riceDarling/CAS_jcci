package unicorn.project.answer;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jcci.service.part.SystemService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自动设置返回结果的时间.每次返回结果都存储数据库的当前时间。方法是一个环绕切面，在程序的最高层。
 * 
 * @author 周峰
 *
 */
@Component
@Aspect
@Order(10)
public class AutoSetDate {
  /**
   * 向返回结果中存入数据库当前时间的执行方法.用来捕获处理方法的返回结果，并向结果中存入数据库当前时间。
   * 
   * @param pjp 切入点
   * @return 方法的返回结果
   * @throws Throwable 所有的异常均抛出
   */
  @Around("execution(* com.jcci.controller.*Portal.*(..)))")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    Object object = pjp.proceed();
    if (object instanceof Answer) {
      Answer answer = (Answer) object;
      Date date = systemS.readNowDate();
      SimpleDateFormat formarter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      answer.setDate(formarter.format(date));
      return answer;
    } else {
      return object;
    }
  }

  @Autowired
  private SystemService systemS;
}
