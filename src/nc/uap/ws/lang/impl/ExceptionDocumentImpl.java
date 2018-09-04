/*
 * An XML document type.
 * Localname: Exception
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.ExceptionDocument
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang.impl;
/**
 * A document containing one Exception(@http://ws.uap.nc/lang) element.
 *
 * This is a complex type.
 */
public class ExceptionDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.uap.ws.lang.ExceptionDocument
{
    
    public ExceptionDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName EXCEPTION$0 = 
        new javax.xml.namespace.QName("http://ws.uap.nc/lang", "Exception");
    
    
    /**
     * Gets the "Exception" element
     */
    public nc.uap.ws.lang.Exception getException()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.Exception target = null;
            target = (nc.uap.ws.lang.Exception)get_store().find_element_user(EXCEPTION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Exception" element
     */
    public void setException(nc.uap.ws.lang.Exception exception)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.Exception target = null;
            target = (nc.uap.ws.lang.Exception)get_store().find_element_user(EXCEPTION$0, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.Exception)get_store().add_element_user(EXCEPTION$0);
            }
            target.set(exception);
        }
    }
    
    /**
     * Appends and returns a new empty "Exception" element
     */
    public nc.uap.ws.lang.Exception addNewException()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.Exception target = null;
            target = (nc.uap.ws.lang.Exception)get_store().add_element_user(EXCEPTION$0);
            return target;
        }
    }
}
