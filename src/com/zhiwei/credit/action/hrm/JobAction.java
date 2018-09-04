package com.zhiwei.credit.action.hrm;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.hrm.Job;
import com.zhiwei.credit.service.hrm.JobService;

import flexjson.JSONSerializer;

/**
 * @description 岗位管理
 * @class JobAction
 * @author YHZ
 * @company www.credit-software.com
 * @data 2010-12-22PM
 * 
 */
public class JobAction extends BaseAction {
	@Resource
	private JobService jobService;
	private Job job;

	private Long jobId;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	/**
	 * 显示列表
	 */
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<Job> list = jobService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = new JSONSerializer();
		buff.append(serializer.exclude(
				new String[] { "class", "department.appUser" }).serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 批量删除
	 */
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				Job removeJob = jobService.get(new Long(id));
				removeJob.setDelFlag(Job.DELFLAG_HAD);
				jobService.save(removeJob);
			}
		}
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * 单条记录删除
	 */
	public String delete() {
		Job removeJob = jobService.get(jobId);
		removeJob.setDelFlag(Job.DELFLAG_HAD);
		jobService.save(removeJob);
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 */
	public String get() {
		Job job = jobService.get(jobId);

		// Gson gson=new Gson();
		JSONSerializer json = new JSONSerializer();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(json.exclude(new String[] { "class" }).serialize(job));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		job.setDelFlag(Job.DELFLAG_NOT);
		jobService.save(job);
		setJsonString("{success:true}");
		return SUCCESS;
	}

	/**
	 * 下拉选择 根据部门来选择
	 */
	public String combo() {
		String strDepId = getRequest().getParameter("depId");
		if (StringUtils.isNotEmpty(strDepId)) {
			List<Job> list = jobService.findByDep(new Long(strDepId));
			StringBuffer sb = new StringBuffer("[");
			for (Job job : list) {
				sb.append("['").append(job.getJobId()).append("','")
						.append(job.getJobName()).append("'],");
			}
			if (list.size() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append("]");
			setJsonString(sb.toString());
		} else {
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}

	/**
	 * 恢复职位
	 */
	public String recovery() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				Job deleteJob = jobService.get(new Long(id));
				deleteJob.setDelFlag(Job.DELFLAG_NOT);
				jobService.save(deleteJob);
			}
		}
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * 添加节点
	 */
	public String add() {
		job.setDelFlag(Job.DELFLAG_NOT);
		if (job.getJobId() != null && job.getJobId() > 0) { // 修改
			jobService.edit(job);
		} else { // 保存
			boolean bo = job.getParentId() > 0; // true:存在父节点
			// 1.判断是否为父节点[为节点深度depth赋值]
			if (bo) { // 有父节点
				// 获取父节点深度
				Job jb = jobService.get(job.getParentId());
				job.setDepth(jb.getDepth() + 1);
			} else
				job.setDepth(new Long(2));
			// 2.保存:jobName,depId,memo,delFlag,parentId,depth,获取jobId,设置path路径
			Job newJob = jobService.save(job);
			// 3.设置path路径
			if (bo) {
				Job jb = jobService.get(job.getParentId());
				newJob.setPath(jb.getPath() + newJob.getJobId() + ".");
			} else {
				newJob.setPath("0." + newJob.getJobId() + ".");
			}
			// 4.再次保存完整的数据
			jobService.save(newJob);
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}

	// /////////////////新增代码//////////////////////////////

	/**
	 * 加载tree节点
	 */
	public String treeLoad() {
		StringBuffer sb = new StringBuffer("[{id:'0',text:'"
				+ AppUtil.getCompanyName() + "',expanded:true,children:[");
		// 查询顶级父节点
		List<Job> list = jobService.findByCondition(new Long(0));
		for (Job jb : list) {
			sb.append("{id:'" + jb.getJobId() + "',text:'" + jb.getJobName()
					+ "',");
			sb.append(findChildren(jb.getJobId()));
		}
		if (!list.isEmpty()) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append("]}]");
		jsonString = sb.toString();
		return SUCCESS;
	}

	/**
	 * 根据parentId查询对应的节点
	 */
	private String findChildren(Long parentId) {
		StringBuffer sb = new StringBuffer();
		List<Job> list = jobService.findByCondition(parentId);
		if (list.isEmpty() || list.size() == 0) { // 不存在子节点
			sb.append("leaf:true},");
			return sb.toString();
		} else { // 存在子节点
			sb.append("expanded:true,children:[");
			for (Job j : list) {
				sb.append("{id:'" + j.getJobId() + "',text:'" + j.getJobName()
						+ "',");
				sb.append(findChildren(j.getJobId()));
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]},");
			return sb.toString();
		}
	}
}
