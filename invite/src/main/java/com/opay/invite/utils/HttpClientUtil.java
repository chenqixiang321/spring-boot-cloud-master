package com.opay.invite.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtil {
	
	private static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public static String get(Map<String,String> map,String url) {
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建Get请求
		StringBuffer sbuffer =getStringBuffer(map);
		HttpGet httpGet = new HttpGet(url + "?" +sbuffer);
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 配置信息
			RequestConfig requestConfig =getConfig();
			httpGet.setConfig(requestConfig);
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode()!=200) {//非正常请求
				log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";response:"+response.toString());
				return "";
			}
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				String result =EntityUtils.toString(responseEntity);
				return result;
			}
			log.warn("url:"+url+";sbuffer:"+sbuffer.toString()+";response:"+response.toString());
		}catch (SocketTimeoutException|ConnectTimeoutException e) {
			log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";ConnectTimeoutException error:"+e.getMessage());
			e.printStackTrace();
		}catch (ClientProtocolException e) {
			log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";ClientProtocolException error:"+e.getMessage());
			e.printStackTrace();
		}catch (ParseException e) {
			log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";ParseException error:"+e.getMessage());
			e.printStackTrace();
		}catch (IOException e) {
			log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";IOException error:"+e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";error:"+e.getMessage());
		}finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "";
	}
	
	public static String post(Map<String,String> map,String url){
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建Get请求
		StringBuffer sbuffer =getStringBuffer(map);
		HttpPost httpPost = new HttpPost(url + "?" +sbuffer);
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 配置信息
			RequestConfig requestConfig =getConfig();
			httpPost.setConfig(requestConfig);
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode()!=200) {//非正常请求
				log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";response:"+response.toString());
				return "";
			}
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				String result =EntityUtils.toString(responseEntity);
				return result;
			}
			log.warn("url:"+url+";sbuffer:"+sbuffer.toString()+";response:"+response.toString());
		}catch (SocketTimeoutException|ConnectTimeoutException e) {
			log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";ConnectTimeoutException error:"+e.getMessage());
			e.printStackTrace();
			return "-1";
		}catch (ClientProtocolException e) {
			log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";ClientProtocolException error:"+e.getMessage());
			e.printStackTrace();
		}catch (ParseException e) {
			log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";ParseException error:"+e.getMessage());
			e.printStackTrace();
		}catch (IOException e) {
			log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";IOException error:"+e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			log.error("url:"+url+";sbuffer:"+sbuffer.toString()+";error:"+e.getMessage());
		}finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return "";//异常
	}

	public static String postEntity(Map<String,String> map,String url){

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		// 创建请求
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("charset", "UTF-8");
		getEntity(httpPost,map);
		// 响应模型
		CloseableHttpResponse response = null;
		try {
			// 配置信息
			RequestConfig requestConfig =getConfig();
			httpPost.setConfig(requestConfig);
			// 由客户端执行(发送)Get请求
			response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode()!=200) {//非正常请求
				log.error("url:"+url+";sbuffer:"+map.toString()+";response:"+response.toString());
				return "";
			}
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				String result =EntityUtils.toString(responseEntity);
				return result;
			}
			log.warn("url:"+url+";sbuffer:"+map.toString()+";response:"+response.toString());
		}catch (SocketTimeoutException|ConnectTimeoutException e) {
			log.error("url:"+url+";sbuffer:"+map.toString()+";ConnectTimeoutException error:"+e.getMessage());
			e.printStackTrace();
			return "-1";
		}catch (ClientProtocolException e) {
			log.error("url:"+url+";sbuffer:"+map.toString()+";ClientProtocolException error:"+e.getMessage());
			e.printStackTrace();
		}catch (ParseException e) {
			log.error("url:"+url+";sbuffer:"+map.toString()+";ParseException error:"+e.getMessage());
			e.printStackTrace();
		}catch (IOException e) {
			log.error("url:"+url+";sbuffer:"+map.toString()+";IOException error:"+e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			log.error("url:"+url+";sbuffer:"+map.toString()+";error:"+e.getMessage());
		}finally {
			try {
				// 释放资源
				if (httpClient != null) {
					httpClient.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return "";//异常
	}

	// 参数
	private static StringBuffer getStringBuffer(final Map<String,String> map) {
		StringBuffer params = new StringBuffer();
		Iterator<Entry<String,String>> entries = map.entrySet().iterator();
		while(entries.hasNext()){
		    Entry<String, String> entry = entries.next();
		    String key = entry.getKey();
			String value = entry.getValue();
		    params.append(key).append("=").append(value).append("&");
		}
		return params;
	}
	private static void getEntity(HttpPost post,Map<String,String> map){
		try {
			if (map != null) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : map.entrySet()) {
					pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				post.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
			}
		}catch (Exception e){}
	}
	
	private static RequestConfig getConfig() {
		RequestConfig requestConfig = RequestConfig.custom()
				// 设置连接超时时间(单位毫秒)
				.setConnectTimeout(1000)
				// 设置请求超时时间(单位毫秒)
				.setConnectionRequestTimeout(1000)
				// socket读写超时时间(单位毫秒)
				.setSocketTimeout(3000)
				// 设置是否允许重定向(默认为true)
				.setRedirectsEnabled(true).build();
		return requestConfig;
	}
}
