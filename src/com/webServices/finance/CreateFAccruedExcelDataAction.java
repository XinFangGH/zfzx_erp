package com.webServices.finance;

/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import nc.vo.crd.acc.feeaccr.feeaccrbvo.FeeAccrBVO;
import nc.vo.crd.acc.inteaccr.inteaccrbvo.InteAccrBVO;

import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.SlAccrued;
import com.zhiwei.credit.model.creditFlow.finance.SlDataInfo;
import com.zhiwei.credit.model.creditFlow.finance.SlDataList;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.SlAccruedService;
import com.zhiwei.credit.service.creditFlow.finance.SlDataInfoService;
import com.zhiwei.credit.service.creditFlow.finance.SlDataListService;
import com.zhiwei.credit.service.creditFlow.finance.SlFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.VFundDetailService;
import com.zhiwei.credit.service.creditFlow.finance.VPunishDetailService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.util.ExcelUtils;

/**
 * 
 * @author
 * 
 */
public class CreateFAccruedExcelDataAction extends BaseAction {
	
	@Resource
	private AppUserService appUserService;
	@Resource
	private DictionaryService dictionaryService;
	
	@Resource
	private SlFundIntentService slFundIntentService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private PersonService personService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private MortgageService mortgageService;
	@Resource
	private EnterpriseBankService enterpriseBankService;
	@Resource
	private SlAccruedService slAccruedService;
	@Resource
	private VFundDetailService vFundDetailService;
	@Resource
	private VPunishDetailService vPunishDetailService;
	@Resource
	private SlDataListService slDataListService;
	@Resource
	private SlDataInfoService slDataInfoService;
	private Long projectId;
	private String businessType;
	private List<InteAccrBVO> inteAccrlist;
	private List<FeeAccrBVO> conAccrlist;
	private BigDecimal[] preDateMoney ;
	private BigDecimal[] currentMoney;
	private SlAccrued curraccrued;
	private Long slAccId;

