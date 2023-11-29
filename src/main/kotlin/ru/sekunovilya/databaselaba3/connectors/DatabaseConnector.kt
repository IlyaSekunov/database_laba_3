package ru.sekunovilya.databaselaba3.connectors

import ru.sekunovilya.databaselaba3.benchmark.config.Library

interface DatabaseConnector {
    fun query(query: QueryType)
    fun toLibrary(): Library
}

enum class QueryType {
    FIRST, SECOND, THIRD, FOURTH
}