package com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountBankDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountRecordDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBank;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoney;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlBankGuaranteemoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankCautionmoneyService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountBankService;
import com.zhiwei.credit.service.creditFlow.guarantee.guaranteefinance.GlAccountRecordService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlBankAccountService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;

/**
 * 
 * @author 
 *
 */
public class GlBankGuaranteemoneyServiceImpl extends BaseServiceImpl<GlBankGuaranteemoney> implements GlBankGuaranteemoneyService{
	@SuppressWarnings("unused")
	private GlBankGuaranteemoneyDao dao;
	@Resource
	private GlBankGuaranteemoneyService glBankGuaranteemoneyService;
	@Resource
	private SlBankAccountService slBankAccountService;
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private GlAccountBankDao glAccountBankDao;
	@Resource
	private GlAccountBankCautionmoneyDao glAccountBankCautionmoneyDao;
	@Resource
	private GlAccountRecordService glAccountRecordService;
	@Resource
	private GlAccountBankCautionmoneyService glAccountBankCautionmoneyService;
	@Resource
	private GlAccountRecordDao glAccountRecordDao;
	public GlBankGuaranteemoneyServiceImpl(GlBankGuaranteemoneyDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<GlBankGuaranteemoney> getbyprojId(Long projId) {
	
		return dao.getbyprojId(projId);
	}


	@Override
	public Integer saveReturnMoneyFlow() {
//		GlBankGuaranteemoney glBankGuaranteemoney=new GlBankGuaranteemoney();
//		List<GlBankGuaranteemoney> list=dao.getbyprojId(projId);
//        if(list.size()!=0){
//        	glBankGuaranteemoney=list.get(0);
//      	  glBankGuaranteemoney.setIsRelease(isRelease);
//      	dao.save(glBankGuaranteemoney);
 //       }
		return 1;
	}
   

	@Override
	public Integer saveAfterFlow(FlowRunInfo flowRunInfo) {
		if(flowRunInfo.isBack()){
			return 1;
		}else{
			String projId=flowRunInfo.getRequest().getParameter("projectId_flow");
			String isRelease=flowRunInfo.getRequest().getParameter("isRelease1");
			String isCharge=flowRunInfo.getRequest().getParameter("isCharge1");
		//	String businessType=flowRunInfo.getRequest().getParameter("businessType1");
			 try{
					
					
					
					
					//冻结银行保证金
				 if(Short.parseShort(isCharge)==0){  //表示财务登记节点
					 List<GlBankGuaranteemoney> a=new ArrayList<GlBankGuaranteemoney>();
						a=glBankGuaranteemoneyService.getbyprojId(Long.valueOf(projId));
						 GlBankGuaranteemoney glBankGuaranteemoney=new GlBankGuaranteemoney();
						 BeanUtil.populateEntity(flowRunInfo.getRequest(), glBankGuaranteemoney, "glBankGuaranteemoney");
						Long projectId = Long.valueOf(projId);
					     if(glBankGuaranteemoney.getBankGuaranteeId()==null &&a.size()==0){
					    			glBankGuaranteemoney.setProjectId(projectId);
					    			glBankGuaranteemoney.setBusinessType("Guarantee");
					    			glBankGuaranteemoney.setOperationType("CompanyBusiness");
					    			glBankGuaranteemoney.setIsRelease(Short.parseShort(isRelease));
					    			if(null !=glBankGuaranteemoney.getAccountId()){
						    			GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
									   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
						     	    	dao.save(glBankGuaranteemoney);
					     	    	
					     	    	    GlAccountRecord glAccountRecord=new GlAccountRecord();
										glAccountRecord.setCautionAccountId(glBankGuaranteemoney.getAccountId());
										glAccountRecord.setCapitalType(3);
										glAccountRecord.setGlAccountBankId(glBankGuaranteemoney.getGlAccountBankId());
										glAccountRecord.setOprateDate(glBankGuaranteemoney.getFreezeDate());
										glAccountRecord.setOprateMoney(glBankGuaranteemoney.getFreezeMoney());
										glAccountRecord.setProjectId(glBankGuaranteemoney.getProjectId());
										AppUser user=ContextUtil.getCurrentUser();
										glAccountRecord.setHandlePerson(user.getFullname());
										glAccountRecordDao.save(glAccountRecord);
					     	    	    saveguaranteemoneyAccount(glBankGuaranteemoney.getGlAccountBankId(),glBankGuaranteemoney.getAccountId());
					    			}else{
					    				
					    				dao.save(glBankGuaranteemoney);
					    			}
					     }else{
							    	 GlBankGuaranteemoney orgGlBankGuaranteemoney;
							    	 if(a.size() !=0){
								    	 orgGlBankGuaranteemoney=dao.getbyprojId(projectId).get(0);
								     }else{ 	 
									     orgGlBankGuaranteemoney=dao.get(glBankGuaranteemoney.getBankGuaranteeId());
								     }
							    	 
							    	 if(null !=glBankGuaranteemoney.getAccountId()){
										  GlAccountBankCautionmoney glAccountBankCautionmoney = glAccountBankCautionmoneyDao.get(glBankGuaranteemoney.getAccountId());
										   BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
										   orgGlBankGuaranteemoney.setIsRelease(Short.parseShort(isRelease));
										   orgGlBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
										   dao.save(orgGlBankGuaranteemoney);
										  
											
											GlAccountRecord glAccountRecord=new GlAccountRecord();//冻结记录
											 List<GlAccountRecord> GlAccountRecords= glAccountRecordService.getbyprojectIdcapitalType(projectId,3);
											 if(GlAccountRecords.size()!=0){
												 glAccountRecord=GlAccountRecords.get(0);
											 }
											glAccountRecord.setCautionAccountId(orgGlBankGuaranteemoney.getAccountId());
											glAccountRecord.setCapitalType(3);
											glAccountRecord.setGlAccountBankId(orgGlBankGuaranteemoney.getGlAccountBankId());
											glAccountRecord.setOprateDate(orgGlBankGuaranteemoney.getFreezeDate());
											glAccountRecord.setOprateMoney(orgGlBankGuaranteemoney.getFreezeMoney());
											glAccountRecord.setProjectId(orgGlBankGuaranteemoney.getProjectId());
											AppUser user=ContextUtil.getCurrentUser();
											glAccountRecord.setHandlePerson(user.getFullname());
											glAccountRecordService.save(glAccountRecord);
										   saveguaranteemoneyAccount(orgGlBankGuaranteemoney.getGlAccountBankId(),orgGlBankGuaranteemoney.getAccountId());
					     
							    	 }else{
							    		 BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
							    		 dao.save(orgGlBankGuaranteemoney);
							    	 }
					     }
						   //冻结保费
							List<SlFundIntent> list2= slFundIntentDao.getByProjectId1(Long.parseLong(projId), "Guarantee");
							SlFundIntent slFundIntent2=new SlFundIntent();
							for(SlFundIntent g:list2){
								if(g.getFundType().equals("GuaranteeToCharge")){
									slFundIntent2=g;
								}
							}
							slFundIntent2.setIsChargeCertificate(Short.parseShort(isCharge));
							slFundIntentDao.save(slFundIntent2);
						   
							//款项和费用
							String slActualToChargeData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
							String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData1");
							slActualToChargeService.savejson(slActualToChargeData, Long.parseLong(projId), "Guarantee", Short.parseShort("0"),null);
							slFundIntentService.savejson1(fundIntentJsonData,Long.parseLong(projId), "Guarantee", Short.parseShort("0"),Short.valueOf("0"),null);
				 }else{  //退还保证金节点
					 
					 GlBankGuaranteemoney glBankGuaranteemoney=new GlBankGuaranteemoney();
					 glBankGuaranteemoney=  dao.getbyprojId(Long.parseLong(projId)).get(0);
					 glBankGuaranteemoney.setIsRelease(Short.parseShort(isRelease));
					 dao.save(glBankGuaranteemoney);
					 
					 
				 }

			        //冻结客户保证金
			   	    List<SlFundIntent> list1= slFundIntentDao.getByProjectId1(Long.parseLong(projId), "Guarantee");
			   	 SlFundIntent slFundIntent1=new SlFundIntent();
			   	    for(SlFundIntent g:list1){
						if(g.getFundType().equals("ToCustomGuarantMoney")){
							slFundIntent1=g;
						}
					}
			   	 if(null !=slFundIntent1){
					slFundIntent1.setIsChargeCertificate(Short.parseShort(isCharge));
					slFundIntentDao.save(slFundIntent1);
			   	 }
					
	
				   }catch(Exception e){
					   e.printStackTrace();
					   return 0;
				   }
					return 1;
		}
	}

	@Override
	public Integer saveAfterFlow1(FlowRunInfo flowRunInfo) {//获取担保责任函下一步
		if(flowRunInfo.isBack()){
		
				   return 1;
			}else{
				
				 GlBankGuaranteemoney glBankGuaranteemoney=new GlBankGuaranteemoney();
				
					   try {
						BeanUtil.populateEntity(flowRunInfo.getRequest(), glBankGuaranteemoney, "glBankGuaranteemoney");
						 GlBankGuaranteemoney orgGlBankGuaranteemoney=glBankGuaranteemoneyService.getbyprojId(glBankGuaranteemoney.getProjectId()).get(0);
					   if(null !=glBankGuaranteemoney.getAccountId()){
							BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
								orgGlBankGuaranteemoney.setUnfreezeMoney(orgGlBankGuaranteemoney.getFreezeMoney());
							   dao.save(orgGlBankGuaranteemoney);
							   GlAccountRecord glAccountRecord1=new GlAccountRecord();
								 List<GlAccountRecord> GlAccountRecord1s= glAccountRecordDao.getbyprojectIdcapitalType(glBankGuaranteemoney.getProjectId(),4);
								 if(GlAccountRecord1s.size()!=0){
									 glAccountRecord1=GlAccountRecord1s.get(0);
								 }
									glAccountRecord1.setCautionAccountId(orgGlBankGuaranteemoney.getAccountId());
									glAccountRecord1.setCapitalType(4);
									glAccountRecord1.setGlAccountBankId(orgGlBankGuaranteemoney.getGlAccountBankId());
									glAccountRecord1.setOprateDate(orgGlBankGuaranteemoney.getFreezeDate());
									glAccountRecord1.setOprateMoney(orgGlBankGuaranteemoney.getFreezeMoney());
									glAccountRecord1.setProjectId(orgGlBankGuaranteemoney.getProjectId());
									AppUser user=ContextUtil.getCurrentUser();
									glAccountRecord1.setHandlePerson(user.getFullname());
							   	glAccountRecordDao.save(glAccountRecord1);
							   saveguaranteemoneyAccount(orgGlBankGuaranteemoney.getGlAccountBankId(),orgGlBankGuaranteemoney.getAccountId());
					   }else{
						   
						   BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
						   dao.save(orgGlBankGuaranteemoney);
					   } 
					   }catch(Exception e){
						   e.printStackTrace();
						   logger.error(e.getMessage());
						   return 0;
					   }
					   return 1;
			}
	
	}

	@Override
	public Integer saveAfterFlowZm(FlowRunInfo flowRunInfo) {
		if(flowRunInfo.isBack()){
			
			   return 1;
		}else{
			 try{
				String projId=flowRunInfo.getRequest().getParameter("projectId_flow");
				String isRelease=flowRunInfo.getRequest().getParameter("isRelease1");
				String isCharge=flowRunInfo.getRequest().getParameter("isCharge1");
				
				 SlFundIntent slFundIntent1=new SlFundIntent();
				SlFundIntent slFundIntent2=new SlFundIntent();
				if(isRelease.equals("0")){ //表示确认费用收取节点
					  //冻结客户保证金
						  List<SlFundIntent> list1= slFundIntentDao.getByProjectId1(Long.parseLong(projId), "Guarantee");
							for(SlFundIntent g:list1){
								if(g.getFundType().equals("ToCustomGuarantMoney")){
									slFundIntent1=g;
								}
							}
							if(null !=slFundIntent1){
								slFundIntent1.setIsChargeCertificate(Short.parseShort(isCharge));
								slFundIntentDao.save(slFundIntent1);
								}
							
							 String slActualToChargeData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
								String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData1");
								slActualToChargeService.savejson(slActualToChargeData, Long.parseLong(projId), "Guarantee", Short.parseShort("0"),null);
								slFundIntentService.savejson1(fundIntentJsonData,Long.parseLong(projId), "Guarantee", Short.parseShort("0"),Short.valueOf("0"),null);
					}
				if(isRelease.equals("1")){ //表示向银行缴纳保证金
					List<GlBankGuaranteemoney> a=new ArrayList<GlBankGuaranteemoney>();
					a=glBankGuaranteemoneyService.getbyprojId(Long.valueOf(projId));
					GlBankGuaranteemoney glBankGuaranteemoney=new GlBankGuaranteemoney();
					 BeanUtil.populateEntity(flowRunInfo.getRequest(), glBankGuaranteemoney, "glBankGuaranteemoney");
					 Long projectId = Long.valueOf(projId);
					 if(glBankGuaranteemoney.getBankGuaranteeId()==null && a.size()==0){
			    			glBankGuaranteemoney.setProjectId(projectId);
			    			glBankGuaranteemoney.setBusinessType("Guarantee");
			    			glBankGuaranteemoney.setOperationType("CompanyBusiness");
			    			glBankGuaranteemoney.setIsRelease(Short.parseShort(isCharge));
			    			GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyService.get(glBankGuaranteemoney.getAccountId());
						   glBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
			     	    	glBankGuaranteemoneyService.save(glBankGuaranteemoney);
		     	    	
		     	    	    GlAccountRecord glAccountRecord=new GlAccountRecord();
							glAccountRecord.setCautionAccountId(glBankGuaranteemoney.getAccountId());
							glAccountRecord.setCapitalType(3);
							glAccountRecord.setGlAccountBankId(glBankGuaranteemoney.getGlAccountBankId());
							glAccountRecord.setOprateDate(glBankGuaranteemoney.getFreezeDate());
							glAccountRecord.setOprateMoney(glBankGuaranteemoney.getFreezeMoney());
							glAccountRecord.setProjectId(glBankGuaranteemoney.getProjectId());
							AppUser user=ContextUtil.getCurrentUser();
							glAccountRecord.setHandlePerson(user.getFullname());
							glAccountRecordDao.save(glAccountRecord);
		     	    	    saveguaranteemoneyAccount(glBankGuaranteemoney.getGlAccountBankId(),glBankGuaranteemoney.getAccountId());
			     }else{	  
			    	 GlBankGuaranteemoney orgGlBankGuaranteemoney;
			    	 if(a.size() !=0){
				    	 orgGlBankGuaranteemoney=dao.getbyprojId(projectId).get(0);
				     }else{ 	 
					     orgGlBankGuaranteemoney=dao.get(glBankGuaranteemoney.getBankGuaranteeId());
				     }
					  GlAccountBankCautionmoney glAccountBankCautionmoney = glAccountBankCautionmoneyDao.get(glBankGuaranteemoney.getAccountId());
					   BeanUtil.copyNotNullProperties(orgGlBankGuaranteemoney, glBankGuaranteemoney);
					   orgGlBankGuaranteemoney.setIsRelease(Short.parseShort(isCharge));
					   orgGlBankGuaranteemoney.setGlAccountBankId(glAccountBankCautionmoney.getParentId());
					   dao.save(orgGlBankGuaranteemoney);
					 
					  GlAccountRecord glAccountRecord=new GlAccountRecord();
						glAccountRecord.setCautionAccountId(glBankGuaranteemoney.getAccountId());
						glAccountRecord.setCapitalType(3);
						glAccountRecord.setGlAccountBankId(glBankGuaranteemoney.getGlAccountBankId());
						glAccountRecord.setOprateDate(orgGlBankGuaranteemoney.getFreezeDate());
						glAccountRecord.setOprateMoney(glBankGuaranteemoney.getFreezeMoney());
						glAccountRecord.setProjectId(glBankGuaranteemoney.getProjectId());
						AppUser user=ContextUtil.getCurrentUser();
						glAccountRecord.setHandlePerson(user.getFullname());
						glAccountRecordDao.save(glAccountRecord);
						
					   saveguaranteemoneyAccount(orgGlBankGuaranteemoney.getGlAccountBankId(),orgGlBankGuaranteemoney.getAccountId());
						}
					 
					 String slActualToChargeData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
						String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData1");
						slActualToChargeService.savejson(slActualToChargeData, Long.parseLong(projId), "Guarantee", Short.parseShort("0"),null);
						slFundIntentService.savejson1(fundIntentJsonData,Long.parseLong(projId), "Guarantee", Short.parseShort("0"),Short.valueOf("0"),null);
				}
			    if(isRelease.equals("2")){ //保费收取节点
						List<SlFundIntent> list2= slFundIntentDao.getByProjectId1(Long.parseLong(projId), "Guarantee");
						for(SlFundIntent g:list2){
							if(g.getFundType().equals("GuaranteeToCharge")){
								slFundIntent2=g;
							}
						   }
						if(null !=slFundIntent2){
							slFundIntent2.setIsChargeCertificate(Short.parseShort(isCharge));
							slFundIntentDao.save(slFundIntent2);
							} 
						 String slActualToChargeData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData1");
							String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData1");
							slActualToChargeService.savejson(slActualToChargeData, Long.parseLong(projId), "Guarantee", Short.parseShort("0"),null);
							slFundIntentService.savejson1(fundIntentJsonData,Long.parseLong(projId), "Guarantee", Short.parseShort("0"),Short.valueOf("0"),null);
			       }
			    
			    if(isRelease.equals("3")){ //退还保证金节点
			    	 List<SlFundIntent> list1= slFundIntentDao.getByProjectId1(Long.parseLong(projId), "Guarantee");
						for(SlFundIntent g:list1){
							if(g.getFundType().equals("ToCustomGuarantMoney")){
								slFundIntent1=g;
							}
						}
						if(null !=slFundIntent1){
							slFundIntent1.setIsChargeCertificate(Short.parseShort(isCharge));
							slFundIntentDao.save(slFundIntent1);
							}
					
					List<SlFundIntent> list2= slFundIntentDao.getByProjectId1(Long.parseLong(projId), "Guarantee");
					for(SlFundIntent g:list2){
						if(g.getFundType().equals("GuaranteeToCharge")){
							slFundIntent2=g;
						}
					   }
					if(null !=slFundIntent2){
						slFundIntent1.setIsChargeCertificate(Short.parseShort(isCharge));
						slFundIntentDao.save(slFundIntent1);
						}
				}
					
			 }catch(Exception e){
				   e.printStackTrace();
				   return 0;
			   }
			    return 1;
		}
	}
	@Override
	public void saveguaranteemoneyAccount(Long glAccountBankId,Long cautionAccountId){
		GlAccountBankCautionmoney glAccountBankCautionmoney=glAccountBankCautionmoneyDao.get(cautionAccountId);
		List<GlAccountRecord> listRecord =glAccountRecordDao.getallbycautionAccountId(cautionAccountId,0,99999999);
		BigDecimal incomemoney=new BigDecimal(0);
		BigDecimal paymoney =new BigDecimal(0);
		BigDecimal frozenMoney=new BigDecimal(0);
		BigDecimal unFrozenMoney =new BigDecimal(0);
		for(GlAccountRecord l:listRecord){
			if(l.getCapitalType()==1){ //存入
				incomemoney=incomemoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==2){ //支出
				paymoney=paymoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==3){ //冻结
				frozenMoney=frozenMoney.add(l.getOprateMoney());
			}
			if(l.getCapitalType()==4){ //解冻
				unFrozenMoney=unFrozenMoney.add(l.getOprateMoney());
			}
		}
		BigDecimal sum =glAccountBankCautionmoney.getRawauthorizationMoney().add(incomemoney).subtract(paymoney);
		glAccountBankCautionmoney.setAuthorizationMoney(sum);
		BigDecimal sumsurplusMoney=glAccountBankCautionmoney.getRawsurplusMoney().add(incomemoney).subtract(paymoney).add(unFrozenMoney).subtract(frozenMoney);
		glAccountBankCautionmoney.setSurplusMoney(sumsurplusMoney);
		glAccountBankCautionmoneyDao.save(glAccountBankCautionmoney);
		
		GlAccountBank glAccountBank=glAccountBankDao.get(glAccountBankId);
		List<GlAccountBankCautionmoney> list=glAccountBankCautionmoneyDao.getallbybankId(glAccountBankId);
		BigDecimal authorizationMoney=new BigDecimal(0);
		BigDecimal surplusMoney =new BigDecimal(0);
		for(GlAccountBankCautionmoney l:list){
			authorizationMoney=authorizationMoney.add(l.getAuthorizationMoney());
			surplusMoney=surplusMoney.add(l.getSurplusMoney());
		}
		glAccountBank.setAuthorizationMoney(authorizationMoney);
		glAccountBank.setSurplusMoney(surplusMoney);
		glAccountBankDao.save(glAccountBank);
	}
	@Override
	public List<GlBankGuaranteemoney> getallbycautionAccountId(
			Long cautionAccountId, int start, int limit) {
		// TODO Auto-generated method stub
		return dao.getallbycautionAccountId(cautionAccountId, start, limit);
	}

	@Override
	public List<GlBankGuaranteemoney> getallbyglAccountBankId(
			Long glAccountBankId, int start, int limit) {
		// TODO Auto-generated method stub
		return dao.getallbyglAccountBankId(glAccountBankId, start, limit);
	}

	@Override
	public int getallbycautionAccountIdsize(Long cautionAccountId) {
		// TODO Auto-generated method stub
		return dao.getallbycautionAccountIdsize(cautionAccountId);
	}

	@Override
	public int getallbyglAccountBankIdsize(Long glAccountBankId) {
		// TODO Auto-generated method stub
		return dao.getallbyglAccountBankIdsize(glAccountBankId);
	}


}