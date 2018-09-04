/*
 * An XML document type.
 * Localname: inteAccrData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one inteAccrData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class InteAccrDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument
{
    
    public InteAccrDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName INTEACCRDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "inteAccrData");
    
    
    /**
     * Gets the "inteAccrData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData getInteAccrData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData)get_store().find_element_user(INTEACCRDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "inteAccrData" element
     */
    public void setInteAccrData(nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData inteAccrData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData)get_store().find_element_user(INTEACCRDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData)get_store().add_element_user(INTEACCRDATA$0);
            }
            target.set(inteAccrData);
        }
    }
    
    /**
     * Appends and returns a new empty "inteAccrData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData addNewInteAccrData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData)get_store().add_element_user(INTEACCRDATA$0);
            return target;
        }
    }
    /**
     * An XML inteAccrData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class InteAccrDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData
    {
        
        public InteAccrDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName INTEACCRBVOITEM$2 = 
            new javax.xml.namespace.QName("", "inteAccrBVOItem");
        
        
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
         * Gets array of all "inteAccrBVOItem" elements
         */
        public nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO[] getInteAccrBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(INTEACCRBVOITEM$2, targetList);
                nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO[] result = new nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "inteAccrBVOItem" element
         */
        public nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO getInteAccrBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO target = null;
                target = (nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO)get_store().find_element_user(INTEACCRBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "inteAccrBVOItem" element
         */
        public boolean isNilInteAccrBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO target = null;
                target = (nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO)get_store().find_element_user(INTEACCRBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "inteAccrBVOItem" element
         */
        public int sizeOfInteAccrBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(INTEACCRBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "inteAccrBVOItem" element
         */
        public void setInteAccrBVOItemArray(nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO[] inteAccrBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(inteAccrBVOItemArray, INTEACCRBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "inteAccrBVOItem" element
         */
        public void setInteAccrBVOItemArray(int i, nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO inteAccrBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO target = null;
                target = (nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO)get_store().find_element_user(INTEACCRBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(inteAccrBVOItem);
            }
        }
        
        /**
         * Nils the ith "inteAccrBVOItem" element
         */
        public void setNilInteAccrBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO target = null;
                target = (nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO)get_store().find_element_user(INTEACCRBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "inteAccrBVOItem" element
         */
        public nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO insertNewInteAccrBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO target = null;
                target = (nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO)get_store().insert_element_user(INTEACCRBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "inteAccrBVOItem" element
         */
        public nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO addNewInteAccrBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO target = null;
                target = (nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO)get_store().add_element_user(INTEACCRBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "inteAccrBVOItem" element
         */
        public void removeInteAccrBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(INTEACCRBVOITEM$2, i);
            }
        }
    }
}
