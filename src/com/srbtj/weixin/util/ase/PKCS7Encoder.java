package com.srbtj.weixin.util.ase;

import java.nio.charset.Charset;
import java.util.Arrays;

public class PKCS7Encoder {

	/** 编码方式 */
	private static Charset CHARSET = Charset.forName("UTF-8");
	/** 字节大小 **/
	private static int BLOCK_SIZE = 32;
	
	/***
	 *  获得对明文进行补位填充的字节.
	 * @param count 需要进行填充补位操作的明文字节个数
	 * @return  补齐用的字节数组
	 */
	public static byte[] encode(int count){
		/** 计算需要填充的位数 **/
		int amoutToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
		if(amoutToPad == 0){
			amoutToPad = BLOCK_SIZE;
		}
		/** 获得补位所用的字符 */
		char padChar = padChar(amoutToPad);
		
		String temp = new String();
		
		for(int i=0;i<amoutToPad;i++){
			temp += padChar;
		}
		
		return temp.getBytes(CHARSET);
	}
	
	/***
	 *   删除解密后明文的补位字符
	 * @param decrypted  解密后的明文
	 * @return   删除补位字符后的明文
	 */
	public static byte[] decode(byte[] decrypted){
		
		int pad = (int) decrypted[decrypted.length - 1];
		
		if(pad < 1 || pad > 32){
			
			pad = 0;
		}
		return Arrays.copyOfRange(decrypted, 0, pad);
	}
	
	/**
	 *  将数字转化成ASCII码对应的字符，用于对明文进行补码
	 * @param numb  需要转化的数字
	 * @return  转化得到的字符
	 */
	private static char padChar(int numb){
		
		byte target = (byte)(numb & 0xFF);
		
		return (char)target;
	}
}
