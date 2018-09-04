package com.zhiwei.credit.service.creditFlow.common.impl;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.BeanUtils;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.webServices.custom.BaseCustomService;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.archives.OurArchivesMaterialsDao;
import com.zhiwei.credit.dao.creditFlow.assuretenet.OurProcreditAssuretenetDao;
import com.zhiwei.credit.dao.creditFlow.assuretenet.SlProcreditAssuretenetDao;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.dao.creditFlow.common.VCommonProjectFlowDao;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObAccountDealInfoDao;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObSystemAccountDao;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.customer.CsInvestmentpersonDao;
import com.zhiwei.credit.dao.creditFlow.customer.common.EnterpriseBankDao;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.BpMoneyBorrowDemandDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlActualToChargeDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlPlansToChargeDao;
import com.zhiwei.credit.dao.creditFlow.financeProject.FlFinancingProjectDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.BpFundProjectDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountRecordDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.project.GLGuaranteeloanProjectDao;
import com.zhiwei.credit.dao.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProjectDao;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.project.FlLeaseFinanceProjectDao;
import com.zhiwei.credit.dao.creditFlow.materials.OurProcreditMaterialsEnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.materials.SlProcreditMaterialsDao;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PlPawnProjectDao;
import com.zhiwei.credit.dao.creditFlow.repaymentSource.SlRepaymentSourceDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.dao.customer.InvestPersonDao;
import com.zhiwei.credit.dao.customer.InvestPersonInfoDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProUserAssignDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.dao.flow.RunDataDao;
import com.zhiwei.credit.dao.flow.TaskDao;
import com.zhiwei.credit.dao.flow.TaskSignDataDao;
import com.zhiwei.credit.dao.p2p.BpFinanceApplyUserDao;
import com.zhiwei.credit.dao.p2p.loan.P2pLoanProductDao;
import com.zhiwei.credit.dao.p2p.loan.P2pLoanRateDao;
import com.zhiwei.credit.dao.system.GlobalTypeDao;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.dao.system.product.BpProductParameterDao;
import com.zhiwei.credit.model.creditFlow.assuretenet.SlProcreditAssuretenet;
import com.zhiwei.credit.model.creditFlow.common.VCommonProjectFlow;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.BpMoneyBorrowDemand;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.repaymentSource.SlRepaymentSource;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.RunData;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.p2p.BpFinanceApplyUser;
import com.zhiwei.credit.model.p2p.loan.P2pLoanProduct;
import com.zhiwei.credit.model.p2p.loan.P2pLoanRate;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.product.BpProductParameter;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.smallLoan.meeting.SlConferenceRecordService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.RunDataService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.product.BpProductParameterService;

@SuppressWarnings({"unchecked","unused"})
public class CreditProjectServiceImpl implements CreditProjectService {
	private static final Log logger=LogFactory.getLog(CreditProjectServiceImpl.class);
	@Resource
	private GlobalTypeDao globalTypeDao;
	@Resource
	private GLGuaranteeloanProjectDao glGuaranteeloanProjectDao;
	@Resource
	private OurProcreditMaterialsEnterpriseDao ourProcreditMaterialsEnterpriseDao;;
	@Resource
	private SlSmallloanProjectDao slSmallloanProjectDao;
	@Resource
	private PersonDao personDao;
	@Resource
	private EnterpriseDao enterpriseDao;
	@Resource
	private EnterpriseShareequityDao enterpriseShareequityDao;
	@Resource
	private BpProductParameterDao bpProductParameterDao;
	@Resource
	private OurProcreditAssuretenetDao ourProcreditAssuretenetDao;
	@Resource
	private OurArchivesMaterialsDao ourArchivesMaterialsDao;
	@Resource
	private SlPlansToChargeDao slPlansToChargeDao;
	@Resource
	private SlActualToChargeDao slActualToChargeDao;
	@Resource
	private ProDefinitionDao proDefinitionDao;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private FlFinancingProjectDao flFinancingProjectDao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private RunDataDao runDataDao;
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private SlProcreditMaterialsDao slProcreditMaterialsDao;
	@Resource
	private SlProcreditAssuretenetDao slProcreditAssuretenetDao;
	@Resource
	private MortgageService mortgageService;
	@Resource
	private TaskDao taskDao;
	@Resource
	private SlRepaymentSourceDao slRepaymentSourceDao;
	@Resource
	private GlBankGuaranteemoneyDao glBankGuaranteemoneyDao;
	@Resource
	private GlAccountRecordDao glAccountRecordDao;
	@Resource
	private VProcreditContractService vProcreditContractService;
	@Resource
	private SlConferenceRecordService slConferenceRecordService;
	@Resource
	private ProUserAssignDao proUserAssignDao;
	@Resource(name="flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;
	@Resource
	private TaskService taskService;
	@Resource
	private SgLawsuitguaranteeProjectDao sgLawsuitguaranteeProjectDao;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private BaseCustomService baseCustomService;
	@Resource
	private VCommonProjectFlowDao vCommonProjectFlowDao;
	@Resource
	private TaskSignDataDao taskSignDataDao;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private InvestPersonDao investPersonDao;
	@Resource
	private EnterpriseBankDao enterpriseBankDao;
	@Resource
	private PlPawnProjectDao plPawnProjectDao;
	@Resource
	private InvestPersonInfoDao investPersonInfoDao;
	@Resource
	private BpFundProjectDao bpFundProjectDao;
	@Resource
	private RunDataService runDataService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private BpProductParameterService bpProductParameterService;
	@Resource
	private FlLeaseFinanceProjectDao flLeaseFinanceProjectDao;
	@Resource
	private InvestEnterpriseService investEnterpriseService;
	@Resource
	private ObSystemAccountDao obSystemAccountDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private ObAccountDealInfoDao obAccountDealInfoDao;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private CsInvestmentpersonDao csInvestmentpersonDao;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private SlCompanyMainService slCompanyMainService;
	@Resource
	private BpMoneyBorrowDemandDao bpMoneyBorrowDemandDao;
	@Resource
	private BpFinanceApplyUserDao bpFinanceApplyUserDao;
	@Resource
	private PlManageMoneyPlanBuyinfoDao plManageMoneyPlanBuyinfoDao;
	@Resource
	private P2pLoanProductDao p2pLoanProductDao;
	@Resource
	private P2pLoanRateDao p2pLoanRateDao;
	@Resource
	private AppUserService appUserService;
	
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	/*
	 * 个贷项目启动信息
	 * */
	@Override
	public String startCreditFlowProjectSSZZ(HttpServletRequest request){
	    String customerName = "";
	    String projectNumber = ""; //项目编号
	    String projectName="";//项目名称
	    String branchNO = "";
	   // Long productId;
	    AppUser user= ContextUtil.getCurrentUser();
		Long companyId = ContextUtil.getLoginCompanyId();
		String history = request.getParameter("history");

		String currentUserId = ContextUtil.getCurrentUserId().toString();
		String flowTypeKey="zftCreditFlow";//正富通流程
		if(configMap.get("flowType").toString()!=null&&!"".equals(configMap.get("flowType").toString())){
			flowTypeKey = configMap.get("flowType").toString();
		}
		if(history!=null&&!"".equals(history)&&!"undefined".equals(history)){
			flowTypeKey = "historyCreditFlowFlow";
		}
	    SlSmallloanProject project = new SlSmallloanProject(); //初始化项目信息
	    String productId=request.getParameter("slSmallloanProject.productId");
		
		FlowRunInfo flowRunInfo = new FlowRunInfo();
		String productName=null;
		if(productId!=null&&!"".equals(productId)){
			BpProductParameter bpProductParameter1=bpProductParameterDao.get(Long.valueOf(productId));
			productName=bpProductParameter1.getProductName();
		}else{
			productName="客户补录流程";
		}
		try {
			
			String strDdate = DateUtil.getNowDateTime("yyMMdd");
			String strDdate1 = DateUtil.getNowDateTime("yy-MM-dd");
			String strdate[]=strDdate1.split("-");
			String dateName=strdate[0]+"年"+strdate[1]+"月";
			ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);//流程key
			
            //生成项目编号开始 月日年_TZ(投资）_01(当天的第一笔)
			if(null!=companyId){
				Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
				branchNO = org.getBranchNO();
			}else{
				Organization organization = organizationDao.getGroupCompany();
				if(organization!=null){
					branchNO = organization.getBranchNO();
				}
			}
			project.setOppositeType("person_customer");//个贷流程
			Object obj = saveCust(project, request);
			Person person = (Person) obj;
			SlSmallloanProject slproject = slSmallloanProjectDao.getProjectNumber(branchNO+"_"+strDdate);
			if(slproject!=null){
				String number = "";
				String proNum = slproject.getProjectNumber();
				String[] proArrs = proNum.split("_");
				int num = new Integer(proArrs[2]);
				num+=1;
				if(num<10){
					number = "00"+num;
				}else if(num>=10&&num<100){
					number = "0"+num;
				}else{
					number = String.valueOf(num);
				}
				projectNumber = branchNO+"_"+strDdate+"_"+number;
				//projectName=person.getName()+"_"+strDdate+"_"+number;
				projectName=person.getName()+"_"+productName+"_"+dateName;
			}else{
				projectNumber = branchNO+"_"+strDdate+"_"+"001";
				//projectName=person.getName()+"_"+strDdate+"_"+"001";//111111111111
				projectName=person.getName()+"_"+productName+"_"+dateName;
			}
			
		    project.setOppositeID(person.getId().longValue());
		    customerName = person.getName();
		    //bpProductParameter.setId(request.getParameter(""));
		    //SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
		    //productId=project.getProductId();
			project.setProjectNumber(projectNumber);
			project.setProjectName(projectName);
			project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
			project.setBusinessType("SmallLoan");
			project.setOperationType("SmallLoanBusiness");//贷款业务流程
			project.setMineId(1L);//实实在在默认的值
			project.setMineType("person_ourmain");
			project.setOppositeType("person_customer");
			project.setOppositeID(Long.valueOf(person.getId()));
			project.setFlowType(flowTypeKey);
			project.setCreateDate(new Date());
			project.setProjectProperties("common");
			//project.setProductId( Long.parseLong(request.getParameter("productId")));
			if(ContextUtil.getLoginCompanyId()!=null){
		    	project.setCompanyId(ContextUtil.getLoginCompanyId());
		    }else{
		    	project.setCompanyId(Long.valueOf("1"));
		    }
			project.setAppUserId(user.getId());
			//Long product1=project.getProductId();			
		    Long projectId = this.slSmallloanProjectDao.save(project).getProjectId();
		    if(productId!=null&&!productId.equals("")){
		    	 project.setProductId(Long.valueOf(productId));
		    }
		   /* BpProductParameter bpProductParameter=new BpProductParameter();
		    bpProductParameter.setId(Long.valueOf(productId));*/
		     
		    if(null!=productId && !"".equals(productId)){
				BpProductParameter bpProductParameter=bpProductParameterDao.get(Long.valueOf(productId));
				//初始化费用清单
				 slActualToChargeDao.initActualChargesProduct(projectId,projectNumber,projectName,project.getBusinessType(),Short.valueOf("1"), Long.valueOf(productId));
				//初始化贷款材料 
				 ourProcreditMaterialsEnterpriseDao.initMaterialsByProductId( projectId, project.getBusinessType(),project.getOperationType(),Long.valueOf(productId));
				 //初始化准入原则
				 ourProcreditAssuretenetDao.initAssuretenetProduct(projectId.toString(),project.getBusinessType(),project.getOperationType(), "",Long.valueOf(productId));
				 //初始化归档材料
				 ourArchivesMaterialsDao.initMaterialsByProductId(projectId, project.getBusinessType(),project.getOperationType(),Long.valueOf(productId));
				if(null!=bpProductParameter){
					project.setDayOfEveryPeriod(bpProductParameter.getDayOfEveryPeriod());//自定义还款周期
					project.setAccrualtype(bpProductParameter.getAccrualtype());//还款方式
					project.setPayaccrualType(bpProductParameter.getPayaccrualType());//还款周期
					project.setPayintentPeriod(bpProductParameter.getPayintentPeriod());//贷款期限
					project.setIsPreposePayAccrual(bpProductParameter.getIsPreposePayAccrual());//前置付息
					project.setIsInterestByOneTime(bpProductParameter.getIsInterestByOneTime());//是否一次性支付利息
					project.setIsStartDatePay(bpProductParameter.getIsStartDatePay());//每期还款日
					project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());//固定在
					project.setYearAccrualRate(bpProductParameter.getYearAccrualRate());//年化利率
					project.setAccrual(bpProductParameter.getAccrual());//月化利率
					project.setDayAccrualRate(bpProductParameter.getDayAccrualRate());//日化利率
					project.setYearFinanceServiceOfRate(bpProductParameter.getYearFinanceServiceOfRate());
					project.setYearManagementConsultingOfRate(bpProductParameter.getYearManagementConsultingOfRate());
					project.setManagementConsultingOfRate(bpProductParameter.getManagementConsultingOfRate());
					project.setFinanceServiceOfRate(bpProductParameter.getFinanceServiceOfRate());
					project.setDayFinanceServiceOfRate(bpProductParameter.getDayFinanceServiceOfRate());
					project.setDayManagementConsultingOfRate(bpProductParameter.getDayManagementConsultingOfRate());
				}
				
			}
		     //将产品中的手续清单存入
			  // String productId=flowRunInfo.getRequest().getParameter("slSmallloanProject.productId");
			List<SlActualToCharge>  list1 = slActualToChargeService.getByProductId(productId);
		    //List<SlPlansToCharge>  list1 = slPlansToChargeService.getByPProductIdAndOperationType(productId,"person_customer");
			   if(null!=list1&&list1.size()!=0){
				  for(SlActualToCharge charge : list1){
					//将产品配置的手续费用清单与该项目关联
					  if(null!=charge.getProductId()&&null==charge.getBusinessType()){
						slActualToChargeService.save(charge, project);
				
					}
				}
			}
		    
			ourArchivesMaterialsDao.initMateriais(String.valueOf(project.getProjectId()), project.getBusinessType(), project.getOperationType());
		    
		    flowRunInfo = saveFlowRunInfo(project,proDefinition);
		    flowRunInfo.getBusMap().put("customerName", person.getName());
			ProcessRun run = this.jbpmService.doStartProcess(flowRunInfo);
			String useTemplate=request.getParameter("useTemplate");
			run.setSubject(String.valueOf(flowRunInfo.getBusMap().get("subjectName")));
			if("true".equals(useTemplate)){
				flowRunInfo.getVariables().putAll(BeanUtil.getMapFromRequest(request));
			}
		
			//保存后，把流程中相关的变量及数据全部提交至run_data表中，以方便后续的展示
			runDataService.saveFlowVars(run.getRunId(), flowRunInfo.getVariables());
			String str = "";
			if (run != null && run.getPiId() != null) {
				str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),user.getUserId().toString(), project.getProjectName());
			}
			return str;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("启动信贷流程出错:"+e.getMessage());
			return null;
		}
	}
	public String startLoanApprovalFlow(BpFinanceApplyUser bpFinanceApplyUser,Person person,String state){
	    String customerName = "";
	    String projectNumber = ""; //项目编号
	    String projectName="";//项目名称
	    String branchNO = "";
	   // Long productId;
	    AppUser user= ContextUtil.getCurrentUser();
		Long companyId = ContextUtil.getLoginCompanyId();

		String currentUserId = ContextUtil.getCurrentUserId().toString();
		String flowTypeKey="zftCreditFlow";//正富通流程
		if(configMap.get("flowType").toString()!=null&&!"".equals(configMap.get("flowType").toString())){
			flowTypeKey = configMap.get("flowType").toString();
		}
		
	    SlSmallloanProject project = new SlSmallloanProject(); //初始化项目信息
		
		FlowRunInfo flowRunInfo = new FlowRunInfo();
		try {
			String strDdate = DateUtil.getNowDateTime("yyMMdd");
			String strDdate1=DateUtil.getNowDateTime("yy-MM-dd");
			String strdate[]=strDdate1.split("-");
			String dateName=strdate[0]+"年"+strdate[1]+"月";
			String productName=bpFinanceApplyUser.getProductName();
			ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);//流程key
			
            //生成项目编号开始 月日年_TZ(投资）_01(当天的第一笔)
			if(null!=companyId){
				Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
				branchNO = org.getBranchNO();
			}else{
				Organization organization = organizationDao.getGroupCompany();
				if(organization!=null){
					branchNO = organization.getBranchNO();
				}
			}
			project.setOppositeType("person_customer");//个贷流程
			
			SlSmallloanProject slproject = slSmallloanProjectDao.getProjectNumber(branchNO+"_"+strDdate);
			if(slproject!=null){
				String number = "";
				String proNum = slproject.getProjectNumber();
				String[] proArrs = proNum.split("_");
				int num = new Integer(proArrs[2]);
				num+=1;
				if(num<10){
					number = "00"+num;
				}else if(num>=10&&num<100){
					number = "0"+num;
				}else{
					number = String.valueOf(num);
				}
				projectNumber = branchNO+"_"+strDdate+"_"+number;
				projectName=person.getName()+"_"+productName+"_"+dateName;
			}else{
				projectNumber = branchNO+"_"+strDdate+"_"+"001";
				//projectName=person.getName()+"_"+strDdate+"_"+"001";
				projectName=person.getName()+"_"+productName+"_"+dateName;
			}
			
		    project.setOppositeID(person.getId().longValue());
		    customerName = person.getName();
		    //bpProductParameter.setId(request.getParameter(""));
		    //SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
		    //productId=project.getProductId();
			project.setProjectNumber(projectNumber);
			project.setProjectName(projectName);
			project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
			project.setBusinessType("SmallLoan");
			project.setOperationType("PersonalCreditLoanBusiness");//贷款业务流程
			project.setMineId(1L);//实实在在默认的值
			project.setMineType("person_ourmain");
			project.setOppositeType("person_customer");
			project.setOppositeID(Long.valueOf(person.getId()));
			project.setFlowType(flowTypeKey);
			project.setCreateDate(new Date());
			project.setProjectProperties("common");
			//project.setProductId( Long.parseLong(request.getParameter("productId")));
			if(ContextUtil.getLoginCompanyId()!=null){
		    	project.setCompanyId(ContextUtil.getLoginCompanyId());
		    }else{
		    	project.setCompanyId(Long.valueOf("1"));
		    }
			Long productId=bpFinanceApplyUser.getProductId();
			 project.setProductId(bpFinanceApplyUser.getProductId());
			project.setAppUserId(user.getId());
			
		    Long projectId = this.slSmallloanProjectDao.save(project).getProjectId();
		    //借款需求
		    BpMoneyBorrowDemand bpMoneyBorrowDemand=new BpMoneyBorrowDemand();
		    if(null!=bpFinanceApplyUser.getLoanUse()){
		    	project.setPurposeType(Long.valueOf(bpFinanceApplyUser.getLoanUse()));
		    }
		    if(null!=bpFinanceApplyUser.getMonthlyInterest()){
		    	bpMoneyBorrowDemand.setRepaymentAmount(bpFinanceApplyUser.getMonthlyInterest());
		    }
		    if(null!=bpFinanceApplyUser.getLoanMoney()){
		    	bpMoneyBorrowDemand.setQuotaApplicationBig(bpFinanceApplyUser.getLoanMoney());
		    }
		    if(null!=bpFinanceApplyUser.getLoanTimeLen()){
		    	bpMoneyBorrowDemand.setLongestRepaymentPeriod(bpFinanceApplyUser.getLoanTimeLen());
		    }
		    bpMoneyBorrowDemand.setAccrual(bpFinanceApplyUser.getExpectAccrual());
		    bpMoneyBorrowDemand.setRemark(bpFinanceApplyUser.getRemark());
		    bpMoneyBorrowDemand.setProjectID(projectId);
		    bpMoneyBorrowDemandDao.save(bpMoneyBorrowDemand);
			if(project.getProductId()!=null){
				BpProductParameter bpProductParameter=bpProductParameterDao.get(project.getProductId());
				if(null!=bpProductParameter){
					project.setAccrualtype(bpProductParameter.getAccrualtype());//还款方式
					project.setPayaccrualType(bpProductParameter.getPayaccrualType());//还款周期
					project.setPayintentPeriod(bpProductParameter.getPayintentPeriod());//贷款期限
					project.setIsPreposePayAccrual(bpProductParameter.getIsPreposePayAccrual());//前置付息
					project.setIsInterestByOneTime(bpProductParameter.getIsInterestByOneTime());//是否一次性支付利息
					project.setIsStartDatePay(bpProductParameter.getIsStartDatePay());//每期还款日
					project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());//固定在
					project.setYearAccrualRate(bpProductParameter.getYearAccrualRate());//年化利率
					project.setAccrual(bpProductParameter.getAccrual());//月化利率
					project.setDayAccrualRate(bpProductParameter.getDayAccrualRate());//日化利率
					project.setYearFinanceServiceOfRate(bpProductParameter.getYearFinanceServiceOfRate());
					project.setYearManagementConsultingOfRate(bpProductParameter.getYearManagementConsultingOfRate());
					project.setManagementConsultingOfRate(bpProductParameter.getManagementConsultingOfRate());
					project.setFinanceServiceOfRate(bpProductParameter.getFinanceServiceOfRate());
					project.setDayFinanceServiceOfRate(bpProductParameter.getDayFinanceServiceOfRate());
					project.setDayManagementConsultingOfRate(bpProductParameter.getDayManagementConsultingOfRate());
					project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());
					project.setMineType(bpProductParameter.getMineType());
					project.setMineId(bpProductParameter.getMineId());
					slSmallloanProjectDao.merge(project);
					//初始化产品费用清单
					slActualToChargeDao.initActualChargesProduct(project.getProjectId(),projectNumber,projectName,"SmallLoan",Short.valueOf("1"), project.getProductId());
					//初始化贷款材料清单
					ourProcreditMaterialsEnterpriseDao.initMaterialsByProductId( project.getProjectId(),"SmallLoan", "PersonalCreditLoanBusiness",project.getProductId());
					//初始化准入原则
					ourProcreditAssuretenetDao.initAssuretenetProduct(project.getProjectId().toString(),"SmallLoan", "PersonalCreditLoanBusiness", null,project.getProductId());
					//初始化归档材料
					ourArchivesMaterialsDao.initMaterialsByProductId( project.getProjectId(),"SmallLoan", "PersonalCreditLoanBusiness",project.getProductId());
				}
			}
		    
		    flowRunInfo = saveFlowRunInfo(project,proDefinition);
		    bpFinanceApplyUser.setState(state);
		    bpFinanceApplyUser.setProjectId(projectId);
		    bpFinanceApplyUserDao.merge(bpFinanceApplyUser);
		    Map<String,Object> vars = new HashMap<String, Object>();
		    vars.put("loanId",bpFinanceApplyUser.getLoanId());
			flowRunInfo.getVariables().putAll(vars);
		    flowRunInfo.getBusMap().put("customerName", person.getName());
			ProcessRun run = this.jbpmService.doStartProcess(flowRunInfo);
			run.setSubject(String.valueOf(flowRunInfo.getBusMap().get("subjectName")));
		
		
			//保存后，把流程中相关的变量及数据全部提交至run_data表中，以方便后续的展示
			runDataService.saveFlowVars(run.getRunId(), flowRunInfo.getVariables());
			String str = "";
			if (run != null && run.getPiId() != null) {
				str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),user.getUserId().toString(), project.getProjectName());
			}
			return str;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("启动信贷流程出错:"+e.getMessage());
			return null;
		}
	}
	/*
	 * 启动企业贷流程
	 * **/ 
	@Override
	public String startCreditFlowExterPriseProject(HttpServletRequest request){

	    String customerName = "";
	    String projectNumber = ""; //项目编号
	    String projectName="";//项目名称
	    String branchNO = "";
	    AppUser user= ContextUtil.getCurrentUser();
	    String mineType = request.getParameter("mineType");
	    String mineId = request.getParameter("mineId");
		Long companyId = ContextUtil.getLoginCompanyId();
		String history = request.getParameter("history");
		String gudong = request.getParameter("gudongInfo");
		String currentUserId = ContextUtil.getCurrentUserId().toString();
	  //  String flowTypeKey="personalCreditLoanFlow";//实实在在流程
		String flowTypeKey="wadEnterprisesFlow";//稳安贷企业流程
		
		String ftk =AppUtil.getSysConfig().get("flowEnterType")==null?"":AppUtil.getSysConfig().get("flowEnterType").toString();// AppUtil.getEnterFlowType();
		//String ftk =AppUtil.getSysConfig().get("flowEnterType").toString();
		if(null!=ftk&&!"".equals(ftk)){
			flowTypeKey = ftk;
		}
	    SlSmallloanProject project = new SlSmallloanProject(); //初始化项目信息
		Enterprise enter = new Enterprise();
		FlowRunInfo flowRunInfo = new FlowRunInfo();
		try {
			String strDdate = DateUtil.getNowDateTime("yyMMdd");
			String strDdate1 = DateUtil.getNowDateTime("yy-MM-dd");
			String strdate[]=strDdate1.split("-");
			String dateName=strdate[0]+"年"+strdate[1]+"月";
			ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);//流程key
			String flowId = String.valueOf(proDefinition.getDefId());
			Long branchCompanyId = proDefinition.getBranchCompanyId();
			BeanUtil.populateEntity(request, enter, "enterprise");
            //生成项目编号开始 月日年_TZ(投资）_01(当天的第一笔)
			if(null!=companyId){
				Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
				branchNO = org.getBranchNO();
			}else{
				Organization organization = organizationDao.getGroupCompany();
				if(organization!=null){
					branchNO = organization.getBranchNO();
				}
			}
			String productId=request.getParameter("slSmallloanProject.productId");	
			String productName=null;
			if(productId!=null&&!productId.equals("")){
				BpProductParameter bpProductParameter1=bpProductParameterDao.get(Long.valueOf(productId));
				 productName=bpProductParameter1.getProductName();
		    }else{
		    	productName="补录流程";
		    }
			SlSmallloanProject slproject = slSmallloanProjectDao.getProjectNumber(branchNO+"_"+strDdate);
			if(slproject!=null){
				String number = "";
				String proNum = slproject.getProjectNumber();
				String[] proArrs = proNum.split("_");
				int num = new Integer(proArrs[2]);
				num+=1;
				if(num<10){
					number = "00"+num;
				}else if(num>=10&&num<100){
					number = "0"+num;
				}else{
					number = String.valueOf(num);
				}
				projectNumber = branchNO+"_"+strDdate+"_"+number;
				projectName=enter.getEnterprisename()+"_"+productName+"_"+dateName;
			}else{
				projectNumber = branchNO+"_"+strDdate+"_"+"001";
				projectName=enter.getEnterprisename()+"_"+productName+"_"+dateName;
				//projectName=enter.getEnterprisename()+"_"+strDdate+"_"+"001";
			}
			//生成项目编号结束
			
		  	if(enter.getId()==null||enter.getId()==0){   
	     		 enter.setId(null);
	     		 enter.setCreater(ContextUtil.getCurrentUser().getFullname());
	     		 enter.setBelongedId(currentUserId);
	    	     enter.setCreaterId(Long.valueOf(currentUserId));
	    	     enter.setCreatedate(new Date());
	    	     enter.setCompanyId(ContextUtil.getLoginCompanyId());
	    	     enterpriseService.save(enter);
	    	     baseCustomService.getCustomToweb("0", enter.getId(),0); 
	    	}else{    
	     		Enterprise persistentEnter = enterpriseService.getById(enter.getId());
	     		BeanUtil.copyNotNullProperties(persistentEnter, enter);
	    		enterpriseService.merge(persistentEnter);
	     		baseCustomService.getCustomToweb("0", enter.getId(),0); 
	     	}
			String shareequity=request.getParameter("gudongInfo");
	   		List<EnterpriseShareequity> listES=new ArrayList<EnterpriseShareequity>(); //股东信息
        	if(null != shareequity && !"".equals(shareequity)) {
    				String[] shareequityArr = shareequity.split("@");
    				 for(int i=0; i<shareequityArr.length; i++) {
    						String str = shareequityArr[i];
    						JSONParser parser = new JSONParser(new StringReader(str));
    						try{
    							EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity)JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
    							listES.add(enterpriseShareequity);
    						} catch(Exception e) {
    							e.printStackTrace();
    						}
    					}
    		}
        	if(listES.size()>0)
	   		 {
	   			 for(int i=0;i<listES.size();i++)
	   			 {
	   				 EnterpriseShareequity es=listES.get(i);
	   				 if(es.getId()==null)
	   				 {
	   					es.setEnterpriseid(enter.getId());
	   					this.enterpriseShareequityDao.save(es);
	   				 }
	   				 else 
	   				 {
	   					 EnterpriseShareequity esPersistent=this.enterpriseShareequityDao.load(es.getId());
	   					 BeanUtils.copyProperties(es, esPersistent,new String[] {"id","enterpriseid"});
	   					 this.enterpriseShareequityDao.merge(esPersistent);
	   				 }
	   			 }
	   		 }
		    project.setOppositeID(Long.valueOf(enter.getId()));
		    customerName = enter.getEnterprisename();
			project.setProjectNumber(projectNumber);
			project.setProjectName(projectName);
			project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
			project.setBusinessType("SmallLoan");
			//project.setOperationType("PersonalCreditLoanBusiness"); //实实在在默认的值
			project.setOperationType("SmallLoanBusiness");//贷款业务流程
			
			
			//我方主体没有，默认为第一个
			if(null!=mineId&&!"".equals(mineId)){
				project.setMineId(Long.parseLong(mineId));
			}else{
				project.setMineId(Long.parseLong("0"));//实实在在默认的值
			}
			if(null!=mineType&&!"".equals(mineType)){
				project.setMineType(mineType);
			}else{
				project.setMineType("company_ourmain");
			}
			//project.setMineId(Long.valueOf(0));//实实在在默认的值
			project.setOppositeType("company_customer");
			project.setOppositeID(Long.valueOf(enter.getId()));
			project.setFlowType(flowTypeKey);
			project.setCreateDate(new Date());
			project.setProjectProperties("common");
			if(ContextUtil.getLoginCompanyId()!=null){
		    	project.setCompanyId(ContextUtil.getLoginCompanyId());
		    }else{
		    	project.setCompanyId(Long.valueOf("1"));
		    }
			project.setAppUserId(user.getId());
		    //Long projectId = this.slSmallloanProjectDao.save(project).getProjectId();
			//把产品中配置的内容初始化到项目里边
			Long projectId = this.slSmallloanProjectDao.save(project).getProjectId();
			//String productId=request.getParameter("slSmallloanProject.productId");
			//把产品id加入到项目表里边
			project.setProductId(Long.valueOf(productId));
			if(null!=productId && !"".equals(productId)){
				BpProductParameter bpProductParameter=bpProductParameterDao.get(Long.valueOf(productId));
				//初始化费用清单
				 slActualToChargeDao.initActualChargesProduct(projectId,projectNumber,projectName,project.getBusinessType(),Short.valueOf("1"), Long.valueOf(productId));
				//初始化贷款材料 
				 ourProcreditMaterialsEnterpriseDao.initMaterialsByProductId( projectId, project.getBusinessType(),project.getOperationType(),Long.valueOf(productId));
				 //初始化准入原则
				 ourProcreditAssuretenetDao.initAssuretenetProduct(projectId.toString(),project.getBusinessType(),project.getOperationType(), "",Long.valueOf(productId));
				 //初始化归档材料
				 ourArchivesMaterialsDao.initMaterialsByProductId(projectId, project.getBusinessType(),project.getOperationType(),Long.valueOf(productId));
				if(null!=bpProductParameter){
					project.setDayOfEveryPeriod(bpProductParameter.getDayOfEveryPeriod());//自定义还款周期
					project.setAccrualtype(bpProductParameter.getAccrualtype());//还款方式
					project.setPayaccrualType(bpProductParameter.getPayaccrualType());//还款周期
					project.setPayintentPeriod(bpProductParameter.getPayintentPeriod());//贷款期限
					project.setIsPreposePayAccrual(bpProductParameter.getIsPreposePayAccrual());//前置付息
					project.setIsInterestByOneTime(bpProductParameter.getIsInterestByOneTime());//是否一次性支付利息
					project.setIsStartDatePay(bpProductParameter.getIsStartDatePay());//每期还款日
					project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());//固定在
					project.setYearAccrualRate(bpProductParameter.getYearAccrualRate());//年化利率
					project.setAccrual(bpProductParameter.getAccrual());//月化利率
					project.setDayAccrualRate(bpProductParameter.getDayAccrualRate());//日化利率
					project.setYearFinanceServiceOfRate(bpProductParameter.getYearFinanceServiceOfRate());
					project.setYearManagementConsultingOfRate(bpProductParameter.getYearManagementConsultingOfRate());
					project.setManagementConsultingOfRate(bpProductParameter.getManagementConsultingOfRate());
					project.setFinanceServiceOfRate(bpProductParameter.getFinanceServiceOfRate());
					project.setDayFinanceServiceOfRate(bpProductParameter.getDayFinanceServiceOfRate());
					project.setDayManagementConsultingOfRate(bpProductParameter.getDayManagementConsultingOfRate());
				}
				
			}
		    
		    Map<String,Object> vars = new HashMap<String, Object>();
		    Map<String, Object> busMap = new HashMap<String, Object>();
		    
			vars.put("projectId",projectId);
			vars.put("oppositeID",project.getOppositeID());
			vars.put("oppositeType",project.getOppositeType());
