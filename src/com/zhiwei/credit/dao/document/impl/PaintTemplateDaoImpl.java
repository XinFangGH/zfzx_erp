package com.zhiwei.credit.dao.document.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.document.PaintTemplateDao;
import com.zhiwei.credit.model.document.PaintTemplate;

@SuppressWarnings("unchecked")
public class PaintTemplateDaoImpl extends BaseDaoImpl<PaintTemplate> implements
	PaintTemplateDao {

    public PaintTemplateDaoImpl() {
	super(PaintTemplate.class);
    }

    /**
     * 按Key查找模板
     * 
     * @param templateKey
     * @return
     */
    public List<PaintTemplate> getByKey(String templateKey) {
	String hql = "from PaintTemplate pt where pt.templateKey=?";
	return findByHql(hql, new Object[] { templateKey });
    }

    /**
     * 查找某个模板，除去id为eptTemplateId对应的记录
     * 
     * @param templateKey
     * @param eptTemplateId
     * @return
     */
    public List<PaintTemplate> getByKeyExceptId(String templateKey,Long eptTemplateId) {
	String hql = "from PaintTemplate pt where pt.templateKey=? and pt.ptemplateId!=?";
	return findByHql(hql, new Object[] { templateKey, eptTemplateId });
    }

}