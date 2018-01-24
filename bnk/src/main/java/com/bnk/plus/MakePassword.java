package com.bnk.plus;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.dom4j.DocumentException;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import com.bnk.plus.commons.util.CryptUtil;
import com.bnk.plus.commons.util.DateUtil;
import com.bnk.plus.config.AppConstBean;

public class MakePassword{
	
	public static void main(String[] args) throws IOException, DocumentException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
//		String str = "admin";
//		
//		StandardPasswordEncoder s = new StandardPasswordEncoder(AppConstBean.StandardPasswordEncoderSalt);
//		System.out.println(s.encode(str));
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		String resultCode = "00";
		
		resultMap.put("code", resultCode);
		System.out.println("step 1 "+resultMap.get("code"));
		resultCode = "99";
		System.out.println("step 2 "+resultMap.get("code"));
		
		
		
		String yturl= "(?:https?:\\/\\/)?(?:www\\.)?(?:youtube\\.com|youtu\\.be)\\/(?:watch\\?v=)?([\\w\\-]{10,12})(?:&feature=related)?(?:[\\w\\-]{0})?";
		Pattern p = Pattern.compile(yturl);
		Matcher m = p.matcher("https://www.youtube.com/watch?v=CIcH2BqYf2k");
		
		while(m.find()){
			System.out.println(m.start() + m.group());
			System.out.println(m.group(1));
		}
		//String ytplayer= '<iframe width="640" height="360" src="http://www.youtube.com/embed/$1" frameborder="0" allowfullscreen></iframe>';
		//str.replace(yturl, ytplayer);
		
	}
	
}
