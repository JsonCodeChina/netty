package netty.rpc.clientstub;

import netty.rpc.server.HelloRPC;
import netty.rpc.server.HelloShen;

public class TestNettyRPC {

    public static void main(String[] args) {

        HelloShen helloShen = (HelloShen) NettyRPCProxy.create(HelloShen.class);
        System.out.println(helloShen.hello());

        HelloRPC helloRPC = (HelloRPC) NettyRPCProxy.create(HelloRPC.class);
        System.out.println(helloRPC.hello("郁思怡"));
    }
}
