package com.srbtj.weixin.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.swing.internal.plaf.synth.resources.synth;

public class CachePool {

	private static CachePool instance; /** 缓存池唯一实例 **/
	private Map<String, Object> cacheItems; /** 缓存Map**/
	
	private CachePool(){
		cacheItems = new HashMap<String, Object>();
	}
	
	/***
	 *  获取唯一实例 
	 */
	public synchronized static CachePool getInstance(){
		if(instance == null){
			instance = new CachePool();
		}
		return instance;
	}
	/***
	 *  清除所有的Item实例
	 */
	public synchronized void clearAllItems(){
		cacheItems.clear();
	}
	/***
	 *  获得缓存实体
	 */
	public synchronized Object getCacheItem(String name){
		
		if(!cacheItems.containsKey(name)){
			return null;
		}
		CacheItem cacheItem = (CacheItem) cacheItems.get(name);
		if(cacheItem.isExpired()){
			
			return null;
		}
		return cacheItem.getEntity();
	}
	
	/***
	 *  存放缓存信息
	 */
	public synchronized void putCacheItem(String name,Object obj,long expire){
		
		if(!cacheItems.containsKey(name)){
			cacheItems.put(name, new CacheItem(obj, expire));
		}
		
		CacheItem cacheItem = (CacheItem) cacheItems.get(name);
		cacheItem.setCreateTime(new Date());
		cacheItem.setEntity(obj);
		cacheItem.setExpireTime(expire);
	}
	
	public synchronized void putCacheItem(String name,Object obj){
		putCacheItem(name, obj,-1);
	}
	
	/***
	 *  移除缓存数据
	 */
	public synchronized void removeCacheItem(String name){
		if(!cacheItems.containsKey(name)){
			return;
		}
		cacheItems.remove(name);
	}
	/***
	 *  获取缓存数量
	 */
	public int getSize(){
		return cacheItems.size();
	}
}
