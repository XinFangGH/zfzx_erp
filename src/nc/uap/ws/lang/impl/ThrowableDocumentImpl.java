/*
 * An XML document type.
 * Localname: Throwable
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.ThrowableDocument
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang.impl;
/**
 * A document containing one Throwable(@http://ws.uap.nc/lang) element.
 *
 * This is a complex type.
 */
public class ThrowableDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.uap.ws.lang.ThrowableDocument
{
    
    public ThrowableDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName THROWABLE$0 = 
        new javax.xml.namespace.QName("http://ws.uap.nc/lang", "Throwable");
    
    
    /**
     * Gets the "Throwable" element
     */
    public nc.uap.ws.lang.Throwable getThrowable()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.Throwable target = null;
            target = (nc.uap.ws.lang.Throwable)get_store().find_element_user(THROWABLE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Throwable" element
     */
    public void setThrowable(nc.uap.ws.lang.Throwable throwable)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.Throwable target = null;
            target = (nc.uap.ws.lang.Throwable)get_store().find_element_user(THROWABLE$0, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.Throwable)get_store().add_element_user(THROWABLE$0);
            }
            target.set(throwable);
        }
    }
    
    /**
     * Appends and returns a new empty "Throwable" element
     */
    public nc.uap.ws.lang.Throwable addNewThrowable()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.Throwable target = null;
            target = (nc.uap.ws.lang.Throwable)get_store().add_element_user(THROWABLE$0);
            return target;
        }
    }
}
