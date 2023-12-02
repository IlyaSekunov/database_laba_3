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

private fun averageQueryResult(
    query: QueryType,
    testsCount: Int,
    connector: DatabaseConnector
) = sequence {
    repeat(testsCount) {
        yield(value = testQuery(query, connector))
    }
}.average()

private fun produceQueryResults(testsCount: Int, connector: DatabaseConnector): Map<QueryType, Double> {
    val result = HashMap<QueryType, Double>()
    return result.apply {
        for (query in QueryType.entries) {
            result[query] = averageQueryResult(query = query, testsCount = testsCount, connector = connector)
        }
    }
}

private fun testQuery(query: QueryType, connector: DatabaseConnector) = measureTimeMillis { connector.query(query) }