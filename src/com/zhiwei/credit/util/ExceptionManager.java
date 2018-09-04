package com.zhiwei.credit.util;

public class ExceptionManager extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExceptionManager(){
		super();
	}
	
    public ExceptionManager(String message) {
       super(message);
    }
    
    public ExceptionManager(String message, Throwable cause) {
       super(message, cause);
    }
   
    public ExceptionManager(Throwable cause) {
       super(cause);
    }

}
