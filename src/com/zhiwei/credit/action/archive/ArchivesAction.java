package com.zhiwei.credit.action.archive;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.task.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.Constants;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.archive.ArchHasten;
import com.zhiwei.credit.model.archive.Archives;
import com.zhiwei.credit.model.archive.ArchivesDep;
import com.zhiwei.credit.model.archive.ArchivesDoc;
import com.zhiwei.credit.model.archive.DocHistory;
import com.zhiwei.credit.model.flow.ProcessRun;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Department;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.archive.ArchHastenService;
import com.zhiwei.credit.service.archive.ArchivesDepService;
import com.zhiwei.credit.service.archive.ArchivesDocService;
import com.zhiwei.credit.service.archive.ArchivesService;
import com.zhiwei.credit.service.archive.DocHistoryService;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProcessRunService;
import com.zhiwei.credit.service.flow.TaskService;
import com.zhiwei.credit.service.info.ShortMessageService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DepartmentService;
import com.zhiwei.credit.service.system.FileAttachService;
import com.zhiwei.credit.service.system.GlobalTypeService;

import flexjson.JSONSerializer;

/**
 * 
 * @author
 * 
 */
public class ArchivesAction extends BaseAction {
	@Resource
	private ArchivesService archivesService;
	@Resource
	private ArchivesDocService archivesDocService;
	@Resource
	private GlobalTypeService globalTypeService;
//	private ArchivesTypeService archivesTypeService;//公文类型改为总分类
	@Resource
	private DepartmentService departmentService;
	@Resource
	private ArchivesDepService archivesDepService;
	@Resource
	private 	JbpmService 	jbpmService;
	@Resource
	private ProcessRunService processRunService;

	private Archives archives;

	@Resource
	private AppUserService appUserService;
	@Resource
	private FileAttachService fileAttachService;
	@Resource
	private DocHistoryService docHistoryService;
//	@Resource
//	private ArchRecUserService archRecUserService;
	@Resource
	private TaskService taskservice;
	@Resource
	private ShortMessageService messageService;
	@Resource
	private ArchHastenService archHastenService;
//	@Resource
//	private ArchFlowConfService archFlowConfService;
	
	private Long archivesId;

	public Long getArchivesId() {
		return archivesId;
	}

	public void setArchivesId(Long archivesId) {
		this.archivesId = archivesId;
	}

	public Archives getArchives() {
		return archives;
	}

	public void setArchives(Archives archives) {
		this.archives = archives;
	}

