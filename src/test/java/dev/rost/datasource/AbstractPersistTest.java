package dev.rost.datasource;

import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

import static dev.rost.datasource.AbstractPersistTest.QueryCountDataSourceConfiguration;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@ContextConfiguration(classes = QueryCountDataSourceConfiguration.class)
@EnableJpaRepositories(basePackageClasses = DatasourceApplication.class, considerNestedRepositories = true)
public class AbstractPersistTest {

    @Container
    static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }


    @TestConfiguration
    static class QueryCountDataSourceConfiguration {

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
