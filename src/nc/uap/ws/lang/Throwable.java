/*
 * XML Type:  Throwable
 * Namespace: http://ws.uap.nc/lang
 * Java type: nc.uap.ws.lang.Throwable
 *
 * Automatically generated - do not modify.
 */
package nc.uap.ws.lang;


/**
 * An XML Throwable(@http://ws.uap.nc/lang).
 *
 * This is a complex type.
 */
public interface Throwable extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Throwable.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s6607560D1AA176E9637C63A88AD4B5EC").resolveHandle("throwablebd55type");
    
    /**
     * Gets the "message" element
     */
    java.lang.String getMessage();
    
    /**
     * Gets (as xml) the "message" element
     */
    org.apache.xmlbeans.XmlString xgetMessage();
    
    /**
     * True if has "message" element
     */
    boolean isSetMessage();
    
    /**
     * Sets the "message" element
     */
    void setMessage(java.lang.String message);
    
    /**
     * Sets (as xml) the "message" element
     */
    void xsetMessage(org.apache.xmlbeans.XmlString message);
    
    /**
     * Unsets the "message" element
     */
    void unsetMessage();
    
    /**
     * Gets array of all "stackTrace" elements
     */
    java.lang.String[] getStackTraceArray();
    
    /**
     * Gets ith "stackTrace" element
     */
    java.lang.String getStackTraceArray(int i);
    
    /**
     * Gets (as xml) array of all "stackTrace" elements
     */
    nc.uap.ws.lang.StackTraceElement[] xgetStackTraceArray();
    
    /**
     * Gets (as xml) ith "stackTrace" element
     */
    nc.uap.ws.lang.StackTraceElement xgetStackTraceArray(int i);
    
    /**
     * Returns number of "stackTrace" element
     */
    int sizeOfStackTraceArray();
    
    /**
     * Sets array of all "stackTrace" element
     */
    void setStackTraceArray(java.lang.String[] stackTraceArray);
    
    /**
     * Sets ith "stackTrace" element
     */
    void setStackTraceArray(int i, java.lang.String stackTrace);
    
    /**
     * Sets (as xml) array of all "stackTrace" element
     */
    void xsetStackTraceArray(nc.uap.ws.lang.StackTraceElement[] stackTraceArray);
    
    /**
     * Sets (as xml) ith "stackTrace" element
     */
    void xsetStackTraceArray(int i, nc.uap.ws.lang.StackTraceElement stackTrace);
    
    /**
     * Inserts the value as the ith "stackTrace" element
     */
    void insertStackTrace(int i, java.lang.String stackTrace);
    
    /**
     * Appends the value as the last "stackTrace" element
     */
    void addStackTrace(java.lang.String stackTrace);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "stackTrace" element
     */
    nc.uap.ws.lang.StackTraceElement insertNewStackTrace(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "stackTrace" element
     */
    nc.uap.ws.lang.StackTraceElement addNewStackTrace();
    
    /**
     * Removes the ith "stackTrace" element
     */
    void removeStackTrace(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static nc.uap.ws.lang.Throwable newInstance() {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static nc.uap.ws.lang.Throwable newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static nc.uap.ws.lang.Throwable parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static nc.uap.ws.lang.Throwable parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static nc.uap.ws.lang.Throwable parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static nc.uap.ws.lang.Throwable parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static nc.uap.ws.lang.Throwable parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static nc.uap.ws.lang.Throwable parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static nc.uap.ws.lang.Throwable parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static nc.uap.ws.lang.Throwable parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static nc.uap.ws.lang.Throwable parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static nc.uap.ws.lang.Throwable parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static nc.uap.ws.lang.Throwable parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static nc.uap.ws.lang.Throwable parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static nc.uap.ws.lang.Throwable parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static nc.uap.ws.lang.Throwable parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.uap.ws.lang.Throwable parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static nc.uap.ws.lang.Throwable parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (nc.uap.ws.lang.Throwable) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
