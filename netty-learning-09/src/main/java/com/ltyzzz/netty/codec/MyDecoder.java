package com.ltyzzz.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author ltyzzz
 * @email ltyzzz2000@gmail.com
 * @date 2023/5/28 18:31
 */
public class MyDecoder extends ByteToMessageDecoder {

    private final int BASE_LENGTH = 4;

    // 02 34 68 69 68 69 03
    // 02；开始位
    // 03；结束位
    // 34；变量，内容长度位

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < BASE_LENGTH) {
            return;
        }
        int beginIdx;
        // 循环找到数据包起始位置
        while (true) {
            // 包头开始的index
            beginIdx = in.readerIndex();
            // 标记当前index
            in.markReaderIndex();
            // 如果为开头，则已找到开始位置，结束循环
            if (in.readByte() == 0x02) {
                break;
            }
            // 未读到包头，则跳过该位置
            in.resetReaderIndex();
            in.readByte();
            // 不足4个字节时，结束方法，等待后续数据到达
            if (in.readableBytes() < BASE_LENGTH) {
                return;
            }
        }
        // 找到包头后，剩余可读长度
        int readableCount = in.readableBytes();
        if (readableCount <= 1) {
            // 回到包头位置
            in.readerIndex(beginIdx);
            return;
        }
        ByteBuf byteBuf = in.readBytes(1);
        // 1字节内容长度
        String msgLengthStr = byteBuf.toString(Charset.forName("GBK"));
        int msgLength = Integer.parseInt(msgLengthStr);

        readableCount = in.readableBytes();
        if (readableCount < msgLength + 1) {
            in.readerIndex(beginIdx);
            return;
        }
        ByteBuf msgContent = in.readBytes(msgLength);
        // 如果没有结尾标识，还原指针位置[其他标识结尾]
        byte end = in.readByte();
        if (end != 0x03) {
            in.readerIndex(beginIdx);
            return;
        }
        out.add(msgContent.toString(Charset.forName("GBK")));
    }
}
