package com.webServices.mortgage.impl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.IZyhtWebServiceStub;
import nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument;
import nc.itf.crd.webservice.izyhtwebservice.AlObGageDataDocument.AlObGageData;
import nc.itf.crd.webservice.izyhtwebservice.UnObGageBVODataDocument.UnObGageBVOData;
import nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO;
import nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.webServices.mortgage.ProcreditMortgageService;
import com.webServices.services.factory.modelfactory.ZyVo;
import com.webServices.services.factory.modelfactory.base.ZyhtVoFactory;
import com.webServices.services.factory.urlFactory.WebServicesUrl;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.dao.system.DictionaryDao;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.service.system.OrganizationService;

public class ProcreditMortgageServiceImpl  extends BaseServiceImpl<ProcreditMortgage> implements ProcreditMortgageService{
	@Resource
	private MortgageService mortgageService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private VProcreditContractService vProcreditContractService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private OrganizationService organizationService;
    private ZyhtVoFactory zyvo=new ZyVo();
	@SuppressWarnings("unchecked")
	private DictionaryDao dao;
	public ProcreditMortgageServiceImpl(DictionaryDao dao) {
		super(dao);
		this.dao =dao;
	}
	public void getTransactMortgage(){	
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		String userid;// 获得项目经理ID
		String productTypeId;
		Dictionary dic;
		AppUser appUser;
		try {
			List<Organization> olist=organizationService.getAllByOrgType();
			for(Organization o:olist){
				String url = WebServicesUrl.getInstances().createUrl();
				boolean isOpen=WebServicesUrl.getInstances().isOpen();
				IZyhtWebServiceStub stub = new IZyhtWebServiceStub(url);			
				Date beginDate=new Date();
				Calendar date = Calendar.getInstance();
				date.setTime(beginDate);
				date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
				List<ProcreditMortgage> list=mortgageService.getTransactMortgage(df.parse(df.format(date.getTime())),o.getOrgId());
				AlObGageDataDocument  agData = AlObGageDataDocument.Factory.newInstance();
				AlObGageData alObGageData = AlObGageData.Factory.newInstance();
				if(null!=list && list.size()>0){
					nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO[] alObGageBVOItemArray=new nc.vo.crd.acc.alobgage.alobgagebvo.AlObGageBVO[list.size()] ;
					for(int i=0;i<list.size();i++){
						ProcreditMortgage mortgage=list.get(i);
						AlObGageBVO alObGageBVO=AlObGageBVO.Factory.newInstance();
						alObGageBVO.setDef10(mortgage.getTractCreateTime().toString().substring(0,mortgage.getCreateTime().toString().lastIndexOf(" ")));
						
						alObGageBVO.setCorpno(o.getOrgId().toString());
						if(mortgage.getMortgagenametypeid()==7){
							alObGageBVO.setGuarname("住宅");
						}else if(mortgage.getMortgagenametypeid()==8){
							alObGageBVO.setGuarname("商铺写字楼");
						}else if(mortgage.getMortgagenametypeid()==9){
							alObGageBVO.setGuarname("住宅用地");
						}else if(mortgage.getMortgagenametypeid()==10){
							alObGageBVO.setGuarname("商业用地");
						}else if(mortgage.getMortgagenametypeid()==11){
							alObGageBVO.setGuarname("商住用地");
						}else if(mortgage.getMortgagenametypeid()==12){
							alObGageBVO.setGuarname("教育用地");
						}else if(mortgage.getMortgagenametypeid()==13){
							alObGageBVO.setGuarname("工业用地");
						}else if(mortgage.getMortgagenametypeid()==14){
							alObGageBVO.setGuarname("无形权利");
						}else if(mortgage.getMortgagenametypeid()==1){
							alObGageBVO.setGuarname("车辆");
						}else if(mortgage.getMortgagenametypeid()==2){
							alObGageBVO.setGuarname("股权");
						}else if(mortgage.getMortgagenametypeid()==5){
							alObGageBVO.setGuarname("机器设备");
						}else if(mortgage.getMortgagenametypeid()==6){
							alObGageBVO.setGuarname("存货/商品");
						}else if(mortgage.getMortgagenametypeid()==15){
							alObGageBVO.setGuarname("公寓");
						}else if(mortgage.getMortgagenametypeid()==6){
							alObGageBVO.setGuarname("联排别墅");
						}else if(mortgage.getMortgagenametypeid()==17){
							alObGageBVO.setGuarname("独栋别墅");
						}
						if(null!=mortgage.getFinalCertificationPrice()){
							alObGageBVO.setGuarvalue(mortgage.getFinalCertificationPrice());
						}else{
							alObGageBVO.setGuarvalue(new BigDecimal(0));
						}
						
						if(mortgage.getAssuretypeid()==604){
							  
							alObGageBVO.setGuartype("E01");
						  }else if(mortgage.getAssuretypeid()==605){
							  alObGageBVO.setGuartype("E02");
							  
						  }else if(mortgage.getAssuretypeid()==606){
							  alObGageBVO.setGuartype("E03");
						  }
						  
						
						SlSmallloanProject sl=new SlSmallloanProject();
						   sl=slSmallloanProjectService.get(mortgage.getProjid());
						   Long oppositeID=sl.getOppositeID();
						   if(sl.getOppositeType().equals("company_customer")){
								Enterprise e=enterpriseService.getById(oppositeID.intValue());
								alObGageBVO.setCustname("E"+e.getId().toString());
				
								
							  }else{
								Person p=personService.getById(oppositeID.intValue());
								alObGageBVO.setCustname("P"+p.getId().toString());
				
								
							   }
						alObGageBVO.setCurr("人民币");
						alObGageBVO.setVnote(mortgage.getTransactRemarks());
						
					/*	 userid = sl.getAppUserId();// 获得项目经理ID
						 productTypeId=sl.getProductTypeId();
						 dic=dictionaryService.get(Long.parseLong(productTypeId));
						 appUser = appUserService.get(Long.parseLong(userid));
						alObGageBVO.setDef1(appUser.getUserNumber());//客户经理编号
						alObGageBVO.setDef2(appUser.getFullname());//客户经理名称
						alObGageBVO.setDef3(dic.getDicKey());//产品编码
						alObGageBVO.setDef4(dic.getItemValue());//产品名称
*/						
						List<VProcreditContract> vlist=vProcreditContractService.getMortgageContract(mortgage.getId());
						if(null!=vlist && vlist.size()>0){
							VProcreditContract vp=vlist.get(0);
							alObGageBVO.setGuarno(vp.getContractNumber());
						}
						alObGageBVOItemArray[i]=alObGageBVO;
					}
					
					  alObGageData.setAlObGageBVOItemArray(alObGageBVOItemArray);
					  alObGageData.setZyhtVO(zyvo.createVo(o.getOrgId().toString(),DateUtil.getNowDateTime("yyyy-mm-dd") , list.size()));			  
					  agData.setAlObGageData(alObGageData);
					  if(isOpen){
					  try {
							String [] ss = stub.alObGageData(agData).getAlObGageDataResponse().getReturnArray();
							
						} catch (RemoteException e) {
							
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					  }	
				}
			
			
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	public void getUnchainMortgage(){
		try{
			List<Organization> olist=organizationService.getAllByOrgType();
			for(Organization o:olist){
				String url = WebServicesUrl.getInstances().createUrl();
				boolean isOpen=WebServicesUrl.getInstances().isOpen();
				IZyhtWebServiceStub stub = new IZyhtWebServiceStub(url);
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				Date beginDate=new Date();
				Calendar date = Calendar.getInstance();
				date.setTime(beginDate);
				date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
				List<ProcreditMortgage> list=mortgageService.getUnchainMortgage(df.parse(df.format(date.getTime())),o.getOrgId());
				UnObGageBVODataDocument  unData = UnObGageBVODataDocument.Factory.newInstance();
				UnObGageBVOData unObGageData = UnObGageBVOData.Factory.newInstance();
				if(null!=list && list.size()>0){
					nc.vo.crd.acc.unobgage.unobgagebvo.UnObGageBVO[] unObGageBVOItemArray=new UnObGageBVO[list.size()] ;
					for(int i=0;i<list.size();i++){
						ProcreditMortgage mortgage=list.get(i);
						UnObGageBVO unObGageBVO=UnObGageBVO.Factory.newInstance();
						unObGageBVO.setDef10(mortgage.getUnchainCreateTime().toString().substring(0,mortgage.getUnchaindate().toString().lastIndexOf(" ")));
						unObGageBVO.setCorpno(o.getOrgId().toString());
						if(mortgage.getMortgagenametypeid()==7){
							unObGageBVO.setGuarname("住宅");
						}else if(mortgage.getMortgagenametypeid()==8){
							unObGageBVO.setGuarname("商铺写字楼");
						}else if(mortgage.getMortgagenametypeid()==9){
							unObGageBVO.setGuarname("住宅用地");
						}else if(mortgage.getMortgagenametypeid()==10){
							unObGageBVO.setGuarname("商业用地");
						}else if(mortgage.getMortgagenametypeid()==11){
							unObGageBVO.setGuarname("商住用地");
						}else if(mortgage.getMortgagenametypeid()==12){
							unObGageBVO.setGuarname("教育用地");
						}else if(mortgage.getMortgagenametypeid()==13){
							unObGageBVO.setGuarname("工业用地");
						}else if(mortgage.getMortgagenametypeid()==14){
							unObGageBVO.setGuarname("无形权利");
						}else if(mortgage.getMortgagenametypeid()==1){
							unObGageBVO.setGuarname("车辆");
						}else if(mortgage.getMortgagenametypeid()==2){
							unObGageBVO.setGuarname("股权");
						}else if(mortgage.getMortgagenametypeid()==5){
							unObGageBVO.setGuarname("机器设备");
						}else if(mortgage.getMortgagenametypeid()==6){
							unObGageBVO.setGuarname("存货/商品");
						}else if(mortgage.getMortgagenametypeid()==15){
							unObGageBVO.setGuarname("公寓");
						}else if(mortgage.getMortgagenametypeid()==16){
							unObGageBVO.setGuarname("联排别墅");
						}else if(mortgage.getMortgagenametypeid()==17){
							unObGageBVO.setGuarname("独栋别墅");
						}
						if(null!=mortgage.getFinalCertificationPrice()){
							unObGageBVO.setGuarvalue(mortgage.getFinalCertificationPrice());
						}else{
							unObGageBVO.setGuarvalue(new BigDecimal(0));
						}
						
						if(mortgage.getAssuretypeid()==604){
							  
							unObGageBVO.setGuartype("E01");
						  }else if(mortgage.getAssuretypeid()==605){
							  unObGageBVO.setGuartype("E02");
							  
						  }else if(mortgage.getAssuretypeid()==606){
							  unObGageBVO.setGuartype("E03");
						  }
						SlSmallloanProject sl=new SlSmallloanProject();
						   sl=slSmallloanProjectService.get(mortgage.getProjid());
						   Long oppositeID=sl.getOppositeID();
						   if(sl.getOppositeType().equals("company_customer")){
								Enterprise e=enterpriseService.getById(oppositeID.intValue());
								unObGageBVO.setCustname("E"+e.getId().toString());
							  }else{
								Person p=personService.getById(oppositeID.intValue());
								unObGageBVO.setCustname("P"+p.getId().toString());
							   }
						
						unObGageBVO.setCurr("人民币");
						unObGageBVO.setVnote(mortgage.getUnchainremark());
						List<VProcreditContract> vlist=vProcreditContractService.getMortgageContract(mortgage.getId());
						if(null!=vlist && vlist.size()>0){
							VProcreditContract vp=vlist.get(0);
							unObGageBVO.setGuarno(vp.getContractNumber());
						}
						unObGageBVOItemArray[i]=unObGageBVO;
					}
					
					unObGageData.setUnObGageBVOItemArray(unObGageBVOItemArray);
					unObGageData.setZyhtVO(zyvo.createVo(o.getOrgId().toString(), df.format(new Date()), list.size()));
					unData.setUnObGageBVOData(unObGageData);
					if(isOpen){
					try {
						String [] ss = stub.unObGageBVOData(unData).getUnObGageBVODataResponse().getReturnArray();
						
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
