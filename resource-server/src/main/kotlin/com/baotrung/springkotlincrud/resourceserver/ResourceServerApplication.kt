package com.baotrung.springkotlincrud.resourceserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ResourceServer

fun main(args: Array<String>) {
    runApplication<ResourceServer>(*args)
}