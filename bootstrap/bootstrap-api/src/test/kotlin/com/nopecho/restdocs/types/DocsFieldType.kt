package com.nopecho.restdocs.types

import org.springframework.restdocs.payload.JsonFieldType
import kotlin.reflect.KClass

sealed class DocsFieldType(
    val type: JsonFieldType
)

data object STRING : DocsFieldType(JsonFieldType.STRING)
data object NUMBER : DocsFieldType(JsonFieldType.NUMBER)
data object BOOLEAN : DocsFieldType(JsonFieldType.BOOLEAN)
data object DATETIME : DocsFieldType(JsonFieldType.STRING)
data object DATE : DocsFieldType(JsonFieldType.STRING)
data object OBJECT : DocsFieldType(JsonFieldType.OBJECT)
data object ARRAY : DocsFieldType(JsonFieldType.ARRAY)
data object NULL : DocsFieldType(JsonFieldType.NULL)
data class ENUM<T : Enum<T>>(val enums: Collection<T>) : DocsFieldType(JsonFieldType.STRING) {
    constructor(clazz: KClass<T>) : this(clazz.java.enumConstants.asList())
}