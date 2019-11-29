package com.baotrung.authorizationserver.util

class RandomUtil {

    companion object {

        private val numeric: CharRange = ('0'..'9')
        private val alphanumeric: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        fun generateNumeric(length: Int): String {
            return List(length) { numeric.random() }.joinToString("")
        }

        fun generateAlphanumeric(length: Int): String {
            return List(length) { alphanumeric.random() }.joinToString("")
        }
    }
}