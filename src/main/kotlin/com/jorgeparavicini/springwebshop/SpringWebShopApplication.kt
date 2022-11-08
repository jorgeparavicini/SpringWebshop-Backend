package com.jorgeparavicini.springwebshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringWebShopApplication {}

fun main(args: Array<String>) {
    runApplication<SpringWebShopApplication>(*args)
}
