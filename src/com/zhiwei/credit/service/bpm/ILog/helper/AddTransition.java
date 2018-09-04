package com.zhiwei.credit.service.bpm.ILog.helper;

/**
 * @description 添加元素之间的连线
 * @author YHZ
 * @createtime 2011-4-14PM
 * 
 */
public class AddTransition {
	/**
	 * 定长,value=20
	 */
	private static final int currentWidth = 20;
	
	/**
	 * 开始，结束节点文本的高度,value=12
	 */
	private static final int STARTLABELHeight = 12;

	/**
	 * A,B,C,D坐标组成的字符串,格式eg:str=A+";"+B;
	 */
	private String str = "";
	
	private String A = "";
	private String B = "";
	private String C = "";
	private String D = "";
	
	/**
	 * @description 获取折线折点坐标组成的字符串
	 * @param start
	 * 			开始引线,x,y属性和属性值
	 * @param end 
	 *	 		结束引线,x,y属性和属性值
	 * @param starts 
	 * 			开始Element对象的属性x,y,width,height
	 * @param ends 
	 * 			结束Element对象的属性x,y,width,height
	 * @param nodeType 
	 * 			引出折线的节点类型 
	 * @param endNodeType
	 * 			折线的终点对应的节点类型
	 * @return String
	 */
	public String getPointString(String start, String end, int[] starts, int[] ends, String nodeType, String endNodeType){
		if(start != null && start.equals("x=0")){
			// x=0
			if (end != null && end.equals("x=0"))
				getPoint_x_0_x_0(starts, ends); // x=0,x=0
			else if (end != null && end.equals("x=1"))
				getPoint_x_0_x_1(starts, ends, nodeType, endNodeType); // x=0,x=1
			else if (end != null && end.equals("y=0"))
				getPoint_x_0_y_0(starts, ends); // x=0,y=0
			else if(end != null && end.equals("y=1"))
				getPoint_x_0_y_1(starts, ends); // x=0,y=1
		} else if (start != null && start.equals("x=1")){
			// x=1
			if (end != null && end.equals("x=0"))
				getPoint_x_1_x_0(starts, ends, nodeType, endNodeType); // x=1,x=0
			else if (end != null && end.equals("x=1"))
				getPoint_x_1_x_1(starts, ends); // x=1,x=1
			else if (end != null && end.equals("y=0"))
				getPoint_x_1_y_0(starts, ends); // x=1,y=0
			else if(end != null && end.equals("y=1"))
				getPoint_x_1_y_1(starts, ends); // x=1,y=1
		} else if (start != null && start.equals("y=0")){
			// y=0
			if(end != null && end.equals("x=0"))
				getPoint_y_0_x_0(starts, ends, nodeType); // y=0,x=0
			else if(end != null && end.equals("x=1"))
				getPoint_y_0_x_1(starts, ends, nodeType); // y=0,x=1
			else if (end != null && end.equals("y=0"))
				getPoint_y_0_y_0(starts, ends, nodeType); // y=0,y=0
			else if(end != null && end.equals("y=1"))
				getPoint_y_0_y_1(starts, ends, nodeType, endNodeType); // y=0,y=1
		} else if(start != null && start.equals("y=1")) {
			// y=1
			if(end != null && end.equals("x=0"))
				getPoint_y_1_x_0(starts, ends, nodeType); // y=1,x=0
			else if(end != null && end.equals("x=1"))
				getPoint_y_1_x_1(starts, ends, nodeType); // y=1,x=1
			else if (end != null && end.equals("y=0"))
				getPoint_y_1_y_0(starts, ends, nodeType, endNodeType); // y=1,y=0
			else if(end != null && end.equals("y=1"))
				getPoint_y_1_y_1(starts, ends, nodeType); // y=1,y=1
		}
		return str;
	}
	
	// ///////////////////////////////////////////////////////////////
	// //////////////////开端[NO1-NO4]：x=0[如下]//////////////////////////////
	// //////////////////////////////////////////////////////////////
	
