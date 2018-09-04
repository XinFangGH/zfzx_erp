package com.zhiwei.credit.service.creditFlow.finance.plateFormFinanceManage.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepaymentDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidPlanDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepayment;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.service.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepaymentService;

/**
 * 
 * @author 
 *
 */
public class SlRiskguaranteemoneyRepaymentServiceImpl extends BaseServiceImpl<SlRiskguaranteemoneyRepayment> implements SlRiskguaranteemoneyRepaymentService{
	@SuppressWarnings("unused")
	private SlRiskguaranteemoneyRepaymentDao dao;
	@Resource
	private PlBidPlanDao plBidPlanDao;
	@Resource
	private SlSmallloanProjectDao slSmallloanProjectDao;
	
	public SlRiskguaranteemoneyRepaymentServiceImpl(SlRiskguaranteemoneyRepaymentDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public void savePlateFormRepayment(String cardNo, String planId,
			BigDecimal allMoney,String peridId) {
		if(planId!=null&&!"".equals(planId)){
			PlBidPlan pl=plBidPlanDao.get(Long.valueOf(planId));
			//PlBidPlan pl=plBidPlanDao.getAllInfoByplanId(Long.valueOf(planId));
			Long proj = null;
			if (pl.getProType().equals("B_Dir")) {
				proj = pl.getBpBusinessDirPro().getProId();
			} else if (pl.getProType().equals("B_Or")) {
				proj = pl.getBpBusinessOrPro().getProId();
				
			} else if (pl.getProType().equals("P_Dir")) {
				proj = pl.getBpPersionDirPro().getProId();
				
			} else if (pl.getProType().equals("P_Or")) {
				proj = pl.getBpPersionOrPro().getProId();
				
			}
			if(proj!=null){
				SlSmallloanProject sl=slSmallloanProjectDao.get(proj);
				if(sl!=null){
					SlRiskguaranteemoneyRepayment rep=new SlRiskguaranteemoneyRepayment();
					rep.setRequestNo(cardNo);
					rep.setPayDate(new Date());
					rep.setPlanId(pl.getBidId());
					rep.setCompanyId(Long.valueOf("1"));
					rep.setNotRebackMoney(allMoney);
					rep.setPayMoney(allMoney);
					rep.setProjectId(sl.getProjectId());
					rep.setPlanType(pl.getProType());
					rep.setPunishmentDate(new Date());
					rep.setPunishmentRate(sl.getOverdueRateLoan());
					rep.setPlanrepayMentDate(DateUtil.parseDate(peridId, "yyyy-MM-dd"));
					this.dao.save(rep);
				}
			}
			
		}
		
	}

}