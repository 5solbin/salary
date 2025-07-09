package solbin.project.salary.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 기본 응답 DTO
 */
@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {

    private final Integer code;
    private final String message;
    private final T data;
}