	/**
	 * 显示列表
	 */
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		List<Archives> list = archivesService.getAll(filter);

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONSerializer json=new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(filter.getPagingBean().getTotalItems()).append(",result:[");
		for(Archives archives:list){
			buff.append("{archivesId:").append(archives.getArchivesId());
			buff.append(",archivesType:").append(json.serialize(archives.getArchivesType()));
			buff.append(",archivesRecType:").append(json.serialize(archives.getArchivesRecType()));
			buff.append(",archivesNo:").append(gson.toJson(archives.getArchivesNo()));
			buff.append(",issueDep:").append(gson.toJson(archives.getIssueDep()));
			buff.append(",subject:").append(gson.toJson(archives.getSubject()));
			buff.append(",issueDate:'").append(sdf.format(archives.getIssueDate())).append("'");
			buff.append(",status:").append(gson.toJson(archives.getStatus()));
			buff.append(",fileCounts:").append(archives.getFileCounts());
			buff.append(",privacyLevel:").append(gson.toJson(archives.getPrivacyLevel()));
			buff.append(",urgentLevel:").append(gson.toJson(archives.getUrgentLevel()));
			buff.append(",issuer:").append(gson.toJson(archives.getIssuer()));
			buff.append(",keywords:").append(gson.toJson(archives.getKeywords()));
			buff.append(",sources:").append(gson.toJson(archives.getSources()));
			buff.append(",archType:").append(archives.getArchType());
			buff.append(",createtime:'").append(sdf.format(archives.getCreatetime())).append("'");
			buff.append(",runId:").append(archives.getRunId());
			buff.append(",archStatus:").append(archives.getArchStatus());
			if(archives.getRunId() != null){
				ProcessRun processRun=processRunService.get(archives.getRunId());
				if(processRun != null && StringUtils.isNotEmpty(processRun.getPiId())){
					buff.append(",handlerStatus:0");
				} else {
					buff.append(",handlerStatus:1");
				}
			}	
			buff.append("},");
		}
		if(list.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]}");
		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 根据taskId[任务id]查询该任务执行人信息
	 * @return
	 */
	public String getHanlderUsers(){
		String runIdStr = getRequest().getParameter("runId");
		String taskStatus = getRequest().getParameter("taskStatus");
		String sb = "{success:true,";
		StringBuffer buff = new StringBuffer("");
		if(StringUtils.isNotEmpty(runIdStr)){
			ProcessRun processRun=processRunService.get(new Long(runIdStr));
			if(processRun != null && processRun.getPiId() != null){
				List<Task> curTasks=jbpmService.getTasksByPiId(processRun.getPiId());
				for(Task task : curTasks){
					Long assigneer = null;
					if(task.getAssignee() != null){
						AppUser user = appUserService.get(new Long(task.getAssignee()));
						if(user != null){
							String taskInfo = "task:{userId:" + user.getUserId() + ",fullname:'" + user.getFullname() + "',taskId:" + task.getId() + ",taskName:'" + task.getActivityName() + "'}";
							assigneer = user.getUserId();
							buff.append("{userId:").append(task.getAssignee()).append(",fullname:'").append(user.getFullname()).append("'," + taskInfo + "},");
						}
					}
					// 查询等待下步人员处理的用户信息
					Set<AppUser> taskHanlderUsers = jbpmService.getNodeHandlerUsers(task.getId(), taskStatus);
					for(AppUser us : taskHanlderUsers){
						if(us.getUserId() != assigneer){				
							String taskInfo = "task:{userId:" + us.getUserId() + ",fullname:'" + us.getFullname() + "',taskId:" + task.getId() + ",taskName:'" + task.getActivityName() + "'}";
							buff.append("{userId:").append(us.getUserId()).append(",fullname:'").append(us.getFullname()).append("'," + taskInfo + "},");
						}
					}
				}
				if(buff.length() > 0 && curTasks.size()>0){
					buff.deleteCharAt(buff.length()-1);
				}
			}
		}
		sb = sb + "result:[" + buff + "]}";
		setJsonString(sb);
		return SUCCESS;
	}
	
	/**
	 * 显示当前用户可分发的公文列表
	 */
	public String cruList() {
		PagingBean pb = getInitPagingBean();
		AppUser appUser = ContextUtil.getCurrentUser();
		List<Archives> list = archivesService.findByUserOrRole(appUser.getUserId(), appUser.getRoles(), pb);
		Type type = new TypeToken<List<Archives>>() {}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(pb.getTotalItems()).append(",result:");

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				archivesService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		Archives archives = archivesService.get(archivesId);

		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(archives));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		String arcRecfileIds = getRequest().getParameter("archivesRecfileIds");
		String archDepId = getRequest().getParameter("archDepId");
		String handlerUids=getRequest().getParameter("signUserIds");
		AppUser appUser = ContextUtil.getCurrentUser();
		archives.setArchType(Archives.ARCHIVE_TYPE_RECEIVE);
		archives.setIssuerId(appUser.getUserId());
		archives.setIssuer(appUser.getFullname());
		archives.setHandlerUids(handlerUids);
		archives.setIssueDate(new Date());
		if(StringUtils.isNotEmpty(arcRecfileIds)){
		   archives.setFileCounts(arcRecfileIds.split(",").length);
		}
		archivesService.save(archives);
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
		
		setJsonString("{success:true,archivesId:" + archives.getArchivesId()
				+ "}");
		return SUCCESS;
	}

	/**
	 * 加载公文的附件文档
	 * 
	 * @return
	 */
	public String docs() {
		StringBuffer sb = new StringBuffer("{success:true,totalCounts:");

		if (archivesId != null) {
			archives = archivesService.get(archivesId);
			@SuppressWarnings("unchecked")
			Set<ArchivesDoc> docs = archives.getArchivesDocs();
			List<ArchivesDoc> docList = new ArrayList<ArchivesDoc>();
			docList.addAll(docs);
			Type type = new TypeToken<List<ArchivesDoc>>(){}.getType();
			sb.append(docs.size());
			sb.append(",results:").append(new Gson().toJson(docList, type));

		} else {
			sb.append("0,results:[]");
		}
		sb.append("}");

		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 发文显示详细信息
	 * 
	 * @return
	 */
	public String getIssue() {
		Archives archives = archivesService.get(archivesId);

		// Gson gson=new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		JSONSerializer json = new JSONSerializer();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		// sb.append(gson.toJson(archives));
		sb.append(json.serialize(archives));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 发文添加及保存操作
	 */
	public String saveIssue() {
		// 取当前该附件的所有文档
		String docs = getRequest().getParameter("docs");
		String statusValue = getRequest().getParameter("statusValue");
		AppUser curUser = ContextUtil.getCurrentUser();
		Set<ArchivesDoc> archivesDocSet = new HashSet<ArchivesDoc>();
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
						archivesDocs[i].setFileAttach(fileAttachService
								.getByPath(archivesDocs[i].getDocPath()));
						archivesDocService.save(archivesDocs[i]);

						// 新增文件同时在历史表增加一历史记录
						DocHistory newHistory = new DocHistory();
						newHistory.setArchivesDoc(archivesDocs[i]);
						newHistory.setFileAttach(archivesDocs[i]
								.getFileAttach());
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

		if (archives.getArchivesId() == null) {
			// 初始化发文的数据
			archives.setIssuer(curUser.getFullname());
			archives.setIssuerId(curUser.getUserId());
			// 设置发文的分类
			GlobalType archivesType = globalTypeService.get(archives
					.getArchivesType().getProTypeId());
			archives.setArchivesType(archivesType);
			// 发文
			archives.setArchType(Archives.ARCHIVE_TYPE_DISPATCH);
			
			// 状态
			if(StringUtils.isNotEmpty(statusValue)){
				archives.setStatus(statusValue);
			}else{
				archives.setStatus("主任核稿");
			}
			
			archives.setCreatetime(new Date());
			archives.setIssueDate(new Date());

			// TODO count the files here
			archives.setFileCounts(archivesDocSet.size());
			archives.setArchivesDocs(archivesDocSet);
			archivesService.save(archives);

		} else {
			Archives orgArchives = archivesService
					.get(archives.getArchivesId());
			// 设置发文的分类
			GlobalType archivesType = globalTypeService.get(archives
					.getArchivesType().getProTypeId());
			archives.setArchivesType(archivesType);
			archives.setTypeName(archivesType.getTypeName());
			// 设置发文状态
			if(StringUtils.isNotEmpty(statusValue)){
				archives.setStatus(statusValue);
			}else{
				archives.setStatus("主任核稿");
			}
			// 设置创建时间
			archives.setCreatetime(orgArchives.getCreatetime());
			// TODO count the files here
			archives.setFileCounts(archivesDocSet.size());
			archives.setArchivesDocs(archivesDocSet);
			// 发布日期
			archives.setIssueDate(new Date());
			archives.setArchType(orgArchives.getArchType());
			// 设置发文人信息
			archives.setIssuer(orgArchives.getIssuer());
			archives.setIssuerId(orgArchives.getIssuerId());
			archivesService.merge(archives);
		}

		setJsonString("{success:true,archivesId:'" + archives.getArchivesId()
				+ "'}");
		return SUCCESS;
	}

	/**
	 * 此方法处理文件分发后修改公文状态
	 * 
	 * @return
	 */
	public String handOut() {
		// 设置接收的部门或人员
		if(archivesId == null){
			archivesId = archives.getArchivesId();
		}
		archives = archivesService.get(archivesId);
		String depIds = archives.getRecDepIds();
		if (StringUtils.isNotEmpty(depIds)) {
			String[] depIdArr = depIds.split("[,]");
			if (depIdArr != null) {
				StringBuffer recIds = new StringBuffer("");
				for (int i = 0; i < depIdArr.length; i++) {
					Long depId = new Long(depIdArr[i]);
					Department department = departmentService.get(depId);
					//ArchRecUser archRecUser =archRecUserService.getByDepId(depId);

					ArchivesDep archivesDep = new ArchivesDep();
					archivesDep.setSubject(archives.getSubject());
					archivesDep.setDepartment(department);
					archivesDep.setArchives(archives);
					archivesDep.setIsMain(ArchivesDep.RECEIVE_MAIN);
					archivesDep.setStatus(ArchivesDep.STATUS_UNSIGNED);
//					if (archRecUser != null &&archRecUser.getUserId() != null) {
//						archivesDep.setSignUserID(archRecUser.getUserId());
//						archivesDep.setSignFullname(archRecUser.getFullname());
//						
//						recIds.append(archRecUser.getUserId()).append(",");
//					} else {
//						msg.append(department.getDepName()).append(" 部门还未添加收文负责人");
//					}

					archivesDepService.save(archivesDep);
				}
				//向需要签收公文的人发送信息
				if(StringUtils.isNotEmpty(recIds.toString())){
					String content = "您有新的公文,请及时签收.";
					messageService.save(AppUser.SYSTEM_USER, recIds.toString(), content, ShortMessage.MSG_TYPE_TASK);
				}
				
			}
		}
		String statusValue = getRequest().getParameter("statusValue");
		if (StringUtils.isNotEmpty(statusValue)) {
			archives.setStatus(statusValue);
		}
		archivesService.save(archives);
		return SUCCESS;
	}
	
	
	public String hasten(){
		String activityName=getRequest().getParameter("activityName");
		String archivesId=getRequest().getParameter("archivesId");
		String content=getRequest().getParameter("content");
		if(StringUtils.isNotEmpty(activityName)&&StringUtils.isNotEmpty(archivesId)){
			Long arcId=new Long(archivesId);
//			Date lastCruTime=archHastenService.getLeastRecordByUser(arcId);
//			if(lastCruTime!=null){
//				Date now=new Date();
//				long time=now.getTime()-lastCruTime.getTime();
//				if(time/60000l<30l){//不得小于半个小时发一次催办信息
//					jsonString = "{success:false,message:'催办过于频繁！'}";
//					return SUCCESS;
//				}
//			}
			Archives archives=archivesService.get(arcId);
			Set<Long> userIds=taskservice.getHastenByActivityNameVarKeyLongVal(activityName, Constants.ARCHIES_ARCHIVESID, new Long(archivesId));
			StringBuffer strUsrIds=new StringBuffer();
			Iterator<Long> it=userIds.iterator();
			while(it.hasNext()){
				ArchHasten ah=new ArchHasten();
				Long userId=it.next();
				AppUser appUser=appUserService.get(userId);
				ah.setContent(content);
				ah.setCreatetime(new Date());
				ah.setArchives(archives);
				ah.setHastenFullname(ContextUtil.getCurrentUser().getFullname());
				ah.setHandlerUserId(appUser.getUserId());
				ah.setHandlerFullname(appUser.getFullname());
				archHastenService.save(ah);
				strUsrIds.append(userId.toString()).append(",");
			}
			if(userIds.size()>0){
				strUsrIds.deleteCharAt(strUsrIds.length()-1);
				messageService.save(AppUser.SYSTEM_USER, strUsrIds.toString(), content, ShortMessage.MSG_TYPE_TASK);
			}

		}
		jsonString = "{success:true}";
		return SUCCESS;
	}

}
