package com.thirdPayInteface.UMPay.UMPayUtil;

/**
 * ***********************************************************************
 * <br>description : 参数校验异常
 * @author      umpay
 * @date        2014-7-24 下午07:42:26
 * @version     1.0  
 ************************************************************************
 */
public class ParameterCheckException extends RuntimeException{

	private static final long serialVersionUID = 7793118467616878809L;
	
	public ParameterCheckException(){
		super();
	}
	public ParameterCheckException(String msg){
		super(msg);
	}
	public ParameterCheckException(String msg,Throwable ex){
		super(msg,ex);
	}

}
