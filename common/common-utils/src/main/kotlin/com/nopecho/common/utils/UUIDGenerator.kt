package com.nopecho.common.utils

import com.fasterxml.uuid.Generators

object UUIDGenerator {
    fun generate(type: UUIDType = UUIDType.TIME): String {
        return when (type) {
            UUIDType.TIME -> generateTimeBasedUUID()
            UUIDType.RANDOM -> generateRandomUUID()
        }
    }

    private fun generateTimeBasedUUID(): String {
        return Generators.timeBasedGenerator()
            .generate()
            .toString()
    }

    private fun generateRandomUUID(): String {
        return Generators.randomBasedGenerator()
            .generate()
            .toString()
    }
}