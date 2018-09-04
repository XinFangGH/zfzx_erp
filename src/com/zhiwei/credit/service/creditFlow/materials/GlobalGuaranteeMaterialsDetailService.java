package com.zhiwei.credit.service.creditFlow.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsDetail;

/**
 * 担保材料
 * @author zhangyl
 *
 */
public interface GlobalGuaranteeMaterialsDetailService extends BaseService<GlobalGuaranteeMaterialsDetail>{
	
	//新建项目时初始化材料(小额贷款)
	public boolean initMaterials(String projId,Long businessType);
	
	//根据项目ID和是否显示查询担保材料
	public List<GlobalGuaranteeMaterialsDetail> getByProjIdAndShow(String projId,String businessType,boolean show);
	
	public boolean initMaterialsOfEnterprise(Long proId,Long businessTypeId);
	
	
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
	
	List<GlobalGuaranteeMaterialsDetail> getListByParentId(Integer parentId);
	
	
	
}


