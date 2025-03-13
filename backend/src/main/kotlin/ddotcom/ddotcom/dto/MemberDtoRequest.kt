package ddotcom.ddotcom.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ddotcom.ddotcom.common.annotation.ValidEnum
import ddotcom.ddotcom.common.status.ROLE
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

//import java.time.LocalDate

//회원가입 dto
data class MemberDtoRequest(
    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    private val _password: String?,

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:NotBlank
    @JsonProperty("phoneNumber")
    private val _phoneNumber: String?,

    @field:NotBlank
    @field:Email
    @JsonProperty("email")
    private val _email: String?,

    @field:NotBlank
    @JsonProperty("university")
    private val _university: String?,

    @field:NotBlank
    @JsonProperty("dormitory")
    private val _dormitory: String?,

    @ValidEnum(enumClass = ROLE::class, message = "유효하지 않은 ROLE 값입니다.")
    @JsonProperty("role")
    private val _role: ROLE?
){
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    val phoneNumber: String
        get() = _phoneNumber!!
    val email: String
        get() = _email!!
    val university: String
        get() = _university!!
    val dormitory: String
        get() = _dormitory!!
    val role: ROLE
        get() = _role!!
}

//로그인 dto
data class LoginDto(
    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password: String?,
) {
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
}

//내 정보 조회시 응답 dto
data class MemberDtoResponse(
    val loginId: String,
    val name:String,
    val phoneNumber: String,
    val email: String,
    val university: String,
    val dormitory: String
)