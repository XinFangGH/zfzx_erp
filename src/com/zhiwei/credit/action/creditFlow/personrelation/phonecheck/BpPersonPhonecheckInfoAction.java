package com.zhiwei.credit.action.creditFlow.personrelation.phonecheck;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;
import com.zhiwei.credit.model.creditFlow.personrelation.phonecheck.BpPersonPhonecheckInfo;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonRelationService;
import com.zhiwei.credit.service.creditFlow.personrelation.phonecheck.BpPersonPhonecheckInfoService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class BpPersonPhonecheckInfoAction extends BaseAction{
	@Resource
	private BpPersonPhonecheckInfoService bpPersonPhonecheckInfoService;
	@Resource
	private PersonRelationService personRelationService;
	@Resource
	private AppUserService appUserService;
		
	private BpPersonPhonecheckInfo bpPersonPhonecheckInfo;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BpPersonPhonecheckInfo getBpPersonPhonecheckInfo() {
		return bpPersonPhonecheckInfo;
	}

	public void setBpPersonPhonecheckInfo(BpPersonPhonecheckInfo bpPersonPhonecheckInfo) {
		this.bpPersonPhonecheckInfo = bpPersonPhonecheckInfo;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpPersonPhonecheckInfo> list= bpPersonPhonecheckInfoService.getAll(filter);
		
		Type type=new TypeToken<List<BpPersonPhonecheckInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 查询电审信息
	 * @return
	 */
	public String getPhoneList(){
		String projectId=this.getRequest().getParameter("projectId");
		String personType=this.getRequest().getParameter("type");
		String personId=this.getRequest().getParameter("personId");
		List<BpPersonPhonecheckInfo> list= new ArrayList<BpPersonPhonecheckInfo>();
		int size=0;
		//根据用户id和关系类型查询人员关系表
		List<PersonRelation> personRelationList=personRelationService.getByIdAndType(Integer.parseInt(personId),personType);
		if(null!=personRelationList && personRelationList.size()>0){
			for(int i=0;i<personRelationList.size();i++){
				PersonRelation personRelation=personRelationList.get(i);
				if(null!=personRelation){
					BpPersonPhonecheckInfo bp=bpPersonPhonecheckInfoService.getPhoneCheck(projectId,personRelation.getId());
					if(null!=bp){
						bp.setPersonRelationName(personRelation.getRelationName());
						bp.setTelphone(personRelation.getRelationCellPhone());
						if("575".equals(personType)){
							bp.setRelation("亲属");
						}else if("576".equals(personType)){
							bp.setRelation("朋友");
						}else{
							bp.setRelation("同事");
						}
						if(null!=bp.getCheckUserId() && !"".equals(bp.getCheckUserId())){
							AppUser appUser=appUserService.get(Long.valueOf(bp.getCheckUserId()));
							bp.setCheckUserName(appUser.getFullname());
						}
						list.add(bp);
					}
				}
			}
		}
		Type type=new TypeToken<List<BpPersonPhonecheckInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(size).append(",result:");
		Gson gson=new Gson();
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
				bpPersonPhonecheckInfoService.remove(new Long(id));
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
		BpPersonPhonecheckInfo bpPersonPhonecheckInfo=bpPersonPhonecheckInfoService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpPersonPhonecheckInfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpPersonPhonecheckInfo.getId()==null){
			bpPersonPhonecheckInfoService.save(bpPersonPhonecheckInfo);
		}else{
			BpPersonPhonecheckInfo orgBpPersonPhonecheckInfo=bpPersonPhonecheckInfoService.get(bpPersonPhonecheckInfo.getId());
			try{
				BeanUtil.copyNotNullProperties(orgBpPersonPhonecheckInfo, bpPersonPhonecheckInfo);
				bpPersonPhonecheckInfoService.save(orgBpPersonPhonecheckInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
