package com.nopecho.infra.client

import org.springframework.stereotype.Component

@Component
class FooClientAdapter(private val fooClient: FooFeignClient) {
}