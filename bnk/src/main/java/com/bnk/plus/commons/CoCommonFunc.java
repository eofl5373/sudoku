package com.bnk.plus.commons;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.bnk.plus.commons.components.CoTopComponent;
import com.bnk.plus.commons.components.bean.ComBean;
import com.bnk.plus.commons.util.DateUtil;
import com.bnk.plus.commons.util.StringUtil;
import com.bnk.plus.config.AppConstBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 시스템 공통 함수 클래스
 *
 * @author sw-yun
 */
@Component
public class CoCommonFunc extends CoTopComponent {

    /**
     * CoConstDef에 정의된 변수 값 참조
     * @param CoConstDef에 정의된 필드명
     * @return 해당 필드 값
     */
    public static String getCoConstDefVal(String nm) {
        try {
            return (String) CoConstDef.class.getField(nm).get(null);
        } catch (Exception e) {
            throw new RuntimeException(nm + ":" + e);
        }
    }

    /**
     * 날짜형식 변환
     * 20140101 --> 2014-01-01
     * @param dt 날짜
     * @param separator 구분자(형식)
     * @return 4-2-2[-2-2-2] 구분자에 의한 포멧 변환된 날짜
     */
    public static String formatDate(String dt, String separator) {
        StringBuffer ret = new StringBuffer();
        dt = dt.replaceAll("[^\\d]*", "");

        if(dt.length() >= 8) {
            if(CoConstDef.DISP_FORMAT_DATE_TAG_SIMPLE.equals(separator)) {
                // yyyyMMdd
                dt = dt.substring(0, 8);
            } else if(dt.length() >= 14) {
                dt = dt.substring(0, 14);
            }
        }

        if (dt.length() < 4) {
            ret.append(dt);
        } else {
            int i = 0;
            ret.append(dt.substring(0, 4));
            if (separator.length() > i) {
                ret.append(separator.charAt(i++));
            }
            dt = dt.substring(4);
            while (dt.length() >= 2) {
                ret.append(dt.substring(0, 2));
                if (separator.length() > i) {
                    ret.append(separator.charAt(i++));
                }
                dt = dt.substring(2);
            }
        }
        return ret.toString();
    }

    /**
     * @see CoCommonFunc#formatDate(String, String)
     * @param dt 대상 날짜 문자열
     * @return yyyy-mm-dd hh24:mi:ss 포멧 변환된 날짜
     */
    public static String formatDate(String dt) {
        return formatDate(dt, CoConstDef.DISP_FORMAT_DATE_TAG_DEFAULT);
    }

    /**
     * @see CoCommonFunc#formatDate(String, String)
     * @param dt 대상 날짜 문자열
     * @return yyyy-mm-dd 포멧 변환된 날짜
     */
    public static String formatDateSimple(String dt) {
        return formatDate(dt, CoConstDef.DISP_FORMAT_DATE_TAG_SIMPLE);
    }

    /**
     * taglib용 contains함수
     * @param array
     * @param item
     * @return
     */
    public static boolean contains(Object array, String item) {
        if(array != null) {
            return Arrays.asList((String[])array).contains(item);
        }
        return false;
    }
    /**
     * taglib용 equals함수
     * @param array
     * @param item
     * @return
     */
    public static boolean equals(String str1, String str2) {
    	return StringUtil.equals(str1, str2);
    }

    /**
     * String배열 elements를 구분자로 연결하여 돌려준다.
     * @param arr
     * @param sep 구분자 : Default is COMMA
     * @return
     */
    public static String arrayToString(String[] arr, String sep) {
        String tmpStr = CoConstDef.EMPTY_STRING;
        if(isEmpty(sep)) {
            sep = ","; // default value
        }
        if(arr != null) {
            for(String s : arr) {
                tmpStr += (isEmpty(tmpStr) ? "" : ",") + s.trim();
            }
        }
        return tmpStr;
    }

