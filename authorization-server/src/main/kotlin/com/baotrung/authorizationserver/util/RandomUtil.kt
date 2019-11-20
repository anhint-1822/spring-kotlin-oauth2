package com.baotrung.authorizationserver.util

class RandomUtil {

    companion object {

        private val alphanumeric: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        fun generateAlphanumeric(length: Int): String {
            return List(length) { alphanumeric.random() }.joinToString("")
        }
    }
}