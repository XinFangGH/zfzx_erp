package com.zhiwei.core.util;

public class SwitchMoneyUtil {

	private static String[] bits = new String[] { "", "拾", "佰", "仟", "", "拾",
			"佰", "仟", "", "拾", "佰", "仟", "", "拾", "佰", "仟", "" };
	private static String[] nums = new String[] { "零", "壹", "贰", "叁", "肆", "伍",
			"陆", "柒", "捌", "玖" };

	public static String getChineseMoney(Double money) {
		String moneyStr = "";

		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

		moneyStr = df.format(money);
		String[] moneys = moneyStr.split("\\.");

		moneyStr = transChineseMoney1(moneys[0])
				+ transChineseMoney2(moneys[1]);
		return moneyStr;
	}

	public static String getChineseMoney(String money) {
		if (money.trim().isEmpty()) {
			return "";
		}
		if (Double.parseDouble(money) <= 0) {
			return "";
		}
		Double dMoney = 0.00;
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		dMoney += Double.parseDouble(money.trim());
		money = df.format(dMoney);
		String[] moneys = money.split("\\.");

		money = transChineseMoney1(moneys[0]) + transChineseMoney2(moneys[1]);
		return money;
	}

	/**
	 * 处理整数位的金额
	 * 
	 * @param money
	 * @return
	 */
	public static String transChineseMoney1(String money) {
		if (money.isEmpty()) {
			return "";
		}
		if (Integer.parseInt(money) == 0) {
			return "";
		}
		String returnMoney = "";
		int zero = 0;
		for (int i = money.length() - 1, j = 0; i >= 0; i--, j++) {
			int num = money.charAt(i) - 48;
			String s = nums[num] + bits[j];
			if (num == 0 && zero == 2 && j == 5) {
				returnMoney = "万" + returnMoney;
				zero += 1;
			} else if (num != 0 && j > 0 && j % 4 == 0) {
				if (j % 8 != 0) {
					returnMoney = s + "万" + returnMoney;
				} else {
					returnMoney = s + "亿" + returnMoney;
				}
				zero = 0;
			} else if (num != 0) {
				returnMoney = s + returnMoney;
				zero = 0;
			} else {
				zero += 1;
				if (zero == 1 && j > 0) {
					if (j % 4 != 0) {
						returnMoney = "零" + returnMoney;
					} else {
						if (j % 4 != 8) {
							returnMoney = "万" + returnMoney;
						} else {
							returnMoney = "亿" + returnMoney;
						}
					}
				}
			}

		}
		returnMoney += "元";
		return returnMoney;
	}

	/**
	 * 处理小数的金额
	 * 
	 * @param money
	 * @return
	 */
	public static String transChineseMoney2(String money) {
		if (money.isEmpty()) {
			return "";
		}

		if (Integer.parseInt(money) == 0) {
			return "整";
		}
		String returnMoney = "";
		money = money.substring(0, 2);
		int jiao = money.charAt(0) - 48;
		int fen = money.charAt(1) - 48;
		if (fen != 0) {
			returnMoney += nums[fen] + "分";
			returnMoney = nums[jiao] + "角" + returnMoney;
		} else {
			if (jiao != 0) {
				returnMoney = nums[jiao] + "角" + returnMoney;
			}
		}
		return returnMoney;
	}

}