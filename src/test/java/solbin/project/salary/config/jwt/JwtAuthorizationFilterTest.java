package solbin.project.salary.config.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import solbin.project.salary.config.auth.LoginUser;
import solbin.project.salary.domain.User;
import solbin.project.salary.domain.UserEnum;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class JwtAuthorizationFilterTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void 인가성공() throws Exception{
        //given
        User user = User.builder().id(1L).role(UserEnum.USER).build();
        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);

        //when
        ResultActions resultActions = mvc.perform(get("/api/s/hello").header(JwtVo.HEADER, jwtToken));

        //then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void 인가실패() throws Exception{
        //given

        //when
        ResultActions resultActions = mvc.perform(get("/api/s/hello"));


        //then
        resultActions.andExpect(status().isUnauthorized());
    }

    @Test
    public void 관리자인가() throws Exception{
        //given
        User user = User.builder().id(1L).role(UserEnum.USER).build();
        LoginUser loginUser = new LoginUser(user);
        String jwtToken = JwtProcess.create(loginUser);

        //when
        ResultActions resultActions = mvc.perform(get("/api/admin/hello").header(JwtVo.HEADER, jwtToken));

        //then
        resultActions.andExpect(status().isForbidden());
    }

}