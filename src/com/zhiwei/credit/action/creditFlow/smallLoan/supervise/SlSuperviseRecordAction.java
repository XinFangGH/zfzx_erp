package com.zhiwei.credit.action.creditFlow.smallLoan.supervise;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.p2p.loan.P2pLoanProduct;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.DictionaryIndependent;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.product.BpProductParameter;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.customer.person.SpouseService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlAlterAccrualRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.supervise.SlSuperviseRecordService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.p2p.loan.P2pLoanProductService;
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
public class SlSuperviseRecordAction extends BaseAction{
	@Resource
	private SlSuperviseRecordService slSuperviseRecordService;
	private SlSuperviseRecord slSuperviseRecord;
	@Resource
	private  DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private ProDefinitionService proDefinitionService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private AreaDicService areaDicService;
	@Resource
	private SpouseService spouseService;

	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private SlCompanyMainService slCompanyMainService;
	@Resource
	private SlPersonMainService slPersonMainService;
	@Resource
	private VProcreditContractService vProcreditContractService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private SlAlterAccrualRecordService  slAlteraccrualRecordService;
	@Resource
	private SlEarlyRepaymentRecordService  slEarlyRepaymentRecordService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private BpProductParameterService bpProductParameterService;
	@Resource
	private P2pLoanProductService p2pLoanProductService;
	private SlEarlyRepaymentRecord slEarlyRepaymentRecord;
	
	private FlLeaseFinanceProject flLeaseFinanceProject;
	
	private SlSmallloanProject slSmallloanProject;
	private Long projectId;
	private String taskId;
	
	private String businessType;
	
	
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
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SlSuperviseRecord getSlSuperviseRecord() {
		return slSuperviseRecord;
	}

