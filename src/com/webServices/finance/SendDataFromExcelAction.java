package com.webServices.finance;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */



import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.IZyhtWebServiceStub;
import nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData;
import nc.itf.crd.webservice.izyhtwebservice.FeePlanDataDocument.FeePlanData;
import nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData;
import nc.itf.crd.webservice.izyhtwebservice.IntePlanDataDocument.IntePlanData;
import nc.itf.crd.webservice.izyhtwebservice.OverDueDataDocument.OverDueData;
import nc.itf.crd.webservice.izyhtwebservice.RealFeeDataDocument.RealFeeData;
import nc.itf.crd.webservice.izyhtwebservice.RealInteDataDocument.RealInteData;
import nc.itf.crd.webservice.izyhtwebservice.RealPiiDataDocument.RealPiiData;
import nc.itf.crd.webservice.izyhtwebservice.RealPriDataDocument.RealPriData;
import nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO;
import nc.vo.crd.acc.feeplan.feeplanbvo.FeePlanBVO;
import nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO;
import nc.vo.crd.acc.inteplan.inteplanbvo.IntePlanBVO;
import nc.vo.crd.acc.overdue.overduebvo.OverDueBVO;
import nc.vo.crd.acc.realfee.realfeebvo.RealFeeBVO;
import nc.vo.crd.acc.realinte.realintebvo.RealInteBVO;
import nc.vo.crd.acc.realpi.realpibvo.RealPiBVO;
import nc.vo.crd.acc.realpri.realpribvo.RealPriBVO;

import org.apache.axis2.AxisFault;

import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.webServices.services.factory.modelfactory.ZyVo;
import com.webServices.services.factory.modelfactory.base.ZyhtVoFactory;
import com.webServices.services.factory.urlFactory.WebServicesUrl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlAccrued;
import com.zhiwei.credit.model.creditFlow.finance.SlDataList;
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
public class SendDataFromExcelAction extends BaseAction {
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
	private Long dataId;
	private String factdate;
	private ZyhtVoFactory zyvo=new ZyVo();
	
