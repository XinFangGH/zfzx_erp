/*
 * An XML document type.
 * Localname: feePlanData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice;


/**
 * A document containing one feePlanData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public interface FeePlanDataDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(FeePlanDataDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("feeplandatac902doctype");
    
    /**
     * Gets the "feePlanData" element
     */
    nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData getFeePlanData();
    
    /**
     * Sets the "feePlanData" element
     */
    void setFeePlanData(nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData feePlanData);
    
    /**
     * Appends and returns a new empty "feePlanData" element
     */
    nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData addNewFeePlanData();
    
    /**
     * An XML feePlanData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public interface FeePlanData extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(FeePlanData.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("feeplandata8d5delemtype");
        
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
         * Gets array of all "feePlanBVOItem" elements
         */
        nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO[] getFeePlanBVOItemArray();
        
        /**
         * Gets ith "feePlanBVOItem" element
         */
        nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO getFeePlanBVOItemArray(int i);
        
        /**
         * Tests for nil ith "feePlanBVOItem" element
         */
        boolean isNilFeePlanBVOItemArray(int i);
        
        /**
         * Returns number of "feePlanBVOItem" element
         */
        int sizeOfFeePlanBVOItemArray();
        
        /**
         * Sets array of all "feePlanBVOItem" element
         */
        void setFeePlanBVOItemArray(nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO[] feePlanBVOItemArray);
        
        /**
         * Sets ith "feePlanBVOItem" element
         */
        void setFeePlanBVOItemArray(int i, nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO feePlanBVOItem);
        
        /**
         * Nils the ith "feePlanBVOItem" element
         */
        void setNilFeePlanBVOItemArray(int i);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "feePlanBVOItem" element
         */
        nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO insertNewFeePlanBVOItem(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "feePlanBVOItem" element
         */
        nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO addNewFeePlanBVOItem();
        
        /**
         * Removes the ith "feePlanBVOItem" element
         */
        void removeFeePlanBVOItem(int i);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData newInstance() {
              return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument newInstance() {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
