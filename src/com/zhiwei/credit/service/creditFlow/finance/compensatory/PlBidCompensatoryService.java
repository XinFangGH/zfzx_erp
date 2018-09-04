package com.zhiwei.credit.service.creditFlow.finance.compensatory;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatory;

/**
 * 
 * @author 
 *
 */
public interface PlBidCompensatoryService extends BaseService<PlBidCompensatory>{

	/**
	 * 平台代偿
	 * @param planId
	 * @param peridId
	 * @param cardNo
	 * @param type
	 */
	public  void saveComPensatoryService(String planId, String peridId, String cardNo,String type,String  period);

	/**
	 * 查询代偿台账列表
	 * @param pb
	 * @param map
	 * @return
	 */
	public List<PlBidCompensatory> compensatoryList(PagingBean pb,
			Map<String, String> map);

	/**
	 * 联合查询出来所有款项信息
	 * @param id
	 * @return
	 */
	public PlBidCompensatory getOneList(Long id);
	/**
	 * 定时器零点重新计算用户的罚息（只计算未偿付的代偿款项）
	 */
	public void  calculateOverDueDays();
	
}


