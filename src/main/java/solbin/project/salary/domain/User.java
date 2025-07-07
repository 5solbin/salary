package solbin.project.salary.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

// check : role 과 같은 추가 옵션
@Entity
@NoArgsConstructor
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public User(LocalDateTime createdAt, String email, Long id, String name, String password, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
        this.updatedAt = updatedAt;
    }
}
