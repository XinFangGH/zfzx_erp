package com.zhiwei.credit.service.creditFlow.multiLevelDic;

import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;

public interface AreaDicService extends BaseService<AreaDic>{
	public AreaDic getById(Integer id);
	public void addDic(AreaDic area);
	public List<AreaDic> listByLabelOrText(String lable,String text);
	public void addDicType(AreaDic area);
	public List<AreaDic> listByParentId(Integer parentId);
	public List<AreaDic> listByParIdAndText(Integer parentId,String text,Integer id);
	public void updateDic(int id,String text,int parentId,int orderid,String remarks,int sourcepid,String remarks1);
	public void getRecordCountByParentID(int parentid);
	public List<AreaDic> listByIdAndLabel(Integer id,String text,String label);
	public void updateDicType(int id,String text,String lable,String remarks,int orderid);
	public void deleteDicType(int id);
	public void deleteById(int id);
	public void returnDicType(String idString);
	public List<AreaDic> listByLabel(String label);
	public void getDicTypeTree(String lable,int node);
	public List<AreaDic> findLikeText(String likeText);
	public void selectPersonAddress(int parentId);
	public List<AreaDic> listByLabelAndIsOld(String label);
	public List<AreaDic> getByParentId(Integer parentId);
	public List<AreaDic> getDictionaryTree(String lable);
	public List<AreaDic> queryDictionaryTree(int parentId);
	public List<AreaDic> queryIsLeaf(int id);
	public void getDictionaryTreeWindow(String lable, String node);
	public void getDictionarNotLastNodeyTreeWindow(String lable, String node);
	public List getNode(int id);
}