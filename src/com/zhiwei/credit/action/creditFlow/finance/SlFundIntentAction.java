package com.zhiwei.credit.action.creditFlow.finance;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.thirdInterface.pay.service.ThirdPayEngine;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.util.UUIDGenerator;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditAssignment.obligationFundIntenList;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.ExportExcel;
import com.zhiwei.credit.core.project.FinanceFundIntentListPro;
import com.zhiwei.credit.core.project.FinanceFundIntentListProtw;
import com.zhiwei.credit.core.project.FundIntent;
import com.zhiwei.credit.core.project.FundIntentFactory;
import com.zhiwei.credit.core.project.FundIntentListPro3;
import com.zhiwei.credit.core.project.FundIntentListPro4;
import com.zhiwei.credit.core.project.FundIntentListPro5;
import com.zhiwei.credit.core.project.FundIntentListProtw;
import com.zhiwei.credit.core.project.GuaranteeFundIntentIist;
import com.zhiwei.credit.core.project.LeaseFinanceFundIntentListPro;
import com.zhiwei.credit.core.project.LeaseFinanceFundIntentListProtw;
import com.zhiwei.credit.core.project.PawnFundIntentListPro;
import com.zhiwei.credit.core.project.PawnFundIntentListProtw;
import com.zhiwei.credit.core.project.impl.FundAlterAccrualFundIntentCreate;
import com.zhiwei.credit.core.project.impl.FundIntentGenerate;
import com.zhiwei.credit.core.project.impl.FundPrepaymentFundIntentCreate;
import com.zhiwei.credit.core.project.impl.SameInterestFactory;
import com.zhiwei.credit.core.project.impl.SuperviseFundIntentCreate;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObObligationInvestInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.ObObligationProject;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.model.creditFlow.finance.SlFundDetail;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;
import com.zhiwei.credit.model.creditFlow.finance.SlPunishDetail;
import com.zhiwei.credit.model.creditFlow.finance.SlPunishInterest;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnContinuedManagment;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.VSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.pay.BpMoneyManager;
import com.zhiwei.credit.model.pay.MadaiLoanInfoBean;
import com.zhiwei.credit.model.pay.MoneyMoreMore;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.CsHoliday;
import com.zhiwei.credit.model.system.DictionaryIndependent;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObObligationInvestInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.project.ObObligationProjectService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlBankAccountService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundDetailService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundQlideService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundintentUrgeService;
import com.zhiwei.credit.service.creditFlow.finance.SlPunishDetailService;
import com.zhiwei.credit.service.creditFlow.finance.SlPunishInterestService;
import com.zhiwei.credit.service.creditFlow.finance.VFundDetailService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.financeProject.VFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.guarantee.project.GLGuaranteeloanProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.VLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.VSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.supervise.SlSuperviseRecordService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.customer.InvestPersonService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.pay.BpMoneyManagerService;
import com.zhiwei.credit.service.pay.IPayService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.CsHolidayService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.service.thirdInterface.FuiouService;
import com.zhiwei.credit.util.Common;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author
 * 
 */
@SuppressWarnings({"unused","unchecked"})
public class SlFundIntentAction extends BaseAction {
	@Resource
	private ObSystemAccountService obSystemAccountService;
	@Resource
	private BpMoneyManagerService bpMoneyManagerService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlFundQlideService slFundQlideService;
	@Resource
	private SlFundDetailService slFundDetailService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private SlBankAccountService slBankAccountService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private GLGuaranteeloanProjectService glGuaranteeloanProjectService; // 企业贷款
	@Resource
	private SlFundintentUrgeService slFundintentUrgeService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private SlPunishInterestService slPunishInterestService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private SlSuperviseRecordService slSuperviseRecordService;
	@Resource
	private VFundDetailService vFundDetailService;
	@Resource
	private CsHolidayService csHolidayService;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private VSmallloanProjectService vSmallloanProjectService;
	@Resource
	private VFinancingProjectService vFinancingProjectService;
	@Resource
	private PlPawnProjectService plPawnProjectService;
	@Resource
	private VLeaseFinanceProjectService vLeaseFinanceProjectService;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	@Resource
	private InvestEnterpriseService investEnterpriseService;
	@Resource
	private InvestPersonService investPersonService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private ObObligationProjectService obObligationProjectService;
	@Resource
	private ObObligationInvestInfoService obObligationInvestInfoService;
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private IPayService iPayService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private SlPunishDetailService slPunishDetailService;
	@Resource
	private CsBankService csBankService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private FuiouService fuiouService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	
	private List<SlFundIntent> listByProjId = null;
	private Long currentPid = null;// 当前款项记录项目id
	private SlSmallloanProject p = null;
	private FlFinancingProject flFinancingProject;
	private PlPawnProject plPawnProject;
	private FlLeaseFinanceProject flLeaseFinanceProject;
	private PawnContinuedManagment pawnContinuedManagment;
	private ObObligationProject obObligationProject;
	private String loanRepayStatus;
	private int flag1;
	private int flag2;
	private Long projectId;
	private Long fundQlideId;
	private String qlideId;
	private String slFundIentJson;
	private String calcutype;
	private String fundType = "invest";
	private String isHaveLending;
	private String relateIntentOrCharge;
	private String businessType;
	private Boolean isGrantedShowAllProjects;// 是否授权查看所有款项催收信息
	private Long punishInterestId;
	
	private SlFundQlide slFundQlide;
	private SlFundIntent slFundIntent;
	private SlPunishInterest slPunishInterest;
	private BpFundProject ownBpFundProject;
	private BpFundProject platFormBpFundProject;
	private SlSmallloanProject slSmallloanProject;
	private SlSuperviseRecord slSuperviseRecord;
	private SlEarlyRepaymentRecord slEarlyRepaymentRecord;
	private SlAlterAccrualRecord slAlterAccrualRecord;
	private GLGuaranteeloanProject gLGuaranteeloanProject;
	
	public SlPunishInterest getSlPunishInterest() {
		return slPunishInterest;
	}
	public void setSlPunishInterest(SlPunishInterest slPunishInterest) {
		this.slPunishInterest = slPunishInterest;
	}
	
	public String getLoanRepayStatus() {
		return loanRepayStatus;
	}
	public void setLoanRepayStatus(String loanRepayStatus) {
		this.loanRepayStatus = loanRepayStatus;
	}
	
	//TODO ----------------------款项对账开始---------------------------
	
