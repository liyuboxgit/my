package liyu.test.netty;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class testNettyByteBuf {
	public static String convertByteBufToString(ByteBuf buf) {
		String str;
		if(buf.hasArray()) { // 处理堆缓冲区
		str = new String(buf.array(), buf.arrayOffset() + buf.readerIndex(), buf.readableBytes());
		} else { // 处理直接缓冲区以及复合缓冲区
		byte[] bytes = new byte[buf.readableBytes()];
		buf.getBytes(buf.readerIndex(), bytes);
		str = new String(bytes, 0, buf.readableBytes());
		}
		return str;
		}
	public static void main(String[] args) {
		ByteBuf buffer = Unpooled.buffer(2);
		buffer.writeCharSequence("hello,世界", Charset.forName("UTF-8"));
		
		System.out.println(convertByteBufToString(buffer));

	}

}
