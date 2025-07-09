package solbin.project.salary.dto.login;

import lombok.Getter;
import lombok.Setter;

/**
 *  LoginReqDto - 로그인 요청 DTO
 *  회원가입시에 이미 유효성 검사를 해서 따로 유효성 검사 x
 */
@Getter @Setter
public class LoginReqDto {

    private String username;
    private String password;

}
