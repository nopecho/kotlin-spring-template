package com.nopecho.restdocs.types

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.snippet.Attributes

open class DocsField(
    private val descriptor: FieldDescriptor,
) {

    open infix fun format(value: String): DocsField {
        descriptor.attributes(Attributes.key("format").value(value))
        return this
    }

    open infix fun mean(value: String): DocsField {
        descriptor.description(value)
        return this
    }

    open infix fun optional(value: Boolean): DocsField {
        if (value) descriptor.optional()
        return this
    }

    open infix fun ignore(value: Boolean): DocsField {
        if (value) descriptor.ignored()
        return this
    }

    fun toDescriptor(): FieldDescriptor {
        return descriptor
    }
}

internal fun errorBody(): Array<DocsField> {
    return arrayOf(
        "code" type NUMBER mean "상태 코드",
        "message" type STRING mean "에러 메세지",
    )
}

infix fun String.type(type: DocsFieldType): DocsField {
    val descriptor = fieldWithPath(this)
        .type(type.type)
    return DocsField(descriptor)
}

infix fun <T : Enum<T>> String.type(enumFieldType: ENUM<T>): DocsField {
    val descriptor = fieldWithPath(this)
        .type(JsonFieldType.STRING)
    return DocsField(descriptor)
        .mean(enumFieldType.enums.joinToString(" | "))
}