package com.zhiwei.credit.service.creditFlow.finance.compensatory.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.finance.compensatory.PlBidCompensatoryDao;
import com.zhiwei.credit.dao.customer.BpCustRelationDao;
import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatory;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.log.BatchRunRecord;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.log.BatchRunRecordService;

/**
 * 
 * @author 
 *
 */
public class PlBidCompensatoryServiceImpl extends BaseServiceImpl<PlBidCompensatory> implements PlBidCompensatoryService{
	@SuppressWarnings("unused")
	private PlBidCompensatoryDao dao;
	@Resource
	private BpFundIntentDao bpFundIntentDao;
	@Resource
	private PlBidPlanService plBidPlanService; 
	@Resource
	private BpCustRelationDao bpCustRelationDao;
	@Resource
	private BatchRunRecordService batchRunRecordService;
	
	public PlBidCompensatoryServiceImpl(PlBidCompensatoryDao dao) {
		super(dao);
		this.dao=dao;
	}

	/* (non-Javadoc)
	 * @see com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryService#saveComPensatoryService(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void saveComPensatoryService(String planId, String peridId,String cardNo, String type,String  period) {
		try{
			Boolean flag=dao.isExist("requestNo", cardNo);
			if(!flag){//表示没有生成罚息台账
				BigDecimal money=bpFundIntentDao.sumAllCompensatoryMoney(planId,peridId,null);
				BigDecimal money1 =bpFundIntentDao.sumAllCompensatoryMoney(planId, period);
				if(money1!=null&&money1.compareTo(new BigDecimal(0))>0){//代偿总金额大于0元
					PlBidPlan plBidPlan =plBidPlanService.get(Long.valueOf(planId));
					BpCustMember mem=plBidPlanService.getLoanMember(plBidPlan);
					if(mem!=null){//有借款人P2P账号
						List<BpCustRelation> bp=bpCustRelationDao.getP2pCustListById(mem.getId());
						if(bp!=null){
							BpCustRelation cust = bp.get(0);
							BpFundProject bfp=plBidPlanService.getBpFundProject(plBidPlan);
							if(bfp!=null){
								PlBidCompensatory plBidCompensatory=new PlBidCompensatory();
								plBidCompensatory.setRequestNo(cardNo);
								plBidCompensatory.setPayintentPeriod(Integer.valueOf(period));
								plBidCompensatory.setPlanId(Long.valueOf(planId));
								plBidCompensatory.setLoanerp2pId(mem.getId());
								plBidCompensatory.setCustmerId(cust.getOfflineCusId());//线下客户Id
								plBidCompensatory.setCustmerType(cust.getOfflineCustType());//线下客户类型
								plBidCompensatory.setCompensatoryType(type);
								if(type.equals(PlBidCompensatory.TYPE_PLATE)){
									plBidCompensatory.setCompensatoryName("平台");
								}else if(type.equals(PlBidCompensatory.TYPE_GURANEE)){
									BpCustMember memGu=plBidPlanService.getGuraneeMember(plBidPlan);
									if(memGu!=null){
										plBidCompensatory.setCompensatoryName(memGu.getTruename());
										plBidCompensatory.setCompensatoryP2PId(memGu.getId());
									}
									
								}
								plBidCompensatory.setCompensatoryMoney(money1);
								plBidCompensatory.setCompensatoryDate(new Date());
								plBidCompensatory.setPunishRate(bfp.getOverdueRate());//逾期后代偿
								plBidCompensatory.setCompensatoryDays(0);
								plBidCompensatory.setPunishMoney(new BigDecimal("0"));
								plBidCompensatory.setBackPunishMoney(new BigDecimal("0"));
								plBidCompensatory.setBackCompensatoryMoney(new BigDecimal("0"));
								dao .save(plBidCompensatory);
							}
						}
					}
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/* 查询代偿台账列表
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryService#compensatoryList(com.zhiwei.core.web.paging.PagingBean, java.util.Map)
	 */
	@Override
	public List<PlBidCompensatory> compensatoryList(PagingBean pb,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.compensatoryList(pb,map);
	}

	/* (non-Javadoc)
	 * @see com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryService#getOneList(java.lang.Long)
	 */
	@Override
	public PlBidCompensatory getOneList(Long id) {
		// TODO Auto-generated method stub
		return dao.getOneList(id);
	}
	
	@Override
	public void  calculateOverDueDays(){
		List<PlBidCompensatory> list=dao.compensatoryList(null,null);
		 //跑批记录 
		AppUser appUser = ContextUtil.getCurrentUser();
		String pushUserName = "定时跑批";
		Long pushUserId = null;
		if(null != appUser && !"".equals(appUser)){
			pushUserName = appUser.getFullname();
			pushUserId = appUser.getUserId();
		}
		BatchRunRecord batchRunRecord = new BatchRunRecord();
		batchRunRecord.setRunType(BatchRunRecord.HRY_1008);
		batchRunRecord.setAppUserId(pushUserId);
		batchRunRecord.setAppUserName(pushUserName);
		batchRunRecord.setStartRunDate(new Date());
		batchRunRecord.setHappenAbnorma("正常");
		batchRunRecord.setTotalNumber(Long.valueOf(list != null ? list.size() : 0));
		if(list!=null&&list.size()>0){
			for(PlBidCompensatory temp:list){
				try {
					//判断逾期台账是否全部偿付
					if(temp.getBackStatus()<2&&temp.getTotalMoney().compareTo(new BigDecimal(0))>0){
						//计算累计代偿天数（从代偿天开始计算，截止日到今天为止）
						int days=DateUtil.getDaysBetweenDate(temp.getCompensatoryDate(), new Date());
						//计算罚息金额（每日重新计算：（代偿总额*罚息费率*累计代偿天数）/100   ；四舍五入保留小数位两位
						BigDecimal fee=temp.getCompensatoryMoney().multiply(temp.getPunishRate()).multiply(new BigDecimal(days)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
						
						PlBidCompensatory plBidCompensatory=dao.get(temp.getId());
						plBidCompensatory.setPunishMoney(fee);
						plBidCompensatory.setCompensatoryDays(days);
						dao.merge(plBidCompensatory);
					}
				} catch (Exception e) {
					String ids="";
					if(null != batchRunRecord.getIds()){
						ids =batchRunRecord.getIds() + "," + temp.getId();
					}else{
						ids = temp.getId().toString();
					}
					batchRunRecord.setIds(ids);
					batchRunRecord.setHappenAbnorma("异常");
					e.printStackTrace();
				}
			}
		}
		batchRunRecord.setEndRunDate(new Date());
		batchRunRecordService.save(batchRunRecord);
	}

}