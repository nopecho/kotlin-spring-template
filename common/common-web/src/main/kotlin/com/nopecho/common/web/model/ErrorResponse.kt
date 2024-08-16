package com.nopecho.common.web.model

import com.nopecho.common.utils.log

data class ErrorResponse(
    val code: Int,
    val message: String? = null,
) {
    fun logging(optional: String? = null) {
        val optionalLog = optional?.let { ", $it" } ?: ""
        log().warn("[ErrorResponse] --x-- message: ${message}${optionalLog}")
    }
}