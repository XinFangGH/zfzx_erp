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

import nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument;
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
import nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData;
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
import nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO;
import nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO;

import org.apache.axis2.AxisFault;

import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.webServices.services.factory.modelfactory.ZyVo;
import com.webServices.services.factory.modelfactory.base.ZyhtVoFactory;
import com.webServices.services.factory.urlFactory.WebServicesUrl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
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
import com.zhiwei.credit.util.ExcelUtils;


/**
 * 
 * @author
 * 
 */
public class ReSendDataByTypeAction extends BaseAction {
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
		String dataTypeStatus=this.getRequest().getParameter("dataTypeStatus");
		String isSuccessall="";
		if(dataTypeStatus.equals("2")){
			 isSuccessall=realInterestToExcel(factdate);
		}
		if(dataTypeStatus.equals("3")){
			 isSuccessall=realchargeToExcel(factdate);
		}
		if(dataTypeStatus.equals("4")){
			 isSuccessall=realpPrincipalPepayToExcel(factdate);
				}
		if(dataTypeStatus.equals("5")){
			 isSuccessall=realPunishInterestToExcel(factdate);
		}
		if(dataTypeStatus.equals("6")){
			 isSuccessall=interestPlanToExcel(factdate);
		}
		if(dataTypeStatus.equals("7")){
			isSuccessall=chargePlanToExcel(factdate);
		}
		if(dataTypeStatus.equals("9")){
			 
			 isSuccessall=principalRepayOverdueToExcel(factdate);
		}
	
		
		if(dataTypeStatus.equals("8")){
			 isSuccessall=interestAccruedFromExcel(factdate);
		}
        if(dataTypeStatus.equals("10")){
        	 isSuccessall=AccruedToExcel(factdate);
		}
        if(isSuccessall!=null&&!isSuccessall.equals("")){
        	StringBuffer sb = new StringBuffer("{success:true,flag:0,result:'"+isSuccessall+"'}");
    		setJsonString(sb.toString());
    		return SUCCESS;
        }else{
        	String str="连接财务系统错误，请检查。";
        	StringBuffer sb = new StringBuffer("{success:true,flag:1,result:'"+str+"'}");
    		setJsonString(sb.toString());
    		return SUCCESS;
        }
   }
	//贷款利息计提
	public String interestAccruedFromExcel(String factDate){
		 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			String url = WebServicesUrl.getInstances().createUrl();
			boolean isOpen=WebServicesUrl.getInstances().isOpen();
			IZyhtWebServiceStub stub=null ;
			List listsize=ExcelUtils.ImportExcelData("dataToAccount",factDate,"计提","贷款利息计提",0);
			InteAccrBVO[] inteAccrBVOItemArray=new InteAccrBVO[listsize.size()] ;
			try {
				stub = new IZyhtWebServiceStub(url);
			} catch (AxisFault e) {
				e.printStackTrace();
			}
			InteAccrDataDocument  iaData = InteAccrDataDocument.Factory.newInstance();
			InteAccrData inteAccrData = InteAccrData.Factory.newInstance();
			String companyId=ContextUtil.getLoginCompanyId().toString();   
		     for(int i=0;i<listsize.size();i++){
					List a=(ArrayList)listsize.get(i);
					InteAccrBVO inteAccrBVO=InteAccrBVO.Factory.newInstance();
						  String date=a.get(0).toString();
						  inteAccrBVO.setDef10(date);
						  inteAccrBVO.setCorpno(a.get(1).toString());
						  inteAccrBVO.setDuebillno(a.get(2).toString());
						  inteAccrBVO.setCustname(a.get(3).toString());
						  inteAccrBVO.setCardno(a.get(4).toString());
						  inteAccrBVO.setCardtype(a.get(5).toString());
						  inteAccrBVO.setCurrinte(new BigDecimal(a.get(6).toString()));
						  inteAccrBVO.setCusttype(a.get(7).toString());
						  inteAccrBVO.setLoantype(a.get(8).toString());
						  inteAccrBVO.setPaytype(a.get(9).toString());
						  inteAccrBVO.setCaltype(a.get(10).toString());
						  inteAccrBVO.setGuatype(a.get(11).toString());
						  inteAccrBVO.setCurr(a.get(12).toString());
						  inteAccrBVO.setVnote(a.get(13).toString());
						  inteAccrBVO.setDef1((a.get(14).toString()));
						  inteAccrBVO.setDef2((a.get(15).toString()));
						  inteAccrBVO.setDef3((a.get(16).toString()));
						  inteAccrBVO.setDef4((a.get(17).toString()));
						  inteAccrBVOItemArray[i]=inteAccrBVO;

						  companyId=inteAccrBVO.getCorpno();

				}
			
			inteAccrData.setInteAccrBVOItemArray(inteAccrBVOItemArray);
			inteAccrData.setZyhtVO(zyvo.createVo(companyId,factDate, inteAccrBVOItemArray.length));
			iaData.setInteAccrData(inteAccrData);
			String isSuccess="";
			if(isOpen){
			try {
				String [] ss = stub.inteAccrData(iaData).getInteAccrDataResponse().getReturnArray();
				isSuccess=ss[2];
				
			} catch (RemoteException e) {
				
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
		  
		  return isSuccess;
	   
 }
	// 贷款随收手续费计提
	public String AccruedToExcel(String factDate){
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen=WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub=null ;
		List listsize=ExcelUtils.ImportExcelData("dataToAccount",factDate,"计提","贷款随收手续费计提",0);
		FeeAccrBVO[] feeAccrBVOItemArray=new FeeAccrBVO[listsize.size()] ;
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		FeeAccrDataDocument  faData = FeeAccrDataDocument.Factory.newInstance();
		FeeAccrData feeAccrData = FeeAccrData.Factory.newInstance();
		String companyId=ContextUtil.getLoginCompanyId().toString();  
	     for(int i=0;i<listsize.size();i++){
				List a=(ArrayList)listsize.get(i);
				FeeAccrBVO feeAccrBVO=FeeAccrBVO.Factory.newInstance();
					  String date=a.get(0).toString();
					  feeAccrBVO.setDef10(date);
					  feeAccrBVO.setCorpno(a.get(1).toString());
					  feeAccrBVO.setDuebillno(a.get(2).toString());
					  feeAccrBVO.setCustname(a.get(3).toString());
					  feeAccrBVO.setCardno(a.get(4).toString());
					  feeAccrBVO.setCardtype(a.get(5).toString());
					  feeAccrBVO.setCurrinte(new BigDecimal(a.get(6).toString()));
					  feeAccrBVO.setCusttype(a.get(7).toString());
					  feeAccrBVO.setLoantype(a.get(8).toString());
					  feeAccrBVO.setPaytype(a.get(9).toString());
					  feeAccrBVO.setCaltype(a.get(10).toString());
					  feeAccrBVO.setGuatype(a.get(11).toString());
					  feeAccrBVO.setCurr(a.get(12).toString());
					  feeAccrBVO.setVnote(a.get(13).toString());
					  feeAccrBVO.setDef1((a.get(14).toString()));
					  feeAccrBVO.setDef2((a.get(15).toString()));
					  feeAccrBVO.setDef3((a.get(16).toString()));
					  feeAccrBVO.setDef4((a.get(17).toString()));
					  feeAccrBVOItemArray[i]=feeAccrBVO;
					  companyId=feeAccrBVO.getCorpno();
			}
	   
		feeAccrData.setFeeAccrBVOItemArray(feeAccrBVOItemArray);
		feeAccrData.setZyhtVO(zyvo.createVo(companyId, factDate, feeAccrBVOItemArray.length));
		faData.setFeeAccrData(feeAccrData);
		String isSuccess="";
		if(isOpen){
		try {
			String [] ss = stub.feeAccrData(faData).getFeeAccrDataResponse().getReturnArray();
			isSuccess=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	  
	  return isSuccess;
  }
	public String custominfo(){
		String url = WebServicesUrl.getInstances().createUrl();
		boolean customerIsOpen=WebServicesUrl.getInstances().customerIsOpen();
		IZyhtWebServiceStub stub=null ;
		
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		BdCubDocDataDocument  bddata = BdCubDocDataDocument.Factory.newInstance();
		BdCubDocData ddCubDocData = BdCubDocData.Factory.newInstance();
	   List<Enterprise> ellist=enterpriseService.getAll();
	   List<Person> pllist=personService.getAll();
	   int totalsize=ellist.size()+pllist.size();
	   int elistbanksize=0;
	   BankInfoPlusVO[] bankInfoPlusVOItemArray=new BankInfoPlusVO[ enterpriseBankService.getAll().size()] ;
	   BdCubasdocPlusVO[] bdCubasdocPlusVOItemArray=new BdCubasdocPlusVO[totalsize] ;
	    int ellistsize=0;
	   for(Enterprise entp:ellist){
		  BdCubasdocPlusVO bdCubasdocPlusVO=BdCubasdocPlusVO.Factory.newInstance();
		  
		  bdCubasdocPlusVO.setCustcode("E"+entp.getId());
		  bdCubasdocPlusVO.setPkCorp(entp.getCompanyId().toString());
		  bdCubasdocPlusVO.setCustname(entp.getEnterprisename());
		  
		  bdCubasdocPlusVOItemArray[ellistsize]=bdCubasdocPlusVO;
		  ellistsize++;
		List elistbank=  enterpriseBankService.getList(entp.getId(), Short.valueOf("0"), Short.valueOf("0"), null, null);
		elistbanksize=elistbank.size();
		
		for(int i=0;i<elistbank.size();i++){
			BankInfoPlusVO bankInfoPlusVO = BankInfoPlusVO.Factory.newInstance();
			EnterpriseBank eb=(EnterpriseBank)elistbank.get(i);
			bankInfoPlusVO.setPkBanktype(eb.getBankname());
			bankInfoPlusVO.setAccount(eb.getAccountnum());
			bankInfoPlusVO.setAccountname(eb.getName());
			bankInfoPlusVO.setPkBankaccbas(bdCubasdocPlusVO.getCustcode());
			bankInfoPlusVO.setPkCorp(bdCubasdocPlusVO.getPkCorp());
			bankInfoPlusVOItemArray[i]=bankInfoPlusVO;
		
		}
		}
	int pllistsize=0;	 
	  for(Person ps:pllist){
		  BdCubasdocPlusVO bdCubasdocPlusVO=BdCubasdocPlusVO.Factory.newInstance();
		  
		  bdCubasdocPlusVO.setCustcode("P"+ps.getId());
		  bdCubasdocPlusVO.setPkCorp(ps.getCompanyId().toString());
		  bdCubasdocPlusVO.setCustname(ps.getName());
		  bdCubasdocPlusVOItemArray[ellistsize+pllistsize]=bdCubasdocPlusVO;
		  pllistsize++;
		List plistbank=  enterpriseBankService.getList(ps.getId(), Short.valueOf("1"), Short.valueOf("0"), null, null);
		
		
		for(int i=elistbanksize;i<plistbank.size()+elistbanksize;i++){
			BankInfoPlusVO bankInfoPlusVO = BankInfoPlusVO.Factory.newInstance();
			EnterpriseBank eb=(EnterpriseBank)plistbank.get(i-elistbanksize);
			bankInfoPlusVO.setPkBanktype(eb.getBankname());
		//	System.out.print("============="+eb.getBankname());
		//	bankInfoPlusVO.setPkBanktype("中国工商银行_河北分行_廊坊支行");
			bankInfoPlusVO.setAccount(eb.getAccountnum());
			bankInfoPlusVO.setAccountname(eb.getName());
			bankInfoPlusVO.setPkBankaccbas(bdCubasdocPlusVO.getCustcode());
			bankInfoPlusVO.setPkCorp(bdCubasdocPlusVO.getPkCorp());
			bankInfoPlusVOItemArray[i]=bankInfoPlusVO;
		}
		}
		ddCubDocData.setBdCubasdocPlusVOItemArray(bdCubasdocPlusVOItemArray);
		ddCubDocData.setBankInfoPlusVOItemArray(bankInfoPlusVOItemArray);
		ddCubDocData.setZyhtVO(zyvo.createVo(bdCubasdocPlusVOItemArray.length));
		bddata.setBdCubDocData(ddCubDocData);
		if(customerIsOpen){
		try {
			String [] ss = stub.bdCubDocData(bddata).getBdCubDocDataResponse().getReturnArray();
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	  
	  return SUCCESS;
  }
	//贷款逾期
	public String principalRepayOverdueToExcel(String factDate){
		
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
		String isSuccess="";
		if(isOpen){
		try {
			String [] ss = stub.overDueData(agData).getOverDueDataResponse().getReturnArray();
			isSuccess=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	  
	  return isSuccess;
  }
	//贷款实收本金
	public String realpPrincipalPepayToExcel(String factDate){
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
		 
	
	   
		realPriData.setRealPriBVOItemArray(realPriBVOItemArray);
		realPriData.setZyhtVO(zyvo.createVo(companyId, factDate, realPriBVOItemArray.length));
		rpData.setRealPriData(realPriData);
		String isSuccess="";
		if(isOpen){
		try {
			String [] ss = stub.realPriData(rpData).getRealPriDataResponse().getReturnArray();
			isSuccess=ss[2];
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	  
	  return isSuccess;
  }
	//贷款实收罚息
	public String realPunishInterestToExcel(String factDate){
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
		String isSuccess="";
		if(isOpen){
		try {
			String [] ss = stub.realPiiData(rpData).getRealPiiDataResponse().getReturnArray();
			isSuccess=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	  
	  return isSuccess;
  }
	//贷款实收利息
	public String realInterestToExcel(String factDate){
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
		
		realInteData.setRealInteBVOItemArray(realInteBVOItemArray);
		realInteData.setZyhtVO(zyvo.createVo(companyId, factDate, realInteBVOItemArray.length));
		riData.setRealInteData(realInteData);
		String isSuccess="";
		if(isOpen){
		try {
			String [] ss = stub.realInteData(riData).getRealInteDataResponse().getReturnArray();
			isSuccess=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	  
	  return isSuccess;
  }
	//贷款利息计划
	public String interestPlanToExcel(String factDate){
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
		String isSuccess="";
		if(isOpen){
		try {
			String [] ss = stub.intePlanData(ipData).getIntePlanDataResponse().getReturnArray();
			isSuccess=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	  
	  return isSuccess;
 }	
	//贷款实收随息手续费
	public String realchargeToExcel(String factDate){
		
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
		String isSuccess="";
		if(isOpen){
		try {
			String [] ss = stub.realFeeData(rfData).getRealFeeDataResponse().getReturnArray();
			isSuccess=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	  
	  return isSuccess;
 }	
	//贷款随息手续费计划
	public String chargePlanToExcel(String factDate){
		
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
		String isSuccess="";
		if(isOpen){
		try {
			String [] ss = stub.feePlanData(fpData).getFeePlanDataResponse().getReturnArray();
			isSuccess=ss[2];
			
		} catch (RemoteException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	  
	  return isSuccess;
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
