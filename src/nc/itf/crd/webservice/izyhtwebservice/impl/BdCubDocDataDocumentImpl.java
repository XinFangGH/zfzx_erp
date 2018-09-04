/*
 * An XML document type.
 * Localname: bdCubDocData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one bdCubDocData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class BdCubDocDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument
{
    
    public BdCubDocDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName BDCUBDOCDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "bdCubDocData");
    
    
    /**
     * Gets the "bdCubDocData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData getBdCubDocData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData)get_store().find_element_user(BDCUBDOCDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "bdCubDocData" element
     */
    public void setBdCubDocData(nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData bdCubDocData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData)get_store().find_element_user(BDCUBDOCDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData)get_store().add_element_user(BDCUBDOCDATA$0);
            }
            target.set(bdCubDocData);
        }
    }
    
    /**
     * Appends and returns a new empty "bdCubDocData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData addNewBdCubDocData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData)get_store().add_element_user(BDCUBDOCDATA$0);
            return target;
        }
    }
    /**
     * An XML bdCubDocData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class BdCubDocDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData
    {
        
        public BdCubDocDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName BDCUBASDOCPLUSVOITEM$2 = 
            new javax.xml.namespace.QName("", "bdCubasdocPlusVOItem");
        private static final javax.xml.namespace.QName BANKINFOPLUSVOITEM$4 = 
            new javax.xml.namespace.QName("", "bankInfoPlusVOItem");
        
        
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
        
        /**
         * Gets array of all "bdCubasdocPlusVOItem" elements
         */
        public nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO[] getBdCubasdocPlusVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(BDCUBASDOCPLUSVOITEM$2, targetList);
                nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO[] result = new nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "bdCubasdocPlusVOItem" element
         */
        public nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO getBdCubasdocPlusVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO)get_store().find_element_user(BDCUBASDOCPLUSVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "bdCubasdocPlusVOItem" element
         */
        public boolean isNilBdCubasdocPlusVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO)get_store().find_element_user(BDCUBASDOCPLUSVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "bdCubasdocPlusVOItem" element
         */
        public int sizeOfBdCubasdocPlusVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(BDCUBASDOCPLUSVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "bdCubasdocPlusVOItem" element
         */
        public void setBdCubasdocPlusVOItemArray(nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO[] bdCubasdocPlusVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(bdCubasdocPlusVOItemArray, BDCUBASDOCPLUSVOITEM$2);
            }
        }
        
        /**
         * Sets ith "bdCubasdocPlusVOItem" element
         */
        public void setBdCubasdocPlusVOItemArray(int i, nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO bdCubasdocPlusVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO)get_store().find_element_user(BDCUBASDOCPLUSVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(bdCubasdocPlusVOItem);
            }
        }
        
        /**
         * Nils the ith "bdCubasdocPlusVOItem" element
         */
        public void setNilBdCubasdocPlusVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO)get_store().find_element_user(BDCUBASDOCPLUSVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "bdCubasdocPlusVOItem" element
         */
        public nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO insertNewBdCubasdocPlusVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO)get_store().insert_element_user(BDCUBASDOCPLUSVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "bdCubasdocPlusVOItem" element
         */
        public nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO addNewBdCubasdocPlusVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO)get_store().add_element_user(BDCUBASDOCPLUSVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "bdCubasdocPlusVOItem" element
         */
        public void removeBdCubasdocPlusVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(BDCUBASDOCPLUSVOITEM$2, i);
            }
        }
        
        /**
         * Gets array of all "bankInfoPlusVOItem" elements
         */
        public nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO[] getBankInfoPlusVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(BANKINFOPLUSVOITEM$4, targetList);
                nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO[] result = new nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "bankInfoPlusVOItem" element
         */
        public nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO getBankInfoPlusVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO)get_store().find_element_user(BANKINFOPLUSVOITEM$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "bankInfoPlusVOItem" element
         */
        public boolean isNilBankInfoPlusVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO)get_store().find_element_user(BANKINFOPLUSVOITEM$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "bankInfoPlusVOItem" element
         */
        public int sizeOfBankInfoPlusVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(BANKINFOPLUSVOITEM$4);
            }
        }
        
        /**
         * Sets array of all "bankInfoPlusVOItem" element
         */
        public void setBankInfoPlusVOItemArray(nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO[] bankInfoPlusVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(bankInfoPlusVOItemArray, BANKINFOPLUSVOITEM$4);
            }
        }
        
        /**
         * Sets ith "bankInfoPlusVOItem" element
         */
        public void setBankInfoPlusVOItemArray(int i, nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO bankInfoPlusVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO)get_store().find_element_user(BANKINFOPLUSVOITEM$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(bankInfoPlusVOItem);
            }
        }
        
        /**
         * Nils the ith "bankInfoPlusVOItem" element
         */
        public void setNilBankInfoPlusVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO)get_store().find_element_user(BANKINFOPLUSVOITEM$4, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "bankInfoPlusVOItem" element
         */
        public nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO insertNewBankInfoPlusVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO)get_store().insert_element_user(BANKINFOPLUSVOITEM$4, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "bankInfoPlusVOItem" element
         */
        public nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO addNewBankInfoPlusVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO target = null;
                target = (nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO)get_store().add_element_user(BANKINFOPLUSVOITEM$4);
                return target;
            }
        }
        
        /**
         * Removes the ith "bankInfoPlusVOItem" element
         */
        public void removeBankInfoPlusVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(BANKINFOPLUSVOITEM$4, i);
            }
        }
    }
}
