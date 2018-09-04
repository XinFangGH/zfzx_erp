package com.zhiwei.credit.action.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTest;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenorTestService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class PlMmOrderChildrenorTestAction extends BaseAction{
	@Resource
	private PlMmOrderChildrenorTestService plMmOrderChildrenorTestService;
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	private PlMmOrderChildrenorTest plMmOrderChildrenorTest;
	
	private Long matchId;
    private Long orderId;
	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public PlMmOrderChildrenorTest getPlMmOrderChildrenorTest() {
		return plMmOrderChildrenorTest;
	}

	public void setPlMmOrderChildrenorTest(PlMmOrderChildrenorTest plMmOrderChildrenorTest) {
		this.plMmOrderChildrenorTest = plMmOrderChildrenorTest;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlMmOrderChildrenorTest> list= plMmOrderChildrenorTestService.getAll(filter);
		
		Type type=new TypeToken<List<PlMmOrderChildrenorTest>>(){}.getType();
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:");

		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
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
			for(String id:ids){
				plMmOrderChildrenorTestService.remove(new Long(id));
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
		PlMmOrderChildrenorTest plMmOrderChildrenorTest=plMmOrderChildrenorTestService.get(matchId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plMmOrderChildrenorTest));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plMmOrderChildrenorTest.getMatchId()==null){
			plMmOrderChildrenorTestService.save(plMmOrderChildrenorTest);
		}else{
			PlMmOrderChildrenorTest orgPlMmOrderChildrenorTest=plMmOrderChildrenorTestService.get(plMmOrderChildrenorTest.getMatchId());
			try{
				BeanUtil.copyNotNullProperties(orgPlMmOrderChildrenorTest, plMmOrderChildrenorTest);
				plMmOrderChildrenorTestService.save(orgPlMmOrderChildrenorTest);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String matchForecast(){
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addFilter("Q_orderId_L_EQ",orderId.toString() );
		List<PlMmOrderChildrenorTest> list= plMmOrderChildrenorTestService.getAll(filter);
		for(PlMmOrderChildrenorTest t:list){
			plMmOrderChildrenorTestService.remove(t);
		}
		PlManageMoneyPlanBuyinfo order=plManageMoneyPlanBuyinfoService.get(orderId);
		plMmOrderChildrenorTestService.matchForecast(order);
		
		return SUCCESS;
		
	}
}
