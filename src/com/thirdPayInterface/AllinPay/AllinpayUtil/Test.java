package com.thirdPayInterface.AllinPay.AllinpayUtil;

import com.aipg.common.InfoReq;
import com.aipg.common.XStreamEx;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Test {
	public static void main(String[] args) {

		
		XStream xstream = new XStreamEx(new DomDriver());
		xstream.alias("AIPG", Q_AIPG.class);
		xstream.alias("INFO", InfoReq.class);
		xstream.alias("QTRANSREQ", QUERY_TRANS.class);
		Q_AIPG g = new Q_AIPG( );
		InfoReq info = new InfoReq( );
		info.setTRX_CODE("200004");
		info.setVERSION("04");
		info.setDATA_TYPE("2");
		info.setREQ_SN("200604000000445-1428404873672");
		info.setUSER_NAME("20060400000044502");
		info.setUSER_PASS("111111");
		info.setLEVEL("5");
		g.setINFO(info);
		
		Q_Body body = new Q_Body( );
		QUERY_TRANS ret_detail=new QUERY_TRANS();
		ret_detail.setMERCHANT_ID("200604000000445");
		ret_detail.setQUERY_SN("200604000000445-1428404873672");
		ret_detail.setSTATUS("2");
		ret_detail.setTYPE("1");
        body.setQTRANSREQ(ret_detail);
		
		g.setBODY(body);
		System.out.println(xstream.toXML(g).replaceAll("__", "_"));
		
		
	}
}
