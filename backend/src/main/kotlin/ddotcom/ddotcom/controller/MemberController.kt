package ddotcom.ddotcom.controller

import ddotcom.ddotcom.dto.MemberDtoRequest
import ddotcom.ddotcom.service.EmailService
import ddotcom.ddotcom.service.MemberService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/member")
@RestController
class MemberController(
    private val memberService: MemberService,
    private val emailService: EmailService
) {
    //회원가입
    @PostMapping("/signup")
    fun signUp(@RequestBody memberDtoRequest: MemberDtoRequest): String {
        return memberService.signUp(memberDtoRequest)
    }
}