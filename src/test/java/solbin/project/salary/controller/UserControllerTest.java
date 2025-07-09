package solbin.project.salary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import solbin.project.salary.dto.join.JoinReqDto;
import solbin.project.salary.dummy.DummyObject;
import solbin.project.salary.repository.UserRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:db/teardown.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserControllerTest extends DummyObject {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setup() {
        userRepository.save(newUser("bean@naver.com", "빈"));
        em.clear();
    }

    @Test
    public void 회원가입_성공() throws Exception{
        //given
        JoinReqDto dto = new JoinReqDto();
        dto.setEmail("sol@naver.com"); // 추후 validataion 을 위해서 이메일 형식
        dto.setPassword("password");
        dto.setName("솔");

        String requestBody = om.writeValueAsString(dto);

        //when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/api/join")
                        .content(requestBody).contentType(MediaType.APPLICATION_JSON));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void 회원가입_실패() throws Exception{
        //given
        JoinReqDto dto = new JoinReqDto();
        dto.setEmail("bean@naver.com"); // 추후 validataion 을 위해서 이메일 형식
        dto.setPassword("password");
        dto.setName("솔");

        String requestBody = om.writeValueAsString(dto);
        //when
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.post("/api/join")
                        .content(requestBody).contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isBadRequest());
    }

}