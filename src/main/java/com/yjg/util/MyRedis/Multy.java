package com.yjg.util.MyRedis;

import redis.clients.jedis.JedisPubSub;

/**
 * Created by evan on 17-8-23.
 */
public class Multy {
    private JedisPubSub sub;
    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public JedisPubSub getSub() {

        return sub;
    }

    public void setSub(JedisPubSub sub) {
        this.sub = sub;
    }
}
