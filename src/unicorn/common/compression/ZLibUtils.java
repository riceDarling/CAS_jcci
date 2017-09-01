package unicorn.common.compression;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/**
 * 压缩工具包.
 * 
 * @author 周峰
 * @time 2016年8月15日 上午11:00:30
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 * 
 */
public class ZLibUtils {
  /**
   * 压缩.
   * 
   * @param data 待压缩数据
   * @return byte[] 压缩后的数据
   */
  public static byte[] compress(byte[] data) {
    byte[] output = new byte[0];

    Deflater compresser = new Deflater();

    compresser.reset();
    compresser.setInput(data);
    compresser.finish();
    ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
    try {
      byte[] buf = new byte[1024];
      while (!compresser.finished()) {
        int index = compresser.deflate(buf);
        bos.write(buf, 0, index);
      }
      output = bos.toByteArray();
    } catch (Exception ex) {
      output = data;
      ex.printStackTrace();
    } finally {
      try {
        bos.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    compresser.end();
    return output;
  }

  /**
   * 压缩.
   * 
   * @param data 待压缩数据 *
   * @param os 输出流
   */
  public static void compress(byte[] data, OutputStream os) {
    DeflaterOutputStream dos = new DeflaterOutputStream(os);

    try {
      dos.write(data, 0, data.length);

      dos.finish();

      dos.flush();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 解压缩.
   * 
   * @param data 待压缩的数据
   * @return byte[] 解压缩后的数据
   */
  public static byte[] decompress(byte[] data) {
    byte[] output = new byte[0];
    Inflater decompresser = new Inflater();
    decompresser.reset();
    decompresser.setInput(data);

    ByteArrayOutputStream bstream = new ByteArrayOutputStream(data.length);
    try {
      byte[] buf = new byte[1024];
      while (!decompresser.finished()) {
        int index = decompresser.inflate(buf);
        bstream.write(buf, 0, index);
      }
      output = bstream.toByteArray();
    } catch (Exception ex) {
      output = data;
      ex.printStackTrace();
    } finally {
      try {
        bstream.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    decompresser.end();
    return output;
  }

  /**
   * 解压缩.
   * 
   * @param is 输入流
   * @return byte[] 解压缩后的数据
   */
  public static byte[] decompress(InputStream is) {
    InflaterInputStream iis = new InflaterInputStream(is);
    ByteArrayOutputStream bstream = new ByteArrayOutputStream(1024);
    try {
      int index = 1024;
      byte[] buf = new byte[index];

      while ((index = iis.read(buf, 0, index)) > 0) {
        bstream.write(buf, 0, index);
      }

    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return bstream.toByteArray();
  }
}
