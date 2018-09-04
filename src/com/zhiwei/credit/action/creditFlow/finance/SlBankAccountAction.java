package com.zhiwei.credit.action.creditFlow.finance;

/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
 */
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import java.util.Map;


import javax.annotation.Resource;

import java.math.BigDecimal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;
import com.zhiwei.credit.model.system.CsDicAreaDynam;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.finance.SlBankAccountService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundQlideService;
import com.zhiwei.credit.service.system.CsDicAreaDynamService;
import com.zhiwei.credit.service.system.OrganizationService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * 
 * @author
 * 
 */
public class SlBankAccountAction extends BaseAction {
	@Resource
	private SlBankAccountService slBankAccountService;
	
	private SlBankAccount slBankAccount;
	
	private Long bankId;
	private Long bankAccountId;
	@Resource
	private SlFundQlideService slFundQlideService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private CsBankService csBankService;

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	

	public Long getBankAccountId() {
		return bankAccountId;
	}

	public void setBankAccountId(Long bankAccountId) {
		this.bankAccountId = bankAccountId;
	}

	public SlBankAccount getSlBankAccount() {
		return slBankAccount;
	}

	public void setSlBankAccount(SlBankAccount slBankAccount) {
		this.slBankAccount = slBankAccount;
	}

	/**
	 * 显示列表
	 */
	public String list() {
		PagingBean pb = new PagingBean(start, limit);
		Map<String,String> map = new HashMap<String,String>();
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    		
    	}
		
		List<SlBankAccount> list = slBankAccountService.getallbycompanyId(pb, map);
		int size=slBankAccountService.getallbycompanyId(null, map)==null?0:slBankAccountService.getallbycompanyId(null, map).size();
		List<SlFundQlide> qlidelist= slFundQlideService.getAll();
		List<SlFundQlide> sortlist= new ArrayList<SlFundQlide>();
		for (SlBankAccount l : list) {
			if(null!=l.getCompanyId()){
				Organization organization=organizationService.get(l.getCompanyId());
				if(null!=organization){
					l.setOrgName(organization.getOrgName());
				}
			
			}
			CsBank cb=csBankService.get(l.getOpenBankId());
			if(null!=cb){
				l.setBankName(cb.getBankname());
			}
			

			
		
			BigDecimal frozenfinalMoney=new BigDecimal("0");
				l.setFinalMoney(l.getRawMoney());
				for(SlFundQlide q:qlidelist){
					if(q.getMyAccount().equals(l.getAccount())){
						if(q.getIncomeMoney()!=null)
						{ l.setFinalMoney(l.getFinalMoney().add(q.getIncomeMoney()));
						 if(null !=q.getBankTransactionType() &&q.getBankTransactionType().equals("cautionmoneytype")){
							   frozenfinalMoney=frozenfinalMoney.add(q.getIncomeMoney());
						   }
						}
						if(q.getPayMoney()!=null){
							l.setFinalMoney(l.getFinalMoney().subtract(q.getPayMoney()));
						}
						
					}
					
				}
				
				l.setFrozenfinalMoney(frozenfinalMoney);
				l.setSurplusfinalMoney(l.getFinalMoney().subtract(l.getFrozenfinalMoney()));
				slBankAccountService.save(l);
		}


		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("finalDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"finalDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();

		return SUCCESS;
	}
	public String selectlist() {  //固定的companyId
		Map<String,String> map = new HashMap<String,String>();
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    	}
    	if(null==map.get("sort")){
    	map.put("sort", "csDicAreaDynam.remarks");
    	map.put("dir", "DESC");
    	}else{
    		if(map.get("sort").endsWith("bankName")){
    			
    			map.put("sort", "csDicAreaDynam.remarks");
    		}
    		
    		
    	}
    	Long companyId=ContextUtil.getLoginCompanyId();
    	if(null!=companyId){
    	map.put("companyId", companyId.toString());
    	}
		List<SlBankAccount> list = slBankAccountService.getall(map);
		int a=slBankAccountService.getallsize(map);
		
