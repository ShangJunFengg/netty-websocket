package com;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebsocketApplicationTests {

    @Test
    public void contextLoads() {

        int port=6666;//端口
        Bootstrap bootstrap = new Bootstrap();//客户端入口
        bootstrap.group(new NioEventLoopGroup());//开个线程
        bootstrap.channel(NioSocketChannel.class);//Channel的类型
        bootstrap.remoteAddress("127.0.0.1",port);//往哪连
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new StringDecoder());//解码方式
                socketChannel.pipeline().addLast(new StringEncoder());//编码方式
                socketChannel.pipeline().addLast(new MyClientHandler());//监听
            }
        });

        ChannelFuture future = bootstrap.connect();//开始连接
        SocketChannel channel = (SocketChannel)future.channel();//拿到channel

        //疯狂发消息
        while (true)
        {
            try {
                TimeUnit.SECONDS.sleep(3);
                channel.writeAndFlush("来自客户端的消息");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    }

class MyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("有新消息了====,内容是:"+msg);
    }
}