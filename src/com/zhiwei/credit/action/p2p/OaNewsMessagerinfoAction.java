package com.zhiwei.credit.action.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
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
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.model.p2p.OaNewsMessage;
import com.zhiwei.credit.model.p2p.OaNewsMessagerinfo;
import com.zhiwei.credit.service.p2p.OaNewsMessagerinfoService;
/**
 * 
 * @author 
 *
 */
public class OaNewsMessagerinfoAction extends BaseAction{
	@Resource
	private OaNewsMessagerinfoService oaNewsMessagerinfoService;
	private OaNewsMessagerinfo oaNewsMessagerinfo;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OaNewsMessagerinfo getOaNewsMessagerinfo() {
		return oaNewsMessagerinfo;
	}

	public void setOaNewsMessagerinfo(OaNewsMessagerinfo oaNewsMessagerinfo) {
		this.oaNewsMessagerinfo = oaNewsMessagerinfo;
	}

	/**
	 * 查询每一个站内信发送给收件人列表
	 */
	public String list(){
		PagingBean pb=new PagingBean(start,limit);
		List<OaNewsMessagerinfo> list= oaNewsMessagerinfoService.getAllInfo(pb,getRequest());
		Type type=new TypeToken<List<OaNewsMessagerinfo>>(){}.getType();

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		System.out.println(jsonString);
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
				oaNewsMessagerinfoService.remove(new Long(id));
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
		OaNewsMessagerinfo oaNewsMessagerinfo=oaNewsMessagerinfoService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(oaNewsMessagerinfo));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(oaNewsMessagerinfo.getId()==null){
			oaNewsMessagerinfoService.save(oaNewsMessagerinfo);
		}else{
			OaNewsMessagerinfo orgOaNewsMessagerinfo=oaNewsMessagerinfoService.get(oaNewsMessagerinfo.getId());
			try{
				BeanUtil.copyNotNullProperties(orgOaNewsMessagerinfo, oaNewsMessagerinfo);
				oaNewsMessagerinfoService.save(orgOaNewsMessagerinfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	public String isDelete(){
		String[]ids=getRequest().getParameterValues("id");
		if(ids!=null){
			for(String id:ids){
				OaNewsMessagerinfo orgOaNewsMessagerinfo=oaNewsMessagerinfoService.get(new Long(id));
				try{
					orgOaNewsMessagerinfo.setStatus(Integer.valueOf("1"));//0为未删除，1为已删除
					oaNewsMessagerinfoService.merge(orgOaNewsMessagerinfo);
				}catch(Exception ex){
					logger.error(ex.getMessage());
				}
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
