package unicorn.common.internet;

import unicorn.common.charset.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {
  /**
   * 获取访问者的IP地址.
   * 
   * @param request HttpServletReqeust对象
   * @return 访问者的IP地址
   */
  public static String getIpAddr(HttpServletRequest request) {
    String ipAddr = request.getHeader("x-forwarded-for");
    if (StringUtils.isEmpty(ipAddr) || "unknown".equalsIgnoreCase(ipAddr)) {
      ipAddr = request.getHeader("Proxy-Client-IP");
    }
    if (StringUtils.isEmpty(ipAddr) || "unknown".equalsIgnoreCase(ipAddr)) {
      ipAddr = request.getHeader("WL-Proxy-Client-IP");
    }
    if (StringUtils.isEmpty(ipAddr) || "unknown".equalsIgnoreCase(ipAddr)) {
      ipAddr = request.getRemoteAddr();
    }
    if (StringUtils.isNotEmpty(ipAddr) && ipAddr.length() > 15) {
      if (ipAddr.indexOf(",") > 0) {
        ipAddr = ipAddr.substring(0, ipAddr.indexOf(","));
      }
    }
    return ipAddr;
  }
}
