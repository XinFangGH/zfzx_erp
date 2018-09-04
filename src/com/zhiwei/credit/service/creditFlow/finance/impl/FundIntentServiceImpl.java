package com.zhiwei.credit.service.creditFlow.finance.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.sdicons.json.mapper.MapperException;
import com.zhiwei.core.dao.GenericDao;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.core.project.FundIntentFactory;
import com.zhiwei.credit.core.project.FundIntentListPro3;
import com.zhiwei.credit.core.project.FundIntentListProtw;
import com.zhiwei.credit.core.project.impl.SameInterestFactory;
import com.zhiwei.credit.dao.creditFlow.finance.FundIntentDao;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidPlanDao;
import com.zhiwei.credit.dao.creditFlow.smallLoan.project.SlSmallloanProjectDao;
import com.zhiwei.credit.dao.customer.InvestPersonInfoDao;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.FundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.InvestEnterprise;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.service.creditFlow.finance.FundIntentService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.MySelfService;

public class FundIntentServiceImpl extends BaseServiceImpl<FundIntent> implements
		FundIntentService{

	@Resource
	private FundIntentDao fundIntentDao;
	@Resource
	private SlSmallloanProjectDao slSmallloanProjectDao;
	@Resource
	private InvestPersonInfoDao investPersonInfoDao;
	@Resource
	private PlBidPlanDao plBidPlanDao;
	
	public FundIntentServiceImpl(FundIntentDao dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public String savejson(String slFundIentJson, Long projectId,
			String businessType, Short flag, Long companyId, Long preceptId,
			String fundResource,Long bidPlanId) {
		if(slFundIentJson==null||"".equals(slFundIentJson)){
			return null;
		}
		String hql = "delete from BpFundIntent bi where bi.preceptId = ? and bi.bidPlanId=?";
		//1、删除所有与改项目相关的款项信息
			fundIntentDao.update(hql, new Object[]{preceptId,bidPlanId});
		//2、解析字符串
			PlBidPlan pbp=null;
			if(bidPlanId!=null){
				pbp = plBidPlanDao.get(bidPlanId);
			}
			try {
				List list = AppUtil.parserList(slFundIentJson, BpFundIntent.class);
				if(null!=list){
					for(Object o :list){
						if(o==null){
							continue;
						}
						BpFundIntent SlFundIntent1 = (BpFundIntent) o;
			/*			Long investId = SlFundIntent1.getInvestPersonId();
						InvestPersonInfo pf = investPersonInfoDao.get(investId);
						if(pf!=null){
							SlFundIntent1.setOrderNo(pf.getOrderNo());
						}*/
					//	Long investId = slFundInten
						SlSmallloanProject loan=slSmallloanProjectDao.get(projectId);
						SlFundIntent1.setProjectId(loan.getProjectId());
						if(pbp!=null){
							SlFundIntent1.setProjectName(pbp.getBidProName());
						}
						//SlFundIntent1.setProjectName(loan.getProjectName());
						SlFundIntent1.setProjectNumber(loan.getProjectNumber());
						
						if (null == SlFundIntent1.getFundIntentId()) {
							BigDecimal lin = new BigDecimal(0.00);
						    if(SlFundIntent1.getIncomeMoney().compareTo(lin)==0){
					        	SlFundIntent1.setNotMoney(SlFundIntent1.getPayMoney());
					        }else{
					        	SlFundIntent1.setNotMoney(SlFundIntent1.getIncomeMoney());
					        }
							SlFundIntent1.setAfterMoney(new BigDecimal(0));
							SlFundIntent1.setAccrualMoney(new BigDecimal(0));
							SlFundIntent1.setFlatMoney(new BigDecimal(0));
							Short isvalid=0;
							SlFundIntent1.setIsValid(isvalid);
							SlFundIntent1.setBusinessType(businessType);
							SlFundIntent1.setCompanyId(companyId);
							SlFundIntent1.setBidPlanId(bidPlanId);
							SlFundIntent1.setIsCheck(flag);
							//add  by zcb 2014-03-20
							if("SmallLoan".equals(fundResource)){
								SlFundIntent1.setFundResource("0");//自有 小贷
							}else if("Pawn".equals(fundResource)){
								SlFundIntent1.setFundResource("1");//自有 典当
							}else{
								SlFundIntent1.setFundResource("2");//平台 
							}
							SlFundIntent1.setPreceptId(preceptId);//保存方案ID
							this.dao.save(SlFundIntent1);
						}	
					}
				}
			} catch (TokenStreamException e) {
				e.printStackTrace();
			} catch (RecognitionException e) {
				e.printStackTrace();
			} catch (MapperException e) {
				e.printStackTrace();
			}
		
		return null;
	}

	@Override
	public List<FundIntent> getListByPreceptId(Long preceptId) {
		String[] names = new String[]{"preceptId"};
		Object[] values = new Object[]{preceptId};
		return fundIntentDao.getList("BpFundIntent", names, values);
	}

	@Override
	public List<FundIntent> createList(BpFundProject bpProject,
			SlSmallloanProject slProject, List<InvestPersonInfo> personInfos,
			List<InvestEnterprise> enterpriseInfos) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		List<FundIntent> list1 = new ArrayList<FundIntent>();
		if(enterpriseInfos!=null){
			for(InvestEnterprise enterp : enterpriseInfos){
				List<SlFundIntent> list = new ArrayList<SlFundIntent>();
					list=  FundIntentListPro3.getFundIntentDefaultList(
							"SmallLoan",
							bpProject.getAccrualtype(),
							slProject.getIsPreposePayAccrual(),
							bpProject.getPayaccrualType(),
							bpProject.getAccrualnew().divide(new BigDecimal(100)), 
							bpProject.getProjectMoney(), 
							sd.format(bpProject.getStartDate()),
							sd.format(bpProject.getIntentDate()),
							bpProject.getManagementConsultingOfRate().divide(BigDecimal.valueOf(100)),
							bpProject.getFinanceServiceOfRate().divide(BigDecimal.valueOf(100)),
							bpProject.getPayintentPeriod(),
							bpProject.getIsStartDatePay(),
							bpProject.getPayintentPerioDate(),
							bpProject.getDayOfEveryPeriod(),
							enterp.getId(),
							enterp.getEnterprisename(),
							0);
					//list1.addAll(list);
				}
			
		}else if(personInfos!=null){
			for(InvestPersonInfo personInfo : personInfos){
				if(personInfo==null){
					continue;//sameprincipalsameInterest
				}
				List<SlFundIntent> list = new ArrayList<SlFundIntent>();
				if(bpProject.getAccrualtype().equals("sameprincipalsameInterest")){
					FundIntentFactory factory = new SameInterestFactory();
					bpProject.setStartDate(bpProject.getStartInterestDate());
					com.zhiwei.credit.core.project.FundIntent fund = factory.createFund();
					list = fund.createList(bpProject, personInfo);
				}else{
					list =  FundIntentListPro3.getFundIntentDefaultList(
							"SmallLoan",
							bpProject.getAccrualtype(),
							bpProject.getIsPreposePayAccrual(), 
							bpProject.getPayaccrualType(),
							bpProject.getAccrual().divide(new BigDecimal(100)), 
							personInfo.getInvestMoney(), 
							sd.format(bpProject.getStartInterestDate()),
							sd.format(bpProject.getIntentDate()),
							bpProject.getManagementConsultingOfRate().divide(BigDecimal.valueOf(100)),
							bpProject.getFinanceServiceOfRate().divide(BigDecimal.valueOf(100)),
							bpProject.getPayintentPeriod(),
							bpProject.getIsStartDatePay(),
							bpProject.getPayintentPerioDate(),
							bpProject.getDayOfEveryPeriod(),
							personInfo.getInvestPersonId(),
							personInfo.getInvestPersonName(),
							0);
				}
				//list1.addAll(list);
			}
		} 

		return list1;
	}

	@Override
	public List<FundIntent> getListByBidPlanId(Long bidPlanId) {
		String[] names = new String[]{"bidPlanId"};
		Object[] values = new Object[]{bidPlanId};
		return fundIntentDao.getList("BpFundIntent", names, values);
	}

	@Override
	public List<FundIntent> listNoEarlyId(Long bidPlanId,
			Long slEarlyRepaymentId) {
		
		return fundIntentDao.listNoEarlyId(bidPlanId, slEarlyRepaymentId);
	}

}
