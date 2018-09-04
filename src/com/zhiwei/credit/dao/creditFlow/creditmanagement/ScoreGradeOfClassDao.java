package com.zhiwei.credit.dao.creditFlow.creditmanagement;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.ScoreGradeOfClass;

public interface ScoreGradeOfClassDao extends BaseDao<ScoreGradeOfClass>{
    public Boolean saveScoreGradeOfClass(ScoreGradeOfClass scoreGradeOfClass);
    
    public Boolean updateScoreGradeOfClass(ScoreGradeOfClass scoreGradeOfClass);
    
    public Boolean deleteScoreGradeOfClass(ScoreGradeOfClass scoreGradeOfClass);
    
    public ScoreGradeOfClass getScoreGradeOfClass(Long grandId);
    
    public List<ScoreGradeOfClass> getScoreGradeList(Long classId);
    
    public Boolean deleteScoreList(List<ScoreGradeOfClass> list);
    
    public ScoreGradeOfClass getScoreGrade(Long classId,float score);
}
