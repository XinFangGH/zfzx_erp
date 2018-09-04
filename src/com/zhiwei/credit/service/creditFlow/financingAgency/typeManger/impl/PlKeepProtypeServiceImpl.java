package com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.typeManger.PlKeepProtypeDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepProtype;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlKeepProtypeService;

/**
 * 
 * @author 
 *
 */
public class PlKeepProtypeServiceImpl extends BaseServiceImpl<PlKeepProtype> implements PlKeepProtypeService{
	@SuppressWarnings("unused")
	private PlKeepProtypeDao dao;
	
	public PlKeepProtypeServiceImpl(PlKeepProtypeDao dao) {
		super(dao);
		this.dao=dao;
	}

	//伪删除
	@Override
	public void deleteProtype(Long id) {
		String hql = "update PlKeepProtype set isDelete = 1 where typeId = ?";
		dao.update(hql, new Object[]{id});
	}

	@Override
	public List<PlKeepProtype> getList(Short isDelete, PagingBean pb) {
		String hql = " from PlKeepProtype where isDelete is null or isDelete = ?";
		return dao.findByHql(hql, new Object[]{isDelete}, pb);
	}

}