package com.jcci.pojo;


/**
 * 车漆信息的贫血类.主要存储车辆的参数，可以包含简单的验证方法。<br>
 * 
 * @author 周峰
 * @time 2017年8月8日 上午10:06:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
public class CarPaintInfo {
	/*
	 * id(主键)
	 * */
	private Long carPId;
	
	/*
	 * 车漆名称
	 * */
	private String carPName;
	
	/*
	 * 车漆图标路径
	 * */
	private String carPIcon;
	
	/*
	 * 车漆颜色
	 * */
	private String carPaint;
	
	/*
	 * 车辆信息ID
	 * */
	private Long carId;
	
	
	@Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("CarPaintInfo ");
	    sb.append("carPId=").append(carPId).append(",");
	    sb.append("carPName=").append(carPName).append(",");
	    sb.append("carPIcon=").append(carPIcon).append(",");
	    sb.append("carPaint=").append(carPaint).append(",");
	    sb.append("carId=").append(carId);
	    return sb.toString();
	  }
	

	public Long getCarPId() {
		return carPId;
	}

	public void setCarPId(Long carPId) {
		this.carPId = carPId;
	}

	public String getCarPName() {
		return carPName;
	}

	public void setCarPName(String carPName) {
		this.carPName = carPName;
	}

	public String getCarPIcon() {
		return carPIcon;
	}

	public void setCarPIcon(String carPIcon) {
		this.carPIcon = carPIcon;
	}

	public String getCarPaint() {
		return carPaint;
	}

	public void setCarPaint(String carPaint) {
		this.carPaint = carPaint;
	}

	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}
}
