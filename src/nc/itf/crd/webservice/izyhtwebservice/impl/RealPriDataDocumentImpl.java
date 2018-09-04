/*
 * An XML document type.
 * Localname: realPriData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one realPriData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class RealPriDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument
{
    
    public RealPriDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REALPRIDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "realPriData");
    
    
    /**
     * Gets the "realPriData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData getRealPriData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData)get_store().find_element_user(REALPRIDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "realPriData" element
     */
    public void setRealPriData(nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData realPriData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData)get_store().find_element_user(REALPRIDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData)get_store().add_element_user(REALPRIDATA$0);
            }
            target.set(realPriData);
        }
    }
    
    /**
     * Appends and returns a new empty "realPriData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData addNewRealPriData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData)get_store().add_element_user(REALPRIDATA$0);
            return target;
        }
    }
    /**
     * An XML realPriData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class RealPriDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData
    {
        
        public RealPriDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName REALPRIBVOITEM$2 = 
            new javax.xml.namespace.QName("", "realPriBVOItem");
        
        
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
         * Gets array of all "realPriBVOItem" elements
         */
        public nc.vo.crd.acc.realpri.realpribvo.RealPriBVO[] getRealPriBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(REALPRIBVOITEM$2, targetList);
                nc.vo.crd.acc.realpri.realpribvo.RealPriBVO[] result = new nc.vo.crd.acc.realpri.realpribvo.RealPriBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "realPriBVOItem" element
         */
        public nc.vo.crd.acc.realpri.realpribvo.RealPriBVO getRealPriBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpri.realpribvo.RealPriBVO target = null;
                target = (nc.vo.crd.acc.realpri.realpribvo.RealPriBVO)get_store().find_element_user(REALPRIBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "realPriBVOItem" element
         */
        public boolean isNilRealPriBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpri.realpribvo.RealPriBVO target = null;
                target = (nc.vo.crd.acc.realpri.realpribvo.RealPriBVO)get_store().find_element_user(REALPRIBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "realPriBVOItem" element
         */
        public int sizeOfRealPriBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(REALPRIBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "realPriBVOItem" element
         */
        public void setRealPriBVOItemArray(nc.vo.crd.acc.realpri.realpribvo.RealPriBVO[] realPriBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(realPriBVOItemArray, REALPRIBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "realPriBVOItem" element
         */
        public void setRealPriBVOItemArray(int i, nc.vo.crd.acc.realpri.realpribvo.RealPriBVO realPriBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpri.realpribvo.RealPriBVO target = null;
                target = (nc.vo.crd.acc.realpri.realpribvo.RealPriBVO)get_store().find_element_user(REALPRIBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(realPriBVOItem);
            }
        }
        
        /**
         * Nils the ith "realPriBVOItem" element
         */
        public void setNilRealPriBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpri.realpribvo.RealPriBVO target = null;
                target = (nc.vo.crd.acc.realpri.realpribvo.RealPriBVO)get_store().find_element_user(REALPRIBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "realPriBVOItem" element
         */
        public nc.vo.crd.acc.realpri.realpribvo.RealPriBVO insertNewRealPriBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpri.realpribvo.RealPriBVO target = null;
                target = (nc.vo.crd.acc.realpri.realpribvo.RealPriBVO)get_store().insert_element_user(REALPRIBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "realPriBVOItem" element
         */
        public nc.vo.crd.acc.realpri.realpribvo.RealPriBVO addNewRealPriBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpri.realpribvo.RealPriBVO target = null;
                target = (nc.vo.crd.acc.realpri.realpribvo.RealPriBVO)get_store().add_element_user(REALPRIBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "realPriBVOItem" element
         */
        public void removeRealPriBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(REALPRIBVOITEM$2, i);
            }
        }
    }
}
