package com.zhiwei.credit.service.creditFlow.financingAgency.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidInfoDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidPlanDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidSaleDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenDao;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidSale;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidSaleService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessDirProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessOrProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionDirProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionOrProService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;

/**
 * 
 * @author 
 *
 */
public class PlBidSaleServiceImpl extends BaseServiceImpl<PlBidSale> implements PlBidSaleService{
	@SuppressWarnings("unused")
	private PlBidSaleDao dao;
	@Resource
	private BpFundIntentDao bpFundIntentDao;
	@Resource
	private PlBidInfoDao plBidInfoDao;
	@Resource
	private BpBusinessOrProService bpBusinessOrProService;
	@Resource
	private BpPersionOrProService bpPersionOrProService;
	@Resource
	private BpPersionDirProService bpPersionDirProService;
	@Resource
	private BpBusinessDirProService bpBusinessDirProService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService; // 小额贷款
	@Resource
	private PlBidPlanDao plBidPlanDao;
	@Resource
	private PlMmObligatoryRightChildrenDao plMmObligatoryRightChildrenDao;
	public PlBidSaleServiceImpl(PlBidSaleDao dao) {
		super(dao);
		this.dao=dao;
	}

	

	@Override
	public List<PlBidSale> inTransferingList(Long userId, PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.inTransferingList(userId, pb, map);
	}

	@Override
	public List<PlBidSale> outTransferingList(Long userId, PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.outTransferingList(userId, pb, map);
	}



	@Override
	public List<PlBidSale> getCanTransferingList(Long userId, PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.getCanTransferingList(userId, pb, map);
	}



	@Override
	public List<PlBidSale> transferingList(Long userId, PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.transferingList(userId, pb, map);
	}



