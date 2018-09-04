package com.zhiwei.credit.action.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.dao.creditFlow.finance.SlActualToChargeDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlPlansToChargeDao;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.model.creditFlow.finance.SlChargeDetail;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlBankAccountService;
import com.zhiwei.credit.service.creditFlow.finance.SlChargeDetailService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundQlideService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class SlActualToChargeAction extends BaseAction{
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private SlPlansToChargeDao slPlansToChargeDao;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private SlBankAccountService slBankAccountService;
	@Resource
	private SlFundQlideService slFundQlideService;
	@Resource
	private SlChargeDetailService slChargeDetailService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private GLGuaranteeloanProjectService glGuaranteeloanProjectService; //企业贷款
	@Resource
	private OrganizationService organizationService;
	@Resource
	private SlActualToChargeDao slActualToChargeDao;
	@Resource
	private CsBankService csBankService;
	
	private SlActualToCharge slActualToCharge;
	
	private Long actualChargeId;
	private Long projectId;
	private String slPlansToChargeJson;
	private Integer isOperationType;
	private String chargekey;
	private String businessType;
	private Long fundQlideId;
	private SlFundQlide slFundQlide;
	private Integer flag;
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public SlFundQlide getSlFundQlide() {
		return slFundQlide;
	}

	public void setSlFundQlide(SlFundQlide slFundQlide) {
		this.slFundQlide = slFundQlide;
	}

	public Long getFundQlideId() {
		return fundQlideId;
	}

	public void setFundQlideId(Long fundQlideId) {
		this.fundQlideId = fundQlideId;
	}

	public String getChargekey() {
		return chargekey;
	}

	public void setChargekey(String chargekey) {
		this.chargekey = chargekey;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getIsOperationType() {
		return isOperationType;
	}

	public void setIsOperationType(Integer isOperationType) {
		this.isOperationType = isOperationType;
	}

	public String getSlPlansToChargeJson() {
		return slPlansToChargeJson;
	}

	public void setSlPlansToChargeJson(String slPlansToChargeJson) {
		this.slPlansToChargeJson = slPlansToChargeJson;
	}


	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getActualChargeId() {
		return actualChargeId;
	}

	public void setActualChargeId(Long actualChargeId) {
		this.actualChargeId = actualChargeId;
	}

	public SlActualToCharge getSlActualToCharge() {
		return slActualToCharge;
	}

	public void setSlActualToCharge(SlActualToCharge slActualToCharge) {
		this.slActualToCharge = slActualToCharge;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		Map<String,String> map = new HashMap<String,String>();
		int size=0;
		List<SlActualToCharge> list=new ArrayList<SlActualToCharge>();
		String searchaccount;
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    		
    	}
    	String projectProperties=this.getRequest().getParameter("projectProperties");
		if(null!=projectProperties && !projectProperties.equals("")){
			String properties="";
			String[] propertiesArr=projectProperties.split(",");
			for(int i=0;i<propertiesArr.length;i++){
				properties=properties+"'"+propertiesArr[i]+"',";
			}
			if(!properties.equals("")){
				properties=properties.substring(0,properties.length()-1);
			}
			map.put("properties",properties);
		}
    	list =slActualToChargeService.search(map,businessType);
		size=slActualToChargeService.searchsize(map,businessType);
	          for (SlActualToCharge l : list) {
	        	  if(null!=l.getCompanyId()){
	  				Organization organization=organizationService.get(l.getCompanyId());
	  				if(null!=organization){
	  					l.setOrgName(organization.getOrgName());
	  				}
	  			
	  			}
	        		l.setTypeName(slPlansToChargeService.get(l.getPlanChargeId()).getName());
//	        	  if(l.getBusinessType().equals("SmallLoan")){
//						l.setProjectName(slSmallloanProjectService.get(l.getProjectId()).getProjectName());
//						l.setProjectNumber(slSmallloanProjectService.get(l.getProjectId()).getProjectNumber());
//	        	  }
//	        	  if(l.getBusinessType().equals("Financing")){
//	        		  l.setProjectName(flFinancingProjectService.get(l.getProjectId()).getProjectName());
//					l.setProjectNumber(flFinancingProjectService.get(l.getProjectId()).getProjectNumber());
//	        	  }
			}
	     
			
		
//	          MyComparecharge comp=new MyComparecharge();
//			 Collections.sort(list,comp);
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate"});
			json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
			return SUCCESS;
	}
	//导出Excel
	public void intentDownLoad() {
		Map<String, String> map = new HashMap<String, String>();
		int size = 0;
		List<SlActualToCharge> list = new ArrayList<SlActualToCharge>();
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		String projectProperties = this.getRequest().getParameter(
				"projectProperties");
		if (null != projectProperties && !projectProperties.equals("")) {
			String properties = "";
			String[] propertiesArr = projectProperties.split(",");
			for (int i = 0; i < propertiesArr.length; i++) {
				properties = properties + "'" + propertiesArr[i] + "',";
			}
			if (!properties.equals("")) {
				properties = properties.substring(0, properties.length() - 1);
			}
			map.put("properties", properties);
		}
		list = slActualToChargeService.search(map, businessType);
		size = slActualToChargeService.searchsize(map, businessType);
		for (SlActualToCharge l : list) {
			if (null != l.getCompanyId()) {
				Organization organization = organizationService.get(l
						.getCompanyId());
				if (null != organization) {
					l.setOrgName(organization.getOrgName());
				}
			}
			l.setTypeName(slPlansToChargeService.get(l.getPlanChargeId())
					.getName());
		}
		String[] tableHeader = { "序号", "项目名称", "项目编号", "手续费用收取类型", "计划收取金额","计划支出金额",
				"计划到账日", "实际到账日", "已对账金额", "未对账金额",
				"核销金额","是否逾期","备注" };
		String[] fields = {"projectName","projectNumber","TypeName","incomeMoney","payMoney",
				"intentDate","factDate","afterMoney","notMoney",
				"flatMoney","isOverdue","remark"};
		try {
			ExportExcel.export(tableHeader, fields, list,"手续收入台账", SlActualToCharge.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据bidPlanId获取手续费列表
	 * */
   public String listbyBidPlanId(){
	    int size = 0; 
	    String isUnLoadData = this.getRequest().getParameter("isUnLoadData");
	    String chargeKey = this.getRequest().getParameter("chargeKey");
	    String bidPlanId = this.getRequest().getParameter("bidPlanId");
	    StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
	    if(Boolean.valueOf(isUnLoadData)){
	    	 List<SlActualToCharge> list = new ArrayList<SlActualToCharge>();
	    	 JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
			 json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate"});
			 json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
			 buff.append(json.serialize(list));
			 buff.append("}");
			 jsonString = buff.toString();
	    }else{
	    	List<SlActualToCharge> list = null;
	    	QueryFilter filter = new QueryFilter(getRequest());
	    	if(null!=bidPlanId&&!"".equals(bidPlanId))
	    		filter.addFilter("Q_bidPlanId_L_EQ", bidPlanId);
	    	if(null!=chargeKey&&!"".equals(chargeKey))
	    		filter.addFilter("Q_chargeKey_S_IN",chargeKey);
	    	list = slActualToChargeService.getAll(filter);
			if(list!=null){
				size = list.size();
			}
			for(SlActualToCharge l:list){
				if(l==null){
					continue;
				}
				slActualToChargeService.evict(l);
				SlPlansToCharge ptc = slPlansToChargeService.get(l.getPlanChargeId());
				if(ptc!=null)
					l.setTypeName(slPlansToChargeService.get(l.getPlanChargeId()).getName());
				if(CompareDate.compare_date(l.getIntentDate(),new Date())>0){
					l.setIsOverdue("是");
				}else{
					l.setIsOverdue("否");
				}
			}
			
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate"});
			json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
	    }
	   return SUCCESS;
   }
   public String listbyproject(){
	   	int size = 0;
	    String isUnLoadData = this.getRequest().getParameter("isUnLoadData");
	    String chargeKey = this.getRequest().getParameter("chargeKey");
	    StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
	    if(Boolean.valueOf(isUnLoadData)){
	    	 List<SlActualToCharge> list = new ArrayList<SlActualToCharge>();
	    	 JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
			 json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate"});
			 json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
			 buff.append(json.serialize(list));
			 buff.append("}");
			 jsonString = buff.toString();
	    }else{
	    	List<SlActualToCharge> list = null;
	    	if(chargeKey!=null&&!"".equals(chargeKey)){
	    		list = slActualToChargeService.listbyproject(projectId,businessType,chargeKey);
	    	}else{
	    		list = slActualToChargeService.listbyproject(projectId,businessType);
	    	}
			if(list!=null){
				size = list.size();
			}
			for(SlActualToCharge l:list){
				slActualToChargeService.evict(l);
				if(null==l||null==l.getPlanChargeId()){
					continue;
				}
				if(slPlansToChargeService.get(l.getPlanChargeId())!=null){
				l.setTypeName(slPlansToChargeService.get(l.getPlanChargeId()).getName());
				}else{
					l.setTypeName("");
				}
				if(null==l.getIntentDate()){
					
				}else{
					if(CompareDate.compare_date(l.getIntentDate(),new Date())>0){
						l.setIsOverdue("是");
					}else{
						l.setIsOverdue("否");
					}
				}
				
			}
			
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate"});
			json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
	    }
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
   
   public String listbySuperviseRecord() {
	   int size=0;
	   List<SlActualToCharge> list=new ArrayList<SlActualToCharge>();
	   String isUnLoadData=this.getRequest().getParameter("isUnLoadData");
	   String isThisSuperviseRecord=getRequest().getParameter("isThisSuperviseRecord");
	   String slSuperviseRecordIdstring=getRequest().getParameter("slSuperviseRecordId");
	   if((Boolean.valueOf(isUnLoadData))){
		   
	   }else if(isThisSuperviseRecord.equals("no") && slSuperviseRecordIdstring.equals("noid")){
	         list=slActualToChargeService.listbyproject(projectId, businessType);
	   }else{
	   
			   Long slSuperviseRecordId=Long.parseLong(getRequest().getParameter("slSuperviseRecordId"));
			   if(isThisSuperviseRecord.equals("yes")){
				   list= slActualToChargeService.getlistbyslSuperviseRecordId(slSuperviseRecordId, businessType, projectId);
			   }else{
				   List<SlActualToCharge> listall= slActualToChargeService.listbyproject(projectId, businessType);
				   for(SlActualToCharge l:listall){
					   if(l.getSlSuperviseRecordId()==null){
						   list.add(l);
					   }
					   if(l.getSlSuperviseRecordId()!=null &&!l.getSlSuperviseRecordId().equals(slSuperviseRecordId)){
						   list.add(l);
					   }
				    }
				   
			   }
	   }
	   
	   for(SlActualToCharge l:list){
			slActualToChargeService.evict(l);
			l.setTypeName(slPlansToChargeService.get(l.getPlanChargeId()).getName());
			if(CompareDate.compare_date(l.getIntentDate(),new Date())>0){
				l.setIsOverdue("是");
			}else{
				
				l.setIsOverdue("否");
			}
		}	 
         
	   StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(
				",result:");
    	JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
			"factDate");
	    json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate","factDate"});
	buff.append(json.serialize(list));
	buff.append("}");
	jsonString = buff.toString();
	return SUCCESS;
	}
   public String listbyEarlyRepaymentRecord() {
	   int size=0;
	   List<SlActualToCharge> list=new ArrayList<SlActualToCharge>();
	   String isUnLoadData=this.getRequest().getParameter("isUnLoadData");
	   String isThisEarlyPaymentRecord=getRequest().getParameter("isThisEarlyPaymentRecord");
	   String isThisEarlyPaymentRecordIdstring=getRequest().getParameter("isThisEarlyPaymentRecordId");
	   if((Boolean.valueOf(isUnLoadData))){
		   
	   }else if(isThisEarlyPaymentRecord.equals("no") && isThisEarlyPaymentRecordIdstring.equals("noid")){
	         list=slActualToChargeService.listbyproject(projectId, businessType);
	   }else{
	   
			   Long slSuperviseRecordId=Long.parseLong(getRequest().getParameter("isThisEarlyPaymentRecordId"));
			   if(isThisEarlyPaymentRecord.equals("yes")){
				   list= slActualToChargeService.getlistbyslSuperviseRecordId(slSuperviseRecordId, businessType, projectId);
			   }else{
				   List<SlActualToCharge> listall= slActualToChargeService.listbyproject(projectId, businessType);
				   for(SlActualToCharge l:listall){
					   if(l.getSlSuperviseRecordId()==null){
						   list.add(l);
					   }
					   if(l.getSlSuperviseRecordId()!=null &&!l.getSlSuperviseRecordId().equals(slSuperviseRecordId)){
						   list.add(l);
					   }
				    }
				   
			   }
	   }
	   
	   for(SlActualToCharge l:list){
			slActualToChargeService.evict(l);
			l.setTypeName(slPlansToChargeService.get(l.getPlanChargeId()).getName());
			if(CompareDate.compare_date(l.getIntentDate(),new Date())>0){
				l.setIsOverdue("是");
			}else{
				
				l.setIsOverdue("否");
			}
		}	 
         
	   StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(
				",result:");
    	JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
			"factDate");
	    json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate","factDate"});
	buff.append(json.serialize(list));
	buff.append("}");
	jsonString = buff.toString();
	return SUCCESS;
	}
   public String listbyAlterAccrualRecord() {
	   int size=0;
	   List<SlActualToCharge> list=new ArrayList<SlActualToCharge>();
	   String isUnLoadData=this.getRequest().getParameter("isUnLoadData");
	   String isThisAlterAccrualRecord=getRequest().getParameter("isThisAlterAccrualRecord");
	   String isThisAlterAccrualRecordIdstring=getRequest().getParameter("isThisAlterAccrualRecordId");
	   if((Boolean.valueOf(isUnLoadData))){
		   
	   }else if(isThisAlterAccrualRecord.equals("no") && isThisAlterAccrualRecordIdstring.equals("noid")){
	         list=slActualToChargeService.listbyproject(projectId, businessType);
	   }else{
	   
			   Long alterAccrualRecordId=Long.parseLong(getRequest().getParameter("isThisAlterAccrualRecordId"));
			   if(isThisAlterAccrualRecord.equals("yes")){
				   list= slActualToChargeService.getlistbyslSuperviseRecordId(alterAccrualRecordId, businessType, projectId);
			   }else{
				   List<SlActualToCharge> listall= slActualToChargeService.listbyproject(projectId, businessType);
				   for(SlActualToCharge l:listall){
					   if(l.getSlAlteraccrualRecordId()==null){
						   list.add(l);
					   }
					   if(l.getSlAlteraccrualRecordId()!=null &&!l.getSlAlteraccrualRecordId().equals(alterAccrualRecordId)){
						   list.add(l);
					   }
				    }
				   
			   }
	   }
	   
	   for(SlActualToCharge l:list){
			slActualToChargeService.evict(l);
			l.setTypeName(slPlansToChargeService.get(l.getPlanChargeId()).getName());
			if(CompareDate.compare_date(l.getIntentDate(),new Date())>0){
				l.setIsOverdue("是");
			}else{
				
				l.setIsOverdue("否");
			}
		}	 
         
	   StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(
				",result:");
    	JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
			"factDate");
	    json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate","factDate"});
	buff.append(json.serialize(list));
	buff.append("}");
	jsonString = buff.toString();
	return SUCCESS;
	}
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				slActualToChargeService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	public String delete() {
		try {
			SlActualToCharge slActualToCharge = slActualToChargeService.get(actualChargeId);
			 projectId=slActualToCharge.getProjectId();
			 businessType=slActualToCharge.getBusinessType();
			slActualToChargeService.remove(slActualToCharge);
			setJsonString("{success:true}");
		} catch (Exception e) {
			setJsonString("{success:false}");
		}
		 saveprojectreleadfinance(projectId,businessType);
		return SUCCESS;
	}
	
	public String deleteByProject() {
		//Long projectId=Long.valueOf(getRequest().getParameter("projectId"));
		//String businessType=this.getRequest().getParameter("businessType");
		try {
			
			SlActualToCharge slActualToCharge = slActualToChargeService.get(actualChargeId);
			 //projectId=slActualToCharge.getProjectId();
			// businessType=slActualToCharge.getBusinessType();
			
			slActualToChargeService.remove(slActualToCharge);
			setJsonString("{success:true}");
		} catch (Exception e) {
			setJsonString("{success:false}");
		}
		 saveprojectreleadfinance(projectId,businessType);
		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		SlActualToCharge slActualToCharge=slActualToChargeService.get(actualChargeId);
		StringBuffer buff = new StringBuffer("{success:true,'data':");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate");
		buff.append(json.serialize(slActualToCharge));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
//		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//		//将数据转成JSON格式
//		StringBuffer sb = new StringBuffer("{success:true,data:");
//		sb.append(gson.toJson(slActualToCharge));
//		sb.append("}");
//		setJsonString(sb.toString());
//		
//		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slActualToCharge.getActualChargeId()==null){
			slActualToChargeService.save(slActualToCharge);
		}else{
			SlActualToCharge orgSlActualToCharge=slActualToChargeService.get(slActualToCharge.getActualChargeId());
			try{
				BeanUtil.copyNotNullProperties(orgSlActualToCharge, slActualToCharge);
				slActualToChargeService.save(orgSlActualToCharge);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String savejson(){

		if (null != slPlansToChargeJson && !"".equals(slPlansToChargeJson)) {

			String[] shareequityArr = slPlansToChargeJson.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
//				String substr = str.substring(18, 20);
//				String str1 = "\"\"";
//				if (substr.equals("\"\"")) {
//					str = str.substring(21);
//					str = "{" + str;
//
//				}
					String[] strArr=str.split(",");
					String typestr="";
					if(strArr.length==7){   //传过来字段的个数
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
									if(p.getName().equals(typename) && p.getBusinessType().equals(businessType)){
										k++;
									}
								}
								
								if(k==0){
									slPlansToCharge.setName(typename);
									slPlansToCharge.setIsType(1);
									slPlansToCharge.setIsValid(0);
								slPlansToCharge.setBusinessType(businessType);
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
							
					
					slActualToCharge.setProjectId(projectId);
					if(businessType.equals("SmallLoan")){
						com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject loan=slSmallloanProjectService.get(projectId);
						slActualToCharge.setProjectName(loan.getProjectName());
						slActualToCharge.setProjectNumber(loan.getProjectNumber());
						
					}
	        	  if(businessType.equals("Financisng")){
	        		  FlFinancingProject flFinancingProject=flFinancingProjectService.get(projectId);
	        		  slActualToCharge.setProjectName(flFinancingProject.getProjectName());
	        		  slActualToCharge.setProjectNumber(flFinancingProject.getProjectNumber());
					}
					slActualToCharge.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
					if (null == slActualToCharge.getActualChargeId()) {

					slActualToCharge.setAfterMoney(new BigDecimal(0));
					slActualToCharge.setFlatMoney(new BigDecimal(0));
					if(slActualToCharge.getIncomeMoney().equals(new BigDecimal(0.00))){
							slActualToCharge.setNotMoney(slActualToCharge.getPayMoney());
						}else{
							slActualToCharge.setNotMoney(slActualToCharge.getIncomeMoney());
						}
					slActualToCharge.setBusinessType(businessType);
					if(flag !=null && flag==0){   //表示在合同签署以后，可以对账了
					Short a=0;
					slActualToCharge.setIsCheck(a);
					}
					slActualToChargeService.save(slActualToCharge);
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
								slActualToCharge1.setBusinessType(businessType);
								slActualToChargeService.save(slActualToCharge1);
					     }
					}

					setJsonString("{success:true}");

				} catch (Exception e) {
					e.printStackTrace();

				}

			
		}
		}
		return SUCCESS;
}
	public String saveprojectreleadfinance(Long projectId,String businessType){
		BigDecimal paychargeMoney=new  BigDecimal(0);
		BigDecimal incomechargeMoney=new  BigDecimal(0);
		BigDecimal payincomechargeMoney=new  BigDecimal(0);
		BigDecimal paypaychargeMoney=new  BigDecimal(0);
		BigDecimal flatincomechargeMoney=new  BigDecimal(0);
		BigDecimal flatpaychargeMoney=new  BigDecimal(0);
		List<SlActualToCharge> slActualToCharges=slActualToChargeService.listbyproject(projectId, businessType);
		
		if(businessType.equals("SmallLoan")){
			 for(SlActualToCharge l:slActualToCharges){
			    	if(l.getIncomeMoney() !=new BigDecimal(0)){
			    		incomechargeMoney=incomechargeMoney.add(l.getIncomeMoney());
			    		payincomechargeMoney=payincomechargeMoney.add(l.getAfterMoney());
			    		flatincomechargeMoney=flatincomechargeMoney.add(l.getFlatMoney());
			    	}else{
			    		paychargeMoney=paychargeMoney.add(l.getIncomeMoney());
			    		paypaychargeMoney=payincomechargeMoney.add(l.getAfterMoney());
			    		flatpaychargeMoney=flatincomechargeMoney.add(l.getFlatMoney());
			    	}
			    }
			SlSmallloanProject project=slSmallloanProjectService.get(projectId);
			project.setPaychargeMoney(paychargeMoney);
			project.setPayincomechargeMoney(payincomechargeMoney);
			project.setIncomechargeMoney(incomechargeMoney);
			project.setPaypaychargeMoney(paypaychargeMoney);
			project.setFlatincomechargeMoney(flatincomechargeMoney);
			project.setFlatpaychargeMoney(flatpaychargeMoney);
			if(null==project.getFlatMoney()){
				project.setFlatMoney(new BigDecimal(0));
			}
			slSmallloanProjectService.save(project);
			
			
		}
		if(businessType.equals("Financing")){
			 for(SlActualToCharge l:slActualToCharges){
			    	if(l.getIncomeMoney() !=new BigDecimal(0)){
			    		incomechargeMoney=incomechargeMoney.add(l.getIncomeMoney());
			    		payincomechargeMoney=payincomechargeMoney.add(l.getAfterMoney());
			    	}else{
			    		paychargeMoney=incomechargeMoney.add(l.getIncomeMoney());
			    	}
			    }
			 FlFinancingProject project=flFinancingProjectService.get(projectId);
			project.setPaychargeMoney(paychargeMoney);
	//		project.setPayincomechargeMoney(payincomechargeMoney);
			project.setIncomechargeMoney(payincomechargeMoney);
			flFinancingProjectService.save(project);
			
		}

		if(businessType.equals("Guarantee")){
			 for(SlActualToCharge l:slActualToCharges){
			    	if(l.getIncomeMoney() !=new BigDecimal(0)){
			    		incomechargeMoney=incomechargeMoney.add(l.getIncomeMoney());
			    		payincomechargeMoney=payincomechargeMoney.add(l.getAfterMoney());
			    		flatincomechargeMoney=flatincomechargeMoney.add(l.getFlatMoney());
			    	}else{
			    		paychargeMoney=paychargeMoney.add(l.getIncomeMoney());
			    		paypaychargeMoney=payincomechargeMoney.add(l.getAfterMoney());
			    		flatpaychargeMoney=flatincomechargeMoney.add(l.getFlatMoney());
			    	}
			    }
			 GLGuaranteeloanProject project=glGuaranteeloanProjectService.get(projectId);
			project.setPaychargeMoney(paychargeMoney);
			project.setPayincomechargeMoney(payincomechargeMoney);
			project.setIncomechargeMoney(incomechargeMoney);
			project.setPaypaychargeMoney(paypaychargeMoney);
			project.setFlatincomechargeMoney(flatincomechargeMoney);
			project.setFlatpaychargeMoney(flatpaychargeMoney);
			if(null==project.getFlatcustomerEarnestMoney()){
				project.setFlatcustomerEarnestMoney(new BigDecimal(0));
			}
			if(null==project.getFlatpremiumMoney()){
				project.setFlatpremiumMoney(new BigDecimal(0));
			}
			glGuaranteeloanProjectService.save(project);
		
			
			
		}

  	

	   
		
		return SUCCESS;
	}
	public String check() {
		
		String[] ids = getRequest().getParameterValues("qlideId");
		slActualToCharge =slActualToChargeService.get(actualChargeId);
		BigDecimal lin = new BigDecimal(0.00);
		BigDecimal payinmoney=new BigDecimal(0.00);
		if (slActualToCharge.getIncomeMoney().compareTo(lin) == 0) {
			payinmoney=slActualToCharge.getPayMoney();
		} else {
			payinmoney= slActualToCharge.getIncomeMoney();
		}
		if (payinmoney
				.compareTo(slActualToCharge.getAfterMoney()) == 0) {

			return SUCCESS;
		}
		int count = 0;
		if (ids != null) {
			for (String id : ids ) {
				if (!id.equals("") && count < 1) {
				SlChargeDetail slChargeDetail = new SlChargeDetail();
				SlFundQlide slFundQlide = slFundQlideService.get(new Long(
						id));
				 if(slFundQlide.getPayMoney()!=null){
                	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
                 }else{
                	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
                	 
                 } 
				long day = CompareDate.compare_date(slActualToCharge.getIntentDate()
						, slFundQlide.getFactDate());
				if (day == -1 || day == 0) {
					if(null==slActualToCharge.getFactDate())
					{slActualToCharge.setIsOverdue("否");
					}
					slChargeDetail.setOperTime(new Date());
				} else {
					slActualToCharge.setIsOverdue("是");
					slChargeDetail.setOperTime(new Date());
				}
			
				BigDecimal chargeAfterMoney = slActualToCharge.getAfterMoney();
				BigDecimal qlideNotMoney = slFundQlide.getNotMoney();
				BigDecimal detailAfterMoney = qlideNotMoney;
				chargeAfterMoney = chargeAfterMoney.add(qlideNotMoney);
			
				if (chargeAfterMoney
						.compareTo(payinmoney) == 1 || chargeAfterMoney
						.compareTo(payinmoney) == 0) {
					count++;
					detailAfterMoney = slActualToCharge.getNotMoney();
					slFundQlide.setAfterMoney(slActualToCharge.getNotMoney()
							.add(slFundQlide.getAfterMoney()));
					slFundQlide.setNotMoney(slFundQlide.getNotMoney()
							.subtract(slActualToCharge.getNotMoney()));

					slActualToCharge.setAfterMoney(payinmoney);
					slActualToCharge.setNotMoney(new BigDecimal(0.00));
				} else {
					slActualToCharge.setAfterMoney(chargeAfterMoney);
					slFundQlide.setAfterMoney(slFundQlide.getDialMoney());
					slFundQlide.setNotMoney(new BigDecimal(0.00));
					BigDecimal chargeNotMoney = payinmoney.subtract(
									slActualToCharge.getAfterMoney());
					slActualToCharge.setNotMoney(chargeNotMoney);
				}

				slActualToCharge.setFactDate(slFundQlide.getFactDate());
				slChargeDetail.setSlActualToCharge(slActualToCharge);
				slChargeDetail.setSlFundQlide(slFundQlide);
				slChargeDetail.setAfterMoney(detailAfterMoney);
				slChargeDetail.setFactDate(slFundQlide.getFactDate());
				AppUser user=ContextUtil.getCurrentUser();
				slChargeDetail.setCheckuser(user.getFullname());
				slChargeDetail.setCompanyId(slActualToCharge.getCompanyId());	
				slChargeDetailService.save(slChargeDetail);
				slFundQlideService.save(slFundQlide);
				slActualToChargeService.save(slActualToCharge);
				
				
				
				}
			}
		}
		 saveprojectreleadfinance(slActualToCharge.getProjectId(),slActualToCharge.getBusinessType());
		return SUCCESS;
	}	
	public String editQlideCheck() {
		
		BigDecimal inIntentMoney=slFundQlide.getNotMoney();
		SlFundQlide slFundQlide = slFundQlideService.get(fundQlideId);
		 if(slFundQlide.getPayMoney()!=null){
        	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
         }else{
        	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
        	 
         }      
		 //现金流水的初始化
			
		slActualToCharge =slActualToChargeService.get(actualChargeId);
		BigDecimal payinmoney=new BigDecimal(0.00);
		BigDecimal lin = new BigDecimal(0.00);
		if (slActualToCharge.getIncomeMoney().compareTo(lin) == 0) {
			payinmoney=slActualToCharge.getPayMoney();
		} else {
			payinmoney= slActualToCharge.getIncomeMoney();
		}
		 //费用的初始化
		
				SlChargeDetail slChargeDetail = new SlChargeDetail();
				long day = CompareDate.compare_date(slActualToCharge.getIntentDate()
						, slFundQlide.getFactDate());
				if (day == -1 || day == 0) {
					if(null==slActualToCharge.getFactDate())
					{slActualToCharge.setIsOverdue("否");
					}
					slChargeDetail.setOperTime(new Date());
				} else {
					slActualToCharge.setIsOverdue("是");
					slChargeDetail.setOperTime(new Date());
				}
			
				BigDecimal chargeAfterMoney = slActualToCharge.getAfterMoney();
				BigDecimal qlideNotMoney = slFundQlide.getNotMoney();
				BigDecimal detailAfterMoney = qlideNotMoney;
				chargeAfterMoney = chargeAfterMoney.add(qlideNotMoney);
			
					slActualToCharge.setAfterMoney(inIntentMoney.add(slActualToCharge.getAfterMoney()));
					slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().add(inIntentMoney));
					slFundQlide.setNotMoney(slFundQlide.getNotMoney().subtract(inIntentMoney));
					slActualToCharge.setNotMoney(slActualToCharge.getNotMoney().subtract(inIntentMoney));

				slActualToCharge.setFactDate(slFundQlide.getFactDate());
				slChargeDetail.setSlActualToCharge(slActualToCharge);
				slChargeDetail.setSlFundQlide(slFundQlide);
				slChargeDetail.setAfterMoney(inIntentMoney);
				slChargeDetail.setFactDate(slFundQlide.getFactDate());
				AppUser user=ContextUtil.getCurrentUser();
				slChargeDetail.setCheckuser(user.getFullname());
				slChargeDetail.setCompanyId(slActualToCharge.getCompanyId());	
				slChargeDetailService.save(slChargeDetail);
				slFundQlideService.save(slFundQlide);
				slActualToChargeService.save(slActualToCharge);
				
				
				 saveprojectreleadfinance(slActualToCharge.getProjectId(),slActualToCharge.getBusinessType());
		return SUCCESS;
	}
	public String cashCheck() {
		BigDecimal cashMoney=slFundQlide.getNotMoney();
		Date factDate=slFundQlide.getFactDate();
		String transactionType=slFundQlide.getTransactionType();
		SlFundQlide slFundQlide = slFundQlideService.getcashQlide("cash").get(0);
		slFundQlide.setFactDate(factDate);
		 if(slFundQlide.getPayMoney()!=null){
        	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
         }else{
        	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
        	 
         }       //现金流水的初始化
		 
		slActualToCharge =slActualToChargeService.get(actualChargeId);
		BigDecimal lin = new BigDecimal(0.00);
		BigDecimal payinmoney=new BigDecimal(0.00);
		if (slActualToCharge.getIncomeMoney().compareTo(lin) == 0) {
			payinmoney=slActualToCharge.getPayMoney();
		} else {
			payinmoney= slActualToCharge.getIncomeMoney();
		}
		 //费用的初始化
		
				SlChargeDetail slChargeDetail = new SlChargeDetail();
				 if(slFundQlide.getPayMoney()!=null){
                	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
                 }else{
                	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
                	 
                 } 
				long day = CompareDate.compare_date(slActualToCharge.getIntentDate()
						, slFundQlide.getFactDate());
				if (day == -1 || day == 0) {
					if(null==slActualToCharge.getFactDate())
					{slActualToCharge.setIsOverdue("否");
					}
					slChargeDetail.setOperTime(new Date());
				} else {
					slActualToCharge.setIsOverdue("是");
					slChargeDetail.setOperTime(new Date());
				}
			
				BigDecimal chargeAfterMoney = slActualToCharge.getAfterMoney();
				BigDecimal qlideNotMoney = slFundQlide.getNotMoney();
				BigDecimal detailAfterMoney = qlideNotMoney;
				chargeAfterMoney = chargeAfterMoney.add(qlideNotMoney);
			
					slActualToCharge.setAfterMoney(slActualToCharge.getAfterMoney().add(cashMoney));
					slActualToCharge.setNotMoney(slActualToCharge.getNotMoney().subtract(cashMoney));

				slActualToCharge.setFactDate(slFundQlide.getFactDate());
				slChargeDetail.setSlActualToCharge(slActualToCharge);
				slChargeDetail.setSlFundQlide(slFundQlide);
				slChargeDetail.setAfterMoney(cashMoney);
				slChargeDetail.setFactDate(slFundQlide.getFactDate());
				slChargeDetail.setTransactionType(transactionType);
				AppUser user=ContextUtil.getCurrentUser();
				slChargeDetail.setCheckuser(user.getFullname());
				slChargeDetail.setCompanyId(slActualToCharge.getCompanyId());	
				slChargeDetailService.save(slChargeDetail);
		       //slFundQlideService.save(slFundQlide);
				slActualToChargeService.save(slActualToCharge);
				
				
				 saveprojectreleadfinance(slActualToCharge.getProjectId(),slActualToCharge.getBusinessType());	
		return SUCCESS;
	}
	public String manyIntentCheck(){
		 slFundQlide = slFundQlideService.get(fundQlideId);
	        if(slFundQlide.getPayMoney()!=null){
	       	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
	        }else{
	       	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
	       	 
	        }     
			//流水初始化
		String[] ids = getRequest().getParameterValues("qlideId");
		if (ids != null) {
			for (String id : ids ) {
				if (!id.equals("")) {
					
					slActualToCharge = slActualToChargeService.get(new Long(id));
					BigDecimal lin = new BigDecimal(0.00);
					BigDecimal payinmoney=new BigDecimal(0.00);
					if (slActualToCharge.getIncomeMoney().compareTo(lin) == 0) {
						payinmoney=slActualToCharge.getPayMoney();
					} else {
						payinmoney= slActualToCharge.getIncomeMoney();
					}
					
					//款项初始化
					
					List<SlChargeDetail> setdetails=slChargeDetailService.getlistbyActualChargeId(slActualToCharge.getActualChargeId());
						List<SlChargeDetail> sortlist = new ArrayList<SlChargeDetail>();
						for(SlChargeDetail d:setdetails){
							sortlist.add(d);
						}
					//详情初始化
				SlChargeDetail slChargeDetail = new SlChargeDetail();
				long day = CompareDate.compare_date(slActualToCharge.getIntentDate()
						, slFundQlide.getFactDate());
				if (day == -1 || day == 0) {
					if(null==slActualToCharge.getFactDate())
					{slActualToCharge.setIsOverdue("否");
					}
					slChargeDetail.setOperTime(new Date());
				} else {
					slActualToCharge.setIsOverdue("是");
					slChargeDetail.setOperTime(new Date());
				}
			
				BigDecimal chargeAfterMoney = slActualToCharge.getAfterMoney();
				BigDecimal qlideNotMoney = slFundQlide.getNotMoney();
				BigDecimal detailAfterMoney = qlideNotMoney;
				chargeAfterMoney = chargeAfterMoney.add(qlideNotMoney);
			
					detailAfterMoney = slActualToCharge.getNotMoney();
					slFundQlide.setAfterMoney(slActualToCharge.getNotMoney()
							.add(slFundQlide.getAfterMoney()));
					slFundQlide.setNotMoney(slFundQlide.getNotMoney()
							.subtract(slActualToCharge.getNotMoney()));

					slActualToCharge.setAfterMoney(payinmoney);
					slActualToCharge.setNotMoney(new BigDecimal(0.00));

				slActualToCharge.setFactDate(slFundQlide.getFactDate());
				slChargeDetail.setSlActualToCharge(slActualToCharge);
				slChargeDetail.setSlFundQlide(slFundQlide);
				slChargeDetail.setAfterMoney(detailAfterMoney);
				slChargeDetail.setFactDate(slFundQlide.getFactDate());
				AppUser user=ContextUtil.getCurrentUser();
				slChargeDetail.setCheckuser(user.getFullname());
				slChargeDetail.setCompanyId(slActualToCharge.getCompanyId());	
				slChargeDetailService.save(slChargeDetail);
				slFundQlideService.save(slFundQlide);
				slActualToChargeService.save(slActualToCharge);
				
				
				
				}
			}
		}
		 saveprojectreleadfinance(slActualToCharge.getProjectId(),slActualToCharge.getBusinessType());
		return SUCCESS;
	}
	public String savecashqlideAndcheck(){
		
		if(slFundQlide.getFundQlideId()==null){
			if(null !=slFundQlide.getPayMoney()){
				slFundQlide.setNotMoney(slFundQlide.getPayMoney());
			}else{
				slFundQlide.setNotMoney(slFundQlide.getIncomeMoney());
				
			}
			slFundQlide.setAfterMoney(new BigDecimal(0.00));
			slFundQlide.setOperTime(new Date());
			slFundQlide.setMyAccount("cahsqlideAccount");
			slFundQlide.setIsCash("cash");
			
			
			BigDecimal cashMoney=slFundQlide.getNotMoney();
			Date factDate=slFundQlide.getFactDate();
			String transactionType=slFundQlide.getTransactionType();
			 if(slFundQlide.getPayMoney()!=null){
	        	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
	         }else{
	        	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
	        	 
	         }       //现金流水的初始化
			 
			slActualToCharge =slActualToChargeService.get(actualChargeId);
			BigDecimal lin = new BigDecimal(0.00);
			BigDecimal payinmoney=new BigDecimal(0.00);
			if (slActualToCharge.getIncomeMoney().compareTo(lin) == 0) {
				payinmoney=slActualToCharge.getPayMoney();
			} else {
				payinmoney= slActualToCharge.getIncomeMoney();
			}
			 //费用的初始化
			
					SlChargeDetail slChargeDetail = new SlChargeDetail();
					 if(slFundQlide.getPayMoney()!=null){
	                	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
	                 }else{
	                	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
	                	 
	                 } 
					long day = CompareDate.compare_date(slActualToCharge.getIntentDate()
							, slFundQlide.getFactDate());
					if (day == -1 || day == 0) {
						if(null==slActualToCharge.getFactDate())
						{slActualToCharge.setIsOverdue("否");
						}
						slChargeDetail.setOperTime(new Date());
					} else {
						slActualToCharge.setIsOverdue("是");
						slChargeDetail.setOperTime(new Date());
					}
				
					BigDecimal chargeAfterMoney = slActualToCharge.getAfterMoney();
					BigDecimal qlideNotMoney = slFundQlide.getNotMoney();
					BigDecimal detailAfterMoney = qlideNotMoney;
					chargeAfterMoney = chargeAfterMoney.add(qlideNotMoney);
				
						slActualToCharge.setAfterMoney(slActualToCharge.getAfterMoney().add(cashMoney));
						slActualToCharge.setNotMoney(slActualToCharge.getNotMoney().subtract(cashMoney));
                 
					slActualToCharge.setFactDate(slFundQlide.getFactDate());
					slFundQlide.setAfterMoney(cashMoney);
					slFundQlide.setNotMoney(lin);
					slFundQlideService.save(slFundQlide);
					slChargeDetail.setSlActualToCharge(slActualToCharge);
					slChargeDetail.setSlFundQlide(slFundQlide);
					slChargeDetail.setAfterMoney(cashMoney);
					slChargeDetail.setFactDate(slFundQlide.getFactDate());
					slChargeDetail.setTransactionType(transactionType);
					AppUser user=ContextUtil.getCurrentUser();
					slChargeDetail.setCheckuser(user.getFullname());
					slChargeDetail.setCompanyId(slActualToCharge.getCompanyId());	
					slChargeDetailService.save(slChargeDetail);
			       //slFundQlideService.save(slFundQlide);
					slActualToChargeService.save(slActualToCharge);
					
					
					 saveprojectreleadfinance(slActualToCharge.getProjectId(),slActualToCharge.getBusinessType());	
		        	return SUCCESS;
			
		}
		
		
		
		return SUCCESS;
	}
	 public String cancelAccount(){
			String[] ids = getRequest().getParameterValues("qlideId");
			slActualToCharge =slActualToChargeService.get(actualChargeId);
			BigDecimal lin = new BigDecimal(0.00);
			BigDecimal payinmoney=new BigDecimal(0.00);
			if (ids != null) {
				for (String id : ids ) {
					if (!id.equals("") && !id.equals("0")){
						if(null ==slChargeDetailService.get(new Long(id))){return SUCCESS;}
						SlFundQlide slFundQlide = slChargeDetailService.get(new Long(id)).getSlFundQlide();
						
	                    if(slFundQlide.getMyAccount().equals("pingqlideAccount")){  //撤销平账
						   
							
	                        SlChargeDetail slChargeDetail=slChargeDetailService.get(new Long(id));
							AppUser user=ContextUtil.getCurrentUser();
							slChargeDetail.setCancelremark(user.getFullname()+"于"+DateUtil.getNowDateTime()+"撤销此对账记录");
							slChargeDetail.setIscancel(Short.valueOf("1"));
							
							slActualToCharge.setNotMoney(slActualToCharge.getNotMoney().add(slChargeDetail.getAfterMoney()));
							slActualToCharge.setFlatMoney(slActualToCharge.getFlatMoney().subtract(slChargeDetail.getAfterMoney()));
							
							slActualToChargeService.save(slActualToCharge);
							slChargeDetailService.save(slChargeDetail);
					    }else{
						List<SlChargeDetail> slChargeDetails =slChargeDetailService.getlistbyActualChargeId(actualChargeId);
						SlChargeDetail slChargeDetail=new SlChargeDetail();
						List<SlChargeDetail> sortlist = new ArrayList<SlChargeDetail>();
						for(SlChargeDetail d:slChargeDetails){
							if(d.getSlFundQlide()==slFundQlide)
							{	
								slChargeDetail=d;
							}
							   sortlist.add(d);
						   }
						sortlist.remove(slChargeDetail);
						MyComparechargedetail myComparechargedetail=new MyComparechargedetail();
						Collections.sort(sortlist,myComparechargedetail);
						if(sortlist.size()==0){
							slActualToCharge.setIsOverdue(null);
							slActualToCharge.setFactDate(null);
						}else{
								 Date maxDate=sortlist.get(sortlist.size()-1).getSlFundQlide().getFactDate();
								if(CompareDate.compare_date(maxDate,slActualToCharge.getIntentDate())==-1){
									slActualToCharge.setIsOverdue("是");
								}else{
									slActualToCharge.setIsOverdue("否");
								}
								slActualToCharge.setFactDate(maxDate);
						}
						slActualToCharge.setAfterMoney(slActualToCharge.getAfterMoney().subtract(slChargeDetail.getAfterMoney()));
						slActualToCharge.setNotMoney(slActualToCharge.getNotMoney().add(slChargeDetail.getAfterMoney()));
						
						slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().subtract(slChargeDetail.getAfterMoney()));
						slFundQlide.setNotMoney(slFundQlide.getNotMoney().add(slChargeDetail.getAfterMoney()));
						
						slChargeDetail.setIscancel(Short.valueOf("1"));
						AppUser user=ContextUtil.getCurrentUser();
						slChargeDetail.setCancelremark(user.getFullname()+"于"+DateUtil.getNowDateTime()+"撤销此对账记录");
						slChargeDetailService.save(slChargeDetail);
						slFundQlideService.save(slFundQlide);
						slActualToChargeService.save(slActualToCharge);
					    }
					}
				}
		}
			 saveprojectreleadfinance(slActualToCharge.getProjectId(),slActualToCharge.getBusinessType());
		 return SUCCESS; 
	 }
	 public String MoneyDetail() {
		 slActualToCharge = slActualToChargeService.get(actualChargeId);
			List<SlChargeDetail> slChargeDetails = new ArrayList<SlChargeDetail>();
			slChargeDetails = slChargeDetailService.getlistbyActualChargeId(actualChargeId);
			SlChargeDetail slChargeDetail = new SlChargeDetail();
			List<SlChargeDetail> list = new ArrayList<SlChargeDetail>();
			for (SlChargeDetail s : slChargeDetails) {

				if(null==s.getSlFundQlide().getIsCash()){
					s.setQlideafterMoney(s.getSlFundQlide().getAfterMoney());
					List<SlBankAccount>	 sllist=slBankAccountService.getbyaccount(s.getSlFundQlide().getMyAccount());
					if(sllist !=null){
						CsBank cb=csBankService.get(sllist.get(0).getOpenBankId());
						s.setQlidemyAccount(cb.getBankname()+"-"+sllist.get(0).getName()+"-"+sllist.get(0).getAccount());
					}
					s.setQlidenotMoney(s.getSlFundQlide().getNotMoney());
					s.setQlidetransactionType(s.getSlFundQlide().getTransactionType());
					s.setQlidepayMoney(s.getSlFundQlide().getPayMoney());
					s.setQlideincomeMoney(s.getSlFundQlide().getIncomeMoney());
					s.setQlidecurrency(s.getSlFundQlide().getCurrency());
				}else if(s.getSlFundQlide().getMyAccount().equals("pingqlideAccount")){
					
					s.setQlidemyAccount("平账");
					s.setQlideafterMoney(s.getSlFundQlide().getAfterMoney());
					s.setQlidenotMoney(s.getSlFundQlide().getNotMoney());
					s.setQlidetransactionType(s.getSlFundQlide().getTransactionType());
					s.setQlidepayMoney(null);
					s.setFactDate(s.getOperTime());
					s.setQlideincomeMoney(null);
					s.setQlidecurrency(s.getSlFundQlide().getCurrency());
					
				}else{
					if(s.getSlFundQlide().getMyAccount().equals("cahsqlideAccount")){
						s.setQlidemyAccount("现金账户");
						s.setQlideafterMoney(s.getSlFundQlide().getAfterMoney());
						s.setQlidenotMoney(s.getSlFundQlide().getNotMoney());
						s.setQlidetransactionType(s.getSlFundQlide().getTransactionType());
						s.setQlidepayMoney(s.getSlFundQlide().getPayMoney());
						s.setQlideincomeMoney(s.getSlFundQlide().getIncomeMoney());
						s.setQlidecurrency(s.getSlFundQlide().getCurrency());
						
					}else{
					s.setQlidetransactionType(s.getTransactionType());
				}
					}
					list.add(s);

			}
//			MyCompareqlide comp=new MyCompareqlide();
//			 Collections.sort(list,comp);
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(list.size()).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
					"factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate","factDate"});
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();

			return SUCCESS;
	 }
	 public String editAfterMoney() {
//		 SlActualToCharge s= slActualToChargeService.get(slActualToCharge.getActualChargeId());
//		 slActualToChargeService.updateFlatMoney(slActualToCharge);
//			if(s.getBusinessType().equals("SmallLoan")){
//					SlSmallloanProject project=slSmallloanProjectService.get(s.getProjectId());
//					if(null==project.getFlatMoney()){
//						project.setFlatMoney(slActualToCharge.getFlatMoney());
//					}else{
//					project.setFlatMoney(project.getFlatMoney().subtract(s.getFlatMoney()));	
//					project.setFlatMoney(project.getFlatMoney().add(slActualToCharge.getFlatMoney()));
//					
//					}
//					
//					slSmallloanProjectService.save(project);
//			}
//		 slActualToChargeService.evict(s);
		
		 
		 
		 SlActualToCharge s= slActualToChargeService.get(slActualToCharge.getActualChargeId());

		 SlChargeDetail slChargeDetail = new SlChargeDetail();
			SlFundQlide slFundQlide = slFundQlideService.getcashQlide("ping").get(0);
          
			s.setFlatMoney(s.getFlatMoney().add(slActualToCharge.getFlatMoney()));	
            s.setNotMoney(slActualToCharge.getNotMoney());
	//		s.setFactDate(new Date());
            
            slChargeDetail.setOperTime(new Date());
            slChargeDetail.setSlActualToCharge(s);
            slChargeDetail.setSlFundQlide(slFundQlide);
            slChargeDetail.setAfterMoney(slActualToCharge.getFlatMoney());
            slChargeDetail.setFactDate(slFundQlide.getFactDate());
            slChargeDetail.setCompanyId(s.getCompanyId());
			 AppUser user=ContextUtil.getCurrentUser();
			 slChargeDetail.setCheckuser(user.getFullname());
			 
			 slChargeDetailService.save(slChargeDetail);
			 slActualToChargeService.save(s);
			
			
			 saveprojectreleadfinance(s.getProjectId(),s.getBusinessType());
			setJsonString("{success:true}");
			return SUCCESS;
		}

		public String editIsOverdue() {

			slActualToChargeService.updateOverdue(slActualToCharge);

			setJsonString("{success:true}");
			return SUCCESS;
		}
		
		//刷新应收保费以及手动调整保费 或者保证金add by lu 2012.09.14
		public String computeMoney(){
			String oprationType = this.getRequest().getParameter("oprationType");
			if(oprationType!=null&&"system".equals(oprationType)){
				String computePremiumMoney = this.getRequest().getParameter("computePremiumMoney");//计算得到的保费金额
				String computeEarnestMoney = this.getRequest().getParameter("computeEarnestMoney");//计算得到的保证金金额
				String incomeMoney = this.getRequest().getParameter("incomeMoney");//手动修改后的保费金额
				String earnestMoneyVM = this.getRequest().getParameter("earnestMoneyVM");//手动修改后的保证金金额
				String chargeKey = this.getRequest().getParameter("chargeKey");//保费、保证金的key
				String businessType = this.getRequest().getParameter("businessType");
				String isCheck=this.getRequest().getParameter("isCheck");
				if(businessType!=null&&!"".equals(businessType)&&projectId!=null&&!"".equals(projectId)&&chargeKey!=null){
					List<SlActualToCharge> list = slActualToChargeService.listbyproject(projectId, businessType, chargeKey);
					if(incomeMoney!=null&&!"".equals(incomeMoney)&&!"0".equals(incomeMoney)){
						computePremiumMoney = incomeMoney;
					}
					if(earnestMoneyVM!=null&&!"".equals(earnestMoneyVM)&&!"0".equals(earnestMoneyVM)){
						computeEarnestMoney = earnestMoneyVM;
					}
					if(list!=null&&list.size()!=0){
						 for(SlActualToCharge chargeObject:list){
							 if("premiumMoney".equals(chargeObject.getChargeKey())){
								 chargeObject.setIncomeMoney(new BigDecimal(computePremiumMoney));
								 chargeObject.setNotMoney(new BigDecimal(computePremiumMoney));
							 }else if("earnestMoney".equals(chargeObject.getChargeKey())){
								 chargeObject.setIncomeMoney(new BigDecimal(computeEarnestMoney));
								 chargeObject.setNotMoney(new BigDecimal(computeEarnestMoney));
							 }
							 slActualToChargeService.merge(chargeObject);
						 }
					}else if("Guarantee".equals(businessType)){//add by gao
						Short a = Short.valueOf(isCheck);
						GLGuaranteeloanProject project = glGuaranteeloanProjectService.get(projectId);
						slActualToChargeDao.initActualCharges(project.getProjectId(),project.getProjectNumber(),project.getProjectName(),this.slPlansToChargeDao.getbyOperationType(0,businessType),businessType,a);
						list = slActualToChargeService.listbyproject(projectId, businessType, chargeKey);
						if(list!=null&&list.size()!=0){
							 for(SlActualToCharge chargeObject:list){
								 if("premiumMoney".equals(chargeObject.getChargeKey())){
									 chargeObject.setIncomeMoney(new BigDecimal(computePremiumMoney));
									 chargeObject.setNotMoney(new BigDecimal(computePremiumMoney));
								 }else if("earnestMoney".equals(chargeObject.getChargeKey())){
									 chargeObject.setIncomeMoney(new BigDecimal(computeEarnestMoney));
									 chargeObject.setNotMoney(new BigDecimal(computeEarnestMoney));
								 }
								 chargeObject.setCompanyId(project.getCompanyId());
								 slActualToChargeService.merge(chargeObject);
							 }
						}
					}
				}
				
			}else if(oprationType!=null&&"manually".equals(oprationType)){
				SlActualToCharge slActua = slActualToChargeService.get(slActualToCharge.getActualChargeId());
				Long companyId = null;
				if("Guarantee".equals(businessType)){
					companyId =  glGuaranteeloanProjectService.get(projectId).getCompanyId();
				}
				if(slActua!=null){
					if(slActualToCharge.getIncomeMoney()!=null){
						slActua.setIncomeMoney(slActualToCharge.getIncomeMoney());
						slActua.setNotMoney(slActualToCharge.getIncomeMoney());
						slActua.setCompanyId(companyId);
					}
					if(slActualToCharge.getRemark()!=null){
						slActua.setRemark(slActualToCharge.getRemark());
					}
					slActualToChargeService.merge(slActua);
				}
			}
			
			setJsonString("{success:true}");
			return SUCCESS;
		}
		
		public String getByProductId(){
	    	List<SlActualToCharge> list=null;
			String productId=this.getRequest().getParameter("productId");
			if(null!=productId && !"".equals(productId) && !"null".equals(productId)){
				list= slActualToChargeService.getByProductId(productId);
			}
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
			if(null!=list){
				buff.append(list.size()).append(",result:");
			}else{
				buff.append(0).append(",result:");
			}
			Gson gson=new Gson();
			buff.append(gson.toJson(list));
			buff.append("}");
			jsonString=buff.toString();
			return SUCCESS;
	    }
}