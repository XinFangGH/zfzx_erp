package com.zhiwei.credit.service.creditFlow.smallLoan.project.impl;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
import java.io.File;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jbpm.api.TaskService;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.BeanUtils;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import cn.emay.process.emaysms.util.GetRequest;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.parser.JSONParser;
import com.webServices.custom.BaseCustomService;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.project.FundIntentListPro3;
import com.zhiwei.credit.core.project.StatsPro;
import com.zhiwei.credit.core.util.ElementUtil;
import com.zhiwei.credit.core.util.FileHelper;
import com.zhiwei.credit.core.util.JacobWord;
import com.zhiwei.credit.dao.creditFlow.archives.PlProjectArchivesDao;
import com.zhiwei.credit.dao.creditFlow.assuretenet.OurProcreditAssuretenetDao;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.dao.creditFlow.common.GlobalSupervisemanageDao;
import com.zhiwei.credit.dao.creditFlow.contract.DocumentTempletDao;
import com.zhiwei.credit.dao.creditFlow.contract.ProcreditContractDao;
import com.zhiwei.credit.dao.creditFlow.customer.common.EnterpriseBankDao;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.EnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.BpMoneyBorrowDemandDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.CsPersonCarDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.CsPersonHouseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.SpouseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.workcompany.WorkCompanyDao;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlActualToChargeDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidPlanDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.business.BpBusinessDirProDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.business.BpBusinessOrProDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.persion.BpPersionDirProDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.persion.BpPersionOrProDao;
import com.zhiwei.credit.dao.creditFlow.fund.project.BpFundProjectDao;
import com.zhiwei.credit.dao.creditFlow.materials.OurProcreditMaterialsEnterpriseDao;
import com.zhiwei.credit.dao.creditFlow.ourmain.SlCompanyMainDao;
import com.zhiwei.credit.dao.creditFlow.ourmain.SlPersonMainDao;
import com.zhiwei.credit.dao.creditFlow.personrelation.netcheck.BpPersonNetCheckInfoDao;
import com.zhiwei.credit.dao.creditFlow.personrelation.phonecheck.BpPersonPhonecheckInfoDao;
import com.zhiwei.credit.dao.creditFlow.repaymentSource.SlRepaymentSourceDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.BorrowerInfoDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlAlterAccrualRecordDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.meeting.SlConferenceRecordDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.ProjectPropertyClassificationDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.supervise.SlSuperviseRecordDao;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.dao.flow.TaskDao;
import com.zhiwei.credit.dao.flow.TaskSignDataDao;
import com.zhiwei.credit.dao.p2p.BpFinanceApplyUserDao;
import com.zhiwei.credit.dao.system.DictionaryDao;
import com.zhiwei.credit.dao.system.FileAttachDao;
import com.zhiwei.credit.dao.system.GlobalTypeDao;
import com.zhiwei.credit.dao.system.product.BpProductParameterDao;
import com.zhiwei.credit.model.creditFlow.archives.PlProjectArchives;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
import com.zhiwei.credit.model.creditFlow.common.GlobalSupervisemanage;
import com.zhiwei.credit.model.creditFlow.contract.AssureIntentBookElementCode;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.SmallLoanElementCode;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.person.BpMoneyBorrowDemand;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonCar;
import com.zhiwei.credit.model.creditFlow.customer.person.CsPersonHouse;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.customer.person.workcompany.WorkCompany;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.ProYearRate;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.VFundDetail;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.model.creditFlow.personrelation.netcheck.BpPersonNetCheckInfo;
import com.zhiwei.credit.model.creditFlow.personrelation.phonecheck.BpPersonPhonecheckInfo;
import com.zhiwei.credit.model.creditFlow.repaymentSource.SlRepaymentSource;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.BorrowerInfo;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlUrgeRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.meeting.SlConferenceRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.ProjectPropertyClassification;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.VSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.model.p2p.BpFinanceApplyUser;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.model.system.product.BpProductParameter;
import com.zhiwei.credit.service.creditFlow.common.CreditProjectService;
import com.zhiwei.credit.service.creditFlow.contract.ElementHandleService;
import com.zhiwei.credit.service.creditFlow.contract.ProcreditContractService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.finance.FundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.VFundDetailService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlUrgeRecordService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.system.OrganizationService;
import com.zhiwei.credit.util.ProjectActionUtil;

/**
 * 
 * @author
 * 
 */
@SuppressWarnings({"static-access","unused","unchecked"})
public class SlSmallloanProjectServiceImpl extends
		BaseServiceImpl<SlSmallloanProject> implements
		SlSmallloanProjectService {
	private SlSmallloanProjectDao dao;
	@Resource
	private SlCompanyMainDao slCompanyMainDao;
	@Resource
	private SlPersonMainDao slPersonMainDao;
	
	public SlSmallloanProjectServiceImpl(SlSmallloanProjectDao dao) {
		super(dao);
		this.dao = dao;
	}
	@Resource
	private SlUrgeRecordService slUrgeRecordService;
	@Resource
	private PlBidPlanService plBidPlanService;
	@Resource
	private BpFundIntentDao bpFundIntentDao;
	

	@Resource
	private PersonDao personDao;

	@Resource
	private EnterpriseDao enterpriseDao;

	@Resource
	private EnterpriseShareequityDao enterpriseShareequityDao;
	@Resource
	private SlActualToChargeDao slActualToChargeDao;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlRepaymentSourceDao slRepaymentSourceDao;
	@Resource
	private SlSuperviseRecordDao slSuperviseRecordDao;
	@Resource
	private GlobalTypeDao globalTypeDao;
	@Resource
	private DictionaryDao dictionaryDao;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private ProDefinitionDao proDefinitionDao;
	@Resource
	private MortgageService mortgageService;
	@Resource
	private TaskService taskService;
	
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private CreditProjectService creditProjectService;
	@Resource
	private SlEarlyRepaymentRecordDao slEarlyRepaymentRecordDao;
	@Resource
	private SlAlterAccrualRecordDao slAlterAccrualRecordDao;
	
	@Resource
	private PlProjectArchivesDao plProjectArchivesDao;
	@Resource
	private BorrowerInfoDao borrowerInfoDao;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private TaskSignDataDao taskSignDataDao;
	@Resource
	private BaseCustomService baseCustomService;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private ElementHandleService elementHandleService;
	@Resource
	private ProcreditContractService procreditContractService;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private SlEarlyRepaymentRecordService slEarlyRepaymentRecordService;
	@Resource
	private VFundDetailService vFundDetailService;
	@Resource(name = "flowTaskService")
	private com.zhiwei.credit.service.flow.TaskService flowTaskService;
	@Resource
	private SlConferenceRecordDao slConferenceRecordDao;
	@Resource
	private TaskDao taskDao;
	@Resource
	private ProjectPropertyClassificationDao projectPropertyClassificationDao;
	@Resource
	private DocumentTempletDao documentTempletDao;
	@Resource
	private FileAttachDao fileAttachDao;
	@Resource
	private ProcreditContractDao procreditContractDao;
	@Resource
	private GlobalSupervisemanageDao globalSupervisemanageDao;
	
	@Resource
	private BpMoneyBorrowDemandDao bpMoneyBorrowDemandDao;// 借款需求
	@Resource
	private CsPersonHouseDao csPersonHouseDao;//房产信息
	@Resource
	private CsPersonCarDao csPersonCarDao;//车产信息
	@Resource
	private WorkCompanyDao workCompanyDao;
	@Resource
	private BpPersonPhonecheckInfoDao bpPersonPhonecheckInfoDao;
	@Resource
	private BpPersonNetCheckInfoDao bpPersonNetCheckInfoDao;
	@Resource
	private SpouseDao spouseDao;
	@Resource
	private OurProcreditMaterialsEnterpriseDao ourProcreditMaterialsEnterpriseDao;
	@Resource
	private OurProcreditAssuretenetDao ourProcreditAssuretenetDao;
	
	@Resource
	private BpFundProjectDao bpFundProjectDao;
	
	private AssureIntentBookElementCode assureIntentBookElementCode;
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	
	@Resource
	private BpPersionDirProDao bpPersionDirProDao;
	@Resource
	private BpBusinessDirProDao bpBusinessDirProDao;
	@Resource
	private InvestEnterpriseService investEnterpriseService;
	@Resource
	private BpFundProjectService bpFundProjectService;
	@Resource
	private FundIntentService fundIntentService;
	@Resource
	private ProcessRunService processRunService;
	@Resource
	private BpBusinessOrProDao bpBusinessOrProDao;
	@Resource
	private BpPersionOrProDao bpPersionOrProDao;
	@Resource
	private BpProductParameterDao bpProductParameterDao;
	@Resource
	private PlBidPlanDao plBidPlanDao;
	@Resource
	private EnterpriseBankDao enterpriseBankDao;
	@Resource
	private SlProcreditMaterialsService slProcreditMaterialsService;
	@Resource
	private BpFinanceApplyUserDao bpFinanceApplyUserDao;
	@Resource
	private PlMmObligatoryRightChildrenService plMmObligatoryRightChildrenService;
	
	
	//个人直投
	public void initPersionP2p(BpFundProject platFormFund,BpPersionDirPro bpPersionDirPro,Person person){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		bpPersionDirPro.setMoneyPlanId(platFormFund.getId());//项目Id
		bpPersionDirPro.setProId(platFormFund.getProjectId());
		bpPersionDirPro.setBusinessType(platFormFund.getBusinessType());//业务品种
		bpPersionDirPro.setProName(platFormFund.getProjectName()+"_P2P");//项目名称
		bpPersionDirPro.setProNumber(platFormFund.getProjectNumber()+"_P2P");//项目编号
		bpPersionDirPro.setYearInterestRate(platFormFund.getYearAccrualRate());//年化利率
		bpPersionDirPro.setMonthInterestRate(platFormFund.getAccrual());//月化利率
		bpPersionDirPro.setDayInterestRate(platFormFund.getDayAccrualRate());//日化利率
		bpPersionDirPro.setTotalInterestRate(platFormFund.getSumAccrualRate());//合计利率
		bpPersionDirPro.setInterestPeriod("1");//计息周期
		bpPersionDirPro.setReceiverName(platFormFund.getReceiverName());//收款人名称
		bpPersionDirPro.setReceiverP2PAccountNumber(platFormFund.getReceiverP2PAccountNumber());//收款人p2p账号
		
		if("sameprincipalandInterest".equals(platFormFund.getAccrualtype())){//等额本息
			bpPersionDirPro.setPayIntersetWay("1");
		}else if("sameprincipal".equals(platFormFund.getAccrualtype())){//等额本金
			bpPersionDirPro.setPayIntersetWay("2");
		}else if("sameprincipalsameInterest".equals(platFormFund.getAccrualtype())){//等本等息
			bpPersionDirPro.setPayIntersetWay("3");
		}else if("singleInterest".equals(platFormFund.getAccrualtype())){//按期收息,到期还本
			bpPersionDirPro.setPayIntersetWay("4");
		}
		if("1".equals(platFormFund.getIsInterestByOneTime())){//一次性支付全部利息
			bpPersionDirPro.setPayIntersetWay("5");
		}
         bpPersionDirPro.setPayAcctualType(platFormFund.getPayaccrualType());// 还款周期类型 日月季年 自定义
		
		if(platFormFund.getPayaccrualType().equals("owerPay")){
			bpPersionDirPro.setCustDate(platFormFund.getDayOfEveryPeriod());
		}
		bpPersionDirPro.setBidMoney(platFormFund.getPlatFormJointMoney());//未招标金额
		bpPersionDirPro.setLoanLife(platFormFund.getPayintentPeriod());//期望借款期限
		bpPersionDirPro.setBidTime(platFormFund.getStartDate());//发标日期
		bpPersionDirPro.setCreateTime(new Date());//创建日期
		bpPersionDirPro.setUpdateTime(new Date());//修改日期
		bpPersionDirPro.setKeepStat(0);//维护状态
		bpPersionDirPro.setSchemeStat(0);//方案状态
		
		bpPersionDirPro.setPersionId(Long.valueOf(person.getId()));//个人Id
		bpPersionDirPro.setPersionName(person.getName());//个人名称
		if(null != person.getSex()){
			bpPersionDirPro.setSex(dictionaryDao.get(Long.valueOf(person.getSex())).getItemValue());//性别
		}
		if(null!=person.getDgree()){
			Dictionary dictionary1=dictionaryDao.get(Long.valueOf(person.getDgree()));
			if(null!=dictionary1){
				bpPersionDirPro.setEducation(dictionary1.getItemValue());//学历
			}
		}
		if(null != person.getMarry()){
			Dictionary dictionary2=dictionaryDao.get(Long.valueOf(person.getMarry()));
			if(null!=dictionary2){
				bpPersionDirPro.setMarriage(dictionary2.getItemValue());//婚姻
			}
		}
		if(null!=person.getJob()){
			Dictionary dictionary3=dictionaryDao.get(Long.valueOf(person.getJob()));
			if(null!=dictionary3){
				bpPersionDirPro.setPosition(dictionary3.getItemValue());//职务
			}
		}
		
		
		bpPersionDirPro.setAge(person.getAge());//年龄
		bpPersionDirPro.setAddress(person.getFamilyaddress());//现居住地
		if(person.getJobstarttime()==null||"".equals(person.getJobstarttime())){
			bpPersionDirPro.setWorkTime(null);//工作时间
		}else{
			bpPersionDirPro.setWorkTime(format.format(person.getJobstarttime()));//工作时间
		}
		bpPersionDirPro.setWorkCity(person.getUnitaddress());//工作城市
		bpPersionDirPro.setUserName("");//用户名
		if(person.getJobincome()!=null){//月收入
			bpPersionDirPro.setMonthIncome(new BigDecimal(person.getJobincome().toString()));
		}
		List<CsPersonHouse> csPersonHouseList=csPersonHouseDao.getByPersonId(person.getId().toString()); //房产  房贷
		if(null!=csPersonHouseList && csPersonHouseList.size()>0){
			bpPersionDirPro.setHouseProperty(0);
			boolean flag=false;
			for(int i=0;i<csPersonHouseList.size();i++){
				if("1".equals(csPersonHouseList.get(i).getIsMortgage())){
					flag=true;
					break;
				}else{
					continue;
				}
			}
			if(flag){
				bpPersionDirPro.setHouseLoan(0);
			}else{
				bpPersionDirPro.setHouseLoan(1);
			}
		}else{
			bpPersionDirPro.setHouseProperty(1);
		}
		List<CsPersonCar> csPersonCarList=csPersonCarDao.getByPersonId(person.getId().toString()); //车产  车贷
		if(null!=csPersonCarList && csPersonCarList.size()>0){
			bpPersionDirPro.setVehicleProperty(0);
			boolean flag=false;
			for(int i=0;i<csPersonCarList.size();i++){
				if("1".equals(csPersonCarList.get(i).getIsMortgage())){
					flag=true;
					break;
				}else{
					continue;
				}
			}
			if(flag){
				bpPersionDirPro.setVehicleLoan(0);
			}else{
				bpPersionDirPro.setVehicleLoan(1);
			}
		}else{
			bpPersionDirPro.setVehicleProperty(1);
		}
		bpPersionDirPro.setCompanyIndustry(person.getHangyeName());//行业
		if(null !=person.getCompanyScale()&&!"".equals(person.getCompanyScale()) && !"null".equals(person.getCompanyScale())){
			Dictionary dictionary=dictionaryDao.get(Long.valueOf(person.getCompanyScale()));
			bpPersionDirPro.setCompanyScale(dictionary.getItemValue());//公司规模
		}
	}
	//企业直投
	public void initBusinessP2p(BpFundProject platFormFund,BpBusinessDirPro bpBusinessDirPro,Enterprise enterprise){
		bpBusinessDirPro.setProId(platFormFund.getProjectId());
		bpBusinessDirPro.setMoneyPlanId(platFormFund.getId());
		bpBusinessDirPro.setBusinessType(platFormFund.getBusinessType()); 
		bpBusinessDirPro.setBusinessId(Long.parseLong(enterprise.getId()+""));
		bpBusinessDirPro.setBusinessName(enterprise.getEnterprisename());
		bpBusinessDirPro.setProName(platFormFund.getProjectName()+"_P2P");
		bpBusinessDirPro.setProNumber(platFormFund.getProjectNumber()+"_P2P");
		bpBusinessDirPro.setYearInterestRate(platFormFund.getYearAccrualRate());
		bpBusinessDirPro.setMonthInterestRate(platFormFund.getAccrual());
		bpBusinessDirPro.setTotalInterestRate(platFormFund.getDayAccrualRate());
		bpBusinessDirPro.setDayInterestRate(platFormFund.getDayAccrualRate());
		//bpBusinessDirPro.setInterestPeriod(platFormFund.getPayintentPeriod()+"");
		bpBusinessDirPro.setInterestPeriod("1");//计息周期
	
		bpBusinessDirPro.setReceiverName(platFormFund.getReceiverName());//收款人名称
		bpBusinessDirPro.setReceiverP2PAccountNumber(platFormFund.getReceiverP2PAccountNumber());//收款人的p2p账号
		  // interestPeriod       varchar(10) comment '计息周期',
		if("sameprincipalandInterest".equals(platFormFund.getAccrualtype())){//等额本息
			bpBusinessDirPro.setPayIntersetWay("1");
		}else if("sameprincipal".equals(platFormFund.getAccrualtype())){//等额本金
			bpBusinessDirPro.setPayIntersetWay("2");
		}else if("sameprincipalsameInterest".equals(platFormFund.getAccrualtype())){//等本等息
			bpBusinessDirPro.setPayIntersetWay("3");
		}else if("singleInterest".equals(platFormFund.getAccrualtype())){//按期收息,到期还本
			bpBusinessDirPro.setPayIntersetWay("4");
		}
		if("1".equals(platFormFund.getIsInterestByOneTime())){//一次性支付全部利息
			bpBusinessDirPro.setPayIntersetWay("5");
		}
		
		bpBusinessDirPro.setPayAcctualType(platFormFund.getPayaccrualType());// 还款周期类型 日月季年 自定义
		
		if(platFormFund.getPayaccrualType().equals("owerPay")){
			bpBusinessDirPro.setCustDate(platFormFund.getDayOfEveryPeriod());
		}
		bpBusinessDirPro.setBidMoney(platFormFund.getPlatFormJointMoney());//未招标金额
		bpBusinessDirPro.setLoanLife(platFormFund.getPayintentPeriod());//期望借款期限
		bpBusinessDirPro.setBidTime(null);//发标日期
		bpBusinessDirPro.setCreateTime(new Date());//创建日期
		bpBusinessDirPro.setUpdateTime(new Date());//修改日期
		bpBusinessDirPro.setKeepStat(0);//维护状态
		bpBusinessDirPro.setSchemeStat(0);//方案状态 
		
	}
	//个人债券直投
	public void initPersionP2p(BpFundProject platFormFund,BpPersionOrPro bpPersionOrPro,Person person){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		bpPersionOrPro.setMoneyPlanId(platFormFund.getId());//项目Id
		bpPersionOrPro.setProId(platFormFund.getProjectId());
		bpPersionOrPro.setBusinessType(platFormFund.getBusinessType());//业务品种
		bpPersionOrPro.setProName(platFormFund.getProjectName()+"_P2P");//项目名称
		bpPersionOrPro.setProNumber(platFormFund.getProjectNumber()+"_P2P");//项目编号
		bpPersionOrPro.setYearInterestRate(platFormFund.getYearAccrualRate());//年化利率
		bpPersionOrPro.setMonthInterestRate(platFormFund.getAccrual());//月化利率
		bpPersionOrPro.setDayInterestRate(platFormFund.getDayAccrualRate());//日化利率
		bpPersionOrPro.setTotalInterestRate(platFormFund.getSumAccrualRate());//合计利率
		bpPersionOrPro.setInterestPeriod("1");//计息周期
		bpPersionOrPro.setReciverType(platFormFund.getReciverType());//收款人的类型
		bpPersionOrPro.setReciverId(platFormFund.getReciverId());//收款人的主键Id
		bpPersionOrPro.setReceiverName(platFormFund.getReceiverName());//收款人名称
		bpPersionOrPro.setReceiverP2PAccountNumber(platFormFund.getReceiverP2PAccountNumber());//收款人的p2p账号
		if("sameprincipalandInterest".equals(platFormFund.getAccrualtype())){//等额本息
			bpPersionOrPro.setPayIntersetWay("1");
		}else if("sameprincipal".equals(platFormFund.getAccrualtype())){//等额本金
			bpPersionOrPro.setPayIntersetWay("2");
		}else if("sameprincipalsameInterest".equals(platFormFund.getAccrualtype())){//等本等息
			bpPersionOrPro.setPayIntersetWay("3");
		}else if("singleInterest".equals(platFormFund.getAccrualtype())){//按期收息,到期还本
			bpPersionOrPro.setPayIntersetWay("4");
		}
		if("1".equals(platFormFund.getIsInterestByOneTime())){//一次性支付全部利息
			bpPersionOrPro.setPayIntersetWay("5");
		}
		
		bpPersionOrPro.setPayAcctualType(platFormFund.getPayaccrualType());// 还款周期类型 日月季年 自定义
		
		if(platFormFund.getPayaccrualType().equals("owerPay")){
			bpPersionOrPro.setCustDate(platFormFund.getDayOfEveryPeriod());
		}
		bpPersionOrPro.setBidMoney(platFormFund.getOwnJointMoney());//未招标金额
	//	bpPersionOrPro.set
		bpPersionOrPro.setLoanLife(platFormFund.getPayintentPeriod().toString());//期望借款期限
		bpPersionOrPro.setBidTime(platFormFund.getPoupseDate());//发标日期
		bpPersionOrPro.setCreateTime(new Date());//创建日期
		bpPersionOrPro.setUpdateTime(new Date());//修改日期
		bpPersionOrPro.setKeepStat(0);//维护状态
		bpPersionOrPro.setSchemeStat(0);//方案状态
		bpPersionOrPro.setLoanStarTime(platFormFund.getStartDate());
		bpPersionOrPro.setLoanEndTime(platFormFund.getIntentDate());
		
		bpPersionOrPro.setPersionId(Long.valueOf(person.getId()));//个人Id
		bpPersionOrPro.setPersionName(person.getName());//个人名称
		bpPersionOrPro.setSex(dictionaryDao.get(Long.valueOf(person.getSex())).getItemValue());//性别
		if(null!=person.getDgree()){
			Dictionary dictionary1=dictionaryDao.get(Long.valueOf(person.getDgree()));
			if(null!=dictionary1){
				bpPersionOrPro.setEducation(dictionary1.getItemValue());//学历
			}
		}
		
		Dictionary dictionary2=dictionaryDao.get(Long.valueOf(person.getMarry()));
		if(null!=dictionary2){
			bpPersionOrPro.setMarriage(dictionary2.getItemValue());//婚姻
		}
		if(null!=person.getJob()){
			Dictionary dictionary3=dictionaryDao.get(Long.valueOf(person.getJob()));
			if(null!=dictionary3){
				bpPersionOrPro.setPosition(dictionary3.getItemValue());//职务
			}
		}
		//获取到投资方的
		if(platFormFund.getInvestorIds()!=null&&platFormFund.getFundResource()!=null&&!"".equals(platFormFund.getInvestorIds())){
			String[] ids = platFormFund.getInvestorIds().split(",");
			if(ids!=null&&ids.length!=0){
				if(null!=ids[0]&&!"".equals(ids[0])){
					InvestEnterprise enter = investEnterpriseService.get(Long.parseLong(ids[0]));
					if(enter!=null){
						bpPersionOrPro.setObligatoryPersion(enter.getEnterprisename());
					}else{
						
						bpPersionOrPro.setObligatoryPersion("彩云金融");
					}
				}else{
					bpPersionOrPro.setObligatoryPersion("彩云金融");
				}
			}else{
				bpPersionOrPro.setObligatoryPersion("彩云金融");
			}
		}else{
			bpPersionOrPro.setObligatoryPersion("彩云金融");
		}
		
		bpPersionOrPro.setAge(person.getAge());//年龄
		bpPersionOrPro.setAddress(person.getFamilyaddress());//现居住地
		
		if(person.getJobstarttime()==null||"".equals(person.getJobstarttime())){
			bpPersionOrPro.setWorkTime(null);//工作时间
		}else{
			bpPersionOrPro.setWorkTime(format.format(person.getJobstarttime()));//工作时间
		}
		bpPersionOrPro.setWorkCity(person.getUnitaddress());//工作城市
		bpPersionOrPro.setUserName("");//用户名
		
		List<CsPersonHouse> csPersonHouseList=csPersonHouseDao.getByPersonId(person.getId().toString()); //房产  房贷
		if(null!=csPersonHouseList && csPersonHouseList.size()>0){
			bpPersionOrPro.setHouseProperty(0);
			boolean flag=false;
			for(int i=0;i<csPersonHouseList.size();i++){
				if("1".equals(csPersonHouseList.get(i).getIsMortgage())){
					flag=true;
					break;
				}else{
					continue;
				}
			}
			if(flag){
				bpPersionOrPro.setHouseLoan(0);
			}else{
				bpPersionOrPro.setHouseLoan(1);
			}
		}else{
			bpPersionOrPro.setHouseProperty(1);
		}
		List<CsPersonCar> csPersonCarList=csPersonCarDao.getByPersonId(person.getId().toString()); //车产  车贷
		if(null!=csPersonCarList && csPersonCarList.size()>0){
			bpPersionOrPro.setVehicleProperty(0);
			boolean flag=false;
			for(int i=0;i<csPersonCarList.size();i++){
				if("1".equals(csPersonCarList.get(i).getIsMortgage())){
					flag=true;
					break;
				}else{
					continue;
				}
			}
			if(flag){
				bpPersionOrPro.setVehicleLoan(0);
			}else{
				bpPersionOrPro.setVehicleLoan(1);
			}
		}else{
			bpPersionOrPro.setVehicleProperty(1);
		}
		bpPersionOrPro.setCompanyIndustry(person.getHangyeName());//行业
		if(null !=person.getCompanyScale() && !"".equals(person.getCompanyScale())){
			Dictionary dictionary=dictionaryDao.get(Long.valueOf(person.getCompanyScale()));
			bpPersionOrPro.setCompanyScale(dictionary.getItemValue());//公司规模
		}
		bpPersionOrPro.setAddress(person.getPostaddress());
		bpPersionOrPro.setMonthIncome(person.getJobincome()!=null?new BigDecimal(person.getJobincome().toString()):new BigDecimal("0"));
	}
	//企业债券转让
	public void initBusinessP2p(BpFundProject platFormFund,BpBusinessOrPro bpBusinessOrPro,Enterprise enterprise){
		bpBusinessOrPro.setProId(platFormFund.getProjectId());
		bpBusinessOrPro.setMoneyPlanId(platFormFund.getId());
		bpBusinessOrPro.setBusinessType(platFormFund.getBusinessType()); 
		bpBusinessOrPro.setBusinessId(Long.parseLong(enterprise.getId()+""));
		bpBusinessOrPro.setBusinessName(enterprise.getEnterprisename());
		bpBusinessOrPro.setProName(platFormFund.getProjectName()+"_P2P");
		bpBusinessOrPro.setProNumber(platFormFund.getProjectNumber()+"_P2P");
		bpBusinessOrPro.setYearInterestRate(platFormFund.getYearAccrualRate());
		bpBusinessOrPro.setMonthInterestRate(platFormFund.getAccrual());
		bpBusinessOrPro.setTotalInterestRate(platFormFund.getDayAccrualRate());
		bpBusinessOrPro.setDayInterestRate(platFormFund.getDayAccrualRate());
		//bpBusinessOrPro.setInterestPeriod(platFormFund.getPayintentPeriod()+"");
		bpBusinessOrPro.setInterestPeriod("1");//计息周期
		bpBusinessOrPro.setReciverType(platFormFund.getReciverType());//收款人的类型
		bpBusinessOrPro.setReciverId(platFormFund.getReciverId());//收款人的主键Id
		bpBusinessOrPro.setReceiverName(platFormFund.getReceiverName());//收款人名称
		bpBusinessOrPro.setReceiverP2PAccountNumber(platFormFund.getReceiverP2PAccountNumber());//收款人的p2p账号
		  // interestPeriod       varchar(10) comment '计息周期',
		if("sameprincipalandInterest".equals(platFormFund.getAccrualtype())){//等额本息
			bpBusinessOrPro.setPayIntersetWay("1");
		}else if("sameprincipal".equals(platFormFund.getAccrualtype())){//等额本金
			bpBusinessOrPro.setPayIntersetWay("2");
		}else if("sameprincipalsameInterest".equals(platFormFund.getAccrualtype())){//等本等息
			bpBusinessOrPro.setPayIntersetWay("3");
		}else if("singleInterest".equals(platFormFund.getAccrualtype())){//按期收息,到期还本
			bpBusinessOrPro.setPayIntersetWay("4");
		}
		if("1".equals(platFormFund.getIsInterestByOneTime())){//一次性支付全部利息
			bpBusinessOrPro.setPayIntersetWay("5");
		}
		bpBusinessOrPro.setPayAcctualType(platFormFund.getPayaccrualType());// 还款周期类型 日月季年 自定义
		
		if(platFormFund.getPayaccrualType().equals("owerPay")){
			bpBusinessOrPro.setCustDate(platFormFund.getDayOfEveryPeriod());
		}
		bpBusinessOrPro.setBidMoney(platFormFund.getOwnJointMoney());//未招标金额
		bpBusinessOrPro.setLoanLife(platFormFund.getPayintentPeriod());//期望借款期限
		bpBusinessOrPro.setBidTime(platFormFund.getPoupseDate());//发标日期
		bpBusinessOrPro.setCreateTime(new Date());//创建日期
		bpBusinessOrPro.setUpdateTime(new Date());//修改日期
		bpBusinessOrPro.setKeepStat(0);//维护状态
		//获取到投资方的
		if(platFormFund.getInvestorIds()!=null&&platFormFund.getFundResource()!=null){
			String[] ids = platFormFund.getInvestorIds().split(",");
			if(ids!=null&&ids.length!=0){
				if(null!=ids[0]&&!"".equals(ids[0])){
					InvestEnterprise enter = investEnterpriseService.get(Long.parseLong(ids[0]));
					if(enter!=null){
						bpBusinessOrPro.setObligatoryPersion(enter.getEnterprisename());
					}else{
						bpBusinessOrPro.setObligatoryPersion("彩云金融");
					}
				}else{
					bpBusinessOrPro.setObligatoryPersion("彩云金融");
				}
				
			}else{
				bpBusinessOrPro.setObligatoryPersion("彩云金融");
			}
		}else{
			bpBusinessOrPro.setObligatoryPersion("彩云金融");
		}
		bpBusinessOrPro.setSchemeStat(0);//方案状态 
		bpBusinessOrPro.setLoanStarTime(platFormFund.getStartDate());
		bpBusinessOrPro.setLoanEndTime(platFormFund.getIntentDate());
	}
	
	
	/***
	 * 制定资金方案提交方法
	 */
	@Override
	public Integer draftFundSchemeNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String platFormBpFundProjectMoney = flowRunInfo.getRequest().getParameter("platFormBpFundProject.platFormJointMoney");
				String ownBpFundProjectMoney=flowRunInfo.getRequest().getParameter("ownBpFundProject.ownJointMoney");
				String transitionName = flowRunInfo.getTransitionName();
				
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							
							BpFundProject oldBpFund1=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort("0"));
							BeanUtil.populateEntity(flowRunInfo.getRequest(), oldBpFund1, "ownBpFundProject");
							bpFundProjectDao.merge(oldBpFund1);
							
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (isToNextTask) {//挂起资金匹配,挂起项目。
								SlSmallloanProject sl = dao.get(Long.valueOf(projectId));
								String slFundIentJson = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
								if(null!=sl){
									String sumintent= slFundIntentService.savejson(slFundIentJson, Long.valueOf(projectId),"SmallLoan",Short.parseShort("0"),1L,oldBpFund1.getId(),oldBpFund1.getFundResource());
									if(!sl.getAccrualtype().equals("singleInterest")){
										sl.setPayintentPeriod(Integer.valueOf(sumintent));
									}
									sl.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
								}
							/*	if(transitionName.contains("资金匹配")){
									sl.setProjectStatus(Constants.PROJECT_STATUS_SUSPENDED);
									vars.put("isSuspendedProject", "true");
								}*/
								dao.merge(sl);
							}
							
							
							BpFundProject oldBpFund2=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort("1"));
							BeanUtil.populateEntity(flowRunInfo.getRequest(), oldBpFund2, "platFormBpFundProject");
							bpFundProjectDao.merge(oldBpFund2);
							
							//保存个人直投标项目缓信息
							BpPersionDirPro  bpPersionDirPro =bpPersionDirProDao.getByBpFundProjectId(oldBpFund2.getId());
							if(null==bpPersionDirPro){
								bpPersionDirPro=new BpPersionDirPro();
								Person person=personDao.getById(Integer.parseInt((oldBpFund2.getOppositeID().toString())));//客户信息
								this.initPersionP2p(oldBpFund2,bpPersionDirPro,person);
								bpPersionDirProDao.save(bpPersionDirPro);
//								bpPersionDirPro.setMonthIncome();
//								bpPersionDirPro.setCompanyIndustry(aValue)//公司行业     个人所在公司信息
//								bpPersionDirPro.setCompanyScale(aValue)//公司规模
							}else{
								Person person=personDao.getById(Integer.parseInt((oldBpFund2.getOppositeID().toString())));//客户信息
								this.initPersionP2p(oldBpFund2,bpPersionDirPro,person);
								bpPersionDirProDao.merge(bpPersionDirPro);
							}
						}
					}
					vars.put("DraftFundSchemeResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-制定资金方案提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	
	
	/***
	 * 创典制定资金方案提交方法
	 */
	@Override
	public Integer draftFundSchemeNextStep2(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String platFormBpFundProjectMoney = flowRunInfo.getRequest().getParameter("platFormBpFundProject.platFormJointMoney");
				String ownBpFundProjectMoney=flowRunInfo.getRequest().getParameter("ownBpFundProject.ownJointMoney");
				String transitionName = flowRunInfo.getTransitionName();
				
				if (transitionName != null && !"".equals(transitionName)) {
					 if(Long.valueOf(platFormBpFundProjectMoney)==0||Long.valueOf(platFormBpFundProjectMoney)==null||platFormBpFundProjectMoney==""){
						//doNext(flowRunInfo);//如果平台资金为0 则不匹配直投表，直接进入下个节点
						 SlSmallloanProject project = dealSmallloan(flowRunInfo);
							if(null!=project){
								//保存客户信息
								try {
									dealCustomer(flowRunInfo, project);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
								try {
									dealBankAccount(flowRunInfo,project);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								//保存手续费用清单
								dealActualToCharge(flowRunInfo, project);
								//保存共同借款人
								dealBorrowers(flowRunInfo, project);
							}
							goToNext(flowRunInfo);
					}else{					
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						generateProduct(flowRunInfo);
						/*
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							
							BpFundProject oldBpFund1=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort("0"));
							BeanUtil.populateEntity(flowRunInfo.getRequest(), oldBpFund1, "ownBpFundProject");
							bpFundProjectDao.merge(oldBpFund1);
							
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (isToNextTask) {//挂起资金匹配,挂起项目。
								SlSmallloanProject sl = dao.get(Long.valueOf(projectId));
								String slFundIentJson = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
								if(null!=sl){
									String sumintent= slFundIntentService.savejson(slFundIentJson, Long.valueOf(projectId),"SmallLoan",Short.parseShort("0"),1L,oldBpFund1.getId(),oldBpFund1.getFundResource());
									if(!sl.getAccrualtype().equals("singleInterest")){
										sl.setPayintentPeriod(Integer.valueOf(sumintent));
									}
									sl.setEndDate(DateUtil.parseDate(DateUtil.getNowDateTime("yyyy-MM-dd"),"yyyy-MM-dd"));
								}
								if(transitionName.contains("资金匹配")){
									sl.setProjectStatus(Constants.PROJECT_STATUS_SUSPENDED);
									vars.put("isSuspendedProject", "true");
								}
								dao.merge(sl);
							}
							
							
							BpFundProject oldBpFund2=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort("1"));
							BeanUtil.populateEntity(flowRunInfo.getRequest(), oldBpFund2, "platFormBpFundProject");
							bpFundProjectDao.merge(oldBpFund2);
							
							//保存个人直投标项目缓信息
							BpPersionDirPro  bpPersionDirPro =bpPersionDirProDao.getByBpFundProjectId(oldBpFund2.getId());
							if(null==bpPersionDirPro){
								bpPersionDirPro=new BpPersionDirPro();
								Person person=personDao.getById(Integer.parseInt((oldBpFund2.getOppositeID().toString())));//客户信息
								this.initPersionP2p(oldBpFund2,bpPersionDirPro,person);
								bpPersionDirProDao.save(bpPersionDirPro);
//								bpPersionDirPro.setMonthIncome();
//								bpPersionDirPro.setCompanyIndustry(aValue)//公司行业     个人所在公司信息
//								bpPersionDirPro.setCompanyScale(aValue)//公司规模
							}else{
								Person person=personDao.getById(Integer.parseInt((oldBpFund2.getOppositeID().toString())));//客户信息
								this.initPersionP2p(oldBpFund2,bpPersionDirPro,person);
								bpPersionDirProDao.merge(bpPersionDirPro);
							}
						}
					*/
						}
					vars.put("OutletPrincipalExamineResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}	
			  }
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-制定资金方案提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	
	/***
	 * 门店负责人分派任务
	 */
	@Override
	public Integer outletAssignTaskNextStep(FlowRunInfo flowRunInfo){

		try{
			/*String netCheckPerson=flowRunInfo.getRequest().getParameter("netCheckPerson");
			String phoneCheckPerson=flowRunInfo.getRequest().getParameter("phoneCheckPerson");
			String assessPerson=flowRunInfo.getRequest().getParameter("assessPerson");*/
			String personId=flowRunInfo.getRequest().getParameter("personId");
			String projectId=flowRunInfo.getRequest().getParameter("projectId");
			String netCheckPerson="";
			String phoneCheckPerson="";
			String assessPerson="";
			String[] assignIds=flowRunInfo.getRequest().getParameterValues("flowAssignId");
			netCheckPerson=assignIds[1];
			phoneCheckPerson=assignIds[2];
			assessPerson=assignIds[3];
			String transitionName = flowRunInfo.getTransitionName();
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				//联系人信息
				List<PersonRelation> listRelation=personDao.getPersonByPersonId(Integer.parseInt(personId));
				if(null!=listRelation && listRelation.size()>0){
					for(int i=0;i<listRelation.size();i++){
						PersonRelation p=listRelation.get(i);
						BpPersonPhonecheckInfo oldPhonecheck=bpPersonPhonecheckInfoDao.getByPersonRelationId(p.getId());
						if(null==oldPhonecheck){
							//向电审表中添加数据
							BpPersonPhonecheckInfo bp=new BpPersonPhonecheckInfo();
							bp.setPersonRelationId(p.getId());
							bp.setPersonRelationName(p.getRelationName());
							bp.setProjectId(Long.valueOf(projectId));
							if(575==p.getRelationShip()){
								bp.setRelation("家人");
							}else if(5756==p.getRelationShip()){
								bp.setRelation("同事");
							}else{
								bp.setRelation("朋友");
							}
							bpPersonPhonecheckInfoDao.save(bp);
						}
					}
				}
				
				QueryFilter filter=new QueryFilter(flowRunInfo.getRequest());
				filter.addFilter("Q_projectId_L_EQ", projectId);
				List<BpPersonNetCheckInfo>  listbpnc=bpPersonNetCheckInfoDao.getAll(filter);
				if(null==listbpnc || listbpnc.size()==0){
					Map<String,String> map=new HashMap<String,String>();
					Person p=this.personDao.getById(Integer.parseInt(personId)); //申请人Id 企业名称  单位电话
					//配偶信息
					Spouse spouse=null;
					if (null != p.getMarry() && p.getMarry() == 317) {
						spouse = spouseDao.getByPersonId(p.getId());
					}
					//房产信息
					List<CsPersonHouse> listHouse=csPersonHouseDao.getByPersonId(personId);
					CsPersonHouse personHouse=null;
					if(null!=listHouse && listHouse.size()>0){
						personHouse=listHouse.get(0);
					}
					PersonRelation relation=personDao.getByRelationShip(Integer.parseInt(personId),575); //家庭联系人
					map.put("申请人Id",null!=p?p.getCardnumber():"");
					map.put("配偶Id",null!=spouse?spouse.getCardnumber():"");
					map.put("企业名称",null!=p?p.getCurrentcompany():"");
					map.put("房产地址",null!=personHouse?personHouse.getAddress():"");
					map.put("单位电话",null!=p?p.getUnitphone():"");
					map.put("家庭联系人号码",null!=relation?relation.getRelationCellPhone():"");
					//向网审表中添加数据
					Object s[] = map.keySet().toArray();
					for(int i = 0; i < map.size(); i++) {
						BpPersonNetCheckInfo bpPersonNetCheckInfo=new BpPersonNetCheckInfo();
						bpPersonNetCheckInfo.setSerachObj(s[i].toString());
						bpPersonNetCheckInfo.setSerachInfo(map.get(s[i]));
						bpPersonNetCheckInfo.setProjectId(Long.valueOf(projectId));
						bpPersonNetCheckInfoDao.save(bpPersonNetCheckInfo);
					}
				}
				
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")|| transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
					} else {
						String flowAssignId="网审:电审:评估|"+netCheckPerson+":"+phoneCheckPerson+":"+assessPerson;
						vars.put("flowAssignId",flowAssignId);
					}
					flowRunInfo.getVariables().putAll(vars);
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	//电审提交方法
	@Override
	public Integer phoneCheckTaskNextStep(FlowRunInfo flowRunInfo){
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String phoneCheckData1=flowRunInfo.getRequest().getParameter("phoneCheckData1");
				String phoneCheckData2=flowRunInfo.getRequest().getParameter("phoneCheckData2");
				String phoneCheckData3=flowRunInfo.getRequest().getParameter("phoneCheckData3");
				
				Map<String,String> map=new HashMap<String,String>();
				if(null!=phoneCheckData1 && !"".equals(phoneCheckData1)){
					map.put("phoneCheckData1", phoneCheckData1);
				}
				if(null!=phoneCheckData2 && !"".equals(phoneCheckData2)){
					map.put("phoneCheckData2", phoneCheckData2);
				}
				if(null!=phoneCheckData3 && !"".equals(phoneCheckData3)){
					map.put("phoneCheckData3", phoneCheckData3);
				}
				this.updatePhoneCheckInfo(map);
				
				//copy 贷款必备条件
				String productId=flowRunInfo.getRequest().getParameter("productId");
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				QueryFilter filter=new QueryFilter(flowRunInfo.getRequest());
				filter.addFilter("Q_projectId_L_EQ", projectId);
				List<OurProcreditAssuretenet> list=ourProcreditAssuretenetDao.getAll(filter);
				if(null==list || list.size()==0){
					list= ourProcreditAssuretenetDao.getByProductId(Long.valueOf(productId));
					if(null!=list && !"".equals(list)){
						for(int i=0;i<list.size();i++){
							OurProcreditAssuretenet op=new OurProcreditAssuretenet();
							op.setAssuretenet(list.get(i).getAssuretenet());
							op.setProjectId(Long.valueOf(projectId));
							ourProcreditAssuretenetDao.save(op);
						}
					}
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	};
	
	//网审提交方法
	@Override
	public Integer netCheckTaskNextStep(FlowRunInfo flowRunInfo){
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String netCheckData=flowRunInfo.getRequest().getParameter("netCheckData");
				this.updateNetCheckInfo(netCheckData);
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	};
	
	@Override
	public Integer updatePhoneCheckInfo(Map<String, String> map) {
		try{
			Object s[] = map.keySet().toArray();
			for(int i = 0; i < map.size(); i++) {
				String[] phoneCheck = map.get(s[i]).split("@");
				for (int j = 0; j < phoneCheck.length; j++) {
					String str = phoneCheck[j];
					//反序列化
					JSONParser parser = new JSONParser(new StringReader(str));
					BpPersonPhonecheckInfo bpPersonPhonecheckInfo = (BpPersonPhonecheckInfo) JSONMapper.toJava(parser.nextValue(), BpPersonPhonecheckInfo.class);
					if(null==bpPersonPhonecheckInfo.getId()||"".equals(bpPersonPhonecheckInfo.getId())){
						bpPersonPhonecheckInfoDao.save(bpPersonPhonecheckInfo);
					}else{
						BpPersonPhonecheckInfo oldInfo=bpPersonPhonecheckInfoDao.get(bpPersonPhonecheckInfo.getId());
						BeanUtil.copyNotNullProperties(oldInfo, bpPersonPhonecheckInfo);
						bpPersonPhonecheckInfoDao.merge(oldInfo);
					}
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public Integer updateNetCheckInfo(String netCheckData) {
		try{
			if(netCheckData!=null&&!"".equals(netCheckData)){
				String[] netCheckInfoArr = netCheckData.split("@");
				for (int i = 0; i < netCheckInfoArr.length; i++) {
					String str = netCheckInfoArr[i];
					//反序列化
					JSONParser parser = new JSONParser(new StringReader(str));
					BpPersonNetCheckInfo bpPersonNetcheckInfo = (BpPersonNetCheckInfo) JSONMapper.toJava(parser.nextValue(), BpPersonNetCheckInfo.class);
					if(null==bpPersonNetcheckInfo.getId()||"".equals(bpPersonNetcheckInfo.getId())){
						bpPersonNetCheckInfoDao.save(bpPersonNetcheckInfo);
					}else{
						BpPersonNetCheckInfo oldInfo=bpPersonNetCheckInfoDao.get(bpPersonNetcheckInfo.getId());
						BeanUtil.copyNotNullProperties(oldInfo, bpPersonNetcheckInfo);
						bpPersonNetCheckInfoDao.merge(oldInfo);
					}
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	//门店负责人提交方法
	@Override
	public Integer outletCheckNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String isFirst = flowRunInfo.getRequest().getParameter("first");
				String transitionName = flowRunInfo.getTransitionName();
				String chargeJson = flowRunInfo.getRequest().getParameter("chargeJson");
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String assignId = flowRunInfo.getRequest().getParameter("flowAssignId");
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								//保存项目信息
								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSmallloanProject");
								SlSmallloanProject sl=dao.get(project.getProjectId());
								BeanUtil.copyNotNullProperties(sl, project);
								dao.merge(sl);
								if(null!=chargeJson&&!"".equals(chargeJson)&&!"undefined".equals(chargeJson)){
									slActualToChargeService.saveJson(chargeJson,project.getProjectId(),"SmallLoan",(short)0,1l,null);
								}
//								List<BpFundProject> fundList= bpFundProjectDao.getByProjectId(Long.valueOf(projectId));
								if(null!=isFirst&&!"".equals(isFirst)&&!"undefined".equals(isFirst)){
									BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort(0+""));
									if(null==fundProject){
										fundProject=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject, sl);
									fundProject.setFlag(Short.parseShort(String.valueOf(0)));
									fundProject.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject);
								}
								/*for(int i=0;i<2;i++){//
									BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort(i+""));
									if(null==fundProject){
										fundProject=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject, sl);
									fundProject.setFlag(Short.parseShort(String.valueOf(i)));
									fundProject.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject);
								}*/
							}
						}
					}
					if(null!=transitionName&&!"".equals(transitionName)){
						if("信审初审".equals(transitionName)){
							vars.put("examineManager", assignId);
						}else if("信审复审".equals(transitionName)){
							vars.put("reExamineManager", assignId);
						}
					}
					vars.put("OutletPrincipalExamineResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-门店负责人审核提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	
	
	//终审提交方法
	@Override
	public Integer finalCheckNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String isFirst = flowRunInfo.getRequest().getParameter("first");
				String transitionName = flowRunInfo.getTransitionName();
				String chargeJson = flowRunInfo.getRequest().getParameter("chargeJson");
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String assignId = flowRunInfo.getRequest().getParameter("flowAssignId");
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								//保存项目信息
								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSmallloanProject");
								SlSmallloanProject sl=dao.get(project.getProjectId());
								BeanUtil.copyNotNullProperties(sl, project);
								dao.merge(sl);
								if(null!=chargeJson&&!"".equals(chargeJson)&&!"undefined".equals(chargeJson)){
									slActualToChargeService.saveJson(chargeJson,project.getProjectId(),"SmallLoan",(short)0,1l,null);
								}
//								List<BpFundProject> fundList= bpFundProjectDao.getByProjectId(Long.valueOf(projectId));
								if(null!=isFirst&&!"".equals(isFirst)&&!"undefined".equals(isFirst)){
									BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort(0+""));
									if(null==fundProject){
										fundProject=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject, sl);
									fundProject.setFlag(Short.parseShort(String.valueOf(0)));
									fundProject.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject);
								}
							}
						}
					}
					vars.put("CreditLastExamineResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("彩云信贷流程-终审提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	
	
	//门店负责人提交方法 大连天储分离  避免影响其他流程
	@Override
	public Integer outletCheckNextStep1(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String isFirst = flowRunInfo.getRequest().getParameter("first");
				String transitionName = flowRunInfo.getTransitionName();
				String chargeJson = flowRunInfo.getRequest().getParameter("chargeJson");
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String assignId = flowRunInfo.getRequest().getParameter("flowAssignId");
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								//保存项目信息
								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSmallloanProject");
								SlSmallloanProject sl=dao.get(project.getProjectId());
								BeanUtil.copyNotNullProperties(sl, project);
								// 款项计划  
								String slFundIentJson = flowRunInfo.getRequest().getParameter(
										"fundIntentJsonData");
								if(slFundIentJson!=null&&!"".equals(slFundIentJson)){
									String maxintentDate = slFundIntentService.savejson(slFundIentJson,
											project.getProjectId(), "SmallLoan", Short.parseShort("1"),
											1L);
								}
								dao.merge(sl);
								if(null!=chargeJson&&!"".equals(chargeJson)&&!"undefined".equals(chargeJson)){
									slActualToChargeService.saveJson(chargeJson,project.getProjectId(),"SmallLoan",(short)0,1l,null);
								}
//								List<BpFundProject> fundList= bpFundProjectDao.getByProjectId(Long.valueOf(projectId));
								if(null!=isFirst&&!"".equals(isFirst)&&!"undefined".equals(isFirst)){
									BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort(0+""));
									if(null==fundProject){
										fundProject=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject, sl);
									fundProject.setFlag(Short.parseShort(String.valueOf(0)));
									fundProject.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject);
								}
								//房产信息
								String personHouseDate = flowRunInfo.getRequest().getParameter("personHouseData");
								
								//保存客户房产信息
								if(personHouseDate!=null&&!"".equals(personHouseDate)){
									String[] personHouseInfoArr = personHouseDate.split("@");
									for (int i = 0; i < personHouseInfoArr.length; i++) {
										String str = personHouseInfoArr[i];
										//反序列化
										JSONParser parser = new JSONParser(new StringReader(str));
										CsPersonHouse csPersonHouse = (CsPersonHouse) JSONMapper.toJava(parser.nextValue(), CsPersonHouse.class);
										if(null==csPersonHouse.getId()||"".equals(csPersonHouse.getId())){
											csPersonHouseDao.save(csPersonHouse);
										}else{
											CsPersonHouse tempHouse=csPersonHouseDao.get(csPersonHouse.getId());
											BeanUtil.copyNotNullProperties(tempHouse, csPersonHouse);
											csPersonHouseDao.merge(tempHouse);
										}
									}
								}
								//产品信息
							
								
								//车产信息
								String personCarData = flowRunInfo.getRequest().getParameter("personCarData");
								
								//保存客户车产信息
								if(personCarData!=null&&!"".equals(personCarData)){
									String[] personCarInfoArr = personCarData.split("@");
									for (int i = 0; i < personCarInfoArr.length; i++) {
										String str = personCarInfoArr[i];
										//反序列化
										JSONParser parser = new JSONParser(new StringReader(str));
										CsPersonCar csPersonCar = (CsPersonCar) JSONMapper.toJava(parser.nextValue(), CsPersonCar.class);
										if(null==csPersonCar.getId()||"".equals(csPersonCar.getId())){
											csPersonCarDao.save(csPersonCar);
										}else{
											CsPersonCar tempCar=csPersonCarDao.get(csPersonCar.getId());
											BeanUtil.copyNotNullProperties(tempCar, csPersonCar);
											csPersonCarDao.merge(tempCar);
										}
									}
								}
								
								//个人客户
								Person pe = null;
								Integer personId=Integer.valueOf(flowRunInfo.getRequest().getParameter("person.id"));
								Person p=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), p,"person");
								if(p.getId()==null){
									this.personDao.save(p);
									pe = p;
								}else{
									Person p1=personDao.getById(personId);
									p.setId(personId);
									BeanUtil.copyNotNullProperties(p1, p);
										this.personDao.merge(p1);
										pe = p1;
								}
								
							
								//更新公司信息
								WorkCompany workCompany =new WorkCompany();
								String workCompanyIds = flowRunInfo.getRequest().getParameter("workCompany.id");
								Integer workCompanyId;
								if(workCompanyIds!=null&&!workCompanyIds.equals("")){
									 workCompanyId=Integer.valueOf(workCompanyIds);
								}
								BeanUtil.populateEntity(flowRunInfo.getRequest(), workCompany,"workCompany");
								if (workCompany.getId()== null||workCompany.getId()==0||workCompany.getId().equals("")) {
									workCompany.setId(null);
									workCompany.setPersonId(pe.getId());
									this.workCompanyDao.save(workCompany);	
								} else {
									workCompany.setPersonId(pe.getId());
									this.workCompanyDao.merge(workCompany);
								}
							}
						}
					}
					if(null!=transitionName&&!"".equals(transitionName)){
						if("信审初审".equals(transitionName)){
							vars.put("examineManager", assignId);
						}else if("信审复审".equals(transitionName)){
							vars.put("reExamineManager", assignId);
						}
					}
					vars.put("OutletPrincipalExamineResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-门店负责人审核提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	/**
	 * 稳安贷(有打回)提交方法
	 */
	public Integer outletCheckNextStep2(FlowRunInfo flowRunInfo){
		try{
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String isFirst = flowRunInfo.getRequest().getParameter("first");
				String transitionName = flowRunInfo.getTransitionName();
				String chargeJson = flowRunInfo.getRequest().getParameter("chargeJson");
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String assignId = flowRunInfo.getRequest().getParameter("flowAssignId");
				
				  //对提交节点做判断
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								 if(transitionName.contains("归档")){
										//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
										if(null!=projectId&&!"".equals(projectId)){
											dealSmallloanBaseInfo(flowRunInfo);//更新项目信息
											generateFundProject(flowRunInfo);//s生成资金方案
											
											SlSmallloanProject project = dao.get(Long.parseLong(projectId));
											BpFundProject bpFund = bpFundProjectService.getByProjectId(project.getProjectId(), (short)1);
											bpFund.setPlatFormJointMoney(project.getProjectMoney());
											bpFundProjectService.merge(bpFund);
											generateDirPlan(project);//生成直投标
											jumpFlow(flowRunInfo);
										}
									}
								//保存项目信息
								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSmallloanProject");
								SlSmallloanProject sl=dao.get(project.getProjectId());
								BeanUtil.copyNotNullProperties(sl, project);
								dao.merge(sl);
								
								//电审信息的保存方法
								if(null!=chargeJson&&!"".equals(chargeJson)&&!"undefined".equals(chargeJson)){
									slActualToChargeService.saveJson(chargeJson,project.getProjectId(),"SmallLoan",(short)0,1l,null);
									String phoneCheckData1=flowRunInfo.getRequest().getParameter("phoneCheckData1");
									String phoneCheckData2=flowRunInfo.getRequest().getParameter("phoneCheckData2");
									String phoneCheckData3=flowRunInfo.getRequest().getParameter("phoneCheckData3");
									
									Map<String,String> map=new HashMap<String,String>();
									if(null!=phoneCheckData1 && !"".equals(phoneCheckData1)){
										map.put("phoneCheckData1", phoneCheckData1);
									}
									if(null!=phoneCheckData2 && !"".equals(phoneCheckData2)){
										map.put("phoneCheckData2", phoneCheckData2);
									}
									if(null!=phoneCheckData3 && !"".equals(phoneCheckData3)){
										map.put("phoneCheckData3", phoneCheckData3);
									}
									this.updatePhoneCheckInfo(map);
									//终审往金融中介---直投表保存方法
								}
						    
//								List<BpFundProject> fundList= bpFundProjectDao.getByProjectId(Long.valueOf(projectId));
								if(null!=isFirst&&!"".equals(isFirst)&&!"undefined".equals(isFirst)){
									BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort(0+""));
									if(null==fundProject){
										fundProject=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject, sl);
									fundProject.setFlag(Short.parseShort(String.valueOf(0)));
									fundProject.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject);
								}

								//手续清单
								if(null!=chargeJson&&!"".equals(chargeJson)&&!"undefined".equals(chargeJson)){
						        slActualToChargeService.saveJson(chargeJson,project.getProjectId(),"SmallLoan",(short)0,1l,null);
					
					            }
								/*for(int i=0;i<2;i++){//
									BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort(i+""));
									if(null==fundProject){
										fundProject=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject, sl);
									fundProject.setFlag(Short.parseShort(String.valueOf(i)));
									fundProject.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject);
								}*/
							}
						}
					}
 					if(null!=transitionName&&!"".equals(transitionName)){
						if("信审初审".equals(transitionName)){
							vars.put("examineManager", assignId);
						}else if("信审复审".equals(transitionName)){
							vars.put("reExamineManager", assignId);
						}
					}         
					vars.put("OutletPrincipalReCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				
			/*	//copy 贷款必备条件
				String productId=flowRunInfo.getRequest().getParameter("productId");
				//String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				QueryFilter filter=new QueryFilter(flowRunInfo.getRequest());
				filter.addFilter("Q_projectId_L_EQ", projectId);
				List<OurProcreditAssuretenet> list=ourProcreditAssuretenetDao.getAll(filter);*/
				
			/*	if(null==list || list.size()==0){
					list= ourProcreditAssuretenetDao.getByProductId(Long.valueOf(productId));
					 if(null!=list && (list.size()!=0)){
							for(int i=0;i<list.size();i++){
								OurProcreditAssuretenet op=new OurProcreditAssuretenet();
								op.setAssuretenet(list.get(i).getAssuretenet());
								op.setProjectId(Long.valueOf(projectId));
								ourProcreditAssuretenetDao.save(op);
							}
						}
				}*/
			   
			
			return 1;
		}
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
	
	public List<SlFundIntent> savejsonintent(String fundIntentJsonData,
			SlSmallloanProject slSmallloanProject, Short flag) {
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
						SlFundIntent slFundIntent2 = slFundIntentService
								.get(SlFundIntent1.getFundIntentId());
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
	//放款确认
	@Override
	public Integer fangKuanQueRen(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String projectStatus = flowRunInfo.getRequest().getParameter("projectStatus");
				String transitionName = flowRunInfo.getTransitionName();
				if(projectId==null||"".equals(projectId)||"undefined".equals(projectId)){
					return 0;
				}
				SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						sp.setProjectStatus((short)3);
						dao.merge(sp);
						flowRunInfo.setStop(true);
						return 1;
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							
						}
					}
				}
				String slFundIentJson = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
				BpFundProject oldBpFund2=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort("1"));
				//slFundIntentService.savejson(slFundIentJson, Long.valueOf(projectId),"SmallLoan",Short.parseShort("0"),oldBpFund2.getCompanyId(),oldBpFund2.getId(),oldBpFund2.getFundResource());
				SlSmallloanProject project = dao.get(Long.parseLong(projectId));
				slFundIntentService.savejson1(slFundIentJson, Long.parseLong(projectId), "SmallLoan", Short.parseShort("0"), Short.parseShort("0"), 1L);
				
				//更新bpFundProject 表
				BpFundProject fundProject = bpFundProjectService.getByProjectId(Long.parseLong(projectId), (short)0);
				fundProject.setOwnJointMoney(project.getProjectMoney());
				fundProject.setStartDate(project.getStartDate());
				fundProject.setIntentDate(project.getIntentDate());
				bpFundProjectService.save(fundProject);
				if(projectStatus!=null&&!"".equals(projectStatus)&&!"undefined".equals(projectStatus)){
					sp.setProjectStatus((short)1);
					dao.merge(sp);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-放款确认提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	//门店负责人复核提交方法
	@Override
	public Integer outletReCheckNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								//保存项目信息
								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSmallloanProject");
								SlSmallloanProject sl=dao.get(project.getProjectId());
								BeanUtil.copyNotNullProperties(sl, project);
								dao.merge(sl);
								
								for(int i=0;i<2;i++){
									BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(project.getProjectId()),Short.parseShort(i+""));
									if(null==fundProject){
										fundProject=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject, sl);
									fundProject.setFlag(Short.parseShort(String.valueOf(i)));
									fundProject.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject);
								}
							}
						}
					}
					vars.put("OutletPrincipalReCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-门店负责人提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	@Override
	public Integer customerYXNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String slActualData=flowRunInfo.getRequest().getParameter("slActualToChargeData");
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								//保存手续费用信息
								if(slActualData!=null&&!"".equals(slActualData)){
									String[] slActualDataArr = slActualData.split("@");
									for (int i = 0; i < slActualDataArr.length; i++) {
										String str = slActualDataArr[i];
										//反序列化
										JSONParser parser = new JSONParser(new StringReader(str));
										SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper.toJava(parser.nextValue(), SlActualToCharge.class);
										if(null==slActualToCharge.getActualChargeId()||"".equals(slActualToCharge.getActualChargeId())){
											slActualToChargeDao.save(slActualToCharge);
										}else{
											SlActualToCharge oldSlActual=slActualToChargeDao.get(slActualToCharge.getActualChargeId());
											BeanUtil.copyNotNullProperties(oldSlActual, slActualToCharge);
											slActualToChargeDao.merge(oldSlActual);
										}
									}
								}
								//向bp_fund_project表中添加两条记录，分别代表自有资金款项和平台资金款项
								List<BpFundProject> fundList= bpFundProjectDao.getByProjectId(Long.valueOf(projectId));
								SlSmallloanProject sl = dao.get(Long.valueOf(projectId));
								if(null==fundList || fundList.size()==0){
									for(int i=0;i<2;i++){
										BpFundProject fundProject=new BpFundProject();
										BeanUtil.copyNotNullProperties(fundProject, sl);
										fundProject.setFlag(Short.parseShort(String.valueOf(i)));
										fundProject.setSlFundIntents(null);
										bpFundProjectDao.save(fundProject);
									}
								}
							}
						}
					}
					vars.put("ReconsiderResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-客户意向确认提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	//初审拒贷提交方法
	@Override
	public Integer firstJudLoanRejecNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slProjectId");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject s=dao.get(Long.valueOf(projectId));
						s.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(s);
					}
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-初审拒贷提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	
	//门店终审
	@Override
	public Integer outletFinalJudgmentNextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					}{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								//保存项目信息
								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSmallloanProject");
								SlSmallloanProject s=dao.get(project.getProjectId());
								BeanUtil.copyNotNullProperties(s, project);
								dao.merge(s);
							}
						}
					}
					vars.put("OutletFinalJudgmentResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-门店终审提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	
	//复议
	@Override
	public Integer reconsiderNextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					}{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								//保存项目信息
								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSmallloanProject");
								SlSmallloanProject sl=dao.get(project.getProjectId());
								BeanUtil.copyNotNullProperties(sl, project);
								dao.merge(sl);
								
								List<BpFundProject> fundList= bpFundProjectDao.getByProjectId(Long.valueOf(projectId));
								if(null!=fundList && fundList.size()>0){
									for(int i=0;i<2;i++){
										BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort(i+""));
										BeanUtil.copyNotNullProperties(fundProject, sl);
										fundProject.setFlag(Short.parseShort(String.valueOf(i)));
										fundProject.setSlFundIntents(null);
										bpFundProjectDao.merge(fundProject);
									}
								}
							}
						}
					}
					vars.put("ReconsiderLoanRejectionResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-复议提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	
	//门店负责人确认
	@Override
	public Integer outletPrincipalCheckNextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					}{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}
						}
					}
					vars.put("OutletPrincipalCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-门店负责人确认提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	
	//二次终审
	@Override
	public Integer secondFinalJudgmentNextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					}{
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}
						}
					}
					vars.put("SecondFinalJudgmentResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("信贷流程-二次终审提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	
	
	@Override
	public Integer writeProjectApplyNextStep(FlowRunInfo flowRunInfo){
		try{
			SlSmallloanProject project=null;
			//下一节点任务指定
			String slActualToChargeData=flowRunInfo.getRequest().getParameter("slActualToChargeData");
			String transitionName = flowRunInfo.getTransitionName();
			String chargeJson = flowRunInfo.getRequest().getParameter("chargeJson");
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")|| transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						//保存项目信息
						saveSelfDate(flowRunInfo);
						project = new SlSmallloanProject();
						BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSmallloanProject");
						SlSmallloanProject s=dao.get(project.getProjectId());
						BeanUtil.copyNotNullProperties(s, project);
						if(project.getProductId()!=null){
							BpProductParameter bpProductParameter=bpProductParameterDao.get(project.getProductId());
						/*	if(null!=bpProductParameter){
								s.setAccrualtype(bpProductParameter.getAccrualtype());//还款方式
								s.setPayaccrualType(bpProductParameter.getPayaccrualType());//还款周期
								s.setPayintentPeriod(bpProductParameter.getPayintentPeriod());//贷款期限
								s.setIsPreposePayAccrual(bpProductParameter.getIsPreposePayAccrual());//前置付息
								s.setIsInterestByOneTime(bpProductParameter.getIsInterestByOneTime());//是否一次性支付利息
								s.setIsStartDatePay(bpProductParameter.getIsStartDatePay());//每期还款日
								s.setPayintentPerioDate(bpProductParameter.getPayintentPerioDate());//固定在
								s.setYearAccrualRate(bpProductParameter.getYearAccrualRate());//年化利率
								s.setAccrual(bpProductParameter.getAccrual());//月化利率
								//s.seta
								s.setDayAccrualRate(bpProductParameter.getDayAccrualRate());//日化利率
								s.setSumAccrualRate(bpProductParameter.getSumAccrualRate());//合计利率
							}*/
						}
						dao.merge(s);
						//将产品中的手续清单存入
						String productId=flowRunInfo.getRequest().getParameter("slSmallloanProject.productId");
						List<SlActualToCharge>  list1 = slActualToChargeService.getByProductId(productId);
						if(null!=list1&&list1.size()!=0){
							for(SlActualToCharge charge : list1){
								//将产品配置的手续费用清单与该项目关联
								if(null!=charge.getProductId()&&null==charge.getBusinessType()){
									slActualToChargeService.merge(charge);
								}
							}
						}
						
						//借款需求
						BpMoneyBorrowDemand borrowDemand =new BpMoneyBorrowDemand();
						BeanUtil.populateEntity(flowRunInfo.getRequest(), borrowDemand,"bpMoneyBorrowDemand");
						if(null==borrowDemand.getBorrowid()){
							borrowDemand.setProjectID(project.getProjectId());
							bpMoneyBorrowDemandDao.save(borrowDemand);
						}else{
							borrowDemand.setProjectID(project.getProjectId());
							bpMoneyBorrowDemandDao.merge(borrowDemand);
						}
						/*//手续清单
						SlActualToCharge slActualToCharge=new SlActualToCharge();
						BeanUtil.populateEntity(flowRunInfo.getRequest(), slActualToCharge,"slActualToCharge");
						if(null==slActualToCharge.getActualChargeId()){
							slActualToCharge.setProjectId(project.getProjectId());
							slActualToChargeDao.save(slActualToCharge);
						}else{
							slActualToCharge.setProjectId(project.getProjectId());
							slActualToChargeDao.merge(slActualToCharge);
						}*/
						//房产信息
						String personHouseDate = flowRunInfo.getRequest().getParameter("personHouseData");
						
						/*//保存客户房产信息
						if(personHouseDate!=null&&!"".equals(personHouseDate)){
							String[] personHouseInfoArr = personHouseDate.split("@");
							for (int i = 0; i < personHouseInfoArr.length; i++) {
								String str = personHouseInfoArr[i];
								//反序列化
								JSONParser parser = new JSONParser(new StringReader(str));
								CsPersonHouse csPersonHouse = (CsPersonHouse) JSONMapper.toJava(parser.nextValue(), CsPersonHouse.class);
								if(null==csPersonHouse.getId()||"".equals(csPersonHouse.getId())){
									csPersonHouseDao.save(csPersonHouse);
								}else{
									CsPersonHouse tempHouse=csPersonHouseDao.get(csPersonHouse.getId());
									BeanUtil.copyNotNullProperties(tempHouse, csPersonHouse);
									csPersonHouseDao.merge(tempHouse);
								}
							}
						}*/
						//产品信息
					
						
						//车产信息
						/*String personCarData = flowRunInfo.getRequest().getParameter("personCarData");
						
						//保存客户车产信息
						if(personCarData!=null&&!"".equals(personCarData)){
							String[] personCarInfoArr = personCarData.split("@");
							for (int i = 0; i < personCarInfoArr.length; i++) {
								String str = personCarInfoArr[i];
								//反序列化
								JSONParser parser = new JSONParser(new StringReader(str));
								CsPersonCar csPersonCar = (CsPersonCar) JSONMapper.toJava(parser.nextValue(), CsPersonCar.class);
								if(null==csPersonCar.getId()||"".equals(csPersonCar.getId())){
									csPersonCarDao.save(csPersonCar);
								}else{
									CsPersonCar tempCar=csPersonCarDao.get(csPersonCar.getId());
									BeanUtil.copyNotNullProperties(tempCar, csPersonCar);
									csPersonCarDao.merge(tempCar);
								}
							}
						}*/
						
						//个人客户
						Person pe = null;
						Integer personId=Integer.valueOf(flowRunInfo.getRequest().getParameter("person.id"));
						Person p=new Person();
						BeanUtil.populateEntity(flowRunInfo.getRequest(), p,"person");
						if(p.getId()==null){
							this.personDao.save(p);
							pe = p;
						}else{
							Person p1=personDao.getById(personId);
							String CompanyScale=String.valueOf(p1.getCompanyScale());
							p1.setCompanyScale(CompanyScale);
							p.setId(personId);
							BeanUtil.copyNotNullProperties(p1, p);
								this.personDao.merge(p1);
								pe = p1;
						}
						
					
						//更新公司信息
						WorkCompany workCompany =new WorkCompany();
						String workCompanyIds = flowRunInfo.getRequest().getParameter("workCompany.id");
						Integer workCompanyId;
						if(workCompanyIds!=null&&!workCompanyIds.equals("")){
							 workCompanyId=Integer.valueOf(workCompanyIds);
						}
						BeanUtil.populateEntity(flowRunInfo.getRequest(), workCompany,"workCompany");
						if (workCompany.getId()== null||workCompany.getId()==0||workCompany.getId().equals("")) {
							workCompany.setId(null);
							workCompany.setPersonId(pe.getId());
							this.workCompanyDao.save(workCompany);	
						} else {
							workCompany.setPersonId(pe.getId());
							this.workCompanyDao.merge(workCompany);
						}
						//依据项目的productId初始化贷款材料（修改，增加，编辑）
						ourProcreditMaterialsEnterpriseDao.initMaterialsByProductId(s.getProjectId(), s.getBusinessType(),s.getOperationType(),s.getProductId());
						/* List<SlProcreditMaterials> listtemp1 = slProcreditMaterialsService.getByProjId(project.getProjectId().toString(), project.getBusinessType(), true);
						//List<OurProcreditMaterialsEnterprise> list= ourProcreditMaterialsEnterpriseDao.getByProjectId(project.getProjectId());
						List<OurProcreditMaterialsEnterprise> list = ourProcreditMaterialsEnterpriseDao.getByProductId(s.getProductId());
						if(null==listtemp1 || listtemp1.size()==0){
							if(s.getProductId()!=null){
								//
								List<OurProcreditMaterialsEnterprise> 
								for(OurProcreditMaterialsEnterprise o : list){
									OurProcreditMaterialsEnterprise op=new OurProcreditMaterialsEnterprise();
									op.setMaterialsName(o.getMaterialsName());
									op.setProjectId(s.getProjectId());
									ourProcreditMaterialsEnterpriseDao.save(op);
								}
							}
						}*/
						
						//copy 贷款必备条件
						String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						QueryFilter filter=new QueryFilter(flowRunInfo.getRequest());
						
						filter.addFilter("Q_projectId_L_EQ", projectId);
						List<OurProcreditAssuretenet> AssuretenetList=ourProcreditAssuretenetDao.getAll(filter);  //项目
						
						List<OurProcreditAssuretenet> newList= ourProcreditAssuretenetDao.getByProductId(Long.valueOf(productId));//产品
						if(null!=AssuretenetList && AssuretenetList.size()>0){//已经存在
							//删除原有的
							for(int i=0;i<AssuretenetList.size();i++){
								OurProcreditAssuretenet assuretenet=AssuretenetList.get(i);
								ourProcreditAssuretenetDao.remove(assuretenet);
							}
						}
						//增加新的
						if(null!=newList && newList.size()>0){
							for(int i=0;i<newList.size();i++){
								OurProcreditAssuretenet op=new OurProcreditAssuretenet();
								op.setAssuretenet(newList.get(i).getAssuretenet());
								op.setProjectId(Long.valueOf(projectId));
								ourProcreditAssuretenetDao.save(op);
							}
						}

						//联系人信息
						List<PersonRelation> listRelation=personDao.getPersonByPersonId(personId);
						if(null!=listRelation && listRelation.size()>0){
							for(int i=0;i<listRelation.size();i++){
								PersonRelation pr=listRelation.get(i);
								BpPersonPhonecheckInfo oldPhonecheck=bpPersonPhonecheckInfoDao.getByPersonRelationId(pr.getId(),s.getProjectId());
								if(null==oldPhonecheck){
									//向电审表中添加数据
									BpPersonPhonecheckInfo bp=new BpPersonPhonecheckInfo();
									bp.setPersonRelationId(pr.getId());
									bp.setPersonRelationName(pr.getRelationName());
									bp.setProjectId(Long.valueOf(projectId));
									if(575==pr.getRelationShip()){
										bp.setRelation("家人");
									}else if(5756==pr.getRelationShip()){
										bp.setRelation("同事");
									}else{
										bp.setRelation("朋友");
									}
									bpPersonPhonecheckInfoDao.save(bp);
								}
							}
						}
					
					}
					
					if(null!=chargeJson&&!"".equals(chargeJson)&&!"undefined".equals(chargeJson)){
						slActualToChargeService.saveJson(chargeJson,project.getProjectId(),"SmallLoan",(short)0,1l,null);
					
					}
					vars.put("productId",project.getProductId());
					flowRunInfo.getVariables().putAll(vars);
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	
	@Override
	public void saveNewSlSmalloanProject(SlSmallloanProject slSmallloanProject,
			Person person, Enterprise enterprise,
			List<EnterpriseShareequity> listES) {

		Integer farenId = personDao.save(person).getId();

		if (slSmallloanProject.getOppositeType().equals("company_customer")) // 企业
		{
			enterprise.setLegalpersonid(farenId);
			Integer duifangzhutiId = enterpriseDao.save(enterprise).getId();
			if (listES != null && listES.size() > 0) {
				for (int i = 0; i < listES.size(); i++) {
					EnterpriseShareequity enterpriseShareequity = listES.get(i);
					enterpriseShareequity.setEnterpriseid(duifangzhutiId);
					enterpriseShareequity.setCreateTime(new Date());
					enterpriseShareequityDao.save(enterpriseShareequity);
				}
			}
			slSmallloanProject.setOppositeID(Long.valueOf(duifangzhutiId));
		} else if (slSmallloanProject.getOppositeType().equals(
				"person_customer")) {
			slSmallloanProject.setOppositeID(Long.valueOf(farenId));
		}
		dao.save(slSmallloanProject);

	}

	@Override
	public Integer updateInfo(SlSmallloanProject slSmallloanProject,
			Person person, Enterprise enterprise,
			List<EnterpriseShareequity> listES,
			List<SlRepaymentSource> SlRepaymentSources,
			List<SlFundIntent> slFundIntents,
			List<SlActualToCharge> slActualToCharges,
			String isDeleteAllFundIntent) {
		try {

			SlSmallloanProject persistent = this.dao.get(slSmallloanProject
					.getProjectId());
			/**
			 * 更新股东信息 开始
			 */
			if (persistent.getOppositeType().equals("company_customer")) {
				if (listES.size() > 0) {

					for (int i = 0; i < listES.size(); i++) {
						EnterpriseShareequity es = listES.get(i);
						if (es.getId() == null) {
							es.setEnterpriseid(enterprise.getId());
							this.enterpriseShareequityDao.save(es);
						} else {
							EnterpriseShareequity esPersistent = this.enterpriseShareequityDao
									.load(es.getId());
							BeanUtils.copyProperties(es, esPersistent,
									new String[] { "id", "enterpriseid" });
							this.enterpriseShareequityDao.merge(esPersistent);
						}
					}
				}

				Enterprise ePersistent = this.enterpriseDao
						.getById(enterprise.getId());
				ePersistent.setEnterprisename(enterprise.getEnterprisename());
				ePersistent.setArea(enterprise.getArea());
				ePersistent.setShortname(enterprise.getShortname());
				ePersistent.setHangyeType(enterprise.getHangyeType());
				ePersistent.setOrganizecode(enterprise.getOrganizecode());
				ePersistent.setCciaa(enterprise.getCciaa());
				ePersistent.setTelephone(enterprise.getTelephone());
				ePersistent.setPostcoding(enterprise.getPostcoding());
				enterpriseDao.merge(ePersistent);
			}
			Person pPersistent = this.personDao.getById(person.getId());
			pPersistent.setMarry(person.getMarry());
			pPersistent.setName(person.getName());
			pPersistent.setSex(person.getSex());
			pPersistent.setCardtype(person.getCardtype());
			pPersistent.setCardnumber(person.getCardnumber());
			pPersistent.setTelphone(person.getTelphone());
			pPersistent.setPostcode(person.getPostcode());
			pPersistent.setSelfemail(person.getSelfemail());
			pPersistent.setPostaddress(person.getPostaddress());
			this.personDao.evict(pPersistent);

			if (SlRepaymentSources.size() > 0) {

				for (SlRepaymentSource temp : SlRepaymentSources) {

					boolean flag = StringUtil.isNumeric(temp.getTypeId());
					GlobalType globalType = globalTypeDao.getByNodeKey(
							"repaymentSource").get(0);
					if (flag == false) {

						Dictionary dic = new Dictionary();
						dic.setItemValue(temp.getTypeId());
						dic.setItemName(globalType.getTypeName());
						dic.setProTypeId(globalType.getProTypeId());
						dic.setDicKey(temp.getTypeId());
						dic.setStatus("0");
						dictionaryDao.save(dic);

						temp.setTypeId(String.valueOf(dic.getDicId()));
					} else {

						Dictionary cd = dictionaryDao.get(Long.valueOf(temp
								.getTypeId()));
						if (null == cd) {
							Dictionary dic = new Dictionary();
							dic.setItemValue(temp.getTypeId());
							dic.setItemName(globalType.getTypeName());
							dic.setProTypeId(globalType.getProTypeId());
							dic.setDicKey(temp.getTypeId());
							dic.setStatus("0");
							dictionaryDao.save(dic);
							temp.setTypeId(String.valueOf(dic.getDicId()));
						}
					}
					temp.setProjId(slSmallloanProject.getProjectId());
					if (temp.getSourceId() == null) {

						this.slRepaymentSourceDao.save(temp);

					} else {

						SlRepaymentSource rPersistent = this.slRepaymentSourceDao
								.get(temp.getSourceId());
						BeanUtil.copyNotNullProperties(rPersistent, temp);
						this.slRepaymentSourceDao.save(rPersistent);
					}
				}
			}
			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
			slSmallloanProject.setCompanyId(persistent.getCompanyId());
			slSmallloanProject.setStates(persistent.getStates());
			// end
			BeanUtils.copyProperties(slSmallloanProject, persistent,
					new String[] { "id", "operationType", "flowType",
							"mineType", "mineId", "oppositeType", "oppositeID",
							"projectName", "projectNumber", "oppositeType",
							"businessType", "createDate" });
			if (isDeleteAllFundIntent.equals("1")) {
				List<SlActualToCharge> allactual = this.slActualToChargeDao
						.listbyproject(slSmallloanProject.getProjectId(),
								"SmallLoan");
				List<SlFundIntent> allintent = this.slFundIntentDao
						.getByProjectId1(slSmallloanProject.getProjectId(),
								"SmallLoan");
				for (SlFundIntent s : allintent) {
					slFundIntentDao.evict(s);
					if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						this.slFundIntentDao.remove(s);
					}
				}
				for (SlFundIntent a : slFundIntents) {
					slFundIntentDao.evict(a);
					this.slFundIntentDao.save(a);
				}
				for (SlActualToCharge a : slActualToCharges) {
					this.slActualToChargeDao.save(a);

				}
			}
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));

			this.dao.merge(persistent);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	// 小贷尽职调查保存方法
	@Override
	public Integer updateSmallLoadInfo(SlSmallloanProject slSmallloanProject,
			Person person, Enterprise enterprise,
			List<EnterpriseShareequity> listES, List<BorrowerInfo> listBO,
			List<SlRepaymentSource> SlRepaymentSources,
			List<SlFundIntent> slFundIntents,
			List<SlActualToCharge> slActualToCharges,
			EnterpriseBank enterpriseBank, Spouse spouse,
			String isDeleteAllFundIntent, StringBuffer sb,ProjectPropertyClassification projectPropertyClassification,SlConferenceRecord slConferenceRecord) {
		try {
			SlSmallloanProject persistent = this.dao.get(slSmallloanProject
					.getProjectId());

			/**
			 * 更新股东信息 开始
			 */
			Short flag1 = 0;
			if (persistent.getOppositeType().equals("company_customer")) {
				flag1 = 0;
				if (listES.size() > 0) {
					for (int i = 0; i < listES.size(); i++) {
						EnterpriseShareequity es = listES.get(i);
						if (es.getId() == null) {
							es.setEnterpriseid(enterprise.getId());
							this.enterpriseShareequityDao.save(es);
						} else {
							EnterpriseShareequity esPersistent = this.enterpriseShareequityDao
									.load(es.getId());
							BeanUtils.copyProperties(es, esPersistent,
									new String[] { "id", "enterpriseid" });
							this.enterpriseShareequityDao.merge(esPersistent);
						}
					}
				}
				Enterprise ePersistent = this.enterpriseDao
						.getById(enterprise.getId());
				ePersistent.setEnterprisename(enterprise.getEnterprisename());
				ePersistent.setArea(enterprise.getArea());
				ePersistent.setShortname(enterprise.getShortname());
				ePersistent.setHangyeType(enterprise.getHangyeType());
				ePersistent.setOrganizecode(enterprise.getOrganizecode());
				ePersistent.setCciaa(enterprise.getCciaa());
				ePersistent.setTelephone(enterprise.getTelephone());
				ePersistent.setPostcoding(enterprise.getPostcoding());
				ePersistent.setRootHangYeType(enterprise.getRootHangYeType());

				// 更新法人信息
				if (null != person.getId() && person.getId() != 0) {
					Person pPersistent = this.personDao.getById(person
							.getId());
					pPersistent.setName(person.getName());
					pPersistent.setSex(person.getSex());
					pPersistent.setCardtype(person.getCardtype());
					pPersistent.setCardnumber(person.getCardnumber());
					pPersistent.setCellphone(person.getCellphone());
					pPersistent.setSelfemail(person.getSelfemail());
					pPersistent.setCompanyId(ContextUtil.getLoginCompanyId());
					this.personDao.merge(pPersistent);
					baseCustomService.getCustomToweb("1", pPersistent.getId(),
							0);
				} else {
					Long currentUserId = ContextUtil.getCurrentUserId();
					Person p = new Person();
					p.setId(null);
					p.setCreater(ContextUtil.getCurrentUser().getFullname());
					p.setBelongedId(currentUserId.toString());
					p.setCreaterId(currentUserId);
					p.setCreatedate(new Date());
					p.setCompanyId(ContextUtil.getLoginCompanyId());
					p.setName(person.getName());
					p.setSex(person.getSex());
					p.setCardtype(person.getCardtype());
					p.setCardnumber(person.getCardnumber());
					p.setCellphone(person.getCellphone());
					p.setSelfemail(person.getSelfemail());

					this.personDao.save(p);
					baseCustomService.getCustomToweb("1", p.getId(), 0);
					ePersistent.setLegalpersonid(p.getId());
				}
				sb.append(",legalpersonid:" + ePersistent.getLegalpersonid());
				enterpriseDao.merge(ePersistent);

			} else if (persistent.getOppositeType().equals("person_customer")) {
				flag1 = 1;
				Person pPersistent = this.personDao.getById(person
						.getId());
				pPersistent.setMarry(person.getMarry());
				pPersistent.setName(person.getName());
				pPersistent.setSex(person.getSex());
				pPersistent.setCardtype(person.getCardtype());
				pPersistent.setCardnumber(person.getCardnumber());
				pPersistent.setCellphone(person.getCellphone());
				pPersistent.setPostcode(person.getPostcode());
				pPersistent.setSelfemail(person.getSelfemail());
				pPersistent.setPostaddress(person.getPostaddress());
				this.personDao.merge(pPersistent);
				// 更新配偶信息开始
				if (person.getMarry() != null) {
					if (person.getMarry() == 317) {
						if (spouse != null && !"".equals(spouse)) {
							if (spouse.getSpouseId() == null) {
								spouse.setPersonId(person.getId());
								creditBaseDao.saveDatas(spouse);
							} else {
								Spouse s = (Spouse) creditBaseDao.getById(
										Spouse.class, spouse.getSpouseId());
								s.setCardnumber(spouse.getCardnumber());
								s.setCardtype(spouse.getCardtype());
								s.setCurrentcompany(spouse.getCurrentcompany());
								s.setDgree(spouse.getDgree());
								s.setJob(spouse.getJob());
								s.setLinkTel(spouse.getLinkTel());
								s.setName(spouse.getName());
								s.setPoliticalStatus(spouse
										.getPoliticalStatus());
								creditBaseDao.updateDatas(s);
							}
							sb.append(",spouseId:" + spouse.getSpouseId());
						}
					} else {
						if (spouse != null && !"".equals(spouse)) {
							if (spouse.getSpouseId() != null) {
								Spouse s = (Spouse) creditBaseDao.getById(
										Spouse.class, spouse.getSpouseId());
								creditBaseDao.deleteDatas(s);
							}
						}

					}
				}
			}

			// 更新共同借款人信息开始
			if (listBO.size() > 0) {
				for (int i = 0; i < listBO.size(); i++) {
					BorrowerInfo bo = listBO.get(i);
					if (bo.getBorrowerInfoId() == null) {
						bo.setProjectId(persistent.getProjectId());
						bo.setBusinessType(persistent.getBusinessType());
						bo.setOperationType(persistent.getOperationType());
						this.borrowerInfoDao.save(bo);
					} else {
						BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo
								.getBorrowerInfoId());
						BeanUtils.copyProperties(boPersistent, bo);
						this.borrowerInfoDao.merge(boPersistent);
					}
					if (null != bo.getType() && bo.getType() == 0) {
						if (null != bo.getCustomerId()) {
							Enterprise e = this.enterpriseDao.getById(bo
									.getCustomerId());
							e.setArea(bo.getAddress());
							e.setCciaa(bo.getCardNum());
							e.setTelephone(bo.getTelPhone());
							this.enterpriseDao.merge(e);
						}
					} else if (null != bo.getType() && bo.getType() == 1) {
						Person p = this.personDao.getById(bo.getCustomerId());
						p.setPostaddress(bo.getAddress());
						p.setCardnumber(bo.getCardNum());
						p.setCellphone(bo.getTelPhone());
						this.personDao.merge(p);
					}
				}
			}

			// 更新账户信息开始
			if (enterpriseBank != null) {
				if (enterpriseBank.getId() == null) {
					List<EnterpriseBank> list = enterpriseBankService.getBankList(
							persistent.getOppositeID().intValue(), flag1, Short
									.valueOf("0"),Short.valueOf("0"));
					for (EnterpriseBank e : list) {
						e.setIscredit(Short.valueOf("1"));
						creditBaseDao.updateDatas(e);
					}
					enterpriseBank.setEnterpriseid(persistent.getOppositeID()
							.intValue());
					enterpriseBank.setIscredit(Short.valueOf("0"));
					enterpriseBank.setIsEnterprise(flag1);
					enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
					enterpriseBank.setIsInvest(Short.valueOf("0"));
					enterpriseBank.setIsInvest(Short.valueOf("0"));
					creditBaseDao.saveDatas(enterpriseBank);
				} else {
					EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(EnterpriseBank.class, enterpriseBank.getId());
					bank.setName(enterpriseBank.getName());
					bank.setOpenType(enterpriseBank.getOpenType());
					bank.setAccountnum(enterpriseBank.getAccountnum());
					bank.setAccountType(enterpriseBank.getAccountType());
					bank.setBankid(enterpriseBank.getBankid());
					bank.setBankname(enterpriseBank.getBankname());
					bank.setAreaId(enterpriseBank.getAreaId());
					bank.setAreaName(enterpriseBank.getAreaName());
					bank.setBankOutletsName(enterpriseBank.getBankOutletsName());
						
					creditBaseDao.updateDatas(bank);
				}
				sb.append(",enterpriseBankId:" + enterpriseBank.getId());
			}
			// baseCustomService.getCustomToweb(flag1.toString(),
			// persistent.getOppositeID().intValue(), 0);

			// 更新第一还款来源开始
			if (SlRepaymentSources.size() > 0) {
				for (SlRepaymentSource temp : SlRepaymentSources) {
					boolean flag = StringUtil.isNumeric(temp.getTypeId());
					GlobalType globalType = globalTypeDao.getByNodeKey(
							"repaymentSource").get(0);
					if (flag == false) {
						Dictionary dic = new Dictionary();
						dic.setItemValue(temp.getTypeId());
						dic.setItemName(globalType.getTypeName());
						dic.setProTypeId(globalType.getProTypeId());
						dic.setDicKey(temp.getTypeId());
						dic.setStatus("0");
						dictionaryDao.save(dic);
						temp.setTypeId(String.valueOf(dic.getDicId()));
					} else {
						Dictionary cd = dictionaryDao.get(Long.valueOf(temp
								.getTypeId()));
						if (null == cd) {
							Dictionary dic = new Dictionary();
							dic.setItemValue(temp.getTypeId());
							dic.setItemName(globalType.getTypeName());
							dic.setProTypeId(globalType.getProTypeId());
							dic.setDicKey(temp.getTypeId());
							dic.setStatus("0");
							dictionaryDao.save(dic);
							temp.setTypeId(String.valueOf(dic.getDicId()));
						}
					}
					temp.setProjId(slSmallloanProject.getProjectId());
					if (temp.getSourceId() == null) {
						this.slRepaymentSourceDao.save(temp);
					} else {
						SlRepaymentSource rPersistent = this.slRepaymentSourceDao
								.get(temp.getSourceId());
						BeanUtil.copyNotNullProperties(rPersistent, temp);
						this.slRepaymentSourceDao.save(rPersistent);
					}
				}
			}
			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
			slSmallloanProject.setCompanyId(persistent.getCompanyId());
			// end
			// 更新项目和项目编辑时不能改变states字段的值，这个字段的值和节点放款及款项计划确认有关 2012-12-27 update
			// by liny
			slSmallloanProject.setStates(persistent.getStates());
			slSmallloanProject.setIsOtherFlow(persistent.getIsOtherFlow());
			BeanUtil.copyNotNullProperties(persistent, slSmallloanProject);
			/*BeanUtils.copyProperties(slSmallloanProject, persistent,
					new String[] { "id", "operationType", "flowType",
							"oppositeType", "oppositeID", "projectName",
							"projectNumber", "oppositeType", "businessType",
							"createDate", "mineId" });*/
			if (isDeleteAllFundIntent.equals("1")) {

				List<SlFundIntent> allintent = this.slFundIntentDao
						.getByProjectId1(slSmallloanProject.getProjectId(),
								"SmallLoan");

				for (SlFundIntent s : allintent) {
					slFundIntentDao.evict(s);
					if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						this.slFundIntentDao.remove(s);
					}
				}
				for (SlFundIntent a : slFundIntents) {
					slFundIntentDao.evict(a);
					this.slFundIntentDao.save(a);
				}
			}

			for (SlActualToCharge a : slActualToCharges) {
				this.slActualToChargeDao.save(a);

			}
			if(null!=projectPropertyClassification){
				projectPropertyClassificationDao.savePropertyInfo(projectPropertyClassification);
				sb.append(",propertyId:" + projectPropertyClassification.getId());
			}
			if(null!=slConferenceRecord){
				if(slConferenceRecord.getConforenceId()==null){
					slConferenceRecordDao.save(slConferenceRecord);
					
					
				}else{
					SlConferenceRecord orgSlConferenceRecord=slConferenceRecordDao.get(slConferenceRecord.getConforenceId());
					try{
						BeanUtil.copyNotNullProperties(orgSlConferenceRecord, slConferenceRecord);
						slConferenceRecordDao.save(orgSlConferenceRecord);
					}catch(Exception ex){
						logger.error(ex.getMessage());
					}
				}
				sb.append(",conforenceId:" +slConferenceRecord.getConforenceId());
			}
		/*	Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));*/

			this.dao.merge(persistent);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public List<SlSmallloanProject> getProjectById(Long projectId) {
		return dao.getProjectById(projectId);
	}

	public List<SlSmallloanProject> getProjectByCompanyId(Long companyId) {
		return dao.getProjectByCompanyId(companyId);
	}

	public Integer updateMcroLoanDiligenceCreditFlowProject(
			FlowRunInfo flowRunInfo) {

		try {
			String borrowerInfo = flowRunInfo.getRequest().getParameter(
					"borrowerInfo");
			String isAheadPay = flowRunInfo.getRequest().getParameter(
					"isAheadPay");
			String isPreposePayAccrual = flowRunInfo.getRequest().getParameter(
					"isPreposePayAccrualCheck");
			String degree = flowRunInfo.getRequest().getParameter("degree");

			String flowOver = flowRunInfo.getRequest().getParameter("flowOver");// 项目放款状态:1表示项目放款;2表示未放款,项目终止。

			List<BorrowerInfo> listBO = new ArrayList<BorrowerInfo>();
			if (null != borrowerInfo && !"".equals(borrowerInfo)) {
				String[] borrowerInfoArr = borrowerInfo.split("@");
				for (int i = 0; i < borrowerInfoArr.length; i++) {
					String str = borrowerInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						BorrowerInfo bo = (BorrowerInfo) JSONMapper.toJava(
								parser.nextValue(), BorrowerInfo.class);
						listBO.add(bo);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");

			if (listBO.size() > 0) {

				for (int i = 0; i < listBO.size(); i++) {
					BorrowerInfo bo = listBO.get(i);
					if (bo.getBorrowerInfoId() == null) {
						bo.setProjectId(project.getProjectId());
						bo.setBusinessType(project.getBusinessType());
						bo.setOperationType(project.getOperationType());
						this.borrowerInfoDao.save(bo);
					} else {
						BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo
								.getBorrowerInfoId());
						BeanUtils.copyProperties(boPersistent, bo);
						this.borrowerInfoDao.merge(boPersistent);
					}
					if (null != bo.getType() && bo.getType() == 0) {
						if (null != bo.getCustomerId()) {
							Enterprise e = this.enterpriseDao.getById(bo
									.getCustomerId());
							e.setArea(bo.getAddress());
							e.setCciaa(bo.getCardNum());
							e.setTelephone(bo.getTelPhone());
							this.enterpriseDao.merge(e);
						}
					} else if (null != bo.getType() && bo.getType() == 1) {
						Person p = this.personDao.getById(bo.getCustomerId());
						p.setPostaddress(bo.getAddress());
						p.setCardnumber(bo.getCardNum());
						p.setCellphone(bo.getTelPhone());
						this.personDao.merge(p);
					}
				}
			}

			String repaymentSource = flowRunInfo.getRequest().getParameter(
					"repaymentSource");
			List<SlRepaymentSource> listRepaymentSources = new ArrayList<SlRepaymentSource>(); // 第一还款来源
			if (null != repaymentSource && !"".equals(repaymentSource)) {
				String[] repaymentSourceArr = repaymentSource.split("@");
				for (int i = 0; i < repaymentSourceArr.length; i++) {

					try {
						String str = repaymentSourceArr[i];
						JSONParser parser = new JSONParser(
								new StringReader(str));
						SlRepaymentSource slRepaymentSource = (SlRepaymentSource) JSONMapper
								.toJava(parser.nextValue(),
										SlRepaymentSource.class);
						listRepaymentSources.add(slRepaymentSource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (listRepaymentSources.size() > 0) {

				for (SlRepaymentSource temp : listRepaymentSources) {

					boolean flag = StringUtil.isNumeric(temp.getTypeId());
					if (temp.getSourceId() == null) {
						GlobalType globalType = globalTypeDao.getByNodeKey(
								"repaymentSource").get(0);
						if (!flag) {

							Dictionary dic = new Dictionary();
							dic.setItemValue(temp.getTypeId());
							dic.setItemName(globalType.getTypeName());
							dic.setProTypeId(globalType.getProTypeId());
							dic.setDicKey(temp.getTypeId());
							dic.setStatus("0");
							dictionaryDao.save(dic);

							temp.setTypeId(String.valueOf(dic.getDicId()));
						} else {

							Dictionary cd = dictionaryDao.get(Long.valueOf(temp
									.getTypeId()));
							if (null == cd) {
								Dictionary dic = new Dictionary();
								dic.setItemValue(temp.getTypeId());
								dic.setItemName(globalType.getTypeName());
								dic.setProTypeId(globalType.getProTypeId());
								dic.setDicKey(temp.getTypeId());
								dic.setStatus("0");
								dictionaryDao.save(dic);
								temp.setTypeId(String.valueOf(dic.getDicId()));
							}
						}
						temp.setProjId(project.getProjectId());
						this.slRepaymentSourceDao.save(temp);
					} else {
						SlRepaymentSource rPersistent = this.slRepaymentSourceDao
								.get(temp.getSourceId());
						BeanUtil.copyNotNullProperties(rPersistent, temp);
						this.slRepaymentSourceDao.save(rPersistent);
					}
				}
			}

			if (null != degree && !"".equals(degree)) {
				project.setAppUserId(degree);
			}
			SlSmallloanProject persistent = this.dao
					.get(project.getProjectId());
			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
			project.setCompanyId(persistent.getCompanyId());
			// end
			// 更新项目和项目编辑时不能改变states字段的值，这个字段的值和节点放款及款项计划确认有关 2012-12-27 update
			// by liny
			project.setStates(persistent.getStates());
			project.setIsOtherFlow(persistent.getIsOtherFlow());
			BeanUtils.copyProperties(project, persistent, new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "oppositeType", "businessType",
					"createDate" });
			if (isAheadPay != null) {
				persistent.setIsAheadPay(Short.valueOf("1"));
			} else {
				persistent.setIsAheadPay(Short.valueOf("0"));
			}

			/*
			 * if(isPreposePayAccrual!=null){
			 * 
			 * persistent.setIsPreposePayAccrual(1); } else {
			 * persistent.setIsPreposePayAccrual(0);
			 * 
			 * }
			 */
			// 小额融资项目节点提交改变项目状态。
			if (null != flowOver) {
				/*
				 * if(Integer.parseInt(fangk)==1){
				 * 
				 * }else if(Integer.parseInt(fangk)==2){
				 * ////更新项目状态为3:终止,并且更新流程状态为终止。 VProcessRunData vp = null; Long
				 * projectId = persistent.getProjectId(); List<VProcessRunData>
				 * vpList =
				 * processRunDataService.getByTaskId(flowRunInfo.getTaskId());
				 * if(vpList!=null||vpList.size()!=0){ vp = vpList.get(0); }
				 * this.updateProcessRunStatus(vp.getRunId(), projectId); }
				 */
			}

			Long projectId = project.getProjectId();
			// 款项计划
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String maxintentDate = slFundIntentService.savejson(slFundIentJson,
					projectId, "SmallLoan", Short.parseShort("1"), 1L);
			// if(!persistent.getAccrualtype().equals("singleInterest")){
			// persistent.setPayintentPeriod(Integer.valueOf(sumintent));
			// }
			// 费用收支
			StatsPro statsPro = new StatsPro();
			statsPro.calcuProIntentDate(persistent);
			String slActualToChargeJson = flowRunInfo.getRequest()
					.getParameter("slActualToChargeJson");
			slActualToChargeService.savejson(slActualToChargeJson, projectId,
					"SmallLoan", Short.parseShort("0"), persistent
							.getCompanyId());
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));
			/**
			 * 年化净利率
			 */
			ProjectActionUtil pu = new ProjectActionUtil();
			pu.getSmallloanMode(persistent);
			this.dao.merge(persistent);

			Person person = new Person();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");

			// 更新person信息开始
			Person persistentPerson = this.personDao.getById(person.getId());
			persistentPerson.setMarry(person.getMarry());
			persistentPerson.setName(person.getName());
			persistentPerson.setSex(person.getSex());
			persistentPerson.setCardtype(person.getCardtype());
			persistentPerson.setCardnumber(person.getCardnumber());
			persistentPerson.setCellphone(person.getCellphone());
			persistentPerson.setPostcode(person.getPostcode());
			persistentPerson.setBirthday(person.getBirthday());
			persistentPerson.setUnitaddress(person.getUnitaddress());
			persistentPerson.setCurrentcompany(person.getCurrentcompany());
			persistentPerson.setFamilyaddress(person.getFamilyaddress());
			persistentPerson.setHukou(person.getHukou());
			persistentPerson.setPostaddress(person.getPostaddress());
			personDao.merge(persistentPerson);

			// 更新person信息结束
			// 账号信息
			EnterpriseBank enterpriseBank = new EnterpriseBank();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,
					"enterpriseBank");
			if (enterpriseBank.getId() == null || enterpriseBank.getId() == 0) {
				List<EnterpriseBank> list = enterpriseBankService.getBankList(
						person.getId(), Short.valueOf("1"), Short.valueOf("0"),Short.valueOf("0"));
				for (EnterpriseBank e : list) {
					e.setIscredit(Short.valueOf("1"));
					creditBaseDao.updateDatas(e);
				}
				enterpriseBank.setOpenCurrency(Short.valueOf("0"));
				enterpriseBank.setEnterpriseid(person.getId());
				enterpriseBank.setIscredit(Short.valueOf("0"));
				enterpriseBank.setIsEnterprise(Short.valueOf("1"));
				enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
				enterpriseBank.setIsInvest(Short.valueOf("0"));
			
				creditBaseDao.saveDatas(enterpriseBank);
			} else {
				EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(
						EnterpriseBank.class, enterpriseBank.getId());
				if (bank.getOpenCurrency() == null) {
					bank.setOpenCurrency(Short.valueOf("0"));
				}
				bank.setAccountnum(enterpriseBank.getAccountnum());
				bank.setAccountType(enterpriseBank.getAccountType());
				bank.setBankid(enterpriseBank.getBankid());
				// bank.setBankname(enterpriseBank.getBankname());
				bank.setName(enterpriseBank.getName());
				bank.setOpenType(enterpriseBank.getOpenType());
				bank.setAreaId(enterpriseBank.getAreaId());
				bank.setAreaName(enterpriseBank.getAreaName());
				bank.setBankOutletsName(bank.getBankOutletsName());
				creditBaseDao.updateDatas(bank);
			}
			baseCustomService.getCustomToweb("1", persistentPerson.getId(), 0);
			// 配偶信息
			if (person.getMarry() == 317) {
				Spouse spouse = new Spouse();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
						"spouse");
				if (spouse.getSpouseId() == null || spouse.getSpouseId() == 0) {
					spouse.setPersonId(person.getId());
					creditBaseDao.saveDatas(spouse);
				} else {
					Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
							spouse.getSpouseId());
					s.setCardnumber(spouse.getCardnumber());
					s.setCardtype(spouse.getCardtype());
					s.setCurrentcompany(spouse.getCurrentcompany());
					s.setDgree(spouse.getDgree());
					s.setJob(spouse.getJob());
					s.setLinkTel(spouse.getLinkTel());
					s.setName(spouse.getName());
					s.setPoliticalStatus(spouse.getPoliticalStatus());
					creditBaseDao.updateDatas(s);
				}
			} else {
				Spouse spouse = new Spouse();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
						"spouse");
				if (spouse.getSpouseId() != null && spouse.getSpouseId() == 0) {
					Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
							spouse.getSpouseId());
					creditBaseDao.deleteDatas(s);
				}
			}
			/**
			 * 财务信息
			FinanceInfo financeInfo = new FinanceInfo();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), financeInfo,
					"financeInfo");
			if (financeInfo.getFinanceInfoId() == null) {
				financeInfo.setProjectId(persistent.getProjectId());
				financeInfo.setBusinessType(persistent.getBusinessType());
				financeInfoDao.save(financeInfo);
			} else {
				FinanceInfo orgFinanceInfo = financeInfoDao.get(financeInfo
						.getFinanceInfoId());
				BeanUtil.copyNotNullProperties(orgFinanceInfo, financeInfo);
				orgFinanceInfo.setBusinessType(persistent.getBusinessType());
				financeInfoDao.save(orgFinanceInfo);

			}
			*/
			Map<String, Object> vars = new HashMap<String, Object>();
			String sbhPartake = flowRunInfo.getRequest().getParameter(
					"sbhPartake");
			if (sbhPartake != null && !"".equals(sbhPartake)) {
				String assignUserIds = sbhPartake;
				vars.put("flowAssignId", assignUserIds);
			}
			flowRunInfo.getVariables().putAll(vars);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷款尽调提交任务  信息出错:" + e.getMessage());
			return 0;
		}
	}

	/**
	 * 小贷补录历史项目信息执行下一步
	 */
	@Override
	public Integer updateSmallHistoryRecords(FlowRunInfo flowRunInfo) {
		try {
			String borrowerInfo = flowRunInfo.getRequest().getParameter(
					"borrowerInfo");
			String gudongInfo = flowRunInfo.getRequest().getParameter(
					"gudongInfo");
			String isAheadPay = flowRunInfo.getRequest().getParameter(
					"isAheadPay");
			String isPreposePayAccrual = flowRunInfo.getRequest().getParameter(
					"isPreposePayAccrualCheck");
			String degree = flowRunInfo.getRequest().getParameter("degree");

			String flowOver = flowRunInfo.getRequest().getParameter("flowOver");// 项目放款状态:1表示项目放款;2表示未放款,项目终止。
			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");
			// 保存共同借款人信息
			List<BorrowerInfo> listBO = new ArrayList<BorrowerInfo>();
			if (null != borrowerInfo && !"".equals(borrowerInfo)) {
				String[] borrowerInfoArr = borrowerInfo.split("@");
				for (int i = 0; i < borrowerInfoArr.length; i++) {
					String str = borrowerInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						BorrowerInfo bo = (BorrowerInfo) JSONMapper.toJava(
								parser.nextValue(), BorrowerInfo.class);
						listBO.add(bo);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 保存股东信息
			List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
			if (null != gudongInfo && !"".equals(gudongInfo)) {
				String[] shareequityArr = gudongInfo.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper
								.toJava(parser.nextValue(),
										EnterpriseShareequity.class);
						listES.add(enterpriseShareequity);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (listBO.size() > 0) {
				for (int i = 0; i < listBO.size(); i++) {
					BorrowerInfo bo = listBO.get(i);
					if (bo.getBorrowerInfoId() == null) {
						bo.setProjectId(project.getProjectId());
						bo.setBusinessType(project.getBusinessType());
						bo.setOperationType(project.getOperationType());
						this.borrowerInfoDao.save(bo);
					} else {
						BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo
								.getBorrowerInfoId());
						BeanUtils.copyProperties(boPersistent, bo);
						this.borrowerInfoDao.merge(boPersistent);
					}
					if (null != bo.getType() && bo.getType() == 0) {
						if (null != bo.getCustomerId()) {
							Enterprise e = this.enterpriseDao.getById(bo
									.getCustomerId());
							e.setArea(bo.getAddress());
							e.setCciaa(bo.getCardNum());
							e.setTelephone(bo.getTelPhone());
							this.enterpriseDao.merge(e);
						}
					} else if (null != bo.getType() && bo.getType() == 1) {
						Person p = this.personDao.getById(bo.getCustomerId());
						p.setPostaddress(bo.getAddress());
						p.setCardnumber(bo.getCardNum());
						p.setCellphone(bo.getTelPhone());
						this.personDao.merge(p);
					}
				}
			}
			// 保存第一还款来源
			String repaymentSource = flowRunInfo.getRequest().getParameter(
					"repaymentSource");
			List<SlRepaymentSource> listRepaymentSources = new ArrayList<SlRepaymentSource>(); // 第一还款来源
			if (null != repaymentSource && !"".equals(repaymentSource)) {
				String[] repaymentSourceArr = repaymentSource.split("@");
				for (int i = 0; i < repaymentSourceArr.length; i++) {
					try {
						String str = repaymentSourceArr[i];
						JSONParser parser = new JSONParser(
								new StringReader(str));
						SlRepaymentSource slRepaymentSource = (SlRepaymentSource) JSONMapper
								.toJava(parser.nextValue(),
										SlRepaymentSource.class);
						listRepaymentSources.add(slRepaymentSource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (listRepaymentSources.size() > 0) {
				for (SlRepaymentSource temp : listRepaymentSources) {
					boolean flag = StringUtil.isNumeric(temp.getTypeId());
					if (temp.getSourceId() == null) {
						GlobalType globalType = globalTypeDao.getByNodeKey(
								"repaymentSource").get(0);
						if (!flag) {
							Dictionary dic = new Dictionary();
							dic.setItemValue(temp.getTypeId());
							dic.setItemName(globalType.getTypeName());
							dic.setProTypeId(globalType.getProTypeId());
							dic.setDicKey(temp.getTypeId());
							dic.setStatus("0");
							dictionaryDao.save(dic);
							temp.setTypeId(String.valueOf(dic.getDicId()));
						} else {

							Dictionary cd = dictionaryDao.get(Long.valueOf(temp
									.getTypeId()));
							if (null == cd) {
								Dictionary dic = new Dictionary();
								dic.setItemValue(temp.getTypeId());
								dic.setItemName(globalType.getTypeName());
								dic.setProTypeId(globalType.getProTypeId());
								dic.setDicKey(temp.getTypeId());
								dic.setStatus("0");
								dictionaryDao.save(dic);
								temp.setTypeId(String.valueOf(dic.getDicId()));
							}
						}
						temp.setProjId(project.getProjectId());
						this.slRepaymentSourceDao.save(temp);
					} else {
						SlRepaymentSource rPersistent = this.slRepaymentSourceDao
								.get(temp.getSourceId());
						BeanUtil.copyNotNullProperties(rPersistent, temp);
						this.slRepaymentSourceDao.save(rPersistent);
					}
				}
			}

			if (null != degree && !"".equals(degree)) {
				project.setAppUserId(degree);
			}
			SlSmallloanProject persistent = this.dao
					.get(project.getProjectId());
			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
			project.setCompanyId(persistent.getCompanyId());
			// end
			// 更新项目和项目编辑时不能改变states字段的值，这个字段的值和节点放款及款项计划确认有关 2012-12-27 update
			// by liny
			project.setStates(persistent.getStates());
			project.setIsOtherFlow(persistent.getIsOtherFlow());
			BeanUtils.copyProperties(project, persistent, new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "oppositeType", "businessType",
					"createDate" });
			if (isAheadPay != null) {
				persistent.setIsAheadPay(Short.valueOf("1"));
			} else {
				persistent.setIsAheadPay(Short.valueOf("0"));
			}
			/*
			 * if(isPreposePayAccrual!=null){
			 * persistent.setIsPreposePayAccrual(1); }else {
			 * persistent.setIsPreposePayAccrual(0);
			 * 
			 * }
			 */
			/*
			 * //小额融资项目节点提交改变项目状态。 if(null!=flowOver) {
			 * if(Integer.parseInt(fangk)==1){
			 * 
			 * }else if(Integer.parseInt(fangk)==2){
			 * ////更新项目状态为3:终止,并且更新流程状态为终止。 VProcessRunData vp = null; Long
			 * projectId = persistent.getProjectId(); List<VProcessRunData>
			 * vpList =
			 * processRunDataService.getByTaskId(flowRunInfo.getTaskId());
			 * if(vpList!=null||vpList.size()!=0){ vp = vpList.get(0); }
			 * this.updateProcessRunStatus(vp.getRunId(), projectId); } }
			 */

			Long projectId = project.getProjectId();

			// 款项计划
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String maxintentDate = slFundIntentService.savejson(slFundIentJson,
					projectId, "SmallLoan", Short.parseShort("0"), persistent
							.getCompanyId());
			/*
			 * if(!persistent.getAccrualtype().equals("singleInterest")){
			 * persistent.setPayintentPeriod(Integer.valueOf(sumintent)); }
			 */
			/*StatsPro statsPro = new StatsPro();
			statsPro.calcuProIntentDate(persistent);*/
			// 费用收支

			String slActualToChargeJson = flowRunInfo.getRequest()
					.getParameter("slActualToChargeJson");
			slActualToChargeService.savejson(slActualToChargeJson, projectId,
					"SmallLoan", Short.parseShort("0"), persistent
							.getCompanyId());
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));
			/**
			 * 年化净利率
			 */
			ProjectActionUtil pu = new ProjectActionUtil();
			pu.getSmallloanMode(persistent);
			this.dao.merge(persistent);

			Short flag = 0;
			if (persistent.getOppositeType().equals("company_customer")) // 企业
			{
				flag = 0;
				Enterprise enterprise = new Enterprise();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise,
						"enterprise");
				Enterprise epersistent = this.enterpriseDao
						.getById(enterprise.getId());
				epersistent.setEnterprisename(enterprise.getEnterprisename());
				epersistent.setArea(enterprise.getArea());
				epersistent.setShortname(enterprise.getShortname());
				epersistent.setHangyeType(enterprise.getHangyeType());
				epersistent.setOrganizecode(enterprise.getOrganizecode());
				epersistent.setCciaa(enterprise.getCciaa());
				epersistent.setTelephone(enterprise.getTelephone());
				epersistent.setPostcoding(enterprise.getPostcoding());

				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,
						"person");
				if (null != person.getId() && person.getId() != 0) {
					Person ppersistent = this.personDao
							.getById(epersistent.getLegalpersonid());
					ppersistent.setMarry(person.getMarry());
					ppersistent.setName(person.getName());
					ppersistent.setSex(person.getSex());
					ppersistent.setCardtype(person.getCardtype());
					ppersistent.setCardnumber(person.getCardnumber());
					ppersistent.setCellphone(person.getCellphone());
					ppersistent.setPostcode(person.getPostcode());
					ppersistent.setSelfemail(person.getSelfemail());
					ppersistent.setPostaddress(person.getPostaddress());
					personDao.merge(ppersistent);
					baseCustomService.getCustomToweb("1", ppersistent.getId(),
							0);
				} else {
					Person p = new Person();
					p.setMarry(person.getMarry());
					p.setName(person.getName());
					p.setSex(person.getSex());
					p.setCardtype(person.getCardtype());
					p.setCardnumber(person.getCardnumber());
					p.setCellphone(person.getCellphone());
					p.setPostcode(person.getPostcode());
					p.setSelfemail(person.getSelfemail());
					p.setPostaddress(person.getPostaddress());
					p.setCompanyId(ContextUtil.getLoginCompanyId());
					personDao.save(p);
					epersistent.setLegalpersonid(p.getId());
					baseCustomService.getCustomToweb("1", p.getId(), 0);
				}
				enterpriseDao.merge(epersistent);
				if (listES.size() > 0) {
					for (int i = 0; i < listES.size(); i++) {
						EnterpriseShareequity es = listES.get(i);
						if (es.getId() == null) {
							es.setEnterpriseid(epersistent.getId());
							this.enterpriseShareequityDao.save(es);
						} else {
							EnterpriseShareequity esPersistent = this.enterpriseShareequityDao
									.load(es.getId());
							BeanUtils.copyProperties(es, esPersistent,
									new String[] { "id", "enterpriseid" });
							this.enterpriseShareequityDao.merge(esPersistent);
						}
					}
				}
			} else if (persistent.getOppositeType().equals("person_customer")) {
				flag = 1;
				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,
						"person");

				// 更新person信息开始
				Person persistentPerson = this.personDao
						.getById(person.getId());
				persistentPerson.setMarry(person.getMarry());
				persistentPerson.setName(person.getName());
				persistentPerson.setSex(person.getSex());
				persistentPerson.setCardtype(person.getCardtype());
				persistentPerson.setCardnumber(person.getCardnumber());
				persistentPerson.setTelphone(person.getTelphone());
				persistentPerson.setPostcode(person.getPostcode());
				persistentPerson.setSelfemail(person.getSelfemail());
				persistentPerson.setPostaddress(person.getPostaddress());
				personDao.merge(persistentPerson);
				// 更新person信息结束
				// 更新配偶信息
				if (person.getMarry() == 317) {
					Spouse spouse = new Spouse();
					BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
							"spouse");
					if (spouse.getSpouseId() == null
							|| spouse.getSpouseId() == 0) {
						spouse.setPersonId(person.getId());
						creditBaseDao.saveDatas(spouse);
					} else {
						Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
								spouse.getSpouseId());
						s.setCardnumber(spouse.getCardnumber());
						s.setCardtype(spouse.getCardtype());
						s.setCurrentcompany(spouse.getCurrentcompany());
						s.setDgree(spouse.getDgree());
						s.setJob(spouse.getJob());
						s.setLinkTel(spouse.getLinkTel());
						s.setName(spouse.getName());
						s.setPoliticalStatus(spouse.getPoliticalStatus());
						creditBaseDao.updateDatas(s);
					}
				} else {
					Spouse spouse = new Spouse();
					BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
							"spouse");
					if (spouse.getSpouseId() != null
							&& spouse.getSpouseId() == 0) {
						Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
								spouse.getSpouseId());
						creditBaseDao.deleteDatas(s);
					}
				}
			}
			EnterpriseBank enterpriseBank = new EnterpriseBank();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,
					"enterpriseBank");
			if (enterpriseBank.getId() == null || enterpriseBank.getId() == 0) {
				List<EnterpriseBank> list = enterpriseBankService.getBankList(
						persistent.getOppositeID().intValue(), flag, Short
								.valueOf("0"),Short.valueOf("0"));
				for (EnterpriseBank e : list) {
					e.setIscredit(Short.valueOf("1"));
					creditBaseDao.updateDatas(e);
				}
				enterpriseBank.setEnterpriseid(persistent.getOppositeID()
						.intValue());
				enterpriseBank.setIscredit(Short.valueOf("0"));
				enterpriseBank.setIsEnterprise(flag);
				enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
				creditBaseDao.saveDatas(enterpriseBank);
				enterpriseBank.setIsInvest(Short.valueOf("0"));
			} else {
				EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(
						EnterpriseBank.class, enterpriseBank.getId());
				bank.setAccountnum(enterpriseBank.getAccountnum());
				bank.setAccountType(enterpriseBank.getAccountType());
				bank.setBankid(enterpriseBank.getBankid());
				bank.setBankname(enterpriseBank.getBankname());
				bank.setName(enterpriseBank.getName());
				bank.setOpenType(enterpriseBank.getOpenType());
				bank.setAreaId(enterpriseBank.getAreaId());
				bank.setAreaName(enterpriseBank.getAreaName());
				bank.setBankOutletsName(bank.getBankOutletsName());
				creditBaseDao.updateDatas(bank);
			}
			baseCustomService.getCustomToweb(flag.toString(), persistent
					.getOppositeID().intValue(), 0);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷补录历史项目信息提交任务  信息出错:" + e.getMessage());
			return 0;
		}
	}

	/**
	 * 小贷常规、标准、快速流程，尽职调查提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	// 能不能在shi点
	@Override
	public Integer diligenceNextStep(FlowRunInfo flowRunInfo) {
		try {
			String borrowerInfo = flowRunInfo.getRequest().getParameter(
					"borrowerInfo");
			String gudongInfo = flowRunInfo.getRequest().getParameter(
					"gudongInfo");
			String isAheadPay = flowRunInfo.getRequest().getParameter(
					"isAheadPay");
			String isPreposePayAccrual = flowRunInfo.getRequest().getParameter(
					"isPreposePayAccrualCheck");
			String slActualToChargeJson=flowRunInfo.getRequest().getParameter("slActualToChargeJson");
			String degree = flowRunInfo.getRequest().getParameter("degree");
			// String flowOver =
			// flowRunInfo.getRequest().getParameter("flowOver");//
			// 项目放款状态:1表示项目放款;2表示未放款,项目终止。
			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");

			// 保存共同借款人信息
			List<BorrowerInfo> listBO = new ArrayList<BorrowerInfo>();
			if (null != borrowerInfo && !"".equals(borrowerInfo)) {
				String[] borrowerInfoArr = borrowerInfo.split("@");
				for (int i = 0; i < borrowerInfoArr.length; i++) {
					String str = borrowerInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						BorrowerInfo bo = (BorrowerInfo) JSONMapper.toJava(
								parser.nextValue(), BorrowerInfo.class);
						listBO.add(bo);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 保存股东信息
			List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
			if (null != gudongInfo && !"".equals(gudongInfo)) {
				String[] shareequityArr = gudongInfo.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper
								.toJava(parser.nextValue(),
										EnterpriseShareequity.class);
						listES.add(enterpriseShareequity);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (listBO.size() > 0) {
				for (int i = 0; i < listBO.size(); i++) {
					BorrowerInfo bo = listBO.get(i);
					if (bo.getBorrowerInfoId() == null) {
						bo.setProjectId(project.getProjectId());
						bo.setBusinessType(project.getBusinessType());
						bo.setOperationType(project.getOperationType());
						this.borrowerInfoDao.save(bo);
					} else {
						BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo
								.getBorrowerInfoId());
						BeanUtils.copyProperties(boPersistent, bo);
						this.borrowerInfoDao.merge(boPersistent);
					}
					if (null != bo.getType() && bo.getType() == 0) {
						if (null != bo.getCustomerId()) {
							Enterprise e = this.enterpriseDao.getById(bo
									.getCustomerId());
							e.setArea(bo.getAddress());
							e.setCciaa(bo.getCardNum());
							e.setTelephone(bo.getTelPhone());
							this.enterpriseDao.merge(e);
						}
					} else if (null != bo.getType() && bo.getType() == 1) {
						Person p = this.personDao.getById(bo.getCustomerId());
						p.setPostaddress(bo.getAddress());
						p.setCardnumber(bo.getCardNum());
						p.setCellphone(bo.getTelPhone());
						this.personDao.merge(p);
					}
				}
			}
			// 保存第一还款来源
			String repaymentSource = flowRunInfo.getRequest().getParameter(
					"repaymentSource");
			List<SlRepaymentSource> listRepaymentSources = new ArrayList<SlRepaymentSource>(); // 第一还款来源
			if (null != repaymentSource && !"".equals(repaymentSource)) {
				String[] repaymentSourceArr = repaymentSource.split("@");
				for (int i = 0; i < repaymentSourceArr.length; i++) {
					try {
						String str = repaymentSourceArr[i];
						JSONParser parser = new JSONParser(
								new StringReader(str));
						SlRepaymentSource slRepaymentSource = (SlRepaymentSource) JSONMapper
								.toJava(parser.nextValue(),
										SlRepaymentSource.class);
						listRepaymentSources.add(slRepaymentSource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (listRepaymentSources.size() > 0) {
				for (SlRepaymentSource temp : listRepaymentSources) {
					boolean flag = StringUtil.isNumeric(temp.getTypeId());
					if (temp.getSourceId() == null) {
						GlobalType globalType = globalTypeDao.getByNodeKey(
								"repaymentSource").get(0);
						if (!flag) {
							Dictionary dic = new Dictionary();
							dic.setItemValue(temp.getTypeId());
							dic.setItemName(globalType.getTypeName());
							dic.setProTypeId(globalType.getProTypeId());
							dic.setDicKey(temp.getTypeId());
							dic.setStatus("0");
							dictionaryDao.save(dic);
							temp.setTypeId(String.valueOf(dic.getDicId()));
						} else {

							Dictionary cd = dictionaryDao.get(Long.valueOf(temp
									.getTypeId()));
							if (null == cd) {
								Dictionary dic = new Dictionary();
								dic.setItemValue(temp.getTypeId());
								dic.setItemName(globalType.getTypeName());
								dic.setProTypeId(globalType.getProTypeId());
								dic.setDicKey(temp.getTypeId());
								dic.setStatus("0");
								dictionaryDao.save(dic);
								temp.setTypeId(String.valueOf(dic.getDicId()));
							}
						}
						temp.setProjId(project.getProjectId());
						this.slRepaymentSourceDao.save(temp);
					} else {
						SlRepaymentSource rPersistent = this.slRepaymentSourceDao
								.get(temp.getSourceId());
						BeanUtil.copyNotNullProperties(rPersistent, temp);
						this.slRepaymentSourceDao.save(rPersistent);
					}
				}
			}

			if (null != degree && !"".equals(degree)) {
				project.setAppUserId(degree);
			}
			SlSmallloanProject persistent = this.dao
					.get(project.getProjectId());
			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
			project.setCompanyId(persistent.getCompanyId());
			// end
			// 更新项目和项目编辑时不能改变states字段的值，这个字段的值和节点放款及款项计划确认有关 2012-12-27 update
			// by liny
			project.setStates(persistent.getStates());
			project.setIsOtherFlow(persistent.getIsOtherFlow());
			/*BeanUtils.copyProperties(persistent,project,new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "oppositeType", "businessType",
					"createDate" });*/
			BeanUtil.copyNotNullProperties(persistent, project);
			//BeanUtils.copyProperties(source, target, ignoreProperties)
			if (isAheadPay != null) {
				persistent.setIsAheadPay(Short.valueOf("1"));
			} else {
				persistent.setIsAheadPay(Short.valueOf("0"));
			}

			// 小贷标准流程使用
			if (isPreposePayAccrual != null && !"".equals(isPreposePayAccrual)) {
				persistent.setIsPreposePayAccrual(1);
			} else {
				persistent.setIsPreposePayAccrual(0);
			}

			/*
			 * //小额融资项目节点提交改变项目状态。 if(null!=flowOver) {
			 * if(Integer.parseInt(fangk)==1){
			 * 
			 * }else if(Integer.parseInt(fangk)==2){
			 * ////更新项目状态为3:终止,并且更新流程状态为终止。 VProcessRunData vp = null; Long
			 * projectId = persistent.getProjectId(); List<VProcessRunData>
			 * vpList =
			 * processRunDataService.getByTaskId(flowRunInfo.getTaskId());
			 * if(vpList!=null||vpList.size()!=0){ vp = vpList.get(0); }
			 * this.updateProcessRunStatus(vp.getRunId(), projectId); } }
			 */

			Long projectId = project.getProjectId();

			// 款项计划
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String maxintentDate = slFundIntentService.savejson(slFundIentJson,
					projectId, "SmallLoan", Short.parseShort("1"), persistent
							.getCompanyId());
			/*
			 * if(!persistent.getAccrualtype().equals("singleInterest")){
			 * persistent.setPayintentPeriod(Integer.valueOf(sumintent)); }
			 */
			StatsPro statsPro = new StatsPro();
			statsPro.calcuProIntentDate(persistent);
			
			//手续清单
			if(null!=slActualToChargeJson&&!"".equals(slActualToChargeJson)&&!"undefined".equals(slActualToChargeJson)){
	        slActualToChargeService.saveJson(slActualToChargeJson,project.getProjectId(),"SmallLoan",(short)0,1l,null);

            }

			/*// 费用收支

			String slActualToChargeJson = flowRunInfo.getRequest()
					.getParameter("slActualToChargeJson");
			slActualToChargeService.savejson(slActualToChargeJson, projectId,
					"SmallLoan", Short.parseShort("0"), persistent
							.getCompanyId());*/
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));
			/**
			 * 年化净利率
			 */
			ProjectActionUtil pu = new ProjectActionUtil();
			pu.getSmallloanMode(persistent);
			this.dao.merge(persistent);

			Short flag = 0;
			if (persistent.getOppositeType().equals("company_customer")) // 企业
			{
				flag = 0;
				Enterprise enterprise = new Enterprise();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise,
						"enterprise");
				Enterprise epersistent = this.enterpriseDao
						.getById(enterprise.getId());
				/*epersistent.setEnterprisename(enterprise.getEnterprisename());
				epersistent.setArea(enterprise.getArea());
				epersistent.setShortname(enterprise.getShortname());
				epersistent.setHangyeType(enterprise.getHangyeType());
				epersistent.setOrganizecode(enterprise.getOrganizecode());
				epersistent.setCciaa(enterprise.getCciaa());
				epersistent.setTelephone(enterprise.getTelephone());
				epersistent.setPostcoding(enterprise.getPostcoding());
				epersistent.setRootHangYeType(enterprise.getRootHangYeType());*/
				BeanUtil.copyNotNullProperties(epersistent, enterprise);
				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,
						"person");
				if (null != person.getId() && person.getId() != 0) {
					Person ppersistent = this.personDao
							.getById(epersistent.getLegalpersonid());
				/*	ppersistent.setMarry(person.getMarry());
					ppersistent.setName(person.getName());
					ppersistent.setSex(person.getSex());
					ppersistent.setCardtype(person.getCardtype());
					ppersistent.setCardnumber(person.getCardnumber());
					ppersistent.setCellphone(person.getCellphone());
					ppersistent.setPostcode(person.getPostcode());
					ppersistent.setSelfemail(person.getSelfemail());
					ppersistent.setPostaddress(person.getPostaddress());*/
					BeanUtil.copyNotNullProperties(ppersistent, person);
					personDao.merge(ppersistent);
					baseCustomService.getCustomToweb("1", ppersistent.getId(),
							0);
				} else {
					Person p = new Person();
					p.setMarry(person.getMarry());
					p.setName(person.getName());
					p.setSex(person.getSex());
					p.setCardtype(person.getCardtype());
					p.setCardnumber(person.getCardnumber());
					p.setCellphone(person.getCellphone());
					p.setPostcode(person.getPostcode());
					p.setSelfemail(person.getSelfemail());
					p.setPostaddress(person.getPostaddress());
					p.setCompanyId(ContextUtil.getLoginCompanyId());
					personDao.save(p);
					epersistent.setLegalpersonid(p.getId());
					baseCustomService.getCustomToweb("1", p.getId(), 0);
				}
				enterpriseDao.merge(epersistent);
				if (listES.size() > 0) {
					for (int i = 0; i < listES.size(); i++) {
						EnterpriseShareequity es = listES.get(i);
						if (es.getId() == null) {
							es.setEnterpriseid(epersistent.getId());
							this.enterpriseShareequityDao.save(es);
						} else {
							EnterpriseShareequity esPersistent = this.enterpriseShareequityDao
									.load(es.getId());
							BeanUtils.copyProperties(es, esPersistent,
									new String[] { "id", "enterpriseid" });
							this.enterpriseShareequityDao.merge(esPersistent);
						}
					}
				}
			} else if (persistent.getOppositeType().equals("person_customer")) {
				flag = 1;
				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,
						"person");

				// 更新person信息开始
				Person persistentPerson = this.personDao.getById(person.getId());
				/*persistentPerson.setMarry(person.getMarry());
				persistentPerson.setName(person.getName());
				persistentPerson.setSex(person.getSex());
				persistentPerson.setCardtype(person.getCardtype());
				persistentPerson.setCardnumber(person.getCardnumber());
				persistentPerson.setCellphone(person.getCellphone());
				persistentPerson.setPostcode(person.getPostcode());
				persistentPerson.setSelfemail(person.getSelfemail());
				persistentPerson.setPostaddress(person.getPostaddress());*/
				BeanUtil.copyNotNullProperties(persistentPerson, person);
				
				personDao.merge(persistentPerson);
				// 更新person信息结束
				// 更新配偶信息
				if (person.getMarry() == 317) {
					Spouse spouse = new Spouse();
					BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
							"spouse");
					if (spouse.getSpouseId() == null
							|| spouse.getSpouseId() == 0) {
						spouse.setPersonId(person.getId());
						creditBaseDao.saveDatas(spouse);
					} else {
						Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
								spouse.getSpouseId());
						s.setCardnumber(spouse.getCardnumber());
						s.setCardtype(spouse.getCardtype());
						s.setCurrentcompany(spouse.getCurrentcompany());
						s.setDgree(spouse.getDgree());
						s.setJob(spouse.getJob());
						s.setLinkTel(spouse.getLinkTel());
						s.setName(spouse.getName());
						s.setPoliticalStatus(spouse.getPoliticalStatus());
						creditBaseDao.updateDatas(s);
					}
				} else {
					Spouse spouse = new Spouse();
					BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
							"spouse");
					if (spouse.getSpouseId() != null
							&& spouse.getSpouseId() == 0) {
						Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
								spouse.getSpouseId());
						creditBaseDao.deleteDatas(s);
					}
				}
			}
			EnterpriseBank enterpriseBank = new EnterpriseBank();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,
					"enterpriseBank");
			if (enterpriseBank.getId() == null || enterpriseBank.getId() == 0) {
				List<EnterpriseBank> list = enterpriseBankService.getBankList(
						persistent.getOppositeID().intValue(), flag, Short
								.valueOf("0"),Short.valueOf("0"));
				for (EnterpriseBank e : list) {
					e.setIscredit(Short.valueOf("1"));
					creditBaseDao.updateDatas(e);
				}
				enterpriseBank.setEnterpriseid(persistent.getOppositeID()
						.intValue());
				enterpriseBank.setIscredit(Short.valueOf("0"));
				enterpriseBank.setIsEnterprise(flag);
				enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
				enterpriseBank.setIsInvest(Short.valueOf("0"));
			
				creditBaseDao.saveDatas(enterpriseBank);
			} else {
				EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(
						EnterpriseBank.class, enterpriseBank.getId());
				bank.setAccountnum(enterpriseBank.getAccountnum());
				bank.setAccountType(enterpriseBank.getAccountType());
				bank.setBankid(enterpriseBank.getBankid());
				bank.setBankname(enterpriseBank.getBankname());
				bank.setName(enterpriseBank.getName());
				bank.setOpenType(enterpriseBank.getOpenType());
				bank.setAreaId(enterpriseBank.getAreaId());
				bank.setAreaName(enterpriseBank.getAreaName());
				bank.setBankOutletsName(bank.getBankOutletsName());
				creditBaseDao.updateDatas(bank);
			}
			baseCustomService.getCustomToweb(flag.toString(), persistent
					.getOppositeID().intValue(), 0);
			jumpFlow(flowRunInfo);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷款尽调提交任务  信息出错:" + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<ProYearRate> getProYearRate() {
		// TODO Auto-generated method stub
		return dao.getProYearRate();
	}

	// 小额常规流程和快速流程最后一个节点提交的时候更新项目的状态。
	public Integer updateProjectStatus(FlowRunInfo flowRunInfo) {
		if (flowRunInfo.isBack()) {
			return 1;
		} else {
			try {
				String projectInMiddle = flowRunInfo.getRequest().getParameter(
						"projectInMiddle");
				String flowOver = flowRunInfo.getRequest().getParameter(
						"flowOver");
				if (null != flowOver) {
					String projectId = flowRunInfo.getRequest().getParameter(
							"slSmallloanProject.projectId");
					if (null != projectId) {
						List pmlist = mortgageService
								.getByBusinessTypeProjectId("SmallLoan", Long
										.parseLong(projectId));
						if (pmlist != null && pmlist.size() != 0) {
							for (int k = 0; k < pmlist.size(); k++) {
								ProcreditMortgage procreditMortgage = (ProcreditMortgage) pmlist
										.get(k);
								if (null != procreditMortgage.getIsTransact()
										&& procreditMortgage.getIsTransact() == true
										&& (null == procreditMortgage
												.getIsunchain() || procreditMortgage
												.getIsunchain() == false)) {
									procreditMortgage
											.setMortgageStatus("xmwcwjc");
								}
								mortgageService.updateMortgage(
										procreditMortgage.getId(),
										procreditMortgage);
							}
						}
						SlSmallloanProject sl = dao.get(new Long(projectId));
						if (null != sl) {
							sl
									.setProjectStatus(Constants.PROJECT_STATUS_COMPLETE);
							sl.setEndDate(DateUtil
									.parseDate(DateUtil
											.getNowDateTime("yyyy-MM-dd"),
											"yyyy-MM-dd"));
							dao.merge(sl);
							return 1;
						}
					}
				}
				if (null != projectInMiddle) {
					String projectId = flowRunInfo.getRequest().getParameter(
							"slSmallloanProject.projectId");
					if (null != projectId) {
						List pmlist = mortgageService
								.getByBusinessTypeProjectId("SmallLoan", Long
										.parseLong(projectId));
						if (pmlist != null && pmlist.size() != 0) {
							for (int k = 0; k < pmlist.size(); k++) {
								ProcreditMortgage procreditMortgage = (ProcreditMortgage) pmlist
										.get(k);
								if (null != procreditMortgage.getIsTransact()
										&& procreditMortgage.getIsTransact() == true
										&& (null == procreditMortgage
												.getIsunchain() || procreditMortgage
												.getIsunchain() == false)) {
									procreditMortgage
											.setMortgageStatus("xmwcwjc");
								}
								mortgageService.updateMortgage(
										procreditMortgage.getId(),
										procreditMortgage);
							}
						}
						SlSmallloanProject sl = dao.get(new Long(projectId));
						if (null != sl) {
							sl
									.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
							dao.merge(sl);
							return 1;
						}
					}
				} else {
					return 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("业务主管审核执行下一步出错:" + e.getMessage());
				return 0;
			}
			return 0;
		}
	}

	public List<SlSmallloanProject> getList(short operationType,
			Date startTime, Date endTime) {
		return dao.getList(operationType, startTime, endTime);
	}

	public List<SlSmallloanProject> getListByProjectStatus(short operationType,
			Date startTime, Date endTime, short projectStatus) {
		return dao.getListByProjectStatus(operationType, startTime, endTime,
				projectStatus);
	}



	@Override
	public List<SlSmallloanProject> getProDetail(Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer superviseCreditFlowProject(FlowRunInfo flowRunInfo) {
		try {

			if (flowRunInfo.isBack()) {

				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String slSuperviseDatas = flowRunInfo.getRequest()
						.getParameter("slSuperviseInfo"); // 保中监管信息
				String projectId = flowRunInfo.getRequest().getParameter(
						"projectId"); // 项目ID
				String slActualToChargeData = flowRunInfo.getRequest()
						.getParameter("slActualToChargeData");
				String fundIntentJsonData = flowRunInfo.getRequest()
						.getParameter("fundIntentJsonData");
				Short flag = Short.parseShort(flowRunInfo.getRequest()
						.getParameter("flag"));
				String businessType = flowRunInfo.getRequest().getParameter(
						"businessType");
				if (null != slSuperviseDatas && !"".equals(slSuperviseDatas)) {

					String[] shareequityArr = slSuperviseDatas.split("@");
					for (int i = 0; i < shareequityArr.length; i++) {
						String str = shareequityArr[i];
						JSONParser parser = new JSONParser(
								new StringReader(str));
						try {

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				slActualToChargeService.savejson(slActualToChargeData, Long
						.parseLong(projectId), businessType, flag, null);
				slFundIntentService.savejson1(fundIntentJsonData, Long
						.parseLong(projectId), businessType, flag, Short
						.valueOf("0"), null);
				SlSmallloanProject persistent = this.dao.get(Long
						.parseLong(projectId));
				Map<String, BigDecimal> map = slFundIntentService
						.saveProjectfiance(persistent.getProjectId(),
								"SmallLoan");
				persistent.setPaychargeMoney(map.get("paychargeMoney"));
				persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
				persistent.setAccrualMoney(map.get("loanInterest"));
				persistent.setConsultationMoney(map.get("consultationMoney"));
				persistent.setServiceMoney(map.get("serviceMoney"));
				this.dao.merge(persistent);

				// String superviseResult =
				// flowRunInfo.getRequest().getParameter("superviseResult");
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					vars.put("superviseResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}

				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷中监管执行下一步出错:" + e.getMessage());
			return 0;
		}

	}

	@Override
	public Integer superviseFastCreditFlowProject(FlowRunInfo flowRunInfo) {
		try {

			if (flowRunInfo.isBack()) {

				return 1;
			} else {
				String slSuperviseDatas = flowRunInfo.getRequest()
						.getParameter("slSuperviseInfo"); // 保中监管信息
				String projectId = flowRunInfo.getRequest().getParameter(
						"slfprojectId"); // 项目ID
				String slActualToChargeData = flowRunInfo.getRequest()
						.getParameter("slActualToChargeData");
				String fundIntentJsonData = flowRunInfo.getRequest()
						.getParameter("fundIntentJsonData");
				Short flag = Short.parseShort(flowRunInfo.getRequest()
						.getParameter("flag"));
				String businessType = flowRunInfo.getRequest().getParameter(
						"businessType");
				
				if (null != slSuperviseDatas && !"".equals(slSuperviseDatas)) {

					String[] shareequityArr = slSuperviseDatas.split("@");
					for (int i = 0; i < shareequityArr.length; i++) {
						String str = shareequityArr[i];
						JSONParser parser = new JSONParser(
								new StringReader(str));
						try {
							

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			

				slActualToChargeService.savejson(slActualToChargeData, Long
						.parseLong(projectId), businessType, flag, null);
				slFundIntentService.savejson1(fundIntentJsonData, Long
						.parseLong(projectId), businessType, flag, Short
						.valueOf("0"), null);
				SlSmallloanProject persistent = this.dao.get(Long
						.parseLong(projectId));
				Map<String, BigDecimal> map = slFundIntentService
						.saveProjectfiance(persistent.getProjectId(),
								"SmallLoan");
				persistent.setPaychargeMoney(map.get("paychargeMoney"));
				persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
				persistent.setAccrualMoney(map.get("loanInterest"));
				persistent.setConsultationMoney(map.get("consultationMoney"));
				persistent.setServiceMoney(map.get("serviceMoney"));
				this.dao.merge(persistent);

				// String superviseResult =
				// flowRunInfo.getRequest().getParameter("superviseResult");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					vars.put("superviseResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	public boolean askForProject(Long projectId,
			List<SlActualToCharge> slActualToCharges,
			List<SlActualToCharge> slActualToChargesuperviseRecord,
			List<SlFundIntent> slFundIntents,
			List<SlFundIntent> slFundIntentsSuperviseRecord,
			SlSuperviseRecord slSuperviseRecord, String categoryIds,
			boolean isNoStart) {

		for (SlActualToCharge slActualToCharge : slActualToCharges) {
			this.slActualToChargeDao.save(slActualToCharge);
		}
		boolean isAdd = true;
		if (null != slSuperviseRecord.getId()) {
			isAdd = false;
		}
		slSuperviseRecord.setProjectId(projectId);
		Long slSuperviseRecordId = slSuperviseRecordDao.save(slSuperviseRecord)
				.getId();

		// for(SlActualToCharge t:slActualToChargesuperviseRecord){
		// t.setSlSuperviseRecordId(slSuperviseRecord.getId());
		// slActualToChargeDao.save(t);
		// }
		for (SlActualToCharge t : slActualToChargesuperviseRecord) {
			t.setSlSuperviseRecordId(slSuperviseRecord.getId());
			slActualToChargeDao.save(t);
		}
		List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentDao
				.getlistbyslSuperviseRecordId(slSuperviseRecordId, "SmallLoan",
						projectId);
		for (SlFundIntent s : slFundIntentsAllsupervise) {
			slFundIntentDao.remove(s);

		}
		for (SlFundIntent b : slFundIntentsSuperviseRecord) {
			b.setSlSuperviseRecordId(slSuperviseRecord.getId());
			slFundIntentDao.save(b);

		}
		for (SlFundIntent a : slFundIntents) {
			slFundIntentDao.save(a);
		}

		// 保存的同时更新合同分类表ProcreditContractCategory add by chencc start...
		if (!"".equals(categoryIds)) {
			String[] idArray = categoryIds.split(",");
			if (idArray.length > 0) {
				for (int i = 0; i < idArray.length; i++) {
					if (!"".equals(idArray[i]) && idArray[i] != null
							&& StringUtil.isNumeric(idArray[i])) {
						try {
							Object[] obj = { slSuperviseRecord.getId(),
									Integer.parseInt(idArray[i]) };
							creditBaseDao
									.excuteSQL(
											"update ProcreditContract set isApply=1,clauseId =? where id =?",
											obj);
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		// 保存的同时更新合同分类表ProcreditContractCategory add by chencc end...
		// SlSmallloanProject persistent=this.dao.get(projectId);
		// Map<String,BigDecimal>
		// map=slFundIntentService.saveProjectfiance(persistent.getProjectId(),"SmallLoan");
		// persistent.setPaychargeMoney(map.get("paychargeMoney"));
		// persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
		// persistent.setAccrualMoney(map.get("loanInterest"));
		// persistent.setConsultationMoney(map.get("consultationMoney"));
		// persistent.setServiceMoney(map.get("serviceMoney"));
		// this.dao.merge(persistent);
		/**
		 * 申请展期启动一个新流程
		 */
		if (slSuperviseRecord.getCheckStatus() == 0 && (!isNoStart)) {
			String customerName = "";
			FlowRunInfo newFlowRunInfo = new FlowRunInfo();
			// ProDefinition pdf =
			// proDefinitionDao.getByProcessName("smallLoanPostponedFlow");
			SlSmallloanProject project = this.dao.get(projectId);
			if (project != null) {
				Long companyId = new Long(0);
				String isGroupVersion = AppUtil.getSystemIsGroupVersion();
				if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
					companyId = project.getCompanyId();
				}
				ProDefinition pdf = proDefinitionDao.getByCompanyIdProcessName(
						companyId, "smallLoanPostponedFlow");
				if (pdf != null) {
					ProcessRun run = processRunDao.getByBusinessTypeProjectId(
							project.getProjectId(), project.getBusinessType());
					if (run != null) {
						customerName = run.getCustomerName();
					}
					Map<String, Object> newVars = new HashMap<String, Object>();
					newVars.put("projectId", project.getProjectId());
					newVars.put("slSuperviseRecordId", slSuperviseRecordId);
					newVars.put("oppositeType", project.getOppositeType());
					newVars.put("businessType", project.getBusinessType());
					newVars.put("customerName", customerName); //
					newVars.put("projectNumber", project.getProjectNumber()); //
					newFlowRunInfo.getVariables().putAll(newVars);
					newFlowRunInfo.setBusMap(newVars);
					newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
					newFlowRunInfo.setFlowSubject(project.getProjectName()
							+ "-" + project.getProjectNumber());
					this.jbpmService.doStartProcess(newFlowRunInfo);
					project.setProjectStatus(Constants.PROJECT_POSTPONED_STATUS_ACT);
					slSuperviseRecord.setCheckStatus(0);
					this.slSuperviseRecordDao.merge(slSuperviseRecord);
					this.dao.merge(project);
				}
			}
		}

		if (isNoStart) {
			SlSmallloanProject project = this.dao.get(projectId);
			project.setProjectStatus(Short.valueOf("5"));
			this.slSuperviseRecordDao.merge(slSuperviseRecord);
		}
		return true;
	}

	/**
	 * 小额贷款快速流程 - 业务主管审批
	 */
	@Override
	public Integer executiveClearCreditFlowProject(FlowRunInfo flowRunInfo) {
		if (flowRunInfo.isBack()) {
			return 1;
		} else {
			try {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm processForm = processFormDao
							.getByTaskId(flowRunInfo.getTaskId());
					if (processForm != null) {
						ProUserAssign userAssign = creditProjectService
								.getByRunIdActivityName(processForm.getRunId(),
										transitionName);
						if (userAssign != null
								&& userAssign.getTaskSequence() != null
								&& !"".equals(userAssign.getTaskSequence())) {
							if (userAssign.getTaskSequence().contains(
									"slfProjectInfo")) {// 打回到"项目信息"
								String creatorId = "1";// 默认一个值，为超级管理员。
								ProcessForm pform = processFormDao
										.getByRunIdFlowNodeKey(processForm
												.getRunId(), "slfProjectInfo");
								if (pform != null
										&& pform.getCreatorId() != null) {
									creatorId = pform.getCreatorId().toString();
									vars.put("flowAssignId", creatorId);
								}
							}
						}

						boolean isToNextTask = creditProjectService
								.compareTaskSequence(processForm.getRunId(),
										processForm.getActivityName(),
										transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
						}
					}

					vars.put("businessManagerApprove", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				/*
				 * String businessManagerApprove =
				 * flowRunInfo.getRequest().getParameter
				 * ("businessManagerApprove"); Map<String,Object> vars=new
				 * HashMap<String, Object>();
				 * if(businessManagerApprove!=null&&businessManagerApprove
				 * .equals("2")){//表示打回重做 flowRunInfo.setAfresh(true); }
				 * vars.put
				 * ("businessManagerApprove",Integer.valueOf(businessManagerApprove
				 * )); flowRunInfo.getVariables().putAll(vars);
				 */
				return 1;

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("小额贷款快速流程 - 业务主管审批 出错:" + e.getMessage());
				return 0;
			}
		}
	}

	@Override
	public Integer riskClearCreditFlowProject(FlowRunInfo flowRunInfo) {
		if (flowRunInfo.isBack()) {
			return 1;
		} else {
			try {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm processForm = processFormDao
							.getByTaskId(flowRunInfo.getTaskId());
					if (processForm != null) {
						ProUserAssign userAssign = creditProjectService
								.getByRunIdActivityName(processForm.getRunId(),
										transitionName);
						if (userAssign != null
								&& userAssign.getTaskSequence() != null
								&& !"".equals(userAssign.getTaskSequence())) {
							if (userAssign.getTaskSequence().contains(
									"slfProjectInfo")) {// 打回到"项目信息"
								String creatorId = "1";// 默认一个值，为超级管理员。
								ProcessForm pform = processFormDao
										.getByRunIdFlowNodeKey(processForm
												.getRunId(), "slfProjectInfo");
								if (pform != null
										&& pform.getCreatorId() != null) {
									creatorId = pform.getCreatorId().toString();
									vars.put("flowAssignId", creatorId);
								}
							}
						}

						boolean isToNextTask = creditProjectService
								.compareTaskSequence(processForm.getRunId(),
										processForm.getActivityName(),
										transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
						}
					}

					vars.put("riskManagerApprove", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				/*
				 * String riskManagerApprove =
				 * flowRunInfo.getRequest().getParameter("riskManagerApprove");
				 * Map<String,Object> vars=new HashMap<String, Object>();
				 * if(riskManagerApprove
				 * !=null&&riskManagerApprove.equals("2")){//表示打回重做
				 * flowRunInfo.setAfresh(true); }
				 * vars.put("riskManagerApprove",Integer
				 * .valueOf(riskManagerApprove));
				 * flowRunInfo.getVariables().putAll(vars);
				 */
				return 1;
			} catch (Exception e) {
				logger.error("小额贷款快速流程 - 风险主管审批提交下一步：" + e.getMessage());
				e.printStackTrace();
				return 0;
			}
		}
	}

	@Override
	public Integer chairmanCreditFlowProject(FlowRunInfo flowRunInfo) {
		if (flowRunInfo.isBack()) {
			return 1;
		} else {
			try {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm processForm = processFormDao
							.getByTaskId(flowRunInfo.getTaskId());
					if (processForm != null) {
						ProUserAssign userAssign = creditProjectService
								.getByRunIdActivityName(processForm.getRunId(),
										transitionName);
						if (userAssign != null
								&& userAssign.getTaskSequence() != null
								&& !"".equals(userAssign.getTaskSequence())) {
							if (userAssign.getTaskSequence().contains(
									"slfProjectInfo")) {// 打回到"项目信息"
								String creatorId = "1";// 默认一个值，为超级管理员。
								ProcessForm pform = processFormDao
										.getByRunIdFlowNodeKey(processForm
												.getRunId(), "slfProjectInfo");
								if (pform != null
										&& pform.getCreatorId() != null) {
									creatorId = pform.getCreatorId().toString();
									vars.put("flowAssignId", creatorId);
								}
							}
						}

						boolean isToNextTask = creditProjectService
								.compareTaskSequence(processForm.getRunId(),
										processForm.getActivityName(),
										transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
						}
					}

					vars.put("chairmanApprove", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				/*
				 * String chairmanApprove =
				 * flowRunInfo.getRequest().getParameter("chairmanApprove");
				 * Map<String,Object> vars=new HashMap<String, Object>();
				 * if(chairmanApprove
				 * !=null&&chairmanApprove.equals("2")){//表示打回重做
				 * flowRunInfo.setAfresh(true); }
				 * vars.put("chairmanApprove",Integer.valueOf(chairmanApprove));
				 * flowRunInfo.getVariables().putAll(vars);
				 */
				return 1;
			} catch (Exception e) {
				logger.error("小额贷款快速流程 - 董事审批提交下一步：" + e.getMessage());
				e.printStackTrace();
				return 0;
			}
		}
	}

	/**
	 * 小额贷款常规流程 - 业务主管审核
	 */
	public Integer businessDirectorAuditing(FlowRunInfo flowRunInfo) {
		if (flowRunInfo.isBack()) {
			return 1;
		} else {
			try {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm processForm = processFormDao
							.getByTaskId(flowRunInfo.getTaskId());
					if (processForm != null) {
						ProUserAssign userAssign = creditProjectService
								.getByRunIdActivityName(processForm.getRunId(),
										transitionName);
						if (userAssign != null
								&& userAssign.getTaskSequence() != null
								&& !"".equals(userAssign.getTaskSequence())) {
							if (userAssign.getTaskSequence().contains(
									"slnProjectManagerSurveyed")) {// 打回到"尽职调查"
								String creatorId = "1";// 默认一个值，为超级管理员。
								ProcessForm pform = processFormDao
										.getByRunIdFlowNodeKey(processForm
												.getRunId(),
												"slnProjectManagerSurveyed");
								if (pform != null
										&& pform.getCreatorId() != null) {
									creatorId = pform.getCreatorId().toString();
									vars.put("flowAssignId", creatorId);
								}
							}
						}

						boolean isToNextTask = creditProjectService
								.compareTaskSequence(processForm.getRunId(),
										processForm.getActivityName(),
										transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
						}
					}

					vars.put("approveResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				/*
				 * String approveResult =
				 * flowRunInfo.getRequest().getParameter("approveResult");
				 * 
				 * Map<String,Object> vars=new HashMap<String, Object>();
				 * if(approveResult!=null&&approveResult.equals("2")){//表示打回重做
				 * flowRunInfo.setAfresh(true); }
				 * vars.put("approveResult",Integer.valueOf(approveResult));
				 * flowRunInfo.getVariables().putAll(vars);
				 */
				return 1;
			} catch (Exception e) {
				logger.error("小额贷款常规流程 - 业务主管审核提交下一步：" + e.getMessage());
				e.printStackTrace();
				return 0;
			}
		}
	}

	/**
	 * 小贷通用流程--贷款审批
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer businessDirectorAuditingTY(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String taskId = flowRunInfo.getTaskId();
				Map<String, Object> vars = new HashMap<String, Object>();
				if (taskId != null && !"".equals(taskId)) {
					List<Transition> trans = jbpmService
							.getTransitionsByTaskId(taskId);
					if (trans != null && trans.size() != 0) {
						String transitionName = trans.get(0).getName();
						flowRunInfo.setTransitionName(transitionName);
					}
				}
				vars.put("fundGoIntoEffect", "true");// 会签通过的话执行款项生效。
				flowRunInfo.getVariables().putAll(vars);
				// String transitionName = flowRunInfo.getTransitionName();
				/*
				 * String creatorId = "1";//默认一个值，为超级管理员。
				 * if(transitionName!=null&&!"".equals(transitionName)){
				 * ProcessForm currentForm =
				 * processFormDao.getByTaskId(flowRunInfo.getTaskId());
				 * if(currentForm!=null){ Map<String,Object> vars=new
				 * HashMap<String, Object>(); boolean isToNextTask =
				 * creditProjectService
				 * .compareTaskSequence(currentForm.getRunId(),
				 * currentForm.getActivityName(), transitionName);
				 * if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
				 * flowRunInfo.setAfresh(true);//表示打回重做 String flowNodeKey =
				 * "slcfAcceptAndHearACase";//默认一个值
				 * 
				 * ProUserAssign userAssign =
				 * creditProjectService.getByRunIdActivityName
				 * (currentForm.getRunId(), transitionName);
				 * if(userAssign!=null&&
				 * userAssign.getTaskSequence()!=null&&!"".equals
				 * (userAssign.getTaskSequence())){
				 * if(userAssign.getTaskSequence
				 * ().contains("slcfAcceptAndHearACase")){//打回至贷款受理 flowNodeKey
				 * = "slcfAcceptAndHearACase"; } } ProcessForm processForm =
				 * processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(),
				 * flowNodeKey);
				 * if(processForm!=null&&processForm.getCreatorId()!=null){
				 * creatorId = processForm.getCreatorId().toString();
				 * vars.put("flowAssignId",creatorId); } }
				 * vars.put("loanExamineResult",transitionName);
				 * flowRunInfo.getVariables().putAll(vars); } }
				 */
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷款审批提交任务报错：" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer updateintnet(SlSmallloanProject slSmallloanProject,
			List<SlFundIntent> slFundIntents,
			List<SlActualToCharge> slActualToCharges) {
		List<SlFundIntent> allintent = this.slFundIntentDao.getByProjectId1(
				slSmallloanProject.getProjectId(), "SmallLoan");
		for (SlFundIntent s : allintent) {
			slFundIntentDao.evict(s);
			if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
				this.slFundIntentDao.remove(s);
			}
		}
		for (SlFundIntent a : slFundIntents) {
			if (a.getFundType().equals("principalLending")) {
				a.setIsCheck(Short.valueOf("0"));
			}
			this.slFundIntentDao.save(a);
			// slFundIntentDao.evict(a);

		}
		for (SlActualToCharge l : slActualToCharges) {
			this.slActualToChargeDao.save(l);

		}
		SlSmallloanProject persistent = this.dao.get(slSmallloanProject
				.getProjectId());
		slSmallloanProject.setStates(persistent.getStates());
		BeanUtils.copyProperties(slSmallloanProject, persistent, new String[] {
				"id", "operationType", "flowType", "mineType", "mineId",
				"oppositeType", "oppositeID", "projectName", "projectNumber",
				"oppositeType", "businessType", "createDate", "appUserId",
				"recommendUserId" });
		Map<String, BigDecimal> map = slFundIntentService.saveProjectfiance(
				slSmallloanProject.getProjectId(), "SmallLoan");
		persistent.setPaychargeMoney(map.get("paychargeMoney"));
		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
		persistent.setAccrualMoney(map.get("loanInterest"));
		persistent.setConsultationMoney(map.get("consultationMoney"));
		persistent.setServiceMoney(map.get("serviceMoney"));
		this.dao.save(persistent);

		return null;
	}

	@Override
	public Integer updateintnetProjectDetail(
			SlSmallloanProject slSmallloanProject,
			List<SlFundIntent> slFundIntents,
			List<SlActualToCharge> slActualToCharges) {

		for (SlFundIntent a : slFundIntents) {
			slFundIntentDao.evict(a);
			this.slFundIntentDao.save(a);

		}
		for (SlActualToCharge l : slActualToCharges) {
			this.slActualToChargeDao.save(l);

		}
		SlSmallloanProject persistent = this.dao.get(slSmallloanProject
				.getProjectId());
		BeanUtils.copyProperties(slSmallloanProject, persistent, new String[] {
				"id", "operationType", "flowType", "mineType", "mineId",
				"oppositeType", "oppositeID", "projectName", "projectNumber",
				"oppositeType", "businessType", "createDate", "appUserId",
				"recommendUserId" });

		Map<String, BigDecimal> map = slFundIntentService.saveProjectfiance(
				slSmallloanProject.getProjectId(), "SmallLoan");
		persistent.setPaychargeMoney(map.get("paychargeMoney"));
		persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
		persistent.setAccrualMoney(map.get("loanInterest"));
		persistent.setConsultationMoney(map.get("consultationMoney"));
		persistent.setServiceMoney(map.get("serviceMoney"));
		this.dao.save(persistent);

		return null;
	}

	/**
	 * 小贷常规、小额快速-放款及款项计划确认提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer updateSmallLoanIntentaffirmNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String isAheadPay = flowRunInfo.getRequest().getParameter(
						"isAheadPay");
				String degree = flowRunInfo.getRequest().getParameter("degree");
				// String isPreposePayAccrual =
				// flowRunInfo.getRequest().getParameter("isPreposePayAccrualCheck");

				SlSmallloanProject project = new SlSmallloanProject();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
						"slSmallloanProject");
				SlSmallloanProject persistent = this.dao.get(project
						.getProjectId());
				project.setStates(persistent.getStates());
				BeanUtils.copyProperties(project, persistent, new String[] {
						"id", "operationType", "flowType", "mineType",
						"mineId", "oppositeType", "oppositeID", "projectName",
						"projectNumber", "oppositeType", "businessType",
						"createDate", "appUserId", "recommendUserId" });

				if (null != degree && !"".equals(degree)) {
					project.setAppUserId(degree);
				}
				if (isAheadPay != null) {
					persistent.setIsAheadPay(Short.valueOf("1"));
				} else {
					persistent.setIsAheadPay(Short.valueOf("0"));
				}

				/*
				 * if(isPreposePayAccrual!=null){
				 * 
				 * persistent.setIsPreposePayAccrual(1); } else {
				 * persistent.setIsPreposePayAccrual(0);
				 * 
				 * }
				 */

				// 款项计划
				String slFundIentJson = flowRunInfo.getRequest().getParameter(
						"fundIntentJsonData");
				String maxintentDate = slFundIntentService.savejson(
						slFundIentJson, persistent.getProjectId(), persistent
								.getBusinessType(), Short.parseShort("0"),
						persistent.getCompanyId());
				// if(!persistent.getAccrualtype().equals("singleInterest")){
				// persistent.setPayintentPeriod(Integer.valueOf(sumintent));
				// }
				StatsPro statsPro = new StatsPro();
				statsPro.calcuProIntentDate(persistent);
				// 费用收支
				String slActualToChargeJson = flowRunInfo.getRequest()
						.getParameter("slActualToChargeJsonData");
				slActualToChargeService.savejson(slActualToChargeJson,
						persistent.getProjectId(),
						persistent.getBusinessType(), Short.parseShort("0"),
						persistent.getCompanyId());
				Map<String, BigDecimal> map = slFundIntentService
						.saveProjectfiance(persistent.getProjectId(),
								persistent.getBusinessType());
				persistent.setPaychargeMoney(map.get("paychargeMoney"));
				persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
				persistent.setAccrualMoney(map.get("loanInterest"));
				persistent.setConsultationMoney(map.get("consultationMoney"));
				persistent.setServiceMoney(map.get("serviceMoney"));
				this.dao.merge(persistent);
				// 用于分支跳转的流程代码
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					// 注释以下代码原因：在jbpmServiceImpl的方法completeTask中会自动设置项目进入保中、贷中状态。只要下一个任务的key的数字大于等于2000。
					// 如果修改了下一个节点不是进入保中，则需要修改key中的数值。
					/*
					 * if (transitionName.equals("提交归档材料")) { String
					 * projectInMiddle =
					 * flowRunInfo.getRequest().getParameter("projectInMiddle");
					 * if (projectInMiddle != null &&
					 * !"".equals(projectInMiddle)) { SlSmallloanProject
					 * persistent1 = this.dao.get(project.getProjectId());
					 * persistent1
					 * .setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
					 * this.dao.merge(persistent1); } }
					 */
					Map<String, Object> vars = new HashMap<String, Object>();
					String smallLoanFastSDH = flowRunInfo.getRequest()
							.getParameter("smallLoanFastSDH");
					if (smallLoanFastSDH != null
							&& !"".equals(smallLoanFastSDH)) {
						vars.put("slfConfirmFundPlanResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					} else {
						vars.put("slnConfirmFundPlanResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}
			}

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 微贷放款及款项计划确认节点
	 */
	@Override
	public Integer updateintentaffirm(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String isAheadPay = flowRunInfo.getRequest().getParameter(
						"isAheadPay");
				SlSmallloanProject project = new SlSmallloanProject();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
						"slSmallloanProject");
				SlSmallloanProject persistent = this.dao.get(project
						.getProjectId());
				project.setStates(persistent.getStates());
				project.setIsOtherFlow(persistent.getIsOtherFlow());
				BeanUtils.copyProperties(project, persistent, new String[] {
						"id", "operationType", "flowType", "mineType",
						"mineId", "oppositeType", "oppositeID", "projectName",
						"projectNumber", "oppositeType", "businessType",
						"createDate", "appUserId", "recommendUserId" });
				String degree = flowRunInfo.getRequest().getParameter("degree");
				String isPreposePayAccrual = flowRunInfo.getRequest()
						.getParameter("isPreposePayAccrualCheck");
				if (null != degree && !"".equals(degree)) {
					project.setAppUserId(degree);
				}
				if (isAheadPay != null) {
					persistent.setIsAheadPay(Short.valueOf("1"));
				} else {
					persistent.setIsAheadPay(Short.valueOf("0"));
				}

				/*
				 * if(isPreposePayAccrual!=null){
				 * 
				 * persistent.setIsPreposePayAccrual(1); } else {
				 * persistent.setIsPreposePayAccrual(0);
				 * 
				 * }
				 */

				// 款项计划
				String slFundIentJson = flowRunInfo.getRequest().getParameter(
						"fundIntentJsonData");
				String maxintentDate = slFundIntentService.savejson(
						slFundIentJson, persistent.getProjectId(), "SmallLoan",
						Short.parseShort("0"), persistent.getCompanyId());
				// if(!persistent.getAccrualtype().equals("singleInterest")){
				// persistent.setPayintentPeriod(Integer.valueOf(sumintent));
				// }
				StatsPro statsPro = new StatsPro();
				statsPro.calcuProIntentDate(persistent);
				// 费用收支
				String slActualToChargeJson = flowRunInfo.getRequest()
						.getParameter("slActualToChargeData");
				slActualToChargeService.savejson(slActualToChargeJson,
						persistent.getProjectId(), "SmallLoan", Short
								.parseShort("0"), persistent.getCompanyId());
				Map<String, BigDecimal> map = slFundIntentService
						.saveProjectfiance(persistent.getProjectId(),
								"SmallLoan");
				persistent.setPaychargeMoney(map.get("paychargeMoney"));
				persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
				persistent.setAccrualMoney(map.get("loanInterest"));
				persistent.setConsultationMoney(map.get("consultationMoney"));
				persistent.setServiceMoney(map.get("serviceMoney"));
				this.dao.merge(persistent);
				// 用于分支跳转的流程代码
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					if (transitionName.equals("提交归档材料")) {
						String projectInMiddle = flowRunInfo.getRequest()
								.getParameter("projectInMiddle");
						if (projectInMiddle != null
								&& !"".equals(projectInMiddle)) {
							SlSmallloanProject persistent1 = this.dao
									.get(project.getProjectId());
							persistent1
									.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
							this.dao.merge(persistent1);
						}
					}
					Map<String, Object> vars = new HashMap<String, Object>();
					String smallLoanFastSDH = flowRunInfo.getRequest()
							.getParameter("smallLoanFastSDH");
					if (smallLoanFastSDH != null
							&& !"".equals(smallLoanFastSDH)) {
						vars.put("mfPlaceOnFileResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					} else {
						vars.put("confirmFundPlanResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}

				}
			}

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer superviseCheckCreditFlowProject(FlowRunInfo flowRunInfo) {

		try {
			String superviseCheck = flowRunInfo.getRequest().getParameter(
					"superviseCheck");
			Long slSuperviseRecordId = Long.parseLong(flowRunInfo.getRequest()
					.getParameter("slSuperviseRecordId"));
			Long projectId = Long.parseLong(flowRunInfo.getRequest()
					.getParameter("projectId_flow"));
			SlSuperviseRecord sup = this.slSuperviseRecordDao
					.get(slSuperviseRecordId);
			SlSmallloanProject project = this.dao.get(projectId);
			project.setProjectStatus(Short.valueOf(superviseCheck));
			this.dao.merge(project);
			sup.setCheckStatus(Integer.valueOf(superviseCheck));
			this.slSuperviseRecordDao.merge(sup);
			if (superviseCheck.equals("5")) {
				String businessType = flowRunInfo.getRequest().getParameter(
						"businessType_flow");
				List<SlFundIntent> rawlist = slFundIntentService
						.getByProjectId(projectId, businessType);
				Short b = 1;

				for (SlFundIntent r : rawlist) {
					int flag1 = 0; // incomeMoney
					if (r.getPayMoney().compareTo(new BigDecimal(0)) != 0) { // payMoney
						flag1 = 1;
					}
					if ((flag1 == 1 && r.getPayMoney().compareTo(
							r.getAfterMoney()) == 0)
							|| (flag1 == 0 && r.getIncomeMoney().compareTo(
									r.getAfterMoney()) == 0)) {

					} else {
						r.setIsValid(b);
						slFundIntentService.save(r);

					}
				}

				List<SlFundIntent> list1 = slFundIntentService
						.getlistbyslSuperviseRecordId(slSuperviseRecordId,
								businessType, projectId);
				List<SlActualToCharge> list2 = slActualToChargeService
						.getlistbyslSuperviseRecordId(slSuperviseRecordId,
								businessType, projectId);
				Short a = 0;
				for (SlFundIntent l : list1) {
					l.setIsValid(a);
					l.setIsCheck(a);
					slFundIntentService.save(l);
				}
				for (SlActualToCharge l : list2) {
					l.setIsCheck(a);
					slActualToChargeService.save(l);
				}
				Map<String, BigDecimal> map = slFundIntentService
						.saveProjectfiance(projectId, "SmallLoan");
				SlSmallloanProject persistent = this.dao.get(projectId);
				persistent.setPaychargeMoney(map.get("paychargeMoney"));
				persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
				persistent.setAccrualMoney(map.get("loanInterest"));
				persistent.setConsultationMoney(map.get("consultationMoney"));
				persistent.setServiceMoney(map.get("serviceMoney"));
				this.dao.merge(persistent);
			}

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小额贷款展期流程 - 展期审查  出错:" + e.getMessage());
			return 0;
		}
	}

	public Integer changeStatusCreditFlowProject(FlowRunInfo flowRunInfo) {
		try {
			Long id = Long.valueOf(flowRunInfo.getRequest().getParameter(
					"superviseManageId").toString());
	
			GlobalSupervisemanage globalSupervisemanage = new GlobalSupervisemanage();
	    	BeanUtil.populateEntity(flowRunInfo.getRequest(), globalSupervisemanage, "globalSupervisemanage");
	    	GlobalSupervisemanage orgGlobalSupervisemanage=globalSupervisemanageDao.get(globalSupervisemanage.getSuperviseManageId());
	    	BeanUtil.copyNotNullProperties(orgGlobalSupervisemanage, globalSupervisemanage);
	    	orgGlobalSupervisemanage.setSuperviseManageStatus(Short.valueOf("1"));
	    	orgGlobalSupervisemanage.setIsProduceTask(true);
	    	globalSupervisemanageDao.save(orgGlobalSupervisemanage);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("小额贷款监管流程 - 贷后监管  出错:" + ex.getMessage());
			return 0;
		}

	}

	/**
	 * 审贷会提交下一步
	 */
	public Integer sdhExaminationArrangement(FlowRunInfo flowRunInfo) {
		try {
			List<Transition> trans = jbpmService
					.getTransitionsByTaskId(flowRunInfo.getTaskId());
			if (trans != null && trans.size() != 0) {
				String transitionName = trans.get(0).getName();
				flowRunInfo.setTransitionName(transitionName);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小额贷款审贷会决议提交下一步出错：" + e.getMessage());
			return 0;
		}
	}

	public Integer analysisCreditFlowProjectTY(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String creatorId = "1";// 默认一个值，为超级管理员。
				if (transitionName != null && !"".equals(transitionName)) {
					ProcessForm currentForm = processFormDao
							.getByTaskId(flowRunInfo.getTaskId());
					if (currentForm != null) {
						Map<String, Object> vars = new HashMap<String, Object>();
						boolean isToNextTask = creditProjectService
								.compareTaskSequence(currentForm.getRunId(),
										currentForm.getActivityName(),
										transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							String flowNodeKey = "slcfAcceptAndHearACase";// 默认一个值

							ProUserAssign userAssign = creditProjectService
									.getByRunIdActivityName(currentForm
											.getRunId(), transitionName);
							if (userAssign != null
									&& userAssign.getTaskSequence() != null
									&& !"".equals(userAssign.getTaskSequence())) {
								if (userAssign.getTaskSequence().contains(
										"slcfAcceptAndHearACase")) {// 打回至贷款受理
									flowNodeKey = "slcfAcceptAndHearACase";
								}
							}
							ProcessForm processForm = processFormDao
									.getByRunIdFlowNodeKey(currentForm
											.getRunId(), flowNodeKey);
							if (processForm != null
									&& processForm.getCreatorId() != null) {
								creatorId = processForm.getCreatorId()
										.toString();
								vars.put("flowAssignId", creatorId);
							}
						}
						vars.put("riskDepExamineResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("风险审核提交任务报错：" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer loanFaFangTY(FlowRunInfo flowRunInfo) {

		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {

				String projectInMiddle = flowRunInfo.getRequest().getParameter(
						"projectInMiddle");
				String flowOver = flowRunInfo.getRequest().getParameter(
						"flowOver");
				if (null != flowOver) {
					String projectId = flowRunInfo.getRequest().getParameter(
							"slSmallloanProject.projectId");
					if (null != projectId) {
						SlSmallloanProject sl = dao.get(new Long(projectId));
						if (null != sl) {
							sl.setEndDate(DateUtil
									.parseDate(DateUtil
											.getNowDateTime("yyyy-MM-dd"),
											"yyyy-MM-dd"));
							dao.merge(sl);
						}

					}
				}
				if (null != projectInMiddle) {
					String projectId = flowRunInfo.getRequest().getParameter(
							"slSmallloanProject.projectId");
					if (null != projectId) {
						/*
						 * SlSmallloanProject sl = dao.get(new Long(projectId));
						 * if(null!=sl){
						 */
						String isAheadPay = flowRunInfo.getRequest()
								.getParameter("isAheadPay");
						String isPreposePayAccrual = flowRunInfo.getRequest()
								.getParameter("isPreposePayAccrualCheck");
						String degree = flowRunInfo.getRequest().getParameter(
								"degree");
						SlSmallloanProject project = new SlSmallloanProject();
						BeanUtil.populateEntity(flowRunInfo.getRequest(),
								project, "slSmallloanProject");
						if (null != degree && !"".equals(degree)) {
							project.setAppUserId(degree);
						}
						SlSmallloanProject sl = this.dao.get(project
								.getProjectId());
						// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
						project.setCompanyId(sl.getCompanyId());
						// end
						project
								.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
						BeanUtils.copyProperties(project, sl, new String[] {
								"id", "operationType", "flowType", "mineType",
								"mineId", "oppositeType", "oppositeID",
								"projectName", "projectNumber", "oppositeType",
								"businessType", "createDate" });
						if (isAheadPay != null) {
							sl.setIsAheadPay(Short.valueOf("1"));
						} else {
							sl.setIsAheadPay(Short.valueOf("0"));
						}

						/*
						 * if(isPreposePayAccrual!=null){
						 * 
						 * sl.setIsPreposePayAccrual(1); } else {
						 * sl.setIsPreposePayAccrual(0);
						 * 
						 * }
						 */

						// 款项计划
						String slFundIentJson = flowRunInfo.getRequest()
								.getParameter("fundIntentJsonData");
						String maxintentDate = slFundIntentService.savejson(
								slFundIentJson, Long.valueOf(projectId),
								"SmallLoan", Short.parseShort("0"), sl
										.getCompanyId());
						// if(!sl.getAccrualtype().equals("singleInterest")){
						// sl.setPayintentPeriod(Integer.valueOf(sumintent));
						// }
						StatsPro statsPro = new StatsPro();
						statsPro.calcuProIntentDate(sl);
						// 费用收支

						String slActualToChargeJson = flowRunInfo.getRequest()
								.getParameter("slActualToChargeJson");
						slActualToChargeService.savejson(slActualToChargeJson,
								Long.valueOf(projectId), "SmallLoan", Short
										.parseShort("0"), sl.getCompanyId());
						Map<String, BigDecimal> map = slFundIntentService
								.saveProjectfiance(sl.getProjectId(),
										"SmallLoan");
						sl.setPaychargeMoney(map.get("paychargeMoney"));
						sl.setIncomechargeMoney(map.get("incomechargeMoney"));
						sl.setAccrualMoney(map.get("loanInterest"));
						sl.setConsultationMoney(map.get("consultationMoney"));
						sl.setServiceMoney(map.get("serviceMoney"));
						/**
						 * 年化净利率
						 */
						ProjectActionUtil pu = new ProjectActionUtil();
						pu.getSmallloanMode(sl);
						dao.merge(sl);
						// }
					}
				}
				String transitionName = flowRunInfo.getTransitionName();
				String creatorId = "1";// 默认一个值，为超级管理员。
				if (transitionName != null && !"".equals(transitionName)) {
					ProcessForm currentForm = processFormDao
							.getByTaskId(flowRunInfo.getTaskId());
					if (currentForm != null) {
						Map<String, Object> vars = new HashMap<String, Object>();
						vars.put("loanProvideResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷款发放提交任务报错：" + e.getMessage());
			return 0;
		}

	}

	public Integer loanJieAnTY(FlowRunInfo flowRunInfo) {

		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectInMiddle = flowRunInfo.getRequest().getParameter(
						"projectInMiddle");
				String flowOver = flowRunInfo.getRequest().getParameter(
						"flowOver");
				if (null != flowOver) {
					String projectId = flowRunInfo.getRequest().getParameter(
							"slSmallloanProject.projectId");
					if (null != projectId) {
						List pmlist = mortgageService
								.getByBusinessTypeProjectId("SmallLoan", Long
										.parseLong(projectId));
						if (pmlist != null && pmlist.size() != 0) {
							for (int k = 0; k < pmlist.size(); k++) {
								ProcreditMortgage procreditMortgage = (ProcreditMortgage) pmlist
										.get(k);
								if (null != procreditMortgage.getIsTransact()
										&& procreditMortgage.getIsTransact() == true
										&& (null == procreditMortgage
												.getIsunchain() || procreditMortgage
												.getIsunchain() == false)) {
									procreditMortgage
											.setMortgageStatus("xmwcwjc");
								}
								mortgageService.updateMortgage(
										procreditMortgage.getId(),
										procreditMortgage);
							}
						}
						SlSmallloanProject sl = dao.get(new Long(projectId));
						if (null != sl) {
							sl.setEndDate(DateUtil
									.parseDate(DateUtil
											.getNowDateTime("yyyy-MM-dd"),
											"yyyy-MM-dd"));
							dao.merge(sl);
						}
					}
				}
				if (null != projectInMiddle) {
					String projectId = flowRunInfo.getRequest().getParameter(
							"slSmallloanProject.projectId");
					if (null != projectId) {
						List pmlist = mortgageService
								.getByBusinessTypeProjectId("SmallLoan", Long
										.parseLong(projectId));
						if (pmlist != null && pmlist.size() != 0) {
							for (int k = 0; k < pmlist.size(); k++) {
								ProcreditMortgage procreditMortgage = (ProcreditMortgage) pmlist
										.get(k);
								if (null != procreditMortgage.getIsTransact()
										&& procreditMortgage.getIsTransact() == true
										&& (null == procreditMortgage
												.getIsunchain() || procreditMortgage
												.getIsunchain() == false)) {
									procreditMortgage
											.setMortgageStatus("xmwcwjc");
								}
								mortgageService.updateMortgage(
										procreditMortgage.getId(),
										procreditMortgage);
							}
						}
						SlSmallloanProject sl = dao.get(new Long(projectId));
						if (null != sl) {
							dao.merge(sl);
						}
					}
				}
				String transitionName = flowRunInfo.getTransitionName();
				String creatorId = "1";// 默认一个值，为超级管理员。
				if (transitionName != null && !"".equals(transitionName)) {
					ProcessForm currentForm = processFormDao
							.getByTaskId(flowRunInfo.getTaskId());
					if (currentForm != null) {
						Map<String, Object> vars = new HashMap<String, Object>();
						vars.put("loanWindUpACaseResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}
				String projectId = flowRunInfo.getRequest().getParameter(
						"slSmallloanProject.projectId");
				PlProjectArchives ppa = new PlProjectArchives();
				ppa.setProjectId(Long.valueOf(projectId));
				ppa.setIsArchives(Short.valueOf("1"));
				ppa.setArchivesTime(new Date());
				ppa.setRemark(flowRunInfo.getComments());
				ppa.setBusinessType("SmallLoan");
				plProjectArchivesDao.save(ppa);
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷款结案提交任务报错：" + e.getMessage());
			return 0;
		}

	}

	@Override
	public boolean askForEarlyRepaymentProject(Long projectId,
			List<SlActualToCharge> slActualToCharges,
			List<SlActualToCharge> slActualToChargesuperviseRecord,
			List<SlFundIntent> slFundIntents,
			List<SlFundIntent> slFundIntentsSuperviseRecord,
			SlEarlyRepaymentRecord slEarlyRepaymentRecord, String contractids) {

		slEarlyRepaymentRecord.setProjectId(projectId);
		Long slEarlyRepaymentId = slEarlyRepaymentRecordDao.save(
				slEarlyRepaymentRecord).getSlEarlyRepaymentId();
		SlSmallloanProject slSmallloanProject = dao.get(projectId);
		/*
		 * DateFormat sd=new SimpleDateFormat("yyyy-MM-dd"); String date="";
		 * if(slSmallloanProject.getIsStartDatePay().equals("1")){ Date
		 * dt=DateUtil.addMonthsToDate(slSmallloanProject.getStartDate(),
		 * slEarlyRepaymentRecord.getPrepayintentPeriod()); date=sd.format(dt);
		 * date=date.substring(0,date.lastIndexOf("-")+1)+slSmallloanProject.
		 * getPayintentPerioDate(); }else{ Date
		 * dt=DateUtil.addDaysToDate(DateUtil
		 * .addMonthsToDate(slSmallloanProject.getStartDate(),
		 * slEarlyRepaymentRecord.getPrepayintentPeriod()), -1);
		 * date=sd.format(dt); } List<SlFundIntent>
		 * slist=slFundIntentService.getListByIntentDate
		 * (slSmallloanProject.getProjectId(),
		 * slSmallloanProject.getBusinessType(), ">='"+date); for(SlFundIntent
		 * s:slist){ if(null==s.getSlEarlyRepaymentId() ||
		 * s.getSlAlteraccrualRecordId()!=slEarlyRepaymentId){
		 * s.setIsValid(Short.valueOf("1")); s.setIsCheck(Short.valueOf("1"));
		 * slFundIntentService.save(s); }
		 * 
		 * }
		 */
		// List<SlFundIntent> rawlist = slFundIntentService.getByProjectId(
		// projectId, "SmallLoan");
		// Short c = 1;
		// for (SlFundIntent r : rawlist) {
		// int flag1 = 0; // incomeMoney
		// if (r.getPayMoney().compareTo(new BigDecimal(0)) != 0) { // payMoney
		// flag1 = 1;
		// }
		// if ((flag1 == 1)
		// || (flag1 == 0 && r.getIncomeMoney().compareTo(
		// r.getAfterMoney()) == 0)) {
		//
		// } else {
		// r.setIsValid(c);
		// slFundIntentService.save(r);
		//
		// }
		// }

		for (SlFundIntent b : slFundIntentsSuperviseRecord) {
			b.setSlEarlyRepaymentId(slEarlyRepaymentId);
			slFundIntentDao.save(b);

		}

		// if (!"".equals(contractids)) {
		// String[] idArray = contractids.split(",");
		// if (idArray.length > 0) {
		// for (int i = 0; i < idArray.length; i++) {
		// if (!"".equals(idArray[i]) && idArray[i] != null
		// && StringUtil.isNumeric(idArray[i])) {
		// try {
		// Object[] obj = {
		// "sl_earlyrepayment_record."
		// + slEarlyRepaymentId,
		// "sl_earlyrepayment_record." + projectId,
		// Integer.parseInt(idArray[i]) };
		// creditBaseDao
		// .excuteSQL(
		// "update FileForm set remark=?,mark =? where id =?",
		// obj);
		// } catch (NumberFormatException e) {
		// e.printStackTrace();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }
		// }

		/**
		 * 申请提前还款 启动一个新流程 先判断当前项目是否存在正在运行的提前还款流程实例
		 */
		SlSmallloanProject project = this.dao.get(projectId);
		if (project != null) {
			// List<ProcessRun> runList =
			// processRunDao.getByProcessNameProjectId(projectId,
			// project.getBusinessType(),"repaymentAheadOfTimeFlow");
			// if(runList!=null&&runList.size()!=0){
			// for(ProcessRun processRun:runList){
			// if(processRun.getPiId()!=null){
			// return false;
			// }
			// }
			// }else{
			FlowRunInfo newFlowRunInfo = new FlowRunInfo();
			ProDefinition pdf = proDefinitionDao.getByCompanyIdProcessName(
					project.getCompanyId(), "repaymentAheadOfTimeFlow");
			if (pdf != null) {
				String customerName = "customerNameIsNull";
				ProcessRun run = processRunDao.getByBusinessTypeProjectId(
						projectId, project.getBusinessType());
				if (run != null) {
					customerName = run.getCustomerName();
				}
				// Map<String,String>
				// mapNew=this.getProjectInfo(project,"alterAccrualFlow");
				Map<String, Object> newVars = new HashMap<String, Object>();
				newVars.put("projectId", project.getProjectId());
				newVars.put("slEarlyRepaymentId", slEarlyRepaymentId);
				newVars.put("oppositeType", project.getOppositeType());
				newVars.put("businessType", project.getBusinessType());
				newVars.put("customerName", customerName); //
				newVars.put("projectNumber", project.getProjectNumber()); //
				newVars.put("operationType", project.getOperationType()); //

				List<VFundDetail> list = vFundDetailService.listByFundType(
						"principalRepayment", projectId, "SmallLoan");
				BigDecimal payMoney = new BigDecimal(0);
				for (VFundDetail VFundDetail : list) {
					payMoney = payMoney.add(new BigDecimal(VFundDetail
							.getAfterMoney()));
				}
				List<SlFundIntent> slist = slFundIntentService
						.getlistbyslEarlyRepaymentId(slEarlyRepaymentId,
								"SmallLoan", projectId);
				Long slSuperviseRecordId = new Long(0);
				if (null != slist && slist.size() > 0) {
					SlFundIntent sf = slist.get(0);
					if (null != sf.getSlSuperviseRecordId()) {
						slSuperviseRecordId = sf.getSlSuperviseRecordId();
					}
				}
				newVars.put("payintentPeriod", slSuperviseRecordId);
				newVars.put("surplusnotMoney", slSmallloanProject
						.getProjectMoney().subtract(payMoney));
				newFlowRunInfo.getVariables().putAll(newVars);
				newFlowRunInfo.setBusMap(newVars);
				newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
				// newFlowRunInfo.setFlowSubject(mapNew.get("projectName").toString()+"-"+project.getProjectNumber());
				this.jbpmService.doStartProcess(newFlowRunInfo);
				slEarlyRepaymentRecord.setCheckStatus(0);
				this.slEarlyRepaymentRecordDao.merge(slEarlyRepaymentRecord);
				project.setIsOtherFlow(Short.valueOf("2"));// 启动提前还款流程后将项目表中isOtherFlow状态改为2
				this.dao.merge(project);
			} else {
				return false;
			}

			// }
		}
		return true;
	}

	@Override
	public boolean askForAlterAccrualProject(Long projectId,
			List<SlActualToCharge> slActualToCharges,
			List<SlActualToCharge> slActualToChargesuperviseRecord,
			List<SlFundIntent> slFundIntents,
			List<SlFundIntent> slFundIntentsSuperviseRecord,
			SlAlterAccrualRecord slAlterAccrualRecord, String contractids) {

		// for(SlActualToCharge slActualToCharge:slActualToCharges){
		// this.slActualToChargeDao.save(slActualToCharge);
		// }
		// for(SlFundIntent a:slFundIntents){
		// slFundIntentDao.save(a);
		// }
		// if(!"".equals(contractids)){
		// String [] idArray = contractids.split(",");
		// if(idArray.length >0){
		// for(int i=0;i<idArray.length;i++ ){
		// if(!"".equals(idArray[i])&& idArray[i]!=null &&
		// StringUtil.isNumeric(idArray[i])){
		// try {
		// Object [] obj =
		// {"sl_alteraccrual_record."+slAlteraccrualRecordId,"sl_alteraccrual_record."+projectId,Integer.parseInt(idArray[i])};
		// creditBaseDao.excuteSQL("update FileForm set remark=?,mark =? where id =?",obj);
		// } catch (NumberFormatException e) {
		// e.printStackTrace();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }
		// }

		/**
		 * 申请利率变更 启动一个新流程 先判断当前项目是否存在正在运行的利率变更流程实例
		 */
		SlSmallloanProject project = this.dao.get(projectId);
		if (project != null) {
			List<ProcessRun> runList = processRunDao.getByProcessNameProjectId(
					projectId, project.getBusinessType(), "alterAccrualFlow");
			if (runList != null && runList.size() != 0) {
				for (ProcessRun processRun : runList) {
					if (processRun.getPiId() != null) {
						return false;
					}
				}
			} else {

				slAlterAccrualRecord.setProjectId(projectId);
				slAlterAccrualRecord.setOpTime(new Date());
				Long slAlteraccrualRecordId = slAlterAccrualRecordDao.save(
						slAlterAccrualRecord).getSlAlteraccrualRecordId();

				List<SlFundIntent> rawlist = slFundIntentService
						.getByProjectId(projectId, "SmallLoan");
				Short c = 1;

				List<SlFundIntent> allintent = this.slFundIntentDao
						.getlistbyslslAlteraccrualRecordId(
								slAlteraccrualRecordId, "SmallLoan", projectId);
				for (SlFundIntent s : allintent) {
					slFundIntentDao.evict(s);
					if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						this.slFundIntentDao.remove(s);
					}
				}

				for (SlFundIntent b : slFundIntentsSuperviseRecord) {
					b.setIsValid(Short.valueOf("3"));
					b.setSlAlteraccrualRecordId(slAlteraccrualRecordId);
					slFundIntentDao.save(b);

				}

				FlowRunInfo newFlowRunInfo = new FlowRunInfo();
				ProDefinition pdf = proDefinitionDao.getByCompanyIdProcessName(
						project.getCompanyId(), "alterAccrualFlow");
				if (pdf != null) {
					String customerName = "customerNameIsNull";
					ProcessRun run = processRunDao.getByBusinessTypeProjectId(
							projectId, project.getBusinessType());
					if (run != null) {
						customerName = run.getCustomerName();
					}
					// Map<String,String>
					// mapNew=this.getProjectInfo(project,"alterAccrualFlow");
					Map<String, Object> newVars = new HashMap<String, Object>();
					newVars.put("projectId", project.getProjectId());
					newVars.put("slAlteraccrualRecordId",
							slAlteraccrualRecordId);
					newVars.put("oppositeType", project.getOppositeType());
					newVars.put("businessType", project.getBusinessType());
					newVars.put("customerName", customerName); //
					newVars.put("projectNumber", project.getProjectNumber()); //
					newVars.put("operationType", project.getOperationType()); //
					newFlowRunInfo.getVariables().putAll(newVars);
					newFlowRunInfo.setBusMap(newVars);
					newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
					// newFlowRunInfo.setFlowSubject(mapNew.get("projectName").toString()+"-"+project.getProjectNumber());
					this.jbpmService.doStartProcess(newFlowRunInfo);
					slAlterAccrualRecord.setCheckStatus(0);
					this.slAlterAccrualRecordDao.merge(slAlterAccrualRecord);
					project.setIsOtherFlow(Short.valueOf("3"));// 启动利率变更流程时将项目表的isOtherFlow字段值设为3
					// ，不在允许其他贷后流程启动
					this.dao.merge(project);
				} else {
					return false;
				}

			}
		}
		return true;
	}

	@Override
	public boolean askForAlterAccrualProjectFlow(Long projectId,
			List<SlActualToCharge> slActualToCharges,
			List<SlActualToCharge> slActualToChargesuperviseRecord,
			List<SlFundIntent> slFundIntents,
			List<SlFundIntent> slFundIntentsSuperviseRecord,
			SlAlterAccrualRecord slAlterAccrualRecord, String contractids) {
		slAlterAccrualRecord.setProjectId(projectId);
		Long slAlteraccrualRecordId = slAlterAccrualRecordDao.save(
				slAlterAccrualRecord).getSlAlteraccrualRecordId();

		List<SlFundIntent> allintent = this.slFundIntentDao
				.getlistbyslslAlteraccrualRecordId(slAlteraccrualRecordId,
						"SmallLoan", projectId);
		for (SlFundIntent s : allintent) {
			slFundIntentDao.evict(s);
			if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
				this.slFundIntentDao.remove(s);
			}
		}

		for (SlFundIntent b : slFundIntentsSuperviseRecord) {
			b.setIsValid(Short.valueOf("3"));
			b.setSlAlteraccrualRecordId(slAlteraccrualRecordId);
			slFundIntentDao.save(b);

		}

		return true;
	}

	@Override
	public boolean askForEarlyRepaymentProjectFlow(Long projectId,
			List<SlActualToCharge> slActualToCharges,
			List<SlActualToCharge> slActualToChargesuperviseRecord,
			List<SlFundIntent> slFundIntents,
			List<SlFundIntent> slFundIntentsSuperviseRecord,
			SlEarlyRepaymentRecord slEarlyRepaymentRecord, String contractids) {
		slEarlyRepaymentRecord.setProjectId(projectId);
		Long slEarlyRepaymentId = slEarlyRepaymentRecord
				.getSlEarlyRepaymentId();
		SlEarlyRepaymentRecord orgSlEarlyRepaymentRecord = slEarlyRepaymentRecordDao
				.get(slEarlyRepaymentId);
		orgSlEarlyRepaymentRecord.setEarlyProjectMoney(slEarlyRepaymentRecord
				.getEarlyProjectMoney());
		orgSlEarlyRepaymentRecord.setPrepayintentPeriod(slEarlyRepaymentRecord
				.getPrepayintentPeriod());
		orgSlEarlyRepaymentRecord.setReason(slEarlyRepaymentRecord.getReason());
		orgSlEarlyRepaymentRecord.setAccrualtype(slEarlyRepaymentRecord
				.getAccrualtype());
		orgSlEarlyRepaymentRecord.setEarlyDate(slEarlyRepaymentRecord
				.getEarlyDate());
		slEarlyRepaymentRecordDao.save(orgSlEarlyRepaymentRecord);
		/*
		 * Long slEarlyRepaymentId = slEarlyRepaymentRecordDao.save(
		 * slEarlyRepaymentRecord).getSlEarlyRepaymentId();
		 */
		List<SlFundIntent> allintent = this.slFundIntentDao
				.getlistbyslEarlyRepaymentId(slEarlyRepaymentId, orgSlEarlyRepaymentRecord.getBusinessType(),
						projectId);
		for (SlFundIntent s : allintent) {
			slFundIntentDao.evict(s);
			if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
				this.slFundIntentDao.remove(s);
			}
		}

		for (SlFundIntent b : slFundIntentsSuperviseRecord) {
			b.setIsValid(Short.valueOf("0"));
			b.setSlEarlyRepaymentId(slEarlyRepaymentId);
			slFundIntentDao.save(b);

		}

		return true;
	}

	@Override
	public boolean askForProjectFlow(Long projectId,
			List<SlActualToCharge> slActualToCharges,
			List<SlActualToCharge> slActualToChargesuperviseRecord,
			List<SlFundIntent> slFundIntents,
			List<SlFundIntent> slFundIntentsSuperviseRecord,
			SlSuperviseRecord slSuperviseRecord, String categoryIds,
			boolean isNoStart) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getList(String customerType, Long customerId) {

		return dao.getList(customerType, customerId);
	}

	@Override
	public boolean startBreach(BpFundProject project, String listed,
			String comments) {

		try {
			project.setProjectStatus(Integer.valueOf("8").shortValue());
			project.setBreachComment(comments);
			this.bpFundProjectDao.merge(project);
			if (listed.equals("1")) {
				String ot = project.getOppositeType();
				Long oppositeID = project.getOppositeID();
				String adminName = ContextUtil.getCurrentUser().getFullname();
				String blackReason = "【" + project.getProjectName()
						+ "】更改为违约处理贷款-【" + adminName + "】将此客户加入黑名单";
				if (ot.equals("company_customer")) {
					Enterprise e = this.enterpriseDao.getById(oppositeID
							.intValue());
					e.setIsBlack(true);
					e.setBlackReason(blackReason);
					this.enterpriseDao.merge(e);
				} else if (ot.equals("person_customer")) {
					Person p = this.personDao.getById(oppositeID.intValue());
					p.setIsBlack(true);
					p.setBlackReason(blackReason);
					this.personDao.merge(p);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public SlSmallloanProject getListByoperationType(Long projectId,
			String operationType) {

		return dao.getListByoperationType(projectId, operationType);
	}

	@Override
	public Integer updateMcroLoanInfo(SlSmallloanProject slSmallloanProject,
			Person person, List<BorrowerInfo> listBO,
			List<SlRepaymentSource> SlRepaymentSources,
			List<SlActualToCharge> slActualToCharges,
			String isDeleteAllFundIntent, 
			EnterpriseBank enterpriseBank, Spouse spouse,
			List<SlFundIntent> slFundIntents, StringBuffer sb) {

		try {

			SlSmallloanProject persistent = this.dao.get(slSmallloanProject
					.getProjectId());
			/**
			 * 更新股东信息 开始
			 */

			if (listBO.size() > 0) {

				for (int i = 0; i < listBO.size(); i++) {
					BorrowerInfo bo = listBO.get(i);
					if (bo.getBorrowerInfoId() == null) {
						bo.setProjectId(persistent.getProjectId());
						bo.setBusinessType(persistent.getBusinessType());
						bo.setOperationType(persistent.getOperationType());
						this.borrowerInfoDao.save(bo);
					} else {
						BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo
								.getBorrowerInfoId());
						BeanUtils.copyProperties(boPersistent, bo);
						this.borrowerInfoDao.merge(boPersistent);
					}
					if (null != bo.getType() && bo.getType() == 0) {
						if (null != bo.getCustomerId()) {
							Enterprise e = this.enterpriseDao.getById(bo
									.getCustomerId());
							e.setArea(bo.getAddress());
							e.setCciaa(bo.getCardNum());
							e.setTelephone(bo.getTelPhone());
							this.enterpriseDao.merge(e);
						}
					} else if (null != bo.getType() && bo.getType() == 1) {
						Person p = this.personDao.getById(bo.getCustomerId());
						p.setPostaddress(bo.getAddress());
						p.setCardnumber(bo.getCardNum());
						p.setCellphone(bo.getTelPhone());
						this.personDao.merge(p);
					}
				}
			}

			Person pPersistent = this.personDao.getById(person.getId());
			pPersistent.setMarry(person.getMarry());
			pPersistent.setName(person.getName());
			pPersistent.setSex(person.getSex());
			pPersistent.setCardtype(person.getCardtype());
			pPersistent.setCardnumber(person.getCardnumber());
			pPersistent.setCellphone(person.getCellphone());
			pPersistent.setPostcode(person.getPostcode());
			pPersistent.setBirthday(person.getBirthday());
			pPersistent.setUnitaddress(person.getUnitaddress());
			pPersistent.setFamilyaddress(person.getFamilyaddress());
			pPersistent.setPostaddress(person.getPostaddress());
			pPersistent.setCurrentcompany(person.getCurrentcompany());
			pPersistent.setHukou(person.getHukou());
			this.personDao.save(pPersistent);

			// 账户信息
			if (enterpriseBank.getId() == null) {
				List<EnterpriseBank> list = enterpriseBankService.getBankList(
						person.getId(), Short.valueOf("1"), Short.valueOf("0"),Short.valueOf("0"));
				for (EnterpriseBank e : list) {
					e.setIscredit(Short.valueOf("1"));
					creditBaseDao.updateDatas(e);
				}
				enterpriseBank.setEnterpriseid(person.getId());
				enterpriseBank.setIscredit(Short.valueOf("0"));
				enterpriseBank.setIsEnterprise(Short.valueOf("1"));
				enterpriseBank.setOpenCurrency(Short.valueOf("0"));
				enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
				enterpriseBank.setIsInvest(Short.valueOf("0"));
				creditBaseDao.saveDatas(enterpriseBank);

			} else {
				EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(
						EnterpriseBank.class, enterpriseBank.getId());
				bank.setName(enterpriseBank.getName());
				bank.setOpenType(enterpriseBank.getOpenType());
				bank.setAccountnum(enterpriseBank.getAccountnum());
				bank.setAccountType(enterpriseBank.getAccountType());
				bank.setBankid(enterpriseBank.getBankid());
				// bank.setBankname(enterpriseBank.getBankname());
				bank.setAreaId(enterpriseBank.getAreaId());
				bank.setAreaName(enterpriseBank.getAreaName());
				bank.setBankOutletsName(bank.getBankOutletsName());

				creditBaseDao.updateDatas(bank);
			}
			sb.append(",enterpriseBankId:" + enterpriseBank.getId());
			baseCustomService.getCustomToweb("1", pPersistent.getId(), 0);
			// 配偶信息
			if (null != person.getMarry() && person.getMarry() == 317) {
				if (spouse.getSpouseId() == null) {
					spouse.setPersonId(person.getId());
					creditBaseDao.saveDatas(spouse);
				} else {
					Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
							spouse.getSpouseId());
					s.setCardnumber(spouse.getCardnumber());
					s.setCardtype(spouse.getCardtype());
					s.setCurrentcompany(spouse.getCurrentcompany());
					s.setDgree(spouse.getDgree());
					s.setJob(spouse.getJob());
					s.setLinkTel(spouse.getLinkTel());
					s.setName(spouse.getName());
					s.setPoliticalStatus(spouse.getPoliticalStatus());
					creditBaseDao.updateDatas(s);
				}
				sb.append(",spouseId:" + spouse.getSpouseId());
			} else {
				if (spouse.getSpouseId() != null) {
					Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
							spouse.getSpouseId());
					creditBaseDao.deleteDatas(s);
				}
			}
			/*
			 * EnterpriseBank bank=(EnterpriseBank)
			 * this.creditBaseDao.getById(EnterpriseBank.class,
			 * enterpriseBank.getId());
			 * bank.setBankid(enterpriseBank.getBankid());
			 * bank.setOpenType(enterpriseBank.getOpenType());
			 * bank.setAccountnum(enterpriseBank.getAccountnum());
			 * bank.setAccountType(enterpriseBank.getAccountType());
			 * bank.setName(enterpriseBank.getName());
			 * bank.setBankname(enterpriseBank.getBankname());
			 * this.creditBaseDao.updateDatas(bank);
			 */
			if (SlRepaymentSources.size() > 0) {

				for (SlRepaymentSource temp : SlRepaymentSources) {

					boolean flag = StringUtil.isNumeric(temp.getTypeId());
					GlobalType globalType = globalTypeDao.getByNodeKey(
							"repaymentSource").get(0);
					if (flag == false) {

						Dictionary dic = new Dictionary();
						dic.setItemValue(temp.getTypeId());
						dic.setItemName(globalType.getTypeName());
						dic.setProTypeId(globalType.getProTypeId());
						dic.setDicKey(temp.getTypeId());
						dic.setStatus("0");
						dictionaryDao.save(dic);

						temp.setTypeId(String.valueOf(dic.getDicId()));
					} else {

						Dictionary cd = dictionaryDao.get(Long.valueOf(temp
								.getTypeId()));
						if (null == cd) {
							Dictionary dic = new Dictionary();
							dic.setItemValue(temp.getTypeId());
							dic.setItemName(globalType.getTypeName());
							dic.setProTypeId(globalType.getProTypeId());
							dic.setDicKey(temp.getTypeId());
							dic.setStatus("0");
							dictionaryDao.save(dic);
							temp.setTypeId(String.valueOf(dic.getDicId()));
						}
					}
					temp.setProjId(slSmallloanProject.getProjectId());
					if (temp.getSourceId() == null) {

						this.slRepaymentSourceDao.save(temp);

					} else {

						SlRepaymentSource rPersistent = this.slRepaymentSourceDao
								.get(temp.getSourceId());
						BeanUtil.copyNotNullProperties(rPersistent, temp);
						this.slRepaymentSourceDao.save(rPersistent);
					}
				}
			}
		/*	
		 * if (financeInfo.getFinanceInfoId() == null) {
				financeInfo.setProjectId(persistent.getProjectId());
				financeInfo.setBusinessType(persistent.getBusinessType());
				financeInfoDao.save(financeInfo);
			} else {
				FinanceInfo orgFinanceInfo = financeInfoDao.get(financeInfo
						.getFinanceInfoId());
				BeanUtil.copyNotNullProperties(orgFinanceInfo, financeInfo);
				orgFinanceInfo.setBusinessType(persistent.getBusinessType());
				financeInfoDao.save(orgFinanceInfo);

			}
			sb.append(",financeInfoId:" + financeInfo.getFinanceInfoId());
		*/
			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
			slSmallloanProject.setCompanyId(persistent.getCompanyId());
			// end
			slSmallloanProject.setStates(persistent.getStates());
			slSmallloanProject.setIsOtherFlow(persistent.getIsOtherFlow());
			BeanUtils.copyProperties(slSmallloanProject, persistent,
					new String[] { "id", "operationType", "flowType",
							"mineType", "mineId", "oppositeType", "oppositeID",
							"projectName", "projectNumber", "oppositeType",
							"businessType", "createDate" });
			if (isDeleteAllFundIntent.equals("1")) {
				List<SlActualToCharge> allactual = this.slActualToChargeDao
						.listbyproject(slSmallloanProject.getProjectId(),
								"SmallLoan");
				List<SlFundIntent> allintent = this.slFundIntentDao
						.getByProjectId1(slSmallloanProject.getProjectId(),
								"SmallLoan");
				for (SlFundIntent s : allintent) {
					slFundIntentDao.evict(s);
					if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						this.slFundIntentDao.remove(s);
					}
				}
			}
			for (SlFundIntent a : slFundIntents) {
				slFundIntentDao.evict(a);
				this.slFundIntentDao.save(a);
			}
			for (SlActualToCharge a : slActualToCharges) {
				this.slActualToChargeDao.save(a);

			}
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));

			this.dao.merge(persistent);

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/*
	 * (non-Javadoc) 微贷风险审核
	 */
	public Integer updateMcroLoanRisk(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao
							.getByTaskId(flowRunInfo.getTaskId());

					if (currentForm != null) {
						boolean isToNextTask = creditProjectService
								.compareTaskSequence(currentForm.getRunId(),
										currentForm.getActivityName(),
										transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao
									.getByRunIdFlowNodeKey(currentForm
											.getRunId(), "mnDueDiligence");
							if (processForm != null
									&& processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId()
										.toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							String sbhPartake = flowRunInfo.getRequest()
									.getParameter("sbhPartake");
							if (sbhPartake != null && !"".equals(sbhPartake)) {
								String assignUserIds = sbhPartake;
								vars.put("flowAssignId", assignUserIds);
							}
						}
					}
					vars.put("riskManagerCheckResult", transitionName);
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
	 * 放款审批提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer examineAndApprove(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")
							|| transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao
								.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService
									.compareTaskSequence(
											currentForm.getRunId(), currentForm
													.getActivityName(),
											transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								String smallLoanFastSDH = flowRunInfo
										.getRequest().getParameter(
												"smallLoanFastSDH");
								if (smallLoanFastSDH != null
										&& !"".equals(smallLoanFastSDH)) {
									ProcessForm processForm = processFormDao
											.getByRunIdFlowNodeKey(currentForm
													.getRunId(),
													"mfDueDiligence");
									if (processForm != null
											&& processForm.getCreatorId() != null) {
										String creatorId = processForm
												.getCreatorId().toString();
										vars.put("flowAssignId", creatorId);
									}
								} else {
									ProcessForm processForm = processFormDao
											.getByRunIdFlowNodeKey(currentForm
													.getRunId(),
													"mnDueDiligence");
									if (processForm != null
											&& processForm.getCreatorId() != null) {
										String creatorId = processForm
												.getCreatorId().toString();
										vars.put("flowAssignId", creatorId);
									}
								}
								// 保存页面中需要保存的贷款信息
								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo
										.getRequest(), project,
										"slSmallloanProject");
								SlSmallloanProject persistent = this.dao
										.get(project.getProjectId());
								project.setCompanyId(persistent.getCompanyId());
								project.setStates(persistent.getStates());
								project.setIsOtherFlow(persistent
										.getIsOtherFlow());
								String degree = flowRunInfo.getRequest()
										.getParameter("degree");
								if (null != degree && !"".equals(degree)) {
									project.setAppUserId(degree);
								}
								String isAheadPay = flowRunInfo.getRequest()
										.getParameter("isAheadPay");
								if (isAheadPay != null) {
									project.setIsAheadPay(Short.valueOf("1"));
								} else {
									project.setIsAheadPay(Short.valueOf("0"));
								}
								BeanUtils.copyProperties(project, persistent,
										new String[] { "id", "operationType",
												"flowType", "mineType",
												"mineId", "oppositeType",
												"oppositeID", "projectName",
												"projectNumber",
												"oppositeType", "businessType",
												"createDate" });
								String slActualToChargeJson = flowRunInfo
										.getRequest().getParameter(
												"slActualToChargeJson");
								slActualToChargeService.savejson(
										slActualToChargeJson, project
												.getProjectId(), "SmallLoan",
										Short.parseShort("0"), persistent
												.getCompanyId());
								ProjectActionUtil pu = new ProjectActionUtil();
								pu.getSmallloanMode(persistent);
								this.dao.merge(persistent);
								// return 1;
							} else {
								// 保存页面中需要保存的贷款信息
								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo
										.getRequest(), project,
										"slSmallloanProject");
								SlSmallloanProject persistent = this.dao
										.get(project.getProjectId());
								project.setStates(persistent.getStates());
								project.setIsOtherFlow(persistent
										.getIsOtherFlow());
								project.setCompanyId(persistent.getCompanyId());
								String degree = flowRunInfo.getRequest()
										.getParameter("degree");
								if (null != degree && !"".equals(degree)) {
									project.setAppUserId(degree);
								}
								String isAheadPay = flowRunInfo.getRequest()
										.getParameter("isAheadPay");
								if (isAheadPay != null) {
									project.setIsAheadPay(Short.valueOf("1"));
								} else {
									project.setIsAheadPay(Short.valueOf("0"));
								}
								BeanUtils.copyProperties(project, persistent,
										new String[] { "id", "operationType",
												"flowType", "mineType",
												"mineId", "oppositeType",
												"oppositeID", "projectName",
												"projectNumber",
												"oppositeType", "businessType",
												"createDate" });
								String slActualToChargeJson = flowRunInfo
										.getRequest().getParameter(
												"slActualToChargeJson");
								slActualToChargeService.savejson(
										slActualToChargeJson, project
												.getProjectId(), "SmallLoan",
										Short.parseShort("0"), persistent
												.getCompanyId());
								ProjectActionUtil pu = new ProjectActionUtil();
								pu.getSmallloanMode(persistent);
								this.dao.merge(persistent);
								// return 1;
							}
						}

						// =====小贷常规项目在贷款审查任务提交时生成贷款审查审批表开始==========（add by
						// liny 2013-2-21）
						String serverPath = AppUtil.getAppAbsolutePath();// 拿到当前项目的却对路径
						String businessType_ = "SmallLoan";
						Long projectId_ = Long.valueOf(flowRunInfo.getRequest()
								.getParameter("slSmallloanProject.projectId"));
						String comments = flowRunInfo.getRequest()
								.getParameter("comments");
						String categoryId = "0";// ProcreditContractCategory中的id
						String cconId = "0";// 合同表的id （contractId）
						String projectID_ = projectId_ + "";
						String _mark = "LoanNotice";
						String htType = "LoanNotice";
						String htmcdName = "放款通知书";
						String projectNumber = "";
						String path = "", fileName = "", shortName = "";
						// ======查询视图看是否已经生成过了审批表开始
						List list = null;
						VProcreditContract vpcc = null;
						Object[] obj = { businessType_, projectID_, _mark };
						String hql = "from VProcreditContract where businessType =? and projId=? and htType=?";
						try {
							list = creditBaseDao.queryHql(hql, obj);
							if (list != null && list.size() > 0) {
								if (list.size() > 1) {
									vpcc = (VProcreditContract) list
											.get(list.size() - 1);
								} else if (list.size() == 0) {
									vpcc = (VProcreditContract) list
											.get(0);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (vpcc != null) {
							categoryId = vpcc.getId() + "";
							cconId = vpcc.getId() + "";
						}
						// =======查询视图看是否已经生成过了审批表结束

						DocumentTemplet doctemplet = documentTempletDao
								.findDocumentTemplet(_mark);// 模版的实体先查左侧数据
						DocumentTemplet templet_ = documentTempletDao
								.getTempletObj(doctemplet.getId());// 根据左侧模版的id
						// 查询右侧的文件
						if (null == templet_) {
							logger.error("没有模版，请上传模版");
						} else {
							String htlxdName = templet_.getText();
							String cType = templet_.getTemplettype() + "";
							int conId = Integer.parseInt(cconId);
							if (!templet_.isIsexist()) {
								logger.error("没有模版，请上传模版");
							} else {
								// 查询对应的模板文件是否上传
								boolean isFileExists = false;
								FileForm fileForm_ = fileFormService
										.getById(templet_.getFileid());
								DocumentTemplet templet = null;
								int tempId = templet_.getId();
								String ttempId = templet_.getId() + "";
								if (null != fileForm_) {
									String path_ = ElementUtil.replaceAllEleme(
											assureIntentBookElementCode,
											serverPath
													+ fileForm_.getFilepath(),
											fileForm_.getFilename());
									if (!"false".equals(path_)) {
										templet = documentTempletDao.getById(tempId);
										if (ttempId != null && templet != null) {
											DocumentTemplet dtemplet = documentTempletDao.getById(tempId);
											if (dtemplet != null) {// 只是单纯看一下数据库是否有模版文件记录
												if (dtemplet.getIsexist() == true
														&& dtemplet.getFileid() != 0) {// 只是单纯看一下数据库是否有模版文件记录是否有值
													FileForm fileF = fileFormService
															.getById(dtemplet
																	.getFileid());
													if (fileF != null) {
														File file = new File(
																serverPath
																		+ fileF
																				.getFilepath());
														if (file.exists()) {
															isFileExists = true;
														} else {
															logger
																	.error("没有模版，请上传模版文件");
														}
													}
												} else {
													logger
															.error("没有模版，请上传模版文件");
												}
											} else {
												logger.error("没有模版，请上传模版文件");
											}
										}
									} else {
										logger.error("没有模版，请上传模版");
									}
								}

								// 如果模板文件已经上传
								if (isFileExists == true) {
									ProcreditContract p = new ProcreditContract();
									p.setContractCategoryText(htlxdName);
									p.setContractCategoryTypeText(doctemplet
											.getText());
									p.setHtType(htType);// htType即使模版的唯一key值
									p.setTemptId(tempId);// 模版文件的Id
									p.setMortgageId(0);// 反担保ID 如果是反担保 这个菜有值
									// 不然就是0
									Long thirdRalationId = Long.parseLong("0");
									p.setIsApply(false);// 展期合同有用 是展期合同 则为true
									// 其他都为false
									if (categoryId == "0"
											|| "0".equals(categoryId)) {
										categoryId = procreditContractService.add(
												p, projectID_, businessType_)
												+ "";
									} else {
										if ("".equals(htlxdName)
												&& !"".equals(htmcdName)) {// 没选合同类型下拉菜单，只选了合同名称下拉菜单
											Object[] obj1 = {
													htmcdName,
													tempId,
													Integer
															.parseInt(categoryId) };
											elementHandleService
													.updateCon(
															"update ProcreditContract set contractCategoryText=?, temptId=? where id =?",
															obj1);
										} else if (!"".equals(htlxdName)
												&& "".equals(htmcdName)) {// 正规操作情况下没有这种可能
											Object[] obj1 = {
													htlxdName,
													Integer
															.parseInt(categoryId) };
											elementHandleService
													.updateCon(
															"update ProcreditContract set contractCategoryTypeText=? where id =?",
															obj1);
										} else if (!"".equals(htlxdName)
												&& !"".equals(htmcdName)) {
											Object[] obj1 = {
													htlxdName,
													htmcdName,
													tempId,
													Integer
															.parseInt(categoryId) };
											elementHandleService
													.updateCon(
															"update ProcreditContract set contractCategoryTypeText=?, contractCategoryText=?, temptId=? where id =?",
															obj1);
										}
									}

									// 合同编号
									String rnum = elementHandleService
											.getNumber(projectID_, ttempId);
									// 向合同表中增加或修改数据数据（由相应的合同id是否存在）
									conId = procreditContractService.makeUpload(
											cconId, ttempId, cType, categoryId,
											projectID_, thirdRalationId,
											businessType_, rnum, "");// 向合同表中添加数据
									SlSmallloanProject sloanProject = null;
									ProcreditContract pcontract = procreditContractService.getById(conId);
									sloanProject = dao.get(Long.valueOf(pcontract
													.getProjId()));
									projectNumber = sloanProject
											.getProjectNumber();
									if ((sloanProject.getOppositeType())
											.equals("company_customer")) {// 客户类型为企业
										Enterprise enterp =enterpriseDao.getById(Integer
														.parseInt(sloanProject
																.getOppositeID()
																.toString()));
										shortName = enterp.getShortname()
												.trim();
									} else if ((sloanProject.getOppositeType())
											.equals("person_customer")) {// 客户类型为个人
										Person person =personDao.getById(Integer
														.parseInt(sloanProject
																.getOppositeID()
																.toString()));
										shortName = person.getName().trim();
									}
									// 生成合同和合同文件的名字
									if (conId != 0) {
										fileName = pcontract.getContractName();
										if (null == fileName) {
											fileName = shortName + "-"
													+ projectNumber + "-"
													+ templet.getText() + "-"
													+ rnum;
										} else {
											fileName = shortName + "-"
													+ projectNumber + "-"
													+ templet.getText() + "-"
													+ rnum;
										}
									} else {
										fileName = shortName + "-"
												+ projectNumber + "-"
												+ templet.getText() + "-"
												+ rnum;
									}

									FileForm fileForm = fileFormService
											.getById(templet.getFileid());
									path = ElementUtil.getUrl(serverPath
											+ fileForm.getFilepath(), fileName
											+ ".doc");
									String fileCraName = shortName + "_"
											+ projectNumber;
									String copyDir = serverPath
											+ "attachFiles\\projFile\\contfolder\\"
											+ fileCraName + "\\"
											+ templet.getParentid() + "\\";
									File cFile = new File(copyDir);
									if (!cFile.exists()) {
										cFile.mkdirs();
									}
									String newPath = (copyDir + fileName + ".doc")
											.trim();
									String filePath = ("projFile/contfolder/"
											+ fileCraName + "/"
											+ templet.getParentid() + "/"
											+ fileName + ".doc").trim();
									Object[] obj2 = { true,
											"attachFiles/" + filePath, conId };
									elementHandleService
											.updateCon(
													"update ProcreditContract set isUpload=?, url=? where id =?",
													obj2);
									ProcreditContract pc=procreditContractDao.getById(conId);
									pc.setIsUpload(true);
									pc.setUrl("attachFiles/" + filePath);
									procreditContractDao.merge(pc);
									List<FileAttach> listfileAttach = fileAttachDao
											.listByContractId(conId);
									if (listfileAttach != null) {
										
										for(FileAttach f:listfileAttach){
											f.setFilePath(filePath);
											fileAttachDao.merge(f);
										}
									} else {
										// 智维附件表操作start...为的是可以在线编辑
										FileAttach fileAttach = new FileAttach();
										fileAttach.setFileName(fileName);
										fileAttach.setFilePath(filePath);
										fileAttach.setCreatetime(new Date());
										fileAttach.setExt("doc");
										fileAttach
												.setFileType("attachFiles/uploads");
										fileAttach.setCreatorId(Long
												.parseLong(ContextUtil
														.getCurrentUser()
														.getId()));
										fileAttach
												.setCreator(ContextUtil
														.getCurrentUser()
														.getFullname());
										fileAttach.setDelFlag(0);
										fileAttach.setCsContractId(conId);
										fileAttachDao.save(fileAttach);
										// 智维附件表操作end...
									}
									SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
									// 贷款审查审批表文档 往贷款审查审批表文档要素里面放值的方法
									SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
											.getElementBySystem(projectID_,
													conId, tempId, htType,
													rnum, comments);
									File file = new File(path);
									if (file.exists()) {
										JacobWord
												.getInstance()
												.replaceAllText(
														path,
														ElementUtil
																.findEleCodeArray(smallLoanElementCode),
														ElementUtil
																.findEleCodeValueArray(smallLoanElementCodeValue));
										FileHelper.copyFile(path, newPath);
										FileHelper.deleteFile(path);
									}

								}
							}
						}
						// =====小贷常规项目在贷款审查任务提交时生成贷款审查审批表结束==========

						String smallLoanFastSDH = flowRunInfo.getRequest()
								.getParameter("smallLoanFastSDH");
						if (smallLoanFastSDH != null
								&& !"".equals(smallLoanFastSDH)) {
							vars.put("mfLoanExamineAndApproveResult",
									transitionName);
							flowRunInfo.getVariables().putAll(vars);
						} else {
							vars.put("loanExamineAndApproveResult",
									transitionName);
							flowRunInfo.getVariables().putAll(vars);
						}
					}
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer updateMcroLoanApproval(FlowRunInfo flowRunInfo) {
		try {
			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");
			String signVoteType = flowRunInfo.getRequest().getParameter(
					"signVoteType1");// 投票结果：同意(1)、决绝(2)、弃权(0)。
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

						// 表示为微贷快速流程的审贷会,根据此参数判断执行不同的分支跳转规则。value只要不为空即可。
						String microFastSDH = flowRunInfo.getRequest()
								.getParameter("microFastSDH");
						if (microFastSDH != null && !"".equals(microFastSDH)) {
							vars.put("microFastSDH", "true");
						}

						vars.put("microNormalSDH", "false");
						vars
								.put("projectId", project.getProjectId()
										.toString());
						// vars.put("businessType", value);
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

	/**
	 * 
	 * 小额确认终审通过意见
	 */
	@Override
	public Integer updateSmallLoanConfirm(FlowRunInfo flowRunInfo) {

		try {

			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");
			SlSmallloanProject persistent = this.dao
					.get(project.getProjectId());
			project.setCompanyId(persistent.getCompanyId());
			project.setStates(persistent.getStates());
			project.setIsOtherFlow(persistent.getIsOtherFlow());
			String degree = flowRunInfo.getRequest().getParameter("degree");
			if (null != degree && !"".equals(degree)) {
				project.setAppUserId(degree);
			}
			String isAheadPay = flowRunInfo.getRequest().getParameter(
					"isAheadPay");
			if (isAheadPay != null) {
				project.setIsAheadPay(Short.valueOf("1"));
			} else {
				project.setIsAheadPay(Short.valueOf("0"));
			}
			BeanUtils.copyProperties(project, persistent, new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "oppositeType", "businessType",
					"createDate" });
			String slActualToChargeJson = flowRunInfo.getRequest()
					.getParameter("slActualToChargeJson");
			slActualToChargeService.savejson(slActualToChargeJson, project
					.getProjectId(), "SmallLoan", Short.parseShort("0"),
					persistent.getCompanyId());
			// 款项计划
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String maxintentDate = slFundIntentService.savejson(slFundIentJson,
					project.getProjectId(), "SmallLoan", Short.parseShort("1"),
					persistent.getCompanyId());
			// if(!persistent.getAccrualtype().equals("singleInterest")){
			// persistent.setPayintentPeriod(Integer.valueOf(sumintent));
			// }
			StatsPro statsPro = new StatsPro();
			statsPro.calcuProIntentDate(persistent);
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));
			ProjectActionUtil pu = new ProjectActionUtil();
			pu.getSmallloanMode(persistent);
			this.dao.merge(persistent);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 微贷确认终审通过意见
	 */
	@Override
	public Integer updateConfirmComments(FlowRunInfo flowRunInfo) {

		try {

			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");
			SlSmallloanProject persistent = this.dao
					.get(project.getProjectId());
			project.setCompanyId(persistent.getCompanyId());
			project.setStates(persistent.getStates());
			project.setIsOtherFlow(persistent.getIsOtherFlow());
			String degree = flowRunInfo.getRequest().getParameter("degree");
			if (null != degree && !"".equals(degree)) {
				project.setAppUserId(degree);
			}
			String isAheadPay = flowRunInfo.getRequest().getParameter(
					"isAheadPay");
			if (isAheadPay != null) {
				project.setIsAheadPay(Short.valueOf("1"));
			} else {
				project.setIsAheadPay(Short.valueOf("0"));
			}
			BeanUtils.copyProperties(project, persistent, new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "oppositeType", "businessType",
					"createDate" });
			String slActualToChargeJson = flowRunInfo.getRequest()
					.getParameter("slActualToChargeJson");
			slActualToChargeService.savejson(slActualToChargeJson, project
					.getProjectId(), "SmallLoan", Short.parseShort("0"),
					persistent.getCompanyId());
			// 款项计划
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String maxintentDate = slFundIntentService.savejson(slFundIentJson,
					project.getProjectId(), "SmallLoan", Short.parseShort("1"),
					persistent.getCompanyId());
			// if(!persistent.getAccrualtype().equals("singleInterest")){
			// persistent.setPayintentPeriod(Integer.valueOf(sumintent));
			// }
			StatsPro statsPro = new StatsPro();
			statsPro.calcuProIntentDate(persistent);
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));
			ProjectActionUtil pu = new ProjectActionUtil();
			pu.getSmallloanMode(persistent);
			this.dao.merge(persistent);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 小贷常规-复核终审通过意见提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer updateSmallLoanReviewCommentsNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")
							|| transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao
								.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService
									.compareTaskSequence(
											currentForm.getRunId(), currentForm
													.getActivityName(),
											transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							} else {
								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo
										.getRequest(), project,
										"slSmallloanProject");
								SlSmallloanProject persistent = this.dao
										.get(project.getProjectId());
								project.setCompanyId(persistent.getCompanyId());
								project.setStates(persistent.getStates());
								project.setIsOtherFlow(persistent
										.getIsOtherFlow());
								String degree = flowRunInfo.getRequest()
										.getParameter("degree");
								// String isPreposePayAccrual =
								// flowRunInfo.getRequest().getParameter("isPreposePayAccrualCheck");
								if (null != degree && !"".equals(degree)) {
									project.setAppUserId(degree);
								}
								String isAheadPay = flowRunInfo.getRequest()
										.getParameter("isAheadPay");
								if (isAheadPay != null) {
									project.setIsAheadPay(Short.valueOf("1"));
								} else {
									project.setIsAheadPay(Short.valueOf("0"));
								}
								/*
								 * if(isPreposePayAccrual!=null){
								 * project.setIsPreposePayAccrual(1); }else {
								 * project.setIsPreposePayAccrual(0);
								 * 
								 * }
								 */
								BeanUtils.copyProperties(project, persistent,
										new String[] { "id", "operationType",
												"flowType", "mineType",
												"mineId", "oppositeType",
												"oppositeID", "projectName",
												"projectNumber",
												"oppositeType", "businessType",
												"createDate" });
								String slActualToChargeJson = flowRunInfo
										.getRequest().getParameter(
												"slActualToChargeJson");
								slActualToChargeService.savejson(
										slActualToChargeJson, project
												.getProjectId(), persistent
												.getBusinessType(), Short
												.parseShort("0"), persistent
												.getCompanyId());
								// 款项计划
								String slFundIentJson = flowRunInfo
										.getRequest().getParameter(
												"fundIntentJsonData");
								String maxintentDate = slFundIntentService
										.savejson(slFundIentJson, project
												.getProjectId(), persistent
												.getBusinessType(), Short
												.parseShort("1"), persistent
												.getCompanyId());
								// if(!persistent.getAccrualtype().equals("singleInterest")){
								// persistent.setPayintentPeriod(Integer.valueOf(sumintent));
								// }
								StatsPro statsPro = new StatsPro();
								statsPro.calcuProIntentDate(persistent);
								Map<String, BigDecimal> map = slFundIntentService
										.saveProjectfiance(persistent
												.getProjectId(), persistent
												.getBusinessType());
								persistent.setPaychargeMoney(map
										.get("paychargeMoney"));
								persistent.setIncomechargeMoney(map
										.get("incomechargeMoney"));
								persistent.setAccrualMoney(map
										.get("loanInterest"));
								persistent.setConsultationMoney(map
										.get("consultationMoney"));
								persistent.setServiceMoney(map
										.get("serviceMoney"));
								ProjectActionUtil pu = new ProjectActionUtil();
								pu.getSmallloanMode(persistent);
								this.dao.merge(persistent);
							}
						}
					}
					vars.put("slnHeadquartersApprovalResult", transitionName);
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
	 * 微贷复核终审意见
	 */
	@Override
	public Integer updateReviewComments(FlowRunInfo flowRunInfo) {

		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					ProcessForm currentForm = processFormDao
							.getByTaskId(flowRunInfo.getTaskId());

					if (currentForm != null) {
						boolean isToNextTask = creditProjectService
								.compareTaskSequence(currentForm.getRunId(),
										currentForm.getActivityName(),
										transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							ProcessForm processForm = processFormDao
									.getByRunIdFlowNodeKey(currentForm
											.getRunId(), "mnConfirmPassResult");
							if (processForm != null
									&& processForm.getCreatorId() != null) {
								String creatorId = processForm.getCreatorId()
										.toString();
								vars.put("flowAssignId", creatorId);
							}
						} else {
							SlSmallloanProject project = new SlSmallloanProject();
							BeanUtil.populateEntity(flowRunInfo.getRequest(),
									project, "slSmallloanProject");
							SlSmallloanProject persistent = this.dao
									.get(project.getProjectId());
							project.setCompanyId(persistent.getCompanyId());
							project.setStates(persistent.getStates());
							project.setIsOtherFlow(persistent.getIsOtherFlow());
							String degree = flowRunInfo.getRequest()
									.getParameter("degree");
							if (null != degree && !"".equals(degree)) {
								project.setAppUserId(degree);
							}
							String isAheadPay = flowRunInfo.getRequest()
									.getParameter("isAheadPay");
							if (isAheadPay != null) {
								project.setIsAheadPay(Short.valueOf("1"));
							} else {
								project.setIsAheadPay(Short.valueOf("0"));
							}
							BeanUtils.copyProperties(project, persistent,
									new String[] { "id", "operationType",
											"flowType", "mineType", "mineId",
											"oppositeType", "oppositeID",
											"projectName", "projectNumber",
											"oppositeType", "businessType",
											"createDate" });
							String slActualToChargeJson = flowRunInfo
									.getRequest().getParameter(
											"slActualToChargeJson");
							slActualToChargeService.savejson(
									slActualToChargeJson, project
											.getProjectId(), "SmallLoan", Short
											.parseShort("0"), persistent
											.getCompanyId());
							// 款项计划
							String slFundIentJson = flowRunInfo.getRequest()
									.getParameter("fundIntentJsonData");
							String maxintentDate = slFundIntentService
									.savejson(slFundIentJson, project
											.getProjectId(), "SmallLoan", Short
											.parseShort("1"), persistent
											.getCompanyId());
							// if(!persistent.getAccrualtype().equals("singleInterest")){
							// persistent.setPayintentPeriod(Integer.valueOf(sumintent));
							// }
							StatsPro statsPro = new StatsPro();
							statsPro.calcuProIntentDate(persistent);
							Map<String, BigDecimal> map = slFundIntentService
									.saveProjectfiance(persistent
											.getProjectId(), "SmallLoan");
							persistent.setPaychargeMoney(map
									.get("paychargeMoney"));
							persistent.setIncomechargeMoney(map
									.get("incomechargeMoney"));
							persistent.setAccrualMoney(map.get("loanInterest"));
							persistent.setConsultationMoney(map
									.get("consultationMoney"));
							persistent.setServiceMoney(map.get("serviceMoney"));
							ProjectActionUtil pu = new ProjectActionUtil();
							pu.getSmallloanMode(persistent);
							this.dao.merge(persistent);
						}
					}
					vars.put("slnHeadquartersApprovalResult", transitionName);
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
	 * 小贷修改贷款账号信息
	 */
	@Override
	public Integer saveSmallLoanPartCustomerInfo(FlowRunInfo flowRunInfo) {
		try {
			EnterpriseBank enterpriseBank = new EnterpriseBank();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,
					"enterpriseBank");
			EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(
					EnterpriseBank.class, enterpriseBank.getId());
			bank.setAccountnum(enterpriseBank.getAccountnum());
			bank.setAccountType(enterpriseBank.getAccountType());
			bank.setBankid(enterpriseBank.getBankid());
			bank.setBankname(enterpriseBank.getBankname());
			bank.setName(enterpriseBank.getName());
			bank.setOpenType(enterpriseBank.getOpenType());
			bank.setAreaId(enterpriseBank.getAreaId());
			bank.setAreaName(enterpriseBank.getAreaName());
			bank.setBankOutletsName(bank.getBankOutletsName());
			creditBaseDao.updateDatas(bank);
			baseCustomService.getCustomToweb(bank.getIsEnterprise().toString(),
					bank.getEnterpriseid(), 0);
			SlSmallloanProject sl = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), sl,
					"slSmallloanProject");
			SlSmallloanProject persistent = this.dao.get(sl.getProjectId());
			sl = persistent;
			if (persistent.getStates() == 2) {
				sl.setStates(Short.valueOf("0"));
			}
			this.dao.save(sl);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 微贷修改贷款账号信息
	 */

	public Integer savepartcustomerInfo(FlowRunInfo flowRunInfo) {
		try {
			EnterpriseBank enterpriseBank = new EnterpriseBank();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,
					"enterpriseBank");
			EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(
					EnterpriseBank.class, enterpriseBank.getId());
			bank.setAccountnum(enterpriseBank.getAccountnum());
			bank.setAccountType(enterpriseBank.getAccountType());
			bank.setBankid(enterpriseBank.getBankid());
			bank.setBankname(enterpriseBank.getBankname());
			bank.setName(enterpriseBank.getName());
			bank.setOpenType(enterpriseBank.getOpenType());
			bank.setAreaId(enterpriseBank.getAreaId());
			bank.setAreaName(enterpriseBank.getAreaName());
			bank.setBankOutletsName(bank.getBankOutletsName());
			creditBaseDao.updateDatas(bank);
			baseCustomService.getCustomToweb("1", bank.getEnterpriseid(), 0);
			SlSmallloanProject sl = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), sl,
					"slSmallloanProject");
			SlSmallloanProject persistent = this.dao.get(sl.getProjectId());
			sl = persistent;
			if (persistent.getStates() == 2) {
				sl.setStates(Short.valueOf("0"));
			}
			this.dao.save(sl);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Integer updateCompanyRiskNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				// transitionName获取获到的节点名
				String transitionName = flowRunInfo.getTransitionName();
				// 选择下一个任务的处理人
				String riskAttacheId = flowRunInfo.getRequest().getParameter(
						"riskAttacheId");
				// 这获是身带会成员，名字一样的话多好
				String onlineJudgementIds = flowRunInfo.getRequest()
						.getParameter("onlineJudgementIds");

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")
							|| transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao
								.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService
									.compareTaskSequence(
											currentForm.getRunId(), currentForm
													.getActivityName(),
											transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							} else {
								// sbhPartake是什么?为毛不同意以下下
								String sbhPartake = flowRunInfo.getRequest()
										.getParameter("sbhPartake");
								if (sbhPartake != null
										&& !"".equals(sbhPartake)) {
									String assignUserIds = sbhPartake;
									vars.put("flowAssignId", assignUserIds);
								}
								if (riskAttacheId != null
										&& !"".equals(riskAttacheId)) {
									String assignUserId = riskAttacheId;
									vars.put("flowAssignId", assignUserId);
								}
								if ("线上评审会决议".equals(transitionName)) {// 有必要不？
									if (onlineJudgementIds != null
											&& !"".equals(onlineJudgementIds)) {
										String assignUserId = onlineJudgementIds;
										vars.put("flowAssignId", assignUserId);
									}
								}
							}
						}
					}
					vars.put("slnRiskManagerCheckResult", transitionName);
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
	 * 小贷常规-风险审核提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer updateSmallLoanRiskNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJson");
				Enumeration aaa=flowRunInfo.getRequest().getParameterNames();
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")
							|| transitionName.contains("结束")) {
						//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao
								.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService
									.compareTaskSequence(
											currentForm.getRunId(), currentForm
													.getActivityName(),
											transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								//手续清单
								SlSmallloanProject project = dao.get(Long.parseLong(projectId));
								if(null!=slActualToChargeJson&&!"".equals(slActualToChargeJson)&&!"undefined".equals(slActualToChargeJson)){
						        slActualToChargeService.saveJson(slActualToChargeJson,project.getProjectId(),"SmallLoan",(short)0,1l,null);
					
					            }
							}
						}
					}
					
					vars.put("slnRiskManagerCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷常规-风险审核提交下一步出错：" + e.getMessage());
			return 0;
		}
	}

	/**
	 * 小贷常规-总公司审批提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer updateCompanyApprovelNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				SlSmallloanProject project = new SlSmallloanProject();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
						"slSmallloanProject");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")
							|| transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao
								.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService
									.compareTaskSequence(
											currentForm.getRunId(), currentForm
													.getActivityName(),
											transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							} else {
								SlSmallloanProject persistent = this.dao
										.get(project.getProjectId());
								persistent.setProjectMoneyPass(project
										.getProjectMoney());
								this.dao.save(persistent);
							}
						}
					}
					vars.put("slnHeadquartersApprovalResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷常规-总公司审批提交下一步出错：" + e.getMessage());
			return 0;
		}

	}

	/**
	 * 小贷常规、小贷快速-贷款审查提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer updateSmallLoadCheckNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")
							|| transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao
								.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService
									.compareTaskSequence(
											currentForm.getRunId(), currentForm
													.getActivityName(),
											transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
								/*
								 * String smallLoanFastSDH =
								 * flowRunInfo.getRequest
								 * ().getParameter("smallLoanFastSDH"); if
								 * (smallLoanFastSDH != null&&
								 * !"".equals(smallLoanFastSDH)) { ProcessForm
								 * processForm =
								 * processFormDao.getByRunIdFlowNodeKey
								 * (currentForm
								 * .getRunId(),"slfCreditCheckResult"); if
								 * (processForm != null&&
								 * processForm.getCreatorId() != null) { String
								 * creatorId =
								 * processForm.getCreatorId().toString();
								 * vars.put("flowAssignId", creatorId); } } else
								 * { ProcessForm processForm =
								 * processFormDao.getByRunIdFlowNodeKey
								 * (currentForm
								 * .getRunId(),"slnCreditCheckResult"); if
								 * (processForm != null&&
								 * processForm.getCreatorId() != null) { String
								 * creatorId =
								 * processForm.getCreatorId().toString();
								 * vars.put("flowAssignId", creatorId); } }
								 */
							} else {
								String borrowerInfo = flowRunInfo.getRequest()
										.getParameter("borrowerInfo");
								String gudongInfo = flowRunInfo.getRequest()
										.getParameter("gudongInfo");
								String isAheadPay = flowRunInfo.getRequest()
										.getParameter("isAheadPay");
								// String isPreposePayAccrual =
								// flowRunInfo.getRequest().getParameter("isPreposePayAccrualCheck");
								String degree = flowRunInfo.getRequest()
										.getParameter("degree");
								// String flowOver =
								// flowRunInfo.getRequest().getParameter("flowOver");//
								// 项目放款状态:1表示项目放款;2表示未放款,项目终止。

								SlSmallloanProject project = new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo
										.getRequest(), project,
										"slSmallloanProject");
								// 保存共同借款人信息
								List<BorrowerInfo> listBO = new ArrayList<BorrowerInfo>();
								if (null != borrowerInfo
										&& !"".equals(borrowerInfo)) {
									String[] borrowerInfoArr = borrowerInfo
											.split("@");
									for (int i = 0; i < borrowerInfoArr.length; i++) {
										String str = borrowerInfoArr[i];
										JSONParser parser = new JSONParser(
												new StringReader(str));
										BorrowerInfo bo = (BorrowerInfo) JSONMapper
												.toJava(parser.nextValue(),
														BorrowerInfo.class);
										listBO.add(bo);
									}
								}
								// 保存股东信息
								List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
								if (null != gudongInfo
										&& !"".equals(gudongInfo)) {
									String[] shareequityArr = gudongInfo
											.split("@");
									for (int i = 0; i < shareequityArr.length; i++) {
										String str = shareequityArr[i];
										JSONParser parser = new JSONParser(
												new StringReader(str));
										EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper
												.toJava(
														parser.nextValue(),
														EnterpriseShareequity.class);
										listES.add(enterpriseShareequity);
									}
								}

								if (listBO.size() > 0) {
									for (int i = 0; i < listBO.size(); i++) {
										BorrowerInfo bo = listBO.get(i);
										if (bo.getBorrowerInfoId() == null) {
											bo.setProjectId(project
													.getProjectId());
											bo.setBusinessType(project
													.getBusinessType());
											bo.setOperationType(project
													.getOperationType());
											this.borrowerInfoDao.save(bo);
										} else {
											BorrowerInfo boPersistent = this.borrowerInfoDao
													.get(bo.getBorrowerInfoId());
											BeanUtils.copyProperties(
													boPersistent, bo);
											this.borrowerInfoDao
													.merge(boPersistent);
										}
										if (null != bo.getType()
												&& bo.getType() == 0) {
											if (null != bo.getCustomerId()) {
												Enterprise e = this.enterpriseDao
														.getById(bo
																.getCustomerId());
												e.setArea(bo.getAddress());
												e.setCciaa(bo.getCardNum());
												e
														.setTelephone(bo
																.getTelPhone());
												this.enterpriseDao.merge(e);
											}
										} else if (null != bo.getType()
												&& bo.getType() == 1) {
											Person p = this.personDao
													.getById(bo.getCustomerId());
											p.setPostaddress(bo.getAddress());
											p.setCardnumber(bo.getCardNum());
											p.setCellphone(bo.getTelPhone());
											this.personDao.merge(p);
										}
									}
								}
								// 保存第一还款来源
								String repaymentSource = flowRunInfo
										.getRequest().getParameter(
												"repaymentSource");
								List<SlRepaymentSource> listRepaymentSources = new ArrayList<SlRepaymentSource>(); // 第一还款来源
								if (null != repaymentSource
										&& !"".equals(repaymentSource)) {
									String[] repaymentSourceArr = repaymentSource
											.split("@");
									for (int i = 0; i < repaymentSourceArr.length; i++) {
										String str = repaymentSourceArr[i];
										JSONParser parser = new JSONParser(
												new StringReader(str));
										SlRepaymentSource slRepaymentSource = (SlRepaymentSource) JSONMapper
												.toJava(parser.nextValue(),
														SlRepaymentSource.class);
										listRepaymentSources
												.add(slRepaymentSource);
									}
								}
								if (listRepaymentSources.size() > 0) {
									for (SlRepaymentSource temp : listRepaymentSources) {
										boolean flag = StringUtil
												.isNumeric(temp.getTypeId());
										if (temp.getSourceId() == null) {
											GlobalType globalType = globalTypeDao
													.getByNodeKey(
															"repaymentSource")
													.get(0);
											if (!flag) {
												Dictionary dic = new Dictionary();
												dic.setItemValue(temp
														.getTypeId());
												dic.setItemName(globalType
														.getTypeName());
												dic.setProTypeId(globalType
														.getProTypeId());
												dic.setDicKey(temp.getTypeId());
												dic.setStatus("0");
												dictionaryDao.save(dic);
												temp
														.setTypeId(String
																.valueOf(dic
																		.getDicId()));
											} else {
												Dictionary cd = dictionaryDao
														.get(Long.valueOf(temp
																.getTypeId()));
												if (null == cd) {
													Dictionary dic = new Dictionary();
													dic.setItemValue(temp
															.getTypeId());
													dic.setItemName(globalType
															.getTypeName());
													dic.setProTypeId(globalType
															.getProTypeId());
													dic.setDicKey(temp
															.getTypeId());
													dic.setStatus("0");
													dictionaryDao.save(dic);
													temp
															.setTypeId(String
																	.valueOf(dic
																			.getDicId()));
												}
											}
											temp.setProjId(project
													.getProjectId());
											this.slRepaymentSourceDao
													.save(temp);
										} else {
											SlRepaymentSource rPersistent = this.slRepaymentSourceDao
													.get(temp.getSourceId());
											BeanUtil.copyNotNullProperties(
													rPersistent, temp);
											this.slRepaymentSourceDao
													.save(rPersistent);
										}
									}
								}

								if (null != degree && !"".equals(degree)) {
									project.setAppUserId(degree);
								}
								SlSmallloanProject persistent = this.dao
										.get(project.getProjectId());
								// add by lisl 2012-09-24
								// 更新项目信息时，companyId的值保持不变
								project.setCompanyId(persistent.getCompanyId());
								// end
								project.setStates(persistent.getStates());
								project.setIsOtherFlow(persistent
										.getIsOtherFlow());
								BeanUtils.copyProperties(project, persistent,
										new String[] { "id", "operationType",
												"flowType", "mineType",
												"mineId", "oppositeType",
												"oppositeID", "projectName",
												"projectNumber",
												"oppositeType", "businessType",
												"createDate" });
								if (isAheadPay != null) {
									persistent
											.setIsAheadPay(Short.valueOf("1"));
								} else {
									persistent
											.setIsAheadPay(Short.valueOf("0"));
								}

								Long projectId = project.getProjectId();

								// 款项计划
								String slFundIentJson = flowRunInfo
										.getRequest().getParameter(
												"fundIntentJsonData");
								String maxintentDate = slFundIntentService
										.savejson(slFundIentJson, projectId,
												"SmallLoan", Short
														.parseShort("1"),
												persistent.getCompanyId());
								// if(!persistent.getAccrualtype().equals("singleInterest")){
								// persistent.setPayintentPeriod(Integer.valueOf(sumintent));
								// }
								// 费用收支
								StatsPro statsPro = new StatsPro();
								statsPro.calcuProIntentDate(persistent);
								String slActualToChargeJson = flowRunInfo
										.getRequest().getParameter(
												"slActualToChargeJson");
								slActualToChargeService.savejson(
										slActualToChargeJson, projectId,
										"SmallLoan", Short.parseShort("0"),
										persistent.getCompanyId());
								Map<String, BigDecimal> map = slFundIntentService
										.saveProjectfiance(persistent
												.getProjectId(), "SmallLoan");
								persistent.setPaychargeMoney(map
										.get("paychargeMoney"));
								persistent.setIncomechargeMoney(map
										.get("incomechargeMoney"));
								persistent.setAccrualMoney(map
										.get("loanInterest"));
								persistent.setConsultationMoney(map
										.get("consultationMoney"));
								persistent.setServiceMoney(map
										.get("serviceMoney"));
								/**
								 * 年化净利率
								 */
								ProjectActionUtil pu = new ProjectActionUtil();
								pu.getSmallloanMode(persistent);
								this.dao.merge(persistent);

								Short flag = 0;
								if (persistent.getOppositeType().equals(
										"company_customer")) { // 企业
									flag = 0;
									Enterprise enterprise = new Enterprise();
									BeanUtil.populateEntity(flowRunInfo
											.getRequest(), enterprise,
											"enterprise");
									Enterprise epersistent = this.enterpriseDao
											.getById(enterprise
													.getId());
									epersistent.setEnterprisename(enterprise
											.getEnterprisename());
									epersistent.setArea(enterprise.getArea());
									epersistent.setShortname(enterprise
											.getShortname());
									epersistent.setHangyeType(enterprise
											.getHangyeType());
									epersistent.setOrganizecode(enterprise
											.getOrganizecode());
									epersistent.setCciaa(enterprise.getCciaa());
									epersistent.setTelephone(enterprise
											.getTelephone());
									epersistent.setPostcoding(enterprise
											.getPostcoding());
									enterpriseDao.merge(epersistent);

									Person person = new Person();
									BeanUtil.populateEntity(flowRunInfo
											.getRequest(), person, "person");
									Person ppersistent = this.personDao
											.getById(epersistent
													.getLegalpersonid());
									ppersistent.setMarry(person.getMarry());
									ppersistent.setName(person.getName());
									ppersistent.setSex(person.getSex());
									ppersistent.setCardtype(person
											.getCardtype());
									ppersistent.setCardnumber(person
											.getCardnumber());
									ppersistent.setTelphone(person
											.getTelphone());
									ppersistent.setPostcode(person
											.getPostcode());
									ppersistent.setSelfemail(person
											.getSelfemail());
									ppersistent.setPostaddress(person
											.getPostaddress());
									personDao.merge(ppersistent);

									if (listES.size() > 0) {
										for (int i = 0; i < listES.size(); i++) {
											EnterpriseShareequity es = listES
													.get(i);
											if (es.getId() == null) {
												es.setEnterpriseid(epersistent
														.getId());
												this.enterpriseShareequityDao
														.save(es);
											} else {
												EnterpriseShareequity esPersistent = this.enterpriseShareequityDao
														.load(es.getId());
												BeanUtils
														.copyProperties(
																es,
																esPersistent,
																new String[] {
																		"id",
																		"enterpriseid" });
												this.enterpriseShareequityDao
														.merge(esPersistent);
											}
										}
									}
								} else if (persistent.getOppositeType().equals(
										"person_customer")) {
									flag = 1;
									Person person = new Person();
									BeanUtil.populateEntity(flowRunInfo
											.getRequest(), person, "person");

									// 更新person信息开始
									Person persistentPerson = this.personDao
											.getById(person.getId());
									persistentPerson
											.setMarry(person.getMarry());
									persistentPerson.setName(person.getName());
									persistentPerson.setSex(person.getSex());
									persistentPerson.setCardtype(person
											.getCardtype());
									persistentPerson.setCardnumber(person
											.getCardnumber());
									persistentPerson.setTelphone(person
											.getTelphone());
									persistentPerson.setPostcode(person
											.getPostcode());
									persistentPerson.setSelfemail(person
											.getSelfemail());
									persistentPerson.setPostaddress(person
											.getPostaddress());
									personDao.merge(persistentPerson);
									// 更新person信息结束
									// 更新配偶信息
									if (person.getMarry() == 317) {
										Spouse spouse = new Spouse();
										BeanUtil
												.populateEntity(flowRunInfo
														.getRequest(), spouse,
														"spouse");
										if (spouse.getSpouseId() == null
												|| spouse.getSpouseId() == 0) {
											spouse.setPersonId(person.getId());
											creditBaseDao.saveDatas(spouse);
										} else {
											Spouse s = (Spouse) creditBaseDao
													.getById(
															Spouse.class,
															spouse
																	.getSpouseId());
											s.setCardnumber(spouse
													.getCardnumber());
											s.setCardtype(spouse.getCardtype());
											s.setCurrentcompany(spouse
													.getCurrentcompany());
											s.setDgree(spouse.getDgree());
											s.setJob(spouse.getJob());
											s.setLinkTel(spouse.getLinkTel());
											s.setName(spouse.getName());
											s.setPoliticalStatus(spouse
													.getPoliticalStatus());
											creditBaseDao.updateDatas(s);
										}
									} else {
										Spouse spouse = new Spouse();
										BeanUtil
												.populateEntity(flowRunInfo
														.getRequest(), spouse,
														"spouse");
										if (spouse.getSpouseId() != null
												&& spouse.getSpouseId() == 0) {
											Spouse s = (Spouse) creditBaseDao
													.getById(
															Spouse.class,
															spouse
																	.getSpouseId());
											creditBaseDao.deleteDatas(s);
										}
									}

								}
								EnterpriseBank enterpriseBank = new EnterpriseBank();
								BeanUtil.populateEntity(flowRunInfo
										.getRequest(), enterpriseBank,
										"enterpriseBank");
								if (enterpriseBank.getId() == null
										|| enterpriseBank.getId() == 0) {
									List<EnterpriseBank> list = enterpriseBankService
											.getBankList(
													persistent.getOppositeID()
															.intValue(), flag,
													Short.valueOf("0"),Short.valueOf("0"));
									for (EnterpriseBank e : list) {
										e.setIscredit(Short.valueOf("1"));
										creditBaseDao.updateDatas(e);
									}
									enterpriseBank.setEnterpriseid(persistent
											.getOppositeID().intValue());
									enterpriseBank.setIscredit(Short
											.valueOf("0"));
									enterpriseBank.setIsEnterprise(flag);
									enterpriseBank.setCompanyId(ContextUtil
											.getLoginCompanyId());
									enterpriseBank.setIsInvest(Short.valueOf("0"));
									creditBaseDao.saveDatas(enterpriseBank);
								} else {
									EnterpriseBank bank = (EnterpriseBank) creditBaseDao
											.getById(EnterpriseBank.class,
													enterpriseBank.getId());
									bank.setAccountnum(enterpriseBank
											.getAccountnum());
									bank.setAccountType(enterpriseBank
											.getAccountType());
									bank.setBankid(enterpriseBank.getBankid());
									bank.setBankname(enterpriseBank
											.getBankname());
									bank.setName(enterpriseBank.getName());
									bank.setOpenType(enterpriseBank
											.getOpenType());
									creditBaseDao.updateDatas(bank);
								}
								String sbhPartake = flowRunInfo.getRequest()
										.getParameter("sbhPartake");
								if (sbhPartake != null
										&& !"".equals(sbhPartake)) {
									String assignUserIds = sbhPartake;
									vars.put("flowAssignId", assignUserIds);
								}
							}
						}
					}
					String smallLoanFastSDH = flowRunInfo.getRequest()
							.getParameter("smallLoanFastSDH");
					SlSmallloanProject project_record = new SlSmallloanProject();
					BeanUtil.populateEntity(flowRunInfo.getRequest(),
							project_record, "slSmallloanProject");
					if (smallLoanFastSDH != null
							&& !"".equals(smallLoanFastSDH)) {
						vars.put("slfCreditCheckResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					} else {
						// =====小贷常规项目在贷款审查任务提交时生成贷款审查审批表开始==========（add by
						// liny 2013-2-21）
						String serverPath = AppUtil.getAppAbsolutePath();// 拿到当前项目的却对路径
						String businessType_ = project_record.getBusinessType();
						Long projectId_ = project_record.getProjectId();
						String categoryId = "0";// ProcreditContractCategory中的id
						String cconId = "0";// 合同表的id （contractId）
						String projectID_ = projectId_ + "";
						String _mark = "slSmallloadReviewTable";
						String htType = "slSmallloadReviewTable";
						String htmcdName = "贷款审查审批表";
						String projectNumber = "";
						String path = "", fileName = "", shortName = "";
						// ======查询视图看是否已经生成过了审批表开始
						List list = null;
						VProcreditContract vpcc = null;
						Object[] obj = { businessType_, projectID_, _mark };
						String hql = "from VProcreditContract where businessType =? and projId=? and htType=?";
						try {
							list = creditBaseDao.queryHql(hql, obj);
							if (list != null && list.size() > 0) {
								if (list.size() > 1) {
									vpcc = (VProcreditContract) list
											.get(list.size() - 1);
								} else if (list.size() == 0) {
									vpcc = (VProcreditContract) list
											.get(0);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (vpcc != null) {
							categoryId = vpcc.getId() + "";
							cconId = vpcc.getId() + "";
						}
						// =======查询视图看是否已经生成过了审批表结束

						DocumentTemplet doctemplet = documentTempletDao
								.findDocumentTemplet(_mark);// 模版的实体先查左侧数据
						DocumentTemplet templet_ = documentTempletDao
								.getTempletObj(doctemplet.getId());// 根据左侧模版的id
						// 查询右侧的文件
						if (null == templet_) {
							logger.error("没有模版，请上传模版");
						} else {
							String htlxdName = templet_.getText();
							String cType = templet_.getTemplettype() + "";
							int conId = Integer.parseInt(cconId);
							if (!templet_.isIsexist()) {
								logger.error("没有模版，请上传模版");
							} else {
								// 查询对应的模板文件是否上传
								boolean isFileExists = false;
								FileForm fileForm_ = fileFormService
										.getById(templet_.getFileid());
								DocumentTemplet templet = null;
								int tempId = templet_.getId();
								String ttempId = templet_.getId() + "";
								if (null != fileForm_) {
									String path_ = ElementUtil.replaceAllEleme(
											assureIntentBookElementCode,
											serverPath
													+ fileForm_.getFilepath(),
											fileForm_.getFilename());
									if (!"false".equals(path_)) {
										templet =documentTempletDao.getById(tempId);
										if (ttempId != null && templet != null) {
											DocumentTemplet dtemplet = documentTempletDao.getById(tempId);
											if (dtemplet != null) {// 只是单纯看一下数据库是否有模版文件记录
												if (dtemplet.getIsexist() == true
														&& dtemplet.getFileid() != 0) {// 只是单纯看一下数据库是否有模版文件记录是否有值
													FileForm fileF = fileFormService
															.getById(dtemplet
																	.getFileid());
													if (fileF != null) {
														File file = new File(
																serverPath
																		+ fileF
																				.getFilepath());
														if (file.exists()) {
															isFileExists = true;
														} else {
															logger
																	.error("没有模版，请上传模版文件");
														}
													}
												} else {
													logger
															.error("没有模版，请上传模版文件");
												}
											} else {
												logger.error("没有模版，请上传模版文件");
											}
										}
									} else {
										logger.error("没有模版，请上传模版");
									}
								}

								// 如果模板文件已经上传
								if (isFileExists == true) {
									ProcreditContract p = new ProcreditContract();
									p.setContractCategoryText(htlxdName);
									p.setContractCategoryTypeText(doctemplet
											.getText());
									p.setHtType(htType);// htType即使模版的唯一key值
									p.setTemptId(tempId);// 模版文件的Id
									p.setMortgageId(0);// 反担保ID 如果是反担保 这个菜有值
									// 不然就是0
									Long thirdRalationId = Long.parseLong("0");
									p.setIsApply(false);// 展期合同有用 是展期合同 则为true
									// 其他都为false
									if (categoryId == "0"
											|| "0".equals(categoryId)) {
										categoryId = procreditContractService.add(
												p, projectID_, businessType_)
												+ "";
									} else {
										if ("".equals(htlxdName)
												&& !"".equals(htmcdName)) {// 没选合同类型下拉菜单，只选了合同名称下拉菜单
											ProcreditContract pcc=procreditContractDao.getById(Integer.parseInt(categoryId));
											if(null!=pcc){
												pcc.setContractCategoryText(htmcdName);
												pcc.setTemptId(tempId);
												procreditContractDao.merge(pcc);
											}
											
										} else if (!"".equals(htlxdName)
												&& "".equals(htmcdName)) {// 正规操作情况下没有这种可能
											ProcreditContract pcc=procreditContractDao.getById(Integer.parseInt(categoryId));
											if(null!=pcc){
												pcc.setContractCategoryTypeText(htlxdName);
												procreditContractDao.merge(pcc);
											}
										} else if (!"".equals(htlxdName)
												&& !"".equals(htmcdName)) {
											ProcreditContract pcc=procreditContractDao.getById(Integer.parseInt(categoryId));
											if(null!=pcc){
												pcc.setContractCategoryTypeText(htlxdName);
												pcc.setContractCategoryText(htmcdName);
												pcc.setTemptId(tempId);
												procreditContractDao.merge(pcc);
											}
										}
									}

									// 合同编号
									String rnum = elementHandleService
											.getNumber(projectID_, ttempId);
									// 向合同表中增加或修改数据数据（由相应的合同id是否存在）
									conId = procreditContractService.makeUpload(
											cconId, ttempId, cType, categoryId,
											projectID_, thirdRalationId,
											businessType_, rnum, "");// 向合同表中添加数据
									SlSmallloanProject sloanProject = null;
									ProcreditContract pcontract =procreditContractService.getById(conId);
									sloanProject = dao.get(Long.valueOf(pcontract
													.getProjId()));
									projectNumber = sloanProject
											.getProjectNumber();
									if ((sloanProject.getOppositeType())
											.equals("company_customer")) {// 客户类型为企业
										Enterprise enterp =enterpriseDao.getById(Integer
														.parseInt(sloanProject
																.getOppositeID()
																.toString()));
										shortName = enterp.getShortname()
												.trim();
									} else if ((sloanProject.getOppositeType())
											.equals("person_customer")) {// 客户类型为个人
										Person person =personDao.getById(Integer
														.parseInt(sloanProject
																.getOppositeID()
																.toString()));
										shortName = person.getName().trim();
									}
									// 生成合同和合同文件的名字
									if (conId != 0) {
										fileName = pcontract.getContractName();
										if (null == fileName) {
											fileName = shortName + "-"
													+ projectNumber + "-"
													+ templet.getText() + "-"
													+ rnum;
										} else {
											fileName = shortName + "-"
													+ projectNumber + "-"
													+ templet.getText() + "-"
													+ rnum;
										}
									} else {
										fileName = shortName + "-"
												+ projectNumber + "-"
												+ templet.getText() + "-"
												+ rnum;
									}

									FileForm fileForm = fileFormService
											.getById(templet.getFileid());
									path = ElementUtil.getUrl(serverPath
											+ fileForm.getFilepath(), fileName
											+ ".doc");
									String fileCraName = shortName + "_"
											+ projectNumber;
									String copyDir = serverPath
											+ "attachFiles\\projFile\\contfolder\\"
											+ fileCraName + "\\"
											+ templet.getParentid() + "\\";
									File cFile = new File(copyDir);
									if (!cFile.exists()) {
										cFile.mkdirs();
									}
									String newPath = (copyDir + fileName + ".doc")
											.trim();
									String filePath = ("projFile/contfolder/"
											+ fileCraName + "/"
											+ templet.getParentid() + "/"
											+ fileName + ".doc").trim();
									Object[] obj2 = { true,
											"attachFiles/" + filePath, conId };
									elementHandleService
											.updateCon(
													"update ProcreditContract set isUpload=?, url=? where id =?",
													obj2);
									ProcreditContract pc=procreditContractDao.getById(conId);
									if(null!=pc){
										pc.setIsUpload(true);
										pc.setUrl("attachFiles/" + filePath);
										procreditContractDao.merge(pc);
									}
									List<FileAttach> listfileAttach = fileAttachDao.listByContractId(conId);
									if (listfileAttach != null) {
										for(FileAttach f:listfileAttach){
											f.setFilePath(filePath);
											fileAttachDao.merge(f);
										}
									} else {
										// 智维附件表操作start...为的是可以在线编辑
										FileAttach fileAttach = new FileAttach();
										fileAttach.setFileName(fileName);
										fileAttach.setFilePath(filePath);
										fileAttach.setCreatetime(new Date());
										fileAttach.setExt("doc");
										fileAttach
												.setFileType("attachFiles/uploads");
										fileAttach.setCreatorId(Long
												.parseLong(ContextUtil
														.getCurrentUser()
														.getId()));
										fileAttach
												.setCreator(ContextUtil
														.getCurrentUser()
														.getFullname());
										fileAttach.setDelFlag(0);
										fileAttach.setCsContractId(conId);
										fileAttachDao.save(fileAttach);
										// 智维附件表操作end...
									}
									SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
									// 贷款审查审批表文档 往贷款审查审批表文档要素里面放值的方法
									SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
											.getElementBySystem(projectID_,
													conId, tempId, htType,
													rnum, null);
									File file = new File(path);
									if (file.exists()) {
										JacobWord
												.getInstance()
												.replaceAllText(
														path,
														ElementUtil
																.findEleCodeArray(smallLoanElementCode),
														ElementUtil
																.findEleCodeValueArray(smallLoanElementCodeValue));
										FileHelper.copyFile(path, newPath);
										FileHelper.deleteFile(path);
									}

								}
							}
						}
						// =====小贷常规项目在贷款审查任务提交时生成贷款审查审批表结束==========
						vars.put("slnCreditCheckResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷款审查保存出错");
			return 0;
		}
	}

	/**
	 * 小贷常规审贷会集体决议提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer slnExaminationArrangement(FlowRunInfo flowRunInfo) {

		try {
			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");
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
						String smallLoanFastSDH = flowRunInfo.getRequest()
								.getParameter("smallLoanFastSDH");
						if (smallLoanFastSDH != null
								&& !"".equals(smallLoanFastSDH)) {
							vars.put("smallLoanFastSDH", "true");
						}
						vars.put("smallLoanNormalSDH", "false");
						vars
								.put("projectId", project.getProjectId()
										.toString());
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

	// 用于接收银行账户出错信息
	@Override
	public SlSmallloanProject findByprojectNumber(String projectNumber) {

		return dao.findByprojectNumber(projectNumber);
	}

	// 用与接收返回的银行账户错误信息来改变项目中的states字段值
	@Override
	public void reciveBankInfoErro(String projectNumber, String infoName) {
		SlSmallloanProject sl = new SlSmallloanProject();
		sl = dao.findByprojectNumber(projectNumber);
		if (infoName.equalsIgnoreCase("N")) {
			sl.setStates(Short.valueOf("2"));
			dao.save(sl);
		}

	}

	@Override
	public List<SlSmallloanProject> getListOfCustomer(String oppositeType,
			Long oppositeID) {

		return dao.getListOfCustomer(oppositeType, oppositeID);
	}

	/**
	 * 小贷常规、小额快速-放款签批提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer updateSmallLoanApprovalNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")
							|| transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao
								.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService
									.compareTaskSequence(
											currentForm.getRunId(), currentForm
													.getActivityName(),
											transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							} else {
								// =====小贷常规项目在贷款审查任务提交时生成贷款审查审批表开始==========（add
								// by liny 2013-2-21）
								String serverPath = AppUtil
										.getAppAbsolutePath();// 拿到当前项目的却对路径
								String businessType_ = "SmallLoan";
								Long projectId_ = Long
										.valueOf(flowRunInfo
												.getRequest()
												.getParameter(
														"slSmallloanProject.projectId"));
								String comments = flowRunInfo.getRequest()
										.getParameter("comments");
								String categoryId = "0";// ProcreditContractCategory中的id
								String cconId = "0";// 合同表的id （contractId）
								String projectID_ = projectId_ + "";
								String _mark = "LoanNotice";
								String htType = "LoanNotice";
								String htmcdName = "放款通知书";
								String projectNumber = "";
								String path = "", fileName = "", shortName = "";
								// ======查询视图看是否已经生成过了审批表开始
								List list = null;
								VProcreditContract vpcc = null;
								Object[] obj = { businessType_, projectID_,
										_mark };
								String hql = "from VProcreditContract where businessType =? and projId=? and htType=?";
								try {
									list = creditBaseDao.queryHql(hql, obj);
									if (list != null && list.size() > 0) {
										if (list.size() > 1) {
											vpcc = (VProcreditContract) list
													.get(list.size() - 1);
										} else if (list.size() == 0) {
											vpcc = (VProcreditContract) list
													.get(0);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								if (vpcc != null) {
									categoryId = vpcc.getId() + "";
									cconId = vpcc.getId() + "";
								}
								// =======查询视图看是否已经生成过了审批表结束

								DocumentTemplet doctemplet = documentTempletDao
										.findDocumentTemplet(_mark);// 模版的实体先查左侧数据
								DocumentTemplet templet_ = documentTempletDao
										.getTempletObj(doctemplet.getId());// 根据左侧模版的id
								// 查询右侧的文件
								if (null == templet_) {
									logger.error("没有模版，请上传模版");
								} else {
									String htlxdName = templet_.getText();
									String cType = templet_.getTemplettype()
											+ "";
									int conId = Integer.parseInt(cconId);
									if (!templet_.isIsexist()) {
										logger.error("没有模版，请上传模版");
									} else {
										// 查询对应的模板文件是否上传
										boolean isFileExists = false;
										FileForm fileForm_ = fileFormService
												.getById(templet_
														.getFileid());
										DocumentTemplet templet = null;
										int tempId = templet_.getId();
										String ttempId = templet_.getId() + "";
										if (null != fileForm_) {
											String path_ = ElementUtil
													.replaceAllEleme(
															assureIntentBookElementCode,
															serverPath
																	+ fileForm_
																			.getFilepath(),
															fileForm_
																	.getFilename());
											if (!"false".equals(path_)) {
												templet = documentTempletDao.getById(tempId);
												if (ttempId != null
														&& templet != null) {
													DocumentTemplet dtemplet =documentTempletDao.getById(tempId);
													if (dtemplet != null) {// 只是单纯看一下数据库是否有模版文件记录
														if (dtemplet
																.getIsexist() == true
																&& dtemplet
																		.getFileid() != 0) {// 只是单纯看一下数据库是否有模版文件记录是否有值
															FileForm fileF = fileFormService
																	.getById(dtemplet
																			.getFileid());
															if (fileF != null) {
																File file = new File(
																		serverPath
																				+ fileF
																						.getFilepath());
																if (file
																		.exists()) {
																	isFileExists = true;
																} else {
																	logger
																			.error("没有模版，请上传模版文件");
																}
															}
														} else {
															logger
																	.error("没有模版，请上传模版文件");
														}
													} else {
														logger
																.error("没有模版，请上传模版文件");
													}
												}
											} else {
												logger.error("没有模版，请上传模版");
											}
										}

										// 如果模板文件已经上传
										if (isFileExists == true) {
											ProcreditContract p = new ProcreditContract();
											p
													.setContractCategoryText(htlxdName);
											p
													.setContractCategoryTypeText(doctemplet
															.getText());
											p.setHtType(htType);// htType即使模版的唯一key值
											p.setTemptId(tempId);// 模版文件的Id
											p.setMortgageId(0);// 反担保ID 如果是反担保
											// 这个菜有值 不然就是0
											Long thirdRalationId = Long
													.parseLong("0");
											p.setIsApply(false);// 展期合同有用 是展期合同
											// 则为true
											// 其他都为false
											if (categoryId == "0"
													|| "0".equals(categoryId)) {
												categoryId = procreditContractService
														.add(p, projectID_,
																businessType_)
														+ "";
											} else {
												if ("".equals(htlxdName)
														&& !""
																.equals(htmcdName)) {// 没选合同类型下拉菜单，只选了合同名称下拉菜单
													Object[] obj1 = {
															htmcdName,
															tempId,
															Integer
																	.parseInt(categoryId) };
													elementHandleService
															.updateCon(
																	"update ProcreditContract set contractCategoryText=?, temptId=? where id =?",
																	obj1);
												} else if (!""
														.equals(htlxdName)
														&& "".equals(htmcdName)) {// 正规操作情况下没有这种可能
													Object[] obj1 = {
															htlxdName,
															Integer
																	.parseInt(categoryId) };
													elementHandleService
															.updateCon(
																	"update ProcreditContract set contractCategoryTypeText=? where id =?",
																	obj1);
												} else if (!""
														.equals(htlxdName)
														&& !""
																.equals(htmcdName)) {
													Object[] obj1 = {
															htlxdName,
															htmcdName,
															tempId,
															Integer
																	.parseInt(categoryId) };
													elementHandleService
															.updateCon(
																	"update ProcreditContract set contractCategoryTypeText=?, contractCategoryText=?, temptId=? where id =?",
																	obj1);
												}
											}

											// 合同编号
											//String rnum = elementHandleService.getNumber(projectID_,ttempId);
											// 向合同表中增加或修改数据数据（由相应的合同id是否存在）
											conId = procreditContractService
													.makeUpload(cconId,
															ttempId, cType,
															categoryId,
															projectID_,
															thirdRalationId,
															businessType_,
															null, "");// 向合同表中添加数据
											SlSmallloanProject sloanProject = null;
											ProcreditContract pcontract =procreditContractService.getById(conId);
											sloanProject = dao.get(Long.valueOf(pcontract.getProjId()));
											projectNumber = sloanProject
													.getProjectNumber();
											if ((sloanProject.getOppositeType())
													.equals("company_customer")) {// 客户类型为企业
												Enterprise enterp =enterpriseDao.getById(Integer
																.parseInt(sloanProject
																		.getOppositeID()
																		.toString()));
												shortName = enterp
														.getShortname().trim();
											} else if ((sloanProject
													.getOppositeType())
													.equals("person_customer")) {// 客户类型为个人
												Person person = personDao.getById(Integer
																.parseInt(sloanProject
																		.getOppositeID()
																		.toString()));
												shortName = person.getName()
														.trim();
											}
											// 生成合同和合同文件的名字
											if (conId != 0) {
												fileName = pcontract
														.getContractName();
												if (null == fileName) {
													fileName = shortName + "-"
															+ projectNumber
															+ "-"
															+ templet.getText()
															/*+ "-" + rnum*/;
												} else {
													fileName = shortName + "-"
															+ projectNumber
															+ "-"
															+ templet.getText()
															/*+ "-" + rnum*/;
												}
											} else {
												fileName = shortName + "-"
														+ projectNumber + "-"
														+ templet.getText()
														/*+ "-" + rnum*/;
											}

											FileForm fileForm = fileFormService
													.getById(templet
															.getFileid());
											path = ElementUtil
													.getUrl(
															serverPath
																	+ fileForm
																			.getFilepath(),
															fileName + ".doc");
											String fileCraName = shortName
													+ "_" + projectNumber;
											String copyDir = serverPath
													+ "attachFiles\\projFile\\contfolder\\"
													+ fileCraName + "\\"
													+ templet.getParentid()
													+ "\\";
											File cFile = new File(copyDir);
											if (!cFile.exists()) {
												cFile.mkdirs();
											}
											String newPath = (copyDir
													+ fileName + ".doc").trim();
											String filePath = ("projFile/contfolder/"
													+ fileCraName
													+ "/"
													+ templet.getParentid()
													+ "/" + fileName + ".doc")
													.trim();
											Object[] obj2 = { true,
													"attachFiles/" + filePath,
													conId };
											elementHandleService
													.updateCon(
															"update ProcreditContract set isUpload=?, url=? where id =?",
															obj2);
											List<FileAttach> listfileAttach = fileAttachDao
													.listByContractId(conId);
											if (listfileAttach != null) {
												for(FileAttach f:listfileAttach){
													f.setFilePath(filePath);
													fileAttachDao.merge(f);
												}
											} else {
												// 智维附件表操作start...为的是可以在线编辑
												FileAttach fileAttach = new FileAttach();
												fileAttach
														.setFileName(fileName);
												fileAttach
														.setFilePath(filePath);
												fileAttach
														.setCreatetime(new Date());
												fileAttach.setExt("doc");
												fileAttach
														.setFileType("attachFiles/uploads");
												fileAttach
														.setCreatorId(Long
																.parseLong(ContextUtil
																		.getCurrentUser()
																		.getId()));
												fileAttach
														.setCreator(ContextUtil
																.getCurrentUser()
																.getFullname());
												fileAttach.setDelFlag(0);
												fileAttach
														.setCsContractId(conId);
												fileAttachDao.save(fileAttach);
												// 智维附件表操作end...
											}
											SmallLoanElementCode smallLoanElementCode = new SmallLoanElementCode();
											// 贷款审查审批表文档 往贷款审查审批表文档要素里面放值的方法
											SmallLoanElementCode smallLoanElementCodeValue = elementHandleService
													.getElementBySystem(
															projectID_, conId,
															tempId, htType,
															null, comments);
											File file = new File(path);
											if (file.exists()) {
												JacobWord
														.getInstance()
														.replaceAllText(
																path,
																ElementUtil
																		.findEleCodeArray(smallLoanElementCode),
																ElementUtil
																		.findEleCodeValueArray(smallLoanElementCodeValue));
												FileHelper.copyFile(path,
														newPath);
												FileHelper.deleteFile(path);
											}

										}
									}
								}
								// =====小贷常规项目在贷款审查任务提交时生成贷款审查审批表结束==========
							}
						}
						String smallLoanFastSDH = flowRunInfo.getRequest()
								.getParameter("smallLoanFastSDH");
						if (smallLoanFastSDH != null
								&& !"".equals(smallLoanFastSDH)) {
							vars.put("slfLoanSignResult", transitionName);
							flowRunInfo.getVariables().putAll(vars);
						} else {
							vars.put("slnLoanSignResult", transitionName);
							flowRunInfo.getVariables().putAll(vars);
						}
					}
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * 微贷补录历史项目信息
	 */
	@Override
	public Integer updateMcroHistoryRecords(FlowRunInfo flowRunInfo) {

		try {
			String borrowerInfo = flowRunInfo.getRequest().getParameter(
					"borrowerInfo");
			String isAheadPay = flowRunInfo.getRequest().getParameter(
					"isAheadPay");
			String isPreposePayAccrual = flowRunInfo.getRequest().getParameter(
					"isPreposePayAccrualCheck");
			String degree = flowRunInfo.getRequest().getParameter("degree");

			String flowOver = flowRunInfo.getRequest().getParameter("flowOver");// 项目放款状态:1表示项目放款;2表示未放款,项目终止。
			String projectStatus = flowRunInfo.getRequest().getParameter(
					"projectStatus");
			List<BorrowerInfo> listBO = new ArrayList<BorrowerInfo>();
			if (null != borrowerInfo && !"".equals(borrowerInfo)) {
				String[] borrowerInfoArr = borrowerInfo.split("@");
				for (int i = 0; i < borrowerInfoArr.length; i++) {
					String str = borrowerInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						BorrowerInfo bo = (BorrowerInfo) JSONMapper.toJava(
								parser.nextValue(), BorrowerInfo.class);
						listBO.add(bo);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");

			if (listBO.size() > 0) {

				for (int i = 0; i < listBO.size(); i++) {
					BorrowerInfo bo = listBO.get(i);
					if (bo.getBorrowerInfoId() == null) {
						bo.setProjectId(project.getProjectId());
						bo.setBusinessType(project.getBusinessType());
						bo.setOperationType(project.getOperationType());
						this.borrowerInfoDao.save(bo);
					} else {
						BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo
								.getBorrowerInfoId());
						BeanUtils.copyProperties(boPersistent, bo);
						this.borrowerInfoDao.merge(boPersistent);
					}
					if (null != bo.getType() && bo.getType() == 0) {
						if (null != bo.getCustomerId()) {
							Enterprise e = this.enterpriseDao.getById(bo
									.getCustomerId());
							e.setArea(bo.getAddress());
							e.setCciaa(bo.getCardNum());
							e.setTelephone(bo.getTelPhone());
							this.enterpriseDao.merge(e);
						}
					} else if (null != bo.getType() && bo.getType() == 1) {
						Person p = this.personDao.getById(bo.getCustomerId());
						p.setPostaddress(bo.getAddress());
						p.setCardnumber(bo.getCardNum());
						p.setCellphone(bo.getTelPhone());
						this.personDao.merge(p);
					}
				}
			}

			String repaymentSource = flowRunInfo.getRequest().getParameter(
					"repaymentSource");
			List<SlRepaymentSource> listRepaymentSources = new ArrayList<SlRepaymentSource>(); // 第一还款来源
			if (null != repaymentSource && !"".equals(repaymentSource)) {
				String[] repaymentSourceArr = repaymentSource.split("@");
				for (int i = 0; i < repaymentSourceArr.length; i++) {

					try {
						String str = repaymentSourceArr[i];
						JSONParser parser = new JSONParser(
								new StringReader(str));
						SlRepaymentSource slRepaymentSource = (SlRepaymentSource) JSONMapper
								.toJava(parser.nextValue(),
										SlRepaymentSource.class);
						listRepaymentSources.add(slRepaymentSource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (listRepaymentSources.size() > 0) {

				for (SlRepaymentSource temp : listRepaymentSources) {

					boolean flag = StringUtil.isNumeric(temp.getTypeId());
					if (temp.getSourceId() == null) {
						GlobalType globalType = globalTypeDao.getByNodeKey(
								"repaymentSource").get(0);
						if (!flag) {

							Dictionary dic = new Dictionary();
							dic.setItemValue(temp.getTypeId());
							dic.setItemName(globalType.getTypeName());
							dic.setProTypeId(globalType.getProTypeId());
							dic.setDicKey(temp.getTypeId());
							dic.setStatus("0");
							dictionaryDao.save(dic);

							temp.setTypeId(String.valueOf(dic.getDicId()));
						} else {

							Dictionary cd = dictionaryDao.get(Long.valueOf(temp
									.getTypeId()));
							if (null == cd) {
								Dictionary dic = new Dictionary();
								dic.setItemValue(temp.getTypeId());
								dic.setItemName(globalType.getTypeName());
								dic.setProTypeId(globalType.getProTypeId());
								dic.setDicKey(temp.getTypeId());
								dic.setStatus("0");
								dictionaryDao.save(dic);
								temp.setTypeId(String.valueOf(dic.getDicId()));
							}
						}
						temp.setProjId(project.getProjectId());
						this.slRepaymentSourceDao.save(temp);
					} else {
						SlRepaymentSource rPersistent = this.slRepaymentSourceDao
								.get(temp.getSourceId());
						BeanUtil.copyNotNullProperties(rPersistent, temp);
						this.slRepaymentSourceDao.save(rPersistent);
					}
				}
			}

			if (null != degree && !"".equals(degree)) {
				project.setAppUserId(degree);
			}
			project.setProjectStatus(Short.valueOf(projectStatus));
			SlSmallloanProject persistent = this.dao
					.get(project.getProjectId());
			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
			project.setCompanyId(persistent.getCompanyId());
			// end
			project.setStates(persistent.getStates());
			project.setIsOtherFlow(persistent.getIsOtherFlow());
			BeanUtils.copyProperties(project, persistent, new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "oppositeType", "businessType",
					"createDate" });
			if (isAheadPay != null) {
				persistent.setIsAheadPay(Short.valueOf("1"));
			} else {
				persistent.setIsAheadPay(Short.valueOf("0"));
			}
			/*
			 * if(isPreposePayAccrual!=null){
			 * 
			 * persistent.setIsPreposePayAccrual(1); } else {
			 * persistent.setIsPreposePayAccrual(0);
			 * 
			 * }
			 */
			// 小额融资项目节点提交改变项目状态。
			if (null != flowOver) {
				/*
				 * if(Integer.parseInt(fangk)==1){
				 * 
				 * }else if(Integer.parseInt(fangk)==2){
				 * ////更新项目状态为3:终止,并且更新流程状态为终止。 VProcessRunData vp = null; Long
				 * projectId = persistent.getProjectId(); List<VProcessRunData>
				 * vpList =
				 * processRunDataService.getByTaskId(flowRunInfo.getTaskId());
				 * if(vpList!=null||vpList.size()!=0){ vp = vpList.get(0); }
				 * this.updateProcessRunStatus(vp.getRunId(), projectId); }
				 */
			}

			Long projectId = project.getProjectId();
			// 款项计划
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String sumintent = slFundIntentService.savejson(slFundIentJson,
					projectId, "SmallLoan", Short.parseShort("0"), persistent
							.getCompanyId());
			/*
			 * if(!persistent.getAccrualtype().equals("singleInterest")){
			 * persistent.setPayintentPeriod(Integer.valueOf(sumintent)); }
			 */
			// 费用收支

			String slActualToChargeJson = flowRunInfo.getRequest()
					.getParameter("slActualToChargeJson");
			slActualToChargeService.savejson(slActualToChargeJson, projectId,
					"SmallLoan", Short.parseShort("0"), persistent
							.getCompanyId());
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));
			/**
			 * 年化净利率
			 */
			ProjectActionUtil pu = new ProjectActionUtil();
			pu.getSmallloanMode(persistent);
			this.dao.merge(persistent);

			Person person = new Person();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");

			// 更新person信息开始
			Person persistentPerson = this.personDao.getById(person.getId());
			persistentPerson.setMarry(person.getMarry());
			persistentPerson.setName(person.getName());
			persistentPerson.setSex(person.getSex());
			persistentPerson.setCardtype(person.getCardtype());
			persistentPerson.setCardnumber(person.getCardnumber());
			persistentPerson.setCellphone(person.getCellphone());
			persistentPerson.setPostcode(person.getPostcode());
			persistentPerson.setBirthday(person.getBirthday());
			persistentPerson.setUnitaddress(person.getUnitaddress());
			persistentPerson.setCurrentcompany(person.getCurrentcompany());
			persistentPerson.setFamilyaddress(person.getFamilyaddress());
			persistentPerson.setHukou(person.getHukou());
			persistentPerson.setPostaddress(person.getPostaddress());
			personDao.merge(persistentPerson);

			// 更新person信息结束
			// 账号信息
			EnterpriseBank enterpriseBank = new EnterpriseBank();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,
					"enterpriseBank");
			if (enterpriseBank.getId() == null || enterpriseBank.getId() == 0) {
				List<EnterpriseBank> list = enterpriseBankService.getBankList(
						person.getId(), Short.valueOf("1"), Short.valueOf("0"),Short.valueOf("0"));
				for (EnterpriseBank e : list) {
					e.setIscredit(Short.valueOf("1"));
					creditBaseDao.updateDatas(e);
				}
				enterpriseBank.setOpenCurrency(Short.valueOf("0"));
				enterpriseBank.setEnterpriseid(person.getId());
				enterpriseBank.setIscredit(Short.valueOf("0"));
				enterpriseBank.setIsEnterprise(Short.valueOf("1"));
				enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
				enterpriseBank.setIsInvest(Short.valueOf("0"));
			
				creditBaseDao.saveDatas(enterpriseBank);
			} else {
				EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(
						EnterpriseBank.class, enterpriseBank.getId());
				if (bank.getOpenCurrency() == null) {
					bank.setOpenCurrency(Short.valueOf("0"));
				}
				bank.setAccountnum(enterpriseBank.getAccountnum());
				bank.setAccountType(enterpriseBank.getAccountType());
				bank.setBankid(enterpriseBank.getBankid());
				// bank.setBankname(enterpriseBank.getBankname());
				bank.setName(enterpriseBank.getName());
				bank.setOpenType(enterpriseBank.getOpenType());
				bank.setAreaId(enterpriseBank.getAreaId());
				bank.setAreaName(enterpriseBank.getAreaName());
				bank.setBankOutletsName(bank.getBankOutletsName());
				creditBaseDao.updateDatas(bank);
			}
			baseCustomService.getCustomToweb("1", persistentPerson.getId(), 0);
			// 配偶信息
			if (person.getMarry() == 317) {
				Spouse spouse = new Spouse();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
						"spouse");
				if (spouse.getSpouseId() == null || spouse.getSpouseId() == 0) {
					spouse.setPersonId(person.getId());
					creditBaseDao.saveDatas(spouse);
				} else {
					Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
							spouse.getSpouseId());
					s.setCardnumber(spouse.getCardnumber());
					s.setCardtype(spouse.getCardtype());
					s.setCurrentcompany(spouse.getCurrentcompany());
					s.setDgree(spouse.getDgree());
					s.setJob(spouse.getJob());
					s.setLinkTel(spouse.getLinkTel());
					s.setName(spouse.getName());
					s.setPoliticalStatus(spouse.getPoliticalStatus());
					creditBaseDao.updateDatas(s);
				}
			} else {
				Spouse spouse = new Spouse();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
						"spouse");
				if (spouse.getSpouseId() != null && spouse.getSpouseId() == 0) {
					Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
							spouse.getSpouseId());
					creditBaseDao.deleteDatas(s);
				}
			}
			/**
			 * 财务信息
			 
			FinanceInfo financeInfo = new FinanceInfo();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), financeInfo,
					"financeInfo");
			if (financeInfo.getFinanceInfoId() == null) {
				financeInfo.setProjectId(persistent.getProjectId());
				financeInfo.setBusinessType(persistent.getBusinessType());
				financeInfoDao.save(financeInfo);
			} else {
				FinanceInfo orgFinanceInfo = financeInfoDao.get(financeInfo
						.getFinanceInfoId());
				BeanUtil.copyNotNullProperties(orgFinanceInfo, financeInfo);
				orgFinanceInfo.setBusinessType(persistent.getBusinessType());
				financeInfoDao.save(orgFinanceInfo);

			}
			*/
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("微贷补录历史项目信息提交任务  信息出错:" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer updateFinalVeto(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				SlSmallloanProject project = new SlSmallloanProject();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
						"slSmallloanProject");
				String projectInMiddle = flowRunInfo.getRequest().getParameter(
						"projectInMiddle");
				if (projectInMiddle != null && !"".equals(projectInMiddle)) {
					SlSmallloanProject persistent1 = this.dao.get(project
							.getProjectId());
					persistent1
							.setProjectStatus(Short.valueOf(projectInMiddle));
					this.dao.merge(persistent1);
				}
			}

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List getprojectList(Long companyId, String startDate,
			String endDate, String status) {

		return dao.getprojectList(companyId, startDate, endDate, status);
	}

	@Override
	public Integer askForAlterAccrualProjectFlowNext(FlowRunInfo flowRunInfo) {

		try {

			if (flowRunInfo.isBack()) {

				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String projectId = flowRunInfo.getRequest().getParameter(
						"projectId_flow"); // 项目ID
				String fundIntentJsonData = flowRunInfo.getRequest()
						.getParameter("fundIntentJsonData");
				String businessType = flowRunInfo.getRequest().getParameter(
						"businessType_flow");
				String slAlteraccrualRecordId = flowRunInfo.getRequest()
						.getParameter("slAlteraccrualRecordId");

				slFundIntentService.savejsonloaned(fundIntentJsonData, Long
						.parseLong(projectId), businessType,
						Short.valueOf("1"), Short.valueOf("3"), null,
						"alterAccrual", Long.valueOf(slAlteraccrualRecordId));

				// if(transitionName!=null&&!"".equals(transitionName)){
				// Map<String,Object> vars=new HashMap<String, Object>();
				// vars.put("superviseResult",transitionName);
				// flowRunInfo.getVariables().putAll(vars);
				// }
				//			
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("利率变更申请执行下一步出错:" + e.getMessage());
			return 0;
		}

	}

	/**
	 * 提前还款流程-提前还款申请提交下一步
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
				String projectId = flowRunInfo.getRequest().getParameter(
						"projectId_flow"); // 项目ID
				String fundIntentJsonData = flowRunInfo.getRequest()
						.getParameter("fundIntentJsonData");
				String businessType = flowRunInfo.getRequest().getParameter(
						"businessType_flow");
				String slEarlyRepaymentId = flowRunInfo.getRequest()
						.getParameter("slEarlyRepaymentId");
				SlEarlyRepaymentRecord slEarlyRepaymentRecord_temp = new SlEarlyRepaymentRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(),
						slEarlyRepaymentRecord_temp, "slEarlyRepaymentRecord");
				slEarlyRepaymentRecord_temp.setProjectId(Long
						.valueOf(projectId));
				slEarlyRepaymentRecordService.save(slEarlyRepaymentRecord_temp);
				slFundIntentService.savejsonloaned(fundIntentJsonData, Long
						.parseLong(projectId), businessType,
						Short.valueOf("1"), Short.valueOf("4"), null,
						"earlyRepayment", Long.valueOf(slEarlyRepaymentId));
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("利率变更申请执行下一步出错:" + e.getMessage());
			return 0;
		}
	}

	@Override
	public Integer askForProjectFlowNext(FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Integer askForAlterAccrualAuditFlowNext(FlowRunInfo flowRunInfo) {
		try {

			if (flowRunInfo.isBack()) {

				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String flag = flowRunInfo.getRequest().getParameter("flag"); // 项目ID
				if (flag.equals("shenhe")) {
					String projectId = flowRunInfo.getRequest().getParameter(
							"projectId_flow"); // 项目ID
					SlSmallloanProject project = this.dao.get(Long
							.valueOf(projectId));
					if (project != null && "".equals(project)) {
						project.setIsOtherFlow(Short.valueOf("0"));
						this.dao.save(project);
					}
					if (transitionName != null && !"".equals(transitionName)) {
						Map<String, Object> vars = new HashMap<String, Object>();
						vars.put("auditingResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}
				if (flag.equals("shencha")) {
					if (transitionName != null && !"".equals(transitionName)) {
						Map<String, Object> vars = new HashMap<String, Object>();
						vars.put("examineResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}
				if (flag.equals("shenpi")) {
					if (transitionName != null && !"".equals(transitionName)) {

						String projectId = flowRunInfo.getRequest()
								.getParameter("projectId_flow"); // 项目ID
						SlSmallloanProject project = this.dao.get(Long
								.valueOf(projectId));
						project.setIsOtherFlow(Short.valueOf("0"));// 利率变更流程走完，把项目表的isOtherFlow字段值恢复为0，表示当前利率变更流程走完了。
						this.dao.save(project);
						String slAlteraccrualRecordId = flowRunInfo
								.getRequest().getParameter(
										"slAlteraccrualRecordId");

						List<SlFundIntent> listall = slFundIntentService
								.getByProjectId(Long.valueOf(projectId),
										"SmallLoan");
						for (SlFundIntent s : listall) {
							if (s.getAfterMoney()
									.compareTo(new BigDecimal("0")) == 0) {
								s.setIsValid(Short.valueOf("1"));
								this.slFundIntentDao.save(s);
							}

						}

						List<SlFundIntent> allintent = this.slFundIntentDao
								.getlistbyslslAlteraccrualRecordId(Long
										.valueOf(slAlteraccrualRecordId),
										"SmallLoan", Long.valueOf(projectId));
						for (SlFundIntent s : allintent) {
							s.setIsValid(Short.valueOf("0")); // 最后审批通过了，
							this.slFundIntentDao.save(s);
						}
						// 先把所有的变成无效，再在下面把新生成的变成有效，这样就
						// 这样就把新生产变成有效，之前没对过账变成无效

						Map<String, Object> vars = new HashMap<String, Object>();
						vars.put("approveResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public Integer askForAuditFlowNext(FlowRunInfo flowRunInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 提前还款流程-提前还款审批提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer askForEarlyRepaymentAuditFlowNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String flag = flowRunInfo.getRequest().getParameter("flag"); // 项目ID;
				if (flag.equals("shenpi")) {
					if (transitionName != null && !"".equals(transitionName)) {
						Map<String, Object> vars = new HashMap<String, Object>();
						if (transitionName.contains("终止")
								|| transitionName.contains("结束")) {
							String projectId = flowRunInfo.getRequest()
									.getParameter("projectId_flow");
							if (null != projectId && !"".equals(projectId)) {
								SlSmallloanProject project = this.dao.get(Long
										.valueOf(projectId));
								project.setIsOtherFlow(Short.valueOf("0"));
								this.dao.save(project);
							}
							flowRunInfo.setStop(true);
						} else {
							ProcessForm currentForm = processFormDao
									.getByTaskId(flowRunInfo.getTaskId());

							if (currentForm != null) {
								boolean isToNextTask = creditProjectService
										.compareTaskSequence(currentForm
												.getRunId(), currentForm
												.getActivityName(),
												transitionName);
								if (!isToNextTask) {
									flowRunInfo.setAfresh(true);// 表示打回重做
									ProcessForm processForm = processFormDao
											.getByRunIdFlowNodeKey(currentForm
													.getRunId(), "raotApplyFor");
									if (processForm != null
											&& processForm.getCreatorId() != null) {
										String creatorId = processForm
												.getCreatorId().toString();
										vars.put("flowAssignId", creatorId);
									}
								} else {/*
										 * String
										 * projectId=flowRunInfo.getRequest
										 * ().getParameter("projectId_flow");
										 * String
										 * slEarlyRepaymentId=flowRunInfo.
										 * getRequest
										 * ().getParameter("slEarlyRepaymentId"
										 * ); SlEarlyRepaymentRecord
										 * slEarlyRepaymentRecord_temp= new
										 * SlEarlyRepaymentRecord();
										 * BeanUtil.populateEntity
										 * (flowRunInfo.getRequest(),
										 * slEarlyRepaymentRecord_temp
										 * ,"slEarlyRepaymentRecord");
										 * SlSmallloanProject
										 * slSmallloanProject=
										 * dao.get(Long.valueOf(projectId));
										 * DateFormat sd=new
										 * SimpleDateFormat("yyyy-MM-dd");
										 * String date="";
										 * if(slSmallloanProject.
										 * getIsStartDatePay().equals("1")){
										 * Datedt=DateUtil.addMonthsToDate(
										 * slSmallloanProject.getStartDate(),
										 * slEarlyRepaymentRecord_temp
										 * .getPrepayintentPeriod());
										 * date=sd.format(dt);
										 * date=date.substring
										 * (0,date.lastIndexOf
										 * ("-")+1)+slSmallloanProject
										 * .getPayintentPerioDate(); }else{ Date
										 * dt=DateUtil.addDaysToDate(DateUtil.
										 * addMonthsToDate
										 * (slSmallloanProject.getStartDate(),
										 * slEarlyRepaymentRecord_temp
										 * .getPrepayintentPeriod()), -1);
										 * date=sd.format(dt); }
										 * List<SlFundIntent>
										 * slist=slFundIntentService
										 * .getListByIntentDate
										 * (slSmallloanProject.getProjectId(),
										 * slSmallloanProject.getBusinessType(),
										 * ">'"+date); for(SlFundIntent
										 * s:slist){
										 * if(null==s.getSlEarlyRepaymentId() ||
										 * !
										 * s.getSlEarlyRepaymentId().toString().
										 * equals(slEarlyRepaymentId)){
										 * s.setIsValid(Short.valueOf("1"));
										 * s.setIsCheck(Short.valueOf("1"));
										 * slFundIntentService.save(s); }
										 * 
										 * }
										 */
								}
							}

						}
						vars.put("raotExamineAndApproveResult", transitionName);
						flowRunInfo.getVariables().putAll(vars);
					}
				}

				if (flag.equals("fengxianshehe")) {
					if (transitionName != null && !"".equals(transitionName)) {

						String projectId = flowRunInfo.getRequest()
								.getParameter("projectId_flow"); // 项目ID
						String slAlteraccrualRecordId = flowRunInfo
								.getRequest().getParameter(
										"slAlteraccrualRecordId");

						List<SlFundIntent> listall = slFundIntentService
								.getByProjectId(Long.valueOf(projectId),
										"SmallLoan");
						for (SlFundIntent s : listall) {
							if (s.getAfterMoney()
									.compareTo(new BigDecimal("0")) == 0) {
								s.setIsValid(Short.valueOf("1"));
								this.slFundIntentDao.save(s);
							}

						}

						List<SlFundIntent> allintent = this.slFundIntentDao
								.getlistbyslslAlteraccrualRecordId(Long
										.valueOf(slAlteraccrualRecordId),
										"SmallLoan", Long.valueOf(projectId));
						for (SlFundIntent s : allintent) {
							s.setIsValid(Short.valueOf("0")); // 最后审批通过了
							this.slFundIntentDao.save(s);
						}
						// 先把所有的变成无效，再在下面把新生成的变成有效，这样就
						// 这样就把新生产变成有效，之前没对过账变成无效

					}
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}
	//启动资金匹配流程
	@Override
	public String startMatchingFunds(Long bidPlanId,String flowType,String subType){

		String customerName = "";
		PlBidPlan plan=plBidPlanDao.get(bidPlanId);
		Long projectId=null;
		Long fundProjectId=null;
		BpBusinessDirPro bdirpro =null;
		BpBusinessOrPro borpro = null;
		BpPersionDirPro pdirpro = null;
		BpPersionOrPro porpro =null;
		if(plan.getProType().equals("B_Dir")){
			bdirpro = bpBusinessDirProDao.get(plan.getBdirProId());
			projectId = bdirpro.getProId();
			fundProjectId=bdirpro.getMoneyPlanId();
		}else if(plan.getProType().equals("B_Or")){
			borpro = bpBusinessOrProDao.get(plan.getBorProId());
			projectId = borpro.getProId();
			fundProjectId=borpro.getMoneyPlanId();
		}else if(plan.getProType().equals("P_Dir")){
			pdirpro = bpPersionDirProDao.get(plan.getPDirProId());
			projectId = pdirpro.getProId();
			fundProjectId=pdirpro.getMoneyPlanId();
		}else if(plan.getProType().equals("P_Or")){
			porpro = bpPersionOrProDao.get(plan.getPOrProId());
			projectId = porpro.getProId();
			fundProjectId=porpro.getMoneyPlanId();
		}
		SlSmallloanProject project = this.dao.get(projectId);
		
		
		
		
		
		
		//project.setIsOtherFlow(Short.valueOf("7"));
		AppUser user = ContextUtil.getCurrentUser();
		//project.setProjectStatus(Short.valueOf("4"));
		//this.dao.merge(project);
		FlowRunInfo newFlowRunInfo = new FlowRunInfo();
		ProDefinition pdf = null;
		Long companyId = project.getCompanyId();
		Organization org = organizationService.get(companyId);
		String isGroupVersion = AppUtil.getSystemIsGroupVersion();
		if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
			if (null != project.getOperationType() && "SmallLoanBusiness".equals(project.getOperationType())) {
				pdf = proDefinitionDao.getByProcessName(flowType);

			}
		} else {
			pdf = proDefinitionDao.getByProcessName(flowType);
		}
		List<ProcessRun> processRunList = processRunDao
				.getByProcessNameProjectId(projectId,
						project.getBusinessType(), project.getFlowType());
		if (processRunList != null && processRunList.size() != 0) {
			customerName = processRunList.get(0).getCustomerName();
		}
		
		if(plan.getState()==4){
			List<InvestPersonInfo> plist=investPersonInfoService.getByBidPlanId(plan.getBidId());
			BigDecimal money=new BigDecimal(0);
			for(InvestPersonInfo p:plist){
				money=money.add(p.getInvestMoney());
			}
			if(plan.getProType().equals("B_Dir")){
			
				bdirpro.setSchemeStat(0);
				bpBusinessDirProDao.merge(bdirpro);
			}else if(plan.getProType().equals("B_Or")){
			
				borpro.setSchemeStat(0);
				bpBusinessOrProDao.merge(borpro);
			}else if(plan.getProType().equals("P_Dir")){
		
				pdirpro.setSchemeStat(0);
				bpPersionDirProDao.merge(pdirpro);
			}else if(plan.getProType().equals("P_Or")){
				
				porpro.setSchemeStat(0);
				bpPersionOrProDao.merge(porpro);
			}
			
			plan.setBidMoney(money);
			plan.setBidMoneyScale(Double.valueOf(100));
		}
		Long branchCompanyId = pdf.getBranchCompanyId();
		Map<String, Object> newVars = new HashMap<String, Object>();
		newVars.put("projectId", projectId);
		newVars.put("bidPlanId", bidPlanId);
		newVars.put("proType", plan.getProType());
		newVars.put("fundProjectId",fundProjectId);
		newVars.put("oppositeType", project.getOppositeType());
		newVars.put("businessType",  project.getBusinessType());
		newVars.put("operationType", project.getOperationType());
		newVars.put("subType", subType);
		newVars.put("customerName", customerName); //
		newVars.put("projectNumber", project.getProjectNumber()); //
		/*newVars.put("projectMoney", project.getProjectMoney());
		newVars.put("payProjectMoney", project.getPayProjectMoney());*/
		newVars.put("branchCompanyId", branchCompanyId==null?"1":branchCompanyId);
		newFlowRunInfo.getVariables().putAll(newVars);
		newFlowRunInfo.setBusMap(newVars);
		newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
		//招标项目的名字
		newFlowRunInfo.setFlowSubject(plan.getBidProName()+"-"+plan.getBidProNumber());
		ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
		String str = "";
		if (run != null && run.getPiId() != null) {
			str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),
					user.getUserId().toString(), project.getProjectName());
		}
		plan.setOriginalState(plan.getState());
		plan.setIsStart(1);
		plan.setState(PlBidPlan.STATE6);
		plBidPlanService.merge(plan);
		return str;
	}
	
	@Override
	public String startRenewalProcess(Long projectId) {

		String customerName = "";

		BpFundProject project = this.bpFundProjectDao.get(projectId);
		project.setIsOtherFlow(Short.valueOf("1"));
		AppUser user = ContextUtil.getCurrentUser();
		SlSuperviseRecord slSuperviseRecord = new SlSuperviseRecord();
		slSuperviseRecord.setProjectId(projectId);
		slSuperviseRecord.setBusinessType("ExhibitionBusiness");
		slSuperviseRecord.setBaseBusinessType(project.getBusinessType());// ◎
		slSuperviseRecord.setOpTime(new Date());
		slSuperviseRecord.setCreator(user.getUserId().toString());
		slSuperviseRecord.setCheckStatus(0);
		slSuperviseRecord.setDateMode(project.getDateMode());
		slSuperviseRecord.setAccrualtype(project.getAccrualtype());
		slSuperviseRecord.setPayaccrualType(project.getPayaccrualType());
		slSuperviseRecord.setIsPreposePayAccrualsupervise(project.getIsPreposePayAccrual());
		slSuperviseRecord.setIsInterestByOneTime(project.getIsInterestByOneTime());
		slSuperviseRecord.setIsStartDatePay(project.getIsStartDatePay());
		slSuperviseRecord.setPayintentPerioDate(project.getPayintentPerioDate());
		slSuperviseRecord.setContinuationRate(project.getAccrual());
		slSuperviseRecord.setYearAccrualRate(project.getYearAccrualRate());
		slSuperviseRecord.setDayAccrualRate(project.getDayAccrualRate());
		slSuperviseRecord.setManagementConsultingOfRate(project.getManagementConsultingOfRate());
		slSuperviseRecord.setDayManagementConsultingOfRate(project.getDayManagementConsultingOfRate());
		slSuperviseRecord.setYearManagementConsultingOfRate(project.getYearManagementConsultingOfRate());
		slSuperviseRecord.setFinanceServiceOfRate(project.getFinanceServiceOfRate());
		slSuperviseRecord.setDayFinanceServiceOfRate(project.getDayFinanceServiceOfRate());
		slSuperviseRecord.setYearFinanceServiceOfRate(project.getYearFinanceServiceOfRate());
		this.slSuperviseRecordDao.save(slSuperviseRecord);
		this.bpFundProjectDao.merge(project);
		FlowRunInfo newFlowRunInfo = new FlowRunInfo();
		ProDefinition pdf = null;
		// Map<String, String> mapNew =null;
		// 不能从session中获取companyId,总公司的人员为分公司启动展期流程的时候,这样获取启动的却是总公司的展期流程.
		// 而是从项目表中获取对应的companyId
		// Long companyId=ContextUtil.getLoginCompanyId();
		Long companyId = project.getCompanyId();
		Organization org = organizationService.get(companyId);
		String isGroupVersion = AppUtil.getSystemIsGroupVersion();
		if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
			if (null != project.getOperationType()
					&& "SmallLoanBusiness".equals(project.getOperationType())) {
				pdf = proDefinitionDao
						.getByProcessName("smallLoanPostponedFlow");

			}/*
			 * else if(null!=project.getOperationType() &&
			 * "MicroLoanBusiness".equals(project.getOperationType())){ pdf =
			 * proDefinitionDao
			 * .getByProcessName("microPostponedFlow_"+org.getKey());
			 * 
			 * }
			 */
		} else {
			pdf = proDefinitionDao.getByProcessName("smallLoanPostponedFlow");
		}
		List<ProcessRun> processRunList = processRunDao
				.getByProcessNameProjectId(projectId,
						project.getBusinessType(), project.getFlowType());
		if (processRunList != null && processRunList.size() != 0) {
			customerName = processRunList.get(0).getCustomerName();
		}
		List<SlFundIntent> slist = slFundIntentService.getByProjectId3(project
				.getProjectId(), "SmallLoan", "principalRepayment");
		Date intentDate = null;
		if (null != slist && slist.size() > 0) {
			SlFundIntent s = slist.get(0);
			intentDate = DateUtil.addDaysToDate(s.getIntentDate(), 1);
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Long branchCompanyId = pdf.getBranchCompanyId();
		Map<String, Object> newVars = new HashMap<String, Object>();
		newVars.put("fundProjectId", project.getId());
		newVars.put("sprojectId", project.getProjectId());
		newVars.put("projectId", slSuperviseRecord.getId());
		newVars.put("oppositeType", project.getOppositeType());
		newVars.put("businessType", "ExhibitionBusiness");
		newVars.put("baseBusinessType", project.getBusinessType());
		newVars.put("operationType", project.getOperationType());
		newVars.put("customerName", customerName); //
		newVars.put("projectNumber", project.getProjectNumber()); //
		newVars.put("branchCompanyId", branchCompanyId==null?"1":branchCompanyId);
		if (intentDate != null) {
			newVars.put("intentDate", sd.format(intentDate));
		}
		newFlowRunInfo.getVariables().putAll(newVars);
		newFlowRunInfo.setBusMap(newVars);
		newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
		newFlowRunInfo.setFlowSubject(project.getProjectName() + "-"
				+ project.getProjectNumber());
		ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
		String str = "";
		if (run != null && run.getPiId() != null) {
			str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),
					user.getUserId().toString(), project.getProjectName());
			
		}
		return str;
	}

	// 利率变更启动流程方法
	@Override
	public String startSlAlteraccrualProcess(Long projectId) {
		String customerName = "";
		BpFundProject project = this.bpFundProjectDao.get(projectId);
		project.setIsOtherFlow(Short.valueOf("3"));
		AppUser user = ContextUtil.getCurrentUser();
		SlAlterAccrualRecord slAlterAccrualRecord = new SlAlterAccrualRecord();
		slAlterAccrualRecord.setProjectId(project.getId());
		slAlterAccrualRecord.setBaseBusinessType("SmallLoan");// 〓
		slAlterAccrualRecord.setCheckStatus(0);
		slAlterAccrualRecord.setOpTime(new Date());
		slAlterAccrualRecord.setCreator(user.getFullname());// 添加创建利率变更的人id
		slAlterAccrualRecord.setOriaccrual(project.getAccrualnew());
		this.slAlterAccrualRecordDao.save(slAlterAccrualRecord);
		this.bpFundProjectDao.merge(project);
		FlowRunInfo newFlowRunInfo = new FlowRunInfo();
		ProDefinition pdf = null;// 流程定义key值
		// 不能从session中获取companyId,总公司的人员为分公司启动展期流程的时候,这样获取启动的却是总公司的展期流程.
		// 而是从项目表中获取对应的companyId
		// Long companyId=ContextUtil.getLoginCompanyId();
		Long companyId = project.getCompanyId();
		Organization org = organizationService.get(companyId);
		String isGroupVersion = AppUtil.getSystemIsGroupVersion();
		if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
			pdf = proDefinitionDao.getByProcessName("alterAccrualFlow_"
					+ org.getKey());
		} else {
			pdf = proDefinitionDao.getByProcessName("alterAccrualFlow");
		}

		// mapNew =
		// this.getProjectInfo(project,"alterAccrualFlow_"+org.getKey());
		List<ProcessRun> processRunList = processRunDao
				.getByProcessNameProjectId(projectId,
						project.getBusinessType(), project.getFlowType());
		if (processRunList != null && processRunList.size() != 0) {
			customerName = processRunList.get(0).getCustomerName();
		}
		Long branchCompanyId = pdf.getBranchCompanyId();

		Map<String, Object> newVars = new HashMap<String, Object>();
		newVars.put("projectId", project.getProjectId());
		newVars.put("fundProjectId", project.getId());
		// newVars.put("projectId",
		// slAlterAccrualRecord.getSlAlteraccrualRecordId());
		newVars.put("slAlteraccrualRecordId", slAlterAccrualRecord.getSlAlteraccrualRecordId());
		newVars.put("oppositeType", project.getOppositeType());
		newVars.put("businessType", project.getBusinessType());
		newVars.put("customerName", customerName); //
		newVars.put("projectNumber", project.getProjectNumber()); //
		newVars.put("branchCompanyId", branchCompanyId);
		newFlowRunInfo.getVariables().putAll(newVars);
		newFlowRunInfo.setBusMap(newVars);
		newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
		newFlowRunInfo.setFlowSubject(project.getProjectName() + "-"
				+ project.getProjectNumber());
		ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
		String str = "";
		if (run != null && run.getPiId() != null) {
			str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),
					user.getUserId().toString(), project.getProjectName());
		
		}
		return str;
	}

	/**
	 * 提前还款流程-提前还款款项确认提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer earlyRepaymentConfirmThePlanOfFundNextStep(
			FlowRunInfo flowRunInfo) {
		try {

			String projectId = flowRunInfo.getRequest().getParameter(
					"projectId_flow");
			String slEarlyRepaymentId = flowRunInfo.getRequest().getParameter(
					"slEarlyRepaymentId");
			String fundIntentJsonData = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String prepayintentPeriod1 = flowRunInfo.getRequest().getParameter(
					"prepayintentPeriod1");
			//update by gao 
			String slSuperviseRecordId =prepayintentPeriod1.lastIndexOf(".")!=-1? prepayintentPeriod1.substring(0,prepayintentPeriod1.lastIndexOf(".")):prepayintentPeriod1;
			
			SlEarlyRepaymentRecord slEarlyRepaymentRecord_temp = new SlEarlyRepaymentRecord();
			BeanUtil.populateEntity(flowRunInfo.getRequest(),
					slEarlyRepaymentRecord_temp, "slEarlyRepaymentRecord");
			slEarlyRepaymentRecord_temp.setProjectId(Long.valueOf(projectId));
			slEarlyRepaymentRecordService.save(slEarlyRepaymentRecord_temp);
			SlSmallloanProject slSmallloanProject = dao.get(Long
					.valueOf(projectId));
			DateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			List<SlFundIntent> slist = null;
			if (!slEarlyRepaymentRecord_temp.getAccrualtype().equals(
					"ontTimeAccrual")) {
				if (slSuperviseRecordId.equals("0")) {
					List<SlFundIntent> flist = slFundIntentService
							.getByIntentPeriod(Long.valueOf(projectId),
									"SmallLoan", "loanInterest", null,
									slEarlyRepaymentRecord_temp
											.getPrepayintentPeriod());
					if (null != flist && flist.size() > 0) {
						SlFundIntent sf = flist.get(0);
						slist = slFundIntentService.getListByIntentDate(
								slSmallloanProject.getProjectId(),
								slSmallloanProject.getBusinessType(), ">'"
										+ sf.getIntentDate(), null);
					}
				} else {
					List<SlFundIntent> flist = slFundIntentService
							.getByIntentPeriod(Long.valueOf(projectId),
									"SmallLoan", "loanInterest", Long
											.valueOf(slSuperviseRecordId),
									slEarlyRepaymentRecord_temp
											.getPrepayintentPeriod());
					if (null != flist && flist.size() > 0) {
						SlFundIntent sf = flist.get(0);
						slist = slFundIntentService.getListByIntentDate(
								slSmallloanProject.getProjectId(),
								slSmallloanProject.getBusinessType(), ">'"
										+ sf.getIntentDate(), Long
										.valueOf(slSuperviseRecordId));
					}
				}
			} else {
				if (slSuperviseRecordId.equals("0")) {
					slist = slFundIntentService.getListByIntentDate(
							slSmallloanProject.getProjectId(),
							slSmallloanProject.getBusinessType(), ">'"
									+ sd.format(slEarlyRepaymentRecord_temp
											.getEarlyDate()), null);
				} else {
					slist = slFundIntentService.getListByIntentDate(
							slSmallloanProject.getProjectId(),
							slSmallloanProject.getBusinessType(), ">'"
									+ sd.format(slEarlyRepaymentRecord_temp
											.getEarlyDate()), Long
									.valueOf(slSuperviseRecordId));
				}
			}
			List<SlFundIntent> ftlist = slFundIntentService
					.getlistbyslEarlyRepaymentId(Long
							.valueOf(slEarlyRepaymentId), "SmallLoan", Long
							.valueOf(projectId));
			if(null!=ftlist){
				for (SlFundIntent slFundIntent : ftlist) {
					slFundIntentService.evict(slFundIntent);
					if (slFundIntent.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						slFundIntentService.remove(slFundIntent);
					}
				}
			}
			
			if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {

				String[] fundIntentJsonArr = fundIntentJsonData.split("@");

				for (int i = 0; i < fundIntentJsonArr.length; i++) {
					String str = fundIntentJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
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
						SlFundIntent1.setIsValid(Short.valueOf("0"));
						SlFundIntent1.setIsCheck(Short.valueOf("0"));
						SlFundIntent1.setBusinessType("SmallLoan");
						SlFundIntent1.setCompanyId(slSmallloanProject
								.getCompanyId());
						SlFundIntent1.setSlEarlyRepaymentId(Long
								.valueOf(slEarlyRepaymentId));
						slFundIntentService.save(SlFundIntent1);
					}

				}
			}
			if(null!=slist){
				for (SlFundIntent s : slist) {
					if (null == s.getSlEarlyRepaymentId()
							|| !(s.getSlEarlyRepaymentId().toString()
									.equals(slEarlyRepaymentId))) {
						s.setEarlyOperateId(Long.valueOf(slEarlyRepaymentId));
						s.setIsValid(Short.valueOf("1"));
						s.setIsCheck(Short.valueOf("1"));
						slFundIntentService.save(s);
					}

				}
			}
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * 提前还款流程-提前还款风险审查提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer updateIsFlowOverNextStep(FlowRunInfo flowRunInfo) {
		try {
			String projectId = flowRunInfo.getRequest().getParameter(
					"projectId_flow");
			String slEarlyRepaymentId = flowRunInfo.getRequest().getParameter(
					"slEarlyRepaymentId_flow");
			if (null != projectId && !"".equals(projectId)) {
				SlSmallloanProject project = this.dao.get(Long
						.valueOf(projectId));
				project.setIsOtherFlow(Short.valueOf("0"));
				this.dao.save(project);
			}
			if (null != slEarlyRepaymentId && !"".equals(slEarlyRepaymentId)) {
				SlEarlyRepaymentRecord slEarlyRepaymentRecord = this.slEarlyRepaymentRecordService
						.get(Long.valueOf(slEarlyRepaymentId));
				slEarlyRepaymentRecord.setCheckStatus(5);
				this.slEarlyRepaymentRecordService.save(slEarlyRepaymentRecord);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String startSlEarlyRepaymentProcess(Long projectId) {
		String customerName = "";
		SlSmallloanProject project = this.dao.get(projectId);
		project.setIsOtherFlow(Short.valueOf("2"));
		AppUser user = ContextUtil.getCurrentUser();
		SlEarlyRepaymentRecord slEarlyRepaymentRecord = new SlEarlyRepaymentRecord();
		slEarlyRepaymentRecord.setProjectId(projectId);
		slEarlyRepaymentRecord.setCheckStatus(0);
		slEarlyRepaymentRecord.setOpTime(new Date());
		slEarlyRepaymentRecord.setCreator(user.getFullname());
		slEarlyRepaymentRecord.setBusinessType(project.getBusinessType());
		this.slEarlyRepaymentRecordService.save(slEarlyRepaymentRecord);
		this.dao.merge(project);
		FlowRunInfo newFlowRunInfo = new FlowRunInfo();
		ProDefinition pdf = null;// 流程定义key值
		// Map<String, String> mapNew =null;
		// 不能从session中获取companyId,总公司的人员为分公司启动展期流程的时候,这样获取启动的却是总公司的展期流程.
		// 而是从项目表中获取对应的companyId
		// Long companyId=ContextUtil.getLoginCompanyId();
		Long companyId = project.getCompanyId();
		Organization org = organizationService.get(companyId);
		String isGroupVersion = AppUtil.getSystemIsGroupVersion();
		if (isGroupVersion != null && Boolean.valueOf(isGroupVersion)) {
			pdf = proDefinitionDao.getByProcessName("repaymentAheadOfTimeFlow_"
					+ org.getKey());
		} else {
			pdf = proDefinitionDao.getByProcessName("repaymentAheadOfTimeFlow");
		}

		List<ProcessRun> processRunList = processRunDao
				.getByProcessNameProjectId(projectId,
						project.getBusinessType(), project.getFlowType());
		if (processRunList != null && processRunList.size() != 0) {
			customerName = processRunList.get(0).getCustomerName();
		}
		// mapNew =
		// this.getProjectInfo(project,"repaymentAheadOfTimeFlow_"+org.getKey());
		Long branchCompanyId = pdf.getBranchCompanyId();
		Map<String, Object> newVars = new HashMap<String, Object>();
		newVars.put("projectId", project.getProjectId());
		newVars.put("slEarlyRepaymentId", slEarlyRepaymentRecord
				.getSlEarlyRepaymentId());
		newVars.put("oppositeType", project.getOppositeType());
		newVars.put("businessType", project.getBusinessType());
		newVars.put("customerName", customerName); //
		newVars.put("projectNumber", project.getProjectNumber()); //
		List<VFundDetail> vlist = vFundDetailService.listByFundType(
				"principalRepayment", project.getProjectId(), "SmallLoan");
		BigDecimal money = new BigDecimal(0);
		for (VFundDetail vFundDetail : vlist) {
			money = money.add(new BigDecimal(vFundDetail.getAfterMoney()));
		}
		newVars.put("surplusnotMoney", project.getProjectMoney()
				.subtract(money));
		newFlowRunInfo.getVariables().putAll(newVars);
		newFlowRunInfo.setBusMap(newVars);
		newFlowRunInfo.setDefId(String.valueOf(pdf.getDefId()));
		newFlowRunInfo.setFlowSubject(project.getProjectName() + "-"
				+ project.getProjectNumber());
		ProcessRun run = this.jbpmService.doStartProcess(newFlowRunInfo);
		String str = "";
		if (run != null && run.getPiId() != null) {
			str = flowTaskService.currentTaskIsStartFlowUser(run.getPiId(),
					user.getUserId().toString(), project.getProjectName());
			/*
			 * String piId = run.getPiId(); String userId =user.getUserId()+"";
			 * PagingBean pb =new PagingBean(0,5); Boolean strs
			 * =judge(userId,"repaymentAheadOfTimeFlow_"
			 * +org.getKey(),pb,project.
			 * getProjectName(),project.getProjectNumber(),piId); if(strs){
			 * List<TaskImpl> list=flowTaskService.getTaskByExecutionId(piId);
			 * if(null!=list && list.size()>0){ Task task = (Task)list.get(0);
			 * if(task!=null){ String taskId = task.getId(); String activityName
			 * = task.getActivityName();
			 * str="taskId:'"+taskId+"',activityName:'"+activityName+"'"; } }
			 * }else{ str="taskId:'1',activityName:''"; }
			 */
		}
		return str;
	}

	/**
	 * 编辑展期项目
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer updatePostponedInfo(FlowRunInfo flowRunInfo, Long projectId,
			Long slSuperviseRecordId) {

		try {
			SlSmallloanProject project = dao.get(projectId);
			/*
			 * if(project!=null){ SlSmallloanProject newProject = new
			 * SlSmallloanProject();
			 * BeanUtil.populateEntity(flowRunInfo.getRequest(),
			 * newProject,"slSmallloanProject");
			 * project.setInfosourceId(newProject.getInfosourceId());
			 * project.setPurposeType(newProject.getPurposeType());
			 * project.setAssuretypeid(newProject.getAssuretypeid());
			 * project.setCustomerChannel(newProject.getCustomerChannel());
			 * project.setProductTypeId(newProject.getProductTypeId());
			 * dao.merge(project);
			 * 
			 * if (project.getOppositeType().equals("company_customer")){ // 企业
			 * Enterprise enterprise = new Enterprise();
			 * BeanUtil.populateEntity(flowRunInfo.getRequest(),
			 * enterprise,"enterprise"); Enterprise epersistent =
			 * this.enterprise1Dao.getEnterpriseById(enterprise.getId());
			 * epersistent.setEnterprisename(enterprise.getEnterprisename());
			 * epersistent.setArea(enterprise.getArea());
			 * epersistent.setShortname(enterprise.getShortname());
			 * epersistent.setHangyeType(enterprise.getHangyeType());
			 * epersistent.setOrganizecode(enterprise.getOrganizecode());
			 * epersistent.setCciaa(enterprise.getCciaa());
			 * epersistent.setTelephone(enterprise.getTelephone());
			 * epersistent.setPostcoding(enterprise.getPostcoding());
			 * epersistent.setRootHangYeType(enterprise.getRootHangYeType());
			 * 
			 * Person person = new Person();
			 * BeanUtil.populateEntity(flowRunInfo.getRequest(),
			 * person,"person"); if (null != person.getId() && person.getId() !=
			 * 0) { Person ppersistent = this.personDao
			 * .getPersonById(epersistent.getLegalpersonid());
			 * ppersistent.setMarry(person.getMarry());
			 * ppersistent.setName(person.getName());
			 * ppersistent.setSex(person.getSex());
			 * ppersistent.setCardtype(person.getCardtype());
			 * ppersistent.setCardnumber(person.getCardnumber());
			 * ppersistent.setCellphone(person.getCellphone());
			 * ppersistent.setPostcode(person.getPostcode());
			 * ppersistent.setSelfemail(person.getSelfemail());
			 * ppersistent.setPostaddress(person.getPostaddress());
			 * personDao.merge(ppersistent);
			 * baseCustomService.getCustomToweb("1", ppersistent.getId(),0); }
			 * else { Person p = new Person(); p.setMarry(person.getMarry());
			 * p.setName(person.getName()); p.setSex(person.getSex());
			 * p.setCardtype(person.getCardtype());
			 * p.setCardnumber(person.getCardnumber());
			 * p.setCellphone(person.getCellphone());
			 * p.setPostcode(person.getPostcode());
			 * p.setSelfemail(person.getSelfemail());
			 * p.setPostaddress(person.getPostaddress());
			 * p.setCompanyId(ContextUtil.getLoginCompanyId());
			 * personDao.save(p); epersistent.setLegalpersonid(p.getId());
			 * baseCustomService.getCustomToweb("1", p.getId(), 0); }
			 * enterprise1Dao.merge(epersistent); } else if
			 * (project.getOppositeType().equals("person_customer")) { Person
			 * person = new Person();
			 * BeanUtil.populateEntity(flowRunInfo.getRequest(),
			 * person,"person");
			 * 
			 * // 更新person信息开始 Person persistentPerson =
			 * this.personDao.getById(person.getId());
			 * persistentPerson.setMarry(person.getMarry());
			 * persistentPerson.setName(person.getName());
			 * persistentPerson.setSex(person.getSex());
			 * persistentPerson.setCardtype(person.getCardtype());
			 * persistentPerson.setCardnumber(person.getCardnumber());
			 * persistentPerson.setCellphone(person.getCellphone());
			 * persistentPerson.setPostcode(person.getPostcode());
			 * persistentPerson.setSelfemail(person.getSelfemail());
			 * persistentPerson.setPostaddress(person.getPostaddress());
			 * personDao.merge(persistentPerson); // 更新person信息结束 } }
			 */

			SlSuperviseRecord slSuperviseRecord = slSuperviseRecordDao
					.get(slSuperviseRecordId);
			if (slSuperviseRecord != null) {
				SlSuperviseRecord newRecord = new SlSuperviseRecord();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), newRecord,
						"slSuperviseRecord");
				BeanUtil.copyNotNullProperties(slSuperviseRecord, newRecord);
				slSuperviseRecordDao.merge(slSuperviseRecord);
			}
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			List<SlFundIntent> slFundIntentsAllsupervise = slFundIntentService
					.getlistbyslSuperviseRecordId(Long
							.valueOf(slSuperviseRecordId), "SmallLoan", Long
							.valueOf(projectId));
			for (SlFundIntent s : slFundIntentsAllsupervise) {
				if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
					slFundIntentService.remove(s);
				}
			}
			if (null != slFundIentJson && !"".equals(slFundIentJson)) {
				String[] slFundIentJsonArr = slFundIentJson.split("@");
				for (int i = 0; i < slFundIentJsonArr.length; i++) {
					String str = slFundIentJsonArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					SlFundIntent slFundIntent = (SlFundIntent) JSONMapper
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
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑展期项目出错：" + e.getMessage());
		}

		return 1;
	}

	/**
	 * 小贷标准流程-汇总审贷会意见提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer summaryApprovalOpinionsNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String slActualToChargeJson = flowRunInfo.getRequest()
						.getParameter("slActualToChargeJson");
				// String projectId =
				// flowRunInfo.getRequest().getParameter("projectId");
				String isAheadPay = flowRunInfo.getRequest().getParameter(
						"isAheadPay");
				SlSmallloanProject project = new SlSmallloanProject();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
						"slSmallloanProject");
				SlSmallloanProject persistent = this.dao.get(project
						.getProjectId());
				BeanUtils.copyProperties(project, persistent, new String[] {
						"id", "operationType", "flowType", "mineType",
						"mineId", "oppositeType", "oppositeID", "projectName",
						"projectNumber", "businessType", "createDate",
						"managementConsultingMineId", "financeServiceMineId",
						"appUserId" });
				if (isAheadPay != null) {
					persistent.setIsAheadPay(Short.valueOf("1"));
				} else {
					persistent.setIsAheadPay(Short.valueOf("0"));
				}
				this.dao.merge(persistent);
				slActualToChargeService.savejson(slActualToChargeJson, project
						.getProjectId(), project.getBusinessType(), Short
						.parseShort("1"), ContextUtil.getLoginCompanyId());
				String transitionName = flowRunInfo.getTransitionName();

				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止")
							|| transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao
								.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService
									.compareTaskSequence(
											currentForm.getRunId(), currentForm
													.getActivityName(),
											transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}
						}
					}
					vars.put("meettingCollectaneaResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷标准流程-汇总审贷会意见提交下一步出错：" + e.getMessage());
			return 0;
		}
	}

	/**
	 * 小贷标准流程-合同审核提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer contractReviewNextStep(FlowRunInfo flowRunInfo) {
		try {
			String transitionName = flowRunInfo.getTransitionName();
			if (transitionName != null && !"".equals(transitionName)) {
				Map<String, Object> vars = new HashMap<String, Object>();
				ProcessForm currentForm = processFormDao
						.getByTaskId(flowRunInfo.getTaskId());
				if (currentForm != null) {
					boolean isToNextTask = creditProjectService
							.compareTaskSequence(currentForm.getRunId(),
									currentForm.getActivityName(),
									transitionName);
					if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
						flowRunInfo.setAfresh(true);// 表示打回重做
						vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
						vars.put("targetActivityName", transitionName);// 打回的目标任务名称
					}
				}
				vars.put("contractCheckResult", transitionName);
				flowRunInfo.getVariables().putAll(vars);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷标准流程-合同审核提交下一步出错：" + e.getMessage());
			return 0;
		}
	}

	/**
	 * 小贷标准流程-办理抵质押及公证手续提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer handleMortgageNextStep(FlowRunInfo flowRunInfo) {
		try {
			List<SlFundIntent> list = new ArrayList<SlFundIntent>();
			int size = 0;
			String projectId = flowRunInfo.getRequest().getParameter(
					"projectId");
			String businessType = flowRunInfo.getRequest().getParameter(
					"businessType");
			SlSmallloanProject slSmallloanProject = this.get(Long
					.valueOf(projectId));
			SlFundIntent sf = new SlFundIntent();
			sf.setFundType(FundIntentListPro3.ProjectLoadOut);// 资金类型
			sf.setIntentDate(slSmallloanProject.getStartDate());
			sf.setPayMoney(slSmallloanProject.getProjectMoney()); // 支出金额
			sf.setIncomeMoney(BigDecimal.valueOf(0)); // 收入金额
			sf.setAfterMoney(new BigDecimal("0"));
			sf.setFlatMoney(new BigDecimal("0"));
			sf.setAccrualMoney(new BigDecimal("0"));
			sf.setRemark("");
			list.add(sf);
			for (SlFundIntent s : list) {
				s.setBusinessType("SmallLoan");
			}
			List<SlFundIntent> slList = slFundIntentDao.getByProjectId(Long
					.valueOf(projectId), "SmallLoan");
			if (slList.size() == 0 || slList == null) {
				for (SlFundIntent t : list) {
					try {
						t.setProjectId(slSmallloanProject.getProjectId());
						t.setProjectName(slSmallloanProject.getProjectName());
						t.setProjectNumber(slSmallloanProject
								.getProjectNumber());
						BigDecimal lin = new BigDecimal(0.00);
						if (t.getIncomeMoney().compareTo(lin) == 0) {
							t.setNotMoney(t.getPayMoney());
						} else {
							t.setNotMoney(t.getIncomeMoney());
						}
						t.setAfterMoney(new BigDecimal(0));
						t.setAccrualMoney(new BigDecimal(0));
						t.setFlatMoney(new BigDecimal(0));
						t.setIsValid(Short.valueOf("0"));
						t.setIsCheck(Short.valueOf("0"));
						t.setBusinessType("SmallLoan");
						t.setCompanyId(slSmallloanProject.getCompanyId());
						slFundIntentDao.save(t);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			List<SlActualToCharge> satcList = slActualToChargeDao
					.getlistbyslSuperviseRecordId(null, "SmallLoan", Long
							.valueOf(projectId));
			for (SlActualToCharge satc : satcList) {
				satc.setIsCheck(Short.valueOf("0"));
				slActualToChargeDao.save(satc);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷标准流程-办理抵质押及公证手续提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	@Override
	public Integer updateBpFundInfoNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String bidPlanId=flowRunInfo.getRequest().getParameter("bidPlanId");
				PlBidPlan plan=plBidPlanService.get(Long.valueOf(bidPlanId));
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						plan.setState(plan.getOriginalState());
						plan.setIsStart(null);
						plan.setStartIntentDate(null);
						plan.setEndIntentDate(null);
						plBidPlanDao.merge(plan);
						List<SlActualToCharge> alist=slActualToChargeDao.listByBidPlanId(plan.getBidId());
						for(SlActualToCharge s:alist){
							slActualToChargeDao.remove(s);
						}
						List<BpFundIntent> plist=bpFundIntentDao.getByBidPlanId(plan.getBidId());
						for(BpFundIntent f:plist){
							bpFundIntentDao.remove(f);
						}
						List<ProcreditContract> clist=procreditContractDao.getByPlanId(plan.getBidId());
						for(ProcreditContract c:clist){
							procreditContractDao.remove(c);
						}
						List<InvestPersonInfo> vlist=investPersonInfoService.getByBidPlanId(plan.getBidId());
						for(InvestPersonInfo p:vlist){
							p.setContractUrls(null);
							investPersonInfoService.merge(p);
						}
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								
								String pFundsDatas = flowRunInfo.getRequest().getParameter("pFundsDatas");
								String fundsJson=flowRunInfo.getRequest().getParameter("fundsJson");
								String chargeJson=flowRunInfo.getRequest().getParameter("chargeJson");
								String isCheck=flowRunInfo.getRequest().getParameter("isCheck");
								
								PlBidPlan plBidPlan=new PlBidPlan();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), plBidPlan, "plBidPlan");
								BeanUtil.copyNotNullProperties(plan, plBidPlan);
								BpFundProject fundProject =null;
								String bidPlanStatus=flowRunInfo.getRequest().getParameter("bidPlanStatus");
								if(plan.getProType().equals("P_Dir") || plan.getProType().equals("B_Dir")){
									// 平台资金款项信息
									String  platFormId=flowRunInfo.getRequest().getParameter("platFormBpFundProject.id");
									if(null!=platFormId && !platFormId.equals("")){
										fundProject= bpFundProjectDao.get(Long.valueOf(platFormId));
									}
									
								}
								if(plan.getProType().equals("P_Or") || plan.getProType().equals("B_Or")){
									
									// 自有资金款项信息
									String  ownId=flowRunInfo.getRequest().getParameter("ownBpFundProject.id");
									if(null!=ownId && !ownId.equals("")){
										fundProject = bpFundProjectDao.get(Long.valueOf(ownId));
									}
									
								}
								//投资人的放款收息表
								bpFundIntentDao.saveFundIntent(pFundsDatas, plan, fundProject, Short.valueOf(isCheck));
								
								
								//保存费用信息
								if(null!=chargeJson && !chargeJson.equals("")){
									slActualToChargeService.savejson(chargeJson, fundProject.getProjectId(), fundProject.getBusinessType(), Short.valueOf(isCheck), fundProject.getCompanyId());
								}
								if(transitionName.equals("完成")){
									List<SlActualToCharge> alist=slActualToChargeDao.listByBidPlanId(plan.getBidId());
									for(SlActualToCharge a:alist){
										a.setIsCheck(Short.valueOf("0"));
										slActualToChargeDao.merge(a);
									}
									List<SlFundIntent> slist=slFundIntentDao.getListByBidPlanId(plan.getBidId());
									for(SlFundIntent s:slist){
										s.setIsCheck(Short.valueOf("0"));
										slFundIntentDao.merge(s);
									}
									List<BpFundIntent> plist=bpFundIntentDao.getByBidPlanId(plan.getBidId());
									for(BpFundIntent p:plist){
										p.setIsCheck(Short.valueOf("0"));
										bpFundIntentDao.merge(p);
									}
									
									if(bidPlanStatus !=null&&!"".equals(bidPlanStatus )&&!"undefined".equals(bidPlanStatus )){
										changeStatusBidPlan(Long.parseLong(bidPlanId), Integer.valueOf(bidPlanStatus));
									}
								}
								plBidPlanDao.merge(plan);
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
	 * 小贷标准流程-贷款发放提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer creditLoanNextStep(FlowRunInfo flowRunInfo) {
		try {
			String transitionName = flowRunInfo.getTransitionName();

			if (transitionName != null && !"".equals(transitionName)) {
				Map<String, Object> vars = new HashMap<String, Object>();
				if (transitionName.contains("终止")
						|| transitionName.contains("结束")) {
					String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
					if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
						SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
						sp.setProjectStatus((short)3);
						dao.merge(sp);
					}
					flowRunInfo.setStop(true);
				} else {
					ProcessForm currentForm = processFormDao
							.getByTaskId(flowRunInfo.getTaskId());
					if (currentForm != null) {
						boolean isToNextTask = creditProjectService
								.compareTaskSequence(currentForm.getRunId(),
										currentForm.getActivityName(),
										transitionName);
						if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
							flowRunInfo.setAfresh(true);// 表示打回重做
							vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
							vars.put("targetActivityName", transitionName);// 打回的目标任务名称
						} else {
							List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
							String fundIntentJsonData = flowRunInfo
									.getRequest().getParameter(
											"fundIntentJsonData");
							SlSmallloanProject project = new SlSmallloanProject();
							BeanUtil.populateEntity(flowRunInfo.getRequest(),
									project, "slSmallloanProject");
							String projectId = flowRunInfo.getRequest()
									.getParameter("slProjectId");
							SlSmallloanProject persistent = this.dao.get(Long
									.valueOf(projectId));
							persistent.setStartDate(project.getStartDate());
							persistent
									.setIntentDate(persistent.getIntentDate());
							this.dao.save(persistent);
							if (null != fundIntentJsonData
									&& !"".equals(fundIntentJsonData)) {
								String[] shareequityArr = fundIntentJsonData
										.split("@");
								for (int i = 0; i < shareequityArr.length; i++) {
									String str = shareequityArr[i];
									JSONParser parser = new JSONParser(
											new StringReader(str));
									SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper
											.toJava(parser.nextValue(),
													SlFundIntent.class);
									SlFundIntent1.setProjectId(persistent
											.getProjectId());
									SlFundIntent1.setProjectName(persistent
											.getProjectName());
									SlFundIntent1.setProjectNumber(persistent
											.getProjectNumber());
									Short isvalid = 0;
									if (null == SlFundIntent1.getFundIntentId()) {

										BigDecimal lin = new BigDecimal(0.00);
										if (SlFundIntent1.getIncomeMoney()
												.compareTo(lin) == 0) {
											SlFundIntent1
													.setNotMoney(SlFundIntent1
															.getPayMoney());
										} else {
											SlFundIntent1
													.setNotMoney(SlFundIntent1
															.getIncomeMoney());
										}
										SlFundIntent1
												.setAfterMoney(new BigDecimal(0));
										SlFundIntent1
												.setAccrualMoney(new BigDecimal(
														0));
										SlFundIntent1
												.setFlatMoney(new BigDecimal(0));
										SlFundIntent1.setIsValid(isvalid);
										if (SlFundIntent1.getFundType().equals(
												"principalLending")) {
											SlFundIntent1.setIsCheck(Short
													.valueOf("0"));
										} else {
											SlFundIntent1.setIsCheck(Short
													.valueOf("1"));
										}
										SlFundIntent1
												.setBusinessType("SmallLoan");
										SlFundIntent1.setCompanyId(persistent
												.getCompanyId());
										slFundIntentDao.save(SlFundIntent1);
									} else {
										BigDecimal lin = new BigDecimal(0.00);
										if (SlFundIntent1.getIncomeMoney()
												.compareTo(lin) == 0) {
											SlFundIntent1
													.setNotMoney(SlFundIntent1
															.getPayMoney());
										} else {
											SlFundIntent1
													.setNotMoney(SlFundIntent1
															.getIncomeMoney());
										}
										SlFundIntent1.setIsValid(isvalid);
										if (SlFundIntent1.getFundType().equals(
												"principalLending")) {
											SlFundIntent1.setIsCheck(Short
													.valueOf("0"));
										} else {
											SlFundIntent1.setIsCheck(Short
													.valueOf("1"));
										}
										SlFundIntent1
												.setBusinessType("SmallLoan");
										SlFundIntent1.setCompanyId(persistent
												.getCompanyId());
										SlFundIntent slFundIntent2 = slFundIntentService
												.get(SlFundIntent1
														.getFundIntentId());
										BeanUtil.copyNotNullProperties(
												slFundIntent2, SlFundIntent1);
										slFundIntentDao.merge(slFundIntent2);
									}
								}
							}
						}
					}
				}
				vars.put("creditLoanResult", transitionName);
				flowRunInfo.getVariables().putAll(vars);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷标准流程-贷款发放提交下一步出错：" + e.getMessage());
			return 0;
		}
	}

	/**
	 * 小贷标准流程-款项计划确认提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer paymentPlanRecognitionNextStep(FlowRunInfo flowRunInfo) {

		String transitionName = flowRunInfo.getTransitionName();
		List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
		String fundIntentJsonData = flowRunInfo.getRequest().getParameter(
				"fundIntentJsonData");
		String slActualToChargeJson = flowRunInfo.getRequest().getParameter(
				"slActualToChargeJson");
		String projectId = flowRunInfo.getRequest().getParameter("slProjectId");
		SlSmallloanProject project = new SlSmallloanProject();

		try {
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");
			SlSmallloanProject persistent = this.dao.get(Long
					.valueOf(projectId));
			slActualToChargeService.savejson(slActualToChargeJson, Long
					.valueOf(projectId), persistent.getBusinessType(), Short
					.parseShort("0"), ContextUtil.getLoginCompanyId());
			BeanUtils.copyProperties(project, persistent, new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "businessType", "createDate",
					"managementConsultingMineId", "financeServiceMineId",
					"appUserId", "isAheadPay" });
			this.dao.merge(persistent);
			List<SlFundIntent> oldList = slFundIntentDao.getByProjectId(Long
					.valueOf(projectId), persistent.getBusinessType());
			for (SlFundIntent sfi : oldList) {
				if (!sfi.getFundType().equals("principalLending")) {
					slFundIntentDao.remove(sfi);
				}
			}

			if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
				String[] shareequityArr = fundIntentJsonData.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					SlFundIntent SlFundIntent1 = (SlFundIntent) JSONMapper
							.toJava(parser.nextValue(), SlFundIntent.class);
					SlFundIntent1.setProjectId(persistent.getProjectId());
					SlFundIntent1.setProjectName(persistent.getProjectName());
					SlFundIntent1.setProjectNumber(persistent
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
						SlFundIntent1.setIsCheck(Short.valueOf("0"));
						SlFundIntent1.setBusinessType(persistent
								.getBusinessType());
						SlFundIntent1.setCompanyId(persistent.getCompanyId());
						slFundIntentDao.save(SlFundIntent1);
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
						if (slFundIntent2 != null) {
							if (slFundIntent2.getAfterMoney().compareTo(
									new BigDecimal(0)) == 0) {
								BeanUtil.copyNotNullProperties(slFundIntent2,
										SlFundIntent1);
								SlFundIntent1.setBusinessType(persistent
										.getBusinessType());
								SlFundIntent1.setCompanyId(persistent
										.getCompanyId());
								SlFundIntent1.setIsCheck(Short.valueOf("0"));
								slFundIntentDao.merge(SlFundIntent1);
							}
						}
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷标准流程-款项计划确认提交下一步出错：" + e.getMessage());
			return 0;
		}
	}

	/**
	 * 小贷标准流程-合同签署提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer contractSigningNextStep(FlowRunInfo flowRunInfo) {
		try {
			String transitionName = flowRunInfo.getTransitionName();
			if (transitionName != null && !"".equals(transitionName)) {
				Map<String, Object> vars = new HashMap<String, Object>();
				if (transitionName.contains("终止")
						|| transitionName.contains("结束")) {
					String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
					if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
						SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
						sp.setProjectStatus((short)3);
						dao.merge(sp);
					}
					flowRunInfo.setStop(true);
				}
				vars.put("signContractResult", transitionName);
				flowRunInfo.getVariables().putAll(vars);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷标准流程-合同签署提交下一步出错：" + e.getMessage());
			return 0;
		}
	}

	/*
	 * public TaskImpl judge(String userId,String processName,PagingBean
	 * pb,String projectName,String projectNumber,String piId){ TaskImpl
	 * taskImpl = null; List<TaskImpl> list =
	 * taskDao.getTasksByUserIdProcessName
	 * (userId,processName,pb,projectName,projectNumber,piId);
	 * if(list!=null&&list.size()>0){ taskImpl = list.get(0); } return taskImpl;
	 * }
	 */

	/**
	 * 小贷标准流程-上传会议纪要提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer uploadMeetingSummaryNextStep(FlowRunInfo flowRunInfo) {

		try
		 {       
			    if(flowRunInfo.isBack()){
			    	return 1;
			    }else{
			    	String transitionName = flowRunInfo.getTransitionName();
			    	Map<String,Object> vars=new HashMap<String, Object>();
			      	if(transitionName!=null&&!"".equals(transitionName)){
			    		if(transitionName.contains("终止")||transitionName.contains("结束")){
			    			String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
							if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
								SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
								sp.setProjectStatus((short)3);
								dao.merge(sp);
							}
			    			flowRunInfo.setStop(true);
			    		}else{
				      		
				      		ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
				      		
					    	if(currentForm!=null){
					    		boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(), transitionName);
					    		if(!isToNextTask){//true表示流程正常往下流转,false则表示打回。
					    			flowRunInfo.setAfresh(true);//表示打回重做
					    			ProcessForm processForm = processFormDao.getByRunIdFlowNodeKey(currentForm.getRunId(), "submitTheFiling");
				    				if(processForm!=null&&processForm.getCreatorId()!=null){
				    					String creatorId = processForm.getCreatorId().toString();
				    					vars.put("flowAssignId",creatorId);
				    				}
					    		}else{
					    			SlConferenceRecord slConferenceRecord = new SlConferenceRecord();
					    			BeanUtil.populateEntity(flowRunInfo.getRequest(),
					    					slConferenceRecord, "slConferenceRecord");
					    			if (slConferenceRecord.getConforenceId() == null) {
					    				slConferenceRecordDao.save(slConferenceRecord);
					    				Long conforenceId = slConferenceRecord.getConforenceId();
					    			} else {
					    				SlConferenceRecord orgSlConferenceRecord = slConferenceRecordDao
					    						.get(slConferenceRecord.getConforenceId());
					    				BeanUtil.copyNotNullProperties(orgSlConferenceRecord,
					    						slConferenceRecord);
					    				slConferenceRecordDao.save(orgSlConferenceRecord);
					    			}
					    		}
					    	}
					    	
			    		}
			    		vars.put("slnRiskManagerCheckResult",transitionName);
						flowRunInfo.getVariables().putAll(vars);
			    	}
			    	return 1;
			    }
		 }
		 catch (Exception e) {
			  e.printStackTrace();
			  return 0;
		}
	
		
	}

	/**
	 * 小贷标准流程-项目归档提交下一步
	 * 
	 * @param flowRunInfo
	 * @return
	 */
	public Integer projectArchiveNextStep(FlowRunInfo flowRunInfo) {
		try {
			String projectId = flowRunInfo.getRequest().getParameter(
					"slProjectId");
			SlSmallloanProject slSmallloanProject = this.get(Long
					.valueOf(projectId));
			slSmallloanProject
					.setProjectStatus(Constants.PROJECT_STATUS_MIDDLE);
			this.save(slSmallloanProject);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Boolean judge(String userId, String processName, PagingBean pb,
			String projectName, String projectNumber, String piId) {
		Boolean str = false;
		List<TaskImpl> list = taskDao.getTasksByUserIdProcessName(userId,
				processName, pb, projectName, projectNumber, piId);
		if (list != null && list.size() > 0) {
			str = true;
		}
		return str;
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
					if (transitionName.contains("终止")
							|| transitionName.contains("结束")) {
						String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
						if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
							SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
							sp.setProjectStatus((short)3);
							dao.merge(sp);
						}
						flowRunInfo.setStop(true);
					} else {
						ProcessForm currentForm = processFormDao
								.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService
									.compareTaskSequence(
											currentForm.getRunId(), currentForm
													.getActivityName(),
											transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}
						}
					}
					vars.put("generalManagerCheckResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("小贷标准-总经理审批提交下一步出错：" + e.getMessage());
			return 0;
		}
	}

	private Integer saveBaseInfo(FlowRunInfo flowRunInfo) {
		try {
			String borrowerInfo = flowRunInfo.getRequest().getParameter(
					"borrowerInfo");
			String gudongInfo = flowRunInfo.getRequest().getParameter(
					"gudongInfo");
			String isAheadPay = flowRunInfo.getRequest().getParameter(
					"isAheadPay");
			String isPreposePayAccrual = flowRunInfo.getRequest().getParameter(
					"isPreposePayAccrualCheck");
			String degree = flowRunInfo.getRequest().getParameter("degree");

			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");

			// 保存共同借款人信息
			List<BorrowerInfo> listBO = new ArrayList<BorrowerInfo>();
			if (null != borrowerInfo && !"".equals(borrowerInfo)) {
				String[] borrowerInfoArr = borrowerInfo.split("@");
				for (int i = 0; i < borrowerInfoArr.length; i++) {
					String str = borrowerInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						BorrowerInfo bo = (BorrowerInfo) JSONMapper.toJava(
								parser.nextValue(), BorrowerInfo.class);
						listBO.add(bo);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 保存股东信息
			List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
			if (null != gudongInfo && !"".equals(gudongInfo)) {
				String[] shareequityArr = gudongInfo.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper
								.toJava(parser.nextValue(),
										EnterpriseShareequity.class);
						listES.add(enterpriseShareequity);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (listBO.size() > 0) {
				for (int i = 0; i < listBO.size(); i++) {
					BorrowerInfo bo = listBO.get(i);
					if (bo.getBorrowerInfoId() == null) {
						bo.setProjectId(project.getProjectId());
						bo.setBusinessType(project.getBusinessType());
						bo.setOperationType(project.getOperationType());
						this.borrowerInfoDao.save(bo);
					} else {
						BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo
								.getBorrowerInfoId());
						BeanUtils.copyProperties(boPersistent, bo);
						this.borrowerInfoDao.merge(boPersistent);
					}
					if (null != bo.getType() && bo.getType() == 0) {
						if (null != bo.getCustomerId()) {
							Enterprise e = this.enterpriseDao.getById(bo
									.getCustomerId());
							e.setArea(bo.getAddress());
							e.setCciaa(bo.getCardNum());
							e.setTelephone(bo.getTelPhone());
							this.enterpriseDao.merge(e);
						}
					} else if (null != bo.getType() && bo.getType() == 1) {
						Person p = this.personDao.getById(bo.getCustomerId());
						p.setPostaddress(bo.getAddress());
						p.setCardnumber(bo.getCardNum());
						p.setCellphone(bo.getTelPhone());
						this.personDao.merge(p);
					}
				}
			}
			// 保存第一还款来源
			String repaymentSource = flowRunInfo.getRequest().getParameter(
					"repaymentSource");
			List<SlRepaymentSource> listRepaymentSources = new ArrayList<SlRepaymentSource>(); // 第一还款来源
			if (null != repaymentSource && !"".equals(repaymentSource)) {
				String[] repaymentSourceArr = repaymentSource.split("@");
				for (int i = 0; i < repaymentSourceArr.length; i++) {
					try {
						String str = repaymentSourceArr[i];
						JSONParser parser = new JSONParser(
								new StringReader(str));
						SlRepaymentSource slRepaymentSource = (SlRepaymentSource) JSONMapper
								.toJava(parser.nextValue(),
										SlRepaymentSource.class);
						listRepaymentSources.add(slRepaymentSource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (listRepaymentSources.size() > 0) {
				for (SlRepaymentSource temp : listRepaymentSources) {
					boolean flag = StringUtil.isNumeric(temp.getTypeId());
					if (temp.getSourceId() == null) {
						GlobalType globalType = globalTypeDao.getByNodeKey(
								"repaymentSource").get(0);
						if (!flag) {
							Dictionary dic = new Dictionary();
							dic.setItemValue(temp.getTypeId());
							dic.setItemName(globalType.getTypeName());
							dic.setProTypeId(globalType.getProTypeId());
							dic.setDicKey(temp.getTypeId());
							dic.setStatus("0");
							dictionaryDao.save(dic);
							temp.setTypeId(String.valueOf(dic.getDicId()));
						} else {

							Dictionary cd = dictionaryDao.get(Long.valueOf(temp
									.getTypeId()));
							if (null == cd) {
								Dictionary dic = new Dictionary();
								dic.setItemValue(temp.getTypeId());
								dic.setItemName(globalType.getTypeName());
								dic.setProTypeId(globalType.getProTypeId());
								dic.setDicKey(temp.getTypeId());
								dic.setStatus("0");
								dictionaryDao.save(dic);
								temp.setTypeId(String.valueOf(dic.getDicId()));
							}
						}
						temp.setProjId(project.getProjectId());
						this.slRepaymentSourceDao.save(temp);
					} else {
						SlRepaymentSource rPersistent = this.slRepaymentSourceDao
								.get(temp.getSourceId());
						BeanUtil.copyNotNullProperties(rPersistent, temp);
						this.slRepaymentSourceDao.save(rPersistent);
					}
				}
			}

			if (null != degree && !"".equals(degree)) {
				project.setAppUserId(degree);
			}
			SlSmallloanProject persistent = this.dao
					.get(project.getProjectId());
			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
			project.setCompanyId(persistent.getCompanyId());
			// end
			// 更新项目和项目编辑时不能改变states字段的值，这个字段的值和节点放款及款项计划确认有关 2012-12-27 update
			// by liny
			project.setStates(persistent.getStates());
			project.setIsOtherFlow(persistent.getIsOtherFlow());
			BeanUtils.copyProperties(project, persistent, new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "oppositeType", "businessType",
					"createDate" });
			if (isAheadPay != null) {
				persistent.setIsAheadPay(Short.valueOf("1"));
			} else {
				persistent.setIsAheadPay(Short.valueOf("0"));
			}

			// 小贷标准流程使用
			if (isPreposePayAccrual != null && !"".equals(isPreposePayAccrual)) {
				persistent.setIsPreposePayAccrual(1);
			} else {
				persistent.setIsPreposePayAccrual(0);
			}

			/*
			 * //小额融资项目节点提交改变项目状态。 if(null!=flowOver) {
			 * if(Integer.parseInt(fangk)==1){
			 * 
			 * }else if(Integer.parseInt(fangk)==2){
			 * ////更新项目状态为3:终止,并且更新流程状态为终止。 VProcessRunData vp = null; Long
			 * projectId = persistent.getProjectId(); List<VProcessRunData>
			 * vpList =
			 * processRunDataService.getByTaskId(flowRunInfo.getTaskId());
			 * if(vpList!=null||vpList.size()!=0){ vp = vpList.get(0); }
			 * this.updateProcessRunStatus(vp.getRunId(), projectId); } }
			 */

			Long projectId = project.getProjectId();

			// 款项计划
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String maxintentDate = slFundIntentService.savejson(slFundIentJson,
					projectId, "SmallLoan", Short.parseShort("1"), persistent
							.getCompanyId());
			/*
			 * if(!persistent.getAccrualtype().equals("singleInterest")){
			 * persistent.setPayintentPeriod(Integer.valueOf(sumintent)); }
			 */
			StatsPro statsPro = new StatsPro();
			statsPro.calcuProIntentDate(persistent);
			// 费用收支

			String slActualToChargeJson = flowRunInfo.getRequest()
					.getParameter("slActualToChargeJson");
			slActualToChargeService.savejson(slActualToChargeJson, projectId,
					"SmallLoan", Short.parseShort("0"), persistent
							.getCompanyId());
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));
			/**
			 * 年化净利率
			 */
			ProjectActionUtil pu = new ProjectActionUtil();
			pu.getSmallloanMode(persistent);
			this.dao.merge(persistent);

			Short flag = 0;
			if (persistent.getOppositeType().equals("company_customer")) // 企业
			{
				flag = 0;
				Enterprise enterprise = new Enterprise();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise,
						"enterprise");
				Enterprise epersistent = this.enterpriseDao
						.getById(enterprise.getId());
				epersistent.setEnterprisename(enterprise.getEnterprisename());
				epersistent.setArea(enterprise.getArea());
				epersistent.setShortname(enterprise.getShortname());
				epersistent.setHangyeType(enterprise.getHangyeType());
				epersistent.setOrganizecode(enterprise.getOrganizecode());
				epersistent.setCciaa(enterprise.getCciaa());
				epersistent.setTelephone(enterprise.getTelephone());
				epersistent.setPostcoding(enterprise.getPostcoding());
				epersistent.setRootHangYeType(enterprise.getRootHangYeType());

				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,
						"person");
				if (null != person.getId() && person.getId() != 0) {
					Person ppersistent = this.personDao
							.getById(epersistent.getLegalpersonid());
					ppersistent.setMarry(person.getMarry());
					ppersistent.setName(person.getName());
					ppersistent.setSex(person.getSex());
					ppersistent.setCardtype(person.getCardtype());
					ppersistent.setCardnumber(person.getCardnumber());
					ppersistent.setCellphone(person.getCellphone());
					ppersistent.setPostcode(person.getPostcode());
					ppersistent.setSelfemail(person.getSelfemail());
					ppersistent.setPostaddress(person.getPostaddress());
					personDao.merge(ppersistent);
					baseCustomService.getCustomToweb("1", ppersistent.getId(),
							0);
				} else {
					Person p = new Person();
					p.setMarry(person.getMarry());
					p.setName(person.getName());
					p.setSex(person.getSex());
					p.setCardtype(person.getCardtype());
					p.setCardnumber(person.getCardnumber());
					p.setCellphone(person.getCellphone());
					p.setPostcode(person.getPostcode());
					p.setSelfemail(person.getSelfemail());
					p.setPostaddress(person.getPostaddress());
					p.setCompanyId(ContextUtil.getLoginCompanyId());
					personDao.save(p);
					epersistent.setLegalpersonid(p.getId());
					baseCustomService.getCustomToweb("1", p.getId(), 0);
				}
				enterpriseDao.merge(epersistent);
				if (listES.size() > 0) {
					for (int i = 0; i < listES.size(); i++) {
						EnterpriseShareequity es = listES.get(i);
						if (es.getId() == null) {
							es.setEnterpriseid(epersistent.getId());
							this.enterpriseShareequityDao.save(es);
						} else {
							EnterpriseShareequity esPersistent = this.enterpriseShareequityDao
									.load(es.getId());
							BeanUtils.copyProperties(es, esPersistent,
									new String[] { "id", "enterpriseid" });
							this.enterpriseShareequityDao.merge(esPersistent);
						}
					}
				}
			} else if (persistent.getOppositeType().equals("person_customer")) {
				flag = 1;
				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,
						"person");

				// 更新person信息开始
				Person persistentPerson = this.personDao
						.getById(person.getId());
				persistentPerson.setMarry(person.getMarry());
				persistentPerson.setName(person.getName());
				persistentPerson.setSex(person.getSex());
				persistentPerson.setCardtype(person.getCardtype());
				persistentPerson.setCardnumber(person.getCardnumber());
				persistentPerson.setCellphone(person.getCellphone());
				persistentPerson.setPostcode(person.getPostcode());
				persistentPerson.setSelfemail(person.getSelfemail());
				persistentPerson.setPostaddress(person.getPostaddress());
				personDao.merge(persistentPerson);
				// 更新person信息结束
				// 更新配偶信息
				if (person.getMarry() == 317) {
					Spouse spouse = new Spouse();
					BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
							"spouse");
					if (spouse.getSpouseId() == null
							|| spouse.getSpouseId() == 0) {
						spouse.setPersonId(person.getId());
						creditBaseDao.saveDatas(spouse);
					} else {
						Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
								spouse.getSpouseId());
						s.setCardnumber(spouse.getCardnumber());
						s.setCardtype(spouse.getCardtype());
						s.setCurrentcompany(spouse.getCurrentcompany());
						s.setDgree(spouse.getDgree());
						s.setJob(spouse.getJob());
						s.setLinkTel(spouse.getLinkTel());
						s.setName(spouse.getName());
						s.setPoliticalStatus(spouse.getPoliticalStatus());
						creditBaseDao.updateDatas(s);
					}
				} else {
					Spouse spouse = new Spouse();
					BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
							"spouse");
					if (spouse.getSpouseId() != null
							&& spouse.getSpouseId() == 0) {
						Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
								spouse.getSpouseId());
						creditBaseDao.deleteDatas(s);
					}
				}
			}
			EnterpriseBank enterpriseBank = new EnterpriseBank();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,
					"enterpriseBank");
			if (enterpriseBank.getId() == null || enterpriseBank.getId() == 0) {
				List<EnterpriseBank> list = enterpriseBankService.getBankList(
						persistent.getOppositeID().intValue(), flag, Short
								.valueOf("0"),Short.valueOf("0"));
				for (EnterpriseBank e : list) {
					e.setIscredit(Short.valueOf("1"));
					creditBaseDao.updateDatas(e);
				}
				enterpriseBank.setEnterpriseid(persistent.getOppositeID()
						.intValue());
				enterpriseBank.setIscredit(Short.valueOf("0"));
				enterpriseBank.setIsEnterprise(flag);
				enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
				enterpriseBank.setIsInvest(Short.valueOf("0"));
			
				creditBaseDao.saveDatas(enterpriseBank);
			} else {
				EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(
						EnterpriseBank.class, enterpriseBank.getId());
				bank.setAccountnum(enterpriseBank.getAccountnum());
				bank.setAccountType(enterpriseBank.getAccountType());
				bank.setBankid(enterpriseBank.getBankid());
				bank.setBankname(enterpriseBank.getBankname());
				bank.setName(enterpriseBank.getName());
				bank.setOpenType(enterpriseBank.getOpenType());
				bank.setAreaId(enterpriseBank.getAreaId());
				bank.setAreaName(enterpriseBank.getAreaName());
				bank.setBankOutletsName(bank.getBankOutletsName());
				creditBaseDao.updateDatas(bank);
			}
			baseCustomService.getCustomToweb(flag.toString(), persistent
					.getOppositeID().intValue(), 0);

			updateCompanyRiskNextStep(flowRunInfo);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷款尽调提交任务  信息出错:" + e.getMessage());
			return 0;
		}
	}

	// 安徽跳入下一个流层
	public Integer ahGoToNext(FlowRunInfo flowRunInfo) {
		try{
			Integer i = 1;// 执行流程
			// 保存一个隐藏字段，判定是否保存加载信息
			String needSave = flowRunInfo.getRequest().getParameter("needSave");
			String projectStatus = flowRunInfo.getRequest().getParameter("projectStatus");
			String extProjId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
			String chargeJson = flowRunInfo.getRequest().getParameter("chargeJson");
			String bpFunds = flowRunInfo.getRequest().getParameter("bpFunds");//是否生成bpFund,1 生成
			
			String fundData = flowRunInfo.getRequest().getParameter("fundIntentJsonData");//款项信息 add by gao 放款收息表
			if ("1".equals(needSave)) { // 第一次修改--精致调查
				i = saveSelfDate(flowRunInfo);
			}
			if ("2".equals(needSave)) { // 第二次修改--审贷会修改
				return summaryApprovalOpinionsNextStep(flowRunInfo);
			}
			if(null!=extProjId&&!"".equals(extProjId)){
				ProjectPropertyClassification property = new ProjectPropertyClassification();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), property,
						"projectPropertyClassification");
				projectPropertyClassificationDao.savePropertyInfo(property);
				SlSmallloanProject slProject = this.dao.get(Long.valueOf(extProjId));
				if(slProject!=null){
					SlSmallloanProject p = new SlSmallloanProject();
					BeanUtil.populateEntity(flowRunInfo.getRequest(), p, "slSmallloanProject");
					BeanUtil.copyNotNullProperties(slProject, p);
					dao.merge(slProject);
				}
				if(null != slProject && !"".equals(fundData) && null !=fundData){
					// 保存款项计划
					
					List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
					slFundIntents = slFundIntentService.savesmallLoanJsonIntent(fundData,
							slProject, Short.parseShort("0"));
					List<SlFundIntent> slist=slFundIntentDao.getByProjectId(slProject.getProjectId(), slProject.getBusinessType());
					for(SlFundIntent s:slist){
						if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
							slFundIntentDao.remove(s);
						}
					}
					for (SlFundIntent a : slFundIntents) {
						slFundIntentDao.evict(a);
						this.slFundIntentDao.save(a);
					}
				}
				if(projectStatus!=null&&!"".equals(projectStatus)){
					slProject.setProjectStatus(Short.valueOf(projectStatus));
					this.dao.merge(slProject);
				}
				if(null!=chargeJson&&!"".equals(chargeJson)&&!"undefined".equals(chargeJson)){
					slActualToChargeService.saveJson(chargeJson,slProject.getProjectId(),"SmallLoan",(short)0,1l,null);
				}
				if(null!=bpFunds&&"1".equals(bpFunds)){
						for(int j=0;j<2;j++){
							BpFundProject fundProject=bpFundProjectDao.getByProjectId(slProject.getProjectId(),Short.parseShort(j+""));
							if(null==fundProject){
								fundProject=new BpFundProject();
							}
							BeanUtil.copyNotNullProperties(fundProject, slProject);
							fundProject.setFlag(Short.parseShort(String.valueOf(j)));
							fundProject.setSlFundIntents(null);
							//fundProject.setFundResource(""+j);
							bpFundProjectDao.merge(fundProject);
						}
							
				}
				/**/
			}
			
		if(i == 0)
				return 0;// 保存有误，返回
		}catch(Exception e){
			e.printStackTrace();
		}
		return saveSelfNext(flowRunInfo);
	}

	private Integer saveSelfNext(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				// transitionName获取获到的节点名
				String transitionName = flowRunInfo.getTransitionName();
				// 选择下一个任务的处理人
				String riskAttacheId = flowRunInfo.getRequest().getParameter(
						"riskAttacheId");
				// 这获是身带会成员，名字可以统一
				String onlineJudgementIds = flowRunInfo.getRequest()
						.getParameter("onlineJudgementIds");
				// 跳转至下一个流程riskAttacheId/onlineJudgementIds可以统一
				goToProcessNext(flowRunInfo, transitionName, riskAttacheId,
						onlineJudgementIds);
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private Integer saveSelfDate(FlowRunInfo flowRunInfo) {
		try {
			String borrowerInfo = flowRunInfo.getRequest().getParameter(
					"borrowerInfo");
			String gudongInfo = flowRunInfo.getRequest().getParameter(
					"gudongInfo");
			String isAheadPay = flowRunInfo.getRequest().getParameter(
					"isAheadPay");
			String isPreposePayAccrual = flowRunInfo.getRequest().getParameter(
					"isPreposePayAccrualCheck");
			String degree = flowRunInfo.getRequest().getParameter("degree");
			// String flowOver =
			// flowRunInfo.getRequest().getParameter("flowOver");//
			// 项目放款状态:1表示项目放款;2表示未放款,项目终止。
			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");
			//BeanUtil.populateEntity(request, dynaModel);
			// 保存共同借款人信息
			List<BorrowerInfo> listBO = new ArrayList<BorrowerInfo>();
			if (null != borrowerInfo && !"".equals(borrowerInfo)) {
				String[] borrowerInfoArr = borrowerInfo.split("@");
				for (int i = 0; i < borrowerInfoArr.length; i++) {
					String str = borrowerInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						BorrowerInfo bo = (BorrowerInfo) JSONMapper.toJava(
								parser.nextValue(), BorrowerInfo.class);
						listBO.add(bo);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 保存股东信息
			List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
			if (null != gudongInfo && !"".equals(gudongInfo)) {
				String[] shareequityArr = gudongInfo.split("@");
				for (int i = 0; i < shareequityArr.length; i++) {
					String str = shareequityArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					try {
						EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper
								.toJava(parser.nextValue(),
										EnterpriseShareequity.class);
						listES.add(enterpriseShareequity);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (listBO.size() > 0) {
				for (int i = 0; i < listBO.size(); i++) {
					BorrowerInfo bo = listBO.get(i);
					if (bo.getBorrowerInfoId() == null) {
						bo.setProjectId(project.getProjectId());
						bo.setBusinessType(project.getBusinessType());
						bo.setOperationType(project.getOperationType());
						this.borrowerInfoDao.save(bo);
					} else {
						BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo
								.getBorrowerInfoId());
						BeanUtils.copyProperties(boPersistent, bo);
						this.borrowerInfoDao.merge(boPersistent);
					}
					if (null != bo.getType() && bo.getType() == 0) {
						if (null != bo.getCustomerId()) {
							Enterprise e = this.enterpriseDao.getById(bo
									.getCustomerId());
							e.setArea(bo.getAddress());
							e.setCciaa(bo.getCardNum());
							e.setTelephone(bo.getTelPhone());
							this.enterpriseDao.merge(e);
						}
					} else if (null != bo.getType() && bo.getType() == 1) {
						Person p = this.personDao.getById(bo.getCustomerId());
						p.setPostaddress(bo.getAddress());
						p.setCardnumber(bo.getCardNum());
						p.setCellphone(bo.getTelPhone());
						this.personDao.merge(p);
					}
				}
			}
			// 保存第一还款来源
			String repaymentSource = flowRunInfo.getRequest().getParameter(
					"repaymentSource");
			List<SlRepaymentSource> listRepaymentSources = new ArrayList<SlRepaymentSource>(); // 第一还款来源
			if (null != repaymentSource && !"".equals(repaymentSource)) {
				String[] repaymentSourceArr = repaymentSource.split("@");
				for (int i = 0; i < repaymentSourceArr.length; i++) {
					try {
						String str = repaymentSourceArr[i];
						JSONParser parser = new JSONParser(
								new StringReader(str));
						SlRepaymentSource slRepaymentSource = (SlRepaymentSource) JSONMapper
								.toJava(parser.nextValue(),
										SlRepaymentSource.class);
						listRepaymentSources.add(slRepaymentSource);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			if (listRepaymentSources.size() > 0) {
				for (SlRepaymentSource temp : listRepaymentSources) {
					boolean flag = StringUtil.isNumeric(temp.getTypeId());
					if (temp.getSourceId() == null) {
						GlobalType globalType = globalTypeDao.getByNodeKey(
								"repaymentSource").get(0);
						if (!flag) {
							Dictionary dic = new Dictionary();
							dic.setItemValue(temp.getTypeId());
							dic.setItemName(globalType.getTypeName());
							dic.setProTypeId(globalType.getProTypeId());
							dic.setDicKey(temp.getTypeId());
							dic.setStatus("0");
							dictionaryDao.save(dic);
							temp.setTypeId(String.valueOf(dic.getDicId()));
						} else {

							Dictionary cd = dictionaryDao.get(Long.valueOf(temp
									.getTypeId()));
							if (null == cd) {
								Dictionary dic = new Dictionary();
								dic.setItemValue(temp.getTypeId());
								dic.setItemName(globalType.getTypeName());
								dic.setProTypeId(globalType.getProTypeId());
								dic.setDicKey(temp.getTypeId());
								dic.setStatus("0");
								dictionaryDao.save(dic);
								temp.setTypeId(String.valueOf(dic.getDicId()));
							}
						}
						temp.setProjId(project.getProjectId());
						this.slRepaymentSourceDao.save(temp);
					} else {
						SlRepaymentSource rPersistent = this.slRepaymentSourceDao
								.get(temp.getSourceId());
						BeanUtil.copyNotNullProperties(rPersistent, temp);
						this.slRepaymentSourceDao.save(rPersistent);
					}
				}
			}

			if (null != degree && !"".equals(degree)) {
				project.setAppUserId(degree);
			}
			SlSmallloanProject persistent = this.dao
					.get(project.getProjectId());
			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
			project.setCompanyId(persistent.getCompanyId());
			// end
			// 更新项目和项目编辑时不能改变states字段的值，这个字段的值和节点放款及款项计划确认有关 2012-12-27 update
			// by liny
			project.setStates(persistent.getStates());
			project.setIsOtherFlow(persistent.getIsOtherFlow());
			BeanUtil.copyNotNullProperties(persistent, project);
			/*BeanUtils.copyProperties(project, persistent, new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "oppositeType", "businessType",
					"createDate" });*/
			if (isAheadPay != null) {
				persistent.setIsAheadPay(Short.valueOf("1"));
			} else {
				persistent.setIsAheadPay(Short.valueOf("0"));
			}

			// 小贷标准流程使用
			if (isPreposePayAccrual != null && !"".equals(isPreposePayAccrual)) {
				persistent.setIsPreposePayAccrual(1);
			} else {
				persistent.setIsPreposePayAccrual(0);
			}

			/*
			 * //小额融资项目节点提交改变项目状态。 if(null!=flowOver) {
			 * if(Integer.parseInt(fangk)==1){
			 * 
			 * }else if(Integer.parseInt(fangk)==2){
			 * ////更新项目状态为3:终止,并且更新流程状态为终止。 VProcessRunData vp = null; Long
			 * projectId = persistent.getProjectId(); List<VProcessRunData>
			 * vpList =
			 * processRunDataService.getByTaskId(flowRunInfo.getTaskId());
			 * if(vpList!=null||vpList.size()!=0){ vp = vpList.get(0); }
			 * this.updateProcessRunStatus(vp.getRunId(), projectId); } }
			 */

			Long projectId = project.getProjectId();

			// 款项计划
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String maxintentDate = slFundIntentService.savejson(slFundIentJson,
					projectId, "SmallLoan", Short.parseShort("1"), persistent
							.getCompanyId());
			/*
			 * if(!persistent.getAccrualtype().equals("singleInterest")){
			 * persistent.setPayintentPeriod(Integer.valueOf(sumintent)); }
			 */
			StatsPro statsPro = new StatsPro();
			if(persistent.getStartDate()!=null){
				statsPro.calcuProIntentDate(persistent);
			}
			// 费用收支

			String slActualToChargeJson = flowRunInfo.getRequest()
					.getParameter("slActualToChargeJson");
			slActualToChargeService.savejson(slActualToChargeJson, projectId,
					"SmallLoan", Short.parseShort("0"), persistent
							.getCompanyId());
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));
			/**
			 * 年化净利率
			 */
			ProjectActionUtil pu = new ProjectActionUtil();
			pu.getSmallloanMode(persistent);
			
			//尽职调查页面
			if(project.getMoney()!=null&& project.getProjectMoney()!=null){
				Double money  = project.getMoney().doubleValue();
				Double pmoney = project.getProjectMoney().doubleValue();
				updateInvesterCredit(persistent.getAvailableId(), (money-pmoney));
			}
		
			this.dao.merge(persistent);

			Short flag = 0;
			if (persistent.getOppositeType().equals("company_customer")) // 企业
			{
				flag = 0;
				Enterprise enterprise = new Enterprise();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise,
						"enterprise");
				Enterprise epersistent = this.enterpriseDao
						.getById(enterprise.getId());
				epersistent.setEnterprisename(enterprise.getEnterprisename());
				epersistent.setArea(enterprise.getArea());
				epersistent.setShortname(enterprise.getShortname());
				epersistent.setHangyeType(enterprise.getHangyeType());
				epersistent.setOrganizecode(enterprise.getOrganizecode());
				epersistent.setCciaa(enterprise.getCciaa());
				epersistent.setTelephone(enterprise.getTelephone());
				epersistent.setPostcoding(enterprise.getPostcoding());
				epersistent.setRootHangYeType(enterprise.getRootHangYeType());

			
				// 更新法人信息
				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,
						"person");
				if (null != person.getId() && person.getId() != 0) {
					Person ppersistent = this.personDao
							.getById(epersistent.getLegalpersonid());
					ppersistent.setName(person.getName());
					ppersistent.setSex(person.getSex());
					ppersistent.setCardtype(person.getCardtype());
					ppersistent.setCardnumber(person.getCardnumber());
					ppersistent.setCellphone(person.getCellphone());
					ppersistent.setSelfemail(person.getSelfemail());
					ppersistent.setCompanyId(ContextUtil.getLoginCompanyId());
					personDao.merge(ppersistent);
				
				} else {
					Long currentUserId = ContextUtil.getCurrentUserId();
					Person p = new Person();
					p.setId(null);
					p.setCreater(ContextUtil.getCurrentUser().getFullname());
					p.setBelongedId(currentUserId.toString());
					p.setCreaterId(currentUserId);
					p.setCreatedate(new Date());
					p.setCompanyId(ContextUtil.getLoginCompanyId());
					p.setName(person.getName());
					p.setSex(person.getSex());
					p.setCardtype(person.getCardtype());
					p.setCardnumber(person.getCardnumber());
					p.setCellphone(person.getCellphone());
					p.setSelfemail(person.getSelfemail());
					personDao.save(p);
					epersistent.setLegalpersonid(p.getId());
				
				}
				enterpriseDao.merge(epersistent);
				if (listES.size() > 0) {
					for (int i = 0; i < listES.size(); i++) {
						EnterpriseShareequity es = listES.get(i);
						if (es.getId() == null) {
							es.setEnterpriseid(epersistent.getId());
							this.enterpriseShareequityDao.save(es);
						} else {
							EnterpriseShareequity esPersistent = this.enterpriseShareequityDao
									.load(es.getId());
							BeanUtils.copyProperties(es, esPersistent,
									new String[] { "id", "enterpriseid" });
							this.enterpriseShareequityDao.merge(esPersistent);
						}
					}
				}
			} else if (persistent.getOppositeType().equals("person_customer")) {
				flag = 1;
				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,
						"person");

				// 更新person信息开始
				Person persistentPerson = this.personDao
						.getById(person.getId());
				persistentPerson.setMarry(person.getMarry());
				persistentPerson.setName(person.getName());
				persistentPerson.setSex(person.getSex());
				persistentPerson.setCardtype(person.getCardtype());
				persistentPerson.setCardnumber(person.getCardnumber());
				persistentPerson.setCellphone(person.getCellphone());
				persistentPerson.setPostcode(person.getPostcode());
				persistentPerson.setSelfemail(person.getSelfemail());
				persistentPerson.setPostaddress(person.getPostaddress());
				personDao.merge(persistentPerson);
				// 更新person信息结束
				// 更新配偶信息
				/*if (person.getMarry() == 317) {
					Spouse spouse = new Spouse();
					BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
							"spouse");
					if (spouse.getSpouseId() == null
							|| spouse.getSpouseId() == 0) {
						spouse.setPersonId(person.getId());
						creditBaseDao.saveDatas(spouse);
					} else {
						Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
								spouse.getSpouseId());
						s.setCardnumber(spouse.getCardnumber());
						s.setCardtype(spouse.getCardtype());
						s.setCurrentcompany(spouse.getCurrentcompany());
						s.setDgree(spouse.getDgree());
						s.setJob(spouse.getJob());
						s.setLinkTel(spouse.getLinkTel());
						s.setName(spouse.getName());
						s.setPoliticalStatus(spouse.getPoliticalStatus());
						creditBaseDao.updateDatas(s);
					}
				} else {
					Spouse spouse = new Spouse();
					BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,
							"spouse");
					if (spouse.getSpouseId() != null
							&& spouse.getSpouseId() == 0) {
						Spouse s = (Spouse) creditBaseDao.getById(Spouse.class,
								spouse.getSpouseId());
						creditBaseDao.deleteDatas(s);
					}
				}*/
				Spouse spousePer = spouseDao.getByPersonId(persistentPerson.getId());
				Spouse spouse = new Spouse();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse,"spouse");
				if(spouse.getPersonId()!=null){
					spouse.setPersonId(person.getId());
				}
				if(spousePer==null){
					spouseDao.save(spouse);
				}else{
					BeanUtil.copyNotNullProperties(spousePer, spouse);
					spouseDao.merge(spouse);
				}
				
			}
			EnterpriseBank enterpriseBank = new EnterpriseBank();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,
					"enterpriseBank");
			if (enterpriseBank.getId() == null || enterpriseBank.getId() == 0) {
				List<EnterpriseBank> list = enterpriseBankService.getBankList(
						persistent.getOppositeID().intValue(), flag, Short
								.valueOf("0"),Short.valueOf("0"));
				for (EnterpriseBank e : list) {
					e.setIscredit(Short.valueOf("1"));
					creditBaseDao.updateDatas(e);
				}
				enterpriseBank.setEnterpriseid(persistent.getOppositeID()
						.intValue());
				enterpriseBank.setIscredit(Short.valueOf("0"));
				enterpriseBank.setIsEnterprise(flag);
				enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
				enterpriseBank.setIsInvest(Short.valueOf("0"));
			
				creditBaseDao.saveDatas(enterpriseBank);
			} else {
				EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(
						EnterpriseBank.class, enterpriseBank.getId());
				bank.setAccountnum(enterpriseBank.getAccountnum());
				bank.setAccountType(enterpriseBank.getAccountType());
				bank.setBankid(enterpriseBank.getBankid());
				bank.setBankname(enterpriseBank.getBankname());
				bank.setName(enterpriseBank.getName());
				bank.setOpenType(enterpriseBank.getOpenType());
				bank.setAreaId(enterpriseBank.getAreaId());
				bank.setAreaName(enterpriseBank.getAreaName());
				bank.setBankOutletsName(enterpriseBank.getBankOutletsName());
				creditBaseDao.updateDatas(bank);
			}
			baseCustomService.getCustomToweb(flag.toString(), persistent
					.getOppositeID().intValue(), 0);

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷款尽调提交任务  信息出错:" + e.getMessage());
			return 0;
		}
	}
	private void updateInvesterCredit(Long investId,Double changeMoney){//更新推介机构当前可用额度
		if(investId==null||"".equals(investId))return;
		InvestEnterprise enter = investEnterpriseService.get(investId);
		if(null!=enter.getNowCreditLimit()){
			Double credit = enter.getNowCreditLimit().doubleValue();
			enter.setNowCreditLimit(new BigDecimal((enter.getNowCreditLimit().doubleValue()+changeMoney)+""));
		}
	}
	private Integer saveSelfDate2(FlowRunInfo flowRunInfo) {
		try {

			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,
					"slSmallloanProject");

			SlSmallloanProject persistent = this.dao
					.get(project.getProjectId());
			// add by lisl 2012-09-24 更新项目信息时，companyId的值保持不变
			project.setCompanyId(persistent.getCompanyId());
			// end
			// 更新项目和项目编辑时不能改变states字段的值，这个字段的值和节点放款及款项计划确认有关 2012-12-27 update
			// by liny
			project.setStates(persistent.getStates());
			project.setIsOtherFlow(persistent.getIsOtherFlow());
			BeanUtils.copyProperties(project, persistent, new String[] { "id",
					"operationType", "flowType", "mineType", "mineId",
					"oppositeType", "oppositeID", "projectName",
					"projectNumber", "oppositeType", "businessType",
					"createDate" });

			Long projectId = project.getProjectId();

			// 款项计划
			String slFundIentJson = flowRunInfo.getRequest().getParameter(
					"fundIntentJsonData");
			String maxintentDate = slFundIntentService.savejson(slFundIentJson,
					projectId, "SmallLoan", Short.parseShort("1"), persistent
							.getCompanyId());
			/*
			 * if(!persistent.getAccrualtype().equals("singleInterest")){
			 * persistent.setPayintentPeriod(Integer.valueOf(sumintent)); }
			 */
			StatsPro statsPro = new StatsPro();
			statsPro.calcuProIntentDate(persistent);
			// 费用收支

			String slActualToChargeJson = flowRunInfo.getRequest()
					.getParameter("slActualToChargeJson");
			slActualToChargeService.savejson(slActualToChargeJson, projectId,
					"SmallLoan", Short.parseShort("0"), persistent
							.getCompanyId());
			Map<String, BigDecimal> map = slFundIntentService
					.saveProjectfiance(persistent.getProjectId(), "SmallLoan");
			persistent.setPaychargeMoney(map.get("paychargeMoney"));
			persistent.setIncomechargeMoney(map.get("incomechargeMoney"));
			persistent.setAccrualMoney(map.get("loanInterest"));
			persistent.setConsultationMoney(map.get("consultationMoney"));
			persistent.setServiceMoney(map.get("serviceMoney"));
			/**
			 * 年化净利率
			 */
			ProjectActionUtil pu = new ProjectActionUtil();
			pu.getSmallloanMode(persistent);
			this.dao.merge(persistent);

			Short flag = 0;

			EnterpriseBank enterpriseBank = new EnterpriseBank();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,
					"enterpriseBank");
			baseCustomService.getCustomToweb(flag.toString(), persistent
					.getOppositeID().intValue(), 0);

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("贷款尽调提交任务  信息出错:" + e.getMessage());
			return 0;
		}
	}
/***
 * 修改项目状态
 * @param projectId
 * @param status
 * @author dell 
 * */
	private void changeStatus(Long projectId,Short status){
		SlSmallloanProject project = this.dao.get(projectId);
		project.setProjectStatus(status);
		this.dao.merge(project);
	}
	private void goToProcessNext(FlowRunInfo flowRunInfo,
			String transitionName, String riskAttacheId,
			String onlineJudgementIds) {

		if (transitionName != null && !"".equals(transitionName)) {
			Map<String, Object> vars = new HashMap<String, Object>();
			if (transitionName.contains("终止") || transitionName.contains("结束")) {
				flowRunInfo.setStop(true);
				if(transitionName.equals("结束2")){
					String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
					SlSmallloanProject project = this.dao.get(Long.parseLong(projectId));
					InvestEnterprise enter = investEnterpriseService.get(project.getAvailableId());
					Double money = project.getProjectMoney().doubleValue();
					updateInvesterCredit(enter.getId(), money);
					
					if(null!=projectId&&!"".equals(projectId)){
						changeStatus(Long.parseLong(projectId),(short)3);
					}
				}
			} else {
				ProcessForm currentForm = processFormDao
						.getByTaskId(flowRunInfo.getTaskId());
				if (currentForm != null) {
					boolean isToNextTask = creditProjectService
							.compareTaskSequence(currentForm.getRunId(),
									currentForm.getActivityName(),
									transitionName);
					if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
						flowRunInfo.setAfresh(true);// 表示打回重做
						vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
						vars.put("targetActivityName", transitionName);// 打回的目标任务名称
					} else {
						if (riskAttacheId != null && !"".equals(riskAttacheId)) {
							String assignUserId = riskAttacheId;
							vars.put("flowAssignId", assignUserId);
						}
						if ("线上评审会决议".equals(transitionName)) {
							if (onlineJudgementIds != null
									&& !"".equals(onlineJudgementIds)) {
								String assignUserId = onlineJudgementIds;
								vars.put("flowAssignId", assignUserId);
							}
						}
					}
				}
			}
			vars.put("slnRiskManagerCheckResult", transitionName);
			flowRunInfo.getVariables().putAll(vars);
		}
	}
	//查出已经放款后的项目
	@Override
	public void LoanIngProjectList(Integer start, Integer limit) {

		// TODO Auto-generated method stub
		
		String hql = "from VSmallloanProject as small  where ( small.processName like 'ahsmallLoanFlow%' or small.processName like 'smallHistoryRecordsFlow%') and  small.projectStatus = 1 and  small.projectId  not in ( select obligation.projectId from ObObligationProject as obligation ) " ;
		//String hql = "from InvestFundProject as ssss where ssss.projectStatus in (1)" ;
		List totalList = new ArrayList();
		List<VSmallloanProject> list = new ArrayList(); 
		int totalProperty = 1 ;
		try {
			totalList = creditBaseDao.queryHql(hql);
			totalProperty = 0; ;//记录总数
			//查询符合条件的部分记录数，数据库分页
			list = creditBaseDao.queryHql(hql,start, limit) ;
			JsonUtil.jsonFromList(list, totalProperty) ;
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject("数据库查询出错，请重试", false) ;
		}

	
		
	}

	@Override
	public Integer updateCreditFlowInfo(SlSmallloanProject slSmallloanProject,Person person,
			 String personHouseDate,String personCarData, StringBuffer sb,BpMoneyBorrowDemand bpMoneyBorrowDemand, WorkCompany workCompany,String slActualData) {
		try{
			SlSmallloanProject persistent = this.dao.get(slSmallloanProject.getProjectId());
			
			// 更新借款需求
			if (bpMoneyBorrowDemand.getBorrowid() == null) {
				bpMoneyBorrowDemand.setProjectID(slSmallloanProject.getProjectId());
				this.bpMoneyBorrowDemandDao.save(bpMoneyBorrowDemand);
			} else {
				BpMoneyBorrowDemand bbd = bpMoneyBorrowDemandDao.load(bpMoneyBorrowDemand.getBorrowid());
				BeanUtil.copyNotNullProperties(bbd, bpMoneyBorrowDemand);
				bbd.setProjectID(slSmallloanProject.getProjectId());
				this.bpMoneyBorrowDemandDao.merge(bbd);
			}
			//保存客户房产信息
			if(personHouseDate!=null&&!"".equals(personHouseDate)){
				String[] personHouseInfoArr = personHouseDate.split("@");
				for (int i = 0; i < personHouseInfoArr.length; i++) {
					String str = personHouseInfoArr[i];
					//反序列化
					JSONParser parser = new JSONParser(new StringReader(str));
					CsPersonHouse csPersonHouse = (CsPersonHouse) JSONMapper.toJava(parser.nextValue(), CsPersonHouse.class);
					if(null==csPersonHouse.getId()||"".equals(csPersonHouse.getId())){
						csPersonHouseDao.save(csPersonHouse);
					}else{
						CsPersonHouse tempHouse=csPersonHouseDao.get(csPersonHouse.getId());
						BeanUtil.copyNotNullProperties(tempHouse, csPersonHouse);
						csPersonHouseDao.merge(tempHouse);
					}
				}
			}
			
			//保存客户车产信息
			if(personCarData!=null&&!"".equals(personCarData)){
				String[] personCarInfoArr = personCarData.split("@");
				for (int i = 0; i < personCarInfoArr.length; i++) {
					String str = personCarInfoArr[i];
					//反序列化
					JSONParser parser = new JSONParser(new StringReader(str));
					CsPersonCar csPersonCar = (CsPersonCar) JSONMapper.toJava(parser.nextValue(), CsPersonCar.class);
					if(null==csPersonCar.getId()||"".equals(csPersonCar.getId())){
						csPersonCarDao.save(csPersonCar);
					}else{
						CsPersonCar tempCar=csPersonCarDao.get(csPersonCar.getId());
						BeanUtil.copyNotNullProperties(tempCar, csPersonCar);
						csPersonCarDao.merge(tempCar);
					}
				}
			}
			
			//保存手续费用信息
			if(slActualData!=null&&!"".equals(slActualData)){

				
				
				String[] slActualDataArr = slActualData.split("@");
				for (int i = 0; i < slActualDataArr.length; i++) {
					String str = slActualDataArr[i];
					//反序列化
					JSONParser parser = new JSONParser(new StringReader(str));
					SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper.toJava(parser.nextValue(), SlActualToCharge.class);
					//当手续费不是产品配置中的，ActualChargeId为空
					if(null==slActualToCharge.getActualChargeId()||"".equals(slActualToCharge.getActualChargeId())){
						slActualToChargeService.save(slActualToCharge, slSmallloanProject);
						//slActualToChargeDao.save(slActualToCharge);
					}else{
						SlActualToCharge oldSlActual=slActualToChargeDao.get(slActualToCharge.getActualChargeId());
						BeanUtil.copyNotNullProperties(oldSlActual, slActualToCharge);
						slActualToChargeDao.merge(oldSlActual);
					}
				}
			}
			
			//更新公司信息
			if(null!=workCompany){
				if (workCompany.getId()== null||workCompany.getId()==0) {
					workCompany.setPersonId(person.getId());
					this.workCompanyDao.save(workCompany);
				} else {
					workCompany.setPersonId(person.getId());
					this.workCompanyDao.merge(workCompany);
				}
				sb.append(",workCompanyId:"+workCompany.getId());
			}
			
			//更新个人客户信息
			Person pPersistent = this.personDao.getPersonById(person.getId());
			BeanUtil.copyNotNullProperties(pPersistent, person);
			pPersistent.setName(person.getName());
			pPersistent.setSex(person.getSex());
			pPersistent.setCardtype(person.getCardtype());
			pPersistent.setCardnumber(person.getCardnumber());
			pPersistent.setCellphone(person.getCellphone());
			pPersistent.setSelfemail(person.getSelfemail());
			pPersistent.setDegreewei(person.getDgree());
//			pPersistent.setPersonCount(person.getPersonCount());
			pPersistent.setFamilyaddress(person.getFamilyaddress());
			pPersistent.setHukou(person.getHukou());
			pPersistent.setEmployway(person.getEmployway());
			pPersistent.setLivingLife(person.getLivingLife());
			pPersistent.setAverageMonthWage(person.getAverageMonthWage());
			pPersistent.setBefMonthBalance(person.getBefMonthBalance());
			pPersistent.setQq(person.getQq());
			pPersistent.setMicroMessage(person.getMicroMessage());
			pPersistent.setBirthday(person.getBirthday());
			pPersistent.setFamilypostcode(person.getFamilypostcode());
			pPersistent.setPostcode(person.getPostcode());
			pPersistent.setResidenceDate(person.getResidenceDate());
			pPersistent.setMarry(person.getMarry());
			pPersistent.setTelphone(person.getTelphone());
			pPersistent.setSelfemail(person.getSelfemail());
			pPersistent.setHomeexpend(person.getHomeexpend());
			pPersistent.setHomecreditexpend(person.getHomecreditexpend());
			pPersistent.setMonthRent(person.getMonthRent());
			pPersistent.setJobincome(person.getJobincome());
			
			//个人公司信息
			pPersistent.setUnitproperties(person.getUnitproperties());//单位性质
			pPersistent.setUnitpostcode(person.getUnitpostcode());
			pPersistent.setUnitphone(person.getUnitphone());
			pPersistent.setUnitaddress(person.getUnitaddress());
			pPersistent.setCompanyDepartment(person.getCompanyDepartment());
			pPersistent.setJob(person.getJob());
			pPersistent.setJobstarttime(person.getJobstarttime());
			pPersistent.setJobincome(person.getJobincome());
			pPersistent.setCompanyFax(person.getCompanyFax());
			this.personDao.merge(pPersistent);
			
			slSmallloanProject.setCompanyId(persistent.getCompanyId());
			
			// 更新项目和项目编辑时不能改变states字段的值，这个字段的值和节点放款及款项计划确认有关 2012-12-27 update
			// by liny
			slSmallloanProject.setStates(persistent.getStates());
			slSmallloanProject.setIsOtherFlow(persistent.getIsOtherFlow());
			BeanUtils.copyProperties(slSmallloanProject, persistent,new String[] { "id", "operationType", "flowType","oppositeType", "oppositeID", "projectName",
							"projectNumber", "oppositeType", "businessType","createDate","mineType", "mineId", "departmentId" });
			if(slSmallloanProject.getMineId()!=null&&slSmallloanProject.getMineType()!=null){
				persistent.setMineId(slSmallloanProject.getMineId());
				persistent.setMineType(slSmallloanProject.getMineType());
			}
			if(slSmallloanProject.getBankId()!=null){
				persistent.setBankId(slSmallloanProject.getBankId());
			}
			this.dao.merge(persistent);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List<SlSmallloanProject> findList(String projectName,
			String projectNumber, String minMoneyStr, String maxMoneyStr,
			String projectStatus, PagingBean pb) {
		return dao.findList(projectName, projectNumber, minMoneyStr, maxMoneyStr, projectStatus, pb);
	}

	public AssureIntentBookElementCode getAssureIntentBookElementCode() {
		return assureIntentBookElementCode;
	}

	public void setAssureIntentBookElementCode(
			AssureIntentBookElementCode assureIntentBookElementCode) {
		this.assureIntentBookElementCode = assureIntentBookElementCode;
	}

	@Override
	public Integer updateSlFundIntent(SlSmallloanProject slSmallloanProject,List<SlFundIntent> slFundIntents, String isDeleteAllFundIntent,String fundResource) {
		try{
			if (isDeleteAllFundIntent.equals("1")) {

				List<SlFundIntent> allintent = this.slFundIntentDao.getByProjectId1(slSmallloanProject.getProjectId(),"SmallLoan");

				for (SlFundIntent s : allintent) {
					slFundIntentDao.evict(s);
					if (s.getAfterMoney().compareTo(new BigDecimal(0)) == 0) {
						this.slFundIntentDao.remove(s);
					}
				}
				for (SlFundIntent a : slFundIntents) {
					a.setFundResource(fundResource);
					slFundIntentDao.evict(a);
					this.slFundIntentDao.save(a);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public Integer matchingFundsStep(FlowRunInfo flowRunInfo) {
		String projectId = flowRunInfo.getRequest().getParameter("projectId");
		String businessType = flowRunInfo.getRequest().getParameter("businessType");
		String investData1 = flowRunInfo.getRequest().getParameter("investData1");
		if(null==projectId||"".equals(projectId)||null==businessType||"".equals(businessType)){
			return 0;
		}
		StringBuffer buff = new StringBuffer();
		BpFundProject bpFundProject = new BpFundProject();
		SlSmallloanProject project = this.dao.get(Long.parseLong(projectId));
		try {
			BeanUtil.populateEntity(flowRunInfo.getRequest(),bpFundProject,"platFormBpFundProject");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		
		if(null!=bpFundProject){
			String investerIds = "";
			if(bpFundProject.getId()!=null){
				BpFundProject bproject = this.bpFundProjectDao.get(bpFundProject.getId());
				try {
					BeanUtil.copyNotNullProperties(bproject, bpFundProject);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				if(bproject.getInvestorIds()!=null&&!bproject.getInvestorIds().equals("")){
					buff.append(bproject.getInvestorIds());
					investerIds = buff.toString();
				}else{
					if(buff.toString().length()>0)
						investerIds = buff.toString().substring(0, buff.toString().length()-1);
					else
						investerIds = null;
				}
				bproject.setInvestorIds(investerIds);
				this.bpFundProjectDao.merge(bproject);
				
			}else{
				if(buff.toString().length()>0)
					investerIds = buff.toString().substring(0, buff.toString().length()-1);
				else
					investerIds = null;
				bpFundProject.setInvestorIds(investerIds);
				this.bpFundProjectDao.save(bpFundProject);
			}
		}
		if(null!=investData1&&!"".equals(investData1)){
			String[] invests = investData1.split("@");
			for(int i=0;i<invests.length;i++){
				String str = invests[i];
				if(null!=str&&!"".equals(str)){
					JSONParser parser = new JSONParser(new StringReader(str));
					InvestPersonInfo investPersonInfo;
					try {
						investPersonInfo = (InvestPersonInfo) JSONMapper.toJava(parser.nextValue(),InvestPersonInfo.class);
						if(investPersonInfo!=null){
							if(investPersonInfo.getInvestId()==null){
								investPersonInfo.setProjectId(Long.parseLong(projectId));
								investPersonInfo.setMoneyPlanId(bpFundProject.getId());
								investPersonInfo.setBusinessType(businessType);
								investPersonInfoService.save(investPersonInfo);
								buff.append(investPersonInfo.getInvestId()).append(",");
							}else{
								InvestPersonInfo ipi = investPersonInfoService.get(investPersonInfo.getInvestId());
								BeanUtil.copyNotNullProperties(ipi, investPersonInfo);
								investPersonInfoService.merge(ipi);
							}
						}
					} catch (TokenStreamException e) {
						e.printStackTrace();
					} catch (RecognitionException e) {
						e.printStackTrace();
					} catch (MapperException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return 1;
	}

	@Override
	public Integer dealFundsStep(FlowRunInfo flowRunInfo) {
		String projectId  = flowRunInfo.getRequest().getParameter("projectId");
		String bidPlanId  = flowRunInfo.getRequest().getParameter("bidPlanId");
		SlSmallloanProject sl = dao.get(Long.valueOf(projectId));
		String ownId = flowRunInfo.getRequest().getParameter("ownBpFundProject.id");
		String p2pId = flowRunInfo.getRequest().getParameter("platFormBpFundProject.id");
		String platStartIntentdDate = flowRunInfo.getRequest().getParameter("platFormBpFundProject.startInterestDate");
		String intentdDate = flowRunInfo.getRequest().getParameter("platFormBpFundProject.intentDate");
		String ownStartIntentDate = flowRunInfo.getRequest().getParameter("ownBpFundProject.startInterestDate");
		String intentdDate1 = flowRunInfo.getRequest().getParameter("ownBpFundProject.intentDate");
		String oFundsDatas = flowRunInfo.getRequest().getParameter("oFundsDatas");
		String pFundsDatas = flowRunInfo.getRequest().getParameter("pFundsDatas");
		String chargeJson = flowRunInfo.getRequest().getParameter("chargeJson");
		String fundsJson = flowRunInfo.getRequest().getParameter("fundsJson");//借款人款项
		String ownslFunds = flowRunInfo.getRequest().getParameter("ownslFunds");//平台
		String transitionName = flowRunInfo.getTransitionName();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String flowType = flowRunInfo.getRequest().getParameter("flowType");
		Map<String, Object> vars = new HashMap<String, Object>();
		PlBidPlan bidPlan = null;
		if(null!=bidPlanId&&!"".equals(bidPlanId)&&bidPlanId.matches("[0-9]*")){
			bidPlan = plBidPlanService.get(Long.parseLong(bidPlanId));
		}
		//BeanUtils.copyProperties(flowRunInfo.getRequest(), sl, new String[]{"startInterestDate"});
		if (transitionName != null && !"".equals(transitionName)) {
			//Map<String, Object> vars = new HashMap<String, Object>();
			if (transitionName.contains("终止") || transitionName.contains("结束")) {
				flowRunInfo.setStop(true);
				sl.setProjectStatus((short)3);
				dao.merge(sl);
				//Map<String, Object> vars = new HashMap<String, Object>();
				vars.put("nextStepName", transitionName);
				flowRunInfo.getVariables().putAll(vars);
				return 1;
			}
		}	
		BpFundProject oldBpFund1 = null;
		BpFundProject oldBpFund2 = null;
		if(null!=ownId&&!"".equals(ownId)&&!"undefined".equals(ownId)){
			oldBpFund1 = bpFundProjectDao.get(Long.parseLong(ownId));
			if(oldBpFund1!=null&&bidPlanId!=null){
				if(null==bidPlanId||"".equals(bidPlanId)||!bidPlanId.matches("[0-9]*")){
					fundIntentService.savejson(oFundsDatas, Long.valueOf(projectId),"SmallLoan",Short.parseShort("0"),oldBpFund1.getCompanyId(),oldBpFund1.getId(),oldBpFund1.getFundResource(),null);
				}else{
					fundIntentService.savejson(oFundsDatas, Long.valueOf(projectId),"SmallLoan",Short.parseShort("0"),oldBpFund1.getCompanyId(),oldBpFund1.getId(),oldBpFund1.getFundResource(),Long.parseLong(bidPlanId));
				}
				
			}
			if(null!=ownStartIntentDate&&!"".equals(ownStartIntentDate)&&!"undefined".equals(ownStartIntentDate)){
				try {
					
					Date date = sdf.parse(ownStartIntentDate);
					Date date1 = sdf.parse(intentdDate1);
					if(null!=bidPlan){
						bidPlan.setStartIntentDate(date);
						bidPlan.setEndIntentDate(date1);
						plBidPlanService.merge(bidPlan);
					}
					oldBpFund1.setStartInterestDate(date);
					oldBpFund1.setIntentDate(date1);
					bpFundProjectService.merge(oldBpFund1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			bpFundProjectService.merge(oldBpFund1);
		}
		if(null!=p2pId&&!"".equals(p2pId)&&!"undefined".equals(p2pId)){//平台资金款项信息
			oldBpFund2 = bpFundProjectDao.get(Long.parseLong(p2pId));
			if(oldBpFund2!=null){
				fundIntentService.savejson(pFundsDatas, Long.valueOf(projectId),"SmallLoan",Short.parseShort("0"),oldBpFund2.getCompanyId(),oldBpFund2.getId(),oldBpFund2.getFundResource(),Long.parseLong(bidPlanId));
			//String sumintent= slFundIntentService.savejson(fundsJson, Long.valueOf(projectId),"SmallLoan",Short.parseShort("0"),oldBpFund2.getCompanyId(),oldBpFund2.getId(),oldBpFund2.getFundResource());
			}
			if(null!=platStartIntentdDate&&!"".equals(platStartIntentdDate)&&!"undefined".equals(platStartIntentdDate)){
				try {
					Date date = sdf.parse(platStartIntentdDate);
					Date date1 = sdf.parse(intentdDate);
					if(null!=bidPlan){
						bidPlan.setStartIntentDate(date);
						bidPlan.setEndIntentDate(date1);
						plBidPlanService.merge(bidPlan);
					}
					oldBpFund2.setStartInterestDate(date);
					oldBpFund2.setIntentDate(date1);
					bpFundProjectService.merge(oldBpFund2);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//平台借款人款项收息表
		if(null != sl && !"".equals(fundsJson) && null !=fundsJson &&bidPlanId!=null&&p2pId!=null){
			saveSlFundIntent(sl,fundsJson,Long.parseLong(bidPlanId),Long.parseLong(p2pId));
		}
		//典当行
		if(null != sl && !"".equals(ownslFunds) && null !=ownslFunds){
			if(null==bidPlanId||"".equals(bidPlanId)||!bidPlanId.matches("[0-9]*")){
				saveSlFundIntent(sl,ownslFunds,null,Long.parseLong(ownId));
			}else{
				saveSlFundIntent(sl,ownslFunds,Long.parseLong(bidPlanId),Long.parseLong(ownId));
			}
			
		}
		/*String slActualToChargeJson, Long projectId,
		String chargekey, Short flag, Long companyId, Long bidPlanId*/
		if(chargeJson!=null&&!"".equals(chargeJson)&&!"undefined".equals(chargeJson))
			if(null==flowType||"".equals(flowType)||"undefined".equals(flowType)){
				slActualToChargeService.saveJson(chargeJson,sl.getProjectId(),sl.getBusinessType(),(short)0,sl.getCompanyId(),Long.parseLong(bidPlanId));
			}else if("dry".equals(flowType)){
				slActualToChargeService.saveJson(chargeJson,sl.getProjectId(),sl.getBusinessType(),(short)0,sl.getCompanyId(),null);
			}
		
		vars.put("nextStepName", transitionName);
		flowRunInfo.getVariables().putAll(vars);
		return 1;
		
	}
//放款收息表
	@Override
	public void saveSlFundIntent(SlSmallloanProject sl,String jsondata,Long bidPlanId,Long preceptId){

		List<SlFundIntent> slFundIntents = new ArrayList<SlFundIntent>();
		slFundIntents = slFundIntentService.savesmallLoanJsonIntent(jsondata,
				sl, Short.parseShort("0"));
		List<SlFundIntent> slist = new ArrayList<SlFundIntent>();
		if(bidPlanId!=null){
			slist=slFundIntentDao.getListByBidPlanId(bidPlanId);
		}else if(preceptId!=null){
			slist=slFundIntentDao.getbyPreceptId(preceptId);
		}
		
		for(SlFundIntent s:slist){
			if(s.getAfterMoney().compareTo(new BigDecimal(0))==0){
				slFundIntentDao.remove(s);
			}
		}
		for (SlFundIntent a : slFundIntents) {
			slFundIntentDao.evict(a);
			a.setBidPlanId(bidPlanId);
			a.setPreceptId(preceptId);
			this.slFundIntentDao.save(a);
		}
	
	}
	
	
	@Override
	public Integer doNextDRY(FlowRunInfo flowRunInfo) {
		String projectId = flowRunInfo.getRequest().getParameter("projectId");
		String activityName = flowRunInfo.getRequest().getParameter("activityName");
		
		SlSmallloanProject slSmallloanProject = new SlSmallloanProject();
		BpFundProject ownProject = new BpFundProject();
		BpFundProject p2pProject = new BpFundProject();
		//String piId = flowRunInfo.getPiId();
		
		String transitionName = flowRunInfo.getTransitionName(); 
		try {
			
			BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
			BeanUtil.populateEntity(flowRunInfo.getRequest(), ownProject, "ownBpFundProject");
			BeanUtil.populateEntity(flowRunInfo.getRequest(), p2pProject, "platFormBpFundProject");
			
			SlSmallloanProject project = this.dao.get(slSmallloanProject.getProjectId());
			BeanUtil.copyNotNullProperties(project, slSmallloanProject);	
			this.dao.merge(project);

			if(ownProject!=null&&ownProject.getId()!=null){
				BpFundProject oproject = this.bpFundProjectDao.get(ownProject.getId());
				BeanUtil.copyNotNullProperties(oproject, ownProject);
				oproject.setProjectStatus((short)2);
				this.bpFundProjectDao.merge(oproject);				
			}
			if(p2pProject!=null&&p2pProject.getId()!=null){
				if(p2pProject.getPlatFormJointMoney().equals(new BigDecimal(0))){
					
				}else{
					BpFundProject pproject = this.bpFundProjectDao.get(p2pProject.getId());
					BeanUtil.copyNotNullProperties(pproject, p2pProject);
					//Double money = project.getProjectMoney().doubleValue();
				//	Double pmoney = pproject.getPlatFormJointMoney().doubleValue();
					//updateInvesterCredit(project.getAvailableId(), (money-pmoney));
					this.bpFundProjectDao.merge(pproject);
					if(pproject.getPlatFormJointMoney()!=null&&!pproject.getPlatFormJointMoney().equals(new BigDecimal(0)))
					if(project.getOppositeType().equals("company_customer")){//企业直投
						Enterprise enterprise  = enterpriseDao.getById(project.getOppositeID().intValue());
						BpBusinessDirPro bDirPro = bpBusinessDirProDao.getByMoneyPlanId(pproject.getId());
						if(bDirPro==null){
							bDirPro = new BpBusinessDirPro();
							this.initBusinessP2p(pproject, bDirPro, enterprise);
							bpBusinessDirProDao.save(bDirPro);
						}else{
							this.initBusinessP2p(pproject, bDirPro, enterprise);
							bpBusinessDirProDao.merge(bDirPro);
						}
					}else if(project.getOppositeType().equals("person_customer")){//个人直投
						Person person = personDao.getById(project.getOppositeID().intValue());
						BpPersionDirPro pDirPro = bpPersionDirProDao.getByBpFundProjectId(pproject.getId());
						if(pDirPro==null){
							pDirPro = new BpPersionDirPro();
							this.initPersionP2p(pproject, pDirPro, person);
							bpPersionDirProDao.save(pDirPro);
						}else{
							this.initPersionP2p(pproject, pDirPro, person);
							bpPersionDirProDao.merge(pDirPro);
						}
					}	
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if(transitionName!=null&&!"".equals(transitionName)){
			Map vars = new HashMap();
			vars.put("OutletPrincipalExamineResult", transitionName);
			flowRunInfo.getVariables().putAll(vars);	
		}
		return 1;
	}
	@Override
	public Integer dealProjectStatus(FlowRunInfo flowRunInfo) {
		String status = flowRunInfo.getRequest().getParameter("status");
		String bidPlanId = flowRunInfo.getRequest().getParameter("bidPlanId");
		String projectId = flowRunInfo.getRequest().getParameter("projectId");
		if(null==projectId||"".equals(projectId)||"undefined".equals(projectId)){
			return 0;
		}
		String transitionName = flowRunInfo.getTransitionName();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String flowType = flowRunInfo.getRequest().getParameter("flowType");
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("slnRiskManagerCheckResult", transitionName);
		flowRunInfo.getVariables().putAll(vars);
		if(status!=null&&!"".equals(status)&&!"undefined".equals(status)){
			changeStatus(Long.parseLong(projectId), Short.parseShort(status));
		}
	//	if(transitionName.equals("完成")){
			List<SlActualToCharge> alist=slActualToChargeDao.listByBidPlanId(Long.valueOf(bidPlanId));
			for(SlActualToCharge a:alist){
				a.setIsCheck(Short.valueOf("0"));
				slActualToChargeDao.merge(a);
			}
		
			List<BpFundIntent> plist=bpFundIntentDao.getByBidPlanId(Long.valueOf(bidPlanId));
			for(BpFundIntent p:plist){
				p.setIsCheck(Short.valueOf("0"));
				bpFundIntentDao.merge(p);
			}
			
	//	}
		return 1;
	}
	@Override
	public Integer subPending(FlowRunInfo flowRunInfo) {
		ProcessRun run = flowRunInfo.getProcessRun();
		List<TaskImpl> list = flowTaskService.getSynchronousTasksByRunIdTransfer(run.getPiDbid());
		if(null!=list&&list.size()>0){
			for(TaskImpl task : list){
				if(task.getActivityName().equals("资金匹配")){
					task.setState(Task.STATE_SUSPENDED);
					flowTaskService.save(task);
					break;
				}
			}
		}
		return 1;
	}
	
	@Override
	public Integer completeMatchingTask(Long projectId, Long bidPlanId) {
		SlSmallloanProject project = dao.get(projectId);
		List<ProcessRun> runList = processRunService.getByProcessNameProjectId(project.getProjectId(), project.getBusinessType(), project.getFlowType());//最多获取到一条记录？
		Map map  = new HashMap();
		map.put("bidPlanId", bidPlanId);
		if(runList!=null&&runList.size()!=0){
			//需要将bidId加入到流程数据中
			//
			ProcessRun run = runList.get(0);
			List<TaskImpl> list = flowTaskService.getSynchronousTasksByRunIdTransfer(run.getPiDbid());
			if(null!=list&&list.size()>0){
				String transName = "";
				String taskId = "";
				if(list.size()>1){
					for(TaskImpl task : list){
						if(!task.getActivityName().equals("资金匹配")){
							transName = task.getActivityName();
							//jbpmService.completeTask(task.getId(),"放款终审",map);
							//break;
						}else if(task.getActivityName().equals("资金匹配")){
							taskId = task.getId();
						}
					}
				}else{
					TaskImpl task = list.get(0);
					transName = task.getActivityName();
					taskId = task.getId();
				}
				//jbpmService.endProcessInstance();
				
				jbpmService.completeTask(taskId,"",map);
			}
		}
		return 1;
	}
	@Override
	public Integer loansConfirmation(FlowRunInfo flowRunInfo) {
		String projectId = flowRunInfo.getRequest().getParameter("projectId");
		if(null!=projectId){
			SlSmallloanProject project = dao.get(Long.parseLong(projectId));
			project.setProjectStatus((short)2);
			dao.merge(project);
		}
		return 1;
	}
	@Override
	public Integer projectStop(FlowRunInfo flowRunInfo) {
		String projectId = flowRunInfo.getRequest().getParameter("projectId");
		String transitionName = flowRunInfo.getTransitionName();
		if (transitionName.contains("终止") || transitionName.contains("结束")) {
			//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
			if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
				SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
				sp.setProjectStatus((short)3);
				dao.merge(sp);
			}
			flowRunInfo.setStop(true);
		}
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("nextStepName", transitionName);
		flowRunInfo.getVariables().putAll(vars);
		return 1;
	}
	/***
	 * 流程入口
	 * **/
	@Override
	public Integer flowEntrance(FlowRunInfo flowRunInfo) {
		
		//保存项目信息
		
		//保存客户信息
		
		//保存资金款项信息
		
		//流程跳转
		
		return 1;
	}
	@Override
	public Integer bondsIssued(FlowRunInfo flowRunInfo) {
		String projectId = flowRunInfo.getRequest().getParameter("projectId");
		String userName = flowRunInfo.getRequest().getParameter("userName");
		//债券表发放---读取BpFundIntent表中 flag = 0  的记录
		if(null==projectId||"".equals(projectId)){
			return 0;
		}
		SlSmallloanProject project = dao.get(Long.parseLong(projectId));
		BpFundProject ownFundProject = bpFundProjectService.getByProjectId(Long.parseLong(projectId), (short)0);
		if(project.getOppositeType().equals("company_customer")){
			Enterprise enterprise  = enterpriseDao.getById(project.getOppositeID().intValue());
			BpBusinessOrPro bOrPro = bpBusinessOrProDao.getBpBusinessOrProByMoneyPlanId(ownFundProject.getId());
			if(bOrPro==null){
				bOrPro = new BpBusinessOrPro();
				this.initBusinessP2p(ownFundProject, bOrPro, enterprise);
				if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
					bOrPro.setObligatoryPersion(userName);
				}
				bpBusinessOrProDao.save(bOrPro);
			}else{
				this.initBusinessP2p(ownFundProject, bOrPro, enterprise);
				if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
					bOrPro.setObligatoryPersion(userName);
				}
				bpBusinessOrProDao.merge(bOrPro);
			}
		}else if(project.getOppositeType().equals("person_customer")){
			Person person = personDao.getById(project.getOppositeID().intValue());
			BpPersionOrPro pOrPro = bpPersionOrProDao.getByBpFundProjectId(ownFundProject.getId());
			if(pOrPro==null){
				pOrPro = new BpPersionOrPro();
				this.initPersionP2p(ownFundProject, pOrPro, person);
				if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
					pOrPro.setObligatoryPersion(userName);
				}
				bpPersionOrProDao.save(pOrPro);
			}else{
				this.initPersionP2p(ownFundProject, pOrPro, person);
				if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
					pOrPro.setObligatoryPersion(userName);
				}
				bpPersionOrProDao.merge(pOrPro);
			}
		}
		return 1;	
	}
	@Override
	public Integer historyCreditFlow(FlowRunInfo flowRunInfo) {
		String chargeJson = flowRunInfo.getRequest().getParameter("chargeJson");
		String projectId = flowRunInfo.getRequest().getParameter(
				"slSmallloanProject.projectId");
		// 保存项目信息
		try {
			SlSmallloanProject project = new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSmallloanProject");
			SlSmallloanProject sl = dao.get(project.getProjectId());
			BeanUtil.copyNotNullProperties(sl, project);
			dao.merge(sl);
			
			
			
			if (null != chargeJson && !"".equals(chargeJson)&& !"undefined".equals(chargeJson)) {
				//手续费更新保存
				slActualToChargeService.saveJson(chargeJson,
						project.getProjectId(), "SmallLoan", (short) 0, 1l, null);
			}
			BpFundProject fundProject = bpFundProjectDao.getByProjectId(Long
					.valueOf(projectId), Short.parseShort(0 + ""));
			if (null == fundProject) {
				fundProject = new BpFundProject();
			}
			BeanUtil.copyNotNullProperties(fundProject, sl);
			fundProject.setFlag(Short.parseShort(String.valueOf(0)));
			fundProject.setOwnJointMoney(sl.getProjectMoney());
			fundProject.setSlFundIntents(null);
			bpFundProjectDao.merge(fundProject);
			//财务信息更新保存
			String slFundIentJson = flowRunInfo.getRequest().getParameter("fundIntentJsonData");
			BpFundProject oldBpFund2=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort("1"));
			slFundIntentService.savejson1(slFundIentJson, Long.parseLong(projectId), "SmallLoan", Short.parseShort("0"), Short.parseShort("0"), sl.getCompanyId());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return 1;
	}
	//项目更新保存
	@Override
	public Integer complateFlow(FlowRunInfo flowRunInfo) {
		String projectId = flowRunInfo.getRequest().getParameter("projectId");
		String transitionName = flowRunInfo.getTransitionName(); 
		if(null!=projectId&&!"".equals(projectId)){
			SlSmallloanProject project = dao.get(Long.parseLong(projectId));
			List<ProcessRun> runList = processRunService.getByProcessNameProjectId(project.getProjectId(), project.getBusinessType(), project.getFlowType());//最多获取到一条记录？
			if(runList!=null&&runList.size()!=0){
				if(transitionName!=null&&transitionName.equals("资金匹配")){
					ProcessRun run = runList.get(0);
					jbpmService.endProcessInstance(run.getPiId());
				}
			}
		}
		return 1;
	}
	
	//流程跳转
	@Override
	public Integer goToNext(FlowRunInfo flowRunInfo) {
		String projectId = flowRunInfo.getRequest().getParameter("projectId");
		String transitionName = flowRunInfo.getTransitionName();
		if(null==transitionName){
			return 1;
		}
		if (transitionName.contains("终止") || transitionName.contains("结束")) {
			//String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
			if(null!=projectId&&!"".equals(projectId)&&!"undefined".equals(projectId)){
				SlSmallloanProject sp = dao.get(Long.parseLong(projectId));
				sp.setProjectStatus((short)3);
				dao.merge(sp);
			}
			flowRunInfo.setStop(true);
		}
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("OutletPrincipalExamineResult", transitionName);
		flowRunInfo.getVariables().putAll(vars);
		return 1;
	}
	@Override
	public Integer urgeNext(FlowRunInfo flowRunInfo) {
		String urgeData = flowRunInfo.getRequest().getParameter("urgeData");
		String transitionName = flowRunInfo.getTransitionName();
		if(null!=urgeData&&!"".equals(urgeData)&&!"undefined".equals(urgeData)&&urgeData.trim().length()!=0){
			String[] datas = urgeData.split("@");
			if(datas!=null){
				for(int i=0;i<datas.length;i++){
					String data = datas[i];
					JSONParser parser = new JSONParser(new StringReader(data));
					if(data!=null&&!"".equals(data)&&!"undefined".equals(data)){
						try {
							SlUrgeRecord record = (SlUrgeRecord) JSONMapper.toJava(parser.nextValue(), SlUrgeRecord.class);
							if(record!=null&&record.getRecordId()!=null){
								SlUrgeRecord rd = slUrgeRecordService.get(record.getRecordId());
								BeanUtil.copyNotNullProperties(rd, record);
								slUrgeRecordService.merge(rd);
							}else{
								slUrgeRecordService.save(record);
							}
						} catch (TokenStreamException e) {
							e.printStackTrace();
						} catch (RecognitionException e) {
							e.printStackTrace();
						} catch (MapperException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		if(transitionName.contains("终止") || transitionName.contains("结束")) {
			flowRunInfo.setStop(true);
		}
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("OutletPrincipalExamineResult", transitionName);
		flowRunInfo.getVariables().putAll(vars);
		return 1;
	}
	@Override
	public Integer dltcSignContract(FlowRunInfo flowRunInfo){
		String projectId = flowRunInfo.getRequest().getParameter("projectId");
		SlSmallloanProject sl = this.get(Long.valueOf(projectId));
		SlSmallloanProject project = new SlSmallloanProject();//前台生成的project可能会包含bankId所以必须要有
		Short flag1 = 1;
		if(sl.getOppositeType().equals("company_customer"))flag1=0;
		EnterpriseBank enterpriseBank = new EnterpriseBank(); 
		try {
			BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank, "enterpriseBank");
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "slSmallloanProject");
			if (enterpriseBank.getId() == null||enterpriseBank.getId()==0) {
//				enterpriseBank.setId(null);
				List<EnterpriseBank> list = enterpriseBankService.getBankList(
						sl.getOppositeID().intValue(), flag1, Short
						.valueOf("0"),Short.valueOf("0"));
				for (EnterpriseBank e : list) {
					e.setIscredit(Short.valueOf("1"));
					enterpriseBankDao.save(e);
				}
				enterpriseBank.setEnterpriseid(sl.getOppositeID()
						.intValue());
				enterpriseBank.setIscredit(Short.valueOf("0"));
				enterpriseBank.setIsEnterprise(flag1);
				enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
				enterpriseBank.setIsInvest(Short.valueOf("0"));
				enterpriseBankDao.save(enterpriseBank);
				project.setBankId(enterpriseBank.getId().longValue());
			} else {
				EnterpriseBank bank = (EnterpriseBank) enterpriseBankDao.getById(enterpriseBank.getId());
				bank.setName(enterpriseBank.getName());
				bank.setOpenType(enterpriseBank.getOpenType());
				bank.setAccountnum(enterpriseBank.getAccountnum());
				bank.setAccountType(enterpriseBank.getAccountType());
				bank.setBankid(enterpriseBank.getBankid());
				bank.setBankname(enterpriseBank.getBankname());
				bank.setAreaId(enterpriseBank.getAreaId());
				bank.setAreaName(enterpriseBank.getAreaName());
				bank.setBankOutletsName(enterpriseBank.getBankOutletsName());
				enterpriseBankDao.save(bank);
			}
			BeanUtil.copyNotNullProperties(sl, project);
			this.merge(sl);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("大连天储信贷流程-合同签署提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	
	//生成债权标
	private Integer generateOrPlan(SlSmallloanProject project){
		return 1;
	}
	
	//生成直投标
	private Integer generateDirPlan(SlSmallloanProject project){
		if(project.getOppositeType().equals("company_customer")){
			genrateDirBusiness(project);
		}else if(project.getOppositeType().equals("person_customer")){
			genrateDirPerson(project);
		}
		return 1;
	}
	
	//生成个人直投标
	private Integer genrateDirPerson(SlSmallloanProject project){
		BpFundProject pproject = this.bpFundProjectDao.getByProjectId(project.getProjectId(), (short)1);
		Person person = personDao.getById(project.getOppositeID().intValue());
		BpPersionDirPro pDirPro = bpPersionDirProDao.getByBpFundProjectId(pproject.getId());
		if(pDirPro==null){
			pDirPro = new BpPersionDirPro();
			this.initPersionP2p(pproject, pDirPro, person);
			bpPersionDirProDao.save(pDirPro);
		}else{
			this.initPersionP2p(pproject, pDirPro, person);
			bpPersionDirProDao.merge(pDirPro);
		}
		return 1;
	}
	//生成企业直投标
	private Integer genrateDirBusiness(SlSmallloanProject project){
		BpFundProject pproject = this.bpFundProjectDao.getByProjectId(project.getProjectId(), (short)1);
		Enterprise enterprise  = enterpriseDao.getById(project.getOppositeID().intValue());
		BpBusinessDirPro bDirPro = bpBusinessDirProDao.getByBpFundProjectId(pproject.getId());
		if(bDirPro==null){
			bDirPro = new BpBusinessDirPro();
			this.initBusinessP2p(pproject, bDirPro, enterprise);
			bpBusinessDirProDao.save(bDirPro);
		}else{
			this.initBusinessP2p(pproject, bDirPro, enterprise);
			bpBusinessDirProDao.merge(bDirPro);
		}
		return 1;
	}
	//生成资金方案
	private Integer generateFundProject(FlowRunInfo flowRunInfo){
		String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
		if(projectId!=null&&!"".equals(projectId)){
			SlSmallloanProject persistent = this.dao.get(Long.parseLong(projectId));
			for(int i=0;i<2;i++){
				BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort(i+""));
				if(null==fundProject){
					fundProject=new BpFundProject();
				}
				try {
					BeanUtil.copyNotNullProperties(fundProject, persistent);
					fundProject.setFlag(Short.parseShort(String.valueOf(i)));
					fundProject.setSlFundIntents(null);
					bpFundProjectDao.merge(fundProject);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return 1;
	}
	//流程跳转---只处理流程中的跳转问题
	@Override
	public Integer jumpFlow(FlowRunInfo flowRunInfo){
		String transitionName = flowRunInfo.getTransitionName();
		if (transitionName.contains("终止") || transitionName.contains("结束")) {
			flowRunInfo.setStop(true);
			updateProjectStatus(flowRunInfo,(short)3);
		}
		Map<String, Object> vars = new HashMap<String, Object>();
		vars.put("nextStepName", transitionName);
		flowRunInfo.getVariables().putAll(vars);
		return 1;
	}
	//处理项目信息
	@Override
	public Integer dealSmallloanBaseInfo(FlowRunInfo flowRunInfo){
		String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
		SlSmallloanProject project = new SlSmallloanProject();
		if(null!=projectId&&!"".equals(projectId)){
			try {
				BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "slSmallloanProject");
				SlSmallloanProject persistent = this.dao.get(Long.parseLong(projectId));
				BeanUtil.copyNotNullProperties(persistent, project);
				dao.merge(persistent);
				jumpFlow(flowRunInfo);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return 1;
	}
	public SlSmallloanProject dealSmallloan(FlowRunInfo flowRunInfo){
		String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
		String projectStatus = flowRunInfo.getRequest().getParameter("projectStatus");
		SlSmallloanProject project = new SlSmallloanProject();
		if(null!=projectId&&!"".equals(projectId)){
			try {
				BeanUtil.populateEntity(flowRunInfo.getRequest(), project, "slSmallloanProject");
				SlSmallloanProject persistent = this.dao.get(Long.parseLong(projectId));
				BeanUtil.copyNotNullProperties(persistent, project);
				if(null!=projectStatus&&!"".equals(projectStatus)){
					persistent.setProjectStatus(Short.valueOf(projectStatus));
				}
				dao.merge(persistent);
				return persistent;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	//处理处理客户信息
	private Integer dealCustomer(FlowRunInfo flowRunInfo,SlSmallloanProject project) throws IllegalAccessException, InvocationTargetException{
		if(null==project.getOppositeType()||"".equals(project.getOppositeType())){
			
		}else{
			if("person_customer".equals(project.getOppositeType())){
				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,"person");
				if(null==person){
					return 1;
				}
				if(null==person||null==person.getId()||person.getId()==0){
					personDao.save(person);
				}else{
					// 更新person信息开始
					Person persistentPerson = this.personDao.getById(person.getId());
					BeanUtil.copyNotNullProperties(persistentPerson, person);
					personDao.merge(persistentPerson);
				}
			}else if("company_customer".equals(project.getOppositeType())){
				Enterprise enterprise = new Enterprise();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise,
						"enterprise");
				if(null==enterprise){
					return 1;
				}
				Enterprise epersistent = this.enterpriseDao
						.getById(enterprise.getId());
				/*epersistent.setEnterprisename(enterprise.getEnterprisename());
				epersistent.setArea(enterprise.getArea());
				epersistent.setShortname(enterprise.getShortname());
				epersistent.setHangyeType(enterprise.getHangyeType());
				epersistent.setOrganizecode(enterprise.getOrganizecode());
				epersistent.setCciaa(enterprise.getCciaa());
				epersistent.setTelephone(enterprise.getTelephone());
				epersistent.setPostcoding(enterprise.getPostcoding());
				epersistent.setRootHangYeType(enterprise.getRootHangYeType());*/
				BeanUtil.copyNotNullProperties(epersistent, enterprise);
				// 更新法人信息
				Person person = new Person();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), person,"person");
				if (null != person.getId() && person.getId() != 0) {
					Person ppersistent = this.personDao
							.getById(epersistent.getLegalpersonid());
					ppersistent.setName(person.getName());
					ppersistent.setSex(person.getSex());
					ppersistent.setCardtype(person.getCardtype());
					ppersistent.setCardnumber(person.getCardnumber());
					ppersistent.setCellphone(person.getCellphone());
					ppersistent.setSelfemail(person.getSelfemail());
					ppersistent.setCompanyId(ContextUtil.getLoginCompanyId());
					personDao.merge(ppersistent);
				
				} else {
					Long currentUserId = ContextUtil.getCurrentUserId();
					Person p = new Person();
					p.setId(null);
					p.setCreater(ContextUtil.getCurrentUser().getFullname());
					p.setBelongedId(currentUserId.toString());
					p.setCreaterId(currentUserId);
					p.setCreatedate(new Date());
					p.setCompanyId(ContextUtil.getLoginCompanyId());
					p.setName(person.getName());
					p.setSex(person.getSex());
					p.setCardtype(person.getCardtype());
					p.setCardnumber(person.getCardnumber());
					p.setCellphone(person.getCellphone());
					p.setSelfemail(person.getSelfemail());
					personDao.save(p);
					epersistent.setLegalpersonid(p.getId());
				}
				enterpriseDao.merge(epersistent);
			}
		} 
		return 1;
	}
	//处理手续费用清单
	public Integer dealActualToCharge(FlowRunInfo flowRunInfo, SlSmallloanProject project){
		String slActualToChargeJson = flowRunInfo.getRequest().getParameter("slActualToChargeJson");
		if(null!=slActualToChargeJson&&!"".equals(slActualToChargeJson)){
			return 1;
		}
		slActualToChargeService.savejson(slActualToChargeJson, project.getProjectId(),"SmallLoan", Short.parseShort("0"), project.getCompanyId());
		return 1;
	}
	//共同借款人信息
	public Integer dealBorrowers(FlowRunInfo flowRunInfo, SlSmallloanProject project){
		String borrowerInfo = flowRunInfo.getRequest().getParameter("borrowerInfo");
		List<BorrowerInfo> listBO = new ArrayList<BorrowerInfo>();
		if (null != borrowerInfo && !"".equals(borrowerInfo)) {
			String[] borrowerInfoArr = borrowerInfo.split("@");
			for (int i = 0; i < borrowerInfoArr.length; i++) {
				String str = borrowerInfoArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					BorrowerInfo bo = (BorrowerInfo) JSONMapper.toJava(
							parser.nextValue(), BorrowerInfo.class);
					listBO.add(bo);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (listBO.size() > 0) {
			for (int i = 0; i < listBO.size(); i++) {
				BorrowerInfo bo = listBO.get(i);
				if (bo.getBorrowerInfoId() == null) {
					bo.setProjectId(project.getProjectId());
					bo.setBusinessType(project.getBusinessType());
					bo.setOperationType(project.getOperationType());
					this.borrowerInfoDao.save(bo);
				} else {
					BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo
							.getBorrowerInfoId());
					BeanUtils.copyProperties(boPersistent, bo);
					this.borrowerInfoDao.merge(boPersistent);
				}
				if (null != bo.getType() && bo.getType() == 0) {
					if (null != bo.getCustomerId()) {
						Enterprise e = this.enterpriseDao.getById(bo
								.getCustomerId());
						e.setArea(bo.getAddress());
						e.setCciaa(bo.getCardNum());
						e.setTelephone(bo.getTelPhone());
						this.enterpriseDao.merge(e);
					}
				} else if (null != bo.getType() && bo.getType() == 1) {
					Person p = this.personDao.getById(bo.getCustomerId());
					p.setPostaddress(bo.getAddress());
					p.setCardnumber(bo.getCardNum());
					p.setCellphone(bo.getTelPhone());
					this.personDao.merge(p);
				}
			}
		}
		return 1;
	}
	//股东投资人信息
	public Integer dealShareequity(FlowRunInfo flowRunInfo,SlSmallloanProject project){
		String gudongInfo = flowRunInfo.getRequest().getParameter("gudongInfo");
		// 保存股东信息
		List<EnterpriseShareequity> listES = new ArrayList<EnterpriseShareequity>();
		if (null != gudongInfo && !"".equals(gudongInfo)) {
			String[] shareequityArr = gudongInfo.split("@");
			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					EnterpriseShareequity enterpriseShareequity = (EnterpriseShareequity) JSONMapper
							.toJava(parser.nextValue(),
									EnterpriseShareequity.class);
					listES.add(enterpriseShareequity);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (listES.size() > 0) {
			for (int i = 0; i < listES.size(); i++) {
				EnterpriseShareequity es = listES.get(i);
				if (es.getId() == null) {
					es.setEnterpriseid(project.getOppositeID().intValue());
					this.enterpriseShareequityDao.save(es);
				} else {
					EnterpriseShareequity esPersistent = this.enterpriseShareequityDao
							.load(es.getId());
					BeanUtils.copyProperties(es, esPersistent,
							new String[] { "id", "enterpriseid" });
					this.enterpriseShareequityDao.merge(esPersistent);
				}
			}
		}
		return 1;
	}
	
	@Override
	public Integer generateProduct(FlowRunInfo flowRunInfo) {
		String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData");
		String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
	
		if(null!=projectId&&!"".equals(projectId)){
			dealSmallloanBaseInfo(flowRunInfo);//更新项目信息
			generateFundProject(flowRunInfo);//s生成资金方案
			
			SlSmallloanProject project = dao.get(Long.parseLong(projectId));
			//手续清单
			if(null!=slActualToChargeJsonData&&!"".equals(slActualToChargeJsonData)&&!"undefined".equals(slActualToChargeJsonData)){
	        slActualToChargeService.saveJson(slActualToChargeJsonData,project.getProjectId(),"SmallLoan",(short)0,1l,null);

            }
			BpFundProject bpFund = bpFundProjectService.getByProjectId(project.getProjectId(), (short)1);
			bpFund.setPlatFormJointMoney(project.getProjectMoney());
			bpFundProjectService.merge(bpFund);
			generateDirPlan(project);//生成直投标
			jumpFlow(flowRunInfo);
		}
		return 1;
	}
	@Override
	public Integer changeProjectStatus(FlowRunInfo flowRunInfo) {
		String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
		if(projectId==null||"".equals(projectId)){
			return 0;
		}
		SlSmallloanProject project = dao.get(Long.parseLong(projectId));
		updateProjectStatus(project,(short)1);
		return 1;
	}
	//更新项目状态
	private Integer updateProjectStatus(FlowRunInfo flowRunInfo,short stats){
		String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
		if(projectId==null||"".equals(projectId)){
			return 0;
		}
		SlSmallloanProject project = dao.get(Long.parseLong(projectId));
		updateProjectStatus(project,stats);
		return 1;
	}
	//更新项目状态
	public Integer updateProjectStatus(SlSmallloanProject project,short stats){
		project.setProjectStatus(stats);
		dao.merge(project);
		return 1;
	}
	//生成资金方案
	public Integer generateFundProject(FlowRunInfo flowRunInfo,SlSmallloanProject project) throws IllegalAccessException, InvocationTargetException{
		//fundingScheme:0，添加数据；1，更数据
		String fundingScheme = flowRunInfo.getRequest().getParameter("fundingScheme");
		if(null!=fundingScheme&&!"".equals(fundingScheme)){
			if("0".equals(fundingScheme)){
				for(int i=0;i<2;i++){
					BpFundProject fundProject=bpFundProjectDao.getByProjectId(project.getProjectId(),Short.parseShort(i+""));
					if(null==fundProject){
						fundProject=new BpFundProject();
					}
					try {
						BeanUtil.copyNotNullProperties(fundProject, project);
						fundProject.setFlag(Short.parseShort(String.valueOf(i)));
						fundProject.setSlFundIntents(null);
						bpFundProjectDao.merge(fundProject);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}else if("1".equals(fundingScheme)){
				//自由资金
				BpFundProject ownProject = new BpFundProject();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), ownProject, "ownBpFundProject");
				if(null!=ownProject&&null!=ownProject.getId()&&null!=ownProject.getOwnJointMoney()&&ownProject.getOwnJointMoney().compareTo(new BigDecimal(0))!=0){
					BpFundProject fundProject = bpFundProjectDao.get(ownProject.getId());
					BeanUtil.copyNotNullProperties(fundProject, ownProject);
					bpFundProjectDao.merge(fundProject);
				}
				//平台资金
				BpFundProject platProject = new BpFundProject();
				BeanUtil.populateEntity(flowRunInfo.getRequest(), platProject, "platFormBpFundProject");
				if(null!=platProject&&null!=platProject.getId()&&null!=platProject.getPlatFormJointMoney()&&platProject.getPlatFormJointMoney().compareTo(new BigDecimal(0))!=0){
					BpFundProject fundProject = bpFundProjectDao.get(platProject.getId());
					BeanUtil.copyNotNullProperties(fundProject, platProject);
					bpFundProjectDao.merge(fundProject);
				}
			}
		}
		return 1;
	}
	//贷款帐户信息
	public Integer dealBankAccount(FlowRunInfo flowRunInfo,SlSmallloanProject project) throws Exception{
		EnterpriseBank enterpriseBank = new EnterpriseBank();
		short flag = 0;
		if(null!=project.getOppositeType()&&"".equals(project.getOppositeType())){
			flag = 1;
		}
		BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank,
				"enterpriseBank");
		if(null==enterpriseBank){
			return 1;
		}
		if (enterpriseBank.getId() == null || enterpriseBank.getId() == 0) {
			List<EnterpriseBank> list = enterpriseBankService.getBankList(
					project.getOppositeID().intValue(), flag, Short
							.valueOf("0"),Short.valueOf("0"));
			for (EnterpriseBank e : list) {
				e.setIscredit(Short.valueOf("1"));
				creditBaseDao.updateDatas(e);
			}
			enterpriseBank.setEnterpriseid(project.getOppositeID()
					.intValue());
			enterpriseBank.setIscredit(Short.valueOf("0"));
			enterpriseBank.setIsEnterprise(flag);
			enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
			enterpriseBank.setIsInvest(Short.valueOf("0"));
		
			creditBaseDao.saveDatas(enterpriseBank);
		} else {
			EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(
					EnterpriseBank.class, enterpriseBank.getId());
			bank.setAccountnum(enterpriseBank.getAccountnum());
			bank.setAccountType(enterpriseBank.getAccountType());
			bank.setBankid(enterpriseBank.getBankid());
			bank.setBankname(enterpriseBank.getBankname());
			bank.setName(enterpriseBank.getName());
			bank.setOpenType(enterpriseBank.getOpenType());
			bank.setAreaId(enterpriseBank.getAreaId());
			bank.setAreaName(enterpriseBank.getAreaName());
			bank.setBankOutletsName(enterpriseBank.getBankOutletsName());
			creditBaseDao.updateDatas(bank);
		}
		return 1;
	}
	
	//风险审核提交方法
	@Override
	public Integer riskCheckNextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String flag=flowRunInfo.getRequest().getParameter("flag");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}
						}
					}
					vars.put("RiskExaminationResult", transitionName);
					flowRunInfo.getVariables().putAll(vars);
				}
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("彩云信贷流程-风险审核提交下一步出错：" + e.getMessage());
			return 0;
		}
	}
	//直投、债权生成
	public Integer generatePro(FlowRunInfo flowRunInfo,SlSmallloanProject project){
		String fundingScheme = flowRunInfo.getRequest().getParameter("fundingScheme");
		String generatePro = flowRunInfo.getRequest().getParameter("generatePro");
		String platFormBpFundProjectMoney=flowRunInfo.getRequest().getParameter("platFormBpFundProjectMoney");
		
		if(null!=fundingScheme&&!"".equals(fundingScheme)){
			if("3".equals(fundingScheme)||(null!=generatePro&&"1".equals(generatePro))){
				BpFundProject pproject = bpFundProjectDao.getByProjectId(project.getProjectId(), (short)1);
				if(pproject.getPlatFormJointMoney()!=null&&pproject.getPlatFormJointMoney().compareTo(new BigDecimal(0))!=0)
					if(project.getOppositeType().equals("company_customer")){//企业直投
						Enterprise enterprise  = enterpriseDao.getById(project.getOppositeID().intValue());
						BpBusinessDirPro bDirPro = bpBusinessDirProDao.getByMoneyPlanId(pproject.getId());
						if(bDirPro==null){
							bDirPro = new BpBusinessDirPro();
							this.initBusinessP2p(pproject, bDirPro, enterprise);
							bpBusinessDirProDao.save(bDirPro);
						}else{
							this.initBusinessP2p(pproject, bDirPro, enterprise);
							bpBusinessDirProDao.merge(bDirPro);
						}
					}else if(project.getOppositeType().equals("person_customer")){//个人直投
						Person person = personDao.getById(project.getOppositeID().intValue());
						BpPersionDirPro pDirPro = bpPersionDirProDao.getByBpFundProjectId(pproject.getId());
						if(pDirPro==null){
							pDirPro = new BpPersionDirPro();
							this.initPersionP2p(pproject, pDirPro, person);
							bpPersionDirProDao.save(pDirPro);
						}else{
							this.initPersionP2p(pproject, pDirPro, person);
							bpPersionDirProDao.merge(pDirPro);
						}
					}
			}else if("4".equals(fundingScheme)){
				BpFundProject ownFundProject = bpFundProjectService.getByProjectId(project.getProjectId(), (short)0);
				String userName = flowRunInfo.getRequest().getParameter("userName");
				if(ownFundProject.getOwnJointMoney()!=null&&ownFundProject.getOwnJointMoney().compareTo(new BigDecimal(0))!=0)
				if(project.getOppositeType().equals("company_customer")){
					Enterprise enterprise  = enterpriseDao.getById(project.getOppositeID().intValue());
					BpBusinessOrPro bOrPro = bpBusinessOrProDao.getBpBusinessOrProByMoneyPlanId(ownFundProject.getId());
					if(bOrPro==null){
						bOrPro = new BpBusinessOrPro();
						this.initBusinessP2p(ownFundProject, bOrPro, enterprise);
						if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
							bOrPro.setObligatoryPersion(userName);
						}
						bpBusinessOrProDao.save(bOrPro);
					}else{
						this.initBusinessP2p(ownFundProject, bOrPro, enterprise);
						if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
							bOrPro.setObligatoryPersion(userName);
						}
						bpBusinessOrProDao.merge(bOrPro);
					}
				}else if(project.getOppositeType().equals("person_customer")){
					Person person = personDao.getById(project.getOppositeID().intValue());
					BpPersionOrPro pOrPro = bpPersionOrProDao.getByBpFundProjectId(ownFundProject.getId());
					if(pOrPro==null){
						pOrPro = new BpPersionOrPro();
						this.initPersionP2p(ownFundProject, pOrPro, person);
						if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
							pOrPro.setObligatoryPersion(userName);
						}
						bpPersionOrProDao.save(pOrPro);
					}else{
						this.initPersionP2p(ownFundProject, pOrPro, person);
						if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
							pOrPro.setObligatoryPersion(userName);
						}
						bpPersionOrProDao.merge(pOrPro);
					}
				}
			}
		}
		return 1;
	}
	
	public Integer dealbpFundIntent(FlowRunInfo flowRunInfo,SlSmallloanProject project){
		String bidPlanId = flowRunInfo.getRequest().getParameter("bidPlanId");
		//ownFundIntent------自有借款人款项信息
		//platFundIntent-----平台借款人款项信息
		//slFundIntent-------小贷借款人款系信息
		//自由资金借款人款项信息
		String ownFundIntent = flowRunInfo.getRequest().getParameter("ownFundIntent");
		BpFundProject ownProject = bpFundProjectDao.getByProjectId(project.getProjectId(), (short)0);
		if(null != project && !"".equals(ownFundIntent) && null !=ownFundIntent){
			if(null==bidPlanId||"".equals(bidPlanId)||!bidPlanId.matches("[0-9]*")){
				saveSlFundIntent(project,ownFundIntent,null,ownProject.getId());
			}else{
				saveSlFundIntent(project,ownFundIntent,Long.parseLong(bidPlanId),ownProject.getId());
			}
		}
		//平台资金借款人款项信息
		String platFundIntent = flowRunInfo.getRequest().getParameter("platFundIntent");
		BpFundProject platProject = bpFundProjectDao.getByProjectId(project.getProjectId(), (short)1);
		if(null != project && !"".equals(platFundIntent) && null !=platFundIntent &&bidPlanId!=null&&null!=platProject){
			saveSlFundIntent(project,platFundIntent,Long.parseLong(bidPlanId),platProject.getId());
		}
		
		//小贷借款人款项信息
		String slFundIntent = flowRunInfo.getRequest().getParameter("slFundIntent");
		
		
		return 1;
	}
	
	@Override
	public Integer doNext(FlowRunInfo flowRunInfo) {
		//保存项目信息
		SlSmallloanProject project = dealSmallloan(flowRunInfo);
		if(null!=project){
			//保存客户信息
			try {
				dealCustomer(flowRunInfo, project);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			try {
				dealBankAccount(flowRunInfo,project);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			//保存手续费用清单
			dealActualToCharge(flowRunInfo, project);
			//保存共同借款人
			dealBorrowers(flowRunInfo, project);
			//股东投资人
			dealShareequity(flowRunInfo, project);
			//生成资金方案
			try {
				generateFundProject(flowRunInfo,project);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			generatePro(flowRunInfo,project);
			dealbpFundIntent(flowRunInfo,project);
		}
		//流程跳转
		goToNext(flowRunInfo);
		return 1;
	}
	@Override
	public Integer dltcUpdateOwn(FlowRunInfo flowRunInfo) {
		String projectId = flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
		if(null!=projectId&&!"".equals(projectId)&&projectId.matches("[0-9]*")){
			SlSmallloanProject project = dao.get(Long.parseLong(projectId));
			BpFundProject ownProject = bpFundProjectService.getByProjectId(Long.parseLong(projectId), (short)0);
			ownProject.setOwnJointMoney(project.getProjectMoney());
			//ownProject.setOwnAccrual(ownAccrual)
			bpFundProjectService.merge(ownProject);
		}
		return 1;
	}
	
	/**
	 * 单纯只保存项目表里面的信息  
	 * add by linyan
	 * 2014-8-21
	 * @param flowRunInfo
	 * @return
	 */
	@Override
	public Integer updateProjectInfo(FlowRunInfo flowRunInfo) {
		//保存项目信息
		SlSmallloanProject project = new SlSmallloanProject();
		try {
			BeanUtil.populateEntity(flowRunInfo.getRequest(), project,"slSmallloanProject");
			SlSmallloanProject sl=dao.get(project.getProjectId());
			BeanUtil.copyNotNullProperties(sl, project);
			dao.merge(sl);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	private void changeStatusBidPlan(Long bidPlanId,int status){
		PlBidPlan project =plBidPlanDao.get(bidPlanId);
		project.setState(status);
		plBidPlanDao.merge(project);
	}
	@Override
	public Integer saveFastSmallProjectInfoNextStep(FlowRunInfo flowRunInfo){
		try{
			String loanId=flowRunInfo.getRequest().getParameter("loanId");//线上申请记录id
			String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
			SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
			SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
			BeanUtil.copyNotNullProperties(project, slSmallloanProject);
			//保存客户信息
			Person person=new Person();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
			Spouse spouse=new Spouse();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse, "spouse");
			WorkCompany workCompany=new WorkCompany();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), workCompany, "workCompany");
			personDao.savePersonInfo(person, spouse, workCompany);
			//保存资金需求
			BpMoneyBorrowDemand bpMoneyBorrowDemand=new BpMoneyBorrowDemand();
			BeanUtil.populateEntity(flowRunInfo.getRequest(), bpMoneyBorrowDemand, "bpMoneyBorrowDemand");
			bpMoneyBorrowDemand.setProjectID(slSmallloanProject.getProjectId());
			if (bpMoneyBorrowDemand.getBorrowid() == null) {
				this.bpMoneyBorrowDemandDao.save(bpMoneyBorrowDemand);
			} else {
				BpMoneyBorrowDemand bbd = bpMoneyBorrowDemandDao.load(bpMoneyBorrowDemand.getBorrowid());
				BeanUtil.copyNotNullProperties(bbd, bpMoneyBorrowDemand);
				this.bpMoneyBorrowDemandDao.merge(bbd);
			}
				
			project.setProjectStatus(Short.valueOf("2"));
			
			//loanId
			if(null != loanId && !"".equals(loanId)){
				BpFinanceApplyUser applyUser=bpFinanceApplyUserDao.get(Long.valueOf(loanId));
				if(null!=applyUser){
					applyUser.setState("6");
					bpFinanceApplyUserDao.merge(applyUser);
				}
			}
					
			// 平台资金款项信息
			if(null!=fundProjectId && !fundProjectId.equals("")){
				BpFundProject platFormFund = bpFundProjectService.get(Long.valueOf(fundProjectId));
				if (null != platFormFund) {
					BeanUtil.copyNotNullProperties(platFormFund,slSmallloanProject);
					platFormFund.setPlatFormJointMoney(slSmallloanProject.getProjectMoney());
					platFormFund.setProjectStatus(Short.valueOf("1"));
					bpFundProjectDao.merge(platFormFund);
				}
				if(platFormFund.getPlatFormJointMoney().compareTo(new BigDecimal(0))>0){
					//保存个人直投标项目缓信息
					BpPersionDirPro  bpPersionDirPro =bpPersionDirProDao.getByBpFundProjectId(platFormFund.getId());
					if(null==bpPersionDirPro){
						bpPersionDirPro=new BpPersionDirPro();
						this.initPersionP2p(platFormFund,bpPersionDirPro,person);
						if(null != loanId && !"".equals(loanId)){
							bpPersionDirPro.setLoanId(Long.valueOf(loanId));
						}
						bpPersionDirProDao.save(bpPersionDirPro);
					}else{
						this.initPersionP2p(platFormFund,bpPersionDirPro,person);
						if(null != loanId && !"".equals(loanId)){
							bpPersionDirPro.setLoanId(Long.valueOf(loanId));
						}
						bpPersionDirProDao.merge(bpPersionDirPro);
					}
				}
			}
		
			dao.merge(project);
		
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public Integer saveOrFastSmallProjectInfoNextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String flag=flowRunInfo.getRequest().getParameter("flag");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								String thisTaskName=flowRunInfo.getRequest().getParameter("thisTaskName");
								String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								Spouse spouse=new Spouse();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse, "spouse");
								WorkCompany workCompany=new WorkCompany();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), workCompany, "workCompany");
								personDao.savePersonInfo(person, spouse, ((null!=thisTaskName && thisTaskName.equals("saveInfo"))?workCompany:null));
								if(null!=thisTaskName && thisTaskName.equals("saveInfo")){
									//保存资金需求
										BpMoneyBorrowDemand bpMoneyBorrowDemand=new BpMoneyBorrowDemand();
										BeanUtil.populateEntity(flowRunInfo.getRequest(), bpMoneyBorrowDemand, "bpMoneyBorrowDemand");
										bpMoneyBorrowDemand.setProjectID(slSmallloanProject.getProjectId());
										if (bpMoneyBorrowDemand.getBorrowid() == null) {
											this.bpMoneyBorrowDemandDao.save(bpMoneyBorrowDemand);
										} else {
											BpMoneyBorrowDemand bbd = bpMoneyBorrowDemandDao.load(bpMoneyBorrowDemand.getBorrowid());
											BeanUtil.copyNotNullProperties(bbd, bpMoneyBorrowDemand);
											this.bpMoneyBorrowDemandDao.merge(bbd);
										}
											
										project.setProjectStatus(Short.valueOf("2"));
								}	
									// 平台资金款项信息
									if(null!=fundProjectId && !fundProjectId.equals("")){
										 
										String mineType=flowRunInfo.getRequest().getParameter("ownBpFundProject.mineType");
										String mineId=flowRunInfo.getRequest().getParameter("ownBpFundProject.mineId");
										BpFundProject ownFund = bpFundProjectService.get(Long.valueOf(fundProjectId));
										if (null != ownFund) {
											BeanUtil.copyNotNullProperties(ownFund,project);
											ownFund.setOwnJointMoney(project.getProjectMoney());
											ownFund.setProjectStatus(Short.valueOf("1"));
											if(null!=mineType && !mineType.equals("")){
												ownFund.setMineType(mineType);
											}
											if(null!=mineId && !mineId.equals("")){
												ownFund.setMineId(Long.valueOf(mineId));
											}
											bpFundProjectDao.merge(ownFund);
										}
										//保存款项信息
										String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
										if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
											List<SlFundIntent> oldList = slFundIntentService.getbyPreceptId(ownFund.getId());
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
													SlFundIntent1.setIsCheck(Short.valueOf("0"));
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
													SlFundIntent1.setIsCheck(Short.valueOf("0"));
													//}
													SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
													BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

													slFundIntentService.merge(slFundIntent2);

												}
												
											}
										}
										if(null!=thisTaskName && thisTaskName.equals("fbzq")){
											String userName="";
											String reciverType=flowRunInfo.getRequest().getParameter("ownBpFundProject.reciverType");
											String receiverName=flowRunInfo.getRequest().getParameter("ownBpFundProject.receiverName");
											String receiverId=flowRunInfo.getRequest().getParameter("ownBpFundProject.reciverId");
											String receiverP2PAccountNumber=flowRunInfo.getRequest().getParameter("ownBpFundProject.receiverP2PAccountNumber");
											ownFund.setReciverType(reciverType);
											ownFund.setReceiverName(receiverName);
											ownFund.setReceiverP2PAccountNumber(receiverP2PAccountNumber);
											if(receiverId!=null&&!"".equals(receiverId)){
												ownFund.setReciverId(Long.valueOf(receiverId));
											}
											bpFundProjectDao.merge(ownFund);
											BpPersionOrPro pOrPro = bpPersionOrProDao.getByBpFundProjectId(ownFund.getId());
											if(pOrPro==null){
												pOrPro = new BpPersionOrPro();
												this.initPersionP2p(ownFund, pOrPro, person);
												if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
													pOrPro.setObligatoryPersion(userName);
												}
												bpPersionOrProDao.save(pOrPro);
											}else{
												this.initPersionP2p(ownFund, pOrPro, person);
												if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
													pOrPro.setObligatoryPersion(userName);
												}
												bpPersionOrProDao.merge(pOrPro);
											}
											
											/**
											 * 更新债权项目的类型
											 */
											if("mmproduceOr".equals(project.getChildType()) ){//理财产品债权项目
												pOrPro.setOrginalType(Short.valueOf("10"));
											}else if("mmplanOr".equals(project.getChildType())){//理财计划债权项目
												pOrPro.setOrginalType(Short.valueOf("20"));
											}else{//线上债权招标项目
												pOrPro.setOrginalType(Short.valueOf("1"));
											}
											bpPersionOrProDao.merge(pOrPro);
											
											
										
										}
									}
							
								dao.merge(project);
							
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
	public Integer saveSmallProjectInfoNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String loanId=flowRunInfo.getRequest().getParameter("loanId");
				String flag=flowRunInfo.getRequest().getParameter("flag");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						project.setEndDate(new Date());
						dao.merge(project);
						//注册用户借款申请状态set为审批流程被驳回（终止项目）
						if(null!=loanId && !loanId.equals("") && !loanId.equals("undefined")){
							BpFinanceApplyUser fu=bpFinanceApplyUserDao.get(Long.valueOf(loanId));
							fu.setState("6");
							bpFinanceApplyUserDao.merge(fu);
						}
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								String thisTaskName=flowRunInfo.getRequest().getParameter("thisTaskName");
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								Spouse spouse=new Spouse();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse, "spouse");
								WorkCompany workCompany=new WorkCompany();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), workCompany, "workCompany");
								personDao.savePersonInfo(person, spouse, ((null!=thisTaskName && thisTaskName.equals("saveInfo"))?workCompany:null));
								//保存费用信息
								/*String chargeJson=flowRunInfo.getRequest().getParameter("chargeJson");
								String isCheck=flowRunInfo.getRequest().getParameter("isCheck");
								if(null!=chargeJson && !chargeJson.equals("")){
									slActualToChargeService.savejson(chargeJson, project.getProjectId(), project.getBusinessType(), Short.valueOf(isCheck), project.getCompanyId());
								}*/
								//保存资金需求
								if(null!=thisTaskName && thisTaskName.equals("saveInfo")){
									BpMoneyBorrowDemand bpMoneyBorrowDemand=new BpMoneyBorrowDemand();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), bpMoneyBorrowDemand, "bpMoneyBorrowDemand");
									bpMoneyBorrowDemand.setProjectID(slSmallloanProject.getProjectId());
									if (bpMoneyBorrowDemand.getBorrowid() == null) {
										this.bpMoneyBorrowDemandDao.save(bpMoneyBorrowDemand);
									} else {
										BpMoneyBorrowDemand bbd = bpMoneyBorrowDemandDao.load(bpMoneyBorrowDemand.getBorrowid());
										BeanUtil.copyNotNullProperties(bbd, bpMoneyBorrowDemand);
										this.bpMoneyBorrowDemandDao.merge(bbd);
									}
									//向网审表插入数据
									List<BpPersonNetCheckInfo> nlist=bpPersonNetCheckInfoDao.listByProjectId(Long.valueOf(projectId));
									if(null==nlist ||(null!=nlist && nlist.size()==0)){
										Map<String,String> map=new HashMap<String,String>();
										map.put("姓名", person.getName());
										map.put("手机号码", person.getCellphone());
										map.put("单位电话", person.getUnitphone());
										map.put("企业名称", person.getCurrentcompany());
										Object s[] = map.keySet().toArray();
										for(int i = 0; i < map.size(); i++) {
											BpPersonNetCheckInfo bpPersonNetCheckInfo=new BpPersonNetCheckInfo();
											bpPersonNetCheckInfo.setSerachObj(s[i].toString());
											bpPersonNetCheckInfo.setSerachInfo(map.get(s[i]));
											bpPersonNetCheckInfo.setProjectId(Long.valueOf(projectId));
											bpPersonNetCheckInfoDao.merge(bpPersonNetCheckInfo);
										}
									}
								}
								//保存网审信息
								if(null!=thisTaskName && thisTaskName.equals("chushen")){
									String netCheckData=flowRunInfo.getRequest().getParameter("netCheckData");
									this.updateNetCheckInfo(netCheckData);
								}
								//产生自由和平台资金款项信息
								if(transitionName.contains("制定资金方案")){
									//自有资金
									BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort("0"));
									if(null==fundProject){
										fundProject=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject, project);
									fundProject.setFlag(Short.parseShort("0"));
									fundProject.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject);
									//平台资金
									BpFundProject fundProject1=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort("1"));
									if(null==fundProject1){
										fundProject1=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject1, project);
									fundProject1.setFlag(Short.parseShort("1"));
									fundProject1.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject1);
									//注册用户借款申请状态set为借款项目审批通过，请补充材料
									if(null!=loanId && !loanId.equals("") && !loanId.equals("undefined")){
										BpFinanceApplyUser fu=bpFinanceApplyUserDao.get(Long.valueOf(loanId));
										fu.setState("7");
										bpFinanceApplyUserDao.merge(fu);
									}
									
								}
									if(null!=thisTaskName && thisTaskName.equals("zdzjfa")){
										BpFundProject ownBpFundProject=new BpFundProject();
										BeanUtil.populateEntity(flowRunInfo.getRequest(), ownBpFundProject, "ownBpFundProject");
										// 自有资金款项信息
										BpFundProject ownFund = bpFundProjectService.getByProjectId(project.getProjectId(), Short.valueOf("0"));
										if (null != ownFund) {
											BeanUtil.copyNotNullProperties(ownFund, ownBpFundProject);
											bpFundProjectDao.merge(ownFund);
										}
										
										
											project.setProjectStatus(Short.valueOf("2"));
										
										
										// 平台资金款项信息
										BpFundProject platFormBpFundProject=new BpFundProject();
										BeanUtil.populateEntity(flowRunInfo.getRequest(), platFormBpFundProject, "platFormBpFundProject");
										BpFundProject platFormFund = bpFundProjectService.getByProjectId(project.getProjectId(), Short.valueOf("1"));
										if (null != platFormFund) {
											BeanUtil.copyNotNullProperties(platFormFund,platFormBpFundProject);
											bpFundProjectDao.merge(platFormFund);
										}
										if(platFormFund.getPlatFormJointMoney().compareTo(new BigDecimal(0))>0){
											//保存个人直投标项目缓信息
											BpPersionDirPro  bpPersionDirPro =bpPersionDirProDao.getByBpFundProjectId(platFormFund.getId());
											if(null==bpPersionDirPro){
												bpPersionDirPro=new BpPersionDirPro();
												this.initPersionP2p(platFormFund,bpPersionDirPro,person);
												bpPersionDirProDao.save(bpPersionDirPro);
	
											}else{
												this.initPersionP2p(platFormFund,bpPersionDirPro,person);
												bpPersionDirProDao.merge(bpPersionDirPro);
											}
										}
									}
								if(null!=thisTaskName && thisTaskName.equals("fkzs")){
									BpFundProject ownBpFundProject=new BpFundProject();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), ownBpFundProject, "ownBpFundProject");
									// 自有资金款项信息
									BpFundProject ownFund = bpFundProjectService.getByProjectId(project.getProjectId(), Short.valueOf("0"));
									if (null != ownFund) {
										BeanUtil.copyNotNullProperties(ownFund, ownBpFundProject);
										ownFund.setProjectStatus(Short.valueOf("1"));
										bpFundProjectDao.merge(ownFund);
									}
									//保存款项信息
									String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
									if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
										List<SlFundIntent> oldList = slFundIntentService.getbyPreceptId(ownFund.getId());
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
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
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
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
												//}
												SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
												BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

												slFundIntentService.merge(slFundIntent2);

											}
											
										}
									}
								}
								
								
								//发布债权
								
								BpPersionOrPro bpPersionOrPro=null;
								String selectCreditorType=flowRunInfo.getRequest().getParameter("selectCreditorType");
								project.setChildType(selectCreditorType);//债权类型
								if(null!=thisTaskName && thisTaskName.equals("fbzq")){
									BpFundProject ownBpFundProject=new BpFundProject();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), ownBpFundProject, "ownBpFundProject");
									// 自有资金款项信息
									BpFundProject ownFund = bpFundProjectService.getByProjectId(project.getProjectId(), Short.valueOf("0"));
									if (null != ownFund) {
										BeanUtil.copyNotNullProperties(ownFund, ownBpFundProject);
										bpFundProjectDao.merge(ownFund);
									}
									String userName="";
									/*if(ownFund.getMineType().equals("person_ourmain")){
										SlPersonMain sp=slPersonMainDao.get(ownFund.getMineId());
										if(null!=sp){
											userName=sp.getName();
										}
									}else{
										SlCompanyMain sc=slCompanyMainDao.get(ownFund.getMineId());
										if(null!=sc){
											userName=sc.getCorName();
										}
									}*/
									BpPersionOrPro pOrPro = bpPersionOrProDao.getByBpFundProjectId(ownFund.getId());
									if(pOrPro==null){
										pOrPro = new BpPersionOrPro();
										this.initPersionP2p(ownFund, pOrPro, person);
										if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
											pOrPro.setObligatoryPersion(userName);
										}
										bpPersionOrPro=bpPersionOrProDao.save(pOrPro);
									}else{
										this.initPersionP2p(ownFund, pOrPro, person);
										if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
											pOrPro.setObligatoryPersion(userName);
										}
										bpPersionOrPro=bpPersionOrProDao.merge(pOrPro);
									}
									/**
									 * 更新债权项目的类型
									 */
									if("mmproduceOr".equals(project.getChildType()) ){//理财产品债权项目
										bpPersionOrPro.setOrginalType(Short.valueOf("10"));
									}else if("mmplanOr".equals(project.getChildType())){//理财计划债权项目
										bpPersionOrPro.setOrginalType(Short.valueOf("20"));
									}else{//线上债权招标项目
										bpPersionOrPro.setOrginalType(Short.valueOf("1"));
									}
									bpPersionOrProDao.merge(bpPersionOrPro);
								}
								dao.merge(project);
								
								System.out.println("childType====="+project.getChildType());
								if("mmproduceOr".equals(project.getChildType()) || "mmplanOr".equals(project.getChildType())){
									SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
									PlBidPlan plan = new PlBidPlan();
									plan.setPOrProId(bpPersionOrPro.getPorProId());
									plan.setProType("P_Or");
									plan.setBiddingTypeId(Long.valueOf(3));
									plan.setState(5);
									plan.setBidProName(bpPersionOrPro.getProName());
									plan.setBidProNumber(bpPersionOrPro.getProNumber());
									plan.setBidMoney(bpPersionOrPro.getBidMoney());
									plan.setBidMoneyScale(Double.valueOf(100));
									plan.setChildType(project.getChildType());//债权类型
									plan.setPayIntersetWay(bpPersionOrPro.getPayIntersetWay());
									
									//往债权库插入数据
									plMmObligatoryRightChildrenService.createObligatoryRightChildren(plan);
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
	public Integer saveEnterpriseProjectInfoNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String flag=flowRunInfo.getRequest().getParameter("flag");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						project.setEndDate(new Date());
						dao.merge(project);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								String thisTaskName=flowRunInfo.getRequest().getParameter("thisTaskName");
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								String gudongInfo=flowRunInfo.getRequest().getParameter("gudongInfo");
								Enterprise enterprise=new Enterprise();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								enterpriseDao.saveSingleEnterprise(enterprise, person, gudongInfo);
								//保存开户银行信息
								EnterpriseBank enterpriseBank=new EnterpriseBank();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank, "enterpriseBank");
								if (enterpriseBank.getId() == null) {
									List<EnterpriseBank> list = enterpriseBankDao.getBankList(project.getOppositeID().intValue(), Short.valueOf("0"), Short.valueOf("0"),Short.valueOf("0"));
									for (EnterpriseBank e : list) {
										e.setIscredit(Short.valueOf("1"));
										enterpriseBankDao.merge(e);
									}
									enterpriseBank.setEnterpriseid(project.getOppositeID().intValue());
									enterpriseBank.setIscredit(Short.valueOf("0"));
									enterpriseBank.setIsEnterprise(Short.valueOf("0"));
									enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
									enterpriseBank.setIsInvest(Short.valueOf("0"));
									enterpriseBankDao.save(enterpriseBank);
								} else {
									EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(EnterpriseBank.class, enterpriseBank.getId());
									BeanUtil.copyNotNullProperties(bank, enterpriseBank);
									enterpriseBankDao.merge(bank);
								}
								//保存第一还款来源
								String repaymentSource=flowRunInfo.getRequest().getParameter("repaymentSource");
								if(null != repaymentSource && !"".equals(repaymentSource)) {

									String[] repaymentSourceArr = repaymentSource.split("@");
									
									for(int i=0; i<repaymentSourceArr.length; i++) {
										String str = repaymentSourceArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										SlRepaymentSource temp = (SlRepaymentSource)JSONMapper.toJava(parser.nextValue(),SlRepaymentSource.class);
										boolean f= StringUtil.isNumeric(temp.getTypeId());
										GlobalType globalType = globalTypeDao.getByNodeKey("repaymentSource").get(0);
										if (f== false) {
											Dictionary dic = new Dictionary();
											dic.setItemValue(temp.getTypeId());
											dic.setItemName(globalType.getTypeName());
											dic.setProTypeId(globalType.getProTypeId());
											dic.setDicKey(temp.getTypeId());
											dic.setStatus("0");
											dictionaryDao.save(dic);
											temp.setTypeId(String.valueOf(dic.getDicId()));
										} else {
											Dictionary cd = dictionaryDao.get(Long.valueOf(temp
													.getTypeId()));
											if (null == cd) {
												Dictionary dic = new Dictionary();
												dic.setItemValue(temp.getTypeId());
												dic.setItemName(globalType.getTypeName());
												dic.setProTypeId(globalType.getProTypeId());
												dic.setDicKey(temp.getTypeId());
												dic.setStatus("0");
												dictionaryDao.save(dic);
												temp.setTypeId(String.valueOf(dic.getDicId()));
											}
										}
										temp.setProjId(slSmallloanProject.getProjectId());
										if (temp.getSourceId() == null) {
											this.slRepaymentSourceDao.save(temp);
										} else {
											SlRepaymentSource rPersistent = this.slRepaymentSourceDao.get(temp.getSourceId());
											BeanUtil.copyNotNullProperties(rPersistent, temp);
											this.slRepaymentSourceDao.save(rPersistent);
										}
									
										
									}
								}
								//保存共同借款人
								String borrowerInfo=flowRunInfo.getRequest().getParameter("borrowerInfo");
								if(null != borrowerInfo && !"".equals(borrowerInfo)) {

									String[] borrowerInfoArr = borrowerInfo.split("@");
									
									for(int i=0; i<borrowerInfoArr.length; i++) {
										String str = borrowerInfoArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										BorrowerInfo bo = (BorrowerInfo)JSONMapper.toJava(parser.nextValue(),BorrowerInfo.class);
										if (bo.getBorrowerInfoId() == null) {
											bo.setProjectId(project.getProjectId());
											bo.setBusinessType(project.getBusinessType());
											bo.setOperationType(project.getOperationType());
											this.borrowerInfoDao.save(bo);
										} else {
											BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo.getBorrowerInfoId());
											BeanUtils.copyProperties(boPersistent, bo);
											this.borrowerInfoDao.merge(boPersistent);
										}
										if (null != bo.getType() && bo.getType() == 0) {
											if (null != bo.getCustomerId()) {
												Enterprise e = this.enterpriseDao.getById(bo.getCustomerId());
												e.setArea(bo.getAddress());
												e.setCciaa(bo.getCardNum());
												e.setTelephone(bo.getTelPhone());
												this.enterpriseDao.merge(e);
											}
										} else if (null != bo.getType() && bo.getType() == 1) {
											Person p = this.personDao.getById(bo.getCustomerId());
											p.setPostaddress(bo.getAddress());
											p.setCardnumber(bo.getCardNum());
											p.setCellphone(bo.getTelPhone());
											this.personDao.merge(p);
										}
									}
								}
								//保存费用信息
								String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData");
								String isCheck=flowRunInfo.getRequest().getParameter("isCheck");
								if(null!=slActualToChargeJsonData && !slActualToChargeJsonData.equals("")){
									slActualToChargeService.savejson(slActualToChargeJsonData, project.getProjectId(), project.getBusinessType(), Short.valueOf(isCheck), project.getCompanyId());
								}
								if(null!=thisTaskName && thisTaskName.equals("meeting")){
									SlConferenceRecord slConferenceRecord = new SlConferenceRecord();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), slConferenceRecord,"slConferenceRecord");
									if(slConferenceRecord.getConforenceId()==null){
										slConferenceRecordDao.save(slConferenceRecord);
										Long conforenceId=slConferenceRecord.getConforenceId();
									}else{
										SlConferenceRecord orgSlConferenceRecord=slConferenceRecordDao.get(slConferenceRecord.getConforenceId());
										BeanUtil.copyNotNullProperties(orgSlConferenceRecord, slConferenceRecord);
										slConferenceRecordDao.save(orgSlConferenceRecord);
									}
								}
								//产生自由和平台资金款项信息
								if(transitionName.contains("制定资金方案")){
									//自有资金
									BpFundProject fundProject=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort("0"));
									if(null==fundProject){
										fundProject=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject, project);
									fundProject.setFlag(Short.parseShort("0"));
									fundProject.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject);
									//平台资金
									BpFundProject fundProject1=bpFundProjectDao.getByProjectId(Long.valueOf(projectId),Short.parseShort("1"));
									if(null==fundProject1){
										fundProject1=new BpFundProject();
									}
									BeanUtil.copyNotNullProperties(fundProject1, project);
									fundProject1.setFlag(Short.parseShort("1"));
									fundProject1.setSlFundIntents(null);
									bpFundProjectDao.merge(fundProject1);
								}
									if(null!=thisTaskName && thisTaskName.equals("zdzjfa")){
										BpFundProject ownBpFundProject=new BpFundProject();
										BeanUtil.populateEntity(flowRunInfo.getRequest(), ownBpFundProject, "ownBpFundProject");
										// 自有资金款项信息
										BpFundProject ownFund = bpFundProjectService.getByProjectId(project.getProjectId(), Short.valueOf("0"));
										if (null != ownFund) {
											BeanUtil.copyNotNullProperties(ownFund, ownBpFundProject);
											bpFundProjectDao.merge(ownFund);
										}
										
										//if(ownFund.getOwnJointMoney().compareTo(new BigDecimal(0))==0){
											project.setProjectStatus(Short.valueOf("2"));
										//}
										
										// 平台资金款项信息
										BpFundProject platFormBpFundProject=new BpFundProject();
										BeanUtil.populateEntity(flowRunInfo.getRequest(), platFormBpFundProject, "platFormBpFundProject");
										BpFundProject platFormFund = bpFundProjectService.getByProjectId(project.getProjectId(), Short.valueOf("1"));
										if (null != platFormFund) {
											BeanUtil.copyNotNullProperties(platFormFund,platFormBpFundProject);
											bpFundProjectDao.merge(platFormFund);
										}
										if(platFormFund.getPlatFormJointMoney().compareTo(new BigDecimal(0))>0){
											BpBusinessDirPro bDirPro = bpBusinessDirProDao.getByMoneyPlanId(platFormFund.getId());
											if(bDirPro==null){
												bDirPro = new BpBusinessDirPro();
												this.initBusinessP2p(platFormFund, bDirPro, enterprise);
												bpBusinessDirProDao.save(bDirPro);
											}else{
												this.initBusinessP2p(platFormFund, bDirPro, enterprise);
												bpBusinessDirProDao.merge(bDirPro);
											}
										}
									}
								if(null!=thisTaskName && thisTaskName.equals("fkzs")){
									BpFundProject ownBpFundProject=new BpFundProject();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), ownBpFundProject, "ownBpFundProject");
									// 自有资金款项信息
									BpFundProject ownFund = bpFundProjectService.getByProjectId(project.getProjectId(), Short.valueOf("0"));
									if (null != ownFund) {
										BeanUtil.copyNotNullProperties(ownFund, ownBpFundProject);
										ownFund.setProjectStatus(Short.valueOf("1"));
										bpFundProjectDao.merge(ownFund);
									}
									//保存款项信息
									String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
									
									if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
										List<SlFundIntent> oldList = slFundIntentService.getbyPreceptId(ownFund.getId());
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
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
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
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
												//}
												SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
												BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

												slFundIntentService.merge(slFundIntent2);

											}
											
										}
									}
								}
								
								String selectCreditorType=flowRunInfo.getRequest().getParameter("selectCreditorType");
								project.setChildType(selectCreditorType);//债权类型
								BpBusinessOrPro bpBusinessOrPro=null;
								//发布债权
								if(null!=thisTaskName && thisTaskName.equals("fbzq")){
									BpFundProject ownBpFundProject=new BpFundProject();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), ownBpFundProject, "ownBpFundProject");
									// 自有资金款项信息
									BpFundProject ownFund = bpFundProjectService.getByProjectId(project.getProjectId(), Short.valueOf("0"));
									if (null != ownFund) {
										BeanUtil.copyNotNullProperties(ownFund, ownBpFundProject);
										bpFundProjectDao.merge(ownFund);
									}
									String userName="";
									/*if(ownFund.getMineType().equals("person_ourmain")){
										SlPersonMain sp=slPersonMainDao.get(ownFund.getMineId());
										if(null!=sp){
											userName=sp.getName();
										}
									}else{
										SlCompanyMain sc=slCompanyMainDao.get(ownFund.getMineId());
										if(null!=sc){
											userName=sc.getCorName();
										}
									}*/
									BpBusinessOrPro bOrPro = bpBusinessOrProDao.getBpBusinessOrProByMoneyPlanId(ownFund.getId());
									if(bOrPro==null){
										bOrPro = new BpBusinessOrPro();
										this.initBusinessP2p(ownFund, bOrPro, enterprise);
										if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
											bOrPro.setObligatoryPersion(userName);
										}
										
										bpBusinessOrPro=bpBusinessOrProDao.save(bOrPro);
									}else{
										this.initBusinessP2p(ownFund, bOrPro, enterprise);
										if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
											bOrPro.setObligatoryPersion(userName);
										}
										bpBusinessOrPro=bpBusinessOrProDao.merge(bOrPro);
									}
									
									if("mmproduceOr".equals(project.getChildType()) ){//理财产品债权项目
										bOrPro.setOrginalType(Short.valueOf("10"));
									}else if("mmplanOr".equals(project.getChildType())){//理财计划债权项目
										bOrPro.setOrginalType(Short.valueOf("20"));
									}else{//线上债权招标项目
										bOrPro.setOrginalType(Short.valueOf("1"));
									}
									bpBusinessOrProDao.merge(bOrPro);
								}
								dao.merge(project);
								
								System.out.println("childType====="+project.getChildType());
								if("mmproduceOr".equals(project.getChildType()) || "mmplanOr".equals(project.getChildType())){
									SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
									PlBidPlan plan = new PlBidPlan();
									plan.setBorProId(bpBusinessOrPro.getBorProId());
									plan.setProType("B_Or");
									plan.setBiddingTypeId(Long.valueOf(3));
									plan.setState(5);
									plan.setBidProName(bpBusinessOrPro.getProName());
									plan.setBidProNumber(bpBusinessOrPro.getProNumber());
									plan.setBidMoney(bpBusinessOrPro.getBidMoney());
									plan.setBidMoneyScale(Double.valueOf(100));
									plan.setChildType(project.getChildType());//债权类型
									plan.setPayIntersetWay(bpBusinessOrPro.getPayIntersetWay());
									
									//往债权库插入数据
									plMmObligatoryRightChildrenService.createObligatoryRightChildren(plan);
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
	public Integer saveDirEnterpriseProjectInfoNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String loanId=flowRunInfo.getRequest().getParameter("loanId");//线上申请记录id
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
				String flag=flowRunInfo.getRequest().getParameter("flag");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								String gudongInfo=flowRunInfo.getRequest().getParameter("gudongInfo");
								Enterprise enterprise=new Enterprise();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								enterpriseDao.saveSingleEnterprise(enterprise, person, gudongInfo);
								//保存开户银行信息
								EnterpriseBank enterpriseBank=new EnterpriseBank();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank, "enterpriseBank");
								if (enterpriseBank.getId() == null) {
									List<EnterpriseBank> list = enterpriseBankDao.getBankList(project.getOppositeID().intValue(), Short.valueOf("0"), Short.valueOf("0"),Short.valueOf("0"));
									for (EnterpriseBank e : list) {
										e.setIscredit(Short.valueOf("1"));
										enterpriseBankDao.merge(e);
									}
									enterpriseBank.setEnterpriseid(project.getOppositeID().intValue());
									enterpriseBank.setIscredit(Short.valueOf("0"));
									enterpriseBank.setIsEnterprise(Short.valueOf("0"));
									enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
									enterpriseBank.setIsInvest(Short.valueOf("0"));
									enterpriseBankDao.save(enterpriseBank);
								} else {
									EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(EnterpriseBank.class, enterpriseBank.getId());
									BeanUtil.copyNotNullProperties(bank, enterpriseBank);
									enterpriseBankDao.merge(bank);
								}
								//保存第一还款来源
								String repaymentSource=flowRunInfo.getRequest().getParameter("repaymentSource");
								if(null != repaymentSource && !"".equals(repaymentSource)) {

									String[] repaymentSourceArr = repaymentSource.split("@");
									
									for(int i=0; i<repaymentSourceArr.length; i++) {
										String str = repaymentSourceArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										SlRepaymentSource temp = (SlRepaymentSource)JSONMapper.toJava(parser.nextValue(),SlRepaymentSource.class);
										boolean f= StringUtil.isNumeric(temp.getTypeId());
										GlobalType globalType = globalTypeDao.getByNodeKey("repaymentSource").get(0);
										if (f== false) {
											Dictionary dic = new Dictionary();
											dic.setItemValue(temp.getTypeId());
											dic.setItemName(globalType.getTypeName());
											dic.setProTypeId(globalType.getProTypeId());
											dic.setDicKey(temp.getTypeId());
											dic.setStatus("0");
											dictionaryDao.save(dic);
											temp.setTypeId(String.valueOf(dic.getDicId()));
										} else {
											Dictionary cd = dictionaryDao.get(Long.valueOf(temp
													.getTypeId()));
											if (null == cd) {
												Dictionary dic = new Dictionary();
												dic.setItemValue(temp.getTypeId());
												dic.setItemName(globalType.getTypeName());
												dic.setProTypeId(globalType.getProTypeId());
												dic.setDicKey(temp.getTypeId());
												dic.setStatus("0");
												dictionaryDao.save(dic);
												temp.setTypeId(String.valueOf(dic.getDicId()));
											}
										}
										temp.setProjId(slSmallloanProject.getProjectId());
										if (temp.getSourceId() == null) {
											this.slRepaymentSourceDao.save(temp);
										} else {
											SlRepaymentSource rPersistent = this.slRepaymentSourceDao.get(temp.getSourceId());
											BeanUtil.copyNotNullProperties(rPersistent, temp);
											this.slRepaymentSourceDao.save(rPersistent);
										}
									
										
									}
								}
								//保存共同借款人
								String borrowerInfo=flowRunInfo.getRequest().getParameter("borrowerInfo");
								if(null != borrowerInfo && !"".equals(borrowerInfo)) {

									String[] borrowerInfoArr = borrowerInfo.split("@");
									
									for(int i=0; i<borrowerInfoArr.length; i++) {
										String str = borrowerInfoArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										BorrowerInfo bo = (BorrowerInfo)JSONMapper.toJava(parser.nextValue(),BorrowerInfo.class);
										if (bo.getBorrowerInfoId() == null) {
											bo.setProjectId(project.getProjectId());
											bo.setBusinessType(project.getBusinessType());
											bo.setOperationType(project.getOperationType());
											this.borrowerInfoDao.save(bo);
										} else {
											BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo.getBorrowerInfoId());
											BeanUtils.copyProperties(boPersistent, bo);
											this.borrowerInfoDao.merge(boPersistent);
										}
										if (null != bo.getType() && bo.getType() == 0) {
											if (null != bo.getCustomerId()) {
												Enterprise e = this.enterpriseDao.getById(bo.getCustomerId());
												e.setArea(bo.getAddress());
												e.setCciaa(bo.getCardNum());
												e.setTelephone(bo.getTelPhone());
												this.enterpriseDao.merge(e);
											}
										} else if (null != bo.getType() && bo.getType() == 1) {
											Person p = this.personDao.getById(bo.getCustomerId());
											p.setPostaddress(bo.getAddress());
											p.setCardnumber(bo.getCardNum());
											p.setCellphone(bo.getTelPhone());
											this.personDao.merge(p);
										}
									}
								}
								//保存费用信息
								String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData");
								if(null!=slActualToChargeJsonData && !slActualToChargeJsonData.equals("")){
									slActualToChargeService.savejson(slActualToChargeJsonData, project.getProjectId(), project.getBusinessType(), Short.valueOf("0"), project.getCompanyId());
								}
						
								
									//平台资金
								if(null!=fundProjectId && !fundProjectId.equals("")){
									BpFundProject fundProject1=bpFundProjectDao.get(Long.valueOf(fundProjectId));
							
									BeanUtil.copyNotNullProperties(fundProject1, project);
									fundProject1.setPlatFormJointMoney(project.getProjectMoney());
									fundProject1.setProjectStatus(Short.valueOf("1"));
									bpFundProjectDao.merge(fundProject1);
									project.setProjectStatus(Short.valueOf("2"));
									if(fundProject1.getPlatFormJointMoney().compareTo(new BigDecimal(0))>0){
										BpBusinessDirPro bDirPro = bpBusinessDirProDao.getByMoneyPlanId(fundProject1.getId());
										if(bDirPro==null){
											bDirPro = new BpBusinessDirPro();
											this.initBusinessP2p(fundProject1, bDirPro, enterprise);
											if(loanId!=null&&!"".equals(loanId)){
												bDirPro.setLoanId(Long.valueOf(loanId));
											}
											bpBusinessDirProDao.save(bDirPro);
										}else{
											this.initBusinessP2p(fundProject1, bDirPro, enterprise);
											if(loanId!=null&&!"".equals(loanId)){
												bDirPro.setLoanId(Long.valueOf(loanId));
											}
											bpBusinessDirProDao.merge(bDirPro);
										}
									}
								}
								
							
								dao.merge(project);
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
	public Integer saveOrEnterpriseProjectInfoNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
				String flag=flowRunInfo.getRequest().getParameter("flag");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								String thisTaskName=flowRunInfo.getRequest().getParameter("thisTaskName");
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								String gudongInfo=flowRunInfo.getRequest().getParameter("gudongInfo");
								Enterprise enterprise=new Enterprise();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								enterpriseDao.saveSingleEnterprise(enterprise, person, gudongInfo);
								//保存开户银行信息
								EnterpriseBank enterpriseBank=new EnterpriseBank();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank, "enterpriseBank");
								if (enterpriseBank.getId() == null) {
									List<EnterpriseBank> list = enterpriseBankDao.getBankList(project.getOppositeID().intValue(), Short.valueOf("0"), Short.valueOf("0"),Short.valueOf("0"));
									for (EnterpriseBank e : list) {
										e.setIscredit(Short.valueOf("1"));
										enterpriseBankDao.merge(e);
									}
									enterpriseBank.setEnterpriseid(project.getOppositeID().intValue());
									enterpriseBank.setIscredit(Short.valueOf("0"));
									enterpriseBank.setIsEnterprise(Short.valueOf("0"));
									enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
									enterpriseBank.setIsInvest(Short.valueOf("0"));
									enterpriseBankDao.save(enterpriseBank);
								} else {
									EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(EnterpriseBank.class, enterpriseBank.getId());
									BeanUtil.copyNotNullProperties(bank, enterpriseBank);
									enterpriseBankDao.merge(bank);
								}
								//保存第一还款来源
								String repaymentSource=flowRunInfo.getRequest().getParameter("repaymentSource");
								if(null != repaymentSource && !"".equals(repaymentSource)) {

									String[] repaymentSourceArr = repaymentSource.split("@");
									
									for(int i=0; i<repaymentSourceArr.length; i++) {
										String str = repaymentSourceArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										SlRepaymentSource temp = (SlRepaymentSource)JSONMapper.toJava(parser.nextValue(),SlRepaymentSource.class);
										boolean f= StringUtil.isNumeric(temp.getTypeId());
										GlobalType globalType = globalTypeDao.getByNodeKey("repaymentSource").get(0);
										if (f== false) {
											Dictionary dic = new Dictionary();
											dic.setItemValue(temp.getTypeId());
											dic.setItemName(globalType.getTypeName());
											dic.setProTypeId(globalType.getProTypeId());
											dic.setDicKey(temp.getTypeId());
											dic.setStatus("0");
											dictionaryDao.save(dic);
											temp.setTypeId(String.valueOf(dic.getDicId()));
										} else {
											Dictionary cd = dictionaryDao.get(Long.valueOf(temp
													.getTypeId()));
											if (null == cd) {
												Dictionary dic = new Dictionary();
												dic.setItemValue(temp.getTypeId());
												dic.setItemName(globalType.getTypeName());
												dic.setProTypeId(globalType.getProTypeId());
												dic.setDicKey(temp.getTypeId());
												dic.setStatus("0");
												dictionaryDao.save(dic);
												temp.setTypeId(String.valueOf(dic.getDicId()));
											}
										}
										temp.setProjId(slSmallloanProject.getProjectId());
										if (temp.getSourceId() == null) {
											this.slRepaymentSourceDao.save(temp);
										} else {
											SlRepaymentSource rPersistent = this.slRepaymentSourceDao.get(temp.getSourceId());
											BeanUtil.copyNotNullProperties(rPersistent, temp);
											this.slRepaymentSourceDao.save(rPersistent);
										}
									
										
									}
								}
								//保存共同借款人
								String borrowerInfo=flowRunInfo.getRequest().getParameter("borrowerInfo");
								if(null != borrowerInfo && !"".equals(borrowerInfo)) {

									String[] borrowerInfoArr = borrowerInfo.split("@");
									
									for(int i=0; i<borrowerInfoArr.length; i++) {
										String str = borrowerInfoArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										BorrowerInfo bo = (BorrowerInfo)JSONMapper.toJava(parser.nextValue(),BorrowerInfo.class);
										if (bo.getBorrowerInfoId() == null) {
											bo.setProjectId(project.getProjectId());
											bo.setBusinessType(project.getBusinessType());
											bo.setOperationType(project.getOperationType());
											this.borrowerInfoDao.save(bo);
										} else {
											BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo.getBorrowerInfoId());
											BeanUtils.copyProperties(boPersistent, bo);
											this.borrowerInfoDao.merge(boPersistent);
										}
										if (null != bo.getType() && bo.getType() == 0) {
											if (null != bo.getCustomerId()) {
												Enterprise e = this.enterpriseDao.getById(bo.getCustomerId());
												e.setArea(bo.getAddress());
												e.setCciaa(bo.getCardNum());
												e.setTelephone(bo.getTelPhone());
												this.enterpriseDao.merge(e);
											}
										} else if (null != bo.getType() && bo.getType() == 1) {
											Person p = this.personDao.getById(bo.getCustomerId());
											p.setPostaddress(bo.getAddress());
											p.setCardnumber(bo.getCardNum());
											p.setCellphone(bo.getTelPhone());
											this.personDao.merge(p);
										}
									}
								}
								//保存费用信息
								String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData");
								if(null!=slActualToChargeJsonData && !slActualToChargeJsonData.equals("")){
									slActualToChargeService.savejson(slActualToChargeJsonData, project.getProjectId(), project.getBusinessType(), Short.valueOf("0"), project.getCompanyId());
								}
						
								
									//平台资金
								if(null!=fundProjectId && !fundProjectId.equals("")){
									 
									String mineType=flowRunInfo.getRequest().getParameter("ownBpFundProject.mineType");
									String mineId=flowRunInfo.getRequest().getParameter("ownBpFundProject.mineId");
									String reciverType=flowRunInfo.getRequest().getParameter("ownBpFundProject.reciverType");
									String receiverName=flowRunInfo.getRequest().getParameter("ownBpFundProject.receiverName");
									String receiverId=flowRunInfo.getRequest().getParameter("ownBpFundProject.reciverId");
									String receiverP2PAccountNumber=flowRunInfo.getRequest().getParameter("ownBpFundProject.receiverP2PAccountNumber");
									BpFundProject ownFund = bpFundProjectService.get(Long.valueOf(fundProjectId));
									if (null != ownFund) {
										BeanUtil.copyNotNullProperties(ownFund,project);
										ownFund.setOwnJointMoney(project.getProjectMoney());
										ownFund.setProjectStatus(Short.valueOf("1"));
										ownFund.setReciverType(reciverType);
										ownFund.setReceiverName(receiverName);
										ownFund.setReceiverP2PAccountNumber(receiverP2PAccountNumber);
										if(receiverId!=null&&!"".equals(receiverId)){
											ownFund.setReciverId(Long.valueOf(receiverId));
										}
										if(null!=mineType && !mineType.equals("")){
											ownFund.setMineType(mineType);
										}
										if(null!=mineId && !mineId.equals("")){
											ownFund.setMineId(Long.valueOf(mineId));
										}
										bpFundProjectDao.merge(ownFund);
									}
									//保存款项信息
									String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
									if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
										List<SlFundIntent> oldList = slFundIntentService.getbyPreceptId(ownFund.getId());
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
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
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
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
												//}
												SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
												BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

												slFundIntentService.merge(slFundIntent2);

											}
											
										}
									}
									if(null!=thisTaskName && thisTaskName.equals("fbzq")){
										String userName="";
										/*if(ownFund.getMineType().equals("person_ourmain")){
											SlPersonMain sp=slPersonMainDao.get(ownFund.getMineId());
											if(null!=sp){
												userName=sp.getName();
											}
										}else{
											SlCompanyMain sc=slCompanyMainDao.get(ownFund.getMineId());
											if(null!=sc){
												userName=sc.getCorName();
											}
										}*/
										
										BpBusinessOrPro bOrPro = bpBusinessOrProDao.getBpBusinessOrProByMoneyPlanId(ownFund.getId());
										if(bOrPro==null){
											bOrPro = new BpBusinessOrPro();
											this.initBusinessP2p(ownFund, bOrPro, enterprise);
											if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
												bOrPro.setObligatoryPersion(userName);
											}
											bpBusinessOrProDao.save(bOrPro);
										}else{
											this.initBusinessP2p(ownFund, bOrPro, enterprise);
											if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
												bOrPro.setObligatoryPersion(userName);
											}
											bpBusinessOrProDao.merge(bOrPro);
										}
										if("mmproduceOr".equals(project.getChildType()) ){//理财产品债权项目
											bOrPro.setOrginalType(Short.valueOf("10"));
										}else if("mmplanOr".equals(project.getChildType())){//理财计划债权项目
											bOrPro.setOrginalType(Short.valueOf("20"));
										}else{//线上债权招标项目
											bOrPro.setOrginalType(Short.valueOf("1"));
										}
										bpBusinessOrProDao.merge(bOrPro);
									
									}
								}
								
							
								dao.merge(project);
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
	public void approveProjectList(PageBean<SlSmallloanProject> pageBean,Short projectStatus, String userIdsStr) {
		dao.approveProjectList(pageBean,projectStatus,userIdsStr);
	}
	@Override
	public Integer saveEnterpriseSmallProjectInfoNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
				String flag=flowRunInfo.getRequest().getParameter("flag");
				String isCheck=flowRunInfo.getRequest().getParameter("isCheck");
				String transitionName = flowRunInfo.getTransitionName();
				BpFundProject ownFund = bpFundProjectDao.get(Long.valueOf(fundProjectId));
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
						ownFund.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						bpFundProjectDao.merge(ownFund);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								String thisTaskName=flowRunInfo.getRequest().getParameter("thisTaskName");
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								String gudongInfo=flowRunInfo.getRequest().getParameter("gudongInfo");
								Enterprise enterprise=new Enterprise();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								enterpriseDao.saveSingleEnterprise(enterprise, person, gudongInfo);
								//保存开户银行信息
								EnterpriseBank enterpriseBank=new EnterpriseBank();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank, "enterpriseBank");
								if (enterpriseBank.getId() == null) {
									List<EnterpriseBank> list = enterpriseBankDao.getBankList(project.getOppositeID().intValue(), Short.valueOf("0"), Short.valueOf("0"),Short.valueOf("0"));
									for (EnterpriseBank e : list) {
										e.setIscredit(Short.valueOf("1"));
										enterpriseBankDao.merge(e);
									}
									enterpriseBank.setEnterpriseid(project.getOppositeID().intValue());
									enterpriseBank.setIscredit(Short.valueOf("0"));
									enterpriseBank.setIsEnterprise(Short.valueOf("0"));
									enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
									enterpriseBank.setIsInvest(Short.valueOf("0"));
									enterpriseBankDao.save(enterpriseBank);
								} else {
									EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(EnterpriseBank.class, enterpriseBank.getId());
									BeanUtil.copyNotNullProperties(bank, enterpriseBank);
									enterpriseBankDao.merge(bank);
								}
								//保存第一还款来源
								String repaymentSource=flowRunInfo.getRequest().getParameter("repaymentSource");
								if(null != repaymentSource && !"".equals(repaymentSource)) {

									String[] repaymentSourceArr = repaymentSource.split("@");
									
									for(int i=0; i<repaymentSourceArr.length; i++) {
										String str = repaymentSourceArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										SlRepaymentSource temp = (SlRepaymentSource)JSONMapper.toJava(parser.nextValue(),SlRepaymentSource.class);
										boolean f= StringUtil.isNumeric(temp.getTypeId());
										GlobalType globalType = globalTypeDao.getByNodeKey("repaymentSource").get(0);
										if (f== false) {
											Dictionary dic = new Dictionary();
											dic.setItemValue(temp.getTypeId());
											dic.setItemName(globalType.getTypeName());
											dic.setProTypeId(globalType.getProTypeId());
											dic.setDicKey(temp.getTypeId());
											dic.setStatus("0");
											dictionaryDao.save(dic);
											temp.setTypeId(String.valueOf(dic.getDicId()));
										} else {
											Dictionary cd = dictionaryDao.get(Long.valueOf(temp
													.getTypeId()));
											if (null == cd) {
												Dictionary dic = new Dictionary();
												dic.setItemValue(temp.getTypeId());
												dic.setItemName(globalType.getTypeName());
												dic.setProTypeId(globalType.getProTypeId());
												dic.setDicKey(temp.getTypeId());
												dic.setStatus("0");
												dictionaryDao.save(dic);
												temp.setTypeId(String.valueOf(dic.getDicId()));
											}
										}
										temp.setProjId(slSmallloanProject.getProjectId());
										if (temp.getSourceId() == null) {
											this.slRepaymentSourceDao.save(temp);
										} else {
											SlRepaymentSource rPersistent = this.slRepaymentSourceDao.get(temp.getSourceId());
											BeanUtil.copyNotNullProperties(rPersistent, temp);
											this.slRepaymentSourceDao.save(rPersistent);
										}
									
										
									}
								}
								//保存共同借款人
								String borrowerInfo=flowRunInfo.getRequest().getParameter("borrowerInfo");
								if(null != borrowerInfo && !"".equals(borrowerInfo)) {

									String[] borrowerInfoArr = borrowerInfo.split("@");
									
									for(int i=0; i<borrowerInfoArr.length; i++) {
										String str = borrowerInfoArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										BorrowerInfo bo = (BorrowerInfo)JSONMapper.toJava(parser.nextValue(),BorrowerInfo.class);
										if (bo.getBorrowerInfoId() == null) {
											bo.setProjectId(project.getProjectId());
											bo.setBusinessType(project.getBusinessType());
											bo.setOperationType(project.getOperationType());
											this.borrowerInfoDao.save(bo);
										} else {
											BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo.getBorrowerInfoId());
											BeanUtils.copyProperties(boPersistent, bo);
											this.borrowerInfoDao.merge(boPersistent);
										}
										if (null != bo.getType() && bo.getType() == 0) {
											if (null != bo.getCustomerId()) {
												Enterprise e = this.enterpriseDao.getById(bo.getCustomerId());
												e.setArea(bo.getAddress());
												e.setCciaa(bo.getCardNum());
												e.setTelephone(bo.getTelPhone());
												this.enterpriseDao.merge(e);
											}
										} else if (null != bo.getType() && bo.getType() == 1) {
											Person p = this.personDao.getById(bo.getCustomerId());
											p.setPostaddress(bo.getAddress());
											p.setCardnumber(bo.getCardNum());
											p.setCellphone(bo.getTelPhone());
											this.personDao.merge(p);
										}
									}
								}
								//保存费用信息
								String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData");
								if(null!=slActualToChargeJsonData && !slActualToChargeJsonData.equals("")){
									slActualToChargeService.savejson(slActualToChargeJsonData, project.getProjectId(), project.getBusinessType(), Short.valueOf(isCheck), project.getCompanyId());
								}
								if(null!=thisTaskName && thisTaskName.equals("meeting")){
									SlConferenceRecord slConferenceRecord = new SlConferenceRecord();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), slConferenceRecord,"slConferenceRecord");
									if(slConferenceRecord.getConforenceId()==null){
										slConferenceRecordDao.save(slConferenceRecord);
										Long conforenceId=slConferenceRecord.getConforenceId();
									}else{
										SlConferenceRecord orgSlConferenceRecord=slConferenceRecordDao.get(slConferenceRecord.getConforenceId());
										BeanUtil.copyNotNullProperties(orgSlConferenceRecord, slConferenceRecord);
										slConferenceRecordDao.save(orgSlConferenceRecord);
									}
								}	
								if (null != ownFund  && project.getProjectStatus()!=2) {
									BeanUtil.copyNotNullProperties(ownFund,project);
									ownFund.setOwnJointMoney(project.getProjectMoney());
									bpFundProjectDao.merge(ownFund);
								}
								//确定资金方案节点  把slSmallloanProject的projectStatus设成2
								if(null!=thisTaskName && thisTaskName.equals("fundScheme")){
									project.setProjectStatus(Short.valueOf("2"));
								}
								//保存款项信息
								String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
								if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
									List<SlFundIntent> oldList = slFundIntentService.getbyPreceptId(ownFund.getId());
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
											SlFundIntent1.setIsCheck(Short.valueOf(isCheck));
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
											SlFundIntent1.setIsCheck(Short.valueOf(isCheck));
											//}
											SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
											BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

											slFundIntentService.merge(slFundIntent2);

										}
										
									}
								}
								if(null!=thisTaskName && thisTaskName.equals("loanConfim")){
									List<SlActualToCharge> slist=slActualToChargeDao.getAllbyProjectId(project.getProjectId(), project.getBusinessType());
									for(SlActualToCharge s:slist){
										s.setIsCheck(Short.valueOf("0"));
										slActualToChargeDao.merge(s);
									}
									BpFundProject ownBpFundProject=new BpFundProject();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), ownBpFundProject, "ownBpFundProject");
									BeanUtil.copyNotNullProperties(ownFund, ownBpFundProject);
									ownFund.setProjectStatus(Short.valueOf("1"));
									bpFundProjectDao.merge(ownFund);
								}
								if(transitionName.contains("B角签字")){	
									if (project.getAppUserIdB() != null && !"".equals(project.getAppUserIdB())) {
										vars.put("flowAssignId", project.getAppUserIdB());
									}
								}
								dao.merge(project);
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
	public Integer savePersonSmallProjectInfoNextStep(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
				String transitionName = flowRunInfo.getTransitionName();
				BpFundProject ownFund = bpFundProjectDao.get(Long.valueOf(fundProjectId));
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
						ownFund.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						bpFundProjectDao.merge(ownFund);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								String thisTaskName=flowRunInfo.getRequest().getParameter("thisTaskName");
								
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								Spouse spouse=new Spouse();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse, "spouse");
								WorkCompany workCompany=new WorkCompany();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), workCompany, "workCompany");
								personDao.savePersonInfo(person, spouse, ((null!=thisTaskName && thisTaskName.equals("saveInfo"))?workCompany:null));
								if(null!=thisTaskName && thisTaskName.equals("saveInfo")){
									EnterpriseBank enterpriseBank=new EnterpriseBank();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank, "enterpriseBank");
									if (enterpriseBank.getId() == null) {
										List<EnterpriseBank> list = enterpriseBankService.getBankList(person.getId(), Short.valueOf("1"), Short.valueOf("0"),Short.valueOf("0"));
										for (EnterpriseBank e : list) {
											e.setIscredit(Short.valueOf("1"));
											enterpriseBankService.merge(e);
										}
										enterpriseBank.setEnterpriseid(person.getId());
										enterpriseBank.setIscredit(Short.valueOf("0"));
										enterpriseBank.setIsEnterprise(Short.valueOf("1"));
										enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
										enterpriseBank.setIsInvest(Short.valueOf("0"));
										enterpriseBankService.save(enterpriseBank);
									} else {
										EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(EnterpriseBank.class, enterpriseBank.getId());
										BeanUtil.copyNotNullProperties(bank, enterpriseBank);
										enterpriseBankService.merge(bank);
									}
									//保存资金需求
										BpMoneyBorrowDemand bpMoneyBorrowDemand=new BpMoneyBorrowDemand();
										BeanUtil.populateEntity(flowRunInfo.getRequest(), bpMoneyBorrowDemand, "bpMoneyBorrowDemand");
										bpMoneyBorrowDemand.setProjectID(slSmallloanProject.getProjectId());
										if (bpMoneyBorrowDemand.getBorrowid() == null) {
											this.bpMoneyBorrowDemandDao.save(bpMoneyBorrowDemand);
										} else {
											BpMoneyBorrowDemand bbd = bpMoneyBorrowDemandDao.load(bpMoneyBorrowDemand.getBorrowid());
											BeanUtil.copyNotNullProperties(bbd, bpMoneyBorrowDemand);
											this.bpMoneyBorrowDemandDao.merge(bbd);
										}
											
								}	
								//保存第一还款来源
								String repaymentSource=flowRunInfo.getRequest().getParameter("repaymentSource");
								if(null != repaymentSource && !"".equals(repaymentSource)) {

									String[] repaymentSourceArr = repaymentSource.split("@");
									
									for(int i=0; i<repaymentSourceArr.length; i++) {
										String str = repaymentSourceArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										SlRepaymentSource temp = (SlRepaymentSource)JSONMapper.toJava(parser.nextValue(),SlRepaymentSource.class);
										boolean flag = StringUtil.isNumeric(temp.getTypeId());
										GlobalType globalType = globalTypeDao.getByNodeKey("repaymentSource").get(0);
										if (flag == false) {
											Dictionary dic = new Dictionary();
											dic.setItemValue(temp.getTypeId());
											dic.setItemName(globalType.getTypeName());
											dic.setProTypeId(globalType.getProTypeId());
											dic.setDicKey(temp.getTypeId());
											dic.setStatus("0");
											dictionaryDao.save(dic);
											temp.setTypeId(String.valueOf(dic.getDicId()));
										} else {
											Dictionary cd = dictionaryDao.get(Long.valueOf(temp
													.getTypeId()));
											if (null == cd) {
												Dictionary dic = new Dictionary();
												dic.setItemValue(temp.getTypeId());
												dic.setItemName(globalType.getTypeName());
												dic.setProTypeId(globalType.getProTypeId());
												dic.setDicKey(temp.getTypeId());
												dic.setStatus("0");
												dictionaryDao.save(dic);
												temp.setTypeId(String.valueOf(dic.getDicId()));
											}
										}
										temp.setProjId(slSmallloanProject.getProjectId());
										if (temp.getSourceId() == null) {
											this.slRepaymentSourceDao.save(temp);
										} else {
											SlRepaymentSource rPersistent = this.slRepaymentSourceDao.get(temp.getSourceId());
											BeanUtil.copyNotNullProperties(rPersistent, temp);
											this.slRepaymentSourceDao.save(rPersistent);
										}
									
										
									}
								}
								//保存共同借款人
								String borrowerInfo=flowRunInfo.getRequest().getParameter("borrowerInfo");
								if(null != borrowerInfo && !"".equals(borrowerInfo)) {

									String[] borrowerInfoArr = borrowerInfo.split("@");
									
									for(int i=0; i<borrowerInfoArr.length; i++) {
										String str = borrowerInfoArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										BorrowerInfo bo = (BorrowerInfo)JSONMapper.toJava(parser.nextValue(),BorrowerInfo.class);
										if (bo.getBorrowerInfoId() == null) {
											bo.setProjectId(project.getProjectId());
											bo.setBusinessType(project.getBusinessType());
											bo.setOperationType(project.getOperationType());
											this.borrowerInfoDao.save(bo);
										} else {
											BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo.getBorrowerInfoId());
											BeanUtils.copyProperties(boPersistent, bo);
											this.borrowerInfoDao.merge(boPersistent);
										}
										if (null != bo.getType() && bo.getType() == 0) {
											if (null != bo.getCustomerId()) {
												Enterprise e = this.enterpriseDao.getById(bo.getCustomerId());
												e.setArea(bo.getAddress());
												e.setCciaa(bo.getCardNum());
												e.setTelephone(bo.getTelPhone());
												this.enterpriseDao.merge(e);
											}
										} else if (null != bo.getType() && bo.getType() == 1) {
											Person p = this.personDao.getById(bo.getCustomerId());
											p.setPostaddress(bo.getAddress());
											p.setCardnumber(bo.getCardNum());
											p.setCellphone(bo.getTelPhone());
											this.personDao.merge(p);
										}
									}
								}
								
								//保存费用信息
								String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData");
								String isCheck=flowRunInfo.getRequest().getParameter("isCheck");
								if(null!=slActualToChargeJsonData && !slActualToChargeJsonData.equals("")){
									slActualToChargeService.savejson(slActualToChargeJsonData, project.getProjectId(), project.getBusinessType(), Short.valueOf(isCheck), project.getCompanyId());
								}
								//确定资金方案节点  把slSmallloanProject的projectStatus设成2
								if(null!=thisTaskName && thisTaskName.equals("fundScheme")){
									project.setProjectStatus(Short.valueOf("2"));
								}
									// 平台资金款项信息
									if(null!=fundProjectId && !fundProjectId.equals("")){
										 
									
										if (null != ownFund && project.getProjectStatus()!=2) {
											BeanUtil.copyNotNullProperties(ownFund,project);
											ownFund.setOwnJointMoney(project.getProjectMoney());
											
											bpFundProjectDao.merge(ownFund);
										}
										//保存款项信息
										String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
										if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
											List<SlFundIntent> oldList = slFundIntentService.getbyPreceptId(ownFund.getId());
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
													SlFundIntent1.setIsCheck(Short.valueOf(isCheck));
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
													SlFundIntent1.setIsCheck(Short.valueOf(isCheck));
													//}
													SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
													BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

													slFundIntentService.merge(slFundIntent2);

												}
												
											}
										}
									}
									if(null!=thisTaskName && thisTaskName.equals("loanConfim")){
										List<SlActualToCharge> slist=slActualToChargeDao.getAllbyProjectId(project.getProjectId(), project.getBusinessType());
										for(SlActualToCharge s:slist){
											s.setIsCheck(Short.valueOf("0"));
											slActualToChargeDao.merge(s);
										}
										BpFundProject ownBpFundProject=new BpFundProject();
										BeanUtil.populateEntity(flowRunInfo.getRequest(), ownBpFundProject, "ownBpFundProject");
										BeanUtil.copyNotNullProperties(ownFund, ownBpFundProject);
										ownFund.setProjectStatus(Short.valueOf("1"));
										bpFundProjectDao.merge(ownFund);
									}
									if(transitionName.contains("B角签字")){	
										if (project.getAppUserIdB() != null && !"".equals(project.getAppUserIdB())) {
											vars.put("flowAssignId", project.getAppUserIdB());
										}
									}
								dao.merge(project);
							
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
	public Integer savePerHistorySmallProjectInfoNextStep(
			FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
				String transitionName = flowRunInfo.getTransitionName();
				BpFundProject ownFund = bpFundProjectDao.get(Long.valueOf(fundProjectId));
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
						ownFund.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						bpFundProjectDao.merge(ownFund);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								Spouse spouse=new Spouse();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse, "spouse");
								WorkCompany workCompany=new WorkCompany();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), workCompany, "workCompany");
								personDao.savePersonInfo(person, spouse, workCompany);
									EnterpriseBank enterpriseBank=new EnterpriseBank();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank, "enterpriseBank");
									if (enterpriseBank.getId() == null) {
										List<EnterpriseBank> list = enterpriseBankService.getBankList(person.getId(), Short.valueOf("1"), Short.valueOf("0"),Short.valueOf("0"));
										for (EnterpriseBank e : list) {
											e.setIscredit(Short.valueOf("1"));
											enterpriseBankService.merge(e);
										}
										enterpriseBank.setEnterpriseid(person.getId());
										enterpriseBank.setIscredit(Short.valueOf("0"));
										enterpriseBank.setIsEnterprise(Short.valueOf("1"));
										enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
										enterpriseBank.setIsInvest(Short.valueOf("0"));
										enterpriseBankService.save(enterpriseBank);
									} else {
										EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(EnterpriseBank.class, enterpriseBank.getId());
										BeanUtil.copyNotNullProperties(bank, enterpriseBank);
										enterpriseBankService.merge(bank);
									}
									//保存资金需求
										BpMoneyBorrowDemand bpMoneyBorrowDemand=new BpMoneyBorrowDemand();
										BeanUtil.populateEntity(flowRunInfo.getRequest(), bpMoneyBorrowDemand, "bpMoneyBorrowDemand");
										bpMoneyBorrowDemand.setProjectID(slSmallloanProject.getProjectId());
										if (bpMoneyBorrowDemand.getBorrowid() == null) {
											this.bpMoneyBorrowDemandDao.save(bpMoneyBorrowDemand);
										} else {
											BpMoneyBorrowDemand bbd = bpMoneyBorrowDemandDao.load(bpMoneyBorrowDemand.getBorrowid());
											BeanUtil.copyNotNullProperties(bbd, bpMoneyBorrowDemand);
											this.bpMoneyBorrowDemandDao.merge(bbd);
										}
											
								//保存第一还款来源
								String repaymentSource=flowRunInfo.getRequest().getParameter("repaymentSource");
								if(null != repaymentSource && !"".equals(repaymentSource)) {

									String[] repaymentSourceArr = repaymentSource.split("@");
									
									for(int i=0; i<repaymentSourceArr.length; i++) {
										String str = repaymentSourceArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										SlRepaymentSource temp = (SlRepaymentSource)JSONMapper.toJava(parser.nextValue(),SlRepaymentSource.class);
										boolean flag = StringUtil.isNumeric(temp.getTypeId());
										GlobalType globalType = globalTypeDao.getByNodeKey("repaymentSource").get(0);
										if (flag == false) {
											Dictionary dic = new Dictionary();
											dic.setItemValue(temp.getTypeId());
											dic.setItemName(globalType.getTypeName());
											dic.setProTypeId(globalType.getProTypeId());
											dic.setDicKey(temp.getTypeId());
											dic.setStatus("0");
											dictionaryDao.save(dic);
											temp.setTypeId(String.valueOf(dic.getDicId()));
										} else {
											Dictionary cd = dictionaryDao.get(Long.valueOf(temp
													.getTypeId()));
											if (null == cd) {
												Dictionary dic = new Dictionary();
												dic.setItemValue(temp.getTypeId());
												dic.setItemName(globalType.getTypeName());
												dic.setProTypeId(globalType.getProTypeId());
												dic.setDicKey(temp.getTypeId());
												dic.setStatus("0");
												dictionaryDao.save(dic);
												temp.setTypeId(String.valueOf(dic.getDicId()));
											}
										}
										temp.setProjId(slSmallloanProject.getProjectId());
										if (temp.getSourceId() == null) {
											this.slRepaymentSourceDao.save(temp);
										} else {
											SlRepaymentSource rPersistent = this.slRepaymentSourceDao.get(temp.getSourceId());
											BeanUtil.copyNotNullProperties(rPersistent, temp);
											this.slRepaymentSourceDao.save(rPersistent);
										}
									
										
									}
								}
								//保存共同借款人
								String borrowerInfo=flowRunInfo.getRequest().getParameter("borrowerInfo");
								if(null != borrowerInfo && !"".equals(borrowerInfo)) {

									String[] borrowerInfoArr = borrowerInfo.split("@");
									
									for(int i=0; i<borrowerInfoArr.length; i++) {
										String str = borrowerInfoArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										BorrowerInfo bo = (BorrowerInfo)JSONMapper.toJava(parser.nextValue(),BorrowerInfo.class);
										if (bo.getBorrowerInfoId() == null) {
											bo.setProjectId(project.getProjectId());
											bo.setBusinessType(project.getBusinessType());
											bo.setOperationType(project.getOperationType());
											this.borrowerInfoDao.save(bo);
										} else {
											BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo.getBorrowerInfoId());
											BeanUtils.copyProperties(boPersistent, bo);
											this.borrowerInfoDao.merge(boPersistent);
										}
										if (null != bo.getType() && bo.getType() == 0) {
											if (null != bo.getCustomerId()) {
												Enterprise e = this.enterpriseDao.getById(bo.getCustomerId());
												e.setArea(bo.getAddress());
												e.setCciaa(bo.getCardNum());
												e.setTelephone(bo.getTelPhone());
												this.enterpriseDao.merge(e);
											}
										} else if (null != bo.getType() && bo.getType() == 1) {
											Person p = this.personDao.getById(bo.getCustomerId());
											p.setPostaddress(bo.getAddress());
											p.setCardnumber(bo.getCardNum());
											p.setCellphone(bo.getTelPhone());
											this.personDao.merge(p);
										}
									}
								}
								
								//保存费用信息
								String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData");
								if(null!=slActualToChargeJsonData && !slActualToChargeJsonData.equals("")){
									slActualToChargeService.savejson(slActualToChargeJsonData, project.getProjectId(), project.getBusinessType(), Short.valueOf("0"), project.getCompanyId());
								}
								
									
									// 平台资金款项信息
									if(null!=fundProjectId && !fundProjectId.equals("")){
										 
									
										if (null != ownFund) {
											BeanUtil.copyNotNullProperties(ownFund,project);
											ownFund.setOwnJointMoney(project.getProjectMoney());
											
											bpFundProjectDao.merge(ownFund);
										}
										//保存款项信息
										String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
										if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
											List<SlFundIntent> oldList = slFundIntentService.getbyPreceptId(ownFund.getId());
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
													SlFundIntent1.setIsCheck(Short.valueOf("0"));
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
													SlFundIntent1.setIsCheck(Short.valueOf("0"));
													//}
													SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
													BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

													slFundIntentService.merge(slFundIntent2);

												}
												
											}
										}
									}
									
									ownFund.setProjectStatus(Short.valueOf("1"));
									bpFundProjectDao.merge(ownFund);
									project.setProjectStatus(Short.valueOf("2"));
								dao.merge(project);
							
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
	public Integer saveEntHistorySmallProjectInfoNextStep(
			FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
				String flag=flowRunInfo.getRequest().getParameter("flag");
				String transitionName = flowRunInfo.getTransitionName();
				BpFundProject ownFund = bpFundProjectDao.get(Long.valueOf(fundProjectId));
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
						ownFund.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						bpFundProjectDao.merge(ownFund);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								String gudongInfo=flowRunInfo.getRequest().getParameter("gudongInfo");
								Enterprise enterprise=new Enterprise();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								enterpriseDao.saveSingleEnterprise(enterprise, person, gudongInfo);
								//保存开户银行信息
								EnterpriseBank enterpriseBank=new EnterpriseBank();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank, "enterpriseBank");
								if (enterpriseBank.getId() == null) {
									List<EnterpriseBank> list = enterpriseBankDao.getBankList(project.getOppositeID().intValue(), Short.valueOf("0"), Short.valueOf("0"),Short.valueOf("0"));
									for (EnterpriseBank e : list) {
										e.setIscredit(Short.valueOf("1"));
										enterpriseBankDao.merge(e);
									}
									enterpriseBank.setEnterpriseid(project.getOppositeID().intValue());
									enterpriseBank.setIscredit(Short.valueOf("0"));
									enterpriseBank.setIsEnterprise(Short.valueOf("0"));
									enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
									enterpriseBank.setIsInvest(Short.valueOf("0"));
									enterpriseBankDao.save(enterpriseBank);
								} else {
									EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(EnterpriseBank.class, enterpriseBank.getId());
									BeanUtil.copyNotNullProperties(bank, enterpriseBank);
									enterpriseBankDao.merge(bank);
								}
								//保存第一还款来源
								String repaymentSource=flowRunInfo.getRequest().getParameter("repaymentSource");
								if(null != repaymentSource && !"".equals(repaymentSource)) {

									String[] repaymentSourceArr = repaymentSource.split("@");
									
									for(int i=0; i<repaymentSourceArr.length; i++) {
										String str = repaymentSourceArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										SlRepaymentSource temp = (SlRepaymentSource)JSONMapper.toJava(parser.nextValue(),SlRepaymentSource.class);
										boolean f= StringUtil.isNumeric(temp.getTypeId());
										GlobalType globalType = globalTypeDao.getByNodeKey("repaymentSource").get(0);
										if (f== false) {
											Dictionary dic = new Dictionary();
											dic.setItemValue(temp.getTypeId());
											dic.setItemName(globalType.getTypeName());
											dic.setProTypeId(globalType.getProTypeId());
											dic.setDicKey(temp.getTypeId());
											dic.setStatus("0");
											dictionaryDao.save(dic);
											temp.setTypeId(String.valueOf(dic.getDicId()));
										} else {
											Dictionary cd = dictionaryDao.get(Long.valueOf(temp
													.getTypeId()));
											if (null == cd) {
												Dictionary dic = new Dictionary();
												dic.setItemValue(temp.getTypeId());
												dic.setItemName(globalType.getTypeName());
												dic.setProTypeId(globalType.getProTypeId());
												dic.setDicKey(temp.getTypeId());
												dic.setStatus("0");
												dictionaryDao.save(dic);
												temp.setTypeId(String.valueOf(dic.getDicId()));
											}
										}
										temp.setProjId(slSmallloanProject.getProjectId());
										if (temp.getSourceId() == null) {
											this.slRepaymentSourceDao.save(temp);
										} else {
											SlRepaymentSource rPersistent = this.slRepaymentSourceDao.get(temp.getSourceId());
											BeanUtil.copyNotNullProperties(rPersistent, temp);
											this.slRepaymentSourceDao.save(rPersistent);
										}
									
										
									}
								}
								//保存共同借款人
								String borrowerInfo=flowRunInfo.getRequest().getParameter("borrowerInfo");
								if(null != borrowerInfo && !"".equals(borrowerInfo)) {

									String[] borrowerInfoArr = borrowerInfo.split("@");
									
									for(int i=0; i<borrowerInfoArr.length; i++) {
										String str = borrowerInfoArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										BorrowerInfo bo = (BorrowerInfo)JSONMapper.toJava(parser.nextValue(),BorrowerInfo.class);
										if (bo.getBorrowerInfoId() == null) {
											bo.setProjectId(project.getProjectId());
											bo.setBusinessType(project.getBusinessType());
											bo.setOperationType(project.getOperationType());
											this.borrowerInfoDao.save(bo);
										} else {
											BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo.getBorrowerInfoId());
											BeanUtils.copyProperties(boPersistent, bo);
											this.borrowerInfoDao.merge(boPersistent);
										}
										if (null != bo.getType() && bo.getType() == 0) {
											if (null != bo.getCustomerId()) {
												Enterprise e = this.enterpriseDao.getById(bo.getCustomerId());
												e.setArea(bo.getAddress());
												e.setCciaa(bo.getCardNum());
												e.setTelephone(bo.getTelPhone());
												this.enterpriseDao.merge(e);
											}
										} else if (null != bo.getType() && bo.getType() == 1) {
											Person p = this.personDao.getById(bo.getCustomerId());
											p.setPostaddress(bo.getAddress());
											p.setCardnumber(bo.getCardNum());
											p.setCellphone(bo.getTelPhone());
											this.personDao.merge(p);
										}
									}
								}
								//保存费用信息
								String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData");
								if(null!=slActualToChargeJsonData && !slActualToChargeJsonData.equals("")){
									slActualToChargeService.savejson(slActualToChargeJsonData, project.getProjectId(), project.getBusinessType(), Short.valueOf("0"), project.getCompanyId());
								}
								
								if (null != ownFund) {
									BeanUtil.copyNotNullProperties(ownFund,project);
									ownFund.setOwnJointMoney(project.getProjectMoney());
									bpFundProjectDao.merge(ownFund);
								}
								
									
								//保存款项信息
								String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
								if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
									List<SlFundIntent> oldList = slFundIntentService.getbyPreceptId(ownFund.getId());
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
											SlFundIntent1.setIsCheck(Short.valueOf("0"));
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
											SlFundIntent1.setIsCheck(Short.valueOf("0"));
											//}
											SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
											BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

											slFundIntentService.merge(slFundIntent2);

										}
										
									}
								}
								
								ownFund.setProjectStatus(Short.valueOf("1"));
								bpFundProjectDao.merge(ownFund);
								
								project.setProjectStatus(Short.valueOf("2"));
								dao.merge(project);
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
	 * 转让标起息办理流程提交下一步
	 */
	@Override
	public Integer updateBpFundInfoOrNextStep(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String transitionName = flowRunInfo.getTransitionName();
				String bidPlanId=flowRunInfo.getRequest().getParameter("bidPlanId");
				PlBidPlan plan=plBidPlanDao.get(Long.valueOf(bidPlanId));
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						plan.setState(plan.getOriginalState());
						plan.setIsStart(null);
						plan.setStartIntentDate(null);
						plan.setEndIntentDate(null);
						plBidPlanDao.merge(plan);
						List<SlActualToCharge> alist=slActualToChargeDao.listByBidPlanId(plan.getBidId());
						for(SlActualToCharge s:alist){
							slActualToChargeDao.remove(s);
						}
						List<BpFundIntent> plist=bpFundIntentDao.getByBidPlanId(plan.getBidId());
						for(BpFundIntent f:plist){
							bpFundIntentDao.remove(f);
						}
						List<ProcreditContract> clist=procreditContractDao.getByPlanId(plan.getBidId());
						for(ProcreditContract c:clist){
							procreditContractDao.remove(c);
						}
						List<InvestPersonInfo> vlist=investPersonInfoService.getByBidPlanId(plan.getBidId());
						for(InvestPersonInfo p:vlist){
							p.setContractUrls(null);
							investPersonInfoService.merge(p);
						}
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								
								PlBidPlan plBidPlan=new PlBidPlan();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), plBidPlan, "plBidPlan");
								BeanUtil.copyNotNullProperties(plan, plBidPlan);
								BpFundProject fundProject =null;
								String bidPlanStatus=flowRunInfo.getRequest().getParameter("bidPlanStatus");
								
								String chargeJson=flowRunInfo.getRequest().getParameter("chargeJson");
								String isCheck=flowRunInfo.getRequest().getParameter("isCheck");
							
								if(plan.getProType().equals("P_Or") || plan.getProType().equals("B_Or")){
									
									// 自有资金款项信息
									String  ownId=flowRunInfo.getRequest().getParameter("ownBpFundProject.id");
									if(null!=ownId && !ownId.equals("")){
										fundProject = bpFundProjectDao.get(Long.valueOf(ownId));
									}
									
								}
								//投资人的放款收息表
								//bpFundIntentDao.saveFundIntent(pFundsDatas, plan, fundProject, Short.valueOf(isCheck));
								
								
								//保存费用信息
								if(null!=chargeJson && !chargeJson.equals("")){
									slActualToChargeService.savejson(chargeJson, fundProject.getProjectId(), fundProject.getBusinessType(), Short.valueOf(isCheck), fundProject.getCompanyId());
								}
								if(transitionName.equals("完成")){
								/*	List<SlActualToCharge> alist=slActualToChargeDao.listByBidPlanId(plan.getBidId());
									for(SlActualToCharge a:alist){
										a.setIsCheck(Short.valueOf("0"));
										slActualToChargeDao.merge(a);
									}
									List<SlFundIntent> slist=slFundIntentDao.getListByBidPlanId(plan.getBidId());
									for(SlFundIntent s:slist){
										s.setIsCheck(Short.valueOf("0"));
										slFundIntentDao.merge(s);
									}*/
									List<BpFundIntent> plist=bpFundIntentDao.getByBidPlanId(plan.getBidId());
									for(BpFundIntent p:plist){
										p.setIsCheck(Short.valueOf("0"));
										bpFundIntentDao.merge(p);
									}
									
									if(bidPlanStatus !=null&&!"".equals(bidPlanStatus )&&!"undefined".equals(bidPlanStatus )){
										changeStatusBidPlan(Long.parseLong(bidPlanId), Integer.valueOf(bidPlanStatus));
									}
									
								}
								plBidPlanDao.merge(plan);
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
	 * 个人债权补录---向债权库插入数据
	 */
	@Override
	public Integer saveOrFastSmallProjectInfoCreditBank(FlowRunInfo flowRunInfo){
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String flag=flowRunInfo.getRequest().getParameter("flag");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								String thisTaskName=flowRunInfo.getRequest().getParameter("thisTaskName");
								String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								Spouse spouse=new Spouse();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), spouse, "spouse");
								WorkCompany workCompany=new WorkCompany();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), workCompany, "workCompany");
								personDao.savePersonInfo(person, spouse, ((null!=thisTaskName && thisTaskName.equals("saveInfo"))?workCompany:null));
								if(null!=thisTaskName && thisTaskName.equals("saveInfo")){
									//保存资金需求
									BpMoneyBorrowDemand bpMoneyBorrowDemand=new BpMoneyBorrowDemand();
									BeanUtil.populateEntity(flowRunInfo.getRequest(), bpMoneyBorrowDemand, "bpMoneyBorrowDemand");
									bpMoneyBorrowDemand.setProjectID(slSmallloanProject.getProjectId());
									if (bpMoneyBorrowDemand.getBorrowid() == null) {
										this.bpMoneyBorrowDemandDao.save(bpMoneyBorrowDemand);
									} else {
										BpMoneyBorrowDemand bbd = bpMoneyBorrowDemandDao.load(bpMoneyBorrowDemand.getBorrowid());
										BeanUtil.copyNotNullProperties(bbd, bpMoneyBorrowDemand);
										this.bpMoneyBorrowDemandDao.merge(bbd);
									}
									project.setProjectStatus(Short.valueOf("2"));
								}	
								//平台资金款项信息
						     	BpPersionOrPro bpPersionOrPro=null;
								if(null!=fundProjectId && !fundProjectId.equals("")){
									String mineType=flowRunInfo.getRequest().getParameter("ownBpFundProject.mineType");
									String mineId=flowRunInfo.getRequest().getParameter("ownBpFundProject.mineId");
									BpFundProject ownFund = bpFundProjectService.get(Long.valueOf(fundProjectId));
									if (null != ownFund) {
										BeanUtil.copyNotNullProperties(ownFund,project);
										ownFund.setOwnJointMoney(project.getProjectMoney());
										ownFund.setProjectStatus(Short.valueOf("1"));
										if(null!=mineType && !mineType.equals("")){
											ownFund.setMineType(mineType);
										}
										if(null!=mineId && !mineId.equals("")){
											ownFund.setMineId(Long.valueOf(mineId));
										}
										BeanUtil.populateEntity(flowRunInfo.getRequest(), ownFund, "ownBpFundProject");
										bpFundProjectDao.merge(ownFund);
									}
									//保存款项信息
									String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
									if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
										List<SlFundIntent> oldList = slFundIntentService.getbyPreceptId(ownFund.getId());
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
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
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
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
												SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
												BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);
												slFundIntentService.merge(slFundIntent2);
											}
										}
									}
									if(null!=thisTaskName && thisTaskName.equals("fbzq")){
										String userName="";
										BpPersionOrPro pOrPro = bpPersionOrProDao.getByBpFundProjectId(ownFund.getId());
										if(pOrPro==null){
											pOrPro = new BpPersionOrPro();
											this.initPersionP2p(ownFund, pOrPro, person);
											if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
												pOrPro.setObligatoryPersion(userName);
											}
											
											bpPersionOrPro=bpPersionOrProDao.save(pOrPro);
										}else{
											this.initPersionP2p(ownFund, pOrPro, person);
											if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
												pOrPro.setObligatoryPersion(userName);
											}
											bpPersionOrPro=bpPersionOrProDao.merge(pOrPro);
										}
										
										if("mmproduceOr".equals(project.getChildType()) ){//理财产品债权项目
											pOrPro.setOrginalType(Short.valueOf("10"));
										}else if("mmplanOr".equals(project.getChildType())){//理财计划债权项目
											pOrPro.setOrginalType(Short.valueOf("20"));
										}else{//线上债权招标项目
											pOrPro.setOrginalType(Short.valueOf("1"));
										}
										bpPersionOrProDao.merge(pOrPro);
									
									}
								}
								dao.merge(project);
								
								PlBidPlan plan = new PlBidPlan();
								plan.setPOrProId(bpPersionOrPro.getPorProId());
								plan.setProType("P_Or");
								plan.setBiddingTypeId(Long.valueOf(3));
								plan.setState(5);
								plan.setBidProName(bpPersionOrPro.getProName());
								plan.setBidProNumber(bpPersionOrPro.getProNumber());
								plan.setBidMoney(bpPersionOrPro.getBidMoney());
								plan.setBidMoneyScale(Double.valueOf(100));
								plan.setChildType(project.getChildType());//债权类型
								plan.setPayIntersetWay(bpPersionOrPro.getPayIntersetWay());
								plan.setReceiverName(bpPersionOrPro.getReceiverName());//原始债权人名称
								plan.setReceiverP2PAccountNumber(bpPersionOrPro.getReceiverP2PAccountNumber());//原始债权人账号
								
								//往债权库插入数据
								plMmObligatoryRightChildrenService.createObligatoryRightChildren(plan);
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
	 * 企业债权补录---向债权库插入数据
	 */
	@Override      
	public Integer saveOrEnterpriseProjectInfoCreditBank(FlowRunInfo flowRunInfo) {
		try {
			if (flowRunInfo.isBack()) {
				return 1;
			} else {
				String projectId=flowRunInfo.getRequest().getParameter("slSmallloanProject.projectId");
				String fundProjectId=flowRunInfo.getRequest().getParameter("fundProjectId");
				String flag=flowRunInfo.getRequest().getParameter("flag");
				String transitionName = flowRunInfo.getTransitionName();
				if (transitionName != null && !"".equals(transitionName)) {
					Map<String, Object> vars = new HashMap<String, Object>();
					if (transitionName.contains("终止") || transitionName.contains("结束")) {
						flowRunInfo.setStop(true);
						SlSmallloanProject project=dao.get(Long.valueOf(projectId));
						project.setProjectStatus(Constants.PROJECT_STATUS_STOP);
						dao.merge(project);
					} else {
						ProcessForm currentForm = processFormDao.getByTaskId(flowRunInfo.getTaskId());
						if (currentForm != null) {
							boolean isToNextTask = creditProjectService.compareTaskSequence(currentForm.getRunId(), currentForm.getActivityName(),transitionName);
							if (!isToNextTask) {// true表示流程正常往下流转,false则表示打回。
								flowRunInfo.setAfresh(true);// 表示打回重做
								vars.put("nextTaskAssignId", "true");// 表示为打回重做，需要设置打回的目标任务处理人
								vars.put("targetActivityName", transitionName);// 打回的目标任务名称
							}else{
								String thisTaskName=flowRunInfo.getRequest().getParameter("thisTaskName");
								SlSmallloanProject slSmallloanProject=new SlSmallloanProject();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), slSmallloanProject, "slSmallloanProject");
								SlSmallloanProject project=dao.get(slSmallloanProject.getProjectId());
								BeanUtil.copyNotNullProperties(project, slSmallloanProject);
								//保存客户信息
								String gudongInfo=flowRunInfo.getRequest().getParameter("gudongInfo");
								Enterprise enterprise=new Enterprise();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterprise, "enterprise");
								Person person=new Person();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), person, "person");
								enterpriseDao.saveSingleEnterprise(enterprise, person, gudongInfo);
								//保存开户银行信息
								EnterpriseBank enterpriseBank=new EnterpriseBank();
								BeanUtil.populateEntity(flowRunInfo.getRequest(), enterpriseBank, "enterpriseBank");
								if (enterpriseBank.getId() == null) {
									List<EnterpriseBank> list = enterpriseBankDao.getBankList(project.getOppositeID().intValue(), Short.valueOf("0"), Short.valueOf("0"),Short.valueOf("0"));
									for (EnterpriseBank e : list) {
										e.setIscredit(Short.valueOf("1"));
										enterpriseBankDao.merge(e);
									}
									enterpriseBank.setEnterpriseid(project.getOppositeID().intValue());
									enterpriseBank.setIscredit(Short.valueOf("0"));
									enterpriseBank.setIsEnterprise(Short.valueOf("0"));
									enterpriseBank.setCompanyId(ContextUtil.getLoginCompanyId());
									enterpriseBank.setIsInvest(Short.valueOf("0"));
									enterpriseBankDao.save(enterpriseBank);
								} else {
									EnterpriseBank bank = (EnterpriseBank) creditBaseDao.getById(EnterpriseBank.class, enterpriseBank.getId());
									BeanUtil.copyNotNullProperties(bank, enterpriseBank);
									enterpriseBankDao.merge(bank);
								}
								//保存第一还款来源
								String repaymentSource=flowRunInfo.getRequest().getParameter("repaymentSource");
								if(null != repaymentSource && !"".equals(repaymentSource)) {
									String[] repaymentSourceArr = repaymentSource.split("@");
									for(int i=0; i<repaymentSourceArr.length; i++) {
										String str = repaymentSourceArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										SlRepaymentSource temp = (SlRepaymentSource)JSONMapper.toJava(parser.nextValue(),SlRepaymentSource.class);
										boolean f= StringUtil.isNumeric(temp.getTypeId());
										GlobalType globalType = globalTypeDao.getByNodeKey("repaymentSource").get(0);
										if (f== false) {
											Dictionary dic = new Dictionary();
											dic.setItemValue(temp.getTypeId());
											dic.setItemName(globalType.getTypeName());
											dic.setProTypeId(globalType.getProTypeId());
											dic.setDicKey(temp.getTypeId());
											dic.setStatus("0");
											dictionaryDao.save(dic);
											temp.setTypeId(String.valueOf(dic.getDicId()));
										} else {
											Dictionary cd = dictionaryDao.get(Long.valueOf(temp.getTypeId()));
											if (null == cd) {
												Dictionary dic = new Dictionary();
												dic.setItemValue(temp.getTypeId());
												dic.setItemName(globalType.getTypeName());
												dic.setProTypeId(globalType.getProTypeId());
												dic.setDicKey(temp.getTypeId());
												dic.setStatus("0");
												dictionaryDao.save(dic);
												temp.setTypeId(String.valueOf(dic.getDicId()));
											}
										}
										temp.setProjId(slSmallloanProject.getProjectId());
										if (temp.getSourceId() == null) {
											this.slRepaymentSourceDao.save(temp);
										} else {
											SlRepaymentSource rPersistent = this.slRepaymentSourceDao.get(temp.getSourceId());
											BeanUtil.copyNotNullProperties(rPersistent, temp);
											this.slRepaymentSourceDao.save(rPersistent);
										}
									}
								}
								//保存共同借款人
								String borrowerInfo=flowRunInfo.getRequest().getParameter("borrowerInfo");
								if(null != borrowerInfo && !"".equals(borrowerInfo)) {
									String[] borrowerInfoArr = borrowerInfo.split("@");
									for(int i=0; i<borrowerInfoArr.length; i++) {
										String str = borrowerInfoArr[i];
										JSONParser parser = new JSONParser(new StringReader(str));
										BorrowerInfo bo = (BorrowerInfo)JSONMapper.toJava(parser.nextValue(),BorrowerInfo.class);
										if (bo.getBorrowerInfoId() == null) {
											bo.setProjectId(project.getProjectId());
											bo.setBusinessType(project.getBusinessType());
											bo.setOperationType(project.getOperationType());
											this.borrowerInfoDao.save(bo);
										} else {
											BorrowerInfo boPersistent = this.borrowerInfoDao.get(bo.getBorrowerInfoId());
											BeanUtils.copyProperties(boPersistent, bo);
											this.borrowerInfoDao.merge(boPersistent);
										}
										if (null != bo.getType() && bo.getType() == 0) {
											if (null != bo.getCustomerId()) {
												Enterprise e = this.enterpriseDao.getById(bo.getCustomerId());
												e.setArea(bo.getAddress());
												e.setCciaa(bo.getCardNum());
												e.setTelephone(bo.getTelPhone());
												this.enterpriseDao.merge(e);
											}
										} else if (null != bo.getType() && bo.getType() == 1) {
											Person p = this.personDao.getById(bo.getCustomerId());
											p.setPostaddress(bo.getAddress());
											p.setCardnumber(bo.getCardNum());
											p.setCellphone(bo.getTelPhone());
											this.personDao.merge(p);
										}
									}
								}
								//保存费用信息
								String slActualToChargeJsonData=flowRunInfo.getRequest().getParameter("slActualToChargeJsonData");
								if(null!=slActualToChargeJsonData && !slActualToChargeJsonData.equals("")){
									slActualToChargeService.savejson(slActualToChargeJsonData, project.getProjectId(), project.getBusinessType(), Short.valueOf("0"), project.getCompanyId());
								}
						
								BpBusinessOrPro bpBusinessOrPro=null;
								//平台资金
								if(null!=fundProjectId && !fundProjectId.equals("")){
									String mineType=flowRunInfo.getRequest().getParameter("ownBpFundProject.mineType");
									String mineId=flowRunInfo.getRequest().getParameter("ownBpFundProject.mineId");
									BpFundProject ownFund = bpFundProjectService.get(Long.valueOf(fundProjectId));
									if (null != ownFund) {
										BeanUtil.copyNotNullProperties(ownFund,project);
										ownFund.setOwnJointMoney(project.getProjectMoney());
										ownFund.setProjectStatus(Short.valueOf("1"));
										if(null!=mineType && !mineType.equals("")){
											ownFund.setMineType(mineType);
										}
										if(null!=mineId && !mineId.equals("")){
											ownFund.setMineId(Long.valueOf(mineId));
										}
										BeanUtil.populateEntity(flowRunInfo.getRequest(), ownFund, "ownBpFundProject");
										
										bpFundProjectDao.merge(ownFund);
									}
									//保存款项信息
									String fundIntentJsonData=flowRunInfo.getRequest().getParameter("fundIntentJsonData");
									if (null != fundIntentJsonData && !"".equals(fundIntentJsonData)) {
										List<SlFundIntent> oldList = slFundIntentService.getbyPreceptId(ownFund.getId());
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
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
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
												SlFundIntent1.setIsCheck(Short.valueOf("0"));
												SlFundIntent slFundIntent2 = slFundIntentService.get(SlFundIntent1.getFundIntentId());
												BeanUtil.copyNotNullProperties(slFundIntent2,SlFundIntent1);

												slFundIntentService.merge(slFundIntent2);
											}
										}
									}
									if(null!=thisTaskName && thisTaskName.equals("fbzq")){
										String userName="";
										BpBusinessOrPro bOrPro = bpBusinessOrProDao.getBpBusinessOrProByMoneyPlanId(ownFund.getId());
										if(bOrPro==null){
											bOrPro = new BpBusinessOrPro();
											this.initBusinessP2p(ownFund, bOrPro, enterprise);
											if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
												bOrPro.setObligatoryPersion(userName);
											}
											bpBusinessOrPro=bpBusinessOrProDao.save(bOrPro);
										}else{
											this.initBusinessP2p(ownFund, bOrPro, enterprise);
											if(null!=userName&&!"".equals(userName)&&!"undefined".equals(userName)){
												bOrPro.setObligatoryPersion(userName);
											}
											bpBusinessOrPro=bpBusinessOrProDao.merge(bOrPro);
										}
										if("mmproduceOr".equals(project.getChildType()) ){//理财产品债权项目
											bOrPro.setOrginalType(Short.valueOf("10"));
										}else if("mmplanOr".equals(project.getChildType())){//理财计划债权项目
											bOrPro.setOrginalType(Short.valueOf("20"));
										}else{//线上债权招标项目
											bOrPro.setOrginalType(Short.valueOf("1"));
										}
										bpBusinessOrProDao.merge(bOrPro);
									}
								}
							
								dao.merge(project);
								
								SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
								PlBidPlan plan = new PlBidPlan();
								plan.setBorProId(bpBusinessOrPro.getBorProId());
								plan.setProType("B_Or");
								plan.setBiddingTypeId(Long.valueOf(3));
								plan.setState(5);
								plan.setBidProName(bpBusinessOrPro.getProName());
								plan.setBidProNumber(bpBusinessOrPro.getProNumber());
								plan.setBidMoney(bpBusinessOrPro.getBidMoney());
								plan.setBidMoneyScale(Double.valueOf(100));
								plan.setChildType(project.getChildType());//债权类型
								plan.setPayIntersetWay(bpBusinessOrPro.getPayIntersetWay());
								plan.setReceiverName(bpBusinessOrPro.getReceiverName());//原始债权人名称
								plan.setReceiverP2PAccountNumber(bpBusinessOrPro.getReceiverP2PAccountNumber());//原始债权人账号
								
								//往债权库插入数据
								plMmObligatoryRightChildrenService.createObligatoryRightChildren(plan);
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
	public SlSmallloanProject getProjectNumber(String projectNumberKey) {
		return dao.getProjectNumber(projectNumberKey);
	}
	
	@Override
	public void getprojectByCustomerId(Integer personId,
			PageBean<Enterprise> pageBean) {
		dao.getprojectByCustomerId(personId, pageBean);
		
	}
}