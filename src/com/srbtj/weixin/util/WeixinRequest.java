package com.srbtj.weixin.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

//import net.spy.memcached.MemcachedClient;

import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.impl.util.json.JSONTokener;

import com.srbtj.weixin.entity.TokenTicket;
import com.srbtj.weixin.schedule.CachePool;


public class WeixinRequest {

	private static String APPID = "wxdf4ac816c369227c";
	private static String APPSECRET = "455a80c71906f90b4e34377007dce34a";
	private static String ACCESSTOKEN = "access_token";
	private static String TICKET = "ticket";
	
	/** 过期时间 **/
//	@Autowired
//	private MemcachedClient memcachedClient; 
	
	/***
	 *  验证 weixin token
	 * @return
	 */
	public String getWeixinToken(){
		
		String access_token = "",
			   ticket = "";	
		CachePool pool = CachePool.getInstance();
//		Object apiToken = memcachedClient.get(ACCESSTOKEN);
//		Object apiTicket = memcachedClient.get(TICKET);
		String apiToken = (String) pool.getCacheItem(ACCESSTOKEN);
		String apiTicket = (String) pool.getCacheItem(TICKET);
//		Object apiExpired;
		
		/** 获得系统当前时间的毫秒值  */
		long currentTimes =  System.currentTimeMillis();
		
		TokenTicket tokenTicket = getData();
		if(null!=tokenTicket){
			
			if(tokenTicket.getExpires()>currentTimes){
				apiToken = tokenTicket.getAccess_token();
				apiTicket = tokenTicket.getTicket();
			}else{
				tokenTicket = null;
			}
			
		}
		
		if(null == apiToken){
			
			try {
				/** get access_token **/
				URL url = new URL("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+
						APPID+"&secret="+APPSECRET);
				
				JSONObject json = getConnection(url);
					
				access_token = (String) json.get("access_token");
//				apiExpired = json.get("expires_in");
				
				if(null == access_token){
					
					return null;
				}
				/** 设置 access_token 有效期 ： 一个半小时 **/
//				memcachedClient.set(ACCESSTOKEN, 1*60*90, access_token);
				
				
				pool.putCacheItem(ACCESSTOKEN, access_token,currentTimes + 1*60*90*1000);
//				pool.putCacheItem(ACCESSTOKEN, "1111",currentTimes + 1*60*90*1000);
			} catch (Exception e) {
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
				
//				memcachedClient.set(TICKET, 1*60*90, ticket);
				pool.putCacheItem(TICKET, ticket, currentTimes + 1*60*90*1000);
//				pool.putCacheItem(TICKET, "22222", currentTimes + 1*60*90*1000 );
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		
		if(tokenTicket == null){
			tokenTicket = new TokenTicket();
			tokenTicket.setExpires(currentTimes + 1*60*90*1000);
			tokenTicket.setAccess_token(access_token);
			tokenTicket.setTicket(apiTicket);
			
			addData(tokenTicket);
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
	
	private TokenTicket getData(){
		
		TokenTicket ticket = null;
		/** test **/
		Connection connection = DBHelper.getConnection();
		String sql = "select id,expires,access_token,ticket from token_ticket";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet set = statement.executeQuery();
			while(set.next()){
				ticket = new TokenTicket();
				ticket.setId(set.getInt(1));
				ticket.setExpires(set.getLong(2));
				ticket.setAccess_token(set.getString(3));
				ticket.setTicket(set.getString(4));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return ticket;
	}
	
	/** 添加数据 */
	public int addData(TokenTicket tokenTicket){
		String sql = "insert into token_ticket(expires,access_token,ticket) values(?,?,?)";
		int count = 0;
		Connection connection = DBHelper.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, tokenTicket.getExpires());
			statement.setString(2, tokenTicket.getAccess_token());
			statement.setString(3, tokenTicket.getTicket());
			
			count = statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
