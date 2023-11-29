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
            QueryType.FIRST -> repository.query1()
            QueryType.SECOND -> repository.query2()
            QueryType.THIRD -> repository.query3()
            QueryType.FOURTH -> repository.query4()
        }

    override fun toLibrary() = SpringDataJpa
}

@Repository
interface SpringDataJpaRepository : JpaRepository<Long, Table> {
    @Query(
        """
        SELECT cab_type, count(*) 
        FROM trips 
        GROUP BY 1
        """
    )
    fun query1()

    @Query(
        """
        SELECT passenger_count, avg(total_amount) 
        FROM trips 
        GROUP BY 1
        """
    )
    fun query2()

    @Query(
        """
        SELECT passenger_count, extract(year from pickup_datetime), count(*) 
        FROM trips 
        GROUP BY 1, 2
        """
    )
    fun query3()

    @Query(
        """
        SELECT passenger_count, extract(year from pickup_datetime), round(trip_distance), count(*) 
        FROM trips 
        GROUP BY 1, 2, 3 
        ORDER BY 2, 4 desc
        """
    )
    fun query4()
}