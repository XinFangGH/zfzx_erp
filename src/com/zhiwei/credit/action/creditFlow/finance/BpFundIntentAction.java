package com.zhiwei.credit.action.creditFlow.finance;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayInterfaceUtil;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.project.impl.FundIntentGenerate;
import com.zhiwei.credit.core.project.impl.PrepaymentFundIntentCreate;
import com.zhiwei.credit.core.project.impl.TransferFundIntentCreate;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.BpFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.DictionaryIndependent;
import com.zhiwei.credit.service.coupon.BpCouponsService;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObAccountDealInfoService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.FundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.MySelfService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.customer.InvestPersonService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.SysConfigService;
import com.zhiwei.credit.service.thirdInterface.YeePayService;
import com.zhiwei.credit.service.thirdPay.BpThirdpayCityService;
import com.zhiwei.credit.service.thirdPay.ThirdpayClientService;
import com.zhiwei.credit.service.thirdPay.fuiou.ThirdPayService;
import com.zhiwei.credit.util.Common;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

@SuppressWarnings({"unchecked","unused"})
public class BpFundIntentAction extends BaseAction {
	@Resource
	private BpThirdpayCityService bpThirdpayCityService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private SysConfigService sysConfigService;
    @Resource
    private ObSystemAccountService obSystemAccountService;
	@Resource
	private CsBankService csBankService;
	@Resource
	private CsInvestmentpersonService csInvestmentpersonService;
	@Resource
	private ObAccountDealInfoService obAccountDealInfoService;
	@Resource
	private YeePayService yeePayService;
	
	@Resource
	private ThirdPayService thirdPayService;

	@Resource
	private InvestPersonService investPersonService;
	@Resource
	private MySelfService mySelfService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private FundIntentService fundIntentService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	@Resource
	private PlPawnProjectService plPawnProjectService;
	@Resource
	private InvestEnterpriseService investEnterpriseService;
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private BpFundIntentService bpFundintentService;
	@Resource
	private ThirdpayClientService thirdpayClientService;
	@Resource
	private PlBidInfoService plBidInfoService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private BpCouponsService bpCouponsService;
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	private BpFundProject ownBpFundProject;
	private BpFundProject platFormBpFundProject;
	private String preceptId;
	private Long bidPlanId;

	private BpFundIntent bpFundIntent;
	private Long bpFundIntentId;
	private Long slEarlyRepaymentId;
	private SlEarlyRepaymentRecord slEarlyRepaymentRecord;
	
	
	public SlEarlyRepaymentRecord getSlEarlyRepaymentRecord() {
		return slEarlyRepaymentRecord;
	}

	public void setSlEarlyRepaymentRecord(
			SlEarlyRepaymentRecord slEarlyRepaymentRecord) {
		this.slEarlyRepaymentRecord = slEarlyRepaymentRecord;
	}

	public Long getSlEarlyRepaymentId() {
		return slEarlyRepaymentId;
	}

	public void setSlEarlyRepaymentId(Long slEarlyRepaymentId) {
		this.slEarlyRepaymentId = slEarlyRepaymentId;
	}

	public Long getBpFundIntentId() {
		return bpFundIntentId;
	}

	public void setBpFundIntentId(Long bpFundIntentId) {
		this.bpFundIntentId = bpFundIntentId;
	}

	public BpFundIntent getBpFundIntent() {
		return bpFundIntent;
	}

	public void setBpFundIntent(BpFundIntent bpFundIntent) {
		this.bpFundIntent = bpFundIntent;
	}

