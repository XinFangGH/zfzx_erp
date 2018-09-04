package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.hurong.credit.config.Pager;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.ArticleDao;
import com.zhiwei.credit.model.p2p.article.Article;
import com.zhiwei.credit.model.p2p.article.Articlecategory;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ArticleDaoImpl extends BaseDaoImpl<Article> implements ArticleDao{

	public ArticleDaoImpl() {
		super(Article.class);
	}
	
	@Override
	public List<Article> getRecommendArticleList(int maxResults) {
		String hql = "from Article as article where article.isPublication = ? and article.isRecommend = ? order by article.isTop desc, article.createDate desc";
		Object[] params={"1","1"};
		return findByHql(hql,params);
	}

	@Override
	public List<Article> getRecommendArticleList(Articlecategory articleCategory, int maxResults) {
		String hql = "from Article as article where article.isPublication = ? and article.isRecommend = ? and (articlecategory = ? or article.articlecategory.path like ?) order by article.isTop desc, article.createDate desc";
		
		Object[] params={"1","1",articleCategory,articleCategory.getPath() + "%"};
		return findByHql(hql,params);
	}

	@Override
	public List<Article> getHotArticleList(int maxResults) {
		String hql = "from Article as article where article.isPublication = ? order by article.hits desc, article.isTop desc, article.createDate desc";
		Object[] params={"1"};
		return findByHql(hql,params);
	}

	@Override
	public List<Article> getHotArticleList(Articlecategory articleCategory, int maxResults) {
		String hql = "from Article as article where article.isPublication = ? and (articlecategory = ? or article.articlecategory.path like ?) order by article.hits desc, article.isTop desc, article.createDate desc";
		
		Object[] params={"1",articleCategory,articleCategory.getPath() + "%"};
		return findByHql(hql,params);
		
	}
	
	@Override
	public List<Article> getNewArticleList(int maxResults) {
		String hql = "from Article as article where article.isPublication = ? order by article.createDate desc";
		
		Object[] params={"1"};
		return findByHql(hql,params);
	}

	@Override
	public List<Article> getNewArticleList(Articlecategory articleCategory, int maxResults) {
		String hql = "from Article as article where article.isPublication = ? and (articlecategory = ? or article.articlecategory.path like ?) order by article.createDate desc";
		
		Object[] params={"1",articleCategory,articleCategory.getPath() + "%"};
		return findByHql(hql,params);
	}
	@Override
	public List<Article> getArticleListByCat(Articlecategory articleCategory) {
		String hql = "from Article as article where articlecategory = ? or article.articlecategory.path like ? order by article.createDate desc";
		
		Object[] params={articleCategory,articleCategory.getPath() + "%"};
		return findByHql(hql,params);
	}
	@Override
	public Pager getArticlePager(Articlecategory articleCategory, Pager pager) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Article.class);
		detachedCriteria.createAlias("articleCategory", "articleCategory");
		detachedCriteria.add(Restrictions.or(Restrictions.eq("articleCategory", articleCategory), Restrictions.like("articleCategory.path", articleCategory.getPath() + "%")));
		detachedCriteria.add(Restrictions.eq("isPublication", true));
		return super.findByPager(pager, detachedCriteria);
	}
}