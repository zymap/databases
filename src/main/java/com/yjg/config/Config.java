package com.yjg.config;

import org.apache.log4j.PropertyConfigurator;

/**
 * Created by evan on 17-8-21.
 */
public class Config {
    public static String REDIS_IP = "127.0.0.1";
    public static String REDIS_ = "";

    public static int REDIS_SUB_HEARTBEAT_TIME = 10000;  //heartbeat

    public static int PORT = 6379;
    public static int client_PORT = 8888;
    public static String Client_IP = "127.0.0.1";
    public static int building=1;
    public static String Queue="data";
    public static int szie=10;
    public final static String type="Json";
    public static String func="com.yjg.util.handle.Json";

    static {PropertyConfigurator.configure("./log/log4j.properties");}

}
