package unicorn.common.excel;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import unicorn.common.datetime.DateUtils;
import unicorn.common.excel.exception.ExcelTransferException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Reader {

  /**
   * 读取stream中的内容到二维字符串数组.
   * 
   * @param inputStream 读取流
   * @return 读取结果
   */
  public Map<String, String[][]> read(InputStream inputStream) {
    try {
      Workbook workbook = WorkbookFactory.create(inputStream);
      Map<String, String[][]> result = new HashMap<String, String[][]>();
      for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
        Sheet sheet = workbook.getSheetAt(i);
        String sheetName = sheet.getSheetName();
        String[][] arrayCache = getSheetData(workbook, sheet);
        if (arrayCache == null) {
          continue;
        }
        String[][] arrayTemp = result.get(sheetName);
        if (arrayTemp != null && arrayTemp.length != 0) {
          System.arraycopy(arrayCache, 0, arrayTemp, 0, arrayCache.length);
        } else {
          result.put(sheetName, arrayCache);
        }
      }
      return result;
    } catch (EncryptedDocumentException ex) {
      throw new ExcelTransferException(ex);
    } catch (InvalidFormatException ex) {
      throw new ExcelTransferException(ex);
    } catch (IOException ex) {
      throw new ExcelTransferException(ex);
    }
  }

  /**
   * 读取stream中的内容到二维字符串数组.
   * 
   * @param inputStream 读取流
   * * @param titleIndex 列头索引
   * @return 读取结果
   */
  public Map<String, String[][]> read(InputStream inputStream,int titleIndex) {
    try {
      Workbook workbook = WorkbookFactory.create(inputStream);
      Map<String, String[][]> result = new HashMap<String, String[][]>();
      for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
        Sheet sheet = workbook.getSheetAt(i);
        String sheetName = sheet.getSheetName();
        String[][] arrayCache = getSheetData(workbook, sheet,titleIndex);
        if (arrayCache == null) {
          continue;
        }
        String[][] arrayTemp = result.get(sheetName);
        if (arrayTemp != null && arrayTemp.length != 0) {
          System.arraycopy(arrayCache, 0, arrayTemp, 0, arrayCache.length);
        } else {
          result.put(sheetName, arrayCache);
        }
      }
      return result;
    } catch (EncryptedDocumentException ex) {
      throw new ExcelTransferException(ex);
    } catch (InvalidFormatException ex) {
      throw new ExcelTransferException(ex);
    } catch (IOException ex) {
      throw new ExcelTransferException(ex);
    }
  }
  
  /**
   * 读取文件的内容到二维字符串数组.
   * 
   * @param file 文件
   * @return 读取结果
   */
  public Map<String, String[][]> read(File file) {
    try {
      return read(FileUtils.openInputStream(file));
    } catch (IOException ex) {
      throw new ExcelTransferException(ex);
    }
  }

  /**
   * 读取多个文件的内容到二维字符串数组.
   * 
   * @param file 文件
   * @return 读取结果
   */
  public Map<String, String[][]> read(MultipartFile file) {
    try {
      return read(file.getInputStream());
    } catch (IOException ex) {
      throw new ExcelTransferException(ex);
    }
  }

  private String[][] getSheetData(Workbook workbook, Sheet sheet) {
    if (sheet.getLastRowNum() < 2) {
      return null;
    }
    Row title = sheet.getRow(1);// 默认第二行是列头。
    short columnNumber = title.getLastCellNum();
    sheet = ExcelUtils.removeEmptyRows(sheet);
    String[][] arrayCache = new String[sheet.getLastRowNum() - 1][columnNumber];
    for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
      Row row = sheet.getRow(rowIndex);
      if(row == null){
    	  for(int i = 0; i < columnNumber; i++){
    		  arrayCache[rowIndex - 2][i] = "";
    	  }
    	  continue;
      }
      for (Cell cell : row) {
    	if(cell.getColumnIndex() >= columnNumber){
      		continue;
      	}
        String value;
        //判断是不是数值型，在判断是不是日期型，最后判断有没有日期
    	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell) 
    			&& cell.getNumericCellValue() >= 1){
			Date date = cell.getDateCellValue();
			value = DateUtils.getDate(date, "yyyy-MM-dd HH:mm:ss");
    	}else{
    		cell.setCellType(Cell.CELL_TYPE_STRING);
            value = cell.getStringCellValue();
    	}
    	arrayCache[rowIndex - 2][cell.getColumnIndex()] = value;
      }
    }
    return arrayCache;
  }
  
  
  /**
   * 读取文件的内容到二维字符串数组.
   * 
   * @param file 文件
   * @param titleIndex 列头索引
   * @return 读取结果
   */
  public Map<String, String[][]> read(File file,int titleIndex) {
    try {
      return read(FileUtils.openInputStream(file),titleIndex);
    } catch (IOException ex) {
      throw new ExcelTransferException(ex);
    }
  }
  
  /*
   * 指定列头索引
   */
  private String[][] getSheetData(Workbook workbook, Sheet sheet,int titleIndex ) {
    if (sheet.getLastRowNum() < titleIndex+1) {
      return null;
    }
    Row title = sheet.getRow(titleIndex);// 默认第二行是列头。
    short columnNumber = title.getLastCellNum();
    sheet = ExcelUtils.removeEmptyRows(sheet);
    String[][] arrayCache = new String[sheet.getLastRowNum()+1][columnNumber];
    for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
      Row row = sheet.getRow(rowIndex);
      for (Cell cell : row) {
    	String value;
    	//判断是不是数值型，在判断是不是日期型，最后判断有没有日期
    	if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC && DateUtil.isCellDateFormatted(cell) 
    			&& cell.getNumericCellValue() >= 1){
			Date date = cell.getDateCellValue();
			value = DateUtils.getDate(date, "yyyy-MM-dd HH:mm:ss");
    	}else{
    		cell.setCellType(Cell.CELL_TYPE_STRING);
            value = cell.getStringCellValue();
    	}
    	arrayCache[rowIndex ][cell.getColumnIndex()] = value;
      }
    }
    return arrayCache;
  }
}
