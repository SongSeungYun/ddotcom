//package ddotcom.ddotcom.database
//
////import com.mongodb.MongoClientSettings
//import com.mongodb.client.MongoClient
//import com.mongodb.client.MongoClients
//import io.github.cdimascio.dotenv.dotenv
//import org.springframework.beans.factory.annotation.Qualifier
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.context.annotation.Primary
//import org.springframework.data.mongodb.MongoDatabaseFactory
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
//import org.springframework.data.mongodb.core.convert.DbRefResolver
//import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
//import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
//import org.springframework.data.mongodb.core.convert.MappingMongoConverter
//import org.springframework.data.mongodb.core.mapping.MongoMappingContext
//
//@Configuration
//class MultipleMongoConfig(
//    private val mongoMappingContext: MongoMappingContext
//) {
//    @Bean
//    fun mongoClient(): MongoClient {
//        val dotenv = dotenv()
//        return MongoClients.create(dotenv["DB_URL"])
//    }
//
//    @Bean(name = ["memberDatabaseFactory"])
//    fun memberDatabaseFactory(): MongoDatabaseFactory {
//        return SimpleMongoClientDatabaseFactory(mongoClient(), "member")
//    }
//
//    @Bean(name = ["productDatabaseFactory"])
//    fun productDatabaseFactory(): MongoDatabaseFactory {
//        return SimpleMongoClientDatabaseFactory(mongoClient(), "product")
//    }
//    @Bean(name = ["memberMappingMongoConverter"])
//    fun memberMappingMongoConverter(
//        @Qualifier("memberDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory // member DB 팩토리 사용
//    ): MappingMongoConverter {
//        val dbRefResolver: DbRefResolver = DefaultDbRefResolver(mongoDatabaseFactory)
//        val converter = MappingMongoConverter(dbRefResolver, mongoMappingContext)
//        // _class 필드 제거 설정
//        converter.setTypeMapper(DefaultMongoTypeMapper(null))
//        return converter
//    }
//
//    @Bean(name = ["productMappingMongoConverter"])
//    fun productMappingMongoConverter(
//        @Qualifier("productDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory // product DB 팩토리 사용
//    ): MappingMongoConverter {
//        val dbRefResolver: DbRefResolver = DefaultDbRefResolver(mongoDatabaseFactory)
//        val converter = MappingMongoConverter(dbRefResolver, mongoMappingContext)
//        // _class 필드 제거 설정
//        converter.setTypeMapper(DefaultMongoTypeMapper(null))
//        return converter
//    }
//
//    @Primary // 기본적으로 사용할 템플릿으로 지정 (member DB)
//    @Bean(name = ["memberMongoTemplate"])
//    fun membermongoTemplate(
//        @Qualifier("memberDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory,
//        @Qualifier("memberMappingMongoConverter") mappingMongoConverter: MappingMongoConverter // 컨버터 주입받음
//    ): MongoTemplate {
//        return MongoTemplate(mongoDatabaseFactory, mappingMongoConverter)
//    }
//
//    @Bean(name = ["productMongoTemplate"])
//    fun productmongoTemplate(
//        @Qualifier("productDatabaseFactory") mongoDatabaseFactory: MongoDatabaseFactory,
//        @Qualifier("productMappingMongoConverter") mappingMongoConverter: MappingMongoConverter // 컨버터 주입받음
//    ): MongoTemplate {
//        return MongoTemplate(mongoDatabaseFactory, mappingMongoConverter)
//    }
//}