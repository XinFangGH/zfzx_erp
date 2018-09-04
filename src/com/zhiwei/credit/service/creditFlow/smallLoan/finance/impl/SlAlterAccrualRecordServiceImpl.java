package com.zhiwei.credit.service.creditFlow.smallLoan.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
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

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.BpFundProjectDao;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.project.FlLeaseFinanceProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlAlterAccrualRecordDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlAlterAccrualRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.supervise.SlSuperviseRecordService;

/**
 * 
 * @author 
 *
 */
public class SlAlterAccrualRecordServiceImpl extends BaseServiceImpl<SlAlterAccrualRecord> implements SlAlterAccrualRecordService{
	private SlAlterAccrualRecordDao dao;
	@Resource
	private SlSmallloanProjectDao slSmallloanProjectDao;
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlSuperviseRecordService slSuperviseRecordService;
	@Resource
	private FlLeaseFinanceProjectDao flLeaseFinanceProjectDao;
	@Resource
	private BpFundProjectDao bpFundProjectDao;
	public SlAlterAccrualRecordServiceImpl(SlAlterAccrualRecordDao dao) {
		super(dao);
		this.dao=dao;
	}
	public List<SlAlterAccrualRecord> getByProjectId(Long projectId,String businessType) {
		return dao.getByProjectId(projectId,businessType);
	}
	//利率变更审核节点提交任务
	@Override
	public Integer askForAlterAccrualFlowExamineAndVerifyNextStep(FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			}else{
				String projectId = flowRunInfo.getRequest().getParameter("projectId_flow"); // 项目ID
				String slAlterAccrualRecordId = flowRunInfo.getRequest().getParameter("slAlteraccrualRecordId");
				SlSmallloanProject project=this.slSmallloanProjectDao.get(Long.valueOf(projectId));
				String transitionName = flowRunInfo.getTransitionName();
				Map<String, Object> vars = new HashMap<String, Object>();
				if (transitionName != null && !"".equals(transitionName)) {
					if(transitionName.contains("结束")||transitionName.contains("终止")){
						flowRunInfo.setStop(true);
						project.setIsOtherFlow(Short.valueOf("0"));//利率变更流程终止后将项目表中的isOtherFlow字段值修改为0
						slSmallloanProjectDao.save(project);
						//讲利率变更申请时生成的款项计划设置为无效的
						List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentDao.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId), "SmallLoan",Long.valueOf(projectId));
						for (SlFundIntent s : slFundIntentsAllsupervise) {
							s.setIsValid(Short.valueOf("1"));
							s.setIsCheck(Short.valueOf("3"));
							slFundIntentDao.save(s);
						}
						//将利率变更表中利率变更记录状态修改
						SlAlterAccrualRecord sls=dao.get(Long.valueOf(slAlterAccrualRecordId));
						sls.setCheckStatus(6);
						dao.save(sls);
					}else{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "irfApplyForChange");
								if (processForm != null&& processForm.getCreatorId() != null) {
									String creatorId = processForm.getCreatorId().toString();
									vars.put("flowAssignId", creatorId);
								}
							} else {
								//写自己的业务逻辑
							}
						}
					}
					vars.put("auditingResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("贷后监管--利率变更审核提交任务出错"+e.getMessage());
			return 0;
		}
	
	}
	//利率变更审批节点提交任务
	@Override
	public Integer askForAlterAccrualFlowNextExamineAndApproveNextStep(FlowRunInfo flowRunInfo) {
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "irfAuditing");
							if (processForm != null&& processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							//写自己的业务逻辑
							String projectId = flowRunInfo.getRequest().getParameter("projectId_flow"); // 项目ID
							String fundIntentJsonData = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
							String slAlterAccrualRecordId = flowRunInfo.getRequest().getParameter("slAlteraccrualRecordId");
							SlSmallloanProject project=this.slSmallloanProjectDao.get(Long.valueOf(projectId));
							
							String alterpayintentPeriod=flowRunInfo.getRequest().getParameter("alterpayintentPeriod");
							String slSuperviseRecordId=alterpayintentPeriod.substring(0,alterpayintentPeriod.lastIndexOf("."));
							SlAlterAccrualRecord slAlterAccrualRecord_temp =new SlAlterAccrualRecord();
							BeanUtil.populateEntity(flowRunInfo.getRequest(), slAlterAccrualRecord_temp,"slAlterAccrualRecord");
							
							List<SlFundIntent> slist=null;
							int period=slAlterAccrualRecord_temp.getAlterpayintentPeriod()-1;
							if(slSuperviseRecordId.equals("0")){
								project.setAccrualnew(slAlterAccrualRecord_temp.getAccrual());
								Date intentDate=null;
								if(period>0){
					            	List<SlFundIntent> flist=slFundIntentService.getByIntentPeriod(Long.valueOf(projectId), "SmallLoan", "loanInterest", null, period);
					            	if(null!=flist && flist.size()>0){
					            		SlFundIntent sf=flist.get(0);
					            		intentDate=sf.getIntentDate();
					            	}
								}else{
									intentDate=project.getStartDate();
								}
				            	
			     			    slist=slFundIntentService.getListByIntentDate(Long.valueOf(projectId), "SmallLoan", ">'"+intentDate,null);
			     				for(SlFundIntent s:slist){
			     					if(project.getAccrualtype().equals("sameprincipalandInterest")){
			     						if((s.getFundType().equals("principalRepayment") || s.getFundType().equals("loanInterest")) && ((null==s.getSlAlteraccrualRecordId()) || (null!=s.getSlAlteraccrualRecordId()) && !s.getSlAlteraccrualRecordId().toString().equals(slAlterAccrualRecordId))){
			     							s.setIsValid(Short.valueOf("1"));
											s.setIsCheck(Short.valueOf("1"));
											s.setAlteraccrualOperateId(Long.valueOf(slAlterAccrualRecordId));
											slFundIntentService.save(s);
			     						}
			     					}else{
			     						if(s.getFundType().equals("loanInterest") && ((null==s.getSlAlteraccrualRecordId()) || (null!=s.getSlAlteraccrualRecordId()) && !s.getSlAlteraccrualRecordId().toString().equals(slAlterAccrualRecordId))){
			     							s.setIsValid(Short.valueOf("1"));
											s.setIsCheck(Short.valueOf("1"));
											s.setAlteraccrualOperateId(Long.valueOf(slAlterAccrualRecordId));
											slFundIntentService.save(s);
			     						}
		     						}
									
								}
							}else{
								SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
								slSuperviseRecord.setContinuationRateNew(slAlterAccrualRecord_temp.getAccrual());
								slSuperviseRecordService.save(slSuperviseRecord);
								Date intentDate=null;
								if(period>0){
									List<SlFundIntent> flist=slFundIntentService.getByIntentPeriod(Long.valueOf(projectId), "SmallLoan", "loanInterest", Long.valueOf(slSuperviseRecordId), period);
									if(null!=flist && flist.size()>0){
					            		SlFundIntent sf=flist.get(0);
					            		intentDate=sf.getIntentDate();
									}
								}else{
									intentDate=slSuperviseRecord.getStartDate();
								}
				            	
			     			    slist=slFundIntentService.getListByIntentDate(Long.valueOf(projectId), "SmallLoan", ">'"+intentDate,Long.valueOf(slSuperviseRecordId));
			     			   for(SlFundIntent s:slist){
			     					if(slSuperviseRecord.getAccrualtype().equals("sameprincipalandInterest")){
			     						if((s.getFundType().equals("principalRepayment") || s.getFundType().equals("loanInterest")) && ((null==s.getSlAlteraccrualRecordId()) || (null!=s.getSlAlteraccrualRecordId()) && !s.getSlAlteraccrualRecordId().toString().equals(slAlterAccrualRecordId))){
			     							s.setIsValid(Short.valueOf("1"));
											s.setIsCheck(Short.valueOf("1"));
											s.setAlteraccrualOperateId(Long.valueOf(slAlterAccrualRecordId));
											slFundIntentService.save(s);
			     						}
			     					}else{
			     						if(s.getFundType().equals("loanInterest") && ((null==s.getSlAlteraccrualRecordId()) || (null!=s.getSlAlteraccrualRecordId()) && !s.getSlAlteraccrualRecordId().toString().equals(slAlterAccrualRecordId))){
			     							s.setIsValid(Short.valueOf("1"));
											s.setIsCheck(Short.valueOf("1"));
											s.setAlteraccrualOperateId(Long.valueOf(slAlterAccrualRecordId));
											slFundIntentService.save(s);
			     						}
		     						}
									
								}
							}
							
							List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentDao.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId), "SmallLoan",Long.valueOf(projectId));
							for (SlFundIntent s : slFundIntentsAllsupervise) {
								slFundIntentDao.remove(s);
							}
							if(null != fundIntentJsonData && !"".equals(fundIntentJsonData)){
								String[] slFundIentJsonArr = fundIntentJsonData.split("@");
								for(int i=0; i<slFundIentJsonArr.length; i++) {
									String str = slFundIentJsonArr[i];
									JSONParser parser = new JSONParser(new StringReader(str));
									try{
										SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
										slFundIntent.setSlAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId));
										slFundIntent.setProjectId(Long.valueOf(projectId));
										slFundIntent.setBusinessType(project.getBusinessType());
										slFundIntent.setProjectName(project.getProjectName());
										slFundIntent.setProjectNumber(project.getProjectNumber());
										slFundIntent.setCompanyId(project.getCompanyId());
										slFundIntent.setIsValid(Short.valueOf("0"));
										BigDecimal lin = new BigDecimal(0.00);
										if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
								        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
								        }else{
								        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
								        	
								        }
										slFundIntent.setAfterMoney(new BigDecimal(0));
										slFundIntent.setAccrualMoney(new BigDecimal(0));
										slFundIntent.setFlatMoney(new BigDecimal(0));
										slFundIntent.setIsCheck(Short.valueOf("0"));
										if(null==slFundIntent.getFundIntentId()){
											slFundIntentDao.save(slFundIntent);
										}else{
											SlFundIntent orgSlFundIntent=slFundIntentDao.get(slFundIntent.getFundIntentId());
											BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
											slFundIntentDao.save(orgSlFundIntent);
										}
									} catch(Exception e) {
										e.printStackTrace();
										logger.error("SlSuperviseRecordAction:款项信息保存出错"+e.getMessage());
									}
								}
							}
							
							//项目表新添字段，当利率变更流程走完后将项目表里面的isOtherFlow字段值改为1
							
							project.setIsOtherFlow(Short.valueOf("0"));
							slSmallloanProjectDao.save(project);
							//将利率变更表中利率变更记录状态修改
							SlAlterAccrualRecord sls=dao.get(Long.valueOf(slAlterAccrualRecordId));
							sls.setCheckStatus(5);
							dao.save(sls);
							
						}
					}
					//vars.put("auditingResult", transitionName);
					vars.put("approveResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("贷后监管--利率变更审批提交任务出错"+e.getMessage());
			return 0;
		}
	}
	//利率变更审查节点提交任务
	@Override
	public Integer askForAlterAccrualFlowNextExamineAndCheckNextStep(FlowRunInfo flowRunInfo) {
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "irfApplyForChange");
							if (processForm != null&& processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							//写自己的业务逻辑
							
						}
					}
					vars.put("examineResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("贷后监管--利率变更审查提交任务出错"+e.getMessage());
			return 0;
		}
	}
	//利率变更申请节点提交任务方法
	@Override
	public Integer askForAlterAccrualProjectFlowNextStep(FlowRunInfo flowRunInfo) {

		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String slAlterAccrualRecordId = flowRunInfo.getRequest().getParameter("slAlteraccrualRecordId");
				SlAlterAccrualRecord sls=dao.get(Long.valueOf(slAlterAccrualRecordId));
				BpFundProject project=bpFundProjectDao.get(sls.getProjectId());
				SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						sls.setCheckStatus(3);
						dao.merge(sls);
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
								SlAlterAccrualRecord slAlterAccrualRecord_temp =new SlAlterAccrualRecord();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slAlterAccrualRecord_temp,"slAlterAccrualRecord");
								BeanUtil.copyNotNullProperties(sls, slAlterAccrualRecord_temp);
								
								//保存款项信息（利率变更新重新计算利率）开始
								String slFundIentJson = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
								
								if(null != slFundIentJson && !"".equals(slFundIentJson)){
									List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId), "SmallLoan",project.getProjectId());
									for (SlFundIntent s : slFundIntentsAllsupervise) {
										if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
											slFundIntentService.remove(s);
										}
									}
									String[] slFundIentJsonArr = slFundIentJson.split("@");
									for(int i=0; i<slFundIentJsonArr.length; i++) {
										String str = slFundIentJsonArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										try{
											SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
											slFundIntent.setProjectId(project.getProjectId());
											slFundIntent.setBusinessType(project.getBusinessType());
											slFundIntent.setProjectName(project.getProjectName());
											slFundIntent.setProjectNumber(project.getProjectNumber());
											slFundIntent.setCompanyId(project.getCompanyId());
											slFundIntent.setIsValid(Short.valueOf("0"));
											BigDecimal lin = new BigDecimal(0.00);
											if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
									        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
									        }else{
									        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
									        	
									        }
											slFundIntent.setAfterMoney(new BigDecimal(0));
											slFundIntent.setAccrualMoney(new BigDecimal(0));
											slFundIntent.setFlatMoney(new BigDecimal(0));
											slFundIntent.setIsCheck(Short.valueOf("1"));
											if(null==slFundIntent.getFundIntentId()){
												slFundIntentService.save(slFundIntent);
											}else{
												SlFundIntent orgSlFundIntent=slFundIntentService.get(slFundIntent.getFundIntentId());
												BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
												slFundIntentService.save(orgSlFundIntent);
											}
										} catch(Exception e) {
											e.printStackTrace();
											logger.error("SlSuperviseRecordAction:款项信息保存出错"+e.getMessage());
										}
									}
								}
								if(transitionName.equals("完成")){
									List<SlFundIntent> list=new ArrayList<SlFundIntent>();
									if(project.getAccrualtype().equals("sameprincipalandInterest")){
										list=slFundIntentDao.listByAlelrtDate(project.getProjectId(), project.getBusinessType(), sd.format(sls.getAlelrtDate()), "('loanInterest','principalRepayment')", project.getId(), sls.getSlAlteraccrualRecordId());
										
									}else{
										list=slFundIntentDao.listByAlelrtDate(project.getProjectId(), project.getBusinessType(), sd.format(sls.getAlelrtDate()), "('loanInterest')", project.getId(), sls.getSlAlteraccrualRecordId());
									}
									if(null!=list && list.size()>0){
										for(SlFundIntent f:list){
											f.setIsValid(Short.valueOf("1"));
											f.setIsCheck(Short.valueOf("1"));
											slFundIntentDao.merge(f);
										}
									}
									List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId), "SmallLoan",project.getProjectId());
									for(SlFundIntent s:slFundIntentsAllsupervise){
										s.setIsCheck(Short.valueOf("0"));
										s.setIsValid(Short.valueOf("0"));
										slFundIntentDao.merge(s);
									}
									sls.setCheckStatus(1);
									project.setIsOtherFlow(Short.valueOf("0"));
									bpFundProjectDao.merge(project);
								}
								dao.merge(sls);
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
	
	//利率变更申请节点提交任务方法
	@Override
	public Integer flSaveAlterAccrualProjectFlowNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId = flowRunInfo.getRequest().getParameter("projectId_flow"); // 项目ID
				String fundIntentJsonData = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
				String slAlterAccrualRecordId = flowRunInfo.getRequest().getParameter("slAlteraccrualRecordId");
				SlAlterAccrualRecord slAlterAccrualRecord_temp =new SlAlterAccrualRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), slAlterAccrualRecord_temp,"slAlterAccrualRecord");
				if(null!=slAlterAccrualRecordId && !"".equals(slAlterAccrualRecordId)){
					SlAlterAccrualRecord sls=dao.get(Long.valueOf(slAlterAccrualRecordId));
					sls.setAlterProjectMoney(slAlterAccrualRecord_temp.getAlterProjectMoney());
					sls.setReason(slAlterAccrualRecord_temp.getReason());
					sls.setAlterpayintentPeriod(slAlterAccrualRecord_temp.getAlterpayintentPeriod());
					sls.setAccrual(slAlterAccrualRecord_temp.getAccrual());
					dao.save(sls);
					//项目表新添字段，需要将贷后的利息，计息方式，利率存入
					FlLeaseFinanceProject project=this.flLeaseFinanceProjectDao.get(Long.valueOf(projectId));
					
					//保存款项信息（利率变更新重新计算利率）开始
					
					List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentDao.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId), "LeaseFinance",Long.valueOf(projectId));
					for (SlFundIntent s : slFundIntentsAllsupervise) {
						if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
							slFundIntentDao.remove(s);
						}
					}
					if(null != fundIntentJsonData && !"".equals(fundIntentJsonData)){
						String[] slFundIentJsonArr = fundIntentJsonData.split("@");
						for(int i=0; i<slFundIentJsonArr.length; i++) {
							String str = slFundIentJsonArr[i];
							JSONParser parser = new JSONParser(new StringReader(str));
							try{
								SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
								slFundIntent.setSlAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId));
								slFundIntent.setProjectId(Long.valueOf(projectId));
								slFundIntent.setBusinessType(project.getBusinessType());
								slFundIntent.setProjectName(project.getProjectName());
								slFundIntent.setProjectNumber(project.getProjectNumber());
								slFundIntent.setCompanyId(project.getCompanyId());
								slFundIntent.setIsValid(Short.valueOf("0"));
								BigDecimal lin = new BigDecimal(0.00);
								if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
						        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
						        }else{
						        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
						        	
						        }
								slFundIntent.setAfterMoney(new BigDecimal(0));
								slFundIntent.setAccrualMoney(new BigDecimal(0));
								slFundIntent.setFlatMoney(new BigDecimal(0));
								slFundIntent.setIsCheck(Short.valueOf("1"));
								if(null==slFundIntent.getFundIntentId()){
									slFundIntentDao.save(slFundIntent);
								}else{
									SlFundIntent orgSlFundIntent=slFundIntentDao.get(slFundIntent.getFundIntentId());
									BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
									slFundIntentDao.save(orgSlFundIntent);
								}
							} catch(Exception e) {
								e.printStackTrace();
								logger.error("SlSuperviseRecordAction:款项信息保存出错"+e.getMessage());
							}
						}
					}
					
				//保存款项信息（利率变更新重新计算利率）结束
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("利率变更申请执行下一步出错:" + e.getMessage());
			return 0;
		}
	}
	
	/***
	 * 融资租赁---利率变更审核节点提交任务
	 */
	@Override
	public Integer flSaveAlterAccrualFlowExamineAndVerifyNextStep(FlowRunInfo flowRunInfo) {
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			}else{
				String projectId = flowRunInfo.getRequest().getParameter("projectId_flow"); // 项目ID
				String slAlterAccrualRecordId = flowRunInfo.getRequest().getParameter("slAlteraccrualRecordId");
				FlLeaseFinanceProject project=this.flLeaseFinanceProjectDao.get(Long.valueOf(projectId));
				String transitionName = flowRunInfo.getTransitionName();
				Map<String, Object> vars = new HashMap<String, Object>();
				if (transitionName != null && !"".equals(transitionName)) {
					if(transitionName.contains("结束")||transitionName.contains("终止")){
						flowRunInfo.setStop(true);
						project.setIsOtherFlow(Short.valueOf("0"));//利率变更流程终止后将项目表中的isOtherFlow字段值修改为0
						flLeaseFinanceProjectDao.save(project);
						//讲利率变更申请时生成的款项计划设置为无效的
						/*List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentDao.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId), "LeaseFinance",Long.valueOf(projectId));
						for (SlFundIntent s : slFundIntentsAllsupervise) {
							s.setIsValid(Short.valueOf("1"));
							s.setIsCheck(Short.valueOf("3"));
							slFundIntentDao.save(s);
						}*/
						//将利率变更表中利率变更记录状态修改
						SlAlterAccrualRecord sls=dao.get(Long.valueOf(slAlterAccrualRecordId));
						sls.setCheckStatus(6);
						dao.save(sls);
					}else{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "irfApplyForChange");
								if (processForm != null&& processForm.getCreatorId() != null) {
									String creatorId = processForm.getCreatorId().toString();
									vars.put("flowAssignId", creatorId);
								}
							} else {
								//写自己的业务逻辑
								String taskId = flowRunInfo.getRequest().getParameter("task_id");
								String comments = flowRunInfo.getRequest().getParameter("comments");
								if (null != taskId && !"".equals(taskId) && null != comments
										&& !"".equals(comments.trim())) {
									this.creditProjectService.saveComments(taskId, comments);
								}
							}
						}
					}
					vars.put("auditingResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("融资租赁--利率变更审核提交任务出错"+e.getMessage());
			return 0;
		}
	}
	
	/***
	 * 融资租赁---利率变更审查节点提交任务
	 */
	@Override
	public Integer flSaveAlterAccrualFlowNextExamineAndCheckNextStep(FlowRunInfo flowRunInfo) {
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "irfApplyForChange");
							if (processForm != null&& processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							//写自己的业务逻辑
							String taskId = flowRunInfo.getRequest().getParameter("task_id");
							String comments = flowRunInfo.getRequest().getParameter("comments");
							if (null != taskId && !"".equals(taskId) && null != comments
									&& !"".equals(comments.trim())) {
								this.creditProjectService.saveComments(taskId, comments);
							}
						}
					}
					vars.put("examineResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("融资租赁--利率变更审查提交任务出错"+e.getMessage());
			return 0;
		}
	}
	
	/***
	 * 融资租赁--利率变更审批节点提交任务
	 */
	@Override
	public Integer flSaveAlterAccrualFlowNextExamineAndApproveNextStep(FlowRunInfo flowRunInfo) {
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "irfAuditing");
							if (processForm != null&& processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							//写自己的业务逻辑
							String projectId = flowRunInfo.getRequest().getParameter("projectId_flow"); // 项目ID
							String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
							String slAlterAccrualRecordId = flowRunInfo.getRequest().getParameter("slAlteraccrualRecordId");
							FlLeaseFinanceProject project=this.flLeaseFinanceProjectDao.get(Long.valueOf(projectId));
							
							String alterpayintentPeriod=flowRunInfo.getRequest().getParameter("alterpayintentPeriod");
							String slSuperviseRecordId="";
							if(null!=alterpayintentPeriod && !"".equals(alterpayintentPeriod)){
								slSuperviseRecordId=alterpayintentPeriod.substring(0,alterpayintentPeriod.lastIndexOf("."));
							}
							SlAlterAccrualRecord slAlterAccrualRecord_temp =new SlAlterAccrualRecord();
							BeanUtil.populateEntity(flowRunInfo.getRequest(), slAlterAccrualRecord_temp,"slAlterAccrualRecord");
							
							List<SlFundIntent> slist=null;
							int period=slAlterAccrualRecord_temp.getAlterpayintentPeriod()-1;
							if(slSuperviseRecordId.equals("0")){
								project.setAccrualnew(slAlterAccrualRecord_temp.getAccrual());
								Date intentDate=null;
								if(period>0){
					            	List<SlFundIntent> flist=slFundIntentService.getByIntentPeriod(Long.valueOf(projectId), "LeaseFinance", "leaseFeeCharging", null, period);
					            	if(null!=flist && flist.size()>0){
					            		SlFundIntent sf=flist.get(0);
					            		intentDate=sf.getIntentDate();
					            	}
								}else{
									intentDate=project.getStartDate();
								}
				            	
			     			    slist=slFundIntentService.getListByIntentDate(Long.valueOf(projectId), "LeaseFinance", ">'"+intentDate,null);
			     			   if(null!=slist){
									for(SlFundIntent s:slist){
										if(project.getAccrualtype().equals("sameprincipalandInterest") || project.getAccrualtype().equals("matchingRepayment")){
											if((s.getFundType().equals("leaseFeeCharging") || s.getFundType().equals("leasePrincipalRepayment")) && (null==s.getSlAlteraccrualRecordId() || (null!=s.getSlAlteraccrualRecordId() && !s.getSlAlteraccrualRecordId().toString().equals(slAlterAccrualRecord_temp.getSlAlteraccrualRecordId().toString())))){
												s.setIsValid(Short.valueOf("1"));
												s.setIsCheck(Short.valueOf("1"));
												s.setAlteraccrualOperateId(Long.valueOf(slAlterAccrualRecordId));
												slFundIntentService.save(s);
											}
										}else{
											if(s.getFundType().equals("leaseFeeCharging") && (null==s.getSlAlteraccrualRecordId() || (null!=s.getSlAlteraccrualRecordId() && !s.getSlAlteraccrualRecordId().toString().equals(slAlterAccrualRecord_temp.getSlAlteraccrualRecordId().toString())))){
												s.setIsValid(Short.valueOf("1"));
												s.setIsCheck(Short.valueOf("1"));
												s.setAlteraccrualOperateId(Long.valueOf(slAlterAccrualRecordId));
												slFundIntentService.save(s);
											}
										}
										
									}
								}
							}else{
								SlSuperviseRecord slSuperviseRecord=null;
								if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
									slSuperviseRecord=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
									slSuperviseRecord.setContinuationRateNew(slAlterAccrualRecord_temp.getAccrual());
									slSuperviseRecordService.save(slSuperviseRecord);
								}
								Date intentDate=null;
								if(period>0){
									List<SlFundIntent> flist=slFundIntentService.getByIntentPeriod(Long.valueOf(projectId), "LeaseFinance", "leaseFeeCharging", Long.valueOf(slSuperviseRecordId), period);
									if(null!=flist && flist.size()>0){
					            		SlFundIntent sf=flist.get(0);
					            		intentDate=sf.getIntentDate();
									}
								}else{
									if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
										intentDate=slSuperviseRecord.getStartDate();
									}
								}
								if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
									slist=slFundIntentService.getListByIntentDate(Long.valueOf(projectId), "LeaseFinance", ">'"+intentDate,Long.valueOf(slSuperviseRecordId));
								}
								if(null!=slist){
									for(SlFundIntent s:slist){
										if(slSuperviseRecord.getAccrualtype().equals("sameprincipalandInterest") || slSuperviseRecord.getAccrualtype().equals("matchingRepayment")){
											if((s.getFundType().equals("leaseFeeCharging") || s.getFundType().equals("leasePrincipalRepayment")) && (null==s.getSlAlteraccrualRecordId() || (null!=s.getSlAlteraccrualRecordId() && !s.getSlAlteraccrualRecordId().toString().equals(slAlterAccrualRecord_temp.getSlAlteraccrualRecordId().toString())))){
												s.setIsValid(Short.valueOf("1"));
												s.setIsCheck(Short.valueOf("1"));
												s.setAlteraccrualOperateId(Long.valueOf(slAlterAccrualRecordId));
												slFundIntentService.save(s);
											}
										}else{
											if(s.getFundType().equals("leaseFeeCharging") && (null==s.getSlAlteraccrualRecordId() || (null!=s.getSlAlteraccrualRecordId() && !s.getSlAlteraccrualRecordId().toString().equals(slAlterAccrualRecord_temp.getSlAlteraccrualRecordId().toString())))){
												s.setIsValid(Short.valueOf("1"));
												s.setIsCheck(Short.valueOf("1"));
												s.setAlteraccrualOperateId(Long.valueOf(slAlterAccrualRecordId));
												slFundIntentService.save(s);
											}
										}
										
									}
								}
							}
							List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentDao.getlistbyslslAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId), "LeaseFinance",Long.valueOf(projectId));
							for (SlFundIntent s : slFundIntentsAllsupervise) {
								if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
									slFundIntentDao.remove(s);
								}
							}
							if(null != fundIntentJsonData && !"".equals(fundIntentJsonData)){
								String[] slFundIentJsonArr = fundIntentJsonData.split("@");
								for(int i=0; i<slFundIentJsonArr.length; i++) {
									String str = slFundIentJsonArr[i];
									JSONParser parser = new JSONParser(new StringReader(str));
									try{
										SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
										slFundIntent.setSlAlteraccrualRecordId(Long.valueOf(slAlterAccrualRecordId));
										slFundIntent.setProjectId(Long.valueOf(projectId));
										slFundIntent.setBusinessType(project.getBusinessType());
										slFundIntent.setProjectName(project.getProjectName());
										slFundIntent.setProjectNumber(project.getProjectNumber());
										slFundIntent.setCompanyId(project.getCompanyId());
										slFundIntent.setIsValid(Short.valueOf("0"));
										BigDecimal lin = new BigDecimal(0.00);
										if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
								        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
								        }else{
								        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
								        	
								        }
										slFundIntent.setAfterMoney(new BigDecimal(0));
										slFundIntent.setAccrualMoney(new BigDecimal(0));
										slFundIntent.setFlatMoney(new BigDecimal(0));
										slFundIntent.setIsCheck(Short.valueOf("0"));
										if(null==slFundIntent.getFundIntentId()){
											slFundIntentDao.save(slFundIntent);
										}else{
											SlFundIntent orgSlFundIntent=slFundIntentDao.get(slFundIntent.getFundIntentId());
											BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
											slFundIntentDao.save(orgSlFundIntent);
										}
									} catch(Exception e) {
										e.printStackTrace();
										logger.error("SlSuperviseRecordAction:款项信息保存出错"+e.getMessage());
									}
								}
							}
							
							//项目表新添字段，当利率变更流程走完后将项目表里面的isOtherFlow字段值改为1
							
							project.setIsOtherFlow(Short.valueOf("0"));
							flLeaseFinanceProjectDao.save(project);
							//将利率变更表中利率变更记录状态修改
							SlAlterAccrualRecord sls=dao.get(Long.valueOf(slAlterAccrualRecordId));
							sls.setCheckStatus(5);
							dao.save(sls);
						}
					}
					vars.put("approveResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("融资租赁--利率变更审批提交任务出错"+e.getMessage());
			return 0;
		}
	}
}