package liyu.test.netty.handler;

import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
	static class MyClientHandler extends SimpleChannelInboundHandler<Integer> {
	    @Override
	    protected void channelRead0(ChannelHandlerContext ctx, Integer msg) throws Exception {
	        System.out.println("client output "+ctx.channel().remoteAddress() + msg);
	    }

	    @Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
	        ctx.writeAndFlush(1);
	        ctx.writeAndFlush(Unpooled.copiedBuffer("aaaa", Charset.forName("ISO-8859-1")));
	        // 因为解码器是int(4个字节)，b会被丢弃
	        ctx.writeAndFlush(Unpooled.copiedBuffer("aaaab", Charset.forName("ISO-8859-1"))); 
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	        super.exceptionCaught(ctx, cause);
	        ctx.close();
	    }
	}
	static class MyClientIniatializer extends ChannelInitializer<SocketChannel> {
	    @Override
	    protected void initChannel(SocketChannel ch) throws Exception {
	        ChannelPipeline pipline = ch.pipeline();
	        pipline.addLast(new IntegerToByteEncoder());
	        pipline.addLast(new ByteToIntegerDecoder());
	        pipline.addLast(new MyClientHandler());
	    }
	}
	public static void main(String[] args) throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyClientIniatializer());
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
