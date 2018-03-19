package com.yjg.util.handle;


import com.yjg.config.Config;
import com.yjg.frame.mybatis.UseMybatis;
import com.yjg.pojo.mybatis.Nyy_org_tem_data;
import com.yjg.util.MyRedis.RedisMessage;
import com.yjg.util.Redis.HeartBeat;
import org.apache.log4j.Logger;
import redis.clients.jedis.JedisPubSub;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by evan on 17-8-21.
 */
public class Message extends JedisPubSub{;
    private static Logger logger = Logger.getLogger(Message.class);
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void onMessage(final String channel, final String message) {
        System.out.println(channel+"\n"
                +message);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    switch (RedisMessage.getHead(message)){
                        case "HEARTBEAT":
                            HeartBeat.handleHeartbeat(message);
                            break;
                        case Config.type:
                            String temp = RedisMessage.getBody(message);
                            String[] mes = temp.split("\\|");
                            String classname = Function.getFunction(RedisMessage.getHead(message));
                            Class c = Class.forName(classname);
                            Json json = (Json)c.newInstance();
                            Method method = c.getMethod("Handler",String.class);
                           Nyy_org_tem_data t = (Nyy_org_tem_data) method.invoke(json,RedisMessage.getBody(message));
                           logger.debug(t.getTem()+t.getWind()+t.getDuid()+t.getStatus()+t.getTemset()+t.getBdevice()+t.getBorg()+t.getCdate()+t.getModeset()+t.getCtime());
//                          //  jdbCdata.insert(t);
 //                          test.addUser(t);

                            UseMybatis.insertIntoTem(t);
                            break;
                    }

                } catch (Exception e) {
                    logger.error("channel:"+channel+"\nmessage:"+message+"\nerror");
                }
            }
        });

    }
}
//Json|{"tem":245,"wind":"13","duid":"2ab0b9d6-323c-47a2-b665-90c2142163d1","dev
//icestatus":20,"status":20,"temset":220,"bdeviced":"aece590d-72a4-4322-8671-faf4a2
//        b63e22","borg":"AAAA","cdate":"2017-08-16","mode
//        set":10,"ctime":"09:41:56","buildmessage":"buildmessage","adn":"01","bfloor":"01"}