package unicorn.project.security;

import static unicorn.common.charset.StringUtils.isEmpty;
import static unicorn.common.charset.StringUtils.uppercaseInitial;
import static unicorn.project.answer.Status.NOTACCESSIBLE;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import unicorn.common.cache.EhCacheUtils;
import unicorn.project.answer.Answer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

/**
 * 访问验证切面.在需要验证访问用户身份的时候验证用户的Token。
 * 
 * @author 周峰
 *
 */
@Component
@Aspect
@Order(3)
public class AccessSecurity {
  /**
   * 访问验证的执行方法.在portal层的方法被调用的前后执行，在方法被调用前，验证用户携带的token是否合法，如果不合法则不允许方法被调用。
   * 方法中产生的任何异常均直接抛出，被抛出的异常会被统一异常处理捕获并处理。
   * 
   * @param pjp 切入点
   * @return 验证结果或方法执行结果
   * @throws Throwable 所有的异常均抛出
   */
  @Around("execution(* com.jcci.portal.*Portal.*(..)) && !@annotation(unicorn.project.security.Exposed)")
  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    // 取的header中的值
    String jwtStr = request.getHeader("Access-Token");
    if (jwtStr == null) {
      jwtStr = request.getParameter("token");
    }
    String uid = null;
    String utype = null;
    try {
      // 将token解析成数据对象
      Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(KEY))
          .parseClaimsJws(jwtStr).getBody();
      // 取的token中的内容
      uid = String.valueOf(claims.get("uid"));
      utype = String.valueOf(claims.get("utype"));
    } catch (SignatureException ex) {
      Answer result =
          Answer.builder().setStatus(NOTACCESSIBLE).putNotice("访问错误").putCause("无效token").build();
      return result;
    } catch (ExpiredJwtException ex) {
      Answer result =
          Answer.builder().setStatus(NOTACCESSIBLE).putNotice("访问错误").putCause("token失效").build();
      return result;
    }
    // 判断是否取到了uid和utype两个值。
    if (isEmpty(uid) && isEmpty(utype)) {
      Answer result =
          Answer.builder().setStatus(NOTACCESSIBLE).putNotice("访问错误").putCause("无效token").build();
      return result;
    } else {
      // 判断token是否过期。
      String token = (String) EhCacheUtils.cache("tokenCache").get(uid);
      if (isEmpty(token)) {
        Answer result = Answer.builder().setStatus(NOTACCESSIBLE).putNotice("访问错误")
            .putCause("token已过期").build();
        return result;
      } else {
        // 取的调用方法的方法签名。
        MethodSignature methodSign = (MethodSignature) pjp.getSignature();
        Method method = methodSign.getMethod();
        Annotation[][] paramAnns = method.getParameterAnnotations();
        Class<?>[] paramsClass = method.getParameterTypes();
        Object[] args = pjp.getArgs();
        for (int i = 0; i < paramAnns.length; i++) {
          Annotation[] annotations = paramAnns[i];
          Class<?> paramClass = paramsClass[i];
          for (Annotation annotation : annotations) {
            if (annotation instanceof Uid) {
              if (args[i] == null) {
                if (paramClass == String.class) {
                  args[i] = uid;
                  continue;
                } else if (paramClass == Integer.class) {
                  args[i] = new Integer(uid);
                  continue;
                } else if (paramClass == Long.class) {
                  args[i] = new Long(uid);
                  continue;
                } else {
                  continue;
                }
              }
            } else if (annotation instanceof Utype) {
              if (args[i] == null) {
                if (paramClass == String.class) {
                  args[i] = utype;
                  continue;
                } else if (paramClass == Integer.class) {
                  args[i] = new Integer(utype);
                  continue;
                } else if (paramClass == Long.class) {
                  args[i] = new Long(utype);
                  continue;
                } else {
                  continue;
                }
              }
            } else if (annotation instanceof Empower) {
              Empower sieve = (Empower) annotation;
              String uidField = sieve.setIdTo();
              String utypeField = sieve.setTypeTo();
              String setUidMethodName =
                  isEmpty(uidField) ? "setAdminId" : "set" + uppercaseInitial(uidField);
              String setUtypeMethodName =
                  isEmpty(utypeField) ? "setAdminType" : "set" + uppercaseInitial(utypeField);
              Object uidInParam = null;
              Object utypeInParam = null;
              if (args[i] == null) {
                Class<?> classType = Class.forName(paramClass.getName());
                args[i] = classType.newInstance();
              }
              Method getUid = paramsClass[i].getMethod(
                  isEmpty(uidField) ? "getAdminId" : "get" + uppercaseInitial(uidField),
                  new Class[] {});
              uidInParam = getUid.invoke(args[i], new Object[] {});
              
              Method getUtype = paramsClass[i].getMethod(
                  isEmpty(utypeField) ? "getAdminType" : "get" + uppercaseInitial(utypeField),
                  new Class[] {});
              utypeInParam = getUtype.invoke(args[i], new Object[] {});
              Method[] exeMethods = paramClass.getDeclaredMethods();
              // 使用循环方法声明的方式去反射调用方法，因为这种方法不局限于必须声明了属性。
              for (Method exeMethod : exeMethods) {
                if (exeMethod.getName().equals(setUidMethodName)) {
                  Class<?>[] setUidMethodParamClasses = exeMethod.getParameterTypes();
                  if (setUidMethodParamClasses.length > 0) {
                    if (uidInParam == null) {
                      if (setUidMethodParamClasses[0] == String.class) {
                        exeMethod.invoke(args[i], uid);
                        continue;
                      } else if (setUidMethodParamClasses[0] == Integer.class) {
                        exeMethod.invoke(args[i], Integer.valueOf(uid));
                        continue;
                      } else if (setUidMethodParamClasses[0] == Long.class) {
                        exeMethod.invoke(args[i], Long.valueOf(uid));
                        continue;
                      } else {
                        continue;
                      }
                    }
                  }
                } else if (exeMethod.getName().equals(setUtypeMethodName)) {
                  Class<?>[] setUtypeMethodParamClasses = exeMethod.getParameterTypes();
                  if (setUtypeMethodParamClasses.length > 0) {
                    if (utypeInParam == null) {
                      if (setUtypeMethodParamClasses[0] == String.class) {
                        exeMethod.invoke(args[i], utype);
                        continue;
                      } else if (setUtypeMethodParamClasses[0] == Integer.class) {
                        exeMethod.invoke(args[i], Integer.valueOf(utype));
                        continue;
                      } else if (setUtypeMethodParamClasses[0] == Long.class) {
                        exeMethod.invoke(args[i], Long.valueOf(utype));
                        continue;
                      } else {
                        continue;
                      }
                    }
                  }
                } else {
                  continue;
                }
              }
            } else {
              continue;
            }
          }
        }
        Object result = pjp.proceed(args);
        return result;
      }
    }
  }

  @Autowired
  private HttpServletRequest request;
  // public static final String KEY = "F!qabihsSJU34jshHSKpq6ezHbYqowx8";
  public static final String KEY = "F!qabihsSJU34jsh";
}
