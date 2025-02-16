package com.nopecho.support.core

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

object Serializer {
    val mapper = ObjectMapper().apply {
        findAndRegisterModules()
        enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    }

    fun serialize(obj: Any): String {
        return runCatching { mapper.writeValueAsString(obj) }
            .getOrElse {
                log().error("failure object serialize. obj: $obj, message: ${it.message}")
                throw IllegalArgumentException("failure object serialize")
            }
    }

    inline fun <reified T> deserialize(json: String): T {
        return runCatching { mapper.readValue(json, T::class.java) }
            .getOrElse {
                log().error("failure json deserialize. json: $json, message: ${it.message}")
                throw IllegalArgumentException("failure json deserialize")
            }
    }
}