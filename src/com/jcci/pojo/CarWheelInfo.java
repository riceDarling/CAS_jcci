package com.jcci.pojo;


/**
 * 车轮毅信息的贫血类.主要存储车辆的参数，可以包含简单的验证方法。<br>
 * 
 * @author 周峰
 * @time 2017年8月8日 上午10:06:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
public class CarWheelInfo {
	
	/*
	 * id(主键)
	 * */
	private Long carHId;
	
	/*
	 * 名称
	 * */
	private String carHName;
	
	/*
	 * 图标
	 * */
	private String carHIcon;
	
	/*
	 * 车辆信息ID
	 * */
	private Long carId;

	
	
	@Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("CarWheelInfo ");
	    sb.append("carHId=").append(carHId).append(",");
	    sb.append("carHName=").append(carHName).append(",");
	    sb.append("carHIcon=").append(carHIcon).append(",");
	    sb.append("carId=").append(carId);
	    return sb.toString();
	  }
	
	
	
	
	public Long getCarHId() {
		return carHId;
	}

	public void setCarHId(Long carHId) {
		this.carHId = carHId;
	}

	public String getCarHName() {
		return carHName;
	}

	public void setCarHName(String carHName) {
		this.carHName = carHName;
	}

	public String getCarHIcon() {
		return carHIcon;
	}

	public void setCarHIcon(String carHIcon) {
		this.carHIcon = carHIcon;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}
}
