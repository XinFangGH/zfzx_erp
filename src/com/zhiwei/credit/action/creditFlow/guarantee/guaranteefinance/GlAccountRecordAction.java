package com.zhiwei.credit.action.creditFlow.guarantee.guaranteefinance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBank;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountRecordService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class GlAccountRecordAction extends BaseAction{
	@Resource
	private GlAccountRecordService glAccountRecordService;
	@Resource
	private GlAccountBankCautionmoneyService glAccountBankCautionmoneyService;
	@Resource
	private GlAccountBankService glAccountBankService;
	@Resource
	private GlBankGuaranteemoneyService glBankGuaranteemoneyService;
	private GlAccountRecord glAccountRecord;
	private String nodeId;
	private String accountBankId;
	private Integer start;
	private Integer limit;
	private String flag;
	
	

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	private Long glAccountRecordId;

	

	public String getAccountBankId() {
		return accountBankId;
	}

	public void setAccountBankId(String accountBankId) {
		this.accountBankId = accountBankId;
	}

	public Long getGlAccountRecordId() {
		return glAccountRecordId;
	}

	public void setGlAccountRecordId(Long glAccountRecordId) {
		this.glAccountRecordId = glAccountRecordId;
	}

	public GlAccountRecord getGlAccountRecord() {
		return glAccountRecord;
	}

	public void setGlAccountRecord(GlAccountRecord glAccountRecord) {
		this.glAccountRecord = glAccountRecord;
	}

	

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		int size=0;
		List<GlAccountRecord> list=new ArrayList<GlAccountRecord>();
		  if(flag ==null || flag.equals("all")){ 
					QueryFilter filter=new QueryFilter(getRequest());
					if(null==nodeId){
					    list= glAccountRecordService.getalllist();
					    size=glAccountRecordService.getalllist().size();
					}
					if(null!=nodeId && nodeId.equals("0")){
						list= glAccountRecordService.getallbyglAccountBankId(Long.valueOf(accountBankId),start,limit);
			              size=glAccountRecordService.getallbyglAccountBankIdsize(Long.valueOf(accountBankId));
					}
			        if(null!=nodeId &&!nodeId.equals("0")){
						list =glAccountRecordService.getallbycautionAccountId(Long.valueOf(nodeId),start,limit);
						 size=glAccountRecordService.getallbycautionAccountIdsize(Long.valueOf(nodeId));
					}
		  }
     
		if (flag != null && (flag.equals("incomepaycheckbox") || flag.equals("freezecheckbox"))) { // 取费用相关
			QueryFilter filter=new QueryFilter(getRequest());
			if(null==nodeId){
			    list= glAccountRecordService.getallreleate(start, limit, flag);
			    size=glAccountRecordService.getallreleate(flag);
			}
			if(null!=nodeId && nodeId.equals("0")){
				list= glAccountRecordService.getallreleatebyglAccountBankId(Long.valueOf(accountBankId), start, limit,flag);
	              size=glAccountRecordService.getallreleatebyglAccountBankIdsize(Long.valueOf(accountBankId),flag);
			}
	        if(null!=nodeId &&!nodeId.equals("0")){
				list =glAccountRecordService.getallreleatebycautionAccountId(Long.valueOf(nodeId),start,limit,flag);
				 size=glAccountRecordService.getallreleatebycautionAccountIdsize(Long.valueOf(nodeId),flag);
			}
			}
			
	  
         if(flag !=null && flag.equals("none")){  //取空
             
         } 
         
         
         for(GlAccountRecord l:list){
				GlAccountBankCautionmoney glAccountBankCautionmoney =new GlAccountBankCautionmoney();
				glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(l.getCautionAccountId());
				l.setBankBranchName(glAccountBankCautionmoney.getText());
				l.setBankname(glAccountBankCautionmoney.getBankBranchName());
				if(l.getCapitalType()==1){
					l.setCapitalTypeValue("存入");
				}
				if(l.getCapitalType()==2){
					l.setCapitalTypeValue("支出");
				}
				if(l.getCapitalType()==3){
					l.setCapitalTypeValue("冻结");
				}
				if(l.getCapitalType()==4){
					l.setCapitalTypeValue("解冻");
				}
			}
       
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(
				",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("oprateDate"
				);
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"oprateDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
public String delete(){
	 glAccountRecord =glAccountRecordService.get(glAccountRecordId);
	 if(glAccountRecord.getProjectId() !=null){
		 if(glAccountRecord.getCapitalType()==4){
		 GlBankGuaranteemoney  glBankGuaranteemoney =glBankGuaranteemoneyService.getbyprojId(glAccountRecord.getProjectId()).get(0);
		 glBankGuaranteemoney.setUnfreezeMoney(null);
		 glBankGuaranteemoney.setReleaseDate(null);
		 glBankGuaranteemoneyService.save(glBankGuaranteemoney);
	     }
		 if(glAccountRecord.getCapitalType()==3){
			 GlBankGuaranteemoney  glBankGuaranteemoney =glBankGuaranteemoneyService.getbyprojId(glAccountRecord.getProjectId()).get(0);
			 glBankGuaranteemoney.setFreezeMoney(null);
			 glBankGuaranteemoney.setFreezeDate(null);
			 glBankGuaranteemoneyService.save(glBankGuaranteemoney);
			 
		 }
		 
	 }
	   glAccountRecordService.remove(glAccountRecordId);
	   saveguaranteemoneyAccount(glAccountRecord.getGlAccountBankId(),glAccountRecord.getCautionAccountId());
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				glAccountRecordService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		GlAccountRecord glAccountRecord=glAccountRecordService.get(glAccountRecordId);
		GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glAccountRecord.getCautionAccountId());
		glAccountRecord.setBankBranchName(glAccountBankCautionmoney.getBankBranchName()+"-"+glAccountBankCautionmoney.getAccountname());
		glAccountRecord.setSurplusMoney(glAccountBankCautionmoney.getSurplusMoney());
		if(glAccountRecord.getCapitalType()==1){
			glAccountRecord.setCapitalTypeValue("存入");
		}
		if(glAccountRecord.getCapitalType()==2){
			glAccountRecord.setCapitalTypeValue("支出");
		}
		if(glAccountRecord.getCapitalType()==3){
			glAccountRecord.setCapitalTypeValue("冻结");
		}
		if(glAccountRecord.getCapitalType()==4){
			glAccountRecord.setCapitalTypeValue("解冻");
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(glAccountRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(glAccountRecord.getGlAccountRecordId()==null){
			AppUser user=ContextUtil.getCurrentUser();
			glAccountRecord.setHandlePerson(user.getFullname());
			GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glAccountRecord.getCautionAccountId());
			glAccountRecord.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
			glAccountRecordService.save(glAccountRecord);
			 saveguaranteemoneyAccount(glAccountRecord.getGlAccountBankId(),glAccountRecord.getCautionAccountId());
		}else{
			GlAccountRecord orgGlAccountRecord=glAccountRecordService.get(glAccountRecord.getGlAccountRecordId());
			try{
				BeanUtil.copyNotNullProperties(orgGlAccountRecord, glAccountRecord);
				glAccountRecordService.save(orgGlAccountRecord);
				 saveguaranteemoneyAccount(orgGlAccountRecord.getGlAccountBankId(),orgGlAccountRecord.getCautionAccountId());
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		
	  
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public void saveguaranteemoneyAccount(Long glAccountBankId,Long cautionAccountId){
		GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(cautionAccountId);
		List<GlAccountRecord> listRecord =glAccountRecordService.getallbycautionAccountId(cautionAccountId,0,99999999);
		BigDecimal incomemoney=new BigDecimal(0);
		BigDecimal paymoney =new BigDecimal(0);
		BigDecimal frozenMoney=new BigDecimal(0);
		BigDecimal unFrozenMoney =new BigDecimal(0);
		for(GlAccountRecord l:listRecord){
			if(l.getCapitalType()==1){ //存入
				incomemoney=incomemoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==2){ //支出
				paymoney=paymoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==3){ //冻结
				frozenMoney=frozenMoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==4){ //解冻
				unFrozenMoney=unFrozenMoney.add(l.getOprateMoney());
			}
		}
		BigDecimal sum =glAccountBankCautionmoney.getRawauthorizationMoney().add(incomemoney).subtract(paymoney);
		glAccountBankCautionmoney.setAuthorizationMoney(sum);
		BigDecimal sumsurplusMoney=glAccountBankCautionmoney.getRawsurplusMoney().add(incomemoney).subtract(paymoney).add(unFrozenMoney).subtract(frozenMoney);
		glAccountBankCautionmoney.setSurplusMoney(sumsurplusMoney);
		glAccountBankCautionmoneyService.save(glAccountBankCautionmoney);
		
		GlAccountBank glAccountBank=glAccountBankService.get(glAccountBankId);
		List<GlAccountBankCautionmoney> list=glAccountBankCautionmoneyService.getbyparentId(glAccountBankId);
		BigDecimal authorizationMoney=new BigDecimal(0);
		BigDecimal surplusMoney =new BigDecimal(0);
		for(GlAccountBankCautionmoney l:list){
			authorizationMoney=authorizationMoney.add(l.getAuthorizationMoney());
			surplusMoney=surplusMoney.add(l.getSurplusMoney());
		}
		glAccountBank.setAuthorizationMoney(authorizationMoney);
		glAccountBank.setSurplusMoney(surplusMoney);
		glAccountBankService.save(glAccountBank);
	}
}
