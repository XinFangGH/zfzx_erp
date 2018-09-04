package com.zhiwei.credit.service.creditFlow.bonusSystem.gradeSet.impl;

import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.bonusSystem.gradeSet.MemberGradeSetDao;
import com.zhiwei.credit.model.creditFlow.bonusSystem.gradeSet.MemberGradeSet;
import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;
import com.zhiwei.credit.service.creditFlow.bonusSystem.gradeSet.MemberGradeSetService;

public class MemberGradeSetServiceImpl extends BaseServiceImpl<MemberGradeSet>
					implements MemberGradeSetService {

	
	public  MemberGradeSetDao dao;
	public MemberGradeSetServiceImpl(MemberGradeSetDao dao) {
		super(dao);
		  this.dao = dao;
	}
	@Override
	public void initData() {
		
		List<MemberGradeSet> list = dao.getAll();
		if(list==null||list.size()==0){
			System.out.println("***********初始化5条会员等级信息*************");
			for(int i = 1 ; i<= 5 ; i++){
				MemberGradeSet memberGradeSet = new MemberGradeSet();
			//	memberGradeSet.setLevelId(Long.valueOf(1));
				memberGradeSet.setLevelName(i+"");
				if(i==1){
					memberGradeSet.setLevelMark("一级会员");
				}else if(i==2){
					memberGradeSet.setLevelMark("二级会员");
				}else if(i==3){
					memberGradeSet.setLevelMark("三级会员");
				}else if(i==4){
					memberGradeSet.setLevelMark("四级会员");
				}else{
					memberGradeSet.setLevelMark("五级会员");
				}
				memberGradeSet.setLevelMinBonus(Long.valueOf((i-1)*100));
				dao.save(memberGradeSet);
			}
			
		}
		
		
	}
	@Override
	public MemberGradeSet findByUserScore(Long socre) {
		List<MemberGradeSet> list = dao.getAll();
		MemberGradeSet result = null;
		
		if(list!=null&&list.size()>0){
			//List排序---按会员积分倒序排序   ----排序方式，选择排序
			//排序后的List
			for(int i=0 ; i < list.size()-1 ; i++){
				for(int j=i+1 ; j<list.size(); j++ ){
					MemberGradeSet temp = list.get(i);
					MemberGradeSet nextTemp = list.get(j);
					if(temp.getLevelMinBonus().compareTo(nextTemp.getLevelMinBonus())<0){
						list.set(i, nextTemp);
						list.set(j, temp);
					}
				}
			}
			
			for(MemberGradeSet memberGradeSet : list){
				//如果用户积分大于等于  并且  积分规则已开启则 返回这个规则
				if((socre.compareTo(memberGradeSet.getLevelMinBonus())>=0)){
					result = memberGradeSet;
					break;
				}	
			}
		}
		
		//排序完成
		return result;
	}
	@Override
	public MemberGradeSet findBylevelName(String levelName) {
		String hql = " from MemberGradeSet where levelName = ? ";
		List<MemberGradeSet> list = dao.findByHql(hql, new Object[]{levelName});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	

}