	@Override
	public void schedulingToRightChildren() {
		Map<String, String> map =new HashMap<String, String>();
		map.put("saleStatus","1" );
		String toRightChildrenUser=AppUtil.getToRightChildrenUser();
		map.put("outCustName",toRightChildrenUser);
		List<PlBidSale> list=dao.getCanToObligatoryRightChildren(map);
		for(PlBidSale la:list){
			
			//TODO linyan
			Boolean re=true;
			if(re){
				PlBidInfo plBidInfo=plBidInfoDao.get(la.getBidInfoID());
				PlBidPlan plBidPlan= plBidInfo.getPlBidPlan();
				List<BpFundIntent> listintent = bpFundIntentDao.getByprincipalRepayment(plBidPlan.getBidId(),plBidInfo.getOrderNo());
				
				Long projectId=null;
				if(plBidPlan.getProType().equals("P_Dir")){
					BpPersionDirPro bpPersionDirPro=bpPersionDirProService.get(plBidPlan.getPDirProId());//
					projectId=bpPersionDirPro.getProId();
				}else if(plBidPlan.getProType().equals("B_Dir")){
					
					BpBusinessDirPro  bpBusinessDirPro=bpBusinessDirProService.get(plBidPlan.getBdirProId());
					projectId=bpBusinessDirPro.getProId();
				};
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				SlSmallloanProject slSmallloanProject= slSmallloanProjectService.get(projectId);
				
				
				for(BpFundIntent l:listintent){
					PlMmObligatoryRightChildren plrc=new PlMmObligatoryRightChildren();
					plrc.setPrincipalMoney(l.getIncomeMoney());
					plrc.setIntentDate(l.getIntentDate());
					plrc.setStartDate(plBidPlan.getStartIntentDate());
					int days=DateUtil.getDaysBetweenDate(sdf.format(plrc.getStartDate()), sdf.format(plrc.getIntentDate()));
					plrc.setOrlimit(days);
					plrc.setDayRate(slSmallloanProject.getDayAccrualRate());
					plrc.setAvailableMoney(l.getIncomeMoney());
					plrc.setParentOrBidId(plBidPlan.getBidId());
					plrc.setParentOrBidName(plBidPlan.getBidProName());
					plrc.setProjectId(projectId);
					plrc.setProjectName(slSmallloanProject.getProjectName());
					plrc.setChildrenorName(plrc.getParentOrBidName()+l.getPayintentPeriod());
					plrc.setSurplusValueMoney(l.getIncomeMoney().multiply(new BigDecimal(plrc.getOrlimit())).multiply(slSmallloanProject.getDayAccrualRate()).divide(new BigDecimal(100)));
					plrc.setType(Short.valueOf("2"));
					plrc.setTypeRelation(la.getBidInfoID().toString());
					plMmObligatoryRightChildrenDao.save(plrc);
				}
				la.setSaleStatus(Short.valueOf("7"));
				la.setSaleSuccessTime(new Date());
				plBidInfo.setIsToObligatoryRightChildren(Short.valueOf("1"));
				plBidInfoDao.save(plBidInfo);
				dao.save(la);
			}
		}
		//有问题，记得执行的时候该下List<BpFundIntent> listintent = bpFundIntentDao.getByprincipalRepayment(pbi.getBidId(), pbi.getOrderNo());
		/* List<PlBidInfo> listPlBidInfo=plBidInfoDao.getByUserName("leeboy");
			int j=0;
			for(PlBidInfo pbi:listPlBidInfo){
				j++;
				System.out.println("j==="+j);
				List<BpFundIntent> listintent = bpFundIntentDao.getByprincipalRepayment(pbi.getBidId(), pbi.getOrderNo());
				int i=0;
				for(BpFundIntent l:listintent){
					if(l.getIsCheck()==0){
						
						i++;
					System.out.println(i+"l.getFundIntentId()==="+l.getFundIntentId());
					PlBidInfo plBidInfo=plBidInfoDao.getByOrderNo(l.getOrderNo());
					   if(null!=plBidInfo&&null!=plBidInfo.getPlBidPlan()){
						   PlBidPlan  plBidPlan=plBidInfo.getPlBidPlan();
						   Long projectId=null;
							if(plBidPlan.getProType().equals("P_Dir")){
								BpPersionDirPro bpPersionDirPro=bpPersionDirProService.get(plBidPlan.getPDirProId());//
								projectId=bpPersionDirPro.getProId();
							}else if(plBidPlan.getProType().equals("B_Dir")){
								
								BpBusinessDirPro  bpBusinessDirPro=bpBusinessDirProService.get(plBidPlan.getBdirProId());
								projectId=bpBusinessDirPro.getProId();
							};
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							
							SlSmallloanProject slSmallloanProject= slSmallloanProjectService.get(projectId);
							
						
						PlMmObligatoryRightChildren plrc=new PlMmObligatoryRightChildren();
						plrc.setPrincipalMoney(l.getIncomeMoney());
						plrc.setIntentDate(l.getIntentDate());
						plrc.setStartDate(plBidPlan.getStartIntentDate());
						int days=DateUtil.getDaysBetweenDate(sdf.format(plrc.getStartDate()), sdf.format(plrc.getIntentDate()));
						plrc.setOrlimit(days);
						plrc.setDayRate(slSmallloanProject.getDayAccrualRate());
						plrc.setAvailableMoney(l.getIncomeMoney());
						plrc.setParentOrBidId(plBidPlan.getBidId());
						plrc.setParentOrBidName(plBidPlan.getBidProName());
						plrc.setProjectId(projectId);
						plrc.setProjectName(slSmallloanProject.getProjectName());
						plrc.setChildrenorName(plrc.getParentOrBidName()+l.getPayintentPeriod());
						plrc.setSurplusValueMoney(l.getIncomeMoney().multiply(new BigDecimal(plrc.getOrlimit())).multiply(slSmallloanProject.getDayAccrualRate()).divide(new BigDecimal(100)));
						plrc.setType(Short.valueOf("3"));
						plrc.setTypeRelation(pbi.getId().toString());
						plMmObligatoryRightChildrenDao.save(plrc);
						
						
						plBidInfo.setIsToObligatoryRightChildren(Short.valueOf("1"));
						plBidInfoDao.save(plBidInfo);
					}
					   }
				}
			}*/
		/*
		List<BpFundIntent> listintent = bpFundIntentDao.getAll();
		
		
		
		for(BpFundIntent l:listintent){
			if(l.getFundType().equals("principalRepayment") &&null!=l.getOrderNo()){
				
				
			System.out.println("l.getFundIntentId()==="+l.getFundIntentId());
			PlBidInfo plBidInfo=plBidInfoDao.getByOrderNo(l.getOrderNo());
			   if(null!=plBidInfo&&null!=plBidInfo.getPlBidPlan()){
				   PlBidPlan  plBidPlan=plBidInfo.getPlBidPlan();
				   Long projectId=null;
					if(plBidPlan.getProType().equals("P_Dir")){
						BpPersionDirPro bpPersionDirPro=bpPersionDirProService.get(plBidPlan.getPDirProId());//
						projectId=bpPersionDirPro.getProId();
					}else if(plBidPlan.getProType().equals("B_Dir")){
						
						BpBusinessDirPro  bpBusinessDirPro=bpBusinessDirProService.get(plBidPlan.getBdirProId());
						projectId=bpBusinessDirPro.getProId();
					};
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					
					SlSmallloanProject slSmallloanProject= slSmallloanProjectService.get(projectId);
					
				
				PlMmObligatoryRightChildren plrc=new PlMmObligatoryRightChildren();
				plrc.setPrincipalMoney(l.getIncomeMoney());
				plrc.setIntentDate(l.getIntentDate());
				plrc.setStartDate(plBidPlan.getStartIntentDate());
				int days=DateUtil.getDaysBetweenDate(sdf.format(plrc.getStartDate()), sdf.format(plrc.getIntentDate()));
				plrc.setOrlimit(days);
				plrc.setDayRate(slSmallloanProject.getDayAccrualRate());
				plrc.setAvailableMoney(l.getIncomeMoney());
				plrc.setParentOrBidId(plBidPlan.getBidId());
				plrc.setParentOrBidName(plBidPlan.getBidProName());
				plrc.setProjectId(projectId);
				plrc.setProjectName(slSmallloanProject.getProjectName());
				plrc.setChildrenorName(plrc.getParentOrBidName()+l.getPayintentPeriod());
				plrc.setSurplusValueMoney(l.getIncomeMoney().multiply(new BigDecimal(plrc.getOrlimit())).multiply(slSmallloanProject.getDayAccrualRate()).divide(new BigDecimal(100)));
				plrc.setType(Short.valueOf("2"));
			//	plrc.setTypeRelation(la.getBidInfoID().toString());
				plMmObligatoryRightChildrenDao.save(plrc);
			}
			   }*/
				
	//	la.setSaleStatus(Short.valueOf("7"));
	//	plBidInfo.setIsToObligatoryRightChildren(Short.valueOf("1"));
	//	plBidInfoDao.save(plBidInfo);
	//	dao.save(la);
	}


	@Override
	public PlBidSale getMoreinfoById(Long id) {
		// TODO Auto-generated method stub
		return dao.getMoreinfoById(id);
	}

}