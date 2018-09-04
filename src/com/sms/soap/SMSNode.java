package com.sms.soap;


public class SMSNode  implements java.io.Serializable, org.apache.axis.encoding.SimpleType {
    private java.lang.String _value;
    private java.lang.String phone;  // attribute
    private java.lang.String recDateTime;  // attribute
    private java.lang.String postFixNumber;  // attribute

    public SMSNode() {
    }

    // Simple Types must have a String constructor
    public SMSNode(java.lang.String _value) {
        this._value = _value;
    }
    // Simple Types must have a toString for serializing the value
    public java.lang.String toString() {
        return _value;
    }


    /**
     * Gets the _value value for this SMSNode.
     * 
     * @return _value
     */
    public java.lang.String get_value() {
        return _value;
    }


    /**
     * Sets the _value value for this SMSNode.
     * 
     * @param _value
     */
    public void set_value(java.lang.String _value) {
        this._value = _value;
    }


    /**
     * Gets the phone value for this SMSNode.
     * 
     * @return phone
     */
    public java.lang.String getPhone() {
        return phone;
    }


    /**
     * Sets the phone value for this SMSNode.
     * 
     * @param phone
     */
    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }


    /**
     * Gets the recDateTime value for this SMSNode.
     * 
     * @return recDateTime
     */
    public java.lang.String getRecDateTime() {
        return recDateTime;
    }


    /**
     * Sets the recDateTime value for this SMSNode.
     * 
     * @param recDateTime
     */
    public void setRecDateTime(java.lang.String recDateTime) {
        this.recDateTime = recDateTime;
    }


    /**
     * Gets the postFixNumber value for this SMSNode.
     * 
     * @return postFixNumber
     */
    public java.lang.String getPostFixNumber() {
        return postFixNumber;
    }


    /**
     * Sets the postFixNumber value for this SMSNode.
     * 
     * @param postFixNumber
     */
    public void setPostFixNumber(java.lang.String postFixNumber) {
        this.postFixNumber = postFixNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SMSNode)) return false;
        SMSNode other = (SMSNode) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this._value==null && other.get_value()==null) || 
             (this._value!=null &&
              this._value.equals(other.get_value()))) &&
            ((this.phone==null && other.getPhone()==null) || 
             (this.phone!=null &&
              this.phone.equals(other.getPhone()))) &&
            ((this.recDateTime==null && other.getRecDateTime()==null) || 
             (this.recDateTime!=null &&
              this.recDateTime.equals(other.getRecDateTime()))) &&
            ((this.postFixNumber==null && other.getPostFixNumber()==null) || 
             (this.postFixNumber!=null &&
              this.postFixNumber.equals(other.getPostFixNumber())));
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
        if (get_value() != null) {
            _hashCode += get_value().hashCode();
        }
        if (getPhone() != null) {
            _hashCode += getPhone().hashCode();
        }
        if (getRecDateTime() != null) {
            _hashCode += getRecDateTime().hashCode();
        }
        if (getPostFixNumber() != null) {
            _hashCode += getPostFixNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}
