package solbin.project.salary.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import solbin.project.salary.domain.user.User;
import solbin.project.salary.repository.UserRepository;

/**
 * 로그인을 위한 LoginService 클래스
 * UserDetailsService를 구현하여 이메일 기반 사용자 조회
 * 존재하지 않는 유저의 경우 예외 발생
 * 인증 성공시 사용자 정보를 포함한 LoginUser 객체 반환
 */
@Service
public class LoginService  implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userPS = userRepository.findByEmail(username).orElseThrow(
                () -> new InternalAuthenticationServiceException("인증 실패")
        );
        return new LoginUser(userPS);
    }
}