	/**
	 * 撤销对账(本金、利息)
	 * @return
	 */
	public String cancelAccount() {
		try{
			SlFundIntent oldSlFundIntent = slFundIntentService.get(fundIntentId);
			BigDecimal lin = new BigDecimal(0.00);
			if (oldSlFundIntent.getIncomeMoney().compareTo(lin) == 0) {
				oldSlFundIntent.setPayInMoney(oldSlFundIntent.getPayMoney());
			} else {
				oldSlFundIntent.setPayInMoney(oldSlFundIntent.getIncomeMoney());
			}
			
			SlFundDetail detail = slFundDetailService.get(Long.valueOf(qlideId));
			if (null != detail) {
				//当前款项对应的流水记录
				SlFundQlide slFundQlide =slFundQlideService.get(detail.getSlFundQlide().getFundQlideId());
				
				//1.修改资金明细
				AppUser user = ContextUtil.getCurrentUser();
				detail.setCancelremark(user.getFullname() + "于"+ DateUtil.getNowDateTime() + "撤销此对账记录");
				detail.setIscancel(Short.valueOf("1"));
				slFundDetailService.merge(detail);
				
				//2.修改流水
				slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().subtract(detail.getAfterMoney()));
				slFundQlide.setNotMoney(slFundQlide.getNotMoney().add(detail.getAfterMoney()));
				slFundQlideService.merge(slFundQlide);
				
				//3.修改款项
				oldSlFundIntent.setAfterMoney(oldSlFundIntent.getAfterMoney().subtract(detail.getAfterMoney()));
				oldSlFundIntent.setNotMoney(oldSlFundIntent.getNotMoney().add(detail.getAfterMoney()));
				oldSlFundIntent.setFactDate(null);
				
				//4.手动跑批
				slFundIntentService.computeAccrualnew(oldSlFundIntent,null);
				slFundIntentService.merge(oldSlFundIntent);
				
				saveprojectreleadfinance(oldSlFundIntent.getProjectId(), oldSlFundIntent.getBusinessType());
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 撤销对账(罚息、逾期)
	 * @return
	 */
	public String cancelPunishAccount() {
		try{
			slPunishInterest = slPunishInterestService.get(punishInterestId);//罚息记录
			BigDecimal lin = new BigDecimal(0.00);
			if (slPunishInterest.getIncomeMoney().compareTo(lin) == 0) {
				slPunishInterest.setPayInMoney(slPunishInterest.getPayMoney());
			} else {
				slPunishInterest.setPayInMoney(slPunishInterest.getIncomeMoney());
			}
			String[] ids = getRequest().getParameterValues("detailIds");//罚息明细id
			if (ids != null) {
				for (String id : ids) {
					if (!id.equals("") && !id.equals("0")) {
						if (null == slPunishDetailService.get(new Long(id))) {
							return SUCCESS;
						}
						SlFundIntent oldSlFundIntent=slFundIntentService.get(slPunishInterest.getFundIntentId());//款项记录
						SlPunishDetail slPunishDetail=slPunishDetailService.get(new Long(id));//罚息对账明细
						SlFundQlide slFundQlide =slFundQlideService.get(slPunishDetail.getFundQlideId());//流水记录
						//1.先修改流水记录
						slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().subtract(slPunishDetail.getAfterMoney()));
						slFundQlide.setNotMoney(slFundQlide.getNotMoney().add(slPunishDetail.getAfterMoney()));
						
						//2.修改罚息记录
						slPunishInterest.setAfterMoney(slPunishInterest.getAfterMoney().subtract(slPunishDetail.getAfterMoney()));
						slPunishInterest.setNotMoney(slPunishInterest.getNotMoney().add(slPunishDetail.getAfterMoney()));
						slPunishInterest.setFactDate(null);
						
						//3.修改罚息对账明细
						AppUser user = ContextUtil.getCurrentUser();
						slPunishDetail.setCancelremark(user.getFullname() + "于"+ DateUtil.getNowDateTime() + "撤销此对账记录");
						slPunishDetail.setIscancel(Short.valueOf("1"));
						slPunishDetailService.merge(slPunishDetail);
						
						//4.修改款项罚息
						oldSlFundIntent.setFaxiAfterMoney(oldSlFundIntent.getFaxiAfterMoney().subtract(slPunishDetail.getAfterMoney()));
						slFundQlideService.merge(slFundQlide);
						slPunishInterestService.merge(slPunishInterest);
						slFundIntentService.merge(oldSlFundIntent);
						
						saveprojectreleadfinance(oldSlFundIntent.getProjectId(), oldSlFundIntent.getBusinessType());
					}
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 利息()核销
	 */
	public String editAfterMoney() {
		try{
			String premark = this.getRequest().getParameter("premark");//核销备注
			//1.修改款项
			SlFundIntent s = slFundIntentService.get(slFundIntent.getFundIntentId());//款项记录
			s.setFlatMoney(s.getFlatMoney().add(slFundIntent.getFlatMoney()));
			s.setNotMoney(slFundIntent.getNotMoney());
			
			//2.创建一条资金明细
			SlFundDetail slFundDetail = new SlFundDetail();
			slFundDetail.setPremark(premark);
			slFundDetail.setOperTime(new Date());
			slFundDetail.setSlFundIntent(s);
			slFundDetail.setSlFundQlide(null);
			slFundDetail.setAfterMoney(slFundIntent.getFlatMoney());
			slFundDetail.setFactDate(new Date());
			slFundDetail.setCompanyId(s.getCompanyId());
			AppUser user = ContextUtil.getCurrentUser();
			slFundDetail.setCheckuser(user.getFullname());
			//计算当前核销金额截止到当前日期所产生的罚息及逾期金额
			BigDecimal overdueMoney = new BigDecimal(0.00);//逾期金额
			BigDecimal penaltyMoney = new BigDecimal(0.00);//罚息金额
			SlSmallloanProject loan = slSmallloanProjectService.get(s.getProjectId());
			//3.逾期罚息利率
			s.setOverdueRate(loan.getOverdueRateLoan());
			if (null == s.getOverdueRate()) {
				s.setOverdueRate(new BigDecimal(0));
			}
			BigDecimal day = new BigDecimal(DateUtil.compare_date(s.getIntentDate(),new Date()));
			overdueMoney=s.getOverdueRate().multiply(day).multiply(slFundIntent.getFlatMoney()).divide(new BigDecimal(100), 2);
			slFundDetail.setOverdueAccrual(overdueMoney);
			
			//4.保存修改
			slFundDetailService.save(slFundDetail);
			slFundIntentService.merge(s);
			
			saveprojectreleadfinance(s.getProjectId(), s.getBusinessType());

			setJsonString("{success:true}");
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 批量对账(一笔款项对应多笔流水)
	 * @return
	 */
	public String check() {
		try{
			String[] ids = getRequest().getParameterValues("qlideId");//流水id
			//1.初始化款项
			SlFundIntent oldSlFundIntent = slFundIntentService.get(fundIntentId);
			//应收金额(应放金额)
			oldSlFundIntent.setPayInMoney(oldSlFundIntent.getNotMoney());
			if (oldSlFundIntent.getPayInMoney().compareTo(oldSlFundIntent.getAfterMoney()) == 0) {
				return "checkfail";
			}

			//2.查询该款项资金明细
			int count = 0;//（如果其中任何一条流水的金额+款项已对账金额）》=款项应收金额，则只需要循环一次
			if (ids != null) {
				for (String id : ids) {
					if (!id.equals("") && count < 1) {
						//3.创建一条资金明细
						SlFundDetail slFundDetail = new SlFundDetail();
						slFundDetail.setOperTime(new Date());
						
						SlFundQlide slFundQlide = slFundQlideService.get(new Long(id));//流水记录
						if(!"principalLending".equals(oldSlFundIntent.getFundType())){
							long day = compare_date(DateUtil.parseDate(oldSlFundIntent.getGraceDay()),slFundQlide.getFactDate());
							if ((day == -1 || day == 0) && (null == oldSlFundIntent.getFactDate())) {
								oldSlFundIntent.setIsOverdue("否");
							} else {
								oldSlFundIntent.setIsOverdue("是");
							}
						}

						BigDecimal intentAfterMoney = oldSlFundIntent.getAfterMoney();//款项已对账金额
						BigDecimal qlideNotMoney = slFundQlide.getNotMoney();//流水未对账金额
						BigDecimal detailAfterMoney = qlideNotMoney;
						intentAfterMoney = intentAfterMoney.add(qlideNotMoney);//款项已对账金额     +  流水未对账金额
						int flag=intentAfterMoney.compareTo(oldSlFundIntent.getPayInMoney());
						if (flag == 1 || flag == 0) {
							count++;
							detailAfterMoney = oldSlFundIntent.getNotMoney();
							slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().add(detailAfterMoney));
							slFundQlide.setNotMoney(slFundQlide.getNotMoney().subtract(detailAfterMoney));
							oldSlFundIntent.setAfterMoney(oldSlFundIntent.getAfterMoney().add(detailAfterMoney));
							oldSlFundIntent.setNotMoney(oldSlFundIntent.getNotMoney().subtract(detailAfterMoney));
						} else {
							slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().add(qlideNotMoney));
							slFundQlide.setNotMoney(slFundQlide.getNotMoney().subtract(qlideNotMoney));
							oldSlFundIntent.setAfterMoney(oldSlFundIntent.getAfterMoney().add(qlideNotMoney));
							oldSlFundIntent.setNotMoney(oldSlFundIntent.getNotMoney().subtract(qlideNotMoney));
						}
						oldSlFundIntent.setFactDate(slFundQlide.getFactDate());
						slFundDetail.setSlFundIntent(oldSlFundIntent);
						slFundDetail.setSlFundQlide(slFundQlide);
						slFundDetail.setAfterMoney(detailAfterMoney);
						slFundDetail.setFactDate(slFundQlide.getFactDate());

						slFundDetail.setCompanyId(oldSlFundIntent.getCompanyId());
						slFundDetail.setOperTime(new Date());
						AppUser user = ContextUtil.getCurrentUser();
						slFundDetail.setCheckuser(user.getFullname());
						oldSlFundIntent.setFactDate(slFundQlide.getFactDate());
						
						if(!"principalLending".equals(oldSlFundIntent.getFundType())){
							//计算当前对账金额截止到流水到账日所产生的罚息及逾期金额
							BigDecimal overdueMoney = new BigDecimal(0.00);//逾期金额
							BigDecimal penaltyMoney = new BigDecimal(0.00);//罚息金额
							SlSmallloanProject loan = slSmallloanProjectService.get(oldSlFundIntent.getProjectId());
							//3.获得逾期利率
							oldSlFundIntent.setOverdueRate(loan.getOverdueRateLoan());
							if (null == oldSlFundIntent.getOverdueRate()) {
								oldSlFundIntent.setOverdueRate(new BigDecimal(0));
							}
							BigDecimal day2 = new BigDecimal(DateUtil.compare_date(oldSlFundIntent.getIntentDate(),slFundQlide.getFactDate()));
							overdueMoney=oldSlFundIntent.getOverdueRate().multiply(day2).multiply(detailAfterMoney).divide(new BigDecimal(100), 2);
							slFundDetail.setOverdueAccrual(overdueMoney);
							slFundDetail.setOverdueNum(day2.longValue());
							
							//一次性全部对账
							if(slFundDetail.getAfterMoney().add(oldSlFundIntent.getFlatMoney()).compareTo(oldSlFundIntent.getIncomeMoney())==0){
								oldSlFundIntent.setPunishDays(day2.intValue());
							}
						}

						slFundDetailService.save(slFundDetail);
						slFundIntentService.merge(oldSlFundIntent);
						slFundQlideService.merge(slFundQlide);
						
						//主动调用跑批
						if(!"principalLending".equals(oldSlFundIntent.getFundType())){
							slFundIntentService.computeAccrualnew(oldSlFundIntent,slFundDetail);
						}
					}
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 单笔对账（一笔款项一笔流水）
	 * @return
	 */
	public String editQlideCheck() {
		try{
			SlFundQlide oldSlFundQlide = slFundQlideService.get(fundQlideId);//流水记录
			
			BigDecimal inIntentMoney = slFundQlide.getNotMoney();//本流水划入本款项金额
			
			//1.修改款项
			SlFundIntent oldSlFundIntent = slFundIntentService.get(fundIntentId);
			oldSlFundIntent.setPayInMoney(oldSlFundIntent.getNotMoney());
			oldSlFundIntent.setAfterMoney(oldSlFundIntent.getAfterMoney().add(inIntentMoney));
			oldSlFundIntent.setNotMoney(oldSlFundIntent.getNotMoney().subtract(inIntentMoney));
			oldSlFundIntent.setFactDate(oldSlFundQlide.getFactDate());
			
			if(!"principalLending".equals(oldSlFundIntent.getFundType())){
				long day = compare_date(DateUtil.parseDate(oldSlFundIntent.getGraceDay()),oldSlFundQlide.getFactDate());
				if ((day == -1 || day == 0) && (null == oldSlFundIntent.getFactDate())) {
					oldSlFundIntent.setIsOverdue("否");
				} else {
					oldSlFundIntent.setIsOverdue("是");
				}
			}
			
			//2.创建资金明细记录
			SlFundDetail slFundDetail = new SlFundDetail();
			slFundDetail.setSlFundIntent(oldSlFundIntent);
			slFundDetail.setSlFundQlide(slFundQlide);
			slFundDetail.setFactDate(oldSlFundQlide.getFactDate());
			slFundDetail.setAfterMoney(inIntentMoney);
			slFundDetail.setOperTime(new Date());
			AppUser user = ContextUtil.getCurrentUser();
			slFundDetail.setCheckuser(user.getFullname());
			slFundDetail.setCompanyId(oldSlFundIntent.getCompanyId());
			
			//3.修改流水
			oldSlFundQlide.setAfterMoney(oldSlFundQlide.getAfterMoney().add(inIntentMoney));
			oldSlFundQlide.setNotMoney(oldSlFundQlide.getNotMoney().subtract(inIntentMoney));
			
			if(!"principalLending".equals(oldSlFundIntent.getFundType())){
				//计算当前对账金额截止到流水到账日所产生的罚息及逾期金额
				BigDecimal overdueMoney = new BigDecimal(0.00);//逾期金额
				BigDecimal penaltyMoney = new BigDecimal(0.00);//罚息金额
				SlSmallloanProject loan = slSmallloanProjectService.get(oldSlFundIntent.getProjectId());
				//3.获得逾期罚息利率
				oldSlFundIntent.setOverdueRate(loan.getOverdueRateLoan());
				if (null == oldSlFundIntent.getOverdueRate()) {
					oldSlFundIntent.setOverdueRate(new BigDecimal(0));
				}
				BigDecimal day2 = new BigDecimal(DateUtil.compare_date(oldSlFundIntent.getIntentDate(),oldSlFundQlide.getFactDate()));
				overdueMoney=oldSlFundIntent.getOverdueRate().multiply(day2).multiply(inIntentMoney).divide(new BigDecimal(100), 2);
				slFundDetail.setOverdueAccrual(overdueMoney);
			}
			
			slFundDetailService.save(slFundDetail);
			slFundQlideService.merge(oldSlFundQlide);
			slFundIntentService.merge(oldSlFundIntent);
			
			//4.重新跑批
			if(!"principalLending".equals(oldSlFundIntent.getFundType())){
				slFundIntentService.computeAccrualnew(oldSlFundIntent,slFundDetail);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 利息、本金收款台账流水对账(多笔款项对应一笔流水)
	 * @return
	 */
	public String manyIntentCheck() {
		try{
			slFundQlide = slFundQlideService.get(fundQlideId);
			if (slFundQlide.getPayMoney() != null) {
				slFundQlide.setDialMoney(slFundQlide.getPayMoney());
			} else {
				slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
			}
			String ids[] = this.getRequest().getParameterValues("fundIntentIds");//款项id集合
			int count = 0;
			for (String id : ids) {
				if (!id.equals("") && count < 1) {
					SlFundIntent oldSlFundIntent = slFundIntentService.get(new Long(id));
					if (oldSlFundIntent.getNotMoney().compareTo(new BigDecimal("0")) == 0) {
						System.out.println("已经对账");
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						oldSlFundIntent.setPayInMoney(oldSlFundIntent.getNotMoney());

						//1.创建一条资金明细
						SlFundDetail slFundDetail = new SlFundDetail();
						slFundDetail.setOperTime(new Date());
						
						long day=0;
						if(!"principalLending".equals(oldSlFundIntent.getFundType())){
							day = compare_date(DateUtil.parseDate(oldSlFundIntent.getGraceDay()),slFundQlide.getFactDate());
							if ((day == -1 || day == 0) && (null == oldSlFundIntent.getFactDate())) {
								oldSlFundIntent.setIsOverdue("否");
							} else {
								oldSlFundIntent.setIsOverdue("是");
							}
						}

						BigDecimal detailAfterMoney = oldSlFundIntent.getNotMoney();//款项未对账金额

						slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().add(detailAfterMoney));
						slFundQlide.setNotMoney(slFundQlide.getNotMoney().subtract(detailAfterMoney));
						
						oldSlFundIntent.setAfterMoney(oldSlFundIntent.getAfterMoney().add(detailAfterMoney));
						oldSlFundIntent.setNotMoney(new BigDecimal(0.00));
						oldSlFundIntent.setFactDate(slFundQlide.getFactDate());

						slFundDetail.setSlFundIntent(oldSlFundIntent);
						slFundDetail.setSlFundQlide(slFundQlide);
						slFundDetail.setAfterMoney(detailAfterMoney);
						slFundDetail.setFactDate(slFundQlide.getFactDate());

						if(!"principalLending".equals(oldSlFundIntent.getFundType())){
							//计算当前对账金额截止到流水到账日所产生的罚息及逾期金额
							BigDecimal overdueMoney = new BigDecimal(0.00);//逾期金额
							BigDecimal penaltyMoney = new BigDecimal(0.00);//罚息金额
							SlSmallloanProject loan = slSmallloanProjectService.get(oldSlFundIntent.getProjectId());
							//3.获得逾期罚息利率
							oldSlFundIntent.setOverdueRate(loan.getOverdueRateLoan());
							if (null == oldSlFundIntent.getOverdueRate()) {
								oldSlFundIntent.setOverdueRate(new BigDecimal(0));
							}
							day=DateUtil.compare_date(oldSlFundIntent.getIntentDate(),slFundQlide.getFactDate());
							if(day>0){
								overdueMoney=oldSlFundIntent.getOverdueRate().multiply(new BigDecimal(day)).multiply(detailAfterMoney).divide(new BigDecimal(100), 2);
								slFundDetail.setOverdueAccrual(overdueMoney);
								slFundDetail.setOverdueNum(day);
							}else{
								slFundDetail.setOverdueNum(0L);
							}
						}
						slFundDetail.setOperTime(new Date());
						AppUser user = ContextUtil.getCurrentUser();
						slFundDetail.setCheckuser(user.getFullname());
						oldSlFundIntent.setFactDate(slFundQlide.getFactDate());

						slFundDetail.setCompanyId(oldSlFundIntent.getCompanyId());
						slFundDetailService.save(slFundDetail);
						slFundIntentService.merge(oldSlFundIntent);
						slFundQlideService.merge(slFundQlide);
						
						//4.重新跑批
						if(!"principalLending".equals(oldSlFundIntent.getFundType())){
							slFundIntentService.computeAccrualnew(oldSlFundIntent,slFundDetail);
						}
					}
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 综合收款台账(一笔款项对一笔流水)
	 * @return
	 */
	public String complexCheck(){
		try{
			String projectId=this.getRequest().getParameter("projectId");//项目Id
			String payintentPeriod=this.getRequest().getParameter("payintentPeriod");//期数
			
			BigDecimal tempMoney=new BigDecimal(0);
			
			BigDecimal bjMoney=new BigDecimal(0);//本金逾期金额
			BigDecimal lxMoney=new BigDecimal(0);//利息逾期金额
			BigDecimal glMoney=new BigDecimal(0);//管理咨询费逾期金额
			BigDecimal cwMoney=new BigDecimal(0);//财务服务费逾期金额
			
			String overDay=this.getRequest().getParameter("overDay");//逾期天数
			
			String bjMoneyStr=this.getRequest().getParameter("bjMoney");//本金逾期金额
			String lxMoneyStr=this.getRequest().getParameter("lxMoney");//利息逾期金额
			String glMoneyStr=this.getRequest().getParameter("glMoney");//管理咨询费逾期金额
			String cwMoneyStr=this.getRequest().getParameter("cwMoney");//财务服务费逾期金额
			
			if(null!=bjMoneyStr && !"".equals(bjMoneyStr)){
				bjMoney=new BigDecimal(bjMoneyStr);
			}
			if(null!=lxMoneyStr && !"".equals(lxMoneyStr)){
				lxMoney=new BigDecimal(lxMoneyStr);
			}
			if(null!=glMoneyStr && !"".equals(glMoneyStr)){
				glMoney=new BigDecimal(glMoneyStr);
			}
			if(null!=cwMoneyStr && !"".equals(cwMoneyStr)){
				cwMoney=new BigDecimal(cwMoneyStr);
			}

			slFundQlide = slFundQlideService.get(fundQlideId);//流水记录
			if (slFundQlide.getPayMoney() != null) {
				slFundQlide.setDialMoney(slFundQlide.getPayMoney());
			} else {
				slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
			}
			
			//1.根据项目Id，期数、业务类别查询款项列表
			List<SlFundIntent> list=slFundIntentService.getComplexList(projectId,payintentPeriod,"SmallLoan");
			//2.循环集合，分别对账
			for(SlFundIntent s:list){
				if("loanInterest".equals(s.getFundType())){
					tempMoney=lxMoney;
				}else if("consultationMoney".equals(s.getFundType())){
					tempMoney=glMoney;
				}else if("serviceMoney".equals(s.getFundType())){
					tempMoney=cwMoney;
				}else{
					tempMoney=bjMoney;
				}
				//判断是否已经对过账
				if(null==s.getFactDate()){
					BigDecimal yingdui=s.getNotMoney();//应还金额
					
					//3.修改款项
					s.setAfterMoney(s.getAfterMoney().add(yingdui));//已对账金额
					s.setNotMoney(new BigDecimal(0.00));//未对账金额
					s.setFactDate(slFundQlide.getFactDate());
					
					SlFundDetail slFundDetail = null;
					
					//除本金放贷记录，其他都需要重新计算罚息
					if(!"principalLending".equals(s.getFundType())){
						//修改款项罚息金额
						s.setAccrualMoney(tempMoney);//罚息金额
						s.setFaxiAfterMoney(tempMoney);//罚息已对账
						
						//生成本款项、罚息、逾期对应的资金明细记录
						Long overDays=0L;
						if(null!=overDay && !"".equals(overDay)){
							overDays=Long.valueOf(overDay);
						}
						slFundDetail = createDetail(yingdui,s,overDays,tempMoney);//款项明细
						
						if(tempMoney.compareTo(new BigDecimal("0"))>0){
							BigDecimal afterMoney = createPunishDetail(tempMoney, s, overDays);
							yingdui=yingdui.add(afterMoney);//逾期明细
						}
						s.setPunishDays(overDays.intValue());
					}
					
					//4.修改流水
					slFundQlide.setAfterMoney(slFundQlide.getAfterMoney().add(yingdui));
					slFundQlide.setNotMoney(slFundQlide.getNotMoney().subtract(yingdui));
					slFundIntentService.merge(s);
					slFundQlideService.merge(slFundQlide);
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 生成罚息对应的资金明细
	 * @param money    金额
	 * @param s        款项
	 * @param overDay  逾期天数
	 * @param flag     用来区分是逾期(1)还是罚息(0)
	 */
	public BigDecimal createPunishDetail(BigDecimal money,SlFundIntent s,Long overDay){
		SlPunishInterest faxi=null;
		//查询罚息记录
		List<SlPunishInterest> listfaxi = slPunishInterestService.listbyisInitialorId(s.getFundIntentId());
		if(null==listfaxi || listfaxi.size()==0){
			faxi = new SlPunishInterest();
			faxi.setIntentDate(new Date());
			faxi.setIncomeMoney(money);
			faxi.setPayMoney(new BigDecimal("0"));
			faxi.setNotMoney(new BigDecimal("0"));
			faxi.setAfterMoney(money);
			faxi.setBusinessType(s.getBusinessType());
			faxi.setFundIntentId(s.getFundIntentId());
			faxi.setProjectId(s.getProjectId());
			faxi.setFundType(s.getFundType());
			faxi.setCompanyId(s.getCompanyId());
			faxi.setPunishDays(s.getPunishDays());
			faxi.setFactDate(s.getFactDate());
			
			//创建一条罚息资金明细
			SlPunishDetail slPunishDetail = new SlPunishDetail();
			slPunishDetail.setPunishInterestId(faxi.getPunishInterestId());
			slPunishDetail.setFundQlideId(slFundQlide.getFundQlideId());
			slPunishDetail.setAfterMoney(faxi.getAfterMoney());
			slPunishDetail.setFactDate(slFundQlide.getFactDate());
			AppUser user = ContextUtil.getCurrentUser();
			slPunishDetail.setCheckuser(user.getFullname());
			slPunishDetail.setCompanyId(faxi.getCompanyId());
			slPunishDetail.setOperTime(new Date());
			
			slPunishInterestService.save(faxi);
			slPunishDetailService.save(slPunishDetail);
		}else{
			faxi= listfaxi.get(0);
			//判断罚息是否已经对过账
			if(null==faxi.getFactDate()){//未对账
				if(null==faxi.getFlatMoney()){
					faxi.setFlatMoney(new BigDecimal("0"));
				}
				//比较罚息金额和罚息平账金额
				if(money.compareTo(faxi.getFlatMoney())==1){
					faxi.setAfterMoney(money.subtract(faxi.getFlatMoney()));
				}else{
					faxi.setAfterMoney(money);
				}
				faxi.setIntentDate(new Date());
				faxi.setFactDate(s.getFactDate());
				faxi.setNotMoney(new BigDecimal("0"));
				faxi.setPunishDays(Integer.valueOf(overDay.toString()));
				
				//创建一条罚息资金明细
				SlPunishDetail slPunishDetail = new SlPunishDetail();
				slPunishDetail.setPunishInterestId(faxi.getPunishInterestId());
				slPunishDetail.setFundQlideId(slFundQlide.getFundQlideId());
				slPunishDetail.setAfterMoney(faxi.getAfterMoney());
				slPunishDetail.setFactDate(slFundQlide.getFactDate());
				AppUser user = ContextUtil.getCurrentUser();
				slPunishDetail.setCheckuser(user.getFullname());
				slPunishDetail.setCompanyId(faxi.getCompanyId());
				slPunishDetail.setOperTime(new Date());
				slPunishDetailService.save(slPunishDetail);
			}else{
				BigDecimal alreadyMoney=new BigDecimal(0);//定义罚息已对账金额
				//比较当前罚息金额和罚息已对账金额
				if(money.compareTo(faxi.getAfterMoney())==1){
					faxi.setAfterMoney(money);
				}
				faxi.setNotMoney(money.subtract(faxi.getAfterMoney()));
			}
			faxi.setIncomeMoney(money);
			slPunishInterestService.merge(faxi);
		}
		
		return faxi.getAfterMoney();
	}
	
	/**
	 * 生成对应的资金明细
	 * @param money    金额
	 * @param s        款项
	 * @param overDay  逾期天数
	 */
	public SlFundDetail createDetail(BigDecimal money,SlFundIntent s,long overDay,BigDecimal overdueMoney){
		SlFundDetail slFundDetail = new SlFundDetail();
		slFundDetail.setOperTime(new Date());
		slFundDetail.setSlFundIntent(s);
		slFundDetail.setSlFundQlide(slFundQlide);
		slFundDetail.setAfterMoney(money);
		slFundDetail.setFactDate(slFundQlide.getFactDate());
		
		slFundDetail.setOverdueNum(overDay);
		AppUser user = ContextUtil.getCurrentUser();
		slFundDetail.setCheckuser(user.getFullname());
		slFundDetail.setCompanyId(s.getCompanyId());
		slFundDetail.setOverdueAccrual(overdueMoney);
		slFundDetailService.save(slFundDetail);
		return slFundDetail;
	}
	
	/**
	 * 综合款项台账对账是需要根据流水重新计算罚息
	 * @return
	 */
	public String reSignOverMoneyTo(){
		//TODO 待修改
		String projectId=this.getRequest().getParameter("projectId");//项目Id
		String payintentPeriod=this.getRequest().getParameter("payintentPeriod");//期数
		slFundQlide = slFundQlideService.get(fundQlideId);//流水记录
		
		Map<String,BigDecimal> map=new HashMap<String,BigDecimal>();
		//1.根据项目Id，期数、业务类别查询款项列表
		List<SlFundIntent> list=slFundIntentService.getComplexList(projectId,payintentPeriod,"SmallLoan");
		//2.循环集合，分别对账
		BigDecimal bjMoney = new BigDecimal(0.00);//本金逾期金额
		BigDecimal lxMoney = new BigDecimal(0.00);//利息逾期金额
		BigDecimal glMoney = new BigDecimal(0.00);//管理咨询费逾期金额
		BigDecimal cwMoney = new BigDecimal(0.00);//财务服务费逾期金额
		for(SlFundIntent s:list){
			reSignOverMoney(s,slFundQlide, map);
			if("loanInterest".equals(s.getFundType())){
				lxMoney=map.get("overdueMoney");
			}else if("consultationMoney".equals(s.getFundType())){
				glMoney=map.get("overdueMoney");
			}else if("serviceMoney".equals(s.getFundType())){
				cwMoney=map.get("overdueMoney");
			}else{
				bjMoney=map.get("overdueMoney");
			}
		}
		map.put("bjMoney",bjMoney);
		map.put("lxMoney",lxMoney);
		map.put("glMoney",glMoney);
		map.put("cwMoney",cwMoney);
		map.remove("overdueMoney");
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 根据流水重新计算罚息、逾期金额
	 * @param owe
	 * @return
	 */
	public void reSignOverMoney(SlFundIntent owe,SlFundQlide slFundQlide,Map<String,BigDecimal> map) {
//		SlSmallloanProject loan = slSmallloanProjectService.get(owe.getProjectId());
		BpFundProject loan=bpFundProjectService.getByProjectId(owe.getProjectId(),Short.valueOf("0"));
		BigDecimal overdueMoney = new BigDecimal(0.00);//逾期金额
		Date overdueDate=new Date();
		try {
			//判断是否逾期
			long day = compare_date(DateUtil.parseDate(owe.getGraceDay()),slFundQlide.getFactDate());
			if ((day == -1 || day == 0) && (null == owe.getFactDate())) {
				owe.setIsOverdue("否");
			} else {
				owe.setIsOverdue("是");
			}
			BigDecimal baseOverMoney = owe.getNotMoney();//逾期本金
			
			//3.获得逾期利率
			owe.setOverdueRate(loan.getOverdueRate());
			if (null == owe.getOverdueRate()) {
				owe.setOverdueRate(new BigDecimal(0));
			}
			//5.计算当前款项的罚息金额、逾期金额
			if (owe.getNotMoney().compareTo(new BigDecimal(0)) == 1) {
				Date newBegin=owe.getIntentDate();
				if(null!=owe.getGraceDay() && !"".equals(owe.getGraceDay())){//宽限至
					newBegin=DateUtil.parseDate(owe.getGraceDay());
				}
				if (DateUtil.compare_date(overdueDate,newBegin) == -1) {
					//计算逾期天数
					long sortday = DateUtil.compare_date(owe.getIntentDate(),slFundQlide.getFactDate());
					if(sortday==-1){
						owe.setPunishDays(0);
						map.put("overDay",new BigDecimal(0));//逾期天数
					}else{
						BigDecimal day1 = new BigDecimal(sortday);
						owe.setPunishDays(day1.intValue());
						//计算逾期金额
						overdueMoney = owe.getOverdueRate().multiply(day1).multiply(baseOverMoney).divide(new BigDecimal(100), 2);
						map.put("overDay",day1);//逾期天数
					}
				}
			}
			map.put("overdueMoney",overdueMoney);//逾期金额
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//TODO ---------------------------款项对账结束---------------------------
	
	/**
	 * 显示列表
	 */
	public String list() { // listbySmallLoan and listbyFinancing
		try {
			List<SlFundIntent> list = null;
			int size = 0;
			String fundResource=this.getRequest().getParameter("fundResource");
			String preceptId=this.getRequest().getParameter("preceptId");
			String bidPlanId = this.getRequest().getParameter("bidPlanId");
			String slFundIntentId = this.getRequest().getParameter("slFundIntentId");
			if (flag1 == 1) { // 正常从数据库获得listslSmallloanProject==null为款项确认，flag1==1尽职调查
//				list = slFundIntentService.getByProjectAsc(projectId,businessType,fundResource);
				//首先要根据bidPlanId加载数据，其次是根据preceptId加载数据，最后在
				if(slFundIntentId!=null&&!"".equals(slFundIntentId)){
					list = new ArrayList<SlFundIntent>();
					SlFundIntent intent = slFundIntentService.get(Long.parseLong(slFundIntentId));
					list.add(intent);
				}else if(null!=bidPlanId&&!"".equals(bidPlanId)&&!"undefined".equals(bidPlanId)){
					list = slFundIntentService.getListByBidPlanId(Long.parseLong(bidPlanId));
				}else if(null==preceptId||"".equals(preceptId)){
					list = slFundIntentService.getByProjectId(projectId, businessType);
				}else if(null!=preceptId && !"".equals(preceptId)){
					list = slFundIntentService.getbyPreceptId(Long.valueOf(preceptId));
				}
				if (list != null) {
					for (SlFundIntent l : list) {
						slFundIntentService.evict(l);
						List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
								.getByDicKey(l.getFundType());
						if(dictionaryIndependent!=null&&dictionaryIndependent.size()!=0)
								l.setFundTypeName(dictionaryIndependent.get(0)
								.getItemValue());
						if (businessType.equals("SmallLoan")) {
							SlSmallloanProject loan = slSmallloanProjectService
									.get(l.getProjectId());
							if (l.getFundType().equals("principalRepayment")) {
								l.setOverdueRate(loan.getOverdueRateLoan());
							} else {
								l.setOverdueRate(loan.getOverdueRate());
							}
						}
						if (businessType.equals("Financing")) {
							FlFinancingProject flFinancingProject = flFinancingProjectService
									.get(l.getProjectId());
							l.setOverdueRate(flFinancingProject
									.getOverdueRate());
						}
						if (businessType.equals("Pawn")) {
							PlPawnProject pawn = plPawnProjectService.get(l
									.getProjectId());
							if (l.getFundType()
									.equals("pawnPrincipalRepayment")) {
								l.setOverdueRate(pawn.getOverdueRateLoan());
							} else {
								l.setOverdueRate(pawn.getOverdueRate());
							}
						}
						BigDecimal lin = new BigDecimal(0.00);
						if (compare_date(l.getIntentDate(), new Date()) > 0) {
							l.setIsOverdue("是");
						} else {

							l.setIsOverdue("否");
						}
						if (l.getIncomeMoney().compareTo(lin) == 0) {
							l.setPayInMoney(l.getPayMoney());
						} else {
							l.setPayInMoney(l.getIncomeMoney());
						}
					}
					size = list.size();

				} else {
					size = 0;
				}
			}
			if (ownBpFundProject != null && flag1 == 0) {
				 // 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("SmallLoan")) {
					if (AppUtil.getInterest().equals("0")) {
						FundIntentGenerate sundIntentGeneraten=new FundIntentGenerate(ownBpFundProject,null,"yes");
						list=sundIntentGeneraten.createBorrowerSlFundIntent();
						/*list = FundIntentListPro3
								.getFundIntentDefaultList(
										calcutype,
										platFormBpFundProject.getAccrualtype(),
										platFormBpFundProject
												.getIsPreposePayAccrual(),
												platFormBpFundProject.getPayaccrualType(),
												platFormBpFundProject
												.getDayAccrualRate()
												.divide(BigDecimal.valueOf(100)),
												platFormBpFundProject.getProjectMoney(),
										sd.format(platFormBpFundProject
												.getStartDate()),
												platFormBpFundProject.getIntentDate() == null ? null
												: sd.format(platFormBpFundProject
														.getIntentDate()),
														platFormBpFundProject
												.getDayManagementConsultingOfRate()
												.divide(BigDecimal.valueOf(100)),
												platFormBpFundProject
												.getDayFinanceServiceOfRate()
												.divide(BigDecimal.valueOf(100)),
												platFormBpFundProject.getPayintentPeriod(),
												platFormBpFundProject.getIsStartDatePay(),
												platFormBpFundProject
												.getPayintentPerioDate(),
												platFormBpFundProject
												.getDayOfEveryPeriod(),
										"yes",
										platFormBpFundProject
												.getIsPreposePayConsultingCheck(),
												platFormBpFundProject
												.getIsInterestByOneTime(),
												platFormBpFundProject.getDateMode());*/
					} else {
						list = FundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										ownBpFundProject.getAccrualtype(),
										ownBpFundProject.getIsPreposePayAccrual(),
										ownBpFundProject.getPayaccrualType(),
										ownBpFundProject.getDayAccrualRate().divide(BigDecimal.valueOf(100)),
										ownBpFundProject.getProjectMoney(),
										sd.format(ownBpFundProject.getStartDate()),
										ownBpFundProject.getIntentDate() == null ? null
												: sd.format(ownBpFundProject.getIntentDate()),
										ownBpFundProject.getDayManagementConsultingOfRate().divide(BigDecimal.valueOf(100)),
										ownBpFundProject.getDayFinanceServiceOfRate().divide(BigDecimal.valueOf(100)),
										ownBpFundProject.getPayintentPeriod(),
										ownBpFundProject.getIsStartDatePay(),
										ownBpFundProject.getPayintentPerioDate(),
										ownBpFundProject.getDayOfEveryPeriod(),
										"yes",
										ownBpFundProject.getIsPreposePayConsultingCheck(),
										ownBpFundProject.getIsInterestByOneTime(),
										ownBpFundProject.getDateMode());
					}
				}

				/*for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0 || t.getFundType().equals("principalLending")) {
						list.add(t);
					}
				}
				size = list.size();*/
			
				
			}
			if (platFormBpFundProject != null && flag1 == 0&&"invest".equals(fundType)) { // 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				list=new ArrayList<SlFundIntent>();
				if (calcutype.equals("SmallLoan")) {
					if (AppUtil.getInterest().equals("0")) {
						String investId=this.getRequest().getParameter("platFormBpFundProject.investorIds");
						//String[] ids=investId.split(",");
						List<InvestPersonInfo> li = investPersonInfoService.getByMoneyPlanId(Long.parseLong(preceptId));
						if(li!=null&&li.size()!=0)
						for(InvestPersonInfo personInfo: li){
							if(null==personInfo){
								continue;//sameprincipalsameInterest
							}
							List<SlFundIntent> slList  = new ArrayList<SlFundIntent>();
							//InvestPersonInfo personInfo=investPersonInfoService.get(Long.valueOf(ids[i]));
							if(platFormBpFundProject.getAccrualtype().equals("sameprincipalsameInterest")){
								FundIntentFactory factory = new SameInterestFactory();
								platFormBpFundProject.setStartDate(platFormBpFundProject.getStartInterestDate());
								FundIntent fund = factory.createFund();
								if(personInfo!=null)
								slList = fund.createList(platFormBpFundProject, personInfo);
							}else{
								slList =  FundIntentListPro3.getFundIntentDefaultList(
										calcutype,
										platFormBpFundProject.getAccrualtype(),
										platFormBpFundProject.getIsPreposePayAccrual(), 
										platFormBpFundProject.getPayaccrualType(),
										platFormBpFundProject.getAccrual().divide(new BigDecimal(100)), 
										personInfo.getInvestMoney(), 
										sd.format(platFormBpFundProject.getStartInterestDate()),
										sd.format(platFormBpFundProject.getIntentDate()),
										platFormBpFundProject.getManagementConsultingOfRate().divide(BigDecimal.valueOf(100)),
										platFormBpFundProject.getFinanceServiceOfRate().divide(BigDecimal.valueOf(100)),
										platFormBpFundProject.getPayintentPeriod(),
										platFormBpFundProject.getIsStartDatePay(),
										platFormBpFundProject.getPayintentPerioDate(),
										platFormBpFundProject.getDayOfEveryPeriod(),
										personInfo.getInvestPersonId(),
										personInfo.getInvestPersonName(),
										0);
							}
							/*slList =  FundIntentListPro3.getFundIntentDefaultList(
									calcutype,
									platFormBpFundProject.getAccrualtype(),
									platFormBpFundProject.getIsPreposePayAccrual(), 
									platFormBpFundProject.getPayaccrualType(),
									platFormBpFundProject.getAccrualnew().divide(new BigDecimal(100)), 
									personInfo.getInvestMoney(), 
									sd.format(platFormBpFundProject.getStartDate()),
									sd.format(platFormBpFundProject.getIntentDate()),
									platFormBpFundProject.getManagementConsultingOfRate().divide(BigDecimal.valueOf(100)),
									platFormBpFundProject.getFinanceServiceOfRate().divide(BigDecimal.valueOf(100)),
									platFormBpFundProject.getPayintentPeriod(),
									platFormBpFundProject.getIsStartDatePay(),
									platFormBpFundProject.getPayintentPerioDate(),
									platFormBpFundProject.getDayOfEveryPeriod(),
									personInfo.getInvestPersonId(),
									personInfo.getInvestPersonName(),
									0);*/
							for (SlFundIntent s : slList) {
								s.setBusinessType(calcutype);
								s.setFundResource(personInfo.getFundResource().toString());
								list.add(s);
							}
						}
					} else {
						list = FundIntentListProtw.getFundIntentDefaultList(
										calcutype,
										platFormBpFundProject.getAccrualtype(),
										platFormBpFundProject.getIsPreposePayAccrual(),
										platFormBpFundProject.getPayaccrualType(),
										platFormBpFundProject.getDayAccrualRate().divide(BigDecimal.valueOf(100)),
										platFormBpFundProject.getProjectMoney(),
										sd.format(platFormBpFundProject.getStartDate()),
										platFormBpFundProject.getIntentDate() == null ? null: sd.format(platFormBpFundProject.getIntentDate()),
										platFormBpFundProject.getDayManagementConsultingOfRate().divide(BigDecimal.valueOf(100)),
										platFormBpFundProject.getDayFinanceServiceOfRate().divide(BigDecimal.valueOf(100)),
										platFormBpFundProject.getPayintentPeriod(),
										platFormBpFundProject.getIsStartDatePay(),
										platFormBpFundProject.getPayintentPerioDate(),
										platFormBpFundProject.getDayOfEveryPeriod(),
										isHaveLending,
										platFormBpFundProject.getIsPreposePayConsultingCheck(),
										platFormBpFundProject.getIsInterestByOneTime(),
										platFormBpFundProject.getDateMode());
					}
				}
				/*for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
					if("0".equals(fundResource)){
						s.setFundResource("企业");
					}else if("1".equals(fundResource)){
						s.setFundResource("个人");
					}
				}*/
				List<SlFundIntent> listafter = slFundIntentService.getByProjectId(projectId, businessType,fundResource);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0 || t.getFundType().equals("principalLending")) {
						list.add(t);
					}
				}
				size = list.size();
			}
			if (platFormBpFundProject != null && flag1 == 0&&"custer".equals(fundType)) { // 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("SmallLoan")) {
					if (AppUtil.getInterest().equals("0")) {
						FundIntentGenerate sundIntentGeneraten=new FundIntentGenerate(platFormBpFundProject,null,"yes");
						list=sundIntentGeneraten.createBorrowerSlFundIntent();
						/*list = FundIntentListPro3
								.getFundIntentDefaultList(
										calcutype,
										platFormBpFundProject.getAccrualtype(),
										platFormBpFundProject
												.getIsPreposePayAccrual(),
												platFormBpFundProject.getPayaccrualType(),
												platFormBpFundProject
												.getDayAccrualRate()
												.divide(BigDecimal.valueOf(100)),
												platFormBpFundProject.getProjectMoney(),
										sd.format(platFormBpFundProject
												.getStartDate()),
												platFormBpFundProject.getIntentDate() == null ? null
												: sd.format(platFormBpFundProject
														.getIntentDate()),
														platFormBpFundProject
												.getDayManagementConsultingOfRate()
												.divide(BigDecimal.valueOf(100)),
												platFormBpFundProject
												.getDayFinanceServiceOfRate()
												.divide(BigDecimal.valueOf(100)),
												platFormBpFundProject.getPayintentPeriod(),
												platFormBpFundProject.getIsStartDatePay(),
												platFormBpFundProject
												.getPayintentPerioDate(),
												platFormBpFundProject
												.getDayOfEveryPeriod(),
										"yes",
										platFormBpFundProject
												.getIsPreposePayConsultingCheck(),
												platFormBpFundProject
												.getIsInterestByOneTime(),
												platFormBpFundProject.getDateMode());*/
					} else {
						list = FundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										platFormBpFundProject.getAccrualtype(),
										platFormBpFundProject
												.getIsPreposePayAccrual(),
												platFormBpFundProject.getPayaccrualType(),
												platFormBpFundProject
												.getDayAccrualRate()
												.divide(BigDecimal.valueOf(100)),
												platFormBpFundProject.getProjectMoney(),
										sd.format(platFormBpFundProject
												.getStartDate()),
												platFormBpFundProject.getIntentDate() == null ? null
												: sd.format(platFormBpFundProject
														.getIntentDate()),
														platFormBpFundProject
												.getDayManagementConsultingOfRate()
												.divide(BigDecimal.valueOf(100)),
												platFormBpFundProject
												.getDayFinanceServiceOfRate()
												.divide(BigDecimal.valueOf(100)),
												platFormBpFundProject.getPayintentPeriod(),
												platFormBpFundProject.getIsStartDatePay(),
												platFormBpFundProject
												.getPayintentPerioDate(),
												platFormBpFundProject
												.getDayOfEveryPeriod(),
										"yes",
										platFormBpFundProject
												.getIsPreposePayConsultingCheck(),
												platFormBpFundProject
												.getIsInterestByOneTime(),
												platFormBpFundProject.getDateMode());
					}
				}

				/*for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0 || t.getFundType().equals("principalLending")) {
						list.add(t);
					}
				}
				size = list.size();*/
			}
			if (slSmallloanProject != null && flag1 == 0) { // 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("SmallLoan")) {
					if (AppUtil.getInterest().equals("0")) {
						String flowType = configMap.get("flowType").toString();
						if("cyjrCreditFlow".equals(flowType)){
							list = FundIntentListPro4.getFundIntentDefaultList(
									calcutype,
									slSmallloanProject.getAccrualtype(),
									slSmallloanProject
									.getIsPreposePayAccrual(),
									slSmallloanProject.getPayaccrualType(),
									slSmallloanProject
									.getDayAccrualRate()
									.divide(BigDecimal.valueOf(100)),
									slSmallloanProject.getProjectMoney(),
									sd.format(slSmallloanProject
											.getStartDate()),
											slSmallloanProject.getIntentDate() == null ? null
													: sd.format(slSmallloanProject
															.getIntentDate()),
															slSmallloanProject
															.getDayManagementConsultingOfRate()
															.divide(BigDecimal.valueOf(100)),
															slSmallloanProject
															.getDayFinanceServiceOfRate()
															.divide(BigDecimal.valueOf(100)),
															slSmallloanProject.getPayintentPeriod(),
															slSmallloanProject.getIsStartDatePay(),
															slSmallloanProject
															.getPayintentPerioDate(),
															slSmallloanProject
															.getDayOfEveryPeriod(),
															"no",
															slSmallloanProject
															.getIsPreposePayConsultingCheck(),
															slSmallloanProject
															.getIsInterestByOneTime(),
															slSmallloanProject.getDateMode());
						}else if("dltcCreditFlow".equals(flowType)){
							list = FundIntentListPro5.getFundIntentDefaultList(
									calcutype,
									slSmallloanProject.getAccrualtype(),
									slSmallloanProject
									.getIsPreposePayAccrual(),
									slSmallloanProject.getPayaccrualType(),
									slSmallloanProject
									.getDayAccrualRate()
									.divide(BigDecimal.valueOf(100)),
									slSmallloanProject.getProjectMoney(),
									sd.format(slSmallloanProject
											.getStartDate()),
											slSmallloanProject.getIntentDate() == null ? null
													: sd.format(slSmallloanProject
															.getIntentDate()),
															slSmallloanProject
															.getDayManagementConsultingOfRate()
															.divide(BigDecimal.valueOf(100)),
															slSmallloanProject
															.getDayFinanceServiceOfRate()
															.divide(BigDecimal.valueOf(100)),
															slSmallloanProject.getPayintentPeriod(),
															slSmallloanProject.getIsStartDatePay(),
															slSmallloanProject
															.getPayintentPerioDate(),
															slSmallloanProject
															.getDayOfEveryPeriod(),
															"yes",
															slSmallloanProject
															.getIsPreposePayConsultingCheck(),
															slSmallloanProject
															.getIsInterestByOneTime(),
															slSmallloanProject.getDateMode());
						}else{//大连天储等项目生成款项计划
							list = FundIntentListPro4.getFundIntentDefaultList(
									calcutype,
									slSmallloanProject.getAccrualtype(),
									slSmallloanProject
									.getIsPreposePayAccrual(),
									slSmallloanProject.getPayaccrualType(),
									slSmallloanProject
									.getDayAccrualRate()
									.divide(BigDecimal.valueOf(100)),
									slSmallloanProject.getProjectMoney(),
									sd.format(slSmallloanProject
											.getStartDate()),
											slSmallloanProject.getIntentDate() == null ? null
													: sd.format(slSmallloanProject
															.getIntentDate()),
															slSmallloanProject
															.getDayManagementConsultingOfRate()
															.divide(BigDecimal.valueOf(100)),
															slSmallloanProject
															.getDayFinanceServiceOfRate()
															.divide(BigDecimal.valueOf(100)),
															slSmallloanProject.getPayintentPeriod(),
															slSmallloanProject.getIsStartDatePay(),
															slSmallloanProject
															.getPayintentPerioDate(),
															slSmallloanProject
															.getDayOfEveryPeriod(),
															"yes",
															slSmallloanProject
															.getIsPreposePayConsultingCheck(),
															slSmallloanProject
															.getIsInterestByOneTime(),
															slSmallloanProject.getDateMode());
						}
					if(slSmallloanProject.getRiskRate()==null||slSmallloanProject.getRiskRate().equals(new BigDecimal(0))){
						
					}else{
						 SlFundIntent sfi  = FundIntentListPro3.calculslfundintent(
								 FundIntentListPro3.riskRate,            // 资金类型
								    DateUtil.parseDate(sd.format(slSmallloanProject
											.getStartDate()),"yyyy-MM-dd"),  //计划日期
									BigDecimal.valueOf(0),                                                // 支出金额                                              // 支出金额
									slSmallloanProject.getProjectMoney().multiply(slSmallloanProject.getRiskRate()).divide(new BigDecimal(100), 100, BigDecimal.ROUND_HALF_UP),
									0,
									slSmallloanProject.getIsPreposePayAccrual(),
									slSmallloanProject.getPayaccrualType(),
									slSmallloanProject.getDayOfEveryPeriod(),
									sd.format(slSmallloanProject
											.getStartDate()),
									slSmallloanProject.getIsInterestByOneTime(),
									sd.format(slSmallloanProject
											.getIntentDate()),
									slSmallloanProject.getIsStartDatePay(),
									slSmallloanProject.getIsStartDatePay().equals("1")?String.valueOf(slSmallloanProject.getPayintentPerioDate()):null,
									slSmallloanProject.getAccrualtype()          // 收入金额
								);	
						 sfi.setInterestStarTime(slSmallloanProject.getStartDate());
						 sfi.setInterestEndTime(slSmallloanProject.getStartDate());
						 list.add(1, sfi);
					}

					} else {
						list = FundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										slSmallloanProject.getAccrualtype(),
										slSmallloanProject
												.getIsPreposePayAccrual(),
										slSmallloanProject.getPayaccrualType(),
										slSmallloanProject
												.getDayAccrualRate()
												.divide(BigDecimal.valueOf(100)),
										slSmallloanProject.getProjectMoney(),
										sd.format(slSmallloanProject
												.getStartDate()),
										slSmallloanProject.getIntentDate() == null ? null
												: sd.format(slSmallloanProject
														.getIntentDate()),
										slSmallloanProject
												.getDayManagementConsultingOfRate()
												.divide(BigDecimal.valueOf(100)),
										slSmallloanProject
												.getDayFinanceServiceOfRate()
												.divide(BigDecimal.valueOf(100)),
										slSmallloanProject.getPayintentPeriod(),
										slSmallloanProject.getIsStartDatePay(),
										slSmallloanProject
												.getPayintentPerioDate(),
										slSmallloanProject
												.getDayOfEveryPeriod(),
										isHaveLending,
										slSmallloanProject
												.getIsPreposePayConsultingCheck(),
										slSmallloanProject
												.getIsInterestByOneTime(),
										slSmallloanProject.getDateMode());
					}
				}

				for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0 || t.getFundType().equals("principalLending")) {
						list.add(t);
					}
				}
				size = list.size();
			}
			if (flFinancingProject != null && flag1 == 0) { // 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("Financing")) {
					if (AppUtil.getInterest().equals("0")) {
						list = FinanceFundIntentListPro
								.getFundIntentDefaultList(
										calcutype,
										flFinancingProject.getAccrualtype(),
										flFinancingProject
												.getIsPreposePayAccrual(),
										flFinancingProject.getPayaccrualType(),
										flFinancingProject
												.getDayAccrualRate()
												.divide(BigDecimal.valueOf(100)),
										flFinancingProject.getProjectMoney(),
										sd.format(flFinancingProject
												.getStartDate()),
										flFinancingProject.getIntentDate() == null ? null
												: sd.format(flFinancingProject
														.getIntentDate()),
										new BigDecimal(0),
										new BigDecimal(0),
										flFinancingProject.getPayintentPeriod(),
										flFinancingProject.getIsStartDatePay(),
										flFinancingProject
												.getPayintentPerioDate(),
										flFinancingProject
												.getDayOfEveryPeriod(),
										isHaveLending, null, flFinancingProject
												.getIsInterestByOneTime(),
										flFinancingProject.getDateMode());
					} else {
						list = FinanceFundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										flFinancingProject.getAccrualtype(),
										flFinancingProject
												.getIsPreposePayAccrual(),
										flFinancingProject.getPayaccrualType(),
										flFinancingProject
												.getDayAccrualRate()
												.divide(BigDecimal.valueOf(100)),
										flFinancingProject.getProjectMoney(),
										sd.format(flFinancingProject
												.getStartDate()),
										flFinancingProject.getIntentDate() == null ? null
												: sd.format(flFinancingProject
														.getIntentDate()),
										new BigDecimal(0),
										new BigDecimal(0),
										flFinancingProject.getPayintentPeriod(),
										flFinancingProject.getIsStartDatePay(),
										flFinancingProject
												.getPayintentPerioDate(),
										flFinancingProject
												.getDayOfEveryPeriod(),
										isHaveLending, null, flFinancingProject
												.getIsInterestByOneTime(),
										flFinancingProject.getDateMode());
					}
				}

				for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0) {
						list.add(t);
					}
				}
				size = list.size();
			}
			if (plPawnProject != null && flag1 == 0) {
				// 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("Pawn")) {
					List<SlFundIntent> slist = slFundIntentService
							.getListByFundType(projectId, businessType,
									"pawnPrincipalLending");
					BigDecimal money = null;
					if (null != slist && slist.size() > 0) {
						SlFundIntent s = slist.get(0);
						money = s.getPayMoney();
					}
					if (AppUtil.getInterest().equals("0")) {
						list = PawnFundIntentListPro.getFundIntentDefaultList(
								calcutype, "singleInterest", plPawnProject
										.getIsPreposePayAccrual(),
								plPawnProject.getPayaccrualType(),
								plPawnProject.getAccrual().divide(
										new BigDecimal(30), 10,
										BigDecimal.ROUND_HALF_UP).divide(
										BigDecimal.valueOf(100)),
								money == null ? plPawnProject.getProjectMoney()
										: money, sd.format(plPawnProject
										.getStartDate()), plPawnProject
										.getIntentDate() == null ? null : sd
										.format(plPawnProject.getIntentDate()),
								plPawnProject.getMonthFeeRate().divide(
										new BigDecimal(30), 10,
										BigDecimal.ROUND_HALF_UP).divide(
										BigDecimal.valueOf(100)),
								new BigDecimal(0), plPawnProject
										.getPayintentPeriod(), plPawnProject
										.getIsStartDatePay(), plPawnProject
										.getPayintentPerioDate(), plPawnProject
										.getDayOfEveryPeriod(), isHaveLending,
								null, plPawnProject.getIsInterestByOneTime(),
								null);
					} else {
						list = PawnFundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										"singleInterest",
										plPawnProject.getIsPreposePayAccrual(),
										plPawnProject.getPayaccrualType(),
										plPawnProject
												.getAccrual()
												.divide(
														new BigDecimal(30),
														10,
														BigDecimal.ROUND_HALF_UP)
												.divide(BigDecimal.valueOf(100)),
										money == null ? plPawnProject
												.getProjectMoney() : money,
										sd.format(plPawnProject.getStartDate()),
										plPawnProject.getIntentDate() == null ? null
												: sd.format(plPawnProject
														.getIntentDate()),
										plPawnProject
												.getMonthFeeRate()
												.divide(
														new BigDecimal(30),
														10,
														BigDecimal.ROUND_HALF_UP)
												.divide(BigDecimal.valueOf(100)),
										new BigDecimal(0), plPawnProject
												.getPayintentPeriod(),
										plPawnProject.getIsStartDatePay(),
										plPawnProject.getPayintentPerioDate(),
										plPawnProject.getDayOfEveryPeriod(),
										isHaveLending, null, plPawnProject
												.getIsInterestByOneTime(), null);
					}
				}

				for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0
							|| t.getFundType().equals("pawnPrincipalLending")) {
						list.add(t);
					}
				}
				size = list.size();

			}
			if (flLeaseFinanceProject != null && flag1 == 0) {
				// 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("LeaseFinance")) {

					if (AppUtil.getInterest().equals("0")) {
						list = LeaseFinanceFundIntentListPro
								.getFundIntentDefaultList(
										calcutype,
										flLeaseFinanceProject.getAccrualtype(),
										0,
										flLeaseFinanceProject
												.getPayaccrualType(),
										flLeaseFinanceProject
												.getRentalRate()
												.divide(BigDecimal.valueOf(100)),
										flLeaseFinanceProject.getProjectMoney(),
										sd.format(flLeaseFinanceProject
												.getStartDate()),
										flLeaseFinanceProject.getIntentDate() == null ? null
												: sd
														.format(flLeaseFinanceProject
																.getIntentDate()),
										flLeaseFinanceProject
												.getRentalFeeRate()
												.divide(BigDecimal.valueOf(100)),
										flLeaseFinanceProject
												.getLeaseDepositMoney(),
										flLeaseFinanceProject
												.getPayintentPeriod(),
										flLeaseFinanceProject
												.getIsStartDatePay(),
										flLeaseFinanceProject
												.getPayintentPerioDate(),
										flLeaseFinanceProject
												.getDayOfEveryPeriod(),
										isHaveLending, null, 0, null,
										flLeaseFinanceProject
												.getLeaseRetentionFeeMoney(),
										flLeaseFinanceProject
												.getFeePayaccrualType(),
										flLeaseFinanceProject
												.getEachRentalReceivable());
					} else {
						list = LeaseFinanceFundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										flLeaseFinanceProject.getAccrualtype(),
										0,
										flLeaseFinanceProject
												.getPayaccrualType(),
										flLeaseFinanceProject
												.getRentalRate()
												.divide(BigDecimal.valueOf(100)),
										flLeaseFinanceProject.getProjectMoney(),
										sd.format(flLeaseFinanceProject
												.getStartDate()),
										flLeaseFinanceProject.getIntentDate() == null ? null
												: sd
														.format(flLeaseFinanceProject
																.getIntentDate()),
										flLeaseFinanceProject
												.getRentalFeeRate()
												.divide(BigDecimal.valueOf(100)),
										flLeaseFinanceProject
												.getLeaseDepositMoney(),
										flLeaseFinanceProject
												.getPayintentPeriod(),
										flLeaseFinanceProject
												.getIsStartDatePay(),
										flLeaseFinanceProject
												.getPayintentPerioDate(),
										flLeaseFinanceProject
												.getDayOfEveryPeriod(),
										isHaveLending, null, 0, null,
										flLeaseFinanceProject
												.getLeaseRetentionFeeMoney(),
										flLeaseFinanceProject
												.getFeePayaccrualType(),
										flLeaseFinanceProject
												.getEachRentalReceivable());
					}
				}

				for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0) {
						list.add(t);
					}
				}
				size = list.size();

			}
/*			List<SlFundIntent> list1 = new ArrayList<SlFundIntent>();
			if (relateIntentOrCharge == null
					|| relateIntentOrCharge.equals("all")) { // 取全部
				if(null!=list&&list.size()!=0)
				for (SlFundIntent l : list) {
					List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
							.getByDicKey(l.getFundType());
					l.setFundTypeName(dictionaryIndependent.get(0)
							.getItemValue());
					list1.add(l);
				}
			}
			if (relateIntentOrCharge != null
					&& relateIntentOrCharge.equals("charge")) { // 取费用相关
				for (SlFundIntent l : list) {
					if (businessType.equals("SmallLoan")) {
						if (l.getFundType().equals("serviceMoney")
								|| l.getFundType().equals("consultationMoney")
								|| l.getFundType().equals("loanInterest")) {
							list1.add(l);
						}
					} else if (businessType.equals("Financing")) {
						if (l.getFundType().equals("FinancingInterest")
								|| l.getFundType().equals(
										"financingconsultationMoney")
								|| l.getFundType().equals(
										"financingserviceMoney")) {
							list1.add(l);
						}
					} else if (businessType.equals("Pawn")) {
						if (l.getFundType().equals("pawnServiceMoney")
								|| l.getFundType().equals("pawnLoanInterest")) {
							list1.add(l);
						}
					} else if (businessType.equals("LeaseFinance")) {
						if (l.getFundType().equals("rentalFeeCharging")
								|| l.getFundType().equals("leaseFeeCharging")
								|| l.getFundType().equals(
										"leaseObjectRetentionFee")) {
							list1.add(l);
						}
					}
				}
			}
			if (relateIntentOrCharge != null
					&& relateIntentOrCharge.equals("intent")) { // 取本金相关
				for (SlFundIntent l : list) {
					if (businessType.equals("SmallLoan")) {
						if (l.getFundType().equals("principalRepayment")
								|| l.getFundType().equals("principalLending")) {
							list1.add(l);
						}
					} else if (businessType.equals("Financing")) {
						if (l.getFundType().equals("Financingborrow")
								|| l.getFundType().equals("FinancingRepay")) {
							list1.add(l);
						}
					} else if (businessType.equals("Pawn")) {
						if (l.getFundType().equals("pawnPrincipalRepayment")
								|| l.getFundType().equals(
										"pawnPrincipalLending")) {
							list1.add(l);
						}
					} else if (businessType.equals("LeaseFinance")) {
						if (l.getFundType().equals("marginCollection")
								|| l.getFundType().equals(
										"leasePrincipalRepayment")
								|| l.getFundType().equals("rentalCostsPaid")
								|| l.getFundType().equals("marginRefund")) {
							list1.add(l);
						}
					}
				}
			}
			if (relateIntentOrCharge != null
					&& relateIntentOrCharge.equals("none")) { // 取空

			}*/

			if (list != null) {
				size = list.size();
			}

			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(size).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
					"factDate", "interestStarTime", "interestEndTime");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"intentDate", "factDate", "interestStarTime",
					"interestEndTime" });
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	public String list2() { // listbySmallLoan and listbyFinancing
		try {
			List<SlFundIntent> list = null;
			int size = 0;
			if (flag1 == 1) { // 正常从数据库获得listslSmallloanProject==null为款项确认，flag1==1尽职调查

				list = slFundIntentService.getByProjectAsc(projectId,businessType,null);
				if (list != null) {
					for (SlFundIntent l : list) {
						slFundIntentService.evict(l);
						List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
								.getByDicKey(l.getFundType());
						l.setFundTypeName(dictionaryIndependent.get(0)
								.getItemValue());
						if (businessType.equals("SmallLoan")) {
							SlSmallloanProject loan = slSmallloanProjectService
									.get(l.getProjectId());
							if (l.getFundType().equals("principalRepayment")) {
								l.setOverdueRate(loan.getOverdueRateLoan());
							} else {
								l.setOverdueRate(loan.getOverdueRate());
							}
						}
						if (businessType.equals("Financing")) {
							FlFinancingProject flFinancingProject = flFinancingProjectService
									.get(l.getProjectId());
							l.setOverdueRate(flFinancingProject
									.getOverdueRate());
						}
						if (businessType.equals("Pawn")) {
							PlPawnProject pawn = plPawnProjectService.get(l
									.getProjectId());
							if (l.getFundType()
									.equals("pawnPrincipalRepayment")) {
								l.setOverdueRate(pawn.getOverdueRateLoan());
							} else {
								l.setOverdueRate(pawn.getOverdueRate());
							}
						}

						// if(l.getNotMoney().compareTo(new BigDecimal(0))==1){
						// BigDecimal sortoverdueMoney=new BigDecimal(0.00);
						// long sortday=new Long(0);
						// SimpleDateFormat from = new
						// SimpleDateFormat("yyyy-MM-dd 00:00:00");
						// String newdate= from.format(new Date());
						// Date d = from.parse(newdate);
						// if(compare_date(d,l.getIntentDate())==-1 &&
						// l.getOverdueRate() !=null){
						// Date b=new Date();
						// Long a=compare_date(new Date(),l.getIntentDate());
						// sortday = compare_date(l.getIntentDate(),new
						// Date())+1;
						// BigDecimal day1 = new BigDecimal(sortday);
						// BigDecimal OverdueMoney =
						// l.getOverdueRate().multiply(day1);
						// sortoverdueMoney =
						// OverdueMoney.multiply(l.getNotMoney()).divide(new
						// BigDecimal(100),2);
						// l.setAccrualMoney(l.getAccrualMoney().add(sortoverdueMoney).setScale(2,BigDecimal.ROUND_HALF_UP));
						// }
						//							
						//							 
						//					   
						// }
						BigDecimal lin = new BigDecimal(0.00);
						// if(l.getIsOverdue()==null){
						if (compare_date(l.getIntentDate(), new Date()) > 0) {
							l.setIsOverdue("是");
						} else {

							l.setIsOverdue("否");
							// }

						}
						if (l.getIncomeMoney().compareTo(lin) == 0) {
							l.setPayInMoney(l.getPayMoney());
						} else {
							l.setPayInMoney(l.getIncomeMoney());
						}
					}
					size = list.size();

				} else {
					size = 0;
				}
			}
			if (slSmallloanProject != null && flag1 == 0) { // 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("SmallLoan")) {
					if (AppUtil.getInterest().equals("0")) {
						list = FundIntentListPro3
								.getFundIntentDefaultList(
										calcutype,
										slSmallloanProject.getAccrualtype(),
										slSmallloanProject
												.getIsPreposePayAccrual(),
										slSmallloanProject.getPayaccrualType(),
										slSmallloanProject
												.getDayAccrualRate()
												.divide(BigDecimal.valueOf(100)),
										slSmallloanProject.getProjectMoney(),
										sd.format(slSmallloanProject
												.getStartDate()),
										slSmallloanProject.getIntentDate() == null ? null
												: sd.format(slSmallloanProject
														.getIntentDate()),
										slSmallloanProject
												.getDayManagementConsultingOfRate()
												.divide(BigDecimal.valueOf(100)),
										//slSmallloanProject.getDayFinanceServiceOfRate().divide(BigDecimal.valueOf(100)),
										slSmallloanProject.getFinanceServiceOfRate().divide(BigDecimal.valueOf(100)),
										slSmallloanProject.getPayintentPeriod(),
										slSmallloanProject.getIsStartDatePay(),
										slSmallloanProject
												.getPayintentPerioDate(),
										slSmallloanProject
												.getDayOfEveryPeriod(),
										isHaveLending,
										slSmallloanProject
												.getIsPreposePayConsultingCheck(),
										slSmallloanProject
												.getIsInterestByOneTime(),
										slSmallloanProject.getDateMode());
					} else {
						list = FundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										slSmallloanProject.getAccrualtype(),
										slSmallloanProject
												.getIsPreposePayAccrual(),
										slSmallloanProject.getPayaccrualType(),
										slSmallloanProject
												.getDayAccrualRate()
												.divide(BigDecimal.valueOf(100)),
										slSmallloanProject.getProjectMoney(),
										sd.format(slSmallloanProject
												.getStartDate()),
										slSmallloanProject.getIntentDate() == null ? null
												: sd.format(slSmallloanProject
														.getIntentDate()),
										slSmallloanProject
												.getDayManagementConsultingOfRate()
												.divide(BigDecimal.valueOf(100)),
										slSmallloanProject
												.getDayFinanceServiceOfRate()
												.divide(BigDecimal.valueOf(100)),
										slSmallloanProject.getPayintentPeriod(),
										slSmallloanProject.getIsStartDatePay(),
										slSmallloanProject
												.getPayintentPerioDate(),
										slSmallloanProject
												.getDayOfEveryPeriod(),
										isHaveLending,
										slSmallloanProject
												.getIsPreposePayConsultingCheck(),
										slSmallloanProject
												.getIsInterestByOneTime(),
										slSmallloanProject.getDateMode());
					}
				}

				for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0) {
						list.add(t);
					}
				}
				size = list.size();
			}
			if (flFinancingProject != null && flag1 == 0) { // 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("Financing")) {
					if (AppUtil.getInterest().equals("0")) {
						list = FinanceFundIntentListPro
								.getFundIntentDefaultList(
										calcutype,
										flFinancingProject.getAccrualtype(),
										flFinancingProject
												.getIsPreposePayAccrual(),
										flFinancingProject.getPayaccrualType(),
										flFinancingProject
												.getDayAccrualRate()
												.divide(BigDecimal.valueOf(100)),
										flFinancingProject.getProjectMoney(),
										sd.format(flFinancingProject
												.getStartDate()),
										flFinancingProject.getIntentDate() == null ? null
												: sd.format(flFinancingProject
														.getIntentDate()),
										new BigDecimal(0),
										new BigDecimal(0),
										flFinancingProject.getPayintentPeriod(),
										flFinancingProject.getIsStartDatePay(),
										flFinancingProject
												.getPayintentPerioDate(),
										flFinancingProject
												.getDayOfEveryPeriod(),
										isHaveLending, null, flFinancingProject
												.getIsInterestByOneTime(),
										flFinancingProject.getDateMode());
					} else {
						list = FinanceFundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										flFinancingProject.getAccrualtype(),
										flFinancingProject
												.getIsPreposePayAccrual(),
										flFinancingProject.getPayaccrualType(),
										flFinancingProject
												.getDayAccrualRate()
												.divide(BigDecimal.valueOf(100)),
										flFinancingProject.getProjectMoney(),
										sd.format(flFinancingProject
												.getStartDate()),
										flFinancingProject.getIntentDate() == null ? null
												: sd.format(flFinancingProject
														.getIntentDate()),
										new BigDecimal(0),
										new BigDecimal(0),
										flFinancingProject.getPayintentPeriod(),
										flFinancingProject.getIsStartDatePay(),
										flFinancingProject
												.getPayintentPerioDate(),
										flFinancingProject
												.getDayOfEveryPeriod(),
										isHaveLending, null, flFinancingProject
												.getIsInterestByOneTime(),
										flFinancingProject.getDateMode());
					}
				}

				for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0) {
						list.add(t);
					}
				}
				size = list.size();
			}
			if (plPawnProject != null && flag1 == 0) {
				// 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("Pawn")) {
					List<SlFundIntent> slist = slFundIntentService
							.getListByFundType(projectId, businessType,
									"pawnPrincipalLending");
					BigDecimal money = null;
					if (null != slist && slist.size() > 0) {
						SlFundIntent s = slist.get(0);
						money = s.getPayMoney();
					}
					if (AppUtil.getInterest().equals("0")) {
						list = PawnFundIntentListPro.getFundIntentDefaultList(
								calcutype, "singleInterest", plPawnProject
										.getIsPreposePayAccrual(),
								plPawnProject.getPayaccrualType(),
								plPawnProject.getAccrual().divide(
										new BigDecimal(30), 10,
										BigDecimal.ROUND_HALF_UP).divide(
										BigDecimal.valueOf(100)),
								money == null ? plPawnProject.getProjectMoney()
										: money, sd.format(plPawnProject
										.getStartDate()), plPawnProject
										.getIntentDate() == null ? null : sd
										.format(plPawnProject.getIntentDate()),
								plPawnProject.getMonthFeeRate().divide(
										new BigDecimal(30), 10,
										BigDecimal.ROUND_HALF_UP).divide(
										BigDecimal.valueOf(100)),
								new BigDecimal(0), plPawnProject
										.getPayintentPeriod(), plPawnProject
										.getIsStartDatePay(), plPawnProject
										.getPayintentPerioDate(), plPawnProject
										.getDayOfEveryPeriod(), isHaveLending,
								null, plPawnProject.getIsInterestByOneTime(),
								null);
					} else {
						list = PawnFundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										"singleInterest",
										plPawnProject.getIsPreposePayAccrual(),
										plPawnProject.getPayaccrualType(),
										plPawnProject
												.getAccrual()
												.divide(
														new BigDecimal(30),
														10,
														BigDecimal.ROUND_HALF_UP)
												.divide(BigDecimal.valueOf(100)),
										money == null ? plPawnProject
												.getProjectMoney() : money,
										sd.format(plPawnProject.getStartDate()),
										plPawnProject.getIntentDate() == null ? null
												: sd.format(plPawnProject
														.getIntentDate()),
										plPawnProject
												.getMonthFeeRate()
												.divide(
														new BigDecimal(30),
														10,
														BigDecimal.ROUND_HALF_UP)
												.divide(BigDecimal.valueOf(100)),
										new BigDecimal(0), plPawnProject
												.getPayintentPeriod(),
										plPawnProject.getIsStartDatePay(),
										plPawnProject.getPayintentPerioDate(),
										plPawnProject.getDayOfEveryPeriod(),
										isHaveLending, null, plPawnProject
												.getIsInterestByOneTime(), null);
					}
				}

				for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0
							) {
						list.add(t);
					}
				}
				size = list.size();

			}
			if (flLeaseFinanceProject != null && flag1 == 0) {
				// 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("LeaseFinance")) {

					if (AppUtil.getInterest().equals("0")) {
						list = LeaseFinanceFundIntentListPro
								.getFundIntentDefaultList(
										calcutype,
										flLeaseFinanceProject.getAccrualtype(),
										0,
										flLeaseFinanceProject
												.getPayaccrualType(),
										flLeaseFinanceProject
												.getRentalRate()
												.divide(BigDecimal.valueOf(100)),
										flLeaseFinanceProject.getProjectMoney(),
										sd.format(flLeaseFinanceProject
												.getStartDate()),
										flLeaseFinanceProject.getIntentDate() == null ? null
												: sd
														.format(flLeaseFinanceProject
																.getIntentDate()),
										flLeaseFinanceProject
												.getRentalFeeRate()
												.divide(BigDecimal.valueOf(100)),
										flLeaseFinanceProject
												.getLeaseDepositMoney(),
										flLeaseFinanceProject
												.getPayintentPeriod(),
										flLeaseFinanceProject
												.getIsStartDatePay(),
										flLeaseFinanceProject
												.getPayintentPerioDate(),
										flLeaseFinanceProject
												.getDayOfEveryPeriod(),
										isHaveLending, null, 0, null,
										flLeaseFinanceProject
												.getLeaseRetentionFeeMoney(),
										flLeaseFinanceProject
												.getFeePayaccrualType(),
										flLeaseFinanceProject
												.getEachRentalReceivable());
					} else {
						list = LeaseFinanceFundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										flLeaseFinanceProject.getAccrualtype(),
										0,
										flLeaseFinanceProject
												.getPayaccrualType(),
										flLeaseFinanceProject
												.getRentalRate()
												.divide(BigDecimal.valueOf(100)),
										flLeaseFinanceProject.getProjectMoney(),
										sd.format(flLeaseFinanceProject
												.getStartDate()),
										flLeaseFinanceProject.getIntentDate() == null ? null
												: sd
														.format(flLeaseFinanceProject
																.getIntentDate()),
										flLeaseFinanceProject
												.getRentalFeeRate()
												.divide(BigDecimal.valueOf(100)),
										flLeaseFinanceProject
												.getLeaseDepositMoney(),
										flLeaseFinanceProject
												.getPayintentPeriod(),
										flLeaseFinanceProject
												.getIsStartDatePay(),
										flLeaseFinanceProject
												.getPayintentPerioDate(),
										flLeaseFinanceProject
												.getDayOfEveryPeriod(),
										isHaveLending, null, 0, null,
										flLeaseFinanceProject
												.getLeaseRetentionFeeMoney(),
										flLeaseFinanceProject
												.getFeePayaccrualType(),
										flLeaseFinanceProject
												.getEachRentalReceivable());
					}
				}

				for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0) {
						list.add(t);
					}
				}
				size = list.size();

			}
			List<SlFundIntent> list1 = new ArrayList<SlFundIntent>();
			if (relateIntentOrCharge == null
					|| relateIntentOrCharge.equals("all")) { // 取全部
				if(list!=null&&list.size()!=0)
				for (SlFundIntent l : list) {
					if(l==null){
						continue;
					}
					List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
							.getByDicKey(l.getFundType());
					l.setFundTypeName(dictionaryIndependent.get(0)
							.getItemValue());
					list1.add(l);
				}
			}
			if (relateIntentOrCharge != null
					&& relateIntentOrCharge.equals("charge")) { // 取费用相关
				for (SlFundIntent l : list) {
					if (businessType.equals("SmallLoan")) {
						if (l.getFundType().equals("serviceMoney")
								|| l.getFundType().equals("consultationMoney")
								|| l.getFundType().equals("loanInterest")) {
							list1.add(l);
						}
					} else if (businessType.equals("Financing")) {
						if (l.getFundType().equals("FinancingInterest")
								|| l.getFundType().equals(
										"financingconsultationMoney")
								|| l.getFundType().equals(
										"financingserviceMoney")) {
							list1.add(l);
						}
					} else if (businessType.equals("Pawn")) {
						if (l.getFundType().equals("pawnServiceMoney")
								|| l.getFundType().equals("pawnLoanInterest")) {
							list1.add(l);
						}
					} else if (businessType.equals("LeaseFinance")) {
						if (l.getFundType().equals("rentalFeeCharging")
								|| l.getFundType().equals("leaseFeeCharging")
								|| l.getFundType().equals(
										"leaseObjectRetentionFee")) {
							list1.add(l);
						}
					}
				}
			}
			if (relateIntentOrCharge != null
					&& relateIntentOrCharge.equals("intent")) { // 取本金相关
				for (SlFundIntent l : list) {
					if (businessType.equals("SmallLoan")) {
						if (l.getFundType().equals("principalRepayment")
								|| l.getFundType().equals("principalLending")) {
							list1.add(l);
						}
					} else if (businessType.equals("Financing")) {
						if (l.getFundType().equals("Financingborrow")
								|| l.getFundType().equals("FinancingRepay")) {
							list1.add(l);
						}
					} else if (businessType.equals("Pawn")) {
						if (l.getFundType().equals("pawnPrincipalRepayment")
								|| l.getFundType().equals(
										"pawnPrincipalLending")) {
							list1.add(l);
						}
					} else if (businessType.equals("LeaseFinance")) {
						if (l.getFundType().equals("marginCollection")
								|| l.getFundType().equals(
										"leasePrincipalRepayment")
								|| l.getFundType().equals("rentalCostsPaid")
								|| l.getFundType().equals("marginRefund")) {
							list1.add(l);
						}
					}
				}
			}
			if (relateIntentOrCharge != null
					&& relateIntentOrCharge.equals("none")) { // 取空

			}

			if (list1 != null) {
				size = list1.size();
			}

			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(size).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
					"factDate", "interestStarTime", "interestEndTime");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"intentDate", "factDate", "interestStarTime",
					"interestEndTime" });
			buff.append(json.serialize(list1));
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}
	public String superviselist() { // listbySmallLoan and listbyFinancing
		try {
			List<SlFundIntent> list = null;
			int size = 0;
			if (slSuperviseRecord != null && flag1 == 0) {
				SuperviseFundIntentCreate superviseFundIntentCreate=new SuperviseFundIntentCreate(slSuperviseRecord,"no");
				list=superviseFundIntentCreate.createBorrowerSlFundIntent();
				/* // 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

				if (calcutype.equals("ExhibitionBusiness")) {
					list = FundIntentListPro3
							.getFundIntentDefaultList(
									calcutype,
									slSuperviseRecord.getAccrualtype(),
									slSuperviseRecord
											.getIsPreposePayAccrualsupervise(),
									slSuperviseRecord.getPayaccrualType(),
									slSuperviseRecord.getContinuationRateNew()
											.divide(BigDecimal.valueOf(100)),
									slSuperviseRecord.getContinuationMoney(),
									sd.format(slSuperviseRecord.getStartDate()),
									sd.format(slSuperviseRecord.getEndDate()),
									slSuperviseRecord
											.getDayManagementConsultingOfRate()
											.divide(BigDecimal.valueOf(100)),
									slSuperviseRecord
											.getDayFinanceServiceOfRate()
											.divide(BigDecimal.valueOf(100)),
									slSuperviseRecord.getPayintentPeriod(),
									slSuperviseRecord.getIsStartDatePay(),
									slSuperviseRecord.getPayintentPerioDate(),
									slSuperviseRecord.getDayOfEveryPeriod(),
									"no", null, slSuperviseRecord
											.getIsInterestByOneTime(),
									slSuperviseRecord.getDateMode());
				}

				for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}
				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0) {
						list.add(t);
					}
				}
				size = list.size();
			*/}

			/*
			 * List<SlFundIntent> list1=new ArrayList<SlFundIntent>();
			 * if(relateIntentOrCharge ==null ||
			 * relateIntentOrCharge.equals("all")){ //取全部 for(SlFundIntent
			 * l:list){ list1.add(l); } } if (relateIntentOrCharge != null &&
			 * relateIntentOrCharge.equals("charge")) { // 取费用相关 for
			 * (SlFundIntent l : list) { if
			 * (businessType.equals("ExhibitionBusiness")) { if
			 * (l.getFundType().equals("serviceMoney") ||
			 * l.getFundType().equals("consultationMoney") ||
			 * l.getFundType().equals("loanInterest")) { list1.add(l); } } else
			 * if (businessType.equals("Financing")) { if
			 * (l.getFundType().equals("FinancingInterest") ||
			 * l.getFundType().equals( "financingconsultationMoney") ||
			 * l.getFundType().equals( "financingserviceMoney")) { list1.add(l);
			 * } } } } if (relateIntentOrCharge != null &&
			 * relateIntentOrCharge.equals("intent")) { // 取本金相关 for
			 * (SlFundIntent l : list) { if
			 * (businessType.equals("ExhibitionBusiness")) { if
			 * (l.getFundType().equals("principalRepayment") ||
			 * l.getFundType().equals("principalLending")) { list1.add(l); } }
			 * else if (businessType.equals("Financing")) { if
			 * (l.getFundType().equals("Financingborrow") ||
			 * l.getFundType().equals("FinancingRepay")) { list1.add(l); } } } }
			 * if(relateIntentOrCharge !=null &&
			 * relateIntentOrCharge.equals("none")){ //取空
			 * 
			 * }
			 * 
			 * 
			 * if(list1 !=null){ size=list1.size(); }
			 */

			StringBuffer buff = new StringBuffer("{success:true,result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
					"factDate", "interestStarTime", "interestEndTime");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"intentDate", "factDate", "interestStarTime",
					"interestEndTime" });
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	public String listbyGurantee() { // listbyGurantee
		try {
			List<SlFundIntent> list = new ArrayList<SlFundIntent>();
			int size = 0;
			if (gLGuaranteeloanProject == null || flag1 == 1) { // 正常从数据库获得listslSmallloanProject==null为款项确认，flag1==1尽职调查

				list = slFundIntentService.getByProjectId(projectId,
						businessType);
				if (list != null) {
					for (SlFundIntent l : list) {
						slFundIntentService.evict(l);
						List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
								.getByDicKey(l.getFundType());
						l.setFundTypeName(dictionaryIndependent.get(0)
								.getItemValue());

						GLGuaranteeloanProject glGuaranteeloanProject = glGuaranteeloanProjectService
								.get(l.getProjectId());
						l.setOverdueRate(glGuaranteeloanProject
								.getOverdueRate());
						if (l.getNotMoney().compareTo(new BigDecimal(0)) == 1) {
							BigDecimal sortoverdueMoney = new BigDecimal(0.00);
							long sortday = new Long(0);
							SimpleDateFormat from = new SimpleDateFormat(
									"yyyy-MM-dd 00:00:00");
							String newdate = from.format(new Date());
							Date d = from.parse(newdate);
							if (compare_date(d, l.getIntentDate()) == -1
									&& l.getOverdueRate() != null) {
								Date b = new Date();
								Long a = compare_date(new Date(), l
										.getIntentDate());
								sortday = compare_date(l.getIntentDate(),
										new Date()) + 1;
								BigDecimal day1 = new BigDecimal(sortday);
								BigDecimal OverdueMoney = l.getOverdueRate()
										.multiply(day1);
								sortoverdueMoney = OverdueMoney.multiply(
										l.getNotMoney()).divide(
										new BigDecimal(100), 2);
								sortoverdueMoney = sortoverdueMoney.divide(
										new BigDecimal(300), 26,
										BigDecimal.ROUND_HALF_UP); // 月
								l.setAccrualMoney(l.getAccrualMoney().add(
										sortoverdueMoney).setScale(2,
										BigDecimal.ROUND_HALF_UP));
							}
						}

						if (compare_date(l.getIntentDate(), new Date()) > 0) {
							l.setIsOverdue("是");
						} else {
							l.setIsOverdue("否");
						}

					}
				}

			}
			if (gLGuaranteeloanProject != null && flag1 == 0) { // 生成款项计划
				String startDate = DateUtil.dateToStr(gLGuaranteeloanProject
						.getAcceptDate(), "yyyy-MM-dd");
				String endDate = DateUtil.dateToStr(gLGuaranteeloanProject
						.getIntentDate(), "yyyy-MM-dd");
				list = GuaranteeFundIntentIist.getFundIntentDefaultList(
						"Guarantee", startDate, endDate, gLGuaranteeloanProject
								.getEarnestmoney(), gLGuaranteeloanProject
								.getCustomerEarnestmoneyScale(),
						gLGuaranteeloanProject.getPremiumRate(),
						gLGuaranteeloanProject.getProjectMoney());

				List<SlFundIntent> listafter = slFundIntentService
						.getByProjectId(projectId, businessType);
				for (SlFundIntent t : listafter) {
					slFundIntentService.evict(t);
					if (t.getAfterMoney().compareTo(new BigDecimal(0)) != 0) {
						list.add(t);
					}
				}
				size = list.size();
			}

			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(size).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
					"factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"intentDate", "factDate" });
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	public Date getIntentDate(Date startperiodDate, Integer prepayintentPeriod,
			String payaccrualType, Integer dayOfEveryPeriod,
			String isStartDatePay, Integer payintentPerioDate) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date intentDate = new Date();
		String[] arr = sd.format(startperiodDate).split("-");
		String startDate = "";
		if (null != isStartDatePay && isStartDatePay.equals("1")) {
			startDate = arr[0] + "-" + arr[1] + "-" + payintentPerioDate;
		}
		if (payaccrualType.equals("dayPay")) {
			if (AppUtil.getInterest().equals("0")) {
				intentDate = DateUtil.addDaysToDate(startperiodDate,
						prepayintentPeriod);
			} else {
				intentDate = DateUtil.addDaysToDate(startperiodDate,
						prepayintentPeriod - 1);
			}

		}
		if (payaccrualType.equals("monthPay")) {
			if (isStartDatePay.equals("1")) {
				intentDate = DateUtil.addMonthsToDate(DateUtil.parseDate(
						startDate, "yyyy-MM-dd"), prepayintentPeriod);
			} else {
				intentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(
						startperiodDate, prepayintentPeriod), -1);
			}
		}
		if (payaccrualType.equals("seasonPay")) {
			if (isStartDatePay.equals("1")) {
				intentDate = DateUtil.addMonthsToDate(DateUtil.parseDate(
						startDate, "yyyy-MM-dd"), prepayintentPeriod * 3);
			} else {
				intentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(
						startperiodDate, prepayintentPeriod * 3), -1);
			}

		}
		if (payaccrualType.equals("yearPay")) {
			if (isStartDatePay.equals("1")) {
				intentDate = DateUtil.addMonthsToDate(DateUtil.parseDate(
						startDate, "yyyy-MM-dd"), prepayintentPeriod * 12);
			} else {
				intentDate = DateUtil.addDaysToDate(DateUtil.addMonthsToDate(
						startperiodDate, prepayintentPeriod * 12), -1);
			}
		}

		if (payaccrualType.equals("owerPay")) {
			if (AppUtil.getInterest().equals("0")) {
				intentDate = DateUtil.addDaysToDate(startperiodDate,
						prepayintentPeriod * dayOfEveryPeriod);
			} else {
				intentDate = DateUtil.addDaysToDate(startperiodDate,
						prepayintentPeriod * dayOfEveryPeriod - 1);
			}
		}
		return intentDate;
	}

	public String listbyAlterAccrualRecord() {
		int size = 0;
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		String isUnLoadData = this.getRequest().getParameter("isUnLoadData");
		String isThisAlterAccrualRecord = getRequest().getParameter(
				"isThisAlterAccrualRecord");
		String isThisAlterAccrualRecordIdstring = getRequest().getParameter(
				"isThisAlterAccrualRecordId");
		String preceptId=this.getRequest().getParameter("preceptId");
		if (slAlterAccrualRecord == null || flag1 == 1) {
			if ((Boolean.valueOf(isUnLoadData))) {
			} else if (isThisAlterAccrualRecord.equals("no")
					&& isThisAlterAccrualRecordIdstring.equals("noid")) {
				list = slFundIntentService.getByProjectAsc(projectId,businessType,((null!=preceptId && !preceptId.equals(""))?Long.valueOf(preceptId):null));
			} else {
				if (isThisAlterAccrualRecordIdstring != null&& !"".equals(isThisAlterAccrualRecordIdstring)&& !"null".equals(isThisAlterAccrualRecordIdstring)) {
					Long alterAccrualRecordId = Long.parseLong(getRequest().getParameter("isThisAlterAccrualRecordId"));
					if (isThisAlterAccrualRecord.equals("yes")) {
						list = slFundIntentService.getlistbyslslAlteraccrualRecordId(alterAccrualRecordId, businessType,projectId);
					} else {
						List<SlFundIntent> listall = slFundIntentService.getByProjectAsc(projectId, businessType,((null!=preceptId && !preceptId.equals(""))?Long.valueOf(preceptId):null));
						for (SlFundIntent l : listall) {
							if (l.getSlAlteraccrualRecordId() == null) {
								list.add(l);
							}
							if (l.getSlAlteraccrualRecordId() != null && !l.getSlAlteraccrualRecordId().equals(alterAccrualRecordId)) {
								list.add(l);
							}
						}
					}
				} else {
					// list = slFundIntentService.getByProjectId4(projectId,
					// businessType);
				}
			}
			if (list != null) {
				for (SlFundIntent l : list) {
					slFundIntentService.evict(l);
					List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
							.getByDicKey(l.getFundType());
					l.setFundTypeName(dictionaryIndependent.get(0)
							.getItemValue());
					if (businessType.equals("SmallLoan")) {
						SlSmallloanProject loan = slSmallloanProjectService
								.get(l.getProjectId());
						if (l.getFundType().equals("principalRepayment")) {
							l.setOverdueRate(loan.getOverdueRateLoan());
						} else {
							l.setOverdueRate(loan.getOverdueRate());
						}

					}
					if (l.getNotMoney().compareTo(new BigDecimal(0)) == 1) {
						BigDecimal sortoverdueMoney = new BigDecimal(0.00);
						long sortday = new Long(0);
						SimpleDateFormat from = new SimpleDateFormat(
								"yyyy-MM-dd 00:00:00");
						String newdate = from.format(new Date());
						Date d;
						try {
							d = from.parse(newdate);

							if (compare_date(d, l.getIntentDate()) == -1
									&& l.getOverdueRate() != null) {
								Date b = new Date();
								Long a = compare_date(new Date(), l
										.getIntentDate());
								sortday = compare_date(l.getIntentDate(),
										new Date()) + 1;
								BigDecimal day1 = new BigDecimal(sortday);
								BigDecimal OverdueMoney = l.getOverdueRate()
										.multiply(day1);
								sortoverdueMoney = OverdueMoney.multiply(
										l.getNotMoney()).divide(
										new BigDecimal(100), 2);
								l.setAccrualMoney(l.getAccrualMoney().add(
										sortoverdueMoney));
							}
						} catch (ParseException e) {
							e.printStackTrace();
							logger.error("小额贷款 -申请展期款项-出错:" + e.getMessage());
						}

					}
					BigDecimal lin = new BigDecimal(0.00);
					// if(l.getIsOverdue()==null){
					if (compare_date(l.getIntentDate(), new Date()) > 0) {
						l.setIsOverdue("是");
					} else {

						l.setIsOverdue("否");
						// }

					}
					if (l.getIncomeMoney().compareTo(lin) == 0) {
						l.setPayInMoney(l.getPayMoney());
					} else {
						l.setPayInMoney(l.getIncomeMoney());
					}
				}
				size = list.size();

			} else {
				size = 0;
			}

		}
		if (slAlterAccrualRecord != null && flag1 == 0) { // 生成款项计划

			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			if(calcutype.equals("SmallLoan")){

				BpFundProject project=bpFundProjectService.get(Long.valueOf(preceptId));
					
				BigDecimal principalMoney=slFundIntentService.getPrincipalMoney(projectId, businessType, sd.format(slAlterAccrualRecord.getAlelrtDate()),project.getId());
				 List<SlFundIntent> flist=slFundIntentService.listByEarlyDate(projectId, businessType, sd.format(slAlterAccrualRecord.getAlelrtDate()),"loanInterest",project.getId());
					SlFundIntent f=null;
					if(null!=flist && flist.size()>0){
						f=flist.get(0);
					}
					if(null!=f){
						Date interestEndTime=f.getInterestEndTime();//本期的截止日期
						if(AppUtil.getInterest().equals("0")){//算头不算尾
							interestEndTime=DateUtil.addDaysToDate(f.getInterestEndTime(), 1);
						}
						SlFundIntent bf=null;
						if(project.getAccrualtype().equals("sameprincipalandInterest")){
							List<SlFundIntent> principalist=slFundIntentService.listByEarlyDate(projectId, businessType, sd.format(slAlterAccrualRecord.getAlelrtDate()),"principalRepayment",project.getId());
							if(null!=principalist && principalist.size()>0){
								bf=principalist.get(0);
							}
						}
						FundAlterAccrualFundIntentCreate alterAccrualFundIntentCreate=new FundAlterAccrualFundIntentCreate(project,slAlterAccrualRecord,f.getPayintentPeriod(),interestEndTime,f.getInterestStarTime(),principalMoney,bf);
						alterAccrualFundIntentCreate.create(list);
					}
			
			}else if(calcutype.equals("Financing")){
			FlFinancingProject project=flFinancingProjectService.get(projectId);
			if(project.getAccrualtype().equals("singleInterest")){
				if(project.getIsInterestByOneTime()!=1){
					List<SlFundIntent> slist=slFundIntentService.getListByTypeAndDate(projectId, businessType, "FinancingInterest", "<='"+sd.format(slAlterAccrualRecord.getAlelrtDate())+"'");
					Date startDate=null;
					if(null!=slist && slist.size()>0){
						SlFundIntent s=slist.get(0);
						startDate=s.getIntentDate();
					}else{
						startDate=project.getStartDate();
					}
					List<SlFundIntent> flist=slFundIntentService.getListByTypeAndDate(projectId, businessType, "FinancingInterest", ">'"+sd.format(slAlterAccrualRecord.getAlelrtDate())+"'");
					Date intentDate=null;
					int period=0;
					if(null!=flist && flist.size()>0){
						SlFundIntent f=flist.get(flist.size()-1);
						intentDate=f.getIntentDate();
						period=f.getPayintentPeriod();
					}else{
						intentDate=project.getIntentDate();
						period=project.getPayintentPeriod();
					}
					if(project.getIsPreposePayAccrual()==1){
						period=period-1;
					}
					int sdays=DateUtil.getDaysBetweenDate(sd.format(startDate), sd.format(slAlterAccrualRecord.getAlelrtDate()));
					int edays=DateUtil.getDaysBetweenDate(sd.format(slAlterAccrualRecord.getAlelrtDate()), sd.format(intentDate));
					List<SlFundIntent> ulist=slFundIntentService.getListByTypeAndDate(projectId, businessType, "FinancingRepay", ">'"+sd.format(slAlterAccrualRecord.getAlelrtDate())+"'");//利率变更日期以后所有未还的本金
					BigDecimal repayMoney=new BigDecimal(0);
					for(SlFundIntent s:ulist){
						repayMoney=repayMoney.add(s.getNotMoney());
					}
					if(project.getIsPreposePayAccrual()==0){
						if(sdays>0){
							SlFundIntent s=new SlFundIntent();
							s.setFundType("FinancingInterest");
							s.setPayMoney(repayMoney.multiply(project.getAccrualNew().divide(new BigDecimal(100)).multiply(new BigDecimal(sdays))).setScale(2,BigDecimal.ROUND_HALF_UP));
							s.setIncomeMoney(new BigDecimal(0));
							s.setIntentDate(slAlterAccrualRecord.getAlelrtDate());
							s.setPayintentPeriod(period);
							s.setInterestStarTime(startDate);
							s.setInterestEndTime(DateUtil.addDaysToDate(slAlterAccrualRecord.getAlelrtDate(), -1));
							if(s.getPayMoney().compareTo(new BigDecimal(0))!=0){
								list.add(s);
							}
						}
						if(edays>0){
							SlFundIntent s=new SlFundIntent();
							s.setFundType("FinancingInterest");
							s.setPayMoney(repayMoney.multiply(slAlterAccrualRecord.getAccrual().divide(new BigDecimal(100)).divide(new BigDecimal(30),10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(edays))).setScale(2,BigDecimal.ROUND_HALF_UP));
							s.setIncomeMoney(new BigDecimal(0));
							s.setIntentDate(intentDate);
							s.setPayintentPeriod(period);
							s.setInterestStarTime(slAlterAccrualRecord.getAlelrtDate());
							s.setInterestEndTime(DateUtil.addDaysToDate(intentDate, -1));
							if(s.getPayMoney().compareTo(new BigDecimal(0))!=0){
								list.add(s);
							}
						}
					}else{
						if(edays>0){
							SlFundIntent s=new SlFundIntent();
							s.setFundType("FinancingInterest");
							s.setPayMoney(repayMoney.multiply((slAlterAccrualRecord.getAccrual().divide(new BigDecimal(100)).divide(new BigDecimal(30),10,BigDecimal.ROUND_HALF_UP)).subtract(project.getAccrualNew().divide(new BigDecimal(100)))).multiply(new BigDecimal(edays)).setScale(2,BigDecimal.ROUND_HALF_UP));
							s.setIncomeMoney(new BigDecimal(0));
							s.setIntentDate(slAlterAccrualRecord.getAlelrtDate());
							s.setPayintentPeriod(1);
							s.setInterestStarTime(project.getStartDate());
							s.setInterestEndTime(DateUtil.addDaysToDate(slAlterAccrualRecord.getAlelrtDate(), -1));
							if(s.getPayMoney().compareTo(new BigDecimal(0))!=0){
								list.add(s);
							}
						}
					}
					List<SlFundIntent> sflist=slFundIntentService.getListByType(projectId, businessType, "FinancingInterest", ">'"+(project.getIsPreposePayAccrual()==1?sd.format(slAlterAccrualRecord.getAlelrtDate()):sd.format(intentDate))+"'");
					int payintentPeriod=0;
					if(null!=sflist && sflist.size()>0){
						payintentPeriod=sflist.size();
					}
				
							for(SlFundIntent s:sflist){
								s.setPayMoney(s.getPayMoney().multiply(slAlterAccrualRecord.getAccrual().divide(project.getAccrualNew().multiply(new BigDecimal(30)),10,BigDecimal.ROUND_HALF_UP)).setScale(2,BigDecimal.ROUND_HALF_UP));
								list.add(s);
							}
				
					}else{
						if(project.getIsPreposePayAccrual()==0){
							int sdays=DateUtil.getDaysBetweenDate(sd.format(project.getStartDate()), sd.format(slAlterAccrualRecord.getAlelrtDate()));
							int edays=DateUtil.getDaysBetweenDate(sd.format(slAlterAccrualRecord.getAlelrtDate()), sd.format(project.getIntentDate()));
							List<SlFundIntent> ulist=slFundIntentService.getListByTypeAndDate(projectId, businessType, "FinancingRepay", ">'"+sd.format(slAlterAccrualRecord.getAlelrtDate())+"'");//利率变更日期以后所有未还的本金
							BigDecimal repayMoney=new BigDecimal(0);
							for(SlFundIntent s:ulist){
								repayMoney=repayMoney.add(s.getNotMoney());
							}
							if(sdays>0){
								SlFundIntent s=new SlFundIntent();
								s.setFundType("FinancingInterest");
								s.setPayMoney(repayMoney.multiply(project.getAccrualNew().divide(new BigDecimal(100)).multiply(new BigDecimal(sdays))).setScale(2,BigDecimal.ROUND_HALF_UP));
								s.setIncomeMoney(new BigDecimal(0));
								s.setIntentDate(slAlterAccrualRecord.getAlelrtDate());
								s.setPayintentPeriod(1);
								s.setInterestStarTime(project.getStartDate());
								s.setInterestEndTime(DateUtil.addDaysToDate(slAlterAccrualRecord.getAlelrtDate(), -1));
								if(s.getPayMoney().compareTo(new BigDecimal(0))!=0){
									list.add(s);
								}
							}
							if(edays>0){
								SlFundIntent s=new SlFundIntent();
								s.setFundType("FinancingInterest");
								s.setPayMoney(repayMoney.multiply(slAlterAccrualRecord.getAccrual().divide(new BigDecimal(100)).divide(new BigDecimal(30),10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(edays))).setScale(2,BigDecimal.ROUND_HALF_UP));
								s.setIncomeMoney(new BigDecimal(0));
								s.setIntentDate(project.getIntentDate());
								s.setPayintentPeriod(1);
								s.setInterestStarTime(slAlterAccrualRecord.getAlelrtDate());
								s.setInterestEndTime(DateUtil.addDaysToDate(project.getIntentDate(), -1));
								if(s.getPayMoney().compareTo(new BigDecimal(0))!=0){
									list.add(s);
								}
							}
						}else{
							int edays=DateUtil.getDaysBetweenDate(sd.format(slAlterAccrualRecord.getAlelrtDate()), sd.format(project.getIntentDate()));
							List<SlFundIntent> ulist=slFundIntentService.getListByTypeAndDate(projectId, businessType, "FinancingRepay", ">'"+sd.format(slAlterAccrualRecord.getAlelrtDate())+"'");//利率变更日期以后所有未还的本金
							BigDecimal repayMoney=new BigDecimal(0);
							for(SlFundIntent s:ulist){
								repayMoney=repayMoney.add(s.getNotMoney());
							}
							if(edays>0){
								SlFundIntent s=new SlFundIntent();
								s.setFundType("FinancingInterest");
								s.setPayMoney(repayMoney.multiply((slAlterAccrualRecord.getAccrual().divide(new BigDecimal(100)).divide(new BigDecimal(30),10,BigDecimal.ROUND_HALF_UP)).subtract(project.getAccrualNew().divide(new BigDecimal(100)))).multiply(new BigDecimal(edays)).setScale(2,BigDecimal.ROUND_HALF_UP));
								s.setIncomeMoney(new BigDecimal(0));
								s.setIntentDate(slAlterAccrualRecord.getAlelrtDate());
								s.setPayintentPeriod(1);
								s.setInterestStarTime(project.getStartDate());
								s.setInterestEndTime(DateUtil.addDaysToDate(slAlterAccrualRecord.getAlelrtDate(), -1));
								if(s.getPayMoney().compareTo(new BigDecimal(0))!=0){
									list.add(s);
								}
							}
						}
					}
			}
		}else if(calcutype.equals("LeaseFinance")){
			List<SlFundIntent> listearly=new ArrayList<SlFundIntent>();
			String prepayintentPeriod = this.getRequest().getParameter("alterpayintentPeriod");
			String slSuperviseRecordId = prepayintentPeriod.substring(0,prepayintentPeriod.lastIndexOf("."));
			int prepayintentPeriod1 = slAlterAccrualRecord.getAlterpayintentPeriod()-1;
			Date intentDate = null;
			Date edate = null;
			if (slSuperviseRecordId.equals("0")) {
					FlLeaseFinanceProject project = flLeaseFinanceProjectService.get(projectId);
					List<SlFundIntent> flist = slFundIntentService.getByIntentPeriod(projectId,"LeaseFinance", "leaseFeeCharging",null, prepayintentPeriod1);
					if (null != flist && flist.size() > 0) {
						SlFundIntent sfd = flist.get(0);
						intentDate = sfd.getIntentDate();
					}
					List<SlFundIntent> slist = slFundIntentService.getListByType(projectId,"LeaseFinance","leasePrincipalRepayment",">'"+sd.format(intentDate)+"'");
					BigDecimal intiprojectMoney = new BigDecimal(0);
					for (SlFundIntent sf : slist) {
						if (null != sf.getSlAlteraccrualRecordId()) {
							if ((null != sf.getSlAlteraccrualRecordId() && !sf.getSlAlteraccrualRecordId().toString().equals(slAlterAccrualRecord.getSlAlteraccrualRecordId().toString()))) {
								intiprojectMoney = intiprojectMoney.add(sf.getIncomeMoney());
							}
						} else {
							intiprojectMoney = intiprojectMoney.add(sf.getIncomeMoney());
						}
					}
					int payintentPeriod = project.getPayintentPeriod()- prepayintentPeriod1;
					if (payintentPeriod > 0) {
						Date date = intentDate;
						if (project.getIsStartDatePay().equals("2")) {
							/*if (!project.getPayaccrualType().equals("owerPay")) {
								date = DateUtil.addDaysToDate(date, 1);
							}*/

						} else {
							String date1 = sd.format(date);
							date1 = date1.substring(0, date1
									.lastIndexOf("-") + 1);
							String startDate = sd.format(project.getStartDate());
							startDate = startDate.substring(startDate.lastIndexOf("-") + 1, startDate.length());
							date1 = date1 + startDate;
							date = DateUtil.parseDate(date1, "yyyy-MM-dd");
						}


						if (AppUtil.getInterest().equals("0")) {
							listearly = LeaseFinanceFundIntentListPro.getFundIntentDefaultList(
								calcutype,
								project.getAccrualtype(),
								0,
								project.getPayaccrualType(),
								slAlterAccrualRecord.getAccrual().divide(BigDecimal.valueOf(100)),
								intiprojectMoney,
								sd.format(date),
								project.getIntentDate() == null ? null: sd.format(project.getIntentDate()),
								new BigDecimal(0),
								new BigDecimal(0),
								payintentPeriod,
								project.getIsStartDatePay(),
								project.getPayintentPerioDate(),
								project.getDayOfEveryPeriod(),
								"no", null, 0, null,
								new BigDecimal(0),
								project.getFeePayaccrualType(),
								project.getEachRentalReceivable()
							);
						} else {
							listearly = LeaseFinanceFundIntentListProtw.getFundIntentDefaultList(
								calcutype,
								project.getAccrualtype(),
								0,
								project.getPayaccrualType(),
								slAlterAccrualRecord.getAccrual().divide(BigDecimal.valueOf(100)),
								intiprojectMoney,
								sd.format(date),
								project.getIntentDate() == null ? null: sd.format(project.getIntentDate()),
								new BigDecimal(0),
								new BigDecimal(0),
								payintentPeriod,
								project.getIsStartDatePay(),
								project.getPayintentPerioDate(),
								project.getDayOfEveryPeriod(),
								"no", null, 0, null,
								new BigDecimal(0),
								project.getFeePayaccrualType(),
								project.getEachRentalReceivable()
							);
						}
					}
					for (SlFundIntent si : listearly) {
						si.setBusinessType(calcutype);
						si.setPayintentPeriod(si.getPayintentPeriod()+prepayintentPeriod1);
						if(project.getAccrualtype().equals("sameprincipalandInterest") || project.getAccrualtype().equals("matchingRepayment")){
							list.add(si);
						}else{
							if(si.getFundType().equals("leaseFeeCharging")){
								list.add(si);
							}
						}
					}
				
			} else {
				SlSuperviseRecord record = slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
				
		
				List<SlFundIntent> flist = slFundIntentService.getByIntentPeriod(projectId,"LeaseFinance","leaseFeeCharging",Long.valueOf(slSuperviseRecordId),prepayintentPeriod1);
				if (null != flist && flist.size() > 0) {
					SlFundIntent sfd = flist.get(0);
					intentDate = sfd.getIntentDate();
				}
					
				
				
				List<SlFundIntent> slist = slFundIntentService.getListOfSupervise(projectId, "LeaseFinance",intentDate, Long.valueOf(slSuperviseRecordId));
				BigDecimal intiprojectMoney = new BigDecimal(0);
				for (SlFundIntent sf : slist) {
					if (null != sf.getSlAlteraccrualRecordId()) {
						if (sf.getFundType().equals("leasePrincipalRepayment") && (!sf.getSlAlteraccrualRecordId().toString().equals(slAlterAccrualRecord.getSlAlteraccrualRecordId().toString()))) {
							intiprojectMoney = intiprojectMoney.add(sf.getIncomeMoney());
						}
					} else {
						if (sf.getFundType().equals("leasePrincipalRepayment")) {
							intiprojectMoney = intiprojectMoney.add(sf.getIncomeMoney());
						}
					}
				}
				int payintentPeriod = record.getPayintentPeriod()-prepayintentPeriod1;
				if (payintentPeriod > 0) {
					Date date = intentDate;
					if (record.getIsStartDatePay().equals("2")) {
						/*if (!record.getPayaccrualType().equals("owerPay")) {
							date = DateUtil.addDaysToDate(date, 1);
						}*/
					} else {
						String date1 = sd.format(date);
						date1 = date1.substring(0,
								date1.lastIndexOf("-") + 1);
						String startDate = sd.format(record.getStartDate());
						startDate = startDate.substring(startDate
								.lastIndexOf("-") + 1, startDate.length());
						date1 = date1 + startDate;
						date = DateUtil.parseDate(date1, "yyyy-MM-dd");
					}
					BigDecimal earlyprojectMoney = new BigDecimal(0);
					earlyprojectMoney = record.getContinuationMoney().subtract(intiprojectMoney);
					if (AppUtil.getInterest().equals("0")) {
						listearly = LeaseFinanceFundIntentListPro.getFundIntentDefaultList(
							calcutype,
							record.getAccrualtype(),
							0,
							record.getPayaccrualType(),
							slAlterAccrualRecord.getAccrual().divide(BigDecimal.valueOf(100)),
							earlyprojectMoney,
							sd.format(date),
							record.getEndDate() == null ? null: sd.format(record.getEndDate()),
							new BigDecimal(0),
							new BigDecimal(0),
							payintentPeriod,
							record.getIsStartDatePay(),
							record.getPayintentPerioDate(),
							record.getDayOfEveryPeriod(),
							"no", null, 0, null,
							new BigDecimal(0),
							record.getFeePayaccrualType(),
							record.getEachRentalReceivable()
						);
					} else {
						listearly = LeaseFinanceFundIntentListProtw.getFundIntentDefaultList(
								calcutype,
								record.getAccrualtype(),
								0,
								record.getPayaccrualType(),
								slAlterAccrualRecord.getAccrual().divide(BigDecimal.valueOf(100)),
								earlyprojectMoney,
								sd.format(date),
								record.getEndDate() == null ? null: sd.format(record.getEndDate()),
								new BigDecimal(0),
								new BigDecimal(0),
								payintentPeriod,
								record.getIsStartDatePay(),
								record.getPayintentPerioDate(),
								record.getDayOfEveryPeriod(),
								"no", null, 0, null,
								new BigDecimal(0),
								record.getFeePayaccrualType(),
								record.getEachRentalReceivable()
							);
					}
					
					for (SlFundIntent si : listearly) {
						si.setBusinessType(calcutype);
						si.setPayintentPeriod(si.getPayintentPeriod()+prepayintentPeriod1);
						si.setSlSuperviseRecordId(Long.valueOf(slSuperviseRecordId));
						if((record.getAccrualtype().equals("sameprincipalandInterest") || record.getAccrualtype().equals("matchingRepayment"))){
							list.add(si);
						}else{
							if(si.getFundType().equals("leaseFeeCharging")){
								list.add(si);
							}
						}
					}
				}
			}
	
			size = list.size();
		}

			

		}

		List<SlFundIntent> list1 = new ArrayList<SlFundIntent>();
		if (relateIntentOrCharge == null || relateIntentOrCharge.equals("all")) { // 取全部
			for (SlFundIntent l : list) {
				list1.add(l);
			}
		}
		if (relateIntentOrCharge != null
				&& relateIntentOrCharge.equals("charge")) { // 取费用相关
			for (SlFundIntent l : list) {
				if (businessType.equals("SmallLoan")) {
					if (l.getFundType().equals("serviceMoney")
							|| l.getFundType().equals("consultationMoney")
							|| l.getFundType().equals("loanInterest")) {
						list1.add(l);
					}
				}
			}
		}
		if (relateIntentOrCharge != null
				&& relateIntentOrCharge.equals("intent")) { // 取本金相关
			for (SlFundIntent l : list) {
				if (businessType.equals("SmallLoan")) {
					if (l.getFundType().equals("principalRepayment")
							|| l.getFundType().equals("principalLending")) {
						list1.add(l);
					}
				}
			}
		}
		if (relateIntentOrCharge != null && relateIntentOrCharge.equals("none")) { // 取空

		}

		if (list1 != null) {
			size = list1.size();
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate", "factDate", "interestStarTime",
						"interestEndTime" });
		buff.append(json.serialize(list1));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	public String listbyEarlyRepaymentRecord() {
		int size = 0;
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		String isUnLoadData = this.getRequest().getParameter("isUnLoadData");
		String isThisEarlyPaymentRecord = getRequest().getParameter("isThisEarlyPaymentRecord");
		String isThisEarlyPaymentRecordIdstring = getRequest().getParameter("isThisEarlyPaymentRecordId");

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		if (slEarlyRepaymentRecord == null || flag1 == 1) {
			if ((Boolean.valueOf(isUnLoadData))) {

			} else if (isThisEarlyPaymentRecord.equals("no")&& isThisEarlyPaymentRecordIdstring.equals("noid")) {
				list = slFundIntentService.getByProjectId(projectId,businessType);
			} else {
				if(isThisEarlyPaymentRecordIdstring!=null&&!"".equals(isThisEarlyPaymentRecordIdstring)&&!"null".equals(isThisEarlyPaymentRecordIdstring)){
					Long isThisEarlyPaymentRecordId = Long.parseLong(getRequest().getParameter("isThisEarlyPaymentRecordId"));
					if (isThisEarlyPaymentRecord.equals("yes")) {
						list = slFundIntentService.getlistbyslEarlyRepaymentId(isThisEarlyPaymentRecordId, businessType,projectId);
					} else {
						List<SlFundIntent> listall = slFundIntentService.listByProjectId(projectId, businessType);
						for (SlFundIntent l : listall) {
							if (l.getSlEarlyRepaymentId() == null) {
								list.add(l);
							}
							if (l.getSlEarlyRepaymentId() != null&& !l.getSlEarlyRepaymentId().equals(isThisEarlyPaymentRecordId)) {
								list.add(l);
							}
						}

					}
				}
			}
			if (list != null) {
				for (SlFundIntent l : list) {
					slFundIntentService.evict(l);
					List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService.getByDicKey(l.getFundType());
					l.setFundTypeName(dictionaryIndependent.get(0).getItemValue());
					if (businessType.equals("SmallLoan")) {
						SlSmallloanProject loan = slSmallloanProjectService.get(l.getProjectId());
						if(l.getFundType().equals("principalRepayment")){
							l.setOverdueRate(loan.getOverdueRateLoan());
						}else{
							l.setOverdueRate(loan.getOverdueRate());
						}
					}
					if (l.getNotMoney().compareTo(new BigDecimal(0)) == 1) {
						BigDecimal sortoverdueMoney = new BigDecimal(0.00);
						long sortday = new Long(0);
						SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
						String newdate = from.format(new Date());
						Date d;
						try {
							d = from.parse(newdate);

							if (compare_date(d, l.getIntentDate()) == -1&& l.getOverdueRate() != null) {
								/*Date b = new Date();
								Long a = compare_date(new Date(), l.getIntentDate());*/
								sortday = compare_date(l.getIntentDate(),new Date()) + 1;
								BigDecimal day1 = new BigDecimal(sortday);
								BigDecimal OverdueMoney = l.getOverdueRate().multiply(day1);
								sortoverdueMoney = OverdueMoney.multiply(l.getNotMoney()).divide(new BigDecimal(100), 2);
								l.setAccrualMoney(l.getAccrualMoney().add(sortoverdueMoney));
							}
						} catch (ParseException e) {
							e.printStackTrace();
							logger.error("小额贷款 -申请展期款项-出错:" + e.getMessage());
						}

					}
					BigDecimal lin = new BigDecimal(0.00);
					// if(l.getIsOverdue()==null){
					if (compare_date(l.getIntentDate(), new Date()) > 0) {
						l.setIsOverdue("是");
					} else {

						l.setIsOverdue("否");
						// }

					}
					if (l.getIncomeMoney().compareTo(lin) == 0) {
						l.setPayInMoney(l.getPayMoney());
					} else {
						l.setPayInMoney(l.getIncomeMoney());
					}
				}
				size = list.size();

			} else {
				size = 0;
			}

		}
		if (slEarlyRepaymentRecord != null && flag1 == 0) {
			String preceptId=this.getRequest().getParameter("preceptId");
			BpFundProject project=bpFundProjectService.get(Long.valueOf(preceptId));
				
			BigDecimal principalMoney=slFundIntentService.getPrincipalMoney(projectId, businessType, sd.format(slEarlyRepaymentRecord.getEarlyDate()),project.getId());
			 List<SlFundIntent> flist=slFundIntentService.listByEarlyDate(projectId, businessType, sd.format(slEarlyRepaymentRecord.getEarlyDate()),"loanInterest",project.getId());
				SlFundIntent f=null;
				if(null!=flist && flist.size()>0){
					f=flist.get(0);
				}
				if(null!=f){
					Date interestEndTime=f.getInterestEndTime();//本期的截止日期
					if(AppUtil.getInterest().equals("0")){//算头不算尾
						interestEndTime=DateUtil.addDaysToDate(f.getInterestEndTime(), 1);
					}
					SlFundIntent bf=null;
					if(project.getAccrualtype().equals("sameprincipalandInterest")){
						List<SlFundIntent> principalist=slFundIntentService.listByEarlyDate(projectId, businessType, sd.format(slEarlyRepaymentRecord.getEarlyDate()),"principalRepayment",project.getId());
						if(null!=principalist && principalist.size()>0){
							bf=principalist.get(0);
						}
					}
				FundPrepaymentFundIntentCreate prepaymentFundIntentCreate=new FundPrepaymentFundIntentCreate(project,slEarlyRepaymentRecord,f.getPayintentPeriod(),interestEndTime,f.getInterestStarTime(),principalMoney,bf);
				prepaymentFundIntentCreate.create(list);
				}
			
		}
		List<SlFundIntent> list1 = new ArrayList<SlFundIntent>();
		if (relateIntentOrCharge == null || relateIntentOrCharge.equals("all")) { // 取全部
			for (SlFundIntent l : list) {
				list1.add(l);
			}
		}
		if (relateIntentOrCharge != null
				&& relateIntentOrCharge.equals("charge")) { // 取费用相关
			for (SlFundIntent l : list) {
				if (businessType.equals("SmallLoan")) {
					if (l.getFundType().equals("serviceMoney")
							|| l.getFundType().equals("consultationMoney")
							|| l.getFundType().equals("loanInterest")) {
						list1.add(l);
					}
				}
			}
		}
		if (relateIntentOrCharge != null
				&& relateIntentOrCharge.equals("intent")) { // 取本金相关
			for (SlFundIntent l : list) {
				if (businessType.equals("SmallLoan")) {
					if (l.getFundType().equals("principalRepayment")
							|| l.getFundType().equals("principalLending")) {
						list1.add(l);
					}
				}
			}
		}
		if (relateIntentOrCharge != null && relateIntentOrCharge.equals("none")) { // 取空

		}

		if (list1 != null) {
			size = list1.size();
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"intentDate", "factDate","interestStarTime","interestEndTime" });
		buff.append(json.serialize(list1));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	public String listbySuperviseRecord() {
		int size = 0;
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		String isUnLoadData = this.getRequest().getParameter("isUnLoadData");
		String isThisSuperviseRecord = getRequest().getParameter(
				"isThisSuperviseRecord");
		String slSuperviseRecordIdstring = getRequest().getParameter(
				"slSuperviseRecordId");
		String preceptId=this.getRequest().getParameter("preceptId");
		if (slSuperviseRecord == null || flag1 == 1) {
			if ((Boolean.valueOf(isUnLoadData))) {

			} else if (isThisSuperviseRecord.equals("no") && slSuperviseRecordIdstring.equals("noid")) {
				list = slFundIntentService.getByProjectAsc(projectId,businessType,((null!=preceptId && !preceptId.equals(""))?Long.valueOf(preceptId):null));
			} else {

				Long slSuperviseRecordId = Long.parseLong(getRequest()
						.getParameter("slSuperviseRecordId"));
				if (isThisSuperviseRecord.equals("yes")) {
					list = slFundIntentService.getlistbyslSuperviseRecordId(
							slSuperviseRecordId, businessType, projectId);
				} else {
					List<SlFundIntent> listall = slFundIntentService
							.getByProjectAsc(projectId, businessType,((null!=preceptId && !preceptId.equals(""))?Long.valueOf(preceptId):null));
					for (SlFundIntent l : listall) {
						if (l.getSlSuperviseRecordId() == null) {
							list.add(l);
						}
						if (l.getSlSuperviseRecordId() != null
								&& !l.getSlSuperviseRecordId().toString()
										.equals(slSuperviseRecordId.toString())) {
							list.add(l);
						}
					}

				}
			}
			if (list != null) {
				for (SlFundIntent l : list) {
					slFundIntentService.evict(l);
					List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
							.getByDicKey(l.getFundType());
					l.setFundTypeName(dictionaryIndependent.get(0)
							.getItemValue());
					if (businessType.equals("SmallLoan")) {
						SlSmallloanProject loan = slSmallloanProjectService
								.get(l.getProjectId());
						if (l.getFundType().equals("principalRepayment")) {
							l.setOverdueRate(loan.getOverdueRateLoan());
						} else {
							l.setOverdueRate(loan.getOverdueRate());
						}
					}
					if (null != l.getNotMoney()
							&& l.getNotMoney().compareTo(new BigDecimal(0)) == 1) {
						BigDecimal sortoverdueMoney = new BigDecimal(0.00);
						long sortday = new Long(0);
						SimpleDateFormat from = new SimpleDateFormat(
								"yyyy-MM-dd 00:00:00");
						String newdate = from.format(new Date());
						Date d;
						try {
							d = from.parse(newdate);

							if (compare_date(d, l.getIntentDate()) == -1
									&& l.getOverdueRate() != null) {
								Date b = new Date();
								Long a = compare_date(new Date(), l
										.getIntentDate());
								sortday = compare_date(l.getIntentDate(),
										new Date()) + 1;
								BigDecimal day1 = new BigDecimal(sortday);
								BigDecimal OverdueMoney = l.getOverdueRate()
										.multiply(day1);
								sortoverdueMoney = OverdueMoney.multiply(
										l.getNotMoney()).divide(
										new BigDecimal(100), 2);
								l.setAccrualMoney(l.getAccrualMoney().add(
										sortoverdueMoney));
							}
						} catch (ParseException e) {
							e.printStackTrace();
							logger.error("小额贷款 -申请展期款项-出错:" + e.getMessage());
						}

					}
					BigDecimal lin = new BigDecimal(0.00);
					// if(l.getIsOverdue()==null){
					if (compare_date(l.getIntentDate(), new Date()) > 0) {
						l.setIsOverdue("是");
					} else {

						l.setIsOverdue("否");
						// }

					}
					if (l.getIncomeMoney().compareTo(lin) == 0) {
						l.setPayInMoney(l.getPayMoney());
					} else {
						l.setPayInMoney(l.getIncomeMoney());
					}
				}
				size = list.size();

			} else {
				size = 0;
			}

		}
		if (slSuperviseRecord != null && flag1 == 0) { // 生成款项计划

			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			List<SlFundIntent> listinti = new ArrayList<SlFundIntent>();
			if(calcutype.equals("SmallLoan")){
				SuperviseFundIntentCreate superviseFundIntentCreate=new SuperviseFundIntentCreate(slSuperviseRecord,"no");
				list=superviseFundIntentCreate.createBorrowerSlFundIntent();
				/*
				if (AppUtil.getInterest().equals("0")) {
					listinti = FundIntentListPro3.getFundIntentDefaultList(
							calcutype, slSuperviseRecord.getAccrualtype(),
							slSuperviseRecord.getIsPreposePayAccrualsupervise(),
							slSuperviseRecord.getPayaccrualType(),
							slSuperviseRecord.getContinuationRateNew().divide(
									BigDecimal.valueOf(100)), slSuperviseRecord
									.getContinuationMoney(), sd
									.format(slSuperviseRecord.getStartDate()), sd
									.format(slSuperviseRecord.getEndDate()),
							slSuperviseRecord.getDayManagementConsultingOfRate()
									.divide(BigDecimal.valueOf(100)),
							slSuperviseRecord.getDayFinanceServiceOfRate().divide(
									BigDecimal.valueOf(100)), slSuperviseRecord
									.getPayintentPeriod(), slSuperviseRecord
									.getIsStartDatePay(), slSuperviseRecord
									.getPayintentPerioDate(), slSuperviseRecord
									.getDayOfEveryPeriod(), "no", null,
							slSuperviseRecord.getIsInterestByOneTime(),
							slSuperviseRecord.getDateMode());
				} else {
					listinti = FundIntentListProtw.getFundIntentDefaultList(
							calcutype, slSuperviseRecord.getAccrualtype(),
							slSuperviseRecord.getIsPreposePayAccrualsupervise(),
							slSuperviseRecord.getPayaccrualType(),
							slSuperviseRecord.getContinuationRateNew().divide(
									BigDecimal.valueOf(100)), slSuperviseRecord
									.getContinuationMoney(), sd
									.format(slSuperviseRecord.getStartDate()), sd
									.format(slSuperviseRecord.getEndDate()),
							slSuperviseRecord.getDayManagementConsultingOfRate()
									.divide(BigDecimal.valueOf(100)),
							slSuperviseRecord.getDayFinanceServiceOfRate().divide(
									BigDecimal.valueOf(100)), slSuperviseRecord
									.getPayintentPeriod(), slSuperviseRecord
									.getIsStartDatePay(), slSuperviseRecord
									.getPayintentPerioDate(), slSuperviseRecord
									.getDayOfEveryPeriod(), "no", null,
							slSuperviseRecord.getIsInterestByOneTime(),
							slSuperviseRecord.getDateMode());
				}
				for (SlFundIntent s : listinti) {
	
					if (!s.getFundType().equals("principalLending")
							&& s.getIncomeMoney().compareTo(new BigDecimal("0")) != 0
							&& s.getAfterMoney().compareTo(new BigDecimal("0")) == 0) {
						s.setBusinessType(calcutype);
						list.add(s);
					}
				}
				
			*/}else if(calcutype.equals("LeaseFinance")){


				if (AppUtil.getInterest().equals("0")) {
					list = LeaseFinanceFundIntentListPro
							.getFundIntentDefaultList(
									calcutype,
									slSuperviseRecord.getAccrualtype(),
									0,
									slSuperviseRecord.getPayaccrualType(),
									slSuperviseRecord.getContinuationRate().divide(BigDecimal.valueOf(100)),
									slSuperviseRecord.getContinuationMoney(),
									sd.format(slSuperviseRecord.getStartDate()),slSuperviseRecord.getEndDate() == null ? null
											: sd.format(slSuperviseRecord.getEndDate()),
									slSuperviseRecord.getManagementConsultingOfRate().divide(BigDecimal.valueOf(100)),
									new BigDecimal(0),
									slSuperviseRecord.getPayintentPeriod(),
									slSuperviseRecord.getIsStartDatePay(),
									slSuperviseRecord.getPayintentPerioDate(),
									slSuperviseRecord.getDayOfEveryPeriod(),
									"no", null, 0, null,
									new BigDecimal(0),
									slSuperviseRecord.getFeePayaccrualType(),
									slSuperviseRecord.getEachRentalReceivable());
				} else {
					list = LeaseFinanceFundIntentListProtw
							.getFundIntentDefaultList(
									calcutype,
									slSuperviseRecord.getAccrualtype(),
									0,
									slSuperviseRecord.getPayaccrualType(),
									slSuperviseRecord.getContinuationRate().divide(BigDecimal.valueOf(100)),
									slSuperviseRecord.getContinuationMoney(),
									sd.format(slSuperviseRecord.getStartDate()),slSuperviseRecord.getEndDate() == null ? null
											: sd.format(slSuperviseRecord.getEndDate()),
									slSuperviseRecord.getManagementConsultingOfRate().divide(BigDecimal.valueOf(100)),
									new BigDecimal(0),
									slSuperviseRecord.getPayintentPeriod(),
									slSuperviseRecord.getIsStartDatePay(),
									slSuperviseRecord.getPayintentPerioDate(),
									slSuperviseRecord.getDayOfEveryPeriod(),
									"no", null, 0, null,
									new BigDecimal(0),
									slSuperviseRecord.getFeePayaccrualType(),
									slSuperviseRecord.getEachRentalReceivable());
				}
			
			}
			size = list.size();
		}

		List<SlFundIntent> list1 = new ArrayList<SlFundIntent>();
		if (relateIntentOrCharge == null || relateIntentOrCharge.equals("all")) { // 取全部
			for (SlFundIntent l : list) {
				list1.add(l);
			}
		}
		if (relateIntentOrCharge != null
				&& relateIntentOrCharge.equals("charge")) { // 取费用相关
			for (SlFundIntent l : list) {
				if (businessType.equals("SmallLoan")) {
					if (l.getFundType().equals("serviceMoney")
							|| l.getFundType().equals("consultationMoney")
							|| l.getFundType().equals("loanInterest")) {
						list1.add(l);
					}
				} else if (businessType.equals("Financing")) {
					if (l.getFundType().equals("FinancingInterest")
							|| l.getFundType().equals(
									"financingconsultationMoney")
							|| l.getFundType().equals("financingserviceMoney")) {
						list1.add(l);
					}
				}
			}
		}
		if (relateIntentOrCharge != null
				&& relateIntentOrCharge.equals("intent")) { // 取本金相关
			for (SlFundIntent l : list) {
				if (businessType.equals("SmallLoan")) {
					if (l.getFundType().equals("principalRepayment")
							|| l.getFundType().equals("principalLending")) {
						list1.add(l);
					}
				} else if (businessType.equals("Financing")) {
					if (l.getFundType().equals("Financingborrow")
							|| l.getFundType().equals("FinancingRepay")) {
						list1.add(l);
					}
				}
			}
		}
		if (relateIntentOrCharge != null && relateIntentOrCharge.equals("none")) { // 取空

		}

		if (list1 != null) {
			size = list1.size();
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate", "factDate", "interestStarTime",
						"interestEndTime" });

		buff.append(json.serialize(list1));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	public String listbyurge() {
		Map<String, String> map = new HashMap<String, String>();
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		Map<String,String> tempMap=GroupUtil.separateFactor(getRequest(),null);
		map.put("companyId", tempMap.get("companyId"));
		PageBean<SlFundIntent> pageBean = new PageBean<SlFundIntent>(start, limit,getRequest());
		slFundIntentService.searchurge(pageBean, map, businessType);

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pageBean.getTotalCounts()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","lastslFundintentUrgeTime", "factDate", "projectStartDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[] { "intentDate","lastslFundintentUrgeTime","factDate","projectStartDate"});
		buff.append(json.serialize(pageBean.getResult()));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	public String list1() { //
		Map<String, String> map = new HashMap<String, String>();
		int size = 0;
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);

		}
		list = slFundIntentService.search(map, businessType);
		size = slFundIntentService.searchsize(map, businessType);

		for (SlFundIntent l : list) {
			List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
					.getByDicKey(l.getFundType());
			l.setFundTypeName(dictionaryIndependent.get(0).getItemValue());
			if (dictionaryIndependent.size() != 0) {
				l.setFundTypeName(dictionaryIndependent.get(0).getItemValue());
			}

			if (l.getBusinessType().equals("SmallLoan")) {
				SlSmallloanProject loan = slSmallloanProjectService.get(l
						.getProjectId());
				if (null != loan.getOverdueRate()) {
					l.setOverdueRate(loan.getOverdueRate());
				} else {
					l.setOverdueRate(new BigDecimal("0"));

				}

			}
			if (l.getBusinessType().equals("Financing")) {
				FlFinancingProject flFinancingProject = flFinancingProjectService
						.get(l.getProjectId());
				l.setOverdueRate(flFinancingProject.getOverdueRate());
			}
			if (l.getBusinessType().equals("Guarantee")) {
				GLGuaranteeloanProject glGuaranteeloanProject = glGuaranteeloanProjectService
						.get(l.getProjectId());
				if (null != glGuaranteeloanProject.getOverdueRate()) {
					l.setOverdueRate(glGuaranteeloanProject.getOverdueRate());
				} else {
					l.setOverdueRate(new BigDecimal("0"));

				}
			}
			BigDecimal lin = new BigDecimal(0.00);
			if (l.getIncomeMoney().compareTo(lin) == 0) {
				l.setPayInMoney(l.getPayMoney());
			} else {
				l.setPayInMoney(l.getIncomeMoney());
			}
			if (l.getNotMoney() == lin) {
				l.setStatus("0");

			} else {
				l.setStatus("1");
			}

			if (l.getNotMoney().compareTo(new BigDecimal(0)) == 1) {
				slFundIntentService.evict(l); // 把对象清除出缓存
				BigDecimal sortoverdueMoney = new BigDecimal(0.00);
				long sortday = new Long(0);
				SimpleDateFormat from = new SimpleDateFormat(
						"yyyy-MM-dd 00:00:00");
				String newdate = from.format(new Date());
				Date d;
				try {
					d = from.parse(newdate);
					if (compare_date(d, l.getIntentDate()) == -1
							&& l.getOverdueRate() != null) {
						sortday = compare_date(l.getIntentDate(), new Date()) + 1;
						BigDecimal day1 = new BigDecimal(sortday);
						BigDecimal OverdueMoney = l.getOverdueRate().multiply(
								day1);
						sortoverdueMoney = OverdueMoney.multiply(
								l.getNotMoney()).divide(new BigDecimal(100), 2);
						if (l.getFundType().equals("GuaranteeToCharge")) {
							sortoverdueMoney = sortoverdueMoney.divide(
									new BigDecimal(300), 26,
									BigDecimal.ROUND_HALF_UP); // 月

						}
						l.setAccrualMoney(l.getAccrualMoney().add(
								sortoverdueMoney));
					}
				} catch (ParseException e) {
					e.printStackTrace();
					logger.error("款项对账功能列表加载-出错:" + e.getMessage());
				}
			}
		}

		// MyCompareintent comp=new MyCompareintent();
		// Collections.sort(list,comp);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate" });
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "factDate" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();

		return SUCCESS;
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
				if (!id.equals("")) {
					slFundIntentService.remove(new Long(id));
				}
				;
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
		SlFundIntent slFundIntent = slFundIntentService.get(fundIntentId);
		BigDecimal lin = new BigDecimal(0.00);
		if (slFundIntent.getIncomeMoney().compareTo(lin) == 0) {
			slFundIntent.setPayInMoney(slFundIntent.getPayMoney());
		} else {
			slFundIntent.setPayInMoney(slFundIntent.getIncomeMoney());
		}

		// Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// //将数据转成JSON格式
		// StringBuffer sb = new StringBuffer("{success:true,data:");
		// sb.append(gson.toJson(slFundIntent));
		// sb.append("}");
		// setJsonString(sb.toString());

		StringBuffer buff = new StringBuffer("{success:true,'data':");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate");
		buff.append(json.serialize(slFundIntent));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;

	}

	/**
	 * 添加及保存操作
	 */
	public String saveDivert() {
		if (slFundIntent.getFundIntentId() == null) {
			AppUser user = ContextUtil.getCurrentUser();
			SlSmallloanProject slSProject = slSmallloanProjectService
					.get(slFundIntent.getProjectId());
			slFundIntent.setBusinessType(slSProject.getBusinessType());
			slFundIntent.setProjectName(slSProject.getProjectName());
			slFundIntent.setProjectNumber(slSProject.getProjectNumber());
			slFundIntent.setAfterMoney(new BigDecimal(0));
			slFundIntent.setAccrualMoney(new BigDecimal(0));
			slFundIntent.setPayMoney(new BigDecimal(0));
			slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
			slFundIntent.setFlatMoney(new BigDecimal(0));
			Short isvalid = 0;
			slFundIntent.setIsValid(isvalid);
			slFundIntent.setIsCheck(isvalid);
			slFundIntent.setCompanyId(slSProject.getCompanyId());
			slFundIntent.setFundType("principalDivert");
			slFundIntent.setStartUserId(user.getUserId());
			slFundIntentService.save(slFundIntent);
		} else {
			SlFundIntent orgSlFundIntent = slFundIntentService.get(slFundIntent
					.getFundIntentId());
			try {
				BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
				slFundIntentService.save(orgSlFundIntent);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;

	}

	public String endDivert() {
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SlFundIntent orgSlFundIntent = slFundIntentService.get(slFundIntent
				.getFundIntentId());
		try {
			BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);

			BigDecimal sortoverdueMoney = new BigDecimal(0.00);
			Long sortday = new Long(0);
			String newdate = from.format(slFundIntent.getFactDate());
			Date d;
			try {
				d = from.parse(newdate);

				if (compare_date(d, orgSlFundIntent.getIntentDate()) == -1
						&& orgSlFundIntent.getPunishAccrual() != null) {
					sortday = compare_date(orgSlFundIntent.getIntentDate(),
							slFundIntent.getFactDate()) + 1;
					BigDecimal day1 = new BigDecimal(sortday);
					BigDecimal OverdueMoney = orgSlFundIntent
							.getPunishAccrual().multiply(day1);
					sortoverdueMoney = OverdueMoney.multiply(
							orgSlFundIntent.getNotMoney()).divide(
							new BigDecimal(100), 2);
					orgSlFundIntent.setPunishDays(Integer.valueOf(sortday
							.toString()));
					orgSlFundIntent.setAccrualMoney(sortoverdueMoney);
				}

				slFundIntentService.save(orgSlFundIntent);
				List<SlPunishInterest> listfaxi = slPunishInterestService
						.listbyisInitialorId(orgSlFundIntent.getFundIntentId());
				SlPunishInterest faxiSlFundIntent = new SlPunishInterest();
				if (listfaxi == null || listfaxi.size() == 0) {

					faxiSlFundIntent.setIntentDate(orgSlFundIntent
							.getFactDate());
					faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
					faxiSlFundIntent.setPayMoney(new BigDecimal("0"));
					faxiSlFundIntent.setNotMoney(sortoverdueMoney);
					faxiSlFundIntent.setAfterMoney(new BigDecimal("0"));
					faxiSlFundIntent.setBusinessType(orgSlFundIntent
							.getBusinessType());
					faxiSlFundIntent.setFundIntentId(orgSlFundIntent
							.getFundIntentId());
					faxiSlFundIntent.setNotMoney(sortoverdueMoney);
					faxiSlFundIntent.setProjectId(orgSlFundIntent
							.getProjectId());
					faxiSlFundIntent.setFundIntentId(orgSlFundIntent
							.getFundIntentId());
					faxiSlFundIntent.setFundType(orgSlFundIntent.getFundType());
					faxiSlFundIntent.setCompanyId(orgSlFundIntent
							.getCompanyId());
				} else {

					faxiSlFundIntent = listfaxi.get(listfaxi.size() - 1);
					if (faxiSlFundIntent.getAfterMoney().compareTo(
							new BigDecimal("0")) == 0) {
						faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
						faxiSlFundIntent.setNotMoney(sortoverdueMoney);
						faxiSlFundIntent.setIntentDate(orgSlFundIntent
								.getFactDate());
					}
				}
				if (faxiSlFundIntent.getIncomeMoney().compareTo(
						new BigDecimal("0")) != 0) {
					slPunishInterestService.save(faxiSlFundIntent);

				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		setJsonString("{success:true}");
		return SUCCESS;

	}

	// 保存尽职调查款项计划
	public String savejson() {
		if (slFundIntentService.getByProjectId(projectId, businessType).size() != 0) {
			for (SlFundIntent l : slFundIntentService.getByProjectId(projectId,
					businessType)) {
				slFundIntentService.remove(l);
			}

		}
		if (null != slFundIentJson && !"".equals(slFundIentJson)) {

			String[] shareequityArr = slFundIentJson.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				// String substr = str.substring(16, 18);
				// String str1 = "\"\"";
				// if (substr.equals("\"\"")) {
				// str = str.substring(19);
				// str = "{" + str;
				//
				// }

				JSONParser parser = new JSONParser(new StringReader(str));

				try {

					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper
							.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(projectId);
					if (businessType.equals("SmallLoan")) {
						SlSmallloanProject loan = slSmallloanProjectService
								.get(projectId);
						SlFundIntent1.setProjectName(loan.getProjectName());
						SlFundIntent1.setProjectNumber(loan.getProjectNumber());

						// if
						// (SlFundIntent1.getFundType().equals("principalLending"))
						// {
						// SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						//							
						// } else
						// if(SlFundIntent1.getFundType().equals("principalLending")){
						// SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						// }

					}
					if (businessType.equals("Financing")) {
						FlFinancingProject flFinancingProject = flFinancingProjectService
								.get(projectId);
						SlFundIntent1.setProjectName(flFinancingProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(flFinancingProject
								.getProjectNumber());
					}
					if (null == SlFundIntent1.getFundIntentId()) {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getIncomeMoney());

						}
						SlFundIntent1.setAfterMoney(new BigDecimal(0));
						SlFundIntent1.setAccrualMoney(new BigDecimal(0));
						SlFundIntent1.setFlatMoney(new BigDecimal(0));
						Short isvalid = 0;
						SlFundIntent1.setIsValid(isvalid);
						SlFundIntent1.setBusinessType(businessType);
						// if(slSmallloanProject !=null){
						// slSmallloanProject.setAccrualMoney(accrualMoney);
						// slSmallloanProjectService.save(slSmallloanProject);
						// }
						slFundIntentService.save(SlFundIntent1);
					}
					setJsonString("{success:true}");

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		}

		return SUCCESS;
	}

	// 保存制定款项计划节点的款项计划
	public String savejson1() {

		if (null != slFundIentJson && !"".equals(slFundIentJson)) {

			String[] shareequityArr = slFundIentJson.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				// String substr = str.substring(16, 18);
				// String str1 = "\"\"";
				// if (substr.equals("\"\"")) {
				// str = str.substring(19);
				// str = "{" + str;
				//
				// }

				JSONParser parser = new JSONParser(new StringReader(str));

				try {

					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper
							.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(projectId);
					if (businessType.equals("SmallLoan")) {
						SlSmallloanProject loan = slSmallloanProjectService
								.get(projectId);
						SlFundIntent1.setProjectName(loan.getProjectName());
						SlFundIntent1.setProjectNumber(loan.getProjectNumber());

					}
					if (businessType.equals("Financing")) {
						FlFinancingProject flFinancingProject = flFinancingProjectService
								.get(projectId);
						SlFundIntent1.setProjectName(flFinancingProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(flFinancingProject
								.getProjectNumber());
					}
					if (null == SlFundIntent1.getFundIntentId()) {

						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getIncomeMoney());

						}
						SlFundIntent1.setAfterMoney(new BigDecimal(0));
						SlFundIntent1.setAccrualMoney(new BigDecimal(0));
						SlFundIntent1.setFlatMoney(new BigDecimal(0));
						Short isvalid = 0;
						SlFundIntent1.setIsValid(isvalid);
						SlFundIntent1.setIsCheck(isvalid);
						SlFundIntent1.setBusinessType(businessType);
						slFundIntentService.save(SlFundIntent1);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getIncomeMoney());

						}
						SlFundIntent slFundIntent2 = slFundIntentService
								.get(SlFundIntent1.getFundIntentId());
						if (slFundIntent2.getAfterMoney().compareTo(
								new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slFundIntent2,
									SlFundIntent1);
							slFundIntent2.setBusinessType(businessType);
							slFundIntentService.updateIntent(slFundIntent2);
						}
					}

					setJsonString("{success:true}");

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		}

		return SUCCESS;
	}

	public String deleteall() {
		if (slFundIntentService.getByProjectId(projectId, businessType).size() != 0) {
			for (SlFundIntent l : slFundIntentService.getByProjectId(projectId,
					businessType)) {
				slFundIntentService.remove(l);
			}

		}
		return SUCCESS;
	}

	public String saveprojectreleadfinance(Long projectId, String businessType) {// 更新项目表的财务字段
		BigDecimal loanInterest = new BigDecimal(0);
		BigDecimal consultationMoney = new BigDecimal(0);
		BigDecimal serviceMoney = new BigDecimal(0);
		BigDecimal payAccrualMoney = new BigDecimal(0);
		// BigDecimal projectMoney=new BigDecimal(0);
		BigDecimal payProjectMoney = new BigDecimal(0);
		BigDecimal flatMoney = new BigDecimal(0);
		List<SlFundIntent> slFundIntents = slFundIntentService.getByProjectId1(
				projectId, businessType);
		List<SlFundIntent> isvalidandchecklist = slFundIntentService
				.getByisvalidAndaftercheck(projectId, businessType);
		if (businessType.equals("SmallLoan")) {
			for (SlFundIntent l : slFundIntents) {
				if (l.getFundType().equals("loanInterest")) {
					loanInterest = loanInterest.add(l.getIncomeMoney());
					payAccrualMoney = payAccrualMoney.add(l.getAfterMoney());
				}
				if (l.getFundType().equals("consultationMoney")) {
					consultationMoney = consultationMoney.add(l
							.getIncomeMoney());
					payAccrualMoney = payAccrualMoney.add(l.getAfterMoney());
				}
				if (l.getFundType().equals("serviceMoney")) {
					serviceMoney = serviceMoney.add(l.getIncomeMoney());
					payAccrualMoney = payAccrualMoney.add(l.getAfterMoney());
				}
				if (l.getFundType().equals("principalRepayment")) {
					// projectMoney=projectMoney.add(l.getIncomeMoney());
					payProjectMoney = payProjectMoney.add(l.getAfterMoney());
				}
				flatMoney = flatMoney.add(l.getFlatMoney());
			}
			for (SlFundIntent c : isvalidandchecklist) {
				if (c.getFundType().equals("loanInterest")) {
					loanInterest = loanInterest.add(c.getAfterMoney());
					payAccrualMoney = payAccrualMoney.add(c.getAfterMoney());
				}
				if (c.getFundType().equals("consultationMoney")) {
					consultationMoney = consultationMoney
							.add(c.getAfterMoney());
					payAccrualMoney = payAccrualMoney.add(c.getAfterMoney());
				}
				if (c.getFundType().equals("serviceMoney")) {
					serviceMoney = serviceMoney.add(c.getAfterMoney());
					payAccrualMoney = payAccrualMoney.add(c.getAfterMoney());
				}
				if (c.getFundType().equals("principalRepayment")) {
					// projectMoney=projectMoney.add(l.getIncomeMoney());
					payProjectMoney = payProjectMoney.add(c.getAfterMoney());
				}
				flatMoney = flatMoney.add(c.getFlatMoney());
			}
			SlSmallloanProject project = slSmallloanProjectService
					.get(projectId);
			project.setAccrualMoney(loanInterest);
			project.setConsultationMoney(consultationMoney);
			project.setServiceMoney(serviceMoney);
			project.setPayAccrualMoney(payAccrualMoney);
			// project.setProjectMoney(projectMoney);
			project.setPayProjectMoney(payProjectMoney);
			project.setFlatMoney(flatMoney);
			if (null == project.getFlatincomechargeMoney()) {
				project.setFlatincomechargeMoney(new BigDecimal(0));
			}
			slSmallloanProjectService.save(project);

		}
		if (businessType.equals("Financing")) {
			for (SlFundIntent l : slFundIntents) {
				if (l.getFundType().equals("FinancingInterest")) {
					loanInterest = loanInterest.add(l.getPayMoney());
					payAccrualMoney = payAccrualMoney.add(l.getAfterMoney());
				}
				if (l.getFundType().equals("financingconsultationMoney")) {
					consultationMoney = consultationMoney.add(l.getPayMoney());
					payAccrualMoney = payAccrualMoney.add(l.getAfterMoney());
				}
				if (l.getFundType().equals("financingserviceMoney")) {
					serviceMoney = serviceMoney.add(l.getPayMoney());
					payAccrualMoney = payAccrualMoney.add(l.getAfterMoney());
				}
				if (l.getFundType().equals("Financingborrow")) {
					// projectMoney=projectMoney.add(l.getIncomeMoney());
					payProjectMoney = payAccrualMoney.add(l.getAfterMoney());
				}
			}
			FlFinancingProject project = flFinancingProjectService
					.get(projectId);
			project.setAccrualMoney(loanInterest);
			project.setConsultationMoney(consultationMoney);
			project.setServiceMoney(serviceMoney);
			// project.setPayAccrualMoney(payAccrualMoney);
			// project.setProjectMoney(projectMoney);
			// project.setPayProjectMoney(payProjectMoney);
			flFinancingProjectService.save(project);

		}

		if (businessType.equals("Guarantee")) {
			BigDecimal paypremiumMoney = new BigDecimal(0);
			BigDecimal paycustomerEarnestMoney = new BigDecimal(0);
			BigDecimal flatpremiumMoney = new BigDecimal(0);
			BigDecimal flatcustomerEarnestMoney = new BigDecimal(0);

			for (SlFundIntent l : slFundIntents) {
				if (l.getFundType().equals("GuaranteeToCharge")) {
					paypremiumMoney = paypremiumMoney.add(l.getAfterMoney());
					flatpremiumMoney = flatpremiumMoney.add(l.getFlatMoney());
				}
				if (l.getFundType().equals("ToCustomGuarantMoney")) {
					paycustomerEarnestMoney = paycustomerEarnestMoney.add(l
							.getAfterMoney());
					flatcustomerEarnestMoney = flatcustomerEarnestMoney.add(l
							.getFlatMoney());
				}

			}

			GLGuaranteeloanProject project = glGuaranteeloanProjectService
					.get(projectId);
			project.setPaypremiumMoney(paypremiumMoney);
			project.setPaycustomerEarnestMoney(paycustomerEarnestMoney);
			project.setFlatcustomerEarnestMoney(flatcustomerEarnestMoney);
			project.setFlatpremiumMoney(flatpremiumMoney);
			if (null == project.getFlatincomechargeMoney()) {
				project.setFlatincomechargeMoney(new BigDecimal(0));
			}
			glGuaranteeloanProjectService.save(project);

		}

		return SUCCESS;
	}

	public String delete() {
		SlFundIntent slFundIntent = slFundIntentService.get(fundIntentId);
		if (slFundIntent.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
			slFundIntentService.remove(slFundIntent);
		}
		saveprojectreleadfinance(slFundIntent.getProjectId(), slFundIntent
				.getBusinessType());

		return SUCCESS;
	}

	public String cancel() {
		SlFundIntent slFundIntent = slFundIntentService.get(fundIntentId);
		Short a = 1;
		slFundIntent.setIsValid(a);
		slFundIntentService.save(slFundIntent);
		saveprojectreleadfinance(slFundIntent.getProjectId(), slFundIntent
				.getBusinessType());
		return SUCCESS;
	}

	public String back() {
		SlFundIntent slFundIntent = slFundIntentService.get(fundIntentId);
		Short a = 0;
		slFundIntent.setIsValid(a);
		slFundIntentService.save(slFundIntent);

		saveprojectreleadfinance(slFundIntent.getProjectId(), slFundIntent
				.getBusinessType());
		return SUCCESS;
	}

	public String cashCheck() {

		BigDecimal cashMoney = slFundQlide.getNotMoney();
		Date factDate = slFundQlide.getFactDate();
		String transactionType = slFundQlide.getTransactionType();
		SlFundQlide slFundQlide = slFundQlideService.getcashQlide("cash")
				.get(0);
		slFundQlide.setFactDate(factDate);
		if (slFundQlide.getPayMoney() != null) {
			slFundQlide.setDialMoney(slFundQlide.getPayMoney());
		} else {
			slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());

		} // 现金流水的初始化

		slFundIntent = slFundIntentService.get(fundIntentId);
		BigDecimal lin = new BigDecimal(0.00);
		if (slFundIntent.getIncomeMoney().compareTo(lin) == 0) {
			slFundIntent.setPayInMoney(slFundIntent.getPayMoney());
		} else {
			slFundIntent.setPayInMoney(slFundIntent.getIncomeMoney());
		}
		// 款项的初始化

		SlFundDetail slFundDetail = new SlFundDetail();
		List<SlFundDetail> setdetails = slFundDetailService
				.getlistbySlFundIntentId(fundIntentId,"1");
		List<SlFundDetail> sortlist = new ArrayList<SlFundDetail>();
		for (SlFundDetail d : setdetails) {
			sortlist.add(d);
		}
		// 详情初始化

		BigDecimal overdueMoney = new BigDecimal(0.00);
		BigDecimal notMoney = slFundIntent.getPayInMoney();
		long day = compare_date(slFundIntent.getIntentDate(), slFundQlide
				.getFactDate());
		if (day == -1 || day == 0) {
			if (null == slFundIntent.getFactDate()) {
				slFundIntent.setIsOverdue("否");
			}
		} else {
			slFundIntent.setIsOverdue("是");
			slFundDetail.setOperTime(new Date());
		}
		BigDecimal intentAfterMoney = slFundIntent.getAfterMoney();
		BigDecimal qlideNotMoney = slFundQlide.getNotMoney();
		BigDecimal detailAfterMoney = qlideNotMoney;
		intentAfterMoney = intentAfterMoney.add(qlideNotMoney);

		slFundIntent.setAfterMoney(slFundIntent.getAfterMoney().add(cashMoney));
		slFundIntent
				.setNotMoney(slFundIntent.getNotMoney().subtract(cashMoney));

		slFundIntent.setFactDate(slFundQlide.getFactDate());

		Set<SlFundDetail> slFundDetails = new HashSet<SlFundDetail>();

		// slFundDetail.setFundQlideId(slFundQlide.getFundQlideId());
		// slFundDetail.setFundIntentId(slFundIntent.getFundIntentId());
		slFundDetail.setSlFundIntent(slFundIntent);
		slFundDetail.setSlFundQlide(slFundQlide);
		slFundDetail.setFactDate(factDate);
		slFundDetail.setAfterMoney(cashMoney);
		slFundDetail.setTransactionType(transactionType);

		sortlist.add(slFundDetail);
		MyCompare comp = new MyCompare();
		Collections.sort(sortlist, comp);

		BigDecimal overdueRate = new BigDecimal(0.00);
		if (slFundIntent.getBusinessType().equals("SmallLoan")) {
			SlSmallloanProject loan = slSmallloanProjectService
					.get(slFundIntent.getProjectId());
			overdueRate = loan.getOverdueRate();
		}
		if (slFundIntent.getBusinessType().equals("Financing")) {
			FlFinancingProject flFinancingProject = flFinancingProjectService
					.get(slFundIntent.getProjectId());
			overdueRate = flFinancingProject.getOverdueRate();
		}
		if (slFundIntent.getBusinessType().equals("Guarantee")) {
			GLGuaranteeloanProject glGuaranteeloanProject = glGuaranteeloanProjectService
					.get(slFundIntent.getProjectId());
			if (glGuaranteeloanProject.getOverdueRate() != null) {
				overdueRate = glGuaranteeloanProject.getOverdueRate();
			}
		}
		BigDecimal sortoverdueMoney = new BigDecimal(0.00);
		BigDecimal sortnotMoney = slFundIntent.getPayInMoney();
		long sortday = new Long(0);
		BigDecimal suboverdueMoney = new BigDecimal(0.00);
		if (compare_date(slFundQlide.getFactDate(), slFundIntent
				.getIntentDate()) == -1) {
			sortday = compare_date(slFundIntent.getIntentDate(), slFundQlide
					.getFactDate()) + 1;
			BigDecimal day1 = new BigDecimal(sortday);
			BigDecimal OverdueMoney = overdueRate.multiply(day1);
			suboverdueMoney = OverdueMoney.multiply(
					slFundDetail.getAfterMoney())
					.divide(new BigDecimal(100), 2);
			sortoverdueMoney = sortoverdueMoney.add(suboverdueMoney);
		}

		if (slFundIntent.getFundType().equals("GuaranteeToCharge")) {
			sortoverdueMoney = sortoverdueMoney.divide(new BigDecimal(300), 26,
					BigDecimal.ROUND_HALF_UP); // 月
		}
		slFundDetail.setOverdueAccrual(sortoverdueMoney);
		slFundDetail.setOverdueNum(sortday);
		slFundDetail.setOperTime(new Date());
		AppUser user = ContextUtil.getCurrentUser();
		slFundDetail.setCheckuser(user.getFullname());
		slFundIntent.setAccrualMoney(slFundIntent.getAccrualMoney().add(
				sortoverdueMoney));
		slFundIntent.setFactDate(sortlist.get(sortlist.size() - 1)
				.getSlFundQlide().getFactDate());

		slFundDetail.setCompanyId(slFundIntent.getCompanyId());
		slFundDetailService.save(slFundDetail);
		slFundIntentService.save(slFundIntent);
		slFundIntentService.computeAccrualnew(slFundIntent,slFundDetail);
		// slFundQlideService.save(slFundQlide);
		/*saveprojectreleadfinance(slFundIntent.getProjectId(), slFundIntent
				.getBusinessType());*/
		return SUCCESS;

	}

	//罚息对账
	public void checkPunishIntent(Long qlideId,String fundIntentId) {
		 slFundQlide = slFundQlideService.get(qlideId);
	        if(slFundQlide.getPayMoney()!=null){
	       	 slFundQlide.setDialMoney(slFundQlide.getPayMoney());
	        }else{
	       	 slFundQlide.setDialMoney(slFundQlide.getIncomeMoney());
	       	 
	        }     
			List<SlPunishInterest> punish = slPunishInterestService.listbyisInitialorId(Long.valueOf(fundIntentId));
			if(punish.size()>0){//有罚息台账就对账
					slPunishInterest = slPunishInterestService.get(punish.get(0).getPunishInterestId());
					BigDecimal lin = new BigDecimal(0.00);
					BigDecimal payinmoney=new BigDecimal(0.00);
					if (slPunishInterest.getIncomeMoney().compareTo(lin) == 0) {
						payinmoney=slPunishInterest.getPayMoney();
					} else {
						payinmoney= slPunishInterest.getIncomeMoney();
					}
					
					//款项初始化
					
					List<SlPunishDetail> setdetails=slPunishDetailService.getlistbyActualChargeId(slPunishInterest.getPunishInterestId());
						List<SlPunishDetail> sortlist = new ArrayList<SlPunishDetail>();
						for(SlPunishDetail d:setdetails){
							sortlist.add(d);
						}
					//详情初始化
						SlPunishDetail slPunishDetail = new SlPunishDetail();
				long day = CompareDate.compare_date(slPunishInterest.getIntentDate()
						, slFundQlide.getFactDate());
				if (day == -1 || day == 0) {
				
					slPunishDetail.setOperTime(new Date());
				} else {
					slPunishDetail.setOperTime(new Date());
				}
			
				BigDecimal chargeAfterMoney = slPunishInterest.getAfterMoney();
				BigDecimal qlideNotMoney = slFundQlide.getNotMoney();
				BigDecimal detailAfterMoney = qlideNotMoney;
				chargeAfterMoney = chargeAfterMoney.add(qlideNotMoney);
			
					detailAfterMoney = slPunishInterest.getNotMoney();
					slFundQlide.setAfterMoney(slPunishInterest.getNotMoney()
							.add(slFundQlide.getAfterMoney()));
					slFundQlide.setNotMoney(slFundQlide.getNotMoney()
							.subtract(slPunishInterest.getNotMoney()));

					slPunishInterest.setAfterMoney(payinmoney);
					slPunishInterest.setNotMoney(new BigDecimal(0.00));

					slPunishInterest.setFactDate(slFundQlide.getFactDate());
					slPunishDetail.setPunishInterestId(slPunishInterest.getPunishInterestId());
					slPunishDetail.setFundQlideId(slFundQlide.getFundQlideId());
					slPunishDetail.setAfterMoney(detailAfterMoney);
					slPunishDetail.setFactDate(slFundQlide.getFactDate());
				AppUser user=ContextUtil.getCurrentUser();
				slPunishDetail.setCheckuser(user.getFullname());
				slPunishDetail.setCompanyId(slPunishInterest.getCompanyId());	
				slPunishDetailService.save(slPunishDetail);
				slFundQlideService.save(slFundQlide);
				slPunishInterestService.save(slPunishInterest);
				SlFundIntent slFundIntent=slFundIntentService.get(slPunishInterest.getFundIntentId());
				slFundIntent.setFaxiAfterMoney(slPunishInterest.getAfterMoney());
				slFundIntentService.save(slFundIntent);
			}
	}	
	public String editQlideCancelCheck() {

		return SUCCESS;

	}

	public String cashCancelAccount() {

		return SUCCESS;

	}

	public List<SlFundDetail> qlidedetail(BigDecimal overdueRate,
			List<SlFundDetail> slFundDetails) {

		List<SlFundDetail> list = new ArrayList<SlFundDetail>();
		try {
			for (SlFundDetail s : slFundDetails) {
				if (null == s.getSlFundQlide().getIsCash()) {
					s.setQlideafterMoney(s.getSlFundQlide().getAfterMoney());
					List<SlBankAccount> sllist = slBankAccountService
							.getbyaccount(s.getSlFundQlide().getMyAccount());
					if (sllist != null) {
						CsBank cb=csBankService.get(sllist.get(0).getOpenBankId());
					
						s.setQlidemyAccount(cb.getBankname() + "-"
								+ sllist.get(0).getName() + "-"
								+ sllist.get(0).getAccount());
					}

					s.setQlidenotMoney(s.getSlFundQlide().getNotMoney());
					s.setQlidetransactionType(s.getSlFundQlide()
							.getTransactionType());
					s.setQlidepayMoney(s.getSlFundQlide().getPayMoney());
					s.setQlideincomeMoney(s.getSlFundQlide().getIncomeMoney());
					s.setQlidecurrency(s.getSlFundQlide().getCurrency());
					s.setOverdueRate(overdueRate);
					s.setIntentFundType(s.getSlFundIntent().getFundType());
				} else {
					if (s.getSlFundQlide().getMyAccount().equals(
							"cahsqlideAccount")) {
						s.setQlidemyAccount("现金账户");
						s
								.setQlideafterMoney(s.getSlFundQlide()
										.getAfterMoney());
						s.setQlidenotMoney(s.getSlFundQlide().getNotMoney());
						s.setQlidetransactionType(s.getSlFundQlide()
								.getTransactionType());
						s.setQlidepayMoney(s.getSlFundQlide().getPayMoney());
						s.setQlideincomeMoney(s.getSlFundQlide()
								.getIncomeMoney());
						s.setQlidecurrency(s.getSlFundQlide().getCurrency());
						s.setOverdueRate(overdueRate);
						s.setIntentFundType(s.getSlFundIntent().getFundType());

					} else if (s.getSlFundQlide().getMyAccount().equals(
							"pingqlideAccount")) {

						s.setQlidemyAccount("平帐");
						s
								.setQlideafterMoney(s.getSlFundQlide()
										.getAfterMoney());
						s.setQlidenotMoney(s.getSlFundQlide().getNotMoney());
						s.setQlidetransactionType(s.getSlFundQlide()
								.getTransactionType());
						s.setQlidepayMoney(null);
						s.setFactDate(s.getOperTime());
						s.setQlideincomeMoney(null);
						s.setQlidecurrency(s.getSlFundQlide().getCurrency());
						s.setOverdueRate(overdueRate);
						s.setIntentFundType(s.getSlFundIntent().getFundType());

					} else {
						s.setQlidetransactionType(s.getTransactionType());
						s.setOverdueRate(overdueRate);
						s.setIntentFundType(s.getSlFundIntent().getFundType());

					}
				}
				list.add(s);
			}
			MyComparedetail comp = new MyComparedetail();
			Collections.sort(list, comp);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String MoneyDetail() {
		try {
			List<SlFundDetail> list=null;
			String type=this.getRequest().getParameter("type");//标识查询资金明细的范围(0查所有既包括罚息、1只查相关款项)
			String projectId=this.getRequest().getParameter("projectId");//项目id
			String period=this.getRequest().getParameter("period");//期数
			if("0".equals(type)){
				list =slFundDetailService.getAllRecordList(projectId,period);
			}else{
				list =slFundDetailService.getlistbySlFundIntentId(fundIntentId,type);
			}
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"intentDate", "factDate" });
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("款项对账功能-查看款项-加载出错" + e.getMessage());
		}
		return SUCCESS;
	}

	public String editIsOverdue() {

		slFundIntentService.updateOverdue(slFundIntent);

		setJsonString("{success:true}");
		return SUCCESS;
	}

	public long compare_date(Date dt1, Date dt2) {
		if (dt1.getTime() > dt2.getTime()) {
			return -1;
		} else if (dt1.getTime() < dt2.getTime()) {
			long msPerDay = 1000 * 60 * 60 * 24; // 一天的毫秒数
			long msSum = dt2.getTime() - dt1.getTime();
			long day = msSum / msPerDay;
			return day;
		} else {
			return 0;
		}
	}

	public static Date getDate() {

		try {
			String stdate = "1980-01-01 00:00:00";
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date Maxdate = new Date();
			Maxdate = df.parse(stdate);
			return Maxdate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;

	}

	// 款项计划列表
	public String fundPlanList() {
		List<SlFundIntent> list = slFundIntentService.getByProjectId(projectId,
				businessType);
		StringBuffer buff = new StringBuffer("{success:true")
				.append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate");
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	// 查看项目信息 款项统计信息
	public String slFundIntentCountInfo() {
		try {
			StringBuffer buff = new StringBuffer("{success:true");
			int overduecount = 0;
			BigDecimal overdueintent = new BigDecimal(0.00);
			BigDecimal overduebreakMoneysum = new BigDecimal(0.00);
			BigDecimal incomeintentsum = new BigDecimal(0.00);
			BigDecimal notincomeintentsum = new BigDecimal(0.00);
			BigDecimal projectMoney = new BigDecimal(0.00);
			BigDecimal payProjectMoney = new BigDecimal(0.00);
			BigDecimal slProjectRepaymentModulus = new BigDecimal(0.00);
			if (businessType.equals("SmallLoan")) {
				SlSmallloanProject loan = slSmallloanProjectService
						.get(projectId);
				slSmallloanProjectService.evict(loan);

				List<SlFundIntent> list1 = slFundIntentService.getByProjectId1(
						projectId, businessType);
				if (null != loan.getProjectMoney()) {
					projectMoney = loan.getProjectMoney();
				}
				for (SlFundIntent s : list1) {
					s.setOverdueRate(loan.getOverdueRate());
					BigDecimal overduebreakMoney = new BigDecimal(0.00);
					if (s.getIsOverdue() != null
							&& !s.getFundType().equals("principalLending")
							&& s.getIsOverdue().equals("是")
							&& s.getNotMoney().compareTo(new BigDecimal(0.00)) == 1) {
						overduecount++;
						overdueintent = overdueintent.add(s.getNotMoney());

						overduebreakMoney = s.getAccrualMoney();
						slFundIntentService.evict(s); // 把对象清除出缓存
						BigDecimal sortoverdueMoney = new BigDecimal(0.00);
						long sortday = new Long(0);
						SimpleDateFormat from = new SimpleDateFormat(
								"yyyy-MM-dd 00:00:00");
						String newdate = from.format(new Date());
						Date d = from.parse(newdate);
						sortday = compare_date(s.getIntentDate(), d) + 1;
						BigDecimal day1 = new BigDecimal(sortday);
						BigDecimal OverdueMoney = s.getOverdueRate().multiply(
								day1);
						sortoverdueMoney = OverdueMoney.multiply(
								s.getNotMoney()).divide(new BigDecimal(100), 2);
						overduebreakMoney = s.getAccrualMoney().add(
								sortoverdueMoney);
						overduebreakMoneysum = overduebreakMoneysum
								.add(overduebreakMoney);

					}
					if (s.getIsOverdue() == null
							&& !s.getFundType().equals("principalLending")
							&& null != s.getNotMoney()
							&& s.getNotMoney().compareTo(new BigDecimal(0.00)) == 1) {

						if (compare_date(s.getIntentDate(), new Date()) > 0) {
							overduecount++;
							overdueintent = overdueintent.add(s.getNotMoney());
							overduebreakMoney = s.getAccrualMoney();
							slFundIntentService.evict(s); // 把对象清除出缓存
							BigDecimal sortoverdueMoney = new BigDecimal(0.00);
							long sortday = new Long(0);
							SimpleDateFormat from = new SimpleDateFormat(
									"yyyy-MM-dd 00:00:00");
							String newdate = from.format(new Date());
							Date d = from.parse(newdate);
							sortday = compare_date(s.getIntentDate(), d) + 1;
							BigDecimal day1 = new BigDecimal(sortday);
							BigDecimal OverdueMoney = s.getOverdueRate() == null ? new BigDecimal(
									"0")
									: s.getOverdueRate().multiply(day1);
							sortoverdueMoney = OverdueMoney.multiply(
									s.getNotMoney()).divide(
									new BigDecimal(100), 2);
							overduebreakMoney = s.getAccrualMoney().add(
									sortoverdueMoney).setScale(2,
									BigDecimal.ROUND_HALF_UP);
							overduebreakMoneysum = overduebreakMoneysum
									.add(overduebreakMoney);
						}
					}

					// if(s.getFundType().equals("loanInterest")
					// ||s.getFundType().equals("consultationMoney")||s.getFundType().equals("serviceMoney")){
					// incomeintentsum=s.getIncomeMoney().add(incomeintentsum);
					// notincomeintentsum=s.getNotMoney().add(notincomeintentsum);
					// }
					if (s.getFundType().equals("principalRepayment")) {
						if (null != s.getAfterMoney()) {
							payProjectMoney = s.getAfterMoney().add(
									payProjectMoney);
						}
					}
				}

				if (null == loan.getAccrualMoney()) {
					loan.setAccrualMoney(new BigDecimal("0"));
				}
				if (null == loan.getServiceMoney()) {
					loan.setServiceMoney(new BigDecimal("0"));
				}
				if (null == loan.getConsultationMoney()) {
					loan.setConsultationMoney(new BigDecimal("0"));
				}
				if (null == loan.getPayAccrualMoney()) {
					loan.setPayAccrualMoney(new BigDecimal("0"));
				}
				incomeintentsum = loan.getAccrualMoney().add(
						loan.getConsultationMoney())
						.add(loan.getServiceMoney());
				notincomeintentsum = incomeintentsum.subtract(loan
						.getPayAccrualMoney());
			} else if (businessType.equals("Pawn")) {

				PlPawnProject loan = plPawnProjectService.get(projectId);
				plPawnProjectService.evict(loan);

				List<SlFundIntent> list1 = slFundIntentService.getByProjectId1(
						projectId, businessType);
				if (null != loan.getProjectMoney()) {
					projectMoney = loan.getProjectMoney();
				}
				for (SlFundIntent s : list1) {
					s.setOverdueRate(loan.getOverdueRate());
					BigDecimal overduebreakMoney = new BigDecimal(0.00);
					if (s.getIsOverdue() != null
							&& !s.getFundType().equals("pawnPrincipalLending")
							&& s.getIsOverdue().equals("是")
							&& s.getNotMoney().compareTo(new BigDecimal(0.00)) == 1) {
						overduecount++;
						overdueintent = overdueintent.add(s.getNotMoney());

						overduebreakMoney = s.getAccrualMoney();
						slFundIntentService.evict(s); // 把对象清除出缓存
						BigDecimal sortoverdueMoney = new BigDecimal(0.00);
						long sortday = new Long(0);
						SimpleDateFormat from = new SimpleDateFormat(
								"yyyy-MM-dd 00:00:00");
						String newdate = from.format(new Date());
						Date d = from.parse(newdate);
						sortday = compare_date(s.getIntentDate(), d) + 1;
						BigDecimal day1 = new BigDecimal(sortday);
						BigDecimal OverdueMoney = s.getOverdueRate().multiply(
								day1);
						sortoverdueMoney = OverdueMoney.multiply(
								s.getNotMoney()).divide(new BigDecimal(100), 2);
						overduebreakMoney = s.getAccrualMoney().add(
								sortoverdueMoney);
						overduebreakMoneysum = overduebreakMoneysum
								.add(overduebreakMoney);

					}
					if (s.getIsOverdue() == null
							&& !s.getFundType().equals("pawnPrincipalLending")
							&& null != s.getNotMoney()
							&& s.getNotMoney().compareTo(new BigDecimal(0.00)) == 1) {

						if (compare_date(s.getIntentDate(), new Date()) > 0) {
							overduecount++;
							overdueintent = overdueintent.add(s.getNotMoney());
							overduebreakMoney = s.getAccrualMoney();
							slFundIntentService.evict(s); // 把对象清除出缓存
							BigDecimal sortoverdueMoney = new BigDecimal(0.00);
							long sortday = new Long(0);
							SimpleDateFormat from = new SimpleDateFormat(
									"yyyy-MM-dd 00:00:00");
							String newdate = from.format(new Date());
							Date d = from.parse(newdate);
							sortday = compare_date(s.getIntentDate(), d) + 1;
							BigDecimal day1 = new BigDecimal(sortday);
							BigDecimal OverdueMoney = s.getOverdueRate() == null ? new BigDecimal(
									"0")
									: s.getOverdueRate().multiply(day1);
							sortoverdueMoney = OverdueMoney.multiply(
									s.getNotMoney()).divide(
									new BigDecimal(100), 2);
							overduebreakMoney = s.getAccrualMoney().add(
									sortoverdueMoney).setScale(2,
									BigDecimal.ROUND_HALF_UP);
							overduebreakMoneysum = overduebreakMoneysum
									.add(overduebreakMoney);
						}
					}

					// if(s.getFundType().equals("loanInterest")
					// ||s.getFundType().equals("consultationMoney")||s.getFundType().equals("serviceMoney")){
					// incomeintentsum=s.getIncomeMoney().add(incomeintentsum);
					// notincomeintentsum=s.getNotMoney().add(notincomeintentsum);
					// }
					if (s.getFundType().equals("principalRepayment")) {
						if (null != s.getAfterMoney()) {
							payProjectMoney = s.getAfterMoney().add(
									payProjectMoney);
						}
					}
				}

				/*
				 * if (null == loan.getAccrualMoney()) {
				 * loan.setAccrualMoney(new BigDecimal("0")); } if (null ==
				 * loan.getServiceMoney()) { loan.setServiceMoney(new
				 * BigDecimal("0")); } if (null == loan.getConsultationMoney())
				 * { loan.setConsultationMoney(new BigDecimal("0")); } if (null
				 * == loan.getPayAccrualMoney()) { loan.setPayAccrualMoney(new
				 * BigDecimal("0")); } incomeintentsum =
				 * loan.getAccrualMoney().add(
				 * loan.getConsultationMoney()).add(loan.getServiceMoney());
				 * notincomeintentsum = incomeintentsum.subtract(loan
				 * .getPayAccrualMoney());
				 */

			}
			buff.append(",'slProjectRepaymentModulus':"
					+ slProjectRepaymentModulus);
			buff.append(",'slProjectMoney':" + projectMoney);
			buff.append(",'slProjectIncomeintentsum':" + incomeintentsum);//
			buff.append(",'slProjectNotincomeintentsum':" + notincomeintentsum);
			buff.append(",'slProjectoverduebreakMoneysum':"
					+ overduebreakMoneysum
							.setScale(2, BigDecimal.ROUND_HALF_UP));
			buff.append(",'slProjectMoneyBalance':"
					+ (projectMoney.subtract(payProjectMoney)));
			buff.append(",'slProjectoverduecount':" + overduecount);
			buff.append(",'slProjectoverdueintent':" + overdueintent);
			List<SlFundIntent> list = slFundIntentService
					.getByProjectIdAndFundType(projectId, new Integer(1149));
			BigDecimal payTotalCommission = new BigDecimal(0.00);
			for (SlFundIntent sfi : list) {
				payTotalCommission = payTotalCommission.add(sfi.getPayMoney());
			}
			// buff.append(",'slProjectCommissionBalance':" + (commission -
			// payTotalCommission));
			buff.append("}");
			jsonString = buff.toString();
			setJsonString(jsonString);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	public String flFundIntentCountInfo() {
		try {
			StringBuffer buff = new StringBuffer("{success:true");
			BigDecimal projectMoney = new BigDecimal(0.00);
			BigDecimal payProjectMoney = new BigDecimal(0.00);
			BigDecimal chargeMoney = new BigDecimal(0.00);
			BigDecimal payChargeMoney = new BigDecimal(0.00);
			List<SlFundIntent> list = slFundIntentService.getByProjectId1(
					projectId, businessType);
			for (SlFundIntent s : list) {
				if (s.getFundType().equals("Financingborrow")) {
					projectMoney = projectMoney.add(s.getIncomeMoney());
				}
				if (s.getFundType().equals("FinancingRepay")) {
					payProjectMoney = payProjectMoney.add(s.getPayMoney());
				}
				if (s.getFundType().equals("FinancingInterest")
						|| s.getFundType().equals("financingconsultationMoney")
						|| s.getFundType().equals("financingserviceMoney")) {
					chargeMoney = chargeMoney.add(s.getPayMoney());
					payChargeMoney = payChargeMoney.add(s.getAfterMoney());

				}
			}
			buff.append(",'flProjectMoney':" + projectMoney);
			buff.append(",'flProjectMoneyBalance':"
					+ (projectMoney.subtract(payProjectMoney)));
			buff.append(",'flProjectChargeMoney':" + chargeMoney);
			buff.append(",'flProjectChargeMoneyBalance':"
					+ (chargeMoney.subtract(payChargeMoney)));
			buff.append("}");
			jsonString = buff.toString();
			setJsonString(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	// 项目流水记录
	public String slProjectFundDetail() {
		try {
			BigDecimal overdueRate = new BigDecimal(0.00);
			if (businessType.equals("SmallLoan")) {
				SlSmallloanProject loan = slSmallloanProjectService
						.get(projectId);
				overdueRate = loan.getOverdueRate();
			}
			if (businessType.equals("Financing")) {
				FlFinancingProject flFinancingProject = flFinancingProjectService
						.get(projectId);
				overdueRate = flFinancingProject.getOverdueRate();
			}
			if (businessType.equals("Guarantee")) {
				GLGuaranteeloanProject glGuaranteeloanProject = glGuaranteeloanProjectService
						.get(projectId);
				if (glGuaranteeloanProject.getOverdueRate() != null) {
					overdueRate = glGuaranteeloanProject.getOverdueRate();
				}

			}
			List<SlFundIntent> fundIntentList = slFundIntentService
					.getByProjectId1(projectId, businessType);
			List<SlFundDetail> list = new ArrayList<SlFundDetail>();
			for (SlFundIntent sfi : fundIntentList) {
				List<SlFundDetail> list1 = new ArrayList<SlFundDetail>();
				slFundIntent = sfi;
				List<SlFundDetail> slFundDetails = new ArrayList<SlFundDetail>();
				slFundDetails = slFundDetailService
						.getlistbySlFundIntentId(slFundIntent.getFundIntentId(),"1");
				list1 = qlidedetail(overdueRate, slFundDetails);
				for (SlFundDetail s : list1) {
					list.add(s);

				}
			}

			MyComparedetail comp = new MyComparedetail();
			Collections.sort(list, comp);

			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(list.size()).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
					"factDate");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"intentDate", "factDate" });
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString = buff.toString();

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		return SUCCESS;
	}

	public String listbyId() {

		QueryFilter filter = new QueryFilter(getRequest());
		String paramValue = (String) getRequest().getParameter(
				"Q_fundIntentId_SN_EQ");
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		list.add(slFundIntentService.get(Long.valueOf(paramValue)));

		for (SlFundIntent l : list) {
			List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
					.getByDicKey(l.getFundType());
			l.setFundTypeName(dictionaryIndependent.get(0).getItemValue());
			if (dictionaryIndependent.size() != 0) {
				l.setFundTypeName(dictionaryIndependent.get(0).getItemValue());
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate" });
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "factDate" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	public String mangOfintentexpire() { //
		int sizetoday = 0;
		int size5 = 0;
		int sizemonth = 0;
		int sizeoverdue = 0;
		int sizetodayInterent = 0;
		int size5Interent = 0;
		int sizemonthInterent = 0;
		int sizeoverdueInterent = 0;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.CHINESE);
		String str1 = simpleDateFormat.format(new Date());
		Date date2 = DateUtil.addDaysToDate(DateUtil.parseDate(str1,
				"yyyy-MM-dd"), 5);
		String str2 = simpleDateFormat.format(date2);
		Date date3 = DateUtil.addDaysToDate(DateUtil.parseDate(str1,
				"yyyy-MM-dd"), 30);
		String str3 = simpleDateFormat.format(date3);
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("Q_intentDate_D_GE", str1);
		map1.put("Q_intentDate_D_LE", str1);
		map1.put("Q_intentType_SN_EQ", "1");
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("Q_intentDate_D_GE", str1);
		map2.put("Q_intentDate_D_LE", str2);
		map2.put("Q_intentType_SN_EQ", "1");
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("Q_intentDate_D_GE", str1);
		map3.put("Q_intentDate_D_LE", str3);
		map3.put("Q_intentType_SN_EQ", "1");
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("Q_intentDate_D_LE", str1);
		map4.put("Q_intentType_SN_EQ", "1");
		String userIdsStr = "";
		PagingBean pb = new PagingBean(start, limit);
		String roleTypeStr = ContextUtil.getRoleTypeSession();
		if (!isGrantedShowAllProjects && !roleTypeStr.equals("control")) {// 如果用户不拥有查看所有项目信息的权限
			userIdsStr = appUserService.getUsersStr();// 当前登录用户以及其所有下属用户
		}
		List<VSmallloanProject> projectList = vSmallloanProjectService
				.getProjectList(userIdsStr, null, pb, getRequest());
		String projectIdStr = "";
		for (int i = 0; i < projectList.size(); i++) {
			if (i != projectList.size() - 1) {
				projectIdStr += projectList.get(i).getProjectId() + ",";
			} else {
				projectIdStr += projectList.get(i).getProjectId();
			}

		}
		sizetoday = slFundIntentService.searchsizeurge(projectIdStr, map1,
				businessType);
		size5 = slFundIntentService.searchsizeurge(projectIdStr, map2,
				businessType);
		sizemonth = slFundIntentService.searchsizeurge(projectIdStr, map3,
				businessType);
		sizeoverdue = slFundIntentService.searchsizeurge(projectIdStr, map4,
				businessType);

		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("Q_intentDate_D_GE", str1);
		map5.put("Q_intentDate_D_LE", str1);
		map5.put("Q_intentType_SN_EQ", "2");
		Map<String, String> map6 = new HashMap<String, String>();
		map6.put("Q_intentDate_D_GE", str1);
		map6.put("Q_intentDate_D_LE", str2);
		map6.put("Q_intentType_SN_EQ", "2");
		Map<String, String> map7 = new HashMap<String, String>();
		map7.put("Q_intentDate_D_GE", str1);
		map7.put("Q_intentDate_D_LE", str3);
		map7.put("Q_intentType_SN_EQ", "2");
		Map<String, String> map8 = new HashMap<String, String>();
		map8.put("Q_intentDate_D_LE", str1);
		map8.put("Q_intentType_SN_EQ", "2");
		sizetodayInterent = slFundIntentService.searchsizeurge(projectIdStr,
				map5, businessType);
		size5Interent = slFundIntentService.searchsizeurge(projectIdStr, map6,
				businessType);
		sizemonthInterent = slFundIntentService.searchsizeurge(projectIdStr,
				map7, businessType);
		sizeoverdueInterent = slFundIntentService.searchsizeurge(projectIdStr,
				map8, businessType);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,sizetoday:"
				+ sizetoday + ",size5:" + size5 + ",sizemonth:" + sizemonth
				+ ",sizeoverdue:" + sizeoverdue + ",sizetodayInterent:"
				+ sizetodayInterent + ",size5Interent:" + size5Interent
				+ ",sizemonthInterent:" + sizemonthInterent
				+ ",sizeoverdueInterent:" + sizeoverdueInterent);

		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	public String listbyLedger() { // 台账
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		// 获取顺延日和 宽限日
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		String typetab = getRequest().getParameter("typetab");
		String fundType = getRequest().getParameter("fundType");
		if (fundType != null && !fundType.equals("")) {
			if (fundType.equals("all")) {
				fundType = "('loanInterest','consultationMoney','serviceMoney','principalRepayment')";
			}
		} else {
			fundType = "('loanInterest','consultationMoney','serviceMoney','principalRepayment')";
		}

		// 拿出列表
		if (businessType != null && !businessType.equals("")) {
			if (businessType.equals("consultationMoney") || businessType.equals("serviceMoney")) {
				businessType = "SmallLoan";
			}
		}
		PageBean<SlFundIntent> pageBean = new PageBean<SlFundIntent>(start, limit,getRequest());
		
		slFundIntentService.listbyLedger(pageBean,businessType, fundType,typetab, pb, map);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pageBean.getTotalCounts()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate", "interestStarTime", "interestEndTime");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[] {"intentDate","factDate","interestStarTime","interestEndTime"});
		buff.append(json.serialize(pageBean.getResult()));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	public String listbyLedgerNew(){
		//综合台账
		//分页开始
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map1=GroupUtil.separateFactor(getRequest(),ids);
		String Q_proj_Name_N_EQ = this.getRequest().getParameter("Q_proj_Name_N_EQ");
		String Q_projNum_N_EQ = this.getRequest().getParameter("Q_projNum_N_EQ");
		String Q_intentDate_D_GE = this.getRequest().getParameter("Q_intentDate_D_GE");
		String Q_intentDate_D_LE = this.getRequest().getParameter("Q_intentDate_D_LE");
		String startFactDate = this.getRequest().getParameter("startFactDate");
		String endFactDate = this.getRequest().getParameter("endFactDate");
		String flagMoney = this.getRequest().getParameter("flagMoney");
		if(Q_proj_Name_N_EQ!=null&&!"".equals(Q_proj_Name_N_EQ)){
			map1.put("Q_proj_Name_N_EQ", Q_proj_Name_N_EQ);
		}

		if(Q_projNum_N_EQ!=null&&!"".equals(Q_projNum_N_EQ)){
			map1.put("Q_projNum_N_EQ", Q_projNum_N_EQ);
		}
		
		if(Q_intentDate_D_GE!=null&&!"".equals(Q_intentDate_D_GE)){
			map1.put("Q_intentDate_D_GE", Q_intentDate_D_GE);
		}
		
		if(Q_intentDate_D_LE!=null&&!"".equals(Q_intentDate_D_LE)){
			map1.put("Q_intentDate_D_LE", Q_intentDate_D_LE);
		}
		
		if(startFactDate!=null&&!"".equals(startFactDate)){
			map1.put("startFactDate", startFactDate);
		}

		if(endFactDate!=null&&!"".equals(endFactDate)){
			map1.put("endFactDate",endFactDate);
		}
		
		if(flagMoney!=null&&!"".equals(flagMoney)){
			map1.put("flagMoney", flagMoney);
		}
		
		PageBean<SlFundIntent> pageBean = new PageBean<SlFundIntent>(start,limit,getRequest());
		slFundIntentService.projectList(pageBean, map1);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pageBean.getTotalCounts()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(pageBean.getResult()));
		buff.append("}");
		jsonString = buff.toString();		
		return SUCCESS;
	}
	
	public String listbyLedger1(){
		// 台账
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		String typetab = getRequest().getParameter("typetab");
		String fundType = getRequest().getParameter("fundType");
		// 拿出列表
		PageBean<SlFundIntent> pageBean = new PageBean<SlFundIntent>(start, limit,getRequest());
		slFundIntentService.listbyLedger(pageBean,businessType, fundType,typetab, pb, map);
		List<SlFundIntent> list=pageBean.getResult();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate", "interestStarTime", "interestEndTime");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[] {"intentDate","factDate","interestStarTime","interestEndTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	public String getlistbyfundType() {
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String fundType = this.getRequest().getParameter("fundType");
		List<SlFundIntent> list = slFundIntentService.getByProjectId3(
				projectId, businessType, fundType);
		for (SlFundIntent l : list) {

			l.setStartUserName(appUserService.get(l.getStartUserId())
					.getUsername());
			if (l.getFactDate() == null) {

				BigDecimal sortoverdueMoney = new BigDecimal(0.00);
				Long sortday = new Long(0);
				String newdate = from.format(new Date());
				Date d;
				try {
					d = from.parse(newdate);

					if (compare_date(d, l.getIntentDate()) == -1
							&& l.getPunishAccrual() != null) {
						sortday = compare_date(l.getIntentDate(), new Date()) + 1;
						BigDecimal day1 = new BigDecimal(sortday);
						BigDecimal OverdueMoney = l.getPunishAccrual()
								.multiply(day1);
						sortoverdueMoney = OverdueMoney.multiply(
								l.getNotMoney()).divide(new BigDecimal(100), 2);
						l.setPunishDays(Integer.valueOf(sortday.toString()));
						l.setAccrualMoney(sortoverdueMoney);
					}
					slFundIntentService.save(l);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(null != list ? list.size() : 0).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
				"intentDate", "factDate" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 款项台账导出方法
	 */
	public void intentDownLoad() {
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		String typetab = getRequest().getParameter("typetab");
		String fundType = getRequest().getParameter("fundType");

		// 拿出列表
		PageBean<SlFundIntent> pageBean = new PageBean<SlFundIntent>(start, limit,getRequest());
		slFundIntentService.listbyLedger(pageBean,businessType, fundType,typetab, null, map);
		List<SlFundIntent> list=pageBean.getResult();
		
		for(SlFundIntent sl:list){
			String fundTypes = sl.getFundType();
			if(fundTypes!=null){
				if(fundTypes.equals("principalLending")){
					sl.setFundTypeName("贷款本金放贷");
				}else if(fundTypes.equals("principalRepayment")){
					sl.setFundTypeName("贷款本金偿还");
				}else if(fundTypes.equals("backInterest")){
					sl.setFundTypeName("其他支出台账");
				}else if(fundTypes.equals("serviceMoney")){
					sl.setFundTypeName("财务服务费用");
				}else if(fundTypes.equals("consultationMoney")){
					sl.setFundTypeName("咨询管理费用");
				}else{
					sl.setFundTypeName("贷款利息");
				}
			}
			/*if (fundType.equals("('principalLending')")) {
				if (sl.getNotMoney().compareTo(new BigDecimal("0")) != 0) {
					if(sl.getIntentDate().before(new Date())){
						sl.setTabType("欠放款项");
					}else{
						sl.setTabType("应放款项");
					}
				}
				if (sl.getNotMoney().compareTo(new BigDecimal("0")) == 0) {
					sl.setTabType("已放款项");
				}
				
			}*/
		}
		if(fundType.equals("('principalLending')")){//本金
			String[] tableHeader = { "序号", "项目名称", "项目编号", "资金类型", "放款金额",
					"计划放款日", "实际放款日",  "已对账金额", "未对账金额",
					"核销金额","放款状态","备注" };
			String[] fields = {"projectName","projectNumber","fundTypeName","payMoney",
					"intentDate","factDate","afterMoney","notMoney",
					"flatMoney","tabType","remark"};
			try {
				ExportExcel.export(tableHeader, fields, list,"本金放款台账", SlFundIntent.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(fundType.equals("('principalRepayment')")){
			String[] tableHeader = { "序号", "项目名称", "项目编号", "资金类型", "放款金额",
					"计划到账日", "实际到账日", "计息开始日期","计息结束日期", "已对账金额", "未对账金额",
					"核销金额","顺延至","罚息金额","顺延息","罚息金额","罚息已对账金额","放款状态","备注" };
			String[] fields = {"projectName","projectNumber","fundTypeName","incomeMoney",
					"intentDate","factDate","interestStarTime","interestEndTime","afterMoney","notMoney",
					"flatMoney","continueDay","amountPayable","continueInterest","accrualMoney","faxiAfterMoney","tabType","remark"};
			try {
				ExportExcel.export(tableHeader, fields, list,"本金收款台账", SlFundIntent.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(fundType.equals("('backInterest')")){
			String[] tableHeader = { "序号", "项目名称", "项目编号", "资金类型", "应付金额",
					"计划到账日", "实际到账日", "已对账金额", "未对账金额","核销金额"
					/*,"顺延至","罚息金额","顺延息","罚息金额","罚息已对账金额"*/,"款项状态","备注" };
			String[] fields = {"projectName","projectNumber","fundTypeName","payMoney",
					"intentDate","factDate","afterMoney","notMoney","flatMoney"
					/*,"continueDay","amountPayable","continueInterest","accrualMoney","faxiAfterMoney"*/,"tabType","remark"};
			try {
				ExportExcel.export(tableHeader, fields, list,"其他支出台账", SlFundIntent.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			String[] tableHeader = { "序号", "项目名称", "项目编号", "资金类型", "计划收入金额",
					"计划到帐日", "实际到帐日", "计息开始日期", "计息结束日期", "已对账金额", "未对账金额",
					"核销金额","顺延至","宽限至","罚息总额","罚息已对账金额","状态","备注" };
			String[] fields = {"projectName","projectNumber","fundTypeName","incomeMoney",
					"intentDate","factDate","interestStarTime","interestEndTime","afterMoney","notMoney",
					"flatMoney","continueDay","graceDay","accrualMoney","faxiAfterMoney","tabType","remark"};
			try {
				String title="利息收款台账";
				if(fundType.equals("('consultationMoney')")){
					title="管理咨询费收款台账";
				}else if(fundType.equals("('serviceMoney')")){
					title="财务服务费收款台账";
				}
				ExportExcel.export(tableHeader, fields, list,title, SlFundIntent.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String download() {
		List<SlFundIntent> list = slFundIntentService.getByProjectId1(
				projectId, businessType);
		SlSmallloanProject loan = slSmallloanProjectService.get(projectId);
		for (SlFundIntent l : list) {

			l.setPunishAccrual(loan.getOverdueRate() == null ? BigDecimal
					.valueOf(0) : loan.getOverdueRate());

		}
		String[] tableHeader = { "序号", "资金类型", "计划收入金额", "计划支出金额'", "计划到帐日",
				"实际到帐日", "已核销金额", "未核销金额", "已平账金额", "逾期费率", "逾期违约金总额" };
		try {
			ExcelHelper.export(list, tableHeader, "还款计划表");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void synthesizeExcel(){
		//综合台账
		//分页开始
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map1=GroupUtil.separateFactor(getRequest(),ids);
		String Q_proj_Name_N_EQ = this.getRequest().getParameter("Q_proj_Name_N_EQ");
		String Q_projNum_N_EQ = this.getRequest().getParameter("Q_projNum_N_EQ");
		String Q_intentDate_D_GE = this.getRequest().getParameter("Q_intentDate_D_GE");
		String Q_intentDate_D_LE = this.getRequest().getParameter("Q_intentDate_D_LE");
		String startFactDate = this.getRequest().getParameter("startFactDate");
		String endFactDate = this.getRequest().getParameter("endFactDate");
		String flagMoney = this.getRequest().getParameter("flagMoney");
		if(Q_proj_Name_N_EQ!=null&&!"".equals(Q_proj_Name_N_EQ)){
			map1.put("Q_proj_Name_N_EQ", Q_proj_Name_N_EQ);
		}
		if(Q_projNum_N_EQ!=null&&!"".equals(Q_projNum_N_EQ)){
			map1.put("Q_projNum_N_EQ", Q_projNum_N_EQ);
		}
		if(Q_intentDate_D_GE!=null&&!"".equals(Q_intentDate_D_GE)){
			map1.put("Q_intentDate_D_GE", Q_intentDate_D_GE);
		}
		if(Q_intentDate_D_LE!=null&&!"".equals(Q_intentDate_D_LE)){
			map1.put("Q_intentDate_D_LE", Q_intentDate_D_LE);
		}
		if(startFactDate!=null&&!"".equals(startFactDate)){
			map1.put("startFactDate", startFactDate);
		}
		if(endFactDate!=null&&!"".equals(endFactDate)){
			map1.put("endFactDate",endFactDate);
		}
		if(flagMoney!=null&&!"".equals(flagMoney)){
			map1.put("flagMoney", flagMoney);
		}
		PageBean<SlFundIntent> pageBean = new PageBean<SlFundIntent>(start,limit,getRequest());
		slFundIntentService.projectList(pageBean, map1);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pageBean.getTotalCounts()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(pageBean.getResult()));
		buff.append("}");
		jsonString = buff.toString();		
		String[] tableHeader = { "序号", "借款项目名称", "项目编号","期数","本金(元)" ,
				"利息(元)","罚息(元)","逾期(元)","合计金额","未对账合计金额","逾期天数","计划还款日","实际到账金额","实际到账日"};
		String[] fields = {"projectName","projectNumber","payintentPeriod","principalRepayment",
				"loanInterest","punishMoney","overdureMoney","synthesizeMoney","notSynthesizeMoney","punishDays","intentDate","synthesizeAfterMoney","factDate"};
		try {
			ExportExcel.export(tableHeader, fields, pageBean.getResult(),"综合收款台账", SlFundIntent.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void leaseDownload() {
		String[] tableHeader = { "日期", "应收租金", "租赁费", "租赁成本", "未付本金", "出资额" };
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd");
			FlLeaseFinanceProject project = flLeaseFinanceProjectService
					.get(projectId);
			String customerName = "";
			if (project.getOppositeType().equals("company_customer")) {
				Enterprise enterprise = (Enterprise) creditBaseDao.getById(
						Enterprise.class, project.getOppositeID().intValue());
				if (null != enterprise) {
					customerName = enterprise.getEnterprisename();
				}
			} else if (project.getOppositeType().equals("person_customer")) {
				Person person = personService.getById(project.getOppositeID()
						.intValue());
				if (null != person) {
					customerName = person.getName();
				}
			}
	/*		String payaccrualType = this.getRequest().getParameter(
					"payaccrualType");
			String payintentPeriod = this.getRequest().getParameter(
					"payintentPeriod");
			String dayOfEveryPeriod = this.getRequest().getParameter(
					"dayOfEveryPeriod");
			String leaseDepositRate = this.getRequest().getParameter(
					"leaseDepositRate");
			String leaseDepositMoney = this.getRequest().getParameter(
					"leaseDepositMoney");
			String rentalFeeRate = this.getRequest().getParameter(
					"rentalFeeRate");
			String rentalFeeMoney = this.getRequest().getParameter(
					"rentalFeeMoney");
			String leaseRetentionFeeRate = this.getRequest().getParameter(
					"leaseRetentionFeeRate");
			String leaseRetentionFeeMoney = this.getRequest().getParameter(
					"leaseRetentionFeeMoney");
			String rentalRate = this.getRequest().getParameter("rentalRate");
			String rentalMoney = this.getRequest().getParameter("rentalMoney");
			String eachRentalReceivable = this.getRequest().getParameter(
					"eachRentalReceivable");
			String projectMoney = this.getRequest()
					.getParameter("projectMoney");
			String allMoney = this.getRequest().getParameter("allMoney");
			String connotationRate = this.getRequest().getParameter(
					"connotationRate");
			String accrualtype = this.getRequest().getParameter("accrualtype");
			String startDate = this.getRequest()
					.getParameter("startDate");
			String intentDate = this.getRequest()
					.getParameter("intentDate");
			project.setPayaccrualType(payaccrualType);
			project.setPayintentPeriod(Integer.valueOf(payintentPeriod));
			if (payaccrualType.equals("owerPay")) {
				project.setDayOfEveryPeriod(Integer.valueOf(dayOfEveryPeriod));
			}
			project.setLeaseDepositRate(new BigDecimal(leaseDepositRate));
			project.setLeaseDepositMoney(new BigDecimal(leaseDepositMoney));
			project.setRentalFeeRate(new BigDecimal(rentalFeeRate));
			project.setRentalFeeMoney(new BigDecimal(rentalFeeMoney));
			project.setLeaseRetentionFeeRate(new BigDecimal(
					leaseRetentionFeeRate));
			project.setLeaseRetentionFeeMoney(new BigDecimal(
					leaseRetentionFeeMoney));
			project.setRentalRate(new BigDecimal(rentalRate));
			project.setRentalMoney(new BigDecimal(rentalMoney));
			project.setProjectMoney(new BigDecimal(projectMoney));
			project.setAllMoney(new BigDecimal(allMoney));
			if (null != eachRentalReceivable
					&& !eachRentalReceivable.equals("")) {
				project.setEachRentalReceivable(new BigDecimal(
						eachRentalReceivable));
			}
			project.setConnotationRate(new BigDecimal(connotationRate));*/
			StringBuffer buff = new StringBuffer("{\"ri\":");
			buff.append(sd.format(project.getStartDate()));
			buff.append(",\"yszj\":");
			buff.append(project.getAllMoney());
			buff.append(",\"zlf\":");
			buff.append(project.getRentalMoney());
			buff.append(",\"ysbj\":");
			buff.append(project.getProjectMoney());
			buff.append(",\"wfbj\":");
			buff.append(project.getProjectMoney());
			buff.append(",\"cze\":");
			buff.append(project.getLeaseDepositMoney().add(
					project.getRentalFeeMoney()).subtract(
					project.getProjectMoney()));
			buff.append("}@");
			BigDecimal money = project.getProjectMoney();
			if (project.getAccrualtype().equals("matchingRepayment")) {
				List<SlFundIntent> slist=slFundIntentService.getListByProjectId(projectId, businessType);
					int j = 1;
					for (int i = 0; i <slist .size(); i++) {


						SlFundIntent SlFundIntent1 = slist.get(i);

						if (SlFundIntent1.getFundType().equals(
								"leasePrincipalRepayment")) {
							SlFundIntent SlFundIntent0 = slist.get(i - 1);
							buff.append("{\"ri\":");
							buff.append(sd
									.format(SlFundIntent1.getIntentDate()));
							buff.append(",\"yszj\":");
							if (j == project.getPayintentPeriod()) {
								buff.append(SlFundIntent1.getIncomeMoney().add(
										SlFundIntent0.getIncomeMoney())
										.subtract(project.getLeaseDepositMoney()));
							} else {
								buff.append(SlFundIntent1.getIncomeMoney().add(
										SlFundIntent0.getIncomeMoney()));
							}
							buff.append(",\"zlf\":");
							buff.append(SlFundIntent0.getIncomeMoney());
							buff.append(",\"ysbj\":");
							if (j == project.getPayintentPeriod()) {
								buff.append(SlFundIntent1.getIncomeMoney()
										.subtract(project.getLeaseDepositMoney()));
							} else {
								buff.append(SlFundIntent1.getIncomeMoney());
							}
							buff.append(",\"wfbj\":");
							money = money.subtract(SlFundIntent1
									.getIncomeMoney());
							if (j == project.getPayintentPeriod()) {
								buff.append(project.getLeaseDepositMoney());
							} else {
								/*if (j == project.getPayintentPeriod() - 1) {
									buff.append(money.subtract(project.getLeaseDepositMoney()));
								} else {*/
									buff.append(money);
								//}
							}
							buff.append(",\"cze\":");
							if (j == project.getPayintentPeriod()) {
								buff
										.append(SlFundIntent1
												.getIncomeMoney()
												.add(
														SlFundIntent0
																.getIncomeMoney())
												.subtract(project.getLeaseDepositMoney())
												.subtract(
														SlFundIntent0
																.getIncomeMoney()
																.multiply(
																		new BigDecimal(
																				0.055))));
							} else {
								buff
										.append(SlFundIntent1
												.getIncomeMoney()
												.add(
														SlFundIntent0
																.getIncomeMoney())
												.subtract(
														SlFundIntent0
																.getIncomeMoney()
																.multiply(
																		new BigDecimal(
																				0.055))));
							}
							buff.append("}@");
							j++;
						}

					}
				
			}
			buff.append("{\"ri\":");
			buff.append(sd.format(project.getIntentDate()));
			buff.append(",\"yszj\":");
			buff.append(project.getLeaseDepositMoney());
			buff.append(",\"zlf\":");
			buff.append("null");
			buff.append(",\"ysbj\":");
			buff.append(project.getLeaseDepositMoney());
			buff.append(",\"wfbj\":");
			buff.append("null");
			buff.append(",\"cze\":");
			buff.append(project.getProjectMoney().multiply(
					project.getLeaseRetentionFeeRate()).divide(
					new BigDecimal(100)).multiply(new BigDecimal(0.945)));
			buff.append("}@");
			String[] arr = buff.toString().split("@");
			ExcelHelper.exportLease(project, customerName, arr, tableHeader,
					"租赁定价表");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String downloadurge() {
		Map<String, String> map = new HashMap<String, String>();
		int size = 0;
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		PagingBean pb = new PagingBean(start, limit);
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> tempMap=GroupUtil.separateFactor(getRequest(),ids);
		map.put("companyId", tempMap.get("companyId"));
		map.put("userId", tempMap.get("userId"));
		Short[] projectStatuses = new Short[] {
				Constants.PROJECT_STATUS_MIDDLE,
				Constants.PROJECT_POSTPONED_STATUS_ACT,
				Constants.PROJECT_POSTPONED_STATUS_REFUSE,
				Constants.PROJECT_POSTPONED_STATUS_PASS };
		List<VSmallloanProject> projectList = vSmallloanProjectService.getProjectList(tempMap.get("userId"), projectStatuses, pb, getRequest());
		PageBean<SlFundIntent> pageBean = new PageBean<SlFundIntent>(start, limit,getRequest());
		slFundIntentService.searchurge(pageBean, map, businessType);

		String[] tableHeader = { "序号", "客户名称", "联系电话", "资金类型", "计划收入金额'","计划到帐日", "实际到帐日", "催收时间" };
		try {
			ExcelHelper.export(pageBean.getResult(), tableHeader, "催收通知单");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String findLastPrincipal() {
		List<SlFundIntent> list = null;
		StringBuffer sb = new StringBuffer("{success:true,defautmoney:");
		BigDecimal defautmoney = null;
		Date date = null;
		list = slFundIntentService.findLastPrincipal(projectId, businessType);
		if (list != null && list.size() > 0) {
			SlFundIntent sl = list.get(0);
			defautmoney = sl.getIncomeMoney();
			date = sl.getIntentDate();
		}
		sb.append(defautmoney);
		sb.append(",date1:'");
		sb.append(date);
		sb.append("'}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 返回利息顺延至 和宽限至 几号
	 * 
	 * @param d
	 *            计划还息日期
	 * @param d0
	 *            实际还息日期
	 * @return
	 */
	private Date[] retCtuDayAndGraceDay(Date d, Date d0) {
		Date[] date = new Date[2];
		// 宽限至几号
		Date graceDay = null;
		// 顺延至几号
		Date CtuDay = null;
		Date crruntDate = d;
		List<CsHoliday> csh = csHolidayService.getAll();
		int len = csh.size();
		// 实际还款日为空说明还没还款
		if (d0 == null) {
			// 如果当前日期等于节假日维护日期 c++;
			for (int i = 0; i < len; i++) {
				if (crruntDate.compareTo(csh.get(i).getDateStr()) == 0) {
					crruntDate = DateUtil.addDaysToDate(crruntDate, 1); // 这个时间就是日期往后推一天的结果
				}
			}
			CtuDay = crruntDate;
		} else { // 已经还款 判断 实际还款日是否在 节假日列表
			if (crruntDate.compareTo(d0) == 0) { // 如果计划还款日 == 实际还款日 不进行 计算
				CtuDay = null;
			} else {
				for (int i = 0; i < len; i++) {
					if (crruntDate.compareTo(csh.get(i).getDateStr()) == 0) {
						crruntDate = DateUtil.addDaysToDate(crruntDate, 1);
					}
					if (crruntDate.compareTo(d0) == 0) {
						break;
					}
				}
				CtuDay = DateUtil.addDaysToDate(crruntDate, 1);
			}
		}
		// 这个时间就是日期往后推3天得到的宽限日
		graceDay = DateUtil.addDaysToDate(d, AppUtil.getGraceDayNum());
		date[0] = graceDay;
		date[1] = CtuDay;
		return date;
	}

	/**
	 * 计算顺延天数
	 * 
	 * @param d
	 *            计划还款日
	 * @param d0
	 *            实际还款日
	 * @return
	 */

	private int continueDates(Date d, Date d0) {
		int c = 0;
		Date crruntDate = d;
		Calendar calendar = new GregorianCalendar();
		List<CsHoliday> csh = csHolidayService.getAll();
		int len = csh.size();
		// 实际还款日为空说明还没还款
		if (d0 == null) {
			// 如果当前日期等于节假日维护日期 c++;
			for (int i = 0; i < len; i++) {
				if (crruntDate.compareTo(csh.get(i).getDateStr()) == 0) {
					c++;
					calendar.setTime(crruntDate);
					calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
					crruntDate = calendar.getTime(); // 这个时间就是日期往后推一天的结果
				}
			}
		} else { // 已经还款 判断 实际还款日是否在 节假日列表
			if (crruntDate.compareTo(d0) == 0) { // 如果计划还款日 == 实际还款日 不进行 计算
				c = 0;
			} else {
				for (int i = 0; i < len; i++) {
					if (crruntDate.compareTo(csh.get(i).getDateStr()) == 0) {
						c++;
						calendar.setTime(crruntDate);
						calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
						crruntDate = calendar.getTime(); // 这个时间就是日期往后推一天的结果
					}
					if (crruntDate.compareTo(d0) == 0) {
						break;
					}
				}
			}
		}
		return c;
	}

	/**
	 * 计算顺延产生利息的数据
	 */
	private void continueMath(SlFundIntent l, SlSmallloanProject p,
			List<SlFundIntent> lst) {

		// 如果是等额本金或者等额本息 获取 最后一期收款记录 计算
		// 如果listLen等于1 说明该记录不是等额本金或者等额本息 该记录即为最后一条还款记录
		int listLen = lst.size();
		// 如果该记录为 本金流水
		if (l.getFundType().equals("principalRepayment")) {
			if (listLen == 1) {
				lastStreamMeth(l, p);
			} else if (lst.get(0).getIntentDate().compareTo(l.getIntentDate()) == 0) {
				lastStreamMeth(l, p);
			} else {
				notLastStreamMeth(l, p);
			}

		} else if (l.getFundType().equals("loanInterest")) { // 利息流水
			loanInterestStreamMeth(l, p);
		} else if (l.getFundType().equals("consultationMoney")) {// 咨询服务费流水
			loanInterestStreamMeth(l, p);
		} else {

		}

	}

	/**
	 * 最后一条流水 计算
	 */
	private void lastStreamMeth(SlFundIntent l, SlSmallloanProject p) {
		if (l.getIntentDate().compareTo(new Date()) == 0
				|| l.getIntentDate().compareTo(new Date()) == -1) {
			// 顺延天数
			int cds0 = continueDates(l.getIntentDate(), l.getFactDate());
			// 顺延利息
			// 日利率
			BigDecimal dayRate = p.getAccrualnew().divide(new BigDecimal(30),
					4, BigDecimal.ROUND_HALF_EVEN);
			// 天数*日利率
			BigDecimal a = dayRate.multiply(new BigDecimal(cds0));
			// 如果未还款金额为0 获取 已还款金额
			BigDecimal m;
			if (l.getNotMoney().compareTo(new BigDecimal(0)) == 0) {
				m = l.getAfterMoney();
			} else {
				m = l.getNotMoney();
			}

			BigDecimal continueInterest = a.multiply(m).divide(
					new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
			l.setContinueDates(cds0);
			l.setContinueDay(DateUtil.dateToStr(DateUtil.addDaysToDate(l
					.getIntentDate(), cds0), "yyyy-MM-dd"));
			l.setContinueInterest(continueInterest);

		} else {
			l.setContinueDates(0);
			l.setContinueDay("");
			l.setContinueInterest(new BigDecimal(0));
		}

	}

	/**
	 * 不是最后一条流水 计算
	 */
	private void notLastStreamMeth(SlFundIntent l, SlSmallloanProject p) {
		if (l.getIntentDate().compareTo(new Date()) == 0
				|| l.getIntentDate().compareTo(new Date()) == -1) {
			// 顺延天数
			int cds0 = continueDates(l.getIntentDate(), l.getFactDate());
			l.setContinueDates(cds0);
			// 顺延至
			l.setContinueDay(DateUtil.dateToStr(DateUtil.addDaysToDate(l
					.getIntentDate(), cds0), "yyyy-MM-dd"));
			// 顺延利息
			l.setContinueInterest(new BigDecimal(0));

		} else {
			l.setContinueDates(0);
			l.setContinueDay("");
			l.setContinueInterest(new BigDecimal(0));
		}

	}

	/**
	 * 计算利息的和咨询费的顺延日 和宽限日
	 */
	private void loanInterestStreamMeth(SlFundIntent l, SlSmallloanProject p) {
		if (l.getIntentDate().compareTo(new Date()) == 0
				|| l.getIntentDate().compareTo(new Date()) == -1) {
			// 顺延天数
			int cds0 = continueDates(l.getIntentDate(), l.getFactDate());
			l.setContinueDates(cds0);
			l.setContinueDay(DateUtil.dateToStr(DateUtil.addDaysToDate(l
					.getIntentDate(), cds0), "yyyy-MM-dd"));
			l.setContinueInterest(new BigDecimal(0));

		} else {
			l.setContinueDates(0);
			l.setContinueDay("");
			l.setContinueInterest(new BigDecimal(0));
		}

		l.setGraceDay(DateUtil.dateToStr(DateUtil.addDaysToDate(l
				.getIntentDate(), AppUtil.getGraceDayNum()), "yyyy-MM-dd"));
	}

	/**
	 * 顺延日期计算开始
	 * 
	 * @param l
	 */
	private void continueMethod(SlFundIntent l) {
		if (currentPid == null || currentPid != l.getProjectId()) {
			currentPid = l.getProjectId();
			p = slSmallloanProjectService.get(currentPid);
			listByProjId = slFundIntentService.findLastPrincipal(currentPid,
					businessType);
		}
		if (listByProjId != null && listByProjId.size() != 0) {

			continueMath(l, p, listByProjId);

		}
	}

	/**
	 * 本金流水罚息计算
	 * 
	 * @param l
	 */
	private void streamPunishMethInit(SlFundIntent l, SimpleDateFormat from) {

		Long currid = l.getProjectId();
		Date dt = null;
		// 同一项目下的所有 本金 流水记录
		List<SlFundIntent> lst = slFundIntentService.findLastPrincipal(currid,
				businessType);
		int len = lst.size();
		// 该记录即为最后一条
		if (len == 1) {
			totalMMeth(l);
		} else if (lst.get(0).getIntentDate().compareTo( // 等额本金或者等额本息 最后一条
				l.getIntentDate()) == 0) {
			totalMMeth(l);//

		} else {// 不是最后一条 针对等额本金 和等额本息
			// 如果 已经超出本金到期日 罚息只计算到 本金到期日 如果有顺延 则计算到 顺延日
			if (lst.get(0).getIntentDate().compareTo(new Date()) == -1) {
				if (lst.get(0).getContinueDay() != null
						&& !lst.get(0).getContinueDay().equals("")) {
					dt = DateUtil.parseDate(lst.get(0).getContinueDay(),
							"yyyy-MM-dd");
				} else {
					dt = lst.get(0).getIntentDate();
				}
				streamPunishM(l, from, dt);
			} else {// 计算罚息到当前日期
				dt = new Date();
				streamPunishM(l, from, dt);
			}
		}

	}

	/**
	 * 利息流水罚息计算
	 * 
	 * @param l
	 */
	private void streamInterestPunishMethInit(SlFundIntent l,
			SimpleDateFormat from) {

		Long currid = l.getProjectId();
		Date dt = null;
		// 同一项目下的所有 本金 流水记录
		List<SlFundIntent> lst = slFundIntentService.findLastPrincipal(currid,
				businessType);
		int len = lst.size();

		// 如果 已经超出本金到期日 罚息只计算到 本金到期日 如果有顺延 则计算到 顺延日
		if (lst.get(0).getIntentDate().compareTo(new Date()) == -1) {
			if (lst.get(0).getContinueDay() != null
					&& !lst.get(0).getContinueDay().equals("")) {
				dt = DateUtil.parseDate(lst.get(0).getContinueDay(),
						"yyyy-MM-dd");
			} else {
				dt = lst.get(0).getIntentDate();
			}
			streamInterestPunishM(l, from, dt);
		} else {// 计算罚息到当前日期
			dt = new Date();
			streamInterestPunishM(l, from, dt);
		}

	}

	/**
	 * 本金罚息计算
	 * 
	 * @param l
	 * @param from
	 * @param dt
	 *            罚息截止日期
	 */
	private void streamPunishM(SlFundIntent l, SimpleDateFormat from, Date dt) {
		BigDecimal sortoverdueMoney = new BigDecimal(0);
		try {
			if (l.getNotMoney().compareTo(new BigDecimal(0)) == 1) {

				// 罚息基数 第一种情况 用剩余的钱乘
				// 第二种情况是 不论是否还过 都用应还金额乘
				BigDecimal notMoney = new BigDecimal(0);
				if (AppUtil.getDefaultInterest().equals("0")) {
					notMoney = l.getNotMoney();
				} else {
					notMoney = l.getIncomeMoney();
				}

				long sortday = new Long(0);
				String newdate = from.format(dt);
				Date d;
				Date d0;
				d = from.parse(newdate);
				if (compare_date(d, l.getIntentDate()) == -1
						&& l.getOverdueRateLoan() != null) {
					if (l.getFactDate() != null) { // 先判断是否还过款
						d0 = l.getFactDate();
					} else {
						if (l.getContinueDay() != null
								&& !l.getContinueDay().equals("")) {
							d0 = DateUtil.parseDate(l.getContinueDay(),
									"yyyy-MM-dd");
							;
						} else {
							d0 = l.getIntentDate();
						}
					}
					sortday = compare_date(d0, dt);

					BigDecimal day1 = new BigDecimal(sortday);
					// BigDecimal OverdueMoney =
					// l.getOverdueRate().multiply(day1);
					BigDecimal OverdueMoney = l.getOverdueRateLoan().multiply(
							day1);
					sortoverdueMoney = OverdueMoney.multiply(notMoney).divide(
							new BigDecimal(100), 3, BigDecimal.ROUND_HALF_EVEN);
					l.setAccrualMoney(sortoverdueMoney);// 罚息金额
					l.setAmountPayable(sortoverdueMoney);// 罚息金额
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 利息罚息计算
	 * 
	 * @param l
	 * @param from
	 * @param dt
	 *            罚息截止日期
	 */
	private void streamInterestPunishM(SlFundIntent l, SimpleDateFormat from,
			Date dt) {
		BigDecimal sortoverdueMoney = new BigDecimal(0);
		try {
			if (l.getNotMoney().compareTo(new BigDecimal(0)) == 1) {
				long sortday = new Long(0);
				String newdate = from.format(dt);
				Date d;// 当前日期
				Date d0; // 截止日期
				Date dd0;// 顺延日
				Date dd1;// 宽限日
				d = from.parse(newdate);
				if (l.getContinueDay() != null && l.getGraceDay() != null
						&& !l.getContinueDay().equals("")
						&& !l.getGraceDay().equals("")) {
					// 比较俩者大小
					dd0 = DateUtil.parseDate(l.getContinueDay(), "yyyy-MM-dd");
					dd1 = DateUtil.parseDate(l.getGraceDay(), "yyyy-MM-dd");
					if (compare_date(dd0, dd1) == -1
							|| compare_date(dd0, dd1) == 0) {
						d0 = dd0;//
					} else {
						d0 = dd1;
					}

				} else if (l.getContinueDay() == null
						&& !l.getContinueDay().equals("")) {
					d0 = DateUtil.parseDate(l.getGraceDay(), "yyyy-MM-dd");
				} else {
					d0 = l.getIntentDate();
				}
				if (compare_date(d, d0) == -1 && l.getOverdueRate() != null) {
					sortday = compare_date(d0, dt);
					BigDecimal day1 = new BigDecimal(sortday);
					BigDecimal OverdueMoney = l.getOverdueRate().multiply(day1);
					// 罚息基数 第一种情况 用剩余的钱乘
					// 第二种情况是 不论是否还过 都用应还金额乘
					BigDecimal notM = new BigDecimal(0);
					if (AppUtil.getDefaultInterest().equals("0")) {
						notM = l.getNotMoney();
					} else {
						notM = l.getIncomeMoney();
					}
					sortoverdueMoney = OverdueMoney.multiply(notM).divide(
							new BigDecimal(100), 2);

					// l.setAccrualMoney(l.getAccrualMoney().add(sortoverdueMoney));
					l.setAccrualMoney(sortoverdueMoney);
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 罚息详细
	 */
	private void defaultInterest(SlFundIntent sf, Date overdueintentDate,
			BigDecimal totalSortoverdueMoney, Integer continueDates,
			BigDecimal amountPayable, BigDecimal continueInterest) {
		List<SlPunishInterest> listfaxi = slPunishInterestService
				.listbyisInitialorId(sf.getFundIntentId());
		SlPunishInterest faxiSlFundIntent = new SlPunishInterest();
		if (listfaxi == null || listfaxi.size() == 0) {

			faxiSlFundIntent.setIntentDate(overdueintentDate);
			faxiSlFundIntent.setIncomeMoney(totalSortoverdueMoney);// 罚息金额
			faxiSlFundIntent.setPayMoney(new BigDecimal("0"));
			// faxiSlFundIntent.setNotMoney(sortoverdueMoney);
			faxiSlFundIntent.setAfterMoney(new BigDecimal("0"));
			faxiSlFundIntent.setBusinessType(sf.getBusinessType());
			faxiSlFundIntent.setFundIntentId(sf.getFundIntentId());
			faxiSlFundIntent.setNotMoney(totalSortoverdueMoney);
			faxiSlFundIntent.setProjectId(sf.getProjectId());
			faxiSlFundIntent.setFundIntentId(sf.getFundIntentId());
			faxiSlFundIntent.setFundType(sf.getFundType());
			faxiSlFundIntent.setCompanyId(sf.getCompanyId());

		} else {

			faxiSlFundIntent = listfaxi.get(listfaxi.size() - 1);
			faxiSlFundIntent.setIncomeMoney(totalSortoverdueMoney);
			faxiSlFundIntent.setNotMoney(totalSortoverdueMoney
					.subtract(faxiSlFundIntent.getAfterMoney()));
			faxiSlFundIntent.setIntentDate(overdueintentDate);

			faxiSlFundIntent.setContinueDates(continueDates);
			if (sf.getContinueDay() != null && !sf.getContinueDay().equals("")) {
				if (compare_date(DateUtil.parseDate(sf.getContinueDay(),
						"yyyy-MM-dd"), sf.getIntentDate()) == 0) {
					faxiSlFundIntent.setContinueDay("无顺延");
				} else {
					faxiSlFundIntent.setContinueDay(sf.getContinueDay());
				}
			}

			faxiSlFundIntent.setAmountPayable(amountPayable);
			if (continueInterest == null) {
				faxiSlFundIntent.setContinueInterest(new BigDecimal(0));
			} else {
				faxiSlFundIntent.setContinueInterest(continueInterest);
			}
			faxiSlFundIntent.setGraceDay(sf.getGraceDay());

		}

		if (faxiSlFundIntent.getIncomeMoney().compareTo(new BigDecimal("0")) != 0) {
			slPunishInterestService.save(faxiSlFundIntent);

		}
	}

	/**
	 * 计算本金最后一期 应收的所有金额
	 * 公式：最后一期本金金额+所有未还利息+所有利息欠收罚息+所有欠收本金+所有本金欠收罚息+所有咨询费欠收金额=最后一期应收金额
	 */
	private void totalMMeth(SlFundIntent l) {
		String[] str = { "principalRepayment", "loanInterest",
				"consultationMoney" };
		SlSmallloanProject p;
		// 逾期总天数
		Long totalDays = null;
		// 顺延日期
		Date d0;
		// 罚息开始时间
		Date d1;
		// 罚息截止时间
		Date d2 = new Date();
		// 罚息金额

		BigDecimal FXMoney = new BigDecimal(0);
		BigDecimal totalMoney = new BigDecimal(0);
		List<SlFundIntent> SFL = slFundIntentService.dynamicGetData(l
				.getProjectId(), businessType, str);
		for (SlFundIntent sfI : SFL) {
			// totalMoney=totalMoney.add(sfI.getNotMoney().add(sfI.getAccrualMoney()));

			if (sfI.getFundType().equals("principalRepayment")) {
				totalMoney = totalMoney.add(sfI.getNotMoney());
			} else {
				totalMoney = totalMoney.add(sfI.getNotMoney().add(
						sfI.getAccrualMoney()));
			}

			// 判断是否有顺延息
			if (sfI.getContinueInterest() != null) {
				totalMoney = totalMoney.add(sfI.getContinueInterest());
			}
		}
		if (l.getContinueDay() != null && !l.getContinueDay().equals("")) {
			d0 = DateUtil.parseDate(l.getContinueDay(), "yyyy-MM-dd");
			if (compare_date(d0, l.getIntentDate()) == 0) {
				d1 = l.getIntentDate();
			} else {
				d1 = d0;
			}
		} else {
			d1 = l.getIntentDate();
		}

		totalDays = compare_date(d1, d2);
		p = slSmallloanProjectService.get(l.getProjectId());
		FXMoney = totalMoney.multiply(
				p.getOverdueRateLoan().multiply(new BigDecimal(totalDays)))
				.divide(new BigDecimal(100), 2);
		l.setAccrualMoney(FXMoney);
	}

	/**
	 * 
	 * @param startDate罚息起始时间
	 * @param endDate
	 *            罚息结束时间
	 * @param m
	 *            金额
	 * @param mRate
	 *            利率 为 贷款项目填写的值
	 * @return
	 */
	private BigDecimal interestPenaltyMeth(Date startDate, Date endDate,
			BigDecimal m, BigDecimal mRate) {

		BigDecimal money = new BigDecimal(0);
		Long sortdaty = compare_date(startDate, endDate);
		money = m.multiply(new BigDecimal(sortdaty).multiply(mRate)).divide(
				new BigDecimal(100), 2, BigDecimal.ROUND_HALF_EVEN);
		return money;
	}

	/**
	 * 重新计算罚息 金额 （对账 等）
	 * 
	 * @return
	 */
	private BigDecimal resetInterestPenaltyMeth(SlFundIntent slFundIntent,
			SlFundDetail slFundDetail) {
		BigDecimal overdueRate = new BigDecimal(0.00);
		// 正常利率
		BigDecimal accrual = new BigDecimal(0.00);
		if (slFundIntent.getBusinessType().equals("SmallLoan")) {
			SlSmallloanProject loan = slSmallloanProjectService
					.get(slFundIntent.getProjectId());
			accrual = loan.getAccrualnew();
			if (loan.getOverdueRateType()!=null&&loan.getOverdueRateType().equals("2")) {
				overdueRate = loan.getAccrual();
			} else {
				overdueRate = loan.getOverdueRate();
			}
		}
		if (slFundIntent.getBusinessType().equals("Financing")) {
			FlFinancingProject flFinancingProject = flFinancingProjectService
					.get(slFundIntent.getProjectId());
			overdueRate = flFinancingProject.getOverdueRate();
		}
		if (slFundIntent.getBusinessType().equals("Guarantee")) {
			GLGuaranteeloanProject glGuaranteeloanProject = glGuaranteeloanProjectService
					.get(slFundIntent.getProjectId());
			if (glGuaranteeloanProject.getOverdueRate() != null) {
				overdueRate = glGuaranteeloanProject.getOverdueRate();
			}
		}
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		Date overdueintentDate = null;
		try {
			overdueintentDate = from.parse(from.format(slFundIntent
					.getFactDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 总罚息金额
		BigDecimal sortoverdueMoney = new BigDecimal(0.00);
		// 实际罚息金额
		BigDecimal sortoverdueMoney0 = new BigDecimal(0.00);
		// 顺延息金额
		BigDecimal sortoverdueMoney1 = new BigDecimal(0.00);
		// 罚息开始日期
		Date startDate;
		// 罚息结束日期
		Date endDate;
		BigDecimal sortnotMoney = slFundIntent.getPayInMoney();
		long sortday = new Long(0);
		BigDecimal suboverdueMoney = new BigDecimal(0.00);
		if (compare_date(slFundIntent.getFactDate(), slFundIntent
				.getIntentDate()) == -1) {
			BigDecimal day1 = null;
			BigDecimal OverdueMoney = null;
			// 之前的算法 加 1 先保留
			/*
			 * sortday = compare_date(slFundIntent.getIntentDate(),
			 * slFundQlide.getFactDate()) + 1;
			 */
			startDate = slFundIntent.getFactDate();
			// 如果顺延日和宽限日都有值
			Date compareDate; // 比较所得日期
			if (slFundIntent.getContinueDay() != null
					&& !slFundIntent.getContinueDay().equals("")
					&& slFundIntent.getGraceDay() != null
					&& !slFundIntent.getGraceDay().equals("")) {
				// 比较顺延日和 宽限日谁大
				if (compare_date(DateUtil.parseDate(slFundIntent
						.getContinueDay(), "yyyy-MM-dd"), DateUtil.parseDate(
						slFundIntent.getGraceDay(), "yyyy-MM-dd")) == -1) {
					compareDate = DateUtil.parseDate(slFundIntent
							.getContinueDay(), "yyyy-MM-dd");
				} else {
					compareDate = DateUtil.parseDate(
							slFundIntent.getGraceDay(), "yyyy-MM-dd");
				}
			} else if (slFundIntent.getContinueDay() != null
					&& !slFundIntent.getContinueDay().equals("")) {
				compareDate = DateUtil.parseDate(slFundIntent.getContinueDay(),
						"yyyy-MM-dd");
			} else if (slFundIntent.getGraceDay() != null
					&& !slFundIntent.getGraceDay().equals("")) {

				compareDate = DateUtil.parseDate(slFundIntent.getGraceDay(),
						"yyyy-MM-dd");
			} else {
				compareDate = slFundIntent.getIntentDate();
			}
			if (compare_date(compareDate, slFundIntent.getIntentDate()) == -1) {
				if (compare_date(compareDate, slFundIntent.getFactDate()) == -1) {
					endDate = slFundIntent.getFactDate();
					startDate = slFundIntent.getIntentDate();
				} else {
					endDate = slFundIntent.getFactDate();
					startDate = compareDate;
				}
			} else {
				endDate = slFundIntent.getFactDate();
				startDate = slFundIntent.getIntentDate();
			}

			sortoverdueMoney = interestPenaltyMeth(startDate, endDate,
					slFundDetail.getAfterMoney(), overdueRate);

			defaultInterest(slFundIntent, overdueintentDate, sortoverdueMoney,
					slFundIntent.getContinueDates(), sortoverdueMoney,
					slFundIntent.getContinueInterest());
		}
		return sortoverdueMoney;
	}

	public String saveFundIntent() {
		try {
			String fundIntentJsonData = this.getRequest().getParameter(
					"fundIntentJsonData");
			String isDeleteAllFundIntent = this.getRequest().getParameter(
					"isDeleteAllFundIntent");
			List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
			FlFinancingProject project = flFinancingProjectService
					.get(flFinancingProject.getProjectId());
			BeanUtil.copyNotNullProperties(project, flFinancingProject);
			flFinancingProjectService.save(project);
			slFundIntents = getFundIntents(fundIntentJsonData, project, Short
					.parseShort("1"));
			if (isDeleteAllFundIntent.equals("1")) {
				List<SlFundIntent> all = slFundIntentService.getByProjectId(
						flFinancingProject.getProjectId(), "Financing");
				for (SlFundIntent s : all) {
					if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						slFundIntentService.remove(s.getFundIntentId());
					}
				}
			}
			for (SlFundIntent s : slFundIntents) {
				slFundIntentService.save(s);
			}
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	public List<SlFundIntent> getFundIntents(String fundIntentJsonData,
			FlFinancingProject slSmallloanProject, Short flag) {

		List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
		if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
			String[] shareequityArr = fundIntentJsonData.split("@");
			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper
							.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(slSmallloanProject
							.getProjectId());
					SlFundIntent1.setProjectName(slSmallloanProject
							.getProjectName());
					SlFundIntent1.setProjectNumber(slSmallloanProject
							.getProjectNumber());

					if (null == SlFundIntent1.getFundIntentId()) {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getIncomeMoney());
						}
						SlFundIntent1.setAfterMoney(new BigDecimal(0));
						SlFundIntent1.setAccrualMoney(new BigDecimal(0));
						SlFundIntent1.setFlatMoney(new BigDecimal(0));
						Short isvalid = 0;
						SlFundIntent1.setIsValid(isvalid);
						SlFundIntent1.setIsCheck(flag);
						SlFundIntent1.setBusinessType("Financing");
						SlFundIntent1.setCompanyId(slSmallloanProject
								.getCompanyId());
						slFundIntents.add(SlFundIntent1);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getIncomeMoney());
						}
						SlFundIntent slFundIntent2 = slFundIntentService
								.get(SlFundIntent1.getFundIntentId());
						BeanUtil.copyNotNullProperties(slFundIntent2,
								SlFundIntent1);
						slFundIntent2.setBusinessType("Financing");
						slFundIntent2.setIsCheck(flag);
						slFundIntents.add(slFundIntent2);
					}
				} catch (Exception e) {
					logger.error("FlFinancingProjectAction款项计划"
							+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return slFundIntents;
	}

	public String autoReconcile() {
		try {
			Date currentDate = new Date();
			BigDecimal payMoney = slFundQlide.getPayMoney();
			BigDecimal incomeMoney = slFundQlide.getIncomeMoney();
			String fundIntentId = getRequest().getParameter("fundIntentId");
			String fundType = getRequest().getParameter("fundType");
			BigDecimal money = null;
			if (fundType.equals("principalLending")) {
				money = payMoney;
			} else if (fundType.equals("principalRepayment")) {
				money = incomeMoney;
			}
			slFundQlide.setNotMoney(new BigDecimal(0.00));
			slFundQlide.setAfterMoney(money);
			slFundQlide.setMyAccount("0");
			slFundQlide.setTransactionType("(虚拟转账)");
			slFundQlide.setOpBankName("中国建设银行股份有限公司");
			slFundQlide.setOpOpenName("虚拟账户");
			slFundQlide.setCompanyId(Long.valueOf("1"));
			slFundQlideService.save(slFundQlide);
			SlBankAccount sba = slBankAccountService.getbyaccount("0").get(0);
			if (sba.getFinalMoney() == null) {
				if (fundType.equals("principalLending")) {
					sba.setFinalMoney(new BigDecimal(0.00).subtract(money));
				} else {
					sba.setFinalMoney(new BigDecimal(0.00).add(money));
				}
			} else {
				if (fundType.equals("principalLending")) {
					sba.setFinalMoney(sba.getFinalMoney().subtract(money));
				} else {
					sba.setFinalMoney(sba.getFinalMoney().add(money));
				}
			}
			sba.setFinalDate(currentDate);
			slBankAccountService.save(sba);
			SlFundIntent slFundIntent = slFundIntentService.get(Long
					.valueOf(fundIntentId));
			slFundIntent.setNotMoney(new BigDecimal(0.00));
			slFundIntent.setAfterMoney(money);
			slFundIntent.setIsOverdue("否");
			slFundIntent.setFactDate(slFundQlide.getFactDate());
			slFundIntentService.save(slFundIntent);
			SlFundDetail slFundDetail = new SlFundDetail();
			slFundDetail.setOverdueAccrual(new BigDecimal(0.00));
			slFundDetail.setOverdueNum(new Long(0));
			slFundDetail.setSlFundIntent(slFundIntent);
			slFundDetail.setSlFundQlide(slFundQlide);
			slFundDetail.setFactDate(slFundQlide.getFactDate());
			slFundDetail.setAfterMoney(new BigDecimal(0.00));
			slFundDetailService.save(slFundDetail);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return SUCCESS;
	}

	// 导出到Excel
	public void outToExcel() {
		try {
			String tabFlag = this.getRequest().getParameter("tabflag");// 类型
			String tableHeaderName = "款项催收";
			String[] tableHeader = { "序号", "项目编号", "客户名称", "联系电话", "资金类型",
					"计划收入金额", "未对帐金额", "计划到帐日", "实际到帐日", "最后催收时间" };
		
			Map<String, String> map = new HashMap<String, String>();
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			Map<String,String> tempMap=GroupUtil.separateFactor(getRequest(),null);
			map.put("companyId", tempMap.get("companyId"));
			PageBean<SlFundIntent> pageBean = new PageBean<SlFundIntent>(null, null,getRequest());
			slFundIntentService.searchurge(pageBean, map, businessType);
			
			if (tabFlag.equals("all")) {
				tableHeaderName = "全部款项催收";
			} else if (tabFlag.equals("coming")) {
				tableHeaderName = "即将到期款项催收";
			} else if (tabFlag.equals("overdue")) {
				tableHeaderName = "已逾期款项催收";
			}
			ExcelHelper.export(pageBean.getResult(), tableHeader, tableHeaderName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String listOfPawnCOntinued() { // listbySmallLoan and listbyFinancing
		try {
			List<SlFundIntent> list = new ArrayList<SlFundIntent>();
			int size = 0;
			if (flag1 == 1) {
				String slSuperviseRecordId = this.getRequest().getParameter(
						"slSuperviseRecordId");
				if (null != slSuperviseRecordId
						&& !"".equals(slSuperviseRecordId)
						&& !"null".equals(slSuperviseRecordId)) {
					list = slFundIntentService.getlistbyslSuperviseRecordId(
							Long.valueOf(slSuperviseRecordId), "Pawn",
							projectId);
				}
			}

			if (pawnContinuedManagment != null && flag1 == 0) {
				// 生成款项计划
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				if (calcutype.equals("Pawn")) {
					plPawnProject = plPawnProjectService.get(projectId);
					if (AppUtil.getInterest().equals("0")) {
						list = PawnFundIntentListPro
								.getFundIntentDefaultList(
										calcutype,
										"singleInterest",
										pawnContinuedManagment
												.getIsPreposePayAccrual(),
										pawnContinuedManagment
												.getPayaccrualType(),
										pawnContinuedManagment
												.getAccrual()
												.divide(
														new BigDecimal(30),
														10,
														BigDecimal.ROUND_HALF_UP)
												.divide(BigDecimal.valueOf(100)),
										plPawnProject.getProjectMoney(),
										sd.format(pawnContinuedManagment
												.getStartDate()),
										pawnContinuedManagment.getIntentDate() == null ? null
												: sd
														.format(pawnContinuedManagment
																.getIntentDate()),
										pawnContinuedManagment
												.getMonthFeeRate()
												.divide(
														new BigDecimal(30),
														10,
														BigDecimal.ROUND_HALF_UP)
												.divide(BigDecimal.valueOf(100)),
										new BigDecimal(0),
										pawnContinuedManagment
												.getPayintentPeriod(),
										pawnContinuedManagment
												.getIsStartDatePay(),
										pawnContinuedManagment
												.getPayintentPerioDate(),
										pawnContinuedManagment
												.getDayOfEveryPeriod(),
										isHaveLending, null,
										pawnContinuedManagment
												.getIsInterestByOneTime(), null);
					} else {
						list = PawnFundIntentListProtw
								.getFundIntentDefaultList(
										calcutype,
										"singleInterest",
										pawnContinuedManagment
												.getIsPreposePayAccrual(),
										pawnContinuedManagment
												.getPayaccrualType(),
										pawnContinuedManagment
												.getAccrual()
												.divide(
														new BigDecimal(30),
														10,
														BigDecimal.ROUND_HALF_UP)
												.divide(BigDecimal.valueOf(100)),
										plPawnProject.getProjectMoney(),
										sd.format(pawnContinuedManagment
												.getStartDate()),
										pawnContinuedManagment.getIntentDate() == null ? null
												: sd
														.format(pawnContinuedManagment
																.getIntentDate()),
										pawnContinuedManagment
												.getMonthFeeRate()
												.divide(
														new BigDecimal(30),
														10,
														BigDecimal.ROUND_HALF_UP)
												.divide(BigDecimal.valueOf(100)),
										new BigDecimal(0),
										pawnContinuedManagment
												.getPayintentPeriod(),
										pawnContinuedManagment
												.getIsStartDatePay(),
										pawnContinuedManagment
												.getPayintentPerioDate(),
										pawnContinuedManagment
												.getDayOfEveryPeriod(),
										isHaveLending, null,
										pawnContinuedManagment
												.getIsInterestByOneTime(), null);
					}
				}

				for (SlFundIntent s : list) {
					s.setBusinessType(calcutype);
				}

				size = list.size();

			}

			List<SlFundIntent> list1 = new ArrayList<SlFundIntent>();
			if (relateIntentOrCharge == null
					|| relateIntentOrCharge.equals("all")) { // 取全部
				for (SlFundIntent l : list) {
					List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
							.getByDicKey(l.getFundType());
					l.setFundTypeName(dictionaryIndependent.get(0)
							.getItemValue());
					list1.add(l);
				}
			}
			if (relateIntentOrCharge != null
					&& relateIntentOrCharge.equals("charge")) { // 取费用相关
				for (SlFundIntent l : list) {
					if (businessType.equals("Pawn")) {
						if (l.getFundType().equals("pawnServiceMoney")
								|| l.getFundType().equals("pawnLoanInterest")) {
							list1.add(l);
						}
					}
				}
			}
			if (relateIntentOrCharge != null
					&& relateIntentOrCharge.equals("intent")) { // 取本金相关
				for (SlFundIntent l : list) {
					if (businessType.equals("Pawn")) {
						if (l.getFundType().equals("pawnPrincipalRepayment")
								|| l.getFundType().equals(
										"pawnPrincipalLending")) {
							list1.add(l);
						}
					}
				}
			}
			if (relateIntentOrCharge != null
					&& relateIntentOrCharge.equals("none")) { // 取空

			}

			if (list1 != null) {
				size = list1.size();
			}

			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(size).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
					"factDate", "interestStarTime", "interestEndTime");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"intentDate", "factDate", "interestStarTime",
					"interestEndTime" });
			buff.append(json.serialize(list1));
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	public String leaseFundIntentInfo() {
		List<SlFundIntent> list = slFundIntentService.getByProjectId1(
				projectId, businessType);
		BigDecimal wszj = new BigDecimal(0);
		BigDecimal yszj = new BigDecimal(0);
		BigDecimal sjcze = new BigDecimal(0);
		BigDecimal wfbj = new BigDecimal(0);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		for (SlFundIntent s : list) {
			if (s.getFundType().equals("leaseFeeCharging")
					|| s.getFundType().equals("leasePrincipalRepayment")) {
				if (null != s.getNotMoney()) {
					wszj = wszj.add(s.getNotMoney());
				}
				if (null != s.getAfterMoney()) {
					yszj = yszj.add(s.getAfterMoney());
				}
			}
			if (s.getFundType().equals("rentalCostsPaid")) {
				sjcze = sjcze.add(s.getPayMoney());
			}
			if (s.getFundType().equals("marginCollection")) {
				sjcze = sjcze.subtract(s.getIncomeMoney());
			}
			if (s.getFundType().equals("rentalFeeCharging")) {
				sjcze = sjcze.subtract(s.getIncomeMoney());
			}
			if (s.getFundType().equals("leasePrincipalRepayment")) {
				wfbj = wfbj.add(s.getNotMoney());
			}
		}

		List<SlFundIntent> slist = slFundIntentService.getyqList(projectId,
				businessType, sd.format(new Date()));
		int yqcount = 0;
		if (null != slist & slist.size() > 0) {
			yqcount = slist.size();
		}
		BigDecimal money = new BigDecimal(0);
		for (SlFundIntent s : slist) {
			if (!s.getFundType().equals("rentalCostsPaid")
					&& !s.getFundType().equals("marginRefund")) {
				money = money.add(s.getNotMoney());
			}
		}
		jsonString = "{success:true,wszj:" + wszj + ",yszj:" + yszj + ",sjcze:"
				+ sjcze + ",wfbj:" + wfbj + ",yqcount:" + yqcount + ",money:"
				+ money + "}";
		return SUCCESS;
	}
	//债权转让款项查询和生成款项
	public String listObligation(){
		/*String accrualtype =this.getRequest().getParameter("accrualtype");//计息方式（等额本金。。）
		String payaccrualType =this.getRequest().getParameter("payaccrualType");//计息周期（按月。。）
		String projectMoney =this.getRequest().getParameter("projectMoney");//投资额
		String startDate =this.getRequest().getParameter("startDate");//投资开始时间
		String intentDate =this.getRequest().getParameter("startDate");//投资结束时间
		String accrualnew =this.getRequest().getParameter("accrualnew");//投资人利率
		String isStartDatePay =this.getRequest().getParameter("isStartDatePay");//是否固定在哪一天收息
		String payintentPerioDate =this.getRequest().getParameter("payintentPerioDate");//固定在那一天收息
		String payintentPeriod =this.getRequest().getParameter("payintentPeriod");//收息期限
		String dayOfEveryPeriod =this.getRequest().getParameter("dayOfEveryPeriod");//自定义周期长度
*/		String investPersonId =this.getRequest().getParameter("investPersonId");//投资人id
		String keyValue =this.getRequest().getParameter("keyValue");//判断是生成多个投资人款项还是一个人的款项
		String obligationInfoId =this.getRequest().getParameter("obligationInfoId");//投资人id
		try{
			List<SlFundIntent> list = new ArrayList();
			int size = 0;
			if (flag1 == 1){//不生成款项只查询
				if(projectId!=null&&!"".equals(projectId)&&projectId!=0){
					Long investMentPersonId=0l;
					Long obligationInfosId=0l;
					if(investPersonId!=null&&!"".equals(investPersonId)){
						investMentPersonId=Long.valueOf(investPersonId);
					}
					if(obligationInfoId!=null&&!"".equals(obligationInfoId)){
						obligationInfosId=Long.valueOf(obligationInfoId);
					}
					list =slFundIntentService.getListByTreeId(projectId,investMentPersonId,obligationInfosId);
					size =list.size();
				}
				
			}if(flag1 == 0&&"oneSlFundIntentCreat".equals(keyValue)){//给单个投资人生成款项
				CsInvestmentperson person =null;
				if(investPersonId!=null&&!"".equals(investPersonId)){
					person =csInvestmentpersonService.get(Long.valueOf(investPersonId));
				}
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				list=obligationFundIntenList.getFundIntentDefaultList(
										obObligationProject.getAccrual().divide(BigDecimal.valueOf(100)),
										obObligationProject.getPayaccrualType(),
										sd.format(obObligationProject.getStartDate()),
										sd.format(obObligationProject.getIntentDate()),
										obObligationProject.getAccrual().divide(BigDecimal.valueOf(100)),
										obObligationProject.getPayintentPerioDate(),
										obObligationProject.getPayintentPeriod(),
										obObligationProject.getIsStartDatePay(),
										obObligationProject.getIsPreposePayAccrual(),
										isHaveLending,obObligationProject.getProjectMoney(),
										obObligationProject.getAccrualtype(),
										obObligationProject.getDayOfEveryPeriod());
				for (SlFundIntent s : list) {
					s.setObligationId(projectId);//债权项目表的id
					s.setInvestPersonId(Long.valueOf(investPersonId));//投资人的id
					s.setInvestPersonName(person.getInvestName());//保存债权人姓名
				}
				size =list.size();
			}if(flag1 == 0&&"manySlFundIntentCreat".equals(keyValue)){//给多个投资人生成款项
				if(projectId!=null&&!"".equals(projectId)){
					ObObligationProject obligationProject =obObligationProjectService.get(projectId);
					List<ObObligationInvestInfo> listInvest=obObligationInvestInfoService.getInfoByobObligationProjectId(projectId, "1");
					if(listInvest!=null&&listInvest.size()>0){
						for(ObObligationInvestInfo temp :listInvest){
							List<SlFundIntent> lists = null;
							CsInvestmentperson person =null;
							if(temp.getInvestMentPersonId()!=null&&!"".equals(temp.getInvestMentPersonId())){
								person =csInvestmentpersonService.get(temp.getInvestMentPersonId());
							}
							if(temp.getFundIntentStatus()==0){
								SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
								lists=obligationFundIntenList.getFundIntentDefaultList(
														temp.getObligationAccrual().divide(BigDecimal.valueOf(100)),
														obObligationProject.getPayaccrualType(),
														sd.format(temp.getInvestStartDate()),
														sd.format(temp.getInvestEndDate()),
														temp.getObligationAccrual().divide(BigDecimal.valueOf(100)),
														obObligationProject.getPayintentPerioDate(),
														obObligationProject.getPayintentPeriod(),
														obObligationProject.getIsStartDatePay(),
														obObligationProject.getIsPreposePayAccrual(),
														isHaveLending,temp.getInvestMoney(),
														obObligationProject.getAccrualtype(),
														obObligationProject.getDayOfEveryPeriod());
								for (SlFundIntent s : lists) {
									s.setObligationId(projectId);//债权项目表的id
									s.setInvestPersonId(temp.getInvestMentPersonId());//投资人的id
									s.setInvestPersonName(temp.getInvestPersonName());//保存债权人姓名
									s.setObligationInfoId(temp.getId());
									list.add(s);
								}
							}else{
								lists =slFundIntentService.getListByTreeId(projectId,temp.getInvestMentPersonId(),temp.getId());
								if(lists!=null&& lists.size()>0){
									for (SlFundIntent s : lists) {
										list.add(s);
									}
								}
							}
							
						}
						
					}
					size =list.size();
				}
			}
			for(SlFundIntent s:list){
				if(null!=s.getFundType() && !"".equals(s.getFundType())){
					List<DictionaryIndependent> dlist=dictionaryIndependentService.getByDicKey(s.getFundType());
					if(null!=dlist && dlist.size()>0){
						DictionaryIndependent dic=dlist.get(0);
						s.setFundTypeName(dic.getItemValue());
					}
				}
			}
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
            JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate","interestStarTime","interestEndTime");
            json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"intentDate", "factDate" ,"interestStarTime","interestEndTime"});
            buff.append(json.serialize(list));
	        buff.append("}");
	        jsonString = buff.toString();
		}catch(Exception e ){
			e.printStackTrace();
			logger.error("债权转让投资人款项生成错误！"+e.getMessage());
		}
		return SUCCESS;
	}
	public void assignmentDownload() {
		String investPersonId =this.getRequest().getParameter("investPersonId");//投资人id
		String keyValue =this.getRequest().getParameter("keyValue");//判断是生成多个投资人款项还是一个人的款项
		String obligationInfoId =this.getRequest().getParameter("obligationInfoId");//投资人id
			List<SlFundIntent> list = new ArrayList();
				if(projectId!=null&&!"".equals(projectId)&&projectId!=0){
					Long investMentPersonId=0l;
					Long obligationInfosId=0l;
					if(!"undefined".equals(investPersonId) && investPersonId!=null&&!"".equals(investPersonId)){
						investMentPersonId=Long.valueOf(investPersonId);
					}
					if(!"undefined".equals(obligationInfoId) && obligationInfoId!=null&&!"".equals(obligationInfoId)){
						obligationInfosId=Long.valueOf(obligationInfoId);
					}
					list =slFundIntentService.getListByTreeId(projectId,investMentPersonId,obligationInfosId);
				}
			for(SlFundIntent s:list){
				List<DictionaryIndependent> dicList=dictionaryIndependentService.getByDicKey(s.getFundType());
				if(null!=dicList && dicList.size()>0){
					DictionaryIndependent dic=dicList.get(0);
					s.setFundTypeName(dic.getItemValue());
				}
			}
//		List<SlFundIntent> list = slFundIntentService.getByProjectId1(
//				projectId, businessType);
//		SlSmallloanProject loan = slSmallloanProjectService.get(projectId);
//		for (SlFundIntent l : list) {
//			l.setPunishAccrual(loan.getOverdueRate() == null ? BigDecimal
//					.valueOf(0) : loan.getOverdueRate());
//		}
		String[] tableHeader = { "序号", "投资人", "期数", "资金类型", "计划收入金额",
				"计划支出金额", "到帐日", "对账日"};
		try {
			ExcelHelper.exportAssignFundIntent(list, tableHeader, "还款计划表");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String listOfInverstPerson() {
		String inverstPersonId = this.getRequest().getParameter(
				"inverstPersonId");
		StringBuffer buff = new StringBuffer("{success:true,result:");
		if (null != inverstPersonId && !"".equals(inverstPersonId)) {
			List<SlFundIntent> list = slFundIntentService.listOfInverstPerson(
					Long.valueOf(inverstPersonId), projectId, "SmallLoan");
			for (SlFundIntent s : list) {
				if (null != s.getFundType() && !"".equals(s.getFundType())) {
					List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService
							.getByDicKey(s.getFundType());
					s.setFundTypeName(dictionaryIndependent.get(0)
							.getItemValue());
				}
			}
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(list));
		}
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	public void transferaudit(String sb) {
		MoneyMoreMore moneyMoreMore = new MoneyMoreMore();
		// 第三方标识
		moneyMoreMore.setLoanNoList(sb.toString());
		moneyMoreMore.setAuditType(MoneyMoreMore.TRANSFER1);
		moneyMoreMore.setRandomTimeStamp(Common.getRandomNum(2)
				+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS")); // 启用防抵赖
		iPayService.transferaudit(moneyMoreMore, this.getBasePath(), this
				.getResponse());
	}

	
	/**
	 * 借款人直接到 投资人
	 * 
	 * @return
	 */
	public String borrowerToInvestor() {/*
		StringBuffer buff = new StringBuffer();
		StringBuffer sb = new StringBuffer(); // 转账列表
		// 借款人第三方标识
		String borrowerFlag = "";
		slFundIntent = slFundIntentService.get(fundIntentId);
		PlBidPlan plBidPlan = new PlBidPlan();
		plBidPlan = plBidPlanService.get(slFundIntent.getBidPlanId());
		if(configMap.get("thirdPayType").toString().equals("1")){
			buff.append("{success:true,result:'只有第三方支付托管模式才能使用从借款人账户直还功能'");
		}else{
			if(configMap.get("thirdPayConfig").toString().equals(Constants.MONEYMOREMORE)){
				borrowerFlag = getCustFlag(plBidPlan);
				// 如果账户余额大于 要还金额 进行还款 否则提示平台进行垫付
				if (getPayMoney(borrowerFlag).compareTo(slFundIntent.getIncomeMoney()) >= 0) {
					MoneyMoreMore moneyMoreMore = createMoneyMoreMore(plBidPlan.getBidId().toString(), slFundIntent, borrowerFlag);
					sb = getLoanList(plBidPlan.getBidId().toString(),slFundIntent);
					// 转账成功以后 进行 审核接口
					// 判断是否已经存在 转账列表
					if (sb == null || sb.length() <= 0) {
						iPayService.transfer(moneyMoreMore, this.getBasePath());
						sb = getLoanList(plBidPlan.getBidId().toString(),slFundIntent);
					}

					// 生成 还款 转账列表
					if (sb.length() > 0) {
						sb = sb.deleteCharAt(sb.length() - 1);
						transferaudit(sb.toString());
					}
					buff.append("{success:true,result:'转账成功'");
				} else {
					buff.append("{success:false,result:'账户余额不足'");
				}
			}else if(configMap.get("thirdPayConfig").toString().equals(Constants.FUIOU)){
				buff=this.repayByLoanFuiou(plBidPlan,slFundIntent,buff);
			}else if(configMap.get("thirdPayConfig").toString().equals(Constants.HUIFU)){
				
			}else{
				buff.append("{success:false,result:'请检查是否正确配置第三方支付配置文件,如果正确配置,那就尚未对接此类型的第三方支付接口,请联系金智万维软件公司进行咨询!'");
			}
		}
		
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	*/
	return SUCCESS;	
	}

	
	/**
	 * 获取返回的 转账列表
	 * 
	 * @param 同hk+台帐ID
	 *            +bid 获取转账列表
	 * @return
	 */
	private StringBuffer getLoanList(String bidPlanID,SlFundIntent slFundIntent) {
		StringBuffer sb = new StringBuffer();
		QueryFilter filter = new QueryFilter(this.getRequest());
		filter.addFilter("Q_BatchNo_S_EQ", "hk_" + slFundIntent.getFundIntentId()+"_"+bidPlanID);
		filter.addFilter("Q_type_S_EQ", BpMoneyManager.TYPE5);
		filter.addFilter("Q_status_S_EQ", "88");

		try {
			List<BpMoneyManager> list = bpMoneyManagerService.getAll(filter);
			for (BpMoneyManager monger : list) {
				sb.append(monger.getLoanNo());
				sb.append(",");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * 生成还款转账列表
	 * 
	 * @param bidPlanID
	 *            标ID
	 * @return
	 */
	private MoneyMoreMore createMoneyMoreMore(String bidPlanID,
			SlFundIntent slFundIntent, String bossowerFlg) {// 生成转账列表
		MoneyMoreMore moneyMoreMore = new MoneyMoreMore();

		List<MadaiLoanInfoBean> listmlib = new ArrayList<MadaiLoanInfoBean>();
		QueryFilter filter = new QueryFilter(this.getRequest());
		filter.addFilter("Q_bidPlanId_L_EQ", bidPlanID); // 标 id
		filter.addFilter("Q_fundType_S_EQ", slFundIntent.getFundType());// 还款类型
		filter.addFilter("Q_payintentPeriod_N_EQ", slFundIntent
				.getPayintentPeriod().toString());// 期数
		List<BpFundIntent> slActual = bpFundIntentService.getAll(filter);

		for (int i = 0; i < slActual.size(); i++) {
			MadaiLoanInfoBean mlib = new MadaiLoanInfoBean();
			mlib.setLoanOutMoneymoremore(bossowerFlg); // 付款人账户

			BpCustMember cust = bpCustMemberService.get(slActual.get(i)
					.getInvestPersonId());
			if (slFundIntent.getFundType().equals("principalRepayment")
					|| slFundIntent.getFundType().equals("loanInterest")) {
			mlib.setLoanInMoneymoremore(cust.getMoneymoremoreId());// 收款人 本金和利息收款人为 投资人
			}else{
				mlib.setLoanInMoneymoremore(AppUtil.getSysConfig().get("MM_PlatformMoneymoremore").toString());// 其它 为 平台
			}
			mlib.setOrderNo(UUIDGenerator.getUUID());
			mlib.setBatchNo("hk_"+slFundIntent.getFundIntentId()+"_" + bidPlanID);// hk 代表是还款的动作 加上 款项台帐ID + 标ID
			mlib.setAmount(slActual.get(i).getIncomeMoney().toString());// 转账金额
			mlib.setFullAmount("");
			mlib.setTransferName("");
			mlib.setRemark(slActual.get(i).getRemark());
			listmlib.add(mlib);
		}

		// 设置 转账列表
		moneyMoreMore.setLoanJsonList(Common.JSONEncode(listmlib));
		moneyMoreMore.setTransferAction(MoneyMoreMore.TACTION_2);
		moneyMoreMore.setAction(MoneyMoreMore.ACTION_2);
		moneyMoreMore.setTransferType(MoneyMoreMore.TTYPE_2);
		// moneyMoreMore.setNeedAudit("1"); //空.需要审核 1.自动通过
		return moneyMoreMore;

	}

	/**
	 * 获取借款人可用余额
	 * 
	 * @param borrowerFlag
	 * @return
	 */
	public BigDecimal getPayMoney(String borrowerFlag) {
		BigDecimal myMoney = new BigDecimal(0);
		String[] moneyArr = null;
		moneyArr = iPayService
				.balanceQuery(borrowerFlag, MoneyMoreMore.PTYPE_2);
		myMoney = new BigDecimal(moneyArr[1].toString());
		return myMoney;
	}

	/**
	 * 获取第三方 标识
	 * 
	 * @param bidplan
	 * @return
	 */
	private String getCustFlag(PlBidPlan bidplan) {
		// 借款人标识
		String custFlag = null;
		// 借款人ID
		Long custID = null;
		// 借款人前台注册ID
		Long custRegID = null;
		try {
			// 项目类型 企业直投 B_Dir 企业 债权 B_Or 个人直投 P_Dir 个人债权 P_Or * @return String
			if (bidplan.getProType() != null
					&& bidplan.getProType().equals("B_Dir")) {
				// 借款客户ID
				custID = bidplan.getBpBusinessDirPro().getBusinessId();
			} else if (bidplan.getProType() != null
					&& bidplan.getProType().equals("B_Or")) {
				custID = bidplan.getBpBusinessOrPro().getBusinessId();
			} else if (bidplan.getProType() != null
					&& bidplan.getProType().equals("P_Dir")) {
				custID = bidplan.getBpPersionDirPro().getPersionId();
			} else if (bidplan.getProType() != null
					&& bidplan.getProType().equals("P_Or")) {
				custID = bidplan.getBpPersionOrPro().getPersionId();
			}
			QueryFilter filter = new QueryFilter(this.getRequest());
			filter.addFilter("Q_offlineCusId_L_EQ", custID.toString());
			List<BpCustRelation> custRelation = bpCustRelationService
					.getAll(filter);
			custFlag = bpCustMemberService.get(
					custRelation.get(0).getP2pCustId()).getThirdPayFlagId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return custFlag;
	}
	
	/**
	 * 通过借款人款项台账还款 （支持多条）
	 * @return
	 */
	public String repayMentList(){
		String[] ret=null;
		String Fee="0.00"; //服务费
		String DivDetails=""; //转入账户明细
		List<BpFundIntent> bpFundintentList=new ArrayList<BpFundIntent>();
		//我的资金
		BigDecimal[] myMoney=null;
		BpFundIntent bpFundIntent=null;//投资人款项台账
		SlFundIntent fund=null;//借款人台账
		StringBuffer sb=new StringBuffer("{success:true,msg:'");
		String fundtype= this.getRequest().getParameter("fundType");//款项类型
		String postType=this.getRequest().getParameter("postType");//提交方式  0  跳出页面  1  返回 json 或者 xml 或者 string
		String[] ids=getRequest().getParameterValues("ids"); //投资人款项台账ids
		String oweParams=fundtype+"@";//自定义参数
		BpCustMember outMember=null;//付款人
		BigDecimal repayMentTotalMoney=new BigDecimal(0); //本次还款总额
		BigDecimal fullMoney=new BigDecimal("0");//满标额
		Long bpfundIntentId=null;
		PlBidPlan bidplan=null;
		for(int i=0;i<ids.length;i++){
			bpfundIntentId=Long.valueOf(ids[i]);//投资人款项台帐id
			bpFundIntent=bpFundIntentService.get(bpfundIntentId);
			bidplan=plBidPlanService.get(bpFundIntent.getBidPlanId());
			fullMoney=bidplan.getBidMoney();
			outMember=plBidPlanService.getLoanMember(bidplan);//获取借款人
			 myMoney=obSystemAccountService.sumTypeTotalMoney(outMember.getId(),"0");
			fund=slFundIntentService.getFundIntent(bpFundIntent.getProjectId(),bpFundIntent.getPayintentPeriod(),bpFundIntent.getFundType(),bpFundIntent.getBidPlanId());
			//计算服务费 如果为利息的时候 并且是还第一个投资人的时候进行收取
			if(fundtype.equals("loanInterest")&&i==0){
				SlFundIntent fund0=slFundIntentService.getFundIntent(bpFundIntent.getProjectId(),bpFundIntent.getPayintentPeriod(),"serviceMoney",bpFundIntent.getBidPlanId());
				SlFundIntent fund1=slFundIntentService.getFundIntent(bpFundIntent.getProjectId(),bpFundIntent.getPayintentPeriod(),"consultationMoney",bpFundIntent.getBidPlanId());
				BigDecimal servideMoney=new BigDecimal(0);
				BigDecimal consultationMoney=new BigDecimal(0);
				if(fund0!=null)
				{
					servideMoney=fund0.getNotMoney();
					oweParams=oweParams+"fid:"+fund0.getFundIntentId()+":"+servideMoney+"_";
				}
				if(fund1!=null)
				{
					consultationMoney=fund1.getNotMoney();
					oweParams=oweParams+"fid:"+fund1.getFundIntentId()+":"+consultationMoney+"_";
				}
				Fee=servideMoney.add(consultationMoney).toString();
			}
			//计算还投资人的总额
			repayMentTotalMoney=repayMentTotalMoney.add(bpFundIntent.getNotMoney());
		}
		// 加上平台服务费
		repayMentTotalMoney=repayMentTotalMoney.add(new BigDecimal(Fee));
		if((myMoney==null||myMoney[3].compareTo(repayMentTotalMoney)>=0)&&!configMap.get("thirdPayConfig").toString().equals(Constants.FUIOU)){

			sb.append("账户余额不足。");
		}else{
			 //账户金额大于还款金额进行还款
			try{
			for(int i=0;i<ids.length;i++){
				if(i>0){
					 Fee="0.00"; //服务费
					 DivDetails=""; //转入账户明细
				}
		     bpfundIntentId=Long.valueOf(ids[i]);//投资人款项台帐id
			bpFundIntent=bpFundIntentService.get(bpfundIntentId);
			//进行还款参数设置
			if(bpFundIntent.getNotMoney().compareTo(new BigDecimal(0))==0){
				//sb.append("false,");
				sb.append(bpFundIntent.getInvestPersonName()+"已经还款不能重复还款!");
				sb.append(",");
			}else{
				//loanInterest@fid:189:30.00_fid:188:30.00_fid:187:30.00_bfid:985:10.00fid:187:30.00_bfid:988:10.00fid:187:30.00_bfid:991:10.00
				 bpFundintentList.add(bpFundIntent);
			}
			}
			//oweParams=oweParams.substring(0,oweParams.lastIndexOf("_"));
			if(bpFundintentList!=null&&bpFundintentList.size()>0){
				ThirdPayEngine thirdPayeng=(ThirdPayEngine)AppUtil.getBean(AppUtil.getSysConfig().get("pay_beanName").toString());
				ret=thirdPayeng.repayment(fund,bpFundintentList, outMember, fullMoney, this.getBasePath(), postType, this.getResponse(), this.getRequest(), oweParams, "", "");
				if(ret!=null)
				{
					sb.append(ret[1]);	
					//sb.append(",");
				}
			}
			}catch (Exception e) {
			    e.printStackTrace();
				sb.append("请求参数错误");	
			}
			
		}
		
		sb.append("'}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 查找投资人
	 * @return
	 */
	public String selectInvestPerson() {
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpFundIntent> list= bpFundIntentService.getAll(filter);
		Type type=new TypeToken<List<CsBank>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 平台随息收费台账公用方法(把查询list的方法提取出来的原因是导出excel要用到结果集)
	 * @param map
	 * @return
	 */
	public List<SlFundIntentPeriod> common(Map<String, String> map){
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		PagingBean pb = new PagingBean(start, limit);
		List<SlFundIntentPeriod> list = new ArrayList();
		String isPay = this.getRequest().getParameter("isPay");
		//标识是还款页面传来的数据还是返款页面传来的数据
		String mark = this.getRequest().getParameter("mark");
		String plateFee = this.getRequest().getParameter("isPlateForm");
		if(loanRepayStatus!=null&&!"".equals(loanRepayStatus)&&(mark==null||"".equals(mark))){//对返款记录进行查询
			if(loanRepayStatus.equals("1")){//
				if(isPay!=null&&!"".equals(isPay)){
					if(isPay.equals("0")){//未返款
						map.put("loanerRepayStatus", "0");
						list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
					}else if(isPay.equals("1")){//已返款
						 map.put("loanerRepayStatus", "1");
						 list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
					}else if(isPay.equals("all")||isPay.equals("none")){//全选
						map.put("loanerRepayStatus", "2");
						list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
					}
				}else{
					map.put("loanerRepayStatus", "2");
					list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
				}
			}else{
				map.put("loanerRepayStatus", "0");
				list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
			}
		}else if(mark!=null&&mark.equals("repayRecord")){//对还款记录进行查询
			//对isPay进行判断  进行查询
			if(isPay!=null&&isPay.equals("all")){
				map.put("loanerRepayStatus", "4");
				list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
			}else if(isPay!=null&&isPay.equals("none")){
				map.put("loanerRepayStatus", "0");
			}else if(isPay!=null&&isPay.equals("notPay")){
				map.put("loanerRepayStatus", "3");
				list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
			}else if(isPay!=null&&isPay.equals("payed")){//
				map.put("loanerRepayStatus", "5");
				list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
			}else{
				map.put("loanerRepayStatus", "3");
				list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
			}
		}else if(plateFee!=null&&"1".equals(plateFee)){
			map.put("loanerRepayStatus", "6");
			list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
		}else{
			map.put("loanerRepayStatus", "0");
			list=slFundIntentService.listByBidPlanIdAndpayintentPeriod(pb, map);
		}
		return list;
	}
	
	public String listPeriodbyLedger(){
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<SlFundIntentPeriod> list=common(map);
		Long size = Long.valueOf("0");
		List<SlFundIntentPeriod> sizelist=slFundIntentService.listByBidPlanIdAndpayintentPeriod(null, map);
		size=Long.valueOf(sizelist.size());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[] { "intentDate" });
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[] { "factDate" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		System.out.println(buff.toString());
		System.currentTimeMillis();
		return SUCCESS;
	}
	/**
	 * 查询优惠券奖励台账
	 * @return
	 */
	public String getCouponsList(){
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<SlFundIntentPeriod> list=slFundIntentService.getCouponsList(pb, map);
		System.currentTimeMillis();
		List<SlFundIntentPeriod> sizelist=slFundIntentService.getCouponsList(null, map);
		
		size=Long.valueOf(sizelist.size());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate" });
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "factDate" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		System.currentTimeMillis();
		return SUCCESS;
	}
	/**
	 * 查询募集期奖励台账
	 * @return
	 */
	public String getraiseRateList(){
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<SlFundIntentPeriod> list=slFundIntentService.getRaiseinterestList(pb, map);
		System.currentTimeMillis();
		List<SlFundIntentPeriod> sizelist=slFundIntentService.getRaiseinterestList(null, map);
		
		size=Long.valueOf(sizelist.size());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate" });
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "factDate" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		System.currentTimeMillis();
		return SUCCESS;
	}
	public void excelCouponsBpfundDetail(){

		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<SlFundIntentPeriod> list=slFundIntentService.getCouponsList(pb, map);
		String excelName="";
		for(SlFundIntentPeriod sl:list){
			String fundType = this.getRequest().getParameter("fundType");
			if(!fundType.equals("commoninterest")){
				excelName="优惠券奖励台账";
				if(sl.getRebateType()!=null&&sl.getRebateType()!=null){
					if(sl.getRebateType().equals("1")){
						sl.setRebateTypeName("返现");
					}else if(sl.getRebateType().equals("2")){
						sl.setRebateTypeName("返息");
					}else if(sl.getRebateType().equals("3")){
						sl.setRebateTypeName("返息现");
					}else if(sl.getRebateType().equals("4")){
						sl.setRebateTypeName("加息");
					}
					if(sl.getRebateWay().equals("1")){
						sl.setRebateWayName("立返");
					}else if(sl.getRebateWay().equals("2")){
						sl.setRebateWayName("随期");
					}else if(sl.getRebateWay().equals("3")){
						sl.setRebateWayName("到期");
					}
				}
			}else{
				excelName="普通加息奖励台账";
				sl.setRebateTypeName("加息");
				sl.setRebateWayName("随期");
			}
			if(sl.getFundType().equals("principalCoupons")){
				sl.setFundTypeName("本金奖励");
			}else
			{
				sl.setFundTypeName("利息奖励");
			}
		}
		String[] tableHeader = {"序号", "招标项目名称", "招标项目编号", "返利类型","返利方式","还款期数" ,"奖励金额",
				"资金类型","计划奖励日期","实际奖励日期"};
		String[] fields = {"bidPlanName","bidPlanProjectNum","rebateTypeName","rebateWayName","payintentPeriod","incomeMoney",
				"fundTypeName","intentDate","factDate"};
		try {
			ExportExcel.export(tableHeader, fields, list,excelName, SlFundIntentPeriod.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void excelRaiseBpfundDetail(){
		
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<SlFundIntentPeriod> list=slFundIntentService.getRaiseinterestList(null, map);
		System.currentTimeMillis();
		List<SlFundIntentPeriod> sizelist=slFundIntentService.getRaiseinterestList(null, map);
		String excelName="";
		for(SlFundIntentPeriod sl:list){
			
				sl.setRebateTypeName("返息");
				sl.setRebateWayName("立返");
			
			if(sl.getFundType().equals("principalCoupons")){
				sl.setFundTypeName("本金奖励");
			}else
			{
				sl.setFundTypeName("利息奖励");
			}
		}
		String[] tableHeader = {"序号", "招标项目名称", "招标项目编号", "返利类型","返利方式","募集期利率" ,"奖励金额",
				"资金类型","计划奖励日期","实际奖励日期"};
		String[] fields = {"bidPlanName","bidPlanProjectNum","rebateTypeName","rebateWayName","raiseRate","incomeMoney",
				"fundTypeName","intentDate","factDate"};
		try {
			ExportExcel.export(tableHeader, fields, list,"募集期奖励台账", SlFundIntentPeriod.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void excelCouponsIncome(){
		
		List<SlFundIntentPeriod> list=bpFundIntentService.getCouponsIncome(null);
		String[] tableHeader = {"序号", "投资人", "用户名", "优惠券收益奖励","普通加息收益奖励","累计收益奖励" ,"未收收益"};
		String[] fields = {"receiverName","receiverP2PName","couponsIncome","addRateIncome","sumIncome","notIncome"};
		try {
			ExportExcel.export(tableHeader, fields, list,"优惠券收益查询", SlFundIntentPeriod.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void excelRepayInfo(){
		Map<String, String> map = new HashMap<String, String>();
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}

		getParams(map);

		List<SlFundIntentPeriod> list=slFundIntentService.listexcelRepayInfo(map);

		String[] tableHeader = {"序号", "返息日期", "企业名称", "标的名称","借款金额","投资期限" ,"收益率","投资人数","到期日期","本金","利息","管理服务费","合计","下次返息日"};
		String[] fields = {"intentDate","borrowName","bidPlanName","bidMoney","term","yearInterestRate","investNum","endIntentDate","principal","interest","manageMoney","allTotalMoney","nestPayBankDate"};
		try {
			ExportExcel.export(tableHeader, fields, list,"还款情况表", SlFundIntentPeriod.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void getParams(Map<String, String> map) {
		String isPay = this.getRequest().getParameter("isPay");
		//标识是还款页面传来的数据还是返款页面传来的数据
		String mark = this.getRequest().getParameter("mark");
		if(loanRepayStatus!=null&&!"".equals(loanRepayStatus)&&(mark==null||"".equals(mark))){//对返款记录进行查询
			if(loanRepayStatus.equals("1")){//
				if(isPay!=null&&!"".equals(isPay)){
					if(isPay.equals("0")){//未返款
						map.put("loanerRepayStatus", "0");
					}else if(isPay.equals("1")){//已返款
						map.put("loanerRepayStatus", "1");
					}else if(isPay.equals("all")||isPay.equals("none")){//全选
						map.put("loanerRepayStatus", "2");
					}
				}else{
					map.put("loanerRepayStatus", "2");
				}
			}else{
				map.put("loanerRepayStatus", "0");
			}
		}else if(mark!=null&&mark.equals("repayRecord")){//对还款记录进行查询
			//对isPay进行判断  进行查询
			if(isPay!=null&&isPay.equals("all")){
				map.put("loanerRepayStatus", "4");
			}else if(isPay!=null&&isPay.equals("none")){
				map.put("loanerRepayStatus", "0");
			}else if(isPay!=null&&isPay.equals("notPay")){
				map.put("loanerRepayStatus", "3");
			}else if(isPay!=null&&isPay.equals("payed")){//
				map.put("loanerRepayStatus", "5");
			}else{
				map.put("loanerRepayStatus", "3");
			}
		}else{
			map.put("loanerRepayStatus", "0");
		}
	}


	/**
	 * 逾期项目管理
	 * @return
	 */
	public  String listPeriodbyOverDue(){
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<SlFundIntentPeriod> list=slFundIntentService.listOverDueByBidPlanIdAndpayintentPeriod(pb, map);
		List<SlFundIntentPeriod> sizelist=slFundIntentService.listOverDueByBidPlanIdAndpayintentPeriod(null, map);

		size=Long.valueOf(sizelist.size());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[] { "intentDate" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		System.currentTimeMillis();
		return SUCCESS;
	}
	/**
	 * 逾期款项管理导出excel
	 */
	public void listOverDueToExcel(){
		
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		PagingBean pb = new PagingBean(start, limit);
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<SlFundIntentPeriod> list=slFundIntentService.listOverDueByBidPlanIdAndpayintentPeriod(pb, map);
		for(SlFundIntentPeriod sl:list){
			BigDecimal total=sl.getPrincipalRepayment().getIncomeMoney().add((sl.getLoanInterest().getIncomeMoney())).add((sl.getConsultationMoney().getIncomeMoney())).add((sl.getServiceMoney().getIncomeMoney())).add((sl.getInterestPenalty().getIncomeMoney()))
			           .add((sl.getPrincipalRepayment().getAccrualMoney())).add((sl.getLoanInterest().getAccrualMoney())).add((sl.getConsultationMoney().getAccrualMoney())).add((sl.getServiceMoney().getAccrualMoney()));
            sl.setAllTotalMoney(total);
	}
		String[] tableHeader = {"序号", "借款人", "招标项目名称", "招标项目编号","期数","本金" ,"利息","管理咨询费","财务服务费","补偿息","逾期天数","罚息","合计","计划还款日"};
		try {
			ExcelHelper.toOverDueExcel(list,tableHeader, "逾期款项管理");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//工作桌面展示逾期提醒款项
	public String display(){
		PagingBean pb = new PagingBean(0, 7);
		Map<String, String> map = new HashMap<String, String>();
		Long size = Long.valueOf("0");
		Date[] date = null;
		String searchaccount;
		Enumeration paramEnu = getRequest().getParameterNames();
		while (paramEnu.hasMoreElements()) {
			String paramName = (String) paramEnu.nextElement();
			String paramValue = (String) getRequest().getParameter(paramName);
			map.put(paramName, paramValue);
		}
		List<SlFundIntentPeriod> list=slFundIntentService.listOverDueByBidPlanIdAndpayintentPeriod(pb, map);
		getRequest().setAttribute("overDuePlanList", list);
		return "display";
	}
	
	public void outputExcel(){
		String [] tableHeader = {"序号","借款人","招标项目名称","招标项目编号","期数","管理咨询费","财务服务费","合计","计划还款日","实际到账金额","实际到账日期","还款方式"};
		try {
			Map<String, String> map = new HashMap<String, String>();
			Enumeration paramEnu = getRequest().getParameterNames();
			while (paramEnu.hasMoreElements()) {
				String paramName = (String) paramEnu.nextElement();
				String paramValue = (String) getRequest().getParameter(paramName);
				map.put(paramName, paramValue);
			}
			List<SlFundIntentPeriod> list=common(map);
			ExcelHelper.export(list,tableHeader,"平台随息收费台账");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BpFundProject getOwnBpFundProject() {
		return ownBpFundProject;
	}

	public void setOwnBpFundProject(BpFundProject ownBpFundProject) {
		this.ownBpFundProject = ownBpFundProject;
	}

	public FlFinancingProject getFlFinancingProject() {
		return flFinancingProject;
	}

	public void setFlFinancingProject(FlFinancingProject flFinancingProject) {
		this.flFinancingProject = flFinancingProject;
	}

	public GLGuaranteeloanProject getgLGuaranteeloanProject() {
		return gLGuaranteeloanProject;
	}

	public void setgLGuaranteeloanProject(
			GLGuaranteeloanProject gLGuaranteeloanProject) {
		this.gLGuaranteeloanProject = gLGuaranteeloanProject;
	}

	public String getIsHaveLending() {
		return isHaveLending;
	}

	public void setIsHaveLending(String isHaveLending) {
		this.isHaveLending = isHaveLending;
	}

	public String getRelateIntentOrCharge() {
		return relateIntentOrCharge;
	}

	public void setRelateIntentOrCharge(String relateIntentOrCharge) {
		this.relateIntentOrCharge = relateIntentOrCharge;
	}

	public SlFundQlide getSlFundQlide() {
		return slFundQlide;
	}

	public void setSlFundQlide(SlFundQlide slFundQlide) {
		this.slFundQlide = slFundQlide;
	}

	public SlAlterAccrualRecord getSlAlterAccrualRecord() {
		return slAlterAccrualRecord;
	}

	public void setSlAlterAccrualRecord(
			SlAlterAccrualRecord slAlterAccrualRecord) {
		this.slAlterAccrualRecord = slAlterAccrualRecord;
	}

	public SlSuperviseRecord getSlSuperviseRecord() {
		return slSuperviseRecord;
	}

	public void setSlSuperviseRecord(SlSuperviseRecord slSuperviseRecord) {
		this.slSuperviseRecord = slSuperviseRecord;
	}

	public Long getFundQlideId() {
		return fundQlideId;
	}

	public void setFundQlideId(Long fundQlideId) {
		this.fundQlideId = fundQlideId;
	}

	public int getFlag1() {
		return flag1;
	}

	public void setFlag1(int flag1) {
		this.flag1 = flag1;
	}

	public int getFlag2() {
		return flag2;
	}

	public void setFlag2(int flag2) {
		this.flag2 = flag2;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getCalcutype() {
		return calcutype;
	}

	public void setCalcutype(String calcutype) {
		this.calcutype = calcutype;
	}

	public SlSmallloanProject getSlSmallloanProject() {
		return slSmallloanProject;
	}

	public void setSlSmallloanProject(SlSmallloanProject slSmallloanProject) {
		this.slSmallloanProject = slSmallloanProject;
	}

	public String getSlFundIentJson() {
		return slFundIentJson;
	}

	public void setSlFundIentJson(String slFundIentJson) {
		this.slFundIentJson = slFundIentJson;
	}

	private Long fundIntentId;

	public Long getFundIntentId() {
		return fundIntentId;
	}

	public void setFundIntentId(Long fundIntentId) {
		this.fundIntentId = fundIntentId;
	}

	public SlFundIntent getSlFundIntent() {
		return slFundIntent;
	}

	public void setSlFundIntent(SlFundIntent slFundIntent) {
		this.slFundIntent = slFundIntent;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Boolean getIsGrantedShowAllProjects() {
		return isGrantedShowAllProjects;
	}

	public void setIsGrantedShowAllProjects(Boolean isGrantedShowAllProjects) {
		this.isGrantedShowAllProjects = isGrantedShowAllProjects;
	}

	public ObObligationProject getObObligationProject() {
		return obObligationProject;
	}

	public void setObObligationProject(ObObligationProject obObligationProject) {
		this.obObligationProject = obObligationProject;
	}

	public PawnContinuedManagment getPawnContinuedManagment() {
		return pawnContinuedManagment;
	}

	public void setPawnContinuedManagment(
			PawnContinuedManagment pawnContinuedManagment) {
		this.pawnContinuedManagment = pawnContinuedManagment;
	}

	public FlLeaseFinanceProject getFlLeaseFinanceProject() {
		return flLeaseFinanceProject;
	}

	public void setFlLeaseFinanceProject(
			FlLeaseFinanceProject flLeaseFinanceProject) {
		this.flLeaseFinanceProject = flLeaseFinanceProject;
	}

	public PlPawnProject getPlPawnProject() {
		return plPawnProject;
	}

	public void setPlPawnProject(PlPawnProject plPawnProject) {
		this.plPawnProject = plPawnProject;
	}

	public SlFundDetailService getSlFundDetailService() {
		return slFundDetailService;
	}

	public SlEarlyRepaymentRecord getSlEarlyRepaymentRecord() {
		return slEarlyRepaymentRecord;
	}

	public void setSlEarlyRepaymentRecord(
			SlEarlyRepaymentRecord slEarlyRepaymentRecord) {
		this.slEarlyRepaymentRecord = slEarlyRepaymentRecord;
	}

	public void setSlFundDetailService(SlFundDetailService slFundDetailService) {
		this.slFundDetailService = slFundDetailService;
	}

	public SlFundQlideService getSlFundQlideService() {
		return slFundQlideService;
	}

	public void setSlFundQlideService(SlFundQlideService slFundQlideService) {
		this.slFundQlideService = slFundQlideService;
	}

	public BpFundProject getPlatFormBpFundProject() {
		return platFormBpFundProject;
	}

	public void setPlatFormBpFundProject(BpFundProject platFormBpFundProject) {
		this.platFormBpFundProject = platFormBpFundProject;
	}

	public String getFundType() {
		return fundType;
	}

	public void setFundType(String fundType) {
		this.fundType = fundType;
	}
	public Long getPunishInterestId() {
		return punishInterestId;
	}
	public void setPunishInterestId(Long punishInterestId) {
		this.punishInterestId = punishInterestId;
	}
	public String getQlideId() {
		return qlideId;
	}
	public void setQlideId(String qlideId) {
		this.qlideId = qlideId;
	}

}
