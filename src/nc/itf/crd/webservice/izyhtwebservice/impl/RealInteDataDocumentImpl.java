/*
 * An XML document type.
 * Localname: realInteData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one realInteData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class RealInteDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument
{
    
    public RealInteDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REALINTEDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "realInteData");
    
    
    /**
     * Gets the "realInteData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData getRealInteData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData)get_store().find_element_user(REALINTEDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "realInteData" element
     */
    public void setRealInteData(nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData realInteData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData)get_store().find_element_user(REALINTEDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData)get_store().add_element_user(REALINTEDATA$0);
            }
            target.set(realInteData);
        }
    }
    
    /**
     * Appends and returns a new empty "realInteData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData addNewRealInteData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData)get_store().add_element_user(REALINTEDATA$0);
            return target;
        }
    }
    /**
     * An XML realInteData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class RealInteDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData
    {
        
        public RealInteDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName REALINTEBVOITEM$2 = 
            new javax.xml.namespace.QName("", "realInteBVOItem");
        
        
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
         * Gets array of all "realInteBVOItem" elements
         */
        public nc.vo.crd.acc.realinte.realintebvo.RealInteBVO[] getRealInteBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(REALINTEBVOITEM$2, targetList);
                nc.vo.crd.acc.realinte.realintebvo.RealInteBVO[] result = new nc.vo.crd.acc.realinte.realintebvo.RealInteBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "realInteBVOItem" element
         */
        public nc.vo.crd.acc.realinte.realintebvo.RealInteBVO getRealInteBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realinte.realintebvo.RealInteBVO target = null;
                target = (nc.vo.crd.acc.realinte.realintebvo.RealInteBVO)get_store().find_element_user(REALINTEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "realInteBVOItem" element
         */
        public boolean isNilRealInteBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realinte.realintebvo.RealInteBVO target = null;
                target = (nc.vo.crd.acc.realinte.realintebvo.RealInteBVO)get_store().find_element_user(REALINTEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "realInteBVOItem" element
         */
        public int sizeOfRealInteBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(REALINTEBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "realInteBVOItem" element
         */
        public void setRealInteBVOItemArray(nc.vo.crd.acc.realinte.realintebvo.RealInteBVO[] realInteBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(realInteBVOItemArray, REALINTEBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "realInteBVOItem" element
         */
        public void setRealInteBVOItemArray(int i, nc.vo.crd.acc.realinte.realintebvo.RealInteBVO realInteBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realinte.realintebvo.RealInteBVO target = null;
                target = (nc.vo.crd.acc.realinte.realintebvo.RealInteBVO)get_store().find_element_user(REALINTEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(realInteBVOItem);
            }
        }
        
        /**
         * Nils the ith "realInteBVOItem" element
         */
        public void setNilRealInteBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realinte.realintebvo.RealInteBVO target = null;
                target = (nc.vo.crd.acc.realinte.realintebvo.RealInteBVO)get_store().find_element_user(REALINTEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "realInteBVOItem" element
         */
        public nc.vo.crd.acc.realinte.realintebvo.RealInteBVO insertNewRealInteBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realinte.realintebvo.RealInteBVO target = null;
                target = (nc.vo.crd.acc.realinte.realintebvo.RealInteBVO)get_store().insert_element_user(REALINTEBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "realInteBVOItem" element
         */
        public nc.vo.crd.acc.realinte.realintebvo.RealInteBVO addNewRealInteBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.realinte.realintebvo.RealInteBVO target = null;
                target = (nc.vo.crd.acc.realinte.realintebvo.RealInteBVO)get_store().add_element_user(REALINTEBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "realInteBVOItem" element
         */
        public void removeRealInteBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(REALINTEBVOITEM$2, i);
            }
        }
    }
}
