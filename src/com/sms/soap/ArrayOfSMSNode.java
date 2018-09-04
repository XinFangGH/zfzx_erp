package com.sms.soap;



public class ArrayOfSMSNode  implements java.io.Serializable {
    private SMSNode[] SMSGroup;

    public ArrayOfSMSNode() {
    }

    public ArrayOfSMSNode(
           SMSNode[] SMSGroup) {
           this.SMSGroup = SMSGroup;
    }


    /**
     * Gets the SMSGroup value for this ArrayOfSMSNode.
     * 
     * @return SMSGroup
     */
    public SMSNode[] getSMSGroup() {
        return SMSGroup;
    }


    /**
     * Sets the SMSGroup value for this ArrayOfSMSNode.
     * 
     * @param SMSGroup
     */
    public void setSMSGroup(SMSNode[] SMSGroup) {
        this.SMSGroup = SMSGroup;
    }

    public SMSNode getSMSGroup(int i) {
        return this.SMSGroup[i];
    }

    public void setSMSGroup(int i, SMSNode _value) {
        this.SMSGroup[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ArrayOfSMSNode)) return false;
        ArrayOfSMSNode other = (ArrayOfSMSNode) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.SMSGroup==null && other.getSMSGroup()==null) || 
             (this.SMSGroup!=null &&
              java.util.Arrays.equals(this.SMSGroup, other.getSMSGroup())));
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
        if (getSMSGroup() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSMSGroup());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSMSGroup(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

}