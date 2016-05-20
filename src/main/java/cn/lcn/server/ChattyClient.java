package cn.lcn.server;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by LCN on 2016/5/19.
 */
public class ChattyClient {

    public ChannelHandlerContext ctx;    //连接上下文
    public Integer timer = 60;
    public String nickname = "";
    public ClientStatus status = ClientStatus.connected;//连接状态

    public ChattyClient(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void sendMessage(String message) {
        ctx.write(message);
        ctx.flush();
    }


    public void kickout() {

//        ctx.disconnect();
    }


    public void resetTimer() {
        timer = 60;
    }

    public static ChattyClient makeClient(ChannelHandlerContext ctx) {

        return new ChattyClient(ctx);
    }
}
