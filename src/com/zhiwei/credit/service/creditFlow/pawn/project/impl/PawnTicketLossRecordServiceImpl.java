package com.zhiwei.credit.service.creditFlow.pawn.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PawnTicketLossRecordDao;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnTicketLossRecord;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnTicketLossRecordService;

/**
 * 
 * @author 
 *
 */
public class PawnTicketLossRecordServiceImpl extends BaseServiceImpl<PawnTicketLossRecord> implements PawnTicketLossRecordService{
	@SuppressWarnings("unused")
	private PawnTicketLossRecordDao dao;
	
	public PawnTicketLossRecordServiceImpl(PawnTicketLossRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PawnTicketLossRecord> getListByProjectId(Long projectId,
			String businessType) {
		return dao.getListByProjectId(projectId, businessType);
	}

}