package netty.rpc.server;

public class HelloShenImpl implements HelloShen {

    @Override
    public String hello() {
        return "你好 沈博！";
    }
}
