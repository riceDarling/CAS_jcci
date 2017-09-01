package com.jcci.portal;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jcci.component.WebPath;

import unicorn.project.dict.FileDict;

import java.io.File;
import java.io.IOException;

/**
 * 和系统相关的服务的接口.
 * 
 * @author 周峰
 * @time 2016年9月14日 上午11:48:59
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
@RestController
public class SystemPortal {
  /**
   * 从download文件夹里下载文件.
   * 
   * @param webPath 项目的绝对路径
   * @param fileName 文件名
   * @return 文件的返回对象
   * @throws IOException 文件读取错误会抛出该异常
   */
  @RequestMapping(value = "file/download.do", method = RequestMethod.GET)
  public ResponseEntity<byte[]> downloadFile(@WebPath String webPath, String fileName)
      throws IOException {
    File file = FileUtils.getFile(webPath + FileDict.downloadPath() + fileName);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    headers.setContentDispositionFormData("attachment",
        new String(file.getName().getBytes("UTF-8"), "ISO8859-1"));
    return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers,
        HttpStatus.CREATED);
  }
}
