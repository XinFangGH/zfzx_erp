/*
 * An XML document type.
 * Localname: NullPointerException
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.NullPointerExceptionDocument
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang.impl;
/**
 * A document containing one NullPointerException(@http://ws.uap.nc/lang) element.
 *
 * This is a complex type.
 */
public class NullPointerExceptionDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.uap.ws.lang.NullPointerExceptionDocument
{
    
    public NullPointerExceptionDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName NULLPOINTEREXCEPTION$0 = 
        new javax.xml.namespace.QName("http://ws.uap.nc/lang", "NullPointerException");
    
    
    /**
     * Gets the "NullPointerException" element
     */
    public nc.uap.ws.lang.NullPointerException getNullPointerException()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.NullPointerException target = null;
            target = (nc.uap.ws.lang.NullPointerException)get_store().find_element_user(NULLPOINTEREXCEPTION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "NullPointerException" element
     */
    public void setNullPointerException(nc.uap.ws.lang.NullPointerException nullPointerException)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.NullPointerException target = null;
            target = (nc.uap.ws.lang.NullPointerException)get_store().find_element_user(NULLPOINTEREXCEPTION$0, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.NullPointerException)get_store().add_element_user(NULLPOINTEREXCEPTION$0);
            }
            target.set(nullPointerException);
        }
    }
    
    /**
     * Appends and returns a new empty "NullPointerException" element
     */
    public nc.uap.ws.lang.NullPointerException addNewNullPointerException()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.NullPointerException target = null;
            target = (nc.uap.ws.lang.NullPointerException)get_store().add_element_user(NULLPOINTEREXCEPTION$0);
            return target;
        }
    }
}
