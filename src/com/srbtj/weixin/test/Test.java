package com.srbtj.weixin.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.apache.tomcat.util.digester.Rule;

import com.srbtj.weixin.entity.receive.ReceiveTextMessage;
import com.srbtj.weixin.entity.response.ResponseTextMessage;
import com.srbtj.weixin.util.SignUtil;
import com.srbtj.weixin.util.XMLParseUtil;
import com.sun.org.apache.bcel.internal.generic.LNEG;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Test {

	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		String xml = "<xml><ToUserName><![CDATA[srbtj]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName>" +
				"<CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content>" +
				"<MsgId>1234567890123456</MsgId></xml>";
		
//		InputStream is = new FileInputStream(xml);
		
//		ByteArrayInputStream is = new ByteArrayInputStream(xml.getBytes());
//		Map<String, String> maps = XMLParseUtil.parseXml(is);
		
//		ReceiveTextMessage textMessage = new ReceiveTextMessage();
		
//		textMessage.setToUserName(maps.get("ToUserName"));
//		textMessage.setFromUserName(maps.get("FromUserName"));
//		textMessage.setCreateTime(Long.parseLong(maps.get("CreateTime")));
//		textMessage.setMsgId(Long.parseLong(maps.get("MsgId")));
//		textMessage.setContent(maps.get("Content"));
//		textMessage.setMsgType("MsgType");
		
		
//		ResponseTextMessage responseTextMessage = new ResponseTextMessage();
//		
//		responseTextMessage.setFromUserName(textMessage.getToUserName());
//		responseTextMessage.setToUserName(textMessage.getFromUserName());
//		responseTextMessage.setCreateTime(Long.parseLong(SignUtil.sign_timerstamp()));
//		responseTextMessage.setContent("你好！");
//		responseTextMessage.setMsgType(textMessage.getMsgType());
//		String reString = XMLParseUtil.textMessageToXML(responseTextMessage);
//		
//		System.out.println(reString);
		String path = "http://192.168.1.104:8080/weixinProject/weixin";
		/** 为指定参数的post请求  **/
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(path);
		
		StringEntity entity = new StringEntity(xml, "UTF-8");
		post.addHeader("Content-Type","text/html");
		post.setEntity(entity);
		
		HttpResponse response = client.execute(post);
		
		HttpEntity resEntity = response.getEntity();
		
		
		InputStreamReader reader = new InputStreamReader(resEntity.getContent(),"ISO-8859-1");
		
		char[] buffer = new char[1024];
		
		int len = 0;
		while((len = reader.read(buffer)) != -1){
			
			System.out.println(new String(buffer,0,len));
		}
		
		client.getConnectionManager().shutdown();
		
	}
	

	
}
