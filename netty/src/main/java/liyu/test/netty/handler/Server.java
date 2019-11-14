package liyu.test.netty.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
	static class MyServerHandler extends SimpleChannelInboundHandler<Integer>{
		@Override
	    protected void channelRead0(ChannelHandlerContext ctx, Integer msg) throws Exception {
	        System.out.println(ctx.channel().remoteAddress()+" --> "+msg);
	        ctx.writeAndFlush(msg);
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	        cause.printStackTrace();
	        ctx.close();
	    }
	}
	static class MyServerInitializer extends ChannelInitializer<SocketChannel>{
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
	        ChannelPipeline pipline = ch.pipeline();
	        pipline.addLast(new IntegerToByteEncoder());
	        pipline.addLast(new ByteToIntegerDecoder());
	        pipline.addLast(new MyServerHandler());
	    }
	}
	public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally{
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
