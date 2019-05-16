/*
 * Copyright (C), 2015-2018
 * FileName: MessageDecoder
 * Author:   wanggang
 * Date:     2018/7/16 15:05
 * Description: 消息解码器
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.game.net.codec;

import com.game.domain.enums.CEnumValue;
import com.game.domain.exception.MessageCodecException;
import com.game.domain.utils.BytebufUtil;
import com.game.netBase.message.IMessage;
import com.game.netBase.message.impl.MessageFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 〈一句话功能简述〉<br>
 * 〈消息解码器〉
 *
 * @author wanggang
 * @date 2018/7/16 15:05
 * @since 1.0.1
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    //判断传送客户端传送过来的数据是否按照协议传输，头部信息的大小应该是 4字节总长度  | 8字节玩家id | 2字节命令名 | 包体 | 4+8+2=14
    private static final int HEADER_SIZE = 14;
    private String messageType;

    /**
     * @param maxFrameLength      解码时，处理每个帧数据的最大长度
     * @param lengthFieldOffset   该帧数据中，存放该帧数据的长度的数据的起始位置
     * @param lengthFieldLength   记录该帧数据长度的字段本身的长度
     * @param lengthAdjustment    修改帧数据长度字段中定义的值，可以为负数
     * @param initialBytesToStrip 解析的时候需要跳过的字节数
     * @param failFast            为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
     */
    private MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
                           int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
                          int initialBytesToStrip, boolean failFast, String messageType) {
        this(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
        this.messageType = messageType;
    }

    public IMessage decodePub(ChannelHandlerContext ctx, ByteBuf in) throws MessageCodecException {
        return decode(ctx, in);
    }

    @Override
    protected IMessage decode(ChannelHandlerContext ctx, ByteBuf in) throws MessageCodecException {
        if (in == null) {
            return null;
        }
        IMessage iMessage = MessageFactory.create(messageType);
        int l = in.readableBytes();
        System.out.println(l);
        if (l == 0) {
            return iMessage;
        }

        if (iMessage == null) {
            throw new MessageCodecException("MessageFactory 获取Message为null");
        }

        if (in.readableBytes() < HEADER_SIZE) {
            throw new MessageCodecException("可读信息段比头部信息都小");
        }

        //注意在读的过程中，readIndex的指针也在移动
        int length = in.readInt();

        if (in.readableBytes() < length - 4) {
            /* throw new MessageCodecException("body获取长度" + length + ",实际长度没有达到");*/
            in.skipBytes(in.readableBytes());
        } else {
            in.skipBytes(8);
            short messageId = in.readShort();

            ByteBuf buf = in.readBytes(length - 8 - 2 - 4);

            //    body = new String(bodyByte, ConstantValue.PROJECT_CHARSET);
            //    CustomMsg customMsg = new CustomMsg(type, flag, length, body);
            //    StringMessage stringMessage = new StringMessage(length, messageId, statusCode, body);
            iMessage.setMessageId(messageId);

            iMessage.setParam(BytebufUtil.byteBufToObject(buf, BytebufUtil.createObject(CEnumValue.getBeanByCode(messageId)), false, false));
//    iMessage.setLength(length);
        }
        return iMessage;
    }

}
