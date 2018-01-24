package com.bnk.plus.commons.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtil {

	private static final String URL = "jdbc:mysql://dev.think-tree.co.kr:3308/LgeOsc";
	private static final String USERNAME = "oss";
	private static final String PASSWORD = "oss";
	
	public static Connection getConn() {
	
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
