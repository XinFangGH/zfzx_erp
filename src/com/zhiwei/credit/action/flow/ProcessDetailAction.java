package com.zhiwei.credit.action.flow;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.service.flow.ProDefinitionService;
/**
 * 流程明细
 * @author csx
 *
 */
public class ProcessDetailAction extends BaseAction {
	@Resource
	private ProDefinitionService proDefinitionService;
	
	private ProDefinition proDefinition;

	public ProDefinition getProDefinition() {
		return proDefinition;
	}

	public void setProDefinition(ProDefinition proDefinition) {
		this.proDefinition = proDefinition;
	}

	@Override
	public String execute() throws Exception {
		String defId=getRequest().getParameter("defId");
		proDefinition=proDefinitionService.get(new Long(defId));
		return SUCCESS;
	}
}
