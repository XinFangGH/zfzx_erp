/*
 * An XML document type.
 * Localname: bankDzdDataResponse
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one bankDzdDataResponse(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class BankDzdDataResponseDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument
{
    
    public BankDzdDataResponseDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BANKDZDDATARESPONSE$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "bankDzdDataResponse");
    
    
    /**
     * Gets the "bankDzdDataResponse" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse getBankDzdDataResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse)get_store().find_element_user(BANKDZDDATARESPONSE$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "bankDzdDataResponse" element
     */
    public void setBankDzdDataResponse(nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse bankDzdDataResponse)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse)get_store().find_element_user(BANKDZDDATARESPONSE$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse)get_store().add_element_user(BANKDZDDATARESPONSE$0);
            }
            target.set(bankDzdDataResponse);
        }
    }
    
    /**
     * Appends and returns a new empty "bankDzdDataResponse" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse addNewBankDzdDataResponse()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse)get_store().add_element_user(BANKDZDDATARESPONSE$0);
            return target;
        }
    }
    /**
     * An XML bankDzdDataResponse(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class BankDzdDataResponseImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.BankDzdDataResponseDocument.BankDzdDataResponse
    {
        
        public BankDzdDataResponseImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RETURN$0 = 
            new javax.xml.namespace.QName("", "return");
        
        
        /**
         * Gets array of all "return" elements
         */
        public nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO[] getReturnArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(RETURN$0, targetList);
                nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO[] result = new nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "return" element
         */
        public nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO getReturnArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO target = null;
                target = (nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "return" element
         */
        public boolean isNilReturnArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO target = null;
                target = (nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "return" element
         */
        public int sizeOfReturnArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(RETURN$0);
            }
        }
        
        /**
         * Sets array of all "return" element
         */
        public void setReturnArray(nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO[] xreturnArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(xreturnArray, RETURN$0);
            }
        }
        
        /**
         * Sets ith "return" element
         */
        public void setReturnArray(int i, nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO xreturn)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO target = null;
                target = (nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(xreturn);
            }
        }
        
        /**
         * Nils the ith "return" element
         */
        public void setNilReturnArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO target = null;
                target = (nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO)get_store().find_element_user(RETURN$0, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "return" element
         */
        public nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO insertNewReturn(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO target = null;
                target = (nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO)get_store().insert_element_user(RETURN$0, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "return" element
         */
        public nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO addNewReturn()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO target = null;
                target = (nc.vo.crd.bd.interf.ebankdzdplusvo.EbankDzdPlusVO)get_store().add_element_user(RETURN$0);
                return target;
            }
        }
        
        /**
         * Removes the ith "return" element
         */
        public void removeReturn(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(RETURN$0, i);
            }
        }
    }
}
