/*
 * An XML document type.
 * Localname: StackTraceElement
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.StackTraceElementDocument
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang.impl;
/**
 * A document containing one StackTraceElement(@http://ws.uap.nc/lang) element.
 *
 * This is a complex type.
 */
public class StackTraceElementDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.uap.ws.lang.StackTraceElementDocument
{
    
    public StackTraceElementDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName STACKTRACEELEMENT$0 = 
        new javax.xml.namespace.QName("http://ws.uap.nc/lang", "StackTraceElement");
    
    
    /**
     * Gets the "StackTraceElement" element
     */
    public java.lang.String getStackTraceElement()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STACKTRACEELEMENT$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "StackTraceElement" element
     */
    public nc.uap.ws.lang.StackTraceElement xgetStackTraceElement()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.StackTraceElement target = null;
            target = (nc.uap.ws.lang.StackTraceElement)get_store().find_element_user(STACKTRACEELEMENT$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "StackTraceElement" element
     */
    public void setStackTraceElement(java.lang.String stackTraceElement)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STACKTRACEELEMENT$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STACKTRACEELEMENT$0);
            }
            target.setStringValue(stackTraceElement);
        }
    }
    
    /**
     * Sets (as xml) the "StackTraceElement" element
     */
    public void xsetStackTraceElement(nc.uap.ws.lang.StackTraceElement stackTraceElement)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.StackTraceElement target = null;
            target = (nc.uap.ws.lang.StackTraceElement)get_store().find_element_user(STACKTRACEELEMENT$0, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.StackTraceElement)get_store().add_element_user(STACKTRACEELEMENT$0);
            }
            target.set(stackTraceElement);
        }
    }
}
