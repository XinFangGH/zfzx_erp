package com.contract;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.util.MoneyFormat;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationEnterprise;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationPerson;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.PlBusinessDirProKeep;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.PlPersionDirProKeep;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.cooperation.CsCooperationEnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.finance.BpFundIntentService;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.PlBusinessDirProKeepService;
import com.zhiwei.credit.service.creditFlow.financingAgency.persion.PlPersionDirProKeepService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.customer.InvestEnterpriseService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import org.apache.commons.lang3.StringUtils;

public class SignP2PElement {
	
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private PersonService personService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpFundIntentService bpFundIntentService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private InvestEnterpriseService investEnterpriseService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	@Resource
	private PlPersionDirProKeepService plPersionDirProKeepService;
	@Resource
	private PlBusinessDirProKeepService plBusinessDirProKeepService;
	@Resource
	private CsCooperationEnterpriseService csCooperationEnterpriseService;

	/**
	 * 为p2p部分要素赋值
	 * @param slSmallloanProject
	 * @param sloanProject
	 * @param
	 * @param map
	 */
	public void updateP2PCodeCommon(SlSmallloanProject slSmallloanProject,BpFundProject sloanProject,PlBidPlan plBidPlan,Map<String,Object> map,HttpServletRequest request){
	
		BigDecimal fxBZJZC = new BigDecimal(0);//风险保证金小写
		BigDecimal fxBZJSR = new BigDecimal(0);//风险保证金大写
		BigDecimal mqchbjjlxse = new BigDecimal(0);//每期本金及利息
		BigDecimal mqjkglfse = new BigDecimal(0);//每期管理费
		BigDecimal hkbxze = new BigDecimal(0);//还款本息总额
		BigDecimal mqcwfwf = new BigDecimal(0);//每期财务服务费
		DecimalFormat myFormatter = new DecimalFormat("####.#");
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		
		map.put("投标名称",plBidPlan.getBidProName());
		map.put("财务服务年化费率",sloanProject.getYearFinanceServiceOfRate().setScale(2,RoundingMode.HALF_UP)+"%");
		map.put("财务服务月化费率",sloanProject.getFinanceServiceOfRate().setScale(2,RoundingMode.HALF_UP)+"%");
		map.put("财务服务合计费率",sloanProject.getSumFinanceServiceOfRate().setScale(2,RoundingMode.HALF_UP)+"%");
		map.put("管理咨询年化费率",sloanProject.getYearManagementConsultingOfRate().setScale(2,RoundingMode.HALF_UP)+"%");
		map.put("管理咨询月化费率",sloanProject.getManagementConsultingOfRate().setScale(2,RoundingMode.HALF_UP)+"%");
		map.put("管理咨询合计费率",sloanProject.getSumManagementConsultingOfRate().setScale(2,RoundingMode.HALF_UP)+"%");
		map.put("借款年化利率",sloanProject.getYearAccrualRate().setScale(2,RoundingMode.HALF_UP)+"%");
		map.put("借款月化利率",sloanProject.getAccrual().setScale(2,RoundingMode.HALF_UP)+"%");
		map.put("贷款年化利率",sloanProject.getYearAccrualRate().setScale(2).toString() + "%");
		map.put("招标项目名称",plBidPlan.getBidProName());
		map.put("招标项目编号",plBidPlan.getBidProNumber());
		map.put("还款期数",sloanProject.getPayintentPeriod());
		
		//原始借款人信息
		map.put("原始借款人ID",sloanProject.getOppositeID());
		map.put("原始借款名称",sloanProject.getProjectName());
		if(sloanProject.getPayaccrualType().equals("monthPay")){
			map.put("原始借款期限",sloanProject.getPayintentPeriod()+BaseConstants.DWQX1);
		}else if(sloanProject.getPayaccrualType().equals("seasonPay")){
			map.put("原始借款期限",sloanProject.getPayintentPeriod()*3+BaseConstants.DWQX1);
		}else if(sloanProject.getPayaccrualType().equals("yearPay")){
			map.put("原始借款期限",sloanProject.getPayintentPeriod()*12+BaseConstants.DWQX1);
		}else if(sloanProject.getPayaccrualType().equals("dayPay")){
			map.put("原始借款期限",sloanProject.getPayintentPeriod()+BaseConstants.DWQX2);
		}else if(sloanProject.getPayaccrualType().equals("owerPay")){
			if(null!=sloanProject.getDayOfEveryPeriod()){
				map.put("原始借款期限",sloanProject.getPayintentPeriod()*sloanProject.getDayOfEveryPeriod()+BaseConstants.DWQX2);
			}
		}
		map.put("原始借款起日",sd.format(sloanProject.getStartDate()));
		map.put("原始借款止日",sd.format(sloanProject.getIntentDate()));
		
		//固定日还款
		if(null!=sloanProject.getIsStartDatePay() && "1".equals(sloanProject.getIsStartDatePay())){
			map.put("每期还款日",sloanProject.getPayintentPerioDate()+"号");
		}
		
		BigDecimal fxBZJRate = fxBZJSR.divide(plBidPlan.getBidMoney(),10,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP); //风险保证金比例
		map.put("风险保证金比例",fxBZJRate.setScale(2,RoundingMode.HALF_UP)+"");
		
		if(null!=plBidPlan.getStartIntentDate()){
			map.put("本次招标起息日",sd.format(plBidPlan.getStartIntentDate()));
		}
		if(null!=plBidPlan.getEndIntentDate()){
			map.put("本次招标还款日",sd.format(plBidPlan.getEndIntentDate()));
		}
		if(null!=plBidPlan.getStartIntentDate()){
			map.put("投资人起息日",sd.format(plBidPlan.getStartIntentDate()).toString());
		}
		if(null!=plBidPlan.getEndIntentDate()){
			map.put("投资人到期日",sd.format(plBidPlan.getEndIntentDate()).toString());
		}
		if(null!=plBidPlan.getBidMoney()){
			map.put("本次招标金额",plBidPlan.getBidMoney().setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"元");
			map.put("本次招标金额大写",MoneyFormat.getInstance().hangeToBig(plBidPlan.getBidMoney()));
			map.put("综合服务费小写",plBidPlan.getBidMoney().multiply(new BigDecimal(0.02)).setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"元");
			map.put("综合服务费大写",MoneyFormat.getInstance().hangeToBig(plBidPlan.getBidMoney().multiply(new BigDecimal(0.02))));
		}
		
		if(null!=sloanProject.getPurposeType()){
			try {
				Dictionary dictionary = (Dictionary)creditBaseDao.getById(Dictionary.class,sloanProject.getPurposeType());
				if(null!=dictionary){
					map.put("借款用途",dictionary.getItemValue());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(null!=sloanProject.getAccrualtype()){
			if("sameprincipal".equals(sloanProject.getAccrualtype())) {
				map.put("还款方式","等额本金");
			}else if ("sameprincipalandInterest".equals(sloanProject.getAccrualtype())){
				map.put("还款方式","等额本息");
			}else if ("singleInterest".equals(sloanProject.getAccrualtype())){
				map.put("还款方式","按期收息,到期还本");
			}else if ("ontTimeAccrual".equals(sloanProject.getAccrualtype())){
				map.put("还款方式","一次性还本付息");
			}else if("sameprincipalsameInterest".equals(sloanProject.getAccrualtype())){
				map.put("还款方式","等本等息");
			}
		}
		if(null!=slSmallloanProject.getPayaccrualType()){
			if(slSmallloanProject.getPayaccrualType().equals("monthPay")){
				if(null!=slSmallloanProject.getPayintentPeriod()){
					map.put("招标贷款期限",slSmallloanProject.getPayintentPeriod()+"个月");
				}
			}else if(slSmallloanProject.getPayaccrualType().equals("seasonPay")){
				if(null!=slSmallloanProject.getPayintentPeriod()){
					map.put("招标贷款期限",slSmallloanProject.getPayintentPeriod()*3+"个月"); 
				}
			}else if(slSmallloanProject.getPayaccrualType().equals("yearPay")){
				if(null!=slSmallloanProject.getPayintentPeriod()){
					map.put("招标贷款期限",slSmallloanProject.getPayintentPeriod()*12+"个月"); 
				}
			}else if(slSmallloanProject.getPayaccrualType().equals("dayPay")){
				if(null!=slSmallloanProject.getPayintentPeriod()){
					map.put("招标贷款期限",slSmallloanProject.getPayintentPeriod()+"天");
				}
			}else if(slSmallloanProject.getPayaccrualType().equals("owerPay")){
				if(null!=slSmallloanProject.getDayOfEveryPeriod() && null!=slSmallloanProject.getPayintentPeriod()){
					map.put("招标贷款期限",slSmallloanProject.getPayintentPeriod()*slSmallloanProject.getDayOfEveryPeriod()+"天"); 
				}
			}
		}
		//担保人--姓名、住址
		if(null!=slSmallloanProject.getAvailableId()){
			InvestEnterprise enterprise = investEnterpriseService.get(slSmallloanProject.getAvailableId());
			if(enterprise!=null){
				map.put("担保人",enterprise.getEnterprisename());
				map.put("住所地",enterprise.getArea());
			}
		}
		//借款人--申请号、注册号
		BpCustRelation relation = null;
		List<BpCustRelation> li=null;
		if(null!=sloanProject.getOppositeType() && sloanProject.getOppositeType().equals("person_customer")){
			li = bpCustRelationService.getByCustIdAndCustType(sloanProject.getOppositeID(),"p_loan");
		}else if(null!=sloanProject.getOppositeType() && sloanProject.getOppositeType().equals("company_customer")){
			li = bpCustRelationService.getByCustIdAndCustType(sloanProject.getOppositeID(), "b_loan");
		}
		if(null!=li && li.size()!=0){
			relation = li.get(0);
		}
		if(null!=relation){
			BpCustMember member = bpCustMemberService.get(relation.getP2pCustId());
			map.put("借款申请号","借-"+member.getId()+"-"+sloanProject.getOppositeID());
			map.put("借款人注册号",member.getLoginname());
		}
		//招标项目查看的贷款项目详情，需要查看招标总金额
		if(plBidPlan!=null){
			map.put("贷款金额小写",myFormatter.format(plBidPlan.getBidMoney()).toString()+"元");
			map.put("贷款金额大写",MoneyFormat.getInstance().hangeToBig(plBidPlan.getBidMoney()));
		}else{
			map.put("贷款金额小写",myFormatter.format(sloanProject.getProjectMoney()).toString()+"元");
			map.put("贷款金额大写",MoneyFormat.getInstance().hangeToBig(sloanProject.getProjectMoney()));
		}
		
		//TODO 待修改
//		map.put("借款合同编号",sdf.format(plBidPlan.getCreatetime())+"-"+sloanProject.getOppositeID());
		
		if(null!=sloanProject.getAccrualtype() && sloanProject.getAccrualtype().equals("sameprincipalsameInterest")){//等本等息
			List<BpFundIntent> interst = bpFundIntentService.getBpBidPlanId(plBidPlan.getBidId(),"loanInterest");
			List<BpFundIntent> principle = bpFundIntentService.getBpBidPlanId(plBidPlan.getBidId(),"principalRepayment");
			Double inter = 0d;
			Double prin = 0d;
			if(interst!=null&&interst.size()!=0){
				BpFundIntent intent = interst.get(0);
				if(null!=intent){
					inter = intent.getIncomeMoney().doubleValue();
				}
			}if(principle!=null&&principle.size()!=0){
				BpFundIntent intent = principle.get(0);
				if(intent!=null){
					prin = intent.getIncomeMoney().doubleValue();
				}
			}
			map.put("期还本金",myFormatter.format(prin).toString()+"元");
			map.put("期还利息",myFormatter.format(inter).toString()+"元");
			map.put("期还总额",myFormatter.format(prin+inter).toString()+"元");
		}
		
		List<SlActualToCharge> listSATC = slActualToChargeService.listbyProjectIdAndBidPlanId(sloanProject.getProjectId(),plBidPlan.getBidId().toString());
		for(SlActualToCharge slActualToCharge : listSATC){
			SlPlansToCharge slPlansToCharge = slPlansToChargeService.get(slActualToCharge.getPlanChargeId());
			if(null!=slPlansToCharge && "风险保证金".equals(slPlansToCharge.getName())){
				if(slActualToCharge.getIncomeMoney()!=null){
					fxBZJSR = fxBZJSR.add(slActualToCharge.getIncomeMoney());
				}
				if(slActualToCharge.getPayMoney()!=null){
					fxBZJZC = fxBZJZC.add(slActualToCharge.getPayMoney());
				}
			}
		}
		fxBZJSR = fxBZJSR.subtract(fxBZJZC);//风险保证金收入
		map.put("风险保证金金额小写",fxBZJSR.setScale(2,RoundingMode.HALF_UP).toString());
		if(fxBZJZC.compareTo(new BigDecimal(0))>0){
			map.put("风险保证金金额大写",MoneyFormat.getInstance().hangeToBig(fxBZJSR)+"元");
		}
		List<?> fundList=bpFundIntentService.bidFundList(plBidPlan.getBidId());
		if(null!=fundList && fundList.size()>0){
			Object[] obj=(Object[]) fundList.get(0);
			if(null!=obj[4]){
				mqcwfwf=mqcwfwf.add(new BigDecimal(obj[4].toString()));
			}
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
		hkbxze=bpFundIntentService.getPrincipalAndInterest(plBidPlan.getBidId());
		map.put("每期偿还本金及利息数额小写",mqchbjjlxse.setScale(2,RoundingMode.HALF_UP).toString()+"元");
		map.put("每期偿还本金及利息数额大写",MoneyFormat.getInstance().hangeToBig(mqchbjjlxse));
		map.put("每期借款管理费数额小写",mqjkglfse.setScale(2,RoundingMode.HALF_UP).toString()+"元");
		map.put("每期借款管理费数额大写",MoneyFormat.getInstance().hangeToBig(mqjkglfse));
		map.put("每期财务服务费数额小写",mqcwfwf.setScale(2,RoundingMode.HALF_UP).toString()+"元");
		map.put("每期财务服务费数额大写",MoneyFormat.getInstance().hangeToBig(mqcwfwf));
		map.put("还款本息总额",hkbxze.setScale(2,RoundingMode.HALF_UP).toString());
		map.put("投资人应收本息合计",hkbxze.setScale(2,RoundingMode.HALF_UP).toString());

		map.put("承租方姓名","SST_TJHW");
		map.put("承租方法人","SST_LZL");




		CsCooperationEnterprise	cooEnterprise=null;
		QueryFilter filter = new QueryFilter(request);
		if(plBidPlan.getProType().endsWith("B_Or")){
			filter.addFilter("Q_bpBusinessOrPro.borProId_L_EQ", plBidPlan.getBorProId().toString());
			List<PlBusinessDirProKeep> list = plBusinessDirProKeepService.getAll(filter);
			if(null!=list && list.size()!=0){
				if(null!=list.get(0).getGuarantorsId()){
					cooEnterprise=csCooperationEnterpriseService.get(list.get(0).getGuarantorsId());
				}
			}
		}else if(plBidPlan.getProType().endsWith("P_Or")){
			filter.addFilter("Q_bpPersionOrPro.porProId_L_EQ", plBidPlan.getPOrProId().toString());
			List<PlPersionDirProKeep> list = plPersionDirProKeepService.getAll(filter);
			if(null!=list&&list.size()!=0){
				if(list.get(0).getGuarantorsId() !=null){
					cooEnterprise=csCooperationEnterpriseService.get(list.get(0).getGuarantorsId());
				}
			}
		}else if(plBidPlan.getProType().endsWith("B_Dir")){
			filter.addFilter("Q_bpBusinessDirPro.bdirProId_L_EQ", plBidPlan.getBdirProId().toString());
			List<PlBusinessDirProKeep> list = plBusinessDirProKeepService.getAll(filter);
			if(null!=list&&list.size()!=0){
				if(list.get(0).getGuarantorsId() !=null){
					cooEnterprise=csCooperationEnterpriseService.get(list.get(0).getGuarantorsId());
				}
			}
		}else if(plBidPlan.getProType().endsWith("P_Dir")){
			filter.addFilter("Q_bpPersionDirPro.pdirProId_L_EQ", plBidPlan.getPDirProId().toString());
			List<PlPersionDirProKeep> list = plPersionDirProKeepService.getAll(filter);
			if(null!=list&&list.size()!=0){
				if(null!=list.get(0).getGuarantorsId()){
					cooEnterprise=csCooperationEnterpriseService.get(list.get(0).getGuarantorsId());
				}
			}
		}
		if(null !=cooEnterprise){
			map.put("担保机构名称",cooEnterprise.getName());
			map.put("担保机构地址",cooEnterprise.getCompanyAddress());
		}
	

		List<BpCustRelation> bpCustRelationlist=bpCustRelationService.getByCustIdAndCustType(sloanProject.getOppositeID(),sloanProject.getOppositeType().equals("person_customer")?"p_loan":"b_loan");
		if(bpCustRelationlist!=null&bpCustRelationlist.size()>0){
			BpCustMember bpCustMember=bpCustMemberService.get(bpCustRelationlist.get(0).getP2pCustId());
			if(null!=bpCustMember){
				map.put("借款人注册账号",bpCustMember.getLoginname());
			}
		}
		
		if("person_customer".equals(sloanProject.getOppositeType())){
			Person p=personService.getById(Integer.valueOf(sloanProject.getOppositeID().toString()));
			if(null!=p){
//				map.put("原始借款人",p.getName());
//				map.put("借款人姓名",p.getName());
//				map.put("借款人手机号码",p.getCellphone());
//				map.put("借款人证件号码",p.getCardnumber());
				if(p.getName()!=null&&!"".equals(p.getName())){
					String name = p.getName();
					map.put("原始借款人",name);
					map.put("借款人姓名",name);
				}
				if(p.getCellphone()!=null&&!"".equals(p.getCellphone())){
					String telphone = p.getCellphone();
					map.put("借款人手机号码",telphone);
				}
				if(p.getCardnumber()!=null&&!"".equals(p.getCardnumber())){
					String cardNumber = p.getCardnumber();
					map.put("借款人证件号码",cardNumber);
				}
				if(p.getPostaddress()!=null&&!"".equals(p.getPostaddress())){
					map.put("借款人地址",p.getPostaddress());
				}
			}
		}else if("company_customer".equals(sloanProject.getOppositeType())){
			Enterprise e=enterpriseService.getById(sloanProject.getOppositeID().intValue());
			if(null!=e){
				if(e.getEnterprisename()!=null&&!"".equals(e.getEnterprisename())){
					String value = e.getEnterprisename();
					map.put("借款人姓名",value);
				}
				if(e.getEnterprisename()!=null&&!"".equals(e.getEnterprisename())){
					String value = e.getEnterprisename();
					map.put("借款人姓名",value);
					map.put("原始借款人",value);
				}
				if(e.getFactaddress()!=null&&!"".equals(e.getFactaddress())){
					map.put("借款人地址",e.getFactaddress());
				}
				if (e.getOrganizecode()!=null &&!"".equals(e.getOrganizecode())){
					map.put("借款人证件号码",e.getOrganizecode());
				}
				if (StringUtils.isNotBlank(e.getUserCode())){
					map.put("借款人众签id",e.getUserCode());
				}
				if (StringUtils.isNotBlank(e.getLinkman())){
					map.put("借款人法人",e.getLinkman());
				}
				Person p=personService.getById(e.getLegalpersonid());
				if(null!=p){
					if(p.getCellphone()!=null&&!"".equals(p.getCellphone())){
						String value = p.getCellphone();
						map.put("借款人手机号码",value);
					}
				/*	if(p.getCardnumber()!=null&&!"".equals(p.getCardnumber())){
						String value = p.getCardnumber();
						map.put("借款人证件号码",value);
					}*/
				}
			}
		}
		
		try{
			if(null!=sloanProject.getReciverType() && "person".equals(sloanProject.getReciverType())){
				CsCooperationPerson cp=(CsCooperationPerson)creditBaseDao.getById(CsCooperationPerson.class,sloanProject.getReciverId());
				map.put("债权人姓名",cp.getName());
				map.put("债权人证件号码",cp.getCardNumber());
				map.put("债权人手机号码",cp.getPhoneNumber());
			}else if(null!=sloanProject.getReciverType() && "enterprise".equals(sloanProject.getReciverType())){
				CsCooperationEnterprise cp=(CsCooperationEnterprise)creditBaseDao.getById(CsCooperationEnterprise.class,sloanProject.getReciverId());
				map.put("债权人姓名",cp.getName());
				map.put("债权人手机号码",cp.getPphone());
			}
			map.put("债权人注册账号",sloanProject.getReceiverP2PAccountNumber());
		}catch(Exception e){
			
		}
	}
	/**
	 * 为结算中心页面赋值
	 * @param
	 * @param map
	 */
	public void updateSettleCodeCommon(SettlementReviewerPay pay,Map<String,Object> map,HttpServletRequest request){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		if(pay!=null){
			map.put("结算开始日期",pay.getPayStartDate()==null?"":sf.format(pay.getPayStartDate()));
			map.put("结算截止日期",pay.getPayEndDate()==null?"":sf.format(pay.getPayEndDate()));
			map.put("收款部门",pay.getOrganName());
			map.put("支付方式","2".equals(pay.getPaymentMethod())?"线下银行转账":("3".equals(pay.getPaymentMethod())?"线下现金支付":""));
			map.put("经办日期",pay.getManagerDate()==null?"":pay.getManagerDate());
			map.put("经办人",pay.getManagerName());
			map.put("审核人",pay.getReviewer());
			map.put("支付人",pay.getPayer());
			map.put("应结算金额",pay.getPayMoney()==null?0:pay.getPayMoney().doubleValue());
			map.put("实际结算金额",pay.getFactMoney()==null?0:pay.getFactMoney().doubleValue());
		}
	}
}
