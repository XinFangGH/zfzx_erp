/*
 * An XML document type.
 * Localname: bankDzdData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one bankDzdData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class BankDzdDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument
{
    
    public BankDzdDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BANKDZDDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "bankDzdData");
    
    
    /**
     * Gets the "bankDzdData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData getBankDzdData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData)get_store().find_element_user(BANKDZDDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "bankDzdData" element
     */
    public void setBankDzdData(nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData bankDzdData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData)get_store().find_element_user(BANKDZDDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData)get_store().add_element_user(BANKDZDDATA$0);
            }
            target.set(bankDzdData);
        }
    }
    
    /**
     * Appends and returns a new empty "bankDzdData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData addNewBankDzdData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData)get_store().add_element_user(BANKDZDDATA$0);
            return target;
        }
    }
    /**
     * An XML bankDzdData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class BankDzdDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.BankDzdDataDocument.BankDzdData
    {
        
        public BankDzdDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        
        
        /**
         * Gets the "zyhtVO" element
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
         * Tests for nil "zyhtVO" element
         */
        public boolean isNilZyhtVO()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.zyhtvo.ZyhtVO target = null;
                target = (nc.vo.crd.bd.interf.zyhtvo.ZyhtVO)get_store().find_element_user(ZYHTVO$0, 0);
                if (target == null) return false;
                return target.isNil();
            }
        }
        
        /**
         * True if has "zyhtVO" element
         */
        public boolean isSetZyhtVO()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(ZYHTVO$0) != 0;
            }
        }
        
        /**
         * Sets the "zyhtVO" element
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
         * Appends and returns a new empty "zyhtVO" element
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
        
        /**
         * Nils the "zyhtVO" element
         */
        public void setNilZyhtVO()
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
                target.setNil();
            }
        }
        
        /**
         * Unsets the "zyhtVO" element
         */
        public void unsetZyhtVO()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(ZYHTVO$0, 0);
            }
        }
    }
}
