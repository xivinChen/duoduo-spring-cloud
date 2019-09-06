package cn.duoduo;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableTransactionManagerServer
public class TxApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(TxApp.class,args);
    }
}
