package ru.sekunovilya.databaselaba3.connectors.impl

import jakarta.persistence.EntityManagerFactory
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import ru.sekunovilya.databaselaba3.benchmark.config.Hibernate
import ru.sekunovilya.databaselaba3.connectors.DatabaseConnector
import ru.sekunovilya.databaselaba3.connectors.QueryType

@Repository
class HibernateConnector(private val entityManagerFactory: EntityManagerFactory) : DatabaseConnector {
    override fun query(query: QueryType) {
        val sessionFactory = entityManagerFactory.unwrap(SessionFactory::class.java)
        when (query) {
            QueryType.FIRST -> {

            }
            QueryType.SECOND -> {}
            QueryType.THIRD -> {}
            QueryType.FOURTH -> {}
        }
    }

    override fun toLibrary() = Hibernate
}