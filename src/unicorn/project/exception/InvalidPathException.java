package unicorn.project.exception;

/**
 * 无效路径异常.当系统中任何时候发生路径不符合要求的时候均抛出此异常。
 * 
 * @author 周峰
 *
 */
public class InvalidPathException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public InvalidPathException() {
    super();
  }

  public InvalidPathException(String message) {
    super(message);
  }

  public InvalidPathException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidPathException(Throwable cause) {
    super(cause);
  }
}
