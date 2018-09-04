/*
 * XML Type:  SuperVO
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.SuperVO
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang.impl;
/**
 * An XML SuperVO(@http://ws.uap.nc/lang).
 *
 * This is a complex type.
 */
public class SuperVOImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.uap.ws.lang.SuperVO
{
    
    public SuperVOImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName STATUS$0 = 
        new javax.xml.namespace.QName("", "status");
    private static final javax.xml.namespace.QName DIRTY$2 = 
        new javax.xml.namespace.QName("", "dirty");
    private static final javax.xml.namespace.QName PRIMARYKEY$4 = 
        new javax.xml.namespace.QName("", "primaryKey");
    
    
    /**
     * Gets the "status" element
     */
    public int getStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$0, 0);
            if (target == null)
            {
                return 0;
            }
            return target.getIntValue();
        }
    }
    
    /**
     * Gets (as xml) the "status" element
     */
    public org.apache.xmlbeans.XmlInt xgetStatus()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUS$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "status" element
     */
    public void setStatus(int status)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STATUS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STATUS$0);
            }
            target.setIntValue(status);
        }
    }
    
    /**
     * Sets (as xml) the "status" element
     */
    public void xsetStatus(org.apache.xmlbeans.XmlInt status)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlInt target = null;
            target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(STATUS$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(STATUS$0);
            }
            target.set(status);
        }
    }
    
    /**
     * Gets the "dirty" element
     */
    public boolean getDirty()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DIRTY$2, 0);
            if (target == null)
            {
                return false;
            }
            return target.getBooleanValue();
        }
    }
    
    /**
     * Gets (as xml) the "dirty" element
     */
    public org.apache.xmlbeans.XmlBoolean xgetDirty()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(DIRTY$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "dirty" element
     */
    public void setDirty(boolean dirty)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DIRTY$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DIRTY$2);
            }
            target.setBooleanValue(dirty);
        }
    }
    
    /**
     * Sets (as xml) the "dirty" element
     */
    public void xsetDirty(org.apache.xmlbeans.XmlBoolean dirty)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlBoolean target = null;
            target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(DIRTY$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(DIRTY$2);
            }
            target.set(dirty);
        }
    }
    
    /**
     * Gets the "primaryKey" element
     */
    public java.lang.String getPrimaryKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIMARYKEY$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "primaryKey" element
     */
    public org.apache.xmlbeans.XmlString xgetPrimaryKey()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIMARYKEY$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "primaryKey" element
     */
    public void setPrimaryKey(java.lang.String primaryKey)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PRIMARYKEY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PRIMARYKEY$4);
            }
            target.setStringValue(primaryKey);
        }
    }
    
    /**
     * Sets (as xml) the "primaryKey" element
     */
    public void xsetPrimaryKey(org.apache.xmlbeans.XmlString primaryKey)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PRIMARYKEY$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PRIMARYKEY$4);
            }
            target.set(primaryKey);
        }
    }
}
