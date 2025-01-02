package dev.rost.datasource;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(PostgreTestContainer.class)
@EnableJpaRepositories(basePackageClasses = DatasourceApplication.class, considerNestedRepositories = true)
public class AbstractPersistTest implements SQLStatementCountValidator {}
