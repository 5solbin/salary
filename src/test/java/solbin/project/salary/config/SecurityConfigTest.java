package solbin.project.salary.config;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SecurityConfigTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void authentication_test() throws Exception{
        //given

        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/s/hello"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();

        System.out.println("테스트 : " + httpStatusCode);
        System.out.println("테스트 : " + responseBody);

        //then
        Assertions.assertThat(httpStatusCode).isEqualTo(401);
    }

    @Test
    public void authorization_test() throws Exception{
        //given

        //when
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/admin/hello"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        int httpStatusCode = resultActions.andReturn().getResponse().getStatus();

        System.out.println("테스트 : " + httpStatusCode);
        System.out.println("테스트 : " + responseBody);

        //then
        Assertions.assertThat(httpStatusCode).isEqualTo(401);
    }

}