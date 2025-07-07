package solbin.project.salary.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResponseDto<T> {

    private final Integer code;
    private final String message;
    private final T data;
}
