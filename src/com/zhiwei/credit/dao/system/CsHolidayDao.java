package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.CsHoliday;

/**
 * 
 * @author 
 *
 */
public interface CsHolidayDao extends BaseDao<CsHoliday>{
	public List<CsHoliday> checkRepeatByDate(Date d);

	/**
	 * 查询最大顺延日
	 * @param intentDate
	 */
	public CsHoliday getMaxDeferDay(Date intentDate);
	
}