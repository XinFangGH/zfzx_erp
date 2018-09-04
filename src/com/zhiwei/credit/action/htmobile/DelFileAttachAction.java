package com.zhiwei.credit.action.htmobile;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.action.htmobile.util.RequestUtil;
import com.zhiwei.credit.service.system.FileAttachService;


public class DelFileAttachAction extends BaseAction {

	@Resource
	private FileAttachService fileAttachService;
	// 删除附件
	public String del() {
		String fileId = RequestUtil.getString(getRequest(), "fileId");// 要删除的附件类型id
		fileAttachService.removeByPath(fileAttachService.get(Long.parseLong(fileId)).getFilePath());
		fileAttachService.remove(Long.parseLong(fileId));
		return SUCCESS;
	}

}
