package com.zhiwei.credit.dao.creditFlow.financingAgency;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;

/**
 * 
 * @author 
 *
 */
public interface PlBidInfoDao extends BaseDao<PlBidInfo>{

	PlBidInfo getByOrderNo(String orderNum);
	
	List<PlBidInfo> queryUserName(Map<String, String> map);
	
	public List<PlBidInfo> getPreAuthorizationNum(Long bidId);
	public PlBidInfo getByPreAuthorizationNum(String authorizationNum);
	public List<PlBidInfo> getByBidId(Long bidId);
	List<PlBidInfo> getInfo(Map<String, String> map,List<Integer> idList);
}