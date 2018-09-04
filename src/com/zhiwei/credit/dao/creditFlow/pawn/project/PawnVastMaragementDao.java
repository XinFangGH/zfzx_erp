package com.zhiwei.credit.dao.creditFlow.pawn.project;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnVastMaragement;

/**
 * 
 * @author 
 *
 */
public interface PawnVastMaragementDao extends BaseDao<PawnVastMaragement>{
	public List<PawnVastMaragement> getListByProjectId(Long projectId,String businessType);
}