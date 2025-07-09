package solbin.project.salary.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.domain.user.UserEnum;

import java.time.LocalDateTime;

/**
 * DummyObject - 테스트에 사용할 메서드를 담고 있다.
 * 회원가입과 테스트 같이 새로 생성될 객체를 흉내내서 사용하기 위한 new*** 메서드
 * Authentication test와 같이 이미 생성된 객체를 흉내내서 사용하기 위한 newMock*** 메서드
 */
public class DummyObject {

    protected User newUser(String email, String name) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("1234");

        return User.builder()
                .email(email)
                .password(encodedPassword)
                .name(name)
                .role(UserEnum.USER)
                .build();
    }

    protected User newMockUser(Long id,String email, String name) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("1234");

        return User.builder()
                .id(id)
                .email(email)
                .password(encodedPassword)
                .name(name)
                .role(UserEnum.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
