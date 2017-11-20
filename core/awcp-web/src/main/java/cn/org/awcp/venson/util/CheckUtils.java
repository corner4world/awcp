package cn.org.awcp.venson.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {

	private CheckUtils() {
	};

	/**
	 * 大陆号码或香港号码均可
	 */
	public static boolean isPhoneLegal(String str) {
		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数
	 * 17+除9的任意数 147
	 */
	public static boolean isChinaPhoneLegal(String str) {
		String regExp = "^1[3|5|7|8]\\d{9}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	public static boolean isHKPhoneLegal(String str) {
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 是否是合法用户名
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLegalUserName(String str) {
		Pattern p = Pattern.compile("[a-zA-Z]{1}[a-zA-Z0-9]{5,16}");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 是否是合法邮箱
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLegalEmail(String str) {
						
		String check = "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(str);
		return matcher.matches();
	}

}
