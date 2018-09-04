/*
 * XML Type:  EbankDzdPlusVO
 * Namespace: http://interf.bd.crd.vo.nc/EbankDzdPlusVO
 * Java type: nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.bd.interf.ebankdzdplusvo.impl;
/**
 * An XML EbankDzdPlusVO(@http://interf.bd.crd.vo.nc/EbankDzdPlusVO).
 *
 * This is a complex type.
 */
public class EbankDzdPlusVOImpl extends nc.uap.ws.lang.impl.SuperVOImpl implements nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO
{
    
    public EbankDzdPlusVOImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CURACC$0 = 
        new javax.xml.namespace.QName("", "curacc");
    private static final javax.xml.namespace.QName TXSEQID$2 = 
        new javax.xml.namespace.QName("", "txseqid");
    private static final javax.xml.namespace.QName RECONCILIATIONCODE$4 = 
        new javax.xml.namespace.QName("", "reconciliationcode");
    private static final javax.xml.namespace.QName TS$6 = 
        new javax.xml.namespace.QName("", "ts");
    private static final javax.xml.namespace.QName YURREF$8 = 
        new javax.xml.namespace.QName("", "yurref");
    private static final javax.xml.namespace.QName USERID$10 = 
        new javax.xml.namespace.QName("", "userid");
    private static final javax.xml.namespace.QName CURRCODE$12 = 
        new javax.xml.namespace.QName("", "currcode");
    private static final javax.xml.namespace.QName STYLEFLAG$14 = 
        new javax.xml.namespace.QName("", "styleflag");
    private static final javax.xml.namespace.QName REPRESERVED1$16 = 
        new javax.xml.namespace.QName("", "represerved1");
    private static final javax.xml.namespace.QName XYFLAG$18 = 
        new javax.xml.namespace.QName("", "xyflag");
    private static final javax.xml.namespace.QName REPRESERVED2$20 = 
        new javax.xml.namespace.QName("", "represerved2");
    private static final javax.xml.namespace.QName CURRNAME$22 = 
        new javax.xml.namespace.QName("", "currname");
    private static final javax.xml.namespace.QName BANKTYPECODE$24 = 
        new javax.xml.namespace.QName("", "banktypecode");
    private static final javax.xml.namespace.QName CRTACC$26 = 
        new javax.xml.namespace.QName("", "crtacc");
    private static final javax.xml.namespace.QName ISHAND$28 = 
        new javax.xml.namespace.QName("", "ishand");
    private static final javax.xml.namespace.QName DBTBALANCE$30 = 
        new javax.xml.namespace.QName("", "dbtbalance");
    private static final javax.xml.namespace.QName CCCYNBR$32 = 
        new javax.xml.namespace.QName("", "c_ccynbr");
    private static final javax.xml.namespace.QName DBTACC$34 = 
        new javax.xml.namespace.QName("", "dbtacc");
    private static final javax.xml.namespace.QName NUSAGE$36 = 
        new javax.xml.namespace.QName("", "nusage");
    private static final javax.xml.namespace.QName CRTDWMC$38 = 
        new javax.xml.namespace.QName("", "crtdwmc");
    private static final javax.xml.namespace.QName PKEBANKDZD$40 = 
        new javax.xml.namespace.QName("", "pk_ebank_dzd");
    private static final javax.xml.namespace.QName DR$42 = 
        new javax.xml.namespace.QName("", "dr");
    private static final javax.xml.namespace.QName DBTDWMC$44 = 
        new javax.xml.namespace.QName("", "dbtdwmc");
    private static final javax.xml.namespace.QName TRANSABSTR$46 = 
        new javax.xml.namespace.QName("", "trans_abstr");
    private static final javax.xml.namespace.QName TRSAMT$48 = 
        new javax.xml.namespace.QName("", "trsamt");
    private static final javax.xml.namespace.QName TRACENO$50 = 
        new javax.xml.namespace.QName("", "traceno");
    private static final javax.xml.namespace.QName UID2$52 = 
        new javax.xml.namespace.QName("", "uid2");
    private static final javax.xml.namespace.QName ACCTHOSTSEQID$54 = 
        new javax.xml.namespace.QName("", "accthostseqid");
    private static final javax.xml.namespace.QName CRTBALANCE$56 = 
        new javax.xml.namespace.QName("", "crtbalance");
    private static final javax.xml.namespace.QName TRANSTIME$58 = 
        new javax.xml.namespace.QName("", "trans_time");
    private static final javax.xml.namespace.QName GRPACC$60 = 
        new javax.xml.namespace.QName("", "grpacc");
    private static final javax.xml.namespace.QName TRANSDATE$62 = 
        new javax.xml.namespace.QName("", "trans_date");
    private static final javax.xml.namespace.QName CDFLAG$64 = 
        new javax.xml.namespace.QName("", "cdflag");
    private static final javax.xml.namespace.QName TRANSTYPE$66 = 
        new javax.xml.namespace.QName("", "trans_type");
    private static final javax.xml.namespace.QName CHECKNUM$68 = 
        new javax.xml.namespace.QName("", "check_num");
    private static final javax.xml.namespace.QName SERIAL$70 = 
        new javax.xml.namespace.QName("", "serial");
    
    
    /**
     * Gets the "curacc" element
     */
    public java.lang.String getCuracc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURACC$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "curacc" element
     */
    public org.apache.xmlbeans.XmlString xgetCuracc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURACC$0, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "curacc" element
     */
    public boolean isNilCuracc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURACC$0, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "curacc" element
     */
    public boolean isSetCuracc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CURACC$0) != 0;
        }
    }
    
    /**
     * Sets the "curacc" element
     */
    public void setCuracc(java.lang.String curacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURACC$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CURACC$0);
            }
            target.setStringValue(curacc);
        }
    }
    
    /**
     * Sets (as xml) the "curacc" element
     */
    public void xsetCuracc(org.apache.xmlbeans.XmlString curacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURACC$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURACC$0);
            }
            target.set(curacc);
        }
    }
    
    /**
     * Nils the "curacc" element
     */
    public void setNilCuracc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURACC$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURACC$0);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "curacc" element
     */
    public void unsetCuracc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CURACC$0, 0);
        }
    }
    
    /**
     * Gets the "txseqid" element
     */
    public java.lang.String getTxseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXSEQID$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "txseqid" element
     */
    public org.apache.xmlbeans.XmlString xgetTxseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXSEQID$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "txseqid" element
     */
    public boolean isNilTxseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXSEQID$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "txseqid" element
     */
    public boolean isSetTxseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TXSEQID$2) != 0;
        }
    }
    
    /**
     * Sets the "txseqid" element
     */
    public void setTxseqid(java.lang.String txseqid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TXSEQID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TXSEQID$2);
            }
            target.setStringValue(txseqid);
        }
    }
    
    /**
     * Sets (as xml) the "txseqid" element
     */
    public void xsetTxseqid(org.apache.xmlbeans.XmlString txseqid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXSEQID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TXSEQID$2);
            }
            target.set(txseqid);
        }
    }
    
    /**
     * Nils the "txseqid" element
     */
    public void setNilTxseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TXSEQID$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TXSEQID$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "txseqid" element
     */
    public void unsetTxseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TXSEQID$2, 0);
        }
    }
    
    /**
     * Gets the "reconciliationcode" element
     */
    public java.lang.String getReconciliationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RECONCILIATIONCODE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "reconciliationcode" element
     */
    public org.apache.xmlbeans.XmlString xgetReconciliationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECONCILIATIONCODE$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "reconciliationcode" element
     */
    public boolean isNilReconciliationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECONCILIATIONCODE$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "reconciliationcode" element
     */
    public boolean isSetReconciliationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(RECONCILIATIONCODE$4) != 0;
        }
    }
    
    /**
     * Sets the "reconciliationcode" element
     */
    public void setReconciliationcode(java.lang.String reconciliationcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RECONCILIATIONCODE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RECONCILIATIONCODE$4);
            }
            target.setStringValue(reconciliationcode);
        }
    }
    
    /**
     * Sets (as xml) the "reconciliationcode" element
     */
    public void xsetReconciliationcode(org.apache.xmlbeans.XmlString reconciliationcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECONCILIATIONCODE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RECONCILIATIONCODE$4);
            }
            target.set(reconciliationcode);
        }
    }
    
    /**
     * Nils the "reconciliationcode" element
     */
    public void setNilReconciliationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RECONCILIATIONCODE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RECONCILIATIONCODE$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "reconciliationcode" element
     */
    public void unsetReconciliationcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(RECONCILIATIONCODE$4, 0);
        }
    }
    
    /**
     * Gets the "ts" element
     */
    public java.lang.String getTs()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TS$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ts" element
     */
    public org.apache.xmlbeans.XmlString xgetTs()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TS$6, 0);
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
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TS$6, 0);
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
            return get_store().count_elements(TS$6) != 0;
        }
    }
    
    /**
     * Sets the "ts" element
     */
    public void setTs(java.lang.String ts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TS$6);
            }
            target.setStringValue(ts);
        }
    }
    
    /**
     * Sets (as xml) the "ts" element
     */
    public void xsetTs(org.apache.xmlbeans.XmlString ts)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TS$6);
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
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TS$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TS$6);
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
            get_store().remove_element(TS$6, 0);
        }
    }
    
    /**
     * Gets the "yurref" element
     */
    public java.lang.String getYurref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(YURREF$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "yurref" element
     */
    public org.apache.xmlbeans.XmlString xgetYurref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(YURREF$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "yurref" element
     */
    public boolean isNilYurref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(YURREF$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "yurref" element
     */
    public boolean isSetYurref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(YURREF$8) != 0;
        }
    }
    
    /**
     * Sets the "yurref" element
     */
    public void setYurref(java.lang.String yurref)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(YURREF$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(YURREF$8);
            }
            target.setStringValue(yurref);
        }
    }
    
    /**
     * Sets (as xml) the "yurref" element
     */
    public void xsetYurref(org.apache.xmlbeans.XmlString yurref)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(YURREF$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(YURREF$8);
            }
            target.set(yurref);
        }
    }
    
    /**
     * Nils the "yurref" element
     */
    public void setNilYurref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(YURREF$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(YURREF$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "yurref" element
     */
    public void unsetYurref()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(YURREF$8, 0);
        }
    }
    
    /**
     * Gets the "userid" element
     */
    public java.lang.String getUserid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERID$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "userid" element
     */
    public org.apache.xmlbeans.XmlString xgetUserid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERID$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "userid" element
     */
    public boolean isNilUserid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERID$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "userid" element
     */
    public boolean isSetUserid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(USERID$10) != 0;
        }
    }
    
    /**
     * Sets the "userid" element
     */
    public void setUserid(java.lang.String userid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERID$10);
            }
            target.setStringValue(userid);
        }
    }
    
    /**
     * Sets (as xml) the "userid" element
     */
    public void xsetUserid(org.apache.xmlbeans.XmlString userid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERID$10);
            }
            target.set(userid);
        }
    }
    
    /**
     * Nils the "userid" element
     */
    public void setNilUserid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERID$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERID$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "userid" element
     */
    public void unsetUserid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(USERID$10, 0);
        }
    }
    
    /**
     * Gets the "currcode" element
     */
    public java.lang.String getCurrcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRCODE$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "currcode" element
     */
    public org.apache.xmlbeans.XmlString xgetCurrcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRCODE$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "currcode" element
     */
    public boolean isNilCurrcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRCODE$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "currcode" element
     */
    public boolean isSetCurrcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CURRCODE$12) != 0;
        }
    }
    
    /**
     * Sets the "currcode" element
     */
    public void setCurrcode(java.lang.String currcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRCODE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CURRCODE$12);
            }
            target.setStringValue(currcode);
        }
    }
    
    /**
     * Sets (as xml) the "currcode" element
     */
    public void xsetCurrcode(org.apache.xmlbeans.XmlString currcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRCODE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRCODE$12);
            }
            target.set(currcode);
        }
    }
    
    /**
     * Nils the "currcode" element
     */
    public void setNilCurrcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRCODE$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRCODE$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "currcode" element
     */
    public void unsetCurrcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CURRCODE$12, 0);
        }
    }
    
    /**
     * Gets the "styleflag" element
     */
    public java.lang.String getStyleflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STYLEFLAG$14, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "styleflag" element
     */
    public org.apache.xmlbeans.XmlString xgetStyleflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STYLEFLAG$14, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "styleflag" element
     */
    public boolean isNilStyleflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STYLEFLAG$14, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "styleflag" element
     */
    public boolean isSetStyleflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STYLEFLAG$14) != 0;
        }
    }
    
    /**
     * Sets the "styleflag" element
     */
    public void setStyleflag(java.lang.String styleflag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STYLEFLAG$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STYLEFLAG$14);
            }
            target.setStringValue(styleflag);
        }
    }
    
    /**
     * Sets (as xml) the "styleflag" element
     */
    public void xsetStyleflag(org.apache.xmlbeans.XmlString styleflag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STYLEFLAG$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STYLEFLAG$14);
            }
            target.set(styleflag);
        }
    }
    
    /**
     * Nils the "styleflag" element
     */
    public void setNilStyleflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(STYLEFLAG$14, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(STYLEFLAG$14);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "styleflag" element
     */
    public void unsetStyleflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STYLEFLAG$14, 0);
        }
    }
    
    /**
     * Gets the "represerved1" element
     */
    public java.lang.String getRepreserved1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPRESERVED1$16, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "represerved1" element
     */
    public org.apache.xmlbeans.XmlString xgetRepreserved1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPRESERVED1$16, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "represerved1" element
     */
    public boolean isNilRepreserved1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPRESERVED1$16, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "represerved1" element
     */
    public boolean isSetRepreserved1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REPRESERVED1$16) != 0;
        }
    }
    
    /**
     * Sets the "represerved1" element
     */
    public void setRepreserved1(java.lang.String represerved1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPRESERVED1$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REPRESERVED1$16);
            }
            target.setStringValue(represerved1);
        }
    }
    
    /**
     * Sets (as xml) the "represerved1" element
     */
    public void xsetRepreserved1(org.apache.xmlbeans.XmlString represerved1)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPRESERVED1$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REPRESERVED1$16);
            }
            target.set(represerved1);
        }
    }
    
    /**
     * Nils the "represerved1" element
     */
    public void setNilRepreserved1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPRESERVED1$16, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REPRESERVED1$16);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "represerved1" element
     */
    public void unsetRepreserved1()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REPRESERVED1$16, 0);
        }
    }
    
    /**
     * Gets the "xyflag" element
     */
    public java.lang.String getXyflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(XYFLAG$18, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "xyflag" element
     */
    public org.apache.xmlbeans.XmlString xgetXyflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XYFLAG$18, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "xyflag" element
     */
    public boolean isNilXyflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XYFLAG$18, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "xyflag" element
     */
    public boolean isSetXyflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(XYFLAG$18) != 0;
        }
    }
    
    /**
     * Sets the "xyflag" element
     */
    public void setXyflag(java.lang.String xyflag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(XYFLAG$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(XYFLAG$18);
            }
            target.setStringValue(xyflag);
        }
    }
    
    /**
     * Sets (as xml) the "xyflag" element
     */
    public void xsetXyflag(org.apache.xmlbeans.XmlString xyflag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XYFLAG$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(XYFLAG$18);
            }
            target.set(xyflag);
        }
    }
    
    /**
     * Nils the "xyflag" element
     */
    public void setNilXyflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(XYFLAG$18, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(XYFLAG$18);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "xyflag" element
     */
    public void unsetXyflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(XYFLAG$18, 0);
        }
    }
    
    /**
     * Gets the "represerved2" element
     */
    public java.lang.String getRepreserved2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPRESERVED2$20, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "represerved2" element
     */
    public org.apache.xmlbeans.XmlString xgetRepreserved2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPRESERVED2$20, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "represerved2" element
     */
    public boolean isNilRepreserved2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPRESERVED2$20, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "represerved2" element
     */
    public boolean isSetRepreserved2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(REPRESERVED2$20) != 0;
        }
    }
    
    /**
     * Sets the "represerved2" element
     */
    public void setRepreserved2(java.lang.String represerved2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(REPRESERVED2$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(REPRESERVED2$20);
            }
            target.setStringValue(represerved2);
        }
    }
    
    /**
     * Sets (as xml) the "represerved2" element
     */
    public void xsetRepreserved2(org.apache.xmlbeans.XmlString represerved2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPRESERVED2$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REPRESERVED2$20);
            }
            target.set(represerved2);
        }
    }
    
    /**
     * Nils the "represerved2" element
     */
    public void setNilRepreserved2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(REPRESERVED2$20, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(REPRESERVED2$20);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "represerved2" element
     */
    public void unsetRepreserved2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(REPRESERVED2$20, 0);
        }
    }
    
    /**
     * Gets the "currname" element
     */
    public java.lang.String getCurrname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRNAME$22, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "currname" element
     */
    public org.apache.xmlbeans.XmlString xgetCurrname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRNAME$22, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "currname" element
     */
    public boolean isNilCurrname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRNAME$22, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "currname" element
     */
    public boolean isSetCurrname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CURRNAME$22) != 0;
        }
    }
    
    /**
     * Sets the "currname" element
     */
    public void setCurrname(java.lang.String currname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CURRNAME$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CURRNAME$22);
            }
            target.setStringValue(currname);
        }
    }
    
    /**
     * Sets (as xml) the "currname" element
     */
    public void xsetCurrname(org.apache.xmlbeans.XmlString currname)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRNAME$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRNAME$22);
            }
            target.set(currname);
        }
    }
    
    /**
     * Nils the "currname" element
     */
    public void setNilCurrname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CURRNAME$22, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CURRNAME$22);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "currname" element
     */
    public void unsetCurrname()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CURRNAME$22, 0);
        }
    }
    
    /**
     * Gets the "banktypecode" element
     */
    public java.lang.String getBanktypecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BANKTYPECODE$24, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "banktypecode" element
     */
    public org.apache.xmlbeans.XmlString xgetBanktypecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BANKTYPECODE$24, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "banktypecode" element
     */
    public boolean isNilBanktypecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BANKTYPECODE$24, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "banktypecode" element
     */
    public boolean isSetBanktypecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(BANKTYPECODE$24) != 0;
        }
    }
    
    /**
     * Sets the "banktypecode" element
     */
    public void setBanktypecode(java.lang.String banktypecode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(BANKTYPECODE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(BANKTYPECODE$24);
            }
            target.setStringValue(banktypecode);
        }
    }
    
    /**
     * Sets (as xml) the "banktypecode" element
     */
    public void xsetBanktypecode(org.apache.xmlbeans.XmlString banktypecode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BANKTYPECODE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BANKTYPECODE$24);
            }
            target.set(banktypecode);
        }
    }
    
    /**
     * Nils the "banktypecode" element
     */
    public void setNilBanktypecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(BANKTYPECODE$24, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(BANKTYPECODE$24);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "banktypecode" element
     */
    public void unsetBanktypecode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(BANKTYPECODE$24, 0);
        }
    }
    
    /**
     * Gets the "crtacc" element
     */
    public java.lang.String getCrtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CRTACC$26, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "crtacc" element
     */
    public org.apache.xmlbeans.XmlString xgetCrtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CRTACC$26, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "crtacc" element
     */
    public boolean isNilCrtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CRTACC$26, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "crtacc" element
     */
    public boolean isSetCrtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CRTACC$26) != 0;
        }
    }
    
    /**
     * Sets the "crtacc" element
     */
    public void setCrtacc(java.lang.String crtacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CRTACC$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CRTACC$26);
            }
            target.setStringValue(crtacc);
        }
    }
    
    /**
     * Sets (as xml) the "crtacc" element
     */
    public void xsetCrtacc(org.apache.xmlbeans.XmlString crtacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CRTACC$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CRTACC$26);
            }
            target.set(crtacc);
        }
    }
    
    /**
     * Nils the "crtacc" element
     */
    public void setNilCrtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CRTACC$26, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CRTACC$26);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "crtacc" element
     */
    public void unsetCrtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CRTACC$26, 0);
        }
    }
    
    /**
     * Gets the "ishand" element
     */
    public java.lang.String getIshand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISHAND$28, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ishand" element
     */
    public org.apache.xmlbeans.XmlString xgetIshand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ISHAND$28, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "ishand" element
     */
    public boolean isNilIshand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ISHAND$28, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "ishand" element
     */
    public boolean isSetIshand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ISHAND$28) != 0;
        }
    }
    
    /**
     * Sets the "ishand" element
     */
    public void setIshand(java.lang.String ishand)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ISHAND$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ISHAND$28);
            }
            target.setStringValue(ishand);
        }
    }
    
    /**
     * Sets (as xml) the "ishand" element
     */
    public void xsetIshand(org.apache.xmlbeans.XmlString ishand)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ISHAND$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ISHAND$28);
            }
            target.set(ishand);
        }
    }
    
    /**
     * Nils the "ishand" element
     */
    public void setNilIshand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ISHAND$28, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ISHAND$28);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "ishand" element
     */
    public void unsetIshand()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ISHAND$28, 0);
        }
    }
    
    /**
     * Gets the "dbtbalance" element
     */
    public double getDbtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DBTBALANCE$30, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "dbtbalance" element
     */
    public org.apache.xmlbeans.XmlDouble xgetDbtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(DBTBALANCE$30, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dbtbalance" element
     */
    public boolean isNilDbtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(DBTBALANCE$30, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dbtbalance" element
     */
    public boolean isSetDbtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DBTBALANCE$30) != 0;
        }
    }
    
    /**
     * Sets the "dbtbalance" element
     */
    public void setDbtbalance(double dbtbalance)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DBTBALANCE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DBTBALANCE$30);
            }
            target.setDoubleValue(dbtbalance);
        }
    }
    
    /**
     * Sets (as xml) the "dbtbalance" element
     */
    public void xsetDbtbalance(org.apache.xmlbeans.XmlDouble dbtbalance)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(DBTBALANCE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(DBTBALANCE$30);
            }
            target.set(dbtbalance);
        }
    }
    
    /**
     * Nils the "dbtbalance" element
     */
    public void setNilDbtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(DBTBALANCE$30, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(DBTBALANCE$30);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dbtbalance" element
     */
    public void unsetDbtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DBTBALANCE$30, 0);
        }
    }
    
    /**
     * Gets the "c_ccynbr" element
     */
    public java.lang.String getCCcynbr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CCCYNBR$32, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "c_ccynbr" element
     */
    public org.apache.xmlbeans.XmlString xgetCCcynbr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CCCYNBR$32, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "c_ccynbr" element
     */
    public boolean isNilCCcynbr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CCCYNBR$32, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "c_ccynbr" element
     */
    public boolean isSetCCcynbr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CCCYNBR$32) != 0;
        }
    }
    
    /**
     * Sets the "c_ccynbr" element
     */
    public void setCCcynbr(java.lang.String cCcynbr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CCCYNBR$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CCCYNBR$32);
            }
            target.setStringValue(cCcynbr);
        }
    }
    
    /**
     * Sets (as xml) the "c_ccynbr" element
     */
    public void xsetCCcynbr(org.apache.xmlbeans.XmlString cCcynbr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CCCYNBR$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CCCYNBR$32);
            }
            target.set(cCcynbr);
        }
    }
    
    /**
     * Nils the "c_ccynbr" element
     */
    public void setNilCCcynbr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CCCYNBR$32, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CCCYNBR$32);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "c_ccynbr" element
     */
    public void unsetCCcynbr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CCCYNBR$32, 0);
        }
    }
    
    /**
     * Gets the "dbtacc" element
     */
    public java.lang.String getDbtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DBTACC$34, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "dbtacc" element
     */
    public org.apache.xmlbeans.XmlString xgetDbtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DBTACC$34, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dbtacc" element
     */
    public boolean isNilDbtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DBTACC$34, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dbtacc" element
     */
    public boolean isSetDbtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DBTACC$34) != 0;
        }
    }
    
    /**
     * Sets the "dbtacc" element
     */
    public void setDbtacc(java.lang.String dbtacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DBTACC$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DBTACC$34);
            }
            target.setStringValue(dbtacc);
        }
    }
    
    /**
     * Sets (as xml) the "dbtacc" element
     */
    public void xsetDbtacc(org.apache.xmlbeans.XmlString dbtacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DBTACC$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DBTACC$34);
            }
            target.set(dbtacc);
        }
    }
    
    /**
     * Nils the "dbtacc" element
     */
    public void setNilDbtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DBTACC$34, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DBTACC$34);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dbtacc" element
     */
    public void unsetDbtacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DBTACC$34, 0);
        }
    }
    
    /**
     * Gets the "nusage" element
     */
    public java.lang.String getNusage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUSAGE$36, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "nusage" element
     */
    public org.apache.xmlbeans.XmlString xgetNusage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NUSAGE$36, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "nusage" element
     */
    public boolean isNilNusage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NUSAGE$36, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "nusage" element
     */
    public boolean isSetNusage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUSAGE$36) != 0;
        }
    }
    
    /**
     * Sets the "nusage" element
     */
    public void setNusage(java.lang.String nusage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUSAGE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUSAGE$36);
            }
            target.setStringValue(nusage);
        }
    }
    
    /**
     * Sets (as xml) the "nusage" element
     */
    public void xsetNusage(org.apache.xmlbeans.XmlString nusage)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NUSAGE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NUSAGE$36);
            }
            target.set(nusage);
        }
    }
    
    /**
     * Nils the "nusage" element
     */
    public void setNilNusage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(NUSAGE$36, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(NUSAGE$36);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "nusage" element
     */
    public void unsetNusage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUSAGE$36, 0);
        }
    }
    
    /**
     * Gets the "crtdwmc" element
     */
    public java.lang.String getCrtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CRTDWMC$38, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "crtdwmc" element
     */
    public org.apache.xmlbeans.XmlString xgetCrtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CRTDWMC$38, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "crtdwmc" element
     */
    public boolean isNilCrtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CRTDWMC$38, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "crtdwmc" element
     */
    public boolean isSetCrtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CRTDWMC$38) != 0;
        }
    }
    
    /**
     * Sets the "crtdwmc" element
     */
    public void setCrtdwmc(java.lang.String crtdwmc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CRTDWMC$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CRTDWMC$38);
            }
            target.setStringValue(crtdwmc);
        }
    }
    
    /**
     * Sets (as xml) the "crtdwmc" element
     */
    public void xsetCrtdwmc(org.apache.xmlbeans.XmlString crtdwmc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CRTDWMC$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CRTDWMC$38);
            }
            target.set(crtdwmc);
        }
    }
    
    /**
     * Nils the "crtdwmc" element
     */
    public void setNilCrtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CRTDWMC$38, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CRTDWMC$38);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "crtdwmc" element
     */
    public void unsetCrtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CRTDWMC$38, 0);
        }
    }
    
    /**
     * Gets the "pk_ebank_dzd" element
     */
    public java.lang.String getPkEbankDzd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PKEBANKDZD$40, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "pk_ebank_dzd" element
     */
    public org.apache.xmlbeans.XmlString xgetPkEbankDzd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKEBANKDZD$40, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "pk_ebank_dzd" element
     */
    public boolean isNilPkEbankDzd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKEBANKDZD$40, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "pk_ebank_dzd" element
     */
    public boolean isSetPkEbankDzd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PKEBANKDZD$40) != 0;
        }
    }
    
    /**
     * Sets the "pk_ebank_dzd" element
     */
    public void setPkEbankDzd(java.lang.String pkEbankDzd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PKEBANKDZD$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PKEBANKDZD$40);
            }
            target.setStringValue(pkEbankDzd);
        }
    }
    
    /**
     * Sets (as xml) the "pk_ebank_dzd" element
     */
    public void xsetPkEbankDzd(org.apache.xmlbeans.XmlString pkEbankDzd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKEBANKDZD$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PKEBANKDZD$40);
            }
            target.set(pkEbankDzd);
        }
    }
    
    /**
     * Nils the "pk_ebank_dzd" element
     */
    public void setNilPkEbankDzd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PKEBANKDZD$40, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PKEBANKDZD$40);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "pk_ebank_dzd" element
     */
    public void unsetPkEbankDzd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PKEBANKDZD$40, 0);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DR$42, 0);
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
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DR$42, 0);
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
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DR$42, 0);
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
            return get_store().count_elements(DR$42) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DR$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DR$42);
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
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DR$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DR$42);
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
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(DR$42, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(DR$42);
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
            get_store().remove_element(DR$42, 0);
        }
    }
    
    /**
     * Gets the "dbtdwmc" element
     */
    public java.lang.String getDbtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DBTDWMC$44, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "dbtdwmc" element
     */
    public org.apache.xmlbeans.XmlString xgetDbtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DBTDWMC$44, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dbtdwmc" element
     */
    public boolean isNilDbtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DBTDWMC$44, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dbtdwmc" element
     */
    public boolean isSetDbtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DBTDWMC$44) != 0;
        }
    }
    
    /**
     * Sets the "dbtdwmc" element
     */
    public void setDbtdwmc(java.lang.String dbtdwmc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DBTDWMC$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DBTDWMC$44);
            }
            target.setStringValue(dbtdwmc);
        }
    }
    
    /**
     * Sets (as xml) the "dbtdwmc" element
     */
    public void xsetDbtdwmc(org.apache.xmlbeans.XmlString dbtdwmc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DBTDWMC$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DBTDWMC$44);
            }
            target.set(dbtdwmc);
        }
    }
    
    /**
     * Nils the "dbtdwmc" element
     */
    public void setNilDbtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DBTDWMC$44, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DBTDWMC$44);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dbtdwmc" element
     */
    public void unsetDbtdwmc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DBTDWMC$44, 0);
        }
    }
    
    /**
     * Gets the "trans_abstr" element
     */
    public java.lang.String getTransAbstr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSABSTR$46, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "trans_abstr" element
     */
    public org.apache.xmlbeans.XmlString xgetTransAbstr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSABSTR$46, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "trans_abstr" element
     */
    public boolean isNilTransAbstr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSABSTR$46, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "trans_abstr" element
     */
    public boolean isSetTransAbstr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRANSABSTR$46) != 0;
        }
    }
    
    /**
     * Sets the "trans_abstr" element
     */
    public void setTransAbstr(java.lang.String transAbstr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSABSTR$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANSABSTR$46);
            }
            target.setStringValue(transAbstr);
        }
    }
    
    /**
     * Sets (as xml) the "trans_abstr" element
     */
    public void xsetTransAbstr(org.apache.xmlbeans.XmlString transAbstr)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSABSTR$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSABSTR$46);
            }
            target.set(transAbstr);
        }
    }
    
    /**
     * Nils the "trans_abstr" element
     */
    public void setNilTransAbstr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSABSTR$46, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSABSTR$46);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "trans_abstr" element
     */
    public void unsetTransAbstr()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRANSABSTR$46, 0);
        }
    }
    
    /**
     * Gets the "trsamt" element
     */
    public double getTrsamt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRSAMT$48, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "trsamt" element
     */
    public org.apache.xmlbeans.XmlDouble xgetTrsamt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TRSAMT$48, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "trsamt" element
     */
    public boolean isNilTrsamt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TRSAMT$48, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "trsamt" element
     */
    public boolean isSetTrsamt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRSAMT$48) != 0;
        }
    }
    
    /**
     * Sets the "trsamt" element
     */
    public void setTrsamt(double trsamt)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRSAMT$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRSAMT$48);
            }
            target.setDoubleValue(trsamt);
        }
    }
    
    /**
     * Sets (as xml) the "trsamt" element
     */
    public void xsetTrsamt(org.apache.xmlbeans.XmlDouble trsamt)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TRSAMT$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(TRSAMT$48);
            }
            target.set(trsamt);
        }
    }
    
    /**
     * Nils the "trsamt" element
     */
    public void setNilTrsamt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(TRSAMT$48, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(TRSAMT$48);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "trsamt" element
     */
    public void unsetTrsamt()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRSAMT$48, 0);
        }
    }
    
    /**
     * Gets the "traceno" element
     */
    public java.lang.String getTraceno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRACENO$50, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "traceno" element
     */
    public org.apache.xmlbeans.XmlString xgetTraceno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRACENO$50, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "traceno" element
     */
    public boolean isNilTraceno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRACENO$50, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "traceno" element
     */
    public boolean isSetTraceno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRACENO$50) != 0;
        }
    }
    
    /**
     * Sets the "traceno" element
     */
    public void setTraceno(java.lang.String traceno)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRACENO$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRACENO$50);
            }
            target.setStringValue(traceno);
        }
    }
    
    /**
     * Sets (as xml) the "traceno" element
     */
    public void xsetTraceno(org.apache.xmlbeans.XmlString traceno)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRACENO$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRACENO$50);
            }
            target.set(traceno);
        }
    }
    
    /**
     * Nils the "traceno" element
     */
    public void setNilTraceno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRACENO$50, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRACENO$50);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "traceno" element
     */
    public void unsetTraceno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRACENO$50, 0);
        }
    }
    
    /**
     * Gets the "uid2" element
     */
    public java.lang.String getUid2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UID2$52, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "uid2" element
     */
    public org.apache.xmlbeans.XmlString xgetUid2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(UID2$52, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "uid2" element
     */
    public boolean isNilUid2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(UID2$52, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "uid2" element
     */
    public boolean isSetUid2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(UID2$52) != 0;
        }
    }
    
    /**
     * Sets the "uid2" element
     */
    public void setUid2(java.lang.String uid2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(UID2$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(UID2$52);
            }
            target.setStringValue(uid2);
        }
    }
    
    /**
     * Sets (as xml) the "uid2" element
     */
    public void xsetUid2(org.apache.xmlbeans.XmlString uid2)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(UID2$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(UID2$52);
            }
            target.set(uid2);
        }
    }
    
    /**
     * Nils the "uid2" element
     */
    public void setNilUid2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(UID2$52, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(UID2$52);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "uid2" element
     */
    public void unsetUid2()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(UID2$52, 0);
        }
    }
    
    /**
     * Gets the "accthostseqid" element
     */
    public java.lang.String getAccthostseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACCTHOSTSEQID$54, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "accthostseqid" element
     */
    public org.apache.xmlbeans.XmlString xgetAccthostseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACCTHOSTSEQID$54, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "accthostseqid" element
     */
    public boolean isNilAccthostseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACCTHOSTSEQID$54, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "accthostseqid" element
     */
    public boolean isSetAccthostseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(ACCTHOSTSEQID$54) != 0;
        }
    }
    
    /**
     * Sets the "accthostseqid" element
     */
    public void setAccthostseqid(java.lang.String accthostseqid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(ACCTHOSTSEQID$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(ACCTHOSTSEQID$54);
            }
            target.setStringValue(accthostseqid);
        }
    }
    
    /**
     * Sets (as xml) the "accthostseqid" element
     */
    public void xsetAccthostseqid(org.apache.xmlbeans.XmlString accthostseqid)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACCTHOSTSEQID$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ACCTHOSTSEQID$54);
            }
            target.set(accthostseqid);
        }
    }
    
    /**
     * Nils the "accthostseqid" element
     */
    public void setNilAccthostseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(ACCTHOSTSEQID$54, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(ACCTHOSTSEQID$54);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "accthostseqid" element
     */
    public void unsetAccthostseqid()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(ACCTHOSTSEQID$54, 0);
        }
    }
    
    /**
     * Gets the "crtbalance" element
     */
    public double getCrtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CRTBALANCE$56, 0);
            if (target == null)
            {
                return 0.0;
            }
            return target.getDoubleValue();
        }
    }
    
    /**
     * Gets (as xml) the "crtbalance" element
     */
    public org.apache.xmlbeans.XmlDouble xgetCrtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(CRTBALANCE$56, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "crtbalance" element
     */
    public boolean isNilCrtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(CRTBALANCE$56, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "crtbalance" element
     */
    public boolean isSetCrtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CRTBALANCE$56) != 0;
        }
    }
    
    /**
     * Sets the "crtbalance" element
     */
    public void setCrtbalance(double crtbalance)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CRTBALANCE$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CRTBALANCE$56);
            }
            target.setDoubleValue(crtbalance);
        }
    }
    
    /**
     * Sets (as xml) the "crtbalance" element
     */
    public void xsetCrtbalance(org.apache.xmlbeans.XmlDouble crtbalance)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(CRTBALANCE$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(CRTBALANCE$56);
            }
            target.set(crtbalance);
        }
    }
    
    /**
     * Nils the "crtbalance" element
     */
    public void setNilCrtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlDouble target = null;
            target = (org.apache.xmlbeans.XmlDouble)get_store().find_element_user(CRTBALANCE$56, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlDouble)get_store().add_element_user(CRTBALANCE$56);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "crtbalance" element
     */
    public void unsetCrtbalance()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CRTBALANCE$56, 0);
        }
    }
    
    /**
     * Gets the "trans_time" element
     */
    public java.lang.String getTransTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSTIME$58, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "trans_time" element
     */
    public org.apache.xmlbeans.XmlString xgetTransTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSTIME$58, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "trans_time" element
     */
    public boolean isNilTransTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSTIME$58, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "trans_time" element
     */
    public boolean isSetTransTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRANSTIME$58) != 0;
        }
    }
    
    /**
     * Sets the "trans_time" element
     */
    public void setTransTime(java.lang.String transTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSTIME$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANSTIME$58);
            }
            target.setStringValue(transTime);
        }
    }
    
    /**
     * Sets (as xml) the "trans_time" element
     */
    public void xsetTransTime(org.apache.xmlbeans.XmlString transTime)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSTIME$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSTIME$58);
            }
            target.set(transTime);
        }
    }
    
    /**
     * Nils the "trans_time" element
     */
    public void setNilTransTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSTIME$58, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSTIME$58);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "trans_time" element
     */
    public void unsetTransTime()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRANSTIME$58, 0);
        }
    }
    
    /**
     * Gets the "grpacc" element
     */
    public java.lang.String getGrpacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GRPACC$60, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "grpacc" element
     */
    public org.apache.xmlbeans.XmlString xgetGrpacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GRPACC$60, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "grpacc" element
     */
    public boolean isNilGrpacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GRPACC$60, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "grpacc" element
     */
    public boolean isSetGrpacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(GRPACC$60) != 0;
        }
    }
    
    /**
     * Sets the "grpacc" element
     */
    public void setGrpacc(java.lang.String grpacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(GRPACC$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(GRPACC$60);
            }
            target.setStringValue(grpacc);
        }
    }
    
    /**
     * Sets (as xml) the "grpacc" element
     */
    public void xsetGrpacc(org.apache.xmlbeans.XmlString grpacc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GRPACC$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(GRPACC$60);
            }
            target.set(grpacc);
        }
    }
    
    /**
     * Nils the "grpacc" element
     */
    public void setNilGrpacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(GRPACC$60, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(GRPACC$60);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "grpacc" element
     */
    public void unsetGrpacc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(GRPACC$60, 0);
        }
    }
    
    /**
     * Gets the "trans_date" element
     */
    public java.lang.String getTransDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSDATE$62, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "trans_date" element
     */
    public org.apache.xmlbeans.XmlString xgetTransDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSDATE$62, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "trans_date" element
     */
    public boolean isNilTransDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSDATE$62, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "trans_date" element
     */
    public boolean isSetTransDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRANSDATE$62) != 0;
        }
    }
    
    /**
     * Sets the "trans_date" element
     */
    public void setTransDate(java.lang.String transDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSDATE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANSDATE$62);
            }
            target.setStringValue(transDate);
        }
    }
    
    /**
     * Sets (as xml) the "trans_date" element
     */
    public void xsetTransDate(org.apache.xmlbeans.XmlString transDate)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSDATE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSDATE$62);
            }
            target.set(transDate);
        }
    }
    
    /**
     * Nils the "trans_date" element
     */
    public void setNilTransDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSDATE$62, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSDATE$62);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "trans_date" element
     */
    public void unsetTransDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRANSDATE$62, 0);
        }
    }
    
    /**
     * Gets the "cdflag" element
     */
    public java.lang.String getCdflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CDFLAG$64, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "cdflag" element
     */
    public org.apache.xmlbeans.XmlString xgetCdflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CDFLAG$64, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "cdflag" element
     */
    public boolean isNilCdflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CDFLAG$64, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "cdflag" element
     */
    public boolean isSetCdflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CDFLAG$64) != 0;
        }
    }
    
    /**
     * Sets the "cdflag" element
     */
    public void setCdflag(java.lang.String cdflag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CDFLAG$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CDFLAG$64);
            }
            target.setStringValue(cdflag);
        }
    }
    
    /**
     * Sets (as xml) the "cdflag" element
     */
    public void xsetCdflag(org.apache.xmlbeans.XmlString cdflag)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CDFLAG$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CDFLAG$64);
            }
            target.set(cdflag);
        }
    }
    
    /**
     * Nils the "cdflag" element
     */
    public void setNilCdflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CDFLAG$64, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CDFLAG$64);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "cdflag" element
     */
    public void unsetCdflag()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CDFLAG$64, 0);
        }
    }
    
    /**
     * Gets the "trans_type" element
     */
    public java.lang.String getTransType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSTYPE$66, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "trans_type" element
     */
    public org.apache.xmlbeans.XmlString xgetTransType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSTYPE$66, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "trans_type" element
     */
    public boolean isNilTransType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSTYPE$66, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "trans_type" element
     */
    public boolean isSetTransType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(TRANSTYPE$66) != 0;
        }
    }
    
    /**
     * Sets the "trans_type" element
     */
    public void setTransType(java.lang.String transType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(TRANSTYPE$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(TRANSTYPE$66);
            }
            target.setStringValue(transType);
        }
    }
    
    /**
     * Sets (as xml) the "trans_type" element
     */
    public void xsetTransType(org.apache.xmlbeans.XmlString transType)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSTYPE$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSTYPE$66);
            }
            target.set(transType);
        }
    }
    
    /**
     * Nils the "trans_type" element
     */
    public void setNilTransType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(TRANSTYPE$66, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(TRANSTYPE$66);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "trans_type" element
     */
    public void unsetTransType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(TRANSTYPE$66, 0);
        }
    }
    
    /**
     * Gets the "check_num" element
     */
    public java.lang.String getCheckNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHECKNUM$68, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "check_num" element
     */
    public org.apache.xmlbeans.XmlString xgetCheckNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKNUM$68, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "check_num" element
     */
    public boolean isNilCheckNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKNUM$68, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "check_num" element
     */
    public boolean isSetCheckNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(CHECKNUM$68) != 0;
        }
    }
    
    /**
     * Sets the "check_num" element
     */
    public void setCheckNum(java.lang.String checkNum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CHECKNUM$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CHECKNUM$68);
            }
            target.setStringValue(checkNum);
        }
    }
    
    /**
     * Sets (as xml) the "check_num" element
     */
    public void xsetCheckNum(org.apache.xmlbeans.XmlString checkNum)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKNUM$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CHECKNUM$68);
            }
            target.set(checkNum);
        }
    }
    
    /**
     * Nils the "check_num" element
     */
    public void setNilCheckNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CHECKNUM$68, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CHECKNUM$68);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "check_num" element
     */
    public void unsetCheckNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(CHECKNUM$68, 0);
        }
    }
    
    /**
     * Gets the "serial" element
     */
    public int getSerial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SERIAL$70, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "serial" element
     */
    public org.apache.xmlbeans.XmlInt xgetSerial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SERIAL$70, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "serial" element
     */
    public boolean isNilSerial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SERIAL$70, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "serial" element
     */
    public boolean isSetSerial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(SERIAL$70) != 0;
        }
    }
    
    /**
     * Sets the "serial" element
     */
    public void setSerial(int serial)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SERIAL$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SERIAL$70);
            }
            target.setIntValue(serial);
        }
    }
    
    /**
     * Sets (as xml) the "serial" element
     */
    public void xsetSerial(org.apache.xmlbeans.XmlInt serial)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SERIAL$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SERIAL$70);
            }
            target.set(serial);
        }
    }
    
    /**
     * Nils the "serial" element
     */
    public void setNilSerial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(SERIAL$70, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(SERIAL$70);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "serial" element
     */
    public void unsetSerial()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(SERIAL$70, 0);
        }
    }
}
