package com.zhiwei.credit.action.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.hrm.EmpProfile;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.hrm.EmpProfileService;
import com.zhiwei.credit.service.system.FileAttachService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class EmpProfileAction extends BaseAction{
//	private static short DELETEFLAG_NOT = (short)0;
//	private static short DELETEFLAG_HAD = (short)1;
	@Resource
	private EmpProfileService empProfileService;
	@Resource
	private FileAttachService fileAttachService; 
	private EmpProfile empProfile;
	
	private Long profileId;

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public EmpProfile getEmpProfile() {
		return empProfile;
	}

	public void setEmpProfile(EmpProfile empProfile) {
		this.empProfile = empProfile;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		
//		//把删除掉的档案过滤
//		filter.addFilter("Q_delFlag_SN_EQ","0");
//		
		List<EmpProfile> list= empProfileService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		JSONSerializer serializer=JsonUtil.getJSONSerializer("birthday","startWorkDate","checktime","createtime");
		buff.append(serializer.exclude(new String[]{"class","job.class","job.department"}).serialize(list));
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
				EmpProfile deletePro = empProfileService.get(new Long(id));
				deletePro.setDelFlag(EmpProfile.DELETE_FLAG_HAD);
				empProfileService.save(deletePro);
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
		EmpProfile empProfile=empProfileService.get(profileId);
		if(empProfile!=null&&empProfile.getPhoto()!=null&&!empProfile.getPhoto().equals("")){
			FileAttach fileattach = fileAttachService.getByPath(empProfile.getPhoto());
			if(fileattach!=null){
				empProfile.setPhotoId(fileattach.getFileId().toString());
			}
		}
		//Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		JSONSerializer json = JsonUtil.getJSONSerializer("birthday","startWorkDate","createtime","checktime");
		
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:[");
		//sb.append(gson.toJson(empProfile));
		sb.append(json.exclude(new String[]{"class","department"}).serialize(empProfile));
		sb.append("]}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		//验证
		boolean pass = false;
		StringBuffer buff = new StringBuffer("{");
		if(empProfile.getProfileId() == null){
			if(empProfileService.checkProfileNo(empProfile.getProfileNo())){
				empProfile.setCreator(ContextUtil.getCurrentUser().getFullname());
				empProfile.setCreatetime(new Date());
				empProfile.setDelFlag(EmpProfile.DELETE_FLAG_NOT);
				pass = true;
			}else{
				buff.append("msg:'档案编号已存在,请重新输入.',");
			}
		}else{
			pass = true;
		}
		if(pass){
			empProfile.setApprovalStatus(EmpProfile.CHECK_FLAG_NONE);
			empProfileService.save(empProfile);
			buff.append("success:true}");
		}else{
			buff.append("failure:true}");
		}
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**
	 * 系统生成档案编号
	 * @return
	 */
	public String number(){
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddHHmmss-SSSS");
		String profileNo = date.format(new Date());
		setJsonString("{success:true,profileNo:'PN"+profileNo+"'}");
		return SUCCESS;
	}
	
	/**
	 * 档案审核动作类
	 * @return
	 */
	public String check(){
		EmpProfile checkProfile = empProfileService.get(profileId);
		checkProfile.setCheckName(ContextUtil.getCurrentUser().getFullname());
		checkProfile.setChecktime(new Date());
		checkProfile.setApprovalStatus(empProfile.getApprovalStatus());
		checkProfile.setOpprovalOpinion(empProfile.getOpprovalOpinion());
		empProfileService.save(checkProfile);
		return SUCCESS;
	}
	/**
	 * 恢复删除掉的记录
	 * @return
	 */
	public String recovery(){
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				EmpProfile deletePro = empProfileService.get(new Long(id));
				deletePro.setDelFlag(EmpProfile.DELETE_FLAG_NOT);
				empProfileService.save(deletePro);
			}
		}
		jsonString="{success:true}";
		return SUCCESS;
	}
	/**
	 * 删除图片
	 */
	public String delphoto(){
		if(profileId!=null){
			empProfile=empProfileService.get(profileId);
			empProfile.setPhoto("");
			empProfileService.save(empProfile);
			jsonString="{success:true}";
		}
		return SUCCESS;
	}
}
