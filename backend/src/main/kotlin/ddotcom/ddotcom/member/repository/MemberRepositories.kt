//package ddotcom.ddotcom.member.repository
//
//import ddotcom.ddotcom.member.entity.Member
//import org.springframework.data.mongodb.repository.MongoRepository
//
//interface MemberRepository : MongoRepository<Member, String> {
//    fun findByLoginId(loginId: String): Member?
//}