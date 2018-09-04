package com.zhiwei.credit.dao.creditFlow.contract.impl;

import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.contract.DocumentTempletDao;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;

@SuppressWarnings("unchecked")
public class DocumentTempletDaoImpl extends BaseDaoImpl<DocumentTemplet> implements DocumentTempletDao{

	public DocumentTempletDaoImpl() {
		super(DocumentTemplet.class);
		
	}

	@Override
	public DocumentTemplet getById(Integer id) {
		String hql="from DocumentTemplet as d where id=?";
		return (DocumentTemplet) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}

	@Override
	public DocumentTemplet getByIdTemId(String businessType) {
		String hql="from DocumentTemplet AS t where t.businessType=? order by t.id";
		DocumentTemplet dtem = null ;
		List<DocumentTemplet> list=this.findByHql(hql, new Object[]{businessType});
		if(null!=list && list.size()>0){
			dtem=list.get(0);
		}
		return dtem;
	}

	@Override
	public List<DocumentTemplet> getByBusinessType(String businessType) {
		String hql="from DocumentTemplet AS dt where dt.businessType=? and dt.isChild is null and dt.parentid=0";
		return this.findByHql(hql, new Object[]{businessType});
	}

	@Override
	public List<DocumentTemplet> getByParentId(int parentid) {
		String hql="from DocumentTemplet AS dt where (dt.isChild <>2 and dt.isChild<>10) and dt.parentid=?";
		return this.findByHql(hql,new Object[]{parentid});
	}

	@Override
	public List<DocumentTemplet> getListByOnlymark(String onlymark) {
		String hql="from DocumentTemplet AS t where t.onlymark=?";
		return this.findByHql(hql,new Object[]{onlymark});
	}

	@Override
	public List<DocumentTemplet> getListByText(String businessType, String text) {
		String hql="from DocumentTemplet AS t where t.businessType = ? and t.text=?";
		return this.findByHql(hql,new Object[]{businessType,text});
	}

	@Override
	public List<DocumentTemplet> getByText(String text) {
		String hql="from DocumentTemplet AS t where t.text=?";
		return this.findByHql(hql,new Object[]{text});
	}

	@Override
	public List<DocumentTemplet> getByMarkAndId(String mark, Integer id) {
		String hql="from DocumentTemplet AS t where t.onlymark=? and t.id !=?";
		return this.findByHql(hql, new Object[]{mark,id});
	}

	@Override
	public List<DocumentTemplet> getByTextAndId(String text, Integer id) {
		String hql="from DocumentTemplet AS t where t.text=? and t.id != ?";
		return this.findByHql(hql, new Object[]{text,id});
	}

	@Override
	public void updateTemplet(Integer parentId, Integer parentId1) {
		String hql="update DocumentTemplet set parentid=? where parentid=?";
		this.getSession().createQuery(hql).setParameter(0, parentId).setParameter(1, parentId1).executeUpdate();
		
	}

	@Override
	public List<DocumentTemplet> listByParentId(Integer parentid) {
		String hql="from DocumentTemplet AS dt where dt.parentid=?";
		return this.findByHql(hql,new Object[]{parentid});
	}

	@Override
	public List<DocumentTemplet> listByTemplettype(Integer templettype,
			PagingBean pb) {
		String hql="from DocumentTemplet AS dt where dt.templettype != ?";
		List<DocumentTemplet> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{templettype});
		}else{
			list=this.findByHql(hql, new Object[]{templettype},pb);
		}
		return list;
	}

	@Override
	public List<DocumentTemplet> listByMarkAndType(String mark,
			Integer templettype) {
		String hql="from DocumentTemplet AS dt where dt.onlymark=? and dt.templettype=?";
		return this.findByHql(hql, new Object[]{mark,templettype});
	}

	@Override
	public List<DocumentTemplet> listByParentAndType(Integer parentId,
			Integer templettype) {
		String hql="from DocumentTemplet AS dt where dt.parentid=? and dt.templettype=? and dt.leaf = false";
		return this.findByHql(hql, new Object[]{parentId,templettype});
	}

	@Override
	public List<DocumentTemplet> listByexistAndType(Boolean isexist,
			Integer templettype,Integer parentId) {
		String hql="from DocumentTemplet AS dt where dt.parentid=? and dt.templettype=? and dt.isexist=?";
		return this.findByHql(hql,new Object[]{parentId,templettype,isexist});
	}

	@Override
	public DocumentTemplet getByMarkAndBus(String onlymark, String businessType) {
		String hql="from DocumentTemplet AS dt where dt.onlymark=? and dt.businessType=?";
		List<DocumentTemplet> list=this.findByHql(hql,new Object[]{onlymark,businessType});
		DocumentTemplet doc=null;
		if(null!=list && list.size()>0){
			doc=list.get(0);
		}
		return doc;
	}

	@Override
	public List<DocumentTemplet> getTempletIsDocument(String businessType,
			PagingBean pb) {
		String hql="from DocumentTemplet AS dt where dt.businessType = ? and dt.isChild <>10 and (dt.handleFun is not null and dt.handleFun <>'')";
		List<DocumentTemplet> list=null;
		if(null==pb){
			list=this.findByHql(hql, new Object[]{businessType});
		}else{
			list=this.findByHql(hql, new Object[]{businessType},pb);
		}
		return list;
	}

	@Override
	public List<DocumentTemplet> getTempletByParentId(int parentid, PagingBean pb) {
		String hql="from DocumentTemplet AS dt where dt.parentid=?";
		List<DocumentTemplet> list=null;
		if(null==pb){
			list=this.findByHql(hql,new Object[]{parentid});
		}else{
			list=this.findByHql(hql,new Object[]{parentid},pb);
		}
		return list;
	}

	@Override
	public List<DocumentTemplet> getDownLoadTemplet(String businessType,
			String type, String[] str, PagingBean pb) {
		String hql="from DocumentTemplet AS dt where dt.businessType = '"+businessType+"' and dt.isChild =10 and (dt.handleFun is not null and dt.handleFun <>'')";
		List<DocumentTemplet> list=null;
		if(null==pb){
			list=this.findByHql(hql,new Object[]{});
		}else{
			list=this.findByHql(hql,new Object[]{},pb);
		}
		return list;
	}

	@Override
	public DocumentTemplet findDocumentTemplet(String mark) {
		String hql="from DocumentTemplet AS t where t.onlymark=? order by t.id";
		List<DocumentTemplet> list=this.findByHql(hql,new Object[]{mark});
		DocumentTemplet doc=null;
		if(null!=list && list.size()>0){
			doc=list.get(0);
		}
		return doc;
	}

	@Override
	public DocumentTemplet getTempletObj(int id) {
		DocumentTemplet tem = null ;
		String hql = "from DocumentTemplet AS dt where dt.parentid=?" ;
		List<DocumentTemplet> list = this.findByHql(hql,new Object[]{id});
		if(list != null){
			if(list.size()>1){
				tem = (DocumentTemplet)list.get(list.size()-1);
			}else{
				tem = (DocumentTemplet)list.get(0);
			}
			
		}
		return tem;
		
	}
}