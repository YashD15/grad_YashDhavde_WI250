package com.example.studentsdbapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.studentsdbapp.entities.Student;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(basePackages="com.example.studentsdbapp.repository.h2", entityManagerFactoryRef="h2EntityManager", transactionManagerRef="h2TransactionManager")
public class H2Config {
    
    @Primary
    @Bean
    @ConfigurationProperties(prefix="spring.datasource.h2")
    public DataSource h2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean h2EntityManager(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(h2DataSource()).packages(Student.class).persistenceUnit("h2").build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager h2TransactionManager(@Qualifier("h2EntityManager") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
