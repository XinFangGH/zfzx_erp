/*
 * An XML document type.
 * Localname: accGrantData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice;


/**
 * A document containing one accGrantData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public interface AccGrantDataDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AccGrantDataDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("accgrantdataad64doctype");
    
    /**
     * Gets the "accGrantData" element
     */
    nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData getAccGrantData();
    
    /**
     * Sets the "accGrantData" element
     */
    void setAccGrantData(nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData accGrantData);
    
    /**
     * Appends and returns a new empty "accGrantData" element
     */
    nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData addNewAccGrantData();
    
    /**
     * An XML accGrantData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public interface AccGrantData extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(AccGrantData.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("accgrantdata11e5elemtype");
        
        /**
         * Gets the "zyhtVO" element
         */
        nc.vo.crd.bd.interf.zyhtvo.ZyhtVO getZyhtVO();
        
        /**
         * Tests for nil "zyhtVO" element
         */
        boolean isNilZyhtVO();
        
        /**
         * True if has "zyhtVO" element
         */
        boolean isSetZyhtVO();
        
        /**
         * Sets the "zyhtVO" element
         */
        void setZyhtVO(nc.vo.crd.bd.interf.zyhtvo.ZyhtVO zyhtVO);
        
        /**
         * Appends and returns a new empty "zyhtVO" element
         */
        nc.vo.crd.bd.interf.zyhtvo.ZyhtVO addNewZyhtVO();
        
        /**
         * Nils the "zyhtVO" element
         */
        void setNilZyhtVO();
        
        /**
         * Unsets the "zyhtVO" element
         */
        void unsetZyhtVO();
        
        /**
         * Gets array of all "grantBVOItem" elements
         */
        nc.vo.crd.acc.grant.grantbvo.GrantBVO[] getGrantBVOItemArray();
        
        /**
         * Gets ith "grantBVOItem" element
         */
        nc.vo.crd.acc.grant.grantbvo.GrantBVO getGrantBVOItemArray(int i);
        
        /**
         * Tests for nil ith "grantBVOItem" element
         */
        boolean isNilGrantBVOItemArray(int i);
        
        /**
         * Returns number of "grantBVOItem" element
         */
        int sizeOfGrantBVOItemArray();
        
        /**
         * Sets array of all "grantBVOItem" element
         */
        void setGrantBVOItemArray(nc.vo.crd.acc.grant.grantbvo.GrantBVO[] grantBVOItemArray);
        
        /**
         * Sets ith "grantBVOItem" element
         */
        void setGrantBVOItemArray(int i, nc.vo.crd.acc.grant.grantbvo.GrantBVO grantBVOItem);
        
        /**
         * Nils the ith "grantBVOItem" element
         */
        void setNilGrantBVOItemArray(int i);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "grantBVOItem" element
         */
        nc.vo.crd.acc.grant.grantbvo.GrantBVO insertNewGrantBVOItem(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "grantBVOItem" element
         */
        nc.vo.crd.acc.grant.grantbvo.GrantBVO addNewGrantBVOItem();
        
        /**
         * Removes the ith "grantBVOItem" element
         */
        void removeGrantBVOItem(int i);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData newInstance() {
              return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument newInstance() {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
