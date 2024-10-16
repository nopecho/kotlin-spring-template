package com.nopecho.infra.jdbc.fixture

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName.parse
import javax.sql.DataSource

@Import(PostgresTestJdbcSupport.Companion.DataSourceConfig::class)
abstract class PostgresTestJdbcSupport {

    companion object {
        private const val POSTGRES_IMAGE = "postgres:16-alpine"
        private const val POSTGRES_INIT_SCRIPT = "init.sql" // resources path

        @Container
        val postgresContainer = PostgreSQLContainer<Nothing>(parse(POSTGRES_IMAGE)).apply {
            withDatabaseName("test")
            withUsername("test")
            withPassword("test")
            withInitScript(POSTGRES_INIT_SCRIPT)
            start()
        }

        @Configuration
        class DataSourceConfig {
            @Bean
            @Primary
            fun dataSource(): DataSource {
                return HikariDataSource().apply {
                    driverClassName = postgresContainer.driverClassName
                    jdbcUrl = postgresContainer.jdbcUrl
                    username = postgresContainer.username
                    password = postgresContainer.password
                }
            }
        }
    }
}