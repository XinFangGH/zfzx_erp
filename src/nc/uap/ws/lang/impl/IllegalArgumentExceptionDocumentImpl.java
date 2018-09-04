/*
 * An XML document type.
 * Localname: IllegalArgumentException
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.IllegalArgumentExceptionDocument
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang.impl;
/**
 * A document containing one IllegalArgumentException(@http://ws.uap.nc/lang) element.
 *
 * This is a complex type.
 */
public class IllegalArgumentExceptionDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.uap.ws.lang.IllegalArgumentExceptionDocument
{
    
    public IllegalArgumentExceptionDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ILLEGALARGUMENTEXCEPTION$0 = 
        new javax.xml.namespace.QName("http://ws.uap.nc/lang", "IllegalArgumentException");
    
    
    /**
     * Gets the "IllegalArgumentException" element
     */
    public nc.uap.ws.lang.IllegalArgumentException getIllegalArgumentException()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.IllegalArgumentException target = null;
            target = (nc.uap.ws.lang.IllegalArgumentException)get_store().find_element_user(ILLEGALARGUMENTEXCEPTION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "IllegalArgumentException" element
     */
    public void setIllegalArgumentException(nc.uap.ws.lang.IllegalArgumentException illegalArgumentException)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.IllegalArgumentException target = null;
            target = (nc.uap.ws.lang.IllegalArgumentException)get_store().find_element_user(ILLEGALARGUMENTEXCEPTION$0, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.IllegalArgumentException)get_store().add_element_user(ILLEGALARGUMENTEXCEPTION$0);
            }
            target.set(illegalArgumentException);
        }
    }
    
    /**
     * Appends and returns a new empty "IllegalArgumentException" element
     */
    public nc.uap.ws.lang.IllegalArgumentException addNewIllegalArgumentException()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.IllegalArgumentException target = null;
            target = (nc.uap.ws.lang.IllegalArgumentException)get_store().add_element_user(ILLEGALARGUMENTEXCEPTION$0);
            return target;
        }
    }
}
