/*
 * An XML document type.
 * Localname: ZyhtVO
 * Namespace: http://interf.bd.crd.vo.nc/ZyhtVO
 * Java type: nc.vo.crd.bd.interf.zyhtvo.ZyhtVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.bd.interf.zyhtvo.impl;
/**
 * A document containing one ZyhtVO(@http://interf.bd.crd.vo.nc/ZyhtVO) element.
 *
 * This is a complex type.
 */
public class ZyhtVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.bd.interf.zyhtvo.ZyhtVODocument
{
    
    public ZyhtVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ZYHTVO$0 = 
        new javax.xml.namespace.QName("http://interf.bd.crd.vo.nc/ZyhtVO", "ZyhtVO");
    
    
    /**
     * Gets the "ZyhtVO" element
     */
    public nc.vo.crd.bd.interf.zyhtvo.ZyhtVO getZyhtVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.zyhtvo.ZyhtVO target = null;
            target = (nc.vo.crd.bd.interf.zyhtvo.ZyhtVO)get_store().find_element_user(ZYHTVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "ZyhtVO" element
     */
    public void setZyhtVO(nc.vo.crd.bd.interf.zyhtvo.ZyhtVO zyhtVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.zyhtvo.ZyhtVO target = null;
            target = (nc.vo.crd.bd.interf.zyhtvo.ZyhtVO)get_store().find_element_user(ZYHTVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.bd.interf.zyhtvo.ZyhtVO)get_store().add_element_user(ZYHTVO$0);
            }
            target.set(zyhtVO);
        }
    }
    
    /**
     * Appends and returns a new empty "ZyhtVO" element
     */
    public nc.vo.crd.bd.interf.zyhtvo.ZyhtVO addNewZyhtVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.zyhtvo.ZyhtVO target = null;
            target = (nc.vo.crd.bd.interf.zyhtvo.ZyhtVO)get_store().add_element_user(ZYHTVO$0);
            return target;
        }
    }
}
