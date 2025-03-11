package ddotcom.ddotcom.common.authority

import org.apache.catalina.filters.CorsFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig{

    @Bean
    fun securityFilterChain(http: HttpSecurity, jwtAuthFilter: JwtAuthFilter): SecurityFilterChain {
        http
            .csrf { it.disable() } // CSRF 비활성화
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("api/member/login","/api/member/signup", "/api/member/check-login-id", "/api/member/verify-email").permitAll()
                auth.requestMatchers("/api/member/**").authenticated()
                auth.requestMatchers("/api/email/**").permitAll()
                auth.requestMatchers("/api/university/find", "/api/university/dormitories").permitAll()
                auth.requestMatchers("/api/university/add").hasRole("ADMIN")
                auth.anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()
}