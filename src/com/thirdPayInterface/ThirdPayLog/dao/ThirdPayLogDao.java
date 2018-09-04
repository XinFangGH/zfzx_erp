package com.thirdPayInterface.ThirdPayLog.dao;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.thirdPayInterface.ThirdPayLog.model.ThirdPayLog;
import com.zhiwei.core.dao.BaseDao;

/**
 * 
 * @author 
 *
 */
public interface ThirdPayLogDao extends BaseDao<ThirdPayLog>{
	public ThirdPayLog getByUniqueId(String uniqueId);
	public ThirdPayLog getByOrderNo(String orderNo);//根据流水号找到第三方日志信息
	
}