package com.zhiwei.credit.service.creditFlow.assuretenet;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;

/**
 * 
 * @author 
 *
 */
public interface OurProcreditAssuretenetService extends BaseService<OurProcreditAssuretenet>{
	   
	    /**
	     * 根据业务种类获取获取担保材料列表
	     * @param businessTypeKey
	     * @return
	     */
	public List<OurProcreditAssuretenet> getListByBussinessType(
			String businessTypeKey,PagingBean pb);

	   public void deleteByProductId(String id);

	   public List<OurProcreditAssuretenet> getByProjectId(Long projectId);

	   public List<OurProcreditAssuretenet> getByProductId(Long productId);

	   public List<OurProcreditAssuretenet> getAssuretenetTree(String customerType);

	public List<OurProcreditAssuretenet> getListByType(String businessType,
			String operationType);

	public List<OurProcreditAssuretenet> getByPProductIdAndOperationType(
			String productId, String businessType);

	public List<OurProcreditAssuretenet> checkIsExit(String productId,
			Long assuretenetId, String businessType);

}


