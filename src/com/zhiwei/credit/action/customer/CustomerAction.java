package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.customer.Customer;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.customer.VCustomer;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.customer.CustomerService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.customer.InvestPersonService;
import com.zhiwei.credit.service.customer.VCustomerService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
/**
 * 
 * @author csx
 *
 */
public class CustomerAction extends BaseAction{
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	@Resource
	private CustomerService customerService;
	@Resource
	private VCustomerService vCustomerService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private OrganizationService organizationService;
	@Resource 
	private InvestPersonService investPersonService;
	@Resource 
	private InvestEnterpriseService investEnterpriseService;
	@Resource 
	private CsInvestmentpersonService csInvestmentpersonService;
	private Customer customer;
	
	private Long customerId;

	private String customerNo;
	private String idCustomerTypeStr;
	private String userIdStr;
	private String shopId;
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getIdCustomerTypeStr() {
		return idCustomerTypeStr;
	}

	public void setIdCustomerTypeStr(String idCustomerTypeStr) {
		this.idCustomerTypeStr = idCustomerTypeStr;
	}

	public String getUserIdStr() {
		return userIdStr;
	}

	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}

	
	public String getCustName(){
		String customerName = null;
		Person person = personService.getById(Integer.valueOf(customerId.toString()));
		if(null!=person){
			customerName = person.getName();
		}
		setJsonString(customerName);
		return SUCCESS;
	}
	
	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<Customer> list= customerService.getAll(filter);
		
		Type type=new TypeToken<List<Customer>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				customerService.remove(new Long(id));
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
		Customer customer=customerService.get(customerId);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(customer));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		boolean pass = false;
		StringBuffer buff = new StringBuffer("{");
		if(customer.getCustomerId()==null){//新增客户时的验证
			if(customerService.checkCustomerNo(customer.getCustomerNo())){
									pass = true;
			}else buff.append("msg:'客户已存在,请重新填写.',rewrite:true,");
		}else{
			pass = true;
		}
		if(pass){
			customerService.save(customer);
			buff.append("success:true,customerId:"+customer.getCustomerId()+"}");
		}else{
			buff.append("failure:true}");
		}
		setJsonString(buff.toString());
		return SUCCESS;
	}
	
	/**
	 * 系统按时间生成客户编号给用户
	 * @return
	 */
	public String number(){
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss-SSS");
		String customerNo = date.format(new Date());
		setJsonString("{success:true,customerNo:'"+customerNo+"'}");
		return SUCCESS;
	}
	
	/**
	 * 验证客户编号是否可用
	 * @return
	 */
	public String check(){
		boolean pass = false;
		pass = customerService.checkCustomerNo(customerNo);
		if(pass){
			setJsonString("{success:true,pass:true}");
		}else{
			setJsonString("{success:true,pass:false}");
		}
		return SUCCESS;
	}
	
	/**
	 * 客户授权管理列表
	 * @author lisl
	 * @return
	 */
	public String customerGrantManageList(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<VCustomer> list= vCustomerService.getAll(filter);
		
		for(VCustomer vc : list) {
			String appuers = "";
			String depNames = "";
			if(null != vc.getBelongedId() && !"".equals(vc.getBelongedId())) {
				String [] appstr = vc.getBelongedId().split(",");
				for(String belongedId : appstr) {
					Organization o = organizationService.get(Long.valueOf(belongedId));
					if(null != o) {
						depNames += o.getOrgName() + ",";
					}
				}
				Set<AppUser> userSet = this.appUserService.getAppUserByStr(appstr);
 				for(AppUser s : userSet){
 					if(1 == s.getDelFlag()) {//用户已被逻辑删除
 						appuers = appuers + "<font color='red'>"+s.getFamilyName()+"</font>"+",";
 					}else {
 						appuers = appuers + s.getFamilyName()+",";
 					}
				}
			}
			if(appuers.length()>0) {
				appuers = appuers.substring(0,appuers.length() - 1);
			}
			if(depNames.length()>0) {
				depNames = depNames.substring(0,depNames.length() - 1);
			}
			vc.setBelonger(appuers);
			vc.setDepName(depNames);
		}
		
		Type type=new TypeToken<List<VCustomer>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		for(VCustomer v : list){
			v.setIdStr(v.getId().toString());
		}
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 授权共享人
	 * @author lisl
	 */
	public String grantBelonger() {
		String [] idCustomerTypes = idCustomerTypeStr.split(",");
		for(String idCustomerType : idCustomerTypes) {
			String [] str = idCustomerType.split(";");
			try{
				if(str[1].equals("company_customer")) {
					Enterprise enterprise = enterpriseService.getById(Integer.valueOf(str[0]));
					if(idCustomerTypes.length > 1) {
						if(!"".equals(enterprise.getBelongedId())&& null !=enterprise.getBelongedId()) {
							if(!"".equals(userIdStr)) {
								String oldUserIdsStr = enterprise.getBelongedId();
								String []userIds = userIdStr.split(",");
								for(String userId : userIds) {
									if(oldUserIdsStr.indexOf(userId) < 0) {
										oldUserIdsStr += "," + userId;
									}
								}
								enterprise.setBelongedId(oldUserIdsStr);
							}else {
								enterprise.setBelongedId(userIdStr);
							}
						}else if("".equals(enterprise.getBelongedId()) || null ==enterprise.getBelongedId()){
							if(!"".equals(userIdStr)) {
								enterprise.setBelongedId(userIdStr);
							}
						}
					}else {
						enterprise.setBelongedId(userIdStr);
					}
					enterpriseService.save(enterprise);
				}else if(str[1].equals("person_customer")) {
					Person person = personService.getById(Integer.valueOf(str[0]));
					if(idCustomerTypes.length > 1) {
						if(!"".equals(person.getBelongedId())&& null !=person.getBelongedId()) {
							if(!"".equals(userIdStr)) {
								String oldUserIdsStr = person.getBelongedId();
								String []userIds = userIdStr.split(",");
								for(String userId : userIds) {
									if(oldUserIdsStr.indexOf(userId) < 0) {
										oldUserIdsStr += "," + userId;
									}
								}
								person.setBelongedId(oldUserIdsStr);
							}else {
								person.setBelongedId(userIdStr);
							}
						}else if("".equals(person.getBelongedId()) || null ==person.getBelongedId()){
							if(!"".equals(userIdStr)) {
								person.setBelongedId(userIdStr);
							}
						}
					}else {
						person.setBelongedId(userIdStr);
					}
					personService.save(person);
				}else if(str[1].equals("InvestPerson")){
//					InvestPerson ip = investPersonService.get(Long.parseLong(str[0]));
					InvestPerson ip = investPersonService.get(Long.valueOf(str[0]));
					//System.out.println("----------------进入授权");
					if(!"".equals(ip)&&null!=ip){
						if(!"".equals(userIdStr)){
							String oldUserIdsStr = ip.getBelongedId();
							String []userIds = userIdStr.split(",");
							StringBuffer sbff = new StringBuffer();
							for(String userId : userIds) {
//								if(oldUserIdsStr.indexOf(userId) < 0) {
//									oldUserIdsStr += "," + userId;
//								}
								if(null !=userId){
									sbff.append(userId);
									sbff.append(",");
								}
							}
							if(null !=userIds && userIds.length >0){
								sbff.deleteCharAt(sbff.length()-1);
							}
							oldUserIdsStr = sbff.toString();
							ip.setBelongedId(oldUserIdsStr);
						}else{
							ip.setBelongedId(userIdStr);
						}
						investPersonService.save(ip);
					}
				}else if(str[1].equals("InvestEnterprise")){
//					InvestPerson ip = investPersonService.get(Long.parseLong(str[0]));
					InvestEnterprise ip = investEnterpriseService.get(Long.valueOf(str[0]));
					//System.out.println("----------------进入授权");
					if(!"".equals(ip)&&null!=ip){
						if(!"".equals(userIdStr)){
							String oldUserIdsStr = ip.getBelongedId();
							String []userIds = userIdStr.split(",");
							StringBuffer sbff = new StringBuffer();
							for(String userId : userIds) {
//								if(oldUserIdsStr.indexOf(userId) < 0) {
//									oldUserIdsStr += "," + userId;
//								}
								if(null !=userId){
									sbff.append(userId);
									sbff.append(",");
								}
							}
							if(null !=userIds && userIds.length >0){
								sbff.deleteCharAt(sbff.length()-1);
							}
							oldUserIdsStr = sbff.toString();
							ip.setBelongedId(oldUserIdsStr);
						}else{
							ip.setBelongedId(userIdStr);
						}
						investEnterpriseService.save(ip);
					}
				}else if(str[1].equals("CsInvestmentperson")){
					CsInvestmentperson cs =csInvestmentpersonService.get(Long.valueOf(str[0]));
					if(!"".equals(cs)&&null!=cs){
						if(!"".equals(userIdStr)){
							String oldUserIdsStr = cs.getBelongedId();
							String []userIds = userIdStr.split(",");
							StringBuffer sbff = new StringBuffer();
							StringBuffer shopIds = new StringBuffer();
							for(String userId : userIds) {
//								if(oldUserIdsStr.indexOf(userId) < 0) {
//									oldUserIdsStr += "," + userId;
//								}
								if(null !=userId){
									sbff.append(userId);
									sbff.append(",");
									
									//维护共享人门店Id
									AppUser  appUser=appUserService.get(Long.valueOf(userId));
									Organization organization=organizationService.getByUserIdToStoreNameAndStoreNameId(appUser.getDepartment().getDepId());
									if(null !=organization && !"".equals(organization)){
										shopIds.append(organization.getOrgId().toString());
										shopIds.append(",");
									}
								}
							}
							if(null !=userIds && userIds.length >0){
								sbff.deleteCharAt(sbff.length()-1);
								if(null !=shopIds && shopIds.length()>0){
									shopIds.deleteCharAt(shopIds.length()-1);
								}
							}
							oldUserIdsStr = sbff.toString();
							cs.setBelongedId(oldUserIdsStr);
							cs.setBelongedShopId(shopIds.toString());
						}else{
							cs.setBelongedId(userIdStr);
							cs.setBelongedShopId(shopId);
						}
						csInvestmentpersonService.merge(cs);
					}
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return SUCCESS;
	}
}
