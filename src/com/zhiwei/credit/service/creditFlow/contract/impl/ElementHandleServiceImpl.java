package com.zhiwei.credit.service.creditFlow.contract.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.credit.proj.entity.ProcreditMortgageBusiness;
import com.credit.proj.entity.ProcreditMortgageBusinessandlive;
import com.credit.proj.entity.ProcreditMortgageCar;
import com.credit.proj.entity.ProcreditMortgageEducation;
import com.credit.proj.entity.ProcreditMortgageHouse;
import com.credit.proj.entity.ProcreditMortgageHouseground;
import com.credit.proj.entity.ProcreditMortgageIndustry;
import com.credit.proj.entity.ProcreditMortgageOfficebuilding;
import com.credit.proj.entity.VProcreditDictionary;
import com.credit.proj.entity.VProcreditDictionaryGuarantee;
import com.credit.proj.entity.VProcreditMortgageGlobal;
import com.credit.proj.entity.VProcreditMortgageLeaseFinance;
import com.credit.proj.entity.VProjMortBusAndLive;
import com.credit.proj.entity.VProjMortBusiness;
import com.credit.proj.entity.VProjMortHouse;
import com.credit.proj.entity.VProjMortHouseGround;
import com.credit.proj.entity.VProjMortIndustry;
import com.credit.proj.entity.VProjMortOfficeBuilding;
import com.credit.proj.entity.VProjMortProduct;
import com.credit.proj.entity.VProjMortStockOwnerShip;
import com.credit.proj.mortgage.business.service.BusinessServMort;
import com.credit.proj.mortgage.businessandlive.service.BusinessandliveService;
import com.credit.proj.mortgage.house.service.HouseService;
import com.credit.proj.mortgage.houseground.service.HousegroundService;
import com.credit.proj.mortgage.industry.service.IndustryService;
import com.credit.proj.mortgage.officebuilding.service.OfficebuildingService;
import com.hurong.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.core.util.ChineseUpperCaser;
import com.zhiwei.credit.core.util.ElementUtilExt;
import com.zhiwei.credit.core.util.MoneyFormat;
import com.zhiwei.credit.dao.creditFlow.common.CsBankDao;
import com.zhiwei.credit.dao.creditFlow.common.EnterpriseShareequityDao;
import com.zhiwei.credit.dao.creditFlow.contract.ProcreditContractDao;
import com.zhiwei.credit.dao.creditFlow.contract.VProcreditContractDao;
import com.zhiwei.credit.dao.creditFlow.customer.common.EnterpriseBankDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.SpouseDao;
import com.zhiwei.credit.dao.creditFlow.fileUploads.FileFormDao;
import com.zhiwei.credit.dao.creditFlow.finance.BpFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlPunishInterestDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.BorrowerInfoDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlAlterAccrualRecordDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlEarlyRepaymentRecordDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.VSmallloanProjectDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.supervise.SlSuperviseRecordDao;
import com.zhiwei.credit.dao.flow.ProcessFormDao;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.dao.system.AppUserDao;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.creditFlow.common.CsBank;
import com.zhiwei.credit.model.creditFlow.contract.AssignmentElementCode;
import com.zhiwei.credit.model.creditFlow.contract.AssureIntentBookElementCode;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;
import com.zhiwei.credit.model.creditFlow.contract.ElementCode;
import com.zhiwei.credit.model.creditFlow.contract.FinancingElementCode;
import com.zhiwei.credit.model.creditFlow.contract.GuaranteeElementCode;
import com.zhiwei.credit.model.creditFlow.contract.LeaseFinanceElementCode;
import com.zhiwei.credit.model.creditFlow.contract.PawnElementCode;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.SmallLoanElementCode;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.VBankBankcontactperson;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.customer.person.VPersonDic;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlPunishInterest;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.guarantee.project.GLGuaranteeloanProject;
import com.zhiwei.credit.model.creditFlow.leaseFinance.project.FlLeaseFinanceProject;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.BorrowerInfo;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlEarlyRepaymentRecord;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.VSmallloanProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.common.CsBankService;
import com.zhiwei.credit.service.creditFlow.contract.ElementHandleService;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.customer.person.SpouseService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.BpPersionDirProService;
import com.zhiwei.credit.service.creditFlow.fund.project.BpFundProjectService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.project.FlLeaseFinanceProjectService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.MySelfService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.util.financeUtil.FundIntentComparator;

import edu.emory.mathcs.backport.java.util.Collections;

public class ElementHandleServiceImpl implements ElementHandleService{

	private static final Log logger=LogFactory.getLog(ElementHandleServiceImpl.class);
	@Resource
	private CreditBaseDao creditBaseDao ;
	@Resource
	private SlFundIntentDao slFundIntentDao ;
	@Resource
	private BpFundProjectService bpFundProjectService ;
	@Resource
	private BpPersionDirProService bpPersionDirProService ;
	@Resource
	private SlPunishInterestDao slPunishInterestDao ;
	@Resource
	private SpouseService spouseService;
	@Resource
	private VSmallloanProjectDao vSmallloanProjectDao;
	@Resource
	private ProcessRunDao processRunDao;
	@Resource
	private ProcessFormDao processFormDao;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private AppUserDao appUserDao;
	@Resource
	private SlAlterAccrualRecordDao slAlterAccrualRecordDao;
	@Resource
	private SlEarlyRepaymentRecordDao slEarlyRepaymentRecordDao;
	@Resource
	private SlSuperviseRecordDao slSuperviseRecordDao;
	@Resource
	private HouseService houseService;
	@Resource
	private OfficebuildingService officebuildingService;
	@Resource
	private HousegroundService housegroundService;
	@Resource
	private BusinessServMort businessServMort;
	@Resource
	private BusinessandliveService businessandliveService;
	@Resource
	private IndustryService industryService;
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private FlLeaseFinanceProjectService flleaseFinanceProjectService; 
	@Resource
	private PlPawnProjectService plPawnProjectService; 
	@Resource
	private EnterpriseService enterpriseService; 
	@Resource
	private PersonService personService; 
	@Resource
	private EnterpriseBankDao enterpriseBankDao;
	@Resource
	private ProcreditContractDao procreditContractDao;
	@Resource
	private FileFormDao fileFormDao;
	@Resource
	private VProcreditContractDao vProcreditContractDao;
	@Resource
	private InvestEnterpriseService investEnterpriseService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private CsBankService csBankService;
	@Resource
	private MySelfService mySelfService;
	@Resource
	private CsBankDao csBankDao;
	@Resource
	private BorrowerInfoDao borrowerInfoDao;
	@Resource
	private EnterpriseShareequityDao enterpriseShareequityDao;
	@Resource
	private PersonDao personDao;
	@Resource
	private SpouseDao spouseDao;
	@Resource
	private BpCustRelationService  bpCustRelationService;
	@Resource
	private BpCustMemberService  bpCustMemberService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private PlBidPlanService plBidPlanService;
	

	//获得所有企业担保的系统要素
	public GuaranteeElementCode getGuaranteeElementBySystem(String proId , int conId ,int tempId, String contractType,String rnum) throws Exception{
		DecimalFormat myFormatter = new DecimalFormat("####.#");
		DecimalFormat myFormatter2 = new DecimalFormat("####.###");
		GuaranteeElementCode code = new GuaranteeElementCode();
		GLGuaranteeloanProject guaranteeProject = (GLGuaranteeloanProject)creditBaseDao.getById(GLGuaranteeloanProject.class, Long.parseLong(proId));
		ProcreditContract contract = (ProcreditContract)creditBaseDao.getById(ProcreditContract.class, conId);
		if(guaranteeProject.getProjectNumber()!= null){
			String projectNumber = guaranteeProject.getProjectNumber();//项目编号
			code.setDbprojectnum_v(projectNumber);
			/*int count = 0;
			if(!StringUtil.isNumeric(rnum)){
				rnum = "0";
			}
			count = Integer.parseInt(rnum);*/
			code.setDbndbyxsbh_v(contract.getContractNumber());//担保意向书编号
			code.setDbbzfdbhtbh_v(contract.getContractNumber());
			code.setDbwtdbhtbh_v(contract.getContractNumber());
			code.setDbdyfdbhtbh_v(contract.getContractNumber());
			code.setDbdcfddyfdbhtbh_v(contract.getContractNumber());
			code.setDbdczyfdbhtbh_v(contract.getContractNumber());
			code.setDbqlzyfdbhtbh_v(contract.getContractNumber());
			code.setDbzjzyfdbhtbh_v(contract.getContractNumber());
			code.setDbwtfdbhtbh_v(contract.getContractNumber());
			code.setDbgqhtbh_v(contract.getContractNumber());//股权合同编号
			code.setDbgqzyfdbhtbh_v(contract.getContractNumber());
			
		}
		//贷款银行
		if(guaranteeProject.getBankId()!= null){
			VBankBankcontactperson vbbcp =null;
			vbbcp = (VBankBankcontactperson)creditBaseDao.getById(VBankBankcontactperson.class, guaranteeProject.getBankId());
			if(vbbcp != null){
				AreaDic areaDic = null;
				areaDic = (AreaDic)creditBaseDao.getById(AreaDic.class, vbbcp.getFenbankid());
				if(areaDic != null && areaDic.getRemarks()!= null){
					code.setDbdkyhmc_v(areaDic.getRemarks());
				}
				
			}
		}
		//信贷种类
		if(guaranteeProject.getCreditType()!= null){
			Dictionary dic = null;
			dic  = (Dictionary)creditBaseDao.getById(Dictionary.class, guaranteeProject.getCreditType());
			if(dic != null){
				code.setDbxdzl_v(dic.getItemValue());
			}
		}
		//币种
		if(guaranteeProject.getCurrencyType()!= null){
			Dictionary dic = null;
			dic  = (Dictionary)creditBaseDao.getById(Dictionary.class, guaranteeProject.getCurrencyType());
			if(dic != null){
				code.setDbbz_v(dic.getItemValue());
			}
		}
		//贷款金额
		if(guaranteeProject.getProjectMoney()!= null){
			BigDecimal projectMoney = guaranteeProject.getProjectMoney();
			String dw = "元整";
			code.setDbdkjexx_v(myFormatter.format(projectMoney).toString()+"元");
			code.setDbdkjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
		}
		//贷款期限dbdkqx
		if(guaranteeProject.getDueTime()!= null){
			String year="";
			double num=guaranteeProject.getDueTime()/12;
			String numString=Double.toString(num);
			String sn=numString.substring(0,numString.indexOf("."));
			int month=guaranteeProject.getDueTime()%12;
			switch(Integer.parseInt(sn)){
			    case 1:
			    	year="壹";
			    	break;
			    case 2:
			    	year="贰";
			    	break;
			    case 3:
			    	year="叁";
			    	break;
			    case 4:
			    	year="肆";
			    	break;
			    case 5:
			    	year="伍";
			    	break;
			    case 6:
			    	year="陆";
			    	break;
			    case 7:
			    	year="柒";
			    	break;
			    case 8:
			    	year="捌";
			    	break;
			    case 9:
			    	year="玖";
			    	break;
			    case 10:
			    	year="拾";
			    	break;
			}
			String m="";
			switch(month){
			    case 1:
			    	m="壹";
			    	break;
			    case 2:
			    	m="贰";
			    	break;
			    case 3:
			    	m="叁";
			    	break;
			    case 4:
			    	m="肆";
			    	break;
			    case 5:
			    	m="伍";
			    	break;
			    case 6:
			    	m="陆";
			    	break;
			    case 7:
			    	m="柒";
			    	break;
			    case 8:
			    	m="捌";
			    	break;
			    case 9:
			    	m="玖";
			    	break;
			    case 10:
			    	m="拾";
			    	break;
			    case 11:
			    	m="拾壹";
			    	break;
			}
			String ym="";
			if(Integer.parseInt(sn)==0){
				ym=m+"个月";
			}else if(Integer.parseInt(sn)!=0 && month==0){
				ym=year+"年";
			}else if(Integer.parseInt(sn)!=0 && month!=0){
				ym=year+"年零"+m+"个月";
			}
			code.setDbdkqx_v(ym);
		}
		//担保起始日期dbqsr
		if(guaranteeProject.getAcceptDate()!= null){
			String startDateStr = guaranteeProject.getAcceptDate().toString().substring(0,guaranteeProject.getAcceptDate().toString().lastIndexOf(" "));
			code.setDbqsr_v(MoneyFormat.getInstance().formatDate(startDateStr));
		}
		//担保截至日期dbjzr
		if(guaranteeProject.getIntentDate()!= null){
			String startDateStr = guaranteeProject.getIntentDate().toString().substring(0,guaranteeProject.getIntentDate().toString().lastIndexOf(" "));
			code.setDbjzr_v(MoneyFormat.getInstance().formatDate(startDateStr));
		}
		//贷款利率
		if(guaranteeProject.getLoanRate()!=null){
			code.setDbdklv_v(guaranteeProject.getLoanRate().doubleValue()+"%");
			code.setDbdklvxx_v(guaranteeProject.getLoanRate().doubleValue()+"%");
			code.setDbdklvdx_v("百分之"+new ChineseUpperCaser(guaranteeProject.getLoanRate().toString()));
		}
		//保费总额
		if(guaranteeProject.getProjectMoney()!= null && guaranteeProject.getPremiumRate()!= null){
			BigDecimal money = guaranteeProject.getProjectMoney().multiply(guaranteeProject.getPremiumRate());
			code.setDbbfzexx_v(myFormatter.format(money)+"元");
			code.setDbbfzedx_v(MoneyFormat.getInstance().hangeToBig(money)+"元");
		}else{
			code.setDbbfzexx_v("0");
			code.setDbbfzedx_v("零元");
		}
		//展期保费费率
		if(guaranteeProject.getStandoverRate()!= null){
			code.setDbzqbffl_v(guaranteeProject.getStandoverRate().doubleValue()+"‰/月");
		}
		//逾期保费费率
		if(guaranteeProject.getOverdueRate()!= null){
			code.setDbyqbffl_v(guaranteeProject.getOverdueRate().doubleValue()+"‰/月");
		}
		//违约金比例
		if(guaranteeProject.getDeditRate()!= null){
			code.setDbwyjbl_v(guaranteeProject.getDeditRate().doubleValue()+"‰/日");
		}
		//保证金比例
		if(guaranteeProject.getEarnestmoney()!= null && guaranteeProject.getProjectMoney()!= null){
			code.setDbbzjjexx_v(guaranteeProject.getEarnestmoney().doubleValue()+"元");
			code.setDbbzjjedx_v(MoneyFormat.getInstance().hangeToBig(guaranteeProject.getEarnestmoney())+"元");
			code.setDbbzjbl_v(guaranteeProject.getEarnestmoney().divide(guaranteeProject.getProjectMoney(),2, BigDecimal.ROUND_HALF_EVEN).doubleValue()+"");
		}
		//资金用途
		if(guaranteeProject.getPurposeType()!= null){
			Dictionary dictionary = null;
			dictionary = (Dictionary)creditBaseDao.getById(Dictionary.class, guaranteeProject.getPurposeType());
			if(dictionary != null){
				code.setDbzjyt_v(dictionary.getItemValue());
			}
		}
		
		//保费费率
		if(guaranteeProject.getPremiumRate()!=null){
			
			code.setDbbfflxx_v(guaranteeProject.getPremiumRate().toString()+"%/年");
			code.setDbbffldx_v("百分之"+new ChineseUpperCaser(guaranteeProject.getPremiumRate().toString())+"/年");
		}
		
		//向客户收取的保证金比例
		if(guaranteeProject.getCustomerEarnestmoneyScale()!=null){
			code.setDbxkhsqdbzjbl_v(guaranteeProject.getCustomerEarnestmoneyScale()+"%");
		}
		if(guaranteeProject.getCustomerEarnestmoneyScale()!=null && guaranteeProject.getEarnestmoney()!=null){
			code.setDbxkhsqdbzjjexx_v(guaranteeProject.getEarnestmoney().multiply(guaranteeProject.getCustomerEarnestmoneyScale().divide(new BigDecimal(100))).toString()+"元");
			code.setDbxkhsqdbzjjedx_v(MoneyFormat.getInstance().hangeToBig(guaranteeProject.getEarnestmoney().multiply(guaranteeProject.getCustomerEarnestmoneyScale().divide(new BigDecimal(100)))).toString()+"元");
		}else{
			code.setDbxkhsqdbzjjexx_v("0");
			code.setDbxkhsqdbzjjedx_v("零元");
		}
		/**
		 * 客户信息
		 * */
		if(guaranteeProject.getOppositeType()!= null){
			if(guaranteeProject.getOppositeType().equals("person_customer")){//个人
				Person person = null;
				person = (Person)creditBaseDao.getById(Person.class, guaranteeProject.getOppositeID().intValue());
				if(person.getName()!=null){
					code.setDbxdsqrmc_v(person.getName());
					code.setDbkhmc_v(person.getName());
				}
				if(person.getCardnumber()!= null){
					code.setDbkhsfzhm_v(person.getCardnumber());
				}
				if(person.getAddress()!= null){
					code.setDbkhjtzz_v(person.getZhusuo());
				}
				if(person.getTelphone()!= null){
					code.setDbkhlxdh_v(person.getTelphone());
				}
				if(person.getFax()!= null){
					code.setDbkhczhm_v(person.getFax());
				}
				if(person.getPostaddress()!=null){
					code.setDbxdsqrdzhzz_v(person.getPostaddress());
				}
				if(person.getCellphone()!=null){
					code.setDbxdsqrlxdh_v(person.getCellphone());
				}
				if(person.getId()!=null){
					List<EnterpriseBank> list=enterpriseBankDao.getBankList(person.getId(), Short.valueOf("0"),Short.valueOf("1"),Short.valueOf("0"));
					if(null!=list && list.size()>0){
						EnterpriseBank bank=list.get(0);
						if(bank.getName()!=null){
							code.setDbdwtrbankname_v(bank.getName());
							code.setDbxdsqrzh_v(bank.getName());
						}
						if(bank.getAccountnum()!=null){
							code.setDbdwtrbanknum_v(bank.getAccountnum());
							code.setDbxdsqraccountnum_v(bank.getAccountnum());
						}
					}
				}
			}else if(guaranteeProject.getOppositeType().equals("company_customer")){//企业
				EnterpriseView enterprise = null;
				enterprise = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, guaranteeProject.getOppositeID().intValue());
				if(enterprise.getEnterprisename() != null){
					code.setDbxdsqrmc_v(enterprise.getEnterprisename());
					code.setDbkhgsmc_v(enterprise.getEnterprisename());
				}
				if(enterprise.getArea()!= null){
					code.setDbkhgsdz_v(enterprise.getArea());
					code.setDbxdsqrdzhzz_v(enterprise.getArea());
				}
				if(enterprise.getLegalperson()!= null){
					code.setDbkhgsfddbr_v(enterprise.getLegalperson());
					code.setDbxdqyfddbr_v(enterprise.getLegalperson());
				}
				if(enterprise.getTelephone()!= null){
					code.setDbkhgslxdh_v(enterprise.getTelephone());
					code.setDbxdsqrlxdh_v(enterprise.getTelephone());
				}
				if(enterprise.getFax()!=null){
					code.setDbkhgsczhm_v(enterprise.getFax());
				}
				if(enterprise.getId()!=null){
					List<EnterpriseBank> list=enterpriseBankDao.getBankList(enterprise.getId(), Short.valueOf("0"), Short.valueOf("0"), Short.valueOf("0"));
					if(null!=list && list.size()>0){
						EnterpriseBank bank=list.get(0);
						if(bank.getName()!=null){
							code.setDbdwtrbankname_v(bank.getName());
							code.setDbxdsqrzh_v(bank.getName());
						}
						if(bank.getAccountnum()!=null){
							code.setDbdwtrbanknum_v(bank.getAccountnum());
							code.setDbxdsqraccountnum_v(bank.getAccountnum());
						}
					}
				}
			}
		}
		
