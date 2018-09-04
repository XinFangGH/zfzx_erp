package com.zhiwei.credit.service.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlActualToChargeDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlPlansToChargeDao;
import com.zhiwei.credit.dao.creditFlow.financeProject.FlFinancingProjectDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.SettlementReviewerPayDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.project.GLGuaranteeloanProjectDao;
import com.zhiwei.credit.dao.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProjectDao;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.project.FlLeaseFinanceProjectDao;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PlPawnProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.lawsuitguarantee.project.SgLawsuitguaranteeProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.parser.JSONParser;

/**
 * 
 * @author 
 *
 */
public class SlActualToChargeServiceImpl extends BaseServiceImpl<SlActualToCharge> implements SlActualToChargeService{
	private SlActualToChargeDao dao;
	@Resource
	private SlPlansToChargeDao slPlansToChargeDao;
	@Resource
	private SlSmallloanProjectDao slSmallloanProjectDao;
	@Resource
	private FlFinancingProjectDao flFinancingProjectDao;
	@Resource
	private GLGuaranteeloanProjectDao gLGuaranteeloanProjectDao;
	@Resource
	private SgLawsuitguaranteeProjectDao sgLawsuitguaranteeProjectDao;
	@Resource
	private FlLeaseFinanceProjectDao flLeaseFinanceProjectDao;
	@Resource
	private PlPawnProjectDao plPawnProjectDao;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private SettlementReviewerPayDao settlementReviewerPayDao;
	public SlActualToChargeServiceImpl(SlActualToChargeDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlActualToCharge> listbyproject(Long projectId) {
		// TODO Auto-generated method stub
		return dao.listbyproject(projectId);
	}

	@Override
	public String savejson(String slPlansToChargeJson, Long projectId,String businessType,Short flag,Long companyId) {  //尽职调查下一步

//		String businessType=businessType1.split(",")[0];//
//		String flowType=null;                  //流程类型
//		if(businessType1.split(",").length==2){
//		 flowType=businessType1.split(",")[1];
//		}
		if (null != slPlansToChargeJson && !"".equals(slPlansToChargeJson)) {

			String[] shareequityArr = slPlansToChargeJson.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
					String[] strArr=str.split(",");
					String typestr="";
					/*if(strArr.length==8){
						typestr=strArr[1]; 
					}else{*/
						typestr=strArr[0];
					//}
					String typeId="";
					String typename="";
					if(typestr.endsWith("\"")==true){
					   typename=typestr.substring(typestr.indexOf(":")+2,typestr.length()-1);
					}else{
						typeId=typestr.substring(typestr.indexOf(":")+1,typestr.length());
			//			typename=typestr.substring(typestr.indexOf(":")+2,typestr.length()-1);
					}
					SlPlansToCharge slPlansToCharge=new SlPlansToCharge();
					
					/*if(!typename.equals("")){						
								List<SlPlansToCharge> list=slPlansToChargeDao.getAll();
								int k=0;
								for(SlPlansToCharge p:list){
									if(p.getName().equals(typename) && p.getBusinessType().equals(businessType)){
										k++;
									}
								}
								
								if(k==0){
									slPlansToCharge.setName(typename);
									slPlansToCharge.setIsType(1);
									slPlansToCharge.setIsValid(0);
									slPlansToCharge.setBusinessType(businessType);
									slPlansToChargeDao.save(slPlansToCharge);
									str="{";
									for(int j=0;j<strArr.length;j++){
										if(j==0){
											str=str+"\"planChargeId\":"+slPlansToCharge.getPlansTochargeId()+",";
										}else{
											str=str+strArr[j]+",";
										}
									}
									if(strArr.length==9){
											str="{";
										for(int j=0;j<=strArr.length-2;j++){
											if(j !=1){
											str=strArr[j]+",";
											}
										}
										str=str+strArr[strArr.length-1];
						
									}else{
										str="{";
										for(int j=1;j<=strArr.length-2;j++){
											str=str+strArr[j]+",";
										}
										str=str+strArr[strArr.length-1];
									}
								}
					}else{
						long typeid=Long.parseLong(typeId);
						slPlansToCharge=slPlansToChargeDao.get(typeid);
					}*/
					
					long typeid=Long.parseLong(typeId);
					slPlansToCharge=slPlansToChargeDao.get(typeid);

				JSONParser parser = new JSONParser(new StringReader(str));

				try {

					SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper
							.toJava(parser.nextValue(), SlActualToCharge.class);
					slActualToCharge.setProjectId(projectId);
					if(businessType.equals("SmallLoan")){
						SlSmallloanProject loan=slSmallloanProjectDao.get(projectId);
						slActualToCharge.setProjectName(loan.getProjectName());
						slActualToCharge.setProjectNumber(loan.getProjectNumber());
						
					}
	        	  if(businessType.equals("Financing")){
	        		  FlFinancingProject flFinancingProject=flFinancingProjectDao.get(projectId);
	        		  slActualToCharge.setProjectName(flFinancingProject.getProjectName());
	        		  slActualToCharge.setProjectNumber(flFinancingProject.getProjectNumber());
					}
	        	  if(businessType.equals("Guarantee")){
	        		  GLGuaranteeloanProject gLGuaranteeloanProject=gLGuaranteeloanProjectDao.get(projectId);
	        		  slActualToCharge.setProjectName(gLGuaranteeloanProject.getProjectName());
	        		  slActualToCharge.setProjectNumber(gLGuaranteeloanProject.getProjectNumber());
					}
	        	  if(businessType.equals("BeNotFinancing")){
	        		  SgLawsuitguaranteeProject gLGuaranteeloanProject=sgLawsuitguaranteeProjectDao.get(projectId);
	        		  slActualToCharge.setProjectName(gLGuaranteeloanProject.getProjectName());
	        		  slActualToCharge.setProjectNumber(gLGuaranteeloanProject.getProjectNumber());
					}
	        	  if(businessType.equals("InvestSettle")){
	        		  SettlementReviewerPay pay=settlementReviewerPayDao.get(projectId);
	        		  slActualToCharge.setProjectName(pay.getProjectNumber());
	        		  slActualToCharge.setProjectNumber(pay.getProjectNumber());
	        	  }
					slActualToCharge.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
					
					if (null == slActualToCharge.getActualChargeId()) {
						slActualToCharge.setAfterMoney(new BigDecimal(0));
						slActualToCharge.setAccrualMoney(new BigDecimal(0));
						slActualToCharge.setFlatMoney(new BigDecimal(0));
							if(slActualToCharge.getIncomeMoney().equals(new BigDecimal(0.00))){
								slActualToCharge.setNotMoney(slActualToCharge.getPayMoney());
							}else{
								slActualToCharge.setNotMoney(slActualToCharge.getIncomeMoney());
							}
							slActualToCharge.setBusinessType(businessType);
							slActualToCharge.setCompanyId(companyId);
								slActualToCharge.setIsCheck(flag);
						dao.save(slActualToCharge);
					} else {
						
						SlActualToCharge slActualToCharge1 = dao.get(slActualToCharge.getActualChargeId());
						//if(slActualToCharge1.getAfterMoney().compareTo(new BigDecimal(0))==0){
						BeanUtil.copyNotNullProperties(slActualToCharge1,slActualToCharge);
							if(slActualToCharge.getIncomeMoney().equals(new BigDecimal(0.00))){
								slActualToCharge.setNotMoney(slActualToCharge.getPayMoney());
							}else{
								slActualToCharge1.setNotMoney(slActualToCharge.getIncomeMoney());
							}
						slActualToCharge1.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
						slActualToCharge1.setBusinessType(businessType);
						slActualToCharge1.setCompanyId(companyId);
						slActualToCharge1.setIsCheck(flag);
						dao.merge(slActualToCharge1);
						//}
					}

				

				} catch (Exception e) {
					e.printStackTrace();
					logger.error(" 保存杂项费用出错:"+e.getMessage());

				}

			
		}
		}
		return "1";
	}
	public void save(SlActualToCharge productCharge,SlSmallloanProject project){
		SlActualToCharge charge = new SlActualToCharge();
		try {
			BeanUtil.copyNotNullProperties(charge, productCharge);
			charge.setActualChargeId(null);//将id制空
			//productCharge.setProductId(null);//产品id制空\
			if(charge.getCostType().matches("[0-9]*")){
				charge.setPlanChargeId(Long.parseLong(charge.getCostType()));
			}
			
			charge.setProjectId(project.getProjectId());
			charge.setProjectName(project.getProjectName());
			charge.setProjectNumber(project.getProjectNumber());
			charge.setNotMoney(productCharge.getPayMoney());
			charge.setAfterMoney(new BigDecimal(0));
			charge.setIncomeMoney(new BigDecimal(0));
			charge.setBusinessType(project.getBusinessType());
			charge.setCompanyId(1L);
			//charge.setIntentDate();
			charge.setIsCheck((short)0);
			charge.setSlChargeDetails(null);
			dao.save(charge);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public String savejson(String slPlansToChargeJson, Long projectId,Long slSuperviseRecordId,String businessType,Short flag,Long companyId) {  //尽职调查下一步

//		String businessType=businessType1.split(",")[0];//
//		String flowType=null;                  //流程类型
//		if(businessType1.split(",").length==2){
//		 flowType=businessType1.split(",")[1];
//		}
		if (null != slPlansToChargeJson && !"".equals(slPlansToChargeJson)) {

			String[] shareequityArr = slPlansToChargeJson.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
					String[] strArr=str.split(",");
					String typestr="";
					if(strArr.length==7){
						typestr=strArr[1]; 
					}else{
						typestr=strArr[0];
					}
					String typeId="";
					String typename="";
					if(typestr.endsWith("\"")==true){
					   typename=typestr.substring(typestr.indexOf(":")+2,typestr.length()-1);
					}else{
						typeId=typestr.substring(typestr.indexOf(":")+1,typestr.length());
					}
					SlPlansToCharge slPlansToCharge=new SlPlansToCharge();
					
					if(!typename.equals("")){
						
								List<SlPlansToCharge> list=slPlansToChargeDao.getAll();
								int k=0;
								for(SlPlansToCharge p:list){
									if(p.getName().equals(typename) && p.getBusinessType().equals(businessType)){
										k++;
									}
								}
								
								if(k==0){
									slPlansToCharge.setName(typename);
									slPlansToCharge.setIsType(1);
									slPlansToCharge.setIsValid(0);
									slPlansToCharge.setBusinessType(businessType);
									slPlansToChargeDao.save(slPlansToCharge);
									if(strArr.length==9){
											str="{";
										for(int j=0;j<=strArr.length-2;j++){
											if(j !=1){
											str=strArr[j]+",";
											}
										}
										str=str+strArr[strArr.length-1];
						
									}else{
										str="{";
										for(int j=1;j<=strArr.length-2;j++){
											str=str+strArr[j]+",";
										}
										str=str+strArr[strArr.length-1];
									}
								}
					}else{
						long typeid=Long.valueOf(typeId);
						slPlansToCharge=slPlansToChargeDao.get(typeid);
						
					}
					

				JSONParser parser = new JSONParser(new StringReader(str));

				try {

					SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper
							.toJava(parser.nextValue(), SlActualToCharge.class);
					slActualToCharge.setProjectId(projectId);
					if(businessType.equals("SmallLoan")){
						SlSmallloanProject loan=slSmallloanProjectDao.get(projectId);
						slActualToCharge.setProjectName(loan.getProjectName());
						slActualToCharge.setProjectNumber(loan.getProjectNumber());
						
					}
	        	  if(businessType.equals("Financing")){
	        		  FlFinancingProject flFinancingProject=flFinancingProjectDao.get(projectId);
	        		  slActualToCharge.setProjectName(flFinancingProject.getProjectName());
	        		  slActualToCharge.setProjectNumber(flFinancingProject.getProjectNumber());
					}
	        	  if(businessType.equals("Guarantee")){
	        		  GLGuaranteeloanProject gLGuaranteeloanProject=gLGuaranteeloanProjectDao.get(projectId);
	        		  slActualToCharge.setProjectName(gLGuaranteeloanProject.getProjectName());
	        		  slActualToCharge.setProjectNumber(gLGuaranteeloanProject.getProjectNumber());
					}
	        	  if(businessType.equals("BeNotFinancing")){
	        		  SgLawsuitguaranteeProject gLGuaranteeloanProject=sgLawsuitguaranteeProjectDao.get(projectId);
	        		  slActualToCharge.setProjectName(gLGuaranteeloanProject.getProjectName());
	        		  slActualToCharge.setProjectNumber(gLGuaranteeloanProject.getProjectNumber());
					}
	        	  if(businessType.equals("LeaseFinance")){
	        		  FlLeaseFinanceProject flLeaseFinanceProject=flLeaseFinanceProjectDao.get(projectId);
	        		  slActualToCharge.setProjectName(flLeaseFinanceProject.getProjectName());
	        		  slActualToCharge.setProjectNumber(flLeaseFinanceProject.getProjectNumber());
					}
	        	  if(businessType.equals("Pawn")){
	        		  PlPawnProject plPawnProject=plPawnProjectDao.get(projectId);
	        		  slActualToCharge.setProjectName(plPawnProject.getProjectName());
	        		  slActualToCharge.setProjectNumber(plPawnProject.getProjectNumber());
					}
					slActualToCharge.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
					if (null == slActualToCharge.getActualChargeId()) {

					slActualToCharge.setAfterMoney(new BigDecimal(0));
					slActualToCharge.setAccrualMoney(new BigDecimal(0));
					slActualToCharge.setFlatMoney(new BigDecimal(0));
						if(slActualToCharge.getIncomeMoney().equals(new BigDecimal(0.00))){
							slActualToCharge.setNotMoney(slActualToCharge.getPayMoney());
						}else{
							slActualToCharge.setNotMoney(slActualToCharge.getIncomeMoney());
						}
						slActualToCharge.setBusinessType(businessType);
						slActualToCharge.setCompanyId(companyId);
							slActualToCharge.setIsCheck(flag);
							slActualToCharge.setSlSuperviseRecordId(slSuperviseRecordId);
					dao.save(slActualToCharge);
					} else {
						
						SlActualToCharge slActualToCharge1 = dao.get(slActualToCharge.getActualChargeId());
						if(slActualToCharge1.getAfterMoney().compareTo(new BigDecimal(0))==0){
						BeanUtil.copyNotNullProperties(slActualToCharge1,slActualToCharge);
							if(slActualToCharge.getIncomeMoney().equals(new BigDecimal(0.00))){
								slActualToCharge.setNotMoney(slActualToCharge.getPayMoney());
							}else{
								slActualToCharge1.setNotMoney(slActualToCharge.getIncomeMoney());
							}
						slActualToCharge1.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
						slActualToCharge1.setBusinessType(businessType);
						slActualToCharge1.setCompanyId(companyId);
						slActualToCharge1.setIsCheck(flag);
						slActualToCharge.setSlSuperviseRecordId(slSuperviseRecordId);
						dao.save(slActualToCharge1);
						}
					}

				

				} catch (Exception e) {
					e.printStackTrace();
					logger.error(" 保存杂项费用出错:"+e.getMessage());

				}

			
		}
		}
		return "1";
	}
	
	@Override
	public List<SlActualToCharge> search(Map<String, String> map,String businessType) {
		// TODO Auto-generated method stub
		return dao.search(map,businessType);
	}

	@Override
	public int searchsize(Map<String, String> map,String businessType) {
		// TODO Auto-generated method stub
		return dao.searchsize(map,businessType);
	}

	@Override
	public int updateFlatMoney(SlActualToCharge s) {
		// TODO Auto-generated method stub
		return dao.updateFlatMoney(s);
	}

	@Override
	public int updateOverdue(SlActualToCharge s) {
		// TODO Auto-generated method stub
		return dao.updateOverdue(s);
	}

	@Override
	public List<SlActualToCharge> listbyproject(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.listbyproject(projectId,businessType);
	}

	@Override
	public List<SlActualToCharge> getlistbyslSuperviseRecordId(
			Long slSuperviseRecordId, String businessType,Long projectId) {
		// TODO Auto-generated method stub
		return dao.getlistbyslSuperviseRecordId(slSuperviseRecordId, businessType,projectId);
	}

	@Override
	public List<SlActualToCharge> getallbycompanyId() {
		// TODO Auto-generated method stub
		return dao.getallbycompanyId();
	}

	@Override
	public List<SlActualToCharge> listbyproject(Long projectId,
			String businessType, String chargeKey) {
		// TODO Auto-generated method stub
		return dao.listbyproject(projectId, businessType, chargeKey);
	}
	@Override
	public List<SlActualToCharge> getlistbyslEarlyRepaymentRecordId(
			Long slEarlyRepaymentRecordId, String businessType, Long projectId) {
		
		return dao.getlistbyslEarlyRepaymentRecordId(slEarlyRepaymentRecordId, businessType, projectId);
	}

	@Override
	public List<SlActualToCharge> getlistbyslAlteraccrualRecordId(
			Long slAlteraccrualRecordId, String businessType, Long projectId) {
		
		return dao.getlistbyslAlteraccrualRecordId(slAlteraccrualRecordId, businessType, projectId);
	}

	@Override
	public List<SlActualToCharge> getAllbyProjectId(Long projectId, String businessType) {
		return dao.getAllbyProjectId(projectId, businessType);
	}

	@Override
	public List<SlActualToCharge> getByProductId(String productId) {
		try {
			String hql = "from SlActualToCharge e where e.productId=?  and e.businessType is null";
			Long id = Long.valueOf(productId);
			return  creditBaseDao.queryHql(hql, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void saveJson(String slActualToChargeJson, Long projectId,
			String chargekey, Short flag, Long companyId, Long bidPlanId) {
		try {
			List list =AppUtil.parserList(slActualToChargeJson, SlActualToCharge.class);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					if(list.get(i)==null){
						continue;
					}
					SlSmallloanProject project = slSmallloanProjectDao.get(projectId);
					SlActualToCharge slActualToCharge = (SlActualToCharge) list.get(i);
					if (null == slActualToCharge.getActualChargeId()) {
						slActualToCharge.setProjectId(project.getProjectId());
						slActualToCharge.setProjectName(project.getProjectName());
						slActualToCharge.setProjectNumber(project.getProjectNumber());
						slActualToCharge.setBusinessType(project.getBusinessType());
						slActualToCharge.setAfterMoney(new BigDecimal(0));
						slActualToCharge.setAccrualMoney(new BigDecimal(0));
						slActualToCharge.setFlatMoney(new BigDecimal(0));
							if(slActualToCharge.getIncomeMoney().equals(new BigDecimal(0.00))){
								slActualToCharge.setNotMoney(slActualToCharge.getPayMoney());
							}else{
								slActualToCharge.setNotMoney(slActualToCharge.getIncomeMoney());
							}
							//slActualToCharge.setBusinessType(businessType);
							slActualToCharge.setCompanyId(companyId);
							slActualToCharge.setIsCheck(flag);
							slActualToCharge.setBidPlanId(bidPlanId);
						dao.save(slActualToCharge);
						} else {
							
							SlActualToCharge slActualToCharge1 = dao.get(slActualToCharge.getActualChargeId());
							if(slActualToCharge1.getAfterMoney().compareTo(new BigDecimal(0))==0){
								BeanUtil.copyNotNullProperties(slActualToCharge1,slActualToCharge);
								if(slActualToCharge.getIncomeMoney().equals(new BigDecimal(0.00))){
									slActualToCharge.setNotMoney(slActualToCharge.getPayMoney());
								}else{
									slActualToCharge1.setNotMoney(slActualToCharge.getIncomeMoney());
								}
							//slActualToCharge1.setPlanChargeId(slPlansToCharge.getPlansTochargeId());
							//slActualToCharge1.setBusinessType(businessType);
							slActualToCharge.setBusinessType(project.getBusinessType());
							slActualToCharge1.setCompanyId(companyId);
							slActualToCharge1.setIsCheck(flag);
							slActualToCharge1.setBidPlanId(bidPlanId);
							dao.save(slActualToCharge1);
							}
						}
				}
			}
		} catch (TokenStreamException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (MapperException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 根据标的id和项目的id 获取到手续费的支出和收入金额
	 * @param bidId
	 * @param projectId2
	 * @param type
	 * @return
	 */
	@Override
	public BigDecimal getSumMoney(String bidId, String projectId2, String type,String businessType) {
		// TODO Auto-generated method stub
		return dao.getSumMoney(bidId, projectId2,type,businessType);
	}

	@Override
	public List<SlActualToCharge> listbyProjectIdAndBidPlanId(Long projectId,
			String bidPlanId) {
		return dao.listbyProjectIdAndBidPlanId(projectId,bidPlanId);
	}

}