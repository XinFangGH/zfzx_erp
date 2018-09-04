package com.zhiwei.credit.action.creditFlow.financeProject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.creditFlow.finance.SlFundIntentAction;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.util.MoneyFormat;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.financeProject.VFinancingProject;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.financeProject.VFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordService;
import com.zhiwei.credit.service.customer.InvestPersonService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.util.ProjectActionUtil;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class FlFinancingProjectAction extends BaseAction{
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private VFinancingProjectService vFinancingProjectService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private SlEarlyRepaymentRecordService slEarlyRepaymentRecordService;
	@Resource
	private InvestPersonService investPersonService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private SlCompanyMainService companyMainService;
	@Resource
	private SlPersonMainService slPersonMainService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private AreaDicService areaDicService;
	private FlFinancingProject flFinancingProject;
	private InvestPerson investPerson;
	private Enterprise enterprise;
	private EnterpriseBank enterpriseBank;
	private Long projectId;
	private Boolean isGrantedShowAllProjects;
	private SlEarlyRepaymentRecord slEarlyRepaymentRecord;

	/**
	 * 项目信息列表
	 * @author lisl
	 * @return
	 */
	public String projectList(){
		String userIdsStr = "";
		PagingBean pb = new PagingBean(start, limit);
		String roleTypeStr = ContextUtil.getRoleTypeSession();
		if(!isGrantedShowAllProjects  && !roleTypeStr.equals("control")) {//如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();//当前登录用户以及其所有下属用户
		}
		List<VFinancingProject> list = vFinancingProjectService.getProjectList(userIdsStr, pb, getRequest(),null);
		String [] appstr = null;
		for(VFinancingProject vp : list) {
			String appuers = "";
			if(null != vp.getBusinessManager()) {
				appstr = vp.getBusinessManager().split(",");
				Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
				for(AppUser s : userSet){
					appuers += s.getFamilyName()+",";
				}
			}
			if(appuers.length()>0) {
				appuers = appuers.substring(0,appuers.length() - 1);
			}
			vp.setBusinessManagerValue(appuers);
			
			//获取当前所处任务的处理人
			String executor = "";//任务执行人
			Set<AppUser> appExecutor = jbpmService.getNodeHandlerUsers(vp.getDefId(), vp.getActivityName());
			for (AppUser su : appExecutor) {
				executor = executor + su.getFamilyName() + ",";
			}
			if (executor.length() > 0) {
				executor = executor.substring(0, executor.length() - 1);
			}
			vp.setExecutor(executor);
		}
		//int counts = vFinancingProjectService.getProjectList(userIdsStr, null, getRequest()).size();
		Type type=new TypeToken<List<VFinancingProject>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list==null?0:list.size()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	public String askForEarlyRepayment() { //提前还款

		try {
			Long proId = Long.valueOf(this.getRequest().getParameter("projectId"));
			String fundIntentJsonDataSuperviseRecord = this.getRequest().getParameter("intent_plan_earlyRepayment");
			FlFinancingProject project = this.flFinancingProjectService.get(proId);
			List<SlFundIntent> slFundIntentsSuperviseRecord = savejsonintent(
					fundIntentJsonDataSuperviseRecord, project,
					Short.parseShort("0"));
			slEarlyRepaymentRecord.setProjectId(proId);
			slEarlyRepaymentRecord.setBusinessType(project.getBusinessType());
			AppUser user=ContextUtil.getCurrentUser();
			slEarlyRepaymentRecord.setCreator(user.getFullname());
			slEarlyRepaymentRecord.setOpTime(new Date());
			Long slEarlyRepaymentId = slEarlyRepaymentRecordService.save(slEarlyRepaymentRecord).getSlEarlyRepaymentId();
			SlEarlyRepaymentRecord  sr = slEarlyRepaymentRecordService.get(slEarlyRepaymentId);
			project.setPayProjectMoney(project.getPayProjectMoney()==null?BigDecimal.valueOf(0):project.getPayProjectMoney().add(sr.getEarlyProjectMoney()));
			project.setIsAheadPay(Short.valueOf("1"));
			flFinancingProjectService.save(project);
			List<SlFundIntent> historyList = slFundIntentService.getListOfHistory(proId, "Financing", slFundIntentsSuperviseRecord.get(0).getIntentDate());
			for(SlFundIntent sf : historyList) {
				sf.setIsValid(Short.valueOf("1"));
				sf.setEarlyOperateId(slEarlyRepaymentId);
				slFundIntentService.save(sf);
			}
			for (SlFundIntent b : slFundIntentsSuperviseRecord) {
				b.setIsValid(Short.valueOf("0"));
				b.setSlEarlyRepaymentId(slEarlyRepaymentId);
				if (ContextUtil.getLoginCompanyId() != null) {
					b.setCompanyId(ContextUtil.getLoginCompanyId());
				} else {
					b.setCompanyId(Long.valueOf("1"));
				}
				slFundIntentService.save(b);
			}
			
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("融后管理——提前还款办理出错:" + e.getMessage());
			return "error";
		}
	}
	public void exportExcel() {
		String userIdsStr = "";
		String roleTypeStr = ContextUtil.getRoleTypeSession();
		if(!isGrantedShowAllProjects  && !roleTypeStr.equals("control")) {//如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();//当前登录用户以及其所有下属用户
		}
		Map<String, String> map = new HashMap<String, String>();
		String departId = this.getRequest().getParameter("departmentId");
		Organization org = null;
		if(null !=departId && !departId.equals("")){//通过部门查询项目&
			org = organizationService.get(new Long(departId));
			List<AppUser> users = new ArrayList<AppUser>();
			if (org.getOrgType() == 0 || org.getOrgType() == 1) {
				List<Organization> orgs = this.organizationService.getOrganizationsGroup(org);
				if (orgs != null && orgs.size() > 0) {
					users = appUserService.getUsersByCompany(orgs, null, null);
				}
			}else {
				List<Organization> orgs = new ArrayList<Organization>();
				orgs = this.organizationService.getOrganizationsGroup(org);
				orgs.add(org);
				users = appUserService.getUsersByCompany(orgs, null, null);
			}
			StringBuffer sbff = new StringBuffer();
			for(AppUser uo :users){
				String userId = uo.getUserId().toString();
				sbff.append(userId);
				sbff.append(",");
			}
			if(null !=users && users.size()>0){
				sbff.deleteCharAt(sbff.length()-1);
			}
			setJsonString(sbff.toString());
			map.put("departmentId",sbff.toString());
		}
		List<VFinancingProject> list = vFinancingProjectService.getProjectList(userIdsStr, null, getRequest(),map);
		String [] appstr = null;
		for(VFinancingProject vp : list) {
			String appuers = "";
			if(null != vp.getBusinessManager()) {
				appstr = vp.getBusinessManager().split(",");
				Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
				for(AppUser s : userSet){
					appuers += s.getFamilyName()+",";
				}
			}
			if(appuers.length()>0) {
				appuers = appuers.substring(0,appuers.length() - 1);
			}
			vp.setBusinessManagerValue(appuers);
		}
		String [] tableHeader = {"序号","项目编号","融资资金","融资起始日","融资利率","计息方式","业务经理","是否进行提前还款"};
		try {
			ExcelHelper.export(list,tableHeader,"融资业绩表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return null;
	}
	/*public String exportExcel() {
		String userIdsStr = "";
		String roleTypeStr = ContextUtil.getRoleTypeSession();
		if(!isGrantedShowAllProjects  && !roleTypeStr.equals("control")) {//如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();//当前登录用户以及其所有下属用户
		}
		List<VFinancingProject> list = vFinancingProjectService.getProjectList(userIdsStr, null, getRequest());
		String [] appstr = null;
		for(VFinancingProject vp : list) {
			String appuers = "";
			if(null != vp.getBusinessManager()) {
				appstr = vp.getBusinessManager().split(",");
				Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
				for(AppUser s : userSet){
					appuers += s.getFamilyName()+",";
				}
			}
			if(appuers.length()>0) {
				appuers = appuers.substring(0,appuers.length() - 1);
			}
			vp.setBusinessManagerValue(appuers);
		}
		String [] tableHeader = {"序号","项目编号","融资资金","融资起始日","融资利率","计息方式","业务经理","是否进行提前还款"};
		try {
			ExcelHelper.export(list,tableHeader,"融资业绩表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}*/
	
	public  String update(){
		 
    	StringBuffer sb = new StringBuffer("{success:true,msg:");
    	String ddd="ddd";
    	sb.append("'"+ddd+"'");
    	sb.append("}");
    	String degree=this.getRequest().getParameter("degree");
    	
    	if(null!=degree && !"".equals(degree)){
    		flFinancingProject.setAppUserId(degree);
    	}
    	String isPreposePayAccrual=this.getRequest().getParameter("isPreposePayAccrualCheck");
    	if(isPreposePayAccrual!=null&&!"".equals(isPreposePayAccrual)){
    		flFinancingProject.setIsPreposePayAccrual(1);
    	}else {
    		flFinancingProject.setIsPreposePayAccrual(0);
    	}
    	/**
    	 * 年化净利率
    	 */
    	ProjectActionUtil pu= new ProjectActionUtil();
    	pu.getFinanceMode(flFinancingProject);
    	Integer flag=this.flFinancingProjectService.updateInfo(flFinancingProject, investPerson,enterpriseBank);
    	
    	if (flag == 0) {
    		
		} else {
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
		}
    	setJsonString(sb.toString());
    	return SUCCESS;
    }
	
	public  String complate(){
		StringBuffer sb = new StringBuffer("{success:true,msg:");
		try {
			FlFinancingProject project = flFinancingProjectService.get(projectId);
			project.setProjectStatus(Short.valueOf("2"));
			flFinancingProjectService.merge(project);
			sb.append("'项目结项成功！'");
	    	sb.append("}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setJsonString(sb.toString());
    	return SUCCESS;
    }
	public String getPayIntentPeriod(){
		String payintentPeriod=this.getRequest().getParameter("payintentPeriod");
		StringBuffer buff = new StringBuffer("[");
		if(null!=payintentPeriod && !"".equals(payintentPeriod)){
			for (int i=0;i<Integer.valueOf(payintentPeriod)+1;i++) {
				buff.append("['").append((i)).append("','")
						.append((i)).append("'],");
			}
			if (Integer.valueOf(payintentPeriod) > 0) {
				buff.deleteCharAt(buff.length() - 1);
			}
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	public String earlyrepaymentRecords() {
		List<SlEarlyRepaymentRecord> list = slEarlyRepaymentRecordService
				.listByProIdAndType(projectId,"Financing");
		FlFinancingProject sp = flFinancingProjectService.get(projectId);
		BigDecimal money=sp.getProjectMoney();
		for (SlEarlyRepaymentRecord srr : list) {
			if(null!=srr.getEarlyProjectMoney()){
				srr.setSurplusProjectMoney(money.subtract(
						srr.getEarlyProjectMoney()));
				money=money.subtract(srr.getEarlyProjectMoney());
			}
			List<SlFundIntent> slist=slFundIntentService.getlistbyslEarlyRepaymentId(srr.getSlEarlyRepaymentId(), "Financing", projectId);
			Long slSuperviseRecordId=new Long(0);
			if(null!=slist && slist.size()>0){
				SlFundIntent sf=slist.get(0);
				if(null!=sf.getSlSuperviseRecordId()){
					slSuperviseRecordId=sf.getSlSuperviseRecordId();
				}
			}
			srr.setPayintentPeriod(slSuperviseRecordId.intValue());
			srr.setDateMode(sp.getProjectName());
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(list.size()).append(",result:");

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");

		jsonString = buff.toString();
		return SUCCESS;
	}
	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public FlFinancingProject getFlFinancingProject() {
		return flFinancingProject;
	}

	public void setFlFinancingProject(FlFinancingProject flFinancingProject) {
		this.flFinancingProject = flFinancingProject;
	}
	
	

	public InvestPerson getInvestPerson() {
		return investPerson;
	}

	public void setInvestPerson(InvestPerson investPerson) {
		this.investPerson = investPerson;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	   public  List<SlFundIntent>  savejsonintent(String fundIntentJsonData,FlFinancingProject slSmallloanProject,Short flag){
		   List<SlFundIntent> slFundIntents=new ArrayList<SlFundIntent>();
		   if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {

				String[] shareequityArr = fundIntentJsonData.split("@");

				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));

					try {

						SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper
								.toJava(parser.nextValue(), SlFundIntent.class);
						SlFundIntent1.setProjectId(slSmallloanProject.getProjectId());
							SlFundIntent1.setProjectName(slSmallloanProject.getProjectName());
							SlFundIntent1.setProjectNumber(slSmallloanProject.getProjectNumber());
					
						if (null == SlFundIntent1.getFundIntentId()) {

							BigDecimal lin = new BigDecimal(0.00);

							   if(SlFundIntent1.getIncomeMoney().compareTo(lin)==0){
						        	SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						        }else{
						        	SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						        	
						        }
									SlFundIntent1.setAfterMoney(new BigDecimal(0));
									SlFundIntent1.setAccrualMoney(new BigDecimal(0));
									SlFundIntent1.setFlatMoney(new BigDecimal(0));
									Short isvalid=0;
									SlFundIntent1.setIsValid(isvalid);
									SlFundIntent1.setIsCheck(flag);
									SlFundIntent1.setBusinessType("Financing");
									slFundIntents.add(SlFundIntent1);
						} else {
							 BigDecimal lin = new BigDecimal(0.00);
							   if(SlFundIntent1.getIncomeMoney().compareTo(lin)==0){
						        	SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						        }else{
						        	SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						        	
						        }
							SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
							BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
							slFundIntent2.setBusinessType("Financing");
							slFundIntent2.setIsCheck(flag);
							slFundIntents.add(slFundIntent2);
						}

					} catch (Exception e) {
						logger.error("FlFinancingProjectAction款项计划"+e.getMessage());
						e.printStackTrace();
					}

				}
			}
		   return slFundIntents;
	   }
	   public List<SlActualToCharge>  savejsoncharge(String slActualToChargeJsonData,FlFinancingProject slSmallloanProject,Short flag){
		   List<SlActualToCharge> slActualToCharges=new ArrayList<SlActualToCharge>();
		   if (null != slActualToChargeJsonData && !"".equals(slActualToChargeJsonData)) {

				String[] shareequityArr = slActualToChargeJsonData.split("@");

				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
						String[] strArr=str.split(",");
						String typestr="";
						if(strArr.length==7){
							typestr=strArr[1]; 
						}else{
							typestr=strArr[0];
						}
						String typeId="";
						String typename="";
						if(typestr.endsWith("\"")==true){
						   typename=typestr.substring(typestr.indexOf(":")+2,typestr.length()-1);
						}else{
							typeId=typestr.substring(typestr.indexOf(":")+1,typestr.length());
						}
						SlPlansToCharge slPlansToCharge=new SlPlansToCharge();
						
						if(!typename.equals("")){
							
									List<SlPlansToCharge> list=slPlansToChargeService.getAll();
									int k=0;
									for(SlPlansToCharge p:list){
										if(p.getName().equals(typename) && p.getBusinessType().equals("Financing")){
											k++;
										}
									}
									
									if(k==0){
										slPlansToCharge.setName(typename);
										slPlansToCharge.setIsType(1);
										slPlansToCharge.setIsValid(0);
										slPlansToCharge.setBusinessType("Financing");
										slPlansToChargeService.save(slPlansToCharge);
										if(strArr.length==9){
												str="{";
											for(int j=0;j<=strArr.length-2;j++){
												if(j !=1){
												str=strArr[j]+",";
												}
											}
											str=str+strArr[strArr.length-1];
							
										}else{
											str="{";
											for(int j=1;j<=strArr.length-2;j++){
												str=str+strArr[j]+",";
											}
											str=str+strArr[strArr.length-1];
										}
									}
						}else{
							long typeid=Long.parseLong(typeId);
							slPlansToCharge=slPlansToChargeService.get(typeid);
							
						}
						

					JSONParser parser = new JSONParser(new StringReader(str));

					try {

						SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper.toJava(parser.nextValue(), SlActualToCharge.class);
								
						
						slActualToCharge.setProjectId(slSmallloanProject.getProjectId());
							slActualToCharge.setProjectName(slSmallloanProject.getProjectName());
							slActualToCharge.setProjectNumber(slSmallloanProject.getProjectNumber());
							
						slActualToCharge.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
						if (null == slActualToCharge.getActualChargeId()) {

						slActualToCharge.setAfterMoney(new BigDecimal(0));
						slActualToCharge.setFlatMoney(new BigDecimal(0));
						if(slActualToCharge.getIncomeMoney().equals(new BigDecimal(0.00))){
								slActualToCharge.setNotMoney(slActualToCharge.getPayMoney());
							}else{
								slActualToCharge.setNotMoney(slActualToCharge.getIncomeMoney());
							}
						slActualToCharge.setBusinessType("Financing");
					    slActualToCharge.setIsCheck(flag);
						slActualToCharges.add(slActualToCharge);
						} else {
							
							SlActualToCharge slActualToCharge1 = slActualToChargeService.get(slActualToCharge.getActualChargeId());
							BeanUtil.copyNotNullProperties(slActualToCharge1,slActualToCharge);
							if(slActualToCharge1.getIncomeMoney().equals(new BigDecimal(0.00))){
								slActualToCharge1.setNotMoney(slActualToCharge.getPayMoney());
							}else{
								slActualToCharge1.setNotMoney(slActualToCharge.getIncomeMoney());
							}
							slActualToCharge1.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
							slActualToCharge1.setBusinessType("Financing");
							slActualToCharge1.setIsCheck(flag);
							slActualToCharges.add(slActualToCharge1);
						}


					} catch (Exception e) {
						e.printStackTrace();
						logger.error("FlFinancingProjectAction款项计划-费用："+e.getMessage());
					}
				}
				}
	   	
		   return slActualToCharges;
	   }
	   
	/**
	 * 获取融资项目信息
	 * @return
	 */
	public String getInfo() {
		
		String appuers = "";
		String mineName = "";
		String projectId = this.getRequest().getParameter("flProjectId"); // 项目ID
		String taskId = this.getRequest().getParameter("flTaskId"); // 任务ID
		try {
			if(projectId!=null&&!"".equals(projectId)){
				Map<String, Object> map = new HashMap<String, Object>();
				FlFinancingProject project = this.flFinancingProjectService.get(Long.valueOf(projectId));
				InvestPerson p = investPersonService.get(project.getOppositeID());
				String areaId = p.getAreaId();
				if(null!=areaId && !areaId.equals("")){
					StringBuffer sb = new StringBuffer();
					String[] adids = areaId.split(",");
					if(adids!=null){
						for(String id: adids){
							if(id!=null){
								if(areaDicService.getNode(Integer.parseInt(id))!=null){
									sb.append(((AreaDic)areaDicService.getNode(Integer.parseInt(id)).get(0)).getText()).append("-");
								}
							}
						}
						if(sb.length()!=0){
							p.setAreaText(sb.toString().substring(0,sb.length()-1 ).toString());
						}
					}
				}
				
				List<EnterpriseBank> list = enterpriseBankService.getBankList(p.getPerId().intValue(), Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("1")) ;
				if(list!=null&&list.size()!=0) {
					map.put("enterpriseBank", list.get(0));
				}
				
				if (project.getAppUserId()!=null) {
					String[] appstr = project.getAppUserId().split(",");
					Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
					for (AppUser s : userSet) {
						appuers += s.getFullname() + ",";
					}
				}
				if (appuers.length()>0) {
					appuers = appuers.substring(0, appuers.length() - 1);
				}
				project.setAppUsers(appuers);
//				if(project.getMineType().equals("company_ourmain")){
//					mineName=this.companyMainService.get(project.getMineId()).getCorName();
//				}else if (project.getMineType().equals("person_ourmain")){
//					mineName=this.slPersonMainService.get(project.getMineId()).getName();
//				}
				Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
				if(flag==false){
			        if(project.getMineType().equals("company_ourmain")){
				      mineName = this.companyMainService.get(project.getMineId()).getCorName();
				      //getMineId 为 organization 下的 id 原来是 企业主体下的id
				      //mineName = this.organizationService.get(project.getMineId()).getOrgName();
				     }else if (project.getMineType().equals("person_ourmain")){
				      mineName = this.slPersonMainService.get(project.getMineId()).getName();
				     }
		       }else{
			     //if(project.getMineType().equals("company_ourmain")){
			      //mineName = this.companyMainService.get(project.getMineId()).getCorName();
			      //getMineId 为 organization 下的 id 原来是 企业主体下的id
			      mineName = this.organizationService.get(project.getMineId()).getOrgName();
			     /*}else if (project.getMineType().equals("person_ourmain")){
			      mineName = this.slPersonMainService.get(project.getMineId()).getName();
			     }*/
			       }
				if (taskId!=null&&!"".equals(taskId)) {
					ProcessForm pform = processFormService.getByTaskId(taskId);
					if (pform == null) {
						pform = creditProjectService.getCommentsByTaskId(taskId);
					}
					if (pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())) {
						map.put("comments", pform.getComments());
					}
				}
				
				String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
				String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
				String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
				
				map.put("mineName", mineName);
				map.put("businessTypeKey",businessTypeKey);
				map.put("operationTypeKey",operationTypeKey);
				map.put("definitionTypeKey",definitionTypeKey);
				map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getName());
				map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
				
				map.put("investPerson", p);
				map.put("flFinancingProject", project);
				
				StringBuffer buff = new StringBuffer("{success:true,data:");
				JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
				buff.append(json.serialize(map));
				buff.append("}");
				jsonString = buff.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Boolean getIsGrantedShowAllProjects() {
		return isGrantedShowAllProjects;
	}

	public void setIsGrantedShowAllProjects(Boolean isGrantedShowAllProjects) {
		this.isGrantedShowAllProjects = isGrantedShowAllProjects;
	}

	public EnterpriseBank getEnterpriseBank() {
		return enterpriseBank;
	}

	public void setEnterpriseBank(EnterpriseBank enterpriseBank) {
		this.enterpriseBank = enterpriseBank;
	}

	public SlEarlyRepaymentRecord getSlEarlyRepaymentRecord() {
		return slEarlyRepaymentRecord;
	}

	public void setSlEarlyRepaymentRecord(
			SlEarlyRepaymentRecord slEarlyRepaymentRecord) {
		this.slEarlyRepaymentRecord = slEarlyRepaymentRecord;
	}
	/**
	 * 融资无审批流程--资料录入
	 * @return
	 */
	public String saveNoApproval(){
    	StringBuffer sb = new StringBuffer("{success:true,msg:");
    	String ddd="ddd";
    	sb.append("'"+ddd+"'");
    	sb.append("}");

    	String degree=this.getRequest().getParameter("degree");
    	
    	if(null!=degree && !"".equals(degree)){
    		flFinancingProject.setAppUserId(degree);
    	}
    	String isPreposePayAccrual=this.getRequest().getParameter("isPreposePayAccrualCheck");
//    	String projectBigMoney = this.getRequest().getParameter("flFinancingProject.projectBigMoney");
//    	flFinancingProject.setProjectBigMoney(projectBigMoney);
    	if(isPreposePayAccrual!=null&&!"".equals(isPreposePayAccrual)){
    		flFinancingProject.setIsPreposePayAccrual(1);
    	}else {
    		flFinancingProject.setIsPreposePayAccrual(0);
    	}
    	/**
    	 * 年化净利率
    	 */
    	ProjectActionUtil pu= new ProjectActionUtil();
    	pu.getFinanceMode(flFinancingProject);
    	Integer flag=this.flFinancingProjectService.updateInfo(flFinancingProject, investPerson,enterpriseBank);
    	
    	if (flag == 0) {
    		
		} else {
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
		}
    	setJsonString(sb.toString());
    	return SUCCESS;
    }
	/**
	 * 融资无审批流程--项目编辑
	 * @return
	 */
	public String updateEdit(){    
    	StringBuffer sb = new StringBuffer("{success:true,msg:");
    	String ddd="ddd";
    	sb.append("'"+ddd+"'");
    	sb.append("}");
    	String degree=this.getRequest().getParameter("degree");
    	if(null!=degree && !"".equals(degree)){
    		flFinancingProject.setAppUserId(degree);
    	}
    	String isPreposePayAccrual=this.getRequest().getParameter("isPreposePayAccrualCheck");
    	if(isPreposePayAccrual!=null&&!"".equals(isPreposePayAccrual)){
    		flFinancingProject.setIsPreposePayAccrual(1);
    	}else {
    		flFinancingProject.setIsPreposePayAccrual(0);
    	}
    	/**
    	 * 年化净利率
    	 */
    	ProjectActionUtil pu= new ProjectActionUtil();
    	pu.getFinanceMode(flFinancingProject);
    	Integer flag=this.flFinancingProjectService.updateInfo(flFinancingProject, investPerson,enterpriseBank);
    	setJsonString(sb.toString());
		try {
			String fundIntentJsonData = this.getRequest().getParameter("fundIntentJson");
			String isDeleteAllFundIntent = this.getRequest().getParameter("isDeleteAllFundIntent");
			List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
			FlFinancingProject project = flFinancingProjectService.get(flFinancingProject.getProjectId());
			BeanUtil.copyNotNullProperties(project, flFinancingProject);
			flFinancingProjectService.save(project);
			SlFundIntentAction f1 = new SlFundIntentAction();
		    //调用另一个action的方法
			slFundIntents = f1.getFundIntents(fundIntentJsonData, project, Short.parseShort("1"));
			if (isDeleteAllFundIntent.equals("1")) {
				List<SlFundIntent> all = slFundIntentService.getByProjectId(flFinancingProject.getProjectId(), "Financing");
				for (SlFundIntent s : all) {
					if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
						slFundIntentService.remove(s.getFundIntentId());
					}
				}
			}
			for (SlFundIntent s : slFundIntents) {
				slFundIntentService.save(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	public String changeMoney(){
		String my = this.getRequest().getParameter("moneys");
		BigDecimal moneys = new BigDecimal(my);
		String dw = "元整";
		String bigmoneys = MoneyFormat.getInstance().hangeToBig(moneys)+dw;
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(bigmoneys);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
		return null;
	}/**
	 * 财务确认收款--融资无审批流程
	 * @return
	 */
	public String updateNoApproval(){
		try {
			String fundIntentJsonData = this.getRequest().getParameter("fundIntentJson");
			String isDeleteAllFundIntent = this.getRequest().getParameter("isDeleteAllFundIntent");
			List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
			FlFinancingProject project = flFinancingProjectService.get(flFinancingProject.getProjectId());
			BeanUtil.copyNotNullProperties(project, flFinancingProject);
			flFinancingProjectService.save(project);
			SlFundIntentAction f1 = new SlFundIntentAction();
		    //调用另一个action的方法
			slFundIntents = f1.getFundIntents(fundIntentJsonData, project, Short.parseShort("1"));
			if (isDeleteAllFundIntent.equals("1")) {
				List<SlFundIntent> all = slFundIntentService.getByProjectId(flFinancingProject.getProjectId(), "Financing");
				for (SlFundIntent s : all) {
					if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
						slFundIntentService.remove(s.getFundIntentId());
					}
				}
			}
			for (SlFundIntent s : slFundIntents) {
				slFundIntentService.save(s);
			}
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
}
