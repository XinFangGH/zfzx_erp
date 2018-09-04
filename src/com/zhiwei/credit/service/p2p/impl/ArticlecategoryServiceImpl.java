package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;



import org.hibernate.Hibernate;


import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.ArticlecategoryDao;
import com.zhiwei.credit.model.p2p.article.Article;
import com.zhiwei.credit.model.p2p.article.Articlecategory;
import com.zhiwei.credit.service.p2p.ArticlecategoryService;

/**
 * 
 * @author 
 *
 */
public class ArticlecategoryServiceImpl extends BaseServiceImpl<Articlecategory> implements ArticlecategoryService{
	@SuppressWarnings("unused")
	private ArticlecategoryDao dao;
	
	public ArticlecategoryServiceImpl(ArticlecategoryDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<Articlecategory> getByParentId(Long long1) {
		List<Articlecategory> list1= this.dao.getRootArticleCategoryList();
		return dao.getByParentId(long1);
		
		
	}

	@Override
	public List<Articlecategory> getRootArticleCategoryList() {
		List<Articlecategory> rootArticlecategoryList = this.dao.getRootArticleCategoryList();
		/*if (rootArticlecategoryList != null) {
			for (Articlecategory rootArticlecategory : rootArticlecategoryList) {
				Hibernate.initialize(rootArticlecategory);
			}
		}*/
		return rootArticlecategoryList;
	}
	
	
	
	
	public List<Articlecategory> getChildrenArticlecategoryList(Article article) {
		Articlecategory articlecategory = article.getArticlecategory();
		List<Articlecategory> ArticlecategoryList = this.getChildrenArticleCategoryList(articlecategory);
		if (ArticlecategoryList == null) {
			ArticlecategoryList = new ArrayList<Articlecategory>();
		}
		ArticlecategoryList.add(articlecategory);
		return ArticlecategoryList;
	}

	@Override
	public List<Articlecategory> getArticleCategoryPathList(
			Articlecategory articleCategory) {
		List<Articlecategory> ArticlecategoryPathList = new ArrayList<Articlecategory>();
		ArticlecategoryPathList.addAll(this.getParentArticleCategoryList(articleCategory));
		ArticlecategoryPathList.add(articleCategory);
		return ArticlecategoryPathList;
	}

	@Override
	public List<Articlecategory> getArticleCategoryPathList(Article article) {
		Articlecategory articlecategory = article.getArticlecategory();
		List<Articlecategory> articlecategoryList = new ArrayList<Articlecategory>();
		articlecategoryList.addAll(this.getParentArticleCategoryList(articlecategory));
		articlecategoryList.add(articlecategory);
		return articlecategoryList;
	}

	@Override
	public List<Articlecategory> getChildrenArticleCategoryList(
			Articlecategory articleCategory) {

		List<Articlecategory> childrenArticlecategoryList = dao.getChildrenArticlecategoryList(articleCategory);
		if (childrenArticlecategoryList != null) {
			for (Articlecategory childrenArticlecategory : childrenArticlecategoryList) {
				Hibernate.initialize(childrenArticlecategory);
			}
		}
		return childrenArticlecategoryList;
	
	}

	@Override
	public List<Articlecategory> getParentArticleCategoryList(
			Articlecategory articleCategory) {

		List<Articlecategory> parentArticlecategoryList = dao.getParentArticleCategoryList(articleCategory);
		if (parentArticlecategoryList != null) {
			for (Articlecategory parentArticlecategory : parentArticlecategoryList) {
				Hibernate.initialize(parentArticlecategory);
			}
		}
		return parentArticlecategoryList;
	
	}

	@Override
	public List<Articlecategory> getParentArticleCategoryList(Article article) {

		Articlecategory articlecategory = article.getArticlecategory();
		List<Articlecategory> articlecategoryList = new ArrayList<Articlecategory>();
		articlecategoryList.addAll(this.getParentArticleCategoryList(articlecategory));
		articlecategoryList.add(articlecategory);
		return articlecategoryList;
	
	}
	
	/*public List<Articlecategory> getArticlecategoryTreeList() {
		List<Articlecategory> allArticlecategoryList = this.getAll();
		return recursivArticlecategoryTreeList(allArticlecategoryList, null, null);
	}
	
	// 递归父类排序分类树
	private List<Articlecategory> recursivArticlecategoryTreeList(List<Articlecategory> allArticlecategoryList, Articlecategory p, List<Articlecategory> temp) {
		if (temp == null) {
			temp = new ArrayList<Articlecategory>();
		}
		for (Articlecategory Articlecategory : allArticlecategoryList) {
			Articlecategory parent = Articlecategory.getParent();
			if ((p == null && parent == null) || (Articlecategory != null && parent == p)) {
				temp.add(Articlecategory);
				if (Articlecategory.getChildren() != null && Articlecategory.getChildren().size() > 0) {
					recursivArticlecategoryTreeList(allArticlecategoryList, Articlecategory, temp);
				}
			}
		}
		return temp;
	}*/

}