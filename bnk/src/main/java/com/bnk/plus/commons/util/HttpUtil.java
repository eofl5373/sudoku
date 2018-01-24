package com.bnk.plus.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author kschoi
 */
public class HttpUtil {

	/**
	 * 생성자, 외부에서 객체를 인스턴스화 할 수 없도록 설정
	 */
	private HttpUtil() {}

	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	
	/**
     * Represents an HTTP connection
     */
    private static HttpURLConnection httpConn;
 
    /**
     * Makes an HTTP request using GET method to the specified URL.
     * 
     * @param requestURL
     *            the URL of the remote server
     * @return An HttpURLConnection object
     * @throws IOException
     *             thrown if any I/O error occurred
     */
    public static HttpURLConnection sendGetRequest(String requestURL)
            throws IOException {
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
 
        httpConn.setDoInput(true); // true if we want to read server's response
        httpConn.setDoOutput(false); // false indicates this is a GET request
 
        return httpConn;
    }
 
    /**
     * Makes an HTTP request using POST method to the specified URL.
     * 
     * @param requestURL
     *            the URL of the remote server
     * @param params
     *            A map containing POST data in form of key-value pairs
     * @return An HttpURLConnection object
     * @throws IOException
     *             thrown if any I/O error occurred
     */
    public static HttpURLConnection sendPostRequest(String requestURL,
            Map<String, String> params) throws IOException {
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        
        int timeout_ = 10*1000;
        httpConn.setReadTimeout(timeout_);
        httpConn.setDoInput(true); // true indicates the server returns response
 
        StringBuffer requestParams = new StringBuffer();
 
        if (params != null && params.size() > 0) {
 
            httpConn.setDoOutput(true); // true indicates POST request
 
            // creates the params string, encode them using URLEncoder
            Iterator<String> paramIterator = params.keySet().iterator();
            while (paramIterator.hasNext()) {
                String key = paramIterator.next();
                String value = params.get(key);
                requestParams.append(URLEncoder.encode(key, "UTF-8"));
                requestParams.append("=").append(
                        URLEncoder.encode(value, "UTF-8"));
                requestParams.append("&");
            }
 
            // sends POST data
            OutputStreamWriter writer = new OutputStreamWriter(
                    httpConn.getOutputStream());
            writer.write(requestParams.toString());
            writer.flush();
        }
 
        return httpConn;
    }
 
    /**
     * Returns only one line from the server's response. This method should be
     * used if the server returns only a single line of String.
     * 
     * @return a String of the server's response
     * @throws IOException
     *             thrown if any I/O error occurred
     */
    public static String readSingleLineRespone() throws IOException {
        InputStream inputStream = null;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("Connection is not established.");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
 
        String response = reader.readLine();
        reader.close();
 
        return response;
    }
 
    /**
     * Returns an array of lines from the server's response. This method should
     * be used if the server returns multiple lines of String.
     * 
     * @return an array of Strings of the server's response
     * @throws IOException
     *             thrown if any I/O error occurred
     */
    public static String[] readMultipleLinesRespone() throws IOException {
        InputStream inputStream = null;
        if (httpConn != null) {
            inputStream = httpConn.getInputStream();
        } else {
            throw new IOException("Connection is not established.");
        }
 
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        List<String> response = new ArrayList<String>();
 
        String line = "";
        while ((line = reader.readLine()) != null) {
            response.add(line);
        }
        reader.close();
 
        return (String[]) response.toArray(new String[0]);
    }
     
    /**
     * Closes the connection if opened
     */
    public static void disconnect() {
        if (httpConn != null) {
            httpConn.disconnect();
        }
    }
	
	public static String getClientIpAddress(HttpServletRequest request){
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("Proxy-Client-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("HTTP_CLIENT_IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getRemoteAddr(); 
		}
		
		return ip;
	}
	
	private static String post(String url, Map params, String encoding, Map<String, String> headers){
		HttpClient client = null;
		HttpPost post = null;
		ResponseHandler<String> rh = null;
        try{
        	client = HttpClientBuilder.create().build();
            post = new HttpPost(url);
            if(headers != null && !headers.isEmpty()) {
            	for(String hKey : headers.keySet()) {
            		String hVal = headers.get(hKey);
            		if(!StringUtil.isEmpty(hKey) && !StringUtil.isEmpty(hVal)) {
            			post.setHeader(hKey, hVal);
            		}
            	}
            }
            List<NameValuePair> paramList = convertParam(params);
            post.setEntity(new UrlEncodedFormEntity(paramList, encoding));
            rh = new BasicResponseHandler();
            return client.execute(post, rh);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            log.error(url);
            if(rh != null) {
            	log.error(rh.toString());
            }
            // 일시적 통신장애 확인을 위해 2초후에 다시 시도
            if(client != null && post != null && rh != null) {
            	try {
            		new Thread();
					Thread.sleep(2000);
                    return client.execute(post, rh);
				} catch (Exception e2) {
					log.debug(e.getMessage(), e);
				}
            }
        }
        return "error";
    }
	private static List<NameValuePair> convertParam(Map params){
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        Iterator<String> keys = params.keySet().iterator();
        while(keys.hasNext()){
            String key = keys.next();
            paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
         
        return paramList;
    }

	/**
	 * API 호출 결과를 String으로 반환한다.
	 * Gson gson = CoCommonFunc.getGsonBuiler();
	 * Type type = new TypeToken<Map<String, Object>>(){}.getType();
	 * Map<String, Object> resultMap = (Map<String, Object>) gson.fromJson(result , type);
	 *
	 * @param url the url
	 * @param params the params
	 * @param headers the headers
	 * @return the string
	 */
	public static String post(String url, Map params, Map<String, String> headers){
        return post(url, params, "UTF-8", headers);
    }
	
	public static String post(String url, Map params){
        return post(url, params, "UTF-8", null);
    }
}