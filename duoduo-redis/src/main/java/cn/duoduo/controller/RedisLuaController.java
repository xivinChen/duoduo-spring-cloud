package cn.duoduo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/lua")
public class RedisLuaController {

    @Autowired
    RedisTemplate redisTemplate;

    @Resource
    DefaultRedisScript<Boolean> redisScript;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/")
    public Object lua() {
        List<String> keys = Arrays.asList("testLua", "hello lua");
        Boolean execute = stringRedisTemplate.execute(redisScript, keys, "100");
        assert execute !=null;
        return execute;
    }

    @GetMapping("/n")
    public Object getN() {
        List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        Boolean execute = stringRedisTemplate.execute(redisScript, list);
        assert execute!=null;
        return execute;
    }

    @GetMapping("/set/n")
    public Object setN() {
        List<String> list=new ArrayList<>();
        list.add("3");
        list.add("4");
        List<String> args=new ArrayList<>();
        args.add("3");
        args.add("4");
        Boolean execute = stringRedisTemplate.execute(redisScript, list);
        assert execute!=null;
        return execute;
    }

    @GetMapping("/add1")
    public Object addN() {
        List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        Boolean execute = stringRedisTemplate.execute(redisScript, list);
        assert execute!=null;
        return execute;
    }

    @GetMapping("/add2")
    public Object add2() {
        Set userComboTime = redisTemplate.opsForZSet().rangeByScore("userComboTime", 1, 100);
        Iterator iterator = userComboTime.iterator();
        List list=new ArrayList<>();
        while (iterator.hasNext()) {
            String next = String.valueOf(iterator.next());
            list.add(next);
        }



        Boolean execute = stringRedisTemplate.execute(redisScript, list,"-1");
        assert execute!=null;
        return execute;
    }



}
