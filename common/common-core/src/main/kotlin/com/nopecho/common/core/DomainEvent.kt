package com.nopecho.common.core

import java.time.LocalDateTime


interface DomainEvent<T> {
    val id: String
    val type: String
    val time: LocalDateTime
    val data: T
}