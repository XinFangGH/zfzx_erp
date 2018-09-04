/*
 * An XML document type.
 * Localname: bdCubDocData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice;


/**
 * A document containing one bdCubDocData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public interface BdCubDocDataDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BdCubDocDataDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("bdcubdocdataa295doctype");
    
    /**
     * Gets the "bdCubDocData" element
     */
    nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData getBdCubDocData();
    
    /**
     * Sets the "bdCubDocData" element
     */
    void setBdCubDocData(nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData bdCubDocData);
    
    /**
     * Appends and returns a new empty "bdCubDocData" element
     */
    nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData addNewBdCubDocData();
    
    /**
     * An XML bdCubDocData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public interface BdCubDocData extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(BdCubDocData.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("bdcubdocdatae9c5elemtype");
        
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
         * Gets array of all "bdCubasdocPlusVOItem" elements
         */
        nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO[] getBdCubasdocPlusVOItemArray();
        
        /**
         * Gets ith "bdCubasdocPlusVOItem" element
         */
        nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO getBdCubasdocPlusVOItemArray(int i);
        
        /**
         * Tests for nil ith "bdCubasdocPlusVOItem" element
         */
        boolean isNilBdCubasdocPlusVOItemArray(int i);
        
        /**
         * Returns number of "bdCubasdocPlusVOItem" element
         */
        int sizeOfBdCubasdocPlusVOItemArray();
        
        /**
         * Sets array of all "bdCubasdocPlusVOItem" element
         */
        void setBdCubasdocPlusVOItemArray(nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO[] bdCubasdocPlusVOItemArray);
        
        /**
         * Sets ith "bdCubasdocPlusVOItem" element
         */
        void setBdCubasdocPlusVOItemArray(int i, nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO bdCubasdocPlusVOItem);
        
        /**
         * Nils the ith "bdCubasdocPlusVOItem" element
         */
        void setNilBdCubasdocPlusVOItemArray(int i);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "bdCubasdocPlusVOItem" element
         */
        nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO insertNewBdCubasdocPlusVOItem(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "bdCubasdocPlusVOItem" element
         */
        nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO addNewBdCubasdocPlusVOItem();
        
        /**
         * Removes the ith "bdCubasdocPlusVOItem" element
         */
        void removeBdCubasdocPlusVOItem(int i);
        
        /**
         * Gets array of all "bankInfoPlusVOItem" elements
         */
        nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO[] getBankInfoPlusVOItemArray();
        
        /**
         * Gets ith "bankInfoPlusVOItem" element
         */
        nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO getBankInfoPlusVOItemArray(int i);
        
        /**
         * Tests for nil ith "bankInfoPlusVOItem" element
         */
        boolean isNilBankInfoPlusVOItemArray(int i);
        
        /**
         * Returns number of "bankInfoPlusVOItem" element
         */
        int sizeOfBankInfoPlusVOItemArray();
        
        /**
         * Sets array of all "bankInfoPlusVOItem" element
         */
        void setBankInfoPlusVOItemArray(nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO[] bankInfoPlusVOItemArray);
        
        /**
         * Sets ith "bankInfoPlusVOItem" element
         */
        void setBankInfoPlusVOItemArray(int i, nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO bankInfoPlusVOItem);
        
        /**
         * Nils the ith "bankInfoPlusVOItem" element
         */
        void setNilBankInfoPlusVOItemArray(int i);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "bankInfoPlusVOItem" element
         */
        nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO insertNewBankInfoPlusVOItem(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "bankInfoPlusVOItem" element
         */
        nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO addNewBankInfoPlusVOItem();
        
        /**
         * Removes the ith "bankInfoPlusVOItem" element
         */
        void removeBankInfoPlusVOItem(int i);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData newInstance() {
              return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument newInstance() {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
