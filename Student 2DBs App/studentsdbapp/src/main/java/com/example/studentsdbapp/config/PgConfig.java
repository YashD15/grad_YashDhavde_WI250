package com.example.studentsdbapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.studentsdbapp.entities.Student;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackages="com.example.studentsdbapp.repository.pg", entityManagerFactoryRef="pgEntityManager", transactionManagerRef="pgTransactionManager")
public class PgConfig {
    
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.pg")
    public DataSource pgDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean pgEntityManager(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(pgDataSource()).packages(Student.class).persistenceUnit("pg").build();
    }

    @Bean
    public PlatformTransactionManager pgTransactionManager(@Qualifier("pgEntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
