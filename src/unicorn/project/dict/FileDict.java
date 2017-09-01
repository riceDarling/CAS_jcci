package unicorn.project.dict;

import unicorn.common.os.OsUtils;

public class FileDict {
  /**
   * 获取系统中的下载目录.获取到的路径是从项目路径后开始。
   * 
   * @return 下载目录
   */
  public static String downloadPath() {
    String path = null;
    switch (OsUtils.getOsName()) {
      case Linux:
        path = "/WEB-RES/download/";
        break;
      case Mac_OS:
        path = "/WEB-RES/download/";
        break;
      case Mac_OS_X:
        path = "/WEB-RES/download/";
        break;
      case Windows:
        path = "\\WEB-RES\\download\\";
        break;
      default:
        path = "\\WEB-RES\\download\\";
        break;
    }
    return path;
  }

  /**
   * 获取系统中的上传目录.获取到的路径是从项目路径后开始。
   * 
   * @return 上传目录
   */
  public static String uploadPath() {
    String path = null;
    switch (OsUtils.getOsName()) {
      case Linux:
        path = "/WEB-RES/upload/";
        break;
      case Mac_OS:
        path = "/WEB-RES/upload/";
        break;
      case Mac_OS_X:
        path = "/WEB-RES/upload/";
        break;
      case Windows:
        path = "\\WEB-RES\\upload\\";
        break;
      default:
        path = "\\WEB-RES\\upload\\";
        break;
    }
    return path;
  }
}
