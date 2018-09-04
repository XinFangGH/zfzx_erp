package com.webServices.finance;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.IZyhtWebServiceStub;
import nc.itf.crd.webservice.izyhtwebservice.AccGrantDataDocument.AccGrantData;
import nc.vo.crd.acc.grant.grantbvo.GrantBVO;

import org.apache.axis2.AxisFault;


import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.webServices.services.factory.modelfactory.ZyVo;
import com.webServices.services.factory.modelfactory.base.ZyhtVoFactory;
import com.webServices.services.factory.urlFactory.WebServicesUrl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.project.FundIntentListPro3;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryService;

/**
 * 
 * @author
 * 
 */
public class FundIntentPrincipalLendingAction extends BaseAction {
	@Resource
	private AppUserService appUserService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private MortgageService mortgageService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private DictionaryService dictionaryService;
	public static SlSmallloanProjectService slSmallloanProjectServices;
	private Long projectId;
	private String businessType;

	// 向WebService传输数据的方法

	public String[] PrincipalLenging(GrantBVO grantBVO) {
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen = WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub = null;
		String[] str = new String[2];
		nc.vo.crd.acc.grant.grantbvo.GrantBVO[] grantBVOItemArray = null;
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		AccGrantDataDocument agData = AccGrantDataDocument.Factory
				.newInstance();
		AccGrantData accgrantData = AccGrantData.Factory.newInstance();

		grantBVOItemArray = new nc.vo.crd.acc.grant.grantbvo.GrantBVO[] { grantBVO };

		ZyhtVoFactory zyvo = new ZyVo();
		accgrantData.setGrantBVOItemArray(grantBVOItemArray);
		accgrantData.setZyhtVO(zyvo.createVo(ContextUtil.getLoginCompanyId().toString(), DateUtil.getNowDateTime("yyyy-mm-dd"),1));

		agData.setAccGrantData(accgrantData);
		if (isOpen) {

			try {
				String[] ss = stub.accGrantData(agData)
						.getAccGrantDataResponse().getReturnArray();
				str[0] = ss[0];
				str[1] = ss[2];
				return str;

			} catch (RemoteException e) {
				e.printStackTrace();
				str[0] = "NO";
				str[1] = "和财务系统对接没有成功";
				return str;
			} catch (Exception e) {
				e.printStackTrace();
				str[0] = "NO";
				str[1] = "和财务系统对接没有成功";
				return str;
			}
		} else {
			str[0] = "Y";
			str[1] = "测试";
		}
		return str;

	}

	// 从数据库中去查询用于发送给webservice的数据，讲数据存放在一个对象数组中
	public GrantBVO reserveGrantBVO(SlFundIntent slFund) {
		SlSmallloanProject sl = new SlSmallloanProject();
		sl = slSmallloanProjectService.get(projectId);
		GrantBVO grantBVO = GrantBVO.Factory.newInstance();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		grantBVO.setDef10(sd.format(slFund.getIntentDate()));// 贷款发放日期
		grantBVO.setCorpno(slFund.getCompanyId().toString());
		grantBVO.setDuebillno(sl.getProjectNumber());

		String userid = sl.getAppUserId();// 获得项目经理ID
		String productTypeId = sl.getProductTypeId();
		Dictionary dic = dictionaryService.get(Long.parseLong(productTypeId));
		AppUser appUser = appUserService.get(Long.parseLong(userid));
		grantBVO.setDef1(appUser.getUserNumber());// 客户经理编号
		grantBVO.setDef2(appUser.getFullname());// 客户经理名称
		grantBVO.setDef3(dic.getDicKey());// 产品编码
		grantBVO.setDef4(dic.getItemValue());// 产品名称

		Long oppositeID = sl.getOppositeID();
		if (sl.getOppositeType().equals("company_customer")) {
			Enterprise e = enterpriseService.getById(oppositeID
					.intValue());
			grantBVO.setCustname("E" + e.getId().toString());
			// grantBVO.setCardtype("营业执照");
			grantBVO.setCardtype("G02");
			grantBVO.setCardno(e.getCciaa());
			grantBVO.setCusttype("A01");
			EnterpriseBank eb =enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
					Short.valueOf("0"));// ?
			
			

			grantBVO.setCustbankname(eb.getName());
			grantBVO.setBanktype("");
			grantBVO.setBankarea("");
//			grantBVO.setBankname(eb.getBankname() + "_" + eb.getAreaName()
//					+ "_" + o.getName());
			grantBVO.setAccountno(eb.getAccountnum());
			grantBVO.setCurr((null == eb.getOpenCurrency() || eb
					.getOpenCurrency() == 0) ? "本币" : "外币");
			grantBVO.setAccounttype((eb.getOpenType() == 0 ? "F01" : "F02"));

		} else {
			Person p = personService.getById(oppositeID.intValue());
			grantBVO.setCustname("P" + p.getId().toString());
			if (p.getCardtype() == 309) {
				grantBVO.setCardtype("G01");
			} else if (p.getCardtype() == 310) {
				grantBVO.setCardtype("G03");
			} else if (p.getCardtype() == 311) {
				grantBVO.setCardtype("G04");
			}
			grantBVO.setCardno(p.getCardnumber());
			grantBVO.setCusttype("A02");
			EnterpriseBank eb = enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
					Short.valueOf("0"));
			
