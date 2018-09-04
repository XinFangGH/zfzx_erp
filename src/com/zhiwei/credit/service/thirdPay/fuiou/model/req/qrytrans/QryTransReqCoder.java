package com.zhiwei.credit.service.thirdPay.fuiou.model.req.qrytrans;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class QryTransReqCoder{
    private static String CHARSET = "UTF-8";
    private static JAXBContext jaxbContext;

    static{
        try{
            jaxbContext = JAXBContext.newInstance("com.zhiwei.credit.service.thirdPay.fuiou.model.req.qrytrans");
        }catch(JAXBException e){
            // this is a deployment error
            throw new Error(e);
        }
    }

    public static String marshal(QryTransReqType qryTransReqType) throws JAXBException{
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, CHARSET);
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, new Boolean(false));
        JAXBElement<QryTransReqType> jaxbElement = (new ObjectFactory()).createQrytransreq(qryTransReqType);
        StringWriter sw = new StringWriter();
        m.marshal(jaxbElement, sw);
        return sw.toString();
    }

    public static QryTransReqType unmarshal(String xml) throws JAXBException{
        Unmarshaller u = jaxbContext.createUnmarshaller();
        StringReader sw = new StringReader(xml);
        JAXBElement<?> element = (JAXBElement<?>) u.unmarshal(sw);
        QryTransReqType qryTransReqType = (QryTransReqType) element.getValue();
        return qryTransReqType;
    }
}
