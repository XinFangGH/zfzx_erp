package com.zhiwei.credit.service.creditFlow.creditmanagement;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.CreditRatingDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.IndicatorDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.MakeScoreDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.MakeScoreGetSubDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.RatingTemplateDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.ScoreGradeOfClassDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.CreditRating;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingIndicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingOption;
import com.zhiwei.credit.model.creditFlow.creditmanagement.RatingTemplate;
import com.zhiwei.credit.model.creditFlow.creditmanagement.ScoreGradeOfClass;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateIndicator;
import com.zhiwei.credit.model.creditFlow.creditmanagement.TemplateOptions;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseFinance;
import com.zhiwei.credit.model.system.AppUser;

//自动计算分数service  没有impl
public class MakeScoreService {

	private MakeScoreDao makeScoreDao;
	@Resource
	private CreditRatingDao creditRatingDao;
	@Resource
	private RatingTemplateDao ratingTemplateDao;
	@Resource
	private ScoreGradeOfClassDao scoreGradeOfClassDao;
	@Resource
	private IndicatorDao indicatorDao;
	@Resource
	private MakeScoreGetSubDao getSubDao;

	public MakeScoreService() {
	}

	public MakeScoreService(MakeScoreDao dao, CreditRatingDao creditRatingDao,
			RatingTemplateDao ratingTemplateDao,
			ScoreGradeOfClassDao scoreGradeOfClassDao,
			IndicatorDao indicatorDao, MakeScoreGetSubDao getSubDao) {
		this.makeScoreDao = dao;
		this.creditRatingDao = creditRatingDao;
		this.ratingTemplateDao = ratingTemplateDao;
		this.scoreGradeOfClassDao = scoreGradeOfClassDao;
		this.indicatorDao = indicatorDao;
		this.getSubDao = getSubDao;
	}

	// 获取所有指标
	public List getSubject(int id) {
		List<Object[]> person = getSubDao.getPerson();
		List<Object[]> enterprise = getSubDao.getEnterprise();
		List<List<String>> l = new ArrayList();
		getSubL(person, l);
		getSubL(enterprise, l);
		Map m = indicatorDao.getByIndicatorId(id);

		for (int i = 0; i < l.size(); i++) {
			String key = l.get(i).get(0);
			String value = (String) m.get(key);
			if (key.equals(value)) {
				l.remove(i);
				--i;
			}
		}
		return l;
	}

	// 可能改变算法需求,不需要合并
	public Object[] makeScores(String type, int mid, int cid) throws Exception {
		List<Object[]> l = getAns(type, mid, cid);
		StringBuffer scoreForSco = new StringBuffer();
		StringBuffer scoreForSub = new StringBuffer();
		StringBuffer scoreForAns = new StringBuffer();
		int scorces = 0;
		int maxScores = 0;

		for (Object[] os : l) {
			// score,max,id,ans
			Object[] o = makeScore(os, mid);
			scorces = scorces + Integer.valueOf(o[0] + "");
			maxScores = maxScores + Integer.valueOf(o[1] + "");
			scoreForSco = scoreForSco.append(o[0]).append(",");
			scoreForSub = scoreForSub.append(o[2]).append(",");
			scoreForAns = scoreForAns.append(o[3] == null ? " " : o[3]).append(
					",");
		}

		String ssc = scoreForSco.substring(0, scoreForSco.length() - 1);
		String ssu = scoreForSub.substring(0, scoreForSub.length() - 1);
		String ans = scoreForAns.substring(0, scoreForAns.length() - 1);
		float scores = (float) scorces / maxScores * 100;

		// 记录数据
		if (scores > 100) {
			scores = 100;
		}
		if (scores < 0) {
			scores = 0;
		}
		// 获取资信记录
		RatingTemplate rt = ratingTemplateDao.getRatingTemplate(mid);
		ScoreGradeOfClass scoreGradeOfClass = scoreGradeOfClassDao
				.getScoreGrade(rt.getClassId(), scores);

		// 获取分数(100分制),保存提id,保存分数id,保存答案值,资信,资信gz
		return new Object[] { scores, ssc, ssu, ans, rt, scoreGradeOfClass };
	}

