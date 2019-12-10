package cn.duoduo;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xivin
 * @ClassName cn.bosenkeji
 * @Version V1.0
 * @create 2019-07-15 18:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApp.class)
public class RedisTest {
//
    @Autowired
    private RedisTemplate redisTemplate;
//
//
//
//
//    @Test
//    public void testPackage() {
//        for (int i=0;i<100;i++){
//            redisTemplate.opsForValue().set("test_springboot_redis_"+i+1111+"_"+i+2222+"_"+i,"测试springboot2 集成 redis"+i);
//        }
//        Set<String> str =  redisTemplate.keys("test_springboot_redis*");
//        List<String> stringList =new ArrayList<>(str);
//        Map<String,Object> stringObjectMap = new HashMap<>();
//        if (!stringList.isEmpty()) {
//            for (String s : stringList) {
//                stringObjectMap.put(s,redisTemplate.opsForValue().get(s));
//                System.err.println("key = " + s+"|value="+stringObjectMap.get(s));
//            }
//        }
//
//        System.out.println(stringObjectMap.size());
//    }
//
//    @Test
//    public void testGetValue() {
//        String str = String.valueOf(redisTemplate.opsForValue().get("test_springboot_redis"));
//        System.out.println("str = " + str);
//    }
//
//
//
//    @Test
//    public void testExpire() {
//        Long time = redisTemplate.getExpire("test_springboot_redis");
//        System.err.println("time = " + time);
//    }

    /**
     *  判断键是否存在，及键的个数
     */
    @Test
    public void testGetZset() {
        Boolean userComboTime_01 = redisTemplate.hasKey("userComboTime_6");
        Long userComboTime_0 = redisTemplate.opsForZSet().size("userComboTime_6");
        System.out.println("userComboTime_0 = " + userComboTime_0+userComboTime_01);
    }

    @Test
    public void test2() {
        int key=0;
        while (true) {
            Long size = redisTemplate.opsForZSet().size("userComboTime_" + key);
            if(size==0) {

                System.out.println(key+"this is 00000");
                if(!redisTemplate.hasKey("userComboTime_"+key)) {
                    System.out.println("create schudelx");
                }
                redisTemplate.opsForZSet().add("userComboTime_"+key,String.valueOf(5+key),360);
                break;
            }
            else if(size<=1) {

                System.out.println(key+"this is <=5000");
                break;
            }
            else {
                key++;
            }
        }
    }

    @Test
    public void test02() {
        Double score = redisTemplate.opsForZSet().score("userComboTime_0", String.valueOf(2));

        System.out.println(score);
    }

    @Test
    public void testDeleteSeveralKeys() {
        Set keys = redisTemplate.keys("ccr1"+"*");
        Long delete = redisTemplate.delete(keys);
        System.out.println("int = " + delete);
    }


    @Test
    public void testSet() {
        long result=0;
        for (int i=0;i<10;i++) {
            result += redisTemplate.opsForSet().add(String.valueOf(i), "userComboTime_0");
        }
        System.out.println("result = " + result);
    }

    @Test
    public void testHash() {
        long result=0;
        for(int i=0;i<10;i++) {
            redisTemplate.opsForHash().put("comboLocal",String.valueOf(i),"userComboTime_0" );
        }
        Set comboLocal = redisTemplate.opsForHash().keys("comboLocal");
        System.out.println("comboLocal = " + comboLocal);
    }

    @Test
    public void test05() {
        Long xinbgeih = redisTemplate.opsForZSet().size("xinbgeih");
        System.out.println("xinbgeih = " + xinbgeih);
    }

    @Test
    public void testGetHash() {
        String comboRedisKey = (String) redisTemplate.opsForHash().get("comboRedisKeyTest", "1");
        System.out.println("comboRedisKey = " + comboRedisKey);
    }

    @Test
    public void testGetZsetObj(){
        Double score = redisTemplate.opsForZSet().score("userComboTime_0", "sgei");
        System.out.println("score = " + score);
    }

    @Test
    public void testDeleteSomeZset() {
        List list=new ArrayList();
        list.add("1");
        redisTemplate.delete(list);
    }

    @Test
    public void testRemoveZset() {
        List<String> list=new ArrayList<>();
        list.add("usercombo");
        Object[] str = list.toArray();
        Long userComboTime_0 = redisTemplate.opsForZSet().remove("userComboTime_0", str);
        System.out.println("userComboTime_0 = " + userComboTime_0);
        Long userComboRedisKeyTest = redisTemplate.opsForHash().delete("comboRedisKeyTest", str);
        System.out.println("ComboRedisKeyTest = " + userComboRedisKeyTest);
    }

    @Test
    public void testDeleteHash() {

        List<String> list=new ArrayList<>();
        list.add("1001");
        list.add("1002");
        final Object[] objects = list.toArray();
        Long comboRedisKeyTest = redisTemplate.opsForHash().delete("comboRedisKeyTest", objects);
        System.out.println("comboRedisKeyTest = " + comboRedisKeyTest);
    }

}
