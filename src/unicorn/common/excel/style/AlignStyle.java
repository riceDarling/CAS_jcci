package unicorn.common.excel.style;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 文本对齐方式的样式.装饰模式，基类为CellStyleMaker，是一个单元格样式生成器。调用该类可以为原始样式提供全文的文本居中对齐格式。
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @since 1.0.0
 */
public class AlignStyle extends CellStyleMaker {
  private ExcelCellStyle ecs = null;

  /**
   * 构造方法.
   * 
   * @param ecs 单元格样式
   */
  public AlignStyle(ExcelCellStyle ecs) {
    this.ecs = ecs;
    if (ecs == null) {
      throw new InstantiationError("无法创建对象，传入数据不合法");
    }
  }

  @Override
  public CellStyle getStyle() {
    ecs.getStyle().setAlignment(HSSFCellStyle.ALIGN_CENTER);
    ecs.getStyle().setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    return ecs.getStyle();
  }
}
