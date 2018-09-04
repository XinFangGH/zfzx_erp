package com.zhiwei.credit.dao.creditFlow.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsDetail;

/**
 * 
 * @author 
 *
 */
public interface GlobalGuaranteeMaterialsDetailDao extends BaseDao<GlobalGuaranteeMaterialsDetail>{
	
	//根据项目ID和是否显示查询担保材料
	public List<GlobalGuaranteeMaterialsDetail> getByProjIdAndShow(String projId,String businessTypeId,boolean show);
	
	public List<GlobalGuaranteeMaterialsDetail> getByProjIdAndParentId(String projId,Integer parentId,String businessType,boolean show);
	
	/**
	 * 取得某个业务品种的项目数据列表
	 * @param projId
	 * @param businessType
	 * 删除一个任务的时候需要删除global_guarantee_materials_detail表中(贷款材料)所有关联数据
	 * add by lu 2012.02.22
	 */
	public List<GlobalGuaranteeMaterialsDetail> getByProjIdBusinessTypeKey(String projId,String businessType);
	
	public List<GlobalGuaranteeMaterialsDetail> getList(String projId,String businessType,String operationTypeKey);
	
	public GlobalGuaranteeMaterialsDetail getSLMaterials(String projId,String businessType,Long materialId);
	
	public List<GlobalGuaranteeMaterialsDetail> getByProjId(String projId,String businessType,boolean show);
	
	
	
}