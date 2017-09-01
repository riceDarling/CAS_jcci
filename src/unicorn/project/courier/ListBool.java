package unicorn.project.courier;

import java.util.List;

public class ListBool<T> extends MsgBool {
  private List<T> content;

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }
}
