package ddotcom.ddotcom.member.dto

import ddotcom.ddotcom.common.status.Gender
//import java.time.LocalDate

data class MemberDtoRequest(
    val memberId: String,
    val loginId: String,
    val password: String,
    val name: String,
    val gender: Gender,
    val phoneNumber: String,
    val email: String,
    val university: String,
    val dormitory: String,
    val bankAccount: String,
    val bankName: String
)