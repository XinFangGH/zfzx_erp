package com.zhiwei.credit.dao.creditFlow.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;

/**
 * 
 * @author 
 *
 */
public interface OurProcreditMaterialsEnterpriseDao extends BaseDao<OurProcreditMaterialsEnterprise>{
	
	 public  List<OurProcreditMaterialsEnterprise> getListByParentId(Integer parentId,PagingBean pb);
	  public  void  initMaterials(String projectId,Integer businessType);
	  public  void  initMaterials(String projectId,String businessTypeKey,String operationTypeKey);
	  public  List<OurProcreditMaterialsEnterprise> getListByParentIdAndType(Integer parentId,String operationTypeKey);
	  public void deleteByProductId(String id);
	  public List<OurProcreditMaterialsEnterprise> getByProductId(Long productId);
	  public List<OurProcreditMaterialsEnterprise> getByProjectId(Long projectId);
	  public List<OurProcreditMaterialsEnterprise> getListByIdsNotNull(String node,String operationTypeKey);
	  /**
		 * 依据项目的productId初始化贷款材料（修改，增加，编辑）
		 * add by  linyan 2014-8-11
		 * @param projectId  项目id
		 * @param businessType 项目类型
		 * @param productId  产品编号
		 */
	  public void initMaterialsByProductId(Long projectId, String businessType,String operationTypeKey,
			Long productId);
	  public List<OurProcreditMaterialsEnterprise> getByProjIdAndOperationType(
				String productId, Long materialsId);
	  public List<OurProcreditMaterialsEnterprise> listByMaterialsIdGroupById(
				String productId);
	  public List<OurProcreditMaterialsEnterprise> listByProductIdAndParentId(Long productId,Integer parentId);
} 