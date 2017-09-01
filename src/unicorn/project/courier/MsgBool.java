package unicorn.project.courier;

public class MsgBool {
  private boolean success;
  private String cause;
  private String notice;

  public MsgBool() {}

  public MsgBool(boolean success) {
    this.success = success;
  }

  public MsgBool(boolean success, String notice) {
    this.success = success;
    this.notice = notice;
  }

  /**
   * 构造方法.传入所有变量，创建MsgBool对象。
   * 
   * @param success 是否成功，true成功，false失败
   * @param cause 失败的原因
   * @param notice 提示给用户的信息
   */
  public MsgBool(boolean success, String cause, String notice) {
    this.success = success;
    this.cause = cause;
    this.notice = notice;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getCause() {
    return cause;
  }

  public void setCause(String cause) {
    this.cause = cause;
  }

  public String getNotice() {
    return notice;
  }

  public void setNotice(String notice) {
    this.notice = notice;
  }
}
