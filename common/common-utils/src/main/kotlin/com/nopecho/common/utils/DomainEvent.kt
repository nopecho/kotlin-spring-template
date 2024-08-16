package com.nopecho.common.utils

import java.time.LocalDateTime


interface DomainEvent<T> {
    val id: String
    val type: String
    val time: LocalDateTime
    val data: T
}