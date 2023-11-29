package ru.sekunovilya.databaselaba3.benchmark.config.loaders.impl

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.decodeFromStream
import ru.sekunovilya.databaselaba3.benchmark.config.Config
import ru.sekunovilya.databaselaba3.benchmark.config.JsonConfig
import ru.sekunovilya.databaselaba3.benchmark.config.knownLibraries
import ru.sekunovilya.databaselaba3.benchmark.config.loaders.ConfigLoader
import ru.sekunovilya.databaselaba3.config.CONFIG_FILE_PATH
import ru.sekunovilya.databaselaba3.exceptions.UnknownLibraryException
import java.io.FileInputStream

fun JsonConfigLoader(builder: JsonBuilder.() -> Unit): JsonConfigLoader = JsonConfigLoader(Json { builder() })

class JsonConfigLoader(private val jsonConverter: Json) : ConfigLoader {
    @OptIn(ExperimentalSerializationApi::class)
    override fun load(path: String): Config {
        val filePath = "$CONFIG_FILE_PATH.json"
        val cfg = jsonConverter.decodeFromStream<JsonConfig>(FileInputStream(filePath))
        cfg.libraries.forEach {
            if (it !in knownLibraries) {
                throw UnknownLibraryException("Unrecognized library '${it.name}'")
            }
        }
        return cfg
    }
}