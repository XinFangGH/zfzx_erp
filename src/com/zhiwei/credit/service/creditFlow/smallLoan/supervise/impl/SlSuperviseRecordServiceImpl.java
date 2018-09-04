package com.zhiwei.credit.service.creditFlow.smallLoan.supervise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.util.ElementUtil;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.core.util.JacobWord;
import com.zhiwei.credit.dao.creditFlow.contract.DocumentTempletDao;
import com.zhiwei.credit.dao.creditFlow.contract.ProcreditContractDao;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.BpFundProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.supervise.SlSuperviseRecordDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.TaskSignDataDao;
import com.zhiwei.credit.dao.system.FileAttachDao;
import com.zhiwei.credit.model.creditFlow.contract.AssureIntentBookElementCode;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.SmallLoanElementCode;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.contract.ElementHandleService;
import com.zhiwei.credit.service.creditFlow.contract.ProcreditContractService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.supervise.SlSuperviseRecordService;
import com.zhiwei.credit.service.flow.JbpmService;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings({"unused","unchecked"})
public class SlSuperviseRecordServiceImpl extends BaseServiceImpl<SlSuperviseRecord> implements SlSuperviseRecordService{
	
	private SlSuperviseRecordDao dao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private TaskSignDataDao taskSignDataDao;
	@Resource
	private SlSmallloanProjectDao slSmallloanProjectdao;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private ElementHandleService elementHandleService;
	@Resource	
	private ProcreditContractService procreditContractService;
	@Resource	
	private FileFormService fileFormService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	@Resource
	private SlEarlyRepaymentRecordService slEarlyRepaymentRecordService;
	@Resource
	private DocumentTempletDao documentTempletDao;
	@Resource
	private EnterpriseDao enterpriseDao;
	@Resource
	private PersonDao personDao;
	@Resource
	private FileAttachDao fileAttachDao;
	@Resource
	private ProcreditContractDao procreditContractDao;
	@Resource
	private BpFundProjectDao bpFundProjectDao;
	private AssureIntentBookElementCode assureIntentBookElementCode ;
	
	public AssureIntentBookElementCode getAssureIntentBookElementCode() {
		return assureIntentBookElementCode;
	}

	public void setAssureIntentBookElementCode(
			AssureIntentBookElementCode assureIntentBookElementCode) {
		this.assureIntentBookElementCode = assureIntentBookElementCode;
	}
	public SlSuperviseRecordServiceImpl(SlSuperviseRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<SlSuperviseRecord> getListByProjectId(Long projectId,String baseBusinessType) {
		// TODO Auto-generated method stub
		return this.dao.getListByProjectId(projectId,baseBusinessType);
	}

	/**
     * 小贷展期流程-展期申请提交下一步
     * @param flowRunInfo
     * @return
     */
	@Override
	public Integer postponedApplyForNextStep(FlowRunInfo flowRunInfo) {
		

		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				SlSuperviseRecord slSuperviseRecord=new SlSuperviseRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord,"slSuperviseRecord");
				SlSuperviseRecord orgSlSuperviseRecord = dao.get(slSuperviseRecord.getId());
				BpFundProject fundProject =  bpFundProjectDao.get(orgSlSuperviseRecord.getProjectId());
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						orgSlSuperviseRecord.setCheckStatus(3);
						dao.merge(orgSlSuperviseRecord);
						fundProject.setIsOtherFlow(Short.valueOf("0"));
						bpFundProjectDao.merge(fundProject);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								BeanUtil.copyNotNullProperties(orgSlSuperviseRecord, slSuperviseRecord);
								
								String slActualToChargeJsonData = flowRunInfo.getRequest().getParameter("slActualToChargeJson");
								
								//保存费用信息
								String isCheck=flowRunInfo.getRequest().getParameter("isCheck");
								if(null!=slActualToChargeJsonData && !slActualToChargeJsonData.equals("")){
									slActualToChargeService.savejson(slActualToChargeJsonData, fundProject.getProjectId(), fundProject.getBusinessType(), Short.valueOf(isCheck), fundProject.getCompanyId());
								}
								//保存款项信息
								String slFundIentJson=flowRunInfo.getRequest().getParameter("slFundIentJsonData");
								if(null != slFundIentJson && !"".equals(slFundIentJson)) {
									List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslSuperviseRecordId(orgSlSuperviseRecord.getId(), "SmallLoan",fundProject.getProjectId());
									for (SlFundIntent s : slFundIntentsAllsupervise) {
										if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
											slFundIntentService.remove(s);
										}
									}
									String[] slFundIentJsonArr = slFundIentJson.split("@");
									
									for(int i=0; i<slFundIentJsonArr.length; i++) {
										String str = slFundIentJsonArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										
										SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
										slFundIntent.setProjectId(fundProject.getProjectId());
										slFundIntent.setBusinessType(fundProject.getBusinessType());
										slFundIntent.setProjectName(fundProject.getProjectName());
										slFundIntent.setProjectNumber(fundProject.getProjectNumber());
										slFundIntent.setCompanyId(fundProject.getCompanyId());
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
											slFundIntentService.save(slFundIntent);
										}else{
											SlFundIntent orgSlFundIntent=slFundIntentService.get(slFundIntent.getFundIntentId());
											BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
											slFundIntentService.save(orgSlFundIntent);
										}
									}
								}
								if(transitionName.contains("完成")){
									orgSlSuperviseRecord.setCheckStatus(1);
									fundProject.setIsOtherFlow(Short.valueOf("0"));
									fundProject.setProjectStatus(Short.valueOf("4"));
									bpFundProjectDao.merge(fundProject);
									List<SlFundIntent> slist=slFundIntentService.getListByFundType(fundProject.getProjectId(), fundProject.getBusinessType(), "principalRepayment",orgSlSuperviseRecord.getId(),fundProject.getId());
									
									if(null!=slist && slist.size()>0){	
										SlFundIntent f =slist.get(0);
										if(null==f.getSlSuperviseRecordId() || (null!=f.getSlSuperviseRecordId() && !f.getSlSuperviseRecordId().toString().equals(orgSlSuperviseRecord.getId().toString()))){
											if(f.getAfterMoney().compareTo(new BigDecimal(0))==0){
												f.setIsValid(Short.valueOf("1"));
												f.setIsCheck(Short.valueOf("1"));
												slFundIntentService.save(f);
											}else{
												SlFundIntent sf=new SlFundIntent();
												sf.setIncomeMoney(f.getIncomeMoney().subtract(f.getAfterMoney()));
												sf.setPayMoney(new BigDecimal(0));
												sf.setIntentDate(f.getIntentDate());
												sf.setFactDate(null);
												sf.setAfterMoney(new BigDecimal(0));
												sf.setNotMoney(f.getIncomeMoney().subtract(f.getAfterMoney()));
												sf.setIsValid(Short.valueOf("1"));
												sf.setIsCheck(Short.valueOf("1"));
												sf.setPayintentPeriod(f.getPayintentPeriod());
												slFundIntentService.save(sf);
												f.setIncomeMoney(f.getAfterMoney());
												f.setNotMoney(new BigDecimal(0));
												slFundIntentService.save(f);
											}
										}
									}
								}
								dao.merge(orgSlSuperviseRecord);
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

	/**
     * 小贷展期流程-展期合同签署及手续办理提交下一步
     * @param flowRunInfo
     * @return
     */
	@Override
	public Integer updateRiskDepCheckNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());

					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "slpfApplyForPostponed");
							if (processForm != null && processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {

						}
					}
					vars.put("riskDepCheckResult", transitionName);
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
	public Integer updateInvestigate(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());

					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "slpfApplyForPostponed");
							if (processForm != null && processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							SlSuperviseRecord slSuperviseRecord=new SlSuperviseRecord();
							BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord,"slSuperviseRecord");
							SlSuperviseRecord orgSlSuperviseRecord = dao.get(slSuperviseRecord.getId());
							BeanUtil.copyNotNullProperties(orgSlSuperviseRecord, slSuperviseRecord);
							//StatsPro.calcuProEndDate(orgSlSuperviseRecord);
							dao.save(orgSlSuperviseRecord);
						}
					}
					SlSmallloanProject project_record = new SlSmallloanProject();
					BeanUtil.populateEntity(flowRunInfo.getRequest(),project_record, "slSmallloanProject");
