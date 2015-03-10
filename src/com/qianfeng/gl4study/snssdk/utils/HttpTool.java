package com.qianfeng.gl4study.snssdk.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

/**
 * Http网络请求的工具类，包含了Get，Post，Put，Delete四种请求方法的支持
 * Created with IntelliJ IDEA.
 * I'm glad to share my knowledge with you all.
 * User:Gaolei
 * Date:2015/3/10
 * Email:pdsfgl@live.com
 */
public class HttpTool {

	/**
	 * 执行Get请求
	 * @param url   请求的网址和数据
	 * @return      字节数组，网络数据
	 */
	public static byte[] get(String url){
		byte[] ret = null;
		if(url!=null){
			HttpGet request = new HttpGet(url);
			ret = directryHttpRequest(request);
		}
		return ret;
	}

	/**
	 * 执行Delete请求
	 * @param url   请求的网址和数据
	 * @return      字节数组，网络数据
	 */
	public static byte[] delete(String url){
		byte[] ret = null;
		if(url!=null){
			HttpDelete request = new HttpDelete(url);
			ret = directryHttpRequest(request);
		}
		return ret;
	}

	/**
	 * 本方法实现Get，Delete请求的通用代码
	 * @param request
	 * @return
	 */
	private static byte[] directryHttpRequest(HttpRequestBase request) {
		byte[] ret = null;
		HttpClient client = new DefaultHttpClient();

		HttpParams httpParams = client.getParams();
		// 请求延时时间
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		// 读取内容时间
		HttpConnectionParams.setSoTimeout(httpParams, 3000);

		try {
			HttpResponse response = client.execute(request);
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == 200){
				HttpEntity entity = response.getEntity();
				ret = EntityUtils.toByteArray(entity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}


	/**
	 * 执行Post请求，采用key=value&key=value的形式
	 * @param url       请求的网址
	 * @param params    key=value的集合
	 * @return          字节数组，网络数据
	 */
	public static byte[] post(String url,HashMap<String,String> params){


		return post(url,params,"UTF-8");
	}

	/**
	 * 执行Post请求，采用key=value&key=value的形式
	 * @param url       请求的网址
	 * @param params    key=value的集合
	 * @return          字节数组，网络数据
	 */
	public static byte[] post(String url,HashMap<String,String> params,String encoding){


		byte[] ret = null;
		if(url!=null){
			HttpPost request = new HttpPost(url);
			ret = processEntityRequest(request,params,encoding);
		}
		return ret;
	}


	/**
	 * 执行Put请求
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 */
	public static byte[] put(String url,HashMap<String,String > params,String encoding){

		byte[] ret = null;
		if(url!=null){
			HttpPut request = new HttpPut(url);
			ret = processEntityRequest(request,params,encoding);
		}
		return ret;
	}

	/**
	 * 本方法实现Post，Put请求的通用代码
	 * @param request
	 * @param params
	 * @param encoding
	 * @return
	 */
	private static byte[] processEntityRequest(HttpEntityEnclosingRequestBase request,
	        HashMap<String,String > params,String encoding){

		byte[] ret = null;
		if(request!=null) {
			if (params != null) {
				//将params中的key=value组合成key=value&key=value的形式，用来向服务器提交
				LinkedList<NameValuePair> parameters = new LinkedList<NameValuePair>();

				//获取HashMap中的key集合
				Set<String> keySet = params.keySet();
				for (String key : keySet) {
					String value = params.get(key);
					parameters.add(new BasicNameValuePair(key, value));
				}
				try {

					if (encoding == null) {
						encoding = "UTF-8";
					}
					//第二个参数十分重要，指定编码格式
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, encoding);

					//设置提交的数据
					request.setEntity(entity);
				} catch (UnsupportedEncodingException e) {  //不支持的编码异常
					e.printStackTrace();
				}
			}

			HttpClient client = new DefaultHttpClient();

			HttpParams httpParams = client.getParams();
			// 请求延时时间
			HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
			// 读取内容时间
			HttpConnectionParams.setSoTimeout(httpParams, 3000);

			try {
				HttpResponse response = client.execute(request);
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					ret = EntityUtils.toByteArray(entity);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}


}
