package com.zhiwei.credit.action.bpm;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.system.GlobalTypeService;

/**
 * @description 在线流程设计
 * @class BpmDesignAction
 * @extends BaseAction
 * @author YHZ
 * @company www.credit-software.com
 * @createtime w2011-5-4AM
 * 
 */
public class BpmDesignAction extends BaseAction {

	@Resource
	private ProDefinitionService proDefinitionService;

	@Resource
	private GlobalTypeService globalTypeService;

	private Long defId; // defId流程定义Id

	private String name;

	public Long getDefId() {
		return defId;
	}

	public void setDefId(Long defId) {
		this.defId = defId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String execute() throws Exception {
		HttpServletRequest request  = getRequest();
		String defId=request.getParameter("defId");
		//flowType=normal:发布标准版本流程;flowType=分公司Id:为分公司指派流程;flowType=currentCompany:为当前分公司发布流程(从后台session获取对应companyId)
		//flowType=isEditFlow:表示编辑流程 add by lu 2012.11.23
		String flowType=request.getParameter("flowType");
		if(StringUtils.isNotEmpty(defId)){
			ProDefinition proDefintion=proDefinitionService.get(new Long(defId));
			request.setAttribute("proDefinition", proDefintion);
			request.setAttribute("title", proDefintion.getName());
		}else{
			request.setAttribute("title", "未命名");
		}
		Long uId = ContextUtil.getCurrentUserId();
		List<GlobalType> record = new ArrayList<GlobalType>();
		String catKey = "FLOW"; // 流程模块
		//AppUser appUser = appUserSerivce.get(Long.valueOf(uId));
		record = globalTypeService.getByCatKeyUserId(ContextUtil.getCurrentUser(), catKey);

		request.setAttribute("record", record);
		request.setAttribute("uId", uId);
		request.setAttribute("flowType", flowType);
		
		return SUCCESS;
	}

}
