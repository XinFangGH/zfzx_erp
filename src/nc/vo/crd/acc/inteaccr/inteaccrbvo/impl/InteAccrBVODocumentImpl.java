/*
 * An XML document type.
 * Localname: InteAccrBVO
 * Namespace: http://inteaccr.acc.crd.vo.nc/InteAccrBVO
 * Java type: nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.inteaccr.inteaccrbvo.impl;
/**
 * A document containing one InteAccrBVO(@http://inteaccr.acc.crd.vo.nc/InteAccrBVO) element.
 *
 * This is a complex type.
 */
public class InteAccrBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVODocument
{
    
    public InteAccrBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INTEACCRBVO$0 = 
        new javax.xml.namespace.QName("http://inteaccr.acc.crd.vo.nc/InteAccrBVO", "InteAccrBVO");
    
    
    /**
     * Gets the "InteAccrBVO" element
     */
    public nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO getInteAccrBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO target = null;
            target = (nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO)get_store().find_element_user(INTEACCRBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "InteAccrBVO" element
     */
    public void setInteAccrBVO(nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO inteAccrBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO target = null;
            target = (nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO)get_store().find_element_user(INTEACCRBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO)get_store().add_element_user(INTEACCRBVO$0);
            }
            target.set(inteAccrBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "InteAccrBVO" element
     */
    public nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO addNewInteAccrBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO target = null;
            target = (nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO)get_store().add_element_user(INTEACCRBVO$0);
            return target;
        }
    }
}
