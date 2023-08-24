package com.tmax.commerce.itemmodule.config;

import com.tmax.commerce.itemmodule.repository.ExtendedRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.tmax.commerce.itemmodule.repository", repositoryBaseClass = ExtendedRepositoryImpl.class)
public class JpaAuditingConfig {
}