			grantBVO.setCustbankname(eb.getName());
			grantBVO.setBanktype("");
			grantBVO.setBankarea("");
			//grantBVO.setBankname(eb.getBankname()+ o.getName());
			grantBVO.setAccountno(eb.getAccountnum());
			grantBVO.setCurr((null == eb.getOpenCurrency() || eb
					.getOpenCurrency() == 0) ? "本币" : "外币");
			grantBVO.setAccounttype((eb.getOpenType() == 0 ? "F01" : "F02"));

		}

		grantBVO.setCapital(slFund.getPayMoney());
		if (sl.getOperationType().equals("MicroLoanBusiness")) {
			grantBVO.setLoantype("B03");
		} else if (sl.getOperationType().equals("SmallLoanBusiness")) {
			grantBVO.setLoantype("B02");
		} else if (sl.getOperationType().equals("")) { // ?
			grantBVO.setLoantype("B01");
		}
		if (sl.getAccrualtype().equals("ontTimeAccrual")
				&& sl.getIsPreposePayAccrual() == 0) {
			grantBVO.setCaltype("D03"); 
			grantBVO.setPaytype("C01");
		} else if (sl.getAccrualtype().equals("singleInterest")) {
			grantBVO.setPaytype("C02");
			if (sl.getIsStartDatePay().equals("1")) {
				grantBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				grantBVO.setCaltype("D02");
			}
		} else if (sl.getAccrualtype().equals("sameprincipalandInterest")) {
			grantBVO.setPaytype("C03");
			if (sl.getIsStartDatePay().equals("1")) {
				grantBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				grantBVO.setCaltype("D02");
			}
		} else if (sl.getAccrualtype().equals("sameprincipal")) {
			grantBVO.setPaytype("C04");
			if (sl.getIsStartDatePay().equals("1")) {
				grantBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				grantBVO.setCaltype("D02");
			}
		}else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
			grantBVO.setPaytype("C05");
			if (sl.getIsStartDatePay().equals("1")) {
				grantBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				grantBVO.setCaltype("D02");
			}
		}
		
		if(sl.getAssuretypeid().equals("815")){
			grantBVO.setGuatype("E01");
		}else if(sl.getAssuretypeid().equals("816")){
			grantBVO.setGuatype("E02");
		}else if(sl.getAssuretypeid().equals("817")){
			grantBVO.setGuatype("E04");
		}else if(sl.getAssuretypeid().equals("818")){
			grantBVO.setGuatype("E03");
		}
		
		grantBVO.setDeadline(null == sl.getLoanLimit() ? "H01" : sl
				.getLoanLimit());
		if(slFund.getRemark()!=null&&!slFund.getRemark().equals("")){
			grantBVO.setVnote(slFund.getRemark());
		}else{
			grantBVO.setVnote(dic.getItemValue()+"放款");
		}
		return grantBVO;
	}

	// 生成本金放贷信息存放在数据库表中（sl_fund_intent）
	public SlFundIntent saveLending() {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		SlSmallloanProject sl = new SlSmallloanProject();
		sl = slSmallloanProjectService.get(projectId);
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		list = slFundIntentService.wsgetByPrincipalLending(projectId,
				businessType);
		SlFundIntent slfund = new SlFundIntent();
		if (list.size() == 0) {
			SlFundIntent slfi = FundIntentListPro3.calculslfundintent(
					"principalLending", sl.getStartDate(),
					sl.getProjectMoney(), new BigDecimal("0"),null,sl.getIsPreposePayAccrual(),sl.getPayaccrualType(),sl.getDayOfEveryPeriod(),sd.format(sl.getStartDate()),sl.getIsInterestByOneTime(),sd.format(sl.getIntentDate()),sl.getIsStartDatePay(),sl.getIsStartDatePay().equals("1")?String.valueOf(sl.getPayintentPerioDate()):null,sl.getAccrualtype());
			slfi.setNotMoney(sl.getProjectMoney());
			slfi.setBusinessType(businessType);
			slfi.setProjectId(projectId);
			slfi.setIsValid(Short.valueOf("0"));
			slfi.setIsCheck(Short.valueOf("0"));
			slfi.setProjectName(sl.getProjectName());
			slfi.setProjectNumber(sl.getProjectNumber());
			slfi.setCompanyId(sl.getCompanyId());
			slfi.setIsAddByHand("发送给财务系统的本金放贷据");
			slFundIntentService.save(slfi);
			slfund = slfi;
		} else {
			slfund = list.get(0);
			slfund.setIsCheck(Short.valueOf("0"));
			slfund.setIsAddByHand("发送给财务系统的本金放贷据");
			slFundIntentService.save(slfund);
		}
		return slfund;
	}

	// 放款按钮调用的方法，主要操作是调用生成本金放款信息，查询得到数据发送
	public String dataSubmit() {
		//boolean isOpen = WebServicesUrl.getInstances().isOpen();
		boolean isOpen = true;
		if(isOpen){
		SlSmallloanProject sl = new SlSmallloanProject();
		sl = slSmallloanProjectService.get(projectId);
		StringBuffer sb = new StringBuffer("{success:true,sub:");
		int sub = 0;// 用于判断本金放贷信息是否成功生成： 0表示没有生成；1表示没有生成
		int flag = 0;// 用于判断发送数据是否成功准备 ： 0表示未成功；1表示没有生成
		int tip = 0;// 用于判断发送本金放贷信息是否成功 发送： 0表示未成功；1表示没有生成
		try {
			SlFundIntent slFund = saveLending();
			sl.setStates(Short.valueOf("0"));
			slSmallloanProjectService.save(sl);
			String ret = "";
			if (slFund != null) {
				sub = 1;
				GrantBVO grantBVO = reserveGrantBVO(slFund);
				if (grantBVO != null) {
					flag = 1;
					String[] ss = PrincipalLenging(grantBVO);
					if (ss[0].equalsIgnoreCase("Y")) {
						tip = 1;
						sl.setStates(Short.valueOf("1"));
						slSmallloanProjectService.save(sl);
						ret = "已向银行发送了放款通知!";
					} else if (ss[0].equalsIgnoreCase("NO")) {
						tip = 2;
						ret = "和财务系统没有对接成功，是否需要启动线下放款?";
					} else {
						if (sl.getStates() == 0) {
							tip = 0;
							sl.setStates(Short.valueOf("0"));
							slSmallloanProjectService.save(sl);
							ret = "向银行发送信息失败！错误：" + ss[1];
						} else {
							tip = 1;
							ret = "已向银行发送了放款通知！";
						}
					}
				} else {
					flag = 0;
					sl.setStates(Short.valueOf("0"));
					slSmallloanProjectService.save(sl);
					ret = "未从财务系统获取数据，请联系管理员！";
				}
			} else {
				sub = 0;
				ret = "本金贷款信息生成出错， 款项计划信息表为空!";
			}
			sb.append(sub);
			sb.append(",flag:");
			sb.append(flag);
			sb.append(",flag1:");
			sb.append(tip);
			sb.append(",result:'" + ret + "'");
			sb.append("}");
			setJsonString(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();

		}
		}
		return SUCCESS;
	}

	// 用与接收返回的银行账户错误信息来改变项目中的states字段值
	public static boolean reciveBankInfoErro(String projectNumber,
			String infoName) {
		boolean ret = false;
		SlSmallloanProject sl = new SlSmallloanProject();
		slSmallloanProjectServices = (SlSmallloanProjectService) AppUtil
				.getBean("slSmallloanProjectService");
		sl = slSmallloanProjectServices.findByprojectNumber(projectNumber);
		if (sl == null) {
			System.out.print("贷款编号不存在！");
			ret = false;
		} else if (infoName.equalsIgnoreCase("N")) {
			sl.setStates(Short.valueOf("2"));
			slSmallloanProjectServices.save(sl);
			ret = true;
		}
		return ret;
	}

	// 手动放款按钮调用的方法，主要操作是调用生成本金放款信息，然后标记本金放款记录为手动放款
	public String handDateSubmit() {
		SlSmallloanProject sl = new SlSmallloanProject();
		sl = slSmallloanProjectService.get(projectId);
		StringBuffer sb = new StringBuffer("{success:true,sub:");
		int sub = 0;// 用于判断本金放贷信息是否成功生成： 0表示没有生成；1表示没有生成
		try {
			SlFundIntent slFund = saveLending();
			sl.setStates(Short.valueOf("0"));
			slSmallloanProjectService.save(sl);
			String ret = "";
			if (slFund != null) {
				slFund.setIsAddByHand("手动放款 后生成的本金放贷据");
				slFundIntentService.save(slFund);
				sub = 1;
				sl.setStates(Short.valueOf("1"));
				slSmallloanProjectService.save(sl);
				ret = "线下放款已经启动，并生成了本金放贷记录！";
			} else {
				sub = 0;
				ret = "本金贷款信息生成出错, 款项计划信息表为空!";
			}
			sb.append(sub);
			sb.append(",result:'" + ret + "'");
			sb.append("}");
			setJsonString(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}
