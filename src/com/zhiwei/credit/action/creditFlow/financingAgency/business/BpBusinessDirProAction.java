package com.zhiwei.credit.action.creditFlow.financingAgency.business;

/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;

import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessDirProService;

import flexjson.JSONSerializer;

/**
 * 
 * @author
 * 
 */
public class BpBusinessDirProAction extends BaseAction {
	@Resource
	private BpBusinessDirProService bpBusinessDirProService;
	private BpBusinessDirPro bpBusinessDirPro;

	private Long bdirProId;

	public Long getBdirProId() {
		return bdirProId;
	}

	public void setBdirProId(Long bdirProId) {
		this.bdirProId = bdirProId;
	}

	public BpBusinessDirPro getBpBusinessDirPro() {
		return bpBusinessDirPro;
	}

	public void setBpBusinessDirPro(BpBusinessDirPro bpBusinessDirPro) {
		this.bpBusinessDirPro = bpBusinessDirPro;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		filter.addSorted("createTime", "DESC");
		filter.addSorted("proNumber", "DESC");
		List<BpBusinessDirPro> list = bpBusinessDirProService.getAll(filter);

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("bidTime",
				"createTime", "updateTime");
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
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
				bpBusinessDirProService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}
	
	/**
	 * 统计发标情况
	 */
	@SuppressWarnings("null")
	public String listPublish() {

		QueryFilter filter = new QueryFilter(getRequest());
		filter.addSorted("proNumber", "DESC");
		List<BpBusinessDirPro> list = bpBusinessDirProService.getAll(filter);
		List<BpBusinessDirPro> listCurr = new ArrayList<BpBusinessDirPro>();
		for (BpBusinessDirPro pack : list) {
			// 计算打包项目的剩余金额
			pack = bpBusinessDirProService.residueMoneyMeth(pack);
			listCurr.add(pack);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("singleTime",
				"createTime","bidTime", "updateTime");
		buff.append(serializer.exclude(new String[] { "class" }).serialize(
				listCurr));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}
	/**
	 * 获取剩余招标金额 招标人数等信息
	 * 
	 * @return
	 */
	public String getBidInfo() {
		BpBusinessDirPro bpBusinessDirPro = bpBusinessDirProService
				.get(bdirProId);

		bpBusinessDirPro = bpBusinessDirProService.residueMoneyMeth(bpBusinessDirPro);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer(
				"singleTime","bidTime", "createTime",
				"updateTime");
		sb.append(serializer.exclude(
				new String[] { "class"}).serialize(
						bpBusinessDirPro));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		BpBusinessDirPro bpBusinessDirPro = bpBusinessDirProService.get(bdirProId);
		if(null == bpBusinessDirPro.getDisclosureCreateDate() || "".equals(bpBusinessDirPro.getDisclosureCreateDate())){
			bpBusinessDirPro.setDisclosureCreateDate(new Date());
		}
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("bidTime", "createTime","updateTime","disclosureCreateDate","disclosureUpdateDate");
		sb.append(serializer.exclude(new String[] { "class"}).serialize(bpBusinessDirPro));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		if (bpBusinessDirPro.getBdirProId() == null) {
			bpBusinessDirProService.save(bpBusinessDirPro);
		} else {
			BpBusinessDirPro orgBpBusinessDirPro = bpBusinessDirProService
					.get(bpBusinessDirPro.getBdirProId());
			try {
				BeanUtil.copyNotNullProperties(orgBpBusinessDirPro,
						bpBusinessDirPro);
				bpBusinessDirProService.save(orgBpBusinessDirPro);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;

	}
}
