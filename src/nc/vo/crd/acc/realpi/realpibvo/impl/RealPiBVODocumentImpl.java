/*
 * An XML document type.
 * Localname: RealPiBVO
 * Namespace: http://realpi.acc.crd.vo.nc/RealPiBVO
 * Java type: nc.vo.crd.acc.realpi.realpibvo.RealPiBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.realpi.realpibvo.impl;
/**
 * A document containing one RealPiBVO(@http://realpi.acc.crd.vo.nc/RealPiBVO) element.
 *
 * This is a complex type.
 */
public class RealPiBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.realpi.realpibvo.RealPiBVODocument
{
    
    public RealPiBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REALPIBVO$0 = 
        new javax.xml.namespace.QName("http://realpi.acc.crd.vo.nc/RealPiBVO", "RealPiBVO");
    
    
    /**
     * Gets the "RealPiBVO" element
     */
    public nc.vo.crd.acc.realpi.realpibvo.RealPiBVO getRealPiBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realpi.realpibvo.RealPiBVO target = null;
            target = (nc.vo.crd.acc.realpi.realpibvo.RealPiBVO)get_store().find_element_user(REALPIBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "RealPiBVO" element
     */
    public void setRealPiBVO(nc.vo.crd.acc.realpi.realpibvo.RealPiBVO realPiBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realpi.realpibvo.RealPiBVO target = null;
            target = (nc.vo.crd.acc.realpi.realpibvo.RealPiBVO)get_store().find_element_user(REALPIBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.realpi.realpibvo.RealPiBVO)get_store().add_element_user(REALPIBVO$0);
            }
            target.set(realPiBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "RealPiBVO" element
     */
    public nc.vo.crd.acc.realpi.realpibvo.RealPiBVO addNewRealPiBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realpi.realpibvo.RealPiBVO target = null;
            target = (nc.vo.crd.acc.realpi.realpibvo.RealPiBVO)get_store().add_element_user(REALPIBVO$0);
            return target;
        }
    }
}
