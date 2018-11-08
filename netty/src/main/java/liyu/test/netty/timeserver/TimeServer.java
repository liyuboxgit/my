package liyu.test.netty.timeserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer  {
	private int port;

	public TimeServer(int port) {
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); 
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap(); 
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) 
					.childHandler(new ChannelInitializer<SocketChannel>() { 
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new TimeServerHandler());
						}
					}).option(ChannelOption.SO_BACKLOG, 128) 
					.childOption(ChannelOption.SO_KEEPALIVE, true); 
			ChannelFuture f = b.bind(port).sync(); 
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		new TimeServer(port).run();
	}
}

class TimeServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(final ChannelHandlerContext ctx) { 
		final ByteBuf time = ctx.alloc().buffer(4); 
		time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
		final ChannelFuture f = ctx.writeAndFlush(time); 
		f.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) {
				assert f == future;
				ctx.close();
			}
		}); 
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}