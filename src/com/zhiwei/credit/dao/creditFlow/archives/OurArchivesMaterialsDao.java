package com.zhiwei.credit.dao.creditFlow.archives;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.archives.OurArchivesMaterials;

/**
 * 
 * @author 
 *
 */
public interface OurArchivesMaterialsDao extends BaseDao<OurArchivesMaterials>{
	 public void initMaterials(String projectId,Integer businessType);
	 public void initMateriais(String projectId,String businessTypeKey,String operationTypeKey);
	 public List<OurArchivesMaterials> getByBusinessType(String businessType,PagingBean pb);
	 public List<OurArchivesMaterials> getbyoperationTypeKey(String operationTypeKey);
	 public List<OurArchivesMaterials> checkIsExit(String productId,
				Long materialsId, String businessType);
	public List<OurArchivesMaterials> getByPProductIdAndOperationType(
			String productId, String businessType);
	public List<OurArchivesMaterials> getByProductId(Long productId);
	public List<OurArchivesMaterials> getListByType(String businessType,String operationType);
	 /**
	 * 依据项目的productId初始化归档材料（修改，增加，编辑）
	 * add by  linyan 2014-9-12
	 * @param projectId  项目id
	 * @param businessType 项目类型
	 * @param productId  产品编号
	 */
  public void initMaterialsByProductId(Long projectId, String businessType,String operationTypeKey,Long productId);
}