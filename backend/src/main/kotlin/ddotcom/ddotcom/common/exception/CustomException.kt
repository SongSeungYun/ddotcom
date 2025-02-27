package ddotcom.ddotcom.exception

import ddotcom.ddotcom.common.status.ErrorCode

class CustomException(
    val errorCode: ErrorCode,
    override val message: String?
) : RuntimeException(message)