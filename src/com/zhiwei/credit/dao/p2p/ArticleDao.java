package com.zhiwei.credit.dao.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;


import com.hurong.credit.config.Pager;
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.p2p.article.Article;
import com.zhiwei.credit.model.p2p.article.Articlecategory;

/**
 * 
 * @author 
 *
 */
public interface ArticleDao extends BaseDao<Article>{
	/**
	 * 根据最大返回数获取所有推荐文章(只包含isPublication=true的对象，不限分类)
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 所有推荐文章集合
	 */
	public List<Article> getRecommendArticleList(int maxResults);

	/**
	 * 根据ArticleCategory对象和最大返回数获取此分类下的所有推荐文章(只包含isPublication=true的对象，包含子分类文章)
	 * 
	 * @param articleCategory
	 *            文章分类
	 *            
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 此分类下的所有推荐文章集合
	 */
	public List<Article> getRecommendArticleList(Articlecategory articleCategory, int maxResults);
	
	/**
	 * 根据最大返回数获取所有热点文章(只包含isPublication=true的对象，不限分类)
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 所有热点文章集合
	 */
	public List<Article> getHotArticleList(int maxResults);

	/**
	 * 根据ArticleCategory对象和最大返回数获取此分类下的所有热点文章(只包含isPublication=true的对象，包含子分类文章)
	 * 
	 * @param articleCategory
	 *            文章分类
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 此分类下的所有热点文章集合
	 */
	public List<Article> getHotArticleList(Articlecategory articleCategory, int maxResults);
	
	/**
	 * 根据最大返回数获取最新文章(只包含isPublication=true的对象，不限分类)
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 最新文章集合
	 */
	public List<Article> getNewArticleList(int maxResults);

	/**
	 * 根据ArticleCategory对象和最大返回数获取此分类下的最新文章(只包含isPublication=true的对象，包含子分类文章)
	 * 
	 * @param articleCategory
	 *            文章分类
	 * 
	 * @param maxResults
	 *            最大返回数
	 * 
	 * @return 此分类下的最新文章集合
	 */
	public List<Article> getNewArticleList(Articlecategory articleCategory, int maxResults);
	
	/**
	 * 根据ArticleCategory对象返回分类 文章
	 * 
	 * @param articleCategory
	 *            文章分类
	 * 
	 * 
	 * @return 此分类下的文章集合
	 */
	public List<Article> getArticleListByCat(Articlecategory articleCategory);

	/**
	 * 根据ArticleCategory和Pager对象，获取此分类下的文章分页对象（只包含isPublication=true的对象，包含子分类文章）
	 * 
	 * @param articleCategory
	 *            文章分类
	 *            
	 * @param pager
	 *            分页对象
	 * 
	 * @return Pager
	 */

	public Pager getArticlePager(Articlecategory articleCategory, Pager pager);
}