package com.nopecho.infra.jdbc.fixture

import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(PostgresTestcontainers.Companion.TestJdbcConfig::class)
annotation class EnableTestJdbcConfig()