    public static String arrayToStringForSql(String[] arr) {
        String tmpStr = CoConstDef.EMPTY_STRING;
        if(arr != null) {
            for(String s : arr) {
                tmpStr += (isEmpty(tmpStr) ? "" : ",") + "\'" + avoidNull(s) + "\'";
            }
        }
        return tmpStr;
    }

    /**
     * String배열 elements를 구분자로 연결하여 돌려준다.
     * @param list List<String>
     * @return
     */
    public static String arrayToString(List<String> list) {
    	if(list == null || list.isEmpty()) {
    		return "";
    	}
    	String[] arr = new String[list.size()];
    	return arrayToString(list.toArray(arr), null);
    }

    /**
     * @see strToArray(String str, String deci, boolean ignoreEmpty)
     * @param str
     * @return
     */
    public static String[] strToArray(String str) {
        return strToArray(str, ",", true);
    }

    /**
     * @see strToArray(String str, String deci, boolean ignoreEmpty)
     * @param str
     * @param deci
     * @return
     */
    public static String[] strToArray(String str, String deci) {
        return strToArray(str, deci, true);
    }
    /**
     * String 분자열을 구분자로 split한 String 배열을 반환
     * @param str
     * @param deci
     * @param ignoreEmpty
     * @return
     */
    public static String[] strToArray(String str, String deci, boolean ignoreEmpty) {
        List<String> retList = new ArrayList<String>();
        if(!isEmpty(str)) {
            str = str.trim();
            if(isEmpty(deci)) {
                for(int i=0; i<str.length(); i++) {
                    String s = new Character(str.charAt(i)).toString();
                    if(ignoreEmpty && isEmpty(s)) {
                        continue;
                    }
                    retList.add(s);
                }
            } else {
                for(String s : str.split(deci)) {
                    if(ignoreEmpty && isEmpty(s)) {
                        continue;
                    }
                    retList.add(s);
                }
            }
        }


        return retList.toArray(new String[retList.size()]);
    }

    public static String getCurrentDateTime() {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        return df.format(new Date());
    }
    public static String getCurrentDateTime(String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }
    public static String getCurrentDateTime(String format, String timeZone) {
        DateFormat df = new SimpleDateFormat(format);
        // 시:분 입력으로 Asia /Seoul 설정
        TimeZone tx=TimeZone.getTimeZone( timeZone ); // "Asia/Seoul"
        df.setTimeZone(tx);
        return df.format(new Date());
    }

    /**
     * @see com.utils.StringUtil.isEmptyTrimmed(Strng)
     * @param s
     * @return
     */
    public static Boolean isEmpty(String s) {
		return StringUtil.isEmptyTrimmed(s);
	}

    /**
     * @see com.utils.StringUtil.isEmptyTrimmed(Strng)
     * @param s
     * @return
     */
    public static Boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

    /**
     * @see com.utils.StringUtil.defaultString(Strng)
     * @param s
     * @return
     */
    public static String avoidNull(String s)
    {
        return StringUtil.defaultString(s);
    }

    /**
     * @see com.utils.StringUtil.defaultString(String, String)
     * @param s
     * @return
     */
    public static String avoidNull(String s0, String s1)
    {
        return StringUtil.isEmptyTrimmed(s0) ? s1 : s0;
    }


