package com.zhiwei.credit.dao.creditFlow.creditmanagement;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.IndicatorStore;

public interface IndicatorStoreDao extends BaseDao<IndicatorStore>{

	public List<IndicatorStore> getIndicatorStoreList(int parentId,String type);
	public List<IndicatorStore> getIndicatorStoreListDX(int parentId);
	public List<IndicatorStore> getIndicatorStoreListDL(int parentId);
	public boolean addIndicatorStrore(IndicatorStore indicatorStore);

	public IndicatorStore getIndicatorStore(int id);

	public boolean updateIndicatorStore(IndicatorStore is);

	public boolean deleteIndicatorStore(int id);

	public List<IndicatorStore> getIndicatorStoreList();

}
