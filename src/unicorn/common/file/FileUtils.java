package unicorn.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件操作工具类。 包含常用的文件操作，如生成文件夹，删除文件夹，判断文件夹或文件是否存在等等
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @since 1.0.0
 */
public final class FileUtils {
  /**
   * 不允许实例化.
   */
  private FileUtils() {}

  /**
   * 创建目录.
   * 
   * @param dir 目录
   * @return boolean 是否执行成功
   */
  public static boolean mkdir(String dir) {
    boolean flag = false;
    try {
      String dirTemp = dir;
      File dirPath = new File(dirTemp);
      if (!dirPath.exists()) {
        dirPath.mkdir();
        flag = true;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return flag;
  }

  /**
   * 新建文件
   * 
   * @param fileName String 包含路径的文件名 如:E:\phsftp\src\123.txt
   * @param content String 文件内容
   * @return boolean 是否执行成功
   */
  public static boolean createNewFile(String fileName, String content) {
    boolean flag = false;
    try {
      String fileNameTemp = fileName;
      File filePath = new File(fileNameTemp);
      if (!filePath.exists()) {
        filePath.createNewFile();
      }
      FileWriter fw = new FileWriter(filePath);
      PrintWriter pw = new PrintWriter(fw);
      String strContent = content;
      pw.println(strContent);
      pw.flush();
      pw.close();
      fw.close();
      flag = true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return flag;
  }

  /**
   * 删除文件.
   * 
   * @param fileName 包含路径的文件名
   * @return boolean 是否执行成功
   */
  public static boolean delFile(String fileName) {
    boolean flag = false;
    try {
      String filePath = fileName;
      java.io.File delFile = new java.io.File(filePath);
      delFile.delete();
      flag = true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return flag;
  }

  /**
   * 删除文件夹.
   * 
   * @param folderPath 文件夹路径
   * @return boolean 是否执行成功
   */
  public static boolean delFolder(String folderPath) {
    boolean flag = false;
    try {
      // 删除文件夹里面所有内容
      delAllFile(folderPath);
      String filePath = folderPath;
      java.io.File myFilePath = new java.io.File(filePath);
      // 删除空文件夹
      myFilePath.delete();
      flag = true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return flag;
  }

  /**
   * 删除文件夹里面的所有文件.
   * 
   * @param path 文件夹路径
   * @return boolean 是否执行成功
   */
  public static boolean delAllFile(String path) {
    boolean sign = false;
    File file = new File(path);
    if (!file.exists()) {
      return sign;
    }
    if (!file.isDirectory()) {
      return sign;
    }
    String[] childFiles = file.list();
    File temp = null;
    if (childFiles == null) {
      return true;
    } else {
      for (int i = 0; i < childFiles.length; i++) {
        // File.separator与系统有关的默认名称分隔符
        // 在UNIX系统上，此字段的值为'/'；在Microsoft Windows系统上，它为 '\'。
        if (path.endsWith(File.separator)) {
          temp = new File(path + childFiles[i]);
        } else {
          temp = new File(path + File.separator + childFiles[i]);
        }
        if (temp.isFile()) {
          temp.delete();
        }
        if (temp.isDirectory()) {
          delAllFile(path + "/" + childFiles[i]);// 先删除文件夹里面的文件
          delFolder(path + "/" + childFiles[i]);// 再删除空文件夹
        }
      }
      sign = true;
      return sign;
    }
  }

  /**
   * 复制单个文件.
   * 
   * @param srcFile 包含路径的源文件 如：E:/phsftp/src/abc.txt
   * @param dirDest 目标文件目录；若文件目录不存在则自动创建 如：E:/phsftp/dest
   * @return boolean 是否执行成功
   */
  public static boolean copyFile(String srcFile, String dirDest) {
    boolean sign = false;
    try {
      FileInputStream in = new FileInputStream(srcFile);
      mkdir(dirDest);
      FileOutputStream out =
          new FileOutputStream(dirDest + "/" + new File(srcFile).getName());
      int len;
      byte[] buffer = new byte[1024];
      while ((len = in.read(buffer)) != -1) {
        out.write(buffer, 0, len);
      }
      out.flush();
      out.close();
      in.close();
      sign = true;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return sign;
  }

  /**
   * 复制文件夹.
   * 
   * @param oldPath 旧文件的路径
   * @param newPath 新文件的路径
   * @return boolean 是否执行成功
   */
  public static boolean copyFolder(String oldPath, String newPath) {
    boolean sign = false;
    try {
      mkdir(newPath);
      File file = new File(oldPath);
      String[] files = file.list();
      File temp = null;
      if (files == null) {
        return true;
      } else {
        for (int i = 0; i < files.length; i++) {
          if (oldPath.endsWith(File.separator)) {
            temp = new File(oldPath + files[i]);
          } else {
            temp = new File(oldPath + File.separator + files[i]);
          }

          if (temp.isFile()) {
            FileInputStream input = new FileInputStream(temp);
            FileOutputStream output =
                new FileOutputStream(newPath + "/" + (temp.getName()).toString());
            byte[] buffer = new byte[1024 * 2];
            int len;
            while ((len = input.read(buffer)) != -1) {
              output.write(buffer, 0, len);
            }
            output.flush();
            output.close();
            input.close();
          }
          if (temp.isDirectory()) { // 如果是子文件夹
            copyFolder(oldPath + "/" + files[i], newPath + "/" + files[i]);
          }
        }
        sign = true;
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return sign;
  }

  /**
   * 移动文件到指定目录.
   * 
   * @param oldPath 旧文件路径
   * @param newPath 新文件路径
   */
  public static void moveFile(String oldPath, String newPath) {
    copyFile(oldPath, newPath);
    delFile(oldPath);
  }

  /**
   * 移动文件到指定目录.不会删除文件夹。
   * 
   * @param oldPath 旧文件路径
   * @param newPath 新文件路径
   */
  public static void moveFiles(String oldPath, String newPath) {
    copyFolder(oldPath, newPath);
    delAllFile(oldPath);
  }

  /**
   * 移动文件到指定目录.会删除文件夹。
   * 
   * @param oldPath 旧文件路径
   * @param newPath 新文件路径
   */
  public static void moveFolder(String oldPath, String newPath) {
    copyFolder(oldPath, newPath);
    delFolder(oldPath);
  }

  // /**
  // * 解压zip文件.
  // *
  // * @param srcDir
  // * @param destDir
  // * @throws Exception
  // */
  // public static void jieYaZip(String srcDir, String destDir)
  // throws Exception
  // {
  // int leng = 0;
  // byte[] b = new byte[1024 * 2];
  // /** 获取zip格式的文件 **/
  // File[] zipFiles = new FileFilterByExtension("zip").getFiles(srcDir);
  // if (zipFiles != null && !"".equals(zipFiles))
  // {
  // for (int i = 0; i < zipFiles.length; i++)
  // {
  // File file = zipFiles[i];
  // /** 解压的输入流 * */
  // ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
  // ZipEntry entry = null;
  // while ((entry = zis.getNextEntry()) != null)
  // {
  // File destFile = null;
  // if (destDir.endsWith(File.separator))
  // {
  // destFile = new File(destDir + entry.getName());
  // }
  // else
  // {
  // destFile = new File(destDir + "/" + entry.getName());
  // }
  // /** 把解压包中的文件拷贝到目标目录 * */
  // FileOutputStream fos = new FileOutputStream(destFile);
  // while ((leng = zis.read(b)) != -1)
  // {
  // fos.write(b, 0, leng);
  // }
  // fos.close();
  // }
  // zis.close();
  // }
  // }
  // }

  /**
   * 压缩文件夹.
   * 
   * @param srcDir 源文件夹
   * @param destDir 目录文件夹
   * @throws Exception 遇到异常抛出
   */
  public static void zip(String srcDir, String destDir) throws Exception {
    String tempFileName = null;
    byte[] buf = new byte[1024 * 2];
    int len;
    // 获取要压缩的文件
    File[] files = new File(srcDir).listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isFile()) {
          FileInputStream fis = new FileInputStream(file);
          BufferedInputStream bis = new BufferedInputStream(fis);
          if (destDir.endsWith(File.separator)) {
            tempFileName = destDir + file.getName() + ".zip";
          } else {
            tempFileName = destDir + "/" + file.getName() + ".zip";
          }
          FileOutputStream fos = new FileOutputStream(tempFileName);
          BufferedOutputStream bos = new BufferedOutputStream(fos);
          ZipOutputStream zos = new ZipOutputStream(bos);// 压缩包

          ZipEntry ze = new ZipEntry(file.getName());// 压缩包文件名
          zos.putNextEntry(ze);// 写入新的ZIP文件条目并将流定位到条目数据的开始处

          while ((len = bis.read(buf)) != -1) {
            zos.write(buf, 0, len);
            zos.flush();
          }
          bis.close();
          zos.close();
        }
      }
    }
  }

  /**
   * 读取数据.
   * 
   * @param inSream 读入的Stream对象
   * @param charsetName 字符集名称
   * @return String 读取的数据
   * @throws Exception 遇到异常抛出
   */
  public static String readData(InputStream inSream, String charsetName)
      throws Exception {
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len = -1;
    while ((len = inSream.read(buffer)) != -1) {
      outStream.write(buffer, 0, len);
    }
    byte[] data = outStream.toByteArray();
    outStream.close();
    inSream.close();
    return new String(data, charsetName);
  }

  /**
   * 一行一行读取文件.适合字符读取，若读取中文字符时会出现乱码
   * 
   * @param path 文件的路径
   * @throws Exception 遇到异常抛出
   */
  public static Set<String> readFile2Set(String path) throws Exception {
    Set<String> datas = new HashSet<String>();
    FileReader fr = new FileReader(path);
    BufferedReader br = new BufferedReader(fr);
    String line = null;
    while ((line = br.readLine()) != null) {
      datas.add(line);
    }
    br.close();
    fr.close();
    return datas;
  }
}
