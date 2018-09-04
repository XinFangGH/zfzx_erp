package com.zhiwei.credit.service.creditFlow.financingAgency.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidPlanDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.business.BpBusinessDirProDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.business.BpBusinessOrProDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.persion.BpPersionDirProDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.persion.BpPersionOrProDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.BpFundProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordDao;
import com.zhiwei.credit.dao.customer.BpCustRelationDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.PlBusinessDirProKeep;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.PlPersionDirProKeep;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.PlBusinessDirProKeepService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.PlPersionDirProKeepService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.TaskService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;

/**
 * 
 * @author 
 *
 */
public class PlBidPlanServiceImpl extends BaseServiceImpl<PlBidPlan> implements PlBidPlanService{
	@SuppressWarnings("unused")
	private PlBidPlanDao dao;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	@Resource
	private PlBusinessDirProKeepService plBusinessDirProKeepService;
	@Resource
	private PlPersionDirProKeepService plPersionDirProKeepService;
	@Resource
     private BpCustMemberService bpCustMemberService;
	@Resource
	private BpBusinessDirProDao bpBusinessDirProDao;
	@Resource
	private BpBusinessOrProDao bpBusinessOrProDao;
	@Resource
	private BpPersionDirProDao bpPersionDirProDao;
	@Resource
	private BpPersionOrProDao bpPersionOrProDao;
	@Resource
	private BpFundProjectDao bpFundProjectDao;
	@Resource
	private SlEarlyRepaymentRecordDao slEarlyRepaymentRecordDao;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private ProDefinitionDao proDefinitionDao;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private TaskService flowTaskService;
	@Resource
	private BpCustRelationDao bpCustRelationDao;
	
