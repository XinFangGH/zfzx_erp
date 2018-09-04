/*
 * An XML document type.
 * Localname: alObGageData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one alObGageData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class AlObGageDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument
{
    
    public AlObGageDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName ALOBGAGEDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "alObGageData");
    
    
    /**
     * Gets the "alObGageData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData getAlObGageData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData)get_store().find_element_user(ALOBGAGEDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "alObGageData" element
     */
    public void setAlObGageData(nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData alObGageData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData)get_store().find_element_user(ALOBGAGEDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData)get_store().add_element_user(ALOBGAGEDATA$0);
            }
            target.set(alObGageData);
        }
    }
    
    /**
     * Appends and returns a new empty "alObGageData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData addNewAlObGageData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData)get_store().add_element_user(ALOBGAGEDATA$0);
            return target;
        }
    }
    /**
     * An XML alObGageData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class AlObGageDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData
    {
        
        public AlObGageDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName ALOBGAGEBVOITEM$2 = 
            new javax.xml.namespace.QName("", "alObGageBVOItem");
        
        
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
         * Gets array of all "alObGageBVOItem" elements
         */
        public nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO[] getAlObGageBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(ALOBGAGEBVOITEM$2, targetList);
                nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO[] result = new nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "alObGageBVOItem" element
         */
        public nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO getAlObGageBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO target = null;
                target = (nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO)get_store().find_element_user(ALOBGAGEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "alObGageBVOItem" element
         */
        public boolean isNilAlObGageBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO target = null;
                target = (nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO)get_store().find_element_user(ALOBGAGEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "alObGageBVOItem" element
         */
        public int sizeOfAlObGageBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(ALOBGAGEBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "alObGageBVOItem" element
         */
        public void setAlObGageBVOItemArray(nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO[] alObGageBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(alObGageBVOItemArray, ALOBGAGEBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "alObGageBVOItem" element
         */
        public void setAlObGageBVOItemArray(int i, nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO alObGageBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO target = null;
                target = (nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO)get_store().find_element_user(ALOBGAGEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(alObGageBVOItem);
            }
        }
        
        /**
         * Nils the ith "alObGageBVOItem" element
         */
        public void setNilAlObGageBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO target = null;
                target = (nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO)get_store().find_element_user(ALOBGAGEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "alObGageBVOItem" element
         */
        public nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO insertNewAlObGageBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO target = null;
                target = (nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO)get_store().insert_element_user(ALOBGAGEBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "alObGageBVOItem" element
         */
        public nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO addNewAlObGageBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO target = null;
                target = (nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO)get_store().add_element_user(ALOBGAGEBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "alObGageBVOItem" element
         */
        public void removeAlObGageBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(ALOBGAGEBVOITEM$2, i);
            }
        }
    }
}
