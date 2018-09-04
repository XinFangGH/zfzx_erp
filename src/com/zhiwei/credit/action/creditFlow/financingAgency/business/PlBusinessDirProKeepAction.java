package com.zhiwei.credit.action.creditFlow.financingAgency.business;

/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */
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
import com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro;
import com.zhiwei.credit.model.creditFlow.financingAgency.business.PlBusinessDirProKeep;
import com.zhiwei.credit.model.creditFlow.financingAgency.persion.PlPersionDirProKeep;
import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepCreditlevel;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessDirProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.BpBusinessOrProService;
import com.zhiwei.credit.service.creditFlow.financingAgency.business.PlBusinessDirProKeepService;
import com.zhiwei.credit.service.creditFlow.financingAgency.typeManger.PlKeepCreditlevelService;

import flexjson.JSONSerializer;

/**
 * 
 * @author
 * 
 */
public class PlBusinessDirProKeepAction extends BaseAction {
	@Resource
	private PlBusinessDirProKeepService plBusinessDirProKeepService;
	@Resource
	private BpBusinessDirProService bpBusinessDirProService;
	@Resource
	private BpBusinessOrProService bpBusinessOrProService;
	@Resource
	private PlKeepCreditlevelService plKeepCreditlevelService;
	private PlBusinessDirProKeep plBusinessDirProKeep;

	private Long keepId;

	public Long getKeepId() {
		return keepId;
	}

	public void setKeepId(Long keepId) {
		this.keepId = keepId;
	}


	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<PlBusinessDirProKeep> list = plBusinessDirProKeepService
				.getAll(filter);

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("createDate",
				"updateDate");
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
				plBusinessDirProKeepService.remove(new Long(id));
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
		PlBusinessDirProKeep plBusinessDirProKeep = plBusinessDirProKeepService
				.get(keepId);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer(
				 "createDate",
				"updateDate");
		sb.append(serializer.exclude(new String[] { "class" }).serialize(
				plBusinessDirProKeep));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}
	
	public String getInfo(){
		try{
			String type=this.getRequest().getParameter("type");
			String id=this.getRequest().getParameter("id");
			PlBusinessDirProKeep keep=plBusinessDirProKeepService.getByType(type, Long.valueOf(id));
			jsonString="{success:true,keepId:"+keep.getKeepId()+"}";
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		String proType=this.getRequest().getParameter("proType");
		String bpBusinessId=this.getRequest().getParameter("bpBusinessId");
		Long proLevelId = Long.valueOf(this.getRequest().getParameter("proLevelId"));
		plBusinessDirProKeep = setProPack(plBusinessDirProKeep);
		PlBusinessDirProKeep dirKeep=null;
		//防止页面刷新失败，在数据库插入相同的数据 start
		if(null != proType && !"".equals(proType) && null != bpBusinessId && !"".equals(bpBusinessId)){
			if(proType.equals("B_Dir")){
				dirKeep = plBusinessDirProKeepService.getByType("B_Dir", Long.valueOf(bpBusinessId));
				
			}else if(proType.equals("B_Or")){
				dirKeep = plBusinessDirProKeepService.getByType("B_Or", Long.valueOf(bpBusinessId));
			}
			
		}
		if(null == dirKeep){
			dirKeep = new PlBusinessDirProKeep();
		}
		try {
			BeanUtil.copyNotNullProperties(dirKeep, plBusinessDirProKeep);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		//end
		
		if (dirKeep.getKeepId() == null) {
			dirKeep.setCreateDate(new Date());
			dirKeep.setUpdateDate(new Date());
			if(null!=proLevelId && !"".equals(proLevelId)){
				PlKeepCreditlevel pl=plKeepCreditlevelService.get(proLevelId);
				dirKeep.setPlKeepCreditlevel(pl);
			}
			plBusinessDirProKeepService.save(dirKeep);
			updateProState(dirKeep.getProType());
		} else {
			PlBusinessDirProKeep orgplBusinessDirProKeep = plBusinessDirProKeepService.get(plBusinessDirProKeep.getKeepId());
			if(null!=proLevelId && !"".equals(proLevelId)){
				PlKeepCreditlevel pl=plKeepCreditlevelService.get(proLevelId);
				orgplBusinessDirProKeep.setPlKeepCreditlevel(pl);
			}
			try {
				BeanUtil.copyNotNullProperties(orgplBusinessDirProKeep,
						plBusinessDirProKeep);

				plBusinessDirProKeepService.save(orgplBusinessDirProKeep);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

		}
		setJsonString("{success:true}");
		return SUCCESS;

	}

	// 设置pack 保存和更新的值
	public PlBusinessDirProKeep setProPack(PlBusinessDirProKeep pack) {
		Long proTypeId = Long.valueOf(this.getRequest().getParameter(
				"proTypeId"));
		Long proLevelId = Long.valueOf(this.getRequest().getParameter(
				"proLevelId"));
		// 抵质押 展示字符串
		String llimitsIds = this.getRequest().getParameter("llimitsIds");
		// 保证担保 展示字符串
		String levelIds = this.getRequest().getParameter("levelIds");
		// 贷款材料清单 展示字符串
		String materialsIds = this.getRequest().getParameter("materialsIds");
		if (materialsIds.length() > 0) {
			pack.setProLoanMaterShow(materialsIds.substring(0, materialsIds
					.length() - 1));
		} else {
			pack.setProLoanMaterShow(materialsIds);
		}
		if (levelIds.length() > 0) {
			pack.setProLoanLevelShow(levelIds.substring(0,
					levelIds.length() - 1));
		} else {
			pack.setProLoanLevelShow(levelIds);
		}
		if (llimitsIds.length() > 0) {
			pack.setProLoanLlimitsShow(llimitsIds.substring(0, llimitsIds
					.length() - 1));
		} else {
			pack.setProLoanLlimitsShow(llimitsIds);
		}
	//	pack.setCreditLevelId(proLevelId);
		pack.setTypeId(proTypeId);

		return pack;
	}

	// 更新贷款项目状态为已经打包项目 设置为1
	public void updateProState(String proType) {
		// 更新贷款项目状态为已经打包项目 设置为1
		if (proType.equals("B_Dir")) {
			BpBusinessDirPro sl = bpBusinessDirProService
					.get(plBusinessDirProKeep.getBDirProId());
			sl.setKeepStat(1);
			bpBusinessDirProService.save(sl);
		} else if (proType.equals("B_Or")) {
			BpBusinessOrPro sl = bpBusinessOrProService
					.get(plBusinessDirProKeep.getBOrProId());
			sl.setKeepStat(1);
			bpBusinessOrProService.save(sl);
		}
	}

	public PlBusinessDirProKeep getPlBusinessDirProKeep() {
		return plBusinessDirProKeep;
	}

	public void setPlBusinessDirProKeep(PlBusinessDirProKeep plBusinessDirProKeep) {
		this.plBusinessDirProKeep = plBusinessDirProKeep;
	}
}
