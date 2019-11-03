package netty.improve;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;


public class ChatClient {


    private final String host;
    private final int port;

    public  ChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run(){
        //创建客户端的一个线程组
        NioEventLoopGroup group = new NioEventLoopGroup();

        //创建客户端的启动助手
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("encoder",new ProtobufEncoder());
                        pipeline.addLast("handler",new ChatClientHandler());
                    }
                });
        
        //启动客户端
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            System.out.println("--------Client："+channel.localAddress().toString().substring(1)+"--连接成功---------");

            Scanner sc = new Scanner(System.in);
            while(sc.hasNextLine()){
                String msg = sc.nextLine();
                channel.writeAndFlush(msg+"\r\n");
            }



        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        new ChatClient("127.0.0.1",9999).run();
    }


}
