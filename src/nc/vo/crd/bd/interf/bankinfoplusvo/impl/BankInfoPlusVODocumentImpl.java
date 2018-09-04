/*
 * An XML document type.
 * Localname: BankInfoPlusVO
 * Namespace: http://interf.bd.crd.vo.nc/BankInfoPlusVO
 * Java type: nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.bd.interf.bankinfoplusvo.impl;
/**
 * A document containing one BankInfoPlusVO(@http://interf.bd.crd.vo.nc/BankInfoPlusVO) element.
 *
 * This is a complex type.
 */
public class BankInfoPlusVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVODocument
{
    
    public BankInfoPlusVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BANKINFOPLUSVO$0 = 
        new javax.xml.namespace.QName("http://interf.bd.crd.vo.nc/BankInfoPlusVO", "BankInfoPlusVO");
    
    
    /**
     * Gets the "BankInfoPlusVO" element
     */
    public nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO getBankInfoPlusVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO target = null;
            target = (nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO)get_store().find_element_user(BANKINFOPLUSVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "BankInfoPlusVO" element
     */
    public void setBankInfoPlusVO(nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO bankInfoPlusVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO target = null;
            target = (nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO)get_store().find_element_user(BANKINFOPLUSVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO)get_store().add_element_user(BANKINFOPLUSVO$0);
            }
            target.set(bankInfoPlusVO);
        }
    }
    
    /**
     * Appends and returns a new empty "BankInfoPlusVO" element
     */
    public nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO addNewBankInfoPlusVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO target = null;
            target = (nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO)get_store().add_element_user(BANKINFOPLUSVO$0);
            return target;
        }
    }
}
