package com.srbtj.weixin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.srbtj.weixin.util.SignUtil;

@Controller
public class WeiXinController {

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
		return null;
	}
	
}
