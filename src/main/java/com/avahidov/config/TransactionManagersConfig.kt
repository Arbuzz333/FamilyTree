package com.avahidov.config;

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
class TransactionManagersConfig(
    val localContainerEntityManagerFactory: EntityManagerFactory,
    val dataSource: DataSource) {

    @Bean(name = ["transactionManager"])
    fun transactionManager(): PlatformTransactionManager {
        val tm = JpaTransactionManager()
        tm.entityManagerFactory = localContainerEntityManagerFactory
        tm.dataSource = dataSource
        return tm
    }
}
