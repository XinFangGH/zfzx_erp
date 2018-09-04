package com.zhiwei.credit.dao.creditFlow.pawn.pawnItems;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.VPawnItemsList;

/**
 * 
 * @author 
 *
 */
public interface PawnItemsListDao extends BaseDao<PawnItemsList>{
	public List<PawnItemsList> getListByProjectId(Long projectId,String businessType);
	public List<VPawnItemsList> listByProjectId(String pawnItemStatus,HttpServletRequest request,PagingBean pb);
}