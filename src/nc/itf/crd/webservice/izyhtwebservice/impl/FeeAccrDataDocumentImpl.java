/*
 * An XML document type.
 * Localname: feeAccrData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one feeAccrData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class FeeAccrDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument
{
    
    public FeeAccrDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FEEACCRDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "feeAccrData");
    
    
    /**
     * Gets the "feeAccrData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData getFeeAccrData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData)get_store().find_element_user(FEEACCRDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "feeAccrData" element
     */
    public void setFeeAccrData(nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData feeAccrData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData)get_store().find_element_user(FEEACCRDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData)get_store().add_element_user(FEEACCRDATA$0);
            }
            target.set(feeAccrData);
        }
    }
    
    /**
     * Appends and returns a new empty "feeAccrData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData addNewFeeAccrData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData)get_store().add_element_user(FEEACCRDATA$0);
            return target;
        }
    }
    /**
     * An XML feeAccrData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class FeeAccrDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData
    {
        
        public FeeAccrDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName FEEACCRBVOITEM$2 = 
            new javax.xml.namespace.QName("", "feeAccrBVOItem");
        
        
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
         * Gets array of all "feeAccrBVOItem" elements
         */
        public nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO[] getFeeAccrBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(FEEACCRBVOITEM$2, targetList);
                nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO[] result = new nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "feeAccrBVOItem" element
         */
        public nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO getFeeAccrBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO target = null;
                target = (nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO)get_store().find_element_user(FEEACCRBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "feeAccrBVOItem" element
         */
        public boolean isNilFeeAccrBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO target = null;
                target = (nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO)get_store().find_element_user(FEEACCRBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "feeAccrBVOItem" element
         */
        public int sizeOfFeeAccrBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FEEACCRBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "feeAccrBVOItem" element
         */
        public void setFeeAccrBVOItemArray(nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO[] feeAccrBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(feeAccrBVOItemArray, FEEACCRBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "feeAccrBVOItem" element
         */
        public void setFeeAccrBVOItemArray(int i, nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO feeAccrBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO target = null;
                target = (nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO)get_store().find_element_user(FEEACCRBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(feeAccrBVOItem);
            }
        }
        
        /**
         * Nils the ith "feeAccrBVOItem" element
         */
        public void setNilFeeAccrBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO target = null;
                target = (nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO)get_store().find_element_user(FEEACCRBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "feeAccrBVOItem" element
         */
        public nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO insertNewFeeAccrBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO target = null;
                target = (nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO)get_store().insert_element_user(FEEACCRBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "feeAccrBVOItem" element
         */
        public nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO addNewFeeAccrBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO target = null;
                target = (nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO)get_store().add_element_user(FEEACCRBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "feeAccrBVOItem" element
         */
        public void removeFeeAccrBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FEEACCRBVOITEM$2, i);
            }
        }
    }
}
