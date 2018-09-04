package com.zhiwei.credit.action.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class Integration {
//积分系统只需要获取当前登录的用户，以及该用户的行为；然后根据配置文件信息执行相应的操作
	public void doBefore(JoinPoint jp){
		System.out.println(jp.getSignature().getName());//获取方法名
		/*Object[] params = jp.getArgs();
		if(params!=null&&params.length!=0){
			for(int i=0;i<params.length;i++){
				System.out.println(params[i]);
			}
		}
		System.out.println(jp.getSignature().getDeclaringType());
		System.out.println(jp.getSignature());//类
		System.out.println(jp.getSignature().getDeclaringTypeName());*/
		doSomeThing(jp.getSignature().getName(),jp.getSignature().getDeclaringTypeName());
		System.out.println("doBefore");
	}
	public void doAfter(JoinPoint jp){
		System.out.println("doAfter");
	}
	public Object doAround(ProceedingJoinPoint pjp)throws Throwable{
		System.out.println("doAround");
		return null;
	}
	public void doException(JoinPoint jp,Throwable ex){
		System.out.println("doException");
	}
	public void doAfterReturn(JoinPoint jp,Object retVal){
		System.out.println("JoinPoint:"+jp.getSignature().getDeclaringTypeName());
		System.out.println("retVal:"+retVal);
		System.out.println("doAfterReturn");
		doSomeThing("","");
	}
	
	@SuppressWarnings("static-access")
	private void doSomeThing(String method,String packAge){
		InputStream in =  Integration.class.getClassLoader().getResourceAsStream("interation.properties");
		Properties p = new Properties();
		if(in==null){
			System.out.println("interation.properties is null");
			return;
		}
		try {
			p.load(in);
			String type = (String) p.get(method);
			if(type==null){
				System.out.println("type is null");
				return;
			}
			System.out.println(type);
			//写一个规则，
			System.out.println("------------------"+p.get(method));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("packAge:"+packAge+", method:"+method);
	}
}
