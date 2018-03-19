package com.yjg.util.MyRedis;

/**
 * Created by evan on 17-8-23.
 */
public class RedisMessage {
    public final static String HEARTBEAT = "HEARTBEAT";
    public final static String MESSAGE = "Json";
    public static String getHead(String str){
        String[] s = str.split("\\|");
        return s[0];
    }

    public static String getBody(String str){
        String[] s = str.split("\\|");
        return s[1];
    }
}
