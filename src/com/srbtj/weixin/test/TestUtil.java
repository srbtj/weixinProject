package com.srbtj.weixin.test;

import java.nio.charset.Charset;
import java.util.UUID;

import org.junit.Test;

public class TestUtil {

	/**
	 *  字符编码方式 ： UTF-8 
	 */
	private Charset charset = Charset.forName("UTF-8");
	private int BLOCK_SIZE = 32;
	@Test
	public void ascDecode(){
		
		String enc_code = "0XzQJscrOyiaaoeqroRYHPEiYnbnXO69b3LwyLd6sU8";
		
//		BASE64Decoder
		
		int numb = 24;
		
//		char target = toChar(numb);
//		
//		System.out.println(target);
				
		System.out.println(UUID.randomUUID().toString());		
	}
	
	/**
	 *  获得对明文时行补位填充的字节 
	 * @param count  需要进行填充补位操作的明文字节个数
	 * @return 补齐用的字节数组
	 */
	public byte[] encode(int count){
		
		/** 获得需要填充的位数 */
		int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
		if(amountToPad == 0){
			amountToPad = BLOCK_SIZE;
		}
		
		char padChar = toChar(amountToPad);
		String temp = new String();
		
		for(int i=0;i<amountToPad;i++){
			temp += padChar;
		}
		
		return temp.getBytes(charset);
	}
	
	/***
	 *  将 数字 转换成对应的 ASCII码 对应的字符 ，用于对明文进行补码;
	 * @param numb  需要转化的数字
	 * @return  转化后得到的字符;
	 */
	public char toChar(int numb){
		
		byte target = (byte)( numb & 0xFF);
		
		return (char) target;
	}
}
