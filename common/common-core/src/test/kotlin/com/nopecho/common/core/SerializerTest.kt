package com.nopecho.common.core

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class SerializerTest {

    @Test
    fun serialize() {
        val obj = mapOf("key" to "value")

        val actual = Serializer.serialize(obj)

        actual shouldBe "{\"key\":\"value\"}"
    }
}