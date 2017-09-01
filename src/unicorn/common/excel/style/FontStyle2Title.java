package unicorn.common.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * title中字体的样式.装饰模式，基类为ExcelCellStyle，是一个单元格样式生成器。调用该类可以为原始样式提供除表头文本的字体样式（宋体，12号字）。
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @see agate.excel.style.CellStyleMaker
 * @since 1.0.0
 */
public class FontStyle2Title extends CellStyleMaker {
  private ExcelCellStyle ecs = null;
  private Workbook wb = null;

  /**
   * 构造函数.
   * 
   * @param wb excel文本
   * @param ecs 单元格样式
   */
  public FontStyle2Title(Workbook wb, ExcelCellStyle ecs) {
    this.ecs = ecs;
    this.wb = wb;
    if (ecs == null || wb == null) {
      throw new InstantiationError("无法创建对象，传入数据不合法");
    }
  }

  @Override
  public CellStyle getStyle() {
    Font font = wb.createFont(); // 字体
    font.setFontHeightInPoints((short) 12);
    font.setFontName("宋体");
    font.setBoldweight((short) 700);// 加粗
    ecs.getStyle().setFont(font);
    ecs.getStyle().setWrapText(true);
    return this.ecs.getStyle();
  }
}
