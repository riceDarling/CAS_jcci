package unicorn.project.exception;

/**
 * SQL语义有误.
 * 
 * @author 周峰
 * 
 */
public class SqlSemanticException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public SqlSemanticException() {
    super();
  }

  public SqlSemanticException(String message) {
    super(message);
  }

  public SqlSemanticException(String message, Throwable cause) {
    super(message, cause);
  }

  public SqlSemanticException(Throwable cause) {
    super(cause);
  }
}
