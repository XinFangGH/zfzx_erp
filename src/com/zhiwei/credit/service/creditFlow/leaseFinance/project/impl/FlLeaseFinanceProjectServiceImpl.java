package com.zhiwei.credit.service.creditFlow.leaseFinance.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;
import org.springframework.beans.BeanUtils;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.dao.creditFlow.common.GlobalSupervisemanageDao;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlActualToChargeDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.project.FlLeaseFinanceProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlAlterAccrualRecordDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.meeting.SlConferenceRecordDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.supervise.SlSuperviseRecordDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.dao.flow.TaskSignDataDao;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.finance.VFundDetail;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseobjectInfo;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.VLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.customer.bankRelationPerson.CustomerBankRelationPersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.VFundDetailService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.system.OrganizationService;

/**
 * 
 * @author 
 *
 */
public class FlLeaseFinanceProjectServiceImpl extends BaseServiceImpl<FlLeaseFinanceProject> implements FlLeaseFinanceProjectService{
	@SuppressWarnings("unused")
	private FlLeaseFinanceProjectDao dao;
	
	@Resource
	private PersonDao personDao;
	@Resource
	private EnterpriseDao enterpriseDao;
	@Resource
	private SlActualToChargeDao slActualToChargeDao;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private TaskSignDataDao taskSignDataDao;
	@Resource
	private OrganizationDao organizationdao;
	@Resource
	private SlAlterAccrualRecordDao slAlterAccrualRecordDao;
	@Resource
	private EnterpriseShareequityDao enterpriseShareequityDao;
	@Resource
	private CustomerBankRelationPersonService customerBankRelationPersonService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private GlobalSupervisemanageDao globalSupervisemanageDao;
	
