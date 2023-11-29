package ru.sekunovilya.databaselaba3.benchmark.config

interface Config {
    val testsCount: Int
    val libraries: List<Library>
}

interface Library {
    val name: String
}

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