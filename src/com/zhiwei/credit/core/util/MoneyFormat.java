package com.zhiwei.credit.core.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;

public class MoneyFormat {

	public static final String EMPTY = "";
	public static final String ZERO = "零";
	public static final String ONE = "壹";
	public static final String TWO = "贰";
	public static final String THREE = "叁";
	public static final String FOUR = "肆";
	public static final String FIVE = "伍";
	public static final String SIX = "陆";
	public static final String SEVEN = "柒";
	public static final String EIGHT = "捌";
	public static final String NINE = "玖";
	public static final String TEN = "拾";
	public static final String HUNDRED = "佰";
	public static final String THOUSAND = "仟";
	public static final String TEN_THOUSAND = "万";
	public static final String HUNDRED_MILLION = "亿";
	public static final String YUAN = "元";
	public static final String JIAO = "角";
	public static final String FEN = "分";
	public static final String DOT = ".";

	private static MoneyFormat formatter = null;
	private HashMap chineseNumberMap = new HashMap();
	private HashMap chineseMoneyPattern = new HashMap();
	private NumberFormat numberFormat = NumberFormat.getInstance();

	private MoneyFormat() {
		numberFormat.setMaximumFractionDigits(4);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setGroupingUsed(false);

		chineseNumberMap.put("0", ZERO);
		chineseNumberMap.put("1", ONE);
		chineseNumberMap.put("2", TWO);
		chineseNumberMap.put("3", THREE);
		chineseNumberMap.put("4", FOUR);
		chineseNumberMap.put("5", FIVE);
		chineseNumberMap.put("6", SIX);
		chineseNumberMap.put("7", SEVEN);
		chineseNumberMap.put("8", EIGHT);
		chineseNumberMap.put("9", NINE);
		chineseNumberMap.put(DOT, DOT);

		chineseMoneyPattern.put("1", TEN);
		chineseMoneyPattern.put("2", HUNDRED);
		chineseMoneyPattern.put("3", THOUSAND);
		chineseMoneyPattern.put("4", TEN_THOUSAND);
		chineseMoneyPattern.put("5", TEN);
		chineseMoneyPattern.put("6", HUNDRED);
		chineseMoneyPattern.put("7", THOUSAND);
		chineseMoneyPattern.put("8", HUNDRED_MILLION);
	}

	public static MoneyFormat getInstance() {
		if (formatter == null)
			formatter = new MoneyFormat();
		return formatter;
	}

	public String format(String moneyStr) {
		checkPrecision(moneyStr);
		String result;
		result = convertToChineseNumber(moneyStr);
		result = addUnitsToChineseMoneyString(result);
		return result;
	}

	public String format(double moneyDouble) {
		return format(numberFormat.format(moneyDouble));
	}

	public String format(int moneyInt) {
		return format(numberFormat.format(moneyInt));
	}

	public String format(long moneyLong) {
		return format(numberFormat.format(moneyLong));
	}

	public String format(Number moneyNum) {
		return format(numberFormat.format(moneyNum));
	}

