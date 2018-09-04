/*
 * An XML document type.
 * Localname: RealInteBVO
 * Namespace: http://realinte.acc.crd.vo.nc/RealInteBVO
 * Java type: nc.vo.crd.acc.realinte.realintebvo.RealInteBVODocument
 *
 * Automatically generated - do not modify.
 */
package nc.vo.crd.acc.realinte.realintebvo.impl;
/**
 * A document containing one RealInteBVO(@http://realinte.acc.crd.vo.nc/RealInteBVO) element.
 *
 * This is a complex type.
 */
public class RealInteBVODocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements nc.vo.crd.acc.realinte.realintebvo.RealInteBVODocument
{
    
    public RealInteBVODocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName REALINTEBVO$0 = 
        new javax.xml.namespace.QName("http://realinte.acc.crd.vo.nc/RealInteBVO", "RealInteBVO");
    
    
    /**
     * Gets the "RealInteBVO" element
     */
    public nc.vo.crd.acc.realinte.realintebvo.RealInteBVO getRealInteBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realinte.realintebvo.RealInteBVO target = null;
            target = (nc.vo.crd.acc.realinte.realintebvo.RealInteBVO)get_store().find_element_user(REALINTEBVO$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "RealInteBVO" element
     */
    public void setRealInteBVO(nc.vo.crd.acc.realinte.realintebvo.RealInteBVO realInteBVO)
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realinte.realintebvo.RealInteBVO target = null;
            target = (nc.vo.crd.acc.realinte.realintebvo.RealInteBVO)get_store().find_element_user(REALINTEBVO$0, 0);
            if (target == null)
            {
                target = (nc.vo.crd.acc.realinte.realintebvo.RealInteBVO)get_store().add_element_user(REALINTEBVO$0);
            }
            target.set(realInteBVO);
        }
    }
    
    /**
     * Appends and returns a new empty "RealInteBVO" element
     */
    public nc.vo.crd.acc.realinte.realintebvo.RealInteBVO addNewRealInteBVO()
    {
        synchronized (monitor())
        {
            check_orphaned();
            nc.vo.crd.acc.realinte.realintebvo.RealInteBVO target = null;
            target = (nc.vo.crd.acc.realinte.realintebvo.RealInteBVO)get_store().add_element_user(REALINTEBVO$0);
            return target;
        }
    }
}
