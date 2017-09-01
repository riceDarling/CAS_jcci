package unicorn.project.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Header {
  private String version;// 服务软件版本。
  private String date;// 数据库当前时间。
  private String forward;// 跳转页。
  private String backward;// 后退页。

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getForward() {
    return forward;
  }

  public void setForward(String forward) {
    this.forward = forward;
  }

  public String getBackward() {
    return backward;
  }

  public void setBackward(String backward) {
    this.backward = backward;
  }
}
