package com.zhiwei.credit.service.creditFlow.pawn.pawnItems.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnItemsListDao;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.VPawnItemsList;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;
import com.zhiwei.credit.service.creditFlow.pawn.pawnItems.PawnItemsListService;

/**
 * 
 * @author 
 *
 */
public class PawnItemsListServiceImpl extends BaseServiceImpl<PawnItemsList> implements PawnItemsListService{
	@SuppressWarnings("unused")
	private PawnItemsListDao dao;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private MortgageService mortgageService;
	@Resource
	private SlProcreditMaterialsService slProcreditMaterialsService;
	public PawnItemsListServiceImpl(PawnItemsListDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PawnItemsList> getListByProjectId(Long projectId,
			String businessType) {
		return dao.getListByProjectId(projectId, businessType);
	}

	public void deleteAllObjectDatas(Class entityClass,String mortgageIds) throws Exception {
		boolean isDeleteAll = false;
		try{
			if(null!=mortgageIds&&!"".equals(mortgageIds)){
				String[] mortIds = mortgageIds.split(",");
				for(int i = 0;i<mortIds.length;i++){
					long morttgageId = Long.parseLong(mortIds[i]);
					PawnItemsList p= ((PawnItemsList) creditBaseDao.getById(entityClass, morttgageId));
					long typeId=p.getPawnItemType();
					List<SlProcreditMaterials> list=slProcreditMaterialsService.getByProjIdBusinessTypeKey(p.getPawnItemId().toString(), "Pawn");
					for(SlProcreditMaterials s:list){
						slProcreditMaterialsService.remove(s);
					}
					isDeleteAll = ((creditBaseDao.deleteDatas(entityClass, morttgageId)) && (mortgageService.deleteObjectDatas(Integer.valueOf(String.valueOf(typeId)),Integer.valueOf(String.valueOf( morttgageId)),"pawn")));
				}
			}
			//接口类定义的删除方法，所以是public
			//isDeleteAll = ((this.deleteMortgage(entityClass, id)) && (this.deleteObjectDatas(typeid, id)));// && (this.deleteContractMortgage(id)) && (this.deleteMortgageDataFromContractCategory(id)));
			if(isDeleteAll){
				JsonUtil.jsonFromObject("删除当物主表及附表信息成功!!!", true);
			}else{
				JsonUtil.jsonFromObject("删除当物主表及附表信息失败!!!", false);
			}
		}catch(Exception e){
			logger.error("删除当物主表及附表信息出错:"+e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<VPawnItemsList> listByProjectId(String pawnItemStatus,
			HttpServletRequest request, PagingBean pb) {
		
		return dao.listByProjectId(pawnItemStatus, request, pb);
	}
}