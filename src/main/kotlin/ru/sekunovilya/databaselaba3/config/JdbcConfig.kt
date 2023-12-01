package ru.sekunovilya.databaselaba3.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceUtils
import java.sql.Connection
import javax.sql.DataSource

@Configuration
class JdbcConfig {
    @Bean
    fun connection(dataSource: DataSource): Connection = DataSourceUtils.getConnection(dataSource)
}