package liyu.test.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

public class UrlConn {
	/* httpClient post json
	 * public static String getResult(String url,Map<String, String> params){
        String returnValue = null;
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
        try {
             HttpPost httppost = new HttpPost(url);
             httppost.addHeader("Content-type","application/json; charset=utf-8");  
             httppost.setHeader("Accept", "application/json");
            
             StringEntity entity = new StringEntity(JSONObject.toJSONString(params),Charset.forName("UTF-8"));    
             httppost.setEntity(entity);
             
             HttpResponse resp = httpclient.execute(httppost);
             if(resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity he = resp.getEntity();
                String respContent = EntityUtils.toString(he,"UTF-8");
                returnValue =  respContent;
             }
        }
        catch (SocketTimeoutException e) {
             e.printStackTrace();
             returnValue = "timeout";
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            httpclient.getConnectionManager().shutdown();
       }
       return  returnValue;
    }*/
	
	/* httpclient post 
	 * public static String postData(String urlStr, Map<String,String> params) throws IOException {
		HttpClient httpClient =  HttpClientBuilder.create().build();
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				BasicNameValuePair param = new BasicNameValuePair(entry.getKey(), entry.getValue());
				paramPairs.add(param);
			}
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
		HttpPost post = new HttpPost(urlStr);
		post.setEntity(entity);

		HttpResponse response = httpClient.execute(post);
		if(response.getStatusLine().getStatusCode()==200){
			InputStream content = response.getEntity().getContent();
			Scanner scan = new Scanner(content);
			scan.useDelimiter("\n");
			StringBuffer ret = new StringBuffer();
			while(scan.hasNext()){
				ret.append(scan.next());
			}
			content.close();
			return ret.toString();
		}
		return "state:"+response.getStatusLine().getStatusCode();
	}*/
	public static void main(String[] args) {
		new Timer().schedule(new R(), 1000, 1000); 
	}
	
	private static class R extends TimerTask{

		@Override
		public void run() {
			try {
				URL url = new java.net.URL("https://..rthdtax.com/rtaxhelp/webs");
				URLConnection connection = url.openConnection();
				InputStream inputStream = connection.getInputStream();
				StringBuilder sb = new StringBuilder();
				String line;

				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = br.readLine()) != null) {
				    sb.append(line);
				}
				
				System.out.println(sb);
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
