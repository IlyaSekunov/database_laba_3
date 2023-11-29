package ru.sekunovilya.databaselaba3.config

import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.springframework.beans.factory.BeanCreationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import java.sql.Connection
import java.sql.DriverManager
import javax.sql.DataSource

@Configuration
@PropertySource("classpath:application.properties")
class DatabaseConfig {
    @Bean
    fun connection(
        @Value("\${spring.datasource.url}") url: String,
        @Value("\${spring.datasource.username}") username: String,
        @Value("\${spring.datasource.password}") password: String
    ): Connection = DriverManager.getConnection(url, username, password)

    @Bean
    fun sqlSessionFactory(dataSource: DataSource): SqlSessionFactory {
        return SqlSessionFactoryBean().let {
            it.setDataSource(dataSource)
            it.`object` ?: throw BeanCreationException("Cannot create bean SqlSessionFactory")
        }
    }
}