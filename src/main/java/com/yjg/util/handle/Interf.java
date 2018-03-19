package com.yjg.util.handle;


import com.sun.org.apache.regexp.internal.RE;
import com.yjg.config.Config;
import com.yjg.util.MyRedis.Redis;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;


public class Interf {
    private ArrayBlockingQueue<String> abq;

    private static Logger logger = Logger.getLogger(Interf.class);


    private void setRedis() {
        JDBCOperation js = null;
        try {
            js = new JDBCOperation();
            HashMap<String, TreeMap<String, String>> hashMap = js.getAllResult();
            abq = new ArrayBlockingQueue<String>(Config.building);
            for (Object o : hashMap.keySet()) {
                String s = (String) o;
                TreeMap<String, String> tree = hashMap.get(s);
                for (Object obj : tree.keySet()) {
                    System.out.println(obj+tree.get(obj));
                    Redis.setList(s, obj+tree.get(obj));
                }
                abq.put(s);
            }
        } catch (Exception e) {
            logger.debug(e);
        }
    }

    public String getSendMessage(){
        setRedis();
        String message  = "";
        for (String s:abq){
            message += s;
        }
        return message;
    }

    public static void main(String[] args) {
        Redis.createRedis();
        System.out.println(new Interf().getSendMessage());
    }






}
