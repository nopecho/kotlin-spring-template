package com.nopecho.restdocs.types

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName

open class DocsHeader(
    private val descriptor: HeaderDescriptor,
) {

    open infix fun mean(value: String): DocsHeader {
        descriptor.description(value)
        return this
    }

    open infix fun optional(value: Boolean): DocsHeader {
        if (value) descriptor.optional()
        return this
    }

    fun toDescriptor(): HeaderDescriptor {
        return descriptor
    }
}

infix fun String.headerIs(desc: String): DocsHeader {
    val descriptor = headerWithName(this)
    return DocsHeader(descriptor)
        .mean(desc)
}

internal fun jwtAuthWith(vararg other: DocsHeader): Array<DocsHeader> {
    return arrayOf(
        "Authorization" headerIs "`Bearer {JWT}`",
        *other
    )
}