/*
 * An XML document type.
 * Localname: RuntimeException
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.RuntimeExceptionDocument
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang.impl;
/**
 * A document containing one RuntimeException(@http://ws.uap.nc/lang) element.
 *
 * This is a complex type.
 */
public class RuntimeExceptionDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.uap.ws.lang.RuntimeExceptionDocument
{
    
    public RuntimeExceptionDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName RUNTIMEEXCEPTION$0 = 
        new javax.xml.namespace.QName("http://ws.uap.nc/lang", "RuntimeException");
    
    
    /**
     * Gets the "RuntimeException" element
     */
    public nc.uap.ws.lang.RuntimeException getRuntimeException()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.RuntimeException target = null;
            target = (nc.uap.ws.lang.RuntimeException)get_store().find_element_user(RUNTIMEEXCEPTION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "RuntimeException" element
     */
    public void setRuntimeException(nc.uap.ws.lang.RuntimeException runtimeException)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.RuntimeException target = null;
            target = (nc.uap.ws.lang.RuntimeException)get_store().find_element_user(RUNTIMEEXCEPTION$0, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.RuntimeException)get_store().add_element_user(RUNTIMEEXCEPTION$0);
            }
            target.set(runtimeException);
        }
    }
    
    /**
     * Appends and returns a new empty "RuntimeException" element
     */
    public nc.uap.ws.lang.RuntimeException addNewRuntimeException()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.RuntimeException target = null;
            target = (nc.uap.ws.lang.RuntimeException)get_store().add_element_user(RUNTIMEEXCEPTION$0);
            return target;
        }
    }
}
