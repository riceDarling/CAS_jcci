package com.jcci.pojo;


/**
 * 亮点信息的贫血类.主要存储车辆的参数，可以包含简单的验证方法。<br>
 * 
 * @author 周峰
 * @time 2017年8月8日 上午10:06:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
public class CarBrightInfo {

	/*
	 * id(主键)
	 * */
	private Long carSId;
	
	/*
	 * 状态，是否启用
	 * */
	private Long carState;
	
	/*
	 * 背景图路径
	 * */
	private String carSBgImg;
	
	/*
	 * 描述颜色
	 * */
	private String carSDescribeColor;
	
	/*
	 * 亮点描述
	 * */
	private String carSDescribe;
	
	/*
	 * 图片
	 * */
	private String carSImg;
	
	/*
	 * 短视频
	 * */
	private String carSMv;
	
	/*
	 * 超链路协议
	 * */
	private String carSHdlc;
	
	/*
	 * 选中的参数
	 * */
	private String carSelecyedParameter;
	
	/*
	 * 车辆信息ID
	 * */
	private String carId;

	
	
	@Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("CarBrightInfo ");
	    sb.append("carSId=").append(carSId).append(",");
	    sb.append("carState=").append(carState).append(",");
	    sb.append("carSBgImg=").append(carSBgImg).append(",");
	    sb.append("carSDescribeColor=").append(carSDescribeColor).append(",");
	    sb.append("carSDescribe=").append(carSDescribe).append(",");
	    sb.append("carSImg=").append(carSImg).append(",");
	    sb.append("carSMv=").append(carSMv).append(",");
	    sb.append("carSHdlc=").append(carSHdlc).append(",");
	    sb.append("carSelecyedParameter=").append(carSelecyedParameter).append(",");
	    sb.append("carId=").append(carId);
	    return sb.toString();
	  }
	
	
	
	
	public Long getCarSId() {
		return carSId;
	}

	public void setCarSId(Long carSId) {
		this.carSId = carSId;
	}

	public Long getCarState() {
		return carState;
	}

	public void setCarState(Long carState) {
		this.carState = carState;
	}

	public String getCarSBgImg() {
		return carSBgImg;
	}

	public void setCarSBgImg(String carSBgImg) {
		this.carSBgImg = carSBgImg;
	}

	public String getCarSDescribeColor() {
		return carSDescribeColor;
	}

	public void setCarSDescribeColor(String carSDescribeColor) {
		this.carSDescribeColor = carSDescribeColor;
	}

	public String getCarSDescribe() {
		return carSDescribe;
	}

	public void setCarSDescribe(String carSDescribe) {
		this.carSDescribe = carSDescribe;
	}

	public String getCarSImg() {
		return carSImg;
	}

	public void setCarSImg(String carSImg) {
		this.carSImg = carSImg;
	}

	public String getCarSMv() {
		return carSMv;
	}

	public void setCarSMv(String carSMv) {
		this.carSMv = carSMv;
	}

	public String getCarSHdlc() {
		return carSHdlc;
	}

	public void setCarSHdlc(String carSHdlc) {
		this.carSHdlc = carSHdlc;
	}

	public String getCarSelecyedParameter() {
		return carSelecyedParameter;
	}

	public void setCarSelecyedParameter(String carSelecyedParameter) {
		this.carSelecyedParameter = carSelecyedParameter;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}
}
