package com.zhiwei.credit.service.creditFlow.fund.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.credit.proj.settlecenter.entity.OwnerShip;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidInfoDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidPlanDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.business.BpBusinessDirProDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.persion.BpPersionDirProDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.SettlementReviewerPayDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.creditFlow.customer.person.BpMoneyBorrowDemand;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.customer.person.workcompany.WorkCompany;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementInfo;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.fund.project.OwnerShipService;
import com.zhiwei.credit.service.creditFlow.fund.project.SettlementInfoService;
import com.zhiwei.credit.service.creditFlow.fund.project.SettlementReviewerPayService;

/**
 * 
 * @author 
 *
 */
public class SettlementReviewerPayServiceImpl extends BaseServiceImpl<SettlementReviewerPay> implements SettlementReviewerPayService{
	@SuppressWarnings("unused")
	private SettlementReviewerPayDao dao;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private BpCustMemberDao bpCustMemberDao;
	@Resource
	private BpFundIntentDao bpFundIntentDao;
	@Resource
	private SettlementInfoService settlementInfoService;
	@Resource
	private SlSmallloanProjectDao slSmallloanProjectDao;
	@Resource
	private BpBusinessDirProDao bpBusinessDirProDao;
	@Resource
	private BpPersionDirProDao bpPersionDirProDao;
	@Resource
	private PlBidPlanDao plBidPlanDao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private OwnerShipService ownerShipService;
	@Resource
	private PlBidInfoDao plBidInfoDao;
	
