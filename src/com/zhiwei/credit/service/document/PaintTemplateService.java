package com.zhiwei.credit.service.document;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.document.PaintTemplate;

public interface PaintTemplateService extends BaseService<PaintTemplate> {
    /**
     * 按Key查找模板
     * 
     * @param templateKey
     * @return
     */
    public List<PaintTemplate> getByKey(String templateKey);

    /**
     * 该Key是否在存
     * 
     * @param templateKey
     * @return
     */
    public boolean isKeyExist(String templateKey);

    /**
     * 查找某个模板，除去id为eptTemplateId对应的记录
     * 
     * @param templateKey
     * @param eptTemplateId
     * @return
     */
    public List<PaintTemplate> getByKeyExceptId(String templateKey,
	    Long eptTemplateId);
}
