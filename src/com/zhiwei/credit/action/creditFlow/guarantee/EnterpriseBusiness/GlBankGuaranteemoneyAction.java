package com.zhiwei.credit.action.creditFlow.guarantee.EnterpriseBusiness;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBank;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountRecordService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.VGuaranteeloanProjectService;
import com.zhiwei.credit.service.system.CsDicAreaDynamService;
import com.zhiwei.credit.service.system.GlobalTypeService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class GlBankGuaranteemoneyAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Resource
	private GlBankGuaranteemoneyService glBankGuaranteemoneyService;
	@Resource
	private GlAccountBankCautionmoneyService glAccountBankCautionmoneyService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private VGuaranteeloanProjectService vGuaranteeloanProjectService;
	@Resource
	private CsDicAreaDynamService csDicAreaDynamService;
	@Resource
	private GlAccountBankService glAccountBankService;
	@Resource
	private GLGuaranteeloanProjectService glGuaranteeloanProjectService;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	private GlAccountRecordService glAccountRecordService;
	@Resource
	private FileFormService fileFormService;
	
	private GlBankGuaranteemoney glBankGuaranteemoney;
	private SlFundIntent slFundIntent1;
	private SlFundIntent slFundIntent2;
	private Long projId;
	private Short isRelease;
	private Short isCharge;
	private String businessType;
	private Long bankGuaranteeId;
	private String nodeId;
	private String accountBankId;
	private Integer start;
	private Integer limit;
	private String operationType;
	@Resource
	private CreditProjectService creditProjectService;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getAccountBankId() {
		return accountBankId;
	}

	public void setAccountBankId(String accountBankId) {
		this.accountBankId = accountBankId;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public SlFundIntent getSlFundIntent1() {
		return slFundIntent1;
	}

	public void setSlFundIntent1(SlFundIntent slFundIntent1) {
		this.slFundIntent1 = slFundIntent1;
	}

	public SlFundIntent getSlFundIntent2() {
		return slFundIntent2;
	}

	public void setSlFundIntent2(SlFundIntent slFundIntent2) {
		this.slFundIntent2 = slFundIntent2;
	}

	public Short getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(Short isCharge) {
		this.isCharge = isCharge;
	}

	public Short getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Short isRelease) {
		this.isRelease = isRelease;
	}
	public Long getBankGuaranteeId() {
		return bankGuaranteeId;
	}

	public void setBankGuaranteeId(Long bankGuaranteeId) {
		this.bankGuaranteeId = bankGuaranteeId;
	}

	public GlBankGuaranteemoney getGlBankGuaranteemoney() {
		return glBankGuaranteemoney;
	}

	public void setGlBankGuaranteemoney(GlBankGuaranteemoney glBankGuaranteemoney) {
		this.glBankGuaranteemoney = glBankGuaranteemoney;
	}


	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		int size=0;
		QueryFilter filter=new QueryFilter(getRequest());
		List<GlBankGuaranteemoney> list=new ArrayList<GlBankGuaranteemoney>();
		if(null==nodeId){
		    list= glBankGuaranteemoneyService.getAll(filter);
		    size=glBankGuaranteemoneyService.getAll().size();
		}
		if(null!=nodeId && nodeId.equals("0")){
			list= glBankGuaranteemoneyService.getallbyglAccountBankId(Long.valueOf(accountBankId),start,limit);
              size=glBankGuaranteemoneyService.getallbyglAccountBankIdsize(Long.valueOf(accountBankId));
		}
        if(null!=nodeId &&!nodeId.equals("0")){
			list =glBankGuaranteemoneyService.getallbycautionAccountId(Long.valueOf(nodeId),start,limit);
			 size=glBankGuaranteemoneyService.getallbycautionAccountIdsize(Long.valueOf(nodeId));
		}
		
		for(GlBankGuaranteemoney l:list){
//			GlAccountBankCautionmoney glAccountBankCautionmoney =new GlAccountBankCautionmoney();
//			glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(l.getAccountId());
			GLGuaranteeloanProject glGuaranteeloanProject=new GLGuaranteeloanProject();
			glGuaranteeloanProject=glGuaranteeloanProjectService.get(l.getProjectId());
			glGuaranteeloanProjectService.evict(glGuaranteeloanProject);
			
			l.setProjectName(glGuaranteeloanProject.getProjectName());
			if(glGuaranteeloanProject.getProjectMoney()!=null){
			l.setProjectMoney(glGuaranteeloanProject.getProjectMoney().divide(new BigDecimal(10000)));
			l.setBusinessType(glGuaranteeloanProject.getBusinessType());
			l.setProjectStatus(glGuaranteeloanProject.getProjectStatus());
			l.setBmStatus(glGuaranteeloanProject.getBmStatus());
			l.setTaskId(vGuaranteeloanProjectService.getByProjectId(glGuaranteeloanProject.getProjectId()).get(0).getTaskId());
		     l.setOppositeType(glGuaranteeloanProject.getOppositeType());
			}
			if(null !=l.getOperationType()&&globalTypeService.getByNodeKey(l.getBusinessType()).size() !=0){
				String typeName = globalTypeService.getByNodeKeyCatKey(l.getOperationType(), "FLOW");
				l.setOperationTypeName(typeName);
				//l.setOperationTypeName(globalTypeService.getByNodeKey(l.getOperationType()).get(0).getTypeName());
			}
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(
				",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("freezeDate",
				"releaseDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"freezeDate","releaseDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();

		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String delete(){
		
		glBankGuaranteemoney=glBankGuaranteemoneyService.get(bankGuaranteeId);
		List<GlAccountRecord>	glAccountRecordlist =glAccountRecordService.getbyprojectId(glBankGuaranteemoney.getProjectId());
		for(GlAccountRecord l:glAccountRecordlist){
			glAccountRecordService.remove(l);
		}
		String typeisfile="typeisbankguaranteemoney";
		String mark="gl_Bank_guaranteemoney."+glBankGuaranteemoney.getProjectId().toString();
		List<FileForm> li=fileFormService.ajaxGetFilesList(typeisfile,mark);
		for(FileForm l:li){
			fileFormService.DeleFile(l.getFileid());
		}
		
		
		String typeisfile1="typeisbackglbankguaranteemoney";
		String mark1="back_gl_bank_guaranteemoney."+glBankGuaranteemoney.getProjectId().toString();
		List<FileForm> li1=fileFormService.ajaxGetFilesList(typeisfile1,mark1);
		for(FileForm l:li1){
			fileFormService.DeleFile(l.getFileid());
		}
		glBankGuaranteemoneyService.remove(bankGuaranteeId);
		
		saveguaranteemoneyAccount(glBankGuaranteemoney.getGlAccountBankId(),glBankGuaranteemoney.getAccountId(),glBankGuaranteemoney.getProjectId());
		return SUCCESS;
	}
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				glBankGuaranteemoneyService.remove(new Long(id));
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
		List<GlBankGuaranteemoney> list=glBankGuaranteemoneyService.getbyprojId(projId);
		GlBankGuaranteemoney glBankGuaranteemoney=new GlBankGuaranteemoney();
		if(list.size() ==0){
			BigDecimal earnestMoney = glGuaranteeloanProjectService.get(projId).getEarnestmoney();
			if(null != earnestMoney) {
				glBankGuaranteemoney.setFreezeMoney(earnestMoney.divide(new BigDecimal(10000)));
			}
		}
		if(list.size() !=0){
			glBankGuaranteemoney=list.get(0);
			if(glBankGuaranteemoney.getAccountId()!=null){
				String account=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId()).getAccountname();
				String name=csDicAreaDynamService.get(glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId()).getBankBranchId()).getRemarks();
				BigDecimal maxfreezeMoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId()).getSurplusMoney();
				glBankGuaranteemoney.setGuaranteeaccount(account);
				glBankGuaranteemoney.setGuaranteebankName(name);
				glBankGuaranteemoney.setMaxfreezeMoney(maxfreezeMoney);
			}
			glBankGuaranteemoney.setProjectName(glGuaranteeloanProjectService.get(glBankGuaranteemoney.getProjectId()).getProjectName());
			String operationTypeName = globalTypeService.getByNodeKeyCatKey(glBankGuaranteemoney.getOperationType(), "FLOW");
			if(!"".equals(operationTypeName)){
				glBankGuaranteemoney.setOperationTypeName(operationTypeName);
				//glBankGuaranteemoney.setOperationTypeName(globalTypeService.getByNodeKey(glBankGuaranteemoney.getOperationType()).get(0).getTypeName());
			}
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(glBankGuaranteemoney));
		sb.append("}"); 
		setJsonString(sb.toString());
		return SUCCESS;
	}


	/**
	 * 添加及保存操作
	 */
	public String save(){   //保证金管理
		if(glBankGuaranteemoney.getBankGuaranteeId()==null){
			if(projId !=null){
		     glBankGuaranteemoney.setProjectId(projId);
			}
			
			glBankGuaranteemoney.setIsRelease(Short.valueOf("0"));
			List<GlBankGuaranteemoney> list= glBankGuaranteemoneyService.getAll();
			for(GlBankGuaranteemoney l:list){
				if(l.getProjectId().equals(glBankGuaranteemoney.getProjectId())){
				setJsonString("{success:true,exsit:false,msg:'此项目已经有保证金记录,请重新选择项目!!!'}");
				return SUCCESS;
				}
			}
			GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
			glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
			glBankGuaranteemoney.setBusinessType("Guarantee");
			glBankGuaranteemoneyService.save(glBankGuaranteemoney);
			
			GlAccountRecord glAccountRecord=new GlAccountRecord();
			glAccountRecord.setCautionAccountId(glBankGuaranteemoney.getAccountId());
			glAccountRecord.setCapitalType(3);
			glAccountRecord.setGlAccountBankId(glBankGuaranteemoney.getGlAccountBankId());
			glAccountRecord.setOprateDate(new Date());
			glAccountRecord.setOprateMoney(glBankGuaranteemoney.getFreezeMoney());
			glAccountRecord.setProjectId(glBankGuaranteemoney.getProjectId());
			AppUser user=ContextUtil.getCurrentUser();
			glAccountRecord.setHandlePerson(user.getFullname());
			
			glAccountRecordService.save(glAccountRecord);
			saveguaranteemoneyAccount(glBankGuaranteemoney.getGlAccountBankId(),glBankGuaranteemoney.getAccountId(),glBankGuaranteemoney.getProjectId());
			
	}else{
		    glBankGuaranteemoney.setIsRelease(Short.valueOf("1"));
			GlBankGuaranteemoney orgGlBankGuaranteemoney=glBankGuaranteemoneyService.get(glBankGuaranteemoney.getBankGuaranteeId());
			int same=0;
			Long accountId=orgGlBankGuaranteemoney.getAccountId();
			Long glAccountBankId=orgGlBankGuaranteemoney.getGlAccountBankId();
			if(!glBankGuaranteemoney.getAccountId().equals(orgGlBankGuaranteemoney.getAccountId())){
				same=1;
			}
			try{
				BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
				
				if(orgGlBankGuaranteemoney.getUnfreezeMoney()!=null){   //解冻编辑
					
					GlAccountRecord glAccountRecord=new GlAccountRecord();
					 List<GlAccountRecord> GlAccountRecords= glAccountRecordService.getbyprojectIdcapitalType(glBankGuaranteemoney.getProjectId(),4);
					 if(GlAccountRecords.size()!=0){
					   glAccountRecord=GlAccountRecords.get(0);
					 }
					glAccountRecord.setCautionAccountId(orgGlBankGuaranteemoney.getAccountId());
					glAccountRecord.setCapitalType(4);
					glAccountRecord.setGlAccountBankId(orgGlBankGuaranteemoney.getGlAccountBankId());
					glAccountRecord.setOprateDate(new Date());
					glAccountRecord.setOprateMoney(orgGlBankGuaranteemoney.getUnfreezeMoney());
					glAccountRecord.setProjectId(orgGlBankGuaranteemoney.getProjectId());
					AppUser user=ContextUtil.getCurrentUser();
					glAccountRecord.setHandlePerson(user.getFullname());
					
					glAccountRecordService.save(glAccountRecord);
				
				}else{ //冻结编辑
					
					GlAccountRecord glAccountRecord=new GlAccountRecord();
					 List<GlAccountRecord> GlAccountRecords= glAccountRecordService.getbyprojectIdcapitalType(glBankGuaranteemoney.getProjectId(),3);
					 if(GlAccountRecords.size()!=0){
						 glAccountRecord=GlAccountRecords.get(0);
					 }
					glAccountRecord.setCautionAccountId(orgGlBankGuaranteemoney.getAccountId());
					glAccountRecord.setCapitalType(3);
					glAccountRecord.setGlAccountBankId(orgGlBankGuaranteemoney.getGlAccountBankId());
					glAccountRecord.setOprateDate(new Date());
					glAccountRecord.setOprateMoney(orgGlBankGuaranteemoney.getFreezeMoney());
					glAccountRecord.setProjectId(orgGlBankGuaranteemoney.getProjectId());
					AppUser user=ContextUtil.getCurrentUser();
					glAccountRecord.setHandlePerson(user.getFullname());
					
					glAccountRecordService.save(glAccountRecord);
				}
				
				
				glBankGuaranteemoneyService.save(orgGlBankGuaranteemoney);
				if(same==1){
				saveguaranteemoneyAccount(glAccountBankId,accountId,orgGlBankGuaranteemoney.getProjectId());
				}
				saveguaranteemoneyAccount(orgGlBankGuaranteemoney.getGlAccountBankId(),orgGlBankGuaranteemoney.getAccountId(),orgGlBankGuaranteemoney.getProjectId());
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		
		return SUCCESS;
		
	}
	public String save1(){   //项目信息保存
		List<GlBankGuaranteemoney> a=new ArrayList<GlBankGuaranteemoney>();
		a=glBankGuaranteemoneyService.getbyprojId(projId);
		 if(glBankGuaranteemoney.getBankGuaranteeId()==null &&a.size()==0){
    			glBankGuaranteemoney.setProjectId(projId);
    			glBankGuaranteemoney.setBusinessType("Guarantee");
    			glBankGuaranteemoney.setOperationType(operationType);
    			glBankGuaranteemoney.setUnfreezeMoney(glBankGuaranteemoney.getFreezeMoney());
    	   if(null !=glBankGuaranteemoney.getAccountId()){
    			GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
			   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
    			   }
     	    	glBankGuaranteemoneyService.save(glBankGuaranteemoney);
     	    	
     	    	
     	    	 GlAccountRecord glAccountRecord=new GlAccountRecord();  //冻结记录
					glAccountRecord.setCautionAccountId(glBankGuaranteemoney.getAccountId());
					glAccountRecord.setCapitalType(3);
					glAccountRecord.setGlAccountBankId(glBankGuaranteemoney.getGlAccountBankId());
					glAccountRecord.setOprateDate(glBankGuaranteemoney.getFreezeDate());
					glAccountRecord.setOprateMoney(glBankGuaranteemoney.getFreezeMoney());
					glAccountRecord.setProjectId(glBankGuaranteemoney.getProjectId());
					AppUser user=ContextUtil.getCurrentUser();
					glAccountRecord.setHandlePerson(user.getFullname());
					glAccountRecordService.save(glAccountRecord);
					
					 GlAccountRecord glAccountRecord1=new GlAccountRecord();  //释放记录
						glAccountRecord1.setCautionAccountId(glBankGuaranteemoney.getAccountId());
						glAccountRecord1.setCapitalType(4);
						glAccountRecord1.setGlAccountBankId(glBankGuaranteemoney.getGlAccountBankId());
						glAccountRecord1.setOprateDate(glBankGuaranteemoney.getFreezeDate());
						glAccountRecord1.setOprateMoney(glBankGuaranteemoney.getFreezeMoney());
						glAccountRecord1.setProjectId(glBankGuaranteemoney.getProjectId());
						glAccountRecord1.setHandlePerson(user.getFullname());
						glAccountRecordService.save(glAccountRecord1);
  	    	       saveguaranteemoneyAccount(glBankGuaranteemoney.getGlAccountBankId(),glBankGuaranteemoney.getAccountId(),projId);
	    	 
	     }else{
			     GlBankGuaranteemoney orgGlBankGuaranteemoney;
			     if(a.size() !=0){
			    	 orgGlBankGuaranteemoney=glBankGuaranteemoneyService.getbyprojId(projId).get(0);
			     }else{ 	 
				     orgGlBankGuaranteemoney=glBankGuaranteemoneyService.get(glBankGuaranteemoney.getBankGuaranteeId());
			     }
			     int same=0;
					Long accountId=orgGlBankGuaranteemoney.getAccountId();
					Long glAccountBankId=orgGlBankGuaranteemoney.getGlAccountBankId();
					if(!glBankGuaranteemoney.getAccountId().equals(orgGlBankGuaranteemoney.getAccountId())){//改变了保证金账户
						same=1;
					}
			     
				   if(null !=glBankGuaranteemoney.getAccountId()){
				   GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
				   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
				   }
				   try{
						BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
						orgGlBankGuaranteemoney.setUnfreezeMoney(orgGlBankGuaranteemoney.getFreezeMoney());
						glBankGuaranteemoneyService.save(orgGlBankGuaranteemoney);
						
						GlAccountRecord glAccountRecord=new GlAccountRecord();//冻结记录
						 List<GlAccountRecord> GlAccountRecords= glAccountRecordService.getbyprojectIdcapitalType(projId,3);
						 if(GlAccountRecords.size()!=0){
							 glAccountRecord=GlAccountRecords.get(0);
						 }
						glAccountRecord.setCautionAccountId(orgGlBankGuaranteemoney.getAccountId());
						glAccountRecord.setCapitalType(3);
						glAccountRecord.setGlAccountBankId(orgGlBankGuaranteemoney.getGlAccountBankId());
						glAccountRecord.setOprateDate(orgGlBankGuaranteemoney.getFreezeDate());
						glAccountRecord.setOprateMoney(orgGlBankGuaranteemoney.getFreezeMoney());
						glAccountRecord.setProjectId(orgGlBankGuaranteemoney.getProjectId());
						AppUser user=ContextUtil.getCurrentUser();
						glAccountRecord.setHandlePerson(user.getFullname());
						glAccountRecordService.save(glAccountRecord);
						
						GlAccountRecord glAccountRecord1=new GlAccountRecord();//释放记录
						 List<GlAccountRecord> GlAccountRecord1s= glAccountRecordService.getbyprojectIdcapitalType(projId,4);
						 if(GlAccountRecord1s.size()!=0){
							 glAccountRecord1=GlAccountRecord1s.get(0);
						 }
							glAccountRecord1.setCautionAccountId(orgGlBankGuaranteemoney.getAccountId());
							glAccountRecord1.setCapitalType(4);
							glAccountRecord1.setGlAccountBankId(orgGlBankGuaranteemoney.getGlAccountBankId());
							glAccountRecord1.setOprateDate(orgGlBankGuaranteemoney.getFreezeDate());
							glAccountRecord1.setOprateMoney(orgGlBankGuaranteemoney.getFreezeMoney());
							glAccountRecord1.setProjectId(orgGlBankGuaranteemoney.getProjectId());
							glAccountRecord1.setHandlePerson(user.getFullname());
							glAccountRecordService.save(glAccountRecord1);
						if(same==1){
								saveguaranteemoneyAccount(glAccountBankId,accountId,orgGlBankGuaranteemoney.getProjectId());
						}
	  	    	       saveguaranteemoneyAccount(orgGlBankGuaranteemoney.getGlAccountBankId(),orgGlBankGuaranteemoney.getAccountId(),orgGlBankGuaranteemoney.getProjectId());
						
						
					}catch(Exception ex){
						logger.error(ex.getMessage());
					}
	     }
		setJsonString("{success:true}");
		
		return SUCCESS;
		
	}
public String saveRelease(){
        
		
		
		List<GlBankGuaranteemoney> list=glBankGuaranteemoneyService.getbyprojId(projId);
		String taskId=this.getRequest().getParameter("task_id");
       	String comments=this.getRequest().getParameter("comments");
          if(list.size()!=0){
        	  GlBankGuaranteemoney orgGlBankGuaranteemoney=list.get(0);
      //  	  glBankGuaranteemoney.setIsRelease(isRelease);
        	  try {
				BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
			} catch (Exception e) {
		
			}
        	  glBankGuaranteemoneyService.save(orgGlBankGuaranteemoney);
        	  
          }
          if(null!=taskId && !"".equals(taskId) && null!=comments && !"".equals(comments.trim()))
	       	{
	       		this.creditProjectService.saveComments(taskId, comments);
	       	}
		return SUCCESS;
		
	}

	public String saveFinancl(){
		if(isRelease==0){   //财务登记节点
				   //冻结银行保证金
			List<GlBankGuaranteemoney> a=new ArrayList<GlBankGuaranteemoney>();
			a=glBankGuaranteemoneyService.getbyprojId(projId);
			     if(glBankGuaranteemoney.getBankGuaranteeId()==null &&a.size()==0){
		    			glBankGuaranteemoney.setProjectId(projId);
		    			glBankGuaranteemoney.setBusinessType("Guarantee");
		    			glBankGuaranteemoney.setOperationType("CompanyBusiness");
		    		   glBankGuaranteemoney.setIsRelease(isRelease);
		    			if(glBankGuaranteemoney.getAccountId() !=null){
		    			GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
					   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
		    			   }
		     	    	glBankGuaranteemoneyService.save(glBankGuaranteemoney);
			    	 
			     }else{
			    	 GlBankGuaranteemoney orgGlBankGuaranteemoney;
				     if(a.size() !=0){
				    	 orgGlBankGuaranteemoney=glBankGuaranteemoneyService.getbyprojId(projId).get(0);
				     }else{ 	 
					     orgGlBankGuaranteemoney=glBankGuaranteemoneyService.get(glBankGuaranteemoney.getBankGuaranteeId());
				     }
					   glBankGuaranteemoney.setIsRelease(isRelease);
					   if(null !=glBankGuaranteemoney.getAccountId()){
					   GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
					   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
					   }
					   try{
							BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
							glBankGuaranteemoneyService.save(orgGlBankGuaranteemoney);
						}catch(Exception ex){
							logger.error(ex.getMessage());
						}
			     }

						//冻结保费
						List<SlFundIntent> list2= slFundIntentService.getByProjectId1(projId, "Guarantee");
						for(SlFundIntent g:list2){
							if(g.getFundType().equals("GuaranteeToCharge")){
								slFundIntent2=g;
							}
						}
						if(null !=slFundIntent2){
						slFundIntent2.setIsChargeCertificate(isCharge);
						slFundIntentService.save(slFundIntent2);
						}
						//款项和费用
						String slActualToChargeData=this.getRequest().getParameter("slActualToChargeJsonData");
						String fundIntentJsonData=this.getRequest().getParameter("fundIntentJsonData");
						slActualToChargeService.savejson(slActualToChargeData, projId, "Guarantee", Short.parseShort("0"),null);
						slFundIntentService.savejson1(fundIntentJsonData,projId, "Guarantee", Short.parseShort("0"),Short.valueOf("0"),null);
					
					}else{
						   //退还保证金节点
						 GlBankGuaranteemoney glBankGuaranteemoney=new GlBankGuaranteemoney();
						 glBankGuaranteemoney=  glBankGuaranteemoneyService.getbyprojId(projId).get(0);
						 glBankGuaranteemoney.setIsRelease(isRelease);
						 glBankGuaranteemoneyService.save(glBankGuaranteemoney);
				
					}
			   
	   //冻结客户保证金
   	    List<SlFundIntent> list1= slFundIntentService.getByProjectId1(projId, "Guarantee");
		for(SlFundIntent g:list1){
			if(g.getFundType().equals("ToCustomGuarantMoney")){
				slFundIntent1=g;
			}
		}
		if(null !=slFundIntent1){
		slFundIntent1.setIsChargeCertificate(isCharge);
		slFundIntentService.save(slFundIntent1);
		}
		String taskId=this.getRequest().getParameter("task_id");
       	String comments=this.getRequest().getParameter("comments");
       	if(null!=taskId && !"".equals(taskId) && null!=comments && !"".equals(comments.trim()))
       	{
       		this.creditProjectService.saveComments(taskId, comments);
       	}
		return SUCCESS;
	}
   public String saveFinancl1(){ //获取担保责任函保存
		
		GlBankGuaranteemoney orgGlBankGuaranteemoney=glBankGuaranteemoneyService.get(glBankGuaranteemoney.getBankGuaranteeId());
		try{
			BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
			orgGlBankGuaranteemoney.setUnfreezeMoney(orgGlBankGuaranteemoney.getFreezeMoney());
			glBankGuaranteemoneyService.save(orgGlBankGuaranteemoney);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
	   return SUCCESS;
	}
	public String saveFinanclZm(){
		if(isRelease==0){ //表示财务确认费用收取节点
		  //冻结客户保证金
			  List<SlFundIntent> list1= slFundIntentService.getByProjectId1(projId, "Guarantee");
				for(SlFundIntent g:list1){
					if(g.getFundType().equals("ToCustomGuarantMoney")){
						slFundIntent1=g;
					}
				}
				if(null !=slFundIntent1){
					slFundIntent1.setIsChargeCertificate(isCharge);
					slFundIntentService.save(slFundIntent1);
					}
				 
				 String slActualToChargeData=this.getRequest().getParameter("slActualToChargeJsonData");
					String fundIntentJsonData=this.getRequest().getParameter("fundIntentJsonData");
					slActualToChargeService.savejson(slActualToChargeData, projId, "Guarantee", Short.parseShort("0"),null);
					slFundIntentService.savejson1(fundIntentJsonData,projId, "Guarantee", Short.parseShort("0"),Short.valueOf("0"),null);
		}
		if(isRelease==1){ //表示向银行缴纳保证金
			List<GlBankGuaranteemoney> a=new ArrayList<GlBankGuaranteemoney>();
			a=glBankGuaranteemoneyService.getbyprojId(projId);
			 if(glBankGuaranteemoney.getBankGuaranteeId()==null &&a.size()==0){
	    			glBankGuaranteemoney.setProjectId(projId);
	    			glBankGuaranteemoney.setBusinessType("Guarantee");
	    			glBankGuaranteemoney.setOperationType("CompanyBusiness");
	    			   glBankGuaranteemoney.setIsRelease(isCharge);
	    			   if(null !=glBankGuaranteemoney.getAccountId()){
	    			GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
				   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
	    			   }
	     	    	glBankGuaranteemoneyService.save(glBankGuaranteemoney);
		    	 
		     }else{
		    	 GlBankGuaranteemoney orgGlBankGuaranteemoney;
		     if(a.size() !=0){
		    	 orgGlBankGuaranteemoney=glBankGuaranteemoneyService.getbyprojId(projId).get(0);
		     }else{ 	 
			     orgGlBankGuaranteemoney=glBankGuaranteemoneyService.get(glBankGuaranteemoney.getBankGuaranteeId());
		     }
			   glBankGuaranteemoney.setIsRelease(isCharge);
			   if(null !=glBankGuaranteemoney.getAccountId()){
			   GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
			   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
			   }
			   try{
					BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
					glBankGuaranteemoneyService.save(orgGlBankGuaranteemoney);
				}catch(Exception ex){
					logger.error(ex.getMessage());
				}
		     }
			 
			 String slActualToChargeData=this.getRequest().getParameter("slActualToChargeJsonData");
				String fundIntentJsonData=this.getRequest().getParameter("fundIntentJsonData");
				slActualToChargeService.savejson(slActualToChargeData, projId, "Guarantee", Short.parseShort("0"),null);
				slFundIntentService.savejson1(fundIntentJsonData,projId, "Guarantee", Short.parseShort("0"),Short.valueOf("0"),null);
			}
		if(isRelease==2){ //保费收取节点
			List<SlFundIntent> list2= slFundIntentService.getByProjectId1(projId, "Guarantee");
			for(SlFundIntent g:list2){
				if(g.getFundType().equals("GuaranteeToCharge")){
					slFundIntent2=g;
				}
			   }
			if(null !=slFundIntent2){
				slFundIntent2.setIsChargeCertificate(isCharge);
				slFundIntentService.save(slFundIntent2);
				}
			
			 String slActualToChargeData=this.getRequest().getParameter("slActualToChargeJsonData");
				String fundIntentJsonData=this.getRequest().getParameter("fundIntentJsonData");
				slActualToChargeService.savejson(slActualToChargeData, projId, "Guarantee", Short.parseShort("0"),null);
				slFundIntentService.savejson1(fundIntentJsonData,projId, "Guarantee", Short.parseShort("0"),Short.valueOf("0"),null);
			}
		if(isRelease==3){ //退还保证金节点
			List<SlFundIntent> list3= slFundIntentService.getByProjectId1(projId, "Guarantee");
			for(SlFundIntent g:list3){
				if(g.getFundType().equals("GuaranteeToCharge")){
					slFundIntent2=g;
				}
			   }
			if(null !=slFundIntent2){
				slFundIntent1.setIsChargeCertificate(isCharge);
				slFundIntentService.save(slFundIntent1);
				}
			
			 List<SlFundIntent> list4= slFundIntentService.getByProjectId1(projId, "Guarantee");
				for(SlFundIntent g:list4){
					if(g.getFundType().equals("ToCustomGuarantMoney")){
						slFundIntent1=g;
					}
				}
				if(null !=slFundIntent1){
					slFundIntent1.setIsChargeCertificate(isCharge);
					slFundIntentService.save(slFundIntent1);
					}
			}
		return SUCCESS;
	}
	
	public void saveguaranteemoneyAccount(Long glAccountBankId,Long cautionAccountId,Long projectId){
		GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(cautionAccountId);
		List<GlAccountRecord> listRecord =glAccountRecordService.getallbycautionAccountId(cautionAccountId,0,99999999);
		BigDecimal incomemoney=new BigDecimal(0);
		BigDecimal paymoney =new BigDecimal(0);
		BigDecimal frozenMoney=new BigDecimal(0);
		BigDecimal unFrozenMoney =new BigDecimal(0);
		for(GlAccountRecord l:listRecord){
			if(l.getCapitalType()==1){ //存入
				incomemoney=incomemoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==2){ //支出
				paymoney=paymoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==3){ //冻结
				frozenMoney=frozenMoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==4){ //解冻
				unFrozenMoney=unFrozenMoney.add(l.getOprateMoney());
			}
		}
		BigDecimal sum =glAccountBankCautionmoney.getRawauthorizationMoney().add(incomemoney).subtract(paymoney);
		glAccountBankCautionmoney.setAuthorizationMoney(sum);
		BigDecimal sumsurplusMoney=glAccountBankCautionmoney.getRawsurplusMoney().add(incomemoney).subtract(paymoney).add(unFrozenMoney).subtract(frozenMoney);
		glAccountBankCautionmoney.setSurplusMoney(sumsurplusMoney);
		glAccountBankCautionmoneyService.save(glAccountBankCautionmoney);
		
		GlAccountBank glAccountBank=glAccountBankService.get(glAccountBankId);
		List<GlAccountBankCautionmoney> list=glAccountBankCautionmoneyService.getbyparentId(glAccountBankId);
		BigDecimal authorizationMoney=new BigDecimal(0);
		BigDecimal surplusMoney =new BigDecimal(0);
		for(GlAccountBankCautionmoney l:list){
			authorizationMoney=authorizationMoney.add(l.getAuthorizationMoney());
			surplusMoney=surplusMoney.add(l.getSurplusMoney());
		}
		glAccountBank.setAuthorizationMoney(authorizationMoney);
		glAccountBank.setSurplusMoney(surplusMoney);
		glAccountBankService.save(glAccountBank);
	}	
		
}
