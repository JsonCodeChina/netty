package netty.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //创建一个线程组：接受客户端连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        //创建一个线程组：用来处理网络操作
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建服务器端启动助手 配置参数
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup,workerGroup)//设置两个线程组
        .channel(NioServerSocketChannel.class)//netty底层服务器端的实现
        .option(ChannelOption.SO_BACKLOG,128)//设置线程队列中等待连接的个数
        .childOption(ChannelOption.SO_KEEPALIVE,true)//保持活动的连接状态
        .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道初始化对象
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new NettyServerHandler());//往pipeLine链中添加自定义业务处理handler
                System.out.println("服务器端准备完毕");
            }
        });

        //启动服务器并绑定端口
        ChannelFuture future = serverBootstrap.bind(9999).sync();
        System.out.println("服务器已经启动了");

        //关闭通道以及线程池
        future.channel().closeFuture().sync();//异步
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();


    }
}
