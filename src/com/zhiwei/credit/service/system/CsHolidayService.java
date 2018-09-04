package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.CsHoliday;

/**
 * 
 * @author 
 *
 */
public interface CsHolidayService extends BaseService<CsHoliday>{
	/**
	 * 通过日期判断是否重复
	 * @param d
	 * @return
	 */
	public List<CsHoliday> checkRepeatByDate(Date d);
	
}


