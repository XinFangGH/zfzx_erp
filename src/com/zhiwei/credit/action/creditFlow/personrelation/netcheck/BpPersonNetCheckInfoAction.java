package com.zhiwei.credit.action.creditFlow.personrelation.netcheck;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.personrelation.netcheck.BpPersonNetCheckInfo;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.personrelation.netcheck.BpPersonNetCheckInfoService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class BpPersonNetCheckInfoAction extends BaseAction{
	@Resource
	private BpPersonNetCheckInfoService bpPersonNetCheckInfoService;
	@Resource
	private AppUserService appUserService;
	private BpPersonNetCheckInfo bpPersonNetCheckInfo;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BpPersonNetCheckInfo getBpPersonNetCheckInfo() {
		return bpPersonNetCheckInfo;
	}

	public void setBpPersonNetCheckInfo(BpPersonNetCheckInfo bpPersonNetCheckInfo) {
		this.bpPersonNetCheckInfo = bpPersonNetCheckInfo;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpPersonNetCheckInfo> list= bpPersonNetCheckInfoService.getAll(filter);
		if(null!=list && !"".equals(list)){
			for(int i=0;i<list.size();i++){
				BpPersonNetCheckInfo bp=list.get(i);
				if(null!=bp.getCheckUserId() && !"".equals(bp.getCheckUserId())){
					AppUser appUser=appUserService.get(Long.valueOf(bp.getCheckUserId()));
					bp.setCheckUserName(appUser.getFullname());
				}
			}
		}
		Type type=new TypeToken<List<BpPersonNetCheckInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
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
				bpPersonNetCheckInfoService.remove(new Long(id));
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
		BpPersonNetCheckInfo bpPersonNetCheckInfo=bpPersonNetCheckInfoService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpPersonNetCheckInfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpPersonNetCheckInfo.getId()==null){
			bpPersonNetCheckInfoService.save(bpPersonNetCheckInfo);
		}else{
			BpPersonNetCheckInfo orgBpPersonNetCheckInfo=bpPersonNetCheckInfoService.get(bpPersonNetCheckInfo.getId());
			try{
				BeanUtil.copyNotNullProperties(orgBpPersonNetCheckInfo, bpPersonNetCheckInfo);
				bpPersonNetCheckInfoService.save(orgBpPersonNetCheckInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
