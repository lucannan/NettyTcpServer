package cn.lcn.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import io.netty.util.internal.ConcurrentSet;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 聊天室
 * Created by LCN on 2016/5/19.
 */
public class ChattyRoom {
    private final static Map<ChannelHandlerContext, ChattyClient> connectedClients = new ConcurrentHashMap<ChannelHandlerContext, ChattyClient>();
    private final static Map<String, ChattyClient> nicknamedClients = new ConcurrentHashMap<String, ChattyClient>();
    private final static Set<String> nickNameSet = new ConcurrentSet<String>();

    private Timer ticker = new HashedWheelTimer();
    private TimerTask tickTask = new TimerTask() {
        @Override
        public void run(Timeout timeout) throws Exception {
            for (ChattyClient chattyClient : connectedClients.values()) {
                chattyClient.timer -= 1;
                if (chattyClient.timer <= 0) {
                    chattyClient.kickout();
                }
            }
            resetTicker();
        }
    };

    public ChattyRoom() {

        resetTicker();
    }


    public void resetTicker() {
//        System.out.println("tick");
        ticker.newTimeout(tickTask, 1, TimeUnit.SECONDS);
    }

    public ChattyClient makeClient(ChannelHandlerContext ctx) {
        if (connectedClients.get(ctx) != null) {
            return connectedClients.get(ctx);
        } else {
            ChattyClient chattyClient = ChattyClient.makeClient(ctx);
            connectedClients.put(ctx, chattyClient);
            return chattyClient;
        }
    }


    public void broadcastDisconnected(ChattyClient self) {
        broadcastMessage(self, "[" + self.nickname + " disconnected.]" + "\r\n");
    }


    public void broadcastConnected(ChattyClient self) {
        broadcastMessage(self, self.nickname + " connected.]\r\n");
    }

    private void broadcastChatContent(ChattyClient self, String message) {
        broadcastMessage(self, self.nickname + ":" + message + "\r\n");
    }

    public void innerBroadcast(ChattyClient self, String message) {
        for (ChattyClient chattyClient : connectedClients.values()) {
            if (chattyClient.status == ClientStatus.nicknamed && chattyClient != self) {
                chattyClient.sendMessage(message);
            }
        }
    }


    public void broadcastMessage(ChattyClient self, String message) {
        for (ChattyClient chattyClient : nicknamedClients.values()) {
            if (chattyClient != self) {
                chattyClient.sendMessage(message);
            }
        }
//        topic.publish(new TopicPair(self.nickname, message));
    }

    public void onConnected(ChattyClient client) {
        client.sendMessage("[Please input your nickname]\r\n");
    }

    public void onDisconnected(ChattyClient client) {
//        broadcastDisconnected(client);
        nickNameSet.remove(client.nickname);
        nicknamedClients.remove(client.nickname);
        connectedClients.remove(client.ctx);
    }

    public void onNicknameReceived(ChattyClient client, String nickname) throws UnknownHostException {
        if (client.status == ClientStatus.connected) {
            if (!nickNameSet.contains(nickname)) {
                client.nickname = nickname;
                client.status = ClientStatus.nicknamed;
                nickNameSet.add(nickname);
                nicknamedClients.put(nickname, client);
                client.sendMessage("[Welcome to " + InetAddress.getLocalHost().getHostName() + "!]\r\n");
                client.sendMessage(nickNameSet.size() + " online!]\r\n");
                client.sendMessage("[It is " + new Date(System.currentTimeMillis()) + " now.]\r\n");
//                broadcastConnected(client);
            } else {
                client.sendMessage("[Nickname " + nickname + " is used, please input another one ]\r\n");
            }
        }
    }

    public void onMessageReceived(ChattyClient client, String message) throws UnknownHostException {
        client.resetTimer();
        if (client.status == ClientStatus.connected) {
            onNicknameReceived(client, message);
        } else if (client.status == ClientStatus.nicknamed) {
//            broadcastChatContent(client, message);
            client.sendMessage(message);
        }
    }

    public void onAndroidMessageReceived(ChattyClient client, String serviceName, String serviceParam) throws ExecutionException {
        client.sendMessage(QueryRobot.query(serviceName, serviceParam));
    }
}