	/**
	 * @description NO1:x=0,x=0；分为：两点，四点区域
	 * @param starts
	 *            折线开始对应的Element对象中的属性(x,y,width,height)
	 * @param ends
	 *            折线结束对应的Element对象中的属性(x,y,width,height)
	 * @return 折点坐标字符串
	 */
	private String getPoint_x_0_x_0(int[] starts, int[] ends) {
	    // 1.区域一：正上方,正下方,结束节点面积[两点]
		// 条件:a.x>xn,y<yn-hm/2;[正下方]
		//      b.x>xn,x<xn+wn,y>yn-hm/2,y<yn+hn-hm/2;[结束节点的部分]
		//      c.x>xn,y>yn+hn-hm/2[正下方]
		boolean bo = starts[1] <= ends[1] - starts[3] / 2 || (starts[0] <= ends[0] + ends[2] && 
		starts[1] >= ends[1] - starts[3] / 2 && starts[1] <= ends[1] + ends[3] - starts[3] / 2) || 
		starts[1] >= ends[1] + ends[3] - starts[3] / 2;
		if(starts[0] >= ends[0] && bo){
			A = (ends[0] - currentWidth) + "," + (starts[1] + starts[3] / 2 );
			B = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B;
		} else if(starts[0] >= ends[0] + ends[2] && starts[1] >= ends[1] - starts[3] / 2 && starts[1] <= ends[1] + ends[3] / 2 - starts[3] / 2){
			// 2.区域二：右边偏上[四点]
			// 条件：x>xn+wn,y>yn-hm/2,y<yn+hn/2-hm/2
			// A.坐标([xm+xn+wn]/2,ym+hm/2)
			// B.坐标([xm+xn+wn]/2,yn-定长) AB的x轴坐标相同
			// C.坐标(xn-定长,yn-定长) BC的y轴坐标相同
			// D.坐标(xn-定长,yn+hn/2) CD的x轴坐标相同
			A = ((starts[0] + ends[0] + ends[2]) / 2) + "," + (starts[1] + starts[3] / 2);
			B = ((starts[0] + ends[0] + ends[2]) / 2) + "," + (ends[1] - currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] - currentWidth);
			D = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] >= ends[0] + ends[2] && starts[1] >= ends[1] + ends[3] / 2 - starts[3] / 2 && starts[1] <= ends[1] + ends[3] - starts[3] / 2){
			// 3.区域三：右边偏下[四点]
			// 条件：x>xn+wn,y>yn+hn/2-hm/2,y<yn+hn-hm/2
			// A.((xn+wn+xm)/2,ym+hm/2)
			// B.((xn+wn+xm)/2,yn+hn+定长)
			// C.(xn-定长,yn+hn+定长)
			// D.(xn-定长,yn+hn/2)
			A = ((starts[0] + ends[0] + ends[2]) / 2) + "," + (starts[1] + starts[3] / 2);
			B = ((starts[0] + ends[0] + ends[2]) / 2) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			D = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] <= ends[0] && starts[1] <= ends[1] + ends[3] / 2 - starts[3] / 2 && starts[1] >= ends[1] + ends[3] / 2 - starts[3] / 2) {
			// 5.区域五：左边偏下[四点]
			// 条件：x<xn,y>yn+hn/2-hm/2,y<yn+hn/2-hm/2
			// A.(xm-wm/2,ym+hm/2)
			// B.(xm-wm/2,ym-定长)
			// C.((xm+wm+xn)/2,ym-定长)
			// D.((xm+wm+xn)/2,yn+hn/2)
			A = (starts[0] - starts[2] / 2) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] - starts[2] / 2) + "," + (starts[1] - currentWidth);
			C = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (starts[1] - currentWidth);
			D = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] <= ends[0] && starts[1] >= ends[1] - starts[3] / 2 && starts[1] <= ends[1] + ends[3] / 2 - starts[3] / 2){
			// 6.区域六：左边偏上[四点]
			// 条件：x<xn,y>yn-hm/2,y<yn+hn/2 - hm/2
			// A.(xm-wm/2,ym+hm/2)
			// B.(xm-wm/2,ym+hm+定长)
			// C.((xm+wm+xn)/2,yn+hn+定长)
			// D.((xm+wm+xn)/2,yn+hn/2)
			A = (starts[0] - starts[2] / 2) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] - starts[2] / 2) + "," + (starts[1] + starts[3] + currentWidth);
			C = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (starts[1] + starts[3] + currentWidth);
			D = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else {
			// 4.区域四：左上下角[两点]
			// 条件：x<xn,y<yn-hm/2 || x<xn,y>yn+hn/2-hm/2
			A = (starts[0] - currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B;
		}
		return str;
	}

	/**
	 * @description NO2:x=0,x=1；存在直线；分为：直线，两点，四点区域
	 * @param starts
	 * @param ends
	 * @param nodeType
	 * @param endNodeType
	 * @return
	 */
	private String getPoint_x_0_x_1(int[] starts, int[] ends, String nodeType, String endNodeType) {
		// 1.区域判断
		// 条件：x < xn+wn
		boolean isFourth = starts[0] < ends[0] + ends[2];
		if (isFourth) { // 四点连接(四个区域)
			// x=0,x=1 
			// 区域一
			if (starts[0] <= (ends[0] + ends[2]) && starts[1] <= ends[1] - starts[3]) {
				// 四点区域一(正上方)： 
				// 条件：x<xn+wn,y<yn-hm
				// A.坐标(xm-定长,ym+hm/2)
				// B.坐标(xm-定长,[ym+yn-hm/2]/2)
				// C.坐标(xn+wn+定长,[ym+yn-hm/2]/2)
				// D.坐标(xn+wn+定长,yn+hn/2) 
				A = (starts[0] - currentWidth) + "," + (starts[1] + starts[3] / 2);
				B = (starts[0] - currentWidth) + "," + (starts[1] + starts[3] + ends[1]) / 2;
				C = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] + starts[3] + ends[1]) / 2;
				D = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			} else if (starts[0] <= ends[0] && starts[1] <= ends[1] + ends[3] / 2 - starts[3] / 2 && starts[1] >= ends[1] - starts[3]){
				 // 四点区域三(左上角)： 
				// 条件：x<xn+wn,y<yn+hn/2-hm/2,y>yn-hm
				// A.坐标(xm-hm/2-定长,ym+hm/2)
				// B.坐标(xn-hm/2-定长,yn+hn+定长)
				// C.坐标(xm+wn+定长,yn+hn+定长)
				// D.坐标(xn+wn+定长,yn+hn/2)
				A = (starts[0] -currentWidth - starts[3] / 2) + "," + (starts[1] + starts[3] / 2);
				B = (starts[0] - currentWidth - starts[3] / 2) + "," + (ends[1] + ends[3] + currentWidth);
				C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
				D = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			} else if (starts[0] <= ends[0] && starts[1] >= (ends[1] + ends[3] / 2 - starts[3] / 2) && starts[1] <= ends[1] + ends[3]) { // 区域二
				// 四点区域二(左下角)： 
				// 条件：x<xn,y>yn+hn/2-hm/2,y<yn+hn
				// A.坐标(xm-定长,ym+hm/2)
				// B.坐标(xm-定长,yn-定长)
				// C.坐标(xn+wn+定长,yn-定长)
				// D.坐标(xn+wn+定长,yn+hn/2)
				A = (starts[0] - currentWidth) + "," + (starts[1] + starts[3] / 2);
				B = (starts[0] - currentWidth) + "," + (ends[1] - currentWidth);
				C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] - currentWidth);
				D = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			} else if (starts[0] >= ends[0] && starts[0] <= ends[0] + ends[2] && starts[1] >= (ends[1] + ends[3] / 2 - starts[3] / 2) && starts[1] <= ends[1] + ends[3]){
				// 四点区域(左下偏右[结束元素覆盖区域]):
				// 条件：x<xn+wn && x>xn,y>yn+hn/2-hm/2,y<yn+hn
				A = (ends[0] - currentWidth) + "," + (starts[1] + starts[3] / 2);
				B = (ends[0] - currentWidth) + "," + (ends[1] - currentWidth);
				C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] - currentWidth);
				D = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
				
			} else if (starts[0] >= ends[0] && starts[0] <= ends[0] + ends[2] && starts[1] <= ends[1] + ends[3] / 2 - starts[3] / 2 && starts[1] >= ends[1] - starts[3]){
				// 四点区域(左上角偏右[结束元素覆盖区域]):
				// 条件：x>xn,x<xn+wn,y<yn+hn/2-hm/2,y>yn-hm
				A = (ends[0] -currentWidth) + "," + (starts[1] + starts[3] / 2);
				B = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
				C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
				D = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			} else {
				// 四点区域四(正下方): 
				// A.坐标(xm-定长,ym+hm/2)
			    // B.坐标(xm-定长,(ym+yn+hn)/2)
				// C.坐标(xn+wn+定长,(ym+yn+hn)/2)
				// D.坐标(xn+wn+定长,yn+hn/2)
				A = (starts[0] - currentWidth) + "," + (starts[1] + starts[3] / 2);
				B = (starts[0] - currentWidth) + "," + ((starts[1] + ends[1] + ends[3]) / 2);
				C = (ends[0] + ends[2] + currentWidth) + "," + ((starts[1] + ends[1] + ends[3]) / 2);
				D = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			}
			str = A + ";" + B + ";" + C + ";" + D;
		} else { // 零点,两点连接
			// 0=0,x=1零点连线操作
			// 条件：ym+hm/2=yn+hn/2+8
			int value = Math.abs(starts[1] + starts[3] - ends[1] - ends[3] / 2);
			int subLength = getNodesSubLength(nodeType, endNodeType);
			if(value >= 0 && value <= subLength){
				str = "";
			} else {
				// x=0,x=1两点连线操作 
				A = ((starts[0] + ends[0] + ends[2]) / 2) + "," + (starts[1] + starts[3] / 2);
				B = ((starts[0] + ends[0] + ends[2]) / 2) + "," + (ends[1] + ends[3] / 2);
				str = A + ";" + B;
			}
		}
		// 求知表达式：
		return str;
	}

	/**
	 * @description NO3:x=0,y=0;分为：一点，三点区域
	 * @param starts
	 * @param ends
	 * @return
	 */
	private String getPoint_x_0_y_0(int[] starts, int[] ends) {
		// 1.区域判断
		if (starts[0] >= ends[0] && starts[1] <= ends[1] - starts[3] / 2) {
			// 一点区域：右上角
			// x=0,y=0一点连线操作 
			// 条件：x>=xn+wn/2,y<=yn-hm/2
			// A.坐标(xn+wn/2,ym+hm/2)
			A = (ends[0] + ends[2] / 2) + "," + (starts[1] + starts[3] / 2);
			str = A;
		} else if(starts[0] <= ends[0] + ends[2] / 2 && starts[1] <= ends[1] - starts[3] / 2){
			// 三点区域一：左上角 
			//条件：x<xn+wn/2,y<yn-hm/2
			// A.坐标(xm-定长,ym+hm/2)
			// B.坐标(xm-定长,(ym+hm+yn)/2)
			// C.坐标(xn+wn/2,(ym+hm+yn)/2)
			A = (starts[0] - currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] - currentWidth) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			C = (ends[0] + ends[2] / 2) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] <= ends[0] && starts[1] >= ends[1] - starts[3]){
			// 三点区域二(左下角): 
			// 条件：x<=xn,y>=yn-hm
			// A.坐标(xm-定长,ym+hm/2)
			// B.坐标(xm-定长,yn-定长)
			// C.坐标(xn+wn/2,yn-定长)
			A = (starts[0] - currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] - currentWidth) + "," + (ends[1] - currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] >= ends[0] && starts[1] >= ends[1] - starts[3] && starts[0] <= ends[1] + ends[2]){
			// 条件：x>=xn,x<=xn+wn,y>=yn-hm
			// 三点区域三(正下方): 
			// A.坐标(xn-定长,ym+hm/2)
			// B.坐标(xn-定长,yn-定长)
			// C.坐标(xn+wn/2,yn-定长)
			A = (ends[0] - currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (ends[0] - currentWidth) + "," + (ends[1] - currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C;
		} else {
			//条件:x>=xn+wn,y>=yn-hm/2
			// 三点区域四(右边偏下): 
			// A.坐标((xn+wn+xm)/2,ym+hm/2)
			// B.坐标((xn+wn+xm)/2,yn-定长)
			// C.坐标(xn+wn/2,yn-定长) 
			A = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (starts[1] + starts[3] / 2);
			B = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (ends[1] - currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth); 
			str = A + ";" + B + ";" + C;
		}
		return str;
	}

	/**
	 * @description NO4:x=0,y=1，分为：一点，三点区域
	 * @param starts
	 * @param ends
	 * @return
	 */
	private String getPoint_x_0_y_1(int[] starts, int[] ends) {
		// x=0,y=1一点连线操作 
		// 1.区域判断
		if (starts[0] >= ends[0] + ends[2] / 2 && starts[1] >= ends[1] + ends[3] - starts[3] / 2 ) { 
			// 一点区域：右下角
			// 条件：x>=xn+wn/2,y>=yn+hn-hm/2
			// A.坐标(xn+wn/2,ym+hm/2)
			A = (ends[0] + ends[2] / 2) + "," + (starts[1] + starts[3] / 2);
			str = A;
		} else if (starts[0] <= ends[0] + ends[2] / 2 && starts[1] >= ends[1] + ends[3]){
			// 三点区域一：左下角 
			//条件：x<xn+wn/2,y<yn+hn
			// A.坐标(xm-定长,ym+hm/2)
			// B.坐标(xm-定长,(yn+hn+ym)/2)
			// C.坐标(xn+wn/2,(yn+hn+ym)/2)
			A = (starts[0] - currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] - currentWidth) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			C = (ends[0] + ends[2] / 2) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] <= ends[0] && starts[1] <= ends[1] + ends[3]){
			// 条件：x<=xn,y>=yn+hn
			// 三点区域二：左上角 
			// A.坐标(xm-wm/2-定长,ym+hm/2)
			// B.坐标(xm-wm/2-定长,yn+hm+定长)
			// C.坐标(xn+wn/2,yn+hn+定长)
			A = (starts[0] - starts[2] / 2 - currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] - starts[2] / 2 - currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] >= ends[0] && starts[0] <= ends[0] + ends[2] && starts[1] <= ends[1] + ends[3]){
			// 三点区域三：正上方
			// 条件：x>xn,x<xn+wn,y<yn+hn
			// A.坐标(xn-定长,ym+hm/2)
			// B.坐标(xn-定长,yn+hn+定长)
			// C.坐标(xn+wn/2,yn+hn+定长)
			A = (ends[0] - currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] +ends[3] + currentWidth );
			str = A + ";" + B + ";" + C;
		} else {
			// 三点区域四：右上角
			// 条件：x>xn+wn,y<yn+hn-hm/2
			// A.坐标((xn+wn+xm)/2,ym+hm/2)
			// B.坐标((xn+wn+xm)/2,yn+hn+定长)
			// C.坐标(xn+wn/2,yn+hn+定长)
			A = ((starts[0] + ends[0] + ends[2]) / 2) + "," + (starts[1] + starts[3] / 2);
			B = ((starts[0] + ends[0] + ends[2]) / 2) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C;
		}
		return str;
	}

	
	// ///////////////////////////////////////////////////////////////
	// //////////////////开端[NO5-NO9]：x=1[如下]//////////////////////////////
	// //////////////////////////////////////////////////////////////

	/**
	 * @description NO5:x=1,x=0；存在直线；分为：直线，两点，四点区域
	 * @param starts
	 * @param ends
	 * @param startNode
	 * @param endNode
	 * @return
	 */
	private String getPoint_x_1_x_0(int[] starts, int[] ends, String startNode, String endNode) {
		// 1.区域判断
		if (starts[0] <= ends[0] - starts[2]) {
			// 零点区域
			// 条件:x<xn-wm
			int value = Math.abs(starts[1] + starts[3] / 2 - ends[1] - ends[3] / 2);
			int subLength = getNodesSubLength(startNode, endNode);
			if(value >= 0 && value <= subLength){
				str = "";
			} else {
				// x<xn-wm
				// 两点区域(右边):
				// A.坐标((xm+wm+xn)/2,ym+hm/2)
				// B.坐标((xm+wm+xn)/2,yn+hn/2)
				A = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (starts[1] + starts[3] / 2);
				B = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (ends[1] + ends[3] / 2);
				str = A + ";" + B;
			}
		} else if (starts[0] >= ends[0] - starts[2] && starts[1] <= ends[1] - starts[3]){
			// 四点区域一：正上方
			//条件：x>xn-wm,y<yn-hm
			// A.坐标xm+wm+定长,ym+hm/2)
			// B.坐标(xm+wm+定长,(ym+hm+yn)/2)
			// C.坐标(xn-定长,(ym+hm+yn)/2)
			// D.坐标(xn-定长,yn+hn/2)
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			C = (ends[0] - currentWidth) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			D = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] >= ends[0] + ends[2] - starts[2] && starts[1] >= ends[1] - starts[3] && starts[1] <= ends[1] + ends[3] / 2 - starts[3] / 2){
			// 四点区域二：右边偏上
			// 条件：x>xn+wn-wm,y>yn-hm,y<yn+hn/2-hm/2
			// A.坐标(xm+wm+定长,ym+hm/2)
			// B.坐标(xm+wm+定长,yn+hn+定长)
			// C.坐标(xn-定长,yn+hn+定长)
			// D.坐标(xn-定长,yn+hn/2)
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + (ends[1] + ends[3]  + currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3]  + currentWidth);
			D = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] >= ends[0] - starts[2] && starts[0] <= ends[0] + ends[2] - starts[2] && starts[1] >= ends[1] - starts[3] && starts[1] <= ends[1] + ends[3] / 2 - starts[3] / 2){
			// 四点区域：右边偏上[结束节点部分]
			// 条件：x>xn-wm,x<xn+wn-wm,y>yn-hm,y<yn+hn/2-hm/2
			// A.坐标(xn+wn+定长,ym+hm/2)
			// B.坐标(xn+wn+定长,yn+hn+定长)
			// C.坐标(xn-定长,yn+hn+定长)
			// D.坐标(xn-定长,yn+hn/2) 
			A = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			D = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] >= ends[0] + ends[2] - starts[2] && starts[1] >= ends[1] + ends[3] / 2 - starts[3] / 2 && starts[1] <= ends[1] + ends[3]){
			// 四点区域三：右边偏下 
			//条件：x>xn+wn-wm,y>yn+hn/2-hm/2,y<yn+hn
			// A.坐标(xm+wm+定长,ym+hm/2)
			// B.坐标(xm+wm+定长,yn-定长)
			// C.坐标(xn-定长,yn-定长)
			// D.坐标(xn-定长,yn+hn/2) 
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + (ends[1] - currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] - currentWidth);
			D = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] >= ends[0] - starts[2] && starts[0] <= ends[0] + ends[2] - starts[2] && starts[1] >= ends[1] + ends[3] / 2 - starts[3] / 2 && starts[1] <= ends[1] + ends[3]){
			// 四点区域：右边偏下[结束节点部分]
			// 条件：x>xn-wm,x<xn+wn-wm,y>yn+hn/2-hm/2,y<yn+hn
			// A.坐标(xn+wn+定长,ym+hm/2)
			// B.坐标(xn+wn+定长,yn-定长)
			// C.坐标(xn-定长,yn-定长)
			// D.坐标(xn-定长,yn+hn/2)
			A = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] - currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] - currentWidth);
			D = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else {
			// 四点区域四：右下角
			// 条件：x>xn-wm,y>yn+hn
			// A.坐标(xm+wm+定长,ym+hm/2)
			// B.坐标(xm+wm+定长,(yn+hn+ym)/2)
			// C.坐标(xn-定长,(yn+hn+ym)/2)
			// D.坐标(xn-定长,yn+hn/2)
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			C = (ends[0] - currentWidth) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			D = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		}
		return str;
	}

	/**
	 * @description NO6:x=1,x=1；分为：两点，四点区域
	 * @param starts
	 * @param ends
	 * @return
	 */
	private String getPoint_x_1_x_1(int[] starts, int[] ends) {
	    // x=1,x=1
		// 左上角
		boolean bo_t = (starts[0] <= ends[0] + ends[2] - starts[2] && starts[1] <= ends[1] - starts[3] / 2) || (starts[0] >= ends[0] - starts[2] &&
				starts[0] <= ends[0] + ends[2] && starts[1] >= ends[1] - ends[3] / 2 && starts[1] < ends[1] + ends[3] / 2 - starts[3] / 2);
		// 左下角
		boolean bo_b = (starts[0] <= ends[0] + ends[2] - starts[2] && starts[1] >= ends[1] + ends[3] - starts[3] / 2) || (
				starts[0] >= ends[0] - starts[2] && starts[0] <= ends[0] + ends[2] && starts[1] >= ends[1] + ends[3] / 2 - starts[3] / 2 && 
				starts[1] <= ends[1] + ends[3] - starts[3] / 2);
		if(bo_t){
			// 两点区域：左上角，阴影部分上方
			// 条件：a.左上角：x<xn+wn-wm,y<yn-hm/2;
	    	//       b.阴影：x>xn-wm,x<xn+wn,y>yn-hn/2,y<yn+hn/2-hm/2
			// A.坐标(xn+wn+定长,ym+hm/2)
			// B.坐标(xn+wn+定长,yn+hn/2)
			A = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B;
		} else if (starts[0] >= ends[0] + ends[2] && starts[1] >= ends[1] + ends[3] / 2 - starts[3] && starts[1] <= ends[1] + ends[3] / 2 - starts[3] / 2) {
			// 四点区域：右边偏上
			// 条件： x>xn+wn,y>yn+hn/2-hm,y<yn+hn/2-hm/2
			// A.(xm+wm+定长,ym+hm/2)
			// B.(xm+wm+定长,ym+hm+定长)
			// C.((xn+wn+wm)/2,ym+hm+定长)
			// D.((xn+wn+wm)/2,yn+hn/2)
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] + currentWidth);
			C = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (starts[1] + starts[3] + currentWidth);
			D = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] >= ends[0] + ends[2] && starts[1] >= ends[1] + ends[3] / 2 - starts[3] / 2 && starts[1] <= ends[1] + ends[3] / 2) {
			// 四点区域：右边偏下
			// 条件：x>xn+wn,y>yn+hn/2-hm/2,y<yn+hn/2
			// A.(xm+wm+定长,ym+hm/2)
			// B.(xm+wm+定长,ym-定长)
			// C.((xn+wn+xm)/2,ym-定长)
			// D.((xn+wn+xm)/2,yn+定长)
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] - currentWidth);
			C = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (starts[1] - currentWidth);
			D = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(bo_b){
			// 四点区域：左下角,阴影部分下方 
			// 条件：a.左下角:x<xn+wn-wm/2,y>yn+hn-hm/2;
			//       b.阴影:x>xn-wm,x<xn+wn,y>yn+hn/2-hm/2,y<yn+hn-hm/2
			// A.(xn+wn+定长,ym+hm/2)
			// B.(xn+wn+定长,yn+hn/2)
			A = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B;
		} else if(starts[0] <= ends[0] - starts[2] && starts[1] >= ends[1] - starts[3] / 2 && starts[1] <= ends[1] + ends[3] / 2 - starts[3] / 2){
			// 四点区域：正左边偏上
			// 条件：x<xn-wm,y>yn-hm/2,y<yn+hn/2-hm/2
			// A.坐标((xm+wm+xn)/2,ym+hm/2)
			// B.坐标((xm+wm+xn)/2,yn-定长)
			// C.坐标(xn+wn+定长,yn-定长)
			// D.坐标(xn+wn+定长,yn+hn/2)
			A = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (starts[1] + starts[3] / 2);
			B = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (ends[1] - currentWidth);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] - currentWidth);
			D = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] <= ends[0] - starts[2] && starts[1] >= ends[1] + ends[3] / 2 - starts[3] / 2 && starts[1] <= ends[1] + ends[1] - starts[3] / 2){
			// 四点区域：正左边偏下
			// 条件：x<xn-wm,y>yn+hn/2-hm/2,y<yn+hn-hm/2
			// A.坐标((xm+wm+xn)/2,ym+hm/2)
			// B.坐标((xm+wm+xn)/2,yn-定长)
			// C.坐标(xn+wn+定长,yn-定长)
			// D.坐标(xn+wn+定长,yn+hn/2)
			A = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (starts[1] + starts[3] / 2);
			B = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			D = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else {
			// 两点区域：右上角，右下角
			// 条件：a.右上角 x>xn+wn-wm,y<yn+hn/2-hm;
			//       b.右下角 x>xn+wn-wm,y>yn+hn/2
			// A.坐标(xm+xw+定长,ym+hm/2)
			// B.坐标(xm+xw+定长,yn+hn/2)
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B;
		}
		return str;
	}

	/**
	 * @description NO7:x=1,y=0，分为：一点，三点区域
	 * @param starts
	 * @param ends
	 * @return
	 */
	private String getPoint_x_1_y_0(int[] starts, int[] ends) {
		// x=1,y=0
		if(starts[0] <= ends[0] + ends[2] / 2 - starts[2] && starts[1] <= ends[1] - starts[3] / 2){
			// 一点区域：左上角
			// 条件:x<xn+wn/2-wm,y<yn-hm/2
			// A.坐标(xn+wn/2,ym+hm/2)
			A = (ends[0] + ends[2] / 2) + "," + (starts[1] + starts[3] / 2);
			str = A;
		} else if (starts[0] <= ends[0] - starts[2] && starts[1] >= ends[1] - starts[3] / 2){
			// 三点区域：左下角
			// 条件：x<xn-wm,y<yn-hm/2
			// A.坐标((xm+wm+xn)/2,ym+hm/2)
			// B.坐标((xm+wm+xn)/2,yn-定长)
			// C.坐标(xn+wn/2,yn-定长)
			A = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (starts[1] + starts[3] / 2);
			B = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (ends[1] - currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C;
		} else if (starts[0] >= ends[0] + ends[2] / 2 - starts[2] && starts[1] <= ends[1] - starts[3]) {
			// 三点区域：右上角
			// 条件：x>xn+wn/2-wm,y<yn-hm
			// A.(xm+wm+定长,ym+hm/2)
			// B.(xm+wm+定长,(ym+hm+yn)/2)
			// C.(xn+wn/2,(ym+hm+yn)/2)
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			C = (ends[0] + ends[2] / 2) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			str = A + ";" + B + ";" + C;
		} else if (starts[0] >= ends[0] + ends[2] - starts[2] && starts[1] >= ends[1] - starts[3]){
			// 三点区域：右下角 
			// 条件：x>xn+wn-wm,y>yn-hm
			// A.(xm+定长,ym+hm/2)
			// B.(xm+定长,yn-定长)
			// C.(xn+wn/2,yn-定长)
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + (ends[1] - currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C;
		} else {
			// 三点区域(正下方): 
			// 条件:x>xn-wm,x<xn+wn-wm,y>yn-hm
			// A.(xn+wn+定长,ym+hm/2)
			// B.(xn+wn+定长,yn-定长)
			// C.(xn+wn/2,yn-定长)
			A = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] - currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C;
		}
		return str;
	}

	
	/**
	 * @description NO8:x=1,y=1，分为：一点，三点区域
	 * @param starts
	 * @param ends
	 * @return
	 */
	private String getPoint_x_1_y_1(int[] starts, int[] ends) {
		// x=1,y=1 
		if (starts[0] <= ends[0] - starts[2] && starts[1] <= ends[1] + ends[3] - starts[3] / 2){
			// 三点区域：左上角
			// 条件：x<xn-wm,y<yn+hn-hm/2
			// A.坐标((xm+wm+xn)/2,ym+hm/2)
			// B.坐标((xm+wm+xn)/2,yn+hn+定长)
			// C.坐标(xn+wn/2,yn+hn+定长)
			A = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (starts[1] + starts[3] / 2);
			B = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] <= ends[0] + ends[2] / 2 - starts[2] && starts[1] >= ends[1] + ends[3] - starts[3] / 2){
			// 一点区域：左下角 
			// 条件:x<xn+wn/2-wm,y>yn+hn-hm/2
			// A.坐标(xn+wn/2,ym+hm/2)
			A = (ends[0] + ends[2] / 2) + "," + (starts[1] + starts[3] / 2);
			str = A;
		} else if (starts[0] >= ends[0] + ends[2] - starts[2] && starts[1] <= ends[1] + ends[3]){
			// 三点区域：右上角  
			// 条件：x>xn+wn-wm,y<yn+hn
			// A.(xm+wm+定长,ym+hm/2)
			// B.(xm+wm+定长,yn+hn+定长)
			// C.(xn+wn/2,yn+hn+定长)
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] >= ends[0] + ends[2] / 2 - starts[2] && starts[1] >= ends[1] + ends[3]) { 
			// 三点区域：右下角
			// 条件：x>xn+wn/2-wm,y>yn+hn
			// A.(xm+wm+定长,ym+hm/2)
			// B.(xm+wm+定长,(yn+hn+ym)/2)
			// C.(xn+wn/2,(yn+hn+ym)/2)
			A = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (starts[0] + starts[2] + currentWidth) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			C = (ends[0] + ends[2] / 2) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			str = A + ";" + B + ";" + C;
		} else {
			// 三点区域：正上方，包含[结束节点部分] 
			// 条件:x>xn-wm,x<xn+wn-wm,y<yn+hn-hm/2
			// A.(xn+wn+定长,ym+hm/2)
			// B.(xn+wn+定长,yn+hn+定长)
			// C.(xn+wn/2,yn+hn+定长)
			A = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] + starts[3] / 2);
			B = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C;
		} 
		return str;
	}
	
	// ///////////////////////////////////////////////////////////////
	// //////////////////开端[NO9-NO12]：y=0[如下]//////////////////////////////
	// //////////////////////////////////////////////////////////////
	
	
	/**
	 * @description NO9:y=0,x=0，分为：一点，三点区域
	 * @param starts
	 * @param ends
	 * @param nodeType 
	 * @return
	 */
	private String getPoint_y_0_x_0(int[] starts, int[] ends, String nodeType) {
		// y=0,x=0 
		if (starts[0] <= ends[0] - starts[2] && starts[1] <= ends[1] + starts[3] / 2){
			// 三点区域：左上角  
			// 条件：x<xn-wm,y<yn+hm/2
			// A.坐标(xm+wm/2,ym-定长)
			// B.坐标((xm+wm+xn)/2,ym-定长)
			// C.坐标((xm+wm+xn)/2,yn+hn/2) 
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (starts[1] - currentWidth);
			C = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] <= ends[0] - starts[2] / 2 && starts[1] >= ends[1] + ends[3] / 2){
			// 一点区域：左下角 
			// 条件:x<xn-wm/2,y>yn+hn/2
			// A.坐标(xm+wm/2,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] + ends[3] / 2);
			str = A;
		} else if (starts[0] >= ends[0] - starts[2] && starts[1] <= ends[1]){
			// 三点区域：右上角
			// 条件:x>xn-wm,y<yn
			// A.(xm+wm/2,ym-定长)
			// B.(xn-定长,ym-定长)
			// C.(xn-定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = (ends[0] - currentWidth) + "," + (starts[1] - currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] >= ends[0] - starts[2] / 2 && starts[1] >= ends[1] + ends[3]){
			// 三点区域：右下角
			// 条件：x>xn-wm/2,y>yn+hn
			// A.(xm++wm/2,(yn+hn+ym)/2)
			// B.(xn-定长,(yn+hn+ym)/2)
			// C.(xn-定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, (ends[1] + ends[3] + starts[1]) / 2);
			B = (ends[0] - currentWidth) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		} else {
			// 三点区域(正右方): 
			// 条件：x>xn-wn,y>yn+hn
			// A.(xm+wm/2,yn-定长)
			// B.(xn-定长,yn-定长)
			// C.(xn-定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] - currentWidth);
			B = (ends[0] - currentWidth) + "," + (ends[1] - currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		}
		return str;
	}

	
	/**
	 * @description NO10:y=0,x=1，分为：一点，三点区域
	 * @param starts
	 * @param ends
	 * @param nodeType
	 * @return
	 */
	private String getPoint_y_0_x_1(int[] starts, int[] ends, String nodeType) {
		// y=0,x=1 
		if (starts[0] <= ends[0] + ends[2] && starts[1] <= ends[1]){
			// 三点区域：左上角 
			// 条件:x<xn+wn,y<yn
			// A.(xm+wm/2,ym+定长)
			// B.(xn+wn+定长,ym+定长)
			// C.(xn+wn+定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] - currentWidth);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] <= ends[0] + ends[2] - starts[2] / 2 && starts[1] >= ends[1] + ends[3]) {
			// 三点区域：左下角 
			// 条件：x<xn+wn-wm/2,y>yn+hn
			// A.(xm+wm/2,(yn+hn+ym)/2)
			// B.(xn+wn+定长,(yn+hn+ym)/2)
			// C.(xn+wn+定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, (ends[1] + ends[3] + starts[1]) / 2);
			B = (ends[0] + ends[2] + currentWidth) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] >= ends[0] + ends[2] && starts[1] <= ends[1] + ends[3] / 2) {
			// 三点区域：右上角 
			// 条件：x>xn+wn,y<yn+hn/2
			// A.(xm+wm/2,ym-定长)
			// B.((xn+wn+xm)/2,ym-定长)
			// C.((xn+wn+xm)/2,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (starts[1] - currentWidth);
			C = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] >= ends[0] + ends[2] - starts[2] / 2 && starts[1] >= ends[1] + ends[3] / 2){
			// 一点区域：右下角
			// 条件:x>xn+wn-wm/2,y>yn+hn/2
			// A.坐标(xm+wm/2,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] + ends[3] / 2);
			str = A;
		} else {
			// 三点区域：正左边
			// 条件：x<xn+wn,y>yn,y<yn+hn
			// A.坐标(xm+wm/2,yn-定长)
			// B.坐标(xn+wn+定长,yn-定长)
			// C.坐标(xn+wn+定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] - currentWidth);
			B = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] - currentWidth);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		}  
		return str;
	}

	/**
	 * @description NO11:y=0,y=0，分为：两点，四点区域
	 * @param starts
	 * @param ends
	 * @param nodeType
	 * @return
	 */
	private String getPoint_y_0_y_0(int[] starts, int[] ends, String nodeType) {
		if((starts[0] <= ends[0] + ends[2] /2 - starts[2] || starts[0] >= ends[0] + ends[2] / 2) && starts[1] <= ends[1]){
			// 两点区域(左上角和右上角): 
			// 条件：(x<xn+wn/2-wm || x>xn+wn/2) && y<yn
			// A.(xm+wm/2,ym-定长)
			// B.(xn+wn/2,ym-定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = (ends[0] + ends[2] / 2) + "," + (starts[1] - currentWidth);
			str = A + ";" + B;
		} else if(starts[0] >= ends[0] + ends[2] / 2 - starts[2] && starts[0] <= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[1] <= ends[1] - starts[3]) {
			// 四点区域(正上方偏左): 
			// 条件：x>xn+wn/2-wm,x<xn+wn/2-wm/2,y<yn-hm
			// A.(xm+wm/2,ym-定长)
			// B.(xm+wm+定长,ym-定长)
			// C.(xm+wm+定长,(ym+hm+yn)/2)
			// D.(xn+wn/2,(ym+hm+yn)/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = (starts[0] + starts[2] + currentWidth) + "," + (starts[1] - currentWidth);
			C = (starts[0] + starts[2] + currentWidth) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			D = (ends[0] + ends[2] / 2) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] >= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[0] <= ends[0] + ends[2] / 2 && starts[1] <= ends[1] - starts[3]){
			// 四点区域(正上方偏右): 
			// 条件：x>xn+wn/2-wm/2,x<xn+wn/2,y<yn-hm
			// A.(xm+wm/2,ym-定长)
			// B.(xm-定长,ym-定长)
			// C.(xm-定长,(ym+hm+yn)/2)
			// D.(xn+wn/2,(ym+hm+yn)/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = (starts[0] - currentWidth) + "," + (starts[1] - currentWidth);
			C = (starts[0] - currentWidth) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			D = (ends[0] + ends[2] / 2) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] >= ends[0] - ends[2] / 2 && starts[0] <= ends[0] + ends[2] / 2 - starts[2] / 2){
			// 四点区域(下方偏左): 
			// 条件：x>xn-wn/2,x<xn+wn/2-wm/2,y>yn+hn
			// A.(xm+wm/2,(yn+hn+ym)/2)
			// B.(xn-定长,(yn+hn+ym)/2)
			// C.(xn-定长,yn-定长)
			// D.(xn+wn/2,yn-定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, (ends[1] + ends[3] + starts[1]) / 2);
			B = (ends[0] - currentWidth) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			C = (ends[0] - currentWidth) + "," + (ends[1] - currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] >= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[0] <= ends[0] + ends[2] - starts[2] / 2 && starts[1] >= ends[1] + ends[3]) { 
			// 四点区域(下方偏右): 
			// 条件:x>xn+wn/2-wm/2,x<xn+wn-wm/2,y>yn+hn
			// A.(xm+wm/2,(yn+hn+ym)/2)
			// B.(xn+wn+定长,(yn+hn+ym)/2)
			// C.(xn+wn+定长,yn-定长)
			// D.(xn+wn/2,yn-定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, (ends[1] + ends[3] + starts[1]) / 2);
			B = (ends[0] + ends[2] + currentWidth) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] - currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else {
			// 两点区域：左下角，右下角，和阴影部分面积 
			// 条件：a.右下角：x>xn+wn-wm/2,y>yn;
			//       b.右边阴影：x>xn+wn/2-wm/2,x<xn+wn-wm/2,y>yn,y<yn+hn;
			//       c.左下角：x<xn-wm/2,y>yn;
			//       d.左边阴影：x>xn-wm/2,x<xn+wn/2-wm/2,y>yn,y<yn+hn
			// A.(xm+wm/2,yn-定长)
			// B.(xn+wn/2,yn-定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] - currentWidth);
			B = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B;
		}
		return str;
	}

	/**
	 * @description NO12:y=0,y=1，存在直线连接，分为：两点，四点，直线区域
	 * @param starts
	 * @param ends
	 * @param nodeType
	 * @param endNodeType
	 * @return
	 */
	private String getPoint_y_0_y_1(int[] starts, int[] ends, String nodeType, String endNodeType) {
		if(starts[0] <= ends[0] - starts[2] && starts[1] <= ends[1] + ends[3]) {
			// 四点区域：左上角
			// 条件：x<xn-wm,y<yn+hn
			// A.(xm+wm/2,ym-定长)
			// B.((xm+wm+xn)/2,ym-定长)
			// C.((xm+wm+xn)/2,yn+hn+定长)
			// D.(xn+wn/2,yn+hn+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = ((starts[0] + starts[2] + ends[0]) / 2) + "," + (starts[1] - currentWidth);
			C = ((starts[0] + starts[2] + ends[0]) / 2)+","+(ends[1] + ends[3] + currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] >= ends[0] - starts[2] && starts[0] <= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[1] >= ends[1] && starts[1] <= ends[1] + ends[3]){
			// 四点区域(左边阴影部分):
			// 条件：x>xn-wm,x<xn+wn/2-wm/2,y>yn,y<yn+hn
			// A.(xm+wm/2,yn-定长)
			// B.(xn+wn+定长,yn-定长)
			// C.(xn+wn+定长,yn+hn+定长)
			// D.(xn+wn/2,yn+hn+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] - currentWidth);
			B = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] - currentWidth);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] >= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[0] < ends[0] + ends[2] && starts[1] >= ends[1] && starts[1] <= ends[1] + ends[3]) {
			// 四点区域(右边阴影部分):
			// 条件：x>xn+wn/2-wm/2,x<xn+wn,y>yn,y<yn+hn
			// A.(xm+wm/2,yn-定长)
			// B.(xn-定长,yn-定长)
			// C.(xn-定长,yn+hn+定长)
			// D.(xn+wn/2,yn+hn+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] - currentWidth);
			B = (ends[0] - currentWidth) + "," + (ends[1] - currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] >= ends[0] - starts[2] && starts[0] <= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[1] <= ends[1] + ends[3]){
			// 四点区域：上方偏左 
			// 条件：x>xn-wm,x<xn+wn/2-wm/2,y<yn+hn
			// A.(xm+wm/2,ym-定长)
			// B.(xn+wn+定长,ym-定长)
			// C.(xn+wn+定长,yn+hn+定长)
			// D.(xn+wn/2,yn+hn+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] - currentWidth);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] >= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[0] <= ends[0] + ends[2] && starts[1] <= ends[1] + ends[3]) {
			// 四点区域：上方偏右
			// 条件:x>xn+wn/2-wm/2,x<xn+wn,y<yn+hn
			// A.(xm+wm/2,ym-定长)
			// B.(xn-定长,ym-定长)
			// C.(xn-定长,yn+hn+定长)
			// D.(xn+wn/2,yn+hn+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = (ends[0] - currentWidth) + "," + (starts[1] - currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] >= ends[0] + ends[2] && starts[1] <= ends[1] + ends[3]) {
			// 四点区域：右上角
			// 条件： x>xn+wn,y<yn+hn
		    // A.(xm+wm/2,yn-定长)
			// B.((xn+wn+xm)/2,yn-定长)
			// C.((xn+wn+xm)/2,yn+hn+定长)
			// D.(xn+wn/2,yn+hn+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] - currentWidth);
			B = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (starts[1] - currentWidth);
			C = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (ends[1] + ends[3] + currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else {
			// 零点区域
			// 条件:xm+wm/2+8=xn+wn/2
			int value = Math.abs(starts[0] + starts[2] / 2 - ends[0] - ends[2] / 2);
			int subLength = getNodesSubLength(nodeType, endNodeType);
			if(value >= 0 && value <= subLength){
				str = "";
			} else {
				// 两点区域(正下方):  
				// 条件：y>yn+hn
				// A.(xm+wm/2,(yn+hn+ym)/2)
				// B.(xn+wn/2,(yn+hn+ym)/2)
				A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, (ends[1] + ends[3] + starts[1]) / 2);
				B = (ends[0] + ends[2] / 2) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
				str = A + ";" + B;
			}
		}
		return str;
	}
	
	// ///////////////////////////////////////////////////////////////
	// //////////////////开端[NO13-NO16]：y=1[如下]//////////////////////////////
	// //////////////////////////////////////////////////////////////
	
	/**
	 * @description NO13:y=1,x=0，分为：一点，三点区域
	 * @param starts
	 * @param ends
	 * @param nodeType
	 * @return
	 */
	private String getPoint_y_1_x_0(int[] starts, int[] ends, String nodeType) {
		// y=1,x=0 
		if(starts[0] <= ends[0] - starts[2] / 2 && starts[1] <= ends[1] + ends[3] / 2 - starts[3]){
			// 一点区域：左上角 
			// 条件：x<xn-wm/2,y<yn+hn/2-hm
			// A.(xm+wm/2,yn+hn/2) 
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] + ends[3] / 2);
			str = A;
		} else if(starts[0] <= ends[0] - starts[2] && starts[1] >= ends[1] + ends[3] / 2 - starts[3]) {
			// 三点区域：左下角
			// 条件：x<xn-wm,y>yn+hn/2-hm
		    // A.(xm+wm/2,ym+hm+定长)
			// B.(((xm+wm+xn)/2,ym+hm+定长)
			// C.((xn+wn+xm)/2,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = ((starts[0] + starts[2] + ends[0]) / 2 + STARTLABELHeight) + "," + (starts[1] + starts[3] + currentWidth);
			C = ((starts[0] + starts[2] + ends[0]) / 2 + STARTLABELHeight) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] >= ends[0] - ends[2] / 2 && starts[1] <= ends[1] - starts[3]) {
			// 三点区域：右上角
			// 条件：x>xn-wn/2,y<yn-hm
			// A.(xm+wm/2,(ym+hm+yn)/2)
			// B.(xn-定长,(ym+hm+yn)/2)
			// C.(xn-定长,yn+hn/2) 
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, (starts[1] + starts[3] + ends[1]) / 2);
			B = (ends[0] - currentWidth) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2); 
			str = A + ";" + B + ";" + C;
		} else if (starts[0] >= ends[0] - ends[2] && starts[1] >= ends[1] - starts[3] && starts[1] <= ends[1] + ends[3] - starts[3]){
			// 三点区域：正右方
			// 条件：x>xn-wn,y>yn-hm,y<yn+hn-hm
			// A.(xm+wm/2,yn+hn+定长)
			// B.(xn-定长,yn+hn+定长)
			// C.(xn-定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] + ends[3] + currentWidth);
			B = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		} else {
			// 三点区域：右下角
			// 条件:x>xn-wm,y>yn+hn-hm
			// A.(xm+wm/2,ym+hm+定长)
			// B.(xn-定长,ym+hm+定长)
			// C.(xn-定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = (ends[0] - currentWidth) + "," + (starts[1] + starts[3] + currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		}
		return str;
	}

	/**
	 * @description NO14:y=1,x=1，分为：一点，三点区域
	 * @param starts
	 * @param ends
	 * @param nodeType
	 * @return
	 */
	private String getPoint_y_1_x_1(int[] starts, int[] ends,String nodeType) {
		// y=1,x=1 
		if(starts[0] >= ends[0] + ends[2] - starts[2] / 2 && starts[1] <= ends[1] + ends[3] / 2 - starts[3]){
			// 一点区域：右上角 
			// 条件：x>xn+wn-wm/2,y<yn+hn/2-hm
			// A.(xm+wm/2,yn+hn/2) 
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] + ends[3] / 2);
			str = A;
		} else if(starts[0] >= ends[0] + ends[2] && starts[0] >= ends[1] + ends[3] / 2 - starts[3]) {
			// 三点区域：右下角 
			// 条件：x>xn+wn,y>yn+hn/2-hm
			// A.(xm+wm/2,ym+hm+定长)
			// B.((xn+wn+xm)/2,ym+hm+定长)
			// C.((xn+wn+xm)/2,yn+hn/2) 
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (starts[1] + starts[3] + currentWidth);
			C = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (ends[1] + ends[3] / 2); 
			str = A + ";" + B + ";" + C;
		} else if (starts[0] <= starts[0] + starts[2] && starts[1] >= ends[1] + ends[3] - starts[3]){
			// 三点区域：左下角
			// 条件：x<xn+wn,y>yn+hn-hm
			// A.(xm+wm/2,ym+hm+定长)
			// B.(xn+wm+定长,ym+hm+定长)
			// C.(xn+wn+定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] + starts[3] + currentWidth);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		} else if(starts[0] <= ends[0] + ends[2] && starts[1] >= ends[1] - starts[3] && starts[1] <= ends[1] + ends[3] - starts[3]) {
			// 三点区域：正左边
			// 条件:x<xn+wn,y>yn-hm,y<yn+hn-hm
			// A.(xm+wm/2,yn+hn+定长)
			// B.(xn+wn+定长,yn+hn+定长)
			// C.(xn+wn+定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] + ends[3] + currentWidth);
			B = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		} else {
			// 三点区域：右上角
			// 条件：x<xn+wn-wm/2,y<yn-hm
		    // A.(xm+wm/2,(ym+hm+yn)/2)
			// B.(xn+wn+定长,(ym+hm+yn)/2)
			// C.(xn+wn+定长,yn+hn/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, (starts[1] + starts[3] + ends[1]) / 2);
			B = (ends[0] + ends[2] + currentWidth) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] / 2);
			str = A + ";" + B + ";" + C;
		}
		return str;
	}

	/**
	 * @description NO15:y=1,y=0，存在直线，分为：二点，四点区域
	 * @param starts
	 * @param ends
	 * @param nodeType
	 * @param endNodeType
	 * @return
	 */
	private String getPoint_y_1_y_0(int[] starts, int[] ends, String nodeType, String endNodeType) {
		// y=1,y=0 
		if(starts[1] <= ends[1] - starts[3]){
			// 上方区域
			// 条件：y<yn-hm
			// 零点区域
			// 添加：xm+xm/2+8=xn+wn/2
			int value = Math.abs(starts[0] + starts[2] / 2 - ends[0] - ends[2] / 2);
			int subLength = getNodesSubLength(nodeType, endNodeType);
			if(value >= 0 && value <= subLength){
				str = "";
			} else {
				// 两点区域(上方偏左): 
				// 条件：y<yn-hm
				// A.(xm+wm/2,(ym+hm+yn)/2) 
				// B.(xn+wn/2,(ym+hm+yn)/2)
				A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, (starts[1] + starts[3] + ends[1]) / 2);
				B = (ends[0] + ends[2] / 2) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
				str = A + ";" + B;
			}
		} else if(starts[0] >= ends[0] + ends[2] && starts[1] >= ends[1] - starts[3]) {
			// 四点区域：左下角
			// 条件：x>xn+wn,y>yn-hm
			// A.(xm+wm/2,ym+hm+定长)
			// B.((xn+wn+xm)/2,ym+hm+定长)
			// C.((xn+wn+xm)/2,yn-定长) 
			// D.(xn+wn/2,yn-定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (starts[1] + starts[3] + currentWidth);
			C = ((ends[0] + ends[2] + starts[0]) / 2) + "," + (ends[1] - currentWidth); 
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] >= ends[0] - starts[2] && starts[0] <= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[1] >= ends[1] + ends[3] - starts[3]) {
			// 四点区域：下方偏左
			// 条件:x>xn-wm,x<xn+wn/2-wm/2,y>yn+hn-hm
			// A.(xm+wm/2,ym+hm+定长)
			// B.(xn+wn+定长,ym+hm+定长)
			// C.(xn+wn+定长,yn-定长)
			// D.(xn+wn/2,yn-定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = (ends[0] + ends[2] + currentWidth) + "," + (starts[1] + starts[3] + currentWidth);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] - currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0]>= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[0] <= ends[0] + ends[2] && starts[1] >= ends[1] + ends[3] - starts[3]){
			// 四点区域：下方偏右
			// 条件：x>xn+wn/2-wm/2,x<xn+wn,y>yn+hn-hm
			// A.(xm+wm/2,ym+hm+定长)
			// B.(xn-定长,ym+hm+定长)
			// C.(xn-定长,yn-定长)
			// D.(xn+wn/2,yn-定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = (ends[0] - currentWidth) + "," + (starts[1] + starts[3] + currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] - currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] >= ends[0] - ends[2] && starts[0] <= ends[0] + ends[2] / 2 - starts[2] /2 && starts[1] >= ends[1] - ends[3] && starts[1] <= ends[1] + ends[3] - starts[3]) {
			// 四点区域：左边阴影
			// 条件：x>xn-wn,x<xn+wn/2-wm/2,y>yn-hm,y<yn+hn-hm
			// A.(xm+wm/2,yn+hn+定长)
			// B.(xn+wn+定长,yn+hn+定长)
			// C.(xn+wn+定长,yn-定长)
			// D.(xn+wn/2,yn-定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] + ends[3] + currentWidth);
			B = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] - currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] >= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[0] <= ends[0] + ends[2] && starts[1] >= ends[1] - starts[3] && starts[1] <= ends[1] + ends[3] - starts[3]) {
			// 四点区域：右边阴影
			// 条件：x>xn+wn/2-wm/2,x<xn+wn,y>yn-hm,y<yn+hn-hm
			// A.(xm+wm/2,yn+hn+定长)
			// B.(xn-定长,yn+hn+定长)
			// C.(xn-定长,yn-定长)
			// D.(xn+wn/2,yn-定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] + ends[3] + currentWidth);
			B = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			C = (ends[0] - currentWidth) + "," + (ends[1] - currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else {
			// 四点区域：左下角
			// 条件：x<xn-wm,y>yn-hm
		    // A.(xm+wm/2,yn+hm+定长)
			// B.((xm+wm+xn)/2,yn+hm+定长)
			// C.((xm+wm+xn)/2,yn-定长)
			// D.(xn+wn/2,yn-定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = ((starts[0] + starts[2] + ends[0]) / 2 + STARTLABELHeight) + "," + (starts[1] + starts[3] + currentWidth);
			C = ((starts[0] + starts[2] + ends[0]) / 2 + STARTLABELHeight) + "," + (ends[1] - currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] - currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		}
		return str;
	}

	/**
	 * @description NO16:y=1,y=1，分为：两点，四点区域
	 * @param starts
	 * @param ends
	 * @param nodeType
	 * @return
	 */
	private String getPoint_y_1_y_1(int[] starts, int[] ends, String nodeType) {
		// y=1,y=1 
		if((starts[0] >= ends[0] + ends[2] / 2 || starts[0] <= ends[0] + ends[2] / 2 - starts[2]) && starts[1] >= ends[1] + ends[3] - starts[3]){
			// 两点区域：左下角，右下角
			// 条件：(x>xn+wn/2 || x<xn+wn/2-wm)&&y>yn+hn-hm
			// A.(xm+wm/2,ym+hm+定长)
			// B.(xn+wn/2,ym+hm+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = (ends[0] + ends[2] / 2) + "," + (starts[1] + starts[3] + currentWidth);
			str = A + ";" + B;
		} else if (starts[0] >= ends[0] + ends[2] / 2 - starts[2] && starts[0] <= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[1] >= ends[1] + ends[3] - starts[3]) {
			// 四点区域：正下方偏左 
			// 条件： x>xn+wn/2-wm,x<xn+wn/2-wm/2,y>yn+hn-hm
			// A.(xm+wm/2,ym+hm+定长)
			// B.(xm+wm+定长,ym+hm+定长)
			// C.(xm+wm+定长,(yn+hn+ym)/2)
			// D.(xn+wn/2,(yn+hn+ym)/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = (starts[0] + starts[2] + currentWidth) + ","  + (starts[1] + starts[3] + currentWidth);
			C = (starts[0] + starts[2] + currentWidth) + ","  + ((ends[1] + ends[3] + starts[1]) / 2);
			D = (ends[0] + ends[2] / 2) + ","  + ((ends[1] + ends[3] + starts[1]) / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] >= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[0] <= ends[0] + ends[0] && starts[1] >= ends[1] + ends[3] - starts[3]) {
			// 四点区域：正下方偏右 
			// 条件：x>xn+wn/2-wm/2,x<xn+wn/2,y>yn+hn-hm
			// A.(xm+wm/2,ym+hm+定长)
			// B.(xm-定长,ym+hm+定长) 
			// C.(xm-定长,(yn+hn+ym)/2)
			// D.(xn+wn/2,(yn+hn+ym)/2)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, starts[1] + starts[3] + currentWidth);
			B = (starts[0] - currentWidth) + "," + (starts[1] + starts[3] + currentWidth);
			C = (starts[0] - currentWidth) + "," +((ends[1] + ends[3] + starts[1]) / 2);
			D = (ends[0] + ends[2] / 2) + "," + ((ends[1] + ends[3] + starts[1]) / 2);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if (starts[0] >= ends[0] - starts[2] / 2 && starts[0] <= ends[0] + ends[2] - starts[2] / 2 && starts[1] > ends[1] - starts[3] && starts[1] < ends[1] + ends[3] - starts[3] ) {
			// 四点区域：左右阴影部分
			// 条件：x>xn-wm/2,x<xn+wn-wm/2,y>yn-hm,y<yn+hn-hm
			// A.(xm+wm/2,yn+hn+定长)
			// B.(xn+wn/2,yn+hn+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] + ends[3] + currentWidth);
			B = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B;
		} else if (starts[0] >= ends[0] - starts[2] / 2 && starts[0] < ends[0] + ends[2] / 2 - starts[2] / 2 && starts[1] <= ends[1] - starts[3]) {
			// 四点区域：正上方偏左
			// 条件：x>xn-wm/2,x<xn+wn/2-wm/2,y<yn-hm
		    // A.(xm+wm/2,(ym+hm+yn)/2)
			// B.(xn-定长,(ym+hm+yn)/2)
			// C.(xn-定长,yn+hn+定长)
			// D.(xn+wn/2,yn+hn+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, (starts[1] + starts[3] + ends[1]) / 2);
			B = (ends[0] - currentWidth) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			C = (ends[0] - currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else if(starts[0] >= ends[0] + ends[2] / 2 - starts[2] / 2 && starts[0] <= ends[0] + ends[2] - starts[2] / 2 && starts[1] <= ends[1] - starts[3]) {
			// 四点区域：正上方偏右 
			// 条件：x>xn+wn/2-wm/2,x<xn+wn-wm/2,y<yn-hm
			// A.(xm+wm/2,(ym+hm+yn)/2)
			// B.(xn+wn+定长,(ym+hm+yn)/2)
			// C.(xn+wn+定长,yn+hn+定长)
			// D.(xn+wn/2,yn+hn+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, (starts[1] + starts[3] + ends[1]) / 2);
			B = (ends[0] + ends[2] + currentWidth) + "," + ((starts[1] + starts[3] + ends[1]) / 2);
			C = (ends[0] + ends[2] + currentWidth) + "," + (ends[1] + ends[3] + currentWidth);
			D = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B + ";" + C + ";" + D;
		} else {
			// if ((starts[0] >= ends[0] + ends[2] - starts[2] / 2 || starts[0] <= ends[0] - starts[2] / 2) && starts[1] <= ends[1] + ends[3] - starts[3]){
			// 两点区域：左上角，右上角
			// 条件:(x>xn+wn-wm/2 || x<xn-wm/2) && y<yn+hn-hm
		    // A.(xm+wm/2,yn+hn+定长)
			// B.(xn+wn/2,yn+hn+定长)
			A = addStartNodeLength(nodeType, starts[0] + starts[2] / 2, ends[1] + ends[3] + currentWidth);
			B = (ends[0] + ends[2] / 2) + "," + (ends[1] + ends[3] + currentWidth);
			str = A + ";" + B;
		} 
		return str;
	}

	/**
	 * @description 向折线引出的StartEvent节点，的x轴坐标添加，StartEvent节点的半个文本长度
	 * @param nodeType 
	 * 				节点类型
	 * @param xLength 
	 * 			x轴坐标组成的字符串
	 * @param yLength 
	 * 			y轴坐标组成的字符串
  	 * @return x,y做成的字符串
	 */
	private String addStartNodeLength(String nodeType, int xLength, int yLength){
		String str = xLength + "," + yLength;
		if(nodeType != null && nodeType.equals("StartEvent"))
			str = (xLength + STARTLABELHeight / 2) + "," + yLength;
		return str;
	}
	
	/**
	 * @description 竖直方向上的差值
	 * @param startNode 
	 * 				开始节点
	 * @param endNode 
	 * 				结束节点
	 * @return 返回:差值的绝对值
	 */
	private int getNodesSubLength(String startNode,String endNode){
		return startNode.equals(endNode) ? 0 : 7;
	}
}
