package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.impl;


import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterestDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmOrderInfoDao;
import com.zhiwei.credit.dao.creditFlow.materials.OurProcreditMaterialsEnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.materials.SlProcreditMaterialsDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderAssignInterest;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderInfo;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmOrderInfoService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.flow.JbpmService;

/**
 * 
 * @author 
 *
 */
public class PlMmOrderInfoServiceImpl extends BaseServiceImpl<PlMmOrderInfo> implements PlMmOrderInfoService{
	@SuppressWarnings("unused")
	private PlMmOrderInfoDao dao;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private SlFundIntentService slFundIntentService;
	
	
	@Resource
	private ProDefinitionDao proDefinitionDao;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private PlManageMoneyPlanService plManageMoneyPlanService;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private PlManageMoneyPlanBuyinfoService PlManageMoneyPlanBuyinfoService;
	@Resource
	private JbpmService jbpmService;
	@Resource(name = "flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;
	@Resource
	private PlMmOrderAssignInterestDao plMmOrderAssignInterestDao;
	@Resource
	private SlProcreditMaterialsDao slProcreditMaterialsDao;
	@Resource
	private OurProcreditMaterialsEnterpriseDao ourProcreditMaterialsEnterpriseDao;
	
	
	public PlMmOrderInfoServiceImpl(PlMmOrderInfoDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public boolean startInvestFlow(HttpServletRequest request) {
		try {
			String mmplanId = request.getParameter("mmplanId");
			String mmplanName = request.getParameter("mmplanName");
			String investPersonId = request.getParameter("plManageMoneyPlanBuyinfo.investPersonId");
			String investPersonName = request.getParameter("plManageMoneyPlanBuyinfo.investPersonName");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			PlManageMoneyPlan plManageMoneyPlan = plManageMoneyPlanService.get(Long.valueOf(mmplanId));
			
			PlMmOrderInfo plMmOrderInfo = new PlMmOrderInfo();
			BeanUtil.populateEntity(request, plMmOrderInfo, "plMmOrderInfo");
			plMmOrderInfo.setMmplanId(mmplanId);
			plMmOrderInfo.setMmplanName(mmplanName);
			plMmOrderInfo.setInvestPersonId(investPersonId);
			plMmOrderInfo.setInvestPersonName(investPersonName);
			plMmOrderInfo.setMmplanNumber(plManageMoneyPlan.getMmNumber());
			plMmOrderInfo.setBuyDate(new Date());
			plMmOrderInfo.setInvestDue(plManageMoneyPlan.getInvestlimit().toString());
			plMmOrderInfo.setRate(plManageMoneyPlan.getYeaRate());
			plMmOrderInfo.setRecordNumber("LC"+ContextUtil.createRuestNumber());
			PlMmOrderInfo _plMmOrderInfo = dao.save(plMmOrderInfo);
			
			AppUser user = ContextUtil.getCurrentUser();
			FlowRunInfo newFlowRunInfo = new FlowRunInfo();
			ProDefinition pdf = null;
		//	String isGroupVersion = AppUtil.getSystemIsGroupVersion();
			pdf = proDefinitionDao.getByKey("FinanceProductBuyFlow");
			
			Long branchCompanyId = pdf.getBranchCompanyId();
			Map<String, Object> newVars = new HashMap<String, Object>();
			
			
			newVars.put("projectId", _plMmOrderInfo.getId());
			newVars.put("businessType", "invest");
			newVars.put("customerName", user.getFullname());
			newVars.put("projectNumber", plManageMoneyPlan.getMmNumber());
			
			newVars.put("plMmOrderInfoId", _plMmOrderInfo.getId());
			newVars.put("mmplanId", mmplanId);
			newVars.put("investPersonId", investPersonId);
			newVars.put("branchCompanyId", branchCompanyId==null?"1":branchCompanyId);
			newFlowRunInfo.getVariables().putAll(newVars);
			newFlowRunInfo.setBusMap(newVars);
			newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
			
			String flowName = format.format(new Date());
			flowName += mmplanName;
			flowName += investPersonName;
			flowName += "投资流程";
			newFlowRunInfo.setFlowSubject(flowName);
			ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
			JsonUtil.responseJsonString("{success:true,runId:'"+run.getRunId()+"'}");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
		
	}

	@Override
	public Integer investFlowNext(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				//下一节点名称
				String transitionName = flowRunInfo.getTransitionName();
				//如果下一节点名称不为空
				if (transitionName != null && !"".equals(transitionName)) {
					//new 一个流程信息map
					Map<String, Object> vars = new HashMap<String, Object>();
					
					String thisTaskName = flowRunInfo.getRequest().getParameter("thisTaskName");
					
					if ("financeConfirm".equals(thisTaskName)&&(transitionName.contains("债权")||transitionName.contains("完成"))) {//如果所提交的节点为完成或者债权匹配，侧进行一系列的计算
						
						try{
							PlMmOrderInfo plMmOrderInfo = new PlMmOrderInfo();
							BeanUtil.populateEntity(flowRunInfo.getRequest(), plMmOrderInfo, "plMmOrderInfo");
							PlMmOrderInfo orgPlMmOrderInfo=dao.get(plMmOrderInfo.getId());
							BeanUtil.copyNotNullProperties(orgPlMmOrderInfo, plMmOrderInfo);
							dao.save(orgPlMmOrderInfo);
						}catch(Exception ex){
							logger.error("保存出错");
						}
						
						String id = flowRunInfo.getRequest().getParameter("plMmOrderInfo.id");
						PlMmOrderInfo plMmOrderInfo = dao.get(Long.valueOf(id));
						//购买理财--扣款方法
						boolean flag = PlManageMoneyPlanBuyinfoService.createOrder(plMmOrderInfo);
						if(!flag){
							return 0;
						}
						
						//保存投资人款项计划表--s
						plMmOrderInfo = dao.get(Long.valueOf(id));
						String jsonStr = flowRunInfo.getRequest().getParameter("plMmOrderAssigninterest_JSONSTR");
						if(jsonStr!=null&&!"".equals(jsonStr)){
							String[] plMmOrderAssigninterestArr = jsonStr.split("@");
							for (int i = 0; i < plMmOrderAssigninterestArr.length; i++) {
								String str = plMmOrderAssigninterestArr[i];
								//反序列化
								JSONParser parser = new JSONParser(new StringReader(str));
								PlMmOrderAssignInterest plMmOrderAssignInterest = (PlMmOrderAssignInterest) JSONMapper.toJava(parser.nextValue(), PlMmOrderAssignInterest.class);
								
								plMmOrderAssignInterest.setOrderId(Long.valueOf(plMmOrderInfo.getOrderId()));
								plMmOrderAssignInterestDao.save(plMmOrderAssignInterest);

							}
						}
						//保存投资人款项计划表--e
						
					}
					
					//如果下一节点为终止或结束
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						//设置流程状态
						flowRunInfo.setStop(true);
						//设置基本流程信息状态值 
					} else {//下一节点不为终止或结束
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								/*----------------------正式开始针对每一个节点保存信息-----------------------------*/
								//VM传来的当前节点名称或标记
								
								try{
									PlMmOrderInfo plMmOrderInfo = new PlMmOrderInfo();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), plMmOrderInfo, "plMmOrderInfo");
									PlMmOrderInfo orgPlMmOrderInfo=dao.get(plMmOrderInfo.getId());
									String orderId=orgPlMmOrderInfo.getOrderId();
									BeanUtil.copyNotNullProperties(orgPlMmOrderInfo, plMmOrderInfo);
									orgPlMmOrderInfo.setOrderId(orderId);
									dao.save(orgPlMmOrderInfo);
							
								
									String jsonStr = flowRunInfo.getRequest().getParameter("ourprocreditmaterials_JSONSTR");
									System.out.println(jsonStr);
									if(jsonStr!=null&&!"".equals(jsonStr)){
										String[] ourProcreditMaterialsEnterpriseArr = jsonStr.split("@");
										for (int i = 0; i < ourProcreditMaterialsEnterpriseArr.length; i++) {
											String str = ourProcreditMaterialsEnterpriseArr[i];
											//反序列化
											JSONParser parser = new JSONParser(new StringReader(str));
											OurProcreditMaterialsEnterprise temp = (OurProcreditMaterialsEnterprise) JSONMapper.toJava(parser.nextValue(), OurProcreditMaterialsEnterprise.class);
											
											OurProcreditMaterialsEnterprise _temp  = ourProcreditMaterialsEnterpriseDao.get(temp.getProjectId());
											
											temp.setProjectId(plMmOrderInfo.getId());
											if(_temp!=null){
												temp.setParentId(_temp.getParentId());
											}
											temp.setOperationTypeKey("FinanceProduct");
											temp.setLeaf(Integer.valueOf(1));
											ourProcreditMaterialsEnterpriseDao.save(temp);
									
										}
									}
								
								}catch(Exception ex){
									ex.printStackTrace();
									logger.error("保存出错");
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

	@Override
	public PlMmOrderInfo getByOrderId(Long id) {
		String hql  = "from PlMmOrderInfo as p where p.orderId = ?";
		 List<PlMmOrderInfo> list = dao.findByHql(hql, new Object[]{id.toString()});
		 if(list!=null&&list.size()>0){
			 return list.get(0);
		 }
		 return null;
	}


}