package unicorn.common.excel;

import unicorn.common.charset.StringUtils;
import unicorn.common.datetime.DateUtils;

public class ExcelBuilder {
  public ExcelBuilder setFilePath(String filePath) {
    this.filePath = filePath;
    return this;
  }

  public ExcelBuilder setFileName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  public ExcelBuilder setFileType(ExcelType fileType) {
    this.fileType = fileType;
    return this;
  }

  public ExcelBuilder setSheetSize(int sheetSize) {
    this.sheetSize = sheetSize;
    return this;
  }

  public ExcelBuilder setSheetName(String sheetName) {
    this.sheetName = sheetName;
    return this;
  }

  /**
   * 实例化Excel对象.
   * 
   * @return excel对象
   */
  public Excel build() {
    Excel excel = buildFilePath(null);
    excel = buildFileName(excel);
    excel = builderFileType(excel);
    excel = buildSheetSize(excel);
    excel = buildSheetName(excel);
    return excel;
  }

  private Excel buildFilePath(Excel excel) {
    if (excel == null) {
      excel = new Excel();
    }
    if (StringUtils.isEmpty(this.filePath)) {
      throw new InstantiationError("生成路径不可用！");
    } else {
      excel.setFilePath(this.filePath);
      return excel;
    }
  }

  private Excel buildFileName(Excel excel) {
    if (excel == null) {
      excel = new Excel();
    }
    excel.setFileName(this.fileName);
    return excel;
  }

  private Excel builderFileType(Excel excel) {
    if (excel == null) {
      excel = new Excel();
    }
    if (this.fileType == null) {
      throw new InstantiationError("未知文件类型！");
    } else {
      switch (this.fileType) {
        case XLS_2003: {
          excel.setWriter(new XlsWriter());
          excel.setSuffix(".xls");
          break;
        }
        case XLSX_2007_AND_LATER: {
          excel.setWriter(new XlsxWriter());
          excel.setSuffix(".xlsx");
          break;
        }
        default: {
          excel.setWriter(new XlsWriter());
          excel.setSuffix(".xls");
          break;
        }
      }
      return excel;
    }
  }

  private Excel buildSheetSize(Excel excel) {
    if (excel == null) {
      excel = new Excel();
    }
    excel.setSheetSize(this.sheetSize);
    return excel;
  }

  private Excel buildSheetName(Excel excel) {
    if (excel == null) {
      excel = new Excel();
    }
    excel.setSheetName(this.sheetName);
    return excel;
  }

  /*
   * 文件路径
   */
  private String filePath;
  /*
   * 文件名，不包含路径，不带后缀。
   */
  private String fileName = DateUtils.getTime("yyyy-MM-dd_HH-mm-ss");
  /*
   * 文件类型
   */
  private ExcelType fileType = ExcelType.XLSX_2007_AND_LATER;
  /*
   * 单页sheet的size
   */
  private int sheetSize = 65534;
  /*
   * 生成的sheet的name
   */
  private String sheetName = "数据";
}
