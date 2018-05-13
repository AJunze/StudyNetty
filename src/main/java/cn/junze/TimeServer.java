package cn.junze;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;



/**
 * Created by Administrator on 2018/5/13 0013.
 */
public class TimeServer {

    public void bind(int port){

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .childHandler(new ChildChannelHandler());
        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();

            future.channel().closeFuture().sync();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
         protected void initChannel(SocketChannel arg0){

            arg0.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) {
        int port = 8080;

        new TimeServer().bind(port);
    }

}


