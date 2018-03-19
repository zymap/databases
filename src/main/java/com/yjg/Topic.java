package com.yjg;

import com.yjg.config.Config;
import com.yjg.frame.netty.HttpClient;
import com.yjg.util.MyRedis.Redis;
import com.yjg.util.MyRedis.ZRedis;
import com.yjg.util.handle.Function;

import com.yjg.util.handle.Interf;
import com.yjg.util.handle.Message;
import redis.clients.jedis.Jedis;

/**
 * Created by evan on 17-9-3.
 */
public class Topic {
    public static void main(String[] args) {
        Function.addFunction(Config.type,Config.func);
        Redis.createRedis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = new Jedis(Config.REDIS_IP,Config.PORT);
                Message jedisPubSub = new Message();
                ZRedis redis1 = new ZRedis();
                redis1.zsubscribe(jedisPubSub,Config.Queue);
            }

        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new HttpClient();
                client.setIp(Config.Client_IP);
                client.setPort(Config.client_PORT);
     //           client.setMessage("AAAA");
               client.setMessage(new Interf().getSendMessage());
                client.sendRedisAlready();
            }
        }).start();
    }
}
