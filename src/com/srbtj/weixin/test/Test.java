package com.srbtj.weixin.test;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.srbtj.weixin.entity.receive.ReceiveTextMessage;
import com.srbtj.weixin.entity.response.ResponseTextMessage;
import com.srbtj.weixin.util.SignUtil;
import com.srbtj.weixin.util.XMLParseUtil;
import com.sun.org.apache.bcel.internal.generic.LNEG;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Test {

	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		String new_msg = "<xml>"+
					 "<ToUserName><![CDATA[gh_680bdefc8c5d]]></ToUserName>"+
					 "<Encrypt><![CDATA[MNn4+jJ/VsFh2gUyKAaOJArwEVYCvVmyN0iXzNarP3O6vXzK62ft1/KG2/XPZ4y5bPWU/jfIfQxODRQ7sLkUsrDRqsWimuhIT8Eq+w4E/28m+XDAQKEOjWTQIOp1p6kNsIV1DdC3B+AtcKcKSNAeJDr7x7GHLx5DZYK09qQsYDOjP6R5NqebFjKt/NpEl/GU3gWFwG8LCtRNuIYdK5axbFSfmXbh5CZ6Bk5wSwj5fu5aS90cMAgUhGsxrxZTY562QR6c+3ydXxb+GHI5w+qA+eqJjrQqR7u5hS+1x5sEsA7vS+bZ5LYAR3+PZ243avQkGllQ+rg7a6TeSGDxxhvLw+mxxinyk88BNHkJnyK//hM1k9PuvuLAASdaud4vzRQlAmnYOslZl8CN7gjCjV41skUTZv3wwGPxvEqtm/nf5fQ=]]></Encrypt>"+
					 "</xml>";
		
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
//		String path = "http://192.168.1.104:8080/weixinProject/weixin";
		String path = "http://localhost:8888/weixinProject/weixin?signature=35703636de2f9df2a77a662b68e521ce17c34db4&timestamp=1414243737&nonce=1792106704&encrypt_type=aes&msg_signature=6147984331daf7a1a9eed6e0ec3ba69055256154";
		/** 为指定参数的post请求  **/
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(path);
		
		StringEntity entity = new StringEntity(new_msg, "UTF-8");
		post.addHeader("Content-Type","text/html");
		post.setEntity(entity);
		
		HttpResponse response = client.execute(post);
		
		HttpEntity resEntity = response.getEntity();
		
		
		InputStreamReader reader = new InputStreamReader(resEntity.getContent(),"UTF-8");
		
		char[] buffer = new char[1024];
		
		int len = 0;
		while((len = reader.read(buffer)) != -1){
			
			System.out.println(new String(buffer,0,len));
		}
		
		client.getConnectionManager().shutdown();
		
	}
	
	
}
