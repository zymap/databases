package com.yjg.util.MyRedis;


import com.yjg.config.Config;
import com.yjg.util.handle.Interf;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by evan on 17-8-18.
 */
public class Redis {
    private static Logger logger = Logger.getLogger(Redis.class);
    static {
        PropertyConfigurator.configure("log/log4j.properties");
    }

    private static ArrayBlockingQueue<Jedis> abq = new ArrayBlockingQueue<Jedis>(Config.szie);




    public static void createRedis(){
        for (int i = 0; i <10; i++){
            try {
                abq.put(new Jedis(Config.REDIS_IP,Config.PORT));
            } catch (InterruptedException e) {
                e.printStackTrace();
                logger.debug(abq,e);
            }
        }
    }

    public static Jedis getRedis()  {
        Jedis jedis = null;
        try {
            jedis =  abq.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isAlive(jedis);
    }

    private static Jedis isAlive(Jedis jedis){
        while (true){
            try {
                if (jedis.ping().equals("PONG")){
                    return jedis;
                }
            }catch (Exception e) {
                stop();
                continue;
            }
        }
    }
    private static void stop(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static void returnRedis(Jedis jedis) {
        try {
            abq.put(jedis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void setList(String key, String v) throws InterruptedException {
        logger.debug("key:"+key+"\tvalue"+v);
        Jedis jedis = getRedis();
        jedis.lpush(key,v);
        returnRedis(jedis);
    }


    public int redis() throws Exception {
        Jedis jedis = new Jedis();
        Interf interf =new Interf();
        ArrayList<String> list = new ArrayList<String>();


        return 0;
    }


    public void subscribeMulty(HashMap<String,List<Multy>> map){
        ExecutorService service = Executors.newCachedThreadPool();
        for (String s : map.keySet()){
            List<Multy> list = map.get(s);
            for (Multy m:list){
                RedisThread redisThread = new RedisThread();
                redisThread.setName(m.getChannel());
                redisThread.setSub(m.getSub());
                service.execute(redisThread);
            }
        }
    }

    public static class RedisThread implements Runnable {
        private String name;
        private JedisPubSub sub;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public JedisPubSub getSub() {
            return sub;
        }

        public void setSub(JedisPubSub sub) {
            this.sub = sub;
        }

        @Override
        public void run() {

            Jedis jedis = Redis.getRedis();
            System.out.println("sub .......");
            jedis.subscribe(sub,name);

        }
    }



}


