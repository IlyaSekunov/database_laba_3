package ru.sekunovilya.databaselaba3.benchmark.config.loaders.impl

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.JsonNames
import kotlinx.serialization.json.decodeFromStream
import ru.sekunovilya.databaselaba3.benchmark.config.*
import ru.sekunovilya.databaselaba3.benchmark.config.loaders.ConfigLoader
import ru.sekunovilya.databaselaba3.config.CONFIG_FILE_PATH
import ru.sekunovilya.databaselaba3.exceptions.UnknownLibraryException
import java.io.FileInputStream

@Serializable
data class JsonConfig @OptIn(ExperimentalSerializationApi::class) constructor(
    @JsonNames("tests-count") override val testsCount: Int,
    override val libraries: List<JsonLibrary>
) : Config

@Serializable
data class JsonLibrary(override val name: String) : Library

fun JsonConfigLoader(builder: JsonBuilder.() -> Unit): JsonConfigLoader = JsonConfigLoader(Json { builder() })

private fun libraryFromJsonLibrary(library: JsonLibrary) =
    when (library.name) {
        Jdbc.name -> Jdbc
        JdbcTemplate.name -> JdbcTemplate
        Hibernate.name -> Hibernate
        SpringDataJpa.name -> SpringDataJpa
        else -> throw UnknownLibraryException("Unrecognized library '${library.name}'")
    }

class JsonConfigLoader(private val jsonConverter: Json) : ConfigLoader {
    @OptIn(ExperimentalSerializationApi::class)
    override fun load(path: String): Config {
        val filePath = "$CONFIG_FILE_PATH.json"
        val cfg = jsonConverter.decodeFromStream<JsonConfig>(FileInputStream(filePath))
        return object : Config {
            override val testsCount = cfg.testsCount
            override val libraries = cfg.libraries.map { libraryFromJsonLibrary(it) }
        }
    }
}