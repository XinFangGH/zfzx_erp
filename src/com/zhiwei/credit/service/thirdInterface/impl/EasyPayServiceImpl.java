package com.zhiwei.credit.service.thirdInterface.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.ArrayUtils;
import org.jfree.util.Log;

import sun.misc.BASE64Encoder;


import com.zhiwei.core.Constants;
import com.zhiwei.core.util.XmlUtil;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.dao.thirdInterface.WebBankcardDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.thirdInterface.EasyPay;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.system.SysConfigService;
import com.zhiwei.credit.service.thirdInterface.EasyPayService;
import com.zhiwei.credit.util.Md5Encrypt;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.util.UrlUtils;
import com.zhiwei.credit.util.WebClient;
import org.dom4j.Document;
import org.dom4j.Node;




public class EasyPayServiceImpl implements EasyPayService {
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private BpCustMemberDao bpCustMemberDao;
	@Resource
	private WebBankcardDao webBankcardDao;
	/**
	 * 生成易生支付的公共参数
	 * @param easypay
	 * @param service
	 * @param tradechannel
	 * @return
	 */
	private EasyPay generatePublicData(EasyPay easypay) {
		// TODO Auto-generated method stub
		//获取合作者身份Id
		String  partner =sysConfigService.findByKey("partnerEasyPay").getDataValue();
		//交易安全检验码，由数字和字母组成的32位字符串
		String key=sysConfigService.findByKey("easyPayKey").getDataValue();
		//访问模式
		String transport=sysConfigService.findByKey("easyPayTransport").getDataValue();
		easypay.setPartner(partner);
		easypay.setKey(key);
		easypay.setTransport(transport);
		easypay.set_input_charset(EasyPay.CHARSETGBK);
		easypay.setSign_type(EasyPay.SIGNTYPE);
		return easypay;
	}
	
	/** 
	 * 功能：生成签名结果
	 * @param sArray 要签名的数组
	 * @param key 安全校验码
	 * @return 签名结果字符串
	 */
	public  String BuildMysign(Map sArray, String key) {
		if(sArray!=null && sArray.size()>0){
			StringBuilder prestr = CreateLinkString(sArray);  //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
			//System.out.println("sign=="+Md5Encrypt.md5(prestr.append(key).toString()));
			String signData=prestr.append(key).toString();
			System.out.println("sign=="+signData);
			String sign=Md5Encrypt.md5(signData);
			System.out.println("sign=="+sign);
			return sign;
			//return Md5Encrypt.md5(prestr.append(key).toString());//把拼接后的字符串再与安全校验码直接连接起来,并且生成加密串
		}
		return null;
	}
	
	/** 
	 * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public  StringBuilder CreateLinkString(Map params){
			List keys = new ArrayList(params.keySet());
			Collections.sort(keys);
			StringBuilder prestr = new StringBuilder();
			String key="";
			String value="";
			for (int i = 0; i < keys.size(); i++) {
				key=(String) keys.get(i);
				value = (String) params.get(key);
				System.out.println(key+"=="+value);
				if("".equals(value) || value == null || 
						key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")){
					continue;
				}
				prestr.append(key).append("=").append(value).append("&");
			}
			return prestr.deleteCharAt(prestr.length()-1);
	}

	
	/**
	 * EasyPay代付接口
	 */
	@Override
	public String[] easyPayWithdraws(ObAccountDealInfo info,String basePath) {
		// TODO Auto-generated method stub
		String[] ret=new String[2];
		try{
			EasyPay easypay=new EasyPay();
			easypay=generateWithdrawsData(easypay);
			String[] dsfXm=getDsfXml(easypay,info);
			if(dsfXm[0].equals(Constants.CODE_SUCCESS)){
				String defXmRecode=encryption(dsfXm[1],AppUtil.getAppAbsolutePath());
				String url=sysConfigService.findByKey("easyPayfkgateURL").getDataValue();
				String[] res =this.sendData(url,defXmRecode,easypay);
				if(res[0].equals(Constants.CODE_SUCCESS)){
					System.out.println("res[1]=="+res[1]);
					Document doc=XmlUtil.stringToDocument(res[1]);
					String  systemNameNode = doc.selectSingleNode("/Resp/status").getText();
					if(systemNameNode.equals("succ")){
						Map<String,Object> map=new HashMap<String,Object>();
						map.put("investPersonId",info.getInvestPersonId());//投资人Id
						map.put("investPersonType",info.getInvestPersonType());//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
						map.put("transferType",ObAccountDealInfo.T_ENCHASHMENT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
						map.put("money",info.getPayMoney());//交易金额
						map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
						map.put("DealInfoId",info.getId());//交易记录id，没有默认为null
						map.put("recordNumber",info.getRecordNumber());//交易流水号
						map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
						String[] returnValue =obAccountDealInfoService.updateAcountInfo(map);

						//String[] returnValue=obAccountDealInfoService.updateAcountInfo(info.getInvestPersonId(), ObAccountDealInfo.T_ENCHASHMENT.toString(), info.getPayMoney().toString(), info.getInvestPersonType().toString(),info.getRecordNumber() , null, "2");
						ret=returnValue;
					}else{
						String  systemNameReson = doc.selectSingleNode("/Resp/reason").getText();
						System.out.println("systemNameReson=="+systemNameReson);
						ret[0]=Constants.CODE_FAILED;
						ret[1]="取现失败原因-"+systemNameReson;
					}
				}else{
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("investPersonId",info.getInvestPersonId());//投资人Id
					map.put("investPersonType",info.getInvestPersonType());//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
					map.put("transferType",ObAccountDealInfo.T_ENCHASHMENT);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
					map.put("money",info.getPayMoney());//交易金额
					map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
					map.put("DealInfoId",info.getId());//交易记录id，没有默认为null
					map.put("recordNumber",info.getRecordNumber());//交易流水号
					map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_3);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)
					String[] returnValue =obAccountDealInfoService.updateAcountInfo(map);
					//String[] returnValue=obAccountDealInfoService.updateAcountInfo(info.getInvestPersonId(), ObAccountDealInfo.T_ENCHASHMENT.toString(), info.getPayMoney().toString(), info.getInvestPersonType().toString(),info.getRecordNumber() , null, "3");
					ret=res;
					ret[1]=ret[1]+returnValue[1];
				}
			}else{
				ret=dsfXm;
			}
		}catch(Exception e){
			System.out.println("系统错误，请联系管理员");
			e.printStackTrace();
			ret[0]=Constants.CODE_FAILED;
			ret[1]="系统错误，请联系管理员";
		}
		
