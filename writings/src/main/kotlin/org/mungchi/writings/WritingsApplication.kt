package org.mungchi.writings

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WritingsApplication

fun main(args: Array<String>) {
	runApplication<WritingsApplication>(*args)
}
