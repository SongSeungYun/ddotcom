package ddotcom.ddotcom.common.dbconfig

//import com.mongodb.MongoClientSettings
import io.github.cdimascio.dotenv.dotenv
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MultipleMongoConfig {
    @Bean
    fun mongoClient(): MongoClient {
        val dotenv = dotenv()
        return MongoClients.create(dotenv["DB_URL"])
    }

    @Primary
    @Bean(name = ["memberMongoTemplate"])
    fun membermongoTemplate(): MongoTemplate {
        return MongoTemplate(mongoClient(), "member")
    }

    @Bean(name=["productMongoTemplate"])
    fun productmongoTemplate(): MongoTemplate {
        return MongoTemplate(mongoClient(), "product")
    }
}