	@Resource
	private SlSuperviseRecordDao slSuperviseRecordDao;
	@Resource
	private ProDefinitionDao proDefinitionDao;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource(name = "flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;
	@Resource
	private SlEarlyRepaymentRecordService slEarlyRepaymentRecordService;
	@Resource
	private VFundDetailService vFundDetailService;
	
	
	
	@Resource
	private SlConferenceRecordDao slConferenceRecordDao;
	
	public FlLeaseFinanceProjectServiceImpl(FlLeaseFinanceProjectDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public List<FlLeaseFinanceProject> getProjectById(Long projectId) {
		return dao.getProjectById(projectId);
	}
	
	
	public VLeaseFinanceProject getViewByProjectId(Long projectId){
		return this.dao.getViewByProjectId(projectId);
	}
	
	//改变项目的companyId    由于多表关联此Id ，同时个别表包含companyId字段，所以要同时更新   目前只知道这两个关联●№○add by gao
	public void updateCompanyId(Long companyId,FlLeaseFinanceProject fl){
		List<SlActualToCharge> list1 = slActualToChargeService.getAllbyProjectId(fl.getProjectId(),fl.getBusinessType());
		List<SlFundIntent> list2 = slFundIntentService.getByProjectId(fl.getProjectId(),fl.getBusinessType());
		for(SlActualToCharge satc : list1){
			satc.setCompanyId(fl.getCompanyId());
			slActualToChargeDao.save(satc);
		}
		for(SlFundIntent sfi : list2){
			sfi.setCompanyId(fl.getCompanyId());
			slFundIntentDao.save(sfi);
		}
	}
	
	
	
	
	
	
	
	
	/**
	 *融资租赁项目   保存(更新)信息
	 */
	public Integer updateInfo(FlLeaseFinanceProject flLeaseFinanceProject,
			Person person, Enterprise enterprise,List<EnterpriseShareequity> listES,
			CustomerBankRelationPerson customerBankRelationPerson,List<SlFundIntent> slFundIntents,
			List<SlActualToCharge> slActualToCharges ,String isDeleteAllFundIntent) {
		try{
			FlLeaseFinanceProject persistent = this.dao.get(flLeaseFinanceProject.getProjectId());
		   	if(persistent.getOppositeType().equals("company_customer")){
		   		if(listES.size()>0){
		   			for(int i=0;i<listES.size();i++){
	   	   				 EnterpriseShareequity es = listES.get(i);
	   	   				 if(es.getId()==null){
	   	   					es.setEnterpriseid(enterprise.getId());
	   	   					this.enterpriseShareequityDao.save(es);
	   	   				 }else {
	   	   					 EnterpriseShareequity esPersistent = this.enterpriseShareequityDao.load(es.getId());
	   	   					 BeanUtils.copyProperties(es, esPersistent,new String[] {"id","enterpriseid"});
	   	   					 this.enterpriseShareequityDao.merge(esPersistent);
	   	   				 }
	   	   			 }
   	   		 	}
		   		
		    	Enterprise ePersistent = this.enterpriseDao.getById(enterprise.getId());
		    	ePersistent.setEnterprisename(enterprise.getEnterprisename());
		    	ePersistent.setArea(enterprise.getArea());
		    	ePersistent.setShortname(enterprise.getShortname());
		    	ePersistent.setHangyeType(enterprise.getHangyeType());
		    	ePersistent.setOrganizecode(enterprise.getOrganizecode());
		    	ePersistent.setCciaa(enterprise.getCciaa());
		    	ePersistent.setTelephone(enterprise.getTelephone());
		    	ePersistent.setPostcoding(enterprise.getPostcoding());
		    	this.enterpriseDao.merge(ePersistent);
		    	 
	    	   	Person pPersistent = this.personDao.getById(ePersistent.getLegalpersonid());
	    		pPersistent.setName(person.getName());
	    		pPersistent.setSex(person.getSex());
	    		pPersistent.setCardtype(person.getCardtype());
	    		pPersistent.setCardnumber(person.getCardnumber());
	    		pPersistent.setTelphone(person.getTelphone());
	    		pPersistent.setSelfemail(person.getSelfemail());
	    		this.personDao.merge(pPersistent);
	    		  
        	}else if(persistent.getOppositeType().equals("person_customer")){
        		Person pPersistent = this.personDao.getById(person.getId());
        		pPersistent.setMarry(person.getMarry());
        		pPersistent.setName(person.getName());
        		pPersistent.setSex(person.getSex());
        		pPersistent.setCardtype(person.getCardtype());
        		pPersistent.setCardnumber(person.getCardnumber());
        		pPersistent.setTelphone(person.getTelphone());
        		pPersistent.setPostcode(person.getPostcode());
        		pPersistent.setSelfemail(person.getSelfemail());
        		pPersistent.setPostaddress(person.getPostaddress());
        		this.personDao.merge(pPersistent);
        	}
		   	
			/*//贷款账户信息
			if(enterpriseBank!=null){
				if(enterpriseBank.getId()==null||enterpriseBank.getId()==0){
					enterpriseBankServ.addnew(enterpriseBank);
			   	}else{
			   		enterpriseBankServ.update(enterpriseBank);
			   	}
			}*/
		   	BeanUtil.copyNotNullProperties(persistent, flLeaseFinanceProject);
    		int bankid = 0;
/*    		if(customerBankRelationPerson.getId()==null||customerBankRelationPerson.getId()==0){ //第一次保存
    			if(null!=customerBankRelationPerson.getBankid()&&"".equals(customerBankRelationPerson.getBankid())){
    				bankRelationPersonService.addPersonNew(customerBankRelationPerson);
    				persistent.setBankId(customerBankRelationPerson.getId());
    			}
    		}else {
    			CustomerBankRelationPerson pasint = this.bankRelationPersonService.queryPerson(customerBankRelationPerson.getId());
    			pasint.setFenbankid(customerBankRelationPerson.getFenbankid());
    			pasint.setBlmphone(customerBankRelationPerson.getBlmphone());
    			pasint.setBlmtelephone(customerBankRelationPerson.getBlmtelephone());
    			pasint.setEmail(customerBankRelationPerson.getEmail());
    			pasint.setDuty(customerBankRelationPerson.getDuty());
    			pasint.setName(customerBankRelationPerson.getName());
    			bankRelationPersonService.updatePersonNew(pasint);
    			persistent.setBankId(customerBankRelationPerson.getId());
    			bankid = pasint.getBankid();
    		}*/
       		// gaomm 银行保证金
			//List<SlActualToCharge> allactual=this.slActualToChargeDao.listbyproject(persistent.getProjectId(),"Guarantee");
    		List<SlFundIntent> allintent=this.slFundIntentDao.getByProjectId1(persistent.getProjectId(),"LeaseFinance");
    		for(SlFundIntent s:allintent){
	    		slFundIntentDao.evict(s);
	    		if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
	    			this.slFundIntentDao.remove(s);
	    		}
	    	}
    		for(SlFundIntent a:slFundIntents){
    			BigDecimal lin = new BigDecimal(0.00);
				if (a.getIncomeMoney().compareTo(lin) == 0) {
					a.setNotMoney(a.getPayMoney());
				} else {
					a.setNotMoney(a.getIncomeMoney());
				}
				a.setAfterMoney(new BigDecimal(0));
				a.setAccrualMoney(new BigDecimal(0));
				a.setFlatMoney(new BigDecimal(0));
				Short isvalid = 0;
				a.setIsValid(isvalid);
				a.setIsCheck(Short.valueOf("1"));
				a.setBusinessType(persistent.getBusinessType());
				a.setCompanyId(persistent.getCompanyId());
				a.setProjectId(persistent.getProjectId());
				a.setProjectName(persistent.getProjectName());
				a.setProjectNumber(persistent.getProjectNumber());
				slFundIntentService.save(a);
    			this.slFundIntentDao.save(a);
    		}
    		for(SlActualToCharge a:slActualToCharges){
    			a.setCompanyId(persistent.getCompanyId());
    			this.slActualToChargeDao.save(a);
    		}
	    	/*List<SlFundIntent> allintent=this.slFundIntentDao.getByProjectId1(persistent.getProjectId(),"LeaseFinance");
	    	if(isDeleteAllFundIntent != null && isDeleteAllFundIntent.equals("1")) {
    	    	for(SlFundIntent s:allintent){
    	    		slFundIntentDao.evict(s);
    	    		if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
    	    			this.slFundIntentDao.remove(s);
    	    		}
    	    	}
	    	}
    		for(SlFundIntent a:slFundIntents){
    			this.slFundIntentDao.save(a);
    		}
    		for(SlActualToCharge a:slActualToCharges){
    			//没有对应的companyId则默认保存项目的companyIdadd by gaoqingrui 
    				a.setCompanyId(persistent.getCompanyId());
    			this.slActualToChargeDao.save(a);
    		}
    		Map<String,BigDecimal> map=slFundIntentService.saveGuaranteeProjectfiance(persistent.getProjectId(),"Guarantee");*/
//    		persistent.setPaychargeMoney(map.get("paychargeMoney"));
//    		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
    		this.dao.merge(persistent);
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	private boolean saveTaskSignData(ProcessForm processForm, String userId,
			String userFullName, String parentTaskId, String signVoteType) {
		try {
			TaskSignData taskSignData = new TaskSignData();
			taskSignData.setVoteId(new Long(userId));
			taskSignData.setVoteName(userFullName);
			taskSignData.setVoteTime(new Date());
			taskSignData.setTaskId(parentTaskId);
			taskSignData.setRunId(new Long(processForm.getRunId()));
			taskSignData.setIsAgree(Short.valueOf(signVoteType));
			taskSignData.setFromTaskId(processForm.getFromTaskId());
			taskSignData.setFormId(processForm.getFormId());
			taskSignDataDao.save(taskSignData);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("审保会集体决议提交保存会签信息出错：" + e.getMessage());
			return false;
		}
	}
	
	public List<SlActualToCharge> savejsoncharge(
			String slActualToChargeJsonData,
			FlLeaseFinanceProject flLeaseFinanceProject, Short flag) {
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

					SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper
							.toJava(parser.nextValue(), SlActualToCharge.class);

					slActualToCharge.setProjectId(flLeaseFinanceProject
							.getProjectId());
					slActualToCharge.setProjectName(flLeaseFinanceProject
							.getProjectName());
					slActualToCharge.setProjectNumber(flLeaseFinanceProject
							.getProjectNumber());

					slActualToCharge.setPlanChargeId(slPlansToCharge
							.getPlansTochargeId());
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
						slActualToCharge.setCompanyId(flLeaseFinanceProject.getCompanyId());
						slActualToCharge.setIsCheck(flag);
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
							slActualToCharge1.setCompanyId(flLeaseFinanceProject.getCompanyId());
							slActualToCharge1.setIsCheck(flag);
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
	public void stopProject(FlowRunInfo flowRunInfo){
		String projectId = flowRunInfo.getRequest().getParameter("flLeaseFinanceProject.projectId");
		FlLeaseFinanceProject persistent = this.dao.get(Long.valueOf(projectId));
		persistent.setProjectStatus(Constants.PROJECT_STATUS_STOP);
		this.dao.save(persistent);
	}
	public void completeProject(FlowRunInfo flowRunInfo){
		String projectId = flowRunInfo.getRequest().getParameter("flLeaseFinanceProject.projectId");
		FlLeaseFinanceProject persistent = this.dao.get(Long.valueOf(projectId));
		persistent.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
		this.dao.save(persistent);
	}
	public void middleProject(FlowRunInfo flowRunInfo){
		String projectId = flowRunInfo.getRequest().getParameter("flLeaseFinanceProject.projectId");
		FlLeaseFinanceProject persistent = this.dao.get(Long.valueOf(projectId));
		persistent.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
		this.dao.save(persistent);
	}
	
	/*保存其他费用信息*/
	public FlLeaseFinanceProject saveActualToChargeData(FlowRunInfo flowRunInfo){
		String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");//收费信息
		String projectId=flowRunInfo.getRequest().getParameter("flLeaseFinanceProject.projectId");//收费信息
		FlLeaseFinanceProject persistent = null;
		try{
			if(null!=projectId&&!"".equals(projectId)){
				persistent = this.dao.get(Long.valueOf(projectId));
			}
			List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
			slActualToCharges=savejsoncharge(slActualToChargeJsonData,persistent,Short.valueOf("0"));
			for(SlActualToCharge a:slActualToCharges){
				a.setCompanyId(persistent.getCompanyId());
				this.slActualToChargeDao.save(a);
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return persistent;
	}
	
	@Override
	public boolean startBreach(FlLeaseFinanceProject project, String listed,
			String comments) {

		try {
			project.setProjectStatus(Integer.valueOf("8").shortValue());
			project.setBreachComment(comments);
			this.dao.merge(project);
			if (listed.equals("1")) {
				String ot = project.getOppositeType();
				Long oppositeID = project.getOppositeID();
				String adminName = ContextUtil.getCurrentUser().getFullname();
				String blackReason = "【" + project.getProjectName()
						+ "】更改为违约处理贷款-【" + adminName + "】将此客户加入黑名单";
				if (ot.equals("company_customer")) {
					Enterprise e = this.enterpriseDao.getById(oppositeID
							.intValue());
					e.setIsBlack(true);
					e.setBlackReason(blackReason);
					this.enterpriseDao.merge(e);
				} else if (ot.equals("person_customer")) {
					Person p = this.personDao.getById(oppositeID.intValue());
					p.setIsBlack(true);
					p.setBlackReason(blackReason);
					this.personDao.merge(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
//----------------------------------------------流程节点方法-----------------------------------------------	
	
	/**
	 * 融资租赁，尽职调查提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer diligenceNextStep(FlowRunInfo flowRunInfo) {
		String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");//股东信息
		String fundIntentJson=flowRunInfo.getRequest().getParameter("fundIntentJsonData1");//款项信息
		String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");//收费信息
		try{
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
	    	
			//更新主体单位名称 add by gaoqingrui
			String mineIdStr = flowRunInfo.getRequest().getParameter("flLeaseFinanceProject.mineId");
			Long mineId = null;
	    	FlLeaseFinanceProject project = new FlLeaseFinanceProject();
	    	
	    	BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "flLeaseFinanceProject");
	    	
	    	FlLeaseFinanceProject persistent = this.dao.get(project.getProjectId());
	    	//更新主体单位名称 add by gaoqingrui
			if(null!= mineIdStr&&!"".equals(mineIdStr)){
				mineId = Long.valueOf(mineIdStr);
				project.setMineId(mineId);
				if("true".equals(AppUtil.getSystemIsGroupVersion())){//集团版本   我方主体id 同时对应 companyId
					project.setCompanyId(mineId);
					updateCompanyId(mineId,project);//这句不需要
				}
			}
	    	BeanUtil.copyNotNullProperties(persistent,project);//替换下一句
	    	
	    	int bankid = 0;
			//费用收支
//			String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
//			slActualToChargeService.savejson(slActualToChargeJson, persistent.getProjectId(),"Guarantee",Short.parseShort("0"),null);
//			slActualToChargeService.savejson(slActualToChargeJson, persistent.getProjectId(),"Guarantee",Short.parseShort("0"),persistent.getCompanyId());
//			Map<String,BigDecimal> map = slFundIntentService.saveGuaranteeProjectfiance(persistent.getProjectId(),"Guarantee");
//    		persistent.setPaychargeMoney(map.get("paychargeMoney"));
//    		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
	    	this.dao.merge(persistent);
	    	
	       	if(persistent.getOppositeType().equals("company_customer")){ //企业
		   		Enterprise enterprise = new Enterprise();
		   		Person person = new Person();
		   	    BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
		   	    Enterprise epersistent = this.enterpriseDao.getById(enterprise.getId());
		   	    BeanUtils.copyProperties(enterprise, epersistent,new String[] {"id","legalpersonid"});
		   	    this.enterpriseDao.merge(epersistent);
		   	    BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
		   	    Person ppersistent = this.personDao.getById(epersistent.getLegalpersonid());
		   	     
		   	    ppersistent.setMarry(person.getMarry());
		   	    ppersistent.setName(person.getName());
		   	    ppersistent.setSex(person.getSex());
		   	    ppersistent.setCardtype(person.getCardtype());
		   	    ppersistent.setCardnumber(person.getCardnumber());
		   	    ppersistent.setTelphone(person.getTelphone());
		   	    ppersistent.setPostcode(person.getPostcode());
		   	    ppersistent.setSelfemail(person.getSelfemail());
		   	    ppersistent.setPostaddress(person.getPostaddress());
	     		personDao.merge(ppersistent);
	     		//更新person信息结束
		   	     
	     		BeanUtils.copyProperties(person, ppersistent,new String[] {"id"});
	     		this.personDao.merge(ppersistent);
		   	     
    	   		if(listES.size()>0){
    	   			for(int i=0;i<listES.size();i++){
    	   				EnterpriseShareequity es=listES.get(i);
    	   				if(es.getId()==null){
    	   					es.setEnterpriseid(epersistent.getId());
    	   					this.enterpriseShareequityDao.save(es);
    	   				 }else {
    	   					EnterpriseShareequity esPersistent=this.enterpriseShareequityDao.load(es.getId());
    	   					BeanUtils.copyProperties(es, esPersistent,new String[] {"id","enterpriseid"});
    	   					this.enterpriseShareequityDao.merge(esPersistent);
    	   				 }
    	   			 }
    	   		 }
        	}else if(persistent.getOppositeType().equals("person_customer")){
		        Person person = new Person();
		   	    BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
		   	     
		       	Person persistentPerson = this.personDao.getById(person.getId());
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
	       	List<SlFundIntent> list=slFundIntentService.getByProjectId1(persistent.getProjectId(), persistent.getBusinessType());
	       	for(SlFundIntent s:list){
	       		if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
	       			slFundIntentService.remove(s);
	       		}
	       	}
	    	//保存款项信息 start   	
	    	List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
			if (null != fundIntentJson && !"".equals(fundIntentJson)) {
				String[] slFundIentJsonArr = fundIntentJson.split("@");
				int j=1;
				for (int i = 0; i < slFundIentJsonArr.length; i++) {
					String str = slFundIentJsonArr[i];
					
					JSONParser parser = new JSONParser(new StringReader(str));
					
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
					slFundIntents.add(SlFundIntent1);
				}
			}
			for(SlFundIntent a:slFundIntents){
    			BigDecimal lin = new BigDecimal(0.00);
				if (a.getIncomeMoney().compareTo(lin) == 0) {
					a.setNotMoney(a.getPayMoney());
				} else {
					a.setNotMoney(a.getIncomeMoney());
				}
				a.setAfterMoney(new BigDecimal(0));
				a.setAccrualMoney(new BigDecimal(0));
				a.setFlatMoney(new BigDecimal(0));
				Short isvalid = 0;
				a.setIsValid(isvalid);
				a.setIsCheck(Short.valueOf("1"));
				a.setBusinessType(persistent.getBusinessType());
				a.setCompanyId(persistent.getCompanyId());
				a.setProjectId(persistent.getProjectId());
				a.setProjectName(persistent.getProjectName());
				a.setProjectNumber(persistent.getProjectNumber());
				slFundIntentService.save(a);
    			this.slFundIntentDao.save(a);
    		}
			//保存款项信息 end
			//实际收费信息
			List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
			slActualToCharges=savejsoncharge(slActualToChargeJsonData,project,Short.valueOf("0"));
			for(SlActualToCharge a:slActualToCharges){
    			a.setCompanyId(persistent.getCompanyId());
    			this.slActualToChargeDao.save(a);
    		}
	    	return 1;
		}catch (Exception e) {
		   e.printStackTrace();
		   logger.error("融资租赁-尽职调查提交下一步出错:"+e.getMessage());
		   return 0;
		}
	}
	 /* 主管初审*/
	public Integer managerFirstCheckNextStep(FlowRunInfo flowRunInfo){
		try{       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String transitionName = flowRunInfo.getTransitionName();
		      	if(transitionName!=null&&!"".equals(transitionName)){
		      		Map<String,Object> vars=new HashMap<String, Object>();
		      		if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				    	if(currentForm!=null){
				    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
				    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
				    			flowRunInfo.setAfresh(true);//表示打回重做
				    			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "dueDiligence");
			    				if(processForm!=null&&processForm.getCreatorId()!=null){
			    					String creatorId = processForm.getCreatorId().toString();
			    					vars.put("flowAssignId",creatorId);
			    				}
				    		}else{
				    			saveActualToChargeData(flowRunInfo);
				    		}
				    	}
		    		}
			    	vars.put("managerFirstCheckResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
		    	}
		    	return 1;
		    }
		 }
	catch (Exception e) {
		e.printStackTrace();
		logger.error("主管初审提交任务  信息出错:"+e.getMessage());
		return 0;
	}
	}
	
	/*风险管理部*/
	public Integer riskManagerAllocateNextStep(FlowRunInfo flowRunInfo){
		try{       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String transitionName = flowRunInfo.getTransitionName();
		    	String riskAttacheId = flowRunInfo.getRequest().getParameter("riskAttacheId");
		      	if(transitionName!=null&&!"".equals(transitionName)){
		      		Map<String,Object> vars=new HashMap<String, Object>();
		      		if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		      			stopProject(flowRunInfo);
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				    	if(currentForm!=null){
				    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
				    		if(!isToNextTask){}else{
				    			if (riskAttacheId != null && !"".equals(riskAttacheId)) {
									String assignUserId = riskAttacheId;
									vars.put("flowAssignId", assignUserId);
								}
				    		}
				    	}
		    		}
			    	vars.put("riskManagerAllocateResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
		    	}
		    	return 1;
		    }
		 }
	catch (Exception e) {
		e.printStackTrace();
		logger.error("主管初审提交任务  信息出错:"+e.getMessage());
		return 0;
	}
	}
	
	/*风险专员审查*/
	public Integer riskAttacheCheckNextStep(FlowRunInfo flowRunInfo){
		try{
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String transitionName = flowRunInfo.getTransitionName();
		      	if(transitionName!=null&&!"".equals(transitionName)){
		      		Map<String,Object> vars=new HashMap<String, Object>();
		      		if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				    	if(currentForm!=null){
				    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
				    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
				    			flowRunInfo.setAfresh(true);//表示打回重做
				    			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "managerFirstCheck");
			    				if(processForm!=null&&processForm.getCreatorId()!=null){
			    					String creatorId = processForm.getCreatorId().toString();
			    					vars.put("flowAssignId",creatorId);
			    				}
				    		}else{
				    			FlLeaseFinanceProject project = new FlLeaseFinanceProject();
				    			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"flLeaseFinanceProject");
				    			FlLeaseFinanceProject persistent =  saveActualToChargeData(flowRunInfo);
				    			BeanUtil.copyNotNullProperties(persistent, project);
				    			this.dao.save(persistent);
				    		}
				    	}
		    		}
			    	vars.put("riskAttacheCheckResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
		    	}
		    	return 1;
		    }
		 }
	catch (Exception e) {
		e.printStackTrace();
		logger.error("风险专员审查提交任务  信息出错:"+e.getMessage());
		return 0;
	}
	}

	/*风险主管审核*/
	public Integer riskManagerCheckNextStep(FlowRunInfo flowRunInfo){	
		try{       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String transitionName = flowRunInfo.getTransitionName();
		    	String onlineJudgementIds = flowRunInfo.getRequest().getParameter("onlineJudgementIds");
		      	if(transitionName!=null&&!"".equals(transitionName)){
		      		Map<String,Object> vars=new HashMap<String, Object>();
		      		if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		      			stopProject(flowRunInfo);
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				    	if(currentForm!=null){//多个打回
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							}else{
								if("线上评审会决议".equals(transitionName)){
									if (onlineJudgementIds != null && !"".equals(onlineJudgementIds)) {
										String assignUserId = onlineJudgementIds;
										vars.put("flowAssignId", assignUserId);
									}
				    			}
								saveActualToChargeData(flowRunInfo);
				    		}
						}
		    		}
			    	vars.put("riskManagerCheckResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
		    	}
		    	return 1;
		    }
		 }catch (Exception e) {
			e.printStackTrace();
			logger.error("风险主管审核任务  信息出错:"+e.getMessage());
			return 0;
		}
	}
	/*线上评审会决议*/
	public Integer onlineJudgementNextStep(FlowRunInfo flowRunInfo){
		try{
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String signVoteType = flowRunInfo.getRequest().getParameter("signVoteType");// 投票结果：同意(1)、否决(2)、打回(3)、有条件通过(4)。
				String userId = ContextUtil.getCurrentUserId().toString();
				String userFullName = ContextUtil.getCurrentUser().getFullname();
				String taskId = flowRunInfo.getTaskId();
				Map<String, String> vars = new HashMap<String, String>();
				
				if (taskId != null && !"".equals(taskId)) {
					Task parentTask = jbpmService.getParentTask(taskId.toString());
					ProcessForm processForm = processFormDao.getByTaskId(taskId);
					if (processForm != null) {
					}
					saveActualToChargeData(flowRunInfo);
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("金融租赁-线上评审会决议确认提交下一步出错："+e.getMessage());
			return 0;
		}
	}
	
	/*汇总评审会意见及纪要*/
	public Integer collectDiscussCommentNextStep(FlowRunInfo flowRunInfo){	
		try{     
			String projectId = flowRunInfo.getRequest().getParameter("flLeaseFinanceProject.projectId");
			FlLeaseFinanceProject persistent = this.dao.get(Long.valueOf(projectId));
			String fundIntentJson=flowRunInfo.getRequest().getParameter("fundIntentJsonData1");//款项信息
			String MId= flowRunInfo.getRequest().getParameter("flLeaseFinanceProject.mineId");
			Long mineId = null;
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars=new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
						flowRunInfo.setStop(true);
						stopProject(flowRunInfo);
					}else{
						
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							}else{
								//会议纪要保存操作，在流程向下进行时 保存 
								SlConferenceRecord slConferenceRecord = new SlConferenceRecord();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slConferenceRecord,"slConferenceRecord");
								if(slConferenceRecord.getConforenceId()==null){
									slConferenceRecordDao.save(slConferenceRecord);
									Long conforenceId=slConferenceRecord.getConforenceId();
								}else{
									SlConferenceRecord orgSlConferenceRecord=slConferenceRecordDao.get(slConferenceRecord.getConforenceId());
									BeanUtil.copyNotNullProperties(orgSlConferenceRecord, slConferenceRecord);
									slConferenceRecordDao.save(orgSlConferenceRecord);
								}
								FlLeaseFinanceProject project = new FlLeaseFinanceProject();
				    			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"flLeaseFinanceProject");
				    			BeanUtil.copyNotNullProperties(persistent, project);
								
								//流程打回 才更新项目信息 add by gao 8月24日
								if(null == MId || "".equals(MId)){//如果不为空则说明对应的companyId更新
									this.dao.save(persistent);
								}else{
									mineId = Long.valueOf(MId);
									persistent.setMineId(mineId);
									if("true".equals(AppUtil.getSystemIsGroupVersion())){//集团版本   我方主体id 同时对应 companyId
										persistent.setCompanyId(mineId);
									}
									this.dao.save(persistent);
								}
								List<SlFundIntent> list=slFundIntentService.getByProjectId1(persistent.getProjectId(), persistent.getBusinessType());
				    	       	for(SlFundIntent s:list){
				    	       		if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
				    	       			slFundIntentService.remove(s);
				    	       		}
				    	       	}
								//款项信息start
								List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
								if (null != fundIntentJson && !"".equals(fundIntentJson)) {
									String[] slFundIentJsonArr = fundIntentJson.split("@");
									int j=1;
									for (int i = 0; i < slFundIentJsonArr.length; i++) {
										String str = slFundIentJsonArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										
										SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
										slFundIntents.add(SlFundIntent1);
									}
								}
								for(SlFundIntent a:slFundIntents){
					    			BigDecimal lin = new BigDecimal(0.00);
									if (a.getIncomeMoney().compareTo(lin) == 0) {
										a.setNotMoney(a.getPayMoney());
									} else {
										a.setNotMoney(a.getIncomeMoney());
									}
									a.setAfterMoney(new BigDecimal(0));
									a.setAccrualMoney(new BigDecimal(0));
									a.setFlatMoney(new BigDecimal(0));
									Short isvalid = 0;
									a.setIsValid(isvalid);
									a.setIsCheck(Short.valueOf("1"));
									a.setBusinessType(persistent.getBusinessType());
									a.setCompanyId(persistent.getCompanyId());
									a.setProjectId(persistent.getProjectId());
									a.setProjectName(persistent.getProjectName());
									a.setProjectNumber(persistent.getProjectNumber());
									slFundIntentService.save(a);
					    			this.slFundIntentDao.save(a);
					    		}
								//保存款项信息 end
								//实际收费信息
								saveActualToChargeData(flowRunInfo);
							}
						}
					}
					vars.put("collectDiscussCommentResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("汇总评审会意见及纪要任务  信息出错:"+e.getMessage());
			return 0;
		}
	}
	
	
	/*汇总评审会纪要*/
	public Integer collectJudgemenNextStep(FlowRunInfo flowRunInfo){	
		try{   
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				String fundIntentJson=flowRunInfo.getRequest().getParameter("fundIntentJsonData1");//款项信息
				String projectId = flowRunInfo.getRequest().getParameter("flLeaseFinanceProject.projectId");
				FlLeaseFinanceProject persistent = this.dao.get(Long.valueOf(projectId));
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars=new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
						flowRunInfo.setStop(true);
						stopProject(flowRunInfo);
					}else{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							}else{
								FlLeaseFinanceProject project = new FlLeaseFinanceProject();
				    			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"flLeaseFinanceProject");
				    			BeanUtil.copyNotNullProperties(persistent, project);
				    			this.dao.save(persistent);
				    			List<SlFundIntent> list=slFundIntentService.getByProjectId1(persistent.getProjectId(), persistent.getBusinessType());
				    	       	for(SlFundIntent s:list){
				    	       		if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
				    	       			slFundIntentService.remove(s);
				    	       		}
				    	       	}
								//款项信息start
								List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
								if (null != fundIntentJson && !"".equals(fundIntentJson)) {
									String[] slFundIentJsonArr = fundIntentJson.split("@");
									int j=1;
									for (int i = 0; i < slFundIentJsonArr.length; i++) {
										String str = slFundIentJsonArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										
										SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
										slFundIntents.add(SlFundIntent1);
									}
								}
								for(SlFundIntent a:slFundIntents){
					    			BigDecimal lin = new BigDecimal(0.00);
									if (a.getIncomeMoney().compareTo(lin) == 0) {
										a.setNotMoney(a.getPayMoney());
									} else {
										a.setNotMoney(a.getIncomeMoney());
									}
									a.setAfterMoney(new BigDecimal(0));
									a.setAccrualMoney(new BigDecimal(0));
									a.setFlatMoney(new BigDecimal(0));
									Short isvalid = 0;
									a.setIsValid(isvalid);
									a.setIsCheck(Short.valueOf("1"));
									a.setBusinessType(persistent.getBusinessType());
									a.setCompanyId(persistent.getCompanyId());
									a.setProjectId(persistent.getProjectId());
									a.setProjectName(persistent.getProjectName());
									a.setProjectNumber(persistent.getProjectNumber());
									slFundIntentService.save(a);
					    			this.slFundIntentDao.save(a);
					    		}
								//保存款项信息 end
								//实际收费信息
								saveActualToChargeData(flowRunInfo);
							}
						}
					}
					vars.put("collectJudgemenResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("汇总审评会意见及纪要任务  信息出错:"+e.getMessage());
			return 0;
		}
	}
	
	/*总经理审批*/
	public Integer generalManagerExamineNextStep(FlowRunInfo flowRunInfo){	
		try{   
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars=new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
						flowRunInfo.setStop(true);
						stopProject(flowRunInfo);
					}else{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
							if(!isToNextTask){
							}else{
								saveActualToChargeData(flowRunInfo);
							}//无打回节点
						}
					}
					vars.put("generalManagerExamineResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("总经理审批任务提交  信息出错:"+e.getMessage());
			return 0;
		}
	}
	/*签署合同*/
	public Integer signContractNextStep(FlowRunInfo flowRunInfo){
		try{
			saveActualToChargeData(flowRunInfo);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("法务审核任务提交  信息出错:"+e.getMessage());
			return 0;
		}
	}
	
	
	/*法务审核*/
	public Integer lawCheckNextStep(FlowRunInfo flowRunInfo){	
		try{       
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars=new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
						flowRunInfo.setStop(true);
					}else{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
							if(!isToNextTask){
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							}else{
								saveActualToChargeData(flowRunInfo);	
							}
						}
					}
					vars.put("lawCheckResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("法务审核任务提交  信息出错:"+e.getMessage());
			return 0;
		}
	}
	/*总经理放款审批*/
	public Integer generalManagerMoneyCheckNextStep(FlowRunInfo flowRunInfo){
		try{    
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars=new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
						flowRunInfo.setStop(true);
						stopProject(flowRunInfo);
					}else{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
							if(!isToNextTask){
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							}else{
								saveActualToChargeData(flowRunInfo);
							}
						}
					}
					vars.put("generalManagerMoneyCheckResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("总经理放款审批任务提交  信息出错:"+e.getMessage());
			return 0;
		}
	}
	/*财务出账确认*/
	public Integer checkMoneyNextStep(FlowRunInfo flowRunInfo){
		String fundIntentJson=flowRunInfo.getRequest().getParameter("fundIntentJsonData1");//款项信息
		String projectId = flowRunInfo.getRequest().getParameter("flLeaseFinanceProject.projectId");
		String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");//收费信息
		try{
			FlLeaseFinanceProject persistent = this.dao.get(Long.valueOf(projectId));
	    	//保存款项信息 start   	
	    	List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
			if (null != fundIntentJson && !"".equals(fundIntentJson)) {
				String[] slFundIentJsonArr = fundIntentJson.split("@");
				int j=1;
				for (int i = 0; i < slFundIentJsonArr.length; i++) {
					String str = slFundIentJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
					slFundIntents.add(SlFundIntent1);
				}
			}
			List<SlFundIntent> slist=slFundIntentDao.getByProjectId(persistent.getProjectId(), persistent.getBusinessType());
			for(SlFundIntent s:slist){
				if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
					slFundIntentDao.remove(s);
				}
			}
			for(SlFundIntent a:slFundIntents){
    			BigDecimal lin = new BigDecimal(0.00);
				if (a.getIncomeMoney().compareTo(lin) == 0) {
					a.setNotMoney(a.getPayMoney());
				} else {
					a.setNotMoney(a.getIncomeMoney());
				}
				a.setAfterMoney(new BigDecimal(0));
				a.setAccrualMoney(new BigDecimal(0));
				a.setFlatMoney(new BigDecimal(0));
				Short isvalid = 0;
				a.setIsValid(isvalid);
				a.setIsCheck(Short.valueOf("0"));
				a.setBusinessType(persistent.getBusinessType());
				a.setCompanyId(persistent.getCompanyId());
				a.setProjectId(persistent.getProjectId());
				a.setProjectName(persistent.getProjectName());
				a.setProjectNumber(persistent.getProjectNumber());
				slFundIntentService.save(a);
    			this.slFundIntentDao.save(a);
    		}
			//保存款项信息 end
			//改变项目状态   进入还款中项目
			persistent.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
			this.dao.merge(persistent);
			String hql = "from com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseobjectInfo as e where e.projectId = ?";
			Object [] params = {persistent.getProjectId()};
			List<FlLeaseobjectInfo> list= creditBaseDao.queryHql(hql, params);
			for(int i = 0;i< list.size();i++){
				FlLeaseobjectInfo entity = list.get(i);
				entity.setIsManaged(true);
				creditBaseDao.saveDatas(entity);
			}
			//实际收费信息
			List<SlActualToCharge> slActualToCharges = new ArrayList<SlActualToCharge>();
			slActualToCharges=savejsoncharge(slActualToChargeJsonData,persistent,Short.valueOf("0"));
			for(SlActualToCharge a:slActualToCharges){
    			a.setCompanyId(persistent.getCompanyId());
    			this.slActualToChargeDao.save(a);
    		}
	    	return 1;
		}catch (Exception e) {
		   e.printStackTrace();
		   logger.error("财务出账确认提交下一步出错:"+e.getMessage());
		   return 0;
		}
	}
	/*归档审核确认*/
	public Integer affirmFilesNextStep(FlowRunInfo flowRunInfo){
		try{    
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String projectId = flowRunInfo.getRequest().getParameter("flLeaseFinanceProject.projectId");
				FlLeaseFinanceProject persistent = this.dao.get(Long.valueOf(projectId));
				String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars=new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
						flowRunInfo.setStop(true);
						completeProject(flowRunInfo);
					}else{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
							if(!isToNextTask){
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							}else{
								persistent.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
								this.dao.save(persistent);
							}
						}
					}
					vars.put("affirmFilesResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("归档审核确认任务提交  信息出错:"+e.getMessage());
			return 0;
		}
	}
	//---------------------------租后监管-----------------------------------------------------
	@Override
	public Integer changeStatusCreditFlowProject(FlowRunInfo flowRunInfo) {
		try {
			String businessType = flowRunInfo.getRequest().getParameter("businessType_flow");
			Long id = Long.valueOf(flowRunInfo.getRequest().getParameter(
					"superviseManageId").toString());
			GlobalSupervisemanage ss = globalSupervisemanageDao.get(id);
			ss.setSuperviseManageStatus(Short.valueOf("1"));
			BeanUtil.populateEntity(flowRunInfo.getRequest(), ss,
					"globalSupervisemanage");
			ss.setBusinessType("LeaseFinance");//手动填  
			globalSupervisemanageDao.save(ss);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("融资租赁监管流程 - 租后监管  出错:" + ex.getMessage());
			return 0;
		}
	}
	/*启动展期流程*/
	@Override
	public String startRenewalProcess(Long projectId) {

		String customerName = "";

		FlLeaseFinanceProject project = this.dao.get(projectId);
		project.setIsOtherFlow(Short.valueOf("1"));
		AppUser user = ContextUtil.getCurrentUser();
		SlSuperviseRecord slSuperviseRecord = new SlSuperviseRecord();
		slSuperviseRecord.setProjectId(projectId);
		slSuperviseRecord.setBusinessType("FlExhibitionBusiness");
		slSuperviseRecord.setBaseBusinessType(project.getBusinessType());//◎
		slSuperviseRecord.setOpTime(new Date());
		slSuperviseRecord.setCreator(user.getUserId().toString());
		project.setProjectStatus(Short.valueOf("4"));
		slSuperviseRecord.setCheckStatus(0);
		this.slSuperviseRecordDao.save(slSuperviseRecord);
		this.dao.merge(project);
		FlowRunInfo newFlowRunInfo = new FlowRunInfo();
		ProDefinition pdf = null;
		// Map<String, String> mapNew =null;
		// 不能从session中获取companyId,总公司的人员为分公司启动展期流程的时候,这样获取启动的却是总公司的展期流程.
		// 而是从项目表中获取对应的companyId
		// Long companyId=ContextUtil.getLoginCompanyId();
		Long companyId = project.getCompanyId();
		Organization org = organizationService.get(companyId);
		String isGroupVersion = AppUtil.getSystemIsGroupVersion();
		if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
			if (null != project.getOperationType()
					&& "DirectLeaseFinance".equals(project.getOperationType())) {
				pdf = proDefinitionDao
						.getByProcessName("leaseFinancePostponedFlow_"+org.getKey());
			}
		} else {
			pdf = proDefinitionDao.getByProcessName("leaseFinancePostponedFlow");
		}
		List<ProcessRun> processRunList = processRunDao
				.getByProcessNameProjectId(projectId,
						project.getBusinessType(), project.getFlowType());
		if (processRunList != null && processRunList.size() != 0) {
			customerName = processRunList.get(0).getCustomerName();
		}
		List<SlFundIntent> slist = slFundIntentService.getByProjectId3(project
			.getProjectId(), "LeaseFinance", "leasePrincipalRepayment");
		Date intentDate = null;
		if (null != slist && slist.size() > 0) {
			SlFundIntent s = slist.get(0);
			intentDate = DateUtil.addDaysToDate(s.getIntentDate(), 1);
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Long branchCompanyId = pdf.getBranchCompanyId();
		Map<String, Object> newVars = new HashMap<String, Object>();
		newVars.put("leaseFinanceProjectId", project.getProjectId());
		newVars.put("projectId", slSuperviseRecord.getId());
		newVars.put("oppositeType", project.getOppositeType());
		newVars.put("businessType", "FlExhibitionBusiness");
		newVars.put("baseBusinessType", project.getBusinessType());
		newVars.put("operationType", project.getOperationType());
		newVars.put("customerName", customerName); //
		newVars.put("projectNumber", project.getProjectNumber()); //
		newVars.put("projectMoney", project.getProjectMoney());
//		newVars.put("payProjectMoney", project.getPayProjectMoney());
		newVars.put("branchCompanyId", branchCompanyId);
		if (intentDate != null) {
			newVars.put("intentDate", sd.format(intentDate));
		}
		newFlowRunInfo.getVariables().putAll(newVars);
		newFlowRunInfo.setBusMap(newVars);
		newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
		newFlowRunInfo.setFlowSubject(project.getProjectName() + "-"
				+ project.getProjectNumber());
		ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
		String str = "";
		if (run != null && run.getPiId() != null) {
			// String piId = run.getPiId();
			str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),
					user.getUserId().toString(), project.getProjectName());
			/*
			 * String userId =user.getUserId()+""; PagingBean pb =new
			 * PagingBean(0,5); String flowKey = "smallLoanPostponedFlow";
			 * Boolean flag =
			 * Boolean.valueOf(AppUtil.getSystemIsGroupVersion()); if(flag){
			 * flowKey = "smallLoanPostponedFlow_"+org.getKey(); } Boolean strs
			 * =judge(userId,flowKey,pb,project.getProjectName(),project.
			 * getProjectNumber(),piId); if(strs){ List<TaskImpl>
			 * list=flowTaskService.getTaskByExecutionId(piId); if(null!=list &&
			 * list.size()>0){ Task task = (Task)list.get(0); if(task!=null){
			 * String taskId = task.getId(); String activityName =
			 * task.getActivityName();
			 * str="taskId:'"+taskId+"',activityName:'"+activityName+"'"; } }
			 * }else{ str="taskId:'1',activityName:''"; }
			 */
		}
		return str;
	}
	
	/*提前还款*/
	@Override
	public String startSlEarlyRepaymentProcess(Long projectId) {
		String customerName = "";
		FlLeaseFinanceProject project = this.dao.get(projectId);
		project.setIsOtherFlow(Short.valueOf("2"));
		AppUser user = ContextUtil.getCurrentUser();
		SlEarlyRepaymentRecord slEarlyRepaymentRecord = new SlEarlyRepaymentRecord();
		slEarlyRepaymentRecord.setProjectId(projectId);
		slEarlyRepaymentRecord.setCheckStatus(0);
		slEarlyRepaymentRecord.setOpTime(new Date());
		slEarlyRepaymentRecord.setCreator(user.getFullname());
		slEarlyRepaymentRecord.setBusinessType(project.getBusinessType());
		this.slEarlyRepaymentRecordService.save(slEarlyRepaymentRecord);
		this.dao.merge(project);
		FlowRunInfo newFlowRunInfo = new FlowRunInfo();
		ProDefinition pdf = null;// 流程定义key值
		// Map<String, String> mapNew =null;
		// 不能从session中获取companyId,总公司的人员为分公司启动展期流程的时候,这样获取启动的却是总公司的展期流程.
		// 而是从项目表中获取对应的companyId
		// Long companyId=ContextUtil.getLoginCompanyId();
		Long companyId = project.getCompanyId();
		Organization org = organizationService.get(companyId);
		String isGroupVersion = AppUtil.getSystemIsGroupVersion();
		if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
			pdf = proDefinitionDao.getByProcessName("flRepaymentAheadOfTimeFlow_"
					+ org.getKey());
		} else {
			pdf = proDefinitionDao.getByProcessName("flRepaymentAheadOfTimeFlow");
		}

		List<ProcessRun> processRunList = processRunDao
				.getByProcessNameProjectId(projectId,
						project.getBusinessType(), project.getFlowType());
		if (processRunList != null && processRunList.size() != 0) {
			customerName = processRunList.get(0).getCustomerName();
		}
		// mapNew =
		// this.getProjectInfo(project,"repaymentAheadOfTimeFlow_"+org.getKey());
		Long branchCompanyId = pdf.getBranchCompanyId();//
		Map<String, Object> newVars = new HashMap<String, Object>();
		newVars.put("projectId", project.getProjectId());
		newVars.put("slEarlyRepaymentId", slEarlyRepaymentRecord
				.getSlEarlyRepaymentId());
		newVars.put("oppositeType", project.getOppositeType());
		newVars.put("businessType", project.getBusinessType());
		newVars.put("customerName", customerName); //
		newVars.put("projectNumber", project.getProjectNumber()); //
		List<VFundDetail> vlist = vFundDetailService.listByFundType(
				"leasePrincipalRepayment", project.getProjectId(), "LeaseFinance");
		BigDecimal money = new BigDecimal(0);
		for (VFundDetail vFundDetail : vlist) {
			money = money.add(new BigDecimal(vFundDetail.getAfterMoney()));
		}
		newVars.put("surplusnotMoney", project.getProjectMoney().subtract(money));
		newFlowRunInfo.getVariables().putAll(newVars);
		newFlowRunInfo.setBusMap(newVars);
		newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
		newFlowRunInfo.setFlowSubject(project.getProjectName() + "-"
				+ project.getProjectNumber());
		ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
		String str = "";
		if (run != null && run.getPiId() != null) {
			str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),
					user.getUserId().toString(), project.getProjectName());
			/*
			 * String piId = run.getPiId(); String userId =user.getUserId()+"";
			 * PagingBean pb =new PagingBean(0,5); Boolean strs
			 * =judge(userId,"repaymentAheadOfTimeFlow_"
			 * +org.getKey(),pb,project.
			 * getProjectName(),project.getProjectNumber(),piId); if(strs){
			 * List<TaskImpl> list=flowTaskService.getTaskByExecutionId(piId);
			 * if(null!=list && list.size()>0){ Task task = (Task)list.get(0);
			 * if(task!=null){ String taskId = task.getId(); String activityName
			 * = task.getActivityName();
			 * str="taskId:'"+taskId+"',activityName:'"+activityName+"'"; } }
			 * }else{ str="taskId:'1',activityName:''"; }
			 */
		}
		return str;
	}
	
	
	/*利率变更*/
	@Override
	public String startSlAlteraccrualProcess(Long projectId) {
		String customerName = "";
		FlLeaseFinanceProject project = this.dao.get(projectId);
		project.setIsOtherFlow(Short.valueOf("3"));
		AppUser user = ContextUtil.getCurrentUser();
		SlAlterAccrualRecord slAlterAccrualRecord = new SlAlterAccrualRecord();
		slAlterAccrualRecord.setProjectId(projectId);
		slAlterAccrualRecord.setBaseBusinessType("LeaseFinance");//〓
		slAlterAccrualRecord.setCheckStatus(0);
		slAlterAccrualRecord.setOpTime(new Date());
		slAlterAccrualRecord.setCreator(user.getFullname());// 添加创建利率变更的人id
		slAlterAccrualRecord.setOriaccrual(project.getAccrualnew());//〓▲
		
		this.slAlterAccrualRecordDao.save(slAlterAccrualRecord);
		this.dao.merge(project);
		FlowRunInfo newFlowRunInfo = new FlowRunInfo();
		ProDefinition pdf = null;// 流程定义key值
		// 不能从session中获取companyId,总公司的人员为分公司启动展期流程的时候,这样获取启动的却是总公司的展期流程.
		// 而是从项目表中获取对应的companyId
		// Long companyId=ContextUtil.getLoginCompanyId();
		Long companyId = project.getCompanyId();//〓
		Organization org = organizationService.get(companyId);
		String isGroupVersion = AppUtil.getSystemIsGroupVersion();
		if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
			pdf = proDefinitionDao.getByProcessName("FlAlterAccrualFlow_"
					+ org.getKey());
		} else {
			pdf = proDefinitionDao.getByProcessName("FlAlterAccrualFlow");
		}

		List<ProcessRun> processRunList = processRunDao
				.getByProcessNameProjectId(projectId,
						project.getBusinessType(), project.getFlowType());
		if (processRunList != null && processRunList.size() != 0) {
			customerName = processRunList.get(0).getCustomerName();
		}
		Long branchCompanyId = pdf.getBranchCompanyId();

		Map<String, Object> newVars = new HashMap<String, Object>();
		newVars.put("projectId", project.getProjectId());
		newVars.put("slAlteraccrualRecordId", slAlterAccrualRecord
				.getSlAlteraccrualRecordId());
		newVars.put("oppositeType", project.getOppositeType());
		newVars.put("businessType", project.getBusinessType());
		newVars.put("customerName", customerName); //
		newVars.put("projectNumber", project.getProjectNumber()); //
		newVars.put("projectMoney", project.getProjectMoney());
