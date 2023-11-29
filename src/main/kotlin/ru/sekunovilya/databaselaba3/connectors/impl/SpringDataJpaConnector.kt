package ru.sekunovilya.databaselaba3.connectors.impl

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.benchmark.config.SpringDataJpa
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType
import ru.sekunovilya.databaselaba3.model.Table

@Repository
class SpringDataJpaConnector(val repository: SpringDataJpaRepository) : DatabaseConnector {
    override fun query(query: QueryType) =
        when (query) {
            QueryType.FIRST -> {
                repository.query1()
                Unit
            }
            QueryType.SECOND -> {
                repository.query2()
                Unit
            }
            QueryType.THIRD -> {
                repository.query3()
                Unit
            }
            QueryType.FOURTH -> {
                repository.query4()
                Unit
            }
        }

    override fun toLibrary() = SpringDataJpa
}

@Repository
interface SpringDataJpaRepository : JpaRepository<Table, Long> {
    @Query(
        """
        SELECT VendorID, count(*) 
        FROM taxi 
        GROUP BY 1
        """,
        nativeQuery = true
    )
    fun query1(): List<Any>

    @Query(
        """
        SELECT passenger_count, avg(total_amount) 
        FROM taxi 
        GROUP BY 1
        """,
        nativeQuery = true
    )
    fun query2(): List<Any>

    @Query(
        """
        SELECT passenger_count, extract(year from tpep_pickup_datetime), count(*) 
        FROM taxi 
        GROUP BY 1, 2
        """,
        nativeQuery = true
    )
    fun query3(): List<Any>

    @Query(
        """
        SELECT passenger_count, extract(year from tpep_pickup_datetime), round(trip_distance), count(*) 
        FROM taxi
        GROUP BY 1, 2, 3 
        ORDER BY 2, 4 desc
        """,
        nativeQuery = true
    )
    fun query4(): List<Any>
}