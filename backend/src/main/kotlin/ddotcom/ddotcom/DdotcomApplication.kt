package ddotcom.ddotcom

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication//(exclude = [DataSourceAutoConfiguration::class])
class DdotcomApplication

fun main(args: Array<String>) {
	runApplication<DdotcomApplication>(*args)
}
