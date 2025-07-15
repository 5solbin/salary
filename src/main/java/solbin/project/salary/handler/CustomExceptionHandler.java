package solbin.project.salary.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import solbin.project.salary.controller.UserController;
import solbin.project.salary.controller.WorklogController;
import solbin.project.salary.dto.ResponseDto;
import solbin.project.salary.handler.ex.CustomApiException;
import solbin.project.salary.handler.ex.CustomForbiddenException;
import solbin.project.salary.handler.ex.CustomValidationException;

/**
 * 예외 처리를 전역에서 제어할 수 있는 Handler 클래스
 *
 * ApiException, ForbiddenException, ValidationException 세가지로 구성돼있다.
 */
@RestControllerAdvice(annotations = {RestControllerAdvice.class}, basePackageClasses =  {WorklogController.class, UserController.class})
public class CustomExceptionHandler {

    Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomForbiddenException.class)
    public ResponseEntity<?> apiException(CustomForbiddenException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> apiException(CustomValidationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }
}
