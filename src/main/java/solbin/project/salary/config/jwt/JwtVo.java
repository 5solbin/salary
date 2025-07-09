package solbin.project.salary.config.jwt;

/**
 * Jwt 기본 설정을 위한 인터페이스
 */
public interface JwtVo {

    public static final String SECRET = "금방";
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";

}
