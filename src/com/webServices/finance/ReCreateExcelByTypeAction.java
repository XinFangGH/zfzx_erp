package com.webServices.finance;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */



import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO;
import nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO;
import nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO;
import nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO;
import nc.vo.crd.acc.overdue.overduebvo.OverDueBVO;
import nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO;
import nc.vo.crd.acc.realinte.realintebvo.RealInteBVO;
import nc.vo.crd.acc.realpi.realpibvo.RealPiBVO;
import nc.vo.crd.acc.realpri.realpribvo.RealPriBVO;

import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlAccrued;
import com.zhiwei.credit.model.creditFlow.finance.SlDataInfo;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.VFundDetail;
import com.zhiwei.credit.model.creditFlow.finance.VPunishDetail;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlAccruedService;
import com.zhiwei.credit.service.creditFlow.finance.SlDataInfoService;
import com.zhiwei.credit.service.creditFlow.finance.SlDataListService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.VFundDetailService;
import com.zhiwei.credit.service.creditFlow.finance.VPunishDetailService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.util.ExcelUtils;


/**
 * 
 * @author
 * 
 */
public class ReCreateExcelByTypeAction extends BaseAction {
	@Resource
	private AppUserService appUserService;
	@Resource
	private DictionaryService dictionaryService;
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
	private EnterpriseBankService  enterpriseBankService;
	@Resource
	private SlAccruedService slAccruedService;
	@Resource
	private VFundDetailService vFundDetailService;
	@Resource
	private VPunishDetailService vPunishDetailService;
	@Resource
	private SlDataListService slDataListService;
	@Resource
	private SlDataInfoService slDataInfoService;
	private Long projectId;
	private String businessType;

	
	public String createExcel(){
		Long companyId=ContextUtil.getLoginCompanyId();
		try {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		String dayDateg=this.getRequest().getParameter("dayDateg");
		String dataTypeStatus=this.getRequest().getParameter("dataTypeStatus");
			dayDateg=this.getRequest().getParameter("factdate");
			Long dataId=Long.valueOf(this.getRequest().getParameter("dataId"));
			List<SlDataInfo> slDataInfo=slDataInfoService.getListByDataId(dataId);
			
			if(dataTypeStatus.equals("2")){
				String realInterestPath=realInterestToExcel(dayDateg);
				SlDataInfo slDataInfo2=slDataInfo.get(0);
				slDataInfo2.setFilePath(realInterestPath);
				slDataInfo2.setCreateTime(new Date());
				slDataInfo2.setDataTypeName("贷款实收利息");
				slDataInfoService.save(slDataInfo2);
				
			}
						
			if(dataTypeStatus.equals("3")){
				String realchargeToPath=realchargeToExcel(dayDateg);
				SlDataInfo slDataInfo3=slDataInfo.get(1);
				slDataInfo3.setFilePath(realchargeToPath);
				slDataInfo3.setCreateTime(new Date());
				slDataInfo3.setDataTypeName("贷款实收随息手续费");
				slDataInfoService.save(slDataInfo3);
							
				}
			
				if(dataTypeStatus.equals("4")){
					String 	realpPrincipalPepayPath=realpPrincipalPepayToExcel(dayDateg);
					SlDataInfo slDataInfo4=slDataInfo.get(2);
					slDataInfo4.setFilePath(realpPrincipalPepayPath);
					slDataInfo4.setCreateTime(new Date());
					slDataInfo4.setDataTypeName("贷款实收本金");
					slDataInfoService.save(slDataInfo4);
								
					}
			
				if(dataTypeStatus.equals("5")){
					String 	realPunishInterestPath=realPunishInterestToExcel(dayDateg);
					SlDataInfo slDataInfo5=slDataInfo.get(3);
					slDataInfo5.setFilePath(realPunishInterestPath);
					slDataInfo5.setCreateTime(new Date());
					slDataInfo5.setDataTypeName("贷款实收罚息");
					slDataInfoService.save(slDataInfo5);
								
				}
			
				if(dataTypeStatus.equals("6")){
								
					String 	interestPlanPath=interestPlanToExcel(dayDateg);
					SlDataInfo slDataInfo6=slDataInfo.get(4);
					slDataInfo6.setFilePath(interestPlanPath);
					slDataInfo6.setCreateTime(new Date());
					slDataInfo6.setDataTypeName("贷款利息计划");
					slDataInfoService.save(slDataInfo6);
				}
			
				if(dataTypeStatus.equals("7")){
					String 	chargePlanPath=chargePlanToExcel(dayDateg);
					SlDataInfo slDataInfo7=slDataInfo.get(5);
					slDataInfo7.setFilePath(chargePlanPath);
					slDataInfo7.setCreateTime(new Date());
					slDataInfo7.setDataTypeName("贷款随息手续费计划");
					slDataInfoService.save(slDataInfo7);
				
			     }
			
				if(dataTypeStatus.equals("9")){
					String 	principalRepayOverduePath=principalRepayOverdueToExcel(dayDateg);
					SlDataInfo slDataInfo9=slDataInfo.get(6);
					slDataInfo9.setFilePath(principalRepayOverduePath);
					slDataInfo9.setCreateTime(new Date());
					slDataInfo9.setDataTypeName("贷款逾期");
					slDataInfoService.save(slDataInfo9);
								
				}
			
				if(dataTypeStatus.equals("8")){
					 String interestAccruedPath=interestAccruedToExcel(dayDateg);
						SlDataInfo slDataInfo8=slDataInfo.get(0);
						slDataInfo8.setFilePath(interestAccruedPath);
						slDataInfo8.setCreateTime(new Date());
						slDataInfo8.setDataTypeName("贷款利息计提");
						slDataInfoService.save(slDataInfo8);
					
				}
                   if(dataTypeStatus.equals("10")){
                	   String chargeAccruedPath=chargeAccruedToExcel(dayDateg);
           			SlDataInfo slDataInfo10=slDataInfo.get(1);
           			slDataInfo10.setFilePath(chargeAccruedPath);
           			slDataInfo10.setCreateTime(new Date());
           			slDataInfo10.setDataTypeName("贷款随收手续费计提");
           			slDataInfoService.save(slDataInfo10);
					
				}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return SUCCESS;
   }

	//贷款逾期
	public String principalRepayOverdueToExcel(String factDate){
		
		List<String[]> list=new ArrayList<String[]>();
		List<OverDueBVO> overDuelist=new ArrayList<OverDueBVO>();
		   
		   List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
		   for(SlSmallloanProject sl:sllist){
			   
				 List<SlFundIntent>  slFundIntentlist =slFundIntentService.wsgetByInterestPlan(sl.getProjectId(),sl.getBusinessType(),  factDate,"principalRepayment");  
				 BigDecimal  delaycapital=new BigDecimal("0");
				 for(SlFundIntent sd:slFundIntentlist){
					 delaycapital =delaycapital.add(sd.getNotMoney());
				 }
			     if(delaycapital.compareTo(new BigDecimal("0"))==0){
					   //都为0就不用发送了
				   }else{
					   
					   OverDueBVO overDueBVO=OverDueBVO.Factory.newInstance();
					   overDueBVO.setDelaycapital(delaycapital);
					   overDueBVO.setDef10(factDate);
						  overDueBVO.setCorpno(sl.getCompanyId().toString());
						  overDueBVO.setVnote("");
							String userid = sl.getAppUserId();// 获得项目经理ID
							String productTypeId=sl.getProductTypeId();
							Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
							AppUser appUser = appUserService.get(Long.parseLong(userid));
							overDueBVO.setDef1(appUser.getUserNumber());//客户经理编号
							overDueBVO.setDef2(appUser.getFullname());//客户经理名称
							overDueBVO.setDef3(dic.getDicKey());//产品编码
							overDueBVO.setDef4(dic.getItemValue());//产品名称
						  overDueBVO.setDeadline(null==sl.getLoanLimit()?"H01":sl.getLoanLimit());
						  overDueBVO.setDuebillno(sl.getProjectNumber());
						  Long oppositeID=sl.getOppositeID();
						  if(sl.getOppositeType().equals("company_customer")){
							Enterprise e=enterpriseService.getById(oppositeID.intValue());
							overDueBVO.setCustname("E"+e.getId().toString());
						//	overDueBVO.setCardtype("营业执照");
							overDueBVO.setCardtype("G02");
							overDueBVO.setCardno(e.getCciaa());
							overDueBVO.setCusttype("A01");
							EnterpriseBank eb= enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
									Short.valueOf("0"));//?
							overDueBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");							
						  }else{
							Person p=personService.getById(oppositeID.intValue());
							overDueBVO.setCustname("P"+p.getId().toString());
							if(p.getCardtype()==309){
								overDueBVO.setCardtype("G01");
							}else if(p.getCardtype()==310){
								overDueBVO.setCardtype("G03");
							}else if(p.getCardtype()==311){
								overDueBVO.setCardtype("G04");
							}
							overDueBVO.setCardno(p.getCardnumber());
							overDueBVO.setCusttype("A02");
							EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
									Short.valueOf("0"));
							overDueBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
						   }
						
						  if(sl.getOperationType().equals("MicroLoanBusiness")){
							  overDueBVO.setLoantype("B03");
						  }else if(sl.getOperationType().equals("SmallLoanBusiness")){
							  overDueBVO.setLoantype("B02");
						   }else if(sl.getOperationType().equals("")){  //?
							   overDueBVO.setLoantype("B01");
						  }
						  if(sl.getAccrualtype().equals("ontTimeAccrual") && sl.getIsPreposePayAccrual()==0){
						  overDueBVO.setPaytype("C01");
						  overDueBVO.setCaltype("D03");
						  }else if(sl.getAccrualtype().equals("singleInterest")){
							  overDueBVO.setPaytype("C02");
							  if(sl.getIsStartDatePay().equals("1")){
								  overDueBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  overDueBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
							  overDueBVO.setPaytype("C03");
							  if(sl.getIsStartDatePay().equals("1")){
								  overDueBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  overDueBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipal")){
							  overDueBVO.setPaytype("C04");
							  if(sl.getIsStartDatePay().equals("1")){
								  overDueBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  overDueBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
							  overDueBVO.setPaytype("C05");
								if (sl.getIsStartDatePay().equals("1")) {
									overDueBVO.setCaltype("D01");
								} else if (sl.getIsStartDatePay().equals("2")) {
									overDueBVO.setCaltype("D02");
								}
							}
						 
						/*  List mortlist=mortgageService.getByBusinessTypeProjectId(sl.getBusinessType(), sl.getProjectId());
						  if(null==mortlist |mortlist.size()==0)
						  {
							  overDueBVO.setGuatype("E01");
						  }else{
							  ProcreditMortgage procreditMortgage= (ProcreditMortgage)mortlist.get(0);
						  if(procreditMortgage.getAssuretypeid()==604){
							  
							  overDueBVO.setGuatype("E01");
						  }else if(procreditMortgage.getAssuretypeid()==605){
							  overDueBVO.setGuatype("E02");
							  
						  }else if(procreditMortgage.getAssuretypeid()==606){
							  overDueBVO.setGuatype("E03");
						  }
						 
						  }*/
						  if(sl.getAssuretypeid().equals("815")){
							  overDueBVO.setGuatype("E01");
							}else if(sl.getAssuretypeid().equals("816")){
								overDueBVO.setGuatype("E02");
							}else if(sl.getAssuretypeid().equals("817")){
								overDueBVO.setGuatype("E04");
							}else if(sl.getAssuretypeid().equals("818")){
								overDueBVO.setGuatype("E03");
							}
						  overDuelist.add(overDueBVO);
					
				   }
		   }
		 

		   String[] headOne=new String[]{"实收日期", "机构号", "贷款编码", "客户名称", "证件号码", "证件类型"
	               ,"逾期本金金额","客户类别",
	               "贷款业务品种","贷款还本方式", "贷款计息方式","贷款担保方式","币种","备注","贷款期限","客户经理编码","客户经理名称","产品编码","产品名称"};	 
			for(OverDueBVO fe:overDuelist){
			
			list.add(new String[] { fe.getDef10(), fe.getCorpno(), fe.getDuebillno(), fe.getCustname(),fe.getCardno(), fe.getCardtype(), 
			fe.getDelaycapital().toString(),fe.getCusttype(),
			fe.getLoantype(),fe.getPaytype(),fe.getCaltype(),fe.getGuatype(),fe.getCurr(),fe.getVnote(),fe.getDeadline(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4() });
			
			}
			
			String[] rt=ExcelUtils.createExcel("dataToAccount",factDate,"台账","贷款逾期",headOne,list);
		  return rt[1];
  }
	//贷款实收本金
	public String realpPrincipalPepayToExcel(String factDate){
		List<String[]> list=new ArrayList<String[]>();
		List<RealPriBVO> realPrillist=new ArrayList<RealPriBVO>();
		   
		   List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
		   for(SlSmallloanProject sl:sllist){
				 List<VFundDetail>  slFundDetaillist =vFundDetailService.wslistbyPrincipalRepay(sl.getBusinessType(), sl.getProjectId(), factDate);  
				 BigDecimal  delaycapital=new BigDecimal("0");
				 BigDecimal  capital=new BigDecimal("0");
				 for(VFundDetail sd:slFundDetaillist){
					   if(sd.getIntentDate().compareTo(DateUtil.parseDate(sd.getFactDate().toString(), "yyyy-MM-dd"))==-1){
						   delaycapital =delaycapital.add(BigDecimal.valueOf(sd.getAfterMoney()));
					   }else{
						   
						   capital =capital.add(BigDecimal.valueOf(sd.getAfterMoney()));
					   }
					   
				 }
			     if(delaycapital.compareTo(new BigDecimal("0"))==0&&capital.compareTo(new BigDecimal("0"))==0){
					   //都为0就不用发送了
				   }else{
					   RealPriBVO realPriBVO=RealPriBVO.Factory.newInstance();
					   realPriBVO.setDelaycapital(delaycapital);
					   realPriBVO.setCapital(capital);
					   realPriBVO.setDef10(factDate);
						  realPriBVO.setCorpno(sl.getCompanyId().toString());
						  realPriBVO.setVnote("");
						  String userid = sl.getAppUserId();// 获得项目经理ID
							String productTypeId=sl.getProductTypeId();
							Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
							AppUser appUser = appUserService.get(Long.parseLong(userid));
							realPriBVO.setDef1(appUser.getUserNumber());//客户经理编号
							realPriBVO.setDef2(appUser.getFullname());//客户经理名称
							realPriBVO.setDef3(dic.getDicKey());//产品编码
							realPriBVO.setDef4(dic.getItemValue());//产品名称
						  realPriBVO.setDeadline(null==sl.getLoanLimit()?"H01":sl.getLoanLimit());
					   
						  realPriBVO.setDuebillno(sl.getProjectNumber());
						  Long oppositeID=sl.getOppositeID();
						  if(sl.getOppositeType().equals("company_customer")){
							Enterprise e=enterpriseService.getById(oppositeID.intValue());
							realPriBVO.setCustname("E"+e.getId().toString());
							realPriBVO.setCardtype("G02");
							realPriBVO.setCardno(e.getCciaa());
							realPriBVO.setCusttype("A01");
							EnterpriseBank eb= enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
									Short.valueOf("0"));//?
							realPriBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
						  }else{
							Person p=personService.getById(oppositeID.intValue());
							realPriBVO.setCustname("P"+p.getId().toString());
							if(p.getCardtype()==309){
								realPriBVO.setCardtype("G01");
							}else if(p.getCardtype()==310){
								realPriBVO.setCardtype("G03");
							}else if(p.getCardtype()==311){
								realPriBVO.setCardtype("G04");
							}
							realPriBVO.setCardno(p.getCardnumber());
							realPriBVO.setCusttype("A02");
							EnterpriseBank eb= enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
									Short.valueOf("0"));
							realPriBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
						   }
						
						  if(sl.getOperationType().equals("MicroLoanBusiness")){
							  realPriBVO.setLoantype("B03");
						  }else if(sl.getOperationType().equals("SmallLoanBusiness")){
							  realPriBVO.setLoantype("B02");
						   }else if(sl.getOperationType().equals("")){  //?
							   realPriBVO.setLoantype("B01");
						  }
						  if(sl.getAccrualtype().equals("ontTimeAccrual") && sl.getIsPreposePayAccrual()==0){
						  realPriBVO.setPaytype("C01");
						  realPriBVO.setCaltype("D03");
						  }else if(sl.getAccrualtype().equals("singleInterest")){
							  realPriBVO.setPaytype("C02");
							  if(sl.getIsStartDatePay().equals("1")){
								  realPriBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  realPriBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
							  realPriBVO.setPaytype("C03");
							  if(sl.getIsStartDatePay().equals("1")){
								  realPriBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  realPriBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipal")){
							  realPriBVO.setPaytype("C04");
							  if(sl.getIsStartDatePay().equals("1")){
								  realPriBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  realPriBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
							  realPriBVO.setPaytype("C05");
								if (sl.getIsStartDatePay().equals("1")) {
									realPriBVO.setCaltype("D01");
								} else if (sl.getIsStartDatePay().equals("2")) {
									realPriBVO.setCaltype("D02");
								}
							}
						  
						  if(sl.getAssuretypeid().equals("815")){
							  realPriBVO.setGuatype("E01");
							}else if(sl.getAssuretypeid().equals("816")){
								realPriBVO.setGuatype("E02");
							}else if(sl.getAssuretypeid().equals("817")){
								realPriBVO.setGuatype("E04");
							}else if(sl.getAssuretypeid().equals("818")){
								realPriBVO.setGuatype("E03");
							}
						 
						  realPrillist.add(realPriBVO);
					  
				   }
			     
		   
		   }

	  
		   String[] headOne=new String[]{"实收日期", "机构号", "贷款编码", "客户名称", "证件号码", "证件类型"
	               ,"收未欠收本金","收欠收本金","客户类别",
	               "贷款业务品种","贷款还本方式", "贷款计息方式","贷款担保方式","币种","备注","贷款期限","客户经理编码","客户经理名称","产品编码","产品名称"};	 
			for(RealPriBVO fe:realPrillist){
			
			list.add(new String[] { fe.getDef10(), fe.getCorpno(), fe.getDuebillno(), fe.getCustname(),fe.getCardno(), fe.getCardtype(), 
			fe.getCapital().toString(),fe.getDelaycapital().toString(),fe.getCusttype(),
			fe.getLoantype(),fe.getPaytype(),fe.getCaltype(),fe.getGuatype(),fe.getCurr(),fe.getVnote(),fe.getDeadline(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4() });
			
			}
			
			
			String[] rt=ExcelUtils.createExcel("dataToAccount",factDate,"台账","贷款实收本金",headOne,list);
		  return rt[1];
  }
	//贷款实收罚息
	public String realPunishInterestToExcel(String factDate){
		List<String[]> list=new ArrayList<String[]>();
		List<RealPiBVO> realPillist=new ArrayList<RealPiBVO>();
		  List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
		   int i=0;
		   for(SlSmallloanProject sl:sllist){
			   
				 List<VPunishDetail>  slFundDetaillist =vPunishDetailService.wslistbyPunish(sl.getBusinessType(), sl.getProjectId(), factDate);  
				 BigDecimal  puinte=new BigDecimal("0");
				 for(VPunishDetail sd:slFundDetaillist){
						   
					 puinte =puinte.add(BigDecimal.valueOf(sd.getAfterMoney()));
					   
				 }
			     if(puinte.compareTo(new BigDecimal("0"))==0){
					   //都为0就不用发送了
				   }else{
					   RealPiBVO realPiBVO=RealPiBVO.Factory.newInstance();
					   realPiBVO.setDef10(factDate);
					  realPiBVO.setVnote("");
					  realPiBVO.setPuinte(puinte);
					  realPiBVO.setWlfx(puinte);
					  realPiBVO.setWnfx(puinte);
						  realPiBVO.setCorpno(sl.getCompanyId().toString());
						  realPiBVO.setDuebillno(sl.getProjectNumber());
						  String userid = sl.getAppUserId();// 获得项目经理ID
							String productTypeId=sl.getProductTypeId();
							Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
							AppUser appUser = appUserService.get(Long.parseLong(userid));
							realPiBVO.setDef1(appUser.getUserNumber());//客户经理编号
							realPiBVO.setDef2(appUser.getFullname());//客户经理名称
							realPiBVO.setDef3(dic.getDicKey());//产品编码
							realPiBVO.setDef4(dic.getItemValue());//产品名称
						  Long oppositeID=sl.getOppositeID();
						  if(sl.getOppositeType().equals("company_customer")){
							Enterprise e=enterpriseService.getById(oppositeID.intValue());
							realPiBVO.setCustname("E"+e.getId().toString());
						//	realPiBVO.setCardtype("营业执照");
							realPiBVO.setCardtype("G02");
							realPiBVO.setCardno(e.getCciaa());
							realPiBVO.setCusttype("A01");
							EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
									Short.valueOf("0"));//?
							realPiBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
						  }else{
							Person p=personService.getById(oppositeID.intValue());
							realPiBVO.setCustname("P"+p.getId().toString());
							if(p.getCardtype()==309){
								realPiBVO.setCardtype("G01");
							}else if(p.getCardtype()==310){
								realPiBVO.setCardtype("G03");
							}else if(p.getCardtype()==311){
								realPiBVO.setCardtype("G04");
							}
							realPiBVO.setCardno(p.getCardnumber());
							realPiBVO.setCusttype("A02");
							EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
									Short.valueOf("0"));
							realPiBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
						   }
						
						  if(sl.getOperationType().equals("MicroLoanBusiness")){
							  realPiBVO.setLoantype("B03");
						  }else if(sl.getOperationType().equals("SmallLoanBusiness")){
							  realPiBVO.setLoantype("B02");
						   }else if(sl.getOperationType().equals("")){  //?
							   realPiBVO.setLoantype("B01");
						  }
						  if(sl.getAccrualtype().equals("ontTimeAccrual") && sl.getIsPreposePayAccrual()==0){
						  realPiBVO.setPaytype("C01");
						  realPiBVO.setCaltype("D03");
						  }else if(sl.getAccrualtype().equals("singleInterest")){
							  realPiBVO.setPaytype("C02");
							  if(sl.getIsStartDatePay().equals("1")){
								  realPiBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  realPiBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
							  realPiBVO.setPaytype("C03");
							  if(sl.getIsStartDatePay().equals("1")){
								  realPiBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  realPiBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipal")){
							  realPiBVO.setPaytype("C04");
							  if(sl.getIsStartDatePay().equals("1")){
								  realPiBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  realPiBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
							  realPiBVO.setPaytype("C05");
								if (sl.getIsStartDatePay().equals("1")) {
									realPiBVO.setCaltype("D01");
								} else if (sl.getIsStartDatePay().equals("2")) {
									realPiBVO.setCaltype("D02");
								}
							}
						 
						  if(sl.getAssuretypeid().equals("815")){
							  realPiBVO.setGuatype("E01");
							}else if(sl.getAssuretypeid().equals("816")){
								realPiBVO.setGuatype("E02");
							}else if(sl.getAssuretypeid().equals("817")){
								realPiBVO.setGuatype("E04");
							}else if(sl.getAssuretypeid().equals("818")){
								realPiBVO.setGuatype("E03");
							}
						 
						  realPillist.add(realPiBVO);
					  }
					
					   
				   }
		 


	  
		   String[] headOne=new String[]{"实收日期", "机构号", "贷款编码", "客户名称", "证件号码", "证件类型"
	               ,"逾期贷款罚息","逾期利息罚息","违规挪用罚息","客户类别",
	               "贷款业务品种","贷款还本方式", "贷款计息方式","贷款担保方式","币种","备注","客户经理编码","客户经理名称","产品编码","产品名称"};	 
			for(RealPiBVO fe: realPillist){
			
			list.add(new String[] { fe.getDef10(), fe.getCorpno(), fe.getDuebillno(), fe.getCustname(),fe.getCardno(), fe.getCardtype(), 
			fe.getPuinte().toString(),fe.getWlfx().toString(),fe.getWnfx().toString(),fe.getCusttype(),
			fe.getLoantype(),fe.getPaytype(),fe.getCaltype(),fe.getGuatype(),fe.getCurr(),fe.getVnote(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4()});
			
			}
			
			String[] rt=ExcelUtils.createExcel("dataToAccount",factDate,"台账","贷款实收罚息",headOne,list);
		  return rt[1];
  }
	//贷款实收利息
	public String realInterestToExcel(String factDate){

		List<String[]> list=new ArrayList<String[]>();
		List<RealInteBVO> realIntellist=new ArrayList<RealInteBVO>();
	//   if(new Date(sdf.format(sd.getIntentDate())).equals(new Date(sdf.format(sd.getFactDate())))){
	   List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
	   int i=0;
	   for(SlSmallloanProject sl:sllist){
		   
			 List<VFundDetail>  slFundDetaillist =vFundDetailService.wslistbyinterest(sl.getBusinessType(), sl.getProjectId(), factDate);  
			 BigDecimal  beforeinte=new BigDecimal("0");
			 BigDecimal  currinte=new BigDecimal("0");
			 for(VFundDetail sd:slFundDetaillist){
				
				   if(sd.getIntentDate().compareTo(DateUtil.parseDate(sd.getFactDate().toString(), "yyyy-MM-dd"))==-1){
					   beforeinte =beforeinte.add(BigDecimal.valueOf(sd.getAfterMoney()));
				   }else{
					   
					   currinte =currinte.add(BigDecimal.valueOf(sd.getAfterMoney()));
				   }
				   
			 }
		     if(beforeinte.compareTo(new BigDecimal("0"))==0&&currinte.compareTo(new BigDecimal("0"))==0){
				   //都为0就不用发送了
			   }else{
				   RealInteBVO realInteBVO=RealInteBVO.Factory.newInstance();
				   realInteBVO.setBeforeinte(beforeinte);
					  realInteBVO.setCurrinte(currinte);
					  
					  realInteBVO.setDef10(factDate);
					  realInteBVO.setVnote("");
					  realInteBVO.setCorpno(sl.getCompanyId().toString());
					  realInteBVO.setDuebillno(sl.getProjectNumber());
					  String userid = sl.getAppUserId();// 获得项目经理ID
						String productTypeId=sl.getProductTypeId();
						Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
						AppUser appUser = appUserService.get(Long.parseLong(userid));
						realInteBVO.setDef1(appUser.getUserNumber());//客户经理编号
						realInteBVO.setDef2(appUser.getFullname());//客户经理名称
						realInteBVO.setDef3(dic.getDicKey());//产品编码
						realInteBVO.setDef4(dic.getItemValue());//产品名称
					  Long oppositeID=sl.getOppositeID();
					  if(sl.getOppositeType().equals("company_customer")){
						Enterprise e=enterpriseService.getById(oppositeID.intValue());
						realInteBVO.setCustname("E"+e.getId().toString());
					//	grantBVO.setCardtype("营业执照");
						realInteBVO.setCardtype("G02");
						realInteBVO.setCardno(e.getCciaa());
						realInteBVO.setCusttype("A01");
						EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
								Short.valueOf("0"));//?
						realInteBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
					  }else{
						Person p=personService.getById(oppositeID.intValue());
						realInteBVO.setCustname("P"+p.getId().toString());
						if(p.getCardtype()==309){
								realInteBVO.setCardtype("G01");
						}else if(p.getCardtype()==310){
							realInteBVO.setCardtype("G03");
						}else if(p.getCardtype()==311){
							realInteBVO.setCardtype("G04");
						}
						realInteBVO.setCardno(p.getCardnumber());
						realInteBVO.setCusttype("A02");
						EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
								Short.valueOf("0"));
						realInteBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
						
					   }
					  if(sl.getOperationType().equals("MicroLoanBusiness")){
						  realInteBVO.setLoantype("B03");
					  }else if(sl.getOperationType().equals("SmallLoanBusiness")){
						  realInteBVO.setLoantype("B02");
					   }else if(sl.getOperationType().equals("")){  //?
						   realInteBVO.setLoantype("B01");
					  }
					  if(sl.getAccrualtype().equals("ontTimeAccrual") && sl.getIsPreposePayAccrual()==0){
						  realInteBVO.setPaytype("C01");
						  realInteBVO.setCaltype("D03");
					  }else if(sl.getAccrualtype().equals("singleInterest")){
						  realInteBVO.setPaytype("C02");
						  if(sl.getIsStartDatePay().equals("1")){
							  realInteBVO.setCaltype("D01");
						  }else if(sl.getIsStartDatePay().equals("2")){
							  realInteBVO.setCaltype("D02");
						  }
					  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
						  realInteBVO.setPaytype("C03");
						  if(sl.getIsStartDatePay().equals("1")){
							  realInteBVO.setCaltype("D01");
						  }else if(sl.getIsStartDatePay().equals("2")){
							  realInteBVO.setCaltype("D02");
						  }
					  }else if(sl.getAccrualtype().equals("sameprincipal")){
						  realInteBVO.setPaytype("C04");
						  if(sl.getIsStartDatePay().equals("1")){
							  realInteBVO.setCaltype("D01");
						  }else if(sl.getIsStartDatePay().equals("2")){
							  realInteBVO.setCaltype("D02");
						  }
					  }else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
						  realInteBVO.setPaytype("C05");
							if (sl.getIsStartDatePay().equals("1")) {
								realInteBVO.setCaltype("D01");
							} else if (sl.getIsStartDatePay().equals("2")) {
								realInteBVO.setCaltype("D02");
							}
						}
					  
					  if(sl.getAssuretypeid().equals("815")){
						  realInteBVO.setGuatype("E01");
						}else if(sl.getAssuretypeid().equals("816")){
							realInteBVO.setGuatype("E02");
						}else if(sl.getAssuretypeid().equals("817")){
							realInteBVO.setGuatype("E04");
						}else if(sl.getAssuretypeid().equals("818")){
							realInteBVO.setGuatype("E03");
						}
					  realIntellist.add(realInteBVO);
					  
			   }
			 
	   }


	   String[] headOne=new String[]{"实收日期", "机构号", "贷款编码", "客户名称", "证件号码", "证件类型"
               ,"收欠息利息","收当期利息","客户类别",
               "贷款业务品种","贷款还本方式", "贷款计息方式","贷款担保方式","币种","备注","客户经理编码","客户经理名称","产品编码","产品名称"};	 
		for(RealInteBVO fe:realIntellist){
		
		list.add(new String[] { fe.getDef10(), fe.getCorpno(), fe.getDuebillno(), fe.getCustname(),fe.getCardno(), fe.getCardtype(), 
		fe.getBeforeinte().toString(),fe.getCurrinte().toString(),fe.getCusttype(),
		fe.getLoantype(),fe.getPaytype(),fe.getCaltype(),fe.getGuatype(),fe.getCurr(),fe.getVnote(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4()});
		
		}
		
		String[] rt=ExcelUtils.createExcel("dataToAccount",factDate,"台账","贷款实收利息",headOne,list);
	  return rt[1];
  }
	//贷款利息计划
	public String interestPlanToExcel(String factDate){
		 List<String[]> list=new ArrayList<String[]>();
		List<IntePlanBVO> intePlanlist=new ArrayList<IntePlanBVO>();
		List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
		   for(SlSmallloanProject sl:sllist){
			   
				 List<SlFundIntent>  slFundIntentlist =slFundIntentService.wsgetByInterestPlan(sl.getProjectId(),sl.getBusinessType(),  factDate,"loanInterest");  
				 BigDecimal  shouldinte=new BigDecimal("0");
				 BigDecimal  delayinte=new BigDecimal("0");
				 for(SlFundIntent sd:slFundIntentlist){
					 shouldinte =shouldinte.add(sd.getIncomeMoney());
					 delayinte =delayinte.add(sd.getNotMoney());
				 }
			     if(shouldinte.compareTo(new BigDecimal("0"))==0&&delayinte.compareTo(new BigDecimal("0"))==0){
					   //都为0就不用发送了
				   }else{
					  
					   IntePlanBVO intePlanBVO=IntePlanBVO.Factory.newInstance();
					   intePlanBVO.setShouldinte(shouldinte);
					   intePlanBVO.setDelayinte(delayinte);
					   intePlanBVO.setDef10(factDate);
						 intePlanBVO.setCorpno(sl.getCompanyId().toString());
						  intePlanBVO.setVnote("");
					   
						  String userid = sl.getAppUserId();// 获得项目经理ID
							String productTypeId=sl.getProductTypeId();
							Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
							AppUser appUser = appUserService.get(Long.parseLong(userid));
							intePlanBVO.setDef1(appUser.getUserNumber());//客户经理编号
							intePlanBVO.setDef2(appUser.getFullname());//客户经理名称
							intePlanBVO.setDef3(dic.getDicKey());//产品编码
							intePlanBVO.setDef4(dic.getItemValue());//产品名称
					  List<SlAccrued> slAccruedlist= slAccruedService.wslist(sl.getProjectId(), sl.getBusinessType());
					    if(null ==slAccruedlist || slAccruedlist.size()==0){
					    	intePlanBVO.setHasinte(new BigDecimal("0"));
					    }else{
					    	intePlanBVO.setHasinte(slAccruedlist.get(0).getAccruedinterestMoney());
					    }
						  intePlanBVO.setDuebillno(sl.getProjectNumber());
						  Long oppositeID=sl.getOppositeID();
						  if(sl.getOppositeType().equals("company_customer")){
							Enterprise e=enterpriseService.getById(oppositeID.intValue());
							intePlanBVO.setCustname("E"+e.getId().toString());
							intePlanBVO.setCardtype("G02");
							intePlanBVO.setCardno(e.getCciaa());
							intePlanBVO.setCusttype("A01");
							EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
									Short.valueOf("0"));//?
							intePlanBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
						  }else{
							Person p=personService.getById(oppositeID.intValue());
							intePlanBVO.setCustname("P"+p.getId().toString());
							if(p.getCardtype()==309){
								intePlanBVO.setCardtype("G01");
							}else if(p.getCardtype()==310){
								intePlanBVO.setCardtype("G03");
							}else if(p.getCardtype()==311){
								intePlanBVO.setCardtype("G04");
							}
							intePlanBVO.setCardno(p.getCardnumber());
							intePlanBVO.setCusttype("A02");
							EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
									Short.valueOf("0"));
							intePlanBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
							
						   }
						
						  if(sl.getOperationType().equals("MicroLoanBusiness")){
							  intePlanBVO.setLoantype("B03");
						  }else if(sl.getOperationType().equals("SmallLoanBusiness")){
							  intePlanBVO.setLoantype("B02");
						   }else if(sl.getOperationType().equals("")){  //?
							   intePlanBVO.setLoantype("B01");
						  }
						  if(sl.getAccrualtype().equals("ontTimeAccrual") && sl.getIsPreposePayAccrual()==0){
						  intePlanBVO.setPaytype("C01");
						  intePlanBVO.setCaltype("D03");
						  }else if(sl.getAccrualtype().equals("singleInterest")){
							  intePlanBVO.setPaytype("C02");
							  if(sl.getIsStartDatePay().equals("1")){
								  intePlanBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  intePlanBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
							  intePlanBVO.setPaytype("C03");
							  if(sl.getIsStartDatePay().equals("1")){
								  intePlanBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  intePlanBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipal")){
							  intePlanBVO.setPaytype("C04");
							  if(sl.getIsStartDatePay().equals("1")){
								  intePlanBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  intePlanBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
							  intePlanBVO.setPaytype("C05");
								if (sl.getIsStartDatePay().equals("1")) {
									intePlanBVO.setCaltype("D01");
								} else if (sl.getIsStartDatePay().equals("2")) {
									intePlanBVO.setCaltype("D02");
								}
							}
						 
						 /* List mortlist=mortgageService.getByBusinessTypeProjectId(sl.getBusinessType(), sl.getProjectId());
						  if(null==mortlist |mortlist.size()==0)
						  {
						  }else{
							  ProcreditMortgage procreditMortgage= (ProcreditMortgage)mortlist.get(0);
						  if(procreditMortgage.getAssuretypeid()==604){
							  
							  intePlanBVO.setGuatype("E01");
						  }else if(procreditMortgage.getAssuretypeid()==605){
							  intePlanBVO.setGuatype("E02");
							  
						  }else if(procreditMortgage.getAssuretypeid()==606){
							  intePlanBVO.setGuatype("E03");
						  }
					
						  }*/
						  if(sl.getAssuretypeid().equals("815")){
							  intePlanBVO.setGuatype("E01");
							}else if(sl.getAssuretypeid().equals("816")){
								intePlanBVO.setGuatype("E02");
							}else if(sl.getAssuretypeid().equals("817")){
								intePlanBVO.setGuatype("E04");
							}else if(sl.getAssuretypeid().equals("818")){
								intePlanBVO.setGuatype("E03");
							}
						  intePlanlist.add(intePlanBVO);
					  
				   }
			     
		   
		   }
		
	
		   String[] headOne=new String[]{"计划还息日期", "机构号", "贷款编码", "客户名称", "证件号码", "证件类型"
                  ,"应收金额","已计提金额","本期欠收金额","客户类别",
                  "贷款业务品种","贷款还本方式", "贷款计息方式","贷款担保方式","币种","备注","客户经理编码","客户经理名称","产品编码","产品名称"};	 
		for(IntePlanBVO fe:intePlanlist){
		
		list.add(new String[] { fe.getDef10(), fe.getCorpno(), fe.getDuebillno(), fe.getCustname(),fe.getCardno(), fe.getCardtype(), 
		fe.getShouldinte().toString(),fe.getHasinte().toString(),fe.getDelayinte().toString(),fe.getCusttype(),
		fe.getLoantype(),fe.getPaytype(),fe.getCaltype(),fe.getGuatype(),fe.getCurr(),fe.getVnote(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4()});
		
		}
		
		String[] rt=ExcelUtils.createExcel("dataToAccount",factDate,"台账","贷款利息计划",headOne,list);
			   
	  
	  
	  return rt[1];
 }	
	//贷款实收随息手续费
	public String realchargeToExcel(String factDate){
		
		 List<String[]> list=new ArrayList<String[]>();
		List<RealFeeBVO> realFeellist=new ArrayList<RealFeeBVO>();
		   
		   List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
		   for(SlSmallloanProject sl:sllist){
				 List<VFundDetail>  slFundDetaillist =vFundDetailService.wslistbyCharge(sl.getBusinessType(), sl.getProjectId(), factDate);  
				 BigDecimal  beforeinte=new BigDecimal("0");
				 BigDecimal  currinte=new BigDecimal("0");
				 for(VFundDetail sd:slFundDetaillist){
					   if(sd.getIntentDate().compareTo(DateUtil.parseDate(sd.getFactDate().toString(), "yyyy-MM-dd"))==-1){
						   beforeinte =beforeinte.add(BigDecimal.valueOf(sd.getAfterMoney()));
					   }else{
						   
						   currinte =currinte.add(BigDecimal.valueOf(sd.getAfterMoney()));
					   }
					   
				 }
			     if(beforeinte.compareTo(new BigDecimal("0"))==0&&currinte.compareTo(new BigDecimal("0"))==0){
					   //都为0就不用发送了
				   }else{
					   RealFeeBVO realFeeBVO=RealFeeBVO.Factory.newInstance();
					   
					   realFeeBVO.setBeforeinte(beforeinte);
					   realFeeBVO.setCurrinte(currinte);
						  
					   realFeeBVO.setDef10(factDate);
					  realFeeBVO.setCorpno(sl.getCompanyId().toString());
						  realFeeBVO.setVnote("");
						  String userid = sl.getAppUserId();// 获得项目经理ID
							String productTypeId=sl.getProductTypeId();
							Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
							AppUser appUser = appUserService.get(Long.parseLong(userid));
							realFeeBVO.setDef1(appUser.getUserNumber());//客户经理编号
							realFeeBVO.setDef2(appUser.getFullname());//客户经理名称
							realFeeBVO.setDef3(dic.getDicKey());//产品编码
							realFeeBVO.setDef4(dic.getItemValue());//产品名称
						  realFeeBVO.setDuebillno(sl.getProjectNumber());
						  
						  Long oppositeID=sl.getOppositeID();
						  if(sl.getOppositeType().equals("company_customer")){
							Enterprise e=enterpriseService.getById(oppositeID.intValue());
							realFeeBVO.setCustname("E"+e.getId().toString());
							realFeeBVO.setCardtype("G02");
							realFeeBVO.setCardno(e.getCciaa());
							realFeeBVO.setCusttype("A01");
							EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
									Short.valueOf("0"));//?
							realFeeBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
							
						  }else{
							Person p=personService.getById(oppositeID.intValue());
							realFeeBVO.setCustname("P"+p.getId().toString());
							if(p.getCardtype()==309){
								realFeeBVO.setCardtype("G01");
							}else if(p.getCardtype()==310){
								realFeeBVO.setCardtype("G03");
							}else if(p.getCardtype()==311){
								realFeeBVO.setCardtype("G04");
							}
							realFeeBVO.setCardno(p.getCardnumber());
							realFeeBVO.setCusttype("A02");
							EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
									Short.valueOf("0"));
							realFeeBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
						   }
						
						  if(sl.getOperationType().equals("MicroLoanBusiness")){
							  realFeeBVO.setLoantype("B03");
						  }else if(sl.getOperationType().equals("SmallLoanBusiness")){
							  realFeeBVO.setLoantype("B02");
						   }else if(sl.getOperationType().equals("")){  //?
							   realFeeBVO.setLoantype("B01");
						  }
						  if(sl.getAccrualtype().equals("ontTimeAccrual") && sl.getIsPreposePayAccrual()==0){
							  realFeeBVO.setPaytype("C01");
							  realFeeBVO.setCaltype("D03");
						  }else if(sl.getAccrualtype().equals("singleInterest")){
							  realFeeBVO.setPaytype("C02");
							  if(sl.getIsStartDatePay().equals("1")){
								  realFeeBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  realFeeBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
							  realFeeBVO.setPaytype("C03");
							  if(sl.getIsStartDatePay().equals("1")){
								  realFeeBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  realFeeBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipal")){
							  realFeeBVO.setPaytype("C04");
							  if(sl.getIsStartDatePay().equals("1")){
								  realFeeBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  realFeeBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
							  realFeeBVO.setPaytype("C05");
								if (sl.getIsStartDatePay().equals("1")) {
									realFeeBVO.setCaltype("D01");
								} else if (sl.getIsStartDatePay().equals("2")) {
									realFeeBVO.setCaltype("D02");
								}
							}
						
						  if(sl.getAssuretypeid().equals("815")){
							  realFeeBVO.setGuatype("E01");
							}else if(sl.getAssuretypeid().equals("816")){
								realFeeBVO.setGuatype("E02");
							}else if(sl.getAssuretypeid().equals("817")){
								realFeeBVO.setGuatype("E04");
							}else if(sl.getAssuretypeid().equals("818")){
								realFeeBVO.setGuatype("E03");
							}
						 
						  realFeellist.add(realFeeBVO);
					  
					   
					   
				   }
			     }
		 
	
		   String[] headOne=new String[]{"实收日期", "机构号", "贷款编码", "客户名称", "证件号码", "证件类型"
                  ,"收欠收手续费","收当期手续费","客户类别",
                  "贷款业务品种","贷款还本方式", "贷款计息方式","贷款担保方式","币种","备注","客户经理编码","客户经理名称","产品编码","产品名称"};	 
			for(RealFeeBVO fe:realFeellist){
			
			list.add(new String[] { fe.getDef10(), fe.getCorpno(), fe.getDuebillno(), fe.getCustname(),fe.getCardno(), fe.getCardtype(), 
			fe.getBeforeinte().toString(),fe.getCurrinte().toString(),fe.getCusttype(),
			fe.getLoantype(),fe.getPaytype(),fe.getCaltype(),fe.getGuatype(),fe.getCurr(),fe.getVnote(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4()});
			
			}
			
			String[] rt=ExcelUtils.createExcel("dataToAccount",factDate,"台账","贷款实收随息手续费",headOne,list);
	  
	  
	  return rt[1];
 }	
	//贷款随息手续费计划
	public String chargePlanToExcel(String factDate){
		
		
		 List<String[]> list=new ArrayList<String[]>();
		List<FeePlanBVO> feepanllist=new ArrayList<FeePlanBVO>();
		   List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
		   for(SlSmallloanProject sl:sllist){
			   
				 List<SlFundIntent>  slFundIntentlist =slFundIntentService.wsgetByInterestPlan(sl.getProjectId(),sl.getBusinessType(),  factDate,"consultationMoney");  
				 BigDecimal  shouldinte=new BigDecimal("0");
				 BigDecimal  delayinte=new BigDecimal("0");
				 for(SlFundIntent sd:slFundIntentlist){
					 shouldinte =shouldinte.add(sd.getIncomeMoney());
					 delayinte =delayinte.add(sd.getNotMoney());
				 }
			     if(shouldinte.compareTo(new BigDecimal("0"))==0&&delayinte.compareTo(new BigDecimal("0"))==0){
					   //都为0就不用发送了
				   }else{
					   FeePlanBVO feePlanBVO=FeePlanBVO.Factory.newInstance();
					   feePlanBVO.setShouldinte(shouldinte);
					   feePlanBVO.setDelayinte(delayinte);
					   feePlanBVO.setDef10(factDate);
						  feePlanBVO.setCorpno(sl.getCompanyId().toString());
						  feePlanBVO.setVnote("");
						  String userid = sl.getAppUserId();// 获得项目经理ID
							String productTypeId=sl.getProductTypeId();
							Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
							AppUser appUser = appUserService.get(Long.parseLong(userid));
							feePlanBVO.setDef1(appUser.getUserNumber());//客户经理编号
							feePlanBVO.setDef2(appUser.getFullname());//客户经理名称
							feePlanBVO.setDef3(dic.getDicKey());//产品编码
							feePlanBVO.setDef4(dic.getItemValue());//产品名称
					   List<SlAccrued> slAccruedlist= slAccruedService.wslist(sl.getProjectId(), sl.getBusinessType());
					    if(null ==slAccruedlist || slAccruedlist.size()==0){
					    	feePlanBVO.setHasinte(new BigDecimal("0"));
					    }else{
					    	feePlanBVO.setHasinte(slAccruedlist.get(0).getAccruedconsultMoney());
					    }
						  feePlanBVO.setDuebillno(sl.getProjectNumber());
						  Long oppositeID=sl.getOppositeID();
						  if(sl.getOppositeType().equals("company_customer")){
							Enterprise e=enterpriseService.getById(oppositeID.intValue());
							feePlanBVO.setCustname("E"+e.getId().toString());
							feePlanBVO.setCardtype("G02");
							feePlanBVO.setCardno(e.getCciaa());
							feePlanBVO.setCusttype("A01");
							EnterpriseBank eb= enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
									Short.valueOf("0"));//?
							feePlanBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
						  }else{
							Person p=personService.getById(oppositeID.intValue());
							feePlanBVO.setCustname("P"+p.getId().toString());
							if(p.getCardtype()==309){
								feePlanBVO.setCardtype("G01");
							}else if(p.getCardtype()==310){
								feePlanBVO.setCardtype("G03");
							}else if(p.getCardtype()==311){
								feePlanBVO.setCardtype("G04");
							}
							feePlanBVO.setCardno(p.getCardnumber());
							feePlanBVO.setCusttype("A02");
							EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
									Short.valueOf("0"));
							feePlanBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");
							
						   }
						
						  if(sl.getOperationType().equals("MicroLoanBusiness")){
							  feePlanBVO.setLoantype("B03");
						  }else if(sl.getOperationType().equals("SmallLoanBusiness")){
							  feePlanBVO.setLoantype("B02");
						   }else if(sl.getOperationType().equals("")){  //?
							   feePlanBVO.setLoantype("B01");
						  }
						  if(sl.getAccrualtype().equals("ontTimeAccrual") && sl.getIsPreposePayAccrual()==0){
						  feePlanBVO.setPaytype("C01");
						  feePlanBVO.setCaltype("D03");
						  }else if(sl.getAccrualtype().equals("singleInterest")){
							  feePlanBVO.setPaytype("C02");
							  if(sl.getIsStartDatePay().equals("1")){
								  feePlanBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  feePlanBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
							  feePlanBVO.setPaytype("C03");
							  if(sl.getIsStartDatePay().equals("1")){
								  feePlanBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  feePlanBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipal")){
							  feePlanBVO.setPaytype("C04");
							  if(sl.getIsStartDatePay().equals("1")){
								  feePlanBVO.setCaltype("D01");
							  }else if(sl.getIsStartDatePay().equals("2")){
								  feePlanBVO.setCaltype("D02");
							  }
						  }else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
							  feePlanBVO.setPaytype("C05");
								if (sl.getIsStartDatePay().equals("1")) {
									feePlanBVO.setCaltype("D01");
								} else if (sl.getIsStartDatePay().equals("2")) {
									feePlanBVO.setCaltype("D02");
								}
							}
						 
						  if(sl.getAssuretypeid().equals("815")){
							  feePlanBVO.setGuatype("E01");
							}else if(sl.getAssuretypeid().equals("816")){
								feePlanBVO.setGuatype("E02");
							}else if(sl.getAssuretypeid().equals("817")){
								feePlanBVO.setGuatype("E04");
							}else if(sl.getAssuretypeid().equals("818")){
								feePlanBVO.setGuatype("E03");
							}
						 
						  feepanllist.add(feePlanBVO);
					  }
					   
				   
			     }
	   String[] headOne=new String[]{"计划还手续费日期", "机构号", "贷款编码", "客户名称", "证件号码", "证件类型"
			                          ,"应收咨询费","已计提咨询费","本期欠收咨询费","客户类别",
			                          "贷款业务品种","贷款还本方式", "贷款计息方式","贷款担保方式","币种","备注" ,"客户经理编码","客户经理名称","产品编码","产品名称"};	 
	  for(FeePlanBVO fe:feepanllist){
		  
		  list.add(new String[] { fe.getDef10(), fe.getCorpno(), fe.getDuebillno(), fe.getCustname(),fe.getCardno(), fe.getCardtype(), 
				  fe.getShouldinte().toString(),
				  fe.getHasinte().toString(),
				  fe.getDelayinte().toString(),fe.getCusttype(),
				  fe.getLoantype(),fe.getPaytype(),fe.getCaltype(),fe.getGuatype(),fe.getCurr(),fe.getVnote(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4()});
		  
	  }

		String[] rt= ExcelUtils.createExcel("dataToAccount",factDate,"台账","贷款随息手续费计划",headOne,list);
	  
	  return rt[1];
 }
	//贷款利息计提
	public String interestAccruedToExcel(String factDate){
		 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		List<String[]> list=new ArrayList<String[]>();
		List<InteAccrBVO> inteAccrlist=new ArrayList<InteAccrBVO>();
	     Date fatctDateDate =new Date();
		try {
			fatctDateDate = sd.parse(factDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
	  for(SlSmallloanProject sl:sllist){
		  //先生存计提表
		if(null !=sl.getIsPreposePayAccrual() &&sl.getIsPreposePayAccrual()==0){  //后置付息才有计提
			List <SlAccrued> slAccruedlist =slAccruedService.wslist(sl.getProjectId(), sl.getBusinessType());
			int flag=-1;
			int sai=0;
			BigDecimal accruedinterestMoney=new BigDecimal("0");
			SlAccrued preslaccrued=null;
			SlAccrued curraccrued=new SlAccrued();
			for(SlAccrued sa:slAccruedlist){
				
				if(DateUtil.getDaysBetweenDate(sd.format(sa.getAccruedDate()),factDate)==0){
					
					flag=sai;
				}
				sai++;
			}
			if(flag !=-1){//有这条计提记录
				if(null !=slAccruedlist.get(flag).getAccruedinterestMoney()){ //利息计提也有值
					if(flag !=0){
					preslaccrued=slAccruedlist.get(flag-1);
					}
					curraccrued=slAccruedlist.get(flag);
					
					if(null!=preslaccrued &&preslaccrued.getInterestfactDate().equals(curraccrued.getInterestfactDate())){
						
						accruedinterestMoney=curraccrued.getAccruedinterestMoney().subtract(preslaccrued.getAccruedinterestMoney());
						
					}else{//如果为空的话本期是第一个
						
						accruedinterestMoney=curraccrued.getAccruedinterestMoney();
						
					}
					
				}else{//利息计提没有值
					curraccrued=slAccruedlist.get(flag);
				    List<SlFundIntent>	slFundIntentlist=slFundIntentService.getByProjectId3(sl.getProjectId(), sl.getBusinessType(),"loanInterest");
					if(null==slFundIntentlist &&slFundIntentlist.size()==0){	 //没有咨询管理费   
					
					
					}else{   
						preslaccrued=slAccruedlist.get(0);
						Integer mintfactdate=0;
					    int indexfact=-1;
					    int kfact=0;
					    for(SlFundIntent fi:slFundIntentlist){
						  if(fi.getFactDate() !=null &&DateUtil.getDaysBetweenDate(sd.format(fi.getFactDate()),factDate)>0){
							 int day= DateUtil.getDaysBetweenDate(sd.format(fi.getFactDate()),factDate);
							  if(day<mintfactdate){
								  mintfactdate=day;
								  indexfact=kfact;
							  } 
						  }
						  if(fi.getFactDate() !=null &&kfact==0){//第一次进来循环先给他赋值
							  
							  mintfactdate=999999;
						  }
						  kfact++;
					   }
					    Date cankaodate=null;
					    if(indexfact==-1){ //还没对过任何一次账，参考日期就是起始日期
					    	cankaodate=sl.getStartDate();
					    	mintfactdate=DateUtil.getDaysBetweenDate(sd.format(cankaodate),factDate);
					    }else{
					    	cankaodate=slFundIntentlist.get(indexfact).getFactDate();
					    	
					    	
					    }
					    int mintintentdate=0;
					    int indexintent=0;
					    int kintent=0;
					    for(SlFundIntent fi:slFundIntentlist){
							  if(DateUtil.getDaysBetweenDate( factDate, sd.format(fi.getIntentDate()))>0){
								 int day= DateUtil.getDaysBetweenDate( factDate, sd.format(fi.getIntentDate()));
								  if(day<mintintentdate){
									  mintintentdate=day;
									  indexintent=kintent;
								  } 
							  }
							  if(kintent==0){//第一次进来循环先给他赋值
								  
								  mintintentdate=999999;
							  }
							  kintent++;
						   }
					    
					    SlFundIntent nextSlFundIntent=slFundIntentlist.get(indexintent);
					    Integer preToNextdays=DateUtil.getDaysBetweenDate(sd.format(cankaodate),sd.format(nextSlFundIntent.getIntentDate()));
					    accruedinterestMoney=nextSlFundIntent.getNotMoney().multiply(new BigDecimal(mintfactdate.toString())).divide(new BigDecimal(preToNextdays.toString()),2,BigDecimal.ROUND_HALF_UP);
					
	           		 curraccrued.setInterestfactDate((cankaodate));
	           	   curraccrued.setAccruedinterestMoney(accruedinterestMoney);
	              	curraccrued.setAccruedDate(fatctDateDate);
		           	curraccrued.setProjectId(sl.getProjectId());
		           	curraccrued.setBusinessType(sl.getBusinessType());
		           	curraccrued.setCompanyId(sl.getCompanyId());
			          slAccruedService.save(curraccrued);
			          
			          preslaccrued=slAccruedlist.get(0);
			          if(preslaccrued.getInterestfactDate().equals(curraccrued.getInterestfactDate())){
			        	  accruedinterestMoney=curraccrued.getAccruedinterestMoney().subtract(preslaccrued.getAccruedinterestMoney());
			          }else{
			        	  
			        	  accruedinterestMoney=accruedinterestMoney;
			        	  
			          }
						
					}
				}
				
			}else{//没有这条日结记录
				
			
				 List<SlFundIntent>	slFundIntentlist=slFundIntentService.getByProjectId3(sl.getProjectId(), sl.getBusinessType(),"loanInterest");
				if(null==slAccruedlist ||slAccruedlist.size()==0){//还没有任何计提
					
					  Integer mintfactdate=0;
					    int indexfact=-1;
					    int kfact=0;
					    for(SlFundIntent fi:slFundIntentlist){
						  if(fi.getFactDate() !=null &&DateUtil.getDaysBetweenDate(sd.format(fi.getFactDate()),factDate)>0){
							 int day= DateUtil.getDaysBetweenDate(sd.format(fi.getFactDate()),factDate);
							  if(day<mintfactdate){
								  mintfactdate=day;
								  indexfact=kfact;
							  } 
						  }
						  if(kfact==0){//第一次进来循环先给他赋值
							  
							  mintfactdate=999999;
						  }
						  kfact++;
					   }
					    Date cankaodate=null;
					    if(indexfact==-1){ //还没对过任何一次账，参考日期就是起始日期
					    	cankaodate=sl.getStartDate();
					    	mintfactdate=DateUtil.getDaysBetweenDate(sd.format(cankaodate),factDate);
					    }else{
					    	cankaodate=slFundIntentlist.get(indexfact).getFactDate();
					    	
					    	
					    }
					    int mintintentdate=0;
					    int indexintent=0;
					    int kintent=0;
					    for(SlFundIntent fi:slFundIntentlist){
					    	
							  if(DateUtil.getDaysBetweenDate(factDate,sd.format(fi.getIntentDate()))>0){
								 int day= DateUtil.getDaysBetweenDate(factDate,sd.format(fi.getIntentDate()));
								  if(day<mintintentdate){
									  mintintentdate=day;
									  indexintent=kintent;
								  } 
							  }
							  if(kintent==0){//第一次进来循环先给他赋值
								  
								  mintintentdate=9999999;
							  }
							  kintent++;
						   }
					    
					    SlFundIntent nextSlFundIntent=slFundIntentlist.get(indexintent);
					    Integer preToNextdays=DateUtil.getDaysBetweenDate(sd.format(cankaodate),sd.format(nextSlFundIntent.getIntentDate()));
					    accruedinterestMoney=nextSlFundIntent.getNotMoney().multiply(new BigDecimal(mintfactdate.toString())).divide(new BigDecimal(preToNextdays.toString()),2,BigDecimal.ROUND_HALF_UP); 
					
	           		 curraccrued.setInterestfactDate((cankaodate));
	           	   curraccrued.setAccruedinterestMoney(accruedinterestMoney);
	           	curraccrued.setAccruedDate(fatctDateDate);
	           	curraccrued.setProjectId(sl.getProjectId());
	           	curraccrued.setBusinessType(sl.getBusinessType());
	           	curraccrued.setCompanyId(sl.getCompanyId());
			         slAccruedService.save(curraccrued);
			         
			        	  
			         accruedinterestMoney=accruedinterestMoney;
			        	  
				}else{  //有计提
					
					preslaccrued=slAccruedlist.get(0);
					Integer mintfactdate=0;
				    int indexfact=-1;
				    int kfact=0;
				    for(SlFundIntent fi:slFundIntentlist){
					  if(fi.getFactDate() !=null &&DateUtil.getDaysBetweenDate(sd.format(fi.getFactDate()),factDate)>0){
						 int day= DateUtil.getDaysBetweenDate(sd.format(fi.getFactDate()),factDate);
						  if(day<mintfactdate){
							  mintfactdate=day;
							  indexfact=kfact;
						  } 
					  }
					  if(kfact==0){//第一次进来循环先给他赋值
						  
						  mintfactdate=9999999;
					  }
					  kfact++;
				   }
				    Date cankaodate=null;
				    if(indexfact==-1){ //还没对过任何一次账，参考日期就是起始日期
				    	cankaodate=sl.getStartDate();
				    	mintfactdate=DateUtil.getDaysBetweenDate(sd.format(cankaodate),factDate);
				    }else{
				    	cankaodate=slFundIntentlist.get(indexfact).getFactDate();
				    	
				    	
				    }
				    int mintintentdate=0;
				    int indexintent=0;
				    int kintent=0;
				    for(SlFundIntent fi:slFundIntentlist){
						  if(DateUtil.getDaysBetweenDate(factDate ,sd.format(fi.getIntentDate()))>0){
							 int day= DateUtil.getDaysBetweenDate(factDate ,sd.format(fi.getIntentDate()));
							  if(day<mintintentdate){
								  mintintentdate=day;
								  indexintent=kintent;
							  } 
						  }
						  if(kintent==0){//第一次进来循环先给他赋值
							  
							  mintintentdate=9999999;
						  }
						  kintent++;
					   }
				    
				    SlFundIntent nextSlFundIntent=slFundIntentlist.get(indexintent);
				    Integer preToNextdays=DateUtil.getDaysBetweenDate(sd.format(cankaodate),sd.format(nextSlFundIntent.getIntentDate()));
				    accruedinterestMoney=nextSlFundIntent.getNotMoney().multiply(new BigDecimal(mintfactdate.toString())).divide(new BigDecimal(preToNextdays.toString()),2,BigDecimal.ROUND_HALF_UP);
				
           		 curraccrued.setInterestfactDate((cankaodate));
           	   curraccrued.setAccruedinterestMoney(accruedinterestMoney);
              	curraccrued.setAccruedDate(fatctDateDate);
	           	curraccrued.setProjectId(sl.getProjectId());
	           	curraccrued.setBusinessType(sl.getBusinessType());
	           	curraccrued.setCompanyId(sl.getCompanyId());
		          slAccruedService.save(curraccrued);
		          
		          preslaccrued=slAccruedlist.get(0);
		          if(preslaccrued.getInterestfactDate().equals(curraccrued.getInterestfactDate())){
		        	  accruedinterestMoney=curraccrued.getAccruedinterestMoney().subtract(preslaccrued.getAccruedinterestMoney());
		          }else{
		        	  
		        	  accruedinterestMoney=accruedinterestMoney;
		        	  
		          }
					
				}
				
				

				  
				  
				
				
			}
		
		
		 
		  
		  
		  
		  InteAccrBVO inteAccrBVO=InteAccrBVO.Factory.newInstance();
		  inteAccrBVO.setDef10(factDate);
		  inteAccrBVO.setCurrinte(accruedinterestMoney);
		  inteAccrBVO.setCorpno(sl.getCompanyId().toString());
		  inteAccrBVO.setVnote("");
		  
			String userid = sl.getAppUserId();// 获得项目经理ID
			String productTypeId=sl.getProductTypeId();
			Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
			AppUser appUser = appUserService.get(Long.parseLong(userid));
			 inteAccrBVO.setDef1(appUser.getUserNumber());//客户经理编号
			 inteAccrBVO.setDef2(appUser.getFullname());//客户经理名称
			 inteAccrBVO.setDef3(dic.getDicKey());//产品编码
			 inteAccrBVO.setDef4(dic.getItemValue());//产品名称
		  inteAccrBVO.setDuebillno(sl.getProjectNumber());
		  
		  Long oppositeID=sl.getOppositeID();
		  if(sl.getOppositeType().equals("company_customer")){
			Enterprise e=enterpriseService.getById(oppositeID.intValue());
			inteAccrBVO.setCustname("E"+e.getId().toString());
			inteAccrBVO.setCardtype("G02");
			inteAccrBVO.setCardno(e.getCciaa());
			inteAccrBVO.setCusttype("A01");
			EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
					Short.valueOf("0"));//?
			inteAccrBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");								
		  }else{
			Person p=personService.getById(oppositeID.intValue());
			inteAccrBVO.setCustname("P"+p.getId().toString());
			if(p.getCardtype()==309){
				inteAccrBVO.setCardtype("G01");
			}else if(p.getCardtype()==310){
				inteAccrBVO.setCardtype("G03");
			}else if(p.getCardtype()==311){
				inteAccrBVO.setCardtype("G04");
			}
			inteAccrBVO.setCardno(p.getCardnumber());
			inteAccrBVO.setCusttype("A02");
			EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
					Short.valueOf("0"));
			inteAccrBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");					
		   }
		
		  if(sl.getOperationType().equals("MicroLoanBusiness")){
			  inteAccrBVO.setLoantype("B03");
		  }else if(sl.getOperationType().equals("SmallLoanBusiness")){
			  inteAccrBVO.setLoantype("B02");
		   }else if(sl.getOperationType().equals("")){  //?
			   inteAccrBVO.setLoantype("B01");
		  }
		  if(sl.getAccrualtype().equals("ontTimeAccrual") && sl.getIsPreposePayAccrual()==0){
		  inteAccrBVO.setPaytype("C01");
		  inteAccrBVO.setCaltype("D03");
		  }else if(sl.getAccrualtype().equals("singleInterest")){
			  inteAccrBVO.setPaytype("C02");
			  if(sl.getIsStartDatePay().equals("1")){
				  inteAccrBVO.setCaltype("D01");
			  }else if(sl.getIsStartDatePay().equals("2")){
				  inteAccrBVO.setCaltype("D02");
			  }
		  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
			  inteAccrBVO.setPaytype("C03");
			  if(sl.getIsStartDatePay().equals("1")){
				  inteAccrBVO.setCaltype("D01");
			  }else if(sl.getIsStartDatePay().equals("2")){
				  inteAccrBVO.setCaltype("D02");
			  }
		  }else if(sl.getAccrualtype().equals("sameprincipal")){
			  inteAccrBVO.setPaytype("C04");
			  if(sl.getIsStartDatePay().equals("1")){
				  inteAccrBVO.setCaltype("D01");
			  }else if(sl.getIsStartDatePay().equals("2")){
				  inteAccrBVO.setCaltype("D02");
			  }
		  }else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
			  inteAccrBVO.setPaytype("C05");
				if (sl.getIsStartDatePay().equals("1")) {
					inteAccrBVO.setCaltype("D01");
				} else if (sl.getIsStartDatePay().equals("2")) {
					inteAccrBVO.setCaltype("D02");
				}
			}
		 
		  if(sl.getAssuretypeid().equals("815")){
			  inteAccrBVO.setGuatype("E01");
			}else if(sl.getAssuretypeid().equals("816")){
				inteAccrBVO.setGuatype("E02");
			}else if(sl.getAssuretypeid().equals("817")){
				inteAccrBVO.setGuatype("E04");
			}else if(sl.getAssuretypeid().equals("818")){
				inteAccrBVO.setGuatype("E03");
			}
		 
		  
		  inteAccrlist.add(inteAccrBVO);
		 }
	  }
	  String[] headOne=new String[]{"实收日期", "机构号", "贷款编码", "客户名称", "证件号码", "证件类型"
              ,"本次计提金额","客户类别",
              "贷款业务品种","贷款还本方式", "贷款计息方式","贷款担保方式","币种","备注" ,"客户经理编码","客户经理名称","产品编码","产品名称"};	 
		for(InteAccrBVO fe:inteAccrlist){
		
		list.add(new String[] { fe.getDef10(), fe.getCorpno(), fe.getDuebillno(), fe.getCustname(),fe.getCardno(), fe.getCardtype(), 
		fe.getCurrinte().toString(),fe.getCusttype(),
		fe.getLoantype(),fe.getPaytype(),fe.getCaltype(),fe.getGuatype(),fe.getCurr(),fe.getVnote(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4()});
		
		}
		
		String[] rt=ExcelUtils.createExcel("dataToAccount",factDate,"计提","贷款利息计提",headOne,list);
	  return rt[1];
	 
	   
 }
	// 贷款随收手续费计提
	public String chargeAccruedToExcel(String factDate){
		 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		List<String[]> list=new ArrayList<String[]>();
		List<FeeAccrBVO> feeAccrlist=new ArrayList<FeeAccrBVO>();
		  Date fatctDateDate =new Date();
			try {
				fatctDateDate = sd.parse(factDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	   List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
	  for(SlSmallloanProject sl:sllist){
		  //先生存计提表
			if(null !=sl.getIsPreposePayAccrual() &&sl.getIsPreposePayAccrual()==0){  //后置付息才有计提
				
		
				BigDecimal accruedconsultMoney=new BigDecimal("0");
				
				
				 FeeAccrBVO feeAccrBVO=FeeAccrBVO.Factory.newInstance();
				 feeAccrBVO.setDef10(factDate);
				  feeAccrBVO.setVnote("");
				  
					String userid = sl.getAppUserId();// 获得项目经理ID
					String productTypeId=sl.getProductTypeId();
					Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
					AppUser appUser = appUserService.get(Long.parseLong(userid));
					feeAccrBVO.setDef1(appUser.getUserNumber());//客户经理编号
					feeAccrBVO.setDef2(appUser.getFullname());//客户经理名称
					feeAccrBVO.setDef3(dic.getDicKey());//产品编码
					feeAccrBVO.setDef4(dic.getItemValue());//产品名称
				 feeAccrBVO.setCurrinte(accruedconsultMoney);
				  feeAccrBVO.setCorpno(sl.getCompanyId().toString());
				  feeAccrBVO.setDuebillno(sl.getProjectNumber());
				  
				  Long oppositeID=sl.getOppositeID();
				  if(sl.getOppositeType().equals("company_customer")){
					Enterprise e=enterpriseService.getById(oppositeID.intValue());
					feeAccrBVO.setCustname("E"+e.getId().toString());
					feeAccrBVO.setCardtype("G02");
					feeAccrBVO.setCardno(e.getCciaa());
					feeAccrBVO.setCusttype("A01");
					EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
							Short.valueOf("0"));//?
					feeAccrBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");																		
				  }else{
					Person p=personService.getById(oppositeID.intValue());
					feeAccrBVO.setCustname("P"+p.getId().toString());
					if(p.getCardtype()==309){
						feeAccrBVO.setCardtype("G01");
					}else if(p.getCardtype()==310){
						feeAccrBVO.setCardtype("G03");
					}else if(p.getCardtype()==311){
						feeAccrBVO.setCardtype("G04");
					}
					feeAccrBVO.setCardno(p.getCardnumber());
					feeAccrBVO.setCusttype("A02");
					EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
							Short.valueOf("0"));
					feeAccrBVO.setCurr((null==eb|| null==eb.getOpenCurrency() ||eb.getOpenCurrency()==0)?"本币":"外币");													
				   }
				
				  if(sl.getOperationType().equals("MicroLoanBusiness")){
					  feeAccrBVO.setLoantype("B03");
				  }else if(sl.getOperationType().equals("SmallLoanBusiness")){
					  feeAccrBVO.setLoantype("B02");
				   }else if(sl.getOperationType().equals("")){  //?
					   feeAccrBVO.setLoantype("B01");
				  }
				  if(sl.getAccrualtype().equals("ontTimeAccrual") && sl.getIsPreposePayAccrual()==0){
				  feeAccrBVO.setPaytype("C01");
				  feeAccrBVO.setCaltype("D03");
				  }else if(sl.getAccrualtype().equals("singleInterest")){
					  feeAccrBVO.setPaytype("C02");
					  if(sl.getIsStartDatePay().equals("1")){
						  feeAccrBVO.setCaltype("D01");
					  }else if(sl.getIsStartDatePay().equals("2")){
						  feeAccrBVO.setCaltype("D02");
					  }
				  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
					  feeAccrBVO.setPaytype("C03");
					  if(sl.getIsStartDatePay().equals("1")){
						  feeAccrBVO.setCaltype("D01");
					  }else if(sl.getIsStartDatePay().equals("2")){
						  feeAccrBVO.setCaltype("D02");
					  }
				  }else if(sl.getAccrualtype().equals("sameprincipal")){
					  feeAccrBVO.setPaytype("C04");
					  if(sl.getIsStartDatePay().equals("1")){
						  feeAccrBVO.setCaltype("D01");
					  }else if(sl.getIsStartDatePay().equals("2")){
						  feeAccrBVO.setCaltype("D02");
					  }
				  }else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
					  feeAccrBVO.setPaytype("C05");
						if (sl.getIsStartDatePay().equals("1")) {
							feeAccrBVO.setCaltype("D01");
						} else if (sl.getIsStartDatePay().equals("2")) {
							feeAccrBVO.setCaltype("D02");
						}
					}
				 
				  if(sl.getAssuretypeid().equals("815")){
					  feeAccrBVO.setGuatype("E01");
					}else if(sl.getAssuretypeid().equals("816")){
						feeAccrBVO.setGuatype("E02");
					}else if(sl.getAssuretypeid().equals("817")){
						feeAccrBVO.setGuatype("E04");
					}else if(sl.getAssuretypeid().equals("818")){
						feeAccrBVO.setGuatype("E03");
					}
				 
				  feeAccrlist.add(feeAccrBVO);
			
			}
		 
		   
	  }
	
	  String[] headOne=new String[]{"实收日期", "机构号", "贷款编码", "客户名称", "证件号码", "证件类型"
              ,"本次计提手续费","客户类别",
              "贷款业务品种","贷款还本方式", "贷款计息方式","贷款担保方式","币种","备注","客户经理编码","客户经理名称","产品编码","产品名称"};	 
		for(FeeAccrBVO fe:feeAccrlist){
		
		list.add(new String[] { fe.getDef10(), fe.getCorpno(), fe.getDuebillno(), fe.getCustname(),fe.getCardno(), fe.getCardtype(), 
		fe.getCurrinte().toString(),fe.getCusttype(),
		fe.getLoantype(),fe.getPaytype(),fe.getCaltype(),fe.getGuatype(),fe.getCurr(),fe.getVnote(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4()});
		
		}
		
		String[] rt=ExcelUtils.createExcel("dataToAccount",factDate,"计提","贷款随收手续费计提",headOne,list);
	
		return rt[1];
	 
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
