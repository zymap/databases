package com.yjg.frame.mybatis;


import com.yjg.pojo.mybatis.Nyy_org_device;
import com.yjg.pojo.mybatis.Nyy_org_tem_data;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by evan on 17-9-3.
 */
public class UseMybatis {
    private static SqlSession sqlSession;

    private static void init(){
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            Iterator<String> iterator = sqlSessionFactory.getConfiguration().getParameterMapNames().iterator();
//            while(iterator.hasNext()){
//                String st = iterator.next();
//                System.out.println(st);
//            }



//            Collection<Object> values = sqlSessionFactory.getConfiguration().getVariables().values();
//            for(Object o: values){
//                System.out.println(o);
//            }


            SqlSession session = sqlSessionFactory.openSession();
            sqlSession = session;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void insertIntoTem(Nyy_org_tem_data data){
        try {
            init();
            MybatisInterface mybatisInterface = sqlSession.getMapper(MybatisInterface.class);
            mybatisInterface.insertData(data);
            sqlSession.commit();

        }finally {
            close();
        }
    }

    public static List<Nyy_org_device> getDeviceList(){
        List<Nyy_org_device> devices = null;
        init();
        MybatisInterface mybatisInterface = sqlSession.getMapper(MybatisInterface.class);
        devices = mybatisInterface.getDevice();
        close();
        return devices;
    }

    public static void close(){
        sqlSession.close();
    }


    public static void main(String[] args) {
        UseMybatis.init();
    }
}
