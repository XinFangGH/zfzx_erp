package com.zhiwei.credit.service.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.p2p.Operate;
import com.zhiwei.credit.model.p2p.PlatDataPublish;

import java.util.Map;

/**
 * 
 * @author 
 *
 */
public interface PlatDataPublishService extends BaseService<PlatDataPublish>{

    /**
     *平台数据详情信息
     *
     * @auther: XinFang
     * @date: 2018/7/5 13:47
     */
    Operate showAll(String  startDate,String endDate);
}


