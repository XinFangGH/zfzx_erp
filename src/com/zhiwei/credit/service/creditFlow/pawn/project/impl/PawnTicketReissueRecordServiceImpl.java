package com.zhiwei.credit.service.creditFlow.pawn.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PawnTicketReissueRecordDao;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnTicketReissueRecord;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnTicketReissueRecordService;

/**
 * 
 * @author 
 *
 */
public class PawnTicketReissueRecordServiceImpl extends BaseServiceImpl<PawnTicketReissueRecord> implements PawnTicketReissueRecordService{
	@SuppressWarnings("unused")
	private PawnTicketReissueRecordDao dao;
	
	public PawnTicketReissueRecordServiceImpl(PawnTicketReissueRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public PawnTicketReissueRecord getByLossRecordId(Long projectId,
			String businessType, Long lossRecordId) {
		
		return dao.getByLossRecordId(projectId, businessType, lossRecordId);
	}

	@Override
	public List<PawnTicketReissueRecord> getListByProjectId(Long projectId,
			String businessType) {
		
		return dao.getListByProjectId(projectId, businessType);
	}

}