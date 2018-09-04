package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.Diary;

/**
 * 
 * @author 
 *
 */
public interface DiaryDao extends BaseDao<Diary>{
	//public List<Diary> getAllBySn(PagingBean pb);
	/**
	 * 查找所有下属的工作日志
	 */
	public List<Diary> getSubDiary(String userIds,PagingBean pb);
}