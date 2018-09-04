package com.zhiwei.credit.action.p2p.redMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedEnvelope;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedMember;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedEnvelopeService;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedMemberService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustRedEnvelopeAction extends BaseAction{
	@Resource
	private BpCustRedEnvelopeService bpCustRedEnvelopeService;
	@Resource
	private BpCustRedMemberService bpCustRedMemberService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private YeePayService yeePayService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	private BpCustRedEnvelope bpCustRedEnvelope;
	private String reddatas;
	//得到config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	private Long redId;
	public Long getRedId() {
		return redId;
	}

	public String getReddatas() {
		return reddatas;
	}

	public void setReddatas(String reddatas) {
		this.reddatas = reddatas;
	}


	public void setRedId(Long redId) {
		this.redId = redId;
	}

	public BpCustRedEnvelope getBpCustRedEnvelope() {
		return bpCustRedEnvelope;
	}

	public void setBpCustRedEnvelope(BpCustRedEnvelope bpCustRedEnvelope) {
		this.bpCustRedEnvelope = bpCustRedEnvelope;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpCustRedEnvelope> list= bpCustRedEnvelopeService.getAll(filter);
		
	
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
        JSONSerializer json = JsonUtil.getJSONSerializer("distributeTime","createTime");
        json.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"), new String[] {"distributeTime","createTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			bpCustRedEnvelopeService.delete(ids);
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
    public String distribute(){
		
		String[] ids=getRequest().getParameterValues("ids");
		bpCustRedEnvelope=bpCustRedEnvelopeService.get(redId);
		StringBuffer msg=new StringBuffer("");
		
		if(ids!=null){
			for(String id:ids){
				BpCustRedMember bpCustRedMember=bpCustRedMemberService.get(new Long(id));
				BpCustMember b=bpCustMemberService.get(bpCustRedMember.getBpCustMemberId());
				String[] ret=this.doReadyDistribute(b,bpCustRedMember);;
				if(ret[0].equals(Constants.CODE_SUCCESS)){
					bpCustRedMember.setEdredMoney(bpCustRedMember.getRedMoney());
					bpCustRedMember.setDistributeTime(new Date());
					bpCustRedMemberService.save(bpCustRedMember);
					bpCustRedEnvelope.setEddistributemoney(bpCustRedEnvelope.getEddistributemoney().add(bpCustRedMember.getRedMoney()));
					msg.append(bpCustRedMember.getLoginname()+",");
				}
			
			}
			
			if(bpCustRedEnvelope.getEddistributemoney().compareTo(new BigDecimal(0))>0){
				bpCustRedEnvelope.setDistributestatus(Short.valueOf("1"));
			}
           if(bpCustRedEnvelope.getEddistributemoney().compareTo(bpCustRedEnvelope.getDistributemoney())==0){
        	  bpCustRedEnvelope.setDistributestatus(Short.valueOf("2"));
        	  bpCustRedEnvelope.setDistributeTime(new Date());
				
			}
			bpCustRedEnvelopeService.save(bpCustRedEnvelope);
		//	tring(0, msg.length()-2).tjsonString="{success:true,msg:"+msg.subsoString()+"}";
			if(bpCustRedEnvelope.getDistributestatus().equals(Short.valueOf("1"))){
				jsonString="{success:1,msg:'已部分派发红包!'}";
				
			}else if(bpCustRedEnvelope.getDistributestatus().equals(Short.valueOf("2"))){
			
				jsonString="{success:2,msg:'已全部派发红包!'}";
			}else {
				
				jsonString="{success:3,msg:'未成功派发红包!投资人未开通第三方支付或平台资金余额不足。 '}";
				
			}
			
			return SUCCESS;
		}
		
		jsonString="{success:false,msg:"+msg.toString()+"}";
		
		return SUCCESS;
	}
   
   /**
    * 派发红包方法
    * add  by linyan
    * 2014-8-14
    * @param b
    * @param bpCustRedMember
    * @return
    */
	private String[] doReadyDistribute(BpCustMember b,BpCustRedMember bpCustRedMember) {
	// TODO Auto-generated method stub
		String[] str=new String[2];
		String thirdPayType=configMap.get("thirdPayType").toString();
		String thirdPayConfig=configMap.get("thirdPayConfig").toString();
		ObSystemAccount account=obSystemAccountService.getByInvrstPersonIdAndType(b.getId(), ObSystemAccount.type0);
		if(null!=bpCustRedMember.getRedMoney()&&bpCustRedMember.getRedMoney().compareTo(new BigDecimal("0"))==1){
			if(account!=null){
				String	requestNo=ContextUtil.createRuestNumber();//第三方账号及系统账号的生成方法
				if(thirdPayType.equals("0")){//托管账户
					if(b.getThirdPayFlagId()!=null){//表示没有开通第三方支付
						String ammont=bpCustRedMember.getRedMoney().toString();
						CommonRequst commonRequest = new CommonRequst();
						commonRequest.setThirdPayConfigId(b.getThirdPayFlagId());//用户第三方标识
						commonRequest.setRequsetNo(requestNo);//请求流水号
						commonRequest.setAmount(new BigDecimal(ammont));//交易金额
						commonRequest.setCustMemberType("0");
						commonRequest.setTransferName(ThirdPayConstants.TN_SEDRED);
						commonRequest.setBussinessType(ThirdPayConstants.BT_SEDRED);
						commonRequest.setBidId(bpCustRedMember.getRedTopersonId().toString());
						if(b.getCustomerType()!=null&&b.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
							commonRequest.setAccountType(1);
						}else{//借款人是个人
							commonRequest.setAccountType(0);
						}
						CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
						if (commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
							str[0]=Constants.CODE_SUCCESS;
							str[1]=commonResponse.getResponseMsg();
						}else{
							str[0]=Constants.CODE_FAILED;
							str[1]=commonResponse.getResponseMsg();
						}
					}else{
						str[0]=Constants.CODE_FAILED;
						str[1]="红包派发失败：投资人尚未注册第三方支付账户";
					}
				}else{//大账户
					str[0]=Constants.CODE_SUCCESS;
					str[1]="红包派发";
				}
				
				if(str[0].equals(Constants.CODE_SUCCESS)){
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("investPersonId",b.getId());//投资人Id（必填）
					map.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
					map.put("transferType",ObAccountDealInfo.T_REDENVELOPE);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
					map.put("money",bpCustRedMember.getRedMoney());//交易金额	（必填）			 
					map.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
					map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
					map.put("recordNumber",requestNo);//交易流水号	（必填）
					map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
					obAccountDealInfoService.operateAcountInfo(map);
				}
			}else{
				str[0]=Constants.CODE_FAILED;
				str[1]="红包派发失败：投资人尚未开通系统账户";
			}
		}else{
			str[0]=Constants.CODE_FAILED;
			str[1]="红包派发成功：派发金额为0";
		}
	return str;
}

	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BpCustRedEnvelope bpCustRedEnvelope=bpCustRedEnvelopeService.get(redId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustRedEnvelope));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpCustRedEnvelope.getRedId()==null){
			bpCustRedEnvelopeService.save(bpCustRedEnvelope);
			if(bpCustRedEnvelope.getRedId()!=null){
				String[] ret=bpCustRedMemberService.saveredmembers(reddatas, bpCustRedEnvelope.getRedId());
				bpCustRedEnvelope.setDistributemoney(new BigDecimal(ret[0]));
				bpCustRedEnvelope.setDistributecount(new Integer(ret[1]));
				bpCustRedEnvelope.setEddistributemoney(new BigDecimal(0));
				bpCustRedEnvelope.setCreateTime(new Date());
				bpCustRedEnvelopeService.save(bpCustRedEnvelope);
				
			}
		
		}else{
			BpCustRedEnvelope orgBpCustRedEnvelope=bpCustRedEnvelopeService.get(bpCustRedEnvelope.getRedId());
			try{
				BeanUtil.copyNotNullProperties(orgBpCustRedEnvelope, bpCustRedEnvelope);
			
				String[] ret=bpCustRedMemberService.saveredmembers(reddatas, bpCustRedEnvelope.getRedId());
				orgBpCustRedEnvelope.setDistributemoney(new BigDecimal(ret[0]));
				orgBpCustRedEnvelope.setDistributecount(new Integer(ret[1]));
				bpCustRedEnvelope.setEddistributemoney(new BigDecimal(0));
				bpCustRedEnvelopeService.save(orgBpCustRedEnvelope);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}

	public void outputExcel(){
	String bpcoupon = this.getRequest().getParameter("bpcoupon");
	if(bpcoupon!=null&&!bpcoupon.equals("")){
		String [] tableHeader = {"序号","用户名"};
		try {
		   List a=	new ArrayList();
		   a.add(1);
			ExcelHelper.export(a,tableHeader,"优惠券派发");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}else{}
	String [] tableHeader = {"序号","用户名","奖励金额"};
	try {
	   List a=	new ArrayList();
	   a.add(1);
		ExcelHelper.export(a,tableHeader,"红包奖励");
	} catch (Exception e) {
		e.printStackTrace();
	}
		
		
	}

}
