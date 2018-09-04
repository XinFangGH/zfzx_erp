package com.zhiwei.credit.service.creditFlow.pawn.pawnItems;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnInspectionRecord;

/**
 * 
 * @author 
 *
 */
public interface PawnInspectionRecordService extends BaseService<PawnInspectionRecord>{
	public List<PawnInspectionRecord> getListByPawnItemId(Long projectId,String businessType,Long pawnItemId);
}


