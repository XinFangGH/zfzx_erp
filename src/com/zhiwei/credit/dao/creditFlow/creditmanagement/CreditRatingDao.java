package com.zhiwei.credit.dao.creditFlow.creditmanagement;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.CreditRating;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingIndicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingOption;
import com.zhiwei.credit.model.creditFlow.creditmanagement.ScoreGrade;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateIndicator;


public interface CreditRatingDao extends BaseDao<CreditRating>{

	public List getIndicatorByTempId(int id);

	public List getOptionsList(int indicatorId);

	public boolean addCreditRating(CreditRating creditRating);

	public List getCreditRatingList(int start, int limit);

	public int getCreditRatingListNum();

	public CreditRating getCreditRating(int id);

	public boolean deleteCreditRating(int id);

	public ScoreGrade getScoreGrade(float ratingScore);

	public List<CreditRating> getCreditRatingList(String customerType,
			String customerName, String creditTemplate, String projectId, int start, int limit,String userId,String startScore,String endScore,String grandName,String companyId);

	public int getCreditRatingListNum(String customerType, String customerName,
			String creditTemplate, String projectId,String userId,String startScore,String endScore,String grandName,String companyId);
	public List<CreditRating> getCreditRatingListOfJD(String customerType,int customerId, int start, int limit);

	public int getCreditRatingListNumOfJD(String customerType, int customerId);
	public TemplateIndicator findTemplateIndicator(int templateIndicatorId);

	public java.io.Serializable addRatingIndicator(RatingIndicator ratingIndicator);

	public List getTemplateOptionList(int id);

	public boolean addTemplateOptions(RatingOption ro);

	public RatingIndicator findRatingIndicator(int ratingIndicatorId);

	public List getRatingOptionList(int id);
	
    public int getMaxStore(int indicatorId);
	public List getRatingOptionListOrder(int id);
	public List getOptionsListOrder(int indicatorId);
}
