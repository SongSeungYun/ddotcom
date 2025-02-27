package ddotcom.ddotcom.config

//import com.mongodb.MongoClientSettings
import com.mongodb.ConnectionString
import com.mongodb.client.MongoClients
import io.github.cdimascio.dotenv.dotenv
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Configuration
class MemberDatabaseConfig(
    private val mongoMappingContext: MongoMappingContext
) {
    @Value("\${spring.data.mongodb.uri}")
    private lateinit var mongoUri: String

    @Primary
    @Bean(name = ["memberDatabaseFactory"])
    fun memberDatabaseFactory(): MongoDatabaseFactory {
        //val dotenv = dotenv()
        return SimpleMongoClientDatabaseFactory(MongoClients.create(ConnectionString(mongoUri)), "member")
    }

    @Primary
    @Bean(name = ["memberMappingMongoConverter"])
    fun memberMappingMongoConverter(
        @Qualifier("memberDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory
    ): MappingMongoConverter {
        val dbRefResolver = DefaultDbRefResolver(mongoDatabaseFactory)
        val converter = MappingMongoConverter(dbRefResolver, mongoMappingContext)
        converter.setTypeMapper(DefaultMongoTypeMapper(null))
        return converter
    }

    @Primary
    @Bean(name = ["memberMongoTemplate"])
    fun memberMongoTemplate(
        @Qualifier("memberDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory,
        @Qualifier("memberMappingMongoConverter") mappingMongoConverter: MappingMongoConverter
    ): MongoTemplate {
        return MongoTemplate(mongoDatabaseFactory, mappingMongoConverter)
    }
}