package com.zhiwei.credit.action.creditFlow.contract;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.contract.CsElementCategory;
import com.zhiwei.credit.service.creditFlow.contract.CsElementCategoryService;
/**
 * 
 * @author 
 *
 */
public class CsElementCategoryAction extends BaseAction{
	@Resource
	private CsElementCategoryService csElementCategoryService;
	private CsElementCategory csElementCategory; 
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsElementCategory getCsElementCategory() {
		return csElementCategory;
	}

	public void setCsElementCategory(CsElementCategory csElementCategory) {
		this.csElementCategory = csElementCategory;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<CsElementCategory> list= csElementCategoryService.getAll(filter);
		
		Type type=new TypeToken<List<CsElementCategory>>(){}.getType();
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
				csElementCategoryService.remove(new Long(id));
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
		CsElementCategory csElementCategory=csElementCategoryService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(csElementCategory));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(csElementCategory.getId()==null){
			csElementCategoryService.save(csElementCategory);
		}else{
			CsElementCategory orgCsElementCategory=csElementCategoryService.get(csElementCategory.getId());
			try{
				BeanUtil.copyNotNullProperties(orgCsElementCategory, csElementCategory);
				csElementCategoryService.save(orgCsElementCategory);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
