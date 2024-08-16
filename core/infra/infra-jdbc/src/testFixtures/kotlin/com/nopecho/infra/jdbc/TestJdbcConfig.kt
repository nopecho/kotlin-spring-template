package com.nopecho.infra.jdbc

import com.infobip.spring.data.jdbc.EnableQuerydslJdbcRepositories
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import javax.sql.DataSource

@EnableJdbcAuditing
@EnableQuerydslJdbcRepositories
@Configuration
class TestJdbcConfig : AbstractJdbcConfiguration() {

    @Bean
    fun namedParameterJdbcOperations(source: DataSource): NamedParameterJdbcOperations {
        return NamedParameterJdbcTemplate(source)
    }

    @Bean
    fun transactionManager(source: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(source)
    }

    @Bean
    fun transactionTemplate(manager: PlatformTransactionManager): TransactionTemplate {
        return TransactionTemplate(manager)
    }

    @Bean
    override fun jdbcCustomConversions(): JdbcCustomConversions {
        val converters = listOf<Converter<*, *>>()
        return JdbcCustomConversions(converters)
    }
}