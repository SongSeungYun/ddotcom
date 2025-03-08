package ddotcom.ddotcom.controller

import ddotcom.ddotcom.dto.MemberDtoRequest
import ddotcom.ddotcom.dto.ResponseWrapper
import ddotcom.ddotcom.service.EmailService
import ddotcom.ddotcom.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/member")
@RestController
@CrossOrigin(origins = ["http://localhost:5173"])
class MemberController(
    private val memberService: MemberService,
    private val emailService: EmailService
) {
    //회원가입
    @PostMapping("/signup")
    fun signUp(@RequestBody memberDtoRequest: MemberDtoRequest): ResponseEntity<ResponseWrapper<String>> {
        val resultMessage = memberService.signUp(memberDtoRequest)
        return ResponseEntity.ok(
            ResponseWrapper(
                request = null,
                status = HttpStatus.OK,
                success = true,
                message = resultMessage,
                data = null
            )
        )
    }

    //로그인 아이디 중복 체크하기
    @GetMapping("/check-login-id")
    fun checkLoginId(@RequestParam loginId: String): ResponseEntity<ResponseWrapper<Boolean>> {
        val isAvailable = memberService.isLoginIdAvailable(loginId)
        return ResponseEntity.ok(
            ResponseWrapper(
                request = null,
                status = HttpStatus.OK,
                success = true,
                message = if (isAvailable) "사용 가능한 아이디입니다." else "이미 사용 중인 아이디입니다.",
                data = isAvailable
            )
        )
    }

    @PostMapping("/verify-email")
    fun verifyEmailAndGetUniversity(@RequestParam email: String, @RequestParam code: String): ResponseEntity<ResponseWrapper<String?>> {
        val (isVerified, universityName) = emailService.verifyEmailCodeAndMapUniversity(email, code)

        if (!isVerified) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ResponseWrapper(
                    request = null,
                    status = HttpStatus.UNAUTHORIZED,
                    success = false,
                    message = "이메일 인증 실패",
                    data = null
                )
            )
        }

        return ResponseEntity.ok(
            ResponseWrapper(
                request = null,
                status = HttpStatus.OK,
                success = true,
                message = "이메일 인증 성공",
                data = universityName // 매핑된 대학교 이름 반환
            )
        )
    }
}