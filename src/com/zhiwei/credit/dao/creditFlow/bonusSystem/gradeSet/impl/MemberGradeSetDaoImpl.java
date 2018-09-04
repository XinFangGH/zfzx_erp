package com.zhiwei.credit.dao.creditFlow.bonusSystem.gradeSet.impl;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.bonusSystem.gradeSet.MemberGradeSetDao;
import com.zhiwei.credit.model.creditFlow.bonusSystem.gradeSet.MemberGradeSet;

/**
 * 会员等级设置dao实现
 * @author songwenjie
 *
 */
@SuppressWarnings("unchecked")
public class MemberGradeSetDaoImpl extends BaseDaoImpl<MemberGradeSet>
		implements MemberGradeSetDao {

	public MemberGradeSetDaoImpl() {
		super(MemberGradeSet.class);
	}


}
