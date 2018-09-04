package com.zhiwei.core.integral;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

import com.zhiwei.core.util.ContextUtil;

/*
 * 积分切面类
 */
public class IntegralAop {
	
	//切面方法
	public Object integralEngine(ProceedingJoinPoint pjp) throws Throwable{
		Object object = null;
		object = pjp.proceed();//放行
		
		Signature signature = pjp.getSignature();
		return object;
	}
	
	
	 public void doAfter(JoinPoint jp,Object val){  
	 }  
}
