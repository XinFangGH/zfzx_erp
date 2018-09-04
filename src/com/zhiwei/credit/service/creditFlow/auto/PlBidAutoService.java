package com.zhiwei.credit.service.creditFlow.auto;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.auto.PlBidAuto;
import com.zhiwei.credit.model.p2p.BpCustMember;

public interface PlBidAutoService extends BaseService<PlBidAuto> {

	public Integer getOrderNum();
	public PlBidAuto getPlBidAuto(Long userId);
	public boolean isUpdate(PlBidAuto auto);
	public List<String> getCreditlevel();
	/**
	 * 保存自动投标数据校验
	 * @param PlBidAuto auto
	 * @return
	 */
	public String savechk(PlBidAuto auto);
	/**
	 * 开通p2p时，自动投标初始化
	 * 初始化pl_bid_auto表
	 * @param id
	 */
	public PlBidAuto initPlBidAuto(BpCustMember mem);
	
	public PlBidAuto getByRequestNo(String string);
	
	public List<PlBidAuto> queryCardcode(String userId,String isOpen,String banned,Integer start,Integer limit);
}
