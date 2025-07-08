package solbin.project.salary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solbin.project.salary.domain.User;
import solbin.project.salary.dto.join.JoinReqDto;
import solbin.project.salary.dto.join.JoinResDto;
import solbin.project.salary.handler.ex.CustomApiException;
import solbin.project.salary.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public JoinResDto join(JoinReqDto dto) {
        // 1. 이메일 중복 검사
        Optional<User> userOP = userRepository.findByEmail(dto.getEmail());
        if (userOP.isPresent()) {
            throw new CustomApiException("동일한 이메일이 존재합니다.");
        }

        // 2. 회원가입 완료, dto 반환
        User user = dto.toEntity(encoder);
        userRepository.save(user);
        return new JoinResDto(user);
    }

}
