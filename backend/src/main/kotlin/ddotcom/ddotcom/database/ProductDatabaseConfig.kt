package ddotcom.ddotcom.database
//import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import io.github.cdimascio.dotenv.dotenv
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.core.convert.DbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
@Configuration
class ProductDatabaseConfig(
    private val mongoMappingContext: MongoMappingContext
) {

    @Bean(name = ["productDatabaseFactory"])
    fun productDatabaseFactory(): MongoDatabaseFactory {
        val dotenv = dotenv()
        return SimpleMongoClientDatabaseFactory(MongoClients.create(dotenv["DB_URL"]), "product")
    }

    @Bean(name = ["productMappingMongoConverter"])
    fun productMappingMongoConverter(
        @Qualifier("productDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory
    ): MappingMongoConverter {
        val dbRefResolver = DefaultDbRefResolver(mongoDatabaseFactory)
        val converter = MappingMongoConverter(dbRefResolver, mongoMappingContext)
        converter.setTypeMapper(DefaultMongoTypeMapper(null))
        return converter
    }

    @Bean(name = ["productMongoTemplate"])
    fun productMongoTemplate(
        @Qualifier("productDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory,
        @Qualifier("productMappingMongoConverter") mappingMongoConverter: MappingMongoConverter
    ): MongoTemplate {
        return MongoTemplate(mongoDatabaseFactory, mappingMongoConverter)
    }
}
