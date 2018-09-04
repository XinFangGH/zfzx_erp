package com.zhiwei.credit.service.creditFlow.pawn.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PawnContinuedManagmentDao;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnContinuedManagment;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnContinuedManagmentService;

/**
 * 
 * @author 
 *
 */
public class PawnContinuedManagmentServiceImpl extends BaseServiceImpl<PawnContinuedManagment> implements PawnContinuedManagmentService{
	@SuppressWarnings("unused")
	private PawnContinuedManagmentDao dao;
	
	public PawnContinuedManagmentServiceImpl(PawnContinuedManagmentDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PawnContinuedManagment> getListByProjectId(Long projectId,
			String businessType, Long continueId) {
		
		return dao.getListByProjectId(projectId, businessType, continueId);
	}

}