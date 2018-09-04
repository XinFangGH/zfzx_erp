package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;


import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.ArticlecategoryDao;
import com.zhiwei.credit.model.p2p.article.Articlecategory;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ArticlecategoryDaoImpl extends BaseDaoImpl<Articlecategory> implements ArticlecategoryDao{

	public ArticlecategoryDaoImpl() {
		super(Articlecategory.class);
	}

	@Override
	public List<Articlecategory> getByParentId(Long long1) {
		String hql=" from Articlecategory ac where ac.parentId = ?";
		return findByHql(hql, new Object[]{long1});
	}

	@Override
	public List<Articlecategory> getRootArticleCategoryList() {
		String hql = "from Articlecategory articleCategory where articleCategory.parentId>=0 order by articleCategory.orderList asc";
		return findByHql(hql);
	}
	
	@Override
	public List<Articlecategory> getParentArticleCategoryList(Articlecategory articleCategory) {
		//String hql = "from Articlecategory articleCategory where articleCategory != ? and articleCategory.id in(?) order by articleCategory.orderList asc";
		StringBuffer buff=new StringBuffer("from Articlecategory articleCategory where articleCategory != ? and articleCategory.id in(");
		
		String[] ids = articleCategory.getPath().split(articleCategory.PATH_SEPARATOR);
		for(int i=0;i<ids.length;i++){
			buff.append(ids[i]);
			buff.append(",");
		}
		buff.deleteCharAt(buff.length()-1);
		buff.append(")");
		buff.append(" order by articleCategory.orderList asc");
		String hql=buff.toString();
		Object[] params ={articleCategory};
		return findByHql(hql, params);
	}
	


	@Override
	public List<Articlecategory> getChildrenArticlecategoryList(
			Articlecategory articlecategory) {
		String hql = "from ArticleCategory articleCategory where articleCategory != ? and articleCategory.path like ? order by articleCategory.orderList asc";
		Object[] params ={articlecategory,articlecategory.getPath() + "%"};
		return findByHql(hql, params);

	}
}