package com.zhiwei.credit.service.p2p.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;
import com.zhiwei.credit.model.p2p.materials.PlWebShowMaterials;

/**
 * 
 * @author 
 *
 */
public interface PlWebShowMaterialsService extends BaseService<PlWebShowMaterials>{

	public List<PlWebShowMaterials> getByProjIdAndShow(String projId,
			String businessType);

	public List<PlWebShowMaterials> getByProMaterialsId(String projId,
			SlProcreditMaterials slProcreditMaterials, String businessType,
			String operationType);

	public List<PlWebShowMaterials> listByMaterialsIdGroupById(String projId,
			String businessType, String operationType);

	public List<PlWebShowMaterials> listByMaterialsIdAndOperationTypeKey(
			String projId, String businessType, String operationType,
			Long valueOf);
	
}


