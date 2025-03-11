package ddotcom.ddotcom.service

//import ddotcom.ddotcom.database.MultipleMongoConfig
import ddotcom.ddotcom.common.authority.JwtTokenProvider
import ddotcom.ddotcom.common.status.ROLE
import ddotcom.ddotcom.dto.LoginDto
import ddotcom.ddotcom.dto.MemberDtoRequest
import ddotcom.ddotcom.entity.Member
//import ddotcom.ddotcom.member.repository.MemberRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    @Qualifier("memberMongoTemplate") private val memberMongoTemplate: MongoTemplate,
    @Qualifier("productMongoTemplate") private val productMongoTemplate: MongoTemplate,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder
) {
    //Id중복 확인
    fun findMemberByLoginId(loginId: String): Member? {
        val query = Query(Criteria.where("loginId").`is`(loginId))
        return memberMongoTemplate.findOne(query, Member::class.java, "member_info")
    }

    fun isLoginIdAvailable(loginId: String): Boolean {
        try {
            val query = Query(Criteria.where("loginId").`is`(loginId))
            val member = memberMongoTemplate.findOne(query, Member::class.java, "member_info")
            return member == null
        } catch (e: Exception) {
            println("Error checking login ID availability: $e")
            return false
        }
    }

    //회원가입
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        println("Received DTO: $memberDtoRequest") // 디버깅용 로그 추가

        if (memberDtoRequest.loginId.isBlank() ||
            memberDtoRequest.password.isBlank() ||
            memberDtoRequest.name.isBlank() ||
            memberDtoRequest.phoneNumber.isBlank() ||
            memberDtoRequest.email.isBlank() ||
            memberDtoRequest.university.isBlank() ||
            memberDtoRequest.dormitory.isBlank()
        ) {
            return "필수 입력값이 누락되었습니다."
        }
        // loginId 중복 확인
        if (!isLoginIdAvailable(memberDtoRequest.loginId)) {
            return "이미 등록된 아이디입니다."
        }

        // 회원 정보 저장
        val member = Member(
            _id = null, // MongoDB 자동 생성
            loginId = memberDtoRequest.loginId,
            password = passwordEncoder.encode(memberDtoRequest.password),
            name = memberDtoRequest.name,
            phoneNumber = memberDtoRequest.phoneNumber,
            email = memberDtoRequest.email,
            university = memberDtoRequest.university, // 이메일 인증 후 매핑된 대학교 이름 사용
            dormitory = memberDtoRequest.dormitory, // 사용자가 선택한 기숙사 이름 사용
            role = ROLE.MEMBER
        )
        memberMongoTemplate.save(member, "member_info")

        return "회원가입이 완료되었습니다."
    }

    //로그인
    fun login(loginDto: LoginDto): String {
        // 입력값 검증
        if (loginDto.loginId.isBlank() || loginDto.password.isBlank()) {
            return "로그인 아이디와 비밀번호는 비어있으면 안됩니다."
        }

        // loginId로 회원 조회
        val query = Query(Criteria.where("loginId").`is`(loginDto.loginId))
        val member = memberMongoTemplate.findOne(query, Member::class.java, "member_info")

        // 아이디 존재 여부 확인
        if (member == null || !passwordEncoder.matches(loginDto.password, member.password)) {
            return "잘못된 로그인 아이디 또는 비밀번호입니다."
        }

        // JWT 토큰 생성
        return jwtTokenProvider.generateToken(member.loginId, listOf(member.role.name))
    }
}