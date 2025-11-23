package com.github.allisson95.algashop.ordering.infrastructure.persistence.jpa;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = { "com.github.allisson95.algashop.ordering.infrastructure.persistence" },
        repositoryBaseClass = BaseJpaRepositoryImpl.class
)
public class JpaConfiguration {

}
