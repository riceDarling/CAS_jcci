package unicorn.common.excel.pojo;

/**
 * excel操作类的表头模型类。 该类是对excel表头的抽象，title为表头内容，order为表头顺序
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @since 1.0.0
 */
public class Title {
  private String title;
  private String order;
  private int bgColor;

  public Title() {}

  public Title(String order, String title) {
    this.order = order;
    this.title = title;
    this.bgColor=44;
  }

  public Title(String order, String title,int color) {
    this.order = order;
    this.title = title;
    this.bgColor=color;
  }
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public int getBgColor() {
    return bgColor;
  }

  public void setBgColor(int bgColor) {
    this.bgColor = bgColor;
  }

}
