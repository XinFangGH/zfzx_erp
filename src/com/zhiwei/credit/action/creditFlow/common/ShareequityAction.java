package com.zhiwei.credit.action.creditFlow.common;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.model.system.DictionaryIndependent;
import com.zhiwei.credit.service.creditFlow.common.EnterpriseShareequityService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.DictionaryService;

import flexjson.JSONSerializer;


public class ShareequityAction extends BaseAction{
	@Resource
	private EnterpriseShareequityService enterpriseShareequityService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private PersonService personService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private GLGuaranteeloanProjectService glGuaranteeloanProjectService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private SgLawsuitguaranteeProjectService sgLawsuitguaranteeProjectService;
	@Resource
	private PlPawnProjectService plPawnProjectService;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	private String shareequity;
	private Integer id;
	private String enterpriseId;
	private Long projectId;
	private Integer personid;
    private String customerType;
    private Integer customerId;
	
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getPersonid() {
		return personid;
	}

	public void setPersonid(Integer personid) {
		this.personid = personid;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShareequity() {
		return shareequity;
	}

	public void setShareequity(String shareequity) {
		this.shareequity = shareequity;
	}
	/*
	 * 保存股东信息
	 * 
	 */
	public String save(){
    	
    	if(null != shareequity && !"".equals(shareequity)) {

			String[] shareequityArr = shareequity.split("@");
			
			for(int i=0; i<shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try{
					EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity)JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
					int enterpriseId=1607;
					enterpriseShareequity.setEnterpriseid(enterpriseId);//假设的企业Id
					if(null==enterpriseShareequity.getId()){
					   enterpriseShareequityService.save(enterpriseShareequity);
					}else{
						EnterpriseShareequity orgEnterpriseShareequity=enterpriseShareequityService.load(enterpriseShareequity.getId());
						BeanUtil.copyNotNullProperties(orgEnterpriseShareequity, enterpriseShareequity);
						enterpriseShareequityService.save(orgEnterpriseShareequity);
					}
					setJsonString("{success:true}");
				
				} catch(Exception e) {
					e.printStackTrace();
					logger.error("ShareequityAction:"+e.getMessage());
				}
				
			}
		}
    	
    	return SUCCESS;
    }
    /*
     * 读取股东信息的详细信息
     */
    public String get(){
        
    	String  bussinessType=this.getRequest().getParameter("bussinessType");
    	if(null!=projectId){    
    		 if("Guarantee".equals(bussinessType)){
    			 GLGuaranteeloanProject project=this.glGuaranteeloanProjectService.get(projectId);
    			 if(null!=project){
    				 enterpriseId=project.getOppositeID().toString();
    			 }
    		 }else if("SmallLoan".equals(bussinessType) || "null".equals(bussinessType)){
    			 SlSmallloanProject project=this.slSmallloanProjectService.get(projectId);
    			 if(null!=project){
    				 enterpriseId=project.getOppositeID().toString();
    			 }
    		 }else if("Financing".equals(bussinessType)){
    			 FlFinancingProject project=this.flFinancingProjectService.get(projectId);
    			 if(null!=project){
    				 enterpriseId=project.getOppositeID().toString();
    			 }
    		 }else if("BeNotFinancing".equals(bussinessType)){
    			 SgLawsuitguaranteeProject project=this.sgLawsuitguaranteeProjectService.get(projectId);
    			 if(null!=project){
    				 enterpriseId=project.getOppositeID().toString();
    			 }
    		 }else if("Pawn".equals(bussinessType)){
    			 PlPawnProject project=this.plPawnProjectService.get(projectId);
    			 if(null!=project){
    				 enterpriseId=project.getOppositeID().toString();
    			 }
    		 }else if("LeaseFinance".equals(bussinessType)){
    			 FlLeaseFinanceProject project=this.flLeaseFinanceProjectService.get(projectId);
    			 if(null!=project){
    				 enterpriseId=project.getOppositeID().toString();
    			 }
    		 }
    	}
    	if(null==enterpriseId || "null".equals(enterpriseId)){
			StringBuffer buff = new StringBuffer("{success:true,result:[");
			buff.append("]");
			buff.append("}");
			jsonString=buff.toString();
			return SUCCESS;
		}
    	List<EnterpriseShareequity> list=enterpriseShareequityService.findShareequityList(Integer.parseInt(enterpriseId));
    	if(list.size()>0){
	    	StringBuffer buff = new StringBuffer("{success:true,result:[");
	        int i=0;
			for(EnterpriseShareequity es:list)
			{
				i++;
				String  shareholdertypeName="";
				if(!"".equals(es.getShareholdertype()))
				{  
					List<DictionaryIndependent> dicList=dictionaryIndependentService.getByDicKey(es.getShareholdertype());
					if(null!=dicList && dicList.size()>0){
					shareholdertypeName=dicList.get(0).getItemValue();
					}
				}
				String  capitaltypeName="";
				if(null!=es.getCapitaltype() && !"".equals(es.getCapitaltype()) && !"null".equals(es.getCapitaltype()))
				{
					Dictionary dic=dictionaryService.get(Long.valueOf(es.getCapitaltype()));
					if(null!=dic){
					    capitaltypeName=dic.getItemValue();
					}
				}
				buff.append("{\"id\":");
				buff.append(es.getId());
	        	buff.append(",\"shareholdertype\":'");
	        	buff.append(es.getShareholdertype());
	        	buff.append("',\"shareholdercode\":'");
	        	buff.append(es.getShareholdercode());
	        	buff.append("',\"capitaltypeName\":'");
	        	buff.append(es.getShareholdercode());
	        	buff.append("',\"shareholdertypeName\":'");
	        	buff.append(shareholdertypeName);
	          	buff.append("',\"capitaltypeName\":'");
	        	buff.append(capitaltypeName);
	        	buff.append("',\"capital\":");
	        	buff.append(es.getCapital());
	        	buff.append(",\"capitaltype\":'");
	        	buff.append(es.getCapitaltype());
	        	buff.append("',\"share\":");
	        	buff.append(es.getShare());
	        	buff.append(",\"shareholder\":'");
	        	buff.append(es.getShareholder());
	        	buff.append("',\"remarks\":'");
	        	buff.append(es.getRemarks());
	        	buff.append("',\"enterpriseid\":");
	        	buff.append(es.getEnterpriseid());
	        	buff.append(",\"createTime\":'");
	        	if(null!=es.getCreateTime()){
	        		buff.append(es.getCreateTime());
	        	}
	        	
	        	buff.append("',\"logger\":");
	        	buff.append("{}");
	        	if(i<list.size()){
	        		buff.append("},");
	        	}
	        		
	        	
	        	
	        }
			buff.append("}]");
			buff.append("}");
			jsonString=buff.toString();
    	}else{
    		StringBuffer buff = new StringBuffer("{success:true,result:[");
			buff.append("]");
			buff.append("}");
			jsonString=buff.toString();
	
    	}
    	return SUCCESS;
    }
    /*
     * 删除股东信息
     */
    public String delete(){
    	try{
	    	EnterpriseShareequity enterpriseShareequity=enterpriseShareequityService.load(id);
	    	enterpriseShareequityService.remove(enterpriseShareequity);
    	    setJsonString("{success:true}");
    	}catch(Exception e){
    		setJsonString("{success:false}");
    		logger.error("ShareequityAction:"+e.getMessage());
    	}
    	
    	return SUCCESS;
    }
  
