package com.ltyzzz.netty.server;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author ltyzzz
 * @email ltyzzz2000@gmail.com
 * @date 2023/5/28 16:47
 */
public class ChannelHandler {

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
