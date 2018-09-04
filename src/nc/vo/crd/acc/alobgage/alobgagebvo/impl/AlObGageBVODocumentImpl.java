/*
 * An XML document type.
 * Localname: AlObGageBVO
 * Namespace: http://alobgage.acc.crd.vo.nc/AlObGageBVO
 * Java type: nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.alobgage.alobgagebvo.impl;
/**
 * A document containing one AlObGageBVO(@http://alobgage.acc.crd.vo.nc/AlObGageBVO) element.
 *
 * This is a complex type.
 */
public class AlObGageBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVODocument
{
    
    public AlObGageBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ALOBGAGEBVO$0 = 
        new javax.xml.namespace.QName("http://alobgage.acc.crd.vo.nc/AlObGageBVO", "AlObGageBVO");
    
    
    /**
     * Gets the "AlObGageBVO" element
     */
    public nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO getAlObGageBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO target = null;
            target = (nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO)get_store().find_element_user(ALOBGAGEBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "AlObGageBVO" element
     */
    public void setAlObGageBVO(nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO alObGageBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO target = null;
            target = (nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO)get_store().find_element_user(ALOBGAGEBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO)get_store().add_element_user(ALOBGAGEBVO$0);
            }
            target.set(alObGageBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "AlObGageBVO" element
     */
    public nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO addNewAlObGageBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO target = null;
            target = (nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO)get_store().add_element_user(ALOBGAGEBVO$0);
            return target;
        }
    }
}
