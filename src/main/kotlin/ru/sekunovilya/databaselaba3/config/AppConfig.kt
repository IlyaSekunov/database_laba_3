package ru.sekunovilya.databaselaba3.config

import java.io.File
import java.nio.file.Paths

private val FILE_PATH_SEPARATOR = File.separatorChar
private const val CONFIG_FILE_NAME = "config"
private val CURRENT_DIRECTORY = Paths.get("").toAbsolutePath()
val CONFIG_FILE_PATH = "$CURRENT_DIRECTORY$FILE_PATH_SEPARATOR$CONFIG_FILE_NAME"
val RESULTS_PATH = "$CURRENT_DIRECTORY${FILE_PATH_SEPARATOR}results$FILE_PATH_SEPARATOR"