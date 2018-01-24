package com.bnk.plus.commons.util;

import javax.servlet.http.Part;

public class FileUtil {
	
	/**
	 * 외부에서 생성자를 호출하지 못하도록 접근제어자 private 으로 설정
	 */
	private FileUtil(){}
	
	/**
	 * Part 에서 File Name을 추출하여 리턴한다.
	 * 
	 * @param javax.servlet.http.Part
	 * @return File Name(Type String)
	 */
	public static String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				return cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
}
