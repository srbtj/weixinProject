package com.srbtj.weixin.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;

import com.srbtj.weixin.entity.TokenTicket;
import com.srbtj.weixin.schedule.CachePool;
import com.srbtj.weixin.util.WeixinRequest;

public class TimeTest {

	public static void main(String[] args) throws InterruptedException {
		
//		for(int i=0;i<1000;i++){
			
//			CachePool pool = CachePool.getInstance();
//			
//			String val = (String) pool.getCacheItem("access_token");
//			if(null == val){
//				pool.putCacheItem("access_token", "123456", 1);
//				val = "123456";
//			}
//			
//			System.out.println( "value :　" + val );
			
//			WeixinRequest request = new WeixinRequest();
//			
//			String token = request.getWeixinToken();
//			
//			System.out.println(token);
//			
//			Thread.sleep(2000);
//		}
		
		WeixinRequest request = new WeixinRequest();
		
		TokenTicket ticket = new TokenTicket();
		ticket.setExpires(111111111111l);
		ticket.setAccess_token("aa");
		ticket.setTicket("bbb");
		
		request.addData(ticket);
		
		
//		Calendar calendar = Calendar.getInstance();
//		
//		System.err.println(calendar.getTimeInMillis());
//	
//		calendar += 60*90*1000
		
//		System.out.println(calendar.getTimeInMillis());
		
//		System.out.println(System.currentTimeMillis());
		
//		long times = System.currentTimeMillis();
//		
//		System.out.println(times);
//		
//		Date date=new Date(times);
//		
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd HH:mm:ss");
//		
//		System.out.println(dateFormat.format(date));
//		times += 60*90*1000;
//		
//		date=new Date(times);
//		
//		dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd HH:mm:ss");
//		
//		
//		System.out.println(dateFormat.format(date));
	}
}
