/*
 * An XML document type.
 * Localname: realPiiData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one realPiiData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class RealPiiDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument
{
    
    public RealPiiDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REALPIIDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "realPiiData");
    
    
    /**
     * Gets the "realPiiData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData getRealPiiData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData)get_store().find_element_user(REALPIIDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "realPiiData" element
     */
    public void setRealPiiData(nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData realPiiData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData)get_store().find_element_user(REALPIIDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData)get_store().add_element_user(REALPIIDATA$0);
            }
            target.set(realPiiData);
        }
    }
    
    /**
     * Appends and returns a new empty "realPiiData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData addNewRealPiiData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData)get_store().add_element_user(REALPIIDATA$0);
            return target;
        }
    }
    /**
     * An XML realPiiData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class RealPiiDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData
    {
        
        public RealPiiDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName REALPIBVOITEM$2 = 
            new javax.xml.namespace.QName("", "realPiBVOItem");
        
        
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
         * Gets array of all "realPiBVOItem" elements
         */
        public nc.vo.crd.acc.realpi.realpibvo.RealPiBVO[] getRealPiBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(REALPIBVOITEM$2, targetList);
                nc.vo.crd.acc.realpi.realpibvo.RealPiBVO[] result = new nc.vo.crd.acc.realpi.realpibvo.RealPiBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "realPiBVOItem" element
         */
        public nc.vo.crd.acc.realpi.realpibvo.RealPiBVO getRealPiBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpi.realpibvo.RealPiBVO target = null;
                target = (nc.vo.crd.acc.realpi.realpibvo.RealPiBVO)get_store().find_element_user(REALPIBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "realPiBVOItem" element
         */
        public boolean isNilRealPiBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpi.realpibvo.RealPiBVO target = null;
                target = (nc.vo.crd.acc.realpi.realpibvo.RealPiBVO)get_store().find_element_user(REALPIBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "realPiBVOItem" element
         */
        public int sizeOfRealPiBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(REALPIBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "realPiBVOItem" element
         */
        public void setRealPiBVOItemArray(nc.vo.crd.acc.realpi.realpibvo.RealPiBVO[] realPiBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(realPiBVOItemArray, REALPIBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "realPiBVOItem" element
         */
        public void setRealPiBVOItemArray(int i, nc.vo.crd.acc.realpi.realpibvo.RealPiBVO realPiBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpi.realpibvo.RealPiBVO target = null;
                target = (nc.vo.crd.acc.realpi.realpibvo.RealPiBVO)get_store().find_element_user(REALPIBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(realPiBVOItem);
            }
        }
        
        /**
         * Nils the ith "realPiBVOItem" element
         */
        public void setNilRealPiBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpi.realpibvo.RealPiBVO target = null;
                target = (nc.vo.crd.acc.realpi.realpibvo.RealPiBVO)get_store().find_element_user(REALPIBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "realPiBVOItem" element
         */
        public nc.vo.crd.acc.realpi.realpibvo.RealPiBVO insertNewRealPiBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpi.realpibvo.RealPiBVO target = null;
                target = (nc.vo.crd.acc.realpi.realpibvo.RealPiBVO)get_store().insert_element_user(REALPIBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "realPiBVOItem" element
         */
        public nc.vo.crd.acc.realpi.realpibvo.RealPiBVO addNewRealPiBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realpi.realpibvo.RealPiBVO target = null;
                target = (nc.vo.crd.acc.realpi.realpibvo.RealPiBVO)get_store().add_element_user(REALPIBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "realPiBVOItem" element
         */
        public void removeRealPiBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(REALPIBVOITEM$2, i);
            }
        }
    }
}