	public void setSlSuperviseRecord(SlSuperviseRecord slSuperviseRecord) {
		this.slSuperviseRecord = slSuperviseRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		
		List<SlSuperviseRecord> list = new ArrayList<SlSuperviseRecord>();
		Type type=new TypeToken<List<SlSuperviseRecord>>(){}.getType();
		
		//◎
		String baseBusinessType = null;
		if(null!=businessType&&!"".equals(businessType)){
			baseBusinessType = businessType;
		}
		if(null!=slSmallloanProject){
			baseBusinessType = slSmallloanProject.getBusinessType();
		}
		 
		if(null!=projectId){
			 list= slSuperviseRecordService.getListByProjectId(projectId,baseBusinessType);
			 if(list.size()>0){
				 for(int i=0;i<list.size();i++){
					 String UserId =list.get(i).getCreator();
					 if(null!= UserId &&!"".equals(UserId)){
						 AppUser appUser =appUserService.get(Long.valueOf(UserId));
						 list.get(i).setAppUserName(appUser.getFullname());
					 }
					
				 }
			 }
			  
		}else{
			 list= slSuperviseRecordService.getAll(filter);//getAll   很危险，会获取所有项目的
			 if(list.size()>0){
				 for(int i=0;i<list.size();i++){
					 String UserId =list.get(i).getCreator();
					 if(null!= UserId &&!"".equals(UserId)){
						 AppUser appUser =appUserService.get(Long.valueOf(UserId));
						 list.get(i).setAppUserName(appUser.getFullname());
					 }
					
				 }
			 }
		}
		List<SlSuperviseRecord> returnList= new  ArrayList<SlSuperviseRecord>();
		for(SlSuperviseRecord sr: list){
			/*if(null!=sr.getAccrualtype() && !"".equals(sr.getAccrualtype())){
				sr.setAccrualtypeName(dictionaryIndependentService.getByDicKey(sr.getAccrualtype()).get(0).getItemValue());
			}
			if(null!=sr.getPayaccrualType()&& !"".equals(sr.getPayaccrualType())){
				
				sr.setPayaccrualTypeName(dictionaryIndependentService.getByDicKey(sr.getPayaccrualType()).get(0).getItemValue());
			}*/
			returnList.add(sr);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(returnList.size()).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(returnList, type));
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
				SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(new Long(id));
				Long projectId=slSuperviseRecord.getProjectId();
				slSuperviseRecordService.remove(new Long(id));
				String businessType=getRequest().getParameter("businessType");
				if(!businessType.equals("") &&businessType !=null){
					List<SlFundIntent>  list=slFundIntentService.getlistbyslSuperviseRecordId(new Long(id), businessType, projectId);
				 	List<SlActualToCharge>  alist=slActualToChargeService.getlistbyslSuperviseRecordId(new Long(id),businessType,projectId);
					for(SlFundIntent s:list){
						slFundIntentService.remove(s);
					}
					for(SlActualToCharge a:alist){
						slActualToChargeService.remove(a);		
					}
					SlSmallloanProject slSmallloanProject=slSmallloanProjectService.get(projectId);
					//展期删除时不包括等额本金和等额本息两种情况
					List<SlFundIntent>  list0=slFundIntentService.getListByOperateId(projectId, businessType, new Long(id), "=0");
					List<SlFundIntent> list1=slFundIntentService.getListByOperateId(projectId, businessType, new Long(id), "=1");
					if(null!=list1 && list1.size()>0){
						SlFundIntent sf1=list1.get(0);
						if(null!=list0 && list0.size()>0){
							SlFundIntent sf0=list0.get(0);
							slSmallloanProject.setProjectStatus(sf0.getProjectStatus());
							slSmallloanProjectService.save(slSmallloanProject);
							sf0.setIncomeMoney(sf0.getIncomeMoney().add(sf1.getIncomeMoney()));
							sf0.setNotMoney(sf0.getNotMoney().add(sf1.getNotMoney()));
							sf0.setOperateId(null);
							sf0.setProjectStatus(null);
							slFundIntentService.save(sf0);
							slFundIntentService.remove(sf1);
							
						}else{
							slSmallloanProject.setProjectStatus(sf1.getProjectStatus());
							slSmallloanProjectService.save(slSmallloanProject);
							sf1.setIsValid(Short.valueOf("0"));
							sf1.setIsCheck(Short.valueOf("0"));
							sf1.setOperateId(null);
							sf1.setProjectStatus(null);
							slFundIntentService.save(sf1);
							
						}
					}
					List<ProcreditContract> plist=vProcreditContractService.getCategoryList(projectId.toString(), businessType, "extenstionContract", new Long(id));
					try {
						for(ProcreditContract category:plist){
							List<ProcreditContract> clist=vProcreditContractService.getContractList(category.getId());
							if(null!=clist && clist.size()>0){
								ProcreditContract procreditContract=clist.get(0);
								creditBaseDao.deleteDatas(procreditContract);
							}
							creditBaseDao.deleteDatas(category);
						}
					} catch (Exception e) {
						
						e.printStackTrace();
					}
					List<FileForm> flist=fileFormService.listFiles(id, "typeiszhanqiws", "typeiszhanqiws."+id, "ExhibitionBusiness");
					for(FileForm form : flist){
						try {
							creditBaseDao.deleteDatas(form);
						} catch (Exception e) {
							e.printStackTrace();
						}
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
		SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slSuperviseRecord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slSuperviseRecord.getId()==null){
			slSuperviseRecordService.save(slSuperviseRecord);
		}else{
			SlSuperviseRecord orgSlSuperviseRecord=slSuperviseRecordService.get(slSuperviseRecord.getId());
			try{
				BeanUtil.copyNotNullProperties(orgSlSuperviseRecord, slSuperviseRecord);
				slSuperviseRecordService.save(orgSlSuperviseRecord);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	//启动展期流程
	public String startRenewalProcess(){
		String str = "";
		if(null != businessType && businessType.equals("SmallLoan")){
			str = slSmallloanProjectService.startRenewalProcess(projectId);
			//str = slSmallloanProjectService.startMatchingFunds(projectId, 20l);
		}else if(null != businessType && businessType.equals("LeaseFinance")){
			str = flLeaseFinanceProjectService.startRenewalProcess(projectId);
		}
		
		jsonString="{success:true,"+str+"}";
		return SUCCESS;
	}
	
	public String getInfo(){
		try{
			String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");
			String task_id=this.getRequest().getParameter("task_id");
			Map<String,Object> map=new HashMap<String,Object>();
			String financeServiceMineName="";
			String managementConsultingMineName="";
			String mineName="";
			if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
				SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
				map.put("slSuperviseRecord", slSuperviseRecord);
			}
			if(null!=projectId){
				SlSmallloanProject project=slSmallloanProjectService.get(projectId);
				map.put("slSmallloanProject", project);
				if(null!=project){
					
					if(null!=project.getFinanceServiceMineType() && !"".equals(project.getFinanceServiceMineType())){
						if(null!=project.getFinanceServiceMineId() && !"".equals(project.getFinanceServiceMineId())){
							if(project.getFinanceServiceMineType().equals("company_ourmain")){
								financeServiceMineName=this.slCompanyMainService.get(project.getFinanceServiceMineId()).getCorName();
							}else{
								financeServiceMineName=this.slPersonMainService.get(project.getFinanceServiceMineId()).getName();
							}
						}
					}
					
					if(null!=project.getManagementConsultingMineType() && !"".equals(project.getManagementConsultingMineType())){
						if(null!=project.getManagementConsultingMineId() && !"".equals(project.getManagementConsultingMineId())){
							if(project.getManagementConsultingMineType().equals("company_ourmain")){
								managementConsultingMineName=this.slCompanyMainService.get(project.getManagementConsultingMineId()).getCorName();
							}else{	
								managementConsultingMineName=this.slPersonMainService.get(project.getManagementConsultingMineId()).getName();
							}
						}
					}
					if(project.getMineType().equals("company_ourmain")){
						
//						mineName=this.companyMainService.get(project.getMineId()).getCorName();//●
						if("true".equals(AppUtil.getSystemIsGroupVersion())){
							mineName = this.organizationService.get(project.getMineId()).getOrgName();
						}else{
							mineName = this.slCompanyMainService.get(project.getMineId()).getCorName();
						}
					}else if (project.getMineType().equals("person_ourmain")){
						
						mineName=this.slPersonMainService.get(project.getMineId()).getName();
					}
					if(null!=project.getOppositeType() && project.getOppositeType().equals("company_customer")){
						if(null!=project.getOppositeID()){
							Enterprise enterprise=enterpriseService.getById(project.getOppositeID().intValue());
							map.put("enterprise", enterprise);
							if(null!=enterprise && null!=enterprise.getLegalpersonid()){
								Person person=personService.getById(enterprise.getLegalpersonid());
								map.put("person", person);
							}
							if(null != enterprise.getHangyeType()) {
								  if(null!=areaDicService.getById(enterprise.getHangyeType())){ 
									  enterprise.setHangyeName(areaDicService.getById(enterprise.getHangyeType()).getText());
								  }
							}
							 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), Short.valueOf("0"), Short.valueOf("0"),Short.valueOf("0"));
					         if(null!=list && list.size()>0){
				        		EnterpriseBank bank=list.get(0);
				        		map.put("enterpriseBank", bank);
				        	}
						}
					}else if(null!=project.getOppositeType() && project.getOppositeType().equals("person_customer")){
						if(null!=project.getOppositeID()){
							Person person=personService.getById(project.getOppositeID().intValue());
							map.put("person", person);
							if(null!=person){
								if(null!=person.getMarry() && person.getMarry()==317){
									Spouse spouse=spouseService.getByPersonId(person.getId());
				        			map.put("spouse", spouse);
								}
							}
							 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), Short.valueOf("1"), Short.valueOf("0"),Short.valueOf("0"));
					         if(null!=list && list.size()>0){
				        		EnterpriseBank bank=list.get(0);
				        		map.put("enterpriseBank", bank);
				        	}
						}
					}
					if(null!=project.getFinanceServiceMineType() && !"".equals(project.getFinanceServiceMineType())){
						if(null!=project.getFinanceServiceMineId() && !"".equals(project.getFinanceServiceMineId())){
							if(project.getFinanceServiceMineType().equals("company_ourmain")){
								financeServiceMineName=this.slCompanyMainService.get(project.getFinanceServiceMineId()).getCorName();
							}else{
								financeServiceMineName=this.slPersonMainService.get(project.getFinanceServiceMineId()).getName();
							}
						}
					}
					
					if(null!=project.getManagementConsultingMineType() && !"".equals(project.getManagementConsultingMineType())){
						if(null!=project.getManagementConsultingMineId() && !"".equals(project.getManagementConsultingMineId())){
							if(project.getManagementConsultingMineType().equals("company_ourmain")){
								managementConsultingMineName=this.slCompanyMainService.get(project.getManagementConsultingMineId()).getCorName();
							}else{	
								managementConsultingMineName=this.slPersonMainService.get(project.getManagementConsultingMineId()).getName();
							}
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
					project.setMineName(textBuffer.toString());
					String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
					String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
					String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
					map.put("businessTypeKey",businessTypeKey);
					map.put("operationTypeKey",operationTypeKey);
					map.put("definitionTypeKey",definitionTypeKey);
					map.put("businessType", project.getBusinessType());
					map.put("financeServiceMineName", financeServiceMineName); 
					map.put("managementConsultingMineName", managementConsultingMineName); 
					map.put("mineName", mineName);
					map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getDescription());
					
					map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
					if(null!=project.getLoanLimit()&&!"".equals(project.getLoanLimit())){
						DictionaryIndependent dic=this.dictionaryIndependentService.getByDicKey(project.getLoanLimit()).get(0);
						if(null!=dic){
							map.put("smallLoanTypeKey", dic.getItemValue());
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
				}
			}
			StringBuffer buff = new StringBuffer("{success:true,data:");
			JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
			buff.append(json.serialize(map));
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getCreditLoanProjectInfo(){
		
		try{
			String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");
			String task_id=this.getRequest().getParameter("task_id");
			Map<String,Object> map=new HashMap<String,Object>();
			String financeServiceMineName="";
			String managementConsultingMineName="";
			//String mineName="";
			if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
				SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
				map.put("slSuperviseRecord", slSuperviseRecord);
			}
			if(null!=projectId){
				SlSmallloanProject project=slSmallloanProjectService.get(projectId);
				map.put("slSmallloanProject", project);
				if(null!=project){
					
			
					if(null!=project.getOppositeType() && project.getOppositeType().equals("company_customer")){
						if(null!=project.getOppositeID()){
							Enterprise enterprise=enterpriseService.getById(project.getOppositeID().intValue());
							map.put("enterprise", enterprise);
							if(null!=enterprise && null!=enterprise.getLegalpersonid()){
								Person person=personService.getById(enterprise.getLegalpersonid());
								map.put("person", person);
							}
							if(null != enterprise.getHangyeType()) {
								  if(null!=areaDicService.getById(enterprise.getHangyeType())){ 
									  enterprise.setHangyeName(areaDicService.getById(enterprise.getHangyeType()).getText());
								  }
							}
							 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), Short.valueOf("0"), Short.valueOf("0"),Short.valueOf("0"));
					         if(null!=list && list.size()>0){
				        		EnterpriseBank bank=list.get(0);
				        		map.put("enterpriseBank", bank);
				        	}
						}
					}else if(null!=project.getOppositeType() && project.getOppositeType().equals("person_customer")){
						if(null!=project.getOppositeID()){
							Person person=personService.getById(project.getOppositeID().intValue());
							map.put("person", person);
							if(null!=person){
								if(null!=person.getMarry() && person.getMarry()==317){
									Spouse spouse=spouseService.getByPersonId(person.getId());
				        			map.put("spouse", spouse);
								}
							}
							 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), Short.valueOf("1"), Short.valueOf("0"),Short.valueOf("0"));
					         if(null!=list && list.size()>0){
				        		EnterpriseBank bank=list.get(0);
				        		map.put("enterpriseBank", bank);
				        	}
						}
					}
					if(null!=project.getFinanceServiceMineType() && !"".equals(project.getFinanceServiceMineType())){
						if(null!=project.getFinanceServiceMineId() && !"".equals(project.getFinanceServiceMineId())){
							if(project.getFinanceServiceMineType().equals("company_ourmain")){
								financeServiceMineName=this.slCompanyMainService.get(project.getFinanceServiceMineId()).getCorName();
							}else{
								financeServiceMineName=this.slPersonMainService.get(project.getFinanceServiceMineId()).getName();
							}
						}
					}
					
					if(null!=project.getManagementConsultingMineType() && !"".equals(project.getManagementConsultingMineType())){
						if(null!=project.getManagementConsultingMineId() && !"".equals(project.getManagementConsultingMineId())){
							if(project.getManagementConsultingMineType().equals("company_ourmain")){
								managementConsultingMineName=this.slCompanyMainService.get(project.getManagementConsultingMineId()).getCorName();
							}else{	
								managementConsultingMineName=this.slPersonMainService.get(project.getManagementConsultingMineId()).getName();
							}
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
					//StringBuffer textBuffer = new StringBuffer (mineName); 
					//project.setMineName(textBuffer.toString());
					String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
					String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
					String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
					map.put("businessTypeKey",businessTypeKey);
					map.put("operationTypeKey",operationTypeKey);
					map.put("definitionTypeKey",definitionTypeKey);
					map.put("businessType", project.getBusinessType());
					map.put("financeServiceMineName", financeServiceMineName); 
					map.put("managementConsultingMineName", managementConsultingMineName); 
				//	map.put("mineName", mineName);
					map.put("flowTypeKey",this.proDefinitionService.getProdefinitionByProcessName(project.getFlowType()).getDescription());
					//产品信息
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
					
					//map.put("mineTypeKey",this.dictionaryIndependentService.getByDicKey(project.getMineType()).get(0).getItemValue());
					if(null!=project.getLoanLimit()&&!"".equals(project.getLoanLimit())){
						DictionaryIndependent dic=this.dictionaryIndependentService.getByDicKey(project.getLoanLimit()).get(0);
						if(null!=dic){
							map.put("smallLoanTypeKey", dic.getItemValue());
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
				}
				BpFundProject ownFund = bpFundProjectService.getByProjectId(Long.valueOf(projectId), Short.valueOf("0"));
				if (null != ownFund) {
					map.put("ownBpFundProject", ownFund);
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
			}
			StringBuffer buff = new StringBuffer("{success:true,data:");
			JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
			buff.append(json.serialize(map));
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
public String getSuperviseRightInfo(){
		
		try{
			String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");
			Map<String,Object> map=new HashMap<String,Object>();
			//String mineName="";
			if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
				SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
				map.put("slSuperviseRecord", slSuperviseRecord);
			}
			if(null!=projectId){
				SlSmallloanProject project=slSmallloanProjectService.get(projectId);
				map.put("slSmallloanProject", project);
			}
			StringBuffer buff = new StringBuffer("{success:true,data:");
			JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
			buff.append(json.serialize(map));
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 融资租赁-展期流程-展期申请节点初始化查询方法
	 * @return
	 */
	public String getFlInfo(){
		try{
			String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");
			String task_id=this.getRequest().getParameter("task_id");
			Map<String,Object> map=new HashMap<String,Object>();
			String financeServiceMineName="";
			String managementConsultingMineName="";
			String mineName="";
			if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
				SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
				map.put("slSuperviseRecord", slSuperviseRecord);
				map.put("businessType",slSuperviseRecord.getBusinessType() );
			}
			if(null!=projectId){
				String appUsersOfA = "";
				FlLeaseFinanceProject project=flLeaseFinanceProjectService.get(projectId);
				map.put("flLeaseFinanceProject", project);
				if(null!=project){
					if(project.getMineType().equals("company_ourmain")){
						if("true".equals(AppUtil.getSystemIsGroupVersion())){
							if(null!=this.organizationService.get(project.getMineId())){
								mineName = this.organizationService.get(project.getMineId()).getOrgName();
							}
						}else{
							if(null!=this.slCompanyMainService.get(project.getMineId())){
								mineName = this.slCompanyMainService.get(project.getMineId()).getCorName();
							}
						}
					}else if (project.getMineType().equals("person_ourmain")){
						
						mineName=this.slPersonMainService.get(project.getMineId()).getName();
					}
					if(null!=project.getOppositeType() && project.getOppositeType().equals("company_customer")){
						if(null!=project.getOppositeID()){
							Enterprise enterprise=enterpriseService.getById(project.getOppositeID().intValue());
							map.put("enterprise", enterprise);
							if(null!=enterprise && null!=enterprise.getLegalpersonid()){
								Person person=personService.getById(enterprise.getLegalpersonid());
								map.put("person", person);
							}
							if(null != enterprise.getHangyeType()) {
								  if(null!=areaDicService.getById(enterprise.getHangyeType())){ 
									  enterprise.setHangyeName(areaDicService.getById(enterprise.getHangyeType()).getText());
								  }
							}
							 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), Short.valueOf("0"), Short.valueOf("0"),Short.valueOf("0"));
					         if(null!=list && list.size()>0){
				        		EnterpriseBank bank=list.get(0);
				        		map.put("enterpriseBank", bank);
				        	}
						}
					}else if(null!=project.getOppositeType() && project.getOppositeType().equals("person_customer")){
						if(null!=project.getOppositeID()){
							Person person=personService.getById(project.getOppositeID().intValue());
							map.put("person", person);
							if(null!=person){
								if(null!=person.getMarry() && person.getMarry()==317){
									Spouse spouse=spouseService.getByPersonId(person.getId());
				        			map.put("spouse", spouse);
								}
							}
							 List<EnterpriseBank> list=enterpriseBankService.getBankList(project.getOppositeID().intValue(), Short.valueOf("1"), Short.valueOf("0"),Short.valueOf("0"));
					         if(null!=list && list.size()>0){
				        		EnterpriseBank bank=list.get(0);
				        		map.put("enterpriseBank", bank);
				        	}
						}
					}
					String appuers="";
					if(null!=project.getAppUserId() && !"".equals(project.getAppUserId())){
							String [] appstr=project.getAppUserId().split(",");
							Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
							for(AppUser s:userSet){
								appUsersOfA+=s.getFamilyName()+",";
							}
							appUsersOfA = appUsersOfA.substring(0, appUsersOfA.length()-1);
					}
					if(appuers.length()>0){
						appuers=appuers.substring(0, appuers.length()-1);
					}
					StringBuffer textBuffer = new StringBuffer (mineName); 
					project.setMineName(textBuffer.toString());
					String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
					String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
					String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
					map.put("businessTypeKey",businessTypeKey);
					map.put("appUsersOfA",appUsersOfA);
					map.put("operationTypeKey",operationTypeKey);
					map.put("definitionTypeKey",definitionTypeKey);
					map.put("financeServiceMineName", financeServiceMineName); 
					map.put("managementConsultingMineName", managementConsultingMineName); 
					map.put("mineName", mineName);
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
				}
			}
			StringBuffer buff = new StringBuffer("{success:true,data:");
			JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
			buff.append(json.serialize(map));
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 融资租赁-展期流程-展期申请节点保存方法
	 * @return
	 */
	public String updateFlInfo(){
		try{
			String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");
			String fundIntentJsonData=this.getRequest().getParameter("fundIntentJsonData");
			if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
				SlSuperviseRecord record=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
				BeanUtil.copyNotNullProperties(record, slSuperviseRecord);
				slSuperviseRecordService.save(record);
				String slActualToChargeJsonData = this.getRequest().getParameter("slActualToChargeJsonData");
				List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
				FlLeaseFinanceProject flProject =  flLeaseFinanceProjectService.get(Long.valueOf(projectId));
				BeanUtil.copyNotNullProperties(flProject,flLeaseFinanceProject);
				slActualToCharges = savejsonchargeFL(slActualToChargeJsonData,record,flLeaseFinanceProject, Short.parseShort("1"));
				for(SlActualToCharge a:slActualToCharges){
	    			slActualToChargeService.save(a);
	    		}
				slSuperviseRecordService.updateSuperviseFundData(projectId,Long.valueOf(slSuperviseRecordId),businessType,fundIntentJsonData);
				String taskId = this.getRequest().getParameter("task_id");
				String comments = this.getRequest().getParameter("comments");
				if (null != taskId && !"".equals(taskId) && null != comments
						&& !"".equals(comments.trim())) {
					this.creditProjectService.saveComments(taskId, comments);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 融资租赁-展期流程-展期款项计划节点保存方法
	 * @return
	 */
	public String flSaveSlFundIntentJson(){
		String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");
		String projectId=this.getRequest().getParameter("projectId");
		String taskId = this.getRequest().getParameter("task_id");
		String slFundIentJson=this.getRequest().getParameter("slFundIentJson");
		FlLeaseFinanceProject p=flLeaseFinanceProjectService.get(Long.valueOf(projectId));
		if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
			SlSuperviseRecord record=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
			try {
				BeanUtil.copyNotNullProperties(record, slSuperviseRecord);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			/*record.setContinuationMoney(slSuperviseRecord.getContinuationMoney());
			record.setStartDate(slSuperviseRecord.getStartDate());
			record.setPayaccrualType(slSuperviseRecord.getPayaccrualType());
			record.setIsStartDatePay(slSuperviseRecord.getIsStartDatePay());
			record.setPayintentPeriod(slSuperviseRecord.getPayintentPeriod());
			record.setPayintentPerioDate(slSuperviseRecord.getPayintentPerioDate());
			record.setEndDate(slSuperviseRecord.getEndDate());
			record.setContinuationRate(slSuperviseRecord.getContinuationRate());
			record.setManagementConsultingOfRate(slSuperviseRecord.getManagementConsultingOfRate());
			record.setAccrualtype(slSuperviseRecord.getAccrualtype());
			record.setOverdueRate(slSuperviseRecord.getOverdueRate());
			record.setOverdueRateLoan(slSuperviseRecord.getOverdueRateLoan());
			record.setIsPreposePayConsultingCheck(slSuperviseRecord.getIsPreposePayConsultingCheck());
			record.setCheckStatus(5);*/
			//StatsPro.calcuProEndDate(record);
			slSuperviseRecordService.save(record);
			String slActualToChargeJsonData = this.getRequest().getParameter("slActualToChargeJsonData");
			List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
			FlLeaseFinanceProject flProject =  flLeaseFinanceProjectService.get(Long.valueOf(projectId));
			slActualToCharges = savejsonchargeFL(slActualToChargeJsonData,record,flProject, Short.parseShort("0"));
			for(SlActualToCharge a:slActualToCharges){
    			slActualToChargeService.save(a);
    		}
			List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslSuperviseRecordId(Long.valueOf(slSuperviseRecordId), "LeaseFinance",Long.valueOf(projectId));
			for (SlFundIntent s : slFundIntentsAllsupervise) {
				if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
					slFundIntentService.remove(s);
				}
			}
			if(null != slFundIentJson && !"".equals(slFundIentJson)) {
				String[] slFundIentJsonArr = slFundIentJson.split("@");
				for(int i=0; i<slFundIentJsonArr.length; i++) {
					String str = slFundIentJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try{
						SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
						slFundIntent.setSlSuperviseRecordId(Long.valueOf(slSuperviseRecordId));
						slFundIntent.setProjectId(Long.valueOf(projectId));
						slFundIntent.setBusinessType(p.getBusinessType());
						slFundIntent.setProjectName(p.getProjectName());
						slFundIntent.setProjectNumber(p.getProjectNumber());
						slFundIntent.setCompanyId(p.getCompanyId());
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
						logger.error("SlSuperviseRecordAction:"+e.getMessage());
					}
					
				}
			}
			/*List<SlFundIntent> slist=slFundIntentService.getListByFundType(p.getProjectId(), p.getBusinessType(), "principalRepayment",Long.valueOf(slSuperviseRecordId));
			for(SlFundIntent f:slist){
				if(null==f.getSlSuperviseRecordId()){
					f.setIsValid(Short.valueOf("1"));
					f.setIsCheck(Short.valueOf("1"));
				}
				if(null!=f.getSlSuperviseRecordId() && !f.getSlSuperviseRecordId().toString().equals(slSuperviseRecordId)){
					f.setIsValid(Short.valueOf("1"));
					f.setIsCheck(Short.valueOf("1"));
				}
				slFundIntentService.save(f);
			}*/
		}
		String comments = this.getRequest().getParameter("comments");
		if (null != taskId && !"".equals(taskId) && null != comments
				&& !"".equals(comments.trim())) {
			this.creditProjectService.saveComments(taskId, comments);
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}

	public String updateInfo(){
		
		try{
			String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");
			if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
				SlSuperviseRecord record=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
				BeanUtil.copyNotNullProperties(record, slSuperviseRecord);
				slSuperviseRecordService.save(record);
				String slActualToChargeJsonData = this.getRequest().getParameter("slActualToChargeJson");
				BpFundProject fundProject =  bpFundProjectService.get(record.getProjectId());
				//保存费用信息
				if(null!=slActualToChargeJsonData && !slActualToChargeJsonData.equals("")){
					slActualToChargeService.savejson(slActualToChargeJsonData, fundProject.getProjectId(), fundProject.getBusinessType(), Short.valueOf("1"), fundProject.getCompanyId());
				}
			//保存款项信息
				String slFundIentJson=this.getRequest().getParameter("slFundIentJsonData");
				if(null != slFundIentJson && !"".equals(slFundIentJson)) {
					List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslSuperviseRecordId(record.getId(), "SmallLoan",fundProject.getProjectId());
					for (SlFundIntent s : slFundIntentsAllsupervise) {
						slFundIntentService.remove(s);
					}
					String[] slFundIentJsonArr = slFundIentJson.split("@");
					
					for(int i=0; i<slFundIentJsonArr.length; i++) {
						String str = slFundIentJsonArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						
						SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
						slFundIntent.setSlSuperviseRecordId(Long.valueOf(slSuperviseRecordId));
						slFundIntent.setProjectId(fundProject.getProjectId());
						slFundIntent.setBusinessType(fundProject.getBusinessType());
						slFundIntent.setProjectName(fundProject.getProjectName());
						slFundIntent.setProjectNumber(fundProject.getProjectNumber());
						slFundIntent.setCompanyId(fundProject.getCompanyId());
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
					}
				}
				String taskId = this.getRequest().getParameter("task_id");
				String comments = this.getRequest().getParameter("comments");
				if (null != taskId && !"".equals(taskId) && null != comments
						&& !"".equals(comments.trim())) {
					this.creditProjectService.saveComments(taskId, comments);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String saveSlFundIntentJson(){
		String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");
		String projectId=this.getRequest().getParameter("projectId");
		String taskId = this.getRequest().getParameter("task_id");
		String slFundIentJson=this.getRequest().getParameter("slFundIentJson");
		SlSmallloanProject p=slSmallloanProjectService.get(Long.valueOf(projectId));
		if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
			SlSuperviseRecord record=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
			try {
				BeanUtil.copyNotNullProperties(record, slSuperviseRecord);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/*record.setContinuationMoney(slSuperviseRecord.getContinuationMoney());
			record.setStartDate(slSuperviseRecord.getStartDate());
			record.setPayaccrualType(slSuperviseRecord.getPayaccrualType());
			record.setIsStartDatePay(slSuperviseRecord.getIsStartDatePay());
			record.setPayintentPeriod(slSuperviseRecord.getPayintentPeriod());
			record.setPayintentPerioDate(slSuperviseRecord.getPayintentPerioDate());
			record.setEndDate(slSuperviseRecord.getEndDate());
			record.setContinuationRate(slSuperviseRecord.getContinuationRate());
			record.setManagementConsultingOfRate(slSuperviseRecord.getManagementConsultingOfRate());
			record.setAccrualtype(slSuperviseRecord.getAccrualtype());
			record.setOverdueRate(slSuperviseRecord.getOverdueRate());
			record.setOverdueRateLoan(slSuperviseRecord.getOverdueRateLoan());
			record.setIsPreposePayConsultingCheck(slSuperviseRecord.getIsPreposePayConsultingCheck());
			record.setCheckStatus(5);*/
			//StatsPro.calcuProEndDate(record);
			slSuperviseRecordService.save(record);
			String slActualToChargeJsonData = this.getRequest().getParameter("slActualToChargeJsonData");
			List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
			SlSmallloanProject slSmallloanProject =  slSmallloanProjectService.get(Long.valueOf(projectId));
			slActualToCharges = savejsoncharge(slActualToChargeJsonData,record,slSmallloanProject, Short.parseShort("0"));
			for(SlActualToCharge a:slActualToCharges){
    			slActualToChargeService.save(a);
    		}
			List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslSuperviseRecordId(Long.valueOf(slSuperviseRecordId), "SmallLoan",Long.valueOf(projectId));
			for (SlFundIntent s : slFundIntentsAllsupervise) {
				slFundIntentService.remove(s);
			}
			if(null != slFundIentJson && !"".equals(slFundIentJson)) {

				String[] slFundIentJsonArr = slFundIentJson.split("@");
				
				for(int i=0; i<slFundIentJsonArr.length; i++) {
					String str = slFundIentJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try{
						SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
						slFundIntent.setSlSuperviseRecordId(Long.valueOf(slSuperviseRecordId));
						slFundIntent.setProjectId(Long.valueOf(projectId));
						slFundIntent.setBusinessType(p.getBusinessType());
						slFundIntent.setProjectName(p.getProjectName());
						slFundIntent.setProjectNumber(p.getProjectNumber());
						slFundIntent.setCompanyId(p.getCompanyId());
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
						logger.error("SlSuperviseRecordAction:"+e.getMessage());
					}
					
				}
			}
			/*List<SlFundIntent> slist=slFundIntentService.getListByFundType(p.getProjectId(), p.getBusinessType(), "principalRepayment",Long.valueOf(slSuperviseRecordId));
			for(SlFundIntent f:slist){
				if(null==f.getSlSuperviseRecordId()){
					f.setIsValid(Short.valueOf("1"));
					f.setIsCheck(Short.valueOf("1"));
				}
				if(null!=f.getSlSuperviseRecordId() && !f.getSlSuperviseRecordId().toString().equals(slSuperviseRecordId)){
					f.setIsValid(Short.valueOf("1"));
					f.setIsCheck(Short.valueOf("1"));
				}
				slFundIntentService.save(f);
			}*/
		}
		
		
		String comments = this.getRequest().getParameter("comments");
		if (null != taskId && !"".equals(taskId) && null != comments
				&& !"".equals(comments.trim())) {
			this.creditProjectService.saveComments(taskId, comments);
		}
		setJsonString("{success:true}");
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
		//查出微贷展期的展期数据
		SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.valueOf(loanedTypeId));
		map.put("slSuperviseRecord", slSuperviseRecord); 
		
		map.put("slSmallloanProject", project); 
		map.put("mineName", mineName);
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
		
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	//微贷展期流程展期申请节点保存方法
	public String updateMiroSuperviseRecord(){
		try{
			Long slProjectId = Long.valueOf(this.getRequest().getParameter("projectId"));
			Long slSuperviseRecordId =Long.valueOf(this.getRequest().getParameter("projectId_flow"));
			if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
				SlSuperviseRecord sls=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
				sls.setContinuationMoney(slSuperviseRecord.getContinuationMoney());
				sls.setStartDate(slSuperviseRecord.getStartDate());
				sls.setPayaccrualType(slSuperviseRecord.getPayaccrualType());
				sls.setIsStartDatePay(slSuperviseRecord.getIsStartDatePay());
				sls.setPayintentPeriod(slSuperviseRecord.getPayintentPeriod());
				sls.setPayintentPerioDate(slSuperviseRecord.getPayintentPerioDate());
				sls.setEndDate(slSuperviseRecord.getEndDate());
				sls.setContinuationRate(slSuperviseRecord.getContinuationRate());
				sls.setManagementConsultingOfRate(slSuperviseRecord.getManagementConsultingOfRate());
				sls.setAccrualtype(slSuperviseRecord.getAccrualtype());
				sls.setOverdueRate(slSuperviseRecord.getOverdueRate());
				sls.setContinuationRateNew(slSuperviseRecord.getContinuationRateNew());
				sls.setOverdueRateLoan(slSuperviseRecord.getOverdueRateLoan());
				sls.setIsPreposePayConsultingCheck(slSuperviseRecord.getIsPreposePayConsultingCheck());
				//StatsPro.calcuProEndDate(sls);
				slSuperviseRecordService.save(sls);
				String taskId = this.getRequest().getParameter("task_id");
				String comments = this.getRequest().getParameter("comments");
				if (null != taskId && !"".equals(taskId) && null != comments
						&& !"".equals(comments.trim())) {
					this.creditProjectService.saveComments(taskId, comments);
				}
				
			}
			return SUCCESS;
		}catch(Exception e ){
			e.printStackTrace();
			logger.error("微贷展期节点保存/更新出错:"+e.getMessage());
			return "error";
		}
		
	}
	//微贷展期流程确认展期终审通过意见及复核展期终审通过意见节点保存方法
	public String updateConfirmCommentsMiroSuperviseRecord(){
		try{
			Long slProjectId = Long.valueOf(this.getRequest().getParameter("projectId"));
			Long slSuperviseRecordId =Long.valueOf(this.getRequest().getParameter("projectId_flow"));
			String bussinessType = this.getRequest().getParameter("businessType_flow");
			//setSlSmallloanProject(this.slSmallloanProjectService.get(slProjectId));
			if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
				SlSuperviseRecord record=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
				record.setContinuationMoney(slSuperviseRecord.getContinuationMoney());
				record.setStartDate(slSuperviseRecord.getStartDate());
				record.setPayaccrualType(slSuperviseRecord.getPayaccrualType());
				record.setIsStartDatePay(slSuperviseRecord.getIsStartDatePay());
				record.setPayintentPeriod(slSuperviseRecord.getPayintentPeriod());
				record.setPayintentPerioDate(slSuperviseRecord.getPayintentPerioDate());
				record.setEndDate(slSuperviseRecord.getEndDate());
				record.setContinuationRate(slSuperviseRecord.getContinuationRate());
				record.setManagementConsultingOfRate(slSuperviseRecord.getManagementConsultingOfRate());
				record.setAccrualtype(slSuperviseRecord.getAccrualtype());
				record.setOverdueRate(slSuperviseRecord.getOverdueRate());
				record.setContinuationRateNew(slSuperviseRecord.getContinuationRateNew());
				record.setOverdueRateLoan(slSuperviseRecord.getOverdueRateLoan());
				record.setIsPreposePayConsultingCheck(slSuperviseRecord.getIsPreposePayConsultingCheck());
				slSuperviseRecord.setProjectId(slProjectId);
				slSuperviseRecord.setBusinessType(bussinessType);
				//StatsPro.calcuProEndDate(record);
				slSuperviseRecordService.save(record);
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
			logger.error("微贷确认展期终审通过意见节点（或复核展期终审通过意见）保存/更新出错:"+e.getMessage());
			return "error";
		}
		
	}
	
	//微贷展期款项计划确认保存方法
	public String updateMiroSuperviseRecordIntentaffirm(){
		try{
			String slProjectId = this.getRequest().getParameter("projectId");
			String slSuperviseRecordId =this.getRequest().getParameter("projectId_flow");
			String slFundIentJson=this.getRequest().getParameter("fundIntentJsonData");
			SlSmallloanProject persistent=slSmallloanProjectService.get(Long.valueOf(slProjectId));
			Long companyId =persistent.getCompanyId();
			if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
				SlSuperviseRecord record=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
				BeanUtil.copyNotNullProperties(record, slSuperviseRecordId);
				/*record.setContinuationMoney(slSuperviseRecord.getContinuationMoney());
				record.setStartDate(slSuperviseRecord.getStartDate());
				record.setPayaccrualType(slSuperviseRecord.getPayaccrualType());
				record.setIsStartDatePay(slSuperviseRecord.getIsStartDatePay());
				record.setPayintentPeriod(slSuperviseRecord.getPayintentPeriod());
				record.setPayintentPerioDate(slSuperviseRecord.getPayintentPerioDate());
				record.setEndDate(slSuperviseRecord.getEndDate());
				record.setContinuationRate(slSuperviseRecord.getContinuationRate());
				record.setManagementConsultingOfRate(slSuperviseRecord.getManagementConsultingOfRate());
				record.setAccrualtype(slSuperviseRecord.getAccrualtype());
				record.setOverdueRate(slSuperviseRecord.getOverdueRate());
				record.setOverdueRateLoan(slSuperviseRecord.getOverdueRateLoan());
				record.setIsPreposePayConsultingCheck(slSuperviseRecord.getIsPreposePayConsultingCheck());*/
				//StatsPro.calcuProEndDate(record);
				slSuperviseRecordService.save(record);
				List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslSuperviseRecordId(Long.valueOf(slSuperviseRecordId), "SmallLoan",Long.valueOf(slProjectId));
				for (SlFundIntent s : slFundIntentsAllsupervise) {
					slFundIntentService.remove(s);
				}
				if(null != slFundIentJson && !"".equals(slFundIentJson)) {
	
					String[] slFundIentJsonArr = slFundIentJson.split("@");
					
					for(int i=0; i<slFundIentJsonArr.length; i++) {
						String str = slFundIentJsonArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						try{
							SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
							slFundIntent.setSlSuperviseRecordId(Long.valueOf(slSuperviseRecordId));
							slFundIntent.setProjectId(Long.valueOf(slProjectId));
							slFundIntent.setBusinessType(persistent.getBusinessType());
							slFundIntent.setProjectName(persistent.getProjectName());
							slFundIntent.setProjectNumber(persistent.getProjectNumber());
							slFundIntent.setCompanyId(companyId);
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
				/*List<SlFundIntent> slist=slFundIntentService.getListByFundType(persistent.getProjectId(), persistent.getBusinessType(), "principalRepayment",Long.valueOf(slSuperviseRecordId));
				for(SlFundIntent f:slist){
					if(null==f.getSlSuperviseRecordId()){
						f.setIsValid(Short.valueOf("1"));
						f.setIsCheck(Short.valueOf("1"));
					}
					if(null!=f.getSlSuperviseRecordId() && !f.getSlSuperviseRecordId().toString().equals(slSuperviseRecordId)){
						f.setIsValid(Short.valueOf("1"));
						f.setIsCheck(Short.valueOf("1"));
					}
					slFundIntentService.save(f);
				}*/
			}
			//意见与说明保存方法
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 融资租赁 -保存手续费用收取清单方法
	 * @param slActualToChargeJsonData
	 * @param record
	 * @param flProject
	 * @param flag
	 * @return
	 */
	public List<SlActualToCharge> savejsonchargeFL(
			String slActualToChargeJsonData,
			SlSuperviseRecord record,FlLeaseFinanceProject flProject,Short flag) {
		List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
		if (null != slActualToChargeJsonData
				&& !"".equals(slActualToChargeJsonData)) {
			String[] shareequityArr = slActualToChargeJsonData.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				String[] strArr = str.split(",");
				String typestr = "";
				if (strArr.length == 7) {
					typestr = strArr[1];
				} else {
					typestr = strArr[0];
				}
				String typeId = "";
				String typename = "";
				if (typestr.endsWith("\"") == true) {
					typename = typestr.substring(typestr.indexOf(":") + 2,
							typestr.length() - 1);
				} else {
					typeId = typestr.substring(typestr.indexOf(":") + 1,
							typestr.length());
				}
				SlPlansToCharge slPlansToCharge = new SlPlansToCharge();

				if (!typename.equals("")) {
					List<SlPlansToCharge> list = slPlansToChargeService
							.getAll();
					int k = 0;
					for (SlPlansToCharge p : list) {
						if (p.getName().equals(typename)
								&& p.getBusinessType().equals("LeaseFinance")) {
							k++;
						}
					}
					if (k == 0) {
						slPlansToCharge.setName(typename);
						slPlansToCharge.setIsType(1);
						slPlansToCharge.setIsValid(0);
						slPlansToCharge.setBusinessType("LeaseFinance");
						slPlansToChargeService.save(slPlansToCharge);
						if (strArr.length == 9) {
							str = "{";
							for (int j = 0; j <= strArr.length - 2; j++) {
								if (j != 1) {
									str = strArr[j] + ",";
								}
							}
							str = str + strArr[strArr.length - 1];

						} else {
							str = "{";
							for (int j = 1; j <= strArr.length - 2; j++) {
								str = str + strArr[j] + ",";
							}
							str = str + strArr[strArr.length - 1];
						}
					}
				} else {
					long typeid = Long.parseLong(typeId);
					slPlansToCharge = slPlansToChargeService.get(typeid);
				}
				JSONParser parser = new JSONParser(new StringReader(str));

				try {
					SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper.toJava(parser.nextValue(), SlActualToCharge.class);

					slActualToCharge.setProjectId(record.getProjectId());
					slActualToCharge.setProjectName(flProject.getProjectName());
					slActualToCharge.setProjectNumber(flProject.getProjectNumber());

					slActualToCharge.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
					if (null == slActualToCharge.getActualChargeId()) {

						slActualToCharge.setAfterMoney(new BigDecimal(0));
						slActualToCharge.setFlatMoney(new BigDecimal(0));
						if (slActualToCharge.getIncomeMoney().equals(
								new BigDecimal(0.00))) {
							slActualToCharge.setNotMoney(slActualToCharge
									.getPayMoney());
						} else {
							slActualToCharge.setNotMoney(slActualToCharge
									.getIncomeMoney());
						}
						slActualToCharge.setBusinessType("LeaseFinance");
						slActualToCharge.setCompanyId(record
								.getCompanyId());
						slActualToCharge.setIsCheck(flag);
						slActualToCharge.setSlSuperviseRecordId(record.getId());
						slActualToCharges.add(slActualToCharge);
					} else {
						SlActualToCharge slActualToCharge1 = slActualToChargeService
								.get(slActualToCharge.getActualChargeId());
						if (slActualToCharge1.getAfterMoney().compareTo(
								new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slActualToCharge1,
									slActualToCharge);
							if (slActualToCharge1.getIncomeMoney().equals(
									new BigDecimal(0.00))) {
								slActualToCharge1.setNotMoney(slActualToCharge
										.getPayMoney());
							} else {
								slActualToCharge1.setNotMoney(slActualToCharge
										.getIncomeMoney());
							}
							slActualToCharge1.setPlanChargeId(slPlansToCharge
									.getPlansTochargeId());
							slActualToCharge1.setBusinessType("LeaseFinance");
							slActualToCharge1.setCompanyId(flProject
									.getCompanyId());
							slActualToCharge1.setIsCheck(flag);
							slActualToCharge.setSlSuperviseRecordId(record.getId());
							slActualToCharges.add(slActualToCharge1);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return slActualToCharges;
	}
	
	public List<SlActualToCharge> savejsoncharge(
			String slActualToChargeJsonData,
			SlSuperviseRecord record,SlSmallloanProject slSmallloanProject,Short flag) {
		List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
		if (null != slActualToChargeJsonData
				&& !"".equals(slActualToChargeJsonData)) {

			String[] shareequityArr = slActualToChargeJsonData.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				String[] strArr = str.split(",");
				String typestr = "";
				if (strArr.length == 7) {
					typestr = strArr[1];
				} else {
					typestr = strArr[0];
				}
				String typeId = "";
				String typename = "";
				if (typestr.endsWith("\"") == true) {
					typename = typestr.substring(typestr.indexOf(":") + 2,
							typestr.length() - 1);
				} else {
					typeId = typestr.substring(typestr.indexOf(":") + 1,
							typestr.length());
				}
				SlPlansToCharge slPlansToCharge = new SlPlansToCharge();

				if (!typename.equals("")) {

					List<SlPlansToCharge> list = slPlansToChargeService
							.getAll();
					int k = 0;
					for (SlPlansToCharge p : list) {
						if (p.getName().equals(typename)
								&& p.getBusinessType().equals("SmallLoan")) {
							k++;
						}
					}

					if (k == 0) {
						slPlansToCharge.setName(typename);
						slPlansToCharge.setIsType(1);
						slPlansToCharge.setIsValid(0);
						slPlansToCharge.setBusinessType("SmallLoan");
						slPlansToChargeService.save(slPlansToCharge);
						if (strArr.length == 9) {
							str = "{";
							for (int j = 0; j <= strArr.length - 2; j++) {
								if (j != 1) {
									str = strArr[j] + ",";
								}
							}
							str = str + strArr[strArr.length - 1];

						} else {
							str = "{";
							for (int j = 1; j <= strArr.length - 2; j++) {
								str = str + strArr[j] + ",";
							}
							str = str + strArr[strArr.length - 1];
						}
					}
				} else {
					long typeid = Long.parseLong(typeId);
					slPlansToCharge = slPlansToChargeService.get(typeid);

				}

				JSONParser parser = new JSONParser(new StringReader(str));

				try {

					SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper.toJava(parser.nextValue(), SlActualToCharge.class);

					slActualToCharge.setProjectId(record.getProjectId());
					slActualToCharge.setProjectName(slSmallloanProject.getProjectName());
					slActualToCharge.setProjectNumber(slSmallloanProject.getProjectNumber());

					slActualToCharge.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
					if (null == slActualToCharge.getActualChargeId()) {

						slActualToCharge.setAfterMoney(new BigDecimal(0));
						slActualToCharge.setFlatMoney(new BigDecimal(0));
						if (slActualToCharge.getIncomeMoney().equals(
								new BigDecimal(0.00))) {
							slActualToCharge.setNotMoney(slActualToCharge
									.getPayMoney());
						} else {
							slActualToCharge.setNotMoney(slActualToCharge
									.getIncomeMoney());
						}
						slActualToCharge.setBusinessType("SmallLoan");
						slActualToCharge.setCompanyId(record
								.getCompanyId());
						slActualToCharge.setIsCheck(flag);
						slActualToCharge.setSlSuperviseRecordId(record.getId());
						slActualToCharges.add(slActualToCharge);
					} else {

						SlActualToCharge slActualToCharge1 = slActualToChargeService
								.get(slActualToCharge.getActualChargeId());
						if (slActualToCharge1.getAfterMoney().compareTo(
								new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slActualToCharge1,
									slActualToCharge);
							if (slActualToCharge1.getIncomeMoney().equals(
									new BigDecimal(0.00))) {
								slActualToCharge1.setNotMoney(slActualToCharge
										.getPayMoney());
							} else {
								slActualToCharge1.setNotMoney(slActualToCharge
										.getIncomeMoney());
							}
							slActualToCharge1.setPlanChargeId(slPlansToCharge
									.getPlansTochargeId());
							slActualToCharge1.setBusinessType("SmallLoan");
							slActualToCharge1.setCompanyId(slSmallloanProject
									.getCompanyId());
							slActualToCharge1.setIsCheck(flag);
							slActualToCharge.setSlSuperviseRecordId(record.getId());
							slActualToCharges.add(slActualToCharge1);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		}

		return slActualToCharges;
	}
	
	/***
	 * 融资租赁--提前还款流程--提前还款申请VM初始化方法
	 * @return
	 */
	public String getInfoAheadRefund(){
		String projectId=this.getRequest().getParameter("projectId"); //项目ID
		String task_id=this.getRequest().getParameter("task_id");  //	任务ID 
		String loanedTypeId=this.getRequest().getParameter("loanedTypeId");  //	贷后类型的id
		String loanedTypeKey=this.getRequest().getParameter("loanedTypeKey");  //	贷后的类型
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
					mineName=this.slCompanyMainService.get(project.getMineId()).getCorName();
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
			appuers=appuers.substring(0, appuers.length()-1);}
			StringBuffer textBuffer = new StringBuffer (mineName); 
			project.setMineName(textBuffer.toString());
					
			if(loanedTypeKey.equals("slAlteraccrual")){
				SlAlterAccrualRecord slAlterAccrualRecord=slAlteraccrualRecordService.get(Long.valueOf(loanedTypeId));
				map.put("slAlterAccrualRecord", slAlterAccrualRecord); 
			}else if(loanedTypeKey.equals("earlyReyment")){
				SlEarlyRepaymentRecord slEarlyRepaymentRecord=slEarlyRepaymentRecordService.get(Long.valueOf(loanedTypeId));
				map.put("slEarlyRepaymentRecord", slEarlyRepaymentRecord);
				List<SlFundIntent> slist=slFundIntentService.getlistbyslEarlyRepaymentId(Long.valueOf(loanedTypeId), "LeaseFinance", project.getProjectId());
				Long slSuperviseRecordId=null;
				if(null!=slist && slist.size()>0){
					SlFundIntent sf=slist.get(0);
					if(null!=sf.getSlSuperviseRecordId()){
						slSuperviseRecordId=sf.getSlSuperviseRecordId();
					}else{
						slSuperviseRecordId=new Long(0);
					}
				}
				map.put("payintentPeriod", slSuperviseRecordId);
			}
		String businessTypeKey = creditProjectService.getGlobalTypeValue("businessType", project.getBusinessType(), project.getProjectId());
		String operationTypeKey = creditProjectService.getGlobalTypeValue("operationType", project.getBusinessType(), project.getProjectId());
		String definitionTypeKey = creditProjectService.getGlobalTypeValue("definitionType", project.getBusinessType(), project.getProjectId());
		map.put("flLeaseFinanceProject", project); 
		map.put("appUsersOfA",appUsersOfA);
		map.put("mineName", mineName);
		map.put("financeServiceMineName", financeServiceMineName); 
		map.put("managementConsultingMineName", managementConsultingMineName); 
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
	
	/***
	 * 融资租赁--提前还款流程(提前还款申请节点保存方法)
	 */
	public String askForEarlyRepaymentFlow() { 
		try {
			Long proId = Long.valueOf(this.getRequest().getParameter(
					"projectId"));
			String fundIntentJsonDataSuperviseRecord = this.getRequest()
					.getParameter("intent_plan_earlyRepayment");
			flLeaseFinanceProject = this.flLeaseFinanceProjectService.get(proId);
			List<SlFundIntent> slFundIntentsSuperviseRecord = savejsonintent(
					fundIntentJsonDataSuperviseRecord, flLeaseFinanceProject,
					Short.parseShort("1"));
			
			this.slSmallloanProjectService.askForEarlyRepaymentProjectFlow(
					proId, null, null, null, slFundIntentsSuperviseRecord,
					slEarlyRepaymentRecord, null);
			saveComment();
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("融资租赁--提前还款流程--提前还款申请节点保存信息出错:" + e.getMessage());
			return "error";
		}
	}
	
	private void saveComment() {
		String taskId = this.getRequest().getParameter("task_id");
		String comments = this.getRequest().getParameter("comments");
		if (null != taskId && !"".equals(taskId) && null != comments
				&& !"".equals(comments.trim())) {
			this.creditProjectService.saveComments(taskId, comments);
		}
	}
	
	public List<SlFundIntent> savejsonintent(String fundIntentJsonData,
			FlLeaseFinanceProject flLeaseFinanceProject, Short flag) {
		List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
		if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
			String[] shareequityArr = fundIntentJsonData.split("@");
			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(flLeaseFinanceProject.getProjectId());
					SlFundIntent1.setProjectName(flLeaseFinanceProject.getProjectName());
					SlFundIntent1.setProjectNumber(flLeaseFinanceProject.getProjectNumber());
					if (null == SlFundIntent1.getFundIntentId()) {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						}
						SlFundIntent1.setAfterMoney(new BigDecimal(0));
						SlFundIntent1.setAccrualMoney(new BigDecimal(0));
						SlFundIntent1.setFlatMoney(new BigDecimal(0));
						Short isvalid = 0;
						SlFundIntent1.setIsValid(isvalid);
						SlFundIntent1.setIsCheck(flag);
						SlFundIntent1.setBusinessType("LeaseFinance");
						SlFundIntent1.setCompanyId(flLeaseFinanceProject.getCompanyId());
						slFundIntents.add(SlFundIntent1);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						}
						SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
						if (slFundIntent2.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
							slFundIntent2.setBusinessType("LeaseFinance");
							slFundIntent2.setCompanyId(flLeaseFinanceProject.getCompanyId());
							slFundIntent2.setIsCheck(flag);
							slFundIntents.add(slFundIntent2);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return slFundIntents;
	}
	
	public String getIsChecked(){
		String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");
		String projectId=this.getRequest().getParameter("projectId");
		List<SlFundIntent> list=slFundIntentService.getlistbyslSuperviseRecordId(Long.valueOf(slSuperviseRecordId), "SmallLoan", Long.valueOf(projectId));
		StringBuffer sb=new StringBuffer("{success:true");
		for(SlFundIntent s:list){
			if(s.getAfterMoney().compareTo(new BigDecimal(0))!=0){
				sb.append(",isChecked:true");
				break;
			}
		}
		sb.append("}");
		jsonString=sb.toString();
		return SUCCESS;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setSlSmallloanProject(SlSmallloanProject slSmallloanProject) {
		this.slSmallloanProject = slSmallloanProject;
	}

	public SlSmallloanProject getSlSmallloanProject() {
		return slSmallloanProject;
	}

	public FlLeaseFinanceProject getFlLeaseFinanceProject() {
		return flLeaseFinanceProject;
	}

	public void setFlLeaseFinanceProject(FlLeaseFinanceProject flLeaseFinanceProject) {
		this.flLeaseFinanceProject = flLeaseFinanceProject;
	}

	public SlEarlyRepaymentRecord getSlEarlyRepaymentRecord() {
		return slEarlyRepaymentRecord;
	}

	public void setSlEarlyRepaymentRecord(
			SlEarlyRepaymentRecord slEarlyRepaymentRecord) {
		this.slEarlyRepaymentRecord = slEarlyRepaymentRecord;
	}
	
	
}
