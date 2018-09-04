package com.zhiwei.credit.action.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.system.FileAttachService;
import com.zhiwei.credit.util.HibernateProxyTypeAdapter;

/**
 * @description 附件管理
 * @class FileAttachAction
 * @author csx,yhz
 * @company www.credit-software.com
 * @createtime 2011-1-24PM
 * 
 */
public class FileAttachAction extends BaseAction {
	@Resource
	private FileAttachService fileAttachService;
	private FileAttach fileAttach;

	private Long fileId;

	private String filePath;

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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 查询我上传附件信息
	 */
	public String list() {
		int start = new QueryFilter(getRequest()).getPagingBean().getStart();
		PagingBean pb = new PagingBean(start, 20);
		String imageOrOthersFile = getRequest().getParameter("type");
		boolean bo = true;// 默认file
		if (imageOrOthersFile != null
				&& imageOrOthersFile.toLowerCase().equals("image")) {
			bo = false; // 图片
			pb = new PagingBean(start, 16);
		}
		String fileType = getRequest().getParameter("fileType");
		List<FileAttach> list = fileAttachService.fileList(pb, fileType, bo);
		return listToJson(list, pb);
	};

	public String listAll() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addSorted("fileType", "DESC");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<FileAttach> list = fileAttachService.getAll(filter);
		// fileId,type,fileName,ext,note,filePath,createtime,totalBytes
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:[");
		for (FileAttach fa : list) {
			buff.append("{");
			buff.append("fileId:" + fa.getFileId())
			.append(",fileName:'" + fa.getFileName() + "'")
			.append(",fileType:'" + fa.getFileType() + "'")
			.append(",ext:'" + fa.getExt() + "'")
			.append(",note:'" + fa.getNote() + "'")
			.append(",filePath:'" + fa.getFilePath() + "'")
			.append(",creator:'" + fa.getCreator() + "'")
			.append(",delFlag:" + fa.getDelFlag())
			.append(",createtime:'" + sdf.format(fa.getCreatetime()) + "'")
			.append(",totalBytes:" + fa.getTotalBytes());
			if(fa.getGlobalType() != null){
				buff.append(",fileTypeName:'" + fa.getGlobalType().getTypeName() + "'");
			} else {
				buff.append(",fileTypeName:'null'");
			}
			buff.append("},");
		}
		if(list != null && list.size()> 0 ){
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 批量删除
	 */
	public String multiDel() {
		String ids = getRequest().getParameter("ids");
		if(!ids.equals("undefined")){
			if (StringUtils.isNotEmpty(ids)) {
				for (String id : ids.split(",")) {
					fileAttachService.remove(new Long(id));
				}
			}
			String isFlex = getRequest().getParameter("isFlex");
			if (StringUtils.isNotEmpty(isFlex)) {
				jsonString = "{\"success\":\"true\"}";
			} else {
				jsonString = "{success:true}";
			}
		}
		return SUCCESS;
	}

	/**
	 * 在flex中删除附件信息
	 */
	public String flexDel() {
		String ids = getRequest().getParameter("ids");
		if (ids != null) {
			for (String id : ids.split(",")) {
				fileAttachService.remove(new Long(id));
			}
		}
		jsonString = "{\"success\":\"true\"}";
		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 */
	public String get() {
		FileAttach fileAttach = fileAttachService.get(fileId);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(fileAttach));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		fileAttachService.save(fileAttach);
		setJsonString("{success:true}");
		return SUCCESS;
	}

	/**
	 * 根据附件的路径删除附件,用于重复上传图片,或删除图片,已用于员工管理模块
	 */
	public String delete() {
		//fileAttachService.removeByPath(filePath);
		return SUCCESS;
	}
	/**
	 * 根据附件的Id删除附件
	 * @return
	 */
	public String deleteFileId(){
		String photoId = this.getRequest().getParameter("photoId");
		AppUser user = ContextUtil.getCurrentUser();
		StringBuffer msg = new StringBuffer("{\"msg\":'");
		if(user!=null&&photoId!=null&&!photoId.equals("")){
			FileAttach fileattach = fileAttachService.get(Long.valueOf(photoId));
			if(fileattach!=null){
				boolean deletePath = false;
				if(user.getId().equals("1")){//超级管理员
					deletePath = true;
				}else{//其他用户删除附件验证是否是本人上传的附件
					if(user.getId().equals(fileattach.getCreatorId().toString())){
						deletePath = true;
					}
				}
				if(deletePath){
					fileAttachService.removeByPath(fileattach.getFilePath());
					 msg.append("删除成功!'");
					 msg.append(",\"success\":true}");
					 setJsonString(msg.toString());
				}else{
					 msg.append("删除失败!'");
					 msg.append(",\"success\":false}");
					 setJsonString(msg.toString());
				}
			}else{
				 msg.append("删除失败!'");
				 msg.append(",\"success\":false}");
				 setJsonString(msg.toString());
			}
		}else{
			 msg.append("删除失败!'");
			 msg.append(",\"success\":false}");
			 setJsonString(msg.toString());
		}
		return SUCCESS;
	}

	/**
	 * 根据IDS取得附件
	 */
	public String loadByIds() {
		List<FileAttach> list = new ArrayList<FileAttach>();
		String ids = getRequest().getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			String[] idsArr = ids.split(",");
			for (String fileId : idsArr) {
				if (StringUtils.isNotEmpty(fileId)) {
					FileAttach fileAttach = fileAttachService.get(new Long(
							fileId));
					list.add(fileAttach);
				}
			}
		}
		Gson gson = new Gson();
		StringBuffer results = new StringBuffer("{success :true,fileAttachs:");
		results.append(gson.toJson(list));
		results.append("}");
		setJsonString(results.toString());
		return SUCCESS;
	}

	/**
	 * @description 将List集合中的数据转化为JSON格式
	 * @param pb
	 *            PagingBean
	 */
	private String listToJson(List<FileAttach> list, PagingBean pb) {
		Type type = new TypeToken<List<FileAttach>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(pb.getTotalItems()).append(",result:");
		Gson gson = new GsonBuilder().registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY).setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		//.registerTypeAdapterFactory()HibernateProxy
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
}
