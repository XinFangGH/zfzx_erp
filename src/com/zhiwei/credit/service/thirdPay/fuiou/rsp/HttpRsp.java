package com.zhiwei.credit.service.thirdPay.fuiou.rsp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.zhiwei.credit.service.thirdPay.fuiou.model.ntfrsp.incomefor.InComeForNtfRspCoder;
import com.zhiwei.credit.service.thirdPay.fuiou.model.ntfrsp.incomefor.InComeForNtfRspType;
import com.zhiwei.credit.service.thirdPay.fuiou.model.ntfrsp.payfor.PayForNtfRspCoder;
import com.zhiwei.credit.service.thirdPay.fuiou.model.ntfrsp.payfor.PayForNtfRspType;
import com.zhiwei.credit.service.thirdPay.fuiou.model.ntfrsp.verify.VerifyNtfRspCoder;
import com.zhiwei.credit.service.thirdPay.fuiou.model.ntfrsp.verify.VerifyNtfRspType;
import com.zhiwei.credit.service.thirdPay.fuiou.model.rsp.refundntf.ReFundntfRspCoder;
import com.zhiwei.credit.service.thirdPay.fuiou.model.rsp.refundntf.ReFundntfRspType;
import com.zhiwei.credit.service.thirdPay.fuiou.model.rsp.refundresendntf.ReFundResendntfRspCoder;
import com.zhiwei.credit.service.thirdPay.fuiou.model.rsp.refundresendntf.ReFundResendntfRspType;
import com.zhiwei.credit.service.thirdPay.fuiou.model.rsp.returnticketntf.ReturnTicketntfRspCoder;
import com.zhiwei.credit.service.thirdPay.fuiou.model.rsp.returnticketntf.ReturnTicketntfRspType;
import com.zhiwei.credit.service.thirdPay.fuiou.model.rsp.rpayforntf.RpayForNtfRspCoder;
import com.zhiwei.credit.service.thirdPay.fuiou.model.rsp.rpayforntf.RpayForNtfRspType;



/**
 * Servlet implementation class HttpRsp
 */
public class HttpRsp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public HttpRsp() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			String reqtype = request.getParameter("reqtype");
			String merid = request.getParameter("merid");
			String xml = request.getParameter("xml");
			String mac = request.getParameter("mac");
			String macSource = merid+"|"+"123456"+"|"+reqtype+"|"+xml;
			String macVerify = DigestUtils.md5Hex(macSource.getBytes("UTF-8"));
			String rspXml="";
			PrintWriter printWriter = null;
			
			if("payforntf".equals(reqtype)){
				PayForNtfRspType payForNtfRspType=new PayForNtfRspType();
				payForNtfRspType.setMemo("成功");
				payForNtfRspType.setRet("000000");
				rspXml=PayForNtfRspCoder.marshal(payForNtfRspType);	
			}else if("incomeforntf".equals(reqtype)){
				InComeForNtfRspType inComeForNtfRspType=new InComeForNtfRspType();
				inComeForNtfRspType.setMemo("成功");
				inComeForNtfRspType.setRet("000000");
				rspXml=InComeForNtfRspCoder.marshal(inComeForNtfRspType);
			}else if("verifyntf".equals(reqtype)){
				VerifyNtfRspType verifyNtfRspType=new VerifyNtfRspType();
				verifyNtfRspType.setMemo("成功");
				verifyNtfRspType.setRet("000000");
				rspXml=VerifyNtfRspCoder.marshal(verifyNtfRspType);
			}else if("rpayforntf".equals(reqtype)){
				RpayForNtfRspType rpayForNtfRspType=new RpayForNtfRspType();
				rpayForNtfRspType.setMemo("成功");
				rpayForNtfRspType.setRet("000000");
				rspXml=RpayForNtfRspCoder.marshal(rpayForNtfRspType);
			}else if("returnticketntf".equals(reqtype)){
				ReturnTicketntfRspType returnTicketntfRspType=new ReturnTicketntfRspType();
				returnTicketntfRspType.setMemo("成功");
				returnTicketntfRspType.setRet("000000");
				rspXml=ReturnTicketntfRspCoder.marshal(returnTicketntfRspType);
			}else if("refundresendntf".equals(reqtype)){
				ReFundResendntfRspType reFundResendntfRspType=new ReFundResendntfRspType();
				reFundResendntfRspType.setMemo("成功");
				reFundResendntfRspType.setRet("000000");
				rspXml=ReFundResendntfRspCoder.marshal(reFundResendntfRspType);
			}else if("refundntf".equals(reqtype)){
				ReFundntfRspType reFundntfRspType=new ReFundntfRspType();
				reFundntfRspType.setMemo("成功");
				reFundntfRspType.setRet("000000");
				rspXml=ReFundntfRspCoder.marshal(reFundntfRspType);
			}
			
			printWriter = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
		    printWriter.write(rspXml);
		    printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
