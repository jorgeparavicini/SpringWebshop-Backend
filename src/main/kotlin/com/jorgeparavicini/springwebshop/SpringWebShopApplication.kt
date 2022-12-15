package com.jorgeparavicini.springwebshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SpringWebShopApplication {}

fun main(args: Array<String>) {
    runApplication<SpringWebShopApplication>(*args)
}
