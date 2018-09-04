package com.thirdInterface.pay.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hurong.core.util.DateUtil;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.credit.action.pay.FontAction;
import com.zhiwei.credit.action.pay.FontHuiFuAction;
import com.zhiwei.credit.core.creditUtils.MD5;
import com.zhiwei.credit.core.creditUtils.StringUtils;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.pay.LoanTransferReturnBean;
import com.zhiwei.credit.model.pay.MadaiLoanInfoBean;
import com.zhiwei.credit.model.pay.MoneyMoreMore;
import com.zhiwei.credit.model.pay.ResultBean;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.util.Common;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;
import com.zhiwei.credit.util.RsaHelper;
import com.zhiwei.credit.util.UrlUtils;
import com.zhiwei.credit.util.WebClient;
/**
 * 双乾实现 接口
 * @author YUAN.zc
 *
 */
public class MMThirdPayServiceImpl implements ThirdPayEngine {
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private com.zhiwei.credit.service.p2p.BpCustMemberService bpCustMemberService;
	private String MM_PostUrl; //双乾的请求地址
	private String MM_PlatformMoneymoremore; //平台标识 商户唯一编号
	private String MM_PrivateKey;  //私钥
	private String MM_PublicKey;  // 公钥
	
	private String Action; //操作类型 1手动  2自动
	private String TransferType; //转账方式 1 桥连 2 直连
	private String NeedAudit; //通过是否需要审核  空.需要审核 1 自动通过
	
	private String AuditType;//审核类型  1.通过2.退回3.二次分配同意4.二次分配不同意


	private String Remark1="" ;
	private String Remark2="" ;
	private String Remark3=""; 
	private String ReturnURL;
	private String NotifyURL;
	private String SignInfo;
	private  String RandomTimeStamp;
	private String submitURL="";
	public MMThirdPayServiceImpl(){
		/*Map map=AppUtil.initPropertes(this.PROSTR);
		MM_PostUrl=map.get("MM_PostUrl").toString();
		MM_PlatformMoneymoremore=map.get("MM_PlatformMoneymoremore").toString();
		MM_PrivateKey=map.get("MM_PrivateKey").toString();
		MM_PublicKey=map.get("MM_PublicKey").toString();*/
	}

	@Override
	public String[] repayment(SlFundIntent fund,List<BpFundIntent> bpFundIntentList,BpCustMember outMem,BigDecimal fullMoney, String basePath, String postType,
			HttpServletResponse rep, HttpServletRequest req, String remk0,String remk1, String remk2) {
		String[] retArr=null;
		String[] urlRetArr=null;//调用post 请求返回的数据 数组
		//款项台帐id 字符串
		submitURL=AppUtil.getSysConfig().get("MM_PostUrl").toString()+"loan/loan.action";
		 //设置 属性
		 RandomTimeStamp=StringUtils.generateRandomTime();
		 String LoanJsonList= generateLoanJsonList(fund,bpFundIntentList,outMem,fullMoney,remk0);
		 String TransferAction="2"; 
		 ReturnURL=basePath + "pay/transferReturnFont.do";
		 NotifyURL=basePath + "pay/transferReturnBack.do";
		 //签名
		String dataStr=LoanJsonList + AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString() + TransferAction + Action + TransferType + NeedAudit + RandomTimeStamp + Remark1 + Remark2 + Remark3 + ReturnURL + NotifyURL;
		SignInfo=sign(dataStr);
		
		//生成提交参数
		Map<String, String> params=generatePubParams();
		params.put("LoanJsonList",StringUtil.stringURLEncoderByUTF8(LoanJsonList));
		params.put("TransferAction",TransferAction);
		params.put("Action",Action);
		params.put("TransferType", TransferType);
		params.put("NeedAudit", NeedAudit);
		params.put("RandomTimeStamp",RandomTimeStamp);
		params.put("Remark1",Remark1);
	    if(postType.equals(ThirdPayEngine.PostType_0)){
	    	//do somthing
	    }else if(postType.equals(ThirdPayEngine.PostType_1)){
	    	urlRetArr =WebClient.doPostQueryCmd(submitURL, params);
	    }
	    retArr=jsonToModel(urlRetArr[1]);
	    System.out.println(urlRetArr[0]);
	    System.out.println(urlRetArr[1]);
		return retArr;
	}
/**
 * json 转换为 实体
 * @param json
 * @return
 */
@SuppressWarnings("unchecked")
private   String[] jsonToModel(String jsonStr){
	String[] arr=new String[2];
	// 转账
	List<Object> loanobjectlist  = Common.JSONDecodeList(jsonStr, LoanTransferReturnBean.class);
	if (loanobjectlist != null && loanobjectlist.size() > 0)
	{
		for (int i = 0; i < loanobjectlist.size(); i++)
		{
			if (loanobjectlist.get(i) instanceof LoanTransferReturnBean)
			{
				LoanTransferReturnBean ltrb = (LoanTransferReturnBean) loanobjectlist.get(i);
				if(ltrb.getAction().equals("1")&&ltrb.getResultCode().equals("88")){
					arr[0]=Constants.CODE_SUCCESS;
					arr[1]=ltrb.getMessage();
				}else{
					arr[0]=Constants.CODE_FAILED;
					arr[1]=ltrb.getMessage();
				}
				//System.out.println(ltrb.getResultCode());
			}
		}
	}
	return arr;
}
	
	/**
	 * 审核接口
	 * @param LoanNoList 双乾返回的转账订单列表
	 * @param basePath
	 * @param response
	 * @return
	 */
	
