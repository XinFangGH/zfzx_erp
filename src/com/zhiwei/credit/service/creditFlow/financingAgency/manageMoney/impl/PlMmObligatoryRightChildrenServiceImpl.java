package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.supervise.SlSuperviseRecordDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessOrProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionOrProService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;

/**
 * 
 * @author 
 *
 */
public class PlMmObligatoryRightChildrenServiceImpl extends BaseServiceImpl<PlMmObligatoryRightChildren> implements PlMmObligatoryRightChildrenService{
	@SuppressWarnings("unused")
	private PlMmObligatoryRightChildrenDao dao;
	@Resource
	private BpBusinessOrProService bpBusinessOrProService;
	@Resource
	private BpPersionOrProService bpPersionOrProService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService; // 小额贷款
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private SlSuperviseRecordDao slSuperviseRecordDao;
	@Resource
	private PlBidPlanService plBidPlanService;
	
	public PlMmObligatoryRightChildrenServiceImpl(PlMmObligatoryRightChildrenDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PlMmObligatoryRightChildren> listbysearch(PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.listbysearch(pb, map);
	}

	@Override
	public String createObligatoryRightChildren(PlBidPlan plBidPlan) {
		Long projectId=null;
		BigDecimal summoney=null;
		if(plBidPlan.getProType().equals("B_Or")){
			BpBusinessOrPro bpBusinessOrPro=bpBusinessOrProService.get(plBidPlan.getBorProId());
			projectId=bpBusinessOrPro.getProId();
			summoney=bpBusinessOrPro.getBidMoney();
		}else if(plBidPlan.getProType().equals("P_Or")){
			
			BpPersionOrPro  bpPersionOrPro=bpPersionOrProService.get(plBidPlan.getPOrProId());
			projectId=bpPersionOrPro.getProId();
			summoney=bpPersionOrPro.getBidMoney();
		};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<SlFundIntent> list = slFundIntentDao.getByprincipalRepayment(projectId,"SmallLoan");
		SlSmallloanProject slSmallloanProject= slSmallloanProjectService.get(projectId);
		
		plBidPlan.setCreatetime(new Date());
		plBidPlan.setUpdatetime(new Date());
		plBidPlan.setBiddingTypeId(Long.valueOf("1"));
    	plBidPlanService.save(plBidPlan);
		
		for(SlFundIntent l:list){
			BigDecimal incomMoney=l.getIncomeMoney().multiply(plBidPlan.getBidMoney()).divide(summoney,2,BigDecimal.ROUND_DOWN);
			PlMmObligatoryRightChildren plrc=new PlMmObligatoryRightChildren();
			plrc.setPrincipalMoney(incomMoney);
			plrc.setIntentDate(l.getIntentDate());
			plrc.setStartDate(slSmallloanProject.getStartDate());
			int days=DateUtil.getDaysBetweenDate(sdf.format(slSmallloanProject.getStartDate()), sdf.format(l.getIntentDate()));
			plrc.setOrlimit(days);
			plrc.setDayRate(slSmallloanProject.getDayAccrualRate());
			plrc.setAvailableMoney(incomMoney);
			plrc.setParentOrBidId(plBidPlan.getBidId());
			plrc.setParentOrBidName(plBidPlan.getBidProName());
			plrc.setProjectId(projectId);
			plrc.setProjectName(slSmallloanProject.getProjectName());
			plrc.setChildrenorName(plrc.getParentOrBidName()+l.getPayintentPeriod());
			plrc.setSurplusValueMoney(incomMoney.multiply(new BigDecimal(plrc.getOrlimit())).multiply(slSmallloanProject.getDayAccrualRate()).divide(new BigDecimal(100)));
			plrc.setChildType(plBidPlan.getChildType());//债权类型
			plrc.setReceiverName(plBidPlan.getReceiverName());
			plrc.setReceiverP2PAccountNumber(plBidPlan.getReceiverP2PAccountNumber());
			dao.save(plrc);
		}
		return "";
	}

	@Override
	public List<PlMmObligatoryRightChildren> listbyUPLan(PagingBean pb,
			Map<String, String> map) {
		return dao.listbyUPLan(pb, map);
	}

	@Override
	public String createOblSuperviseRightChildren(PlBidPlan plBidPlan,
			Long slSuperviseRecordId,Long porProId) {
		// TODO Auto-generated method stub
		Long projectId=null;
		BigDecimal summoney=null;
		if(plBidPlan.getProType().equals("B_Or")){
			BpBusinessOrPro bpBusinessOrPro=bpBusinessOrProService.get(porProId);//
			projectId=bpBusinessOrPro.getProId();
			summoney=bpBusinessOrPro.getBidMoney();
		}else if(plBidPlan.getProType().equals("P_Or")){
			
			BpPersionOrPro  bpPersionOrPro=bpPersionOrProService.get(porProId);
			projectId=bpPersionOrPro.getProId();
			summoney=bpPersionOrPro.getBidMoney();
		};
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<SlFundIntent> list = slFundIntentDao.findSlSuperviseByFundType(projectId,slSuperviseRecordId,"SmallLoan","principalRepayment");
		SlSmallloanProject slSmallloanProject= slSmallloanProjectService.get(projectId);
		SlSuperviseRecord slSuperviseRecord=slSuperviseRecordDao.get(Long.valueOf(slSuperviseRecordId));
		for(SlFundIntent l:list){
			BigDecimal incomMoney=l.getIncomeMoney().multiply(plBidPlan.getBidMoney()).divide(summoney,2,BigDecimal.ROUND_DOWN);
			PlMmObligatoryRightChildren plrc=new PlMmObligatoryRightChildren();
			plrc.setPrincipalMoney(incomMoney);
			plrc.setIntentDate(l.getIntentDate());
			plrc.setStartDate(slSuperviseRecord.getStartDate());
			int days=DateUtil.getDaysBetweenDate(sdf.format(slSuperviseRecord.getStartDate()), sdf.format(l.getIntentDate()));
			plrc.setOrlimit(days);
			plrc.setDayRate(slSuperviseRecord.getDayAccrualRate());
			plrc.setAvailableMoney(incomMoney);
			plrc.setParentOrBidId(plBidPlan.getBidId());
			plrc.setParentOrBidName(plBidPlan.getBidProName());
			plrc.setProjectId(projectId);
			plrc.setProjectName(slSmallloanProject.getProjectName());
			plrc.setChildrenorName(plrc.getParentOrBidName()+l.getPayintentPeriod());
			plrc.setSurplusValueMoney(incomMoney.multiply(new BigDecimal(plrc.getOrlimit())).multiply(slSuperviseRecord.getDayAccrualRate()).divide(new BigDecimal(100)));
			plrc.setChildType(slSmallloanProject.getChildType());//债权类型
			dao.save(plrc);
		}
		return "";
	}
	
	@Override
	public List<PlMmObligatoryRightChildren> balanceList(PagingBean pb,Map<String, String> map) {
		return dao.balanceList(pb, map);
	}

	@Override
	public BigDecimal totalMoney(String account,String keystr) {
		return dao.totalMoney(account,keystr);
	}
}