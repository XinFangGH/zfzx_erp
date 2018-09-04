package com.zhiwei.credit.action.creditFlow.common;

import java.io.File;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.jbpm.api.TaskService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.jbpm.pv.TaskInfo;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.util.SwitchMoneyUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;
import com.zhiwei.credit.model.creditFlow.common.VCommonProjectFlow;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlChargeDetail;
import com.zhiwei.credit.model.creditFlow.finance.SlFundDetail;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.financeProject.VFinancingProject;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.VGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.VLawsuitguarantProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.VLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.VPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.VSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.p2p.loan.P2pLoanProduct;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.DictionaryIndependent;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.ReportTemplate;
import com.zhiwei.credit.model.system.product.BpProductParameter;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.common.GlobalSupervisemanageService;
import com.zhiwei.credit.service.creditFlow.common.VCommonProjectFlowService;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.customer.person.SpouseService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlChargeDetailService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundDetailService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.financeProject.VFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.VGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.lawsuitguarantee.project.VLawsuitguarantProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlAlterAccrualRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.VSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.supervise.SlSuperviseRecordService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProUserAssignService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.TaskSignDataService;
import com.zhiwei.credit.service.p2p.loan.P2pLoanProductService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.GlobalTypeService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.ReportTemplateService;
import com.zhiwei.credit.service.system.product.BpProductParameterService;

import flexjson.JSONSerializer;

