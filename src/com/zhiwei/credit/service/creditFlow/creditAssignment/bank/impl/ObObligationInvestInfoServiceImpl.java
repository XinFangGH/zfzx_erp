package com.zhiwei.credit.service.creditFlow.creditAssignment.bank.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObAccountDealInfoDao;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObObligationInvestInfoDao;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObSystemAccountDao;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.project.ObObligationProjectDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundIntentDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObObligationInvestInfo;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.ObObligationProject;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObObligationInvestInfoService;

/**
 * 
 * @author 
 *
 */
public class ObObligationInvestInfoServiceImpl extends BaseServiceImpl<ObObligationInvestInfo> implements ObObligationInvestInfoService{
	@SuppressWarnings("unused")
	private ObObligationInvestInfoDao dao;
	@Resource
	private SlFundIntentDao slFundIntentDao;
	@Resource
	private ObSystemAccountDao obSystemAccountDao;
	@Resource
	private ObAccountDealInfoDao obAccountDealInfoDao;
	@Resource
	private ObObligationProjectDao obObligationProjectDao;
	public ObObligationInvestInfoServiceImpl(ObObligationInvestInfoDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<ObObligationInvestInfo> getInfoByobObligationProjectId(Long id,
			String flag) {
		
		return dao.getInfoByobObligationProjectId(id, flag);
	}

	@Override
	public List<ObObligationInvestInfo> getListInvestPeonId(
			Long investmentPersonId, String flag) {
		
		return dao.getListInvestPeonId(investmentPersonId, flag);
	}
	//用自动对账和更新平台账户(一条债权信息的对账)
	@Override
	public void checkSlFundQulid(ObObligationInvestInfo ob) {
		// TODO Auto-generated method stub
		try{
			List<SlFundIntent> listAll =slFundIntentDao.getListByTreeId(ob.getObligationId(),ob.getInvestMentPersonId(),ob.getId());
			if(listAll!=null&&listAll.size()>0){
				List<SlFundIntent> lists =slFundIntentDao.getListByTreeIdUn(ob.getObligationId(),ob.getInvestMentPersonId(),ob.getId());
				ObSystemAccount account =obSystemAccountDao.getByInvrstPersonId(ob.getInvestMentPersonId());
				ObAccountDealInfo  accountDealInfo =new ObAccountDealInfo();
				if(lists!=null&&lists.size()>0){
					Date currentDate =new Date();
					for(SlFundIntent temp :lists){
						if(temp.getIntentDate().compareTo(currentDate)<=0){
							BigDecimal moneny = new BigDecimal(0);
							BigDecimal lin = new BigDecimal(0.00);
							if (temp.getIncomeMoney().compareTo(lin) == 0) {//表示平台账户投资支出金额
								temp.setAfterMoney(temp.getPayMoney());
								//对账后生成平台账户的明细记录以及修改账户的总额
								accountDealInfo.setPayMoney(temp.getPayMoney());
								accountDealInfo.setTransferType(ObAccountDealInfo.T_INVEST);
								accountDealInfo.setCurrentMoney(account.getTotalMoney().subtract(temp.getPayMoney()));
								account.setTotalMoney(account.getTotalMoney().subtract(temp.getPayMoney()));
							} else {//表示平台账户投资收益金额
								temp.setAfterMoney(temp.getIncomeMoney());
								//对账后生成平台账户的明细记录以及修改账户的总额
								accountDealInfo.setPayMoney(temp.getIncomeMoney());
								accountDealInfo.setTransferType(ObAccountDealInfo.T_PROFIT);
								accountDealInfo.setCurrentMoney(account.getTotalMoney().add(temp.getIncomeMoney()));
								account.setTotalMoney(account.getTotalMoney().add(temp.getIncomeMoney()));
							}
							temp.setNotMoney(lin);
							temp.setFactDate(currentDate);
							slFundIntentDao.merge(temp);
							accountDealInfo.setCompanyId(temp.getCompanyId());
							accountDealInfo.setAccountId(account.getId());
							accountDealInfo.setAccountName(account.getAccountName());
							accountDealInfo.setAccountNumber(account.getAccountNumber());
							accountDealInfo.setInvestPersonId(ob.getInvestMentPersonId());
							accountDealInfo.setInvestPersonName(account.getInvestPersonName());
							accountDealInfo.setCreateDate(currentDate);
							accountDealInfo.setTransferDate(temp.getIntentDate());
							accountDealInfo.setInvestObligationInfoId(ob.getId());
							obAccountDealInfoDao.save(accountDealInfo);
							obSystemAccountDao.merge(account);
							ob.setFundIntentStatus(Short.valueOf("1"));
							ob.setInvestObligationStatus(Short.valueOf("1"));
							this.dao.merge(ob);
							//temp.setAfterMoney();
						}
					}
				}
				List<SlFundIntent> list =slFundIntentDao.getListByTreeIdUn(ob.getObligationId(),ob.getInvestMentPersonId(),ob.getId());
				if(list==null){//表示所有的账都对完了
					ob.setFundIntentStatus(Short.valueOf("2"));//表示全部回款成功
					ob.setInvestObligationStatus(Short.valueOf("2"));//表示债权已经结束
					this.dao.merge(ob);
				}
				System.out.println("成功自动对账！");
			}else{
				System.out.println("没有生成款项信息的账目不能对账！");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("自动对账出错了！"+e.getMessage());
		}
		
	}
	//修改系统默认债权信息以及项目状态（投资人添加债权和撤销债权时以及产品添加债权产品时用的公共方法）
	@Override
	public void changeStatus(String obligationId, int i) {
		// TODO Auto-generated method stub
		ObObligationInvestInfo  systemInvest =null;//默认查出当前债权产品的系统公司债权记录（）
		ObObligationProject obproject=obObligationProjectDao.get(Long.valueOf(obligationId));
		List<ObObligationInvestInfo>  listsystem =this.dao.getInfoByobObligationProjectId(Long.valueOf(obligationId),"0");
		if(listsystem!=null&&listsystem.size()>0){
			BigDecimal compar =new BigDecimal(0);
			systemInvest =listsystem.get(0);
			List<ObObligationInvestInfo>  listinvests =this.dao.getInfoByobObligationProjectId(Long.valueOf(obligationId),"1");
			if(listinvests.size()>0&&listinvests!=null){
				BigDecimal totalInvest =new BigDecimal(0);
				Long Quotient =0l;
				BigDecimal rate =new BigDecimal(0);//默认公司债权还剩的比例
				for(ObObligationInvestInfo temp :listinvests){
					totalInvest=totalInvest.add(temp.getInvestMoney());
					Quotient=Quotient+temp.getInvestQuotient();
				}
				if(totalInvest.compareTo(compar)==0){
					
				}else if(totalInvest.compareTo(obproject.getProjectMoney())==0||totalInvest.compareTo(obproject.getProjectMoney())==1){
					systemInvest.setInvestMoney(new BigDecimal(0));
					systemInvest.setInvestQuotient(Long.valueOf("0"));
					systemInvest.setInvestRate(new BigDecimal(0));
					systemInvest.setInvestObligationStatus(Short.valueOf("2"));
					this.dao.merge(systemInvest);
					if(i==1){//表示是添加债权时更新系统默认的投资人信息
						obproject.setObligationStatus(Short.valueOf("1"));//债权产品匹配成功后改变债权产品状态
						obObligationProjectDao.merge(obproject);
					}
				}else if(totalInvest.compareTo(obproject.getProjectMoney())==-1){//债权产品没有匹配完成
					BigDecimal avaible =obproject.getProjectMoney().subtract(totalInvest);
					systemInvest.setInvestMoney(avaible);
					systemInvest.setInvestQuotient(Long.valueOf(obproject.getTotalQuotient().toString())-Quotient);
					systemInvest.setInvestRate(avaible.divide(obproject.getProjectMoney(),4,BigDecimal.ROUND_HALF_EVEN).multiply(new BigDecimal(100)));
					this.dao.merge(systemInvest);
				}
			}
		}
		if(i==0){//表示属于撤销债权时修改债权产品状态
			if(obproject.getObligationStatus()==1){
				if(obproject.getIntentDate().compareTo(new Date())!=0){//不是债权关闭时间到了关闭的债权信息
					obproject.setObligationStatus(Short.valueOf("0"));
					obObligationProjectDao.merge(obproject);
				}
			}
		}
		
	
		
	}

}