package cn.duoduo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("cn.duoduo.mapper")
@EnableDiscoveryClient
@EnableCaching
public class Service2App
{
    public static void main( String[] args )
    {
        SpringApplication.run(Service2App.class,args);
    }
}
