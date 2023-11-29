package ru.sekunovilya.databaselaba3.benchmark.config.loaders

import ru.sekunovilya.databaselaba3.benchmark.config.Config

interface ConfigLoader {
    fun load(path: String): Config
}