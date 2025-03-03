package ddotcom.ddotcom.controller

import ddotcom.ddotcom.dto.UnivDtoRequest
import ddotcom.ddotcom.entity.University
import ddotcom.ddotcom.service.UnivService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/university")
@RestController
class UnivController(
    private val univService: UnivService
) {
    // 대학교 등록
    @PostMapping("/add")
    fun addUniversity(@RequestBody univDtoRequest: UnivDtoRequest): String {
        return univService.addUniversity(univDtoRequest)
    }

    @GetMapping("/find")
    fun findUniversityByEmailDomain(@RequestParam emailDomain: String): University? {
        return univService.findUniversityByEmailDomain(emailDomain)
    }
}