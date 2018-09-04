package com.zhiwei.credit.service.creditFlow.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;

/**
 * 
 * @author 
 *
 */
public interface OurProcreditMaterialsEnterpriseService extends BaseService<OurProcreditMaterialsEnterprise>{
	 
	 public List<OurProcreditMaterialsEnterprise> getListByParentId(Integer parentId,PagingBean pb);
	 
	 public  List<OurProcreditMaterialsEnterprise> getListByParentIdAndType(Integer parentId,String operationTypeKey);
	 
	 public void deleteByProductId(String id);
	 
	 public List<OurProcreditMaterialsEnterprise> getByProjectId(Long projectId);
	 
	 public List<OurProcreditMaterialsEnterprise> getByProductId(Long productId);
	 
	 public List<OurProcreditMaterialsEnterprise> getListByIdsNotNull(String node,String operationTypeKey);
	 
	 public List<OurProcreditMaterialsEnterprise> listByMaterialsIdGroupById(String productId);
	 
	 public List<OurProcreditMaterialsEnterprise> getByPProductIdAndOperationType(String productId, Long materialsId);
	 
	 public List<OurProcreditMaterialsEnterprise> listByProductIdAndParentId(Long productId,Integer parentId);
	 
	 public List<OurProcreditMaterialsEnterprise> listByProjectAndBusinessType(Long projectId, String businessType);
}


