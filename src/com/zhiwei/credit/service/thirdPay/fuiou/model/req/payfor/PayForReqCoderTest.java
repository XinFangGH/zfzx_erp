package com.zhiwei.credit.service.thirdPay.fuiou.model.req.payfor;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

public class PayForReqCoderTest{

    @Before
    public void setUp() throws Exception{
    }

    @Test
    public void testMarshal(){
        PayForReqType payForReqType = new PayForReqType();
        payForReqType.setAccntnm("yangliehui");
        payForReqType.setAccntno("11111111111");
        try{
           String xml = PayForReqCoder.marshal(payForReqType);
           System.out.println(xml);
        }catch(JAXBException e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testUnmarshal(){
       String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><payforreq><accntno>11111111111</accntno><accntnm>yangliehui</accntnm></payforreq>";
        try{
        	PayForReqType payForReqType = PayForReqCoder.unmarshal(xml);
            assertNotNull(payForReqType);
        }catch(JAXBException e){
            e.printStackTrace();
        }
    }

}
