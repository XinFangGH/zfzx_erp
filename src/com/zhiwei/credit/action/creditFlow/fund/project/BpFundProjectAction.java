package com.zhiwei.credit.action.creditFlow.fund.project;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hurong.core.Constants;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.system.AppUserService;

import flexjson.JSONSerializer;

public class BpFundProjectAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private BpFundProject platFormBpFundProject;//平台资金
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private SlFundIntentService slFundIntentService;
	private Long projectId;
	private Boolean isGrantedShowAllProjects;
	private Short projectStatus;
	public String get(){
		BpFundProject ssp = bpFundProjectService.getByProjectId(projectId, Short.valueOf("1"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("platFormBpFundProject",ssp);
		/*Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(ssp));
		sb.append("}");
		setJsonString(sb.toString());*/
		doJson(map);
		return SUCCESS;
	}
	
	public String save(){
		String bidPlanId = getRequest().getParameter("bidPlanId");
		try{
			/**
			 * 投资人信息开始
			 */
			List<InvestPersonInfo> investList=new ArrayList<InvestPersonInfo>();
	    	String investInfo=this.getRequest().getParameter("investInfo");
	    	String projectId = this.getRequest().getParameter("projectId");
	    	String id = this.getRequest().getParameter("id");
	    	//String slFundIntentDatas=this.getRequest().getParameter("slFundIntentDatas");
	    	
			String investIds="";
	    	if(investInfo!=null && !"".equals(investInfo)) {
	    		 String[] investArr = investInfo.split("@");
	    		 for(int i=0; i<investArr.length; i++) {
	    			String str = investArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try{
						InvestPersonInfo investPersonInfo = (InvestPersonInfo)JSONMapper.toJava(parser.nextValue(),InvestPersonInfo.class);
						investPersonInfo.setPersionType((short)1);
						if(null==bidPlanId||"".equals(bidPlanId)||"undefined".equals(bidPlanId)){
							
						}else{
							investPersonInfo.setBidPlanId(Long.parseLong(bidPlanId));
						}
						investList.add(investPersonInfo);
					}catch(Exception e){
						e.printStackTrace();
					}
	    		 }
			}
	    	if(investList!=null&&investList.size()!=0){
	   			for(int i=0;i<investList.size();i++){
				   InvestPersonInfo investPersonInfo=investList.get(i);
				   if(null==investPersonInfo.getInvestId() ||  "".equals(investPersonInfo.getInvestId())){
					   investPersonInfo.setBusinessType("SmallLoan");
					   investPersonInfo.setProjectId(Long.parseLong(projectId));
					   investPersonInfo.setMoneyPlanId(Long.parseLong(id));
					   investPersonInfoService.save(investPersonInfo);
				   }else{
					   InvestPersonInfo ps=this.investPersonInfoService.get(investPersonInfo.getInvestId());
					   BeanUtil.copyNotNullProperties(ps, investPersonInfo);
					   ps.setProjectId(Long.parseLong(projectId));
					   ps.setMoneyPlanId(Long.parseLong(id));
					   investPersonInfoService.save(ps);
					 
				   }
				}
	   		}
			setJsonString("{success:true}");
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String findMoney(){
		String bidPlanId = getRequest().getParameter("bidPlanId");
		if(bidPlanId==null||"".equals(bidPlanId)||"undefined".equals(bidPlanId)){
			BpFundProject bpFundProject = bpFundProjectService.getByProjectId(projectId, Short.valueOf("1"));
			Double money = 1d;
			if(bpFundProject!=null){
				money = bpFundProject.getPlatFormJointMoney().doubleValue();
			}
				
			jsonString = money.toString();
		}else{
			PlBidPlan bidPlan = plBidPlanService.get(Long.parseLong(bidPlanId));
			Double money = 1d;
			if(bidPlan!=null){
				money = bidPlan.getBidMoney().doubleValue();
			}
			jsonString = money.toString();
		}
		
		return SUCCESS;
	}
	
	private void doJson(Map<String, Object> map) {
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil
				.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString = buff.toString();
	}
	public String getInfo(){
		try{
			String bidPlanId = this.getRequest().getParameter("bidPlanId");
			if(bidPlanId!=null&&!bidPlanId.equals("")){
				PlBidPlan plan = plBidPlanService.get(Long.valueOf(bidPlanId));
				if(plan!=null){
					if(plan.getProType().equals("P_Dir")){
						projectId=plan.getBpPersionDirPro().getMoneyPlanId();
					}else{
						projectId=plan.getBpBusinessDirPro().getMoneyPlanId();
					}
				}
			}
			BpFundProject project=bpFundProjectService.get(projectId);
			StringBuffer buff=new StringBuffer("{success:true,data:");
			Gson gson=new Gson();
			buff.append(gson.toJson(project));
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String projectList(){
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
		PageBean<BpFundProject> pageBean = new PageBean<BpFundProject>(start,limit,getRequest());
		String managerType=this.getRequest().getParameter("managerType");
		map.put("managerType",managerType);
		StringBuffer projectStatuses =new StringBuffer("");
		switch (projectStatus) {
			case 1:
				projectStatuses.append(Constants.PROJECT_STATUS_MIDDLE).append(",")
							   .append(Constants.PROJECT_POSTPONED_STATUS_ACT).append(",")
							   .append(Constants.PROJECT_POSTPONED_STATUS_REFUSE).append(",")
							   .append(Constants.PROJECT_POSTPONED_STATUS_PASS);
				break;
			case 0:
				projectStatuses.append(Constants.PROJECT_STATUS_ACTIVITY);
				break;
			case 2:
				projectStatuses.append(Constants.PROJECT_STATUS_COMPLETE);
				break;
			case 3:
				projectStatuses.append(Constants.PROJECT_STATUS_STOP);
				break;
			case 4:
				projectStatuses.append(Constants.PROJECT_POSTPONED_STATUS_ACT);
				break;
			case 5:
				projectStatuses.append(Constants.PROJECT_POSTPONED_STATUS_PASS);
				break;
			case 6:
				projectStatuses.append(Constants.PROJECT_POSTPONED_STATUS_REFUSE);
				break;
			case 8:
				projectStatuses.append(Constants.PROJECT_STATUS_BREAKACONTRACT);
				break;
			case 10:
				projectStatuses.append(Constants.PROJECT_STATUS_SUSPENDED);
				break;
			case 15:
				projectStatuses.append(Constants.PROJECT_STATUS_MIDDLE).append(",")
							   .append(Constants.PROJECT_POSTPONED_STATUS_PASS);
				break;
		}
		map.put("projectStatuses",projectStatuses.toString());
		bpFundProjectService.projectList(pageBean,map);
	
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pageBean.getTotalCounts()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(pageBean.getResult()));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	//查询条件中的项目列表
	public String projectListQuery(){
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
		PageBean<BpFundProject> pageBean = new PageBean<BpFundProject>(start,limit,getRequest());
		
		bpFundProjectService.projectList(pageBean,map);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pageBean.getTotalCounts()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(pageBean.getResult()));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	
	public String projectLoanList(){
		String userIdsStr = "";
		PagingBean pb = new PagingBean(start, limit);
		Short[] projectStatuses = null;
		switch (projectStatus) {
		case 1:
			projectStatuses = new Short[] { Constants.PROJECT_STATUS_MIDDLE,
					Constants.PROJECT_POSTPONED_STATUS_ACT,
					Constants.PROJECT_POSTPONED_STATUS_REFUSE,
					Constants.PROJECT_POSTPONED_STATUS_PASS };
			break;
		case 0:
			projectStatuses = new Short[] { Constants.PROJECT_STATUS_ACTIVITY };
			break;
		case 2:
			projectStatuses = new Short[] { Constants.PROJECT_STATUS_COMPLETE };
			break;
		case 3:
			projectStatuses = new Short[] { Constants.PROJECT_STATUS_STOP };
			break;
		case 4:
			projectStatuses = new Short[] { Constants.PROJECT_POSTPONED_STATUS_ACT };
			break;
		case 5:
			projectStatuses = new Short[] { Constants.PROJECT_POSTPONED_STATUS_PASS };
			break;
		case 6:
			projectStatuses = new Short[] { Constants.PROJECT_POSTPONED_STATUS_REFUSE };
			break;
		case 7:
			projectStatuses = new Short[] {};
			break;
		case 8:
			projectStatuses = new Short[] { Constants.PROJECT_STATUS_BREAKACONTRACT };
			break;
		case 10:
			projectStatuses = new Short[] { Constants.PROJECT_STATUS_SUSPENDED };
			break;
		case 15:
			projectStatuses = new Short[] { Constants.PROJECT_STATUS_MIDDLE,
					Constants.PROJECT_POSTPONED_STATUS_PASS };
			break;
		}
		List<BpFundProject> list = bpFundProjectService.projectLoanList(userIdsStr, projectStatuses, pb, getRequest());
	
		Long count=bpFundProjectService.projectLoanCount(userIdsStr, projectStatuses, getRequest());
	
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(count).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public String bpProjectList(){
		PagingBean pb = new PagingBean(start, limit);
		Short[] projectStatuses = {1, 4, 6, 5};
		List<BpFundProject> list = bpFundProjectService.bpProjectList(projectStatuses, pb, getRequest());
		Long count=bpFundProjectService.bpProjectCount(projectStatuses, getRequest());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(count).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public void bpProjectListToExcel(){
		PagingBean pb = new PagingBean(start, limit);
		Short[] projectStatuses = {1, 4, 6, 5};
		List<BpFundProject> list = bpFundProjectService.bpProjectList(projectStatuses, pb, getRequest());
		String [] tableHeader = {"序号","项目名称","项目金额","项目经理","开始时间","结束时间","五级分类"};
		ExcelHelper.exportAllBpProjectList(list,tableHeader,"贷款五级分类");
	}
	
	
	public String overdueProjectList(){
		String userIdsStr = "";
		PagingBean pb = new PagingBean(start, limit);
		Short[] projectStatuses = {1, 4, 6, 5};
		List<BpFundProject> list = bpFundProjectService.overdueProjectList(projectStatuses, pb, getRequest());
		if(null !=list && !list.isEmpty()){
			for(BpFundProject bpFundProject : list){//计算出逾期本金，逾期利息，天数
				List<SlFundIntent> slList=slFundIntentService.getOverdueProjectId(bpFundProject.getProjectId(),bpFundProject.getBusinessType());
				BigDecimal overduePrincipalRepayment=BigDecimal.valueOf(0);//逾期本金
				Long overduePrincipalRepaymentDays=Long.valueOf(0);//逾期本金最长天数
				BigDecimal overdueLoanInterest=BigDecimal.valueOf(0);//逾期利息
				Long overdueLoanInterestDays=Long.valueOf(0);//逾期利息最长天数
				
				for(int i = 0;i<slList.size();i++){
					if(slList.get(i).getFundType().equals("principalRepayment")){
						overduePrincipalRepayment = overduePrincipalRepayment.add(slList.get(i).getNotMoney());
						bpFundProject.setOverduePrincipalRepayment(overduePrincipalRepayment);
						//不累加，只计算出最长的逾期天数
						if(Long.valueOf(DateUtil.getDaysBetweenDate(slList.get(i).getIntentDate(), new Date()))>overduePrincipalRepaymentDays){
							overduePrincipalRepaymentDays=Long.valueOf(DateUtil.getDaysBetweenDate(slList.get(i).getIntentDate(), new Date()));
						}
						bpFundProject.setOverduePrincipalRepaymentDays(overduePrincipalRepaymentDays);
					}else if(!slList.get(i).getFundType().equals("principalRepayment")){
						overdueLoanInterest=overdueLoanInterest.add(slList.get(i).getNotMoney());
						bpFundProject.setOverdueLoanInterest(overdueLoanInterest);
						if(Long.valueOf(DateUtil.getDaysBetweenDate(slList.get(i).getIntentDate(), new Date()))>overdueLoanInterestDays){
							overdueLoanInterestDays=Long.valueOf(DateUtil.getDaysBetweenDate(slList.get(i).getIntentDate(), new Date()));
						}
						bpFundProject.setOverdueLoanInterestDays(overdueLoanInterestDays);
					}
				}
			}
		}
		Long count=bpFundProjectService.overdueProjectCount(projectStatuses, getRequest());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(count).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public void overdueProjectListToExcel(){
		PagingBean pb = new PagingBean(start, limit);
		Short[] projectStatuses = {1, 4, 6, 5};
		List<BpFundProject> list = bpFundProjectService.overdueProjectList(projectStatuses, pb, getRequest());
		if(null !=list && !list.isEmpty()){
			for(BpFundProject bpFundProject : list){//计算出逾期本金，逾期利息，天数
				List<SlFundIntent> slList=slFundIntentService.getOverdueProjectId(bpFundProject.getProjectId(),bpFundProject.getBusinessType());
				BigDecimal overduePrincipalRepayment=BigDecimal.valueOf(0);//逾期本金
				Long overduePrincipalRepaymentDays=Long.valueOf(0);//逾期本金最长天数
				BigDecimal overdueLoanInterest=BigDecimal.valueOf(0);//逾期利息
				Long overdueLoanInterestDays=Long.valueOf(0);//逾期利息最长天数
				
				for(int i = 0;i<slList.size();i++){
					if(slList.get(i).getFundType().equals("principalRepayment")){
						overduePrincipalRepayment = overduePrincipalRepayment.add(slList.get(i).getIncomeMoney());
						bpFundProject.setOverduePrincipalRepayment(overduePrincipalRepayment);
						//不累加，只计算出最长的逾期天数
						if(Long.valueOf(DateUtil.getDaysBetweenDate(slList.get(i).getIntentDate(), new Date()))>overduePrincipalRepaymentDays){
							overduePrincipalRepaymentDays=Long.valueOf(DateUtil.getDaysBetweenDate(slList.get(i).getIntentDate(), new Date()));
						}
						bpFundProject.setOverduePrincipalRepaymentDays(overduePrincipalRepaymentDays);
					}else if(!slList.get(i).getFundType().equals("principalRepayment")){
						overdueLoanInterest=overdueLoanInterest.add(slList.get(i).getIncomeMoney());
						bpFundProject.setOverdueLoanInterest(overdueLoanInterest);
						if(Long.valueOf(DateUtil.getDaysBetweenDate(slList.get(i).getIntentDate(), new Date()))>overdueLoanInterestDays){
							overdueLoanInterestDays=Long.valueOf(DateUtil.getDaysBetweenDate(slList.get(i).getIntentDate(), new Date()));
						}
						bpFundProject.setOverdueLoanInterestDays(overdueLoanInterestDays);
					}
				}
			}
		}
		String [] tableHeader = {"序号","项目名称","项目金额","项目经理","开始时间","结束时间","本金逾期金额","本金逾期天数（最长）","利息逾期金额","利息逾期天数（最长）"};
		ExcelHelper.exportAllOverdueProjectList(list,tableHeader,"贷后逾期预警");
	}
	
	public String IndustryProjectList(){
		PagingBean pb = new PagingBean(start, limit);
		Short[] projectStatuses = {1, 4, 6, 5};
		List<BpFundProject> list = bpFundProjectService.IndustryProjectList(projectStatuses, pb, getRequest());
		Long count=bpFundProjectService.IndustryProjectCount(projectStatuses, getRequest());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(count).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public void industryProjectListToExcel(){
		PagingBean pb = new PagingBean(start, limit);
		Short[] projectStatuses = {1, 4, 6, 5};
		List<BpFundProject> list = bpFundProjectService.IndustryProjectList(projectStatuses, pb, getRequest());
		String [] tableHeader = {"序号","行业类型","项目名称","项目金额","项目经理","开始时间","结束时间","五级分类"};
		ExcelHelper.exportAllIndustryProjectList(list,tableHeader,"行业预警");
	}
	
	public Boolean getIsGrantedShowAllProjects() {
		return isGrantedShowAllProjects;
	}

	public void setIsGrantedShowAllProjects(Boolean isGrantedShowAllProjects) {
		this.isGrantedShowAllProjects = isGrantedShowAllProjects;
	}

	public Short getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Short projectStatus) {
		this.projectStatus = projectStatus;
	}

	public BpFundProject getPlatFormBpFundProject() {
		return platFormBpFundProject;
	}

	public void setPlatFormBpFundProject(BpFundProject platFormBpFundProject) {
		this.platFormBpFundProject = platFormBpFundProject;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}
