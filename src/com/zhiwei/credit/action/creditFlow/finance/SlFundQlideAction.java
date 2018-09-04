package com.zhiwei.credit.action.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.File;
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

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;
import com.zhiwei.credit.model.system.DictionaryIndependent;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.finance.SlBankAccountService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundQlideService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.service.system.OrganizationService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;




/**
 * 
 * @author 
 *
 */
public class SlFundQlideAction extends BaseAction{
	@Resource
	private SlFundQlideService slFundQlideService;
	@Resource
	private SlBankAccountService slBankAccountService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private EnterpriseBankService enterpriseBankService ;
	@Resource
	private CsBankService csBankService;
	private SlBankAccount slBankAccount;
	

	
    private  Integer fundType;
	private SlFundQlide slFundQlide;
	private String excelsqlName;
	private File excelsql;
	private String plQlideJson;
	public Integer getFundType() {
		return fundType;
	}

	public void setFundType(Integer fundType) {
		this.fundType = fundType;
	}

	public String getPlQlideJson() {
		return plQlideJson;
	}

	public void setPlQlideJson(String plQlideJson) {
		this.plQlideJson = plQlideJson;
	}

	public SlBankAccount getSlBankAccount() {
		return slBankAccount;
	}

	public void setSlBankAccount(SlBankAccount slBankAccount) {
		this.slBankAccount = slBankAccount;
	}
	public SlBankAccountService getSlBankAccountService() {
		return slBankAccountService;
	}

	public void setSlBankAccountService(SlBankAccountService slBankAccountService) {
		this.slBankAccountService = slBankAccountService;
	}

	public String getExcelsqlName() {
		return excelsqlName;
	}

	public void setExcelsqlName(String excelsqlName) {
		this.excelsqlName = excelsqlName;
	}

	public File getExcelsql() {
		return excelsql;
	}

	public void setExcelsql(File excelsql) {
		this.excelsql = excelsql;
	}

	private Long fundQlideId;

	public Long getFundQlideId() {
		return fundQlideId;
	}

	public void setFundQlideId(Long fundQlideId) {
		this.fundQlideId = fundQlideId;
	}

	public SlFundQlide getSlFundQlide() {
		return slFundQlide;
	}

	public void setSlFundQlide(SlFundQlide slFundQlide) {
		this.slFundQlide = slFundQlide;
	}

	/**
	 * 显示列表
	 */
	public String list(){ 
		Long companyId=ContextUtil.getLoginCompanyId();
		int size=0;
		List<SlFundQlide> list=new ArrayList<SlFundQlide>();
    	Map<String,String> map = new HashMap<String,String>();
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    		
    	}
    	
