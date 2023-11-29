package ru.sekunovilya.databaselaba3

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import ru.sekunovilya.databaselaba3.benchmark.config.*
import ru.sekunovilya.databaselaba3.benchmark.config.loaders.impl.JsonConfigLoader
import ru.sekunovilya.databaselaba3.charts.ConnectorResults
import ru.sekunovilya.databaselaba3.charts.ResultExtension
import ru.sekunovilya.databaselaba3.charts.drawChart
import ru.sekunovilya.databaselaba3.config.CONFIG_FILE_PATH
import ru.sekunovilya.databaselaba3.config.RESULTS_PATH
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType
import ru.sekunovilya.databaselaba3.exceptions.UnknownLibraryException
import java.io.File

@SpringBootApplication
@MapperScan
class MainApp

fun main(args: Array<String>) {
    val context = runApplication<MainApp>(*args)
    val cfg = loadConfigFromJson(CONFIG_FILE_PATH)
    if (!isResultsDirectoryExists()) {
        createResultsDirectory()
    }
    // val databaseConnectors = databaseConnectorsFromConfig(config = cfg, context = context)
    val results = listOf(
        ConnectorResults(
            library = Jdbc,
            queryResults = mapOf(
                QueryType.FIRST to 800.0,
                QueryType.SECOND to 900.0,
                QueryType.THIRD to 1100.0,
                QueryType.FOURTH to 1500.0
            )
        ),
        ConnectorResults(
            library = JdbcTemplate,
            queryResults = mapOf(
                QueryType.FIRST to 1800.0,
                QueryType.SECOND to 1900.0,
                QueryType.THIRD to 2100.0,
                QueryType.FOURTH to 2500.0
            )
        ),
        ConnectorResults(
            library = Hibernate,
            queryResults = mapOf(
                QueryType.FIRST to 2800.0,
                QueryType.SECOND to 2900.0,
                QueryType.THIRD to 3100.0,
                QueryType.FOURTH to 3500.0
            )
        ),
        ConnectorResults(
            library = SpringDataJpa,
            queryResults = mapOf(
                QueryType.FIRST to 3800.0,
                QueryType.SECOND to 3900.0,
                QueryType.THIRD to 4100.0,
                QueryType.FOURTH to 4500.0
            )
        ),
        ConnectorResults(
            library = MyBatis,
            queryResults = mapOf(
                QueryType.FIRST to 4800.0,
                QueryType.SECOND to 4900.0,
                QueryType.THIRD to 5100.0,
                QueryType.FOURTH to 5500.0
            )
        )
    )
    drawChart(
        results = results,
        resultExtension = ResultExtension.XLSX
    )
}

fun isResultsDirectoryExists() = File(RESULTS_PATH).exists()

fun createResultsDirectory() = File(RESULTS_PATH).mkdir()

fun loadConfigFromJson(filePath: String) = JsonConfigLoader { ignoreUnknownKeys = true }.load(filePath)

fun databaseConnectorsFromConfig(config: Config, context: ApplicationContext) = config.libraries.map {
    when (it) {
        Jdbc -> context.getBean("jdbcConnector", DatabaseConnector::class.java)
        JdbcTemplate -> context.getBean("jdbcTemplateConnector", DatabaseConnector::class.java)
        Hibernate -> context.getBean("hibernate", DatabaseConnector::class.java)
        SpringDataJpa -> context.getBean("springDataJpaConnector", DatabaseConnector::class.java)
        else -> throw UnknownLibraryException("Unrecognized library '${it.name}'")
    }
}