package com.zhiwei.credit.action.creditFlow.customer.common;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.Constants;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.customer.InvestPersonService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

public class EnterpriseBankAction extends BaseAction{
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private CsBankService csBankService;
	@Resource
	private InvestEnterpriseService investEnterpriseService;
	@Resource
	private InvestPersonService investPersonService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	private Short isInvest;
	private int id ;
	private EnterpriseBank enterpriseBank;
	
	
	public EnterpriseBank getEnterpriseBank() {
		return enterpriseBank;
	}

	public void setEnterpriseBank(EnterpriseBank enterpriseBank) {
		this.enterpriseBank = enterpriseBank;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Short getIsInvest() {
		return isInvest;
	}

	public void setIsInvest(Short isInvest) {
		this.isInvest = isInvest;
	}

	public void queryList(){
		try{
			String isEnterpriseStr=this.getRequest().getParameter("isEnterpriseStr");
			int totalProperty = 0;
			List<EnterpriseBank> elist=enterpriseBankService.getList(id,isEnterpriseStr == null ?null:Short.valueOf(isEnterpriseStr), isInvest,null , null);
			if(null!=elist && elist.size()>0){
				totalProperty=elist.size();
			}
			List<EnterpriseBank> list = enterpriseBankService.getList(id,isEnterpriseStr == null ?null:Short.valueOf(isEnterpriseStr), isInvest,start , limit);
			for(EnterpriseBank bank:list){
				if(null!=bank.getBankid()&&!"".equals(bank.getBankid())){
					CsBank cb=csBankService.get(bank.getBankid());
					if(null!=cb){
						bank.setBankname(cb.getBankname());
					}
				}
			
			}
			JsonUtil.jsonFromList(list, totalProperty) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	public void add(){
		if(enterpriseBank.getIsInvest()!=null && enterpriseBank.getIsInvest() == 1) {
			if(enterpriseBank.getIsEnterprise()==0){
				enterpriseBank.setCompanyId(investEnterpriseService.get(Long.valueOf(enterpriseBank.getEnterpriseid())).getCompanyId());
			}else{
				enterpriseBank.setCompanyId(investPersonService.get(Long.valueOf(enterpriseBank.getEnterpriseid())).getCompanyId());
			}
		}else {
			if(enterpriseBank.getIsEnterprise()==0){
				enterpriseBank.setCompanyId(enterpriseService.getById(enterpriseBank.getEnterpriseid()).getCompanyId());
			}else{
				enterpriseBank.setCompanyId(personService.getById(enterpriseBank.getEnterpriseid()).getCompanyId());
			}
		}
		
		 
		try{
			enterpriseBankService.add(enterpriseBank) ;
			JsonUtil.responseJsonString("{success:true}");
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	/**
	 * 供其他系统调用
	 * @return
	 */
	public String  addByPost(){
		try{
		 Integer enterpriseid=Integer.valueOf(this.getRequest().getParameter("enterpriseid"));
		 Long bankid=Long.valueOf(this.getRequest().getParameter("bankid"));
		 String bankname=StringUtil.stringURLDecoderByUTF8(this.getRequest().getParameter("bankname"));
		 String accountnum=this.getRequest().getParameter("accountnum");
		 String name=StringUtil.stringURLDecoderByUTF8(this.getRequest().getParameter("name"));
		
		 String areaId=this.getRequest().getParameter("areaId");
		 String areaName=StringUtil.stringURLDecoderByUTF8(this.getRequest().getParameter("areaName"));
		 String  bankOutletsName=StringUtil.stringURLDecoderByUTF8(this.getRequest().getParameter("bankOutletsName"));
		 
		 Short openType=Short.valueOf("0");//0 个人1公司
		 Short accountType=Short.valueOf("0");
		 Short iscredit=Short.valueOf("0");//是否主贷款（0是1否）
		 String creditnum="";
		 String creditpsw="";
		 String remarks="线上投资客户体现账户";
		 Short openCurrency=Short.valueOf("0");//0本币1外币
		 Short isEnterprise=Short.valueOf("1");//是否是企业(0企业，1是个人)
		/**
		 * 0表示普通客户
		 * 1表示投资客户
		 * 3表示债权系统的投资客户
		 * 4线上投资客户
		 */
		 Short isInvest=Short.valueOf("4");//是否是投资客户(0是,1否)
		enterpriseBank =new EnterpriseBank();
		enterpriseBank.setAccountnum(accountnum);
		enterpriseBank.setAccountType(accountType);
		enterpriseBank.setAreaId(areaId);
		enterpriseBank.setAreaName(areaName);
		enterpriseBank.setBankid(bankid);
		enterpriseBank.setBankname(bankname);
		enterpriseBank.setBankOutletsName(bankOutletsName);
		enterpriseBank.setCompanyId(Long.valueOf(1));
		enterpriseBank.setCreditnum(creditnum);
		enterpriseBank.setCreditpsw(creditpsw);
		enterpriseBank.setEnterpriseid(enterpriseid);
		enterpriseBank.setIscredit(iscredit);
		enterpriseBank.setIsEnterprise(isEnterprise);
		enterpriseBank.setIsInvest(isInvest);
		enterpriseBank.setName(name);
		enterpriseBank.setOpenCurrency(openCurrency);
		enterpriseBank.setOpenType(openType);
		enterpriseBank.setRemarks(remarks);
		enterpriseBankService.save(enterpriseBank);
		jsonString = "{code:'"+Constants.CODE_SUCCESS+"',msg:'卡号填加成功'}";
		}catch(Exception e){
			e.printStackTrace();
			jsonString = "{code:'"+Constants.CODE_FAILED+"',msg:'卡号填加失败'}";
		}
		return SUCCESS;
	}
	public void update(){
		try{
		
			enterpriseBankService.update(enterpriseBank) ;
			JsonUtil.responseJsonString("{success:true}");
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public void find(){
		try{
			EnterpriseBank e =enterpriseBankService.getById(id) ;
			JsonUtil.jsonFromObject(e, true) ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	public void deleteRs(){
		try {
			EnterpriseBank bank=enterpriseBankService.getById(id);
			if(null!=bank){
				enterpriseBankService.remove(bank);
			}
			JsonUtil.jsonFromObject(null, true);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject(null, false);
		}
	}
	
	public String listbycheckbox(){
		PageBean<EnterpriseBank> pageBean=new PageBean<EnterpriseBank>(start, limit,getRequest());
		
		enterpriseBankService.querySomeList(pageBean);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pageBean.getTotalCounts()).append(",result:");
		JSONSerializer json=new JSONSerializer();
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{});
		buff.append(json.serialize(pageBean.getResult()));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}

	public void getCreditBank() {
		try{
			List<EnterpriseBank> list = enterpriseBankService.getBankList(id, Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("1")) ;
			EnterpriseBank eb = new EnterpriseBank();
			if(list != null && list.size()>0) {
				eb = list.get(0);
			}
			JsonUtil.jsonFromObject(eb, true);
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	public void getAccountBank() {
		try{
			List<EnterpriseBank> elist = enterpriseBankService.getBankList(id, Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("3"));
			if (null != elist && elist.size() > 0) {
				enterpriseBank = elist.get(0);
			}
			JsonUtil.jsonFromObject(enterpriseBank, true);
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	
	public void updateAccount(){
		try{
			Short iscredit = 0;
			enterpriseBank.setIscredit(iscredit);
			enterpriseBankService.update(enterpriseBank) ;
			JsonUtil.responseJsonString("{success:true}");
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
	public void queryAlreadyAccount(){
		try{
			String accountnum=this.getRequest().getParameter("accountnum");
			String accountId=this.getRequest().getParameter("id");
			Integer id =null;
			if(accountId!=null&&!accountId.equals("")){
				id = Integer.valueOf(accountId);
			}
			List<EnterpriseBank> list = enterpriseBankService.queryAlreadyAccount(id, accountnum);
			if(list!=null&&list.size()>0){
					
				JsonUtil.responseJsonString("{success:true,msg:true}");
			}else{
				JsonUtil.responseJsonString("{success:true,msg:false}");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}