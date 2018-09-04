package com.zhiwei.credit.service.creditFlow.materials.impl;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com/
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.materials.GlobalGuaranteeMaterialsDetailDao;
import com.zhiwei.credit.dao.system.DictionaryDao;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsDetail;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.materials.GlobalGuaranteeMaterialsDetailService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;

public class GlobalGuaranteeMaterialsDetailServiceImpl extends BaseServiceImpl<GlobalGuaranteeMaterialsDetail> implements GlobalGuaranteeMaterialsDetailService{
	@SuppressWarnings("unused")
	private GlobalGuaranteeMaterialsDetailDao dao;
	@Resource
	private DictionaryDao dictionaryDao;
	@Resource
	private  AreaDicService areaDicService;
	
	
	public GlobalGuaranteeMaterialsDetailServiceImpl(GlobalGuaranteeMaterialsDetailDao dao) {
		super(dao);
		this.dao=dao;
	}
	/**
	 * 贷款材料初始化（新建项目时用到）
	 */
	@Override
	public boolean initMaterials(String projId,Long businessType) {
		String itemName = "贷款材料";
		List<Dictionary> list = dictionaryDao.getByItemName(itemName);
		
		for(Dictionary dictionary : list){
			GlobalGuaranteeMaterialsDetail slProcreditMaterials = new GlobalGuaranteeMaterialsDetail();
			slProcreditMaterials.setProjId(projId);
			slProcreditMaterials.setMaterialsId(dictionary.getDicId());
			slProcreditMaterials.setMaterialsName(dictionary.getItemValue());
			slProcreditMaterials.setIsShow(false);
			slProcreditMaterials.setDatumNums(0);
			slProcreditMaterials.setParentId(null);
			slProcreditMaterials.setBusinessTypeId(businessType.intValue());
			dao.save(slProcreditMaterials);
		}
		return true;
	}
	//根据项目Id和是否显示查询担保材料
	@Override
	public List<GlobalGuaranteeMaterialsDetail> getByProjIdAndShow(String projId,String businessType,boolean show)
	{
		return dao.getByProjIdAndShow(projId,businessType,show);
	}
	@Override
	public boolean initMaterialsOfEnterprise(Long proId,Long businessType) {
		try
		{
		           List<AreaDic> areaList=areaDicService.listByParentId(1966);
		           for(AreaDic a:areaList)
		           {
		        	   List<AreaDic> list1=this.areaDicService.listByParentId(a.getId());
		        	   for(AreaDic ac:list1)
		        	   {
		        		   GlobalGuaranteeMaterialsDetail slProcreditMaterials = new GlobalGuaranteeMaterialsDetail();
			        	   slProcreditMaterials.setProjId(String.valueOf(proId));
			        	   slProcreditMaterials.setMaterialsId(Long.valueOf(ac.getId()));
			        	   slProcreditMaterials.setIsShow(false);
			        	   slProcreditMaterials.setParentId(a.getId());
			        	   slProcreditMaterials.setBusinessTypeId(businessType.intValue());
			        	   slProcreditMaterials.setDatumNums(0);
			        	   slProcreditMaterials.setIsReceive(false);
			        	   slProcreditMaterials.setMaterialsName(ac.getText());
			        	   dao.save(slProcreditMaterials);
		        	   }
		           }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public List<GlobalGuaranteeMaterialsDetail> getByProjIdAndParentId(String projId,Integer parentId,
			String businessType, boolean show) {
		
		return this.dao.getByProjIdAndParentId(projId, parentId, businessType, show);
	}
	
	/**
	 * 取得某个业务品种的项目数据列表
	 * @param projId
	 * @param businessType
	 * 删除一个任务的时候需要删除sl_procredit_materials表中(贷款材料)所有关联数据
	 * add by lu 2012.02.22
	 */
	public List<GlobalGuaranteeMaterialsDetail> getByProjIdBusinessTypeKey(String projId,String businessType){
		return dao.getByProjIdBusinessTypeKey(projId, businessType);
	}
	@Override
	public List<GlobalGuaranteeMaterialsDetail> getList(String projId,
			String businessType, String operationTypeKey) {
		
		return dao.getList(projId, businessType, operationTypeKey);
	}
	@Override
	public GlobalGuaranteeMaterialsDetail getSLMaterials(String projId,
			String businessType, Long materialId) {
		
		return dao.getSLMaterials(projId, businessType, materialId);
	}
	@Override
	public List<GlobalGuaranteeMaterialsDetail> getByProjId(String projId,
			String businessType, boolean show) {
		
		return dao.getByProjId(projId, businessType, show);
	}
	@Override
	public List<GlobalGuaranteeMaterialsDetail> getListByParentId(
			Integer parentId) {
		// TODO Auto-generated method stub
		return null;
	}


}