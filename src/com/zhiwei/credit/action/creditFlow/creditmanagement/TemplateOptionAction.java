package com.zhiwei.credit.action.creditFlow.creditmanagement;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.TemplateOptionDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions;

public class TemplateOptionAction extends BaseAction {
	private int id;
	private int templateIndicatorId;
	private TemplateOptions templateOption;
	@Resource
	private TemplateOptionDao templateOptionDao;
	
	public void tpList() {
		List list = templateOptionDao.getOptionListByIndicatorId(templateIndicatorId);
		try {
			JsonUtil.jsonFromList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add() {
		boolean b = templateOptionDao.addTemplateOption(templateOption);
		
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
			JsonUtil.responseJsonString(msg);
	}
	
	public void update() {
		boolean b = templateOptionDao.updateTemplateOption(templateOption);
		
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
			JsonUtil.responseJsonString(msg);
	}
	
	public void deleteRs() {
		boolean b = templateOptionDao.deleteTemplateOption(id);
		
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
			JsonUtil.responseJsonString(msg);
	}
	
	public int getTemplateIndicatorId() {
		return templateIndicatorId;
	}

	public void setTemplateIndicatorId(int templateIndicatorId) {
		this.templateIndicatorId = templateIndicatorId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TemplateOptions getTemplateOption() {
		return templateOption;
	}

	public void setTemplateOption(TemplateOptions templateOption) {
		this.templateOption = templateOption;
	}

	public TemplateOptionDao getTemplateOptionDao() {
		return templateOptionDao;
	}

	public void setTemplateOptionDao(TemplateOptionDao templateOptionDao) {
		this.templateOptionDao = templateOptionDao;
	}
	
	
}
