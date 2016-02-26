package com.major.base.util;

import java.text.SimpleDateFormat;

public class SimplDateFormatUtils {

	private static SimpleDateFormat YMD = null;
	
	private static SimpleDateFormat YM = null;

	private SimplDateFormatUtils() throws Exception {
		throw new UnsupportedOperationException("对象不支持此操作!");
	}
	
	/**
	 * 年月日格式
	 * @return
	 */
	public static SimpleDateFormat createYMDFormat() {
		if (null == YMD) {
			YMD = new SimpleDateFormat("yyyy-MM-dd");
		}
		return YMD;
	}
	
	/**
	 * 年月格式
	 * @return
	 */
	public static SimpleDateFormat createYMFormat() {
		if (null == YM) {
			YM = new SimpleDateFormat("yyyy-MM");
		}
		return YM;
	}
	
	/**
	 * 年月日时分秒格式
	 * @return
	 */
	public static SimpleDateFormat createYMDHMSFormat() {
		return SingletonHolder.YMDHMS;
	}

	private static class SingletonHolder {
		private static SimpleDateFormat YMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

}
