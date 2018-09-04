package com.sms.soap;

public class SendStatus  implements java.io.Serializable {
    private String retCode;
    private java.lang.String message;
    private int jobID;
    private int OKPhoneCounts;
    private int stockReduced;
    private java.lang.String errPhones;

    public SendStatus() {
    }

    public SendStatus(
           String retCode,
           java.lang.String message,
           int jobID,
           int OKPhoneCounts,
           int stockReduced,
           java.lang.String errPhones) {
           this.retCode = retCode;
           this.message = message;
           this.jobID = jobID;
           this.OKPhoneCounts = OKPhoneCounts;
           this.stockReduced = stockReduced;
           this.errPhones = errPhones;
    }


    /**
     * Gets the retCode value for this SendStatus.
     * 
     * @return retCode
     */
    public String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this SendStatus.
     * 
     * @param retCode
     */
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the message value for this SendStatus.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this SendStatus.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the jobID value for this SendStatus.
     * 
     * @return jobID
     */
    public int getJobID() {
        return jobID;
    }


    /**
     * Sets the jobID value for this SendStatus.
     * 
     * @param jobID
     */
    public void setJobID(int jobID) {
        this.jobID = jobID;
    }


    /**
     * Gets the OKPhoneCounts value for this SendStatus.
     * 
     * @return OKPhoneCounts
     */
    public int getOKPhoneCounts() {
        return OKPhoneCounts;
    }


    /**
     * Sets the OKPhoneCounts value for this SendStatus.
     * 
     * @param OKPhoneCounts
     */
    public void setOKPhoneCounts(int OKPhoneCounts) {
        this.OKPhoneCounts = OKPhoneCounts;
    }


    /**
     * Gets the stockReduced value for this SendStatus.
     * 
     * @return stockReduced
     */
    public int getStockReduced() {
        return stockReduced;
    }


    /**
     * Sets the stockReduced value for this SendStatus.
     * 
     * @param stockReduced
     */
    public void setStockReduced(int stockReduced) {
        this.stockReduced = stockReduced;
    }


    /**
     * Gets the errPhones value for this SendStatus.
     * 
     * @return errPhones
     */
    public java.lang.String getErrPhones() {
        return errPhones;
    }


    /**
     * Sets the errPhones value for this SendStatus.
     * 
     * @param errPhones
     */
    public void setErrPhones(java.lang.String errPhones) {
        this.errPhones = errPhones;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SendStatus)) return false;
        SendStatus other = (SendStatus) obj;
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
            this.jobID == other.getJobID() &&
            this.OKPhoneCounts == other.getOKPhoneCounts() &&
            this.stockReduced == other.getStockReduced() &&
            ((this.errPhones==null && other.getErrPhones()==null) || 
             (this.errPhones!=null &&
              this.errPhones.equals(other.getErrPhones())));
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
        _hashCode += getJobID();
        _hashCode += getOKPhoneCounts();
        _hashCode += getStockReduced();
        if (getErrPhones() != null) {
            _hashCode += getErrPhones().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}