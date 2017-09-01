package unicorn.common.os;

/**
 * 所有操作系统的枚举. 包含着所有系统类型，用于判断当前系统。
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @since 1.0.0
 */
public enum OperatingSystem {
  Any("any"), Linux("Linux"), Mac_OS("Mac OS"), Mac_OS_X("Mac OS X"), Windows(
      "Windows"), OS2("OS/2"), Solaris("Solaris"), SunOS("SunOS"), MPEiX("MPE/iX"), HP_UX(
          "HP-UX"), AIX("AIX"), OS390("OS/390"), FreeBSD("FreeBSD"), Irix(
              "Irix"), Digital_Unix("Digital Unix"), NetWare_411(
                  "NetWare"), OSF1("OSF1"), OpenVMS("OpenVMS"), Others("Others");

  private OperatingSystem(String desc) {
    description = desc;
  }

  public String toString() {
    return description;
  }

  private String description;
}
