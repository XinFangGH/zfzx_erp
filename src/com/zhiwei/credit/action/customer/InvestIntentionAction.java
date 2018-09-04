package com.zhiwei.credit.action.customer;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.customer.InvestIntention;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.customer.InvestIntentionService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class InvestIntentionAction extends BaseAction{
	@Resource
	private InvestIntentionService investIntentionService;
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	
	
	private CsInvestmentperson csInvestmentperson;
	private InvestIntention investIntention;
	
	private Long intentId;

	public Long getIntentId() {
		return intentId;
	}

	public void setIntentId(Long intentId) {
		this.intentId = intentId;
	}

	public InvestIntention getInvestIntention() {
		return investIntention;
	}

	public void setInvestIntention(InvestIntention investIntention) {
		this.investIntention = investIntention;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<InvestIntention> list= investIntentionService.getAll(filter);
		
		Type type=new TypeToken<List<InvestIntention>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
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
				investIntentionService.remove(new Long(id));
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
		Map<String, Object> map = new HashMap<String, Object>();
		InvestIntention investIntention=investIntentionService.get(intentId);
		map.put("investIntention", investIntention);
		if(investIntention!=null){
			CsInvestmentperson cs =csInvestmentpersonService.get(investIntention.getInvestPersonId().longValue());
			map.put("csInvestmentperson", cs);
		}
		
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = com.zhiwei.core.util.JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		try{
			String [] statuses = investIntention.getStatus().split(",");
			investIntention.setStatus(statuses[0].trim());
			if(investIntention.getIntentId()==null){
				CsInvestmentperson cs =csInvestmentpersonService.get(csInvestmentperson.getInvestId());
				BeanUtil.copyNotNullProperties(cs, csInvestmentperson);
				csInvestmentpersonService.merge(cs);
				investIntention.setInvestPersonName(cs.getInvestName());
				investIntentionService.save(investIntention);
			}else{
				InvestIntention orgInvestIntention=investIntentionService.get(investIntention.getIntentId());
				
				CsInvestmentperson cs =csInvestmentpersonService.get(csInvestmentperson.getInvestId());
				BeanUtil.copyNotNullProperties(cs, csInvestmentperson);
				csInvestmentpersonService.merge(cs);
				BeanUtil.copyNotNullProperties(orgInvestIntention, investIntention);
				orgInvestIntention.setInvestPersonName(cs.getInvestName());
				investIntentionService.save(orgInvestIntention);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		setJsonString("{success:true}");
		return SUCCESS;
		
	}

	public void setCsInvestmentperson(CsInvestmentperson csInvestmentperson) {
		this.csInvestmentperson = csInvestmentperson;
	}

	public CsInvestmentperson getCsInvestmentperson() {
		return csInvestmentperson;
	}
}
