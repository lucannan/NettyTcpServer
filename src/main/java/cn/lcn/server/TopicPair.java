package cn.lcn.server;

import java.io.Serializable;

/**
 * Created by LCN on 2016/5/19.
 */
public class TopicPair  implements Serializable{
    public String nickname;
    public String message;

    public TopicPair(String nickname, String message) {
        this.nickname = nickname;
        this.message = message;
    }

    public TopicPair() {
    }
}
