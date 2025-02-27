package ddotcom.ddotcom.service

import ddotcom.ddotcom.common.status.ErrorCode
import ddotcom.ddotcom.exception.CustomException
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import java.util.Random

@Service
class EmailService(
    private val emailSender: JavaMailSender,
    private val templateEngine: TemplateEngine
) {

    @Value("\${spring.mail.username}")
    private lateinit var from: String

    // 이메일 인증 코드 전송 (비동기 처리)
    @Async
    fun sendAuthEmail(email: String, authCode: String) {
        try {
            val emailForm = makeEmailForm(email, authCode)
            emailSender.send(emailForm)
        } catch (e: Exception) {
            throw CustomException(ErrorCode.BAD_REQUEST, e.message ?: "An error occurred while sending the email.")
        }
    }

    // 이메일 폼 생성
    @Throws(MessagingException::class)
    private fun makeEmailForm(email: String, authCode: String): MimeMessage {
        val message = emailSender.createMimeMessage()
        message.addRecipients(MimeMessage.RecipientType.TO, email) // 수신자 설정
        message.subject = "디닷컴 회원가입 이메일 인증" // 제목 설정
        message.setFrom(from) // 발신자 설정
        message.setContent(setContext(authCode), "text/html;charset=euc-kr") // HTML 내용 설정

        return message
    }

    // HTML 내용 생성 및 인증 코드 포함
    private fun setContext(authCode: String): String {
        val context = Context()
        context.setVariable("code", authCode)
        return templateEngine.process("email", context)
    }

    // 인증 코드 생성
    fun makeAuthCode(): String {
        val random = Random()
        val key = StringBuilder()

        repeat(8) {
            when (random.nextInt(3)) {
                0 -> key.append((random.nextInt(26) + 97).toChar()) // 소문자 추가
                1 -> key.append((random.nextInt(26) + 65).toChar()) // 대문자 추가
                2 -> key.append(random.nextInt(10)) // 숫자 추가
            }
        }
        return key.toString()
    }
}