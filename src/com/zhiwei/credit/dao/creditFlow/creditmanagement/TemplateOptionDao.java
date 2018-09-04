package com.zhiwei.credit.dao.creditFlow.creditmanagement;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions;

public interface TemplateOptionDao extends BaseDao<TemplateOptions> {

	public List getOptionListByIndicatorId(int templateIndicatorId);

	public boolean addTemplateOption(TemplateOptions templateOption);

	public boolean deleteTemplateOption(int id);

	public boolean updateTemplateOption(TemplateOptions templateOption);

}
