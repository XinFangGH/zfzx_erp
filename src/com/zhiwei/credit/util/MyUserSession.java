package com.zhiwei.credit.util;

public class MyUserSession {
	
	public static final String MEMBEER_SESSION = "hurong_website";//登录信息
	public static final String TIMEOUT_SESSION = "time_out";// 邮箱验证 手机验证超时设置
	public static final String TELPHONE_REG_RANDOM_SESSION = "telphone_reg_random";//注册手机验证码
	public static final String EMAIL_REG_RANDOM_SESSION = "email_reg_random";//
	public static final String FORM_TOKEN="form_token";//表单提交 session 防止重复提交
	
	public static final String lOCK_NUM = "lock_num";//登陆失败次数，登陆成功后清零
	public static final String LOCK_NAME = "lock_name";//登陆失败名字
	public static final String LOCK_TIME = "lock_time";//登陆锁定时间--锁定的账号还是ip？
	
	public static final String RANDOM_SESSION = "random_session";//随机码--邮箱验证、短信验证--
	public static final String TEMP_MEMBER = "temp_session";//邮箱、短信找回密码时，临时存放用户
	
	
	/***********用户调用短信接口**************/
	public static final String SMS_PHONE = "sms_phone";//接收手机
	public static final String SMS_IP = "sms_ip";//登陆ip
	public static final String SMS_USER = "sms_user";//登陆用户
	public static final String SMS_NUMBER = "sms_number";//发送次数
	public static final String SMS_TIME = "sms_time";//首次发送时间
	/************************/
	
	
	
	
	

}
