package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.system.DiaryDao;
import com.zhiwei.credit.model.system.Diary;
import com.zhiwei.credit.service.system.DiaryService;

public class DiaryServiceImpl extends BaseServiceImpl<Diary> implements DiaryService{
	private DiaryDao dao;
	
	public DiaryServiceImpl(DiaryDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<Diary> getAllBySn(PagingBean pb) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Diary> getSubDiary(String userIds, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getSubDiary(userIds, pb);
	}

}