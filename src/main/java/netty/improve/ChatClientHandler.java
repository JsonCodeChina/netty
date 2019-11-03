package netty.improve;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {


    //通道准备就绪
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("客户端:"+channel.localAddress().toString().substring(1)+"上线了");
        PersonModel.Person personModel = PersonModel.Person.newBuilder().setId(1).setName("沈博").setEmail("ss").build();
        channel.writeAndFlush(personModel);
    }


    //通道未准备就绪
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("客户端:"+channel.localAddress().toString().substring(1)+"掉线了");
    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    //读取通道数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg.trim());
    }
}
