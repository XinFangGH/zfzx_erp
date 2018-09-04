package com.zhiwei.credit.service.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.p2p.article.Article;
import com.zhiwei.credit.model.p2p.article.Articlecategory;

/**
 * 
 * @author 
 *
 */
public interface ArticlecategoryService extends BaseService<Articlecategory>{

	List<Articlecategory> getByParentId(Long long1);
	
	/**
	 * 获取所有顶级文章分类集合;
	 * 
	 * @return 所有顶级文章分类集合
	 * 
	 */
	public List<Articlecategory> getRootArticleCategoryList();
	
	/**
	 * 根据ArticleCategory对象获取所有父类集合，若无父类则返回null;
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<Articlecategory> getParentArticleCategoryList(Articlecategory articleCategory);
	
	/**
	 * 根据Article对象获取所有父类集合，若无父类则返回null;
	 * 
	 * @return 父类集合
	 * 
	 */
	public List<Articlecategory> getParentArticleCategoryList(Article article);
	
	/**
	 * 根据ArticleCategory对象获取路径集合;
	 * 
	 * @return ArticleCategory集合
	 * 
	 */
	public List<Articlecategory> getArticleCategoryPathList(Articlecategory articleCategory);
	
	/**
	 * 根据Article对象获取路径集合;
	 * 
	 * @return ArticleCategory集合
	 * 
	 */
	public List<Articlecategory> getArticleCategoryPathList(Article article);
	
	/**
	 * 根据ArticleCategory对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<Articlecategory> getChildrenArticleCategoryList(Articlecategory articleCategory);
	
	

}