	public String createExcel() {
		Long companyId = ContextUtil.getLoginCompanyId();
		String interestType="loanInterest";
		String consultationType="consultationMoney";
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			String dayDateg = this.getRequest().getParameter("dayDateg");
			String isReceate = this.getRequest().getParameter("isReceate");
			if (null != isReceate && isReceate.equals("yes")) { // 重新发送
				dayDateg = this.getRequest().getParameter("factdate");
				Long dataId = Long.valueOf(this.getRequest().getParameter(
						"dataId"));
				List<SlDataInfo> slDataInfo = slDataInfoService
						.getListByDataId(dataId);

				String[] interestAccruedPath = interestAccruedToExcel(dayDateg,interestType);
				if(interestAccruedPath[0].equals("Y")){
				SlDataInfo slDataInfo8 = slDataInfo.get(0);
				slDataInfo8.setFilePath(interestAccruedPath[2]);
				slDataInfo8.setCreateTime(new Date());
				slDataInfo8.setDataTypeName("贷款利息计提");
				slDataInfoService.save(slDataInfo8);
				}
				String[] chargeAccruedPath = interestAccruedToExcel(dayDateg,consultationType);
				if(chargeAccruedPath[0].equals("Y")){
				if(slDataInfo.size()==2){
				SlDataInfo slDataInfo10 = slDataInfo.get(1);
				slDataInfo10.setFilePath(chargeAccruedPath[2]);
				slDataInfo10.setCreateTime(new Date());
				slDataInfo10.setDataTypeName("贷款随收手续费计提");
				slDataInfoService.save(slDataInfo10);
				}
				}

			} else {// 就发一天的
				SlDataList slDataList = new SlDataList();
				slDataList.setCompanyId(companyId);
				slDataList.setDayDate(sd.parse(dayDateg));
				slDataList.setType("jt");
				slDataList.setSendStatus(Short.valueOf("1"));
				slDataListService.save(slDataList);
				Long dataId = slDataList.getDataId();

				String[] interestAccruedPath = interestAccruedToExcel(dayDateg,interestType);
				if(interestAccruedPath[0].equals("Y")){
				SlDataInfo slDataInfo8 = new SlDataInfo();
				slDataInfo8.setCompanyId(companyId);
				slDataInfo8.setDataId(dataId);
				slDataInfo8.setFilePath(interestAccruedPath[2]);
				slDataInfo8.setDataTypeStatus(Short.valueOf("8"));
				slDataInfo8.setCreateTime(new Date());
				slDataInfo8.setDataTypeName("贷款利息计提");
				slDataInfoService.save(slDataInfo8);
				}
				String[] chargeAccruedPath = interestAccruedToExcel(dayDateg,consultationType);
				if(chargeAccruedPath[0].equals("Y")){
				SlDataInfo slDataInfo10 = new SlDataInfo();
				slDataInfo10.setCompanyId(companyId);
				slDataInfo10.setDataId(dataId);
				slDataInfo10.setFilePath(chargeAccruedPath[2]);
				slDataInfo10.setDataTypeStatus(Short.valueOf("10"));
				slDataInfo10.setCreateTime(new Date());
				slDataInfo10.setDataTypeName("贷款随收手续费计提");
				slDataInfoService.save(slDataInfo10);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}

	/**
	 * factDate 计提日期 通过前端获得 consultDate 参考日期 preDate 上一付息日 nexDate 下一付息日
	 * nexDateMoney 下一付息日金额 preDateMoney 上一付息日金额 如果未还利息用
	 * totalMoney总金额-currentMoner 已经计提总金额
	 * totalMoney=nexDateMoney/（nexDate-preDate
	 * ）*(factDate-preDate)+preDateMoney（多次金额） currentMoner=计提表里边已经计提过的金额
	 */
	public String[] interestAccruedToExcel(String factDate,String type) {
		String[] ret = new String[3];
		String[] ret0 = new String[2];
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date fatctDateDate = new Date();
		Date consultDate = new Date();
		String preDate = "";
		String nexDate = "";
		Integer[] nexIndex;
		BigDecimal nexDateMoney = new BigDecimal("0");
		
		String fg="0";
		try {
			fatctDateDate = sd.parse(factDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			
		
		// 根据机构ID 获取
		List<SlSmallloanProject> sllist = slSmallloanProjectService
				.getProjectByCompanyId(ContextUtil.getLoginCompanyId());
		for (SlSmallloanProject sl : sllist) {
			
			if (null != sl.getIsPreposePayAccrual()
					&& sl.getIsPreposePayAccrual() == 0) { // 后置付息才有计提
				// 本次计提金额
				BigDecimal money = new BigDecimal("0");
			
				// 获取当前项目计提记录
				List<SlAccrued> slAccruedlist = slAccruedService.wslist(sl
						.getProjectId(), sl.getBusinessType());
				
				List<SlFundIntent> slFundIntentlist = slFundIntentService
				.getByProjectId3(sl.getProjectId(), sl
						.getBusinessType(), type); // 获取该项目的
					if(slFundIntentlist!=null&&slFundIntentlist.size()!=0){											// 款项信息表
				if(slAccruedlist==null||slAccruedlist.size()==0)
				{
					 // 还未存在任何该项目的计提记录
					consultDate = sl.getStartDate();
					preDateMoney = new BigDecimal[1];
					preDateMoney[0]=new BigDecimal(0);//上一付息日的钱为 0
					currentMoney=new BigDecimal[1];
					currentMoney[0]=new BigDecimal(0);//已经计提的钱为 0
					nexIndex=new Integer[3];
					nexIndex=nexSlFundIntent(slFundIntentlist, sd, factDate);
					
					SlFundIntent nextSlFundIntent = slFundIntentlist
					.get(nexIndex[0]);
					preDate=sd.format(consultDate);//上一付息日为参考日期
					nexDate=sd.format(nextSlFundIntent.getIntentDate());//下一付息日
					nexDateMoney=nextSlFundIntent.getIncomeMoney();//下一付息日的未对账金额
				
				}else{
					int flag = -1;
					int sai = 0;
					Integer[] preIndex=new Integer[3];
					nexIndex=new Integer[3];
					for (SlAccrued sa : slAccruedlist) {
						if (DateUtil.getDaysBetweenDate(sd.format(sa
								.getAccruedDate()), factDate) == 0) {
							flag = sai;
						}
						sai++;
					}
				
					if (flag != -1) { //已经存在该计提日期的  计提记录
						slAccId=slAccruedlist.get(flag).getAccruedId();
						if(flag==0){
							consultDate = sl.getStartDate();
							preDateMoney = new BigDecimal[1];
							preDateMoney[0]=new BigDecimal(0);//上一付息日的钱为 0
							currentMoney=new BigDecimal[1];
							currentMoney[0]=new BigDecimal(0);//已经计提的钱为 0
							nexIndex=new Integer[3];
							nexIndex=nexSlFundIntent(slFundIntentlist, sd, factDate);
							
							SlFundIntent nextSlFundIntent = slFundIntentlist
							.get(nexIndex[0]);
							preDate=sd.format(consultDate);//上一付息日为参考日期
							nexDate=sd.format(nextSlFundIntent.getIntentDate());//下一付息日
							nexDateMoney=nextSlFundIntent.getIncomeMoney();//下一付息日应对账金额
						}else{
						preIndex=preSlFundIntent(slFundIntentlist,sd,factDate);
						nexIndex=nexSlFundIntent(slFundIntentlist,sd,factDate);
						if(preIndex[0]==-1){ //没有还过利息
							fg="1";//1代表 需要做减法
							consultDate=slAccruedlist.get(sai-1).getInterestfactDate();
							nexDateMoney=slFundIntentlist.get(nexIndex[0]).getNotMoney();
							nexDate=sd.format(slFundIntentlist.get(nexIndex[0]).getIntentDate());
							preDate=sd.format(slFundIntentlist.get(nexIndex[0]).getIntentDate());
							preDateMoney = new BigDecimal[nexIndex[0]];
							currentMoney = new BigDecimal[flag];
							for(int i=0;i<nexIndex[0];i++){
								preDateMoney[i]=slFundIntentlist.get(i).getIncomeMoney();
							}
							for(int j=0;j<flag;j++){
								currentMoney[j]=slAccruedlist.get(j).getAccruedinterestMoney();
							}
						}else{
							fg="0";//0代表不需要做减法
							consultDate=slFundIntentlist.get(preIndex[0]).getFactDate();
							nexDateMoney=slFundIntentlist.get(nexIndex[0]).getIncomeMoney();
							preDate=sd.format(consultDate);
							nexDate=sd.format(slFundIntentlist.get(nexIndex[0]).getIntentDate());
							
							preDateMoney = new BigDecimal[1];
							preDateMoney[0]=new BigDecimal(0);//上一付息日的钱为 0
							currentMoney=new BigDecimal[1];
							currentMoney[0]=new BigDecimal(0);//已经计提的钱为 0
						}//11
						}
					} else {
						preIndex=preSlFundIntent(slFundIntentlist,sd,factDate);
						nexIndex=nexSlFundIntent(slFundIntentlist,sd,factDate);
						if(preIndex[0]==-1){ //没有还过利息
							fg="1";//1代表 需要做减法
							consultDate=slAccruedlist.get(sai-1).getInterestfactDate();
							nexDateMoney=slFundIntentlist.get(nexIndex[0]).getIncomeMoney();
							nexDate=sd.format(slFundIntentlist.get(nexIndex[0]).getIntentDate());
							preDate=sd.format(slFundIntentlist.get(nexIndex[0]).getIntentDate());
							preDateMoney = new BigDecimal[nexIndex[0]];
							currentMoney = new BigDecimal[sai];
							for(int i=0;i<nexIndex[0];i++){
								preDateMoney[i]=slFundIntentlist.get(i).getIncomeMoney();
							}
							for(int j=0;j<sai;j++){
								currentMoney[j]=slAccruedlist.get(j).getAccruedinterestMoney();
							}
						}else{
							fg="0";//0代表不需要做减法
							consultDate=slFundIntentlist.get(preIndex[0]).getFactDate();
							nexDateMoney=slFundIntentlist.get(nexIndex[0]).getIncomeMoney();
							preDate=sd.format(consultDate);
							nexDate=sd.format(slFundIntentlist.get(nexIndex[0]).getIntentDate());
							
							preDateMoney = new BigDecimal[1];
							preDateMoney[0]=new BigDecimal(0);//上一付息日的钱为 0
							currentMoney=new BigDecimal[1];
							currentMoney[0]=new BigDecimal(0);//已经计提的钱为 0
						}
					}
				}
				money = getMoney(fg,nexDateMoney,
						preDate, nexDate, factDate,preDateMoney,currentMoney);
				//money = money;
				// 生成利息记录 List
				if(type.equals("loanInterest")){
					if(slAccId==null){
					curraccrued = new SlAccrued();
					curraccrued.setInterestfactDate(consultDate);
					curraccrued.setAccruedinterestMoney(money);
					
					curraccrued.setAccruedDate(fatctDateDate);
					curraccrued.setProjectId(sl.getProjectId());
					curraccrued.setBusinessType(sl.getBusinessType());
					curraccrued.setCompanyId(sl.getCompanyId());
					}else{
						curraccrued = slAccruedService.get(slAccId);
						curraccrued.setInterestfactDate(consultDate);
						curraccrued.setAccruedinterestMoney(money);
					}
					createInAccList(factDate, money, sl);//利息计提
					
					
				}else{
					if(curraccrued!=null){
					curraccrued.setConsultfactDate(consultDate);
					curraccrued.setAccruedconsultMoney(money);
					// 保存到计提表
					saveSlAccrued(curraccrued);
					}
					createConAccList(factDate, money, sl);//随收手续费计提
				}
			}
			}
		}
		if(type.equals("loanInterest")){
			if (inteAccrlist != null && inteAccrlist.size() > 0) {
				
				ret0 = createInterestExcel(inteAccrlist, factDate);
				if(ret0[0].equals("Y")){
					ret[0]="Y";
					ret[1]="";
					ret[2]=ret0[1];
				}
			} else {
				ret[0] = "N";
				ret[1] = "没有对应计提信息！请检查";
				ret[2]="";
			}
		}else{
			if (conAccrlist != null && conAccrlist.size() > 0) {
				ret0 = createConsultExcel(conAccrlist, factDate);
				if(ret0[0].equals("Y")){
					ret[0]="Y";
					ret[1]="";
					ret[2]=ret0[1];
				}
			} else {
				ret[0] = "N";
				ret[1] = "没有对应计提信息！请检查";
				ret[2]="";
			}
		}
		} catch (Exception e) {
			ret[0] = "N";
			ret[1] = "生成计提信息错误";
			ret[2]="";
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 
	 * @param inteAccrlist
	 *            计提列表
	 * @param factDate
	 *            计提日期
	 * @return
	 */
	private String[] createInterestExcel(List<InteAccrBVO> inteAccrlist, String factDate) {
		String[] ret = new String[2];
		List<String[]> list = new ArrayList<String[]>();
		String[] headOne = new String[] { "计提日期", "机构号", "贷款编码", "客户名称",
				"证件号码", "证件类型", "本次计提金额", "客户类别", "贷款业务品种", "贷款还本方式", "贷款计息方式",
				"贷款担保方式", "币种", "备注","客户经理编码","客户经理名称","产品编码","产品名称" };
		for (InteAccrBVO fe : inteAccrlist) {

			list.add(new String[] { fe.getDef10(), fe.getCorpno(),
					fe.getDuebillno(), fe.getCustname(), fe.getCardno(),
					fe.getCardtype(), fe.getCurrinte().toString(),
					fe.getCusttype(), fe.getLoantype(), fe.getPaytype(),
					fe.getCaltype(), fe.getGuatype(), fe.getCurr(),
					fe.getVnote(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4() });

		}

		ret = ExcelUtils.createExcel("dataToAccount", factDate, "计提", "贷款利息计提",
				headOne, list);
		return ret;
	}
	
	/**
	 * 
	 * @param inteAccrlist
	 *            计提列表
	 * @param factDate
	 *            计提日期
	 * @return
	 */
	private String[] createConsultExcel(List<FeeAccrBVO> feeAccrlist, String factDate) {
		String[] ret = new String[2];
		List<String[]> list = new ArrayList<String[]>();
		String[] headOne = new String[] { "实收日期", "机构号", "贷款编码", "客户名称",
				"证件号码", "证件类型", "本次计提手续费", "客户类别", "贷款业务品种", "贷款还本方式",
				"贷款计息方式", "贷款担保方式", "币种", "备注","客户经理编码","客户经理名称","产品编码","产品名称" };
		for (FeeAccrBVO fe : feeAccrlist) {

			list.add(new String[] { fe.getDef10(), fe.getCorpno(),
					fe.getDuebillno(), fe.getCustname(), fe.getCardno(),
					fe.getCardtype(), fe.getCurrinte().toString(),
					fe.getCusttype(), fe.getLoantype(), fe.getPaytype(),
					fe.getCaltype(), fe.getGuatype(), fe.getCurr(),
					fe.getVnote(),fe.getDef1(),fe.getDef2(),fe.getDef3(),fe.getDef4()  });

		}

		ret = ExcelUtils.createExcel("dataToAccount", factDate, "计提",
				"贷款随收手续费计提", headOne, list);
		return ret;
	}

	/**
	 * 利息
	 * @param factDate
	 *            计提日期
	 * @param accruedinterestMoney
	 *            利息计提金额
	 * @param sl
	 *            项目实体
	 */
	private void createInAccList(String factDate,
			BigDecimal accruedinterestMoney, SlSmallloanProject sl) {
		inteAccrlist=new ArrayList<InteAccrBVO>();
		InteAccrBVO inteAccrBVO = InteAccrBVO.Factory.newInstance();
		inteAccrBVO.setDef10(factDate);
		inteAccrBVO.setCurrinte(accruedinterestMoney);
		// inteAccrBVO.setCurrinte(new BigDecimal("0"));
		inteAccrBVO.setCorpno(sl.getCompanyId().toString());
		inteAccrBVO.setVnote("");
		inteAccrBVO.setDuebillno(sl.getProjectNumber());
		
		String userid = sl.getAppUserId();// 获得项目经理ID
		String productTypeId=sl.getProductTypeId();
		
		Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
		AppUser appUser = appUserService.get(Long.parseLong(userid));
		 inteAccrBVO.setDef1(appUser.getUserNumber());//客户经理编号
		 inteAccrBVO.setDef2(appUser.getFullname());//客户经理名称
		 inteAccrBVO.setDef3(dic.getDicKey());//产品编码
		 inteAccrBVO.setDef4(dic.getItemValue());//产品名称
		
		Long oppositeID = sl.getOppositeID();
		if (sl.getOppositeType().equals("company_customer")) {
			Enterprise e = enterpriseService.getById(oppositeID
					.intValue());
			inteAccrBVO.setCustname("E" + e.getId().toString());
			inteAccrBVO.setCardtype("G02");
			inteAccrBVO.setCardno(e.getCciaa());
			inteAccrBVO.setCusttype("A01");
			EnterpriseBank eb = enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
					Short.valueOf("0"));// ?
			inteAccrBVO
					.setCurr((null == eb || null == eb.getOpenCurrency() || eb
							.getOpenCurrency() == 0) ? "本币" : "外币");
		} else {
			Person p = personService.getById(oppositeID.intValue());
			inteAccrBVO.setCustname("P" + p.getId().toString());
			if (p.getCardtype() == 309) {
				inteAccrBVO.setCardtype("G01");
			} else if (p.getCardtype() == 310) {
				inteAccrBVO.setCardtype("G03");
			} else if (p.getCardtype() == 311) {
				inteAccrBVO.setCardtype("G04");
			}
			inteAccrBVO.setCardno(p.getCardnumber());
			inteAccrBVO.setCusttype("A02");
			EnterpriseBank eb = enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
					Short.valueOf("0"));
			inteAccrBVO
					.setCurr((null == eb || null == eb.getOpenCurrency() || eb
							.getOpenCurrency() == 0) ? "本币" : "外币");
		}

		if (sl.getOperationType().equals("MicroLoanBusiness")) {
			inteAccrBVO.setLoantype("B03");
		} else if (sl.getOperationType().equals("SmallLoanBusiness")) {
			inteAccrBVO.setLoantype("B02");
		} else if (sl.getOperationType().equals("")) { // ?
			inteAccrBVO.setLoantype("B01");
		}
		if (sl.getAccrualtype().equals("ontTimeAccrual")
				&& sl.getIsPreposePayAccrual() == 0) {
			inteAccrBVO.setPaytype("C01");
			inteAccrBVO.setCaltype("D03");
		} else if (sl.getAccrualtype().equals("singleInterest")) {
			inteAccrBVO.setPaytype("C02");
			
			if (sl.getIsStartDatePay().equals("1")) {
				inteAccrBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				inteAccrBVO.setCaltype("D02");
			}
		} else if (sl.getAccrualtype().equals("sameprincipalandInterest")) {
			inteAccrBVO.setPaytype("C03");
			
			if (sl.getIsStartDatePay().equals("1")) {
				inteAccrBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				inteAccrBVO.setCaltype("D02");
			}
		} else if (sl.getAccrualtype().equals("sameprincipal")) {
			inteAccrBVO.setPaytype("C04");
			
			if (sl.getIsStartDatePay().equals("1")) {
				inteAccrBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				inteAccrBVO.setCaltype("D02");
			}
		}else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
			inteAccrBVO.setPaytype("C05");
			if (sl.getIsStartDatePay().equals("1")) {
				inteAccrBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				inteAccrBVO.setCaltype("D02");
			}
		}
		
	/*	List mortlist = mortgageService.getByBusinessTypeProjectId(sl
				.getBusinessType(), sl.getProjectId());
		if (null == mortlist || mortlist.size() == 0) {
			inteAccrBVO.setGuatype("E01");
		} else {
			ProcreditMortgage procreditMortgage = (ProcreditMortgage) mortlist
					.get(0);
			if (procreditMortgage.getAssuretypeid() == 604) {

				inteAccrBVO.setGuatype("E01");
			} else if (procreditMortgage.getAssuretypeid() == 605) {
				inteAccrBVO.setGuatype("E02");

			} else if (procreditMortgage.getAssuretypeid() == 606) {
				inteAccrBVO.setGuatype("E03");
			}
		}*/
		if(sl.getAssuretypeid().equals("815")){
			inteAccrBVO.setGuatype("E01");
		}else if(sl.getAssuretypeid().equals("816")){
			inteAccrBVO.setGuatype("E02");
		}else if(sl.getAssuretypeid().equals("817")){
			inteAccrBVO.setGuatype("E04");
		}else if(sl.getAssuretypeid().equals("818")){
			inteAccrBVO.setGuatype("E03");
		}

		inteAccrlist.add(inteAccrBVO);
	}
	
	/**
	 * 随息手续费
	 * @param factDate
	 *            计提日期
	 * @param accruedinterestMoney
	 *            利息计提金额
	 * @param sl
	 *            项目实体
	 */
	private void createConAccList(String factDate,
			BigDecimal accruedconsultMoney, SlSmallloanProject sl) {
		conAccrlist=new ArrayList<FeeAccrBVO>();
		FeeAccrBVO feeAccrBVO = FeeAccrBVO.Factory.newInstance();
		feeAccrBVO.setDef10(factDate);
		feeAccrBVO.setVnote("");
		feeAccrBVO.setCurrinte(accruedconsultMoney);
		feeAccrBVO.setCorpno(sl.getCompanyId().toString());
		feeAccrBVO.setDuebillno(sl.getProjectNumber());
		
		String userid = sl.getAppUserId();// 获得项目经理ID
		String productTypeId=sl.getProductTypeId();
		Dictionary dic=dictionaryService.get(Long.parseLong(productTypeId));
		AppUser appUser = appUserService.get(Long.parseLong(userid));
		feeAccrBVO.setDef1(appUser.getUserNumber());//客户经理编号
		feeAccrBVO.setDef2(appUser.getFullname());//客户经理名称
		feeAccrBVO.setDef3(dic.getDicKey());//产品编码
		feeAccrBVO.setDef4(dic.getItemValue());//产品名称

		Long oppositeID = sl.getOppositeID();
		if (sl.getOppositeType().equals("company_customer")) {
			Enterprise e = enterpriseService.getById(oppositeID
					.intValue());
			feeAccrBVO.setCustname("E" + e.getId().toString());
			feeAccrBVO.setCardtype("G02");
			feeAccrBVO.setCardno(e.getCciaa());
			feeAccrBVO.setCusttype("A01");
			EnterpriseBank eb = enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("0"),
					Short.valueOf("0"));// ?
			feeAccrBVO.setCurr((null == eb
					|| null == eb.getOpenCurrency() || eb
					.getOpenCurrency() == 0) ? "本币" : "外币");
		} else {
			Person p = personService.getById(oppositeID.intValue());
			feeAccrBVO.setCustname("P" + p.getId().toString());
			if (p.getCardtype() == 309) {
				feeAccrBVO.setCardtype("G01");
			} else if (p.getCardtype() == 310) {
				feeAccrBVO.setCardtype("G03");
			} else if (p.getCardtype() == 311) {
				feeAccrBVO.setCardtype("G04");
			}
			feeAccrBVO.setCardno(p.getCardnumber());
			feeAccrBVO.setCusttype("A02");
			EnterpriseBank eb = enterpriseBankService.queryIscredit(oppositeID.intValue(),Short.valueOf("1"),
					Short.valueOf("0"));
			feeAccrBVO.setCurr((null == eb
					|| null == eb.getOpenCurrency() || eb
					.getOpenCurrency() == 0) ? "本币" : "外币");
		}

		if (sl.getOperationType().equals("MicroLoanBusiness")) {
			feeAccrBVO.setLoantype("B03");
		} else if (sl.getOperationType().equals("SmallLoanBusiness")) {
			feeAccrBVO.setLoantype("B02");
		} else if (sl.getOperationType().equals("")) { // ?
			feeAccrBVO.setLoantype("B01");
		}
		if (sl.getAccrualtype().equals("ontTimeAccrual")
				&& sl.getIsPreposePayAccrual() == 0) {
			feeAccrBVO.setPaytype("C01");
			feeAccrBVO.setCaltype("D03");
		} else if (sl.getAccrualtype().equals("singleInterest")) {
			feeAccrBVO.setPaytype("C02");
			if (sl.getIsStartDatePay().equals("1")) {
				feeAccrBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				feeAccrBVO.setCaltype("D02");
			}
		} else if (sl.getAccrualtype().equals(
				"sameprincipalandInterest")) {
			feeAccrBVO.setPaytype("C03");
			if (sl.getIsStartDatePay().equals("1")) {
				feeAccrBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				feeAccrBVO.setCaltype("D02");
			}
		} else if (sl.getAccrualtype().equals("sameprincipal")) {
			feeAccrBVO.setPaytype("C04");
			if (sl.getIsStartDatePay().equals("1")) {
				feeAccrBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				feeAccrBVO.setCaltype("D02");
			}
		}else if(sl.getAccrualtype().equals("sameprincipalsameInterest")){
			feeAccrBVO.setPaytype("C05");
			if (sl.getIsStartDatePay().equals("1")) {
				feeAccrBVO.setCaltype("D01");
			} else if (sl.getIsStartDatePay().equals("2")) {
				feeAccrBVO.setCaltype("D02");
			}
		}
		
		/*List mortlist = mortgageService.getByBusinessTypeProjectId(sl
				.getBusinessType(), sl.getProjectId());
		if (null == mortlist || mortlist.size() == 0) {
			feeAccrBVO.setGuatype("E01");
		} else {
			ProcreditMortgage procreditMortgage = (ProcreditMortgage) mortlist
					.get(0);
			if (procreditMortgage.getAssuretypeid() == 604) {

				feeAccrBVO.setGuatype("E01");
			} else if (procreditMortgage.getAssuretypeid() == 605) {
				feeAccrBVO.setGuatype("E02");

			} else if (procreditMortgage.getAssuretypeid() == 606) {
				feeAccrBVO.setGuatype("E03");
			}
		}*/
		
		if(sl.getAssuretypeid().equals("815")){
			feeAccrBVO.setGuatype("E01");
		}else if(sl.getAssuretypeid().equals("816")){
			feeAccrBVO.setGuatype("E02");
		}else if(sl.getAssuretypeid().equals("817")){
			feeAccrBVO.setGuatype("E04");
		}else if(sl.getAssuretypeid().equals("818")){
			feeAccrBVO.setGuatype("E03");
		}

		conAccrlist.add(feeAccrBVO);
	}

	/**
	 * 
	 * @param cankaodate
	 * @param accruedinterestMoney
	 * @param fatctDateDate
	 * @param sl
	 * @inOrco 0 利息 1利息手续费
	 */
	private void saveSlAccrued(SlAccrued slcurraccrued) {
		if(slcurraccrued!=null&&slcurraccrued.getAccruedId()==null){
			slAccruedService.save(slcurraccrued); //add
		}else{

			SlAccrued sa = slAccruedService
					.get(slcurraccrued.getAccruedId());
			try {
				BeanUtil.copyNotNullProperties(slcurraccrued,
						sa);
				slAccruedService.save(sa);
			} catch (Exception e) {
				e.getStackTrace();
				// logger.error(e.getMessage());
			}
		
		}
		curraccrued=null;
	}

	/**
	 * flag 0参考日期不同 1参考日期相同 
	 * factDate 计提日期 通过前端获得 consultDate 参考日期 preDate 上一付息日
	 * nexDate 下一付息日 nexDateMoney 下一付息日金额 preDateMoney 上一付息日金额 如果未还利息用
	 * totalMoney总金额-currentMoney 已经计提总金额
	 * totalMoney=nexDateMoney/（nexDate-preDate）*(factDate-preDate)+preDateMoney（多次金额） currentMoner=计提表里边已经计提过的金额
	 */
	private BigDecimal getMoney(String flag, BigDecimal nexDateMoney,
			String preDate, String nexDate, String factDate,BigDecimal[] preDateMoney,BigDecimal[] currentMoney) {
		BigDecimal money = new BigDecimal("0");
		Integer a = 0;// 计提日期减上一付息日
		Integer b = 0;// 下一付息日减上一付息日
		a = DateUtil.getDaysBetweenDate(preDate, factDate);
		b = DateUtil.getDaysBetweenDate(preDate, nexDate);
		if (flag.equals("0")) {
			if(b==0){ // 如果b==0 说明咨询服务费 为提前收取  
				money=new BigDecimal(0);
			}else{
			money = nexDateMoney.multiply(new BigDecimal(a)).divide(
					new BigDecimal(b), 2, BigDecimal.ROUND_HALF_UP);
			}
		} else {
			BigDecimal totalM=new BigDecimal("0");
			BigDecimal currentM=new BigDecimal("0");
			if(b==0){ // 如果b==0 说明咨询服务费 为提前收取  
				money=new BigDecimal(0);
			}else{
			money = nexDateMoney.multiply(new BigDecimal(a.toString())).divide(
					new BigDecimal(b.toString()), 2, BigDecimal.ROUND_HALF_UP);
			}
			for(int i=0;i<preDateMoney.length;i++){
				totalM=totalM.add(preDateMoney[i]);
			}
			for(int j=0;j<currentMoney.length;j++){
				currentM=currentM.add(currentMoney[j]);
			}
			money=money.add(totalM).subtract(currentM);
		}
		return money;
	}
	/**
	 * 上一付息记录
	 * @param slFundIntentlist
	 * @param sd
	 * @param factDate
	 * @return
	 */
	private Integer[] preSlFundIntent(List<SlFundIntent> slFundIntentlist, SimpleDateFormat sd,String factDate){
		Integer[] ret=new Integer[3];
		Integer mintfactdate = 0;
		Integer indexfact = -1;
		Integer kfact = 0;
		for (SlFundIntent fi : slFundIntentlist) {
			if (kfact == 0) {// 第一次进来循环先给他赋值

				mintfactdate = 999999;
			}
			if (fi.getFactDate() != null
					&& DateUtil.getDaysBetweenDate(sd.format(fi
							.getFactDate()), factDate) > 0) {
				int day = DateUtil.getDaysBetweenDate(sd
						.format(fi.getFactDate()), factDate);
				if (day <= mintfactdate) {
					mintfactdate = day;
					indexfact = kfact;
				}
			}
			kfact++;
		}
		ret[0]=indexfact;
		ret[1]=kfact;
		ret[2]=mintfactdate;
		return ret;
	}
	/**
	 * 下一付息记录
	 * @param slFundIntentlist
	 * @param sd
	 * @param factDate
	 * @return
	 */
	private Integer[] nexSlFundIntent(List<SlFundIntent> slFundIntentlist, SimpleDateFormat sd,String factDate){
		Integer[] ret=new Integer[3];
		Integer mintintentdate = 0;
		Integer indexintent = 0;
		Integer kintent = 0;
		for (SlFundIntent fi : slFundIntentlist) {
			if (kintent == 0) { //第一次进来循环先给他赋值
				mintintentdate = 9999999;
			}
			if (DateUtil.getDaysBetweenDate(factDate, sd
					.format(fi.getIntentDate())) > 0) {
				int day = DateUtil.getDaysBetweenDate(factDate,
						sd.format(fi.getIntentDate()));
				if (day <= mintintentdate) {
					mintintentdate = day;
					indexintent = kintent;
				}
			}
			
			kintent++;
		}
		ret[0]=indexintent;
		ret[1]=kintent;
		ret[2]=mintintentdate;
		return ret;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}
