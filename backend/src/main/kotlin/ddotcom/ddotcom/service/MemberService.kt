package ddotcom.ddotcom.service

//import ddotcom.ddotcom.database.MultipleMongoConfig
import ddotcom.ddotcom.dto.MemberDtoRequest
import ddotcom.ddotcom.entity.Member
//import ddotcom.ddotcom.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class MemberService(
    @Qualifier("memberMongoTemplate") private val memberMongoTemplate: MongoTemplate,
    @Qualifier("productMongoTemplate") private val productMongoTemplate: MongoTemplate
)
{
    //Id중복 확인
    fun findMemberByLoginId(loginId: String): Member? {
        val query = Query(Criteria.where("loginId").`is`(loginId))
        return memberMongoTemplate.findOne(query, Member::class.java, "member_info")
    }

    //회원가입
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        var member: Member? = findMemberByLoginId(memberDtoRequest.loginId)
        if(member!=null){
            return "이미 등록된 아이디입니다"
        }
        member = Member(
            _id = null,  // MongoDB는 자동으로 _id 생성
            memberId = memberDtoRequest.memberId,
            loginId = memberDtoRequest.loginId,
            password = memberDtoRequest.password,
            name = memberDtoRequest.name,
            gender = memberDtoRequest.gender,
            phoneNumber = memberDtoRequest.phoneNumber,
            email = memberDtoRequest.email,
            university = memberDtoRequest.university,
            dormitory = memberDtoRequest.dormitory,
            bankAccount = memberDtoRequest.bankAccount,
            bankName = memberDtoRequest.bankName
        )
        memberMongoTemplate.save(member, "member_info")

        return "회원가입이 완료되었습니다."
    }
}