	private String convertToChineseNumber(String moneyStr) {
		String result;
		StringBuffer cMoneyStringBuffer = new StringBuffer();
		for (int i = 0; i < moneyStr.length(); i++) {
			cMoneyStringBuffer.append(chineseNumberMap.get(moneyStr.substring(
					i, i + 1)));
		}
		// 拾佰仟万亿等都是汉字里面才有的单位，加上它们
		int indexOfDot = cMoneyStringBuffer.indexOf(DOT);
		int moneyPatternCursor = 1;
		for (int i = indexOfDot - 1; i > 0; i--) {
			cMoneyStringBuffer.insert(i, chineseMoneyPattern.get(EMPTY
					+ moneyPatternCursor));
			moneyPatternCursor = moneyPatternCursor == 8 ? 1
					: moneyPatternCursor + 1;
		}

		String fractionPart = cMoneyStringBuffer.substring(cMoneyStringBuffer
				.indexOf("."));
		cMoneyStringBuffer.delete(cMoneyStringBuffer.indexOf("."),
				cMoneyStringBuffer.length());
		while (cMoneyStringBuffer.indexOf("零拾") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零拾"),
					cMoneyStringBuffer.indexOf("零拾") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零佰") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零佰"),
					cMoneyStringBuffer.indexOf("零佰") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零仟") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零仟"),
					cMoneyStringBuffer.indexOf("零仟") + 2, ZERO);
		}
		while (cMoneyStringBuffer.indexOf("零万") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零万"),
					cMoneyStringBuffer.indexOf("零万") + 2, TEN_THOUSAND);
		}
		while (cMoneyStringBuffer.indexOf("零亿") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零亿"),
					cMoneyStringBuffer.indexOf("零亿") + 2, HUNDRED_MILLION);
		}
		while (cMoneyStringBuffer.indexOf("零零") != -1) {
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零零"),
					cMoneyStringBuffer.indexOf("零零") + 2, ZERO);
		}
		if (cMoneyStringBuffer.lastIndexOf(ZERO) == cMoneyStringBuffer.length() - 1)
			cMoneyStringBuffer.delete(cMoneyStringBuffer.length() - 1,
					cMoneyStringBuffer.length());
		cMoneyStringBuffer.append(fractionPart);

		result = cMoneyStringBuffer.toString();
		return result;
	}

	private String addUnitsToChineseMoneyString(String moneyStr) {
		String result;
		StringBuffer cMoneyStringBuffer = new StringBuffer(moneyStr);
		int indexOfDot = cMoneyStringBuffer.indexOf(DOT);
		cMoneyStringBuffer.replace(indexOfDot, indexOfDot + 1, YUAN);
		cMoneyStringBuffer.insert(cMoneyStringBuffer.length() - 1, JIAO);
		cMoneyStringBuffer.insert(cMoneyStringBuffer.length(), FEN);
		if (cMoneyStringBuffer.indexOf("零角零分") != -1)// 没有零头，加整
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零角零分"),
					cMoneyStringBuffer.length(), "整");
		else if (cMoneyStringBuffer.indexOf("零分") != -1)// 没有零分，加整
			cMoneyStringBuffer.replace(cMoneyStringBuffer.indexOf("零分"),
					cMoneyStringBuffer.length(), "整");
		else {
			if (cMoneyStringBuffer.indexOf("零角") != -1)
				cMoneyStringBuffer.delete(cMoneyStringBuffer.indexOf("零角"),
						cMoneyStringBuffer.indexOf("零角") + 2);
			// tmpBuffer.append("整");
		}
		result = cMoneyStringBuffer.toString();
		return result;
	}

	private void checkPrecision(String moneyStr) {
		int fractionDigits = moneyStr.length() - moneyStr.indexOf(DOT) - 1;
		if (fractionDigits > 2)
			throw new RuntimeException("金额" + moneyStr + "的小数位多于两位。"); // 精度不能比分低
	}
	public static String[] chineseDigits = new String[] { "零", "壹", "贰", "叁",
			"肆", "伍", "陆", "柒", "捌", "玖" };

	public static String amountToChinese(double amount) {

		if (amount > 99999999999999.99 || amount < -99999999999999.99)
			throw new IllegalArgumentException(
					"参数值超出允许范围 (-99999999999999.99 ～ 99999999999999.99)！");

		boolean negative = false;
		if (amount < 0) {
			negative = true;
			amount = amount * (-1);
		}

		long temp = Math.round(amount * 100);
		int numFen = (int) (temp % 10); // 分
		temp = temp / 10;
		int numJiao = (int) (temp % 10); // 角
		temp = temp / 10;
		// temp 目前是金额的整数部分

		int[] parts = new int[20]; // 其中的元素是把原来金额整数部分分割为值在 0~9999 之间的数的各个部分
		int numParts = 0; // 记录把原来金额整数部分分割为了几个部分（每部分都在 0~9999 之间）
		for (int i = 0;; i++) {
			if (temp == 0)
				break;
			int part = (int) (temp % 10000);
			parts[i] = part;
			numParts++;
			temp = temp / 10000;
		}

		boolean beforeWanIsZero = true; // 标志“万”下面一级是不是 0

		String chineseStr = "";
		for (int i = 0; i < numParts; i++) {

			String partChinese = partTranslate(parts[i]);
			if (i % 2 == 0) {
				if ("".equals(partChinese))
					beforeWanIsZero = true;
				else
					beforeWanIsZero = false;
			}
			if (i != 0) {
				if (i % 2 == 0)
					chineseStr = chineseStr;
				else {
					if ("".equals(partChinese) && !beforeWanIsZero) // 如果“万”对应的
																	// part 为
																	// 0，而“万”下面一级不为
																	// 0，则不加“万”，而加“零”
						chineseStr = chineseStr;
					else {
						if (parts[i - 1] < 1000 && parts[i - 1] > 0) // 如果"万"的部分不为
																		// 0,
																		// 而"万"前面的部分小于
																		// 1000
																		// 大于 0，
																		// 则万后面应该跟“零”
							chineseStr = chineseStr;
						chineseStr = chineseStr;
					}
				}
			}
			chineseStr = partChinese + chineseStr;
		}

		if ("".equals(chineseStr)) // 整数部分为 0, 则表达为"零元"
			chineseStr = chineseDigits[0];
		else if (negative) // 整数部分不为 0, 并且原金额为负数
			chineseStr = chineseStr;

		chineseStr = chineseStr;

		if (numFen == 0 && numJiao == 0) {
			chineseStr = chineseStr;
		} else if (numFen == 0) { // 0 分，角数不为 0
			chineseStr = chineseStr + chineseDigits[numJiao];
		} else { // “分”数不为 0
			if (numJiao == 0)
				chineseStr = chineseStr + chineseDigits[numFen];
			else
				chineseStr = chineseStr + chineseDigits[numJiao]
						+ chineseDigits[numFen];
		}

		return chineseStr;

	}

	/**
	 * 把一个 0~9999 之间的整数转换为汉字的字符串，如果是 0 则返回 ""
	 * 
	 * @param amountPart
	 * @return
	 */
	private static String partTranslate(int amountPart) {

		if (amountPart < 0 || amountPart > 10000) {
			throw new IllegalArgumentException("参数必须是大于等于 0，小于 10000 的整数！");
		}

		String[] units = new String[] { "", "拾", "佰", "仟" };

		int temp = amountPart;

		String amountStr = new Integer(amountPart).toString();
		int amountStrLength = amountStr.length();
		boolean lastIsZero = true; // 在从低位往高位循环时，记录上一位数字是不是 0
		String chineseStr = "";

		for (int i = 0; i < amountStrLength; i++) {
			if (temp == 0) // 高位已无数据
				break;
			int digit = temp % 10;
			if (digit == 0) { // 取到的数字为 0
				if (!lastIsZero) // 前一个数字不是 0，则在当前汉字串前加“零”字;
					chineseStr = "零" + chineseStr;
				lastIsZero = true;
			} else { // 取到的数字不是 0
				chineseStr = chineseDigits[digit] + units[i] + chineseStr;
				lastIsZero = false;
			}
			temp = temp / 10;
		}
		return chineseStr;
	}

	public String hangeToBig(BigDecimal value) {
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		if(value.compareTo(new BigDecimal("0"))<0){
			return "零";
		}
		long midVal = (value.multiply(new BigDecimal("100")).longValue()); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串
		//String valStr = value+""; // 转化成字符串
		System.out.println(valStr);
		if(valStr.equals("0")){
			valStr="000";
		}else if(valStr.length()<=1){
			valStr="00"+valStr;
		}
		
		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分
		String railFen = valStr.substring(valStr.length()-1); // 取小数部分
		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "";
		} else {
			if(railFen.equals("0")){
				suffix = digit[rail.charAt(0) - '0'] + "角";	
			}else{
				String isRailFen = valStr.substring(0,valStr.length() - 1); // 取小数部分
				if(isRailFen.equals("00")){
					suffix =  digit[railFen.charAt(0) - '0'] + "分";
				}else{
					suffix = digit[rail.charAt(0) - '0'] + "角"
					+ digit[railFen.charAt(0) - '0'] + "分";
				}
			}// 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0'&&idx!=0) { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if ((idx == 0 && vidx > 0)/*||(idx ==1 && vidx > 0)*/) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}

		if (prefix.length() > 0)
			prefix += "元"; // 如果整数部分存在,则有圆的字样
		if("".equals(suffix)){//如果没有小数位才有整字，否则必须要有整
			prefix += "整";
		}
		return prefix + suffix; // 返回正确表示
	}

	
	
	public static void main(String[] args) {
		MoneyFormat m = new MoneyFormat();
		String kk = m.hangeToBig(new BigDecimal(101.01));
		System.out.println("kk===="+kk);
	}
	
	
	//去掉数字后边多余的0
	public String removeTailZero(BigDecimal b) {
		String s = b.toString();
		int i, len = s.length();
		for (i = 0; i < len; i++)
			if (s.charAt(len - 1 - i) != '0')
				break;
		if (s.charAt(len - i - 1) == '.')
			return s.substring(0, len - i - 1);
		return s.substring(0, len - i);
	}
	
	//格式化时间
	public String formatDate(String date){
		String dateStr = "";
		String [] str = new String[3];
		str = date.split("-");
		if(str.length >0){
			dateStr = str[0]+"年"+str[1]+"月"+str[2]+"日";
		}
		return dateStr;
	}

}
