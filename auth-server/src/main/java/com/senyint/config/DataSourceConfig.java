package com.senyint.config;

import java.sql.SQLException;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DataSourceConfig {

	@Bean
	public DruidDataSource ds() throws SQLException{
		
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl("jdbc:oracle:thin:@36.102.232.6:1521:orcl");
		ds.setInitialSize(20);
		ds.setMaxActive(100);
		ds.setRemoveAbandoned(true);
		ds.setRemoveAbandonedTimeout(10);
		ds.setUsername("nmis");
		ds.setPassword("nmis");
		ds.setFilters("stat");
		return ds;
	}
}
