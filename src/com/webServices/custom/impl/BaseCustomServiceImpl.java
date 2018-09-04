package com.webServices.custom.impl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.IZyhtWebServiceStub;
import nc.itf.crd.webservice.izyhtwebservice.BdCubDocDataDocument.BdCubDocData;
import nc.vo.crd.bd.interf.bankinfoplusvo.BankInfoPlusVO;
import nc.vo.crd.bd.interf.bdcubasdocplusvo.BdCubasdocPlusVO;

import org.apache.axis2.AxisFault;

import com.credit.proj.entity.ProcreditMortgage;
import com.webServices.custom.BaseCustomService;
import com.webServices.services.factory.modelfactory.ZyVo;
import com.webServices.services.factory.urlFactory.WebServicesUrl;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.system.DictionaryDao;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;

public class BaseCustomServiceImpl  extends BaseServiceImpl<ProcreditMortgage> implements BaseCustomService{
	@SuppressWarnings("unchecked")

	@Resource
	private PersonService personService;
	@Resource
	private EnterpriseBankService  enterpriseBankService;
	//@Resource
	private EnterpriseService enterpriseService;

	public EnterpriseService getEnterpriseService() {
		return enterpriseService;
	}

	public void setEnterpriseService(EnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}


	private DictionaryDao dao;
	public BaseCustomServiceImpl(DictionaryDao dao) {
		super(dao);
		this.dao =dao;
	}
	

