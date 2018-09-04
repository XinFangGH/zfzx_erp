/*
 * An XML document type.
 * Localname: intePlanData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one intePlanData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class IntePlanDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument
{
    
    public IntePlanDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INTEPLANDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "intePlanData");
    
    
    /**
     * Gets the "intePlanData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData getIntePlanData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData)get_store().find_element_user(INTEPLANDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "intePlanData" element
     */
    public void setIntePlanData(nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData intePlanData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData)get_store().find_element_user(INTEPLANDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData)get_store().add_element_user(INTEPLANDATA$0);
            }
            target.set(intePlanData);
        }
    }
    
    /**
     * Appends and returns a new empty "intePlanData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData addNewIntePlanData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData)get_store().add_element_user(INTEPLANDATA$0);
            return target;
        }
    }
    /**
     * An XML intePlanData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class IntePlanDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData
    {
        
        public IntePlanDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName INTEPLANBVOITEM$2 = 
            new javax.xml.namespace.QName("", "intePlanBVOItem");
        
        
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
         * Gets array of all "intePlanBVOItem" elements
         */
        public nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO[] getIntePlanBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(INTEPLANBVOITEM$2, targetList);
                nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO[] result = new nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "intePlanBVOItem" element
         */
        public nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO getIntePlanBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO target = null;
                target = (nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO)get_store().find_element_user(INTEPLANBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "intePlanBVOItem" element
         */
        public boolean isNilIntePlanBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO target = null;
                target = (nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO)get_store().find_element_user(INTEPLANBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "intePlanBVOItem" element
         */
        public int sizeOfIntePlanBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INTEPLANBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "intePlanBVOItem" element
         */
        public void setIntePlanBVOItemArray(nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO[] intePlanBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(intePlanBVOItemArray, INTEPLANBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "intePlanBVOItem" element
         */
        public void setIntePlanBVOItemArray(int i, nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO intePlanBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO target = null;
                target = (nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO)get_store().find_element_user(INTEPLANBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(intePlanBVOItem);
            }
        }
        
        /**
         * Nils the ith "intePlanBVOItem" element
         */
        public void setNilIntePlanBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO target = null;
                target = (nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO)get_store().find_element_user(INTEPLANBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "intePlanBVOItem" element
         */
        public nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO insertNewIntePlanBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO target = null;
                target = (nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO)get_store().insert_element_user(INTEPLANBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "intePlanBVOItem" element
         */
        public nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO addNewIntePlanBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO target = null;
                target = (nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO)get_store().add_element_user(INTEPLANBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "intePlanBVOItem" element
         */
        public void removeIntePlanBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INTEPLANBVOITEM$2, i);
            }
        }
    }
}
