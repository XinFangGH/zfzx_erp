package com.zhiwei.credit.action.system;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;

import com.zhiwei.credit.model.system.RelativeJob;
import com.zhiwei.credit.service.system.RelativeJobService;

/**
 * @description 相对岗位管理
 * @class RelativeJobAction
 * @author YHZ
 * @data 2010-12-13AM
 * @company www.credit-software.com
 */
public class RelativeJobAction extends BaseAction {
	@Resource
	private RelativeJobService relativeJobService;
	
	private RelativeJob relativeJob;

	private Long reJobId;

	public Long getReJobId() {
		return reJobId;
	}

	public void setReJobId(Long reJobId) {
		this.reJobId = reJobId;
	}

	public RelativeJob getRelativeJob() {
		return relativeJob;
	}

	public void setRelativeJob(RelativeJob relativeJob) {
		this.relativeJob = relativeJob;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<RelativeJob> list = relativeJobService.getAll(filter);

		Type type = new TypeToken<List<RelativeJob>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 加载相对岗位信息
	 */
	public String treeLoad() {
		StringBuffer sb = new StringBuffer("");
		sb.append("[{id:'0',text:'" + AppUtil.getCompanyName()
				+ "',expanded:true,children:[");
		List<RelativeJob> list = relativeJobService.findByParentId(new Long(0)); // 顶级父节点
		for (RelativeJob job : list) {
			sb.append("{id:'" + job.getReJobId() + "',text:'"
					+ job.getJobName() + "',");
			sb.append(findChildren(job.getReJobId()));
		}
		if (!list.isEmpty())
			sb.deleteCharAt(sb.length() - 1);
		sb.append("]}]");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null)
			for (String id : ids)
				relativeJobService.remove(new Long(id));
		jsonString = "{success:true}";
		return SUCCESS;
	}

	@Override
	public String delete() {
		relativeJobService.remove(reJobId);
		jsonString = "{success:true}";
		return SUCCESS;
	}

	
	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		RelativeJob relativeJob = relativeJobService.get(reJobId);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:[");
		sb.append(gson.toJson(relativeJob));
		sb.append("]}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 编辑操作
	 */
	public String save() {
		if (relativeJob.getReJobId() == null) {
			add();
		} else {
			RelativeJob orgRelativeJob = relativeJobService.get(relativeJob
					.getReJobId());
			try {
				BeanUtil.copyNotNullProperties(orgRelativeJob, relativeJob);
				relativeJobService.save(orgRelativeJob);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;

	}

	/**
	 * 添加操作
	 */
	private String add() {
		// 1.判断是否为顶级结点，设置深度
		boolean isParent = relativeJob.getParent() < 1;
		if (isParent)
			relativeJob.setDepath(2);
		else {
			Integer parentDepath = relativeJobService.get(
					relativeJob.getParent()).getDepath(); // 父节点深度
			relativeJob.setDepath(parentDepath + 1);
		}
		// 2.保存：jobName,parent,同时获取 :reJobId
		RelativeJob newRelativeJob = relativeJobService.save(relativeJob);
		// 3.设置：depath,path,jobCode
		if (isParent) { // 顶级节点
			newRelativeJob.setPath("0." + newRelativeJob.getReJobId() + ".");
		} else { // 有父节点
			// 获取父节点Path
			String parentPath = relativeJobService.get(
					newRelativeJob.getParent()).getPath();
			newRelativeJob.setPath(parentPath + newRelativeJob.getReJobId()
					+ ".");
		}
		newRelativeJob.setJobCode("0");
		// 4.修改数据
		relativeJobService.save(newRelativeJob);
		setJsonString("{success:true}");
		return SUCCESS;
	}

	// ------------------私有方法------------------//
	/**
	 * 根据父节点编号,查询对应的子节点信息
	 */
	private String findChildren(Long childId) {
		StringBuffer sb = new StringBuffer("");
		List<RelativeJob> list = relativeJobService.findByParentId(childId);
		if (list.isEmpty() || list.size() == 0) {
			sb.append("leaf:true},");
			return sb.toString();
		} else {
			sb.append("children:[");
			for (RelativeJob job : list) {
				sb.append("{id:'" + job.getReJobId() + "',text:'"
						+ job.getJobName() + "',");
				sb.append(findChildren(job.getReJobId()));
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append("]},");
			return sb.toString();
		}
	}
}