/*			vars.put("oppositeID",31l);
			vars.put("oppositeType","company_customer");*/
			vars.put("businessType",project.getBusinessType());
			vars.put("operationType",project.getOperationType());
			vars.put("branchCompanyId", branchCompanyId);
			vars.put("personId",enter.getId());
			flowRunInfo.getVariables().putAll(vars);
			
			busMap.put("projectNumber",project.getProjectNumber());
			busMap.put("projectName", project.getProjectName());
			busMap.put("subjectName", project.getProjectName()+"-"+project.getProjectNumber());
			busMap.put("businessType",project.getBusinessType());
			busMap.put("customerName",customerName);
			busMap.put("operationType",project.getOperationType());
			busMap.put("flowType",flowTypeKey);
			busMap.put("oppositeID",project.getOppositeID());
			busMap.put("oppositeType", project.getOppositeType());
			busMap.put("projectId",projectId);
			flowRunInfo.setBusMap(busMap);
			flowRunInfo.setDefId(flowId);
			ProcessRun run = this.jbpmService.doStartProcess(flowRunInfo);
			String useTemplate=request.getParameter("useTemplate");
			run.setSubject(String.valueOf(flowRunInfo.getBusMap().get("subjectName")));
			if("true".equals(useTemplate)){
				flowRunInfo.getVariables().putAll(BeanUtil.getMapFromRequest(request));
			}
			//贷款材料清单
			//ourProcreditMaterialsEnterpriseDao.initMaterials(String.valueOf(projectId), "SmallLoan", "SmallLoanBusiness");
			ourProcreditAssuretenetDao.initAssuretenet(String.valueOf(projectId),  "SmallLoan", "SmallLoanBusiness",project.getOppositeType()); //初始化贷款准入原则
			ourArchivesMaterialsDao.initMateriais(String.valueOf(project.getProjectId()), project.getBusinessType(), project.getOperationType());

			//保存后，把流程中相关的变量及数据全部提交至run_data表中，以方便后续的展示
			runDataService.saveFlowVars(run.getRunId(), flowRunInfo.getVariables());
			String str = "";
			if (run != null && run.getPiId() != null) {
				str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),user.getUserId().toString(), project.getProjectName());
				
			}
			return str;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("启动信贷流程出错:"+e.getMessage());
			return null;
		}
	}
	/*
	 * 生成项目名称
	 * */
	private void saveProject(SlSmallloanProject project,Person person,Enterprise enter){
		if(null!=project.getOppositeType()&&"person_customer".equals(project.getOppositeType())){
			
		}else if(null!=project.getOppositeType()&&"company_customer".equals(project.getOppositeType())){
			
		} 
	}
	
	/*
	 * 保存项目信息到流程中
	 * */
	private  FlowRunInfo saveFlowRunInfo(SlSmallloanProject project,ProDefinition proDefinition){
		FlowRunInfo flowRunInfo = new FlowRunInfo();
		 Map<String,Object> vars = new HashMap<String, Object>();
		    Map<String, Object> busMap = new HashMap<String, Object>();
		    
			vars.put("projectId",project.getProjectId());
			vars.put("oppositeID",project.getOppositeID());
			vars.put("oppositeType",project.getOppositeType());
			vars.put("businessType",project.getBusinessType());
			vars.put("operationType",project.getOperationType());
			vars.put("branchCompanyId", proDefinition.getBranchCompanyId());
			vars.put("personId",project.getOppositeID());
			flowRunInfo.getVariables().putAll(vars);
			
			busMap.put("projectNumber",project.getProjectNumber());
			busMap.put("projectName", project.getProjectName());
			busMap.put("subjectName", project.getProjectName()+"-"+project.getProjectNumber());
			busMap.put("businessType",project.getBusinessType());
			//busMap.put("customerName",project.set);
			busMap.put("operationType",project.getOperationType());
			busMap.put("flowType",proDefinition.getDEFKEY());
			busMap.put("oppositeID",project.getOppositeID());
			busMap.put("oppositeType", project.getOppositeType());
			busMap.put("projectId",project);
			flowRunInfo.setBusMap(busMap);
			flowRunInfo.setDefId(proDefinition.getDefId().toString());
		
		return flowRunInfo;
	}
	/*
	 * 保存客户信息
	 * */
	public Object saveCust(SlSmallloanProject project,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		String currentUserId = ContextUtil.getCurrentUserId().toString();
		if(null!=project.getOppositeType()&&"person_customer".equals(project.getOppositeType())){
			Person person = new Person();   //初始化person
			Long personId = null;
			BeanUtil.populateEntity(request, person, "person");
		  	if(person.getId()==null||person.getId()==0){   
	     		 person.setId(null);
	     		 person.setCreater(ContextUtil.getCurrentUser().getFullname());
	     		 person.setBelongedId(currentUserId);
	    	     person.setCreaterId(Long.valueOf(currentUserId));
	    	     person.setCreatedate(new Date());
	    	     person.setCompanyId(ContextUtil.getLoginCompanyId());
	    	     personDao.save(person);
	    	     personId =person.getId().longValue();
	    	     baseCustomService.getCustomToweb("1", person.getId(),0);
	    	 	project.setOppositeID(personId);
	    	     return person;
	    	}else{    
	     		Person persistentPerson = this.personDao.getById(person.getId());	
	     		BeanUtil.copyNotNullProperties(persistentPerson, person);
	     		personDao.merge(persistentPerson);
	     		personId = persistentPerson.getId().longValue();
	     		baseCustomService.getCustomToweb("1", persistentPerson.getId(),0); 
	     		project.setOppositeID(personId);
	     		return persistentPerson;
	     	}
		  
		}else if(null!=project.getOppositeType()&&"company_customer".equals(project.getOppositeType())){
			
		}
		return 1;
	}
	
	/**
	 * 启动小额贷款微贷常规流程
	 * @param flowRunInfo
	 * @return
	 */
	public Integer startCreditFlowSlSmallloanProjectOfMicro(FlowRunInfo flowRunInfo){

	    String customerName = "";//客户名称
	    String projectNumber = ""; //项目编号
	    String branchNO = "";//公司编号
	    
	    Person person = new Person();   //初始化person
	    EnterpriseBank ebank = new EnterpriseBank();
	    SlSmallloanProject project = new SlSmallloanProject(); //初始化项目信息
		
		try {
			
			String degree = flowRunInfo.getRequest().getParameter("degree");
			String smallLoanType = flowRunInfo.getRequest().getParameter("smallLoanType");
			String strDdate = DateUtil.getNowDateTime("yyMMdd");
			Long companyId = ContextUtil.getLoginCompanyId();
			
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "slSmallloanProject");
			BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
			BeanUtil.populateEntity(flowRunInfo.getRequest(), ebank, "enterpriseBank");
			
			
            //生成项目编号开始
			if(companyId!=null){
				Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
				if(org!=null){
					branchNO = org.getBranchNO();
				}
			}else{
				Organization organization = organizationDao.getGroupCompany();
				if(organization!=null){
					branchNO = organization.getBranchNO();
				}
			}
			
			SlSmallloanProject slproject = slSmallloanProjectDao.getProjectNumber(branchNO+"_"+strDdate);
			if(slproject!=null){
				String number = "";
				String proNum = slproject.getProjectNumber();
				String[] proArrs = proNum.split("_");
				int num = new Integer(proArrs[2]);
				num+=1;
				if(num<10){
					number = "00"+num;
				}else if(num>=10&&num<100){
					number = "0"+num;
				}else{
					number = String.valueOf(num);
				}
				projectNumber = branchNO+"_"+strDdate+"_"+number;
			}else{
				projectNumber = branchNO+"_"+strDdate+"_"+"001";
			}
			// 生成项目编号结束
			
	    	String currentUserId = ContextUtil.getCurrentUserId().toString();
	     	if(person.getId()==null || person.getId()==0){   
	     		person.setId(null);
	     		person.setCreater(ContextUtil.getCurrentUser().getFullname());
	     		person.setBelongedId(currentUserId);
	    	    person.setCreaterId(Long.valueOf(currentUserId));
	    	    person.setCreatedate(new Date());
	    	    person.setCompanyId(ContextUtil.getLoginCompanyId());
	    	    personDao.save(person);
	    	    baseCustomService.getCustomToweb("1", person.getId(),0); 
	    	}else{
	     		Person persistentPerson=this.personDao.getById(person.getId());  	     		
	     		persistentPerson.setName(person.getName());
	     		persistentPerson.setSex(person.getSex());
	     		persistentPerson.setCardtype(person.getCardtype());
	     		persistentPerson.setCardnumber(person.getCardnumber());
	     		persistentPerson.setCellphone(person.getCellphone());
	     		personDao.merge(persistentPerson);
	     		baseCustomService.getCustomToweb("1", persistentPerson.getId(),0); 
	     	}
	     		
	     	customerName = person.getName();	
	     	String flowTypeKey = project.getFlowType();
	     	String businessTypeKey = flowRunInfo.getBusMap().get("businessTypeKey").toString();
	     	String operationTypeKey = flowRunInfo.getBusMap().get("operationTypeKey").toString();
	     	ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);
			String flowId = String.valueOf(proDefinition.getDefId());
			Long branchCompanyId = proDefinition.getBranchCompanyId();
			
			project.setOppositeID(Long.valueOf(person.getId()));
			project.setLoanLimit(smallLoanType);
			project.setProjectNumber(projectNumber);
			project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
			project.setBusinessType(businessTypeKey);
			project.setOperationType(operationTypeKey);
			project.setFlowType(flowTypeKey);
			project.setOppositeType("person_customer");
			project.setCreateDate(new Date());
			project.setProjectProperties("common");
			if(ContextUtil.getLoginCompanyId()!=null){
		    	project.setCompanyId(ContextUtil.getLoginCompanyId());
		    }else{
		    	project.setCompanyId(Long.valueOf("1"));
		    }
			if(degree!=null&&!"".equals(degree)){
				project.setAppUserId(degree);
	        }
			
		    Long projectId = this.slSmallloanProjectDao.save(project).getProjectId();
		   // ourProcreditMaterialsDao.initMateriais(String.valueOf(projectFolowId), businessTypeKey, operationTypeKey); //初始化贷款材料
		   // 初始化小贷的贷款材料清单(公有的)
/*		    List<OurProcreditMaterials> list = ourProcreditMaterialsDao.getMaterialsOfPublic(1,operationTypeKey);
		    for(OurProcreditMaterials o : list){
		    	SlProcreditMaterials slProcreditMaterials = new SlProcreditMaterials();
				slProcreditMaterials.setProjId(String.valueOf(projectId));
				slProcreditMaterials.setMaterialsId(o.getMaterialsId());
				slProcreditMaterials.setMaterialsName(o.getMaterialsName());
				slProcreditMaterials.setIsShow(true);
				slProcreditMaterials.setDatumNums(0);
				slProcreditMaterials.setParentId(null);
				slProcreditMaterials.setBusinessTypeKey(businessTypeKey);
				slProcreditMaterials.setOperationTypeKey(operationTypeKey);
				slProcreditMaterialsDao.save(slProcreditMaterials);
		    }
		    ourProcreditAssuretenetDao.initAssuretenet(String.valueOf(projectId), businessTypeKey, operationTypeKey,project.getOppositeType()); //初始化贷款准入原则
			*/
		    
		  //初始化担保材料
			ourProcreditMaterialsEnterpriseDao.initMaterials(String.valueOf(projectId), businessTypeKey, operationTypeKey);
			
		    //初始化费用收支
		    Short a = 0;
		    if(this.slPlansToChargeDao.getbytype(0)!=null && this.slPlansToChargeDao.getbytype(0).size()>0){   
		    	slActualToChargeDao.initActualCharges(project.getProjectId(),project.getProjectNumber(),project.getProjectName(),this.slPlansToChargeDao.getbyOperationType(0,businessTypeKey),businessTypeKey,a);
		    }

		    //初始化小贷的归档材料
		    ourArchivesMaterialsDao.initMateriais(String.valueOf(project.getProjectId()), businessTypeKey, operationTypeKey);
		  
		    Map<String,Object> vars = new HashMap<String, Object>();
		    Map<String, Object> busMap = new HashMap<String, Object>();
		    
			vars.put("projectId",projectId);
			vars.put("oppositeType",project.getOppositeType());
			vars.put("businessType",project.getBusinessType());
			vars.put("operationType",project.getOperationType());
			vars.put("branchCompanyId",branchCompanyId);
			flowRunInfo.getVariables().putAll(vars);
			
			busMap.put("projectNumber",project.getProjectNumber());
			busMap.put("projectName", project.getProjectName());
			busMap.put("subjectName", project.getProjectName()+"-"+project.getProjectNumber());
			busMap.put("businessType",project.getBusinessType());
			busMap.put("customerName",customerName);
			busMap.put("operationType",project.getOperationType());
			busMap.put("flowType",flowTypeKey);
			busMap.put("projectId",projectId);
			flowRunInfo.setBusMap(busMap);
			flowRunInfo.setDefId(flowId);
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("启动小额微贷项目出错:"+e.getMessage());
			return 0;
		}
		return 1;

	}
	
	/**
	 * 启动小额贷款常规流程
	 * @param flowRunInfo
	 * @return
	 */
	public Integer startCreditFlowSlSmallloanProject(FlowRunInfo flowRunInfo){
		
		    String customerName = "";
		    String projectNumber = ""; //项目编号
		    String branchNO = "";
		    String degree = flowRunInfo.getRequest().getParameter("degree");
			String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");//股东信息
			String investId = flowRunInfo.getRequest().getParameter("investId");//推介公司信息
			Long companyId = ContextUtil.getLoginCompanyId();
			String currentUserId = ContextUtil.getCurrentUserId().toString();
			
			Boolean isSubCompany = Boolean.valueOf(flowRunInfo.getRequest().getParameter("isSubCompany"));//如果true则表示，分公司形式分配任务.否则采取session获取companyId  add by gaoqingru
			Long mineId = Long.valueOf(flowRunInfo.getRequest().getParameter("mineId")) ;//gaoqingrui 通过传递集团公司参数 获取公司id
		    
		    SlSmallloanProject project = new SlSmallloanProject(); //初始化项目信息
			Person person = new Person();   //初始化person
			Enterprise  enterprise = new Enterprise();  //初始化enterprise
			EnterpriseBank ebank = new EnterpriseBank();
			
			try {
				String businessTypeKey = flowRunInfo.getBusMap().get("businessTypeKey").toString();
				String operationTypeKey = flowRunInfo.getBusMap().get("operationTypeKey").toString();
				String strDdate = DateUtil.getNowDateTime("yyMMdd");
				
				BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "slSmallloanProject");
//区分流程类别
				//String flowTypeKey = "SmallloanBusinessType";//胜鼎业务
				//String flowTypeKey = "zftCreditFlow";//正富通业务
				//String flowTypeKey = "drySmallloanFlow";//多融易业务
				String flowTypeKey = "jhcCreditFlow";//"BondTransferFlow";//债权转让
				
				
				/*String flowType = configMap.get("flowType").toString();
				if(null!=flowType&&!"".equals(flowType)&&flowType.length()!=0){
				    	flowTypeKey = flowType;
				}*/
				if(project.getOppositeType().equals("company_customer")){ //企业
					BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
					BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
				 }else if(project.getOppositeType().equals("person_customer")){     
					BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
					BeanUtil.populateEntity(flowRunInfo.getRequest(), ebank, "enterpriseBank");
					flowTypeKey = "yndjSmallloanFlow";//点聚业务
				 }
				
				ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);
				String flowId = String.valueOf(proDefinition.getDefId());
				Long branchCompanyId = proDefinition.getBranchCompanyId();
				
				
                //生成项目编号开始 月日年_TZ(投资）_01(当天的第一笔)
				if(null!=companyId){
					Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
					branchNO = org.getBranchNO();
				}else{
					Organization organization = organizationDao.getGroupCompany();
					if(organization!=null){
						branchNO = organization.getBranchNO();
					}
				}
				SlSmallloanProject slproject = slSmallloanProjectDao.getProjectNumber(branchNO+"_"+strDdate);
				if(slproject!=null){
					String number = "";
					String proNum = slproject.getProjectNumber();
					String[] proArrs = proNum.split("_");
					int num = new Integer(proArrs[2]);
					num+=1;
					if(num<10){
						number = "00"+num;
					}else if(num>=10&&num<100){
						number = "0"+num;
					}else{
						number = String.valueOf(num);
					}
					projectNumber = branchNO+"_"+strDdate+"_"+number;
				}else{
					projectNumber = branchNO+"_"+strDdate+"_"+"001";
				}
				//生成项目编号结束
				
		     	if(project.getOppositeType().equals("company_customer")){ //企业
		     		//股东信息开始
					List<EnterpriseShareequity> listES=new ArrayList<EnterpriseShareequity>();
			    	if(gudongInfo!=null&&!"".equals(gudongInfo)) {
			    		String[] shareequityArr = gudongInfo.split("@");
			    		for(int i=0; i<shareequityArr.length; i++) {
			    			String str = shareequityArr[i];
			    			JSONParser parser = new JSONParser(new StringReader(str));
							EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity)JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
							listES.add(enterpriseShareequity);
			    		}
					}
			    	//股东信息结束
			    	
				   if(enterprise.getId()==null || enterprise.getId()==0){
					   
					   enterprise.setCompanyId(ContextUtil.getLoginCompanyId());
					   enterprise.setId(null);
					   if(person.getId()==null ||person.getId()==0){//如果企业客户是新增，法人信息也是新增，添加保存法人信息代码 （add by ly   2013-6-18）
						   person.setId(null);
						   person.setCompanyId(ContextUtil.getLoginCompanyId());
						   person.setCreater(ContextUtil.getCurrentUser().getFullname());
						   person.setBelongedId(currentUserId);
				    	   person.setCreaterId(Long.valueOf(currentUserId));
				    	   person.setCreatedate(new Date());
						   personDao.save(person);
						   baseCustomService.getCustomToweb("1", person.getId(),0); 
					   }
					   enterprise.setLegalpersonid(person.getId());
					   enterprise.setCreater(ContextUtil.getCurrentUser().getFullname());
					   enterprise.setBelongedId(currentUserId);
					   enterprise.setCreaterId(Long.valueOf(currentUserId));
					   enterprise.setCreatedate(new Date());
					   enterpriseDao.save(enterprise);
					   baseCustomService.getCustomToweb("0", enterprise.getId(),0);
			       } else{
					   Enterprise persistentEnterprise=this.enterpriseDao.getById(enterprise.getId());
					   persistentEnterprise.setEnterprisename(enterprise.getEnterprisename());
					   persistentEnterprise.setArea(enterprise.getArea());						  
					   persistentEnterprise.setOrganizecode(enterprise.getOrganizecode());					
					   persistentEnterprise.setTelephone(enterprise.getTelephone());
					   persistentEnterprise.setEmail(enterprise.getEmail());
					   enterpriseDao.merge(persistentEnterprise);
					   baseCustomService.getCustomToweb("0", persistentEnterprise.getId(),0);
					   
					   Person p = personDao.getById(person.getId());
					   if(p!=null){
						   p.setName(person.getName());
				     	   p.setSex(person.getSex());
				     	   p.setCardtype(person.getCardtype());
				     	   p.setCardnumber(person.getCardnumber());
				     	   p.setCellphone(person.getCellphone());
				     	   p.setSelfemail(person.getSelfemail());
				     	   personDao.merge(p);
				     	   baseCustomService.getCustomToweb("1", p.getId(),0); 
					   }
				   }
				   
				   Integer duifangzhutiId = enterprise.getId();
				   if(listES!=null && listES.size()>0){
						for(int i=0;i<listES.size();i++){
						   EnterpriseShareequity enterpriseShareequity=listES.get(i);
						   if(enterpriseShareequity.getId()==null||"".equals(enterpriseShareequity.getId())) {
							   enterpriseShareequity.setEnterpriseid(duifangzhutiId);
							   enterpriseShareequityDao.save(enterpriseShareequity);
						   }else {
							   EnterpriseShareequity ps = this.enterpriseShareequityDao.load(enterpriseShareequity.getId());
							   BeanUtil.copyNotNullProperties(ps, enterpriseShareequity);
							   enterpriseShareequityDao.save(ps);
						   }
						}
					}
				   project.setOppositeID(Long.valueOf(duifangzhutiId));
				   customerName = enterprise.getEnterprisename();
				}else if(project.getOppositeType().equals("person_customer")){
				  	if(person.getId()==null||person.getId()==0){   
			     		 person.setId(null);
			     		 person.setCreater(ContextUtil.getCurrentUser().getFullname());
			     		 person.setBelongedId(currentUserId);
			    	     person.setCreaterId(Long.valueOf(currentUserId));
			    	     person.setCreatedate(new Date());
			    	     person.setCompanyId(ContextUtil.getLoginCompanyId());
			    	     personDao.save(person);
			    	     baseCustomService.getCustomToweb("1", person.getId(),0); 
			    	}else{    
			     		Person persistentPerson = this.personDao.getById(person.getId());		     	   
			     	    persistentPerson.setName(person.getName());
			     	    persistentPerson.setSex(person.getSex());
			     	    persistentPerson.setCardtype(person.getCardtype());
			     	    persistentPerson.setCardnumber(person.getCardnumber());
			     	    persistentPerson.setCellphone(person.getCellphone());
			     	    persistentPerson.setMarry(person.getMarry());
			     	    persistentPerson.setSelfemail(person.getSelfemail());
			     	    persistentPerson.setPostcode(person.getPostcode());
			     	    persistentPerson.setPostaddress(person.getPostaddress());
			     		personDao.merge(persistentPerson);
			     		baseCustomService.getCustomToweb("1", persistentPerson.getId(),0); 
			     	}
				   project.setOppositeID(Long.valueOf(person.getId()));
				   customerName = person.getName();
				}
		     	String smallLoanType = flowRunInfo.getRequest().getParameter("smallLoanType");
				project.setLoanLimit(smallLoanType);
				project.setProjectNumber(projectNumber);
				project.setPayintentPeriod(project.getPoupsePeriod());
				project.setProjectMoney(project.getMoney());
				project.setStartDate(project.getPoupseDate());
				project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
				project.setBusinessType(businessTypeKey);
				project.setOperationType(operationTypeKey);
				project.setFlowType(flowTypeKey);
				project.setCreateDate(new Date());
				project.setProjectProperties("common");
				//改动  by gao  集团版  主体单位 关联 companyId
				if(isSubCompany){//集团版本，mineId指定分公司id
					project.setMineId(mineId);//add by gaoqingrui 
					project.setCompanyId(mineId);
				}else{
					if(ContextUtil.getLoginCompanyId()!=null){
				    	project.setCompanyId(ContextUtil.getLoginCompanyId());
				    }else{
				    	project.setCompanyId(Long.valueOf("1"));
				    }
				}
				/*if(ContextUtil.getLoginCompanyId()!=null){
			    	project.setCompanyId(ContextUtil.getLoginCompanyId());
			    }else{
			    	project.setCompanyId(Long.valueOf("1"));
			    }*/
				if(degree!=null&&!"".equals(degree)){
				  	project.setAppUserId(degree);
		        }
				///修改推介机构的可用额度
				if(null!=project.getAvailableId()&&!"".equals(project.getAvailableId())){
					InvestEnterprise enter = investEnterpriseService.get(project.getAvailableId());
					if(null!=enter.getNowCreditLimit()){
						Double enterCredit = enter.getNowCreditLimit().doubleValue();
						Double pmoney = project.getMoney().doubleValue();
						enter.setNowCreditLimit(BigDecimal.valueOf(enterCredit-pmoney));
						investEnterpriseService.merge(enter);
					}	
				}
			    Long projectId = this.slSmallloanProjectDao.save(project).getProjectId();
			   // ourProcreditMaterialsDao.initMateriais(String.valueOf(projectFolowId), businessTypeKey, operationTypeKey); //初始化贷款材料
			   // 初始化小贷的贷款材料清单(公有的)
	/*		    List<OurProcreditMaterials>  list=ourProcreditMaterialsDao.getMaterialsOfPublic(1,operationTypeKey);
			    for(OurProcreditMaterials o : list){
			    	SlProcreditMaterials slProcreditMaterials = new SlProcreditMaterials();
					slProcreditMaterials.setProjId(String.valueOf(projectId));
					slProcreditMaterials.setMaterialsId(o.getMaterialsId());
					slProcreditMaterials.setMaterialsName(o.getMaterialsName());
					slProcreditMaterials.setIsShow(true);
					slProcreditMaterials.setDatumNums(0);
					slProcreditMaterials.setParentId(null);
					slProcreditMaterials.setBusinessTypeKey(businessTypeKey);
					slProcreditMaterials.setOperationTypeKey(operationTypeKey);
					slProcreditMaterialsDao.save(slProcreditMaterials);
			    }
			    ourProcreditAssuretenetDao.initAssuretenet(String.valueOf(projectId), businessTypeKey, operationTypeKey,project.getOppositeType()); //初始化贷款准入原则
				*/
			  //初始贷款材料
				ourProcreditMaterialsEnterpriseDao.initMaterials(String.valueOf(projectId), businessTypeKey, operationTypeKey);
				ourProcreditAssuretenetDao.initAssuretenet(String.valueOf(projectId), businessTypeKey, operationTypeKey,project.getOppositeType()); //初始化贷款准入原则
			    
			    //初始化费用收支
			    Short a = 0;
			    if(this.slPlansToChargeDao.getbytype(0)!=null && this.slPlansToChargeDao.getbytype(0).size()>0){   
			    	slActualToChargeDao.initActualCharges(project.getProjectId(),project.getProjectNumber(),project.getProjectName(),this.slPlansToChargeDao.getbyOperationType(0,businessTypeKey),businessTypeKey,a);
			    }

			    //初始化小贷的归档材料
			    ourArchivesMaterialsDao.initMateriais(String.valueOf(project.getProjectId()), businessTypeKey, operationTypeKey);
			    
			    Map<String,Object> vars = new HashMap<String, Object>();
			    Map<String, Object> busMap = new HashMap<String, Object>();
			   
				vars.put("projectId",projectId);
				//vars.put("bidPlanId", null);
				vars.put("oppositeType",project.getOppositeType());
				vars.put("businessType",project.getBusinessType());
				vars.put("operationType",project.getOperationType());
				vars.put("branchCompanyId", branchCompanyId);
				vars.put("oppositeID",project.getOppositeID());
				if(person!=null){
					vars.put("personId",person.getId());
				}
				
				flowRunInfo.getVariables().putAll(vars);//会存入数据库
				
				busMap.put("projectNumber",project.getProjectNumber());
				busMap.put("projectName", project.getProjectName());
				busMap.put("subjectName", project.getProjectName()+"-"+project.getProjectNumber());
				busMap.put("businessType",project.getBusinessType());
				busMap.put("customerName",customerName);
				busMap.put("operationType",project.getOperationType());
				busMap.put("flowType",flowTypeKey);
				busMap.put("projectId",projectId);
				
				busMap.put("oppositeID",project.getOppositeID());
				
				flowRunInfo.setBusMap(busMap);//不会存入书库
				flowRunInfo.setDefId(flowId);
				return 1;
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error("启动小额贷款常规流程出错:"+e.getMessage());
				return 0;
			}
	}
