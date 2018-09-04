package com.zhiwei.credit.service.creditFlow.smallLoan.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hurong.core.util.BeanUtil;
import com.hurong.core.util.DateUtil;
import com.hurong.credit.util.Common;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.sun.net.httpserver.Authenticator.Success;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidPlanDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.BpFundProjectDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.SettlementInfoDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementInfo;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.fund.project.SettlementReviewerPayService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.TaskService;

/**
 * 
 * @author 
 *
 */
public class SlEarlyRepaymentRecordServiceImpl extends BaseServiceImpl<SlEarlyRepaymentRecord> implements SlEarlyRepaymentRecordService{
	@SuppressWarnings("unused")
	private SlEarlyRepaymentRecordDao dao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private PlBidPlanDao plBidPlanDao;
	@Resource
	private BpFundProjectDao bpFundProjectDao;
	@Resource
	private BpFundIntentDao bpFundIntentDao;
	@Resource
	private SlFundIntentDao slFundIntentDao;
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
	private SettlementInfoDao settlementInfoDao;
	public SlEarlyRepaymentRecordServiceImpl(SlEarlyRepaymentRecordDao dao) {
		super(dao);
		this.dao=dao;
	}
	public List<SlEarlyRepaymentRecord> getByProjectId(Long projectId) {
		return dao.getByProjectId(projectId);
	}
	@Override
	public List<SlEarlyRepaymentRecord> listByProIdAndType(Long projectId,
			String businessType) {
		
		return dao.listByProIdAndType(projectId, businessType);
	}
	@Override
	public Integer updateEarlyProjectInfoNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				SlEarlyRepaymentRecord slEarlyRepaymentRecord=new SlEarlyRepaymentRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), slEarlyRepaymentRecord,"slEarlyRepaymentRecord");
				SlEarlyRepaymentRecord record=dao.get(slEarlyRepaymentRecord.getSlEarlyRepaymentId());
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						record.setCheckStatus(3);
						dao.merge(record);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								String bidPlanId=flowRunInfo.getRequest().getParameter("bidPlanId");
								String fundsJson=flowRunInfo.getRequest().getParameter("fundsJson");
								String isCheck=flowRunInfo.getRequest().getParameter("isCheck");
								String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
								PlBidPlan plan=plBidPlanDao.get(Long.valueOf(bidPlanId));
								BpFundProject fundProject = bpFundProjectDao.get(Long.valueOf(fundProjectId));
								
								BeanUtil.copyNotNullProperties(record, slEarlyRepaymentRecord);
								
								if(null!=fundsJson && !fundsJson.equals("")){
									List<BpFundIntent> list=bpFundIntentDao.listbySlEarlyRepaymentId(Long.valueOf(bidPlanId),slEarlyRepaymentRecord.getSlEarlyRepaymentId());
									for(BpFundIntent f:list){
										if(f.getAfterMoney().compareTo(new BigDecimal(0))==0){
											bpFundIntentDao.remove(f);
										}
									}
									//投资人的放款收息表
									bpFundIntentDao.saveCommonFundIntent(fundsJson, plan, fundProject, Short.valueOf(isCheck));
								}
								if(transitionName.equals("完成")){
									List<BpFundIntent> list=bpFundIntentDao.listbySlEarlyRepaymentId(Long.valueOf(bidPlanId),slEarlyRepaymentRecord.getSlEarlyRepaymentId());
									for(BpFundIntent f:list){
										f.setIsCheck(Short.valueOf("0"));
										bpFundIntentDao.merge(f);
									} 
									List<BpFundIntent> flist=bpFundIntentDao.listByBidPlanId(Long.valueOf(bidPlanId));
									for(BpFundIntent bf:flist){
										if(bf.getSlEarlyRepaymentId()==null || !bf.getSlEarlyRepaymentId().toString().equals(slEarlyRepaymentRecord.getSlEarlyRepaymentId().toString())){
											if(bf.getIntentDate().compareTo(slEarlyRepaymentRecord.getEarlyDate())>0){
												bf.setIsValid(Short.valueOf("1"));
												bpFundIntentDao.merge(bf);
											}
										}
									}
									plan.setIsOtherFlow(Short.valueOf("0"));
									plBidPlanDao.merge(plan);
									record.setCheckStatus(1);
								}
								dao.merge(record);
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
	@Override
	public List allList(HttpServletRequest request, Integer start, Integer limit) {
		
		return dao.allList(request, start, limit);
	}
	@Override
	public Long allListCount(HttpServletRequest request) {
		
		return dao.allListCount(request);
	}
	@Override
	public String startSlEarlyRepaymentProcess(Long projectId) {
		String customerName = "";
		BpFundProject project = this.bpFundProjectDao.get(projectId);
		project.setIsOtherFlow(Short.valueOf("2"));
		AppUser user = ContextUtil.getCurrentUser();
		SlEarlyRepaymentRecord slEarlyRepaymentRecord = new SlEarlyRepaymentRecord();
		slEarlyRepaymentRecord.setProjectId(project.getId());
		slEarlyRepaymentRecord.setBusinessType(project.getBusinessType());
		slEarlyRepaymentRecord.setCheckStatus(0);
		slEarlyRepaymentRecord.setOpTime(new Date());
		slEarlyRepaymentRecord.setCreator(user.getFullname());
		this.dao.save(slEarlyRepaymentRecord);
		this.bpFundProjectDao.merge(project);
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
			pdf = proDefinitionDao.getByProcessName("repaymentAheadOfTimeFlow_"
					+ org.getKey());
		} else {
			pdf = proDefinitionDao.getByProcessName("repaymentAheadOfTimeFlow");
		}

		List<ProcessRun> processRunList = processRunDao.getByProcessNameProjectId(projectId,project.getBusinessType(), project.getFlowType());
		if (processRunList != null && processRunList.size() != 0) {
			customerName = processRunList.get(0).getCustomerName();
		}
		// mapNew =
		// this.getProjectInfo(project,"repaymentAheadOfTimeFlow_"+org.getKey());
		Long branchCompanyId = pdf.getBranchCompanyId();
		Map<String, Object> newVars = new HashMap<String, Object>();
		newVars.put("projectId", project.getProjectId());
		newVars.put("fundProjectId", project.getId());
		newVars.put("slEarlyRepaymentId", slEarlyRepaymentRecord
				.getSlEarlyRepaymentId());
		newVars.put("oppositeType", project.getOppositeType());
		newVars.put("businessType", project.getBusinessType());
		newVars.put("customerName", customerName); //
		newVars.put("projectNumber", project.getProjectNumber()); //
		
		newFlowRunInfo.getVariables().putAll(newVars);
		newFlowRunInfo.setBusMap(newVars);
		newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
		newFlowRunInfo.setFlowSubject(project.getProjectName() + "-"
				+ project.getProjectNumber());
		ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
		String str = "";
		if (run != null && run.getPiId() != null) {
			str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),user.getUserId().toString(), project.getProjectName());
		
		}
		return str;
	}
	
	public Integer savePrepaymentInfoNextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String slEarlyRepaymentId=flowRunInfo.getRequest().getParameter("slEarlyRepaymentId");
				SlEarlyRepaymentRecord record=dao.get(Long.valueOf(slEarlyRepaymentId));
				String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
				BpFundProject project=bpFundProjectDao.get(Long.valueOf(fundProjectId));
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						record.setCheckStatus(3);
						dao.merge(record);
						project.setIsOtherFlow(Short.valueOf("0"));
						bpFundProjectDao.merge(project);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								Long taskSequence=creditProjectService.getOrder(currentForm.getProcessRun().getProDefinition().getDeployId(), currentForm.getActivityName());
								
								SlEarlyRepaymentRecord slEarlyRepaymentRecord=new SlEarlyRepaymentRecord();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slEarlyRepaymentRecord, "slEarlyRepaymentRecord");
								BeanUtil.copyNotNullProperties(record, slEarlyRepaymentRecord);
								if(null!=taskSequence && taskSequence>=2000){
									record.setCheckStatus(1);
									List<SlFundIntent> slist=slFundIntentDao.listByDateAndEarlyId(project.getProjectId(), project.getBusinessType(), sd.format(slEarlyRepaymentRecord.getEarlyDate()), record.getSlEarlyRepaymentId(), project.getId());
									for(SlFundIntent s:slist){
										s.setIsValid(Short.valueOf("1"));
										s.setIsCheck(Short.valueOf("1"));
										slFundIntentDao.merge(s);
									}
								}
								//保存款项信息
								String fundIntentJsonData=flowRunInfo.getRequest().getParameter("earlyFundIntentJsonData");
								if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
									List<SlFundIntent> oldList = slFundIntentDao.getlistbyslEarlyRepaymentId(record.getSlEarlyRepaymentId(), project.getBusinessType(), project.getProjectId());
									for (SlFundIntent sfi : oldList) {
										if (sfi.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
											slFundIntentDao.remove(sfi);
										}
									}
									String[] shareequityArr = fundIntentJsonData.split("@");
									for (int i = 0; i < shareequityArr.length; i++) {
										String fundIntentstr = shareequityArr[i];
										JSONParser parser = new JSONParser(new StringReader(fundIntentstr));
								
										SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper.toJava(parser.nextValue(), SlFundIntent.class);
										SlFundIntent1.setProjectId(project.getProjectId());
										SlFundIntent1.setProjectName(project.getProjectName());
										SlFundIntent1.setProjectNumber(project.getProjectNumber());

										if (null == SlFundIntent1.getFundIntentId()) {

											BigDecimal lin = new BigDecimal(0.00);
											if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
												SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
											} else {
												SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
											}
											SlFundIntent1.setAfterMoney(new BigDecimal(0));
											SlFundIntent1.setAccrualMoney(new BigDecimal(0));
											SlFundIntent1.setFlatMoney(new BigDecimal(0));
											Short isvalid = 0;
											SlFundIntent1.setIsValid(isvalid);
											/*if (SlFundIntent1.getFundType().equals("principalLending")) {
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
											} else {*/
											SlFundIntent1.setIsCheck((null!=taskSequence && taskSequence>=2000)?Short.valueOf("0"):Short.valueOf("1"));
											//}
											SlFundIntent1.setBusinessType(project.getBusinessType());
											SlFundIntent1.setCompanyId(project.getCompanyId());
											slFundIntentDao.save(SlFundIntent1);
										} else {
											BigDecimal lin = new BigDecimal(0.00);
											if (SlFundIntent1.getIncomeMoney().compareTo(lin) == 0) {
												SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
											} else {
												SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
											}
											SlFundIntent1.setBusinessType(project.getBusinessType());
											SlFundIntent1.setCompanyId(project.getCompanyId());
											/*if (SlFundIntent1.getFundType().equals(
													"principalLending")) {
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
											} else {*/
											SlFundIntent1.setIsCheck((null!=taskSequence && taskSequence>=2000)?Short.valueOf("0"):Short.valueOf("1"));
											//}
											SlFundIntent slFundIntent2 = slFundIntentDao.get(SlFundIntent1.getFundIntentId());
											BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

											slFundIntentDao.merge(slFundIntent2);

										}
										
									}
								}
								
								if(transitionName.contains("完成")){
									project.setIsOtherFlow(Short.valueOf("0"));
								}
								bpFundProjectDao.merge(project);
								dao.merge(record);
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
	
	@Resource
	private SettlementReviewerPayService settlementReviewerPayService;
	
	public String investSettle(String id,String organId){
		SettlementReviewerPay settle = new SettlementReviewerPay();
		SettlementReviewerPay settle1 = settlementReviewerPayService.get(Long.valueOf(id));
		String str = "";
		Boolean flag = true;
		Organization o = organizationDao.get(Long.valueOf(settle1.getCollectionDepartment()));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String num = o.getOrgName()+"_提成结算"+sf.format(new Date());
		AppUser user = ContextUtil.getCurrentUser();//获取当前登录用户
//		settlementReviewerPayService.merge(settle);
		QueryFilter filter = new QueryFilter();
		filter.addFilter("Q_orgId_L_EQ", o.getOrgId());
		List<SettlementInfo> infos = settlementInfoDao.getAll(filter);
		
		if(settle1.getFlowType()==null&&settle1.getState()==null){
			settle = settle1;
			settle.setFlowType("InvestSettle");
			settle.setState(Short.valueOf("0"));//0 结算 1 审核 2 支付
			Date startDate = DateUtil.addDaysToDate(new Date(),0);
			if(infos!=null&&infos.size()>0){
				startDate = infos.get(0).getCreateDate();
			}else{
				startDate = DateUtil.addDaysToDate(new Date(),0);
				flag = false;
			}
			settle.setPayStartDate(startDate);
			settle.setProjectNumber(num);
			settle.setCreateDate(new Date());
		}else{
			settle.setFlowType("InvestSettle");
			settle.setCollectionDepartment(settle1.getCollectionDepartment());//部门ID
			settle.setState(Short.valueOf("0"));//0 结算 1 审核 2 支付
			settle.setRemark3(user.getFullname());//当前用户
			Date startDate = settle1.getPayEndDate();
			if(startDate==null){
				if(infos!=null&&infos.size()>0){
					startDate = infos.get(0).getCreateDate();
					flag = false;
				}else{
					startDate = new Date();
				}
			}else{
				startDate = DateUtil.addDaysToDate(settle1.getPayEndDate(),1);
			}
			settle.setPayStartDate(startDate);
			settle.setProjectNumber(num);
			settle.setCreateDate(new Date());
		}
//		if(flag){
			settle = settlementReviewerPayService.save(settle);
			FlowRunInfo newFlowRunInfo = new FlowRunInfo();
			ProDefinition pdf = null;// 流程定义key值
			pdf = proDefinitionDao.getByProcessName("InvestSettle");
			Map<String, Object> newVars = new HashMap<String, Object>();
			newVars.put("projectId", settle.getId());
			newVars.put("businessType", "InvestSettle");
			newVars.put("settlementReviewerPayId", settle.getId());
			newVars.put("organizationId", o.getOrgId());
			newVars.put("type", o.getSettlementType());
			newVars.put("customerName", o.getSettlementType()); //
			newVars.put("projectNumber", num); //
			
			newFlowRunInfo.getVariables().putAll(newVars);
			newFlowRunInfo.setBusMap(newVars);
			newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
			newFlowRunInfo.setFlowSubject(num);
			ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
			if (run != null && run.getPiId() != null) {
				str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),user.getUserId().toString(), num);
			
			}
			return str;
//		}else{
//			return "";
//		}
	}
}