		/**
		 * 我方主体
		 * 
		 * */
		if(guaranteeProject.getMineType().equals("person_ourmain")){//我方主体为个人
			SlPersonMain personMain = null;
			if(guaranteeProject.getMineId()!=0){
				personMain = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, guaranteeProject.getMineId());
				if(personMain != null){
					if(personMain.getName()!=null){
						code.setDbwfzt_v(personMain.getName());
						code.setDbwfztmc_v(personMain.getName());
					}
					if(personMain.getCardnum()!= null){
						code.setDbwfztsfzhm_v(personMain.getCardnum());
					}
					if(personMain.getHome()!= null){
						code.setDbwfztjtzz_v(personMain.getHome());
					}
					if(personMain.getTel()!= null){
						code.setDbwfztlxdh_v(personMain.getTel());
					}
					if(personMain.getTax()!= null){
						code.setDbwfztczhm_v(personMain.getTax());
					}
					if(personMain.getPostalCode()!=null){
						code.setDbwfztyzbm_v(personMain.getPostalCode());
						code.setDbpersonpostcode_v(personMain.getPostalCode());
					}
				}
			}
		}else if(guaranteeProject.getMineType().equals("company_ourmain")){//我方主体为企业
			SlCompanyMain companyMain = null;
			if(guaranteeProject.getMineId()!=0){
				companyMain = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, guaranteeProject.getMineId());
				if(companyMain != null){
					if(companyMain.getCorName()!= null){
						code.setDbwfzt_v(companyMain.getCorName());
						code.setDbwfztgsmc_v(companyMain.getCorName());
					}
					if(companyMain.getSjjyAddress()!= null){
						code.setDbwfztgsdz_v(companyMain.getSjjyAddress());
					}
					if(companyMain.getLawName()!= null){
						code.setDbwfztgsfddbr_v(companyMain.getLawName());
					}
					if(companyMain.getTel()!= null){
						code.setDbwfztgslxdh_v(companyMain.getTel());
					}
					if(companyMain.getTax()!= null){
						code.setDbwfztgsczhm_v(companyMain.getTax());
					}
					if(companyMain.getPostalCode()!=null){
						code.setDbwfztgsyzbm_v(companyMain.getPostalCode());
						code.setDbpostcode_v(companyMain.getPostalCode());
					}
				}
			}
		}
		/**
		 * 反担保合同相关
		 * 所有人类型：法人 602，自然人 603
		 * 担保类型：抵押担保604，质押担保605，信用担保606
		 * */
		if(conId != 0){
			if(null!=contract && contract.getMortgageId()!= 0 && null!=contractType && ("thirdContract".equals(contractType) || "baozContract".equals(contractType))){
				VProcreditDictionaryGuarantee mortgage = null;
				mortgage = (VProcreditDictionaryGuarantee)creditBaseDao.getById(VProcreditDictionaryGuarantee.class, contract.getMortgageId());
				EnterpriseView e = null ;
				VPersonDic p = null ;
				if(mortgage!=null){
					if(null!=mortgage.getEnterprisename()){
					    code.setDbfdbgsmc_v(mortgage.getEnterprisename());
					}
					if(null!=mortgage.getMortgagenametypeid()){
						code.setDbfdbwlx_v(mortgage.getMortgagenametypeid());
					}
					if(null!=mortgage.getAssuretypeidValue()){
						code.setDblx_v(mortgage.getAssuretypeidValue());
					}
					if(null!=mortgage.getAssuretypeid() && mortgage.getAssuretypeid()==606){
						if(null!=mortgage.getPersonTypeId() && mortgage.getPersonTypeId()==602){
							if(null!=mortgage.getAssureofname()){
								List<EnterpriseBank> list=enterpriseBankDao.getBankList(mortgage.getAssureofname(), Short.valueOf("0"), Short.valueOf("0"), Short.valueOf("0"));
								if(null!=list && list.size()>0){
									EnterpriseBank bank=list.get(0);
									if(bank.getName()!=null){
										code.setDbcompanybankname_v(bank.getName());//保证公司账户
									}
									if(bank.getAccountnum()!=null){
										code.setDbcompanybanknum_v(bank.getAccountnum());//保证公司账号
									}
								}
							}
						}else{
							if(null!=mortgage.getAssureofname()){
								Spouse spouse=spouseService.getByPersonId(mortgage.getAssureofname());
								if(null!=spouse){
									if(spouse.getName()!=null){
										code.setDbbzrpomc_v(spouse.getName());
									}
									if(spouse.getLinkTel()!=null){
										code.setDbbzrpolxdh_v(spouse.getLinkTel());
									}
									if(spouse.getCardtype()!=null){
										Dictionary dic=(Dictionary) this.creditBaseDao.getById(Dictionary.class, spouse.getCardtype().longValue());
										if(null!=dic){
											code.setDbbzrpozjlx_v(dic.getItemValue());
										}
									}
									if(spouse.getCardnumber()!=null){
										code.setDbbzrpozjhm_v(spouse.getCardnumber());
									}
								}
							}
						}
					}
				}else{
					if(null!=mortgage.getPersonTypeId() && mortgage.getPersonTypeId()==602){
						if(mortgage.getAssureofname()!=null){
							List<EnterpriseBank> list=enterpriseBankDao.getBankList(mortgage.getAssureofname(), Short.valueOf("0"), Short.valueOf("0"), Short.valueOf("0"));
							if(null!=list && list.size()>0){
								EnterpriseBank bank=list.get(0);
								if(bank.getName()!=null){
									code.setDbdzycompanybankname_v(bank.getName());
								}
								if(bank.getAccountnum()!=null){
									code.setDbdzycompanybanknum_v(bank.getAccountnum());
								}
							}
						}
					}
				}
				if(null!=mortgage && null!=mortgage.getPersonTypeId() && mortgage.getPersonTypeId() == 603){//自然人
					p = (VPersonDic)creditBaseDao.getById(VPersonDic.class, mortgage.getAssureofname());
					if(null != p.getName()){
						code.setDbbzrmc_v(p.getName());
						code.setDbdyrmc_v(p.getName());//抵押人名称
						code.setDbczrxm_v(p.getName());
					}
					if(null != p.getCardnumber()){
						code.setDbbzrsfzhm_v(p.getCardnumber());
						code.setDbdyrsfzhm_v(p.getCardnumber());//抵押人身份证号码
						code.setDbczrsfzhm_v(p.getCardnumber());
					}
					if(null != p.getPostaddress()){
						code.setDbbzrjtzz_v(p.getPostaddress());
						code.setDbdyrjtzz_v(p.getPostaddress());//抵押人家庭住址
						code.setDbczrjtzz_v(p.getPostaddress());
					}
					/*if(null != p.getZhusuo()){
						code.setDbbzrjtzz_v(p.getZhusuo());
						code.setDbdyrjtzz_v(p.getZhusuo());//抵押人家庭住址
						code.setDbczrjtzz_v(p.getZhusuo());
					}*/
					if(null != p.getCellphone()){
						code.setDbbzrlxdh_v(p.getCellphone());
						code.setDbdyrlxdh_v(p.getCellphone());//抵押人联系电话
						code.setDbczrlxdh_v(p.getCellphone());
					}/*else if(null == p.getTelphone()&& null!= p.getCellphone()){
						code.setDbbzrlxdh_v(p.getCellphone());
						code.setDbdyrlxdh_v(p.getCellphone());
						code.setDbczrlxdh_v(p.getCellphone());
					}else if(null != p.getTelphone()&& null== p.getCellphone()){
						code.setDbbzrlxdh_v(p.getTelphone());
						code.setDbdyrlxdh_v(p.getTelphone());
						code.setDbczrlxdh_v(p.getTelphone());
					}*/
					if(null != p.getFax()){
						code.setDbbzrczhm_v(p.getFax());
						code.setDbdyrczhm_v(p.getFax());//抵押人传真号码
						code.setDbczrczhm_v(p.getFax());
					}
					if(null!=p.getPostcode()){
						code.setDbczryzbm_v(p.getPostcode());
						code.setDbbzryzbm_v(p.getPostcode());
						code.setDbdyryb_v(p.getPostcode());
					}
					if(null!=p.getPostaddress()){
						code.setDbczrtxdz_v(p.getPostaddress());
						code.setDbbzrtxdz_v(p.getPostaddress());
						code.setDbdyrtxdz_v(p.getPostaddress());
					}
				}else if(null!=mortgage && null!=mortgage.getPersonTypeId() && mortgage.getPersonTypeId() == 602){//法人
					e = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, mortgage.getAssureofname());
					if(e.getEnterprisename()!= null){
						code.setDbbzgsmc_v(e.getEnterprisename());
						code.setDbdygsmc_v(e.getEnterprisename());//抵押方公司名称
						code.setDbczgsmc_v(e.getEnterprisename());
					}
					if(e.getArea()!= null){
						code.setDbbzgsdz_v(e.getArea());
						
					}
					/*if(e.getFactaddress()!=null){
						code.setDbdygsdz_v(e.getFactaddress());//抵押方公司地址
						code.setDbczgsdz_v(e.getFactaddress());
					}*/
					if(e.getArea()!=null){
						code.setDbdygsdz_v(e.getArea());//抵押方公司地址
						code.setDbczgsdz_v(e.getArea());
					}
					if(e.getTelephone()!= null){
						code.setDbbzgslxdh_v(e.getTelephone());
						code.setDbdygslxdh_v(e.getTelephone());//抵押方公司联系电话
						code.setDbczgsdh_v(e.getTelephone());
					}
					if(e.getFax() != null){
						code.setDbbzgsczhm_v(e.getFax());
						code.setDbdygsczhm_v(e.getFax());//抵押方公司传真号码
						code.setDbczgsczhm_v(e.getFax());
					}
					if(e.getLegalperson()!= null){
						code.setDbbzgsfddbr_v(e.getLegalperson());
						code.setDbdygsfddbr_v(e.getLegalperson());//抵押方公司法定代表
						code.setDbczgsfddbr_v(e.getLegalperson());
					}
					if(e.getArea()!=null){
						code.setDbdygstxdz_v(e.getArea());//抵押方公司通信地址
						code.setDbczgstxdz_v(e.getArea());
					}
					if(null!=e.getPostcoding()){
						code.setDbdygsyb_v(e.getPostcoding());//抵押方公司邮政编码
						code.setDbbzgsyzbm_v(e.getPostcoding());
						code.setDbczgsyzbm_v(e.getPostcoding());
					}
					if(null!=e.getReceiveMail()){
						code.setDbdygssjr_v(e.getReceiveMail());//抵押方公司收件人
						code.setDbbzgssjr_v(e.getReceiveMail());
						code.setDbczgssjr_v(e.getReceiveMail());
					}
				}
				//目标公司名称
				if(null!=mortgage && mortgage.getEnterprisename()!= null){
					code.setDbgqmbgsmc_v(mortgage.getEnterprisename());
				}
				/*if(null!=mortgage){
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==1){
						VProjMortCar vcm=(VProjMortCar) creditBaseDao.getById(VProjMortCar.class, mortgage.getDywId());
						code.setDbdyrxq_v("名下"+vcm.getManufacturerValue()+"车辆抵押（发动机号码为："+vcm.getEngineNo()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==5){
						ProcreditMortgageMachineinfo pm=(ProcreditMortgageMachineinfo) creditBaseDao.getById(ProcreditMortgageMachineinfo.class, mortgage.getDywId());
					    code.setDbdyrxq_v("名下"+pm.getMachinename()+"设备抵押(设备型号为："+pm.getMachinetype()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==6){
						ProcreditMortgageProduct pm=(ProcreditMortgageProduct) creditBaseDao.getById(ProcreditMortgageProduct.class, mortgage.getDywId());
					    code.setDbdyrxq_v("名下"+pm.getName()+"存货商品(品牌："+pm.getBrand()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==7){
						ProcreditMortgageHouse pm=(ProcreditMortgageHouse) creditBaseDao.getById(ProcreditMortgageHouse.class, mortgage.getDywId());
						code.setDbdyrxq_v("名下位于"+pm.getHouseaddress()+pm.getBuildacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==10){
						ProcreditMortgageBusiness pm=(ProcreditMortgageBusiness) creditBaseDao.getById(ProcreditMortgageBusiness.class, mortgage.getDywId());
						code.setDbdyrxq_v("名下位于"+pm.getAddress()+pm.getAcreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==11){
						ProcreditMortgageBusinessandlive pm=(ProcreditMortgageBusinessandlive) creditBaseDao.getById(ProcreditMortgageBusinessandlive.class, mortgage.getDywId());
						code.setDbdyrxq_v("名下位于"+pm.getAddress()+pm.getAnticipateacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==12){
						ProcreditMortgageEducation pm=(ProcreditMortgageEducation) creditBaseDao.getById(ProcreditMortgageEducation.class, mortgage.getDywId());
						code.setDbdyrxq_v("名下位于"+pm.getAddress()+pm.getAcreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==13){
						ProcreditMortgageIndustry pm=(ProcreditMortgageIndustry) creditBaseDao.getById(ProcreditMortgageIndustry.class, mortgage.getDywId());
						code.setDbdyrxq_v("名下位于"+pm.getAddress()+pm.getOccupyacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
				}*/
				//反担保物详情
				
				
				//股权.%
				Double stockownershippercent = 0.0;
				if(null!=mortgage && mortgage.getId()!=null){
					String hql = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
					List list = creditBaseDao.queryHql(hql);
					if(list!= null && list.size()>0){
						VProjMortStockOwnerShip vs = (VProjMortStockOwnerShip)list.get(0);
						if(null!=vs && vs.getStockownershippercent()==null){
							stockownershippercent = 0.0;
						}else{
							stockownershippercent = vs.getStockownershippercent();
						}
						code.setDbgqbl_v(stockownershippercent.toString()+"%");
						//目标公司名称
						code.setDbgqmbgsmc_v(mortgage.getEnterprisename());
						Object obj = null;
						obj = creditBaseDao.getById(Enterprise.class, vs.getCorporationname());
						if(obj != null){
							Enterprise en = (Enterprise)obj;
							//出质股权数额小写
							Double d = 0.0;
							if(en.getRegistermoney()!=null){
								d = en.getRegistermoney()*stockownershippercent/100;
							}
							
							code.setDbczgqse_v(d.toString()+"万元");
						}
						
					}
					
				}
				//质押担保债权本金数额 
				if(mortgage.getId()!= null){
					String hql = "from VProcreditDictionaryGuarantee AS d where d.projid =? and d.businessType =?";
					Object [] obj = {Long.parseLong(proId),guaranteeProject.getBusinessType()};
					List list = creditBaseDao.queryHql(hql,obj);
					Double shippercent = 0.0;
					int enterpId = 0;
					if(list!= null && list.size()>0 && list.size()>1){
						for(int i =0;i<list.size();i++){
							String hqlship = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
							List listship = creditBaseDao.queryHql(hqlship);
							if(listship!= null && listship.size()>0){
								VProjMortStockOwnerShip vs = (VProjMortStockOwnerShip)listship.get(0);
								Double shipt = 0.0;
								if(vs.getStockownershippercent()!= null){
									shipt = vs.getStockownershippercent();
								}
								shippercent = shipt;
								enterpId = vs.getCorporationname();
							}
							for(int j= 1;j<list.size();j++){
								String hqlship2 = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
								List listship2 = creditBaseDao.queryHql(hqlship2);
								if(listship2!= null && listship2.size()>0){
									VProjMortStockOwnerShip vs2 = (VProjMortStockOwnerShip)listship2.get(0);
									if(enterpId == vs2.getCorporationname()){
										Double shipt = 0.0;
										if(vs2.getStockownershippercent()!= null){
											shipt=vs2.getStockownershippercent();
										}
										shippercent += (shippercent + shipt);
									}
								}
							}
							
						}
					}else{
						shippercent = Double.valueOf("1");
					}
					if(shippercent.compareTo(0.0)>0){
						Double je = stockownershippercent/shippercent*100;
						code.setDbzzqbedbl_v(je+"%");
					}
				}
				//债权本金数额（万元为单位）
				if(null!=mortgage && mortgage.getId()!= null){
					String hql = "from VProcreditDictionaryGuarantee AS d where d.projid =? and d.businessType =?";
					Object [] obj = {Long.parseLong(proId),guaranteeProject.getBusinessType()};
					List list = creditBaseDao.queryHql(hql,obj);
					Double shippercent = 0.0;
					int enterpId = 0;
					if(list!= null && list.size()>0 && list.size()>1){
						for(int i =0;i<list.size();i++){
							String hqlship = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
							List listship = creditBaseDao.queryHql(hqlship);
							if(listship!= null && listship.size()>0){
								VProjMortStockOwnerShip vs = (VProjMortStockOwnerShip)listship.get(0);
								Double shipt = 0.0;
								if(vs.getStockownershippercent()!= null){
									shipt = vs.getStockownershippercent();
								}
								shippercent = shipt;
								enterpId = vs.getCorporationname();
							}
							for(int j= 1;j<list.size();j++){
								String hqlship2 = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
								List listship2 = creditBaseDao.queryHql(hqlship2);
								if(listship2!= null && listship2.size()>0){
									VProjMortStockOwnerShip vs2 = (VProjMortStockOwnerShip)listship2.get(0);
									if(enterpId == vs2.getCorporationname()){
										Double shipt = 0.0;
										if(vs2.getStockownershippercent()!= null){
											shipt=vs2.getStockownershippercent();
										}
										shippercent += (shippercent + shipt);
									}
								}
							}
							
						}
					}else{
						shippercent = Double.valueOf("1");
					}
					if(shippercent.compareTo(0.0)>0){
						Double je = guaranteeProject.getProjectMoney().doubleValue()*(stockownershippercent/shippercent)/100;
						code.setDbzqbjse_v(myFormatter.format(je/10000)+"万元");
					}
				}
			}
		}
		
		
		return code;
	}
	//获得所有融资的系统要素
	public FinancingElementCode getFinancingElementBySystem(String proId , int conId ,int tempId, String contractType,String rnum) throws Exception{
		DecimalFormat myFormatter = new DecimalFormat("####.#");
		FinancingElementCode elementCode = new FinancingElementCode();
		FlFinancingProject financingProject = (FlFinancingProject)creditBaseDao.getById(FlFinancingProject.class, Long.parseLong(proId));
		ProcreditContract contract = (ProcreditContract)creditBaseDao.getById(ProcreditContract.class, conId);
		/*
		 * 融资合同编号
		 * */
		//借款合同编号
		if(financingProject.getProjectNumber() != null){
			/*String projectNumber = financingProject.getProjectNumber();//项目编号
			int count = 0;
			if(!StringUtil.isNumeric(rnum)){
				rnum = "0";
			}
			count = Integer.parseInt(rnum);
			if(count <10){*/
				elementCode.setRzjkhtbh_v(contract.getContractNumber());//借款合同编号
				elementCode.setNewjkhtbh_v(contract.getContractNumber());
				elementCode.setRzcwxybh_v(contract.getContractNumber());//融资财务协议编号
				elementCode.setRzbzhtbh_v(contract.getContractNumber());//融资保证合同编号
				elementCode.setRzdyhtbh_v(contract.getContractNumber());//融资抵押合同编号
				elementCode.setRzzyhtbh_v(contract.getContractNumber());
			/*}else{
				elementCode.setRzjkhtbh_v(contract.getContractNumber());//借款合同编号
				elementCode.setNewjkhtbh_v(contract.getContractNumber());
				elementCode.setRzcwxybh_v(contract.getContractNumber());//融资财务协议编号
				elementCode.setRzbzhtbh_v(contract.getContractNumber());//融资保证合同编号
				elementCode.setRzdyhtbh_v(contract.getContractNumber());//融资抵押合同编号
				elementCode.setRzzyhtbh_v(contract.getContractNumber());
			}*/
		}
		//借款用途
		if(financingProject.getPurposeType()!= null){
			Dictionary dictionary = null;
			dictionary = (Dictionary)creditBaseDao.getById(Dictionary.class, financingProject.getPurposeType());
			if(dictionary != null){
				elementCode.setRzjkyt_v(dictionary.getItemValue());
			}
		}
		//借款金额大小写
		if(financingProject.getProjectMoney()!= null){
			BigDecimal projectMoney = financingProject.getProjectMoney();
			String dw = "元整";
			/*if(projectMoney > 10000.0){
				dw = "万元";
			}*/
			elementCode.setRzjkjexx_v(myFormatter.format(projectMoney).toString()+"元");
			elementCode.setRzjkjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
			elementCode.setNewjkjexx_v(myFormatter.format(projectMoney).toString()+"元");
			elementCode.setNewjkjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
		}
		//币种
		if(financingProject.getCurrency()!= null){
			Dictionary dictionary = null;
			dictionary = (Dictionary)creditBaseDao.getById(Dictionary.class, financingProject.getCurrency());
			if(dictionary != null){
				elementCode.setRzbz_v(dictionary.getItemValue());
				elementCode.setNewbz_v(dictionary.getItemValue());
			}
		}
		//逾期费率
		if(financingProject.getOverdueRate()!=null){
			elementCode.setRzyqfl_v(financingProject.getOverdueRate()+"%/日");
		}
		//违约金
		if(financingProject.getBreachRate()!=null){
			elementCode.setRzwyjbl_v(financingProject.getBreachRate()+"%");
		}
		//借款利率
		if(financingProject.getAccrual() != null){
			elementCode.setRzjkll_v(financingProject.getAccrual().doubleValue()+"%");
		}
		//借款起始日
		if(financingProject.getStartDate()!= null){
			String startDateStr = financingProject.getStartDate().toString().substring(0,financingProject.getStartDate().toString().lastIndexOf(" "));
			elementCode.setRzjkqsr_v(MoneyFormat.getInstance().formatDate(startDateStr));
		}
		//借款截至日
		if(financingProject.getIntentDate()!= null){
			String intentDateStr = financingProject.getIntentDate().toString().substring(0,financingProject.getIntentDate().toString().lastIndexOf(" "));
			elementCode.setRzjkjzr_v(MoneyFormat.getInstance().formatDate(intentDateStr));
		}
		//利息支付方式
		if(financingProject.getIsPreposePayAccrual() != null){
			if(financingProject.getIsPreposePayAccrual() == 1){//前置
				if(financingProject.getPayaccrualType().equals("oneTimePay")){//一次性付息
					elementCode.setRzlxzffs_v("利息一次性付清，支付日期：年  月  日（或贷款实际发放日）。");
				}else if(financingProject.getPayaccrualType().equals("monthPay")){
					elementCode.setRzlxzffs_v("利息每月提前支付 ，贷款发放之日支付第一个月利息，利息按月支付，每月  日前，支付到贷款方指定账号。");
				}else if(financingProject.getPayaccrualType().equals("calendarMonthPay")){
					elementCode.setRzlxzffs_v("利息每月提前支付 ，贷款发放之日支付第一个月利息，首次付息人民币       元整（￥：       元），付息日期：年  月  日  ；剩余利息按月支付，每月1日前，支付到贷款方指定账号。");
				}
			}else{
				if(financingProject.getPayaccrualType().equals("oneTimePay")){//一次性付息
					elementCode.setRzlxzffs_v("利息一次性付清，支付日期：年  月  日。");
				}else if(financingProject.getPayaccrualType().equals("monthPay")){
					elementCode.setRzlxzffs_v("利息按月支付，每月  日前，支付到贷款方指定账号。");
				}else if(financingProject.getPayaccrualType().equals("calendarMonthPay")){
					elementCode.setRzlxzffs_v("首次付息人民币       元整（￥：       元），付息日期：年  月  日  ；剩余利息按月支付，每月1日前，支付到贷款方指定账号。");
				}
			}
		}
		//服务费用总额（大写）
		if(financingProject.getFinanceServiceOfRate()!= null && financingProject.getProjectMoney()!= null){
			//Double serviceMoney = (financingProject.getFinanceServiceOfRate().divide(BigDecimal.valueOf(100)).multiply(financingProject.getProjectMoney())).doubleValue();
			if(financingProject.getFinanceServiceOfRate().compareTo(BigDecimal.ZERO)!=0 ){
				String fundType = "";
				if(financingProject.getBusinessType().equals("SmallLoan")){
					fundType = "serviceMoney";
				}else{
					fundType = "financingserviceMoney";
				}
				BigDecimal serviceMoney = new BigDecimal("0");
				List listMoney = null;
				Object [] obje = {financingProject.getProjectId(),financingProject.getBusinessType(),fundType};
				String hqlm ="from SlFundIntent where projectId = ? and businessType =? and fundType = ?";
				listMoney = creditBaseDao.queryHql(hqlm,obje);
				if(listMoney!= null && listMoney.size()>0){
					for(int i=0;i<listMoney.size();i++){
						SlFundIntent slFundIntent = (SlFundIntent)listMoney.get(i);
						serviceMoney = serviceMoney.add(slFundIntent.getNotMoney());
					}
				}
				//服务费用总额（大写）
				elementCode.setRzfwfyzedx_v(MoneyFormat.getInstance().hangeToBig(serviceMoney)+"元");
				//多笔支付款项内容(服务费列表)
				
				List<SlFundIntent> list = null;
				String hql = "from SlFundIntent AS f where f.projectId =? and f.fundType =? and f.businessType =?";
				Object [] param ={Long.parseLong(proId),fundType,financingProject.getBusinessType()};
				list = creditBaseDao.queryHql(hql, param);
				if(list != null && list.size()>0){
					StringBuffer dbzfkxnrBuffer = new StringBuffer();
					String dbzfkxnr = "";
					for(int i = 0;i < list.size(); i++){
						SlFundIntent fi = (SlFundIntent)list.get(i);
						String intentDateStr = fi.getIntentDate().toString().substring(0,fi.getIntentDate().toString().lastIndexOf(" "));
						if(fundType == "serviceMoney" || "serviceMoney".equals(fundType)){
							dbzfkxnr = "费用支付时间："+MoneyFormat.getInstance().formatDate(intentDateStr)+"      "+"支付金额："+myFormatter.format(fi.getIncomeMoney())+"元\r\n";
						}else{
							dbzfkxnr = "费用支付时间："+MoneyFormat.getInstance().formatDate(intentDateStr)+"      "+"支付金额："+myFormatter.format(fi.getPayMoney())+"元\r\n";
						}
						dbzfkxnrBuffer.append(dbzfkxnr);
						
					}
					elementCode.setRzdbzfkxnr_v(dbzfkxnrBuffer.toString());
				}
				
			}else{
				elementCode.setRzfwfyzedx_v("零元");
			}
			
			
		}
		
		/*
		 * 客户类型-贷款方，债权人，抵押权人，质权人
		 * */
		if(financingProject.getOppositeType().equals("person_customer")){
			Person person = null;
			person = (Person)creditBaseDao.getById(Person.class, financingProject.getOppositeID().intValue());
			if(person.getName()!=null){
				elementCode.setRzdkfmc_v(person.getName());
				elementCode.setRzzqrmc_v(person.getName());
				elementCode.setNewdkr_v(person.getName());
				elementCode.setRzdyqrmc_v(person.getName());
				elementCode.setRzzhqrmc_v(person.getName());
			}
			if(person.getCardnumber() !=null){
				elementCode.setRzdkfsfzhm_v(person.getCardnumber());
				elementCode.setRzzqrsfzhm_v(person.getCardnumber());
				elementCode.setRzdyqrsfzhm_v(person.getCardnumber());
				elementCode.setRzzhqrsfzhm_v(person.getCardnumber());
			}
			if(person.getAddress() != null){
				elementCode.setRzdkfzsdz_v(person.getAddress());
				elementCode.setRzzqrzs_v(person.getAddress());
				elementCode.setRzdyqrzs_v(person.getAddress());
				elementCode.setRzzhqrzs_v(person.getAddress());
			}
			if(person.getTelphone() != null){
				elementCode.setRzdkflxdh_v(person.getTelphone());
				elementCode.setRzzqrlxdh_v(person.getTelphone());
				elementCode.setRzdyqrlxdh_v(person.getTelphone());
				elementCode.setRzzhqrlxdh_v(person.getTelphone());
			}
			if(person.getPostcode() != null){
				elementCode.setRzdkfyzbm_v(person.getPostcode());
				elementCode.setRzzqryzbm_v(person.getPostcode());
				elementCode.setRzdyqryzbm_v(person.getPostcode());
				elementCode.setRzzhqryzbm_v(person.getPostaddress());
			}
			if(person.getPostaddress() != null){
				elementCode.setRzdkftxdz_v(person.getPostaddress());
				elementCode.setRzzqrtxdz_v(person.getPostaddress());
				elementCode.setRzdyqrtxdz_v(person.getPostaddress());
				elementCode.setRzzhqrtxdz_v(person.getPostaddress());
			}
			if(person.getFax()!= null){
				elementCode.setRzzqrczhm_v(person.getFax());
				elementCode.setRzdyqrczhm_v(person.getFax());
				elementCode.setRzzhqrczhm_v(person.getFax());
			}
		}else if(financingProject.getOppositeType().equals("company_customer")){
			EnterpriseView enterprise = null;
			enterprise = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, financingProject.getOppositeID().intValue());
			if(enterprise.getEnterprisename() != null){
				elementCode.setRzdkfqymc_v(enterprise.getEnterprisename());
				elementCode.setRzzqgsmc_v(enterprise.getEnterprisename());
				elementCode.setNewdkr_v(enterprise.getEnterprisename());
				elementCode.setRzdyqrgsmc_v(enterprise.getEnterprisename());
				elementCode.setRzzqrgsmc_v(enterprise.getEnterprisename());
			}
			if(enterprise.getCciaa()!= null){
				elementCode.setRzdkfqyzjhm_v(enterprise.getCciaa());
				elementCode.setRzzqgsyyzzhm_v(enterprise.getCciaa());
				elementCode.setRzdyqryyzzhm_v(enterprise.getCciaa());
				elementCode.setRzzqrgsyyzzhm_v(enterprise.getCciaa());
			}
			if(enterprise.getAddress() != null){
				elementCode.setRzdkfgsdz_v(enterprise.getAddress());
				elementCode.setRzqqgsdz_v(enterprise.getAddress());
				elementCode.setRzdyqrgsdz_v(enterprise.getAddress());
				elementCode.setRzzqrgsdz_v(enterprise.getAddress());
			}
			if(enterprise.getTelephone()!= null){
				elementCode.setRzdkfqylxdh_v(enterprise.getTelephone());
				elementCode.setRzzqgslxdh_v(enterprise.getTelephone());
				elementCode.setRzdyqrgslxdh_v(enterprise.getTelephone());
				elementCode.setRzzqrgslxdh_v(enterprise.getTelephone());
			}
			if(enterprise.getPostcoding()!= null){
				elementCode.setRzdkfqyyzbm_v(enterprise.getPostcoding());
				elementCode.setRzzqgsyzbm_v(enterprise.getPostcoding());
				elementCode.setRzdyqrgsyzbm_v(enterprise.getPostcoding());
				elementCode.setRzzqrgsyzbm_v(enterprise.getPostcoding());
			}
			if(enterprise.getArea()!=null){
				elementCode.setRzdkfqytxdz_v(enterprise.getArea());
				elementCode.setRzzqgstxdz_v(enterprise.getArea());
				elementCode.setRzdyqrgstxdz_v(enterprise.getArea());
				elementCode.setRzzqrgstxdz_v(enterprise.getArea());
			}
			if(enterprise.getLegalperson()!= null){
				elementCode.setRzdkffddbr_v(enterprise.getLegalperson());
			}
			if(enterprise.getFax()!= null){
				elementCode.setRzzqrczhm_v(enterprise.getFax());
				elementCode.setRzzqgsczhm_v(enterprise.getFax());
				elementCode.setRzdyqrgsczhm_v(enterprise.getFax());
				elementCode.setRzzqrgsczhm_v(enterprise.getFax());
			}
		}
		/*
		 * 我方主体-借款方，债务人
		 * */
		if(financingProject.getMineType().equals("person_ourmain")){//我方主体为个人
			SlPersonMain personMain = null;
			if(financingProject.getMineId()!=0){
				personMain = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, financingProject.getMineId());
				if(personMain != null){
					if(personMain.getName()!=null){
						elementCode.setRzjkfmc_v(personMain.getName());
						elementCode.setRzzwr_v(personMain.getName());
						elementCode.setNewjkr_v(personMain.getName());
					}
					if(personMain.getCardnum()!= null){
						elementCode.setRzjkfsfzhm_v(personMain.getCardnum());
					}
					if(personMain.getHome() !=null){
						elementCode.setRzjkfzsdz_v(personMain.getHome());
					}
					if(personMain.getPostalCode() != null){
						elementCode.setRzjkfyzbm_v(personMain.getPostalCode());
					}
					if(personMain.getAddress()!= null){
						elementCode.setRzjkftxdz_v(personMain.getAddress());
					}
				}
			}
		}else if(financingProject.getMineType().equals("company_ourmain")){//我方主体为企业
			SlCompanyMain companyMain = null;
			if(financingProject.getMineId()!=0){
				companyMain = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, financingProject.getMineId());
				if(companyMain != null){
					if(companyMain.getCorName()!= null){
						elementCode.setRzjkfqymc_v(companyMain.getCorName());
						elementCode.setRzzwr_v(companyMain.getCorName());
						elementCode.setNewjkr_v(companyMain.getCorName());
					}
					if(companyMain.getBusinessCode()!=null){
						elementCode.setRzjkfqyzjhm_v(companyMain.getBusinessCode());
					}
					if(companyMain.getSjjyAddress() != null){
						elementCode.setRzjkfgsdz_v(companyMain.getSjjyAddress());
					}
					if(companyMain.getTel() != null){
						elementCode.setRzjkfqylxdh_v(companyMain.getTel());
					}
					if(companyMain.getPostalCode()!= null){
						elementCode.setRzjkfqyyzbm_v(companyMain.getPostalCode());
					}
					if(companyMain.getMessageAddress()!= null){
						elementCode.setRzjkfqytxdz_v(companyMain.getMessageAddress());
					}
					if(companyMain.getLawName()!= null){
						elementCode.setRzjkffddbr_v(companyMain.getLawName());
					}
				}
			}
		}
		/*
		 * 担保措施相关
		 * 所有人类型：法人 602，自然人 603
		 * 担保类型：抵押担保604，质押担保605，信用担保606 
		 * */
		if(conId != 0){/*
			if(contract.getMortgageId()!= 0 && "thirdContract".equals(contractType)){
				VProcreditDictionaryFinance mortgage = null;
				//获取融资反担保的id
				SlMortgageFinancing slmortgage =null;
				slmortgage =(SlMortgageFinancing)creditBaseDao.getById(SlMortgageFinancing.class, contract.getMortgageId().longValue());
				VProcreditMortgageFinance vmortgagef = null;
				vmortgagef = (VProcreditMortgageFinance)creditBaseDao.getById(VProcreditMortgageFinance.class, contract.getMortgageId().longValue());
				if(vmortgagef != null){
					//目标公司名称
					if(vmortgagef.getMortgageId()!= null){
						List list = null;
						String hql ="from VProjMortStockFinance AS sf where sf.mortgageid="+vmortgagef.getMortgageId();
						list = creditBaseDao.queryHql(hql);
						if(list!= null && list.size()>0){
							VProjMortStockFinance vmsf = null;
							vmsf =(VProjMortStockFinance)list.get(0);
							if(vmsf != null){
								if(vmsf.getEnterprisename()!= null){
									elementCode.setNewmbgsmc_v(vmsf.getEnterprisename());
								}
							}
						}
					}
					if(vmortgagef.getAssureofnameEnterOrPerson()!= null){
						elementCode.setNewkhmc_v(vmortgagef.getAssureofnameEnterOrPerson());
					}
				}
				if(slmortgage!= null){
					mortgage = (VProcreditDictionaryFinance)creditBaseDao.getById(VProcreditDictionaryFinance.class, slmortgage.getMortId().intValue());
					EnterpriseView e = null ;
					VPersonDic p = null ;
					
					if(mortgage.getPersonTypeId() == 603){//自然人
						p = (VPersonDic)creditBaseDao.getById(VPersonDic.class, mortgage.getAssureofname());
						elementCode.setNewkhmc_v(p.getName());
					}else if(mortgage.getPersonTypeId() == 602){//法人
						e = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, mortgage.getAssureofname());
						elementCode.setNewkhmc_v(e.getEnterprisename());
					}
					//产权人，房地产地点，建筑面积，证件号码
					if(mortgage.getId()!= null){
						StringBuffer fdbwxq = new StringBuffer();
						if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==10){//商业用地
							String hql = "from ProcreditMortgageBusiness where mortgageid="+mortgage.getId();
							List list = creditBaseDao.queryHql(hql);
							if(list != null && list.size()>0){
								ProcreditMortgageBusiness pmb = (ProcreditMortgageBusiness)list.get(0);
								if(pmb.getPropertyperson()!= null){
									elementCode.setNewcqr_v(pmb.getPropertyperson());
								}
								if(pmb.getAddress()!= null){
									elementCode.setNewfdcdd_v(pmb.getAddress());
								}
								if(pmb.getAcreage()!= null){
									elementCode.setNewjzmj_v(pmb.getAcreage().toString()+"㎡");
								}
								if(pmb.getCertificatenumber()!= null){
									elementCode.setNewzjhm_v(pmb.getCertificatenumber());
								}
								if(pmb.getCertificatenumber()== null){
									pmb.setCertificatenumber(" ");
								}
								//反担保物详情
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
							}
						}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==11){//商住用地
							String hql1 = "from ProcreditMortgageBusinessandlive where mortgageid="+mortgage.getId();
							List list1 = creditBaseDao.queryHql(hql1);
							if(list1!= null && list1.size()>0){
								ProcreditMortgageBusinessandlive pmb = (ProcreditMortgageBusinessandlive)list1.get(0);
								if(pmb.getPropertyperson()!= null){
									elementCode.setNewcqr_v(pmb.getPropertyperson());
								}
								if(pmb.getAddress()!= null){
									elementCode.setNewfdcdd_v(pmb.getAddress());
								}
								if(pmb.getAcreage()!= null){
									elementCode.setNewjzmj_v(pmb.getAcreage().toString()+"㎡");
								}
								if(pmb.getCertificatenumber()!= null){
									elementCode.setNewzjhm_v(pmb.getCertificatenumber());
								}
								if(pmb.getCertificatenumber()== null){
									pmb.setCertificatenumber(" ");
								}
								//反担保物详情
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
							}
						}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==12){//教育用地
							String hql2 = "from ProcreditMortgageEducation where mortgageid="+mortgage.getId();
							List list2 = creditBaseDao.queryHql(hql2);
							if(list2 != null && list2.size()>0){
								ProcreditMortgageEducation pmb = (ProcreditMortgageEducation)list2.get(0);
								if(pmb.getPropertyperson()!= null){
									elementCode.setNewcqr_v(pmb.getPropertyperson());
								}
								if(pmb.getAddress()!= null){
									elementCode.setNewfdcdd_v(pmb.getAddress());
								}
								if(pmb.getAcreage()!= null){
									elementCode.setNewjzmj_v(pmb.getAcreage().toString()+"㎡");
								}
								if(pmb.getCertificatenumber()!= null){
									elementCode.setNewzjhm_v(pmb.getCertificatenumber());
								}
								if(pmb.getCertificatenumber()== null){
									pmb.setCertificatenumber(" ");
								}
								//反担保物详情
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
							}
						}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==7){//住宅
							String hql3 = "from ProcreditMortgageHouse where mortgageid="+mortgage.getId();
							List list3 = creditBaseDao.queryHql(hql3);
							if(list3 != null && list3.size()>0){
								ProcreditMortgageHouse pmb = (ProcreditMortgageHouse)list3.get(0);
								if(pmb.getPropertyperson()!= null){
									elementCode.setNewcqr_v(pmb.getPropertyperson());
								}
								if(pmb.getHouseaddress()!= null){
									elementCode.setNewfdcdd_v(pmb.getHouseaddress());
								}
								if(pmb.getBuildacreage()!= null){
									elementCode.setNewjzmj_v(pmb.getBuildacreage().toString()+"㎡");
								}
								if(pmb.getCertificatenumber()!= null){
									elementCode.setNewzjhm_v(pmb.getCertificatenumber());
								}
								if(pmb.getCertificatenumber()== null){
									pmb.setCertificatenumber(" ");
								}
								//反担保物详情
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
							}
						}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==9){//住宅用地
							String hql4 = "from ProcreditMortgageHouseground where mortgageid="+mortgage.getId();
							List list4 = creditBaseDao.queryHql(hql4);
							if(list4 != null && list4.size()>0){
								ProcreditMortgageHouseground pmb = (ProcreditMortgageHouseground)list4.get(0);
								if(pmb.getPropertyperson()!= null){
									elementCode.setNewcqr_v(pmb.getPropertyperson());
								}
								if(pmb.getAddress()!= null){
									elementCode.setNewfdcdd_v(pmb.getAddress());
								}
								if(pmb.getAcreage()!= null){
									elementCode.setNewjzmj_v(pmb.getAcreage().toString()+"㎡");
								}
								if(pmb.getCertificatenumber()!= null){
									elementCode.setNewzjhm_v(pmb.getCertificatenumber());
								}
								if(pmb.getCertificatenumber()== null){
									pmb.setCertificatenumber(" ");
								}
								//反担保物详情
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
							}
						}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==13){//工业用地
							String hql5 = "from ProcreditMortgageIndustry where mortgageid="+mortgage.getId();
							List list5 = creditBaseDao.queryHql(hql5);
							if(list5 != null && list5.size()>0){
								ProcreditMortgageIndustry pmb = (ProcreditMortgageIndustry)list5.get(0);
								if(pmb.getPropertyperson()!= null){
									elementCode.setNewcqr_v(pmb.getPropertyperson());
								}
								if(pmb.getAddress()!= null){
									elementCode.setNewfdcdd_v(pmb.getAddress());
								}
								if(pmb.getOccupyacreage()!= null){
									elementCode.setNewjzmj_v(pmb.getOccupyacreage().toString()+"㎡");
								}
								if(pmb.getCertificatenumber()!= null){
									elementCode.setNewzjhm_v(pmb.getCertificatenumber());
								}
								if(pmb.getCertificatenumber()== null){
									pmb.setCertificatenumber(" ");
								}
								//反担保物详情
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
							}
						}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==8){//商铺写字楼
							String hql6 = "from ProcreditMortgageOfficebuilding where mortgageid="+mortgage.getId();
							List list6 = creditBaseDao.queryHql(hql6);
							if(list6 != null && list6.size()>0){
								ProcreditMortgageOfficebuilding pmb = (ProcreditMortgageOfficebuilding)list6.get(0);
								if(pmb.getPropertyperson()!= null){
									elementCode.setNewcqr_v(pmb.getPropertyperson());
								}
								if(pmb.getHouseaddress()!= null){
									elementCode.setNewfdcdd_v(pmb.getHouseaddress());
								}
								if(pmb.getBuildacreage()!= null){
									elementCode.setNewjzmj_v(pmb.getBuildacreage().toString()+"㎡");
								}
								if(pmb.getCertificatenumber()!= null){
									elementCode.setNewzjhm_v(pmb.getCertificatenumber());
								}
								if(pmb.getCertificatenumber()== null){
									pmb.setCertificatenumber(" ");
								}
								//反担保物详情
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
							}
						}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==1){//车辆
							String hql = "from VProjMortCar where mortgageid ="+mortgage.getId();
							List list = creditBaseDao.queryHql(hql);
							VProjMortCar pmb = null;
							if(list != null && list.size()>0){
								pmb = (VProjMortCar)list.get(0);
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下"+(pmb.getManufacturerValue()==null?"   ":pmb.getManufacturerValue())+"车辆抵押（发动机号码为："+(pmb.getEngineNo()==null?"   ":pmb.getEngineNo())+"）作为抵押物\r\n");
							}else{
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下     车辆抵押（发动机号码为：    ）作为抵押物\r\n");
							}
						}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==5){//机械设备
							String hql = "from VProjMortMachineInfo where mortgageid="+mortgage.getId();
							VProjMortMachineInfo pmb = null;
							List list = creditBaseDao.queryHql(hql);
							if(list != null && list.size()>0){
								pmb = (VProjMortMachineInfo)list.get(0);
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下"+(pmb.getMachinename()==null?"   ":pmb.getMachinename())+"设备抵押（设备型号："+(pmb.getMachinetype()==null?"   ":pmb.getMachinetype())+"）作为抵押物\r\n");
							}else{
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下      设备抵押（设备型号：    ）作为抵押物\r\n");
							}
							
						}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==6){//存货商品
							String hql = "from VProjMortProduct where mortgageid ="+mortgage.getId();
							VProjMortProduct pmb = null;
							List list = creditBaseDao.queryHql(hql);
							if(list != null && list.size()>0){
								pmb = (VProjMortProduct)list.get(0);
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下"+(pmb.getName()==null?"   ":pmb.getName())+"存货商品（品牌："+(pmb.getBrand()==null?"   ":pmb.getBrand())+"）作为抵押");
							}else{
								fdbwxq.append(elementCode.getNewkhmc_v()+"名下       存货商品（品牌：    ）作为抵押");
							}
						}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==14){//无形权利
							String hql = "from VProjMortDroit where mortgageid ="+mortgage.getId();
							VProjMortDroit pmb = null;
							List list = creditBaseDao.queryHql(hql);
							if(list != null && list.size()>0){
								pmb = (VProjMortDroit)list.get(0);
								fdbwxq.append(elementCode.getNewkhmc_v()+"把享受权利比重"+(pmb.getDroitpercent()==null?"   ":pmb.getDroitpercent())+"%作为抵押");
							}else{
								fdbwxq.append(elementCode.getNewkhmc_v()+"把享受权利比重      %作为抵押");
							}
						}
						elementCode.setNewfdbwxq_v(fdbwxq.toString());////反担保物详情
					}
					if(null!=mortgage){
						if(null!=mortgage.getTypeid() && mortgage.getTypeid()==1){
							VProjMortCar vcm=(VProjMortCar) creditBaseDao.getById(VProjMortCar.class, mortgage.getDywId());
							elementCode.setNewfdbwxq_v("名下"+vcm.getManufacturerValue()+"车辆抵押（发动机号码为："+vcm.getEngineNo()+"作为抵押物");
						}
						if(null!=mortgage.getTypeid() && mortgage.getTypeid()==5){
							ProcreditMortgageMachineinfo pm=(ProcreditMortgageMachineinfo) creditBaseDao.getById(ProcreditMortgageMachineinfo.class, mortgage.getDywId());
							elementCode.setNewfdbwxq_v("名下"+pm.getMachinename()+"设备抵押(设备型号为："+pm.getMachinetype()+")作为抵押物");
						}
						if(null!=mortgage.getTypeid() && mortgage.getTypeid()==6){
							ProcreditMortgageProduct pm=(ProcreditMortgageProduct) creditBaseDao.getById(ProcreditMortgageProduct.class, mortgage.getDywId());
							elementCode.setNewfdbwxq_v("名下"+pm.getName()+"存货商品(品牌："+pm.getBrand()+")作为抵押物");
						}
						if(null!=mortgage.getTypeid() && mortgage.getTypeid()==7){
							ProcreditMortgageHouse pm=(ProcreditMortgageHouse) creditBaseDao.getById(ProcreditMortgageHouse.class, mortgage.getDywId());
							elementCode.setNewfdbwxq_v("名下位于"+pm.getHouseaddress()+pm.getBuildacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
						}
						if(null!=mortgage.getTypeid() && mortgage.getTypeid()==10){
							ProcreditMortgageBusiness pm=(ProcreditMortgageBusiness) creditBaseDao.getById(ProcreditMortgageBusiness.class, mortgage.getDywId());
							elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getAcreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
						}
						if(null!=mortgage.getTypeid() && mortgage.getTypeid()==11){
							ProcreditMortgageBusinessandlive pm=(ProcreditMortgageBusinessandlive) creditBaseDao.getById(ProcreditMortgageBusinessandlive.class, mortgage.getDywId());
							elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getAnticipateacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
						}
						if(null!=mortgage.getTypeid() && mortgage.getTypeid()==12){
							ProcreditMortgageEducation pm=(ProcreditMortgageEducation) creditBaseDao.getById(ProcreditMortgageEducation.class, mortgage.getDywId());
							elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getAcreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
						}
						if(null!=mortgage.getTypeid() && mortgage.getTypeid()==13){
							ProcreditMortgageIndustry pm=(ProcreditMortgageIndustry) creditBaseDao.getById(ProcreditMortgageIndustry.class, mortgage.getDywId());
							elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getOccupyacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
						}
						if(null!=mortgage.getTypeid() && mortgage.getTypeid()==14){
							VProjMortDroit pm = (VProjMortDroit) creditBaseDao.getById(VProjMortDroit.class, mortgage.getDywId());
							elementCode.setNewfdbwxq_v("把享受权利比重"+(pm.getDroitpercent()==null?"   ":pm.getDroitpercent())+"%作为抵押");
						}
					}
					
					//股权.%
					Double stockownershippercent = 0.0;
					if(mortgage.getId()!=null){
						String hql = "from VProjMortStockFinance AS vs where vs.mortgageid ="+mortgage.getId();
						List list = creditBaseDao.queryHql(hql);
						if(list!= null && list.size()>0){
							VProjMortStockFinance vs = (VProjMortStockFinance)list.get(0);
							if(vs.getStockownershippercent()!=null){
								stockownershippercent = vs.getStockownershippercent();
								elementCode.setNewgqbfs_v(stockownershippercent.toString()+"%");
							}else{
								elementCode.setNewgqbfs_v(stockownershippercent+"%");
							}
							//目标公司名称
							elementCode.setNewmbgsmc_v(vs.getEnterprisename());
							if(vs.getCorporationname()!= null){
								Object obj = null;
								obj = creditBaseDao.getById(SlCompanyMain.class, vs.getCorporationname().longValue());
								if(obj != null){
									SlCompanyMain en = (SlCompanyMain)obj;
									//出质股权数额小写
									Double d = 0.0;
									if(en.getRegisterMoney()!=null){
										d = en.getRegisterMoney()*stockownershippercent;
									}
									elementCode.setNewczgqsexx_v(d.toString()+"万元");
								}
							}
						}
						
					}
					//质押担保债权本金数额 
					if(mortgage.getId()!= null){
						String hql = "from VProcreditDictionary AS d where d.projid =? and d.businessType =?";
						Object [] obj = {Long.parseLong(proId),financingProject.getBusinessType()};
						List list = creditBaseDao.queryHql(hql,obj);
						Double shippercent = 0.0;
						int enterpId = 0;
						if(list!= null && list.size()>0 && list.size()>1){
							for(int i =0;i<list.size();i++){
								String hqlship = "from VProjMortStockFinance AS vs where vs.mortgageid ="+mortgage.getId();
								List listship = creditBaseDao.queryHql(hqlship);
								if(listship!= null && listship.size()>0){
									VProjMortStockFinance vs = (VProjMortStockFinance)listship.get(0);
									shippercent = vs.getStockownershippercent();
									enterpId = vs.getCorporationname();
								}
								for(int j= 1;j<list.size();j++){
									String hqlship2 = "from VProjMortStockFinance AS vs where vs.mortgageid ="+mortgage.getId();
									List listship2 = creditBaseDao.queryHql(hqlship2);
									if(listship2!= null && listship2.size()>0){
										VProjMortStockFinance vs2 = (VProjMortStockFinance)listship2.get(0);
										if(enterpId == vs2.getCorporationname()){
											shippercent += (shippercent + vs2.getStockownershippercent());
										}
									}
								}
								
							}
						}else{
							shippercent = Double.valueOf("1");
						}
						if(shippercent.compareTo(0.0)>0){
							Double je = 0.0;
							if(financingProject.getProjectMoney()!=null){
								je =financingProject.getProjectMoney().doubleValue()*(stockownershippercent/shippercent);
								elementCode.setNewzydbzqbjse_v(myFormatter.format(je)+"元");
							}
							
						}
					}
				}
				
			}
		*/}
		/*
		 * 我方抵质押物
		 * 
		 * */
		if(conId != 0){/*
			if(contract.getMortgageId()!= 0 && "thirdContract".equals(contractType)){
				VProcreditDictionaryFinance vmortgage = null;
				SlMortgageFinancing slmortgage =null;
				slmortgage =(SlMortgageFinancing)creditBaseDao.getById(SlMortgageFinancing.class, contract.getMortgageId().longValue());
				if(slmortgage!= null){
					vmortgage =(VProcreditDictionaryFinance)creditBaseDao.getById(VProcreditDictionaryFinance.class, slmortgage.getMortId().intValue());
					if(vmortgage != null){
						if(vmortgage.getAssureofnameEnterOrPerson()!=null){
							elementCode.setRzczrgsmc_v(vmortgage.getAssureofnameEnterOrPerson());
							elementCode.setRzczrmc_v(vmortgage.getAssureofnameEnterOrPerson());
						}
						if(vmortgage.getMortgagename()!= null){
							elementCode.setRzdyw_v(vmortgage.getMortgagename());
						}
						//保证方式
						if(vmortgage.getAssuremodeidValue() != null){
							elementCode.setBzfs_v(vmortgage.getAssuremodeidValue());
						}
						
						if(vmortgage.getAssureofname()!=null && vmortgage.getPersonTypeId()!= null){
							SlCompanyMain e = null;
							SlPersonMain p = null;
							if(vmortgage.getPersonTypeId() == 603){//自然人
								p = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, vmortgage.getAssureofname().longValue());
								if(p.getName()!=null){
									elementCode.setRzbzrmc_v(p.getName());
									elementCode.setRzdyrmc_v(p.getName());
								}
								if(p.getCardnum()!=null){
									elementCode.setRzczrsfzhm_v(p.getCardnum());
									elementCode.setRzbzrzjhm_v(p.getCardnum());
									elementCode.setRzdyrsfzhm_v(p.getCardnum());
								}
								if(p.getAddress()!=null){
									elementCode.setRzczrtxdz_v(p.getAddress());
									elementCode.setRzbzrtxdz_v(p.getAddress());
									elementCode.setRzdyrtxdz_v(p.getAddress());
								}
								if(p.getHome()!=null){
									elementCode.setRzczrzs_v(p.getHome());
									elementCode.setRzbzrzsdz_v(p.getHome());
									elementCode.setRzdyrzs_v(p.getHome());
								}
								if(p.getPostalCode()!= null){
									elementCode.setRzczryzbm_v(p.getPostalCode());
									elementCode.setRzbzryzbm_v(p.getPostalCode());
									elementCode.setRzdyryzbm_v(p.getPostalCode());
								}
								if(p.getTel()!= null){
									elementCode.setRzczrlxdh_v(p.getTel());
									elementCode.setRzbzrlxdh_v(p.getTel());
									elementCode.setRzdyrlxdh_v(p.getTel());
								}
								if(p.getTax()!= null){
									elementCode.setRzczrczhm_v(p.getTax());
									elementCode.setRzbzrczhm_v(p.getTax());
									elementCode.setRzdyrczhm_v(p.getTax());
								}
							}else if(vmortgage.getPersonTypeId() == 602){//法人
								e = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, vmortgage.getAssureofname().longValue());
								if(e.getCorName()!=null){
									elementCode.setRzbzgsmc_v(e.getCorName());
									elementCode.setRzdyrgsmc_v(e.getCorName());
								}
								if(e.getOrganizeCode()!= null){
									elementCode.setRzczrgsyyzzhm_v(e.getOrganizeCode());
									elementCode.setRzbzgszjhm_v(e.getOrganizeCode());
									elementCode.setRzdyryyzzhm_v(e.getOrganizeCode());
								}
								if(e.getMessageAddress()!= null){
									elementCode.setRzczrgstxdz_v(e.getMessageAddress());
									elementCode.setRzbzgstxdz_v(e.getMessageAddress());
									elementCode.setRzdyrgstxdz_v(e.getMessageAddress());
								}
								if(e.getSjjyAddress()!= null){
									elementCode.setRzczrgsdz_v(e.getSjjyAddress());
									elementCode.setRzbzgsdz_v(e.getSjjyAddress());
									elementCode.setRzdyrgsdz_v(e.getSjjyAddress());
								}
								if(e.getPostalCode()!= null){
									elementCode.setRzczrgsyzbm_v(e.getPostalCode());
									elementCode.setRzbzgsyzbm_v(e.getPostalCode());
									elementCode.setRzdyrgsyzbm(e.getPostalCode());
								}
								if(e.getTel()!= null){
									elementCode.setRzczrgslxdh_v(e.getTel());
									elementCode.setRzbzgslxdh_v(e.getTel());
									elementCode.setRzdyrgslxdh_v(e.getTel());
								}
								if(e.getTax()!= null){
									elementCode.setRzczrgsczhm_v(e.getTax());
									elementCode.setRzbzgsczhm_v(e.getTax());
									elementCode.setRzdyrgsczhm_v(e.getTax());
								}
								if(e.getHaveCharcter() != null){
									Object object  = creditBaseDao.getById(Dictionary.class, e.getHaveCharcter().longValue());
									if(object != null){
										Dictionary dic = (Dictionary)object;
										if(dic != null && dic.getItemValue()!= null){
											elementCode.setSyzxz_v(dic.getItemValue());
										}
									}
									
								}
							}
							
						}
					}
					
				}
			}
		*/}
		
		return elementCode;
	}
	/*//获得所有小额贷的系统要素
	public SmallLoanElementCode getGuaranteeElementBySystem(String proId , int conId ,int tempId, String contractType,String rnum) throws Exception{
		DecimalFormat myFormatter = new DecimalFormat("####.#");
		GLGuaranteeloanProject smallloanProject = (GLGuaranteeloanProject)creditBaseDao.getById(GLGuaranteeloanProject.class, Long.parseLong(proId)) ; //项目信息实体
		SmallLoanElementCode elementCode = new SmallLoanElementCode();
		//SlSmallloanProject smallloanProject =(SlSmallloanProject)creditBaseDao.getById(SlSmallloanProject.class, Long.parseLong(proId)) ; //项目信息实体
		//EnterpriseView enterv = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID().intValue());
		ProcreditContract contract = (ProcreditContract)creditBaseDao.getById(ProcreditContract.class, conId);
		
		 * 合同编号
		 * 合同文档名称：客户简称+项目编号+合同名称+01(同类合同依次相加)
		 * 合同编号：项目编号 +合同特定代表（如1）+01(同类合同依次相加)
		 * 合同类型代表：借款合同1，保证合同2，抵押合同3，权利质押合同4，财务协议5，股东会决议6
		 * 
		//合同编号
		if(smallloanProject.getProjectNumber() != null){
			String projectNumber = smallloanProject.getProjectNumber();//项目编号
			int count = 0;
			if(!StringUtil.isNumeric(rnum)){
				rnum = "0";
			}
			count = Integer.parseInt(rnum);
			if(count <10){
				elementCode.setJkhtbh_v(projectNumber+"-1"+"-0"+count);//借款合同编号
				elementCode.setNewjkhtbh_v(projectNumber+"-1"+"-0"+count);
				elementCode.setBzhtbh_v(projectNumber+"-2"+"-0"+count);//保证合同编号
				elementCode.setCwxyhtbh_v(projectNumber+"-3"+"-0"+count);//财务协议合同编号
				elementCode.setDyhtbh_v(projectNumber+"-4"+"-0"+count);//抵押合同编号
				elementCode.setZyhtbh_v(projectNumber+"-5"+"-0"+count);//质押合同编号
			}else{
				elementCode.setJkhtbh_v(projectNumber+"-1"+"-"+count);//借款合同编号
				elementCode.setNewjkhtbh_v(projectNumber+"-1"+"-"+count);
				elementCode.setBzhtbh_v(projectNumber+"-2"+"-"+count);//保证合同编号
				elementCode.setCwxyhtbh_v(projectNumber+"-3"+"-"+count);//财务协议合同编号
				elementCode.setDyhtbh_v(projectNumber+"-4"+"-"+count);//抵押合同编号
				elementCode.setZyhtbh_v(projectNumber+"-5"+"-"+count);//质押合同编号
			}
		}
		
		
		 * 项目表相关信息
		 * 贷款金额，币种，贷款利率，贷款起始日，贷款结束日等等
		 * 
		//贷款本金金额大小写
		if(smallloanProject.getProjectMoney()!=null){
			Double projectMoney = smallloanProject.getProjectMoney().doubleValue();
			String dw = "元整";
			if(projectMoney > 10000.0){
				dw = "万元";
			}
			elementCode.setDkbjjexx_v(myFormatter.format(projectMoney).toString()+"元");
			elementCode.setDkbjjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
			elementCode.setNewjkjexx_v(myFormatter.format(projectMoney).toString()+"元");
			elementCode.setNewjkjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
		}
		//币种
		if(smallloanProject.getCurrency() != null){
			Dictionary dictionary = null;
			dictionary = (Dictionary)creditBaseDao.getById(Dictionary.class, smallloanProject.getCurrency());
			if(dictionary != null){
				elementCode.setBz_v(dictionary.getItemValue());
				elementCode.setNewbz_v(dictionary.getItemValue());
			}
		}
		//贷款用途
		if(smallloanProject.getPurposeType()!= null){
			Dictionary dictionary = null;
			dictionary = (Dictionary)creditBaseDao.getById(Dictionary.class, smallloanProject.getPurposeType());
			if(dictionary != null){
				elementCode.setDkyt_v(dictionary.getItemValue());
			}
		}
		//贷款利率
		if(smallloanProject.getAccrual()!=null){
			elementCode.setDklv_v(smallloanProject.getAccrual().doubleValue()+"%");
		}
		//贷款起始日
		if(smallloanProject.getStartDate()!= null){
			String startDateStr = smallloanProject.getStartDate().toString().substring(0,smallloanProject.getStartDate().toString().lastIndexOf(" "));
			elementCode.setDkqsr_v(MoneyFormat.getInstance().formatDate(startDateStr));
		}
		//贷款结束日
		if(smallloanProject.getIntentDate()!= null){
			String intentDateStr = smallloanProject.getIntentDate().toString().substring(0,smallloanProject.getIntentDate().toString().lastIndexOf(" "));
			elementCode.setDkjsr_v(MoneyFormat.getInstance().formatDate(intentDateStr));
		}
		//违约金比例（%/天）逾期费率
		if(smallloanProject.getOverdueRate()!= null){
			elementCode.setWyjblt_v(smallloanProject.getOverdueRate().doubleValue()+"%/天");
		}
		//违约金比例
		if(smallloanProject.getBreachRate() != null){
			elementCode.setWyjbl_v(smallloanProject.getBreachRate().doubleValue()+"%");
		}
		//是否前置付息
		if(smallloanProject.getIsPreposePayAccrual() != null){
			if(smallloanProject.getIsPreposePayAccrual() == 1){//前置
				if(smallloanProject.getPayaccrualType().equals("oneTimePay")){//一次性付息
					elementCode.setFwfyzffs_v("财务服务费一次性付清，支付日期：年  月  日（或贷款实际发放日）。");
					elementCode.setLxzffs_v("利息一次性付清，支付日期：年  月  日（或贷款实际发放日）。");
				}else if(smallloanProject.getPayaccrualType().equals("monthPay")){
					elementCode.setFwfyzffs_v("财务服务费每月提前支付 ，贷款发放之日支付第一个月财务服务费，财务服务费按月支付，每月  日前，支付到贷款方指定账号。");
					elementCode.setLxzffs_v("利息每月提前支付 ，贷款发放之日支付第一个月利息，利息按月支付，每月  日前，支付到贷款方指定账号。");
				}else if(smallloanProject.getPayaccrualType().equals("calendarMonthPay")){
					elementCode.setFwfyzffs_v("财务服务费每月提前支付 ，贷款发放之日支付第一个月财务服务费，首次财务服务费人民币       元整（￥：       元），付息日期：年  月  日  ；剩余财务服务费按月支付，每月1日前，支付到贷款方指定账号。");
					elementCode.setLxzffs_v("利息每月提前支付 ，贷款发放之日支付第一个月利息，首次利息人民币       元整（￥：       元），付息日期：年  月  日  ；剩余利息按月支付，每月1日前，支付到贷款方指定账号。");
				}
			}else{
				if(smallloanProject.getPayaccrualType().equals("oneTimePay")){//一次性付息
					elementCode.setFwfyzffs_v("财务服务费一次性付清，支付日期：年  月  日。");
					elementCode.setLxzffs_v("利息一次性付清，支付日期：年  月  日。");
				}else if(smallloanProject.getPayaccrualType().equals("monthPay")){
					elementCode.setFwfyzffs_v("财务服务费按月支付，每月  日前，支付到贷款方指定账号。");
					elementCode.setLxzffs_v("利息按月支付，每月  日前，支付到贷款方指定账号。");
				}else if(smallloanProject.getPayaccrualType().equals("calendarMonthPay")){
					elementCode.setFwfyzffs_v("首次财务服务费人民币       元整（￥：       元），付息日期：年  月  日  ；剩余财务服务费按月支付，每月1日前，支付到贷款方指定账号。");
					elementCode.setLxzffs_v("首次利息人民币       元整（￥：       元），付息日期：年  月  日  ；剩余利息按月支付，每月1日前，支付到贷款方指定账号。");
				}
			}
		}
		
		//服务费用总额（大写）
		if(smallloanProject.getFinanceServiceOfRate()!= null && smallloanProject.getProjectMoney()!= null){
			//Double serviceMoney = (smallloanProject.getFinanceServiceOfRate().divide(BigDecimal.valueOf(100)).multiply(smallloanProject.getProjectMoney())).doubleValue();
			if(smallloanProject.getFinanceServiceOfRate().compareTo(BigDecimal.ZERO)!=0 ){
				String fundType = "";
				if(smallloanProject.getBusinessType().equals("SmallLoan")){
					fundType = "serviceMoney";
				}else{
					fundType = "financingserviceMoney";
				}
				Double serviceMoney = 0.0;
				List listMoney = null;
				Object [] obje = {smallloanProject.getProjectId(),smallloanProject.getBusinessType(),fundType};
				String hqlm ="from SlFundIntent where projectId = ? and businessType =? and fundType = ?";
				listMoney = creditBaseDao.queryHql(hqlm,obje);
				if(listMoney!= null && listMoney.size()>0){
					for(int i=0;i<listMoney.size();i++){
						SlFundIntent slFundIntent = (SlFundIntent)listMoney.get(i);
						serviceMoney += slFundIntent.getIncomeMoney().doubleValue();
					}
				}
				//服务费用总额（大写）
				elementCode.setFwfyjedx_v(MoneyFormat.getInstance().hangeToBig(serviceMoney)+"元");
				//多笔支付款项内容(服务费列表)
				
				List<SlFundIntent> list = null;
				String hql = "from SlFundIntent AS f where f.projectId =? and f.fundType =? and f.businessType = ?";
				Object [] param ={Long.parseLong(proId),fundType,smallloanProject.getBusinessType()};
				list = creditBaseDao.queryHql(hql, param);
				if(list != null && list.size()>0){
					StringBuffer dbzfkxnrBuffer = new StringBuffer();
					String dbzfkxnr = "";
					for(int i = 0;i < list.size(); i++){
						SlFundIntent fi = (SlFundIntent)list.get(i);
						String intentDateStr = fi.getIntentDate().toString().substring(0,fi.getIntentDate().toString().lastIndexOf(" "));
						if(fundType == "serviceMoney" || "serviceMoney".equals(fundType)){
							dbzfkxnr = "费用支付时间："+MoneyFormat.getInstance().formatDate(intentDateStr)+"      "+"支付金额："+myFormatter.format(fi.getIncomeMoney())+"元\r\n";
						}else{
							dbzfkxnr = "费用支付时间："+MoneyFormat.getInstance().formatDate(intentDateStr)+"      "+"支付金额："+myFormatter.format(fi.getPayMoney())+"元\r\n";
						}
						dbzfkxnrBuffer.append(dbzfkxnr);
						
					}
					elementCode.setDbzfkxnr_v(dbzfkxnrBuffer.toString());
				}
				
			}else{
				elementCode.setFwfyjedx_v("零元");
			}
			
			
		}
		
		
		 * 客户类型-债务人
		 * 个人person_customer，企业company_customer
		 * 
		if(smallloanProject.getOppositeType().equals("person_customer")){
			Person person = null;
			person = (Person)creditBaseDao.getById(Person.class, smallloanProject.getOppositeID().intValue());
			if(person.getName()!=null){
				elementCode.setZwr_v(person.getName());//债务人
				elementCode.setJkrmc_v(person.getName());//甲方（借款人名称）
				elementCode.setNewjkr_v(person.getName());
			}
			if(person.getCardnumber()!=null){
				elementCode.setJkrsfzhm_v(person.getCardnumber());//借款人身份证号码
			}
			if(person.getAddress()!=null){
				elementCode.setJkrzz_v(person.getAddress());//借款人住址
			}
			if(person.getCellphone()!= null){
				elementCode.setJkrlxdh_v(person.getCellphone());
			}
			if(person.getPostcode() != null){
				elementCode.setJkryzbm_v(person.getPostcode());
			}
			if(person.getPostaddress() != null){
				elementCode.setJkrtxdz_v(person.getPostaddress());
			}
		}else if(smallloanProject.getOppositeType().equals("company_customer")){
			EnterpriseView enterprise = null;
			enterprise = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID().intValue());
			if(enterprise.getEnterprisename()!= null){
				elementCode.setZwr_v(enterprise.getEnterprisename());
				elementCode.setJkfgsmc_v(enterprise.getEnterprisename());//甲方（借款方公司名称）
				elementCode.setNewjkr_v(enterprise.getEnterprisename());
			}
			if(enterprise.getLegalperson()!= null){
				elementCode.setJkffddbr_v(enterprise.getLegalperson());//借款公司法定代表人
			}
			if(enterprise.getAddress()!= null){
				elementCode.setJkfgsdz_v(enterprise.getAddress());//借款公司地址
			}
			if(enterprise.getTelephone() != null){
				elementCode.setJkfgslxdh_v(enterprise.getTelephone());//借款方公司联系电话
			}
			if(enterprise.getPostcoding()!= null){
				elementCode.setJkfgsyzbm_v(enterprise.getPostcoding());
			}
			if(enterprise.getArea()!= null){
				elementCode.setJkfgstxdz_v(enterprise.getArea());
			}
			if(enterprise.getCciaa() != null){
				elementCode.setJkfyyzzhm_v(enterprise.getCciaa());//借款方营业执照号码
			}
		}
		
		
		 * 我方主体-债权人
		 * 个人person_ourmain，企业company_ourmain
		 * 
		if(smallloanProject.getMineType().equals("person_ourmain")){//我方主体为个人
			SlPersonMain personMain = null;
			if(smallloanProject.getMineId()!=0){
				personMain = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, smallloanProject.getMineId());
				if(personMain != null){
					if(personMain.getName()!=null){
						elementCode.setZqrmc_v(personMain.getName());
						elementCode.setDkrmc_v(personMain.getName());//乙方（贷款人名称）
						elementCode.setDyqr_v(personMain.getName());//抵押权人
						elementCode.setZhqr_v(personMain.getName());//质权人
						elementCode.setNewdkr_v(personMain.getName());
					}
					if(personMain.getCardnum()!=null){
						elementCode.setZqrsfzhm_v(personMain.getCardnum());
						elementCode.setDkrsfzhm_v(personMain.getCardnum());//贷款人身份证号
						elementCode.setDyqrsfzhm_v(personMain.getCardnum());//抵押权人身份证号码
						elementCode.setZhqrsfzhm_v(personMain.getCardnum());//质权人身份证号码
					}
					if(personMain.getAddress()!=null){
						elementCode.setZqrtxdz_v(personMain.getAddress());
						elementCode.setDkrzz_v(personMain.getAddress());//贷款人住址
						elementCode.setJkrtxdz_v(personMain.getAddress());
						elementCode.setDyrtxdz_v(personMain.getAddress());//抵押权人通讯地址
						elementCode.setZhqrtxdz_v(personMain.getAddress());//质权人通讯地址
					}
					if(personMain.getLinktel()!=null){
						elementCode.setZqrlxdh_v(personMain.getLinktel());
						elementCode.setJkrlxdh_v(personMain.getLinktel());
						elementCode.setDyqrlxdh_v(personMain.getLinktel());//抵押权人联系电话
						elementCode.setZhqrlxdh_v(personMain.getLinktel());//质权人联系电话
					}
					if(personMain.getHome()!= null){
						elementCode.setZqrzs_v(personMain.getHome());//债权人住所
						elementCode.setDyqrzs_v(personMain.getHome());//抵押权人住所
						elementCode.setZhqrzs_v(personMain.getHome());//质权人住所
					}
					if(personMain.getPostalCode()!= null){
						elementCode.setZqryzbm_v(personMain.getPostalCode());//债权人邮政编码
						elementCode.setDyqryzbm_v(personMain.getPostalCode());//抵押权人邮政 编码
						elementCode.setDkryzbm_v(personMain.getPostalCode());//贷款人邮政编码
						elementCode.setZhqryzbm_v(personMain.getPostalCode());//质权人邮政编码
					}
					if(personMain.getTax()!= null){
						elementCode.setZqrczhm_v(personMain.getTax());//债权人传真号码
						elementCode.setDyqrcz_v(personMain.getTax());//抵押权人传真
						elementCode.setZhqrczhm_v(personMain.getTax());//质权人传真
					}
				}
			}
			
		}else if(smallloanProject.getMineType().equals("company_ourmain")){//我方主体为企业
			SlCompanyMain companyMain = null;
			if(smallloanProject.getMineId()!=0){
				companyMain = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, smallloanProject.getMineId());
				if(companyMain != null){
					if(companyMain.getCorName()!= null){
						elementCode.setZqgsmc_v(companyMain.getCorName());
						elementCode.setDkfgsmc_v(companyMain.getCorName());//乙方（贷款方公司名称）
						elementCode.setDyqgsmc_v(companyMain.getCorName());//抵押权公司名称
						elementCode.setZqfgsmc_v(companyMain.getCorName());//质权方公司名称
						elementCode.setNewdkr_v(companyMain.getCorName());
					}
					if(companyMain.getBusinessCode()!= null){
						elementCode.setZqgsyyzzhm_v(companyMain.getBusinessCode());
						elementCode.setDyqgsyyzzhm_v(companyMain.getBusinessCode());//抵押权公司营业执照号码
						elementCode.setZqfyyzzhm_v(companyMain.getBusinessCode());//质权方 营业执照号码
						elementCode.setDkfyyzzhm_v(companyMain.getBusinessCode());//贷款方营业执照号码
					}
					if(companyMain.getMessageAddress() != null){
						elementCode.setZqgstxdz_v(companyMain.getMessageAddress());
						elementCode.setDyqgstxdz(companyMain.getMessageAddress());//抵押权公司通讯地址
						elementCode.setZqftxdz_v(companyMain.getMessageAddress());//质权方通讯地址
						elementCode.setDkfgstxdz_v(companyMain.getMessageAddress());//贷款方公司通讯地址
					}
					if(companyMain.getTel()!= null){
						elementCode.setZqgslxdh_v(companyMain.getTel());
						elementCode.setDkfgslxdh_v(companyMain.getTel());
						elementCode.setDyqgslxdh_v(companyMain.getTel());//抵押权公司联系电话
						elementCode.setZqflxdh_v(companyMain.getTel());//质权方联系电话
					}
					if(companyMain.getTax()!= null){
						elementCode.setZqgszzhm_v(companyMain.getTax());
						elementCode.setDyqgsczhm_v(companyMain.getTax());//抵押权公司传真号码
						elementCode.setZqfczhm_v(companyMain.getTax());//质权方传真号码
					}
					if(companyMain.getLawName()!=null){
						elementCode.setDkffddbr_v(companyMain.getLawName());//贷款方法定代表人
					}
					if(companyMain.getSjjyAddress()!=null){
						elementCode.setDkfgsdz_v(companyMain.getSjjyAddress());//贷款方公司地址
						elementCode.setDyqgsdz_v(companyMain.getSjjyAddress());//抵押权公司地址
						elementCode.setZqgsdz_v(companyMain.getSjjyAddress());//债权公司地址
						elementCode.setZqfgsdz_v(companyMain.getSjjyAddress());//质权方公司地址
					}
					if(companyMain.getPostalCode()!= null){
						elementCode.setZqgsyzbm_v(companyMain.getPostalCode());//债权公司邮政编码
						elementCode.setDyqgsyzbm(companyMain.getPostalCode());//抵押权公司邮政编码
						elementCode.setDkfgsyzbm_v(companyMain.getPostalCode());//贷款方公司邮政编码
						elementCode.setZqfyzbm_v(companyMain.getPostalCode());//质权方邮政编码
					}
				}
				
			}
		}
		
		
		 * 反担保合同相关
		 * 所有人类型：法人 602，自然人 603
		 * 担保类型：抵押担保604，质押担保605，信用担保606
		 * 
		if(conId != 0){
			if(contract.getMortgageId()!= 0 && "thirdContract".equals(contractType)){
				VProcreditDictionaryGuarantee mortgage = null;
				mortgage = (VProcreditDictionaryGuarantee)creditBaseDao.getById(VProcreditDictionaryGuarantee.class, contract.getMortgageId());
				EnterpriseView e = null ;
				VPersonDic p = null ;
				//抵押物名称
				if(mortgage.getMortgagename() != null){
					elementCode.setDywmc_v(mortgage.getMortgagename());
				}
				//保证方式
				if(mortgage.getAssuremodeidValue() != null){
					elementCode.setBzfs_v(mortgage.getAssuremodeidValue());
				}
				if(mortgage.getPersonTypeId() == 603){//自然人
					p = (VPersonDic)creditBaseDao.getById(VPersonDic.class, mortgage.getAssureofname());
					if(null != p.getName()){
						elementCode.setBzrmc_v(p.getName());
						elementCode.setDyr_v(p.getName());//抵押人
						elementCode.setCzr_v(p.getName());//出质人
						elementCode.setNewkhmc_v(p.getName());
					}
					if(null != p.getCardnumber()){
						elementCode.setBzrsfzhm_v(p.getCardnumber());
						elementCode.setDyrsfzhm(p.getCardnumber());//抵押人身份证号码
						elementCode.setCzrsfzhm_v(p.getCardnumber());//出质人身份证号码
					}
					if(null != p.getAddress()){
						elementCode.setBzrzs_v(p.getAddress());
						elementCode.setDyrzs_v(p.getAddress());//抵押人住所
						elementCode.setCzrzs_v(p.getAddress());//出质人住所
					}
					if(null != p.getPostaddress()){
						elementCode.setBzrtxdz_v(p.getPostaddress());
						elementCode.setDyrtxdz_v(p.getPostaddress());//抵押人通讯地址
						elementCode.setCzrtxdz_v(p.getPostaddress());//出质人通讯地址
					}
					if(null != p.getPostcode()){
						elementCode.setBzryzbm_v(p.getPostcode());
						elementCode.setDyryzbm_v(p.getPostcode());//抵押人邮政编码
						elementCode.setCzryzbm_v(p.getPostcode());//出质人邮政编码
					}
					if(null != p.getTelphone()&& null!= p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getCellphone()+"/"+p.getTelphone());
						elementCode.setDyrlxdh_v(p.getCellphone()+"/"+p.getTelphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getCellphone()+"/"+p.getTelphone());//出质人联系电话
					}else if(null == p.getTelphone()&& null!= p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getCellphone());
						elementCode.setDyrlxdh_v(p.getCellphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getCellphone());//出质人联系电话
					}else if(null != p.getTelphone()&& null== p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getTelphone());
						elementCode.setDyrlxdh_v(p.getTelphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getTelphone());//出质人联系电话
					}
					if(null != p.getFax()){
						elementCode.setBzrczhm_v(p.getFax());
						elementCode.setDyrcz_v(p.getFax());//抵押人传真
						elementCode.setCzrczhm_v(p.getFax());//出质人传真号码
					}
				}else if(mortgage.getPersonTypeId() == 602){//法人
					e = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, mortgage.getAssureofname());
					if(e.getEnterprisename()!= null){
						elementCode.setBzgsmc(e.getEnterprisename());
						elementCode.setDygsmc_v(e.getEnterprisename());//抵押公司名称
						elementCode.setCzfgsmc_v(e.getEnterprisename());//出质方公司名称
						elementCode.setNewkhmc_v(e.getEnterprisename());
					}
					if(e.getCciaa()!= null){
						elementCode.setBzgsyyzzhm_v(e.getCciaa());
						elementCode.setDygsyyzzhm_v(e.getCciaa());//抵押公司营业执照号码
						elementCode.setCzfyyzzhm_v(e.getCciaa());//出质方营业执照号码
					}
					if(e.getAddress()!= null){
						elementCode.setBzgsdz_v(e.getAddress());
						elementCode.setDygsdz_v(e.getAddress());//抵押公司地址
						elementCode.setCzfgsdz_v(e.getAddress());//出质方公司地址
					}
					if(e.getArea() != null){
						elementCode.setBzgstxdz_v(e.getArea());
						elementCode.setDygstxdz_v(e.getArea());//抵押公司通讯地址
						elementCode.setCzftxdz_v(e.getArea());//出质方通讯地址
					}
					if(e.getPostcoding()!= null){
						elementCode.setBzgsyzbm_v(e.getPostcoding());
						elementCode.setDygsyzbm_v(e.getPostcoding());//抵押公司邮政编码
						elementCode.setCzfyzbm_v(e.getPostcoding());//出质方邮政编码
					}
					if(e.getTelephone()!= null){
						elementCode.setBzgslxdh_v(e.getTelephone());
						elementCode.setDygslxdh_v(e.getTelephone());//抵押公司联系电话
						elementCode.setCzflxdh_v(e.getTelephone());//出质方联系电话
					}
					if(e.getFax() != null){
						elementCode.setBzgsczhm_v(e.getFax());
						elementCode.setDygsczhm_v(e.getFax());//抵押公司传真号码
						elementCode.setCzfczhm_v(e.getFax());//出质方传真号码
					}
					if(e.getOwnershipv() != null){
						elementCode.setSyzxz_v(e.getOwnershipv());//所有制性质
					}
				}
				//产权人，房地产地点，建筑面积，证件号码
				if(mortgage.getId()!= null){
					StringBuffer fdbwxq = new StringBuffer();
					if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==10){//商业用地
						String hql = "from ProcreditMortgageBusiness where mortgageid="+mortgage.getId();
						List list = creditBaseDao.queryHql(hql);
						if(list != null && list.size()>0){
							ProcreditMortgageBusiness pmb = (ProcreditMortgageBusiness)list.get(0);
							if(pmb.getPropertyperson()!= null){
								elementCode.setNewcqr_v(pmb.getPropertyperson());
							}
							if(pmb.getAddress()!= null){
								elementCode.setNewfdcdd_v(pmb.getAddress());
							}
							if(pmb.getAcreage()!= null){
								elementCode.setNewjzmj_v(pmb.getAcreage().toString()+"㎡");
							}
							if(pmb.getCertificatenumber()!= null){
								elementCode.setNewzjhm_v(pmb.getCertificatenumber());
							}
							if(pmb.getCertificatenumber()== null){
								pmb.setCertificatenumber(" ");
							}
							//反担保物详情
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==11){//商住用地
						String hql1 = "from ProcreditMortgageBusinessandlive where mortgageid="+mortgage.getId();
						List list1 = creditBaseDao.queryHql(hql1);
						if(list1!= null && list1.size()>0){
							ProcreditMortgageBusinessandlive pmb = (ProcreditMortgageBusinessandlive)list1.get(0);
							if(pmb.getPropertyperson()!= null){
								elementCode.setNewcqr_v(pmb.getPropertyperson());
							}
							if(pmb.getAddress()!= null){
								elementCode.setNewfdcdd_v(pmb.getAddress());
							}
							if(pmb.getAcreage()!= null){
								elementCode.setNewjzmj_v(pmb.getAcreage().toString()+"㎡");
							}
							if(pmb.getCertificatenumber()!= null){
								elementCode.setNewzjhm_v(pmb.getCertificatenumber());
							}
							if(pmb.getCertificatenumber()== null){
								pmb.setCertificatenumber(" ");
							}
							//反担保物详情
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==12){//教育用地
						String hql2 = "from ProcreditMortgageEducation where mortgageid="+mortgage.getId();
						List list2 = creditBaseDao.queryHql(hql2);
						if(list2 != null && list2.size()>0){
							ProcreditMortgageEducation pmb = (ProcreditMortgageEducation)list2.get(0);
							if(pmb.getPropertyperson()!= null){
								elementCode.setNewcqr_v(pmb.getPropertyperson());
							}
							if(pmb.getAddress()!= null){
								elementCode.setNewfdcdd_v(pmb.getAddress());
							}
							if(pmb.getAcreage()!= null){
								elementCode.setNewjzmj_v(pmb.getAcreage().toString()+"㎡");
							}
							if(pmb.getCertificatenumber()!= null){
								elementCode.setNewzjhm_v(pmb.getCertificatenumber());
							}
							if(pmb.getCertificatenumber()== null){
								pmb.setCertificatenumber(" ");
							}
							//反担保物详情
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==7){//住宅
						String hql3 = "from ProcreditMortgageHouse where mortgageid="+mortgage.getId();
						List list3 = creditBaseDao.queryHql(hql3);
						if(list3 != null && list3.size()>0){
							ProcreditMortgageHouse pmb = (ProcreditMortgageHouse)list3.get(0);
							if(pmb.getPropertyperson()!= null){
								elementCode.setNewcqr_v(pmb.getPropertyperson());
							}
							if(pmb.getHouseaddress()!= null){
								elementCode.setNewfdcdd_v(pmb.getHouseaddress());
							}
							if(pmb.getBuildacreage()!= null){
								elementCode.setNewjzmj_v(pmb.getBuildacreage().toString()+"㎡");
							}
							if(pmb.getCertificatenumber()!= null){
								elementCode.setNewzjhm_v(pmb.getCertificatenumber());
							}
							if(pmb.getCertificatenumber()== null){
								pmb.setCertificatenumber(" ");
							}
							//反担保物详情
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==9){//住宅用地
						String hql4 = "from ProcreditMortgageHouseground where mortgageid="+mortgage.getId();
						List list4 = creditBaseDao.queryHql(hql4);
						if(list4 != null && list4.size()>0){
							ProcreditMortgageHouseground pmb = (ProcreditMortgageHouseground)list4.get(0);
							if(pmb.getPropertyperson()!= null){
								elementCode.setNewcqr_v(pmb.getPropertyperson());
							}
							if(pmb.getAddress()!= null){
								elementCode.setNewfdcdd_v(pmb.getAddress());
							}
							if(pmb.getAcreage()!= null){
								elementCode.setNewjzmj_v(pmb.getAcreage().toString()+"㎡");
							}
							if(pmb.getCertificatenumber()!= null){
								elementCode.setNewzjhm_v(pmb.getCertificatenumber());
							}
							if(pmb.getCertificatenumber()== null){
								pmb.setCertificatenumber(" ");
							}
							//反担保物详情
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==13){//工业用地
						String hql5 = "from ProcreditMortgageIndustry where mortgageid="+mortgage.getId();
						List list5 = creditBaseDao.queryHql(hql5);
						if(list5 != null && list5.size()>0){
							ProcreditMortgageIndustry pmb = (ProcreditMortgageIndustry)list5.get(0);
							if(pmb.getPropertyperson()!= null){
								elementCode.setNewcqr_v(pmb.getPropertyperson());
							}
							if(pmb.getAddress()!= null){
								elementCode.setNewfdcdd_v(pmb.getAddress());
							}
							if(pmb.getOccupyacreage()!= null){
								elementCode.setNewjzmj_v(pmb.getOccupyacreage().toString()+"㎡");
							}
							if(pmb.getCertificatenumber()!= null){
								elementCode.setNewzjhm_v(pmb.getCertificatenumber());
							}
							if(pmb.getCertificatenumber()== null){
								pmb.setCertificatenumber(" ");
							}
							//反担保物详情
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==8){//商铺写字楼
						String hql6 = "from ProcreditMortgageOfficebuilding where mortgageid="+mortgage.getId();
						List list6 = creditBaseDao.queryHql(hql6);
						if(list6 != null && list6.size()>0){
							ProcreditMortgageOfficebuilding pmb = (ProcreditMortgageOfficebuilding)list6.get(0);
							if(pmb.getPropertyperson()!= null){
								elementCode.setNewcqr_v(pmb.getPropertyperson());
							}
							if(pmb.getHouseaddress()!= null){
								elementCode.setNewfdcdd_v(pmb.getHouseaddress());
							}
							if(pmb.getBuildacreage()!= null){
								elementCode.setNewjzmj_v(pmb.getBuildacreage().toString()+"㎡");
							}
							if(pmb.getCertificatenumber()!= null){
								elementCode.setNewzjhm_v(pmb.getCertificatenumber());
							}
							if(pmb.getCertificatenumber()== null){
								pmb.setCertificatenumber(" ");
							}
							//反担保物详情
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下位于"+elementCode.getNewfdcdd_v()+elementCode.getNewjzmj_v()+"房产抵押（房权证号为："+pmb.getCertificatenumber()+"）作为抵押物\r\n");
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==1){//车辆
						String hql = "from VProjMortCar where mortgageid ="+mortgage.getId();
						List list = creditBaseDao.queryHql(hql);
						VProjMortCar pmb = null;
						if(list != null && list.size()>0){
							pmb = (VProjMortCar)list.get(0);
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下"+(pmb.getManufacturerValue()==null?"   ":pmb.getManufacturerValue())+"车辆抵押（发动机号码为："+(pmb.getEngineNo()==null?"   ":pmb.getEngineNo())+"）作为抵押物\r\n");
						}else{
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下     车辆抵押（发动机号码为：    ）作为抵押物\r\n");
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==5){//机械设备
						String hql = "from VProjMortMachineInfo where mortgageid="+mortgage.getId();
						VProjMortMachineInfo pmb = null;
						List list = creditBaseDao.queryHql(hql);
						if(list != null && list.size()>0){
							pmb = (VProjMortMachineInfo)list.get(0);
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下"+(pmb.getMachinename()==null?"   ":pmb.getMachinename())+"设备抵押（设备型号："+(pmb.getMachinetype()==null?"   ":pmb.getMachinetype())+"）作为抵押物\r\n");
						}else{
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下       设备抵押（设备型号：     ）作为抵押物\r\n");
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==6){//存货商品
						String hql = "from VProjMortProduct where mortgageid ="+mortgage.getId();
						VProjMortProduct pmb = null;
						List list = creditBaseDao.queryHql(hql);
						if(list != null && list.size()>0){
							pmb = (VProjMortProduct)list.get(0);
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下"+pmb.getName()+"存货商品（品牌："+(pmb.getBrand()==null?"   ":pmb.getBrand())+"）作为抵押");
						}else{
							fdbwxq.append(elementCode.getNewkhmc_v()+"名下       存货商品（品牌：     ）作为抵押");
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==14){//无形权利
						String hql = "from VProjMortDroit where mortgageid ="+mortgage.getId();
						VProjMortDroit pmb = null;
						List list = creditBaseDao.queryHql(hql);
						if(list != null && list.size()>0){
							pmb = (VProjMortDroit)list.get(0);
							fdbwxq.append(elementCode.getNewkhmc_v()+"把享受权利比重"+(pmb.getDroitpercent()==null?"   ":pmb.getDroitpercent())+"%作为抵押");
						}else{
							fdbwxq.append(elementCode.getNewkhmc_v()+"把享受权利比重        %作为抵押");
						}
					}
					elementCode.setNewfdbwxq_v(fdbwxq.toString());////反担保物详情
				}
				//目标公司名称
				if(mortgage.getEnterprisename()!= null){
					elementCode.setNewmbgsmc_v(mortgage.getEnterprisename());
				}
				//股权.%
				Double stockownershippercent = 0.0;
				if(mortgage.getId()!=null){
					String hql = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
					List list = creditBaseDao.queryHql(hql);
					if(list!= null && list.size()>0){
						VProjMortStockOwnerShip vs = (VProjMortStockOwnerShip)list.get(0);
						if(vs.getStockownershippercent()==null){
							stockownershippercent = 0.0;
						}else{
							stockownershippercent = vs.getStockownershippercent();
						}
						elementCode.setNewgqbfs_v(stockownershippercent.toString()+"%");
						//目标公司名称
						elementCode.setNewmbgsmc_v(vs.getEnterprisename());
						Object obj = null;
						obj = creditBaseDao.getById(Enterprise.class, vs.getCorporationname());
						if(obj != null){
							Enterprise en = (Enterprise)obj;
							//出质股权数额小写
							Double d = 0.0;
							if(en.getRegistermoney()!= null){
								d = en.getRegistermoney()*stockownershippercent/100;
							}
							elementCode.setNewczgqsexx_v(d.toString()+"万元");
						}
						
					}
					
				}
				//质押担保债权本金数额 
				if(mortgage.getId()!= null){
					String hql = "from VProcreditDictionary AS d where d.projid =? and d.businessType =?";
					Object [] obj = {Long.parseLong(proId),smallloanProject.getBusinessType()};
					List list = creditBaseDao.queryHql(hql,obj);
					Double shippercent = 0.0;
					int enterpId = 0;
					if(list!= null && list.size()>0 && list.size()>1){
						for(int i =0;i<list.size();i++){
							String hqlship = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
							List listship = creditBaseDao.queryHql(hqlship);
							if(listship!= null && listship.size()>0){
								VProjMortStockOwnerShip vs = (VProjMortStockOwnerShip)listship.get(0);
								Double shipt = 0.0;
								if(vs.getStockownershippercent()!= null){
									shipt = vs.getStockownershippercent();
								}
								shippercent = shipt;
								enterpId = vs.getCorporationname();
							}
							for(int j= 1;j<list.size();j++){
								String hqlship2 = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
								List listship2 = creditBaseDao.queryHql(hqlship2);
								if(listship2!= null && listship2.size()>0){
									VProjMortStockOwnerShip vs2 = (VProjMortStockOwnerShip)listship2.get(0);
									if(enterpId == vs2.getCorporationname()){
										Double shipt = 0.0;
										if(vs2.getStockownershippercent()!= null){
											shipt=vs2.getStockownershippercent();
										}
										shippercent += (shippercent + shipt);
									}
								}
							}
							
						}
					}else{
						shippercent = Double.valueOf("1");
					}
					if(shippercent.compareTo(0.0)>0){
						Double je = smallloanProject.getProjectMoney().doubleValue()*(stockownershippercent/shippercent)/100;
						elementCode.setNewzydbzqbjse_v(myFormatter.format(je)+"元");
					}
				}
			}
		}
		return elementCode;
	}*/
	
	/**
	 *  金额要素填充方法
	 *  add by liusl
	 */
	public void moneyCodeSetValue(SmallLoanElementCode smallLoanElementCode,BpFundProject bpFundProject,String bidPlanId){
		
		DecimalFormat df = new DecimalFormat("0.00");
		//风险保证金金额
		BigDecimal fxBZJSR = new BigDecimal(0);
		BigDecimal fxBZJZC = new BigDecimal(0);
		List<SlActualToCharge> listSATC = null ;
		if(bidPlanId!=null&&!"".equals(bidPlanId)){
			listSATC = slActualToChargeService.listbyProjectIdAndBidPlanId(bpFundProject.getProjectId(),bidPlanId);
		}else{
			listSATC = slActualToChargeService.listbyproject(bpFundProject.getProjectId());
		}
		
		for(SlActualToCharge slActualToCharge : listSATC){
			SlPlansToCharge slPlansToCharge = slPlansToChargeService.get(slActualToCharge.getPlanChargeId());
			if(slPlansToCharge!=null&&"风险保证金".equals(slPlansToCharge.getName())){
				if(slActualToCharge.getIncomeMoney()!=null){
					fxBZJSR = fxBZJSR.add(slActualToCharge.getIncomeMoney());
				}
				if(slActualToCharge.getPayMoney()!=null){
					fxBZJZC = fxBZJZC.add(slActualToCharge.getPayMoney());
				}
			}
		}
		
		fxBZJSR = fxBZJSR.subtract(fxBZJZC);   //风险保证金收入
		smallLoanElementCode.setFXBJZJEXX_v(fxBZJSR.setScale(2,RoundingMode.HALF_UP).toString());//小写
		
		if(fxBZJZC.compareTo(new BigDecimal(0))>0){
			String strfxBZJSR = MoneyFormat.getInstance().hangeToBig(fxBZJSR)+"元";
			smallLoanElementCode.setFXBJZJEDX_v(strfxBZJSR);//大写
		}
		if(bidPlanId!=null&&!"".equals(bidPlanId)){
			com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan plBidPlan = plBidPlanService.get(Long.valueOf(bidPlanId));
			BigDecimal fxBZJRate = fxBZJSR.divide(plBidPlan.getBidMoney(),10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP); //风险保证金比例
			smallLoanElementCode.setFXBZJBL_v(fxBZJRate.setScale(2,RoundingMode.HALF_UP)+"");//风险保证金比例
			//还款日
			if(bpFundProject.getPayintentPerioDate()!=null){
				smallLoanElementCode.setHKR_v(bpFundProject.getPayintentPerioDate()+"号");
			}else{
				smallLoanElementCode.setHKR_v(DateUtil.dateToStr(DateUtil.addDaysToDate(plBidPlan.getStartIntentDate(), -1), "dd")+"号");
			}
		}else{
			BigDecimal fxBZJRate = fxBZJSR.divide(bpFundProject.getProjectMoney(),10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP); //风险保证金比例
			smallLoanElementCode.setFXBZJBL_v(fxBZJRate.setScale(2,RoundingMode.HALF_UP)+"");//风险保证金比例
			
		}
		//还款期限
		smallLoanElementCode.setHKQS_v(bpFundProject.getPayintentPeriod()+"");
		/*//还款日
		if(bpFundProject.getPayintentPerioDate()!=null){
			smallLoanElementCode.setHKR_v(bpFundProject.getPayintentPerioDate()+"号");
		}else{
			smallLoanElementCode.setHKR_v(DateUtil.dateToStr(DateUtil.addDaysToDate(bpFundProject.getStartDate(), -1), "dd")+"号");
		}*/
		//管理咨询费率 ---年化
		smallLoanElementCode.setGLZXNHFL_v(bpFundProject.getYearManagementConsultingOfRate().setScale(2,RoundingMode.HALF_UP)+"%");
		//管理咨询费率 ---合计
		smallLoanElementCode.setGLZXHJFL_v(bpFundProject.getSumManagementConsultingOfRate().setScale(2,RoundingMode.HALF_UP)+"%");
		//财务服务费率 ---年化
		smallLoanElementCode.setCWFWNHFL_v(bpFundProject.getYearFinanceServiceOfRate().setScale(2,RoundingMode.HALF_UP)+"%");
		//财务服务费率 ---合计
		smallLoanElementCode.setCWFWHJFL_v(bpFundProject.getSumFinanceServiceOfRate().setScale(2,RoundingMode.HALF_UP)+"%");
		//借款年化利率
		smallLoanElementCode.setJKNHLL_v(bpFundProject.getYearAccrualRate().setScale(2,RoundingMode.HALF_UP)+"%");
		
		
		if(bidPlanId!=null&&!"".equals(bidPlanId)){
			
			//每期本金及利息
			BigDecimal mqchbjjlxse = new BigDecimal(0);
			//每期管理费
			BigDecimal mqjkglfse = new BigDecimal(0);
			//还款本息总额
			BigDecimal hkbxze = new BigDecimal(0);
			List fundList=bpFundIntentService.bidFundList(Long.valueOf(bidPlanId));
			if(null!=fundList && fundList.size()>0){
				Object[] obj=(Object[]) fundList.get(0);
				if(null!=obj[3]){
					mqjkglfse=mqjkglfse.add(new BigDecimal(obj[3].toString()));
				}
				if(null!=obj[1]){
					mqchbjjlxse=mqchbjjlxse.add(new BigDecimal(obj[1].toString()));
				}
				if(null!=obj[2]){
					mqchbjjlxse=mqchbjjlxse.add(new BigDecimal(obj[2].toString()));
				}
			}
			hkbxze=bpFundIntentService.getPrincipalAndInterest(Long.valueOf(bidPlanId));
			/*List<FundIntent> list = bpFundIntentService.getLoanPersonIntentList(bpFundProject.getId().toString(),Long.valueOf(bidPlanId));
			if(list!=null){
				for(int i =0 ; i < list.size() ; i++){
					FundIntent fundIntent = list.get(i);
					if(fundIntent.getPayintentPeriod()==1&&fundIntent.getFundType().equals("principalRepayment")){
						mqchbjjlxse = mqchbjjlxse.add(fundIntent.getIncomeMoney());
					}
					if(fundIntent.getPayintentPeriod()==1&&fundIntent.getFundType().equals("loanInterest")){
						mqchbjjlxse = mqchbjjlxse.add(fundIntent.getIncomeMoney());
					}
					if(fundIntent.getPayintentPeriod()==1&&fundIntent.getFundType().equals("consultationMoney")){
						mqjkglfse = mqjkglfse.add(fundIntent.getIncomeMoney());
					}
					if(fundIntent.getFundType().equals("principalRepayment")||fundIntent.getFundType().equals("loanInterest")){
						hkbxze = hkbxze.add(fundIntent.getIncomeMoney());
					}
					
				}
			}*/
			
			//每期本金及利息
			smallLoanElementCode.setMQCHBJJLXSEXX_v(mqchbjjlxse.setScale(2,RoundingMode.HALF_UP).toString()+"元"); //小写
			smallLoanElementCode.setMQCHBJJLXSEDX_v(MoneyFormat.getInstance().hangeToBig(mqchbjjlxse)); //大写
			
			//每期管理费
			smallLoanElementCode.setMQJKGLFSEXX_v(mqjkglfse.setScale(2,RoundingMode.HALF_UP).toString()+"元"); //小写
			smallLoanElementCode.setMQJKGLFSEDX_v(MoneyFormat.getInstance().hangeToBig(mqjkglfse)); //小写
			
			//还款本息总额
			smallLoanElementCode.setHKBXZE_v(hkbxze.setScale(2,RoundingMode.HALF_UP).toString());
			
			
		}
	//	return smallLoanElementCode;
	}
	
	
	
	//获得所有小额贷的系统要素
	@SuppressWarnings("unchecked")
	public SmallLoanElementCode getElementBySystem(String proId , Integer conId ,Integer tempId, String contractType,String rnum,String comments) throws Exception{
		DecimalFormat myFormatter = new DecimalFormat("####.#");
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdzw=new SimpleDateFormat("yyyy年MM月dd日");
		SmallLoanElementCode elementCode = new SmallLoanElementCode();
		BpFundProject smallloanProject =(BpFundProject)creditBaseDao.getById(BpFundProject.class, Long.parseLong(proId)) ; //项目信息实体
		if(null!=rnum && !rnum.equals("")){
			PlBidPlan plan=(PlBidPlan) creditBaseDao.getById(PlBidPlan.class, Long.valueOf(rnum));
			elementCode.setBCZBHKR_v(null!=plan.getEndIntentDate()?sd.format(plan.getEndIntentDate()):null);
			elementCode.setBCZBJE_v(null!=plan.getBidMoney()?plan.getBidMoney().setScale(2, BigDecimal.ROUND_HALF_UP).toString():null);
			elementCode.setBCZBQXR_v(null!=plan.getStartIntentDate()?sd.format(plan.getStartIntentDate()):null);
			elementCode.setBCZBXMMC_v(plan.getBidProName());
		}
		moneyCodeSetValue(elementCode, smallloanProject,rnum);
		
		//EnterpriseView enterv = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID().intValue());
		
		//ProcreditContractCategory contractCategory = (ProcreditContractCategory)creditBaseDao.getById(ProcreditContractCategory.class, conId);
		/**
		 * 合同编号
		 * 合同文档名称：客户简称+项目编号+合同名称+01(同类合同依次相加)
		 * 合同编号：项目编号 +合同特定代表（如1）+01(同类合同依次相加)
		 * 合同类型代表：借款合同1，保证合同2，抵押合同3，权利质押合同4，财务协议5，股东会决议6
		 * */
		//合同编号
		ProcreditContract contract=null;
		if(null!=conId&&smallloanProject.getProjectNumber() != null){
			 contract = (ProcreditContract)creditBaseDao.getById(ProcreditContract.class, conId);
			elementCode.setJkhtbh_v(contract.getContractNumber());
			elementCode.setBzdbhtbh_v(contract.getContractNumber());
			elementCode.setJjbh_v(contract.getContractNumber());
			/*String projectNumber = smallloanProject.getProjectNumber();//项目编号
			projectNumber.replaceAll("_","-");
			int count = 0;
			if(!StringUtil.isNumeric(rnum)){
				rnum = "0";
			}
			count = Integer.parseInt(rnum);*/
			/*if(count <10){
				elementCode.setJkhtbh_v("LA-"+projectNumber);
				elementCode.setBzdbhtbh_v("SA-"+projectNumber+"-0"+count);
				elementCode.setJjbh_v("LR"+projectNumber+"-0"+count);
				elementCode.setJkhtbh_v(projectNumber+"-1"+"-0"+count);//借款合同编号
				elementCode.setBzdbhtbh_v(projectNumber+"-2"+"-0"+count);//保证合同编号
				elementCode.setDydbhtbh_v(projectNumber+"-4"+"-0"+count);//抵押合同编号
				elementCode.setZydbhtbh_v(projectNumber+"-5"+"-0"+count);//质押合同编号
			}else{
				elementCode.setJkhtbh_v("LA-"+projectNumber);
				elementCode.setBzdbhtbh_v("SA-"+projectNumber+"-"+count);
				elementCode.setJjbh_v("LR"+projectNumber+"-"+count);
				elementCode.setJkhtbh_v(projectNumber+"-1"+"-"+count);//借款合同编号
				elementCode.setBzdbhtbh_v(projectNumber+"-2"+"-"+count);//保证合同编号
				elementCode.setDydbhtbh_v(projectNumber+"-4"+"-"+count);//抵押合同编号
				elementCode.setZydbhtbh_v(projectNumber+"-5"+"-"+count);//质押合同编号
			}*/
		}
		//债权人相关信息
		if(null!=smallloanProject.getMineId()){
			Organization organization = organizationDao.get(smallloanProject.getMineId());
			if(null!=organization){
				if(organization.getOrgName() != null) {
					elementCode.setZqrmc_v(organization.getOrgName());
				}
				if(organization.getAddress() != null) {
					elementCode.setZqrdz_v(organization.getAddress());
				}
				if(organization.getPostCode() != null) {
					elementCode.setZqryb_v(organization.getPostCode());
				}
				if(organization.getLinktel() != null) {
					elementCode.setZqrdh_v(organization.getLinktel());
				}
				if(organization.getFax() != null) {
					elementCode.setZqrcz_v(organization.getFax());
				}
				if(organization.getLinkman()!=null){
					elementCode.setZqffddbr_v(organization.getLinkman());
				}
			}
		}
		/*
		 * 项目表相关信息
		 * 贷款金额，币种，贷款利率，贷款起始日，贷款结束日等等
		 * */
		//币种
		if(smallloanProject.getCurrency() != null){
			Dictionary dictionary = null;
			dictionary = (Dictionary)creditBaseDao.getById(Dictionary.class, smallloanProject.getCurrency());
			if(dictionary != null){
				elementCode.setBz_v(dictionary.getItemValue());
			}
		}
		//贷款利率
		if(smallloanProject.getAccrual()!=null){
			elementCode.setDkyll_v(smallloanProject.getAccrual().toString() + "%");
		}
	
		//贷款本金金额大小写
		if(smallloanProject.getProjectMoney()!=null){
			BigDecimal projectMoney = smallloanProject.getProjectMoney();
			String dw = "元整";
			elementCode.setDkjexx_v(myFormatter.format(projectMoney).toString()+"元");
			elementCode.setDkjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
		}
		//还款固定日
		if(smallloanProject.getPayintentPerioDate()!=null&&smallloanProject.getIsStartDatePay()!=null&&smallloanProject.getIsStartDatePay().equals("1")){
			elementCode.setHkgdr_v(smallloanProject.getPayintentPerioDate()-1+"日");
		}
		//月还款本息金额大小写
		if(smallloanProject.getProjectMoney()!=null&&smallloanProject.getPayintentPeriod()!=null&&smallloanProject.getAccrual()!=null&smallloanProject.getPayaccrualType()!=null&&smallloanProject.getPayaccrualType().equals("monthPay")&&smallloanProject.getAccrualtype()!=null&&smallloanProject.getAccrualtype().equals("sameprincipalsameInterest")){
			BigDecimal period = new BigDecimal(smallloanProject.getPayintentPeriod()).setScale(2);
			BigDecimal monthpayMoney = smallloanProject.getProjectMoney().divide(period,2,BigDecimal.ROUND_HALF_UP).add(smallloanProject.getProjectMoney().multiply(smallloanProject.getAccrual()).divide(new BigDecimal(100)));
			BigDecimal monthpayMoneyRoundUp = monthpayMoney.setScale(0, BigDecimal.ROUND_UP);
			elementCode.setYhbxyqwyj_v(((monthpayMoneyRoundUp.divide(new BigDecimal(10))).compareTo(new BigDecimal(100))>0?monthpayMoneyRoundUp.divide(new BigDecimal(10)).setScale(2, BigDecimal.ROUND_UP):new BigDecimal(100)).toString());//违约金金额
			elementCode.setYhbxytfx_v(monthpayMoneyRoundUp.multiply(new BigDecimal(0.0075)).setScale(2, BigDecimal.ROUND_UP).toString());
			elementCode.setYhbxstfx_v(monthpayMoneyRoundUp.multiply(new BigDecimal(0.075)).setScale(2, BigDecimal.ROUND_UP).toString());
			BigDecimal monthpayDb = monthpayMoney.setScale(0, BigDecimal.ROUND_UP);
			elementCode.setYhbxjedx_v(MoneyFormat.getInstance().hangeToBig(monthpayDb)+"元");
			elementCode.setYhbxjexx_v(monthpayDb.toString()+"元");
		}
		//贷款本金金额(余额)大小写
		BigDecimal yueMoney = new BigDecimal("0");
		if(smallloanProject.getProjectMoney()!=null && smallloanProject.getPayProjectMoney() != null){
			BigDecimal projectMoney = smallloanProject.getProjectMoney();
			BigDecimal payProjectMoney = smallloanProject.getPayProjectMoney();
			yueMoney = projectMoney.subtract(payProjectMoney);
			String dw = "元整";
			elementCode.setDkjexx_v(myFormatter.format(projectMoney).toString()+"元");
			elementCode.setDkjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
			elementCode.setJkyedx_v(yueMoney.toString()+"元");
			elementCode.setJkyedx_v(MoneyFormat.getInstance().hangeToBig(yueMoney)+dw);
		}
//add by gao start 	
		//所有还款款项   计算各期的还款金额
		List<SlFundIntent> fundPayList=slFundIntentDao.getByProjectId4( Long.valueOf(proId), "SmallLoan");
		Collections.sort(fundPayList,new FundIntentComparator());
		StringBuffer sb = new StringBuffer();
		int period = -1;
		BigDecimal tmpPeriodMoney = BigDecimal.ZERO;
		Date tmpPeriodDate = null;
		BigDecimal cwfwfMoney = BigDecimal.ZERO;
		String tempStr = "期数∏还款时间∏还款金额（元）";
		for(SlFundIntent sfi : fundPayList){
			if(sfi.getPayintentPeriod()==null)continue;
			if(sfi.getPayintentPeriod()!=period||fundPayList.indexOf(sfi)==(fundPayList.size()-1)){//期数变了，tempMoney 清零，StringBuffer加@
				if(period<0){
					sb.append(tempStr);
					period = sfi.getPayintentPeriod();
				}else{
					if(period==1&&elementCode.getHkqsr_v().equals(""))elementCode.setHkqsr_v(sdzw.format(tmpPeriodDate));
					if(fundPayList.indexOf(sfi)==(fundPayList.size()-1)){
						elementCode.setHkjzr_v(sdzw.format(sfi.getIntentDate()));
						tmpPeriodMoney = tmpPeriodMoney.add(sfi.getIncomeMoney());
						tmpPeriodDate = sfi.getIntentDate();
					}
					if(tmpPeriodMoney.compareTo(BigDecimal.ZERO)!=0){
						sb.append("＃"+period+"∏"+sd.format(tmpPeriodDate)+"∏"+tmpPeriodMoney.setScale(0,BigDecimal.ROUND_UP));//逢位进一
					}
					period = sfi.getPayintentPeriod();
					tmpPeriodMoney=sfi.getIncomeMoney();
				}
			}else{
				tmpPeriodMoney = tmpPeriodMoney.add(sfi.getIncomeMoney());
				tmpPeriodDate = sfi.getIntentDate();
			}
			if(sfi.getFundType().equals("serviceMoney"))cwfwfMoney = cwfwfMoney.add(sfi.getIncomeMoney());
		}
		elementCode.setCwfwf_v(cwfwfMoney.setScale(0,BigDecimal.ROUND_UP).toString());
		elementCode.setCwfwfdx_v(MoneyFormat.getInstance().hangeToBig(cwfwfMoney.setScale(0,BigDecimal.ROUND_UP)));
		elementCode.setHklb_v(sb.toString());
		
		//共同借款人
		StringBuffer sb2 = new StringBuffer();//大连天储借款人信息
		StringBuffer sb3 = new StringBuffer();//大连天储借款人名称
		//企业   股东信息
		if(smallloanProject.getOppositeType().equals("company_customer")&&smallloanProject.getOppositeID()!=null){
			EnterpriseView enterprise = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID().intValue());
			sb2.append(enterprise.toElementStr());
			sb3.append(enterprise.getEnterprisename());
			List<EnterpriseShareequity> shareequityList = enterpriseShareequityDao.findShareequityList(smallloanProject.getOppositeID().intValue());
			if(shareequityList !=null&&shareequityList.size()>0){
				for(EnterpriseShareequity es : shareequityList){
					if(es.getPersonid()!=null){
						Person person = personDao.getById(es.getPersonid());
						sb2.append(person.toElementStr());
						sb3.append(","+person.getName());
					}else if(es.getEnterpriseid()!=null){
						Enterprise enterTemp = enterpriseService.getById(es.getEnterpriseid());
						sb2.append(enterTemp.toElementStr());
						sb3.append(","+enterTemp.getEnterprisename());
					}
				}
			}
		}else{
			Person person = personDao.getById(smallloanProject.getOppositeID().intValue());
			sb2.append(person.toElementStr());
			sb3.append(person.getName());
			if(person.getMarry()==317){
				Spouse spouse = spouseDao.getByPersonId(person.getId());
				if(spouse!=null){
					sb2.append(spouse.toElementStr());
					sb3.append(","+spouse.getName());
				}
			}
		}
		List<BorrowerInfo> borrowerinfoList = borrowerInfoDao.getBorrowerListDetail(smallloanProject.getProjectId());
		if(borrowerinfoList!=null&&borrowerinfoList.size()>0){
			for(BorrowerInfo bi : borrowerinfoList){
				sb2.append(bi.toElementStr());
				sb3.append(","+bi.getCustomerName());
			}
		}
		elementCode.setDltcjkrmc_v(sb3.toString());
		elementCode.setDltcjkrxx_v(sb2.toString());
