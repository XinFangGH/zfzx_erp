package com.zhiwei.credit.service.creditFlow.financingAgency.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.creditFlow.financingAgency.PlBidInfoDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidInfoService;
import com.zhiwei.credit.service.creditFlow.financingAgency.PlBidPlanService;
import com.zhiwei.credit.service.customer.BpCustRelationService;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;
import com.zhiwei.credit.service.p2p.BpCustMemberService;

/**
 * 
 * @author 
 *
 */
public class PlBidInfoServiceImpl extends BaseServiceImpl<PlBidInfo> implements PlBidInfoService{
	@SuppressWarnings("unused")
	private PlBidInfoDao dao;
	@Resource
	private InvestPersonInfoService investPersonInfoService;
	@Resource
	private BpCustRelationService bpCustRelationService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private PlBidPlanService plBidPlanService;
	
	//得到整个系统全部的config.properties读取的所有资源
	private static Map configMap = AppUtil.getConfigMap();
	public PlBidInfoServiceImpl(PlBidInfoDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public PlBidInfo getByOrderNo(String orderNum) {
		return dao.getByOrderNo(orderNum);
	}

	@Override
	public List<PlBidInfo> queryUserName(Map<String, String> map) {
		return dao.queryUserName(map);
	}

	@Override
	public List<PlBidInfo> getPreAuthorizationNum(Long bidId) {
		// TODO Auto-generated method stub
		return dao.getPreAuthorizationNum(bidId);
	}

	@Override
	public PlBidInfo getByPreAuthorizationNum(String authorizationNum) {
		// TODO Auto-generated method stub
		return dao.getByPreAuthorizationNum(authorizationNum);
	}
	@Override
	public BpCustMember getLoanMember(PlBidPlan bidplan) {
		BpCustMember member=new BpCustMember();
		// 借款人关系 获取 网站注册用户信息
		BpCustRelation custRelation = new BpCustRelation();
		// 网站注册用户
		// 项目类型 企业直投 B_Dir 企业 债权 B_Or 个人直投 P_Dir 个人债权 P_Or * @return String
		String loanUserType = "";
		Long loanUserId = null;
		Long custMamberId = null;
		if (bidplan.getProType().equals("B_Dir")) {
			loanUserType = "b_loan";
			loanUserId = bidplan.getBpBusinessDirPro().getBusinessId();
		} else if (bidplan.getProType().equals("B_Or")) {//有原始债权人概念
			String loginName=bidplan.getBpBusinessOrPro().getReceiverP2PAccountNumber();
			member=bpCustMemberService.getMemberUserName(loginName,null);
		} else if (bidplan.getProType().equals("P_Dir")) {
			loanUserType = "p_loan";
			loanUserId = bidplan.getBpPersionDirPro().getPersionId();
			
		} else if (bidplan.getProType().equals("P_Or")) {//有原始债权人概念
			String loginName=bidplan.getBpPersionOrPro().getReceiverP2PAccountNumber();
			member=bpCustMemberService.getMemberUserName(loginName,null);
			
		}
		if (loanUserId != null) {
			BpCustRelation bpCustRelation = bpCustRelationService.getByLoanTypeAndId(loanUserType, loanUserId);
			if (bpCustRelation != null) {
				custMamberId = bpCustRelation.getP2pCustId();
			}
		}
		if (custMamberId != null && !custMamberId.equals("")) {
			member = bpCustMemberService.get(custMamberId);
		}
		return member;
	}

	@Override
	public List<PlBidInfo> getByBidId(Long bidId) {
		// TODO Auto-generated method stub
		return dao.getByBidId(bidId);
	}
	@Override
	public PlBidInfo getByOrdId(String ordId) {
		return dao.getByOrderNo(ordId);
	}
	@Override
	public List<PlBidInfo> getInfo(Map<String, String> map,List<Integer> idList) {
		// TODO Auto-generated method stub
		return dao.getInfo(map,idList);
	}
}