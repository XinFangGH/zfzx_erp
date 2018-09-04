package com.zhiwei.credit.service.creditFlow.bonusSystem.gradeSet;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.bonusSystem.gradeSet.MemberGradeSet;

public interface MemberGradeSetService extends BaseService<MemberGradeSet> {
	
	/**
	 * 初始化5条积分等级数据
	 */
	void initData();
	
	/**
	 * 计算用户当前积分等级
	 * 
	 * @param Score   用户积分数值
	 * @return 	返回积分等级对象
	 */
	public MemberGradeSet findByUserScore(Long socre);
	
	/**
	 * 查询相同等级是事存在
	 * @param levelName
	 * @return
	 */
	MemberGradeSet findBylevelName(String levelName);


}
