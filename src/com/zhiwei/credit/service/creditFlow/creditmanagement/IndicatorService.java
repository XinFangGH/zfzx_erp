package com.zhiwei.credit.service.creditFlow.creditmanagement;





public interface IndicatorService {

	Object[] getListByMidId(String type, int indicatorTypeId, int start,
			int limit);

	Object[] getListByLastId(String type, int indicatorTypeId, int start,
			int limit);
	
}
