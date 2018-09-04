package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import com.hurong.core.util.BeanUtil;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObSystemAccountDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlEarlyRedemptionDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOrDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlEarlyRedemption;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderChildrenOr;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlEarlyRedemptionService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.TaskService;

/**
 * 
 * @author 
 *
 */
public class PlEarlyRedemptionServiceImpl extends BaseServiceImpl<PlEarlyRedemption> implements PlEarlyRedemptionService{
	@SuppressWarnings("unused")
	private PlEarlyRedemptionDao dao;
	@Resource
	private PlManageMoneyPlanBuyinfoDao plManageMoneyPlanBuyinfoDao;
	@Resource
	private PlManageMoneyPlanDao plManageMoneyPlanDao;
	@Resource
	private ProDefinitionDao proDefinitionDao;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private TaskService flowTaskService;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private PlMmOrderAssignInterestDao plMmOrderAssignInterestDao;
	@Resource
	private ObSystemAccountDao obSystemAccountDao;
	@Resource
	private PlMmOrderChildrenOrDao plMmOrderChildrenOrDao;
	@Resource
	private PlMmObligatoryRightChildrenDao plMmObligatoryRightChildrenDao;
	
	@Resource
	private PlManageMoneyPlanBuyinfoService plManageMoneyPlanBuyinfoService;
	public PlEarlyRedemptionServiceImpl(PlEarlyRedemptionDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public String startEarlyRedemptionProcess(Long orderId) {
		String customerName = "";
		PlManageMoneyPlanBuyinfo buyinfo = this.plManageMoneyPlanBuyinfoDao.get(orderId);
		buyinfo.setIsOtherFlow(Short.valueOf("1"));
		AppUser user = ContextUtil.getCurrentUser();
		PlEarlyRedemption plEarlyRedemption = new PlEarlyRedemption();
		plEarlyRedemption.setPlManageMoneyPlanBuyinfo(buyinfo);
		plEarlyRedemption.setCheckStatus(Short.valueOf("0"));
		plEarlyRedemption.setCreateDate(new Date());
		plEarlyRedemption.setCreator(user.getFullname());
		plEarlyRedemption.setKeystr(buyinfo.getKeystr());
		this.dao.save(plEarlyRedemption);
		this.plManageMoneyPlanBuyinfoDao.merge(buyinfo);
		FlowRunInfo newFlowRunInfo = new FlowRunInfo();
		ProDefinition pdf = null;// 流程定义key值
		// Map<String, String> mapNew =null;
		// 不能从session中获取companyId,总公司的人员为分公司启动展期流程的时候,这样获取启动的却是总公司的展期流程.
		// 而是从项目表中获取对应的companyId
		// Long companyId=ContextUtil.getLoginCompanyId();
		pdf = proDefinitionDao.getByProcessName("earlyRedemptionFlow");

		
		Map<String, Object> newVars = new HashMap<String, Object>();
		newVars.put("projectId", plEarlyRedemption.getEarlyRedemptionId());
		newVars.put("orderId", buyinfo.getOrderId());
		newVars.put("businessType", "earlyRedemption");
		newVars.put("customerName", buyinfo.getInvestPersonName()); //
		newVars.put("projectNumber", buyinfo.getPlManageMoneyPlan().getMmNumber()); //
		
		newFlowRunInfo.getVariables().putAll(newVars);
		newFlowRunInfo.setBusMap(newVars);
		newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
		newFlowRunInfo.setFlowSubject(buyinfo.getPlManageMoneyPlan().getMmName() + "-"+ buyinfo.getPlManageMoneyPlan().getMmNumber()+"-"+buyinfo.getInvestPersonName());
		ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
		String str = "";
		if (run != null && run.getPiId() != null) {
			str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),user.getUserId().toString(), buyinfo.getPlManageMoneyPlan().getMmName());
		
		}
		return str;
	}

	@Override
	public Integer updateRedemptionInfoNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String earlyRedemptionId=flowRunInfo.getRequest().getParameter("plEarlyRedemption.earlyRedemptionId");
				PlEarlyRedemption redemption=this.dao.get(Long.valueOf(earlyRedemptionId));
				PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoDao.get(redemption.getPlManageMoneyPlanBuyinfo().getOrderId());
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						redemption.setCheckStatus(Short.valueOf("3"));
						plManageMoneyPlanBuyinfo.setState(Short.valueOf("2"));
						dao.merge(redemption);
						plManageMoneyPlanBuyinfoDao.merge(plManageMoneyPlanBuyinfo);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
								PlEarlyRedemption plEarlyRedemption=new PlEarlyRedemption();
								plEarlyRedemption.setPlManageMoneyPlanBuyinfo(redemption.getPlManageMoneyPlanBuyinfo());
								BeanUtil.populateEntity(flowRunInfo.getRequest(), plEarlyRedemption, "plEarlyRedemption");
								BeanUtil.copyNotNullProperties(redemption, plEarlyRedemption);
								
								//PlManageMoneyPlanBuyinfo plManageMoneyPlanBuyinfo=plManageMoneyPlanBuyinfoDao.get(redemption.getPlManageMoneyPlanBuyinfo().getOrderId());
								String fundsJson=flowRunInfo.getRequest().getParameter("fundsJson");
								if(null!=fundsJson && !fundsJson.equals("")){
									List<PlMmOrderAssignInterest> list=plMmOrderAssignInterestDao.listByEarlyRedemptionId(redemption.getEarlyRedemptionId());
									for(PlMmOrderAssignInterest a:list){
										if(a.getAfterMoney().compareTo(new BigDecimal(0))==0){
											plMmOrderAssignInterestDao.remove(a);
										}
									}
									String[] fundsJsonArr = fundsJson.split("@");
									
									for(int i=0; i<fundsJsonArr.length; i++) {
										String str = fundsJsonArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										PlMmOrderAssignInterest plMmOrderAssignInterest = (PlMmOrderAssignInterest)JSONMapper.toJava(parser.nextValue(),PlMmOrderAssignInterest.class);
										plMmOrderAssignInterest.setAfterMoney(new BigDecimal(0));
										plMmOrderAssignInterest.setInvestPersonId(plManageMoneyPlanBuyinfo.getInvestPersonId());
										plMmOrderAssignInterest.setInvestPersonName(plManageMoneyPlanBuyinfo.getInvestPersonName());
										plMmOrderAssignInterest.setIsCheck(Short.valueOf("1"));
										plMmOrderAssignInterest.setIsValid(Short.valueOf("0"));
										plMmOrderAssignInterest.setKeystr("mmproduce");
										plMmOrderAssignInterest.setMmName(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmName());
										plMmOrderAssignInterest.setMmplanId(plManageMoneyPlanBuyinfo.getPlManageMoneyPlan().getMmplanId());
										plMmOrderAssignInterestDao.save(plMmOrderAssignInterest);
										
									}
								}
								if(transitionName.contains("完成")){
									List<PlMmOrderAssignInterest> list=plMmOrderAssignInterestDao.listByEarlyDate(">'"+sd.format(redemption.getEarlyDate())+"'", redemption.getPlManageMoneyPlanBuyinfo().getOrderId(),"('loanInterest','principalRepayment')",redemption.getEarlyRedemptionId());
									for(PlMmOrderAssignInterest ai:list){
											ai.setIsValid(Short.valueOf("1"));
											ai.setIsCheck(Short.valueOf("1"));
											plMmOrderAssignInterestDao.merge(ai);
									}
									
									List<PlMmOrderAssignInterest> elist=plMmOrderAssignInterestDao.listByEarlyRedemptionId(redemption.getEarlyRedemptionId());
									for(PlMmOrderAssignInterest a:elist){
										a.setIsCheck(Short.valueOf("0"));
										plMmOrderAssignInterestDao.merge(a);
									}

									//债权匹配修改
									BigDecimal money=plManageMoneyPlanBuyinfo.getCurrentGetedMoney();
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
								//	List<PlMmOrderChildrenOr> clist= plMmOrderChildrenOrDao.listbyorderid(redemption.getPlManageMoneyPlanBuyinfo().getOrderId(),sdf.format(redemption.getEarlyDate()));
								
									
									//与债权的匹配日最大不能超过提前支取日
									plManageMoneyPlanBuyinfoService.gcalculateEarlyOutOrmatching(plEarlyRedemption);
									
									/*for(PlMmOrderChildrenOr c:clist){
										c.setMatchingEndDate(redemption.getEarlyDate());
										int matchingLimit=DateUtil.getDaysBetweenDate(sdf.format(c.getMatchingStartDate()),sdf.format(c.getMatchingEndDate()));
										if(matchingLimit<=0){
											plMmOrderChildrenOrDao.remove(c);
											
										}else{
											c.setMatchingLimit(matchingLimit);
											BigDecimal thismatchgetMoney=c.getMatchingMoney().multiply(c.getChildrenOrDayRate().multiply(new BigDecimal(matchingLimit))).divide(new BigDecimal(100)) ;
											money=money.subtract(c.getMatchingGetMoney()).add(thismatchgetMoney);
											c.setMatchingGetMoney(thismatchgetMoney);
											plMmOrderChildrenOrDao.save(c);
										}
									}*/
									
								//	plManageMoneyPlanBuyinfo.setCurrentGetedMoney(money);
									plManageMoneyPlanBuyinfo.setEarlierOutDate(redemption.getEarlyDate());
									plManageMoneyPlanBuyinfo.setIsOtherFlow(Short.valueOf("0"));
									
									redemption.setCheckStatus(Short.valueOf("1"));
								}
								plManageMoneyPlanBuyinfoDao.merge(plManageMoneyPlanBuyinfo);
								this.dao.merge(redemption);
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
	public List<PlEarlyRedemption> listByOrderId(Long orderId) {
		
		return dao.listByOrderId(orderId);
	}


}