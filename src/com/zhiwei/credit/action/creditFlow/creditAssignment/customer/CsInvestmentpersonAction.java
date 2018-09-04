package com.zhiwei.credit.action.creditFlow.creditAssignment.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hurong.core.util.DateUtil;
import com.hurong.credit.util.Common;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditAssignment.obligationFundIntenList;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.VInvestmentPerson;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.VObligationInvestInfo;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.pay.BankCode;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.thirdInterface.WebBankcard;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.contract.ElementHandleService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObObligationInvestInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.project.VObligationInvestInfoService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.pay.BankCodeService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.thirdInterface.FuiouService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class CsInvestmentpersonAction extends BaseAction{
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private ElementHandleService elementHandleService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private ObObligationInvestInfoService obObligationInvestInfoService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private CreditProjectService creditProjectService;

	@Resource
	private VObligationInvestInfoService vObligationInvestInfoService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private CsBankService csBankService;
	@Resource
	private BankCodeService bankCodeService;
	@Resource
	private FuiouService fuiouService;
	@Resource
	private OrganizationService organizationService;
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	private CsInvestmentperson csInvestmentperson;
	
	private Long investId;
	private Boolean isAll;
	private String sexvalue;
	private String investName;
	private String sex;
	private String cellphone;
	private String cardtype;
	private String cardnumber;
	private EnterpriseBank enterpriseBank;
	private Integer contractStatus;
	private VInvestmentPerson vInvestmentPerson;
	private static String fundIntentStatusName_S ="未生效";
	private static String fundIntentStatusName_M ="回款中";
	private static String fundIntentStatusName_E="已结清";
	private List<VObligationInvestInfo> vObligationInvestInfo;
	
	
	public List<VObligationInvestInfo> getvObligationInvestInfo() {
		return vObligationInvestInfo;
	}

	public void setvObligationInvestInfo(
			List<VObligationInvestInfo> vObligationInvestInfo) {
		this.vObligationInvestInfo = vObligationInvestInfo;
	}

	public VInvestmentPerson getvInvestmentPerson() {
		return vInvestmentPerson;
	}

	public void setvInvestmentPerson(VInvestmentPerson vInvestmentPerson) {
		this.vInvestmentPerson = vInvestmentPerson;
	}

	public Integer getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getSexvalue() {
		return sexvalue;
	}

	public void setSexvalue(String sexvalue) {
		this.sexvalue = sexvalue;
	}

	public String getInvestName() {
		return investName;
	}

	public void setInvestName(String investName) {
		this.investName = investName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public EnterpriseBank getEnterpriseBank() {
		return enterpriseBank;
	}

	public void setEnterpriseBank(EnterpriseBank enterpriseBank) {
		this.enterpriseBank = enterpriseBank;
	}

	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}

	public Long getInvestId() {
		return investId;
	}

	public void setInvestId(Long investId) {
		this.investId = investId;
	}

	public CsInvestmentperson getCsInvestmentperson() {
		return csInvestmentperson;
	}

	public void setCsInvestmentperson(CsInvestmentperson csInvestmentperson) {
		this.csInvestmentperson = csInvestmentperson;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		String investName = this.getRequest().getParameter("Q_investName_S_EQ");
		QueryFilter filter=new QueryFilter(getRequest());
		if(investName!=null&&!"".equals(investName)){
			filter.addFilter("Q_investName_S_EQ", investName);
		}
		List<CsInvestmentperson> list= csInvestmentpersonService.getAll(filter);
		for(int i=0;i<list.size();i++){
			CsInvestmentperson c=list.get(i);
			BigDecimal[] a=obSystemAccountService.sumTypeTotalMoney(c.getInvestId(),"1");
			if(a[0].compareTo(new BigDecimal("0"))==0){
				
			}else{
			  c.setTotalMoney(a[3]);
			}
			/*if(null==c.getTotalMoney()){
			//	list.remove(i);
				
			}*/
		}
		Type type=new TypeToken<List<CsInvestmentperson>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		System.out.println(jsonString);
		return SUCCESS;
	}
	
	public String listCareAndGrant(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<CsInvestmentperson> list= csInvestmentpersonService.getAllList(this.getRequest(),start,limit);
		List<CsInvestmentperson> listcount= csInvestmentpersonService.getAllList(this.getRequest(),null,null);
		Type type=new TypeToken<List<CsInvestmentperson>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalProperty':")
		.append(listcount!=null?listcount.size():0).append(",topics:");
		
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
				csInvestmentpersonService.remove(new Long(id));
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
		CsInvestmentperson csInvestmentperson=csInvestmentpersonService.get(investId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(csInvestmentperson));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(csInvestmentperson.getInvestId()==null){
			csInvestmentpersonService.save(csInvestmentperson);
		}else{
			CsInvestmentperson orgCsInvestmentperson=csInvestmentpersonService.get(csInvestmentperson.getInvestId());
			try{
				BeanUtil.copyNotNullProperties(orgCsInvestmentperson, csInvestmentperson);
				csInvestmentpersonService.save(orgCsInvestmentperson);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/** 
	 * 查询线下投资人员表的list
	 * 
	 * */
	public void queryInvestmentPerson() {
		try {
			Object ids=this.getRequest().getSession().getAttribute("userIds");
			String belongedId=this.getRequest().getParameter("belongedId");
			Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
			if(null!=belongedId && !"".equals(belongedId)){
				map.put("belongedId",belongedId);
			}
			Object[] obj = { investName, sex, cellphone, cardtype,cardnumber };
			String[] str = { "investName", "sex", "cellphone", "cardtype","cardnumber" };
			csInvestmentpersonService.ajaxQueryinvestmentPerson(map,start, limit,
					"from cs_investmentperson AS p where 1=1", str, obj,sort, dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 添加投资客户数据*/
	public void addPerson() {
		try {
			Long currentUserId = ContextUtil.getCurrentUserId();
			Long companyId = ContextUtil.getLoginCompanyId();
			if (null == csInvestmentperson.getCompanyId()) {
				csInvestmentperson.setCompanyId(companyId);
			}
			csInvestmentperson.setCreater(ContextUtil.getCurrentUser()
					.getFullname());
			csInvestmentperson.setCreaterId(currentUserId);
			if ("".equals(csInvestmentperson.getBelongedId())
					|| csInvestmentperson.getBelongedId() == null) {
				csInvestmentperson.setBelongedId(currentUserId.toString());
			}
			csInvestmentperson.setCreatedate(new Date());
			csInvestmentpersonService.addInvestment(csInvestmentperson);
			/*
			 * 投资人保存成功了才可以保存身份证和银行卡信息
			 * */
			if(csInvestmentperson.getInvestId()!=null&&!"".equals(csInvestmentperson.getInvestId())){
				String personSFZZId = super.getRequest().getParameter("personSFZZId");
				if (personSFZZId != "" && !personSFZZId.equals("")) {
					Object[] obj = {
							"cs_investmentperson_sfzz."
									+ csInvestmentperson.getInvestId(),
							Integer.parseInt(personSFZZId) };
					elementHandleService.updateCon(
							"update FileForm set mark = ? where fileid = ?", obj);
				}
				String personSFZFId = super.getRequest().getParameter("personSFZFId");
				if (personSFZFId != "" && !personSFZFId.equals("")) {
					Object[] obj = {
							"cs_investmentperson_sfzf."
									+ csInvestmentperson.getInvestId(),
							Integer.parseInt(personSFZFId) };
					elementHandleService.updateCon(
							"update FileForm set mark = ? where fileid = ?", obj);
				}

				if (enterpriseBank.getId() == null
						|| enterpriseBank.getId().equals("")
						|| enterpriseBank.getId().equals("null")) {
					enterpriseBank.setEnterpriseid(Integer
							.valueOf(csInvestmentperson.getInvestId().toString()));
					enterpriseBank.setCompanyId(companyId);
					enterpriseBank.setIscredit(Short.valueOf("0"));
					enterpriseBankService.save(enterpriseBank);
				} else {
				
					EnterpriseBank e = enterpriseBankService.getById(enterpriseBank.getId());
					BeanUtil.copyNotNullProperties(e, enterpriseBank);
					enterpriseBankService.save(e);
				}

				String personYHAZId = this.getRequest().getParameter("personYHKZId");
				if (personYHAZId != "" && !personYHAZId.equals("")) {
					Object[] obj = {
							"cs_investmentperson_yhaz."
									+ enterpriseBank.getId(),
							Integer.parseInt(personYHAZId) };
					elementHandleService.updateCon(
							"update FileForm set mark = ? where fileid = ?", obj);
				}
				String personYHAFId = this.getRequest()
						.getParameter("personYHKFId");
				if (personYHAZId != "" && !personYHAZId.equals("")) {
					Object[] obj = {
							"cs_investmentperson_yhaf."
									+ enterpriseBank.getId(),
							Integer.parseInt(personYHAFId) };
					elementHandleService.updateCon(
							"update FileForm set mark = ? where fileid = ?", obj);
				}
			}
			
			/* ===========创建投资人系统虚拟银行账户=========
			ObSystemAccount obSystemAccount = new ObSystemAccount();
			obSystemAccount.setCompanyId(companyId);
			obSystemAccount.setAccountName(csInvestmentPerson.getInvestName());
			obSystemAccount.setInvestmentPersonId(csInvestmentPerson.getInvestId());
			obSystemAccount.setAccountNumber(csInvestmentPerson.getCardnumber());// 当前默认系统虚拟账户是投资人的身份证号码
			obSystemAccount.setTotalMoney(new BigDecimal(0));
			obSystemAccount.setInvestPersonName(csInvestmentPerson.getInvestName());
			obSystemAccountService.save(obSystemAccount);*/
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/** 
	 * 查看银行卡账户
	 */
	public String seePersonBank() {
		try {
			vInvestmentPerson = csInvestmentpersonService
					.queryInvestmentPerson(investId);
			if (vInvestmentPerson.getBelongedId() != null
					&& !"".equals(vInvestmentPerson.getBelongedId())) {
				String belongedName = "";
				String[] str = vInvestmentPerson.getBelongedId().split(",");
				Set<AppUser> userSet = appUserService.getAppUserByStr(str);
				int i = 0;
				for (AppUser s : userSet) {
					belongedName += s.getFamilyName();
					i++;
					if (i != userSet.size()) {
						belongedName = belongedName + ",";
					}
				}
				vInvestmentPerson.setBelongedName(belongedName);
			}
			List<EnterpriseBank> elist = enterpriseBankService.getBankList(Integer
					.valueOf(vInvestmentPerson.getInvestId().toString()), Short
					.valueOf("1"), Short.valueOf("0"), Short.valueOf("3"));
			if (null != elist && elist.size() > 0) {
				EnterpriseBank bank = elist.get(0);
				vInvestmentPerson.setBankName(bank.getBankname());
				vInvestmentPerson.setBankNum(bank.getAccountnum());
				vInvestmentPerson.setEnterpriseBankId(bank.getId());
				vInvestmentPerson.setBankId(bank.getBankid());
				vInvestmentPerson.setAccountType(bank.getAccountType());
				vInvestmentPerson.setKhname(bank.getName());
				vInvestmentPerson.setOpenType(bank.getOpenType());
				vInvestmentPerson.setAreaId(bank.getAreaId());
				vInvestmentPerson.setAreaName(bank.getAreaName());
				vInvestmentPerson.setBankOutletsName(bank.getBankOutletsName());
				vInvestmentPerson.setOpenCurrency(bank.getOpenCurrency());
				vInvestmentPerson.setIsEnterprise(bank.getIsEnterprise());
				vInvestmentPerson.setIsInvest(bank.getIsInvest());
				vInvestmentPerson.setRemarks(bank.getRemarks());
				
				FileForm fileForm4 = fileFormService
				.getFileByMark("cs_investmentperson_yhaz." + bank.getId());
				if (fileForm4 != null) {
					vInvestmentPerson.setPersonYHKZId(fileForm4.getFileid());
					vInvestmentPerson.setPersonYHKZUrl(fileForm4.getWebPath());
					vInvestmentPerson.setPersonYHKZExtendName(fileForm4
							.getExtendname());
				}
				FileForm fileForm5 = fileFormService
						.getFileByMark("cs_investmentperson_yhaf." + bank.getId());
				if (fileForm5 != null) {
					vInvestmentPerson.setPersonYHKFId(fileForm5.getFileid());
					vInvestmentPerson.setPersonYHKFUrl(fileForm5.getWebPath());
					vInvestmentPerson.setPersonYHKFExtendName(fileForm5
							.getExtendname());
				}
			}
			FileForm fileForm2 = fileFormService
					.getFileByMark("cs_investmentperson_sfzz." + investId);
			if (fileForm2 != null) {
				vInvestmentPerson.setPersonSFZZId(fileForm2.getFileid());
				vInvestmentPerson.setPersonSFZZUrl(fileForm2.getWebPath());
				vInvestmentPerson.setPersonSFZZExtendName(fileForm2
						.getExtendname());
			}
			FileForm fileForm3 = fileFormService
					.getFileByMark("cs_investmentperson_sfzf." + investId);
			if (fileForm3 != null) {
				vInvestmentPerson.setPersonSFZFId(fileForm3.getFileid());
				vInvestmentPerson.setPersonSFZFUrl(fileForm3.getWebPath());
				vInvestmentPerson.setPersonSFZFExtendName(fileForm3
						.getExtendname());
			}
			
			JsonUtil.jsonFromObject(vInvestmentPerson, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 *  在取现申请时获得个人客户基本信息
	 *  包括个人投资客户信息
	 *  银行账户信息
	 *  系统账户信息
	 * 
	 * */
	public String getMoneyInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String investPersonId = this.getRequest().getParameter("investPersonId");
			if(investPersonId!=null&&!"".equals(investPersonId)){
				/* ===========查询余额信息============= */
				ObSystemAccount ob=obSystemAccountService.getByInvrstPersonIdAndType(Long.parseLong(investPersonId), ObSystemAccount.type1);
				if(ob!=null){
					BigDecimal freeze = obSystemAccountService.prefreezMoney(ob.getId(), null);
					if(freeze!=null){
						ob.setFreezeMoney(freeze);
						ob.setAvailableInvestMoney(ob.getTotalMoney().subtract(freeze));
					}else{
						ob.setFreezeMoney(new BigDecimal(0));
						ob.setAvailableInvestMoney(ob.getTotalMoney());
					}
				}
				map.put("obSystemAccount", ob);
				
				/* =========== 查询账户信息=========== */
				csInvestmentperson = csInvestmentpersonService.get(Long.parseLong(investPersonId));
				map.put("csInvestmentperson", csInvestmentperson);
				List<EnterpriseBank> elist = enterpriseBankService.getBankList(Integer.valueOf(investPersonId), Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("3"));
				if (null != elist && elist.size() > 0) {
					enterpriseBank = elist.get(0);
				}
				map.put("enterpriseBank", enterpriseBank);
			}
			StringBuffer buff = new StringBuffer("{success:true,data:");
			JSONSerializer json = com.zhiwei.core.util.JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
			buff.append(json.serialize(map));
			buff.append("}");
			jsonString=buff.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;

	}

	/**
	 *  查看选中的投资客户数据
	 */
	public String seePerson() {
		try {
			vInvestmentPerson = csInvestmentpersonService
					.queryInvestmentPerson(investId);
			if (vInvestmentPerson.getBelongedId() != null
					&& !"".equals(vInvestmentPerson.getBelongedId())) {
				String belongedName = "";
				String[] str = vInvestmentPerson.getBelongedId().split(",");
				Set<AppUser> userSet = appUserService.getAppUserByStr(str);
				int i = 0;
				for (AppUser s : userSet) {
					belongedName += s.getFamilyName();
					i++;
					if (i != userSet.size()) {
						belongedName = belongedName + ",";
					}
				}
				vInvestmentPerson.setBelongedName(belongedName);
			}
			List<EnterpriseBank> elist = enterpriseBankService.getBankList(Integer
					.valueOf(vInvestmentPerson.getInvestId().toString()), Short
					.valueOf("1"), Short.valueOf("0"), Short.valueOf("3"));
			if (null != elist && elist.size() > 0) {
				EnterpriseBank bank = elist.get(0);
				vInvestmentPerson.setBankName(bank.getBankname());
				vInvestmentPerson.setBankNum(bank.getAccountnum());
				vInvestmentPerson.setEnterpriseBankId(bank.getId());
				vInvestmentPerson.setBankId(bank.getBankid());
				vInvestmentPerson.setAccountType(bank.getAccountType());
				vInvestmentPerson.setKhname(bank.getName());
				vInvestmentPerson.setOpenType(bank.getOpenType());
				vInvestmentPerson.setAreaId(bank.getAreaId());
				vInvestmentPerson.setAreaName(bank.getAreaName());
				vInvestmentPerson.setBankOutletsName(bank.getBankOutletsName());
				vInvestmentPerson.setOpenCurrency(bank.getOpenCurrency());
				vInvestmentPerson.setIsEnterprise(bank.getIsEnterprise());
				vInvestmentPerson.setIsInvest(bank.getIsInvest());
				vInvestmentPerson.setRemarks(bank.getRemarks());
				FileForm fileForm4 = fileFormService
				.getFileByMark("cs_investmentperson_yhaz." + bank.getId());
				if (fileForm4 != null) {
					vInvestmentPerson.setPersonYHKZId(fileForm4.getFileid());
					vInvestmentPerson.setPersonYHKZUrl(fileForm4.getWebPath());
					vInvestmentPerson.setPersonYHKZExtendName(fileForm4
							.getExtendname());
				}
				FileForm fileForm5 = fileFormService
						.getFileByMark("cs_investmentperson_yhaf." + bank.getId());
				if (fileForm5 != null) {
					vInvestmentPerson.setPersonYHKFId(fileForm5.getFileid());
					vInvestmentPerson.setPersonYHKFUrl(fileForm5.getWebPath());
					vInvestmentPerson.setPersonYHKFExtendName(fileForm5
							.getExtendname());
				}
			}
			FileForm fileForm2 = fileFormService
					.getFileByMark("cs_investmentperson_sfzz." + investId);
			if (fileForm2 != null) {
				vInvestmentPerson.setPersonSFZZId(fileForm2.getFileid());
				vInvestmentPerson.setPersonSFZZUrl(fileForm2.getWebPath());
				vInvestmentPerson.setPersonSFZZExtendName(fileForm2
						.getExtendname());
			}
			FileForm fileForm3 = fileFormService
					.getFileByMark("cs_investmentperson_sfzf." + investId);
			if (fileForm3 != null) {
				vInvestmentPerson.setPersonSFZFId(fileForm3.getFileid());
				vInvestmentPerson.setPersonSFZFUrl(fileForm3.getWebPath());
				vInvestmentPerson.setPersonSFZFExtendName(fileForm3
						.getExtendname());
			}
			
			JsonUtil.jsonFromObject(vInvestmentPerson, true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 *  查看选中的投资客户数据
	 */
	public String seePersonInfo() {
		try {
			vInvestmentPerson = csInvestmentpersonService
					.queryInvestmentPerson(investId);
			if (vInvestmentPerson.getBelongedId() != null
					&& !"".equals(vInvestmentPerson.getBelongedId())) {
				String belongedName = "";
				String[] str = vInvestmentPerson.getBelongedId().split(",");
				Set<AppUser> userSet = appUserService.getAppUserByStr(str);
				int i = 0;
				for (AppUser s : userSet) {
					belongedName += s.getFamilyName();
					i++;
					if (i != userSet.size()) {
						belongedName = belongedName + ",";
					}
				}
				vInvestmentPerson.setBelongedName(belongedName);
			}
			List<EnterpriseBank> elist = enterpriseBankService.getBankList(Integer
					.valueOf(vInvestmentPerson.getInvestId().toString()), Short
					.valueOf("1"), Short.valueOf("0"), Short.valueOf("3"));
			if (null != elist && elist.size() > 0) {
				EnterpriseBank bank = elist.get(0);
				/*以下设置信息只是为了页面接值，彼此之间关系并不是一一对应的*/
				vInvestmentPerson.setBankName(bank.getBankname());
				vInvestmentPerson.setBankNum(bank.getAccountnum());
				vInvestmentPerson.setEnterpriseBankId(bank.getId());
				vInvestmentPerson.setBankId(bank.getBankid());
				vInvestmentPerson.setAccountType(bank.getAccountType());
				vInvestmentPerson.setKhname(bank.getName());
				vInvestmentPerson.setOpenType(bank.getOpenType());
				vInvestmentPerson.setAreaId(bank.getAreaId());
				vInvestmentPerson.setAreaName(bank.getAreaName());
				vInvestmentPerson.setBankOutletsName(bank.getBankOutletsName());
				vInvestmentPerson.setOpenCurrency(bank.getOpenCurrency());
				vInvestmentPerson.setIsEnterprise(bank.getIsEnterprise());
				vInvestmentPerson.setIsInvest(bank.getIsInvest());
				vInvestmentPerson.setRemarks(bank.getRemarks());
				vInvestmentPerson.setIscredit(bank.getIscredit());
				vInvestmentPerson.setCompanyId(bank.getCompanyId());
				FileForm fileForm4 = fileFormService
				.getFileByMark("cs_investmentperson_yhaz." + bank.getId());
				if (fileForm4 != null) {
					vInvestmentPerson.setPersonYHKZId(fileForm4.getFileid());
					vInvestmentPerson.setPersonYHKZUrl(fileForm4.getWebPath());
					vInvestmentPerson.setPersonYHKZExtendName(fileForm4
							.getExtendname());
				}
				FileForm fileForm5 = fileFormService
						.getFileByMark("cs_investmentperson_yhaf." + bank.getId());
				if (fileForm5 != null) {
					vInvestmentPerson.setPersonYHKFId(fileForm5.getFileid());
					vInvestmentPerson.setPersonYHKFUrl(fileForm5.getWebPath());
					vInvestmentPerson.setPersonYHKFExtendName(fileForm5
							.getExtendname());
				}
			}
			FileForm fileForm2 = fileFormService
					.getFileByMark("cs_investmentperson_sfzz." + investId);
			if (fileForm2 != null) {
				vInvestmentPerson.setPersonSFZZId(fileForm2.getFileid());
				vInvestmentPerson.setPersonSFZZUrl(fileForm2.getWebPath());
				vInvestmentPerson.setPersonSFZZExtendName(fileForm2
						.getExtendname());
			}
			FileForm fileForm3 = fileFormService
					.getFileByMark("cs_investmentperson_sfzf." + investId);
			if (fileForm3 != null) {
				vInvestmentPerson.setPersonSFZFId(fileForm3.getFileid());
				vInvestmentPerson.setPersonSFZFUrl(fileForm3.getWebPath());
				vInvestmentPerson.setPersonSFZFExtendName(fileForm3
						.getExtendname());
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			String taskId = this.getRequest().getParameter("taskId"); // 任务ID
			if (null != taskId && !"".equals(taskId)) {
				ProcessForm pform = processFormService.getByTaskId(taskId);
				if (pform == null) {
					pform = creditProjectService.getCommentsByTaskId(taskId);
				}
				if (pform != null && pform.getComments() != null
						&& !"".equals(pform.getComments())) {
					map.put("comments", pform.getComments());
				}
			}
			ObSystemAccount oa = obSystemAccountService
					.getByInvrstPersonId(investId);
			map.put("csInvestmentPerson", vInvestmentPerson);
			map.put("obSystemAccount", oa);
			StringBuffer buff = new StringBuffer("{success:true,data:");
			JSONSerializer json = new JSONSerializer();
			buff.append(json.serialize(map));
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 更新选中投资客户的数据
	 */
	public void updatePerson() {
		try {
			if (csInvestmentperson.getInvestId() != null
					&& !"".equals(csInvestmentperson.getInvestId())) {
				CsInvestmentperson persistent = csInvestmentpersonService
						.get(csInvestmentperson.getInvestId());
				BeanUtil.copyNotNullProperties(persistent, csInvestmentperson);
				csInvestmentpersonService.updatePerson(persistent);
				String personSFZZId = super.getRequest().getParameter(
						"personSFZZId");
				if (personSFZZId != null && !personSFZZId.equals("")) {
					Object[] obj = {
							"cs_investmentperson_sfzz."
									+ csInvestmentperson.getInvestId(),
							Integer.parseInt(personSFZZId) };
					elementHandleService.updateCon(
							"update FileForm set mark = ? where fileid = ?",
							obj);
				}
				String personSFZFId = super.getRequest().getParameter("personSFZFId");
				if (personSFZFId != null && !personSFZFId.equals("")) {
					Object[] obj = {
							"cs_investmentperson_sfzf."
									+ csInvestmentperson.getInvestId(),
							Integer.parseInt(personSFZFId) };
					elementHandleService.updateCon(
							"update FileForm set mark = ? where fileid = ?",
							obj);
				}

				if (enterpriseBank.getId() != null
						&& enterpriseBank.getId()!=0) {
					EnterpriseBank e = enterpriseBankService
							.getById(enterpriseBank.getId());
					BeanUtil.copyNotNullProperties(e, enterpriseBank);
					enterpriseBankService.save(e);
				}else{
					enterpriseBank.setId(null);
					enterpriseBank.setEnterpriseid(Integer
							.valueOf(csInvestmentperson.getInvestId().toString()));
					enterpriseBank.setCompanyId(csInvestmentperson.getCompanyId());
					enterpriseBank.setIscredit(Short.valueOf("0"));
					enterpriseBankService.save(enterpriseBank);
				}

				String personYHAZId = super.getRequest().getParameter(
						"personYHKZId");
				if (personYHAZId != null && !personYHAZId.equals("")) {
					Object[] obj = {
							"cs_investmentperson_yhaz."
									+ enterpriseBank.getId(),
							Integer.parseInt(personYHAZId) };
					elementHandleService.updateCon(
							"update FileForm set mark = ? where fileid = ?",
							obj);
				}
				String personYHAFId = super.getRequest().getParameter(
						"personYHKFId");
				if (personYHAFId != null && !personYHAFId.equals("")) {
					Object[] obj = {
							"cs_investmentperson_yhaf."
									+ enterpriseBank.getId(),
							Integer.parseInt(personYHAFId) };
					elementHandleService.updateCon(
							"update FileForm set mark = ? where fileid = ?",
							obj);
				}
				//add begin
				ObSystemAccount account=obSystemAccountService.getByInvrstPersonIdAndType(csInvestmentperson.getInvestId(),Short.valueOf("1"));
				if(null==account){
					account=new ObSystemAccount();
					account.setCompanyId(csInvestmentperson.getCompanyId());
					account.setAccountName(csInvestmentperson.getInvestName());
					account.setInvestmentPersonId(csInvestmentperson.getInvestId());
					account.setAccountNumber(csInvestmentperson.getCardnumber()+"-"+ObSystemAccount.type1.toString());
					account.setTotalMoney(new BigDecimal("0"));
					account.setInvestPersonName(csInvestmentperson.getInvestName());
					account.setInvestPersionType(Short.valueOf(ObSystemAccount.type1.toString()));
				}else{
					account.setAccountName(persistent.getInvestName());
					account.setInvestPersonName(persistent.getInvestName());
				}
				obSystemAccountService.merge(account);
				//end
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** 输出到Excel表格 */
	public void outputExcel() {
		List<VInvestmentPerson> list = null;
		String[] tableHeader = { "序号", "姓名", "登记门店", "性别", "手机号码", "证件类型",
				"证件号码", "银行账户号码" };
		try {
			Object ids=this.getRequest().getSession().getAttribute("userIds");
			Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
			String companyId= map.get("companyId");
			String userIds= map.get("userId");
			list=csInvestmentpersonService.getList(this.getRequest(),companyId,userIds);
			List arrayList = new ArrayList();
			/*========更具投资人id查询投资人银行账户========*/
			for (int i = 0; i < list.size(); i++) {
				VInvestmentPerson vIvestmentPerson = (VInvestmentPerson) list
						.get(i);
				if (null != vIvestmentPerson.getInvestId()) {
					List<EnterpriseBank> elist = enterpriseBankService
							.getBankList(Integer.valueOf(vIvestmentPerson
									.getInvestId().toString()), Short
									.valueOf("1"), Short.valueOf("0"), Short
									.valueOf("3"));
					if (null != elist && elist.size() > 0) {
						EnterpriseBank bank = elist.get(0);
						vIvestmentPerson.setBankNum(bank.getAccountnum());
					}
				}
				arrayList.add(vIvestmentPerson);
			}
			ExcelHelper.exportInvesmentPerson(arrayList, tableHeader,
					"企业投资客户信息");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**根据投资人的id,回款状态，日期查出来投资人的债权情况*/
	public String seeObligation(){
		String investPersonId = this.getRequest().getParameter("investId");
		String investObligationStatus = this.getRequest().getParameter("investObligationStatus");
		String investStartDate = this.getRequest().getParameter("investStartDate");
		String investEndDate = this.getRequest().getParameter("investEndDate");
		vObligationInvestInfo=vObligationInvestInfoService.getlistInvestPersonObligation(investPersonId, investObligationStatus, investStartDate, investEndDate);
		 if(vObligationInvestInfo!=null&&vObligationInvestInfo.size()>0){
			 for(VObligationInvestInfo temp:vObligationInvestInfo){
				 BigDecimal allBackMoney =new BigDecimal(0);
				 BigDecimal alreadyBackMoney =new BigDecimal(0);
				 BigDecimal unBackMoney =new BigDecimal(0);
				 if(temp.getFundIntentStatus()==0){
					 temp.setFundIntentStatusName(fundIntentStatusName_S);
				 }else{
					 List<SlFundIntent> list =slFundIntentService.getListByTreeId(temp.getObligationId(),temp.getInvestMentPersonId(),temp.getId());
					 if(list!=null&&list.size()>0){
						 for(SlFundIntent sl :list){
							 if(sl.getFundType().equals(obligationFundIntenList.InvestAccrual)||sl.getFundType().equals(obligationFundIntenList.InvestRoot)){//表示本金收回和利息收回
								 allBackMoney=allBackMoney.add(sl.getIncomeMoney());
								 if(temp.getFundIntentStatus()==1){//表示正在回款中的债权项目
									 if(sl.getAfterMoney().compareTo(new BigDecimal(0))!=0&&sl.getNotMoney().compareTo(new BigDecimal(0))==0){//表示已经对过账的收益
										 alreadyBackMoney=alreadyBackMoney.add(sl.getIncomeMoney()); 
									 }else{
										 unBackMoney=unBackMoney.add(sl.getIncomeMoney());
									 }
								 }
								 
							 }
						 }
						
					 }
					 if(temp.getFundIntentStatus()==2){
						 alreadyBackMoney=allBackMoney;
						 temp.setFundIntentStatusName(fundIntentStatusName_E);
					 }else if(temp.getFundIntentStatus()==1){
						 temp.setFundIntentStatusName(fundIntentStatusName_M);
					 }
				 }
				 temp.setAllBackMoney(allBackMoney);
				 temp.setAlreadyBackMoney(alreadyBackMoney);
				 temp.setUnBackMoney(unBackMoney);
			 }
		 }
		 Type type = new TypeToken<List<VObligationInvestInfo>>(){}.getType();
		 StringBuffer buff = new
		 StringBuffer("{success:true,'totalCounts':")
		 .append(vObligationInvestInfo == null ? 0 : vObligationInvestInfo.size()).append(",result:");
		 
		 Gson gson = new
		 GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		 buff.append(gson.toJson(vObligationInvestInfo, type));
		 buff.append("}");
		 jsonString = buff.toString();
		return SUCCESS;
	}
	//根据投资人的id查询出来投资人的相关信息
	public String getPersonById(){
		String investId = this.getRequest().getParameter("investId");
		csInvestmentperson = csInvestmentpersonService.get(Long.valueOf(investId));
			JsonUtil.jsonFromObject(csInvestmentperson, true);
			return SUCCESS;
	}
	/** 查询投资人和银行卡详细信息 */
	public String getPersonAndBank() {
		csInvestmentperson = new CsInvestmentperson();
		List<CsInvestmentperson> list = csInvestmentpersonService.getPersonAndBank();
		Type type = new TypeToken<List<CsInvestmentperson>>() {
		}.getType();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(list == null ? 0 : list.size()).append(",result:")
				.append(gson.toJson(list, type) + "}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	public String seeObligationById(){
		String id = this.getRequest().getParameter("id");
		VObligationInvestInfo vObligationInvestInfo= vObligationInvestInfoService.get(Long.parseLong(id));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("person", vObligationInvestInfo);
		JsonUtil.jsonFromObject(vObligationInvestInfo, true);
		return SUCCESS;
	}
	public String getBankInfo(){
		String investId=this.getRequest().getParameter("investId");
		if(null!=investId && !"".equals(investId)){
			Map<String,Object> map=new HashMap<String,Object>();
			List<EnterpriseBank> elist = enterpriseBankService.getBankList(Integer.valueOf(investId), Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("3"));
			if(null!=elist && elist.size()>0){
				EnterpriseBank bank=elist.get(0);
				map.put("enterpriseBank", bank);
				FileForm fileForm4 = fileFormService.getFileByMark("cs_investmentperson_yhaz." + bank.getId());
				if (fileForm4 != null) {
					map.put("personYHKZId", fileForm4.getFileid());
					map.put("personYHKZUrl", fileForm4.getWebPath());
					map.put("personYHKZExtendName", fileForm4.getExtendname());
				}
				FileForm fileForm5 = fileFormService.getFileByMark("cs_investmentperson_yhaf." + bank.getId());
				if (fileForm5 != null) {
					map.put("personYHKFId", fileForm5.getFileid());
					map.put("personYHKFUrl", fileForm5.getWebPath());
					map.put("personYHKFExtendName", fileForm5.getExtendname());
				}
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				StringBuffer buff = new StringBuffer("{success:true,data:").append(gson.toJson(map) + "}");
				jsonString = buff.toString();
			}
		
		}
		return SUCCESS;
	}
	public String saveBankInfo(){
		try{
			if(null==enterpriseBank.getId()){
				enterpriseBankService.save(enterpriseBank);
			}else{
				EnterpriseBank e = enterpriseBankService.getById(enterpriseBank.getId());
				BeanUtil.copyNotNullProperties(e, enterpriseBank);
				enterpriseBankService.save(e);
			}
			String personYHAZId = this.getRequest().getParameter("personYHKZId");
			if (personYHAZId != "" && !personYHAZId.equals("")) {
				Object[] obj = {
						"cs_investmentperson_yhaz."
								+ enterpriseBank.getId(),
						Integer.parseInt(personYHAZId) };
				elementHandleService.updateCon(
						"update FileForm set mark = ? where fileid = ?", obj);
			}
			String personYHAFId = this.getRequest()
					.getParameter("personYHKFId");
			if (personYHAFId != "" && !personYHAFId.equals("")) {
				Object[] obj = {
						"cs_investmentperson_yhaf."
								+ enterpriseBank.getId(),
						Integer.parseInt(personYHAFId) };
				elementHandleService.updateCon(
						"update FileForm set mark = ? where fileid = ?", obj);
			}
			jsonString="{success:true}";
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 开通第三发支付方法 
	 * @return
	 */
	public String addThirdPayCount(){
		String customerId=this.getRequest().getParameter("personId");
		String customerType=this.getRequest().getParameter("type");
		String thirdPayConfig =configMap.get("thirdPayConfig").toString();
		String thirdPayType=configMap.get("thirdPayType").toString();
		String remindMsg="";
		if(thirdPayType.equals("0")){
			if(thirdPayConfig.equals(Constants.FUIOU)){
				remindMsg=this.regeditFuiou(customerId,customerType);
			}else{
				remindMsg="尚不支持此类第三方支付开通账户";
			}
		}else{
			remindMsg="只有托管账户才需要开通第三方支付账户";
		}
		jsonString="{success:true,msg:'"+remindMsg+"'}";
		return SUCCESS;
	}
	
	/**
	 * 线下客户开通富有金账户
	 * @param customerId
	 * @param customerType
	 * @return
	 */
	private String regeditFuiou(String customerId, String customerType) {
		String remindMsg="";
		if(customerType.equals("0")){
			CsInvestmentperson cs=csInvestmentpersonService.get(Long.valueOf(customerId));
			if(cs!=null){
				if(cs.getThirdPayCongfigId()==null){
					List<EnterpriseBank> elist = enterpriseBankService.getBankList(Integer.valueOf(customerId), Short.valueOf("1"), Short.valueOf("0"), Short.valueOf("3"));
					if(elist!=null&&elist.size()>0){
						EnterpriseBank bank=elist.get(0);
						WebBankcard card =new WebBankcard();
						card.setCustomerId(cs.getInvestId());
						card.setUsername(cs.getInvestName());
						card.setCardNum(bank.getAccountnum());
						if(bank.getBankid()!=null){
							CsBank csbank=csBankService.get(bank.getBankid());
							if(csbank!=null&&csbank.getTypeKey().equals(Constants.FUIOU)){
								card.setBankId(csbank.getRemarks());
								card.setBankname(csbank.getBankname());
								if(bank.getAreaId()!=null){
									String cityname =bank.getAreaName().split("-")[2];
									if(cityname.equals("市辖区")){
										cityname=bank.getAreaName().split("-")[1];
									}
									BankCode area =bankCodeService.getBycityName(cityname,configMap.get("thirdPayConfig").toString());
									if(area!=null){
									card.setCityId(Long.valueOf(area.getCode()));
										card.setCityName(cityname);
										BpCustMember mem=new BpCustMember();
										mem.setId(cs.getInvestId());
										mem.setTruename(cs.getInvestName());
										mem.setCardcode(cs.getCardnumber());
										mem.setTelphone(cs.getCellphone());
										mem.setEmail(cs.getSelfemail());
										String orderNum = Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
										String[] ret=fuiouService.register(this.getResponse(), mem, orderNum+"hdxd", this.getBasePath(), this.getRequest(), card);
										if(ret[0].equals(Constants.CODE_SUCCESS)){
											cs.setThirdPayCongfig(configMap.get("thirdPayConfig").toString());
											cs.setThirdPayCongfigId(cs.getCellphone());
											cs.setRegistNum(orderNum);
											csInvestmentpersonService.merge(cs);
											remindMsg=ret[1];
										}else{
											remindMsg="开通第三方支付出错，错误代码："+ret[0];
										}
									}else{
										remindMsg="开通第三方支付银行卡开户行没有正确选择";
									}
								}else{
									remindMsg="开通第三方支付需要先确认开户地区是否填写";
								}
							}else{
								remindMsg="开通第三方支付需要先确认开户银行是否填写";
							}
						}else{
							remindMsg="开通第三方支付需要先确认开户银行是否填写";
						}
					}else{
						remindMsg="开通第三方支付需要线下投资客户填写银行卡信息";
					}
				}else{
					remindMsg="线下投资客户已经开通了第三方支付账户";
				}
			}else{
				remindMsg="需要开通第三方支付的线下投资客户不存在";
			}
		}
		return remindMsg;
	}
	public String listInverst(){
		List<CsInvestmentperson> listInverst=csInvestmentpersonService.listInvest(start,limit,this.getRequest());
		List<CsInvestmentperson> listInverstCount=csInvestmentpersonService.listInvest(null,null,this.getRequest());
		StringBuffer buff = new StringBuffer("{success:true,'totalProperty':")
		.append(listInverstCount!=null?listInverstCount.size():0).append(",topics:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
		/* .excludeFieldsWithoutExposeAnnotation() */.create();
		buff.append(gson.toJson(listInverst));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public String verification(){
		try{
		String cardNum=this.getRequest().getParameter("cardNum");
		String personId=this.getRequest().getParameter("personId");
		StringBuffer sb = new StringBuffer("{success:true");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		if(null==personId || "".equals(personId) || "0".equals(personId)){
			CsInvestmentperson p=csInvestmentpersonService.queryPersonCardnumber(cardNum);
			if(null!=p){
				AppUser user=appUserService.get(p.getCreaterId());
				p.setCreater(user.getFullname());
				Organization org=organizationService.get(p.getCompanyId());
				p.setShopName(org.getOrgName());
				sb.append(",msg:false,data:").append(gson.toJson(p));
			}else{
				sb.append(",msg:true");
			}
		}else{
			CsInvestmentperson person=csInvestmentpersonService.get(Long.valueOf(personId));
			if(!person.getCardnumber().equals(cardNum)){
				CsInvestmentperson p=csInvestmentpersonService.queryPersonCardnumber(cardNum);
				if(null!=p){
					AppUser user=appUserService.get(p.getCreaterId());
					p.setCreater(user.getFullname());
					Organization org=organizationService.get(p.getCompanyId());
					p.setShopName(org.getOrgName());
					sb.append(",msg:false,data:").append(gson.toJson(p));
				}else{
					sb.append(",msg:true");
				}
			}
		}
		sb.append("}");
		setJsonString(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询线下投资人列表
	 */
	public String listDown(){
		String investName = this.getRequest().getParameter("Q_investName_S_EQ");
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		String belongedId=this.getRequest().getParameter("belongedId");
		Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
		if(null!=belongedId && !"".equals(belongedId)){
			map.put("belongedId",belongedId);
		}
		if(null !=investName && !"".equals(investName)){
			map.put("investName",investName);
		}
		List<CsInvestmentperson> list= csInvestmentpersonService.getDown(map,start,limit);
		List<CsInvestmentperson> listCount= csInvestmentpersonService.getDown(map,null,null);
		for(int i=0;i<list.size();i++){
			CsInvestmentperson c=list.get(i);
			BigDecimal[] a=obSystemAccountService.sumTypeTotalMoney(c.getInvestId(),"1");
			if(a[0].compareTo(new BigDecimal("0"))==0){
				
			}else{
			  c.setTotalMoney(a[3]);
			}
		}
		Type type=new TypeToken<List<CsInvestmentperson>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(null != listCount ? listCount.size() : 0).append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		System.out.println(jsonString);
		return SUCCESS;
	}
}