    public String getEnterprise(){
	    	GLGuaranteeloanProject glGuaranteeloanProject=glGuaranteeloanProjectService.get(projectId);
	    
	    	if(null!=glGuaranteeloanProject.getOppositeID() && glGuaranteeloanProject.getOppositeType().equals("company_customer")){
		    	Enterprise enterprise=enterpriseService.getById(glGuaranteeloanProject.getOppositeID().intValue());
		    	Map<String,Object> map=new HashMap<String,Object>();
		        map.put("data", enterprise);
		        jsonString=JsonUtil.mapEntity2Json(map, "enterprise");
	    	}else if(null!=glGuaranteeloanProject.getOppositeID() && glGuaranteeloanProject.getOppositeType().equals("person_customer")){
	    		Person person=personService.getById(glGuaranteeloanProject.getOppositeID().intValue());
	    		Map<String,Object> map=new HashMap<String,Object>();
	 	        map.put("data", person);
	 	       jsonString=JsonUtil.mapEntity2Json(map, "person");
	    	}

    	return SUCCESS;
    }
    public String getCustomer(){
    	JSONSerializer json = JsonUtil
		.getJSONSerializerDateByFormate("yyyy-MM-dd");
		if(null!=customerId && customerType.equals("企业")){
			Enterprise enterprise=enterpriseService.getById(customerId);
			enterpriseService.evict(enterprise);
			enterprise.setBpCustEntCashflowAndSaleIncomelist(null);
			enterprise.setBpCustEntLawsuitlist(null);
			enterprise.setBpCustEntUpanddownstreamlist(null);
			enterprise.setBpCustEntUpanddowncontractlist(null);
			enterprise.setBpCustEntPaytaxlist(null);
	    	Map<String,Object> map=new HashMap<String,Object>();
	        map.put("data", enterprise);
	        jsonString = json.serialize(map);
	       // jsonString=JsonUtil.mapEntity2Json(map, "enterprise");
		}else if(null!=customerId && customerType.equals("个人")){
			Person person=personService.getById(customerId);
    		Map<String,Object> map=new HashMap<String,Object>();
 	        map.put("data", person);
 	       //jsonString=JsonUtil.mapEntity2Json(map, "person");
 	       jsonString = json.serialize(map);
		}
		return SUCCESS;
	
    }
	private void doJson(Map<String, Object> map) {
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil
				.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString = buff.toString();
	}
    public String getByType(){
    	List<EnterpriseShareequity> list=enterpriseShareequityService.findByType(customerId, customerType);
    	StringBuffer buff = new StringBuffer("{success:true,result:[");
    	for(EnterpriseShareequity es:list){
    		buff.append("{\"id\":");
    		buff.append(es.getId());
    		buff.append(",\"personid\":");
    		buff.append(es.getPersonid());
    		buff.append(",\"enterpriseid\":");
    		buff.append(es.getEnterpriseid());
    		buff.append(",\"enterpriseName\":'");
    		if(es.getEnterpriseid()!=null){
    			Enterprise ep=enterpriseService.getById(es.getEnterpriseid());
    			buff.append(ep.getEnterprisename());
    		}
    		buff.append("',\"shareholdercode\":'");
    		buff.append(es.getShareholdercode());
    		buff.append("',\"capital\":");
    		buff.append(es.getCapital());
    		buff.append(",\"capitaltype\":'");
    		if(null!=es.getCapitaltype() && !"".equals(es.getCapitaltype())){
    			Dictionary dic=dictionaryService.get(Long.parseLong(es.getCapitaltype()));
    			buff.append(dic.getItemValue());
    		}
    		buff.append("',\"share\":");
    		buff.append(es.getShare());
    		buff.append(",\"remarks\":'");
    		buff.append(es.getRemarks());
    		buff.append("',\"createTime\":'");
    		if(null!=es.getCreateTime()){
    			buff.append(es.getCreateTime());
    		}
    		buff.append("'},");
    	}
    	buff.deleteCharAt(buff.length()-1);
    	buff.append("]}");
    	jsonString=buff.toString();
    	return SUCCESS;
    }
    
