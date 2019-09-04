package cn.duoduo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("cn.duoduo.service")
public class ProductSearchApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ProductSearchApp.class,args);
    }
}
