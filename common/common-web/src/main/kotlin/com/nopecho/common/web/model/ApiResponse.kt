package com.nopecho.common.web.model

import org.springframework.http.ResponseEntity

data class ApiResponse<T>(
    val data: T? = null,
    val meta: MetaResponse? = null,
) {
    companion object {
        fun <T : Any> ok(data: T, meta: MetaResponse? = null): ResponseEntity<ApiResponse<T>> {
            return ResponseEntity.ok(of(data, meta))
        }

        private fun <T : Any> of(data: T, meta: MetaResponse? = null): ApiResponse<T> {
            return ApiResponse(data, meta)
        }
    }
}
