/*
 * An XML document type.
 * Localname: overDueData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice.impl;
/**
 * A document containing one overDueData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public class OverDueDataDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument
{
    
    public OverDueDataDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName OVERDUEDATA$0 = 
        new javax.xml.namespace.QName("http://webservice.crd.itf.nc/IZyhtWebService", "overDueData");
    
    
    /**
     * Gets the "overDueData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData getOverDueData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData)get_store().find_element_user(OVERDUEDATA$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "overDueData" element
     */
    public void setOverDueData(nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData overDueData)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData)get_store().find_element_user(OVERDUEDATA$0, 0);
            if (target == null)
            {
                target = (nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData)get_store().add_element_user(OVERDUEDATA$0);
            }
            target.set(overDueData);
        }
    }
    
    /**
     * Appends and returns a new empty "overDueData" element
     */
    public nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData addNewOverDueData()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData target = null;
            target = (nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData)get_store().add_element_user(OVERDUEDATA$0);
            return target;
        }
    }
    /**
     * An XML overDueData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public static class OverDueDataImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData
    {
        
        public OverDueDataImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName ZYHTVO$0 = 
            new javax.xml.namespace.QName("", "zyhtVO");
        private static final javax.xml.namespace.QName OVERDUEBVOITEM$2 = 
            new javax.xml.namespace.QName("", "overDueBVOItem");
        
        
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
         * Gets array of all "overDueBVOItem" elements
         */
        public nc.vo.crd.acc.overdue.overduebvo.OverDueBVO[] getOverDueBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                java.util.List targetList = new java.util.ArrayList();
                get_store().find_all_element_users(OVERDUEBVOITEM$2, targetList);
                nc.vo.crd.acc.overdue.overduebvo.OverDueBVO[] result = new nc.vo.crd.acc.overdue.overduebvo.OverDueBVO[targetList.size()];
                targetList.toArray(result);
                return result;
            }
        }
        
        /**
         * Gets ith "overDueBVOItem" element
         */
        public nc.vo.crd.acc.overdue.overduebvo.OverDueBVO getOverDueBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.overdue.overduebvo.OverDueBVO target = null;
                target = (nc.vo.crd.acc.overdue.overduebvo.OverDueBVO)get_store().find_element_user(OVERDUEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target;
            }
        }
        
        /**
         * Tests for nil ith "overDueBVOItem" element
         */
        public boolean isNilOverDueBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.overdue.overduebvo.OverDueBVO target = null;
                target = (nc.vo.crd.acc.overdue.overduebvo.OverDueBVO)get_store().find_element_user(OVERDUEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                return target.isNil();
            }
        }
        
        /**
         * Returns number of "overDueBVOItem" element
         */
        public int sizeOfOverDueBVOItemArray()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(OVERDUEBVOITEM$2);
            }
        }
        
        /**
         * Sets array of all "overDueBVOItem" element
         */
        public void setOverDueBVOItemArray(nc.vo.crd.acc.overdue.overduebvo.OverDueBVO[] overDueBVOItemArray)
        {
            synchronized (monitor())
            {
                check_orphaned();
                arraySetterHelper(overDueBVOItemArray, OVERDUEBVOITEM$2);
            }
        }
        
        /**
         * Sets ith "overDueBVOItem" element
         */
        public void setOverDueBVOItemArray(int i, nc.vo.crd.acc.overdue.overduebvo.OverDueBVO overDueBVOItem)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.overdue.overduebvo.OverDueBVO target = null;
                target = (nc.vo.crd.acc.overdue.overduebvo.OverDueBVO)get_store().find_element_user(OVERDUEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.set(overDueBVOItem);
            }
        }
        
        /**
         * Nils the ith "overDueBVOItem" element
         */
        public void setNilOverDueBVOItemArray(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.overdue.overduebvo.OverDueBVO target = null;
                target = (nc.vo.crd.acc.overdue.overduebvo.OverDueBVO)get_store().find_element_user(OVERDUEBVOITEM$2, i);
                if (target == null)
                {
                    throw new IndexOutOfBoundsException();
                }
                target.setNil();
            }
        }
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "overDueBVOItem" element
         */
        public nc.vo.crd.acc.overdue.overduebvo.OverDueBVO insertNewOverDueBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.overdue.overduebvo.OverDueBVO target = null;
                target = (nc.vo.crd.acc.overdue.overduebvo.OverDueBVO)get_store().insert_element_user(OVERDUEBVOITEM$2, i);
                return target;
            }
        }
        
        /**
         * Appends and returns a new empty value (as xml) as the last "overDueBVOItem" element
         */
        public nc.vo.crd.acc.overdue.overduebvo.OverDueBVO addNewOverDueBVOItem()
        {
            synchronized (monitor())
            {
                check_orphaned();
                nc.vo.crd.acc.overdue.overduebvo.OverDueBVO target = null;
                target = (nc.vo.crd.acc.overdue.overduebvo.OverDueBVO)get_store().add_element_user(OVERDUEBVOITEM$2);
                return target;
            }
        }
        
        /**
         * Removes the ith "overDueBVOItem" element
         */
        public void removeOverDueBVOItem(int i)
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(OVERDUEBVOITEM$2, i);
            }
        }
    }
}