//add by gao end 
		
		List<SlFundIntent> listloanInterest1=new ArrayList<SlFundIntent>();
		listloanInterest1=slFundIntentDao.listbyOwe("SmallLoan", Long.valueOf(proId), "('loanInterest')");
		BigDecimal  loanInterestMoney=new BigDecimal("0");
		BigDecimal  principalMoney=new BigDecimal("0");
		BigDecimal  punishMoney=new BigDecimal("0");
		BigDecimal  sumMoney=new BigDecimal("0");
		List<SlFundIntent>	listprincipalt=slFundIntentDao.listbyOwe("SmallLoan", Long.valueOf(proId), "('principalRepayment')");
		List<SlFundIntent> listloanInterest=slFundIntentDao.listbyOwe("SmallLoan", Long.valueOf(proId), "('loanInterest','consultationMoney','serviceMoney')");
		for(SlFundIntent inte:listloanInterest){
			loanInterestMoney=loanInterestMoney.add(inte.getNotMoney());
			List<SlPunishInterest> listpunish=slPunishInterestDao.listbyisInitialorId(inte.getFundIntentId());
			for(SlPunishInterest pun:listpunish){
				punishMoney=punishMoney.add(pun.getNotMoney());
				
			}
		}
		for(SlFundIntent pri:listprincipalt){
			principalMoney=principalMoney.add(pri.getNotMoney());
			List<SlPunishInterest> listpunish=slPunishInterestDao.listbyisInitialorId(pri.getFundIntentId());
			for(SlPunishInterest pun:listpunish){
				punishMoney=punishMoney.add(pun.getNotMoney());
				
			}	
		}
		sumMoney=loanInterestMoney.add(principalMoney).add(punishMoney).add(yueMoney);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		String str = simpleDateFormat.format(new Date());
		//截止日期
		elementCode.setJzrq_v(simpleDateFormat.format(new Date()));
		//利息余额大小写
		elementCode.setLxyedx_v(loanInterestMoney.doubleValue()==0?"零元整":MoneyFormat.getInstance().hangeToBig(loanInterestMoney)+"元整");
		elementCode.setLxyexx_v(loanInterestMoney.doubleValue()+"元");
		//罚息余额大小写
		elementCode.setFxyedx_v(punishMoney.doubleValue()==0?"零元整":MoneyFormat.getInstance().hangeToBig(punishMoney)+"元整");
		elementCode.setFxyexx_v(punishMoney.doubleValue()+"元");
		//合计余额大小写
		elementCode.setHjyedx_v(sumMoney.doubleValue()==0?"零元整":MoneyFormat.getInstance().hangeToBig(sumMoney)+"元整");
		elementCode.setHjyexx_v(sumMoney.toString()+"元");
		
		//借款用途
		if(smallloanProject.getPurposeType()!= null){
			Dictionary dictionary = null;
			String purposeTypeStr = "";
			dictionary = (Dictionary)creditBaseDao.getById(Dictionary.class, smallloanProject.getPurposeType());
			if(dictionary != null){
				purposeTypeStr = dictionary.getItemValue();
				elementCode.setJkyt_v(purposeTypeStr);
			}
		}
		/*if(smallloanProject.getPurposeTypeStr()!=null){
			String purposeTypeStr = smallloanProject.getPurposeTypeStr();
			elementCode.setJkyt_v(purposeTypeStr);
		}*/
		//贷款起始日期
		if(smallloanProject.getStartDate()!= null){
		//	String startDateStr = smallloanProject.getStartDate().toString().substring(0,smallloanProject.getStartDate().toString().lastIndexOf(" "));
			elementCode.setDkqsrq_v(sd.format(smallloanProject.getStartDate()));
		}
		

		if(null != smallloanProject.getMineType() && !"".equals(smallloanProject.getMineType())){
			if(smallloanProject.getMineType().equals("person_ourmain")){//我方主体为个人
				SlPersonMain personMain = null;
				if(smallloanProject.getMineId()!=0){
					personMain = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, smallloanProject.getMineId());
					if(personMain != null){
						if(personMain.getName()!=null){
							elementCode.setZqrmc_v(personMain.getName());
						}
						if(personMain.getAddress()!=null){
							elementCode.setZqrdz_v(personMain.getAddress());
						}
						if(personMain.getPostalCode()!= null){
							elementCode.setZqryb_v(personMain.getPostalCode());//债权人邮政编码
						}
						if(personMain.getLinktel()!=null){
							elementCode.setZqrdh_v(personMain.getLinktel());
						}
						if(personMain.getTax()!= null){
							elementCode.setZqrcz_v(personMain.getTax());//债权人传真号码
						}	
						if(personMain.getCardnum()!=null){
							elementCode.setWfztzjh_v(personMain.getCardnum());
						}
					}
				}
				
			}else if(smallloanProject.getMineType().equals("company_ourmain")){//我方主体为企业
				SlCompanyMain companyMain = null;
				if(smallloanProject.getMineId()!=0){
					companyMain = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, smallloanProject.getMineId());
					if(companyMain != null){
						if(companyMain.getCorName()!= null){
							elementCode.setZqrmc_v(companyMain.getCorName());
						}
						if(companyMain.getMessageAddress() != null){
							elementCode.setZqrdz_v(companyMain.getMessageAddress());
						}
						if(companyMain.getPostalCode()!= null){
							elementCode.setZqryb_v(companyMain.getPostalCode());//债权公司邮政编码
						}
						if(companyMain.getTel()!= null){
							elementCode.setZqrdh_v(companyMain.getTel());
						}
						if(companyMain.getTax()!= null){
							elementCode.setZqrcz_v(companyMain.getTax());
						}
						if(companyMain.getLawName()!=null){
							elementCode.setZqffddbr_v(companyMain.getLawName());//债权方法定代表人
						}
						elementCode.setWfztzjh_v(companyMain.getBusinessCode());
					}
				}
			}
		}

		/*if(smallloanProject.getEndDate()!= null){
			String endDateStr = smallloanProject.getEndDate().toString().substring(0,smallloanProject.getEndDate().toString().lastIndexOf(" "));
			elementCode.setDkdqrq_v(MoneyFormat.getInstance().formatDate(endDateStr));
		}*/
		//贷款期限(月)
		if(smallloanProject.getStartDate()!= null) {
			if(null!=smallloanProject.getPayaccrualType()){
				if(smallloanProject.getPayaccrualType().equals("monthPay")){
					if(null!=smallloanProject.getPayintentPeriod()){
						 elementCode.setDkqx_v(smallloanProject.getPayintentPeriod()+"个月");
					}
				}else if(smallloanProject.getPayaccrualType().equals("seasonPay")){
					if(null!=smallloanProject.getPayintentPeriod()){
						 elementCode.setDkqx_v(smallloanProject.getPayintentPeriod()*3+"个月");
					}
				}else if(smallloanProject.getPayaccrualType().equals("yearPay")){
					if(null!=smallloanProject.getPayintentPeriod()){
						 elementCode.setDkqx_v(smallloanProject.getPayintentPeriod()*12+"个月");
					}
				}else if(smallloanProject.getPayaccrualType().equals("dayPay")){
					if(null!=smallloanProject.getPayintentPeriod()){
						 elementCode.setDkqx_v(smallloanProject.getPayintentPeriod()+"天");
					}
				}else if(smallloanProject.getPayaccrualType().equals("owerPay")){
					if(null!=smallloanProject.getDayOfEveryPeriod() && null!=smallloanProject.getPayintentPeriod()){
						 elementCode.setDkqx_v(smallloanProject.getPayintentPeriod()*smallloanProject.getDayOfEveryPeriod()+"天");
					}
				}
			}
			//贷款到期日期
			if(!smallloanProject.getAccrualtype().equals("ontTimeAccrual")){
				if(smallloanProject.getPayaccrualType().equals("monthPay")){
					Date intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(smallloanProject.getStartDate(), smallloanProject.getPayintentPeriod()), -1);
					elementCode.setDkdqrq_v(sd.format(intentDate));
				}else if(smallloanProject.getPayaccrualType().equals("monthPay")){
					Date intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(smallloanProject.getStartDate(), smallloanProject.getPayintentPeriod()*3), -1);
					elementCode.setDkdqrq_v(sd.format(intentDate));
				}else if(smallloanProject.getPayaccrualType().equals("yearPay")){
					Date intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(smallloanProject.getStartDate(), smallloanProject.getPayintentPeriod()*12), -1);
					elementCode.setDkdqrq_v(sd.format(intentDate));
				}else if(smallloanProject.getPayaccrualType().equals("dayPay")){
					Date intentDate=DateUtil.addDaysToDate(smallloanProject.getStartDate(), smallloanProject.getPayintentPeriod()-1);
					elementCode.setDkdqrq_v(sd.format(intentDate));
				}else if(smallloanProject.getPayaccrualType().equals("owerPay")){
					Date intentDate=DateUtil.addDaysToDate(smallloanProject.getStartDate(), smallloanProject.getPayintentPeriod()*smallloanProject.getDayOfEveryPeriod()-1);
					elementCode.setDkdqrq_v(sd.format(intentDate));
				}
			}else{
				elementCode.setDkdqrq_v(sd.format(smallloanProject.getIntentDate()));
			}
		/*	Date d1 = smallloanProject.getStartDate();
			Date d2 = smallloanProject.getIntentDate();
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar c = Calendar.getInstance();
	        c.setTime(d1);
	        int year1 = c.get(Calendar.YEAR);
	        int month1 = c.get(Calendar.MONTH);
	         
	        c.setTime(d2);
	        int year2 = c.get(Calendar.YEAR);
	        int month2 = c.get(Calendar.MONTH);
	         
	        Integer result;
	        if(year1 == year2) {
	            result = month1 - month2;
	        } else if(year1 > year2){
	            result = 12*(year1 - year2) + month1 - month2;
	        }else{
	        	result = 12*(year2 - year1) + month1 - month2;
	        }
			
	        elementCode.setDkqx_v(result.toString());*/
		}
		//还款方式
		if(smallloanProject.getAccrualtype() != null) {
			String accrualtypeValue = "";
			if("sameprincipal".equals(smallloanProject.getAccrualtype())) {
				accrualtypeValue = "等额本金";
			}else if ("sameprincipalandInterest".equals(smallloanProject.getAccrualtype())){
				accrualtypeValue = "等额本息";
			}else if ("singleInterest".equals(smallloanProject.getAccrualtype())){
				accrualtypeValue = "按期收息,到期还本";
			}else if ("ontTimeAccrual".equals(smallloanProject.getAccrualtype())){
				accrualtypeValue = "一次性还本付息";
			}
			elementCode.setHkfs_v(accrualtypeValue);
		}
		if(null!=smallloanProject.getCompanyId()){
			Organization org=organizationDao.get(smallloanProject.getCompanyId());
			elementCode.setWfzt_v(org.getOrgName());
		}
		if(null!=smallloanProject.getAppUserId()){
			String[] ids=smallloanProject.getAppUserId().split(",");
			String name="";
			for(int i=0;i<ids.length;i++){
				AppUser user=appUserDao.get(Long.valueOf(ids[i]));
				name=user.getFullname()+",";
			}
			name=name.substring(0,name.length()-1);
			elementCode.setXdy_v(name);
		}
		//担保方式
		VSmallloanProject vp = vSmallloanProjectDao.getByProjectId(Long.valueOf(proId));
		if(vp!=null){
			if(vp.getAssuretypeidValue()!=null) {
				elementCode.setDbfs_v(vp.getAssuretypeidValue());
			}
		}
		
		if(null!=smallloanProject.getProjectId()){
			List<SlAlterAccrualRecord> list=slAlterAccrualRecordDao.getByProjectId(smallloanProject.getProjectId(),"SmallLoan");//◎
			if(null!=list && list.size()>0){
				SlAlterAccrualRecord record=list.get(0);
				if(null!=record.getAccrual()){
					elementCode.setNbgyll_v(record.getAccrual().toString());
				}
			}
			List<SlEarlyRepaymentRecord> elist=slEarlyRepaymentRecordDao.getByProjectId(smallloanProject.getProjectId());
			if(null!=elist && elist.size()>0){
				SlEarlyRepaymentRecord slEarlyRepaymentRecord=elist.get(0);
				if(slEarlyRepaymentRecord.getEarlyProjectMoney().compareTo(smallloanProject.getProjectMoney())==0){
					elementCode.setTqhkzl_v("全部提前结清");
				}else{
					elementCode.setTqhkzl_v("部分提前还款");
				}
			}
		}
