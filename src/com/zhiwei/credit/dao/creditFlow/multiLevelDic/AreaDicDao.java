package com.zhiwei.credit.dao.creditFlow.multiLevelDic;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;

public interface AreaDicDao extends BaseDao<AreaDic>{
	public AreaDic getById(Integer id);
	public List<AreaDic> listByLabelOrText(String lable,String text);
	public List<AreaDic> listByParentId(Integer parentId);
	public List<AreaDic> listByParIdAndText(Integer parentId,String text,Integer id);
	public List<AreaDic> listByIdAndLabel(Integer id,String text,String label);
	public List<AreaDic> listByLabel(String label);
	public List<AreaDic> findLikeText(String likeText);
	public List<AreaDic> listByLabelAndIsOld(String label);
	public List<AreaDic> getByParentId(Integer parentId);
}