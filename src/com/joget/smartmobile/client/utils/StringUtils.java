package com.joget.smartmobile.client.utils;

public class StringUtils {
	private static final String QUOTES = "\"";

	public static String getValue(Object obj) {
		if (obj != null) {
			return obj.toString();
		} else {
			return "";
		}

	}
	
	public static String getRidOfQuotes(String str) {
		String result = str;
		if (result != null) {
			if (str.startsWith(QUOTES)) {
				result = result.substring(1);
			}
			if (str.endsWith(QUOTES)) {
				result = result.substring(0,result.length()-1);
			}
			
		}

		return result;
	}
}