    public String getEntByLegalPersonId(){
    	List<Enterprise> list=enterpriseService.getListByLegalPersonId(personid);
    	for(Enterprise e:list){
    		if(null!=e.getOwnership() && !"".equals(e.getOwnership())){
    			Dictionary dic=dictionaryService.get(Long.parseLong(e.getOwnership()));
    			e.setOwnership(dic.getItemValue());
    		}
    	}
    	Type type=new TypeToken<List<Enterprise>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,result:");	
		JSONSerializer json = JsonUtil.getJSONSerializer();
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();
    	return SUCCESS;
    }
    
    //保存股东信息
    public String saveAll(){
    	String shareequity=this.getRequest().getParameter("shareequityData");
    	String entId=this.getRequest().getParameter("entId");
   		List<EnterpriseShareequity> listES=new ArrayList<EnterpriseShareequity>(); //股东信息
    	if(null != shareequity && !"".equals(shareequity)) {
				String[] shareequityArr = shareequity.split("@");
				 for(int i=0; i<shareequityArr.length; i++) {
						String str = shareequityArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						try{
							EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity)JSONMapper.toJava(parser.nextValue(),EnterpriseShareequity.class);
							
							if(enterpriseShareequity.getId()==null){
								enterpriseShareequity.setEnterpriseid(Integer.valueOf(entId));
								enterpriseShareequityService.save(enterpriseShareequity);
			   				 }else {
			   					 EnterpriseShareequity esPersistent=this.enterpriseShareequityService.load(enterpriseShareequity.getId());
			   					 BeanUtils.copyProperties(enterpriseShareequity, esPersistent,new String[] {"id","enterpriseid"});
			   					enterpriseShareequityService.merge(esPersistent);
			   				 }
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				 
				 setJsonString("{success:true}");
		}else{
			setJsonString("{success:false}");
		}
    	return SUCCESS;
    }

}
