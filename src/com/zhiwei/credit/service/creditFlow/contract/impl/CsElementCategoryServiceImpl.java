package com.zhiwei.credit.service.creditFlow.contract.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.contract.CsElementCategoryDao;

import com.zhiwei.credit.model.creditFlow.contract.CsElementCategory;

import com.zhiwei.credit.service.creditFlow.contract.CsElementCategoryService;


/**
 * 
 * @author 
 *
 */

public class CsElementCategoryServiceImpl extends BaseServiceImpl<CsElementCategory> implements CsElementCategoryService {
    private CsElementCategoryDao dao;
   
	public CsElementCategoryServiceImpl(CsElementCategoryDao dao) {
		super(dao);
		this.dao=dao;
		// TODO Auto-generated constructor stub
	}
}