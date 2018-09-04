package com.zhiwei.credit.service.creditFlow.auto.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.auto.PlBidAutoDao;
import com.zhiwei.credit.model.creditFlow.auto.PlBidAuto;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.creditFlow.auto.PlBidAutoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlKeepCreditlevelService;


public class PlBidAutoServiceImpl extends BaseServiceImpl<PlBidAuto> implements PlBidAutoService {

	private PlBidAutoDao dao;
	@Resource
	private PlKeepCreditlevelService plKeepCreditlevelService;
	public PlBidAutoServiceImpl(PlBidAutoDao dao){
		super(dao);
		this.dao=dao;
	}
	@Override
	public Integer getOrderNum() {
		return dao.getOrderNum();
	}
	@Override
	public PlBidAuto getPlBidAuto(Long userId) {
		return dao.getPlBidAuto(userId);
	}
	@Override
	public boolean isUpdate(PlBidAuto auto) {
		return dao.isUpdate(auto);
	}
	@Override
	public List<String> getCreditlevel() {
		return dao.getCreditlevel();
	}
	@Override
	public String savechk(PlBidAuto auto) {
		StringBuffer bf=new StringBuffer("{");
		int rateStartOrder=0;
		int rateEndOrder=0;
			if(auto.getRateStart()!=null){
				rateStartOrder=Integer.valueOf(plKeepCreditlevelService.get(Long.valueOf(auto.getRateStart())).getKeyStr());
			}
			if(auto.getRateEnd()!=null){
				rateEndOrder=Integer.valueOf(plKeepCreditlevelService.get(Long.valueOf(auto.getRateEnd())).getKeyStr());
			}
		 if(auto.getBidMoney().compareTo(new BigDecimal(PlBidAuto.MAXBIDMONEY))<0||!isDivide(auto.getBidMoney(),PlBidAuto.DIVIDEMONEY)){
			bf.append("\"bidMoneycode\":");
			bf.append("\""+Constants.FAILDFLAG+"\",\"bidMoney\":");
			bf.append("\"每次投标金额必须大于"+PlBidAuto.MAXBIDMONEY+"元。且必须能整除"+PlBidAuto.DIVIDEMONEY+"。\",");
		 }else{
			 bf.append("\"bidMoneycode\":");
			 bf.append("\""+Constants.SUCCESSFLAG+"\",\"bidMoney\":");
			 bf.append("\"\","); 
		 }
		 
		 if(auto.getInterestStart()<PlBidAuto.ISTART||auto.getInterestEnd()>PlBidAuto.IEND){
				bf.append("\"interestcode\":");
				bf.append("\""+Constants.FAILDFLAG+"\",\"interest\":");
				bf.append("\"利率范围应该在"+PlBidAuto.ISTART+"%至"+PlBidAuto.IEND+"%之间，填写的数值必须是正整数。\",");
			 }else{
				 bf.append("\"interestcode\":");
				 bf.append("\""+Constants.SUCCESSFLAG+"\",\"interest\":");
				 bf.append("\"\","); 
			 }
		 
		 if(auto.getPeriodStart()<PlBidAuto.PSTART||auto.getInterestEnd()>PlBidAuto.PEND){
				bf.append("\"periodcode\":");
				bf.append("\""+Constants.FAILDFLAG+"\",\"period\":");
				bf.append("\"借款期限应该在"+PlBidAuto.PSTART+"到"+PlBidAuto.PEND+"个月以内。\",");
			 }else{
				 bf.append("\"periodcode\":");
				 bf.append("\""+Constants.SUCCESSFLAG+"\",\"period\":");
				 bf.append("\"\","); 
			 }
		 
		 if(auto.getKeepMoney().compareTo(new BigDecimal(0))<0){
				bf.append("\"keepMoneycode\":");
				bf.append("\""+Constants.FAILDFLAG+"\",\"keepMoney\":");
				bf.append("\"账户保留金额不能为负值，必须是0或者正整数。\",");
			 }else{
				 bf.append("\"keepMoneycode\":");
				 bf.append("\""+Constants.SUCCESSFLAG+"\",\"keepMoney\":");
				 bf.append("\"\","); 
			 }
		 if(rateEndOrder>rateStartOrder){
				bf.append("\"ratecode\":");
				bf.append("\""+Constants.FAILDFLAG+"\",\"rate\":");
				bf.append("\"信用等级范围不能低于下限\",");
			 }else{
				 bf.append("\"ratecode\":");
				 bf.append("\""+Constants.SUCCESSFLAG+"\",\"rate\":");
				 bf.append("\"\","); 
			 }
		if(bf.length()>1){
			bf.deleteCharAt(bf.length() - 1);
		}
		bf.append("}");
		return bf.toString();
	}
	private boolean isDivide(BigDecimal a,Integer b){

		double ab = (Double.valueOf(a.toString()) * 100)
				% (Double.valueOf(b.toString()) * 100);
		if (ab == 0) {
			return true;
		}else{
			return false;
		}
	
	}
	/**
	 * 开通p2p时自动投标初始化
	 * 初始化pl_bid_auto表
	 * @param id
	 */
	@Override
	public PlBidAuto initPlBidAuto(BpCustMember mem) {
		//自动投标添加一行记录
		PlBidAuto auto = new PlBidAuto();
		auto.setUserID(mem.getId());
		auto.setBidMoney(new java.math.BigDecimal(200));
		auto.setInterestStart(8);
		auto.setInterestEnd(24);
		auto.setPeriodStart(0);
		auto.setPeriodEnd(36);
		auto.setRateStart(null);
		auto.setRateEnd(null);
		auto.setKeepMoney(new java.math.BigDecimal(PlBidAuto.TOTALMONEY));
		auto.setIsOpen(0);
		auto.setOrderTime(null);
		auto.setBanned(0);
		auto=dao.save(auto);
		return auto;
	}
	
	@Override
	public PlBidAuto getByRequestNo(String requestNo) {
		return dao.getByRequestNo(requestNo);
	}
	@Override
	public List<PlBidAuto> queryCardcode(String userId,String isOpen,String banned,Integer start,Integer limit) {
		return dao.queryCardcode(userId,isOpen,banned, start, limit);
	}
}
