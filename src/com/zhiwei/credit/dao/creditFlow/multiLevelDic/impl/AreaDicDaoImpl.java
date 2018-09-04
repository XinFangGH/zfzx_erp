package com.zhiwei.credit.dao.creditFlow.multiLevelDic.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.multiLevelDic.AreaDicDao;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;

@SuppressWarnings("unchecked")
public class AreaDicDaoImpl extends BaseDaoImpl<AreaDic> implements AreaDicDao{

	public AreaDicDaoImpl() {
		super(AreaDic.class);
	}

	@Override
	public AreaDic getById(Integer id) {
		String hql="from AreaDic as a where a.id=?";
		return (AreaDic) this.getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public List<AreaDic> listByLabelOrText(String lable, String text) {
		String hql="FROM AreaDic WHERE (lable = ? AND lable IS NOT NULL) OR (text = ? AND lable IS NOT NULL)";
		return this.findByHql(hql,new Object[]{lable,text});
	}

	@Override
	public List<AreaDic> listByParentId(Integer parentId) {
		String hql="from AreaDic AS a where a.parentId =? and a.isOld=0 order by a.orderid ";
		return this.findByHql(hql,new Object[]{parentId});
	}

	@Override
	public List<AreaDic> listByParIdAndText(Integer parentId, String text,
			Integer id) {
		String hql="FROM AreaDic WHERE parentId = ? AND text = ? AND id != ?";
		return this.findByHql(hql, new Object[]{parentId,text,id});
	}

	@Override
	public List<AreaDic> listByIdAndLabel(Integer id, String text, String label) {
		String hql="FROM AreaDic WHERE (id != ? AND text = ? AND lable IS NOT NULL) OR (id != ? AND lable = ? AND lable IS NOT NULL)";
		return this.findByHql(hql, new Object[]{id,text,id,label});
	}

	@Override
	public List<AreaDic> listByLabel(String label) {
		String hql="from AreaDic AS d where d.lable = ? order by d.orderid ";
		return this.findByHql(hql,new Object[]{label});
	}

	@Override
	public List<AreaDic> findLikeText(String likeText) {
		String hql="from AreaDic AS a where a.text like '%"+likeText+"%'";
		return this.findByHql(hql);
	}

	@Override
	public List<AreaDic> listByLabelAndIsOld(String lable) {
		List<AreaDic> list=null;
		lable = lable.trim();
		String hql = "FROM AreaDic WHERE 1=1 and isOld!=1";
		if(lable.length() != 0){
			String[] params = lable.split(",");
			if(params.length > 0){
				for(int i=0; i<params.length; i++){
					if(i == 0){
						hql += " AND lable ='"+params[i]+"'";
					}else{
						hql += " OR lable = '"+params[i]+"'";
					}
				}
			}
			list=this.findByHql(hql);
		}
		return list;
	}

	@Override
	public List<AreaDic> getByParentId(Integer parentId) {
		String hql="FROM AreaDic WHERE parentId = ?";
		return this.findByHql(hql,new Object[]{parentId});
	}
}