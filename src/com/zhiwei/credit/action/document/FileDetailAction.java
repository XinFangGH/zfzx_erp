package com.zhiwei.credit.action.document;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.system.FileAttachService;

public class FileDetailAction extends BaseAction{
	@Resource
	private FileAttachService fileAttachService;
	
	private FileAttach fileAttach;
	private Long fileId;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public FileAttach getFileAttach() {
		return fileAttach;
	}

	public void setFileAttach(FileAttach fileAttach) {
		this.fileAttach = fileAttach;
	}

	@Override
	public String execute() throws Exception {
		fileAttach=fileAttachService.get(fileId);
		return SUCCESS;
	}
}
