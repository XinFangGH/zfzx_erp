/*
 * An XML document type.
 * Localname: realFeeData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one realFeeData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class RealFeeDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument
{
    
    public RealFeeDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REALFEEDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "realFeeData");
    
    
    /**
     * Gets the "realFeeData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData getRealFeeData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData)get_store().find_element_user(REALFEEDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "realFeeData" element
     */
    public void setRealFeeData(nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData realFeeData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData)get_store().find_element_user(REALFEEDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData)get_store().add_element_user(REALFEEDATA$0);
            }
            target.set(realFeeData);
        }
    }
    
    /**
     * Appends and returns a new empty "realFeeData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData addNewRealFeeData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData)get_store().add_element_user(REALFEEDATA$0);
            return target;
        }
    }
    /**
     * An XML realFeeData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class RealFeeDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData
    {
        
        public RealFeeDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName REALFEEBVOITEM$2 = 
            new javax.xml.namespace.QName("", "realFeeBVOItem");
        
        
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
         * Gets array of all "realFeeBVOItem" elements
         */
        public nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO[] getRealFeeBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(REALFEEBVOITEM$2, targetList);
                nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO[] result = new nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "realFeeBVOItem" element
         */
        public nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO getRealFeeBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO target = null;
                target = (nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO)get_store().find_element_user(REALFEEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "realFeeBVOItem" element
         */
        public boolean isNilRealFeeBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO target = null;
                target = (nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO)get_store().find_element_user(REALFEEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "realFeeBVOItem" element
         */
        public int sizeOfRealFeeBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(REALFEEBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "realFeeBVOItem" element
         */
        public void setRealFeeBVOItemArray(nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO[] realFeeBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(realFeeBVOItemArray, REALFEEBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "realFeeBVOItem" element
         */
        public void setRealFeeBVOItemArray(int i, nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO realFeeBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO target = null;
                target = (nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO)get_store().find_element_user(REALFEEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(realFeeBVOItem);
            }
        }
        
        /**
         * Nils the ith "realFeeBVOItem" element
         */
        public void setNilRealFeeBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO target = null;
                target = (nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO)get_store().find_element_user(REALFEEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "realFeeBVOItem" element
         */
        public nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO insertNewRealFeeBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO target = null;
                target = (nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO)get_store().insert_element_user(REALFEEBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "realFeeBVOItem" element
         */
        public nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO addNewRealFeeBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO target = null;
                target = (nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO)get_store().add_element_user(REALFEEBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "realFeeBVOItem" element
         */
        public void removeRealFeeBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(REALFEEBVOITEM$2, i);
            }
        }
    }
}
