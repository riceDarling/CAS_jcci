package unicorn.common.os;

/**
 * 判断当前的操作系统类型
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @since 1.0.0
 */
public final class OsUtils {
  /*
   * 从系统中获取的当前操作系统类型。
   */
  private static String CURRENT_OS = System.getProperty("os.name").toLowerCase();

  /*
   * OsUtils的实例。
   */
  private static OsUtils instance = new OsUtils();

  /*
   * OperatingSystem的实例。
   */
  private OperatingSystem os;

  /*
   * 不允许实例化.
   */
  private OsUtils() {}

  public static boolean isLinux() {
    return CURRENT_OS.indexOf("linux") >= 0;
  }

  public static boolean isMacOs() {
    return CURRENT_OS.indexOf("mac") >= 0 && CURRENT_OS.indexOf("os") > 0
        && CURRENT_OS.indexOf("x") < 0;
  }

  public static boolean isMacOsX() {
    return CURRENT_OS.indexOf("mac") >= 0 && CURRENT_OS.indexOf("os") > 0
        && CURRENT_OS.indexOf("x") > 0;
  }

  public static boolean isWindows() {
    return CURRENT_OS.indexOf("windows") >= 0;
  }

  public static boolean isOs2() {
    return CURRENT_OS.indexOf("os/2") >= 0;
  }

  public static boolean isSolaris() {
    return CURRENT_OS.indexOf("solaris") >= 0;
  }

  public static boolean isSunOs() {
    return CURRENT_OS.indexOf("sunos") >= 0;
  }

  public static boolean isMpEiX() {
    return CURRENT_OS.indexOf("mpe/ix") >= 0;
  }

  public static boolean isHpUx() {
    return CURRENT_OS.indexOf("hp-ux") >= 0;
  }

  public static boolean isAix() {
    return CURRENT_OS.indexOf("aix") >= 0;
  }

  public static boolean isOs390() {
    return CURRENT_OS.indexOf("os/390") >= 0;
  }

  public static boolean isFreeBsd() {
    return CURRENT_OS.indexOf("freebsd") >= 0;
  }

  public static boolean isIrix() {
    return CURRENT_OS.indexOf("irix") >= 0;
  }

  public static boolean isDigitalUnix() {
    return CURRENT_OS.indexOf("digital") >= 0 && CURRENT_OS.indexOf("unix") > 0;
  }

  public static boolean isNetWare() {
    return CURRENT_OS.indexOf("netware") >= 0;
  }

  public static boolean isOsF1() {
    return CURRENT_OS.indexOf("osf1") >= 0;
  }

  public static boolean isOpenVms() {
    return CURRENT_OS.indexOf("openvms") >= 0;
  }

  /**
   * 获取操作系统的枚举.
   * 
   * @return 操作系统的枚举
   */
  public static OperatingSystem getOsName() {
    if (isAix()) {
      instance.os = OperatingSystem.AIX;
    } else if (isDigitalUnix()) {
      instance.os = OperatingSystem.Digital_Unix;
    } else if (isFreeBsd()) {
      instance.os = OperatingSystem.FreeBSD;
    } else if (isHpUx()) {
      instance.os = OperatingSystem.HP_UX;
    } else if (isIrix()) {
      instance.os = OperatingSystem.Irix;
    } else if (isLinux()) {
      instance.os = OperatingSystem.Linux;
    } else if (isMacOs()) {
      instance.os = OperatingSystem.Mac_OS;
    } else if (isMacOsX()) {
      instance.os = OperatingSystem.Mac_OS_X;
    } else if (isMpEiX()) {
      instance.os = OperatingSystem.MPEiX;
    } else if (isNetWare()) {
      instance.os = OperatingSystem.NetWare_411;
    } else if (isOpenVms()) {
      instance.os = OperatingSystem.OpenVMS;
    } else if (isOs2()) {
      instance.os = OperatingSystem.OS2;
    } else if (isOs390()) {
      instance.os = OperatingSystem.OS390;
    } else if (isOsF1()) {
      instance.os = OperatingSystem.OSF1;
    } else if (isSolaris()) {
      instance.os = OperatingSystem.Solaris;
    } else if (isSunOs()) {
      instance.os = OperatingSystem.SunOS;
    } else if (isWindows()) {
      instance.os = OperatingSystem.Windows;
    } else {
      instance.os = OperatingSystem.Others;
    }
    return instance.os;
  }
}
