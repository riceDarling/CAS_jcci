package unicorn.project.courier;

public class StrBool extends MsgBool {
  private String content;

  /**
   * 构造函数.
   */
  public StrBool() {}

  /**
   * 构造函数.
   * 
   * @param success 是否成功
   */
  public StrBool(boolean success) {
    super.setSuccess(success);
  }

  /**
   * 构造函数.
   * 
   * @param success 是否成功
   * @param cause 原因
   * @param notice 提示信息
   * @param content 内容
   */
  public StrBool(boolean success, String cause, String notice, String content) {
    super.setSuccess(success);
    super.setCause(cause);
    super.setNotice(notice);
    this.content = content;
  }

  /**
   * 构造函数.
   * 
   * @param success 是否成功
   * @param cause 原因
   * @param notice 提示信息
   */
  public StrBool(boolean success, String cause, String notice) {
    super.setSuccess(success);
    super.setCause(cause);
    super.setNotice(notice);
  }

  /**
   * 构造函数.
   * 
   * @param success 是否成功
   * @param content 内容
   */
  public StrBool(boolean success, String content) {
    super.setSuccess(success);
    this.content = content;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
