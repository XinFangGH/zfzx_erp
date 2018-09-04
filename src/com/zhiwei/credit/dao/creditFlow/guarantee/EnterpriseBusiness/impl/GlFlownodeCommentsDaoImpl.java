package com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeCommentsDao;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeComments;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlFlownodeCommentsDaoImpl extends BaseDaoImpl<GlFlownodeComments> implements GlFlownodeCommentsDao{

	public GlFlownodeCommentsDaoImpl() {
		super(GlFlownodeComments.class);
	}
	
	public GlFlownodeComments getByFormId(Long formId){
		String hql = "from GlFlownodeComments gf where gf.formId=?";
		List<GlFlownodeComments> flow = findByHql(hql, new Object[]{formId});
		if(flow.size()>0){
			return flow.get(0);
		}
		return null;
	}

}