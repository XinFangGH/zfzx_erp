package com.zhiwei.credit.service.web.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.web.WebTemplateMessageDao;
import com.zhiwei.credit.model.web.WebTemplateMessage;
import com.zhiwei.credit.service.web.WebTemplateMessageService;

/**
 * 
 * @author 
 *
 */
public class WebTemplateMessageServiceImpl extends BaseServiceImpl<WebTemplateMessage> implements WebTemplateMessageService{
	@SuppressWarnings("unused")
	private WebTemplateMessageDao dao;
	
	public WebTemplateMessageServiceImpl(WebTemplateMessageDao dao) {
		super(dao);
		this.dao=dao;
	}

}