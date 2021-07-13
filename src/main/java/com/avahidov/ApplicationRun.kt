package com.avahidov

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource


@SpringBootApplication
@PropertySource("classpath:application.config")
class ApplicationRun

    fun main(args: Array<String>) {
        runApplication<ApplicationRun>(*args)
    }
