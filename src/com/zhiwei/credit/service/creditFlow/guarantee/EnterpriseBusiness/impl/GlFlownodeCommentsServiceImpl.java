package com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeCommentsDao;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeComments;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeCommentsService;

/**
 * 
 * @author 
 *
 */
public class GlFlownodeCommentsServiceImpl extends BaseServiceImpl<GlFlownodeComments> implements GlFlownodeCommentsService{
	@SuppressWarnings("unused")
	private GlFlownodeCommentsDao dao;
	
	public GlFlownodeCommentsServiceImpl(GlFlownodeCommentsDao dao) {
		super(dao);
		this.dao=dao;
	}

	public GlFlownodeComments getByFormId(Long formId){
		return dao.getByFormId(formId);
	}
}