package ddotcom.ddotcom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [
	org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration::class,
	org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration::class
])
class DdotcomApplication

fun main(args: Array<String>) {
	runApplication<DdotcomApplication>(*args)
}