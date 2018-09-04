/*
 * An XML document type.
 * Localname: IntePlanBVO
 * Namespace: http://inteplan.acc.crd.vo.nc/IntePlanBVO
 * Java type: nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.inteplan.inteplanbvo.impl;
/**
 * A document containing one IntePlanBVO(@http://inteplan.acc.crd.vo.nc/IntePlanBVO) element.
 *
 * This is a complex type.
 */
public class IntePlanBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVODocument
{
    
    public IntePlanBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INTEPLANBVO$0 = 
        new javax.xml.namespace.QName("http://inteplan.acc.crd.vo.nc/IntePlanBVO", "IntePlanBVO");
    
    
    /**
     * Gets the "IntePlanBVO" element
     */
    public nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO getIntePlanBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO target = null;
            target = (nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO)get_store().find_element_user(INTEPLANBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "IntePlanBVO" element
     */
    public void setIntePlanBVO(nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO intePlanBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO target = null;
            target = (nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO)get_store().find_element_user(INTEPLANBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO)get_store().add_element_user(INTEPLANBVO$0);
            }
            target.set(intePlanBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "IntePlanBVO" element
     */
    public nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO addNewIntePlanBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO target = null;
            target = (nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO)get_store().add_element_user(INTEPLANBVO$0);
            return target;
        }
    }
}
