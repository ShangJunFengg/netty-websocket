package com.netty.bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class NettyBootstrap {

    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap b;

    @Value("${netty.port}")
    private int port;

    @PostConstruct
    public void start() throws Exception {
        b.bind(port).addListener((ChannelFutureListener) f -> {
            if (f.isSuccess()) {
                System.out.println("server start  success---------------port:"+port);
            } else {
                System.out.println("server server  error---------等待重启 " );
            }
        });


    }

}
