package com.srbtj.weixin.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.srbtj.weixin.service.response.ResponseTextMessageServer;
import com.srbtj.weixin.util.SignUtil;
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
	 *  处理微信服务器发来的消息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/weixin",method = RequestMethod.POST)
	@ResponseBody
	public String weixin(HttpServletRequest request){
		Map<String, String> maps = XMLParseUtil.parseXml(request);
		String type = maps.get("MsgType");
		String responseMessage = null;
		if(type.equals("text")){
			
			responseMessage = responseTextMessageServer.responseTextMessage(maps);
			
		}
		
		return responseMessage;
	}
	
}
