/*
 * An XML document type.
 * Localname: FeePlanBVO
 * Namespace: http://feeplan.acc.crd.vo.nc/FeePlanBVO
 * Java type: nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.feeplan.feeplanbvo.impl;
/**
 * A document containing one FeePlanBVO(@http://feeplan.acc.crd.vo.nc/FeePlanBVO) element.
 *
 * This is a complex type.
 */
public class FeePlanBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVODocument
{
    
    public FeePlanBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FEEPLANBVO$0 = 
        new javax.xml.namespace.QName("http://feeplan.acc.crd.vo.nc/FeePlanBVO", "FeePlanBVO");
    
    
    /**
     * Gets the "FeePlanBVO" element
     */
    public nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO getFeePlanBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO target = null;
            target = (nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO)get_store().find_element_user(FEEPLANBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "FeePlanBVO" element
     */
    public void setFeePlanBVO(nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO feePlanBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO target = null;
            target = (nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO)get_store().find_element_user(FEEPLANBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO)get_store().add_element_user(FEEPLANBVO$0);
            }
            target.set(feePlanBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "FeePlanBVO" element
     */
    public nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO addNewFeePlanBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO target = null;
            target = (nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO)get_store().add_element_user(FEEPLANBVO$0);
            return target;
        }
    }
}