	public String getDateFromExcel(){
		
		String[] isSuccessall1=realInterestToExcel(factdate);
		String[] isSuccessall2=principalRepayOverdueToExcel(factdate);
		String[] isSuccessall3=realpPrincipalPepayToExcel(factdate);
		String[] isSuccessall4=realPunishInterestToExcel(factdate);
		String[] isSuccessall5=interestPlanToExcel(factdate);
		String[] isSuccessall6=realchargeToExcel(factdate);
		String[] isSuccessall7=chargePlanToExcel(factdate);
		String result="";
		if(isSuccessall1[0].equals("Y")||isSuccessall2[0].equals("Y")||isSuccessall3[0].equals("Y")||isSuccessall4[0].equals("Y")||
				isSuccessall5[0].equals("Y")||isSuccessall6[0].equals("Y")||isSuccessall7[0].equals("Y")){
		SlDataList	slDataList=slDataListService.get(dataId);
		slDataList.setSendStatus(Short.valueOf("0"));
		slDataList.setSendTime(new Date());
		 AppUser user=ContextUtil.getCurrentUser();
		slDataList.setSendPersonId(user.getUserId());
		slDataListService.save(slDataList);
		result="【"+isSuccessall1[1]+"】"+"【"+isSuccessall2[1]+"】"+"【"+isSuccessall3[1]+"】"+"【"+isSuccessall4[1]+"】"+"【"+isSuccessall5[1]+"】"+"【"+isSuccessall6[1]+"】"+"【"+isSuccessall7[1]+"】";
		result=result.replace("\r\n", "");
		StringBuffer sb = new StringBuffer("{success:true,flag:0,result:'"+result+"'}");
		setJsonString(sb.toString());
		return SUCCESS;
		}else if(isSuccessall1[0].equals("N")&&isSuccessall2[0].equals("N")&&isSuccessall3[0].equals("N")&&isSuccessall4[0].equals("N")&&
			isSuccessall5[0].equals("N")&&isSuccessall6[0].equals("N")&&isSuccessall7[0].equals("N")){
			result="【"+isSuccessall1[1]+"】"+"【"+isSuccessall2[1]+"】"+"【"+isSuccessall3[1]+"】"+"【"+isSuccessall4[1]+"】"+"【"+isSuccessall5[1]+"】"+"【"+isSuccessall6[1]+"】"+"【"+isSuccessall7[1]+"】";
			result=result.replace("\r\n", "");
			StringBuffer sb = new StringBuffer("{success:true,flag:0,result:'"+result+"'}");
		setJsonString(sb.toString());
		
		return SUCCESS;
		}else{
			result="未连接到财务系统 /数据传输未开启。";
			StringBuffer sb = new StringBuffer("{success:true,flag:1,result:'"+result+"'}");
			setJsonString(sb.toString());
			return SUCCESS;
		}
   }
	//贷款利息计提
	public String interestAccruedFromExcel(String factDate){
		String companyId=ContextUtil.getLoginCompanyId().toString();
		 List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
		 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			String url = WebServicesUrl.getInstances().createUrl();
			boolean isOpen=WebServicesUrl.getInstances().isOpen();
			IZyhtWebServiceStub stub=null ;
			InteAccrBVO[] inteAccrBVOItemArray=new InteAccrBVO[sllist.size()] ;
			try {
				stub = new IZyhtWebServiceStub(url);
			} catch (AxisFault e) {
				e.printStackTrace();
			}
			InteAccrDataDocument  iaData = InteAccrDataDocument.Factory.newInstance();
			InteAccrData inteAccrData = InteAccrData.Factory.newInstance();
		     Date fatctDateDate=new Date(factDate);
		  
		  for(SlSmallloanProject sl:sllist){
			  //先生存计提表
			if(sl.getIsPreposePayAccrual()==1){  //后置付息才有计提
				List <SlAccrued> slAccruedlist =slAccruedService.wslist(sl.getProjectId(), sl.getBusinessType());
				int flag=-1;
				int sai=0;
				BigDecimal accruedinterestMoney=new BigDecimal("0");
				SlAccrued preslaccrued=null;
				SlAccrued curraccrued=new SlAccrued();
				for(SlAccrued sa:slAccruedlist){
					sai++;
					if(sa.getAccruedDate().equals(fatctDateDate)){
						
						flag=sai;
					}
				}
				if(flag !=-1){//有这条日结记录
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
						
//					    List<SlFundIntent>	slFundIntentlist=slFundIntentService.getByProjectId3(sl.getProjectId(), sl.getBusinessType(),"loanInterest");
//					  for(){
//						  
//					  }
					    
//					    DateUtil.getDaysBetweenDate(sd.format());
					  curraccrued.setAccruedinterestMoney(accruedinterestMoney);
			//			 curraccrued.setInterestfactDate(interestfactDate);
					    slAccruedService.save(curraccrued);
					}
					
				}else{//没有这条日结记录
					
					 
					 curraccrued.setAccruedinterestMoney(accruedinterestMoney);
			//		 curraccrued.setInterestfactDate(interestfactDate);
					curraccrued.setAccruedDate(new Date(factDate));
					curraccrued.setProjectId(sl.getProjectId());
					curraccrued.setBusinessType(sl.getBusinessType());
					curraccrued.setProjectId(sl.getProjectId());
					  slAccruedService.save(curraccrued);
				}
			  
			  
			  InteAccrBVO inteAccrBVO=InteAccrBVO.Factory.newInstance();
			  inteAccrBVO.setDef10(factDate);
			  inteAccrBVO.setCurrinte(accruedinterestMoney);
			  inteAccrBVO.setCorpno(sl.getCompanyId().toString());
			  inteAccrBVO.setDuebillno(sl.getProjectNumber());
			  
				String userid = sl.getAppUserId();// 获得项目经理ID
				String productTypeId=sl.getProductTypeId();
				Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
				AppUser appUser = appUserService.get(Long.parseLong(userid));
				inteAccrBVO.setDef1(appUser.getUserNumber());//客户经理编号
				inteAccrBVO.setDef2(appUser.getFullname());//客户经理名称
				inteAccrBVO.setDef3(dic.getDicKey());//产品编码
				inteAccrBVO.setDef4(dic.getItemValue());//产品名称
			  
			  Long oppositeID=sl.getOppositeID();
			  if(sl.getOppositeType().equals("company_customer")){
				Enterprise e=enterpriseService.getById(oppositeID.intValue());
				inteAccrBVO.setCustname("E"+e.getId().toString());
				inteAccrBVO.setCardtype("G02");
				inteAccrBVO.setCardno(e.getCciaa());
				inteAccrBVO.setCusttype("A01");
				EnterpriseBank eb= enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),Short.valueOf("0"));//?
				inteAccrBVO.setCurr((null==eb.getOpenCurrency() || eb.getOpenCurrency()==0)?"本币":"外币");
				
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
				inteAccrBVO.setCusttype("A01");
				EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),Short.valueOf("0"));
				inteAccrBVO.setCurr(eb.getOpenCurrency()==0?"本币":"外币");
				
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
			  }else if(sl.getAccrualtype().equals("singleInterest")){
				  inteAccrBVO.setPaytype("C02");
			  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
				  inteAccrBVO.setPaytype("C03");
			  }else if(sl.getAccrualtype().equals("sameprincipal")){
				  inteAccrBVO.setPaytype("C04");
			  }
			  if(sl.getIsStartDatePay().equals("1")){
				  inteAccrBVO.setCaltype("D01");
			  }else if(sl.getIsStartDatePay().equals("2")){
				  inteAccrBVO.setCaltype("D02");
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
			  companyId=sl.getCompanyId().toString();
			  inteAccrBVOItemArray = new InteAccrBVO[]{inteAccrBVO};
			 }
		  }
		
		  System.out.print("================"+inteAccrBVOItemArray.length+"========"); 
		  
			
			 inteAccrData.setInteAccrBVOItemArray(inteAccrBVOItemArray);
			 inteAccrData.setZyhtVO(zyvo.createVo(companyId, DateUtil.getNowDateTime("yyyy-mm-dd"), inteAccrBVOItemArray.length));
			iaData.setInteAccrData(inteAccrData);
			String isSuccess="Y";
			if(isOpen){
			try {
				String [] ss = stub.inteAccrData(iaData).getInteAccrDataResponse().getReturnArray();
				isSuccess=ss[0];
				
			} catch (RemoteException e) {
				
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			}else{
				isSuccess="NP";
			}
		  
		  return isSuccess;
	   
 }
	// 贷款随收手续费计提
	public String AccruedToExcel(String factDate){
		String companyId=ContextUtil.getLoginCompanyId().toString();
		 List<SlSmallloanProject> sllist=slSmallloanProjectService.getAll();
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub=null ;
		FeeAccrBVO[] feeAccrBVOItemArray=new FeeAccrBVO[sllist.size()] ;
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		FeeAccrDataDocument  faData = FeeAccrDataDocument.Factory.newInstance();
		FeeAccrData feeAccrData = FeeAccrData.Factory.newInstance();
	     Date fatctDateDate=new Date(factDate);
	  
	  for(SlSmallloanProject sl:sllist){
		  
		  //先生存计提表
			if(sl.getIsPreposePayAccrual()==1){  //后置付息才有计提
				BigDecimal accruedconsultMoney=new BigDecimal("0");
				 FeeAccrBVO feeAccrBVO=FeeAccrBVO.Factory.newInstance();
				 feeAccrBVO.setDef10(factDate);
				  feeAccrBVO.setVnote("");
				 feeAccrBVO.setCurrinte(accruedconsultMoney);
				  feeAccrBVO.setCorpno(sl.getCompanyId().toString());
				  feeAccrBVO.setDuebillno(sl.getProjectNumber());
				  
					String userid = sl.getAppUserId();// 获得项目经理ID
					String productTypeId=sl.getProductTypeId();
					Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
					AppUser appUser = appUserService.get(Long.parseLong(userid));
					feeAccrBVO.setDef1(appUser.getUserNumber());//客户经理编号
					feeAccrBVO.setDef2(appUser.getFullname());//客户经理名称
					feeAccrBVO.setDef3(dic.getDicKey());//产品编码
					feeAccrBVO.setDef4(dic.getItemValue());//产品名称
				  
				  Long oppositeID=sl.getOppositeID();
				  if(sl.getOppositeType().equals("company_customer")){
					Enterprise e=enterpriseService.getById(oppositeID.intValue());
					feeAccrBVO.setCustname("E"+e.getId().toString());
					feeAccrBVO.setCardtype("G02");
					feeAccrBVO.setCardno(e.getCciaa());
					feeAccrBVO.setCusttype("A01");
					EnterpriseBank eb=enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),Short.valueOf("0"));//?
					feeAccrBVO.setCurr((null==eb.getOpenCurrency() || eb.getOpenCurrency()==0)?"本币":"外币");
					
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
					feeAccrBVO.setCusttype("A01");
					EnterpriseBank eb= enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),Short.valueOf("0"));
					feeAccrBVO.setCurr(eb.getOpenCurrency()==0?"本币":"外币");
					
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
				  }else if(sl.getAccrualtype().equals("singleInterest")){
					  feeAccrBVO.setPaytype("C02");
				  }else if(sl.getAccrualtype().equals("sameprincipalandInterest")){
					  feeAccrBVO.setPaytype("C03");
				  }else if(sl.getAccrualtype().equals("sameprincipal")){
					  feeAccrBVO.setPaytype("C04");
				  }
				  if(sl.getIsStartDatePay().equals("1")){
					  feeAccrBVO.setCaltype("D01");
				  }else if(sl.getIsStartDatePay().equals("2")){
					  feeAccrBVO.setCaltype("D02");
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
				  companyId=sl.getCompanyId().toString();
				   feeAccrBVOItemArray = new FeeAccrBVO[]{feeAccrBVO};
			
			}
		 
		   
	  }
	
	  System.out.print("================"+feeAccrBVOItemArray.length+"========"); 
		
		feeAccrData.setFeeAccrBVOItemArray(feeAccrBVOItemArray);
		feeAccrData.setZyhtVO(zyvo.createVo(companyId, factDate, feeAccrBVOItemArray.length));
		faData.setFeeAccrData(feeAccrData);
		String isSuccess="Y";
		if(isOpen){
		try {
			String [] ss = stub.feeAccrData(faData).getFeeAccrDataResponse().getReturnArray();
			isSuccess=ss[0];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else{
			isSuccess="NP";
		}
	  
	  return isSuccess;
  }
	//贷款逾期
	public String[] principalRepayOverdueToExcel(String factDate){
		String[] str=new String[2];
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub=null ;
		List listsize=ExcelUtils.ImportExcelData("dataToAccount",factDate,"台帐","贷款逾期",0);
		OverDueBVO[] overDueBVOItemArray=new  OverDueBVO[listsize.size()];
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		OverDueDataDocument  agData = OverDueDataDocument.Factory.newInstance();
		OverDueData overDueData = OverDueData.Factory.newInstance();
		String companyId=ContextUtil.getLoginCompanyId().toString();   
		for(int i=0;i<listsize.size();i++){
			List a=(ArrayList)listsize.get(i);
			OverDueBVO overDueBVO=OverDueBVO.Factory.newInstance();
				  String date=a.get(0).toString();
				  overDueBVO.setDef10(date);
				  overDueBVO.setCorpno(a.get(1).toString());
				  overDueBVO.setDuebillno(a.get(2).toString());
				  overDueBVO.setCustname(a.get(3).toString());
				  overDueBVO.setCardno(a.get(4).toString());
				  overDueBVO.setCardtype(a.get(5).toString());
				  overDueBVO.setDelaycapital(new BigDecimal(a.get(6).toString()));
				  overDueBVO.setCusttype(a.get(7).toString());
				  overDueBVO.setLoantype(a.get(8).toString());
				  overDueBVO.setPaytype(a.get(9).toString());
				  overDueBVO.setCaltype(a.get(10).toString());
				  overDueBVO.setGuatype(a.get(11).toString());
				  overDueBVO.setCurr(a.get(12).toString());
				  overDueBVO.setVnote(a.get(13).toString());
				  overDueBVO.setDeadline(a.get(14).toString());
				  overDueBVO.setDef1((a.get(15).toString()));
				  overDueBVO.setDef2((a.get(16).toString()));
				  overDueBVO.setDef3((a.get(17).toString()));
				  overDueBVO.setDef4((a.get(18).toString()));
				  overDueBVOItemArray[i]=overDueBVO;
				  companyId=overDueBVO.getCorpno();
			
		}
		  
		
		 overDueData.setOverDueBVOItemArray(overDueBVOItemArray);
		 overDueData.setZyhtVO(zyvo.createVo(companyId, factDate, overDueBVOItemArray.length));
		agData.setOverDueData(overDueData);
		if(isOpen){
		try {
			String [] ss = stub.overDueData(agData).getOverDueDataResponse().getReturnArray();
			str[0]=ss[0];
			str[1]=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
		}else{
			str[0]="NP";
			str[1]="NP";
		}
	  return str;
  }
	//贷款实收本金
	public String[] realpPrincipalPepayToExcel(String factDate){
		String[] str=new String[2];
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub=null ;
		List listsize=ExcelUtils.ImportExcelData("dataToAccount",factDate,"台帐","贷款实收本金",0);
		RealPriBVO[] realPriBVOItemArray=new RealPriBVO[listsize.size()] ;
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		RealPriDataDocument  rpData = RealPriDataDocument.Factory.newInstance();
		RealPriData realPriData =RealPriData.Factory.newInstance();
		String companyId=ContextUtil.getLoginCompanyId().toString();  
		for(int i=0;i<listsize.size();i++){
			List a=(ArrayList)listsize.get(i);
			RealPriBVO realPriBVO=RealPriBVO.Factory.newInstance();
				  String date=a.get(0).toString();
				  realPriBVO.setDef10(date);
				  realPriBVO.setCorpno(a.get(1).toString());
				  realPriBVO.setDuebillno(a.get(2).toString());
				  realPriBVO.setCustname(a.get(3).toString());
				  realPriBVO.setCardno(a.get(4).toString());
				  realPriBVO.setCardtype(a.get(5).toString());
				  realPriBVO.setCapital(new BigDecimal(a.get(6).toString()));
				  realPriBVO.setDelaycapital(new BigDecimal(a.get(7).toString()));
				  realPriBVO.setCusttype(a.get(8).toString());
				  realPriBVO.setLoantype(a.get(9).toString());
				  realPriBVO.setPaytype(a.get(10).toString());
				  realPriBVO.setCaltype(a.get(11).toString());
				  realPriBVO.setGuatype(a.get(12).toString());
				  realPriBVO.setCurr(a.get(13).toString());
				  realPriBVO.setVnote(a.get(14).toString());
				  realPriBVO.setDeadline(a.get(15).toString());
				  realPriBVO.setDef1((a.get(16).toString()));
				  realPriBVO.setDef2((a.get(17).toString()));
				  realPriBVO.setDef3((a.get(18).toString()));
				  realPriBVO.setDef4((a.get(19).toString()));
				  realPriBVOItemArray[i]=realPriBVO;
				  companyId=realPriBVO.getCorpno();
				
			
			
		}
		 
	
		System.out.print("================"+realPriBVOItemArray.length+"========"); 
	   
		realPriData.setRealPriBVOItemArray(realPriBVOItemArray);
		realPriData.setZyhtVO(zyvo.createVo(companyId, factDate, realPriBVOItemArray.length));
		rpData.setRealPriData(realPriData);
		if(isOpen){
		try {
			String [] ss = stub.realPriData(rpData).getRealPriDataResponse().getReturnArray();
			str[0]=ss[0];
			str[1]=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
		}else{
			str[0]="NP";
			str[1]="NP";
		}
	  return str;
  }
	//贷款实收罚息
	public String[] realPunishInterestToExcel(String factDate){
		String[] str=new String[2];
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub=null ;
		List listsize=ExcelUtils.ImportExcelData("dataToAccount",factDate,"台帐","贷款实收罚息",0);
		RealPiBVO[] realPiBVOItemArray=new RealPiBVO[listsize.size()] ;
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		RealPiiDataDocument  rpData = RealPiiDataDocument.Factory.newInstance();
		RealPiiData realPiiData = RealPiiData.Factory.newInstance();
		String companyId=ContextUtil.getLoginCompanyId().toString();  
		for(int i=0;i<listsize.size();i++){
			List a=(ArrayList)listsize.get(i);
			RealPiBVO realPiiBVO=RealPiBVO.Factory.newInstance();
				  String date=a.get(0).toString();
				  realPiiBVO.setDef10(date);
				  realPiiBVO.setCorpno(a.get(1).toString());
				  realPiiBVO.setDuebillno(a.get(2).toString());
				  realPiiBVO.setCustname(a.get(3).toString());
				  realPiiBVO.setCardno(a.get(4).toString());
				  realPiiBVO.setCardtype(a.get(5).toString());
				  realPiiBVO.setPuinte(new BigDecimal(a.get(6).toString()));
				  realPiiBVO.setWlfx(new BigDecimal(a.get(7).toString()));
				  realPiiBVO.setWnfx(new BigDecimal(a.get(8).toString()));
				  realPiiBVO.setCusttype(a.get(9).toString());
				  realPiiBVO.setLoantype(a.get(10).toString());
				  realPiiBVO.setPaytype(a.get(11).toString());
				  realPiiBVO.setCaltype(a.get(12).toString());
				  realPiiBVO.setGuatype(a.get(13).toString());
				  realPiiBVO.setCurr(a.get(14).toString());
				  realPiiBVO.setVnote(a.get(15).toString());
				  realPiiBVO.setDef1((a.get(16).toString()));
				  realPiiBVO.setDef2((a.get(17).toString()));
				  realPiiBVO.setDef3((a.get(18).toString()));
				  realPiiBVO.setDef4((a.get(19).toString()));
				  realPiBVOItemArray[i]=realPiiBVO;
				
				  companyId=realPiiBVO.getCorpno(); 
			
			
		}
		
		realPiiData.setRealPiBVOItemArray(realPiBVOItemArray);
		realPiiData.setZyhtVO(zyvo.createVo(companyId, factDate, realPiBVOItemArray.length));
		rpData.setRealPiiData(realPiiData);
		if(isOpen){
		try {
			String [] ss = stub.realPiiData(rpData).getRealPiiDataResponse().getReturnArray();
			str[0]=ss[0];
			str[1]=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else{
			str[0]="NP";
			str[1]="NP";
		}
	  
	  return str;
  }
	//贷款实收利息
	public String[] realInterestToExcel(String factDate){
		String[] str=new String[2];
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		List listsize=ExcelUtils.ImportExcelData("dataToAccount",factDate,"台帐","贷款实收利息",0);
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub=null ;
		RealInteBVO[] realInteBVOItemArray=new RealInteBVO[listsize.size()] ;
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		RealInteDataDocument  riData = RealInteDataDocument.Factory.newInstance();
		RealInteData realInteData = RealInteData.Factory.newInstance();
		String companyId=ContextUtil.getLoginCompanyId().toString();   
		for(int i=0;i<listsize.size();i++){
			List a=(ArrayList)listsize.get(i);
				  RealInteBVO realInteBVO=RealInteBVO.Factory.newInstance();
				  String date=a.get(0).toString();
				  realInteBVO.setDef10(date);
				  realInteBVO.setCorpno(a.get(1).toString());
				  realInteBVO.setDuebillno(a.get(2).toString());
				  realInteBVO.setCustname(a.get(3).toString());
				  realInteBVO.setCardno(a.get(4).toString());
				  realInteBVO.setCardtype(a.get(5).toString());
				  realInteBVO.setBeforeinte(new BigDecimal(a.get(6).toString()));
				  realInteBVO.setCurrinte(new BigDecimal(a.get(7).toString()));
				  realInteBVO.setCusttype(a.get(8).toString());
				  realInteBVO.setLoantype(a.get(9).toString());
				  realInteBVO.setPaytype(a.get(10).toString());
				  realInteBVO.setCaltype(a.get(11).toString());
				  realInteBVO.setGuatype(a.get(12).toString());
				  realInteBVO.setCurr(a.get(13).toString());
				  realInteBVO.setVnote(a.get(14).toString());
				  realInteBVO.setDef1((a.get(15).toString()));
				  realInteBVO.setDef2((a.get(16).toString()));
				  realInteBVO.setDef3((a.get(17).toString()));
				  realInteBVO.setDef4((a.get(18).toString()));
				  realInteBVOItemArray[i]=realInteBVO;
				  companyId=realInteBVO.getCorpno();
		}
		

	   System.out.print("================"+realInteBVOItemArray.length+"========"); 
		
		realInteData.setRealInteBVOItemArray(realInteBVOItemArray);
		realInteData.setZyhtVO(zyvo.createVo(companyId, factDate, realInteBVOItemArray.length));
		riData.setRealInteData(realInteData);
	if(isOpen){
		try {
			String [] ss = stub.realInteData(riData).getRealInteDataResponse().getReturnArray();
			str[0]=ss[0];
			str[1]=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
	}else{
		str[0]="NP";
		str[1]="NP";
	}
	  return str;
  }
	//贷款利息计划
	public String[] interestPlanToExcel(String factDate){
		String[] str=new String[2];
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub=null ;
		List listsize=ExcelUtils.ImportExcelData("dataToAccount",factDate,"台帐","贷款利息计划",0);
		IntePlanBVO[] intePlanBVOItemArray=new  IntePlanBVO[listsize.size()];
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		IntePlanDataDocument  ipData = IntePlanDataDocument.Factory.newInstance();
		IntePlanData intePlanData = IntePlanData.Factory.newInstance();
		String companyId=ContextUtil.getLoginCompanyId().toString(); 
		  for(int i=0;i<listsize.size();i++){
				List a=(ArrayList)listsize.get(i);
				IntePlanBVO intePlanBVO=IntePlanBVO.Factory.newInstance();
					  String date=a.get(0).toString();
					  intePlanBVO.setDef10(date);
					  intePlanBVO.setCorpno(a.get(1).toString());
					  intePlanBVO.setDuebillno(a.get(2).toString());
					  intePlanBVO.setCustname(a.get(3).toString());
					  intePlanBVO.setCardno(a.get(4).toString());
					  intePlanBVO.setCardtype(a.get(5).toString());
					  
					  intePlanBVO.setShouldinte(new BigDecimal(a.get(6).toString()));
					  intePlanBVO.setHasinte(new BigDecimal(a.get(7).toString()));
					  intePlanBVO.setDelayinte(new BigDecimal(a.get(8).toString()));
					  intePlanBVO.setCusttype(a.get(9).toString());
					  intePlanBVO.setLoantype(a.get(10).toString());
					  intePlanBVO.setPaytype(a.get(11).toString());
					  intePlanBVO.setCaltype(a.get(12).toString());
					  intePlanBVO.setGuatype(a.get(13).toString());
					  intePlanBVO.setCurr(a.get(14).toString());
					  intePlanBVO.setVnote(a.get(15).toString());
					  intePlanBVO.setDef1((a.get(16).toString()));
					  intePlanBVO.setDef2((a.get(17).toString()));
					  intePlanBVO.setDef3((a.get(18).toString()));
					  intePlanBVO.setDef4((a.get(19).toString()));
					  intePlanBVOItemArray[i]=intePlanBVO;
					
					  companyId=intePlanBVO.getCorpno();
				
				
			}


		intePlanData.setIntePlanBVOItemArray(intePlanBVOItemArray);
		intePlanData.setZyhtVO(zyvo.createVo(companyId, factDate, intePlanBVOItemArray.length));
		ipData.setIntePlanData(intePlanData);
		if(isOpen){
		try {
			String [] ss = stub.intePlanData(ipData).getIntePlanDataResponse().getReturnArray();
			str[0]=ss[0];
			str[1]=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else{
			str[0]="NP";
			str[1]="NP";
		}
	  
	  return str;
 }	
	//贷款实收随息手续费
	public String[] realchargeToExcel(String factDate){
		String[] str=new String[2];
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub=null ;
		List listsize=ExcelUtils.ImportExcelData("dataToAccount",factDate,"台帐","贷款实收随息手续费",0);
		RealFeeBVO[] realFeeBVOItemArray=new  RealFeeBVO[listsize.size()];
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		RealFeeDataDocument  rfData = RealFeeDataDocument.Factory.newInstance();
		RealFeeData realFeeData = RealFeeData.Factory.newInstance();
		String companyId=ContextUtil.getLoginCompanyId().toString();  
		for(int i=0;i<listsize.size();i++){
			List a=(ArrayList)listsize.get(i);
			RealFeeBVO realFeeBVO=RealFeeBVO.Factory.newInstance();
				  String date=a.get(0).toString();
				  realFeeBVO.setDef10(date);
				  realFeeBVO.setCorpno(a.get(1).toString());
				  realFeeBVO.setDuebillno(a.get(2).toString());
				  realFeeBVO.setCustname(a.get(3).toString());
				  realFeeBVO.setCardno(a.get(4).toString());
				  realFeeBVO.setCardtype(a.get(5).toString());
				  realFeeBVO.setBeforeinte(new BigDecimal(a.get(6).toString()));
				  realFeeBVO.setCurrinte(new BigDecimal(a.get(7).toString()));
				  realFeeBVO.setCusttype(a.get(8).toString());
				  realFeeBVO.setLoantype(a.get(9).toString());
				  realFeeBVO.setPaytype(a.get(10).toString());
				  realFeeBVO.setCaltype(a.get(11).toString());
				  realFeeBVO.setGuatype(a.get(12).toString());
				  realFeeBVO.setCurr(a.get(13).toString());
				  realFeeBVO.setVnote(a.get(14).toString());
				  realFeeBVO.setDef1((a.get(15).toString()));
				  realFeeBVO.setDef2((a.get(16).toString()));
				  realFeeBVO.setDef3((a.get(17).toString()));
				  realFeeBVO.setDef4((a.get(18).toString()));
				  realFeeBVOItemArray[i]=realFeeBVO;
				  companyId=realFeeBVO.getCorpno();
				
			
		}
	
		realFeeData.setRealFeeBVOItemArray(realFeeBVOItemArray);
		realFeeData.setZyhtVO(zyvo.createVo(companyId, factDate, realFeeBVOItemArray.length));
		rfData.setRealFeeData(realFeeData);
		if(isOpen){
		try {
			String [] ss = stub.realFeeData(rfData).getRealFeeDataResponse().getReturnArray();
			str[0]=ss[0];
			str[1]=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else{
			str[0]="NP";
			str[1]="NP";
		}
	  
	  return str;
 }	
	//贷款随息手续费计划
	public String[] chargePlanToExcel(String factDate){
		String[] str=new String[2];
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub=null ;
		List listsize=ExcelUtils.ImportExcelData("dataToAccount",factDate,"台帐","贷款随息手续费计划",0);
		FeePlanBVO[] feePlanBVOItemArray=new FeePlanBVO[listsize.size()] ;
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		 FeePlanDataDocument  fpData = FeePlanDataDocument.Factory.newInstance();
		 FeePlanData feePlanData = FeePlanData.Factory.newInstance();
		 String companyId=ContextUtil.getLoginCompanyId().toString();  
		   for(int i=0;i<listsize.size();i++){
				List a=(ArrayList)listsize.get(i);
				FeePlanBVO feePlanBVO=FeePlanBVO.Factory.newInstance();
					  String date=a.get(0).toString();
					  feePlanBVO.setDef10(date);
					  feePlanBVO.setCorpno(a.get(1).toString());
					  feePlanBVO.setDuebillno(a.get(2).toString());
					  feePlanBVO.setCustname(a.get(3).toString());
					  feePlanBVO.setCardno(a.get(4).toString());
					  feePlanBVO.setCardtype(a.get(5).toString());
					  
					  feePlanBVO.setShouldinte(new BigDecimal(a.get(6).toString()));
					  feePlanBVO.setHasinte(new BigDecimal(a.get(7).toString()));
					  feePlanBVO.setDelayinte(new BigDecimal(a.get(8).toString()));
					  feePlanBVO.setCusttype(a.get(9).toString());
					  feePlanBVO.setLoantype(a.get(10).toString());
					  feePlanBVO.setPaytype(a.get(11).toString());
					  feePlanBVO.setCaltype(a.get(12).toString());
					  feePlanBVO.setGuatype(a.get(13).toString());
					  feePlanBVO.setCurr(a.get(14).toString());
					  feePlanBVO.setVnote(a.get(15).toString());
					  feePlanBVO.setDef1((a.get(16).toString()));
					  feePlanBVO.setDef2((a.get(17).toString()));
					  feePlanBVO.setDef3((a.get(18).toString()));
					  feePlanBVO.setDef4((a.get(19).toString()));
					  feePlanBVOItemArray[i]=feePlanBVO;
					  companyId=feePlanBVO.getCorpno();
				
			}
	
		feePlanData.setFeePlanBVOItemArray(feePlanBVOItemArray);
		feePlanData.setZyhtVO(zyvo.createVo(companyId, factDate, feePlanBVOItemArray.length));
		fpData.setFeePlanData(feePlanData);
		if(isOpen){
		try {
			String [] ss = stub.feePlanData(fpData).getFeePlanDataResponse().getReturnArray();
			str[0]=ss[0];
			str[1]=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
		}else{
			str[0]="NP";
			str[1]="NP";
		}
	  return str;
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
	public Long getDataId() {
		return dataId;
	}
	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}
	public String getFactdate() {
		return factdate;
	}
	public void setFactdate(String factdate) {
		this.factdate = factdate;
	}	

  
}
