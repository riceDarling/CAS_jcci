package unicorn.common.excel.style;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 背景色的样式. 装饰模式，基类为CellStyleMaker，是一个单元格样式生成器。调用该类可以为原始样式提供全文的白色背景色。
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @since 1.0.0
 */
public class BackGroundColorStyle2Body extends CellStyleMaker {
  private ExcelCellStyle ecs = null;

  /**
   * 构造方法.
   * 
   * @param ecs 单元格样式
   */
  public BackGroundColorStyle2Body(ExcelCellStyle ecs) {
    this.ecs = ecs;
    if (ecs == null) {
      throw new InstantiationError("无法创建对象，传入数据不合法");
    }
  }

  @Override
  public CellStyle getStyle() {
    ecs.getStyle().setFillBackgroundColor(HSSFCellStyle.NO_FILL);
    return ecs.getStyle();
  }
}
