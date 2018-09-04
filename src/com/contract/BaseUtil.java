package com.contract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.zhiwei.credit.model.creditFlow.contract.Element;

@SuppressWarnings("unchecked")
public class BaseUtil extends HibernateDaoSupport{

	/** 
     * 新建目录 
     * @param filePath 如 c:/aaa 
     */ 
	public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        if (dir.mkdirs()) {  
            return true;  
        } else {  
            return false;  
        }  
    } 
    
    /**
	 * 读取合同要素属性文件
	 * @param flag  用来表示map的key、value存放的先后顺序
	 * 0(借款人个人名称,person.name)
	 * 1(person.name,借款人个人名称)
	 * 2(借款人个人名称,"")
	 * @param businessType 业务类型
	 * @return 要素集合
	 */
    public static Map<String,String> readProperties(String flag,String businessType){
		Map<String,String> map=new TreeMap<String,String>();
		try{
			int size=0;
			String type="";
			if("InternetFinance".equals(businessType)){
				size=BaseConstants.P2PSIZE;
				type="p2p";
				
			}else if("SmallLoan".equals(businessType)){
				size=BaseConstants.LOANSIZE;
				type="loan";
			}
			OrderedProperties props = new OrderedProperties();
			for(int i=1;i<=size;i++){
				String path=type+"/Element_"+i+".properties";
				props.load(BaseUtil.class.getResourceAsStream(path));
		    	Iterator it= props.keySet().iterator();
		    	String key="";
		    	while(it.hasNext()){
		    		key=(String)it.next();
		    		if("0".equals(flag)){
		    			map.put(key,props.getProperty(key));
		    		}else if("1".equals(flag)){
		    			map.put(props.getProperty(key),key);
		    		}else if("2".equals(flag)){
		    			map.put(key,"");
		    		}
		    	}
			}
	    	return map;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
     * 将字符串首字母转大写
     * @param s  需要转换的字符串
     * @return 转换后的字符串
     */
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0))){
            return s;
        }else{
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
    
    /**

     * 使用文件通道的方式复制文件
     * @param s  源文件
     * @param t  复制到的新文件
     */
     public static void fileChannelCopy(String srcPath,String outpath) {
    	 File s = new File(srcPath);
    	 File t = new File(outpath);
         FileInputStream fi = null;
         FileOutputStream fo = null;
         FileChannel in = null;
         FileChannel out = null;
         try {
             fi = new FileInputStream(s);
             fo = new FileOutputStream(t);
             in = fi.getChannel();//得到对应的文件通道
             out = fo.getChannel();//得到对应的文件通道
             in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             try {
                 fi.close();
                 in.close();
                 fo.close();
                 out.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }

     /**
      * 根据业务类型查询相应类型下的合同要素属性文件
      * @param businessType  业务类型
      * @param flag  标识
      * @param size  用来存放不同类别下合同属性文件的个数,eg:小贷_9/p2p_6
      * @return
      */
	public static List<?> readPropertiesByType(String businessType,boolean flag) {
		int size=0;
		String type="";//主要用于拼接属性文件路径
		List list =new ArrayList();
		if("SmallLoan".equals(businessType)){
			type="loan";
			size=BaseConstants.LOANSIZE;
		}else{
			type="p2p";
			size=BaseConstants.P2PSIZE;
		}
		common(businessType,size,type,list,flag);
		return list;
	}
	
	/**
	 * 读取不同分类下属性文件内容
	 * @param businessType  业务类别
	 * @param size  不同分类下属性文件的个数
	 * @param type  分类标识(目前是文件夹名称)
	 * @param list
	 * @param flag
	 */
	public static void common(String businessType,int size,String type,List list,boolean flag){
		for(int i=1;i<=size;i++){
			Element element = new Element();
			element.setValue("");
			String path=type+"/Element_"+i+".properties";
			Map<String,String> tempMap=BaseUtil.getReportPro(path,false);
			if("SmallLoan".equals(businessType)){
				if(i==1){
					element.setDepict("借款人信息_"+tempMap.size());
				}else if(i==2){
					element.setDepict("管理费法律主体_"+tempMap.size());
				}else if(i==3){
					element.setDepict("服务费法律主体_"+tempMap.size());
				}else if(i==4){
					element.setDepict("借款信息_"+tempMap.size());
				}else if(i==5){
					element.setDepict("保证人信息_"+tempMap.size());
				}else if(i==6){
					element.setDepict("抵押人信息_"+tempMap.size());
				}else if(i==7){
					element.setDepict("出质人信息_"+tempMap.size());
				}else if(i==8){
					element.setDepict("抵质押物信息_"+tempMap.size());
				}else if(i==9){
					element.setDepict("合同信息_"+tempMap.size());
				}else if(i==10){
					element.setDepict("贷后信息_"+tempMap.size());
				}else if(i==11){
					element.setDepict("出借法律主体_"+tempMap.size());
				}
			}else{
				if(i==1){
					element.setDepict("借款人信息_"+tempMap.size());
				}else if(i==2){
					element.setDepict("投资人信息_"+tempMap.size());
				}else if(i==3){
					element.setDepict("投资人投标信息_"+tempMap.size());
				}else if(i==4){
					element.setDepict("招标信息_"+tempMap.size());
				}else if(i==5){
					element.setDepict("散标资金信息_"+tempMap.size());
				}else if(i==6){
					element.setDepict("债权人信息_"+tempMap.size());
				}else if(i==7){
					element.setDepict("原始债权信息_"+tempMap.size());
				}
			}
			if(!flag){
				element.setDepict(element.getDepict().split("_")[0]);
			}
			list.add(element);
			getSysListElement(tempMap,list);
		}
	}
	
	/**
	 * 读取合同要素属性文件
	 * 该属性文件配置的主要是系统中需要的合同(类似报表的属性文件结构)
	 * @param path  属性文件路径
	 * @return 要素集合
	 */
	public static Map<String,String> getReportPro(String path,boolean flag){
		Map<String,String> map=new LinkedHashMap<String,String>();
		try{
			OrderedProperties props = new  OrderedProperties();    
	        props.load(ContractUtil.class.getResourceAsStream(path));
	    	Iterator it= props.keySet().iterator();
	    	String key="";
	    	while(it.hasNext()){
	    		key=(String)it.next();
	    		map.put(key,key);
	    	}
	    	return map;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 查看系统要素
	 * @param tempMap  某个属性文件中的元素集合
	 * @return List
	 */
	public static void getSysListElement(Map<String,String> tempMap,List list) {
		try{
			Iterator ite=tempMap.keySet().iterator();
			while(ite.hasNext()){
				Object obj=ite.next();
				Element element = new Element();
				element.setDepict(obj.toString());
				element.setValue("");
				element.setCode("${"+obj.toString()+"}");
				list.add(element);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}