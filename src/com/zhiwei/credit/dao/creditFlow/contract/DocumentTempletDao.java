package com.zhiwei.credit.dao.creditFlow.contract;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.contract.DocumentTemplet;

public interface DocumentTempletDao extends BaseDao<DocumentTemplet>{
	public DocumentTemplet getById(Integer id);
	public DocumentTemplet getByIdTemId(String businessType);
	public List<DocumentTemplet> getByBusinessType(String businessType);
	public List<DocumentTemplet> getByParentId(int parentid);
	public List<DocumentTemplet> getListByText(String businessType,String text);
	public List<DocumentTemplet> getListByOnlymark(String onlymark);
	public List<DocumentTemplet> getByText(String text);
	public List<DocumentTemplet> getByTextAndId(String text,Integer id);
	public List<DocumentTemplet> getByMarkAndId(String mark,Integer id);
	public void updateTemplet(Integer parentId,Integer parentId1);
	public List<DocumentTemplet> listByParentId(Integer parentid);
	public List<DocumentTemplet> listByTemplettype(Integer templettype,PagingBean pb);
	public List<DocumentTemplet> listByMarkAndType(String mark,Integer templettype);
	public List<DocumentTemplet> listByexistAndType(Boolean isexist,Integer templettype,Integer parentId);
	public List<DocumentTemplet> listByParentAndType(Integer parentId,Integer templettype);
	public DocumentTemplet getByMarkAndBus(String onlymark,String businessType);
	public List<DocumentTemplet> getTempletIsDocument(String businessType,PagingBean pb);
	public List<DocumentTemplet> getTempletByParentId(int parentid,PagingBean pb);
	public List<DocumentTemplet> getDownLoadTemplet(String businessType,String type,String [] str,PagingBean pb);
	public DocumentTemplet findDocumentTemplet(String mark);
	public DocumentTemplet getTempletObj(int id);
}