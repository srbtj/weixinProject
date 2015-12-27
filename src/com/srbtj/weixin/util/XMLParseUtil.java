package com.srbtj.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.srbtj.weixin.entity.response.ResponseMessage;
import com.thoughtworks.xstream.XStream;

public class XMLParseUtil {
	

	public static Map<String, String> parseXml(/**HttpServletRequest request**/ InputStream is){
		
		Map<String, String> maps = new HashMap<String, String>();
		try {
			/** 获得输入流 */
//			InputStream is = request.getInputStream();
			/** 读取输入流 */
			SAXReader reader = new SAXReader();
			Document document = reader.read(is);
			/** 获得根元素  */
			Element root = document.getRootElement();
			/** 获得根元素下的所有子节点 */
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
	
	public static String textMessageToXML(ResponseMessage message){
		XStream xStream = new XStream();
		xStream.alias("xml", message.getClass());
	
		return xStream.toXML(message);
	}
}
