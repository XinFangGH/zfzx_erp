/*
 * An XML document type.
 * Localname: accGrantData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one accGrantData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class AccGrantDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument
{
    
    public AccGrantDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ACCGRANTDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "accGrantData");
    
    
    /**
     * Gets the "accGrantData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData getAccGrantData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData)get_store().find_element_user(ACCGRANTDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "accGrantData" element
     */
    public void setAccGrantData(nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData accGrantData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData)get_store().find_element_user(ACCGRANTDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData)get_store().add_element_user(ACCGRANTDATA$0);
            }
            target.set(accGrantData);
        }
    }
    
    /**
     * Appends and returns a new empty "accGrantData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData addNewAccGrantData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData)get_store().add_element_user(ACCGRANTDATA$0);
            return target;
        }
    }
    /**
     * An XML accGrantData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class AccGrantDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData
    {
        
        public AccGrantDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName GRANTBVOITEM$2 = 
            new javax.xml.namespace.QName("", "grantBVOItem");
        
        
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
         * Gets array of all "grantBVOItem" elements
         */
        public nc.vo.crd.acc.grant.grantbvo.GrantBVO[] getGrantBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(GRANTBVOITEM$2, targetList);
                nc.vo.crd.acc.grant.grantbvo.GrantBVO[] result = new nc.vo.crd.acc.grant.grantbvo.GrantBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "grantBVOItem" element
         */
        public nc.vo.crd.acc.grant.grantbvo.GrantBVO getGrantBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.grant.grantbvo.GrantBVO target = null;
                target = (nc.vo.crd.acc.grant.grantbvo.GrantBVO)get_store().find_element_user(GRANTBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "grantBVOItem" element
         */
        public boolean isNilGrantBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.grant.grantbvo.GrantBVO target = null;
                target = (nc.vo.crd.acc.grant.grantbvo.GrantBVO)get_store().find_element_user(GRANTBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "grantBVOItem" element
         */
        public int sizeOfGrantBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(GRANTBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "grantBVOItem" element
         */
        public void setGrantBVOItemArray(nc.vo.crd.acc.grant.grantbvo.GrantBVO[] grantBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(grantBVOItemArray, GRANTBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "grantBVOItem" element
         */
        public void setGrantBVOItemArray(int i, nc.vo.crd.acc.grant.grantbvo.GrantBVO grantBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.grant.grantbvo.GrantBVO target = null;
                target = (nc.vo.crd.acc.grant.grantbvo.GrantBVO)get_store().find_element_user(GRANTBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(grantBVOItem);
            }
        }
        
        /**
         * Nils the ith "grantBVOItem" element
         */
        public void setNilGrantBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.grant.grantbvo.GrantBVO target = null;
                target = (nc.vo.crd.acc.grant.grantbvo.GrantBVO)get_store().find_element_user(GRANTBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "grantBVOItem" element
         */
        public nc.vo.crd.acc.grant.grantbvo.GrantBVO insertNewGrantBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.grant.grantbvo.GrantBVO target = null;
                target = (nc.vo.crd.acc.grant.grantbvo.GrantBVO)get_store().insert_element_user(GRANTBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "grantBVOItem" element
         */
        public nc.vo.crd.acc.grant.grantbvo.GrantBVO addNewGrantBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.grant.grantbvo.GrantBVO target = null;
                target = (nc.vo.crd.acc.grant.grantbvo.GrantBVO)get_store().add_element_user(GRANTBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "grantBVOItem" element
         */
        public void removeGrantBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(GRANTBVOITEM$2, i);
            }
        }
    }
}
