package com.srbtj.weixin.util;

import com.srbtj.weixin.util.ase.AesException;
import com.srbtj.weixin.util.ase.WXBizMsgCrypt;

public class MessageProgress {
	/** 令牌 **/
	private static String TOKEN = "weixinProject";
////	/** 消息加解密密钥 **/
	private static String ENCODING_AES_KEY = "0XzQJscrOyiaaoeqroRYHPEiYnbnXO69b3LwyLd6sU8";
////	/** 开发者 ID **/
	private static String APPID = "wxdf4ac816c369227c";
//	private static String TOKEN = "weixin";
	/** 消息加解密密钥 **/
//	private static String ENCODING_AES_KEY = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
	/** 开发者 ID **/
//	private static String APPID = "wxbad0b45542aa0b5e";
	
	
	private static WXBizMsgCrypt biz  = null;
	
	static{
		try {
			biz = new WXBizMsgCrypt(TOKEN, ENCODING_AES_KEY, APPID);
		} catch (AesException e) {
			e.printStackTrace();
		}
	}
	
	/***
	 *  对明文时行加密 
	 * @param nonce 
	 * @param timestamp 
	 * @param backStr 
	 * @return  加密后的base64字符串
	 */
	public static String encryptMsg(String backStr, String timestamp, String nonce){
		
		String result = null;
		try {
			result =  biz.EncryptMsg(backStr, timestamp, nonce);
		} catch (AesException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 *  对密文进行解密 
	 * @param msgSignature
	 * @param timeStamp
	 * @param nonce
	 * @param postData
	 * @return  解密码后的字符串 
	 */
	public static String decryptMsg(String msgSignature,String timeStamp,String nonce,String postData){
		
		String result = null;
		try {
			result = biz.DecryptMsg(msgSignature, timeStamp, nonce, postData);
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
