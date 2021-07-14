package com.avahidov.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource


@Configuration
class EntityManagerFactoriesConfiguration(
    val dataSourceHikari: DataSource) {

    @Bean(name = ["entityManagerFactory"])
    fun localContainerEntityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSourceHikari
        em.setPackagesToScan("com.avahidov.entity")

        val vendorAdapter = HibernateJpaVendorAdapter()
        em.jpaVendorAdapter = vendorAdapter
        em.persistenceUnitName = "entityManager"
        em.setJpaProperties(getHibernateProperties())

        return em
    }

    private fun getHibernateProperties(): Properties {
        val properties = Properties()
        properties["hibernate.show_sql"] = "true"
        properties["hibernate.ddl-auto"] = "none"
        properties["hibernate.dialect"] = "org.hibernate.dialect.PostgreSQLDialect"
        return properties
    }
}