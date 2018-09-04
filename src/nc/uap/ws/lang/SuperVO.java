/*
 * XML Type:  SuperVO
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.SuperVO
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang;


/**
 * An XML SuperVO(@http://ws.uap.nc/lang).
 *
 * This is a complex type.
 */
public interface SuperVO extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(SuperVO.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("supervob649type");
    
    /**
     * Gets the "status" element
     */
    int getStatus();
    
    /**
     * Gets (as xml) the "status" element
     */
    org.apache.xmlbeans.XmlInt xgetStatus();
    
    /**
     * Sets the "status" element
     */
    void setStatus(int status);
    
    /**
     * Sets (as xml) the "status" element
     */
    void xsetStatus(org.apache.xmlbeans.XmlInt status);
    
    /**
     * Gets the "dirty" element
     */
    boolean getDirty();
    
    /**
     * Gets (as xml) the "dirty" element
     */
    org.apache.xmlbeans.XmlBoolean xgetDirty();
    
    /**
     * Sets the "dirty" element
     */
    void setDirty(boolean dirty);
    
    /**
     * Sets (as xml) the "dirty" element
     */
    void xsetDirty(org.apache.xmlbeans.XmlBoolean dirty);
    
    /**
     * Gets the "primaryKey" element
     */
    java.lang.String getPrimaryKey();
    
    /**
     * Gets (as xml) the "primaryKey" element
     */
    org.apache.xmlbeans.XmlString xgetPrimaryKey();
    
    /**
     * Sets the "primaryKey" element
     */
    void setPrimaryKey(java.lang.String primaryKey);
    
    /**
     * Sets (as xml) the "primaryKey" element
     */
    void xsetPrimaryKey(org.apache.xmlbeans.XmlString primaryKey);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        /** @deprecated No need to be able to create instances of abstract types */
        public static nc.uap.ws.lang.SuperVO newInstance() {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        /** @deprecated No need to be able to create instances of abstract types */
        public static nc.uap.ws.lang.SuperVO newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static nc.uap.ws.lang.SuperVO parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static nc.uap.ws.lang.SuperVO parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static nc.uap.ws.lang.SuperVO parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static nc.uap.ws.lang.SuperVO parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static nc.uap.ws.lang.SuperVO parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static nc.uap.ws.lang.SuperVO parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static nc.uap.ws.lang.SuperVO parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static nc.uap.ws.lang.SuperVO parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static nc.uap.ws.lang.SuperVO parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static nc.uap.ws.lang.SuperVO parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static nc.uap.ws.lang.SuperVO parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static nc.uap.ws.lang.SuperVO parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static nc.uap.ws.lang.SuperVO parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static nc.uap.ws.lang.SuperVO parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.uap.ws.lang.SuperVO parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.uap.ws.lang.SuperVO parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.uap.ws.lang.SuperVO) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
