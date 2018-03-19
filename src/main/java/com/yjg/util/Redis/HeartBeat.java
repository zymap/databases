package com.yjg.util.Redis;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.yjg.config.Config;
import com.yjg.util.MyRedis.ZRedis;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.PropertyPermission;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by zy on 17-8-23.
 */
public class HeartBeat {
    private Logger heart = Logger.getLogger("thirdLogger");

    public static String HEARTBEAT = "";
    private final static String MESSAGE = "HEARTBEAT|HEARTBEAT";
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    private Jedis jedis;

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

//    public void countDown(){
//        latch.countDown();
//    }

    public void Heartbeat(final String channel, final JedisPubSub sub) throws InterruptedException {
        int time = 0;
        while (true){

            try {
                HEARTBEAT="";
                ZRedis.publish(channel, MESSAGE);
                lock.lock();
                condition.await(3000, TimeUnit.MILLISECONDS);
                lock.unlock();
                if (HEARTBEAT.equals("")){
                    if (sub != null) {
                        try {
                            sub.unsubscribe();
                        }catch (Exception e){
                            heart.debug("pre sub unsubscribe fail ....",e);
                        }
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Jedis jedis = new Jedis(Config.REDIS_IP,6379);
                            try {
                                heart.debug("restart already");
                                jedis.subscribe(sub,channel);
                            }catch (Exception e){
                                heart.error("restart is error");
                            }
                        }
                    }).start();
                }
                time=0;
            }catch (Exception e){
                heart.error("reconnect");
                Thread.sleep(500);
                continue;
            }
            Thread.sleep(Config.REDIS_SUB_HEARTBEAT_TIME);
        }
    }

    public static void handleHeartbeat(String message){
        try {
            HEARTBEAT = message;
            lock.lock();
            condition.signal();
        }finally {
            lock.unlock();
        }
    }
}
