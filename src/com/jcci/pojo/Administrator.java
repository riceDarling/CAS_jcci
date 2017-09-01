package com.jcci.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import unicorn.common.excel.annotation.ExcelInstruction;
import unicorn.common.excel.bind.Disguiser;
import unicorn.project.pojo.EmptyCheckRule;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 管理员的贫血类.主要存储管理员的参数，可以包含简单的验证方法。<br>
 * 该类和其他类关联时没有使用内部类的形式，原因有二。一是字典表设计的并不适合做内部类使用。二是在该类的使用环境中还没有使用大量内部类数据的需求和预设需求。所以讲其他类的字段冗余到了该类中。
 * 
 * @author 周峰
 * @time 2016年7月22日 上午11:02:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
public class Administrator implements EmptyCheckRule, Comparable<Administrator> {
	
  /*
   * 管理员的主键。
   */
  @DecimalMax(value = "4294967295", message = "1")
  private Long adminId;
  
  /*
   * 管理员的类型
   */
  @Max(255) // 数据库中为tinyint类型的字段,最大值为255
  private Integer adminType;
 
  /*
   * 管理员的用户名。
   */
  @ExcelInstruction(order = "1", title = "账户名")
  @Size(max = 64, message = "1")
  private String adminCode;
  /*
   * 管理员的密码。
   */
  @Size(max = 32, message = "1") // 长度应固定为32，MD5加密
  private String adminPwd;
  /*
   * 管理员的手机号。
   */
  @ExcelInstruction(order = "5", title = "手机")
  @Pattern(regexp = "^1[3|4|5|7|8]\\d{9}$", message = "1")
  @Size(max = 14, message = "1")
  private String adminPhone;




  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Administrator ");
    sb.append("adminId=").append(adminId).append(",");
    sb.append("adminCode=").append(adminCode).append(",");
    sb.append("adminPwd=").append(adminPwd).append(",");
    sb.append("adminPhone=").append(adminPhone).append(",");
    return sb.toString();
  }

  @Override
  @JsonIgnore
  public boolean isEmpty() {
    if (getAdminId() == null && isEmptyWithOutId()) {
      return true;
    } else {
      return false;
    }
  }


  @Override
  @JsonIgnore
  public boolean isMeetD() {
    if (getAdminId() == null) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  @JsonIgnore
  public boolean isMeetU() {
    if (getAdminId() == null) {
      return false;
    } else if (isEmptyWithOutId()) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Administrator) {
      Administrator admin = (Administrator) obj;
      if (this.adminId == admin.getAdminId()) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
  
  
  
  @Override
  public int compareTo(Administrator o) {
  	// TODO Auto-generated method stub
  	return 0;
  }

  @Override
  public boolean isEmptyWithOutId() {
  	// TODO Auto-generated method stub
  	return false;
  }

  @Override
  public boolean isMeetC() {
  	// TODO Auto-generated method stub
  	return false;
  }

  

  public Long getAdminId() {
    return adminId;
  }

  public void setAdminId(Long adminId) {
    this.adminId = adminId;
  }



  public Integer getAdminType() {
    return adminType;
  }

  public void setAdminType(Integer adminType) {
    this.adminType = adminType;
  }





  public String getAdminCode() {
    return adminCode;
  }

  public void setAdminCode(String adminCode) {
    this.adminCode = adminCode;
  }

  /**
   * 读取的所有值都会被转换成小写.
   * 
   * @return 转换成小写的pwd
   */
  public String getAdminPwd() {
    if (adminPwd != null) {
      return adminPwd.toLowerCase();
    } else {
      return adminPwd;
    }
  }

  /**
   * 存入的所有值都会被转换成小写.
   * 
   * @param adminPwd MD5加密的密码
   */
  public void setAdminPwd(String adminPwd) {
    if (adminPwd != null) {
      this.adminPwd = adminPwd.toLowerCase();
    } else {
      this.adminPwd = adminPwd;
    }
  }

  public String getAdminPhone() {
    return adminPhone;
  }

  public void setAdminPhone(String adminPhone) {
    this.adminPhone = adminPhone;
  }

  private class AdminStatusDisguiser implements Disguiser<String> {
    @Override
    public String mask(Object param) {
      Boolean bool = (Boolean) param;
      if (bool) {
        return "启用";
      } else {
        return "停用";
      }
    }
  }


}