		for (SlBankAccount l : list) {
			
			CsBank cb=csBankService.get(l.getOpenBankId());
			if(null!=cb){
				l.setBankName(cb.getBankname());
				//l.setSumbankname(cb.getBankname());
			}
			
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(a).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("finalDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"finalDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();

		return SUCCESS;
	}
	public String selectlist1() { //管控的id
		PagingBean pb = new PagingBean(start, limit);
		Map<String,String> map = new HashMap<String,String>();
		Enumeration paramEnu= getRequest().getParameterNames();
    	while(paramEnu.hasMoreElements()){
    		String paramName=(String)paramEnu.nextElement();
    			String paramValue=(String)getRequest().getParameter(paramName);
    			map.put(paramName, paramValue);
    	}
    	if(null==map.get("sort")){
    	map.put("sort", "openBankId");
    	map.put("dir", "DESC");
    	}else{
    		if(map.get("sort").endsWith("bankName")){
    			
    			map.put("sort", "openBankId");
    		}
    		
    		
    		
    	}
    
		List<SlBankAccount> list = slBankAccountService.selectbycompanyId(pb, map);
		int a=slBankAccountService.selectbycompanyId(null, map).size();
		
		for (SlBankAccount l : list) {
			if(null!=l.getCompanyId()){
				Organization organization=organizationService.get(l.getCompanyId());
				if(null!=organization){
					l.setOrgName(organization.getOrgName());
				}
			
			}
			CsBank cb=csBankService.get(l.getOpenBankId());
			if(null!=cb){
				l.setBankName(cb.getBankname());
			}
			
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(a).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("finalDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"finalDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();

		return SUCCESS;
	}
	/**
	 * 批量删除
	 * 
	 * @return
	 */
	@LogResource(description = "删除银行账户")
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				slBankAccountService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		SlBankAccount slBankAccount = slBankAccountService.get(bankAccountId);
	/*	slBankAccount.setBankId(slBankAccount.getCsDicAreaDynam().getId());
		slBankAccount.setBankName(slBankAccount.getCsDicAreaDynam()
				.getRemarks());*/
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slBankAccount));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 * 
	 * 
	 */
	@LogResource(description = "添加或修改银行账户")
	public String save() {
		Long companyId=ContextUtil.getLoginCompanyId();
		int i=0;
		if (slBankAccount.getBankAccountId() == null) {
			slBankAccount.setCompanyId(companyId);
			slBankAccount.setFinalMoney(slBankAccount.getRawMoney());
			slBankAccount.setFinalDate(slBankAccount.getRecordTime());
			slBankAccount.setAccount(slBankAccount.getAccount().trim());//去掉末尾的空格
			/*CsDicAreaDynam csDicAreaDynam = csDicAreaDynamService.get(bankId);
			slBankAccount.setCsDicAreaDynam(csDicAreaDynam);*/
			List<SlBankAccount>  list=slBankAccountService.getallbycompanyId(null);
			for(SlBankAccount s:list){
				if(slBankAccount.getAccount().equals(s.getAccount())){
					i++;
				}
			}
			if(i==0){
				slBankAccountService.save(slBankAccount);
			}
		} else {
			SlBankAccount orgSlBankAccount = slBankAccountService.get(slBankAccount.getBankAccountId());
		
			try {
				
				List<SlBankAccount>  list=slBankAccountService.getallbycompanyId(null);
				SlBankAccount slBankAccount1=new SlBankAccount();
				for(SlBankAccount s:list){
					if(slBankAccount.getBankAccountId()==s.getBankAccountId()){
						slBankAccount1=s;
					}
				}
				list.remove(slBankAccount1);
				for(SlBankAccount s:list){
					if(slBankAccount.getAccount().equals(s.getAccount())){
						i++;
					}
				}
				if(i==0){
					slBankAccount.setAccount(slBankAccount.getAccount().trim());//去掉末尾的空格
					BeanUtil.copyNotNullProperties(orgSlBankAccount, slBankAccount);
					slBankAccountService.save(orgSlBankAccount);
				}
				
			} catch (Exception ex) {

				logger.error(ex.getMessage());
				System.out.print(ex.getMessage());
			}
		}
		setJsonString("{success:true,exsit:false}");
		if(i>0){
			setJsonString("{success:true,exsit:true}");	
		}
		
		accountFinalMoney();
		return SUCCESS;

	}
public void accountFinalMoney(){
		
		
		List<SlBankAccount> accountList=slBankAccountService.getAll();
		List<SlFundQlide> qlidelist= slFundQlideService.getAll();
		List<SlFundQlide> sortlist= new ArrayList<SlFundQlide>();
		for(SlBankAccount a:accountList){
			a.setFinalMoney(a.getRawMoney());
			for(SlFundQlide q:qlidelist){
				
				if(q.getMyAccount().equals(a.getAccount())){
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
			
			slBankAccountService.save(a);
			
		}
		
	}
}
