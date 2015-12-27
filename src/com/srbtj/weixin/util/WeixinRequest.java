package com.srbtj.weixin.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.spy.memcached.MemcachedClient;

import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.util.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("WeixinRequest")
public class WeixinRequest {

	private static String APPID = "wxdf4ac816c369227c";
	private static String APPSECRET = "455a80c71906f90b4e34377007dce34a";
	private static String ACCESSTOKEN = "access_token";
	private static String TICKET = "ticket";
	
	/** 过期时间 **/
	@Autowired
	private MemcachedClient memcachedClient; 
	
	/***
	 *  验证 weixin token
	 * @return
	 */
	public String getWeixinToken(){
		
		String access_token = "",
			   ticket = "";	
		
		Object apiToken = memcachedClient.get(ACCESSTOKEN);
		Object apiTicket = memcachedClient.get(TICKET);
		Object apiExpired;
		
		if(null == apiToken){
			
			try {
				/** get access_token **/
				URL url = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+
						APPID+"&secret="+APPSECRET);
				
				JSONObject json = getConnection(url);
					
				access_token = (String) json.get("access_token");
				apiExpired = json.get("expires_in");
				
				if(null == access_token){
					
					return null;
				}
				/** 设置 access_token 有效期 ： 一个半小时 **/
				memcachedClient.set(ACCESSTOKEN, 1*60*90, access_token);
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
		}else{
			
			access_token = (String) apiToken;
		}
		
		System.out.println("----->> access_token :　" +  access_token);
		
		if(null == apiTicket){
			
			try {
				
				/** 获得 api_ticket **/
				URL url = new URL("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=wx_card");
				
				JSONObject jsonObject = getConnection(url);
				
				ticket = jsonObject.getString("ticket");
				
				memcachedClient.set(TICKET, 1*60*90, ticket);
				
			} catch (MalformedURLException e) {
				
				e.printStackTrace();
			}
		}
		
		return ticket;
	}
	
	/***
	 *  发送get请求
	 * @param url
	 * @return
	 */
	private JSONObject getConnection(URL url){
		
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencode");
			
			connection.connect();
			JSONObject jsonObject = new JSONObject(new JSONTokener(new InputStreamReader(connection.getInputStream())));
			connection.disconnect();
			
			return jsonObject;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
