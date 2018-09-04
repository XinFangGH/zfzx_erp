/*
 * An XML document type.
 * Localname: EbankDzdPlusVO
 * Namespace: http://interf.bd.crd.vo.nc/EbankDzdPlusVO
 * Java type: nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.bd.interf.ebankdzdplusvo.impl;
/**
 * A document containing one EbankDzdPlusVO(@http://interf.bd.crd.vo.nc/EbankDzdPlusVO) element.
 *
 * This is a complex type.
 */
public class EbankDzdPlusVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVODocument
{
    
    public EbankDzdPlusVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName EBANKDZDPLUSVO$0 = 
        new javax.xml.namespace.QName("http://interf.bd.crd.vo.nc/EbankDzdPlusVO", "EbankDzdPlusVO");
    
    
    /**
     * Gets the "EbankDzdPlusVO" element
     */
    public nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO getEbankDzdPlusVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO target = null;
            target = (nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO)get_store().find_element_user(EBANKDZDPLUSVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "EbankDzdPlusVO" element
     */
    public void setEbankDzdPlusVO(nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO ebankDzdPlusVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO target = null;
            target = (nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO)get_store().find_element_user(EBANKDZDPLUSVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO)get_store().add_element_user(EBANKDZDPLUSVO$0);
            }
            target.set(ebankDzdPlusVO);
        }
    }
    
    /**
     * Appends and returns a new empty "EbankDzdPlusVO" element
     */
    public nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO addNewEbankDzdPlusVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO target = null;
            target = (nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO)get_store().add_element_user(EBANKDZDPLUSVO$0);
            return target;
        }
    }
}
