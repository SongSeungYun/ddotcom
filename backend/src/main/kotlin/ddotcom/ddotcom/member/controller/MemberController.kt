package ddotcom.ddotcom.member.controller

import ddotcom.ddotcom.member.dto.MemberDtoRequest
import ddotcom.ddotcom.member.entity.Member
import ddotcom.ddotcom.member.service.MemberService
import org.springframework.web.bind.annotation.*
//밑에는 홍길동 이름가진애들 삭제하려고 만들었음
import org.springframework.http.ResponseEntity


@RequestMapping("/api/member")
@RestController
class MemberController(
    private val memberService: MemberService
) {
    //회원가입
    @PostMapping("/signup")
    fun signUp(@RequestBody memberDtoRequest: MemberDtoRequest): String {
        return memberService.signUp(memberDtoRequest)
    }
}