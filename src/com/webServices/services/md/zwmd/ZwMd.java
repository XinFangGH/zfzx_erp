package com.webServices.services.md.zwmd;

public class ZwMd {
	private String appcode;//  系统编码 测试系统为 001
	private String password; //密码   测试系统为1
	private int num ;         //本次传输数据数量 
	private String date;      //当前日期
	
	public String getAppcode() {
		return appcode;
	}
	public void setAppcode(String appcode) {
		this.appcode = appcode;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
