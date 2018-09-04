package com.zhiwei.credit.action.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderInfo;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderInfoService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class PlMmOrderInfoAction extends BaseAction  {
	
	
	@Resource
	private PlMmOrderInfoService plMmOrderInfoService;
	private PlMmOrderInfo plMmOrderInfo;
	
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	@Resource
	private PlMmOrderAssignInterestService plMmOrderAssignInterestService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private CreditProjectService creditProjectService;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PlMmOrderInfo getPlMmOrderInfo() {
		return plMmOrderInfo;
	}

	public void setPlMmOrderInfo(PlMmOrderInfo plMmOrderInfo) {
		this.plMmOrderInfo = plMmOrderInfo;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<PlMmOrderInfo> list= plMmOrderInfoService.getAll(filter);
		
		Type type=new TypeToken<List<PlMmOrderInfo>>(){}.getType();
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
				plMmOrderInfoService.remove(new Long(id));
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
		PlMmOrderInfo plMmOrderInfo=plMmOrderInfoService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plMmOrderInfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
		
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plMmOrderInfo.getId()==null){
			plMmOrderInfoService.save(plMmOrderInfo);
		}else{
			PlMmOrderInfo orgPlMmOrderInfo=plMmOrderInfoService.get(plMmOrderInfo.getId());
			try{
				BeanUtil.copyNotNullProperties(orgPlMmOrderInfo, plMmOrderInfo);
				plMmOrderInfoService.save(orgPlMmOrderInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 * 投资流程页面保存方法
	 * @return
	 */
	public String savaByFlow(){
		
		PlMmOrderInfo orgPlMmOrderInfo=plMmOrderInfoService.get(id);
		try{
			BeanUtil.copyNotNullProperties(orgPlMmOrderInfo, plMmOrderInfo);
			plMmOrderInfoService.save(orgPlMmOrderInfo);
			// 意见说明
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	
	/**
	 * 启动投资流程
	 */
	public String startInvestFlow(){
		
		boolean flag = plMmOrderInfoService.startInvestFlow(getRequest());
		
		return SUCCESS;
	}
	
	
	/**
	 * 生成投资人款项计划表
	 */
	public String createFundList(){
		String plMmOrderInfoId = this.getRequest().getParameter("plMmOrderInfoId");
		String flag = this.getRequest().getParameter("flag");
		List<PlMmOrderAssignInterest> list = null;
		PlMmOrderInfo _plMmOrderInfo = plMmOrderInfoService.get(Long.valueOf(plMmOrderInfoId));
		try {
			if(null!=plMmOrderInfo){
				BeanUtil.copyNotNullProperties(_plMmOrderInfo, plMmOrderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		if(_plMmOrderInfo.getOrderId()!=null&&!"".equals(_plMmOrderInfo.getOrderId())&&"undefined".equals(_plMmOrderInfo.getOrderId())){
			list = plMmOrderAssignInterestService.listByOrderId(_plMmOrderInfo.getOrderId());
		}else if("create".equals(flag)){
			list = plManageMoneyPlanBuyinfoService.createPlMmOrderAssignInterestList(_plMmOrderInfo);
		}else{
			list = new ArrayList<PlMmOrderAssignInterest>();
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list==null?0:list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[] { "intentDate", "factDate", "interestStarTime","interestEndTime" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
		
	}
	
	/**
	 * 获得流程信息
	 * @return
	 */
	public String findRunInfo(){
		
		String runId = this.getRequest().getParameter("runId");
		
		ProcessRun processRun = processRunService.get(Long.valueOf(runId));
		List<ProcessForm> list = processFormService.listByRunId(Long.valueOf(runId));
		
		ProcessForm processForm = list.get(list.size()-1);
		
		StringBuffer sb = new StringBuffer("{success:true,");
		sb.append("taskId:").append("\"").append(processForm.getTaskId()).append("\",");
		sb.append("activityName:").append("\"").append(processForm.getActivityName()).append("\",");
		sb.append("projectName:").append("\"").append(processRun.getSubject()).append("\"");
		sb.append("}");
		jsonString = sb.toString();
		return SUCCESS;
	}

	
	/**
	 * 校验投资人投资金额大于购买理财产品金额
	 */
	public String checkInvestMoney(){
		String plMmOrderInfoId = this.getRequest().getParameter("plMmOrderInfoId");
		PlMmOrderInfo _plMmOrderInfo = plMmOrderInfoService.get(Long.valueOf(plMmOrderInfoId));
		String msg = "{success:true,flag:true}";
		
		BigDecimal[] a=obSystemAccountService.sumTypeTotalMoney(Long.valueOf(_plMmOrderInfo.getInvestPersonId()),"1");
		if(a[0].compareTo(new BigDecimal("0"))==0){
			 msg = "{success:true,flag:flase,msg:'此投资人没有开设账户'}";
		}
		if(a[3].compareTo(_plMmOrderInfo.getBuyMoney())==-1){
			msg = "{success:true,flag:false,msg:'购买金额不能大于可用金额'}";
		}
		jsonString = msg;
		return SUCCESS;
	}
	
	
}
