/**
 * Project Name:poll3
 * File Name:SurveyStatisticsVM.java
 * Package Name:com.briup.apps.poll.vm
 * Date:2019年3月6日下午4:25:08
 * Copyright (c) 2019, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.briup.apps.poll.vm;
/**
 * ClassName:SurveyStatisticsVM <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2019年3月6日 下午4:25:08 <br/>
 * @author   lichunyu
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class SurveyStatisticsVM {
	private Long userId;
	private String username;	//姓名
	private String average;	//平均分
	private String min;		//最低分
	private String max;		//最高分
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAverage() {
		return average;
	}
	public void setAverage(String average) {
		this.average = average;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	
}