		return ret;
	}
	/**
	 * 生成取现必须参数
	 * @param easypay
	 * @return
	 */
	public  EasyPay generateWithdrawsData(EasyPay easypay) {
		// TODO Auto-generated method stub
		easypay=generatePublicData(easypay);
		easypay.setTransCode(EasyPay.AGENCYPAY);
		String easyPayBatchVersion=sysConfigService.findByKey("easyPayBatchVersion").getDataValue();
		easypay.setVersion(easyPayBatchVersion);
		return easypay;
	}
	
	/**
	 * 申请代付交易报文
	 * @param items
	 * @param tradeBatchNo
	 * @param transCode
	 * @return
	 */
	public  String[] getDsfXml(EasyPay easypay,ObAccountDealInfo info){
		String[] ret=new String[2];
		if(info.getInvestPersonType().compareTo(ObSystemAccount.type0)==0){
			BpCustMember bp=bpCustMemberDao.get(info.getInvestPersonId());
			WebBankcard card=webBankcardDao.get(info.getBankId());
			if(bp!=null&&card!=null){
				SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
				SimpleDateFormat sfDate = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sfTime = new SimpleDateFormat("hhmmssSSS");
				Date now = new Date();
				StringBuilder sb = new StringBuilder();
				sb.append("<?xml version='1.0' encoding='GBK'?>");
				sb.append("<EasyPay>");
					sb.append("<ST>");
						sb.append("<pub>");
							sb.append("<TransCode>").append(easypay.getTransCode()).append("</TransCode>");
							sb.append("<CustomerNo>").append(easypay.getPartner()).append("</CustomerNo>");
							sb.append("<CustomerKey>").append(easypay.getKey()).append("</CustomerKey>");
							sb.append("<BatchVersion>").append(easypay.getVersion()).append("</BatchVersion>");
							sb.append("<TranDate>").append(sfDate.format(now)).append("</TranDate>");
							sb.append("<TranTime>").append(sfTime.format(now)).append("</TranTime>");
							sb.append("<fSeqno>").append(info.getRecordNumber()).append("</fSeqno>");
		                sb.append("</pub>");
		                sb.append("<in>");
		                	sb.append("<BatchCount>1</BatchCount>");
							sb.append("<TradeBatchNo>").append(info.getRecordNumber()).append("</TradeBatchNo>");
							sb.append("<BatchAmount>").append(info.getPayMoney()).append("</BatchAmount>");
							sb.append("<SignTime>").append(sf.format(now)).append("</SignTime>");
							sb.append("<ReqReserved></ReqReserved>");
								sb.append("<rd>");
								sb.append("<TradeNum>1</TradeNum>");
								sb.append("<TradeCardnum>").append(card.getCardNum()).append("</TradeCardnum>");
								sb.append("<TradeCardname>").append(card.getUsername()).append("</TradeCardname>");
								sb.append("<TradeAccountname>").append(card.getAccountname()).append("</TradeAccountname>");
								sb.append("<TradeBranchbank>").append(card.getBranchbank()).append("</TradeBranchbank>");
								sb.append("<TradeSubbranchbank>").append(card.getSubbranchbank()).append("</TradeSubbranchbank>");
								sb.append("<TradeAccounttype>").append(card.getAccounttype()).append("</TradeAccounttype>");
								sb.append("<TradeAmount>").append(info.getPayMoney()).append("</TradeAmount>");
								sb.append("<TradeAmounttype>").append("CNY").append("</TradeAmounttype>");
								sb.append("<TradeMobile>").append(bp.getTelphone()).append("</TradeMobile>");
								sb.append("<CertificateType>").append(bp.getCardtype()).append("</CertificateType>");
								sb.append("<CertificateNum>").append(bp.getCardcode()).append("</CertificateNum>");
								sb.append("<ContractUsercode>").append("1111").append("</ContractUsercode>");
								sb.append("<TradeCustorder>").append(info.getRecordNumber()).append("</TradeCustorder>");
								sb.append("</rd>");
						
						sb.append("</in>");
					sb.append("</ST>");
				sb.append("</EasyPay>");
				ret[0]=Constants.CODE_SUCCESS;
				ret[1]=sb.toString();
				System.out.println("ret[1]===="+ret[1]);
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="投资人或取现银行卡不存在！";
			}
		}else{
			ret[0]=Constants.CODE_FAILED;
			ret[1]="交易记录不属于线上交易记录";
		}
		return ret;
	}

	/**
	 * 加密
	 * @param content
	 * @param pa
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws CertificateException
	 * @throws FileNotFoundException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static String encryption(String content, String pa)
			throws UnsupportedEncodingException, CertificateException,
			FileNotFoundException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] msg = content.getBytes("GBK"); // 待加解密的消息

		// 用证书的公钥加密
		CertificateFactory cff = CertificateFactory.getInstance("X.509");
		String path=(pa+"WEB-INF\\key\\tomcat.cer" ).replace("\\", "/");
		System.out.println("path==="+path);
		System.out.println("pa==="+pa);
		FileInputStream fis1 = new FileInputStream(path); // 证书文件
		
		Certificate cf = cff.generateCertificate(fis1);
		PublicKey pk1 = cf.getPublicKey(); // 得到证书文件携带的公钥
		Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // 定义算法：RSA
		byte[] dataReturn = null;
		c1.init(Cipher.PUBLIC_KEY, pk1);
		// StringBuilder sb = new StringBuilder();
		for (int i = 0; i < msg.length; i += 100) {
			byte[] doFinal = c1.doFinal(ArrayUtils.subarray(msg, i, i + 100));

			// sb.append(new String(doFinal,"gbk"));
			dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
		}

		BASE64Encoder encoder = new BASE64Encoder();

		String afjmText = encoder.encode(dataReturn);

		return afjmText;
	}
	/**
	 * 向易生支付发起请求
	 * @param url
	 * @param content
	 * @param easypay
	 * @return
	 */
	public String[] sendData(String url,String content,EasyPay easypay){
		 String[] ret=new String[2];
		 String strResp = "";
		 System.out.println("url==="+url);
		 PostMethod postMethod = new PostMethod(url);
		 try{
			 HttpClient httpClient = new HttpClient();
	        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(300*1000);
	        httpClient.getHttpConnectionManager().getParams().setSoTimeout(300*1000);
	      
	        httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
	        
	        Map<String,String> sPara = new HashMap<String,String>();
	        sPara.put("batchBizid",easypay.getPartner());
	        sPara.put("batchVersion","01");
	        sPara.put("batchContent",content);
	        Map<String,String> sParaNew = this.ParaFilter(sPara); //除去数组中的空值和签名参数
	        String sign = this.BuildMysign(sParaNew, easypay.getKey());//生成签名结果
	        NameValuePair[] param = {new NameValuePair("sign",sign),
	        		new NameValuePair("signType",easypay.getSign_type()),
	        		new NameValuePair("batchBizid",easypay.getPartner()),
	        		new NameValuePair("batchVersion","01"),
	        		new NameValuePair("batchContent",content)};
	        postMethod.setRequestBody(param);
        	httpClient.executeMethod(postMethod);
            byte[] responseBody = postMethod.getResponseBody();
            strResp = new String(responseBody);
            ret[0]=Constants.CODE_SUCCESS;
            ret[1]=strResp;
 //       	strResp =  postMethod.getResponseBodyAsString();
        }catch(Exception e){
        	strResp = e.getMessage();
        	ret[0]=Constants.CODE_FAILED;
            ret[1]=strResp;
        }finally{
        	postMethod.releaseConnection();
        }
		return ret;
		
	}
	
	/**
	 * 功能：除去数组中的空值和签名参数
	 * 
	 * @param sArray
	 *            签名参数组
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public Map<String,String> ParaFilter(Map<String,String> sArray) {
		List<String> keys = new ArrayList<String>(sArray.keySet());
		Map<String,String> sArrayNew = new HashMap<String,String>();
		for (int i = 0; i < keys.size(); i++) {
			String key =  keys.get(i);
			String value = sArray.get(key);
			/*
			 * if(value.equals("") || value == null ||
			 * key.equalsIgnoreCase("sign") ||
			 * key.equalsIgnoreCase("sign_type")){//新增notifyid不参加签名,只做标识用
			 * continue; }
			 */

			sArrayNew.put(key, value);
		}

		return sArrayNew;
	}
}