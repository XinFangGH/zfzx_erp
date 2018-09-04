package com.thirdPayInterface.AllinPay;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import java.lang.reflect.Field;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.aipg.common.AipgReq;
import com.aipg.common.AipgRsp;
import com.aipg.common.InfoReq;
import com.aipg.common.XSUtil;
import com.aipg.common.XStreamEx;
import com.aipg.payreq.Body;
import com.aipg.payreq.Trans_Detail;
import com.aipg.payreq.Trans_Sum;
import com.aipg.rtrsp.TransRet;
import com.aipg.singleacctvalid.ValidRet;
import com.aipg.transquery.QTDetail;
import com.aipg.transquery.QTransRsp;
import com.allinpay.XmlTools;
import com.allinpay.ets.client.PaymentResult;
import com.allinpay.ets.client.RequestOrder;
import com.zhiwei.core.util.AppUtil;
import com.thirdPayInterface.AllinPay.AllinpayUtil.QUERY_TRANS;
import com.thirdPayInterface.AllinPay.AllinpayUtil.Q_AIPG;
import com.thirdPayInterface.CommonRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayWebClient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class AllinPayInterfaceUtil {
	private static Log logger=LogFactory.getLog(AllinPayInterfaceUtil.class);
	 /**
     * 获取系统配置参数
     */
	private static Map configMap = AppUtil.getConfigMap();
	/**
	 * 第三方支付环境（正式环境和测试环境）
	 */
	private static String  thirdPayEnvironmentType=configMap.containsKey("thirdPayEnvironmentType")?configMap.get("thirdPayEnvironmentType").toString().trim():ThirdPayConstants.THIRDENVIRONMENT1;
	/**
	 * 获取双乾支付的第三方支付环境参数
	 * @return
	 */
	public static Map allinPayProperty(){
		Map allinPayConfigMap=new HashMap();
		try{
			InputStream in=null;
			//获取当前支付环境为正式环境还是测试环境
			if(thirdPayEnvironmentType.equals(ThirdPayConstants.THIRDENVIRONMENT0)){//正式环境
		       in = AllinPayInterfaceUtil.class.getResourceAsStream("AllinPayNormalEnvironment.properties"); 
			}else{
		        in = AllinPayInterfaceUtil.class.getResourceAsStream("AllinPayTestEnvironment.properties"); 
			}
			Properties props =  new  Properties(); 
			if(in!=null){
				props.load(in);
		    	Iterator it= props.keySet().iterator();
		    	while(it.hasNext()){
		    		String key=(String)it.next();
		    		allinPayConfigMap.put(key, props.getProperty(key));
		    	}
			}
		}catch(Exception ex){
			ex.printStackTrace();
    		logger.error(ex.getMessage());
    	}
		return allinPayConfigMap;
	}
	/**
	 * 获取通用参数
	 * @param returnURL
	 * @param notifyURL
	 * @param randomStamp
	 * @return
	 */
	public static RequestOrder generatePublicData(boolean returnURL, boolean notifyURL,boolean isMobile) {
		// TODO Auto-generated method stub
		Map thirdPayConfig=allinPayProperty();
		if(thirdPayConfig!=null){
			RequestOrder requestOrder = new RequestOrder();
			//商户号
			String merchantId = thirdPayConfig.get("merchantId").toString();
			//网关接收支付请求接口版本
			String version = thirdPayConfig.get("version").toString();
			//密钥
			String key = thirdPayConfig.get("key").toString();
			//支付方式
			String payType = thirdPayConfig.get("payType").toString();
			
			requestOrder.setPayType(Integer.valueOf(payType));
			requestOrder.setVersion(version);
			requestOrder.setMerchantId(merchantId);
			requestOrder.setKey(key); //key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。
			
			String BasePath=ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath() + "/";
			if(returnURL){
				if(isMobile){
					requestOrder.setPickupUrl(BasePath +thirdPayConfig.get("thirdPay_pageCallUrl").toString().trim());
				}else{
					requestOrder.setPickupUrl(BasePath +thirdPayConfig.get("thirdPay_pageCallUrl").toString().trim());
				}
			}
			if(notifyURL){
				requestOrder.setReceiveUrl(BasePath +thirdPayConfig.get("thirdPay_notifyUrl").toString().trim());
			}
			return requestOrder;
		}else{
			return null;
		}
	}

	/**
	 * 充值
	 */
	public static CommonResponse recharge(CommonRequst commonRequst) {
		CommonResponse response = new CommonResponse();
		try {
			Map thirdPayConfig=allinPayProperty();
			RequestOrder requestOrder = generatePublicData(true,true,false);
			if(requestOrder!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String orderDatetime = sdf.format(new Date());
				requestOrder.setOrderNo(commonRequst.getRequsetNo());
				requestOrder.setOrderAmount(Long.valueOf(commonRequst.getAmount().multiply(new BigDecimal("100")).setScale(0).toString()));//整型数字，金额与币种有关如果是人民币，则单位是分，即10元提交时金额应为1000
				requestOrder.setPayerName(commonRequst.getTrueName());
				requestOrder.setOrderDatetime(orderDatetime);//商户订单提交时间
//				requestOrder.setExt1(commonRequst.getLogin_id());//扩展字段，支付完成后，按照原样返回给商户
				requestOrder.setInputCharset(1);//1代表UTF-8、2代表GBK、3代表GB2312；
				requestOrder.setLanguage(1);//1代表简体中文、2代表繁体中文、3代表英文
				requestOrder.setSignType(1);//0表示订单上送和交易结果通知都使用MD5进行签名1表示商户用使用MD5算法验签上送订单，通联交易结果通知使用证书签名Asp商户不使用通联dll文件签名验签的商户填0
				requestOrder.setOrderCurrency("0");//订单金额币种类型
				requestOrder.setOrderExpireDatetime("60");//订单过期时间
				requestOrder.setProductName("充值");
				requestOrder.setPayerName(commonRequst.getTrueName());
				//String strSrcMsg = requestOrder.getSrc(); // 此方法用于debug，测试通过后可注释。
				String strSignMsg = requestOrder.doSign(); // 签名，设为signMsg字段值。
				requestOrder.setSignMsg(strSignMsg);
				// 生成注册map
				Map<String, String> paramss = ConvertObjToMap(requestOrder);
				String [] rett=ThirdPayWebClient.operateParameter(thirdPayConfig.get("payUrl").toString(), paramss,"utf-8");
				if(rett!=null&&ThirdPayConstants.RECOD_SUCCESS.equals(rett[0])){
					response.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
					response.setResponseMsg("充值申请成功");
				}else{
					response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
					response.setResponseMsg("充值申请失败");
				}
			}else{
				response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
				response.setResponseMsg("充值基本参数获取失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			response.setResponseMsg("充值对接失败");
		}
		
		return response;
		
	}
	/**
	 * 提现
	 */
	public static CommonResponse withdraws(CommonRequst commonRequst) {
		CommonResponse response = new CommonResponse();
		Map thirdPayConfig=allinPayProperty();
		//获取第三方环境路径
		String URL11https= thirdPayConfig.get("DF_payUrl").toString();
		//批量代付交易代码
		String trxCode= thirdPayConfig.get("DF_trxCode").toString();
		//业务代码
		String DF_businessCode = thirdPayConfig.get("DF_businessCode").toString();
		//获取代付商户号 
		String DF_merchantId = thirdPayConfig.get("DF_merchantId").toString();
		//交易批次号
		String reqSn=CommonRequst.HRY+commonRequst.getQueryRequsetNo();
		boolean isfront=false;//是否发送至前置机（由前置机进行签名）
		//设置安全提供者,注意，这一步尤为重要
//		BouncyCastleProvider provider = new BouncyCastleProvider();
//		XmlTools.initProvider(provider);
		String xml="";
		AipgReq aipg=new AipgReq();
		// 组装报文头部
		InfoReq info=makeReq(trxCode,reqSn,thirdPayConfig);
		aipg.setINFO(info);
		Body body = new Body() ;
		//组装BODY/TRANS_SUM
		Trans_Sum trans_sum = new Trans_Sum() ;
		trans_sum.setBUSINESS_CODE(DF_businessCode) ;
		trans_sum.setMERCHANT_ID(DF_merchantId) ;
		//总记录数
		trans_sum.setTOTAL_ITEM("1") ;
		//总金额,整数，单位分
		trans_sum.setTOTAL_SUM(commonRequst.getAmount().multiply(new BigDecimal("100")).setScale(0).toString()) ;
		body.setTRANS_SUM(trans_sum);
		List <Trans_Detail>transList = new ArrayList<Trans_Detail>() ;
		Trans_Detail trans_detail = new Trans_Detail() ;
		//记录序号
		trans_detail.setSN("0001") ;
		//账号名.银行卡或存折上的所有人姓名
    	trans_detail.setACCOUNT_NAME(commonRequst.getTrueName()) ;
    	//账号属性,0私人，1公司。不填时，默认为私人0。
 		trans_detail.setACCOUNT_PROP("0") ;
 		//银行卡或存折号码
		trans_detail.setACCOUNT_NO(commonRequst.getBankCardNumber()) ;
		//银行代码
		trans_detail.setBANK_CODE(commonRequst.getBankCode()) ;
		trans_detail.setAMOUNT(commonRequst.getAmount().multiply(new BigDecimal("100")).setScale(0).toString()) ;
		//货币类型,人民币：CNY, 港元：HKD，美元：USD。不填时，默认为人民币。
		trans_detail.setCURRENCY("CNY");
		
		transList.add(trans_detail) ;
        body.setDetails(transList) ;
        aipg.addTrx(body) ;
		 
        xml=XmlTools.buildXml(aipg,true);
        String result[] = new String[3];
        try {
        	result = dealRet(sendToTlt(xml,isfront,URL11https));
    		//0000表示第三方已经受理。但是不代表已经成功
    		if(result[0].equals("0000")){
    			response.setResponsecode(CommonResponse.RESPONSECODE_APPLAY);
    			response.setResponseMsg("提现申请成功");
    			//如果第三方没有返回最终结果通知，20秒后轮询一次
    			CommonRequst requst = new CommonRequst();
    			requst.setQueryRequsetNo(reqSn);
    			response = queryResult(requst);
    		}else{
    			response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
    			response.setResponseMsg("提现申请失败:"+result[1]);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			response.setResponseMsg("提现申请请求失败");
		}
		response.setLoanNo(reqSn);
		return response;
	}
	/**
	 * 批量代付 交易结果查询
	 * @param reqSn
	 * @param isfront
	 * @param URL11https
	 * @return
	 */
	public static CommonResponse queryResult(CommonRequst commonRequst){
		CommonResponse commonResponse = new CommonResponse();
		//获取代付商户号 
		Map thirdPayConfig=allinPayProperty();
		//获取第三方环境路径
		String URL11https = thirdPayConfig.get("DF_payUrl").toString();
		String DF_merchantId = thirdPayConfig.get("DF_merchantId").toString();
		String DF_userName = thirdPayConfig.get("DF_userName").toString();
		String DF_password = thirdPayConfig.get("DF_password").toString();
		String xml="";
		//封装xml格式
		XStream xstream = new XStreamEx(new DomDriver());
		xstream.alias("AIPG", Q_AIPG.class);
		xstream.alias("INFO", InfoReq.class);
		xstream.alias("QTRANSREQ", QUERY_TRANS.class);
		Q_AIPG g = new Q_AIPG( );
		InfoReq info = new InfoReq( );
		info.setTRX_CODE("200004");
		info.setVERSION("04");
		info.setDATA_TYPE("2");
		info.setREQ_SN(commonRequst.getQueryRequsetNo());
		info.setUSER_NAME(DF_userName);
		info.setUSER_PASS(DF_password);
		info.setLEVEL("5");
		g.setINFO(info);
		
		QUERY_TRANS ret_detail=new QUERY_TRANS();
		ret_detail.setMERCHANT_ID(DF_merchantId);
		ret_detail.setQUERY_SN(commonRequst.getQueryRequsetNo());
		ret_detail.setSTATUS("2");
		ret_detail.setTYPE("1");
		
		g.setQTRANSREQ(ret_detail);
		//查询处理返回结果
		xml=xstream.toXML(g).replaceAll("__", "_");
		String result[] = dealRet(sendToTlt(xml,false,URL11https));
		//返回结果集
		List<CommonRecord> record = new ArrayList<CommonRecord>();
		CommonRecord re = new CommonRecord();
		re.setStatus(result[0]);
		record.add(re);
		commonResponse.setRecordList(record);
		commonResponse.setResponsecode(result[0]);
		commonResponse.setResponseMsg(result[1]);
		return commonResponse;
	}
	/**
	 * 测试
	 * @param url
	 * @param isTLTFront
	 * @throws Exception
	 */
	public void batchDaiShou(String url,boolean isTLTFront) throws Exception {
		Map thirdPayConfig=allinPayProperty();
		String xml="";
		AipgReq aipg=new AipgReq();
		//批量代付交易代码
		String trxCode= AppUtil.getSysConfig().get("DF_trxCode").toString();
		//获取代付商户号 
		String DF_merchantId = AppUtil.getSysConfig().get("DF_merchantId").toString();
		String reqSn=CommonRequst.HRY+String.valueOf(System.currentTimeMillis());
		InfoReq info=makeReq(trxCode,reqSn,thirdPayConfig);
		aipg.setINFO(info);
		Body body = new Body() ;
		Trans_Sum trans_sum = new Trans_Sum() ;
		trans_sum.setBUSINESS_CODE("00600") ;
		trans_sum.setMERCHANT_ID(DF_merchantId) ;
		trans_sum.setTOTAL_ITEM("1") ;
		trans_sum.setTOTAL_SUM("500000") ;
		body.setTRANS_SUM(trans_sum) ;
		
		List <Trans_Detail>transList = new ArrayList<Trans_Detail>() ;
		Trans_Detail trans_detail = new Trans_Detail() ;
		Trans_Detail trans_detail2 = new Trans_Detail() ;
		Trans_Detail trans_detail3 = new Trans_Detail() ;
		Trans_Detail trans_detail4 = new Trans_Detail() ;
		trans_detail.setSN("0001") ;
    	trans_detail.setACCOUNT_NAME("曾浩") ;
 		trans_detail.setACCOUNT_PROP("0") ;
		trans_detail.setACCOUNT_NO("603023061216191772") ;
		trans_detail.setBANK_CODE("103") ;
		trans_detail.setAMOUNT("500000") ;
		trans_detail.setCURRENCY("CNY");
//		trans_detail.setSETTGROUPFLAG("xCHM");
//		trans_detail.setSUMMARY("分组清算");
//		trans_detail.setUNION_BANK("234234523523");
		transList.add(trans_detail) ;
		
		trans_detail2.setSN("0002") ;
		trans_detail2.setACCOUNT_NAME("系统对接测试02") ;
//		trans_detail.setACCOUNT_PROP("1") ;
		trans_detail2.setACCOUNT_NO("622682-0013800763464") ;
		trans_detail2.setBANK_CODE("103") ;
		trans_detail2.setAMOUNT("1") ;
		trans_detail2.setCURRENCY("CNY");
		trans_detail2.setUNION_BANK("234234523523");
//		trans_detail2.setSETTGROUPFLAG("CHM");
//		trans_detail2.setSUMMARY("分组清算");
		transList.add(trans_detail2);
		
		trans_detail3.setSN("0003") ;
		trans_detail3.setACCOUNT_NAME("系统对接测试03") ;
//		trans_detail.setACCOUNT_PROP("1") ;
		trans_detail3.setACCOUNT_NO("621034-32645-1271") ;
		trans_detail3.setBANK_CODE("103") ;
		trans_detail3.setAMOUNT("1") ;
		trans_detail3.setUNION_BANK("234234523523");
//		trans_detail3.setSETTGROUPFLAG("CHM");
//		trans_detail3.setSUMMARY("分组清算");
		transList.add(trans_detail3);
		
        body.setDetails(transList) ;
        aipg.addTrx(body) ;
		
        xml=XmlTools.buildXml(aipg,true);
		dealRet(sendToTlt(xml,isTLTFront,url));
	}
	/**
	 * 组装报文头部
	 * @param trxcod
	 * @return
	 *日期：Sep 9, 2012
	 */
	public static InfoReq makeReq(String trxcod,String reqSn,Map thirdPayConfig)
	{
		String DF_userName = thirdPayConfig.get("DF_userName").toString();
		String DF_password = thirdPayConfig.get("DF_password").toString();
		//版本
		String DF_VERSION = thirdPayConfig.get("DF_VERSION").toString();  
		InfoReq info=new InfoReq();
		info.setTRX_CODE(trxcod);
		info.setREQ_SN(reqSn);
		info.setUSER_NAME(DF_userName);
		info.setUSER_PASS(DF_password);
		info.setLEVEL("5");
		info.setDATA_TYPE("2");
		info.setVERSION(DF_VERSION);
		/*if("300000".equals(trxcod)||"300001".equals(trxcod)||"300003".equals(trxcod)){
			info.setMERCHANT_ID(tranxContants.merchantId);
		}*/
		return info;
	}
	/**
	 * 返回报文处理逻辑
	 * @param retXml
	 */
	public static String[] dealRet(String retXml){
		String[] result=new String[2];
		String trxcode = null;
		AipgRsp aipgrsp=null;
		//或者交易码
		if (retXml.indexOf("<TRX_CODE>") != -1)
		{
			int end = retXml.indexOf("</TRX_CODE>");
			int begin = end - 6;
			if (begin >= 0) trxcode = retXml.substring(begin, end);
		}
		aipgrsp=XSUtil.xmlRsp(retXml);
		
		//批量代收付返回处理逻辑
		if("100001".equals(trxcode)||"100002".equals(trxcode)||"211000".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
				result[0]=aipgrsp.getINFO().getRET_CODE();
				result[1]="受理成功";
				System.out.println("受理成功，请在20分钟后进行10/每次的轮询");
			}else{
				result[0]=aipgrsp.getINFO().getRET_CODE();
				result[1]=aipgrsp.getINFO().getERR_MSG();
				System.out.println("受理失败，失败原因："+aipgrsp.getINFO().getERR_MSG());
			}
		}
		//交易查询处理逻辑
		if("200004".equals(trxcode)||"200005".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
				QTransRsp qrsq=(QTransRsp) aipgrsp.getTrxData().get(0);
				List<QTDetail> details=qrsq.getDetails();
				for(QTDetail lobj:details){
					System.out.print("原支付交易批次号:"+lobj.getBATCHID()+"  ");
					System.out.print("记录序号:"+lobj.getSN()+"  ");
					System.out.print("账号:"+lobj.getACCOUNT_NO()+"  ");
					System.out.print("户名:"+lobj.getACCOUNT_NAME()+"  ");
					System.out.print("金额:"+lobj.getAMOUNT()+"  ");
					System.out.print("返回结果:"+lobj.getRET_CODE()+"  ");
					if("0000".equals(lobj.getRET_CODE())){
						result[0]=CommonResponse.RESPONSECODE_SUCCESS;
						result[1]="交易成功";
					}else if("3066".equals(lobj.getRET_CODE())){
						result[0]=CommonResponse.RESPONSECODE_FAILD;
						result[1]="此提现银行卡号暂不支持提现，请更换银行卡号!";
						System.out.println("返回说明："+lobj.getERR_MSG());
					}else{
						result[0]=CommonResponse.RESPONSECODE_FAILD;
						result[1]=lobj.getERR_MSG();
						System.out.println("返回说明:"+lobj.getERR_MSG()+"  ");
					}
					
				}
			}else if("2000".equals(aipgrsp.getINFO().getRET_CODE())
					||"2001".equals(aipgrsp.getINFO().getRET_CODE())
					||"2003".equals(aipgrsp.getINFO().getRET_CODE())
					||"2005".equals(aipgrsp.getINFO().getRET_CODE())
					||"2007".equals(aipgrsp.getINFO().getRET_CODE())
					||"2008".equals(aipgrsp.getINFO().getRET_CODE())){
				
				result[0]=result[0]=CommonResponse.RESPONSECODE_APPLAY;;
				result[1]=aipgrsp.getINFO().getERR_MSG();
				
				System.out.print("返回说明:"+aipgrsp.getINFO().getRET_CODE()+"  ");
				System.out.println("返回说明："+aipgrsp.getINFO().getERR_MSG());
				System.out.println("该状态时，说明整个批次的交易都在处理中");
			}else if("2004".equals(aipgrsp.getINFO().getRET_CODE())){
				result[0]=aipgrsp.getINFO().getRET_CODE();
				result[1]="整批交易未受理通过（最终失败）";
				result[0]=CommonResponse.RESPONSECODE_FAILD;
				System.out.println("整批交易未受理通过（最终失败）");
			}else if("1002".equals(aipgrsp.getINFO().getRET_CODE())){
				result[0]=aipgrsp.getINFO().getRET_CODE();
				result[1]="查询无结果集";
				result[0]=CommonResponse.RESPONSECODE_FAILD;
				System.out.println("查询无结果集");
			}else{
				result[0]=aipgrsp.getINFO().getRET_CODE();
				result[0]=CommonResponse.RESPONSECODE_APPLAY;
				result[1]="查询请求失败，请重新发起查询";
				System.out.println("查询请求失败，请重新发起查询");
			}
		}
		//实时交易结果返回处理逻辑(包括单笔实时代收，单笔实时代付，单笔实时身份验证)
		if("100011".equals(trxcode)||"100014".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("提交成功");
				TransRet ret=(TransRet) aipgrsp.getTrxData().get(0);
				System.out.println("交易结果："+ret.getRET_CODE()+":"+ret.getERR_MSG());
				if("0000".equals(ret.getRET_CODE())){
					System.out.println("交易成功（最终结果）");
				}else{
					System.out.println("交易失败（最终结果）");
					System.out.println("交易失败原因："+ret.getERR_MSG());
				}
			}else if("2000".equals(aipgrsp.getINFO().getRET_CODE())
					||"2001".equals(aipgrsp.getINFO().getRET_CODE())
					||"2003".equals(aipgrsp.getINFO().getRET_CODE())
					||"2005".equals(aipgrsp.getINFO().getRET_CODE())
					||"2007".equals(aipgrsp.getINFO().getRET_CODE())
					||"2008".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("交易处理中或者不确定状态，需要在稍后5分钟后进行交易结果查询（轮询）");
			}else if(aipgrsp.getINFO().getRET_CODE().startsWith("1")){
				String errormsg=aipgrsp.getINFO().getERR_MSG()==null?"连接异常，请重试":aipgrsp.getINFO().getERR_MSG();
				System.out.println("交易请求失败，原因："+errormsg);
			}else{
				TransRet ret=(TransRet) aipgrsp.getTrxData().get(0);
				System.out.println("交易失败(最终结果)，失败原因："+ret.getERR_MSG());
			}
		}
		//(单笔实时身份验证结果返回处理逻辑)
		if("211003".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("提交成功");
				ValidRet ret=(ValidRet) aipgrsp.getTrxData().get(0);
				System.out.println("交易结果："+ret.getRET_CODE()+":"+ret.getERR_MSG());
			}else if("2000".equals(aipgrsp.getINFO().getRET_CODE())
					||"2001".equals(aipgrsp.getINFO().getRET_CODE())
					||"2003".equals(aipgrsp.getINFO().getRET_CODE())
					||"2005".equals(aipgrsp.getINFO().getRET_CODE())
					||"2007".equals(aipgrsp.getINFO().getRET_CODE())
					||"2008".equals(aipgrsp.getINFO().getRET_CODE())){
				System.out.println("验证处理中或者不确定状态，需要在稍后5分钟后进行验证结果查询（轮询）");
			}else if(aipgrsp.getINFO().getRET_CODE().startsWith("1")){
				String errormsg=aipgrsp.getINFO().getERR_MSG()==null?"连接异常，请重试":aipgrsp.getINFO().getERR_MSG();
				System.out.println("验证请求失败，原因："+errormsg);
			}else{
				TransRet ret=(TransRet) aipgrsp.getTrxData().get(0);
				System.out.println("验证失败(最终结果)，失败原因："+ret.getERR_MSG());
			}
		}
		return result;
	}
	public String isFront(String xml,boolean flag,String url) {
		try{
			if(!flag){
				xml=this.signMsg(xml);
			}else{
				xml=xml.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
			}
			return sendXml(xml,url,flag);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public static String sendToTlt(String xml,boolean flag,String url) {
		try{
			if(!flag){
				xml=signMsg(xml);
			}else{
				xml=xml.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
			}
			return sendXml(xml,url,flag);
		}catch(Exception e){
			e.printStackTrace();
			if(e.getCause() instanceof ConnectException||e instanceof ConnectException){
				System.out.println("请求链接中断，请做交易结果查询，以确认上一笔交易是否已被通联受理，避免重复交易");
			}
		}
		return "请求链接中断，请做交易结果查询，以确认上一笔交易是否已被通联受理，避免重复交易";
	}
	/**
	 * 报文签名
	 * @param msg
	 * @return
	 *日期：Sep 9, 2012
	 * @throws Exception 
	 */
	public static String signMsg(String xml) throws Exception{
		Map thirdPayConfig=allinPayProperty();
		String pfxPassword = thirdPayConfig.get("pfxPassword").toString();
		String pfxPath=thirdPayConfig.get("pfxPath").toString();
		/*try {
			 configPath = java.net.URLDecoder.decode(this.getClass().getResource("/").getPath(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  */
		pfxPath=AppUtil.getAppAbsolutePath()+pfxPath;
		String pfxcerUrl = pfxPath.replace("\\", "/");
		xml=XmlTools.signMsg(xml, pfxcerUrl,pfxPassword, false);
		return xml;
	}
	public static String sendXml(String xml,String url,boolean isFront) throws UnsupportedEncodingException, Exception{
		Map thirdPayConfig=allinPayProperty();
		System.out.println("======================发送报文======================：\n"+xml);
		String resp=XmlTools.send(url,xml);
		System.out.println("======================响应内容======================") ;
		String pfxcerPath=thirdPayConfig.get("pfxcerPath").toString();
		/*try {
			 configPath = java.net.URLDecoder.decode(this.getClass().getResource("/").getPath(),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		pfxcerPath=AppUtil.getAppAbsolutePath()+pfxcerPath;
		String pfxcerUrl = pfxcerPath.replace("\\", "/");
		boolean flag= verifyMsg(resp, pfxcerUrl,isFront);
		if(flag){
			System.out.println("响应内容验证通过") ;
		}else{
			System.out.println("响应内容验证不通过") ;
		}
		return resp;
	}
	/**
	 * 验证签名
	 * @param msg
	 * @return
	 *日期：Sep 9, 2012
	 * @throws Exception 
	 */
	public static boolean verifyMsg(String msg,String cer,boolean isFront) throws Exception{
		 boolean flag=XmlTools.verifySign(msg, cer, false,isFront);
		System.out.println("验签结果["+flag+"]") ;
		return flag;
	}
	/**
	 * 实体对象转map
	 * @param obj
	 * @return
	 */
	 public static Map ConvertObjToMap(Object obj){
		  Map<String,Object> reMap = new HashMap<String,Object>();
		  if (obj == null) 
		   return null;
		  Field[] fields = obj.getClass().getDeclaredFields();
		  try {
			   for(int i=0;i<fields.length;i++){
			     Field f = obj.getClass().getDeclaredField(fields[i].getName());
			     f.setAccessible(true);
			           Object o = f.get(obj);
			           if(o!=null&&!o.toString().equals("-1")){
			        	   reMap.put(fields[i].getName(), o);
			           }
			   	}
		  } catch (Exception e) {
			  e.printStackTrace();
		  } 
		  return reMap;
	}
	 /**
		 * 实体对象转map
		 * @param obj
		 * @return
		 */
	public static Map<String,String> createMap(Class<?> type,Object obj) {
		Map<String,String> map = new HashMap<String,String>();
		try{
	        BeanInfo beanInfo = Introspector.getBeanInfo(type);
	        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
	        for(int i=0;i<propertyDescriptors.length;i++){
	        	 PropertyDescriptor descriptor=propertyDescriptors[i];
	        	 String propertyName = descriptor.getName();
	             if (!propertyName.equals("class")) {
	                 Method readMethod = descriptor.getReadMethod();
	                 Object result = readMethod.invoke(type.cast(obj), new Object[0]);
	                 //需要签名的数据必须是非null,可以是""
	                 if(null!=result){
	                	 map.put(propertyName,result.toString());
	                 }
	             }
	        }
		}catch(Exception e){
			e.printStackTrace();
			map=null;
		}
		return map;
	}
	/**
	 * 回调验签
	 * @return
	 */
	public static boolean allinVerify(PaymentResult paymentResult)
	{
		Map thirdPayConfig=allinPayProperty();
		String pfxPath=thirdPayConfig.get("tlCertPath").toString();
		pfxPath=AppUtil.getAppAbsolutePath()+pfxPath;
		String pfxcerUrl = pfxPath.replace("\\", "/");
		paymentResult.setCertPath(pfxcerUrl); 
		//验证签名：返回true代表验签成功；否则验签失败。
		boolean verifyResult = paymentResult.verify();
		return verifyResult;
	}
	/**
	 * 提现解析xml
	 * @param retXml
	 * @return
	 */
	public static String dealWithdraw(String retXml){
		String notify_sn="";
		String trxcode = null;
		//交易码
		if (retXml.indexOf("<TRX_CODE>") != -1)
		{
			int end = retXml.indexOf("</TRX_CODE>");
			int begin = end - 6;
			if (begin >= 0) trxcode = retXml.substring(begin, end);
		}
		if(trxcode.equals("200003")){
			if (retXml.indexOf("<NOTIFY_SN>") != -1)
			{
				int start = retXml.indexOf("<NOTIFY_SN>");
				int end = retXml.indexOf("</NOTIFY_SN>");
				int begin = start + 11;
				if (begin >= 0) notify_sn = retXml.substring(begin, end);
			}
		}
		return notify_sn;
	}
}