//=====小贷展期项目在展期审核任务提交时生成展期审查审批表开始==========（add by liny 2013-2-21）
					String serverPath = AppUtil.getAppAbsolutePath();//拿到当前项目的却对路径
					String businessType_ =project_record.getBusinessType();
					Long projectId_ =project_record.getProjectId();
					String categoryId ="0";//ProcreditContractCategory中的id
					String cconId = "0";//合同表的id  （contractId）
					String projectID_=projectId_+"";
					String _mark = "superviseRecordVerification";//展期审批表唯一key
					String htType ="superviseRecordVerification";
					String htmcdName ="展期审查审批表";
					String projectNumber = "";
					String path = "", fileName ="",shortName ="";
					//======查询视图看是否已经生成过了审批表开始
					List list = null;
					VProcreditContract vpcc = null;
					Object []obj = {businessType_,projectID_,_mark};
					String hql = "from VProcreditContract where businessType =? and projId=? and htType=?";
					try{
						list = creditBaseDao.queryHql(hql,obj);
						if(list!= null && list.size()>0){
							if(list.size()>1){
								vpcc = (VProcreditContract)list.get(list.size()-1);
							}else if(list.size()==0){
								vpcc = (VProcreditContract)list.get(0);
							}
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					if(vpcc!=null){
						categoryId =vpcc.getId()+"";
						cconId =vpcc.getId()+"";
					}
					//=======查询视图看是否已经生成过了审批表结束

					DocumentTemplet templet_ = documentTempletDao.findDocumentTemplet(_mark);//模版的实体先查左侧数据
					templet_ = documentTempletDao.getTempletObj(templet_.getId());//根据左侧模版的id  查询右侧的文件
					if(null==templet_){
						logger.error("没有模版，请上传模版"); 
					}else{
						String htlxdName = templet_.getText();
						String cType = templet_.getTemplettype()+"";
						int conId = Integer.parseInt(cconId);
						if(!templet_.isIsexist()){
							logger.error("没有模版，请上传模版");
						}else{
							//查询对应的模板文件是否上传
							boolean isFileExists = false;
							FileForm fileForm_ = fileFormService.getById(templet_.getFileid());
							DocumentTemplet templet = null ;
							int tempId = templet_.getId();
							String ttempId = templet_.getId()+"";
							if(null != fileForm_){
								String path_ = ElementUtil.replaceAllEleme(assureIntentBookElementCode,serverPath+fileForm_.getFilepath(),fileForm_.getFilename());
								if(!"false".equals(path_)){
									templet = documentTempletDao.getById(tempId);
									if(ttempId != null && templet!=null){
										DocumentTemplet dtemplet = documentTempletDao.getById(tempId);
										if(dtemplet != null){//只是单纯看一下数据库是否有模版文件记录
											if(dtemplet.getIsexist()== true && dtemplet.getFileid()!=0){//只是单纯看一下数据库是否有模版文件记录是否有值
												FileForm fileF = fileFormService.getById(dtemplet.getFileid());
												if(fileF != null){
													File file = new File(serverPath + fileF.getFilepath());
													if(file.exists()){
														isFileExists = true;
													}else{
														logger.error("没有模版，请上传模版文件");
													}
												}	
											}else{
												logger.error("没有模版，请上传模版文件");
											}
										}else{
											logger.error("没有模版，请上传模版文件");
										}
									}
								}else{
									logger.error("没有模版，请上传模版");
								}
							}
								

							//如果模板文件已经上传
							if(isFileExists==true){
								ProcreditContract p = new ProcreditContract();
								p.setContractCategoryTypeText(htlxdName);
								p.setHtType(htType);//htType即使模版的唯一key值
								p.setTemptId(tempId);//模版文件的Id
								p.setMortgageId(0);//反担保ID  如果是反担保  这个菜有值  不然就是0
								Long thirdRalationId = Long.parseLong("0");
								p.setIsApply(true);//展期合同有用  是展期合同  则为true    其他都为false
								if(categoryId=="0"||"0".equals(categoryId)){
									categoryId = procreditContractService.add(p,projectID_,businessType_)+"";
								}else{
									if("".equals(htlxdName) && !"".equals(htmcdName)){//没选合同类型下拉菜单，只选了合同名称下拉菜单
										Object[] obj1 = {htmcdName ,tempId,Integer.parseInt(categoryId)} ;
										elementHandleService.updateCon("update ProcreditContract set contractCategoryText=?, temptId=? where id =?",obj1);
									}else if(!"".equals(htlxdName) && "".equals(htmcdName)){//正规操作情况下没有这种可能
										Object[] obj1 = {htlxdName,Integer.parseInt(categoryId)} ;
										elementHandleService.updateCon("update ProcreditContract set contractCategoryTypeText=? where id =?",obj1);
									}else if(!"".equals(htlxdName) && !"".equals(htmcdName)){
										Object[] obj1 = {htlxdName ,htmcdName ,tempId,Integer.parseInt(categoryId)} ;
										elementHandleService.updateCon("update ProcreditContract set contractCategoryTypeText=?, contractCategoryText=?, temptId=? where id =?",obj1);
									}
								}
								
								//合同编号
								//String rnum = elementHandleService.getNumber(projectID_, ttempId);	
								//向合同表中增加或修改数据数据（由相应的合同id是否存在）
								conId = procreditContractService.makeUpload(cconId, ttempId, cType, categoryId, projectID_, thirdRalationId,businessType_,null,"");//向合同表中添加数据
								SlSmallloanProject sloanProject =null;
								ProcreditContract pcontract =procreditContractService.getById(conId);
								sloanProject =  slSmallloanProjectService.get(Long.valueOf(pcontract.getProjId()));
								projectNumber = sloanProject.getProjectNumber();
								if((sloanProject.getOppositeType()).equals("company_customer")){//客户类型为企业
									Enterprise enterp =enterpriseDao.getById(Integer.parseInt(sloanProject.getOppositeID().toString()));
									shortName = enterp.getShortname().trim();
								}else if((sloanProject.getOppositeType()).equals("person_customer")){//客户类型为个人
									Person person =personDao.getById(Integer.parseInt(sloanProject.getOppositeID().toString()));
									shortName = person.getName().trim();
								}
								//生成合同和合同文件的名字
								if(conId != 0){
									fileName = pcontract.getContractName();
									if(null == fileName){
										fileName = shortName+"-"+projectNumber+"-"+templet.getText()/*+"-"+rnum*/;
									}else{
										fileName = shortName+"-"+projectNumber+"-"+templet.getText()/*+"-"+rnum*/;
									}
								}else{
									fileName = shortName+"-"+projectNumber+"-"+templet.getText()/*+"-"+rnum*/;
								}
								
								FileForm fileForm = fileFormService.getById(templet.getFileid());
								path = ElementUtil.getUrl(serverPath + fileForm.getFilepath(),fileName+".doc");
								String fileCraName = shortName+"_"+projectNumber;
								String copyDir = serverPath + "attachFiles\\projFile\\contfolder\\"+fileCraName+"\\"+templet.getParentid()+"\\";
								File cFile = new File(copyDir);
								if(!cFile.exists()){
									cFile.mkdirs();
								}
								String newPath = (copyDir+fileName+".doc").trim();
								String filePath =("projFile/contfolder/"+fileCraName+"/"+templet.getParentid()+"/"+fileName+".doc").trim();
								Object[] obj2 = {true ,"attachFiles/"+filePath ,conId} ;
								elementHandleService.updateCon("update ProcreditContract set isUpload=?, url=? where id =?",obj2);
								ProcreditContract pc=procreditContractDao.getById(conId);
								if(null!=pc){
									pc.setIsUpload(true);
									pc.setUrl("attachFiles/"+filePath);
									procreditContractDao.save(pc);
								}
								List<FileAttach> listfileAttach = fileAttachDao.listByContractId(conId);
								if(listfileAttach != null){
									for(FileAttach f:listfileAttach){
										f.setFilePath(filePath);
										fileAttachDao.merge(f);
									}
								}else{
									//智维附件表操作start...为的是可以在线编辑
									FileAttach fileAttach = new FileAttach();
									fileAttach.setFileName(fileName);
									fileAttach.setFilePath(filePath);
									fileAttach.setCreatetime(new Date());
									fileAttach.setExt("doc");
									fileAttach.setFileType("attachFiles/uploads");
									fileAttach.setCreatorId(Long.parseLong(ContextUtil.getCurrentUser().getId()));
									fileAttach.setCreator(ContextUtil.getCurrentUser().getFullname());
									fileAttach.setDelFlag(0);
									fileAttach.setCsContractId(conId);
									fileAttachDao.save(fileAttach);
									//智维附件表操作end...
								}
								SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
								//贷款审查审批表文档     往贷款审查审批表文档要素里面放值的方法
								SmallLoanElementCode smallLoanElementCodeValue = elementHandleService.getElementBySystemReviewTable(projectID_,conId,tempId,htType,null) ;
								File file = new File(path);
								if(file.exists()){
									JacobWord.getInstance().replaceAllText(path, ElementUtil.findEleCodeArray(smallLoanElementCode), ElementUtil.findEleCodeValueArray(smallLoanElementCodeValue));
									FileHelper.copyFile(path, newPath);
							        FileHelper.deleteFile(path);
								}
								
							}	
						}
					}
//=====小贷展期项目在展期审核任务提交时生成展期审查审批表结束==========
					vars.put("investigateResult", transitionName);
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
	public Integer updateHeadOfficeRiskDepCheck(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());

					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "slpfInvestigate");
							if (processForm != null && processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							String sbhPartake = flowRunInfo.getRequest().getParameter("sbhPartake");
							if (sbhPartake != null && !"".equals(sbhPartake)) {
								String assignUserIds = sbhPartake;
								vars.put("flowAssignId", assignUserIds);
							}
						}
					}
					vars.put("headOfficeRiskDepCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}
	private boolean saveTaskSignData(ProcessForm processForm, String userId,
			String userFullName, String parentTaskId, String signVoteType) {
		try {
			TaskSignData taskSignData = new TaskSignData();
			taskSignData.setVoteId(new Long(userId));
			taskSignData.setVoteName(userFullName);
			taskSignData.setVoteTime(new Date());
			taskSignData.setTaskId(parentTaskId);
			taskSignData.setRunId(new Long(processForm.getRunId()));
			taskSignData.setIsAgree(Short.valueOf(signVoteType));
			taskSignData.setFromTaskId(processForm.getFromTaskId());
			taskSignData.setFormId(processForm.getFormId());
			taskSignDataDao.save(taskSignData);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("审保会集体决议提交保存会签信息出错：" + e.getMessage());
			return false;
		}
	}
	public Integer examinationArrangement(FlowRunInfo flowRunInfo) {

		try {
			SlSuperviseRecord project = new SlSuperviseRecord();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSuperviseRecord");
			String signVoteType = flowRunInfo.getRequest().getParameter(
					"signVoteType1");//
			String userId = ContextUtil.getCurrentUserId().toString();
			String userFullName = ContextUtil.getCurrentUser().getFullname();
			Map<String, String> vars = new HashMap<String, String>();

			String taskId = flowRunInfo.getTaskId();
			if (taskId != null && !"".equals(taskId)) {
				Task parentTask = jbpmService.getParentTask(taskId.toString());
				ProcessForm processForm = processFormDao.getByTaskId(taskId);
				if (processForm != null) {
					boolean saveTaskSignDataOk = this.saveTaskSignData(
							processForm, userId, userFullName, parentTask
									.getId(), signVoteType);
					if (saveTaskSignDataOk) {
						// 表示为小贷快速流程的审贷会,根据此参数判断执行不同的分支跳转规则。value只要不为空即可。
						String microSupervise = flowRunInfo.getRequest().getParameter("microSupervise");
						if (microSupervise != null&& !"".equals(microSupervise)) {
							vars.put("microNormalSDH", "false");
						}else{
							vars.put("smallLoanNormalSDH", "false");
						}
						//vars.put("projectId", project.getId().toString());
						vars.put("isExhibition", "true");
						flowRunInfo.getVariables().putAll(vars);
						List<Transition> trans = jbpmService
								.getTransitionsByTaskId(taskId);
						if (trans != null && trans.size() != 0) {
							String transitionName = trans.get(0).getName();
							flowRunInfo.setTransitionName(transitionName);
						}
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷常规审贷会集体决议提交下一步出错：" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer updateExtenstionApproval(FlowRunInfo flowRunInfo) {
		try {

			if (flowRunInfo.isBack()) {

				return 1;
			} else {
				SlSuperviseRecord slSuperviseRecord=new SlSuperviseRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord,
						"slSuperviseRecord");
				SlSuperviseRecord orgSlSuperviseRecord = dao.get(slSuperviseRecord.getId());
				BeanUtil.copyNotNullProperties(orgSlSuperviseRecord, slSuperviseRecord);
				//StatsPro.calcuProEndDate(orgSlSuperviseRecord);
				dao.save(orgSlSuperviseRecord);
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("确认展期终审通过意见执行下一步出错:" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer updateExtenstionApprovalAgain(FlowRunInfo flowRunInfo) {
		try {

			if (flowRunInfo.isBack()) {

				return 1;
			} else {

				String transitionName = flowRunInfo.getTransitionName();

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());

					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "slpfPassOpinion");
							if (processForm != null && processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							SlSuperviseRecord slSuperviseRecord=new SlSuperviseRecord();
							BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord,
									"slSuperviseRecord");
							SlSuperviseRecord orgSlSuperviseRecord = dao.get(slSuperviseRecord.getId());
							BeanUtil.copyNotNullProperties(orgSlSuperviseRecord, slSuperviseRecord);
							//StatsPro.calcuProEndDate(orgSlSuperviseRecord);
							dao.save(orgSlSuperviseRecord);
						}
					}
					vars.put("slnHeadquartersApprovalResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			
			
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("复核展期终审通过意见执行下一步出错:" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer updateSignPostponedInfo(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());

					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "slpfApplyForPostponed");
							if (processForm != null && processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {

						}
					}
					vars.put("signPostponedInfoResult", transitionName);
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
	public Integer refuseExtenstionApproval(FlowRunInfo flowRunInfo) {
		try {

			if (flowRunInfo.isBack()) {

				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("smallProjectId");
				String slSuperviseRecordId=flowRunInfo.getRequest().getParameter("slSuperviseRecordId");
				if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
					SlSuperviseRecord slSuperviseRecord=dao.get(Long.valueOf(slSuperviseRecordId));
					slSuperviseRecord.setCheckStatus(6);
					dao.save(slSuperviseRecord);
				}
				if(null!=projectId && !"".equals(projectId)){
					SlSmallloanProject project=slSmallloanProjectService.get(Long.valueOf(projectId));
					project.setProjectStatus(Short.valueOf("6"));
					project.setIsOtherFlow(Short.valueOf("0"));
					slSmallloanProjectService.save(project);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("复核展期终审通过意见执行下一步出错:" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer generalManagerApproval(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());

					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "slpfHeadOfficeRiskDepCheck");
							if (processForm != null && processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {

						}
					}
					vars.put("generalManagerApprovalResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
     * 小贷展期流程-款项计划确认提交下一步
     * @param flowRunInfo
     * @return
     */
	@Override
	public Integer confirmThePlanOfFundNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				SlSuperviseRecord slSuperviseRecord=new SlSuperviseRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord,"slSuperviseRecord");
				SlSuperviseRecord orgSlSuperviseRecord = dao.get(slSuperviseRecord.getId());
				orgSlSuperviseRecord.setCheckStatus(5);
				BeanUtil.copyNotNullProperties(orgSlSuperviseRecord, slSuperviseRecord);
				//StatsPro.calcuProEndDate(orgSlSuperviseRecord);
				dao.save(orgSlSuperviseRecord);
				String slSuperviseRecordId=flowRunInfo.getRequest().getParameter("slSuperviseRecordId");
				String slFundIentJson=flowRunInfo.getRequest().getParameter("slFundIentJsonData");
				String projectId=flowRunInfo.getRequest().getParameter("projectId");
				SlSmallloanProject p=slSmallloanProjectService.get(Long.valueOf(projectId));
				String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJson");
				slActualToChargeService.savejson(slActualToChargeJson, p.getProjectId(),orgSlSuperviseRecord.getId(),"SmallLoan", Short.parseShort("0"), p.getCompanyId());
				if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
					List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslSuperviseRecordId(Long.valueOf(slSuperviseRecordId), "SmallLoan",Long.valueOf(projectId));
					for (SlFundIntent s : slFundIntentsAllsupervise) {
						if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
							slFundIntentService.remove(s);
						}
					}
					if(null != slFundIentJson && !"".equals(slFundIentJson)) {
						String[] slFundIentJsonArr = slFundIentJson.split("@");
						for(int i=0; i<slFundIentJsonArr.length; i++) {
							String str = slFundIentJsonArr[i];
							JSONParser parser = new JSONParser(new StringReader(str));
							SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
							slFundIntent.setSlSuperviseRecordId(Long.valueOf(slSuperviseRecordId));
							slFundIntent.setProjectId(Long.valueOf(projectId));
							slFundIntent.setBusinessType(p.getBusinessType());
							slFundIntent.setProjectName(p.getProjectName());
							slFundIntent.setProjectNumber(p.getProjectNumber());
							slFundIntent.setCompanyId(p.getCompanyId());
							slFundIntent.setIsValid(Short.valueOf("0"));
							BigDecimal lin = new BigDecimal(0.00);
							if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
					        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
					        }else{
					        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
					        }
							slFundIntent.setAfterMoney(new BigDecimal(0));
							slFundIntent.setAccrualMoney(new BigDecimal(0));
							/*if(slFundIntent.getFlatMoney().compareTo(new BigDecimal(0))==0){
					        	slFundIntent.setNotMoney(new BigDecimal(0));
					        }else{
					        	slFundIntent.setNotMoney(slFundIntent.getFlatMoney());
					        	
					        }*/
							slFundIntent.setFlatMoney(new BigDecimal(0));
							slFundIntent.setIsCheck(Short.valueOf("0"));
							if(null==slFundIntent.getFundIntentId()){
								slFundIntentService.save(slFundIntent);
							}else{
								SlFundIntent orgSlFundIntent=slFundIntentService.get(slFundIntent.getFundIntentId());
								BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
								slFundIntentService.save(orgSlFundIntent);
							}
						}
					}
					List<SlFundIntent> slist=slFundIntentService.getListByFundType(p.getProjectId(), p.getBusinessType(), "principalRepayment",Long.valueOf(slSuperviseRecordId),null);
					//小贷展期生效，开始重新计算款项计算
					//String Accrualtype =p.getAccrualtype();
					//if("sameprincipal".equals(Accrualtype)||"sameprincipalandInterest".equals(Accrualtype)){
					if(null!=slist && slist.size()>0){	
						SlFundIntent f =slist.get(0);
						if(null==f.getSlSuperviseRecordId() || (null!=f.getSlSuperviseRecordId() && !f.getSlSuperviseRecordId().toString().equals(slSuperviseRecordId))){
							if(f.getAfterMoney().compareTo(new BigDecimal(0))==0){
								f.setIsValid(Short.valueOf("1"));
								f.setIsCheck(Short.valueOf("1"));
								f.setOperateId(Long.valueOf(slSuperviseRecordId));
								f.setProjectStatus(p.getProjectStatus());
								slFundIntentService.save(f);
							}else{
								SlFundIntent sf=new SlFundIntent();
								sf.setIncomeMoney(f.getIncomeMoney().subtract(f.getAfterMoney()));
								sf.setPayMoney(new BigDecimal(0));
								sf.setIntentDate(f.getIntentDate());
								sf.setFactDate(null);
								sf.setAfterMoney(new BigDecimal(0));
								sf.setNotMoney(f.getIncomeMoney().subtract(f.getAfterMoney()));
								sf.setIsValid(Short.valueOf("1"));
								sf.setIsCheck(Short.valueOf("1"));
								sf.setPayintentPeriod(f.getPayintentPeriod());
								sf.setOperateId(Long.valueOf(slSuperviseRecordId));
								sf.setProjectStatus(p.getProjectStatus());
								slFundIntentService.save(sf);
								f.setIncomeMoney(f.getAfterMoney());
								f.setNotMoney(new BigDecimal(0));
								slFundIntentService.save(f);
							}
						}
					}	
				}
				if(null!=projectId && !"".equals(projectId)){
					SlSmallloanProject project=slSmallloanProjectService.get(Long.valueOf(projectId));
					project.setProjectStatus(Constants.PROJECT_POSTPONED_STATUS_PASS);
					project.setIsOtherFlow(Short.valueOf("0"));//展期结束后将项目状态更改为0，表示一个展期流程已经完成。
					slSmallloanProjectService.save(project);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷展期流程-款项计划确认提交下一步出错:" + e.getMessage());
			return 0;
		}
	}
	
	//微贷展期申请节点提交任务
	@Override
	public Integer updateMcroLoanExtensionAppFlowProject(FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
		try{
			String projectId = flowRunInfo.getRequest().getParameter("smallProjectId");//项目ID
			String slSuperviseRecordId = flowRunInfo.getRequest().getParameter("projectId_flow");  //展期表Id
			//String businessType =flowRunInfo.getRequest().getParameter("businessType_flow"); //展期项目类型
			SlSuperviseRecord slSuperviseRecord_temp= new SlSuperviseRecord();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord_temp,"slSuperviseRecord");
			SlSuperviseRecord sls =dao.get(Long.valueOf(slSuperviseRecordId));
			BeanUtil.copyNotNullProperties(sls, slSuperviseRecord_temp);
			//StatsPro.calcuProEndDate(sls);
			dao.save(sls);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.error("贷后监管--微贷展期申请提交任务出错"+e.getMessage());
			return 0;
		}
		
	}
	//微贷展期风险审核节点提交任务
	@Override
	public Integer updateMcroLoanExtensionRiskFlowProject(
			FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
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
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "mpfApplyForPostponed");
							if (processForm != null&& processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							String sbhPartake = flowRunInfo.getRequest().getParameter("sbhPartake");
							if (sbhPartake != null && !"".equals(sbhPartake)) {
								String assignUserIds = sbhPartake;
								vars.put("flowAssignId", assignUserIds);
							}
						
						}
					}
					vars.put("riskDepCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("贷后监管--微贷展期风险审核提交任务出错"+e.getMessage());
			return 0;
		}
	}
	//微贷确认展期终审通过意见提交任务
	@Override
	public Integer updateMcroLoanExtensionConfirmFlowProject(
			FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			}else{
				String projectId = flowRunInfo.getRequest().getParameter("smallProjectId");//项目ID
				String slSuperviseRecordId = flowRunInfo.getRequest().getParameter("projectId_flow");  //展期表Id
				String businessType =flowRunInfo.getRequest().getParameter("businessType_flow"); //展期项目类型
				//String slSuperviseRecordId =flowRunInfo.getRequest().getParameter("slSuperviseRecordId"); //展期表Id
				SlSuperviseRecord slSuperviseRecord_temp= new SlSuperviseRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord_temp,"slSuperviseRecord");
				SlSuperviseRecord sls =dao.get(Long.valueOf(slSuperviseRecordId));
				BeanUtil.copyNotNullProperties(sls, slSuperviseRecord_temp);
				//StatsPro.calcuProEndDate(sls);
				dao.save(sls);
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("贷后监管--微贷确认展期终审通过意见提交任务"+e.getMessage());
			return 0;
		}
	}
//微贷复核展期终审通过意见提交任务
	@Override
	public Integer updateMcroLoanExtensionReviewConfirmFlowProject(
			FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
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
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "mpfRefuseOpinion");
							if (processForm != null && processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {

							String projectId = flowRunInfo.getRequest().getParameter("smallProjectId");//项目ID
							String slSuperviseRecordId = flowRunInfo.getRequest().getParameter("projectId_flow");  //展期表Id
							String businessType =flowRunInfo.getRequest().getParameter("businessType_flow"); //展期项目类型
							//String slSuperviseRecordId =flowRunInfo.getRequest().getParameter("slSuperviseRecordId"); //展期表Id
							SlSuperviseRecord slSuperviseRecord_temp= new SlSuperviseRecord();
							BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord_temp,"slSuperviseRecord");
							SlSuperviseRecord sls =dao.get(Long.valueOf(slSuperviseRecordId));
							BeanUtil.copyNotNullProperties(sls, slSuperviseRecord_temp);
							//StatsPro.calcuProEndDate(sls);
							dao.save(sls);
						}
					}
					vars.put("slnHeadquartersApprovalResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("贷后监管--微贷复核展期终审通过意见提交任务"+e.getMessage());
			return 0;
		}
	}
	//微贷确认展期终审否决意见提交任务
	@Override
	public Integer updateMcroLoanExtensionFinalConfirmFlowProject(
			FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId = flowRunInfo.getRequest().getParameter("smallProjectId");//项目ID
				String slSuperviseRecordId = flowRunInfo.getRequest().getParameter("projectId_flow");  //展期表Id
				SlSmallloanProject persistent = this.slSmallloanProjectdao.get(Long.valueOf(projectId));
				persistent.setProjectStatus(Short.valueOf("6"));
				persistent.setIsOtherFlow(Short.valueOf("0"));
				this.slSmallloanProjectdao.merge(persistent);
				//改变展期状态
				SlSuperviseRecord sls =dao.get(Long.valueOf(slSuperviseRecordId));
				sls.setCheckStatus(Integer.parseInt("6"));
				dao.save(sls);
			}

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷后监管--微贷确认展期终审否决意见提交任务"+e.getMessage());
			return 0;
		}
	}
