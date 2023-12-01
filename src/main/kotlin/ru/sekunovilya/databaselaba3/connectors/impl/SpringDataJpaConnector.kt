package ru.sekunovilya.databaselaba3.connectors.impl

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.benchmark.config.SpringDataJpa
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType

@Repository
class SpringDataJpaConnector(
    val entityManager: EntityManager
) : DatabaseConnector {
    override fun query(query: QueryType) =
        when (query) {
            QueryType.FIRST -> {
                entityManager.createNamedQuery("firstQuery").resultList
                Unit
            }
            QueryType.SECOND -> {
                entityManager.createNamedQuery("secondQuery").resultList
                Unit
            }
            QueryType.THIRD -> {
                entityManager.createNamedQuery("thirdQuery").resultList
                Unit
            }
            QueryType.FOURTH -> {
                entityManager.createNamedQuery("fourthQuery").resultList
                Unit
            }
        }

    override fun toLibrary() = SpringDataJpa
}