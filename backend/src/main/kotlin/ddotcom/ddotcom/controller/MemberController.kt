package ddotcom.ddotcom.controller

import ddotcom.ddotcom.common.dto.BaseResponse
import ddotcom.ddotcom.common.exception.InvalidInputException
import ddotcom.ddotcom.dto.*
import ddotcom.ddotcom.service.EmailService
import ddotcom.ddotcom.service.MemberService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
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
    fun signUp(@Valid @RequestBody memberDtoRequest: MemberDtoRequest): ResponseEntity<ResponseWrapper<String>> {
        val result = memberService.signUp(memberDtoRequest)
        return if (result.startsWith("이미 등록된") || result.startsWith("필수")) {
            ResponseEntity.ok(
                ResponseWrapper(
                    request = null,
                    status = HttpStatus.OK,
                    success = false,
                    message = result,
                    data = null
                )
            )
        } else {
            ResponseEntity.ok(
                ResponseWrapper(
                    request = null,
                    status = HttpStatus.OK,
                    success = true,
                    message = result,
                    data = null
                )
            )
        }
//        return ResponseEntity.ok(
//            ResponseWrapper(
//                request = null,
//                status = HttpStatus.OK,
//                success = true,
//                message = resultMessage,
//                data = null
//            )
//        )
    }

    //로그인 아이디 중복 체크하기
    @GetMapping("/check-login-id")
    fun checkLoginId(@RequestParam loginId: String): ResponseEntity<ResponseWrapper<Boolean>> {
        val isAvailable = memberService.checkLoginIdAvailability(loginId)
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

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<ResponseWrapper<String?>> {
        val result = memberService.login(loginDto)

        return if (result.startsWith("잘못된 로그인 아이디") || result.startsWith("로그인 아이디와 비밀번호는")) {
            ResponseEntity.ok(
                ResponseWrapper(
                    request = null,
                    status = HttpStatus.OK,
                    success = false,
                    message = result,
                    data = null
                )
            )
        } else {
            ResponseEntity.ok(
                ResponseWrapper(
                    request = null,
                    status = HttpStatus.OK,
                    success = true,
                    message = "로그인 성공",
                    data = result // JWT 토큰 반환
                )
            )
        }
    }

    //내 정보 찾기
    @GetMapping("/my-info")
    fun getMyInfo(@AuthenticationPrincipal username: String): MemberDtoResponse {
        println("🔵 현재 로그인한 사용자: $username")
        val member = memberService.getMemberByLoginId(username)
            ?: throw InvalidInputException("loginId", "회원 정보를 찾을 수 없습니다.")

        return MemberDtoResponse(
            loginId = member.loginId,
            name = member.name,
            phoneNumber = member.phoneNumber,
            email = member.email,
            university = member.university,
            dormitory = member.dormitory,
            nickname = member.nickname
        )
    }

    @PutMapping("/my-info/nickname")
    fun updateNickname(
        @AuthenticationPrincipal username: String,
        @Valid @RequestBody nicknameUpdateRequest: NicknameUpdateRequest
    ): ResponseEntity<ResponseWrapper<String>> {
        println("🔵 현재 로그인한 사용자: $username")

        memberService.updateNickname(username, nicknameUpdateRequest)

        return ResponseEntity.ok(
            ResponseWrapper(
                request = null,
                status = HttpStatus.OK,
                success = true,
                message = "닉네임이 성공적으로 변경되었습니다.",
                data = null
            )
        )
    }

    @PutMapping("/my-info/login-id")
    fun updateLoginId(
        @AuthenticationPrincipal username: String,
        @Valid @RequestBody loginIdUpdateRequest: LoginIdUpdateRequest
    ): ResponseEntity<ResponseWrapper<String>> {
        println("🔵 현재 로그인한 사용자: $username")

        memberService.updateLoginId(username, loginIdUpdateRequest)

        return ResponseEntity.ok(
            ResponseWrapper(
                request = null,
                status = HttpStatus.OK,
                success = true,
                message = "로그인 아이디가 성공적으로 변경되었습니다.",
                data = null
            )
        )
    }

    // 비밀번호 변경
    @PutMapping("/my-info/password")
    fun updatePassword(
        @AuthenticationPrincipal username: String,
        @RequestBody passwordUpdateRequest: PasswordUpdateRequest
    ): ResponseEntity<ResponseWrapper<String>> {
        println("🔵 현재 로그인한 사용자: $username")

        memberService.updatePassword(username, passwordUpdateRequest)

        return ResponseEntity.ok(
            ResponseWrapper(
                request = null,
                status = HttpStatus.OK,
                success = true,
                message = "비밀번호가 성공적으로 변경되었습니다.",
                data = null
            )
        )
    }
}