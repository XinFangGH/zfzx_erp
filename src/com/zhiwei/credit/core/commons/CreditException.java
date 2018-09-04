package com.zhiwei.credit.core.commons;

/**
 * @function exception
 * @author credit004
 *
 */
public class CreditException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CreditException(){
		super();
	}
	
	public CreditException(String msg){
		super(msg);
	}
	
	public CreditException(String msg, Throwable cause){
		super(msg);
		super.initCause(cause);
	}
	
	public CreditException(Throwable cause) {
	    super();
	    super.initCause(cause);
	  }
}
