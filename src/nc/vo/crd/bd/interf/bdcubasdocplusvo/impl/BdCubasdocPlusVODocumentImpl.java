/*
 * An XML document type.
 * Localname: BdCubasdocPlusVO
 * Namespace: http://interf.bd.crd.vo.nc/BdCubasdocPlusVO
 * Java type: nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.bd.interf.bdcubasdocplusvo.impl;
/**
 * A document containing one BdCubasdocPlusVO(@http://interf.bd.crd.vo.nc/BdCubasdocPlusVO) element.
 *
 * This is a complex type.
 */
public class BdCubasdocPlusVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVODocument
{
    
    public BdCubasdocPlusVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BDCUBASDOCPLUSVO$0 = 
        new javax.xml.namespace.QName("http://interf.bd.crd.vo.nc/BdCubasdocPlusVO", "BdCubasdocPlusVO");
    
    
    /**
     * Gets the "BdCubasdocPlusVO" element
     */
    public nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO getBdCubasdocPlusVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO target = null;
            target = (nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO)get_store().find_element_user(BDCUBASDOCPLUSVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "BdCubasdocPlusVO" element
     */
    public void setBdCubasdocPlusVO(nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO bdCubasdocPlusVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO target = null;
            target = (nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO)get_store().find_element_user(BDCUBASDOCPLUSVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO)get_store().add_element_user(BDCUBASDOCPLUSVO$0);
            }
            target.set(bdCubasdocPlusVO);
        }
    }
    
    /**
     * Appends and returns a new empty "BdCubasdocPlusVO" element
     */
    public nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO addNewBdCubasdocPlusVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO target = null;
            target = (nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO)get_store().add_element_user(BDCUBASDOCPLUSVO$0);
            return target;
        }
    }
}
