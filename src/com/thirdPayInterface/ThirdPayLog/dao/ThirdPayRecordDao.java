package com.thirdPayInterface.ThirdPayLog.dao;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.zhiwei.core.dao.BaseDao;

/**
 * 
 * @author 
 *
 */
public interface ThirdPayRecordDao extends BaseDao<ThirdPayRecord>{
	public ThirdPayRecord getByUniqueId(String uniqueId);
	public ThirdPayRecord getByOrderNo(String orderNo);//根据流水号找到第三方日志信息
	/**
     * 根据第三方标识查询用户名，id
     * @param thirdPayConfigId
     * @return
     */
    public List getBpCustmember(String thirdPayConfigId);
    /**
     * 根据第三方标识与业务类型查询
     * @param thirdPayConfigId
     * @param interfaceType
     * @return
     */
    public List<ThirdPayRecord> getByIdAndType(String thirdPayConfigId,String interfaceType);
}