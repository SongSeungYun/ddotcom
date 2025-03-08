package ddotcom.ddotcom.common.authority

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.Claims
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private lateinit var originKey: String

    private lateinit var secretKey: Key

    @Value("\${jwt.expiration}")
    private var expirationTime: Long = 0

    @PostConstruct
    fun init() {
        if (originKey.isEmpty()) {
            throw IllegalStateException("JWT Secret Key is not set. Check application.yml or environment variables.")
        }
        val decodedKey = io.jsonwebtoken.io.Decoders.BASE64.decode(originKey)
        secretKey = Keys.hmacShaKeyFor(decodedKey)
    }

    /**
     * JWT 생성
     */
    fun generateToken(username: String): String {
        val now = Date()
        val expirationDate = Date(now.time + expirationTime)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(secretKey, SignatureAlgorithm.HS256) // 수정된 메서드 사용
            .compact()
    }

    /**
     * JWT 검증 및 Claims 반환
     */
    fun validateToken(token: String): Claims? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey) // 수정된 메서드 사용
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            null // 토큰이 유효하지 않으면 null 반환
        }
    }
}
