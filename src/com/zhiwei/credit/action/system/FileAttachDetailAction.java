package com.zhiwei.credit.action.system;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.system.FileAttachService;

/**
 * @description 图片详细信息展示
 * @author : YHZ
 * @company : www.credit-software.com
 * @datetime 2010-11-23AM
 */
public class FileAttachDetailAction extends BaseAction {

	@Resource
	private FileAttachService fileAttachService;

	private Long fileId;
	private FileAttach fileAttach;

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
		fileAttach = fileAttachService.get(fileId);
		return SUCCESS;
	}
}
