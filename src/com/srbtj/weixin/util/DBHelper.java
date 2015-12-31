package com.srbtj.weixin.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {

	private static Connection connection;
	private static PreparedStatement preparedStatement;
	
	private static String username = "root";
	private static String password = "root";
	private static String url = "jdbc:mysql://localhost:3306/srbtj?user="+username+"&password="+
			password+"&useUnicode=true&characterEncoding=UTF8" ;
	public static Connection getConnection(){
		 try{   
			
			 Class.forName("com.mysql.jdbc.Driver");
			 connection = DriverManager.getConnection(url);   
	     }catch(SQLException | ClassNotFoundException se){   
		    System.out.println("数据库连接失败！");   
		    se.printStackTrace() ;   
	     }   
		 return connection;
	}
	
	public static void close(){
		
		if(connection != null){
			
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
