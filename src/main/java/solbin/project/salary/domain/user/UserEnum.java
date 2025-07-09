package solbin.project.salary.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UserEnum - 접근 권한을 위한 권한포함
 */
@AllArgsConstructor
@Getter
public enum UserEnum {
    ADMIN("관리자"),USER("고객");

    private String value;
}
