package com.nopecho.restdocs

import com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document
import com.nopecho.restdocs.types.DocsField
import com.nopecho.restdocs.types.DocsHeader
import com.nopecho.restdocs.types.DocsParameter
import io.restassured.http.ContentType
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSpecification
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders
import org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields
import org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields
import org.springframework.restdocs.request.RequestDocumentation.relaxedPathParameters
import org.springframework.restdocs.request.RequestDocumentation.relaxedQueryParameters
import org.springframework.restdocs.snippet.Snippet
import org.springframework.restdocs.snippet.TemplatedSnippet

internal fun RequestSpecification.apiDocs(
    spec: RequestSpecification,
    summary: String = "",
    description: String = "",
    id: String = "{method-name}",
    snippets: () -> Array<out Snippet>
): RequestSpecification {
    return spec(spec)
        .contentType(ContentType.JSON)
        .filter(
            document(
                identifier = id,
                summary = summary,
                description = description,
                snippets = snippets()
            )
        )
}

internal fun http(vararg elements: Array<TemplatedSnippet>): Array<out Snippet> {
    return elements.map { it.toList() }
        .flatten()
        .toTypedArray()
}

internal fun requestSpec(
    headers: Array<DocsHeader> = emptyArray(),
    path: Array<DocsParameter> = emptyArray(),
    query: Array<DocsParameter> = emptyArray(),
    body: Array<DocsField> = emptyArray()
): Array<TemplatedSnippet> {
    return headers.map { requestHeaders(it.toDescriptor()) }
        .map { it as TemplatedSnippet }
        .toTypedArray()
        .plus(path.map { relaxedPathParameters(it.toDescriptor()) })
        .plus(query.map { relaxedQueryParameters(it.toDescriptor()) })
        .plus(body.map { relaxedRequestFields(it.toDescriptor()) })
}

internal fun responseSpec(
    headers: Array<DocsHeader> = emptyArray(),
    body: Array<DocsField> = emptyArray()
): Array<TemplatedSnippet> {
    return headers.map { responseHeaders(it.toDescriptor()) }
        .map { it as TemplatedSnippet }
        .toTypedArray()
        .plus(body.map { relaxedResponseFields(it.toDescriptor()) })
}

internal fun ValidatableResponse.logging() {
    log().all()
}

internal fun RequestSpecification.logging() {
    log().all()
}
