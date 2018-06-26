package com.netty.config;
import com.netty.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerBootstrapConfig {

    @Autowired
    ServerHandler serverHandler;

    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap() {
        ServerBootstrap b = new ServerBootstrap();
        b.group(new NioEventLoopGroup(), new NioEventLoopGroup());
        b.channel(NioServerSocketChannel.class);
//        b.option(ChannelOption.SO_BACKLOG, 128);
//        b.option(ChannelOption.TCP_NODELAY, true);
//        b.childOption(ChannelOption.SO_KEEPALIVE, true);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new StringDecoder());//解码方式
                socketChannel.pipeline().addLast(new StringEncoder());//编码方式
                socketChannel.pipeline().addLast(serverHandler);//监听
            }
        });
        return b;
    }

}