/*
 * An XML document type.
 * Localname: RealPriBVO
 * Namespace: http://realpri.acc.crd.vo.nc/RealPriBVO
 * Java type: nc.vo.crd.acc.realpri.realpribvo.RealPriBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.realpri.realpribvo.impl;
/**
 * A document containing one RealPriBVO(@http://realpri.acc.crd.vo.nc/RealPriBVO) element.
 *
 * This is a complex type.
 */
public class RealPriBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.realpri.realpribvo.RealPriBVODocument
{
    
    public RealPriBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REALPRIBVO$0 = 
        new javax.xml.namespace.QName("http://realpri.acc.crd.vo.nc/RealPriBVO", "RealPriBVO");
    
    
    /**
     * Gets the "RealPriBVO" element
     */
    public nc.vo.crd.acc.realpri.realpribvo.RealPriBVO getRealPriBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realpri.realpribvo.RealPriBVO target = null;
            target = (nc.vo.crd.acc.realpri.realpribvo.RealPriBVO)get_store().find_element_user(REALPRIBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "RealPriBVO" element
     */
    public void setRealPriBVO(nc.vo.crd.acc.realpri.realpribvo.RealPriBVO realPriBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realpri.realpribvo.RealPriBVO target = null;
            target = (nc.vo.crd.acc.realpri.realpribvo.RealPriBVO)get_store().find_element_user(REALPRIBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.realpri.realpribvo.RealPriBVO)get_store().add_element_user(REALPRIBVO$0);
            }
            target.set(realPriBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "RealPriBVO" element
     */
    public nc.vo.crd.acc.realpri.realpribvo.RealPriBVO addNewRealPriBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realpri.realpribvo.RealPriBVO target = null;
            target = (nc.vo.crd.acc.realpri.realpribvo.RealPriBVO)get_store().add_element_user(REALPRIBVO$0);
            return target;
        }
    }
}
