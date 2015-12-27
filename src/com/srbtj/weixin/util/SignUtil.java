package com.srbtj.weixin.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component("SignUtil")
public class SignUtil {

	private static String token = "weixinProject";
	
	/***
	 *  signature
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		
		/****
		 *  加密/校验流程
		 *   1. 将token,timestamp,nonce 三个参数进行字典序排序;
		 *   2. 将三个参数字符串拼接成一个字符串进行sha1加密
		 *   3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		 */
		String[] str = new String[]{token, timestamp, nonce};
		Arrays.sort(str);
		
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();

		/** 将加密后的字符口中与signature进行比较，标识该请求是否来自微信 **/
		return digest != null ? signature.equals(digest) : false;
	}
	
	
	public static Map<String, String> sign(String ticket,String url){
		
		Map<String, String> maps = new HashMap<String,String>();
		
		String nonstr = sign_nonstr();
		String timerstamp = sign_timerstamp();
		/** 签名字符串 */
		String str = "";
		String signature = "";
		str = "jsapi_ticket="+ticket+
						   "&noncestr="+nonstr+
						   "&timestamp="+timerstamp+
						   "&url="+url;
		
		signature = new SHA1().getDigestOfString(str.getBytes()).toLowerCase();
		
		maps.put("url", url);
		maps.put("jsapi_ticket", ticket);
		maps.put("nonceStr", nonstr);
		maps.put("timestamp", timerstamp);
		maps.put("signature", signature);
		
		return maps;
	}
	
	/**
	 *  获得随机字符串
	 * @return
	 */
	private static String sign_nonstr(){
		
		return UUID.randomUUID().toString();
	}
	
	/**
	 *  获得时间截
	 * @return
	 */
	private static String sign_timerstamp(){
		
		return Long.toString(System.currentTimeMillis());
	}
}
