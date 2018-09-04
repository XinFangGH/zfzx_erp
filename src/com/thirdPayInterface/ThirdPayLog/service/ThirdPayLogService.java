package com.thirdPayInterface.ThirdPayLog.service;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.thirdPayInterface.ThirdPayLog.model.ThirdPayLog;
import com.zhiwei.core.service.BaseService;

/**
 * 
 * @author 
 *
 */
public interface ThirdPayLogService extends BaseService<ThirdPayLog>{
    public ThirdPayLog getByOrderNo(String recordNum);
    /**
     * 根据唯一标识查询该条记录
     * @param uniqueId
     * @return
     */
    public ThirdPayLog getByUniqueId(String uniqueId);
}


