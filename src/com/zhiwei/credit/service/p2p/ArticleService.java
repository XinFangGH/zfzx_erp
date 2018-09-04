package com.zhiwei.credit.service.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.hurong.credit.config.Pager;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.p2p.article.Article;
import com.zhiwei.credit.model.p2p.article.Articlecategory;

/**
 * 
 * @author 
 *
 */
public interface ArticleService extends BaseService<Article>{
	public Pager getArticlePager(Articlecategory articleCategory, Pager pager);
}