	/**
	 * 첨부파일 upload
	 *
	 * @param CommonsMultipartFile
	 * @return HashMap
	 */
	public static HashMap<String, Object> uploadInfoCreate(MultipartFile uploadFile) {
		// 파일관련 변수
		String uploadCode = "01";
        String orgFileName = "";
        String destFileFullPath = "";
        File destFile = null;

        // 반환할 map 선언
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

		// 파일 null 체크
		if(uploadFile != null && uploadFile.getSize() > 0){
			// STEP 1 : 디렉토리 구조를 정하고 디렉토리를 만든다.
			String thisDate = DateUtil.getCurrentDateTime("yyyy-MM-dd-hh-mm-ss");

			String[] thisDateSplit = thisDate.split("\\-");


	        String year = thisDateSplit[0];
	        String month = thisDateSplit[1];
	        String day = thisDateSplit[2];
	        String hour = thisDateSplit[3];
	        String minute = thisDateSplit[4];
	        String second = thisDateSplit[5];

	        // STEP 1.2 : 파일의 경로를 get
	        String rUploadPath = "";

	        // 생성할 디렉토리 경로 완성(년 - 월)
	        rUploadPath += "attach/" +year + "/" + month;

	        // 확장자 추출
	        String fileExt = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));

	        // TODO : 확장자 체크 로직 추가

	        // 원본 파일명을 정한다.
	        orgFileName = uploadFile.getOriginalFilename();

	        // 파일명을 정한다.
	        String frontFileName = day + hour + minute + second;
	        String destFileName = frontFileName + "_" + UUID.randomUUID().toString();
	        destFileFullPath = rUploadPath + "/" + destFileName + fileExt;

