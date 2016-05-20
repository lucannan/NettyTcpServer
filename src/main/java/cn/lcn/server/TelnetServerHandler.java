package cn.lcn.server;

import io.netty.channel.*;

/**
 * Created by LCN on 2016/5/19.
 */
@ChannelHandler.Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {


    private static ChattyRoom chattyRoom = new ChattyRoom();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        chattyRoom.onConnected(chattyRoom.makeClient(ctx));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chattyRoom.onDisconnected(chattyRoom.makeClient(ctx));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {

        String response;

        if ("\\quit".equals(request.toLowerCase())) {
            response = "Have a good day!\r\n";
            ChannelFuture future = ctx.write(response);
            future.addListener(ChannelFutureListener.CLOSE);
        } else if (request.startsWith("\\")) {
            try {
                String[] command = request.split(" ");
                chattyRoom.onAndroidMessageReceived(chattyRoom.makeClient(ctx), command[0], command[1]);
            } catch (Exception e) {
                chattyRoom.onMessageReceived(chattyRoom.makeClient(ctx), "Bad Request!");
            }
        } else {
            chattyRoom.onMessageReceived(chattyRoom.makeClient(ctx), request);
        }

    }
}
