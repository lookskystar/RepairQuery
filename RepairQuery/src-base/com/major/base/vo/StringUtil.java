package com.major.base.vo;
/**
 * 处理字符串
 */
public class StringUtil {
	
	/**
	 * 将字符串null变为""
	 * @param desc
	 * @return
	 */
	public static String dealNullString(String desc){
		if(desc==null){
			return "";
		}else{
			return desc;
		}
	}
	
	/**
	 * 追加非重复的字符串
	 */
	public static String addString(String parent,String child){
		String result = "";
		if(parent==null || "".equals(parent)){
			parent = "";
		}
		if(child==null || "".equals(child)){
			child = "";
		}
		parent = parent.replace(",","");
		child = child.replace(",","");
		if(parent.contains(child)){
			result = parent;
		}else{
			result = parent+","+child;
		}
		if(child.contains(parent)){
			result = child;
		}
		return result;
	}
}
