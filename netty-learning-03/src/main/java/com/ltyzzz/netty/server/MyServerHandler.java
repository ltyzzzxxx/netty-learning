package com.ltyzzz.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ltyzzz
 * @email ltyzzz2000@gmail.com
 * @date 2023/5/28 14:24
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        System.out.println("连接报告信息：有一客户端链接到本服务端");
        System.out.println("连接报告IP：" + socketChannel.localAddress().getHostString());
        System.out.println("连接报告Port：" + socketChannel.localAddress().getPort());
        System.out.println("连接报告Done!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 接收到消息：" + msg);
    }
}
