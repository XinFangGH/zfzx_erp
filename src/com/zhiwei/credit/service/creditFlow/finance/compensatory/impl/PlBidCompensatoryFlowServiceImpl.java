package com.zhiwei.credit.service.creditFlow.finance.compensatory.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import javax.annotation.Resource;

import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.finance.compensatory.PlBidCompensatoryDao;
import com.zhiwei.credit.dao.creditFlow.finance.compensatory.PlBidCompensatoryFlowDao;
import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatory;
import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatoryFlow;
import com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryFlowService;

/**
 * 
 * @author 
 *
 */
public class PlBidCompensatoryFlowServiceImpl extends BaseServiceImpl<PlBidCompensatoryFlow> implements PlBidCompensatoryFlowService{
	@SuppressWarnings("unused")
	private PlBidCompensatoryFlowDao dao;
	@Resource
	private PlBidCompensatoryDao plBidCompensatorydao;
	public PlBidCompensatoryFlowServiceImpl(PlBidCompensatoryFlowDao dao) {
		super(dao);
		this.dao=dao;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryFlowService#saveCompensatoryFlow(com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatoryFlow)
	 */
	@Override
	public Boolean saveCompensatoryFlow(
			PlBidCompensatoryFlow plBidCompensatoryFlow) {
		// TODO Auto-generated method stub
		try{
			String requestNo="DCB1"+ContextUtil.createRuestNumber();
			plBidCompensatoryFlow.setRequestNo(requestNo);
			if(plBidCompensatoryFlow.getCompensatoryId()!=null&&!"".equals(plBidCompensatoryFlow.getCompensatoryId())){
				PlBidCompensatory plBidCompensatory =plBidCompensatorydao.get(plBidCompensatoryFlow.getCompensatoryId());
				if(plBidCompensatory!=null&&!"".equals(plBidCompensatory)){
					//未偿付金额
					BigDecimal unbackMoney=plBidCompensatory.getCompensatoryMoney().add(plBidCompensatory.getPunishMoney()).subtract(plBidCompensatory.getBackCompensatoryMoney()).subtract(plBidCompensatory.getBackPunishMoney()).subtract(plBidCompensatory.getPlateMoney());
					//本次偿付金额
					BigDecimal backMoney=plBidCompensatoryFlow.getBackCompensatoryMoney().add(plBidCompensatoryFlow.getBackPunishMoney()).add(plBidCompensatoryFlow.getFlateMoney());
					if(backMoney.compareTo(unbackMoney)<=0){
						if(backMoney.compareTo(unbackMoney)==0){
							plBidCompensatory.setBackStatus(2);//全部偿付
						}
						//已偿付代偿款
						plBidCompensatory.setBackCompensatoryMoney(plBidCompensatory.getBackCompensatoryMoney().add(plBidCompensatoryFlow.getBackCompensatoryMoney()));
						//已偿付罚息
						plBidCompensatory.setBackPunishMoney(plBidCompensatory.getBackPunishMoney().add(plBidCompensatoryFlow.getBackPunishMoney()));
						//已平账金额
						plBidCompensatory.setPlateMoney(plBidCompensatory.getPlateMoney().add(plBidCompensatoryFlow.getFlateMoney()));
						plBidCompensatory.setBackDate(plBidCompensatoryFlow.getBackDate());
						plBidCompensatorydao.merge(plBidCompensatory);
						plBidCompensatoryFlow.setBackStatus(2);
						dao.save(plBidCompensatoryFlow);
						return true;
					}else{
						return false;
					}
				}else{
					return false;
				}
				
			}else{
				return false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	/* 
	 * 用来检测本次回款金额是否合规
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.creditFlow.finance.compensatory.PlBidCompensatoryFlowService#check(com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatoryFlow)
	 */
	@Override
	public String[] check(PlBidCompensatoryFlow plBidCompensatoryFlow) {
		String[] ret=new String[2];
		try{
			//本次偿付金额
			BigDecimal backMoney=plBidCompensatoryFlow.getBackCompensatoryMoney().add(plBidCompensatoryFlow.getBackPunishMoney()).add(plBidCompensatoryFlow.getFlateMoney());
			if(backMoney.compareTo(new BigDecimal(0))>0){
				if(plBidCompensatoryFlow.getCompensatoryId()!=null&&!"".equals(plBidCompensatoryFlow.getCompensatoryId())){
					PlBidCompensatory plBidCompensatory =plBidCompensatorydao.get(plBidCompensatoryFlow.getCompensatoryId());
					if(plBidCompensatory!=null&&!"".equals(plBidCompensatory)){
						//未偿付罚息
						BigDecimal unpunish=plBidCompensatory.getPunishMoney().subtract(plBidCompensatory.getBackPunishMoney());
						BigDecimal unCompensatory=plBidCompensatory.getCompensatoryMoney().subtract(plBidCompensatory.getBackCompensatoryMoney());
						//未偿付金额
						BigDecimal unbackMoney=unpunish.add(unCompensatory).subtract(plBidCompensatory.getPlateMoney());
						
						if(backMoney.compareTo(unbackMoney)<=0){
							if(unpunish.compareTo(plBidCompensatoryFlow.getBackPunishMoney())>=0){
								if(unCompensatory.compareTo(plBidCompensatoryFlow.getBackCompensatoryMoney())>=0){
									ret[0]=Constants.CODE_SUCCESS;
									ret[1]="金额正确";
								}else{
									ret[0]=Constants.CODE_FAILED;
									ret[1]="偿付代偿款应小于未偿付代偿款";
								}
							}else{
								ret[0]=Constants.CODE_FAILED;
								ret[1]="偿付代偿罚息应小于未偿付代偿罚息";
								
							}
							
						}else{
							ret[0]=Constants.CODE_FAILED;
							ret[1]="本次回款金额应小于累计未偿付总额";
						}
					}else{
						ret[0]=Constants.CODE_FAILED;
						ret[1]="没有找到代偿款项";
					}
					
				}else{
					ret[0]=Constants.CODE_FAILED;
					ret[1]="参数不正确";
				}
			}else{
				ret[0]=Constants.CODE_FAILED;
				ret[1]="本次回款总金额应大于0";
			}
			
		}catch(Exception e){
			e.printStackTrace();
			ret[0]=Constants.CODE_FAILED;
			ret[1]="系统错误";
		}
		return ret;
	}

}