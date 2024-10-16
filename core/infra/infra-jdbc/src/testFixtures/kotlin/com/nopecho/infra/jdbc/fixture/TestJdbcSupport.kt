package com.nopecho.infra.jdbc.fixture

import com.nopecho.infra.jdbc.TestJdbcConfig
import com.nopecho.infra.jdbc.TestJdbcScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate

@Import(TestJdbcConfig::class)
@SpringJUnitConfig(TestJdbcScan::class)
abstract class TestJdbcSupport : PostgresTestJdbcSupport() {

    @Autowired
    private lateinit var template: TransactionTemplate

    @Autowired
    private lateinit var manager: PlatformTransactionManager

    protected fun <T> rollback(block: () -> T) {
        val transaction = manager.getTransaction(template)
        try {
            block()
        } finally {
            manager.rollback(transaction)
        }
    }

    protected fun <T> transaction(block: () -> T) {
        val transaction = manager.getTransaction(template)
        try {
            block()
            manager.commit(transaction)
        } catch (e: Exception) {
            manager.rollback(transaction)
            println("tx error: ${e.message}")
        }
    }
}