		if(map.size()==4){
		 list= slFundQlideService.getsortAll(map);   //有问题aaaa，还得穿companyId过来，点那个款项对账就传那个款项的companyId
		size=slFundQlideService.getsortcount(map);
		}else{
			if(fundType==0){
				fundType=1;
			}else{
				fundType=0;
			}
			map.put("Q_fundType_SN_EQ", fundType.toString());
			 list= slFundQlideService.searchnotcheck(map);
			 size=slFundQlideService.searchnotchecksize(map);
			
		}
		for(SlFundQlide q:list){
			if(null==q.getIsCash()){
			   SlBankAccount a=new SlBankAccount();
			   List<SlBankAccount> list1=slBankAccountService.getbyaccount(q.getMyAccount());
			   if(list1.size()!=0){
					 a=list1.get(0);
					 CsBank cb=csBankService.get(a.getOpenBankId());
					 q.setBankName(cb.getBankname()+'-'+a.getName()+"-"+a.getAccount());
				//	 q.setOpenName(a.getName());
				 }
			}else{
				q.setBankName("现金账户");
				
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	public String list1(){

		Map<String,String> map = new HashMap<String,String>();
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    		
    	}
////		QueryFilter filter=new QueryFilter(getRequest());
////		if(map.get("sort")==null){
////			filter.addSorted("factDate", "DESC");
////		}
//		
//		List<SlFundQlide> list= slFundQlideService.getAll(filter);
    	map.put("isProject","所有");
    	List<SlFundQlide> list= slFundQlideService.search(map);
    	int count=slFundQlideService.searchsize(map);
		for(SlFundQlide q:list){
			if(null!=q.getCompanyId()){
				Organization organization=organizationService.get(q.getCompanyId());
				if(null!=organization){
					q.setOrgName(organization.getOrgName());
				}
			
			}
			if(q.getBankTransactionType() !=null && !q.getBankTransactionType().equals("")){
			
				List<DictionaryIndependent> dictionaryIndependent=dictionaryIndependentService.getByDicKey(q.getBankTransactionType());
			    q.setBankTransactionTypeName(dictionaryIndependent.get(0).getItemValue());
			
			}
			
			
			
			   SlBankAccount a=new SlBankAccount();
			   List<SlBankAccount> list1=slBankAccountService.getbyaccount(q.getMyAccount());
			   if(list1.size()!=0){
					
					 a=list1.get(0);
					 CsBank cb=csBankService.get(a.getOpenBankId());
					 if(null!=cb){
						 q.setBankName(cb.getBankname());
					 }
					 q.setOpenName(a.getName());
				 }
			   
			   SlBankAccount op=new SlBankAccount();
			   List<SlBankAccount> list3=slBankAccountService.getbyaccount(q.getOpAccount());
			   if(list3.size()!=0){
					 op=list3.get(0);
					 CsBank cb=csBankService.get(op.getOpenBankId());
					 q.setBankName(cb.getBankname());
					 q.setOpenName(op.getName());
				 }
		}
//		MyCompareqlidedec myCompareqlide=new MyCompareqlidedec();
//		Collections.sort(list,myCompareqlide);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(count).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"),new String[]{"factDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();
	return "list1success";
	}
	public String listbyaccount(){
		Map<String,String> map = new HashMap<String,String>();
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    		
    	}
		String myaccount=this.getRequest().getParameter("myaccount");
		Long companyId=ContextUtil.getLoginCompanyId();
    	List<SlFundQlide> list= slFundQlideService.listbyaccount(map);
    	int count=slFundQlideService.listbyaccountsize(map);
    	
 	   SlBankAccount a=new SlBankAccount();
	   List<SlBankAccount> list1=slBankAccountService.getbyaccount(myaccount);
	   String dir=map.get("dir");
	   String bankname="";
	   String opennmae="";
	   BigDecimal summoney=new BigDecimal(0);
	   if(list1.size()!=0){
			
			 a=list1.get(0);
			 CsBank cb=csBankService.get(a.getOpenBankId());
			/* bankname=cb.getBankname();
			 opennmae=a.getName();*/
			 summoney=a.getRawMoney();
		
	   }
	   BigDecimal frozenfinalMoney=new BigDecimal("0");
	   if(list.size()!=0){
		   SlFundQlide b=null;
		   if(null==dir ||dir.equals("ASC")){
			  b=list.get(0);
		   }else{
			   b=list.get(list.size()-1);
		   }
		 
			List<SlFundQlide> listall= slFundQlideService.listbyaccountall(map); //有问题
			BigDecimal incomeMoney=new BigDecimal("0");
			BigDecimal payMoney=new BigDecimal("0");
			
			for(SlFundQlide l:listall){
				if(l.getFactDate().getTime() <b.getFactDate().getTime()||(l.getFactDate().getTime()==b.getFactDate().getTime()&& l.getFundQlideId()<b.getFundQlideId())){
					if(null !=l.getIncomeMoney()){
					  incomeMoney=incomeMoney.add(l.getIncomeMoney());
					  if(null !=l.getBankTransactionType() && l.getBankTransactionType().equals("cautionmoneytype")){
						   frozenfinalMoney=frozenfinalMoney.add(l.getIncomeMoney());
					   }
					}
					if(null !=l.getPayMoney()){
					  payMoney=payMoney.add(l.getPayMoney());
					}
					
				}else{
					
					break;
				}
			}
			  summoney=summoney.add(incomeMoney).subtract(payMoney);
	  
	  
	   if(null==dir ||dir.equals("ASC")){
			for(SlFundQlide q:list){
				if(null!=q.getCompanyId()){
					Organization organization=organizationService.get(q.getCompanyId());
					if(null!=organization){
						q.setOrgName(organization.getOrgName());
					}
				
				}
			       if(q.getBankTransactionType() !=null&& !q.getBankTransactionType().equals("")){
							List<DictionaryIndependent> dictionaryIndependent=dictionaryIndependentService.getByDicKey(q.getBankTransactionType());
						    q.setBankTransactionTypeName(dictionaryIndependent.get(0).getItemValue());
						
						}
				
				
			
			       q.setBankName(bankname);
					 q.setOpenName(opennmae);
				  
				   
				   SlBankAccount op=new SlBankAccount();
				   List<SlBankAccount> list3=slBankAccountService.getbyaccount(q.getOpAccount());
				   if(list3.size()!=0){
						 op=list3.get(0);
						 CsBank cb=csBankService.get(op.getOpenBankId());
						 q.setBankName(cb.getBankname());
						 q.setOpenName(op.getName());
					 }
				   
			
				
					if(null !=q.getPayMoney()){
						summoney=summoney.subtract(q.getPayMoney());
						q.setFinalMoney(summoney);
					}else{
						BigDecimal im=new BigDecimal("0");
						if(q.getIncomeMoney() !=null){
							im=q.getIncomeMoney();
						}
						summoney=summoney.add(im);
						q.setFinalMoney(summoney);
						if(null !=q.getBankTransactionType() && q.getBankTransactionType().equals("cautionmoneytype")){
							   frozenfinalMoney=frozenfinalMoney.add(im);
						   }
					}
					q.setFrozenfinalMoney(frozenfinalMoney);
					q.setSurplusfinalMoney(q.getFinalMoney().subtract(q.getFrozenfinalMoney()));
					
			}
		   
	   }else{
		   
			for(int k=list.size()-1;k>=0;k--){
				SlFundQlide q=list.get(k);
				if(null!=q.getCompanyId()){
					Organization organization=organizationService.get(q.getCompanyId());
					if(null!=organization){
						q.setOrgName(organization.getOrgName());
					}
				
				}
			       if(q.getBankTransactionType() !=null&& !q.getBankTransactionType().equals("")){
							List<DictionaryIndependent> dictionaryIndependent=dictionaryIndependentService.getByDicKey(q.getBankTransactionType());
						    q.setBankTransactionTypeName(dictionaryIndependent.get(0).getItemValue());
						
						}
				
				
			
			       q.setBankName(bankname);
					 q.setOpenName(opennmae);
				  
				   
				   SlBankAccount op=new SlBankAccount();
				   List<SlBankAccount> list3=slBankAccountService.getbyaccount(q.getOpAccount());
				   if(list3.size()!=0){
						 op=list3.get(0);
						 CsBank cb=csBankService.get(op.getOpenBankId());
						 q.setBankName(cb.getBankname());
						 q.setOpenName(op.getName());
					 }
				   
			
				
					if(null !=q.getPayMoney()){
						summoney=summoney.subtract(q.getPayMoney());
						q.setFinalMoney(summoney);
					}else{
						BigDecimal im=new BigDecimal("0");
						if(q.getIncomeMoney() !=null){
							im=q.getIncomeMoney();
						}
						summoney=summoney.add(im);
						q.setFinalMoney(summoney);
						if(null !=q.getBankTransactionType() && q.getBankTransactionType().equals("cautionmoneytype")){
							   frozenfinalMoney=frozenfinalMoney.add(im);
						   }
					}
					q.setFrozenfinalMoney(frozenfinalMoney);
					q.setSurplusfinalMoney(q.getFinalMoney().subtract(q.getFrozenfinalMoney()));
					
			}
	   }
	   }
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(count).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd HH:mm:ss"),new String[]{"factDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();
	return "list1success";
	}
	public String listcash(){

		Map<String,String> map = new HashMap<String,String>();
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    		
    	}
    	List<SlFundQlide> list= slFundQlideService.searchcash(map);
    	int count=slFundQlideService.searchcashsize(map);
	
