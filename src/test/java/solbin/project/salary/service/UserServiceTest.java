package solbin.project.salary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.dto.user.join.JoinReqDto;
import solbin.project.salary.dto.user.join.JoinResDto;
import solbin.project.salary.dummy.DummyObject;
import solbin.project.salary.handler.ex.CustomApiException;
import solbin.project.salary.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends DummyObject {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void 회원가입_성공(){
        //given
        JoinReqDto dto = new JoinReqDto();
        dto.setEmail("bean@naver.com"); // 추후 validataion 을 위해서 이메일 형식
        dto.setPassword("password");
        dto.setName("빈");

        // stub 1
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // stub 2
        User user = newUser( "bean@naver.com", "빈");
        when(userRepository.save(any())).thenReturn(user);

        //when
        JoinResDto join = userService.join(dto);
        System.out.println("테스트 : " + join.getName());

        //then
        assertThat(join.getName()).isEqualTo("빈");
    }

    @Test
    public void 회원가입_실패() {
        //given
        JoinReqDto dto = new JoinReqDto();
        dto.setEmail("bean@naver.com"); // 추후 validataion 을 위해서 이메일 형식
        dto.setPassword("password");
        dto.setName("빈");

        // stub
        User user = newMockUser(1L, "bean@naver.com", "빈");
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        //when


        //then
        CustomApiException e = assertThrows(CustomApiException.class, () -> userService.join(dto));
        assertThat(e.getMessage()).isEqualTo("동일한 이메일이 존재합니다.");
    }

}