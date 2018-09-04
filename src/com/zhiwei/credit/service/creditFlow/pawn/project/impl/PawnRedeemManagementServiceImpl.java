package com.zhiwei.credit.service.creditFlow.pawn.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.pawn.project.PawnRedeemManagementDao;
import com.zhiwei.credit.model.creditFlow.pawn.project.PawnRedeemManagement;
import com.zhiwei.credit.service.creditFlow.pawn.project.PawnRedeemManagementService;

/**
 * 
 * @author 
 *
 */
public class PawnRedeemManagementServiceImpl extends BaseServiceImpl<PawnRedeemManagement> implements PawnRedeemManagementService{
	@SuppressWarnings("unused")
	private PawnRedeemManagementDao dao;
	
	public PawnRedeemManagementServiceImpl(PawnRedeemManagementDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PawnRedeemManagement> getListByProjectId(Long projectId,
			String businessType) {
		
		return dao.getListByProjectId(projectId, businessType);
	}

}