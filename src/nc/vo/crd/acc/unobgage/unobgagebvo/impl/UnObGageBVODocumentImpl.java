/*
 * An XML document type.
 * Localname: UnObGageBVO
 * Namespace: http://unobgage.acc.crd.vo.nc/UnObGageBVO
 * Java type: nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.unobgage.unobgagebvo.impl;
/**
 * A document containing one UnObGageBVO(@http://unobgage.acc.crd.vo.nc/UnObGageBVO) element.
 *
 * This is a complex type.
 */
public class UnObGageBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVODocument
{
    
    public UnObGageBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UNOBGAGEBVO$0 = 
        new javax.xml.namespace.QName("http://unobgage.acc.crd.vo.nc/UnObGageBVO", "UnObGageBVO");
    
    
    /**
     * Gets the "UnObGageBVO" element
     */
    public nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO getUnObGageBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO target = null;
            target = (nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO)get_store().find_element_user(UNOBGAGEBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "UnObGageBVO" element
     */
    public void setUnObGageBVO(nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO unObGageBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO target = null;
            target = (nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO)get_store().find_element_user(UNOBGAGEBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO)get_store().add_element_user(UNOBGAGEBVO$0);
            }
            target.set(unObGageBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "UnObGageBVO" element
     */
    public nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO addNewUnObGageBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO target = null;
            target = (nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO)get_store().add_element_user(UNOBGAGEBVO$0);
            return target;
        }
    }
}
