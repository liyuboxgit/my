/*package liyu.test.task.downloadwebsite;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;


*//**
 * http测试工具类
 * @author liyu
 *//*
public class HttpClientUtil {
	
	private boolean needlogin = true;
	
	private String baseUrl;
	
	private List<Entry> entries = new ArrayList<Entry>();
	
	private CloseableHttpClient client = HttpClients.createDefault();
	*//**
	 * 报告
	 *//*
	private static StringBuffer report = new StringBuffer();
	private boolean logined;
	
	public static final int REQUEST_FORM = 1;
	public static final int REQUEST_AJAX = 2;
	*//**
	 * 
	 * @param baseUrl 连接url，如http://baidu.com/test,由host，port，domain组成
	 * @param needlogin 是否需要登录
	 *//*
	public HttpClientUtil(String baseUrl,boolean needlogin){
		Assert.hasLength(baseUrl);
		this.baseUrl = baseUrl;
		this.needlogin = needlogin;
	}
	*//**
	 * 登录
	 * @param path 请求模块路径
	 * @param username
	 * @param password
	 *//*
	public HttpClientUtil login(String path,String username,String password){
		HttpPost httpPost = new HttpPost(this.baseUrl+path);
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("username",username));
		param.add(new BasicNameValuePair("password",password));
		CloseableHttpResponse response = null;
		try {
			UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(param, "UTF-8");
			httpPost.setEntity(postEntity);
			report.append("请求开始 URL为:" + httpPost.getRequestLine()+"\n");
			printParmaList(param);
			response = client.execute(httpPost);
			printResponse(response);
			return this;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(response!=null)
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logined = true;
		}
		return null;
		
	}
	*//**
	 * 打印响应信息
	 * @param response
	 *//*
	private void printResponse(CloseableHttpResponse response) {
		HttpEntity entity = response.getEntity();
		report.append("返回状态:\n" + "\t"+ response.getStatusLine()+"\n");
		report.append("请求头信息:\n");
		HeaderIterator iterator = response.headerIterator();
		while (iterator.hasNext()) {
			report.append("\t" + iterator.next()+"\n");
		}
		
		if (entity != null) {
			String responseString = null;
			try {
				responseString = EntityUtils.toString(entity, "UTF-8");
				report.append("返回信息长度:\n" +"\t"+ responseString.length()+"\n");
				report.append("返回内容:\n" +"\t"+ responseString.replace("\r\n", ""));
				report.append("\n\n\n");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	*//**
	 * 打印参数信息
	 * @param param
	 *//*
	private void printParmaList(List<NameValuePair> param){
		report.append("请求参数:\n");
		for (NameValuePair e : param) {
			printParma(e,true);
		}
		
	}
	*//**
	 * 执行除登录外的请求
	 *//*
	public HttpClientUtil execute() {
		if (needlogin) {
			if (logined)
				for (Entry e : this.entries) {
					dopost(e);
				}
			else {
				throw new RuntimeException("请先执行登录");
			}
		} else {
			for (Entry e : this.entries) {
				dopost(e);
			}
		}

		return this;
	}
	*//**
	 * 打印整体报告
	 * file 文件名
	 *//*
	public void printReport(File file) {
				
		System.out.println(report.toString());
		
		if(this.client!=null)
			try {
				client.close();
			} catch (IOException e) {
				
			}
	}
	private void dopost(Entry el) {
		HttpPost httpPost = new HttpPost(baseUrl+el.path);
		report.append("请求开始 URL为:" + httpPost.getRequestLine()+"\n");
		if(el.param!=null){
			if(el.requestType == 1){
				UrlEncodedFormEntity postEntity;
				try {
					postEntity = new UrlEncodedFormEntity(getParam(el.param,true), "UTF-8");
					httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
					httpPost.setEntity(postEntity);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				
			}else if(el.requestType == 2){
				StringEntity stringEntity;
				try {
					String json = JSON.toJSONString(el.param);
					stringEntity = new StringEntity(json,"UTF-8");
					report.append("\t"+json+"\n");
					stringEntity.setContentType("application/json");    
					httpPost.setEntity(stringEntity);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpPost);
			printResponse(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(response != null)
			try {
				response.close();
			} catch (IOException e) {
				
			}
		}
		
	}
	
	private void doget(Entry el) {
		String url = baseUrl+el.path;
		String p = url.indexOf("?")>0?"&":"?";
		if(el.param!=null){
			List<NameValuePair> list = getParam(el.param,false);
			
			for(int i=0;i<list.size();i++){
				if(i==list.size()-1)
					p += list.get(i).getName()+"="+list.get(i).getValue();
				else
					p += list.get(i).getName()+"="+list.get(i).getValue()+"&";
			}
		}
		HttpGet httpget = new HttpGet(url+p);
		httpget.setHeader("Content-Type", "application/json");
		report.append("请求开始 URL为:" + httpget.getRequestLine()+"\n");
		
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpget);
			printResponse(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(response != null)
			try {
				response.close();
			} catch (IOException e) {
				
			}
		}
		
	}
	
	public HttpClientUtil addEntry(Entry entry){
		if(entry!=null)
			this.entries.add(entry);
		return this;
	}
	
	public static class Entry{
		private String path;
		private int requestType = 1;
		private Map<String,Object> param;
		*//**
		 * 请求体
		 * @param path 模块路径
		 * @param param 请求参数
		 * @param requestType 请求方式 ajav或者form
		 *//*
		public Entry(String path,Map<String,Object> param,int requestType){
			this.path = path;
			this.requestType = requestType;
			if(requestType>2)
				this.requestType = 1;
			this.param = param;
		}
		
		*//**
		 * 请求体
		 * @param path 模块路径
		 * @param param 请求参数
		 *//*
		public Entry(String path,Map<String,Object> param){
			this.path = path;
			this.param = param;
		}
	}
	
	private static List<NameValuePair> getParam(Map<String, Object> paramMap,boolean log) {
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		Iterator<Map.Entry<String, Object>> it = paramMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<?, ?> parmEntry = (Map.Entry<?, ?>) it.next();
			BasicNameValuePair nameValuePair = new BasicNameValuePair((String) parmEntry.getKey(), (String) parmEntry.getValue());
			param.add(nameValuePair);
			printParma(nameValuePair,log);
		}
		return param;
	}
	
	private static void printParma(NameValuePair e,boolean log) {
		if(log)
			report.append("\t"+e.getName()+"="+e.getValue()+"\n");
	}
	
	/// main方法测试
	public static void main(String[] args) {
		
		HttpClientUtil client = new HttpClientUtil("http://localhost:8080/security", false);
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("userid", "1");
		map.put("userage", "9");
		map.put("username", "战狼");
		
		.login("/sec/login", "scott", "123456")
		
		client
			.addEntry(new HttpClientUtil.Entry("/api/form",map,HttpClientUtil.REQUEST_FORM))
			.addEntry(new HttpClientUtil.Entry("/api/ajax",map,HttpClientUtil.REQUEST_AJAX))
			.execute()
			.printReport(null);
	}
}
*/