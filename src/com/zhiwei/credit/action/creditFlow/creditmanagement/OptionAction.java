package com.zhiwei.credit.action.creditFlow.creditmanagement;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.OptionDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Options;

public class OptionAction extends BaseAction {
	private int id;
	private Options options;
	@Resource
	private OptionDao optionDao;
	
	private int indicatorId;
	
	public void list1() {
		List list = optionDao.getOptionListByIndicatorId(indicatorId);
		try {
			JsonUtil.jsonFromList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addOption() {
		boolean b = optionDao.addOption(options);
		
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
			JsonUtil.responseJsonString(msg);
	}
	
	public void updateOption() {
		boolean b = optionDao.updateOption(options);
		
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
			JsonUtil.responseJsonString(msg);
	}
	
	public void deleteRs() {
		boolean b = optionDao.deleteOption(id);
		
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
			JsonUtil.responseJsonString(msg);
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Options getOptions() {
		return options;
	}
	
	public void setOptions(Options options) {
		this.options = options;
	}
	
	public OptionDao getOptionDao() {
		return optionDao;
	}
	
	public void setOptionDao(OptionDao optionDao) {
		this.optionDao = optionDao;
	}

	public int getIndicatorId() {
		return indicatorId;
	}

	public void setIndicatorId(int indicatorId) {
		this.indicatorId = indicatorId;
	}
	
	
}
