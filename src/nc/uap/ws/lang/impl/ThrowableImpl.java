/*
 * XML Type:  Throwable
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.Throwable
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang.impl;
/**
 * An XML Throwable(@http://ws.uap.nc/lang).
 *
 * This is a complex type.
 */
public class ThrowableImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.uap.ws.lang.Throwable
{
    
    public ThrowableImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName MESSAGE$0 = 
        new javax.xml.namespace.QName("", "message");
    private static final javax.xml.namespace.QName STACKTRACE$2 = 
        new javax.xml.namespace.QName("", "stackTrace");
    
    
    /**
     * Gets the "message" element
     */
    public java.lang.String getMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "message" element
     */
    public org.apache.xmlbeans.XmlString xgetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$0, 0);
            return target;
        }
    }
    
    /**
     * True if has "message" element
     */
    public boolean isSetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(MESSAGE$0) != 0;
        }
    }
    
    /**
     * Sets the "message" element
     */
    public void setMessage(java.lang.String message)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(MESSAGE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(MESSAGE$0);
            }
            target.setStringValue(message);
        }
    }
    
    /**
     * Sets (as xml) the "message" element
     */
    public void xsetMessage(org.apache.xmlbeans.XmlString message)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(MESSAGE$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(MESSAGE$0);
            }
            target.set(message);
        }
    }
    
    /**
     * Unsets the "message" element
     */
    public void unsetMessage()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(MESSAGE$0, 0);
        }
    }
    
    /**
     * Gets array of all "stackTrace" elements
     */
    public java.lang.String[] getStackTraceArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(STACKTRACE$2, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "stackTrace" element
     */
    public java.lang.String getStackTraceArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STACKTRACE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "stackTrace" elements
     */
    public nc.uap.ws.lang.StackTraceElement[] xgetStackTraceArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(STACKTRACE$2, targetList);
            nc.uap.ws.lang.StackTraceElement[] result = new nc.uap.ws.lang.StackTraceElement[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "stackTrace" element
     */
    public nc.uap.ws.lang.StackTraceElement xgetStackTraceArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.StackTraceElement target = null;
            target = (nc.uap.ws.lang.StackTraceElement)get_store().find_element_user(STACKTRACE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (nc.uap.ws.lang.StackTraceElement)target;
        }
    }
    
    /**
     * Returns number of "stackTrace" element
     */
    public int sizeOfStackTraceArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(STACKTRACE$2);
        }
    }
    
    /**
     * Sets array of all "stackTrace" element
     */
    public void setStackTraceArray(java.lang.String[] stackTraceArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(stackTraceArray, STACKTRACE$2);
        }
    }
    
    /**
     * Sets ith "stackTrace" element
     */
    public void setStackTraceArray(int i, java.lang.String stackTrace)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STACKTRACE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(stackTrace);
        }
    }
    
    /**
     * Sets (as xml) array of all "stackTrace" element
     */
    public void xsetStackTraceArray(nc.uap.ws.lang.StackTraceElement[]stackTraceArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(stackTraceArray, STACKTRACE$2);
        }
    }
    
    /**
     * Sets (as xml) ith "stackTrace" element
     */
    public void xsetStackTraceArray(int i, nc.uap.ws.lang.StackTraceElement stackTrace)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.StackTraceElement target = null;
            target = (nc.uap.ws.lang.StackTraceElement)get_store().find_element_user(STACKTRACE$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(stackTrace);
        }
    }
    
    /**
     * Inserts the value as the ith "stackTrace" element
     */
    public void insertStackTrace(int i, java.lang.String stackTrace)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(STACKTRACE$2, i);
            target.setStringValue(stackTrace);
        }
    }
    
    /**
     * Appends the value as the last "stackTrace" element
     */
    public void addStackTrace(java.lang.String stackTrace)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STACKTRACE$2);
            target.setStringValue(stackTrace);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "stackTrace" element
     */
    public nc.uap.ws.lang.StackTraceElement insertNewStackTrace(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.StackTraceElement target = null;
            target = (nc.uap.ws.lang.StackTraceElement)get_store().insert_element_user(STACKTRACE$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "stackTrace" element
     */
    public nc.uap.ws.lang.StackTraceElement addNewStackTrace()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.StackTraceElement target = null;
            target = (nc.uap.ws.lang.StackTraceElement)get_store().add_element_user(STACKTRACE$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "stackTrace" element
     */
    public void removeStackTrace(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(STACKTRACE$2, i);
        }
    }
}
