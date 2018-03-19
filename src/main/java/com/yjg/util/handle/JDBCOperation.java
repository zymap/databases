package com.yjg.util.handle;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yjg.pojo.mybatis.Nyy_org_device;
import com.yjg.frame.mybatis.UseMybatis;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by evan on 17-7-16.
 */
public class JDBCOperation {
    private static Logger logger = Logger.getLogger(JDBCOperation.class);

    ComboPooledDataSource dataSource = new ComboPooledDataSource("postgre");


    public JDBCOperation(){
    }



    public HashMap<String,TreeMap<String,String>> getAllResult(){
        TreeMap<String,String> map=new TreeMap<String, String>();
        HashMap<String,TreeMap<String,String>> hashMap = new HashMap<String, TreeMap<String, String>>();
        List<Nyy_org_device> list = UseMybatis.getDeviceList();
        for (Nyy_org_device device : list){
            String borg = device.getBorg();
            String key = device.getBfloor()+"#"+device.getBgroup()+"#";
            String value = device.getAdn()+":"+device.getDuid();

            if (hashMap.containsKey(borg)){
                if(map.containsKey(key)) {
                    value=map.get(key)+";"+value;
                }
                map.put(key,value);
                hashMap.put(borg,map);
            }else {
                map.put(key,value);
                hashMap.put(borg,map);
            }
        }
        return hashMap;
    }



}

//{"tem":245,"wind":13,"duid":"b35001a2-d634-48d6-b574-581f6526c3ab","devicestatus":20,"status":10,"temset":200,"bdevice":"396a1321-c171-4bc4-af41-ca1b627db364","borg":"4dc259ff-8750-4532-9240-de0e63f06776","cdate":"2017-08-21","modeset":10,"ctime":"20:14:03","buildmessage":"buildmessage"}
