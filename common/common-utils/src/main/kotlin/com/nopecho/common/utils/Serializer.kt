package com.nopecho.common.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

object Serializer {
    val mapper = ObjectMapper().apply {
        findAndRegisterModules()
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
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