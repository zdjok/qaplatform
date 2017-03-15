package com.yt.qa.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@Component
public class RequestClient {
	@Autowired
	UDB2HeaderEntity headerEntity;
	
	DefaultHttpClient httpClient = new DefaultHttpClient();;
	HttpPost post;
	String accessToken= "";
	String areaId = "";
	String resourceId = "";
	String usId = "";
	String unicode = "";
	
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getUsId() {
		return usId;
	}

	public void setUsId(String usId) {
		this.usId = usId;
	}

	public String getUnicode() {
		return unicode;
	}

	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}
	
	
	public  JSONObject  doPost(String url,JSONObject jsonParam){
		System.out.println(">>>>>>>>>Request Url : " + url);
		System.out.println(">>>>>>>>>Request Post Body : " + jsonParam );
        //post请求返回结果
        JSONObject jsonResult = new JSONObject();
        post = new HttpPost(url);
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                /*
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                */
                updateHeader();
                post.setEntity(new StringEntity(jsonParam.toString(), "utf-8"));
             
            }
            HttpResponse result = httpClient.execute(post);
            
            //result.setHeader("Content-Type", "image/jpeg");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
            	//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+result.getHeaders("Content-Type")[0].getValue().toString());
            	getHeader();
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    /**把json字符串转换成json对象**/
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) {
                }
            }
        } catch (IOException e) {
        }finally{
        	//post.abort();
        	post.releaseConnection();
        }
        return jsonResult;
    }
	
	public void updateHeader(){
		post.setHeader("Content", "application/json;charset=utf-8");
        //post.setHeader("connection", "Keep-Alive");
        //"Content-Type","application/json;charset=utf-8"
        post.setHeader("Content-Type", "application/json");
        //post.setHeader("Connection", "close");
        
		if (accessToken != null && accessToken.trim().length() > 0) {
			post.setHeader("accessToken", accessToken);
		}
		if (areaId != null && areaId.trim().length() > 0) {
			post.setHeader("areaId", areaId);
		}
		if (resourceId != null && resourceId.trim().length() > 0) {
			post.setHeader("resourceId", resourceId);
		}
		if (usId != null && usId.trim().length() > 0) {
			post.setHeader("usId", usId);
		}
		if (unicode != null && unicode.trim().length() > 0) {
			post.setHeader("unicode", unicode);
		}
	}
	
	public void updateHeader(String accessToken , String unicode , String usId){
		setAccessToken(accessToken);
		setUnicode(unicode);
		setUsId(usId);
		updateHeader();
	}
	
	public void updateHeader(JSONObject json){
		String accessToken = json.optString("accessToken");
		String unicode = json.optString("unicode");
		String usId = json.optString("usId");
		this.updateHeader(accessToken , unicode,usId);
	}
	
	public void getHeader(){
		Header[] header = post.getHeaders("accessToken");
		if (header.length > 0  && header[0].getValue() != null && header[0].getValue().length() > 0) {
			accessToken = header[0].getValue();
		}
		header = post.getHeaders("areaId");
		if (header.length > 0  && header[0].getValue() != null && header[0].getValue().length() > 0) {
			areaId = header[0].getValue();
		}
		header = post.getHeaders("resourceId");
		if (header.length > 0  && header[0].getValue() != null && header[0].getValue().length() > 0) {
			resourceId = header[0].getValue();
		}
		header = post.getHeaders("usId");
		if (header.length > 0  && header[0].getValue() != null && header[0].getValue().length() > 0) {
			usId = header[0].getValue();
		}
		header = post.getHeaders("unicode");
		if (header.length > 0  && header[0].getValue() != null && header[0].getValue().length() > 0) {
			unicode = header[0].getValue();
		}
		header = post.getAllHeaders();
		for(Header head : header){
			System.out.println( head.getName() + " : " + head.getValue());
		}
		
	}
	
	
	public void setHeader(){
		String signature = headerEntity.getSignature();
		String client_id = headerEntity.getClient_id();
		String unicode = headerEntity.getUnicode();
		String access_token = headerEntity.getAccess_token();
		
		post.setHeader("Content-Type", "application/json;charset=utf-8");
//        post.setHeader("Content-Type", "application/json");
		if (signature != null && signature.trim().length() > 0) {
			post.setHeader("signature", signature);
		}
		if (client_id != null && client_id.trim().length() > 0) {
			post.setHeader("client_id", client_id);
		}
		if (unicode != null && unicode.trim().length() > 0) {
			post.setHeader("unicode", unicode);
		}
		if (access_token != null && access_token.trim().length() > 0) {
			post.setHeader("access_token", access_token);
		}
	}
	
	public JSONObject doUDBPost(String url, JSONObject jsonParams){
		System.out.println(">>>>>>>>>Request Url : " + url);
		System.out.println(">>>>>>>>>Request Post Body : " + jsonParams );
		
		JSONObject res = new JSONObject();
		post = new HttpPost(url);

		try {
			post.setEntity(new StringEntity(jsonParams.toString(), "UTF-8"));
			
			setHeader();
			HttpResponse httpResponse = httpClient.execute(post);
			int status = httpResponse.getStatusLine().getStatusCode();
			String str = "";
			if(status >= 200){
				str = EntityUtils.toString(httpResponse.getEntity());
				res = JSONObject.fromObject(str);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			post.releaseConnection();
		}
		
		return res;
	}
	
	public String doGetAndFetchRedirectURL(String url, String methodType){
		String redirectURL = "";
		System.out.println(url);
		try {
			URL uri = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setRequestMethod(methodType);
			//必须设置false，否则会自动redirect到Location的地址
			conn.setInstanceFollowRedirects(false); 
			conn.connect();
			redirectURL = conn.getHeaderField("Location");  
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return redirectURL;
	}
	
}
