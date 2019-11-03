package netty.rpc.clientstub;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ResultHandler extends ChannelInboundHandlerAdapter {


    private Object respone;

    public Object getRespone(){
        return  respone;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        respone = msg;
        ctx.close();
    }
}
