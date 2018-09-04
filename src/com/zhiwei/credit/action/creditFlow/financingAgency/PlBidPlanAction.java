package com.zhiwei.credit.action.creditFlow.financingAgency;

/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hurong.credit.config.HtmlConfig;
import com.messageAlert.service.SmsSendService;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.auto.PlBidAuto;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.PlBusinessDirProKeep;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.PlPersionDirProKeep;
import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepProtype;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.BpFinanceApplyUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.UploadLog;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.auto.PlBidAutoService;
import com.zhiwei.credit.service.creditFlow.contract.ProcreditContractService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessDirProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessOrProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.PlBusinessDirProKeepService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionDirProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionOrProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.PlPersionDirProKeepService;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlBiddingTypeService;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlKeepProtypeService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.fund.project.SettlementReviewerPayService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.ProcessService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.BpFinanceApplyUserService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;
import com.zhiwei.credit.service.system.BuildHtml2Web;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.system.UploadLogService;
import com.zhiwei.credit.service.thirdInterface.FuiouService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
import com.zhiwei.credit.util.Common;
import com.zhiwei.credit.util.JsonUtils;

import flexjson.JSONSerializer;
import fr.opensagres.xdocreport.utils.StringUtils;

/**
 *
 * @author
 *
 */
public class PlBidPlanAction extends BaseAction {
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private BuildHtml2Web buildHtml2WebService;
	private static final int PUBLISHSTAT = 1; // 已经发布
	private PlBidPlan plBidPlan;
	@Resource
	private PlBusinessDirProKeepService plBusinessDirProKeepService;
	@Resource
	private PlPersionDirProKeepService plPersionDirProKeepService;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private BpBusinessDirProService bpBusinessDirProService;
	@Resource
	private BpBusinessOrProService bpBusinessOrProService;
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	@Resource
	private BpPersionDirProService bpPersionDirProService;
	@Resource
	private BpPersionOrProService bpPersionOrProService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private ProcessService processService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private FuiouService fuiouService;
	@Resource
	private PlBidAutoService plBidAutoService;
	@Resource
	private YeePayService yeePayService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private PlBiddingTypeService plBiddingTypeService;
	@Resource
	private UploadLogService uploadLogService;
	@Resource
	private ProcreditContractService procreditContractService;
	@Resource
	private PlKeepProtypeService plKeepProtypeService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	// 得到整个系统全部的config.properties读取的所有资源
	@Resource
	private BpCouponsService bpCouponsService;
	@Resource
	private SmsSendService smsSendService;
	SmsSendUtil smsSendUtil= new SmsSendUtil();
	@Resource
	private BpFinanceApplyUserService bpFinanceApplyUserService;
	@Resource
	private SlSmallloanProjectService slSmalloanProjectService;
	@Resource
	private SettlementReviewerPayService settlementReviewerPayService;

	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();

	private Long bidId;
	/**
	 * 	标记多个状态的表查询的状态值,11代表全部；12代表招标中，已到期，已满标；13代表已满标，已到期
	 */

	private int state;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Long getBidId() {
		return bidId;
	}

	public void setBidId(Long bidId) {
		this.bidId = bidId;
	}

	public PlBidPlan getPlBidPlan() {
		return plBidPlan;
	}

