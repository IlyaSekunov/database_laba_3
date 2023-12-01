package ru.sekunovilya.databaselaba3.connectors.impl

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.benchmark.config.MyBatis
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType
import ru.sekunovilya.databaselaba3.model.FirstQuery
import ru.sekunovilya.databaselaba3.model.FourthQuery
import ru.sekunovilya.databaselaba3.model.SecondQuery
import ru.sekunovilya.databaselaba3.model.ThirdQuery

@Repository
class MyBatisConnector(val myBatis: MyBatisRepository) : DatabaseConnector {
    override fun query(query: QueryType) =
        when (query) {
            QueryType.FIRST -> {
                myBatis.query1()
                Unit
            }
            QueryType.SECOND -> {
                myBatis.query2()
                Unit
            }
            QueryType.THIRD -> {
                myBatis.query3()
                Unit
            }
            QueryType.FOURTH -> {
                myBatis.query4()
                Unit
            }
        }

    override fun toLibrary() = MyBatis
}

@Mapper
interface MyBatisRepository {
    @Select(
        """
        SELECT VendorID, count(*) 
        FROM taxi 
        GROUP BY 1
        """
    )
    @Results(
        value = [
            Result(property = "vendorId", column = "VendorID"),
            Result(property = "count", column = "count")
        ]
    )
    fun query1(): List<FirstQuery>

    @Select(
        """
        SELECT passenger_count, avg(total_amount) 
        FROM taxi 
        GROUP BY 1
        """
    )
    @Results(
        value = [
            Result(property = "passengerCount", column = "passenger_count"),
            Result(property = "avg", column = "avg")
        ]
    )
    fun query2(): List<SecondQuery>

    @Select(
        """
        SELECT passenger_count, extract(year from tpep_pickup_datetime), count(*) 
        FROM taxi
        GROUP BY 1, 2
        """
    )
    @Results(
        value = [
            Result(property = "passengerCount", column = "passenger_count"),
            Result(property = "extract", column = "extract"),
            Result(property = "count", column = "count")
        ]
    )
    fun query3(): List<ThirdQuery>

    @Select(
        """
        SELECT passenger_count, extract(year from tpep_pickup_datetime), round(trip_distance), count(*) 
        FROM taxi
        GROUP BY 1, 2, 3 
        ORDER BY 2, 4 desc
        """
    )
    @Results(
        value = [
            Result(property = "passengerCount", column = "passenger_count"),
            Result(property = "extract", column = "extract"),
            Result(property = "round", column = "round"),
            Result(property = "count", column = "count")
        ]
    )
    fun query4(): List<FourthQuery>
}
