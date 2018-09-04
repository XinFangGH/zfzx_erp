package com.zhiwei.credit.action.creditFlow.smallLoan.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.product.BpProductParameter;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.customer.person.SpouseService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlAlterAccrualRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.supervise.SlSuperviseRecordService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.GlobalTypeService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.product.BpProductParameterService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class SlAlterAccrualRecordAction extends BaseAction{
	@Resource
	private SlAlterAccrualRecordService slAlterAccrualRecordService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private AreaDicService areaDicService;
	@Resource
	private SpouseService spouseService;
    @Resource
    private EnterpriseService enterpriseService;
    @Resource
    private PersonService personService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private  DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private SlSuperviseRecordService slSuperviseRecordService;
	@Resource
	private SlCompanyMainService companyMainService;
	@Resource
	private SlPersonMainService  slPersonMainService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	private SlAlterAccrualRecord slAlterAccrualRecord;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private BpProductParameterService bpProductParameterService;
	
	private Long slAlteraccrualRecordId;
	private Long projectId;
	private SlSmallloanProject slSmallloanProject;
	private String taskId;

	public Long getSlAlteraccrualRecordId() {
		return slAlteraccrualRecordId;
	}

	public void setSlAlteraccrualRecordId(Long slAlteraccrualRecordId) {
		this.slAlteraccrualRecordId = slAlteraccrualRecordId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public SlAlterAccrualRecord getSlAlterAccrualRecord() {
		return slAlterAccrualRecord;
	}

	public void setSlAlterAccrualRecord(SlAlterAccrualRecord slAlterAccrualRecord) {
		this.slAlterAccrualRecord = slAlterAccrualRecord;
	}
	public void setSlSmallloanProject(SlSmallloanProject slSmallloanProject) {
		this.slSmallloanProject = slSmallloanProject;
	}

	public SlSmallloanProject getSlSmallloanProject() {
		return slSmallloanProject;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskId() {
		return taskId;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlAlterAccrualRecord> list= slAlterAccrualRecordService.getAll(filter);
		
		Type type=new TypeToken<List<SlAlterAccrualRecord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
		public String initAlteraccrualPanel(){
		    SlAlterAccrualRecord	r=new SlAlterAccrualRecord();
			SlSmallloanProject	sl=slSmallloanProjectService.get(projectId);
			r.setAccrual(sl.getAccrual());
			r.setFinanceServiceOfRate(sl.getFinanceServiceOfRate());
			r.setManagementConsultingOfRate(sl.getManagementConsultingOfRate());
			r.setPayaccrualType(sl.getPayaccrualType());
			r.setAccrualtype(sl.getAccrualtype());
			r.setDateMode(sl.getDateMode());
			r.setIsPreposePayAccrual(sl.getIsPreposePayAccrual());
			r.setIsStartDatePay(sl.getIsStartDatePay());
			r.setPayintentPerioDate(sl.getPayintentPerioDate());
			r.setProjectId(sl.getProjectId());
			r.setPayintentPeriod(sl.getPayintentPeriod());
			
//			List<SlFundIntent> allintent=this.slFundIntentService.getByProjectId1(projectId,"SmallLoan");
//			int count=0;
//			for(SlFundIntent a:allintent){
//				if(a.getFundType().equals("loanInterest")){
//					count++;
//					
//				}
//			}
//			
//			r.setPayintentPeriod(count);
			r.setDayOfEveryPeriod(sl.getDayOfEveryPeriod());
				
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			//将数据转成JSON格式
			StringBuffer sb = new StringBuffer("{success:true,data:");
			sb.append(gson.toJson(r));
			sb.append("}");
			setJsonString(sb.toString());
			return SUCCESS;
			}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		String[]ids=getRequest().getParameterValues("ids");
		String businessType=this.getRequest().getParameter("businessType");
		if(ids!=null){
			for(String id:ids){
				SlAlterAccrualRecord slAlterAccrualRecord=slAlterAccrualRecordService.get(new Long(id));
				SlSmallloanProject project=slSmallloanProjectService.get(slAlterAccrualRecord.getProjectId());
				project.setAccrual(slAlterAccrualRecord.getOriaccrual());
				slSmallloanProjectService.save(project);
				slAlterAccrualRecordService.remove(new Long(id));
				List<SlFundIntent> ilist=slFundIntentService.getlistbyslslAlteraccrualRecordId(new Long(id), businessType, project.getProjectId());
				for(SlFundIntent slFundIntent:ilist){
					slFundIntentService.remove(slFundIntent);
				}
				List<SlFundIntent> list=slFundIntentService.getListByAlteraccrualOperateId(project.getProjectId(), businessType, new Long(id), "=1");
				for(SlFundIntent s:list){
					s.setIsValid(Short.valueOf("0"));
					s.setIsCheck(Short.valueOf("0"));
					slFundIntentService.save(s);
				}
				List<SlActualToCharge>  alist=slActualToChargeService.getlistbyslAlteraccrualRecordId(new Long(id), businessType, project.getProjectId());
				for(SlActualToCharge a:alist){
					slActualToChargeService.remove(a);
				}
				List flist=fileFormService.listFiles(project.getProjectId().toString(), "sl_alteraccrual_record."+new Long(id), "sl_alteraccrual_record."+project.getProjectId(), "SmallLoan");
				for(int i=0;i<flist.size();i++){
					FileForm file=(FileForm) flist.get(i);
					try {
						creditBaseDao.deleteDatas(file);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
				
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 融资租赁利率变更--批量删除
	 * @return
	 */
	public String flMultiDel(){
		String[]ids=getRequest().getParameterValues("ids");
		String businessType=this.getRequest().getParameter("businessType");
		if(ids!=null){
			for(String id:ids){
				SlAlterAccrualRecord slAlterAccrualRecord=slAlterAccrualRecordService.get(new Long(id));
				FlLeaseFinanceProject project=flLeaseFinanceProjectService.get(slAlterAccrualRecord.getProjectId());
				project.setAccrualnew(slAlterAccrualRecord.getOriaccrual());
				flLeaseFinanceProjectService.save(project);
				slAlterAccrualRecordService.remove(new Long(id));
				List<SlFundIntent> ilist=slFundIntentService.getlistbyslslAlteraccrualRecordId(new Long(id), businessType, project.getProjectId());
				for(SlFundIntent slFundIntent:ilist){
					slFundIntentService.remove(slFundIntent);
				}
				List<SlFundIntent> list=slFundIntentService.getListByAlteraccrualOperateId(project.getProjectId(), businessType, new Long(id), "=1");
				for(SlFundIntent s:list){
					s.setIsValid(Short.valueOf("0"));
					s.setIsCheck(Short.valueOf("0"));
					slFundIntentService.save(s);
				}
				List<SlActualToCharge>  alist=slActualToChargeService.getlistbyslAlteraccrualRecordId(new Long(id), businessType, project.getProjectId());
				for(SlActualToCharge a:alist){
					slActualToChargeService.remove(a);
				}
				List flist=fileFormService.listFiles(project.getProjectId().toString(), "sl_alteraccrual_record."+new Long(id), "sl_alteraccrual_record."+project.getProjectId(), "LeaseFinance");
				for(int i=0;i<flist.size();i++){
					FileForm file=(FileForm) flist.get(i);
					try {
						creditBaseDao.deleteDatas(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
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
		SlAlterAccrualRecord slAlterAccrualRecord=slAlterAccrualRecordService.get(slAlteraccrualRecordId);
		Map<String,Object> map=new HashMap<String,Object>();
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slAlterAccrualRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slAlterAccrualRecord.getSlAlteraccrualRecordId()==null){
			slAlterAccrualRecordService.save(slAlterAccrualRecord);
		}else{
			SlAlterAccrualRecord orgSlAlterAccrualRecord=slAlterAccrualRecordService.get(slAlterAccrualRecord.getSlAlteraccrualRecordId());
			try{
				BeanUtil.copyNotNullProperties(orgSlAlterAccrualRecord, slAlterAccrualRecord);
				slAlterAccrualRecordService.save(orgSlAlterAccrualRecord);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	//启动小贷利率变更流程
	public String startSlAlteraccrualProcess(){
		String str=slSmallloanProjectService.startSlAlteraccrualProcess(projectId);
		jsonString="{success:true,"+str+"}";
		return SUCCESS;
	}
	//启动融资租赁利率变更流程
	public String startFLSlAlteraccrualProcess(){
		String str=flLeaseFinanceProjectService.startSlAlteraccrualProcess(projectId);
		jsonString="{success:true,"+str+"}";
		return SUCCESS;
	}
	

//利率变更流程loaddata方法
	public String getInfoLoaned(){
		String sProjectId=this.getRequest().getParameter("sProjectId"); //贷款项目ID
		String task_id=this.getRequest().getParameter("task_id");  //	任务ID 
		Map<String, Object> map = new HashMap<String, Object>();
		SlSmallloanProject project=this.slSmallloanProjectService.get(Long.valueOf(sProjectId));
		String financeServiceMineName="";
		String managementConsultingMineName="";
		if (null != project.getProductId() && !"".equals(project.getProductId())) {
			BpProductParameter bpProductParameter = bpProductParameterService.get(project.getProductId());
			if(null!=bpProductParameter){
				map.put("bpProductParameter", bpProductParameter);
			}
		}
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
		
		SlAlterAccrualRecord  slAlterAccrualRecord=slAlterAccrualRecordService.get(slAlteraccrualRecordId);
        map.put("slAlterAccrualRecord", slAlterAccrualRecord); 
		map.put("slSmallloanProject", project); 
		map.put("financeServiceMineName", financeServiceMineName); 
		map.put("managementConsultingMineName", managementConsultingMineName); 
		map.put("businessType", project.getBusinessType());
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
	
	//利率变更流程利率变更申请节点保存方法
	public String askForAlterAccrualFlow(){
		try{
			String slAlterAccrualRecordId =this.getRequest().getParameter("slAlteraccrualRecordId");
			if(null!=slAlterAccrualRecordId && !"".equals(slAlterAccrualRecordId)){
				SlAlterAccrualRecord sls=slAlterAccrualRecordService.get(Long.valueOf(slAlterAccrualRecordId));
				BeanUtil.copyNotNullProperties(sls, slAlterAccrualRecord);
				slAlterAccrualRecordService.merge(sls);
				//项目表新添字段，需要将贷后的利息，计息方式，利率存入
				BpFundProject project=this.bpFundProjectService.get(sls.getProjectId());
				
				//保存款项信息（利率变更新重新计算利率）开始
				String slFundIentJson = this.getRequest().getParameter("fundIntentJsonData");
				
				if(null != slFundIentJson && !"".equals(slFundIentJson)){
					List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId), "SmallLoan",project.getProjectId());
					for (SlFundIntent s : slFundIntentsAllsupervise) {
						slFundIntentService.remove(s);
					}
					String[] slFundIentJsonArr = slFundIentJson.split("@");
					for(int i=0; i<slFundIentJsonArr.length; i++) {
						String str = slFundIentJsonArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						try{
							SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
							slFundIntent.setProjectId(project.getProjectId());
							slFundIntent.setBusinessType(project.getBusinessType());
							slFundIntent.setProjectName(project.getProjectName());
							slFundIntent.setProjectNumber(project.getProjectNumber());
							slFundIntent.setCompanyId(project.getCompanyId());
							slFundIntent.setIsValid(Short.valueOf("0"));
							BigDecimal lin = new BigDecimal(0.00);
							if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
					        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
					        }else{
					        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
					        	
					        }
							slFundIntent.setAfterMoney(new BigDecimal(0));
							slFundIntent.setAccrualMoney(new BigDecimal(0));
							slFundIntent.setFlatMoney(new BigDecimal(0));
							slFundIntent.setIsCheck(Short.valueOf("1"));
							if(null==slFundIntent.getFundIntentId()){
								slFundIntentService.save(slFundIntent);
							}else{
								SlFundIntent orgSlFundIntent=slFundIntentService.get(slFundIntent.getFundIntentId());
								BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
								slFundIntentService.save(orgSlFundIntent);
							}
						} catch(Exception e) {
							e.printStackTrace();
							logger.error("SlSuperviseRecordAction:款项信息保存出错"+e.getMessage());
						}
					}
				}
			
			}
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
			return SUCCESS;
		}catch(Exception e ){
			e.printStackTrace();
			logger.error("微贷展期节点保存/更新出错:"+e.getMessage());
			return "error";
		}
	}
	public String updateAlterAccrualInfo(){
		try{
			String slAlterAccrualRecordId =this.getRequest().getParameter("slAlteraccrualRecordId");
			if(null!=slAlterAccrualRecordId && !"".equals(slAlterAccrualRecordId)){
				SlAlterAccrualRecord sls=slAlterAccrualRecordService.get(Long.valueOf(slAlterAccrualRecordId));
				BeanUtil.copyNotNullProperties(sls, slAlterAccrualRecord);
				slAlterAccrualRecordService.merge(sls);
				//项目表新添字段，需要将贷后的利息，计息方式，利率存入
				BpFundProject project=this.bpFundProjectService.get(sls.getProjectId());
				
				//保存款项信息（利率变更新重新计算利率）开始
				String slFundIentJson = this.getRequest().getParameter("fundIntentJsonData");
				
				if(null != slFundIentJson && !"".equals(slFundIentJson)){
					List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId), "SmallLoan",project.getProjectId());
					for (SlFundIntent s : slFundIntentsAllsupervise) {
						if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
							slFundIntentService.remove(s);
						}
					}
					String[] slFundIentJsonArr = slFundIentJson.split("@");
					for(int i=0; i<slFundIentJsonArr.length; i++) {
						String str = slFundIentJsonArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						try{
							SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
							slFundIntent.setProjectId(project.getProjectId());
							slFundIntent.setBusinessType(project.getBusinessType());
							slFundIntent.setProjectName(project.getProjectName());
							slFundIntent.setProjectNumber(project.getProjectNumber());
							slFundIntent.setCompanyId(project.getCompanyId());
							slFundIntent.setIsValid(Short.valueOf("0"));
							BigDecimal lin = new BigDecimal(0.00);
							if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
					        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
					        }else{
					        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
					        	
					        }
							slFundIntent.setAfterMoney(new BigDecimal(0));
							slFundIntent.setAccrualMoney(new BigDecimal(0));
							slFundIntent.setFlatMoney(new BigDecimal(0));
							slFundIntent.setIsCheck(Short.valueOf("1"));
							if(null==slFundIntent.getFundIntentId()){
								slFundIntentService.save(slFundIntent);
							}else{
								SlFundIntent orgSlFundIntent=slFundIntentService.get(slFundIntent.getFundIntentId());
								BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
								slFundIntentService.save(orgSlFundIntent);
							}
						} catch(Exception e) {
							e.printStackTrace();
							logger.error("SlSuperviseRecordAction:款项信息保存出错"+e.getMessage());
						}
					}
				}
			
			}
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
			return SUCCESS;
		}catch(Exception e ){
			e.printStackTrace();
			logger.error("微贷展期节点保存/更新出错:"+e.getMessage());
			return "error";
		}
	}
	public String getIsChecked(){
		String slAlteraccrualRecordId=this.getRequest().getParameter("slAlteraccrualRecordId");
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		if(null==businessType || "".equals(businessType)){
			businessType="SmallLoan";
		}
		List<SlFundIntent> list=slFundIntentService.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlteraccrualRecordId),businessType, Long.valueOf(projectId));
		StringBuffer sb=new StringBuffer("{success:true");
		for(SlFundIntent s:list){
			if(s.getAfterMoney().compareTo(new BigDecimal(0))!=0){
				sb.append(",isChecked:true");
				break;
			}
		}
		sb.append("}");
		jsonString=sb.toString();
		return 	SUCCESS;	
	}
	
	/***
	 * 融资租赁---利率变更流程loaddata方法
	 * @return
	 */
	public String flGetInfoLoaned(){
		String task_id=this.getRequest().getParameter("task_id");  //	任务ID 
		String loanedTypeId=this.getRequest().getParameter("loanedTypeId");  //	利率变更贷后类型的id
		String businessType =this.getRequest().getParameter("type");//项目表的businessType
		Map<String, Object> map = new HashMap<String, Object>();
		FlLeaseFinanceProject project=this.flLeaseFinanceProjectService.get(Long.valueOf(projectId));
		String mineName="";
		String financeServiceMineName="";
		String managementConsultingMineName="";
		try{
			if(project.getMineType().equals("company_ourmain")){
				if("true".equals(AppUtil.getSystemIsGroupVersion())){
					mineName=this.organizationService.get(project.getMineId()).getOrgName();
				}else{
					mineName=this.companyMainService.get(project.getMineId()).getCorName();
				}
			}else if (project.getMineType().equals("person_ourmain")){
				mineName=this.slPersonMainService.get(project.getMineId()).getName();
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
		String appUsersOfA="";
		if(null!=project.getAppUserId())
		{
			String [] appstr=project.getAppUserId().split(",");
			Set<AppUser> userSet=this.appUserService.getAppUserByStr(appstr);
			for(AppUser s:userSet){
				appUsersOfA+=s.getFullname()+",";
			}
			appUsersOfA = appUsersOfA.substring(0, appUsersOfA.length()-1);
		}
		if(appuers.length()>0){
			appuers=appuers.substring(0, appuers.length()-1);
		}
		StringBuffer textBuffer = new StringBuffer (mineName); 
		project.setMineName(textBuffer.toString());
		SlAlterAccrualRecord  slAlterAccrualRecord=slAlterAccrualRecordService.get(Long.valueOf(loanedTypeId));
        map.put("slAlterAccrualRecord", slAlterAccrualRecord); 
        List<SlFundIntent> slist=slFundIntentService.getlistbyslslAlteraccrualRecordId(Long.valueOf(loanedTypeId), businessType, project.getProjectId());
		Long slSuperviseRecordId=null;
		if(null!=slist && slist.size()>0){
			SlFundIntent sf=slist.get(0);
			if(null!=sf.getSlSuperviseRecordId()){
				slSuperviseRecordId=sf.getSlSuperviseRecordId();
			}else{
				slSuperviseRecordId=new Long(0);
			}
		}
		String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
		map.put("payintentPeriod", slSuperviseRecordId);
		map.put("flLeaseFinanceProject", project); 
		map.put("appUsersOfA",appUsersOfA);
		map.put("mineName", mineName);
		map.put("financeServiceMineName", financeServiceMineName); 
		map.put("managementConsultingMineName", managementConsultingMineName); 
		map.put("businessType", project.getBusinessType());
		map.put("businessTypeKey",this.globalTypeService.getByNodeKey(project.getBusinessType()).get(0).getTypeName());
		map.put("operationTypeKey",operationTypeKey);
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
		/*if(null!=project.getAccrualnew() && !project.getAccrualnew().equals("")){
			DictionaryIndependent dic=this.dictionaryIndependentService.getByDicKey(String.valueOf(project.getAccrualnew())).get(0);
			if(null!=dic){
				map.put("smallLoanTypeKey", dic.getItemValue());
			}
		}*/
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/***
	 * 融资租赁---利率变更流程利率变更申请节点保存方法
	 * @return
	 */
	public String flSaveAlterAccrualFlow(){
		try{
			String slProjectId = this.getRequest().getParameter("projectId");
			String slAlterAccrualRecordId =this.getRequest().getParameter("slAlteraccrualRecordId");
			if(null!=slAlterAccrualRecordId && !"".equals(slAlterAccrualRecordId)){
				SlAlterAccrualRecord sls=slAlterAccrualRecordService.get(Long.valueOf(slAlterAccrualRecordId));
				sls.setAlterProjectMoney(slAlterAccrualRecord.getAlterProjectMoney());
				sls.setReason(slAlterAccrualRecord.getReason());
				sls.setAlterpayintentPeriod(slAlterAccrualRecord.getAlterpayintentPeriod());
				sls.setAccrual(slAlterAccrualRecord.getAccrual());
				slAlterAccrualRecordService.save(sls);
				//项目表新添字段，需要将贷后的利息，计息方式，利率存入
				FlLeaseFinanceProject project=this.flLeaseFinanceProjectService.get(Long.valueOf(slProjectId));
				
				//保存款项信息（利率变更新重新计算利率）开始
				String slFundIentJson = this.getRequest().getParameter("intent_plan_earlyRepayment");
				List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId), "LeaseFinance",Long.valueOf(slProjectId));
				for (SlFundIntent s : slFundIntentsAllsupervise) {
					if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
						slFundIntentService.remove(s);
					}
				}
				if(null != slFundIentJson && !"".equals(slFundIentJson)){
					String[] slFundIentJsonArr = slFundIentJson.split("@");
					for(int i=0; i<slFundIentJsonArr.length; i++) {
						String str = slFundIentJsonArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						try{
							SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
							slFundIntent.setSlAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId));
							slFundIntent.setProjectId(Long.valueOf(slProjectId));
							slFundIntent.setBusinessType(project.getBusinessType());
							slFundIntent.setProjectName(project.getProjectName());
							slFundIntent.setProjectNumber(project.getProjectNumber());
							slFundIntent.setCompanyId(project.getCompanyId());
							slFundIntent.setIsValid(Short.valueOf("0"));
							BigDecimal lin = new BigDecimal(0.00);
							if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
					        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
					        }else{
					        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
					        	
					        }
							slFundIntent.setAfterMoney(new BigDecimal(0));
							slFundIntent.setAccrualMoney(new BigDecimal(0));
							slFundIntent.setFlatMoney(new BigDecimal(0));
							slFundIntent.setIsCheck(Short.valueOf("1"));
							if(null==slFundIntent.getFundIntentId()){
								slFundIntentService.save(slFundIntent);
							}else{
								SlFundIntent orgSlFundIntent=slFundIntentService.get(slFundIntent.getFundIntentId());
								BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
								slFundIntentService.save(orgSlFundIntent);
							}
						} catch(Exception e) {
							e.printStackTrace();
							logger.error("SlSuperviseRecordAction:款项信息保存出错"+e.getMessage());
						}
					}
				}
			//保存款项信息（利率变更新重新计算利率）结束
			//slSmallloanProjectService.save(project);
			}
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
			return SUCCESS;
		}catch(Exception e ){
			e.printStackTrace();
			logger.error("利率变更申请节点保存/更新出错:"+e.getMessage());
			return "error";
		}
	}
	public String savealterInfo(){
		try{

			//写自己的业务逻辑
			String projectId = this.getRequest().getParameter("projectId_flow"); // 项目ID
			FlFinancingProject project=this.flFinancingProjectService.get(Long.valueOf(projectId));
			
			String alelrtDate=this.getRequest().getParameter("slAlterAccrualRecord.alelrtDate");
			String fundIntentJsonData = this.getRequest().getParameter("fundIntentJsonData");
			String slActualToChargeJsonData = this.getRequest().getParameter("slActualToChargeJsonData");
			String contractids= this.getRequest().getParameter("contractids");
            	
			List<SlFundIntent> slist=slFundIntentService.getListByIntentDate(Long.valueOf(projectId), "Financing", ">'"+alelrtDate,null);
			for(SlFundIntent s:slist){
				if(s.getFundType().equals("FinancingInterest")){
					s.setIsValid(Short.valueOf("1"));
					s.setIsCheck(Short.valueOf("1"));
					slFundIntentService.save(s);
				}
			}
			project.setAccrualNew(slAlterAccrualRecord.getAccrual().divide(new BigDecimal(30),10,BigDecimal.ROUND_HALF_UP));
			flFinancingProjectService.save(project);
			//将利率变更表中利率变更记录状态修改
			if(slAlterAccrualRecord.getSlAlteraccrualRecordId()==null){
				slAlterAccrualRecord.setCheckStatus(5);
				slAlterAccrualRecord.setProjectId(Long.valueOf(projectId));
				slAlterAccrualRecord.setBaseBusinessType("Financing");
				slAlterAccrualRecordService.save(slAlterAccrualRecord);
			}else{
				SlAlterAccrualRecord record=slAlterAccrualRecordService.get(slAlterAccrualRecord.getSlAlteraccrualRecordId());
				BeanUtil.copyNotNullProperties(record, slAlterAccrualRecord);
				slAlterAccrualRecordService.save(slAlterAccrualRecord);
			}
			List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecord.getSlAlteraccrualRecordId()), "SmallLoan",Long.valueOf(projectId));
			for (SlFundIntent s : slFundIntentsAllsupervise) {
				if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
				slFundIntentService.remove(s);
				}
			}
			if(null != fundIntentJsonData && !"".equals(fundIntentJsonData)){
				String[] slFundIentJsonArr = fundIntentJsonData.split("@");
				for(int i=0; i<slFundIentJsonArr.length; i++) {
					String str = slFundIentJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try{
						SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
						slFundIntent.setSlAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecord.getSlAlteraccrualRecordId()));
						slFundIntent.setProjectId(Long.valueOf(projectId));
						slFundIntent.setBusinessType(project.getBusinessType());
						slFundIntent.setProjectName(project.getProjectName());
						slFundIntent.setProjectNumber(project.getProjectNumber());
						slFundIntent.setCompanyId(project.getCompanyId());
						slFundIntent.setIsValid(Short.valueOf("0"));
						BigDecimal lin = new BigDecimal(0.00);
						if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
				        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
				        }else{
				        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
				        	
				        }
						slFundIntent.setAfterMoney(new BigDecimal(0));
						slFundIntent.setAccrualMoney(new BigDecimal(0));
						slFundIntent.setFlatMoney(new BigDecimal(0));
						slFundIntent.setIsCheck(Short.valueOf("0"));
						if(null==slFundIntent.getFundIntentId()){
							slFundIntentService.save(slFundIntent);
						}else{
							SlFundIntent orgSlFundIntent=slFundIntentService.get(slFundIntent.getFundIntentId());
							BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
							slFundIntentService.save(orgSlFundIntent);
						}
					} catch(Exception e) {
						e.printStackTrace();
						logger.error("SlSuperviseRecordAction:款项信息保存出错"+e.getMessage());
					}
				}
			}
			/*List<SlActualToCharge> alist=new ArrayList<SlActualToCharge>();
			alist=slActualToChargeService.getListByJson(slActualToChargeJsonData, project.getProjectId(), project.getBusinessType(), Short.valueOf("0"), project.getCompanyId());
			for(SlActualToCharge s:alist){
				s.setSlAlteraccrualRecordId(slAlterAccrualRecord.getSlAlteraccrualRecordId());
				slActualToChargeService.save(s);
			}*/
			if(!"".equals(contractids)){
				String [] idArray = contractids.split(",");
				if(idArray.length >0){
					for(int i=0;i<idArray.length;i++ ){
						if(!"".equals(idArray[i])&& idArray[i]!=null && StringUtil.isNumeric(idArray[i])){
							try {
								Object [] obj = {"fl_alteraccrual_record."+slAlterAccrualRecord.getSlAlteraccrualRecordId(),"fl_alteraccrual_record."+projectId,Integer.parseInt(idArray[i])};
								creditBaseDao.excuteSQL("update FileForm set remark=?,mark =? where id =?",obj);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
