/*
 * An XML document type.
 * Localname: unObGageBVOData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one unObGageBVOData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class UnObGageBVODataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument
{
    
    public UnObGageBVODataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName UNOBGAGEBVODATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "unObGageBVOData");
    
    
    /**
     * Gets the "unObGageBVOData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData getUnObGageBVOData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData)get_store().find_element_user(UNOBGAGEBVODATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "unObGageBVOData" element
     */
    public void setUnObGageBVOData(nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData unObGageBVOData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData)get_store().find_element_user(UNOBGAGEBVODATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData)get_store().add_element_user(UNOBGAGEBVODATA$0);
            }
            target.set(unObGageBVOData);
        }
    }
    
    /**
     * Appends and returns a new empty "unObGageBVOData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData addNewUnObGageBVOData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData)get_store().add_element_user(UNOBGAGEBVODATA$0);
            return target;
        }
    }
    /**
     * An XML unObGageBVOData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class UnObGageBVODataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData
    {
        
        public UnObGageBVODataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName UNOBGAGEBVOITEM$2 = 
            new javax.xml.namespace.QName("", "unObGageBVOItem");
        
        
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
         * Gets array of all "unObGageBVOItem" elements
         */
        public nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO[] getUnObGageBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(UNOBGAGEBVOITEM$2, targetList);
                nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO[] result = new nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "unObGageBVOItem" element
         */
        public nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO getUnObGageBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO target = null;
                target = (nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO)get_store().find_element_user(UNOBGAGEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "unObGageBVOItem" element
         */
        public boolean isNilUnObGageBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO target = null;
                target = (nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO)get_store().find_element_user(UNOBGAGEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "unObGageBVOItem" element
         */
        public int sizeOfUnObGageBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(UNOBGAGEBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "unObGageBVOItem" element
         */
        public void setUnObGageBVOItemArray(nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO[] unObGageBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(unObGageBVOItemArray, UNOBGAGEBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "unObGageBVOItem" element
         */
        public void setUnObGageBVOItemArray(int i, nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO unObGageBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO target = null;
                target = (nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO)get_store().find_element_user(UNOBGAGEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(unObGageBVOItem);
            }
        }
        
        /**
         * Nils the ith "unObGageBVOItem" element
         */
        public void setNilUnObGageBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO target = null;
                target = (nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO)get_store().find_element_user(UNOBGAGEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "unObGageBVOItem" element
         */
        public nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO insertNewUnObGageBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO target = null;
                target = (nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO)get_store().insert_element_user(UNOBGAGEBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "unObGageBVOItem" element
         */
        public nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO addNewUnObGageBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO target = null;
                target = (nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO)get_store().add_element_user(UNOBGAGEBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "unObGageBVOItem" element
         */
        public void removeUnObGageBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(UNOBGAGEBVOITEM$2, i);
            }
        }
    }
}
