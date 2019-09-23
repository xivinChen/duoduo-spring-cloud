package cn.duoduo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableCaching
//@EnableScheduling
public class RedisApp
{

    private static final Logger log =LoggerFactory.getLogger(RedisApp.class);

    public static void main( String[] args )
    {
        log.info("Redis App is Starting");
        SpringApplication.run(RedisApp.class, args);
        log.debug("this is log debug!~");
        log.error("error has open,this is log error!!!");
        log.info("Redis App has Started,it is running!!!");
    }
}