	@Override
	public String getCustomToweb(String isEnterprise, int customId, int bankId) {
		String str="";
		boolean isOpen = WebServicesUrl.getInstances().isOpen();
		if(isOpen){
		String area="";
		String[] areaArr;
		EnterpriseService enterpriseService=(EnterpriseService)AppUtil.getBean("enterpriseService");
		EnterpriseBankService enterpriseBankService=(EnterpriseBankService)AppUtil.getBean("enterpriseBankService");
		String url = WebServicesUrl.getInstances().createUrl();
		boolean customerIsOpen=WebServicesUrl.getInstances().customerIsOpen();
		String companyId=null;
		IZyhtWebServiceStub stub=null ;
		
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		BdCubDocDataDocument  bddata = BdCubDocDataDocument.Factory.newInstance();
		BdCubDocData ddCubDocData = BdCubDocData.Factory.newInstance();
		 BdCubasdocPlusVO[] bdCubasdocPlusVOItemArray=null;
		 BankInfoPlusVO[] bankInfoPlusVOItemArray=null;
		if(isEnterprise.equals("0")){ //企业
		    bdCubasdocPlusVOItemArray=new BdCubasdocPlusVO[1] ;
			Enterprise entp=enterpriseService.getById(customId);
			List elistbank=  enterpriseBankService.getList(entp.getId(), Short.valueOf("0"), Short.valueOf("0"), null, null);
			int elistbanksize=(null==elistbank)?0:elistbank.size();
			 bankInfoPlusVOItemArray=new BankInfoPlusVO[elistbanksize] ;
			  BdCubasdocPlusVO bdCubasdocPlusVO=BdCubasdocPlusVO.Factory.newInstance();
			  companyId=entp.getCompanyId().toString();
			  bdCubasdocPlusVO.setCustcode("E"+entp.getId());
			  bdCubasdocPlusVO.setPkCorp(entp.getCompanyId().toString());
			  bdCubasdocPlusVO.setCustname(entp.getEnterprisename());
			  bdCubasdocPlusVOItemArray[0]=bdCubasdocPlusVO;
			 
			 
			 for(int i=0;i<elistbanksize;i++){
					BankInfoPlusVO bankInfoPlusVO = BankInfoPlusVO.Factory.newInstance();
					EnterpriseBank eb=(EnterpriseBank)elistbank.get(i);
					 area=eb.getAreaName();
					 if(!"".equals(area)&&area.indexOf("-")!=-1){
						 areaArr=area.split("-");
							if(areaArr[1].equals("北京市")||areaArr[1].equals("天津市")||areaArr[1].equals("上海市")||areaArr[1].equals("重庆市")||areaArr[1].equals("台湾省")){
								area=areaArr[1];
							}else{
								area=areaArr[areaArr.length-1];
							}
					 }
					bankInfoPlusVO.setBankarea(area);
				
					bankInfoPlusVO.setAccount(eb.getAccountnum());
					bankInfoPlusVO.setAccountname(eb.getName());
					bankInfoPlusVO.setPkBankaccbas(bdCubasdocPlusVO.getCustcode());
					bankInfoPlusVO.setPkCorp(bdCubasdocPlusVO.getPkCorp());
					bankInfoPlusVO.setDef1("E"+eb.getId());
					bankInfoPlusVOItemArray[i]=bankInfoPlusVO;
				}
			
		}else{
			
			  bdCubasdocPlusVOItemArray=new BdCubasdocPlusVO[1] ;
				Person ps=personService.getById(customId);
				List plistbank= enterpriseBankService.getList(ps.getId(), Short.valueOf("1"), Short.valueOf("0"), null, null);
				int plistbanksize=(null==plistbank)?0:plistbank.size();
				 bankInfoPlusVOItemArray=new BankInfoPlusVO[plistbanksize] ;
				 companyId=ps.getCompanyId().toString();
				 BdCubasdocPlusVO bdCubasdocPlusVO=BdCubasdocPlusVO.Factory.newInstance();
				  bdCubasdocPlusVO.setCustcode("P"+ps.getId());
				  bdCubasdocPlusVO.setPkCorp(ps.getCompanyId().toString());
				  bdCubasdocPlusVO.setCustname(ps.getName());
				  bdCubasdocPlusVOItemArray[0]=bdCubasdocPlusVO;
			
				  for(int i=0;i<plistbanksize;i++){
						BankInfoPlusVO bankInfoPlusVO = BankInfoPlusVO.Factory.newInstance();
						EnterpriseBank eb=(EnterpriseBank)plistbank.get(i);
						 area=eb.getAreaName();
						 areaArr=area.split("-");
						if(areaArr[1].equals("北京市")||areaArr[1].equals("天津市")||areaArr[1].equals("上海市")||areaArr[1].equals("重庆市")||areaArr[1].equals("台湾省")){
							area=areaArr[1];
						}else{
							area=areaArr[areaArr.length-1];
						}
						bankInfoPlusVO.setBankarea(area);
//						bankInfoPlusVO.setPkBanktype(eb.getBankname().trim()+"_"+o.getName());
					//	System.out.print("============="+eb.getBankname());
					//	bankInfoPlusVO.setPkBanktype("中国工商银行_河北分行_廊坊支行");
						bankInfoPlusVO.setAccount(eb.getAccountnum());
						bankInfoPlusVO.setAccountname(eb.getName());
						bankInfoPlusVO.setPkBankaccbas(bdCubasdocPlusVO.getCustcode());
						bankInfoPlusVO.setPkCorp(bdCubasdocPlusVO.getPkCorp());
						bankInfoPlusVO.setDef1("P"+eb.getId());
						bankInfoPlusVOItemArray[i]=bankInfoPlusVO;
					}
		}
		
			ZyVo zyvo=new ZyVo();
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			ddCubDocData.setBdCubasdocPlusVOItemArray(bdCubasdocPlusVOItemArray);
			ddCubDocData.setBankInfoPlusVOItemArray(bankInfoPlusVOItemArray);
			ddCubDocData.setZyhtVO(zyvo.createVo(companyId,sd.format(new Date()),bdCubasdocPlusVOItemArray.length));
			bddata.setBdCubDocData(ddCubDocData);
			if(customerIsOpen){
			try {
				String [] ss = stub.bdCubDocData(bddata).getBdCubDocDataResponse().getReturnArray();
				str=ss[2];
				str=str.replace("\n", "");
			} catch (RemoteException e) {
				e.printStackTrace();
				str="未与财务系统对接";
			} catch (Exception e) {
				e.printStackTrace();
				str="未与财务系统对接";
			}
			}
		}
		  return str;
	}
}
