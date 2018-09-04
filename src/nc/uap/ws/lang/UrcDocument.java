/*
 * An XML document type.
 * Localname: Urc
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.UrcDocument
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang;


/**
 * A document containing one Urc(@http://ws.uap.nc/lang) element.
 *
 * This is a complex type.
 */
public interface UrcDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(UrcDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("urcaa2bdoctype");
    
    /**
     * Gets the "Urc" element
     */
    nc.uap.ws.lang.UrcDocument.Urc getUrc();
    
    /**
     * Sets the "Urc" element
     */
    void setUrc(nc.uap.ws.lang.UrcDocument.Urc urc);
    
    /**
     * Appends and returns a new empty "Urc" element
     */
    nc.uap.ws.lang.UrcDocument.Urc addNewUrc();
    
    /**
     * An XML Urc(@http://ws.uap.nc/lang).
     *
     * This is a complex type.
     */
    public interface Urc extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Urc.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("urc68ddelemtype");
        
        /**
         * Gets the "datasource" element
         */
        java.lang.String getDatasource();
        
        /**
         * Gets (as xml) the "datasource" element
         */
        org.apache.xmlbeans.XmlString xgetDatasource();
        
        /**
         * True if has "datasource" element
         */
        boolean isSetDatasource();
        
        /**
         * Sets the "datasource" element
         */
        void setDatasource(java.lang.String datasource);
        
        /**
         * Sets (as xml) the "datasource" element
         */
        void xsetDatasource(org.apache.xmlbeans.XmlString datasource);
        
        /**
         * Unsets the "datasource" element
         */
        void unsetDatasource();
        
        /**
         * Gets the "corpCode" element
         */
        java.lang.String getCorpCode();
        
        /**
         * Gets (as xml) the "corpCode" element
         */
        org.apache.xmlbeans.XmlString xgetCorpCode();
        
        /**
         * True if has "corpCode" element
         */
        boolean isSetCorpCode();
        
        /**
         * Sets the "corpCode" element
         */
        void setCorpCode(java.lang.String corpCode);
        
        /**
         * Sets (as xml) the "corpCode" element
         */
        void xsetCorpCode(org.apache.xmlbeans.XmlString corpCode);
        
        /**
         * Unsets the "corpCode" element
         */
        void unsetCorpCode();
        
        /**
         * Gets the "userCode" element
         */
        java.lang.String getUserCode();
        
        /**
         * Gets (as xml) the "userCode" element
         */
        org.apache.xmlbeans.XmlString xgetUserCode();
        
        /**
         * True if has "userCode" element
         */
        boolean isSetUserCode();
        
        /**
         * Sets the "userCode" element
         */
        void setUserCode(java.lang.String userCode);
        
        /**
         * Sets (as xml) the "userCode" element
         */
        void xsetUserCode(org.apache.xmlbeans.XmlString userCode);
        
        /**
         * Unsets the "userCode" element
         */
        void unsetUserCode();
        
        /**
         * Gets the "loginDate" element
         */
        java.lang.String getLoginDate();
        
        /**
         * Gets (as xml) the "loginDate" element
         */
        org.apache.xmlbeans.XmlString xgetLoginDate();
        
        /**
         * True if has "loginDate" element
         */
        boolean isSetLoginDate();
        
        /**
         * Sets the "loginDate" element
         */
        void setLoginDate(java.lang.String loginDate);
        
        /**
         * Sets (as xml) the "loginDate" element
         */
        void xsetLoginDate(org.apache.xmlbeans.XmlString loginDate);
        
        /**
         * Unsets the "loginDate" element
         */
        void unsetLoginDate();
        
        /**
         * Gets the "clientHost" element
         */
        java.lang.String getClientHost();
        
        /**
         * Gets (as xml) the "clientHost" element
         */
        org.apache.xmlbeans.XmlString xgetClientHost();
        
        /**
         * True if has "clientHost" element
         */
        boolean isSetClientHost();
        
        /**
         * Sets the "clientHost" element
         */
        void setClientHost(java.lang.String clientHost);
        
        /**
         * Sets (as xml) the "clientHost" element
         */
        void xsetClientHost(org.apache.xmlbeans.XmlString clientHost);
        
        /**
         * Unsets the "clientHost" element
         */
        void unsetClientHost();
        
        /**
         * Gets the "langCode" element
         */
        java.lang.String getLangCode();
        
        /**
         * Gets (as xml) the "langCode" element
         */
        org.apache.xmlbeans.XmlString xgetLangCode();
        
        /**
         * True if has "langCode" element
         */
        boolean isSetLangCode();
        
        /**
         * Sets the "langCode" element
         */
        void setLangCode(java.lang.String langCode);
        
        /**
         * Sets (as xml) the "langCode" element
         */
        void xsetLangCode(org.apache.xmlbeans.XmlString langCode);
        
        /**
         * Unsets the "langCode" element
         */
        void unsetLangCode();
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static nc.uap.ws.lang.UrcDocument.Urc newInstance() {
              return (nc.uap.ws.lang.UrcDocument.Urc) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static nc.uap.ws.lang.UrcDocument.Urc newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (nc.uap.ws.lang.UrcDocument.Urc) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static nc.uap.ws.lang.UrcDocument newInstance() {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static nc.uap.ws.lang.UrcDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static nc.uap.ws.lang.UrcDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static nc.uap.ws.lang.UrcDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static nc.uap.ws.lang.UrcDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.uap.ws.lang.UrcDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.uap.ws.lang.UrcDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.uap.ws.lang.UrcDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
