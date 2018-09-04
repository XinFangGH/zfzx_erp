package com.zhiwei.credit.action.info;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.info.SuggestBox;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.SysConfig;
import com.zhiwei.credit.service.info.SuggestBoxService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.SysConfigService;
/**
 * 
 * @author 
 *
 */
public class SuggestBoxAction extends BaseAction{
	@Resource
	private SuggestBoxService suggestBoxService;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private AppUserService appUserService;
	private SuggestBox suggestBox;
	
	private Long boxId;

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public SuggestBox getSuggestBox() {
		return suggestBox;
	}

	public void setSuggestBox(SuggestBox suggestBox) {
		this.suggestBox = suggestBox;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<SuggestBox> list= suggestBoxService.getAll(filter);
		
		Type type=new TypeToken<List<SuggestBox>>(){}.getType();
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
				suggestBoxService.remove(new Long(id));
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
		SuggestBox suggestBox=suggestBoxService.get(boxId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(suggestBox));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		//意见箱创建日期
		suggestBox.setCreatetime(new Date());
		//意见发表人IP
		suggestBox.setSenderIp(getRequest().getRemoteAddr());
		//取得意见箱配置的接收人ID
		SysConfig suggestId = sysConfigService.findByKey("suggestId");
		AppUser suggestManager = appUserService.get(new Long(suggestId.getDataValue()));
		
		if(suggestManager !=null){
			suggestBox.setRecFullname(suggestManager.getFullname());
			suggestBox.setRecUid(suggestManager.getUserId());
			
			//发送邮件(是系统中用户时用该用户发送,系统外时用系统用户发送)
			
		}
		
		suggestBoxService.save(suggestBox);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 意见回复
	 * @return
	 */
	public String reply(){
		SuggestBox orgSuggest = suggestBoxService.get(suggestBox.getBoxId());
		AppUser curUser= appUserService.get(new Long(sysConfigService.findByKey("suggestId").getDataValue()));
		orgSuggest.setReplyId(curUser.getUserId());
		orgSuggest.setIsOpen(suggestBox.getIsOpen());
		orgSuggest.setReplyFullname(curUser.getFullname());
		orgSuggest.setReplyTime(new Date());
		orgSuggest.setStatus(SuggestBox.STATUS_AUDIT);
		orgSuggest.setReplyContent(suggestBox.getReplyContent());
		suggestBoxService.save(orgSuggest);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	/**
	 * 密码访问
	 */
	public String match(){
		SuggestBox orgSuggest = suggestBoxService.get(suggestBox.getBoxId());
		if(orgSuggest.getQueryPwd().equals(suggestBox.getQueryPwd())){
			setJsonString("{success:true}");
		}else{
			setJsonString("{failure:true}");
		}
		return SUCCESS;
	}
}
