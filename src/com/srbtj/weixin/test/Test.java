package com.srbtj.weixin.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import com.srbtj.weixin.entity.receive.ReceiveTextMessage;
import com.srbtj.weixin.entity.response.ResponseTextMessage;
import com.srbtj.weixin.util.SignUtil;
import com.srbtj.weixin.util.XMLParseUtil;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		
		String xml = "<xml><ToUserName><![CDATA[srbtj]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>" +
				"<CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content>" +
				"<MsgId>1234567890123456</MsgId></xml>";
		
		InputStream is = new FileInputStream(xml);
		
		
		Map<String, String> maps = XMLParseUtil.parseXml(is);
		
		ReceiveTextMessage textMessage = new ReceiveTextMessage();
		
		textMessage.setToUserName(maps.get("ToUserName"));
		textMessage.setFromUserName(maps.get("FromUserName"));
		textMessage.setCreateTime(Long.parseLong(maps.get("CreateTime")));
		textMessage.setMsgId(Long.parseLong(maps.get("MsgId")));
		textMessage.setMsgType("MsgType");
		
		
		ResponseTextMessage responseTextMessage = new ResponseTextMessage();
		
		responseTextMessage.setFromUserName(textMessage.getToUserName());
		responseTextMessage.setToUserName(textMessage.getFromUserName());
		responseTextMessage.setCreateTime(Long.parseLong(SignUtil.sign_timerstamp()));
		responseTextMessage.setContent("你好！");
		responseTextMessage.setMsgType(textMessage.getMsgType());
		String reString = XMLParseUtil.textMessageToXML(responseTextMessage);
		
		System.out.println(reString);
	}
}
