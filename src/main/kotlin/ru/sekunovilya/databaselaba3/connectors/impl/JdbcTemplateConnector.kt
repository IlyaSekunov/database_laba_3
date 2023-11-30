package ru.sekunovilya.databaselaba3.connectors.impl

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType
import ru.sekunovilya.databaselaba3.model.*

@Repository
class JdbcTemplateConnector(private val jdbcTemplate: JdbcTemplate) : DatabaseConnector {
    override fun query(query: QueryType) =
        when (query) {
            QueryType.FIRST -> {
                val sql = "SELECT VendorID, count(*) FROM taxi GROUP BY 1"
                jdbcTemplate.query(sql) { rs, _ -> firstQueryFromResultSet(rs) }
                Unit
            }

            QueryType.SECOND -> {
                val sql = "SELECT passenger_count, avg(total_amount) FROM taxi GROUP BY 1"
                jdbcTemplate.query(sql) { rs, _ -> secondQueryFromResultSet(rs) }
                Unit
            }

            QueryType.THIRD -> {
                val sql =
                    "SELECT passenger_count, extract(year from tpep_pickup_datetime), count(*) FROM taxi GROUP BY 1, 2"
                jdbcTemplate.query(sql) { rs, _ -> thirdQueryFromResultSet(rs) }
                Unit
            }

            QueryType.FOURTH -> {
                val sql =
                    "SELECT passenger_count, extract(year from tpep_pickup_datetime), round(trip_distance), count(*) FROM taxi GROUP BY 1, 2, 3 ORDER BY 2, 4 desc"
                jdbcTemplate.query(sql) { rs, _ -> fourthQueryFromResultSet(rs) }
                Unit
            }
        }

    override fun toLibrary() = ru.sekunovilya.databaselaba3.benchmark.config.JdbcTemplate
}
