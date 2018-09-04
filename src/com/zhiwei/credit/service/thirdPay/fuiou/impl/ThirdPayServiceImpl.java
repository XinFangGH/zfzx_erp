package com.zhiwei.credit.service.thirdPay.fuiou.impl;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.codec.digest.DigestUtils;

import com.zhiwei.credit.service.thirdPay.fuiou.ThirdPayService;
import com.zhiwei.credit.service.thirdPay.fuiou.model.req.incomefor.InComeForReqCoder;
import com.zhiwei.credit.service.thirdPay.fuiou.model.req.incomefor.InComeForReqType;
import com.zhiwei.credit.service.thirdPay.fuiou.model.req.payfor.PayForReqCoder;
import com.zhiwei.credit.service.thirdPay.fuiou.model.req.payfor.PayForReqType;
import com.zhiwei.credit.service.thirdPay.fuiou.util.HttpClientHelper;

public class ThirdPayServiceImpl implements ThirdPayService {

	@Override
	public String directPay(String merId ,String password,String URL,PayForReqType payForReqType,String charSet,String timeOut) {
		String outStr = null;
		String reqType = "payforreq";
		if(charSet==null||charSet.equals(""))
		{
			charSet="utf-8";
		}
		if(timeOut==null||timeOut.equals(""))
		{
			timeOut="1200000";
		}
		try {
			String url = URL+"/req.do";
			List<String[]> list = new ArrayList<String[]>();
			String xml = "";
			try {
				xml = PayForReqCoder.marshal(payForReqType);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			String[] nv1 = new String[] { "xml", xml };
			list.add(nv1);
			String[] nv2 = new String[] {"merid",merId}; //商户号
			list.add(nv2);
			String[] nv3 = new String[] {"reqtype",reqType};
			list.add(nv3);
			String macSource = merId+"|"+password+"|"+reqType+"|"+xml;
			String macVerify = DigestUtils.md5Hex(macSource.getBytes("UTF-8"));
			String[] nv4 = new String[]{"mac",macVerify};
			list.add(nv4);
			String nvPairs = HttpClientHelper.getNvPairs(list, charSet);
			 outStr = HttpClientHelper.doHttp(url, HttpClientHelper.POST,charSet, nvPairs, timeOut);
			System.out.println(outStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return outStr;
	}

	@Override
	public String loan(String merId ,String password,String URL,InComeForReqType inComeForReqType,String charSet,String timeOut) {

		String outStr = null;
		String reqType = "sincomeforreq";
		if(charSet==null||charSet.equals(""))
		{
			charSet="utf-8";
		}
		if(timeOut==null||timeOut.equals(""))
		{
			timeOut="1200000";
		}
		try {
			String url = URL+"/req.do";
			List<String[]> list = new ArrayList<String[]>();
			String xml = "";
			try {
				xml = InComeForReqCoder.marshal(inComeForReqType);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			String[] nv1 = new String[] { "xml", xml };
			list.add(nv1);
			String[] nv2 = new String[] {"merid",merId}; //商户号
			list.add(nv2);
			String[] nv3 = new String[] {"reqtype",reqType};
			list.add(nv3);
			String macSource = merId+"|"+password+"|"+reqType+"|"+xml;
			String macVerify = DigestUtils.md5Hex(macSource.getBytes("UTF-8"));
			String[] nv4 = new String[]{"mac",macVerify};
			list.add(nv4);
			String nvPairs = HttpClientHelper.getNvPairs(list, charSet);
			 outStr = HttpClientHelper.doHttp(url, HttpClientHelper.POST,charSet, nvPairs, timeOut);
			System.out.println(outStr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return outStr;
	
	}

}