	public ResultBean transferaudit(String LoanNoList, String basePath,HttpServletResponse response) {
		ResultBean resultBean=new ResultBean();
		
		submitURL=AppUtil.getSysConfig().get("MM_PostUrl").toString() +"loan/toloantransferaudit.action";
		ReturnURL=basePath + "pay/transferauditFont.do";//前台方法
		NotifyURL=basePath + "pay/transferauditBack.do";//后台方法
		RandomTimeStamp=StringUtils.generateRandomTime();
		String dataStr =LoanNoList + AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString() + AuditType + RandomTimeStamp + Remark1 + Remark2 + Remark3 + ReturnURL + NotifyURL;
		SignInfo=sign(dataStr);
		Map<String, String> params=generatePubParams();
		params.put("LoanNoList", LoanNoList);
		params.put("AuditType", AuditType);
		params.put("RandomTimeStamp", RandomTimeStamp);
		WebClient.urlResponse(params,submitURL,response,Constants.CHAR_UTF);
		return resultBean;
	}
	

	/**
	 * 生成转账列表
	 * @param bpFundIntentList
	 * @return
	 */
	private String  generateLoanJsonList(SlFundIntent slFundIntent, List<BpFundIntent> bpFundIntentList,BpCustMember outMem,BigDecimal fullMoney,String remk0){
		String LoanJsonList="";
		String oweParams=remk0;
		List<MadaiLoanInfoBean> listmlib =new ArrayList<MadaiLoanInfoBean>() ;
		BpCustMember inMem=null; //收款方
		
		for(BpFundIntent bpFundIntent:bpFundIntentList){
	    oweParams=oweParams+"fid:"+slFundIntent.getFundIntentId()+":"+slFundIntent.getNotMoney()+"_"+"bfid:"+bpFundIntent.getFundIntentId()+":"+bpFundIntent.getNotMoney();

		inMem=bpCustMemberService.get(bpFundIntent.getInvestPersonId());
		MadaiLoanInfoBean mlib = new MadaiLoanInfoBean();
		mlib.setLoanOutMoneymoremore(outMem.getThirdPayFlagId());
		mlib.setLoanInMoneymoremore(inMem.getThirdPayFlagId());
		mlib.setOrderNo(StringUtils.generateRandomTime());
		mlib.setBatchNo(bpFundIntent.getBidPlanId().toString());
		mlib.setAmount(bpFundIntent.getNotMoney().toString());
		mlib.setFullAmount(fullMoney.toString());
		mlib.setTransferName("huankuan");
		mlib.setRemark(bpFundIntent.getRemark());
		listmlib.add(mlib);
		}
		Remark1=oweParams;
		//设置 转账列表
		LoanJsonList=Common.JSONEncode(listmlib);
		return LoanJsonList;
	}
	/**
	 * 生成公共提交参数
	 * @return
	 */
	private  Map<String, String> generatePubParams(){
		Map<String, String> params=new HashMap<String, String>();
		params.put("Remark1", Remark1);
		params.put("Remark2", Remark2);
		params.put("Remark3", Remark3);
		params.put("ReturnURL", ReturnURL);
		params.put("NotifyURL", NotifyURL);
		params.put("SignInfo", SignInfo);
		params.put("PlatformMoneymoremore",AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());
		return params;
	}
	
	/**
	 * 生成签名 vale
	 * @param dataStr
	 * @return
	 */
	private String sign(String dataStr){
		// 签名
		RsaHelper rsa = RsaHelper.getInstance();
		MD5 md5=new MD5();
		return rsa.signData(md5.getMD5Info(dataStr), AppUtil.getSysConfig().get("MM_PrivateKey").toString()).replaceAll("\r", "").replaceAll("\n", "");
	}
	   /**
	    * 验证签名 
	    * @param sign 签名数据
	    * @param data 原数据
	    * @return
	    */
		private boolean verifySignature(String sign,String data){
			boolean ret=false;
			MD5 md5=new MD5();
			sign=sign.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "");
			data=md5.getMD5Info(data.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", ""));
			RsaHelper rsa=RsaHelper.getInstance();
			ret=rsa.verifySignature(sign, data, AppUtil.getSysConfig().get("MM_publicMyKey").toString());
			return ret;
		}
	public void setMM_PostUrl(String mMPostUrl) {
		MM_PostUrl = mMPostUrl;
	}

	public void setMM_PlatformMoneymoremore(String mMPlatformMoneymoremore) {
		MM_PlatformMoneymoremore = mMPlatformMoneymoremore;
	}

	public void setMM_PrivateKey(String mMPrivateKey) {
		MM_PrivateKey = mMPrivateKey;
	}

	public void setMM_PublicKey(String mMPublicKey) {
		MM_PublicKey = mMPublicKey;
	}

	public void setAction(String action) {
		Action = action;
	}

	public void setTransferType(String transferType) {
		TransferType = transferType;
	}

	public void setNeedAudit(String needAudit) {
		NeedAudit = needAudit;
	}

	public void setRemark1(String remark1) {
		Remark1 = remark1;
	}

	public void setRemark2(String remark2) {
		Remark2 = remark2;
	}

	public void setRemark3(String remark3) {
		Remark3 = remark3;
	}

	public void setReturnURL(String returnURL) {
		ReturnURL = returnURL;
	}

	public void setNotifyURL(String notifyURL) {
		NotifyURL = notifyURL;
	}



	public void setAuditType(String auditType) {
		AuditType = auditType;
	}

}
