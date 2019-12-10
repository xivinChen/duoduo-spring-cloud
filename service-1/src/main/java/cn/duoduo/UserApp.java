package cn.duoduo;


import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("cn.duoduo.mapper")
@EnableDiscoveryClient
@EnableFeignClients("cn.duoduo.service")
//@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "cn.duoduo")
public class UserApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(UserApp.class,args);
    }
}
