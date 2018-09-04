package com.zhiwei.credit.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;


public class Processor  extends Thread{
	List list = null;
	//dealMethod 的正确写法是 service.method
	public String dealMethod;
	public String serviceMethod;
	


	public String getServiceMethod() {
		return serviceMethod;
	}
	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	CountDownLatch countDownLatch;
	
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
	public String getDealMethod() {
		return dealMethod;
	}
	public void setDealMethod(String dealMethod) {
		dealMethod = dealMethod;
	}
	
	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}
	public void setCountDownLatch(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}
	@Override
	public void run() {
		//TODO 处理list里的数据
		int i=0;
		System.out.println("计数count==="+(i++));
		System.out.println("serviceMethod==="+serviceMethod);
		if(serviceMethod!=null){//线程用来处理业务数据方法抽象
			try {
				String [] beanMethods=serviceMethod.split("[.]");
				if(beanMethods!=null){
					String beanId=beanMethods[0];//用来处理业务的service 
					String method=beanMethods[1];//用来处理业务的method
					//触发该Bean下的业务方法
					Object serviceBean=AppUtil.getBean(beanId);
					if(serviceBean!=null){
						Method invokeMethod;
						invokeMethod = serviceBean.getClass().getDeclaredMethod(method, new Class[]{List.class});
					    invokeMethod.invoke(serviceBean,list);
					}
				}else{//业务处理方法名写法出错：  正确的处理方法是service.method ;线程跑空
					System.out.println("业务处理方法名写法出错：  正确的处理方法是service.method ;线程跑空");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{//没有业务处理方法，线程就是跑空
			System.out.println("没有业务处理方法，线程就是跑空");
		}
		//无论处理结果如何，线程计数都会减一
		countDownLatch.countDown();
	}
	
	public static  List<Processor> generateProcessorList(List<Object> list,int count4EachThread,String serviceMethod) {
		 List<Processor> plist = new ArrayList<Processor>();
		int beginIndex=0;
		int endIndex = 0;
		for(int i=0 ;endIndex!=list.size()-1;i++){
			Processor processor = new Processor();
			System.out.println("set之前"+processor.getDealMethod());
			beginIndex = ((i)*count4EachThread);
			endIndex = ((i+1)*count4EachThread)>(list.size()-1)?(list.size()-1):((i+1)*count4EachThread);
			List subList = list.subList(beginIndex, endIndex);
			processor.setList(subList);
			processor.setServiceMethod(serviceMethod);
			System.out.println("setSSS之后"+processor.getServiceMethod());
			
			processor.setDealMethod(serviceMethod);
			System.out.println("setDealMethod之后"+processor.getDealMethod());
			plist.add(processor);
		}
		return plist;
	}
}
