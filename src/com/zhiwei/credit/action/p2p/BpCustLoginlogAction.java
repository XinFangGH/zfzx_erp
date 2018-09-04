package com.zhiwei.credit.action.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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


import com.zhiwei.credit.model.p2p.BpCustLoginlog;
import com.zhiwei.credit.service.p2p.BpCustLoginlogService;
/**
 * 
 * @author 
 *
 */
public class BpCustLoginlogAction extends BaseAction{
	@Resource
	private BpCustLoginlogService bpCustLoginlogService;
	private BpCustLoginlog bpCustLoginlog;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BpCustLoginlog getBpCustLoginlog() {
		return bpCustLoginlog;
	}

	public void setBpCustLoginlog(BpCustLoginlog bpCustLoginlog) {
		this.bpCustLoginlog = bpCustLoginlog;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//将数据转成JSON格式	
		StringBuffer sb = new StringBuffer();
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BpCustLoginlog> list2=bpCustLoginlogService.getAllList(getRequest(),start,limit);
		List<BpCustLoginlog> list3=bpCustLoginlogService.getAllList(getRequest(),null,null);
		
		Type type=new TypeToken<List<BpCustLoginlog>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list3!=null?list3.size():0).append(",result:");
		buff.append(gson.toJson(list2, type));
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
				bpCustLoginlogService.remove(new Long(id));
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
		BpCustLoginlog bpCustLoginlog=bpCustLoginlogService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustLoginlog));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpCustLoginlog.getId()==null){
			bpCustLoginlogService.save(bpCustLoginlog);
		}else{
			BpCustLoginlog orgBpCustLoginlog=bpCustLoginlogService.get(bpCustLoginlog.getId());
			try{
				BeanUtil.copyNotNullProperties(orgBpCustLoginlog, bpCustLoginlog);
				bpCustLoginlogService.save(orgBpCustLoginlog);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
