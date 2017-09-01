package unicorn.common.excel;

import org.apache.poi.ss.usermodel.Workbook;

import unicorn.common.file.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Excel的工具类.通过该类可以根据数据对象的集合正确生成Excel，默认生成在C盘中。该类中还能配置sheet名和sheet的pagesize参数。
 * 该类中所有方法均捕获了可捕获异常并将可捕获异常转译成ExcelExportException，提供给调用方法可供日志记录。
 * 
 * @author 周峰
 * @version v0.0.1
 */
public class Excel {

  public static ExcelBuilder builder() {
    return new ExcelBuilder();
  }

  /**
   * 生成excel文件.传入数据对象集合以及数据对象的类型，根据对象上的注解生成excel。集合不能为空，如果为空，则返回的生成标识为false。
   * 每页sheet的容量根据向ExcelHelper对象传入的sheetSize为准 （如果不传则默认65534），如果记录超出65534则自动生成另外一个sheet
   * 。每页的名称为向ExcelHelper传入的fileName（如果不传则默认为 “数据”），如果分页，则新生成页名为“数据2”，再生成为“数据3”，以此类推。
   * 
   * @param paramsList 数据对象集合
   * @return true,生成成功。false,生成失败，修改路径或文件名后重试。
   */
  public boolean write(List<?> paramsList) {
    boolean flag;
    if (writer != null && paramsList.size() > 0 && sheetSize > 0) {
      Workbook workBook = writer.newExcel(paramsList, sheetName, sheetSize);
      FileUtils.mkdir(filePath);
      FileOutputStream os = null;
      try {
        os = new FileOutputStream(filePath + fileName + suffix);
        workBook.write(os);
        flag = true;
      } catch (IOException ex) {
        flag = false;
        ex.printStackTrace();
      } finally {
        if (os != null) {
          try {
            os.close();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    } else {
      flag = false;
    }
    return flag;
  }
  
  public boolean write(List<?> paramsList,boolean append) {
    boolean flag;
    if (writer != null && paramsList.size() > 0 && sheetSize > 0) {
      Workbook workBook =null;
      if(append){
        workBook = writer.newExcel(filePath + fileName + suffix,paramsList, sheetName, sheetSize);
      }else{
        workBook = writer.newExcel(paramsList, sheetName, sheetSize);
      }

      FileUtils.mkdir(filePath);
      FileOutputStream os = null;
      try {
        os = new FileOutputStream(filePath + fileName + suffix);
        workBook.write(os);
        flag = true;
      } catch (IOException ex) {
        flag = false;
        ex.printStackTrace();
      } finally {
        if (os != null) {
          try {
            os.close();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    } else {
      flag = false;
    }
    return flag;
  }
  
  /**
   * 生成excel文件.传入数据对象集合，根据数据对象集合以及其中的注解生成Excel文件，map集合不能为空，map集合中有空的list集合，则去除该list，
   * 如果最后map集合为空，则返回fasle标志位。每页sheet的容量根据向ExcelHelper对象传入的sheetSize为准 （如果不传则默认65534
   * ），如果记录超出65534则自动生成另外一个sheet。生成的sheet名为传入的paramsMap的List对应的Key值 （无默认值，所以Key需要有值）。
   * 
   * @param paramsMap 多个list
   * @return boolean true,生成成功。false,生成失败，修改路径或文件名后重试。
   */
  public boolean write(Map<String, List<?>> paramsMap) {
    boolean flag;
    for (Entry<String, List<?>> entry : paramsMap.entrySet()) {
      if (entry.getValue().size() == 0) {
        paramsMap.remove(entry.getKey());
      }
    }
    if (writer != null && paramsMap.size() > 0 && sheetSize > 0) {
      FileOutputStream os = null;
      try {
        Workbook workBook = writer.newExcel(paramsMap, sheetSize);
        FileUtils.mkdir(filePath);
        os = new FileOutputStream(filePath + fileName + suffix);
        workBook.write(os);
        flag = true;
      } catch (IOException ex) {
        flag = false;
        ex.printStackTrace();
      } catch (Exception ex) {
        flag = false;
        ex.printStackTrace();
      } finally {
        if (os != null) {
          try {
            os.close();
          } catch (IOException ex) {
            ex.printStackTrace();
          }
        }
      }
    } else {
      flag = false;
    }
    return flag;
  }

  void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  void setFileName(String fileName) {
    this.fileName = fileName;
  }

  void setSheetSize(int sheetSize) {
    this.sheetSize = sheetSize;
  }

  void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }

  void setSuffix(String suffix) {
    this.suffix = suffix;
  }

  void setWriter(Writer writer) {
    this.writer = writer;
  }

  public String getFileName() {
    return this.fileName + this.suffix;
  }

  /*
   * 文件路径
   */
  private String filePath;
  /*
   * 文件名，不包含路径
   */
  private String fileName;
  /*
   * 单页sheet的size
   */
  private int sheetSize;
  /*
   * 生成的sheet的name
   */
  private String sheetName;
  /*
   * 生成文件的后缀
   */
  private String suffix;
  /*
   * 操作对象
   */
  private Writer writer;
}