	public PlBidPlanServiceImpl(PlBidPlanDao dao) {
		super(dao);
		this.dao=dao;
	}
	@SuppressWarnings("unchecked")
	@Override
	public PlBidPlan bidDynamic(PlBidPlan plBidPlan){
		double progress;//进度
		int persionNum = 0;//人数
		BigDecimal money=new BigDecimal(0);//剩余金额
		//投标金额合计
		BigDecimal totalMoney=new BigDecimal(0);
		
		List<InvestPersonInfo> list=investPersonInfoService.getByBidPlanId(plBidPlan.getBidId());
		for(InvestPersonInfo investPI:list){
			totalMoney=totalMoney.add(investPI.getInvestMoney());
			persionNum=persionNum+1;
		}
		money=plBidPlan.getBidMoney().subtract(totalMoney);
		progress=Double.valueOf(totalMoney.divide(plBidPlan.getBidMoney(),BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)).toString());
		plBidPlan.setProgress(progress);//进度
		plBidPlan.setPersionNum(persionNum);//人数
		plBidPlan.setAfterMoney(money);//剩余金额
		return plBidPlan;
		
	}
	@Override
	public String findLoanTotalMoneyBySQL(String pid) {
		return dao.findLoanTotalMoneyBySQL(pid);
	}
	@Override
	public String findOrgMoneyBySQL(String pid, String flag) {
		return dao.findOrgMoneyBySQL( pid,  flag);
	}
	@Override
	public void loanMoney(Long plBidPlanId) {
		PlBidPlan plan = dao.get(plBidPlanId);
		plan.setIsLoan((short)1);
		//提交到完成才能改变状态，因为款项台账的有效状态是在提交到完成的时候改变的
		//plan.setState(PlBidPlan.STATE7);
		dao.merge(plan);
	}
	
	/**
	 * update by linyan  2015-4-09
	 * 通过 投标记录获取借款人注册信息
	 * @param PlBidInfo plBidInfo  投标记录
	 * @return
	 */
	@Override
	public BpCustMember getLoanMember(PlBidInfo plBidInfo){
		PlBidPlan bidplan = dao.get(plBidInfo.getBidId());
		return getLoanMember(bidplan);
	}
	/**
	 * update by linyan  2015-4-09
	 * 通过 标的信息获取借款人注册信息
	 * @param PlBidPlan bidplan  招标项目
	 * @return
	 */
	@Override
	public BpCustMember getLoanMember(PlBidPlan bidplan) {
		BpCustMember member=new BpCustMember();
		// 网站注册用户
		// 项目类型 企业直投 B_Dir 企业 债权 B_Or 个人直投 P_Dir 个人债权 P_Or * @return String
		String loanUserType = "";
		Long loanUserId = null;
		Long custMamberId = null;
		if (bidplan.getProType().equals("B_Dir")) {
			loanUserType = "b_loan";
			loanUserId = bidplan.getBpBusinessDirPro().getBusinessId();
		} else if (bidplan.getProType().equals("B_Or")) {
			loanUserType = "b_loan";
			member = bpCustMemberService.getMemberUserName(bidplan.getBpBusinessOrPro().getReceiverP2PAccountNumber());
			
		} else if (bidplan.getProType().equals("P_Dir")) {
			loanUserType = "p_loan";
			loanUserId = bidplan.getBpPersionDirPro().getPersionId();
			
		} else if (bidplan.getProType().equals("P_Or")) {
			loanUserType = "p_loan";
			member = bpCustMemberService.getMemberUserName(bidplan.getBpPersionOrPro().getReceiverP2PAccountNumber());//存放着债权人账户信息
		}
		if (loanUserId != null) {
			BpCustRelation bpCustRelation = bpCustRelationService.getByLoanTypeAndId(loanUserType, loanUserId);
			if (bpCustRelation != null) {
				custMamberId = bpCustRelation.getP2pCustId();
			}
		}
		if (custMamberId != null && !custMamberId.equals("")) {
			member = bpCustMemberService.get(custMamberId);
		}
		return member;
	}
	
	@Override
	public PlBidPlan getPlan(PlBidPlan bidplan) {
		try{
		QueryFilter filter = new QueryFilter();
		if (bidplan.getProType().equals("B_Dir")) {
			bidplan.setProjId(bidplan.getBpBusinessDirPro().getProId());
			bidplan.setMoneyPlanId(bidplan.getBpBusinessDirPro().getMoneyPlanId());
			bidplan.setInterestPeriod(Integer.valueOf(bidplan.getBpBusinessDirPro().getLoanLife()));
			bidplan.setYearRate(Double.valueOf(bidplan.getBpBusinessDirPro().getYearInterestRate().setScale(2).toString()).intValue());
			
			//获取信用等级
			filter.addFilter("Q_bpBusinessDirPro.bdirProId_L_EQ", bidplan.getBdirProId().toString());
			List<PlBusinessDirProKeep> list=plBusinessDirProKeepService.getAll(filter);
			if(list.size()>0){
			PlBusinessDirProKeep planKeep =(PlBusinessDirProKeep) list.get(0);
			bidplan.setCreditLeveId(planKeep.getCreditLevelId());
			}else{
				bidplan.setCreditLeveId(Long.valueOf(0));
			}
			
		} else if (bidplan.getProType().equals("B_Or")) {
			bidplan.setProjId(bidplan.getBpBusinessOrPro().getProId());
			bidplan.setMoneyPlanId(bidplan.getBpBusinessOrPro().getMoneyPlanId());
			bidplan.setInterestPeriod(Integer.valueOf(bidplan.getBpBusinessOrPro().getInterestPeriod()));
			bidplan.setYearRate(Double.valueOf(bidplan.getBpBusinessOrPro().getYearInterestRate().setScale(2).toString()).intValue());
			
			//获取信用等级
			filter.addFilter("Q_bpBusinessOrPro.borProId_L_EQ", bidplan.getBorProId().toString());
			List<PlBusinessDirProKeep> list=plBusinessDirProKeepService.getAll(filter);
			if(list.size()>0){
			PlBusinessDirProKeep planKeep =(PlBusinessDirProKeep)list.get(0);
			bidplan.setCreditLeveId(planKeep.getCreditLevelId());
			}else{
				bidplan.setCreditLeveId(Long.valueOf(0));
			}
		} else if (bidplan.getProType().equals("P_Dir")) {
			bidplan.setProjId(bidplan.getBpPersionDirPro().getProId());
			bidplan.setMoneyPlanId(bidplan.getBpPersionDirPro().getMoneyPlanId());
			bidplan.setInterestPeriod(Integer.valueOf(bidplan.getBpPersionDirPro().getLoanLife()));
			bidplan.setYearRate(Double.valueOf(bidplan.getBpPersionDirPro().getYearInterestRate().setScale(2).toString()).intValue());
			
			//获取信用等级
			filter.addFilter("Q_bpPersionDirPro.pdirProId_L_EQ", bidplan.getPDirProId().toString());
			List<PlPersionDirProKeep> list=plPersionDirProKeepService.getAll(filter);
			if(list.size()>0){
			PlPersionDirProKeep planKeep = (PlPersionDirProKeep)(list.get(0));
			bidplan.setCreditLeveId(planKeep.getCreditLevelId());
			}else{
				bidplan.setCreditLeveId(Long.valueOf(0));
			}
			
		} else if (bidplan.getProType().equals("P_Or")) {
			bidplan.setProjId(bidplan.getBpPersionOrPro().getProId());
			bidplan.setMoneyPlanId(bidplan.getBpPersionOrPro().getMoneyPlanId());
			bidplan.setInterestPeriod(Integer.valueOf(bidplan.getBpPersionOrPro().getInterestPeriod()));
			bidplan.setYearRate(Double.valueOf(bidplan.getBpPersionOrPro().getYearInterestRate().setScale(2).toString()).intValue());
			
			//获取信用等级
			filter.addFilter("Q_bpPersionOrPro.porProId_L_EQ", bidplan
					.getPOrProId().toString());
			List<PlPersionDirProKeep> list=plPersionDirProKeepService.getAll(filter);
			if(list.size()>0){
			PlPersionDirProKeep planKeep = (PlPersionDirProKeep)list.get(0);
			bidplan.setCreditLeveId(planKeep.getCreditLevelId());
			}else{
				bidplan.setCreditLeveId(Long.valueOf(0));
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return bidplan;
	}
	@Override
	public void bidComplete(Long planId,HttpServletRequest req) {
		QueryFilter filter=new QueryFilter(req);
		filter.getCommands().removeAll(filter.getCommands());
		filter.addFilter("Q_bidPlanId_L_EQ", planId.toString());
		List<BpFundIntent> bplist=bpFundIntentService.getAll(filter);
		BigDecimal notMoney=new BigDecimal(0);
		if(bplist!=null){
			for(BpFundIntent bp:bplist){
				notMoney=notMoney.add(bp.getNotMoney());
			}
			if(notMoney.compareTo(BigDecimal.ZERO)==0){
				PlBidPlan plan=this.get(planId);
				plan.setState(PlBidPlan.STATE7);
				this.merge(plan);
			}
		}

		
	}
	
	@Override
	public List<PlBidPlan> allLoanedList(HttpServletRequest request, PagingBean pb) {
		
		return dao.allLoanedList(request, pb);
	}
	@Override
	public String startBidPrePaymentProcess(Long bidId) {
		String customerName = "";
		PlBidPlan plBidPlan = this.dao.get(bidId);
		plBidPlan.setIsOtherFlow(Short.valueOf("1"));
		Long projectId=null;
		if(plBidPlan.getProType().equals("B_Dir")){
			BpBusinessDirPro bdirpro = bpBusinessDirProDao.get(plBidPlan.getBdirProId());
			projectId = bdirpro.getMoneyPlanId();
		}else if(plBidPlan.getProType().equals("B_Or")){
			BpBusinessOrPro borpro = bpBusinessOrProDao.get(plBidPlan.getBorProId());
			projectId = borpro.getMoneyPlanId();
		}else if(plBidPlan.getProType().equals("P_Dir")){
			BpPersionDirPro pdirpro = bpPersionDirProDao.get(plBidPlan.getPDirProId());
			projectId = pdirpro.getMoneyPlanId();
		}else if(plBidPlan.getProType().equals("P_Or")){
			BpPersionOrPro porpro = bpPersionOrProDao.get(plBidPlan.getPOrProId());
			projectId = porpro.getMoneyPlanId();
		}
		BpFundProject project=bpFundProjectDao.get(projectId);
		AppUser user = ContextUtil.getCurrentUser();
		SlEarlyRepaymentRecord slEarlyRepaymentRecord = new SlEarlyRepaymentRecord();
		slEarlyRepaymentRecord.setProjectId(projectId);
		slEarlyRepaymentRecord.setCheckStatus(0);
		slEarlyRepaymentRecord.setOpTime(new Date());
		slEarlyRepaymentRecord.setCreator(user.getFullname());
		slEarlyRepaymentRecord.setBusinessType(project.getBusinessType());
		slEarlyRepaymentRecord.setBidPlanId(bidId);
		this.slEarlyRepaymentRecordDao.save(slEarlyRepaymentRecord);
		this.dao.merge(plBidPlan);
		FlowRunInfo newFlowRunInfo = new FlowRunInfo();
		ProDefinition pdf = null;// 流程定义key值
		// Map<String, String> mapNew =null;
		// 不能从session中获取companyId,总公司的人员为分公司启动展期流程的时候,这样获取启动的却是总公司的展期流程.
		// 而是从项目表中获取对应的companyId
		// Long companyId=ContextUtil.getLoginCompanyId();
		Long companyId = project.getCompanyId();
		Organization org = organizationDao.get(companyId);
		String isGroupVersion = AppUtil.getSystemIsGroupVersion();
		if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
			pdf = proDefinitionDao.getByProcessName("BidPrepaymentFlow_"
					+ org.getKey());
		} else {
			pdf = proDefinitionDao.getByProcessName("BidPrepaymentFlow");
		}

		List<ProcessRun> processRunList = processRunDao
				.getByProcessNameProjectId(projectId,
						project.getBusinessType(), project.getFlowType());
		if (processRunList != null && processRunList.size() != 0) {
			customerName = processRunList.get(0).getCustomerName();
		}
		// mapNew =
		// this.getProjectInfo(project,"repaymentAheadOfTimeFlow_"+org.getKey());
		Long branchCompanyId = pdf.getBranchCompanyId();
		Map<String, Object> newVars = new HashMap<String, Object>();
		newVars.put("fundProjectId", projectId);
		newVars.put("projectId", project.getProjectId());
		newVars.put("slEarlyRepaymentId", slEarlyRepaymentRecord.getSlEarlyRepaymentId());
		newVars.put("oppositeType", project.getOppositeType());
		newVars.put("businessType", project.getBusinessType());
		newVars.put("customerName", customerName); //
		newVars.put("projectNumber", project.getProjectNumber()); //
		newVars.put("bidPlanId", bidId);
		newVars.put("proType", plBidPlan.getProType());
		newVars.put("productId", project.getProductId());
		
		newFlowRunInfo.getVariables().putAll(newVars);
		newFlowRunInfo.setBusMap(newVars);
		newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
		newFlowRunInfo.setFlowSubject(plBidPlan.getBidProName() + "-"
				+ plBidPlan.getBidProNumber());
		ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
		String str = "";
		if (run != null && run.getPiId() != null) {
			str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),
					user.getUserId().toString(), project.getProjectName());
			
		}
		return str;
	}
	@Override
	public List<PlBidPlan> findPlbidplanLoanAfter(HttpServletRequest request,
			PagingBean pb) {
		
		return dao.findPlbidplanLoanAfter(request,pb);
	}
	@Override
	public List<PlBidPlan> listByState(String state, String proType, Long proId) {
		
		return dao.listByState(state, proType, proId);
	}
	@Override
	public List<PlBidPlan> getByStateList(HttpServletRequest request,
			PagingBean pb) {
		return dao.getByStateList(request,pb);
	}
	@Override
	public Integer countList(HttpServletRequest request, PagingBean pb) {
		return dao.countList(request, pb);
	}

	/* (non-Javadoc)
	 * @see com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService#getBpFundProject(com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan)
	 */
	@Override
	public BpFundProject getBpFundProject(PlBidPlan bidplan) {
		//资金方案id
		Long moneyPlanId = null;
		BpFundProject bpFundProject = null;
		if (bidplan.getProType().equals("B_Dir")) {
			moneyPlanId = bidplan.getBpBusinessDirPro().getMoneyPlanId();
		} else if (bidplan.getProType().equals("B_Or")) {
			moneyPlanId = bidplan.getBpBusinessOrPro().getMoneyPlanId();
			
		} else if (bidplan.getProType().equals("P_Dir")) {
			moneyPlanId = bidplan.getBpPersionDirPro().getMoneyPlanId();
			
		} else if (bidplan.getProType().equals("P_Or")) {
			moneyPlanId = bidplan.getBpPersionOrPro().getMoneyPlanId();
		}
		if (moneyPlanId != null) {
			bpFundProject=bpFundProjectDao.get(moneyPlanId);
		}
		
		return bpFundProject;
	}
	/**
	 * 获取招标项目的担保机构的P2P账号
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService#getGuraneeMember(com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan)
	 */
	@Override
	public BpCustMember getGuraneeMember(PlBidPlan bidplan) {
		// TODO Auto-generated method stub
		//资金方案id
		String type=null;
		Long moneyPlanId = null;
		BpCustMember mem = null;
		if (bidplan.getProType().equals("B_Dir")) {
			type="e_cooperation";
			moneyPlanId = bidplan.getBpBusinessDirPro().getPlBusinessDirProKeep().getGuarantorsId();
		} else if (bidplan.getProType().equals("B_Or")) {
			type="e_cooperation";
			moneyPlanId = bidplan.getBpBusinessOrPro().getPlBusinessDirProKeep().getGuarantorsId();
			
		} else if (bidplan.getProType().equals("P_Dir")) {
			type="p_cooperation";
			moneyPlanId = bidplan.getBpPersionDirPro().getPlPersionDirProKeep().getGuarantorsId();
			
		} else if (bidplan.getProType().equals("P_Or")) {
			type="p_cooperation";
			moneyPlanId = bidplan.getBpPersionOrPro().getPlPersionDirProKeep().getGuarantorsId();
		}
		if (moneyPlanId != null) {
			BpCustRelation bp=bpCustRelationDao.getByLoanTypeAndId(type, moneyPlanId);
			if(bp!=null){
				mem=bpCustMemberService.get(bp.getP2pCustId());
			}
		}
		
		return mem;
	}

	@Override
	public List<PlBidPlan> listByTypeId(String proType, Long proId) {
		// TODO Auto-generated method stub
		return dao.listByTypeId(proType, proId);
	}
	@Override
	public Long countPdBidPlanList(HttpServletRequest request, PagingBean pb,
			int[] bidStates) {
		// TODO Auto-generated method stub
		return dao.countPdBidPlanList(request, pb, bidStates);
	}
	@Override
	public List<PlBidPlan> pdBidPlanList(HttpServletRequest request,
			PagingBean pb, int[] bidStates) {
		// TODO Auto-generated method stub
		return dao.pdBidPlanList(request, pb, bidStates);
	}
	@Override
	public List<PlBidPlan> bdBidPlanList(HttpServletRequest request,
			PagingBean pb, int[] bidStates) {
		// TODO Auto-generated method stub
		return dao.bdBidPlanList(request, pb, bidStates);
	}
	@Override
	public List<PlBidPlan> boBidPlanList(HttpServletRequest request,
			PagingBean pb, int[] bidStates) {
		// TODO Auto-generated method stub
		return dao.boBidPlanList(request, pb, bidStates);
	}
	@Override
	public Long countPoBidPlanList(HttpServletRequest request, PagingBean pb,
			int[] bidStates) {
		// TODO Auto-generated method stub
		return dao.countPoBidPlanList(request, pb, bidStates);
	}
	@Override
	public Long countbdBidPlanList(HttpServletRequest request, PagingBean pb,
			int[] bidStates) {
		// TODO Auto-generated method stub
		return dao.countbdBidPlanList(request, pb, bidStates);
	}
	@Override
	public Long countboBidPlanList(HttpServletRequest request, PagingBean pb,
			int[] bidStates) {
		// TODO Auto-generated method stub
		return dao.countboBidPlanList(request, pb, bidStates);
	}
	@Override
	public List<PlBidPlan> poBidPlanList(HttpServletRequest request,
			PagingBean pb, int[] bidStates) {
		// TODO Auto-generated method stub
		return dao.poBidPlanList(request, pb, bidStates);
	}
	@Override
	public List<PlBidPlan> getPlanByStatusList(Short valueOf, PagingBean pb,
			Map map) {
		// TODO Auto-generated method stub
		return dao.getPlanByStatusList(valueOf,pb,map);
	}
	@Override
	public int updateStatByMoney(Long bidId, BigDecimal currMoney) {
		int s=-1;
		PlBidPlan plan = this.get(bidId);
		// 投标金额合计
		BigDecimal totalMoney = bidedMoney(bidId).add(currMoney);
		if (totalMoney.compareTo(plan.getBidMoney()) == 0) {
			// 设置为已经齐标
			plan.setState(PlBidPlan.STATE2);
			// 发送邮件
			/*if (AppUtil.getSysConfig().get("systemEmail") != null) {
				String[] StrArr = AppUtil.getSysConfig().get("systemEmail")
						.toString().split("\\|");
				if (StrArr != null && StrArr.length > 0) {
					for (int i = 0; i < StrArr.length; i++) {
						MailData md = new MailData("满标提醒",
								StrArr[i].split(",")[0],
								StrArr[i].split(",")[1]);
						md.setProjName(plan.getBidProName());
						md.setProjNumber(plan.getBidProNumber());
						md.setBidId(plan.getBidId().toString());
						sendMail("mail/bidfullMsg.vm", md);
					}
				}
			}*/
			plan.setFullTime(new Date());// 设置齐标日期
			this.save(plan);
			s=0;
		}else if (totalMoney.compareTo(plan.getBidMoney())> 0){
			s=1;
		}else {
			s=-1;
		}
		return s;
	}
	@Override
	public BigDecimal bidedMoney(Long bidId) {
		List<InvestPersonInfo> investList=investPersonInfoService.getByPlanId(bidId);
		// 投标成功金额合计
		BigDecimal totalMoney = new BigDecimal(0);
		for(InvestPersonInfo invest:investList){
			totalMoney = totalMoney.add(invest.getInvestMoney());
		}
		return totalMoney;
	}
	
	@Override
	public List<PlBidPlan> getByProType(Map<String, String> map) {
		return dao.getByProType(map);
	}
	@Override
	public PlBidPlan getplanByLoanOrderNo(String loanOrderNo) {
		return dao.getplanByLoanOrderNo(loanOrderNo);
	}

	@Override
	public PlBidPlan getByBdirProId(Long aLong) {
		return dao.getByBdirProId(aLong);
	}


}