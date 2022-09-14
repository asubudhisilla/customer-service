package com.amar.customer.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public HikariDataSource dataSource(HikariConfig hikariConfig){
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public HikariConfig dsConfig(@Value("${app.datasource.db-name}") String jdbcUrl,
                                 @Value("${app.datasource.driverClassName}") String driverClassName,
                                 @Value("${app.datasource.username}") String userName,
                                 @Value("${app.datasource.password}") String password){
        var dataSourceConfig = new HikariConfig();
        dataSourceConfig.setJdbcUrl(jdbcUrl);
        dataSourceConfig.setDriverClassName(driverClassName);
        dataSourceConfig.setUsername(userName);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setMinimumIdle(5);
        dataSourceConfig.setMaximumPoolSize(10);
        dataSourceConfig.setIdleTimeout(30000);
        dataSourceConfig.setMaxLifetime(2000000);
        dataSourceConfig.setConnectionTimeout(30000);
        dataSourceConfig.setPoolName("HikariPoolCustomerService");
        return dataSourceConfig;
    }
}
