package com.zhiwei.credit.dao.creditFlow.creditmanagement;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.Indicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.IndicatorStore;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingTemplate;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateIndicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions;

public interface RatingTemplateDao extends BaseDao<RatingTemplate>{

	public List<RatingTemplate> getRatingTemplateList();

	public int getRatingTemplateNum();

	public void addRatingTemplate(RatingTemplate ratingTemplate);

	public List<IndicatorStore> getTemplateIndicatorStoreList(int parseInt,
			int templateId);

	public List<TemplateIndicator> getTemplateIndicatorList(int parseInt,
			int templateId);

	public Indicator getIndicator(int parseInt);

	public java.io.Serializable addTemplateIndicator(TemplateIndicator ti);

	public List getOptionList(int parseInt);

	public boolean addTemplateOptions(TemplateOptions o);

	public RatingTemplate getRatingTemplate(int id);

	public boolean updateRatingTemplate(RatingTemplate rt);

	public boolean deleteRatingTemplate(int id);

	public boolean subTemplateIndicatorSuccess(int id);

	public int countTemplateScore(int id);

	public boolean deleteTemplateIndicator(int indicatorId);

	public List<TemplateIndicator> templateContentList(int id);

	public List getOptionsList(int indicatorId);

	public List<TemplateIndicator> getTemplateIndicator(int id);

	public int getNewTemplateId();

	public boolean updateTemplateIndicator(TemplateIndicator ti);

	public int getMaxScore(int templateId);

	public int getMinScore(int templateId);

	public int getMaxScoreOfOption(int indicatorId);

	public int getMinScoreOfOption(int indicatorId);

	public int getIndicatorTypeNum(int templateId);

	public List<RatingTemplate> getRatingTemplateList(String p, String c);
}
