package com.zhiwei.credit.action.htmobile;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Node;


import com.credit.proj.entity.ProcreditMortgageProduct;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.util.XmlUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.RatingTemplateDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.BpMoneyBorrowDemandDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingTemplate;
import com.zhiwei.credit.model.creditFlow.customer.person.BpMoneyBorrowDemand;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.model.system.DictionaryIndependent;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.model.system.GlobalTypeIndependent;
import com.zhiwei.credit.model.system.product.BpProductParameter;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.common.GlobalSupervisemanageService;
import com.zhiwei.credit.service.creditFlow.customer.person.BpMoneyBorrowDemandService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.TaskSignDataService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.service.system.GlobalTypeIndependentService;
import com.zhiwei.credit.service.system.GlobalTypeService;
import com.zhiwei.credit.service.system.RelativeUserService;
import com.zhiwei.credit.service.system.product.BpProductParameterService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class VmInfoAction extends BaseAction {
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService; // 小额贷款
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private GlobalTypeIndependentService globalTypeIndependentService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource
	protected AreaDicService areaDicService ;
	@Resource
	private BpMoneyBorrowDemandService bpMoneyBorrowDemandService;
	@Resource
	private BpProductParameterService bpProductParameterService;
	@Resource
	private RatingTemplateDao ratingTemplateDao;
	@Resource
	private CsBankService csBankService;
	
	@Resource
	private ProcessRunService processRunService;
	
//	@Resource
//	private ProcreditMortgageProductService procreditMortgageProductService;
	

	@Resource
	private TaskSignDataService taskSignDataService;
	
	@Resource
	private BpMoneyBorrowDemandDao bpMoneyBorrowDemandDao;
	
	@Resource
	private RelativeUserService relativeUserService;
	@Resource
	private PersonDao personDao;
	private Person person;
	private Long productId;
	
	private Integer personId;
	private SlSmallloanProject slSmallloanProject;
	private BpMoneyBorrowDemand bpMoneyBorrowDemand;
	@Resource
	private GlobalSupervisemanageService globalSupervisemanageService;
	private  GlobalSupervisemanage globalSupervisemanage;
	private String userId;
	private ProcreditMortgageProduct procreditMortgageProduct;
	
	
	public ProcreditMortgageProduct getProcreditMortgageProduct() {
		return procreditMortgageProduct;
	}
	public void setProcreditMortgageProduct(
			ProcreditMortgageProduct procreditMortgageProduct) {
		this.procreditMortgageProduct = procreditMortgageProduct;
	}
	public SlSmallloanProject getSlSmallloanProject() {
		return slSmallloanProject;
	}
	public void setSlSmallloanProject(SlSmallloanProject slSmallloanProject) {
		this.slSmallloanProject = slSmallloanProject;
	}
	public BpMoneyBorrowDemand getBpMoneyBorrowDemand() {
		return bpMoneyBorrowDemand;
	}
	public void setBpMoneyBorrowDemand(BpMoneyBorrowDemand bpMoneyBorrowDemand) {
		this.bpMoneyBorrowDemand = bpMoneyBorrowDemand;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserId() {
		return userId;
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
	public GlobalSupervisemanage getGlobalSupervisemanage() {
		return globalSupervisemanage;
	}
	public void setGlobalSupervisemanage(GlobalSupervisemanage globalSupervisemanage) {
		this.globalSupervisemanage = globalSupervisemanage;
	}
	public String getCreditLoanProjectInfo() {
		String projectId = this.getRequest().getParameter("projectId"); // 项目ID
		
		SlSmallloanProject project = this.slSmallloanProjectService.get(Long.valueOf(projectId));
		if(userId!=null&&!userId.equals("")){
		Set<AppUser> set =relativeUserService.getUpUser(Long.valueOf(userId));
			if(null!=set||set.size()>0){
				for (Iterator it = set.iterator(); it.hasNext();){
					AppUser user = (AppUser) it.next();
					project.setTeamManagerName(user.getFullname());
					project.setTeamManagerId(user.getUserId().toString());
					break;
				}
			}
		}
/*		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("finalDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"finalDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();*/
		StringBuffer sb = new StringBuffer("{\"success\":true,\"data\":");
		/*Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		
		sb.append(gson.toJson(project));
		sb.append("}");
		setJsonString(sb.toString());*/
		
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate");
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
			"endDate","startInterestDate","startDate" ,"intentDate" });
		sb.append(json.serialize(project));
		sb.append("}");
		jsonString = sb.toString();
		System.out.println("jsonString==="+jsonString);
		return SUCCESS;
	}
	//是否是线下审贷会，线上审贷会
	public String getIsOnline() {
		Long projectId = Long.valueOf(this.getRequest().getParameter("projectId")); // 项目ID
		String businessType = this.getRequest().getParameter("businessType"); // 
		SlSmallloanProject project = this.slSmallloanProjectService.get(projectId);
		List<ProcessRun> runList = processRunService.getByProcessNameProjectId(
				projectId,businessType, project.getFlowType());
		if (runList != null && runList.size() != 0) {
			List<TaskSignData> tsdList = taskSignDataService.getByRunId(runList
					.get(0).getRunId());
			if (tsdList.size() > 0) {
				StringBuffer sb = new StringBuffer("{\"success\":true,\"isOnline\":true}");
				jsonString = sb.toString();
			} else {
				StringBuffer sb = new StringBuffer("{\"success\":true,\"isOnline\":false}");
				jsonString = sb.toString();
			}
		}
	
		return SUCCESS;
	}
	public String getPersonId() {
		String projectId = this.getRequest().getParameter("projectId"); // 项目ID
		String businessType = this.getRequest().getParameter("businessType"); // 
		SlSmallloanProject project = this.slSmallloanProjectService.get(Long.valueOf(projectId));
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{\"success\":true,\"oppositeID\":");
		sb.append(project.getOppositeID());
		sb.append("}");
		setJsonString(sb.toString());
           System.out.println("sb==="+sb);
		return SUCCESS;
	}
	public String getFundInfo() {
		String projectId = this.getRequest().getParameter("projectId"); // 项目ID
		String businessType = this.getRequest().getParameter("businessType"); // 
		Short isOwn = Short.valueOf(this.getRequest().getParameter("projectId")); // 项目ID
		BpFundProject bpFundProject=null;
		if(isOwn.equals(Short.valueOf("0"))){
			
			// 自有资金
			bpFundProject = bpFundProjectService.getByProjectId(Long
					.valueOf(projectId), isOwn);
		}else{
			// 平台资金
			bpFundProject = bpFundProjectService.getByProjectId(Long
					.valueOf(projectId), isOwn);
		}
		

		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{\"success\":true,\"data\":");
		sb.append(gson.toJson(bpFundProject));
		sb.append("}");
		setJsonString(sb.toString());
           System.out.println("sb==="+sb);
		return SUCCESS;
	}
	public String getPlBidPlanInfo() {
		String bidPlanId = this.getRequest().getParameter("bidPlanId"); // 项目ID
		PlBidPlan plBidPlan = plBidPlanService.get(Long.parseLong(bidPlanId));
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		StringBuffer buff = new StringBuffer("{\"success\":true,\"data\":");
		JSONSerializer json = JsonUtil.getJSONSerializer("startIntentDate",
				"endIntentDate");
		buff.append(json.serialize(plBidPlan));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String loadIndepItems(){
	
		String nodeKey =this.getRequest().getParameter("nodeKey");
		GlobalTypeIndependent globalTypeIndependent=globalTypeIndependentService.getByNodeKey(nodeKey);
		StringBuffer buff = new StringBuffer("{\"success\":true,\"totalCounts\":");
		   if(null!=globalTypeIndependent ){
			   List<DictionaryIndependent> list=dictionaryIndependentService.getListByProTypeId(globalTypeIndependent.getProTypeId());
			   buff.append(list.size()).append(",\"result\":[{\"text\":\"\",\"value\":\"\"},");
			//   buff.append(list.size()).append(",\"result\":[");
				for (DictionaryIndependent dic : list) {
					buff.append("{\"text\":\"").append(dic.getItemValue()).append("\",\"value\":\"")
							.append(dic.getDicKey()).append("\"},");

				}
				if (list.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]}");
				setJsonString(buff.toString());
		   }
	
		return SUCCESS;
	}
	//DictionaryAction
	 public String loadItemByNodeKey(){
			String nodeKey =this.getRequest().getParameter("nodeKey");
		   List<GlobalType> list=globalTypeService.getByNodeKey(nodeKey);
		   StringBuffer buff = new StringBuffer("{\"success\":true,\"totalCounts\":");
		   if(null!=list && list.size()>0){
			   List<Dictionary> list1=dictionaryService.getByProTypeId(list.get(0).getProTypeId());
			   buff.append(list.size()).append(",\"result\":[{\"text\":\"\",\"value\":\"\"},");
			//   buff.append(list.size()).append(",\"result\":[");
				for (Dictionary dic : list1) {
					buff.append("{\"text\":\"").append(dic.getItemValue()).append("\",\"value\":\"")
					.append(dic.getDicId()).append("\"},");

				}
				if (list.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]}");
				setJsonString(buff.toString());
				System.out.println("buff.toString()=="+buff.toString());
		   }
		   return SUCCESS;
	   }
	 public String loadItem(){
			String itemName =this.getRequest().getParameter("itemName");
			String itemnames;
			try {
				itemnames = URLDecoder.decode(itemName,"UTF-8");
			
			List<Dictionary> list = dictionaryService.getByItemName(itemnames);
		   StringBuffer buff = new StringBuffer("{\"success\":true,\"totalCounts\":");
		   if(null!=list && list.size()>0){
			//   List<Dictionary> list1=dictionaryService.getByProTypeId(list.get(0).getProTypeId());
			   buff.append(list.size()).append(",\"result\":[{\"text\":\"\",\"value\":\"\"},");
			//   buff.append(list.size()).append(",\"result\":[");
				for (Dictionary dic : list) {
					buff.append("{\"text\":\"").append(dic.getItemValue()).append("\",\"value\":\"")
					.append(dic.getDicId()).append("\"},");

				}
				if (list.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]}");
				setJsonString(buff.toString());
				System.out.println("buff.toString()=="+buff.toString());
		   }
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		   return SUCCESS;
	   }
	//获取根节点
		public String listByParentId() throws Exception{
		String parentIdstr=	this.getRequest().getParameter("parentId");
			if(!"null".equals(parentIdstr)&&null!=parentIdstr&&!parentIdstr.equals("")){
				Integer parentId =Integer.valueOf(this.getRequest().getParameter("parentId"));
				try{
					List<AreaDic> list=areaDicService.listByParentId(parentId);
					if(null!=list && list.size()>0){
						StringBuffer buff = new StringBuffer("[");
						for (AreaDic areaDic:list) {
							buff.append("{\"text\":\"").append(areaDic.getText()).append("\",\"value\":\"")
							.append(areaDic.getId()).append("\"},");

						}
						if (list.size() > 0) {
							buff.deleteCharAt(buff.length() - 1);
						}
						buff.append("]");
						
						setJsonString(buff.toString());
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			return SUCCESS;
		}
		 public String lendNeed(){
				String projectId = this.getRequest().getParameter("projectId"); // 项目ID
			// 借款需求
				BpMoneyBorrowDemand	bpMoneyBorrowDemand=new BpMoneyBorrowDemand();
				if (null != projectId && !"".equals(projectId)) {
					List<BpMoneyBorrowDemand> list = this.bpMoneyBorrowDemandService
							.getMessageByProjectID(Long.valueOf(projectId));
					if (list.size() > 0) {
						bpMoneyBorrowDemand = list.get(0);
					}
				}
				SlSmallloanProject project = this.slSmallloanProjectService.get(Long.valueOf(projectId));
				StringBuffer sb = new StringBuffer("{\"success\":true,\"data\":");
				JSONSerializer json = JsonUtil.getJSONSerializer("intentDate");
				json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"endDate","startInterestDate","startDate" });
				sb.append(json.serialize(bpMoneyBorrowDemand));
				sb=new StringBuffer(sb.substring(0, sb.length()-1));
				sb.append(",\"customerChannel\":\"").append(project.getCustomerChannel());
				sb.append("\",\"purposeType\":\"").append(project.getPurposeType());
				sb.append("\",\"payaccrualType\":\"").append(project.getPayaccrualType());
				sb.append("\"}}");
				jsonString = sb.toString();
				System.out.println("jsonString===++++++++++++++++++++++++++++++"+jsonString);
				return SUCCESS;
		   }
      public String bpProductParameterlist(){
				
					QueryFilter filter=new QueryFilter(getRequest());
					List<BpProductParameter> list= bpProductParameterService.getAll(filter);
					try{
					if(null!=list && list.size()>0){
						StringBuffer buff = new StringBuffer("[");
						for (BpProductParameter a:list) {
							buff.append("{\"text\":\"").append(a.getProductName()).append("\",\"value\":\"")
							.append(a.getId()).append("\"},");

						}
						if (list.size() > 0) {
							buff.deleteCharAt(buff.length() - 1);
						}
						buff.append("]");
						
						setJsonString(buff.toString());
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
				
				return SUCCESS;
			}
      public String getTypes(){
		String customerType=this.getRequest().getParameter("customerType");
		List<RatingTemplate> list=null;
		if("0".equals(customerType)){
			list = ratingTemplateDao.getRatingTemplateList(null,"企业");
		}else if("1".equals(customerType)){
			list = ratingTemplateDao.getRatingTemplateList(null,"个人");
		}
		if(null!=list && list.size()>0){
			StringBuffer buff = new StringBuffer("[");
			for (RatingTemplate rt : list) {
				buff.append("{\"text\":\"").append(rt.getTemplateName()).append("\",\"value\":\"")
						.append(rt.getId()).append("\"},");
			}
			if (list.size() > 0) {
				buff.deleteCharAt(buff.length() - 1);
			}
			buff.append("]");
			setJsonString(buff.toString());
		}
		return SUCCESS;
	}

      public String getBankList(){
    	  List<CsBank> list=csBankService.getAll();
    	  
  		if(null!=list && list.size()>0){
  			StringBuffer buff = new StringBuffer("[");
  			for (CsBank bank : list) {
  				buff.append("{\"text\":\"").append(bank.getBankname()).append("\",\"value\":\"")
  						.append(bank.getBankid()).append("\"},");
  			}
  			if (list.size() > 0) {
  				buff.deleteCharAt(buff.length() - 1);
  			}
  			buff.append("]");
  			setJsonString(buff.toString());
  		}
  		return SUCCESS;
  	}
      
      public String savePerson(){
    	  Person p=personDao.getById(person.getId());
    		try {
				BeanUtil.copyNotNullProperties(p, person);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
    		
    		personDao.merge(p);
		String str=	personDao.savePersonInfo(person, null, null);
		setJsonString(str);
		
	/*	  Person p=personDao.getById(person.getId());
  		p.setVehicleConfiguration(person.getVehicleConfiguration());
  		p.setName("adsf");
  		p.setPostaddress("adsfa");
  		System.out.println("p.getVehicleConfiguration()==="+p.getVehicleConfiguration());
  		personDao.save(p);*/
		return SUCCESS;
      
      }
      //保存、修改需求信息
      public String lendForm(){
    	  String projectId = this.getRequest().getParameter("projectId"); // 项目ID
    	  SlSmallloanProject slSmallloanProject111 = slSmallloanProjectService.get(Long.valueOf(projectId));
    	  try {
				BeanUtil.copyNotNullProperties(slSmallloanProject111, slSmallloanProject);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			slSmallloanProject111.setProjectMoney(bpMoneyBorrowDemand.getQuotaApplicationSmall());
			slSmallloanProject111.setAccrual(bpMoneyBorrowDemand.getAccrual());
			slSmallloanProject111.setPayintentPeriod(bpMoneyBorrowDemand.getLongestRepaymentPeriod());
    	  slSmallloanProjectService.merge(slSmallloanProject111);
    	  System.out.println(slSmallloanProject.getProjectId());
    	  bpMoneyBorrowDemand.setProjectID(Long.valueOf(projectId));
			if (bpMoneyBorrowDemand.getBorrowid() == null) {
				this.bpMoneyBorrowDemandService.save(bpMoneyBorrowDemand);
			} else {
				BpMoneyBorrowDemand bbd = bpMoneyBorrowDemandDao.load(bpMoneyBorrowDemand.getBorrowid());
				try {
					BeanUtil.copyNotNullProperties(bbd, bpMoneyBorrowDemand);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				this.bpMoneyBorrowDemandService.merge(bbd);
			}
    	  System.out.println(bpMoneyBorrowDemand.getLongestRepaymentPeriod());
    	  jsonString = "{success:true}";
		return SUCCESS;
      
      }
      public String getFlowToInfo() {
  		String projectId = this.getRequest().getParameter("projectId"); // 项目ID
  		String businessType = this.getRequest().getParameter("businessType"); // 
  		SlSmallloanProject project = this.slSmallloanProjectService.get(Long.valueOf(projectId));
  		
  	   //获取 节点流转控制金额
		Document document=XmlUtil.getSystemConfigXML();
	    Node controlMoneyNode = document.selectSingleNode("/zhiwei/systemConfig/controlMoney");
	    String controlMoney="0";
	    if(null!=controlMoneyNode){
	    	controlMoney= controlMoneyNode.getText();
	    }
	    
	
	    
  		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
  		StringBuffer sb = new StringBuffer("{\"success\":true,\"projectMoney\":");
  		sb.append(project.getProjectMoney());
  		sb.append(",\"controlMoney\":");
  		sb.append(controlMoney);
  		//车辆估值建议，移植代码如果没有这个属性可以直接删除下面两行
//  		sb.append(",\"vehicleproLoanAmount\":");
//  		sb.append(project.getVehicleproLoanAmount());
  		sb.append("}");
  		setJsonString(sb.toString());
  		return SUCCESS;
  	}
      
    //修改项目基本信息
      public String creditLoanProject(){

    	  String projectId = this.getRequest().getParameter("projectId"); // 项目ID
    	  SlSmallloanProject slSmallloanProject111 = slSmallloanProjectService.get(Long.valueOf(projectId));
    	  try {
				BeanUtil.copyNotNullProperties(slSmallloanProject111, slSmallloanProject);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
    	  slSmallloanProjectService.merge(slSmallloanProject111);
    	  System.out.println(slSmallloanProject.getProjectId());
    	  jsonString = "{success:true}";
		return SUCCESS;
      
      }
      
      //保存监管
      public String saveGlobalSupervisemanage() {
    	  String projectId = this.getRequest().getParameter("projectId"); // 项目ID
    	  String businessType = this.getRequest().getParameter("businessType"); //
  		try {
  			if(globalSupervisemanage.getSuperviseManageId()==null){
  				globalSupervisemanage.setProjectId(Long.valueOf(projectId));
				globalSupervisemanage.setBusinessType(businessType);
				globalSupervisemanage.setSuperviseManageStatus(Short.valueOf("0"));
				globalSupervisemanage.setDesignee(ContextUtil.getCurrentUser().getFullname());
				globalSupervisemanage.setDesigneeId(Long.valueOf(ContextUtil.getCurrentUser().getId()));
  				globalSupervisemanageService.save(globalSupervisemanage);
  			}else{
	  	    	GlobalSupervisemanage orgGlobalSupervisemanage=globalSupervisemanageService.get(globalSupervisemanage.getSuperviseManageId());
	  	    	BeanUtil.copyNotNullProperties(orgGlobalSupervisemanage, globalSupervisemanage);

	  	    	globalSupervisemanageService.save(orgGlobalSupervisemanage);
  	    	
  			}
  		} catch (Exception ex) {
  			ex.printStackTrace();
  			logger.error(":" + ex.getMessage());
  		}
  	  jsonString = "{success:true}";
		return SUCCESS;
  	}
  	public String getGlobalSupervisemanageobj() {
		String superviseManageId = this.getRequest().getParameter("superviseManageId"); // 项目ID

		 globalSupervisemanage = this.globalSupervisemanageService.get(Long.valueOf(superviseManageId));
		StringBuffer sb = new StringBuffer("{\"success\":true,\"data\":");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate");
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
			"endDate","startInterestDate","startDate" ,"intentDate" });
		sb.append(json.serialize(globalSupervisemanage));
		sb.append("}");
		jsonString = sb.toString();
		System.out.println("jsonString==="+jsonString);
		return SUCCESS;
	}
  	
  	
  	
  	public String getGlobalSupervisemanagelist() {
		String superviseManageId = this.getRequest().getParameter("superviseManageId"); // 项目ID

		 globalSupervisemanage = this.globalSupervisemanageService.get(Long.valueOf(superviseManageId));
		StringBuffer sb = new StringBuffer("{\"success\":true,\"data\":");
		JSONSerializer json = JsonUtil.getJSONSerializer("strsuperviseManageTime");
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
			"strsuperviseManageTime" });
		sb.append(json.serialize(globalSupervisemanage));
		sb.append("}");
		jsonString = sb.toString();
		System.out.println("jsonString==="+jsonString);
		return SUCCESS;
	}
//  	/**
//	 * 显示列表
//	 */
//	public String listProcreditMortgageProduct(){
//		
//		QueryFilter filter=new QueryFilter(getRequest());
//		filter.addFilter("Q_mortgageid_N_EQ", personId);
//		List<ProcreditMortgageProduct> list= procreditMortgageProductService.getAll(filter);
//		
//		//Type type=new TypeToken<List<Product>>(){}.getType();
//		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
//		.append(filter.getPagingBean().getTotalItems()).append(",result:");
//		
//		//Gson gson=new Gson();
//		JSONSerializer json = JsonUtil.getJSONSerializer();
//		buff.append(json.exclude(new String[]{"class"}).serialize(list));
//		buff.append("}");
//		
//		jsonString=buff.toString();
//		System.out.println("==================="+jsonString);
//		return SUCCESS;
//	}
//  	/**
//	 * 批量删除
//	 * @return
//	 */
//	public String multiDelProcreditMortgageProduct(){
//		
//		String[]ids=getRequest().getParameterValues("ids");
//		if(ids!=null){
//			for(String id:ids){
//				procreditMortgageProductService.remove(new Long(id));
//			}
//		}
//		
//		jsonString="{success:true}";
//		
//		return SUCCESS;
//	}
//	
//	/**
//	 * 显示详细信息
//	 * @return
//	 */
//	public String getProcreditMortgageProductById(){
//		ProcreditMortgageProduct procreditMortgageProduct=procreditMortgageProductService.getById(Integer.valueOf(productId.toString()));
//		
//		//Gson gson=new Gson();
//		JSONSerializer json = JsonUtil.getJSONSerializer("createtime","updatetime");
//		//将数据转成JSON格式
//		StringBuffer sb = new StringBuffer("{success:true,data:");
//		sb.append(json.exclude(new String[]{"class"}).serialize(procreditMortgageProduct));
//		sb.append("}");
//		setJsonString(sb.toString());
//		
//		return SUCCESS;
//	}
//	/**
//	 * 添加及保存操作
//	 */
//	public String saveProcreditMortgageProduct(){
//		if(personId!=null){
//			procreditMortgageProduct.setMortgageid(personId);
//		}
//		if(procreditMortgageProduct.getId()==null){
//			procreditMortgageProductService.save(procreditMortgageProduct);
//		}else if(procreditMortgageProduct.getId()!=null){
//			procreditMortgageProductService.merge(procreditMortgageProduct);
//		}
//		setJsonString("{success:true}");
//		return SUCCESS;
//	}
	
}