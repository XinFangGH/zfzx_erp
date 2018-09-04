package com.zhiwei.credit.action.creditFlow.smallLoan.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.httpclient.util.DateUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.riskControl.creditInvestigation.BpBadCredit;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.financeProject.FlFinancingProjectService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.supervise.SlSuperviseRecordService;
/**
 * 
 * @author 
 *
 */
public class SlEarlyRepaymentRecordAction extends BaseAction{
	@Resource
	private SlEarlyRepaymentRecordService slEarlyRepaymentRecordService;
	private SlEarlyRepaymentRecord slEarlyRepaymentRecord;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private FlLeaseFinanceProjectService flLeaseFinanceProjectService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlSuperviseRecordService slSuperviseRecordService;
	@Resource
	private FlFinancingProjectService flFinancingProjectService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private CreditProjectService creditProjectService;
	private Long slEarlyRepaymentId;
	private SlSmallloanProject slSmallloanProject;
	private Long projectId;
	private String taskId;
	private String businessType;
	
	
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public SlSmallloanProject getSlSmallloanProject() {
		return slSmallloanProject;
	}

	public void setSlSmallloanProject(SlSmallloanProject slSmallloanProject) {
		this.slSmallloanProject = slSmallloanProject;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Long getSlEarlyRepaymentId() {
		return slEarlyRepaymentId;
	}

	public void setSlEarlyRepaymentId(Long slEarlyRepaymentId) {
		this.slEarlyRepaymentId = slEarlyRepaymentId;
	}

	public SlEarlyRepaymentRecord getSlEarlyRepaymentRecord() {
		return slEarlyRepaymentRecord;
	}

	public void setSlEarlyRepaymentRecord(SlEarlyRepaymentRecord slEarlyRepaymentRecord) {
		this.slEarlyRepaymentRecord = slEarlyRepaymentRecord;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlEarlyRepaymentRecord> list= slEarlyRepaymentRecordService.getAll(filter);
		
		Type type=new TypeToken<List<SlEarlyRepaymentRecord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		String businessType=this.getRequest().getParameter("businessType");
		if(ids!=null){
			for(String id:ids){
				SlEarlyRepaymentRecord slEarlyRepaymentRecord=slEarlyRepaymentRecordService.get(new Long(id));
				if(null!=businessType && businessType.equals("SmallLoan")){
					List fflist=fileFormService.listFiles(slEarlyRepaymentRecord.getProjectId().toString(), "sl_earlyrepayment_record."+slEarlyRepaymentRecord.getSlEarlyRepaymentId(), "sl_earlyrepayment_record."+slEarlyRepaymentRecord.getProjectId(), "SmallLoan");
					for(int i=0;i<fflist.size();i++){
						FileForm file=(FileForm) fflist.get(i);
						try {
							creditBaseDao.deleteDatas(file);
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					}
					List<SlActualToCharge>  alist=slActualToChargeService.getlistbyslEarlyRepaymentRecordId(new Long(id), businessType, slEarlyRepaymentRecord.getProjectId());
					for(SlActualToCharge a:alist){
						slActualToChargeService.remove(a);
					}
				}
				slEarlyRepaymentRecordService.remove(new Long(id));
				if(null!=businessType && !"".equals(businessType)){
					List<SlFundIntent> flist=slFundIntentService.getlistbyslEarlyRepaymentId(new Long(id), businessType, slEarlyRepaymentRecord.getProjectId());
					for(SlFundIntent f:flist){
						slFundIntentService.remove(f);
					}
					
					List<SlFundIntent> list1=slFundIntentService.getListByEarlyOperateId(slEarlyRepaymentRecord.getProjectId(), businessType, new Long(id), "=1");
					List<SlFundIntent> list0=slFundIntentService.getListByEarlyOperateId(slEarlyRepaymentRecord.getProjectId(), businessType, new Long(id), "=0");
					
					if(null!=list0 && list0.size()>0){
						SlFundIntent s0=list0.get(0);
						SlFundIntent s1=null;
						for(SlFundIntent s:list1){
							if(!s.getFundType().equals("principalRepayment")){
								s.setIsValid(Short.valueOf("0"));
								s.setIsCheck(Short.valueOf("0"));
								s.setEarlyOperateId(null);
								s.setProjectStatus(null);
								slFundIntentService.save(s);
							}else{
								s1=s;
								slFundIntentService.remove(s);
							}
						}
						s0.setIncomeMoney(s0.getIncomeMoney().add(s1.getIncomeMoney()));
						s0.setNotMoney(s1.getNotMoney());
						s0.setEarlyOperateId(null);
						slFundIntentService.save(s0);
					}else{
						for(SlFundIntent s:list1){
							s.setIsValid(Short.valueOf("0"));
							s.setIsCheck(Short.valueOf("0"));
							s.setEarlyOperateId(null);
							s.setProjectStatus(null);
							slFundIntentService.save(s);
						}
					}
					
				}
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		SlEarlyRepaymentRecord slEarlyRepaymentRecord=slEarlyRepaymentRecordService.get(slEarlyRepaymentId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slEarlyRepaymentRecord));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slEarlyRepaymentRecord.getSlEarlyRepaymentId()==null){
			slEarlyRepaymentRecordService.save(slEarlyRepaymentRecord);
		}else{
			SlEarlyRepaymentRecord orgSlEarlyRepaymentRecord=slEarlyRepaymentRecordService.get(slEarlyRepaymentRecord.getSlEarlyRepaymentId());
			try{
				BeanUtil.copyNotNullProperties(orgSlEarlyRepaymentRecord, slEarlyRepaymentRecord);
				slEarlyRepaymentRecordService.save(orgSlEarlyRepaymentRecord);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	//启动提前还款流程
	public String startEarlyRepaymentProcess(){
		String str=slSmallloanProjectService.startRenewalProcess(projectId);
		jsonString="{success:true,"+str+"}";
		return SUCCESS;
	}
	
	public String getPrepayintentPeriod(){
		List<SlFundIntent> list=new ArrayList<SlFundIntent>();
		String businessType=this.getRequest().getParameter("businessType");
		if(null!=businessType && businessType.equals("SmallLoan")){
			list=slFundIntentService.getByFundType(projectId, "SmallLoan", "loanInterest");
		}else if(null!=businessType && businessType.equals("Financing")){
			list=slFundIntentService.getByFundType(projectId, "Financing", "FinancingInterest");
		}else if(null!=businessType && businessType.equals("LeaseFinance")){
			list=slFundIntentService.getByFundType(projectId, "LeaseFinance", "leasePrincipalRepayment");
		}
		StringBuffer buff = new StringBuffer("[");
		long sid=0;
		int j=0;
		for(SlFundIntent sf:list){
			if(null!=sf.getSlSuperviseRecordId()){
				SlSuperviseRecord record=slSuperviseRecordService.get(sf.getSlSuperviseRecordId());
				if(sid!=sf.getSlSuperviseRecordId()){
					if(null!=record && null!=record.getIsInterestByOneTime() && record.getIsInterestByOneTime()==1){
						if(record.getIsStartDatePay().equals("1")){
							for(int i=0;i<=record.getPayintentPeriod();i++){
								buff.append("['").append((sf.getSlSuperviseRecordId()+"."+i)).append("','")
								.append("展期第"+i+"期").append("'],");
							}
						}else{
							for(int i=1;i<=record.getPayintentPeriod();i++){
								buff.append("['").append((sf.getSlSuperviseRecordId()+"."+i)).append("','")
								.append("展期第"+i+"期").append("'],");
							}
						}
						sid=sf.getSlSuperviseRecordId();
					}else{
						buff.append("['").append((sf.getSlSuperviseRecordId()+"."+sf.getPayintentPeriod())).append("','")
						.append("展期第"+sf.getPayintentPeriod()+"期").append("'],");
					}
				}else{
					if(null!=record && record.getIsInterestByOneTime()!=1){
						buff.append("['").append((sf.getSlSuperviseRecordId()+"."+sf.getPayintentPeriod())).append("','")
						.append("展期第"+sf.getPayintentPeriod()+"期").append("'],");
					}
				}
			}else{
				if(null!=businessType && businessType.equals("SmallLoan")){
					SlSmallloanProject project=slSmallloanProjectService.get(projectId);
					if(project.getIsInterestByOneTime()==1){
						if(j==0){
							if(project.getIsStartDatePay().equals("1")){
								for(int i=0;i<=project.getPayintentPeriod();i++){
									buff.append("['").append((0+"."+i)).append("','")
									.append("第"+i+"期").append("'],");
								}
							}else{
								for(int i=1;i<=project.getPayintentPeriod();i++){
									buff.append("['").append((0+"."+i)).append("','")
									.append("第"+i+"期").append("'],");
								}
							}
							j=1;
						}
					}else{
						buff.append("['").append((0+"."+sf.getPayintentPeriod())).append("','")
						.append("第"+sf.getPayintentPeriod()+"期").append("'],");
					}
				}else if(null!=businessType && businessType.equals("Financing")){
					FlFinancingProject project=flFinancingProjectService.get(projectId);
					if(project.getIsInterestByOneTime()==1){
						if(j==0){
							if(project.getIsStartDatePay().equals("1")){
								for(int i=0;i<=project.getPayintentPeriod();i++){
									buff.append("['").append((0+"."+i)).append("','")
									.append("第"+i+"期").append("'],");
								}
							}else{
								for(int i=1;i<=project.getPayintentPeriod();i++){
									buff.append("['").append((0+"."+i)).append("','")
									.append("第"+i+"期").append("'],");
								}
							}
							j=1;
						}
					}else{
						buff.append("['").append((0+"."+sf.getPayintentPeriod())).append("','")
						.append("第"+sf.getPayintentPeriod()+"期").append("'],");
					}
				}else if(null!=businessType && businessType.equals("LeaseFinance")){
					FlLeaseFinanceProject project=flLeaseFinanceProjectService.get(projectId);
						if(j==0){
							if(project.getIsStartDatePay().equals("1")){
								for(int i=0;i<=project.getPayintentPeriod();i++){
									buff.append("['").append((0+"."+i)).append("','")
									.append("第"+i+"期").append("'],");
								}
							}else{
								for(int i=1;i<=project.getPayintentPeriod();i++){
									buff.append("['").append((0+"."+i)).append("','")
									.append("第"+i+"期").append("'],");
								}
							}
							j=1;
						}
				}
			}
		}
		if(null!=list && list.size()>0){
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	
	public String getAccrualType(){
		String slSuperviseRecordId=this.getRequest().getParameter("slSuperviseRecordId");
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		String accrualType="";
		if(slSuperviseRecordId.equals("0")){
			if(null!=businessType && businessType.equals("SmallLoan")){
				SlSmallloanProject project=slSmallloanProjectService.get(Long.valueOf(projectId));
				accrualType=project.getAccrualtype();
			}else if(null!=businessType && businessType.equals("Financing")){
				FlFinancingProject project=flFinancingProjectService.get(Long.valueOf(projectId));
				accrualType=project.getAccrualtype();
			}
		}else{
			SlSuperviseRecord slSuperviseRecord=slSuperviseRecordService.get(Long.valueOf(slSuperviseRecordId));
			accrualType=slSuperviseRecord.getAccrualtype();
		}
		jsonString="{success:true,accrualType:'"+accrualType+"'}";
		return SUCCESS;
	}
	
	public String startSlEarlyRepaymentProcess(){
		String str = "";
		if(null != businessType && businessType.equals("SmallLoan")){
			//str = slSmallloanProjectService.startSlEarlyRepaymentProcess(projectId);
			str = slEarlyRepaymentRecordService.startSlEarlyRepaymentProcess(projectId);
		}else if(null != businessType && businessType.equals("LeaseFinance")){
			str = flLeaseFinanceProjectService.startSlEarlyRepaymentProcess(projectId);
		}
//		String str=slSmallloanProjectService.startSlEarlyRepaymentProcess(projectId);
		jsonString="{success:true,"+str+"}";
		return SUCCESS;
	}
	
	public String investSettle(){
		String str = "";
		String organId = this.getRequest().getParameter("organId");
		String id = this.getRequest().getParameter("id");
		str = slEarlyRepaymentRecordService.investSettle(id, organId);
		
		jsonString="{success:true,"+str+"}";
		return SUCCESS;
	}
	
	public String updateEarlyRepaymentInfo(){
		try{
			String slProjectId = this.getRequest().getParameter("projectId");
			String slEarlyRepaymentId =this.getRequest().getParameter("slEarlyRepaymentId");
			if(null!=slEarlyRepaymentId && !"".equals(slEarlyRepaymentId)){
				SlEarlyRepaymentRecord sls=slEarlyRepaymentRecordService.get(Long.valueOf(slEarlyRepaymentId));
				sls.setEarlyProjectMoney(slEarlyRepaymentRecord.getEarlyProjectMoney());
				sls.setReason(slEarlyRepaymentRecord.getReason());
				sls.setPrepayintentPeriod(slEarlyRepaymentRecord.getPrepayintentPeriod());
				sls.setAccrualtype(slEarlyRepaymentRecord.getAccrualtype());
				sls.setEarlyDate(slEarlyRepaymentRecord.getEarlyDate());
				slEarlyRepaymentRecordService.save(sls);
				FlLeaseFinanceProject flProject=null;
				SlSmallloanProject slProject=null;
				if("LeaseFinance".equals(businessType)){
					flProject=flLeaseFinanceProjectService.get(Long.valueOf(slProjectId));
				}else{
					slProject=slSmallloanProjectService.get(Long.valueOf(slProjectId));
				}
				
				//保存款项信息（利率变更新重新计算利率）开始
				String slFundIentJson = this.getRequest().getParameter("intent_plan_earlyRepayment");
				Short isValid=null;
				Short isCheck=null;
				List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService.getlistbyslEarlyRepaymentId(Long.valueOf(slEarlyRepaymentId), businessType,Long.valueOf(slProjectId));
				for (SlFundIntent s : slFundIntentsAllsupervise) {
					isValid=s.getIsValid();
					isCheck=s.getIsCheck();
					if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
						slFundIntentService.remove(s);
					}
				}
				if(null != slFundIentJson && !"".equals(slFundIentJson)){
					String[] slFundIentJsonArr = slFundIentJson.split("@");
					for(int i=0; i<slFundIentJsonArr.length; i++) {
						String str = slFundIentJsonArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						try{
							SlFundIntent slFundIntent = (SlFundIntent)JSONMapper.toJava(parser.nextValue(),SlFundIntent.class);
							slFundIntent.setSlEarlyRepaymentId(Long.valueOf(slEarlyRepaymentId));
							slFundIntent.setProjectId(Long.valueOf(slProjectId));
							if("LeaseFinance".equals(businessType)){
								slFundIntent.setBusinessType(flProject.getBusinessType());
								slFundIntent.setProjectName(flProject.getProjectName());
								slFundIntent.setProjectNumber(flProject.getProjectNumber());
								slFundIntent.setCompanyId(flProject.getCompanyId());
							}else{
								slFundIntent.setBusinessType(slProject.getBusinessType());
								slFundIntent.setProjectName(slProject.getProjectName());
								slFundIntent.setProjectNumber(slProject.getProjectNumber());
								slFundIntent.setCompanyId(slProject.getCompanyId());
							}
							slFundIntent.setIsValid(isValid);
							BigDecimal lin = new BigDecimal(0.00);
							if(slFundIntent.getIncomeMoney().compareTo(lin)==0){
					        	slFundIntent.setNotMoney(slFundIntent.getPayMoney());
					        }else{
					        	slFundIntent.setNotMoney(slFundIntent.getIncomeMoney());
					        	
					        }
							slFundIntent.setAfterMoney(new BigDecimal(0));
							slFundIntent.setAccrualMoney(new BigDecimal(0));
							slFundIntent.setFlatMoney(new BigDecimal(0));
							slFundIntent.setIsCheck(isCheck);
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
			}
			return SUCCESS;
		}catch(Exception e ){
			e.printStackTrace();
			logger.error("利率变更编辑出错:"+e.getMessage());
			return "error";
		}
	}
	public String getIsChecked(){
		String slEarlyRepaymentId=this.getRequest().getParameter("slEarlyRepaymentId");
		String projectId=this.getRequest().getParameter("projectId");
		String businessType=this.getRequest().getParameter("businessType");
		List<SlFundIntent> list=slFundIntentService.getlistbyslEarlyRepaymentId(Long.valueOf(slEarlyRepaymentId), businessType, Long.valueOf(projectId));
		StringBuffer sb=new StringBuffer("{success:true");
		
		for(SlFundIntent s:list){
			if(s.getAfterMoney().compareTo(new BigDecimal(0))!=0){
				sb.append(",isChecked:true");
				break;
			}
		}
		sb.append("}");
		jsonString=sb.toString();
		return SUCCESS;
	}
	public String startBidPrePaymentProcess(){
		try{
			String str="";
			String bidId=this.getRequest().getParameter("bidId");
			if(null!=bidId && !bidId.equals("")){
				str=plBidPlanService.startBidPrePaymentProcess(Long.valueOf(bidId));
			}
			jsonString="{success:true,"+str+"}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String updateEarlyProjectInfo(){
		try{
			String fundProjectId=this.getRequest().getParameter("fundProjectId");
			String bidPlanId=this.getRequest().getParameter("bidPlanId");
			String isCheck=this.getRequest().getParameter("isCheck");
			String fundsJson=this.getRequest().getParameter("fundsJson");
			SlEarlyRepaymentRecord record=slEarlyRepaymentRecordService.get(slEarlyRepaymentRecord.getSlEarlyRepaymentId());
			BeanUtil.copyNotNullProperties(record, slEarlyRepaymentRecord);
			slEarlyRepaymentRecordService.merge(record);
			BpFundProject fundProject=bpFundProjectService.get(Long.valueOf(fundProjectId));
			PlBidPlan plan=plBidPlanService.get(Long.valueOf(bidPlanId));
			//投资人的放款收息表
			if(null!=fundsJson && !fundsJson.equals("")){
				List<BpFundIntent> list=bpFundIntentService.listbySlEarlyRepaymentId(Long.valueOf(bidPlanId), slEarlyRepaymentRecord.getSlEarlyRepaymentId());
				for(BpFundIntent f:list){
					if(f.getAfterMoney().compareTo(new BigDecimal(0))==0){
						bpFundIntentService.remove(f);
					}
				}
				bpFundIntentService.saveCommonFundIntent(fundsJson, plan, fundProject, Short.valueOf(isCheck));
			}
			// 意见说明
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String allList(){
		try{
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			List list=this.slEarlyRepaymentRecordService.allList(this.getRequest(), start, limit);
			Long count=this.slEarlyRepaymentRecordService.allListCount(getRequest());
			StringBuffer buff=new StringBuffer("{success:true,'totalCounts':"+count+",result:[");
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[]) list.get(i);
				buff.append("{\"slEarlyRepaymentId\":");
				buff.append(obj[0]);
				buff.append(",\"earlyDate\":'");
				if(null!=obj[1]){
					buff.append(sd.format(obj[1]));
				}
				buff.append("',\"earlyProjectMoney\":'");
				if(null!=obj[2]){
					buff.append(obj[2]);
				}
				buff.append("',\"bidPlanId\":");
				buff.append(obj[3]);
				buff.append(",\"bidProName\":'");
				buff.append(obj[4]);
				buff.append("',\"bidProNumber\":'");
				buff.append(obj[5]);
				buff.append("',\"penaltyDays\":'");
				if(null!=obj[6]){
					buff.append(obj[6]);
				}
				buff.append("',\"proName\":'");
				if(null!=obj[7] && !obj[7].toString().equals("")){
					buff.append(obj[7]);
				}
				if(null!=obj[8] && !obj[8].toString().equals("")){
					buff.append(obj[8]);
				}
				if(null!=obj[9] && !obj[9].toString().equals("")){
					buff.append(obj[9]);
				}
				if(null!=obj[10] && !obj[10].toString().equals("")){
					buff.append(obj[10]);
				}
				buff.append("',\"loanInterestMoney\":'");
				if(null!=obj[11]){
					buff.append(obj[11]);
				}
				buff.append("',\"interestPenaltyMoney\":'");
				if(null!=obj[12]){
					buff.append(obj[12]);
				}
				buff.append("',\"consultationMoney\":'");
				if(null!=obj[13]){
					buff.append(obj[13]);
				}
				buff.append("',\"serviceMoney\":'");
				if(null!=obj[14]){
					buff.append(obj[14]);
				}
				buff.append("',\"proType\":'");
				buff.append(obj[15]);
				buff.append("',\"allMoney\":'");
				BigDecimal allMoney=new BigDecimal(0);
				if(null!=obj[2]){
					allMoney=allMoney.add(new BigDecimal(obj[2].toString()));
				}
				if(null!=obj[11]){
					allMoney=allMoney.add(new BigDecimal(obj[11].toString()));
				}
				if(null!=obj[12]){
					allMoney=allMoney.add(new BigDecimal(obj[12].toString()));
				}
				if(null!=obj[13]){
					allMoney=allMoney.add(new BigDecimal(obj[13].toString()));
				}
				if(null!=obj[14]){
					allMoney=allMoney.add(new BigDecimal(obj[14].toString()));
				}
				buff.append(allMoney);
				buff.append("',\"projectId\":");
				if(null!=obj[16] && !obj[16].toString().equals("")){
					buff.append(obj[16]);
				}
				if(null!=obj[17] && !obj[17].toString().equals("")){
					buff.append(obj[17]);
				}
				if(null!=obj[18] && !obj[18].toString().equals("")){
					buff.append(obj[18]);
				}
				if(null!=obj[19] && !obj[19].toString().equals("")){
					buff.append(obj[19]);
				}
				buff.append(",\"fundProjectId\":");
				if(null!=obj[20] && !obj[20].toString().equals("")){
					buff.append(obj[20]);
				}
				if(null!=obj[21] && !obj[21].toString().equals("")){
					buff.append(obj[21]);
				}
				if(null!=obj[22] && !obj[22].toString().equals("")){
					buff.append(obj[22]);
				}
				if(null!=obj[23] && !obj[23].toString().equals("")){
					buff.append(obj[23]);
				}
				buff.append("},");
				
			}
			if(null!=list && list.size()>0){
				buff.deleteCharAt(buff.length()-1);
			}
			buff.append("]}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 标的提前还款记录导出到Excel中
	 */
	public void toExcel(){
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		List list=this.slEarlyRepaymentRecordService.allList(this.getRequest(), 0, 1000000);
			String [] tableHeader = {"序号","招标项目名称","招标项目编号","借款项目名称","提前还款日期","提前还款本金","截止利息金额","截止咨询费金额","截止服务费金额","补偿息天数","补偿息金额","合计"};
		try {
			ExcelHelper.exportSlEarlyRepaymentList(list,tableHeader,"提前还款列表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getSurplusProjectMoney(){
		try{
			StringBuffer buff=new StringBuffer("{success:true");
			String earlyDate=this.getRequest().getParameter("earlyDate");
			String earlyProjectMoney=this.getRequest().getParameter("earlyProjectMoney");
			String penaltyDays=this.getRequest().getParameter("penaltyDays");
			String businessType=this.getRequest().getParameter("businessType");
			String preceptId=this.getRequest().getParameter("preceptId");
			BpFundProject project=bpFundProjectService.get(Long.valueOf(preceptId));
			if(null!=earlyDate && !earlyDate.equals("") && null!=earlyProjectMoney && !earlyProjectMoney.equals("")){
				BigDecimal principalMoney= slFundIntentService.getPrincipalMoney(projectId,businessType,earlyDate,Long.valueOf(preceptId));
				BigDecimal money=project.getOwnJointMoney().subtract(new BigDecimal(earlyProjectMoney));
				if(null!=principalMoney){
					money=money.subtract(principalMoney);
				}
				buff.append(",surplusProjectMoney:"+money);
				if(null!=penaltyDays && !penaltyDays.equals("")){
					BigDecimal penaltyMoney=new BigDecimal(earlyProjectMoney).multiply(new BigDecimal(penaltyDays)).multiply(project.getAccrual().divide(new BigDecimal(3000),5,BigDecimal.ROUND_HALF_UP));
					buff.append(",penaltyMoney:"+penaltyMoney);
				}
			}
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	} 
	public String savePrepaymentInfo(){
		try{
			String fundProjectId=this.getRequest().getParameter("fundProjectId");
			SlEarlyRepaymentRecord record=slEarlyRepaymentRecordService.get(slEarlyRepaymentId);
			BeanUtil.copyNotNullProperties(record, slEarlyRepaymentRecord);
			slEarlyRepaymentRecordService.merge(record);
			BpFundProject project=bpFundProjectService.get(Long.valueOf(fundProjectId));
			//保存款项信息
			String fundIntentJsonData=this.getRequest().getParameter("earlyFundIntentJsonData");
			if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
				List<SlFundIntent> oldList = slFundIntentService.getlistbyslEarlyRepaymentId(record.getSlEarlyRepaymentId(), project.getBusinessType(), project.getProjectId());
				for (SlFundIntent sfi : oldList) {
					if (sfi.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						slFundIntentService.remove(sfi);
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
						SlFundIntent1.setIsCheck(record.getCheckStatus()==0?Short.valueOf("0"):Short.valueOf("1"));
						//}
						SlFundIntent1.setBusinessType(project.getBusinessType());
						SlFundIntent1.setCompanyId(project.getCompanyId());
						slFundIntentService.save(SlFundIntent1);
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
						SlFundIntent1.setIsCheck(record.getCheckStatus()==0?Short.valueOf("0"):Short.valueOf("1"));
						//}
						SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
						BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

						slFundIntentService.merge(slFundIntent2);

					}
					
				}
			}
			// 意见说明
			String taskId = this.getRequest().getParameter("task_id");
			String comments = this.getRequest().getParameter("comments");
			if (null != taskId && !"".equals(taskId) && null != comments
					&& !"".equals(comments.trim())) {
				this.creditProjectService.saveComments(taskId, comments);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
