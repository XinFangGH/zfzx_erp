package com.thirdPayInterface.ThirdPayLog.service;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.zhiwei.core.service.BaseService;

/**
 * 
 * @author 
 *
 */
public interface ThirdPayRecordService extends BaseService<ThirdPayRecord>{
	//插入日志字段
	public void insertOrUpdateLog(CommonRequst request,CommonResponse response);
    public ThirdPayRecord getByOrderNo(String recordNum);
    /**
     * 根据唯一标识查询该条记录
     * @param uniqueId
     * @return
     */
    public ThirdPayRecord getByUniqueId(String uniqueId);
    public void insertInfo(CommonRequst request, CommonResponse response);
    public void handleByBussinessType(CommonResponse response);
    /**
     * 根据第三方标识查询用户名，id
     * @param thirdPayConfigId
     * @return
     */
    public List getBpCustmember(String thirdPayConfigId);
    /**
     * 根据第三方标识与业务类型查询(按降序查询)
     * @param thirdPayConfigId
     * @param interfaceType
     * @return
     */
    public List<ThirdPayRecord> getByIdAndType(String thirdPayConfigId,String interfaceType);
}