	public String list() {
		int size = 0;

		//String fundResource=this.getRequest().getParameter("fundResource");
		String preceptId=this.getRequest().getParameter("preceptId");
		String businessType=this.getRequest().getParameter("businessType");

		String flag1 = this.getRequest().getParameter("flag1");
		//String projectId = this.getRequest().getParameter("projectId");
		List<FundIntent> list = new ArrayList<FundIntent>();
		List<FundIntent> list1 = new ArrayList<FundIntent>();

		if("0".equals(flag1)){
			//SlSmallloanProject project = slSmallloanProjectService.get(Long.parseLong(projectId));
			if(ownBpFundProject!=null){
				ownBpFundProject.setId(Long.valueOf(preceptId));
				List<InvestPersonInfo> li = new ArrayList<InvestPersonInfo>();
				if(null!=bidPlanId){
					li = investPersonInfoService.getByBidPlanId(bidPlanId);
				}else{
					li = investPersonInfoService.getByMoneyPlanId(Long.parseLong(preceptId));
				}
				List<FundIntent> filist = new ArrayList<FundIntent>();
				if(li!=null&&li.size()!=0){
					for(InvestPersonInfo person:li){
						FundIntentGenerate sundIntentGeneraten=new FundIntentGenerate(ownBpFundProject,person,"yes");
						filist=sundIntentGeneraten.createInvesterFundIntent();
						list1.addAll(filist);
						for(FundIntent l:filist){
							l.setInvestPersonId(person.getInvestPersonId());
							
						}
						if(ownBpFundProject.getAccrualtype().equals("singleInterest")){
							for(FundIntent l:filist){
								if(l.getFundType()=="loanInterest"||l.getFundType()=="riskRate"){
									l.setPayintentPeriod(l.getPayintentPeriod()+1);
								}
								
							}
							
							filist.get(filist.size()-1).setPayintentPeriod(filist.get(filist.size()-2).getPayintentPeriod());
							
						}
					}
				}
				
				
			}else if(platFormBpFundProject!=null){
				//通过bidPlanId 获取投资人
				//plBidPlanService.get(bidPlanId);
				List<InvestPersonInfo> li = new ArrayList<InvestPersonInfo>();
				if (null != bidPlanId) {
					li = investPersonInfoService.getByBidPlanId(bidPlanId);
				} else {
					li = investPersonInfoService.getByMoneyPlanId(Long
							.parseLong(preceptId));
				}

				List<FundIntent> filist = new ArrayList<FundIntent>();
/*				platFormBpFundProject.setYearFinanceServiceOfRate(new BigDecimal(0));
				platFormBpFundProject.setFinanceServiceOfRate(new BigDecimal(0));
				platFormBpFundProject.setDayFinanceServiceOfRate(new BigDecimal(0));
				platFormBpFundProject.setManagementConsultingOfRate(new BigDecimal(0));
				platFormBpFundProject.setYearManagementConsultingOfRate(new BigDecimal(0));
				platFormBpFundProject.setDayManagementConsultingOfRate(new BigDecimal(0));*/
				if (li != null && li.size() != 0) {
					for (InvestPersonInfo person : li) {
						FundIntentGenerate sundIntentGeneraten = new FundIntentGenerate(
								platFormBpFundProject, person, "yes");
						filist = sundIntentGeneraten.createInvesterFundIntent();
				
						list1.addAll(filist);
					}
				}
				
			}
			if (list1 != null) {
			} else {
				size = 0;
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
		} else {
			if (null != bidPlanId) {
				if(null!=slEarlyRepaymentId){
					list= fundIntentService.listNoEarlyId(bidPlanId, slEarlyRepaymentId);
				}else{
					list = fundIntentService.getListByBidPlanId(bidPlanId);
				}
			} else if (null != preceptId && !"".equals(preceptId)) {
				list = fundIntentService.getListByPreceptId(Long
						.parseLong(preceptId));
			}
			if (list != null) {
				for (FundIntent l : list) {
					fundIntentService.evict(l);
					List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService.getByDicKey(l.getFundType());
					if (null != dictionaryIndependent
							&& dictionaryIndependent.size() != 0)
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
						l.setOverdueRate(flFinancingProject.getOverdueRate());
					}
					if (businessType.equals("Pawn")) {
						PlPawnProject pawn = plPawnProjectService.get(l
								.getProjectId());
						if (l.getFundType().equals("pawnPrincipalRepayment")) {
							l.setOverdueRate(pawn.getOverdueRateLoan());
						} else {
							l.setOverdueRate(pawn.getOverdueRate());
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
					InvestPersonInfo pif = investPersonInfoService.get(l
							.getInvestPersonId());
					if (null != pif) {
						l.setFundResource(pif.getFundResource() + "");
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
		}

		return SUCCESS;
	}
	public String listloan() {
		try{
			int size=0;
			String projectId=this.getRequest().getParameter("projectId");
			String createType=this.getRequest().getParameter("createType");
			String businessType=this.getRequest().getParameter("businessType");
			String preceptId=this.getRequest().getParameter("preceptId");
			String flag1 = this.getRequest().getParameter("flag1");
			List<FundIntent> list = new ArrayList<FundIntent>();
			List<BpFundIntent> list1 = new ArrayList<BpFundIntent>();
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			if("0".equals(flag1)){
				if(platFormBpFundProject!=null){
					List<InvestPersonInfo> li = new ArrayList<InvestPersonInfo>();
					if(null!=bidPlanId){
						li = investPersonInfoService.getByBidPlanId(bidPlanId);
					}else{
						li = investPersonInfoService.getByMoneyPlanId(Long.parseLong(preceptId));
					}
					 PlBidPlan plBidPlan=plBidPlanService.get(bidPlanId);
					 plBidPlan.setStartIntentDate(platFormBpFundProject.getStartInterestDate());
					 plBidPlan.setEndIntentDate(platFormBpFundProject.getIntentDate());
					 BpFundProject project=bpFundProjectService.get(Long.parseLong(preceptId));
					 bpFundProjectService.evict(project);
					 boolean createCoupons=false;//判断是否设置有投资人奖励
					if((null !=plBidPlan.getRaiseRate() && plBidPlan.getRaiseRate().compareTo(new BigDecimal(0))>0) ||
							(null !=plBidPlan.getAddRate() && plBidPlan.getAddRate().compareTo(new BigDecimal(0))>0) ||
							(null !=plBidPlan.getCoupon() && plBidPlan.getCoupon()==1)
							){
							createCoupons=true;
					}
					if(li!=null&&li.size()!=0){
						project.setStartInterestDate(platFormBpFundProject.getStartInterestDate());
						project.setIntentDate(platFormBpFundProject.getIntentDate());
						project.setProjectMoney(platFormBpFundProject.getProjectMoney());
						if(null!=plBidPlan.getProType() && (plBidPlan.getProType().equals("B_Dir") || plBidPlan.getProType().equals("P_Dir"))){
							for(InvestPersonInfo person:li){
								List<BpFundIntent> filist = new ArrayList<BpFundIntent>();
								
								if(createType!=null&&!createType.equals("")&&createType.equals("couponInterest")){
									if(createCoupons){
												bpFundintentService.deleteFundType("coupon",bidPlanId);
												PlBidInfo info = plBidInfoService.getByOrderNo(person.getOrderNo());
												person.setInvestTime(info.getBidtime());
												//计算优惠券利息
												bpFundIntentService.calculateSubjoinInterest(plBidPlan, person);
												FundIntentGenerate sundIntentGeneraten=new FundIntentGenerate(project,person,"yes");
												filist=sundIntentGeneraten.createCouponInterestBpFundIntent(createType,plBidPlan);
												list1.addAll(filist);
									}else{
										setJsonString("{success:true,msg:'此标没有设置投资人奖励'}");
										return SUCCESS;
									}
									
									
								}else{
									bpFundintentService.deleteFundIntent(bidPlanId);
									FundIntentGenerate sundIntentGeneraten=new FundIntentGenerate(project,person,"yes");
									filist=sundIntentGeneraten.createInvesterBpFundIntent();
									list1.addAll(filist);
								}
								
							}
						}else{ 
							List<SlFundIntent> slist=slFundIntentService.listByPreceptIdAndDate(project.getProjectId(), project.getBusinessType(), project.getId(), sd.format(platFormBpFundProject.getStartInterestDate()), "loanInterest");
							if(null!=slist && slist.size()>0){
								SlFundIntent s=slist.get(0);
								for(InvestPersonInfo person:li){
									List<BpFundIntent> filist = new ArrayList<BpFundIntent>();
									if(createType!=null&&!createType.equals("")&&createType.equals("couponInterest")){
										if(createCoupons){
													bpFundintentService.deleteFundType("coupon",bidPlanId);
													PlBidInfo info = plBidInfoService.getByOrderNo(person.getOrderNo());
													person.setInvestTime(info.getBidtime());
													//计算优惠券利息
													bpFundIntentService.calculateSubjoinInterest(plBidPlan, person);
													
													TransferFundIntentCreate transferFundIntentCreate=new TransferFundIntentCreate(project,person,s,"yes");
													filist=transferFundIntentCreate.createCouponsBpFundIntent(createType,plBidPlan);
													list1.addAll(filist);
										}else{
											setJsonString("{success:true,msg:'此标没有设置投资人奖励'}");
											return SUCCESS;
										}
										
									}else{
										bpFundintentService.deleteFundIntent(bidPlanId);
										TransferFundIntentCreate transferFundIntentCreate=new TransferFundIntentCreate(project,person,s,"yes");
										filist=transferFundIntentCreate.createInvesterBpFundIntent();
										list1.addAll(filist);
									}
								}
								
							}
						}
					}
					bpFundIntentService.saveFundIntent(list1,bidPlanId);
				}
				setJsonString("{success:true,msg:'生成成功'}");
			}else{
				StringBuffer buff = new StringBuffer("{success:true,result:[");
				List fundList=bpFundIntentService.bidFundList(bidPlanId);
				for(int i=0;i<fundList.size();i++){
					Object[] obj=(Object[]) fundList.get(i);
					BigDecimal money=new BigDecimal(0);
					buff.append("{\"payintentPeriod\":");
					buff.append(obj[0]);
					buff.append(",\"principal\":'");
					if(null!=obj[1]){
						money=money.add(new BigDecimal(obj[1].toString()));
						buff.append(obj[1]);
					}
					
					buff.append("',\"interest\":'");
					if(null!=obj[2]){
						money=money.add(new BigDecimal(obj[2].toString()));
						buff.append(obj[2]);
					}
					buff.append("',\"consultationMoney\":'");
					if(null!=obj[3]){
						money=money.add(new BigDecimal(obj[3].toString()));
						buff.append(obj[3]);
					}
					buff.append("',\"serviceMoney\":'");
					if(null!=obj[4]){
						money=money.add(new BigDecimal(obj[4].toString()));
						buff.append(obj[4]);
					}
					buff.append("',\"intentDate\":'");
					if(null!=obj[5]){
						buff.append(sd.format(obj[5]));
					}
					
					buff.append("',\"interestPenalty\":'");
					if(null!=obj[8]){
						money=money.add(new BigDecimal(obj[8].toString()));
						buff.append(obj[8]);
					}
					buff.append("',\"accrualMoney\":'");
					if(null!=obj[9]){
						money=money.add(new BigDecimal(obj[9].toString()));
						buff.append(obj[9]);
					}
					
					buff.append("',\"sumMoney\":'");
					buff.append(money);
					
					buff.append("',\"factDate\":'");
					if(null!=obj[6]){
						buff.append(sd.format(obj[6]));
					}
					buff.append("',\"afterMoney\":'");
					if(null!=obj[7]){
						buff.append(obj[7]);
					}
					
					buff.append("'},");
				}
				if(null!=fundList && fundList.size()>0){
					buff.deleteCharAt(buff.length()-1);
				}
				buff.append("]}");
					/*List<InvestPersonInfo> li = new ArrayList<InvestPersonInfo>();
					if(null!=bidPlanId){
						li = investPersonInfoService.getByBidPlanId(bidPlanId);
					}else{
						li = investPersonInfoService.getByMoneyPlanId(Long.parseLong(preceptId));
					}
					
				
					if(li!=null&&li.size()!=0){
					
						int i=1;
						for(InvestPersonInfo person:li){
							List<BpFundIntent> filist = new ArrayList<BpFundIntent>();
							
							if(null!=slEarlyRepaymentId){
								filist=bpFundintentService.listOfInvestAndEarlyId(person.getOrderNo(), "",slEarlyRepaymentId);
							}else{
								filist=bpFundintentService.listOfInverstPerson(person.getOrderNo(), "");
							}
						   if(i==1){
							   
							   list1.addAll(filist);
							   
						   }else{
							   for(int j=0;j<list1.size();j++){
								   fundIntentService.evict(list1.get(j));
								   if(list1.get(j).getIncomeMoney().compareTo(new BigDecimal("0"))==1){
									   list1.get(j).setIncomeMoney( list1.get(j).getIncomeMoney().add(filist.get(j).getIncomeMoney()));
									   list1.get(j).setNotMoney( list1.get(j).getIncomeMoney());
								   }else{
									   list1.get(j).setPayMoney( list1.get(j).getPayMoney().add(filist.get(j).getPayMoney()));
									   list1.get(j).setNotMoney( list1.get(j).getPayMoney());
								   }
								   list1.get(j).setFundTypeName(dictionaryIndependentService.getByDicKey(list1.get(j).getFundType()).get(0).getItemValue());
							   }
							   
						   }
							
							
							i++;
							
							
					
						}
					}*/
					
				jsonString = buff.toString();
					
				}
		}catch(Exception e){
			setJsonString("{success:true,msg:'生成失败'}");
			e.printStackTrace();
		}
		return SUCCESS;
		}
	
	public String list2() {
		QueryFilter filter = new QueryFilter(getRequest());
		String sdate = getRequest().getParameter("sdate");
		String edate = getRequest().getParameter("edate");
		if(null!=sdate&&!"".equals(sdate)){
			filter.addFilter("Q_intentDate_DL_GE", sdate);
		}
		if(null!=edate&&!"".equals(edate)){
			filter.addFilter("Q_intentDate_DG_LE", edate);
		}
		List inList=new ArrayList();
		inList.add("principalLending");
		inList.add("principalRepayment");
		inList.add("loanInterest");
		filter.addFilterIn("Q_fundType_S_IN", inList);
		List<BpFundIntent> list = bpFundintentService.getAll(filter);
		if (list != null && list.size() > 0) {
			for (BpFundIntent s : list) {
				if ("1".equals(s.getFundResource())) {
					s.setFundResource("1");
				} else if ("2".equals(s.getFundResource())) {
					s.setFundResource("0");
				}
			}
		}
		Type type = new TypeToken<List<BpFundIntent>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate", "interestStarTime", "interestEndTime");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate", "factDate", "interestStarTime",
						"interestEndTime" });
		buff.append(json.serialize(list));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}
	
	/**
	 * 查询一个标下的所有的奖励利息，加息的，优惠券的
	 * @return
	 */
	public String listInterest(){
		String bidPlanId = this.getRequest().getParameter("bidPlanId");
		List<BpFundIntent> list = bpFundIntentService.listInterest(Long.valueOf(bidPlanId));
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate", "interestStarTime", "interestEndTime");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[] { "intentDate", "factDate", "interestStarTime","interestEndTime" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 即将到期的项目
	 * @return
	 */
	public String list5() {
		QueryFilter filter = new QueryFilter(getRequest());
		int num=0;
		if(this.getRequest().getParameter("num")!=null&&!this.getRequest().getParameter("num").equals("")){
			num=Integer.valueOf(this.getRequest().getParameter("num"));
		}
		if(num>0){
		String startDate=DateUtil.dateToStr(new Date(),"yyyy-MM-dd");//开始日期
		String endDate=DateUtil.dateToStr(DateUtil.addDaysToDate(new Date(), num), "yyyy-MM-dd");	//结束日期	
			filter.addFilter("Q_intentDate_DL_GE", startDate);
			filter.addFilter("Q_intentDate_DL_LE", endDate);
		}
		filter.addFilter("Q_notMoney_BD_GT", "0");
		filter.addSorted("intentDate", QueryFilter.ORDER_ASC);
		filter.addFilter("Q_isValid_SN_EQ", "0");
		filter.addFilter("Q_isCheck_SN_EQ", "0");
		List<BpFundIntent> list = bpFundintentService.getAll(filter);
		/*if (list != null && list.size() > 0) {
			for (BpFundIntent s : list) {
				if ("1".equals(s.getFundResource())) {
					s.setFundResource("1");
				} else if ("2".equals(s.getFundResource())) {
					s.setFundResource("0");
				}
			}
		}*/
		Type type = new TypeToken<List<BpFundIntent>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate", "interestStarTime", "interestEndTime");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate", "factDate", "interestStarTime",
						"interestEndTime" });
		buff.append(json.serialize(list));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	public String list3() {
		List<BpFundIntent> list = bpFundintentService.getListBysql(this.getRequest(),start,limit);
		List<BpFundIntent> listcount = bpFundintentService.getListBysql(this.getRequest(),null,null);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(listcount!=null?listcount.size():0).append(
						",result:");

		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate", "interestStarTime", "interestEndTime");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate", "factDate", "interestStarTime",
						"interestEndTime" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();

		return SUCCESS;
	}
	
	/**
	 * 合并两条附近利息返回json串
	 * @param sb1
	 * @param subjoinInterestList
	 * @param couponInterestList
	 * @return
	 */
	private String combine(StringBuffer sb1,List<BpFundIntent> subjoinInterestList,List<BpFundIntent> couponInterestList){
		 SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		 int index = 0;
		 //优惠券利息
		 if(subjoinInterestList!=null&&subjoinInterestList.size()>0){
			 index = 1;
			 BpFundIntent bpFundIntent = subjoinInterestList.get(0);
			 sb1.append("{\"payintentPeriod\":")
				.append("\"").append("0").append("\"").append(",")
				.append("\"principal\":")
				.append("\"").append("0").append("\"").append(",")
				.append("\"interest\":")
				.append("\"").append("0").append("\"").append(",");
			 	 //优惠券利息
				 if(couponInterestList!=null&&couponInterestList.size()>0){
					 index = 2;
					 sb1.append("\"couponInterest\":")
					    .append("\"").append(couponInterestList.get(0).getIncomeMoney().toString()).append("\"").append(",")
					    .append("\"sumMoney\":")
						.append("\"").append(bpFundIntent.getIncomeMoney().add(couponInterestList.get(0).getIncomeMoney()).toString()).append("\"").append(",");
				 }else{
					 sb1.append("\"couponInterest\":")
					    .append("\"").append("0").append("\"").append(",")
					    .append("\"sumMoney\":")
						.append("\"").append(bpFundIntent.getIncomeMoney().toString()).append("\"").append(",");
				 }
				 //加息利息
				 if(subjoinInterestList!=null&&subjoinInterestList.size()>0){
					 sb1.append("\"subjoinInterest\":")
					    .append("\"").append(bpFundIntent.getIncomeMoney().toString()).append("\"").append(",");
				 }else{
					 sb1.append("\"subjoinInterest\":")
					    .append("\"").append("0").append("\"").append(",");
				 }
				 //计划放款日
				 sb1.append("\"intentDate\":")
			    .append("\"").append(bpFundIntent.getIncomeMoney().toString()).append("\"").append(",")
				.append("\"intentDate\":")
				.append("\"").append(sd.format(bpFundIntent.getIntentDate())).append("\"").append(",")
				
				
				.append("\"factDate\":")
				.append("\"").append("").append("\"").append(",")
				.append("\"afterMoney\":")
				.append("\"").append("0").append("\"").append("")
				.append("}");
		 }
		 if(index==2){//说明即有加息又有优惠券利息
			 return sb1.toString();
		 }
		 
		//优惠券利息
		 if(couponInterestList!=null&&couponInterestList.size()>0){
			 BpFundIntent bpFundIntent = couponInterestList.get(0);
			 sb1.append("{\"payintentPeriod\":")
				.append("\"").append("0").append("\"").append(",")
				.append("\"principal\":")
				.append("\"").append("0").append("\"").append(",")
				.append("\"interest\":")
				.append("\"").append("0").append("\"").append(",");
			 	 //优惠券利息
				 if(couponInterestList!=null&&couponInterestList.size()>0){
					 sb1.append("\"couponInterest\":")
					    .append("\"").append(couponInterestList.get(0).getIncomeMoney().toString()).append("\"").append(",");
				 }else{
					 sb1.append("\"couponInterest\":")
					    .append("\"").append("0").append("\"").append(",");
				 }
				 //加息利息
				 if(subjoinInterestList!=null&&subjoinInterestList.size()>0){
					 sb1.append("\"subjoinInterest\":")
					    .append("\"").append(bpFundIntent.getIncomeMoney().toString()).append("\"").append(",");
				 }else{
					 sb1.append("\"subjoinInterest\":")
					    .append("\"").append("0").append("\"").append(",");
				 }
				 //计划放款日
				 sb1.append("\"intentDate\":")
			    .append("\"").append(bpFundIntent.getIncomeMoney().toString()).append("\"").append(",")
				.append("\"intentDate\":")
				.append("\"").append(sd.format(bpFundIntent.getIntentDate())).append("\"").append(",")
				.append("\"sumMoney\":")
				.append("\"").append(bpFundIntent.getIncomeMoney().toString()).append("\"").append(",")
				.append("\"factDate\":")
				.append("\"").append("").append("\"").append(",")
				.append("\"afterMoney\":")
				.append("\"").append("0").append("\"").append("")
				.append("}");
		 }
		 
		 return sb1.toString();
	}
	
	public String listOfInverstPerson() {
		String orderNo=this.getRequest().getParameter("orderNo");
		/*List<BpFundIntent> list = bpFundintentService.listOfInverstPerson(orderNo, "'principalRepayment','principalLending','loanInterest'");
		for(BpFundIntent l:list){
			List<DictionaryIndependent> dictionaryIndependent = dictionaryIndependentService.getByDicKey(l.getFundType());
			if (null != dictionaryIndependent
					&& dictionaryIndependent.size() != 0)
				l.setFundTypeName(dictionaryIndependent.get(0)
						.getItemValue());
			
		}*/
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		List list=bpFundintentService.bidFundListByOrderNo(bidPlanId, orderNo);
/*		//加息利息
		List<BpFundIntent> subjoinInterestList = bpFundintentService.getByPlanIdA(bidPlanId, null, orderNo, "'subjoinInterest'");
		//优惠券利息
		List<BpFundIntent> couponInterestList = bpFundintentService.getByPlanIdA(bidPlanId, null, orderNo, "'couponInterest'");
		
		StringBuffer sb1 = new StringBuffer("");
		//合并json串
		combine(sb1, subjoinInterestList, couponInterestList);*/
				  

				  
		StringBuffer buff = new StringBuffer("{success:true,result:[");
/*		//把优惠券利息 ，和加息利息加入json串中
		if(!"".equals(sb1.toString())){
			buff.append(sb1);
			if(list!=null&&list.size()>0){
				buff.append(",");
			}
		}
		*/
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			BigDecimal money=new BigDecimal(0);
			buff.append("{\"payintentPeriod\":");
			buff.append(obj[0]);
			buff.append(",\"principal\":'");
			if(null!=obj[1]){
				money=money.add(new BigDecimal(obj[1].toString()));
				buff.append(obj[1]);
			}
			
			buff.append("',\"interest\":'");
			if(null!=obj[2]){
				money=money.add(new BigDecimal(obj[2].toString()));
				buff.append(obj[2]);
			}
			
			buff.append("',\"reward\":'");
			if(null!=obj[6]){
				money=money.add(new BigDecimal(obj[6].toString()));
				buff.append(obj[6]);
			}
			
			buff.append("',\"interestPenaltyMoney\":'");
			if(null!=obj[7]){
				money=money.add(new BigDecimal(obj[7].toString()));
				buff.append(obj[7]);
			}
			
			buff.append("',\"accrualMoney\":'");
			if(null!=obj[8]){
				money=money.add(new BigDecimal(obj[8].toString()));
				buff.append(obj[8]);
			}
			
			buff.append("',\"intentDate\":'");
			if(null!=obj[3]){
				buff.append(sd.format(obj[3]));
			}
			
			buff.append("',\"sumMoney\":'");
			buff.append(money);
			
			buff.append("',\"factDate\":'");
			if(null!=obj[4]){
				buff.append(sd.format(obj[4]));
			}
			
			buff.append("',\"afterMoney\":'");
			if(null!=obj[5]){
				buff.append(obj[5]);
			}
			
			buff.append("'},");
		}
		if(null!=list && list.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		
		/*JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
				"factDate", "interestStarTime", "interestEndTime");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate", "factDate", "interestStarTime",
						"interestEndTime" });
		buff.append(json.serialize(list));*/
		buff.append("]}");
		jsonString = buff.toString();
	//	System.out.println(jsonString);
		return SUCCESS;
	}
	public String save() {
		String bpFundIntentJson = getRequest().getParameter("bpFundIntentJson");
		String businessType = getRequest().getParameter("businessType");
		String pId = getRequest().getParameter("projectId");
		if (pId == null || "".equals(pId)) {
			return SUCCESS;
		}
		Long projectId = Long.parseLong(pId);
		if (null != bpFundIntentJson && !"".equals(bpFundIntentJson)) {

			String[] shareequityArr = bpFundIntentJson.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));

				try {

					BpFundIntent BpFundIntent1 = (BpFundIntent) JSONMapper
							.toJava(parser.nextValue(), BpFundIntent.class);
					BpFundIntent1.setProjectId(projectId);
					if (businessType.equals("SmallLoan")) {
						SlSmallloanProject loan = slSmallloanProjectService
								.get(projectId);
						BpFundIntent1.setProjectName(loan.getProjectName());
						BpFundIntent1.setProjectNumber(loan.getProjectNumber());
					}
					if (businessType.equals("Financing")) {
						FlFinancingProject flFinancingProject = flFinancingProjectService
								.get(projectId);
						BpFundIntent1.setProjectName(flFinancingProject
								.getProjectName());
						BpFundIntent1.setProjectNumber(flFinancingProject
								.getProjectNumber());
					}
					if (null == BpFundIntent1.getFundIntentId()) {

						BigDecimal lin = new BigDecimal(0.00);
						if (BpFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							BpFundIntent1.setNotMoney(BpFundIntent1
									.getPayMoney());
						} else {
							BpFundIntent1.setNotMoney(BpFundIntent1
									.getIncomeMoney());

						}
						BpFundIntent1.setAfterMoney(new BigDecimal(0));
						BpFundIntent1.setAccrualMoney(new BigDecimal(0));
						BpFundIntent1.setFlatMoney(new BigDecimal(0));
						Short isvalid = 0;
						BpFundIntent1.setIsValid(isvalid);
						BpFundIntent1.setIsCheck(isvalid);
						BpFundIntent1.setBusinessType(businessType);
						// BpFundIntentService.save(BpFundIntent1);
						mySelfService.saveOrUpdate(BpFundIntent1);
					} else {
						BigDecimal lin = new BigDecimal(0.00);
						if (BpFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							BpFundIntent1.setNotMoney(BpFundIntent1
									.getPayMoney());
						} else {
							BpFundIntent1.setNotMoney(BpFundIntent1
									.getIncomeMoney());

						}
						BpFundIntent BpFundIntent2 = (BpFundIntent) mySelfService
								.get("BpFundIntent", "fundIntentId",
										BpFundIntent1.getFundIntentId());
						if (BpFundIntent2.getAfterMoney().compareTo(
								new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(BpFundIntent2,
									BpFundIntent1);
							BpFundIntent2.setBusinessType(businessType);
							// BpFundIntentService.updateIntent(BpFundIntent2);
							mySelfService.saveOrUpdate(BpFundIntent2);
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

	public long compare_date(Date dt1, Date dt2) {
		if (dt1.getTime() > dt2.getTime()) {
			return -1;
		} else if (dt1.getTime() < dt2.getTime()) {
			long msPerDay = 1000 * 60 * 60 * 24; // 一天的毫秒数
			// System.out.print(dt2.getTime() + "==" + dt1.getTime());
			long msSum = dt2.getTime() - dt1.getTime();
			long day = msSum / msPerDay;
			return day;
		} else {
			return 0;
		}
	}

	@Override
	public String delete() {
		/*
		 * fundIntentId : row.data.fundIntentId, projectId : projId,
		 * businessType : this.businessType
		 */
		String fundIntentId = getRequest().getParameter("fundIntentId");
		String projectId = getRequest().getParameter("projectId");
		String businessType = getRequest().getParameter("businessType");
		if (null == fundIntentId || "".equals(fundIntentId)
				|| "undefined".equals(fundIntentId)) {
			return SUCCESS;
		}
		BpFundIntent bpFundIntent = (BpFundIntent) mySelfService.get(
				"BpFundIntent", "fundIntentId", Long.parseLong(fundIntentId));
		mySelfService.delete(bpFundIntent);
		return SUCCESS;
	}
    /**
     * 还款操作
     * @return
     */
	public String repayment() { 
		StringBuffer buff = new StringBuffer("");
		try{
		bpFundIntent=bpFundintentService.get(bpFundIntentId);
		InvestPersonInfo investPersionInfo=investPersonInfoService.get(bpFundIntent.getInvestPersonId());
			if(bpFundIntent.getFundType().equals("principalLending")){
				
				buff.append("{success:false,'msg':'不能对本金放款台帐还款'");
			
			}else if(bpFundIntent.getNotMoney().compareTo(new BigDecimal(0))<=0){
				buff.append("{success:false,'msg':'还款金额为0 不允许再次还款'");
			}else{
				if(configMap.get("thirdPayConfig").toString().equals(Constants.YEEPAY)){
					BpCustMember borrowcustmem=plBidPlanService.getLoanMember(plBidPlanService.get(bpFundIntent.getBidPlanId()));
					BpCustMember investcustmem=bpCustMemberService.get(investPersionInfo.getInvestPersonId());
				}
				String ret="";
				//更新账户信息
					ret=updateMyAccount(investPersionInfo.getInvestPersonId(),investPersionInfo.getPersionType(),bpFundIntent.getIncomeMoney());
					//ret=obAccountDealInfoService.updateAcountInfo(investPersionInfo.getInvestPersonId(), ObAccountDealInfo.T_INVEST.toString(), bpFundIntent.getIncomeMoney().toString(), investPersionInfo.getPersionType().toString(), investPersionInfo.getOrderNo(), null, "2");
				if (ret.equals("success")) {
					//更新还款台帐
					bpFundIntent.setFactDate(new Date());
					bpFundIntent.setAfterMoney(bpFundIntent.getIncomeMoney());
					bpFundIntent.setNotMoney(bpFundIntent.getNotMoney().subtract(bpFundIntent.getIncomeMoney()));
					bpFundintentService.save(bpFundIntent);
				}else{
						buff.append("{success:false,'msg':'更新账户出错！'");
					}
				buff.append("{success:true,'msg':'还款成功！'");
				
			}
		}catch(Exception e){
			e.printStackTrace();
			buff.append("{success:false,'msg':'操作失败：更新账户出错"+e.getMessage()+"'");
		}
		buff.append("}");
		jsonString = buff.toString();
	   return SUCCESS;
	}
	/**
	 * 更新账户信息
	 * csInvestmentperson 投资人
	 * money 还款金额
	 */
	private String  updateMyAccount(Long investPersionId,Short investPsersionType,BigDecimal money){
		String ret="";
		ObSystemAccount account=new ObSystemAccount();
		account=obSystemAccountService.getByInvrstPersonIdAndType(investPersionId,investPsersionType);
		ret=obAccountDealInfoService.saveRechargeDealInfo(account.getId().toString(),money.toString(),"3","2","1",account.getInvestPersonName(),investPersionId.toString(),"true","1");
		return ret;
	}
	public String listOfPrepayment(){
		try{
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			List<BpFundIntent> list=new ArrayList<BpFundIntent>();
			String flag=this.getRequest().getParameter("flag");
			if(flag.equals("1")){
				list=bpFundIntentService.listbySlEarlyRepaymentId(bidPlanId, slEarlyRepaymentId);
			}else{
				List<InvestPersonInfo> li = new ArrayList<InvestPersonInfo>();
				if(null!=bidPlanId){
					li = investPersonInfoService.getByBidPlanId(bidPlanId);
				}else{
					li = investPersonInfoService.getByMoneyPlanId(Long.parseLong(preceptId));
				}
				PlBidPlan plan=plBidPlanService.get(bidPlanId);
				BpFundProject project=bpFundProjectService.get(Long.parseLong(preceptId));
				//SlEarlyRepaymentRecord slEarlyRepaymentRecord=slEarlyRepaymentRecordService.get(slEarlyRepaymentId);
				for(InvestPersonInfo p:li){
					List<BpFundIntent> flist=bpFundIntentService.listByEarlyDate(bidPlanId, p.getOrderNo(), sd.format(slEarlyRepaymentRecord.getEarlyDate()),"loanInterest");
					BpFundIntent f=null;
					if(null!=flist && flist.size()>0){
						f=flist.get(0);
					}
					
					Date interestEndTime=f.getInterestEndTime();//本期的截止日期
					if(AppUtil.getInterest().equals("0")){//算头不算尾
						interestEndTime=DateUtil.addDaysToDate(f.getInterestEndTime(), 1);
					}
					BpFundIntent bf=null;
					if(project.getAccrualtype().equals("sameprincipalandInterest")){
						List<BpFundIntent> principalist=bpFundIntentService.listByEarlyDate(bidPlanId, p.getOrderNo(), sd.format(slEarlyRepaymentRecord.getEarlyDate()),"principalRepayment");
						if(null!=principalist && principalist.size()>0){
							bf=principalist.get(0);
						}
					}
					BigDecimal principalMoney= bpFundIntentService.getPrincipalMoney(bidPlanId,sd.format(slEarlyRepaymentRecord.getEarlyDate()),p.getOrderNo());
					PrepaymentFundIntentCreate prepaymentFundIntentCreate=new PrepaymentFundIntentCreate(project,p,slEarlyRepaymentRecord,plan,f.getPayintentPeriod(),interestEndTime,f.getInterestStarTime(),principalMoney,bf);
					prepaymentFundIntentCreate.create(list);
				}
			}
			for(BpFundIntent f:list){
				if(null!=f.getFundType()){
					f.setFundTypeName(dictionaryIndependentService.getByDicKey(f.getFundType()).get(0).getItemValue());
				}
			}
			StringBuffer buff = new StringBuffer("{success:true,result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
					"factDate", "interestStarTime", "interestEndTime");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"intentDate", "factDate", "interestStarTime","interestEndTime" });
			buff.append(json.serialize(list));
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String listloanCommon(){
	
		String flag1 = this.getRequest().getParameter("flag1");
		//String projectId = this.getRequest().getParameter("projectId");
		List<FundIntent> list = new ArrayList<FundIntent>();
		List<FundIntent> list1 = new ArrayList<FundIntent>();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		PlBidPlan plan=plBidPlanService.get(bidPlanId);
		BpFundProject project=bpFundProjectService.get(Long.parseLong(preceptId));
		if("0".equals(flag1)){
			if(this.slEarlyRepaymentId!=null){
				List<InvestPersonInfo> li = new ArrayList<InvestPersonInfo>();
				if(null!=bidPlanId){
					li = investPersonInfoService.getByBidPlanId(bidPlanId);
				}else{
					li = investPersonInfoService.getByMoneyPlanId(Long.parseLong(preceptId));
				}
				
		
				if(li!=null&&li.size()!=0){
					int i=1;
					for(InvestPersonInfo person:li){
						List<BpFundIntent> filist = new ArrayList<BpFundIntent>();
						/*List<BpFundIntent> listr=	bpFundintentService.listOfInverstPerson(person.getOrderNo(), "");
						 for(BpFundIntent a:listr){
		                	   bpFundintentService.remove(a);
							}*/
						 List<BpFundIntent> flist=bpFundIntentService.listByEarlyDate(bidPlanId, person.getOrderNo(), sd.format(slEarlyRepaymentRecord.getEarlyDate()),"loanInterest");
							BpFundIntent f=null;
							if(null!=flist && flist.size()>0){
								f=flist.get(0);
							}
							
							Date interestEndTime=f.getInterestEndTime();//本期的截止日期
							if(AppUtil.getInterest().equals("0")){//算头不算尾
								interestEndTime=DateUtil.addDaysToDate(f.getInterestEndTime(), 1);
							}
							BpFundIntent bf=null;
							if(project.getAccrualtype().equals("sameprincipalandInterest")){
								List<BpFundIntent> principalist=bpFundIntentService.listByEarlyDate(bidPlanId, person.getOrderNo(), sd.format(slEarlyRepaymentRecord.getEarlyDate()),"principalRepayment");
								if(null!=principalist && principalist.size()>0){
									bf=principalist.get(0);
								}
							}
							BigDecimal principalMoney= bpFundIntentService.getPrincipalMoney(bidPlanId,sd.format(slEarlyRepaymentRecord.getEarlyDate()),person.getOrderNo());
							PrepaymentFundIntentCreate prepaymentFundIntentCreate=new PrepaymentFundIntentCreate(project,person,slEarlyRepaymentRecord,plan,f.getPayintentPeriod(),interestEndTime,f.getInterestStarTime(),principalMoney,bf);
							prepaymentFundIntentCreate.create(filist);
						for(BpFundIntent bfi:filist){
							bfi.setPreceptId(project.getId());
							bfi.setBidPlanId(bidPlanId);
							bfi.setPreceptId(Long.valueOf(preceptId));
							bfi.setInvestPersonId(person.getInvestId());
							bfi.setProjectId(project.getProjectId());
							bfi.setProjectName(plan.getBidProName());
							bfi.setProjectNumber(project.getProjectNumber());
							bfi.setFundTypeName(dictionaryIndependentService.getByDicKey(bfi.getFundType()).get(0).getItemValue());
							bfi.setBusinessType("SmallLoan");
							bfi.setIsCheck(Short.valueOf("1"));
							bfi.setIsValid(Short.valueOf("0"));
							bfi.setOrderNo(person.getOrderNo());
							//bpFundintentService.save(bfi);
							bpFundintentService.evict(bfi);
						   }
					   if(i==1){
						   
						   list1.addAll(filist);
						   
					   }else{
						   for(int j=0;j<list1.size();j++){
							  
							   if(list1.get(j).getIncomeMoney().compareTo(new BigDecimal("0"))==1){
								   list1.get(j).setIncomeMoney( list1.get(j).getIncomeMoney().add(filist.get(j).getIncomeMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
								   list1.get(j).setNotMoney( list1.get(j).getIncomeMoney());
							   }else{
								   list1.get(j).setPayMoney( list1.get(j).getPayMoney().add(filist.get(j).getPayMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
								   list1.get(j).setNotMoney( list1.get(j).getPayMoney());
							   }
							  
						   }
						   
					   }
						
						
						i++;
						
						
				
					}
				}
				
				StringBuffer buff = new StringBuffer("{success:true,result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
					"factDate", "interestStarTime", "interestEndTime");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"intentDate", "factDate", "interestStarTime",
					"interestEndTime" });
			buff.append(json.serialize(list1));
			buff.append("}");
			jsonString = buff.toString();
			}
			
		}else{
				List<InvestPersonInfo> li = new ArrayList<InvestPersonInfo>();
				if(null!=bidPlanId){
					li = investPersonInfoService.getByBidPlanId(bidPlanId);
				}else{
					li = investPersonInfoService.getByMoneyPlanId(Long.parseLong(preceptId));
				}
				
			
				if(li!=null&&li.size()!=0){
				
					int i=1;
					for(InvestPersonInfo person:li){
						List<BpFundIntent> filist = new ArrayList<BpFundIntent>();
						
						if(null!=slEarlyRepaymentId){
							filist=bpFundintentService.listByOrderNoAndEarlyId(person.getOrderNo(), "",slEarlyRepaymentId);
						}else{
							filist=bpFundintentService.listOfInverstPerson(person.getOrderNo(), "");
						}
					   if(i==1){
						   
						   list1.addAll(filist);
						   
					   }else{
						   for(int j=0;j<list1.size();j++){
							   fundIntentService.evict(list1.get(j));
							   if(list1.get(j).getIncomeMoney().compareTo(new BigDecimal("0"))==1){
								   list1.get(j).setIncomeMoney( list1.get(j).getIncomeMoney().add(filist.get(j).getIncomeMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
								   list1.get(j).setNotMoney( list1.get(j).getIncomeMoney());
							   }else{
								   list1.get(j).setPayMoney( list1.get(j).getPayMoney().add(filist.get(j).getPayMoney()).setScale(2,BigDecimal.ROUND_HALF_UP));
								   list1.get(j).setNotMoney( list1.get(j).getPayMoney());
							   }
							   list1.get(j).setFundTypeName(dictionaryIndependentService.getByDicKey(list1.get(j).getFundType()).get(0).getItemValue());
						   }
						   
					   }
						
						
						i++;
						
						
				
					}
				}
				StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(list1.size()).append(",result:");
			JSONSerializer json = JsonUtil.getJSONSerializer("intentDate",
					"factDate", "interestStarTime", "interestEndTime");
			json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {
					"intentDate", "factDate", "interestStarTime",
					"interestEndTime" });
			buff.append(json.serialize(list1));
			buff.append("}");
			jsonString = buff.toString();
				
			}
		return SUCCESS;
		
	}
	public String listPeriodbyOneLedger(){
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
		List<BpFundIntentPeriod> list=bpFundintentService.listByBidPlanIdAndpayintentPeriodAndorerNo(pb, map);
		List<BpFundIntentPeriod> sizelist=bpFundintentService.listByBidPlanIdAndpayintentPeriodAndorerNo(null, map);

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
	 * 优惠券收益查询
	 * @return
	 */
	public String getCouponsIncome(){
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String fundType = this.getRequest().getParameter("fundType");
		String planId = this.getRequest().getParameter("planId");
		String payintentPeriod = this.getRequest().getParameter("payintentPeriod");
		PagingBean pb = new PagingBean(start, limit);
		Long size = Long.valueOf("0");
		List<SlFundIntentPeriod> list=bpFundintentService.getCouponsIncome(pb);
		size=Long.valueOf(list.size());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer();
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		System.currentTimeMillis();
		return SUCCESS;
	}
	/**
	 * 优惠券奖励明细台账
	 * @return
	 */
	public String getCouponsFundInentDetail(){
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String fundType = this.getRequest().getParameter("fundType");
		String planId = this.getRequest().getParameter("planId");
		String payintentPeriod = this.getRequest().getParameter("payintentPeriod");
		PagingBean pb = new PagingBean(start, limit);
		Long size = Long.valueOf("0");
		List<BpFundIntent> list=bpFundintentService.getByPlanIdAndPeriod(Long.valueOf(planId),Integer.valueOf(payintentPeriod), fundType);
		size=Long.valueOf(list.size());
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
	 * 募集期奖励明细台账
	 * @return
	 */
	public String getRaiseRateFundInentDetail(){
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd");
		String fundType = this.getRequest().getParameter("fundType");
		String planId = this.getRequest().getParameter("planId");
		PagingBean pb = new PagingBean(start, limit);
		Long size = Long.valueOf("0");
		List<BpFundIntentPeriod> list=bpFundintentService.getRaiseBpfundIntent(Long.valueOf(planId));
		size=Long.valueOf(list.size());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(size).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate","bidtime","startTime");
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "intentDate" });
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "factDate" });
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "bidtime" });
		json.transform(new DateTransformer("yyyy-MM-dd"),
				new String[] { "startTime" });
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		System.currentTimeMillis();
		return SUCCESS;
	}
	/**
	 * 优惠券派息
	 * @return
	 */
	public String distributeMoney(){
		String planId = this.getRequest().getParameter("planId");
		String fundType = this.getRequest().getParameter("fundType");
		String payintentPeriod = this.getRequest().getParameter("payintentPeriod");
		String transferType="";
		boolean coupons=false;
		try {
		List<BpFundIntent> list = bpFundIntentService.getdistributeMoney(Long.valueOf(planId), Long.valueOf(payintentPeriod), fundType,null);
		if(list.size()>0){
			for(BpFundIntent bpfund:list){
				if(bpfund.getFactDate()==null||bpfund.getFactDate().equals("")){
					BpCustMember mem=null;
					PlBidInfo bidInfo1 = plBidInfoService.getByOrderNo(bpfund.getOrderNo());
					//判断是否发生了债权交易,奖励发给新的债权人
					if(bidInfo1.getNewInvestPersonId()!=null&&!bidInfo1.getNewInvestPersonId().equals("")){
						mem=bpCustMemberService.get(bidInfo1.getNewInvestPersonId());
					}else{
						mem=bpCustMemberService.get(bpfund.getInvestPersonId());
					}
					String requestNo=Common.getRandomNum(3)+ DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS");
					CommonRequst commonRequest = new CommonRequst();
					commonRequest.setThirdPayConfigId(mem.getThirdPayFlagId());//用户第三方标识
					commonRequest.setRequsetNo(requestNo);//请求流水号
					commonRequest.setAmount(bpfund.getNotMoney());//交易金额
					if(mem.getCustomerType()!=null&&mem.getCustomerType().equals(BpCustMember.CUSTOMER_ENTERPRISE)){//判断是企业
						commonRequest.setAccountType(1);
					}else{//借款人是个人
						commonRequest.setAccountType(0);
					}
					commonRequest.setCustMemberType("0");
					commonRequest.setBidId(bpfund.getFundIntentId().toString());
					commonRequest.setBussinessType(ThirdPayConstants.BT_COUPONAWARD);//业务类型
					commonRequest.setTransferName(ThirdPayConstants.TN_COUPONAWARD);//业务名称
					CommonResponse commonResponse=ThirdPayInterfaceUtil.thirdCommon(commonRequest);
					if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
						if(bpfund.getFundType().equals("raiseinterest")){
							transferType=ObAccountDealInfo.T_BIDRETURN_RATE31;
						}else if(bpfund.getFundType().equals("commoninterest")){
							transferType=ObAccountDealInfo.T_BIDRETURN_ADDRATE;
						}else if(bpfund.getFundType().equals("couponInterest")){
							transferType=ObAccountDealInfo.T_BIDRETURN_RATE29;
						}else if(bpfund.getFundType().equals("principalCoupons")){
							transferType=ObAccountDealInfo.T_BIDRETURN_RATE28;
						}
						//添加资金明细
						Map<String,Object> map3=new HashMap<String,Object>();
						map3.put("investPersonId",mem.getId());//投资人Id（必填）
						map3.put("investPersonType",ObSystemAccount.type0);//投资人类型：0线上投资人，1线下投资人(参见ObSystemAccount中的常量) （必填）					 
						map3.put("transferType",transferType);//交易类型:1表示充值，2表示取现,3收益，4投资，5还本(参见ObAccountDealInfo中的常量) （必填）
						map3.put("money",bpfund.getNotMoney());//交易金额	（必填）			 
						map3.put("dealDirection",ObAccountDealInfo.DIRECTION_INCOME);//交易方向:1收入，2支出（参见ObAccountDealInfo中的常量）（必填）
						map3.put("dealType",ObAccountDealInfo.THIRDPAYDEAL);//交易方式：1表示现金交易，2表示银行卡交易，3表示第三方支付交易（参见ObAccountDealInfo中的常量）（必填）
						map3.put("recordNumber",requestNo);//交易流水号	（必填）
						map3.put("dealStatus",ObAccountDealInfo.DEAL_STATUS_2);//资金交易状态：1等待支付，2支付成功，3 支付失败。。。(参见ObAccountDealInfo中的常量)	（必填）
						obAccountDealInfoService.operateAcountInfo(map3);
						
						//更新款项
						bpfund.setNotMoney(new BigDecimal(0));
						bpfund.setAfterMoney(bpfund.getIncomeMoney());
						bpfund.setFactDate(new Date());
						bpFundIntentService.save(bpfund);
					}
				}
			}
			setJsonString("{success:true,msg:'派息成功'}");
		}else{
			setJsonString("{success:true,msg:'该项目未找到尚未派息的记录,奖励已经派发，不能重复派发'}");
		}
		} catch (Exception e) {
			e.printStackTrace();
			setJsonString("{success:true,msg:'派息失败'}");
		}
		return SUCCESS;
	}
	public BpFundProject getOwnBpFundProject() {
		return ownBpFundProject;
	}

	public void setOwnBpFundProject(BpFundProject ownBpFundProject) {
		this.ownBpFundProject = ownBpFundProject;
	}

	public BpFundProject getPlatFormBpFundProject() {
		return platFormBpFundProject;
	}

	public void setPlatFormBpFundProject(BpFundProject platFormBpFundProject) {
		this.platFormBpFundProject = platFormBpFundProject;
	}

	public String getPreceptId() {
		return preceptId;
	}

	public void setPreceptId(String preceptId) {
		this.preceptId = preceptId;
	}

	public Long getBidPlanId() {
		return bidPlanId;
	}

	public void setBidPlanId(Long bidPlanId) {
		this.bidPlanId = bidPlanId;
	}

}