    	for(SlFundQlide i:list){
    		if(null!=i.getCompanyId()){
				Organization organization=organizationService.get(i.getCompanyId());
				if(null!=organization){
					i.setOrgName(organization.getOrgName());
				}
			
			}
    		if(i.getBankTransactionType() !=null&& !i.getBankTransactionType().equals("")){
    			
				List<DictionaryIndependent> dictionaryIndependent=dictionaryIndependentService.getByDicKey(i.getBankTransactionType());
			    i.setBankTransactionTypeName(dictionaryIndependent.get(0).getItemValue());
			
			}
    		String strs=ContextUtil.getBranchIdsStr();//39,40
    		String[] companyIdarray=null;
    		if(strs!=null&&!"".equals(strs)){
    			companyIdarray=strs.split(",");
    			
    			
    		}else{
    			String s ="1";
    			companyIdarray=s.split(",");

    		}
    		BigDecimal frozenfinalMoney=new BigDecimal("0");  //有问题sssss 公司id
    		List<SlFundQlide> listall= slFundQlideService.listcashall(map);
			BigDecimal incomeMoney=new BigDecimal("0");
			BigDecimal payMoney=new BigDecimal("0");
			 if (companyIdarray != null) {
					for (String companyid : companyIdarray) {
						for(SlFundQlide l:listall){       
							//并且分公司id要相同
							if(l.getFactDate().getTime() <i.getFactDate().getTime() &&l.getCompanyId().toString().equals(companyid)){
								if(null !=l.getIncomeMoney()){
								  incomeMoney=incomeMoney.add(l.getIncomeMoney());
								  if(null !=l.getBankTransactionType() && l.getBankTransactionType().equals("cautionmoneytype")){
									   frozenfinalMoney=frozenfinalMoney.add(l.getIncomeMoney());
								   }
								}
								  if(null !=l.getPayMoney()){
									  payMoney=payMoney.add(l.getPayMoney());
									}
								
							}
						}
					}
			}
			if(null !=i.getPayMoney()){
				i.setFinalMoney(incomeMoney.subtract(payMoney).subtract(i.getPayMoney()));
			}else{
				i.setFinalMoney(incomeMoney.subtract(payMoney).add(i.getIncomeMoney()));
				if(null !=i.getBankTransactionType() && i.getBankTransactionType().equals("cautionmoneytype")){
					   frozenfinalMoney=frozenfinalMoney.add(i.getIncomeMoney());
				   }
			}
    		
			i.setFrozenfinalMoney(frozenfinalMoney);
			i.setSurplusfinalMoney(i.getFinalMoney().subtract(i.getFrozenfinalMoney()));
    	}
    	
    	
    	
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(count).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();
	return "list1success";
	}
	
 public String MoneyDetail(){
		 
		 
		 
		 
		 return SUCCESS;
	 }
	/**
	 * 批量删除
	 * @return
	 */
 	@LogResource(description = "删除资金流水记录")
	public String multiDel(){
		
		String[] ids=getRequest().getParameterValues("ids");
		//String[] ids=getRequest().getParameter("ids").split(",");
		if(ids!=null){
			for(String id:ids){
				slFundQlide=slFundQlideService.get(new Long(id));
				if(slFundQlide.getAfterMoney().compareTo(new BigDecimal(0.00))==0){
					slFundQlideService.remove(new Long(id));
					//jsonString="{success:true}";
				}else{
					//jsonString="{success:false}";
					
				}
//				return	"failure";
				 
			}
		}
		accountFinalMoney();
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		SlFundQlide slFundQlide=slFundQlideService.get(fundQlideId);
	//	slFundQlide.setCurrency(dictionaryService.get(Long.getLong(slFundQlide.getCurrency())).getItemValue());
		 SlBankAccount a=new SlBankAccount();
		   List<SlBankAccount> list1=slBankAccountService.getbyaccount(slFundQlide.getMyAccount());
		   if(list1.size()!=0){
				 a=list1.get(0);
				 CsBank cb=csBankService.get(a.getOpenBankId());
				 slFundQlide.setBankName(cb.getBankname());
				 slFundQlide.setOpenName(a.getName());
			 }
			
			StringBuffer buff = new StringBuffer("{success:true,'data':");
			JSONSerializer json = JsonUtil.getJSONSerializer(
					"factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"factDate"});
			buff.append(json.serialize(slFundQlide));
			buff.append("}");
			jsonString = buff.toString();
			return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	@LogResource(description = "添加业务往来资金记录")
	public String save(){
		Long companyId=ContextUtil.getLoginCompanyId();
		if(slFundQlide.getFundQlideId()==null){
			if(null !=slFundQlide.getPayMoney()){
				slFundQlide.setNotMoney(slFundQlide.getPayMoney());
			}else{
				slFundQlide.setNotMoney(slFundQlide.getIncomeMoney());
				
			}
			slFundQlide.setCompanyId(companyId);
			slFundQlide.setAfterMoney(new BigDecimal(0.00));
			slFundQlide.setOperTime(new Date());
			
		    int pl=0; 	
		    List enterpriseBanklist= enterpriseBankService.getAll();
		    for(int i=0;i<(null !=enterpriseBanklist?enterpriseBanklist.size():0);i++){
		    	EnterpriseBank vpcc = (EnterpriseBank)enterpriseBanklist.get(i);
		    	if(slFundQlide.getOpAccount().equals(vpcc.getAccountnum())){
		    		pl++;
		    		break;
		    	}
		    	
		    }
		    if(pl==0&&!slFundQlide.getOpAccount().equals("")){
		    	EnterpriseBank enterpriseBank=new EnterpriseBank();
		    	enterpriseBank.setAccountnum(slFundQlide.getOpAccount());
		    	enterpriseBank.setName(slFundQlide.getOpOpenName());
		    	enterpriseBank.setBankname(slFundQlide.getOpBankName());
		    	enterpriseBank.setCompanyId(companyId);
		    	enterpriseBankService.save(enterpriseBank);
		    }
		    	
			
			slFundQlideService.save(slFundQlide);
		}else{
			SlFundQlide orgSlFundQlide=slFundQlideService.get(slFundQlide.getFundQlideId());
		//	slFundQlide.setCurrency(dictionaryService.get(Long.getLong(slFundQlide.getCurrency())).getItemValue());
			if(null !=slFundQlide.getPayMoney()){
				slFundQlide.setNotMoney(slFundQlide.getPayMoney());
				orgSlFundQlide.setIncomeMoney(null);
			}else{
				slFundQlide.setNotMoney(slFundQlide.getIncomeMoney());
				orgSlFundQlide.setPayMoney(null);
			}
			slFundQlide.setEditTime(new Date());
			
		    int pl=0; 	
		    List enterpriseBanklist= enterpriseBankService.getAll();
		    for(int i=0;i<enterpriseBanklist.size();i++){
		    	EnterpriseBank vpcc = (EnterpriseBank)enterpriseBanklist.get(i);
		    	if(slFundQlide.getOpAccount().equals(vpcc.getAccountnum())){
		    		pl++;
		    		break;
		    	}
		    	
		    }
		    if(pl==0&&!slFundQlide.getOpAccount().equals("")){
		    	EnterpriseBank enterpriseBank=new EnterpriseBank();
		    	enterpriseBank.setAccountnum(slFundQlide.getOpAccount());
		    	enterpriseBank.setName(slFundQlide.getOpOpenName());
		    	enterpriseBank.setBankname(slFundQlide.getOpBankName());
		    	enterpriseBank.setCompanyId(companyId);
		    	enterpriseBankService.save(enterpriseBank);
		    }
			
			try{
				BeanUtil.copyNotNullProperties(orgSlFundQlide, slFundQlide);
				slFundQlideService.save(orgSlFundQlide);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		accountFinalMoney();
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	@LogResource(description = "添加已知账户资金互转记录")
public String saveInternalqlide(){
	   Long companyId=ContextUtil.getLoginCompanyId();
		if(slFundQlide.getFundQlideId()==null){
			slFundQlide.setCompanyId(companyId);
			slFundQlide.setNotMoney(slFundQlide.getPayMoney());
			slFundQlide.setAfterMoney(new BigDecimal(0.00));
			slFundQlide.setOperTime(new Date());
			slFundQlide.setBankTransactionType("normalTransactionType");
		
			SlFundQlide slFundQlide1=new SlFundQlide();
			slFundQlide1.setCompanyId(companyId);
			slFundQlide1.setMyAccount(slFundQlide.getOpAccount());
			slFundQlide1.setOpAccount(slFundQlide.getMyAccount());
			slFundQlide1.setAfterMoney(new BigDecimal(0.00));
			slFundQlide1.setNotMoney(slFundQlide.getPayMoney());
			slFundQlide1.setOperTime(new Date());
			SlBankAccount a=new SlBankAccount();
			   List<SlBankAccount> list1=slBankAccountService.getbyaccount(slFundQlide.getMyAccount());
			   if(list1.size()!=0){
					 a=list1.get(0);
					 CsBank cb=csBankService.get(a.getOpenBankId());
					 slFundQlide1.setOpBankName(a.getBankName());
					 slFundQlide1.setOpenName(a.getName());
				 }
			slFundQlide1.setTransactionType("(内转)"+slFundQlide.getTransactionType());
			slFundQlide1.setCurrency(slFundQlide.getCurrency());
			slFundQlide1.setIncomeMoney(slFundQlide.getPayMoney());
			slFundQlide1.setIsProject(slFundQlide.getIsProject());
			slFundQlide1.setFactDate(slFundQlide.getFactDate());
			slFundQlide1.setBankTransactionType("normalTransactionType");
			
			slFundQlideService.save(slFundQlide);
			slFundQlideService.save(slFundQlide1);
			
		}
		accountFinalMoney();
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
public String savecashqlide(){
	Long companyId=ContextUtil.getLoginCompanyId();
	if(slFundQlide.getFundQlideId()==null){
		slFundQlide.setCompanyId(companyId);
		if(null !=slFundQlide.getPayMoney()){
			slFundQlide.setNotMoney(slFundQlide.getPayMoney());
		}else{
			slFundQlide.setNotMoney(slFundQlide.getIncomeMoney());
			
		}
		slFundQlide.setAfterMoney(new BigDecimal(0.00));
		slFundQlide.setOperTime(new Date());
		slFundQlide.setMyAccount("cahsqlideAccount");
		slFundQlide.setIsCash("cash");
		slFundQlideService.save(slFundQlide);
	}else{
		SlFundQlide orgSlFundQlide=slFundQlideService.get(slFundQlide.getFundQlideId());
		if(null !=slFundQlide.getPayMoney()){
			slFundQlide.setNotMoney(slFundQlide.getPayMoney());
			orgSlFundQlide.setIncomeMoney(null);
		}else{
			slFundQlide.setNotMoney(slFundQlide.getIncomeMoney());
			orgSlFundQlide.setPayMoney(null);
		}
		slFundQlide.setEditTime(new Date());
		try{
			BeanUtil.copyNotNullProperties(orgSlFundQlide, slFundQlide);
			slFundQlideService.save(orgSlFundQlide);
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
	}
	accountFinalMoney();
	setJsonString("{success:true}");
	return SUCCESS;
	
}
	public String savealotcashqlide(){
		Long companyId=ContextUtil.getLoginCompanyId();	

	if (null != plQlideJson && !"".equals(plQlideJson)) {

		String[] shareequityArr = plQlideJson.split("@");
     
	for (int i = 0; i < shareequityArr.length; i++) {
			String str = shareequityArr[i];
			JSONParser parser = new JSONParser(new StringReader(str));
			try {
				SlFundQlide slFundQlide1 = (SlFundQlide) JSONMapper.toJava(parser.nextValue(), SlFundQlide.class);
					BigDecimal lin = new BigDecimal(0.00);
			        if(slFundQlide1.getIncomeMoney().compareTo(lin)==0){
			        	slFundQlide1.setNotMoney(slFundQlide1.getPayMoney());
			        	slFundQlide1.setIncomeMoney(null);
			        }else{
			        	slFundQlide1.setNotMoney(slFundQlide1.getIncomeMoney());
			        	slFundQlide1.setPayMoney(null);
			        	
			        } 
			    	slFundQlide1.setCompanyId(companyId);
			        slFundQlide1.setAfterMoney(new BigDecimal(0));
			        slFundQlide1.setOperTime(new Date());
			        slFundQlide1.setMyAccount("cahsqlideAccount");
			        slFundQlide1.setIsCash("cash");
			    	slFundQlideService.save(slFundQlide1);
			    	
			    	
			
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
	}
	setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String savealotqlide(){
		
		Long companyId=ContextUtil.getLoginCompanyId();
		String myaccount=this.getRequest().getParameter("myaccount");

		if (null != plQlideJson && !"".equals(plQlideJson)) {

			String[] shareequityArr = plQlideJson.split("@");
	     
		for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));

				try {

					SlFundQlide slFundQlide1 = (SlFundQlide) JSONMapper.toJava(parser.nextValue(), SlFundQlide.class);
						BigDecimal lin = new BigDecimal(0.00);
				        if(slFundQlide1.getIncomeMoney().compareTo(lin)==0){
				        	slFundQlide1.setNotMoney(slFundQlide1.getPayMoney());
				        	slFundQlide1.setIncomeMoney(null);
				        }else{
				        	slFundQlide1.setNotMoney(slFundQlide1.getIncomeMoney());
				        	slFundQlide1.setPayMoney(null);
				        	
				        } 
				        slFundQlide1.setAfterMoney(new BigDecimal(0));
				        slFundQlide1.setOperTime(new Date());
				        slFundQlide1.setMyAccount(myaccount);
				        
				        
				        int pl=0; 	
					    List enterpriseBanklist= enterpriseBankService.getAll();
					    for(int j=0;j<enterpriseBanklist.size();j++){
					    	EnterpriseBank vpcc = (EnterpriseBank)enterpriseBanklist.get(j);
					    	if(slFundQlide1.getOpAccount().equals(vpcc.getAccountnum())){
					    		pl++;
					    		break;
					    	}
					    	
					    }
					    if(pl==0&&!slFundQlide1.getOpAccount().equals("")){
					    	EnterpriseBank enterpriseBank=new EnterpriseBank();
					    	enterpriseBank.setAccountnum(slFundQlide1.getOpAccount());
					    	enterpriseBank.setName(slFundQlide1.getOpOpenName());
					    	enterpriseBank.setBankname(slFundQlide1.getOpBankName());
					    	enterpriseBank.setCompanyId(companyId);
					    	enterpriseBankService.save(enterpriseBank);
					    }
						slFundQlide1.setCompanyId(companyId);
				    	slFundQlideService.save(slFundQlide1);
				    
				    	
				    	
				
				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		}
		accountFinalMoney();
		setJsonString("{success:true}");
			return SUCCESS;
			
		}
	public void accountFinalMoney(){
		
		
		List<SlBankAccount> accountList=slBankAccountService.getallbycompanyId(null);
		List<SlFundQlide> qlidelist= slFundQlideService.getallbycompanyId();
		List<SlFundQlide> sortlist= new ArrayList<SlFundQlide>();
		for(SlBankAccount a:accountList){
			a.setFinalMoney(a.getRawMoney());
			for(SlFundQlide q:qlidelist){
				
				if(q.getMyAccount().equals(a.getAccount()) && q.getCompanyId().equals(a.getCompanyId())){ //不仅账户要相同而且 分公司要相同才是同个
					if(q.getIncomeMoney()!=null)
					{ a.setFinalMoney(a.getFinalMoney().add(q.getIncomeMoney()));
					}
					if(q.getPayMoney()!=null){
						a.setFinalMoney(a.getFinalMoney().subtract(q.getPayMoney()));
					}
					sortlist.add(q);
					
				}
				
			}
			
			
			 if(null !=sortlist &&sortlist.size() !=0){
				 MyCompareqlide comp=new MyCompareqlide();
				 Collections.sort(sortlist,comp);
				 a.setFinalDate(sortlist.get(sortlist.size()-1).getFactDate());
			 }
			 sortlist.clear();
			slBankAccountService.save(a);
			
		}
		
	}



	
	
}
