package com.zhiwei.credit.dao.creditFlow.creditmanagement.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.ScoreGradeOfClassDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.ScoreGradeOfClass;

public class ScoreGradeOfClassDaoImpl extends BaseDaoImpl<ScoreGradeOfClass> implements ScoreGradeOfClassDao{
	public ScoreGradeOfClassDaoImpl() {
		super(ScoreGradeOfClass.class);
	}

	@Resource
	private CreditBaseDao creditBaseDao;

	@Override
	public Boolean deleteScoreGradeOfClass(ScoreGradeOfClass scoreGradeOfClass) {
		boolean b=false;
		try {
			b=creditBaseDao.deleteDatas(scoreGradeOfClass);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public Boolean saveScoreGradeOfClass(ScoreGradeOfClass scoreGradeOfClass) {
		boolean b=false;
		try {
			b=creditBaseDao.saveDatas(scoreGradeOfClass);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public Boolean updateScoreGradeOfClass(ScoreGradeOfClass scoreGradeOfClass) {
		boolean b=false;
		try {
			b=creditBaseDao.updateDatas(scoreGradeOfClass);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public ScoreGradeOfClass getScoreGradeOfClass(Long grandId) {
		ScoreGradeOfClass scoreGradeOfClass=null;
		try {
			scoreGradeOfClass=(ScoreGradeOfClass) creditBaseDao.getById(ScoreGradeOfClass.class, grandId);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return scoreGradeOfClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScoreGradeOfClass> getScoreGradeList(Long classId) {
		String hql="from ScoreGradeOfClass s where s.classId=?";
		return getSession().createQuery(hql).setParameter(0, classId).list();
	}

	@Override
	public Boolean deleteScoreList(List<ScoreGradeOfClass> list) {
		boolean b=false;
		try {
			creditBaseDao.deleteAll(list);
			b=true;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public ScoreGradeOfClass getScoreGrade(Long classId, float score) {
		if(score<100){
		String hql="from ScoreGradeOfClass s where s.classId=? and s.startScore<=? and s.endScore>?";
		return (ScoreGradeOfClass) getSession().createQuery(hql).setParameter(0, classId).setParameter(1, score).setParameter(2, score).uniqueResult();
		}else{
			String hql="from ScoreGradeOfClass s where s.classId=? and s.startScore<=? and s.endScore=100";
			return (ScoreGradeOfClass) getSession().createQuery(hql).setParameter(0, classId).setParameter(1, score).uniqueResult();
		}
	}
}
