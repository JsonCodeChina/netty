package netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

//客户端业务处理类
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //通道准备事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端:"+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("老板还钱吧！", CharsetUtil.UTF_8));
    }

    //读取通道事件
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("来自服务器端的数据:"+buf.toString(CharsetUtil.UTF_8));

    }

    //通道读取完事件
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       ctx.flush();
    }

    //异常发生事件
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.close();
    }
}
