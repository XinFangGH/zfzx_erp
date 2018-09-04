package com.zhiwei.credit.action.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.sql.BatchUpdateException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hurong.core.util.JsonUtil;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.p2p.article.Articlecategory;
import com.zhiwei.credit.service.p2p.ArticlecategoryService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class ArticlecategoryAction extends BaseAction{
	@Resource
	private ArticlecategoryService articlecategoryService;
	private Articlecategory articlecategory;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Articlecategory getArticlecategory() {
		return articlecategory;
	}

	public void setArticlecategory(Articlecategory articlecategory) {
		this.articlecategory = articlecategory;
	}
	/**
	 * 产生树
	 * @return
	 */
	public String tree(){
		StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'顶级分类',expanded:true,children:[");
		List<Articlecategory> rticlecategoryList = articlecategoryService.getByParentId(new Long(0l));
		for(Articlecategory type:rticlecategoryList){
			buff.append("{id:'"+type.getId()).append("',text:'"+type.getName()).append("',");
			 buff.append(getChildType(type.getId()));
		}
		
		if (!rticlecategoryList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");

		setJsonString(buff.toString());
		
		logger.info("tree json:" + buff.toString());		
		return SUCCESS;
	}
	
	public String getChildType(Long parentId){
		StringBuffer buff = new StringBuffer();
		List<Articlecategory> typeList = articlecategoryService.getByParentId(parentId);
		if(typeList.size() == 0){
			buff.append("leaf:true,expanded:true},");
			return buff.toString(); 
		}else {
			buff.append("expanded:true,children:[");
			for(Articlecategory type:typeList){
				buff.append("{id:'"+type.getId()).append("',text:'"+type.getName()).append("',");
				
			    buff.append(getChildType(type.getId()));
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		}
	}

	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<Articlecategory> list= articlecategoryService.getAll(filter);
		
		
		
		Type type=new TypeToken<List<Articlecategory>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
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
		try {
			if (ids != null) {
				for (String id : ids) {
					articlecategoryService.remove(new Long(id));
				}
			}
			jsonString="{success:true}";
		} catch (Exception e) {
			// TODO: handle exception
			jsonString="{success:false}";
		}
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		Articlecategory articlecategory=articlecategoryService.get(id);
		
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer(
				 "bidTime", "createTime",
				"updateTime","loanStarTime","loanEndTime");
		sb.append(serializer.exclude(
				new String[] { "class"}).serialize(
						articlecategory));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 保存二级分类
	 */
	public String save(){
		if(articlecategory.getParentId()!=null&&!"".equals(articlecategory.getParentId())){//顶级分类不能为空
			Articlecategory s=articlecategoryService.get(articlecategory.getParentId());
			if(s!=null){//不能没有该类型
				if(articlecategory.getId()==null){
					//确保类型的Key值不能重复
					if(!articlecategoryService.isExist("cateKey", articlecategory.getCateKey())){
						articlecategory.setCreateDate(new Date());
						articlecategory.setModifyDate(new Date());
						articlecategory.setWebKey(s.getWebKey());
						articlecategory.setParentName(s.getName());
						articlecategory.setDepth(2);
						articlecategory.setIsShow("1");
						articlecategoryService.save(articlecategory);
					}else{
						
					}
					
				}else{
					Articlecategory orgArticlecategory=articlecategoryService.get(articlecategory.getId());
					//确保类型的Key值不能重复
					if(articlecategory.getCateKey().equals(orgArticlecategory.getCateKey())||!articlecategoryService.isExist("cateKey", articlecategory.getCateKey())){
						try{
							BeanUtil.copyNotNullProperties(orgArticlecategory, articlecategory);
							orgArticlecategory.setModifyDate(new Date());
							orgArticlecategory.setWebKey(s.getWebKey());
							orgArticlecategory.setParentName(s.getName());
							orgArticlecategory.setDepth(2);
							orgArticlecategory.setIsShow("1");
							articlecategoryService.merge(orgArticlecategory);
						}catch(Exception ex){
							logger.error(ex.getMessage());
						}
					}else{
						
					}

					
				}
			}else{
				
			}
			
		}else{
			
		}
		
		
		setJsonString("{success:true}");
		return SUCCESS;
	}
	//顶级分类保存方法
	public String saveTop(){
		if(articlecategory.getId()==null){
			//确保类型的Key值不能重复
			if(!articlecategoryService.isExist("cateKey", articlecategory.getCateKey())){
				articlecategory.setCreateDate(new Date());
				articlecategory.setModifyDate(new Date());
				articlecategory.setDepth(1);
				articlecategory.setIsShow("1");
				articlecategory.setOrderList(1);
				articlecategory.setParentName("顶级分类");
				articlecategoryService.save(articlecategory);
			}else{
				
			}
			
			
		}else{
			Articlecategory orgArticlecategory=articlecategoryService.get(articlecategory.getId());
			//确保类型的Key值不能重复
			if(articlecategory.getCateKey().equals(orgArticlecategory.getCateKey())||!articlecategoryService.isExist("cateKey", articlecategory.getCateKey())){
				try{
					BeanUtil.copyNotNullProperties(orgArticlecategory, articlecategory);
					orgArticlecategory.setModifyDate(new Date());
					orgArticlecategory.setDepth(1);
					orgArticlecategory.setIsShow("1");
					orgArticlecategory.setOrderList(1);
					orgArticlecategory.setParentName("顶级分类");
					articlecategoryService.merge(orgArticlecategory);
				}catch(Exception ex){
					logger.error(ex.getMessage());
				}
			}else{
				
			}
			
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 查询出来顶级的数据分类类型
	 * @return
	 */
	public String getList(){
		QueryFilter filter=new QueryFilter(getRequest());
		
		if(this.getRequest().getParameter("type")!=null){
			filter.addFilter("Q_parentId_L_GT", "0");
		}else{
			filter.addFilter("Q_parentId_L_EQ", "0");
		}
		filter.getPagingBean().setPageSize(500);
		List<Articlecategory> rticlecategoryList = articlecategoryService.getAll(filter);
		StringBuffer buff = new StringBuffer("[");
		if(null!=rticlecategoryList && rticlecategoryList.size()>0){
			for(Articlecategory type:rticlecategoryList){
				buff.append("[").append(type.getId()).append(",'")
				.append(type.getName()).append("','").append(type.getWebKey()).append("'],");
			}
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
}
