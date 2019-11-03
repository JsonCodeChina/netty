package netty.improve;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class ChartServerHandler extends ChannelInboundHandlerAdapter {

    public static List<Channel> channels = new ArrayList<>();//存储已经就绪的通道


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server:"+ctx);
        PersonModel.Person person = (PersonModel.Person) msg;
        System.out.println("客户端发来的信息:"+person.getName());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();//得到通道
        channels.add(channel);

        System.out.println("Server：【"+channel.remoteAddress().toString().substring(1)+"】用户在线");


    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.remove(channel);//从集合中移除
        System.out.println("Server：【"+channel.remoteAddress().toString().substring(1)+"】用户掉线了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("Server：【"+channel.remoteAddress().toString().substring(1)+"】出了异常："+cause.getMessage());
        ctx.close();
    }


}
