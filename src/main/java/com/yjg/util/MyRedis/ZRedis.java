package com.yjg.util.MyRedis;

import com.yjg.config.Config;
import com.yjg.util.Redis.HeartBeat;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;


/**
 * Created by evan on 17-8-22.
 */
public class ZRedis {
    private Logger logger = Logger.getLogger(ZRedis.class);
    private static String MESSAGE = "MESSAGE|";

    public void zsubscribe(final JedisPubSub sub, final String channel){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = new Jedis(Config.REDIS_IP,Config.PORT);
                try {
                    jedis.subscribe(sub,channel);
                }catch (Exception e){
                    jedis.disconnect();
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                HeartBeat heartBeat = new HeartBeat();
                try {
                    heartBeat.Heartbeat(channel,sub);
                } catch (InterruptedException e) {
                    logger.error("heartbeat is broken.....");
                }
            }
        }).start();
    }


    public static void publish(String channel, String message){
        Jedis jedis = null;
        try {
            jedis = Redis.getRedis();
            jedis.publish(channel,message);
        } finally {
            Redis.returnRedis(jedis);
        }
    }


}
