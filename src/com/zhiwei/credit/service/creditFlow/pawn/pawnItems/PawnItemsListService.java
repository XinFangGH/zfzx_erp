package com.zhiwei.credit.service.creditFlow.pawn.pawnItems;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.VPawnItemsList;

/**
 * 
 * @author 
 *
 */
public interface PawnItemsListService extends BaseService<PawnItemsList>{
	public List<PawnItemsList> getListByProjectId(Long projectId,String businessType);
	
	//为删除主表,次表添加事务的删除方法
	public void deleteAllObjectDatas(Class entityClass,String mortgageIds) throws Exception;
	public List<VPawnItemsList> listByProjectId(String pawnItemStatus,HttpServletRequest request,PagingBean pb);

}


