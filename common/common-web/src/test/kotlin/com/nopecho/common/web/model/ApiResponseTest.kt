package com.nopecho.common.web.model

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ApiResponseTest {

    @Test
    fun ok() {
        val actual = ApiResponse.ok("data")

        actual.body shouldBe ApiResponse("data")
        actual.statusCode shouldBe HttpStatus.OK
    }
}