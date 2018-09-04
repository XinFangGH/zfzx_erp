package com.zhiwei.credit.action.creditFlow.creditmanagement;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.service.creditFlow.creditmanagement.MakeScoreService;

public class MakeScoreAction extends BaseAction {
	private int mid;
	private int cid;// 客户id
	private String cname;// 客户名称 保存
	private String ctype;// 客户type 判断
	@Resource
	private MakeScoreService makeScoreService;

	// 创建分数记录
	public void makeScore() throws Exception {
		// 得分总,得分[],题目id,适用评卷,评卷[]
		Object[] m = makeScoreService.makeScores(ctype, mid, cid);// 题目, 答案，
		// Object[] scores = getScorces(mid, cid);
		// 添加记录
		boolean b = makeScoreService.doSaveScores(m,new Object[]{mid,cid,cname});
		String msg = "";
		if (b) {
			msg = "{success:true}";
		} else {
			msg = "{success:false}";
		}
		try {
			JsonUtil.responseJsonString(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// 获取题目
	public void getSubject() {
		List s = makeScoreService.getSubject(mid);
		
		JsonUtil.jsonFromList(s, s.size());
	}

	
	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public MakeScoreService getMakeScoreService() {
		return makeScoreService;
	}

	public void setMakeScoreService(MakeScoreService makeScoreService) {
		this.makeScoreService = makeScoreService;
	}

}
