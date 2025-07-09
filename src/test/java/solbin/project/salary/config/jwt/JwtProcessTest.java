package solbin.project.salary.config.jwt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import solbin.project.salary.config.auth.LoginUser;
import solbin.project.salary.domain.User;
import solbin.project.salary.domain.UserEnum;

import static org.junit.jupiter.api.Assertions.*;

class JwtProcessTest {

    private String createToken() {
        User user = User.builder().id(1L).role(UserEnum.USER).build();
        LoginUser loginUser = new LoginUser(user);

        return JwtProcess.create(loginUser);
    }

    @Test
    public void 토큰생성() throws Exception{
        //given
        User user = User.builder().id(1L).role(UserEnum.USER).build();
        LoginUser loginUser = new LoginUser(user);

        //when
        String jwtToken = createToken();
        System.out.println("테스트 : " + jwtToken);

        //then
        assertTrue(jwtToken.startsWith(JwtVo.TOKEN_PREFIX));
    }

    @Test
    public void 토큰검증() throws Exception{
        // given
        String token = createToken();
        String jwtToken = token.replace(JwtVo.TOKEN_PREFIX, "");

        //when
        LoginUser loginUser = JwtProcess.verify(jwtToken);

        //then
        Assertions.assertThat(loginUser.getUser().getId()).isEqualTo(1L);
    }

}