	        // STEP 2 : 업로드할 full path를 정한다.
	        try{
	        	//
	        	destFile = new File(rUploadPath, destFileName+fileExt);

	        }catch(Exception e) {
	        	log.error(e.getMessage(), e);
	        	uploadCode = "00";
	        	// TODO : 메세지 처리

	        }
		}
		resultMap.put("uploadCode",uploadCode);
		resultMap.put("orgFileName",orgFileName);
		resultMap.put("destFileFullPath",destFileFullPath);
		resultMap.put("destFile",destFile);

		return resultMap;
	}

	/**
	 * Mac Address 하이픈(-) 처리
	 * @param s
	 * @return mac address
	 */
	public static String getMacAddressForm(String s) {
		String ret = "";
		String Hyphen = "-";

		if(s.length() == 12) {
			ret += s.substring(0, 2) + Hyphen + s.substring(2, 4) + Hyphen + s.substring(4, 6) + Hyphen + s.substring(6, 8);
			ret += Hyphen + s.substring(8, 10) + Hyphen + s.substring(10, 12);
		}else{
			ret = "형식에 맞지 않습니다.";
		}

		return ret;
	}

	/**
	 * <pre>
	 * 1. 설명 : {@link CoTopComponent}.bitOperAnd 참고
	 * 2. 동작 : Custom Tag Lib에서 사용하기 위해 추가
	 * 3. Input : int A, int B
	 * 4. Output : java.lang.Boolean
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2015. 8. 17.     Administrator         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @param a
	 * @param b
	 * @return boolean
	 */
	public static boolean bitOperAnd(int a, int b) {
		return CoTopComponent.bitOperAnd(a, b);
	}

	/**
	 * 패키지 내 Gson 사용을 위한 serializer bulder 한 gson 오프젝트를 반환한다.
	 * json 형태의 반환을 위해 GridBean 사용시 comBean을 extends한 하위 VO 객체의 serializer 적용
	 * <pre>
	 * 1. 설명 :
	 * 2. 동작 :
	 * 3. Input :
	 * 4. Output :
	 * 5. 수정내역
	 * ----------------------------------------------------------------
	 * 변경일                 작성자                                            변경내용
	 * ----------------------------------------------------------------
	 * 2016. 5. 2.     sw-yun         최초작성
	 * ----------------------------------------------------------------
	 * </pre>
	 *
	 * @return
	 */
	public static Gson getGsonBuiler() {
		GsonBuilder gbuilder = new GsonBuilder();
		gbuilder.registerTypeAdapter(ComBean.class, new ComBeanSerializer());
		return gbuilder.create();
	}


	public static boolean isAdmin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String result = "";
    	try{
	    	Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	    	if(!authorities.isEmpty()){
	    		for (GrantedAuthority authority : authorities) {
	    			result = (authority.getAuthority()).replaceFirst(AppConstBean.SECURITY_ROLE_PREFIX, "");
	    			break;
				}
	    	}
    	} catch(Exception e){}

    	if (auth != null && "ROLE_ADMIN".equalsIgnoreCase(result) && auth.isAuthenticated()) {
        	return true;
        }
    	return false;
    }

	public static boolean isDealer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String result = "";
    	try{
	    	Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
	    	if(!authorities.isEmpty()){
	    		for (GrantedAuthority authority : authorities) {
	    			result = (authority.getAuthority()).replaceFirst(AppConstBean.SECURITY_ROLE_PREFIX, "");
	    			break;
				}
	    	}
    	} catch(Exception e){}

    	if (auth != null && CoConstDef.SECURITY_ROLE_DEALER.equalsIgnoreCase(result) && auth.isAuthenticated()) {
        	return true;
        }
    	return false;
    }
	public static boolean isLogin() {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !"anonymousUser".equalsIgnoreCase(auth.getName()) && auth.isAuthenticated()) {
        	return true;
        }
    	return false;
	}

	public static String makeFullUrl(String path) {
		StringBuilder  rtn = new StringBuilder();
		HttpServletRequest request = null;
		try {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} catch (Exception e) {
		}
		if(request != null) {
			rtn.append(request.getScheme()).append("://").append(request.getServerName());
			int _port = request.getServerPort();
			if(_port != 80 && _port != 443) {
				rtn.append(":").append(_port);
			}
		} else {
			rtn.append(appEnv.getProperty("sys.info.site.domain"));
		}

		if(!isEmpty(path)) {
			if(!path.startsWith("/")) {
				rtn.append("/");
			}
			rtn.append(path);
		}
		return rtn.toString();
	}

	public static String phoneFomatter(String num){
		return phoneFomatter(num, false);
	}

	/**
	 * 숫자형 전화번호를 하이픈 구분 format을 변환하여 반환 <br />
	 * @param num
	 * @param doMasking : true인 경우, 가운데 자를 를 mask처리해서 반환한다.
	 * @return
	 */
	public static String phoneFomatter(String num, boolean doMasking){

		if(isEmpty(num) || !StringUtil.isNumeric(num))
		{
			return num;
		}
	    String formatNum = "";

	    num = num.replaceAll("-", "");

	    if(num.length()==11){

	        if(doMasking){
	            formatNum = num.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-****-$3");
	        }else{
	            formatNum = num.replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
	        }
	    }else if(num.length()==8){
	        formatNum = num.replace("(\\d{4})(\\d{4})", "$1-$2");
	    }else if(num.length()==12){
	    	formatNum = num.replaceAll("(\\d{4})(\\d{4})(\\d{4})", "$1-$2-$3");	//12자리 안심번호 추가
	    }else{
	        if(num.indexOf("02")==0){
	            if(doMasking){
	                formatNum = num.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "$1-****-$3");
	            }else{
	                formatNum = num.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "$1-$2-$3");
	            }
	        }else{
	            if(doMasking){
	                formatNum = num.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1-***-$3");
	            }else{
	                formatNum = num.replaceAll("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
	            }
	        }
	    }
	    return formatNum;

	}

	public static String toJson(Object obj) {
		Gson gson = CoCommonFunc.getGsonBuiler();
		return gson.toJson(obj);
	}

	public static String isChecked(Object obj, String str) {
		return isChecked(obj,str,null);
	}
	public static String isChecked(Object obj, String str, String className) {
		return contains(obj, str) ? nvl(className,"checked") : "";
	}
	
	public static String getNameAsterisk(String name){
		if(name == null || name.length() < 2 ){
            return name;
        }
        else if (name.length() == 2){
            return "*" + name.charAt(1);
        } else {
            return name.charAt(0) + "*" + name.charAt(name.length() - 1);
        }
	}
}
