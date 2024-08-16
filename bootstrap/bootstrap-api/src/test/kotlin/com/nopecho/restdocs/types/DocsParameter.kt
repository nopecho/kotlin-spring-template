package com.nopecho.restdocs.types

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

open class DocsParameter(
    private val descriptor: ParameterDescriptor,
) {

    open infix fun mean(value: String): DocsParameter {
        descriptor.description(value)
        return this
    }

    open infix fun optional(value: Boolean): DocsParameter {
        if (value) descriptor.optional()
        return this
    }

    open infix fun ignore(value: Boolean): DocsParameter {
        if (value) descriptor.ignored()
        return this
    }

    fun toDescriptor(): ParameterDescriptor {
        return descriptor
    }
}

infix fun String.paramIs(mean: String): DocsParameter {
    val descriptor = parameterWithName(this)
    return DocsParameter(descriptor)
        .mean(mean)
}

infix fun <T : Enum<T>> String.paramIs(enumFieldType: ENUM<T>): DocsParameter {
    val descriptor = parameterWithName(this)
    return DocsParameter(descriptor)
        .mean(enumFieldType.enums.joinToString(" | "))
}