package com.nopecho.common.web.model

data class MetaResponse(
    val page: Long? = null,
    val size: Long? = null,
    val total: Long? = null,
    val hasNext: Boolean? = null,
)