package unicorn.common.excel.style;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 边框宽度和颜色的样式. 装饰模式，基类为ExcelCellStyle，是一个单元格样式生成器。调用该类可以为原始样式提供全文的文本框边框1宽度，黑色的默认格式。
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @see agate.excel.style.CellStyleMaker
 * @since 1.0.0
 */
public class BorderStyle extends CellStyleMaker {
  private ExcelCellStyle ecs = null;

  /**
   * 构造方法.
   * 
   * @param ecs 单元格样式
   */
  public BorderStyle(ExcelCellStyle ecs) {
    this.ecs = ecs;
    if (ecs == null) {
      throw new InstantiationError("无法创建对象，传入数据不合法");
    }
  }

  @Override
  public CellStyle getStyle() {
    ecs.getStyle().setBorderBottom((short) 1);
    ecs.getStyle().setBorderLeft((short) 1);
    ecs.getStyle().setBorderRight((short) 1);
    ecs.getStyle().setBorderTop((short) 1);
    ecs.getStyle().setWrapText(true);
    ecs.getStyle().setLeftBorderColor(HSSFColor.BLACK.index);
    ecs.getStyle().setRightBorderColor(HSSFColor.BLACK.index);
    ecs.getStyle().setTopBorderColor(HSSFColor.BLACK.index);
    ecs.getStyle().setBottomBorderColor(HSSFColor.BLACK.index);
    return ecs.getStyle();
  }
}
