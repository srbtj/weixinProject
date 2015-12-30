package com.srbtj.weixin.test;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class GetTokenTest {

	private static String url = "http://localhost:8080/weixinProject/getAccessToken";
	private static CloseableHttpClient httpClient = HttpClients.createDefault();
	
	public static void main(String[] args) throws ParseException, IOException {
		
		/***
		 *  httpClient get 请求
		 */
		
		CloseableHttpResponse response = null;
		
		HttpGet get = doGet(url);
		
		response = execute(get);
		
		if(response == null) return ;
		
		String result = EntityUtils.toString(response.getEntity());
		
//		JSONObject json = new JSONObject();
//		
//		JSONObject jsonObject = json.
	}
	
	public static HttpGet doGet(String url) {  
        HttpGet httpGet = new HttpGet(url);  
        httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");  
        httpGet.addHeader("Connection", "Keep-Alive");  
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");  
        httpGet.addHeader("Cookie", "");  
        return httpGet;  
  
    } 
	
	private static CloseableHttpResponse execute(HttpGet httpGet){
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			int state = response.getStatusLine().getStatusCode();
			if(state >= 200 && state < 300){
				return response;
			}
			response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static void close(CloseableHttpResponse response){
		try {
			if(response != null) response.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
