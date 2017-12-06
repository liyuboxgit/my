package liyu.test.task.downloadwebsite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MyClient{
	
	private CloseableHttpClient client = HttpClients.createDefault();
	  
	private String doget(String url){
		HttpGet httpGet = new HttpGet(url);
	
		httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*;q=0.8");  
	    httpGet.setHeader("Accept-Encoding", "gzip, deflate"); 
	    httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.9");  
	    httpGet.setHeader("Cache-Control", "max-age=0");  
	    httpGet.setHeader("Connection", "keep-alive");
	    httpGet.setHeader("Cookie", "cloudIndexUrl=\"\"; RSESSIONID=AB81BF4D34E47BFE5A5EF61B3FB81D47; IDWORKSPC=JPMF_CONF_SCK; USERNAME=`61646D40736E736F66742E636F6D2E636E`; SYSID=00");  
	    httpGet.setHeader("Host", "120.78.130.137");
	    //httpGet.setHeader("If-None-Match","W/\"5a1d1f1a-879a\"");
	    httpGet.setHeader("Upgrade-Insecure-Requests", "1");  
	    httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.75 Safari/537.36");
	    
	    CloseableHttpResponse response = null;
		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String responseString = EntityUtils.toString(entity, "UTF-8");
				return responseString;
			}
			return null;	
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
		
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		String url = "http://120.78.130.137/erp/JPMF_CONF_SCK/zh_CN/theme1/27.TS.10.30.15.10.N9.html";
		MyClient my = new MyClient();
		String ret = my.doget(url);
		System.out.println(ret);
		
		String domain = "erp";
		String root = "c:/liyu/rthd/apache-tomcat-8.5.23/webapps/";
		
		String dom = url.substring(url.indexOf(domain), url.lastIndexOf("/"));
		
		File file = new File(root+dom);
		mkDir(file);
		
		System.out.println(file.getPath());
		
		String filename = url.substring(url.lastIndexOf("/")+1);
		
		System.out.println(filename);
		
		if(ret!=null){
			File html = new File(file+File.separator+filename);
			System.out.println(file+File.separator+filename);
			boolean createFile = html.createNewFile();
			
			System.out.println(html.exists());
			
			writeToFile(ret,html);
			
			
			BufferedReader reader = null;
	        ArrayList<String> js = new ArrayList<String>();
	        ArrayList<String> css = new ArrayList<String>();
	        
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(html));
	            String tempString = null;
	             
	            while ((tempString = reader.readLine()) != null){
	            	String trim = tempString.trim();
	            	if(trim.startsWith("<script")){
	            		if(trim.indexOf("src")>-1){	            			
	            			String script = trim.substring(trim.indexOf("src='")+5,trim.indexOf("'>"));
	            			System.out.println(script);
	            			js.add(script);
	            		}
	            	}
	            	if(trim.startsWith("<link")){
	            		if(trim.indexOf("href")>-1){	            			
	            			String script = trim.substring(trim.indexOf("href='")+6,trim.indexOf("' rel"));
	            			System.out.println(script);
	            			css.add(script);
	            		}
	            	}
	            }
	            
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
            } finally {
	            if (reader != null){
		            try {
		            	reader.close();
		            } catch (IOException e1) {}
	            }
            }
	        
	        System.out.println(js.size());
	        System.out.println(css.size());
	        
	        String u = url.substring(0, url.lastIndexOf("/")+1);
	        
	        for(int i=0;i<js.size();i++){
	        	System.out.println(js.get(i));
	        	String jsf = js.get(i);
	        	String jscont = new MyClient().doget(u+jsf);
	        	System.out.println(u+jsf);
	        	//System.out.println(jscont);
	        	String substring = jsf.substring(jsf.lastIndexOf("../")+2,jsf.lastIndexOf("/"));
	        	String name = jsf.substring(jsf.lastIndexOf("/"));
	        	System.out.println(substring);
	        	System.out.println(root+domain+substring);
	        	
	        	File cssdir = new File(root+domain+substring);
	    		mkDir(cssdir);
	    		if(name.indexOf("?")>-1)
	    			name = name.substring(0, name.indexOf("?"));
	    		File f = new File(root+domain+substring+name);
	    		System.out.println(root+domain+substring+name);
	    		f.createNewFile();
	        	
	    		writeToFile(jscont,f);
	        }
	        for(int i=0;i<css.size();i++){
	        	System.out.println(css.get(i));
	        	String cssf = css.get(i);
	        	String csscont = new MyClient().doget(u+cssf);
	        	System.out.println(u+cssf);
	        
	        	System.out.println(csscont);
	        	
	        	String substring = cssf.substring(cssf.lastIndexOf("../")+2,cssf.lastIndexOf("/"));
	        	String name = cssf.substring(cssf.lastIndexOf("/"));
	        	
	        	System.out.println(substring);
	        	System.out.println(root+domain+substring);
	        	File cssdir = new File(root+domain+substring);
	    		mkDir(cssdir);
	        	
	    		if(name.indexOf("?")>-1)
	    			name = name.substring(0, name.indexOf("?"));
	    		File f = new File(root+domain+substring+name);
	    		System.out.println(root+domain+substring+name);
	    		f.createNewFile();
	        	
	    		writeToFile(csscont,f);
	        }
		}
	}
	private static void writeToFile(String ret, File html) {
		FileOutputStream fop = null;
		try {
			fop = new FileOutputStream(html);
			byte[] contentInBytes = ret.getBytes();
			fop.write(contentInBytes);
			fop.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fop!=null){
				try {
					fop.close();
				} catch (IOException e) {
					
				}
			}
		}
	}

	public static void mkDir(File file) {  
        if (file.getParentFile().exists()) {  
            file.mkdir();  
        } else {  
            mkDir(file.getParentFile());  
            file.mkdir();    
        }  
    }  
	   
}
