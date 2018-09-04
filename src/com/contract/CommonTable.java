package com.contract;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;

/**
 * 目前该类只能操作已docx结尾的word文件
 * @author zhangcb
 *
 */
public class CommonTable {
	
	/**
	 * @Description poi默认是按照模板生成表格,但是其规则是按照word中表格的行数去一一匹配,不符合项目需求
	 * 所以目前改造成word中表格(需要替换数据)部分只需要画一个单元格,单元格中的内容是表格的名称
	 * 并且声称后的表格宽度都是统一的。
	 * @param resultList 表格数据集(map的key是表格的名称,value是表格的内容)
	 * @param isDelTmpRow 是否删除模版行
	 */
	public static void insertValueToTable(XWPFDocument doc,Map<String,List<List<String>>> resultList,boolean isDelTmpRow) throws Exception {
		XWPFTable table = null;
		XWPFTableRow tmpRow=null;//模板行
		XWPFTableCell tmpCell=null;//模板单元格
		
		Iterator<XWPFTable> iterator = doc.getTablesIterator();
		
		while (iterator.hasNext()){//循环表格集合
			table = iterator.next();
			List<List<String>> cotentList=resultList.get(table.getText().trim().replace("${","").replace("}",""));
			if(null!=cotentList && cotentList.size()>0){//判断数据集中的表格名称是否与表格的名称相同
				tmpRow=table.getRows().get(0);//第1行
				tmpCell=tmpRow.getTableCells().get(0);//第一列。
				for(int i=0;i<cotentList.size();i++){//循环数据集合,动态创建行
					List<String> list=cotentList.get(i);//取出数据集中的一条记录
					XWPFTableRow row=table.createRow();//创建行
					row.setHeight(tmpRow.getHeight());
					for(int j=0;j<list.size();j++){//循环数据集中的一条记录,创建  
						XWPFTableCell cell=null;
						if(j==0){//如果是第一个单元格则不要创建单元格而是用原来的
							cell=row.getCell(0);
						}else{
							cell=row.createCell();//创建单元格
						}
	 					setCellText(tmpCell,cell,list.get(j),list.size());//将模板单元格的所有样式赋给新的单元格
		    		}
				}
				//删除模版行
				if(isDelTmpRow){
					table.removeRow(0);
				}
			}
		}
	}
	
	/**
	 * @Description 创建单元格
	 * @param tmpCell  模板单元格
	 * @param cell     新增加的单元格
	 * @param text     需要填充的内容
	 * @param col      当前表格的列数
	 * @throws Exception
	 */
	public  static void setCellText(XWPFTableCell tmpCell,XWPFTableCell cell,String text,int col) throws Exception{
		CTTc cttc2 = tmpCell.getCTTc();
		CTTcPr ctPr2=cttc2.getTcPr();
		
		CTTc cttc = cell.getCTTc();
		CTTcPr ctPr = cttc.addNewTcPr();
		cell.setColor(tmpCell.getColor());
		
		if(ctPr2.getTcW()!=null){
			//TODO 8600是生成后的pdf中表格最大的宽度,此处设置生成后每一列的列宽都是相同的
			ctPr.addNewTcW().setW(new BigInteger("8600").divide(new BigInteger(String.valueOf(col))));
			ctPr.getTcW().setType(ctPr2.getTcW().getType());
		}
		
		
		//必须添加这段代码(添加规则是根据报错信息一点一点调整的,具体原因不太清楚,猜测是poi的代码存在bug没有做非空判断)
		//TODO 开始  
		if(null==ctPr2.getShd()){
			CTShd ctshd = ctPr2.addNewShd();
			ctshd.xgetFill();
			ctshd.setColor("auto");
			ctshd.setVal(STShd.CLEAR);
			ctshd.setFill("FFFFFF");//填充颜色
			ctPr.setShd(ctshd);
		}
		//TODO 结束
		
		//垂直居中
		cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
		
		//水平对齐
		CTP ctp = cttc.getPList().get(0);
		CTPPr ctppr = ctp.getPPr();
		if (ctppr == null) {
		    ctppr = ctp.addNewPPr();
		}
		CTJc ctjc = ctppr.getJc();
		if (ctjc == null) {
		    ctjc = ctppr.addNewJc();
		}
		ctjc.setVal(STJc.CENTER); //水平居中
		
		XWPFParagraph cellP=cell.getParagraphs().get(0);
		XWPFRun cellR = cellP.createRun();
		cellR.setFontFamily("宋体");
		cellR.setText(text);
	}
}