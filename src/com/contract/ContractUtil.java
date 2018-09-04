package com.contract;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.contract.VProcreditContractDao;
import com.zhiwei.credit.model.admin.ContractElement;
import com.zhiwei.credit.model.creditFlow.contract.Element;
import com.zhiwei.credit.model.creditFlow.fund.project.BpFundProject;

public class ContractUtil {
	
 	/**
 	 * 根据指定的模板为模板要素赋值
 	 * @param signElement  为系统要素赋值类
 	 * @param project   项目
 	 * @param filePath  word文件路径
 	 * @param idMap     存放id的map对象,map的key必须和要素属性文件的key类名相同
 	 * @param businessType  业务类型
 	 * @param contractId    合同id
 	 * eg:<Person,10>,<SlSmallloanProject,360>
 	 * 具体逻辑判断在业务层添加,此处调用明确合同文件已经存在
 	 * 注:业务代码处必须传指定对象的id
 	 */
	public static List<Element> putContentToElements(SignElement signElement,BpFundProject project,CreditBaseDao util,String filePath,Map<String,Object> idMap,String businessType,int contractId){
 		try{
 			Object obj=null;
 			String flag="";//临时标识
 			List<Element> list = new ArrayList<Element>();
 			//1.获得模板中的所有要素
 			//此处需要获得属性文件中的value值，因为之后就要根据id为指定要素赋值了
 			Map<String,Object> map=WordPOI.getReplaceElementsInWord("0",filePath,businessType);//eg(借款人个人名称:person.name)
 			Iterator<?> ite=map.keySet().iterator();
 			//2.定义存放需要计算的要素集合
 			Map<String,Element> tempMap=new TreeMap<String,Element>();
 			VProcreditContractDao vDao = (VProcreditContractDao) AppUtil.getBean("vProcreditContractDao");
			List<?> list2 = null;
			if(!"".equals(contractId)){
				list2 =vDao.getListByCategoryId(contractId);
			}
			while(ite.hasNext()){
				boolean tempFlag=true;//如果是需要计算的要素则tempFlag=false
				String ele=ite.next().toString();//要素中文名 
				Element element =new Element();
				element.setElementType("系统要素");
				element.setDepict(ele);//要素描述
				element.setCode("${"+ele+"}");//要素编码
				Object value=map.get(ele);//要素对应实体或者none_design
				if(null!=value){//如果属性文件中没有定义该合同要素
					if("none_design".equals(value)){//该要素是需要计算的
//						System.out.println("<!----该要素需要计算---->"+ele);
						tempMap.put(ele,element);
						tempFlag=false;
					}else if("none_table".equals(value)){
						tempFlag=false;
					}else{
						//1.首先判断该要素是否存在分类,例如:(bz_:保证、cz_:出质、dy_:抵押)
						String type="";//类别
						String content="";//去掉标识后的类信息
						String[] tempStr=value.toString().split("_");
						if(value.toString().contains("_")){
							type=BaseConstants.getType(tempStr[0]);
							content=tempStr[1];
						}else{
							content=tempStr[0];
						}
						//2.判断该要素的值是否存在多种来源(无论一种或者多种后续代码都相同)
						String[] str=content.split("!");
						for(String s:str){
							String[] pathAndProperty=s.split("#");//bean全路径#属性
							String[] temp=pathAndProperty[0].split("\\.");//获得bean名称
							String className=(type+temp[temp.length-1]);//实体类类名(带有前缀)
							Object classId=idMap.get(className);//对应实体类id
							if(null!=classId){
								//3.反射获得实体类对象
								if(!flag.equals(className) || "".equals(flag)){//判断标识与当前变量是否相等
									Class<?> objct=Class.forName(pathAndProperty[0]);
									obj=util.getById(objct,Serializable.class.cast(classId));
								}
								//4.为临时变量赋值
								flag=className;
								//5.反射获得指定要素的值
								try{
									if(null!=obj){
										Method method=obj.getClass().getDeclaredMethod("get"+BaseUtil.toUpperCaseFirstOne(pathAndProperty[1]),null);
										if(null!=method.invoke(obj)){
											Class<?> tempC=method.getReturnType();
											if(tempC.getName().equals("java.util.Date")){
												element.setValue(method.invoke(obj).toString().substring(0,10));//要素值
											}else if(tempC.getName().equals("java.math.BigDecimal")){
												BigDecimal bd=new BigDecimal(method.invoke(obj).toString());
												element.setValue(bd.setScale(2,RoundingMode.HALF_UP).toString());//要素值
											}else{
												element.setValue(method.invoke(obj).toString());//要素值
											}
										}
									}else{
//										System.out.println("<!--id="+idMap.get(className)+"对应的实体不存在-->");
									}
								}catch(Exception e){
									System.out.println("<!----"+BaseUtil.toUpperCaseFirstOne(pathAndProperty[1])+"要素是父类中的属性---->");
									if(null!=obj){
										Method method=obj.getClass().getSuperclass().getDeclaredMethod("get"+BaseUtil.toUpperCaseFirstOne(pathAndProperty[1]),null);
										if(null!=method.invoke(obj)){
											Class<?> tempC=method.getReturnType();
											if(tempC.getName().equals("java.util.Date")){
												element.setValue(method.invoke(obj).toString().substring(0,10));//要素值
											}else if(tempC.getName().equals("java.math.BigDecimal")){
												BigDecimal bd=new BigDecimal(method.invoke(obj).toString());
												element.setValue(bd.setScale(2,RoundingMode.HALF_UP).toString());//要素值
											}else{
												element.setValue(method.invoke(obj).toString());//要素值
											}
										}
									}else{
//										System.out.println("<!--id="+idMap.get(className)+"对应的实体不存在-->");
									}
								}
							}else{
//								System.out.println("<!----请在ContractHelperAction类的findElement方法中传递"+temp[temp.length-1]+"对象id---->");
							}
						}
					}
				}else{
//					System.out.println(ele+"<!----该要素没有在属性文件中配置---->");
					element.setElementType("自定义要素");
					if (null != list2 && list2.size()>0) {
						for (int k = 0; k < list2.size(); k++) {
							ContractElement cElement = (ContractElement) list2.get(k);
							if(cElement.getElementName().equals(ele)){
								element.setValue(cElement.getElementValue());
								tempFlag=true;
								break;
							}
						}
					} else {
						element.setValue("");
						tempFlag=true;
					}
				}
				if(tempFlag){
					list.add(element);
				}
			}
			//如果tempMap不为空说明该模板中有些要素需要手动计算值
			if(null!=tempMap && tempMap.size()>0){
				signElement.signElementValue(project,util,tempMap,contractId,list);
			}
			return list;
 		}catch(Exception e){
 			e.printStackTrace();
 			return null;
 		}
 	}
}