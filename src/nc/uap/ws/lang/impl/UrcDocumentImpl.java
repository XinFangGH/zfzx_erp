/*
 * An XML document type.
 * Localname: Urc
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.UrcDocument
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang.impl;
/**
 * A document containing one Urc(@http://ws.uap.nc/lang) element.
 *
 * This is a complex type.
 */
public class UrcDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.uap.ws.lang.UrcDocument
{
    
    public UrcDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName URC$0 = 
        new javax.xml.namespace.QName("http://ws.uap.nc/lang", "Urc");
    
    
    /**
     * Gets the "Urc" element
     */
    public nc.uap.ws.lang.UrcDocument.Urc getUrc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UrcDocument.Urc target = null;
            target = (nc.uap.ws.lang.UrcDocument.Urc)get_store().find_element_user(URC$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Urc" element
     */
    public void setUrc(nc.uap.ws.lang.UrcDocument.Urc urc)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UrcDocument.Urc target = null;
            target = (nc.uap.ws.lang.UrcDocument.Urc)get_store().find_element_user(URC$0, 0);
            if (target == null)
            {
                target = (nc.uap.ws.lang.UrcDocument.Urc)get_store().add_element_user(URC$0);
            }
            target.set(urc);
        }
    }
    
    /**
     * Appends and returns a new empty "Urc" element
     */
    public nc.uap.ws.lang.UrcDocument.Urc addNewUrc()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.uap.ws.lang.UrcDocument.Urc target = null;
            target = (nc.uap.ws.lang.UrcDocument.Urc)get_store().add_element_user(URC$0);
            return target;
        }
    }
    /**
     * An XML Urc(@http://ws.uap.nc/lang).
     *
     * This is a complex type.
     */
    public static class UrcImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.uap.ws.lang.UrcDocument.Urc
    {
        
        public UrcImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName DATASOURCE$0 = 
            new javax.xml.namespace.QName("", "datasource");
        private static final javax.xml.namespace.QName CORPCODE$2 = 
            new javax.xml.namespace.QName("", "corpCode");
        private static final javax.xml.namespace.QName USERCODE$4 = 
            new javax.xml.namespace.QName("", "userCode");
        private static final javax.xml.namespace.QName LOGINDATE$6 = 
            new javax.xml.namespace.QName("", "loginDate");
        private static final javax.xml.namespace.QName CLIENTHOST$8 = 
            new javax.xml.namespace.QName("", "clientHost");
        private static final javax.xml.namespace.QName LANGCODE$10 = 
            new javax.xml.namespace.QName("", "langCode");
        
        
        /**
         * Gets the "datasource" element
         */
        public java.lang.String getDatasource()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATASOURCE$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "datasource" element
         */
        public org.apache.xmlbeans.XmlString xgetDatasource()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATASOURCE$0, 0);
                return target;
            }
        }
        
        /**
         * True if has "datasource" element
         */
        public boolean isSetDatasource()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(DATASOURCE$0) != 0;
            }
        }
        
        /**
         * Sets the "datasource" element
         */
        public void setDatasource(java.lang.String datasource)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DATASOURCE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DATASOURCE$0);
                }
                target.setStringValue(datasource);
            }
        }
        
        /**
         * Sets (as xml) the "datasource" element
         */
        public void xsetDatasource(org.apache.xmlbeans.XmlString datasource)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DATASOURCE$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DATASOURCE$0);
                }
                target.set(datasource);
            }
        }
        
        /**
         * Unsets the "datasource" element
         */
        public void unsetDatasource()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(DATASOURCE$0, 0);
            }
        }
        
        /**
         * Gets the "corpCode" element
         */
        public java.lang.String getCorpCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CORPCODE$2, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "corpCode" element
         */
        public org.apache.xmlbeans.XmlString xgetCorpCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CORPCODE$2, 0);
                return target;
            }
        }
        
        /**
         * True if has "corpCode" element
         */
        public boolean isSetCorpCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(CORPCODE$2) != 0;
            }
        }
        
        /**
         * Sets the "corpCode" element
         */
        public void setCorpCode(java.lang.String corpCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CORPCODE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CORPCODE$2);
                }
                target.setStringValue(corpCode);
            }
        }
        
        /**
         * Sets (as xml) the "corpCode" element
         */
        public void xsetCorpCode(org.apache.xmlbeans.XmlString corpCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CORPCODE$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CORPCODE$2);
                }
                target.set(corpCode);
            }
        }
        
        /**
         * Unsets the "corpCode" element
         */
        public void unsetCorpCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(CORPCODE$2, 0);
            }
        }
        
        /**
         * Gets the "userCode" element
         */
        public java.lang.String getUserCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERCODE$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "userCode" element
         */
        public org.apache.xmlbeans.XmlString xgetUserCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERCODE$4, 0);
                return target;
            }
        }
        
        /**
         * True if has "userCode" element
         */
        public boolean isSetUserCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(USERCODE$4) != 0;
            }
        }
        
        /**
         * Sets the "userCode" element
         */
        public void setUserCode(java.lang.String userCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(USERCODE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(USERCODE$4);
                }
                target.setStringValue(userCode);
            }
        }
        
        /**
         * Sets (as xml) the "userCode" element
         */
        public void xsetUserCode(org.apache.xmlbeans.XmlString userCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(USERCODE$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(USERCODE$4);
                }
                target.set(userCode);
            }
        }
        
        /**
         * Unsets the "userCode" element
         */
        public void unsetUserCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(USERCODE$4, 0);
            }
        }
        
        /**
         * Gets the "loginDate" element
         */
        public java.lang.String getLoginDate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOGINDATE$6, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "loginDate" element
         */
        public org.apache.xmlbeans.XmlString xgetLoginDate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOGINDATE$6, 0);
                return target;
            }
        }
        
        /**
         * True if has "loginDate" element
         */
        public boolean isSetLoginDate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(LOGINDATE$6) != 0;
            }
        }
        
        /**
         * Sets the "loginDate" element
         */
        public void setLoginDate(java.lang.String loginDate)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LOGINDATE$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LOGINDATE$6);
                }
                target.setStringValue(loginDate);
            }
        }
        
        /**
         * Sets (as xml) the "loginDate" element
         */
        public void xsetLoginDate(org.apache.xmlbeans.XmlString loginDate)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LOGINDATE$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LOGINDATE$6);
                }
                target.set(loginDate);
            }
        }
        
        /**
         * Unsets the "loginDate" element
         */
        public void unsetLoginDate()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(LOGINDATE$6, 0);
            }
        }
        
        /**
         * Gets the "clientHost" element
         */
        public java.lang.String getClientHost()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLIENTHOST$8, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "clientHost" element
         */
        public org.apache.xmlbeans.XmlString xgetClientHost()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLIENTHOST$8, 0);
                return target;
            }
        }
        
        /**
         * True if has "clientHost" element
         */
        public boolean isSetClientHost()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(CLIENTHOST$8) != 0;
            }
        }
        
        /**
         * Sets the "clientHost" element
         */
        public void setClientHost(java.lang.String clientHost)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(CLIENTHOST$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(CLIENTHOST$8);
                }
                target.setStringValue(clientHost);
            }
        }
        
        /**
         * Sets (as xml) the "clientHost" element
         */
        public void xsetClientHost(org.apache.xmlbeans.XmlString clientHost)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(CLIENTHOST$8, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(CLIENTHOST$8);
                }
                target.set(clientHost);
            }
        }
        
        /**
         * Unsets the "clientHost" element
         */
        public void unsetClientHost()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(CLIENTHOST$8, 0);
            }
        }
        
        /**
         * Gets the "langCode" element
         */
        public java.lang.String getLangCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LANGCODE$10, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "langCode" element
         */
        public org.apache.xmlbeans.XmlString xgetLangCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LANGCODE$10, 0);
                return target;
            }
        }
        
        /**
         * True if has "langCode" element
         */
        public boolean isSetLangCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                return get_store().count_elements(LANGCODE$10) != 0;
            }
        }
        
        /**
         * Sets the "langCode" element
         */
        public void setLangCode(java.lang.String langCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(LANGCODE$10, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(LANGCODE$10);
                }
                target.setStringValue(langCode);
            }
        }
        
        /**
         * Sets (as xml) the "langCode" element
         */
        public void xsetLangCode(org.apache.xmlbeans.XmlString langCode)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(LANGCODE$10, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(LANGCODE$10);
                }
                target.set(langCode);
            }
        }
        
        /**
         * Unsets the "langCode" element
         */
        public void unsetLangCode()
        {
            synchronized (monitor())
            {
                check_orphaned();
                get_store().remove_element(LANGCODE$10, 0);
            }
        }
    }
}
