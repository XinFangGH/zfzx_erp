package com.zhiwei.credit.service.creditFlow.guarantee.project.impl;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.model.Transition;
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
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.dao.creditFlow.common.GlobalSupervisemanageDao;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlActualToChargeDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlSuperviseRecordDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.project.GLGuaranteeloanProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.meeting.SlConferenceRecordDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.TaskSignDataDao;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GLSuperviseRecord;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.customer.bankRelationPerson.CustomerBankRelationPersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.meeting.SlConferenceRecordService;
import com.zhiwei.credit.service.flow.JbpmService;

public class GLGuaranteeloanProjectServiceImpl extends
		BaseServiceImpl<GLGuaranteeloanProject> implements
		GLGuaranteeloanProjectService {
	
	private  GLGuaranteeloanProjectDao dao;
	
	@Resource
	private GlSuperviseRecordDao glSuperviseRecordDao;
	
	
	public GLGuaranteeloanProjectServiceImpl(GLGuaranteeloanProjectDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	@Resource
	private PersonDao personDao;
	@Resource
	private EnterpriseDao enterpriseDao;
	/*@Resource
	private EnterpriseShareequityDao enterpriseShareequityDao;*/
	@Resource
	private SlActualToChargeDao slActualToChargeDao;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private  CustomerBankRelationPersonService customerBankRelationPersonService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private TaskSignDataDao taskSignDataDao;
	@Resource
	private OrganizationDao organizationdao;
	
	@Resource
	private EnterpriseShareequityDao enterpriseShareequityDao;
	
	
	
	//担保,汇总审评会意见
	@Resource
	private SlConferenceRecordService slConferenceRecordService;
	@Resource
	private SlConferenceRecordDao slConferenceRecordDao;
	
	//担保,银行放款收息表

	@Resource
	private GlobalSupervisemanageDao globalSupervisemanageDao;
	
	/**
	 * 担保项目 业务经理尽职调查节点  保存(更新)信息
	 */
	public Integer updateInfo(GLGuaranteeloanProject gLGuaranteeloanProject,
			Person person, Enterprise enterprise,List<EnterpriseShareequity> listES,
			CustomerBankRelationPerson customerBankRelationPerson,List<SlFundIntent> slFundIntents,
			List<SlActualToCharge> slActualToCharges ,String isDeleteAllFundIntent) {
		try{
			GLGuaranteeloanProject persistent = this.dao.get(gLGuaranteeloanProject.getProjectId());
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
		   	BeanUtil.copyNotNullProperties(persistent, gLGuaranteeloanProject);
    		//BeanUtils.copyProperties(gLGuaranteeloanProject, persistent,new String[] {"id","operationType","flowType","mineType","oppositeType","oppositeID","projectName","projectNumber","createDate","businessType","companyId"});
    		int bankid = 0;
    		if(customerBankRelationPerson.getId()==null||customerBankRelationPerson.getId()==0){ //第一次保存
    			if(null!=customerBankRelationPerson.getBankid()&&"".equals(customerBankRelationPerson.getBankid())){
    				AppUser currentUser = ContextUtil.getCurrentUser();//个人信息
    				Long currentUserId = ContextUtil.getCurrentUserId();
    				customerBankRelationPerson.setCreater(currentUser.getFullname());
    				customerBankRelationPerson.setCreaterId(currentUserId);
    				customerBankRelationPerson.setBelongedId(currentUserId.toString());
    				customerBankRelationPerson.setCreatedate(DateUtil.getNowDateTimeTs());
    				customerBankRelationPersonService.addPerson(customerBankRelationPerson);
    				persistent.setBankId(customerBankRelationPerson.getId());
    			}
    		}else {
    			CustomerBankRelationPerson pasint = this.customerBankRelationPersonService.getById(customerBankRelationPerson.getId());
    			pasint.setFenbankid(customerBankRelationPerson.getFenbankid());
    			pasint.setBlmphone(customerBankRelationPerson.getBlmphone());
    			pasint.setBlmtelephone(customerBankRelationPerson.getBlmtelephone());
    			pasint.setEmail(customerBankRelationPerson.getEmail());
    			pasint.setDuty(customerBankRelationPerson.getDuty());
    			pasint.setName(customerBankRelationPerson.getName());
    			customerBankRelationPersonService.addPerson(pasint);
    			persistent.setBankId(customerBankRelationPerson.getId());
    			bankid = pasint.getBankid();
    		}
       		// gaomm 银行保证金
			//List<SlActualToCharge> allactual=this.slActualToChargeDao.listbyproject(persistent.getProjectId(),"Guarantee");
	    	List<SlFundIntent> allintent=this.slFundIntentDao.getByProjectId1(persistent.getProjectId(),"Guarantee");
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
    		Map<String,BigDecimal> map=slFundIntentService.saveGuaranteeProjectfiance(persistent.getProjectId(),"Guarantee");
    		persistent.setPaychargeMoney(map.get("paychargeMoney"));
    		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
    		this.dao.merge(persistent);
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/*@Override
	public Integer updateInfo(GLGuaranteeloanProject gLGuaranteeloanProject,
			Person person, Enterprise enterprise,
			List<EnterpriseShareequity> listES,CustomerBankRelationPerson customerBankRelationPerson,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges ,String isDeleteAllFundIntent) {
		try
		{
		
			GLGuaranteeloanProject persistent=this.dao.get(gLGuaranteeloanProject.getProjectId());
		   	if(persistent.getOppositeType().equals("company_customer"))
        	{
    	   		 if(listES.size()>0)
    	   		 {
    	   			 
    	   			 for(int i=0;i<listES.size();i++)
    	   			 {
    	   				 EnterpriseShareequity es=listES.get(i);
    	   				 if(es.getId()==null)
    	   				 {
    	   					es.setEnterpriseid(enterprise.getId());
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
    	    	 Enterprise ePersistent=this.enterprise1Dao.getEnterpriseById(enterprise.getId());
    	    	 ePersistent.setEnterprisename(enterprise.getEnterprisename());
    	    	 ePersistent.setArea(enterprise.getArea());
    	    	 ePersistent.setShortname(enterprise.getShortname());
    	    	 ePersistent.setHangyeType(enterprise.getHangyeType());
    	    	 ePersistent.setOrganizecode(enterprise.getOrganizecode());
    	    	 ePersistent.setCciaa(enterprise.getCciaa());
    	    	 ePersistent.setTelephone(enterprise.getTelephone());
    	    	 ePersistent.setPostcoding(enterprise.getPostcoding());
    	    	 this.enterprise1Dao.merge(ePersistent);
    	    	 
	    	   	  Person pPersistent=this.personDao.getPersonById(ePersistent.getLegalpersonid());
	    		  pPersistent.setName(person.getName());
	    		  pPersistent.setSex(person.getSex());
	    		  pPersistent.setCardtype(person.getCardtype());
	    		  pPersistent.setCardnumber(person.getCardnumber());
	    		  pPersistent.setTelphone(person.getTelphone());
	    		  pPersistent.setSelfemail(person.getSelfemail());
	    		  this.personDao.merge(pPersistent);
    	    	
        	}
        	else if(persistent.getOppositeType().equals("person_customer"))
        	{
        		  Person pPersistent=this.personDao.getPersonById(person.getId());
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
    		BeanUtils.copyProperties(gLGuaranteeloanProject, persistent,new String[] {"id","operationType","flowType","mineType","mineId","oppositeType","oppositeID","projectName","projectNumber","createDate","businessType"});
    		int bankid=0;
    		if(null==customerBankRelationPerson.getId() || customerBankRelationPerson.getId()==0) //第一次保存
    		{    
    			 if(null!=customerBankRelationPerson.getBankid() && "".equals(customerBankRelationPerson.getBankid()))
    			 {
                  bankRelationPersonService.addPersonNew(customerBankRelationPerson);
    			  persistent.setBankId(customerBankRelationPerson.getId());
    			 }
    		}
    		else
    		{
    			CustomerBankRelationPerson pasint=this.bankRelationPersonService.queryPerson(customerBankRelationPerson.getId());
    			pasint.setFenbankid(customerBankRelationPerson.getFenbankid());
    			pasint.setBlmphone(customerBankRelationPerson.getBlmphone());
    			pasint.setBlmtelephone(customerBankRelationPerson.getBlmtelephone());
    			pasint.setEmail(customerBankRelationPerson.getEmail());
    			pasint.setDuty(customerBankRelationPerson.getDuty());
    			pasint.setName(customerBankRelationPerson.getName());
    			bankRelationPersonService.updatePersonNew(pasint);
    			persistent.setBankId(customerBankRelationPerson.getId());
    			bankid=pasint.getBankid();
    		}
    		
// 	    // gaomm 银行保证金
// 	    	GlBankGuaranteemoney glBankGuaranteemoney= new GlBankGuaranteemoney();
//			List<GlBankGuaranteemoney> listBankGuarantee = glBankGuaranteemoneyDao.getbyprojId(gLGuaranteeloanProject.getProjectId());
//			if(listBankGuarantee.size() != 0){
//				glBankGuaranteemoney=listBankGuarantee.get(0);
//			}
//			glBankGuaranteemoney.setProjectId(gLGuaranteeloanProject.getProjectId());
//			glBankGuaranteemoney.setBusinessType("Guarantee");
//			glBankGuaranteemoney.setOperationType("CompanyBusiness");
// 	    	this.glBankGuaranteemoneyDao.save(glBankGuaranteemoney);
// 	       // gaomm 银行保证金
    			List<SlActualToCharge> allactual=this.slActualToChargeDao.listbyproject(persistent.getProjectId(),"Guarantee");
    	    	List<SlFundIntent> allintent=this.slFundIntentDao.getByProjectId1(persistent.getProjectId(),"Guarantee");
    	    	if(isDeleteAllFundIntent.equals("1")) {
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
        			this.slActualToChargeDao.save(a);
        			
        		}
        		Map<String,BigDecimal> map=slFundIntentService.saveGuaranteeProjectfiance(persistent.getProjectId(),"Guarantee");
        		persistent.setPaychargeMoney(map.get("paychargeMoney"));
        		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
    		this.dao.merge(persistent);
			return 1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}*/
	
	/**
	 * 金融担保，尽职调查提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer diligenceNextStep(FlowRunInfo flowRunInfo) {
		
		String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");//股东信息
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
			
/*			//更新主体单位名称 add by gaoqingrui
			String mineIdStr = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.mineId");
			Long mineId = null;*/
		    	
	    	GLGuaranteeloanProject project = new GLGuaranteeloanProject();
	    	CustomerBankRelationPerson customerBankRelationPerson = new CustomerBankRelationPerson();
	    	BeanUtil.populateEntity(flowRunInfo.getRequest(), customerBankRelationPerson, "customerBankRelationPerson");
	    	BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "gLGuaranteeloanProject");
	    	if("true".equals(AppUtil.getSystemIsGroupVersion())){//集团办  主体单位Id与 分公司Id一致
	    		project.setCompanyId(project.getMineId());
	    	}
	    	GLGuaranteeloanProject persistent = this.dao.get(project.getProjectId());
//	    	if(null!= mineIdStr&&!"".equals(mineIdStr)){
//				mineId = Long.valueOf(mineIdStr);
//				gLGuaranteeloanProject.setMineId(mineId);
//			}
	    	BeanUtil.copyNotNullProperties(persistent,project);//替换下一句
	    	//BeanUtils.copyProperties(project, persistent,new String[] {"id","operationType","flowType","mineType","mineId","oppositeType","oppositeID","projectName","projectNumber","createDate","businessType"});
	    	
	    	int bankid = 0;
	    	if(customerBankRelationPerson.getId()==null||customerBankRelationPerson.getId()==0){ //第一次保存
	    		AppUser currentUser = ContextUtil.getCurrentUser();//个人信息
	    		Long currentUserId = ContextUtil.getCurrentUserId();
	    		customerBankRelationPerson.setCreater(currentUser.getFullname());
	    		customerBankRelationPerson.setCreaterId(currentUserId);
	    		customerBankRelationPerson.setBelongedId(currentUserId.toString());
	    		customerBankRelationPerson.setCreatedate(DateUtil.getNowDateTimeTs());
	    		customerBankRelationPersonService.addPerson(customerBankRelationPerson);
    			persistent.setBankId(customerBankRelationPerson.getId());
    		}else{
    			CustomerBankRelationPerson pasint=this.customerBankRelationPersonService.getById(customerBankRelationPerson.getId());
    			pasint.setFenbankid(customerBankRelationPerson.getFenbankid());
    			pasint.setBlmphone(customerBankRelationPerson.getBlmphone());
    			pasint.setBlmtelephone(customerBankRelationPerson.getBlmtelephone());
    			pasint.setName(customerBankRelationPerson.getName());
    			pasint.setDuty(customerBankRelationPerson.getDuty());
    			pasint.setEmail(customerBankRelationPerson.getEmail());
    			customerBankRelationPersonService.addPerson(pasint);
    			persistent.setBankId(customerBankRelationPerson.getId());
    			bankid=pasint.getBankid();
    		}

			//费用收支
			String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
//			slActualToChargeService.savejson(slActualToChargeJson, persistent.getProjectId(),"Guarantee",Short.parseShort("0"),null);
			slActualToChargeService.savejson(slActualToChargeJson, persistent.getProjectId(),"Guarantee",Short.parseShort("0"),persistent.getCompanyId());
			Map<String,BigDecimal> map = slFundIntentService.saveGuaranteeProjectfiance(persistent.getProjectId(),"Guarantee");
    		persistent.setPaychargeMoney(map.get("paychargeMoney"));
    		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
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
	    	return 1;
		}catch (Exception e) {
		   e.printStackTrace();
		   logger.error("金融担保-尽职调查提交下一步出错:"+e.getMessage());
		   return 0;
		}
	}
	
	/**
	 * 金融担保-风险管理提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer riskDepManageNextStep(FlowRunInfo flowRunInfo){
		try{
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars = new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							}
						}
		    		}
					vars.put("riskManageResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("金融担保-风险管理提交下一步出错："+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 金融担保-风险总监审核提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer directorCheckNextStep(FlowRunInfo flowRunInfo){
		try{
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars = new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
							if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
				    			flowRunInfo.setAfresh(true);//表示打回重做
				    			vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
				    		}
						}
		    		}
					vars.put("directorCheckResult",transitionName);
		    		flowRunInfo.getVariables().putAll(vars);
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("金融担保-风险总监审核提交下一步出错："+e.getMessage());
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

	@Override
	public Integer updateEnterpriceReportMaterial(FlowRunInfo flowRunInfo) {
		 try
		 {       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }
		 	String transitionName = flowRunInfo.getTransitionName();
			if(transitionName!=null&&!"".equals(transitionName)){
				Map<String,Object> vars=new HashMap<String, Object>();
	    		ProcessForm processForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
	    		if(processForm!=null){
	    			ProUserAssign userAssign = creditProjectService.getByRunIdActivityName(processForm.getRunId(), transitionName);
	    			if(userAssign!=null&&userAssign.getTaskSequence()!=null&&!"".equals(userAssign.getTaskSequence())){
	    				if(userAssign.getTaskSequence().contains("glnProjectManagerSurveyed")){//打回到"业务经理尽职调查"
	    					String creatorId = "1";//默认一个值，为超级管理员。
	    					ProcessForm pform = processFormDao.getByRunIdFlowNodeKey(processForm.getRunId(), "glnProjectManagerSurveyed");
		    				if(pform!=null&&pform.getCreatorId()!=null){
		    					creatorId = pform.getCreatorId().toString();
		    					vars.put("flowAssignId",creatorId);
		    				}
	    				}
	    			}
	    		}
	    		
	    		vars.put("bmMeettingResult",transitionName);
	    		flowRunInfo.getVariables().putAll(vars);
			}
			   /* String shenpi = flowRunInfo.getRequest().getParameter("shenpi");
			    Map<String,Object> vars=new HashMap<String, Object>();
			    if(shenpi!=null&&shenpi.equals("2")){//表示打回重做
					flowRunInfo.setAfresh(true);
				}
				vars.put("meetting",Integer.valueOf(shenpi));
				flowRunInfo.getVariables().putAll(vars);*/
			    return 1;
		 }
		 catch (Exception e) {
			  e.printStackTrace();
			  logger.error("业务主管审查上报材料执行下一步  出错:"+e.getMessage());
			  return 0;
		}
	}

	@Override
	public Integer updateEnterpriceRiskManagerAudit(FlowRunInfo flowRunInfo) {
	
		try {
			
			if(flowRunInfo.isBack()){
				return 1;
			}
			String transitionName = flowRunInfo.getTransitionName();
			if(transitionName!=null&&!"".equals(transitionName)){
				Map<String,Object> vars=new HashMap<String, Object>();
	    		ProcessForm processForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
	    		if(processForm!=null){
	    			ProUserAssign userAssign = creditProjectService.getByRunIdActivityName(processForm.getRunId(), transitionName);
	    			if(userAssign!=null&&userAssign.getTaskSequence()!=null&&!"".equals(userAssign.getTaskSequence())){
	    				if(userAssign.getTaskSequence().contains("glnBeforeSynthesizeAnalyse")){//打回到"保前风险综合分析"
	    					String creatorId = "1";//默认一个值，为超级管理员。
	    					ProcessForm pform = processFormDao.getByRunIdFlowNodeKey(processForm.getRunId(), "glnBeforeSynthesizeAnalyse");
		    				if(pform!=null&&pform.getCreatorId()!=null){
		    					creatorId = pform.getCreatorId().toString();
		    					vars.put("flowAssignId",creatorId);
		    				}
	    				}
	    			}
	    		}
	    		
	    		vars.put("riskmMeettingResult",transitionName);
	    		flowRunInfo.getVariables().putAll(vars);
			}
			/*String meetting = flowRunInfo.getRequest().getParameter("meetting");
			String temp = flowRunInfo.getRequest().getParameter("isFlowFlag");
			GLGuaranteeloanProject project=new GLGuaranteeloanProject();
	        BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "gLGuaranteeloanProject");
			boolean isFlowFlag=true;
			if(null==temp)
			{
				isFlowFlag=false;
			}
			Map<String,Object> vars=new HashMap<String, Object>();
			Integer mt=0;
			if(null!=meetting && !"".equals(meetting))
			{
				if(meetting.equals("5")){//表示打回重做
					flowRunInfo.setAfresh(true);
				}
				mt=Integer.valueOf(meetting);
			}
			vars.put("meetting",mt);
			vars.put("isFlowFlag",isFlowFlag);
			vars.put("creditMoney",project.getProjectMoney().doubleValue());
			flowRunInfo.getVariables().putAll(vars);*/

			return 1;
			     
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error("风险主管审查执行下一步  出错:"+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 项目归档提交
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updateProjectStatus(FlowRunInfo flowRunInfo){
		if(flowRunInfo.isBack()){
			return 1;
		}else{
			try{
				String flowOver = flowRunInfo.getRequest().getParameter("flowOver");
				if(null!=flowOver){
					String projectId = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.projectId");
					if(null!=projectId){
						GLGuaranteeloanProject gl = dao.get(new Long(projectId));
						if(null!=gl){
							//gl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
							gl.setBmStatus(Constants.PROJECT_STATUS_COMPLETE);
							gl.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
							dao.merge(gl);
							return 1;
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public void updateSupervise(Long projectId,List<SlFundIntent> slFundIntents,List<SlActualToCharge> slActualToCharges) {
		
//		       for(GlProcreditSupervision gs:glProcreditSupervisions){
//			   gs.setProjectId(projectId);
//			   this.glProcreditSupervisionDao.save(gs);
//			   
//		      }
		
		       for(SlActualToCharge slActualToCharge:slActualToCharges){
					this.slActualToChargeDao.save(slActualToCharge);
				}
				for(SlFundIntent a:slFundIntents){
					slFundIntentDao.save(a);
				}
	}

	@Override
	public boolean askForProject(Long projectId,
			List<SlActualToCharge> slActualToCharges,
			List<SlFundIntent> slFundIntents,
			GLSuperviseRecord glSuperviseRecord, String categoryIds) {
		glSuperviseRecord.setProjectId(projectId);
		glSuperviseRecordDao.save(glSuperviseRecord);   
		for(SlActualToCharge slActualToCharge:slActualToCharges){
			this.slActualToChargeDao.save(slActualToCharge);
		}
		for(SlFundIntent a:slFundIntents){
			slFundIntentDao.save(a);
			
		}
		//保存的同时更新合同分类表ProcreditContractCategory add by chencc start...
		if(!"".equals(categoryIds)){
			String [] idArray = categoryIds.split(",");
			if(idArray.length >0){
				for(int i=0;i<idArray.length;i++ ){
					if(!"".equals(idArray[i])&& idArray[i]!=null && StringUtil.isNumeric(idArray[i])){
						try {
							Object [] obj = {glSuperviseRecord.getId(),Integer.parseInt(idArray[i])};
							creditBaseDao.excuteSQL("update ProcreditContract set isApply=1,clauseId =? where id =?",obj);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		//保存的同时更新合同分类表ProcreditContractCategory add by chencc end...
		return true;
	}
	
	/**
	 * 金融担保-审保会决议确认提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer examinationArrangementNextStep(FlowRunInfo flowRunInfo){
		try{
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String signVoteType = flowRunInfo.getRequest().getParameter("signVoteType1");// 投票结果：同意(1)、决绝(2)、弃权(0)。
				String userId = ContextUtil.getCurrentUserId().toString();
				String userFullName = ContextUtil.getCurrentUser().getFullname();
				String taskId = flowRunInfo.getTaskId();
				Map<String, String> vars = new HashMap<String, String>();
				
				if (taskId != null && !"".equals(taskId)) {
					Task parentTask = jbpmService.getParentTask(taskId.toString());
					ProcessForm processForm = processFormDao.getByTaskId(taskId);
					if (processForm != null) {
						boolean saveTaskSignDataOk = this.saveTaskSignData(processForm, userId, userFullName, parentTask.getId(), signVoteType);
						if (saveTaskSignDataOk) {
							vars.put("microNormalSDH", "false");
							flowRunInfo.getVariables().putAll(vars);
							List<Transition> trans = jbpmService.getTransitionsByTaskId(taskId);
							if (trans != null && trans.size() != 0) {
								String transitionName = trans.get(0).getName();
								flowRunInfo.setTransitionName(transitionName);
							}
						}
					}
				}
				/*String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
					if(currentForm!=null){
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							String creatorId = "1";// 默认一个值，为超级管理员。
							ProcessForm pform = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "gnDueDiligence");
							if (pform != null&& pform.getCreatorId() != null) {
								creatorId = pform.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						}
						vars.put("sbhResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}*/
			}
			/*List<Transition> trans=jbpmService.getTransitionsByTaskId(flowRunInfo.getTaskId());
			if(trans!=null&&trans.size()!=0){
				String transitionName = trans.get(0).getName();
				flowRunInfo.setTransitionName(transitionName);
			}*/
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("金融担保-审保会决议确认提交下一步出错："+e.getMessage());
			return 0;
		}
	}
	
	//-------------------------------------高庆瑞------------------------------------------------------
	
	/**
	 * 金融担保流程-主管初审提交下一步    //风险管理部  主管初审共用一个service●№○
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	@Override
	public Integer gManageFirstCheckNextStep(FlowRunInfo flowRunInfo){
		try{       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String transitionName = flowRunInfo.getTransitionName();
		    	String projectId = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.projectId");
		    	String riskAttacheId = flowRunInfo.getRequest().getParameter("riskAttacheId");
		      	if(transitionName!=null&&!"".equals(transitionName)){
		      		Map<String,Object> vars=new HashMap<String, Object>();
		      		if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			if(null!=projectId){
							GLGuaranteeloanProject gl = dao.get(new Long(projectId));
							if(null!=gl){
								gl.setBmStatus(Constants.PROJECT_STATUS_STOP);
								gl.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
								dao.merge(gl);
								flowRunInfo.setStop(true);
							}
						}
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
				    			if (riskAttacheId != null && !"".equals(riskAttacheId)) {
									String assignUserId = riskAttacheId;
									vars.put("flowAssignId", assignUserId);
								}
				    		}
				    	}
		    		}
			    	vars.put("manageFirstCheckResult",transitionName);
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
	/**
	 * 金融担保流程-风险管理部任务提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	@Override
	public Integer gRiskManagerAllocateNextStep(FlowRunInfo flowRunInfo){	
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
				    		if(!isToNextTask){}//无打回操作
				    	}
		    		}
			    	vars.put("riskManagerAllocateResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
		    	}
		    	return 1;
		    }
		 }catch (Exception e) {
			e.printStackTrace();
			logger.error("风险管理部任务  信息出错:"+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 金融担保流程-风险专员审查提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	@Override
	public Integer gRiskAttacheCheckNextStep(FlowRunInfo flowRunInfo){	
		try{       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String transitionName = flowRunInfo.getTransitionName();
		    	String projectId = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.projectId");
		      	if(transitionName!=null&&!"".equals(transitionName)){
		      		Map<String,Object> vars=new HashMap<String, Object>();
		      		if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			if(null!=projectId){
							GLGuaranteeloanProject gl = dao.get(new Long(projectId));
							if(null!=gl){
								//gl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
								gl.setBmStatus(Constants.PROJECT_STATUS_STOP);
								gl.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
								dao.merge(gl);
								flowRunInfo.setStop(true);
							}
						}
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
			    				}}//无打回操作
				    	}
		    		}
			    	vars.put("riskAttacheCheckResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
		    	}
		    	return 1;
		    }
		 }catch (Exception e) {
			e.printStackTrace();
			logger.error("主管初审提交任务  信息出错:"+e.getMessage());
			return 0;
		}
	}
	/**
	 * 金融担保流程-风险主管审查提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	@Override
	public Integer gRiskManagerCheckNextStep(FlowRunInfo flowRunInfo){	
		try{       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String transitionName = flowRunInfo.getTransitionName();
		    	String onlineJudgementIds = flowRunInfo.getRequest().getParameter("onlineJudgementIds");
		    	String projectId = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.projectId");
		      	if(transitionName!=null&&!"".equals(transitionName)){
		      		Map<String,Object> vars=new HashMap<String, Object>();
		      		if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			if(null!=projectId){
							GLGuaranteeloanProject gl = dao.get(new Long(projectId));
							if(null!=gl){
								//gl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
								gl.setBmStatus(Constants.PROJECT_STATUS_STOP);
								gl.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
								dao.merge(gl);
								flowRunInfo.setStop(true);
							}
						}
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				    	if(currentForm!=null){//多个打回
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
								/*//true表示流程正常往下流转,false则表示打回。
				    			flowRunInfo.setAfresh(true);//表示打回重做
				    			ProcessForm processForm = null;
				    			if("主管初审".equals(transitionName)){
				    				processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "managerFirstCheck");
				    			}else{
				    				processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "riskAttacheCheck");
				    			}
			    				if(processForm!=null&&processForm.getCreatorId()!=null){
			    					String creatorId = processForm.getCreatorId().toString();
			    					vars.put("flowAssignId",creatorId);
			    				}*/
							}else{
								if("线上评审会决议".equals(transitionName)){
									if (onlineJudgementIds != null && !"".equals(onlineJudgementIds)) {
										String assignUserId = onlineJudgementIds;
										vars.put("flowAssignId", assignUserId);
									}
				    			}
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
	
	/**
	 * 金融担保-线上评审会决议提交下一步
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer gOnlineJudgementNextStep(FlowRunInfo flowRunInfo){
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
					if (processForm != null) {/*
//						boolean saveTaskSignDataOk = this.saveTaskSignData(processForm, userId, userFullName, parentTask.getId(), signVoteType);
						if (saveTaskSignDataOk) {
	http://localhost:8181/ah_s3group2/ext/resources/images/default/s.gif						vars.put("microNormalSDH", "false");
							flowRunInfo.getVariables().putAll(vars);
							List<Transition> trans = jbpmService.getTransitionsByTaskId(taskId);
							if (trans != null && trans.size() != 0) {
								String transitionName = trans.get(0).getName();
								flowRunInfo.setTransitionName(transitionName);
							}
						}
					*/}
				}
				/*String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
					if(currentForm!=null){
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							String creatorId = "1";// 默认一个值，为超级管理员。
							ProcessForm pform = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "gnDueDiligence");
							if (pform != null&& pform.getCreatorId() != null) {
								creatorId = pform.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						}
						vars.put("sbhResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}*/
			}
			/*List<Transition> trans=jbpmService.getTransitionsByTaskId(flowRunInfo.getTaskId());
			if(trans!=null&&trans.size()!=0){
				String transitionName = trans.get(0).getName();
				flowRunInfo.setTransitionName(transitionName);
			}*/
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("金融担保-审保会决议确认提交下一步出错："+e.getMessage());
			return 0;
		}
	}
	/**
	 * 金融担保流程-汇总审评会意见及纪要提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	@Override
	public Integer gCollectDiscussCommentNextStep(FlowRunInfo flowRunInfo){	
		try{     
			String projectId = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.projectId");
			GLGuaranteeloanProject persistent = null;
			if(null != projectId){
				persistent = this.dao.get(Long.valueOf(projectId));
			}
			String MId= flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.mineId");
			Long mineId = null;
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars=new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
						if(null != persistent){
							persistent.setBmStatus(Constants.PROJECT_STATUS_STOP);
							persistent.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
							dao.merge(persistent);
						}
						flowRunInfo.setStop(true);
					}else{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {//更正  8月24 add by gaoqingrui  
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
								
								//流程打回 才更新项目信息 add by gao 8月24日
								if(null == MId || "".equals(MId)){//如果不为空则说明对应的companyId更新
									/*更新款项信息*/
		    						GLGuaranteeloanProject project = new GLGuaranteeloanProject();
							    	BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "gLGuaranteeloanProject");
							    	BeanUtil.copyNotNullProperties(persistent, project);
							    	this.dao.save(persistent);
								}else{
									mineId = Long.valueOf(MId);
									if(null!=persistent){
										String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
			    						slActualToChargeService.savejson(slActualToChargeJson, persistent.getProjectId(),"Guarantee",Short.parseShort("0"),persistent.getCompanyId());
										GLGuaranteeloanProject project = new GLGuaranteeloanProject();
								    	BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "gLGuaranteeloanProject");
								    	BeanUtil.copyNotNullProperties(persistent, project);
										persistent.setMineId(mineId);
										if("true".equals(AppUtil.getSystemIsGroupVersion())){//集团版 mineId 和companyId一致
		    								persistent.setCompanyId(mineId);
		    							}
										this.dao.save(persistent);
									}
								}
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
			logger.error("汇总审评会意见及纪要任务  信息出错:"+e.getMessage());
			return 0;
		}
	}
	/**
	 * 担保流程-汇总评审会意见提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	@Override
	public Integer gCollectJudgemenNextStep(FlowRunInfo flowRunInfo){	
		try{       
			String projectId = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.projectId");
			GLGuaranteeloanProject persistent = null;
			if(null != projectId ){
				persistent = this.dao.get(Long.valueOf(projectId));
			}
			String MId= flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.mineId");
			Long mineId = null;
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String transitionName = flowRunInfo.getTransitionName();
		      	if(transitionName!=null&&!"".equals(transitionName)){
		      		Map<String,Object> vars=new HashMap<String, Object>();
		      		if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			if(null != persistent){
		      				persistent.setBmStatus(Constants.PROJECT_STATUS_STOP);
		      				persistent.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
							dao.merge(persistent);
		      			}
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
			    				}}else{
			    					if(null == MId || "".equals(MId)){//如果不为空则说明对应的companyId更新
			    						/*更新款项信息*/
			    						GLGuaranteeloanProject project = new GLGuaranteeloanProject();
								    	BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "gLGuaranteeloanProject");
								    	BeanUtil.copyNotNullProperties(persistent, project);
								    	this.dao.save(persistent);
			    					}else{
			    						mineId = Long.valueOf(MId);
			    						String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
			    						slActualToChargeService.savejson(slActualToChargeJson, persistent.getProjectId(),"Guarantee",Short.parseShort("0"),persistent.getCompanyId());
			    						GLGuaranteeloanProject project = new GLGuaranteeloanProject();
								    	BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "gLGuaranteeloanProject");
								    	BeanUtil.copyNotNullProperties(persistent, project);
			    						if(null != persistent){
			    							persistent.setMineId(mineId);
			    							if("true".equals(AppUtil.getSystemIsGroupVersion())){//集团版 mineId 和companyId一致
			    								persistent.setCompanyId(mineId);
			    							}
			    							this.dao.save(persistent);
			    						}
			    					}
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
			logger.error("汇总评审会意见任务提交  信息出错:"+e.getMessage());
			return 0;
		}
	}
	/**
	 * 金融担保流程-总公司总经理审批提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	@Override
	public Integer gGeneralManagerExamineNextStep(FlowRunInfo flowRunInfo){	
		try{       
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				String projectId = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.projectId");
				String transitionName = flowRunInfo.getTransitionName();
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars=new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
						if(null!=projectId){
							GLGuaranteeloanProject gl = dao.get(new Long(projectId));
							if(null!=gl){
								//gl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
								gl.setBmStatus(Constants.PROJECT_STATUS_STOP);
								gl.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
								dao.merge(gl);
								flowRunInfo.setStop(true);
							}
						}
					}else{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
							if(!isToNextTask){}//无打回节点
						}
					}
					vars.put("generalManagerExamineResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("汇总评审会意见任务提交  信息出错:"+e.getMessage());
			return 0;
		}
	}
	/**
	 * 金融担保流程-签署合同提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	@Override
	public Integer gSignContractNextStep(FlowRunInfo flowRunInfo){	
		try{       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String projectId = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.projectId");
		    	String transitionName = flowRunInfo.getTransitionName();
		      	if(transitionName!=null&&!"".equals(transitionName)){
		      		Map<String,Object> vars=new HashMap<String, Object>();
		      		if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			if(null!=projectId){
							GLGuaranteeloanProject gl = dao.get(new Long(projectId));
							if(null!=gl){
								gl.setBmStatus(Constants.PROJECT_STATUS_STOP);
								gl.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
								dao.merge(gl);
								flowRunInfo.setStop(true);
							}
						}
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				    	if(currentForm!=null){
				    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
				    		if(!isToNextTask){}//无打回操作
				    	}
		    		}
			    	vars.put("signContractResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
		    	}
		    	return 1;
		    }
		 }catch (Exception e) {
			e.printStackTrace();
			logger.error("签署合同任务提交  信息出错:"+e.getMessage());
			return 0;
		}
	}
	/**
	 * 金融担保流程-银行登记信息提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	@Override
	public Integer gBankRegisterNextStep(FlowRunInfo flowRunInfo){	
	
		try{       
			if(flowRunInfo.isBack()){
				return 1;
			}else{
				//提交任务前，保存银行放款收息表
				String transitionName = flowRunInfo.getTransitionName();
				String fundIntentJsonData = flowRunInfo.getRequest().getParameter("fundIntentJsonData1");
				String projId = flowRunInfo.getRequest().getParameter("projId");
				Long projectId = Long.parseLong(projId);
				
				if(transitionName!=null&&!"".equals(transitionName)){
					Map<String,Object> vars=new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
						if(null!=projectId){
							GLGuaranteeloanProject gl = dao.get(projectId);
							if(null!=gl){
								//gl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
								gl.setBmStatus(Constants.PROJECT_STATUS_STOP);
								gl.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
								dao.merge(gl);
								flowRunInfo.setStop(true);
							}
						}
					}else{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if(currentForm!=null){
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
							if(!isToNextTask){
							}else{
								if(null!=projectId){
									GLGuaranteeloanProject gl = dao.get(projectId);
									if(null!=gl){
										//gl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
										gl.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
//											gl.set(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
										dao.merge(gl);
									}
									List<SlActualToCharge> list = slActualToChargeService.listbyproject(projectId, "Guarantee", "'premiumMoney','earnestMoney'");
									for(SlActualToCharge s:list){
										s.setIsCheck(Short.valueOf("0"));
										slActualToChargeService.save(s);
									}
									if(null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
										String[] fundIntentJsonArr = fundIntentJsonData.split("@");
										
										for(int i=0; i<fundIntentJsonArr.length; i++) {
											String str = fundIntentJsonArr[i];
											JSONParser parser = new JSONParser(new StringReader(str));
											try{
												SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
												slFundIntent.setProjectId(gl.getProjectId());
												slFundIntent.setProjectName(gl.getProjectName());
												slFundIntent.setProjectNumber(gl.getProjectNumber());
												slFundIntent.setCompanyId(gl.getCompanyId());
												if(null==slFundIntent.getFundIntentId()){
													BigDecimal lin = new BigDecimal(0.00);

													if (slFundIntent.getIncomeMoney().compareTo(lin) == 0) {
														slFundIntent.setNotMoney(slFundIntent
																.getPayMoney());
													} else {
														slFundIntent.setNotMoney(slFundIntent
																.getIncomeMoney());

													}
													slFundIntent.setAfterMoney(new BigDecimal(0));
													slFundIntent.setAccrualMoney(new BigDecimal(0));
													slFundIntent.setFlatMoney(new BigDecimal(0));
													Short isvalid = 0;
													slFundIntent.setIsValid(isvalid);
													slFundIntent.setIsCheck(Short.parseShort("0"));
													slFundIntent.setBusinessType("Guarantee");
													slFundIntentService.save(slFundIntent);
												}else{
													BigDecimal lin = new BigDecimal(0.00);
													if (slFundIntent.getIncomeMoney().compareTo(lin) == 0) {
														slFundIntent.setNotMoney(slFundIntent
																.getPayMoney());
													} else {
														slFundIntent.setNotMoney(slFundIntent
																.getIncomeMoney());

													}
													SlFundIntent slFundIntent2 = slFundIntentService
															.get(slFundIntent.getFundIntentId());
													if (slFundIntent2.getAfterMoney().compareTo(
															new BigDecimal(0)) == 0) {
														BeanUtil.copyNotNullProperties(slFundIntent2,
																slFundIntent);
														slFundIntent2.setBusinessType("Guarantee");
														slFundIntent2.setIsCheck(Short.parseShort("0"));
														
													}
													slFundIntentService.save(slFundIntent2);
												}
											} catch(Exception e) {
												e.printStackTrace();
												logger.error("ShareequityAction:"+e.getMessage());
											}
											
										}
									}
								}
							
							}
						}
					}
					vars.put("bankRegisterResult",transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("银行登记任务提交  信息出错:"+e.getMessage());
			return 0;
		}
	}
	/**
	 * 金融担保流程-归档审核确认提交下一步
	 * @param flowRunInfo
	 * @return
	 * @author gaoqingrui
	 */
	@Override
	public Integer gAffirmFilesNextStep(FlowRunInfo flowRunInfo){	
		try{       
		    if(flowRunInfo.isBack()){
		    	return 1;
		    }else{
		    	String transitionName = flowRunInfo.getTransitionName();
		    	String projectId = flowRunInfo.getRequest().getParameter("gLGuaranteeloanProject.projectId");
		      	if(transitionName!=null&&!"".equals(transitionName)){
		      		Map<String,Object> vars=new HashMap<String, Object>();
		      		if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			if(null!=projectId){
							GLGuaranteeloanProject gl = dao.get(new Long(projectId));
							if(null!=gl){
								//gl.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
								gl.setBmStatus(Constants.PROJECT_STATUS_COMPLETE);
								gl.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
								dao.merge(gl);
							}
						}
		      			flowRunInfo.setStop(true);
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				    	if(currentForm!=null){//多个打回
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {//true表示流程正常往下流转,false则表示打回。
				    			flowRunInfo.setAfresh(true);//表示打回重做
				    			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "commitFiles");
			    				if(processForm!=null&&processForm.getCreatorId()!=null){
			    					String creatorId = processForm.getCreatorId().toString();
			    					vars.put("flowAssignId",creatorId);
			    				}
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
			logger.error("归档审核确认任务  信息出错:"+e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer changeStatusCreditFlowProject(FlowRunInfo flowRunInfo) {
		try{
			GlobalSupervisemanage globalSupervisemanage = new GlobalSupervisemanage();
	    	BeanUtil.populateEntity(flowRunInfo.getRequest(), globalSupervisemanage, "globalSupervisemanage");
	    	GlobalSupervisemanage orgGlobalSupervisemanage=globalSupervisemanageDao.get(globalSupervisemanage.getSuperviseManageId());
	    	BeanUtil.copyNotNullProperties(orgGlobalSupervisemanage, globalSupervisemanage);
	    	globalSupervisemanageDao.save(orgGlobalSupervisemanage);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
}
