package com.reminiscence.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY;

    @Autowired
    public JwtUtil(Environment env) {
        this.SECRET_KEY = env.getProperty("jwt.secret");
    }

    // JWT 토큰의 만료 시간 계산
    public Date generateExpirationDate(int expirationTime) {
        return new Date(System.currentTimeMillis() + expirationTime); // 계산된 만료 시간 반환
    }

    public String extractClaimValue(String token, String claimName) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();
            return claims.get(claimName, String.class); // 클레임 값 가져오기

        } catch (Exception e) {
            // 토큰 파싱이 실패한 경우 또는 클레임이 없는 경우 예외 처리
            e.printStackTrace();
            return null;
        }
    }

    // Request의 Header에서 AccessToken 값을 가져옵니다. "Authorization" : "Bearer AccessToken:"
    public String resolveAccessToken(HttpServletRequest request) {
        String requestHeader = request.getHeader(JwtProperties.HEADER_STRING);
        String tokenPrefix = JwtProperties.TOKEN_PREFIX+JwtProperties.ACCESS_TOKEN_PREFIX;

        if(requestHeader != null && requestHeader.startsWith(tokenPrefix))
            return requestHeader.substring(tokenPrefix.length());
        return null;
    }

    // Request의 Header에서 RefreshToken 값을 가져옵니다. "Authorization" : "Bearer RefreshToken:"
    public String resolveRefreshToken(HttpServletRequest request) {
        String requestHeader = request.getHeader(JwtProperties.HEADER_STRING);
        String tokenPrefix = JwtProperties.TOKEN_PREFIX+JwtProperties.REFRESH_TOKEN_PREFIX;

        if(requestHeader != null && requestHeader.startsWith(tokenPrefix))
            return requestHeader.substring(tokenPrefix.length());
        return null;
    }

//    // 클레임 정보 설정
//    public Map<String, Object> setCustomClaims(String key, String value) {
//        Map<String, Object> customClaims = new HashMap<>();
//        customClaims.put(key, value);
//
//        return customClaims;
//    }

}