public Integer startCreditFlowPawnProject(FlowRunInfo flowRunInfo){
		
	    String customerName = "";
	    String projectNumber = ""; //项目编号
	    String branchNO = "";
	    String degree = flowRunInfo.getRequest().getParameter("degree");
		String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");//股东信息
		Long companyId = ContextUtil.getLoginCompanyId();
		String currentUserId = ContextUtil.getCurrentUserId().toString();
		
		Boolean isSubCompany = Boolean.valueOf(flowRunInfo.getRequest().getParameter("isSubCompany"));//如果true则表示，分公司形式分配任务.否则采取session获取companyId  add by gaoqingru
		Long mineId = Long.valueOf(flowRunInfo.getRequest().getParameter("mineId")) ;//gaoqingrui 通过传递集团公司参数 获取公司id
	    
	    PlPawnProject project = new PlPawnProject(); //初始化项目信息
		Person person = new Person();   //初始化person
		Enterprise  enterprise = new Enterprise();  //初始化enterprise
		EnterpriseBank ebank = new EnterpriseBank();
		
		try {
			String businessTypeKey = flowRunInfo.getBusMap().get("businessTypeKey").toString();
			String operationTypeKey = flowRunInfo.getBusMap().get("operationTypeKey").toString();
			String strDdate = DateUtil.getNowDateTime("yyMMdd");
			
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "slSmallloanProject");
			String flowTypeKey = project.getFlowType();
			ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);
			String flowId = String.valueOf(proDefinition.getDefId());
			Long branchCompanyId = proDefinition.getBranchCompanyId();
			
			
			if(project.getOppositeType().equals("company_customer")){ //企业
				BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
			 }else if(project.getOppositeType().equals("person_customer")){     
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
				BeanUtil.populateEntity(flowRunInfo.getRequest(), ebank, "enterpriseBank");
			 }
			
            //生成项目编号开始 月日年_TZ(投资）_01(当天的第一笔)
			if(null!=companyId){
				Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
				branchNO = org.getBranchNO();
			}else{
				Organization organization = organizationDao.getGroupCompany();
				if(organization!=null){
					branchNO = organization.getBranchNO();
				}
			}
			PlPawnProject plproject = plPawnProjectDao.getProjectNumber(branchNO+"_"+strDdate);
			if(plproject!=null){
				String number = "";
				String proNum = plproject.getProjectNumber();
				String[] proArrs = proNum.split("_");
				int num = new Integer(proArrs[2]);
				num+=1;
				if(num<10){
					number = "00"+num;
				}else if(num>=10&&num<100){
					number = "0"+num;
				}else{
					number = String.valueOf(num);
				}
				projectNumber = branchNO+"_"+strDdate+"_"+number;
			}else{
				projectNumber = branchNO+"_"+strDdate+"_"+"001";
			}
			//生成项目编号结束
			
	     	if(project.getOppositeType().equals("company_customer")){ //企业
	     		//股东信息开始
				List<EnterpriseShareequity> listES=new ArrayList<EnterpriseShareequity>();
		    	if(gudongInfo!=null&&!"".equals(gudongInfo)) {
		    		String[] shareequityArr = gudongInfo.split("@");
		    		for(int i=0; i<shareequityArr.length; i++) {
		    			String str = shareequityArr[i];
		    			JSONParser parser = new JSONParser(new StringReader(str));
						EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity)JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
						listES.add(enterpriseShareequity);
		    		}
				}
		    	//股东信息结束
		    	
			   if(enterprise.getId()==null || enterprise.getId()==0){
				   
				   enterprise.setCompanyId(ContextUtil.getLoginCompanyId());
				   enterprise.setId(null);
				   if(person.getId()==null ||person.getId()==0){//如果企业客户是新增，法人信息也是新增，添加保存法人信息代码 （add by ly   2013-6-18）
					   person.setId(null);
					   person.setCompanyId(ContextUtil.getLoginCompanyId());
					   person.setCreater(ContextUtil.getCurrentUser().getFullname());
					   person.setBelongedId(currentUserId);
			    	   person.setCreaterId(Long.valueOf(currentUserId));
			    	   person.setCreatedate(new Date());
					   personDao.save(person);
					   baseCustomService.getCustomToweb("1", person.getId(),0); 
				   }
				   enterprise.setLegalpersonid(person.getId());
				   enterprise.setCreater(ContextUtil.getCurrentUser().getFullname());
				   enterprise.setBelongedId(currentUserId);
				   enterprise.setCreaterId(Long.valueOf(currentUserId));
				   enterprise.setCreatedate(new Date());
				   enterpriseDao.save(enterprise);
				   baseCustomService.getCustomToweb("0", enterprise.getId(),0);
		       } else{
				   Enterprise persistentEnterprise=this.enterpriseDao.getById(enterprise.getId());
				   persistentEnterprise.setEnterprisename(enterprise.getEnterprisename());
				   persistentEnterprise.setArea(enterprise.getArea());						  
				   persistentEnterprise.setOrganizecode(enterprise.getOrganizecode());					
				   persistentEnterprise.setTelephone(enterprise.getTelephone());
				   enterpriseDao.merge(persistentEnterprise);
				   baseCustomService.getCustomToweb("0", persistentEnterprise.getId(),0);
				   
				   Person p = personDao.getById(person.getId());
				   if(p!=null){
					   p.setName(person.getName());
			     	   p.setSex(person.getSex());
			     	   p.setCardtype(person.getCardtype());
			     	   p.setCardnumber(person.getCardnumber());
			     	   p.setCellphone(person.getCellphone());
			     	   p.setSelfemail(person.getSelfemail());
			     	   personDao.merge(p);
			     	   baseCustomService.getCustomToweb("1", p.getId(),0); 
				   }
			   }
			   
			   Integer duifangzhutiId = enterprise.getId();
			   if(listES!=null && listES.size()>0){
					for(int i=0;i<listES.size();i++){
					   EnterpriseShareequity enterpriseShareequity=listES.get(i);
					   if(enterpriseShareequity.getId()==null||"".equals(enterpriseShareequity.getId())) {
						   enterpriseShareequity.setEnterpriseid(duifangzhutiId);
						   enterpriseShareequityDao.save(enterpriseShareequity);
					   }else {
						   EnterpriseShareequity ps = this.enterpriseShareequityDao.load(enterpriseShareequity.getId());
						   BeanUtil.copyNotNullProperties(ps, enterpriseShareequity);
						   enterpriseShareequityDao.save(ps);
					   }
					}
				}
			   project.setOppositeID(Long.valueOf(duifangzhutiId));
			   customerName = enterprise.getEnterprisename();
			}else if(project.getOppositeType().equals("person_customer")){
			  	if(person.getId()==null||person.getId()==0){   
		     		 person.setId(null);
		     		 person.setCreater(ContextUtil.getCurrentUser().getFullname());
		     		 person.setBelongedId(currentUserId);
		    	     person.setCreaterId(Long.valueOf(currentUserId));
		    	     person.setCreatedate(new Date());
		    	     person.setCompanyId(ContextUtil.getLoginCompanyId());
		    	     personDao.save(person);
		    	     baseCustomService.getCustomToweb("1", person.getId(),0); 
		    	}else{    
		     		Person persistentPerson = this.personDao.getById(person.getId());		     	   
		     	    persistentPerson.setName(person.getName());
		     	    persistentPerson.setSex(person.getSex());
		     	    persistentPerson.setCardtype(person.getCardtype());
		     	    persistentPerson.setCardnumber(person.getCardnumber());
		     	    persistentPerson.setCellphone(person.getCellphone());
		     	    persistentPerson.setMarry(person.getMarry());
		     	    persistentPerson.setSelfemail(person.getSelfemail());
		     	    persistentPerson.setPostcode(person.getPostcode());
		     	    persistentPerson.setPostaddress(person.getPostaddress());
		     		personDao.merge(persistentPerson);
		     		baseCustomService.getCustomToweb("1", persistentPerson.getId(),0); 
		     	}
			   project.setOppositeID(Long.valueOf(person.getId()));
			   customerName = person.getName();
			}
			project.setProjectNumber(projectNumber);
			project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
			project.setBusinessType(businessTypeKey);
			project.setOperationType(operationTypeKey);
			project.setFlowType(flowTypeKey);
			project.setCreateDate(new Date());
			if(isSubCompany){//集团版本，mineId指定分公司id
				project.setMineId(mineId);//add by gaoqingrui 
				project.setCompanyId(mineId);
			}else{
				if(ContextUtil.getLoginCompanyId()!=null){
			    	project.setCompanyId(ContextUtil.getLoginCompanyId());
			    }else{
			    	project.setCompanyId(Long.valueOf("1"));
			    }
			}
			/*if(ContextUtil.getLoginCompanyId()!=null){
		    	project.setCompanyId(ContextUtil.getLoginCompanyId());
		    }else{
		    	project.setCompanyId(Long.valueOf("1"));
		    }*/
			if(degree!=null&&!"".equals(degree)){
			  	project.setAppUserId(degree);
	        }
			project.setLockStatus(Long.valueOf(0));
		    Long projectId = this.plPawnProjectDao.save(project).getProjectId();
		 
		    
		    Map<String,Object> vars = new HashMap<String, Object>();
		    Map<String, Object> busMap = new HashMap<String, Object>();
		    
			vars.put("projectId",projectId);
			vars.put("oppositeType",project.getOppositeType());
			vars.put("businessType",project.getBusinessType());
			vars.put("operationType",project.getOperationType());
			vars.put("branchCompanyId", branchCompanyId);
			flowRunInfo.getVariables().putAll(vars);
			
			busMap.put("projectNumber",project.getProjectNumber());
			busMap.put("projectName", project.getProjectName());
			busMap.put("subjectName", project.getProjectName()+"-"+project.getProjectNumber());
			busMap.put("businessType",project.getBusinessType());
			busMap.put("customerName",customerName);
			busMap.put("operationType",project.getOperationType());
			busMap.put("flowType",flowTypeKey);
			busMap.put("projectId",projectId);
			flowRunInfo.setBusMap(busMap);
			flowRunInfo.setDefId(flowId);
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("启动小额贷款常规流程出错:"+e.getMessage());
			return 0;
		}
}
/**
 * 启动金融担保常规流程
 * @param flowRunInfo
 * @return
 */
