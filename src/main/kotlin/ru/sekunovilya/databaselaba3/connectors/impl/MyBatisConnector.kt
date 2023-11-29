package ru.sekunovilya.databaselaba3.connectors.impl

import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.benchmark.config.MyBatis
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType

@Repository
class MyBatisConnector(val myBatis: MyBatisRepository) : DatabaseConnector {
    override fun query(query: QueryType) =
        when (query) {
            QueryType.FIRST -> myBatis.query1()
            QueryType.SECOND -> myBatis.query2()
            QueryType.THIRD -> myBatis.query3()
            QueryType.FOURTH -> myBatis.query4()
        }

    override fun toLibrary() = MyBatis
}

interface MyBatisRepository {
    @Select(
        """
        SELECT cab_type, count(*) 
        FROM trips 
        GROUP BY 1
        """
    )
    fun query1()

    @Select(
        """
        SELECT passenger_count, avg(total_amount) 
        FROM trips 
        GROUP BY 1
        """
    )
    fun query2()

    @Select(
        """
        SELECT passenger_count, extract(year from pickup_datetime), count(*) 
        FROM trips 
        GROUP BY 1, 2
        """
    )
    fun query3()

    @Select(
        """
        SELECT passenger_count, extract(year from pickup_datetime), round(trip_distance), count(*) 
        FROM trips 
        GROUP BY 1, 2, 3 
        ORDER BY 2, 4 desc
        """
    )
    fun query4()
}
