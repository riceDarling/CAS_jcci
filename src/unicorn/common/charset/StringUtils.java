package unicorn.common.charset;

import java.util.Random;

/**
 * 字符串操作类。 该类提供字符串的首字母大小写转换，空字符串转换，生成随机字符串等等常用操作
 * 
 * @author 周峰
 * @since 1.0.0
 */
public abstract class StringUtils {
  /**
   * 不可实例化.
   */
  private StringUtils() {}

  /**
   * 将字符串的首字母转换成小写字母.如果传入的字符串首字母为小写字母或者不是字母，则返回原值。<br>
   * 示例：
   * <ul>
   * <li>传入" Upa"返回" Upa"</li>
   * <li>传入"测试"返回"测试"</li>
   * <li>传入"Upa"返回"upa"</li>
   * </ul>
   * 
   * @param str 字符串
   * @return 首字母小写的字符串
   */
  public static final String lowercaseInitial(String str) {
    return str.substring(0, 1).toLowerCase() + str.substring(1);
  }

  /**
   * 将字符串的首字母转换成大写字母.如果传入的字符串首字母为大写字母或者不是字母，则返回原值。<br>
   * 示例：
   * <ul>
   * <li>传入" upa"返回" upa"</li>
   * <li>传入"测试"返回"测试"</li>
   * <li>传入"upa"返回"Upa"</li>
   * </ul>
   * 
   * @param str 字符串
   * @return 首字母大写的字符串
   */
  public static final String uppercaseInitial(String str) {
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  /**
   * 将程序中的null字符串转换为""字符串.主要作用是使得程序不易抛出{@link NullPointerException}。 如果传入的字符串不为空，则不做处理，并返回原字符串。
   * 
   * @param str 字符串
   * @return 转换成""的结果
   */
  public static final String null2String(Object str) {
    return str == null ? "" : str.toString().trim();
  }

  /**
   * 判断传入的字符串是否为空.在判断前会将传入的参数做trim处理。
   * 
   * @param str 需要判断的字符串
   * @return 空字符串返回true，其他返回false
   */
  public static final boolean isEmpty(String str) {
    if (str == null) {
      return true;
    }
    str = str.trim();
    if ("".equals(str)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 判断传入的字符串是否不是空.在判断前会将传入的参数做trim处理。
   * 
   * @param str 需要判断的字符串
   * @return 空字符串返回false，其他返回true
   */
  public static final boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  /**
   * 判断某个字符串是否在目标字符串中存在.如果存在，则返回子字符串在目标字符串中的起始位置。如果不存在则返回-1。<br>
   * 示例：
   * <ul>
   * <li>传入"这是一个测试地址"和"个测"，返回3</li>
   * <li>传入"abcdefghi"和"gh"，返回6</li>
   * <li>传入"1234567890"和"6"，返回5</li>
   * <li>传入"这是一个测试地址"和"无"，返回-1</li>
   * </ul>
   * 
   * @param str 目标字符串
   * @param sub 子字符串
   * @return 目标字符串中包含子字符串的位置
   */
  public static final int indexOf(String str, String sub) {
    return str.indexOf(sub);
  }

  /**
   * 判断某个字符串是否在目标字符串中存在。如果存在则返回true，如果不存在则返回false。该方法调用了
   * {@linkplain #indexOf(java.lang.String, java.lang.String) indexOf}
   * 
   * @param str 字符串
   * @param sub 包含的字符串
   * @return boolean 是否包含
   */
  public static final boolean isIndexOf(String str, String sub) {
    int flag = indexOf(str, sub);
    if (flag != -1) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 生成一个固定长度的随机字符串. 大小写敏感。
   * 
   * @param length 生成的长度
   * @return 随机字符串
   */
  public static final String randomString(int length) {
    char[] numbersAndLetters =
        "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();
    if (length < 1) {
      return "";
    }
    Random randGen = new Random();
    char[] randBuffer = new char[length];
    for (int i = 0; i < randBuffer.length; i++) {
      randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
    }
    return new String(randBuffer);
  }

  /**
   * 根据标志位拆解字符串为字符串数组.
   * 
   * @param str 字符串
   * @param regex 标志位
   * @return 拆解后的字符串数组
   */
  public static final String[] splitString(String str, String regex) {
    if (isNotEmpty(str)) {
      String[] tmp = str.split(regex);
      String[] resultArray = new String[tmp.length];
      for (int i = 0; i < tmp.length; i++) {
        resultArray[i] = tmp[i];
      }
      return resultArray;
    } else {
      return new String[] {};
    }
  }

  /**
   * 字符串去前后空格.<br>
   * 去除前后空格的方式如下:
   * <ul>
   * <li>如果字符串以空格开始，则循环去除空格，直到字符串不再以空格开始为止。</li>
   * <li>如果字符串以空格结尾，则循环去除后空格，直到字符串不再以空格结尾为止。</li>
   * </ul>
   * 示例： <blockquote>传入<code>"  123 45 6 xd 测        "</code> 则返回
   * <code>"123 45 6 xd 测"</code></blockquote>
   * 
   * @param str 被操作的字符串
   * @return 去除前后空格后的字符串
   */
  public static final String trimSpaces(String str) {
    while (str.startsWith(" ")) {
      str = str.substring(1, str.length()).trim();
    }
    while (str.endsWith(" ")) {
      str = str.substring(0, str.length() - 1).trim();
    }
    return str;
  }

  /**
   * 判断一个字符是否是汉字.
   * 
   * @param character 需要判断的字符
   * @return 是汉字返回true，不是汉字返回false
   */
  public static final boolean isChinese(char character) {
    Character.UnicodeBlock ub = Character.UnicodeBlock.of(character);
    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
        || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
        || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
        || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
        || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
      return true;
    }
    return false;
  }

  /**
   * 将字符串编程字符数组.
   * 
   * @param str 操作的字符串
   * @return 散开后的字符数组
   */
  public static final char[] scatter(String str) {
    return str.toCharArray();
  }

  /**
   * 判断字符串中是否包含汉字.
   * 
   * @param str 检验的字符串
   * @return 任何字符是汉字返回true，否则返回false
   */
  public static final boolean isContainsChinese(String str) {
    if (isNotEmpty(str)) {
      for (char c : scatter(str)) {
        if (isChinese(c)) {
          return true;
        }
      }
      return false;
    } else {
      return false;
    }
  }

  /**
   * 判断字符串中是否不包含汉字.
   * 
   * @param str 检验的字符串
   * @return 任何字符是汉字返回false，否则返回true
   */
  public static final boolean isNotContainsChinese(String str) {
    return !isContainsChinese(str);
  }
}