	public boolean doSaveScores(Object[] oo, Object[] o2) {
		int mid = Integer.valueOf(o2[0] + "");
		int cid = Integer.valueOf(o2[1] + "");
		String cname = (String) o2[2];

		// 得分总,得分[],题目id,ans,评卷,参考量[]
		float score = Float.valueOf("" + oo[0]);
		String scores = (String) oo[1];
		String subs = (String) oo[2];
		String ans = (String) oo[3];
		RatingTemplate rt = (RatingTemplate) oo[4];
		ScoreGradeOfClass scs = (ScoreGradeOfClass) oo[5];
		String customerType = rt.getCustomerType();// 企业/个人
		String tName = rt.getTemplateName();// 模板名
		String reg = scs.getGrandname();// A/B
		String hanyi = scs.getClassName();// 很好很强大

		// 分数,分裤,题库
		CreditRating creditRating = new CreditRating();
		Date date = new Date();
		AppUser userInfo = ContextUtil.getCurrentUser();

		creditRating.setRatingTime(date);
		creditRating.setArr_id(subs);
		creditRating.setArr_score(scores);
		creditRating.setRatingMan(userInfo.getFullname());
		creditRating.setUserId(userInfo.getUserId());
		creditRating.setCustomerType(customerType);
		creditRating.setCreditTemplate(tName);
		creditRating.setCreditTemplateId(mid); // 模板id
		creditRating.setRatingScore(score); // 总分
		creditRating.setCreditRegister(reg);
		creditRating.setAdvise_sb(hanyi); // 评价
		creditRating.setCustomerName(cname);
		creditRating.setCustomerId(cid);
		creditRating.setAns(ans);

		boolean b = creditRatingDao.addCreditRating(creditRating);
		return b;

	}

	private void getSubL(List<Object[]> ml, List l) {
		for (Object[] o : ml) {
			List nl = new ArrayList();
			nl.add(o[0]);
			l.add(nl);
		}
	}

	private Object[] makeScore(Object[] os, int mid) {
		String exam = (String) os[0];
		Double ans = (Double) os[1];
		TemplateIndicator tm = (TemplateIndicator) os[3];
		List<TemplateOptions> gz = (List<TemplateOptions>) os[2];
		// 题目,答案,gz,t(无配置)
		int max = 0;
		int min = 0;
		int score = 0;
		if (gz.size() > 0) {
			max = getQZorScore(gz.get(0));
			min = max;
			score = max;
		}
		for (TemplateOptions t : gz) {
			String s = t.getOptionName();// 获取比较值
			Double c = ("".equals(s) ? 0 : Double.valueOf(s));
			int thisScore = getQZorScore(t);// 本次分制
			if (ans != null && ans > c) {
				score = thisScore;
			}
			if (max < thisScore)
				max = thisScore;
			if (min > thisScore)
				min = thisScore;
		}
		if (ans == null)
			score = min;
		int id = saveAns(tm, gz, mid);// 保存答案,并获取答案id
		return new Object[] { score, max, id, ans };
	}

	private int saveAns(TemplateIndicator templateIndicator,
			List<TemplateOptions> templateOptionList, int mid) {
		// 保存题目
		RatingIndicator ratingIndicator = new RatingIndicator(mid,
				templateIndicator.getId(), templateIndicator
						.getIndicatorTypeId(), templateIndicator
						.getIndicatorType(), templateIndicator
						.getIndicatorName(), templateIndicator
						.getIndicatorDesc(), templateIndicator.getCreater(),
				templateIndicator.getCreateTime(), templateIndicator
						.getLastModifier(), templateIndicator
						.getLastModifTime());
		int indicatorId = (Integer) creditRatingDao
				.addRatingIndicator(ratingIndicator);

		// 保存选项
		for (int j = 0; j < templateOptionList.size(); j++) {
			TemplateOptions o_old = templateOptionList.get(j);
			RatingOption ro = new RatingOption();
			ro.setIndicatorId(indicatorId);
			ro.setOptionName(o_old.getOptionName());
			ro.setScore(o_old.getScore());
			ro.setSortNo(o_old.getSortNo());
			creditRatingDao.addTemplateOptions(ro);
		}

		return indicatorId;
	}

