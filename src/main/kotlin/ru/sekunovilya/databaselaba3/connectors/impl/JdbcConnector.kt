package ru.sekunovilya.databaselaba3.connectors.impl

import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.benchmark.config.Jdbc
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType
import java.sql.Connection

@Repository
class JdbcConnector(private val connection: Connection) : DatabaseConnector {
    override fun query(query: QueryType) =
        when (query) {
            QueryType.FIRST -> {
                val sql = "SELECT cab_type, count(*) FROM trips GROUP BY 1"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                statement.close()
            }

            QueryType.SECOND -> {
                val sql = "SELECT passenger_count, avg(total_amount) FROM trips GROUP BY 1"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                statement.close()
            }

            QueryType.THIRD -> {
                val sql =
                    "SELECT passenger_count, extract(year from pickup_datetime), count(*) FROM trips GROUP BY 1, 2"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                statement.close()
            }

            QueryType.FOURTH -> {
                val sql =
                    "SELECT passenger_count, extract(year from pickup_datetime), round(trip_distance), count(*) FROM trips GROUP BY 1, 2, 3 ORDER BY 2, 4 desc"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                statement.close()
            }
        }

    override fun toLibrary() = Jdbc
}