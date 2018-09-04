
/**
 * Exception.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

package nc.itf.crd.webservice.izyhtwebservice;

public class Exception extends java.lang.Exception{
    
    private nc.uap.ws.lang.ExceptionDocument faultMessage;
    
    public Exception() {
        super("Exception");
    }
           
    public Exception(java.lang.String s) {
       super(s);
    }
    
    public Exception(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(nc.uap.ws.lang.ExceptionDocument msg){
       faultMessage = msg;
    }
    
    public nc.uap.ws.lang.ExceptionDocument getFaultMessage(){
       return faultMessage;
    }
}
    