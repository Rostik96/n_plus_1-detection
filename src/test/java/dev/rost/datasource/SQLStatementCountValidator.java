package dev.rost.datasource;

import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;

import static dev.rost.datasource.SQLStatementCountValidator.QueryCountDataSourceConfiguration;
import static io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator.reset;

@ContextConfiguration(classes = QueryCountDataSourceConfiguration.class)
interface SQLStatementCountValidator {

    @BeforeEach
    default void beforeEach() {
        reset();
    }


    @TestConfiguration
    class QueryCountDataSourceConfiguration {

        @Bean
        BeanPostProcessor dataSourceBPP() {
            return new BeanPostProcessor() {
                @Override
                public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
                    if (beanName.equals("dataSource")) {
                        return ProxyDataSourceBuilder
                                .create((DataSource) bean)
                                .name("queryCountDataSource")
                                .listener(new DataSourceQueryCountListener())
                                .build();
                    }

                    return bean;
                }
            };
        }
    }
}
