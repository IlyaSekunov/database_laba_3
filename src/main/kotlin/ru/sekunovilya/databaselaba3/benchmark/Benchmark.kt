package ru.sekunovilya.databaselaba3.benchmark

import ru.sekunovilya.databaselaba3.charts.ConnectorResults
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType
import kotlin.system.measureTimeMillis

fun testConnectors(connectors: List<DatabaseConnector>, testsCount: Int): List<ConnectorResults> {
    val results = ArrayList<ConnectorResults>(connectors.size)
    return results.apply {
        connectors.forEach { connector ->
            add(
                ConnectorResults(
                    library = connector.toLibrary(),
                    queryResults = produceQueryResults(testsCount, connector)
                )
            )
        }
    }
}

private fun produceQueryResults(testsCount: Int, connector: DatabaseConnector) = sequence {
    fun averageQueryResult(query: QueryType) = sequence {
        repeat(testsCount) {
            yield(value = testQuery(query, connector))
        }
    }.average()

    for (query in QueryType.entries) {
        val queryResult = averageQueryResult(query)
        yield(value = query to queryResult)
    }
}.toMap()

private fun testQuery(query: QueryType, connector: DatabaseConnector) = measureTimeMillis { connector.query(query) }