public Integer startCreditFlowGLGuaranteeloanProject(FlowRunInfo flowRunInfo){
	
	String projectNumber = "";
	String branchNO = "";
	String customerName = "";
	String degree = flowRunInfo.getRequest().getParameter("degree");
	String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");//股东信息
	String currentUserId = ContextUtil.getCurrentUserId().toString();
	Boolean isSubCompany = Boolean.valueOf(flowRunInfo.getRequest().getParameter("isSubCompany"));//如果true则表示，分公司形式分配任务.否则采取session获取companyId  add by gaoqingru
	Long companyId =  ContextUtil.getLoginCompanyId();//不通过session获取公司Id
	
	Long mineId = Long.valueOf(flowRunInfo.getRequest().getParameter("mineId")) ;//gaoqingrui 通过传递集团公司参数 获取公司id
		
	String strDdate = DateUtil.getNowDateTime("yyMMdd");
	
	GLGuaranteeloanProject project = new GLGuaranteeloanProject();
    Person person = new Person();   //初始化person
	    Enterprise  enterprise = new Enterprise();  //初始化enterprise
	    
	 try{  
	    BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "gLGuaranteeloanProject");
		if(project.getOppositeType().equals("company_customer")){ //企业
		   BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
		   BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
		   if(enterprise.getId()==null||enterprise.getId()==0){
			   enterprise.setId(null);
		   }
		}else if(project.getOppositeType().equals("person_customer")) {   
			BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
		}
		
        //生成项目编号开始
		if(companyId!=null){
			Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
			if(org!=null){
				branchNO = org.getBranchNO();
			}
		}else{
			Organization organization = organizationDao.getGroupCompany();
			
			if(organization!=null){
				branchNO = organization.getBranchNO();
			}
		}
		
		GLGuaranteeloanProject glproject = glGuaranteeloanProjectDao.getProjectNumber(branchNO+"_"+strDdate);
		if(glproject!=null){
			String number = "";
			String proNum = glproject.getProjectNumber();
			String[] proArrs = proNum.split("_");
			int num = new Integer(proArrs[2]);
			num+=1;
			if(num<10){
				number = "00"+num;
			}else if(num>=10&&num<100){
				number = "0"+num;
			}else{
				number = String.valueOf(num);
			}
			projectNumber = branchNO+"_"+strDdate+"_"+number;
		}else{
			projectNumber = branchNO+"_"+strDdate+"_"+"001";
		}
		
		List<EnterpriseShareequity> listES=new ArrayList<EnterpriseShareequity>();
    	if(null != gudongInfo && !"".equals(gudongInfo)) {
    		String[] shareequityArr = gudongInfo.split("@");
    		for(int i=0; i<shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity)JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
				listES.add(enterpriseShareequity);
			}
		}
    	
    	if(person.getId()!=null&&person.getId()==0){
    		person.setId(null);
    		person.setCompanyId(ContextUtil.getLoginCompanyId());
    		person.setCreater(ContextUtil.getCurrentUser().getFullname());
    		person.setBelongedId(currentUserId);
    		person.setCreaterId(Long.valueOf(currentUserId));
    		person.setCreatedate(new Date());
    		personDao.save(person).getId();
    	}else{
    		 Person persistentPerson=this.personDao.getById(person.getId());
     	     persistentPerson.setMarry(person.getMarry());
     	     persistentPerson.setName(person.getName());
     	     persistentPerson.setSex(person.getSex());
     	     persistentPerson.setCardtype(person.getCardtype());
     	     persistentPerson.setCardnumber(person.getCardnumber());
     	     persistentPerson.setTelphone(person.getTelphone());
     	     persistentPerson.setPostcode(person.getPostcode());
     	     persistentPerson.setSelfemail(person.getSelfemail());
     	     persistentPerson.setPostaddress(person.getPostaddress());
     		 personDao.merge(persistentPerson);
    	}
    	
		Integer farenId = person.getId();
		if(project.getOppositeType().equals("company_customer")){ //企业
			enterprise.setLegalpersonid(farenId);
			if(enterprise.getId()==null||enterprise.getId()==0){
			   enterprise.setId(null);
			   enterprise.setLegalpersonid(person.getId());
			   enterprise.setCreater(ContextUtil.getCurrentUser().getFullname());
			   enterprise.setBelongedId(currentUserId);
			   enterprise.setCreaterId(Long.valueOf(currentUserId));
			   enterprise.setCreatedate(new Date());
			   enterprise.setCompanyId(ContextUtil.getLoginCompanyId());
			   enterpriseDao.save(enterprise);
			} else{
			   Enterprise persistentEnterprise = this.enterpriseDao.getById(enterprise.getId());
			   persistentEnterprise.setEnterprisename(enterprise.getEnterprisename());
			   persistentEnterprise.setArea(enterprise.getArea());
			   persistentEnterprise.setShortname(enterprise.getShortname());
			   persistentEnterprise.setHangyeType(enterprise.getHangyeType());
			   persistentEnterprise.setOrganizecode(enterprise.getOrganizecode());
			   persistentEnterprise.setCciaa(enterprise.getCciaa());
			   persistentEnterprise.setTelephone(enterprise.getTelephone());
			   persistentEnterprise.setPostcoding(enterprise.getPostcoding());
			   persistentEnterprise.setLegalpersonid(person.getId());
			   enterpriseDao.merge(persistentEnterprise);
			}
			
			Integer duifangzhutiId = enterprise.getId();
			if(listES!=null&&listES.size()>0){
				for(int i=0;i<listES.size();i++){
				   EnterpriseShareequity enterpriseShareequity = listES.get(i);
				   if(enterpriseShareequity.getId()==null|| "".equals(enterpriseShareequity.getId())){
					   enterpriseShareequity.setEnterpriseid(duifangzhutiId);
					   enterpriseShareequityDao.save(enterpriseShareequity);
				   }else{
					   EnterpriseShareequity ps = this.enterpriseShareequityDao.load(enterpriseShareequity.getId());
					   BeanUtil.copyNotNullProperties(ps, enterpriseShareequity);
					   enterpriseShareequityDao.save(ps);
				   }
				}
			}
			project.setOppositeID(Long.valueOf(duifangzhutiId));
			customerName = enterprise.getEnterprisename();
		}else if(project.getOppositeType().equals("person_customer")){
		   project.setOppositeID(Long.valueOf(farenId));
		   customerName = person.getName();
		}
		
		/*flowTypeKey = project.getFlowType();
        if(flowTypeKey.equals("zmNormalFlow")){
	    	CustomerBankRelationPerson customerBankRelationPerson =new CustomerBankRelationPerson();
	    	BeanUtil.populateEntity(flowRunInfo.getRequest(), customerBankRelationPerson, "customerBankRelationPerson");
	    	if(null==customerBankRelationPerson.getId() || customerBankRelationPerson.getId()==0) //第一次保存
    		{
                 bankRelationPersonService.addPerson(customerBankRelationPerson);
                 project.setBankId(customerBankRelationPerson.getId());
    		}
    		else
    		{
    			CustomerBankRelationPerson pasint=this.bankRelationPersonService.queryPerson(customerBankRelationPerson.getId());
    			pasint.setFenbankid(customerBankRelationPerson.getFenbankid());
    			pasint.setBlmphone(customerBankRelationPerson.getBlmphone());
    			pasint.setBlmtelephone(customerBankRelationPerson.getBlmtelephone());
    			pasint.setName(customerBankRelationPerson.getName());
    			pasint.setDuty(customerBankRelationPerson.getDuty());
    			pasint.setEmail(customerBankRelationPerson.getEmail());
    			bankRelationPersonService.updatePerson(pasint);
    			project.setBankId(customerBankRelationPerson.getId());
    		}
	    }*/
		String flowTypeKey = project.getFlowType();
        String businessTypeKey = flowRunInfo.getBusMap().get("businessTypeKey").toString();
		    String operationTypeKey = flowRunInfo.getBusMap().get("operationTypeKey").toString();
		String flowId = String.valueOf(proDefinitionDao.getByKey(flowTypeKey).getDefId());
		project.setBusinessType(businessTypeKey);
		project.setOperationType(operationTypeKey);
		project.setFlowType(flowTypeKey);
		project.setProjectNumber(projectNumber);
		project.setCreateDate(new Date());
		if(isSubCompany){//集团版本，mineId指定分公司id
			project.setMineId(mineId);//add by gaoqingrui 
			project.setCompanyId(mineId);
		}else{
			if(ContextUtil.getLoginCompanyId()!=null){
		    	project.setCompanyId(ContextUtil.getLoginCompanyId());
		    }else{
		    	project.setCompanyId(Long.valueOf("1"));
		    }
		}
		project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
		project.setBmStatus(Constants.PROJECT_STATUS_ACTIVITY);
		
		
		
		if(degree!=null&&!"".equals(degree)){
			project.setAppUserIdOfA(degree);
        }
		
		Long projectId = glGuaranteeloanProjectDao.save(project).getProjectId();
	    
		//初始化准入原则
		ourProcreditAssuretenetDao.initAssuretenet(String.valueOf(projectId), businessTypeKey, operationTypeKey,project.getOppositeType());
		//初始化担保材料
		ourProcreditMaterialsEnterpriseDao.initMaterials(String.valueOf(projectId), businessTypeKey, operationTypeKey);
		
		//初始化费用收支
		/*Short a = 0;
	    if(this.slPlansToChargeDao.getbytype(0)!=null && this.slPlansToChargeDao.getbytype(0).size()>0){   
	    	slActualToChargeDao.initActualCharges(project.getProjectId(),project.getProjectNumber(),project.getProjectName(),this.slPlansToChargeDao.getbyOperationType(0,businessTypeKey),businessTypeKey,a);
	    }*/
	    
	    Map<String,Object> vars = new HashMap<String, Object>();
	    Map<String, Object> busMap = new HashMap<String, Object>();
	    
		vars.put("projectId",projectId);
		vars.put("businessType",project.getBusinessType());
		vars.put("oppositeType",project.getOppositeType());
		vars.put("operationType",project.getOperationType());
		flowRunInfo.getVariables().putAll(vars);
		
		busMap.put("projectNumber",projectNumber);
		busMap.put("projectName", project.getProjectName());
		busMap.put("subjectName", project.getProjectName()+"-"+projectNumber);
		busMap.put("businessType",project.getBusinessType());
		busMap.put("customerName",customerName);
		busMap.put("operationType",project.getOperationType());
		busMap.put("flowType",project.getFlowType());
		busMap.put("projectId",projectId);
		flowRunInfo.setBusMap(busMap);
		flowRunInfo.setDefId(flowId);
		return 1;
	}
	catch (Exception e) {
		e.printStackTrace();
		logger.error("启动担保常规流程出错:"+e.getMessage());
		return 0;
	}
}

	
	/**
	 * 启动融资租赁
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	public Integer startCreditFlowLeaseFinanceProject(FlowRunInfo flowRunInfo){
		
		String projectNumber = "";
		String branchNO = "";
		String customerName = "";
		String degree = flowRunInfo.getRequest().getParameter("degree");
		String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");//股东信息
		String currentUserId = ContextUtil.getCurrentUserId().toString();
		Boolean isSubCompany = Boolean.valueOf(flowRunInfo.getRequest().getParameter("isSubCompany"));//如果true则表示，分公司形式分配任务.否则采取session获取companyId  add by gaoqingru
		Long companyId =  ContextUtil.getLoginCompanyId();//不通过session获取公司Id
		
		Long mineId = Long.valueOf(flowRunInfo.getRequest().getParameter("mineId")) ;//gaoqingrui 通过传递集团公司参数 获取公司id
			
		String strDdate = DateUtil.getNowDateTime("yyMMdd");
		
		FlLeaseFinanceProject project = new FlLeaseFinanceProject();
		
//		flLeaseFinanceProjectDao
		
        Person person = new Person();   //初始化person
   	    Enterprise  enterprise = new Enterprise();  //初始化enterprise
   	    
		 try{  
		    BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "flLeaseFinanceProject");
			if(project.getOppositeType().equals("company_customer")){ //企业
			   BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
			   BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
			   if(enterprise.getId()==null||enterprise.getId()==0){
				   enterprise.setId(null);
			   }
			}else if(project.getOppositeType().equals("person_customer")) {   
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
			}
			
            //生成项目编号开始
			if(companyId!=null){
				Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
//				Organization org = isSubCompany? organizationDao.getBranchById(companyId):(Organization) creditBaseDao.getById(Organization.class, companyId);//add by gaoqingrui
				if(org!=null){
					branchNO = org.getBranchNO();
				}
			}else{
				Organization organization = organizationDao.getGroupCompany();
				
				if(organization!=null){
					branchNO = organization.getBranchNO();
				}
			}
			
			FlLeaseFinanceProject flproject = flLeaseFinanceProjectDao.getProjectNumber(branchNO+"_"+strDdate);
			
			if(flproject!=null){
				String number = "";
				String proNum = flproject.getProjectNumber();
				String[] proArrs = proNum.split("_");
				int num = new Integer(proArrs[2]);
				num+=1;
				if(num<10){
					number = "00"+num;
				}else if(num>=10&&num<100){
					number = "0"+num;
				}else{
					number = String.valueOf(num);
				}
				projectNumber = branchNO+"_"+strDdate+"_"+number;
			}else{
				projectNumber = branchNO+"_"+strDdate+"_"+"001";
			}
			
			List<EnterpriseShareequity> listES=new ArrayList<EnterpriseShareequity>();
	    	if(null != gudongInfo && !"".equals(gudongInfo)) {
	    		String[] shareequityArr = gudongInfo.split("@");
	    		for(int i=0; i<shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity)JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
					listES.add(enterpriseShareequity);
				}
			}
	    	
	    	if(person.getId()!=null&&person.getId()==0){
	    		person.setId(null);
	    		person.setCompanyId(ContextUtil.getLoginCompanyId());
	    		person.setCreater(ContextUtil.getCurrentUser().getFullname());
	    		person.setBelongedId(currentUserId);
	    		person.setCreaterId(Long.valueOf(currentUserId));
	    		person.setCreatedate(new Date());
	    		personDao.save(person).getId();
	    	}else{
	    		 Person persistentPerson=this.personDao.getById(person.getId());
	     	     persistentPerson.setMarry(person.getMarry());
	     	     persistentPerson.setName(person.getName());
	     	     persistentPerson.setSex(person.getSex());
	     	     persistentPerson.setCardtype(person.getCardtype());
	     	     persistentPerson.setCardnumber(person.getCardnumber());
	     	     persistentPerson.setTelphone(person.getTelphone());
	     	     persistentPerson.setPostcode(person.getPostcode());
	     	     persistentPerson.setSelfemail(person.getSelfemail());
	     	     persistentPerson.setPostaddress(person.getPostaddress());
	     		 personDao.merge(persistentPerson);
	    	}
	    	
			Integer farenId = person.getId();
			if(project.getOppositeType().equals("company_customer")){ //企业
				enterprise.setLegalpersonid(farenId);
				if(enterprise.getId()==null||enterprise.getId()==0){
				   enterprise.setId(null);
				   enterprise.setLegalpersonid(person.getId());
				   enterprise.setCreater(ContextUtil.getCurrentUser().getFullname());
				   enterprise.setBelongedId(currentUserId);
				   enterprise.setCreaterId(Long.valueOf(currentUserId));
				   enterprise.setCreatedate(new Date());
				   enterprise.setCompanyId(ContextUtil.getLoginCompanyId());
				   enterpriseDao.save(enterprise);
				} else{
				   Enterprise persistentEnterprise = this.enterpriseDao.getById(enterprise.getId());
				   persistentEnterprise.setEnterprisename(enterprise.getEnterprisename());
				   persistentEnterprise.setArea(enterprise.getArea());
				   persistentEnterprise.setShortname(enterprise.getShortname());
				   persistentEnterprise.setHangyeType(enterprise.getHangyeType());
				   persistentEnterprise.setOrganizecode(enterprise.getOrganizecode());
				   persistentEnterprise.setCciaa(enterprise.getCciaa());
				   persistentEnterprise.setTelephone(enterprise.getTelephone());
				   persistentEnterprise.setPostcoding(enterprise.getPostcoding());
				   persistentEnterprise.setLegalpersonid(person.getId());
				   enterpriseDao.merge(persistentEnterprise);
				}
				
				Integer duifangzhutiId = enterprise.getId();
				if(listES!=null&&listES.size()>0){
					for(int i=0;i<listES.size();i++){
					   EnterpriseShareequity enterpriseShareequity = listES.get(i);
					   if(enterpriseShareequity.getId()==null|| "".equals(enterpriseShareequity.getId())){
						   enterpriseShareequity.setEnterpriseid(duifangzhutiId);
						   enterpriseShareequityDao.save(enterpriseShareequity);
					   }else{
						   EnterpriseShareequity ps = this.enterpriseShareequityDao.load(enterpriseShareequity.getId());
						   BeanUtil.copyNotNullProperties(ps, enterpriseShareequity);
						   enterpriseShareequityDao.save(ps);
					   }
					}
				}
				
				project.setOppositeID(Long.valueOf(duifangzhutiId));
				customerName = enterprise.getEnterprisename();
			}else if(project.getOppositeType().equals("person_customer")){
			   project.setOppositeID(Long.valueOf(farenId));
			   customerName = person.getName();
			}

			String leasingType = project.getLeasingType();
			String flowTypeKey = project.getFlowType();
            String businessTypeKey = flowRunInfo.getBusMap().get("businessTypeKey").toString();
 		    String operationTypeKey = flowRunInfo.getBusMap().get("operationTypeKey").toString();
			String flowId = String.valueOf(proDefinitionDao.getByKey(flowTypeKey).getDefId());
			project.setBusinessType(businessTypeKey);
			project.setOperationType(operationTypeKey);
			project.setFlowType(flowTypeKey);
			project.setProjectNumber(projectNumber);
			project.setCreateDate(new Date());
			
			if(isSubCompany){//集团版本，mineId指定分公司id
				project.setMineId(mineId);//add by gaoqingrui 
				project.setCompanyId(mineId);
			}else{
				if(ContextUtil.getLoginCompanyId()!=null){
			    	project.setCompanyId(ContextUtil.getLoginCompanyId());
			    }else{
			    	project.setCompanyId(Long.valueOf("1"));
			    }
			}
			
			
			if(degree!=null&&!"".equals(degree)){
				project.setAppUserId(degree);
	        }
			project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
			
			Long projectId = flLeaseFinanceProjectDao.save(project).getProjectId();
			
			//初始化小贷的归档材料
		    ourArchivesMaterialsDao.initMateriais(projectId.toString(), businessTypeKey, operationTypeKey);
		    
			//初始化准入原则
			ourProcreditAssuretenetDao.initAssuretenet(String.valueOf(projectId), businessTypeKey, operationTypeKey,project.getOppositeType());
			//初始化担保材料
			ourProcreditMaterialsEnterpriseDao.initMaterials(String.valueOf(projectId), businessTypeKey, operationTypeKey);
			
			//初始化费用收支
			Short a = 0;
		    if(this.slPlansToChargeDao.getbytype(0)!=null && this.slPlansToChargeDao.getbytype(0).size()>0){   
		    	slActualToChargeDao.initActualCharges(project.getProjectId(),project.getProjectNumber(),project.getProjectName(),this.slPlansToChargeDao.getbyOperationType(0,businessTypeKey),businessTypeKey,a);
		    }
		    
		    Map<String,Object> vars = new HashMap<String, Object>();
		    Map<String, Object> busMap = new HashMap<String, Object>();
		    
			vars.put("projectId",projectId);
			vars.put("businessType",project.getBusinessType());
			vars.put("oppositeType",project.getOppositeType());
			vars.put("operationType",project.getOperationType());
			vars.put("LeasingType",project.getLeasingType());
			flowRunInfo.getVariables().putAll(vars);
			
			busMap.put("projectNumber",projectNumber);
			busMap.put("projectName", project.getProjectName());
			busMap.put("subjectName", project.getProjectName()+"-"+projectNumber);
			busMap.put("businessType",project.getBusinessType());
			busMap.put("customerName",customerName);
			busMap.put("operationType",project.getOperationType());
			busMap.put("flowType",project.getFlowType());
			busMap.put("projectId",projectId);
			flowRunInfo.setBusMap(busMap);
			flowRunInfo.setDefId(flowId);
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("启动担保常规流程出错:"+e.getMessage());
			return 0;
		}
	}
	
	public Integer startCreditFlowFlFinancingProject(FlowRunInfo flowRunInfo){

		String customerName = "";
		String projectNumber = "";
		String branchNO = "";
		Long companyId = ContextUtil.getLoginCompanyId();
		
		FlFinancingProject project = new FlFinancingProject(); // 初始化项目信息
		InvestPerson person = new InvestPerson(); // 初始化person
		EnterpriseBank enterpriseBank = new EnterpriseBank();//初始化付息账号信息
		//Integer projectSize = 0;
		try {
			//projectSize = this.flFinancingProjectDao.getAll().size(); // 查询项目总数
			String strDdate = DateUtil.getNowDateTime("yyMMdd");
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"flFinancingProject");
			BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "investPerson");
			String degree = flowRunInfo.getRequest().getParameter("degree");
			String businessTypeKey = flowRunInfo.getBusMap().get("businessTypeKey").toString();
	     	String operationTypeKey = flowRunInfo.getBusMap().get("operationTypeKey").toString();
			
			//生成项目编号开始
			if(companyId!=null){
				Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
				if(org!=null){
					branchNO = org.getBranchNO();
				}
			}else{
				Organization organization = organizationDao.getGroupCompany();
				if(organization!=null){
					branchNO = organization.getBranchNO();
				}
			}
			
			FlFinancingProject flproject = flFinancingProjectDao.getProjectNumber(branchNO+"_"+strDdate);
			if(flproject!=null){
				String number = "";
				String proNum = flproject.getProjectNumber();
				String[] proArrs = proNum.split("_");
				int num = new Integer(proArrs[2]);
				num+=1;
				if(num<10){
					number = "00"+num;
				}else if(num>=10&&num<100){
					number = "0"+num;
				}else{
					number = String.valueOf(num);
				}
				projectNumber = branchNO+"_"+strDdate+"_"+number;
			}else{
				projectNumber = branchNO+"_"+strDdate+"_"+"001";
			}
			
			String currentUserId = ContextUtil.getCurrentUserId().toString();
			
			//EnterpriseBankServ EnterpriseBankServ=(EnterpriseBankServ)AppUtil.getBean("bankServ");
			
			if (person.getPerId() == 0) {
				person.setPerId(null);
				person.setCreater(ContextUtil.getCurrentUser().getFullname());
				person.setCreaterId(Long.valueOf(currentUserId));
				person.setCreatedate(new Date());
				person.setBelongedId(currentUserId.toString());
				person.setCompanyId(ContextUtil.getLoginCompanyId());
				investPersonDao.save(person);
			} else {
				InvestPerson persistentPerson = investPersonDao.get(person.getPerId());
				BeanUtil.copyNotNullProperties(persistentPerson, person);
				//BeanUtils.copyProperties(person, persistentPerson,new String[] {"id","creater","createrId","createdate","companyId"});
				investPersonDao.merge(persistentPerson);
			}
			Long farenId = person.getPerId();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank, "enterpriseBank");
			
			//flFinancingProjectService.updateEnterpriseBankInfo(enterpriseBank, farenId, farenId.intValue());
			if(enterpriseBank.getId() == 0) {
				enterpriseBank.setId(null);
				InvestPerson p = investPersonDao.get(farenId);
				Long personCompanyId = p.getCompanyId();
				enterpriseBank.setEnterpriseid(farenId.intValue());
				enterpriseBank.setCompanyId(personCompanyId);
				
				enterpriseBankDao.save(enterpriseBank);
			}else {
				EnterpriseBank persistentEnterpriseBank = enterpriseBankDao.getBankList(person.getPerId().intValue(), Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("1")).get(0);
				BeanUtils.copyProperties(enterpriseBank, persistentEnterpriseBank,new String[] {"id","companyId","enterpriseid","isInvest"});
			
				enterpriseBankDao.merge(persistentEnterpriseBank);
			}
			
			if (ContextUtil.getLoginCompanyId() != null) {
				project.setCompanyId(ContextUtil.getLoginCompanyId());
			} else {
				project.setCompanyId(Long.valueOf("1"));
			}
			if (null != degree && !"".equals(degree)) {
				project.setAppUserId(degree);
			}
			project.setOppositeType("person_customer");
			project.setOppositeID(Long.valueOf(farenId));
			customerName = person.getPerName();
			project.setProjectNumber(projectNumber);
			project.setBusinessType(businessTypeKey);
			project.setOperationType(operationTypeKey);
			project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
			project.setCreateDate(new Date());
			Long projectId = this.flFinancingProjectDao.save(project).getProjectId();
			//ProDefinition proDefinition = proDefinitionDao.getByCompanyIdProcessName(companyId, "qdsyFinancingBusinessFlow");
			String flowTypeKey = project.getFlowType();
			ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);
			String flowId = String.valueOf(proDefinition.getDefId());
			
			Map<String,Object> vars = new HashMap<String, Object>();
		    Map<String, Object> busMap = new HashMap<String, Object>();
		    
			vars.put("projectId",projectId);
			vars.put("businessType",businessTypeKey);
			vars.put("oppositeType",project.getOppositeType());
			vars.put("operationType",operationTypeKey);
			flowRunInfo.getVariables().putAll(vars);
			
			busMap.put("projectNumber",projectNumber);
			busMap.put("projectName", project.getProjectName());
			busMap.put("subjectName", project.getProjectName()+"-"+projectNumber);
			busMap.put("businessType",businessTypeKey);
			busMap.put("customerName",customerName);
			busMap.put("operationType",operationTypeKey);
			busMap.put("flowType",project.getFlowType());
			busMap.put("projectId",projectId);
			
			flowRunInfo.setBusMap(busMap);
			flowRunInfo.setDefId(flowId);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("启动融资项目出错:" + e.getMessage());
			return 0;
		}
	}
	
	public Integer startCreditFlowSgLawsuitguaranteeProject(FlowRunInfo flowRunInfo){
		SgLawsuitguaranteeProject project=new SgLawsuitguaranteeProject();
        Person person=new Person();   //初始化person
   	    Enterprise  enterprise=new Enterprise();  //初始化enterprise
		 try
		 {  
			String projectId="";
		    BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "sgLawsuitguaranteeProject");
		   // project.setBusinessType("BeNotFinancing");
			 String degree=flowRunInfo.getRequest().getParameter("degree");
			 if(null!=degree && !"".equals(degree))
	         {
			  		project.setAppUserId(degree);
	         }
			if(project.getOppositeType().equals("company_customer")) //企业
			{
			   BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
			   BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
			   if(null==enterprise.getId() || enterprise.getId()==0)
			   {
				      enterprise.setId(null);
			   }
			}
			else if(project.getOppositeType().equals("person_customer")) 
			{   
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
			}
			
			Integer projectSize=0;
			List<GLGuaranteeloanProject> projectOfTodays =glGuaranteeloanProjectDao.getTodayProjectList((DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd")));
			if(null!=projectOfTodays && projectOfTodays.size()>0)
			{
				projectSize=projectOfTodays.size();
			}
			projectSize=projectSize+1;
			DecimalFormat df=new DecimalFormat("00");
			String strSize=df.format(projectSize);
			String strDdate=DateUtil.getNowDateTime("yyyyMMdd");  
			String strType="_DB";
			projectId=strDdate+strType+"_"+strSize;
			
//			Date dateNow=DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd");
		    project.setCreateDate(new Date());
		     
			String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");//股东信息
			List<EnterpriseShareequity> listES=new ArrayList<EnterpriseShareequity>();
	    	if(null != gudongInfo && !"".equals(gudongInfo)) {
					 String[] shareequityArr = gudongInfo.split("@");
					 for(int i=0; i<shareequityArr.length; i++) {
							String str = shareequityArr[i];
							JSONParser parser = new JSONParser(new StringReader(str));
							try{
								EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity)JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
								listES.add(enterpriseShareequity);
							
							} catch(Exception e) {
								e.printStackTrace();
								logger.error("CreditProjectServiceImpl:"+e.getMessage());
							}
						}
			}
	    	String currentUserId = ContextUtil.getCurrentUserId().toString();
	    	if(null!=person.getId() && person.getId()==0)
	    	{
	    	       person.setId(null);
	    	       person.setCreater(ContextUtil.getCurrentUser().getFullname());
	    	       person.setBelongedId(currentUserId);
	    	       person.setCompanyId(ContextUtil.getLoginCompanyId());
	    	       person.setCreaterId(Long.valueOf(currentUserId));
		    	   person.setCreatedate(new Date());
	    	       personDao.save(person).getId();
	    	}
	    	else{
	    		
	    		 Person persistentPerson=this.personDao.getById(person.getId());
	     	     persistentPerson.setMarry(person.getMarry());
	     	     persistentPerson.setName(person.getName());
	     	     persistentPerson.setSex(person.getSex());
	     	     persistentPerson.setCardtype(person.getCardtype());
	     	     persistentPerson.setCardnumber(person.getCardnumber());
	     	     persistentPerson.setTelphone(person.getTelphone());
	     	     persistentPerson.setPostcode(person.getPostcode());
	     	     persistentPerson.setSelfemail(person.getSelfemail());
	     	     persistentPerson.setPostaddress(person.getPostaddress());
	     		 personDao.merge(persistentPerson);
	    	}
			Integer farenId=person.getId();
			String customerName="";
			if(project.getOppositeType().equals("company_customer")) //企业
			{
				   enterprise.setLegalpersonid(farenId);
				   if(null==enterprise.getId() || enterprise.getId()==0)
			       {
					   enterprise.setId(null);
					   enterprise.setLegalpersonid(person.getId());
					   enterprise.setCreater(ContextUtil.getCurrentUser().getFullname());
					   enterprise.setBelongedId(currentUserId);
					   enterprise.setCreaterId(Long.valueOf(currentUserId));
					   enterprise.setCreatedate(new Date());
					   enterprise.setCompanyId(ContextUtil.getLoginCompanyId());
					   enterpriseDao.save(enterprise);
			       }
				   else{
					   Enterprise persistentEnterprise=this.enterpriseDao.getById(enterprise.getId());
					   persistentEnterprise.setEnterprisename(enterprise.getEnterprisename());
					   persistentEnterprise.setArea(enterprise.getArea());
					   persistentEnterprise.setShortname(enterprise.getShortname());
					   persistentEnterprise.setHangyeType(enterprise.getHangyeType());
					   persistentEnterprise.setOrganizecode(enterprise.getOrganizecode());
					   persistentEnterprise.setCciaa(enterprise.getCciaa());
					   persistentEnterprise.setTelephone(enterprise.getTelephone());
					   persistentEnterprise.setPostcoding(enterprise.getPostcoding());
					   persistentEnterprise.setLegalpersonid(person.getId());
					   enterpriseDao.merge(persistentEnterprise);
				   }
				   Integer  duifangzhutiId=enterprise.getId();
					if(listES!=null && listES.size()>0)
					{
						for(int i=0;i<listES.size();i++)
						{
						   EnterpriseShareequity enterpriseShareequity=listES.get(i);
						   if(null==enterpriseShareequity.getId() ||  "".equals(enterpriseShareequity.getId()))
						   {
							   enterpriseShareequity.setEnterpriseid(duifangzhutiId);
							   enterpriseShareequityDao.save(enterpriseShareequity);
						   }
						   else 
						   {
							   EnterpriseShareequity ps=this.enterpriseShareequityDao.load(enterpriseShareequity.getId());
							  
							   BeanUtil.copyNotNullProperties(ps, enterpriseShareequity);
							   enterpriseShareequityDao.save(ps);
						   }
						 
						  
						}
					}
				   project.setOppositeID(Long.valueOf(duifangzhutiId));
				   customerName=enterprise.getEnterprisename();
				   
			}
			else if(project.getOppositeType().equals("person_customer"))
			{
				   project.setOppositeID(Long.valueOf(farenId));
				   customerName=person.getName();
			}
			
			String flowTypeKey="";
			flowTypeKey=project.getFlowType();
            
			String businessTypeKey=flowRunInfo.getBusMap().get("businessTypeKey").toString();
			String operationTypeKey=flowRunInfo.getBusMap().get("operationTypeKey").toString();
//			if(null!=this.globalTypeDao.get(Long.valueOf(project.getBusinessType())))
//		    {
//				businessTypeKey=globalTypeDao.get(Long.valueOf(project.getBusinessType())).getNodeKey();
//			}
			/*if(null!=this.globalTypeDao.get(Long.valueOf(project.getOperationType()))){
				operationTypeKey=globalTypeDao.get(Long.valueOf(project.getOperationType())).getNodeKey();
			}*/
			String flowId=String.valueOf(proDefinitionDao.getByKey(flowTypeKey).getDefId());
			project.setBusinessType(businessTypeKey);
			project.setOperationType(operationTypeKey);
			project.setFlowType(flowTypeKey);
			project.setProjectNumber(projectId);
			project.setProjectStatus(Short.valueOf("0"));
			Long projectFolowId =sgLawsuitguaranteeProjectDao.save(project).getProjectId();
		    
			//初始化担保材料
			ourProcreditMaterialsEnterpriseDao.initMaterials(String.valueOf(projectId), businessTypeKey, operationTypeKey);
			
			//初始化费用收支
			Short a=0;
		    if(this.slPlansToChargeDao.getbytype(0)!=null && this.slPlansToChargeDao.getbytype(0).size()>0)
		    {   
		    	slActualToChargeDao.initActualCharges(project.getProjectId(),project.getProjectNumber(),project.getProjectName(),this.slPlansToChargeDao.getbyOperationType(0,"BeNotFinancing"),"BeNotFinancing",a);
		    }
		    Map<String,Object> vars=new HashMap<String, Object>();
			vars.put("projectId",projectFolowId);
			vars.put("businessType",project.getBusinessType());
			vars.put("oppositeType",project.getOppositeType());
			flowRunInfo.getVariables().putAll(vars);
			Map<String, Object> busMap=new HashMap<String, Object>();
			busMap.put("projectNumber",projectId);
			busMap.put("projectName", project.getProjectName());
			busMap.put("subjectName", project.getProjectName()+"-"+projectId);
			busMap.put("businessType",project.getBusinessType());
			busMap.put("customerName",customerName);
			busMap.put("operationType",project.getOperationType());
			busMap.put("flowType",project.getFlowType());
			busMap.put("projectId",projectFolowId);
			flowRunInfo.setBusMap(busMap);
			flowRunInfo.setDefId(flowId);
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("CreditProjectServiceImpl:"+e.getMessage());
			return 0;
		}
	}
	@Override
    public Integer startCreditFlowProject(FlowRunInfo flowRunInfo) {
		
		Map<String, String> busMap = new HashMap<String, String>();
		String businessType = flowRunInfo.getRequest().getParameter("businessType");//业务类别
	    String operationType=flowRunInfo.getRequest().getParameter("operationType");//业务品种
	    String definitionType=flowRunInfo.getRequest().getParameter("definitionType");//自定义的类型
	    String operationTypeKey = "";//业务品种的key值(CompanyBusiness,PersonalConsumeBusiness,FinancingBusiness等)
		try{
			
			if(definitionType!=null&&!"".equals(definitionType)){
				businessType = "";
				operationType = "";
			}else{
				if(operationType!=null&&!"".equals(operationType)&&!"TwoGrades".equals(operationType)){
					businessType = "";
				}
			}
			
			if(businessType!=null&&!"".equals(businessType)){//只有业务类别-流程类别的情况(两级)
				busMap = this.getOperationType(businessType);
			}
			
			if(operationType!=null&&!"".equals(operationType)&&!"TwoGrades".equals(operationType)){//只有业务类别-业务品种-流程类别的情况(三级)
				busMap = this.getOperationType(operationType);
			}
			
			if(definitionType!=null&&!"".equals(definitionType)){//只有业务类别-业务品种-自定义-流程类别的情况(四级)
				busMap = this.getOperationType(definitionType);
			}
	 		
			//如果再分级则以此类推。。。(一个新的属性代表新的分级的属性,都只为获取第二个属性).
			//Guarantee_CompanyBusiness_test_test1
			//第一个属性：Guarantee已经代表了对应的项目表；第二个属性CompanyBusiness代表在同一个项目表的不同类型，如小贷、微贷。
			//最后的一个属性的key在global_type中对应的ID保存在流程定义表pro_definition中的属性proTypeId,最后一个属性为了查询显示
			//对应的流程信息。第二个属性到最后一个属性之间的属性没有实际意义，只代表了级数或者说区分在global_type中不同的key。
			//在这里启动对应的流程只需要前两个属性，对应的启动方法里获取flowType用户选择的流程的key。
			operationTypeKey = busMap.get("operationTypeKey");
			if(operationTypeKey!=null&&!"".equals(operationTypeKey)){
				flowRunInfo.setBusMap(busMap);
				if(operationTypeKey.contains("MicroLoanBusiness")){//小额贷款-微型贷款业务
		 			return this.startCreditFlowSlSmallloanProjectOfMicro(flowRunInfo);
		 		}else if(operationTypeKey.contains("SmallLoanBusiness")){//小贷贷款-小额贷款业务
	 			    return this.startCreditFlowSlSmallloanProject(flowRunInfo);
		 		}else if(operationTypeKey.contains("CompanyBusiness")){//金融担保-企业经营型贷款担保
		 			return this.startCreditFlowGLGuaranteeloanProject(flowRunInfo);
		 		}else if(operationTypeKey.contains("PersonalConsumeBusiness")){//金融担保-个人消费型贷款担保
		 			//return this.startCreditFlowGLGuaranteeloanProject(flowRunInfo);
		 		}else if(operationTypeKey.contains("FinancingBusiness")||operationTypeKey.contains("NoApprovalBusiness")){//资金融资-资金融资业务
		 			return this.startCreditFlowFlFinancingProject(flowRunInfo);
		 			//return flFinancingProjectService.startCreditFlowFlFinancingProject(flowRunInfo);
		 		}else if(operationTypeKey.contains("lawsuitguarantee")){//诉讼保全担保
		 			return this.startCreditFlowSgLawsuitguaranteeProject(flowRunInfo);
		 		}else if(operationTypeKey.contains("PawnBusiness")){
		 			return this.startCreditFlowPawnProject(flowRunInfo);
		 		}else if(operationTypeKey.contains("LeaseFinance")){
		 			return this.startCreditFlowLeaseFinanceProject(flowRunInfo);
		 		}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("CreditProjectServiceImpl:"+e.getMessage());
			return 0;
		}
		return 1;
	}
	
	private Map<String, String> getOperationType(String typeId){

		String businessTypeKey = "";
		String operationTypeKey = "";
		Map<String, String> busMap = new HashMap<String, String>();
		Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(typeId);
		if(!isNum.matches()){
			if(typeId!=null&&typeId.indexOf("_")!=-1){
				String[] proArrs = typeId.split("_");
				if(proArrs.length>1){//Guarantee_definitionType_CompanyBusiness无论多少级,获取的都是第一个和最后一个属性值。而且必定是一级目录以上。
					businessTypeKey = proArrs[0];
					operationTypeKey = proArrs[proArrs.length-1];
				}
			}
		
			busMap.put("businessTypeKey", businessTypeKey);
			busMap.put("operationTypeKey", operationTypeKey);
			return busMap;
		}else{
			GlobalType globalType = globalTypeDao.get(Long.valueOf(typeId));
			if(globalType!=null){
				if(globalType.getNodeKey()!=null&&globalType.getNodeKey().indexOf("_")!=-1){
					String[] proArrs = globalType.getNodeKey().split("_");
					if(proArrs.length>1){//Guarantee_definitionType_CompanyBusiness无论多少级,获取的都是第一个和最后一个属性值。而且必定是一级目录以上。
						businessTypeKey = proArrs[0];
						operationTypeKey = proArrs[proArrs.length-1];
					}
				}
			}
			busMap.put("businessTypeKey", businessTypeKey);
			busMap.put("operationTypeKey", operationTypeKey);
			return busMap;
		}
		
	
	/*	String businessTypeKey = "";
		String operationTypeKey = "";
		Map<String, String> busMap = new HashMap<String, String>();
		GlobalType globalType = globalTypeDao.get(Long.valueOf(typeId));
		if(globalType!=null){
			if(globalType.getNodeKey()!=null&&globalType.getNodeKey().indexOf("_")!=-1){
				String[] proArrs = globalType.getNodeKey().split("_");
				if(proArrs.length>1){//Guarantee_definitionType_CompanyBusiness无论多少级,获取的都是第一个和最后一个属性值。而且必定是一级目录以上。
					businessTypeKey = proArrs[0];
					operationTypeKey = proArrs[proArrs.length-1];
				}
			}
		}
		busMap.put("businessTypeKey", businessTypeKey);
		busMap.put("operationTypeKey", operationTypeKey);
		return busMap;*/
	}
	
	public void updateDueDate(ProcessForm processForm,Task task){
		
		String activityName = task.getActivityName();
		Date dueDate = null;
		short timeLimitType = 1;//默认为工作日
		short taskTimeLimit = 1;//默认任务期限为1天
		
		try {
			ProcessRun processRun = processRunDao.get(processForm.getRunId());
			if(processRun!=null){
				String deployId = processRun.getProDefinition().getDeployId();
				ProUserAssign proUserAssign = proUserAssignDao.getByDeployIdActivityName(deployId, activityName);
				if(proUserAssign!=null){
					if(proUserAssign.getTimeLimitType()!=null){
						timeLimitType = proUserAssign.getTimeLimitType();
					}
					if(proUserAssign.getTaskTimeLimit()!=null){
						taskTimeLimit = proUserAssign.getTaskTimeLimit();
					}
					if(timeLimitType==1){//工作日
						dueDate = DateUtils.parseDate(DateUtil.getTaskTimeLimit(taskTimeLimit), new String[]{"yyyy-MM-dd HH:mm:ss"});
					}else if(timeLimitType==2){//正常日
						dueDate = DateUtils.addDays(new Date(), taskTimeLimit);
					}else{
						dueDate = DateUtils.parseDate(DateUtil.getTaskTimeLimit(taskTimeLimit), new String[]{"yyyy-MM-dd HH:mm:ss"});
					}
				}
			}
			
			task.setDuedate(dueDate);
			processForm.setTaskLimitTime(dueDate);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CreditProjectServiceImpl:"+e.getMessage());
		}
	}
	
	//针对追回等情况设置任务期限。
	public void updateDueDate(String piId,String preTaskName,Task newTask){
		Date dueDate = null;
		short timeLimitType = 1;//默认为工作日
		short taskTimeLimit = 1;//默认任务期限为1天
		
		try {
			ProcessRun processRun = processRunDao.getByPiId(piId);
			if(processRun!=null){
				String deployId = processRun.getProDefinition().getDeployId();
				ProUserAssign proUserAssign = proUserAssignDao.getByDeployIdActivityName(deployId, preTaskName);
				if(proUserAssign!=null){
					if(proUserAssign.getTimeLimitType()!=null){
						timeLimitType = proUserAssign.getTimeLimitType();
					}
					if(proUserAssign.getTaskTimeLimit()!=null){
						taskTimeLimit = proUserAssign.getTaskTimeLimit();
					}
					if(timeLimitType==1){//工作日
						dueDate = DateUtils.parseDate(DateUtil.getTaskTimeLimit(taskTimeLimit), new String[]{"yyyy-MM-dd HH:mm:ss"});
					}else if(timeLimitType==2){//正常日
						dueDate = DateUtils.addDays(new Date(), taskTimeLimit);
					}else{
						dueDate = DateUtils.parseDate(DateUtil.getTaskTimeLimit(taskTimeLimit), new String[]{"yyyy-MM-dd HH:mm:ss"});
					}
				}
				newTask.setDuedate(dueDate);
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("设置任务期限出错:"+e.getMessage());
		}
	}
	
	//流程终止修改项目状态--流程节点的终止调用此方法。
	public Integer updateProcessRunStatus(Long runId,Long projectId,String businessType){
		this.updateProcessRunStatus(null, runId, projectId.toString(), businessType, "projectStopInFlow","Small");
		return 1;
	}

	//流程终止修改项目状态--编辑项目-终止项目调用的方法；流程节点的终止也调用此方法。
	@Override
	public Integer updateProcessRunStatus(String taskId,Long runId,String  projectId,String businessType,String comments,String type){
		ProcessRun processRun=processRunService.get(runId);
		if(processRun!=null){
			String piId=processRun.getPiId();
			try{
				//流程结束的时候自动结束流程实例，不需要再次执行jbpmService.endProcessInstance方法。否则出错。设置process_run的piId为空即可。
				//如果是手动终止项目则需要执行以下方法。
				if(!"projectStopInFlow".equals(comments)){
					if(piId!=null){
						ProcessInstance pi= jbpmService.getProcessInstance(piId);
						if(pi!=null){
							jbpmService.endProcessInstance(piId);
						}
					}
				}
				//add by lisl 修改意见与记录中的备注 update by lu 2013.01.15
				AppUser user=ContextUtil.getCurrentUser();
				ProcessForm existForm = null;
				if(taskId!=null&&!"".equals(taskId)){
					String newTaskId = this.getNewTaskId(taskId);
					if(newTaskId!=null&&!"".equals(newTaskId)){
						existForm = processFormService.getByTaskId(newTaskId);
					}else{
						existForm = processFormService.getByTaskId(taskId);
					}
					if(existForm!=null){
						if(!"projectStopInFlow".equals(comments)){
							existForm.setComments(comments);
						}
						this.updateComments(existForm, user.getFullname(),"项目列表或编辑项目中执行了");
					}
				}else{
					VCommonProjectFlow common = vCommonProjectFlowDao.getByRunId(runId);
					if(common!=null&&common.getTaskId()!=null){
						ProcessForm pf = processFormDao.getByTaskId(common.getTaskId());
						if(pf!=null){
							this.updateComments(pf, user.getFullname(),"任务("+pf.getActivityName()+")中选择了");
						}
					}
				}
				//end
				processRun.setRunStatus(ProcessRun.RUN_STATUS_STOP);
				processRun.setPiId(null);
				if(!"".equals(projectId) && projectId != null) {
					if("SmallLoan".equals(businessType) && type.equals("Small")){
						SlSmallloanProject sl=slSmallloanProjectDao.get(Long.valueOf(projectId));
						sl.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						sl.setEndDate(new Date());
						slSmallloanProjectDao.save(sl);
					}else if("SmallLoan".equals(businessType) && type.equals("Bpfund")){
						BpFundProject sl=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.valueOf("0"));
						sl.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						sl.setEndDate(new Date());
						bpFundProjectDao.save(sl);
					}else if("Guarantee".equals(businessType)){
						GLGuaranteeloanProject gp = glGuaranteeloanProjectDao.get(Long.valueOf(projectId));
						gp.setBmStatus(Constants.PROJECT_STATUS_STOP);
						gp.setEndDate(new Date());
						glGuaranteeloanProjectDao.save(gp);
					}else if("Financing".equals(businessType)){
						FlFinancingProject fl = flFinancingProjectDao.get(Long.valueOf(projectId));
						fl.setEndDate(new Date());
						fl.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						flFinancingProjectDao.save(fl);
					}else if("BeNotFinancing".equals(businessType)){
						SgLawsuitguaranteeProject sp = sgLawsuitguaranteeProjectDao.get(Long.valueOf(projectId));
						sp.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						sgLawsuitguaranteeProjectDao.save(sp);
					}else if("LeaseFinance".equals(businessType)){ //leaseFinance businessType  add by gao  ★★★典当没加
						FlLeaseFinanceProject fl = flLeaseFinanceProjectDao.get(Long.valueOf(projectId));
						fl.setEndDate(new Date());
						fl.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						flLeaseFinanceProjectDao.save(fl);
					}else if("Pawn".equals(businessType)){
						PlPawnProject pl=plPawnProjectDao.get(Long.valueOf(projectId));
						pl.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						pl.setEndDate(new Date());
						plPawnProjectDao.save(pl);
					}
					List<SlActualToCharge> slActualToCharges=new ArrayList<SlActualToCharge>();
					slActualToCharges=slActualToChargeDao.listbyproject(Long.valueOf(projectId), businessType);
					for(SlActualToCharge l:slActualToCharges){
						l.setIsCheck(Short.valueOf("3"));
						slActualToChargeDao.save(l);
					}
				 	List<SlFundIntent>  slFundIntents=new ArrayList<SlFundIntent>();
				 	slFundIntents=slFundIntentDao.getByProjectId2(Long.valueOf(projectId), businessType);
	                 for(SlFundIntent s:slFundIntents){
	                	 s.setIsCheck(Short.valueOf("3"));
	                	 slFundIntentDao.save(s);
					}
				}
				
			 	
				processRunService.save(processRun);
				return 1;
			}catch(Exception e){
			    e.printStackTrace();
			    logger.error("CreditProjectServiceImpl:"+e.getMessage());
			    return 0;
			}
		 }
		return 1;
	}
	
	private void updateComments(ProcessForm processForm,String userFullName,String remark){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stopProjectComments = "【"+userFullName+"在"+remark+"终止项目! "+sdf.format(new Date())+"】";
		String projectAssign = processForm.getProjectAssign()!= null?processForm.getProjectAssign() : "";
		String curComments = processForm.getComments()!= null?processForm.getComments() : "";
		if(processForm.getCountersignRefuse()==null||"".equals(processForm.getCountersignRefuse())){
			processForm.setProjectAssign(projectAssign + stopProjectComments);
			processForm.setComments(curComments+stopProjectComments);
		}else{
			List<ProcessForm> list = processFormDao.getListByFromTaskId(processForm.getFromTaskId());
			String stopComments="【系统提示信息：会签投票不通过,项目终止或者进行特殊处理!"+sdf.format(new Date())+"】";
			for(ProcessForm pform:list){
				if(!processForm.getTaskId().equals(pform.getTaskId())){
					String comments = pform.getComments()!= null?pform.getComments() : "";
					pform.setStatus(ProcessForm.STATUS_PROJECTSTOP);
					pform.setComments(stopComments+comments);
					pform.setProjectAssign(stopComments+comments);
					processFormDao.merge(pform);
				}
			}
		}
		processForm.setStatus(ProcessForm.STATUS_PROJECTSTOP);
		processForm.setEndtime(new Date());
		processFormDao.save(processForm);
	}
	
	//任务跳转到小额的"贷中监管"和"项目完成"节点,更新项目状态进入贷中。
	//各个流程跳转到保中、贷中等情况更新项目状态。贷中(保中)和之后的节点顺序定义的规则为2000。
	public Integer updateProjectInMiddle(Long runId){
		
		if(runId!=null){
			ProcessRun processRun = processRunDao.get(runId);
			if(processRun!=null){
				String businessType = processRun.getBusinessType();
				Long projectId = processRun.getProjectId();
				if("SmallLoan".equals(businessType)){
					SlSmallloanProject slSmallloanProject = slSmallloanProjectDao.get(projectId);
					if(slSmallloanProject!=null){
						slSmallloanProject.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
						slSmallloanProjectDao.merge(slSmallloanProject);
					}
				}else if("Guarantee".equals(businessType)){
					GLGuaranteeloanProject guarantee = glGuaranteeloanProjectDao.get(projectId);
					if(guarantee!=null){
						guarantee.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
						glGuaranteeloanProjectDao.merge(guarantee);
					}
				}
			}
		}
		return 1;
	}

	//完成融资项目
	public Integer complateFlProject(Long projectId) {
		try {
			FlFinancingProject fl = flFinancingProjectDao.get(projectId);
			fl.setEndDate(new Date());
			fl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
			flFinancingProjectDao.save(fl);
			return 1;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("CreditProjectServiceImpl:"+e.getMessage());
			return 0;
		}
	}
	
	//删除项目、任务及相关数据
	public Integer removeByRunId(Long runId,String url){
		try{
			
			ProcessRun pr = processRunDao.get(runId);
			String businessType = pr.getBusinessType();
			Long projectId = pr.getProjectId();
			
			this.removeCommonDatas(businessType, projectId, runId,url);//删除项目公共数据
			
			if(pr!=null){//删除小额、金融担保、资金融资项目及相关数据
				if("SmallLoan".equals(businessType)){
					this.removeSmallLoanProjectDatas(businessType, projectId);
				}else if("Guarantee".equals(businessType)){
					this.removeGLGuaranteeloanProjectDatas(businessType, projectId);//businessType索引公共表时候用到。目前暂时未用到。
				}else if("Financing".equals(businessType)){
					this.removeFlFinancingProjectDatas(businessType, projectId);
				}
			}
			this.removeTasksByRunObject(pr);
			/*if(pr.getPiId()!=null){//删除任务相关信息
				this.removeTasksByPiId(pr.getPiId());
			}
			
			processRunDao.remove(runId);//最后删除process_run表数据
*/			
			return 1;
		}catch(Exception e){
			logger.error("删除项目、任务及相关数据出错："+e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * 删除项目或删除对应子流程
	 * @param ProcessRun
	 * add by lu 2013.05.14
	 */
	public Integer removeTasksByRunObject(ProcessRun run){
		try{
			List<RunData> list=runDataDao.getByRunId(run.getRunId());
			if(list!=null&&list.size()!=0){
				for(RunData runData:list){
					runDataDao.remove(runData);//删除run_data表数据
				}
			}
			
			List flist = processFormDao.getByRunId(run.getRunId());
			if(flist!=null&&flist.size()!=0){
				for(int j=0;j<flist.size();j++){
					ProcessForm pf = (ProcessForm) flist.get(j);
					processFormDao.remove(pf);//删除process_form表数据
				}
			}
			
			List<TaskSignData> listData = taskSignDataDao.getByRunId(run.getRunId());
			if(listData!=null&&listData.size()!=0){
				for(TaskSignData signData : listData){
					taskSignDataDao.remove(signData);
				}
			}
			
			if(run.getPiId()!=null){//正常任务、并列任务、会签任务。
				List<TaskImpl> taskList = taskDao.getTaskByExecutionId(run.getPiId());
				if(taskList!=null&&taskList.size()!=0){
					for(TaskImpl task:taskList){
						//List<Task> lt = taskService.getSubTasks(task.getId());
						taskDao.remove(task);//删除jbpm4_task、jbpm4_participation表数据,会签及并列任务会自动删除子任务。
					}
				}
			}
			
			processRunDao.remove(run);//最后删除process_run表数据
			
			return 1;
		}catch(Exception e){
			logger.error("删除任务相关信息出错："+e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	
	//删除项目公共数据
	private Integer removeCommonDatas(String businessType,Long projectId,Long runId,String url){
		try{
			
			List<SlProcreditMaterials> slmList = slProcreditMaterialsDao.getByProjIdBusinessTypeKey(String.valueOf(projectId), businessType);
			if(slmList!=null&&slmList.size()!=0){
				for(SlProcreditMaterials slProcreditMaterials:slmList){
					slProcreditMaterialsDao.remove(slProcreditMaterials);//删除贷款材料sl_procredit_materials表中数据
				}
			}
			
			List<SlProcreditAssuretenet> slaList = slProcreditAssuretenetDao.getByProjId(String.valueOf(projectId),businessType);
			if(slaList!=null&&slaList.size()!=0){
				for(SlProcreditAssuretenet slProcreditAssuretenet:slaList){
					slProcreditAssuretenetDao.remove(slProcreditAssuretenet);//删除贷款准入原则sl_procredit_assuretenet表中数据
				}
			}
			
			List mortgageList = mortgageService.getByBusinessTypeProjectId(businessType, projectId);
			if(mortgageList!=null&&mortgageList.size()!=0){
				String mortIds = "";
				int t = 0;
				for(int m=0;m<mortgageList.size();m++){
					if (t++ > 0) {
						mortIds += ",";
					}
					ProcreditMortgage pm = (ProcreditMortgage) mortgageList.get(m);
					int mortgageId = pm.getId();
					mortIds += mortgageId;
				}
				mortgageService.deleteAllObjectDatas(ProcreditMortgage.class, mortIds);//删除抵质押物及子表信息cs_procredit_mortgage
			}
			
			List<SlFundIntent> sllist = slFundIntentDao.getByProjectId2(projectId, businessType);
			if(sllist!=null&&sllist.size()!=0){
				for(SlFundIntent slFundIntent:sllist){
					slFundIntentDao.remove(slFundIntent);//删除sl_fund_intent表数据。款项没有对账，所有不需要删除sl_fund_detail表的数据，或者说没有对过账则在sl_fund_detail没有数据。
				}
			}
			
			List<SlActualToCharge> actualList = slActualToChargeDao.listbyproject(projectId, businessType);
			if(actualList!=null&&actualList.size()!=0){
				for(SlActualToCharge slActualToCharge:actualList){
					slActualToChargeDao.remove(slActualToCharge);//删除收支费用表sl_actual_to_charge数据。费用没有对账，所有不需要删除sl_charge_detail表的数据，或者说没有对过账则在sl_charge_detail没有数据。
				}
			}
			
			vProcreditContractService.deleteAllContractByProjectIdAndBusinessType(url, projectId, businessType);//删除合同表及合同分类表对外担保函文件等。
			vProcreditContractService.deleteAllReportByProjectIdAndBusinessType(url, projectId, businessType);//删除调查报告附件及文件等。
			slConferenceRecordService.deleteAllConferenceRecordByProjectIdAndBusinessType(projectId, businessType);//删除会议纪要等文件。
			
			return 1;
		}catch(Exception e){
			logger.error("删除项目公共数据："+e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	
	//删除小额项目及相关数据
	private Integer removeSmallLoanProjectDatas(String businessType,Long projectId){
		try{
			List<SlRepaymentSource> repayList = slRepaymentSourceDao.findListByProjId(projectId);
			if(repayList!=null&&repayList.size()!=0){
				for(SlRepaymentSource slRepaymentSource:repayList){
					slRepaymentSourceDao.remove(slRepaymentSource);//删除sl_repayment_source表数据(第一还款来源)。只与小额项目表有关联
				}
			}
			
			SlSmallloanProject sl = slSmallloanProjectDao.get(projectId);
			if(sl!=null){
				slSmallloanProjectDao.remove(sl);//删除小额项目sl_smallloan_project表数据
			}
			return 1;
		}catch(Exception e){
			logger.error("删除小额项目及相关数据出错："+e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	
	//删除金融担保项目及相关数据
	private Integer removeGLGuaranteeloanProjectDatas(String businessType,Long projectId){
		try{
			
			List<GlBankGuaranteemoney> list = glBankGuaranteemoneyDao.getbyprojId(projectId);
			if(list!=null&&list.size()!=0){
				for(GlBankGuaranteemoney glBankGuaranteemoney:list){
					glBankGuaranteemoneyDao.remove(glBankGuaranteemoney);//删除gl_bank_guaranteemoney表数据(银行保证金)。只与担保项目有关联
				}
			}
			
			List<GlAccountRecord> listRecord = glAccountRecordDao.getbyprojectId(projectId);
			if(listRecord!=null&&listRecord.size()!=0){
				for(GlAccountRecord glAccountRecord:listRecord){
					glAccountRecordDao.remove(glAccountRecord);//删除gl_account_record表数据(银行保证金记录)。只与担保项目有关联
				}
			}
			
			//只包含保费和保证金的数据-只担保有
			List<SlActualToCharge> chargeKeyList = slActualToChargeDao.listbyproject(projectId, businessType, "'premiumMoney','earnestMoney'");
			if(chargeKeyList!=null&&chargeKeyList.size()!=0){
				for(SlActualToCharge charge:chargeKeyList){
					slActualToChargeDao.remove(charge);
				}
			}
			
			GLGuaranteeloanProject gl = glGuaranteeloanProjectDao.get(projectId);
			if(gl!=null){
				glGuaranteeloanProjectDao.remove(gl);//删除金融担保gl_guaranteeloan_project表数据
			}
			return 1;
		}catch(Exception e){
			logger.error("删除金融担保项目及相关数据出错："+e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	
	//删除资金融资项目及相关数据
	private Integer removeFlFinancingProjectDatas(String businessType,Long projectId){
		try{
		/*	List<SlMortgageFinancing> list = slMortgageFinancingDao.getListByProjectId(projectId);
			if(list!=null&&list.size()!=0){
				for(int i=0;i<list.size();i++){
					SlMortgageFinancing financing = list.get(i);
					Long mortgageId = financing.getMortId();
					mortgageService.updateIsPledged(mortgageId.intValue(), (short)0);
					slMortgageFinancingDao.remove(financing);//删除我方抵质押物sl_mortgage_financing表数据，同时更新cs_procredit_mortgage的数据为未抵押状态。
				}
			}*/
			
			FlFinancingProject fp = flFinancingProjectDao.get(projectId);
			if(fp!=null){
				flFinancingProjectDao.remove(fp);//删除融资项目fl_financing_project表数据
			}
			return 1;
		}catch(Exception e){
			logger.error("删除资金融资项目及相关数据出错："+e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	
	//更新项目等表挂起(isSuspendedProject:1)、恢复(isSuspendedProject:0)状态
	public Integer suspendProject(String businessType,Long projectId,ProcessRun processRun,String isSuspendedProject){
		if("SmallLoan".equals(businessType)){
			SlSmallloanProject slPorject = slSmallloanProjectDao.get(projectId);
			if(slPorject!=null){
				if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
					slPorject.setProjectStatus(Constants.PROJECT_STATUS_SUSPENDED);
				}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
					slPorject.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
				}
				slSmallloanProjectDao.merge(slPorject);
			}
		}else if("Guarantee".equals(businessType)){
			GLGuaranteeloanProject glPorject = glGuaranteeloanProjectDao.get(projectId);
			if(glPorject!=null){
				if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
					glPorject.setBmStatus(Constants.PROJECT_STATUS_SUSPENDED);
				}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
					glPorject.setBmStatus(Constants.PROJECT_STATUS_ACTIVITY);
				}
				glGuaranteeloanProjectDao.merge(glPorject);
			}
		}else if("Financing".equals(businessType)){
			FlFinancingProject flProject = flFinancingProjectDao.get(projectId);
			if(flProject!=null){
				if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
					flProject.setProjectStatus(Constants.PROJECT_STATUS_SUSPENDED);
				}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
					flProject.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
				}
				flFinancingProjectDao.merge(flProject);
			}
		}else if("LeaseFinance".equals(businessType)){
			FlLeaseFinanceProject flPorject = flLeaseFinanceProjectDao.get(projectId);
			if(flPorject!=null){
				if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
					flPorject.setProjectStatus(Constants.PROJECT_STATUS_SUSPENDED);
				}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
					flPorject.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
				}
				flLeaseFinanceProjectDao.merge(flPorject);
			}
		}
		
		if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
			processRun.setRunStatus(Constants.PROJECT_STATUS_SUSPENDED);
		}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
			processRun.setRunStatus(ProcessRun.RUN_STATUS_RUNNING);
		}
		
		processRunDao.merge(processRun);
		
		return 1;
	}
	
	//判断当前节点是否在某一个节点之后
	public boolean isBeforeTask(Long runId,String activityName){
		
		boolean isBeforeTask = false;
		Long taskSequence = new Long(0);//节点顺序，用户跳转判断是否是保前保中等情况。
		ProcessRun processRun = processRunDao.get(runId);
		if(processRun!=null){
			String deployId = processRun.getProDefinition().getDeployId();
			if(deployId!=null&&!"".equals(deployId)){
				ProUserAssign proUserAssign = proUserAssignDao.getByDeployIdActivityName(deployId, activityName);
				if(proUserAssign!=null){
					String taskSequenceNodeKey = proUserAssign.getTaskSequence();
					if(taskSequenceNodeKey!=null&&!"0".equals(taskSequenceNodeKey)){
						if(taskSequenceNodeKey.indexOf("_")!=-1){
							String[] proArrs = taskSequenceNodeKey.split("_");
							taskSequence = new Long(proArrs[0]);
						}else{
							logger.error("设置流程的key不符合节点(顺序_节点key)格式,如(100_flowNodeKey)的规则,请修改!流程的key为："+processRun.getProcessName()+"---节点名称为："+proUserAssign.getActivityName());
						}
					}
					//Long taskSequence = proUserAssign.getTaskSequence();
					if(taskSequence>=1000){
						isBeforeTask = true;
					}
				}
			}
		}
		return isBeforeTask;
	}
	
	/**
	 * 判断某个节点是否在保中(贷中)，是则返回true；否则返回false
	 * @param deployId
	 * @param activityName
	 * add by lu 2012.07.06
	 */
	public boolean isFlowInMiddle(String deployId,String activityName){
		
		boolean isInMiddle = false;
		ProUserAssign proUserAssign = proUserAssignDao.getByDeployIdActivityName(deployId, activityName);
		if(proUserAssign!=null&&proUserAssign.getTaskSequence()!=null&&!"0".equals(proUserAssign.getTaskSequence())){
			String taskSequenceNodeKey = proUserAssign.getTaskSequence();
			if(taskSequenceNodeKey.indexOf("_")!=-1){
				String[] proArrs = taskSequenceNodeKey.split("_");
				Long taskSequence = new Long(proArrs[0].trim());
				if(taskSequence>=2000){
					isInMiddle = true;
				}
			}else{
				logger.error("设置流程的key不符合节点(顺序_节点key)格式,如(100_flowNodeKey)的规则,请修改!deployId为："+deployId+"---节点名称为："+proUserAssign.getActivityName());
			}
		}
		return isInMiddle;
	}
	
	@Override
	public Integer updateProjectStatus(Long runId,Long projectId, String businessType) {
		ProcessRun processRun=processRunService.get(runId);
		if(processRun!=null){
			//String piId=processRun.getPiId();
			try{
				//流程结束的时候自动结束流程实例，不需要再次执行jbpmService.endProcessInstance方法。否则出错。设置process_run的piId为空即可。
				/*ProcessInstance pi= jbpmService.getProcessInstance(piId);
				if(pi!=null){
					jbpmService.endProcessInstance(piId);
				}*/
				processRun.setRunStatus(ProcessRun.RUN_STATUS_FINISHED);
				processRun.setPiId(null);
				if("SmallLoan".equals(businessType)){
					SlSmallloanProject sl=slSmallloanProjectDao.get(projectId);
					sl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
					sl.setEndDate(new Date());
					slSmallloanProjectDao.save(sl);
				}else if("Guarantee".equals(businessType)){
					GLGuaranteeloanProject gp = glGuaranteeloanProjectDao.get(projectId);
					gp.setBmStatus(Constants.PROJECT_STATUS_COMPLETE);
					gp.setEndDate(new Date());
					glGuaranteeloanProjectDao.save(gp);
				}else if("Financing".equals(businessType)){
					FlFinancingProject fl = flFinancingProjectDao.get(projectId);
					fl.setEndDate(new Date());
					fl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
					flFinancingProjectDao.save(fl);
				}
			/*	List<SlActualToCharge> slActualToCharges=new ArrayList<SlActualToCharge>();
				slActualToCharges=slActualToChargeDao.listbyproject(projectId, businessType);
				for(SlActualToCharge l:slActualToCharges){
					l.setIsCheck(Short.valueOf("3"));
					slActualToChargeDao.save(l);
				}
			 	List<SlFundIntent>  slFundIntents=new ArrayList<SlFundIntent>();
			 	slFundIntents=slFundIntentDao.getByProjectId2(projectId, businessType);
                 for(SlFundIntent s:slFundIntents){
                	 s.setIsCheck(Short.valueOf("3"));
                	 slFundIntentDao.save(s);
				}*/
			 	
				processRunService.save(processRun);
				//add by lisl 修改意见与记录中的备注
				//ProcessForm existForm = processFormService.getByTaskId(taskId);
				//String curComment = existForm.getComments()!= null?existForm.getComments() : "";
				//existForm.setComments(curComment + comments);
				//processFormService.save(existForm);
				//end 
				return 1;
			}catch(Exception e){
			    e.printStackTrace();
			    logger.error("CreditProjectServiceImpl:"+e.getMessage());
			    return 0;
			}
		 }
		return 1;
	}
	/**
	 * 针对追回的情况获取新的taskId
	 * @param taskId
	 * add by lu 2012.07.12
	 */
	public String getNewTaskId(String taskId){
		String newTaskId = "";
		
		Task newTask = taskService.getTask(taskId.toString());
		if(newTask==null){//表示为追回的情况，通过以下方法获取新的taskId
			ProcessForm pform = processFormService.getByTaskId(taskId.toString());
			if(pform!=null){
				ProcessRun run = processRunService.get(pform.getRunId());
				if(run!=null){
					List<TaskImpl> taskList = flowTaskService.getTaskByExecutionId(run.getPiId());
					if(taskList!=null&&taskList.size()!=0){
						for(int i=0;i<taskList.size();i++){
							TaskImpl taskImpl = taskList.get(i);
							if(taskImpl.getActivityName().equals(pform.getActivityName())){
								newTaskId = taskImpl.getId();
							}
						}
					}
				}
			}
		}else{
			newTaskId = "";
		}
		
		return newTaskId;
	}
	
	public ProcessForm getCommentsByTaskId(String taskId){
		ProcessForm processForm = null;
		
		Task newTask = taskService.getTask(taskId);
		if(newTask!=null){
			ProcessRun processRun = processRunDao.getByPiId(newTask.getExecutionId());
			  if(processRun!=null){
				  processForm = processFormDao.getByRunIdActivityName(processRun.getRunId(), newTask.getActivityName());
			  }
		}
		return processForm;
	}
	
	@Override
	public void saveComments(String taskId, String comments) {
		
		  ProcessForm  pf=this.processFormDao.getByTaskId(taskId);
		  if(null!=pf){
			  pf.setComments(comments);
			  this.processFormDao.merge(pf);
		  }else{//针对追回的情况保存意见与说明。
			  Task newTask = taskService.getTask(taskId);
			  if(newTask!=null){
				  ProcessRun processRun = processRunDao.getByPiId(newTask.getExecutionId());
				  if(processRun!=null){
					  ProcessForm processForm = processFormDao.getByRunIdActivityName(processRun.getRunId(), newTask.getActivityName());
					  if(processForm!=null){
						  processForm.setComments(comments);
						  processFormDao.merge(processForm);
					  }
				  }
			  }
			 /* String newTaskId = this.getNewTaskId(taskId);
			  if(newTaskId!=null&&!"".equals(newTaskId)){
				 
			  }*/
		  }
		
	}
	
	/**
	 * 提交任务到决策部分,需要对当前任务和目标任务进行对比,判断是否为打回重做。
	 * @param runId
	 * @param currentActivityName
	 * @param targetActivityName
	 * add by lu 2012.07.19
	 */
	public boolean compareTaskSequence(Long runId,String currentActivityName,String targetActivityName){
		boolean compareResult = false;
		if(targetActivityName.contains("完成")){
			compareResult = true;
		}else{
			ProcessRun processRun = processRunDao.get(runId);
			if(processRun!=null){
				String  deployId=null;
				ProDefinition proDefinition = proDefinitionDao.get(processRun.getProDefinition().getDefId());
				if(proDefinition!=null){
					if(proDefinition.getPdId().equals(processRun.getPdId())){
						deployId=proDefinition.getDeployId();
					}else{
						ProcessForm form =processFormService.getByRunIdActivityName(runId,currentActivityName);
						if(form!=null){
							deployId= jbpmService.getProcessDefinitionByTaskId(form.getTaskId()).getDeploymentId();
						}
					}
					if(deployId!=null){
						ProUserAssign currentAssign = proUserAssignDao.getByDeployIdActivityName(deployId, currentActivityName);
						ProUserAssign targetAssign = null;
						String actName = targetActivityName;
						if(targetActivityName.indexOf(",")!=-1){
							String[] getTargetActivityName = targetActivityName.split(",");
							actName = getTargetActivityName[0];
						}
						targetAssign = proUserAssignDao.getByDeployIdActivityName(deployId, actName);
						if(currentAssign!=null&&targetAssign!=null){
							String currentTaskSequence = currentAssign.getTaskSequence();
							String targetTaskSequence = targetAssign.getTaskSequence();
							
							if(currentTaskSequence.indexOf("_")!=-1&&targetTaskSequence.indexOf("_")!=-1){
								String[] current = currentTaskSequence.split("_");
								String[] target = targetTaskSequence.split("_");
								Long currentNodeNumber = new Long(current[0].trim());
								Long targetNodeNumber = new Long(target[0].trim());
								if(currentNodeNumber<targetNodeNumber){
									compareResult = true;//正常流转至下一个节点,false则打回至比当前节点更前的节点。
								}
							}else{
								logger.error("设置流程的key不符合节点(顺序_节点key)格式,如(100_flowNodeKey)的规则,请修改!deployId为："+deployId+"---节点名称为："+currentActivityName);
							}
						}
					}
					
				}
			}
		}
		return compareResult;
	}
	
	/**
	 * 获取节点顺序key对象。
	 * @param runId
	 * @param activityName
	 * add by lu 2012.07.12
	 */
	public ProUserAssign getByRunIdActivityName(Long runId,String activityName){
		
		ProUserAssign proUserAssign = null;
		ProcessRun processRun = processRunDao.get(runId);
		if(processRun!=null){
			ProDefinition proDefinition = proDefinitionDao.get(processRun.getProDefinition().getDefId());
			if(proDefinition!=null){
				proUserAssign = proUserAssignDao.getByDeployIdActivityName(proDefinition.getDeployId(), activityName);
			}
		}
		return proUserAssign;
	}
	
	/**
	 * 获取业务类别、业务品种、自定义类型数据字典的值。不同的分级获取的对象不一样，而项目表中只存在业务类别、业务品种的值。
	 * @param typeKey
	 * @param projectId
	 * @param businessType
	 * add by lu 2013.05.14
	 */
	public String getGlobalTypeValue(String typeKey,String businessType,Long projectId){
		String typeName = "";
		try{
			ProcessRun processRun = processRunDao.getByBusinessTypeProjectId(projectId, businessType);
			if(processRun!=null){
				Long proTypeId = processRun.getProDefinition().getProTypeId();
				GlobalType global = globalTypeDao.get(proTypeId);
				if(global!=null&&global.getNodeKey()!=null&&global.getNodeKey().indexOf("_")!=-1){
					String[] proArrs = global.getNodeKey().split("_");
					if(proArrs.length==3){//Guarantee_definitionType_CompanyBusiness无论多少级,获取的都是第一个和最后一个属性值。而且必定是一级目录以上。
						if("businessType".equals(typeKey)){
							typeName = this.getGlobalTypeName(proArrs[0]);
						}else if("operationType".equals(typeKey)){
							typeName = this.getGlobalTypeName(proArrs[0]+"_"+proArrs[1]);
						}else if("definitionType".equals(typeKey)){
							typeName = global.getTypeName();
						}else if("leasingType".equals(typeKey)){
							typeName = global.getTypeName();
						}
					}else if(proArrs.length==2){//Guarantee_CompanyBusiness(两级或三级的情况)
						GlobalType gt = globalTypeDao.get(global.getParentId());
						if("businessType".equals(typeKey)){
							if(gt!=null&&!"0".equals(gt.getParentId().toString())){
								GlobalType parentGlobal = globalTypeDao.get(gt.getParentId());
								if(parentGlobal!=null&&!"0".equals(parentGlobal.getParentId().toString())){
									GlobalType pglobal = globalTypeDao.get(parentGlobal.getParentId());
									if(pglobal!=null){
										typeName = pglobal.getTypeName();
									}
								}else{
									typeName = gt.getTypeName();
								}
							}else{
								typeName = global.getTypeName();
							}
						}else if("operationType".equals(typeKey)){//如果是只有两级：业务类别和流程类别，该值就已经不需要。如果是三级则需要。
							typeName = global.getTypeName();
						}else if("leasingType".equals(typeKey)){//如果是只有两级：业务类别和流程类别，该值就已经不需要。如果是三级则需要。
							typeName = global.getTypeName();
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getGlobalTypeValue:"+e.getMessage());
		}
		return typeName;
	}
	
	private String getGlobalTypeName(String nodeKey){
		String typeName = "";
		List<GlobalType> list = globalTypeDao.getByNodeKey(nodeKey);
		if(list!=null&&list.size()!=0){
			typeName = list.get(0).getTypeName();
		}
		return typeName;
	}
	
	/**
	 * 当前任务提交下一个节点为同步或者分支(包含打回、终止、同步)的情况获取同步节点的key
	 * @param taskId
	 * @param nextActivityName
	 * @return
	 * add by lu 2013.06.13
	 */
	public String getSynchronizeNodeKey(Long taskId,String nextActivityName){
		String synchronizeNodeKey = "";
		try{
			ProcessForm processForm = processFormService.getByTaskId(taskId.toString());
			if(processForm!=null){
				ProUserAssign proUserAssign = this.getByRunIdActivityName(processForm.getRunId(), nextActivityName);
				if(proUserAssign!=null&&proUserAssign.getTaskSequence().indexOf("_")!=-1){
					String[] proArrs = proUserAssign.getTaskSequence().split("_");
					if(proArrs.length>2){//集团分公司的情况,如：10_beijing_flowNodeKeyName
						synchronizeNodeKey = proArrs[2];
					}else{
						synchronizeNodeKey = proArrs[1];//非集团分公司的情况,如：10_flowNodeKeyName
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取同步任务节点的key出错："+e.getMessage());
		}
		return synchronizeNodeKey;
	}
	
	/**
	 * 子流程终止的时候只改变流程相关数据的状态，而不改变对应项目的状态。
	 * @param runId
	 * @return
	 * add by lu 2013.06.04
	 */
	public Integer updateFlowStatus(Long runId){
		ProcessRun processRun = processRunService.get(runId);
		AppUser user = ContextUtil.getCurrentUser();
		if(processRun!=null){
			VCommonProjectFlow common = vCommonProjectFlowDao.getByRunId(runId);
			if(common!=null&&common.getTaskId()!=null){
				ProcessForm pf = processFormDao.getByTaskId(common.getTaskId());
				if(pf!=null){
					this.updateComments(pf, user.getFullname(),"任务("+pf.getActivityName()+")中选择了");
				}
			}
			processRun.setRunStatus(ProcessRun.RUN_STATUS_STOP);
		}
		return 1;
	}
	
	@Override
	public Integer startFormalitiesFlow(HttpServletRequest request){
		try {
			Long projectId = Long.valueOf(request.getParameter("platFormBpFundProject.projectId"));
			BpFundProject oldProject =bpFundProjectDao.getByProjectId(projectId, Short.valueOf("1"));
			BpFundProject project=new BpFundProject();
			BeanUtil.populateEntity(request, project, "platFormBpFundProject");
			BeanUtil.copyNotNullProperties(oldProject,project);
			
			SlSmallloanProject oldSlSmallloanProject = slSmallloanProjectDao.get(projectId);
			SlSmallloanProject smproject=new SlSmallloanProject();
			BeanUtil.populateEntity(request, smproject, "slSmallloanProject");
			BeanUtil.copyNotNullProperties(oldSlSmallloanProject,smproject);
			oldSlSmallloanProject.setProjectStatus(Constants.PROJECT_POSTPONED_STATUS_ACT);
			slSmallloanProjectDao.merge(oldSlSmallloanProject);
			
			Double projectMoney = Double.valueOf(request.getParameter("platFormBpFundProject.platFormJointMoney"));
			String investInfo=request.getParameter("investData1");
			String slFundIntentDatas=request.getParameter("slFundIntentData1");
			List<InvestPersonInfo> investList=new ArrayList<InvestPersonInfo>();
			String investIds="";
	    	if(investInfo!=null && !"".equals(investInfo)) {
	    		 String[] investArr = investInfo.split("@");
	    		 for(int i=0; i<investArr.length; i++) {
	    			String str = investArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try{
						InvestPersonInfo investPersonInfo = (InvestPersonInfo)JSONMapper.toJava(parser.nextValue(),InvestPersonInfo.class);
						double investMoney = investPersonInfo.getInvestMoney().doubleValue();
						double percent = 100*investMoney/projectMoney;
						investPersonInfo.setInvestPercent(new BigDecimal(percent));
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
					   investPersonInfo.setProjectId(projectId);
					   investPersonInfo.setBusinessType("SmallLoan");
					   investPersonInfoDao.save(investPersonInfo);
					   investIds += investPersonInfo.getInvestId()+",";
				   }else{
					   InvestPersonInfo ps=this.investPersonInfoDao.get(investPersonInfo.getInvestId());
					   BeanUtil.copyNotNullProperties(ps, investPersonInfo);
					   investPersonInfoDao.save(ps);
					   investIds += investPersonInfo.getInvestId()+",";
				   }
				}
	   		}
	    	oldProject.setInvestorIds(investIds.substring(0, investIds.length()-1));
	    	bpFundProjectDao.merge(oldProject);
	    	
	    	List<SlFundIntent> list=slFundIntentDao.getByProjectId2(oldProject.getProjectId(), "SmallLoan");
			for(SlFundIntent s:list){
				if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
					slFundIntentDao.remove(s);
				}
			}
			if(slFundIntentDatas!=null && !"".equals(slFundIntentDatas)) {
	    		 String[] slFundIntentArr = slFundIntentDatas.split("@");
	    		 for(int i=0; i<slFundIntentArr.length; i++) {
	    			String str = slFundIntentArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try{
						SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
						if(null==slFundIntent.getFundIntentId() || "".equals(slFundIntent.getFundIntentId())){
							slFundIntent.setProjectId(oldProject.getProjectId());
							slFundIntent.setProjectName(oldProject.getProjectName());
							slFundIntent.setProjectNumber(oldProject.getProjectNumber());
							slFundIntent.setCompanyId(oldProject.getCompanyId());
							slFundIntent.setBusinessType("SmallLoan");
							slFundIntent.setAfterMoney(new BigDecimal(0));
							slFundIntent.setAccrualMoney(new BigDecimal(0));
							slFundIntent.setFlatMoney(new BigDecimal(0));
							if(slFundIntent.getPayMoney().compareTo(new BigDecimal(0))==0){
								slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
							}else{
								slFundIntent.setNotMoney(slFundIntent.getPayMoney());
							}
							slFundIntent.setIsValid(Short.valueOf("0"));
							slFundIntent.setIsCheck(Short.valueOf("0"));
							slFundIntentDao.save(slFundIntent);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
	    		 }
			}
			suspendSubmitCurrentTask(Long.valueOf(request.getParameter("runId")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public Integer startGoThroughFormalitiesFlow(HttpServletRequest request){
		try {
			Long projectId = Long.valueOf(request.getParameter("slSmallloanProject.projectId"));
			SlSmallloanProject oldSlSmallloanProject = slSmallloanProjectDao.get(projectId);
			SlSmallloanProject project=new SlSmallloanProject();
			BeanUtil.populateEntity(request, project, "slSmallloanProject");
			BeanUtil.copyNotNullProperties(oldSlSmallloanProject,project);
			oldSlSmallloanProject.setProjectStatus(Constants.PROJECT_POSTPONED_STATUS_ACT);
			
			Double projectMoney = Double.valueOf(request.getParameter("slSmallloanProject.projectMoney"));
			String investInfo=request.getParameter("investData1");
			String slFundIntentDatas=request.getParameter("slFundIntentData1");
			List<InvestPersonInfo> investList=new ArrayList<InvestPersonInfo>();
			String investIds="";
	    	if(investInfo!=null && !"".equals(investInfo)) {
	    		 String[] investArr = investInfo.split("@");
	    		 for(int i=0; i<investArr.length; i++) {
	    			String str = investArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try{
						InvestPersonInfo investPersonInfo = (InvestPersonInfo)JSONMapper.toJava(parser.nextValue(),InvestPersonInfo.class);
						double investMoney = investPersonInfo.getInvestMoney().doubleValue();
						double percent = 100*investMoney/projectMoney;
						investPersonInfo.setInvestPercent(new BigDecimal(percent));
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
					   investPersonInfo.setProjectId(projectId);
					   investPersonInfo.setBusinessType("SmallLoan");
					   investPersonInfoDao.save(investPersonInfo);
					   investIds += investPersonInfo.getInvestId()+",";
				   }else{
					   InvestPersonInfo ps=this.investPersonInfoDao.get(investPersonInfo.getInvestId());
					   BeanUtil.copyNotNullProperties(ps, investPersonInfo);
					   investPersonInfoDao.save(ps);
					   investIds += investPersonInfo.getInvestId()+",";
				   }
				}
	   		}
	    	oldSlSmallloanProject.setInvestorIds(investIds.substring(0, investIds.length()-1));
	    	slSmallloanProjectDao.merge(oldSlSmallloanProject);

	    	List<SlFundIntent> list=slFundIntentDao.getByProjectId2(oldSlSmallloanProject.getProjectId(), "SmallLoan");
			for(SlFundIntent s:list){
				if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
					slFundIntentDao.remove(s);
				}
			}
			if(slFundIntentDatas!=null && !"".equals(slFundIntentDatas)) {
	    		 String[] slFundIntentArr = slFundIntentDatas.split("@");
	    		 for(int i=0; i<slFundIntentArr.length; i++) {
	    			String str = slFundIntentArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try{
						SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
						if(null==slFundIntent.getFundIntentId() || "".equals(slFundIntent.getFundIntentId())){
							slFundIntent.setProjectId(oldSlSmallloanProject.getProjectId());
							slFundIntent.setProjectName(oldSlSmallloanProject.getProjectName());
							slFundIntent.setProjectNumber(oldSlSmallloanProject.getProjectNumber());
							slFundIntent.setCompanyId(oldSlSmallloanProject.getCompanyId());
							slFundIntent.setBusinessType("SmallLoan");
							slFundIntent.setAfterMoney(new BigDecimal(0));
							slFundIntent.setAccrualMoney(new BigDecimal(0));
							slFundIntent.setFlatMoney(new BigDecimal(0));
							if(slFundIntent.getPayMoney().compareTo(new BigDecimal(0))==0){
								slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
							}else{
								slFundIntent.setNotMoney(slFundIntent.getPayMoney());
							}
							slFundIntent.setIsValid(Short.valueOf("0"));
							slFundIntent.setIsCheck(Short.valueOf("0"));
							slFundIntentDao.save(slFundIntent);
						}
					}catch(Exception e){
						e.printStackTrace();
					}
	    		 }
			}
			suspendSubmitCurrentTask(Long.valueOf(request.getParameter("runId")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 恢复并且提交当前任务
	 * @param runId
	 * @return
	 * desc 资金匹配成功后执行
	 * add by luqh 2013.05.03
	 */
	public Integer suspendSubmitCurrentTask(Long runId){
		try{
			ProcessRun run = processRunDao.get(runId);
			if(run!=null&&run.getPiId()!=null){
				//add by zcb
				String state="suspended";
				List<TaskImpl> task = flowTaskService.getTaskByExecutionId(run.getPiId(),state);
				//end
				this.suspendProject(run.getProjectId(), run, "0", true);//先恢复其挂起的任务和修改相应数据的状态。
				//当前情况只存在"资金匹配"的单个任务，还是需要判断是否存在多个任务的情况并完成相关任务产生下一个任务。
//				List<TaskImpl> task = flowTaskService.getTaskByExecutionId(run.getPiId());
				if(task!=null&&task.size()!=0){
					for(TaskImpl taskImpl : task){
						ProUserAssign userAssign = this.getByRunIdActivityName(run.getRunId(), taskImpl.getActivityName());
						if(userAssign!=null&&userAssign.getTaskSequence()!=null&&!"".equals(userAssign.getTaskSequence())){
							if(userAssign.getTaskSequence().contains("sszzFundMatching")){//资金匹配
								//ProUserAssign pro = proUserAssignDao.getByDeployIdFlowNodeKey(run.getProDefinition().getDeployId(), "sszzContractAndGuaranteeStep");//合同签署
								FlowRunInfo flowRunInfo = new FlowRunInfo();
								List<Transition> trans=jbpmService.getTransitionsByTaskId(taskImpl.getId(),true);
								if(trans!=null&&trans.size()!=0){
									Map busMap = new HashMap();
									busMap.put("isSubmitCurrentTask", "true");
									String transitionName = trans.get(0).getName();
									flowRunInfo.setTransitionName(transitionName);
									flowRunInfo.setDestName(null);
									flowRunInfo.setTaskId(taskImpl.getId());
									flowRunInfo.setPiId(run.getPiId());
									flowRunInfo.setActivityName(taskImpl.getActivityName());
									flowRunInfo.setBusMap(busMap);
									jbpmService.doNextStep(flowRunInfo);
								}
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("恢复并且提交当前任务出错："+e.getMessage());
			return 0;
		}
		return 1;
    }
	
	//更新项目等表挂起(isSuspendedProject:1)、恢复(isSuspendedProject:0)状态
	public Integer suspendProject(Long projectId,ProcessRun processRun,String isSuspendedProject,boolean isChangeProjectStatus){
		if(!isChangeProjectStatus){
			if(processRun.getBusinessType()!=null&&!"".equals(processRun.getBusinessType())){
				if("SmallLoan".equals(processRun.getBusinessType())){
					SlSmallloanProject slPorject = slSmallloanProjectDao.get(projectId);
					if(slPorject!=null){
						if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
							//slPorject.setProjectStatus(Constants.PROJECT_STATUS_SUSPENDED);
							slPorject.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
						}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
							slPorject.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
						}
						slSmallloanProjectDao.merge(slPorject);
					}
				}else if("Guarantee".equals(processRun.getBusinessType())){
					GLGuaranteeloanProject glPorject = glGuaranteeloanProjectDao.get(projectId);
					if(glPorject!=null){
						if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
							glPorject.setBmStatus(Constants.PROJECT_STATUS_SUSPENDED);
						}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
							glPorject.setBmStatus(Constants.PROJECT_STATUS_ACTIVITY);
						}
						glGuaranteeloanProjectDao.merge(glPorject);
					}
				}else if("Financing".equals(processRun.getBusinessType())){
					FlFinancingProject flProject = flFinancingProjectDao.get(projectId);
					if(flProject!=null){
						if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
							flProject.setProjectStatus(Constants.PROJECT_STATUS_SUSPENDED);
						}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
							flProject.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
						}
						flFinancingProjectDao.merge(flProject);
					}
				}
			}
		}
		
		
		List<TaskImpl> task = flowTaskService.getTaskByExecutionId(processRun.getPiId());
		if(task!=null&&task.size()>1){//存在并列任务或者并列任务
			for(int k=0;k<task.size();k++){
				TaskImpl taskImpl = task.get(k);
				if(isSuspendedProject!=null&&"1".equals(isSuspendedProject) && "资金匹配".equals(taskImpl.getActivityName())){
					taskImpl.setState(Task.STATE_SUSPENDED);
				}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject) && "资金匹配".equals(taskImpl.getActivityName())){
					taskImpl.setState(Task.STATE_OPEN);
				}
				flowTaskService.merge(taskImpl);
			}
		}
		return 1;
	}
	
	private Integer suspendTasks(String piId,String isSuspendedProject){
		try{
			List<TaskImpl> task = flowTaskService.getTaskByExecutionId(piId);
			//大多时候都只存在一个任务,此时只更新一条记录即可,而非每次都进行多次循环判断执行。
			if(task!=null&&task.size()==1){//单个任务或者会签任务
				String dbId = task.get(0).getId();
				List<Task> taskList = taskService.getSubTasks(dbId);
				if(taskList!=null&&taskList.size()!=0){//会签任务
					for(int i=0;i<taskList.size();i++){
						TaskImpl subTask = (TaskImpl)taskList.get(i);
						if(subTask!=null){
							if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
								subTask.setState(Task.STATE_SUSPENDED);
							}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
								subTask.setState(Task.STATE_OPEN);
		    				}
							flowTaskService.merge(subTask);
						}
					}
				}else{//单个任务
					TaskImpl t = task.get(0);
					if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
						t.setState(Task.STATE_SUSPENDED);
					}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
						t.setState(Task.STATE_OPEN);
					}
					flowTaskService.merge(t);
				}
			}else if(task!=null&&task.size()>1){//存在并列任务或者并列任务、会签任务一起存在
				for(int k=0;k<task.size();k++){
					TaskImpl taskImpl = task.get(k);
					if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
						taskImpl.setState(Task.STATE_SUSPENDED);
					}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
						taskImpl.setState(Task.STATE_OPEN);
					}
					flowTaskService.merge(taskImpl);
					List<Task> taskList = taskService.getSubTasks(taskImpl.getId());
					if(taskList!=null&&taskList.size()!=0){
						for(int t=0;t<taskList.size();t++){
							TaskImpl ta = (TaskImpl)taskList.get(t);
							if(isSuspendedProject!=null&&"1".equals(isSuspendedProject)){
		    					ta.setState(Task.STATE_SUSPENDED);
		    				}else if(isSuspendedProject!=null&&"0".equals(isSuspendedProject)){
		    					ta.setState(Task.STATE_OPEN);
		    				}
							flowTaskService.merge(ta);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 投资客户启动取现流程，不论线上线下
	 * @param obAccountDealInfo
	 * @return
	 */
	@Override
	public String startEnchashmentFlow(ObAccountDealInfo obAccountDealInfo) {
		// TODO Auto-generated method stub
		String msg="";
		try{
			if(obAccountDealInfo!=null){
				FlowRunInfo newFlowRunInfo = new FlowRunInfo();
				//CsInvestmentperson   investPerson =csInvestmentpersonDao.get(obAccountDealInfo.getInvestPersonId());
				ProDefinition pdf = null;
				obAccountDealInfoDao.save(obAccountDealInfo);
				String isGroupVersion = AppUtil.getSystemIsGroupVersion();
				if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
					pdf = proDefinitionDao.getByProcessName("enchashmentFlow");
				} else {
					pdf = proDefinitionDao.getByProcessName("enchashmentFlow");
				}
				Long branchCompanyId = pdf.getBranchCompanyId();
				Map<String, Object> newVars = new HashMap<String, Object>();
				newVars.put("investmentId", obAccountDealInfo.getInvestPersonId());
				newVars.put("projectId", obAccountDealInfo.getAccountId());
				newVars.put("obAccountDealInfoId", obAccountDealInfo.getId());
				newVars.put("businessType", "EnchashmentBusiness");
				newVars.put("customerType", obAccountDealInfo.getInvestPersonType());
				
				newVars.put("customerName", obAccountDealInfo.getInvestPersonName()); //

				newVars.put("investmentPersonId", obAccountDealInfo.getInvestPersonId()); //
				newVars.put("projectNumber", "1111"); //
				newVars.put("branchCompanyId", obAccountDealInfo.getCompanyId());
				
				newFlowRunInfo.getVariables().putAll(newVars);
				newFlowRunInfo.setBusMap(newVars);
				newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
				ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
				if (run != null && run.getPiId() != null) {
					if(obAccountDealInfo.getInvestPersonType().compareTo(ObSystemAccount.type0)==0){
						msg="taskId:12,msg:'流程已经启动'";
					}else{
						msg = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),ContextUtil.getCurrentUserId().toString(), run.getSubject());
					}
					obAccountDealInfo.setDealRecordStatus(ObAccountDealInfo.DEAL_STATUS_7);
					obAccountDealInfo.setRecordStatus(ObAccountDealInfo.DEAL_STATUS_1);
					obAccountDealInfo.setRunId(run.getRunId());
					obAccountDealInfoDao.merge(obAccountDealInfo);
					ObSystemAccount ob = this.obSystemAccountDao.get(obAccountDealInfo.getAccountId());// 系统虚拟银行信息
					ob.setAccountStatus((short) 1);// 1投资客户已经启动流程，0流程未启动
					this.obSystemAccountDao.merge(ob);
				}else{
					obAccountDealInfoDao.remove(obAccountDealInfo);
					msg="taskId:0,msg:'流程启动出错，请联系管理员'";
				}
			}else{
				msg="taskId:0,msg:'没有获取取现信息，请检查'";
			}
		}catch(Exception e){
			e.printStackTrace();
			msg="taskId:0,msg:'系统出错了，请联系管理员'";
		}
		return msg;
	}
	
	
	@Override
	public Integer ApprovalTaskFlow(FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
		String transitionName = flowRunInfo.getTransitionName();
		try {
			int backvalue=1;
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("project_id");
				ObAccountDealInfo persistent  = this.obAccountDealInfoDao.get(Long.valueOf(projectId));
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")|| transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						persistent.setDealRecordStatus(Short.valueOf("3"));
						this.obAccountDealInfoDao.merge(persistent);
					} else {
						ProcessForm currentForm = processFormService.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								 if(transitionName.contains("客户取现复审")){
//									persistent.setDealRecordStatus(Short.valueOf("4"));
									persistent.setRecordStatus(ObAccountDealInfo.DEAL_STATUS_4);
									this.obAccountDealInfoDao.merge(persistent);
								}else if(transitionName.contains("完成")){
									//审批流程走完了  可以进行取现办理了:状态为5
//									persistent.setDealRecordStatus(Short.valueOf("5"));
									persistent.setRecordStatus(ObAccountDealInfo.DEAL_STATUS_5);
									this.obAccountDealInfoDao.merge(persistent);
								}
							}
						}
					}
					vars.put("decisionResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return backvalue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("取现流程-"+transitionName+"提交下一步出错：" + e.getMessage());
			return 0;
		}
	
	
	}
	/**
     * P2P项目启动流程方法（标准个贷，企贷，以及车贷启动方法）
     * Add by linyan  2014-9-16
     * @param flowRunInfo
     * @return
     */
	@Override
	public Integer startCreditP2PProjectFlow(FlowRunInfo flowRunInfo) {
		Map<String, String> busMap = new HashMap<String, String>();
	    String operationType=flowRunInfo.getRequest().getParameter("operationType");//业务品种
	    String history=flowRunInfo.getRequest().getParameter("history");
	    String operationTypeKey = "";//业务品种的key值(CompanyBusiness,PersonalConsumeBusiness,FinancingBusiness等)
		try{
			busMap = this.getOperationType(operationType);
			String[] proArrs = operationType.split("_");
			if(proArrs.length>1){//Guarantee_definitionType_CompanyBusiness无论多少级,获取的都是第一个和最后一个属性值。而且必定是一级目录以上。
				operationTypeKey = proArrs[proArrs.length-1];
			}
			if(operationTypeKey!=null&&!"".equals(operationTypeKey)){
				flowRunInfo.setBusMap(busMap);
				if(operationTypeKey.contains("SmallLoanBusiness")){//小额贷款-企贷启动方法
					if(null!=history && history.equals("entSmall")){
						return this.startEnterpriseSmallFlow(flowRunInfo);
					}else{
						return this.startCreditFlowEnterpriseLoanNormalFlow(flowRunInfo);
					}
		 		}else if(operationTypeKey.contains("PersonalCreditLoanBusiness")){//小贷贷款-个贷启动方法
		 			if(null!=history && history.equals("personSmall")){
		 				return this.startPersonSmallFlow(flowRunInfo);
					}else{
						return this.startCreditFlowPersonLoanNormalFlow(flowRunInfo);
					}
		 		}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("CreditProjectServiceImpl:"+e.getMessage());
			return 0;
		}
		return 1;
	}
	//标准企贷启动方法
	public Integer startCreditFlowEnterpriseLoanNormalFlow(
			FlowRunInfo flowRunInfo) {
		try{
			 logger.info("启动企贷标准流程开始时间："+new Date());
			 String projectNumber = ""; //项目编号
			 String projectName="";//项目名称
			 String branchNO = "";
			 AppUser user= ContextUtil.getCurrentUser();
		     Long companyId = ContextUtil.getLoginCompanyId();
			 String currentUserId = ContextUtil.getCurrentUserId().toString();
			 String businessTypeKey = flowRunInfo.getBusMap().get("businessTypeKey").toString();
			 String operationTypeKey = flowRunInfo.getBusMap().get("operationTypeKey").toString();
			 String history=flowRunInfo.getRequest().getParameter("history");
			 
			 String loanId=flowRunInfo.getRequest().getParameter("loanId");//线上申请记录id
			 
			 //从配置文件获取个贷流程key
			 Object  flowType =configMap.get("flowEnterType");
			 if(flowType!=null){
				 String flowTypeKey=flowType.toString();
				 if(null!=history && history.equals("dir")){
					 flowTypeKey="EntCreditDirFastFlow";//直投标快速补录
				 }else if(null!=history && history.equals("or")){
					 flowTypeKey="EntCreditOrFastFlow";//债权转让快速补录
				 }else if(null!=history && history.equals("mmproduceOr")){
					 flowTypeKey="EntCreditOrFastMMFlow";//企业债权补录-理财产品与计划
				 }else if(null!=history && history.equals("mmplanOr")){
					 flowTypeKey="EntCreditOrFastMMFlow";//个人债权补录-理财产品与计划
				 }
				 String strDdate = DateUtil.getNowDateTime("yyMMdd");
				 ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);//流程key
				 String flowId = String.valueOf(proDefinition.getDefId());
				 Long branchCompanyId = proDefinition.getBranchCompanyId();
				 SlSmallloanProject project = new SlSmallloanProject(); //初始化项目信息
				 BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "slSmallloanProject");
				 
				 //保存客户信息开始 ==========================================================================================================
				 Person person = new Person();   //初始化person
				 Enterprise  enterprise = new Enterprise();  //初始化enterprise
				 if(flowRunInfo.getRequest().getParameter("person.id") !=null && !"".equals(flowRunInfo.getRequest().getParameter("person.id"))){
					 Integer id=Integer.valueOf(flowRunInfo.getRequest().getParameter("person.id"));
					 person=personDao.getById(id);
				 }
				 if(flowRunInfo.getRequest().getParameter("enterprise.id") !=null && !"".equals(flowRunInfo.getRequest().getParameter("enterprise.id"))){
					 Integer id=Integer.valueOf(flowRunInfo.getRequest().getParameter("enterprise.id"));
					 enterprise=enterpriseDao.getById(id);
				 }
				 BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
				 BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
				 if(null!=project.getDepartId() && null==enterprise.getId()){
					 person.setShopId(project.getDepartId());
					 person.setShopName(project.getDepartMentName());
					 enterprise.setShopId(project.getDepartId());
					 enterprise.setShopName(project.getDepartMentName());
				 }
				 String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");//股东信息
				 Enterprise oEnterprise= enterpriseDao.saveSingleEnterprise(enterprise,person,gudongInfo);
				 
				 //保存客户信息结束 ==========================================================================================================
				 
				 //生成项目名开始 （客户名+年月+个贷项目+流程名称）=============================================================================
				 SimpleDateFormat formart=new SimpleDateFormat("yyyy",Locale.CHINA);
				 SimpleDateFormat formart1=new SimpleDateFormat("MM",Locale.CHINA);
				 Calendar calendar=Calendar.getInstance(Locale.CHINA);
				 String yearStr=formart.format(calendar.getTime());
				 String monthStr=formart1.format(calendar.getTime());
				 String yearAndMonthStr=yearStr+"年"+monthStr+"月";       //年月字符串
				 String cusName=oEnterprise.getEnterprisename();//客户名称
				 String flowTypeName =proDefinition.getName();
				 projectName=cusName+yearAndMonthStr+"企贷项目"+"("+flowTypeName+")";
				 logger.info("启动个贷标准流程生成项目名称："+projectName);
				 
				//生成项目名结束 （客户名+年月+个贷项目+流程名称）=============================================================================
				
				//生成项目编号开始 (组织机构编码_yyMMdd_01)(当天的第一笔)============================================
				if(null!=companyId){
					Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
					branchNO = org.getBranchNO();
				}else{
					Organization organization = organizationDao.getGroupCompany();
					if(organization!=null){
						branchNO = organization.getBranchNO();
					}
				}
				SlSmallloanProject slproject = slSmallloanProjectDao.getProjectNumber(branchNO+"_"+strDdate);
				if(slproject!=null){
					String number = "";
					String proNum = slproject.getProjectNumber();
					String[] proArrs = proNum.split("_");
					int num = new Integer(proArrs[2]);
					num+=1;
					if(num<10){
						number = "00"+num;
					}else if(num>=10&&num<100){
						number = "0"+num;
					}else{
						number = String.valueOf(num);
					}
					projectNumber = branchNO+"_"+strDdate+"_"+number;
				}else{
					projectNumber = branchNO+"_"+strDdate+"_"+"001";
				}
				logger.info("启动个贷标准流程生成项目编号："+projectNumber);
				//生成项目编号结束   (组织机构编码_yyMMdd_01)(当天的第一笔)============================================
				
				//债权类型
				if(null!=history && history.equals("mmproduceOr")){
					project.setChildType("mmproduceOr");
				}else if(null!=history && history.equals("mmplanOr")){
					project.setChildType("mmplanOr");
				}
				
				project.setBusinessType(businessTypeKey);
				project.setOperationType(operationTypeKey);
				project.setFlowType(flowTypeKey);
				project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
				project.setFlowType(flowTypeKey);
				project.setCreateDate(new Date());
				project.setProjectProperties("common");
				project.setMineId(1L);//我方主体必填项  默认为1
				project.setMineType("company_ourmain");
				if(ContextUtil.getLoginCompanyId()!=null){
			    	project.setCompanyId(ContextUtil.getLoginCompanyId());
			    }else{
			    	project.setCompanyId(Long.valueOf("1"));
			    }
			    project.setOppositeID(oEnterprise.getId().longValue());
				project.setOppositeType("company_customer");
				project.setProjectName(projectName);
				project.setProjectNumber(projectNumber);
				if(project.getAppUserId()!=null&&!"".equals(project.getAppUserId())){
					String userId = project.getAppUserId().split(",")[0];
					AppUser user1 = appUserService.get(Long.valueOf(userId));
					project.setDepartId(user1.getDepartment().getDepId());
				}
				slSmallloanProjectDao.save(project);
				//根据产品来初始化项目基本信息开始===================================================================
				if(null!=loanId && !"".equals(loanId) && null!=project.getProductId()){//线上申请流程
					BpFinanceApplyUser applyUser=bpFinanceApplyUserDao.get(Long.valueOf(loanId));//申请记录
					P2pLoanProduct product=p2pLoanProductDao.get(project.getProductId());//产品
					QueryFilter filter=new QueryFilter();
					filter.addFilter("Q_productId_L_EQ",project.getProductId());//产品id
					filter.addFilter("Q_loanTime_L_EQ",applyUser.getLoanTimeLen());//贷款期限
					List<P2pLoanRate> rateList=p2pLoanRateDao.getAll(filter);
					if(null!=product){
						//贷款金额、计息方式、还款周期、   贷款利率、管理咨询费率、财务服务费率
						project.setProjectMoney(applyUser.getLoanMoney());//借款金额
						if(null!=applyUser.getPayIntersetWay()){
							project.setAccrualtype(applyUser.getPayIntersetWay());//还款方式
						}
						project.setPayaccrualType(product.getPayaccrualType());//还款周期
						project.setPayintentPeriod(applyUser.getLoanTimeLen());//贷款期限
						project.setIsPreposePayAccrual(product.getIsPreposePayAccrual());//前置付息
						project.setIsInterestByOneTime(product.getIsInterestByOneTime());//是否一次性支付利息
						if(null!=rateList && rateList.size()>0){
							project.setYearAccrualRate(rateList.get(0).getYearAccrualRate());//年化利率
							project.setAccrual(project.getYearAccrualRate().divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));//月化利率
							project.setDayAccrualRate(project.getYearAccrualRate().divide(new BigDecimal(360),10,BigDecimal.ROUND_HALF_EVEN));//日化利率
							
							project.setYearFinanceServiceOfRate(rateList.get(0).getYearFinanceServiceOfRate());
							project.setFinanceServiceOfRate(project.getYearFinanceServiceOfRate().divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));
							project.setDayFinanceServiceOfRate(project.getYearFinanceServiceOfRate().divide(new BigDecimal(360),10,BigDecimal.ROUND_HALF_EVEN));
						
							project.setYearManagementConsultingOfRate(rateList.get(0).getYearManagementConsultingOfRate());
							project.setManagementConsultingOfRate(project.getYearManagementConsultingOfRate().divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));
							project.setDayManagementConsultingOfRate(project.getYearManagementConsultingOfRate().divide(new BigDecimal(360),10,BigDecimal.ROUND_HALF_EVEN));
						}
						slSmallloanProjectDao.merge(project);
					}
					
					applyUser.setState("5");//已启动借款项目
					bpFinanceApplyUserDao.merge(applyUser);
				}else if(project.getProductId()!=null){
					BpProductParameter bpProductParameter=bpProductParameterDao.get(project.getProductId());
					if(null!=bpProductParameter){
						project.setAccrualtype(bpProductParameter.getAccrualtype());//还款方式
						project.setPayaccrualType(bpProductParameter.getPayaccrualType());//还款周期
						project.setPayintentPeriod(bpProductParameter.getPayintentPeriod());//贷款期限
						project.setIsPreposePayAccrual(bpProductParameter.getIsPreposePayAccrual());//前置付息
						project.setIsInterestByOneTime(bpProductParameter.getIsInterestByOneTime());//是否一次性支付利息
						project.setIsStartDatePay(bpProductParameter.getIsStartDatePay());//每期还款日
						project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());//固定在
						project.setYearAccrualRate(bpProductParameter.getYearAccrualRate());//年化利率
						project.setAccrual(bpProductParameter.getAccrual());//月化利率
						project.setDayAccrualRate(bpProductParameter.getDayAccrualRate());//日化利率
						project.setYearFinanceServiceOfRate(bpProductParameter.getYearFinanceServiceOfRate());
						project.setYearManagementConsultingOfRate(bpProductParameter.getYearManagementConsultingOfRate());
						project.setManagementConsultingOfRate(bpProductParameter.getManagementConsultingOfRate());
						project.setFinanceServiceOfRate(bpProductParameter.getFinanceServiceOfRate());
						project.setDayFinanceServiceOfRate(bpProductParameter.getDayFinanceServiceOfRate());
						project.setDayManagementConsultingOfRate(bpProductParameter.getDayManagementConsultingOfRate());
						project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());
						project.setMineType(bpProductParameter.getMineType());
						project.setMineId(bpProductParameter.getMineId());
						slSmallloanProjectDao.merge(project);
					}
				}
				
				if(null!=project.getProductId() ){
					//初始化产品费用清单
					slActualToChargeDao.initActualChargesProduct(project.getProjectId(),projectNumber,projectName,businessTypeKey,Short.valueOf("1"), project.getProductId());
					//初始化贷款材料清单
					ourProcreditMaterialsEnterpriseDao.initMaterialsByProductId( project.getProjectId(),businessTypeKey, operationTypeKey,project.getProductId());
					//初始化准入原则
					ourProcreditAssuretenetDao.initAssuretenetProduct(project.getProjectId().toString(),businessTypeKey, operationTypeKey, null,project.getProductId());
					//初始化归档材料
					ourArchivesMaterialsDao.initMaterialsByProductId( project.getProjectId(),businessTypeKey, operationTypeKey,project.getProductId());
				}
				
				//根据产品来初始化项目基本信息结束===================================================================
				
				Map<String,Object> vars = new HashMap<String, Object>();
			    Map<String, Object> busMap = new HashMap<String, Object>();
			    if(null!=history && history.equals("dir")){
					 BpFundProject bpFundProject=new BpFundProject();
					 BeanUtil.copyNotNullProperties(bpFundProject, project);
					 bpFundProject.setFlag(Short.valueOf("1"));
					 bpFundProjectDao.save(bpFundProject);
					 vars.put("fundProjectId",bpFundProject.getId());
				 }else if(null!=history && history.equals("or")){
					 BpFundProject bpFundProject=new BpFundProject();
					 BeanUtil.copyNotNullProperties(bpFundProject, project);
					 bpFundProject.setFlag(Short.valueOf("0"));
					 bpFundProjectDao.save(bpFundProject);
					 vars.put("fundProjectId",bpFundProject.getId());
				 }else if(null!=history && history.equals("mmproduceOr")){
					 BpFundProject bpFundProject=new BpFundProject();
					 BeanUtil.copyNotNullProperties(bpFundProject, project);
					 bpFundProject.setFlag(Short.valueOf("0"));
					 bpFundProjectDao.save(bpFundProject);
					 vars.put("fundProjectId",bpFundProject.getId());
				 }else if(null!=history && history.equals("mmplanOr")){
					 BpFundProject bpFundProject=new BpFundProject();
					 BeanUtil.copyNotNullProperties(bpFundProject, project);
					 bpFundProject.setFlag(Short.valueOf("0"));
					 bpFundProjectDao.save(bpFundProject);
					 vars.put("fundProjectId",bpFundProject.getId());
				 }
				vars.put("projectId",project.getProjectId());
				vars.put("oppositeType",project.getOppositeType());
				vars.put("businessType",project.getBusinessType());
				vars.put("operationType",project.getOperationType());
				vars.put("branchCompanyId", branchCompanyId);
				vars.put("productId", project.getProductId());
				vars.put("oppositeID",project.getOppositeID());
				if(person!=null){
					vars.put("personId",person.getId());
				}
				if(null!=loanId && !"".equals(loanId)){
					vars.put("isOnlineApply",true);
				}
				vars.put("childType",project.getChildType());
				flowRunInfo.getVariables().putAll(vars);//会存入数据库
				
				busMap.put("projectNumber",project.getProjectNumber());
				busMap.put("projectName", project.getProjectName());
				busMap.put("subjectName", project.getProjectName()+"-"+project.getProjectNumber());
				busMap.put("businessType",project.getBusinessType());
				busMap.put("customerName",enterprise.getEnterprisename());
				busMap.put("operationType",project.getOperationType());
				busMap.put("flowType",flowTypeKey);
				busMap.put("projectId",project.getProjectId());
				busMap.put("oppositeID",project.getOppositeID());
				
				if(null!=loanId && !"".equals(loanId)){
					busMap.put("isOnlineApply",true);
				}
				flowRunInfo.setBusMap(busMap);
				flowRunInfo.setDefId(flowId);
				return 1;
			 }else{
				 logger.info("启动个贷标准流程失败原因：配置项没有找到个贷流程配置项，请检查；出错时间："+new Date());
				 return 0;
			 }
			 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("个贷启动项目报错,报错时间："+new Date());
			logger.error("个贷启动项目报错,报错信息："+e.getMessage());
			return 0;
		}
		
		
	
	}
	//标准个贷启动方法
	public Integer startCreditFlowPersonLoanNormalFlow(FlowRunInfo flowRunInfo) {
		try{
			 logger.info("启动个贷标准流程开始时间："+new Date());
			 String projectNumber = ""; //项目编号
			 String projectName="";//项目名称
			 String branchNO = "";
			 AppUser user= ContextUtil.getCurrentUser();
		     Long companyId = ContextUtil.getLoginCompanyId();
			 String currentUserId = ContextUtil.getCurrentUserId().toString();
			 String businessTypeKey = flowRunInfo.getBusMap().get("businessTypeKey").toString();
			 String operationTypeKey = flowRunInfo.getBusMap().get("operationTypeKey").toString();
			 String history=flowRunInfo.getRequest().getParameter("history");
			 
			 String loanId=flowRunInfo.getRequest().getParameter("loanId");//线上申请记录id
			 
			 //从配置文件获取个贷流程key
			 Object  flowType =configMap.get("flowType");
			 if(flowType!=null){
				 String flowTypeKey=flowType.toString();
				 if(null!=history && history.equals("dir")){
					 flowTypeKey="PersonCreditDirFastFlow";//直投标快速补录
				 }else if(null!=history && history.equals("or")){
					 flowTypeKey="PersonCreditOrFastFlow";//债权转让快速补录
				 }else if(null !=history && history.equals("mmproduceOr")){
					 flowTypeKey="PersonCreditOrFastMMFlow";//债权转让快速补录--理财产品/理财计划
				 }else if(null !=history && history.equals("mmplanOr")){
					 flowTypeKey="PersonCreditOrFastMMFlow";//债权转让快速补录--理财产品/理财计划
				 }
				 String strDdate = DateUtil.getNowDateTime("yyMMdd");
				 ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);//流程key
				 String flowId = String.valueOf(proDefinition.getDefId());
				 Long branchCompanyId = proDefinition.getBranchCompanyId();
				 SlSmallloanProject project = new SlSmallloanProject(); //初始化项目信息
				 BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "slSmallloanProject");
				 
				 //保存客户信息开始 ==========================================================================================================
				 Person person = new Person();   //初始化person
				 if(flowRunInfo.getRequest().getParameter("person.id") !=null && !"".equals(flowRunInfo.getRequest().getParameter("person.id"))){
					 Integer id=Integer.valueOf(flowRunInfo.getRequest().getParameter("person.id"));
					 person=personDao.getById(id);
				 }
				 BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
				 if(null!=project.getDepartId() && null==person.getId()){
					 person.setShopId(project.getDepartId());
					 person.setShopName(project.getDepartMentName());
				 }
				 Person  operson =personDao.saveSinglePerson(person);
				 //保存客户信息结束 ==========================================================================================================
				 
				 //生成项目名开始 （客户名+年月+个贷项目+流程名称）=============================================================================
				 SimpleDateFormat formart=new SimpleDateFormat("yyyy",Locale.CHINA);
				 SimpleDateFormat formart1=new SimpleDateFormat("MM",Locale.CHINA);
				 Calendar calendar=Calendar.getInstance(Locale.CHINA);
				 String yearStr=formart.format(calendar.getTime());
				 String monthStr=formart1.format(calendar.getTime());
				 String yearAndMonthStr=yearStr+"年"+monthStr+"月";       //年月字符串
				 String cusName=operson.getName();//客户名称
				 String flowTypeName =proDefinition.getName();
				 projectName=cusName+yearAndMonthStr+"个贷项目"+"("+flowTypeName+")";
				 logger.info("启动个贷标准流程生成项目名称："+projectName);
				 
				//生成项目名结束 （客户名+年月+个贷项目+流程名称）=============================================================================
				
				//生成项目编号开始 (组织机构编码_yyMMdd_01)(当天的第一笔)============================================
				if(null!=companyId){
					Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
					branchNO = org.getBranchNO();
				}else{
					Organization organization = organizationDao.getGroupCompany();
					if(organization!=null){
						branchNO = organization.getBranchNO();
					}
				}
				SlSmallloanProject slproject = slSmallloanProjectDao.getProjectNumber(branchNO+"_"+strDdate);
				if(slproject!=null){
					String number = "";
					String proNum = slproject.getProjectNumber();
					String[] proArrs = proNum.split("_");
					int num = new Integer(proArrs[2]);
					num+=1;
					if(num<10){
						number = "00"+num;
					}else if(num>=10&&num<100){
						number = "0"+num;
					}else{
						number = String.valueOf(num);
					}
					projectNumber = branchNO+"_"+strDdate+"_"+number;
				}else{
					projectNumber = branchNO+"_"+strDdate+"_"+"001";
				}
				logger.info("启动个贷标准流程生成项目编号："+projectNumber);
				//生成项目编号结束   (组织机构编码_yyMMdd_01)(当天的第一笔)============================================
				
			   //用来区分债权类型
				if(null !=history && history.equals("mmproduceOr")){
					project.setChildType("mmproduceOr");
				}else if(null !=history && history.equals("mmplanOr")){
					project.setChildType("mmplanOr");
				}
				
				project.setBusinessType(businessTypeKey);
				project.setOperationType(operationTypeKey);
				project.setFlowType(flowTypeKey);
				project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
				project.setCreateDate(new Date());
				project.setProjectProperties("common");
				if(ContextUtil.getLoginCompanyId()!=null){
			    	project.setCompanyId(ContextUtil.getLoginCompanyId());
			    }else{
			    	project.setCompanyId(Long.valueOf("1"));
			    }
			    project.setOppositeID(operson.getId().longValue());
				project.setOppositeType("person_customer");
				project.setProjectName(projectName);
				project.setProjectNumber(projectNumber);
				if(project.getAppUserId()!=null&&!"".equals(project.getAppUserId())){
					String userId = project.getAppUserId().split(",")[0];
					AppUser user1 = appUserService.get(Long.valueOf(userId));
					project.setDepartId(user1.getDepartment().getDepId());
				}
				slSmallloanProjectDao.save(project);
				//根据产品来初始化项目基本信息开始===================================================================
				if(null!=loanId && !"".equals(loanId) && null!=project.getProductId()){//线上申请流程
					BpFinanceApplyUser applyUser=bpFinanceApplyUserDao.get(Long.valueOf(loanId));//申请记录
					P2pLoanProduct product=p2pLoanProductDao.get(project.getProductId());//产品
					QueryFilter filter=new QueryFilter();
					filter.addFilter("Q_productId_L_EQ",project.getProductId());//产品id
					filter.addFilter("Q_loanTime_L_EQ",applyUser.getLoanTimeLen());//贷款期限
					List<P2pLoanRate> rateList=p2pLoanRateDao.getAll(filter);
					if(null!=product){
						//贷款金额、计息方式、还款周期、   贷款利率、管理咨询费率、财务服务费率
						project.setProjectMoney(applyUser.getLoanMoney());//借款金额
						if(null!=applyUser.getPayIntersetWay()){
							project.setAccrualtype(applyUser.getPayIntersetWay());//还款方式
						}
						project.setPayaccrualType(product.getPayaccrualType());//还款周期
						project.setPayintentPeriod(applyUser.getLoanTimeLen());//贷款期限
						project.setIsPreposePayAccrual(product.getIsPreposePayAccrual());//前置付息
						project.setIsInterestByOneTime(product.getIsInterestByOneTime());//是否一次性支付利息
						if(null!=rateList && rateList.size()>0){
							BigDecimal yearRate=rateList.get(0).getYearAccrualRate();
							if(null!=yearRate){
								project.setYearAccrualRate(yearRate);//年化利率
								project.setAccrual(yearRate.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));//月化利率
								project.setDayAccrualRate(yearRate.divide(new BigDecimal(360),10,BigDecimal.ROUND_HALF_EVEN));//日化利率
							}
							
							BigDecimal financeRate=rateList.get(0).getYearFinanceServiceOfRate();
							if(null!=financeRate){
								project.setYearFinanceServiceOfRate(rateList.get(0).getYearFinanceServiceOfRate());
								project.setFinanceServiceOfRate(project.getYearFinanceServiceOfRate().divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));
								project.setDayFinanceServiceOfRate(project.getYearFinanceServiceOfRate().divide(new BigDecimal(360),10,BigDecimal.ROUND_HALF_EVEN));
							}
							BigDecimal managementRate=rateList.get(0).getYearManagementConsultingOfRate();
							if(null!=managementRate){
								project.setYearManagementConsultingOfRate(managementRate);
								project.setManagementConsultingOfRate(managementRate.divide(new BigDecimal(12),10,BigDecimal.ROUND_HALF_EVEN));
								project.setDayManagementConsultingOfRate(managementRate.divide(new BigDecimal(360),10,BigDecimal.ROUND_HALF_EVEN));
							}
						}
						project.setPurposeType(applyUser.getLoanUse());//借款用途
						slSmallloanProjectDao.merge(project);
						
						//借款需求
						BpMoneyBorrowDemand demand=new BpMoneyBorrowDemand();
						demand.setLongestRepaymentPeriod(applyUser.getLoanTimeLen());
						demand.setAccrual(project.getAccrual());
						demand.setRemark(applyUser.getRemark());
						demand.setProjectID(project.getProjectId());
						bpMoneyBorrowDemandDao.merge(demand);
					}
					
					applyUser.setState("5");//已启动借款项目
					bpFinanceApplyUserDao.merge(applyUser);
				}else if(project.getProductId()!=null){
					BpProductParameter bpProductParameter=bpProductParameterDao.get(project.getProductId());
					if(null!=bpProductParameter){
						project.setAccrualtype(bpProductParameter.getAccrualtype());//还款方式
						project.setPayaccrualType(bpProductParameter.getPayaccrualType());//还款周期
						project.setPayintentPeriod(bpProductParameter.getPayintentPeriod());//贷款期限
						project.setIsPreposePayAccrual(bpProductParameter.getIsPreposePayAccrual());//前置付息
						project.setIsInterestByOneTime(bpProductParameter.getIsInterestByOneTime());//是否一次性支付利息
						project.setIsStartDatePay(bpProductParameter.getIsStartDatePay());//每期还款日
						project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());//固定在
						project.setYearAccrualRate(bpProductParameter.getYearAccrualRate());//年化利率
						project.setAccrual(bpProductParameter.getAccrual());//月化利率
						project.setDayAccrualRate(bpProductParameter.getDayAccrualRate());//日化利率
						project.setYearFinanceServiceOfRate(bpProductParameter.getYearFinanceServiceOfRate());
						project.setYearManagementConsultingOfRate(bpProductParameter.getYearManagementConsultingOfRate());
						project.setManagementConsultingOfRate(bpProductParameter.getManagementConsultingOfRate());
						project.setFinanceServiceOfRate(bpProductParameter.getFinanceServiceOfRate());
						project.setDayFinanceServiceOfRate(bpProductParameter.getDayFinanceServiceOfRate());
						project.setDayManagementConsultingOfRate(bpProductParameter.getDayManagementConsultingOfRate());
						project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());
						project.setMineType(bpProductParameter.getMineType());
						project.setMineId(bpProductParameter.getMineId());
						slSmallloanProjectDao.merge(project);
						//初始化产品费用清单
						slActualToChargeDao.initActualChargesProduct(project.getProjectId(),projectNumber,projectName,businessTypeKey,Short.valueOf("1"), project.getProductId());
						//初始化贷款材料清单
						ourProcreditMaterialsEnterpriseDao.initMaterialsByProductId( project.getProjectId(),businessTypeKey, operationTypeKey,project.getProductId());
						//初始化准入原则
						ourProcreditAssuretenetDao.initAssuretenetProduct(project.getProjectId().toString(),businessTypeKey, operationTypeKey, null,project.getProductId());
						//初始化归档材料
						ourArchivesMaterialsDao.initMaterialsByProductId( project.getProjectId(),businessTypeKey, operationTypeKey,project.getProductId());
					}
				}
				//根据产品来初始化项目基本信息结束===================================================================
				
				Map<String,Object> vars = new HashMap<String, Object>();
			    Map<String, Object> busMap = new HashMap<String, Object>();
			    if(null!=history && history.equals("dir")){
					 BpFundProject bpFundProject=new BpFundProject();
					 BeanUtil.copyNotNullProperties(bpFundProject, project);
					 bpFundProject.setFlag(Short.valueOf("1"));
					 bpFundProjectDao.save(bpFundProject);
					 vars.put("fundProjectId",bpFundProject.getId());
				 }else if(null!=history && history.equals("or")){
					 BpFundProject bpFundProject=new BpFundProject();
					 BeanUtil.copyNotNullProperties(bpFundProject, project);
					 bpFundProject.setFlag(Short.valueOf("0"));
					 bpFundProjectDao.save(bpFundProject);
					 vars.put("fundProjectId",bpFundProject.getId());
				 }else if(null!=history && history.equals("mmproduceOr")){
					 BpFundProject bpFundProject=new BpFundProject();
					 BeanUtil.copyNotNullProperties(bpFundProject, project);
					 bpFundProject.setFlag(Short.valueOf("0"));
					 bpFundProjectDao.save(bpFundProject);
					 vars.put("fundProjectId",bpFundProject.getId());
				 }else if(null!=history && history.equals("mmplanOr")){
					 BpFundProject bpFundProject=new BpFundProject();
					 BeanUtil.copyNotNullProperties(bpFundProject, project);
					 bpFundProject.setFlag(Short.valueOf("0"));
					 bpFundProjectDao.save(bpFundProject);
					 vars.put("fundProjectId",bpFundProject.getId());
				 }
				vars.put("projectId",project.getProjectId());
				vars.put("oppositeType",project.getOppositeType());
				vars.put("businessType",project.getBusinessType());
				vars.put("operationType",project.getOperationType());
				vars.put("branchCompanyId", branchCompanyId);
				vars.put("oppositeID",project.getOppositeID());
				vars.put("productId",project.getProductId());
				if(person!=null){
					vars.put("personId",person.getId());
				}
				if(null!=loanId && !"".equals(loanId)){
					vars.put("isOnlineApply",true);
					vars.put("loanId",loanId);
				}
				vars.put("childType", project.getChildType());
				flowRunInfo.getVariables().putAll(vars);//会存入数据库
				
				busMap.put("projectNumber",project.getProjectNumber());
				busMap.put("projectName", project.getProjectName());
				busMap.put("subjectName", project.getProjectName()+"-"+project.getProjectNumber());
				busMap.put("businessType",project.getBusinessType());
				busMap.put("customerName",operson.getName());
				busMap.put("operationType",project.getOperationType());
				busMap.put("flowType",flowTypeKey);
				busMap.put("projectId",project.getProjectId());
				busMap.put("oppositeID",project.getOppositeID());
				
				if(null!=loanId && !"".equals(loanId)){
					busMap.put("isOnlineApply",true);
					busMap.put("loanId",loanId);
				}
				
				flowRunInfo.setBusMap(busMap);
				flowRunInfo.setDefId(flowId);
				return 1;
			 }else{
				 logger.info("启动个贷标准流程失败原因：配置项没有找到个贷流程配置项，请检查；出错时间："+new Date());
				 return 0;
			 }
			 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("个贷启动项目报错,报错时间："+new Date());
			logger.error("个贷启动项目报错,报错信息："+e.getMessage());
			return 0;
		}
		
		
	}
	/**
	 * 得到某个节点的顺序 如尽职调查节点的Key是10_www,返回的是10
	 */
	public Long getOrder(String deployId,String activityName){
		
		Long taskSequence=null;
		ProUserAssign proUserAssign = proUserAssignDao.getByDeployIdActivityName(deployId, activityName);
		if(proUserAssign!=null&&proUserAssign.getTaskSequence()!=null&&!"0".equals(proUserAssign.getTaskSequence())){
			String taskSequenceNodeKey = proUserAssign.getTaskSequence();
			if(taskSequenceNodeKey.indexOf("_")!=-1){
				String[] proArrs = taskSequenceNodeKey.split("_");
				taskSequence = new Long(proArrs[0].trim());
				
			}
		}
		return taskSequence;
	}
	//企业客户小贷申请
	public Integer startEnterpriseSmallFlow(
			FlowRunInfo flowRunInfo) {
		try{
			 logger.info("启动企业客户小贷开始时间："+new Date());
			 String projectNumber = ""; //项目编号
			 String projectName="";//项目名称
			 String branchNO = "";
			 AppUser user= ContextUtil.getCurrentUser();
		     Long companyId = ContextUtil.getLoginCompanyId();
			 String currentUserId = ContextUtil.getCurrentUserId().toString();
			 String businessTypeKey = flowRunInfo.getBusMap().get("businessTypeKey").toString();
			 String operationTypeKey = flowRunInfo.getBusMap().get("operationTypeKey").toString();
			 String history=flowRunInfo.getRequest().getParameter("history");
			 //从配置文件获取个贷流程key
			 Object  flowType =configMap.get("seflowType");
			 if(null!=history && history.equals("entHistory")){
				 flowType="EntHistoryFlow";
			 }
			 if(flowType!=null){
				 String flowTypeKey=flowType.toString();
				
				 String strDdate = DateUtil.getNowDateTime("yyyyMM");
				 ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);//流程key
				 String flowId = String.valueOf(proDefinition.getDefId());
				 Long branchCompanyId = proDefinition.getBranchCompanyId();
				 SlSmallloanProject project = new SlSmallloanProject(); //初始化项目信息
				 BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "slSmallloanProject");
				 
				 //保存客户信息开始 ==========================================================================================================
				 Person person = new Person();   //初始化person
				 Enterprise  enterprise = new Enterprise();  //初始化enterprise
				 if(flowRunInfo.getRequest().getParameter("person.id") !=null || !"".equals(flowRunInfo.getRequest().getParameter("person.id"))){
					 Integer id=Integer.valueOf(flowRunInfo.getRequest().getParameter("person.id"));
					 person=personDao.getById(id);
				 }
				 if(flowRunInfo.getRequest().getParameter("enterprise.id") !=null || !"".equals(flowRunInfo.getRequest().getParameter("enterprise.id"))){
					 Integer id=Integer.valueOf(flowRunInfo.getRequest().getParameter("enterprise.id"));
					 enterprise=enterpriseDao.getById(id);
				 }
				 BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
				 BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
				 if(null!=project.getDepartId() && null==enterprise.getId()){
					 person.setShopId(project.getDepartId());
					 person.setShopName(project.getDepartMentName());
					 enterprise.setShopId(project.getDepartId());
					 enterprise.setShopName(project.getDepartMentName());
				 }
				 String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");//股东信息
				 Enterprise oEnterprise= enterpriseDao.saveSingleEnterprise(enterprise,person,gudongInfo);
				 
				 //保存客户信息结束 ==========================================================================================================
				 
				 //生成项目名开始 （客户名+年月+个贷项目+流程名称）=============================================================================
				 SimpleDateFormat formart=new SimpleDateFormat("yyyy",Locale.CHINA);
				 SimpleDateFormat formart1=new SimpleDateFormat("MM",Locale.CHINA);
				 Calendar calendar=Calendar.getInstance(Locale.CHINA);
				 String yearStr=formart.format(calendar.getTime());
				 String monthStr=formart1.format(calendar.getTime());
				 String yearAndMonthStr=yearStr+"年"+monthStr+"月";       //年月字符串
				 String cusName=oEnterprise.getEnterprisename();//客户名称
				 String flowTypeName =proDefinition.getName();
				 projectName=cusName+yearAndMonthStr+"企贷项目"+"("+flowTypeName+")";
				 logger.info("启动企业客户小贷生成项目名称："+projectName);
				 
				//生成项目名结束 （客户名+年月+个贷项目+流程名称）=============================================================================
				
				//生成项目编号开始 (组织机构编码_yyMMdd_01)(当天的第一笔)============================================
				if(null!=companyId){
					Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
					branchNO = org.getBranchNO();
				}else{
					Organization organization = organizationDao.getGroupCompany();
					if(organization!=null){
						branchNO = organization.getBranchNO();
					}
				}
				SlSmallloanProject slproject = slSmallloanProjectDao.getProjectNumber(branchNO+"_"+yearStr);
				if(slproject!=null){
					String number = "";
					String proNum = slproject.getProjectNumber();
					String[] proArrs = proNum.split("_");
					int num = new Integer(proArrs[2]);
					num+=1;
					if(num<10){
						number = "00"+num;
					}else if(num>=10&&num<100){
						number = "0"+num;
					}else{
						number = String.valueOf(num);
					}
					projectNumber = branchNO+"_"+strDdate+"_"+number;
				}else{
					projectNumber = branchNO+"_"+strDdate+"_"+"001";
				}
				logger.info("启动个贷标准流程生成项目编号："+projectNumber);
				//生成项目编号结束   (组织机构编码_yyMMdd_01)(当天的第一笔)============================================
				
				project.setBusinessType(businessTypeKey);
				project.setOperationType(operationTypeKey);
				project.setFlowType(flowTypeKey);
				project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
				project.setFlowType(flowTypeKey);
				project.setCreateDate(new Date());
				project.setProjectProperties("common");
				project.setMineId(1L);//我方主体必填项  默认为1
				project.setMineType("company_ourmain");
				if(ContextUtil.getLoginCompanyId()!=null){
			    	project.setCompanyId(ContextUtil.getLoginCompanyId());
			    }else{
			    	project.setCompanyId(Long.valueOf("1"));
			    }
			    project.setOppositeID(oEnterprise.getId().longValue());
				project.setOppositeType("company_customer");
				project.setProjectName(projectName);
				project.setProjectNumber(projectNumber);
				slSmallloanProjectDao.save(project);
				//根据产品来初始化项目基本信息开始===================================================================
				if(project.getProductId()!=null){
					BpProductParameter bpProductParameter=bpProductParameterDao.get(project.getProductId());
					if(null!=bpProductParameter){
						project.setAccrualtype(bpProductParameter.getAccrualtype());//还款方式
						project.setPayaccrualType(bpProductParameter.getPayaccrualType());//还款周期
						project.setPayintentPeriod(bpProductParameter.getPayintentPeriod());//贷款期限
						project.setIsPreposePayAccrual(bpProductParameter.getIsPreposePayAccrual());//前置付息
						project.setIsInterestByOneTime(bpProductParameter.getIsInterestByOneTime());//是否一次性支付利息
						project.setIsStartDatePay(bpProductParameter.getIsStartDatePay());//每期还款日
						project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());//固定在
						project.setYearAccrualRate(bpProductParameter.getYearAccrualRate());//年化利率
						project.setAccrual(bpProductParameter.getAccrual());//月化利率
						project.setDayAccrualRate(bpProductParameter.getDayAccrualRate());//日化利率
						project.setYearFinanceServiceOfRate(bpProductParameter.getYearFinanceServiceOfRate());
						project.setYearManagementConsultingOfRate(bpProductParameter.getYearManagementConsultingOfRate());
						project.setManagementConsultingOfRate(bpProductParameter.getManagementConsultingOfRate());
						project.setFinanceServiceOfRate(bpProductParameter.getFinanceServiceOfRate());
						project.setDayFinanceServiceOfRate(bpProductParameter.getDayFinanceServiceOfRate());
						project.setDayManagementConsultingOfRate(bpProductParameter.getDayManagementConsultingOfRate());
						project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());
						project.setMineType(bpProductParameter.getMineType());
						project.setMineId(bpProductParameter.getMineId());
						slSmallloanProjectDao.merge(project);
						//初始化产品费用清单
						slActualToChargeDao.initActualChargesProduct(project.getProjectId(),projectNumber,projectName,businessTypeKey,Short.valueOf("1"), project.getProductId());
						//初始化贷款材料清单
						ourProcreditMaterialsEnterpriseDao.initMaterialsByProductId( project.getProjectId(),businessTypeKey, operationTypeKey,project.getProductId());
						//初始化准入原则
						ourProcreditAssuretenetDao.initAssuretenetProduct(project.getProjectId().toString(),businessTypeKey, operationTypeKey, null,project.getProductId());
						//初始化归档材料
						ourArchivesMaterialsDao.initMaterialsByProductId( project.getProjectId(),businessTypeKey, operationTypeKey,project.getProductId());
					}
				}
				//根据产品来初始化项目基本信息结束===================================================================
				
				Map<String,Object> vars = new HashMap<String, Object>();
			    Map<String, Object> busMap = new HashMap<String, Object>();
			   
				 BpFundProject bpFundProject=new BpFundProject();
				 BeanUtil.copyNotNullProperties(bpFundProject, project);
				 bpFundProject.setFlag(Short.valueOf("0"));
				 bpFundProjectDao.save(bpFundProject);
				 vars.put("fundProjectId",bpFundProject.getId());
				vars.put("projectId",project.getProjectId());
				vars.put("oppositeType",project.getOppositeType());
				vars.put("businessType",project.getBusinessType());
				vars.put("operationType",project.getOperationType());
				vars.put("branchCompanyId", branchCompanyId);
				vars.put("productId", project.getProductId());
				vars.put("oppositeID",project.getOppositeID());
				vars.put("projectNumber",project.getProjectNumber());
				if(person!=null){
					vars.put("personId",person.getId());
				}
				flowRunInfo.getVariables().putAll(vars);//会存入数据库
				
				busMap.put("projectNumber",project.getProjectNumber());
				busMap.put("projectName", project.getProjectName());
				busMap.put("subjectName", project.getProjectName()+"-"+project.getProjectNumber());
				busMap.put("businessType",project.getBusinessType());
				busMap.put("customerName",enterprise.getEnterprisename());
				busMap.put("operationType",project.getOperationType());
				busMap.put("flowType",flowTypeKey);
				busMap.put("projectId",project.getProjectId());
				busMap.put("oppositeID",project.getOppositeID());
				flowRunInfo.setBusMap(busMap);
				flowRunInfo.setDefId(flowId);
				return 1;
			 }else{
				 logger.info("启动企业客户小贷失败原因：配置项没有找到个贷流程配置项，请检查；出错时间："+new Date());
				 return 0;
			 }
			 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("企业客户小贷启动项目报错,报错时间："+new Date());
			logger.error("企业客户小贷启动项目报错,报错信息："+e.getMessage());
			return 0;
		}
		
		
	
	}
	//个人客户小贷启动方法
	public Integer startPersonSmallFlow(FlowRunInfo flowRunInfo) {
		try{
			 logger.info("启动个贷标准流程开始时间："+new Date());
			 String projectNumber = ""; //项目编号
			 String projectName="";//项目名称
			 String branchNO = "";
			 AppUser user= ContextUtil.getCurrentUser();
		     Long companyId = ContextUtil.getLoginCompanyId();
			 String currentUserId = ContextUtil.getCurrentUserId().toString();
			 String businessTypeKey = flowRunInfo.getBusMap().get("businessTypeKey").toString();
			 String operationTypeKey = flowRunInfo.getBusMap().get("operationTypeKey").toString();
			 String history=flowRunInfo.getRequest().getParameter("history");
			 //从配置文件获取个贷流程key
			 Object  flowType =configMap.get("spflowType");
			 if(null!=history && history.equals("personHistory")){
				 flowType="PersonHistoryFlow";
			 }
			 if(flowType!=null){
				 String flowTypeKey=flowType.toString();
				
				 String strDdate = DateUtil.getNowDateTime("yyyyMM");
				 ProDefinition proDefinition = proDefinitionDao.getByKey(flowTypeKey);//流程key
				 String flowId = String.valueOf(proDefinition.getDefId());
				 Long branchCompanyId = proDefinition.getBranchCompanyId();
				 SlSmallloanProject project = new SlSmallloanProject(); //初始化项目信息
				 BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "slSmallloanProject");
				 
				 //保存客户信息开始 ==========================================================================================================
				 Person person = new Person();   //初始化person
				 if(flowRunInfo.getRequest().getParameter("person.id") !=null || !"".equals(flowRunInfo.getRequest().getParameter("person.id"))){
					 Integer id=Integer.valueOf(flowRunInfo.getRequest().getParameter("person.id"));
					 person=personDao.getById(id);
				 }
				 BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
				 if(null!=project.getDepartId() && null==person.getId()){
					 person.setShopId(project.getDepartId());
					 person.setShopName(project.getDepartMentName());
				 }
				 Person  operson =personDao.saveSinglePerson(person);
				 //保存客户信息结束 ==========================================================================================================
				 
				 //生成项目名开始 （客户名+年月+个贷项目+流程名称）=============================================================================
				 SimpleDateFormat formart=new SimpleDateFormat("yyyy",Locale.CHINA);
				 SimpleDateFormat formart1=new SimpleDateFormat("MM",Locale.CHINA);
				 Calendar calendar=Calendar.getInstance(Locale.CHINA);
				 String yearStr=formart.format(calendar.getTime());
				 String monthStr=formart1.format(calendar.getTime());
				 String yearAndMonthStr=yearStr+"年"+monthStr+"月";       //年月字符串
				 String cusName=operson.getName();//客户名称
				 String flowTypeName =proDefinition.getName();
				 projectName=cusName+yearAndMonthStr+"个贷项目"+"("+flowTypeName+")";
				 logger.info("启动个贷标准流程生成项目名称："+projectName);
				 
				//生成项目名结束 （客户名+年月+个贷项目+流程名称）=============================================================================
				
				//生成项目编号开始 (组织机构编码_yyMMdd_01)(当天的第一笔)============================================
				if(null!=companyId){
					Organization org = (Organization) creditBaseDao.getById(Organization.class, companyId);
					branchNO = org.getBranchNO();
				}else{
					Organization organization = organizationDao.getGroupCompany();
					if(organization!=null){
						branchNO = organization.getBranchNO();
					}
				}
				SlSmallloanProject slproject = slSmallloanProjectDao.getProjectNumber(branchNO+"_"+yearStr);
				if(slproject!=null){
					String number = "";
					String proNum = slproject.getProjectNumber();
					String[] proArrs = proNum.split("_");
					int num = new Integer(proArrs[2]);
					num+=1;
					if(num<10){
						number = "00"+num;
					}else if(num>=10&&num<100){
						number = "0"+num;
					}else{
						number = String.valueOf(num);
					}
					projectNumber = branchNO+"_"+strDdate+"_"+number;
				}else{
					projectNumber = branchNO+"_"+strDdate+"_"+"001";
				}
				logger.info("启动个贷标准流程生成项目编号："+projectNumber);
				//生成项目编号结束   (组织机构编码_yyMMdd_01)(当天的第一笔)============================================
				
				project.setBusinessType(businessTypeKey);
				project.setOperationType(operationTypeKey);
				project.setFlowType(flowTypeKey);
				project.setProjectStatus(Constants.PROJECT_STATUS_ACTIVITY);
				project.setCreateDate(new Date());
				project.setProjectProperties("common");
				if(ContextUtil.getLoginCompanyId()!=null){
			    	project.setCompanyId(ContextUtil.getLoginCompanyId());
			    }else{
			    	project.setCompanyId(Long.valueOf("1"));
			    }
			    project.setOppositeID(operson.getId().longValue());
				project.setOppositeType("person_customer");
				project.setProjectName(projectName);
				project.setProjectNumber(projectNumber);
				project.setArchivesBelonging(operson.getArchivesBelonging());
				slSmallloanProjectDao.save(project);
				//根据产品来初始化项目基本信息开始===================================================================
				if(project.getProductId()!=null){
					BpProductParameter bpProductParameter=bpProductParameterDao.get(project.getProductId());
					if(null!=bpProductParameter){
						project.setAccrualtype(bpProductParameter.getAccrualtype());//还款方式
						project.setPayaccrualType(bpProductParameter.getPayaccrualType());//还款周期
						project.setPayintentPeriod(bpProductParameter.getPayintentPeriod());//贷款期限
						project.setIsPreposePayAccrual(bpProductParameter.getIsPreposePayAccrual());//前置付息
						project.setIsInterestByOneTime(bpProductParameter.getIsInterestByOneTime());//是否一次性支付利息
						project.setIsStartDatePay(bpProductParameter.getIsStartDatePay());//每期还款日
						project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());//固定在
						project.setYearAccrualRate(bpProductParameter.getYearAccrualRate());//年化利率
						project.setAccrual(bpProductParameter.getAccrual());//月化利率
						project.setDayAccrualRate(bpProductParameter.getDayAccrualRate());//日化利率
						project.setYearFinanceServiceOfRate(bpProductParameter.getYearFinanceServiceOfRate());
						project.setYearManagementConsultingOfRate(bpProductParameter.getYearManagementConsultingOfRate());
						project.setManagementConsultingOfRate(bpProductParameter.getManagementConsultingOfRate());
						project.setFinanceServiceOfRate(bpProductParameter.getFinanceServiceOfRate());
						project.setDayFinanceServiceOfRate(bpProductParameter.getDayFinanceServiceOfRate());
						project.setDayManagementConsultingOfRate(bpProductParameter.getDayManagementConsultingOfRate());
						project.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());
						project.setMineType(bpProductParameter.getMineType());
						project.setMineId(bpProductParameter.getMineId());
						slSmallloanProjectDao.merge(project);
						//初始化产品费用清单
						slActualToChargeDao.initActualChargesProduct(project.getProjectId(),projectNumber,projectName,businessTypeKey,Short.valueOf("1"), project.getProductId());
						//初始化贷款材料清单
						ourProcreditMaterialsEnterpriseDao.initMaterialsByProductId( project.getProjectId(),businessTypeKey, operationTypeKey,project.getProductId());
						//初始化准入原则
						ourProcreditAssuretenetDao.initAssuretenetProduct(project.getProjectId().toString(),businessTypeKey, operationTypeKey, null,project.getProductId());
						//初始化归档材料
						ourArchivesMaterialsDao.initMaterialsByProductId( project.getProjectId(),businessTypeKey, operationTypeKey,project.getProductId());
					}
				}
				//根据产品来初始化项目基本信息结束===================================================================
				
				Map<String,Object> vars = new HashMap<String, Object>();
			    Map<String, Object> busMap = new HashMap<String, Object>();
			   
				 BpFundProject bpFundProject=new BpFundProject();
				 BeanUtil.copyNotNullProperties(bpFundProject, project);
				 bpFundProject.setFlag(Short.valueOf("0"));
				 bpFundProjectDao.save(bpFundProject);
				 vars.put("fundProjectId",bpFundProject.getId());
				vars.put("projectId",project.getProjectId());
				vars.put("oppositeType",project.getOppositeType());
				vars.put("businessType",project.getBusinessType());
				vars.put("operationType",project.getOperationType());
				vars.put("branchCompanyId", branchCompanyId);
				vars.put("oppositeID",project.getOppositeID());
				vars.put("productId",project.getProductId());
				vars.put("projectNumber",project.getProjectNumber());
				if(person!=null){
					vars.put("personId",person.getId());
				}
				
				/*if(null!=loanId && !"".equals(loanId)){
					vars.put("isOnlineApply",true);
					vars.put("loanId",loanId);
				}*/
				
				flowRunInfo.getVariables().putAll(vars);//会存入数据库
				
				busMap.put("projectNumber",project.getProjectNumber());
				busMap.put("projectName", project.getProjectName());
				busMap.put("subjectName", project.getProjectName()+"-"+project.getProjectNumber());
				busMap.put("businessType",project.getBusinessType());
				busMap.put("customerName",operson.getName());
				busMap.put("operationType",project.getOperationType());
				busMap.put("flowType",flowTypeKey);
				busMap.put("projectId",project.getProjectId());
				busMap.put("oppositeID",project.getOppositeID());
				flowRunInfo.setBusMap(busMap);
				flowRunInfo.setDefId(flowId);
				return 1;
			 }else{
				 logger.info("启动个贷标准流程失败原因：配置项没有找到个贷流程配置项，请检查；出错时间："+new Date());
				 return 0;
			 }
			 
		}catch(Exception e){
			e.printStackTrace();
			logger.error("个贷启动项目报错,报错时间："+new Date());
			logger.error("个贷启动项目报错,报错信息："+e.getMessage());
			return 0;
		}
		
		
	}
	
	@Override
	public String startTurnoverCustomerFlow(HttpServletRequest request,PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo, String relt) {
		String branchNO = "";
		String applyNumber = ""; //申请编号
		Long companyId = ContextUtil.getLoginCompanyId();
		AppUser user= ContextUtil.getCurrentUser();
		FlowRunInfo flowRunInfo = new FlowRunInfo();
		try{
			String strDdate = DateUtil.getNowDateTime("yyMMdd");
			ProDefinition proDefinition = proDefinitionDao.getByKey("FinanceProductBuyFlow");//流程key
			String flowId = String.valueOf(proDefinition.getDefId());
			Long branchCompanyId = proDefinition.getBranchCompanyId();
			
		//	plManageMoneyPlanBuyinfo.setBuyNumber(applyNumber);
//			Organization org=organizationDao.get(ContextUtil.getLoginCompanyId());
//			if(ContextUtil.getLoginCompanyId()!=null){
//				plManageMoneyPlanBuyinfo.setOrg(org);
//		    }else{
//		    	org=new Organization();
//		    	org.setCompanyId(Long.valueOf("1"));
//		    	plManageMoneyPlanBuyinfo.setOrg(org);
//		    }
			PlManageMoneyPlanBuyinfo _plManageMoneyPlanBuyinfo = plManageMoneyPlanBuyinfoDao.save(plManageMoneyPlanBuyinfo);
			Long projectId = _plManageMoneyPlanBuyinfo.getOrderId();
			Map<String,Object> vars = new HashMap<String, Object>();
			Map<String, Object> busMap = new HashMap<String, Object>();
			
			vars.put("projectId",projectId);
			vars.put("oppositeId",plManageMoneyPlanBuyinfo.getInvestPersonId());
			vars.put("projectNumber", plManageMoneyPlanBuyinfo.getInvestPersonId()+"--"+DateUtil.getNowDateTime("yyMMddHHmmss"));
			vars.put("branchCompanyId", branchCompanyId);
			vars.put("businessType","FinancingBusiness");
		//	vars.put("dealId",relt);
			flowRunInfo.getVariables().putAll(vars);
			
			busMap.put("subjectName", plManageMoneyPlanBuyinfo.getInvestPersonName()+"("+proDefinition.getName()+")");
			busMap.put("customerName",plManageMoneyPlanBuyinfo.getInvestPersonName());
			busMap.put("businessType","FinancingBusiness");
			busMap.put("flowType","FinanceProductBuyFlow");
			busMap.put("oppositeId",plManageMoneyPlanBuyinfo.getInvestPersonId());
			busMap.put("projectNumber", plManageMoneyPlanBuyinfo.getInvestPersonId()+"--"+DateUtil.getNowDateTime("yyMMddHHmmss"));
			busMap.put("projectId",projectId);
		//	busMap.put("dealId",relt);
			flowRunInfo.setBusMap(busMap);
			flowRunInfo.setDefId(flowId);
			ProcessRun run = this.jbpmService.doStartProcess(flowRunInfo);
			
			//特殊计息流程 查看意见与说明记录   以及  流程图需要runId
			PlManageMoneyPlanBuyinfo tempProject=plManageMoneyPlanBuyinfoDao.get(projectId);
			tempProject.setRunId(run.getRunId());
			plManageMoneyPlanBuyinfoDao.merge(tempProject);
			
			String useTemplate=request.getParameter("useTemplate");
			run.setSubject(String.valueOf(flowRunInfo.getBusMap().get("subjectName")));
			if("true".equals(useTemplate)){
				flowRunInfo.getVariables().putAll(BeanUtil.getMapFromRequest(request));
			}
			//保存后，把流程中相关的变量及数据全部提交至run_data表中，以方便后续的展示
			runDataService.saveFlowVars(run.getRunId(), flowRunInfo.getVariables());
			String str = "";
			if (run != null && run.getPiId() != null) {
				str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),user.getUserId().toString(), plManageMoneyPlanBuyinfo.getInvestPersonName());
			}
			return str;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("启动理财产品购买流程出错:"+e.getMessage());
			return null;
		}
	}
	
}
