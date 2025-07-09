package solbin.project.salary.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import solbin.project.salary.config.auth.LoginUser;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.domain.user.UserEnum;

import java.util.Date;

/**
 * 토근을 생성, 검증하는 로직을 담고 있는 JwtProcess 클래스
 */
public class JwtProcess {

    // 토큰 생성
    public static String create(LoginUser loginUser) {
        String jwtToken = JWT.create()
                .withSubject("salary")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVo.EXPIRATION_TIME))
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getRole() + "")
                .sign(Algorithm.HMAC512(JwtVo.SECRET));

        return JwtVo.TOKEN_PREFIX + jwtToken;
    }

    // 토큰 검증
    public static LoginUser verify(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JwtVo.SECRET)).build().verify(token);
        Long id = jwt.getClaim("id").asLong();
        String role = jwt.getClaim("role").asString();
        User user = User.builder().id(id).role(UserEnum.valueOf(role)).build();
        return new LoginUser(user);
    }

}
