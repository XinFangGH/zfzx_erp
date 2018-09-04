package com.sms.soap;



public class StockDetails  implements java.io.Serializable {
    private String retCode;
    private java.lang.String message;
    private int stockRemain;
    private int points;
    private int sendTotal;
    private int curDaySend;

    public StockDetails() {
    }

    public StockDetails(
           String retCode,
           java.lang.String message,
           int stockRemain,
           int points,
           int sendTotal,
           int curDaySend) {
           this.retCode = retCode;
           this.message = message;
           this.stockRemain = stockRemain;
           this.points = points;
           this.sendTotal = sendTotal;
           this.curDaySend = curDaySend;
    }


    /**
     * Gets the retCode value for this StockDetails.
     * 
     * @return retCode
     */
    public String getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this StockDetails.
     * 
     * @param retCode
     */
    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the message value for this StockDetails.
     * 
     * @return message
     */
    public java.lang.String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this StockDetails.
     * 
     * @param message
     */
    public void setMessage(java.lang.String message) {
        this.message = message;
    }


    /**
     * Gets the stockRemain value for this StockDetails.
     * 
     * @return stockRemain
     */
    public int getStockRemain() {
        return stockRemain;
    }


    /**
     * Sets the stockRemain value for this StockDetails.
     * 
     * @param stockRemain
     */
    public void setStockRemain(int stockRemain) {
        this.stockRemain = stockRemain;
    }


    /**
     * Gets the points value for this StockDetails.
     * 
     * @return points
     */
    public int getPoints() {
        return points;
    }


    /**
     * Sets the points value for this StockDetails.
     * 
     * @param points
     */
    public void setPoints(int points) {
        this.points = points;
    }


    /**
     * Gets the sendTotal value for this StockDetails.
     * 
     * @return sendTotal
     */
    public int getSendTotal() {
        return sendTotal;
    }


    /**
     * Sets the sendTotal value for this StockDetails.
     * 
     * @param sendTotal
     */
    public void setSendTotal(int sendTotal) {
        this.sendTotal = sendTotal;
    }


    /**
     * Gets the curDaySend value for this StockDetails.
     * 
     * @return curDaySend
     */
    public int getCurDaySend() {
        return curDaySend;
    }


    /**
     * Sets the curDaySend value for this StockDetails.
     * 
     * @param curDaySend
     */
    public void setCurDaySend(int curDaySend) {
        this.curDaySend = curDaySend;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StockDetails)) return false;
        StockDetails other = (StockDetails) obj;
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
            this.stockRemain == other.getStockRemain() &&
            this.points == other.getPoints() &&
            this.sendTotal == other.getSendTotal() &&
            this.curDaySend == other.getCurDaySend();
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
        _hashCode += getStockRemain();
        _hashCode += getPoints();
        _hashCode += getSendTotal();
        _hashCode += getCurDaySend();
        __hashCodeCalc = false;
        return _hashCode;
    }

}