	private int getQZorScore(TemplateOptions t) {
		// 要权重，喵的权重为0用原值
		int s = t.getQzScore();
		if (s == 0)
			s = t.getScore();
		return s;
	}

	// 从数据库中取值,默认企业
	private List<Object[]> getAns(String type, int mid, int cid)
			throws Exception {
		// 获取题目,分析所需要关联数据库
		List<Object[]> l = new ArrayList<Object[]>();
		List<TemplateIndicator> list = creditRatingDao
				.getIndicatorByTempId(mid);// 具体题库
		Set needDoSql = getNeedDoSqlForSet(type, list);
		Map<String, Map> m = doSql(needDoSql, cid);// 数据信息
		Map<String, Object[]> ml = getSubDao.getForMap(type);// 所有题目信息

		// 便利题目, 整合题目与答案
		for (TemplateIndicator t : list) {
			String key = t.getIndicatorName();// 题目
			Object[] o = ml.get(key); // { "个人资产", "enterpriseFinance","name"
			Double ans = 0d;
			List<TemplateOptions> gz = creditRatingDao.getOptionsListOrder(t
					.getId());
			if (o != null) {
				Map m2 = m.get(o[1]); // 所有的元素

				// 类型转换
				Object _v = m2.get(o[2]);
				
				if(_v==null){
					ans=null;
				}else{
				ans = Double.valueOf(_v+"");
				}

			} else {
				ans = null; // null判断,ans可以为-，无缺省
			}
			l.add(new Object[] { key, ans, gz, t });
		}
		return l;
	}

	// 查询数据库,并存入map中
	private Map<String, Map> doSql(Set<String> se, int cid) throws Exception {
		Map<String, Object[]> mapping = getSubDao.mappingModel();
		Map<String, Map> m = new HashMap();
		Object[] o = se.toArray();
		for (Object s : o) {
			Object[] oo = mapping.get(s);
			Map m2 = doSqlM(cid, oo);
			m.put((String) s, m2);
		}
		return m;
	}

	private Map doSqlM(int cid, Object[] oo) throws Exception {
		Map m = new HashMap();
		if ("".equals(oo[1])) {
			Object bean = makeScoreDao.get((Class) oo[0],
					String.valueOf(oo[2]), cid);
			m = beanToMap(bean);
		} else if ("list".equals(oo[1])) {
			List l = makeScoreDao.getForList((Class) oo[0], String
					.valueOf(oo[2]), cid);
			for (int i = 0; i < l.size(); i++) {
				EnterpriseFinance o = (EnterpriseFinance) l.get(i);
				m.put(o.getTextFeildId(), o.getTextFeildText());
			}
		}
		return m;
	}

	private Set<String> getNeedDoSqlForSet(String type,
			List<TemplateIndicator> list) {
		Map<String, Object[]> allM = getSubDao.getForMap(type);// 所有的题库与映射关系
		Set<String> needDoSql = new HashSet<String>();

		for (TemplateIndicator t : list) { // 题目可能会重复，不可使用，需要重新遍历
			String key = t.getIndicatorName(); // 个人总资产
			Object[] o = allM.get(key); // Vepertson
			if (o != null) {
				String value = (String) o[1]; // Vepertson
				if (value != null && !"".equals(value)) {
					needDoSql.add(value);
				}
			}
		}
		return needDoSql;
	}

	// 工具
	private Map beanToMap(Object bean) throws Exception {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

}
