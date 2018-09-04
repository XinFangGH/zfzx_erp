package com.zhiwei.credit.action.creditFlow.guarantee.guaranteefinance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBank;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountRecordService;
import com.zhiwei.credit.service.system.CsDicAreaDynamService;
/**
 * 
 * @author 
 *
 */
public class GlAccountBankCautionmoneyAction extends BaseAction{
	@Resource
	private GlAccountBankCautionmoneyService glAccountBankCautionmoneyService;
	@Resource
	private CsDicAreaDynamService csDicAreaDynamService;
	@Resource
	private GlAccountBankService glAccountBankService;
	@Resource
	private GlAccountRecordService glAccountRecordService;
	@Resource
	private GlBankGuaranteemoneyService glBankGuaranteemoneyService;
	private GlAccountBankCautionmoney glAccountBankCautionmoney;
	
	private Long glAccountBankCautionmoneyId;

	

	public Long getGlAccountBankCautionmoneyId() {
		return glAccountBankCautionmoneyId;
	}

	public void setGlAccountBankCautionmoneyId(Long glAccountBankCautionmoneyId) {
		this.glAccountBankCautionmoneyId = glAccountBankCautionmoneyId;
	}

	public GlAccountBankCautionmoney getGlAccountBankCautionmoney() {
		return glAccountBankCautionmoney;
	}

	public void setGlAccountBankCautionmoney(GlAccountBankCautionmoney glAccountBankCautionmoney) {
		this.glAccountBankCautionmoney = glAccountBankCautionmoney;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		int size=0;
		QueryFilter filter=new QueryFilter(getRequest());
		List<GlAccountBankCautionmoney> list= glAccountBankCautionmoneyService.getalllist();
		for(GlAccountBankCautionmoney l:list){
			l.setBankName(csDicAreaDynamService.get(l.getBankBranchId()).getRemarks());
			l.setAccountFrozenMoney(l.getAuthorizationMoney().subtract(l.getSurplusMoney()));
			size=list.size();
		}
		
		Type type=new TypeToken<List<GlAccountBankCautionmoney>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
	
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				glAccountBankCautionmoneyService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
    public String detele(){
    	 glAccountBankCautionmoney =glAccountBankCautionmoneyService.get(glAccountBankCautionmoneyId);
    	Long glAccountBankId=glAccountBankCautionmoney.getParentId();
    	 List<GlAccountRecord> list1=glAccountRecordService.getallbycautionAccountId(glAccountBankCautionmoneyId,0,999999999);
		List<GlBankGuaranteemoney> list2=glBankGuaranteemoneyService.getallbycautionAccountId(glAccountBankCautionmoneyId,0,999999999);
		for(GlAccountRecord s:list1){
			s.setIdDelete(Short.valueOf("1"));
			glAccountRecordService.save(s);
		}
//		for(GlBankGuaranteemoney t:list2){
//			glBankGuaranteemoneyService.remove(t);
//		}
		GlAccountBankCautionmoney  glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glAccountBankCautionmoneyId);
		glAccountBankCautionmoney.setIdDelete(Short.valueOf("1"));
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
	
	
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glAccountBankCautionmoneyId);
		GlAccountBank b=glAccountBankService.get(glAccountBankCautionmoney.getParentId());
		glAccountBankCautionmoney.setBankParentId(b.getBankParentId());
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(glAccountBankCautionmoney));
		sb.append("}");
		setJsonString(sb.toString());
		
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		GlAccountBank glAccountBank=new GlAccountBank();
		
		if(glAccountBankCautionmoney.getId()==null){
			glAccountBankCautionmoney.setBankBranchName(csDicAreaDynamService.get(glAccountBankCautionmoney.getBankBranchId()).getRemarks());
			glAccountBankCautionmoney.setText(csDicAreaDynamService.get(glAccountBankCautionmoney.getBankBranchId()).getText());
	//		glAccountBankCautionmoney.setCreateDate(new Date());
			glAccountBankCautionmoney.setLeaf(true);
		//	glAccountBankCautionmoney.setRawauthorizationMoney(glAccountBankCautionmoney.getAuthorizationMoney());
			glAccountBankCautionmoney.setAuthorizationMoney(glAccountBankCautionmoney.getRawauthorizationMoney());
			glAccountBankCautionmoney.setSurplusMoney(glAccountBankCautionmoney.getRawsurplusMoney());
			glAccountBankCautionmoneyService.save(glAccountBankCautionmoney);
		    glAccountBank=glAccountBankService.get(glAccountBankCautionmoney.getParentId());
			 
		}else{
			glAccountBankCautionmoney.setText(csDicAreaDynamService.get(glAccountBankCautionmoney.getBankBranchId()).getText());
			GlAccountBankCautionmoney orgGlAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glAccountBankCautionmoney.getId());
			try{
				BeanUtil.copyNotNullProperties(orgGlAccountBankCautionmoney, glAccountBankCautionmoney);
				glAccountBankCautionmoneyService.save(orgGlAccountBankCautionmoney);
				  glAccountBank=glAccountBankService.get(orgGlAccountBankCautionmoney.getParentId());
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		
		
		glAccountBankCautionmoneyService.evict(glAccountBankCautionmoney);
		List<GlAccountBankCautionmoney> list=glAccountBankCautionmoneyService.getbyparentId(glAccountBank.getId());
		BigDecimal authorizationMoney=new BigDecimal(0);
		BigDecimal surplusMoney =new BigDecimal(0);
		for(GlAccountBankCautionmoney l:list){
			authorizationMoney=authorizationMoney.add(l.getAuthorizationMoney());
			surplusMoney=surplusMoney.add(l.getSurplusMoney());
		}
		glAccountBank.setAuthorizationMoney(authorizationMoney);
		glAccountBank.setSurplusMoney(surplusMoney);
		glAccountBankService.save(glAccountBank);
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
