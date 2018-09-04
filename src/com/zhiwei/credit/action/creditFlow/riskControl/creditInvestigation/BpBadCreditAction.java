package com.zhiwei.credit.action.creditFlow.riskControl.creditInvestigation;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpBadCredit;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpLoneExternal;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;
import com.zhiwei.credit.service.creditFlow.riskControl.creditInvestigation.BpBadCreditService;
/**
 * 
 * @author 
 *
 */
public class BpBadCreditAction extends BaseAction{
	@Resource
	private BpBadCreditService bpBadCreditService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private  BpFundProjectService bpFundProjectService;
	@Resource
	private PersonService personService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private SlCompanyMainService slCompanyMainService;
	@Resource
	private SlPersonMainService slPersonMainService;
	
	private BpBadCredit bpBadCredit;
	
	private Long creditId;
	
	private Long moneyId;
	private String moneyType;

	
	public Long getMoneyId() {
		return moneyId;
	}

	public void setMoneyId(Long moneyId) {
		this.moneyId = moneyId;
	}

	public String getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}

	public Long getCreditId() {
		return creditId;
	}

	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}

	public BpBadCredit getBpBadCredit() {
		return bpBadCredit;
	}

	public void setBpBadCredit(BpBadCredit bpBadCredit) {
		this.bpBadCredit = bpBadCredit;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		PagingBean pb = new PagingBean(start, limit);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
	    List<BpBadCredit> listS=bpBadCreditService.bpBadCreditList(pb, getRequest());
	    Long scount=bpBadCreditService.bpBadCreditCount(getRequest());
		     buff=buff.append(scount).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			 buff.append(gson.toJson(listS));
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
				bpBadCreditService.remove(new Long(id));
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
		BpBadCredit bpBadCredit=bpBadCreditService.get(creditId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpBadCredit));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpBadCredit.getCreditId()==null){
			AppUser user= ContextUtil.getCurrentUser();
			bpBadCredit.setCreator(user.getFullname());
			bpBadCredit.setCreatorId(user.getUserId());
			bpBadCreditService.save(bpBadCredit);
		}else{
			BpBadCredit orgBpBadCredit=bpBadCreditService.get(bpBadCredit.getCreditId());
			try{
				BeanUtil.copyNotNullProperties(orgBpBadCredit, bpBadCredit);
				bpBadCreditService.save(orgBpBadCredit);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 外部借款人登记项目记录导出到Excel中
	 */
	public void outExcel(){
		 List<BpBadCredit> listS=null;
	   
		 listS=bpBadCreditService.bpBadCreditList(null, getRequest());
			String [] tableHeader = {"序号","业务品种","上报类型","项目名称","借款人","证件号码","金额","借款单位","逾期天数","不良类型","上报人","日期"};
		try {
			ExcelHelper.exportAllBpBadCreditList(listS,tableHeader,"不良征信登记列表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String getByMoneyIdType(){
		BpBadCredit bpBadCredit=bpBadCreditService.getByMoneyIdType(moneyId, moneyType);
		if(null==bpBadCredit){
			bpBadCredit =new BpBadCredit();
			if(null!=moneyType && "fund".equals(moneyType)){
				SlFundIntent slFundIntent=slFundIntentService.get(moneyId);
				bpBadCredit.setLoneMoney(slFundIntent.getNotMoney());
				bpBadCredit.setProjectName(slFundIntent.getProjectName());
				bpBadCredit.setReportType(Short.valueOf("0"));
				bpBadCredit.setMoneyId(moneyId);
				bpBadCredit.setMoneyType(moneyType);
				bpBadCredit.setCreateDate(new Date());
			    AppUser user=ContextUtil.getCurrentUser();
			    bpBadCredit.setCreatorId(user.getUserId());
			    bpBadCredit.setCreator(user.getFullname());
			    long intervalMilli = new Date().getTime() - slFundIntent.getIntentDate().getTime();
			    Long days=(Long) (intervalMilli / (24 * 60 * 60 * 1000));
			    bpBadCredit.setOverdueDays(days);
			    bpBadCredit.setBusinessType(slFundIntent.getBusinessType());
			    if(null!=slFundIntent.getBusinessType() && "SmallLoan".equals(slFundIntent.getBusinessType())){
			    	BpFundProject  bpFundProject=bpFundProjectService.get(slFundIntent.getPreceptId());
			    	bpBadCredit.setCustomerType(bpFundProject.getOppositeType());
			    	if(null!=bpFundProject.getOppositeType() && "person_customer".equals(bpFundProject.getOppositeType())){
			    		Person person=personService.getById(Integer.valueOf(bpFundProject.getOppositeID().toString()));
			    		bpBadCredit.setCustomerId(person.getId());
			    		bpBadCredit.setCustomerName(person.getName());
			    		bpBadCredit.setCardnumber(person.getCardnumber());
			    		
			    	}else if("company_customer".equals(bpFundProject.getOppositeType())){
			    		Enterprise enterprise1= enterpriseService.getById(Integer.valueOf(bpFundProject.getOppositeID().toString()));
			    		bpBadCredit.setCustomerId(enterprise1.getId());
			    		bpBadCredit.setCustomerName(enterprise1.getEnterprisename());
			    		bpBadCredit.setCardnumber(enterprise1.getOrganizecode());
			    	}
			    	if(null!=bpFundProject.getMineType() && "company_ourmain".equals(bpFundProject.getMineType())){
			    		SlCompanyMain slCompanyMain =slCompanyMainService.get(bpFundProject.getMineId());
			    		bpBadCredit.setLoneCompany(slCompanyMain.getCorName());
			    	}
			    	else if(null!=bpFundProject.getMineType() && "person_ourmain".equals(bpFundProject.getMineType())){
			    		SlPersonMain slPersonMain =slPersonMainService.get(bpFundProject.getMineId());
			    		bpBadCredit.setLoneCompany(slPersonMain.getName());
			    	}
			    }
				
			}
		}else{
			SlFundIntent slFundIntent=slFundIntentService.get(moneyId);
		    long intervalMilli = new Date().getTime() - slFundIntent.getIntentDate().getTime();
		    Long days=(Long) (intervalMilli / (24 * 60 * 60 * 1000));
		    bpBadCredit.setOverdueDays(days);
		    bpBadCredit.setCreateDate(new Date());
		    bpBadCredit.setLoneMoney(slFundIntent.getNotMoney());
		}
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpBadCredit));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
}
