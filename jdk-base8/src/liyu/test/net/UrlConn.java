package liyu.test.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

public class UrlConn {
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
