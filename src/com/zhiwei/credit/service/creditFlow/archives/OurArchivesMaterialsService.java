package com.zhiwei.credit.service.creditFlow.archives;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.archives.OurArchivesMaterials;

/**
 * 
 * @author 
 *
 */
public interface OurArchivesMaterialsService extends BaseService<OurArchivesMaterials>{
	public List<OurArchivesMaterials> getByBusinessType(String businessType,PagingBean pb);
	 public List<OurArchivesMaterials> getbyoperationTypeKey(String operationTypeKey);
	public List<OurArchivesMaterials> getByProductId(Long valueOf);
	public List<OurArchivesMaterials> getListByType(String businessType,
			String operationType);
	public List<OurArchivesMaterials> checkIsExit(String productId,
			Long materialsId, String businessType);

	public List<OurArchivesMaterials> getByPProductIdAndOperationType(
			String productId, String businessType);
}


