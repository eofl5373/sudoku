package com.bnk.plus.commons; 

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class LayerFunc {
	
	public static final String TAG = "LayerFunc";
	
    public static boolean exist(HttpServletRequest req, String path) {
    	boolean result = true;
    	
    	URL fileUrl;
		try {
			fileUrl = req.getServletContext().getResource(path);
			new File( fileUrl.getFile() );
			
		} catch (MalformedURLException e) {
			result = false;
		} catch (Exception e){
			result = false;
//			System.out.println(TAG + " Exception : " + e.getMessage());
		}
    	return result;
    }
}
