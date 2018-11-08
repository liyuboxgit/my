package liyu.test.netty.discard;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
/**
 * 
 * @Description 此类描述的是：由于没有telnet，使用java socket代替，但是服务端接收不到数据，只能接受到应答
 * @author: you@rthdtax.com
 * @version: 2018年11月8日 上午11:08:22
 */
public class DiscardClient {
	public static void main(String[] args) {
		Socket socket = null;
		try {
			socket = new Socket("localhost", 8080);
			OutputStream out = socket.getOutputStream();
			ByteBuf header = Unpooled.buffer(1024);
	        while(true) {	        	
	        	header.clear();
	        	header.writeBytes("Hello...".getBytes());
	        	out.write(header.array());
	        	out.flush();
	        	Thread.sleep(2000);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
