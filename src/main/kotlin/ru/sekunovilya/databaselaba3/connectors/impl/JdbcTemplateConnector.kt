package ru.sekunovilya.databaselaba3.connectors.impl

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType

@Repository
class JdbcTemplateConnector(private val jdbcTemplate: JdbcTemplate) : DatabaseConnector {
    override fun query(query: QueryType) =
        when (query) {
            QueryType.FIRST -> {
                val sql = "SELECT cab_type, count(*) FROM trips GROUP BY 1"
                //jdbcTemplate.query(sql, )
            }

            QueryType.SECOND -> {
                val sql = "SELECT passenger_count, avg(total_amount) FROM trips GROUP BY 1"
                //jdbcTemplate.query(sql, )
            }

            QueryType.THIRD -> {
                val sql =
                    "SELECT passenger_count, extract(year from pickup_datetime), count(*) FROM trips GROUP BY 1, 2"
                //jdbcTemplate.query(sql, )
            }

            QueryType.FOURTH -> {
                val sql =
                    "SELECT passenger_count, extract(year from pickup_datetime), round(trip_distance), count(*) FROM trips GROUP BY 1, 2, 3 ORDER BY 2, 4 desc"
                //jdbcTemplate.query(sql, )
            }
        }

    override fun toLibrary() = ru.sekunovilya.databaselaba3.benchmark.config.JdbcTemplate
}