/*		ProcessRun pr = processRunDao.getByBusinessTypeProjectId(Long.valueOf(proId), "SmallLoan");
		ProcessForm pf = processFormDao.getByRunIdActivityName(pr.getRunId(), "尽职调查");
		ProcessForm pf1 = processFormDao.getByRunIdActivityName(pr.getRunId(), "风险审核");
		ProcessForm pf2 = processFormDao.getByRunIdActivityName(pr.getRunId(), "贷款审查");
		ProcessForm pf3 = processFormDao.getByRunIdActivityName(pr.getRunId(), "放款审查");
		ProcessForm pf4 = processFormDao.getByRunIdActivityName(pr.getRunId(), "放款签批");
		ProcessForm pf5 = processFormDao.getByRunIdActivityName(pr.getRunId(), "提前还款申请");
		ProcessForm pf6 = processFormDao.getByRunIdActivityName(pr.getRunId(), "提前还款审批");
		ProcessForm pf7 = processFormDao.getByRunIdActivityName(pr.getRunId(), "利率变更申请");
		ProcessForm pf8 = processFormDao.getByRunIdActivityName(pr.getRunId(), "利率变更审核");
		ProcessForm pf9 = processFormDao.getByRunIdActivityName(pr.getRunId(), "利率变更审查");
		ProcessForm pf10 = processFormDao.getByRunIdActivityName(pr.getRunId(), "利率变更审批");
		ProcessForm pf11 = processFormDao.getByRunIdActivityName(pr.getRunId(), "确认终审通过意见");
		//业务经理意见
		if(null!=pf && pf.getComments() != null) {
			elementCode.setYwjlyj_v(pf.getComments());
		}
		//风险经理意见
		if(null!=pf1 && pf1.getComments() != null) {
			elementCode.setFxjlyj_v(pf1.getComments());
		}
		if(null!=pf2 && null!=pf2.getComments()){
			elementCode.setZjlyj_v(pf2.getComments());
		}
		if(null!=pf3 && null!=pf3.getComments()){
			elementCode.setFxfkscyj_v(pf3.getComments());
		}
		if(null!=comments){
			elementCode.setZjlfkqpyj_v(comments);

		}else{
			if(null!=pf4 && null!=pf4.getComments()){
				elementCode.setZjlfkqpyj_v(pf4.getComments());
			}
		}
		if(null!=pf5 && null!=pf5.getComments()){
			elementCode.setYwjltqhksqyj_v(pf5.getComments());
		}
		if(null!=pf6 && null!=pf6.getComments()){
			elementCode.setZjltqhkspyj_v(pf6.getComments());
		}
		if(null!=pf7 && null!=pf7.getComments()){
			elementCode.setYwjlllbgsqyj_v(pf7.getComments());
		}
		if(null!=pf8 && null!=pf8.getComments()){
			elementCode.setZjlllbgshyj_v(pf8.getComments());
		}
		if(null!=pf9 && null!=pf9.getComments()){
			elementCode.setKgllbgshyj_v(pf9.getComments());
		}
		if(null!=pf10 && null!=pf10.getComments()){
			elementCode.setKgllbgspyj_v(pf10.getComments());
		}
		if(null!=pf11 && null!=pf11.getComments()){
			elementCode.setZsyj_v(pf11.getComments());
		}*/
		
		//服务费用总额（大写）
		if(smallloanProject.getFinanceServiceOfRate()!= null && smallloanProject.getProjectMoney()!= null){
			//Double serviceMoney = (smallloanProject.getFinanceServiceOfRate().divide(BigDecimal.valueOf(100)).multiply(smallloanProject.getProjectMoney())).doubleValue();
			if(smallloanProject.getFinanceServiceOfRate().compareTo(BigDecimal.ZERO)!=0 ){
				String fundType = "";
				if(smallloanProject.getBusinessType().equals("SmallLoan")){
					fundType = "serviceMoney";
				}else{
					fundType = "financingserviceMoney";
				}
				Double serviceMoney = 0.0;
				List listMoney = null;
				Object [] obje = {smallloanProject.getProjectId(),smallloanProject.getBusinessType(),fundType};
				String hqlm ="from SlFundIntent where projectId = ? and businessType =? and fundType = ?";
				listMoney = creditBaseDao.queryHql(hqlm,obje);
				if(listMoney!= null && listMoney.size()>0){
					for(int i=0;i<listMoney.size();i++){
						SlFundIntent slFundIntent = (SlFundIntent)listMoney.get(i);
						serviceMoney += slFundIntent.getIncomeMoney().doubleValue();
					}
				}
				//服务费用总额（大写）
				//elementCode.setFwfyjedx_v(MoneyFormat.getInstance().hangeToBig(serviceMoney)+"元");
				//多笔支付款项内容(服务费列表)
				
				List<SlFundIntent> list = null;
				String hql = "from SlFundIntent AS f where f.projectId =? and f.fundType =? and f.businessType = ?";
				Object [] param ={Long.parseLong(proId),fundType,smallloanProject.getBusinessType()};
				list = creditBaseDao.queryHql(hql, param);
				if(list != null && list.size()>0){
					StringBuffer dbzfkxnrBuffer = new StringBuffer();
					String dbzfkxnr = "";
					for(int i = 0;i < list.size(); i++){
						SlFundIntent fi = (SlFundIntent)list.get(i);
						String intentDateStr = fi.getIntentDate().toString().substring(0,fi.getIntentDate().toString().lastIndexOf(" "));
						if(fundType == "serviceMoney" || "serviceMoney".equals(fundType)){
							dbzfkxnr = "费用支付时间："+MoneyFormat.getInstance().formatDate(intentDateStr)+"      "+"支付金额："+myFormatter.format(fi.getIncomeMoney())+"元\r\n";
						}else{
							dbzfkxnr = "费用支付时间："+MoneyFormat.getInstance().formatDate(intentDateStr)+"      "+"支付金额："+myFormatter.format(fi.getPayMoney())+"元\r\n";
						}
						dbzfkxnrBuffer.append(dbzfkxnr);
						
					}
//					elementCode.setDbzfkxnr_v(dbzfkxnrBuffer.toString());
				}
				
			}else{
//				elementCode.setFwfyjedx_v("零元");
			}
			
			
		}
		
		/*
		 * 客户类型-债务人
		 * 个人person_customer，企业company_customer
		 * */
		if(smallloanProject.getOppositeType().equals("person_customer")){
			//VPersonDic person = (VPersonDic)creditBaseDao.getById(VPersonDic.class, smallloanProject.getOppositeID().intValue());
			Person person=(Person) creditBaseDao.getById(Person.class, smallloanProject.getOppositeID().intValue());
			if(person.getName()!=null){
				elementCode.setJkrmc_v(person.getName());//甲方（借款人名称）
			}
			if(person.getCardtypevalue() != null) {
				elementCode.setJkrzjlx_v(person.getCardtypevalue().toString());
			}
			if(person.getCardnumber()!=null){
				elementCode.setJkrzjhm_v(person.getCardnumber());//借款证件号码
			}
			if(person.getPostaddress() != null){
				elementCode.setJkrdz_v(person.getPostaddress());
			}
			if(person.getCellphone()!= null){
				elementCode.setJkrlxdh_v(person.getCellphone());
			}
			if(person.getTelphone()!= null){
				elementCode.setJkrgddh_v(person.getTelphone());
			}
			if(person.getPostcode() != null){
				elementCode.setJkryzmb_v(person.getPostcode());
			}
			if(person.getFax() != null){
				elementCode.setJkrcz_v(person.getFax());
			}
			if(person.getSelfemail() != null) {
				elementCode.setJkrdzyx_v(person.getSelfemail());
			}
/*			if(smallloanProject.getBankId()!=null){
				EnterpriseBank epv = enterpriseBankDao.getById(smallloanProject.getBankId().intValue());
				if(epv != null) {
					if(epv.getName() != null) {
						elementCode.setJkrzhmc_v(epv.getName());
					}
					if(epv.getAccountnum() != null) {
						elementCode.setJkryhzh_v(epv.getAccountnum());
					}
					if(epv.getBankid() != null) {
						CsBank cb = csBankDao.get(epv.getBankid());
						if(cb!=null){
							elementCode.setJkrkhyh_v(cb.getBankname());
							if(epv.getBankOutletsName()!=null)elementCode.setJkrkhyh_v(cb.getBankname()+"_"+epv.getBankOutletsName());
							
						}
					}
				}
			}else{*/
				EnterpriseBank epv = enterpriseBankDao.queryIscredit(person.getId(), Short.valueOf("1"), Short.valueOf("0"));
				if(epv != null) {
					if(epv.getName() != null) {
						elementCode.setJkrzhmc_v(epv.getName());
					}
					if(epv.getAccountnum() != null) {
						elementCode.setJkryhzh_v(epv.getAccountnum());
					}
					if(epv.getBankid() != null) {
						CsBank cb = csBankDao.get(epv.getBankid());
						if(cb!=null){
							elementCode.setJkrkhyh_v(cb.getBankname());
						}
					}
				}
			//}
			if(person.getFamilyaddress()!=null){
				elementCode.setJkrdz_v(person.getFamilyaddress());
			}
			if(person.getPostaddress()!=null){
				elementCode.setJkrtxdz_v(person.getPostaddress());
			}
		}else if(smallloanProject.getOppositeType().equals("company_customer")){
			EnterpriseView enterprise = null;
			enterprise = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID().intValue());
			if(enterprise.getEnterprisename()!= null){
				elementCode.setJkrmc_v(enterprise.getEnterprisename());
			}
			if(enterprise.getLegalperson()!= null){
				elementCode.setJkffddbr_v(enterprise.getLegalperson());//借款方法定代表人
			}
			if(enterprise.getCciaa() != null){
				elementCode.setJkryyzzdm_v(enterprise.getCciaa());//借款方营业执照号码
				elementCode.setJkrzjhm_v(enterprise.getCciaa());
			}
			if(enterprise.getOrganizecode() != null){
				elementCode.setJkrzzjgdm_v(enterprise.getOrganizecode());//借款方组织机构代码
			}
			if(enterprise.getArea()!= null){
				elementCode.setJkrdz_v(enterprise.getArea());
			}
			if(enterprise.getTelephone() != null){
				elementCode.setJkrlxdh_v(enterprise.getTelephone());//借款方公司联系电话
			}
			if(enterprise.getFax()!= null){
				elementCode.setJkrcz_v(enterprise.getFax());
			}
			if(enterprise.getPostcoding() != null) {
				elementCode.setJkryzmb_v(enterprise.getPostcoding());
			}
			if(enterprise.getEmail() != null) {
				elementCode.setJkrdzyx_v(enterprise.getEmail());
			}
			EnterpriseBank epv = enterpriseBankDao.queryIscredit(enterprise.getId(), Short.valueOf("0"), Short.valueOf("0"));
			if(epv != null) {
				if(epv.getName() != null) {
					elementCode.setJkrzhmc_v(epv.getName());
				}
				if(epv.getAccountnum() != null) {
					elementCode.setJkryhzh_v(epv.getAccountnum());
				}
				if(epv.getBankname() != null) {
					elementCode.setJkrkhyh_v(epv.getBankname());
				}
			}
		}
		/*
		 * 财务协议合同-甲方-借款人
		 * 
		 * */
		/*if(null!=smallloanProject.getFinanceServiceMineType() && smallloanProject.getFinanceServiceMineType().equals("person_ourmain")){
			if(smallloanProject.getFinanceServiceMineId()!=0){
				SlPersonMain personMain = null;
				personMain = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, smallloanProject.getFinanceServiceMineId());
				if(personMain.getName()!=null){
					elementCode.setCwdkrmc_v(personMain.getName());
				}
				if(personMain.getCardnum()!= null){
					elementCode.setCwdkrsfzhm_v(personMain.getCardnum());
				}
				if(personMain.getAddress()!= null){
					elementCode.setCwdkrzz_v(personMain.getAddress());
				}
			}
		}else if(null!=smallloanProject.getFinanceServiceMineType() && smallloanProject.getFinanceServiceMineType().equals("company_ourmain")){
			SlCompanyMain companyMain = null;
			companyMain = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, smallloanProject.getFinanceServiceMineId());
			if(companyMain.getCorName()!= null){
				elementCode.setCwdkfgsmc_v(companyMain.getCorName());
			}
			if(companyMain.getLawName()!= null){
				elementCode.setCwdkffddbr_v(companyMain.getLawName());
			}
			if(companyMain.getSjjyAddress()!= null){
				elementCode.setCwdkfgsdz_v(companyMain.getSjjyAddress());
			}
		}*/
		/*
		 * 我方主体-债权人
		 * 个人person_ourmain，企业company_ourmain
		 * */
	/*	if(smallloanProject.getMineType().equals("person_ourmain")){//我方主体为个人
			SlPersonMain personMain = null;
			if(smallloanProject.getMineId()!=0){
				personMain = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, smallloanProject.getMineId());
				if(personMain != null){
					if(personMain.getName()!=null){
						elementCode.setZqrmc_v(personMain.getName());
					}
					if(personMain.getAddress()!=null){
						elementCode.setZqrdz_v(personMain.getAddress());
					}
					if(personMain.getPostalCode()!= null){
						elementCode.setZqryb_v(personMain.getPostalCode());//债权人邮政编码
					}
					if(personMain.getLinktel()!=null){
						elementCode.setZqrdh_v(personMain.getLinktel());
					}
					if(personMain.getTax()!= null){
						elementCode.setZqrcz_v(personMain.getTax());//债权人传真号码
					}	
				}
			}
			
		}else if(smallloanProject.getMineType().equals("company_ourmain")){//我方主体为企业
			SlCompanyMain companyMain = null;
			if(smallloanProject.getMineId()!=0){
				companyMain = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, smallloanProject.getMineId());
				if(companyMain != null){
					if(companyMain.getCorName()!= null){
						elementCode.setZqrmc_v(companyMain.getCorName());
					}
					if(companyMain.getMessageAddress() != null){
						elementCode.setZqrdz_v(companyMain.getMessageAddress());
					}
					if(companyMain.getPostalCode()!= null){
						elementCode.setZqryb_v(companyMain.getPostalCode());//债权公司邮政编码
					}
					if(companyMain.getTel()!= null){
						elementCode.setZqrdh_v(companyMain.getTel());
					}
					if(companyMain.getTax()!= null){
						elementCode.setZqrcz_v(companyMain.getTax());
					}
					if(companyMain.getLawName()!=null){
						elementCode.setZqffddbr_v(companyMain.getLawName());//债权方法定代表人
					}
				}
			}
		}*/
	
		/*
		 * 反担保合同相关
		 * 所有人类型：法人 602，自然人 603
		 * 担保类型：抵押担保604，质押担保605，信用担保606
		 * */
		if(null!=contract){
			if(contract.getMortgageId()!= 0 && ("thirdContract".equals(contractType) || "baozContract".equals(contractType))){
				/*String contractNumber = contract.getContractNumber();
				elementCode.setDydbhtbh_v(contractNumber);
				elementCode.setZydbhtbh_v(contractNumber);
				elementCode.setBzdbhtbh_v(contractNumber);*/
				
				VProcreditDictionary mortgage = null;
				mortgage = (VProcreditDictionary)creditBaseDao.getById(VProcreditDictionary.class, contract.getMortgageId());
				EnterpriseView e = null ;
				VPersonDic p = null ;
				//抵、质押物
				if(mortgage.getAssuretypeid() == 604 || mortgage.getAssuretypeid() == 605) {
					if(mortgage.getMortgagepersontypeforvalue() != null){
						elementCode.setDywmc_v(mortgage.getMortgagepersontypeforvalue());
					}
					if(mortgage.getTypeid()==7 || mortgage.getTypeid()==15 || mortgage.getTypeid()==16 || mortgage.getTypeid()==17){
						List list=houseService.seeHouse(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortHouse house=(VProjMortHouse) list.get(0);
							if(null!=house){
								elementCode.setGyr_v(house.getMutualofperson());
							}
						}
					}
					if(mortgage.getTypeid()==8){
						List list=officebuildingService.seeOfficebuilding(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortOfficeBuilding office=(VProjMortOfficeBuilding) list.get(0);
							if(null!=office){
								elementCode.setSycqr_v(office.getPropertyperson());
								elementCode.setGyr_v(office.getMutualofperson());
							}
						}
					}
					if(mortgage.getTypeid()==9){
						List list=housegroundService.seeHouseground(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortHouseGround houseGround=(VProjMortHouseGround) list.get(0);
							if(null!=houseGround){
								elementCode.setSycqr_v(houseGround.getPropertyperson());
							}
						}
					}
					if(mortgage.getTypeid()==10){
						List list=businessServMort.seeBusiness(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortBusiness business=(VProjMortBusiness) list.get(0);
							if(null!=business){
								elementCode.setSycqr_v(business.getPropertyperson());
							}
						}
					}
					if(mortgage.getTypeid()==11){
						List list=businessandliveService.seeBusinessandlive(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortBusAndLive bus=(VProjMortBusAndLive) list.get(0);
							if(null!=bus){
								elementCode.setSycqr_v(bus.getPropertyperson());
							}
						}
					}
					if(mortgage.getTypeid()==13){
						List list=industryService.seeIndustry(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortIndustry industry=(VProjMortIndustry) list.get(0);
							if(null!=industry){
								elementCode.setSycqr_v(industry.getPropertyperson());
							}
						}
					}
				}
				if(mortgage.getAssuretypeid()==604){
					/*int count = 0;
					if(!StringUtil.isNumeric(rnum)){
						rnum = "0";
					}
					count = Integer.parseInt(rnum);
					if(count <10){*/
						elementCode.setDydbhtbh_v(contract.getContractNumber());//抵押合同编号
					/*}else{
						elementCode.setDydbhtbh_v("MA-"+smallloanProject.getProjectNumber().replaceAll("_", "-")+"-"+count);//抵押合同编号
					}*/
				}
				if(mortgage.getAssuretypeid() == 605) {
					if(mortgage.getMortgagepersontypeforvalue() != null){
						elementCode.setZywmc_v(mortgage.getMortgagepersontypeforvalue());
					}
					/*int count = 0;
					if(!StringUtil.isNumeric(rnum)){
						rnum = "0";
					}
					count = Integer.parseInt(rnum);
					if(count <10){*/
						elementCode.setZydbhtbh_v(contract.getContractNumber());//质押合同编号
					/*}else{
						elementCode.setZydbhtbh_v("PA-"+smallloanProject.getProjectNumber().replaceAll("_", "-")+"-"+count);//质押合同编号
					}*/
					//creditBaseDao.saveDatas(contract);
				}
				//抵押物认定价值
				if(mortgage.getFinalCertificationPrice() != null) {
					elementCode.setDywgyjz_v(mortgage.getFinalCertificationPrice().toString()+"元");
				}
				if(mortgage.getPersonTypeId() == 603){//自然人
					p = (VPersonDic)creditBaseDao.getById(VPersonDic.class, mortgage.getAssureofname());
					if(null != p.getName()){
						elementCode.setBzrmc_v(p.getName());//保证人
						elementCode.setDyr_v(p.getName());//抵押人
						elementCode.setCzr_v(p.getName());//出质人
					}
					if(p.getFamilyaddress()!=null){
						elementCode.setJkrdz_v(p.getFamilyaddress());
					}
					if(p.getPostaddress()!=null){
						elementCode.setBzrtxdz_v(p.getPostaddress());
					}
					if(p.getHukou()!=null){
						elementCode.setBzrdz_v(p.getHukou());
					}
					@SuppressWarnings("all")
					List<EnterpriseBank> bankList = enterpriseBankService.getBankList(mortgage.getId(), (short)1, (short)0, (short)0);
					if(bankList!=null&&bankList.size()!=0){
						EnterpriseBank ebank = bankList.get(0);
						if(ebank!=null){
							if(ebank.getBankid()!=null){
								CsBank cBank = csBankService.get(ebank.getBankid());
								if(cBank!=null){
									if(ebank.getBankOutletsName()!=null&&cBank.getBankname()!=null){
										elementCode.setBzrzhkhh_v(cBank.getBankname()+" "+ebank.getBankOutletsName());
									}
								}
							}
							if(ebank.getAccountnum()!=null){
								elementCode.setBzryhkh_v(ebank.getAccountnum());
							}
						}
					}
					if(p.getPostaddress()!=null){
						
					}
					if(p.getCardtypevalue() != null) {//证件种类
						elementCode.setBzrzjzl_v(p.getCardtypevalue().toString());
						elementCode.setDyrzjzl(p.getCardtypevalue().toString());
						elementCode.setCzrzjzl_v(p.getCardtypevalue().toString());
					}
					if(null != p.getCardnumber()){//证件号码
						elementCode.setBzrzjhm_v(p.getCardnumber());
						elementCode.setDyrzjhm(p.getCardnumber());//抵押人身份证号码
						elementCode.setCzrzjhm_v(p.getCardnumber());//出质人身份证号码
					}
					if(null != p.getPostaddress()){//地址
						//elementCode.setBzrdz_v(p.getPostaddress());
						elementCode.setDyrdz_v(p.getPostaddress());//抵押人通讯地址
						elementCode.setCzrtxdz_v(p.getPostaddress());//出质人通讯地址
					}
					if(null != p.getPostcode()){
						elementCode.setBzryzbm_v(p.getPostcode());
						elementCode.setDyryzbm_v(p.getPostcode());//抵押人邮政编码
						elementCode.setCzryzbm_v(p.getPostcode());//出质人邮政编码
					}
					if(null != p.getTelphone()&& null!= p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getCellphone()+"/"+p.getTelphone());
						elementCode.setDyrdh_v(p.getCellphone()+"/"+p.getTelphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getCellphone()+"/"+p.getTelphone());//出质人联系电话
					}else if(null == p.getTelphone()&& null!= p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getCellphone());
						elementCode.setDyrdh_v(p.getCellphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getCellphone());//出质人联系电话
					}else if(null != p.getTelphone()&& null== p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getTelphone());
						elementCode.setDyrdh_v(p.getTelphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getTelphone());//出质人联系电话
					}
					if(null != p.getFax()){
						elementCode.setBzrczhm_v(p.getFax());
						elementCode.setDyrcz_v(p.getFax());//抵押人传真
						elementCode.setCzrczhm_v(p.getFax());//出质人传真号码
					}
					Spouse spouse = spouseService.getByPersonId(p.getId());
					if(spouse != null) {//配偶信息
						elementCode.setBzrpomc_v(spouse.getName());
						elementCode.setBzrpozjhm_v(spouse.getCardnumber());
					}
					
				}else if(mortgage.getPersonTypeId() == 602){//法人
					e = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, mortgage.getAssureofname());
					
					if(e.getPostcoding()!=null){//保证人邮编
						elementCode.setBzryzbm_v(e.getPostcoding());
					}
					if(e.getFactaddress()!=null){//保证人地址
						elementCode.setBzrdz_v(e.getFactaddress());
					}
					if(e.getArea()!=null){//保证人通讯地址
						elementCode.setBzrtxdz_v(e.getArea());
					}
					List<EnterpriseBank> bankList = enterpriseBankService.getBankList(e.getId(), (short)0, (short)0, (short)0);
					if(bankList!=null&&bankList.size()!=0){
						EnterpriseBank ebank = bankList.get(0);
						if(ebank!=null){
							CsBank cBank = csBankService.get(ebank.getBankid());
							if(cBank!=null){
								if(ebank.getBankOutletsName()!=null&&cBank.getBankname()!=null){
									elementCode.setBzrzhkhh_v(cBank.getBankname()+" "+ebank.getBankOutletsName());
								}
							}
							if(ebank.getAccountnum()!=null){
								elementCode.setBzryhkh_v(ebank.getAccountnum());
							}
						}
					}
					if(e.getEnterprisename()!= null){
						elementCode.setBzrmc_v(e.getEnterprisename());//保证人
						elementCode.setDyr_v(e.getEnterprisename());//抵押人
						elementCode.setCzr_v(e.getEnterprisename());//出质人
					}
					if(e.getLegalperson() != null) {
						elementCode.setBzffddbr_v(e.getLegalperson());
						elementCode.setDyffddbr_v(e.getLegalperson());
						elementCode.setCzffddbr_v(e.getLegalperson());
					}
					if(e.getCciaa()!= null){
						elementCode.setBzfyyzzh_v(e.getCciaa());
						elementCode.setDyfyyzzhm_v(e.getCciaa());//抵押公司营业执照号码
						elementCode.setCzfyyzzhm_v(e.getCciaa());//出质方营业执照号码
					}
					if(e.getArea() != null){
						//elementCode.setBzrdz_v(e.getArea());
						elementCode.setDyrdz_v(e.getArea());//抵押公司通讯地址
						elementCode.setCzrtxdz_v(e.getArea());//出质方通讯地址
					}
					if(e.getPostcoding()!= null){
						elementCode.setBzryzbm_v(e.getPostcoding());
						elementCode.setDyryzbm_v(e.getPostcoding());//抵押公司邮政编码
						elementCode.setCzryzbm_v(e.getPostcoding());//出质方邮政编码
					}
					if(e.getTelephone()!= null){
						elementCode.setBzrlxdh_v(e.getTelephone());
						elementCode.setDyrdh_v(e.getTelephone());//抵押人联系电话
						elementCode.setCzrlxdh_v(e.getTelephone());//出质人联系电话
					}
					if(e.getFax() != null){
						elementCode.setBzrczhm_v(e.getFax());
						elementCode.setDyrcz_v(e.getFax());//抵押人传真
						elementCode.setCzrczhm_v(e.getFax());//出质人传真号码
					}
					VPersonDic vpd= (VPersonDic)creditBaseDao.getById(VPersonDic.class, e.getLegalpersonid());
					if(vpd.getJobvalue() != null) {
						elementCode.setBzffddbrzw_v(vpd.getJobvalue());
						elementCode.setDyffddbrzw_v(vpd.getJobvalue());
						elementCode.setCzffddbrzw_v(vpd.getJobvalue());
					}
					Spouse spouse = spouseService.getByPersonId(vpd.getId());
					if(spouse != null) {//配偶信息
						elementCode.setBzrpomc_v(spouse.getName());
						elementCode.setBzrpozjhm_v(spouse.getCardnumber());
					}
				}
				//产权人，房地产地点，建筑面积，证件号码
				if(mortgage.getId()!= null){

					/*if(null!=mortgage.getTypeid() && mortgage.getTypeid()==6){
						ProcreditMortgageProduct pm=(ProcreditMortgageProduct) creditBaseDao.getById(ProcreditMortgageProduct.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下"+pm.getName()+"存货商品(品牌："+pm.getBrand()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==7){
						ProcreditMortgageHouse pm=(ProcreditMortgageHouse) creditBaseDao.getById(ProcreditMortgageHouse.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下位于"+pm.getHouseaddress()+pm.getBuildacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==10){
						ProcreditMortgageBusiness pm=(ProcreditMortgageBusiness) creditBaseDao.getById(ProcreditMortgageBusiness.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getAcreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==11){
						ProcreditMortgageBusinessandlive pm=(ProcreditMortgageBusinessandlive) creditBaseDao.getById(ProcreditMortgageBusinessandlive.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getAnticipateacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==12){
						ProcreditMortgageEducation pm=(ProcreditMortgageEducation) creditBaseDao.getById(ProcreditMortgageEducation.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getAcreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==13){
						ProcreditMortgageIndustry pm=(ProcreditMortgageIndustry) creditBaseDao.getById(ProcreditMortgageIndustry.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getOccupyacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==14){
						VProjMortDroit pm = (VProjMortDroit) creditBaseDao.getById(VProjMortDroit.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("把享受权利比重"+(pm.getDroitpercent()==null?"   ":pm.getDroitpercent())+"%作为抵押");
					}*/
					
					StringBuffer fdbwxq = new StringBuffer();
					if(mortgage.getTypeid()!= 0 && (mortgage.getTypeid()==7 || mortgage.getTypeid()==15 || mortgage.getTypeid()==16 || mortgage.getTypeid()==17)){//住宅、公寓、联排别墅、独栋别墅
						String hql3 = "from ProcreditMortgageHouse where mortgageid="+mortgage.getId();
						List list3 = creditBaseDao.queryHql(hql3);
						if(list3 != null && list3.size()>0){
							ProcreditMortgageHouse pmb = (ProcreditMortgageHouse)list3.get(0);
							if(pmb.getHouseaddress() != null) {
								elementCode.setDywszd_v(pmb.getHouseaddress());
							}
							if(pmb.getMutualofperson() != null) {
								elementCode.setDywgyr_v(pmb.getMutualofperson());
							}
							if(pmb.getCertificatenumber() != null) {
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getCertificatenumber()!=null){//证券编号
								elementCode.setCqbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getHouseaddress()!=null){//住宅地址
								elementCode.setZzdd_v(pmb.getHouseaddress());
							}
							if(pmb.getBuildacreage()!=null){//住宅面积
								elementCode.setZzmj_v(pmb.getBuildacreage().toString());
							}
						} 
						
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==10){//商业用地
						String hql = "from ProcreditMortgageBusiness where mortgageid="+mortgage.getId();
						List list = creditBaseDao.queryHql(hql);
						if(list != null && list.size()>0){
							ProcreditMortgageBusiness pmb = (ProcreditMortgageBusiness)list.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress() != null) {
								pmb.setAddress("");
							}
							if(pmb.getAddress() != null) {
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==8){//商铺写字楼
						String hql6 = "from ProcreditMortgageOfficebuilding where mortgageid="+mortgage.getId();
						List list6 = creditBaseDao.queryHql(hql6);
						if(list6 != null && list6.size()>0){
							ProcreditMortgageOfficebuilding pmb = (ProcreditMortgageOfficebuilding)list6.get(0);
							if(pmb.getMutualofperson()!= null){
								elementCode.setDywgyr_v(pmb.getMutualofperson());
							}
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getHouseaddress()!= null){
								elementCode.setDywszd_v(pmb.getHouseaddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==9){//住宅用地
						String hql4 = "from ProcreditMortgageHouseground where mortgageid="+mortgage.getId();
						List list4 = creditBaseDao.queryHql(hql4);
						if(list4 != null && list4.size()>0){
							ProcreditMortgageHouseground pmb = (ProcreditMortgageHouseground)list4.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==11){//商住用地
						String hql1 = "from ProcreditMortgageBusinessandlive where mortgageid="+mortgage.getId();
						List list1 = creditBaseDao.queryHql(hql1);
						if(list1!= null && list1.size()>0){
							ProcreditMortgageBusinessandlive pmb = (ProcreditMortgageBusinessandlive)list1.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==12){//教育用地
						String hql2 = "from ProcreditMortgageEducation where mortgageid="+mortgage.getId();
						List list2 = creditBaseDao.queryHql(hql2);
						if(list2 != null && list2.size()>0){
							ProcreditMortgageEducation pmb = (ProcreditMortgageEducation)list2.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==13){//工业用地
						String hql5 = "from ProcreditMortgageIndustry where mortgageid="+mortgage.getId();
						List list5 = creditBaseDao.queryHql(hql5);
						if(list5 != null && list5.size()>0){
							ProcreditMortgageIndustry pmb = (ProcreditMortgageIndustry)list5.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==6){//存货商品
						String hql = "from VProjMortProduct where mortgageid ="+mortgage.getId();
						VProjMortProduct pmb = null;
						List list = creditBaseDao.queryHql(hql);
						if(list != null && list.size()>0){
							pmb = (VProjMortProduct)list.get(0);
							if(pmb.getDepositary() != null) {
								elementCode.setZywcfdd_v(pmb.getDepositary());
							}
							if(pmb.getAmount() != null) {
								elementCode.setZywsl_v(pmb.getAmount().toString());
							}
						}
				}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==1){//车辆信息
					String hql = "from ProcreditMortgageCar where mortgageid = " + mortgage.getId();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("mortgageid", mortgage.getId());
					List<ProcreditMortgageCar> carList = (List<ProcreditMortgageCar>) mySelfService.getForList("ProcreditMortgageCar", map);
					if(carList!=null&&carList.size()!=0){
						ProcreditMortgageCar car = carList.get(0);
						if(car!=null){
							if(car.getCarModel()!=null){
								elementCode.setCx_v(car.getCarModel());
							}
							if(car.getEngineNo()!=null){
								elementCode.setFdjxh_v(car.getEngineNo());
							}
							if(car.getVin()!=null){
								elementCode.setCjh_v(car.getVin());
							}
						}
					}
				}
				if(mortgage.getFinalprice()!=null){
					elementCode.setPgjz_v(mortgage.getFinalprice().toString());
				}
				//担保人（所有担保人）
				String hql = "from VProcreditDictionary where projid ="+ smallloanProject.getProjectId();
				List<VProcreditDictionary> vdList = creditBaseDao.queryHql(hql);
				String dbrStr = "";
				for(VProcreditDictionary vd : vdList) {
					if(vd.getAssureofnameEnterOrPerson() != null && vd.getAssureofnameEnterOrPerson() != "") {
						dbrStr = dbrStr +"/"+ vd.getAssureofnameEnterOrPerson();
					}
				}
				elementCode.setDbrmc_v(dbrStr);
				//借款合同编号
				String dbht="";
				List<VProcreditContract> vpcclist= vProcreditContractDao.getList(proId, "SmallLoan", null);
				for(VProcreditContract v:vpcclist){
					dbht=dbht+"/"+v.getContractNumber();
				}
		    	elementCode.setJkhtbh_v(dbht);
		    	//担保合同编号（所有）
		    	String sydbht="";
				List<VProcreditContract> vpcclist1= vProcreditContractDao.getList(proId, "SmallLoan", "('thirdContract')");
				for(VProcreditContract v:vpcclist1){
					sydbht = sydbht + "/"+v.getContractNumber();
				}
		    	elementCode.setDbhtbh_v(sydbht);
				//目标公司名称
				/*if(mortgage.getEnterprisename()!= null){
					elementCode.setNewmbgsmc_v(mortgage.getEnterprisename());
				}
				//股权.%
				Double stockownershippercent = 0.0;
				if(mortgage.getId()!=null){
					String hql = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
					List list = creditBaseDao.queryHql(hql);
					if(list!= null && list.size()>0){
						VProjMortStockOwnerShip vs = (VProjMortStockOwnerShip)list.get(0);
						if(vs.getStockownershippercent()==null){
							stockownershippercent = 0.0;
						}else{
							stockownershippercent = vs.getStockownershippercent();
						}
						elementCode.setNewgqbfs_v(stockownershippercent.toString()+"%");
						//目标公司名称
						elementCode.setNewmbgsmc_v(vs.getEnterprisename());
						Object obj = null;
						obj = creditBaseDao.getById(Enterprise.class, vs.getCorporationname());
						if(obj != null){
							Enterprise en = (Enterprise)obj;
							//出质股权数额小写
							Double d = 0.0;
							if(en.getRegistermoney()!=null){
								d = en.getRegistermoney()*stockownershippercent/100;
							}
							
							elementCode.setNewczgqsexx_v(d.toString()+"万元");
						}
						
					}
					
				}
				//质押担保债权本金数额 
				if(mortgage.getId()!= null){
					String hql = "from VProcreditDictionary AS d where d.projid =? and d.businessType =?";
					Object [] obj = {Long.parseLong(proId),smallloanProject.getBusinessType()};
					List list = creditBaseDao.queryHql(hql,obj);
					Double shippercent = 0.0;
					int enterpId = 0;
					if(list!= null && list.size()>0 && list.size()>1){
						for(int i =0;i<list.size();i++){
							String hqlship = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
							List listship = creditBaseDao.queryHql(hqlship);
							if(listship!= null && listship.size()>0){
								VProjMortStockOwnerShip vs = (VProjMortStockOwnerShip)listship.get(0);
								Double shipt = 0.0;
								if(vs.getStockownershippercent()!= null){
									shipt = vs.getStockownershippercent();
								}
								shippercent = shipt;
								enterpId = vs.getCorporationname();
							}
							for(int j= 1;j<list.size();j++){
								String hqlship2 = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
								List listship2 = creditBaseDao.queryHql(hqlship2);
								if(listship2!= null && listship2.size()>0){
									VProjMortStockOwnerShip vs2 = (VProjMortStockOwnerShip)listship2.get(0);
									if(enterpId == vs2.getCorporationname()){
										Double shipt = 0.0;
										if(vs2.getStockownershippercent()!= null){
											shipt=vs2.getStockownershippercent();
										}
										shippercent += (shippercent + shipt);
									}
								}
							}
							
						}
					}else{
						shippercent = Double.valueOf("1");
					}
					if(shippercent.compareTo(0.0)>0){
						Double je = smallloanProject.getProjectMoney().doubleValue()*(stockownershippercent/shippercent)/100;
						elementCode.setNewzydbzqbjse_v(myFormatter.format(je)+"元");
					}
				}*/
				
				
				
				//
				
			}
		}
		
		}
		if(null!=proId && !"".equals(proId)){
			String hql="from ProcreditContract as p where p.projId="+Long.valueOf(proId);
			List<ProcreditContract> list=creditBaseDao.queryHql(hql);
			String contractNumber="";
			if(null!=list){
				for(ProcreditContract p:list){
					contractNumber=contractNumber+p.getContractNumber()+",";
				}
				if(!"".equals(contractNumber)){
					contractNumber=contractNumber.substring(0,contractNumber.length()-1);
				}
			}
			elementCode.setDbhtbh_v(contractNumber);
		}
	


        List<BpCustRelation> bpCustRelationList=bpCustRelationService.getByCustIdAndCustType(smallloanProject.getOppositeID(),smallloanProject.getOppositeType().equals("person_customer")?"p_loan":"b_loan");
		if(bpCustRelationList!=null&&bpCustRelationList.size()>0){
        BpCustRelation bpCustRelation=bpCustRelationList.get(0);
		BpCustMember bpCustMember=bpCustMemberService.get(bpCustRelation.getP2pCustId());
		if(null!=bpCustMember){
			elementCode.setJkryhm_v(bpCustMember.getLoginname());
			//elementCode.setZbxmbh_v("p2p_"+bpCustMember.getLoginname()+"_"+smallloanProject.getId());
		}
		}else{
			elementCode.setJkryhm_v("");
			/*if(smallloanProject.getOppositeType().equals("person_customer")){
				VPersonDic person = (VPersonDic)creditBaseDao.getById(VPersonDic.class, smallloanProject.getOppositeID().intValue());
			   elementCode.setZbxmbh_v("erp_"+person.getCardnumber()+"_"+smallloanProject.getId());
			}*/
		}
		/*BpFundProject bpFundProject=null;
	    bpFundProject=bpFundProjectService.get(Long.parseLong(proId));
	    com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro bpPersionDirPro=null;
		bpPersionDirPro=bpPersionDirProService.getByProjectId(bpFundProject.getProjectId());*/
		com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan plBidPlan=null;
		if(rnum!=null && !rnum.equals("")){
		plBidPlan=plBidPlanService.get(Long.valueOf(rnum));
		if(plBidPlan.getBidProNumber()!=null&&!"".equals(plBidPlan.getBidProNumber())){
		elementCode.setZbxmbh_v(plBidPlan.getBidProNumber());
		}else{
			elementCode.setZbxmbh_v("");
		}
	  }
		return elementCode;
	}
	//以下为老方法
	public ElementCode getElementBySystem2(String proId , int conId ,int tempId, String contractType) throws Exception{
		ElementCode elementCode = new ElementCode(); 
		/*SlSmallloanProject smallloanProject =(SlSmallloanProject)creditBaseDao.getById(SlSmallloanProject.class, proId) ; //项目信息实体
		VEnterpriseProj vEnterProject = (VEnterpriseProj)creditBaseDao.getById(VEnterpriseProj.class, proId) ;  //项目信息试图
		CsSuretyCompany company = (CsSuretyCompany)creditBaseDao.getById(CsSuretyCompany.class, vEnterProject.getGuaranteeCompany());//担保公司信息
		Enterprise enter = (Enterprise)creditBaseDao.getById(Enterprise.class, smallloanProject.getOppositeID());
		EnterpriseView enterv = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID());
		ProcreditContract contract = (ProcreditContract)creditBaseDao.getById(ProcreditContract.class, conId);
		//设置担保公司相关信息的要素
		if(null != company.getCompanyName()){
			elementCode.setZmg_v(company.getCompanyName());
		}if(null != company.getBusinessLicenseAddress()){
			elementCode.setZmga_v(company.getBusinessLicenseAddress());
		}if(null != company.getLegalDelegatePerson()){
			elementCode.setZmglp_v(company.getLegalDelegatePerson());
		}if(null != company.getPhone()){
			elementCode.setZmgp_v(company.getPhone());
		}if(null != company.getAddress()){
			elementCode.setZmgca_v(company.getAddress());
		}if(null != company.getFax()){
			elementCode.setZmgf_v(company.getFax());
		}if(null != company.getAddressee()){
			elementCode.setZmgrln_v(company.getAddressee());
		}
		//合同相关信息
		if(null != smallloanProject.getProjectNumber()){
			//elementCode.setRacn_v(enterProject.getProjnum()+"-1");  //反担保合同编号
			//elementCode.setPln_v(enterProject.getProjNum()+"-3") ;   //承诺函编号
			elementCode.setPln_v(smallloanProject.getProjectNumber()) ;   //承诺函编号
			//elementCode.setCaacn_v(enterProject.getProjNum()+"-2"); //委托反担保合同编号
			elementCode.setCaacn_v(smallloanProject.getProjectNumber()); //委托反担保合同编号
			//elementCode.setCacn_v(enterProject.getProjNum()+"-1");  //委托担保合同编号
			elementCode.setCacn_v(smallloanProject.getProjectNumber());  //委托担保合同编号 update by chencc
		}
		//企业项目表有有关的要素
		
		if(null != vEnterProject.get_bankName()){
			elementCode.setLbn_v(vEnterProject.get_bankName());
		}if(null!= vEnterProject.getSubBankName()){
			elementCode.setLbnb_v(vEnterProject.getSubBankName());
		}if(null != vEnterProject.getCreditmoney()){
			elementCode.setLmc_v(vEnterProject.getCreditmoney().toString());
			elementCode.setLmcb_v(MoneyFormat.getInstance().amountToChinese(vEnterProject.getCreditmoney()));
		}if(null != vEnterProject.getCreditterm()){
			elementCode.setLt_v(vEnterProject.getCreditterm().toString());
		}if(null != vEnterProject.getCreditpurpose()){
			elementCode.setFp_v(vEnterProject.getCreditpurpose());
		}if(null != vEnterProject.getCurrencytype()){
			elementCode.setC_v(vEnterProject.getCurrencytype());
		}if(null != vEnterProject.getAssuremoney()){
			elementCode.setAmcl_v(vEnterProject.getAssuremoney().toString()) ;
			elementCode.setAmcb_v(MoneyFormat.getInstance().amountToChinese(vEnterProject.getAssuremoney()));
		}if(null != vEnterProject.getPremiumrate()){
			elementCode.setPrl_v(vEnterProject.getPremiumrate().toString()); 
			elementCode.setPrb_v(MoneyFormat.getInstance().amountToChinese(vEnterProject.getPremiumrate()));
		}if(null != vEnterProject.getPremiumrate() && null != vEnterProject.getCreditmoney()){
			Double pcl = (vEnterProject.getPremiumrate()/100) * (vEnterProject.getCreditmoney() * 10000); //保费
			elementCode.setPcl_v(pcl.toString());
//			elementCode.setPcb_v(MoneyFormat.getInstance().hangeToBig(pcl));
			elementCode.setPcb_v(new FormatMoney(pcl).toString());//update by chencc 保费金额（元）
		}if(null != vEnterProject.getCustomermarginrate()){
			elementCode.setCmr_v(vEnterProject.getCustomermarginrate().toString());  //向客户收取的保证金比例
		}if(null != vEnterProject.getCustomermarginrate() && null != vEnterProject.getCreditmoney()){
			Double cmcl = (vEnterProject.getCustomermarginrate() * vEnterProject.getCreditmoney())/100;
			elementCode.setCmcl_v(cmcl.toString());
			elementCode.setCmcb_v(MoneyFormat.getInstance().amountToChinese(cmcl));
		}if(null != vEnterProject.getCreditkind()){
			elementCode.setCt_v(vEnterProject.getCreditkind());    //信贷种类
		}if(null != vEnterProject.getCreditrate()){
			elementCode.setLir_v(vEnterProject.getCreditrate().toString()) ;  //借款利率
		}if(null != vEnterProject.getZqbffl()){
			elementCode.setDpr_v(vEnterProject.getZqbffl().toString()); //展期保费费率
		}if(null != vEnterProject.getYqbffl()){
			elementCode.setOpr_v(vEnterProject.getYqbffl().toString()); //逾期保费费率
		}if(null != vEnterProject.getCreditmoney()){//add by chencc
			List lt=creditBaseDao.queryHql("from VProjMortStockOwnerShip as vp where vp.mortgageid=?", conId);
			if(lt!=null){
				VProjMortStockOwnerShip vpmsos=(VProjMortStockOwnerShip)lt.get(0);
				if(vpmsos.getStockownershippercent()!=null){
					elementCode.setPsr_v(vpmsos.getStockownershippercent().toString());//质押股权
					Double pram=vEnterProject.getCreditmoney()*vpmsos.getStockownershippercent();
					elementCode.setPram_v(pram.toString());//质押反担保金额（该金额为总债权金额*所占股权比例）
				}
			}
			
		}
		// 最高额贷款要素
		if(null != vEnterProject.getCreditmoney()){
			elementCode.setMhcm_v(vEnterProject.getCreditmoney().toString());  //最高额合同金额(万元) 小写
			elementCode.setMhcmb_v(MoneyFormat.getInstance().amountToChinese(vEnterProject.getCreditmoney()));
		}if(null != vEnterProject.getCreditterm() ){
			elementCode.setMhct_v(vEnterProject.getCreditterm().toString());  //最高额合同期限（月）
		}if(null != vEnterProject.getCreditGrantingMoney()){
			elementCode.setFirstbm_v(vEnterProject.getCreditGrantingMoney().toString()) ;
			elementCode.setFirstbmb_v(MoneyFormat.getInstance().amountToChinese(vEnterProject.getCreditGrantingMoney()));   //第一次借款金额(万元) 大写
		}if(null != vEnterProject.getCreditGrantingTerm()){
			elementCode.setFirstbt_v(vEnterProject.getCreditGrantingTerm().toString());  //第一次借款期限
		}if(null != vEnterProject.getPremiumrate()){
			elementCode.setFirstpr_v(vEnterProject.getPremiumrate().toString());  //首期保费费率
			elementCode.setFirstprb_v(MoneyFormat.getInstance().hangeToBig(vEnterProject.getPremiumrate()));
		}if(null != vEnterProject.getPremiumrateSecond()){
			elementCode.setAgainpr_v(vEnterProject.getPremiumrateSecond().toString());  //再次保费费率
			elementCode.setAgainprb_v(MoneyFormat.getInstance().hangeToBig(vEnterProject.getPremiumrateSecond()));
		}if(null != vEnterProject.getCreditGrantingMoney() && null != vEnterProject.getCreditGrantingTerm()&& null != vEnterProject.getPremiumrate()){
			Double firstlpt = (vEnterProject.getCreditGrantingMoney()* 10000) * vEnterProject.getCreditGrantingTerm()* (vEnterProject.getPremiumrate()/100) ;
			elementCode.setFirstlpt_v(firstlpt.toString());  //第一次借款保费总额（元）
		}//end
		//贷款信息(法人)
		if(null != enterv.getEnterprisename()){
			elementCode.setCc_v(enter.getEnterprisename());
		}if(null != enterv.getFactaddress()){
			elementCode.setCca_v(enter.getFactaddress());
		}if(null != enterv.getLegalperson()){
			elementCode.setCclm_v(enterv.getLegalperson());
		}if(null != enterv.getFactaddress()){
			elementCode.setCcca_v(enterv.getFactaddress());
		}if(null != enter.getTelephone()){
			elementCode.setCcp_v(enter.getTelephone());
		}if(null != enter.getFax()){
			elementCode.setCcf_v(enter.getFax());
		}if(null != enter.getReceiveMail()){
			elementCode.setCcrlm_v(enter.getReceiveMail());
		}
		
		 * 与反担保合同相关 ：
		 * 1、抵押担保（法人/自然人）   法人抵押人/自然人抵押人
		 * 2、质押担保（法人/自然人）   法人质押人/自然人质押人
		 * 3、信用保证（法人/自然人）   法人保证人/自然人保证人
		 *    所有人类型 （personTypeId）：法人 1657 自然人 1658
		 *    担保类型 （assuretypeid） ： 抵押担保 835 质押担保836  信用保证 1656
		 * 
		if(conId != 0){
			VProcreditDictionary mortgage =  null ;
			//add by chencc
			if(null != smallloanProject.getProjectNumber()){
				elementCode.setRacn_v(smallloanProject.getProjectNumber());//反担保合同编号
			}
			if(contract.getMortgageId() != 0){
				mortgage = (VProcreditDictionary)creditBaseDao.getById(VProcreditDictionary.class, contract.getMortgageId());
//				if(null != contract.getContractNumber()){
//					elementCode.setRacn_v(contract.getContractNumber());
//				}
				if(null != mortgage.getMortgagename()){
					elementCode.setG_v(mortgage.getMortgagename());
				}
				EnterpriseView e = null ;
				VPersonDic p = null ;
				if(mortgage.getPersonTypeId() == 1657 && mortgage.getAssuretypeid() == 835){
					//法人抵押人
					e = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, mortgage.getAssureofname());
					if(null != e.getEnterprisename()){
						elementCode.setCm_v(e.getEnterprisename());
					}if(null != e.getAddress()){
						elementCode.setCma_v(e.getAddress());
					}if(null != e.getLegalperson()){
						elementCode.setCmlm_v(e.getLegalperson());
					}if(null != e.getTelephone()){
						elementCode.setCmp_v(e.getTelephone());
					}
					//add by chencc 法人通讯地址、传真号码、收件人
					if(null != e.getFactaddress()){
						elementCode.setFrtxdz_v(e.getFactaddress());
					}
					if(null != e.getFax()){
						elementCode.setFrczhm_v(e.getFax());
					}
					if(null != e.getReceiveMail()){
						elementCode.setFrsjr_v(e.getReceiveMail());
					}
				}else if(mortgage.getPersonTypeId() == 1657 && mortgage.getAssuretypeid() == 836){
					//法人质押人
					e = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, mortgage.getAssureofname());
					if(null != e.getEnterprisename()){
						elementCode.setLmp_v(e.getEnterprisename());
					}if(null != e.getAddress()){
						elementCode.setLmpa_v(e.getAddress());
					}if(null != e.getLegalperson()){
						elementCode.setLmplm_v(e.getLegalperson());
					}if(null != e.getTelephone()){
						elementCode.setLmpp_v(e.getTelephone());
					}
					//add by chencc 法人通讯地址、传真号码、收件人
					if(null != e.getFactaddress()){
						elementCode.setFrtxdz_v(e.getFactaddress());
					}
					if(null != e.getFax()){
						elementCode.setFrczhm_v(e.getFax());
					}
					if(null != e.getReceiveMail()){
						elementCode.setFrsjr_v(e.getReceiveMail());
					}
				}else if(mortgage.getPersonTypeId() == 1657 && mortgage.getAssuretypeid() == 1656){
					//法人保证人
					e = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, mortgage.getAssureofname());
					if(null != e.getEnterprisename()){
						elementCode.setCa_v(e.getEnterprisename()) ;
					}if(null != e.getAddress()){
						elementCode.setCaa_v(e.getAddress());
					}if(null != e.getLegalperson()){
						elementCode.setCalm_v(e.getLegalperson()) ;
					}if(null != e.getTelephone()){
						elementCode.setCap_v(e.getTelephone());
					}
					//add by chencc 法人通讯地址、传真号码、收件人
					if(null != e.getFactaddress()){
						elementCode.setFrtxdz_v(e.getFactaddress());
					}
					if(null != e.getFax()){
						elementCode.setFrczhm_v(e.getFax());
					}
					if(null != e.getReceiveMail()){
						elementCode.setFrsjr_v(e.getReceiveMail());
					}
				}else if(mortgage.getPersonTypeId() == 1658 && mortgage.getAssuretypeid() == 835){
					//自然人抵押人
					p = (VPersonDic)creditBaseDao.getById(VPersonDic.class, mortgage.getAssureofname());
					if(null != p.getName()){
						elementCode.setNpm_v(p.getName());
					}if(null != p.getShengvalue() || null != p.getShivalue() || null != p.getXianvalue()){
						elementCode.setNpma_v(p.getShengvalue()+p.getShivalue()+p.getXianvalue());
					}if(null != p.getCardtypevalue() || null != p.getCardnumber()){
						elementCode.setNpmcn_v(p.getCardtypevalue()+":"+p.getCardnumber());
					}if(null != p.getCellphone() || null != p.getTelphone()){
						elementCode.setNpmp_v(p.getCellphone()+"/"+p.getTelphone());
					}else if(null != p.getCellphone()){
						elementCode.setNpmp_v(p.getCellphone());
					}else if(null != p.getTelphone()){
						elementCode.setNpmp_v(p.getTelphone());
					}
					//add by chencc 自然人通讯地址、传真号码、收件人
					if(null != p.getPostaddress()){
						elementCode.setZrrtxdz_v(p.getPostaddress());
					}
					if(null != p.getFax()){
						elementCode.setZrrczhm_v(p.getFax());
					}
					if(null != p.getName()){
						elementCode.setZrrsjr_v(p.getName());
					}
				}else if(mortgage.getPersonTypeId() == 1658 && mortgage.getAssuretypeid() == 836){
					//自然人质押人
					p = (VPersonDic)creditBaseDao.getById(VPersonDic.class, mortgage.getAssureofname());
					if(null != p.getName()){
						elementCode.setNppl_v(p.getName());
					}if(null != p.getShengvalue() || null != p.getShivalue() || null != p.getXianvalue()){
						elementCode.setNppla_v(p.getShengvalue()+p.getShivalue()+p.getXianvalue());
					}if(null != p.getCardtypevalue() || null != p.getCardnumber()){
						elementCode.setNpplcn_v(p.getCardtypevalue()+":"+p.getCardnumber());
					}if(null != p.getCellphone() || null != p.getTelphone()){
						elementCode.setNpplp_v(p.getCellphone()+"/"+p.getTelphone());
					}else if(null != p.getCellphone()){
						elementCode.setNpplp_v(p.getCellphone());
					}else if(null != p.getTelphone()){
						elementCode.setNpplp_v(p.getTelphone());
					}
					//add by chencc 自然人通讯地址、传真号码、收件人
					if(null != p.getPostaddress()){
						elementCode.setZrrtxdz_v(p.getPostaddress());
					}
					if(null != p.getFax()){
						elementCode.setZrrczhm_v(p.getFax());
					}
					if(null != p.getName()){
						elementCode.setZrrsjr_v(p.getName());
					}
				}else if(mortgage.getPersonTypeId() == 1658 && mortgage.getAssuretypeid() == 1656){
					//自然人保证人
					p = (VPersonDic)creditBaseDao.getById(VPersonDic.class, mortgage.getAssureofname());
					if(null != p.getName()){
						elementCode.setNp_v(p.getName());
					}if(null != p.getShengvalue() || null != p.getShivalue() || null != p.getXianvalue()){
						elementCode.setNpa_v(p.getShengvalue()+p.getShivalue()+p.getXianvalue());
					}if(null != p.getCardtypevalue() || null != p.getCardnumber()){
						elementCode.setNpcn_v(p.getCardtypevalue()+":"+p.getCardnumber());
					}if(null != p.getCellphone() || null != p.getTelphone()){
						elementCode.setNpp_v(p.getCellphone()+"/"+p.getTelphone());
					}else if(null != p.getCellphone()){
						elementCode.setNpp_v(p.getCellphone());
					}else if(null != p.getTelphone()){
						elementCode.setNpp_v(p.getTelphone());	
					}
					//add by chencc 自然人通讯地址、传真号码、收件人
					if(null != p.getPostaddress()){
						elementCode.setZrrtxdz_v(p.getPostaddress());
					}
					if(null != p.getFax()){
						elementCode.setZrrczhm_v(p.getFax());
					}
					if(null != p.getName()){
						elementCode.setZrrsjr_v(p.getName());
					}
				}
			}
			if(contract.getTemplateId() == 254){
				// 6
				if(null != vEnterProject.getCreditGrantingMoney() && null != vEnterProject.getCreditGrantingTerm() && null != vEnterProject.getPremiumrate()){
					Double pretb = (vEnterProject.getCreditGrantingMoney()*10000) * vEnterProject.getCreditGrantingTerm() * (vEnterProject.getPremiumrate()/100);
					elementCode.setPret_v(pretb.toString());
					elementCode.setPretb_v(MoneyFormat.getInstance().hangeToBig(pretb));
					Double tfypm = (vEnterProject.getCreditGrantingMoney()*10000) * (vEnterProject.getPremiumrate()/100);
					elementCode.setTfypm_v(tfypm.toString());
					elementCode.setTfypmb_v(MoneyFormat.getInstance().hangeToBig(tfypm));
					int month = vEnterProject.getCreditGrantingTerm() - 12;
					if(month<=12){
						Double sypm0 = (vEnterProject.getCreditGrantingMoney()*10000) * 1 * (vEnterProject.getPremiumrate()/100);
						elementCode.setSypm_v(sypm0.toString());
						elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm0));
					}else{
						int yu = month % 12 ;
						int year = month / 12 ;
						if(yu < 5){
							Double sypm1 = (vEnterProject.getCreditGrantingMoney()*10000) * year * (vEnterProject.getPremiumrate()/100);
							elementCode.setSypm_v(sypm1.toString());
							elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm1));
						}else{
							Double sypm2 = (vEnterProject.getCreditGrantingMoney()*10000) * (year+1) * (vEnterProject.getPremiumrate()/100) ;
							elementCode.setSypm_v(sypm2.toString());
							elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm2));
						}
					}
				}
			}if(contract.getTemplateId() == 255){
				// 7
				if(null != vEnterProject.getCreditmoney() && null != vEnterProject.getCreditterm() && null != vEnterProject.getPremiumrate()){
					Double pretb = (vEnterProject.getCreditmoney()*10000) * vEnterProject.getCreditterm() * (vEnterProject.getPremiumrate()/100);
					elementCode.setPret_v(pretb.toString());
					elementCode.setPretb_v(MoneyFormat.getInstance().hangeToBig(pretb));
				}
			}if(contract.getTemplateId() == 256){
				//8
				if(null != vEnterProject.getCreditmoney() && null != vEnterProject.getCreditterm() && null != vEnterProject.getPremiumrate()){
					Double pretb = (vEnterProject.getCreditmoney()*10000) * vEnterProject.getCreditterm() * (vEnterProject.getPremiumrate()/100);
					elementCode.setPret_v(pretb.toString());
					elementCode.setPretb_v(MoneyFormat.getInstance().hangeToBig(pretb));
					Double tfypm = (vEnterProject.getCreditmoney()*100) * (vEnterProject.getPremiumrate()/100);
					elementCode.setTfypm_v(tfypm.toString());
					elementCode.setTfypmb_v(MoneyFormat.getInstance().hangeToBig(tfypm));
					int month = vEnterProject.getCreditterm() - 12;
					if(month<=12){
						Double sypm0 = (vEnterProject.getCreditGrantingMoney()*10000) * 1 * (vEnterProject.getPremiumrate()/100) ;
						elementCode.setSypm_v(sypm0.toString());
						elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm0));
					}else{
						int yu = month % 12 ;
						int year = month / 12 ;
						if(yu < 5){
							Double sypm1 = (vEnterProject.getCreditmoney()*10000) * year * (vEnterProject.getPremiumrate()/100) ;
							elementCode.setSypm_v(sypm1.toString());
							elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm1));
						}else{
							Double sypm2 = (vEnterProject.getCreditmoney()*10000) * (year+1) * (vEnterProject.getPremiumrate()/100) ;
							elementCode.setSypm_v(sypm2.toString());
							elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm2));
						}
					}
				}
			}//end
		}else {
			//最高额贷款
			if(tempId == 254){
				// 6
				if(null != vEnterProject.getCreditGrantingMoney() && null != vEnterProject.getCreditGrantingTerm() && null != vEnterProject.getPremiumrate()){
					Double pretb = (vEnterProject.getCreditGrantingMoney()*10000) * vEnterProject.getCreditGrantingTerm() * (vEnterProject.getPremiumrate()/100);
					elementCode.setPret_v(pretb.toString());
					elementCode.setPretb_v(MoneyFormat.getInstance().hangeToBig(pretb));
					Double tfypm = (vEnterProject.getCreditGrantingMoney()*10000) * (vEnterProject.getPremiumrate()/100);
					elementCode.setTfypm_v(tfypm.toString());
					elementCode.setTfypmb_v(MoneyFormat.getInstance().hangeToBig(tfypm));
					int month = vEnterProject.getCreditGrantingTerm() - 12;
					if(month<=12){
						Double sypm0 = (vEnterProject.getCreditGrantingMoney()*10000) * 1 * (vEnterProject.getPremiumrate()/100) ;
						elementCode.setSypm_v(sypm0.toString());
						elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm0));
					}else{
						int yu = month % 12 ;
						int year = month / 12 ;
						if(yu < 5){
							Double sypm1 = (vEnterProject.getCreditGrantingMoney()*10000) * year * (vEnterProject.getPremiumrate()/100) ;
							elementCode.setSypm_v(sypm1.toString());
							elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm1));
						}else{
							Double sypm2 = (vEnterProject.getCreditGrantingMoney()*10000) * (year+1) * (vEnterProject.getPremiumrate()/100) ;
							elementCode.setSypm_v(sypm2.toString());
							elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm2));
						}
					}
				}
			}if(tempId == 255){
				// 7
				if(null != vEnterProject.getCreditmoney() && null != vEnterProject.getCreditterm() && null != vEnterProject.getPremiumrate()){
					Double pretb = (vEnterProject.getCreditmoney()*10000) * vEnterProject.getCreditterm() * (vEnterProject.getPremiumrate()/100);
					elementCode.setPret_v(pretb.toString());
					elementCode.setPretb_v(MoneyFormat.getInstance().hangeToBig(pretb));
				}
			}if(tempId == 256){
				//8
				if(null != vEnterProject.getCreditmoney() && null != vEnterProject.getCreditterm() && null != vEnterProject.getPremiumrate()){
					Double pretb = (vEnterProject.getCreditGrantingMoney()*10000) * vEnterProject.getCreditGrantingTerm() * (vEnterProject.getPremiumrate()/100);
					elementCode.setPret_v(pretb.toString());
					elementCode.setPretb_v(MoneyFormat.getInstance().hangeToBig(pretb));
					Double tfypm = (vEnterProject.getCreditGrantingMoney()*10000) * (vEnterProject.getPremiumrate()/100);
					elementCode.setTfypm_v(tfypm.toString());
					elementCode.setTfypmb_v(MoneyFormat.getInstance().hangeToBig(tfypm));
					int month = vEnterProject.getCreditterm() - 12;
					if(month<=12){
						Double sypm0 = (vEnterProject.getCreditGrantingMoney()*10000) * 1 * (vEnterProject.getPremiumrate()/100) ;
						elementCode.setSypm_v(sypm0.toString());
						elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm0));
					}else{
						int yu = month % 12 ;
						int year = month / 12 ;
						if(yu < 5){
							Double sypm1 = (vEnterProject.getCreditGrantingMoney()*10000) * year * (vEnterProject.getPremiumrate()/100) ;
							elementCode.setSypm_v(sypm1.toString());
							elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm1));
						}else{
							Double sypm2 = (vEnterProject.getCreditGrantingMoney()*10000) * (year+1) * (vEnterProject.getPremiumrate()/100) ;
							elementCode.setSypm_v(sypm2.toString());
							elementCode.setSypmb_v(MoneyFormat.getInstance().hangeToBig(sypm2));
						}
					}
				}
			}//end
		}
		Date now = new Date();  
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式  
		String nowDate = dateFormat.format(now);  
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改  
		int year = c.get(Calendar.YEAR);  
		int month = c.get(Calendar.MONTH)+1;  
		int date = c.get(Calendar.DATE)+30;  
		String endDate = "" ;
		if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12 ){
			date = date - 31 ;
			month = month + 1 ;
			if(month > 12){
				year = year + 1 ;
				month = 1 ;
			}
		}else if(month == 4 || month == 6 || month == 9 || month == 11){
			date = date - 30 ;
			month = month + 1 ;
		}else if(month == 2){
			if((year%4 == 0)&&((year%100 != 0)|(year%400 == 0))){
				date = date - 29 ;
				month = month + 1 ;
			}else{
				date = date - 28 ;
				month = month + 1 ;
			}
		}if(month <= 9){
			endDate = year+"-0"+month;
		}else{
			endDate = year+"-"+month;
		}if(date <= 9){
			endDate = endDate+"-0"+date;
		}else{
			endDate = endDate+"-"+date;
		}
		elementCode.setStartdate_v(nowDate);
		elementCode.setEnddate_v(endDate);
		if(vEnterProject.getProjnum() != null){
			elementCode.setNdbyxsbh_v(vEnterProject.getProjnum());
		}
		//个人经营性贷款 个人信息add by chencc
		if(vEnterProject.getPersonCustomerId()!=null){
			String jkrjjzz="";
			VPersonDic person=(VPersonDic)creditBaseDao.getById(VPersonDic.class,vEnterProject.getPersonCustomerId());
			if(person.getTelphone()!=null){
				elementCode.setJkrjtdh_v(person.getTelphone());
			}
			jkrjjzz=person.getShengvalue()!=null?person.getShengvalue():"";
			jkrjjzz+=person.getShivalue()!=null?person.getShivalue():"";
			jkrjjzz+=person.getXianvalue()!=null?person.getXianvalue():"";
			jkrjjzz+=person.getRoadname()!=null?person.getRoadname():"";
			jkrjjzz+=person.getRoadnum()!=null?person.getRoadnum()+"号路":"";
			jkrjjzz+=person.getCommunityname()!=null?person.getCommunityname():"";
			jkrjjzz+=person.getDoorplatenum()!=null?person.getDoorplatenum()+"号":"";
			elementCode.setJkrjtzz_v(jkrjjzz);
			
			if(person.getCellphone()!=null){
				elementCode.setJkrsj_v(person.getCellphone());
			}
			if(person.getName()!=null){
				elementCode.setJkrxm_v(person.getName());
			}
			if(person.getCardnumber()!=null){
				elementCode.setJkrzjhm_v(person.getCardnumber());
			}
			if(person.getCardtypevalue()!=null){
				elementCode.setJkrzjlx_v(person.getCardtypevalue());
			}
		}*/
		
		return elementCode ;
	}
	public AssureIntentBookElementCode getAssureBookElement(String projId,String dStartTime,String dEndTime) throws Exception {
		AssureIntentBookElementCode assureIntentBookElementCode = new AssureIntentBookElementCode() ;
		return assureIntentBookElementCode ;
	}
	public boolean updateCon(String hql ,Object[] obj)throws Exception {
		return creditBaseDao.excuteSQL(hql, obj);
	}
	
	
	public boolean deleteFileContractById(int id){
		boolean flag=false;
		try {
			ProcreditContract contract=(ProcreditContract)creditBaseDao.getById(ProcreditContract.class, id);
			if(contract!=null){
				creditBaseDao.deleteDatas(contract);
			}
			flag=true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag=false;
			logger.error("删除合同出错:"+e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	public SlSmallloanProject findByEnterProjectId(String id) throws Exception {
		SlSmallloanProject smallProject = null;
		if(id != null&&id!=""){
			Long id_long = Long.parseLong(id);
			smallProject=(SlSmallloanProject)creditBaseDao.getById(SlSmallloanProject.class, id_long);
		}
		
//		if(smallProject == null) {
//			EnterpriseChildLoanProject entChildLoanProject = (EnterpriseChildLoanProject)creditBaseDao.getById(EnterpriseChildLoanProject.class, id);
//			entProject = (EnterpriseProject)creditBaseDao.getById(EnterpriseProject.class, entChildLoanProject.getProjectId());
//		}
		return smallProject;
//		return null;
	}
	
	
	
	public PlPawnProject findByPawnProjectId(String id) throws Exception{
		Long id_long = Long.parseLong(id);
		return (PlPawnProject)creditBaseDao.getById(PlPawnProject.class, id_long);
	}
	


	
	public boolean ajaxValida(int id){
		boolean flag = false ;
		try {
			ProcreditContract contract =procreditContractDao.getById(id);
			if(contract != null){
				if(contract.getUrl() != null || contract.getUrl() != ""){
					flag = true ;
				}
			}
		} catch (Exception e) {
			flag = false ;
			logger.error("验证合同是否存在出错:"+e.getMessage());
			e.printStackTrace();
		}
		return flag ;
	}
	//by chencc
	public boolean ajaxFileValida(String mark){
		boolean flag = false ;
		try {
			List list=fileFormDao.listByMark(mark);
			if(list!=null){
				flag=true;
			}
		} catch (Exception e) {
			flag = false ;
			logger.error("验证文件是否存在出错:"+e.getMessage());
			e.printStackTrace();
		}
		return flag ;
	}
	
	//生成客户承诺函保存一条记录到合同表，如果存在则不添加;
	public boolean saveAcceptanceLetter(String projectId,DocumentTemplet templet,int contractType,String templetName){
		
		boolean isExitsORSaveOK = false;
		List<ProcreditContract> contractList = null;
		try {
			contractList =procreditContractDao.listByTemplateId(projectId, templet.getId()) ;
			if(contractList == null || contractList.size() == 0){
				ProcreditContract procreditContract = new ProcreditContract();
				procreditContract.setProjId(projectId);
				procreditContract.setContractName(templetName);
				procreditContract.setTemplateId(templet.getId());
				procreditContract.setContractType(contractType);
				procreditContractDao.save(procreditContract);
				isExitsORSaveOK = true;
			}else{
				isExitsORSaveOK = true;
			}
		} catch (Exception e) {
			logger.error("生成客户承诺函保存一条记录到合同表，如果存在则不添加出错:"+e.getMessage());
			e.printStackTrace();
		}
		
		return isExitsORSaveOK;
	}
	
	
	//上传客户承诺函需要的合同id
	public void ajaxGetContractIdForUpLoadFile(String projId,DocumentTemplet templet){
		
		VProcreditContract proc = null;
		List<VProcreditContract> contractList = null;
		try {
			contractList =vProcreditContractDao.listByTemplateId(projId, templet.getId());
			if(contractList == null || contractList.size() == 0){
				JsonUtil.jsonFromObject(null, true);
			}else{
				proc = (VProcreditContract)contractList.get(0);
				JsonUtil.jsonFromObject(proc, true);
			}
		} catch (Exception e) {
			logger.error("上传客户承诺函需要的合同id出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void updateProcreditContractById(ProcreditContract procreditContract){
		AppUser user=ContextUtil.getCurrentUser();
		ProcreditContract pc = null;
		try {
			pc =procreditContractDao.getById(procreditContract.getId());
			if(pc !=null){
				if(procreditContract.getSignDate()!=null){
					pc.setSignDate(procreditContract.getSignDate());
				}
				if(procreditContract.getIssign()!=null){
					pc.setIssign(procreditContract.getIssign());
					pc.setIssignId(Integer.parseInt(user.getId()));
				}
				if(procreditContract.getIsLegalCheck()!=null){
					pc.setIsLegalCheck(procreditContract.getIsLegalCheck());
					pc.setLegalCheckDate(new Date());
					pc.setLegalCheckpId(Integer.parseInt(user.getId()));
				}
				if(procreditContract.getIsRecord()!=null){
					pc.setIsRecord(procreditContract.getIsRecord());
					pc.setRecordDate(new Date());
					pc.setRecordId(Integer.parseInt(user.getId()));
				}
				if(procreditContract.getRecordRemark()!=null) {
					pc.setRecordRemark(procreditContract.getRecordRemark());
				}
				procreditContractDao.merge(pc);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("更新合同出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	

	
	public String getNumber(String projId,String temptId){
		int count = 0;
		String rn = "0";
		
		List<ProcreditContract> list = null;
		try {
			list = procreditContractDao.listByTemplateId(projId, Integer.parseInt(temptId));
			ProcreditContract pc = null;
			String number = "0";
			if(list != null && list.size()>0){
				//获得合同编号数组
				int []array = new int[list.size()];
				for(int i = 0;i<list.size();i++){
					pc = (ProcreditContract)list.get(i);
					try{//判断合同编号是否符合规则
						number = pc.getContractNumber().substring(pc.getContractNumber().lastIndexOf("-")+1);
						if(number.subSequence(0, 1).equals("0")){
							number = number.subSequence(1, 2).toString();
						}
					}catch(Exception e){
						number = "0";
					}
					array[i] = Integer.parseInt(number);
				}
				if(array.length>1){//取得合同编号的最大值
					for(int j =0;j<array.length-1;j++){
						if(array[j+1]>array[j]){
							count = array[j+1];
						}else{
							count = array[j];
						}
					}
				}else{
					count = 1;
				}
				
//				count = list.size();
			}else{
				count = 0;
			}
			if(count <9){
				rn = "0"+(count+1);
			}else{
				rn = count+1+"";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rn = "00";
		}
		
		return rn;
	}
	public SmallLoanElementCode getInterntElementBySystem(String slfundintentId,String rnum) throws Exception{
		

		DecimalFormat myFormatter = new DecimalFormat("####.#");
		SmallLoanElementCode elementCode = new SmallLoanElementCode();
		SlFundIntent slfundintent =(SlFundIntent)creditBaseDao.getById(SlFundIntent.class, Long.parseLong(slfundintentId)) ; //项目信息实体
		SlSmallloanProject smallloanProject =(SlSmallloanProject)creditBaseDao.getById(SlSmallloanProject.class, slfundintent.getProjectId());//项目信息实体
		//EnterpriseView enterv = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID().intValue());
		/*
		 * 合同编号
		 * 合同文档名称：客户简称+项目编号+合同名称+01(同类合同依次相加)
		 * 合同编号：项目编号 +合同特定代表（如1）+01(同类合同依次相加)
		 * 合同类型代表：借款合同1，保证合同2，抵押合同3，权利质押合同4，财务协议5，股东会决议6
		 * */
		
		
		/*
		 * 项目表相关信息
		 * 贷款金额，币种，贷款利率，贷款起始日，贷款结束日等等
		 * */
		//贷款本金金额大小写
		if(slfundintent.getNotMoney()!=null){
			BigDecimal projectMoney = slfundintent.getNotMoney();
			String dw = "元整";
			/*if(projectMoney > 10000.0){
				dw = "万元";
			}*/
			elementCode.setDkjexx_v(myFormatter.format(projectMoney).toString()+"元");
			elementCode.setDkjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
		}
		
		//贷款起始日
		if(smallloanProject.getStartDate()!= null){
			String startDateStr = smallloanProject.getStartDate().toString().substring(0,smallloanProject.getStartDate().toString().lastIndexOf(" "));
			elementCode.setDkqsrq_v(MoneyFormat.getInstance().formatDate(startDateStr));
		}
		//贷款结束日
	/*	if(smallloanProject.getIntentDate()!= null){
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			elementCode.setDkdqrq_v(sd.format(smallloanProject.getIntentDate()));
		}*/
	
		
		
		
		
		

		
		if(smallloanProject.getOppositeType().equals("person_customer")){
			Person person = null;
			person = (Person)creditBaseDao.getById(Person.class, smallloanProject.getOppositeID().intValue());
			if(person.getName()!=null){
				elementCode.setZqrmc_v(person.getName());//债务人
				elementCode.setJkrmc_v(person.getName());//甲方（借款人名称）
			}
		
		}else if(smallloanProject.getOppositeType().equals("company_customer")){
			EnterpriseView enterprise = null;
			enterprise = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID().intValue());
			if(enterprise.getEnterprisename()!= null){
				elementCode.setZqrmc_v(enterprise.getEnterprisename());
				elementCode.setJkrmc_v(enterprise.getEnterprisename());//甲方（借款方公司名称）
			}
	
		}
		/*
		 * 我方主体-债权人
		 * 个人person_ourmain，企业company_ourmain
		 * */
		if(smallloanProject.getMineType().equals("person_ourmain")){//我方主体为个人
			SlPersonMain personMain = null;
			if(smallloanProject.getMineId()!=0){
				personMain = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, smallloanProject.getMineId());
				if(personMain != null){
					if(personMain.getName()!=null){
						elementCode.setZqrmc_v(personMain.getName());
					}
					if(personMain.getAddress()!=null){
						elementCode.setZqrdz_v(personMain.getAddress());
					}
					if(personMain.getPostalCode()!= null){
						elementCode.setZqryb_v(personMain.getPostalCode());//债权人邮政编码
					}
					if(personMain.getLinktel()!=null){
						elementCode.setZqrdh_v(personMain.getLinktel());
					}
					if(personMain.getTax()!= null){
						elementCode.setZqrcz_v(personMain.getTax());//债权人传真号码
					}
				}
			}
//		}else if(smallloanProject.getMineType().equals("company_ourmain")){//我方主体为企业    update by gao
		}else if(smallloanProject.getMineType().equals("company_ourmain")&&!"true".equals(AppUtil.getSystemIsGroupVersion())){//我方主体为企业    （集团版的时候 有可能mineId存入分公司）   update by gao
			SlCompanyMain companyMain = null;
			if(smallloanProject.getMineId()!=0){
				companyMain = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, smallloanProject.getMineId());
				if(companyMain != null){
					if(companyMain.getCorName()!= null){
						elementCode.setZqrmc_v(companyMain.getCorName());
					}
					if(companyMain.getMessageAddress() != null){
						elementCode.setZqrdz_v(companyMain.getMessageAddress());
					}
					if(companyMain.getPostalCode()!= null){
						elementCode.setZqryb_v(companyMain.getPostalCode());//债权公司邮政编码
					}
					if(companyMain.getTel()!= null){
						elementCode.setZqrdh_v(companyMain.getTel());
					}
					if(companyMain.getTax()!= null){
						elementCode.setZqrcz_v(companyMain.getTax());
					}
					if(companyMain.getLawName()!=null){
						elementCode.setZqffddbr_v(companyMain.getLawName());//债权方法定代表人
					}
				}
			}
		}else{//集团版 我方主体为organization
			Organization org = null;
			if(smallloanProject.getCompanyId()!=0){
				org = (Organization)creditBaseDao.getById(Organization.class, smallloanProject.getCompanyId());
				if(org != null){
					if(org.getOrgName()!= null){
						elementCode.setZqrmc_v(org.getOrgName());
					}
					if(org.getAddress() != null){
						elementCode.setZqrdz_v(org.getAddress());
					}
					if(org.getPostCode()!= null){
						elementCode.setZqryb_v(org.getPostCode());//债权公司邮政编码
					}
					if(org.getLinktel()!= null){
						elementCode.setZqrdh_v(org.getLinktel());
					}
					if(org.getFax()!= null){
						elementCode.setZqrcz_v(org.getFax());
					}
					/*if(org.getFax()!=null){
						elementCode.setZqffddbr_v(org.getFax());//债权方法定代表人
					}*/
				}
			}
		
		}
		return elementCode;
	}
	@Override
	public SmallLoanElementCode getExhibitionElementBySystem(String proId,
			int conId, int tempId, String contractType, String rnum) {
		SmallLoanElementCode elementCode = new SmallLoanElementCode();
		DecimalFormat myFormatter = new DecimalFormat("####.#");
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		if(null!=proId && !"".equals(proId)){
			SlSuperviseRecord slSuperviseRecord=slSuperviseRecordDao.get(Long.valueOf(proId));
			if(null!=slSuperviseRecord){
				if(null!=slSuperviseRecord.getContinuationMoney()){
					elementCode.setZqje_v(myFormatter.format(slSuperviseRecord.getContinuationMoney())+"元");
					elementCode.setZqjedx_v(MoneyFormat.getInstance().hangeToBig(slSuperviseRecord.getContinuationMoney())+"元");
				}
				if(null!=slSuperviseRecord.getEndDate()){
					elementCode.setZqdqr_v(sd.format(slSuperviseRecord.getEndDate()));
				}
				if(null!=slSuperviseRecord.getContinuationRate()){
					elementCode.setZqll_v(slSuperviseRecord.getContinuationRate().toString());
				}
				ProcessRun pr = processRunDao.getByBusinessTypeProjectId(Long.valueOf(proId), "ExhibitionBusiness");
				ProcessForm pf = processFormDao.getByRunIdActivityName(pr.getRunId(), "展期风险审核");
				ProcessForm pf1 = processFormDao.getByRunIdActivityName(pr.getRunId(), "展期审核");
				if(null!=pf && null!=pf.getComments()){
					elementCode.setZqfxshyj_v(pf.getComments());
				}
				if(null!=pf1 && null!=pf1.getComments()){
					elementCode.setZqshyj_v(pf1.getComments());
				}
				
			}
		}
		return elementCode;
	}
	
	//给贷款审查审批表文档 要素里面填值的方法（add  by  liny 2013-2-21）
	@Override
	public SmallLoanElementCode getElementBySystemReviewTable(String projectID,
			int conId, int tempId, String htType, String rnum) {
		// TODO Auto-generated method stub
		SmallLoanElementCode elementCode = new SmallLoanElementCode();
		return elementCode;
	}
	@Override
	public PawnElementCode getPawnElementBySystem(String proId, int conId,
			int tempId, String contractType, String rnum,Long dwId) throws Exception {
		PlPawnProject project = plPawnProjectService.get(Long.valueOf(proId));
		ProcreditContract contract = null;
		if(0 !=conId){
			contract = (ProcreditContract)creditBaseDao.getById(ProcreditContract.class, conId);
		}
		VProcreditMortgageGlobal mortgage = null;
		PawnItemsList pawnItem= null;
		
		
		if("true".equals(AppUtil.getSystemIsGroupVersion())){//集团版本   我方主体为 orgazation表   以companyId关联，也可用MineId关联，但要保证二者一致
			Organization org = organizationDao.get(Long.valueOf(project.getCompanyId().toString()));
			project.setMineName(org.getOrgName());
		}else{
			if("company_ourmain".equals(project.getMineType())){
				SlCompanyMain com=(SlCompanyMain) creditBaseDao.getById(SlCompanyMain.class, project.getMineId());
				project.setMineName(com.getCorName());
			}else{
				SlPersonMain p = (SlPersonMain) creditBaseDao.getById(SlPersonMain.class,project.getMineId());
				project.setMineName(p.getName());
			}
		}
		if(contract != null&&contract.getMortgageId()!= 0 && ("thirdContract".equals(contractType) || "baozContract".equals(contractType))){
			mortgage = (VProcreditMortgageGlobal)creditBaseDao.getById(VProcreditMortgageGlobal.class, contract.getMortgageId());
		}
		if(null != dwId){
			pawnItem = (PawnItemsList)creditBaseDao.getById(PawnItemsList.class, dwId);
		}
		PawnElementCode elementCode = new PawnElementCode();
		new ElementUtilExt(project,rnum,mortgage,pawnItem).updateElement(elementCode);
		return elementCode;
	}
	@Override
	public LeaseFinanceElementCode getLeaseFinanceElementBySystem(String proId, int conId,
			int tempId, String contractType, String rnum,Long leaseObjectInfoId) throws Exception {
		FlLeaseFinanceProject project = flleaseFinanceProjectService.get(Long.valueOf(proId));
		ProcreditContract contract = (ProcreditContract)creditBaseDao.getById(ProcreditContract.class, conId);
		VProcreditMortgageLeaseFinance mortgage = null;
		if(contract != null && contract.getMortgageId()!= 0 && contract.getMortgageId()!=null && ("thirdContract".equals(contractType) || "baozContract".equals(contractType))){
			mortgage = (VProcreditMortgageLeaseFinance)creditBaseDao.getById(VProcreditMortgageLeaseFinance.class, contract.getMortgageId());
		}
		LeaseFinanceElementCode elementCode =new LeaseFinanceElementCode();
		new ElementUtilExt(project,leaseObjectInfoId,rnum,mortgage).updateElement(elementCode);
		
		return elementCode;
	}
	@Override
	public AssignmentElementCode getAssignmentElementBySystem()
			throws Exception {
		AssignmentElementCode code=new AssignmentElementCode();
		return code;
	}
	@Override
	public SmallLoanElementCode getElementBySystem(String proId, int conId,
			int tempId, String contractType, String rnum, String comments,
			InvestPersonInfo investPerson) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINESE);
		DecimalFormat myFormatter = new DecimalFormat("####.#");
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdzw=new SimpleDateFormat("yyyy年MM月dd日");
		SmallLoanElementCode elementCode = new SmallLoanElementCode();
		SlSmallloanProject smallloanProject =(SlSmallloanProject)creditBaseDao.getById(SlSmallloanProject.class, Long.parseLong(proId)) ; //项目信息实体
		//EnterpriseView enterv = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID().intValue());
		ProcreditContract contract = (ProcreditContract)creditBaseDao.getById(ProcreditContract.class, conId);
		//ProcreditContractCategory contractCategory = (ProcreditContractCategory)creditBaseDao.getById(ProcreditContractCategory.class, conId);
		/*
		 * 合同编号
		 * 合同文档名称：客户简称+项目编号+合同名称+01(同类合同依次相加)
		 * 合同编号：项目编号 +合同特定代表（如1）+01(同类合同依次相加)
		 * 合同类型代表：借款合同1，保证合同2，抵押合同3，权利质押合同4，财务协议5，股东会决议6
		 * */
		//合同编号
		if(smallloanProject.getProjectNumber() != null){
			elementCode.setJkhtbh_v(contract.getContractNumber());
			elementCode.setBzdbhtbh_v(contract.getContractNumber());
			elementCode.setJjbh_v(contract.getContractNumber());		
		}
		//债权人相关信息
		Organization organization = organizationDao.get(smallloanProject.getMineId());
		if(null!=organization){
			if(organization.getOrgName() != null) {
				elementCode.setZqrmc_v(organization.getOrgName());
			}
			if(organization.getAddress() != null) {
				elementCode.setZqrdz_v(organization.getAddress());
			}
			if(organization.getPostCode() != null) {
				elementCode.setZqryb_v(organization.getPostCode());
			}
			if(organization.getLinktel() != null) {
				elementCode.setZqrdh_v(organization.getLinktel());
			}
			if(organization.getFax() != null) {
				elementCode.setZqrcz_v(organization.getFax());
			}
			if(organization.getLinkman()!=null){
				elementCode.setZqffddbr_v(organization.getLinkman());
			}
		}
		/*
		 * 项目表相关信息
		 * 贷款金额，币种，贷款利率，贷款起始日，贷款结束日等等
		 * */
		//币种
		if(smallloanProject.getCurrency() != null){
			Dictionary dictionary = null;
			dictionary = (Dictionary)creditBaseDao.getById(Dictionary.class, smallloanProject.getCurrency());
			if(dictionary != null){
				elementCode.setBz_v(dictionary.getItemValue());
			}
		}
		//贷款利率
		if(smallloanProject.getAccrual()!=null){
			elementCode.setDkyll_v(smallloanProject.getAccrual().toString() + "%");
		}
		
		//还款固定日
		if(smallloanProject.getPayintentPerioDate()!=null&&smallloanProject.getIsStartDatePay()!=null&&smallloanProject.getIsStartDatePay().equals("1")){
			elementCode.setHkgdr_v((smallloanProject.getPayintentPerioDate()-1)+"日");
		}
	
		//贷款本金金额大小写
		if(smallloanProject.getProjectMoney()!=null){
			BigDecimal projectMoney = smallloanProject.getProjectMoney();
			String dw = "元整";
			elementCode.setDkjexx_v(myFormatter.format(projectMoney).toString()+"元");
			elementCode.setDkjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
		}
		//月还款本息金额大小写
		if(smallloanProject.getProjectMoney()!=null&&smallloanProject.getPayintentPeriod()!=null&&smallloanProject.getAccrual()!=null&smallloanProject.getPayaccrualType()!=null&&smallloanProject.getPayaccrualType().equals("monthPay")&&smallloanProject.getAccrualtype()!=null&&smallloanProject.getAccrualtype().equals("sameprincipalsameInterest")){
			BigDecimal period = new BigDecimal(smallloanProject.getPayintentPeriod()).setScale(2);
			BigDecimal monthpayMoney = smallloanProject.getProjectMoney().divide(period,2,BigDecimal.ROUND_HALF_UP).add(smallloanProject.getProjectMoney().multiply(smallloanProject.getAccrual()).divide(new BigDecimal(100)));
			BigDecimal monthpayMoneyRoundUp = monthpayMoney.setScale(0, BigDecimal.ROUND_UP);
			elementCode.setYhbxyqwyj_v(((monthpayMoneyRoundUp.divide(new BigDecimal(10))).compareTo(new BigDecimal(100))>0?monthpayMoneyRoundUp.divide(new BigDecimal(10)).setScale(2, BigDecimal.ROUND_UP):new BigDecimal(100)).toString());//违约金金额
			elementCode.setYhbxytfx_v(monthpayMoneyRoundUp.multiply(new BigDecimal(0.0075)).setScale(2, BigDecimal.ROUND_UP).toString());
			elementCode.setYhbxstfx_v(monthpayMoneyRoundUp.multiply(new BigDecimal(0.075)).setScale(2, BigDecimal.ROUND_UP).toString());
			BigDecimal monthpayDb = monthpayMoney.setScale(0,BigDecimal.ROUND_UP);
			elementCode.setYhbxjedx_v(MoneyFormat.getInstance().hangeToBig(monthpayDb)+"元");
			elementCode.setYhbxjexx_v(monthpayDb.toString()+"元");
		}
		//贷款本金金额(余额)大小写
		BigDecimal yueMoney = new BigDecimal("0");
		if(smallloanProject.getProjectMoney()!=null && smallloanProject.getPayProjectMoney() != null){
			BigDecimal projectMoney = investPerson.getInvestMoney();//smallloanProject.getProjectMoney().doubleValue();
			BigDecimal payProjectMoney = smallloanProject.getPayProjectMoney();
			yueMoney = projectMoney.subtract(payProjectMoney);
			String dw = "元整";
			elementCode.setDkjexx_v(myFormatter.format(projectMoney).toString()+"元");
			elementCode.setDkjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
			elementCode.setJkyedx_v(yueMoney.toString()+"元");
			elementCode.setJkyedx_v(MoneyFormat.getInstance().hangeToBig(yueMoney)+dw);
		}
		
		List<SlFundIntent> listloanInterest1=new ArrayList<SlFundIntent>();
		listloanInterest1=slFundIntentDao.listbyOwe("SmallLoan", Long.valueOf(proId), "('loanInterest')");
		BigDecimal  loanInterestMoney=new BigDecimal("0");
		BigDecimal  principalMoney=new BigDecimal("0");
		BigDecimal  punishMoney=new BigDecimal("0");
		BigDecimal  sumMoney=new BigDecimal("0");
		List<SlFundIntent>	listprincipalt=slFundIntentDao.listbyOwe("SmallLoan", Long.valueOf(proId), "('principalRepayment')");
		List<SlFundIntent> listloanInterest=slFundIntentDao.listbyOwe("SmallLoan", Long.valueOf(proId), "('loanInterest','consultationMoney','serviceMoney')");
		for(SlFundIntent inte:listloanInterest){
			loanInterestMoney=loanInterestMoney.add(inte.getNotMoney());
			List<SlPunishInterest> listpunish=slPunishInterestDao.listbyisInitialorId(inte.getFundIntentId());
			for(SlPunishInterest pun:listpunish){
				punishMoney=punishMoney.add(pun.getNotMoney());
				
			}
		}
		for(SlFundIntent pri:listprincipalt){
			principalMoney=principalMoney.add(pri.getNotMoney());
			List<SlPunishInterest> listpunish=slPunishInterestDao.listbyisInitialorId(pri.getFundIntentId());
			for(SlPunishInterest pun:listpunish){
				punishMoney=punishMoney.add(pun.getNotMoney());
				
			}	
		}
// add by gao start
		//所有还款款项   计算各期的还款金额
		List<SlFundIntent> fundPayList=slFundIntentDao.getByProjectId4( Long.valueOf(proId), "SmallLoan");
		Collections.sort(fundPayList,new FundIntentComparator());
		StringBuffer sb = new StringBuffer();
		int period = -1;
		BigDecimal tmpPeriodMoney = BigDecimal.ZERO;
		BigDecimal cwfwfMoney = BigDecimal.ZERO;
		String tempStr = "期数∏还款时间∏还款金额（元）";
		Date tmpPeriodDate = null;
		for(SlFundIntent sfi : fundPayList){
			if(sfi.getPayintentPeriod()==null)continue;
			if(sfi.getPayintentPeriod()!=period||fundPayList.indexOf(sfi)==(fundPayList.size()-1)){//期数变了，tempMoney 清零，StringBuffer加@
				if(period<0){
					sb.append(tempStr);
					period = sfi.getPayintentPeriod();
				}else{
					if(period==1&&elementCode.getHkqsr_v().equals(""))elementCode.setHkqsr_v(sdzw.format(tmpPeriodDate));
					if(fundPayList.indexOf(sfi)==(fundPayList.size()-1)){
						elementCode.setHkjzr_v(sdzw.format(sfi.getIntentDate()));
						tmpPeriodMoney = tmpPeriodMoney.add(sfi.getIncomeMoney());
						tmpPeriodDate = sfi.getIntentDate();
					}
					if(tmpPeriodMoney.compareTo(BigDecimal.ZERO)!=0){
						sb.append("＃"+period+"∏"+sd.format(tmpPeriodDate)+"∏"+tmpPeriodMoney.setScale(0,BigDecimal.ROUND_UP));//逢位进一
					}
					period = sfi.getPayintentPeriod();
					tmpPeriodMoney=sfi.getIncomeMoney();
				}
			}else{
				tmpPeriodMoney = tmpPeriodMoney.add(sfi.getIncomeMoney());
				tmpPeriodDate = sfi.getIntentDate();
			}
			if(sfi.getFundType().equals("serviceMoney"))cwfwfMoney = cwfwfMoney.add(sfi.getIncomeMoney());
		}
		elementCode.setCwfwf_v(cwfwfMoney.setScale(0,BigDecimal.ROUND_UP).toString());
		elementCode.setCwfwfdx_v(MoneyFormat.getInstance().hangeToBig(cwfwfMoney.setScale(0,BigDecimal.ROUND_UP)));
		elementCode.setHklb_v(sb.toString());
		//共同借款人
		StringBuffer sb2 = new StringBuffer();//大连天储借款人信息
		StringBuffer sb3 = new StringBuffer();//大连天储借款人名称
		//企业   股东信息
		if(smallloanProject.getOppositeType().equals("company_customer")&&smallloanProject.getOppositeID()!=null){
			EnterpriseView enterprise = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID().intValue());
			sb2.append(enterprise.toElementStr());
			sb3.append(enterprise.getEnterprisename());
			List<EnterpriseShareequity> shareequityList = enterpriseShareequityDao.findShareequityList(smallloanProject.getOppositeID().intValue());
			if(shareequityList !=null&&shareequityList.size()>0){
				for(EnterpriseShareequity es : shareequityList){
					if(es.getPersonid()!=null){
						Person person = personDao.getById(es.getPersonid());
						sb2.append(person.toElementStr());
						sb3.append(","+person.getName());
					}else if(es.getEnterpriseid()!=null){
						Enterprise enterTemp = enterpriseService.getById(es.getEnterpriseid());
						sb2.append(enterTemp.toElementStr());
						sb3.append(","+enterTemp.getEnterprisename());
					}
				}
			}
		}else{
			Person person = personDao.getById(smallloanProject.getOppositeID().intValue());
			sb2.append(person.toElementStr());
			sb3.append(person.getName());
			if(person.getMarry()==317){
				Spouse spouse = spouseDao.getByPersonId(person.getId());
				if(spouse!=null){
					sb2.append(spouse.toElementStr());
					sb3.append(","+spouse.getName());
				}
			}
		}
		List<BorrowerInfo> borrowerinfoList = borrowerInfoDao.getBorrowerListDetail(smallloanProject.getProjectId());
		if(borrowerinfoList!=null&&borrowerinfoList.size()>0){
			for(BorrowerInfo bi : borrowerinfoList){
				sb2.append(bi.toElementStr());
				sb3.append(","+bi.getCustomerName());
			}
		}
		elementCode.setDltcjkrmc_v(sb3.toString());
		elementCode.setDltcjkrxx_v(sb2.toString());
		
		
//add by gao end 
		sumMoney=loanInterestMoney.add(principalMoney).add(punishMoney).add(yueMoney);
		String str = simpleDateFormat.format(new Date());
		//截止日期
		elementCode.setJzrq_v(simpleDateFormat.format(new Date()));
		//利息余额大小写
		elementCode.setLxyedx_v(loanInterestMoney.doubleValue()==0?"零元整":MoneyFormat.getInstance().hangeToBig(loanInterestMoney)+"元整");
		elementCode.setLxyexx_v(loanInterestMoney.doubleValue()+"元");
		//罚息余额大小写
		elementCode.setFxyedx_v(punishMoney.doubleValue()==0?"零元整":MoneyFormat.getInstance().hangeToBig(punishMoney)+"元整");
		elementCode.setFxyexx_v(punishMoney.doubleValue()+"元");
		//合计余额大小写
		elementCode.setHjyedx_v(sumMoney.doubleValue()==0?"零元整":MoneyFormat.getInstance().hangeToBig(sumMoney)+"元整");
		elementCode.setHjyexx_v(sumMoney.toString()+"元");
		
		//借款用途
		if(smallloanProject.getLoanPurpose()!=null){
			elementCode.setJkyt_v(smallloanProject.getLoanPurpose());
		}
		//贷款起始日期
		if(smallloanProject.getStartDate()!= null){
		//	String startDateStr = smallloanProject.getStartDate().toString().substring(0,smallloanProject.getStartDate().toString().lastIndexOf(" "));
			elementCode.setDkqsrq_v(sd.format(smallloanProject.getStartDate()));
		}
		//投资人
		elementCode.setInvest_v(investPerson.getInvestPersonName());
		
		//投资人申请号
		elementCode.setTzsqh_v("胜鼎(投)"+investPerson.getInvestId());
		
		/*if(smallloanProject.getEndDate()!= null){
			String endDateStr = smallloanProject.getEndDate().toString().substring(0,smallloanProject.getEndDate().toString().lastIndexOf(" "));
			elementCode.setDkdqrq_v(MoneyFormat.getInstance().formatDate(endDateStr));
		}*/
		//贷款期限(月)
		if(smallloanProject.getStartDate()!= null) {
			if(null!=smallloanProject.getPayaccrualType()){
				if(smallloanProject.getPayaccrualType().equals("monthPay")){
					if(null!=smallloanProject.getPayintentPeriod()){
						 elementCode.setDkqx_v(smallloanProject.getPayintentPeriod()+"个月");
					}
				}else if(smallloanProject.getPayaccrualType().equals("seasonPay")){
					if(null!=smallloanProject.getPayintentPeriod()){
						 elementCode.setDkqx_v(smallloanProject.getPayintentPeriod()*3+"个月");
					}
				}else if(smallloanProject.getPayaccrualType().equals("yearPay")){
					if(null!=smallloanProject.getPayintentPeriod()){
						 elementCode.setDkqx_v(smallloanProject.getPayintentPeriod()*12+"个月");
					}
				}else if(smallloanProject.getPayaccrualType().equals("dayPay")){
					if(null!=smallloanProject.getPayintentPeriod()){
						 elementCode.setDkqx_v(smallloanProject.getPayintentPeriod()+"天");
					}
				}else if(smallloanProject.getPayaccrualType().equals("owerPay")){
					if(null!=smallloanProject.getDayOfEveryPeriod() && null!=smallloanProject.getPayintentPeriod()){
						 elementCode.setDkqx_v(smallloanProject.getPayintentPeriod()*smallloanProject.getDayOfEveryPeriod()+"天");
					}
				}
			}
			//贷款到期日期
			if(!smallloanProject.getAccrualtype().equals("ontTimeAccrual")){
				if(smallloanProject.getPayaccrualType().equals("monthPay")){
					Date intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(smallloanProject.getStartDate(), smallloanProject.getPayintentPeriod()), -1);
					elementCode.setDkdqrq_v(sd.format(intentDate));
				}else if(smallloanProject.getPayaccrualType().equals("monthPay")){
					Date intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(smallloanProject.getStartDate(), smallloanProject.getPayintentPeriod()*3), -1);
					elementCode.setDkdqrq_v(sd.format(intentDate));
				}else if(smallloanProject.getPayaccrualType().equals("yearPay")){
					Date intentDate=DateUtil.addDaysToDate(DateUtil.addMonthsToDate(smallloanProject.getStartDate(), smallloanProject.getPayintentPeriod()*12), -1);
					elementCode.setDkdqrq_v(sd.format(intentDate));
				}else if(smallloanProject.getPayaccrualType().equals("dayPay")){
					Date intentDate=DateUtil.addDaysToDate(smallloanProject.getStartDate(), smallloanProject.getPayintentPeriod()-1);
					elementCode.setDkdqrq_v(sd.format(intentDate));
				}else if(smallloanProject.getPayaccrualType().equals("owerPay")){
					Date intentDate=DateUtil.addDaysToDate(smallloanProject.getStartDate(), smallloanProject.getPayintentPeriod()*smallloanProject.getDayOfEveryPeriod()-1);
					elementCode.setDkdqrq_v(sd.format(intentDate));
				}
			}else{
				elementCode.setDkdqrq_v(sd.format(smallloanProject.getIntentDate()));
			}
		/*	Date d1 = smallloanProject.getStartDate();
			Date d2 = smallloanProject.getIntentDate();
			SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar c = Calendar.getInstance();
	        c.setTime(d1);
	        int year1 = c.get(Calendar.YEAR);
	        int month1 = c.get(Calendar.MONTH);
	         
	        c.setTime(d2);
	        int year2 = c.get(Calendar.YEAR);
	        int month2 = c.get(Calendar.MONTH);
	         
	        Integer result;
	        if(year1 == year2) {
	            result = month1 - month2;
	        } else if(year1 > year2){
	            result = 12*(year1 - year2) + month1 - month2;
	        }else{
	        	result = 12*(year2 - year1) + month1 - month2;
	        }
			
	        elementCode.setDkqx_v(result.toString());*/
		}
		//还款方式
		if(smallloanProject.getAccrualtype() != null) {
			String accrualtypeValue = "";
			if("sameprincipal".equals(smallloanProject.getAccrualtype())) {
				accrualtypeValue = "等额本金";
			}else if ("sameprincipalandInterest".equals(smallloanProject.getAccrualtype())){
				accrualtypeValue = "等额本息";
			}else if ("singleInterest".equals(smallloanProject.getAccrualtype())){
				accrualtypeValue = "按期收息,到期还本";
			}else if ("ontTimeAccrual".equals(smallloanProject.getAccrualtype())){
				accrualtypeValue = "一次性还本付息";
			}
			elementCode.setHkfs_v(accrualtypeValue);
		}
		if(null!=smallloanProject.getCompanyId()){
			Organization org=organizationDao.get(smallloanProject.getCompanyId());
			elementCode.setWfzt_v(org.getOrgName());
		}
		if(null!=smallloanProject.getAppUserId()){
			String[] ids=smallloanProject.getAppUserId().split(",");
			String name="";
			for(int i=0;i<ids.length;i++){
				AppUser user=appUserDao.get(Long.valueOf(ids[i]));
				name=user.getFullname()+",";
			}
			name=name.substring(0,name.length()-1);
			elementCode.setXdy_v(name);
		}
		//担保方式
		VSmallloanProject vp = vSmallloanProjectDao.getByProjectId(Long.valueOf(proId));
		if(vp.getAssuretypeidValue()!=null) {
			elementCode.setDbfs_v(vp.getAssuretypeidValue());
		}
		if(null!=smallloanProject.getProjectId()){
			List<SlAlterAccrualRecord> list=slAlterAccrualRecordDao.getByProjectId(smallloanProject.getProjectId(),"SmallLoan");//◎
			if(null!=list && list.size()>0){
				SlAlterAccrualRecord record=list.get(0);
				if(null!=record.getAccrual()){
					elementCode.setNbgyll_v(record.getAccrual().toString());
				}
			}
			List<SlEarlyRepaymentRecord> elist=slEarlyRepaymentRecordDao.getByProjectId(smallloanProject.getProjectId());
			if(null!=elist && elist.size()>0){
				SlEarlyRepaymentRecord slEarlyRepaymentRecord=elist.get(0);
				if(slEarlyRepaymentRecord.getEarlyProjectMoney().compareTo(smallloanProject.getProjectMoney())==0){
					elementCode.setTqhkzl_v("全部提前结清");
				}else{
					elementCode.setTqhkzl_v("部分提前还款");
				}
			}
		}
		ProcessRun pr = processRunDao.getByBusinessTypeProjectId(Long.valueOf(proId), "SmallLoan");
		ProcessForm pf = processFormDao.getByRunIdActivityName(pr.getRunId(), "尽职调查");
		ProcessForm pf1 = processFormDao.getByRunIdActivityName(pr.getRunId(), "风险审核");
		ProcessForm pf2 = processFormDao.getByRunIdActivityName(pr.getRunId(), "贷款审查");
		ProcessForm pf3 = processFormDao.getByRunIdActivityName(pr.getRunId(), "放款审查");
		ProcessForm pf4 = processFormDao.getByRunIdActivityName(pr.getRunId(), "放款签批");
		ProcessForm pf5 = processFormDao.getByRunIdActivityName(pr.getRunId(), "提前还款申请");
		ProcessForm pf6 = processFormDao.getByRunIdActivityName(pr.getRunId(), "提前还款审批");
		ProcessForm pf7 = processFormDao.getByRunIdActivityName(pr.getRunId(), "利率变更申请");
		ProcessForm pf8 = processFormDao.getByRunIdActivityName(pr.getRunId(), "利率变更审核");
		ProcessForm pf9 = processFormDao.getByRunIdActivityName(pr.getRunId(), "利率变更审查");
		ProcessForm pf10 = processFormDao.getByRunIdActivityName(pr.getRunId(), "利率变更审批");
		ProcessForm pf11 = processFormDao.getByRunIdActivityName(pr.getRunId(), "确认终审通过意见");
		//业务经理意见
		if(null!=pf && pf.getComments() != null) {
			elementCode.setYwjlyj_v(pf.getComments());
		}
		//风险经理意见
		if(null!=pf1 && pf1.getComments() != null) {
			elementCode.setFxjlyj_v(pf1.getComments());
		}
		if(null!=pf2 && null!=pf2.getComments()){
			elementCode.setZjlyj_v(pf2.getComments());
		}
		if(null!=pf3 && null!=pf3.getComments()){
			elementCode.setFxfkscyj_v(pf3.getComments());
		}
		if(null!=comments){
			elementCode.setZjlfkqpyj_v(comments);

		}else{
			if(null!=pf4 && null!=pf4.getComments()){
				elementCode.setZjlfkqpyj_v(pf4.getComments());
			}
		}
		if(null!=pf5 && null!=pf5.getComments()){
			elementCode.setYwjltqhksqyj_v(pf5.getComments());
		}
		if(null!=pf6 && null!=pf6.getComments()){
			elementCode.setZjltqhkspyj_v(pf6.getComments());
		}
		if(null!=pf7 && null!=pf7.getComments()){
			elementCode.setYwjlllbgsqyj_v(pf7.getComments());
		}
		if(null!=pf8 && null!=pf8.getComments()){
			elementCode.setZjlllbgshyj_v(pf8.getComments());
		}
		if(null!=pf9 && null!=pf9.getComments()){
			elementCode.setKgllbgshyj_v(pf9.getComments());
		}
		if(null!=pf10 && null!=pf10.getComments()){
			elementCode.setKgllbgspyj_v(pf10.getComments());
		}
		if(null!=pf11 && null!=pf11.getComments()){
			elementCode.setZsyj_v(pf11.getComments());
		}
		
		//服务费用总额（大写）
		if(smallloanProject.getFinanceServiceOfRate()!= null && smallloanProject.getProjectMoney()!= null){
			//Double serviceMoney = (smallloanProject.getFinanceServiceOfRate().divide(BigDecimal.valueOf(100)).multiply(smallloanProject.getProjectMoney())).doubleValue();
			if(smallloanProject.getFinanceServiceOfRate().compareTo(BigDecimal.ZERO)!=0 ){
				String fundType = "";
				if(smallloanProject.getBusinessType().equals("SmallLoan")){
					fundType = "serviceMoney";
				}else{
					fundType = "financingserviceMoney";
				}
				Double serviceMoney = 0.0;
				List listMoney = null;
				Object [] obje = {smallloanProject.getProjectId(),smallloanProject.getBusinessType(),fundType};
				String hqlm ="from SlFundIntent where projectId = ? and businessType =? and fundType = ?";
				listMoney = creditBaseDao.queryHql(hqlm,obje);
				if(listMoney!= null && listMoney.size()>0){
					for(int i=0;i<listMoney.size();i++){
						SlFundIntent slFundIntent = (SlFundIntent)listMoney.get(i);
						serviceMoney += slFundIntent.getIncomeMoney().doubleValue();
					}
				}
				//服务费用总额（大写）
				//elementCode.setFwfyjedx_v(MoneyFormat.getInstance().hangeToBig(serviceMoney)+"元");
				//多笔支付款项内容(服务费列表)
				
				List<SlFundIntent> list = null;
				String hql = "from SlFundIntent AS f where f.projectId =? and f.fundType =? and f.businessType = ?";
				Object [] param ={Long.parseLong(proId),fundType,smallloanProject.getBusinessType()};
				list = creditBaseDao.queryHql(hql, param);
				if(list != null && list.size()>0){
					StringBuffer dbzfkxnrBuffer = new StringBuffer();
					String dbzfkxnr = "";
					for(int i = 0;i < list.size(); i++){
						SlFundIntent fi = (SlFundIntent)list.get(i);
						String intentDateStr = fi.getIntentDate().toString().substring(0,fi.getIntentDate().toString().lastIndexOf(" "));
						if(fundType == "serviceMoney" || "serviceMoney".equals(fundType)){
							dbzfkxnr = "费用支付时间："+MoneyFormat.getInstance().formatDate(intentDateStr)+"      "+"支付金额："+myFormatter.format(fi.getIncomeMoney())+"元\r\n";
						}else{
							dbzfkxnr = "费用支付时间："+MoneyFormat.getInstance().formatDate(intentDateStr)+"      "+"支付金额："+myFormatter.format(fi.getPayMoney())+"元\r\n";
						}
						dbzfkxnrBuffer.append(dbzfkxnr);
						
					}
//					elementCode.setDbzfkxnr_v(dbzfkxnrBuffer.toString());
				}
				
			}else{
//				elementCode.setFwfyjedx_v("零元");
			}
			
			
		}
		
		/*
		 * 客户类型-债务人
		 * 个人person_customer，企业company_customer
		 * */
		if(smallloanProject.getOppositeType().equals("person_customer")){
			VPersonDic person = (VPersonDic)creditBaseDao.getById(VPersonDic.class, smallloanProject.getOppositeID().intValue());
			//借款人申请号
			elementCode.setJksqh_v("胜鼎(借)"+person.getId());
			if(person.getName()!=null){
				elementCode.setJkrmc_v(person.getName());//甲方（借款人名称）
			}
			if(person.getCardtypevalue() != null) {
				elementCode.setJkrzjlx_v(person.getCardtypevalue().toString());
			}
			if(person.getCardnumber()!=null){
				elementCode.setJkrzjhm_v(person.getCardnumber());//借款证件号码
			}
			if(person.getPostaddress() != null){
				elementCode.setJkrdz_v(person.getPostaddress());
			}
			if(person.getCellphone()!= null){
				elementCode.setJkrlxdh_v(person.getCellphone());
			}
			if(person.getTelphone()!= null){
				elementCode.setJkrgddh_v(person.getTelphone());
			}
			if(person.getPostcode() != null){
				elementCode.setJkryzmb_v(person.getPostcode());
			}
			if(person.getFax() != null){
				elementCode.setJkrcz_v(person.getFax());
			}
			if(person.getSelfemail() != null) {
				elementCode.setJkrdzyx_v(person.getSelfemail());
			}
			if(smallloanProject.getBankId()!=null){
				EnterpriseBank epv = enterpriseBankDao.getById(smallloanProject.getBankId().intValue());
				if(epv != null) {
					if(epv.getName() != null) {
						elementCode.setJkrzhmc_v(epv.getName());
					}
					if(epv.getAccountnum() != null) {
						elementCode.setJkryhzh_v(epv.getAccountnum());
					}
					/*if(epv.getBankname() != null) {
						elementCode.setJkrkhyh_v(epv.getBankname());
						if(epv.getAreaName()!=null)elementCode.setJkrkhyh_v(epv.getBankname()+"_"+epv.getBankOutletsName());
					}*/
					if(epv.getBankid() != null) {
						CsBank cb = csBankDao.get(epv.getBankid());
						if(cb!=null){
							elementCode.setJkrkhyh_v(cb.getBankname());
							if(epv.getBankOutletsName()!=null)elementCode.setJkrkhyh_v(cb.getBankname()+"_"+epv.getBankOutletsName());
							
						}
					}
				}
			}else{
				EnterpriseBank epv = enterpriseBankDao.queryIscredit(person.getId(), Short.valueOf("1"), Short.valueOf("0"));
				if(epv != null) {
					if(epv.getName() != null) {
						elementCode.setJkrzhmc_v(epv.getName());
					}
					if(epv.getAccountnum() != null) {
						elementCode.setJkryhzh_v(epv.getAccountnum());
					}
					if(epv.getBankname() != null) {
						elementCode.setJkrkhyh_v(epv.getBankname());
					}
				}
			}
		}else if(smallloanProject.getOppositeType().equals("company_customer")){
			EnterpriseView enterprise = null;
			enterprise = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, smallloanProject.getOppositeID().intValue());
			//借款人申请号
			elementCode.setJksqh_v("胜鼎(借)"+enterprise.getId());
			if(enterprise.getEnterprisename()!= null){
				elementCode.setJkrmc_v(enterprise.getEnterprisename());
			}
			if(enterprise.getLegalperson()!= null){
				elementCode.setJkffddbr_v(enterprise.getLegalperson());//借款方法定代表人
			}
			if(enterprise.getCciaa() != null){
				elementCode.setJkryyzzdm_v(enterprise.getCciaa());//借款方营业执照号码
				elementCode.setJkrzjhm_v(enterprise.getCciaa());
			}
			if(enterprise.getOrganizecode() != null){
				elementCode.setJkrzzjgdm_v(enterprise.getOrganizecode());//借款方组织机构代码
			}
			if(enterprise.getArea()!= null){
				elementCode.setJkrdz_v(enterprise.getArea());
			}
			if(enterprise.getTelephone() != null){
				elementCode.setJkrlxdh_v(enterprise.getTelephone());//借款方公司联系电话
			}
			if(enterprise.getFax()!= null){
				elementCode.setJkrcz_v(enterprise.getFax());
			}
			if(enterprise.getPostcoding() != null) {
				elementCode.setJkryzmb_v(enterprise.getPostcoding());
			}
			if(enterprise.getEmail() != null) {
				elementCode.setJkrdzyx_v(enterprise.getEmail());
			}
			EnterpriseBank epv = enterpriseBankDao.queryIscredit(enterprise.getId(), Short.valueOf("0"), Short.valueOf("0"));
			if(epv != null) {
				if(epv.getName() != null) {
					elementCode.setJkrzhmc_v(epv.getName());
				}
				if(epv.getAccountnum() != null) {
					elementCode.setJkryhzh_v(epv.getAccountnum());
				}
				if(epv.getBankname() != null) {
					elementCode.setJkrkhyh_v(epv.getBankname());
				}
			}
		}
		/*
		 * 财务协议合同-甲方-借款人
		 * 
		 * */
		/*if(null!=smallloanProject.getFinanceServiceMineType() && smallloanProject.getFinanceServiceMineType().equals("person_ourmain")){
			if(smallloanProject.getFinanceServiceMineId()!=0){
				SlPersonMain personMain = null;
				personMain = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, smallloanProject.getFinanceServiceMineId());
				if(personMain.getName()!=null){
					elementCode.setCwdkrmc_v(personMain.getName());
				}
				if(personMain.getCardnum()!= null){
					elementCode.setCwdkrsfzhm_v(personMain.getCardnum());
				}
				if(personMain.getAddress()!= null){
					elementCode.setCwdkrzz_v(personMain.getAddress());
				}
			}
		}else if(null!=smallloanProject.getFinanceServiceMineType() && smallloanProject.getFinanceServiceMineType().equals("company_ourmain")){
			SlCompanyMain companyMain = null;
			companyMain = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, smallloanProject.getFinanceServiceMineId());
			if(companyMain.getCorName()!= null){
				elementCode.setCwdkfgsmc_v(companyMain.getCorName());
			}
			if(companyMain.getLawName()!= null){
				elementCode.setCwdkffddbr_v(companyMain.getLawName());
			}
			if(companyMain.getSjjyAddress()!= null){
				elementCode.setCwdkfgsdz_v(companyMain.getSjjyAddress());
			}
		}*/
		/*
		 * 我方主体-债权人
		 * 个人person_ourmain，企业company_ourmain
		 * */
		if(smallloanProject.getMineType().equals("person_ourmain")){//我方主体为个人
			SlPersonMain personMain = null;
			if(smallloanProject.getMineId()!=0){
				personMain = (SlPersonMain)creditBaseDao.getById(SlPersonMain.class, smallloanProject.getMineId());
				if(personMain != null){
					if(personMain.getName()!=null){
						elementCode.setZqrmc_v(personMain.getName());
					}
					if(personMain.getAddress()!=null){
						elementCode.setZqrdz_v(personMain.getAddress());
					}
					if(personMain.getPostalCode()!= null){
						elementCode.setZqryb_v(personMain.getPostalCode());//债权人邮政编码
					}
					if(personMain.getLinktel()!=null){
						elementCode.setZqrdh_v(personMain.getLinktel());
					}
					if(personMain.getTax()!= null){
						elementCode.setZqrcz_v(personMain.getTax());//债权人传真号码
					}	
					if(personMain.getCardnum()!=null){
						elementCode.setWfztzjh_v(personMain.getCardnum());
					}
				}
			}
			
		}else if(smallloanProject.getMineType().equals("company_ourmain")){//我方主体为企业
			SlCompanyMain companyMain = null;
			if(smallloanProject.getMineId()!=0){
				companyMain = (SlCompanyMain)creditBaseDao.getById(SlCompanyMain.class, smallloanProject.getMineId());
				if(companyMain != null){
					if(companyMain.getCorName()!= null){
						elementCode.setZqrmc_v(companyMain.getCorName());
					}
					if(companyMain.getMessageAddress() != null){
						elementCode.setZqrdz_v(companyMain.getMessageAddress());
					}
					if(companyMain.getPostalCode()!= null){
						elementCode.setZqryb_v(companyMain.getPostalCode());//债权公司邮政编码
					}
					if(companyMain.getTel()!= null){
						elementCode.setZqrdh_v(companyMain.getTel());
					}
					if(companyMain.getTax()!= null){
						elementCode.setZqrcz_v(companyMain.getTax());
					}
					if(companyMain.getLawName()!=null){
						elementCode.setZqffddbr_v(companyMain.getLawName());//债权方法定代表人
					}
					elementCode.setWfztzjh_v(companyMain.getBusinessCode());
				}
			}
		}
	
		/*
		 * 反担保合同相关
		 * 所有人类型：法人 602，自然人 603
		 * 担保类型：抵押担保604，质押担保605，信用担保606
		 * */
		if(null!=contract){
			if(contract.getMortgageId()!= 0 && ("thirdContract".equals(contractType) || "baozContract".equals(contractType))){
				/*String contractNumber = contract.getContractNumber();
				elementCode.setDydbhtbh_v(contractNumber);
				elementCode.setZydbhtbh_v(contractNumber);
				elementCode.setBzdbhtbh_v(contractNumber);*/
				
				VProcreditDictionary mortgage = null;
				mortgage = (VProcreditDictionary)creditBaseDao.getById(VProcreditDictionary.class, contract.getMortgageId());
				EnterpriseView e = null ;
				VPersonDic p = null ;
				//抵、质押物
				if(mortgage.getAssuretypeid() == 604 || mortgage.getAssuretypeid() == 605) {
					if(mortgage.getMortgagepersontypeforvalue() != null){
						elementCode.setDywmc_v(mortgage.getMortgagepersontypeforvalue());
					}
					if(mortgage.getTypeid()==7 || mortgage.getTypeid()==15 || mortgage.getTypeid()==16 || mortgage.getTypeid()==17){
						List list=houseService.seeHouse(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortHouse house=(VProjMortHouse) list.get(0);
							if(null!=house){
								elementCode.setGyr_v(house.getMutualofperson());
							}
						}
					}
					if(mortgage.getTypeid()==8){
						List list=officebuildingService.seeOfficebuilding(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortOfficeBuilding office=(VProjMortOfficeBuilding) list.get(0);
							if(null!=office){
								elementCode.setSycqr_v(office.getPropertyperson());
								elementCode.setGyr_v(office.getMutualofperson());
							}
						}
					}
					if(mortgage.getTypeid()==9){
						List list=housegroundService.seeHouseground(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortHouseGround houseGround=(VProjMortHouseGround) list.get(0);
							if(null!=houseGround){
								elementCode.setSycqr_v(houseGround.getPropertyperson());
							}
						}
					}
					if(mortgage.getTypeid()==10){
						List list=businessServMort.seeBusiness(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortBusiness business=(VProjMortBusiness) list.get(0);
							if(null!=business){
								elementCode.setSycqr_v(business.getPropertyperson());
							}
						}
					}
					if(mortgage.getTypeid()==11){
						List list=businessandliveService.seeBusinessandlive(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortBusAndLive bus=(VProjMortBusAndLive) list.get(0);
							if(null!=bus){
								elementCode.setSycqr_v(bus.getPropertyperson());
							}
						}
					}
					if(mortgage.getTypeid()==13){
						List list=industryService.seeIndustry(mortgage.getId());
						if(null!=list && list.size()>0){
							VProjMortIndustry industry=(VProjMortIndustry) list.get(0);
							if(null!=industry){
								elementCode.setSycqr_v(industry.getPropertyperson());
							}
						}
					}
				}
				if(mortgage.getAssuretypeid()==604){
					/*int count = 0;
					if(!StringUtil.isNumeric(rnum)){
						rnum = "0";
					}
					count = Integer.parseInt(rnum);
					if(count <10){*/
						elementCode.setDydbhtbh_v(contract.getContractNumber());//抵押合同编号
					/*}else{
						elementCode.setDydbhtbh_v("MA-"+smallloanProject.getProjectNumber().replaceAll("_", "-")+"-"+count);//抵押合同编号
					}*/
				}
				if(mortgage.getAssuretypeid() == 605) {
					if(mortgage.getMortgagepersontypeforvalue() != null){
						elementCode.setZywmc_v(mortgage.getMortgagepersontypeforvalue());
					}
					/*int count = 0;
					if(!StringUtil.isNumeric(rnum)){
						rnum = "0";
					}
					count = Integer.parseInt(rnum);
					if(count <10){*/
						elementCode.setZydbhtbh_v(contract.getContractNumber());//质押合同编号
					/*}else{
						elementCode.setZydbhtbh_v("PA-"+smallloanProject.getProjectNumber().replaceAll("_", "-")+"-"+count);//质押合同编号
					}*/
					//creditBaseDao.saveDatas(contract);
				}
				//抵押物认定价值
				if(mortgage.getFinalCertificationPrice() != null) {
					elementCode.setDywgyjz_v(mortgage.getFinalCertificationPrice().toString()+"元");
				}
				if(mortgage.getPersonTypeId() == 603){//自然人
					p = (VPersonDic)creditBaseDao.getById(VPersonDic.class, mortgage.getAssureofname());
					if(null != p.getName()){
						elementCode.setBzrmc_v(p.getName());//保证人
						elementCode.setDyr_v(p.getName());//抵押人
						elementCode.setCzr_v(p.getName());//出质人
					}
					if(p.getCardtypevalue() != null) {//证件种类
						elementCode.setBzrzjzl_v(p.getCardtypevalue().toString());
						elementCode.setDyrzjzl(p.getCardtypevalue().toString());
						elementCode.setCzrzjzl_v(p.getCardtypevalue().toString());
					}
					if(null != p.getCardnumber()){//证件号码
						elementCode.setBzrzjhm_v(p.getCardnumber());
						elementCode.setDyrzjhm(p.getCardnumber());//抵押人身份证号码
						elementCode.setCzrzjhm_v(p.getCardnumber());//出质人身份证号码
					}
					if(null != p.getPostaddress()){//地址
						elementCode.setBzrdz_v(p.getPostaddress());
						elementCode.setDyrdz_v(p.getPostaddress());//抵押人通讯地址
						elementCode.setCzrtxdz_v(p.getPostaddress());//出质人通讯地址
					}
					if(null != p.getPostcode()){
						elementCode.setBzryzbm_v(p.getPostcode());
						elementCode.setDyryzbm_v(p.getPostcode());//抵押人邮政编码
						elementCode.setCzryzbm_v(p.getPostcode());//出质人邮政编码
					}
					if(null != p.getTelphone()&& null!= p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getCellphone()+"/"+p.getTelphone());
						elementCode.setDyrdh_v(p.getCellphone()+"/"+p.getTelphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getCellphone()+"/"+p.getTelphone());//出质人联系电话
					}else if(null == p.getTelphone()&& null!= p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getCellphone());
						elementCode.setDyrdh_v(p.getCellphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getCellphone());//出质人联系电话
					}else if(null != p.getTelphone()&& null== p.getCellphone()){
						elementCode.setBzrlxdh_v(p.getTelphone());
						elementCode.setDyrdh_v(p.getTelphone());//抵押人联系电话
						elementCode.setCzrlxdh_v(p.getTelphone());//出质人联系电话
					}
					if(null != p.getFax()){
						elementCode.setBzrczhm_v(p.getFax());
						elementCode.setDyrcz_v(p.getFax());//抵押人传真
						elementCode.setCzrczhm_v(p.getFax());//出质人传真号码
					}
					Spouse spouse = spouseService.getByPersonId(p.getId());
					if(spouse != null) {//配偶信息
						elementCode.setBzrpomc_v(spouse.getName());
						elementCode.setBzrpozjhm_v(spouse.getCardnumber());
					}
				}else if(mortgage.getPersonTypeId() == 602){//法人
					e = (EnterpriseView)creditBaseDao.getById(EnterpriseView.class, mortgage.getAssureofname());
					if(e.getEnterprisename()!= null){
						elementCode.setBzrmc_v(e.getEnterprisename());//保证人
						elementCode.setDyr_v(e.getEnterprisename());//抵押人
						elementCode.setCzr_v(e.getEnterprisename());//出质人
					}
					if(e.getLegalperson() != null) {
						elementCode.setBzffddbr_v(e.getLegalperson());
						elementCode.setDyffddbr_v(e.getLegalperson());
						elementCode.setCzffddbr_v(e.getLegalperson());
					}
					if(e.getCciaa()!= null){
						elementCode.setBzfyyzzh_v(e.getCciaa());
						elementCode.setDyfyyzzhm_v(e.getCciaa());//抵押公司营业执照号码
						elementCode.setCzfyyzzhm_v(e.getCciaa());//出质方营业执照号码
					}
					if(e.getArea() != null){
						elementCode.setBzrdz_v(e.getArea());
						elementCode.setDyrdz_v(e.getArea());//抵押公司通讯地址
						elementCode.setCzrtxdz_v(e.getArea());//出质方通讯地址
					}
					if(e.getPostcoding()!= null){
						elementCode.setBzryzbm_v(e.getPostcoding());
						elementCode.setDyryzbm_v(e.getPostcoding());//抵押公司邮政编码
						elementCode.setCzryzbm_v(e.getPostcoding());//出质方邮政编码
					}
					if(e.getTelephone()!= null){
						elementCode.setBzrlxdh_v(e.getTelephone());
						elementCode.setDyrdh_v(e.getTelephone());//抵押人联系电话
						elementCode.setCzrlxdh_v(e.getTelephone());//出质人联系电话
					}
					if(e.getFax() != null){
						elementCode.setBzrczhm_v(e.getFax());
						elementCode.setDyrcz_v(e.getFax());//抵押人传真
						elementCode.setCzrczhm_v(e.getFax());//出质人传真号码
					}
					VPersonDic vpd= (VPersonDic)creditBaseDao.getById(VPersonDic.class, e.getLegalpersonid());
					if(vpd.getJobvalue() != null) {
						elementCode.setBzffddbrzw_v(vpd.getJobvalue());
						elementCode.setDyffddbrzw_v(vpd.getJobvalue());
						elementCode.setCzffddbrzw_v(vpd.getJobvalue());
					}
					Spouse spouse = spouseService.getByPersonId(vpd.getId());
					if(spouse != null) {//配偶信息
						elementCode.setBzrpomc_v(spouse.getName());
						elementCode.setBzrpozjhm_v(spouse.getCardnumber());
					}
				}
				//产权人，房地产地点，建筑面积，证件号码
				if(mortgage.getId()!= null){

					/*if(null!=mortgage.getTypeid() && mortgage.getTypeid()==6){
						ProcreditMortgageProduct pm=(ProcreditMortgageProduct) creditBaseDao.getById(ProcreditMortgageProduct.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下"+pm.getName()+"存货商品(品牌："+pm.getBrand()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==7){
						ProcreditMortgageHouse pm=(ProcreditMortgageHouse) creditBaseDao.getById(ProcreditMortgageHouse.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下位于"+pm.getHouseaddress()+pm.getBuildacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==10){
						ProcreditMortgageBusiness pm=(ProcreditMortgageBusiness) creditBaseDao.getById(ProcreditMortgageBusiness.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getAcreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==11){
						ProcreditMortgageBusinessandlive pm=(ProcreditMortgageBusinessandlive) creditBaseDao.getById(ProcreditMortgageBusinessandlive.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getAnticipateacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==12){
						ProcreditMortgageEducation pm=(ProcreditMortgageEducation) creditBaseDao.getById(ProcreditMortgageEducation.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getAcreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==13){
						ProcreditMortgageIndustry pm=(ProcreditMortgageIndustry) creditBaseDao.getById(ProcreditMortgageIndustry.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("名下位于"+pm.getAddress()+pm.getOccupyacreage()+"房产抵押(房权证号为:"+pm.getCertificatenumber()+")作为抵押物");
					}
					if(null!=mortgage.getTypeid() && mortgage.getTypeid()==14){
						VProjMortDroit pm = (VProjMortDroit) creditBaseDao.getById(VProjMortDroit.class, mortgage.getDywId());
						elementCode.setNewfdbwxq_v("把享受权利比重"+(pm.getDroitpercent()==null?"   ":pm.getDroitpercent())+"%作为抵押");
					}*/
					
					StringBuffer fdbwxq = new StringBuffer();
					if(mortgage.getTypeid()!= 0 && (mortgage.getTypeid()==7 || mortgage.getTypeid()==15 || mortgage.getTypeid()==16 || mortgage.getTypeid()==17)){//住宅、公寓、联排别墅、独栋别墅
						String hql3 = "from ProcreditMortgageHouse where mortgageid="+mortgage.getId();
						List list3 = creditBaseDao.queryHql(hql3);
						if(list3 != null && list3.size()>0){
							ProcreditMortgageHouse pmb = (ProcreditMortgageHouse)list3.get(0);
							if(pmb.getHouseaddress() != null) {
								elementCode.setDywszd_v(pmb.getHouseaddress());
							}
							if(pmb.getMutualofperson() != null) {
								elementCode.setDywgyr_v(pmb.getMutualofperson());
							}
							if(pmb.getCertificatenumber() != null) {
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
						} 
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==10){//商业用地
						String hql = "from ProcreditMortgageBusiness where mortgageid="+mortgage.getId();
						List list = creditBaseDao.queryHql(hql);
						if(list != null && list.size()>0){
							ProcreditMortgageBusiness pmb = (ProcreditMortgageBusiness)list.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress() != null) {
								pmb.setAddress("");
							}
							if(pmb.getAddress() != null) {
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==8){//商铺写字楼
						String hql6 = "from ProcreditMortgageOfficebuilding where mortgageid="+mortgage.getId();
						List list6 = creditBaseDao.queryHql(hql6);
						if(list6 != null && list6.size()>0){
							ProcreditMortgageOfficebuilding pmb = (ProcreditMortgageOfficebuilding)list6.get(0);
							if(pmb.getMutualofperson()!= null){
								elementCode.setDywgyr_v(pmb.getMutualofperson());
							}
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getHouseaddress()!= null){
								elementCode.setDywszd_v(pmb.getHouseaddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==9){//住宅用地
						String hql4 = "from ProcreditMortgageHouseground where mortgageid="+mortgage.getId();
						List list4 = creditBaseDao.queryHql(hql4);
						if(list4 != null && list4.size()>0){
							ProcreditMortgageHouseground pmb = (ProcreditMortgageHouseground)list4.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==11){//商住用地
						String hql1 = "from ProcreditMortgageBusinessandlive where mortgageid="+mortgage.getId();
						List list1 = creditBaseDao.queryHql(hql1);
						if(list1!= null && list1.size()>0){
							ProcreditMortgageBusinessandlive pmb = (ProcreditMortgageBusinessandlive)list1.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==12){//教育用地
						String hql2 = "from ProcreditMortgageEducation where mortgageid="+mortgage.getId();
						List list2 = creditBaseDao.queryHql(hql2);
						if(list2 != null && list2.size()>0){
							ProcreditMortgageEducation pmb = (ProcreditMortgageEducation)list2.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==13){//工业用地
						String hql5 = "from ProcreditMortgageIndustry where mortgageid="+mortgage.getId();
						List list5 = creditBaseDao.queryHql(hql5);
						if(list5 != null && list5.size()>0){
							ProcreditMortgageIndustry pmb = (ProcreditMortgageIndustry)list5.get(0);
							if(pmb.getCertificatenumber()!= null){
								elementCode.setDywqzbh_v(pmb.getCertificatenumber());
							}
							if(pmb.getAddress()!= null){
								elementCode.setDywszd_v(pmb.getAddress());
							}
						}
					}else if(mortgage.getTypeid()!= 0 && mortgage.getTypeid()==6){//存货商品
						String hql = "from VProjMortProduct where mortgageid ="+mortgage.getId();
						VProjMortProduct pmb = null;
						List list = creditBaseDao.queryHql(hql);
						if(list != null && list.size()>0){
							pmb = (VProjMortProduct)list.get(0);
							if(pmb.getDepositary() != null) {
								elementCode.setZywcfdd_v(pmb.getDepositary());
							}
							if(pmb.getAmount() != null) {
								elementCode.setZywsl_v(pmb.getAmount().toString());
							}
						}
				}
				//担保人（所有担保人）
				InvestEnterprise enter = investEnterpriseService.get(smallloanProject.getAvailableId());
				elementCode.setDbrmc_v(enter.getEnterprisename());
				elementCode.setZsd_v(enter.getAddress());
				//借款合同编号
				String dbht="";
				List<VProcreditContract> vpcclist= vProcreditContractDao.getList(proId, "SmallLoan", null);
				for(VProcreditContract v:vpcclist){
					dbht=dbht+"/"+v.getContractNumber();
					
				}
		    	elementCode.setJkhtbh_v(dbht);
		    	//担保合同编号（所有）
		    	String sydbht="";
				List<VProcreditContract> vpcclist1= vProcreditContractDao.getList(proId, "SmallLoan", "('thirdContract')");
				for(VProcreditContract v:vpcclist1){
					sydbht = sydbht + "/"+v.getContractNumber();
				}
		    	elementCode.setDbhtbh_v(sydbht);
				//目标公司名称
				/*if(mortgage.getEnterprisename()!= null){
					elementCode.setNewmbgsmc_v(mortgage.getEnterprisename());
				}
				//股权.%
				Double stockownershippercent = 0.0;
				if(mortgage.getId()!=null){
					String hql = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
					List list = creditBaseDao.queryHql(hql);
					if(list!= null && list.size()>0){
						VProjMortStockOwnerShip vs = (VProjMortStockOwnerShip)list.get(0);
						if(vs.getStockownershippercent()==null){
							stockownershippercent = 0.0;
						}else{
							stockownershippercent = vs.getStockownershippercent();
						}
						elementCode.setNewgqbfs_v(stockownershippercent.toString()+"%");
						//目标公司名称
						elementCode.setNewmbgsmc_v(vs.getEnterprisename());
						Object obj = null;
						obj = creditBaseDao.getById(Enterprise.class, vs.getCorporationname());
						if(obj != null){
							Enterprise en = (Enterprise)obj;
							//出质股权数额小写
							Double d = 0.0;
							if(en.getRegistermoney()!=null){
								d = en.getRegistermoney()*stockownershippercent/100;
							}
							
							elementCode.setNewczgqsexx_v(d.toString()+"万元");
						}
						
					}
					
				}
				//质押担保债权本金数额 
				if(mortgage.getId()!= null){
					String hql = "from VProcreditDictionary AS d where d.projid =? and d.businessType =?";
					Object [] obj = {Long.parseLong(proId),smallloanProject.getBusinessType()};
					List list = creditBaseDao.queryHql(hql,obj);
					Double shippercent = 0.0;
					int enterpId = 0;
					if(list!= null && list.size()>0 && list.size()>1){
						for(int i =0;i<list.size();i++){
							String hqlship = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
							List listship = creditBaseDao.queryHql(hqlship);
							if(listship!= null && listship.size()>0){
								VProjMortStockOwnerShip vs = (VProjMortStockOwnerShip)listship.get(0);
								Double shipt = 0.0;
								if(vs.getStockownershippercent()!= null){
									shipt = vs.getStockownershippercent();
								}
								shippercent = shipt;
								enterpId = vs.getCorporationname();
							}
							for(int j= 1;j<list.size();j++){
								String hqlship2 = "from VProjMortStockOwnerShip AS vs where vs.mortgageid ="+mortgage.getId();
								List listship2 = creditBaseDao.queryHql(hqlship2);
								if(listship2!= null && listship2.size()>0){
									VProjMortStockOwnerShip vs2 = (VProjMortStockOwnerShip)listship2.get(0);
									if(enterpId == vs2.getCorporationname()){
										Double shipt = 0.0;
										if(vs2.getStockownershippercent()!= null){
											shipt=vs2.getStockownershippercent();
										}
										shippercent += (shippercent + shipt);
									}
								}
							}
							
						}
					}else{
						shippercent = Double.valueOf("1");
					}
					if(shippercent.compareTo(0.0)>0){
						Double je = smallloanProject.getProjectMoney().doubleValue()*(stockownershippercent/shippercent)/100;
						elementCode.setNewzydbzqbjse_v(myFormatter.format(je)+"元");
					}
				}*/
				
				
				
				//
				
			}
		}
		
		}
		if(null!=proId && !"".equals(proId)){
			String hql="from ProcreditContract as p where p.projId="+Long.valueOf(proId);
			List<ProcreditContract> list=creditBaseDao.queryHql(hql);
			String contractNumber="";
			if(null!=list){
				for(ProcreditContract p:list){
					contractNumber=contractNumber+p.getContractNumber()+",";
				}
				if(!"".equals(contractNumber)){
					contractNumber=contractNumber.substring(0,contractNumber.length()-1);
				}
			}
			elementCode.setDbhtbh_v(contractNumber);
		}
		
	
		BigDecimal projectMoney = investPerson.getInvestMoney();
		
		String dw = "元整";
		elementCode.setTzrtbjexx_v(myFormatter.format(projectMoney).toString()+"元");
		elementCode.setTzrtbjedx_v(MoneyFormat.getInstance().hangeToBig(projectMoney)+dw);
		
		
		
		BpCustRelation bpCustRelation=bpCustRelationService.getByCustIdAndCustType(smallloanProject.getOppositeID(),smallloanProject.getOppositeType().equals("person_customer")?"p_loan":"b_loan").get(0);
		BpCustMember bpCustMember=bpCustMemberService.get(bpCustRelation.getP2pCustId());
		elementCode.setJkryhm_v(bpCustMember.getLoginname());
		elementCode.setDknhll_v(smallloanProject.getYearAccrualRate().toString() + "%");
		
		elementCode.setDkqsrq_v(smallloanProject.getStartInterestDate().toString());
		if(null!=bpCustMember){
			elementCode.setJkryhm_v(bpCustMember.getLoginname());
			//elementCode.setZbxmbh_v("p2p_"+bpCustMember.getLoginname()+"_"+smallloanProject.getProjectId());
		}else{
			elementCode.setJkryhm_v("");
			/*if(smallloanProject.getOppositeType().equals("person_customer")){
				VPersonDic person = (VPersonDic)creditBaseDao.getById(VPersonDic.class, smallloanProject.getOppositeID().intValue());
			   elementCode.setZbxmbh_v("erp_"+person.getCardnumber()+"_"+smallloanProject.getProjectId());
			}*/
		}
		com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan plBidPlan=null;
		if(rnum!=null){
			plBidPlan=plBidPlanService.get(Long.valueOf(rnum));
			if(plBidPlan.getBidProNumber()!=null&&!"".equals(plBidPlan.getBidProNumber())){
			elementCode.setZbxmbh_v(plBidPlan.getBidProNumber());
			}else{
				elementCode.setZbxmbh_v("");
			}
		  }
		
	//	elementCode.
		
		return elementCode;
	}
}
