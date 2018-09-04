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

import nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.IZyhtWebServiceStub;
import nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData;
import nc.itf.crd.webservice.izyhtwebservice.FeeAccrDataDocument.FeeAccrData;
import nc.itf.crd.webservice.izyhtwebservice.InteAccrDataDocument.InteAccrData;
import nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO;
import nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO;
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
import com.zhiwei.credit.model.creditFlow.finance.SlDataList;
import com.zhiwei.credit.model.system.AppUser;
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
public class SendAccruedFromExcelAction extends BaseAction {
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
		String result="";
		String[] isSuccessall1=interestAccruedFromExcel(factdate);
		String[] isSuccessall2=AccruedToExcel(factdate);
		if(isSuccessall1[0].equals("Y")&&isSuccessall2[0].equals("Y")){
		SlDataList	slDataList=slDataListService.get(dataId);
		slDataList.setSendStatus(Short.valueOf("0"));
		slDataList.setSendTime(new Date());
		 AppUser user=ContextUtil.getCurrentUser();
		slDataList.setSendPersonId(user.getUserId());
		slDataListService.save(slDataList);
		
		result="【"+isSuccessall1[1]+"】"+"【"+isSuccessall2[1]+"】";
		result=result.replace("\r\n", "");
		StringBuffer sb = new StringBuffer("{success:true,flag:0,result:'"+result+"'}");
		setJsonString(sb.toString());
		return SUCCESS;
		}else if(isSuccessall1[0].equals("N")&&isSuccessall2[1].equals("N")){
			
			result="【"+isSuccessall1[1]+"】"+"【"+isSuccessall2[1]+"】";
			result=result.replace("\r\n", "");
			StringBuffer sb = new StringBuffer("{success:true,flag:0,result:'"+result+"'}");
			setJsonString(sb.toString());
			return SUCCESS;
			
		}else {
			result="未连接到财务系统 /数据传输未开启。";
			StringBuffer sb = new StringBuffer("{success:true,flag:1,result:'"+result+"'}");
			setJsonString(sb.toString());
			return SUCCESS;
		}
   }
	//贷款利息计提
	public String[] interestAccruedFromExcel(String factDate){
		String[] str=new String[2];
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
			if(isOpen){
			try {
				String [] ss = stub.inteAccrData(iaData).getInteAccrDataResponse().getReturnArray();
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
	// 贷款随收手续费计提
	public String[] AccruedToExcel(String factDate){
		String[] str=new String[2];
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
		if(isOpen){
		try {
			String [] ss = stub.feeAccrData(faData).getFeeAccrDataResponse().getReturnArray();
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
