package me.pedroeugenio.blazebot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableFeignClients
open class Boot

fun main(args: Array<String>) {
    runApplication<Boot>(*args)
}
