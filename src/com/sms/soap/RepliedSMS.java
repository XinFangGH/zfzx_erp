package com.sms.soap;

public class RepliedSMS  implements java.io.Serializable {
    private String retCode;
    private java.lang.String message;
    private int count;
    private ArrayOfSMSNode nodes;

    public RepliedSMS() {
    }

    public RepliedSMS(
           String retCode,
           java.lang.String message,
           int count,
           ArrayOfSMSNode nodes) {
           this.retCode = retCode;
           this.message = message;
           this.count = count;
           this.nodes = nodes;
    }


    /**
     * Gets the retCode value for this RepliedSMS.
     * 
     * @return retCode
     */
    public String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this RepliedSMS.
     * 
     * @param retCode
     */
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the message value for this RepliedSMS.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this RepliedSMS.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the count value for this RepliedSMS.
     * 
     * @return count
     */
    public int getCount() {
        return count;
    }


    /**
     * Sets the count value for this RepliedSMS.
     * 
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }


    /**
     * Gets the nodes value for this RepliedSMS.
     * 
     * @return nodes
     */
    public ArrayOfSMSNode getNodes() {
        return nodes;
    }


    /**
     * Sets the nodes value for this RepliedSMS.
     * 
     * @param nodes
     */
    public void setNodes(ArrayOfSMSNode nodes) {
        this.nodes = nodes;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RepliedSMS)) return false;
        RepliedSMS other = (RepliedSMS) obj;
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
              this.message.equals(other.getMessage()))) &&
            this.count == other.getCount() &&
            ((this.nodes==null && other.getNodes()==null) || 
             (this.nodes!=null &&
              this.nodes.equals(other.getNodes())));
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
        _hashCode += getCount();
        if (getNodes() != null) {
            _hashCode += getNodes().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}