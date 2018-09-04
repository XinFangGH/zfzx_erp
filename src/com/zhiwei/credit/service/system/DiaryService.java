package com.zhiwei.credit.service.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.Diary;

/**
 * @description 日志管理
 * @class DiaryService
 * @updater YHZ
 * @company www.credit-software.com
 * @data 2010-12-27AM
 * 
 */
public interface DiaryService extends BaseService<Diary> {

	public List<Diary> getAllBySn(PagingBean pb);

	/**
	 * 查找下属的所有工作日志
	 * 
	 * @param userIds
	 * @param pb
	 * @return
	 */
	public List<Diary> getSubDiary(String userIds, PagingBean pb);

}
