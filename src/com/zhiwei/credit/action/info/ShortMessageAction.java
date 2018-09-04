package com.zhiwei.credit.action.info;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.info.InMessage;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.info.InMessageService;
import com.zhiwei.credit.service.info.ShortMessageService;
import com.zhiwei.credit.service.system.AppUserService;

public class ShortMessageAction extends BaseAction {

	static short NOT_DELETE=(short)0;
	private ShortMessage shortMessage;
    private Date from;
    private Date to;
	private List<InMessage> inList=new ArrayList<InMessage>();
	
	public List<InMessage> getInList() {
		return inList;
	}

	public void setInList(List<InMessage> inList) {
		this.inList = inList;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public ShortMessage getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(ShortMessage shortMessage) {
		this.shortMessage = shortMessage;
	}
	
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private InMessageService inMessageService;
	@Resource
	private AppUserService appUserService;
	
	/**
	 * 发送列表
	 */
	public String list(){
		List<Object[]> list;
		PagingBean pb=getInitPagingBean();
		AppUser appUser=ContextUtil.getCurrentUser();
		list=shortMessageService.searchShortMessage(appUser.getUserId(), shortMessage, from, to, pb,null);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':"+pb.getTotalItems()+",result:");
		List<InMessage> inList=new ArrayList<InMessage>();
		for(int i=0;i<list.size();i++){
		    InMessage inMessage=(InMessage)list.get(i)[0];
		    inList.add(inMessage);
	    }
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	    Type type=new TypeToken<List<InMessage>>(){}.getType();
	    buff.append(gson.toJson(inList,type));
		buff.append("}");
		setJsonString(buff.toString());
	    return SUCCESS;
	}
	
	/**
	 * 发送
	 * @return
	 */
	public String send(){
		String reId=getRequest().getParameter("userId");
		String content=getRequest().getParameter("content");
		
		AppUser appUser=ContextUtil.getCurrentUser();
		if(StringUtils.isNotEmpty(reId)&&StringUtils.isNotEmpty(content)){
			String st[]=reId.split(",");
			ShortMessage message=new ShortMessage();
			message.setContent(content);
			message.setMsgType((short)1);
			message.setSenderId(appUser.getUserId());
			message.setSender(appUser.getFullname());
			message.setSendTime(new Date());
			shortMessageService.save(message);
			for(int i=0;i<st.length;i++){
				InMessage in=new InMessage();	
				in.setUserId(Long.parseLong(st[i]));
				AppUser user=appUserService.get(Long.parseLong(st[i]));
				in.setUserFullname(user.getFullname());
				in.setDelFlag(NOT_DELETE);
				in.setReadFlag((short)0);
				in.setShortMessage(message);
				inMessageService.save(in);
			}						
			setJsonString("{success:true}");
		}else{
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
	

}
