package com.zhiwei.credit.action.creditFlow.guarantee.zmNormalFlow;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountRecordDao;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GLSuperviseRecord;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlSuperviseRecordService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.zmNormalFlow.GLGuaranteeloanZmNormalFlowProjectService;
import com.zhiwei.credit.service.flow.ProcessFormService;

public class GLGuaranteeloanZmNormalFlowProjectAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Resource
	private GLGuaranteeloanZmNormalFlowProjectService guaranteeloanZmNormalFlowProjectService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private GlAccountBankCautionmoneyService glAccountBankCautionmoneyService;
	
	private GLSuperviseRecord glSuperviseRecord;
	
	private GLGuaranteeloanProject gLGuaranteeloanProject;
	
	@Resource
	private  GlSuperviseRecordService glSuperviseRecordService;
	@Resource
	private ProcessFormService processFormService;
	
	private CustomerBankRelationPerson customerBankRelationPerson;
	
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private GlAccountRecordDao glAccountRecordDao;
	@Resource
	private GlAccountBankCautionmoneyDao glAccountBankCautionmoneyDao;
	@Resource
	private GlBankGuaranteemoneyService glBankGuaranteemoneyService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	private Person person;
	
	private Enterprise enterprise;
	private GlBankGuaranteemoney glBankGuaranteemoney;
	
	private String oppositeType;
	private Long oppositeId;
	
	
	public String getOppositeType() {
		return oppositeType;
	}

	public void setOppositeType(String oppositeType) {
		this.oppositeType = oppositeType;
	}

	public Long getOppositeId() {
		return oppositeId;
	}

	public void setOppositeId(Long oppositeId) {
		this.oppositeId = oppositeId;
	}

	public GlBankGuaranteemoney getGlBankGuaranteemoney() {
		return glBankGuaranteemoney;
	}

	public void setGlBankGuaranteemoney(GlBankGuaranteemoney glBankGuaranteemoney) {
		this.glBankGuaranteemoney = glBankGuaranteemoney;
	}
	private String comments;
	
	public GLGuaranteeloanProject getgLGuaranteeloanProject() {
		return gLGuaranteeloanProject;
	}

	public void setgLGuaranteeloanProject(
			GLGuaranteeloanProject gLGuaranteeloanProject) {
		this.gLGuaranteeloanProject = gLGuaranteeloanProject;
	}

	public CustomerBankRelationPerson getCustomerBankRelationPerson() {
		return customerBankRelationPerson;
	}

	public void setCustomerBankRelationPerson(
			CustomerBankRelationPerson customerBankRelationPerson) {
		this.customerBankRelationPerson = customerBankRelationPerson;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}
	
	public GLSuperviseRecord getGlSuperviseRecord() {
		return glSuperviseRecord;
	}

	public void setGlSuperviseRecord(GLSuperviseRecord glSuperviseRecord) {
		this.glSuperviseRecord = glSuperviseRecord;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * 业务经理尽职调查-保存（更新）信息    企业贷款类型
	 * @return
	 */
	public  String update()
	{
		StringBuffer sb = new StringBuffer("{success:true,result:");
		try{
    	        String shareequity=this.getRequest().getParameter("gudongInfo");
    	        String fundIntentJsonData=this.getRequest().getParameter("fundIntentJsonData");
    	        String slActualToChargeJsonData=this.getRequest().getParameter("slActualToChargeJsonData");
    	        String isDeleteAllFundIntent=this.getRequest().getParameter("isDeleteAllFundIntent");
    			List<EnterpriseShareequity> listES=new ArrayList<EnterpriseShareequity>();
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
    			List<SlFundIntent>  slFundIntents=new ArrayList<SlFundIntent>();
    		   	slFundIntents=savejsonintent(fundIntentJsonData,gLGuaranteeloanProject,Short.parseShort("1"));
    		   	List<SlActualToCharge> slActualToCharges=new ArrayList<SlActualToCharge>();
    		   	slActualToCharges=savejsoncharge(slActualToChargeJsonData,gLGuaranteeloanProject,Short.parseShort("0"));
    	    	this.guaranteeloanZmNormalFlowProjectService.updateInfo(gLGuaranteeloanProject, person, enterprise, listES,customerBankRelationPerson,slFundIntents,slActualToCharges,isDeleteAllFundIntent);
    	    	String taskId=this.getRequest().getParameter("task_id");
		       	String comments=this.getRequest().getParameter("comments");
		        ProcessForm formp=this.processFormService.getByTaskId(taskId);
		        if(null!=formp){
		        	formp.setComments(comments);
		        	this.processFormService.merge(formp);
		        }
		}
		catch (Exception e) {
			logger.error("担保项目-中铭流程   项目尽调材料上报service-保存（更新）信息   出错:"+e.getMessage());
			e.printStackTrace();
		}
		Integer bankId=0;
		if(null!=customerBankRelationPerson.getId()){
			bankId=customerBankRelationPerson.getId();
		}
		sb.append("[{\"bankId\":\""+bankId+"\"}]");
		sb.append("}");
		setJsonString(sb.toString());
    	return SUCCESS;
	}
    public  String saveComments(){
    	
    	String taskId=this.getRequest().getParameter("task_id");
       	String comments=this.getRequest().getParameter("comments");
       	if(null!=taskId && !"".equals(taskId) && null!=comments && !"".equals(comments.trim()))
       	{
       		this.creditProjectService.saveComments(taskId, comments);
       	}
    	return SUCCESS;
    }
	public  String chargeaffirm()
	{
		try{
    	        String fundIntentJsonData=this.getRequest().getParameter("fundIntentJsonData");
    	        String slActualToChargeJsonData=this.getRequest().getParameter("slActualToChargeJsonData");
    			List<SlFundIntent>  slFundIntents=new ArrayList<SlFundIntent>();
    		   	slFundIntents=savejsonintent(fundIntentJsonData,gLGuaranteeloanProject,Short.parseShort("1"));
    		   	List<SlActualToCharge> slActualToCharges=new ArrayList<SlActualToCharge>();
    		   	slActualToCharges=savejsoncharge(slActualToChargeJsonData,gLGuaranteeloanProject,Short.parseShort("0"));
    	    	this.guaranteeloanZmNormalFlowProjectService.updatechargeaffirm(gLGuaranteeloanProject,slFundIntents,slActualToCharges);
    	    	
    	    	String taskId=this.getRequest().getParameter("task_id");
    	       	String comments=this.getRequest().getParameter("comments");
    	       	if(null!=taskId && !"".equals(taskId) && null!=comments && !"".equals(comments.trim()))
    	       	{
    	       		this.creditProjectService.saveComments(taskId, comments);
    	       	}
		}
		catch (Exception e) {
			logger.error("担保项目-中铭流程   项目尽调材料上报service-保存（更新）信息   出错:"+e.getMessage());
			e.printStackTrace();
		}
		
		  StringBuffer sb = new StringBuffer("{success:true}");
		   setJsonString(sb.toString());
    	return SUCCESS;
	}
	public  String financechargeaffirm()
	{
		try{    
    	        
    	    	
    		  //表示向银行缴纳保证金
   			 List<GlBankGuaranteemoney> a=new ArrayList<GlBankGuaranteemoney>();
   				a=glBankGuaranteemoneyService.getbyprojId(gLGuaranteeloanProject.getProjectId());
   				 if(glBankGuaranteemoney.getBankGuaranteeId()==null &&a.size()==0){
   		    			glBankGuaranteemoney.setProjectId(gLGuaranteeloanProject.getProjectId());
   		    			glBankGuaranteemoney.setBusinessType("Guarantee");
   		    			glBankGuaranteemoney.setOperationType("CompanyBusiness");
   		    		   glBankGuaranteemoney.setIsRelease(Short.valueOf("0"));
   		    		 if(null !=glBankGuaranteemoney.getAccountId()){
   		    			GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
   					   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
   		    			   }
   		     	    	glBankGuaranteemoneyService.save(glBankGuaranteemoney);
   			    	 
   			     }else{
   			    	 GlBankGuaranteemoney orgGlBankGuaranteemoney;
   				     if(a.size() !=0){
   				    	 orgGlBankGuaranteemoney=glBankGuaranteemoneyService.getbyprojId(gLGuaranteeloanProject.getProjectId()).get(0);
   				     }else{ 	 
   					     orgGlBankGuaranteemoney=glBankGuaranteemoneyService.get(glBankGuaranteemoney.getBankGuaranteeId());
   				     }
   			         glBankGuaranteemoney.setIsRelease(Short.valueOf("0"));
   					   if(null !=glBankGuaranteemoney.getAccountId()){
   					   GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
   					   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
   					   }
   				   BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
   					glBankGuaranteemoneyService.save(orgGlBankGuaranteemoney);
   				
   				 
   				
   					}
    		   	
   				String slActualToChargeJsonData=this.getRequest().getParameter("slActualToChargeJsonData");
    	        slActualToChargeService.savejson(slActualToChargeJsonData, gLGuaranteeloanProject.getProjectId(), "Guarantee", Short.parseShort("0"),null);
    	    	String taskId=this.getRequest().getParameter("task_id");
    	       	String comments=this.getRequest().getParameter("comments");
    	       	if(null!=taskId && !"".equals(taskId) && null!=comments && !"".equals(comments.trim()))
    	       	{
    	       		this.creditProjectService.saveComments(taskId, comments);
    	       	}
		}
		catch (Exception e) {
			logger.error("担保项目-中铭流程   项目尽调材料上报service-保存（更新）信息   出错:"+e.getMessage());
			e.printStackTrace();
		}
		
		  StringBuffer sb = new StringBuffer("{success:true}");
		   setJsonString(sb.toString());
    	return SUCCESS;
	}
	   public  List<SlFundIntent>  savejsonintent(String fundIntentJsonData,GLGuaranteeloanProject slSmallloanProject,Short flag){
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
									SlFundIntent1.setBusinessType("Guarantee");
									slFundIntents.add(SlFundIntent1);
						} else {
							 BigDecimal lin = new BigDecimal(0.00);
							   if(SlFundIntent1.getIncomeMoney().compareTo(lin)==0){
						        	SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						        }else{
						        	SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						        	
						        }
							SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
							if(slFundIntent2.getAfterMoney().compareTo(new BigDecimal(0))==0){
							BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
							slFundIntent2.setBusinessType("Guarantee");
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
	   public List<SlActualToCharge>  savejsoncharge(String slActualToChargeJsonData,GLGuaranteeloanProject slSmallloanProject,Short flag){
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
										if(p.getName().equals(typename) && p.getBusinessType().equals("Guarantee")){
											k++;
										}
									}
									
									if(k==0){
										slPlansToCharge.setName(typename);
										slPlansToCharge.setIsType(1);
										slPlansToCharge.setIsValid(0);
										slPlansToCharge.setBusinessType("Guarantee");
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
						slActualToCharge.setBusinessType("Guarantee");
							slActualToCharge.setIsCheck(flag);
						slActualToCharges.add(slActualToCharge);
						} else {
							
							SlActualToCharge slActualToCharge1 = slActualToChargeService.get(slActualToCharge.getActualChargeId());
								if(slActualToCharge1.getAfterMoney().compareTo(new BigDecimal(0))==0){
								BeanUtil.copyNotNullProperties(slActualToCharge1,slActualToCharge);
								if(slActualToCharge1.getIncomeMoney().equals(new BigDecimal(0.00))){
									slActualToCharge1.setNotMoney(slActualToCharge.getPayMoney());
								}else{
									slActualToCharge1.setNotMoney(slActualToCharge.getIncomeMoney());
								}
								slActualToCharge1.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
								slActualToCharge1.setBusinessType("Guarantee");
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
	   /**
	    * 贷中监管保存数据
	    * @return
	    */
	   public String updateSupervise(){
//		   List<GlProcreditSupervision>   glProcreditSupervisions=new ArrayList<GlProcreditSupervision>();
		   String  glProcreditSupervisionStr=this.getRequest().getParameter("slSuperviseDatas");
		   String  fundIntentJsonData=this.getRequest().getParameter("fundIntentJsonData");
		   String  slActualToChargeJsonData=this.getRequest().getParameter("slActualToChargeJsonData");
		   
			if(null != glProcreditSupervisionStr && !"".equals(glProcreditSupervisionStr)) {

				String[] glProcreditSupervisionArr = glProcreditSupervisionStr.split("@");
				for(int i=0; i<glProcreditSupervisionArr.length; i++) {
					String str = glProcreditSupervisionArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try{
//						GlProcreditSupervision glSupervision = (GlProcreditSupervision)JSONMapper.toJava(parser.nextValue(),GlProcreditSupervision.class);
//						glProcreditSupervisions.add(glSupervision);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			//保存款项计划
	    	List<SlFundIntent>  slFundIntents=new ArrayList<SlFundIntent>();
	    	slFundIntents=savejsonintent(fundIntentJsonData,gLGuaranteeloanProject,Short.parseShort("0"));
	    	
	    	//保存杂项费用
	    	List<SlActualToCharge> slActualToCharges=new ArrayList<SlActualToCharge>();
	    	slActualToCharges=savejsoncharge(slActualToChargeJsonData,gLGuaranteeloanProject,Short.parseShort("0"));
		   Long  projectId=Long.valueOf(this.getRequest().getParameter("projectId"));
		   this.guaranteeloanZmNormalFlowProjectService.updateSupervise(projectId, slFundIntents,slActualToCharges);
		   StringBuffer sb = new StringBuffer("{success:true}");
		   setJsonString(sb.toString());
		   return SUCCESS;
	   }
	   public String askfor(){
		   Long projectId=Long.valueOf(this.getRequest().getParameter("projectId"));
		   String  slActualToChargeJsonData=this.getRequest().getParameter("money_plan");
			String  fundIntentJsonData=this.getRequest().getParameter("intent_plan");
			String categoryIds = this.getRequest().getParameter("categoryIds");
			//Short flag = Short.valueOf(this.getRequest().getParameter("flag"));

			gLGuaranteeloanProject= this.guaranteeloanZmNormalFlowProjectService.get(projectId);
		   List<SlFundIntent>  slFundIntents=new ArrayList<SlFundIntent>();
		   	slFundIntents=savejsonintent(fundIntentJsonData,gLGuaranteeloanProject,Short.parseShort("0"));
		   	List<SlActualToCharge> slActualToCharges=new ArrayList<SlActualToCharge>();
		   	slActualToCharges=savejsoncharge(slActualToChargeJsonData,gLGuaranteeloanProject,Short.parseShort("0"));
            
		   this.guaranteeloanZmNormalFlowProjectService.askForProject(projectId, slActualToCharges, slFundIntents, glSuperviseRecord, categoryIds);
		   StringBuffer sb = new StringBuffer("{success:true}");
		   setJsonString(sb.toString());
		   return SUCCESS;
	   }
	   public String listSuperviseRecord(){
		     Long projectId=Long.valueOf(this.getRequest().getParameter("projectId"));
		    Type type=new TypeToken<List<GLSuperviseRecord>>(){}.getType();
			List<GLSuperviseRecord> list = new ArrayList<GLSuperviseRecord>();
			list= glSuperviseRecordService.getListByProjectId(projectId);
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
			Gson gson=new Gson();
			buff.append(gson.toJson(list, type));
			buff.append("}");
			jsonString=buff.toString();
		    return SUCCESS; 
	   }
	   public String  deleteSuperviseRecord(){
		   
			String[]ids=getRequest().getParameterValues("ids");
			if(ids!=null){
				for(String id:ids){
					glSuperviseRecordService.remove(new Long(id));
				}
			}
		   StringBuffer sb = new StringBuffer("{success:true}");
		   setJsonString(sb.toString());
		   return SUCCESS;
		   
	   }
	   public String joinBlackList(){
		   String msg="";
			try {
				String blackReason=this.getRequest().getParameter("blackReason");
				if(oppositeType.equals("company_customer")){
					Enterprise ep=enterpriseService.getById(oppositeId.intValue());
					ep.setIsBlack(true);
					ep.setBlackReason(blackReason);
					enterpriseService.merge(ep);
				}else{
					Person person=personService.getById(oppositeId.intValue());
					person.setIsBlack(true);
					person.setBlackReason(blackReason);
					personService.merge(person);
				}
				msg="{success:true}";
			} catch (Exception e) {
				msg="{success:false}";
				e.printStackTrace();
			}
			jsonString=msg;
		   return SUCCESS;
	   }
	  
}
