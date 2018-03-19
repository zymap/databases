package com.yjg.frame.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.apache.log4j.Logger;

import java.net.URI;

/**
 * Created by evan on 17-8-20.
 */
public class HttpClient {
    private String ip;
    private int port;
    private String message;

    private static Logger logger = Logger.getLogger(HttpClient.class);


    public HttpClient(String ip, int port, String message){
        this.ip = ip;
        this.port = port;
        this.message = message;
    }

    public HttpClient(){}


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void sendRedisAlready(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.TCP_NODELAY,true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    ch.pipeline().addLast(new HttpRequestEncoder());
                }
            });

            ChannelFuture f = b.connect(ip, port).sync();
            URI uri = new URI("/redis/key="+message);
            String msg = message;
            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString(), Unpooled.wrappedBuffer(msg.getBytes("UTF-8")));
            request.headers().set(HttpHeaderNames.HOST,ip);
            request.headers().setInt(HttpHeaderNames.CONTENT_LENGTH,request.content().readableBytes());
            // 发送http请求
            f.channel().write(request);
            f.channel().flush();
            f.channel().close();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("ip:"+ip+"port"+port+"message"+message,e);
        } finally {
            workerGroup.shutdownGracefully();
            logger.debug("ip:"+ip+"port"+port+"message"+message);
        }
    }

    public static void main(String[] args) {
        HttpClient client = new HttpClient("localhost",8888,"sdfsdf");
        client.sendRedisAlready();
    }

}
