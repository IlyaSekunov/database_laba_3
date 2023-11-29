package ru.sekunovilya.databaselaba3.connectors.impl

import jakarta.persistence.EntityManagerFactory
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.benchmark.config.Hibernate
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType

@Repository
class HibernateConnector(private val entityManagerFactory: EntityManagerFactory) : DatabaseConnector {
    override fun query(query: QueryType) {
        val sessionFactory = entityManagerFactory.unwrap(SessionFactory::class.java)
        when (query) {
            QueryType.FIRST -> {
                val sql = "SELECT VendorID, count(*) FROM taxi GROUP BY 1"
                sessionFactory.inSession {
                    it.createNativeQuery(sql, Any::class.java).resultList
                }
            }
            QueryType.SECOND -> {
                val sql = "SELECT passenger_count, avg(total_amount) FROM taxi GROUP BY 1"
                sessionFactory.inSession {
                    it.createNativeQuery(sql, Any::class.java).resultList
                }
            }
            QueryType.THIRD -> {
                val sql =
                    "SELECT passenger_count, extract(year from tpep_pickup_datetime), count(*) FROM taxi GROUP BY 1, 2"
                sessionFactory.inSession {
                    it.createNativeQuery(sql, Any::class.java).resultList
                }
            }
            QueryType.FOURTH -> {
                val sql =
                    "SELECT passenger_count, extract(year from tpep_pickup_datetime), round(trip_distance), count(*) FROM taxi GROUP BY 1, 2, 3 ORDER BY 2, 4 desc"
                sessionFactory.inSession {
                    it.createNativeQuery(sql, Any::class.java).resultList
                }
            }
        }
    }

    override fun toLibrary() = Hibernate
}