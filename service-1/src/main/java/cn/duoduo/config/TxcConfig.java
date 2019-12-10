package cn.duoduo.config;

/*import com.taobao.txc.client.aop.TxcTransactionScaner;
import com.taobao.txc.datasource.cobar.TxcDataSource;*/
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

public class TxcConfig {
    /**//*@Bean(name = "primaryDataSource")
    @Qualifier("primaryDataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public com.taobao.txc.datasource.cobar.TxcDataSource primaryDataSource()
    {
        return new TxcDataSource();
    }


    @Bean(name = "primaryDataSource")
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("primaryDataSource") javax.sql.DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    //定义声明式事务，要想让事务annotation感知的话，要在这里定义一下
    @Bean(name = "txcScanner")
    @ConfigurationProperties(prefix="aluser")
    public TxcTransactionScaner txcTransactionScaner()
    {
        return  new TxcTransactionScaner("bs-ccr-transaction-dev.1755295415742693.HZ");
    }*/
}
