package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.hurong.credit.config.HtmlConfig;
import com.hurong.credit.config.Pager;
import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.p2p.ArticleDao;
import com.zhiwei.credit.model.p2p.article.Article;
import com.zhiwei.credit.model.p2p.article.Articlecategory;
import com.zhiwei.credit.service.p2p.ArticleService;
import com.zhiwei.credit.service.p2p.ArticlecategoryService;
import com.zhiwei.credit.service.system.BuildHtml2Web;
import com.zhiwei.credit.service.system.ResultWebPmsService;
import com.zhiwei.credit.util.JsonUtils;

/**
 * 
 * @author 
 *
 */
public class ArticleServiceImpl extends BaseServiceImpl<Article> implements ArticleService{
	@Resource
	private ArticlecategoryService articlecategoryService;
	@SuppressWarnings("unused")
	private ArticleDao dao;
	@Resource
	private ResultWebPmsService resultWebPmsService;
	@Resource
	private BuildHtml2Web buildHtml2WebService;
	public ArticleServiceImpl(ArticleDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	
	@Override
	public Article save(Article article) {
		article = dao.save(article);
		return article;
	}
	// 重写方法，保存对象的同时生成HTML静态文件
	/*@Override
	public Article save(Article article) {
		String singleName="";
		if(article.getCol()!=null&&!article.getCol().equals("")){
			singleName=article.getCol();
		}else{
			singleName="default";
		}
		Article articleNew=new Article();
		article.setPageCount(0);
		HtmlConfig htmlConfig =null;
		if(article.getSingle()==0){ //单页面
			String params="";
			params=HtmlConfig.ARTICLE_SINGLE+"_"+singleName;
			htmlConfig = resultWebPmsService.findSingleHtmlCon(params);
		}else{//内容页面
			htmlConfig = resultWebPmsService.findHtmlCon(HtmlConfig.ARTICLE_CONTENT);
		}
		if(article.getHtmlFilePath()==null||article.getHtmlFilePath().equals("")){
			String htmlFilePath = htmlConfig.getHtmlFilePath();
		article.setHtmlFilePath(htmlFilePath);
		}
		articleNew = dao.save(article);
		dao.flush();
		dao.evict(article);
		article = dao.get(articleNew.getId());
		
		if (article.getIsPublication().equals("1")) {
			buildContent(article,htmlConfig);
		}
		buildFootHtml();
		return article;
	}*/
	@Override
	public Pager getArticlePager(Articlecategory articleCategory, Pager pager) {
		return dao.getArticlePager(articleCategory, pager);
	}
	public void buildFootHtml(){
		//List<Article> list = new ArrayList<Article>();
		Map<String,List<Article>> mapp = new HashMap<String,List<Article>>();
		HtmlConfig htmlConfig = null;
		List<Articlecategory>  categoryList = articlecategoryService.getAll();
		if(categoryList!=null&&categoryList.size()!=0){
			for(Articlecategory category:categoryList)
			if(category!=null){
				List<Article> list = dao.getArticleListByCat(category);
				mapp.put(category.getName(), list);
			}
		}
		if(mapp.size()!=0){
			htmlConfig = resultWebPmsService.findHtmlCon("footPage");
			String templateFilePath = htmlConfig.getTemplateFilePath();
			String htmlFilePath = htmlConfig.getHtmlFilePath();
			Map<String, Object> data = buildHtml2WebService.getCommonData();
			data.put("sitemap", JsonUtils.getJson(mapp, JsonUtils.TYPE_OBJ));
			data.put("htmlFilePath", htmlFilePath);
			data.put("templateFilePath", templateFilePath);
			buildHtml2WebService.buildHtml(Constants.BUILDHTML_FORMAT_JSON,AppUtil.getWebServiceUrlRs(), "htmlService","signSchemeContentBuildHtml", data);
		}
	}
	
	public void buildContent(Article article,HtmlConfig htmlConfig){
		Map<String, Object> data = buildHtml2WebService.getCommonData();
		Articlecategory articleCategory=article.getArticlecategory();
		data.put("articleCat", JsonUtils.getJson(articleCategory,JsonUtils.TYPE_OBJ));
		data.put("article", JsonUtils.getJson(article,JsonUtils.TYPE_OBJ));
		data.put("pathList", JsonUtils.getJson(articlecategoryService.getArticleCategoryPathList(article),JsonUtils.TYPE_LIST));
		data.put("rootArticleCategoryList", JsonUtils.getJson(articlecategoryService.getRootArticleCategoryList(),JsonUtils.TYPE_LIST));
		data.put("recommendArticleList", JsonUtils.getJson(dao.getRecommendArticleList(articleCategory, Article.MAX_RECOMMEND_ARTICLE_LIST_COUNT),JsonUtils.TYPE_LIST));
		data.put("hotArticleList", JsonUtils.getJson(dao.getHotArticleList(articleCategory, Article.MAX_HOT_ARTICLE_LIST_COUNT),JsonUtils.TYPE_LIST));
		data.put("newArticleList", JsonUtils.getJson(dao.getNewArticleList(articleCategory, Article.MAX_NEW_ARTICLE_LIST_COUNT),JsonUtils.TYPE_LIST));
		data.put("newArticleListByCat", JsonUtils.getJson(dao.getArticleListByCat(articleCategory),JsonUtils.TYPE_LIST));
		
		String htmlFilePath = article.getHtmlFilePath();
		String prefix = StringUtils.substringBeforeLast(htmlFilePath, ".");
		String extension = StringUtils.substringAfterLast(htmlFilePath, ".");
		List<String> pageContentList = article.getPageContentList();
		article.setPageCount(pageContentList.size());
		dao.save(article);
		dao.flush();
		for (int i = 0; i < pageContentList.size(); i++) {
			data.put("content", pageContentList.get(i));
			data.put("pageNumber", i + 1);
			data.put("pageCount", pageContentList.size());
			String templateFilePath = htmlConfig.getTemplateFilePath();
			String currentHtmlFilePath = null;
			if (i == 0) {
				currentHtmlFilePath = htmlFilePath;
			} else {
				currentHtmlFilePath = prefix + "_" + (i + 1) + "." + extension;
			}
			data.put("templateFilePath", templateFilePath);
			data.put("currentHtmlFilePath", currentHtmlFilePath);
			buildHtml2WebService.buildHtml(Constants.BUILDHTML_FORMAT_JSON, AppUtil.getWebServiceUrlRs(), "htmlService", "articleContentBuildHtml", data);
		}
	}
}