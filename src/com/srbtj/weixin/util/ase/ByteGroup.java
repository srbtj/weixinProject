package com.srbtj.weixin.util.ase;

import java.util.ArrayList;
import java.util.List;


public class ByteGroup {

	List<Byte> bytes = new ArrayList<Byte>();
	
	public byte[] toBytes(){
		
		byte[] temp = new byte[bytes.size()];
		
		for(int i=0,iLen = bytes.size();i<iLen; i++){
			
			temp[i] = bytes.get(i);
		}
		
		return temp;
	}
	
	public ByteGroup addBytes(byte[] temp){
		
		for(byte b : temp){
			
			bytes.add(b);
		}
		
		return this;
	}
	
	public int size(){
		
		return bytes.size();
	}
}
