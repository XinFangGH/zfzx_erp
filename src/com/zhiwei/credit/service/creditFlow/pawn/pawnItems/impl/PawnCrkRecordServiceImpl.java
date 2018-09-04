package com.zhiwei.credit.service.creditFlow.pawn.pawnItems.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.pawn.pawnItems.PawnCrkRecordDao;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnCrkRecord;
import com.zhiwei.credit.service.creditFlow.pawn.pawnItems.PawnCrkRecordService;

/**
 * 
 * @author 
 *
 */
public class PawnCrkRecordServiceImpl extends BaseServiceImpl<PawnCrkRecord> implements PawnCrkRecordService{
	@SuppressWarnings("unused")
	private PawnCrkRecordDao dao;
	
	public PawnCrkRecordServiceImpl(PawnCrkRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PawnCrkRecord> getListByPawnItemId(Long projectId,
			String businessType, Long pawnItemId) {
		
		return dao.getListByPawnItemId(projectId, businessType, pawnItemId);
	}

}