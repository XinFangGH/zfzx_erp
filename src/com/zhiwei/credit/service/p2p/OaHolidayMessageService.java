package com.zhiwei.credit.service.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.p2p.OaHolidayMessage;

import java.util.List;

/**
 * 
 * @author 
 *
 */
public interface OaHolidayMessageService extends BaseService<OaHolidayMessage>{

    /**
     * 根据类型查找
     * @param type
     * @return
             */
    List<OaHolidayMessage> getMessageByType(Integer type);


    /**
     * 定时任务，查询并且发送短信
     */
    public void messageSending();

    /**
     * 定时任务 ,查询当天过生日的用户并发送短信
     */
    public void birthdayMessageSending();

}


