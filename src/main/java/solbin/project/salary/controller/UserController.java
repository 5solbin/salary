package solbin.project.salary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solbin.project.salary.dto.ResponseDto;
import solbin.project.salary.dto.join.JoinReqDto;
import solbin.project.salary.dto.join.JoinResDto;
import solbin.project.salary.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> login(@RequestBody JoinReqDto joinReqDto, BindingResult result) {

        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();

            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(new ResponseDto<>(-1, "유효성 검사 실패", errors), HttpStatus.BAD_REQUEST);
        }

        JoinResDto joinResDto = userService.join(joinReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "회원가입 성공", joinResDto), HttpStatus.OK);
    }
}
