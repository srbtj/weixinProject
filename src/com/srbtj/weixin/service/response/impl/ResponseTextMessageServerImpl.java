package com.srbtj.weixin.service.response.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.srbtj.weixin.entity.response.ResponseTextMessage;
import com.srbtj.weixin.service.response.ResponseTextMessageServer;
import com.srbtj.weixin.util.SignUtil;
import com.srbtj.weixin.util.XMLParseUtil;

@Service
public class ResponseTextMessageServerImpl implements ResponseTextMessageServer{

	@Override
	public String responseTextMessage(Map<String, String> maps) {
		
		ResponseTextMessage textMessage = new ResponseTextMessage();
		
		textMessage.setFromUserName(maps.get("ToUserName"));
		textMessage.setToUserName(maps.get("FromUserName"));
		textMessage.setCreateTime(Long.parseLong(SignUtil.sign_timerstamp()));
		textMessage.setMsgType(maps.get("MsgType"));
		textMessage.setContent("成功");
		return XMLParseUtil.textMessageToXML(textMessage);
	}

}
