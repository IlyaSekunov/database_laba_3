package ru.sekunovilya.databaselaba3.benchmark.config

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

sealed interface Config {
    val testCount: Int
    val libraries: List<Library>
}

sealed interface Library {
    val name: String
}

@Serializable
class JsonLibrary(override val name: String) : Library

@Serializable
class JsonConfig @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("tests-count") override val testCount: Int,
    override val libraries: List<JsonLibrary>
) : Config

data object Jdbc : Library {
    override val name: String = "jdbc"
}

data object JdbcTemplate : Library {
    override val name: String = "jdbc-template"
}
data object Hibernate : Library {
    override val name: String = "hibernate"
}

data object SpringDataJpa : Library {
    override val name: String = "spring-data-jpa"
}

data object MyBatis : Library {
    override val name: String = "my-batis"
}

val knownLibraries = listOf(Jdbc.name, JdbcTemplate.name, Hibernate.name, SpringDataJpa.name, MyBatis.name)