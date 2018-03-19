package com.yjg.frame.mybatis;

import com.yjg.pojo.mybatis.Nyy_org_device;
import com.yjg.pojo.mybatis.Nyy_org_tem_data;

import java.util.List;

/**
 * Created by evan on 17-9-3.
 */
public interface MybatisInterface {
    List<Nyy_org_device> getDevice();
    void insertData(Nyy_org_tem_data data);
}
