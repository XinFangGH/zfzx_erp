package com.zhiwei.credit.action.mobile.flow;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.service.flow.ProDefinitionService;

public class MobileProDefAction extends BaseAction{
	@Resource
	private ProDefinitionService proDefinitionService;
	
	public String list(){
		List<ProDefinition> proDefList=proDefinitionService.getAll(getInitPagingBean());
		getRequest().setAttribute("proDefList", proDefList);
		return SUCCESS;
	}
}
