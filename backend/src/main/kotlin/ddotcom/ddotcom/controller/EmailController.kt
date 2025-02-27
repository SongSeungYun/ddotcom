package ddotcom.ddotcom.controller

import ddotcom.ddotcom.dto.ResponseWrapper
import ddotcom.ddotcom.dto.EmailDtoRequest
import ddotcom.ddotcom.service.EmailService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/email/auth")
class EmailController(
    private val request: HttpServletRequest,
    private val emailService: EmailService
) {

    @PostMapping("")
    fun confirmEmailAuth(@RequestBody dto: EmailDtoRequest): ResponseEntity<*> {
        // 인증 코드 생성 및 비동기 전송 처리
        val authCode = emailService.makeAuthCode()
        emailService.sendAuthEmail(dto.email, authCode)

        return ResponseEntity.ok(
            ResponseWrapper(
                request = request,
                status = HttpStatus.OK,
                success = true,
                message = "이메일이 성공적으로 전송되었습니다.",
                data = "AuthCode: [$authCode]"
            )
        )
    }
}