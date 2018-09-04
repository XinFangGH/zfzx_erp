package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.MD5;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationEnterprise;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationPerson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.activity.BpActivityManageService;
import com.zhiwei.credit.service.creditFlow.auto.PlBidAutoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.customer.cooperation.CsCooperationEnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.cooperation.CsCooperationPersonService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.util.MD5_T;
/**
 * 
 * @author 
 *
 */
public class BpCustRelationAction extends BaseAction{
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private  BpCustMemberService bpCustMemberService;
	@Resource
	private PersonService personService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	@Resource
	private PlBidAutoService plBidAutoService;
	private BpCustRelation bpCustRelation;
	@Resource
	private CsCooperationPersonService csCooperationPersonService;
	@Resource
	private CsCooperationEnterpriseService csCooperationEnterpriseService;
	@Resource
	private BpActivityManageService bpActivityManageService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	
	private Long relationId;

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public BpCustRelation getBpCustRelation() {
		return bpCustRelation;
	}

	public void setBpCustRelation(BpCustRelation bpCustRelation) {
		this.bpCustRelation = bpCustRelation;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpCustRelation> list= bpCustRelationService.getAll(filter);
		
		Type type=new TypeToken<List<BpCustRelation>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
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
				bpCustRelationService.remove(new Long(id));
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
		BpCustRelation bpCustRelation=bpCustRelationService.get(relationId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustRelation));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	/**2014-07-28
	 * 
	 * 开通p2p账号
	 */
	public String save(){
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
		String username =this.getRequest().getParameter("username").toLowerCase();
		String password =this.getRequest().getParameter("password");
		String customerType = this.getRequest().getParameter("customerType");//客户类型
		String userId = this.getRequest().getParameter("userId");//所选用户ID
		boolean flag=true;//检查是否存在用户
		try{
			//用户名验证
			QueryFilter filter =new QueryFilter(this.getRequest());
			filter.addFilter("Q_loginname_S_EQ", username);
			List<BpCustMember> list=bpCustMemberService.getAll(filter);
			if(null!=list && list.size()>0){
				setJsonString("{success:false,msg:'用户名已存在!'}");
				flag=false;
			}else{
				String cardnumber = null;
				String cellphone = null;
				String selfemail = null;
				Integer cardType=null;//证件类型
				String birthDay=null;
				if(customerType.equals("p_loan")){//个人借款客户
					Person person = personService.getById(Integer.valueOf(userId));
					cardType=person.getCardtype();
					cardnumber = person.getCardnumber();
					cellphone = person.getCellphone();
					selfemail = person.getSelfemail();
				}else if(customerType.equals("b_loan")){//企业借款客户
					Enterprise enterprise = enterpriseService.getById(Integer.valueOf(userId));
					cardnumber = enterprise.getCciaa();
					cellphone = enterprise.getTelephone();
					selfemail = enterprise.getEmail();
				}else if(customerType.equals("p_cooperation") || customerType.equals("p_financial")){//个人债权客户 || 个人理财顾问
					CsCooperationPerson csCooperationPerson = csCooperationPersonService.get(Long.valueOf(userId));
					cardnumber = csCooperationPerson.getCardNumber();
					cardType=Integer.valueOf(csCooperationPerson.getCardType());
					cellphone = csCooperationPerson.getPhoneNumber();
					selfemail = csCooperationPerson.getEmail();
				}else if(customerType.equals("b_cooperation") || customerType.equals("b_guarantee")){//企业债权客户 || 担保机构客户
					CsCooperationEnterprise csCooperationEnterprise = csCooperationEnterpriseService.get(Long.valueOf(userId));
					cardnumber = csCooperationEnterprise.getOrganizationNumber();
					cellphone = csCooperationEnterprise.getPphone();
				}else if(customerType.equals("p_invest")){//线下投资客户
					CsInvestmentperson csInvestmentperson = csInvestmentpersonService.get(Long.valueOf(userId));
					cardType=csInvestmentperson.getCardtype();
					cardnumber = csInvestmentperson.getCardnumber();
					cellphone = csInvestmentperson.getCellphone();
					selfemail = csInvestmentperson.getSelfemail();
				}
				if(null!=cardnumber && null!=cardType && 309==cardType){//证件类型为身份证并且证件号吗存在
					birthDay=cardnumber.substring(6,10)+"-"+cardnumber.substring(10,12)+"-"+cardnumber.substring(12,14);
				}
				if(flag){
					//证件号码验证
					QueryFilter filter_1 =new QueryFilter();
					filter_1.addFilter("Q_cardcode_S_EQ", cardnumber);
					List<BpCustMember> list_1=bpCustMemberService.getAll(filter_1);
					if(null!= list_1 && list_1.size()>0){
						setJsonString("{success:false,msg:'证件号码已存在'}");
						flag=false;
					}
				}
				if(flag){
					//手机号
					QueryFilter filter_2 =new QueryFilter();
					filter_2.addFilter("Q_telphone_S_EQ", cellphone);
					List<BpCustMember> list_2=bpCustMemberService.getAll(filter_2);
					if(null!=list_2 && list_2.size()>0){
						setJsonString("{success:false,msg:'手机号已存在'}");
						flag=false;
					}
				}
				if(flag){
					//邮箱为非必填项，故而在此判null
					if(null !=selfemail && !"".equals(selfemail)){
						//邮箱
						if(null!=selfemail && !"".equals(selfemail)){
							QueryFilter filter_3 =new QueryFilter();
							filter_3.addFilter("Q_email_S_EQ", selfemail);
							List<BpCustMember> list_3=bpCustMemberService.getAll(filter_3);
							if(null!=list_3 && list_3.size()>0){
								setJsonString("{success:false,msg:'邮箱已存在'}");
								flag=false;
							}
						}
					}
				}
				if(flag){
					BpCustMember cust=new BpCustMember();
					boolean isSave = false;
					if(customerType.equals("p_loan")){//个人借款客户
						cust.setLoginname(username);//用户名
						MD5 md5 = new MD5();
						cust.setPassword(md5.md5(password, "UTF-8"));//密码
						cust.setRegistrationDate(new Date());//日期
						Person person=personService.getById(Integer.valueOf(userId));//得到这个所选用户的对象
						cust.setTruename(person.getName());//真实姓名
						cust.setBirthday(person.getBirthday());//生日
						cust.setHomePhone(person.getTelphone());//家庭电话
						cust.setCardcode(person.getCardnumber());//身份证号
					//	cust.setIsCheckCard("1");
						String cell=person.getCellphone();
						if(!cell.equals("")&&cell!=null){
							cust.setTelphone(cell);//手机号
							cust.setIsCheckPhone("1");
						}
						String eml=person.getSelfemail();
						if(eml!=null && !eml.equals("")){
							cust.setEmail(eml=="null"?"":eml);//邮箱
							cust.setIsCheckEmail("1");
						}
						cust.setSex(person.getSex());
						if(person.getSex()!=null){//非必填项判断
							if(person.getSex()==312){//判断性别
								cust.setSex(0);//男
							}else{
								cust.setSex(1);//女
							}
						}
						
						cust.setNativePlaceCity(person.getHomeland());//市
						cust.setRelationAddress(person.getAddress());//地址
						cust.setFax(person.getFax());//传真
						cust.setPostCode(person.getPostcode());//邮编
						cust.setCustomerType(Integer.valueOf("0"));
						cust.setCardtype(cardType);
						if(null!=birthDay){
							cust.setBirthday(sdf.parse(birthDay));
						}
						isSave = true;
					}else if(customerType.equals("b_loan")){//企业借款客户
						cust.setLoginname(username);//用户名
						MD5 md5 = new MD5();
						cust.setPassword(md5.md5(password, "UTF-8"));//密码
						cust.setRegistrationDate(new Date());//日期
						Enterprise enterprise=enterpriseService.getById(Integer.valueOf(userId));
						Person person=personService.getById(enterprise.getLegalpersonid());//得到这个所选用户的对象
						cust.setTruename(enterprise.getEnterprisename());//真实姓名
						cust.setCardcode(enterprise.getOrganizecode());//身份证号
						//cust.setIsCheckCard("1");
						String cell=enterprise.getTelephone();
						if(!cell.equals("")&&cell!=null){
							cust.setTelphone(cell);//手机号
							cust.setIsCheckPhone("1");
						}
						String eml=enterprise.getEmail();
						if(eml!=null && !eml.equals("")){
							cust.setEmail(eml=="null"?"":eml);//邮箱
							cust.setIsCheckEmail("1");
						}
						cust.setRelationAddress(enterprise.getArea());//地址
						cust.setFax(enterprise.getFax());//传真
						cust.setPostCode(enterprise.getPostcoding());//邮编
						cust.setLegalPerson(person.getName());
						cust.setLegalNo(person.getCardnumber());
						cust.setContactPerson(enterprise.getEnterprisename());
						cust.setBusinessLicense(enterprise.getCciaa());
						cust.setCustomerType(Integer.valueOf("1"));
						isSave = true;
					}else if(customerType.equals("p_cooperation") || customerType.equals("p_financial")){//个人债权客户 || 个人理财顾问
						cust.setLoginname(username);//用户名
						MD5 md5 = new MD5();
						cust.setPassword(md5.md5(password, "UTF-8"));//密码
						cust.setRegistrationDate(new Date());//日期
						CsCooperationPerson csCooperationPerson = csCooperationPersonService.get(Long.valueOf(userId));
						cust.setTruename(csCooperationPerson.getName());//真实姓名
						cust.setTelphone(csCooperationPerson.getPhoneNumber());//手机号
						cust.setIsCheckPhone("1");//手机号是否认证
						cust.setCardcode(csCooperationPerson.getCardNumber());//身份证号
						cust.setEmail(csCooperationPerson.getEmail());//邮箱
						cust.setSex(Integer.valueOf(csCooperationPerson.getSex()));
						cust.setCardtype(cardType);
						if(null!=birthDay){
							cust.setBirthday(sdf.parse(birthDay));
						}
						csCooperationPerson.setIsOpenP2P("0");
						csCooperationPersonService.save(csCooperationPerson);
						isSave = true;
					}else if(customerType.equals("b_cooperation") || customerType.equals("b_guarantee")){//企业债权客户 || 担保机构客户
						cust.setLoginname(username);//用户名
						MD5 md5 = new MD5();
						cust.setPassword(md5.md5(password, "UTF-8"));//密码
						cust.setRegistrationDate(new Date());//日期
						CsCooperationEnterprise csCooperationEnterprise = csCooperationEnterpriseService.get(Long.valueOf(userId));
						cust.setTruename(csCooperationEnterprise.getName());
						cust.setCardcode(csCooperationEnterprise.getOrganizationNumber());
						cust.setTelphone(csCooperationEnterprise.getPphone());
						cust.setIsCheckPhone("1");//手机号是否验证
						cust.setBusinessLicense(csCooperationEnterprise.getLicenseNumber());
						cust.setLegalPerson(csCooperationEnterprise.getPname());
						cust.setLegalNo(csCooperationEnterprise.getPcardNumber());
						cust.setContactPerson(csCooperationEnterprise.getName());
						csCooperationEnterprise.setIsOpenP2P("0");
						cust.setCustomerType(Integer.valueOf("1"));
						if(customerType.equals("b_guarantee")){
							cust.setEntCompanyType(Short.valueOf("1"));//企业客户类型为担保户
						}
						csCooperationEnterpriseService.save(csCooperationEnterprise);
						isSave = true;
					}else if(customerType.equals("p_invest")){//线下投资客户
						cust.setLoginname(username);//用户名
						MD5 md5 = new MD5();
						cust.setPassword(md5.md5(password, "UTF-8"));//密码
						cust.setRegistrationDate(new Date());//日期
						CsInvestmentperson inverst=csInvestmentpersonService.get(Long.valueOf(userId));
						cust.setTruename(inverst.getInvestName());//真实姓名
						cust.setTelphone(inverst.getCellphone());//手机号
						if(null!=inverst.getBirthDay()&&!"".equals(inverst.getBirthDay())){
							Date d=sdf.parse(inverst.getBirthDay());
							cust.setBirthday(d);//生日
						}
						cust.setCardtype(cardType);
						if(null!=birthDay){
							cust.setBirthday(sdf.parse(birthDay));
						}
						cust.setCardcode(inverst.getCardnumber());//身份证号
						String eml=inverst.getSelfemail();
						cust.setEmail(eml=="null"?"":eml);//邮箱
						if(inverst.getSex()==312){//判断性别
							cust.setSex(0);//男
						}else{
							cust.setSex(1);//女
						}
						cust.setRelationAddress(inverst.getPostaddress());//地址
						cust.setPostCode(inverst.getPostcode());//邮编
						isSave = true;
					}
					if(isSave){
						MD5_T md1 = new MD5_T();//生成推荐码
						cust.setPlainpassword(md1.md5_3(cust.getLoginname()));
						bpCustMemberService.save(cust);
						//生成系统虚拟账户
						obSystemAccountService.saveAccount(cust.getId().toString(), "0");
						//自动投标初始化
						plBidAutoService.initPlBidAuto(cust);
						//维护关系表
						bpCustRelation=new BpCustRelation();
						bpCustRelation.setOfflineCusId(Long.valueOf(userId));
						bpCustRelation.setOfflineCustType(customerType);
						bpCustRelation.setP2pCustId(cust.getId());
						if(bpCustRelation.getRelationId()==null){
							bpCustRelationService.save(bpCustRelation);
						}
						//活动中心管理--------
						bpActivityManageService.autoDistributeEngine("1", cust.getId().toString(),null,null);
						//活动中心管理 --------end
						setJsonString("{success:true,msg:'开通成功'}");
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("{success:false,msg:'保存出错'}");
		}
		return SUCCESS;
	}
	
	/**
	 * 系统用户开通P2P账号
	 * @return
	 */	public String saveP2P(){
		String userId =this.getRequest().getParameter("userId");
		String fullname =this.getRequest().getParameter("fullname");//p2p_truename
		String p2pAccount =this.getRequest().getParameter("p2pAccount");//p2p账号
		String password =this.getRequest().getParameter("password");//密码
		String email =this.getRequest().getParameter("email");//邮箱
		String mobile =this.getRequest().getParameter("mobile");//手机号
		String plainpassword =this.getRequest().getParameter("plainpassword");//推荐码
		String type =this.getRequest().getParameter("type");//标识是开通还是绑定(0开通,1绑定)
		String memberId =this.getRequest().getParameter("memberId");
		AppUser appUser = appUserService.get(Long.valueOf(userId));
		try{
			BpCustMember cust=null;
			if(type.equals("0")){
				cust=new BpCustMember();
				cust.setLoginname(p2pAccount);//用户名
				MD5 md5 = new MD5();
				cust.setPassword(md5.md5(password, "UTF-8"));//密码
				cust.setRegistrationDate(new Date());//日期
				cust.setTruename(fullname);//真实姓名
				cust.setTelphone(mobile);//手机号
				cust.setIsCheckPhone("1");
				cust.setEmail(email);//邮箱
				if(email!=null&&!"".equals(email)){
					cust.setIsCheckEmail("1");
				}
				cust.setPlainpassword(plainpassword);
				cust=bpCustMemberService.save(cust);
				if(null==appUser.getMobile() || "".equals(appUser.getMobile())){
					appUser.setMobile(mobile);
				}
			}else{
				cust=bpCustMemberService.get(Long.valueOf(memberId));
				BpCustRelation b=bpCustRelationService.getByTypeAndP2pCustId(cust.getId(), "p_staff");
				if(null!=b){
					setJsonString("{success:false,msg:'绑定p2p账号失败，该p2p账号已被其他员工绑定！'}");
					return SUCCESS;
				}
			//	cust.setPlainpassword(plainpassword);
				cust=bpCustMemberService.merge(cust);
				if(null==appUser.getMobile() || "".equals(appUser.getMobile())){
					appUser.setMobile(cust.getTelphone());
				}
			}
			appUserService.merge(appUser);
			bpCustRelation=new BpCustRelation();
			bpCustRelation.setOfflineCusId(Long.valueOf(userId));
			bpCustRelation.setOfflineCustType("p_staff");
			bpCustRelation.setP2pCustId(cust.getId());
			if(bpCustRelation.getRelationId()==null){
				bpCustRelationService.save(bpCustRelation);
			}
			if(type.equals("0")){
				setJsonString("{success:true,msg:'开通P2P账号成功!!!'}");
			}else{
				setJsonString("{success:true,msg:'绑定P2P账号成功!!!'}");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("开通/绑定系统用户P2P账号出错:" + e.getMessage());
			setJsonString("{success:false,msg:'开通/绑定P2P账号失败!!!'}");
		}
		return SUCCESS;
	}
	
	/**
	 * 修改p2p账号的状态
	 * @return
	 */
	public String changeP2PStatus(){
		try{
			String bpCustMemberId=this.getRequest().getParameter("bpCustMemberId");
			String isForbidden=this.getRequest().getParameter("isForbidden");
			BpCustMember cust=bpCustMemberService.get(Long.valueOf(bpCustMemberId));
			cust.setIsForbidden(Integer.valueOf(isForbidden));
			bpCustMemberService.merge(cust);
			setJsonString("{success:true,msg:'状态修改成功!!!'}");
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("{success:false,msg:'状态修改失败!!!'}");
			logger.error("修改系统用户P2P账号状态出错:" + e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 判断手机号是否存在,判断P2P账号是否存在
	 * @return
	 */
	public String isExist(){
		QueryFilter filter=new QueryFilter(this.getRequest());
		List<BpCustMember> list=bpCustMemberService.getAll(filter);
		if(null!=list && list.size()>0){
			setJsonString("{success:true,memberId:"+list.get(0).getId()+"}");
		}else{
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
	
	/**
	 * 导出系统用户信息及p2p账号信息
	 */
	public void exportExcel(){
		//0代表导出推荐码，1代表导出推荐个数，2代表导出推荐业绩
		String type=this.getRequest().getParameter("type");
		String[] tableHeader=null;
		if("0".equals(type)){
			tableHeader = new String[]{"序号","姓名","工号","ERP账号","P2P账号","推荐码","手机号","邮箱"};
		}else if("1".equals(type)){
			tableHeader = new String[]{"序号","姓名","推荐码","手机号","邮箱","开通日期","一级推荐个数","二级推荐个数"};
		}else if("2".equals(type)){
			tableHeader = new String[]{"序号","姓名","推荐码","手机号","邮箱","开通日期","一级推荐金额","二级推荐金额"};
		}
		try{
			PageBean<AppUser> pageBean = new PageBean<AppUser>(null,null,getRequest());
			appUserService.getUserList(pageBean);
			String headerStr="";
			if("0".equals(type)){
				headerStr="系统账户列表";
			}else if("1".equals(type)){
				headerStr="推荐个数列表";
			}else if("2".equals(type)){
				headerStr="推荐业绩列表";
			}
			ExcelHelper.export(pageBean.getResult(),tableHeader,headerStr);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出系统账号出错:" + e.getMessage());
		}
	}
	
	/**
	 * 导出线下推荐业绩明细
	 */
	public void exportDetailExcel(){
		//one代表一级 two代表二级
		String type=this.getRequest().getParameter("type");
		String[] tableHeader=null;
		if("one".equals(type)){
			tableHeader = new String[]{"序号","客户姓名","身份证号","投资次数","投资日期","投资类型","投资项目","金额","期限","一级推荐人","一级推荐码","所属一级部门","所属二级部门","角色"};
		}else{
			tableHeader = new String[]{"序号","客户姓名","身份证号","投资次数","投资日期","投资类型","投资项目","金额","期限","二级推荐人","二级推荐码","所属一级部门","所属二级部门","角色"};
		}
		try{
			PageBean<AppUser> pageBean = new PageBean<AppUser>(null,null,getRequest());
			appUserService.userPerformanceList(pageBean);
			String headerStr="";
			if("one".equals(type)){
				headerStr="线下一级业绩明细表";
			}else{
				headerStr="线下二级业绩明细表";
			}
			ExcelHelper.export(pageBean.getResult(),tableHeader,headerStr);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出线下推荐业绩明细 出错:" + e.getMessage());
		}
	}
	
	/**2015-12-02   huangguohui
	 * 绑定p2p用户
	 * @return
	 */
	public String bindUser(){
		String username =this.getRequest().getParameter("username");
		//客户类型
		String customerType = this.getRequest().getParameter("customerType");
		//所选用户ID
		String userId = this.getRequest().getParameter("userId");
		//(1).检查是否存在用户
		boolean flag = true;
		if(null==username || username.equals("")){
			setJsonString("{success:false,msg:'用户名不能为空'}");
			flag = false;
		}
		//(2).判断用户名是否存在
		QueryFilter filter3=new QueryFilter(this.getRequest());
		filter3.addFilter("Q_loginname_S_EQ", username);
		List<BpCustMember> list3 =bpCustMemberService.getAll(filter3);
		String result="";
		if(null!=list3 && list3.size()>0){
			//(3).判断该p2p账号类型是否和当前erp账号类型相同(既个人只能绑定个人)
			if("b_loan".equals(customerType) || "b_cooperation".equals(customerType)){//企业客户、企业债权
				filter3.addFilter("Q_customerType_N_EQ",1);
				result="{success:false,msg:'请填写企业/企业债权p2p账号!!!'}";
			}else if("b_guarantee".equals(customerType)){//担保机构
				filter3.addFilter("Q_customerType_N_EQ",1);
				filter3.addFilter("Q_entCompanyType_SN_EQ",1);
				result="{success:false,msg:'请填写企业担保户的p2p账号!!!'}";
			}else{
				filter3.addFilter("Q_customerType_N_EQ",0);
				result="{success:false,msg:'请填写个人p2p账号!!!'}";
			}
//			list3 =bpCustMemberService.getAll(filter3);
			/*if(null==list3 || list3.size()<=0){
				setJsonString(result);
				flag = false;
			}else{
				if(!"b_guarantee".equals(customerType)){
					BpCustMember bc=list3.get(0);
					if(null!=bc.getEntCompanyType() && bc.getEntCompanyType()==1){
						setJsonString("{success:false,msg:'该p2p账号为担保户的p2p账号，非担保机构不能绑定!!!'}");
						flag = false;
					}
				}
			}*/
			if(list3!=null&&list3.size()>0){
				BpCustMember bc=list3.get(0);
				if(bc!=null&&null!=bc.getEntCompanyType() && bc.getEntCompanyType()==1){
					setJsonString("{success:false,msg:'该p2p账号为担保户的p2p账号，非担保机构不能绑定!!!'}");
					flag = false;
				}
			}
		}else{
			setJsonString("{success:false,msg:'用户名不存在,请核实！'}");
			flag = false;
		}
		//(4).判断该用户是否已绑定p2p账号
		if(flag){
			QueryFilter filter0=new QueryFilter();
			filter0.addFilter("Q_offlineCustType_S_EQ", customerType);
			filter0.addFilter("Q_offlineCusId_L_EQ", userId);
			List<BpCustRelation> list0 =bpCustRelationService.getAll(filter0);
			if(list0!=null&&list0.size()>0){
				setJsonString("{success:false,msg:'该用户已绑定P2P账号'}");
				flag = false;
			}
		}
		//(5).验证通过,最终新增关联关系
		if(flag){
			String cardnumber=this.getRequest().getParameter("cardnumber");
			//维护关系表
//			BpCustMember bpCustMember = bpCustMemberService.getMemberUserName(username);
			BpCustMember bpCustMember = list3.get(0);
			BpCustRelation b=bpCustRelationService.getByTypeAndP2pCustId(bpCustMember.getId(), customerType);
			if(null!=b){
				setJsonString("{success:false,msg:'该P2P账号已被其他同类客户绑定'}");
			}else if(!cardnumber.equals(bpCustMember.getCardcode())){
				setJsonString("{success:false,msg:'证件号码不匹配,不能进行绑定'}");
			}else{
				bpCustRelation=new BpCustRelation();
				bpCustRelation.setOfflineCusId(Long.valueOf(userId));
				bpCustRelation.setOfflineCustType(customerType);
				bpCustRelation.setP2pCustId(bpCustMember.getId());
				if(bpCustRelation.getRelationId()==null){
					bpCustRelationService.save(bpCustRelation);
					setJsonString("{success:true,msg:'绑定成功'}");
				}
				if(customerType.equals("p_cooperation") || customerType.equals("p_financial")){//个人债权客户 || 个人理财顾问
					CsCooperationPerson csCooperationPerson = csCooperationPersonService.get(Long.valueOf(userId));
					csCooperationPerson.setIsOpenP2P("0");
					csCooperationPersonService.save(csCooperationPerson);
				}else if(customerType.equals("b_cooperation") || customerType.equals("b_guarantee")){//企业债权客户 || 担保机构客户
					CsCooperationEnterprise csCooperationEnterprise = csCooperationEnterpriseService.get(Long.valueOf(userId));
					csCooperationEnterprise.setIsOpenP2P("0");
					csCooperationEnterpriseService.save(csCooperationEnterprise);
				}
		    }
		}
		return SUCCESS;
	}
	
}