	public void setPlBidPlan(PlBidPlan plBidPlan) {
		this.plBidPlan = plBidPlan;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 显示列表
	 */
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		// 标的类型
		String Q_proType_S_EQ = this.getRequest()
				.getParameter("Q_proType_S_EQ");

		String Q_state_N_EQ = getRequest().getParameter("Q_state_N_EQ");
		String flag = getRequest().getParameter("flag");
		// 已过期的列表
		if (null != Q_state_N_EQ && "".equals(Q_state_N_EQ)) {

			// 先判断标是否已经手动关闭
			filter.addFilter("Q_state_N_EQ", "-1");
			// 查询出招标中的标信息，再按时间排序
			// filter.addFilter("Q_state_N_EQ", "1");
			filter.addFilter("Q_proType_S_EQ", Q_proType_S_EQ);
			filter.addFilter("Q_bidEndTime_DG_LE", sdf.format(new Date()));
		} else {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// filter.addFilter("Q_state_N_EQ", Q_state_N_EQ);
			filter.addFilter("Q_proType_S_EQ", Q_proType_S_EQ);
			if (null != Q_state_N_EQ && !Q_state_N_EQ.isEmpty()) {
				if (null != flag && flag.equals("21")) {
					filter.addFilter("Q_prepareSellTime_D_GT", sdf1
							.format(new Date()));
				}
				if (null != flag && flag.equals("22")) {
					filter.addFilter("Q_prepareSellTime_D_LE", sdf1
							.format(new Date()));
					filter.addFilter("Q_publishSingeTime_D_GT", sdf1
							.format(new Date()));

				}
				if (null != flag && flag.equals("23")) {
					filter.addFilter("Q_publishSingeTime_D_LE", sdf1
							.format(new Date()));
				}
			}
		}
		String publishSingeTime_GE = this.getRequest().getParameter("publishSingeTime_GE");
		String publishSingeTime_LE = this.getRequest().getParameter("publishSingeTime_LE");
		String bidEndTime_GE = this.getRequest().getParameter("bidEndTime_GE");
		String bidEndTime_LE = this.getRequest().getParameter("bidEndTime_LE");
		if(null != publishSingeTime_GE && !"".equals(publishSingeTime_GE)){
			filter.addFilter("Q_publishSingeTime_D_GE", publishSingeTime_GE+" 00:00:00");
		}
		if(null != publishSingeTime_LE && !"".equals(publishSingeTime_LE)){
			filter.addFilter("Q_publishSingeTime_D_LE", publishSingeTime_LE+" 23:59:59");
		}
		if(null != bidEndTime_GE && !"".equals(bidEndTime_GE)){
			filter.addFilter("Q_bidEndTime_D_GE", bidEndTime_GE+" 00:00:00");
		}
		if(null != bidEndTime_LE && !"".equals(bidEndTime_LE)){
			filter.addFilter("Q_bidEndTime_D_LE", bidEndTime_LE+" 23:59:59");
		}
		filter.addSorted("createtime", "DESC");
		List<PlBidPlan> list = plBidPlanService.getAll(filter);
		List<Integer> idList=new ArrayList<Integer>();
		idList.add(1);
		idList.add(2);
		for (PlBidPlan plan : list) {
			plBidPlanService.evict(plan);
			BigDecimal money = new BigDecimal(0);
			Map<String, String> map = new HashMap<String, String>();
			map.put("bidId", plan.getBidId().toString());
//			map.put("state", "1,2");
			List<PlBidInfo> infoList = plBidInfoService.getInfo(map,idList);
			for (PlBidInfo p : infoList) {
				money = money.add(p.getUserMoney());
			}
			plan.setInvestMoney(money);
			//只有未发布和招标中的按计划招标金额计算占比，其他状态按实际投资金额计算占比
			if(!"0,1".contains(plan.getState().toString()) ){
				if(plan.getProType().equals("P_Dir")){
					BpPersionDirPro bpPersionDirPro = bpPersionDirProService.get(plan.getPDirProId());
					plan.setBidMoneyScale(money.divide(bpPersionDirPro.getBidMoney(), 8,
							BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue());
				}else if(plan.getProType().equals("B_Dir")){
					BpBusinessDirPro bpBusinessDirPro = bpBusinessDirProService.get(plan.getBdirProId());
					plan.setBidMoneyScale(money.divide(bpBusinessDirPro.getBidMoney(), 8,
							BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue());
				}else if(plan.getProType().equals("P_Or")){
					BpPersionOrPro bpPersionOrPro = bpPersionOrProService.get(plan.getPOrProId());
					plan.setBidMoneyScale(money.divide(bpPersionOrPro.getBidMoney(), 8,
							BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue());
				}else if(plan.getProType().equals("B_Or")){
					BpBusinessOrPro bpBusinessOrPro = bpBusinessOrProService.get(plan.getBorProId());
					plan.setBidMoneyScale(money.divide(bpBusinessOrPro.getBidMoney(), 8,
							BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue());
				}
			}

			if (plan.getBidMoney().compareTo(new BigDecimal(0)) != 0) {
				plan.setBidSchedule(money.divide(plan.getBidMoney(), 2,
								BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
			} else {
				plan.setBidSchedule(new BigDecimal(0));
			}

		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer(
				"publishSingeTime", "bidEndTime", "updatetime", "createtime",
				"prepareSellTime");
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
		buff.append("}");

		jsonString = buff.toString();
		return SUCCESS;
	}
	/**
	 * 显示标列表
	 */
	public String bidList() {
		int [] bidStates = null;
		PagingBean pb = new PagingBean(start, limit);
		switch (state) {
		case 11:
			bidStates = new int[] { -1,1,2,3,4,5,6,7,9,10};
			break;
		case 12:
			bidStates = new int[] { 1,2,4};
			break;
		case 13:
			bidStates = new int[] { 2,4};
			break;
	    default:
	       bidStates = new int[] { state};

		}
		String bstate=this.getRequest().getParameter("bstate");
		if(null!=bstate  && !"".equals(bstate) ){
			bidStates = new int[] { Integer.valueOf(bstate)};
		}
		List<PlBidPlan> list = new ArrayList<PlBidPlan>();
		Long count=new Long(0);
		String proType=this.getRequest().getParameter("proType");
		if(null!=proType && "P_Dir".equals(proType)){
			 list = plBidPlanService.pdBidPlanList(getRequest(), pb, bidStates);
			 count=plBidPlanService.countPdBidPlanList(getRequest(), null, bidStates);
		}
		if(null!=proType && "B_Dir".equals(proType)){
			 list = plBidPlanService.bdBidPlanList(getRequest(), pb, bidStates);
			 count=plBidPlanService.countbdBidPlanList(getRequest(), null, bidStates);
		}
		if(null!=proType && "P_Or".equals(proType)){
			 list = plBidPlanService.poBidPlanList(getRequest(), pb, bidStates);
			 count=plBidPlanService.countPoBidPlanList(getRequest(), null, bidStates);
		}
		if(null!=proType && "B_Or".equals(proType)){
			 list = plBidPlanService.boBidPlanList(getRequest(), pb, bidStates);
			 count=plBidPlanService.countboBidPlanList(getRequest(), null, bidStates);
		}
		for (PlBidPlan plan : list) {
			BigDecimal money = new BigDecimal(0);
			List<InvestPersonInfo> plist = investPersonInfoService
					.getByBidPlanId(plan.getBidId());
			for (InvestPersonInfo p : plist) {
				money = money.add(p.getInvestMoney());
			}
			if (plan.getBidMoney().compareTo(new BigDecimal(0)) != 0) {
				plan
						.setBidSchedule(money.divide(plan.getBidMoney(), 2,
								BigDecimal.ROUND_HALF_UP).multiply(
								new BigDecimal(100)));
			} else {
				plan.setBidSchedule(new BigDecimal(0));
			}

		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(count).append(
						",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer(
				"publishSingeTime", "bidEndTime", "updatetime", "createtime",
				"prepareSellTime");
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
		buff.append("}");

		jsonString = buff.toString();
		return SUCCESS;
	}
	/**
	 * 会员借款项目
	 * @return
	 */
	public String borrowPlbidPlan(){
		QueryFilter filter = new QueryFilter(getRequest());
		String userName = this.getRequest().getParameter("userName");
		String bidName = this.getRequest().getParameter("bidName");
		String bidProNumber = this.getRequest().getParameter("bidProNumber");
		if(bidName!=null&&!bidName.equals("")){
			filter.addFilter("Q_bidProName_S_EQ", bidName);
		}
		if(bidProNumber!=null&&!bidProNumber.equals("")){
			filter.addFilter("Q_bidProNumber_S_EQ", bidProNumber);
		}
		filter.addSorted("createtime", "DESC");
		List<PlBidPlan> list = plBidPlanService.getAll(filter);
		List<PlBidPlan> listSize = plBidPlanService.getAll();
		for (PlBidPlan plan : list) {
			plBidPlanService.evict(plan);
			if(plan.getBpPersionDirPro()!=null){
			}else{
				plan.setBpPersionDirPro(new BpPersionDirPro());
			}

			if(plan.getBpBusinessDirPro()!=null){
			}else{
				plan.setBpBusinessDirPro(new BpBusinessDirPro());
			}

			if(plan.getBpPersionOrPro()!=null){
			}else{
				plan.setBpPersionOrPro(new BpPersionOrPro());
			}

			if(plan.getBpBusinessOrPro()!=null){
			}else{
				plan.setBpBusinessOrPro(new BpBusinessOrPro());
			}

			if(plan.getProType().equals("P_Dir")){
				if(plan.getBpPersionDirPro().getLoanLife()!=null){
					plan.setInterestPeriod(Integer.valueOf(plan.getBpPersionDirPro().getLoanLife()));
				}
				plan.setYearInterestRate(plan.getBpPersionDirPro().getYearInterestRate());
				plan.setMoneyPlanId(plan.getBpPersionDirPro().getMoneyPlanId());
				plan.setProjId(plan.getBpPersionDirPro().getProId());
				plan.setOppositeType("person_customer");
			}
			if(plan.getProType().equals("B_Dir")){
				plan.setInterestPeriod(Integer.valueOf(plan.getBpBusinessDirPro().getLoanLife()));
				plan.setYearInterestRate(plan.getBpBusinessDirPro().getYearInterestRate());
				plan.setMoneyPlanId(plan.getBpBusinessDirPro().getMoneyPlanId());
				plan.setProjId(plan.getBpBusinessDirPro().getProId());
				plan.setOppositeType("company_customer");
			}

			if(plan.getProType().equals("P_Or")){
				if(plan.getBpPersionOrPro().getLoanLife()!=null){
					plan.setInterestPeriod(Integer.valueOf(plan.getBpPersionOrPro().getLoanLife()));
				}
				plan.setYearInterestRate(plan.getBpPersionOrPro().getYearInterestRate());
				plan.setMoneyPlanId(plan.getBpPersionOrPro().getMoneyPlanId());
				plan.setProjId(plan.getBpPersionOrPro().getProId());
				plan.setOppositeType("person_customer");
			}

			if(plan.getProType().equals("B_Or")){
				if(plan.getBpBusinessOrPro().getLoanLife()!=null){
					plan.setInterestPeriod(Integer.valueOf(plan.getBpBusinessOrPro().getLoanLife()));
				}
				plan.setYearInterestRate(plan.getBpBusinessOrPro().getYearInterestRate());
				plan.setMoneyPlanId(plan.getBpBusinessOrPro().getMoneyPlanId());
				plan.setProjId(plan.getBpBusinessOrPro().getProId());
				plan.setOppositeType("company_customer");
			}

			BpCustMember bp = plBidPlanService.getLoanMember(plan);
			if(bp!=null){
				plan.setLoginName(bp.getLoginname());
			}
		}
		List<PlBidPlan> userList = new ArrayList<PlBidPlan>();
		if(userName!=null&&!userName.equals("")){
			for (PlBidPlan plan : list) {
				String loginName = plan.getLoginName();
				if(plan.getLoginName()!=null){
					if(loginName.equals(userName)){
						userList.add(plan);
					}
				}
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(listSize.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		//serializer.exclude(new String[]{"*.handler","*.hibernateLazyInitializer"})  ;
		if(userName!=null&&!userName.equals("")){
			buff.append(json.exclude(new String[]{"*.handler","*.hibernateLazyInitializer"}).serialize(userList));
		}else{
			buff.append(json.exclude(new String[]{"*.handler","*.hibernateLazyInitializer"}).serialize(list));
		}
		buff.append("}");
		jsonString = buff.toString();
		System.out.println(jsonString);
		return SUCCESS;
	}
	/**
	 * 导出
	 */
	   public void borrowPlbidPlanToexcel(){
		   QueryFilter filter = new QueryFilter(getRequest());
			String userName = this.getRequest().getParameter("userName");
			String bidName = this.getRequest().getParameter("bidName");
			String bidProNumber = this.getRequest().getParameter("bidProNumber");
			if(bidName!=null&&!bidName.equals("")){
				filter.addFilter("Q_bidProName_S_EQ", bidName);
			}
			if(bidProNumber!=null&&!bidProNumber.equals("")){
				filter.addFilter("Q_bidProNumber_S_EQ", bidProNumber);
			}
			filter.addSorted("createtime", "DESC");
			List<PlBidPlan> list = plBidPlanService.getAll(filter);
			for (PlBidPlan plan : list) {
				plBidPlanService.evict(plan);
				if(plan.getBpPersionDirPro()!=null){
					plan.setInterestPeriod(Integer.valueOf(plan.getBpPersionDirPro().getLoanLife()));
				}else{
					plan.setBpPersionDirPro(new BpPersionDirPro());
				}
				if(plan.getBpBusinessDirPro()!=null){
					plan.setInterestPeriod(Integer.valueOf(plan.getBpBusinessDirPro().getLoanLife()));
				}else{
					plan.setBpBusinessDirPro(new BpBusinessDirPro());
				}
				BpCustMember bp = plBidPlanService.getLoanMember(plan);
				if(bp!=null){
					plan.setLoginName(bp.getLoginname());
				}
			}
			List<PlBidPlan> userList = new ArrayList<PlBidPlan>();
			if(userName!=null&&!userName.equals("")){
				for (PlBidPlan plan : list) {
					String loginName = plan.getLoginName();
					if(plan.getLoginName()!=null){
						if(loginName.equals(userName)){
							userList.add(plan);
						}
					}
				}
			}
			if(userList.size()>0){
				for(PlBidPlan pl:userList){
					if(pl.getState()==0){
						pl.setStateRemark("待发标");
					}else if(pl.getState()==1){
						pl.setStateRemark("招标中");
					}else if(pl.getState()==2){
						pl.setStateRemark("已满标");
					}else if(pl.getState()==3){
						pl.setStateRemark("已流标");
					}else if(pl.getState()==4){
						pl.setStateRemark("已过期");
					}else if(pl.getState()==5){
						pl.setStateRemark("起息办理中");
					}else if(pl.getState()==6){
						pl.setStateRemark("起息办理中");
					}else if(pl.getState()==7){
						pl.setStateRemark("还款中");
					}else if(pl.getState()==10){
						pl.setStateRemark("已完成");
					}else if(pl.getState()==-1){
						pl.setStateRemark("已关闭");
					}
					if(pl.getBpPersionDirPro().getYearInterestRate()!=null){
						pl.setYearInterestRate(pl.getBpPersionDirPro().getYearInterestRate());
					}
					if(pl.getBpBusinessDirPro().getYearInterestRate()!=null){
						pl.setYearInterestRate(pl.getBpBusinessDirPro().getYearInterestRate());
					}
				}
			}else{
				for(PlBidPlan pl:list){
					if(pl.getState()==0){
						pl.setStateRemark("待发标");
					}else if(pl.getState()==1){
						pl.setStateRemark("招标中");
					}else if(pl.getState()==2){
						pl.setStateRemark("已满标");
					}else if(pl.getState()==3){
						pl.setStateRemark("已流标");
					}else if(pl.getState()==4){
						pl.setStateRemark("已过期");
					}else if(pl.getState()==5){
						pl.setStateRemark("起息办理中");
					}else if(pl.getState()==6){
						pl.setStateRemark("起息办理中");
					}else if(pl.getState()==7){
						pl.setStateRemark("还款中");
					}else if(pl.getState()==10){
						pl.setStateRemark("已完成");
					}else if(pl.getState()==-1){
						pl.setStateRemark("已关闭");
					}
					if(pl.getBpPersionDirPro().getYearInterestRate()!=null){
						pl.setYearInterestRate(pl.getBpPersionDirPro().getYearInterestRate());
					}
					if(pl.getBpBusinessDirPro().getYearInterestRate()!=null){
						pl.setYearInterestRate(pl.getBpBusinessDirPro().getYearInterestRate());
					}
				}

			}
			String[] tableHeader = { "序号", "借款人", "招标名称","项目编号","招标金额","年化利率(%)","招标截止日期","项目状态","起息日","项目期限(月)" };
			String[] fields = {"loginName","bidProName","bidProNumber","bidMoney","yearInterestRate","bidEndTime","stateRemark","startIntentDate","interestPeriod"};
			try {
				if(userList.size()>0){
					ExportExcel.export(tableHeader, fields, userList,"会员借款信息", PlBidPlan.class);
				}else{
					ExportExcel.export(tableHeader, fields, list,"会员借款信息", PlBidPlan.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	/**
	 * 批量删除
	 *
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				plBidPlanService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 *
	 * @return
	 */
	public String get() {
		PlBidPlan plBidPlan = plBidPlanService.get(bidId);

		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer(
				"publishSingeTime", "createtime", "updateTime", "bidEndTime",
				"prepareSellTime");
		sb.append(serializer.exclude(new String[] { "class" }).serialize(
				plBidPlan));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {//Ext.getCmp('residueMoney').setValue(residueMoney-currMoney);
		//判断是否选中优惠券
		String coupon = this.getRequest().getParameter("plBidPlan.coupon");
		if(coupon!=null&&!coupon.equals("")){
			plBidPlan.setCoupon(1);
		}else{
			plBidPlan.setCoupon(0);
		}
		//判断是否是新手专享
		String novice = this.getRequest().getParameter("plBidPlan.novice");
		if(novice!=null&&!novice.equals("")){
			plBidPlan.setNovice(1);
		}else{
			plBidPlan.setNovice(0);
		}
		//判断是否是VIP专享
		String isVip = this.getRequest().getParameter("plBidPlan.isVip");
		if(isVip!=null&&!isVip.equals("")){
			plBidPlan.setIsVip(Short.valueOf("1"));
		}else{
			plBidPlan.setIsVip(Short.valueOf("0"));
		}
		if (plBidPlan.getBidId() == null) {
			plBidPlan.setCreatetime(new Date());
			plBidPlan.setUpdatetime(new Date());
			if (null == plBidPlan.getPrepareSellTime()) {
				plBidPlan.setPrepareSellTime(plBidPlan.getPublishSingeTime());
			}
			plBidPlan = plBidPlanService.save(plBidPlan);
			// 更新方案状态 是否完成
			updatePlanStat(plBidPlan);
			//建立标的信息
			//BpCustMember  bp =this.getLoanerP2PAccount(plBidPlan);
			String yearRate = "";
			String projId = "";
			long borrowCustId = 0;
			String  borrowerId = "";//借款人id
			String mobileNum = "";
			String loanLife = "";
			String loanLifeType = "";
			SlSmallloanProject smallloan  = new SlSmallloanProject();
			String orderNum =ContextUtil.createRuestNumber();//生成第三需要的流水号
			BpFundProject fundProject = new BpFundProject();
			String custType = "";
			if (plBidPlan.getProType().equals("B_Dir")) {
				BpBusinessDirPro bdirpro = bpBusinessDirProService.get(plBidPlan
						.getBdirProId());
				custType = "b_loan";
				smallloan = slSmalloanProjectService.get(bdirpro.getProId());
				borrowCustId = bdirpro.getBusinessId();
				fundProject = bpFundProjectService.get(bdirpro.getProId());
				loanLife = bdirpro.getLoanLife().toString();//借款周期
				loanLifeType = bdirpro.getPayAcctualType();//类型  (年,月,日)
				projId = String.valueOf(bdirpro.getProId());//项目id号
				yearRate = bdirpro.getYearInterestRate().setScale(2).toString();
			} else if (plBidPlan.getProType().equals("B_Or")) {
				BpBusinessOrPro borpro = bpBusinessOrProService.get(plBidPlan.getBorProId());
				custType="b_loan";
				fundProject = bpFundProjectService.get(borpro.getProId());
				BpFundProject project = bpFundProjectService.getByProjectId(borpro.getProId(),Short.valueOf("0"));
				loanLife =project.getPayintentPeriod().toString();
				smallloan = slSmalloanProjectService.get(borpro.getProId());
				loanLifeType = borpro.getPayAcctualType();
				/*//得到债权持有人信息
				BpCustMember custmember = bpCustMemberService.getMemberUserName(borpro.getReceiverP2PAccountNumber());
				if(custmember!=null){
					borrowCustId = custmember.getId();
				}else{
					borrowCustId = borpro.getBusinessId();
				}*/
				projId = String.valueOf(borpro.getProId());
				yearRate = borpro.getYearInterestRate().setScale(2).toString();
			} else if (plBidPlan.getProType().equals("P_Dir")) {
				BpPersionDirPro pdirpro = bpPersionDirProService.get(plBidPlan
						.getPDirProId());
				pdirpro.getPersionId();
				loanLife = pdirpro.getLoanLife().toString();
				loanLifeType = pdirpro.getPayAcctualType();
				smallloan = slSmalloanProjectService.get(pdirpro.getProId());
				fundProject = bpFundProjectService.get(pdirpro.getProId());
				borrowCustId = pdirpro.getPersionId();
				projId = String.valueOf(pdirpro.getProId());
				yearRate = pdirpro.getYearInterestRate().setScale(2).toString();
				custType="p_loan";
			} else if (plBidPlan.getProType().equals("P_Or")) {
				BpPersionOrPro porpro = bpPersionOrProService.get(plBidPlan
						.getPOrProId());

				fundProject = bpFundProjectService.get(porpro.getProId());
				loanLife = porpro.getLoanLife();
				loanLifeType = porpro.getPayAcctualType();
				smallloan = slSmalloanProjectService.get(porpro.getProId());
				projId = String.valueOf(porpro.getProId());
				custType="p_loan";
				/*//得到债权持有人信息
				BpCustMember custmember = bpCustMemberService.getMemberUserName(porpro.getReceiverP2PAccountNumber());
				if(custmember!=null){
					borrowCustId = custmember.getId();
				}else{
					borrowCustId = porpro.getPersionId();
				}*/
				yearRate = porpro.getYearInterestRate().setScale(2).toString();
			}
			plBidPlan.setYearInterestRate(new BigDecimal(yearRate));
			//得到借款人信息
			BpCustMember custmember = null;
			if (plBidPlan.getProType().equals("P_Or")||plBidPlan.getProType().equals("B_Or")) {
				 custmember = bpCustMemberService.getMemberUserName(plBidPlan.getReceiverP2PAccountNumber());
			}else{
				if(borrowCustId!=0){
					List<BpCustRelation> list = bpCustRelationService.getByCustIdAndCustType(borrowCustId, custType);
				    if(list.size()>0){
				    	custmember = bpCustMemberService.get(list.get(0).getP2pCustId());
				    }
				}
			}

			CommonRequst commonRequst=new CommonRequst();
			commonRequst.setRequsetNo(orderNum);
			commonRequst.setBidId(plBidPlan.getBidId().toString());//标的id
			commonRequst.setBidType(CommonRequst.HRY_BID);
			commonRequst.setBidName(plBidPlan.getBidProName());//标的名称
			commonRequst.setBidMoney(plBidPlan.getBidMoney());//标的金额
			if(custmember!=null){
				commonRequst.setThirdPayConfigId(custmember.getThirdPayFlagId());//借款人
				if(custmember.getCustomerType()!=null&&custmember.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){
					commonRequst.setAccountType(1);//标识企业
					commonRequst.setLoanAccType("02");
				}else{
					commonRequst.setAccountType(0);//标识个人
				}
			}
			commonRequst.setThirdPayConfigId(custmember.getThirdPayFlagId());  //accountNo
			commonRequst.setThirdPayConfigId0(custmember.getThirdPayFlag0()); //userName
			commonRequst.setBorrTotAmt(plBidPlan.getBidMoney().setScale(2).toString());//借款金额
			commonRequst.setYearRate(yearRate);//年利率
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			commonRequst.setBidStartDate(sdf.format(plBidPlan.getPublishSingeTime()));//投标开始时间
			commonRequst.setBidEndDate(sdf.format(plBidPlan.getBidEndTime()));//投标结束时间
			commonRequst.setRetAmt(plBidPlan.getBidMoney().setScale(2).multiply(new BigDecimal(1.5)).setScale(2).toString());//应还款总金额(本息之和)
			commonRequst.setBorrMobiPhone(custmember.getTelphone());//借款人手机
			commonRequst.setRetInterest(plBidPlan.getBidMoney().multiply(new BigDecimal(0.5)).setScale(2).toString());//plBidPlan.getBidMoney().multiply(new BigDecimal(0.2)).setScale(2).toString()
			if(loanLife!=null&&!"".equals(loanLife)&&loanLifeType!=null&&!"".equals(loanLifeType)){
				String name = loanLifeType.equals("monthPay")?"个月":loanLifeType.equals("dayPay")?"天":"个季度";
				commonRequst.setLoanPeriod(loanLife+""+name);
			}else{
				commonRequst.setLoanPeriod("自定义");
			}
			//将开始时间赋给日历实例
	        Calendar sCalendar = Calendar.getInstance();
	        sCalendar.setTime(plBidPlan.getBidEndTime());
	        //计算结束时间
	        if(loanLifeType.equals("monthPay")){
	        	sCalendar.add(Calendar.MONTH, Integer.valueOf(loanLife)+1);
	        }else if(loanLifeType.equals("dayPay")){
	        	sCalendar.add(Calendar.DATE, Integer.valueOf(loanLife)+1);
	        }else if(loanLifeType.equals("seasonPay")){//按季度计算
	        	sCalendar.add(Calendar.MONTH, (Integer.valueOf(loanLife)*3+1));
	        }

	        //返回Date类型结束时间
	        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			commonRequst.setLastRetDate(format.format(sCalendar.getTime()));
			commonRequst.setBorrPurpose(smallloan.getPurposeTypeStr()!=null?smallloan.getPurposeTypeStr():"资金流动周转");//借款用途
			commonRequst.setBorrType(custmember.getCustomerType()==0?"01":"02");//借款人类型
			commonRequst.setBorrName(custmember.getTruename());//借款人名称
			commonRequst.setRetDate(format.format(sCalendar.getTime()));//应还款日期
			commonRequst.setBorrCertId(commonRequst.getBorrType().equals("01")?custmember.getCardcode():"");
			commonRequst.setBorrBusiCode(commonRequst.getBorrType().equals("01")?"":custmember.getBusinessLicense());
			commonRequst.setBorrCertType(commonRequst.getBorrType().equals("01")?"00":"");
			commonRequst.setBussinessType(ThirdPayConstants.BT_CREATEBID);
			commonRequst.setTransferName(ThirdPayConstants.TN_CREATEBID);
			CommonResponse response = ThirdPayInterfaceUtil.thirdCommon(commonRequst);
			if (response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
				plBidPlan.setState(Integer.valueOf("0"));
				plBidPlan.setUmPayBidStatus(Short.valueOf("-1"));
				plBidPlan.setLoanTxNo(response.getLoanTxNo());
				plBidPlan.setLoanOrderDate(response.getLoanOrderDate());
				plBidPlan.setLoanOrderNo(response.getLoanOrderNo());
				plBidPlan.setReceiverName(custmember.getTruename());
				plBidPlan.setReceiverP2PAccountNumber(custmember.getLoginname());

				if (StringUtils.isNotEmpty(response.getLoanAccNo())) {
					plBidPlan.setLoanAccNo(response.getLoanAccNo());
				}
				updatePlanStat(plBidPlan);
				plBidPlan.setReceiverName(custmember.getTruename());
				plBidPlan.setReceiverP2PAccountNumber(custmember.getLoginname());
				// 更新方案状态 是否完成
				String requestNO =ContextUtil.createRuestNumber();//生成第三需要的流水号
				//更新标
				CommonRequst cr = new CommonRequst();
				cr.setRequsetNo(requestNO);
				cr.setBidId(plBidPlan.getBidId().toString());
				cr.setBidType(CommonRequst.HRY_BID);
				cr.setBidIdStatus("0");//更改标的状态为开标
				cr.setBussinessType(ThirdPayConstants.BT_UPDATEBID);
				cr.setTransferName(ThirdPayConstants.TN_UPDATEBID);
				CommonResponse resp = ThirdPayInterfaceUtil.thirdCommon(cr);
				if (resp.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
					plBidPlan.setUmPayBidStatus(Short.valueOf("0"));
					plBidPlanService.save(plBidPlan);
					setJsonString("{success:true,msg:'新增成功'}");
				}else{
					setJsonString("{success:true,msg:'增加失败，开通标的账户失败'}");
				}
			}else if (response.getResponsecode().equals(CommonResponse.RESPONSECODE_FAILD)) {
				plBidPlanService.remove(plBidPlan);
				setJsonString("{success:true,msg:'增加失败，开通标的账户失败'}");
			}
		} else {
			PlBidPlan orgPlBidPlan = plBidPlanService.get(plBidPlan.getBidId());
			try {
				if (null == plBidPlan.getPrepareSellTime()) {
					plBidPlan.setPrepareSellTime(orgPlBidPlan
							.getPublishSingeTime());
				}
						orgPlBidPlan.setPlBiddingType(null);
						BeanUtil.copyNotNullProperties(orgPlBidPlan, plBidPlan);
						orgPlBidPlan.setUpdatetime(new Date());
						plBidPlanService.merge(orgPlBidPlan);
						// 更新方案状态 是否完成
						updatePlanStat(orgPlBidPlan);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}

	/**
	 * 平台给用户在转账
	 * @return
	 */
	public String transfer() {
		String p2pName = this.getRequest().getParameter("p2pName");
		String transferMoney = this.getRequest().getParameter("transferMoney");
		BigDecimal money =BigDecimal.ZERO;
		if (StringUtils.isNotEmpty(p2pName)) {
			BpCustMember member = bpCustMemberService.getMemberUserName(p2pName);
			if (member!= null) {
				if (StringUtils.isNotEmpty(transferMoney)) {
					transferMoney = transferMoney.replaceAll(",", "");
					String orderNum =ContextUtil.createRuestNumber();//生成第三需要的流水号
					money = new BigDecimal(transferMoney);//转账金额
					CommonRequst commonRequst=new CommonRequst();
					commonRequst.setRequsetNo(orderNum);
					commonRequst.setThirdPayConfigId(member.getThirdPayFlagId());  //accountNo
					commonRequst.setThirdPayConfigId0(member.getThirdPayFlag0()); //userName
					commonRequst.setAmount(money);
					commonRequst.setBussinessType(ThirdPayConstants.BT_TRANSFER_MEMBER);
					commonRequst.setTransferName(ThirdPayConstants.TN_TRANSFER_MEMBER);
					CommonResponse resp = ThirdPayInterfaceUtil.thirdCommon(commonRequst);
					if (resp.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
//					if (true) {
						Map<String,Object> mapCustomer=new HashMap<String,Object>();
						mapCustomer.put("investPersonId",member.getId());//投资人Id（必填）
						mapCustomer.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）
						mapCustomer.put("transferType",ObAccountDealInfo.T_TRANSFER);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						mapCustomer.put("money",money);//交易金额	（必填）
						mapCustomer.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						mapCustomer.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						mapCustomer.put("recordNumber",orderNum);//交易流水号	（必填）
						mapCustomer.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						obAccountDealInfoService.operateAcountInfo(mapCustomer);

						Map<String,Object> mapAdmin=new HashMap<String,Object>();
						mapAdmin.put("investPersonId",Long.valueOf("7164"));//投资人Id（必填）
						mapAdmin.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）
						mapAdmin.put("transferType",ObAccountDealInfo.T_TRANSFERPAY);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						mapAdmin.put("money",money);//交易金额	（必填）
						mapAdmin.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						mapAdmin.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						mapAdmin.put("recordNumber",orderNum + "_1");//交易流水号	（必填）
						mapAdmin.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						obAccountDealInfoService.operateAcountInfo(mapAdmin);

						setJsonString("{success:true,msg:'转账成功'}");
					}else{
						setJsonString("{success:true,msg:'转账失败'}");
					}

				}else {
					setJsonString("{success:false,msg:'转账金额不可为空'}");
				}
			}else {
				setJsonString("{success:false,msg:'用户不存在'}");
			}
		}else {
			setJsonString("{success:false,msg:'用户名不可为空'}");
		}


		return SUCCESS;
	}


	/**
	 * 更新方案状态 是否 已经发布完成
	 *
	 * @param plBidPlan
	 */
	private void updatePlanStat(PlBidPlan plBidPlan) {
		if (plBidPlan.getProType().equals("B_Dir")) {
			BpBusinessDirPro bdirpro = bpBusinessDirProService.get(plBidPlan
					.getBdirProId());
			PlBusinessDirProKeep plBusinessDirProKeep=plBusinessDirProKeepService.getByType("B_Dir", bdirpro.getBdirProId());
			bdirpro = bpBusinessDirProService.residueMoneyMeth(bdirpro);

			plBidPlan.setReceiverName(bdirpro.getReceiverName());
			plBidPlan.setReceiverP2PAccountNumber(bdirpro.getReceiverP2PAccountNumber());
			plBidPlan.setProKeepType(plBusinessDirProKeep.getPlKeepProtype().getName());//标的类型
			plBidPlan.setKeepCreditlevelName(plBusinessDirProKeep.getPlKeepCreditlevel().getName());//信用等级
			plBidPlan.setPayMoneyTime(bdirpro.getLoanLife());//借款期限
			plBidPlan.setPayMoneyTimeType(bdirpro.getPayAcctualType());//还款时间方式
			plBidPlan.setGuarantorsId(plBusinessDirProKeep.getGuarantorsId());//担保机构id
			plBidPlan.setGuaranteeWay(plBusinessDirProKeep.getGuaranteeWay());//保障方式
			if (bdirpro.getRate() >= 100) {
				bdirpro.setSchemeStat(1);
				bpBusinessDirProService.save(bdirpro);
			}
			plBidPlan.setYearInterestRate(bdirpro.getYearInterestRate());//年化利率
		} else if (plBidPlan.getProType().equals("B_Or")) {
			BpBusinessOrPro borpro = bpBusinessOrProService.get(plBidPlan
					.getBorProId());
			PlBusinessDirProKeep plBusinessDirProKeep=plBusinessDirProKeepService.getByType("B_Or", borpro.getBorProId());
			BpFundProject bpFundProject=bpFundProjectService.get(borpro.getMoneyPlanId());
			borpro = bpBusinessOrProService.residueMoneyMeth(borpro);

			plBidPlan.setReceiverName(borpro.getReceiverName());
			plBidPlan.setReceiverP2PAccountNumber(borpro.getReceiverP2PAccountNumber());
			plBidPlan.setProKeepType(plBusinessDirProKeep.getPlKeepProtype().getName());//标的类型
			plBidPlan.setKeepCreditlevelName(plBusinessDirProKeep.getPlKeepCreditlevel().getName());//信用等级
			plBidPlan.setPayMoneyTime(bpFundProject.getPayintentPeriod());//借款期限
			plBidPlan.setPayMoneyTimeType(bpFundProject.getPayaccrualType());//还款时间方式
			plBidPlan.setGuarantorsId(plBusinessDirProKeep.getGuarantorsId());//担保机构id
			plBidPlan.setGuaranteeWay(plBusinessDirProKeep.getGuaranteeWay());//保障方式
			if (borpro.getRate() >= 100) {
				borpro.setSchemeStat(1);
				bpBusinessOrProService.save(borpro);
			}
			plBidPlan.setYearInterestRate(borpro.getYearInterestRate());//年化利率
		} else if (plBidPlan.getProType().equals("P_Dir")) {
			BpPersionDirPro pdirpro = bpPersionDirProService.get(plBidPlan
					.getPDirProId());
			PlPersionDirProKeep persionDirProKeep=plPersionDirProKeepService.getByType("P_Dir", pdirpro.getPdirProId());
			pdirpro = bpPersionDirProService.residueMoneyMeth(pdirpro);
			plBidPlan.setReceiverName(pdirpro.getReceiverName());
			plBidPlan.setReceiverP2PAccountNumber(pdirpro.getReceiverP2PAccountNumber());
			plBidPlan.setProKeepType(persionDirProKeep.getPlKeepProtype().getName());//标的类型
			plBidPlan.setKeepCreditlevelName(persionDirProKeep.getPlKeepCreditlevel().getName());//信用等级
			plBidPlan.setPayMoneyTime(pdirpro.getLoanLife());//借款期限
			plBidPlan.setPayMoneyTimeType(pdirpro.getPayAcctualType());//还款时间方式
			plBidPlan.setGuarantorsId(persionDirProKeep.getGuarantorsId());//担保机构id
			plBidPlan.setGuaranteeWay(persionDirProKeep.getGuaranteeWay());//保障方式
			if (pdirpro.getRate() >= 100) {
				pdirpro.setSchemeStat(1);
				bpPersionDirProService.save(pdirpro);
			}
			plBidPlan.setYearInterestRate(pdirpro.getYearInterestRate());//年化利率
		} else if (plBidPlan.getProType().equals("P_Or")) {
			BpPersionOrPro porpro = bpPersionOrProService.get(plBidPlan
					.getPOrProId());
			PlPersionDirProKeep persionDirProKeep=plPersionDirProKeepService.getByType("P_Or", porpro.getPorProId());
			BpFundProject bpFundProject=bpFundProjectService.get(porpro.getMoneyPlanId());
			porpro = bpPersionOrProService.residueMoneyMeth(porpro);

			plBidPlan.setReceiverName(porpro.getReceiverName());
			plBidPlan.setReceiverP2PAccountNumber(porpro.getReceiverP2PAccountNumber());
			plBidPlan.setProKeepType(persionDirProKeep.getPlKeepProtype().getName());//标的类型
			plBidPlan.setKeepCreditlevelName(persionDirProKeep.getPlKeepCreditlevel().getName());//信用等级
			plBidPlan.setPayMoneyTime(bpFundProject.getPayintentPeriod());//借款期限
			plBidPlan.setPayMoneyTimeType(bpFundProject.getPayaccrualType());//还款时间方式
			plBidPlan.setGuarantorsId(persionDirProKeep.getGuarantorsId());//担保机构id
			plBidPlan.setGuaranteeWay(persionDirProKeep.getGuaranteeWay());//保障方式
			if (porpro.getRate() >= 100) {
				porpro.setSchemeStat(1);
				bpPersionOrProService.save(porpro);
			}
			plBidPlan.setYearInterestRate(porpro.getYearInterestRate());//年化利率
		}
		//更新 标 的 借款人信息
		plBidPlanService.merge(plBidPlan);
	}

	// 修改状态
	public String bidUpdateState() {
		List<BpFundIntent> list=bpFundIntentService.getNotMoneyInfoByBidId(Long.valueOf(bidId));
		if(null!=list && list.size()>0){
			setJsonString("{success:false,msg:'该标存在未结清的款项!!!'}");
		}else{
			String state = this.getRequest().getParameter("state");
			plBidPlan = plBidPlanService.get(bidId);
			plBidPlan.setState(Integer.valueOf(state));
			plBidPlanService.merge(plBidPlan);
			plBidPlanService.flush();
			if (plBidPlan.getProType().equals("B_Dir")) {
				BpBusinessDirPro bdirpro = bpBusinessDirProService.get(plBidPlan
						.getBdirProId());
				bdirpro.setSchemeStat(0);
				bpBusinessDirProService.merge(bdirpro);
			} else if (plBidPlan.getProType().equals("B_Or")) {
				BpBusinessOrPro borpro = bpBusinessOrProService.get(plBidPlan
						.getBorProId());
				borpro.setSchemeStat(0);
				bpBusinessOrProService.merge(borpro);
			} else if (plBidPlan.getProType().equals("P_Dir")) {
				BpPersionDirPro pdirpro = bpPersionDirProService.get(plBidPlan
						.getPDirProId());
				pdirpro.setSchemeStat(0);
				bpPersionDirProService.merge(pdirpro);
			} else if (plBidPlan.getProType().equals("P_Or")) {
				BpPersionOrPro porpro = bpPersionOrProService.get(plBidPlan
						.getPOrProId());
				porpro.setSchemeStat(0);
				bpPersionOrProService.merge(porpro);
			}
			if (state != null && state.equals("-1")) {
				Set<PlBidInfo> info = plBidPlan.getPlBidInfos();
				Iterator iterator = info.iterator();
				while (iterator.hasNext()) {
					PlBidInfo temp = (PlBidInfo) iterator.next();
					if (temp != null) {
						String[] set = new String[2];
						String orderNum = Common.getRandomNum(3)
								+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmss");
						if (configMap.get("thirdPayType").toString().equals("0")) {
							if (configMap.get("thirdPayConfig").toString().equals(
									Constants.FUIOU)) {
								BpCustMember customer = bpCustMemberService
										.get(temp.getUserId());
								BpCustMember LoanMember = plBidPlanService.getLoanMember(temp);
								String amount = temp.getUserMoney().multiply(
										new BigDecimal(100)).setScale(0).toString();
								set = fuiouService.preAuthRev(orderNum, customer
										.getThirdPayFlag0(), LoanMember
										.getThirdPayFlag0(), temp
										.getPreAuthorizationNum(), "", this
										.getRequest());
							} else if (configMap.get("thirdPayConfig").toString()
									.equals(Constants.YEEPAY)) {

								BpCustMember customer = bpCustMemberService
										.get(temp.getUserId());
								/**
								 * (17)取消投标 Map<String,Object> map 第三方支付取消投标需要的map参数
								 * map.get("platformUserNo").toString() 第三方支付账号
								 * map.get("requestNo").toString()交易流水号
								 * map.get("bidrequestNo").toString()之前投标流水号
								 *
								 * @return
								 */
								Map<String, Object> mapFail = new HashMap<String, Object>();
								mapFail.put("platformUserNo", customer
										.getThirdPayFlagId());
								mapFail.put("requestNo", orderNum);
								mapFail.put("bidrequestNo", temp.getOrderNo());
								set = yeePayService.REVOCATION_TRANSFER(mapFail);
							}
						} else {
							set[0] = Constants.CODE_SUCCESS;
							set[1] = "可以更新标的状态";
						}

						if (set[0].equals(Constants.CODE_SUCCESS)) {
							temp.setState(Short.valueOf("4"));
							plBidInfoService.save(temp);
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("investPersonId", temp.getUserId());// 投资人Id
							map.put("investPersonType", ObSystemAccount.type0);// 投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
							map.put("transferType", ObAccountDealInfo.T_INVEST);// 交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
							map.put("money", temp.getUserMoney());// 交易金额
							map.put("dealDirection",
									ObAccountDealInfo.DIRECTION_PAY);// 交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
							map.put("DealInfoId", null);// 交易记录id，没有默认为null
							map.put("recordNumber", temp.getOrderNo());// 交易流水号
							map.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_2);// 资金交易状态：1等待支付，2支付成功，3
																					// 支付失败。。。(参见ObAccountDealInfo中的常量)
							String[] ret = obAccountDealInfoService
									.updateAcountInfo(map);

							try {
								BpCustMember customer = bpCustMemberService.get(temp.getUserId());
								PlBidPlan plan = plBidPlanService.get(temp.getBidId());
								//流标成功发送短信
								Map<String, String> mapSms = new HashMap<String, String>();
								mapSms.put("telephone", customer.getTelphone());
								mapSms.put("${name}", customer.getTruename());
								mapSms.put("${code}", temp.getUserMoney().toString());
								mapSms.put("${projName}",plan.getBidProName());
								mapSms.put("key", "sms_flowStandard");
								smsSendService.smsSending(mapSms);
								//smsSendUtil.sms_flowStandard(customer.getTelphone(),customer.getTruename(),temp.getUserMoney().toString(), plan.getBidProName());
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				}
				jsonString = "{success:true,msg:'已经关闭了，并返还投资人投资金额'}";
			}else{
				jsonString = "{success:true,msg:'设置成功'}";
			}
		}
		return SUCCESS;
	}

	// 针对已流标的关闭
	public String closeBidInfo() {
		try {
			String state = this.getRequest().getParameter("state");
			plBidPlan = plBidPlanService.get(bidId);
			plBidPlan.setState(Integer.valueOf(state));
			plBidPlanService.save(plBidPlan);
			if (state != null && state.equals("-1")) {
				Set<PlBidInfo> info = plBidPlan.getPlBidInfos();
				Iterator iterator = info.iterator();
				while (iterator.hasNext()) {
					PlBidInfo temp = (PlBidInfo) iterator.next();
					temp.setState(Short.valueOf("4"));
					plBidInfoService.save(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 如果是线上申请  则需要修改申请记录的状态
	 * @param plBidPlan
	 */
	public void changeApplyState(PlBidPlan plBidPlan){
		BpFinanceApplyUser apply=null;
		if("P_Dir".equals(plBidPlan.getProType())){//个人直投标
        	BpPersionDirPro pro=bpPersionDirProService.get(plBidPlan.getPDirProId());
        	if(null!=pro && null!=pro.getLoanId()){
        		apply=bpFinanceApplyUserService.get(pro.getLoanId());
        	}
        }else if("B_Dir".equals(plBidPlan.getProType())){//企业直投
        	BpBusinessDirPro pro=bpBusinessDirProService.get(plBidPlan.getBdirProId());
        	if(null!=pro && null!=pro.getLoanId()){
        		apply=bpFinanceApplyUserService.get(pro.getLoanId());
        	}
        }
		if(null!=apply){
			apply.setState("6");
			bpFinanceApplyUserService.merge(apply);
		}
	}

	/**
	 * 预览发标信息 isPublish 是否发布 true 发布 false 不发布
	 */
	public String previewPublish() {
		Boolean isPublish = Boolean.valueOf(this.getRequest().getParameter("isPublish"));
		// 先判断借款客户有没有开通第三方
		plBidPlan = plBidPlanService.get(bidId);
        //更新标的状态
		if(plBidPlan.getUmPayBidStatus()!=null&&plBidPlan.getUmPayBidStatus().equals(Short.valueOf("-1"))){
			CommonRequst cr = new CommonRequst();
			//判断借款人是企业还是个人
			String proType = plBidPlan.getProType();
			String loanerType = (plBidPlan.getProType().equals("B_Dir")||plBidPlan.getProType().equals("B_Or"))?"Enterprise":"Person";
			if(loanerType.equals("Enterprise")){
				cr.setAccountType(1);
				cr.setLoanAccType("02");
			}else{
				cr.setAccountType(0);
			}
			cr.setBidId(plBidPlan.getBidId().toString());
			cr.setBidType(CommonRequst.HRY_BID);
			cr.setBidIdStatus("0");//更改标的状态为开标
			cr.setBussinessType(ThirdPayConstants.BT_UPDATEBID);
			cr.setTransferName(ThirdPayConstants.TN_UPDATEBID);
			CommonResponse resp = ThirdPayInterfaceUtil.thirdCommon(cr);
			if (resp.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
				plBidPlan.setUmPayBidStatus(Short.valueOf("0"));
				plBidPlanService.save(plBidPlan);
				setJsonString("{success:false,msg:'更新标的成功,请再次发布'}");
			}else{
				setJsonString("{success:false,msg:'更新标的失败,请重试'}");
			}
 		}else if(plBidPlan.getUmPayBidStatus()!=null&&plBidPlan.getUmPayBidStatus().equals(Short.valueOf("0"))){
			CommonRequst cr = new CommonRequst();
			cr.setBidId(plBidPlan.getBidId().toString());
			cr.setBidType(CommonRequst.HRY_BID);
			cr.setBidIdStatus("1");//更改标的状态为投标中
			cr.setBussinessType(ThirdPayConstants.BT_UPDATEBID);
			cr.setTransferName(ThirdPayConstants.TN_UPDATEBID);
			CommonResponse resp = ThirdPayInterfaceUtil.thirdCommon(cr);
			if (resp.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
				plBidPlan.setUmPayBidStatus(Short.valueOf("1"));
				plBidPlanService.save(plBidPlan);
				Object[] check=this.checkPreviewPublishCondition(plBidPlan);
				if (check[0].equals(true)) {
					String[] ret = buildHtml(isPublish);
					if (ret[0].equals("88")) {
						setJsonString("{success:true,msg:'" + ret[1]+ "',htmlPath:'" + ret[2] + "'}");
					} else {
						setJsonString("{success:false,msg:'" + ret[1]+ "',htmlPath:'" + ret[2] + "'}");
					}
					changeApplyState(plBidPlan);
				} else {
					setJsonString("{success:false,msg:'"+check[1]+"!'}");
				}
			}
		}else{
			Object[] check=this.checkPreviewPublishCondition(plBidPlan);
			 if (check[0].equals(true)) {
				 String[] ret = buildHtml(isPublish);
				 if (ret[0].equals("88")) {
					 setJsonString("{success:true,msg:'" + ret[1]+ "',htmlPath:'" + ret[2] + "'}");
				 } else {
					 setJsonString("{success:false,msg:'" + ret[1] + "',htmlPath:'" + ret[2] + "'}");
				 }
				 changeApplyState(plBidPlan);
			 } else {
				 setJsonString("{success:false,msg:'"+check[1]+"!'}");
			 }
		}
		return SUCCESS;
	}
	/**
	 *
	 * @param plBidPlan 标的信息
	 * @return
	 */
	private Object[] checkPreviewPublishCondition(PlBidPlan plBidPlan2) {
		Object[] ret=new Object[2];
		if(checkProjectEdit(plBidPlan2)){
			BpCustMember member = plBidPlanService.getLoanMember(plBidPlan2);
			if(member!=null){
				if(configMap.get("thirdPayType").toString().equals("0")){
					if(member.getThirdPayFlagId()!=null&&!"".equals(member.getThirdPayFlagId())){
						if(member.getThirdPayStatus()!=null&&member.getThirdPayStatus().equals(BpCustMember.THIRDPAY_DEACCTIVED)){//第三方账户未激活
							ret[0]=false;
							ret[1]="招标项目的借款人或债权持有者第三支付账户未激活，请等待账户激活后发布项目";
						}else{
							plBidPlan2.setReceiverName(member.getTruename());
							plBidPlan2.setReceiverP2PAccountNumber(member.getLoginname());
							ret[0]=true;
							ret[1]="招标项目可以发布";
						}

					}else{
						ret[0]=false;
						ret[1]="招标项目的借款人或债权持有者必须开通第三支付账户";
					}
				}else{
					ret[0]=true;
					ret[1]="招标项目可以发布";
				}
			}else{
				ret[0]=false;
				ret[1]="招标项目必须有借款人或者债权持有人";
			}
		}else{
			ret[0]=false;
			ret[1]="招标项目没有维护，请先维护项目";
		}
		return ret;
	}


	/**
	 * 检查招标项目是否维护
	 * @param plBidPlan2
	 * @return
	 */
	private boolean checkProjectEdit(PlBidPlan plBidPlan2) {
		Boolean flag=false;
		if (plBidPlan2.getProType().equals("B_Dir")) {
			BpBusinessDirPro dirPro = bpBusinessDirProService.get(plBidPlan.getBdirProId());
			if (dirPro.getKeepStat() != 0) {//已经维护了项目
				flag=true;
			}
		} else if (plBidPlan2.getProType().equals("B_Or")) {
			BpBusinessOrPro orPro=bpBusinessOrProService.get(plBidPlan.getBorProId());
			if (orPro.getKeepStat() != 0) {//已经维护了项目
				flag=true;
			}
		} else if (plBidPlan2.getProType().equals("P_Dir")) {
			BpPersionDirPro dirPro = bpPersionDirProService.get(plBidPlan.getPDirProId());
			if (dirPro.getKeepStat() != 0) {//已经维护了项目
				flag=true;
			}
		} else if (plBidPlan2.getProType().equals("P_Or")) {
			BpPersionOrPro dirPro = bpPersionOrProService.get(plBidPlan.getPOrProId());
			if (dirPro.getKeepStat() != 0) {//已经维护了项目
				flag=true;
			}
		}
		return flag;
	}

	/**
	 * 启动资金匹配流程
	 * */
	public String startMatchingFundsFlow() {
		PlBidPlan plBidPlan = plBidPlanService.get(bidId);
		// plBidPlan.get
		// 验证 是否已经启动过该流程
		if (plBidPlan.getState() == PlBidPlan.STATE6||(plBidPlan.getIsStart()!=null&&plBidPlan.getIsStart()==1)) {
			jsonString = "{success:false,\"msg\":\"已经启动过不能重复启动,请刷新页面!\"}";
		} else {
			List<InvestPersonInfo> list = investPersonInfoService
					.getByBidPlanId(bidId);
			if (null == list || list.size() == 0) {
				jsonString = "{success:true,msg:'该标的投资金额为0，不能启动办理流程!'}";
				return SUCCESS;
			}
			String subType=this.getRequest().getParameter("subType");
			String str = slSmallloanProjectService.startMatchingFunds(bidId,((plBidPlan.getProType().equals("B_Dir") || plBidPlan.getProType().equals("P_Dir")) ? "matchingfundsflow": "transferMatchingfundsflow"),subType);
			jsonString = "{success:true," + str + "}";
			/******
			 * 通过配置----决定流程所走的vm路径 *
			 ****/
			/*
			 * if(projectId!=null){ SlSmallloanProject project =
			 * slSmallloanProjectService.get(projectId);
			 * if(project.getFlowType().equals("drySmallloanFlow")){ String str
			 * = slSmallloanProjectService.completeMatchingTask(projectId,
			 * bidId)+""; jsonString="{success:true,"+str+"}"; }else {
			 */
			// if(plBidPlan.getProType().equals("B_Dir")||plBidPlan.getProType().equals("P_Dir")){

			/*
			 * }else if(plBidPlan.getProType().equals("B_Or")){ str =
			 * slSmallloanProjectService.startMatchingFunds(projectId,
			 * bidId,"BondEnterpriseMatchFlow"); }else
			 * if(plBidPlan.getProType().equals("P_Or")){ str =
			 * slSmallloanProjectService.startMatchingFunds(projectId,
			 * bidId,"BondMatchFlow"); }
			 */
			// String str =
			// slSmallloanProjectService.startMatchingFunds(projectId,
			// bidId,"matchingfundsflow");

			/*
			 * }else if(project.getFlowType().equals("BondTransferFlow")){
			 * String str =
			 * slSmallloanProjectService.startMatchingFunds(projectId,
			 * bidId,"matchingfundsflow"); jsonString="{success:true,"+str+"}";
			 * }else if(project.getFlowType().equals("zftCreditFlow")){ String
			 * str = slSmallloanProjectService.startMatchingFunds(projectId,
			 * bidId,"BondMatchFlow"); jsonString="{success:true,"+str+"}"; } }
			 */

			// list();//重新刷新数据
		}
		return SUCCESS;
	}

	/**
	 * 预览发布 生成 html 页面
	 *
	 * @param isPublish
	 *            是否发布 true 发布 false 不发布
	 * @return
	 */
	public String[] buildHtml(boolean isPublish) {
		String[] ret = new String[3];
		Map<String, Object> data = buildHtml2WebService.getCommonData();
		String htmlFilePath = "";
		HtmlConfig htmlConfig = null;
		boolean isFirst = false;// 是否已经存在页面
		try {
			plBidPlan = plBidPlanService.get(bidId);
			// 获取动态信息 如 投标进度 投标人数 投标剩余金额
			plBidPlan = plBidPlanService.bidDynamic(plBidPlan);
			PlPersionDirProKeep keep=null;
			PlBusinessDirProKeep bkeep=null;
			if (plBidPlan.getProType().equals("B_Dir")) {
				// htmlConfig =
				// resultWebPmsService.findHtmlCon(HtmlConfig.BUSINESSDIRBID_CONTENT);
				bkeep=plBusinessDirProKeepService.getByType(plBidPlan.getProType(),plBidPlan.getBdirProId());
				plBidPlan.setLoanTotalMoney(new BigDecimal(plBidPlanService
						.findLoanTotalMoneyBySQL(plBidPlan
								.getBpBusinessDirPro().getProId().toString())));
				plBidPlan.setOrgMoney(new BigDecimal(plBidPlanService
						.findOrgMoneyBySQL(plBidPlan.getBpBusinessDirPro()
								.getProId().toString(), "1")));

			} else if (plBidPlan.getProType().equals("B_Or")) {
				// htmlConfig =
				// resultWebPmsService.findHtmlCon(HtmlConfig.BUSINESSORBID_CONTENT);
				bkeep=plBusinessDirProKeepService.getByType(plBidPlan.getProType(),plBidPlan.getBorProId());
				plBidPlan.setLoanTotalMoney(new BigDecimal(plBidPlanService
						.findLoanTotalMoneyBySQL(plBidPlan.getBpBusinessOrPro()
								.getProId().toString())));
				plBidPlan.setOrgMoney(new BigDecimal(plBidPlanService
						.findOrgMoneyBySQL(plBidPlan.getBpBusinessOrPro()
								.getProId().toString(), "1")));
			} else if (plBidPlan.getProType().equals("P_Dir")) {
				// htmlConfig =
				// resultWebPmsService.findHtmlCon(HtmlConfig.PERSIONDIRBID_CONTENT);
				keep=plPersionDirProKeepService.getByType(plBidPlan.getProType(),plBidPlan.getPDirProId());
				plBidPlan.setLoanTotalMoney(new BigDecimal(plBidPlanService
						.findLoanTotalMoneyBySQL(plBidPlan.getBpPersionDirPro()
								.getProId().toString())));
				plBidPlan.setOrgMoney(new BigDecimal(plBidPlanService
						.findOrgMoneyBySQL(plBidPlan.getBpPersionDirPro()
								.getProId().toString(), "1")));
			} else if (plBidPlan.getProType().equals("P_Or")) {
				// htmlConfig =
				// resultWebPmsService.findHtmlCon(HtmlConfig.PERSIONORBID_CONTENT);
				keep=plPersionDirProKeepService.getByType(plBidPlan.getProType(),plBidPlan.getPOrProId());
				plBidPlan.setLoanTotalMoney(new BigDecimal(plBidPlanService
						.findLoanTotalMoneyBySQL(plBidPlan.getBpPersionOrPro()
								.getProId().toString())));
				plBidPlan.setOrgMoney(new BigDecimal(plBidPlanService
						.findOrgMoneyBySQL(plBidPlan.getBpPersionOrPro()
								.getProId().toString(), "1")));

			}
			if(null!=keep){
				PlKeepProtype protype=plKeepProtypeService.get(keep.getTypeId());
				if(null!=protype){
					plBidPlan.setProKeepType(protype.getName());
				}
			}
			findPlanProjInfo(data, plBidPlan);
			if (plBidPlan.getHtmlPath() != null
					&& !plBidPlan.getHtmlPath().equals("")) {
				htmlFilePath = plBidPlan.getHtmlPath();
				isFirst = true;
			} else {
				// htmlFilePath = htmlConfig.getHtmlFilePath();
			}
			String templateFilePath = "";
			data.put("htmlFilePath", htmlFilePath);
			data.put("templateFilePath", templateFilePath);
			data.put("plan", JsonUtils.getJson(plBidPlan, JsonUtils.TYPE_OBJ));
			findPlanProjInfo(data, plBidPlan);
			// buildHtml2WebService.buildHtml(Constants.BUILDHTML_FORMAT_JSON,AppUtil.getWebServiceUrlRs(),
			// "htmlService","signSchemeContentBuildHtml", data);
			if (!isFirst) {
				plBidPlan.setHtmlPath(htmlFilePath);
				plBidPlanService.save(plBidPlan);
			}
			// 是否发布
			if (isPublish) {
				BpCustMember bploaner = plBidPlanService.getLoanMember(plBidPlan);
				plBidPlan.setState(PUBLISHSTAT);
				// 发布时间
				Date d = new Date();
				plBidPlanService.save(plBidPlan);
				ret[0] = "88";
				ret[1] = "生成成功";
				ret[2] = htmlFilePath;

			} else {
				ret[0] = "00";
				ret[1] = "尚未发布";
				ret[2] = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			ret[0] = "00";
			ret[1] = "生成失败,可能未维护项目";
			ret[2] = "";
		}
		return ret;
	}

	private Map<String, Object> findPlanProjInfo(Map<String, Object> data,
			PlBidPlan plBidPlan) {
		Object planPro = null;
		Object planKeep = null;
		QueryFilter filter = new QueryFilter(this.getRequest());
		if (plBidPlan.getProType().equals("B_Dir")) {
			planPro = bpBusinessDirProService.get(plBidPlan.getBdirProId());
			filter.addFilter("Q_bpBusinessDirPro.bdirProId_L_EQ", plBidPlan
					.getBdirProId().toString());
			planKeep = plBusinessDirProKeepService.getAll(filter).get(0);

		} else if (plBidPlan.getProType().equals("B_Or")) {
			planPro = bpBusinessOrProService.get(plBidPlan.getBorProId());

			filter.addFilter("Q_bpBusinessOrPro.borProId_L_EQ", plBidPlan
					.getBorProId().toString());
			planKeep = plBusinessDirProKeepService.getAll(filter).get(0);

		} else if (plBidPlan.getProType().equals("P_Dir")) {
			planPro = bpPersionDirProService.get(plBidPlan.getPDirProId());

			filter.addFilter("Q_bpPersionDirPro.pdirProId_L_EQ", plBidPlan
					.getPDirProId().toString());
			planKeep = plPersionDirProKeepService.getAll(filter).get(0);

		} else if (plBidPlan.getProType().equals("P_Or")) {
			planPro = bpPersionOrProService.get(plBidPlan.getPOrProId());
			BpPersionOrPro pop = (BpPersionOrPro) planPro;
			if (pop.getLoanStarTime() != null && pop.getLoanEndTime() != null) {
				String month = loanLife(pop.getLoanStarTime(), pop
						.getLoanEndTime());
				pop.setLoanLife(month);
			}
			filter.addFilter("Q_bpPersionOrPro.porProId_L_EQ", plBidPlan
					.getPOrProId().toString());
			planKeep = plPersionDirProKeepService.getAll(filter).get(0);

		}
		data.put("planPro", JsonUtils.getJson(planPro, JsonUtils.TYPE_OBJ));
		data.put("planKeep", JsonUtils.getJson(planKeep, JsonUtils.TYPE_OBJ));
		return data;
	}

	public String loanLife(Date startDate, Date endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<Integer, Integer> map = DateUtil.getMonthAndDaysBetweenDate(sdf
				.format(startDate), sdf.format(endDate));
		if (map != null) {
			Integer month = map.get(1);
			return month + "";
		}
		return null;
	}
	/**
	 * 取消投标
	 * @return
	 */
	@SuppressWarnings(value={"unused","unchecked","rawtypes"})
	public String bidFailed(){
		PlBidPlan plBidPlan = plBidPlanService.get(bidId);
		//借款人
		String loanerName = plBidPlan.getReceiverP2PAccountNumber();
		BpCustMember loanerMember = bpCustMemberService.getMemberUserName(loanerName);
		String cardNo=ContextUtil.createRuestNumber();//流水号
		String[] set = new String[2];
		if (configMap.get("thirdPayType").toString().equals("0")) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			CommonRequst cq=new CommonRequst();
			cq.setThirdPayConfigId(loanerMember.getThirdPayFlagId());  //accountNo
			cq.setThirdPayConfigId0(loanerMember.getThirdPayFlag0()); //userName
			cq.setQueryRequsetNo(plBidPlan.getLoanOrderNo());//之前投标的请求流水号
			cq.setLoanNoList(plBidPlan.getLoanTxNo());//投标时第三方返回的流水号
			cq.setOrderDate(plBidPlan.getLoanOrderDate());
			cq.setRequsetNo(cardNo);//请求流水号
			cq.setBussinessType(ThirdPayConstants.BT_CANCELBID);//业务类型代码
			cq.setTransferName(ThirdPayConstants.TN_CANCELBID);//业务用途

			//请求三方处理业务
			CommonResponse cr=ThirdPayInterfaceUtil.thirdCommon(cq);
			if(cr.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
				//调用成功以后处理业务
				Set<PlBidInfo> info = plBidPlan.getPlBidInfos();
				System.out.println(info.size());
				Iterator iterator = info.iterator();
				while (iterator.hasNext()) {
					PlBidInfo temp = (PlBidInfo) iterator.next();
					if (temp != null&& temp.getState() != null&& (temp.getState().equals(Short.valueOf("1")) || temp.getState().equals(Short.valueOf("2")))) {
						BpCustMember customer = bpCustMemberService.get(temp.getUserId());
						//判断是否使用了优惠券
						if(temp.getCouponId()!=null&&!temp.getCouponId().equals("")){
							BpCoupons coupon = bpCouponsService.get(temp.getCouponId());
							coupon.setCouponMoney(new BigDecimal(0));
							coupon.setCouponStatus(Short.valueOf("5"));
							bpCouponsService.save(coupon);
						}

						temp.setState(Short.valueOf("3"));
						temp.setCouponId(null);
						plBidInfoService.save(temp);
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("investPersonId", temp.getUserId());// 投资人Id
						map.put("investPersonType", ObSystemAccount.type0);// 投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
						map.put("transferType", ObAccountDealInfo.T_INVEST);// 交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
						map.put("money", temp.getUserMoney());// 交易金额
						map.put("dealDirection", ObAccountDealInfo.DIRECTION_PAY);// 交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
						map.put("DealInfoId", null);// 交易记录id，没有默认为null
						map.put("recordNumber", temp.getOrderNo());// 交易流水号
						map.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_8);// 资金交易状态：1等待支付，2支付成功，3
						String[] ret = obAccountDealInfoService
								.updateAcountInfo(map);
						try {
							PlBidPlan plan = plBidPlanService.get(temp.getBidId());
							//流标成功发送短信
							Map<String, String> mapSms = new HashMap<String, String>();
							mapSms.put("telephone", customer.getTelphone());
							mapSms.put("${name}", customer.getLoginname());
							mapSms.put("${code}", temp.getUserMoney().toString());
							mapSms.put("${projName}",plan.getBidProName());
							mapSms.put("key", "sms_flowStandard");
							smsSendService.smsSending(mapSms);
							//smsSendUtil.sms_flowStandard(customer.getTelphone(),customer.getLoginname(),temp.getUserMoney().toString(), plan.getBidProName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				plBidPlan.setState(PlBidPlan.STATE3);
				plBidPlanService.save(plBidPlan);
				if (plBidPlan.getProType().equals("B_Dir")) {
					BpBusinessDirPro bdirpro = bpBusinessDirProService.get(plBidPlan
							.getBdirProId());
					bdirpro.setSchemeStat(0);
					bpBusinessDirProService.merge(bdirpro);
				} else if (plBidPlan.getProType().equals("B_Or")) {
					BpBusinessOrPro borpro = bpBusinessOrProService.get(plBidPlan
							.getBorProId());
					borpro.setSchemeStat(0);
					bpBusinessOrProService.merge(borpro);
				} else if (plBidPlan.getProType().equals("P_Dir")) {
					BpPersionDirPro pdirpro = bpPersionDirProService.get(plBidPlan
							.getPDirProId());
					pdirpro.setSchemeStat(0);
					bpPersionDirProService.merge(pdirpro);
				} else if (plBidPlan.getProType().equals("P_Or")) {
					BpPersionOrPro porpro = bpPersionOrProService.get(plBidPlan
							.getPOrProId());
					porpro.setSchemeStat(0);
					bpPersionOrProService.merge(porpro);
				}
				jsonString = "{success:true,msg:'已经流标了，并返还投资人投资金额'}";

				set[0]= Constants.CODE_SUCCESS;
				set[1]="流标成功,第三方冻结资金已经归还";
			}else if(cr.getResponsecode().equals(CommonResponse.RESPONSECODE_APPLAY)) {
				set[1]="流标申请成功，正在处理。。。";
			}else {
				set[0]=Constants.CODE_FAILED;
				set[1]= cr.getResponseMsg();
			}
		}
		return SUCCESS;
	}


	/**
	 * 取消投标
	 * 原方法保留
	 * @return
	 */
	public String bidFailedOld(){
		PlBidPlan plBidPlan = plBidPlanService.get(bidId);
		String loanerName = plBidPlan.getReceiverP2PAccountNumber();
		BpCustMember loanerMember = bpCustMemberService.getMemberUserName(loanerName);
		Set<PlBidInfo> info = plBidPlan.getPlBidInfos();
		System.out.println(info.size());
		Iterator iterator = info.iterator();
		String[] set = new String[2];
		while (iterator.hasNext()) {
			PlBidInfo temp = (PlBidInfo) iterator.next();
			if (temp != null&& temp.getState() != null&& (temp.getState().equals(Short.valueOf("1")) || temp.getState().equals(Short.valueOf("2")))) {
				String cardNo=ContextUtil.createRuestNumber();//流水号
				String unFreezeNum = ContextUtil.createRuestNumber();
				if (configMap.get("thirdPayType").toString().equals("0")) {
					BpCustMember customer = bpCustMemberService.get(temp.getUserId());
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					CommonRequst cq=new CommonRequst();
					cq.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方标识 出账账户
					cq.setQueryRequsetNo(temp.getOrderNo());//之前投标的请求流水号
					cq.setLoanNoList(temp.getPreAuthorizationNum());//投标时第三方返回的流水号
					cq.setRequsetNo(cardNo);//请求流水号
	    			cq.setContractNo(temp.getPreAuthorizationNum()!=null?temp.getPreAuthorizationNum():"");
					cq.setBussinessType(ThirdPayConstants.BT_CANCELBID);//业务类型代码
	    			cq.setTransferName(ThirdPayConstants.TN_CANCELBID);//业务用途
	    			cq.setTransactionTime(date);
	    			cq.setOrderDate(sdf.format(date));
	    			cq.setOrdId(temp.getOrderNo());//汇付的投标撤销接口(投标的流水号)
	    			cq.setLoaner_thirdPayflagId(loanerMember.getThirdPayFlagId());//入账账户  富友
	    			cq.setBidId(bidId.toString());
	    			cq.setMerPriv("");//商户私有域
	    			cq.setTrxId(temp.getTrxId());
	    			cq.setFreezeOrdId(temp.getFreezeTrxId());
	    			cq.setAmount(temp.getUserMoney().setScale(2));
	    			if(customer.getCustomerType()!=null&&!"".equals(customer.getCustomerType())&&customer.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){
	    				cq.setAccountType(1);//企业
	    			}else{
	    				cq.setAccountType(0);//个人
	    			}
	    			CommonResponse cr=ThirdPayInterfaceUtil.thirdCommon(cq);
					if(cr.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
						set[0]= Constants.CODE_SUCCESS;
						set[1]="流标成功,第三方冻结资金已经归还";
					}else{
						set[0]=Constants.CODE_FAILED;
						set[1]= cr.getResponseMsg();
					}
					if (set[0].equals(Constants.CODE_SUCCESS)) {
								//判断是否使用了优惠券
								if(temp.getCouponId()!=null&&!temp.getCouponId().equals("")){
									BpCoupons coupon = bpCouponsService.get(temp.getCouponId());
									coupon.setCouponMoney(new BigDecimal(0));
									coupon.setCouponStatus(Short.valueOf("5"));
									bpCouponsService.save(coupon);
								}

								temp.setState(Short.valueOf("3"));
								temp.setCouponId(null);
								plBidInfoService.save(temp);
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("investPersonId", temp.getUserId());// 投资人Id
								map.put("investPersonType", ObSystemAccount.type0);// 投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
								map.put("transferType", ObAccountDealInfo.T_INVEST);// 交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
								map.put("money", temp.getUserMoney());// 交易金额
								map.put("dealDirection", ObAccountDealInfo.DIRECTION_PAY);// 交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
								map.put("DealInfoId", null);// 交易记录id，没有默认为null
								map.put("recordNumber", temp.getOrderNo());// 交易流水号
								map.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_8);// 资金交易状态：1等待支付，2支付成功，3
								String[] ret = obAccountDealInfoService
										.updateAcountInfo(map);
								try {
									PlBidPlan plan = plBidPlanService.get(temp.getBidId());
									//流标成功发送短信
									Map<String, String> mapSms = new HashMap<String, String>();
									mapSms.put("telephone", customer.getTelphone());
									mapSms.put("${name}", customer.getLoginname());
									mapSms.put("${code}", temp.getUserMoney().toString());
									mapSms.put("${projName}",plan.getBidProName());
									mapSms.put("key", "sms_flowStandard");
									smsSendService.smsSending(mapSms);
									//smsSendUtil.sms_flowStandard(customer.getTelphone(),customer.getLoginname(),temp.getUserMoney().toString(), plan.getBidProName());
								} catch (Exception e) {
									e.printStackTrace();
								}

								plBidPlan.setState(PlBidPlan.STATE3);
								plBidPlanService.save(plBidPlan);
								if (plBidPlan.getProType().equals("B_Dir")) {
									BpBusinessDirPro bdirpro = bpBusinessDirProService.get(plBidPlan
											.getBdirProId());
									bdirpro.setSchemeStat(0);
									bpBusinessDirProService.merge(bdirpro);
								} else if (plBidPlan.getProType().equals("B_Or")) {
									BpBusinessOrPro borpro = bpBusinessOrProService.get(plBidPlan
											.getBorProId());
									borpro.setSchemeStat(0);
									bpBusinessOrProService.merge(borpro);
								} else if (plBidPlan.getProType().equals("P_Dir")) {
									BpPersionDirPro pdirpro = bpPersionDirProService.get(plBidPlan
											.getPDirProId());
									pdirpro.setSchemeStat(0);
									bpPersionDirProService.merge(pdirpro);
								} else if (plBidPlan.getProType().equals("P_Or")) {
									BpPersionOrPro porpro = bpPersionOrProService.get(plBidPlan
											.getPOrProId());
									porpro.setSchemeStat(0);
									bpPersionOrProService.merge(porpro);
								}
								jsonString = "{success:true,msg:'已经流标了，并返还投资人投资金额'}";
					}else{
						jsonString = "{success:true,msg:'流标请求失败'}";
					}
				}
			}
		}
		//针对于没有投资的标直接返回流标成功
		if(info.size()==0){
			plBidPlan.setState(PlBidPlan.STATE3);//本地改变状态为已流标
			plBidPlanService.merge(plBidPlan);
			if (plBidPlan.getProType().equals("B_Dir")) {
				BpBusinessDirPro bdirpro = bpBusinessDirProService.get(plBidPlan
						.getBdirProId());
				bdirpro.setSchemeStat(0);
				bpBusinessDirProService.merge(bdirpro);
			} else if (plBidPlan.getProType().equals("B_Or")) {
				BpBusinessOrPro borpro = bpBusinessOrProService.get(plBidPlan
						.getBorProId());
				borpro.setSchemeStat(0);
				bpBusinessOrProService.merge(borpro);
			} else if (plBidPlan.getProType().equals("P_Dir")) {
				BpPersionDirPro pdirpro = bpPersionDirProService.get(plBidPlan
						.getPDirProId());
				pdirpro.setSchemeStat(0);
				bpPersionDirProService.merge(pdirpro);
			} else if (plBidPlan.getProType().equals("P_Or")) {
				BpPersionOrPro porpro = bpPersionOrProService.get(plBidPlan
						.getPOrProId());
				porpro.setSchemeStat(0);
				bpPersionOrProService.merge(porpro);
			}
			jsonString = "{success:true,msg:'已经流标了，并返还投资人投资金额'}";
		}
		return SUCCESS;
	}

	/**
	 * 自动投标
	 *
	 * @return
	 */
	public String bidAuto() {
		PlBidPlan plBidPlan = plBidPlanService.get(bidId);
		plBidPlan = plBidPlanService.bidDynamic(plBidPlan);
		plBidPlan = plBidPlanService.getPlan(plBidPlan);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 获取散标的借款人以及原始债权人
		BpCustMember loanMem = plBidPlanService.getLoanMember(plBidPlan);
		int bidNum = 0;// 实际有多少人进行了投标
		String err = "";
		CommonRequst common = new CommonRequst();
		CommonResponse reponse = new CommonResponse();
		try {
			if (plBidPlan.getProgress() >= PlBidAuto.MAXAUTO) {
				jsonString = "{success:true,msg:'该标的投资进度已经达到"
						+ PlBidAuto.MAXAUTO + "%不能进行自动投标'}";
			} else {
				List<PlBidAuto> list = autoList(plBidPlan);
				if (list.size() < 0 || list.isEmpty()) {
					jsonString = "{success:true,msg:'未找到合适的投资者'}";
				} else if (loanMem.getThirdPayFlagId() == null|| loanMem.getThirdPayFlagId().equals("")) {
					jsonString = "{success:true,msg:'借款人未开通第三方支付'}";
				} else {
					for (PlBidAuto auto : list) {
						//判断是否超标
						//投标金额合计
						BigDecimal investTotalMoney = investPersonInfoService.getInvestTotalMoney(plBidPlan.getBidId());
						if(investTotalMoney==null){
							investTotalMoney = BigDecimal.ZERO;
						}
						if (investTotalMoney.add(auto.getBidMoney()).compareTo(plBidPlan.getBidMoney())>0) {
							//err = "该标的投资进度已经达到" + PlBidAuto.MAXAUTO+ "%不能进行自动投标 ";
						} else {
							BpCustMember mem = bpCustMemberService.get(auto.getUserID());
							// 投资人的第三方账户可用余额
							BigDecimal totalMoney = new BigDecimal(0);
							BigDecimal money[] = new BigDecimal[7];
							if (AppUtil.getSysConfig().get("thirdPayType").equals("0")) {
								totalMoney = userMoney(mem);
							} else {
								money = obSystemAccountService.sumTypeTotalMoney(mem.getId(),"0");
								totalMoney = money[3];
							}
							// 是否可投 条件：账户保留金额+每次投标的金额>=totalMoney 进行投标
							BigDecimal isBidMoney = auto.getKeepMoney().add(auto.getBidMoney());
							String orderNum1 = ContextUtil.createRuestNumber();
							if (totalMoney.compareTo(isBidMoney)>=0) {
								String orderNum = ContextUtil.createRuestNumber();
								// 调用投标接口
								common.setThirdPayConfigId(mem.getThirdPayFlagId());
								common.setThirdPayConfigId0(mem.getThirdPayFlag0());
								common.setRequsetNo(orderNum);//流水号
								common.setAmount(auto.getBidMoney().setScale(2));//投资金额
								common.setProId(plBidPlan.getLoanTxNo());
								common.setBussinessType(ThirdPayConstants.BT_AUTOBID);//业务类型
								common.setTransferName(ThirdPayConstants.TN_AUTOBID);

								common.setBidId(bidId.toString());//标id
								common.setPlanMoney(plBidPlan.getBidMoney());//标金额
								common.setBidName(plBidPlan.getBidProName());//招标名
								common.setMaxTenderRate("0.10");
								common.setOrderDate(sdf.format(new Date()));
								common.setIsFreeze("Y");
								common.setFreezeOrdId(orderNum1);
//								common.setBorrowerDetails("[{\"BorrowerCustId\":\""+loanMem.getThirdPayFlagId().toString()+"\",\"BorrowerAmt\":\""+auto.getBidMoney().setScale(2)+"\",\"BorrowerRate\":\""+0.99+"\",\"ProId\":\""+plBidPlan.getBidRemark()+"\"}]");
								common.setBidType(CommonRequst.HRY_BID);
								if(plBidPlan.getProType().equals("P_Dir")||plBidPlan.getProType().equals("P_Or")){
										common.setAccountType(0);
									}
									else{
										common.setAccountType(1);
									}
								reponse=ThirdPayInterfaceUtil.thirdCommon(common);
								if (reponse!= null&& reponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
									String businessType = "SmallLoan";
									BigDecimal investPercent = auto.getBidMoney().divide(plBidPlan.getBidMoney(), 2);
									int flag=plBidPlanService.updateStatByMoney(bidId,auto.getBidMoney());
									if(flag==1){//1表示已满标
										//调用流标接口
										String requsetNo = ContextUtil.createRuestNumber();
										BpCustMember customer = bpCustMemberService.get(mem.getId());
										Date date = new Date();
										CommonRequst cq=new CommonRequst();
										cq.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方标识
										cq.setQueryRequsetNo(orderNum);//之前投标的请求流水号
										cq.setLoanNoList(reponse.getLoanNo());//投标时第三方返回的流水号
										cq.setRequsetNo(requsetNo);//请求流水号
						    			cq.setBussinessType(ThirdPayConstants.BT_CANCELBID);//业务类型代码
						    			cq.setTransferName(ThirdPayConstants.TN_CANCELBID);//业务用途
						    			cq.setTransactionTime(date);
						    			cq.setBidId(bidId.toString());
						    			cq.setAmount(auto.getBidMoney());
						    			if(customer.getCustomerType()!=null&&!"".equals(customer.getCustomerType())&&customer.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){
						    				cq.setAccountType(1);//企业
						    			}else{
						    				cq.setAccountType(0);//个人
						    			}
						    			CommonResponse cr=ThirdPayInterfaceUtil.thirdCommon(cq);
										if(cr.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
											plBidPlan.setState(PlBidPlan.STATE2);
											plBidPlanService.save(plBidPlan);

										}
									}else{
										// 保存数据到 投资成功表
										investPersonInfoService.saveInvestPerson(
												mem.getId(), mem.getLoginname(),
												auto.getBidMoney(), investPercent,
												reponse.getResponseMsg().toString(), businessType,
												plBidPlan.getProjId(), orderNum,
												bidId, plBidPlan.getMoneyPlanId(),
												Short.valueOf("1"));
										if(reponse.getContract_no()!=null&&!"".equals(reponse.getContract_no())){
											saveBidInfo(plBidPlan, mem, Short.valueOf("1"), orderNum, auto.getBidMoney(),reponse.getLoanNo(),reponse.getContract_no());
										}else{
											// 保存投标信息到队列表
											saveBidInfo(plBidPlan, mem, Short.valueOf("1"), orderNum, auto.getBidMoney(),reponse.getLoanNo(),"");
										}
										// 冻结金额
										Map<String, Object> map = new HashMap<String, Object>();
										map.put("investPersonId", mem.getId());// 投资人Id（必填）
										map.put("investPersonType",ObSystemAccount.type0);// 投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
										map.put("transferType",ObAccountDealInfo.T_INVEST);// 交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
										map.put("money", auto.getBidMoney());// 交易金额// （必填）
										map.put("dealDirection",ObAccountDealInfo.DIRECTION_PAY);// 交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
										map.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);// 交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
										map.put("recordNumber", orderNum);// 交易流水号// （必填）
										map.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_7);// 资金交易状态：1等待支付，2支付成功，3// 支付失败。。。(参见ObAccountDealInfo中的常量)// （必填）
										String[] ret = obAccountDealInfoService.operateAcountInfo(map);
										bidNum++;
									}
									if(investTotalMoney.add(auto.getBidMoney()).compareTo(plBidPlan.getBidMoney())==0){
										plBidPlan.setState(PlBidPlan.STATE2);
										plBidPlan.setFullTime(new Date());// 设置齐标日期
										plBidPlanService.merge(plBidPlan);
									}
								} else {
									// 保存投标信息到队列表
									saveBidInfo(plBidPlan, mem, Short.valueOf("4"), orderNum, auto.getBidMoney(),null,"");
									err = reponse.getResponseMsg().toString() + ",";
								}

							}
						}
					}
					jsonString = "{success:true,msg:'自动投标完成，符合参数条件人数" + list.size()
							+ "人,标的剩余部分实际参与" + bidNum + "人。" + err + "'}";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonString = "{success:true,msg:'请求参数错误'}";
		}
		return SUCCESS;
	}

	/**
	 * 保存投标信息到队列表
	 *
	 * @param plan
	 * @param mem
	 * @param state
	 * @param orderNo
	 * @param userMoney
	 */
	private void saveBidInfo(PlBidPlan plan, BpCustMember mem, Short state,
			String orderNo, BigDecimal userMoney,String loanNo,String contractNo) {
		PlBidInfo bidInfo = new PlBidInfo();
		bidInfo.setBidName(plan.getBidProName());
		bidInfo.setBidtime(new Date());
		bidInfo.setOrderNo(orderNo);
		bidInfo.setBidId(plan.getBidId());
		bidInfo.setState(state);
		bidInfo.setUserId(mem.getId());
		bidInfo.setUserName(mem.getLoginname());
		if(contractNo!=null&&!"".equals(contractNo)){
			bidInfo.setPreAuthorizationNum(contractNo);
		}
		bidInfo.setUserMoney(userMoney);
		if(loanNo!=null&&!loanNo.equals("")){
			bidInfo.setPreAuthorizationNum(loanNo);
		}
		plBidInfoService.save(bidInfo);
	}

	/**
	 * 获取第三方帐户余额
	 *
	 * @param mem
	 * @return
	 */
	private BigDecimal userMoney(BpCustMember mem) {
		BigDecimal totalMoney = new BigDecimal(0);
		// 调用投标接口
		if (mem != null && mem.getThirdPayFlagId() != null) {
			/**
			 * (11)注册用户查询接口 Map<String,Object> map 第三方支付注册用户查询需要的map参数
			 * map.get("platformUserNo").toString() 第三方支付账号
			 */
			CommonRequst common = new CommonRequst();
			CommonResponse response = new CommonResponse();
			String orderNum =ContextUtil.createRuestNumber();
			common.setRequsetNo(orderNum);//请求流水号
			common.setThirdPayConfigId(mem.getThirdPayFlagId());
			common.setBussinessType(ThirdPayConstants.BT_QUERYUSER);
			common.setTransferName(ThirdPayConstants.TN_QUERYUSER);
			response=ThirdPayInterfaceUtil.thirdCommon(common);
			if (response!= null &&response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
				totalMoney = response.getAvailableAmount();
			}
		}
		return totalMoney;
	}

	/**
	 * 获取自动投标符合的list
	 *
	 * @return
	 */
	private List<PlBidAuto> autoList(PlBidPlan plBidPlan) {
		QueryFilter filter = new QueryFilter(this.getRequest());
		filter.addFilter("Q_isOpen_N_EQ", PlBidAuto.OPEN);
		filter.addFilter("Q_periodStart_N_LE", plBidPlan.getInterestPeriod().toString());
		filter.addFilter("Q_periodEnd_N_GE", plBidPlan.getInterestPeriod().toString());
		filter.addFilter("Q_interestStart_N_LE", plBidPlan.getYearRate().toString());
		filter.addFilter("Q_interestEnd_N_GE", plBidPlan.getYearRate().toString());
		filter.addFilter("Q_rateStart_S_LE", plBidPlan.getCreditLeveId().toString());
		filter.addFilter("Q_rateEnd_S_GE", plBidPlan.getCreditLeveId().toString());
		filter.addFilter("Q_banned_N_GE", "0");
		filter.addSorted("orderTime", QueryFilter.ORDER_ASC);
		filter.getPagingBean().setPageSize(10000000);
		List<PlBidAuto> autolist = plBidAutoService.getAll(filter);
		List<PlBidAuto> list=new ArrayList<PlBidAuto>();
		for(PlBidAuto plbidAuto : autolist){
			//是否可投条件：投资起点金额<=每次投标金额<=单笔投资上限     且  （每次投标金额-起投金额）/递增金额 等于 整数
			String[] riseOk=plbidAuto.getBidMoney().subtract(plBidPlan.getStartMoney()).divide(plBidPlan.getRiseMoney()).setScale(5).toString().split("[.]");
			if(plbidAuto.getBidMoney().compareTo(plBidPlan.getStartMoney())>=0 &&
					plbidAuto.getBidMoney().compareTo(plBidPlan.getMaxMoney())<=0 &&
					Long.valueOf(riseOk[1])==0
			){
				list.add(plbidAuto);
			}
		}
		return list;
	}

	/** 获取已经齐标数量 */
	public String getBidSum() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_state_N_EQ", "2");
		List<PlBidPlan> list = plBidPlanService.getAll(filter);
		if (list == null) {
			setJsonString("{success:true,data:'" + 0 + "'}");
		} else {
			setJsonString("{success:true,data:'" + list.size() + "'}");
		}
		return SUCCESS;
	}

	public String allLoanedList() {
		try {
			PagingBean pb = new PagingBean(start, limit);
			List<PlBidPlan> list = plBidPlanService.allLoanedList(this
					.getRequest(), pb);
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(pb.getTotalItems()).append(",result:");
			JSONSerializer serializer = JsonUtil.getJSONSerializer(
					"publishSingeTime", "bidEndTime", "updatetime",
					"createtime", "startIntentDate", "endIntentDate");
			buff.append(serializer.exclude(new String[] { "class" }).serialize(
					list));
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 获得借款人未还的本金
	 */
	public String getSurplusProjectMoney() {
		try {
			StringBuffer buff = new StringBuffer("{success:true");
			String earlyDate = this.getRequest().getParameter("earlyDate");
			String earlyProjectMoney = this.getRequest().getParameter(
					"earlyProjectMoney");
			String penaltyDays = this.getRequest().getParameter("penaltyDays");
			PlBidPlan plBidPlan = plBidPlanService.get(bidId);
			Long projectId = null;
			if (plBidPlan.getProType().equals("B_Dir")) {
				BpBusinessDirPro bdirpro = bpBusinessDirProService
						.get(plBidPlan.getBdirProId());
				projectId = bdirpro.getMoneyPlanId();
			} else if (plBidPlan.getProType().equals("B_Or")) {
				BpBusinessOrPro borpro = bpBusinessOrProService.get(plBidPlan
						.getBorProId());
				projectId = borpro.getMoneyPlanId();
			} else if (plBidPlan.getProType().equals("P_Dir")) {
				BpPersionDirPro pdirpro = bpPersionDirProService.get(plBidPlan
						.getPDirProId());
				projectId = pdirpro.getMoneyPlanId();
			} else if (plBidPlan.getProType().equals("P_Or")) {
				BpPersionOrPro porpro = bpPersionOrProService.get(plBidPlan
						.getPOrProId());
				projectId = porpro.getMoneyPlanId();
			}
			BpFundProject project = bpFundProjectService.get(projectId);
			if (null != earlyDate && !earlyDate.equals("")
					&& null != earlyProjectMoney
					&& !earlyProjectMoney.equals("")) {
				BigDecimal principalMoney = bpFundIntentService
						.getPrincipalMoney(bidId, earlyDate, null);
				BigDecimal money = plBidPlan.getBidMoney().subtract(
						new BigDecimal(earlyProjectMoney));
				if (null != principalMoney) {
					money = money.subtract(principalMoney);
				}
				buff.append(",surplusProjectMoney:" + money);
				if (null != penaltyDays && !penaltyDays.equals("")) {
					BigDecimal penaltyMoney = new BigDecimal(earlyProjectMoney)
							.multiply(new BigDecimal(penaltyDays)).multiply(
									project.getAccrual().divide(
											new BigDecimal(3000), 5,
											BigDecimal.ROUND_HALF_UP));
					buff.append(",penaltyMoney:" + penaltyMoney);
				}
			}
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 散标贷后查询
	 */
	public String findPlbidplanLoanAfter() {

		PagingBean pb = new PagingBean(start, limit);
		List<PlBidPlan> list = plBidPlanService.findPlbidplanLoanAfter(
				getRequest(), pb);

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(pb.getTotalItems()).append(",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("endIntentDate",
				"payMoneyTime", "startIntentDate", "bidEndTime", "updatetime",
				"createtime");

		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 散标贷后查询
	 */
	public String findByState() {

		PagingBean pb = new PagingBean(start, limit);
		/*List<PlBidPlan> list = plBidPlanService.findPlbidplanLoanAfter(
				getRequest(), pb);*/
		List<PlBidPlan> list = plBidPlanService.getByStateList(getRequest(), pb);
		List<PlBidPlan> listCount = plBidPlanService.getByStateList(getRequest(), null);
		//Integer listCount = plBidPlanService.countList(getRequest(), null);

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(listCount!=null?listCount.size():0).append(",result:");

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	public String updateInfo() {
		try {
			PlBidPlan plan = plBidPlanService.get(plBidPlan.getBidId());
			BeanUtil.copyNotNullProperties(plan, plBidPlan);
			plBidPlanService.merge(plan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String bidPlanVerification() {
		try {
			PlBidPlan plan = plBidPlanService.get(bidId);
			if (null == plan.getIsLoan() || plan.getIsLoan() != 1) {
				jsonString = "{success:true,isLoan:false}";
				return SUCCESS;
			}
			List<UploadLog> list = uploadLogService.listBybBidId(bidId
					.toString());
			Integer num = 0;
			for (UploadLog log : list) {
				if (null != log.getSuccessCount()) {
					num = num + log.getSuccessCount();
				}
			}
			Long count = procreditContractService.countByBidId(bidId);

			if (count.intValue() > num) {
				jsonString = "{success:true,isUpload:false}";
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String allList() {
		try {
			QueryFilter filter = new QueryFilter(this.getRequest());
			List<PlBidPlan> list = plBidPlanService.getAll(filter);
			for (PlBidPlan plan : list) {
				BigDecimal money = new BigDecimal(0);
				List<InvestPersonInfo> plist = investPersonInfoService
						.getByBidPlanId(plan.getBidId());
				for (InvestPersonInfo p : plist) {
					money = money.add(p.getInvestMoney());
				}
				if (plan.getBidMoney().compareTo(new BigDecimal(0)) != 0) {
					plan.setBidSchedule(money.divide(plan.getBidMoney(), 2,
							BigDecimal.ROUND_HALF_UP).multiply(
							new BigDecimal(100)));
				} else {
					plan.setBidSchedule(new BigDecimal(0));
				}

			}
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(filter.getPagingBean().getTotalItems()).append(
							",result:");
			JSONSerializer serializer = JsonUtil.getJSONSerializer(
					"publishSingeTime", "bidEndTime", "updatetime",
					"createtime", "prepareSellTime");
			buff.append(serializer.exclude(new String[] { "class" }).serialize(
					list));
			buff.append("}");

			jsonString = buff.toString();
			System.out.println("---" + buff.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public BpCustMember getLoanerP2PAccount(PlBidPlan bidplan){
		BpCustMember member = null;
		// 借款人关系 获取 网站注册用户信息
		BpCustRelation custRelation = new BpCustRelation();
		// 网站注册用户
		BpCustMember custMamber = new BpCustMember();
		// 项目类型 企业直投 B_Dir 企业 债权 B_Or 个人直投 P_Dir 个人债权 P_Or * @return String
		String loanUserType = "";
		Long loanUserId = null;
		Long custMamberId = null;
		if (bidplan.getProType().equals("B_Dir")) {
			loanUserType = "b_loan";
			loanUserId = bidplan.getBpBusinessDirPro().getBusinessId();
			if(loanUserId==null){
				loanUserId = bpBusinessDirProService.get(bidplan.getBdirProId()).getBusinessId();
			}
		} else if (bidplan.getProType().equals("B_Or")) {
			loanUserType = "b_loan";
			loanUserId = bidplan.getBpBusinessOrPro().getBusinessId();
			if(loanUserId==null){
				loanUserId = bpBusinessOrProService.get(bidplan.getBorProId()).getBusinessId();
			}

		} else if (bidplan.getProType().equals("P_Dir")) {
			loanUserType = "p_loan";
			Long BpPersionOrProId=bidplan.getBpPersionDirPro().getPdirProId();
			loanUserId = bpPersionDirProService.get(BpPersionOrProId).getPersionId();
			//loanUserId = bidplan.getBpPersionDirPro().getPersionId();
		} else if (bidplan.getProType().equals("P_Or")) {
			loanUserType = "p_loan";
			Long BpPersionOrProId=bidplan.getBpPersionOrPro().getPorProId();
			loanUserId = bpPersionOrProService.get(BpPersionOrProId).getPersionId();
		}
		if (loanUserId != null) {
			BpCustRelation bpCustRelation = bpCustRelationService
					.getByLoanTypeAndId(loanUserType, loanUserId);
			if (bpCustRelation != null) {
				custMamberId = bpCustRelation.getP2pCustId();
			}
		}
		if (custMamberId != null && !custMamberId.equals("")) {
			member = bpCustMemberService.get(custMamberId);
		}
		return member;
	}

	public String getToTimeDate() {
		PlBidPlan plBidPlan = plBidPlanService.get(bidId);

		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil
		.getJSONSerializerDateByFormate("yyyy-MM-dd HH:mm:ss");
		sb.append(json.serialize(plBidPlan));
		sb.append("}");
		jsonString = sb.toString();

		return SUCCESS;
	}
	/**
	 * 将标导出到Excel中
	 */
	public void allExportExcel(){
		try {
			QueryFilter filter = new QueryFilter(getRequest());
			// 标的类型
			String Q_proType_S_EQ = this.getRequest()
					.getParameter("Q_proType_S_EQ");

			String Q_state_N_EQ = getRequest().getParameter("Q_state_N_EQ");
			String flag = getRequest().getParameter("flag");
			// 已过期的列表
			if (null != Q_state_N_EQ && "".equals(Q_state_N_EQ)) {

				// 先判断标是否已经手动关闭
				filter.addFilter("Q_state_N_EQ", "-1");
				// 查询出招标中的标信息，再按时间排序
				// filter.addFilter("Q_state_N_EQ", "1");
				filter.addFilter("Q_proType_S_EQ", Q_proType_S_EQ);
				filter.addFilter("Q_bidEndTime_DG_LE", sdf.format(new Date()));
			} else {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				// filter.addFilter("Q_state_N_EQ", Q_state_N_EQ);
				filter.addFilter("Q_proType_S_EQ", Q_proType_S_EQ);
				if (null != Q_state_N_EQ && !Q_state_N_EQ.isEmpty()) {
					if (null != flag && flag.equals("21")) {
						filter.addFilter("Q_prepareSellTime_D_GT", sdf1
								.format(new Date()));
					}
					if (null != flag && flag.equals("22")) {
						filter.addFilter("Q_prepareSellTime_D_LE", sdf1
								.format(new Date()));
						filter.addFilter("Q_publishSingeTime_D_GT", sdf1
								.format(new Date()));
					}
					if (null != flag && flag.equals("23")) {
						filter.addFilter("Q_publishSingeTime_D_LE", sdf1
								.format(new Date()));
					}
				}
			}
			String publishSingeTime_GE = this.getRequest().getParameter("publishSingeTime_GE");
			String publishSingeTime_LE = this.getRequest().getParameter("publishSingeTime_LE");
			String bidEndTime_GE = this.getRequest().getParameter("bidEndTime_GE");
			String bidEndTime_LE = this.getRequest().getParameter("bidEndTime_LE");
			if(null != publishSingeTime_GE && !"".equals(publishSingeTime_GE)){
				filter.addFilter("Q_publishSingeTime_D_GE", publishSingeTime_GE+" 00:00:00");
			}
			if(null != publishSingeTime_LE && !"".equals(publishSingeTime_LE)){
				filter.addFilter("Q_publishSingeTime_D_LE", publishSingeTime_LE+" 23:59:59");
			}
			if(null != bidEndTime_GE && !"".equals(bidEndTime_GE)){
				filter.addFilter("Q_bidEndTime_D_GE", bidEndTime_GE+" 00:00:00");
			}
			if(null != bidEndTime_LE && !"".equals(bidEndTime_LE)){
				filter.addFilter("Q_bidEndTime_D_LE", bidEndTime_LE+" 23:59:59");
			}
			filter.addSorted("createtime", "DESC");
			List<PlBidPlan> list = plBidPlanService.getAll(filter);
			for(PlBidPlan pl:list){
				if(null!=pl.getProType() && "P_Dir".equals(pl.getProType())){
					pl.setYearInterestRate(pl.getBpPersionDirPro().getYearInterestRate());
				}
				if(null!=pl.getProType() && "B_Dir".equals(pl.getProType())){
					pl.setYearInterestRate(pl.getBpBusinessDirPro().getYearInterestRate());
				}
				if(null!=pl.getProType() && "P_Or".equals(pl.getProType())){
					pl.setYearInterestRate(pl.getBpPersionOrPro().getYearInterestRate());
				}
				if(null!=pl.getProType() && "B_Or".equals(pl.getProType())){
					pl.setYearInterestRate(pl.getBpBusinessOrPro().getYearInterestRate());
				}
			}
			String[] tableHeader = { "序号", "招标名称","招标编号","招标金额","年利率(%)","预售公告时间","开始投标时间" ,"招标截止时间"};
			String[] fields = {"bidProName","bidProNumber","bidMoney","yearInterestRate","prepareSellTime","publishSingeTime","bidEndTime"};
			try {
			   {
					ExportExcel.export(tableHeader, fields, list,"标的台账列表", PlBidPlan.class);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//工作桌面展示满标项目
	public String display(){
		String count=this.getRequest().getParameter("limitCount");
		PagingBean pb=new PagingBean(0, 7);//获取前七条数据
		Map map =ContextUtil.createResponseMap(this.getRequest());
		//List<TaskInfo> tasks=flowTaskService.getTaskInfosByUserId(ContextUtil.getCurrentUserId().toString(),pb);
		List<PlBidPlan> tasks=plBidPlanService.getPlanByStatusList(Short.valueOf("2"), pb,map);
		getRequest().setAttribute("plBidPlanList", tasks);
		return "display";
	}

	/**
	 * 查询此借款项目是否有进入还款中或者已完成的标的
	 * @return
	 */
	public String isAfterLoan(){
		Enumeration paramEnu = getRequest().getParameterNames();
		Map<String, String> map = new HashMap<String, String>();
		while(paramEnu.hasMoreElements()){
			String paramName = (String)paramEnu.nextElement();
			String paramValue = (String)getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<PlBidPlan> list = null;
		list=plBidPlanService.getByProType(map);
		if(null !=list && list.size()>0){
			setJsonString("{success:true,msg:'此借款项目有还款中或者已完成的标的，可以进行贷后披露!'}");
		}else{
			setJsonString("{success:false,msg:'此借款项目没有还款中或者已完成的标的，不能进行贷后披露!'}");
		}
		return SUCCESS;
	}
	public static void main(String[] args) {
	        //将开始时间赋给日历实例
	        Calendar sCalendar = Calendar.getInstance();
	        sCalendar.setTime(new Date());
	        //计算结束时间
	        sCalendar.add(Calendar.DATE, 12);
	        //返回Date类型结束时间
	        System.out.println(sCalendar.getTime());
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        System.out.println(sdf.format(sCalendar.getTime()));;
	    }

	@Resource
	private OrganizationService organizationService;

	/**
	 * 投资端结算中心 -- 显示
	 */
	public String platInvest(){
		List<Organization> list = organizationService.listOrgan();
		StringBuffer buff = new StringBuffer("[");
		for(Organization o :list){
			buff.append("[").append(o.getOrgId()).append(",'");
			if(o.getSettlementType()==(short)0){
				buff.append("普通部门").append("'],");
			}else if(o.getSettlementType()==(short)1){
				buff.append("投资推广部").append("'],");
			}else if(o.getSettlementType()==(short)2){
				buff.append("投资部门").append("'],");
			}else if(o.getSettlementType()==(short)3){
				buff.append("融资部门").append("'],");
			}
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");

		setJsonString(buff.toString());
		return SUCCESS;
	}

	public String settleSel(){
		List<SettlementReviewerPay> list = settlementReviewerPayService.platInvestCore(this.getRequest());

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(
				",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer(
				"payStartDate", "payEndDate");
		buff.append(serializer.exclude(new String[] { "class" }).serialize(
				list));
		buff.append("}");
		setJsonString(buff.toString());
		return SUCCESS;
	}

	/**
	 * 撤销单笔投标记录
	 */
	public String cancelBidInfo(){
		String[] set=new String[2];
		String infoId = this.getRequest().getParameter("infoId");
		PlBidInfo temp = plBidInfoService.get(Long.valueOf(infoId));
		PlBidPlan plan = temp.getPlBidPlan();
		List<InvestPersonInfo> investList = investPersonInfoService.getByBidPlanId(temp.getBidId());
		BigDecimal totleBidMoney = BigDecimal.ZERO;   // 撤销前投资总额
		if(investList!=null&&investList.size()>0){
			for(InvestPersonInfo invest:investList){
				totleBidMoney = totleBidMoney.add(invest.getInvestMoney());
			}
		}
		BpCustMember loanerMember = bpCustMemberService.getMemberUserName(plan.getReceiverP2PAccountNumber());
		if(loanerMember==null){
			jsonString = "{success:true,msg:该客户不存在,请检查'}";
		}
		if(Short.valueOf("1").equals(temp.getState())){
				String cardNo=ContextUtil.createRuestNumber();//流水号
				String unFreezeNum = ContextUtil.createRuestNumber();
				if (configMap.get("thirdPayType").toString().equals("0")) {
					BpCustMember customer = bpCustMemberService.get(temp.getUserId());
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					CommonRequst cq=new CommonRequst();
					cq.setThirdPayConfigId(customer.getThirdPayFlagId());//用户第三方标识 出账账户
					cq.setQueryRequsetNo(temp.getOrderNo());//之前投标的请求流水号
					cq.setLoanNoList(temp.getPreAuthorizationNum());//投标时第三方返回的流水号
					cq.setRequsetNo(cardNo);//请求流水号
	    			cq.setContractNo(temp.getPreAuthorizationNum()!=null?temp.getPreAuthorizationNum():"");
					cq.setBussinessType(ThirdPayConstants.BT_CANCELBID);//业务类型代码
	    			cq.setTransferName(ThirdPayConstants.TN_CANCELBID);//业务用途
	    			cq.setTransactionTime(date);
	    			cq.setOrderDate(sdf.format(date));
	    			cq.setOrdId(temp.getOrderNo());//汇付的投标撤销接口(投标的流水号)
	    			cq.setLoaner_thirdPayflagId(loanerMember.getThirdPayFlagId());//入账账户  富友
	    			cq.setBidId(plan.getBidId().toString());
	    			cq.setMerPriv("");//商户私有域
	    			cq.setTrxId(temp.getTrxId());
	    			cq.setFreezeOrdId(temp.getFreezeTrxId());
	    			cq.setAmount(temp.getUserMoney().setScale(2));
	    			if(customer.getCustomerType()!=null&&!"".equals(customer.getCustomerType())&&customer.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){
	    				cq.setAccountType(1);//企业
	    			}else{
	    				cq.setAccountType(0);//个人
	    			}
	    			CommonResponse cr=ThirdPayInterfaceUtil.thirdCommon(cq);
					if(cr.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
						set[0]= Constants.CODE_SUCCESS;
						set[1]="流标成功,第三方冻结资金已经归还";
					}else{
						set[0]=Constants.CODE_FAILED;
						set[1]= cr.getResponseMsg();
					}
					if (set[0].equals(Constants.CODE_SUCCESS)) {
								//判断是否使用了优惠券
								if(temp.getCouponId()!=null&&!temp.getCouponId().equals("")){
									BpCoupons coupon = bpCouponsService.get(temp.getCouponId());
									coupon.setCouponMoney(new BigDecimal(0));
									coupon.setCouponStatus(Short.valueOf("5"));
									bpCouponsService.save(coupon);
								}

								temp.setState(Short.valueOf("3"));
								temp.setCouponId(null);
								plBidInfoService.save(temp);
								List<InvestPersonInfo> invests = investPersonInfoService.getByRequestNumber(temp.getOrderNo());
								if(invests!=null&&invests.size()>0){
									//删除投标成功记录
									InvestPersonInfo invest = invests.get(0);
									investPersonInfoService.remove(invest);
								}
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("investPersonId", temp.getUserId());// 投资人Id
								map.put("investPersonType", ObSystemAccount.type0);// 投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量)
								map.put("transferType", ObAccountDealInfo.T_INVEST);// 交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量)
								map.put("money", temp.getUserMoney());// 交易金额
								map.put("dealDirection", ObAccountDealInfo.DIRECTION_PAY);// 交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）
								map.put("DealInfoId", null);// 交易记录id，没有默认为null
								map.put("recordNumber", temp.getOrderNo());// 交易流水号
								map.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_8);// 资金交易状态：1等待支付，2支付成功，3
								String[] ret = obAccountDealInfoService
										.updateAcountInfo(map);
								try {
									//流标成功发送短信
									Map<String, String> mapSms = new HashMap<String, String>();
									mapSms.put("telephone", customer.getTelphone());
									mapSms.put("${name}", customer.getLoginname());
									mapSms.put("${code}", temp.getUserMoney().toString());
									mapSms.put("${projName}",plan.getBidProName());
									mapSms.put("key", "sms_flowStandard");
									smsSendService.smsSending(mapSms);
									//smsSendUtil.sms_flowStandard(customer.getTelphone(),customer.getLoginname(),temp.getUserMoney().toString(), plan.getBidProName());
								} catch (Exception e) {
									e.printStackTrace();
								}
								if(totleBidMoney.subtract(temp.getUserMoney()).compareTo(plan.getBidMoney())==0){
					    			plan.setState(2);
					    			plBidPlanService.merge(plan);
					    			jsonString = "{success:true,msg:'已经流标了，并返还投资人投资金额'}";
					    		}else if(totleBidMoney.subtract(temp.getUserMoney()).compareTo(plan.getBidMoney())<0){
					    			plan.setState(1);
					    			plBidPlanService.merge(plan);
					    			jsonString = "{success:true,msg:'已经流标了，并返还投资人投资金额'}";
					    		}else if(totleBidMoney.subtract(temp.getUserMoney()).compareTo(plan.getBidMoney())>0){
					    			jsonString="{success:true,msg:'该标还处于超标状态,请核实'}";
					    		}
					}else{
						jsonString = "{success:true,msg:'流标请求失败'}";
					}
				}
		}else{
			jsonString="{success:true,msg:'只能取消投资冻结的记录。'}";
		}
		return SUCCESS;
  }

    /**
     * 增加对当前标有募集期奖励的用户发放募集期奖励
     * XF
     */
    public String collectPeriodMoney() {
        String bidPlanId = this.getRequest().getParameter("bidPlanId");
        List<BpFundIntent> list = bpFundIntentService.listCollectPeriod(Long.valueOf(bidPlanId));
        BigDecimal money = BigDecimal.ZERO;
		BpFundIntent bpFund;
		int counts = 0;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                bpFund = list.get(i);
                BpCustMember member = bpCustMemberService.get(bpFund.getInvestPersonId());
                if (member != null) {
                    if (bpFund.getNotMoney().compareTo(BigDecimal.ZERO) != 0) {
                    	//请求银行,调用三方转账接口
                        String orderNum = ContextUtil.createRuestNumber();
                        money = bpFund.getNotMoney();
                        if (money.longValue() > 100){
                        	money = new BigDecimal(100);
						}
                        CommonRequst commonRequst = new CommonRequst();
                        commonRequst.setRequsetNo(orderNum);
                        commonRequst.setThirdPayConfigId(member.getThirdPayFlagId());
                        commonRequst.setAmount(money);
                        commonRequst.setThirdPayConfigId0(member.getThirdPayFlag0());
                        commonRequst.setBussinessType(ThirdPayConstants.BT_TRANSFER_MEMBER);
                        commonRequst.setTransferName(ThirdPayConstants.TN_TRANSFER_MEMBER);
                        CommonResponse resp = ThirdPayInterfaceUtil.thirdCommon(commonRequst);
                        if (resp.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)) {
//                        if (true && i !=3) {
                        	//放款成功,调整数据库相应字段
                            bpFund.setNotMoney(bpFund.getNotMoney().subtract(money));
                            bpFund.setAfterMoney(bpFund.getAfterMoney().add(money));
							bpFund.setFactDate(new Date());
							bpFundIntentService.save(bpFund);
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("investPersonId", member.getId());//投资人Id（必填）
                            map.put("investPersonType", ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）
                            map.put("transferType", ObAccountDealInfo.T_BIDRETURN_RATE31);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
                            map.put("money", money);//交易金额	（必填）
                            map.put("dealDirection", ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
                            map.put("dealType", ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
                            map.put("recordNumber", orderNum);//交易流水号	（必填）
                            map.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
                            obAccountDealInfoService.operateAcountInfo(map);

							//每放款成功一次,扣放款账户一笔钱
							Map<String, Object> map2 = new HashMap<String, Object>();
							map2.put("investPersonId", Long.valueOf("7164"));//投资人Id（必填）
							map2.put("investPersonType", ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）
							map2.put("transferType", ObAccountDealInfo.T_TRANSFER);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
							map2.put("money", money);//交易金额	（必填）
							map2.put("dealDirection", ObAccountDealInfo.DIRECTION_PAY);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
							map2.put("dealType", ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
							map2.put("recordNumber", orderNum+"_1");//交易流水号	（必填）
							map2.put("dealStatus", ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
							obAccountDealInfoService.operateAcountInfo(map2);
							counts++;
							jsonString = "{success:true,msg:'转账成功,成功个数:"+counts+"个'}";
                        } else {
							jsonString= "{success:true,msg:'转账成功个数:" + counts +"个'}";
							continue;
                        }
                    }else{
						jsonString= "{success:true,msg:'转账失败,当前标没有用户有奖励或已发放'}";
					}
                }
            }
        }else {
			jsonString="{success:true,msg:'转账失败,当前标的没有奖励'}";
        }

        return SUCCESS;
    }




}
