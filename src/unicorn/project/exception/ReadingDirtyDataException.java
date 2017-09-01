package unicorn.project.exception;

/**
 * 读取到脏数据的异常,因数据库设计不严谨或程序逻辑错误而存入数据库中的脏数据被读取到时程序抛出该异常。该异常为运行时异常，因为一般情况下错误数据不能被修补，
 * 而且如果出现脏数据以后的业务逻辑也不能正常运行，所以将其定义为可以影响整个业务的严重异常，应终止后续代码。
 * 
 * @author 周峰
 * @since v1.0.0
 * @version v0.0.1
 */
public class ReadingDirtyDataException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ReadingDirtyDataException() {
    super();
  }

  public ReadingDirtyDataException(String message) {
    super(message);
  }

  public ReadingDirtyDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public ReadingDirtyDataException(Throwable cause) {
    super(cause);
  }
}
