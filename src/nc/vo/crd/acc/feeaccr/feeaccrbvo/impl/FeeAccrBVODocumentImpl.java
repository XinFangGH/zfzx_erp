/*
 * An XML document type.
 * Localname: FeeAccrBVO
 * Namespace: http://feeaccr.acc.crd.vo.nc/FeeAccrBVO
 * Java type: nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.feeaccr.feeaccrbvo.impl;
/**
 * A document containing one FeeAccrBVO(@http://feeaccr.acc.crd.vo.nc/FeeAccrBVO) element.
 *
 * This is a complex type.
 */
public class FeeAccrBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVODocument
{
    
    public FeeAccrBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FEEACCRBVO$0 = 
        new javax.xml.namespace.QName("http://feeaccr.acc.crd.vo.nc/FeeAccrBVO", "FeeAccrBVO");
    
    
    /**
     * Gets the "FeeAccrBVO" element
     */
    public nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO getFeeAccrBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO target = null;
            target = (nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO)get_store().find_element_user(FEEACCRBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "FeeAccrBVO" element
     */
    public void setFeeAccrBVO(nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO feeAccrBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO target = null;
            target = (nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO)get_store().find_element_user(FEEACCRBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO)get_store().add_element_user(FEEACCRBVO$0);
            }
            target.set(feeAccrBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "FeeAccrBVO" element
     */
    public nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO addNewFeeAccrBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO target = null;
            target = (nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO)get_store().add_element_user(FEEACCRBVO$0);
            return target;
        }
    }
}
