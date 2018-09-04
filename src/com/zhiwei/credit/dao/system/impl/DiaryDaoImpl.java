package com.zhiwei.credit.dao.system.impl;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.system.DiaryDao;
import com.zhiwei.credit.model.system.Diary;

/**
 * @description 日志管理
 * @class DiaryDaoImpl
 * @author YHZ
 * @company www.credit-software.com
 * @data 2010-12-27AM
 * 
 */
@SuppressWarnings("unchecked")
public class DiaryDaoImpl extends BaseDaoImpl<Diary> implements DiaryDao {

	public DiaryDaoImpl() {
		super(Diary.class);
	}

	@Override
	public List<Diary> getSubDiary(String userIds, PagingBean pb) {
		String hql = "from Diary vo where vo.appUser.userId in (" + userIds
				+ ") and vo.diaryType=1";
		return findByHql(hql, null, pb);
	}

}