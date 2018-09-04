package com.zhiwei.credit.service.document.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.document.PaintTemplateDao;
import com.zhiwei.credit.model.document.PaintTemplate;
import com.zhiwei.credit.service.document.PaintTemplateService;

public class PaintTemplateServiceImpl extends BaseServiceImpl<PaintTemplate>
	implements PaintTemplateService {
    @SuppressWarnings("unused")
    private PaintTemplateDao dao;

    public PaintTemplateServiceImpl(PaintTemplateDao dao) {
	super(dao);
	this.dao = dao;
    }

    /**
     * 查找某个模板，除去id为eptTemplateId对应的记录
     * 
     * @param templateKey
     * @param eptTemplateId
     * @return
     */
    public List<PaintTemplate> getByKeyExceptId(String templateKey,
	    Long eptTemplateId) {
	return dao.getByKeyExceptId(templateKey, eptTemplateId);
    }

    /**
     * 按Key查找模板
     * 
     * @param templateKey
     * @return
     */
    public List<PaintTemplate> getByKey(String templateKey) {
	return dao.getByKey(templateKey);
    }

    /**
     * 该Key是否在存
     * 
     * @param templateKey
     * @return
     */
    public boolean isKeyExist(String templateKey) {
	if (getByKey(templateKey).size() > 0) {
	    return true;
	}
	return false;
    }

}