package com.zhiwei.credit.service.creditFlow.financingAgency;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;

/**
 * 
 * @author 
 *
 */
public interface PlBidInfoService extends BaseService<PlBidInfo>{

	PlBidInfo getByOrderNo(String orderNum);
	
	List<PlBidInfo> queryUserName(Map<String, String> map);
	/**
	 * 根据标id
	 * 查询投资成功的并且有第三方流水号的记录
	 * @param bidId
	 * @return
	 */
	public List<PlBidInfo> getPreAuthorizationNum(Long bidId);

	public PlBidInfo getByPreAuthorizationNum(String authorizationNum);
	public  BpCustMember getLoanMember(PlBidPlan bidplan);
	/**
	 * 根据标的号查找对应的plbidinfo 记录
	 */
	public List<PlBidInfo> getByBidId(Long bidId);
	/**
	 * 通过订单号获取
	 * @param ordId
	 * @return
	 */
	public PlBidInfo getByOrdId(String ordId);
	
	List<PlBidInfo> getInfo(Map<String, String> map,List<Integer> idList);
}


