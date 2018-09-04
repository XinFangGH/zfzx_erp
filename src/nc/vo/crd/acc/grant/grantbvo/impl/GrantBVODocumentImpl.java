/*
 * An XML document type.
 * Localname: GrantBVO
 * Namespace: http://grant.acc.crd.vo.nc/GrantBVO
 * Java type: nc.vo.crd.acc.grant.grantbvo.GrantBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.grant.grantbvo.impl;
/**
 * A document containing one GrantBVO(@http://grant.acc.crd.vo.nc/GrantBVO) element.
 *
 * This is a complex type.
 */
public class GrantBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.grant.grantbvo.GrantBVODocument
{
    
    public GrantBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName GRANTBVO$0 = 
        new javax.xml.namespace.QName("http://grant.acc.crd.vo.nc/GrantBVO", "GrantBVO");
    
    
    /**
     * Gets the "GrantBVO" element
     */
    public nc.vo.crd.acc.grant.grantbvo.GrantBVO getGrantBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.grant.grantbvo.GrantBVO target = null;
            target = (nc.vo.crd.acc.grant.grantbvo.GrantBVO)get_store().find_element_user(GRANTBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "GrantBVO" element
     */
    public void setGrantBVO(nc.vo.crd.acc.grant.grantbvo.GrantBVO grantBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.grant.grantbvo.GrantBVO target = null;
            target = (nc.vo.crd.acc.grant.grantbvo.GrantBVO)get_store().find_element_user(GRANTBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.grant.grantbvo.GrantBVO)get_store().add_element_user(GRANTBVO$0);
            }
            target.set(grantBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "GrantBVO" element
     */
    public nc.vo.crd.acc.grant.grantbvo.GrantBVO addNewGrantBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.grant.grantbvo.GrantBVO target = null;
            target = (nc.vo.crd.acc.grant.grantbvo.GrantBVO)get_store().add_element_user(GRANTBVO$0);
            return target;
        }
    }
}
