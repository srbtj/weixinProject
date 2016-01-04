package com.srbtj.weixin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.srbtj.weixin.service.response.ResponseTextMessageServer;
import com.srbtj.weixin.util.MessageProgress;
import com.srbtj.weixin.util.SignUtil;
import com.srbtj.weixin.util.WeixinRequest;
import com.srbtj.weixin.util.XMLParseUtil;

@Controller
public class WeiXinController {

	private ResponseTextMessageServer responseTextMessageServer;
	
	@Autowired
	public WeiXinController(ResponseTextMessageServer responseTextMessageServer){
		this.responseTextMessageServer = responseTextMessageServer;
	}
	/***
	 *  token 验证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@RequestMapping(value = "/weixin",method=RequestMethod.GET)
	@ResponseBody
	public String weixin(String signature,String timestamp,String nonce,String echostr){
		
		boolean flag = SignUtil.checkSignature(signature, timestamp, nonce);
		if(flag){
			return echostr;
		}else{
			return "";
		}
	}

	/***
	 *  处理微信服务器发来的消息  明文
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/weixin",method = RequestMethod.POST)
	@ResponseBody
	public String weixin(HttpServletRequest request,
			@RequestParam(value="encrypt_type",required=false,defaultValue="")String encrypt_type,  /** 加密类型： 不存在或为 raw(明文) ; aes:(兼容或密文)*/
			@RequestParam(value="msg_signature",required=false,defaultValue="")String msg_signature, /** 消息签名 */
			@RequestParam(value="timestamp",required=false,defaultValue="")String timestamp,  /** 时间截 **/
			@RequestParam(value="nonce",required=false,defaultValue="")String nonce,  /** 随机字符串 */
			@RequestParam(value="signature",required=false,defaultValue="")String signature) throws IOException{  
		Map<String, String> maps = new HashMap<String, String>();
		
		String responseMessage = null;
		
		if("".equals(encrypt_type) || encrypt_type.equals("raw")){   /** 明文  **/
			
			maps = XMLParseUtil.parseXml(request);
			String type = maps.get("MsgType");
			if(type.equals("text")){
				responseMessage = responseTextMessageServer.responseTextMessage(maps);
			}
		}else{ /** 密文 **/  
			
			/** 将输入 流转换成字符串 */
			InputStream is = request.getInputStream();
			String postData = IOUtils.toString(is, "UTF-8");
			/** 解密 获得解密后的字符串 */
			String result = MessageProgress.decryptMsg(msg_signature, timestamp, nonce,postData);
//			System.out.println("result " + result);
			/** 获得解密后的字符串 */
			maps = XMLParseUtil.stringToXML(result);
			
			String type = maps.get("MsgType");
			
			if(type.equals("text")){
				
				String backStr = responseTextMessageServer.responseTextMessage(maps);
				
//				System.out.println("backStr : "+backStr);
				/**
				 *  对返回的数据进行加密 
				 * **/
				responseMessage = MessageProgress.encryptMsg(backStr,timestamp,nonce);
				
//				System.out.println("responseMessage : " + responseMessage);
			}
		}
		return responseMessage;
	}
	
	/***
	 *  String appid,String appsecrect
	 *  获取 access_token 
	 * @throws IOException 
	 */
	@RequestMapping(value="/getAccessToken",method=RequestMethod.GET)
	public String getAccessToken() throws IOException{
		
//		String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
//		String request_url = token_url.replace("APPID", appid).replace("APPSECRET", appsecrect);
		WeixinRequest request = new WeixinRequest();
		String ticket = request.getWeixinToken();
		System.out.println(ticket);
	
		return null;
	}
	
	/**
	 *  获取 jsapi_ticket 
	 */
	
}
