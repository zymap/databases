package com.yjg.util.handle;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by evan on 17-8-23.
 */
public class Function {
    private static ConcurrentHashMap<String,String> function = new ConcurrentHashMap<String, String>();

    public static void addFunction(String functionname,String usefunction){
        function.put(functionname,usefunction);
    }

    public static String getFunction(String functionname){
        return function.get(functionname);
    }

    public static void remove(String functionname){
        function.remove(functionname);
    }
}