	public SettlementReviewerPayServiceImpl(SettlementReviewerPayDao dao) {
		super(dao);
		this.dao=dao;
	}
	public List<SettlementReviewerPay> platInvestCore(HttpServletRequest request){
		return dao.platInvestCore(request);
	}
	public List<SettlementReviewerPay> listSettle(Long id,String startDate,String endDate){
		return dao.listSettle(id, startDate, endDate);
	}
	public List<SettlementReviewerPay> listInformation(String date){
		return dao.listInformation(date);
	}
	@Override
	public List<SettlementReviewerPay> getByOrgId(String orgId){
		return dao.getByOrgId(orgId);
	}
	/**
	 * 每日保有量计算
	 * 计算前一天的保有量
	 */
	@Override
	public void createSettleInfo(){
		//查询出符合条件的部门
		//查询状态为 1 - 投资推广部  2- 投资部  3-融资部
		QueryFilter filter1 = new QueryFilter();
		List<Short> list1 = new ArrayList<Short>();
		list1.add(new Short("1"));
		list1.add(new Short("2"));
		list1.add(new Short("3"));
		filter1.addFilter("Q_orgType_SN_EQ","2");
		filter1.addFilterIn("Q_settlementType_SN_IN", list1);
		List<Organization> orgs = organizationDao.getAll(filter1);
		System.out.println("进入保有量计算方法--------------部门数量为=="+orgs.size());
		Date yestoday = DateUtil.addDaysToDate(new Date(), -1);   //计算昨天的提成
		if(orgs!=null&&orgs.size()>0){
			for(Organization o:orgs){
				if(Short.valueOf("1").equals(o.getSettlementType())){   //投资推荐部的
					bidSettleInfo(o.getOrgId(), o.getSettlementType().toString(),yestoday);
				}else if(Short.valueOf("2").equals(o.getSettlementType())){ //投资部的
					bidSettleInfo(o.getOrgId(), o.getSettlementType().toString(),yestoday);
				}else if(Short.valueOf("3").equals(o.getSettlementType())){  //融资部的
					loanSettleInfo(o.getOrgId(),yestoday);
				}
			}
		}
		
	}
	/**
	 * 每日保有量计算
	 * 计算前一天的保有量
	 */
	@Override
	public void createSettleInfoAll(Date startDate,Date endDate){
		//查询出符合条件的部门
		//查询状态为 1 - 投资推广部  2- 投资部  3-融资部
		QueryFilter filter1 = new QueryFilter();
		List<Short> list1 = new ArrayList<Short>();
		list1.add(new Short("1"));
		list1.add(new Short("2"));
		list1.add(new Short("3"));
		filter1.addFilter("Q_orgType_SN_EQ","2");
		filter1.addFilterIn("Q_settlementType_SN_IN", list1);
		List<Organization> orgs = organizationDao.getAll(filter1);
		System.out.println("进入保有量计算方法--------------部门数量为=="+orgs.size());
		if(orgs!=null&&orgs.size()>0){
			for (Date yestoday = startDate; yestoday.before(endDate); yestoday=DateUtil.addDaysToDate(yestoday, 1)){
				try{
					for(Organization o:orgs){
						if(Short.valueOf("1").equals(o.getSettlementType())){   //投资推荐部的
							bidSettleInfo(o.getOrgId(), o.getSettlementType().toString(),yestoday);
						}else if(Short.valueOf("2").equals(o.getSettlementType())){ //投资部的
							bidSettleInfo(o.getOrgId(), o.getSettlementType().toString(),yestoday);
						}else if(Short.valueOf("3").equals(o.getSettlementType())){  //融资部的
							loanSettleInfo(o.getOrgId(),yestoday);
						}
					}
				System.out.println("计算时间为："+DateUtil.getFormatDateTime(yestoday, "yyyy-MM-dd"));
				}catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
		}
	}
	/**
	 * 投资部及推广部保有量计算
	 * @param orgId
	 * @param type
	 */
	public void bidSettleInfo(Long orgId,String type,Date yestoday){
//		Date yestoday = DateUtil.addDaysToDate(new Date(), -1);   //计算昨天的提成
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Organization org = organizationDao.get(orgId);
		String recommendCode = null;
		BigDecimal totleMoney = BigDecimal.ZERO;   //保有量
		if(org!=null){
			if("2".equals(type)){
				recommendCode = org.getRecommendCode();
				if(recommendCode==null){
					recommendCode="";
				}
			}else{
				recommendCode = null;
			}
			List<BpCustMember> mems = bpCustMemberDao.getBpCustMemberByInternalCode(recommendCode);
			List<OwnerShip> listShip = new ArrayList<OwnerShip>();
			if(mems!=null&&mems.size()>0){
				try{
				for(BpCustMember m:mems){
					List<BpFundIntent> fundList = bpFundIntentDao.listPrincipalByInvestId(m.getId(),sf.format(yestoday));   //查询所属投资人款项
//					System.out.println("fundList.size========"+fundList.size());
					for(BpFundIntent i:fundList){
						totleMoney = totleMoney.add(i.getSumMoney());
						String orderNo = i.getOrderNo();
						Long investPersonId = null;
						String investPersonName = null;
						if(orderNo!=null&&!"".equals(orderNo)){
							PlBidInfo bidInfo = plBidInfoDao.getByOrderNo(orderNo);
							if(bidInfo!=null){
								investPersonId = bidInfo.getNewInvestPersonId();
								investPersonName = bidInfo.getNewInvestPersonName();
							}
						}
						//插入提成详情表
						OwnerShip ship = new OwnerShip();
						ship.setInvestId((investPersonId!=null&&!"".equals(investPersonId))?investPersonId:i.getInvestPersonId());
						ship.setInvestName((investPersonId!=null&&!"".equals(investPersonId))?investPersonName:i.getInvestpersonName());
						ship.setBidId(i.getBidPlanId());
						ship.setBidName(i.getBidProName());
						ship.setTransferDate(yestoday);
						ship.setReMainMoney(i.getSumMoney());
						listShip.add(ship);
					}
				}
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			//插入每日提成信息
			SettlementInfo info = new SettlementInfo();
			info.setSettleMoney(totleMoney);
			info.setRoyaltyRatio(org.getCommissionRate()==null?BigDecimal.ZERO:org.getCommissionRate());
			info.setRoyaltyMoney(totleMoney.multiply(org.getCommissionRate()==null?BigDecimal.ZERO:org.getCommissionRate()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
			info.setCreateDate(yestoday);
			info.setOrgId(org.getOrgId());
			SettlementInfo info1 = settlementInfoService.save(info);
			if(listShip!=null&&listShip.size()>0){
			System.out.println("投资部保有量插入明细==="+listShip);
				for(OwnerShip s:listShip){
					s.setInfoId(info1.getInfoId());
					ownerShipService.save(s);
				}
			}
		}
	}
	/**
	 * 查询融资部门日化保有量计算
	 */
	public void loanSettleInfo(Long orgId,Date yestoday){
//		Date yestoday = DateUtil.addDaysToDate(new Date(), -1);   //计算昨天的提成
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Organization org = organizationDao.get(orgId);
		BigDecimal totleMoney = BigDecimal.ZERO;   //保有量
		if(org!=null){
			QueryFilter filter1 = new QueryFilter();
			filter1.addFilter("Q_departId_L_EQ", orgId);
			if(orgId==108L){
				System.out.println("");
			}
			List<SlSmallloanProject> list1 = slSmallloanProjectDao.getAll(filter1);   //查询满足条件的项目
			List<OwnerShip> listShip = new ArrayList<OwnerShip>();
			if(list1!=null&&list1.size()>0){
				for(SlSmallloanProject s:list1){
					if("company_customer".equals(s.getOppositeType())){
						BpBusinessDirPro bPro = bpBusinessDirProDao.getByProjectId1(s.getProjectId(), s.getBusinessType());   //查询中间表
						if(bPro!=null){
							List<PlBidPlan> plans = plBidPlanDao.listByBdirProId(bPro.getBdirProId());
							if(plans!=null&&plans.size()>0){
								for(PlBidPlan p : plans){
									BigDecimal totleMoney1 = BigDecimal.ZERO;
									List<BpFundIntent> funds = bpFundIntentDao.listPrincipalByBidId(p.getBidId(), sf.format(yestoday));
									if(funds!=null&&funds.size()>0){
										for(BpFundIntent f:funds){
											totleMoney = totleMoney.add(f.getNotMoney());
											totleMoney1 = totleMoney1.add(f.getNotMoney());
										}
									}
									OwnerShip ship = new OwnerShip();
									ship.setBorrower(p.getReceiverP2PAccountNumber());
									ship.setBorrowerId(p.getReciverId());
									ship.setBidId(p.getBidId());
									ship.setBidName(p.getBidProName());
									ship.setTransferDate(yestoday);
									ship.setReMainMoney(totleMoney1);
									listShip.add(ship);
								}
							}
						}
					}else if("person_customer".equals(s.getOppositeType())){
						BpPersionDirPro pPro = bpPersionDirProDao.getByProjectId1(s.getProjectId(), s.getBusinessType());   //查询中间表
						if(pPro!=null){
							List<PlBidPlan> plans = plBidPlanDao.listByPdirProId(pPro.getPdirProId());
							if(plans!=null&&plans.size()>0){
								for(PlBidPlan p : plans){
									BigDecimal totleMoney1 = BigDecimal.ZERO;
									try{
									List<BpFundIntent> funds = bpFundIntentDao.listPrincipalByBidId(p.getBidId(), sf.format(yestoday));
									if(funds!=null&&funds.size()>0){
										for(BpFundIntent f:funds){
											totleMoney = totleMoney.add(f.getNotMoney());
											totleMoney1 = totleMoney1.add(f.getNotMoney());
										}
									}
								  }catch (Exception e) {
									// TODO: handle exception
								}
								  OwnerShip ship = new OwnerShip();
									ship.setBorrower(p.getReceiverP2PAccountNumber());
									ship.setBorrowerId(p.getReciverId());
									ship.setBidId(p.getBidId());
									ship.setBidName(p.getBidProName());
									ship.setTransferDate(yestoday);
									ship.setReMainMoney(totleMoney1);
									listShip.add(ship);
								}
							}
						}
					}
				}
			}
			//插入每日提成信息
			SettlementInfo info = new SettlementInfo();
			info.setSettleMoney(totleMoney);
			info.setRoyaltyRatio(org.getCommissionRate()==null?BigDecimal.ZERO:org.getCommissionRate());
			info.setRoyaltyMoney(totleMoney.multiply(org.getCommissionRate()==null?BigDecimal.ZERO:org.getCommissionRate()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
			info.setCreateDate(yestoday);
			info.setOrgId(org.getOrgId());
			SettlementInfo info1 = settlementInfoService.save(info);
			if(listShip!=null&&listShip.size()>0){
				System.out.println("融资部保有量插入明细==="+listShip);
				for(OwnerShip s:listShip){
					s.setInfoId(info1.getInfoId());
					ownerShipService.save(s);
				}
			}
		}
	}
	@Override
	public Integer updateInfoNextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String settlementPayToChangeJson = flowRunInfo.getRequest().getParameter("settlementPayToChangeJson");
				String projectId = flowRunInfo.getRequest().getParameter("projectId");
				String taskName = flowRunInfo.getRequest().getParameter("taskName");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
								SettlementReviewerPay pay = this.get(Long.valueOf(projectId));
								if("shenhe".equals(taskName)){
									pay.setState(new Short("0"));
								}
								if("zhifu".equals(taskName)){
									pay.setState(new Short("1"));
								}
								this.merge(pay);
							}else{
								SettlementReviewerPay pay = this.get(Long.valueOf(projectId));
								SettlementReviewerPay pay1 = new SettlementReviewerPay();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), pay1, "settlementReviewerPay");
								BeanUtil.copyNotNullProperties(pay, pay1);
								if("jiesuan".equals(taskName)){
									pay.setState(new Short("0"));
								}
								if("shenhe".equals(taskName)){
									pay.setState(new Short("1"));
								}
								if("zhifu".equals(taskName)){
									pay.setState(new Short("2"));
								}
								this.merge(pay);
								//保存手续费
								if(settlementPayToChangeJson!=null&&!"".equals(settlementPayToChangeJson)){
									slActualToChargeService.savejson(settlementPayToChangeJson, pay.getId(), "InvestSettle", new Short("0"), pay.getCompanyId());
								}
							}
						}
					}
					vars.put("decisionResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}