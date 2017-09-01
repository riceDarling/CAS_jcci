package unicorn.project.log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * 记录访问者和返回结果.
 * 
 * @author 周峰
 */
@Component
@Aspect
@Order(4)
public class PortalLogger {

  /**
   * 记录访问日志.
   * 
   * @param pjp 代理对象
   * @return 处理结果
   * @throws Throwable 所有的异常均抛出
   */
  @Around("execution(* com.jcci.portal.*Portal.*(..)) ||" 
		  + "execution(* com.jcci.portal.customer.*Portal.*(..))")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    StringBuilder sb = new StringBuilder();
    Enumeration<String> paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String paramName = (String) paramNames.nextElement();
      String[] paramValues = request.getParameterValues(paramName);
      if (paramValues.length == 1) {
        String paramValue = paramValues[0];
        if (paramValue.length() != 0) {
          sb.append(paramName).append("-").append(paramValue).append(",");
        }
      }
    }
    if (sb.length() > 0) {
      sb.deleteCharAt(sb.length() - 1);
    }
    logger.error("=======================================================");
    logger.error("=> {} Params:{}", request.getRequestURL().toString(),
        sb.length() > 0 ? sb.toString() : "empty");
    long startTime = System.currentTimeMillis();
    Object result = pjp.proceed();
    long endTime = System.currentTimeMillis();
    ObjectMapper mapper = new ObjectMapper();
    logger.error("<= {}ms {}", endTime - startTime, mapper.writeValueAsString(result));
    return result;
  }

  @Autowired
  private HttpServletRequest request;
  private static Logger logger = LoggerFactory.getLogger(PortalLogger.class);
}
