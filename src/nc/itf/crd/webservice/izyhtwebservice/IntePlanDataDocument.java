/*
 * An XML document type.
 * Localname: intePlanData
 * Namespace: http://webservice.crd.itf.nc/IZyhtWebService
 * Java type: nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument
 *
 * Automatically generated - do not modify.
 */
package nc.itf.crd.webservice.izyhtwebservice;


/**
 * A document containing one intePlanData(@http://webservice.crd.itf.nc/IZyhtWebService) element.
 *
 * This is a complex type.
 */
public interface IntePlanDataDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(IntePlanDataDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("inteplandataac60doctype");
    
    /**
     * Gets the "intePlanData" element
     */
    nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData getIntePlanData();
    
    /**
     * Sets the "intePlanData" element
     */
    void setIntePlanData(nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData intePlanData);
    
    /**
     * Appends and returns a new empty "intePlanData" element
     */
    nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData addNewIntePlanData();
    
    /**
     * An XML intePlanData(@http://webservice.crd.itf.nc/IZyhtWebService).
     *
     * This is a complex type.
     */
    public interface IntePlanData extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(IntePlanData.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("inteplandataba65elemtype");
        
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
         * Gets array of all "intePlanBVOItem" elements
         */
        nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO[] getIntePlanBVOItemArray();
        
        /**
         * Gets ith "intePlanBVOItem" element
         */
        nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO getIntePlanBVOItemArray(int i);
        
        /**
         * Tests for nil ith "intePlanBVOItem" element
         */
        boolean isNilIntePlanBVOItemArray(int i);
        
        /**
         * Returns number of "intePlanBVOItem" element
         */
        int sizeOfIntePlanBVOItemArray();
        
        /**
         * Sets array of all "intePlanBVOItem" element
         */
        void setIntePlanBVOItemArray(nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO[] intePlanBVOItemArray);
        
        /**
         * Sets ith "intePlanBVOItem" element
         */
        void setIntePlanBVOItemArray(int i, nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO intePlanBVOItem);
        
        /**
         * Nils the ith "intePlanBVOItem" element
         */
        void setNilIntePlanBVOItemArray(int i);
        
        /**
         * Inserts and returns a new empty value (as xml) as the ith "intePlanBVOItem" element
         */
        nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO insertNewIntePlanBVOItem(int i);
        
        /**
         * Appends and returns a new empty value (as xml) as the last "intePlanBVOItem" element
         */
        nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO addNewIntePlanBVOItem();
        
        /**
         * Removes the ith "intePlanBVOItem" element
         */
        void removeIntePlanBVOItem(int i);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData newInstance() {
              return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument newInstance() {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
