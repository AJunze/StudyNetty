package cn.junze.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Administrator on 2018/5/13 0013.
 */
public class TimeClientHandler extends ChannelHandlerAdapter{

    private final ByteBuf firstMassage;

    public TimeClientHandler(){

        byte[] req = "Query Time Order".getBytes();
        firstMassage = Unpooled.buffer(req.length);
        firstMassage.writeBytes(req);

    }
    @Override
    public void channelActive(ChannelHandlerContext ctx){

        ctx.writeAndFlush(firstMassage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "utf-8");
        System.out.println("接收到的时间是：" + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exception from downstream" + cause.getMessage());
        ctx.close();
    }
}
