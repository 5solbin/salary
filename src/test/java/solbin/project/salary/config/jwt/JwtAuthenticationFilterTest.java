package solbin.project.salary.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import solbin.project.salary.dto.login.LoginReqDto;
import solbin.project.salary.dummy.DummyObject;
import solbin.project.salary.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class JwtAuthenticationFilterTest extends DummyObject {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.save(newUser("sol@naver.com", "솔"));
    }

    @Test
    public void 인증성공() throws Exception{
        //given
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("sol@naver.com");
        loginReqDto.setPassword("1234");
        String requestBody = om.writeValueAsString(loginReqDto);
        System.out.println("테스트 : " + requestBody);

        //when
        ResultActions resultActions = mvc.perform(post("/api/login")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String jwtToken = resultActions.andReturn().getResponse().getHeader(JwtVo.HEADER);
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);
        System.out.println("테스트 : " + jwtToken);

        //then
        resultActions.andExpect(status().isOk());
        assertNotNull(jwtToken);
        assertTrue(jwtToken.startsWith(JwtVo.TOKEN_PREFIX));
        resultActions.andExpect(jsonPath("$.data.username").value("sol@naver.com"));
    }

    @Test
    public void 인증실패() throws Exception{
        //given
        LoginReqDto loginReqDto = new LoginReqDto();
        loginReqDto.setUsername("sol@naver.com");
        loginReqDto.setPassword("12345");
        String requestBody = om.writeValueAsString(loginReqDto);

        //when
        ResultActions resultActions = mvc.perform(post("/api/login")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isUnauthorized());
    }


}