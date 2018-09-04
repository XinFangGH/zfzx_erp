/*
 * An XML document type.
 * Localname: feePlanData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one feePlanData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class FeePlanDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument
{
    
    public FeePlanDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FEEPLANDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "feePlanData");
    
    
    /**
     * Gets the "feePlanData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData getFeePlanData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData)get_store().find_element_user(FEEPLANDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "feePlanData" element
     */
    public void setFeePlanData(nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData feePlanData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData)get_store().find_element_user(FEEPLANDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData)get_store().add_element_user(FEEPLANDATA$0);
            }
            target.set(feePlanData);
        }
    }
    
    /**
     * Appends and returns a new empty "feePlanData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData addNewFeePlanData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData)get_store().add_element_user(FEEPLANDATA$0);
            return target;
        }
    }
    /**
     * An XML feePlanData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class FeePlanDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData
    {
        
        public FeePlanDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName FEEPLANBVOITEM$2 = 
            new javax.xml.namespace.QName("", "feePlanBVOItem");
        
        
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
         * Gets array of all "feePlanBVOItem" elements
         */
        public nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO[] getFeePlanBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(FEEPLANBVOITEM$2, targetList);
                nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO[] result = new nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "feePlanBVOItem" element
         */
        public nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO getFeePlanBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO target = null;
                target = (nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO)get_store().find_element_user(FEEPLANBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "feePlanBVOItem" element
         */
        public boolean isNilFeePlanBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO target = null;
                target = (nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO)get_store().find_element_user(FEEPLANBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "feePlanBVOItem" element
         */
        public int sizeOfFeePlanBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(FEEPLANBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "feePlanBVOItem" element
         */
        public void setFeePlanBVOItemArray(nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO[] feePlanBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(feePlanBVOItemArray, FEEPLANBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "feePlanBVOItem" element
         */
        public void setFeePlanBVOItemArray(int i, nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO feePlanBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO target = null;
                target = (nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO)get_store().find_element_user(FEEPLANBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(feePlanBVOItem);
            }
        }
        
        /**
         * Nils the ith "feePlanBVOItem" element
         */
        public void setNilFeePlanBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO target = null;
                target = (nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO)get_store().find_element_user(FEEPLANBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "feePlanBVOItem" element
         */
        public nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO insertNewFeePlanBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO target = null;
                target = (nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO)get_store().insert_element_user(FEEPLANBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "feePlanBVOItem" element
         */
        public nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO addNewFeePlanBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO target = null;
                target = (nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO)get_store().add_element_user(FEEPLANBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "feePlanBVOItem" element
         */
        public void removeFeePlanBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(FEEPLANBVOITEM$2, i);
            }
        }
    }
}
