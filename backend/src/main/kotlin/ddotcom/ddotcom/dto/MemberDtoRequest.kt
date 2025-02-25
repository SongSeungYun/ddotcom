package ddotcom.ddotcom.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ddotcom.ddotcom.common.annotation.ValidEnum
import ddotcom.ddotcom.common.status.Gender
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

//import java.time.LocalDate

data class MemberDtoRequest(
    private val _memberId: String?,

    @field:NotBlank
    @JsonProperty("login_id")
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
    @field:ValidEnum(enumClass = Gender::class, message = "MAN 이나 WOMAN 중 하나를 선택해주세요.")
    @JsonProperty("gender")
    private val _gender: String?,

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

    @field:NotBlank
    @JsonProperty("bankAccount")
    private val _bankAccount: String?,

    @field:NotBlank
    @JsonProperty("bankName")
    private val _bankName: String?
){
    val memberId: String
        get() = _memberId!!
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    val gender: Gender
        get() = Gender.valueOf(_gender!!)
    val phoneNumber: String
        get() = _phoneNumber!!
    val email: String
        get() = _email!!
    val university: String
        get() = _university!!
    val dormitory: String
        get() = _dormitory!!
    val bankAccount: String
        get() = _bankAccount!!
    val bankName: String
        get() = _bankName!!
}