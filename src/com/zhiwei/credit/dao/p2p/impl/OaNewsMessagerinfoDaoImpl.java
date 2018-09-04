package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.p2p.OaNewsMessagerinfoDao;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.p2p.OaNewsMessagerinfo;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class OaNewsMessagerinfoDaoImpl extends BaseDaoImpl<OaNewsMessagerinfo> implements OaNewsMessagerinfoDao{

	public OaNewsMessagerinfoDaoImpl() {
		super(OaNewsMessagerinfo.class);
	}

	@Override
	public List<OaNewsMessagerinfo> getAllInfo(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String hql="SELECT "+
						"p.id as id, "+
						"p.userId as userId, "+
						"p.userType as userType, "+
						"p.userName as userName, "+
						"p.`status` as `status`, "+
						"p.readStatus as readStatus, "+
						"p.readTime as readTime, "+
						"p.istop as istop, "+
						"p.isTopTime as isTopTime, "+
						"p.messageId as messageId,  "+
						"o.title as title, "+
						"o.content as content, "+
						"o.operator as operator, "+
						"o.addresser as addresser, "+
						"o.typename as typename, "+
						"o.sendTime as sendTime "+
					"FROM "+
						"`oa_news_messagerinfo` AS p "+
						"LEFT JOIN oa_news_message AS o ON (o.id = p.messageId) where 1=1 ";
		String  status=request.getParameter("status");
		if(status!=null&&!"".equals(status)){
			if("1".equals(status)){//删除成功
				hql=hql+" and  p.status=1";
			}else if("2".equals(status)){//收件
				hql=hql+" and  p.status=2";
			}
		}
		
		String  useType=request.getParameter("useType");
		if(useType!=null&&!"".equals(useType)){
			if("0".equals(useType)){//收件成功
				hql=hql+" and p.userId is NULL  ";
			}else if("1".equals(useType)){//已删除
				hql=hql+" and   p.userId is not NULL  ";
			}
		}
		
		 String messageId=request.getParameter("messageId");
		 if(messageId!=null&&!"".equals(messageId)){
			 hql=hql+" and  p.messageId="+Long.valueOf(messageId);
		 }
		 String userName=request.getParameter("userName");
		 if(userName!=null&&!"".equals(userName)){
			 hql=hql+" and  p.userName like'%"+userName+"%'";
		 }
		 hql = hql+"ORDER BY o.sendTime DESC";
		// System.out.println(hql);
		 List<OaNewsMessagerinfo> listcount=this.getSession().createSQLQuery(hql).
		 addScalar("id", Hibernate.LONG).
		 addScalar("userType", Hibernate.STRING).
		 addScalar("userName", Hibernate.STRING).
		 addScalar("status", Hibernate.INTEGER).
		 addScalar("readStatus", Hibernate.INTEGER).
		 addScalar("readTime", Hibernate.DATE).
		 addScalar("istop", Hibernate.INTEGER).
		 addScalar("isTopTime", Hibernate.DATE).
		 addScalar("messageId", Hibernate.LONG).
		 addScalar("title", Hibernate.STRING).
		 addScalar("content", Hibernate.STRING).
		 addScalar("operator", Hibernate.STRING).
		 addScalar("addresser", Hibernate.STRING).
		 addScalar("typename", Hibernate.STRING).
		 addScalar("sendTime", Hibernate.DATE).
		 setResultTransformer(Transformers.aliasToBean(OaNewsMessagerinfo.class)).
		 list();
		 pb.setTotalItems(listcount.size());
		 if(pb.getStart()!=null&&pb.getPageSize()!=null){
			 List<OaNewsMessagerinfo> list=this.getSession().createSQLQuery(hql).
										 addScalar("id", Hibernate.LONG).
										 addScalar("userType", Hibernate.STRING).
										 addScalar("userName", Hibernate.STRING).
										 addScalar("status", Hibernate.INTEGER).
										 addScalar("readStatus", Hibernate.INTEGER).
										 addScalar("readTime", Hibernate.DATE).
										 addScalar("istop", Hibernate.INTEGER).
										 addScalar("isTopTime", Hibernate.DATE).
										 addScalar("messageId", Hibernate.LONG).
										 addScalar("title", Hibernate.STRING).
										 addScalar("content", Hibernate.STRING).
										 addScalar("operator", Hibernate.STRING).
										 addScalar("addresser", Hibernate.STRING).
										 addScalar("typename", Hibernate.STRING).
										 addScalar("sendTime", Hibernate.DATE).
										 setResultTransformer(Transformers.aliasToBean(OaNewsMessagerinfo.class)).
										 setFirstResult(pb.getStart()).
										 setMaxResults(pb.getPageSize()).list();
			 return list;
		 }else{
			 return listcount;
		 }
	}

}