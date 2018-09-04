package com.zhiwei.credit.model.personal;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

/**
 * 用于设置公司的班制
 * @author csx
 *
 */
public class DutySystemSections {
	//周日对应的班次名称，如上午班次,下午班次
	private String day0;
	
	private String day1;
	private String day2;
	private String day3;
	private String day4;
	private String day5;
	private String day6;
	
	//周日对应的班次的ID，如1,2
	private String dayId0;
	private String dayId1;
	private String dayId2;
	private String dayId3;
	private String dayId4;
	private String dayId5;
	private String dayId6;
	
	public String getDay0() {
		return day0;
	}
	public void setDay0(String day0) {
		this.day0 = day0;
	}
	public String getDay1() {
		return day1;
	}
	public void setDay1(String day1) {
		this.day1 = day1;
	}
	public String getDay2() {
		return day2;
	}
	public void setDay2(String day2) {
		this.day2 = day2;
	}
	public String getDay3() {
		return day3;
	}
	public void setDay3(String day3) {
		this.day3 = day3;
	}
	public String getDay4() {
		return day4;
	}
	public void setDay4(String day4) {
		this.day4 = day4;
	}
	public String getDay5() {
		return day5;
	}
	public void setDay5(String day5) {
		this.day5 = day5;
	}
	public String getDay6() {
		return day6;
	}
	public void setDay6(String day6) {
		this.day6 = day6;
	}
	public String getDayId0() {
		return dayId0;
	}
	public void setDayId0(String dayId0) {
		this.dayId0 = dayId0;
	}
	public String getDayId1() {
		return dayId1;
	}
	public void setDayId1(String dayId1) {
		this.dayId1 = dayId1;
	}
	public String getDayId2() {
		return dayId2;
	}
	public void setDayId2(String dayId2) {
		this.dayId2 = dayId2;
	}
	public String getDayId3() {
		return dayId3;
	}
	public void setDayId3(String dayId3) {
		this.dayId3 = dayId3;
	}
	public String getDayId4() {
		return dayId4;
	}
	public void setDayId4(String dayId4) {
		this.dayId4 = dayId4;
	}
	public String getDayId5() {
		return dayId5;
	}
	public void setDayId5(String dayId5) {
		this.dayId5 = dayId5;
	}
	public String getDayId6() {
		return dayId6;
	}
	public void setDayId6(String dayId6) {
		this.dayId6 = dayId6;
	}
	
	/**
	 * 班次字符串
	 */
	public String dayToString(){
		return day0 + "|" + day1 + "|" + day2 + "|"+ day3 + "|"+ day4 + "|"+ day5 + "|" + day6 ;
	}
	
	/**
	 * 班次字符串
	 */
	public String dayIdToString(){
		return dayId0 + "|" + dayId1 + "|" + dayId2 + "|"+ dayId3 + "|"+ dayId4 + "|"+ dayId5 + "|" + dayId6 ;
	}
	
}
