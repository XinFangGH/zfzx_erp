package com.zhiwei.credit.action.admin;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.ConfSummary;
import com.zhiwei.credit.service.admin.ConfSummaryService;

/**
 * @description 会议纪要详细信息展示
 * @author YHZ
 * @data 2010-11-1 PM
 * @company www.hurong-jee.org
 * 
 */
public class ConfSummaryDetailAction extends BaseAction {

	@Resource
	private ConfSummaryService confSummaryService;

	private Long sumId; // 会议纪要编号
	private ConfSummary confSummary;

	public Long getSumId() {
		return sumId;
	}

	public void setSumId(Long sumId) {
		this.sumId = sumId;
	}

	public ConfSummary getConfSummary() {
		return confSummary;
	}

	public void setConfSummary(ConfSummary confSummary) {
		this.confSummary = confSummary;
	}

	@Override
	public String execute() throws Exception {
		confSummary = confSummaryService.get(sumId);
		return SUCCESS;
	}
}
