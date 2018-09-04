/*
 * XML Type:  ZyhtVO
 * Namespace: http://interf.bd.crd.vo.nc/ZyhtVO
 * Java type: nc.vo.crd.bd.interf.zyhtvo.ZyhtVO
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.bd.interf.zyhtvo.impl;
/**
 * An XML ZyhtVO(@http://interf.bd.crd.vo.nc/ZyhtVO).
 *
 * This is a complex type.
 */
public class ZyhtVOImpl extends nc.uap.ws.lang.impl.SuperVOImpl implements nc.vo.crd.bd.interf.zyhtvo.ZyhtVO
{
    
    public ZyhtVOImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CORPNO$0 = 
        new javax.xml.namespace.QName("", "corpno");
    private static final javax.xml.namespace.QName NUM$2 = 
        new javax.xml.namespace.QName("", "num");
    private static final javax.xml.namespace.QName APPCODE$4 = 
        new javax.xml.namespace.QName("", "appcode");
    private static final javax.xml.namespace.QName DATEEND$6 = 
        new javax.xml.namespace.QName("", "dateEnd");
    private static final javax.xml.namespace.QName DATE$8 = 
        new javax.xml.namespace.QName("", "date");
    private static final javax.xml.namespace.QName DATESTART$10 = 
        new javax.xml.namespace.QName("", "dateStart");
    private static final javax.xml.namespace.QName PASSWORD$12 = 
        new javax.xml.namespace.QName("", "password");
    
    
    /**
     * Gets the "corpno" element
     */
    public java.lang.String getCorpno()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CORPNO$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CORPNO$0, 0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CORPNO$0, 0);
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
            return get_store().count_elements(CORPNO$0) != 0;
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CORPNO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CORPNO$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CORPNO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CORPNO$0);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CORPNO$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CORPNO$0);
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
            get_store().remove_element(CORPNO$0, 0);
        }
    }
    
    /**
     * Gets the "num" element
     */
    public int getNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUM$2, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "num" element
     */
    public org.apache.xmlbeans.XmlInt xgetNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(NUM$2, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "num" element
     */
    public boolean isNilNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(NUM$2, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "num" element
     */
    public boolean isSetNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(NUM$2) != 0;
        }
    }
    
    /**
     * Sets the "num" element
     */
    public void setNum(int num)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(NUM$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(NUM$2);
            }
            target.setIntValue(num);
        }
    }
    
    /**
     * Sets (as xml) the "num" element
     */
    public void xsetNum(org.apache.xmlbeans.XmlInt num)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(NUM$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(NUM$2);
            }
            target.set(num);
        }
    }
    
    /**
     * Nils the "num" element
     */
    public void setNilNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(NUM$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(NUM$2);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "num" element
     */
    public void unsetNum()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(NUM$2, 0);
        }
    }
    
    /**
     * Gets the "appcode" element
     */
    public java.lang.String getAppcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(APPCODE$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "appcode" element
     */
    public org.apache.xmlbeans.XmlString xgetAppcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(APPCODE$4, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "appcode" element
     */
    public boolean isNilAppcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(APPCODE$4, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "appcode" element
     */
    public boolean isSetAppcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(APPCODE$4) != 0;
        }
    }
    
    /**
     * Sets the "appcode" element
     */
    public void setAppcode(java.lang.String appcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(APPCODE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(APPCODE$4);
            }
            target.setStringValue(appcode);
        }
    }
    
    /**
     * Sets (as xml) the "appcode" element
     */
    public void xsetAppcode(org.apache.xmlbeans.XmlString appcode)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(APPCODE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(APPCODE$4);
            }
            target.set(appcode);
        }
    }
    
    /**
     * Nils the "appcode" element
     */
    public void setNilAppcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(APPCODE$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(APPCODE$4);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "appcode" element
     */
    public void unsetAppcode()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(APPCODE$4, 0);
        }
    }
    
    /**
     * Gets the "dateEnd" element
     */
    public java.lang.String getDateEnd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEEND$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "dateEnd" element
     */
    public org.apache.xmlbeans.XmlString xgetDateEnd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATEEND$6, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dateEnd" element
     */
    public boolean isNilDateEnd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATEEND$6, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dateEnd" element
     */
    public boolean isSetDateEnd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATEEND$6) != 0;
        }
    }
    
    /**
     * Sets the "dateEnd" element
     */
    public void setDateEnd(java.lang.String dateEnd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATEEND$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATEEND$6);
            }
            target.setStringValue(dateEnd);
        }
    }
    
    /**
     * Sets (as xml) the "dateEnd" element
     */
    public void xsetDateEnd(org.apache.xmlbeans.XmlString dateEnd)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATEEND$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DATEEND$6);
            }
            target.set(dateEnd);
        }
    }
    
    /**
     * Nils the "dateEnd" element
     */
    public void setNilDateEnd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATEEND$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DATEEND$6);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dateEnd" element
     */
    public void unsetDateEnd()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATEEND$6, 0);
        }
    }
    
    /**
     * Gets the "date" element
     */
    public java.lang.String getDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATE$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "date" element
     */
    public org.apache.xmlbeans.XmlString xgetDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATE$8, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "date" element
     */
    public boolean isNilDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATE$8, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "date" element
     */
    public boolean isSetDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATE$8) != 0;
        }
    }
    
    /**
     * Sets the "date" element
     */
    public void setDate(java.lang.String date)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATE$8);
            }
            target.setStringValue(date);
        }
    }
    
    /**
     * Sets (as xml) the "date" element
     */
    public void xsetDate(org.apache.xmlbeans.XmlString date)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DATE$8);
            }
            target.set(date);
        }
    }
    
    /**
     * Nils the "date" element
     */
    public void setNilDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATE$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DATE$8);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "date" element
     */
    public void unsetDate()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATE$8, 0);
        }
    }
    
    /**
     * Gets the "dateStart" element
     */
    public java.lang.String getDateStart()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATESTART$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "dateStart" element
     */
    public org.apache.xmlbeans.XmlString xgetDateStart()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATESTART$10, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "dateStart" element
     */
    public boolean isNilDateStart()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATESTART$10, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "dateStart" element
     */
    public boolean isSetDateStart()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(DATESTART$10) != 0;
        }
    }
    
    /**
     * Sets the "dateStart" element
     */
    public void setDateStart(java.lang.String dateStart)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATESTART$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATESTART$10);
            }
            target.setStringValue(dateStart);
        }
    }
    
    /**
     * Sets (as xml) the "dateStart" element
     */
    public void xsetDateStart(org.apache.xmlbeans.XmlString dateStart)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATESTART$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DATESTART$10);
            }
            target.set(dateStart);
        }
    }
    
    /**
     * Nils the "dateStart" element
     */
    public void setNilDateStart()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATESTART$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DATESTART$10);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "dateStart" element
     */
    public void unsetDateStart()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(DATESTART$10, 0);
        }
    }
    
    /**
     * Gets the "password" element
     */
    public java.lang.String getPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSWORD$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "password" element
     */
    public org.apache.xmlbeans.XmlString xgetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$12, 0);
            return target;
        }
    }
    
    /**
     * Tests for nil "password" element
     */
    public boolean isNilPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$12, 0);
            if (target == null) return false;
            return target.isNil();
        }
    }
    
    /**
     * True if has "password" element
     */
    public boolean isSetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PASSWORD$12) != 0;
        }
    }
    
    /**
     * Sets the "password" element
     */
    public void setPassword(java.lang.String password)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PASSWORD$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PASSWORD$12);
            }
            target.setStringValue(password);
        }
    }
    
    /**
     * Sets (as xml) the "password" element
     */
    public void xsetPassword(org.apache.xmlbeans.XmlString password)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PASSWORD$12);
            }
            target.set(password);
        }
    }
    
    /**
     * Nils the "password" element
     */
    public void setNilPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PASSWORD$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PASSWORD$12);
            }
            target.setNil();
        }
    }
    
    /**
     * Unsets the "password" element
     */
    public void unsetPassword()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PASSWORD$12, 0);
        }
    }
}
