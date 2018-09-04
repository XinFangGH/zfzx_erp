package com.zhiwei.credit.service.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.archive.ArchivesDao;
import com.zhiwei.credit.model.archive.ArchDispatch;
import com.zhiwei.credit.model.archive.Archives;
import com.zhiwei.credit.model.archive.ArchivesDep;
import com.zhiwei.credit.model.archive.ArchivesDoc;
import com.zhiwei.credit.model.archive.DocHistory;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Department;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.archive.ArchDispatchService;
import com.zhiwei.credit.service.archive.ArchivesDepService;
import com.zhiwei.credit.service.archive.ArchivesDocService;
import com.zhiwei.credit.service.archive.ArchivesService;
import com.zhiwei.credit.service.archive.DocHistoryService;
import com.zhiwei.credit.service.system.DepartmentService;
import com.zhiwei.credit.service.system.FileAttachService;
import com.zhiwei.credit.service.system.GlobalTypeService;

public class ArchivesServiceImpl extends BaseServiceImpl<Archives> implements ArchivesService{
	private ArchivesDao dao;
	@Resource
	private GlobalTypeService globalTypeService;
	@Resource 
	private FileAttachService fileAttachService;
	@Resource
	private ArchivesDocService archivesDocService;
	@Resource
	private DocHistoryService docHistoryService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private ArchivesDepService archivesDepService;
	@Resource
	private ArchDispatchService archDispatchService;
	public ArchivesServiceImpl(ArchivesDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<Archives> findByUserOrRole(Long userId, Set<AppRole> roles,PagingBean pb) {
		return dao.findByUserOrRole(userId, roles,pb);
	}

	@Override
	public Integer startArchFlow(FlowRunInfo flowRunInfo) {
		//保存公文信息
		AppUser curUser = ContextUtil.getCurrentUser();
		Archives archives = new Archives();
		try{
			BeanUtil.populateEntity(flowRunInfo.getRequest(), archives, "archives");
		}catch(Exception ex){
			logger.error(ex.getMessage());
			return 0;
		}
		//取得公文正文
		String docs = flowRunInfo.getRequest().getParameter("docs");
		Set archivesDocSet = new HashSet();
		if (StringUtils.isNotEmpty(docs)) {
			Gson gson = new Gson();
			ArchivesDoc[] archivesDocs = gson.fromJson(docs,
					ArchivesDoc[].class);
			if (archivesDocs != null) {
				for (int i = 0; i < archivesDocs.length; i++) {
					if (archivesDocs[i].getDocId() == null
							|| archivesDocs[i].getDocId() == 0) {
						archivesDocs[i].setDocId(null);
						archivesDocs[i].initUsers(curUser);
						archivesDocs[i].setDocStatus(ArchivesDoc.STATUS_MODIFY);
						archivesDocs[i].setUpdatetime(new Date());
						archivesDocs[i].setCreatetime(new Date());
						archivesDocs[i].setFileAttach(fileAttachService.getByPath(archivesDocs[i].getDocPath()));
						archivesDocService.save(archivesDocs[i]);

						// 新增文件同时在历史表增加一历史记录
						DocHistory newHistory = new DocHistory();
						newHistory.setArchivesDoc(archivesDocs[i]);
						newHistory.setFileAttach(archivesDocs[i].getFileAttach());
						newHistory.setDocName(archivesDocs[i].getDocName());
						newHistory.setPath(archivesDocs[i].getDocPath());
						newHistory.setVersion(ArchivesDoc.ORI_VERSION);
						newHistory.setUpdatetime(new Date());
						newHistory.setMender(curUser.getFullname());
						docHistoryService.save(newHistory);
					} else {
						archivesDocs[i] = archivesDocService
								.get(archivesDocs[i].getDocId());
					}
					archivesDocSet.add(archivesDocs[i]);
				}
			}
		}
		// 初始化发文的数据
		archives.setIssuer(curUser.getFullname());
		archives.setIssuerId(curUser.getUserId());
		// 设置发文的分类
		GlobalType archivesType = globalTypeService.get(archives
				.getArchivesType().getProTypeId());
		archives.setArchivesType(archivesType);
		// 发文
		archives.setArchType(Archives.ARCHIVE_TYPE_DISPATCH);
		
		archives.setCreatetime(new Date());
		archives.setIssueDate(new Date());

		// TODO count the files here
		archives.setFileCounts(archivesDocSet.size());
		archives.setArchivesDocs(archivesDocSet);
		
		//设置公文状态
		archives.setStatus(flowRunInfo.getDestName());
		
		dao.save(archives);
		
		//为下一步操作设置ArchivesId
		flowRunInfo.getVariables().put("archivesId", archives.getArchivesId());
		flowRunInfo.setFlowSubject(archives.getSubject());
		return 1;
	}

	/**
	 * 在流程启动后把runId写入公文中
	 */
	@Override
	public Integer setRunId(FlowRunInfo flowRunInfo) {
		Long archivesId = (Long)flowRunInfo.getVariables().get("archivesId");
		if(archivesId == null){
			return 0;
		}
		Archives archives = dao.get(archivesId);
		archives.setRunId(flowRunInfo.getProcessRun().getRunId());
		dao.save(archives);
		return 1;
	}
	/**
	 * 流程任务结束后把status写入公文中
	 */
	@Override
	public Integer saveStatus(FlowRunInfo flowRunInfo) {
		String archivesId = flowRunInfo.getRequest().getParameter("archivesId");
		String status = flowRunInfo.getDestName();
		if(StringUtils.isEmpty(archivesId)){
			return 0;
		}
		Archives archives = dao.get(new Long(archivesId));
		archives.setStatus(status);
		archives.setArchStatus(Archives.END_FLOW_NONE);
		dao.save(archives);
		
		return 1;
	}
	
	/**
	 * 发文流程结束,归档时操作endFlow,执行发文分发及把公文设置为归档状态
	 */
	@Override
	public Integer endFlow(FlowRunInfo flowRunInfo) {
		//取得公文ID
		String archivesId = flowRunInfo.getRequest().getParameter("archivesId");
		String status = flowRunInfo.getDestName();
		if(StringUtils.isEmpty(archivesId)){
			return 0;
		}
		Archives archives = dao.get(new Long(archivesId));
		archives.setStatus(status);
		archives.setArchStatus(Archives.END_FLOW);//归档结束
		dao.save(archives);
		
		//发文分发
		String depIds = archives.getRecDepIds();
		if (StringUtils.isNotEmpty(depIds)) {
			String[] depIdArr = depIds.split("[,]");
			if (depIdArr != null) {
				
				StringBuffer recIds = new StringBuffer("");
				
				for (int i = 0; i < depIdArr.length; i++) {
					Long depId = new Long(depIdArr[i]);
					Department department = departmentService.get(depId);
					
					ArchivesDep archivesDep = new ArchivesDep();
					archivesDep.setSubject(archives.getSubject());
					archivesDep.setDepartment(department);
					archivesDep.setArchives(archives);
					archivesDep.setIsMain(ArchivesDep.RECEIVE_MAIN);
					archivesDep.setStatus(ArchivesDep.STATUS_UNSIGNED);

					archivesDepService.save(archivesDep);
				}
			}
		}
		return 1;
	}

	
	/**
	 * 收文流程启动前执行以下方法
	 */
	@Override
	public Integer startRecFlow(FlowRunInfo flowRunInfo) {
		//取得公文信息
		Archives archives = new Archives();
		try{
			BeanUtil.populateEntity(flowRunInfo.getRequest(), archives, "archives");
		}catch(Exception ex){
			logger.error(ex.getMessage());
			return 0;
		}
		
		String arcRecfileIds = flowRunInfo.getRequest().getParameter("archivesRecfileIds");
		String archDepId = flowRunInfo.getRequest().getParameter("archDepId");
		String handlerUids=flowRunInfo.getRequest().getParameter("signUserIds");
		String recTypeId = flowRunInfo.getRequest().getParameter("recTypeId");
		
		AppUser appUser = ContextUtil.getCurrentUser();
		archives.setArchType(Archives.ARCHIVE_TYPE_RECEIVE);
		archives.setIssuerId(appUser.getUserId());
		archives.setIssuer(appUser.getFullname());
		archives.setHandlerUids(handlerUids);
		archives.setCreatetime(new Date());
		archives.setIssueDate(new Date());
		archives.setStatus(flowRunInfo.getDestName());
		archives.setArchivesRecType(globalTypeService.get(new Long(recTypeId)));
		if(StringUtils.isNotEmpty(arcRecfileIds)){
		   archives.setFileCounts(arcRecfileIds.split(",").length);
		}
		dao.save(archives);
		if (StringUtils.isNotEmpty(arcRecfileIds)) {
			List<ArchivesDoc> list = archivesDocService.findByAid(archives
					.getArchivesId());
			for (ArchivesDoc archivesDoc : list) {
				archivesDocService.remove(archivesDoc);
			}
			String[] fileIds = arcRecfileIds.split(",");
			for (String id : fileIds) {
				FileAttach fileAttach = fileAttachService.get(new Long(id));
				ArchivesDoc archivesDoc = new ArchivesDoc();
				archivesDoc.setArchives(archives);
				archivesDoc.setFileAttach(fileAttach);
				archivesDoc.setDocName(fileAttach.getFileName());
				archivesDoc.setDocStatus((short) 1);
				archivesDoc.setCurVersion(1);
				archivesDoc.setDocPath(fileAttach.getFilePath());
				archivesDoc.setCreatetime(new Date());
				archivesDoc.setUpdatetime(new Date());
				archivesDocService.save(archivesDoc);
			}
		}
		
		//公文签收后在部门签收公文表中作标记
		if(StringUtils.isNotEmpty(archDepId)){
			ArchivesDep archivesDep = archivesDepService.get(new Long(archDepId));
			AppUser curUser=ContextUtil.getCurrentUser();
			archivesDep.setStatus(ArchivesDep.STATUS_SIGNED);
			archivesDep.setSignTime(new Date());
			archivesDep.setSignFullname(curUser.getFullname());
			archivesDep.setSignUserID(curUser.getUserId());
			archivesDepService.save(archivesDep);
		}
		//为下一步操作设置ArchivesId
		flowRunInfo.getVariables().put("archivesId", archives.getArchivesId());
		flowRunInfo.getVariables().put("archDepId", archDepId);
		flowRunInfo.setFlowSubject(archives.getSubject());
		return 1;
	}
	/**
	 * 
	 * 收文流程结束,归档时操作endFlow,执行发文分发及把公文设置为归档状态
	 * @return
	 */
	public Integer endRecFlow (FlowRunInfo flowRunInfo){
			//取得公文ID
			String archivesId = flowRunInfo.getRequest().getParameter("archivesId");
			String status = flowRunInfo.getDestName();
			if(StringUtils.isEmpty(archivesId)){
				return 0;
			}
			Archives archives = dao.get(new Long(archivesId));
			archives.setStatus(status);
			archives.setArchStatus(Archives.END_FLOW);//归档结束
			dao.save(archives);
			
			//填写反馈意见
			String cruArchDepId=flowRunInfo.getRequest().getParameter("archDepId");
			String readFeedback = (String)flowRunInfo.getRequest().getParameter("comments");
		    if(StringUtils.isNotEmpty(cruArchDepId)&&cruArchDepId.indexOf("$")==-1){
		    	ArchivesDep archivesDep= archivesDepService.get(new Long(cruArchDepId));
		    	archivesDep.setHandleFeedback(readFeedback);
				    archivesDepService.save(archivesDep);
		    }
			return 1;
	}
	/**
	 * 
	 * 收文流程分发时记录分发信息
	 * @return
	 */
	public Integer saveDispatch (FlowRunInfo flowRunInfo){
		String archivesId = flowRunInfo.getRequest().getParameter("archivesId");
		String archUserType = flowRunInfo.getRequest().getParameter("archUserType");
		String readFeedback = (String)flowRunInfo.getRequest().getParameter("comments");
		Archives archives = dao.get(new Long(archivesId));
		if (archives != null) {
			ArchDispatch archDispatch = new ArchDispatch();
			AppUser user=ContextUtil.getCurrentUser();
			archDispatch.setArchives(archives);
			archDispatch.setArchUserType(new Short(archUserType));
			archDispatch.setUserId(user.getUserId());
			archDispatch.setFullname(user.getFullname());
			archDispatch.setDispatchTime(new Date());
			archDispatch.setSubject(archives.getSubject());
			archDispatch.setIsRead(ArchDispatch.HAVE_READ);
			archDispatch.setReadFeedback(readFeedback);
			archDispatchService.save(archDispatch);
		}
		return 1;
	}
}