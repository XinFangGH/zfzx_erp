/*
 * An XML document type.
 * Localname: OverDueBVO
 * Namespace: http://overdue.acc.crd.vo.nc/OverDueBVO
 * Java type: nc.vo.crd.acc.overdue.overduebvo.OverDueBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.overdue.overduebvo.impl;
/**
 * A document containing one OverDueBVO(@http://overdue.acc.crd.vo.nc/OverDueBVO) element.
 *
 * This is a complex type.
 */
public class OverDueBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.overdue.overduebvo.OverDueBVODocument
{
    
    public OverDueBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OVERDUEBVO$0 = 
        new javax.xml.namespace.QName("http://overdue.acc.crd.vo.nc/OverDueBVO", "OverDueBVO");
    
    
    /**
     * Gets the "OverDueBVO" element
     */
    public nc.vo.crd.acc.overdue.overduebvo.OverDueBVO getOverDueBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.overdue.overduebvo.OverDueBVO target = null;
            target = (nc.vo.crd.acc.overdue.overduebvo.OverDueBVO)get_store().find_element_user(OVERDUEBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "OverDueBVO" element
     */
    public void setOverDueBVO(nc.vo.crd.acc.overdue.overduebvo.OverDueBVO overDueBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.overdue.overduebvo.OverDueBVO target = null;
            target = (nc.vo.crd.acc.overdue.overduebvo.OverDueBVO)get_store().find_element_user(OVERDUEBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.overdue.overduebvo.OverDueBVO)get_store().add_element_user(OVERDUEBVO$0);
            }
            target.set(overDueBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "OverDueBVO" element
     */
    public nc.vo.crd.acc.overdue.overduebvo.OverDueBVO addNewOverDueBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.overdue.overduebvo.OverDueBVO target = null;
            target = (nc.vo.crd.acc.overdue.overduebvo.OverDueBVO)get_store().add_element_user(OVERDUEBVO$0);
            return target;
        }
    }
}
