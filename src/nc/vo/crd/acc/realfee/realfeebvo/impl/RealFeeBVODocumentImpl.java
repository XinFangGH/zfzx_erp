/*
 * An XML document type.
 * Localname: RealFeeBVO
 * Namespace: http://realfee.acc.crd.vo.nc/RealFeeBVO
 * Java type: nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.realfee.realfeebvo.impl;
/**
 * A document containing one RealFeeBVO(@http://realfee.acc.crd.vo.nc/RealFeeBVO) element.
 *
 * This is a complex type.
 */
public class RealFeeBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVODocument
{
    
    public RealFeeBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REALFEEBVO$0 = 
        new javax.xml.namespace.QName("http://realfee.acc.crd.vo.nc/RealFeeBVO", "RealFeeBVO");
    
    
    /**
     * Gets the "RealFeeBVO" element
     */
    public nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO getRealFeeBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO target = null;
            target = (nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO)get_store().find_element_user(REALFEEBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "RealFeeBVO" element
     */
    public void setRealFeeBVO(nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO realFeeBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO target = null;
            target = (nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO)get_store().find_element_user(REALFEEBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO)get_store().add_element_user(REALFEEBVO$0);
            }
            target.set(realFeeBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "RealFeeBVO" element
     */
    public nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO addNewRealFeeBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO target = null;
            target = (nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO)get_store().add_element_user(REALFEEBVO$0);
            return target;
        }
    }
}
