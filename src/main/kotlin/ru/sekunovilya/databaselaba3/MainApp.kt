package ru.sekunovilya.databaselaba3

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import ru.sekunovilya.databaselaba3.benchmark.config.*
import ru.sekunovilya.databaselaba3.benchmark.config.loaders.impl.JsonConfigLoader
import ru.sekunovilya.databaselaba3.benchmark.testConnectors
import ru.sekunovilya.databaselaba3.charts.ResultExtension
import ru.sekunovilya.databaselaba3.charts.drawChart
import ru.sekunovilya.databaselaba3.config.CONFIG_FILE_PATH
import ru.sekunovilya.databaselaba3.config.RESULTS_PATH
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.exceptions.UnknownLibraryException
import java.io.File

@SpringBootApplication
//@MapperScan
class MainApp

fun main(args: Array<String>) {
    val context = runApplication<MainApp>(*args)
    val cfg = loadConfigFromJson(CONFIG_FILE_PATH)
    if (!isResultsDirectoryExists()) {
        createResultsDirectory()
    }
    val databaseConnectors = databaseConnectorsFromConfig(config = cfg, context = context)
    val results = testConnectors(databaseConnectors, cfg.testCount)
    drawChart(
        results = results,
        resultExtension = ResultExtension.XLSX
    )
}

fun isResultsDirectoryExists() = File(RESULTS_PATH).exists()

fun createResultsDirectory() = File(RESULTS_PATH).mkdir()

fun loadConfigFromJson(filePath: String) = JsonConfigLoader { ignoreUnknownKeys = true }.load(filePath)

fun databaseConnectorsFromConfig(config: Config, context: ApplicationContext) = config.libraries.map {
    when (it.name) {
        Jdbc.name -> context.getBean("jdbcConnector", DatabaseConnector::class.java)
        JdbcTemplate.name -> context.getBean("jdbcTemplateConnector", DatabaseConnector::class.java)
        Hibernate.name -> context.getBean("hibernateConnector", DatabaseConnector::class.java)
        SpringDataJpa.name -> context.getBean("springDataJpaConnector", DatabaseConnector::class.java)
        MyBatis.name -> context.getBean("myBatisConnector", DatabaseConnector::class.java)
        else -> throw UnknownLibraryException("Unrecognized library '${it.name}'")
    }
}