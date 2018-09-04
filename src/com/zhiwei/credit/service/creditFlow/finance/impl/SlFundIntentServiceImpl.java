package com.zhiwei.credit.service.creditFlow.finance.impl;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.core.project.FundIntentListPro3;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObSystemAccountDao;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundDetailDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlPunishDetailDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlPunishInterestDao;
import com.zhiwei.credit.dao.creditFlow.financeProject.FlFinancingProjectDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.BpFundProjectDao;
import com.zhiwei.credit.dao.creditFlow.guarantee.project.GLGuaranteeloanProjectDao;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.project.FlLeaseFinanceProjectDao;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PlPawnProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.dao.system.CsHolidayDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundDetail;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlPunishDetail;
import com.zhiwei.credit.model.creditFlow.finance.SlPunishInterest;
import com.zhiwei.credit.model.creditFlow.finance.fundintentmerge.SlFundIntentPeriod;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.log.BatchRunRecord;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.CsHoliday;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.log.BatchRunRecordService;
import com.zhiwei.credit.service.flow.JbpmService;

/**
 * 
 * @author
 * 
 */
public class SlFundIntentServiceImpl extends BaseServiceImpl<SlFundIntent>
		implements SlFundIntentService {
	private SlFundIntentDao dao;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private SlSmallloanProjectDao slSmallloanProjectDao;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private FlFinancingProjectDao flFinancingProjectDao;
	@Resource
	private GLGuaranteeloanProjectDao gLGuaranteeloanProjectDao;
	@Resource
	private MortgageService mortgageService;
	@Resource
	private SlPunishInterestDao slPunishInterestDao;
	@Resource
	private FlLeaseFinanceProjectDao flLeaseFinanceProjectDao;
	@Resource
	private PlPawnProjectDao plPawnProjectDao;
	@Resource
	private ObSystemAccountDao obSystemAccountDao;
	@Resource
	private ProDefinitionDao proDefinitionDao;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private BpFundIntentDao bpFundIntentDao;
	@Resource
	private SlFundDetailDao slFundDetailDao;
	@Resource
	private BpFundProjectDao bpFundProjectDao;
	@Resource
	private SlPunishDetailDao slPunishDetailDao;
	@Resource
	private BatchRunRecordService batchRunRecordService;
	@Resource
	private CsHolidayDao csHolidayDao;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// 得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();

	public SlFundIntentServiceImpl(SlFundIntentDao dao) {
		super(dao);
		this.dao = dao;
	}

	@Override
	public int updateOverdue(SlFundIntent s) {

		return dao.updateOverdue(s);
	}

	@Override
	public int updateFlatMoney(SlFundIntent s) {
		// TODO Auto-generated method stub
		return dao.updateFlatMoney(s);
	}

	@Override
	public int updateIntent(SlFundIntent s) {
		// TODO Auto-generated method stub
		return dao.updateIntent(s);
	}

	@Override
	public List<SlFundIntent> getByProjectId(Long projectId, String businessType) {
		return dao.getByProjectId(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> getByProjectIdAsc(Long projectId,
			String businessType) {
		return dao.getByProjectIdAsc(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> getByProjectId(Long projectId,
			String businessType, String fundResource) {
		return dao.getByProjectId(projectId, businessType, fundResource);
	}

	// 小额贷保存制定款项计划节点的款项计划
	public Integer saveAfterFlow(FlowRunInfo flowRunInfo) {
		if (flowRunInfo.isBack()) {
			return 1;
		} else {
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String slActualToChargeData = flowRunInfo.getRequest()
					.getParameter("slActualToChargeData");
			Long projectId = Long.parseLong(flowRunInfo.getRequest()
					.getParameter("projectId"));
			String businessType = flowRunInfo.getRequest().getParameter(
					"businessType");
			Short flag = Short.parseShort(flowRunInfo.getRequest()
					.getParameter("flag"));
			slActualToChargeService.savejson(slActualToChargeData, projectId,
					"SmallLoan", flag, null); // 有问题
			if (null != slFundIentJson && !"".equals(slFundIentJson)) {

				String[] shareequityArr = slFundIentJson.split("@");

				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));

					try {

						SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper
								.toJava(parser.nextValue(), SlFundIntent.class);
						SlFundIntent1.setProjectId(projectId);
						SlSmallloanProject loan = slSmallloanProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(loan.getProjectName());
						SlFundIntent1.setProjectNumber(loan.getProjectNumber());

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
							SlFundIntent1.setBusinessType("SmallLoan");
							// if(slSmallloanProject !=null){
							// slSmallloanProject.setAccrualMoney(accrualMoney);
							// slSmallloanProjectDao.save(slSmallloanProject);
							// }
							dao.save(SlFundIntent1);
						} else {
							// if
							// (SlFundIntent1.getFundType().equals("principalLending"))
							// {
							// SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
							// SlFundIntent1.setIncomeMoney(new BigDecimal(0));
							//							
							// } else {
							// SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
							// SlFundIntent1.setPayMoney(new BigDecimal(0));
							// }
							BigDecimal lin = new BigDecimal(0.00);
							if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
								SlFundIntent1.setNotMoney(SlFundIntent1
										.getPayMoney());
							} else {
								SlFundIntent1.setNotMoney(SlFundIntent1
										.getIncomeMoney());

							}
							SlFundIntent slFundIntent2 = dao.get(SlFundIntent1
									.getFundIntentId());
							if (slFundIntent2.getAfterMoney().compareTo(
									new BigDecimal(0)) == 0) {
								BeanUtil.copyNotNullProperties(slFundIntent2,
										SlFundIntent1);
								slFundIntent2.setBusinessType("SmallLoan");
								dao.updateIntent(slFundIntent2);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
						logger.error(" 保存款项出错:" + e.getMessage());
						return -1;
					}

				}
			}
			SlSmallloanProject persistent = slSmallloanProjectDao
					.get(projectId);
			Map<String, BigDecimal> map = saveProjectfiance(projectId,
					"SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));
			slSmallloanProjectDao.save(persistent);
			return 1;
		}
	}

	public List<SlFundIntent> getByProjectIdAndFundType(Long projectId,
			Integer fundType) {
		return dao.getByProjectIdAndFundType(projectId, fundType);
	}

	@Override
	public int searchsize(Map<String, String> map, String businessType) {
		// TODO Auto-generated method stub
		return dao.searchsize(map, businessType);
	}

	@Override
	public List<SlFundIntent> search(Map<String, String> map,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.search(map, businessType);
	}

	// 保存尽职调查款项计划
	@Override
	public String savejson(String slFundIentJson, Long projectId,
			String businessType, Short flag, Long companyId, Long preceptId,
			String fundResource) {
		// 根据方案id查
		if (null != preceptId && !"".equals(preceptId)) {
			if (dao.getbyPreceptId(preceptId).size() != 0) {
				List<SlFundIntent> listall = new ArrayList<SlFundIntent>();
				listall = dao.getByProjectId(projectId, businessType);
				for (SlFundIntent l : listall) {
					slFundIntentDao.evict(l);
					if (l.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						dao.remove(l);
					}
				}
			}
		} else {
			if (dao.getByProjectId(projectId, businessType).size() != 0) {
				List<SlFundIntent> listall = new ArrayList<SlFundIntent>();
				listall = dao.getByProjectId(projectId, businessType);
				for (SlFundIntent l : listall) {
					slFundIntentDao.evict(l);
					if (l.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						dao.remove(l);
					}
				}
			}
		}
		int sumintent = 0; // 计算有多少条本金偿还的款项
		if (null != slFundIentJson && !"".equals(slFundIentJson)) {
			String[] shareequityArr = slFundIentJson.split("@");
			for (int k = 0; k < shareequityArr.length; k++) {
				String str = shareequityArr[k];
				JSONParser parser = new JSONParser(new StringReader(str));
				SlFundIntent SlFundIntent1;
				try {
					SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser
							.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(projectId);
					if (businessType.equals("SmallLoan")) {
						SlSmallloanProject loan = slSmallloanProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(loan.getProjectName());
						SlFundIntent1.setProjectNumber(loan.getProjectNumber());
					}
					if (businessType.equals("Financing")) {
						FlFinancingProject flFinancingProject = flFinancingProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(flFinancingProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(flFinancingProject
								.getProjectNumber());
					}
					if (businessType.equals("Guarantee")) {
						GLGuaranteeloanProject gLGuaranteeloanProject = gLGuaranteeloanProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(gLGuaranteeloanProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(gLGuaranteeloanProject
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
						SlFundIntent1.setCompanyId(companyId);
						SlFundIntent1.setIsCheck(flag);
						// add by zcb 2014-03-20
						if ("SmallLoan".equals(fundResource)) {
							SlFundIntent1.setFundResource("0");// 自有 小贷
						} else if ("Pawn".equals(fundResource)) {
							SlFundIntent1.setFundResource("1");// 自有 典当
						} else {
							SlFundIntent1.setFundResource("2");// 平台
						}
						SlFundIntent1.setPreceptId(preceptId);// 保存方案ID
						slFundIntentDao.save(SlFundIntent1);
						if (SlFundIntent1.getFundType().equals(
								"principalRepayment")) {
							sumintent++;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(" 保存款项出错:" + e.getMessage());
				}
			}
		}
		return String.valueOf(sumintent);
	}

	@Override
	public List<SlFundIntent> getByProjectId1(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.getByProjectId1(projectId, businessType);
	}

	@Override
	public String savejson1(String slFundIentJson, Long projectId,
			String businessType, Short flag, Short isValid, Long companyId) {
		if (null != slFundIentJson && !"".equals(slFundIentJson)) {

			String[] shareequityArr = slFundIentJson.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));

				try {

					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper
							.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(projectId);
					if (businessType.equals("SmallLoan")) {
						SlSmallloanProject loan = slSmallloanProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(loan.getProjectName());
						SlFundIntent1.setProjectNumber(loan.getProjectNumber());

					}
					if (businessType.equals("Financing")) {
						FlFinancingProject flFinancingProject = flFinancingProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(flFinancingProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(flFinancingProject
								.getProjectNumber());
					}
					if (businessType.equals("Guarantee")) {
						GLGuaranteeloanProject gLGuaranteeloanProject = gLGuaranteeloanProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(gLGuaranteeloanProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(gLGuaranteeloanProject
								.getProjectNumber());
					}
					if (null == SlFundIntent1.getFundIntentId()) {

						BigDecimal lin = new BigDecimal(0.00);

						// if
						// (SlFundIntent1.getFundType().equals("principalLending"))
						// {
						// SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						// SlFundIntent1.setIncomeMoney(new BigDecimal(0));
						//						
						// } else {
						// SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						// SlFundIntent1.setPayMoney(new BigDecimal(0));
						// }
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
						SlFundIntent1.setIsValid(isValid);
						SlFundIntent1.setBusinessType(businessType);
						SlFundIntent1.setCompanyId(companyId);
						SlFundIntent1.setIsCheck(flag);
						// if(slSmallloanProject !=null){
						// slSmallloanProject.setAccrualMoney(accrualMoney);
						// slSmallloanProjectDao.save(slSmallloanProject);
						// }
						dao.save(SlFundIntent1);
					} else {
						// if
						// (SlFundIntent1.getFundType().equals("principalLending"))
						// {
						// SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
						// SlFundIntent1.setIncomeMoney(new BigDecimal(0));
						//						
						// } else {
						// SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
						// SlFundIntent1.setPayMoney(new BigDecimal(0));
						// }
						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getIncomeMoney());

						}

						SlFundIntent slFundIntent2 = dao.get(SlFundIntent1
								.getFundIntentId());
						if (slFundIntent2.getAfterMoney().compareTo(
								new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slFundIntent2,
									SlFundIntent1);
							slFundIntent2.setIsCheck(flag);
							dao.save(slFundIntent2);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					logger.error(" 保存款项出错:" + e.getMessage());
					return "1";
				}

			}
		}
		return "1";
	}

	@Override
	public Integer saveAfterFlowcontract(FlowRunInfo flowRunInfo) { // 合同制作的下一步
		if (flowRunInfo.isBack()) {
			return 1;
		} else {
			String businessType = flowRunInfo.getRequest().getParameter(
					"businessType_flow");
			Long projectId = Long.parseLong(flowRunInfo.getRequest()
					.getParameter("projectId_flow"));
			List<SlFundIntent> list1 = dao.getByProjectId1(projectId,
					businessType);
			List<SlActualToCharge> list2 = slActualToChargeService
					.listbyproject(projectId, businessType);
			Short a = 0;
			for (SlFundIntent l : list1) {
				l.setIsCheck(a);
				dao.save(l);
			}
			for (SlActualToCharge l : list2) {
				l.setIsCheck(a);
				slActualToChargeService.save(l);
			}
			// 抵质押物状态
			List pmlist = mortgageService.getByBusinessTypeProjectId(
					businessType, projectId);
			if (pmlist != null && pmlist.size() != 0) {
				for (int k = 0; k < pmlist.size(); k++) {
					ProcreditMortgage procreditMortgage = (ProcreditMortgage) pmlist
							.get(k);
					procreditMortgage.setMortgageStatus("htqswbl");
					try {
						mortgageService.updateMortgage(procreditMortgage
								.getId(), procreditMortgage);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return 1;
	}

	@Override
	public Map<String, BigDecimal> saveProjectfiance(Long projectId,
			String businessType) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		List<SlFundIntent> listintent = slFundIntentDao.getByProjectId1(
				projectId, businessType);
		List<SlFundIntent> isvalidandchecklist = slFundIntentDao
				.getByisvalidAndaftercheck(projectId, businessType);
		BigDecimal loanInterest = new BigDecimal(0);
		BigDecimal consultationMoney = new BigDecimal(0);
		BigDecimal serviceMoney = new BigDecimal(0);
		for (SlFundIntent l : listintent) {
			slFundIntentDao.evict(l);
			if (l.getFundType().equals("loanInterest")) {
				loanInterest = loanInterest.add(l.getIncomeMoney());
			}
			if (l.getFundType().equals("consultationMoney")) {
				consultationMoney = consultationMoney.add(l.getIncomeMoney());
			}
			if (l.getFundType().equals("serviceMoney")) {
				serviceMoney = serviceMoney.add(l.getIncomeMoney());
			}
		}
		for (SlFundIntent c : isvalidandchecklist) {
			slFundIntentDao.evict(c);
			if (c.getFundType().equals("loanInterest")) {
				loanInterest = loanInterest.add(c.getAfterMoney());
			}
			if (c.getFundType().equals("consultationMoney")) {
				consultationMoney = consultationMoney.add(c.getAfterMoney());
			}
			if (c.getFundType().equals("serviceMoney")) {
				serviceMoney = serviceMoney.add(c.getAfterMoney());
			}
		}
		List<SlActualToCharge> listcharge = slActualToChargeService
				.listbyproject(projectId, businessType);
		BigDecimal incomechargeMoney = new BigDecimal(0);
		BigDecimal paychargeMoney = new BigDecimal(0);
		BigDecimal lin = new BigDecimal(0);
		for (SlActualToCharge l : listcharge) {
			if (l.getIncomeMoney().compareTo(lin) == 0) {
				if (null == l.getPayMoney()) {
					// paychargeMoney=paychargeMoney.add(l.getPayMoney());
				} else {
					paychargeMoney = paychargeMoney.add(l.getPayMoney());
				}

			} else {
				incomechargeMoney = incomechargeMoney.add(l.getIncomeMoney());
			}
		}
		map.put("paychargeMoney", paychargeMoney);
		map.put("incomechargeMoney", incomechargeMoney);
		map.put("loanInterest", loanInterest);
		map.put("consultationMoney", consultationMoney);
		map.put("serviceMoney", serviceMoney);
		return map;

	}

	@Override
	public Map<String, BigDecimal> saveFinancingProjectfiance(Long projectId,
			String businessType) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		BigDecimal loanInterest = new BigDecimal(0);
		BigDecimal consultationMoney = new BigDecimal(0);
		BigDecimal serviceMoney = new BigDecimal(0);
		List<SlFundIntent> listintent = slFundIntentDao.getByProjectId1(
				projectId, businessType);
		for (SlFundIntent l : listintent) {
			if (l.getFundType().equals("FinancingInterest")) {
				loanInterest = loanInterest.add(l.getPayMoney());
			}
			if (l.getFundType().equals("financingconsultationMoney")) {
				consultationMoney = consultationMoney.add(l.getPayMoney());
			}
			if (l.getFundType().equals("financingserviceMoney")) {
				serviceMoney = serviceMoney.add(l.getPayMoney());
			}
		}

		BigDecimal incomechargeMoney = new BigDecimal(0);
		BigDecimal paychargeMoney = new BigDecimal(0);
		BigDecimal lin = new BigDecimal(0);
		List<SlActualToCharge> listcharge = slActualToChargeService
				.listbyproject(projectId, businessType);
		for (SlActualToCharge l : listcharge) {
			if (l.getIncomeMoney().compareTo(lin) == 0) {
				paychargeMoney = paychargeMoney.add(l.getPayMoney());
			} else {
				incomechargeMoney = incomechargeMoney.add(l.getIncomeMoney());
			}
		}
		map.put("paychargeMoney", paychargeMoney);
		map.put("incomechargeMoney", incomechargeMoney);
		map.put("loanInterest", loanInterest);
		map.put("consultationMoney", consultationMoney);
		map.put("serviceMoney", serviceMoney);
		return map;
	}

	@Override
	public Map<String, BigDecimal> saveGuaranteeProjectfiance(Long projectId,
			String businessType) {
		Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
		List<SlActualToCharge> listcharge = slActualToChargeService
				.listbyproject(projectId, businessType);
		BigDecimal incomechargeMoney = new BigDecimal(0);
		BigDecimal paychargeMoney = new BigDecimal(0);
		BigDecimal lin = new BigDecimal(0);
		for (SlActualToCharge l : listcharge) {
			if (l.getIncomeMoney().compareTo(lin) == 0) {
				paychargeMoney = paychargeMoney.add(l.getPayMoney());
			} else {
				incomechargeMoney = incomechargeMoney.add(l.getIncomeMoney());
			}
		}
		map.put("paychargeMoney", paychargeMoney);
		map.put("incomechargeMoney", incomechargeMoney);
		return map;
	}

	@Override
	public List<SlFundIntent> getlistbyslSuperviseRecordId(
			Long slSuperviseRecordId, String businessType, Long projectId) {
		return dao.getlistbyslSuperviseRecordId(slSuperviseRecordId,
				businessType, projectId);
	}

	@Override
	public List<SlFundIntent> getByProjectId2(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.getByProjectId2(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> getByisvalidAndaftercheck(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.getByisvalidAndaftercheck(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> getByaftercheck(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.getByaftercheck(projectId, businessType);
	}

	@Override
	public int searchsizeurge(String projectIdStr, Map<String, String> map,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.searchsizeurge(projectIdStr, map, businessType);
	}

	@Override
	public void searchurge(PageBean<SlFundIntent> pageBean,Map<String, String> map, String businessType) {
		dao.searchurge(pageBean, map, businessType);
	}

	@Override
	public String intentToeffective(Long projectId, String businessType) {
		List<SlFundIntent> list1 = dao.getByProjectId1(projectId, businessType);
		List<SlActualToCharge> list2 = slActualToChargeService.listbyproject(
				projectId, businessType);
		Short a = 0;
		for (SlFundIntent l : list1) {
			l.setIsCheck(a);
			dao.save(l);
		}
		for (SlActualToCharge l : list2) {
			l.setIsCheck(a);
			slActualToChargeService.save(l);
		}
		return "1";
	}

	@Override
	public List<SlFundIntent> getlistbyslEarlyRepaymentId(
			Long slEarlyRepaymentId, String businessType, Long projectId) {
		// TODO Auto-generated method stub
		return dao.getlistbyslEarlyRepaymentId(slEarlyRepaymentId,
				businessType, projectId);
	}

	@Override
	public List<SlFundIntent> getlistbyslslAlteraccrualRecordId(
			Long slAlteraccrualRecordId, String businessType, Long projectId) {
		// TODO Auto-generated method stub
		return dao.getlistbyslslAlteraccrualRecordId(slAlteraccrualRecordId,
				businessType, projectId);
	}

	@Override
	public void listbyLedger(PageBean<SlFundIntent> pageBean,String businessType,String fundType, String typetab, PagingBean pb,Map<String, String> map) {
		dao.listbyLedger(pageBean,businessType, fundType, typetab, pb, map);
	}

	public List<SlFundIntent> changToFundPay(List<BpFundIntent> list) {
		List<SlFundIntent> FundPaylist = new ArrayList<SlFundIntent>();
		BigDecimal zero = new BigDecimal(0);
		int i = 0;
		while (i < list.size()) {

			BpFundIntent fp = list.get(i);
			SlFundIntent fpp = new SlFundIntent();
			fpp.setAfterMoney(fp.getAfterMoney());
			fpp.setIntentDate(fp.getIntentDate());
			fpp.setNotMoney(fp.getNotMoney());
			fpp.setPayintentPeriod(fp.getPayintentPeriod());
			fpp.setBidPlanId(fp.getBidPlanId());
			fpp.setFundType(fp.getFundType());
			fpp.setProjectId(fp.getProjectId());
			fpp.setFactDate(fp.getFactDate());
			fpp.setFlatMoney(fp.getFlatMoney());
			fpp.setAccrualMoney(fp.getAccrualMoney());
			fpp.setProjectName(fp.getProjectName());
			fpp.setProjectNumber(fp.getProjectNumber());
			fpp.setFaxiAfterMoney(fp.getFaxiAfterMoney());
			fpp.setBusinessType("SmallLoan");
			fpp.setBidPlanId(fp.getBidPlanId());
			// fpp.setIds(fp.getFundType()+"="+fp.getFundIntentId().toString());
			// Long investPersonId=
			// investPersonInfoService.get(fp.getInvestPersonId()).getInvestPersonId();
			Long investPersonId = fp.getInvestPersonId();
			fpp.setIds(investPersonId.toString());
			if (fp.getFundType().equals("principalLending")) {

				fpp.setPayMoney(fp.getPayMoney());
			} else {
				fpp.setIncomeMoney(fp.getIncomeMoney());

			}
			FundPaylist.add(fpp);
			int j = 0;
			while (j < list.size()) {
				BpFundIntent fps = list.get(j);
				if (!fp.equals(fps)
						&& fps.getBidPlanId().equals(fp.getBidPlanId())
						&& fps.getPayintentPeriod().equals(
								fp.getPayintentPeriod())
						&& fps.getFundType().equals(fp.getFundType())) {

					if (fps.getFundType().equals("principalLending")) {

						fpp.setPayMoney(fpp.getPayMoney()
								.add(fps.getPayMoney()));
					} else {
						fpp.setIncomeMoney(fpp.getIncomeMoney().add(
								fps.getIncomeMoney()));

					}
					fpp.setNotMoney(fpp.getNotMoney().add(fps.getNotMoney()));
					investPersonId = fp.getInvestPersonId();
					if (!fpp.getIds().contains(investPersonId.toString())) {

						fpp.setIds(fpp.getIds() + ","
								+ investPersonId.toString());
					}

					list.remove(fps);
				} else {

					j++;

				}

			}
			i++;
		}

		return FundPaylist;
	}

	@Override
	public Long sizebyLedger(String businessType, String fundType,
			String typetab, PagingBean pb, Map<String, String> map) {
		// TODO Auto-generated method stub
		return dao.sizebyLedger(businessType, fundType, typetab, pb, map);
	}

	@Override
	public List<SlFundIntent> listbyfundType(String businessType,
			Long projectId, String fundType, Long isInitialorId) {
		// TODO Auto-generated method stub
		return dao.listbyfundType(businessType, projectId, fundType,
				isInitialorId);
	}

	@Override
	public List<SlFundIntent> listbyOwe(String businessType, String fundType,
			Long isInitialorId) {
		// TODO Auto-generated method stub
		return dao.listbyOwe(businessType, fundType, isInitialorId);
	}

	@Override
	public List<SlFundIntent> listbyisInitialorId(Long isInitialorId) {
		// TODO Auto-generated method stub
		return dao.listbyisInitialorId(isInitialorId);
	}

	@Override
	public List<SlFundIntent> getallbycompanyId() {
		// TODO Auto-generated method stub
		return dao.getallbycompanyId();
	}

	@Override
	public List<SlFundIntent> wsgetByPrincipalLending(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.wsgetByPrincipalLending(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> wsgetByInterestAccrued(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.wsgetByInterestAccrued(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> wsgetByInterestPlan(Long projectId,
			String businessType, String factDate, String fundType) {
		// TODO Auto-generated method stub
		return dao.wsgetByInterestPlan(projectId, businessType, factDate,
				fundType);
	}

	@Override
	public List<SlFundIntent> wsgetByPrincipalRepayOverdue(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.wsgetByPrincipalRepayOverdue(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> wsgetByRealInterest(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.wsgetByRealInterest(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> wsgetByRealPunishInterest(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.wsgetByRealPunishInterest(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> wsgetByRealpPrincipalPepay(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.wsgetByRealpPrincipalPepay(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> getByProjectId3(Long projectId,
			String businessType, String fundType) {
		// TODO Auto-generated method stub
		return dao.getByProjectId3(projectId, businessType, fundType);
	}

	/*
	 * public void createPunishByTiming(){ SimpleDateFormat from = new
	 * SimpleDateFormat("yyyy-MM-dd 00:00:00"); //挪用罚息 String
	 * fundType="('principalDivert')"; if(true){ try{ List<SlFundIntent>
	 * listbyowe=dao.listbyOwe("SmallLoan",fundType, null); for(SlFundIntent
	 * owe:listbyowe){ // dao.evict(owe); SlSmallloanProject
	 * loan=slSmallloanProjectDao.get(owe.getProjectId()); BigDecimal
	 * sortoverdueMoney=new BigDecimal(0.00); Date
	 * overdueintentDate=from.parse(from.format(new Date())); Long sortday=new
	 * Long(0);
	 * 
	 * if(loan.getOverdueRateType().equals("1")
	 * ||loan.getOverdueRateType().equals("2")){ //按天
	 * 
	 * if(owe.getFactDate() ==null){
	 * 
	 * String newdate= from.format(new Date()); Date d; d = from.parse(newdate);
	 * if(DateUtil.compare_date(d,owe.getIntentDate())==-1 &&
	 * owe.getPunishAccrual() !=null){ sortday =
	 * DateUtil.compare_date(owe.getIntentDate(),new Date())+1; BigDecimal day1
	 * = new BigDecimal(sortday); BigDecimal OverdueMoney =
	 * owe.getPunishAccrual().multiply(day1); sortoverdueMoney =
	 * OverdueMoney.multiply(owe.getNotMoney()).divide(new BigDecimal(100),2);
	 * owe.setAccrualMoney(sortoverdueMoney); } List<SlPunishInterest>
	 * listfaxi=slPunishInterestDao.listbyisInitialorId(owe.getFundIntentId());
	 * SlPunishInterest faxiSlFundIntent=new SlPunishInterest();
	 * if(listfaxi==null || listfaxi.size()==0){
	 * 
	 * faxiSlFundIntent.setIntentDate(overdueintentDate);
	 * faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setPayMoney(new BigDecimal("0"));
	 * faxiSlFundIntent.setNotMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setAfterMoney(new BigDecimal("0"));
	 * faxiSlFundIntent.setBusinessType(owe.getBusinessType());
	 * faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
	 * faxiSlFundIntent.setNotMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setProjectId(owe.getProjectId());
	 * faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
	 * faxiSlFundIntent.setFundType(owe.getFundType());
	 * faxiSlFundIntent.setCompanyId(owe.getCompanyId());
	 * faxiSlFundIntent.setPunishDays(Integer.valueOf(sortday.toString()));
	 * }else{
	 * 
	 * faxiSlFundIntent=listfaxi.get(listfaxi.size()-1);
	 * 
	 * faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setNotMoney
	 * (sortoverdueMoney.subtract(faxiSlFundIntent.getAfterMoney()));
	 * faxiSlFundIntent.setIntentDate(overdueintentDate);
	 * 
	 * } if(faxiSlFundIntent.getIncomeMoney().compareTo(new BigDecimal("0"))
	 * !=0){ slPunishInterestDao.save(faxiSlFundIntent);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * }catch(Exception e){ logger.error(e.getMessage());
	 * System.out.print(e.getMessage());
	 * 
	 * }} //本金罚息 fundType="('principalRepayment')"; try{ List<SlFundIntent>
	 * listbyowe=dao.listbyOwe("SmallLoan",fundType, null); for(SlFundIntent
	 * owe:listbyowe){ // dao.evict(owe); SlSmallloanProject
	 * loan=slSmallloanProjectDao.get(owe.getProjectId()); BigDecimal
	 * sortoverdueMoney=new BigDecimal(0.00); Date
	 * overdueintentDate=from.parse(from.format(new Date()));
	 * 
	 * 
	 * if(loan.getOverdueRateType().equals("1")
	 * ||loan.getOverdueRateType().equals("2")){ //按天
	 * owe.setOverdueRate(loan.getOverdueRate());
	 * if(null==owe.getOverdueRate()){ owe.setOverdueRate(new BigDecimal(0)); }
	 * 
	 * if(owe.getNotMoney().compareTo(new BigDecimal(0))==1){ BigDecimal
	 * notMoney=owe.getNotMoney(); BigDecimal sortoverdueMoneyinterest=new
	 * BigDecimal(0); List<SlFundIntent>
	 * listbyoweinterest=dao.listbyOwe("SmallLoan"
	 * ,"('loanInterest','consultationMoney','serviceMoney')", null);
	 * for(SlFundIntent oi:listbyoweinterest){
	 * notMoney=notMoney.add(oi.getNotMoney());
	 * if(loan.getOverdueRateType().equals("1")
	 * ||loan.getOverdueRateType().equals("2")){ //按天
	 * oi.setOverdueRate(loan.getOverdueRate()); if(null==oi.getOverdueRate()){
	 * oi.setOverdueRate(new BigDecimal(0)); }
	 * 
	 * if(oi.getNotMoney().compareTo(new BigDecimal(0))==1){
	 * 
	 * 
	 * long sortday=new Long(0);
	 * 
	 * String newdate= from.format(new Date()); Date d; d = from.parse(newdate);
	 * if(DateUtil.compare_date(d,oi.getIntentDate())==-1 && oi.getOverdueRate()
	 * !=null){ sortday = DateUtil.compare_date(oi.getIntentDate(),new Date());
	 * BigDecimal day1 = new BigDecimal(sortday); BigDecimal OverdueMoney =
	 * oi.getOverdueRate().multiply(day1); sortoverdueMoneyinterest =
	 * OverdueMoney.multiply(oi.getNotMoney()).divide(new BigDecimal(100),2);
	 * notMoney=notMoney.add(sortoverdueMoneyinterest); } }
	 * 
	 * } }
	 * 
	 * 
	 * 
	 * long sortday=new Long(0);
	 * 
	 * String newdate= from.format(new Date()); Date d; d = from.parse(newdate);
	 * if(DateUtil.compare_date(d,owe.getIntentDate())==-1 &&
	 * owe.getOverdueRate() !=null){ sortday =
	 * DateUtil.compare_date(owe.getIntentDate(),new Date()); BigDecimal day1 =
	 * new BigDecimal(sortday); BigDecimal OverdueMoney =
	 * owe.getOverdueRate().multiply(day1); sortoverdueMoney =
	 * OverdueMoney.multiply(notMoney).divide(new BigDecimal(100),2);
	 * owe.setAccrualMoney(sortoverdueMoney); } }
	 * 
	 * }
	 * 
	 * List<SlPunishInterest>
	 * listfaxi=slPunishInterestDao.listbyisInitialorId(owe.getFundIntentId());
	 * SlPunishInterest faxiSlFundIntent=new SlPunishInterest();
	 * if(listfaxi==null || listfaxi.size()==0){
	 * 
	 * faxiSlFundIntent.setIntentDate(overdueintentDate);
	 * faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setPayMoney(new BigDecimal("0"));
	 * faxiSlFundIntent.setNotMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setAfterMoney(new BigDecimal("0"));
	 * faxiSlFundIntent.setBusinessType(owe.getBusinessType());
	 * faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
	 * faxiSlFundIntent.setNotMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setProjectId(owe.getProjectId());
	 * faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
	 * faxiSlFundIntent.setFundType(owe.getFundType());
	 * faxiSlFundIntent.setCompanyId(owe.getCompanyId()); }else{
	 * 
	 * faxiSlFundIntent=listfaxi.get(listfaxi.size()-1);
	 * faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setNotMoney
	 * (sortoverdueMoney.subtract(faxiSlFundIntent.getAfterMoney()));
	 * faxiSlFundIntent.setIntentDate(overdueintentDate); }
	 * if(faxiSlFundIntent.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
	 * slPunishInterestDao.save(faxiSlFundIntent);
	 * 
	 * } }
	 * 
	 * }catch(Exception e){ logger.error(e.getMessage());
	 * 
	 * } //利息罚息 fundType="('loanInterest','consultationMoney','serviceMoney')";
	 * try{ List<SlFundIntent> listbyowe=dao.listbyOwe("SmallLoan",fundType,
	 * null); for(SlFundIntent owe:listbyowe){ // dao.evict(owe);
	 * SlSmallloanProject loan=slSmallloanProjectDao.get(owe.getProjectId());
	 * BigDecimal sortoverdueMoney=new BigDecimal(0.00); Date
	 * overdueintentDate=from.parse(from.format(new Date())); List<SlFundIntent>
	 * principalslFundIntent =dao.getByProjectId3(loan.getProjectId(),
	 * loan.getBusinessType(),"principalRepayment"); SlFundIntent
	 * slfunprincipal=new SlFundIntent();
	 * slfunprincipal.setIntentDate(from.parse(from.format(new Date())));
	 * if(null !=principalslFundIntent&&principalslFundIntent.size() !=0){
	 * slfunprincipal
	 * .setIntentDate(principalslFundIntent.get(0).getIntentDate());
	 * 
	 * }
	 * if(DateUtil.compare_date(overdueintentDate,slfunprincipal.getIntentDate(
	 * ))==-1 ){//今天大于本金最后计划日期就结束罚息了
	 * 
	 * }else{ if(loan.getOverdueRateType().equals("1")
	 * ||loan.getOverdueRateType().equals("2")){ //按天
	 * owe.setOverdueRate(loan.getOverdueRate());
	 * if(null==owe.getOverdueRate()){ owe.setOverdueRate(new BigDecimal(0)); }
	 * 
	 * if(owe.getNotMoney().compareTo(new BigDecimal(0))==1){
	 * 
	 * 
	 * long sortday=new Long(0);
	 * 
	 * String newdate= from.format(new Date()); Date d; d = from.parse(newdate);
	 * if(DateUtil.compare_date(d,owe.getIntentDate())==-1 &&
	 * owe.getOverdueRate() !=null){ sortday =
	 * DateUtil.compare_date(owe.getIntentDate(),new Date()); BigDecimal day1 =
	 * new BigDecimal(sortday); BigDecimal OverdueMoney =
	 * owe.getOverdueRate().multiply(day1); sortoverdueMoney =
	 * OverdueMoney.multiply(owe.getNotMoney()).divide(new BigDecimal(100),2);
	 * owe.setAccrualMoney(sortoverdueMoney); } }
	 * 
	 * }else{ //按期计算
	 * 
	 * List<SlFundIntent>
	 * listbyproject=dao.listbyfundType(owe.getBusinessType(),
	 * owe.getProjectId(), owe.getFundType(), null);//要改成原始的利息 int i=0;
	 * //此款项属于第几期 for(SlFundIntent s:listbyproject){ i++;
	 * if(s.getIntentDate().compareTo(owe.getIntentDate())==0){ break; } } int
	 * j=0; //截止今天是属于第几期 for(SlFundIntent s:listbyproject){ j++;
	 * if(s.getIntentDate().compareTo(new Date())==1){ j--; break; } } if(i!=j){
	 * sortoverdueMoney
	 * =owe.getNotMoney().multiply(FundIntentListPro3.mnfang(loan
	 * .getAccrual().divide(new BigDecimal("100")).add(new BigDecimal("1")),
	 * j-i).subtract(new BigDecimal("1"))); }
	 * 
	 * overdueintentDate=listbyproject.get(j-1).getIntentDate(); }
	 * 
	 * List<SlPunishInterest>
	 * listfaxi=slPunishInterestDao.listbyisInitialorId(owe.getFundIntentId());
	 * SlPunishInterest faxiSlFundIntent=new SlPunishInterest();
	 * if(listfaxi==null || listfaxi.size()==0){
	 * 
	 * faxiSlFundIntent.setIntentDate(overdueintentDate);
	 * faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setPayMoney(new BigDecimal("0"));
	 * faxiSlFundIntent.setNotMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setAfterMoney(new BigDecimal("0"));
	 * faxiSlFundIntent.setBusinessType(owe.getBusinessType());
	 * faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
	 * faxiSlFundIntent.setNotMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setProjectId(owe.getProjectId());
	 * faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
	 * faxiSlFundIntent.setFundType(owe.getFundType());
	 * faxiSlFundIntent.setCompanyId(owe.getCompanyId()); }else{
	 * 
	 * faxiSlFundIntent=listfaxi.get(listfaxi.size()-1);
	 * faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
	 * faxiSlFundIntent.setNotMoney
	 * (sortoverdueMoney.subtract(faxiSlFundIntent.getAfterMoney()));
	 * faxiSlFundIntent.setIntentDate(overdueintentDate); }
	 * if(faxiSlFundIntent.getIncomeMoney().compareTo(new BigDecimal("0")) !=0){
	 * slPunishInterestDao.save(faxiSlFundIntent); } } }
	 * 
	 * }catch(Exception e){ logger.error(e.getMessage());
	 * 
	 * }
	 * 
	 * 
	 * }
	 */

	@Override
	public String savejsonloaned(String slFundIentJson, Long projectId,
			String businessType, Short flag, Short isValid, Long companyId,
			String isLoanedType, Long loanedId) {

		if (isLoanedType.equals("alterAccrual")) {

			List<SlFundIntent> allintent = this.slFundIntentDao
					.getlistbyslslAlteraccrualRecordId(loanedId, businessType,
							projectId);
			for (SlFundIntent s : allintent) {
				slFundIntentDao.evict(s);
				if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
					this.slFundIntentDao.remove(s);
				}
			}
		}
		if (isLoanedType.equals("earlyRepayment")) {
			List<SlFundIntent> allintent = this.slFundIntentDao
					.getlistbyslEarlyRepaymentId(loanedId, businessType,
							projectId);
			for (SlFundIntent s : allintent) {
				slFundIntentDao.evict(s);
				if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
					this.slFundIntentDao.remove(s);
				}
			}
		}
		if (isLoanedType.equals("extensionl")) {

		}

		if (null != slFundIentJson && !"".equals(slFundIentJson)) {

			String[] shareequityArr = slFundIentJson.split("@");

			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));

				try {

					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper
							.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(projectId);
					if (businessType.equals("SmallLoan")) {
						SlSmallloanProject loan = slSmallloanProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(loan.getProjectName());
						SlFundIntent1.setProjectNumber(loan.getProjectNumber());
						SlFundIntent1.setCompanyId(loan.getCompanyId());
					}
					if (businessType.equals("Financing")) {
						FlFinancingProject flFinancingProject = flFinancingProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(flFinancingProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(flFinancingProject
								.getProjectNumber());
						SlFundIntent1.setCompanyId(flFinancingProject
								.getCompanyId());
					}
					if (businessType.equals("Guarantee")) {
						GLGuaranteeloanProject gLGuaranteeloanProject = gLGuaranteeloanProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(gLGuaranteeloanProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(gLGuaranteeloanProject
								.getProjectNumber());
						SlFundIntent1.setCompanyId(gLGuaranteeloanProject
								.getCompanyId());
					}
					if (businessType.equals("LeaseFinance")) {
						FlLeaseFinanceProject flLeaseFinanceProject = flLeaseFinanceProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(flLeaseFinanceProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(flLeaseFinanceProject
								.getProjectNumber());
						SlFundIntent1.setCompanyId(flLeaseFinanceProject
								.getCompanyId());
					}
					if (businessType.equals("Pawn")) {
						PlPawnProject plPawnProject = plPawnProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(plPawnProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(plPawnProject
								.getProjectNumber());
						SlFundIntent1
								.setCompanyId(plPawnProject.getCompanyId());
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
						SlFundIntent1.setIsValid(isValid);
						SlFundIntent1.setBusinessType(businessType);
						// SlFundIntent1.setCompanyId(companyId);
						SlFundIntent1.setIsCheck(flag);
						if (isLoanedType.equals("alterAccrual")) {

							SlFundIntent1.setSlAlteraccrualRecordId(loanedId);
						}
						if (isLoanedType.equals("earlyRepayment")) {
							SlFundIntent1.setSlEarlyRepaymentId(loanedId);
						}
						if (isLoanedType.equals("extensionl")) {

							SlFundIntent1.setSlSuperviseRecordId(loanedId);
						}

						dao.save(SlFundIntent1);
					} else {

						BigDecimal lin = new BigDecimal(0.00);
						if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getPayMoney());
						} else {
							SlFundIntent1.setNotMoney(SlFundIntent1
									.getIncomeMoney());

						}

						SlFundIntent slFundIntent2 = dao.get(SlFundIntent1
								.getFundIntentId());
						if (slFundIntent2.getAfterMoney().compareTo(
								new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slFundIntent2,
									SlFundIntent1);
							slFundIntent2.setIsCheck(flag);
							dao.save(slFundIntent2);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					logger.error(" 保存款项出错:" + e.getMessage());
					return "1";
				}

			}
		}
		return "0";
	}

	@Override
	public BigDecimal computeAccrual(String fundType, String businessType,
			Date factdate, SlFundIntent owe) { // 计算罚息
		SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		BigDecimal sortoverdueMoney = new BigDecimal(0.00);
		if (fundType.equals("principalRepayment")) {
			try {
				slFundIntentDao.evict(owe);
				SlSmallloanProject loan = slSmallloanProjectDao.get(owe
						.getProjectId());

				Date overdueintentDate = from.parse(from.format(factdate));

				if (loan.getOverdueRateType().equals("1")
						|| loan.getOverdueRateType().equals("2")) { // 按天
					owe.setOverdueRate(loan.getOverdueRate());
					if (null == owe.getOverdueRate()) {
						owe.setOverdueRate(new BigDecimal(0));
					}

					if (owe.getNotMoney().compareTo(new BigDecimal(0)) == 1) {
						BigDecimal notMoney = owe.getNotMoney();
						BigDecimal sortoverdueMoneyinterest = new BigDecimal(0);
						List<SlFundIntent> listbyoweinterest = slFundIntentDao
								.listbyOwe(
										businessType,
										"('loanInterest','consultationMoney','serviceMoney')",
										null);
						for (SlFundIntent oi : listbyoweinterest) {
							notMoney = notMoney.add(oi.getNotMoney());
							if (loan.getOverdueRateType().equals("1")
									|| loan.getOverdueRateType().equals("2")) { // 按天
								oi.setOverdueRate(loan.getOverdueRate());
								if (null == oi.getOverdueRate()) {
									oi.setOverdueRate(new BigDecimal(0));
								}

								if (oi.getNotMoney().compareTo(
										new BigDecimal(0)) == 1) {

									long sortday = new Long(0);

									String newdate = from.format(factdate);
									Date d;
									d = from.parse(newdate);
									if (compare_date(d, oi.getIntentDate()) == -1
											&& oi.getOverdueRate() != null) {
										sortday = compare_date(oi
												.getIntentDate(), factdate);
										BigDecimal day1 = new BigDecimal(
												sortday);
										BigDecimal OverdueMoney = oi
												.getOverdueRate()
												.multiply(day1);
										sortoverdueMoneyinterest = OverdueMoney
												.multiply(oi.getNotMoney())
												.divide(new BigDecimal(100), 2);
										notMoney = notMoney
												.add(sortoverdueMoneyinterest);
									}
								}

							}
						}

						long sortday = new Long(0);

						String newdate = from.format(factdate);
						Date d;
						d = from.parse(newdate);
						if (compare_date(d, owe.getIntentDate()) == -1
								&& owe.getOverdueRate() != null) {
							sortday = compare_date(owe.getIntentDate(),
									factdate);
							BigDecimal day1 = new BigDecimal(sortday);
							BigDecimal OverdueMoney = owe.getOverdueRate()
									.multiply(day1);
							sortoverdueMoney = OverdueMoney.multiply(notMoney)
									.divide(new BigDecimal(100), 2);
							owe.setAccrualMoney(sortoverdueMoney);
						}
					}

				}

				List<SlPunishInterest> listfaxi = slPunishInterestDao
						.listbyisInitialorId(owe.getFundIntentId());
				SlPunishInterest faxiSlFundIntent = new SlPunishInterest();
				if (listfaxi == null || listfaxi.size() == 0) {

					faxiSlFundIntent.setIntentDate(overdueintentDate);
					faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
					faxiSlFundIntent.setPayMoney(new BigDecimal("0"));
					faxiSlFundIntent.setNotMoney(sortoverdueMoney);
					faxiSlFundIntent.setAfterMoney(new BigDecimal("0"));
					faxiSlFundIntent.setBusinessType(owe.getBusinessType());
					faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
					faxiSlFundIntent.setNotMoney(sortoverdueMoney);
					faxiSlFundIntent.setProjectId(owe.getProjectId());
					faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
					faxiSlFundIntent.setFundType(owe.getFundType());
					faxiSlFundIntent.setCompanyId(owe.getCompanyId());
				} else {

					faxiSlFundIntent = listfaxi.get(listfaxi.size() - 1);
					faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
					faxiSlFundIntent.setNotMoney(sortoverdueMoney
							.subtract(faxiSlFundIntent.getAfterMoney()));
					faxiSlFundIntent.setIntentDate(overdueintentDate);
				}
				if (faxiSlFundIntent.getIncomeMoney().compareTo(
						new BigDecimal("0")) != 0) {
					slPunishInterestDao.save(faxiSlFundIntent);

				}

			} catch (Exception e) {
				logger.error(e.getMessage());

			}
		}
		if (fundType.equals("loanInterest")
				|| fundType.equals("consultationMoney")
				|| fundType.equals("serviceMoney")) {
			try {
				slFundIntentDao.evict(owe);
				SlSmallloanProject loan = slSmallloanProjectDao.get(owe
						.getProjectId());
				Date overdueintentDate = from.parse(from.format(new Date()));
				List<SlFundIntent> principalslFundIntent = slFundIntentDao
						.getByProjectId3(loan.getProjectId(), loan
								.getBusinessType(), "principalRepayment");
				SlFundIntent slfunprincipal = new SlFundIntent();
				slfunprincipal.setIntentDate(from
						.parse(from.format(new Date())));
				if (null != principalslFundIntent
						&& principalslFundIntent.size() != 0) {
					slfunprincipal.setIntentDate(principalslFundIntent.get(0)
							.getIntentDate());

				}
				if (compare_date(factdate, slfunprincipal.getIntentDate()) == -1) {// 今天大于本金最后计划日期就结束罚息了

					factdate = slfunprincipal.getIntentDate();
					if (loan.getOverdueRateType().equals("1")
							|| loan.getOverdueRateType().equals("2")) { // 按天
						owe.setOverdueRate(loan.getOverdueRate());
						if (null == owe.getOverdueRate()) {
							owe.setOverdueRate(new BigDecimal(0));
						}

						if (owe.getNotMoney().compareTo(new BigDecimal(0)) == 1) {

							long sortday = new Long(0);

							String newdate = from.format(factdate);
							Date d;
							d = from.parse(newdate);
							if (compare_date(d, owe.getIntentDate()) == -1
									&& owe.getOverdueRate() != null) {
								sortday = compare_date(owe.getIntentDate(),
										factdate);
								BigDecimal day1 = new BigDecimal(sortday);
								BigDecimal OverdueMoney = owe.getOverdueRate()
										.multiply(day1);
								sortoverdueMoney = OverdueMoney.multiply(
										owe.getNotMoney()).divide(
										new BigDecimal(100), 2);
								owe.setAccrualMoney(sortoverdueMoney);
							}
						}

					} else { // 按期计算

						List<SlFundIntent> listbyproject = slFundIntentDao
								.listbyfundType(owe.getBusinessType(), owe
										.getProjectId(), owe.getFundType(),
										null);// 要改成原始的利息
						int i = 0; // 此款项属于第几期
						for (SlFundIntent s : listbyproject) {
							i++;
							if (s.getIntentDate()
									.compareTo(owe.getIntentDate()) == 0) {
								break;
							}
						}
						int j = 0; // 截止今天是属于第几期
						for (SlFundIntent s : listbyproject) {
							j++;
							if (s.getIntentDate().compareTo(new Date()) == 1) {
								j--;
								break;
							}
						}
						if (i != j) {
							sortoverdueMoney = owe
									.getNotMoney()
									.multiply(
											FundIntentListPro3
													.mnfang(
															loan
																	.getAccrual()
																	.divide(
																			new BigDecimal(
																					"100"))
																	.add(
																			new BigDecimal(
																					"1")),
															j - i)
													.subtract(
															new BigDecimal("1")));
						}

						overdueintentDate = listbyproject.get(j - 1)
								.getIntentDate();
					}

					List<SlPunishInterest> listfaxi = slPunishInterestDao
							.listbyisInitialorId(owe.getFundIntentId());
					SlPunishInterest faxiSlFundIntent = new SlPunishInterest();
					if (listfaxi == null || listfaxi.size() == 0) {

						faxiSlFundIntent.setIntentDate(overdueintentDate);
						faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
						faxiSlFundIntent.setPayMoney(new BigDecimal("0"));
						faxiSlFundIntent.setNotMoney(sortoverdueMoney);
						faxiSlFundIntent.setAfterMoney(new BigDecimal("0"));
						faxiSlFundIntent.setBusinessType(owe.getBusinessType());
						faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
						faxiSlFundIntent.setNotMoney(sortoverdueMoney);
						faxiSlFundIntent.setProjectId(owe.getProjectId());
						faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
						faxiSlFundIntent.setFundType(owe.getFundType());
						faxiSlFundIntent.setCompanyId(owe.getCompanyId());
					} else {

						faxiSlFundIntent = listfaxi.get(listfaxi.size() - 1);
						faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
						faxiSlFundIntent.setNotMoney(sortoverdueMoney
								.subtract(faxiSlFundIntent.getAfterMoney()));
						faxiSlFundIntent.setIntentDate(overdueintentDate);
					}
					if (faxiSlFundIntent.getIncomeMoney().compareTo(
							new BigDecimal("0")) != 0) {
						slPunishInterestDao.save(faxiSlFundIntent);
					}
				}

			} catch (Exception e) {
				logger.error(e.getMessage());

			}
		}

		return sortoverdueMoney;
	}

	public static long compare_date(Date dt1, Date dt2) {
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
	public List<SlFundIntent> getListByFundType(Long projectId,
			String businessType, String fundType, Long slSuperviseRecordId,
			Long preceptId) {

		return dao.getListByFundType(projectId, businessType, fundType,
				slSuperviseRecordId, preceptId);
	}

	public List<SlFundIntent> getListByIntentDate(Long projectId,
			String businessType, String date, Long slSuperviseRecordId) {

		return dao.getListByIntentDate(projectId, businessType, date,
				slSuperviseRecordId);

	}

	@Override
	public List<SlFundIntent> listOfOneTime(String startDate,
			String intentDate, String earlyDate, String accrualType,
			String payaccrualType, BigDecimal accrual,
			BigDecimal managementConsultingOfRate, BigDecimal earlyMoney,
			BigDecimal projectMoney, Integer payintentPerioDate,
			Boolean isPreposePayConsultingCheck) {/*
												 * List<SlFundIntent> list=new
												 * ArrayList<SlFundIntent>();
												 * SimpleDateFormat sdf=new
												 * SimpleDateFormat
												 * ("yyyy-MM-dd");
												 * if(accrualType
												 * .equals("ontTimeAccrual")){
												 * BigDecimal
												 * dayOfEveryPeriod1=new
												 * BigDecimal
												 * (Integer.valueOf(DateUtil
												 * .getDaysBetweenDate
												 * (startDate,
												 * earlyDate)).toString());
												 * accrual=accrual.divide(new
												 * BigDecimal(100));
												 * accrual=accrual.divide(new
												 * BigDecimal("30"), 100,
												 * BigDecimal.ROUND_HALF_UP);
												 * managementConsultingOfRate
												 * =managementConsultingOfRate
												 * .divide(new BigDecimal(100));
												 * managementConsultingOfRate
												 * =managementConsultingOfRate
												 * .divide(new BigDecimal("30"),
												 * 100,
												 * BigDecimal.ROUND_HALF_UP);
												 * BigDecimal accrualMoney1=new
												 * BigDecimal(0);
												 * accrualMoney1=projectMoney
												 * .multiply(accrual).multiply(
												 * dayOfEveryPeriod1);
												 * BigDecimal
												 * managementConsultingMoney1
												 * =new BigDecimal(0);
												 * managementConsultingMoney1
												 * =projectMoney.multiply(
												 * managementConsultingOfRate
												 * ).multiply
												 * (dayOfEveryPeriod1);
												 * SlFundIntent
												 * df=FundIntentListPro3
												 * .calculslfundintent
												 * ("loanInterest",
												 * DateUtil.parseDate
												 * (earlyDate,"yyyy-MM-dd"), new
												 * BigDecimal(0),
												 * accrualMoney1,1);
												 * if(df.getIncomeMoney
												 * ().compareTo(new
												 * BigDecimal(0))!=0){
												 * list.add(df); }
												 * if(isPreposePayConsultingCheck
												 * ==false){ SlFundIntent
												 * df1=FundIntentListPro3
												 * .calculslfundintent
												 * ("consultationMoney",
												 * DateUtil
												 * .parseDate(earlyDate,"yyyy-MM-dd"
												 * ), new BigDecimal(0),
												 * managementConsultingMoney1
												 * ,1);
												 * if(df1.getIncomeMoney().compareTo
												 * (new BigDecimal(0))!=0){
												 * list.add(df1); } }
												 * SlFundIntent
												 * df2=FundIntentListPro3
												 * .calculslfundintent
												 * ("principalRepayment",
												 * DateUtil
												 * .parseDate(earlyDate,"yyyy-MM-dd"
												 * ), new BigDecimal(0),
												 * earlyMoney,1);
												 * if(df2.getIncomeMoney
												 * ().compareTo(new
												 * BigDecimal(0))!=0){
												 * list.add(df2); } BigDecimal
												 * dayOfEveryPeriod2=new
												 * BigDecimal
												 * (Integer.valueOf(DateUtil
												 * .getDaysBetweenDate
												 * (earlyDate,
												 * intentDate)).toString());
												 * BigDecimal accrualMoney2=new
												 * BigDecimal(0);
												 * accrualMoney2=(
												 * projectMoney.subtract
												 * (earlyMoney
												 * )).multiply(accrual
												 * ).multiply(
												 * dayOfEveryPeriod2);
												 * BigDecimal
												 * managementConsultingMoney2
												 * =new BigDecimal(0);
												 * managementConsultingMoney2
												 * =(projectMoney
												 * .subtract(earlyMoney
												 * )).multiply
												 * (managementConsultingOfRate
												 * ).multiply
												 * (dayOfEveryPeriod2);
												 * SlFundIntent
												 * df3=FundIntentListPro3
												 * .calculslfundintent
												 * ("loanInterest",
												 * DateUtil.parseDate
												 * (intentDate,"yyyy-MM-dd"),
												 * new BigDecimal(0),
												 * accrualMoney2,1);
												 * if(df3.getIncomeMoney
												 * ().compareTo(new
												 * BigDecimal(0))!=0){
												 * list.add(df3); }
												 * if(isPreposePayConsultingCheck
												 * ==false){ SlFundIntent
												 * df4=FundIntentListPro3
												 * .calculslfundintent
												 * ("consultationMoney"
												 * ,DateUtil.
												 * parseDate(intentDate
												 * ,"yyyy-MM-dd"), new
												 * BigDecimal(0),
												 * managementConsultingMoney2
												 * ,1);
												 * if(df4.getIncomeMoney().compareTo
												 * (new BigDecimal(0))!=0){
												 * list.add(df4); } }
												 * SlFundIntent
												 * df5=FundIntentListPro3
												 * .calculslfundintent
												 * ("principalRepayment",
												 * DateUtil
												 * .parseDate(intentDate,
												 * "yyyy-MM-dd"), new
												 * BigDecimal(0),
												 * projectMoney.subtract
												 * (earlyMoney),1);
												 * if(df5.getIncomeMoney
												 * ().compareTo(new
												 * BigDecimal(0))!=0){
												 * list.add(df5); } } return
												 * list;
												 */
		return null;
	}

	// add by liny 2013-2-28 查找共有多少条本金偿还
	@Override
	public List<SlFundIntent> findLastPrincipal(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.findLastPrincipal(projectId, businessType);

	}

	@Override
	public List<SlFundIntent> getByProjectId4(Long projectId,
			String businessType) {

		return dao.getByProjectId4(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> getListOfSupervise(Long projectId,
			String businessType, Date intentDate, Long slSuperviseRecordId) {

		return dao.getListOfSupervise(projectId, businessType, intentDate,
				slSuperviseRecordId);
	}

	@Override
	public List<SlFundIntent> getByFundType(Long projectId,
			String businessType, String fundType) {

		return dao.getByFundType(projectId, businessType, fundType);
	}

	@Override
	public List<SlFundIntent> getByIntentPeriod(Long projectId,
			String businessType, String fundType, Long slSuperviseRecordId,
			Integer payIntentPeriod) {

		return dao.getByIntentPeriod(projectId, businessType, fundType,
				slSuperviseRecordId, payIntentPeriod);
	}

	@Override
	public List<SlFundIntent> getOfEarly(Long projectId, String businessType,
			String fundType, Long slSuperviseRecordId) {

		return dao.getOfEarly(projectId, businessType, fundType,
				slSuperviseRecordId);
	}

	@Override
	public List<SlFundIntent> dynamicGetData(Long projectId,
			String businessType, String[] str) {
		return dao.dynamicGetData(projectId, businessType, str);
	}

	public List<SlFundIntent> getListOfHistory(Long projectId,
			String businessType, Date intentDate) {
		return dao.getListOfHistory(projectId, businessType, intentDate);
	}

	@Override
	public List<SlFundIntent> listByProjectId(Long projectId,
			String businessType) {

		return dao.listByProjectId(projectId, businessType);
	}

	// 用来导出贷款催收的记录 add by liny
	@Override
	public List<SlFundIntent> listOutExcel(String projectIdStr,
			Map<String, String> map, String businessType) {
		// TODO Auto-generated method stub
		return dao.listOutExcel(projectIdStr, map, businessType);
	}

	@Override
	public BigDecimal getSumMoney(Long projectId, String businessType,
			String fundType) {

		return dao.getSumMoney(projectId, businessType, fundType);
	}

	@Override
	public List<SlFundIntent> getListByOperateId(Long projectId,
			String businessType, Long operateId, String status) {

		return dao.getListByOperateId(projectId, businessType, operateId,
				status);
	}

	@Override
	public List<SlFundIntent> getListByEarlyOperateId(Long projectId,
			String businessType, Long earlyOperateId, String status) {

		return dao.getListByEarlyOperateId(projectId, businessType,
				earlyOperateId, status);
	}

	@Override
	public List<SlFundIntent> getListByAlteraccrualOperateId(Long projectId,
			String businessType, Long alteraccrualOperateId, String status) {

		return dao.getListByAlteraccrualOperateId(projectId, businessType,
				alteraccrualOperateId, status);
	}

	@Override
	public List<SlFundIntent> getListByFundType(Long projectId,
			String businessType, String fundType) {

		return dao.getListByFundType(projectId, businessType, fundType);
	}

	@Override
	public List<SlFundIntent> getListByTypeAndDate(Long projectId,
			String businessType, String fundType, String intentDate) {

		return dao.getListByTypeAndDate(projectId, businessType, fundType,
				intentDate);
	}

	@Override
	public List<SlFundIntent> getyqList(Long projectId, String businessType,
			String intentDate) {

		return dao.getyqList(projectId, businessType, intentDate);
	}

	@Override
	public List<SlFundIntent> savesmallLoanJsonIntent(
			String fundIntentJsonData, SlSmallloanProject slSmallloanProject,
			Short flag) {

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
						SlFundIntent1.setBusinessType("SmallLoan");
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
						SlFundIntent slFundIntent2 = this.get(SlFundIntent1
								.getFundIntentId());
						if (slFundIntent2.getAfterMoney().compareTo(
								new BigDecimal(0)) == 0) {
							BeanUtil.copyNotNullProperties(slFundIntent2,
									SlFundIntent1);
							slFundIntent2.setBusinessType("SmallLoan");
							slFundIntent2.setCompanyId(slSmallloanProject
									.getCompanyId());
							slFundIntent2.setIsCheck(flag);
							slFundIntents.add(slFundIntent2);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return slFundIntents;

	}

	@Override
	public List<SlFundIntent> getListByProjectId(Long projectId,
			String businessType) {
		return dao.getListByProjectId(projectId, businessType);
	}

	@Override
	public List<SlFundIntent> getList(String fundType, String intentDate,
			String companyId) {
		return dao.getList(fundType, intentDate, companyId);
	}

	@Override
	public List<SlFundIntent> getListByType(Long projectId,
			String businessType, String fundType, String intentDate) {

		return dao.getListByType(projectId, businessType, fundType, intentDate);
	}

	@Override
	public List<SlFundIntent> getByProjectAsc(Long projectId,
			String businessType, Long preceptId) {

		return dao.getByProjectAsc(projectId, businessType, preceptId);
	}

	/*
	 * @Override public List<SlFundIntent> getByProjectAsc(Long projectId,String
	 * businessType,String fundResource) {
	 * 
	 * return dao.getByProjectAsc(projectId, businessType,fundResource); }
	 */
	// 给充值和取现生成款项台账(债权转让业务)
	@Override
	public void addreChargeInfo(ObAccountDealInfo info, int i) {
		// TODO Auto-generated method stub
		String strs = ContextUtil.getBranchIdsStr();
		if (info != null && !"".equals(info)) {
			List<SlFundIntent> list = this.dao.findAccountFundIntent(info
					.getAccountId(), info.getInvestPersonId(), info.getId(), i);
			if (list != null && list.size() > 0) {// 用来移除该条平台账户交易有款项台账记录
				for (SlFundIntent temp : list) {
					this.dao.remove(temp);
				}
			}
			ObSystemAccount account = obSystemAccountDao.get(info
					.getAccountId());
			SlFundIntent sl = new SlFundIntent();
			sl.setAfterMoney(new BigDecimal(0));
			sl.setIsCheck(Short.valueOf("0"));// 0表示可以对账
			sl.setIsValid(Short.valueOf("0"));// 0表示有效的
			sl.setBusinessType("Assignment");// 充值取现的businessType=Assignment
			sl.setAccountDealInfoId(info.getId());
			sl.setIntentDate(info.getTransferDate());
			sl.setSystemAccountId(info.getAccountId());// 保存投资人平台账户
			sl.setAccountNumber(account.getAccountNumber());// 保存投资人平台账户的账号
			sl.setSystemAccountId(info.getAccountId());
			sl.setInvestPersonId(info.getInvestPersonId());
			sl.setInvestPersonName(info.getInvestPersonName());
			sl.setCompanyId(info.getCompanyId());
			if (i == 0) {// 表示充值
				sl.setFundType("accountRecharge");// accountRecharge资金类型表示为平台账户充值
				sl.setIncomeMoney(info.getIncomMoney());// 表示充值金额
				sl.setNotMoney(info.getIncomMoney());// 表示未对账金额
			} else if (i == 1) {
				sl.setFundType("accountEnchashment");// accountEnchashment资金类型表示为平台账户取现
				sl.setPayMoney(info.getPayMoney());// 表示充值金额
				sl.setNotMoney(info.getPayMoney());// 表示未对账金额
			}
			this.dao.save(sl);
		}

	}

	// 查出来取现和充值的款项
	@Override
	public List<SlFundIntent> findAccountFundIntent(Long accountId,
			Long investPersonId, Long id, int i) {
		// TODO Auto-generated method stub
		return this.dao.findAccountFundIntent(accountId, investPersonId, id, i);
	}

	// 查出债权人已经对过账的利息收益
	@Override
	public List<SlFundIntent> getTreeIdAndFundType(Long obligationId,
			Long investMentPersonId, Long id, String investaccrual) {
		// TODO Auto-generated method stub
		return this.dao.getListByTreeIdUn(obligationId, investMentPersonId, id,
				investaccrual);
	}

	// 查出一个债权人购买一个债权记录产生的款项信息(没有对账的款项)
	@Override
	public List<SlFundIntent> getListByTreeIdUn(Long projectId,
			Long investMentPersonId, Long id) {
		// TODO Auto-generated method stub
		return this.dao.getListByTreeIdUn(projectId, investMentPersonId, id);
	}

	// 查出一个债权人购买一个债权记录产生的款项信息
	@Override
	public List<SlFundIntent> getListByTreeId(Long projectId,
			Long investMentPersonId, Long id) {
		// TODO Auto-generated method stub
		return this.dao.getListByTreeId(projectId, investMentPersonId, id);
	}

	@Override
	public List<SlFundIntent> listOfInverstPerson(Long inverstPersonId,
			Long projectId, String businessType) {
		return dao
				.listOfInverstPerson(inverstPersonId, projectId, businessType);
	}

	@Override
	public List<SlFundIntent> getbyPreceptId(Long preceptId) {
		return this.dao.getbyPreceptId(preceptId);
	}

	@Override
	public String savejson(String slFundIentJson, Long projectId,
			String businessType, short flag, Long companyId) {

		if (dao.getByProjectId(projectId, businessType).size() != 0) {
			List<SlFundIntent> listall = new ArrayList<SlFundIntent>();
			listall = dao.getByProjectId(projectId, businessType);
			for (SlFundIntent l : listall) {
				slFundIntentDao.evict(l);
				if (l.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
					dao.remove(l);

				}
			}

		}
		int sumintent = 0; // 计算有多少条本金偿还的款项
		if (null != slFundIentJson && !"".equals(slFundIentJson)) {

			String[] shareequityArr = slFundIentJson.split("@");

			for (int k = 0; k < shareequityArr.length; k++) {
				String str = shareequityArr[k];
				// String substr = str.substring(16, 18);
				// String str1 = "\"\"";
				// if (substr.equals("\"\"")) {
				// str = str.substring(19);
				// str = "{" + str;
				// System.out.print(str);
				//
				// }
				JSONParser parser = new JSONParser(new StringReader(str));
				SlFundIntent SlFundIntent1;
				try {
					SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser
							.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(projectId);
					if (businessType.equals("SmallLoan")) {
						SlSmallloanProject loan = slSmallloanProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(loan.getProjectName());
						SlFundIntent1.setProjectNumber(loan.getProjectNumber());

					}
					if (businessType.equals("Financing")) {
						FlFinancingProject flFinancingProject = flFinancingProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(flFinancingProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(flFinancingProject
								.getProjectNumber());
					}
					if (businessType.equals("Guarantee")) {
						GLGuaranteeloanProject gLGuaranteeloanProject = gLGuaranteeloanProjectDao
								.get(projectId);
						SlFundIntent1.setProjectName(gLGuaranteeloanProject
								.getProjectName());
						SlFundIntent1.setProjectNumber(gLGuaranteeloanProject
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
						SlFundIntent1.setCompanyId(companyId);
						SlFundIntent1.setIsCheck(flag);

						slFundIntentDao.save(SlFundIntent1);
						if (SlFundIntent1.getFundType().equals(
								"principalRepayment")) {
							sumintent++;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(" 保存款项出错:" + e.getMessage());
				}
			}
		}
		return String.valueOf(sumintent);
	}

	@Override
	public List<SlFundIntent> getListByBidPlanId(Long bidPlanId) {
		return dao.getListByBidPlanId(bidPlanId);
	}

	/******
	 * 启动催收还款流程 查找所有未还款且三天内到期的说有流程 还款信息
	 * **/
	@Override
	public boolean startUrgeFlow() {
		List<SlFundIntent> list = new ArrayList<SlFundIntent>();
		String flowType = configMap.get("flowType").toString();
		if (flowType == null || !"ccbfCreditFlow".equals(flowType)) {
			return false;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 3);
		list = dao.getListByIntentDate(calendar.getTime(), (short) 0);
		if (list != null && list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				SlFundIntent fundIntent = list.get(i);
				if (fundIntent != null) {
					startNewFlow(fundIntent);
				}
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public void startNewFlow(SlFundIntent slFundIntent) {
		String flowType = configMap.get("flowType").toString();
		if (flowType == null || !"ccbfCreditFlow".equals(flowType)) {
			return;
		}
		Long companyId = new Long(0);
		SlSmallloanProject project = slSmallloanProjectDao.get(slFundIntent
				.getProjectId());
		String isGroupVersion = AppUtil.getSystemIsGroupVersion();
		if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
			companyId = project.getCompanyId();
		}
		ProDefinition pdf = proDefinitionDao.getByCompanyIdProcessName(
				companyId, "ccbfUrgeFlow");
		if (pdf != null) {
			String customerName = "";
			Map<String, Object> newVars = new HashMap<String, Object>();
			ProcessRun run = processRunDao.getByBusinessTypeProjectId(project
					.getProjectId(), project.getBusinessType());
			if (run != null) {
				customerName = run.getCustomerName();
			}
			FlowRunInfo newFlowRunInfo = new FlowRunInfo();
			newVars.put("projectId", project.getProjectId());
			newVars.put("oppositeType", project.getOppositeType());
			newVars.put("businessType", project.getBusinessType());
			newVars.put("customerName", customerName);
			// newVars.put("customerName",mapNew.get("cusName"));
			newVars.put("projectNumber", project.getProjectNumber());
			newVars.put("slFundIntentId", slFundIntent.getFundIntentId());
			newFlowRunInfo.getVariables().putAll(newVars);
			newFlowRunInfo.setBusMap(newVars);
			newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
			newFlowRunInfo.setFlowSubject(project.getProjectName() + "(还款催收流程"
					+ slFundIntent.getFundIntentId() + ")" + "-"
					+ project.getProjectNumber());
			// newFlowRunInfo.setFlowSubject(mapNew.get("projectName").toString()+"-"+project.getProjectNumber());
			jbpmService.doStartProcess(newFlowRunInfo);
			slFundIntent.setIsUrge((short) 1);
			dao.merge(slFundIntent);
		}
	}

	@Override
	public void updateFinance(String params) {
		// params="fid:460:1.61_fid:461:1.61_fid:459:9.67_bfid:247:0.00";
		// fid:480:0.00_bfid:18:485
		System.out.println("更新台账 +" + params);
		String[] arr0 = null;// 截取 -
		String[] arr1 = null;// 截取：
		SlFundIntent fund = null;
		BpFundIntent bfund = null;
		try {
			arr0 = params.split("_");
			for (int i = 0; i < arr0.length; i++) {
				arr1 = arr0[i].split(":");
				for (int j = 0; j < arr1.length; j++) {
					System.out.println("更新台账j +" + j + "==" + params);
					// 借款人款项台帐
					if (arr1[j].equals("fid")) {
						System.out.println("更新台账 2+" + params);
						fund = this.get(Long.valueOf(arr1[1]));
						// 款项台帐 剩余金额大于0
						if (fund.getNotMoney().compareTo(new BigDecimal(0)) > 0) {
							System.out.println("更新台账3+" + params);
							// 未还金额减少
							fund.setNotMoney(fund.getNotMoney().subtract(
									new BigDecimal(arr1[2])));
							// 已还金额增加
							fund.setAfterMoney(fund.getAfterMoney().add(
									new BigDecimal(arr1[2])));
							fund.setFactDate(new Date());
							this.save(fund);
							System.out.println("更新台账4+" + params);
						}

					} else if (arr1[j].equals("bfid")) {// 修改投资人款项台帐

						bfund = bpFundIntentService.get(Long.valueOf(arr1[1]));
						// 款项台帐 剩余金额大于0
						if (bfund.getNotMoney().compareTo(new BigDecimal(0)) > 0) {
							// 未还金额减少
							bfund.setNotMoney(bfund.getNotMoney().subtract(
									new BigDecimal(arr1[2])));
							// 已还金额增加
							bfund.setAfterMoney(bfund.getAfterMoney().add(
									new BigDecimal(arr1[2])));
							bfund.setFactDate(new Date());
							bpFundIntentService.save(bfund);
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public SlFundIntent getFundIntent(Long projectId, Integer payintentPeriod,
			String fundType, Long bidPlanId) {
		return dao.getFundIntent(projectId, payintentPeriod, fundType,
				bidPlanId);
	}

	@Override
	public List<SlFundIntentPeriod> listByBidPlanIdAndpayintentPeriod(
			PagingBean pb, Map<String, String> map) {

		List<SlFundIntentPeriod> list = bpFundIntentDao.listByBidPlanIdAndpayintentPeriod(pb, map);
		int j = 0;
		if (null != pb) {
			for (SlFundIntentPeriod l : list) {
				List<BpFundIntent> bpfundlist = bpFundIntentDao
						.getByBidPlanIdAndIntentDate(l.getPlanId(), l
								.getIntentDate(), null);
				l.initSlFundIntentPeriod(bpfundlist);
				j++;
			}
		}
		return list;
	}

	@Override
	public List<SlFundIntentPeriod> getCouponsList(
			PagingBean pb, Map<String, String> map) {
		
		List<SlFundIntentPeriod> list=	bpFundIntentDao.getCouponsList(pb, map);
	/*	BigDecimal money = new BigDecimal("0");
		if(null!=pb){
			for(SlFundIntentPeriod  l:list){
				List<BpFundIntent>	bpfundlist=bpFundIntentDao.getCouponsIntent(l.getPlanId().toString(), l.getPayintentPeriod().toString(), null, map.get("fundType").toString());
				for(BpFundIntent bp :bpfundlist){
					money=money.add(bp.getIncomeMoney());
				}
				l.setIncomeMoney(money);
				money = new BigDecimal("0");
			}
		}*/
		return list;
	}
	@Override
	public List<SlFundIntentPeriod> getRaiseinterestList(
			PagingBean pb, Map<String, String> map) {
		
		List<SlFundIntentPeriod> list=	bpFundIntentDao.getRaiseinterestList(pb, map);
		return list;
	}
	@Override
	public List<SlFundIntent> listByDateAndEarlyId(Long projectId,
			String businessType, String earlyDate, Long slEarlyRepaymentId,
			Long preceptId) {

		return dao.listByDateAndEarlyId(projectId, businessType, earlyDate,
				slEarlyRepaymentId, preceptId);
	}

	@Override
	public BigDecimal getPrincipalMoney(Long projectId, String businessType,
			String earlyDate, Long preceptId) {

		return dao.getPrincipalMoney(projectId, businessType, earlyDate,
				preceptId);
	}

	@Override
	public List<SlFundIntent> listByEarlyDate(Long projectId,
			String businessType, String earlyDate, String fundType,
			Long preceptId) {

		return dao.listByEarlyDate(projectId, businessType, earlyDate,
				fundType, preceptId);
	}

	/**
	 * 修改款项顺延日及宽限日
	 * @param owe
	 */
	public void common(SlFundIntent owe){
		//获得顺延日
		CsHoliday ch=csHolidayDao.getMaxDeferDay(owe.getIntentDate());
		if(null!=ch){
			owe.setContinueDay(sdf.format(DateUtil.addDaysToDate(ch.getYearStr(),1)));
		}else{
			owe.setContinueDay(sdf.format(owe.getIntentDate()));
		}
		//获得宽限日
		Date newEnd=DateUtil.addDaysToDate(owe.getIntentDate(),AppUtil.getGraceDayNum());
		owe.setGraceDay(sdf.format(newEnd));//宽限至
	}
	
	@Override
	public void createPunishByTiming() {
		String fundType = "('loanInterest','consultationMoney','serviceMoney','principalRepayment')";
		List<SlFundIntent> listbyowe = dao.listbyOweTiming("SmallLoan",fundType, null);
		//跑批记录 
		AppUser appUser = ContextUtil.getCurrentUser();
		String pushUserName = "定时跑批";
		Long pushUserId = null;
		if(null != appUser && !"".equals(appUser)){
			pushUserName = appUser.getFullname();
			pushUserId = appUser.getUserId();
		}
		BatchRunRecord batchRunRecord = new BatchRunRecord();
		batchRunRecord.setRunType("罚息计算");
		batchRunRecord.setAppUserId(pushUserId);
		batchRunRecord.setAppUserName(pushUserName);
		batchRunRecord.setStartRunDate(new Date());
		batchRunRecord.setTotalNumber(Long.valueOf(listbyowe.size()));
		batchRunRecord.setHappenAbnorma("正常");
		for (int i=0;i<listbyowe.size();i++) {
			SlFundIntent owe=listbyowe.get(i);
			try {
				//没有对过账的前提下可以修改款项的顺延日和宽限日
				if(owe.getAfterMoney().compareTo(new BigDecimal(0))==0){
					//根据款项查找罚息记录(查逾期记录一样),只要有记录就说明计算了罚息
					List<SlPunishInterest> listfaxi = slPunishInterestDao.listbyisInitialorId(owe.getFundIntentId());
					if(null!=listfaxi && listfaxi.size()>0){
						//查找罚息对账记录
						List<SlPunishDetail> list=slPunishDetailDao.getlistbyActualChargeId(listfaxi.get(0).getPunishInterestId());
						if(null==list || list.size()==0){
							common(owe);
						}
					}else{
						common(owe);
					}
				}
				computeAccrualnew(owe,null);
			} catch (Exception e) {
				String ids="";
				if(null != batchRunRecord.getIds()){
					ids =batchRunRecord.getIds() + "," + owe.getFundIntentId();
				}else{
					ids = owe.getFundIntentId().toString();
				}
				batchRunRecord.setIds(ids);
				batchRunRecord.setHappenAbnorma("异常");
				e.printStackTrace();
			}
		}
		batchRunRecord.setEndRunDate(new Date());
		batchRunRecordService.save(batchRunRecord);
	}

	@Override
	public void computeAccrualnew(SlFundIntent owe,SlFundDetail slFundDetail) {
		boolean flag=true;//用于标识是否需要修改款项的逾期天数punishDays
		Date overdueDate=new Date();
//		SlSmallloanProject loan = slSmallloanProjectDao.get(owe.getProjectId());
		BpFundProject loan=bpFundProjectDao.getByProjectId(owe.getProjectId(),Short.valueOf("0"));
		BigDecimal money = owe.getNotMoney();//未对账金额(逾期本金)
		BigDecimal overdueMoney = new BigDecimal(0.00);//逾期金额
		//1.获得逾期利率
		owe.setOverdueRate(loan.getOverdueRate());
		if (null == owe.getOverdueRate()) {
			owe.setOverdueRate(new BigDecimal(0));
		}
		//2.计算当前款项的罚息金额、逾期金额
		if (money.compareTo(new BigDecimal(0)) == 1) {
			Date newBegin=owe.getIntentDate();
			boolean isGrace = false;
			boolean iscontinue = false;
			if(null != owe.getGraceDay() && !"".equals(owe.getGraceDay())){//宽限至
				newBegin=DateUtil.parseDate(owe.getGraceDay());
				isGrace = true;
			}
			if(null != owe.getContinueDay() && !"".equals(owe.getContinueDay())){//顺延至
				newBegin=DateUtil.parseDate(owe.getContinueDay());
				iscontinue = true;
			}
			if(isGrace && iscontinue){//如果两个日期都不为null,以最大的日期为准
				if(owe.getContinueDay().compareTo(owe.getGraceDay()) >0 ){
					newBegin = DateUtil.parseDate(owe.getContinueDay());
				}else{
					newBegin=DateUtil.parseDate(owe.getGraceDay());
				}
			}
			if (DateUtil.compare_date(overdueDate,newBegin) == -1) {
				//计算逾期天数
				long sortday = DateUtil.compare_date(owe.getIntentDate(),overdueDate)+1;
				BigDecimal day1 = new BigDecimal(sortday);
				//计算逾期金额
				overdueMoney = owe.getOverdueRate().multiply(day1).multiply(money).divide(new BigDecimal(100), 2);
//			    System.out.println("逾期金额："+overdueMoney+"==Id:"+owe.getFundIntentId()+"=="+owe.getOverdueRate()+"=="+day1);
				if(null!=slFundDetail){
					long sortday2 = DateUtil.compare_date(owe.getIntentDate(),slFundDetail.getFactDate());
					BigDecimal day2 = new BigDecimal(sortday2);
					slFundDetail.setOverdueNum(day2.longValue());
				}
				owe.setPunishDays(day1.intValue());
			}
		}
		//3.获得资金明细的罚息金额、逾期金额
		List<SlFundDetail> detiallist = slFundDetailDao.getlistbySlFundIntentId(owe.getFundIntentId(),"1");
		BigDecimal oversum = new BigDecimal("0");
		for (SlFundDetail de : detiallist) {
			//已经全部对账的情况下需要比较所有资金明细，获得最大对账日
			if(flag && owe.getNotMoney().compareTo(new BigDecimal(0))==0){
				flag=false;
				Long day=0L;
				if(null!=de.getOverdueNum()){
					day=de.getOverdueNum();
					owe.setFactDate(de.getFactDate());
				}
				owe.setPunishDays(day.intValue());
			}
			
			if(null==de.getOverdueAccrual()){
				de.setOverdueAccrual(new BigDecimal(0));
			}
			oversum = oversum.add(de.getOverdueAccrual());//获得逾期金额
		}
		overdueMoney = overdueMoney.add(oversum);
		owe.setAccrualMoney(overdueMoney);
		//4.修改款项罚息总额
		BigDecimal afterMoneyPunish=new BigDecimal(0);
		if(overdueMoney.compareTo(new BigDecimal(0))==1){
			afterMoneyPunish = updatepunish(owe,overdueMoney,overdueDate);
		}
		owe.setFaxiAfterMoney(afterMoneyPunish);
		dao.save(owe);
		if(null!=slFundDetail){
			slFundDetailDao.merge(slFundDetail);
		}
	}
	
	/**
	 * 
	 * @param owe   款项对象
	 * @param money 罚息/逾期金额
	 * @param overDate 罚息到账日
	 * @param flag     用来区分是逾期(1)还是罚息(0)
	 * @return
	 */
	public BigDecimal updatepunish(SlFundIntent owe,BigDecimal money, Date overDate) {
		//重新生成罚息记录
		List<SlPunishInterest> listfaxi = slPunishInterestDao.listbyisInitialorId(owe.getFundIntentId());
		SlPunishInterest faxiSlFundIntent = new SlPunishInterest();
		if (listfaxi == null || listfaxi.size() == 0) {
			faxiSlFundIntent.setIntentDate(overDate);
			faxiSlFundIntent.setIncomeMoney(money);
			faxiSlFundIntent.setPayMoney(new BigDecimal("0"));
			faxiSlFundIntent.setNotMoney(money);
			faxiSlFundIntent.setAfterMoney(new BigDecimal("0"));
			faxiSlFundIntent.setBusinessType(owe.getBusinessType());
			faxiSlFundIntent.setFundIntentId(owe.getFundIntentId());
			faxiSlFundIntent.setProjectId(owe.getProjectId());
			faxiSlFundIntent.setFundType(owe.getFundType());
			faxiSlFundIntent.setCompanyId(owe.getCompanyId());
			faxiSlFundIntent.setPunishDays(owe.getPunishDays());
			slPunishInterestDao.save(faxiSlFundIntent);
			return faxiSlFundIntent.getAfterMoney();
		} else {
			if(null==faxiSlFundIntent.getFactDate()){
				faxiSlFundIntent.setPunishDays(owe.getPunishDays());
			}
			faxiSlFundIntent = listfaxi.get(0);
			faxiSlFundIntent.setIncomeMoney(money);
			BigDecimal flatMoney = new BigDecimal(0.00);
			if(null!=faxiSlFundIntent.getFlatMoney()){
				flatMoney=faxiSlFundIntent.getFlatMoney();
			}
			//罚息总额-罚息已对账金额=当前罚息未对账金额
			faxiSlFundIntent.setNotMoney(money.subtract(faxiSlFundIntent.getAfterMoney()).subtract(flatMoney));
			faxiSlFundIntent.setIntentDate(overDate);
			return faxiSlFundIntent.getAfterMoney();
		}
	}

	@Override
	public void calculatePenalty() {
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			List<SlFundIntent> list = slFundIntentDao.listByCurrentDate(sd
					.format(new Date()));
			for (SlFundIntent s : list) {
				BigDecimal rate = new BigDecimal(0);
				if (s.getBusinessType().equals("SmallLoan")) {
					BpFundProject project = bpFundProjectDao.get(s
							.getPreceptId());
					if (null != project) {
						if (s.getFundType().equals("principalRepayment")) {
							rate = project.getOverdueRateLoan();
						} else {
							rate = project.getOverdueRate();
						}
					}
				} else if (s.getBusinessType().equals("Pawn")) {
					PlPawnProject project = plPawnProjectDao.get(s
							.getProjectId());
					if (null != project) {
						if (s.getFundType().equals("principalRepayment")) {
							rate = project.getOverdueRateLoan();
						} else {
							rate = project.getOverdueRate();
						}
					}
				}
				int days = 0;
				if (null == s.getFactDate()) {
					days = DateUtil.getDaysBetweenDate(s.getIntentDate(),
							new Date());
				} else {
					days = DateUtil.getDaysBetweenDate(s.getFactDate(),
							new Date());
				}
				BigDecimal sortoverdueMoney = new BigDecimal(0);
				sortoverdueMoney = s.getNotMoney().multiply(rate).divide(
						new BigDecimal(100)).multiply(new BigDecimal(days));
				List<SlPunishInterest> listfaxi = slPunishInterestDao
						.listbyisInitialorId(s.getFundIntentId());
				if (null == listfaxi || listfaxi.size() == 0) {
					SlPunishInterest faxiSlFundIntent = new SlPunishInterest();
					faxiSlFundIntent.setIntentDate(DateUtil.parseDate(sd
							.format(new Date()), "yyyy-MM-dd"));
					faxiSlFundIntent.setIncomeMoney(sortoverdueMoney);
					faxiSlFundIntent.setPayMoney(new BigDecimal("0"));
					faxiSlFundIntent.setNotMoney(sortoverdueMoney);
					faxiSlFundIntent.setAfterMoney(new BigDecimal("0"));
					faxiSlFundIntent.setBusinessType(s.getBusinessType());
					faxiSlFundIntent.setFundIntentId(s.getFundIntentId());
					faxiSlFundIntent.setNotMoney(sortoverdueMoney);
					faxiSlFundIntent.setProjectId(s.getProjectId());
					faxiSlFundIntent.setFundType(s.getFundType());
					faxiSlFundIntent.setCompanyId(s.getCompanyId());
					faxiSlFundIntent.setPunishDays(days);
					if (faxiSlFundIntent.getIncomeMoney().compareTo(
							new BigDecimal("0")) != 0) {
						slPunishInterestDao.save(faxiSlFundIntent);

					}
					s.setAccrualMoney(sortoverdueMoney);
					slFundIntentDao.merge(s);
				} else {
					SlPunishInterest faxiSlFundIntent = listfaxi.get(0);
					faxiSlFundIntent.setIntentDate(DateUtil.parseDate(sd
							.format(new Date()), "yyyy-MM-dd"));
					faxiSlFundIntent.setIncomeMoney(faxiSlFundIntent
							.getIncomeMoney().add(sortoverdueMoney));
					faxiSlFundIntent.setNotMoney(faxiSlFundIntent.getNotMoney()
							.add(sortoverdueMoney));
					faxiSlFundIntent.setPunishDays(days);
					if (faxiSlFundIntent.getIncomeMoney().compareTo(
							new BigDecimal("0")) != 0) {
						slPunishInterestDao.merge(faxiSlFundIntent);

					}
					s.setAccrualMoney(faxiSlFundIntent.getNotMoney());
					slFundIntentDao.merge(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<SlFundIntent> listByPreceptIdAndDate(Long projectId,
			String businessType, Long preceptId, String date, String fundType) {

		return dao.listByPreceptIdAndDate(projectId, businessType, preceptId,
				date, fundType);
	}

	/**
	 * 查询逾期款项
	 */
	@Override
	public List<SlFundIntent> getOverdueProjectId(Long projectId,
			String businessType) {
		return dao.getOverdueProjectId(projectId, businessType);
	}

	/* 逾期项目管理
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService#listOverDueByBidPlanIdAndpayintentPeriod(com.zhiwei.core.web.paging.PagingBean, java.util.Map)
	 */
	@Override
	public List<SlFundIntentPeriod> listOverDueByBidPlanIdAndpayintentPeriod(
			PagingBean pb, Map<String, String> map) {
		List<SlFundIntentPeriod> list = bpFundIntentDao.listOverDueByBidPlanIdAndpayintentPeriod(pb, map);
		int j = 0;
		if (null != pb) {
			for (SlFundIntentPeriod l : list) {
				List<BpFundIntent> bpfundlist = bpFundIntentDao
						.getByBidPlanIdAndIntentDate(l.getPlanId(), l
								.getIntentDate(), null);
				l.initSlFundIntentPeriod(bpfundlist);
				j++;
			}
		}
		return list;
	}

	@Override
	public List<SlFundIntent> listbyOwe(String businessType, Long projectId,String fundType) {
		return dao.listbyOwe(businessType, projectId, fundType);
	}

	@Override
	public void projectList(PageBean<SlFundIntent> pageBean,
			Map<String, String> map) {
		// TODO Auto-generated method stub
		dao.listbyLedgerNew(pageBean, map);
	}

	@Override
	public List<SlFundIntent> getComplexList(String projectId,String payintentPeriod, String businessType) {
		return dao.getComplexList(projectId,payintentPeriod,businessType);
	}

	@Override
	public List<SlFundIntentPeriod> listexcelRepayInfo(Map<String, String> map) {
		return dao.listexcelRepayInfo(map);
	}
}