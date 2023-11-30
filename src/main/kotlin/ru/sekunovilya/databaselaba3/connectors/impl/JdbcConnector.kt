package ru.sekunovilya.databaselaba3.connectors.impl

import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.benchmark.config.Jdbc
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType
import ru.sekunovilya.databaselaba3.model.*
import java.sql.Connection

@Repository
class JdbcConnector(private val connection: Connection) : DatabaseConnector {
    override fun query(query: QueryType) =
        when (query) {
            QueryType.FIRST -> {
                val sql = "SELECT VendorID, count(*) FROM taxi GROUP BY 1"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                listFirstQueryFromResultSet(resultSet)
                statement.close()
            }

            QueryType.SECOND -> {
                val sql = "SELECT passenger_count, avg(total_amount) FROM taxi GROUP BY 1"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                listSecondQueryFromResultSet(resultSet)
                statement.close()
            }

            QueryType.THIRD -> {
                val sql =
                    "SELECT passenger_count, extract(year from tpep_pickup_datetime), count(*) FROM taxi GROUP BY 1, 2"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                listThirdQueryFromResultSet(resultSet)
                statement.close()
            }

            QueryType.FOURTH -> {
                val sql =
                    "SELECT passenger_count, extract(year from tpep_pickup_datetime), round(trip_distance), count(*) FROM taxi GROUP BY 1, 2, 3 ORDER BY 2, 4 desc"
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(sql)
                listFourthQueryFromResultSet(resultSet)
                statement.close()
            }
        }

    override fun toLibrary() = Jdbc
}