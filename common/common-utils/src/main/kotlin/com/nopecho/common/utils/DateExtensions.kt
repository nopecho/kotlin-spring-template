package com.nopecho.common.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

val KST: ZoneId = ZoneId.of("Asia/Seoul")
val UTC: ZoneId = ZoneId.of("UTC")

fun String.toLocalDateTime(zone: ZoneId = UTC): LocalDateTime? {
    return runCatching {
        if (this.endsWith("Z")) {
            return ZonedDateTime.parse(this)
                .withZoneSameInstant(zone)
                .toLocalDateTime()
        }
        LocalDateTime.parse(this)
    }.getOrElse {
        log().warn("failure String to LocalDateTime. string: $this, message: ${it.message}")
        null
    }
}

fun ZonedDateTime.toLocal(zone: ZoneId = KST): LocalDateTime {
    return this.withZoneSameInstant(zone)
        .toLocalDateTime()
}

fun LocalDateTime.toZoned(from: ZoneId = KST, to: ZoneId = UTC): ZonedDateTime {
    return this.atZone(from)
        .withZoneSameInstant(to)
}