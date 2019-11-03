package netty.rpc.serverstub;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

public class InvokeHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ClassInfo classInfo = (ClassInfo)msg;//把客户端发送的数据强转成ClassInfo
        String className = getImplClassName(classInfo);//得到接口的实现类的名字
        Class<?> clazz = Class.forName(className);//得到实现类的class对象
        Object instance = clazz.newInstance();//得到实现类的实例对象

        Method method = clazz.getMethod(classInfo.getMethodName(), classInfo.getTypes());//得到客户端要调用的方法
        //通过反射调用实现类的方法
        Object invoke = method.invoke(instance, classInfo.getObjects());
        //把调用方法的结果刷新到远程的客户端节点
        ctx.writeAndFlush(invoke);
    }



    private  String getImplClassName(ClassInfo classInfo) throws  Exception{
        String interfacePath = "netty.rpc.server";
        int lastDot = classInfo.getClassName().lastIndexOf(".");
        String interfaceName=classInfo.getClassName().substring(lastDot);
        Class superClass=Class.forName(interfacePath+interfaceName);
        Reflections reflections = new Reflections(interfacePath);
        //得到某接口下的所有实现类
        Set<Class> implClassSet = reflections.getSubTypesOf(superClass);
        if(implClassSet.size()==0){//说明没有实现类
            System.out.println("未找到实现类");
            return null;
        }else if(implClassSet.size()>1){
            System.out.println("找了多个实现类，未明确使用哪一个");
            return null;
        }else{
            //把实现类的集合转换成数组
            Class[] classes = implClassSet.toArray(new Class[0]);
            //返回实现类的名字
            return classes[0].getName();
        }
    }

}
