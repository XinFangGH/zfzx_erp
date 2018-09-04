package com.zhiwei.credit.dao.creditFlow.auto;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.auto.PlBidAuto;

public interface PlBidAutoDao extends BaseDao<PlBidAuto>{

	public Integer getOrderNum();
	public PlBidAuto getPlBidAuto(Long userId);
	public boolean isUpdate(PlBidAuto auto);
	public List<String> getCreditlevel();
	public PlBidAuto getByRequestNo(String requestNo);
	
	public List<PlBidAuto> queryCardcode(String userId,String isOpen,String banned,Integer start,Integer limit);
}
