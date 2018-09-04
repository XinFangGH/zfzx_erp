package com.zhiwei.credit.dao.creditFlow.creditmanagement;

import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Indicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.IndicatorStore;

public interface IndicatorDao extends BaseDao<Indicator> {

	public List getIndicatorList(int indicatorTypeId, String indicatorType,
			String indicatorName, String creater, int start, int limit,String type);

	public int getIndicatorListNum(int indicatorTypeId, String indicatorType,
			String indicatorName, String creater,String type);

	public boolean addIndicator(Indicator indicator);

	public List<Indicator> getIndicatorList(int parseInt,String type);

	public List<IndicatorStore> getIndicatorStoreList(int parseInt);

	public Indicator getIndicator(int id);

	public boolean updateIndicator(Indicator i);

	public boolean deleteIndicator(int id);

	public List getOptionsList(int indicatorId);

	public List getTemplateIndicator(int templateId);

	// 得到所有的指标
	public List<Indicator> getAllIndicator(int start, int limit,String type);

	public int getAllIndicatorNum(String type);

	public List<Indicator> getIndicatorByType(int parentId, int start, int limit,String type);

	public int getIndicatorNum(int parentId,String type);

	public int getMaxStore(int indicatorTypeId);

	public int getMinStore(int indicatorTypeId);

	public int getMaxScore(int indicatorId);

	public int getMinScore(int indicatorId);

	public List<Indicator> getIndicatorByType(int start, int limit, String type);

	public int getMaxStore(int indicatorTypeId, String type);

	public int getMinStore(int indicatorTypeId, String type);
	public Object[] getIndicatorForLX(String type,int indicatorTypeId,int  start,int  limit);
	public Object[] getIndicatorForLXP(String type,int indicatorTypeId,int  start,int  limit);
	
	public List getOptionsListOrder(int indicatorId,String s);
	
	public Map getByIndicatorId(int indicatorId);
	public List<IndicatorStore> getIndicatorStoreList(int parseInt,String type);

	public List<Indicator> getIndicatorListByType(int parseInt, String type,
			String operationType);

	public String getSystemCreditelementCode(int customerId,
			String operationType, String elementCode);


}