//		newVars.put("payProjectMoney", project.getPayProjectMoney());//〓▲
		newVars.put("branchCompanyId", branchCompanyId);
		List<VFundDetail> vlist = vFundDetailService.listByFundType(
				"leasePrincipalRepayment", project.getProjectId(), "LeaseFinance");
		BigDecimal money = new BigDecimal(0);
		for (VFundDetail vFundDetail : vlist) {
			money = money.add(new BigDecimal(vFundDetail.getAfterMoney()));
		}
		newVars.put("surplusnotMoney", project.getProjectMoney()
				.subtract(money));
		newFlowRunInfo.getVariables().putAll(newVars);
		newFlowRunInfo.setBusMap(newVars);
		newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
		newFlowRunInfo.setFlowSubject(project.getProjectName() + "-"
				+ project.getProjectNumber());
		ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
		String str = "";
		if (run != null && run.getPiId() != null) {
			str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),
					user.getUserId().toString(), project.getProjectName());
			/*
			 * String piId = run.getPiId(); String userId =user.getUserId()+"";
			 * PagingBean pb =new PagingBean(0,5); Boolean strs
			 * =judge(userId,"alterAccrualFlow_"
			 * +org.getKey(),pb,project.getProjectName
			 * (),project.getProjectNumber(),piId); if(strs){ List<TaskImpl>
			 * list=flowTaskService.getTaskByExecutionId(piId); if(null!=list &&
			 * list.size()>0){ Task task = (Task)list.get(0); if(task!=null){
			 * String taskId = task.getId(); String activityName =
			 * task.getActivityName();
			 * str="taskId:'"+taskId+"',activityName:'"+activityName+"'"; } }
			 * }else{ str="taskId:'1',activityName:''"; }
			 */
		}
		return str;
	}
	

	/**
	 * 融资租赁--提前还款流程-提前还款审批提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer askForEarlyRepaymentAuditFlowNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String flag = flowRunInfo.getRequest().getParameter("flag"); // 项目ID;
				if (flag.equals("shenpi")) {
					if (transitionName != null && !"".equals(transitionName)) {
						Map<String, Object> vars = new HashMap<String, Object>();
						if (transitionName.contains("终止")
								|| transitionName.contains("结束")) {
							String projectId = flowRunInfo.getRequest().getParameter("projectId_flow");
							if (null != projectId && !"".equals(projectId)) {
								FlLeaseFinanceProject project = this.dao.get(Long.valueOf(projectId));
								project.setIsOtherFlow(Short.valueOf("0"));
								this.dao.save(project);
							}
							flowRunInfo.setStop(true);
						} else {
							ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
							if (currentForm != null) {
								boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
								if (!isToNextTask) {
									flowRunInfo.setAfresh(true);// 表示打回重做
									ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "raotApplyFor");
									if (processForm != null && processForm.getCreatorId() != null) {
										String creatorId = processForm.getCreatorId().toString();
										vars.put("flowAssignId", creatorId);
									}
								}
							}
						}
						vars.put("raotExamineAndApproveResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}
				if (flag.equals("fengxianshehe")) {
					if (transitionName != null && !"".equals(transitionName)) {
						String projectId = flowRunInfo.getRequest().getParameter("projectId_flow"); // 项目ID
						String slAlteraccrualRecordId = flowRunInfo.getRequest().getParameter("slAlteraccrualRecordId");
						List<SlFundIntent> listall = slFundIntentService.getByProjectId(Long.valueOf(projectId),"LeaseFinance");
						for (SlFundIntent s : listall) {
							if (s.getAfterMoney().compareTo(new BigDecimal("0")) == 0) {
								s.setIsValid(Short.valueOf("1"));
								this.slFundIntentDao.save(s);
							}
						}

						List<SlFundIntent> allintent = this.slFundIntentDao
								.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlteraccrualRecordId),"LeaseFinance", Long.valueOf(projectId));
						for (SlFundIntent s : allintent) {
							s.setIsValid(Short.valueOf("0")); // 最后审批通过了
							this.slFundIntentDao.save(s);
						}
						// 先把所有的变成无效，再在下面把新生成的变成有效，这样就
						// 这样就把新生产变成有效，之前没对过账变成无效
					}
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 *融资租赁--提前还款流程-提前还款款项确认提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer earlyRepaymentConfirmThePlanOfFundNextStep(
			FlowRunInfo flowRunInfo) {
		try {
			String projectId = flowRunInfo.getRequest().getParameter("projectId_flow");
			String slEarlyRepaymentId = flowRunInfo.getRequest().getParameter("slEarlyRepaymentId");
			String fundIntentJsonData = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
			String prepayintentPeriod1 = flowRunInfo.getRequest().getParameter("prepayintentPeriod1");
			String slSuperviseRecordId = prepayintentPeriod1.substring(0,prepayintentPeriod1.lastIndexOf("."));
			SlEarlyRepaymentRecord slEarlyRepaymentRecord_temp = new SlEarlyRepaymentRecord();
			BeanUtil.populateEntity(flowRunInfo.getRequest(),slEarlyRepaymentRecord_temp, "slEarlyRepaymentRecord");
			slEarlyRepaymentRecord_temp.setProjectId(Long.valueOf(projectId));
			slEarlyRepaymentRecordService.save(slEarlyRepaymentRecord_temp);
			FlLeaseFinanceProject flLeaseFinanceProject = dao.get(Long.valueOf(projectId));
			DateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			List<SlFundIntent> slist = null;
			if (!slEarlyRepaymentRecord_temp.getAccrualtype().equals("ontTimeAccrual")) {
				if (slSuperviseRecordId.equals("0")) {
					List<SlFundIntent> flist = slFundIntentService.getByIntentPeriod(Long.valueOf(projectId),"LeaseFinance", "leasePrincipalRepayment", null,
									slEarlyRepaymentRecord_temp.getPrepayintentPeriod());
					if (null != flist && flist.size() > 0) {
						SlFundIntent sf = flist.get(0);
						slist = slFundIntentService.getListByIntentDate(
								flLeaseFinanceProject.getProjectId(),
								flLeaseFinanceProject.getBusinessType(), ">'"
										+ sf.getIntentDate(), null);
					}
				} else {
					List<SlFundIntent> flist = slFundIntentService
							.getByIntentPeriod(Long.valueOf(projectId),
									"LeaseFinance", "leasePrincipalRepayment", Long
											.valueOf(slSuperviseRecordId),
									slEarlyRepaymentRecord_temp
											.getPrepayintentPeriod());
					if (null != flist && flist.size() > 0) {
						SlFundIntent sf = flist.get(0);
						slist = slFundIntentService.getListByIntentDate(
								flLeaseFinanceProject.getProjectId(),
								flLeaseFinanceProject.getBusinessType(), ">'"
										+ sf.getIntentDate(), Long
										.valueOf(slSuperviseRecordId));
					}
				}
			} else {
				if (slSuperviseRecordId.equals("0")) {
					slist = slFundIntentService.getListByIntentDate(
							flLeaseFinanceProject.getProjectId(),
							flLeaseFinanceProject.getBusinessType(), ">'"
									+ sd.format(slEarlyRepaymentRecord_temp
											.getEarlyDate()), null);
				} else {
					slist = slFundIntentService.getListByIntentDate(
							flLeaseFinanceProject.getProjectId(),
							flLeaseFinanceProject.getBusinessType(), ">'"
									+ sd.format(slEarlyRepaymentRecord_temp
											.getEarlyDate()), Long
									.valueOf(slSuperviseRecordId));
				}
			}
			List<SlFundIntent> ftlist = slFundIntentService
					.getlistbyslEarlyRepaymentId(Long.valueOf(slEarlyRepaymentId), "LeaseFinance", Long.valueOf(projectId));
			for (SlFundIntent slFundIntent : ftlist) {
				slFundIntentService.evict(slFundIntent);
				if (slFundIntent.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
					slFundIntentService.remove(slFundIntent);
				}
			}
			if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
				String[] fundIntentJsonArr = fundIntentJsonData.split("@");
				for (int i = 0; i < fundIntentJsonArr.length; i++) {
					String str = fundIntentJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
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
						SlFundIntent1.setIsValid(Short.valueOf("0"));
						SlFundIntent1.setIsCheck(Short.valueOf("0"));
						SlFundIntent1.setBusinessType("LeaseFinance");
						SlFundIntent1.setCompanyId(flLeaseFinanceProject.getCompanyId());
						SlFundIntent1.setSlEarlyRepaymentId(Long.valueOf(slEarlyRepaymentId));
						slFundIntentService.save(SlFundIntent1);
					}
				}
			}
			for (SlFundIntent s : slist) {
				if (null == s.getSlEarlyRepaymentId()|| !(s.getSlEarlyRepaymentId().toString().equals(slEarlyRepaymentId))) {
					if(!s.getFundType().equals("marginRefund") && !s.getFundType().equals("leaseObjectRetentionFee")){
						s.setEarlyOperateId(Long.valueOf(slEarlyRepaymentId));
						s.setIsValid(Short.valueOf("1"));
						s.setIsCheck(Short.valueOf("1"));
						slFundIntentService.save(s);
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 *融资租赁--提前还款流程-提前还款风险审查提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer updateIsFlowOverNextStep(FlowRunInfo flowRunInfo) {
		try {
			String projectId = flowRunInfo.getRequest().getParameter(
					"projectId_flow");
			String slEarlyRepaymentId = flowRunInfo.getRequest().getParameter(
					"slEarlyRepaymentId_flow");
			if (null != projectId && !"".equals(projectId)) {
				FlLeaseFinanceProject project = this.dao.get(Long.valueOf(projectId));
				project.setIsOtherFlow(Short.valueOf("0"));
				this.dao.save(project);
			}
			if (null != slEarlyRepaymentId && !"".equals(slEarlyRepaymentId)) {
				SlEarlyRepaymentRecord slEarlyRepaymentRecord = this.slEarlyRepaymentRecordService
						.get(Long.valueOf(slEarlyRepaymentId));
				slEarlyRepaymentRecord.setCheckStatus(5);
				this.slEarlyRepaymentRecordService.save(slEarlyRepaymentRecord);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}