/*
 * XML Type:  IntePlanBVO
 * Namespace: http://inteplan.acc.crd.vo.nc/IntePlanBVO
 * Java type: nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.inteplan.inteplanbvo.impl;
/**
 * An XML IntePlanBVO(@http://inteplan.acc.crd.vo.nc/IntePlanBVO).
 *
 * This is a complex type.
 */
public class IntePlanBVOImpl extends nc.uap.ws.lang.impl.SuperVOImpl implements nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO
{
    
    public IntePlanBVOImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName PRIMARYKEY$0 = 
        new javax.xml.namespace.QName("", "primaryKey");
    private static final javax.xml.namespace.QName DEF12$2 = 
        new javax.xml.namespace.QName("", "def12");
    private static final javax.xml.namespace.QName CARDTYPE$4 = 
        new javax.xml.namespace.QName("", "cardtype");
    private static final javax.xml.namespace.QName CORPNO$6 = 
        new javax.xml.namespace.QName("", "corpno");
    private static final javax.xml.namespace.QName DEF13$8 = 
        new javax.xml.namespace.QName("", "def13");
    private static final javax.xml.namespace.QName CALTYPE$10 = 
        new javax.xml.namespace.QName("", "caltype");
    private static final javax.xml.namespace.QName LOANTYPE$12 = 
        new javax.xml.namespace.QName("", "loantype");
    private static final javax.xml.namespace.QName DEF10$14 = 
        new javax.xml.namespace.QName("", "def10");
    private static final javax.xml.namespace.QName DEF11$16 = 
        new javax.xml.namespace.QName("", "def11");
    private static final javax.xml.namespace.QName TS$18 = 
        new javax.xml.namespace.QName("", "ts");
    private static final javax.xml.namespace.QName DEF18$20 = 
        new javax.xml.namespace.QName("", "def18");
    private static final javax.xml.namespace.QName DEF19$22 = 
        new javax.xml.namespace.QName("", "def19");
    private static final javax.xml.namespace.QName DEF16$24 = 
        new javax.xml.namespace.QName("", "def16");
    private static final javax.xml.namespace.QName DEF17$26 = 
        new javax.xml.namespace.QName("", "def17");
    private static final javax.xml.namespace.QName DEF14$28 = 
        new javax.xml.namespace.QName("", "def14");
    private static final javax.xml.namespace.QName DEF15$30 = 
        new javax.xml.namespace.QName("", "def15");
    private static final javax.xml.namespace.QName VNOTE$32 = 
        new javax.xml.namespace.QName("", "vnote");
    private static final javax.xml.namespace.QName PAYTYPE$34 = 
        new javax.xml.namespace.QName("", "paytype");
    private static final javax.xml.namespace.QName CURR$36 = 
        new javax.xml.namespace.QName("", "curr");
    private static final javax.xml.namespace.QName SHOULDINTE$38 = 
        new javax.xml.namespace.QName("", "shouldinte");
    private static final javax.xml.namespace.QName GUATYPE$40 = 
        new javax.xml.namespace.QName("", "guatype");
    private static final javax.xml.namespace.QName CUSTNAME$42 = 
        new javax.xml.namespace.QName("", "custname");
    private static final javax.xml.namespace.QName DR$44 = 
        new javax.xml.namespace.QName("", "dr");
    private static final javax.xml.namespace.QName CUSTTYPE$46 = 
        new javax.xml.namespace.QName("", "custtype");
    private static final javax.xml.namespace.QName DUEBILLNO$48 = 
        new javax.xml.namespace.QName("", "duebillno");
    private static final javax.xml.namespace.QName DEF7$50 = 
        new javax.xml.namespace.QName("", "def7");
    private static final javax.xml.namespace.QName DEF8$52 = 
        new javax.xml.namespace.QName("", "def8");
    private static final javax.xml.namespace.QName DELAYINTE$54 = 
        new javax.xml.namespace.QName("", "delayinte");
    private static final javax.xml.namespace.QName DEF5$56 = 
        new javax.xml.namespace.QName("", "def5");
    private static final javax.xml.namespace.QName DEF6$58 = 
        new javax.xml.namespace.QName("", "def6");
    private static final javax.xml.namespace.QName DEF9$60 = 
        new javax.xml.namespace.QName("", "def9");
    private static final javax.xml.namespace.QName DATADATE$62 = 
        new javax.xml.namespace.QName("", "datadate");
    private static final javax.xml.namespace.QName HASINTE$64 = 
        new javax.xml.namespace.QName("", "hasinte");
    private static final javax.xml.namespace.QName PKINTEPLANB$66 = 
        new javax.xml.namespace.QName("", "pk_inteplan_b");
    private static final javax.xml.namespace.QName PKINTEPLAN$68 = 
        new javax.xml.namespace.QName("", "pk_inteplan");
    private static final javax.xml.namespace.QName DEF20$70 = 
        new javax.xml.namespace.QName("", "def20");
    private static final javax.xml.namespace.QName CARDNO$72 = 
        new javax.xml.namespace.QName("", "cardno");
    private static final javax.xml.namespace.QName DEF4$74 = 
        new javax.xml.namespace.QName("", "def4");
    private static final javax.xml.namespace.QName DEF3$76 = 
        new javax.xml.namespace.QName("", "def3");
    private static final javax.xml.namespace.QName DEF2$78 = 
        new javax.xml.namespace.QName("", "def2");
    private static final javax.xml.namespace.QName DEF1$80 = 
        new javax.xml.namespace.QName("", "def1");
    
    
    /**
     * Gets first "primaryKey" element
     */
    public java.lang.String getPrimaryKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIMARYKEY$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) first "primaryKey" element
     */
    public org.apache.xmlbeans.XmlString xgetPrimaryKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIMARYKEY$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil first "primaryKey" element
     */
    public boolean isNilPrimaryKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIMARYKEY$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * Gets array of all "primaryKey" elements
     */
    public java.lang.String[] getPrimaryKeyArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PRIMARYKEY$0, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "primaryKey" element
     */
    public java.lang.String getPrimaryKeyArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIMARYKEY$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "primaryKey" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetPrimaryKeyArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PRIMARYKEY$0, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "primaryKey" element
     */
    public org.apache.xmlbeans.XmlString xgetPrimaryKeyArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIMARYKEY$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
        }
    }
    
    /**
     * Tests for nil ith "primaryKey" element
     */
    public boolean isNilPrimaryKeyArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIMARYKEY$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.isNil();
        }
    }
    
    /**
     * Returns number of "primaryKey" element
     */
    public int sizeOfPrimaryKeyArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PRIMARYKEY$0);
        }
    }
    
    /**
     * Sets first "primaryKey" element
     */
    public void setPrimaryKey(java.lang.String primaryKey)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIMARYKEY$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRIMARYKEY$0);
            }
            target.setStringValue(primaryKey);
        }
    }
    
    /**
     * Sets (as xml) first "primaryKey" element
     */
    public void xsetPrimaryKey(org.apache.xmlbeans.XmlString primaryKey)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIMARYKEY$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRIMARYKEY$0);
            }
            target.set(primaryKey);
        }
    }
    
    /**
     * Nils the first "primaryKey" element
     */
    public void setNilPrimaryKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIMARYKEY$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRIMARYKEY$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Sets array of all "primaryKey" element
     */
    public void setPrimaryKeyArray(java.lang.String[] primaryKeyArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(primaryKeyArray, PRIMARYKEY$0);
        }
    }
    
    /**
     * Sets ith "primaryKey" element
     */
    public void setPrimaryKeyArray(int i, java.lang.String primaryKey)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIMARYKEY$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(primaryKey);
        }
    }
    
    /**
     * Sets (as xml) array of all "primaryKey" element
     */
    public void xsetPrimaryKeyArray(org.apache.xmlbeans.XmlString[]primaryKeyArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(primaryKeyArray, PRIMARYKEY$0);
        }
    }
    
    /**
     * Sets (as xml) ith "primaryKey" element
     */
    public void xsetPrimaryKeyArray(int i, org.apache.xmlbeans.XmlString primaryKey)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIMARYKEY$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(primaryKey);
        }
    }
    
    /**
     * Nils the ith "primaryKey" element
     */
    public void setNilPrimaryKeyArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIMARYKEY$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setNil();
        }
    }
    
    /**
     * Inserts the value as the ith "primaryKey" element
     */
    public void insertPrimaryKey(int i, java.lang.String primaryKey)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(PRIMARYKEY$0, i);
            target.setStringValue(primaryKey);
        }
    }
    
    /**
     * Appends the value as the last "primaryKey" element
     */
    public void addPrimaryKey(java.lang.String primaryKey)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRIMARYKEY$0);
            target.setStringValue(primaryKey);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "primaryKey" element
     */
    public org.apache.xmlbeans.XmlString insertNewPrimaryKey(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(PRIMARYKEY$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "primaryKey" element
     */
    public org.apache.xmlbeans.XmlString addNewPrimaryKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRIMARYKEY$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "primaryKey" element
     */
    public void removePrimaryKey(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PRIMARYKEY$0, i);
        }
    }
    
    /**
     * Gets the "def12" element
     */
    public java.math.BigDecimal getDef12()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF12$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "def12" element
     */
    public nc.uap.ws.lang.UFDouble xgetDef12()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF12$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def12" element
     */
    public boolean isNilDef12()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF12$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def12" element
     */
    public boolean isSetDef12()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF12$2) != 0;
        }
    }
    
    /**
     * Sets the "def12" element
     */
    public void setDef12(java.math.BigDecimal def12)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF12$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF12$2);
            }
            target.setBigDecimalValue(def12);
        }
    }
    
    /**
     * Sets (as xml) the "def12" element
     */
    public void xsetDef12(nc.uap.ws.lang.UFDouble def12)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF12$2, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DEF12$2);
            }
            target.set(def12);
        }
    }
    
    /**
     * Nils the "def12" element
     */
    public void setNilDef12()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF12$2, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DEF12$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def12" element
     */
    public void unsetDef12()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF12$2, 0);
        }
    }
    
    /**
     * Gets the "cardtype" element
     */
    public java.lang.String getCardtype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CARDTYPE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "cardtype" element
     */
    public org.apache.xmlbeans.XmlString xgetCardtype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CARDTYPE$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "cardtype" element
     */
    public boolean isNilCardtype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CARDTYPE$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "cardtype" element
     */
    public boolean isSetCardtype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CARDTYPE$4) != 0;
        }
    }
    
    /**
     * Sets the "cardtype" element
     */
    public void setCardtype(java.lang.String cardtype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CARDTYPE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CARDTYPE$4);
            }
            target.setStringValue(cardtype);
        }
    }
    
    /**
     * Sets (as xml) the "cardtype" element
     */
    public void xsetCardtype(org.apache.xmlbeans.XmlString cardtype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CARDTYPE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CARDTYPE$4);
            }
            target.set(cardtype);
        }
    }
    
    /**
     * Nils the "cardtype" element
     */
    public void setNilCardtype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CARDTYPE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CARDTYPE$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "cardtype" element
     */
    public void unsetCardtype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CARDTYPE$4, 0);
        }
    }
    
    /**
     * Gets the "corpno" element
     */
    public java.lang.String getCorpno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CORPNO$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "corpno" element
     */
    public org.apache.xmlbeans.XmlString xgetCorpno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CORPNO$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "corpno" element
     */
    public boolean isNilCorpno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CORPNO$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "corpno" element
     */
    public boolean isSetCorpno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CORPNO$6) != 0;
        }
    }
    
    /**
     * Sets the "corpno" element
     */
    public void setCorpno(java.lang.String corpno)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CORPNO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CORPNO$6);
            }
            target.setStringValue(corpno);
        }
    }
    
    /**
     * Sets (as xml) the "corpno" element
     */
    public void xsetCorpno(org.apache.xmlbeans.XmlString corpno)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CORPNO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CORPNO$6);
            }
            target.set(corpno);
        }
    }
    
    /**
     * Nils the "corpno" element
     */
    public void setNilCorpno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CORPNO$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CORPNO$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "corpno" element
     */
    public void unsetCorpno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CORPNO$6, 0);
        }
    }
    
    /**
     * Gets the "def13" element
     */
    public java.math.BigDecimal getDef13()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF13$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "def13" element
     */
    public nc.uap.ws.lang.UFDouble xgetDef13()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF13$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def13" element
     */
    public boolean isNilDef13()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF13$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def13" element
     */
    public boolean isSetDef13()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF13$8) != 0;
        }
    }
    
    /**
     * Sets the "def13" element
     */
    public void setDef13(java.math.BigDecimal def13)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF13$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF13$8);
            }
            target.setBigDecimalValue(def13);
        }
    }
    
    /**
     * Sets (as xml) the "def13" element
     */
    public void xsetDef13(nc.uap.ws.lang.UFDouble def13)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF13$8, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DEF13$8);
            }
            target.set(def13);
        }
    }
    
    /**
     * Nils the "def13" element
     */
    public void setNilDef13()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF13$8, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DEF13$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def13" element
     */
    public void unsetDef13()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF13$8, 0);
        }
    }
    
    /**
     * Gets the "caltype" element
     */
    public java.lang.String getCaltype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CALTYPE$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "caltype" element
     */
    public org.apache.xmlbeans.XmlString xgetCaltype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CALTYPE$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "caltype" element
     */
    public boolean isNilCaltype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CALTYPE$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "caltype" element
     */
    public boolean isSetCaltype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CALTYPE$10) != 0;
        }
    }
    
    /**
     * Sets the "caltype" element
     */
    public void setCaltype(java.lang.String caltype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CALTYPE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CALTYPE$10);
            }
            target.setStringValue(caltype);
        }
    }
    
    /**
     * Sets (as xml) the "caltype" element
     */
    public void xsetCaltype(org.apache.xmlbeans.XmlString caltype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CALTYPE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CALTYPE$10);
            }
            target.set(caltype);
        }
    }
    
    /**
     * Nils the "caltype" element
     */
    public void setNilCaltype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CALTYPE$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CALTYPE$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "caltype" element
     */
    public void unsetCaltype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CALTYPE$10, 0);
        }
    }
    
    /**
     * Gets the "loantype" element
     */
    public java.lang.String getLoantype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOANTYPE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "loantype" element
     */
    public org.apache.xmlbeans.XmlString xgetLoantype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOANTYPE$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "loantype" element
     */
    public boolean isNilLoantype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOANTYPE$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "loantype" element
     */
    public boolean isSetLoantype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(LOANTYPE$12) != 0;
        }
    }
    
    /**
     * Sets the "loantype" element
     */
    public void setLoantype(java.lang.String loantype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOANTYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOANTYPE$12);
            }
            target.setStringValue(loantype);
        }
    }
    
    /**
     * Sets (as xml) the "loantype" element
     */
    public void xsetLoantype(org.apache.xmlbeans.XmlString loantype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOANTYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LOANTYPE$12);
            }
            target.set(loantype);
        }
    }
    
    /**
     * Nils the "loantype" element
     */
    public void setNilLoantype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOANTYPE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LOANTYPE$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "loantype" element
     */
    public void unsetLoantype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(LOANTYPE$12, 0);
        }
    }
    
    /**
     * Gets the "def10" element
     */
    public java.lang.String getDef10()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF10$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "def10" element
     */
    public org.apache.xmlbeans.XmlString xgetDef10()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF10$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def10" element
     */
    public boolean isNilDef10()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF10$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def10" element
     */
    public boolean isSetDef10()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF10$14) != 0;
        }
    }
    
    /**
     * Sets the "def10" element
     */
    public void setDef10(java.lang.String def10)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF10$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF10$14);
            }
            target.setStringValue(def10);
        }
    }
    
    /**
     * Sets (as xml) the "def10" element
     */
    public void xsetDef10(org.apache.xmlbeans.XmlString def10)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF10$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF10$14);
            }
            target.set(def10);
        }
    }
    
    /**
     * Nils the "def10" element
     */
    public void setNilDef10()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF10$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF10$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def10" element
     */
    public void unsetDef10()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF10$14, 0);
        }
    }
    
    /**
     * Gets the "def11" element
     */
    public java.math.BigDecimal getDef11()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF11$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "def11" element
     */
    public nc.uap.ws.lang.UFDouble xgetDef11()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF11$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def11" element
     */
    public boolean isNilDef11()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF11$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def11" element
     */
    public boolean isSetDef11()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF11$16) != 0;
        }
    }
    
    /**
     * Sets the "def11" element
     */
    public void setDef11(java.math.BigDecimal def11)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF11$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF11$16);
            }
            target.setBigDecimalValue(def11);
        }
    }
    
    /**
     * Sets (as xml) the "def11" element
     */
    public void xsetDef11(nc.uap.ws.lang.UFDouble def11)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF11$16, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DEF11$16);
            }
            target.set(def11);
        }
    }
    
    /**
     * Nils the "def11" element
     */
    public void setNilDef11()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF11$16, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DEF11$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def11" element
     */
    public void unsetDef11()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF11$16, 0);
        }
    }
    
    /**
     * Gets the "ts" element
     */
    public java.util.Calendar getTs()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TS$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "ts" element
     */
    public nc.uap.ws.lang.UFDateTime xgetTs()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDateTime target = null;
            target = (nc.uap.ws.lang.UFDateTime)get_store().find_element_user(TS$18, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "ts" element
     */
    public boolean isNilTs()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDateTime target = null;
            target = (nc.uap.ws.lang.UFDateTime)get_store().find_element_user(TS$18, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ts" element
     */
    public boolean isSetTs()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TS$18) != 0;
        }
    }
    
    /**
     * Sets the "ts" element
     */
    public void setTs(java.util.Calendar ts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TS$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TS$18);
            }
            target.setCalendarValue(ts);
        }
    }
    
    /**
     * Sets (as xml) the "ts" element
     */
    public void xsetTs(nc.uap.ws.lang.UFDateTime ts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDateTime target = null;
            target = (nc.uap.ws.lang.UFDateTime)get_store().find_element_user(TS$18, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDateTime)get_store().add_element_user(TS$18);
            }
            target.set(ts);
        }
    }
    
    /**
     * Nils the "ts" element
     */
    public void setNilTs()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDateTime target = null;
            target = (nc.uap.ws.lang.UFDateTime)get_store().find_element_user(TS$18, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDateTime)get_store().add_element_user(TS$18);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ts" element
     */
    public void unsetTs()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TS$18, 0);
        }
    }
    
    /**
     * Gets the "def18" element
     */
    public int getDef18()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF18$20, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "def18" element
     */
    public org.apache.xmlbeans.XmlInt xgetDef18()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF18$20, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def18" element
     */
    public boolean isNilDef18()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF18$20, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def18" element
     */
    public boolean isSetDef18()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF18$20) != 0;
        }
    }
    
    /**
     * Sets the "def18" element
     */
    public void setDef18(int def18)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF18$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF18$20);
            }
            target.setIntValue(def18);
        }
    }
    
    /**
     * Sets (as xml) the "def18" element
     */
    public void xsetDef18(org.apache.xmlbeans.XmlInt def18)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF18$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEF18$20);
            }
            target.set(def18);
        }
    }
    
    /**
     * Nils the "def18" element
     */
    public void setNilDef18()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF18$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEF18$20);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def18" element
     */
    public void unsetDef18()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF18$20, 0);
        }
    }
    
    /**
     * Gets the "def19" element
     */
    public int getDef19()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF19$22, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "def19" element
     */
    public org.apache.xmlbeans.XmlInt xgetDef19()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF19$22, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def19" element
     */
    public boolean isNilDef19()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF19$22, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def19" element
     */
    public boolean isSetDef19()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF19$22) != 0;
        }
    }
    
    /**
     * Sets the "def19" element
     */
    public void setDef19(int def19)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF19$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF19$22);
            }
            target.setIntValue(def19);
        }
    }
    
    /**
     * Sets (as xml) the "def19" element
     */
    public void xsetDef19(org.apache.xmlbeans.XmlInt def19)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF19$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEF19$22);
            }
            target.set(def19);
        }
    }
    
    /**
     * Nils the "def19" element
     */
    public void setNilDef19()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF19$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEF19$22);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def19" element
     */
    public void unsetDef19()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF19$22, 0);
        }
    }
    
    /**
     * Gets the "def16" element
     */
    public int getDef16()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF16$24, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "def16" element
     */
    public org.apache.xmlbeans.XmlInt xgetDef16()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF16$24, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def16" element
     */
    public boolean isNilDef16()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF16$24, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def16" element
     */
    public boolean isSetDef16()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF16$24) != 0;
        }
    }
    
    /**
     * Sets the "def16" element
     */
    public void setDef16(int def16)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF16$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF16$24);
            }
            target.setIntValue(def16);
        }
    }
    
    /**
     * Sets (as xml) the "def16" element
     */
    public void xsetDef16(org.apache.xmlbeans.XmlInt def16)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF16$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEF16$24);
            }
            target.set(def16);
        }
    }
    
    /**
     * Nils the "def16" element
     */
    public void setNilDef16()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF16$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEF16$24);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def16" element
     */
    public void unsetDef16()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF16$24, 0);
        }
    }
    
    /**
     * Gets the "def17" element
     */
    public int getDef17()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF17$26, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "def17" element
     */
    public org.apache.xmlbeans.XmlInt xgetDef17()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF17$26, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def17" element
     */
    public boolean isNilDef17()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF17$26, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def17" element
     */
    public boolean isSetDef17()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF17$26) != 0;
        }
    }
    
    /**
     * Sets the "def17" element
     */
    public void setDef17(int def17)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF17$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF17$26);
            }
            target.setIntValue(def17);
        }
    }
    
    /**
     * Sets (as xml) the "def17" element
     */
    public void xsetDef17(org.apache.xmlbeans.XmlInt def17)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF17$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEF17$26);
            }
            target.set(def17);
        }
    }
    
    /**
     * Nils the "def17" element
     */
    public void setNilDef17()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF17$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEF17$26);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def17" element
     */
    public void unsetDef17()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF17$26, 0);
        }
    }
    
    /**
     * Gets the "def14" element
     */
    public java.math.BigDecimal getDef14()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF14$28, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "def14" element
     */
    public nc.uap.ws.lang.UFDouble xgetDef14()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF14$28, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def14" element
     */
    public boolean isNilDef14()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF14$28, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def14" element
     */
    public boolean isSetDef14()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF14$28) != 0;
        }
    }
    
    /**
     * Sets the "def14" element
     */
    public void setDef14(java.math.BigDecimal def14)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF14$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF14$28);
            }
            target.setBigDecimalValue(def14);
        }
    }
    
    /**
     * Sets (as xml) the "def14" element
     */
    public void xsetDef14(nc.uap.ws.lang.UFDouble def14)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF14$28, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DEF14$28);
            }
            target.set(def14);
        }
    }
    
    /**
     * Nils the "def14" element
     */
    public void setNilDef14()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF14$28, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DEF14$28);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def14" element
     */
    public void unsetDef14()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF14$28, 0);
        }
    }
    
    /**
     * Gets the "def15" element
     */
    public java.math.BigDecimal getDef15()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF15$30, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "def15" element
     */
    public nc.uap.ws.lang.UFDouble xgetDef15()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF15$30, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def15" element
     */
    public boolean isNilDef15()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF15$30, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def15" element
     */
    public boolean isSetDef15()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF15$30) != 0;
        }
    }
    
    /**
     * Sets the "def15" element
     */
    public void setDef15(java.math.BigDecimal def15)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF15$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF15$30);
            }
            target.setBigDecimalValue(def15);
        }
    }
    
    /**
     * Sets (as xml) the "def15" element
     */
    public void xsetDef15(nc.uap.ws.lang.UFDouble def15)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF15$30, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DEF15$30);
            }
            target.set(def15);
        }
    }
    
    /**
     * Nils the "def15" element
     */
    public void setNilDef15()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DEF15$30, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DEF15$30);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def15" element
     */
    public void unsetDef15()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF15$30, 0);
        }
    }
    
    /**
     * Gets the "vnote" element
     */
    public java.lang.String getVnote()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VNOTE$32, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "vnote" element
     */
    public org.apache.xmlbeans.XmlString xgetVnote()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VNOTE$32, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "vnote" element
     */
    public boolean isNilVnote()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VNOTE$32, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "vnote" element
     */
    public boolean isSetVnote()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(VNOTE$32) != 0;
        }
    }
    
    /**
     * Sets the "vnote" element
     */
    public void setVnote(java.lang.String vnote)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VNOTE$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VNOTE$32);
            }
            target.setStringValue(vnote);
        }
    }
    
    /**
     * Sets (as xml) the "vnote" element
     */
    public void xsetVnote(org.apache.xmlbeans.XmlString vnote)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VNOTE$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(VNOTE$32);
            }
            target.set(vnote);
        }
    }
    
    /**
     * Nils the "vnote" element
     */
    public void setNilVnote()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VNOTE$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(VNOTE$32);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "vnote" element
     */
    public void unsetVnote()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(VNOTE$32, 0);
        }
    }
    
    /**
     * Gets the "paytype" element
     */
    public java.lang.String getPaytype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYTYPE$34, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "paytype" element
     */
    public org.apache.xmlbeans.XmlString xgetPaytype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAYTYPE$34, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "paytype" element
     */
    public boolean isNilPaytype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAYTYPE$34, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "paytype" element
     */
    public boolean isSetPaytype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PAYTYPE$34) != 0;
        }
    }
    
    /**
     * Sets the "paytype" element
     */
    public void setPaytype(java.lang.String paytype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PAYTYPE$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PAYTYPE$34);
            }
            target.setStringValue(paytype);
        }
    }
    
    /**
     * Sets (as xml) the "paytype" element
     */
    public void xsetPaytype(org.apache.xmlbeans.XmlString paytype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAYTYPE$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAYTYPE$34);
            }
            target.set(paytype);
        }
    }
    
    /**
     * Nils the "paytype" element
     */
    public void setNilPaytype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PAYTYPE$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PAYTYPE$34);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "paytype" element
     */
    public void unsetPaytype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PAYTYPE$34, 0);
        }
    }
    
    /**
     * Gets the "curr" element
     */
    public java.lang.String getCurr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURR$36, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "curr" element
     */
    public org.apache.xmlbeans.XmlString xgetCurr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURR$36, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "curr" element
     */
    public boolean isNilCurr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURR$36, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "curr" element
     */
    public boolean isSetCurr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CURR$36) != 0;
        }
    }
    
    /**
     * Sets the "curr" element
     */
    public void setCurr(java.lang.String curr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURR$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CURR$36);
            }
            target.setStringValue(curr);
        }
    }
    
    /**
     * Sets (as xml) the "curr" element
     */
    public void xsetCurr(org.apache.xmlbeans.XmlString curr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURR$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURR$36);
            }
            target.set(curr);
        }
    }
    
    /**
     * Nils the "curr" element
     */
    public void setNilCurr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURR$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURR$36);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "curr" element
     */
    public void unsetCurr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CURR$36, 0);
        }
    }
    
    /**
     * Gets the "shouldinte" element
     */
    public java.math.BigDecimal getShouldinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SHOULDINTE$38, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "shouldinte" element
     */
    public nc.uap.ws.lang.UFDouble xgetShouldinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(SHOULDINTE$38, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "shouldinte" element
     */
    public boolean isNilShouldinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(SHOULDINTE$38, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "shouldinte" element
     */
    public boolean isSetShouldinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SHOULDINTE$38) != 0;
        }
    }
    
    /**
     * Sets the "shouldinte" element
     */
    public void setShouldinte(java.math.BigDecimal shouldinte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SHOULDINTE$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SHOULDINTE$38);
            }
            target.setBigDecimalValue(shouldinte);
        }
    }
    
    /**
     * Sets (as xml) the "shouldinte" element
     */
    public void xsetShouldinte(nc.uap.ws.lang.UFDouble shouldinte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(SHOULDINTE$38, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(SHOULDINTE$38);
            }
            target.set(shouldinte);
        }
    }
    
    /**
     * Nils the "shouldinte" element
     */
    public void setNilShouldinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(SHOULDINTE$38, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(SHOULDINTE$38);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "shouldinte" element
     */
    public void unsetShouldinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SHOULDINTE$38, 0);
        }
    }
    
    /**
     * Gets the "guatype" element
     */
    public java.lang.String getGuatype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GUATYPE$40, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "guatype" element
     */
    public org.apache.xmlbeans.XmlString xgetGuatype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GUATYPE$40, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "guatype" element
     */
    public boolean isNilGuatype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GUATYPE$40, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "guatype" element
     */
    public boolean isSetGuatype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GUATYPE$40) != 0;
        }
    }
    
    /**
     * Sets the "guatype" element
     */
    public void setGuatype(java.lang.String guatype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GUATYPE$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GUATYPE$40);
            }
            target.setStringValue(guatype);
        }
    }
    
    /**
     * Sets (as xml) the "guatype" element
     */
    public void xsetGuatype(org.apache.xmlbeans.XmlString guatype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GUATYPE$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(GUATYPE$40);
            }
            target.set(guatype);
        }
    }
    
    /**
     * Nils the "guatype" element
     */
    public void setNilGuatype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GUATYPE$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(GUATYPE$40);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "guatype" element
     */
    public void unsetGuatype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GUATYPE$40, 0);
        }
    }
    
    /**
     * Gets the "custname" element
     */
    public java.lang.String getCustname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTNAME$42, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "custname" element
     */
    public org.apache.xmlbeans.XmlString xgetCustname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTNAME$42, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "custname" element
     */
    public boolean isNilCustname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTNAME$42, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "custname" element
     */
    public boolean isSetCustname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUSTNAME$42) != 0;
        }
    }
    
    /**
     * Sets the "custname" element
     */
    public void setCustname(java.lang.String custname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTNAME$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUSTNAME$42);
            }
            target.setStringValue(custname);
        }
    }
    
    /**
     * Sets (as xml) the "custname" element
     */
    public void xsetCustname(org.apache.xmlbeans.XmlString custname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTNAME$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUSTNAME$42);
            }
            target.set(custname);
        }
    }
    
    /**
     * Nils the "custname" element
     */
    public void setNilCustname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTNAME$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUSTNAME$42);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "custname" element
     */
    public void unsetCustname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUSTNAME$42, 0);
        }
    }
    
    /**
     * Gets the "dr" element
     */
    public int getDr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DR$44, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "dr" element
     */
    public org.apache.xmlbeans.XmlInt xgetDr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DR$44, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dr" element
     */
    public boolean isNilDr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DR$44, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dr" element
     */
    public boolean isSetDr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DR$44) != 0;
        }
    }
    
    /**
     * Sets the "dr" element
     */
    public void setDr(int dr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DR$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DR$44);
            }
            target.setIntValue(dr);
        }
    }
    
    /**
     * Sets (as xml) the "dr" element
     */
    public void xsetDr(org.apache.xmlbeans.XmlInt dr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DR$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DR$44);
            }
            target.set(dr);
        }
    }
    
    /**
     * Nils the "dr" element
     */
    public void setNilDr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DR$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DR$44);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dr" element
     */
    public void unsetDr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DR$44, 0);
        }
    }
    
    /**
     * Gets the "custtype" element
     */
    public java.lang.String getCusttype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTTYPE$46, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "custtype" element
     */
    public org.apache.xmlbeans.XmlString xgetCusttype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTTYPE$46, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "custtype" element
     */
    public boolean isNilCusttype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTTYPE$46, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "custtype" element
     */
    public boolean isSetCusttype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CUSTTYPE$46) != 0;
        }
    }
    
    /**
     * Sets the "custtype" element
     */
    public void setCusttype(java.lang.String custtype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CUSTTYPE$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CUSTTYPE$46);
            }
            target.setStringValue(custtype);
        }
    }
    
    /**
     * Sets (as xml) the "custtype" element
     */
    public void xsetCusttype(org.apache.xmlbeans.XmlString custtype)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTTYPE$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUSTTYPE$46);
            }
            target.set(custtype);
        }
    }
    
    /**
     * Nils the "custtype" element
     */
    public void setNilCusttype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CUSTTYPE$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CUSTTYPE$46);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "custtype" element
     */
    public void unsetCusttype()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CUSTTYPE$46, 0);
        }
    }
    
    /**
     * Gets the "duebillno" element
     */
    public java.lang.String getDuebillno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DUEBILLNO$48, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "duebillno" element
     */
    public org.apache.xmlbeans.XmlString xgetDuebillno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DUEBILLNO$48, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "duebillno" element
     */
    public boolean isNilDuebillno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DUEBILLNO$48, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "duebillno" element
     */
    public boolean isSetDuebillno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DUEBILLNO$48) != 0;
        }
    }
    
    /**
     * Sets the "duebillno" element
     */
    public void setDuebillno(java.lang.String duebillno)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DUEBILLNO$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DUEBILLNO$48);
            }
            target.setStringValue(duebillno);
        }
    }
    
    /**
     * Sets (as xml) the "duebillno" element
     */
    public void xsetDuebillno(org.apache.xmlbeans.XmlString duebillno)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DUEBILLNO$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DUEBILLNO$48);
            }
            target.set(duebillno);
        }
    }
    
    /**
     * Nils the "duebillno" element
     */
    public void setNilDuebillno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DUEBILLNO$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DUEBILLNO$48);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "duebillno" element
     */
    public void unsetDuebillno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DUEBILLNO$48, 0);
        }
    }
    
    /**
     * Gets the "def7" element
     */
    public java.lang.String getDef7()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF7$50, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "def7" element
     */
    public org.apache.xmlbeans.XmlString xgetDef7()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF7$50, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def7" element
     */
    public boolean isNilDef7()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF7$50, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def7" element
     */
    public boolean isSetDef7()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF7$50) != 0;
        }
    }
    
    /**
     * Sets the "def7" element
     */
    public void setDef7(java.lang.String def7)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF7$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF7$50);
            }
            target.setStringValue(def7);
        }
    }
    
    /**
     * Sets (as xml) the "def7" element
     */
    public void xsetDef7(org.apache.xmlbeans.XmlString def7)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF7$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF7$50);
            }
            target.set(def7);
        }
    }
    
    /**
     * Nils the "def7" element
     */
    public void setNilDef7()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF7$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF7$50);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def7" element
     */
    public void unsetDef7()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF7$50, 0);
        }
    }
    
    /**
     * Gets the "def8" element
     */
    public java.lang.String getDef8()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF8$52, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "def8" element
     */
    public org.apache.xmlbeans.XmlString xgetDef8()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF8$52, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def8" element
     */
    public boolean isNilDef8()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF8$52, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def8" element
     */
    public boolean isSetDef8()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF8$52) != 0;
        }
    }
    
    /**
     * Sets the "def8" element
     */
    public void setDef8(java.lang.String def8)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF8$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF8$52);
            }
            target.setStringValue(def8);
        }
    }
    
    /**
     * Sets (as xml) the "def8" element
     */
    public void xsetDef8(org.apache.xmlbeans.XmlString def8)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF8$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF8$52);
            }
            target.set(def8);
        }
    }
    
    /**
     * Nils the "def8" element
     */
    public void setNilDef8()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF8$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF8$52);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def8" element
     */
    public void unsetDef8()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF8$52, 0);
        }
    }
    
    /**
     * Gets the "delayinte" element
     */
    public java.math.BigDecimal getDelayinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELAYINTE$54, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "delayinte" element
     */
    public nc.uap.ws.lang.UFDouble xgetDelayinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DELAYINTE$54, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "delayinte" element
     */
    public boolean isNilDelayinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DELAYINTE$54, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "delayinte" element
     */
    public boolean isSetDelayinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DELAYINTE$54) != 0;
        }
    }
    
    /**
     * Sets the "delayinte" element
     */
    public void setDelayinte(java.math.BigDecimal delayinte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DELAYINTE$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DELAYINTE$54);
            }
            target.setBigDecimalValue(delayinte);
        }
    }
    
    /**
     * Sets (as xml) the "delayinte" element
     */
    public void xsetDelayinte(nc.uap.ws.lang.UFDouble delayinte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DELAYINTE$54, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DELAYINTE$54);
            }
            target.set(delayinte);
        }
    }
    
    /**
     * Nils the "delayinte" element
     */
    public void setNilDelayinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(DELAYINTE$54, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(DELAYINTE$54);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "delayinte" element
     */
    public void unsetDelayinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DELAYINTE$54, 0);
        }
    }
    
    /**
     * Gets the "def5" element
     */
    public java.lang.String getDef5()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF5$56, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "def5" element
     */
    public org.apache.xmlbeans.XmlString xgetDef5()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF5$56, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def5" element
     */
    public boolean isNilDef5()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF5$56, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def5" element
     */
    public boolean isSetDef5()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF5$56) != 0;
        }
    }
    
    /**
     * Sets the "def5" element
     */
    public void setDef5(java.lang.String def5)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF5$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF5$56);
            }
            target.setStringValue(def5);
        }
    }
    
    /**
     * Sets (as xml) the "def5" element
     */
    public void xsetDef5(org.apache.xmlbeans.XmlString def5)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF5$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF5$56);
            }
            target.set(def5);
        }
    }
    
    /**
     * Nils the "def5" element
     */
    public void setNilDef5()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF5$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF5$56);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def5" element
     */
    public void unsetDef5()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF5$56, 0);
        }
    }
    
    /**
     * Gets the "def6" element
     */
    public java.lang.String getDef6()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF6$58, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "def6" element
     */
    public org.apache.xmlbeans.XmlString xgetDef6()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF6$58, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def6" element
     */
    public boolean isNilDef6()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF6$58, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def6" element
     */
    public boolean isSetDef6()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF6$58) != 0;
        }
    }
    
    /**
     * Sets the "def6" element
     */
    public void setDef6(java.lang.String def6)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF6$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF6$58);
            }
            target.setStringValue(def6);
        }
    }
    
    /**
     * Sets (as xml) the "def6" element
     */
    public void xsetDef6(org.apache.xmlbeans.XmlString def6)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF6$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF6$58);
            }
            target.set(def6);
        }
    }
    
    /**
     * Nils the "def6" element
     */
    public void setNilDef6()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF6$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF6$58);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def6" element
     */
    public void unsetDef6()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF6$58, 0);
        }
    }
    
    /**
     * Gets the "def9" element
     */
    public java.lang.String getDef9()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF9$60, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "def9" element
     */
    public org.apache.xmlbeans.XmlString xgetDef9()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF9$60, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def9" element
     */
    public boolean isNilDef9()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF9$60, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def9" element
     */
    public boolean isSetDef9()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF9$60) != 0;
        }
    }
    
    /**
     * Sets the "def9" element
     */
    public void setDef9(java.lang.String def9)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF9$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF9$60);
            }
            target.setStringValue(def9);
        }
    }
    
    /**
     * Sets (as xml) the "def9" element
     */
    public void xsetDef9(org.apache.xmlbeans.XmlString def9)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF9$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF9$60);
            }
            target.set(def9);
        }
    }
    
    /**
     * Nils the "def9" element
     */
    public void setNilDef9()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF9$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF9$60);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def9" element
     */
    public void unsetDef9()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF9$60, 0);
        }
    }
    
    /**
     * Gets the "datadate" element
     */
    public java.util.Calendar getDatadate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATADATE$62, 0);
            if (target == null)
            {
                return null;
            }
            return target.getCalendarValue();
        }
    }
    
    /**
     * Gets (as xml) the "datadate" element
     */
    public nc.uap.ws.lang.UFDate xgetDatadate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDate target = null;
            target = (nc.uap.ws.lang.UFDate)get_store().find_element_user(DATADATE$62, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "datadate" element
     */
    public boolean isNilDatadate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDate target = null;
            target = (nc.uap.ws.lang.UFDate)get_store().find_element_user(DATADATE$62, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "datadate" element
     */
    public boolean isSetDatadate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATADATE$62) != 0;
        }
    }
    
    /**
     * Sets the "datadate" element
     */
    public void setDatadate(java.util.Calendar datadate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATADATE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATADATE$62);
            }
            target.setCalendarValue(datadate);
        }
    }
    
    /**
     * Sets (as xml) the "datadate" element
     */
    public void xsetDatadate(nc.uap.ws.lang.UFDate datadate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDate target = null;
            target = (nc.uap.ws.lang.UFDate)get_store().find_element_user(DATADATE$62, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDate)get_store().add_element_user(DATADATE$62);
            }
            target.set(datadate);
        }
    }
    
    /**
     * Nils the "datadate" element
     */
    public void setNilDatadate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDate target = null;
            target = (nc.uap.ws.lang.UFDate)get_store().find_element_user(DATADATE$62, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDate)get_store().add_element_user(DATADATE$62);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "datadate" element
     */
    public void unsetDatadate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATADATE$62, 0);
        }
    }
    
    /**
     * Gets the "hasinte" element
     */
    public java.math.BigDecimal getHasinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HASINTE$64, 0);
            if (target == null)
            {
                return null;
            }
            return target.getBigDecimalValue();
        }
    }
    
    /**
     * Gets (as xml) the "hasinte" element
     */
    public nc.uap.ws.lang.UFDouble xgetHasinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(HASINTE$64, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "hasinte" element
     */
    public boolean isNilHasinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(HASINTE$64, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "hasinte" element
     */
    public boolean isSetHasinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(HASINTE$64) != 0;
        }
    }
    
    /**
     * Sets the "hasinte" element
     */
    public void setHasinte(java.math.BigDecimal hasinte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HASINTE$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(HASINTE$64);
            }
            target.setBigDecimalValue(hasinte);
        }
    }
    
    /**
     * Sets (as xml) the "hasinte" element
     */
    public void xsetHasinte(nc.uap.ws.lang.UFDouble hasinte)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(HASINTE$64, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(HASINTE$64);
            }
            target.set(hasinte);
        }
    }
    
    /**
     * Nils the "hasinte" element
     */
    public void setNilHasinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UFDouble target = null;
            target = (nc.uap.ws.lang.UFDouble)get_store().find_element_user(HASINTE$64, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UFDouble)get_store().add_element_user(HASINTE$64);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "hasinte" element
     */
    public void unsetHasinte()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(HASINTE$64, 0);
        }
    }
    
    /**
     * Gets the "pk_inteplan_b" element
     */
    public java.lang.String getPkInteplanB()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PKINTEPLANB$66, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "pk_inteplan_b" element
     */
    public org.apache.xmlbeans.XmlString xgetPkInteplanB()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKINTEPLANB$66, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "pk_inteplan_b" element
     */
    public boolean isNilPkInteplanB()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKINTEPLANB$66, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "pk_inteplan_b" element
     */
    public boolean isSetPkInteplanB()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PKINTEPLANB$66) != 0;
        }
    }
    
    /**
     * Sets the "pk_inteplan_b" element
     */
    public void setPkInteplanB(java.lang.String pkInteplanB)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PKINTEPLANB$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PKINTEPLANB$66);
            }
            target.setStringValue(pkInteplanB);
        }
    }
    
    /**
     * Sets (as xml) the "pk_inteplan_b" element
     */
    public void xsetPkInteplanB(org.apache.xmlbeans.XmlString pkInteplanB)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKINTEPLANB$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PKINTEPLANB$66);
            }
            target.set(pkInteplanB);
        }
    }
    
    /**
     * Nils the "pk_inteplan_b" element
     */
    public void setNilPkInteplanB()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKINTEPLANB$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PKINTEPLANB$66);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "pk_inteplan_b" element
     */
    public void unsetPkInteplanB()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PKINTEPLANB$66, 0);
        }
    }
    
    /**
     * Gets the "pk_inteplan" element
     */
    public java.lang.String getPkInteplan()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PKINTEPLAN$68, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "pk_inteplan" element
     */
    public org.apache.xmlbeans.XmlString xgetPkInteplan()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKINTEPLAN$68, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "pk_inteplan" element
     */
    public boolean isNilPkInteplan()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKINTEPLAN$68, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "pk_inteplan" element
     */
    public boolean isSetPkInteplan()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PKINTEPLAN$68) != 0;
        }
    }
    
    /**
     * Sets the "pk_inteplan" element
     */
    public void setPkInteplan(java.lang.String pkInteplan)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PKINTEPLAN$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PKINTEPLAN$68);
            }
            target.setStringValue(pkInteplan);
        }
    }
    
    /**
     * Sets (as xml) the "pk_inteplan" element
     */
    public void xsetPkInteplan(org.apache.xmlbeans.XmlString pkInteplan)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKINTEPLAN$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PKINTEPLAN$68);
            }
            target.set(pkInteplan);
        }
    }
    
    /**
     * Nils the "pk_inteplan" element
     */
    public void setNilPkInteplan()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKINTEPLAN$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PKINTEPLAN$68);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "pk_inteplan" element
     */
    public void unsetPkInteplan()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PKINTEPLAN$68, 0);
        }
    }
    
    /**
     * Gets the "def20" element
     */
    public int getDef20()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF20$70, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "def20" element
     */
    public org.apache.xmlbeans.XmlInt xgetDef20()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF20$70, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def20" element
     */
    public boolean isNilDef20()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF20$70, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def20" element
     */
    public boolean isSetDef20()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF20$70) != 0;
        }
    }
    
    /**
     * Sets the "def20" element
     */
    public void setDef20(int def20)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF20$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF20$70);
            }
            target.setIntValue(def20);
        }
    }
    
    /**
     * Sets (as xml) the "def20" element
     */
    public void xsetDef20(org.apache.xmlbeans.XmlInt def20)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF20$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEF20$70);
            }
            target.set(def20);
        }
    }
    
    /**
     * Nils the "def20" element
     */
    public void setNilDef20()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DEF20$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DEF20$70);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def20" element
     */
    public void unsetDef20()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF20$70, 0);
        }
    }
    
    /**
     * Gets the "cardno" element
     */
    public java.lang.String getCardno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CARDNO$72, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "cardno" element
     */
    public org.apache.xmlbeans.XmlString xgetCardno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CARDNO$72, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "cardno" element
     */
    public boolean isNilCardno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CARDNO$72, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "cardno" element
     */
    public boolean isSetCardno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CARDNO$72) != 0;
        }
    }
    
    /**
     * Sets the "cardno" element
     */
    public void setCardno(java.lang.String cardno)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CARDNO$72, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CARDNO$72);
            }
            target.setStringValue(cardno);
        }
    }
    
    /**
     * Sets (as xml) the "cardno" element
     */
    public void xsetCardno(org.apache.xmlbeans.XmlString cardno)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CARDNO$72, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CARDNO$72);
            }
            target.set(cardno);
        }
    }
    
    /**
     * Nils the "cardno" element
     */
    public void setNilCardno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CARDNO$72, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CARDNO$72);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "cardno" element
     */
    public void unsetCardno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CARDNO$72, 0);
        }
    }
    
    /**
     * Gets the "def4" element
     */
    public java.lang.String getDef4()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF4$74, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "def4" element
     */
    public org.apache.xmlbeans.XmlString xgetDef4()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF4$74, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def4" element
     */
    public boolean isNilDef4()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF4$74, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def4" element
     */
    public boolean isSetDef4()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF4$74) != 0;
        }
    }
    
    /**
     * Sets the "def4" element
     */
    public void setDef4(java.lang.String def4)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF4$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF4$74);
            }
            target.setStringValue(def4);
        }
    }
    
    /**
     * Sets (as xml) the "def4" element
     */
    public void xsetDef4(org.apache.xmlbeans.XmlString def4)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF4$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF4$74);
            }
            target.set(def4);
        }
    }
    
    /**
     * Nils the "def4" element
     */
    public void setNilDef4()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF4$74, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF4$74);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def4" element
     */
    public void unsetDef4()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF4$74, 0);
        }
    }
    
    /**
     * Gets the "def3" element
     */
    public java.lang.String getDef3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF3$76, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "def3" element
     */
    public org.apache.xmlbeans.XmlString xgetDef3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF3$76, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def3" element
     */
    public boolean isNilDef3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF3$76, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def3" element
     */
    public boolean isSetDef3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF3$76) != 0;
        }
    }
    
    /**
     * Sets the "def3" element
     */
    public void setDef3(java.lang.String def3)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF3$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF3$76);
            }
            target.setStringValue(def3);
        }
    }
    
    /**
     * Sets (as xml) the "def3" element
     */
    public void xsetDef3(org.apache.xmlbeans.XmlString def3)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF3$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF3$76);
            }
            target.set(def3);
        }
    }
    
    /**
     * Nils the "def3" element
     */
    public void setNilDef3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF3$76, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF3$76);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def3" element
     */
    public void unsetDef3()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF3$76, 0);
        }
    }
    
    /**
     * Gets the "def2" element
     */
    public java.lang.String getDef2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF2$78, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "def2" element
     */
    public org.apache.xmlbeans.XmlString xgetDef2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF2$78, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def2" element
     */
    public boolean isNilDef2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF2$78, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def2" element
     */
    public boolean isSetDef2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF2$78) != 0;
        }
    }
    
    /**
     * Sets the "def2" element
     */
    public void setDef2(java.lang.String def2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF2$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF2$78);
            }
            target.setStringValue(def2);
        }
    }
    
    /**
     * Sets (as xml) the "def2" element
     */
    public void xsetDef2(org.apache.xmlbeans.XmlString def2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF2$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF2$78);
            }
            target.set(def2);
        }
    }
    
    /**
     * Nils the "def2" element
     */
    public void setNilDef2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF2$78, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF2$78);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def2" element
     */
    public void unsetDef2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF2$78, 0);
        }
    }
    
    /**
     * Gets the "def1" element
     */
    public java.lang.String getDef1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF1$80, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "def1" element
     */
    public org.apache.xmlbeans.XmlString xgetDef1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF1$80, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "def1" element
     */
    public boolean isNilDef1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF1$80, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "def1" element
     */
    public boolean isSetDef1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DEF1$80) != 0;
        }
    }
    
    /**
     * Sets the "def1" element
     */
    public void setDef1(java.lang.String def1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DEF1$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DEF1$80);
            }
            target.setStringValue(def1);
        }
    }
    
    /**
     * Sets (as xml) the "def1" element
     */
    public void xsetDef1(org.apache.xmlbeans.XmlString def1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF1$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF1$80);
            }
            target.set(def1);
        }
    }
    
    /**
     * Nils the "def1" element
     */
    public void setNilDef1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DEF1$80, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DEF1$80);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "def1" element
     */
    public void unsetDef1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DEF1$80, 0);
        }
    }
}
