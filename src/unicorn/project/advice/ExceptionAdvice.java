package unicorn.project.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import unicorn.common.os.OsUtils;
import unicorn.project.answer.Answer;
import unicorn.project.answer.Status;

/**
 * 控制器的通知类,该类是用来向监控程序中的控制器，并向根据控制器产生的异常情况向调用方法返回json数据的控制器通知类。
 * 
 * @author 周峰
 * @version 0.0.1
 * 
 */
@ControllerAdvice(basePackages = "com.jcci.portal")
public class ExceptionAdvice {
  static {
    switch (OsUtils.getOsName()) {
      case Linux:
        WRAP = "\n";
        break;
      case Mac_OS:
        WRAP = "\r";
        break;
      case Mac_OS_X:
        WRAP = "\r";
        break;
      case Windows:
        WRAP = "\r\n";
        break;
      default:
        WRAP = "\r\n";
        break;
    }
  }

  /**
   * 系统的统一异常处理方法.如发生的异常没有被其他方法截获,则调用该方法。
   * 
   * @param ex 所有的异常均需被捕获
   * @return 根据异常生成的返回结果
   */
  @ExceptionHandler(value = Throwable.class)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Answer exception(Throwable ex) {
    ex.printStackTrace();
    StringBuilder sb = new StringBuilder();
    StackTraceElement[] traces = ex.getStackTrace();
    for (StackTraceElement trace : traces) {
      sb.append("\tat ").append(trace).append(WRAP);
    }
    logger.error(sb.toString());
    Answer answer = Answer.builder().setStatus(Status.EXCEPTION).putNotice("程序发生异常").build();
    logger.debug("返回失败结果给调用者，对象内容为:{}", answer.toString());
    return answer;
  }

  private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);
  private static final String WRAP;
}
