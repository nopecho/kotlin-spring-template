package com.nopecho.infra.client

import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "fooFeignClient", url = "\${client.foo.url}")
interface FooFeignClient {
}