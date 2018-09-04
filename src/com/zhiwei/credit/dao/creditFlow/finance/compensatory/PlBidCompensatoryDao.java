package com.zhiwei.credit.dao.creditFlow.finance.compensatory;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatory;

/**
 * 
 * @author 
 *
 */
public interface PlBidCompensatoryDao extends BaseDao<PlBidCompensatory>{

	/**
	 * 查询代偿台账列表
	 * @param pb
	 * @param map
	 * @return
	 */
	public List<PlBidCompensatory> compensatoryList(PagingBean pb,
			Map<String, String> map);

	/**
	 * @param id
	 * @return
	 */
	public PlBidCompensatory getOneList(Long id);
	
}