package com.zhiwei.credit.action.archive;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.archive.ArchivesDoc;
import com.zhiwei.credit.model.archive.DocHistory;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.archive.ArchivesDocService;
import com.zhiwei.credit.service.archive.DocHistoryService;
import com.zhiwei.credit.service.system.FileAttachService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class ArchivesDocAction extends BaseAction{
	@Resource
	private ArchivesDocService archivesDocService;
	@Resource
	private DocHistoryService docHistoryService;
	@Resource
	private FileAttachService fileAttachService;
	private ArchivesDoc archivesDoc;
	
	private Long docId;

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public ArchivesDoc getArchivesDoc() {
		return archivesDoc;
	}

	public void setArchivesDoc(ArchivesDoc archivesDoc) {
		this.archivesDoc = archivesDoc;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		String archivesId = getRequest().getParameter("archivesId");
		if(archivesId != null && !"".equals(archivesId)){
			filter.addFilter("Q_archives.archivesId_L_EQ", archivesId);
		}
		List<ArchivesDoc> list= archivesDocService.getAll(filter);
		
		Type type=new TypeToken<List<ArchivesDoc>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				if(StringUtils.isNotEmpty(id) && !"0".equals(id)){
					archivesDocService.remove(new Long(id));
				}
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		ArchivesDoc archivesDoc=archivesDocService.get(docId);
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(archivesDoc));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
 		AppUser curUser = ContextUtil.getCurrentUser();
		if(archivesDoc.getDocId() == null || archivesDoc.getDocId().longValue()==0){
			//新增版本
			archivesDoc.initUsers(curUser);
			archivesDoc.setDocStatus(ArchivesDoc.STATUS_MODIFY);
			archivesDoc.setUpdatetime(new Date());
			archivesDoc.setCreatetime(new Date());
			archivesDoc.setCurVersion(ArchivesDoc.ORI_VERSION);
			archivesDoc.setFileAttach(fileAttachService.getByPath(archivesDoc.getDocPath()));
			archivesDocService.save(archivesDoc);
		}else{
			//修改版本
			ArchivesDoc oldVersion = archivesDocService.get(archivesDoc.getDocId());
			archivesDoc.setCreatetime(oldVersion.getCreatetime());
			archivesDoc.setArchives(oldVersion.getArchives());
			archivesDoc.setCreatorId(oldVersion.getCreatorId());
			archivesDoc.setFileAttach(fileAttachService.getByPath(archivesDoc.getDocPath()));
			archivesDoc.setCreator(oldVersion.getCreator());
			archivesDoc.setDocStatus(ArchivesDoc.STATUS_MODIFY);
			archivesDoc.setUpdatetime(new Date());
			archivesDoc.setCurVersion(oldVersion.getCurVersion()+1);
			archivesDoc.setMender(curUser.getFullname());
			archivesDoc.setMenderId(curUser.getUserId());
			archivesDoc.setDocHistorys(oldVersion.getDocHistorys());
			archivesDoc.setFileAttach(fileAttachService.getByPath(archivesDoc.getDocPath()));
			archivesDocService.merge(archivesDoc);
			
		}
		//历史版本
		DocHistory docHistory = new DocHistory();
		docHistory.setArchivesDoc(archivesDoc);
		docHistory.setFileAttach(fileAttachService.getByPath(archivesDoc.getDocPath()));
		docHistory.setDocName(archivesDoc.getDocName());
		docHistory.setPath(archivesDoc.getDocPath());
		docHistory.setVersion(archivesDoc.getCurVersion());
		docHistory.setUpdatetime(new Date());
		docHistory.setMender(curUser.getFullname());
		docHistoryService.save(docHistory);
		
		//保存成功后返回版本JSON数据
//		Archives archives = archivesDoc.getArchives();
//		Set<ArchivesDoc> docSet = archives.getArchivesDocs();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		//Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[]{"class","docHistorys"}).serialize(archivesDoc));
//		buff.append(gson.toJson(docSet));
		buff.append("}");
		
		setJsonString(buff.toString());
		return SUCCESS;
	}
	
	public String copy(){
		String historyId = getRequest().getParameter("historyId");
		DocHistory docHistory = docHistoryService.get(new Long(historyId));
		DocHistory newHistory = new DocHistory();

		archivesDoc = docHistory.getArchivesDoc();

		newHistory.setDocName(docHistory.getDocName());
		newHistory.setFileAttach(docHistory.getFileAttach());
		//newHistory.setFileId(docHistory.getFileId());
		newHistory.setMender(ContextUtil.getCurrentUser().getFullname());
		newHistory.setPath(docHistory.getPath());
		newHistory.setUpdatetime(new Date());
		newHistory.setVersion(archivesDoc.getCurVersion()+1);
		newHistory.setArchivesDoc(archivesDoc);
		docHistoryService.save(newHistory);

		archivesDoc.setCurVersion(newHistory.getVersion());
		archivesDoc.setDocPath(newHistory.getPath());
		archivesDoc.setFileAttach(newHistory.getFileAttach());
		
		archivesDocService.save(archivesDoc);
		
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[]{"class","docHistorys"}).serialize(archivesDoc));
		buff.append("}");
		
		setJsonString(buff.toString());
		return SUCCESS;
	}
}
