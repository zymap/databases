package com.yjg.util.handle;


import com.yjg.pojo.mybatis.Nyy_org_tem_data;

/**
 * Created by evan on 17-8-23.
 */
public class Json {
    public Nyy_org_tem_data Handler(String json){
        JsonTools jsontools = new JsonTools(json);
        Nyy_org_tem_data t = new Nyy_org_tem_data();
        t.setDuid(jsontools.getDuid());
        t.setCdate(jsontools.getCdate());
        t.setCtime(jsontools.getCtime());
        t.setStatus(jsontools.getStatus());
        t.setBorg(jsontools.getBorg());
        t.setBdevice(jsontools.getBdevice());
        t.setTem(jsontools.getTem());
        t.setModeset(jsontools.getModeset());
        t.setDevicestatus(jsontools.getDevicestatus());
        t.setWind(jsontools.getWind());
        t.setTemset(jsontools.getTemset());
        return t;
    }
}
