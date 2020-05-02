package com.example.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MetaSpaceOOM_Demo {

    private List<Integer> list;

    @Autowired
    private RedisConnectionFactory factory;

    @GetMapping("/hello")
    public String meatSpaceOOM() {
//        while(true){
//            final Integer i = new Integer(1);
//            list.add(i);
//            System.out.println("======");
//            /*try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }*/
//        }
        RedisClusterConnection conn = factory.getClusterConnection();
        conn.set("k1".getBytes(), "123".getBytes());
        return "成功！！";
    }
}
