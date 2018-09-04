package com.zhiwei.credit.dao.web.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.web.WebTemplateMessageDao;
import com.zhiwei.credit.model.web.WebTemplateMessage;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class WebTemplateMessageDaoImpl extends BaseDaoImpl<WebTemplateMessage> implements WebTemplateMessageDao{

	public WebTemplateMessageDaoImpl() {
		super(WebTemplateMessage.class);
	}

}