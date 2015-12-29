package com.srbtj.weixin.util;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.druid.util.IOUtils;
import com.srbtj.weixin.entity.response.ResponseTextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class XMLParseUtil {
	
	/** 扩展标志 **/
	private static String PREFIX_CDATA="<![CDATA[";
	private static String SUFFIX_CDATA="]]>";
	private static XStream xStream = createXStream();
	
	/** 接收的消息类型  **/
	public static String RECEIVE_TEST = "text";  /* 文本 **/
	public static String RECEIVE_IMAGE = "image"; /* 图片 **/
	public static String RECEIVE_VOICE = "voice"; /* 语音 **/
	public static String RECEIVE_VIDEO = "video"; /* 视频  **/
	public static String RECEIVE_NEWS = "news"; /* 图文 **/
	public static String RECEIVE_MUSIC = "music"; /* 音乐  **/

	public static Map<String, String> parseXml(HttpServletRequest request){
		
		Map<String, String> maps = new HashMap<String, String>();
		try {
			/** 获得输入流 */
			InputStream is = request.getInputStream();
			/** 读取输入流 */
			SAXReader reader = new SAXReader();
			Document document = reader.read(is);
			/** 获得根元素  */
			Element root = document.getRootElement();
			/** 获得根元素下的所有子节点 */
			@SuppressWarnings("unchecked")
			List<Element> eList = root.elements();
			
			/** 循环读取根元素下的所有子节点 **/
			for(Element element : eList){
				maps.put(element.getName(), element.getText());
			}
			is.close();
			is = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maps;
	}
	
	public static String textMessageToXML(ResponseTextMessage message){
		
		xStream.alias("xml", message.getClass());
	
		return xStream.toXML(message);
	}
	
	/***
	 *  扩展 xStream 使其支持CDATA块 
	 *  
	 *  字符串加上 CDATA扩展 
	 *  float与时间不加任何处理
	 */
	public static XStream createXStream(){
		
	
		return new XStream(new XppDriver() {  
	
        public HierarchicalStreamWriter createWriter(Writer out) {  
            return new PrettyPrintWriter(out) {  
                // 对所有xml节点的转换都增加CDATA标记  
                boolean cdata = false;  
  
                @SuppressWarnings("unchecked")  
                public void startNode(String name, Class clazz) {  
                    
                	if(!name.equals("xml")){
                		
                		char[] arr = name.toCharArray();
                		
                		if(arr[0] >= 'a' && arr[0] <= 'z'){
                			
                			arr[0] = (char)((int) arr[0] - 32);
                		}
                		
                		name = new String(arr);
                	}
                	
                	super.startNode(name, clazz);
                }  
                
                @Override
                public void setValue(String text) {
                	
                	if(null!=text && !"".equals(text)){
                		
                		/** 浮点数判断  */
                		Pattern floatPattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
                		/** 整形判断 **/
                		Pattern intPattern = Pattern.compile("[0-9]+");
                		/** 如果是整形或浮点数 不加CDATA **/
                		if(floatPattern.matcher(text).matches() || intPattern.matcher(text).matches()){
                			
                			cdata = false;
                		}else{
                			
                			cdata = true;
                		}
                	}
                	
                	super.setValue(text);
                };
  
                protected void writeText(QuickWriter writer, String text) {  
                	
                    if (cdata) {  
                        writer.write(PREFIX_CDATA);  
                        writer.write(text);  
                        writer.write(SUFFIX_CDATA);  
                    } else {  
                        writer.write(text);  
                    }  
                }  
            };  
        }  
		});  
	}
	
	/**
	 *  string --> xml
	 * @param str
	 * @return
	 */
	
	public static Map<String, String> stringToXML(String str){
		
		Map<String,String> maps = new HashMap<String,String>();
		
		try {
			Document document =DocumentHelper.parseText(str);
			Element element = document.getRootElement();
			List<Element> eList = element.elements();
			for(Element ele : eList){
				maps.put(ele.getName(), ele.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return maps;
	}
	
}
