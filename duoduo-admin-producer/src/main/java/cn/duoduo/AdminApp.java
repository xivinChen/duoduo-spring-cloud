package cn.duoduo;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan("cn.duoduo.mapper")
@EnableDistributedTransaction
public class AdminApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AdminApp.class,args);
    }
}
