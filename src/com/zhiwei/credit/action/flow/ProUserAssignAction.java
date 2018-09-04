package com.zhiwei.credit.action.flow;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.io.*;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.zhiwei.core.jbpm.jpdl.Node;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProDefinitionService;
import com.zhiwei.credit.service.flow.ProUserAssignService;

/**
 * 
 * @author csx
 * 
 */
public class ProUserAssignAction extends BaseAction {
	@Resource
	private ProUserAssignService proUserAssignService;
	@Resource
	private JbpmService jbpmService;
	@Resource
	private ProDefinitionService proDefinitionService;

	public void setJbpmService(JbpmService jbpmService) {
		this.jbpmService = jbpmService;
	}

	private ProUserAssign proUserAssign;

	private Long assignId;

	public Long getAssignId() {
		return assignId;
	}

	public void setAssignId(Long assignId) {
		this.assignId = assignId;
	}

	public ProUserAssign getProUserAssign() {
		return proUserAssign;
	}

	public void setProUserAssign(ProUserAssign proUserAssign) {
		this.proUserAssign = proUserAssign;
	}

	/**
	 * 显示授权的列表
	 */
	public String list() {

		Gson gson = new Gson();

		String defId = getRequest().getParameter("defId");

		ProDefinition proDefinition = proDefinitionService.get(new Long(defId));

		List<Node> nodes = jbpmService.getTaskNodesByDefId(new Long(defId));

		List<ProUserAssign> nodesAssignList = proUserAssignService
				.getByDeployId(proDefinition.getDeployId());

		StringBuffer buff = new StringBuffer("{result:[");

		for (int i = 0; i < nodes.size(); i++) {
			String nodeName = nodes.get(i).getName();
			buff.append("{activityName:'").append(nodeName).append(
					"',deployId:'" + proDefinition.getDeployId()).append("'");
			for (int j = 0; j < nodesAssignList.size(); j++) {
				ProUserAssign assign = nodesAssignList.get(j);
				if (nodeName.equals(assign.getActivityName())) {
					buff
							.append(",assignId:'")
							.append(
									gson.toJson(assign.getAssignId()).replace(
											"\"", ""))
							.append("',userId:'")
							.append(
									assign.getUserId() == null ? "" : assign
											.getUserId())
							.append("',username:'")
							.append(
									gson.toJson(assign.getUsername()).replace(
											"\"", ""))
							.append("',roleId:'")
							.append(
									assign.getRoleId() == null ? "" : assign
											.getRoleId())
							.append("',roleName:'")
							.append(
									gson.toJson(assign.getRoleName()).replace(
											"\"", ""))
							.append("',jobId:'")
							.append(
									assign.getJobId() == null ? "" : assign
											.getJobId())
							.append("',jobName:'")
							.append(
									gson.toJson(
											assign.getJobName() == null ? ""
													: assign.getJobName())
											.replace("\"", ""))
							.append("',depPosIds:'")
							.append(
									assign.getDepPosIds() == null ? "" : assign
											.getDepPosIds())
							.append("',depPosNames:'")
							.append(
									gson
											.toJson(
													assign.getDepPosNames() == null ? ""
															: assign
																	.getDepPosNames())
											.replace("\"", "")).append(
									"',reJobId:'").append(
									assign.getReJobId() == null ? "" : assign
											.getReJobId()).append(
									"',reJobName:'").append(
									gson.toJson(
											assign.getReJobName() == null ? ""
													: assign.getReJobName())
											.replace("\"", "")).append(
									"',depIds:'").append(
									assign.getDepIds() == null ? "" : assign
											.getDepIds())
							.append("',depNames:'").append(
									gson.toJson(
											assign.getDepNames() == null ? ""
													: assign.getDepNames())
											.replace("\"", "")).append(
									"',isSigned:'").append(
									assign.getIsSigned() == null ? 0 : assign
											.getIsSigned()).append(
									"',posUserFlag:'").append(
									assign.getPosUserFlag() == null ? 0
											: assign.getPosUserFlag()).append(
									"'").append(",taskTimeLimit:").append(
									assign.getTaskTimeLimit() == null ? 0
											: assign.getTaskTimeLimit())
							.append(",timeLimitType:").append(
									assign.getTimeLimitType() == null ? 0
											: assign.getTimeLimitType())
							.append(",isJumpToTargetTask:").append(
									assign.getIsJumpToTargetTask() == null ? 1
											: assign.getIsJumpToTargetTask())
							.append(",taskSequence:'").append(
									assign.getTaskSequence() == null ? 0
											: assign.getTaskSequence())
							.append("',isseparate:'").append(
									assign.getIsseparate() == null ? "0"
											: assign.getIsseparate())
							.append("',isReSetNext:'").append(assign.getIsReSetNext() == null ? "0": assign.getIsReSetNext())	
							.append(
									"',isProjectChange:").append(
									assign.getIsProjectChange() == null ? 0
											: assign.getIsProjectChange());
					break;
				}
			}
			buff.append("},");
		}

		if (!nodes.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
		}

		buff.append("]}");
		setJsonString(buff.toString());

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		ProUserAssign proUserAssign = proUserAssignService.get(assignId);

		Gson gson = new Gson();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(proUserAssign));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		String data = getRequest().getParameter("data");
		// logger.info("data:"+data);
		if (StringUtils.isNotEmpty(data)) {
			Gson gson = new Gson();
			ProUserAssign[] assigns = gson
					.fromJson(data, ProUserAssign[].class);
			for (ProUserAssign assign : assigns) {
				if (assign.getAssignId() == -1) {// 若为-1，则代表尚未持久化,主要用于防止自动绑值（gson.fromJson(data,
					// ProUserAssign[].class)）出错;
					assign.setAssignId(null);
				}
				if (assign.getIsJumpToTargetTask() == -1) {
					assign.setIsJumpToTargetTask((short) 1);
				}
				if (assign.getIsProjectChange() == -1) {
					assign.setIsProjectChange((short) 1);
				} else {
					assign.setIsProjectChange((short) 0);
				}
				proUserAssignService.save(assign);
				doMakeVms(assign.getTaskSequence(),assign.getActivityName());
				// 添加用户自己的处理代码
				// jbpmService.addAssignHandler(assign);
			}
		}

		return SUCCESS;
	}

	// id:会签节点 name:流程任务名
	// 以会签节点最后_id为准
	private void doMakeVms(String id, String name) {
		String[] ids = id.split("_");
		String lastId = ids[ids.length - 1];
		makeVm(lastId);// 产生vm
	}

	private void makeVm(String id) {
		File sourceFile = null;
	//	if ("jzdc".equals(id)) {
			sourceFile = new File("");
			String absolutePath = sourceFile.getAbsolutePath();
			System.out.println(absolutePath);// D:\
		//}

	}

	private void copyVm(File sourceFile, File targetFile) throws Exception {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}
}
