package unicorn.common.excel.exception;

/**
 * excel转换异常.Excel数据导入导出时发生的任何异常均转义成此异常。
 * 
 * @author 周峰
 * @version v0.0.1
 */
public class ExcelTransferException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ExcelTransferException() {
    super();
  }

  public ExcelTransferException(String message) {
    super(message);
  }

  public ExcelTransferException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExcelTransferException(Throwable cause) {
    super(cause);
  }
}