//微贷展期 展期签批节点提交方法
	@Override
	public Integer updateMcroLoanExtensionEndorsementFlowProject(
			FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
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
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "mpfApplyForPostponed");
							if (processForm != null&& processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							
						}
					}
					vars.put("postponedCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("贷后监管--微贷 展期签批提交任务出错"+e.getMessage());
			return 0;
		}
	}
	//微贷展期款项计划确认提交任务
	@Override
	public Integer updateMcroLoanExtensionConfirmationFlowProject(
			FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId = flowRunInfo.getRequest().getParameter("smallProjectId");//项目ID
				String slSuperviseRecordId = flowRunInfo.getRequest().getParameter("projectId_flow");  //展期表Id
				String businessType =flowRunInfo.getRequest().getParameter("businessType_flow"); //展期项目类型
				SlSuperviseRecord slSuperviseRecord_temp= new SlSuperviseRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord_temp,"slSuperviseRecord");
				//展期款项计划信息
				String slFundIentJson=flowRunInfo.getRequest().getParameter("slFundIentJsonData");
				SlSmallloanProject persistent = this.slSmallloanProjectdao.get(Long.valueOf(projectId));
				if(persistent!=null){
					Long companyId =persistent.getCompanyId();
					if(null!=slSuperviseRecordId && !"".equals(slSuperviseRecordId)){
						List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslSuperviseRecordId(Long.valueOf(slSuperviseRecordId), "SmallLoan",Long.valueOf(projectId));
						for (SlFundIntent s : slFundIntentsAllsupervise) {
							slFundIntentService.remove(s);
						}
						if(null != slFundIentJson && !"".equals(slFundIentJson)) {
		
							String[] slFundIentJsonArr = slFundIentJson.split("@");
							
							for(int i=0; i<slFundIentJsonArr.length; i++) {
								String str = slFundIentJsonArr[i];
								JSONParser parser = new JSONParser(new StringReader(str));
								SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
								slFundIntent.setSlSuperviseRecordId(Long.valueOf(slSuperviseRecordId));
								slFundIntent.setProjectId(Long.valueOf(projectId));
								slFundIntent.setBusinessType(persistent.getBusinessType());
								slFundIntent.setProjectName(persistent.getProjectName());
								slFundIntent.setProjectNumber(persistent.getProjectNumber());
								slFundIntent.setCompanyId(companyId);
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
									slFundIntentService.save(slFundIntent);
								}else{
									SlFundIntent orgSlFundIntent=slFundIntentService.get(slFundIntent.getFundIntentId());
									BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
									slFundIntentService.save(orgSlFundIntent);
								}
							}
						}
						List<SlFundIntent> slist=slFundIntentService.getListByFundType(persistent.getProjectId(), persistent.getBusinessType(), "principalRepayment",Long.valueOf(slSuperviseRecordId),null);
						
						if(null!=slist && slist.size()>0){	
							SlFundIntent f =slist.get(0);
							if(null==f.getSlSuperviseRecordId() || (null!=f.getSlSuperviseRecordId() && !f.getSlSuperviseRecordId().toString().equals(slSuperviseRecordId))){
								if(f.getAfterMoney().compareTo(new BigDecimal(0))==0){
									f.setIsValid(Short.valueOf("1"));
									f.setIsCheck(Short.valueOf("1"));
									slFundIntentService.save(f);
								}else{
									SlFundIntent sf=new SlFundIntent();
									sf.setIncomeMoney(f.getIncomeMoney().subtract(f.getAfterMoney()));
									sf.setPayMoney(new BigDecimal(0));
									sf.setIntentDate(f.getIntentDate());
									sf.setFactDate(null);
									sf.setAfterMoney(new BigDecimal(0));
									sf.setNotMoney(f.getIncomeMoney().subtract(f.getAfterMoney()));
									sf.setIsValid(Short.valueOf("1"));
									sf.setIsCheck(Short.valueOf("1"));
									sf.setPayintentPeriod(f.getPayintentPeriod());
									slFundIntentService.save(sf);
									f.setIncomeMoney(f.getAfterMoney());
									f.setNotMoney(new BigDecimal(0));
									slFundIntentService.save(f);
								}
							}
						}	
						
					}
					persistent.setProjectStatus(Short.valueOf("5"));
					persistent.setIsOtherFlow(Short.valueOf("0"));//展期结束后将项目状态更改为0，表示一个展期流程已经完成。
					this.slSmallloanProjectdao.merge(persistent);
				}
				//改变展期状态
				SlSuperviseRecord sls =dao.get(Long.valueOf(slSuperviseRecordId));
				sls.setCheckStatus(Integer.parseInt("5"));
				//StatsPro.calcuProEndDate(sls);
				dao.save(sls);
			}

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷后监管--微贷确款项计划提交任务"+e.getMessage());
			return 0;
		}
	}

	/**
     * 小贷展期流程-展期审批提交下一步
     * @param flowRunInfo
     * @return
     */
	public Integer postponedApproveNextStep(FlowRunInfo flowRunInfo) {
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							}
						}
		    		}
					vars.put("approvePostponedResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("小贷展期-展期审批提交下一步出错"+e.getMessage());
			return 0;
		}
	}
	
	/**
     * 融资租赁展期流程-展期申请提交下一步
     * @param flowRunInfo
     * @return
     */
	@Override
	public Integer flLeaseFinancePostponedApplyForNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				SlSuperviseRecord slSuperviseRecord=new SlSuperviseRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord,"slSuperviseRecord");
				SlSuperviseRecord orgSlSuperviseRecord = dao.get(slSuperviseRecord.getId());
				BeanUtil.copyNotNullProperties(orgSlSuperviseRecord, slSuperviseRecord);
				//StatsPro.calcuProEndDate(orgSlSuperviseRecord);
				dao.save(orgSlSuperviseRecord);
				FlLeaseFinanceProject  project=flLeaseFinanceProjectService.get(orgSlSuperviseRecord.getProjectId());
				flLeaseFinanceProjectService.save(project);
				String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJson");
				slActualToChargeService.savejson(slActualToChargeJson, project.getProjectId(),orgSlSuperviseRecord.getId(),"LeaseFinance", Short.parseShort("1"), project.getCompanyId());
				String taskId = flowRunInfo.getRequest().getParameter("task_id");
				String comments = flowRunInfo.getRequest().getParameter("comments");
				if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
					this.creditProjectService.saveComments(taskId, comments);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("展期申请执行下一步出错:" + e.getMessage());
			return 0;
		}
	}
	
	/**
     * 融资租赁展期流程-展期审批提交下一步
     * @param flowRunInfo
     * @return
     */
	public Integer flLeaseFinancePostponedApproveNextStep(FlowRunInfo flowRunInfo) {
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			}else{
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if(transitionName.contains("终止")||transitionName.contains("结束")){
		      			flowRunInfo.setStop(true);
		    		}else{
		    			ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");//表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);//打回的目标任务名称
							}
						}
		    		}
					
					String taskId = flowRunInfo.getRequest().getParameter("task_id");
					String comments = flowRunInfo.getRequest().getParameter("comments");
					if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
						this.creditProjectService.saveComments(taskId, comments);
					}
					
					vars.put("approvePostponedResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("融资租赁展期流程-展期审批提交下一步出错"+e.getMessage());
			return 0;
		}
	}

	/**
     * 融资租赁展期流程-展期合同签署及手续办理提交下一步
     * @param flowRunInfo
     * @return
     */
	@Override
	public Integer flLeaseFinanceUpdateRiskDepCheckNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());

					if (currentForm != null) {
						boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(),currentForm.getActivityName(),transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "slpfApplyForPostponed");
							if (processForm != null && processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId().toString();
								vars.put("flowAssignId", creatorId);
							}
						}
					}
					
					String taskId = flowRunInfo.getRequest().getParameter("task_id");
					String comments = flowRunInfo.getRequest().getParameter("comments");
					if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
						this.creditProjectService.saveComments(taskId, comments);
					}
					
					vars.put("riskDepCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("融资租赁展期流程-展期合同签署及手续办理提交下一步出错"+e.getMessage());
			return 0;
		}
	}

	/**
     * 融资租赁展期流程-款项计划确认提交下一步
     * @param flowRunInfo
     * @return
     */
	@Override
	public Integer flLeaseFinanceConfirmThePlanOfFundNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				SlSuperviseRecord slSuperviseRecord=new SlSuperviseRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), slSuperviseRecord,"slSuperviseRecord");
				SlSuperviseRecord orgSlSuperviseRecord = dao.get(slSuperviseRecord.getId());
				orgSlSuperviseRecord.setCheckStatus(5);
				BeanUtil.copyNotNullProperties(orgSlSuperviseRecord, slSuperviseRecord);
				//StatsPro.calcuProEndDate(orgSlSuperviseRecord);
				dao.save(orgSlSuperviseRecord);
				Long slSuperviseRecordId=orgSlSuperviseRecord.getId();
				String slFundIentJson=flowRunInfo.getRequest().getParameter("slFundIentJsonData");
				String projectId=flowRunInfo.getRequest().getParameter("projectId");
				FlLeaseFinanceProject flProject=flLeaseFinanceProjectService.get(Long.valueOf(projectId));
				String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJson");
				slActualToChargeService.savejson(slActualToChargeJson, flProject.getProjectId(),orgSlSuperviseRecord.getId(),"LeaseFinance", Short.parseShort("0"), flProject.getCompanyId());
				if(null!=slSuperviseRecordId){
					List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslSuperviseRecordId(slSuperviseRecordId, "LeaseFinance",Long.valueOf(projectId));
					for (SlFundIntent s : slFundIntentsAllsupervise) {
						if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
							slFundIntentService.remove(s);
						}
					}
					if(null != slFundIentJson && !"".equals(slFundIentJson)) {
						String[] slFundIentJsonArr = slFundIentJson.split("@");
						for(int i=0; i<slFundIentJsonArr.length; i++) {
							String str = slFundIentJsonArr[i];
							JSONParser parser = new JSONParser(new StringReader(str));
							SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
							slFundIntent.setSlSuperviseRecordId(slSuperviseRecordId);
							slFundIntent.setProjectId(Long.valueOf(projectId));
							slFundIntent.setBusinessType(flProject.getBusinessType());
							slFundIntent.setProjectName(flProject.getProjectName());
							slFundIntent.setProjectNumber(flProject.getProjectNumber());
							slFundIntent.setCompanyId(flProject.getCompanyId());
							slFundIntent.setIsValid(Short.valueOf("0"));
							BigDecimal lin = new BigDecimal(0.00);
							if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
					        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
					        }else{
					        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
					        }
							slFundIntent.setAfterMoney(new BigDecimal(0));
							slFundIntent.setAccrualMoney(new BigDecimal(0));
							/*if(slFundIntent.getFlatMoney().compareTo(new BigDecimal(0))==0){
					        	slFundIntent.setNotMoney(new BigDecimal(0));
					        }else{
					        	slFundIntent.setNotMoney(slFundIntent.getFlatMoney());
					        	
					        }*/
							slFundIntent.setFlatMoney(new BigDecimal(0));
							slFundIntent.setIsCheck(Short.valueOf("0"));
							if(null==slFundIntent.getFundIntentId()){
								slFundIntentService.save(slFundIntent);
							}else{
								SlFundIntent orgSlFundIntent=slFundIntentService.get(slFundIntent.getFundIntentId());
								BeanUtil.copyNotNullProperties(orgSlFundIntent, slFundIntent);
								slFundIntentService.save(orgSlFundIntent);
							}
						}
					}
					List<SlFundIntent> slist=slFundIntentService.getListByFundType(flProject.getProjectId(), flProject.getBusinessType(), "leasePrincipalRepayment",slSuperviseRecordId,null);
					//小贷展期生效，开始重新计算款项计算
					//String Accrualtype =p.getAccrualtype();
					//if("sameprincipal".equals(Accrualtype)||"sameprincipalandInterest".equals(Accrualtype)){
					if(null!=slist && slist.size()>0){	
						SlFundIntent f =slist.get(0);
						if(null==f.getSlSuperviseRecordId() || (null!=f.getSlSuperviseRecordId() && !f.getSlSuperviseRecordId().toString().equals(slSuperviseRecordId.toString()))){
							if(f.getAfterMoney().compareTo(new BigDecimal(0))==0){
								f.setIsValid(Short.valueOf("1"));
								f.setIsCheck(Short.valueOf("1"));
								f.setOperateId(slSuperviseRecordId);
								f.setProjectStatus(flProject.getProjectStatus());
								slFundIntentService.save(f);
							}else{
								SlFundIntent sf=new SlFundIntent();
								sf.setIncomeMoney(f.getIncomeMoney().subtract(f.getAfterMoney()));
								sf.setPayMoney(new BigDecimal(0));
								sf.setIntentDate(f.getIntentDate());
								sf.setFactDate(null);
								sf.setAfterMoney(new BigDecimal(0));
								sf.setNotMoney(f.getIncomeMoney().subtract(f.getAfterMoney()));
								sf.setIsValid(Short.valueOf("1"));
								sf.setIsCheck(Short.valueOf("1"));
								sf.setPayintentPeriod(f.getPayintentPeriod());
								sf.setOperateId(Long.valueOf(slSuperviseRecordId));
								sf.setProjectStatus(flProject.getProjectStatus());
								slFundIntentService.save(sf);
								f.setIncomeMoney(f.getAfterMoney());
								f.setNotMoney(new BigDecimal(0));
								slFundIntentService.save(f);
							}
						}
					}
					List<SlFundIntent> list1=slFundIntentService.listbyfundType(flProject.getBusinessType(), flProject.getProjectId(), "marginRefund", null);
					if(null!=list1 && list1.size()>0){
						SlFundIntent s=list1.get(0);
						s.setIntentDate(orgSlSuperviseRecord.getEndDate());
						s.setOperateId(Long.valueOf(slSuperviseRecordId));
						s.setProjectStatus(flProject.getProjectStatus());
						slFundIntentService.save(s);
					}
					List<SlFundIntent> list2=slFundIntentService.listbyfundType(flProject.getBusinessType(), flProject.getProjectId(), "leaseObjectRetentionFee", null);
					if(null!=list2 && list2.size()>0){
						SlFundIntent s=list2.get(0);
						s.setIntentDate(orgSlSuperviseRecord.getEndDate());
						s.setOperateId(Long.valueOf(slSuperviseRecordId));
						s.setProjectStatus(flProject.getProjectStatus());
						slFundIntentService.save(s);
					}
				}
				if(null!=projectId && !"".equals(projectId)){
					FlLeaseFinanceProject project=flLeaseFinanceProjectService.get(Long.valueOf(projectId));
					project.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
					project.setIsOtherFlow(Short.valueOf("0"));//展期结束后将项目状态更改为0，表示一个展期流程已经完成。
					flLeaseFinanceProjectService.save(project);
				}
				
				String taskId = flowRunInfo.getRequest().getParameter("task_id");
				String comments = flowRunInfo.getRequest().getParameter("comments");
				if (null != taskId && !"".equals(taskId) && null != comments && !"".equals(comments.trim())) {
					this.creditProjectService.saveComments(taskId, comments);
				}
				
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("融资租赁展期流程-款项计划确认提交下一步出错"+e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 融资租赁提前还款流程-提前还款申请提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer askForEarlyRepaymentProjectFlowNextStep(
			FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String projectId = flowRunInfo.getRequest().getParameter("projectId_flow"); // 项目ID
				String fundIntentJsonData = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
				String businessType = flowRunInfo.getRequest().getParameter("businessType_flow");
				String slEarlyRepaymentId = flowRunInfo.getRequest().getParameter("slEarlyRepaymentId");
				SlEarlyRepaymentRecord slEarlyRepaymentRecord_temp = new SlEarlyRepaymentRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(),slEarlyRepaymentRecord_temp, "slEarlyRepaymentRecord");
				slEarlyRepaymentRecord_temp.setProjectId(Long.valueOf(projectId));
				slEarlyRepaymentRecordService.save(slEarlyRepaymentRecord_temp);
				slFundIntentService.savejsonloaned(fundIntentJsonData, Long.parseLong(projectId), businessType,
						Short.valueOf("1"), Short.valueOf("0"), null,"earlyRepayment", Long.valueOf(slEarlyRepaymentId));
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("提前还款申请执行下一步出错:" + e.getMessage());
			return 0;
		}
	}
	

	@Override
	public void updateSuperviseFundData(Long projectId,Long slSuperviseRecordId, String businessType,
			String fundIntentJsonData) {
	FlLeaseFinanceProject project = null;
	List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService
		.getlistbyslSuperviseRecordId(slSuperviseRecordId, businessType, Long
				.valueOf(projectId));
	for (SlFundIntent s : slFundIntentsAllsupervise) {
		if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
			slFundIntentService.remove(s);
		}
	}
	if("LeaseFinance".equals(businessType)){
		project =  flLeaseFinanceProjectService.get(projectId);
	}
	if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
		String[] slFundIentJsonArr = fundIntentJsonData.split("@");
		for (int i = 0; i < slFundIentJsonArr.length; i++) {
			String str = slFundIentJsonArr[i];
			JSONParser parser = new JSONParser(new StringReader(str));
			SlFundIntent slFundIntent;
			try {
				slFundIntent = (SlFundIntent) JSONMapper
						.toJava(parser.nextValue(), SlFundIntent.class);
				slFundIntent.setSlSuperviseRecordId(Long
						.valueOf(slSuperviseRecordId));
				slFundIntent.setProjectId(Long.valueOf(projectId));
				slFundIntent.setBusinessType(project.getBusinessType());
				slFundIntent.setProjectName(project.getProjectName());
				slFundIntent.setProjectNumber(project.getProjectNumber());
				slFundIntent.setCompanyId(project.getCompanyId());
				slFundIntent.setIsValid(Short.valueOf("0"));
				BigDecimal lin = new BigDecimal(0.00);
				if (slFundIntent.getIncomeMoney().compareTo(lin) == 0) {
					slFundIntent.setNotMoney(slFundIntent.getPayMoney());
				} else {
					slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
				}
				slFundIntent.setAfterMoney(new BigDecimal(0));
				slFundIntent.setAccrualMoney(new BigDecimal(0));
				slFundIntent.setFlatMoney(new BigDecimal(0));
				slFundIntent.setIsCheck(Short.valueOf("0"));
				if (null == slFundIntent.getFundIntentId()) {
					slFundIntentService.save(slFundIntent);
				} else {
					SlFundIntent orgSlFundIntent = slFundIntentService
							.get(slFundIntent.getFundIntentId());
					BeanUtil.copyNotNullProperties(orgSlFundIntent,
							slFundIntent);
					slFundIntentService.save(orgSlFundIntent);
				}
			} catch (Exception e) {
				e.printStackTrace();
		}
	}
		
	}
}
}