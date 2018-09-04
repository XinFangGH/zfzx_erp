package com.sms.soap;


public class CommonReturn  implements java.io.Serializable {
    private String retCode;
    private java.lang.String message;

    public CommonReturn() {
    }

    public CommonReturn(
           String retCode,
           java.lang.String message) {
           this.retCode = retCode;
           this.message = message;
    }


    /**
     * Gets the retCode value for this CommonReturn.
     * 
     * @return retCode
     */
    public String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this CommonReturn.
     * 
     * @param retCode
     */
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the message value for this CommonReturn.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this CommonReturn.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CommonReturn)) return false;
        CommonReturn other = (CommonReturn) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.retCode==null && other.getRetCode()==null) || 
             (this.retCode!=null &&
              this.retCode.equals(other.getRetCode()))) &&
            ((this.message==null && other.getMessage()==null) || 
             (this.message!=null &&
              this.message.equals(other.getMessage())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getRetCode() != null) {
            _hashCode += getRetCode().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}