package com.jcci.pojo;

import javax.validation.constraints.DecimalMax;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 车辆信息的贫血类.主要存储车辆的参数，可以包含简单的验证方法。<br>
 * 
 * @author 周峰
 * @time 2017年8月8日 上午10:06:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
public class CarRegisterInfo {

	/*
	   * 车辆信息的主键。
	   */
	  @DecimalMax(value = "4294967295", message = "1")
	  private Long carId;
	  
	  /*
	   * 名称。
	   */
	  private String carName;
	  
	  /*
	   * 品牌。
	   */
	  private String carBrand;
	
	  /*
	   * 版本
	   * */
	  private String carVersion;
	  
	  /*
	   * SDK版本
	   * */
	  private String carSdkVersion;
	  
	  /*
	   * 更新时间
	   * */
	  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	  private String carUpdateTime;
	  
	  /*
	   * 下载背景图片路径
	   * */
	  private String carDoenLoadBgImg;
	  
	  /*
	   * 下载描述
	   * */
	  private String carDoenLoadSpeak;
	  
	  
	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("CarRegisterInfo ");
	    sb.append("carId=").append(carId).append(",");
	    sb.append("carName=").append(carName).append(",");
	    sb.append("carBrand=").append(carBrand).append(",");
	    sb.append("carVersion=").append(carVersion).append(",");
	    sb.append("carSdkVersion=").append(carSdkVersion).append(",");
	    sb.append("carUpdateTime=").append(carUpdateTime).append(",");
	    sb.append("carDoenLoadBgImg=").append(carDoenLoadBgImg).append(",");
	    sb.append("carDoenLoadSpeak=").append(carDoenLoadSpeak);
	    return sb.toString();
	  }


	public Long getCarId() {
		return carId;
	}


	public void setCarId(Long carId) {
		this.carId = carId;
	}


	public String getCarName() {
		return carName;
	}


	public void setCarName(String carName) {
		this.carName = carName;
	}


	public String getCarBrand() {
		return carBrand;
	}


	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}


	public String getCarVersion() {
		return carVersion;
	}


	public void setCarVersion(String carVersion) {
		this.carVersion = carVersion;
	}


	public String getCarSdkVersion() {
		return carSdkVersion;
	}


	public void setCarSdkVersion(String carSdkVersion) {
		this.carSdkVersion = carSdkVersion;
	}


	public String getCarUpdateTime() {
		return carUpdateTime;
	}


	public void setCarUpdateTime(String carUpdateTime) {
		this.carUpdateTime = carUpdateTime;
	}


	public String getCarDoenLoadBgImg() {
		return carDoenLoadBgImg;
	}


	public void setCarDoenLoadBgImg(String carDoenLoadBgImg) {
		this.carDoenLoadBgImg = carDoenLoadBgImg;
	}


	public String getCarDoenLoadSpeak() {
		return carDoenLoadSpeak;
	}


	public void setCarDoenLoadSpeak(String carDoenLoadSpeak) {
		this.carDoenLoadSpeak = carDoenLoadSpeak;
	}


	
}
