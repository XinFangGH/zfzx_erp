package com.contract;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class WordPOI {
	
	/**
	 * 返回Docx中需要替换的特殊字符，没有重复项
	 * 推荐传入正则表达式参数"\\$\\{[^{}]+\\}"
	 * @param filePath word文件路径
	 * @param businessType 业务类型
	 */
    public static Map<String,Object> getReplaceElementsInWord(String flag,String filePath,String businessType){ 
        Map<String,String> map=null;
        if(null!=businessType){
        	map=BaseUtil.readProperties(flag,businessType);
        }
        Map<String,Object> map2=new TreeMap<String,Object>();  
        XWPFDocument document = null;  
        try {  
            document = new XWPFDocument(POIXMLDocument.openPackage(filePath));  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{
        	try {
            	if(null!=document){
            		document.close();
            	}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }  
        //遍历段落 (只要内容另起一行就是新的段落，不包括表格)
        Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();  
        while (itPara.hasNext()) {  
            XWPFParagraph paragraph = (XWPFParagraph) itPara.next();  
            String paragraphString = paragraph.getText();//读取到段落中的内容  
            CharSequence cs = paragraphString.subSequence(0,paragraphString.length());  
            Pattern pattern = Pattern.compile(BaseConstants.REGEX);  
            Matcher matcher = pattern.matcher(cs);  
            addContentToMap(matcher,map2,map,businessType);
        }  
        //遍历表格  
        Iterator<XWPFTable> itTable = document.getTablesIterator();  
        while (itTable.hasNext()) {  
            XWPFTable table = (XWPFTable) itTable.next();//获得表格对象  
            int rcount = table.getNumberOfRows();//获得当前表格的行数
            for (int i = 0; i < rcount; i++) {//循环行  
                XWPFTableRow row = table.getRow(i);//获得行对象  
                List<XWPFTableCell> cells = row.getTableCells(); //获得当前行的所有的单元格集合 
                for (XWPFTableCell cell : cells) {//循环单元格
                    String cellText = cell.getText();  
                    CharSequence cs = cellText.subSequence(0,cellText.length());  
                    Pattern pattern = Pattern.compile(BaseConstants.REGEX);  
                    Matcher matcher = pattern.matcher(cs);  
                    addContentToMap(matcher,map2,map, businessType); 
                }  
            }  
        }
        return map2;  
    }
    
   /**
    * 将匹配后的数据添加到map2中
    * @param matcher
    * @param map
    * @param map2
    * @param businessType
    */
    public static void addContentToMap(Matcher matcher,Map<String,Object> map2,Map<String,String> map,String businessType){
    	int startPosition = 0;//初始化索引位置  
        while (matcher.find(startPosition)){
        	if(null!=matcher.group()){//matcher.group() 匹配了正则表达式的内容
            	//借款人个人名称:person.name
    			String temp=matcher.group().replace("${","").replace("}","");
    			if(null!=businessType){
    				map2.put(temp,map.get(temp));
    			}else{
    				map2.put(temp,temp);
    			}
			}
            startPosition = matcher.end();//匹配的结束索引
        }  
    }
    
    
    public static void replaceContent(Matcher matcher,Map<String,Object> map,XWPFParagraph paragraph){
    	List<XWPFRun> runs = paragraph.getRuns();
    	for(int i = 0; i < runs.size(); i++){
    		XWPFRun r=runs.get(i);
    		String rText=r.getText(0);
    		boolean flag=false;
    		if(null!=rText && rText.contains("${") && rText.contains("}")){//1.首先判断rText是否等于${??}
    			flag=true;
    		}
    		if(!flag){
     			if(null!=r.getText(0) && r.getText(0).contains("${")){//判断当前runs.get(i)包含${
    				StringBuffer sb=new StringBuffer();
    				sb.append(r.getText(0).replace("${",""));
    				r.setText("",0);
    				//继续i++直到找到}为止
    				for(int j = i+1; j < runs.size(); j++){
    					if(runs.get(j).getText(0).contains("}")){
    						sb.append(runs.get(j).getText(0).replace("}",""));
    						runs.get(j).setText("",0);
    						break;
    					}else{
    						sb.append(runs.get(j).getText(0));
    						runs.get(j).setText("",0);
    					}
    				}
    				flag=true;
    				rText=sb.toString();
    			}
    		}
    		if(flag){
    			StringBuffer text=new StringBuffer();
    			String[] str=rText.split("}");
    			for(int j=0;j<str.length;j++){
    				String temp=str[j].replace("${","").trim();
    				if(!"".equals(temp) && null!=map.get(temp)){
	    				text.append(map.get(temp).toString());
    				}
    			}
    			r.setText(rText.replace(rText,text),0);
    		}
    	}
    }
    
    public static void replaceTable(Matcher matcher,Map<String,Object> map,XWPFTableCell cell){
        try{
        	List<XWPFParagraph> par_list=cell.getParagraphs();
        	for(XWPFParagraph p:par_list){
        		replaceContent(matcher, map, p);
        	}
		}catch(Exception e1){
//			System.out.println("<--空单元格-->");
		}
    }
    
    /**
     * 替换word中需要替换的特殊字符 
     * @param srcPath  word源路径
     * @param destPath 生成后的路径
     * @param map	        需要替换的内容值
     * @param isHaveTable	文档中是否有表格,如果有则需要替换
     * @param resultList    表格数据集
     * @return
     */
    public static boolean replaceAndGenerateWord(String srcPath,String destPath, Map<String,Object> map,boolean isHaveTable,Map<String,List<List<String>>> resultList) {  
    	XWPFDocument document=null;
    	FileOutputStream outStream = null; 
        try {
            document = new XWPFDocument(POIXMLDocument.openPackage(srcPath)); 
            //替换段落中的指定文字  
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();  
            while (itPara.hasNext()) {  
                XWPFParagraph paragraph = (XWPFParagraph) itPara.next();  
                String paragraphString = paragraph.getText();//读取到段落中的内容  
                if(paragraphString.contains("${")){
                	CharSequence cs = paragraphString.subSequence(0,paragraphString.length());  
                	Pattern pattern = Pattern.compile(BaseConstants.REGEX);  
                	Matcher matcher = pattern.matcher(cs);  
                	replaceContent(matcher,map,paragraph);
                }
            }  
            
            //替换表格中的指定文字  
            Iterator<XWPFTable> itTable = document.getTablesIterator();
            while (itTable.hasNext()) {  
                XWPFTable table = (XWPFTable) itTable.next();//获得表格对象  
                int rcount = table.getNumberOfRows();//获得当前表格的行数
                for (int i = 0; i < rcount; i++) {//循环行  
                    XWPFTableRow row = table.getRow(i);//获得行对象  
                    List<XWPFTableCell> cells = row.getTableCells(); //获得当前行的所有的单元格集合 
                    for (XWPFTableCell cell : cells) {//循环单元格
                        String cellText = cell.getText();  
                        if(cellText.contains("${")){
                        	CharSequence cs = cellText.subSequence(0,cellText.length());  
                        	Pattern pattern = Pattern.compile(BaseConstants.REGEX);  
                        	Matcher matcher = pattern.matcher(cs);  
                        	replaceTable(matcher,map,cell);
                        }
                    }  
                }  
            }
            outStream = new FileOutputStream(destPath);
            if(isHaveTable && null!=resultList && resultList.size()>0){
            	//替换表格
            	CommonTable.insertValueToTable(document,resultList,true);
            }
            document.write(outStream);
            return true;  
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("<!--文件不存在或者该文件已被打开-->");
            return false;  
        } finally{
        	try {
        		if(null!=outStream){
        			outStream.close();
        		}
        		if(null!=document){
        			document.close();
        		}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        } 
    }
}