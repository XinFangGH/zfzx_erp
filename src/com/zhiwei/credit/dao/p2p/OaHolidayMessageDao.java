package com.zhiwei.credit.dao.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.p2p.OaHolidayMessage;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author 
 *
 */
public interface OaHolidayMessageDao extends BaseDao<OaHolidayMessage>{

    /**
     * 根据类型查找
     * @param type
     * @return
     */
    List<OaHolidayMessage> getMessageByType(Integer type);

    /**
     * 根据节日日期查询，如果date=null,则查询当天的
     * @param date
     * @param type
     * @return
     */
    List<OaHolidayMessage> getMessageByDate(Date date,Integer type);
}