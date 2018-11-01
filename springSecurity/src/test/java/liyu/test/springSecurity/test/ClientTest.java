package liyu.test.springSecurity.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class ClientTest {

	public static void main(String[] args)  {
		String url = "http://localhost:8080/springSecurity";
		try {
			String fromUrl = readFromUrl(url, null);
			System.out.println(fromUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String readFromUrl(String url,InetSocketAddress proxyAddr) throws Exception{
		URL localURL = new URL(url);
		
		URLConnection connection;
        if (proxyAddr != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);
            connection = localURL.openConnection(proxy);
        }else {        	
        	connection = localURL.openConnection();
        } 
        
        
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;
        
        httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;
        // fail
        if (httpURLConnection.getResponseCode() != 200) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
        }
        
        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            
            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }
            
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return resultBuffer.toString();
	}
}