public class ProjectAction  extends BaseAction{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private SlCompanyMainService companyMainService;
	@Resource
	private SlPersonMainService  slPersonMainService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService; //小额贷款
    @Resource
    private EnterpriseService enterpriseService;
    @Resource
    private PersonService personService;
	@Resource
	private GLGuaranteeloanProjectService glGuaranteeloanProjectService; //企业贷款
	@Resource
	private AreaDicService areaDicService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private TaskService taskService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	@Resource
	private TaskSignDataService taskSignDataService;
	@Resource(name="flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;
	@Resource 
	private ProcessFormService processFormService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private VGuaranteeloanProjectService vGuaranteeloanProjectService;
	@Resource
	private SlSuperviseRecordService slSuperviseRecordService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlFundDetailService slFundDetailService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlChargeDetailService slChargeDetailService;
	@Resource
	ProUserAssignService proUserAssignService;
	@Resource
	private VSmallloanProjectService vSmallloanProjectService;
	@Resource
	private VFinancingProjectService vFinancingProjectService;
	@Resource
	private VCommonProjectFlowService vCommonProjectFlowService; 
	@Resource
	private JbpmService jbpmService;
	@Resource
	private VLawsuitguarantProjectService vLawsuitguarantProjectService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private SpouseService spouseService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private VProcreditContractService vProcreditContractService;
	@Resource
	private SlAlterAccrualRecordService  slAlteraccrualRecordService;
	@Resource
	private SlEarlyRepaymentRecordService  slEarlyRepaymentRecordService;
	@Resource
	private PlPawnProjectService plPawnProjectService;
	@Resource
	private GlobalSupervisemanageService globalSupervisemanageService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private SlCompanyMainService slCompanyMainService;
	@Resource
	private P2pLoanProductService p2pLoanProductService; 
	@Resource
	private BpProductParameterService bpProductParameterService;
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	private String taskId;
	private String businessType;
	private Long projectId;
	private Long runId;
	private Long safeLevel;
	private String flowTaskId;//流程任务id
	private String customerId;
	private String customerType;
	private String userId;
	private String activityName;
	

	@Resource
	private ReportTemplateService reportTemplateService;
	
	public String getBusinessTypeList(){
		List<GlobalType> list = globalTypeService.getByNodeKey("Business");
		List<GlobalType> returnList=new  ArrayList<GlobalType>();
		if(null!=list && list.size()>0){
			
			returnList=this.globalTypeService.getByParentId(list.get(0).getProTypeId());
			StringBuffer buff = new StringBuffer("[");
			for (GlobalType glType : returnList) {
				buff.append("[").append(glType.getProTypeId()).append(",'")
						.append(glType.getTypeName()).append("'],");
			}
			if (returnList.size() > 0) {
				buff.deleteCharAt(buff.length() - 1);
			}
			buff.append("]");
			setJsonString(buff.toString());
		}
		return SUCCESS;
	}
	public String getBusinessTypeList1(){
		List<GlobalType> list = globalTypeService.getByNodeKey("Business");
		List<GlobalType> returnList=new  ArrayList<GlobalType>();
		if(null!=list && list.size()>0){
			
			returnList=this.globalTypeService.getByParentId(list.get(0).getProTypeId());
			StringBuffer buff = new StringBuffer("[");
			for (GlobalType glType : returnList) {
				if(!glType.getNodeKey().equals("InternetFinance")){
				buff.append("['").append(glType.getNodeKey()).append("','")
						.append(glType.getTypeName()).append("'],");}
			}
			if (returnList.size() > 0) {
				buff.deleteCharAt(buff.length() - 1);
			}
			buff.append("]");
			setJsonString(buff.toString());
		}
		return SUCCESS;
	}
	//业务类别包含互联网金融
	public String getBusinessTypeListAll(){
		List<GlobalType> list = globalTypeService.getByNodeKey("Business");
		List<GlobalType> returnList=new  ArrayList<GlobalType>();
		if(null!=list && list.size()>0){
			
			returnList=this.globalTypeService.getByParentId(list.get(0).getProTypeId());
			StringBuffer buff = new StringBuffer("[");
			for (GlobalType glType : returnList) {
			//	if(glType.getNodeKey().equals("InternetFinance")){
				buff.append("['").append(glType.getNodeKey()).append("','")
						.append(glType.getTypeName()).append("'],");
				//}
			}
			if (returnList.size() > 0) {
				buff.deleteCharAt(buff.length() - 1);
			}
			buff.append("]");
			setJsonString(buff.toString());
		}
		return SUCCESS;
	}
	public String getBusinessTypeListByParentId(){
		
		String parentIdStr=this.getRequest().getParameter("parentId");
		Long parentId=Long.valueOf(parentIdStr);
		List<GlobalType> list=new  ArrayList<GlobalType>();
		list=this.globalTypeService.getByParentId(parentId);
		
		StringBuffer buff = new StringBuffer("[");
		for (GlobalType glType : list) {
			buff.append("[").append(glType.getProTypeId()).append(",'")
					.append(glType.getTypeName()).append("'],");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	public String getOperationTypeListByParentId(){
		
		String parentIdStr=this.getRequest().getParameter("parentId");
		Long parentId=Long.valueOf(parentIdStr);
		List<GlobalType> list=new  ArrayList<GlobalType>();
		list=this.globalTypeService.getByParentId(parentId);
		
		StringBuffer buff = new StringBuffer("[");
		for (GlobalType glType : list) {
			buff.append("['").append(glType.getNodeKey()).append("','")
					.append(glType.getTypeName()).append("'],");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	public String getProDefinitionByGlobalTypeId(){
		
		//判断是否为集团版本。是集团版本则启动流程的时候根据对应登录人员所属分公司显示对应流程。add by lu 2012.11.23 start
		boolean isGroup = false;
		Long branchCompanyId = new Long(1);
		String isGroupVersion=AppUtil.getSystemIsGroupVersion();
		if(isGroupVersion!=null&&Boolean.valueOf(isGroupVersion)){
			isGroup = true;
			String companyId = this.getSession().getAttribute("CompanyId").toString();
			if(companyId!=null&&!"".equals(companyId)){
				branchCompanyId = Long.valueOf(companyId);
			}
		}
		//add by lu 2012.11.23 end
		
		String proTypeIdStr=this.getRequest().getParameter("parentId");
		Long proTypeId=Long.valueOf(proTypeIdStr);
	    List<ProDefinition> list=this.proDefinitionService.getByProTypeId(proTypeId,isGroup,branchCompanyId);
		StringBuffer buff = new StringBuffer("[");
		for (ProDefinition proDefinition : list) {
			buff.append("['").append(proDefinition.getDEFKEY()).append("','")
					.append(proDefinition.getName()).append("'],");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	public String getProjectName(){
		String projectName="";
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		/**
		 * 生成年份和月份
		 */
		try
		{
			SimpleDateFormat formart=new SimpleDateFormat("yyyy",Locale.CHINA);
			SimpleDateFormat formart1=new SimpleDateFormat("MM",Locale.CHINA);
			Calendar calendar=Calendar.getInstance(Locale.CHINA);
			String yearStr=formart.format(calendar.getTime());
			String monthStr=formart1.format(calendar.getTime());
			String mineId=this.getRequest().getParameter("mineId");  //我方主体ID
			String mineType=this.getRequest().getParameter("mineType"); //我方主体类型
			String operationType=this.getRequest().getParameter("operationType");//业务品种
			String businessType=this.getRequest().getParameter("businessType");//业务类别
			String flowType=this.getRequest().getParameter("flowType");//流程类别
			String mineNameShort=""; //我方主体简称   非集团版读的是我方主体名称   集团版读取的是分公司名称
			String yearAndMonthStr=yearStr+"年"+monthStr+"月";       //年月字符串
			String cusName=this.getRequest().getParameter("cusName");//客户名称
			
			String ftype = configMap.get("flowType").toString();
			if(null!=ftype&&!"".equals(ftype)){
			 flowType = ftype;
			}
			
			if(operationType!=null&&!"".equals(operationType)&&!"TwoGrades".equals(operationType)){
				operationType=this.globalTypeService.get(Long.valueOf(operationType)).getTypeName(); //业务品种
			}else{
				if(businessType!=null&&!"".equals(businessType)){
					operationType=this.globalTypeService.get(Long.valueOf(businessType)).getTypeName(); //业务类别的值(两级目录)
				}else{
					operationType = "未定义";
				}
			}
			
			String flowTypeStr=proDefinitionService.getByKey(flowType).getName();
			if(flag){//判断是否为集团版，（flag==true表示集团版）集团版读取的是分公司名称  add  by  liny
				//尚未添加集团版分公司名称字段
				//判断是否为集团版，集团版读取的是分公司名称，（flag==false表示非集团版）非集团版读取的是我方主体的简称
				if(mineType.equals("company_ourmain")){ //企业
					/*if(null!=mineId){
						Organization o=(Organization) creditBaseDao.getById(Organization.class, Long.valueOf(mineId));
						if(null!=o){
							mineNameShort=o.getOrgName();
						}
					}*/
					if(mineId!=null&&!"".equals(mineId)&&!"null".equals(mineId)){
						Organization o=(Organization) creditBaseDao.getById(Organization.class, Long.valueOf(mineId));
						if(null!=o){
							mineNameShort=o.getOrgName();
						}
					}
				}
				else  if(mineType.equals("person_ourmain")) //个人
				{
					if(null!=mineId){
						Organization o=(Organization) creditBaseDao.getById(Organization.class, Long.valueOf(mineId));
						if(null!=o){
							mineNameShort=o.getOrgName();
						}
					}
					
				}
			
				
				
				
				
			}else{//判断是否为集团版，集团版读取的是分公司名称，（flag==false表示非集团版）非集团版读取的是我方主体的简称
				if(mineType.equals("company_ourmain")){ //企业
					/*if(null!=mineId){
						Organization o=(Organization) creditBaseDao.getById(Organization.class, Long.valueOf(mineId));
						if(null!=o){
							mineNameShort=o.getOrgName();
						}
					}*/
					if(mineId!=null&&!"".equals(mineId)&&!"null".equals(mineId)){
						mineNameShort=this.companyMainService.get(Long.valueOf(mineId)).getSimpleName();
					}
				}
				else  if(mineType.equals("person_ourmain")) //个人
				{
					/*if(null!=mineId){
						Organization o=(Organization) creditBaseDao.getById(Organization.class, Long.valueOf(mineId));
						if(null!=o){
							mineNameShort=o.getOrgName();
						}
					}*/
					if(mineId!=null&&!"".equals(mineId)&&!"null".equals(mineId)){
						mineNameShort=this.slPersonMainService.get(Long.valueOf(mineId)).getName();
					}
				}
			}
			
		    projectName=cusName+yearAndMonthStr+operationType+"项目("+mineNameShort+")(贷款业务流程)";
			StringBuffer sb = new StringBuffer("{success:true,data:");
			sb.append("[{\"dd\":\""+projectName+"\"}]");
			sb.append("}");
			setJsonString(sb.toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ProjectAction生成项目名称出错:"+e.getMessage());
		}
		return SUCCESS;
	}
	public String getEnterProjectName(){
		String projectName="";
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		/**
		 * 生成年份和月份
		 */
		try
		{
			SimpleDateFormat formart=new SimpleDateFormat("yyyy",Locale.CHINA);
			SimpleDateFormat formart1=new SimpleDateFormat("MM",Locale.CHINA);
			Calendar calendar=Calendar.getInstance(Locale.CHINA);
			String yearStr=formart.format(calendar.getTime());
			String monthStr=formart1.format(calendar.getTime());
			
			String yearAndMonthStr=yearStr+"年"+monthStr+"月";       //年月字符串
			String cusName=this.getRequest().getParameter("cusName");//客户名称
		
		    projectName=cusName+yearAndMonthStr+"项目(贷款业务流程)";
			StringBuffer sb = new StringBuffer("{success:true,data:");
			sb.append("[{\"dd\":\""+projectName+"\"}]");
			sb.append("}");
			setJsonString(sb.toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ProjectAction生成项目名称出错:"+e.getMessage());
		}
		return SUCCESS;
	}
	public String getName(){
		String projectName="";
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
		/**
		 * 生成年份和月份
		 */
		try
		{
			SimpleDateFormat formart=new SimpleDateFormat("yyyy",Locale.CHINA);
			SimpleDateFormat formart1=new SimpleDateFormat("MM",Locale.CHINA);
			Calendar calendar=Calendar.getInstance(Locale.CHINA);
			String yearStr=formart.format(calendar.getTime());
			String monthStr=formart1.format(calendar.getTime());
			//String mineId=this.getRequest().getParameter("mineId");  //我方主体ID
			//String mineType=this.getRequest().getParameter("mineType"); //我方主体类型
			String operationType="SmallLoanBusiness";//业务品种
			String businessType="SmallLoan";//业务类别
			String flowType="SmallloanBusinessType";//流程类别
			String mineNameShort=""; //我方主体简称   非集团版读的是我方主体名称   集团版读取的是分公司名称
			String yearAndMonthStr=yearStr+"年"+monthStr+"月";       //年月字符串
			String cusName=this.getRequest().getParameter("cusName");//客户名称
			/*if(operationType!=null&&!"".equals(operationType)&&!"TwoGrades".equals(operationType)){
				operationType=this.globalTypeService.get //业务品种
			}else{
				if(businessType!=null&&!"".equals(businessType)){
					operationType=this.globalTypeService.get(Long.valueOf(businessType)).getTypeName(); //业务类别的值(两级目录)
				}else{
					operationType = "未定义";
				}
			}*/
			
			String flowTypeStr=proDefinitionService.getByKey(flowType).getName();
			/*if(flag){//判断是否为集团版，（flag==true表示集团版）集团版读取的是分公司名称  add  by  liny
				//尚未添加集团版分公司名称字段
				//判断是否为集团版，集团版读取的是分公司名称，（flag==false表示非集团版）非集团版读取的是我方主体的简称
				if(mineType.equals("company_ourmain")){ //企业
					if(null!=mineId){
						Organization o=(Organization) creditBaseDao.getById(Organization.class, Long.valueOf(mineId));
						if(null!=o){
							mineNameShort=o.getOrgName();
						}
					}
					if(mineId!=null&&!"".equals(mineId)&&!"null".equals(mineId)){
						Organization o=(Organization) creditBaseDao.getById(Organization.class, Long.valueOf(mineId));
						if(null!=o){
							mineNameShort=o.getOrgName();
						}
					}
				}
				else  if(mineType.equals("person_ourmain")) //个人
				{
					if(null!=mineId){
						Organization o=(Organization) creditBaseDao.getById(Organization.class, Long.valueOf(mineId));
						if(null!=o){
							mineNameShort=o.getOrgName();
						}
					}
					
				}
			
				
				
				
				
			}else{//判断是否为集团版，集团版读取的是分公司名称，（flag==false表示非集团版）非集团版读取的是我方主体的简称
				if(mineType.equals("company_ourmain")){ //企业
					if(null!=mineId){
						Organization o=(Organization) creditBaseDao.getById(Organization.class, Long.valueOf(mineId));
						if(null!=o){
							mineNameShort=o.getOrgName();
						}
					}
					if(mineId!=null&&!"".equals(mineId)&&!"null".equals(mineId)){
						mineNameShort=this.companyMainService.get(Long.valueOf(mineId)).getSimpleName();
					}
				}
				else  if(mineType.equals("person_ourmain")) //个人
				{
					if(null!=mineId){
						Organization o=(Organization) creditBaseDao.getById(Organization.class, Long.valueOf(mineId));
						if(null!=o){
							mineNameShort=o.getOrgName();
						}
					}
					if(mineId!=null&&!"".equals(mineId)&&!"null".equals(mineId)){
						mineNameShort=this.slPersonMainService.get(Long.valueOf(mineId)).getName();
					}
				}
			}
			*/
		    projectName=cusName+yearAndMonthStr+"小额贷款"+"项目"+flowTypeStr+")";
			StringBuffer sb = new StringBuffer("{success:true,data:");
			sb.append("[{\"dd\":\""+projectName+"\"}]");
			sb.append("}");
			setJsonString(sb.toString());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ProjectAction生成项目名称出错:"+e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 读取微贷项目信息
	 */
	public String getMcroLoanInfo(){
		String taskId=this.getRequest().getParameter("taskId"); //项目ID
		String type=this.getRequest().getParameter("type");  //	 
		String task_id=this.getRequest().getParameter("task_id");  //	任务ID 
	    
		   
		SlSmallloanProject project=this.slSmallloanProjectService.get(Long.valueOf(taskId));
		Map<String, Object> map = new HashMap<String, Object>();
		String mineName="";
		String financeServiceMineName="";
		String managementConsultingMineName="";
		
		if(null!=project.getFinanceServiceMineType() && !"".equals(project.getFinanceServiceMineType())){
		 if(null!=project.getFinanceServiceMineId() && !"".equals(project.getFinanceServiceMineId()))
		 {
				if(project.getFinanceServiceMineType().equals("company_ourmain")){
					
					financeServiceMineName=this.companyMainService.get(project.getFinanceServiceMineId()).getCorName();
				}else if (project.getFinanceServiceMineType().equals("person_ourmain")){
					
					financeServiceMineName=this.slPersonMainService.get(project.getFinanceServiceMineId()).getName();
				}
		
		}}
		
		if(null!=project.getManagementConsultingMineType() && !"".equals(project.getManagementConsultingMineType())){
		if(null!=project.getManagementConsultingMineId() && !"".equals(project.getManagementConsultingMineId())){
				if(project.getManagementConsultingMineType().equals("company_ourmain")){
					
					managementConsultingMineName=this.companyMainService.get(project.getManagementConsultingMineId()).getCorName();
				}
				else{
					
					managementConsultingMineName=this.slPersonMainService.get(project.getManagementConsultingMineId()).getName();
				}
		
		}}
		
		
		
		
		
		try
		{
			if(project.getMineType().equals("company_ourmain"))
			{
				Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
				if(null!=o){
					mineName=o.getOrgName();
				}
				//mineName=this.companyMainService.get(project.getMineId()).getCorName();
			}
			else if (project.getMineType().equals("person_ourmain"))
			{
				Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
				if(null!=o){
					mineName=o.getOrgName();
				}
				//mineName=this.slPersonMainService.get(project.getMineId()).getName();
			}
				Person p=this.personService.getById(project.getOppositeID().intValue());
				map.put("person", p);
		        if(null!=p){
		        	if(null!=p.getId()){
		        		List<EnterpriseBank> list=enterpriseBankService.getBankList(p.getId(), Short.valueOf("1"), Short.valueOf("0"),Short.valueOf("0"));
		        		if(null!=list && list.size()>0){
		        			EnterpriseBank bank=list.get(0);
		        			map.put("enterpriseBank", bank);
		        		}
		        		if(null!=p.getMarry() && p.getMarry()==317){
		        			Spouse spouse=spouseService.getByPersonId(p.getId());
		        			map.put("spouse", spouse);
		        		}
		        	}
		        }
//		        List<FinanceInfo> flist=financeInfoService.getList(Long.valueOf(taskId), type);
//		        if(null!=flist && flist.size()>0){
//		        	FinanceInfo financeInfo=flist.get(0);
//		        	map.put("financeInfo", financeInfo);
//		        }
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("ProjectAction:"+e.getMessage());
		}
		String appuers="";
		if(null!=project.getAppUserId())
		{
			String [] appstr=project.getAppUserId().split(",");
			Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet){
				appuers+=s.getFamilyName()+",";
			}
			
		}
		if(appuers.length()>0){
		appuers=appuers.substring(0, appuers.length()-1);}
		StringBuffer textBuffer = new StringBuffer (mineName); 
		project.setMineName(textBuffer.toString());
		project.setAppUsers(appuers);
		String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");  //	 
		if(null!=slSuperviseRecordId &&  !"".equals(slSuperviseRecordId)){
			
			SlSuperviseRecord slSuperviseRecord=this.slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
			map.put("slSuperviseRecord", slSuperviseRecord);
		}
		Long times = processFormService.getActvityExeTimes(runId, "slnExaminationArrangement");//是否经过审贷会
		map.put("times", times);
		map.put("slSmallloanProject", project); 
		map.put("mineName", mineName);
		map.put("financeServiceMineName", financeServiceMineName); 
		map.put("managementConsultingMineName", managementConsultingMineName); 
		map.put("businessType", project.getBusinessType());
		map.put("isAheadPay", project.getIsAheadPay());
		map.put("businessTypeKey",this.globalTypeService.getByNodeKey(project.getBusinessType()).get(0).getTypeName());
		map.put("operationTypeKey",this.globalTypeService.getByNodeKey(project.getOperationType()).get(0).getTypeName());
		map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getDescription());
		map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
		try{
			if(null!=project.getLoanLimit()){
				DictionaryIndependent dic=this.dictionaryIndependentService.getByDicKey(project.getLoanLimit()).get(0);
				if(null!=dic){
					map.put("smallLoanTypeKey", dic.getItemValue());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(null!=task_id && !"".equals(task_id)){
			ProcessForm pform = processFormService.getByTaskId(task_id);
			if(pform==null){
				pform = creditProjectService.getCommentsByTaskId(task_id);
			}
			if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
				map.put("comments", pform.getComments());
			}
		}
		/*if(null!=task_id && !"".equals(task_id)){
			map.put("comments", this.processFormService.getByTaskId(task_id).getComments());
		}*/
		//add by lisl 2012-10-10 监管记录信息
/*		if(this.getRequest().getParameter("superviseManageId")!=null){
			String smId = this.getRequest().getParameter("superviseManageId");
			if(!"".equals(smId)) {
				Long id = Long.valueOf(smId);
				map.put("slSupervisemanage", ss);
			}
		}*/
		
		//end 
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 读取项目信息 公共方法   by shendexuan 修改
	 * 该方法只针对小贷。担保、融资等另起方法，否则该方法不易于维护。
	 */
	public String getInfo(){
		
		String taskId=this.getRequest().getParameter("taskId"); //项目ID
		String task_id=this.getRequest().getParameter("task_id");  //	任务ID 
		SlSmallloanProject project=this.slSmallloanProjectService.get(Long.valueOf(taskId));
		Map<String, Object> map = new HashMap<String, Object>();
		String mineName="";
		String financeServiceMineName="";
		String managementConsultingMineName="";
		if(null!=project.getFinanceServiceMineType() && !"".equals(project.getFinanceServiceMineType())){
			if(null!=project.getFinanceServiceMineId() && !"".equals(project.getFinanceServiceMineId())){
				if(project.getFinanceServiceMineType().equals("company_ourmain")){
					financeServiceMineName=this.companyMainService.get(project.getFinanceServiceMineId()).getCorName();
				}else{
					financeServiceMineName=this.slPersonMainService.get(project.getFinanceServiceMineId()).getName();
				}
			}
		}
		
		if(null!=project.getManagementConsultingMineType() && !"".equals(project.getManagementConsultingMineType())){
			if(null!=project.getManagementConsultingMineId() && !"".equals(project.getManagementConsultingMineId())){
				if(project.getManagementConsultingMineType().equals("company_ourmain")){
					managementConsultingMineName=this.companyMainService.get(project.getManagementConsultingMineId()).getCorName();
				}else{	
					managementConsultingMineName=this.slPersonMainService.get(project.getManagementConsultingMineId()).getName();
				}
			}
		}
		
		try{
			if(project.getMineType().equals("company_ourmain")){
				Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
				if(null!=o){
					mineName=o.getOrgName();
				}
				//mineName=this.companyMainService.get(project.getMineId()).getCorName();
			}else if (project.getMineType().equals("person_ourmain")){
				Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
				if(null!=o){
					mineName=o.getOrgName();
				}
				//mineName=this.slPersonMainService.get(project.getMineId()).getName();
			}
			Person p = new Person();
			//if判断是企业客户 elseif是个人客户
			Short sub=0;
			if(project.getOppositeType().equals("company_customer")){
				sub =0;
				Enterprise enterprise1= enterpriseService.getById(project.getOppositeID().intValue());
				if(enterprise1.getLegalpersonid()!=null){
					p=this.personService.getById(enterprise1.getLegalpersonid());
					map.put("person", p);
				}
				if(null != enterprise1.getHangyeType()) {
					  if(null!=areaDicService.getById(enterprise1.getHangyeType())){ 
						  enterprise1.setHangyeName(areaDicService.getById(enterprise1.getHangyeType()).getText());
					  }
				}
	            map.put("enterprise", enterprise1);
	           
			}else if(project.getOppositeType().equals("person_customer")) {
				sub = 1;
				p=this.personService.getById(project.getOppositeID().intValue());
				map.put("person", p);
				if(null!=p){
					if(null!=p.getId()){
						if(null!=p.getMarry() && p.getMarry()==317){
		        			Spouse spouse=spouseService.getByPersonId(p.getId());
		        			map.put("spouse", spouse);
		        		}
					}
				}
			}
		 if(null!=project.getOppositeID()){
			 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), sub, Short.valueOf("0"),Short.valueOf("0"));
	         if(null!=list && list.size()>0){
        		EnterpriseBank bank=list.get(0);
        		map.put("enterpriseBank", bank);
        	}
		 }
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ProjectAction:"+e.getMessage());
		}
		
		String appuers="";
		if(null!=project.getAppUserId()){
			String [] appstr=project.getAppUserId().split(",");
			Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet){
				appuers+=s.getFamilyName()+",";
			}
		}
		if(appuers.length()>0){
			appuers=appuers.substring(0, appuers.length()-1);
		}
		StringBuffer textBuffer = new StringBuffer (mineName); 
		project.setMineName(textBuffer.toString());
		project.setAppUsers(appuers);
		String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");  //	 
		if(null!=slSuperviseRecordId &&  !"".equals(slSuperviseRecordId)){
			SlSuperviseRecord slSuperviseRecord=this.slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
			map.put("slSuperviseRecord", slSuperviseRecord);
		}
		Long times = processFormService.getActvityExeTimes(runId, "slnExaminationArrangement");//是否经过审贷会
		String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
		String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
		String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
		map.put("times", times);
		//计算还款日期
/*		if(null==project.getIntentDate()||"".equals(project.getIntentDate())){
			qqqq
		}*/
		
		
		map.put("slSmallloanProject", project); 
		map.put("mineName", mineName);
		map.put("financeServiceMineName", financeServiceMineName); 
		map.put("managementConsultingMineName", managementConsultingMineName); 
		map.put("businessType", project.getBusinessType());
		map.put("isAheadPay", project.getIsAheadPay());
		map.put("businessTypeKey",businessTypeKey);
		map.put("operationTypeKey",operationTypeKey);
		map.put("definitionTypeKey",definitionTypeKey);
		map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getName());
		map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
		try {
			if(null!=project.getLoanLimit() && !project.getLoanLimit().equals("")){
				DictionaryIndependent dic=this.dictionaryIndependentService.getByDicKey(project.getLoanLimit()).get(0);
				if(null!=dic){
					map.put("smallLoanTypeKey", dic.getItemValue());
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(null!=task_id && !"".equals(task_id)){
			ProcessForm pform = processFormService.getByTaskId(task_id);
			if(pform==null){
				pform = creditProjectService.getCommentsByTaskId(task_id);
			}
			if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
				map.put("comments", pform.getComments());
			}
		}
		//add by lisl 2012-10-10 监管记录信息
	/*	if(this.getRequest().getParameter("superviseManageId")!=null){
			String smId = this.getRequest().getParameter("superviseManageId");
			if(!"".equals(smId)) {
				Long id = Long.valueOf(smId);
				SlSupervisemanage ss = slSupervisemanageService.get(id);
				map.put("slSupervisemanage", ss);
			}
		}*/
		
		//end 
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		 /*}
			  else if(type.equals("Guarantee")) //企业贷款
			   {
				   
				    GLGuaranteeloanProject project=this.glGuaranteeloanProjectService.get(Long.valueOf(taskId));
				    Map<String, Object> map = new HashMap<String, Object>();
					String mineName="";
					if(project.getMineType().equals("company_ourmain"))
					{
						mineName=this.companyMainService.get(project.getMineId()).getCorName();
					}
					else if (project.getMineType().equals("person_ourmain"))
					{
						
						mineName=this.slPersonMainService.get(project.getMineId()).getName();
					}
					try
					{
						if(project.getOppositeType().equals("company_customer")) //企业
						{
							Enterprise enterprise1= enterpriseServ.getEnterpriseById(project.getOppositeID().intValue());
							Person p=this.personManagerService.queryPersonEntity(enterprise1.getLegalpersonid());
							if(null != enterprise1.getHangyeType()) {
								  if(null!=areaDicServ.getById(enterprise1.getHangyeType())){
									  
									  enterprise1.setHangyeName(areaDicServ.getById(enterprise1.getHangyeType()).getText());
								  }
							}
				            map.put("enterprise", enterprise1);
				            map.put("person", p);
						}
						else if(project.getOppositeType().equals("person_customer")) //个人
						{
							Person p=this.personManagerService.queryPersonEntity(project.getOppositeID().intValue());
							map.put("person", p);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						logger.error("ProjectAction:"+e.getMessage());
					}
					StringBuffer textBuffer = new StringBuffer (mineName); 
					project.setMineName(textBuffer.toString());
					if(null!=project.getLoanRate() && project.getLoanRate().doubleValue()==0){
						  project.setLoanRate(null);
					}
					map.put("gLGuaranteeloanProject", project);
					String appUsersOfA="";
					String appUsersOfB="";
					if(null!=project.getAppUserIdOfA() && !"".equals(project.getAppUserIdOfA()))
					{
						String [] appstr=project.getAppUserIdOfA().split(",");
						Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
						for(AppUser s:userSet){
							appUsersOfA+=s.getFamilyName()+",";
						}
						appUsersOfA = appUsersOfA.substring(0, appUsersOfA.length()-1);
					}
					if(null!=project.getAppUserIdOfB() && !"".equals(project.getAppUserIdOfB()) )
					{
						String [] appstr=project.getAppUserIdOfB().split(",");
						Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
						for(AppUser s:userSet){
							appUsersOfB+=s.getFamilyName()+",";
						}
						appUsersOfB = appUsersOfB.substring(0, appUsersOfB.length()-1);
					}
					map.put("appUsersOfA",appUsersOfA);
					map.put("appUsersOfB",appUsersOfB);
					map.put("businessType", project.getBusinessType());
					map.put("businessTypeKey",this.globalTypeService.getByNodeKey(project.getBusinessType()).get(0).getTypeName());
					map.put("operationTypeKey",this.globalTypeService.getByNodeKey(project.getOperationType()).get(0).getTypeName());
					map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getDescription());
					map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
					map.put("oppositeTypeKey",this.dictionaryIndependentService.getByDicKey(project.getOppositeType()).get(0).getItemValue());
					if(null!=task_id && !"".equals(task_id)){
						ProcessForm pform = processFormService.getByTaskId(task_id);
						if(pform==null){
							pform = creditProjectService.getCommentsByTaskId(task_id);
						}
						if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
							map.put("comments", pform.getComments());
						}
						GlFlownodeComments flowNode = glFlownodeCommentsService.getByFormId(pform.getFormId());
						if(flowNode!=null){
							map.put("glFlownodeComments", flowNode);
						}
					}
					CustomerBankRelationPerson  bankRelationPerson=new CustomerBankRelationPerson();
					if(null!=project.getBankId() && !project.getBankId().equals("")){
						String bankName="";
						String fenBankName="";
						bankRelationPerson=this.bankRelationPersonService.queryPerson(project.getBankId());
						List<AreaDic> list1=bankRelationPersonService.findBank(bankRelationPerson.getBankid());
						bankName=list1.get(0).getText();
						if(null!=bankRelationPerson.getFenbankid() && !bankRelationPerson.getFenbankid().equals(""))
						{  
							List<AreaDic> list2=bankRelationPersonService.findBank(bankRelationPerson.getFenbankid());
							if(null!=list2 &&list2.size()>0){
								fenBankName=list2.get(0).getRemarks();
								bankName=fenBankName;
								if(bankName==""){
									fenBankName=list2.get(0).getText();
									bankName=bankName+"_"+fenBankName;
								}
							}
						}
						bankRelationPerson.setBankName(bankName);
					}
					map.put("customerBankRelationPerson", bankRelationPerson);
					
					//add by lu 2012.06.21 中铭常规流程审保会集体决议各种意见与说明的查询。--start--
					if(flowTaskId!=null&&!"".equals(flowTaskId)){
						ProcessForm processForm = processFormService.getByTaskId(flowTaskId);
						if(processForm!=null){
							GlFlownodeComments flowNode = glFlownodeCommentsService.getByFormId(processForm.getFormId());
							if(flowNode!=null){
								map.put("glFlownodeComments", flowNode);
							}
							map.put("comments", processForm.getComments());
						}
					}
					//--end--
					
					
					StringBuffer buff = new StringBuffer("{success:true,data:");
					JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
					buff.append(json.serialize(map));
					buff.append("}");
					jsonString=buff.toString();
			   } 
			   else if(type.equals("Financing")) //融资项目
			   {
				   //String projectId=this.getRequest().getParameter("projectId"); //项目ID
					//String businessType=this.getRequest().getParameter("businessType");  //	 
				 try{	
					FlFinancingProject project=this.flFinancingProjectService.get(Long.valueOf(taskId));
					Map<String, Object> map = new HashMap<String, Object>();
					String mineName="";
					if(project.getMineType().equals("company_ourmain")){
						mineName=this.companyMainService.get(project.getMineId()).getCorName();
					}else if (project.getMineType().equals("person_ourmain")){
						mineName=this.slPersonMainService.get(project.getMineId()).getName();
					}
					try{
						if(project.getOppositeType().equals("company_customer")){//企业
							Enterprise enterprise1= enterpriseServ.getEnterpriseById(project.getOppositeID().intValue());
							Person p=this.personManagerService.queryPersonEntity(enterprise1.getLegalpersonid());
							if(null != enterprise1.getHangyeType()) {
								  if(null!=areaDicServ.getById(enterprise1.getHangyeType())){
									  
									  enterprise1.setHangyeName(areaDicServ.getById(enterprise1.getHangyeType()).getText());
								  }
							}
				            map.put("enterprise", enterprise1);
				            map.put("person", p);
						}else if(project.getOppositeType().equals("person_customer")){//个人
							Person p=this.personManagerService.queryPersonEntity(project.getOppositeID().intValue());
							map.put("person", p);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						logger.error("ProjectAction:"+e.getMessage());
					}
					String appuers="";
					if(null!=project.getAppUserId())
					{
						String [] appstr=project.getAppUserId().split(",");
						Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
						for(AppUser s:userSet){
							appuers+=s.getFamilyName()+",";
						}
						
					}
					if(appuers.length()>0)
					appuers=appuers.substring(0, appuers.length()-1);
					StringBuffer textBuffer = new StringBuffer (mineName); 
					project.setMineName(textBuffer.toString());
					project.setAppUsers(appuers);
					map.put("flFinancingProject", project);
					map.put("businessType", project.getBusinessType());
					map.put("businessTypeKey",this.globalTypeService.getByNodeKey(project.getBusinessType()).get(0).getTypeName());
					map.put("operationTypeKey",this.globalTypeService.getByNodeKey(project.getOperationType()).get(0).getTypeName());
					map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getDescription());
					map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
					if(null!=task_id && !"".equals(task_id)){
						ProcessForm pform = processFormService.getByTaskId(task_id);
						if(pform==null){
							pform = creditProjectService.getCommentsByTaskId(task_id);
						}
						if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
							map.put("comments", pform.getComments());
						}
					}
					if(null!=task_id && !"".equals(task_id)){
						map.put("comments", this.processFormService.getByTaskId(task_id).getComments());
					}
					//map.put("isAheadPay", project.getIsAheadPay());
					StringBuffer buff = new StringBuffer("{success:true,data:");
					JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
					buff.append(json.serialize(map));
					buff.append("}");
					jsonString=buff.toString();
				 }
				 catch (Exception e) {
					e.printStackTrace();
				  }
			   } else if(type.equals("BeNotFinancing")) //企业贷款
			   {
				   
				   SgLawsuitguaranteeProject project=this.sgLawsuitguaranteeProjectService.get(Long.valueOf(taskId));
				    Map<String, Object> map = new HashMap<String, Object>();
					String mineName="";
					if(project.getMineType().equals("company_ourmain"))
					{
						mineName=this.companyMainService.get(project.getMineId()).getCorName();
					}
					else if (project.getMineType().equals("person_ourmain"))
					{
						
						mineName=this.slPersonMainService.get(project.getMineId()).getName();
					}
					try
					{
						if(project.getOppositeType().equals("company_customer")) //企业
						{
							Enterprise enterprise1= enterpriseServ.getEnterpriseById(project.getOppositeID().intValue());
							Person p=this.personManagerService.queryPersonEntity(enterprise1.getLegalpersonid());
							if(null != enterprise1.getHangyeType()) {
								  if(null!=areaDicServ.getById(enterprise1.getHangyeType())){
									  
									  enterprise1.setHangyeName(areaDicServ.getById(enterprise1.getHangyeType()).getText());
								  }
							}
				            map.put("enterprise", enterprise1);
				            map.put("person", p);
						}
						else if(project.getOppositeType().equals("person_customer")) //个人
						{
							Person p=this.personManagerService.queryPersonEntity(project.getOppositeID().intValue());
							map.put("person", p);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						logger.error("ProjectAction:"+e.getMessage());
					}
					StringBuffer textBuffer = new StringBuffer (mineName); 
					project.setMineName(textBuffer.toString());
				
					map.put("sgLawsuitguaranteeProject", project);
					String appUsers="";
					if(null!=project.getAppUserId() && !"".equals(project.getAppUserId()))
					{
						String [] appstr=project.getAppUserId().split(",");
						Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
						for(AppUser s:userSet){
							appUsers+=s.getFamilyName()+",";
						}
						appUsers = appUsers.substring(0, appUsers.length()-1);
					}
					
					map.put("appUsers",appUsers);
					map.put("businessType", project.getBusinessType());
					map.put("businessTypeKey",this.globalTypeService.getByNodeKey(project.getBusinessType()).get(0).getTypeName());
					map.put("operationTypeKey",this.globalTypeService.getByNodeKey(project.getOperationType()).get(0).getTypeName());
					map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getDescription());
					map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
					map.put("oppositeTypeKey",this.dictionaryIndependentService.getByDicKey(project.getOppositeType()).get(0).getItemValue());
					if(null!=task_id && !"".equals(task_id)){
						ProcessForm pform = processFormService.getByTaskId(task_id);
						if(pform==null){
							pform = creditProjectService.getCommentsByTaskId(task_id);
						}
						if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
							map.put("comments", pform.getComments());
						}
						GlFlownodeComments flowNode = glFlownodeCommentsService.getByFormId(pform.getFormId());
						if(flowNode!=null){
							map.put("glFlownodeComments", flowNode);
						}
					}
					
					StringBuffer buff = new StringBuffer("{success:true,data:");
					JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
					buff.append(json.serialize(map));
					buff.append("}");
					jsonString=buff.toString();
			   } 
		}*/
		return SUCCESS;
	}
	
	
	/**
	 * 读取信贷监管流程信息
	 * 
	 */
	public String getCreditLoanProjectInfo(){
		String sProjectId=this.getRequest().getParameter("sProjectId"); //项目ID
		String task_id=this.getRequest().getParameter("task_id");  //	任务ID 
		SlSmallloanProject project=this.slSmallloanProjectService.get(Long.valueOf(sProjectId));
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			if (null != project.getProductId() && !"".equals(project.getProductId())) {
				BpProductParameter bpProductParameter = bpProductParameterService.get(project.getProductId());
				if(null!=bpProductParameter){
					map.put("bpProductParameter", bpProductParameter);
				}
			}
			
			Person p = new Person();
			//if判断是企业客户 elseif是个人客户
			Short sub=0;
			if(project.getOppositeType().equals("company_customer")){
				sub =0;
				Enterprise enterprise1= enterpriseService.getById(project.getOppositeID().intValue());
				if(enterprise1.getLegalpersonid()!=null){
					p=this.personService.getById(enterprise1.getLegalpersonid());
					map.put("person", p);
				}
				if(null != enterprise1.getHangyeType()) {
					  if(null!=areaDicService.getById(enterprise1.getHangyeType())){ 
						  enterprise1.setHangyeName(areaDicService.getById(enterprise1.getHangyeType()).getText());
					  }
				}
	            map.put("enterprise", enterprise1);
	           
			}else if(project.getOppositeType().equals("person_customer")) {
				sub = 1;
				p=this.personService.getById(project.getOppositeID().intValue());
				map.put("person", p);
				if(null!=p){
					if(null!=p.getId()){
						if(null!=p.getMarry() && p.getMarry()==317){
		        			Spouse spouse=spouseService.getByPersonId(p.getId());
		        			map.put("spouse", spouse);
		        		}
					}
				}
			}
		 if(null!=project.getOppositeID()){
			 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), sub, Short.valueOf("0"),Short.valueOf("0"));
	         if(null!=list && list.size()>0){
        		EnterpriseBank bank=list.get(0);
        		map.put("enterpriseBank", bank);
        	}
		 }
		 if(null!=project.getFinanceServiceMineType() && !"".equals(project.getFinanceServiceMineType())){
			 String financeServiceMineName="";
				if(null!=project.getFinanceServiceMineId() && !"".equals(project.getFinanceServiceMineId())){
					if(project.getFinanceServiceMineType().equals("company_ourmain")){
						financeServiceMineName=this.slCompanyMainService.get(project.getFinanceServiceMineId()).getCorName();
					}else{
						financeServiceMineName=this.slPersonMainService.get(project.getFinanceServiceMineId()).getName();
					}
				}
			}
			
			if(null!=project.getManagementConsultingMineType() && !"".equals(project.getManagementConsultingMineType())){
				String managementConsultingMineName="";
				if(null!=project.getManagementConsultingMineId() && !"".equals(project.getManagementConsultingMineId())){
					if(project.getManagementConsultingMineType().equals("company_ourmain")){
						managementConsultingMineName=this.slCompanyMainService.get(project.getManagementConsultingMineId()).getCorName();
					}else{	
						managementConsultingMineName=this.slPersonMainService.get(project.getManagementConsultingMineId()).getName();
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ProjectAction:"+e.getMessage());
		}
		
		String appuers="";
		if(null!=project.getAppUserId()){
			String [] appstr=project.getAppUserId().split(",");
			Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet){
				appuers+=s.getFamilyName()+",";
			}
		}
		if(appuers.length()>0){
			appuers=appuers.substring(0, appuers.length()-1);
		}
		project.setAppUsers(appuers);
		String superviseManageId=this.getRequest().getParameter("superviseManageId");  //	 
		if(null!=superviseManageId &&  !"".equals(superviseManageId)){
			GlobalSupervisemanage globalSupervisemanage=globalSupervisemanageService.get(Long.valueOf(superviseManageId));
			//map.put("globalSupervisemanage", globalSupervisemanage);
			if(null!=globalSupervisemanage.getProjectId()){
				BpFundProject ownBpFundProject=bpFundProjectService.get(globalSupervisemanage.getProjectId());
				map.put("ownBpFundProject", ownBpFundProject);
			}
		}
	
		map.put("slSmallloanProject", project); 
		
		if(null!=task_id && !"".equals(task_id)){
			ProcessForm pform = processFormService.getByTaskId(task_id);
			if(pform==null){
				pform = creditProjectService.getCommentsByTaskId(task_id);
			}
			if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
				map.put("comments", pform.getComments());
			}
		}
	
		
		//end 
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	public String getAllInfo(){		
		String fundProjectId=this.getRequest().getParameter("fundProjectId"); //项目ID
		Map<String, Object> map = new HashMap<String, Object>();
		BpFundProject ownBpFundProject=bpFundProjectService.get(Long.valueOf(fundProjectId));
		map.put("ownBpFundProject", ownBpFundProject);
		
		SlSmallloanProject project=this.slSmallloanProjectService.get(ownBpFundProject.getProjectId());
					
		String financeServiceMineName="";
		String managementConsultingMineName="";
		if(null!=project.getFinanceServiceMineType() && !"".equals(project.getFinanceServiceMineType())){
			if(null!=project.getFinanceServiceMineId() && !"".equals(project.getFinanceServiceMineId())){
					if(project.getFinanceServiceMineType().equals("company_ourmain")){
						financeServiceMineName=this.companyMainService.get(project.getFinanceServiceMineId()).getCorName();
					}else{
						financeServiceMineName=this.slPersonMainService.get(project.getMineId()).getName();
					}
			
			}
		}
		
		if(null!=project.getManagementConsultingMineType() && !"".equals(project.getManagementConsultingMineType())){
			if(null!=project.getManagementConsultingMineId() && !"".equals(project.getManagementConsultingMineId())){
				if(project.getManagementConsultingMineType().equals("company_ourmain")){
					managementConsultingMineName=this.companyMainService.get(project.getManagementConsultingMineId()).getCorName();
				}else{	
					managementConsultingMineName=this.slPersonMainService.get(project.getManagementConsultingMineId()).getName();
				}
			}
		}
		Person p = new Person();
		//if判断是企业客户 elseif是个人客户
		Short isE=0;
		if(project.getOppositeType().equals("company_customer")){
			isE=0;
			Enterprise enterprise1= enterpriseService.getById(project.getOppositeID().intValue());
			
			if(null != enterprise1.getHangyeType()) {
				  if(null!=areaDicService.getById(enterprise1.getHangyeType())){ 
					  enterprise1.setHangyeName(areaDicService.getById(enterprise1.getHangyeType()).getText());
				  }
			}
            map.put("enterprise", enterprise1);
            if(null!=enterprise1.getLegalpersonid()){
	            p=this.personService.getById(enterprise1.getLegalpersonid());
	            map.put("person", p);
            }
		}else if(project.getOppositeType().equals("person_customer")) {
			isE=1;
			p=this.personService.getById(project.getOppositeID().intValue());
			map.put("person", p);
			if(null!=p){
				if(null!=p.getId()){
					if(null!=p.getMarry() && p.getMarry()==317){
	        			Spouse spouse=spouseService.getByPersonId(p.getId());
	        			map.put("spouse", spouse);
	        		}
				}
			}
		}
		
		if (null != project.getProductId() && !"".equals(project.getProductId())) {
			BpProductParameter bpProductParameter = bpProductParameterService.get(project.getProductId());
			if(null!=bpProductParameter){
				map.put("bpProductParameter", bpProductParameter);
			}
			if("信贷业务".equals(project.getFlowType())){
				P2pLoanProduct p2pProduct=p2pLoanProductService.get(project.getProductId());
				if(null!=p2pProduct){
					map.put("bpProductParameter", p2pProduct);
				}
			}
		}

		
		List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), isE, Short.valueOf("0"),Short.valueOf("0"));
		if(null!=list && list.size()>0){
			EnterpriseBank bank=list.get(0);
			map.put("enterpriseBank", bank);
		}			
		map.put("slSmallloanProject", project); 
		map.put("financeServiceMineName", financeServiceMineName); 
		map.put("managementConsultingMineName", managementConsultingMineName); 
		
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
	
		return SUCCESS;
	
	}
	
	//编辑展期项目的信息查询
	public String getPostponedInfo(){
		
		String projectId = this.getRequest().getParameter("projectId");
		if(projectId!=null&&!"".equals(projectId)){
			SlSmallloanProject project = slSmallloanProjectService.get(Long.valueOf(projectId));
			if(project!=null){
				try{
					Map<String, Object> map = new HashMap<String, Object>();
					String mineName="";
					String financeServiceMineName="";
					String managementConsultingMineName="";
					if(null!=project.getFinanceServiceMineType() && !"".equals(project.getFinanceServiceMineType())){
						if(null!=project.getFinanceServiceMineId() && !"".equals(project.getFinanceServiceMineId())){
							if(project.getFinanceServiceMineType().equals("company_ourmain")){
								financeServiceMineName=this.companyMainService.get(project.getFinanceServiceMineId()).getCorName();
							}else{
								financeServiceMineName=this.slPersonMainService.get(project.getFinanceServiceMineId()).getName();
							}
						}
					}
					if(null!=project.getManagementConsultingMineType() && !"".equals(project.getManagementConsultingMineType())){
						if(null!=project.getManagementConsultingMineId() && !"".equals(project.getManagementConsultingMineId())){
							if(project.getManagementConsultingMineType().equals("company_ourmain")){
								managementConsultingMineName=this.companyMainService.get(project.getManagementConsultingMineId()).getCorName();
							}else{	
								managementConsultingMineName=this.slPersonMainService.get(project.getManagementConsultingMineId()).getName();
							}
						}
					}
					
					
					if(project.getMineType().equals("company_ourmain")){
						mineName=this.companyMainService.get(project.getMineId()).getCorName();
						/*Organization o = (Organization) creditBaseDao.getById(Organization.class, project.getMineId());
						if(null!=o){
							mineName=o.getOrgName();
						}*/
					}else if (project.getMineType().equals("person_ourmain")){
						/*Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
						if(null!=o){
							mineName=o.getOrgName();
						}*/
						mineName=this.slPersonMainService.get(project.getMineId()).getName();
					}
					
					if(project.getOperationType()!=null&&project.getOperationType().equals("MicroLoanBusiness")){
						Person	p = this.personService.getById(project.getOppositeID().intValue());
						map.put("person", p);
					}else{
						Person p = new Person();
						//if判断是企业客户 elseif是个人客户
						Short isE=0;
						if(project.getOppositeType().equals("company_customer")){
							isE=0;
							Enterprise enterprise1= enterpriseService.getById(project.getOppositeID().intValue());
							
							if(null != enterprise1.getHangyeType()) {
								  if(null!=areaDicService.getById(enterprise1.getHangyeType())){ 
									  enterprise1.setHangyeName(areaDicService.getById(enterprise1.getHangyeType()).getText());
								  }
							}
				            map.put("enterprise", enterprise1);
				            if(null!=enterprise1.getLegalpersonid()){
					            p=this.personService.getById(enterprise1.getLegalpersonid());
					            map.put("person", p);
				            }
						}else if(project.getOppositeType().equals("person_customer")) {
							isE=1;
							p=this.personService.getById(project.getOppositeID().intValue());
							map.put("person", p);
						}
						 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(),isE, Short.valueOf("0"),Short.valueOf("0"));
				         if(null!=list && list.size()>0){
			        		EnterpriseBank bank=list.get(0);
			        		map.put("enterpriseBank", bank);
			        	}
					}
					
					String appuers="";
					if(null!=project.getAppUserId())
					{
						String [] appstr=project.getAppUserId().split(",");
						Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
						for(AppUser s:userSet){
							appuers+=s.getFullname()+",";
						}
						
					}
					if(appuers.length()>0){
						appuers=appuers.substring(0, appuers.length()-1);
					}
					project.setAppUsers(appuers);
					project.setAppUserName(appuers);
					StringBuffer textBuffer = new StringBuffer (mineName); 
					project.setMineName(managementConsultingMineName);
					String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");  //	 
					if(null!=slSuperviseRecordId &&  !"".equals(slSuperviseRecordId)){
						SlSuperviseRecord slSuperviseRecord=this.slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
						map.put("slSuperviseRecord", slSuperviseRecord);
					}
					String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
					String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
					String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
					map.put("slSmallloanProject", project); 
					map.put("mineName", mineName);
					map.put("financeServiceMineName", financeServiceMineName); 
					map.put("managementConsultingMineName", managementConsultingMineName); 
					map.put("businessType", project.getBusinessType());
					map.put("businessTypeKey",businessTypeKey);
					map.put("operationTypeKey",operationTypeKey);
					map.put("definitionTypeKey",definitionTypeKey);
					map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getName());
					map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
					
					StringBuffer buff = new StringBuffer("{success:true,data:");
					JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
					buff.append(json.serialize(map));
					buff.append("}");
					jsonString=buff.toString();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return SUCCESS;
	}
	
	//根据taskId获取项目名称-处理人名字-任务分配时间-任务完成时限 add by lu 2011.12.14
	public String getTaskInfo(){
		
		String  projectName="";
		String s=taskId;
		String dueDateTime = "";
		TaskImpl task=(TaskImpl)taskService.getTask(taskId);
		if(businessType.equals("SmallLoan")){
			SlSmallloanProject sl = slSmallloanProjectService.get(projectId);
			if(null!=sl){
				projectName = sl.getProjectName();
			}
		}else if(businessType.equals("Guarantee")){
			GLGuaranteeloanProject gl = glGuaranteeloanProjectService.get(projectId);
			if(null!=gl){
				projectName = gl.getProjectName();
			}
		}else if("Financing".equals(businessType)){
			FlFinancingProject fp = flFinancingProjectService.get(projectId);
			if(fp!=null){
				projectName = fp.getProjectName();
			}
		}else if("LeaseFinance".equals(businessType)){
			FlLeaseFinanceProject fp = flLeaseFinanceProjectService.get(projectId);
			if(fp!=null){
				projectName = fp.getProjectName();
			}
		}
		if(task!=null&&task.getDuedate()!=null){
			dueDateTime = task.getDuedate().toString().substring(0,19);
		}else{
			dueDateTime = "任务期限没有设置,请联系管理员进行设置!";
		}
		AppUser user=ContextUtil.getCurrentUser();
		jsonString = "{success:true,data:{projectName:'"+projectName+"',createTime:'"+task.getCreateTime().toString().substring(0,19)+"',dueTime:'"+dueDateTime+"',creator:'"+user.getFullname()+"'}}" ;
		return SUCCESS;
	}
	
	/**
	 * 显示会签情况列表
	 * @author lisl
	 * @return
	 */
	public String signList() {
		// 取得已经投票的会签情况
		List<TaskSignData> signDataList = taskSignDataService.getByRunId(runId);
		for(TaskSignData tsd : signDataList) {
			//ProcessForm pf = processFormService.getByRunIdFromTaskIdCreatorId(runId, tsd.getFromTaskId(), tsd.getVoteId());
			ProcessForm pf = null;
			if(tsd.getFormId()!=null&&!"".equals(tsd.getFormId())&&!"0".equals(tsd.getFormId().toString())){
				pf = processFormService.get(tsd.getFormId());
			}else{
				pf = processFormService.getByRunIdFromTaskIdCreatorId(tsd.getRunId(), tsd.getFromTaskId(), tsd.getVoteId());
			}
			tsd.setComments(pf.getComments());
		}
		// 取得尚未投票的人员
		List<String> taskIdList = processFormService.getByRunIdActivityName(runId, "slnExaminationArrangement", Short.valueOf("0"));
		if(taskIdList.size() > 0) {
			for (String taskId : taskIdList) {
				TaskSignData data = new TaskSignData();
				Long userId = Long.valueOf(flowTaskService.get(Long.valueOf(taskId)).getAssignee());
				AppUser user = appUserService.get(userId);
				data.setVoteName(user.getFullname());
				// 在前台界面同时也显示尚未投票的人员
				signDataList.add(data);
			}
		}
		if(signDataList!=null&&signDataList.size()!=0){
			for(int i=0;i<signDataList.size();i++){
				TaskSignData tdata = signDataList.get(i);
				//ProcessForm pf = processFormService.getByRunIdFromTaskIdCreatorId(runId, tdata.getFromTaskId(), tdata.getVoteId());
				ProcessForm pform = null;
				if(tdata.getFormId()!=null&&!"".equals(tdata.getFormId())&&!"0".equals(tdata.getFormId().toString())){
					pform = processFormService.get(tdata.getFormId());
				}else{
					pform = processFormService.getByRunIdFromTaskIdCreatorId(tdata.getRunId(), tdata.getFromTaskId(), tdata.getVoteId());
				}
				if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
					tdata.setComments(pform.getComments());
				}
			}
		}
		Type type=new TypeToken<List<TaskSignData>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,result:");	
		JSONSerializer json = JsonUtil.getJSONSerializer();
		buff.append(json.serialize(signDataList));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 显示意见与说明记录
	 * @return
	 * @author lisl
	 */
	public String runList() {
		
		String filterableNodeKeys = getRequest().getParameter("filterableNodeKeys");
		if(taskId!=null&&!"".equals(taskId)){//节点页面加载意见与说明的参数。不是节点页面的方法则taskId为null。
			ProcessForm processForm = processFormService.getByTaskId(taskId);
			if(processForm!=null){
				runId = processForm.getRunId();
			}else{//针对追回的情况,追回的任务在任务表存在taskId,而在processForm中不存在,通过ExecutionId获取对应的runId。
				Task task = taskService.getTask(taskId);
				if(task!=null&&task.getExecutionId()!=null){
					ProcessRun run = processRunService.getByPiId(task.getExecutionId());
					if(run!=null){
						runId = run.getRunId();
					}
				}
			}
		}
		ProcessRun processRun = processRunService.get(runId);
		if(processRun!=null){
			List<ProcessForm> pfList=processFormService.getCommentsByRunId(runId,filterableNodeKeys);
			
			//将带有html格式的文本输出为纯文本
			/*int len=pfList.size();
			for(int i=0 ;i<len;i++){
				pfList.get(i).setComments(StringUtil.html2Text(pfList.get(i).getComments()));
			}*/
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pfList==null?0:pfList.size()).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer();
			buff.append(json.serialize(pfList));
			buff.append("}");
			jsonString = buff.toString();
			System.out.println(jsonString);
		}
		return SUCCESS;
		/*
		
		List pfList = null;
		List<ProcessForm> pfList = new ArrayList<ProcessForm>();
		String businessType = "";
		Long projectId = new Long(0);
		
		//先判断是否是小贷流程,如果是则判断是否有展期项目。add by lu 2012.04.24
		ProcessRun processRun = processRunService.get(runId);
		if(processRun!=null){
			businessType = processRun.getBusinessType();
			projectId = processRun.getProjectId();
			if("SmallLoan".equals(businessType)){
				List<ProcessRun> list = processRunService.getByProcessNameProjectId(projectId, businessType, "smallLoanPostponedFlow");
				if(list!=null&&list.size()!=0){
					String runIds = processRun.getRunId().toString()+",";
					int i=0;
					for(int k=0;k<list.size();k++){
						if(i++>0){
							runIds+=",";
						}
						runIds+=list.get(k).getRunId();
					}
					pfList=processFormService.getByRunId(runIds,safeLevel);
				}else{
					pfList=processFormService.getByRunId(runId,safeLevel);
				}
			}else{
				pfList=processFormService.getByRunId(runId,safeLevel);
			}
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pfList.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		buff.append(json.serialize(pfList));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	*/}
	
	/**
	 * 终止项目
	 * @return
	 * @author lisl
	 */
	public String end() {
		String taskId = getRequest().getParameter("taskId");
		String runId = getRequest().getParameter("runId");
		String projectId = getRequest().getParameter("projectId");
		String businessType = getRequest().getParameter("businessType");
		String comments = getRequest().getParameter("comments");
		String type = getRequest().getParameter("type");//用于区分是修改sl_smallloan_project还是bp_fund_project
		
		if(taskId!=null&&!"".equals(taskId)&&!"null".equals(taskId)){
			//判断是否为追回的情况，是的话通过以下方法获取新的taskId
			String newTaskId = creditProjectService.getNewTaskId(taskId.toString());
			if(newTaskId!=null&&!"".equals(newTaskId)){
				taskId = newTaskId;
			}
		}
		
		creditProjectService.updateProcessRunStatus(taskId,Long.valueOf(runId),projectId,businessType,comments,type);
		return SUCCESS;
	}
	
	/**
	 * 完成项目
	 * @author lisl
	 */
	public String complate() {
		String projectId = getRequest().getParameter("projectId");
		creditProjectService.complateFlProject(Long.valueOf(projectId));
		return SUCCESS;
	}
	 /**
	 * 删除任务及相关数据方法
	 * @param taskId
	 * @param runId
	 */
	
	public String removeByRunId(){
		String url = super.getRequest().getRealPath("/");//获取服务器路径：删除合同相关的文件以及附件等。
		String runId=getRequest().getParameter("runId");
		boolean isExistActual = false;//判断费用是否对过帐
		boolean isExistIntent = false;//判断款项是否对过帐
		//boolean isBeforeTask = false;//判断是否在某个重要节点前后,目前各个流程定义该重要节点为合同制作或者签署节点,由设置流程信息部分设置。值为：1000(分界点)
		if(runId!=null){
			ProcessRun processRun = processRunService.get(new Long(runId));
			if(processRun!=null){
				String businessType = processRun.getBusinessType();
				Long projectId = processRun.getProjectId();
				
				/*String deployId = processRun.getProDefinition().getDeployId();
				if(deployId!=null&&!"".equals(deployId)){
					ProUserAssign proUserAssign = proUserAssignService.getByDeployIdActivityName(deployId, activityName);
					if(proUserAssign!=null&&proUserAssign.getIsJumpToTargetTask()!=null){
						Long taskSequence = proUserAssign.getTaskSequence();
						if(taskSequence>=1000){//当前的任务在某主要节点之后
							isBeforeTask = true;
						}
					}
				}*/
				
				//if(!isBeforeTask){//当前的任务在某主要节点之前
			List<SlFundIntent> listIntent = slFundIntentService.getByProjectId2(projectId, businessType);
				if(listIntent!=null&&listIntent.size()!=0){
					for(int i=0;i<listIntent.size();i++){
						SlFundIntent sl = listIntent.get(i);
						Long fundIntentId = sl.getFundIntentId();
						List<SlFundDetail> listFundDetail = slFundDetailService.getlistbySlFundIntentId(fundIntentId,"1");
						if(listFundDetail!=null&&listFundDetail.size()!=0){
							isExistIntent = true;
							break;
						}
					} 
				}
				//}
				
				if(!isExistIntent){//如果款项对过帐就不再执行以下判断：费用是否对过帐。否则执行。
					List<SlActualToCharge> listCharge = slActualToChargeService.listbyproject(projectId, businessType);
					if(listCharge!=null&&listCharge.size()!=0){
						for(int j=0;j<listCharge.size();j++){
							SlActualToCharge slCharge = listCharge.get(j);
							Long actualChargeId = slCharge.getActualChargeId();
							List<SlChargeDetail> listDetail = slChargeDetailService.getlistbyActualChargeId(actualChargeId);
							if(listDetail!=null&&listDetail.size()!=0){
								isExistActual = true;
								break;
							}
						}
					}
				}
				
				if(isExistIntent||isExistActual){
					jsonString = "{success:false}";
				}/*else if(isBeforeTask){
					jsonString = "{isBeforeTask:false}";
				}*/else{
					int result = creditProjectService.removeByRunId(new Long(runId),url);
					if(result==-1){
						jsonString = "{deleteError:false}";
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 删除项目子流程,不删除业务数据
	 * @return
	 */
	public String removeBranchFlow(){
		String projectId = getRequest().getParameter("projectId");
		String businessType = getRequest().getParameter("businessType");
		String processName = getRequest().getParameter("processName");
		if(projectId!=null&&businessType!=null&&processName!=null&&!"".equals(projectId)&&!"".equals(businessType)&&!"".equals(businessType)){
			List<ProcessRun> list = processRunService.getByProcessNameProjectId(Long.valueOf(projectId), businessType, processName);
			for(ProcessRun run : list){
				creditProjectService.removeTasksByRunObject(run);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 判断是否存在并列任务，如果存在多条任务，返回任务列表
	 * @author lisl
	 * @param taskId
	 * @param runId
	 */
	public String isExistSynchronousTasksByRunId(){
		
		//boolean isNormalTask = false;
		VCommonProjectFlow vcp = vCommonProjectFlowService.get(runId);
		if(vcp!=null){
			Long piDbid = vcp.getPiDbid();
			List<TaskInfo> tasks = flowTaskService.getSynchronousTasksByRunId(piDbid);
			if(tasks!=null&&tasks.size()==1){//正常任务：不为空并且只有一条任务。
				jsonString = "{success:true}";
			}else{
				jsonString = "{success:false}";
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 得到并列任务列表
	 * @return
	 */
	public String getSynchronousTasksByRunId(){
		List<Map> allList=new ArrayList<Map>();
		int counts = 0;
		VCommonProjectFlow vcp = vCommonProjectFlowService.get(runId);
		Long piDbid = vcp.getPiDbid();
		List<TaskInfo> tasks = flowTaskService.getSynchronousTasksByRunId(piDbid);
		for(int i=0;i<tasks.size();i++){
			Map<String, Object> map = new HashMap<String, Object>();
			TaskInfo info = tasks.get(i);
			if(info.getExecutionId()==null){//排除父任务。
				counts++;
				map.put("task", info);
    			map.put("vCommonProjectFlow", vcp);
    			allList.add(map);
			}
		}
		StringBuffer buff = new StringBuffer("{success:false,'totalCounts':");
    	buff.append(counts);
    	buff.append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd HH:mm:ss");
		buff.append(json.serialize(allList));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 项目详细页面得到审贷会方式
	 * @author lisl
	 * @return
	 */
	public String getMeetingType() {
		String meetingType = "0";

		Long times = processFormService.getActvityExeTimes(runId, "slnExaminationArrangement");
		Long times1 = processFormService.getActvityExeTimes(runId, "slnUploadMeettingSummary");
		Long times2 = processFormService.getActvityExeTimes(runId, "zmnSbhDraftResolution");

		if((times !=0 ||times2 !=0)&& times1 ==0) {//审贷会方式为线上
			meetingType = "1";
		}else if ((times ==0 ||times2 ==0) && times1 !=0) {//审贷会方式为线下
			meetingType = "2";
		}else if ((times !=0 ||times2 !=0) && times1 !=0) {//两者都有
			meetingType = "3";
		}else{//两者都没有
			meetingType = "0"; 
		}
		setJsonString("{type:"+meetingType+"}");
		return SUCCESS;
	}
	/**
	 * 担保项目详细页面得到审贷会方式
	 * @author gaoqingrui
	 * @return
	 */
	public String getGLMeetingType() {
		String meetingType = "0";

		Long times = processFormService.getActvityExeTimes(runId, "onlineJudgement");//线上评审会决议
		Long times1 = processFormService.getActvityExeTimes(runId, "collectDiscussComment");//汇总评审会决议及纪要

		if(times !=0 && times1 ==0) {//线上评审会决议
			meetingType = "1";
		}else if (times ==0  && times1 !=0) {//汇总评审会决议及纪要
			meetingType = "2";
		}else if(times !=0 && times1 !=0){
			meetingType = "3";
		}else{//两者都没有
			meetingType = "0"; 
		}
		setJsonString("{type:"+meetingType+"}");
		return SUCCESS;
	}
	/**
	 * 融资租赁项目详细页面得到审贷会方式
	 * @author gaoqingrui
	 * @return
	 */
	public String getFlMeetingType() {
		String meetingType = "0";
		Long times = processFormService.getActvityExeTimes(runId, "onlineJudgement");//线上评审会决议
		Long times1 = processFormService.getActvityExeTimes(runId, "collectDiscussComment");//汇总评审会决议及纪要

		if(times !=0 && times1 ==0) {//线上评审会决议
			meetingType = "1";
		}else if (times ==0  && times1 !=0) {//汇总评审会决议及纪要
			meetingType = "2";
		}else if(times !=0 && times1 !=0){
			meetingType = "3";
		}else{//两者都没有
			meetingType = "0"; 
		}
		setJsonString("{type:"+meetingType+"}");
		return SUCCESS;
	}
	public String printfkWorkOrder(){
		String listids=this.getRequest().getParameter("ids");
		String reportKey=this.getRequest().getParameter("reportKey");
		ReportTemplate rt=this.reportTemplateService.getReportTemplateByKey(reportKey);
		String path=rt.getReportLocation();
		path=this.getRequest().getSession().getServletContext().getRealPath("/")+"attachFiles/"+path;
		path=path.replaceAll("/", "\\\\");
		File reportFile=new File(path);
		List<Map<String, String>>  wlist=new ArrayList<Map<String,String>>();
 	    String [] idsarray=listids.split(",");
 	    for(int j=0;j<idsarray.length;j++){
 	    	Map<String, String> map1= new HashMap<String, String>();
 	    	Long projectId=Long.valueOf(idsarray[j]);
 	    	VSmallloanProject vp= this.vSmallloanProjectService.get(projectId);
 	    	map1.put("purposeType", vp.getPurposeTypeValue()); //借款用途
 	        Date startDate=vp.getStartDate();
 	        Date endDate=vp.getExpectedRepaymentDate();
 	        String sd="";
 	        if(null!=startDate)
 	        sd=DateUtil.dateToStr(startDate, "yyyy-MM-dd");
 	        String ed="";
 	        String months=String.valueOf("0");
 	        if(null!=endDate)
 	        {ed=DateUtil.dateToStr(endDate, "yyyy-MM-dd");}
 	        if(!"".equals(sd)){
 	        	String [] sds=sd.split("-");
 	        	map1.put("s_year", sds[0]);
 	        	map1.put("s_month", sds[1]);
 	        	map1.put("s_day", sds[2]);
 	        }
 	        if(!"".equals(ed)){
 	        	String [] sds=ed.split("-");
 	        	map1.put("e_year", sds[0]);
 	        	map1.put("e_month", sds[1]);
 	        	map1.put("e_day", sds[2]);
 	        }
 	        if(!"".equals(sd) && !"".equals(ed))
 	        {
 	        	months=String.valueOf(DateUtil.getMonthAndDaysBetweenDate(sd,ed).get(1));
 	        	
 	        }
 	        String projectMoney="0.00";
 	        if(null!=vp.getProjectMoney())
 	        {
 	           projectMoney=vp.getProjectMoney().toString();
 	        }
 	        String oppositeType=vp.getOperationType();
 	        Short sub=0;
			if(oppositeType.equals("company_customer")){
				sub=0;
			}
			else if(oppositeType.equals("person_customer")){
				sub=1;
			}
 	        List<EnterpriseBank> er=this.enterpriseBankService.getBankList(vp.getOppositeId().intValue(), sub, Short.valueOf("0"),Short.valueOf("0"));
 	        
 	        if(null!=er && er.size()>0){
 	        	EnterpriseBank eb=er.get(0);
 	        	String bankName="";
 	        	String [] tempbank=eb.getBankname().split("-");
 	        	for(int k=0;k<tempbank.length;k++){
 	        		bankName+=tempbank[k];
 	        	}
 	        	map1.put("bankName",bankName); //开户银行
 	        	map1.put("bankNo", eb.getAccountnum()); //开户银行账号
 	        }
 	        String lendMoneyType="";
	        if(vp.getProcessName().equals("microNormalFlow")){
	        	lendMoneyType="小额微贷";
	        }else if(vp.getProcessName().equals("smallLoanNormalFlow"))
	        {
	        	lendMoneyType="小额贷款";
	        }
	        List<VProcreditContract> vlist=vProcreditContractService.getList(vp.getProjectId().toString(), vp.getBusinessType(), "('thirdContract')");
 	        if(null!=vlist && vlist.size()==1){
 	        	 map1.put("bcontractno",vlist.get(0).getContractNumber()); //担保合同编号
 	        }
	        List<VProcreditContract> list=vProcreditContractService.getList(vp.getProjectId().toString(), vp.getBusinessType(), "('loanContract')");
	        if(null!=list && list.size()>0){
	        	map1.put("lcontractno",list.get(0).getContractNumber());//借款合同编号
	        }
	        SwitchMoneyUtil sm=new SwitchMoneyUtil();      
 	        map1.put("loanType",vp.getAssuretypeidValue());    //担保方式    
 	        map1.put("accrualType",vp.getAccrualtypeValue());    //还款方式 
 	        map1.put("lendtype",lendMoneyType); //借款种类
 	        map1.put("bigMoney", sm.getChineseMoney(projectMoney)); //借款金额大写
 	        map1.put("customerName",vp.getCustomerName());
 	        map1.put("months",months);
 	        java.text.DecimalFormat   df  =new  java.text.DecimalFormat("#.00");  
 	        String accrual="";
 	        if(null!=vp.getAccrual())
 	        {
 	        	accrual=df.format(vp.getAccrual());
 	        }
 	        projectMoney=projectMoney.replace(".", "");
 	        while(projectMoney.length()<10)
 	        {
 	    	   projectMoney="0"+projectMoney;
 	        }
 	        String contractNO="LR";
 	        String company=this.getRequest().getParameter("company");
 	         Organization organization=null;
 	        if(null!=company && !"".equals(company) &&  !"null".equals(company)){
			    organization=this.organizationService.getBranchCompanyByKey(company);
			}
			else{
				organization=this.organizationService.getGroupCompany();
			}
 	        String nowDate=DateUtil.getNowDate("yyyyMMdd");
 	        String projectNO=vp.getProjectNumber().split("_")[2];
 	        contractNO=contractNO+"_"+organization.getAcronym()+"_"+nowDate+"_"+projectNO+"_"+"01";
 	        map1.put("accrual",accrual);
 	        map1.put("contractNO",contractNO);
 	        map1.put("wanqian",String.valueOf(projectMoney.charAt(0)));
 	        map1.put("baiW",String.valueOf(projectMoney.charAt(1)));
 	        map1.put("shiW",String.valueOf(projectMoney.charAt(2)));
 	        map1.put("geW",String.valueOf(projectMoney.charAt(3)));
 	        map1.put("qian",String.valueOf(projectMoney.charAt(4)));
 	        map1.put("bai",String.valueOf(projectMoney.charAt(5)));
 	        map1.put("shi",String.valueOf(projectMoney.charAt(6)));
 	        map1.put("yuan",String.valueOf(projectMoney.charAt(7)));
 	        map1.put("jiao",String.valueOf(projectMoney.charAt(8)));
 	        map1.put("fen",String.valueOf(projectMoney.charAt(9)));
 	    	wlist.add(map1);
 	    }
	    try {
			JasperReport jreport=(JasperReport) JRLoader.loadObject(reportFile);
			JasperPrint jprint=JasperFillManager.fillReport(jreport,null,new JRBeanCollectionDataSource(wlist));
			ServletOutputStream ouputStream = this.getResponse().getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(ouputStream);   
			oos.writeObject(jprint);
			oos.flush();      
			oos.close();
		} catch (Exception e) {
			logger.error("Exception ：printfkWorkOrder()>>>>>>>>>>>>>>>>生成借款借据错误 ");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	 return null;
     }
	/**
	 * 通过projectId和businessType得到项目信息视图对象
	 * @author lisl
	 * @return
	 * @throws Exception 
	 */
	public String getProjectViewObject(){
		StringBuffer buff = new StringBuffer("{success:true,data:");
		Type type = null;
		Gson gson=new GsonBuilder().create();
		if ("SmallLoan".equals(businessType)) {
			JSONSerializer json = com.zhiwei.core.util.JsonUtil.getJSONSerializer();
			List<BpFundProject> list=bpFundProjectService.getProject(projectId,"SmallLoan");
			if(null!=list && list.size()>0){
				BpFundProject project=list.get(0);
				//VSmallloanProject vp = vSmallloanProjectService.getByProjectId(projectId);
				buff.append(json.serialize(project));
			}else{
				VSmallloanProject vp = vSmallloanProjectService.getByProjectId(projectId);
				if(vp!=null){
					buff.append(json.serialize(vp));
				}
			}
		} else if ("Financing".equals(businessType)) {
			VFinancingProject vp = vFinancingProjectService.getByProjectId(projectId);
			type=new TypeToken<VFinancingProject>(){}.getType();
			buff.append(gson.toJson(vp, type));
		}else if ("Guarantee".equals(businessType)) {
			VGuaranteeloanProject vp = vGuaranteeloanProjectService.getByProjectId(projectId).get(0);
			type=new TypeToken<VGuaranteeloanProject>(){}.getType();
			buff.append(gson.toJson(vp, type));
		}else if ("BeNotFinancing".equals(businessType)) {
			VLawsuitguarantProject vp = vLawsuitguarantProjectService.getByProjectId(projectId);
			type=new TypeToken<VLawsuitguarantProject>(){}.getType();
			buff.append(gson.toJson(vp, type));
		}else if("Pawn".equals(businessType)){
			VPawnProject vp=plPawnProjectService.getByProjectId(projectId);
			type=new TypeToken<VPawnProject>(){}.getType();
			buff.append(gson.toJson(vp, type));
		}else if("LeaseFinance".equals(businessType)){
			VLeaseFinanceProject vp=flLeaseFinanceProjectService.getViewByProjectId(projectId);
			type=new TypeToken<VLeaseFinanceProject>(){}.getType();
			buff.append(gson.toJson(vp, type));
		}else if ("ParentSmallLoan".equals(businessType)) {
			VSmallloanProject vp = vSmallloanProjectService.getByProjectId(projectId);
			buff.append(gson.toJson(vp));
		}
		buff.append("}");
		jsonString=buff.toString();
		//System.out.println("-->"+jsonString);
		return SUCCESS;
	}
	/**
	 * 通过runId和businessType得到流程表单列表 
	 * @author lisl
	 * @return
	 */
	public String getProcessForm() {
		
		List<ProcessForm> list = processFormService.getByRunId(runId);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 项目流程跳转后，如果进入到贷、保中项目阶段，使其相应的款项生效
	 */
	public String ajaxEffectPayment() {
		List<SlFundIntent> list1=slFundIntentService.getByProjectId1(projectId, businessType);
		List<SlActualToCharge>list2=slActualToChargeService.listbyproject(projectId, businessType);
		Short a=0;
		for(SlFundIntent l:list1){
			l.setIsCheck(a);
			slFundIntentService.save(l);
		}
		for(SlActualToCharge l:list2){
			l.setIsCheck(a);	
			slActualToChargeService.save(l);
		}
		jsonString = "{success:true}";
		return SUCCESS;
	}
	
	//通过方法更新已有的任务信息的节点key
	public String updateFlowNodeKey(){
		List<ProcessRun> listRun = processRunService.getAll();
		if(listRun!=null&&listRun.size()!=0){
			for(int i=0;i<listRun.size();i++){
				ProcessRun run = listRun.get(i);
				if(run!=null){
					String deployId = run.getProDefinition().getDeployId();
					//此方法过滤掉未处理的任务,所有在执行此方法的时候需要去掉processFormDaoImpl中34行的语句条件and pf.taskId is not null and pf.endtime is not null。
					//否则未处理的任务不能更新流程节点对应的key。
					List<ProcessForm> listForm = processFormService.getByRunId(run.getRunId());
					if(listForm!=null&&listForm.size()!=0){
						for(int f=0;f<listForm.size();f++){
							ProcessForm pf = listForm.get(f);
							String activityName = pf.getActivityName();
							ProUserAssign assign = proUserAssignService.getByDeployIdActivityName(deployId, activityName);
							if(assign!=null){
								pf.setTaskSequenceNodeKey(assign.getTaskSequence());
							}else{
								pf.setTaskSequenceNodeKey("0_start_start");
							}
							processFormService.merge(pf);
						}
					}
				}
			}
		}
		return SUCCESS;
	}
	public String saveComments() {

		String taskId = this.getRequest().getParameter("task_id");
		String comments = this.getRequest().getParameter("comments");
		if (null != taskId && !"".equals(taskId) && null != comments&& !"".equals(comments.trim())) {
			this.creditProjectService.saveComments(taskId, comments);
		}
		return SUCCESS;
	}
	
	
	//判断当前节点是否允许进行项目换人操作
	public String isProjectTaskChangeAssign(){
		if(runId!=null&&!"".equals(runId)&&activityName!=null&&!"".equals(activityName)){
			ProUserAssign proUserAssign = creditProjectService.getByRunIdActivityName(runId, activityName);
			if(proUserAssign!=null){
				Long startUserId = new Long(1);
				String startUserName = "";
				if(taskId!=null&&!"".equals(taskId)){
					startUserId = jbpmService.getFlowStartUserId(taskId);
					AppUser appUser = appUserService.get(startUserId);
					if(appUser!=null){
						startUserName = appUser.getFullname();
					}
				}else{
					ProcessRun processRun = processRunService.get(runId);
					if(processRun!=null){
						startUserId = processRun.getUserId();
						startUserName = processRun.getCreator();
					}
				}
				String isProjectChange = proUserAssign.getIsProjectChange().toString();
				if("1".equals(isProjectChange)){
					jsonString = "{success:true,data:{startUserId:"+startUserId+",startUserName:'"+startUserName+"'}}" ;
				}else{
					jsonString = "{success:false}";
				}
			}
		}
		return SUCCESS;
	}
	
	//项目换人处理
	public String projectAssign(){
		if(taskId!=null&&!"".equals(taskId)&&userId!=null&&!"".equals(userId)){
			taskService.assignTask(taskId, userId);
			//Task task = taskService.getTask(taskId);
			if(runId!=null){
				ProcessRun processRun = processRunService.get(runId);
				if(processRun!=null){
					jbpmService.updateStartUserId(new Long(userId), processRun.getPiDbid(), "startUserId");
					AppUser appUser = appUserService.get(new Long(userId));
					if(appUser!=null){
						processRun.setAppUser(appUser);
						processRun.setCreator(appUser.getFullname());
					}
					processRunService.merge(processRun);
				}
			}
			
			String comments = this.getRequest().getParameter("comments");
			String startUserId = this.getRequest().getParameter("startUserId");
			ProcessForm processForm = processFormService.getByTaskId(taskId);
			if(processForm!=null&&startUserId!=null){
				AppUser app = appUserService.get(new Long(startUserId));
				AppUser user = appUserService.get(new Long(userId));
				String nowDateTime = DateUtil.getDateAndTime();
				String projectAssign = processForm.getProjectAssign()!= null?processForm.getProjectAssign() : "";
				processForm.setProjectAssign(projectAssign+"【项目换人："+app.getFullname()+"→"+user.getFullname()+":"+nowDateTime+"】");
				
				String curComment = processForm.getComments()!= null?processForm.getComments() : "";
				processForm.setComments(curComment + comments + "；");
				processForm.setStatus(ProcessForm.STATUS_PROJECTASSIGN);
				processFormService.merge(processForm);
			}
		}
		return SUCCESS;
		/*if(taskId!=null&&!"".equals(taskId)&&userId!=null&&!"".equals(userId)){
			taskService.assignTask(taskId, userId);
			//Task task = taskService.getTask(taskId);
			if(runId!=null){
				ProcessRun processRun = processRunService.get(runId);
				if(processRun!=null){
					jbpmService.updateStartUserId(new Long(userId), processRun.getPiDbid(), "startUserId");
					AppUser appUser = appUserService.get(new Long(userId));
					if(appUser!=null){
						processRun.setAppUser(appUser);
						processRun.setCreator(appUser.getFullname());
					}
					processRunService.merge(processRun);
				}
			}
			
			String comments = this.getRequest().getParameter("comments");
			String startUserId = this.getRequest().getParameter("startUserId");
			ProcessForm processForm = processFormService.getByTaskId(taskId);
			if(processForm!=null&&startUserId!=null){
				AppUser app = appUserService.get(new Long(startUserId));
				AppUser user = appUserService.get(new Long(userId));
				
				String projectAssign = processForm.getProjectAssign()!= null?processForm.getProjectAssign() : "";
				processForm.setProjectAssign(projectAssign+"【项目换人："+app.getFullname()+"→"+user.getFullname()+":"+new Date()+"】");
				
				String curComment = processForm.getComments()!= null?processForm.getComments() : "";
				processForm.setComments(curComment + comments + "；");
				processForm.setStatus(ProcessForm.STATUS_PROJECTASSIGN);
				processFormService.merge(processForm);
			}
		}
		return SUCCESS;*/
	}
	/*
	 * 拿到当前用户的姓名和ID
	 * */
	public String getCurrentUserInfo(){
		try{
			AppUser user =ContextUtil.getCurrentUser();
			String appuserId = user.getId();
			String appuserName = user.getFullname();
			Long companyId=ContextUtil.getLoginCompanyId();
			//将数据转成JSON格式
			StringBuffer sb = new StringBuffer("{success:true,userId:");
			sb.append(appuserId);
			sb.append(",userName:'");
			sb.append(appuserName);
			sb.append("',\"companyId\":");
			sb.append(companyId);
			sb.append(",\"companyName\":'");
			if(null!=companyId){
				Organization or=(Organization) creditBaseDao.getById(Organization.class, companyId);
				if(null!=or){
					sb.append(or.getOrgName());
				}
			}
			
			sb.append("'}");
			setJsonString(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	//检查本金放款台账中数据
	public String checkFinanceData(){
		StringBuffer sb = new StringBuffer("{success:true,flag:");
		int flag =0;
		String date =null;
		//Long sun= 4l;
		try{
			List<SlFundIntent> list1=slFundIntentService.getByProjectId1(projectId, businessType);
			if(list1!=null&&list1.size()!=0){
				for(SlFundIntent sl:list1){
					String type = sl.getFundType();
					if(type.equalsIgnoreCase("principalLending")){
						if(sl.getNotMoney().compareTo(new BigDecimal("0"))==0){
							flag=1;
							Date date1 = sl.getFactDate();
							DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
							date=df.format(date1);
						}else{
						flag = 0;
						}
					}
				}
			}else{
			}
			sb.append(flag);
			sb.append(",flag1:'");
			sb.append(date);
			sb.append("'}");
			setJsonString(sb.toString());
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 业务往来
	 * @return
	 */
	public String getBusiness(){
		jsonString=slSmallloanProjectService.getList(customerType, Long.parseLong(customerId));
		
		return SUCCESS;
	}
	//利率变更申请页面
	public String getInfoLoaned(){
		String projectId=this.getRequest().getParameter("slProjectId"); //项目ID
		String task_id=this.getRequest().getParameter("task_id");  //	任务ID 
		String loanedTypeId=this.getRequest().getParameter("loanedTypeId");  //	贷后类型的id
		String loanedTypeKey=this.getRequest().getParameter("loanedTypeKey");  //	贷后的类型
		Map<String, Object> map = new HashMap<String, Object>();
		SlSmallloanProject project=this.slSmallloanProjectService.get(Long.valueOf(projectId));
		String financeServiceMineName="";
		String managementConsultingMineName="";
		if(null!=project.getFinanceServiceMineType() && !"".equals(project.getFinanceServiceMineType())){
			if(null!=project.getFinanceServiceMineId() && !"".equals(project.getFinanceServiceMineId())){
				if(project.getFinanceServiceMineType().equals("company_ourmain")){
					financeServiceMineName=this.companyMainService.get(project.getFinanceServiceMineId()).getCorName();
				}else{
					financeServiceMineName=this.slPersonMainService.get(project.getFinanceServiceMineId()).getName();
				}
			}
		}
		
		if(null!=project.getManagementConsultingMineType() && !"".equals(project.getManagementConsultingMineType())){
			if(null!=project.getManagementConsultingMineId() && !"".equals(project.getManagementConsultingMineId())){
				if(project.getManagementConsultingMineType().equals("company_ourmain")){
					managementConsultingMineName=this.companyMainService.get(project.getManagementConsultingMineId()).getCorName();
				}else{	
					managementConsultingMineName=this.slPersonMainService.get(project.getManagementConsultingMineId()).getName();
				}
			}
		}
		
		
		try{
			/*if(project.getMineType().equals("company_ourmain")){
				Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
				if(null!=o){
					mineName=o.getOrgName();
				}
				if("true".equals(AppUtil.getSystemIsGroupVersion())){
					mineName=this.organizationService.get(project.getMineId()).getOrgName();
				}else{
					mineName=this.companyMainService.get(project.getMineId()).getCorName();
				}
			}else if (project.getMineType().equals("person_ourmain")){
				Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
				if(null!=o){
					mineName=o.getOrgName();
				}
				mineName=this.slPersonMainService.get(project.getMineId()).getName();
			}*/
			Person p = new Person();
			//if判断是企业客户 elseif是个人客户
			Short sub=0;
			if(project.getOppositeType().equals("company_customer")){
				sub =0;
				Enterprise enterprise1=enterpriseService.getById(project.getOppositeID().intValue());
				if(enterprise1.getLegalpersonid()!=null){
					p=this.personService.getById(enterprise1.getLegalpersonid());
					map.put("person", p);
				}
				if(null != enterprise1.getHangyeType()) {
					  if(null!=areaDicService.getById(enterprise1.getHangyeType())){ 
						  enterprise1.setHangyeName(areaDicService.getById(enterprise1.getHangyeType()).getText());
					  }
				}
	            map.put("enterprise", enterprise1);
	           
			}else if(project.getOppositeType().equals("person_customer")) {
				sub = 1;
				p=this.personService.getById(project.getOppositeID().intValue());
				map.put("person", p);
				if(null!=p){
					if(null!=p.getId()){
						if(null!=p.getMarry() && p.getMarry()==317){
		        			Spouse spouse=spouseService.getByPersonId(p.getId());
		        			map.put("spouse", spouse);
		        		}
					}
				}
				
			}
		 if(null!=project.getOppositeID()){
			 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), sub, Short.valueOf("0"),Short.valueOf("0"));
	         if(null!=list && list.size()>0){
	        		EnterpriseBank bank=list.get(0);
	        		map.put("enterpriseBank", bank);
	        	}
		 }
		 
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ProjectAction:"+e.getMessage());
		}
		
		
		
		if(loanedTypeKey.equals("slAlteraccrual")){
			
			SlAlterAccrualRecord slAlterAccrualRecord=slAlteraccrualRecordService.get(Long.valueOf(loanedTypeId));
			map.put("slAlterAccrualRecord", slAlterAccrualRecord); 
		}else if(loanedTypeKey.equals("earlyReyment")){
			
			SlEarlyRepaymentRecord slEarlyRepaymentRecord=slEarlyRepaymentRecordService.get(Long.valueOf(loanedTypeId));
			map.put("slEarlyRepaymentRecord", slEarlyRepaymentRecord);
			
		}
		if (null != project.getProductId()&& !"".equals(project.getProductId())) {
			String isOnlineApply=this.getRequest().getParameter("isOnlineApply");//是否是线上申请
			if(null!=isOnlineApply && !"".equals(isOnlineApply) && "true".equals(isOnlineApply)){
				P2pLoanProduct product=p2pLoanProductService.get(project.getProductId());
				map.put("bpProductParameter",product);
			}else{
				BpProductParameter bpProductParameter = bpProductParameterService.get(project.getProductId());
				map.put("bpProductParameter", bpProductParameter);
			}
		}
		
		// 自有资金
		BpFundProject ownFund = bpFundProjectService.getByProjectId(Long
				.valueOf(projectId), Short.valueOf("0"));
		if (null != ownFund) {
			map.put("ownBpFundProject", ownFund);
		}
	
		String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
		String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
		String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
		map.put("slSmallloanProject", project); 
		//map.put("mineName", mineName);
		map.put("financeServiceMineName", financeServiceMineName); 
		map.put("managementConsultingMineName", managementConsultingMineName); 
		map.put("businessType", project.getBusinessType());
		map.put("businessTypeKey",businessTypeKey);
		map.put("operationTypeKey",operationTypeKey);
		map.put("definitionTypeKey",definitionTypeKey);
		map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getName());
		List<DictionaryIndependent>  dics = this.dictionaryIndependentService.getByDicKey(project.getMineType());
		if(null!=dics&&dics.size()!=0)
			map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
		/*try{
			if(null!=project.getLoanLimit()){
				DictionaryIndependent dic=this.dictionaryIndependentService.getByDicKey(project.getLoanLimit()).get(0);
				if(null!=dic){
					map.put("smallLoanTypeKey", dic.getItemValue());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}*/
		if (null != project.getProductId()&& !"".equals(project.getProductId())) {
			String isOnlineApply=this.getRequest().getParameter("isOnlineApply");//是否是线上申请
			if(null!=isOnlineApply && !"".equals(isOnlineApply) && "true".equals(isOnlineApply)){
				P2pLoanProduct product=p2pLoanProductService.get(project.getProductId());
				map.put("bpProductParameter",product);
			}else{
				BpProductParameter bpProductParameter = bpProductParameterService.get(project.getProductId());
				map.put("bpProductParameter", bpProductParameter);
			}
		}
		if(null!=task_id && !"".equals(task_id)){
			ProcessForm pform = processFormService.getByTaskId(task_id);
			if(pform==null){
				pform = creditProjectService.getCommentsByTaskId(task_id);
			}
			if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
				map.put("comments", pform.getComments());
			}
		}
		
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();

return SUCCESS;
}
	//微贷展期中节点页面loaddata所用的方法
	public String getMiroFinanceExtensionlInfo(){
		
		String taskId=this.getRequest().getParameter("taskId"); //贷款项目（微贷）ID
		String task_id=this.getRequest().getParameter("task_id");  //	任务ID 
		String loanedTypeId=this.getRequest().getParameter("loanedTypeId");  //	展期贷后类型的id
		Map<String, Object> map = new HashMap<String, Object>();
		SlSmallloanProject project=this.slSmallloanProjectService.get(Long.valueOf(taskId));
		String mineName="";
		try{
			if(project.getMineType().equals("company_ourmain")){
				Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
				if(null!=o){
					mineName=o.getOrgName();
				}
			}else if (project.getMineType().equals("person_ourmain")){
				Organization o=(Organization) creditBaseDao.getById(Organization.class, project.getMineId());
				if(null!=o){
					mineName=o.getOrgName();
				}
			}
			Person p = new Person();
			//if判断是企业客户 elseif是个人客户
			Short sub=0;
			if(project.getOppositeType().equals("company_customer")){
				sub =0;
				Enterprise enterprise1=enterpriseService.getById(project.getOppositeID().intValue());
				if(enterprise1.getLegalpersonid()!=null){
					p=this.personService.getById(enterprise1.getLegalpersonid());
					map.put("person", p);
				}
				if(null != enterprise1.getHangyeType()) {
					  if(null!=areaDicService.getById(enterprise1.getHangyeType())){ 
						  enterprise1.setHangyeName(areaDicService.getById(enterprise1.getHangyeType()).getText());
					  }
				}
	            map.put("enterprise", enterprise1);
	           
			}else if(project.getOppositeType().equals("person_customer")) {
				sub = 1;
				p=this.personService.getById(project.getOppositeID().intValue());
				map.put("person", p);
				if(null!=p){
					if(null!=p.getId()){
						if(null!=p.getMarry() && p.getMarry()==317){
		        			Spouse spouse=spouseService.getByPersonId(p.getId());
		        			map.put("spouse", spouse);
		        		}
					}
				}
			}
			
			 if(null!=project.getOppositeID()){
				 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), sub, Short.valueOf("0"),Short.valueOf("0"));
		         if(null!=list && list.size()>0){
		        		EnterpriseBank bank=list.get(0);
		        		map.put("enterpriseBank", bank);
		        	}
			 }
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("ProjectAction:"+e.getMessage());
		}
		
		String appuers="";
		if(null!=project.getAppUserId())
		{
			String [] appstr=project.getAppUserId().split(",");
			Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet){
				appuers+=s.getFamilyName()+",";
			}
			
		}
		if(appuers.length()>0){
		appuers=appuers.substring(0, appuers.length()-1);}
		StringBuffer textBuffer = new StringBuffer (mineName); 
		project.setMineName(textBuffer.toString());
		project.setAppUsers(appuers);
		String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
		String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
		String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
		//查出微贷展期的展期数据
		SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.valueOf(loanedTypeId));
		map.put("slSuperviseRecord", slSuperviseRecord); 
		map.put("slSmallloanProject", project); 
		map.put("mineName", mineName);
		map.put("businessType", project.getBusinessType());
		map.put("businessTypeKey",businessTypeKey);
		map.put("operationTypeKey",operationTypeKey);
		map.put("definitionTypeKey",definitionTypeKey);
		map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getName());
		map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
		
		
		if(null!=task_id && !"".equals(task_id)){
			ProcessForm pform = processFormService.getByTaskId(task_id);
			if(pform==null){
				pform = creditProjectService.getCommentsByTaskId(task_id);
			}
			if(pform!=null&&pform.getComments()!=null&&!"".equals(pform.getComments())){
				map.put("comments", pform.getComments());
			}
		}
		
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	
	//查询主借款人项目记录
	public String personBrowerProjectList(){
		String businessType=this.getRequest().getParameter("businessType");
		if(null==businessType || "".equals(businessType)){
			return SUCCESS;
		}
		PagingBean pb = new PagingBean(start, limit);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if(businessType.equals("Guarantee")){
		}else if(businessType.equals("SmallLoan")){
			 List<BpFundProject> listS=bpFundProjectService.personBrowerProjectList(pb, getRequest());
			 Long scount=bpFundProjectService.personBrowerProjectCount(getRequest());
			 buff=buff.append(scount).append(",result:");
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				buff.append(gson.toJson(listS));
				buff.append("}");
	   }else if(businessType.equals("InternetFinance")){
			 List<BpFundProject> listS=bpFundProjectService.personBrowerOnlineProjectList(pb, getRequest());
			 Long scount=bpFundProjectService.personBrowerOnlineProjectCount(getRequest());
			 buff=buff.append(scount).append(",result:");
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				buff.append(gson.toJson(listS));
				buff.append("}");
	
	   }else if(businessType.equals("Pawn")){
		}
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 主借款人项目记录导出到Excel中，现考虑的只是贷款业务，未加担保和典当
	 */
	public void personBrowerOutExcel(){
		String businessType=this.getRequest().getParameter("businessType");
		List<BpFundProject> listS=null;
		if(null==businessType || "".equals(businessType)){
		}
      
		if(businessType.equals("Guarantee")){
		}else if(businessType.equals("SmallLoan")){
		   listS=bpFundProjectService.personBrowerProjectList(null, getRequest());
		
	   }else if(businessType.equals("InternetFinance")){
			 listS=bpFundProjectService.personBrowerOnlineProjectList(null, getRequest());
	   }else if(businessType.equals("Pawn")){
		}
		String [] tableHeader = {"序号","业务品种","项目名称","项目金额","开始日期","结束日期","本金逾期金额","本金逾期天数","利息逾期金额","利息逾期天数","状态"};
		try {
		
			ExcelHelper.exportAllBrowerProjectList(listS,tableHeader,"主借款人登记项目列表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//查询保证人项目记录
	public String personAssureProjectList(){
		String businessType=this.getRequest().getParameter("businessType");
		if(null==businessType || "".equals(businessType)){
			return SUCCESS;
		}
		PagingBean pb = new PagingBean(start, limit);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if(businessType.equals("Guarantee")){
		}else if(businessType.equals("SmallLoan")){
			 List<BpFundProject> listS=bpFundProjectService.personAssureProjectList(pb, getRequest());
			 Long scount=bpFundProjectService.personAssureProjectCount(getRequest());
			 buff=buff.append(scount).append(",result:");
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				buff.append(gson.toJson(listS));
				buff.append("}");
	   }else if(businessType.equals("InternetFinance")){
			 List<BpFundProject> listS=bpFundProjectService.personAssureOnlineProjectList(pb, getRequest());
			 Long scount=bpFundProjectService.personAssureOnlineProjectCount(getRequest());
			 buff=buff.append(scount).append(",result:");
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				buff.append(gson.toJson(listS));
				buff.append("}");
	
	   }else if(businessType.equals("Pawn")){
		}
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 保证人项目记录导出到Excel中，现考虑的只是贷款业务，未加担保和典当
	 */
	public void personAssureOutExcel(){
		String businessType=this.getRequest().getParameter("businessType");
		List<BpFundProject> listS=null;
		if(null==businessType || "".equals(businessType)){
		}
		if(businessType.equals("Guarantee")){
		}else if(businessType.equals("SmallLoan")){
		   listS=bpFundProjectService.personAssureProjectList(null, getRequest());
		
	   }else if(businessType.equals("InternetFinance")){
			 listS=bpFundProjectService.personAssureOnlineProjectList(null, getRequest());
	   }else if(businessType.equals("Pawn")){
		}
		String [] tableHeader = {"序号","业务品种","保证人","证件号码","项目名称","项目金额","担保关系","开始日期","结束日期","本金逾期金额","本金逾期天数","利息逾期金额","利息逾期天数","状态"};
		try {
		
			ExcelHelper.exportAllBrowerProjectList(listS,tableHeader,"保证人登记项目列表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//查询个人法人项目记录
	public String personLegalProjectList(){
		String businessType=this.getRequest().getParameter("businessType");
		if(null==businessType || "".equals(businessType)){
			return SUCCESS;
		}
		PagingBean pb = new PagingBean(start, limit);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if(businessType.equals("Guarantee")){
		}else if(businessType.equals("SmallLoan")){
			 List<BpFundProject> listS=bpFundProjectService.personLegalProjectList(pb, getRequest());
			 Long scount=bpFundProjectService.personLegalProjectCount(getRequest());
			 buff=buff.append(scount).append(",result:");
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				buff.append(gson.toJson(listS));
				buff.append("}");
	   }else if(businessType.equals("InternetFinance")){
			 List<BpFundProject> listS=bpFundProjectService.personLegalOnlineProjectList(pb, getRequest());
			 Long scount=bpFundProjectService.personLegalOnlineProjectCount(getRequest());
			 buff=buff.append(scount).append(",result:");
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				buff.append(gson.toJson(listS));
				buff.append("}");
	
	   }else if(businessType.equals("Pawn")){
		}
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 个人法人项目记录导出到Excel中，现考虑的只是贷款业务，未加担保和典当
	 */
	public void personLegalOutExcel(){
		String businessType=this.getRequest().getParameter("businessType");
		List<BpFundProject> listS=null;
		if(null==businessType || "".equals(businessType)){
		}
		if(businessType.equals("Guarantee")){
		}else if(businessType.equals("SmallLoan")){
		   listS=bpFundProjectService.personLegalProjectList(null, getRequest());
		
	   }else if(businessType.equals("InternetFinance")){
			 listS=bpFundProjectService.personLegalOnlineProjectList(null, getRequest());
	   }else if(businessType.equals("Pawn")){
		}
		String [] tableHeader = {"序号","业务品种","法人","证件号码","项目名称","项目金额","开始日期","结束日期","本金逾期金额","本金逾期天数","利息逾期金额","利息逾期天数","状态"};
		try {
		
			ExcelHelper.exportAllBrowerProjectList(listS,tableHeader,"法人借款登记项目列表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Long getSafeLevel() {
		return safeLevel;
	}

	public void setSafeLevel(Long safeLevel) {
		this.safeLevel = safeLevel;
	}

	public String getFlowTaskId() {
		return flowTaskId;
	}

	public void setFlowTaskId(String flowTaskId) {
		this.flowTaskId = flowTaskId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

}
