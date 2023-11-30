package ru.sekunovilya.databaselaba3.connectors.impl

import jakarta.persistence.EntityManagerFactory
import org.hibernate.SessionFactory
import org.hibernate.query.Query
import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.benchmark.config.Hibernate
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType
import ru.sekunovilya.databaselaba3.model.FirstQuery
import ru.sekunovilya.databaselaba3.model.FourthQuery
import ru.sekunovilya.databaselaba3.model.SecondQuery
import ru.sekunovilya.databaselaba3.model.ThirdQuery
import java.math.BigDecimal

@Repository
class HibernateConnector(private val entityManagerFactory: EntityManagerFactory) : DatabaseConnector {
    override fun query(query: QueryType) {
        val sessionFactory = entityManagerFactory.unwrap(SessionFactory::class.java)
        when (query) {
            QueryType.FIRST -> {
                val sql = "SELECT VendorID, count(*) FROM taxi GROUP BY 1"
                sessionFactory.inSession {
                    it
                        .createNativeQuery(sql, Any::class.java)
                        .unwrap(Query::class.java)
                        .setTupleTransformer { tuple, _ ->
                            FirstQuery(
                                vendorId = tuple[0] as BigDecimal,
                                count = tuple[1] as Long
                            )
                        }
                        .resultList
                }
            }
            QueryType.SECOND -> {
                val sql = "SELECT passenger_count, avg(total_amount) FROM taxi GROUP BY 1"
                sessionFactory.inSession {
                    it
                        .createNativeQuery(sql, Any::class.java)
                        .unwrap(Query::class.java)
                        .setTupleTransformer { tuple, _ ->
                            SecondQuery(
                                passengerCount = tuple[0] as BigDecimal,
                                avg = tuple[1] as BigDecimal
                            )
                        }
                        .resultList
                }
            }
            QueryType.THIRD -> {
                val sql =
                    "SELECT passenger_count, extract(year from tpep_pickup_datetime), count(*) FROM taxi GROUP BY 1, 2"
                sessionFactory.inSession {
                    it
                        .createNativeQuery(sql, Any::class.java)
                        .unwrap(Query::class.java)
                        .setTupleTransformer { tuple, _ ->
                            ThirdQuery(
                                passengerCount = tuple[0] as BigDecimal,
                                extract = tuple[1] as BigDecimal,
                                count = tuple[2] as Long
                            )
                        }
                        .resultList
                }
            }
            QueryType.FOURTH -> {
                val sql =
                    "SELECT passenger_count, extract(year from tpep_pickup_datetime), round(trip_distance), count(*) FROM taxi GROUP BY 1, 2, 3 ORDER BY 2, 4 desc"
                sessionFactory.inSession {
                    it
                        .createNativeQuery(sql, Any::class.java)
                        .unwrap(Query::class.java)
                        .setTupleTransformer { tuple, _ ->
                            FourthQuery(
                                passengerCount = tuple[0] as BigDecimal,
                                extract = tuple[1] as BigDecimal,
                                round = tuple[2] as BigDecimal,
                                count = tuple[3] as Long
                            )
                        }
                        .resultList
                }
            }
        }
    }

    override fun toLibrary() = Hibernate
}