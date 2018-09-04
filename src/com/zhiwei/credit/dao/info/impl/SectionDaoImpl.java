package com.zhiwei.credit.dao.info.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.info.SectionDao;
import com.zhiwei.credit.model.info.Section;

@SuppressWarnings("unchecked")
public class SectionDaoImpl extends BaseDaoImpl<Section> implements SectionDao{

	public SectionDaoImpl() {
		super(Section.class);
	}

	@Override
	public Integer getLastColumn() {
		final String hql = "select max(st.rowNumber) from Section st where st.colNumber = ? ";
		
		Integer maxRow = (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				query.setInteger(0,Section.COLUMN_ONE);
				return query.uniqueResult();
			}
		});
		if(maxRow == null){
			maxRow = 1;//为空是返回1
		}
		return maxRow;
	}

}