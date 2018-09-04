package com.zhiwei.credit.action.creditFlow.finance.compensatory;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.Constants;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatoryFlow;
import com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryFlowService;
/**
 * 
 * @author 
 *
 */
public class PlBidCompensatoryFlowAction extends BaseAction{
	@Resource
	private PlBidCompensatoryFlowService plBidCompensatoryFlowService;
	private PlBidCompensatoryFlow plBidCompensatoryFlow;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlBidCompensatoryFlow getPlBidCompensatoryFlow() {
		return plBidCompensatoryFlow;
	}

	public void setPlBidCompensatoryFlow(PlBidCompensatoryFlow plBidCompensatoryFlow) {
		this.plBidCompensatoryFlow = plBidCompensatoryFlow;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addFilter("Q_compensatoryId_L_EQ", getRequest().getParameter("compensatoryId"));
		filter.addFilter("Q_backStatus_N_EQ", "2");
		List<PlBidCompensatoryFlow> list= plBidCompensatoryFlowService.getAll(filter);
		
		Type type=new TypeToken<List<PlBidCompensatoryFlow>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
				plBidCompensatoryFlowService.remove(new Long(id));
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
		PlBidCompensatoryFlow plBidCompensatoryFlow=plBidCompensatoryFlowService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plBidCompensatoryFlow));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		plBidCompensatoryFlow.setBackCompensatoryMoney(plBidCompensatoryFlow.getBackCompensatoryMoney()!=null?plBidCompensatoryFlow.getBackCompensatoryMoney():new BigDecimal(0));
		plBidCompensatoryFlow.setBackPunishMoney(plBidCompensatoryFlow.getBackPunishMoney()!=null?plBidCompensatoryFlow.getBackPunishMoney():new BigDecimal(0));
		plBidCompensatoryFlow.setFlateMoney(plBidCompensatoryFlow.getFlateMoney()!=null?plBidCompensatoryFlow.getFlateMoney():new BigDecimal(0));
		String [] ret=plBidCompensatoryFlowService.check(plBidCompensatoryFlow);
		if(ret[0].equals(Constants.CODE_SUCCESS)){
			Boolean  flag=plBidCompensatoryFlowService.saveCompensatoryFlow(plBidCompensatoryFlow);
			if(flag){
				setJsonString("{success:true,msg:'保存成功'}");
			}else{
				setJsonString("{success:false,msg:'保存失败'}");
			}
		}else{
			setJsonString("{success:false,msg:'"+ret[1]+"'}");
		}
		return SUCCESS;
		
	}
}
