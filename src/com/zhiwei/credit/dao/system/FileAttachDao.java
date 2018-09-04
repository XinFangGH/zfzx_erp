package com.zhiwei.credit.dao.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.FileAttach;

/**
 * @description 附件分类管理
 * @author YHZ
 * @company www.credit-software.com
 * @datatime 2010-11-20PM
 * 
 */
public interface FileAttachDao extends BaseDao<FileAttach> {

	void removeByPath(String filePath);

	/**
	 * 按文件路径取得路径
	 */
	FileAttach getByPath(String filePath);

	/**
	 * 分页查询图片
	 * 参数[pb:PagingBean,filePath:fileType搜索条件,bo:boolean{true:file文件,false:
	 * image图片}]
	 */
	List<FileAttach> fileList(PagingBean pb, String fileType, boolean bo);

	/**
	 * @description 根据fileType查询所有满足条件的数据
	 * @param fileType
	 *            fileType搜索条件
	 * @return List<FileAttach>
	 */
	List<FileAttach> fileList(String fileType);
	
	/**
	 * 根据csFileId删除一天记录
	 */
	void removeByFileId(int csFileId);
	
	public List<FileAttach> listByContractId(Integer csContractId);
	
	public FileAttach getFileAttachByCsFileId(Integer csFileId);
}