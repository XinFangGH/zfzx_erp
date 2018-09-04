
package com.thirdPayInterface.UMPay.UMPayUtil;
/**
 * ***********************************************************************
 * <br>description : 签名验签校验异常
 * @author      umpay
 * @date        2014-7-24 下午07:42:26
 * @version     1.0  
 ************************************************************************
 */
public class VerifyException extends Exception{

	private static final long serialVersionUID = 7793118467616878809L;
	
	public VerifyException(){
		super();
	}
	public VerifyException(String msg){
		super(msg);
	}
	public VerifyException(String msg,Throwable ex){
		super(msg,ex);
	}
	public VerifyException(Throwable ex){
		super(ex);
	}

}
