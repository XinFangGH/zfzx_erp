package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.CsHolidayDao;
import com.zhiwei.credit.model.system.CsHoliday;
import com.zhiwei.credit.service.system.CsHolidayService;

/**
 * 
 * @author 
 *
 */
public class CsHolidayServiceImpl extends BaseServiceImpl<CsHoliday> implements CsHolidayService{
	@SuppressWarnings("unused")
	private CsHolidayDao dao;
	
	public CsHolidayServiceImpl(CsHolidayDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<CsHoliday> checkRepeatByDate(Date d) {
		
		return dao.checkRepeatByDate(d);
	}

}